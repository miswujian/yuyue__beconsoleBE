package com.yuyue.service;

import java.util.ArrayList;
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

import com.yuyue.dao.BeUserDAO;
import com.yuyue.dao.RsRolepermissionDAO;
import com.yuyue.pojo.BeInstitution;
import com.yuyue.pojo.BeRole;
import com.yuyue.pojo.BeUser;
import com.yuyue.pojo.BeWarehouse;
import com.yuyue.pojo.BsBookcaseinfo;
import com.yuyue.pojo.Case;
import com.yuyue.pojo.RsRolepermission;
import com.yuyue.pojo.RsUserwarehouse;
import com.yuyue.pojo.User;
import com.yuyue.pojo.Warehouse;
import com.yuyue.util.Md5Util;
import com.yuyue.util.Page4Navigator;
import com.yuyue.util.UpdateUtil;

import net.sf.json.JSONObject;

@Service
public class BeUserService {

	@Autowired
	private BeUserDAO beUserDAO;
	
	@Autowired
	private RsRolepermissionDAO rsRolepermissionDAO;
	
	@Autowired
	private BeRoleService beRoleService;
	
	@Autowired
	private BeWarehouseService beWarehouseService;
	
	@Autowired
	private RsUserwarehouseService rsUserwarehouseService;
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate = null;
	
	public List<BeUser> list(BeInstitution bit){
		return beUserDAO.findByBeInstitution(bit);
	}
	
	public List<User> listUser(){
		List<BeUser> bus = beUserDAO.findAll();
		List<User> us = new ArrayList<>();
		for(BeUser bu : bus) {
			User user = new User();
			user.setUid(bu.getUid());
			user.setUserName(bu.getUserName());
			us.add(user);
		}
		return us;
	}
	
	public int changePassword(BeUser beUser) {
		try {
			BeUser bu = beUserDAO.save(beUser);
			return bu.getUid();
		} catch (Exception e) {
			return 0;
		}
	}
	
	/**
	 * 存在事务的情况下仅查询使用 如果用于更新或者增加的话 会产生级联更新 导致 BeInstitution的BeInstitution变空 
	 * 因为你结构的变化 在事务结束后会自动提交 导致更新
	 * @param uid
	 * @return
	 */
	public BeUser getById(int uid) {
		BeUser bu = beUserDAO.findOne(uid);
		bu.getBeInstitution().setBeInstitution(null);
		setRole(bu);
		setPermission(bu);
		bu.setPassword(null);
		return bu;
	}
	
	public BeUser getByUId(int uid) {
		BeUser bu = beUserDAO.findOne(uid);
		return bu;
	}
	
	/**
	 * 获取一个用户拥有的所有柜子
	 * @param uid
	 * @return
	 */
	public List<Warehouse> get(int uid){
		BeUser bu = beUserDAO.findOne(uid);
		List<RsUserwarehouse> ruws = bu.getRsUserwarehouses();
		List<BeWarehouse> warehouseIds = new ArrayList<>();
		for(RsUserwarehouse ruw : ruws) {
			if(!isRepeat(warehouseIds, ruw.getBeWarehouse().getWarehouseId()))
				warehouseIds.add(ruw.getBeWarehouse());
		}
		List<Warehouse> warehouse = new ArrayList<>();
		for(BeWarehouse bw : warehouseIds) {
			Warehouse w = new Warehouse();
			w.setWarehouseId(bw.getWarehouseId());
			w.setWarehouseName(bw.getWarehouseName());
			List<BsBookcaseinfo> bbcs = rsUserwarehouseService.getBookcaseinfo(bu, bw);
			List<Case> cases = new ArrayList<>();
			for(BsBookcaseinfo bbc : bbcs) {
				Case c = new Case();
				c.setCaseId(bbc.getCaseId());
				c.setCaseName(bbc.getCaseName()+bbc.getCaseId());
				cases.add(c);
			}
			w.setCases(cases);
			warehouse.add(w);
		}
		return warehouse;
	}
	
	/**
	 * 获取所有仓库的所有柜子
	 * @return
	 */
	public List<Warehouse> get(){
		List<BeWarehouse> bws = beWarehouseService.list();
		List<Warehouse> warehouse = new ArrayList<>();
		for(BeWarehouse bw : bws) {
			Warehouse w = new Warehouse();
			w.setWarehouseId(bw.getWarehouseId());
			w.setWarehouseName(bw.getWarehouseName());
			if(bw.getBsBookcaseinfos()!=null) {
				List<Case> cases = new ArrayList<>();
				for(BsBookcaseinfo bbc : bw.getBsBookcaseinfos()) {
					Case c = new Case();
					c.setCaseId(bbc.getCaseId());
					c.setCaseName(bbc.getCaseName()+bbc.getCaseId());
					cases.add(c);
				}
				w.setCases(cases);
			}
			warehouse.add(w);
		}
		return warehouse;
	}
	
	/**
	 * 用户拥有的仓库
	 * @param session
	 * @return
	 */
	public List<Warehouse> getUserWarehouse(HttpSession session){
		String u = stringRedisTemplate.opsForValue().get(session.getId().toString());
		JSONObject json = JSONObject.fromObject(u);
        User user = (User) JSONObject.toBean(json,User.class);
        BeUser bu = beUserDAO.findOne(user.getUid());
        List<RsUserwarehouse> ruws = bu.getRsUserwarehouses();
        List<Warehouse> warehouses = new ArrayList<>();
        for(RsUserwarehouse ruw : ruws) {
        	if(ruw.getType()==(byte)1&&!isRepeats(warehouses, ruw.getBeWarehouse().getWarehouseId())){
        		Warehouse warehouse = new Warehouse();
        		warehouse.setWarehouseId(ruw.getBeWarehouse().getWarehouseId());
        		warehouse.setWarehouseName(ruw.getBeWarehouse().getWarehouseName());
        		warehouses.add(warehouse);
        	}
        }
        return warehouses;
	}
	
	/**
	 * 获取一个用户拥有哪些柜子的id
	 * @param uid
	 * @return
	 */
	public List<Integer> getArray(int uid){
		BeUser bu = beUserDAO.findOne(uid);
		List<RsUserwarehouse> ruws = bu.getRsUserwarehouses();
		List<BeWarehouse> warehouseIds = new ArrayList<>();
		List<Integer> caseIds = new ArrayList<>();
		for(RsUserwarehouse ruw : ruws) {
			if(ruw.getType()==(byte)2&&!isRepeat(warehouseIds, ruw.getBeWarehouse().getWarehouseId()))
				warehouseIds.add(ruw.getBeWarehouse());
			else if(ruw.getType()==(byte)1){
				for(BsBookcaseinfo bbc : ruw.getBeWarehouse().getBsBookcaseinfos()) {
					caseIds.add(bbc.getCaseId());
				}
			}
		}
		for(BeWarehouse bw : warehouseIds) {
			List<BsBookcaseinfo> bbcs = rsUserwarehouseService.getBookcaseinfo(bu, bw);
			for(BsBookcaseinfo bbc : bbcs) {
				caseIds.add(bbc.getCaseId());
			}
		}
		return caseIds;
	}
	
	/**
	 * 判断重复的
	 * @param ids
	 * @param a
	 * @return
	 */
	public boolean isRepeat(List<BeWarehouse> ids, Integer a) {
		for(BeWarehouse id : ids) {
			if(id.getWarehouseId().equals(a))
				return true;
		}
		return false;
	}
	
	public boolean isRepeats(List<Warehouse> ids, Integer a) {
		for(Warehouse id : ids) {
			if(id.getWarehouseId().equals(a))
				return true;
		}
		return false;
	}
	
	public int add(BeUser beUser) {
		try {
			BeRole beRole = beRoleService.getById(beUser.getRoleType());
			beUser.setBeRole(beRole);
			System.err.println(beUser.getBeRole().getId());
			beUser.setPassword(Md5Util.md5(beUser.getPassword()));
			beUser.setRegistrationtime(new Date());
			beUserDAO.save(beUser);
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}
	
	public BeUser login(String name, String password) {
		BeUser bu = beUserDAO.getByUserNameAndPassword(name, password);
		if(bu == null)
			return null;
		bu.getBeInstitution().setBeInstitution(null);
		setRole(bu);
		setPermission(bu);
		return bu;
	}
	
	public int update(BeUser beUser) {
		try {
			if(beUser.getPassword()!=null)
				beUser.setPassword(Md5Util.md5(beUser.getPassword()));
			BeUser bu = beUserDAO.findOne(beUser.getUid());
			if(beUser.getRoleType()!=null) {
				BeRole beRole = beRoleService.getById(beUser.getRoleType());
				beUser.setBeRole(beRole);
			}
			UpdateUtil.copyNullProperties(bu, beUser);
			beUserDAO.saveAndFlush(beUser);
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}
	
	public int delete(int uid) {
		try {
			beUserDAO.delete(uid);
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}
	
	public List<BeUser> list(){
		List<BeUser> bus = beUserDAO.findAll();
		setRole(bus);
		setPermission(bus);
		setPasswordAsNull(bus);
		return bus;
	}
	
	public Page4Navigator<BeUser> list(int start, int size, int navigatePages){
		Sort sort = new Sort(Sort.Direction.DESC,"uid");
		Pageable pageable = new PageRequest(start, size, sort);
		Page<BeUser> pageFromJPA = beUserDAO.findAll(pageable);
		Page4Navigator<BeUser> bus = new Page4Navigator<>(pageFromJPA, navigatePages);
		setRole(bus.getContent());
		setPermission(bus.getContent());
		setPasswordAsNull(bus.getContent());
		return bus;
	}
	
	public void setPasswordAsNull(List<BeUser> bus) {
		for(BeUser bu : bus)
			setPasswordAsNull(bu);
	}
	
	public void setPasswordAsNull(BeUser be) {
		be.setPassword(null);
	}
	
	public BeUser getUserPermission(int id) {
		BeUser bu = beUserDAO.getOne(id);
		setRole(bu);
		setPermission(bu);
		return bu;
	}
	
	public void setRole(BeUser bu) {
		bu.setRole(bu.getBeRole().getName());
		bu.setRoleType(bu.getBeRole().getId());
	}
	
	public void setRole(List<BeUser> bus) {
		for(BeUser bu : bus)
			setRole(bu);
	}
	
	public void setPermission(BeUser bu) {
		List<RsRolepermission> rrps = rsRolepermissionDAO.findByBeRole(bu.getBeRole());
		List<String> permissions = new ArrayList<>();
		for(RsRolepermission rrp : rrps) {
			permissions.add(rrp.getBePermission().getName());
		}
		bu.setPermissions(permissions);
	}
	
	public void setPermission(List<BeUser> bus) {
		for(BeUser bu : bus)
			setPermission(bu);
	}
	
}
