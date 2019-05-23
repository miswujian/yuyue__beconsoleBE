package com.yuyue.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.yuyue.dao.BsSysdictDAO;
import com.yuyue.pojo.BsSysdict;
import com.yuyue.util.Page4Navigator;

@Service
public class BsSysdictService {

	@Autowired
	private BsSysdictDAO bsSysdictDAO;
	
	public Page4Navigator<BsSysdict> list(int start, int size, int navigatePages){
		Sort sort = new Sort(Sort.Direction.DESC,"dictId");
		Pageable pageable = new PageRequest(start, size, sort);
		Page<BsSysdict> pageFromJPA = bsSysdictDAO.findAll(pageable);
		return new Page4Navigator<>(pageFromJPA, navigatePages);
	}
	
	public Page4Navigator<BsSysdict> list(int start, int size, int navigatePages, String dictType){
		Sort sort = new Sort(Sort.Direction.DESC,"dictId");
		Pageable pageable = new PageRequest(start, size, sort);
		Page<BsSysdict> pageFromJPA = bsSysdictDAO.findByDictType(dictType, pageable);
		return new Page4Navigator<>(pageFromJPA, navigatePages);
	}
	
	public int update(int dictId, String dictValue) {
		BsSysdict bs = bsSysdictDAO.findOne(dictId);
		bs.setUpdateTime(new Date());
		bs.setDictValue(dictValue);
		try {
			bs = bsSysdictDAO.saveAndFlush(bs);
			return bs.getDictId(); 
		} catch (Exception e) {
			return 0;
		}
	}
	
}
