package com.cloud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.entities.Dept;
import com.cloud.service.DeptService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class DeptController {
	@Autowired
	private DiscoveryClient discoveryClient;
	@Autowired
	private DeptService deptService;

	@PostMapping("dept/add")
	public boolean addDept(@RequestBody Dept dept) {
		return this.deptService.add(dept);
	}

	@HystrixCommand(fallbackMethod = "processHystrix")
	@GetMapping(value = { "dept/{id}", "dept" })
	public Dept findById(@PathVariable(value = "id", required = false) Long id) {
		Dept dept = this.deptService.get(id);
		if(dept == null) {
		
			throw new RuntimeException("该ID：" + id + "不存在");
		}
		return dept;
	}
	@HystrixCommand
	@GetMapping("dept/list")
	public List<Dept> getAll() {
		return this.deptService.list();
	}

	@GetMapping("discovery")
	public Object discovery() {
		List<String> services = this.discoveryClient.getServices();
		services.forEach(System.out::println);
		List<ServiceInstance> instances = this.discoveryClient.getInstances("cloud-provider-dept");
		instances.forEach(e -> System.out
				.println(e.getInstanceId() + "\t" + e.getHost() + "\t" + e.getPort() + "\t" + e.getUri()));
		return this.discoveryClient;
	}

	public Dept processHystrix(@PathVariable("id") Long id) {
		return new Dept().setDeptno(id).setDname("该ID：" + id + " 不存在").setDb_source("No this database in mysql !");
	}
}
