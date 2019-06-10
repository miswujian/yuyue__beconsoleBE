package com.yuyue.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuyue.dao.RsTransferrecorditemDAO;
import com.yuyue.pojo.RsTransferrecorditem;
import com.yuyue.util.UpdateUtil;

@Service
public class RsTransferrecorditemService {

	@Autowired
	private RsTransferrecorditemDAO rsTransferrecorditemDAO;
	
	public RsTransferrecorditem get(int transferitemId) {
		return rsTransferrecorditemDAO.findOne(transferitemId);
	}
	
	public int add(RsTransferrecorditem rtri) throws Exception {
		try {
			rsTransferrecorditemDAO.save(rtri);
			return 1;
		} catch (Exception e) {
			throw new Exception();
		}
	}
	
	public int delete(int transferitemId) throws Exception {
		try {
			rsTransferrecorditemDAO.delete(transferitemId);
			return 1;
		} catch (Exception e) {
			throw new Exception(); 
		}
	}
	
	public int update(RsTransferrecorditem rtri) throws Exception {
		try {
			RsTransferrecorditem rsTransferrecorditem = rsTransferrecorditemDAO.findOne(rtri.getTransferitemId());
			UpdateUtil.copyNullProperties(rsTransferrecorditem, rtri);
			rsTransferrecorditemDAO.saveAndFlush(rtri);
			return 1;
		} catch (Exception e) {
			throw new Exception();
		}
	}
	
}
