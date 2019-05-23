package com.yuyue.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuyue.dao.BsBookcolumnDAO;
import com.yuyue.pojo.BsBookcolumn;

@Service
public class BsBookcolumnService {

	@Autowired
	private BsBookcolumnDAO bsBookcolumnDAO;
	
	public BsBookcolumn get(int columnId) {
		return bsBookcolumnDAO.findOne(columnId);
	}
	
}
