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

import com.yuyue.dao.RsCurdonaterecordDAO;
import com.yuyue.pojo.RsCurdonaterecord;
import com.yuyue.util.Page4Navigator;
import com.yuyue.util.StringUtil;

@Service
public class RsCurdonaterecordService {

	@Autowired
	private RsCurdonaterecordDAO rsCurdonaterecordDAO;
	
	@Autowired
	private BsBookinfoService bsBookinfoService;
	
	public Page4Navigator<RsCurdonaterecord> list(int start, int size, int navigatePages){
		Sort sort = new Sort(Sort.Direction.DESC,"donateId");
		Pageable pageable = new PageRequest(start, size, sort);
		Page<RsCurdonaterecord> pageFromJPA = rsCurdonaterecordDAO.findAll(pageable);
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
	 * @param keyword1
	 * @param keyword2
	 * @return
	 */
	public Page4Navigator<RsCurdonaterecord> list(int start, int size, int navigatePages, String status, Date starttime,
			Date endtime, String keyword1, String keyword2){
		Sort sort = new Sort(Sort.Direction.DESC,"donateId");
		Pageable pageable = new PageRequest(start, size, sort);
		long time = System.currentTimeMillis();
		if (starttime == null)
			starttime = new Date(time / 10);
		if (endtime == null)
			endtime = new Date(time);
		ArrayList<Byte> statuslist = new ArrayList<>();
		//是否为数字判断
		if(!StringUtil.isNumeric(status))
			status = "";
		/**
		 * 默认为全部
		 */
		if(!status.equals(""))
			statuslist.add((byte)Integer.parseInt(status));
		else {
			statuslist.add((byte)-1);
			statuslist.add((byte)0);
			statuslist.add((byte)1);
			statuslist.add((byte)2);
			statuslist.add((byte)9);
		}
		//通过bookname来查询的 因为不存在外键 所以只能同isbn来查找书
		List<String> isbns = bsBookinfoService.getIsbnsByBookName(keyword2);
		Page<RsCurdonaterecord> pageFromJPA = rsCurdonaterecordDAO.
				queryByStatusInAndDonateTimeBetweenAndNikeNameLikeOrVipNoLikeAndBookNameLikeOrIsbnLike
				(statuslist, starttime, endtime, "%"+keyword1+"%", "%"+keyword1+"%", "%"+keyword2+"%", 
						(ArrayList<String>)isbns, pageable);
		return new Page4Navigator<>(pageFromJPA, navigatePages);
	}
	
	public int addCurdonaterecord(RsCurdonaterecord rsCurdonaterecord) {
		try {
			RsCurdonaterecord rcd = rsCurdonaterecordDAO.save(rsCurdonaterecord);
			return rcd.getDonateId();
		} catch (Exception e) {
			return 0;
		}
		
	}
	
	/**
	 * 还捐审单 捐书
	 * @param isbn
	 * @return
	 */
	public RsCurdonaterecord getByIsbn(String isbn) {
		return rsCurdonaterecordDAO.findByStatusIsAndIsbnIs(isbn);
	}
	
	public RsCurdonaterecord getByOrder(String orderNo) {
		return rsCurdonaterecordDAO.findByOrderNo(orderNo);
	}
	
	public int updateCurdonaterecord(RsCurdonaterecord rsCurdonaterecord) {
		try {
			RsCurdonaterecord rcd = rsCurdonaterecordDAO.save(rsCurdonaterecord);
			return rcd.getDonateId();
		} catch (Exception e) {
			return 0;
		}
		
	}
	
	public RsCurdonaterecord findRsCurdonaterecord(int donateId) {
		return rsCurdonaterecordDAO.findOne(donateId);
	}
	
	public void setUserinfoNull(RsCurdonaterecord rsCurdonaterecord) {
		rsCurdonaterecord.setNikeName(rsCurdonaterecord.getBsUserinfo().getNickname());
		rsCurdonaterecord.setVipNo(rsCurdonaterecord.getBsUserinfo().getMobilePhone());
		rsCurdonaterecord.setVipEnd(rsCurdonaterecord.getBsUserinfo().getVipEnd());
		rsCurdonaterecord.setBsUserinfo(null);
	}
	
	public void setUserinfoNull(List<RsCurdonaterecord> rcds) {
		for(RsCurdonaterecord rcd :rcds)
			setUserinfoNull(rcd);
	}
	
	public void setBookName(RsCurdonaterecord rcd) {
		rcd.setBookName(bsBookinfoService.getBookNameByIsbn(rcd.getIsbn()));
	}
	
	public void setBookName(List<RsCurdonaterecord> rcds) {
		for(RsCurdonaterecord rcd : rcds)
			setBookName(rcd);
	}
	
}
