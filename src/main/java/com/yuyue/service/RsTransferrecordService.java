package com.yuyue.service;

import java.util.ArrayList;
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

import com.yuyue.dao.RsTransferrecordDAO;
import com.yuyue.pojo.BeLocation;
import com.yuyue.pojo.BeUser;
import com.yuyue.pojo.Bookinstore;
import com.yuyue.pojo.BsBookinstore;
import com.yuyue.pojo.RsTransferrecord;
import com.yuyue.pojo.RsTransferrecorditem;
import com.yuyue.pojo.User;
import com.yuyue.util.Page4Navigator;
import com.yuyue.util.UpdateUtil;

import net.sf.json.JSONObject;

@Service
public class RsTransferrecordService {

	@Autowired
	private RsTransferrecordDAO rsTransferrecordDAO;

	@Autowired
	private BeUserService beUserService;

	@Autowired
	private BsBookinstoreService bsBookinstoreService;

	@Autowired
	private BeLocationService beLocationService;

	@Autowired
	private RsTransferrecorditemService rsTransferrecorditemService;

	@Autowired
	private StringRedisTemplate stringRedisTemplate = null;

	public RsTransferrecord get(int transferId) {
		RsTransferrecord rtr = rsTransferrecordDAO.findOne(transferId);
		setNull(rtr);
		return rtr;
	}

	public Page4Navigator<RsTransferrecord> list(int start, int size, int navigatePages) {
		Sort sort = new Sort(Sort.Direction.DESC, "transferId");
		Pageable pageable = new PageRequest(start, size, sort);
		Page<RsTransferrecord> pageFromJPA = rsTransferrecordDAO.findAll(pageable);
		Page4Navigator<RsTransferrecord> rtrs = new Page4Navigator<>(pageFromJPA, navigatePages);
		setNull(rtrs.getContent());
		return rtrs;
	}

	/**
	 * 更改状态 1-草稿 2-未审核 3-已审核
	 * 
	 * @param transferId
	 * @param status
	 * @return
	 */
	public int changeStatus(int transferId, byte status, HttpSession session) {
		try {
			RsTransferrecord rtr = rsTransferrecordDAO.findOne(transferId);
			rtr.setStatus(status);
			if (status == 3) {
				String u = stringRedisTemplate.opsForValue().get(session.getId().toString());
				JSONObject json = JSONObject.fromObject(u);
				User user = (User) JSONObject.toBean(json, User.class);
				BeUser beUser = beUserService.getByUId(user.getUid());
				rtr.setBeUser2(beUser);
				rtr.setReviewTime(new Date());
			}
			rsTransferrecordDAO.saveAndFlush(rtr);
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public int add(RsTransferrecord rtr, HttpSession session) throws Exception {
		try {
			String u = stringRedisTemplate.opsForValue().get(session.getId().toString());
			JSONObject json = JSONObject.fromObject(u);
			User user = (User) JSONObject.toBean(json, User.class);
			BeUser beUser = beUserService.getByUId(user.getUid());
			rtr.setBeUser1(beUser);
			rtr.setCreateTime(new Date());
			if (rtr.getStatus() < 1)
				rtr.setStatus((byte) 1);
			Calendar c = Calendar.getInstance();
			if (rtr.getOrderNo() == null)
				rtr.setOrderNo("R" + c.get(Calendar.YEAR) + (c.get(Calendar.MONTH) + 1) + c.get(Calendar.DAY_OF_MONTH)
						+ (int) (100 * Math.random()));
			rtr = rsTransferrecordDAO.save(rtr);
			if (rtr.getBookinstores() != null && rtr.getBookinstores().size() > 0) {
				for (Bookinstore bi : rtr.getBookinstores()) {
					BsBookinstore bbi = bsBookinstoreService.getBookinstore(bi.getBookId());
					BeLocation ibl = beLocationService.get(bi.getLocationId());
					BeLocation obl = beLocationService.get(bi.getLocationId2());
					RsTransferrecorditem rtri = new RsTransferrecorditem();
					rtri.setBeLocation1(ibl);
					rtri.setBeLocation2(obl);
					rtri.setBsBookinstore(bbi);
					rtri.setPrice(bi.getPrice());
					rtri.setRsTransferrecord(rtr);
					rsTransferrecorditemService.add(rtri);
				}
			}
			return rtr.getTransferId();
		} catch (Exception e) {
			throw new Exception();
		}
	}

	public int update(RsTransferrecord rtr) throws Exception {
		try {
			RsTransferrecord rsTransferrecord = rsTransferrecordDAO.findOne(rtr.getTransferId());
			UpdateUtil.copyNullProperties(rsTransferrecord, rtr);
			rtr = rsTransferrecordDAO.saveAndFlush(rtr);
			if(rtr.getBookinstores() != null && rtr.getBookinstores().size() > 0) {
				for (Bookinstore bi : rtr.getBookinstores()) {
					if(bi.getIsDelete()==1&&bi.getTransferitemId()!=null&&bi.getTransferitemId()>0) {
						rsTransferrecorditemService.delete(bi.getTransferitemId());
					}else if(bi.getTransferitemId()!=null&&bi.getTransferitemId()>0){
						BsBookinstore bbi = bsBookinstoreService.getBookinstore(bi.getBookId());
						BeLocation ibl = beLocationService.get(bi.getLocationId());
						BeLocation obl = beLocationService.get(bi.getLocationId2());
						RsTransferrecorditem rtri = new RsTransferrecorditem();
						rtri.setBeLocation1(ibl);
						rtri.setBeLocation2(obl);
						rtri.setBsBookinstore(bbi);
						rtri.setPrice(bi.getPrice());
						rtri.setTransferitemId(bi.getTransferitemId());
						rsTransferrecorditemService.update(rtri);
					}else {
						BsBookinstore bbi = bsBookinstoreService.getBookinstore(bi.getBookId());
						BeLocation ibl = beLocationService.get(bi.getLocationId());
						BeLocation obl = beLocationService.get(bi.getLocationId2());
						RsTransferrecorditem rtri = new RsTransferrecorditem();
						rtri.setBeLocation1(ibl);
						rtri.setBeLocation2(obl);
						rtri.setBsBookinstore(bbi);
						rtri.setPrice(bi.getPrice());
						rtri.setRsTransferrecord(rtr);
						rsTransferrecorditemService.add(rtri);
					}
				}
			}
			return 1;
		} catch (Exception e) {
			throw new Exception();
		}
	}

	public int delete(int transferId) {
		try {
			rsTransferrecordDAO.delete(transferId);
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * 处理给用户的数据
	 * 
	 * @param rtr
	 */
	private void setNull(RsTransferrecord rtr) {
		if (rtr.getBeUser1() != null) {
			User user1 = new User();
			user1.setUid(rtr.getBeUser1().getUid());
			user1.setUserName(rtr.getBeUser1().getUserName());
			rtr.setUser1(user1);
		}
		if (rtr.getBeUser2() != null) {
			User user2 = new User();
			user2.setUid(rtr.getBeUser2().getUid());
			user2.setUserName(rtr.getBeUser2().getUserName());
			rtr.setUser1(user2);
		}
		if (rtr.getRsTransferrecorditems() != null && rtr.getRsTransferrecorditems().size() > 0) {
			List<Bookinstore> bis = new ArrayList<>();
			for (RsTransferrecorditem rtri : rtr.getRsTransferrecorditems()) {
				Bookinstore bookinstore = new Bookinstore();
				bookinstore.setBookId(rtri.getBsBookinstore().getBookId());
				bookinstore.setRfid(rtri.getBsBookinstore().getRfid());
				bookinstore.setCode(rtri.getBsBookinstore().getCode());
				bookinstore.setIsbn(rtri.getBsBookinstore().getBsBookinfo().getIsbn());
				bookinstore.setPrice(rtri.getPrice());
				bookinstore.setLocationName(rtri.getBeLocation1().getLocationName());
				bookinstore.setLocationName2(rtri.getBeLocation2().getLocationName());
				bookinstore.setLocationId(rtri.getBeLocation1().getLocationId());
				bookinstore.setLocationId2(rtri.getBeLocation2().getLocationId());
				bookinstore.setTransferitemId(rtri.getTransferitemId());
				bis.add(bookinstore);
			}
			rtr.setBookinstores(bis);
		}
		if (rtr.getBeWarehouse1() != null) {
			rtr.getBeWarehouse1().setBeLocations(null);
			rtr.getBeWarehouse1().setBeDepartment(null);
		}
		if (rtr.getBeWarehouse2() != null) {
			rtr.getBeWarehouse2().setBeLocations(null);
			rtr.getBeWarehouse2().setBeDepartment(null);
		}
		rtr.setBeUser1(null);
		rtr.setBeUser2(null);
		rtr.setRsTransferrecorditems(null);
	}

	private void setNull(List<RsTransferrecord> rtrs) {
		for (RsTransferrecord rtr : rtrs) {
			setNull(rtr);
		}
	}

}
