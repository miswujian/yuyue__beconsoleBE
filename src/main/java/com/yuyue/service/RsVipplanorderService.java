package com.yuyue.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.yuyue.dao.RsVipplanorderDAO;
import com.yuyue.pojo.RsVipplanorder;
import com.yuyue.util.Page4Navigator;
import com.yuyue.util.StringUtil;

@Service
public class RsVipplanorderService {

	@Autowired
	private RsVipplanorderDAO rsVipplanorderDAO;
	
	public Page4Navigator<RsVipplanorder> list(int start, int size, int navigatePages){
		Sort sort = new Sort(Sort.Direction.DESC,"planorderId");
		Pageable pageable = new PageRequest(start, size, sort);
		Page<RsVipplanorder> pageFromJPA = rsVipplanorderDAO.findAll(pageable);
		return new Page4Navigator<>(pageFromJPA, navigatePages);
	}
	
	public Page4Navigator<RsVipplanorder> listBystatus(byte status, int start, int size, int navigatePages){
		Sort sort = new Sort(Sort.Direction.DESC,"planorderId");
		Pageable pageable = new PageRequest(start, size, sort);
		Page<RsVipplanorder> pageFromJPA = rsVipplanorderDAO.findByStatus(status, pageable);
		return new Page4Navigator<>(pageFromJPA, navigatePages);
	}
	
	public Page4Navigator<RsVipplanorder> list(int start, int size, int navigatePages, String payType, String status, 
			Date starttime, Date endtime, String keyword){
		Sort sort = new Sort(Sort.Direction.DESC,"planorderId");
		Pageable pageable = new PageRequest(start, size, sort);
		long time = System.currentTimeMillis();
		if (starttime == null)
			starttime = new Date(time / 10);
		if (endtime == null)
			endtime = new Date(time);
		ArrayList<Byte> payTypelist = new ArrayList<>();
		if(!StringUtil.isNumeric(payType))
			payType = "";
		if(!payType.equals(""))
			payTypelist.add((byte)Integer.parseInt(payType));
		else {
			payTypelist.add((byte)1);
			payTypelist.add((byte)2);
		}
		ArrayList<Byte> statuslist = new ArrayList<>();
		if(!StringUtil.isNumeric(status))
			status = "";
		if(!status.equals(""))
			statuslist.add((byte)Integer.parseInt(status));
		else {
			statuslist.add((byte)0);
			statuslist.add((byte)1);
			statuslist.add((byte)-1);
			statuslist.add((byte)-2);
		}
		Page<RsVipplanorder> pageFromJPA = rsVipplanorderDAO.
				quertByPayTypeAndStatusAndPayTimeBetweenAndNikeNameLikeOrVipNoLikeOrOrderNoLikeOrTradeNoLike
				(payTypelist, statuslist, starttime, endtime, 
						"%"+keyword+"%", "%"+keyword+"%", "%"+keyword+"%", "%"+keyword+"%", pageable);
		return new Page4Navigator<>(pageFromJPA, navigatePages);
	}
	
	public void setUserinfoNull(RsVipplanorder rsVipplanorder) {
		rsVipplanorder.setNikeName(rsVipplanorder.getBsUserinfo().getNickname());
		rsVipplanorder.setVipNo(rsVipplanorder.getBsUserinfo().getMobilePhone());
		rsVipplanorder.setBsUserinfo(null);
	}
	
	public void setUserinfoNull(List<RsVipplanorder> rvs) {
		for(RsVipplanorder rv :rvs)
			setUserinfoNull(rv);
	}
	
}
