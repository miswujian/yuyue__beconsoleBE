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

import com.yuyue.dao.RsHisborrowrecordDAO;
import com.yuyue.pojo.RsHisborrowrecord;
import com.yuyue.util.Page4Navigator;
import com.yuyue.util.StringUtil;

@Service
public class RsHisborrowrecordService {

	@Autowired
	private RsHisborrowrecordDAO rsHisborrowrecordDAO;
	
	public Page4Navigator<RsHisborrowrecord> list(int start, int size, int navigatePages){
		Sort sort = new Sort(Sort.Direction.DESC,"borrowId");
		Pageable pageable = new PageRequest(start, size, sort);
		Page<RsHisborrowrecord> pageFromJPA = rsHisborrowrecordDAO.findAll(pageable);
		return new Page4Navigator<>(pageFromJPA, navigatePages);
	}
	
	public Page4Navigator<RsHisborrowrecord> list(int start, int size, int navigatePages, String status, Date starttime,
			Date endtime, String deliverType, String returnWay, String keyword1, String keyword2){
		Sort sort = new Sort(Sort.Direction.DESC,"borrowId");
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
			statuslist.add((byte) 1);
			statuslist.add((byte) 2);
		}
		ArrayList<Byte> deliverTypelist = new ArrayList<>();
		if(deliverType.equals(1+""))
			deliverTypelist.add((byte) 1);
		else if(deliverType.equals(2+""))
			deliverTypelist.add((byte) 2);
		else{
			deliverTypelist.add((byte) 1);
			deliverTypelist.add((byte) 2);
		}
		ArrayList<Byte> returnWaylist = new ArrayList<>();
		if(returnWay.equals(1+""))
			returnWaylist.add((byte)1);
		else if(returnWay.equals(2+""))
			returnWaylist.add((byte)2);
		else {
			returnWaylist.add((byte)1);
			returnWaylist.add((byte)2);
		}
		Page<RsHisborrowrecord> pageFromJPA = rsHisborrowrecordDAO.
				queryByStatusInAndstartTimeBetweenAndDeliverTypeInAndReturnWayInAndNikeNameLikeOrVipNoLikeAndBookNameLikeOrRfidLike
				(statuslist, starttime, endtime, deliverTypelist, returnWaylist, 
						"%"+keyword1+"%", "%"+keyword1+"%", "%"+keyword2+"%", "%"+keyword2+"%", pageable);
		return new Page4Navigator<>(pageFromJPA, navigatePages);
	}
	
	public int addHisborrowrecord(RsHisborrowrecord rsHisborrowrecord) {
		try {
			RsHisborrowrecord rhb = rsHisborrowrecordDAO.save(rsHisborrowrecord);
			return rhb.getBorrowId();
		} catch (Exception e) {
			return 0;
		}
		
	}
	
	public int updateHisborrowrecord(RsHisborrowrecord rsHisborrowrecord) {
		try {
			RsHisborrowrecord rhb = rsHisborrowrecordDAO.save(rsHisborrowrecord);
			return rhb.getBorrowId();
		} catch (Exception e) {
			return 0;
		}
		
	}
	
	public RsHisborrowrecord getByOrderNo(String orderNo) {
		return rsHisborrowrecordDAO.findByOrderNo(orderNo);
	}
	
	public RsHisborrowrecord getHisborrowrecord(int borrowId) {
		return rsHisborrowrecordDAO.findOne(borrowId);
	}
	
	public void setBookinfoNull(RsHisborrowrecord rsHisborrowrecord) {
		rsHisborrowrecord.setBookName(rsHisborrowrecord.getBsBookinfo().getBookName());
		rsHisborrowrecord.getBsBookinstore().setBsBookinfo(null);
	}
	
	public void setBookinfoNull(List<RsHisborrowrecord> rhbs) {
		for(RsHisborrowrecord rhb : rhbs) {
			setBookinfoNull(rhb);
		}
	}
	
	public void setUserinfoNull(RsHisborrowrecord rhb) {
		rhb.setNikeName(rhb.getBsUserinfo().getNickname());
		rhb.setVipNo(rhb.getBsUserinfo().getMobilePhone());
		rhb.setBsUserinfo(null);
	}
	
	public void setUserinfoNull(List<RsHisborrowrecord> rhbs) {
		for(RsHisborrowrecord rhb : rhbs)
			setUserinfoNull(rhb);
	}
	
	public void setBookinstoreNull(RsHisborrowrecord rhb) {
		rhb.setBsBookinstore(null);
	}
	
	public void setBookinstoreNull(List<RsHisborrowrecord> rhbs) {
		for(RsHisborrowrecord rhb : rhbs)
			setBookinstoreNull(rhb);
	}
	
	public void setBookinstoreBookNull(RsHisborrowrecord rhb) {
		rhb.getBsBookinstore().setBsBookinfo(null);
	}
	
	public void setBookinstoreBookNull(List<RsHisborrowrecord> rhbs) {
		for(RsHisborrowrecord rhb : rhbs)
			setBookinstoreBookNull(rhb);
	}
	
}
