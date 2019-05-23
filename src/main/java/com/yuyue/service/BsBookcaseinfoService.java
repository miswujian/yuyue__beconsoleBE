package com.yuyue.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.yuyue.dao.BsBookcaseinfoDAO;
import com.yuyue.pojo.BsBookcaseinfo;
import com.yuyue.util.Page4Navigator;

@Service
public class BsBookcaseinfoService {

	@Autowired
	private BsBookcaseinfoDAO bsBookcaseinfoDAO;
	
	public List<BsBookcaseinfo> list(int warehouseId){
		return bsBookcaseinfoDAO.queryByWarehouseId(warehouseId);
	}
	
	public int getAllocation() {
		List<BsBookcaseinfo> bbs = bsBookcaseinfoDAO.findByAllocation(1);
		if(bbs == null || bbs.isEmpty())
			return 0;
		return bbs.size();
	}
	
	public Page4Navigator<BsBookcaseinfo> list(int start, int size, int navigatePages){
		Sort sort = new Sort(Sort.Direction.DESC,"caseId");
		Pageable pageable = new PageRequest(start, size, sort);
		Page<BsBookcaseinfo> pageFromJPA = bsBookcaseinfoDAO.findAll(pageable);
		return new Page4Navigator<>(pageFromJPA, navigatePages);
	}
	
	public List<BsBookcaseinfo> list(){
		return bsBookcaseinfoDAO.findAll();
	}
	
	public void setUserNull(BsBookcaseinfo bbc) {
		if(bbc.getBeUser()!=null) {
			bbc.setYwName(bbc.getBeUser().getUserName());
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
