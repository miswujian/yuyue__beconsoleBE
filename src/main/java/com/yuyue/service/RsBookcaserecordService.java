package com.yuyue.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuyue.dao.RsBookcaserecordDAO;
import com.yuyue.pojo.BeUser;
import com.yuyue.pojo.BookCaseStatus;
import com.yuyue.pojo.BookCaseitem;
import com.yuyue.pojo.RsBookcaserecord;
import com.yuyue.pojo.RsBookcaserecorditem;
import com.yuyue.pojo.User;
import com.yuyue.util.Page4Navigator;
import com.yuyue.util.UpdateUtil;

import net.sf.json.JSONObject;

@Service
public class RsBookcaserecordService {

	@Autowired
	private RsBookcaserecordDAO rsBookcaserecordDAO;
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate = null;
	
	@Autowired
	private BeUserService beUserService;
	
	@Autowired
	private RsBookcaserecorditemService rsBookcaserecorditemService;
	
	public Page4Navigator<RsBookcaserecord> list(int start, int size, int navigatePages, byte type){
		Sort sort = new Sort(Sort.Direction.DESC,"bookcaseId");
		Pageable pageable = new PageRequest(start, size, sort);
		Page<RsBookcaserecord> pageFromJPA = rsBookcaserecordDAO.findByType(type, pageable);
		Page4Navigator<RsBookcaserecord> rbrs = new Page4Navigator<>(pageFromJPA, navigatePages);
		setNull(rbrs.getContent());
		return rbrs;
	}
	
	public RsBookcaserecord get(int bookcaseId) {
		RsBookcaserecord rbr = rsBookcaserecordDAO.findOne(bookcaseId);
		setNull(rbr);
		return rbr;
	}
	
	public int delete(int bookcaseId) throws Exception {
		try {
			rsBookcaserecordDAO.delete(bookcaseId);
			return 1;
		} catch (Exception e) {
			throw new Exception();
		}
	}
	
	public int add(RsBookcaserecord rsBookcaserecord, HttpSession session) throws Exception {
		try {
			String u = stringRedisTemplate.opsForValue().get(session.getId().toString());
			JSONObject json = JSONObject.fromObject(u);
			User user = (User)JSONObject.toBean(json);
			BeUser beUser = beUserService.getByUId(user.getUid());
			rsBookcaserecord.setBeUser1(beUser);
			Calendar c = Calendar.getInstance();
			if(rsBookcaserecord.getOrderNo()!=null&&rsBookcaserecord.getType()==(byte)1)
				rsBookcaserecord.setOrderNo("T" + c.get(Calendar.YEAR) + (c.get(Calendar.MONTH) + 1)
						+ c.get(Calendar.DAY_OF_MONTH) + (int) (100 * Math.random()));
			else if(rsBookcaserecord.getOrderNo()!=null&&rsBookcaserecord.getType()==(byte)2) 
				rsBookcaserecord.setOrderNo("H" + c.get(Calendar.YEAR) + (c.get(Calendar.MONTH) + 1)
						+ c.get(Calendar.DAY_OF_MONTH) + (int) (100 * Math.random()));
			rsBookcaserecord.setCreateTime(new Date());
			rsBookcaserecord.setStatus((byte)1);
			rsBookcaserecordDAO.save(rsBookcaserecord);
			return 1;
		} catch (Exception e) {
			throw new Exception();
		}
	}
	
	/**
	 * 从去发货开始
	 * @param bookCaseStatus
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public int changeStatus(BookCaseStatus bookCaseStatus, HttpSession session) throws Exception {
		try {
			RsBookcaserecord rbr = rsBookcaserecordDAO.findOne(bookCaseStatus.getBookcaseId());
			rbr.setStatus(bookCaseStatus.getStatus());
			if(bookCaseStatus.getStatus()==(byte)3) {
				rsBookcaserecordDAO.saveAndFlush(rbr);
			}else if(bookCaseStatus.getStatus()==(byte)4) {
				rbr.setOrdersTime(new Date());
				rsBookcaserecordDAO.saveAndFlush(rbr);
			}else if(bookCaseStatus.getStatus()==(byte)5) {
				if(bookCaseStatus.getItemStatus()!=null&&bookCaseStatus.getItemStatus().size()>0) {
					for(BookCaseitem bci : bookCaseStatus.getItemStatus()) {
						RsBookcaserecorditem rbri = rsBookcaserecorditemService.get(bci.getBookcaseitemId());
						rbri.setStatus(bci.getStatus());
						rsBookcaserecorditemService.add(rbri);
					}
				}
				rbr.setUppercaseTime(new Date());
				rsBookcaserecordDAO.saveAndFlush(rbr);
			}else if(bookCaseStatus.getStatus()==(byte)6) {
				rsBookcaserecordDAO.saveAndFlush(rbr);
			}else if(bookCaseStatus.getStatus()==(byte)7) {
				String u = stringRedisTemplate.opsForValue().get(session.getId().toString());
				JSONObject json = JSONObject.fromObject(u);
				User user = (User)JSONObject.toBean(json);
				BeUser beUser = beUserService.getByUId(user.getUid());
				rbr.setBeUser3(beUser);
				rbr.setReason(bookCaseStatus.getReason());
				rbr.setReviewTime(new Date());
				rsBookcaserecordDAO.saveAndFlush(rbr);
			}
			return 1;
		} catch (Exception e) {
			throw new Exception();
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int update(RsBookcaserecord rsBookcaserecord) throws Exception {
		try {
			if(rsBookcaserecord.getStatus() != (byte)1&&
					(rsBookcaserecord.getUser2()==null||rsBookcaserecord.getUser2().getUid()==null))
				throw new Exception();
			if(rsBookcaserecord.getStatus() == (byte)1) {
				RsBookcaserecord rbr = rsBookcaserecordDAO.findOne(rsBookcaserecord.getBookcaseId());
				UpdateUtil.copyNullProperties(rbr, rsBookcaserecord);
				rsBookcaserecordDAO.saveAndFlush(rsBookcaserecord);
			}else if(rsBookcaserecord.getStatus() == (byte)2&&rsBookcaserecord.getRsBookcaserecorditems()!=null
					&&rsBookcaserecord.getRsBookcaserecorditems().size()>0) {
				RsBookcaserecord rbr = rsBookcaserecordDAO.findOne(rsBookcaserecord.getBookcaseId());
				BeUser beUser2 = beUserService.getByUId(rsBookcaserecord.getUser2().getUid());
				rsBookcaserecord.setBeUser2(beUser2);
				UpdateUtil.copyNullProperties(rbr, rsBookcaserecord);
				rsBookcaserecordDAO.saveAndFlush(rsBookcaserecord);
				for(RsBookcaserecorditem rbri : rsBookcaserecord.getRsBookcaserecorditems()) {
					if(rbri.getIsDelete()==(byte)1&&rbri.getBookcaseitemId()!=null&&rbri.getBookcaseitemId()>0) {
						rsBookcaserecorditemService.delete(rbri.getBookcaseitemId());
					}else if(rbri.getBookcaseitemId()!=null&&rbri.getBookcaseitemId()>0) {
						rsBookcaserecorditemService.update(rbri);
					}else {
						rsBookcaserecorditemService.add(rbri);
					}
				}
			}else if(rsBookcaserecord.getStatus() == (byte)2) {
				RsBookcaserecord rbr = rsBookcaserecordDAO.findOne(rsBookcaserecord.getBookcaseId());
				BeUser beUser2 = beUserService.getByUId(rsBookcaserecord.getUser2().getUid());
				rsBookcaserecord.setBeUser2(beUser2);
				UpdateUtil.copyNullProperties(rbr, rsBookcaserecord);
				rsBookcaserecordDAO.saveAndFlush(rsBookcaserecord);
			}
			return 1;
		} catch (Exception e) {
			throw new Exception();
		}
	}
	
	public void setNull(RsBookcaserecord rbr) {
		if(rbr.getBeUser1() != null) {
			User user1 = new User();
			user1.setUid(rbr.getBeUser1().getUid());
			user1.setUserName(rbr.getBeUser1().getUserName());
			rbr.setUser1(user1);
		}
		if(rbr.getBeUser2() != null) {
			User user2 = new User();
			user2.setUid(rbr.getBeUser2().getUid());
			user2.setUserName(rbr.getBeUser2().getUserName());
			rbr.setUser2(user2);
		}
		if(rbr.getBeUser3()!=null) {
			User user3 = new User();
			user3.setUid(rbr.getBeUser3().getUid());
			user3.setUserName(rbr.getBeUser3().getUserName());
			rbr.setUser3(user3);
		}
		if(rbr.getRsBookcaserecorditems()!=null&&rbr.getRsBookcaserecorditems().size()>0) {
			for(RsBookcaserecorditem rbri : rbr.getRsBookcaserecorditems()) {
				if(rbri.getBsBookinstore()!=null&&rbri.getBsBookinstore().getBsBookinfo()!=null) {
					rbri.setBookName(rbri.getBsBookinstore().getBsBookinfo().getBookName());
					rbri.setRfid(rbri.getBsBookinstore().getRfid());
				}
				rbri.setBsBookinstore(null);
				rbri.getBeLocation().setBeCells(null);
				rbri.setRsBookcaserecord(null);
			}
		}
		rbr.setBeUser1(null);
		rbr.setBeUser2(null);
		rbr.setBeUser3(null);
	}
	
	public void setNull(List<RsBookcaserecord> rbrs) {
		for(RsBookcaserecord rbr : rbrs) {
			setNull(rbr);
		}
	}
	
}
