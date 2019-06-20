package com.cloud.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloud.entities.Dept;
import com.cloud.mapper.DeptDao;
import com.cloud.service.DeptService;
@Service
public class DeptServiceImpl implements DeptService{

	@Autowired
	private DeptDao deptDao;
	@Override
	public boolean add(Dept dept) {
		return this.deptDao.addDept(dept);
	}

	@Override
	public Dept get(Long id) {
		return this.deptDao.fingById(id);
	}

	@Override
	public List<Dept> list() {
		return this.deptDao.findAll();
	}

}
