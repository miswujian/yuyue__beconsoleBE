package com.yuyue.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuyue.dao.RsBookincolumnDAO;
import com.yuyue.pojo.RsBookincolumn;

@Service
public class RsBookincolumnService {

	@Autowired
	private RsBookincolumnDAO rsBookincolumnDAO;
	
	public List<RsBookincolumn> list(){
		return rsBookincolumnDAO.findAll();
	}
	
}
