package com.yuyue.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuyue.dao.RsUserfineDAO;
import com.yuyue.pojo.RsUserfine;

@Service
public class RsUserfineService {

	@Autowired
	private RsUserfineDAO rsUserfineDAO;
	
	public RsUserfine getByOrderNo(String orderNo) {
		List<RsUserfine> rus = rsUserfineDAO.findByOrderNo(orderNo);
		if(rus.isEmpty()||rus == null)
			return null;
		return rus.get(0);
	}
	
}
