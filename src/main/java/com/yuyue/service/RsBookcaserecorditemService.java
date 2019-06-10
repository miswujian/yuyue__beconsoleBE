package com.yuyue.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuyue.dao.RsBookcaserecorditemDAO;
import com.yuyue.pojo.RsBookcaserecorditem;
import com.yuyue.util.UpdateUtil;

@Service
public class RsBookcaserecorditemService {

	@Autowired
	private RsBookcaserecorditemDAO rsBookcaserecorditemDAO;
	
	public RsBookcaserecorditem get(int bookcaseitemId) {
		return rsBookcaserecorditemDAO.findOne(bookcaseitemId);
	}
	
	public int delete(int bookcaseitemId) throws Exception {
		try {
			rsBookcaserecorditemDAO.delete(bookcaseitemId);
			return 1;
		} catch (Exception e) {
			throw new Exception();
		}
	}
	
	public int add(RsBookcaserecorditem rsBookcaserecorditem) throws Exception {
		try {
			rsBookcaserecorditemDAO.save(rsBookcaserecorditem);
			return 1;
		} catch (Exception e) {
			throw new Exception();
		}
	}
	
	public int update(RsBookcaserecorditem rsBookcaserecorditem) throws Exception {
		try {
			RsBookcaserecorditem rbri = rsBookcaserecorditemDAO.findOne(rsBookcaserecorditem.getBookcaseitemId());
			UpdateUtil.copyNullProperties(rbri, rsBookcaserecorditem);
			rsBookcaserecorditemDAO.save(rsBookcaserecorditem);
			return 1;
		} catch (Exception e) {
			throw new Exception();
		}
	}
	
}
