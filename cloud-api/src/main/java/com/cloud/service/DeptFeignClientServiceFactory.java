package com.cloud.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.cloud.entities.Dept;

import feign.hystrix.FallbackFactory;
@Component
public class DeptFeignClientServiceFactory implements FallbackFactory<DeptFeignClientService>{

	@Override
	public DeptFeignClientService create(Throwable cause) {
		return new DeptFeignClientService() {
			
			@Override
			public List<Dept> getAll() {
				return null;
			}
			
			@Override
			public Dept get(Long id) {
				return new Dept().setDeptno(id).setDname("该ID：" + id + " 不存在").setDb_source("No this database in mysql !");
			}
			
			@Override
			public Object discovery() {
				return null;
			}
			
			@Override
			public boolean add(Dept dept) {
				// TODO Auto-generated method stub
				return false;
			}
		};
	}

}
