package com.yuyue.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.yuyue.dao.BsBookcellinfoDAO;
import com.yuyue.pojo.BsBookcaseinfo;
import com.yuyue.pojo.BsBookcellinfo;
import com.yuyue.pojo.BsBookinstore;
import com.yuyue.pojo.RsCurborrowrecord;
import com.yuyue.pojo.RsHisborrowrecord;
import com.yuyue.util.Page4Navigator;

@Service
public class BsBookcellinfoService {

	@Autowired
	private BsBookcellinfoDAO bsBookcellinfoDAO;
	
	@Autowired
	private BsBookinstoreService bsBookinstoreService;
	
	@Autowired
	private RsCurborrowrecordService rsCurborrowrecordService;
	
	public int getRepair() {
		List<BsBookcellinfo> bbs = bsBookcellinfoDAO.findByRepair(1);
		if(bbs == null || bbs.isEmpty())
			return 0;
		return bbs.size();
	}
	
	public Page4Navigator<BsBookcellinfo> list(int start, int size, int navigatePages, BsBookcaseinfo bsBookcaseinfo){
		Sort sort = new Sort(Sort.Direction.DESC,"cellId");
		Pageable pageable = new PageRequest(start, size, sort);
		Page<BsBookcellinfo> pageFromJPA = bsBookcellinfoDAO.findByBsBookcaseinfo(bsBookcaseinfo, pageable);
		Page4Navigator<BsBookcellinfo> bbcs = new Page4Navigator<>(pageFromJPA, navigatePages);
		for(BsBookcellinfo bbc : bbcs.getContent()) {
			if(bbc.getIsBusy()==1) {
				BsBookinstore bbi = bsBookinstoreService.getBsBookinstore(bbc);
				if(bbi!=null) {
					if(bbi.getBsBookinfo()!=null)
						bbc.setBookName(bbi.getBsBookinfo().getBookName());
					bbc.setBookId(bbi.getBookId().toString());
					if(bbi.getStatus()==1) {
						RsCurborrowrecord rcb = rsCurborrowrecordService.getRsCurborrowrecord(bbi);
						bbc.setOrderNo(rcb.getOrderNo());
						bbc.setCreateTime(rcb.getCreateTime());
					}
				}
			}
		}
		return bbcs;
	}
	
	public List<BsBookcellinfo> list(BsBookcaseinfo bbc){
		return bsBookcellinfoDAO.findByBsBookcaseinfo(bbc);
	}
	
	public List<Integer> list(List<Integer> caseIds){
		List<Integer> ids = new ArrayList<>();
		for(Integer caseId : caseIds) {
			ids.addAll(list(caseId));
		}
		return ids;
	}
	
	public List<Integer> list(Integer caseId){
		List<BsBookcellinfo> bbcs = bsBookcellinfoDAO.findByCaseId(caseId);
		List<Integer> caseIds = new ArrayList<>();
		for(BsBookcellinfo bbc :bbcs) {
			caseIds.add(bbc.getCellId());
		}
		return caseIds;
	}
	
	public BsBookcellinfo getBookcellinfo(int cellId) {
		return bsBookcellinfoDAO.findOne(cellId);
	}
	
	public int addBookcellinfo(BsBookcellinfo bbc) {
		try {
			BsBookcellinfo bb = bsBookcellinfoDAO.save(bbc);
			return bb.getCellId();
		} catch (Exception e) {
			return 0;
		}
		
	}
	
	public int updateBookcellinfo(BsBookcellinfo bbc) {
		try {
			BsBookcellinfo bb = bsBookcellinfoDAO.save(bbc);
			return bb.getCellId();
		} catch (Exception e) {
			return 0;
		}
		
	}
	
	public void setBookcellinfoNull(RsHisborrowrecord rhb) {
		rhb.getBsBookinstore().setBsBookcellinfo(null);
	}
	
	public void setBookcellinfoNull(RsCurborrowrecord rhb) {
		rhb.getBsBookinstore().setBsBookcellinfo(null);
	}
	
	public void setBookcellinfoNull(List<RsHisborrowrecord> rhbs) {
		for(RsHisborrowrecord rhb : rhbs)
			setBookcellinfoNull(rhb);
	}
	
}
