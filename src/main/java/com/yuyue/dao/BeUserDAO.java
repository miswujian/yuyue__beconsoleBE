package com.yuyue.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yuyue.pojo.BeInstitution;
import com.yuyue.pojo.BeUser;

public interface BeUserDAO extends JpaRepository<BeUser, Integer> {

	BeUser getByUserNameAndPassword(String userName, String password);
	
	List<BeUser> findByBeInstitution(BeInstitution beInstitution);
	
}
