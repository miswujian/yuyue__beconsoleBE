package com.yuyue.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuyue.dao.RsStoragerecorditemDAO;
import com.yuyue.pojo.RsStoragerecorditem;
import com.yuyue.util.UpdateUtil;

@Service
public class RsStoragerecorditemService {

	@Autowired
	private RsStoragerecorditemDAO rsStoragerecorditemDAO;
	
	public RsStoragerecorditem get(int storageitemId) {
		return rsStoragerecorditemDAO.findOne(storageitemId);
	}
	
	public int add(RsStoragerecorditem rsri) throws Exception {
		try {
			rsStoragerecorditemDAO.save(rsri);
			return 1;
		} catch (Exception e) {
			throw new Exception();
		}
	}
	
	public int delete(int storageitemId) throws Exception {
		try {
			rsStoragerecorditemDAO.delete(storageitemId);
			return 1;
		} catch (Exception e) {
			throw new Exception();
		}
	}
	
	public int update(RsStoragerecorditem rsri) throws Exception {
		try {
			RsStoragerecorditem rsStoragerecorditem = rsStoragerecorditemDAO.findOne(rsri.getStorageitemId());
			UpdateUtil.copyNullProperties(rsStoragerecorditem, rsri);
			rsStoragerecorditemDAO.saveAndFlush(rsri);
			return 1;
		} catch (Exception e) {
			throw new Exception();
		}
	}
	
}
