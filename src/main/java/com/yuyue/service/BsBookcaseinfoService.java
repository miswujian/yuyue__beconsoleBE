package com.yuyue.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuyue.dao.BsBookcaseinfoDAO;
import com.yuyue.pojo.BeOperationlog;
import com.yuyue.pojo.BeUser;
import com.yuyue.pojo.BsBookcaseinfo;
import com.yuyue.pojo.User;
import com.yuyue.util.Page4Navigator;
import com.yuyue.util.UpdateUtil;


@Service
public class BsBookcaseinfoService {

	@Autowired
	private BsBookcaseinfoDAO bsBookcaseinfoDAO;
	
	@Autowired
	private BeOperationlogService beOperationlogService;

	public List<BsBookcaseinfo> list(int warehouseId){
		return bsBookcaseinfoDAO.queryByWarehouseId(warehouseId);
	}
	
	public int getAllocation() {
		List<BsBookcaseinfo> bbs = bsBookcaseinfoDAO.findByAllocation(1);
		if(bbs == null || bbs.isEmpty())
			return 0;
		return bbs.size();
	}
	
	public BsBookcaseinfo get(int caseId) {
		BsBookcaseinfo bbc = bsBookcaseinfoDAO.findOne(caseId);
		setUserNull(bbc);
		return bbc;
	}
	
	public Page4Navigator<BsBookcaseinfo> list(int start, int size, int navigatePages){
		Sort sort = new Sort(Sort.Direction.DESC,"caseId");
		Pageable pageable = new PageRequest(start, size, sort);
		Page<BsBookcaseinfo> pageFromJPA = bsBookcaseinfoDAO.findAll(pageable);
		Page4Navigator<BsBookcaseinfo> bbcs = new Page4Navigator<>(pageFromJPA, navigatePages);
		setUserNull(bbcs.getContent());
		return bbcs;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int add(BsBookcaseinfo bbc, BeUser beUser) throws Exception {
		try {
			bbc = bsBookcaseinfoDAO.save(bbc);
			BeOperationlog bo = new BeOperationlog();
			bo.setBeUser(beUser);
			bo.setCrud((byte)1);
			bo.setOperationTime(new Date());
			bo.setFeatures("bookcaseinfos");
			bo.setRemarks(beUser.getUserName()+"增加了机柜信息"+bbc.getCaseId());
			bo.setFeaturesId(bbc.getCaseId());
			beOperationlogService.add(bo);
			return 1;
		} catch (Exception e) {
			throw new Exception();
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int delete(int caseId, BeUser beUser) throws Exception {
		try {
			bsBookcaseinfoDAO.delete(caseId);
			BeOperationlog bo = new BeOperationlog();
			bo.setBeUser(beUser);
			bo.setCrud((byte)2);
			bo.setOperationTime(new Date());
			bo.setFeatures("bookcaseinfos");
			bo.setRemarks(beUser.getUserName()+"删除了机柜信息"+caseId);
			bo.setFeaturesId(caseId);
			beOperationlogService.add(bo);
			return 1;
		} catch (Exception e) {
			throw new Exception();
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int update(BsBookcaseinfo bbc, BeUser beUser) throws Exception {
		try {
			BsBookcaseinfo b = bsBookcaseinfoDAO.findOne(bbc.getCaseId());
			UpdateUtil.copyNullProperties(b, bbc);
			bsBookcaseinfoDAO.saveAndFlush(bbc);
			BeOperationlog bo = new BeOperationlog();
			bo.setBeUser(beUser);
			bo.setCrud((byte)3);
			bo.setOperationTime(new Date());
			bo.setFeatures("bookcaseinfos");
			bo.setRemarks(beUser.getUserName()+"更新了机柜信息"+bbc.getCaseId());
			bo.setFeaturesId(bbc.getCaseId());
			beOperationlogService.add(bo);
			return 1;
		} catch (Exception e) {
			throw new Exception();
		}
	}
	
	public List<BsBookcaseinfo> list(){
		return bsBookcaseinfoDAO.findAll();
	}
	
	public void setUserNull(BsBookcaseinfo bbc) {
		if(bbc.getBeUser()!=null) {
			User user = new User();
			user.setUid(bbc.getBeUser().getUid());
			user.setTelephone(bbc.getBeUser().getTelephone());
			user.setUserName(bbc.getBeUser().getUserName());
			bbc.setUser(user);
			bbc.setBeUser(null);
		}
	}
	
	public void setUserNull(List<BsBookcaseinfo> bbcs) {
		for(BsBookcaseinfo bbc : bbcs)
			setUserNull(bbc);
	}
	
	public int addBookcaseinfo(BsBookcaseinfo bbc) {
		try {
			BsBookcaseinfo bb = bsBookcaseinfoDAO.save(bbc);
			return bb.getCaseId();
		} catch (Exception e) {
			return 0;
		}
		
	}
	
	public int updateBookcaseinfo(BsBookcaseinfo bbc) {
		try {
			BsBookcaseinfo bb = bsBookcaseinfoDAO.save(bbc);
			return bb.getCaseId();
		} catch (Exception e) {
			return 0;
		}
		
	}
	
	public BsBookcaseinfo getBookcaseinfo(int caseId) {
		return bsBookcaseinfoDAO.findOne(caseId);
	}
	
}
