package com.yuyue.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuyue.dao.BeDepartmentDAO;
import com.yuyue.pojo.BeDepartment;

@Service
public class BeDepartmentService {

	@Autowired
	private BeDepartmentDAO beDepartmentDAO;
	
	public List<BeDepartment> list(){
		return beDepartmentDAO.findAll();
	}
	
}
