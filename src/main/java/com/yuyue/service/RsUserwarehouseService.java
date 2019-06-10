package com.yuyue.service;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuyue.dao.RsUserwarehouseDAO;
import com.yuyue.pojo.BeUser;
import com.yuyue.pojo.BeWarehouse;
import com.yuyue.pojo.BsBookcaseinfo;
import com.yuyue.pojo.RsUserwarehouse;
import com.yuyue.util.Lists;

@Service
public class RsUserwarehouseService {

	@Autowired
	private RsUserwarehouseDAO rsUserwarehouseDAO;
	
	@Autowired
	private BsBookcaseinfoService bsBookcaseinfoService;
	
	@Autowired
	private BeUserService beUserService;
	
	public List<BsBookcaseinfo> getBookcaseinfo(BeUser bu, BeWarehouse bw){
		List<RsUserwarehouse> ruws = rsUserwarehouseDAO.findByBeUserAndBeWarehouseAndType(bu, bw, (byte)2);
		List<BsBookcaseinfo> bbcs = new ArrayList<>();
		for(RsUserwarehouse ruw : ruws) {
			bbcs.add(ruw.getBsBookcaseinfo());
		}
		return bbcs;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int update(Lists lists) {
		try {
			int userId = lists.getUserId();
			BeUser beUser = beUserService.getByUId(userId);
			if(lists.getAddCaseIds() == null || lists.getAddCaseIds().isEmpty()) {	
			}else {
				List<Integer> addCaseIds = lists.getAddCaseIds();
				for(Integer caseId : addCaseIds) {
					BsBookcaseinfo bbc = bsBookcaseinfoService.get(caseId);
					RsUserwarehouse ruw = new RsUserwarehouse();
					ruw.setBeUser(beUser);
					ruw.setBsBookcaseinfo(bbc);
					ruw.setBeWarehouse(bbc.getBeWarehouse());
					rsUserwarehouseDAO.save(ruw);
				}
			}
			if(lists.getDeleteCaseIds() == null || lists.getDeleteCaseIds().isEmpty()) {
			}else {
				List<Integer> deleteCaseIds = lists.getDeleteCaseIds();
				for(Integer caseId : deleteCaseIds) {
					rsUserwarehouseDAO.deleteByUidAndCaseId(userId, caseId);
				}
			}
			return 1;
		} catch (Exception e) {
			throw e;
		}
	}
	
	public int delete(int userId, int caseId) {
		try {
			rsUserwarehouseDAO.deleteByUidAndCaseId(userId, caseId);
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}
	
}
