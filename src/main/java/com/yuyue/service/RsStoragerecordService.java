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

import com.yuyue.dao.RsStoragerecordDAO;
import com.yuyue.pojo.BeLocation;
import com.yuyue.pojo.BeUser;
import com.yuyue.pojo.Bookinstore;
import com.yuyue.pojo.BsBookinfo;
import com.yuyue.pojo.BsBookinstore;
import com.yuyue.pojo.RsStoragerecord;
import com.yuyue.pojo.RsStoragerecorditem;
import com.yuyue.pojo.User;
import com.yuyue.util.Page4Navigator;
import com.yuyue.util.UpdateUtil;

import net.sf.json.JSONObject;

@Service
public class RsStoragerecordService {

	@Autowired
	private RsStoragerecordDAO rsStoragerecordDAO;

	@Autowired
	private BeUserService beUserService;

	@Autowired
	private BsBookinstoreService bsBookinstoreService;

	@Autowired
	private BeLocationService beLocationService;

	@Autowired
	private RsStoragerecorditemService rsStoragerecorditemService;

	@Autowired
	private BsBookinfoService bsBookinfoService;

	@Autowired
	private StringRedisTemplate stringRedisTemplate = null;

	public Page4Navigator<RsStoragerecord> list(int start, int size, int navigatePages, byte recordType) {
		Sort sort = new Sort(Sort.Direction.DESC, "storageId");
		Pageable pageable = new PageRequest(start, size, sort);
		Page<RsStoragerecord> pageFromJPA = rsStoragerecordDAO.findByRecordType(recordType, pageable);
		Page4Navigator<RsStoragerecord> rss = new Page4Navigator<>(pageFromJPA, navigatePages);
		setUserNull(rss.getContent());
		return rss;
	}

	public int changeStatus(int storageId, byte status, HttpSession session) {
		try {
			RsStoragerecord rsStoragerecord = rsStoragerecordDAO.findOne(storageId);
			rsStoragerecord.setStatus(status);
			if (status == 3) {
				String u = stringRedisTemplate.opsForValue().get(session.getId().toString());
				JSONObject json = JSONObject.fromObject(u);
				User user = (User) JSONObject.toBean(json, User.class);
				BeUser beUser = beUserService.getByUId(user.getUid());
				rsStoragerecord.setBeUser1(beUser);
				rsStoragerecord.setReviewTime(new Date());
			}
			rsStoragerecordDAO.saveAndFlush(rsStoragerecord);
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * 库单的增加 包括了库单项 和 商品的增加一起
	 * 
	 * @param rsStoragerecord
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@Transactional(rollbackFor = Exception.class)
	public int add(RsStoragerecord rsStoragerecord, HttpSession session) throws Exception {
		try {
			String u = stringRedisTemplate.opsForValue().get(session.getId().toString());
			JSONObject json = JSONObject.fromObject(u);
			User user = (User) JSONObject.toBean(json, User.class);
			BeUser beUser = beUserService.getByUId(user.getUid());
			rsStoragerecord.setBeUser2(beUser);
			rsStoragerecord.setCreateTime(new Date());
			if (rsStoragerecord.getStatus() < 1)
				rsStoragerecord.setStatus((byte) 1);
			Calendar c = Calendar.getInstance();
			if (rsStoragerecord.getOrderNo() == null)
				rsStoragerecord.setOrderNo("R" + c.get(Calendar.YEAR) + (c.get(Calendar.MONTH) + 1)
						+ c.get(Calendar.DAY_OF_MONTH) + (int) (100 * Math.random()));
			RsStoragerecord rsr = rsStoragerecordDAO.save(rsStoragerecord);
			if (rsStoragerecord.getBookinstores() != null && rsStoragerecord.getBookinstores().size() > 0) {
				for (Bookinstore bi : rsStoragerecord.getBookinstores()) {
					if (bi.getBookId() != null) {
						BsBookinstore bbi = bsBookinstoreService.getBookinstore(bi.getBookId());
						BeLocation bl = beLocationService.get(bi.getLocationId());
						RsStoragerecorditem rsri = new RsStoragerecorditem();
						rsri.setBeLocation(bl);
						rsri.setBsBookinstore(bbi);
						rsri.setRsStoragerecord(rsr);
						rsri.setPrice(bi.getPrice());
						rsStoragerecorditemService.add(rsri);
					} else {
						BsBookinstore bbi = new BsBookinstore();
						BeLocation bl = beLocationService.get(bi.getLocationId());
						BsBookinfo bb = bsBookinfoService.getByIsbn(bi.getIsbn());
						bbi.setBeLocation(bl);
						bbi.setBsBookinfo(bb);
						if (rsr.getType() == 2)
							bbi.setIsDonate((byte) 1);
						else
							bbi.setIsDonate((byte) 0);
						bbi.setStatus((byte) 0);
						if (bi.getCode() == null)
							bbi.setCode("R" + c.get(Calendar.YEAR) + (c.get(Calendar.MONTH) + 1)
									+ c.get(Calendar.DAY_OF_MONTH) + (int) (100 * Math.random()));
						else
							bbi.setCode(bi.getCode());
						bbi.setRfid(bi.getRfid());
						bbi = bsBookinstoreService.add(bbi);
						RsStoragerecorditem rsri = new RsStoragerecorditem();
						rsri.setBeLocation(bl);
						rsri.setBsBookinstore(bbi);
						rsri.setRsStoragerecord(rsr);
						rsri.setPrice(bi.getPrice());
						rsStoragerecorditemService.add(rsri);
					}
				}
			}
			return rsr.getStorageId();
		} catch (Exception e) {
			throw new Exception();
		}
	}

	/**
	 * 通过isDelete 来判断是否是删除
	 * @param rsStoragerecord
	 * @return
	 * @throws Exception 
	 */
	@Transactional(rollbackFor = Exception.class)
	public int update(RsStoragerecord rsStoragerecord) throws Exception {
		try {
			RsStoragerecord rs = rsStoragerecordDAO.findOne(rsStoragerecord.getStorageId());
			UpdateUtil.copyNullProperties(rs, rsStoragerecord);
			RsStoragerecord rsr = rsStoragerecordDAO.saveAndFlush(rsStoragerecord);
			if (rsStoragerecord.getBookinstores() != null && rsStoragerecord.getBookinstores().size() > 0) {
				for (Bookinstore bi : rsStoragerecord.getBookinstores()) {
					if (bi.getIsDelete()!=null&&bi.getIsDelete() == 1&&bi.getStorageitemId()!=null&&bi.getStorageitemId()>0) {
						rsStoragerecorditemService.delete(bi.getStorageitemId());
					} else if(bi.getStorageitemId()!=null&&bi.getStorageitemId()>0){
						RsStoragerecorditem rsri = new RsStoragerecorditem();
						BsBookinstore bbi = bsBookinstoreService.getBookinstore(bi.getBookId());
						if(bi.getLocationId()!=null) {
							BeLocation bl = beLocationService.get(bi.getLocationId());
							rsri.setBeLocation(bl);
						}
						rsri.setBsBookinstore(bbi);
						rsri.setPrice(bi.getPrice());
						rsri.setStorageitemId(bi.getStorageitemId());
						rsStoragerecorditemService.update(rsri);
					}else if (bi.getBookId() != null) {
						BsBookinstore bbi = bsBookinstoreService.getBookinstore(bi.getBookId());
						BeLocation bl = beLocationService.get(bi.getLocationId());
						RsStoragerecorditem rsri = new RsStoragerecorditem();
						rsri.setBeLocation(bl);
						rsri.setBsBookinstore(bbi);
						rsri.setRsStoragerecord(rsr);
						rsri.setPrice(bi.getPrice());
						rsStoragerecorditemService.add(rsri);
					} else {
						BsBookinstore bbi = new BsBookinstore();
						BeLocation bl = beLocationService.get(bi.getLocationId());
						BsBookinfo bb = bsBookinfoService.getByIsbn(bi.getIsbn());
						bbi.setBeLocation(bl);
						bbi.setBsBookinfo(bb);
						if (rsStoragerecord.getType() == 2)
							bbi.setIsDonate((byte) 1);
						else
							bbi.setIsDonate((byte) 0);
						bbi.setStatus((byte) 0);
						Calendar c = Calendar.getInstance();
						if (bi.getCode() == null)
							bbi.setCode("R" + c.get(Calendar.YEAR) + (c.get(Calendar.MONTH) + 1)
									+ c.get(Calendar.DAY_OF_MONTH) + (int) (100 * Math.random()));
						else
							bbi.setCode(bi.getCode());
						bbi.setRfid(bi.getRfid());
						bbi = bsBookinstoreService.add(bbi);
						RsStoragerecorditem rsri = new RsStoragerecorditem();
						rsri.setBeLocation(bl);
						rsri.setBsBookinstore(bbi);
						rsri.setRsStoragerecord(rsr);
						rsri.setPrice(bi.getPrice());
						rsStoragerecorditemService.add(rsri);
					}
				}
			}
			return 1;
		} catch (Exception e) {
			throw new Exception();
		}
	}

	public int delete(int storageId) {
		try {
			rsStoragerecordDAO.delete(storageId);
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}

	public RsStoragerecord get(int storageId) {
		RsStoragerecord rs = rsStoragerecordDAO.findOne(storageId);
		setUserNull(rs);
		return rs;
	}

	public void setUserNull(RsStoragerecord rsStoragerecord) {
		if (rsStoragerecord.getBeUser1() != null) {
			User user1 = new User();
			user1.setUid(rsStoragerecord.getBeUser1().getUid());
			user1.setUserName(rsStoragerecord.getBeUser1().getUserName());
			rsStoragerecord.setUser1(user1);
		}
		if (rsStoragerecord.getBeUser2() != null) {
			User user2 = new User();
			user2.setUid(rsStoragerecord.getBeUser2().getUid());
			user2.setUserName(rsStoragerecord.getBeUser2().getUserName());
			rsStoragerecord.setUser2(user2);
		}
		if (rsStoragerecord.getRsStoragerecorditems() != null && rsStoragerecord.getRsStoragerecorditems().size() > 0) {
			List<Bookinstore> bis = new ArrayList<>();
			for (RsStoragerecorditem rsri : rsStoragerecord.getRsStoragerecorditems()) {
				Bookinstore bookinstore = new Bookinstore();
				bookinstore.setBookId(rsri.getBsBookinstore().getBookId());
				bookinstore.setRfid(rsri.getBsBookinstore().getRfid());
				bookinstore.setCode(rsri.getBsBookinstore().getCode());
				bookinstore.setIsbn(rsri.getBsBookinstore().getBsBookinfo().getIsbn());
				bookinstore.setPrice(rsri.getPrice());
				bookinstore.setLocationName(rsri.getBeLocation().getLocationName());
				bookinstore.setLocationId(rsri.getBeLocation().getLocationId());
				bookinstore.setStorageitemId(rsri.getStorageitemId());
				bis.add(bookinstore);
			}
			rsStoragerecord.setBookinstores(bis);
		}
		if (rsStoragerecord.getBeWarehouse() != null) {
			rsStoragerecord.getBeWarehouse().setBeLocations(null);
			rsStoragerecord.getBeWarehouse().setBeDepartment(null);
		}
		rsStoragerecord.setBeUser1(null);
		rsStoragerecord.setBeUser2(null);
		rsStoragerecord.setRsStoragerecorditems(null);
	}

	public void setUserNull(List<RsStoragerecord> rss) {
		for (RsStoragerecord rsStoragerecord : rss)
			setUserNull(rsStoragerecord);
	}

}
