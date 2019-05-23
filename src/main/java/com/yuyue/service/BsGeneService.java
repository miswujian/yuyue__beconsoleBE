package com.yuyue.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.yuyue.dao.BsGeneDAO;
import com.yuyue.pojo.BsGene;
import com.yuyue.util.Page4Navigator;
import com.yuyue.util.UpdateUtil;

@Service
public class BsGeneService {

	@Autowired
	private BsGeneDAO bsGeneDAO;
	
	public int add(BsGene bsGene) {
		if(bsGene == null)
			return 0;
		try {
			bsGeneDAO.save(bsGene);
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}
	
	public int update(BsGene bsGene) {
		if(bsGene == null||bsGene.getGeneId()==null)
			return 0;
		BsGene bg = bsGeneDAO.findOne(bsGene.getGeneId());
		UpdateUtil.copyNullProperties(bg, bsGene);
		try {
			bsGeneDAO.saveAndFlush(bsGene);
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}
	
	public BsGene get(int geneId) {
		return bsGeneDAO.findOne(geneId);
	}
	
	public Page4Navigator<BsGene> list(int start, int size, int navigatePages){
		Sort ssort = new Sort(Sort.Direction.ASC,"sort");
		Sort sort = new Sort(Sort.Direction.DESC,"geneId").and(ssort);
		Pageable pageable = new PageRequest(start, size, sort);
		Page<BsGene> pageFromJPA = bsGeneDAO.findAll(pageable);
		return new Page4Navigator<>(pageFromJPA, navigatePages);
	}
	
	public Page4Navigator<BsGene> list(int start, int size, int navigatePages, String keyword){
		Sort ssort = new Sort(Sort.Direction.ASC,"sort");
		Sort sort = new Sort(Sort.Direction.DESC,"geneId").and(ssort);
		Pageable pageable = new PageRequest(start, size, sort);
		Page<BsGene> pageFromJPA = bsGeneDAO.findByName("%" + keyword + "%", pageable);
		return new Page4Navigator<>(pageFromJPA, navigatePages);
	}
	
	public List<BsGene> list(){
		return bsGeneDAO.findAll();
	}
	
	public int delete(int geneId) {
		try {
			bsGeneDAO.delete(geneId);
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}
	
}
