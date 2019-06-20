package com.cloud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.entities.Dept;
import com.cloud.service.DeptFeignClientService;

@RestController
public class DeptController_Consumer {
	
	@Autowired
	private DeptFeignClientService deptFeignClientService;
	
	@RequestMapping("consumer/dept/add")
	public boolean add(Dept dept) {
		return this.deptFeignClientService.add(dept);
	}
	@GetMapping("consumer/dept/{id}")
	public Dept get(@PathVariable("id")Long id) {
		return this.deptFeignClientService.get(id);
	}
	
	@GetMapping("consumer/dept/list")
	public List<Dept> list(){
		return this.deptFeignClientService.getAll();
	}
	@GetMapping("consumer/discovery")
	public Object discovery() {
		return this.deptFeignClientService.discovery();
	}
}