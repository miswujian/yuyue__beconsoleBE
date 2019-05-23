package com.yuyue.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yuyue.dao.BsVipplanDAO;
import com.yuyue.pojo.BsVipplan;

@Service
public class BsVipplanService {

	private BsVipplanDAO bsVipplanDAO;
	
	public List<BsVipplan> list(){
		return bsVipplanDAO.findAll();
	}
	
}
