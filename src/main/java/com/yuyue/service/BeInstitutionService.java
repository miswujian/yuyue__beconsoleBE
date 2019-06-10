package com.yuyue.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuyue.dao.BeInstitutionDAO;
import com.yuyue.pojo.BeInstitution;
import com.yuyue.pojo.BeUser;
import com.yuyue.pojo.User;
import com.yuyue.util.UpdateUtil;

@Service
public class BeInstitutionService {

	@Autowired
	private BeInstitutionDAO beInstitutionDAO;

	@Autowired
	private BeUserService beUserService;

	/*
	 * public List<BeInstitution> list(){ List<BeInstitution> bits =
	 * beInstitutionDAO.findByLever(1); getBeInstitutionByParent(bits);
	 * for(BeInstitution bis : bits)
	 * getBeInstitutionByParent(bis.getBeInstitutions()); return bits; }
	 */

	public List<BeInstitution> list() {
		List<BeInstitution> bits = beInstitutionDAO.findByLever(1);
		for(BeInstitution bit : bits)
			recursive(bit);
		return bits;
	}
	
	public int add(BeInstitution beInstitution) {
		try {
			if(beInstitution.getBeInstitution()!=null) {
				if(beInstitutionDAO.findOne(beInstitution.getBeInstitution().getId())==null) {
					return 0;
				}
				BeInstitution bi = beInstitutionDAO.findOne(beInstitution.getBeInstitution().getId());
				beInstitution.setLever(bi.getLever()+1);
			}else {
				beInstitution.setLever(1);
			}
			beInstitutionDAO.save(beInstitution);
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}
	
	public BeInstitution get(int caseId) {
		return beInstitutionDAO.findOne(caseId);
	}
	
	public int delete(int id) {
		try {
			BeInstitution bi = beInstitutionDAO.findOne(id);
			if(!beUserService.list(bi).isEmpty()||!beInstitutionDAO.findByBeInstitution(bi).isEmpty()) {
				return 0;
			}
			beInstitutionDAO.delete(bi);
			return 1;
		} catch (Exception e) {
			return 0;
		}	
	}
	
	public int update(BeInstitution beInstitution) {
		try {
			if(beInstitution.getId().equals(beInstitution.getBeInstitution().getId()))
				return 0;
			BeInstitution bi = beInstitutionDAO.findOne(beInstitution.getId());
			UpdateUtil.copyNullProperties(bi, beInstitution);
			if(beInstitution.getBeInstitution().getId()<=0) {
				beInstitution.setLever(1);
				beInstitution.setBeInstitution(null);
			}else {
				BeInstitution father = beInstitutionDAO.findOne(beInstitution.getBeInstitution().getId());
				beInstitution.setLever(father.getLever()+1);
			}
			beInstitutionDAO.saveAndFlush(beInstitution);
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}

	// 递归函数
	public void recursive(BeInstitution beInstitution) {
		List<BeInstitution> bits = beInstitutionDAO.findByBeInstitution(beInstitution);
		if (bits != null) {
			beInstitution.setBeInstitutions(bits);
			for (BeInstitution bit : bits) {
				recursive(bit);
			}
		}
		List<BeUser> bus = beUserService.list(beInstitution);
		if(bus!=null) {
			List<User> users = new ArrayList<>();
			for(BeUser bu : bus) {
				User user = new User();
				user.setUid(bu.getUid());
				user.setRole(bu.getBeRole().getName());
				user.setUserName(bu.getUserName());
				users.add(user);
			}
			beInstitution.setBeInstitution(null);
			beInstitution.setUsers(users);
		}
	}

	public List<BeInstitution> listinfo() {
		return beInstitutionDAO.findAll();
	}

	public void getBeInstitutionByParent(BeInstitution beInstitution) {
		List<BeInstitution> bits = beInstitutionDAO.findByBeInstitution(beInstitution);
		beInstitution.setBeInstitutions(bits);
	}

	public void getBeInstitutionByParent(List<BeInstitution> bits) {
		for (BeInstitution bis : bits)
			getBeInstitutionByParent(bis);
	}

}
