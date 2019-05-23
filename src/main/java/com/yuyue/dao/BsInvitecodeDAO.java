package com.yuyue.dao;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.yuyue.pojo.BsInvitecode;

public interface BsInvitecodeDAO extends JpaRepository<BsInvitecode, Integer> {

	@Query("from BsInvitecode t where t.status in ?1 and t.createTime between ?2 and ?3 and (t.custName like ?4 or t.code like ?5)")
	public Page<BsInvitecode> queryByStatusInAndCreateTimeBetweenAndCustNameLikeOrCodeLike
	(ArrayList<Byte> status, Date starttime, Date endtime, String custName, String code, Pageable pageable);
	
}
