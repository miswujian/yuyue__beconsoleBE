package com.yuyue.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuyue.dao.RsUserdynamiclikeDAO;
import com.yuyue.pojo.RsUserdynamiclike;

@Service
public class RsUserdynamiclikeService {

	@Autowired
	private RsUserdynamiclikeDAO rsUserdynamiclikeDAO;
	
	public List<RsUserdynamiclike> list(){
		return rsUserdynamiclikeDAO.findAll();
	}
	
}
