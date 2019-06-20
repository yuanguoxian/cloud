package com.cloud.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cloud.entities.Dept;

@FeignClient(value = "cloud-provider-dept",fallbackFactory = DeptFeignClientServiceFactory.class)
public interface DeptFeignClientService {
	@RequestMapping("dept/add")
	public boolean add(Dept dept);
	@GetMapping("dept/{id}")
	public Dept get(@PathVariable("id") Long id);
	@GetMapping("dept/list")
	public List<Dept> getAll();
	@GetMapping("discovery")
	public Object discovery();
}
