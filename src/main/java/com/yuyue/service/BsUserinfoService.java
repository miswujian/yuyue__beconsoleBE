package com.yuyue.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.yuyue.dao.BsUserinfoDAO;
import com.yuyue.pojo.BsIvtuserinfo;
import com.yuyue.pojo.BsUserinfo;
import com.yuyue.pojo.RsCurborrowrecord;
import com.yuyue.pojo.RsUsercredit;
import com.yuyue.util.Page4Navigator;
import com.yuyue.util.StringUtil;

@Service
public class BsUserinfoService {

	@Autowired
	private BsUserinfoDAO bsUserinfoDAO;
	
	@Autowired
	private BsIvtuserinfoService bsIvtuserinfoService;
	
	@Autowired
	private RsUsercreditService rsUsercreditService;
	
	/**
	 * 如果family_id为null的话就是个人 否则为家庭
	 * @return
	 */
	public Page4Navigator<BsUserinfo> list(int start, int size, int navigatePages){
		Sort sort = new Sort(Sort.Direction.DESC,"userId");
		Pageable pageable = new PageRequest(start, size, sort);
		Page<BsUserinfo> pageFromJPA = bsUserinfoDAO.findAll(pageable);
		Page4Navigator<BsUserinfo> bus = new Page4Navigator<>(pageFromJPA, navigatePages);
		setIvtuserinfo(bus.getContent());
		return bus;
	}
	
	public int changeCredit(int userId, String type, String credit, int uid) {
		if(!StringUtil.isNumeric(credit))
			return 0;
		BsUserinfo bu = getUserinfo(userId);
		if(bu == null)
			return 0;
		if(!StringUtil.isNumeric(type))
			return 0;
		int creditValue = Integer.parseInt(credit);
		int point = bu.getPoint();
		int freezePoint = bu.getFreezePoint();
		if(type.equals("0")) {
			point += Integer.parseInt(credit);
			bu.setPoint(point);
		}else if(type.equals("1")){
			if(point < Integer.parseInt(credit))
				return 0;
			else
				point -= Integer.parseInt(credit);
			bu.setPoint(point);
		}else if(type.equals("2")) {
			if(point < freezePoint+Integer.parseInt(credit))
				return 0;
			else
				freezePoint += Integer.parseInt(credit);
			bu.setFreezePoint(freezePoint);
		}
		RsUsercredit ru = new RsUsercredit();
		ru.setBsUserinfo(bu);
		ru.setType((byte)Integer.parseInt(type));
		ru.setCreditValue(creditValue);
		ru.setCreateTime(new Date());
		ru.setOperatorId(uid);
		ru.setOperateTime(new Date());
		bsUserinfoDAO.save(bu);
		rsUsercreditService.add(ru);
		return 1;
	}
	
	public BsUserinfo getUserinfoByVipNo(String vipNo) {
		return bsUserinfoDAO.findByMobilePhone(vipNo);
	}
	
	public BsUserinfo getUserinfo(int userId) {
		return bsUserinfoDAO.findOne(userId);
	}
	
	public void setUserinfoNull(RsCurborrowrecord rsCurborrowrecord) {
		rsCurborrowrecord.setBsUserinfo(null);
	}
	
	public void setIvtuserinfo(BsUserinfo bsUserinfo) {
		BsIvtuserinfo bsIvtuserinfo = bsIvtuserinfoService.getBsIvtuserinfo(bsUserinfo.getUserId());
		if(bsIvtuserinfo != null) {
			bsUserinfo.setBsIvtuserinfo(bsIvtuserinfo);
			bsUserinfo.setCustName(bsUserinfo.getBsIvtuserinfo().getBsInvitecode().getCustName());
		}
	}
	
	public void setIvtuserinfo(List<BsUserinfo> bus) {
		for(BsUserinfo bu : bus)
			setIvtuserinfo(bu);
	}
	
	public void setUserinfoNull(List<RsCurborrowrecord> rcbs) {
		for(RsCurborrowrecord rcb : rcbs) {
			setUserinfoNull(rcb);
		}
	}
	
}
