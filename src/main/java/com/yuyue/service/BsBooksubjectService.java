package com.yuyue.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.yuyue.dao.BsBooksubjectDAO;
import com.yuyue.pojo.BsBooksubject;
import com.yuyue.util.Page4Navigator;
import com.yuyue.util.StringUtil;

@Service
public class BsBooksubjectService {

	@Autowired
	private BsBooksubjectDAO bsBooksubjectDAO;
	
	public Page4Navigator<BsBooksubject> list(int start, int size,int navigatePages){
		Sort ssort = new Sort(Sort.Direction.DESC,"isShow");
		Sort sort = new Sort(Sort.Direction.ASC,"sort").and(ssort);
    	Pageable pageable = new PageRequest(start, size, sort);   
    	Page<BsBooksubject> pageFromJPA = bsBooksubjectDAO.findAll(pageable);
    	return new Page4Navigator<>(pageFromJPA,navigatePages);
	}
	
	public Page4Navigator<BsBooksubject> list(int start, int size,int navigatePages, String isShow, String keyword){
		Sort ssort = new Sort(Sort.Direction.DESC,"isShow");
		Sort sort = new Sort(Sort.Direction.ASC,"sort").and(ssort);
    	Pageable pageable = new PageRequest(start, size, sort);   
    	ArrayList<Byte> isShowlist = new ArrayList<>();
    	if(!StringUtil.isNumeric(isShow))
    		isShow = "";
    	if(!isShow.equals(""))
    		isShowlist.add((byte)Integer.parseInt(isShow));
    	else {
    		isShowlist.add((byte)0);
    		isShowlist.add((byte)1);
    	}
    	Page<BsBooksubject> pageFromJPA = bsBooksubjectDAO.
    			queryByIsShowInAndSubjectNameLike(isShowlist, "%"+keyword+"%", pageable);
    	return new Page4Navigator<>(pageFromJPA,navigatePages);
	}
	
	public int addSubject(BsBooksubject bsBooksubject) {
		try {
			BsBooksubject bs = bsBooksubjectDAO.save(bsBooksubject);
			return bs.getBooksubjectId();
		} catch (Exception e) {
			return 0;
		}
		
	}
	
	public int updateSubject(BsBooksubject bsBooksubject) {
		try {
			BsBooksubject bs = bsBooksubjectDAO.save(bsBooksubject);
			return bs.getBooksubjectId();
		} catch (Exception e) {
			return 0;
		}
		
	}
	
	public BsBooksubject getSubject(int id) {
		return bsBooksubjectDAO.findOne(id);
	}
	
	public int deleteSubject(int booksubjectId) {
		try {
			bsBooksubjectDAO.delete(booksubjectId);
			return 1;
		} catch (Exception e) {
			return 0;
		} 
	}
	
}
