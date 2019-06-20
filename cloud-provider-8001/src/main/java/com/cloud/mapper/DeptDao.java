package com.cloud.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.cloud.entities.Dept;

@Mapper
public interface DeptDao {
	public boolean addDept(Dept dept);
	public Dept fingById(Long id);
	public List<Dept> findAll();
}
