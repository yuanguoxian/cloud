package com.cloud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.cloud.entities.Dept;

@RestController
public class DeptController_Consumer {
	@Autowired
	private RestTemplate restTemplate;
	private final String PROVIDER_URL_PREFIX = "http://cloud-provider-dept"; 
	@RequestMapping("consumer/dept/add")
	public boolean add(Dept dept) {
		return this.restTemplate.postForObject(PROVIDER_URL_PREFIX + "/dept/add", dept, boolean.class);
	}
	@GetMapping("consumer/dept/{id}")
	public Dept get(@PathVariable("id")Long id) {
		return this.restTemplate.getForObject(PROVIDER_URL_PREFIX + "/dept/" + id, Dept.class);
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("consumer/dept/list")
	public List<Dept> list(){
		return this.restTemplate.getForObject(PROVIDER_URL_PREFIX + "/dept/list", List.class);
	}
	@GetMapping("consumer/discovery")
	public Object discovery() {
		return this.restTemplate.getForObject(PROVIDER_URL_PREFIX + "/discovery", Object.class);
	}
}
