package com.yuyue.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.yuyue.dao.BsPublishinfoDAO;
import com.yuyue.pojo.BsPublishinfo;
import com.yuyue.util.Page4Navigator;

@Service
public class BsPublishinfoService {

	@Autowired
	private BsPublishinfoDAO bsPublishinfoDAO;
	
	public List<BsPublishinfo> list(){
		return bsPublishinfoDAO.findAll();
	}
	
	public Page4Navigator<BsPublishinfo> list(int start, int size, int navigatePages){
		Sort sort = new Sort(Sort.Direction.DESC, "pubId");
		Pageable pageable = new PageRequest(start, size, sort);
		Page<BsPublishinfo> pageFromJPA = bsPublishinfoDAO.findAll(pageable);
		return new Page4Navigator<>(pageFromJPA, navigatePages);
	}
	
	public Page4Navigator<BsPublishinfo> list(int start, int size, int navigatePages, String keyword){
		Sort sort = new Sort(Sort.Direction.DESC, "pubId");
		Pageable pageable = new PageRequest(start, size, sort);
		Page<BsPublishinfo> pageFromJPA = bsPublishinfoDAO.findByPubNameLike("%"+keyword+"%",pageable);
		return new Page4Navigator<>(pageFromJPA, navigatePages);
	}
	
	public ArrayList<Integer> getPubIds(){
		List<BsPublishinfo> bps = bsPublishinfoDAO.findAll();
		ArrayList<Integer> pubIdlist = new ArrayList<>();
		for(BsPublishinfo bp : bps)
			pubIdlist.add(bp.getPubId());
		return pubIdlist;
	}
	
	public BsPublishinfo getPublishinfo(int pubId) {
		return bsPublishinfoDAO.findOne(pubId);
	}
	
	public int addPublishinfo(BsPublishinfo bpi) {
		try {
			BsPublishinfo bp = bsPublishinfoDAO.save(bpi);
			return bp.getPubId();
		} catch (Exception e) {
			return 0;
		}
		
	}
	
	public int updatePublishinfo(BsPublishinfo bpi) {
		try {
			BsPublishinfo bp = bsPublishinfoDAO.save(bpi);
			return bp.getPubId();
		} catch (Exception e) {
			return 0;
		}
		
	}
	
	public int deletePublishinfo(int pubId) {
		try {
			bsPublishinfoDAO.delete(pubId);
			return 1;
		} catch (Exception e) {
			return 0;
		}
		
	}
	
}
