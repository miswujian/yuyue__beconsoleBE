package com.yuyue.service;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.yuyue.dao.RsUsercreditDAO;
import com.yuyue.pojo.RsUsercredit;
import com.yuyue.util.Page4Navigator;
import com.yuyue.util.StringUtil;

@Service
public class RsUsercreditService {

	@Autowired
	private RsUsercreditDAO rsUsercreditDAO;
	
	public Page4Navigator<RsUsercredit> list(int start, int size, int navigatePages){
		Sort sort = new Sort(Sort.Direction.DESC,"usercreditId");
		Pageable pageable = new PageRequest(start, size, sort);
		Page<RsUsercredit> pageFromJPA = rsUsercreditDAO.findAll(pageable);
		return new Page4Navigator<>(pageFromJPA, navigatePages);
	}
	
	public Page4Navigator<RsUsercredit> list(int start, int size, int navigatePages, String type, Date starttime, Date endtime, 
			String keyword){
		Sort sort = new Sort(Sort.Direction.DESC,"usercreditId");
		Pageable pageable = new PageRequest(start, size, sort);
		long time = System.currentTimeMillis();
		if (starttime == null)
			starttime = new Date(time / 10);
		if (endtime == null)
			endtime = new Date(time);
		ArrayList<Byte> typelist = new ArrayList<>();
		if(!StringUtil.isNumeric(type))
			type = "";
		if(!type.equals(""))
			typelist.add((byte)Integer.parseInt(type));
		else {
			typelist.add((byte)0);
			typelist.add((byte)1);
			typelist.add((byte)2);
		}
		Page<RsUsercredit> pageFromJPA = rsUsercreditDAO.
				queryByTypeInAndCreateTimeBetweenAndNickNameLikeOrVipNoLikeOrOrderNoLikeOrTradeNoLike
				(typelist, starttime, endtime, "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%", pageable);
		return new Page4Navigator<>(pageFromJPA, navigatePages);
	}
	
	public int add(RsUsercredit ru) {
		try {
			rsUsercreditDAO.saveAndFlush(ru);
			return 1;
		} catch (Exception e) {
			return 0;
		}	
	}
	
}
