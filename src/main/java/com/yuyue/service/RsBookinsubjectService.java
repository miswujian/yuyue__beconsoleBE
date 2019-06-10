package com.yuyue.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuyue.dao.RsBookinsubjectDAO;
import com.yuyue.pojo.BsBooksubject;
import com.yuyue.pojo.RsBookinsubject;

@Service
public class RsBookinsubjectService{

	@Autowired
	private RsBookinsubjectDAO rsBookinsubjectDAO;
	
	public List<RsBookinsubject> getByBsBooksubject(BsBooksubject bsBooksubject){
		List<RsBookinsubject> rbis = rsBookinsubjectDAO.findByBsBooksubject(bsBooksubject);
		setGenesNull(rbis);
		return rbis;
	}

	public int delete(int bookinsubjectId) {
		try {
			rsBookinsubjectDAO.delete(bookinsubjectId);
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}
	
	public int addBookinsubject(RsBookinsubject rsBookinsubject) {
		try {
			RsBookinsubject rbs = rsBookinsubjectDAO.save(rsBookinsubject);
			return rbs.getBookinsubjectId();
		} catch (Exception e) {
			return 0;
		}
	}
	
	public void setGenesNull(RsBookinsubject rbi) {
		rbi.getBsBookinfo().setBsGenes(null);
	}
	
	public void setGenesNull(List<RsBookinsubject> rbis) {
		for(RsBookinsubject rbi : rbis) {
			setGenesNull(rbi);
		}
	}
	
}
