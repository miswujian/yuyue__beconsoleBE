package com.yuyue.service;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.yuyue.dao.BsInvitecodeDAO;
import com.yuyue.pojo.BsInvitecode;
import com.yuyue.util.Page4Navigator;
import com.yuyue.util.StringUtil;

@Service
public class BsInvitecodeService {

	@Autowired
	private BsInvitecodeDAO bsInvitecodeDAO;
	
	public Page4Navigator<BsInvitecode> list(int start, int size, int navigatePages){
		Sort sort = new Sort(Sort.Direction.DESC,"ivtcodeId");
		Pageable pageable = new PageRequest(start, size, sort);
		Page<BsInvitecode> pageFromJPA = bsInvitecodeDAO.findAll(pageable);
		return new Page4Navigator<>(pageFromJPA, navigatePages);
	}
	
	public Page4Navigator<BsInvitecode> list(int start, int size, int navigatePages, String status, 
			Date starttime, Date endtime, String keyword){
		Sort sort = new Sort(Sort.Direction.DESC,"ivtcodeId");
		Pageable pageable = new PageRequest(start, size, sort);
		long time = System.currentTimeMillis();
		if (starttime == null)
			starttime = new Date(time / 10);
		if (endtime == null)
			endtime = new Date(time);
		ArrayList<Byte> statuslist = new ArrayList<>();
		if(!StringUtil.isNumeric(status))
			status = "";
		if(!status.equals(""))
			statuslist.add((byte)Integer.parseInt(status));
		else {
			statuslist.add((byte)1);
			statuslist.add((byte)2);
		}
		Page<BsInvitecode> pageFromJPA = bsInvitecodeDAO.queryByStatusInAndCreateTimeBetweenAndCustNameLikeOrCodeLike
				(statuslist, starttime, endtime, "%" + keyword +"%", "%" + keyword +"%", pageable);
		return new Page4Navigator<>(pageFromJPA, navigatePages);
	}
	
}
