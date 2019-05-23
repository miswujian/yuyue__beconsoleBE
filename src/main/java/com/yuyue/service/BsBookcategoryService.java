package com.yuyue.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuyue.dao.BsBookcategoryDAO;
import com.yuyue.pojo.BsBookcategory;

@Service
public class BsBookcategoryService {
	
	@Autowired
	private BsBookcategoryDAO bsBookcategoryDAO;
	
	@Autowired
	private BsBookinfoService bsBookinfoService;
	
	public List<BsBookcategory> list(){
		List<BsBookcategory> bbcs = bsBookcategoryDAO.findByParentId("00");
		getBsBookcategoryByParent(bbcs);
		for(BsBookcategory bbc : bbcs) {
			getBsBookcategoryByParent(bbc.getBsBookcategorys());
			bsBookinfoService.setBookinfo(bbc.getBsBookcategorys());
		}	
		return bbcs;
	}
	
	public List<BsBookcategory> listchild(){
		List<BsBookcategory> bbcs = bsBookcategoryDAO.findByParentId("00");
		getBsBookcategoryByParent(bbcs);
		List<BsBookcategory> childs = new ArrayList<>();
		for(BsBookcategory bbc : bbcs.get(0).getBsBookcategorys()) {
			childs.addAll(bsBookcategoryDAO.findByParentId(bbc.getCategoryId()));
		}
		return childs;
	}
	
	public void getBsBookcategoryByParent(List<BsBookcategory> bbcs) {
		for(BsBookcategory bbc : bbcs)
			getBsBookcategoryByParent(bbc);
	}
	
	public void getBsBookcategoryByParent(BsBookcategory bbc) {
		List<BsBookcategory> bbcs = bsBookcategoryDAO.findByParentId(bbc.getCategoryId());
		bbc.setBsBookcategorys(bbcs);
	}
	
}
