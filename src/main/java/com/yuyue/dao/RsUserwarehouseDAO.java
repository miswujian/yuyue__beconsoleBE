package com.yuyue.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.yuyue.pojo.BeUser;
import com.yuyue.pojo.BeWarehouse;
import com.yuyue.pojo.BsBookcaseinfo;
import com.yuyue.pojo.RsUserwarehouse;

public interface RsUserwarehouseDAO extends JpaRepository<RsUserwarehouse, Integer> {

	List<RsUserwarehouse> findByBeUserAndBeWarehouseAndType(BeUser beUser, BeWarehouse beWarehouse, byte type);
	
	RsUserwarehouse findByBeUserAndBsBookcaseinfo(BeUser beUser, BsBookcaseinfo bsBookcaseinfo);
	
	@Transactional
    @Modifying
    @Query(value = "delete t from rs_userwarehouse t where t.uid = ?1 and t.case_id = ?2",nativeQuery = true)
	void deleteByUidAndCaseId(int uid, int caseId);
	
}
