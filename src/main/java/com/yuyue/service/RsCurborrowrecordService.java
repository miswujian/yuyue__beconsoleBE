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

import com.yuyue.dao.RsCurborrowrecordDAO;
import com.yuyue.pojo.BsBookinstore;
import com.yuyue.pojo.RsCurborrowrecord;
import com.yuyue.util.Page4Navigator;
import com.yuyue.util.StringUtil;

@Service
public class RsCurborrowrecordService {

	@Autowired
	private RsCurborrowrecordDAO rsCurborrowrecordDAO;
	
	@Autowired
	private RsUserfineService rsUserfineService;
	
	public RsCurborrowrecord getRsCurborrowrecord(BsBookinstore bsBookinstore) {
		Sort sort = new Sort(Sort.Direction.DESC,"createTime");
		List<RsCurborrowrecord> rcbs = rsCurborrowrecordDAO.findByBsBookinstore(bsBookinstore, sort);
		if(rcbs==null||rcbs.isEmpty())
			return null;
		return rcbs.get(0);
	}

	public Page4Navigator<RsCurborrowrecord> list(int start, int size, int navigatePages) {
		Sort sort = new Sort(Sort.Direction.DESC, "borrowId");
		Pageable pageable = new PageRequest(start, size, sort);
		Page<RsCurborrowrecord> pageFromJPA = rsCurborrowrecordDAO.findAll(pageable);
		return new Page4Navigator<>(pageFromJPA, navigatePages);
	}

	/**
	 * 高级查询
	 * @param start
	 * @param size
	 * @param navigatePages
	 * @param status
	 * @param starttime
	 * @param endtime
	 * @param deliverType
	 * @param expressNo
	 * @param keyword1
	 * @param keyword2
	 * @return
	 */
	public Page4Navigator<RsCurborrowrecord> list(int start, int size, int navigatePages, String stage, Date starttime,
			Date endtime, String deliverType, String expressNo, String keyword1, String keyword2) {
		Sort sort = new Sort(Sort.Direction.DESC, "borrowId");
		Pageable pageable = new PageRequest(start, size, sort);
		long time = System.currentTimeMillis();
		if (starttime == null)
			starttime = new Date(time / 10);
		if (endtime == null)
			endtime = new Date(time);
		Page<RsCurborrowrecord> pageFromJPA;
		//为后面的in做准备
		ArrayList<Byte> statuslist = new ArrayList<>();
		if(!StringUtil.isNumeric(stage))
			stage = "";
		if(!stage.equals(""))
			statuslist.add((byte)Integer.parseInt(stage));
		else{
			statuslist.add((byte) 0);
			statuslist.add((byte) 1);
			statuslist.add((byte) 2);
			statuslist.add((byte) 3);
			statuslist.add((byte) 4);
			statuslist.add((byte) 5);
			statuslist.add((byte) 6);
			statuslist.add((byte) 7);
			statuslist.add((byte) 8);
			statuslist.add((byte) 9);
			statuslist.add((byte) 10);
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
		//订单号是否为零
		if (expressNo != null)
			pageFromJPA = rsCurborrowrecordDAO
					.queryByStageInAndStartTimeBetweenAndDeliverTypeInAndExpressNoLikeAndNikeNameLikeOrVipNoLikeAndBookNameLikeOrRfidLike(
							statuslist, starttime, endtime, deliverTypelist, "%" + expressNo + "%",
							"%" + keyword1 + "%","%" + keyword1 + "%", "%" + keyword2 + "%", "%" + keyword2 + "%",
							pageable);
		else
			pageFromJPA = rsCurborrowrecordDAO
					.queryByStageInAndStartTimeBetweenAndDeliverTypeInAndNikeNameLikeOrVipNoLikeAndBookNameLikeOrRfidLike(
							statuslist, starttime, endtime, deliverTypelist,
							"%" + keyword1 + "%","%" + keyword1 + "%", "%" + keyword2 + "%", "%" + keyword2 + "%",
							pageable);
		return new Page4Navigator<>(pageFromJPA, navigatePages);
	}
	
	/**
	 * 还捐审单 还书
	 * @param rfid
	 * @return
	 */
	public RsCurborrowrecord getByRfid(String rfid) {
		return rsCurborrowrecordDAO.findByStageIsAndRfidIs(rfid);
	}
	
	/**
	 * 待支付(0), 待发货(1),待出库(2), 配送中(3),待收书/已发货(4) 
	 * 扫码直接跳5,待归还(5),审核中(6),审核通过(7),审核未通过(8),逾期欠费(9),已取消(10)
	 * @param borrowId
	 * @param stage 1-发快递/发书柜 2-接单 3-上柜 4-审核通过 5-审核不通过 6-关闭订单
	 * @return -1-订单不存在 0-更新状态失败 1-更新成功
	 */
	public int updateByStage(int borrowId, int stage){
		RsCurborrowrecord rcb = getCurborrowrecord(borrowId);
		if(rcb == null)
			return -1;
		byte b = 0;
		if(stage == 1)
			b = (byte)2;
		else if(stage == 2)
			b = (byte)3;
		else if(stage == 3)
			b = (byte)4;
		else if(stage == 4)
			b = (byte)7;
		else if(stage == 5)
			b = (byte)8;
		else if(stage == 6) {
			b = (byte)10;
		}
		rcb.setStage(b);
		try {
			rsCurborrowrecordDAO.saveAndFlush(rcb);
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}

	public RsCurborrowrecord getByOrderNo(String orderNo) {
		return rsCurborrowrecordDAO.findByOrderNo(orderNo);
	}

	public int addCurborrowrecord(RsCurborrowrecord rsCurborrowrecord) {
		try {
			RsCurborrowrecord rcb = rsCurborrowrecordDAO.save(rsCurborrowrecord);
			return rcb.getBorrowId();
		} catch (Exception e) {
			return 0;
		}
		
		
	}

	public int updateCurborrowrecord(RsCurborrowrecord rsCurborrowrecord) {
		try {
			RsCurborrowrecord rcb = rsCurborrowrecordDAO.save(rsCurborrowrecord);
			return rcb.getBorrowId();
		} catch (Exception e) {
			return 0;
		}
		
	}

	public RsCurborrowrecord getCurborrowrecord(int borrowId) {
		return rsCurborrowrecordDAO.findOne(borrowId);
	}

	public void setBookinfoNull(RsCurborrowrecord rsCurborrowrecord) {
		rsCurborrowrecord.setBookName(rsCurborrowrecord.getBsBookinfo().getBookName());
		rsCurborrowrecord.setBsBookinfo(null);
	}

	public void setBookinfoNull(List<RsCurborrowrecord> rcbs) {
		for (RsCurborrowrecord rcb : rcbs) {
			setBookinfoNull(rcb);
		}
	}
	
	public void setUserfine(RsCurborrowrecord rcb) {
		rcb.setRsUserfine(rsUserfineService.getByOrderNo(rcb.getOrderNo()));
	}

	public void setUserinfoNull(RsCurborrowrecord rsCurborrowrecord) {
		rsCurborrowrecord.setNikeName(rsCurborrowrecord.getBsUserinfo().getNickname());
		rsCurborrowrecord.setVipNo(rsCurborrowrecord.getBsUserinfo().getMobilePhone());
		rsCurborrowrecord.setVipEnd(rsCurborrowrecord.getBsUserinfo().getVipEnd());
		rsCurborrowrecord.setBsUserinfo(null);
	}

	public void setUserinfoNull(List<RsCurborrowrecord> rcbs) {
		for (RsCurborrowrecord rcb : rcbs)
			setUserinfoNull(rcb);
	}
	
	public void setBookinstoreNull(RsCurborrowrecord rsCurborrowrecord) {
		rsCurborrowrecord.setBsBookinstore(null);
	}
	
	public void setBookinstoreNull(List<RsCurborrowrecord> rcbs) {
		for(RsCurborrowrecord rcb : rcbs)
			setBookinstoreNull(rcb);
	}
	
	public void setBookinstoreBookNull(RsCurborrowrecord rsCurborrowrecord) {
		rsCurborrowrecord.getBsBookinstore().setBsBookinfo(null);
	}
	
	public void setBookinstoreBookNull(List<RsCurborrowrecord> rcbs) {
		for(RsCurborrowrecord rcb : rcbs)
			setBookinstoreBookNull(rcb);
	}
	
	public void setGenesNull(RsCurborrowrecord rcb) {
		rcb.getBsBookinfo().setBsGenes(null);
	}
	
	public void setGenesNull(List<RsCurborrowrecord> rcbs) {
		for(RsCurborrowrecord rcb : rcbs)
			setGenesNull(rcb);
	}

}
