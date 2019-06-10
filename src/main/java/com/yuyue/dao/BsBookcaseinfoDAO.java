package com.yuyue.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.yuyue.pojo.BsBookcaseinfo;

public interface BsBookcaseinfoDAO extends JpaRepository<BsBookcaseinfo, Integer> {

	public List<BsBookcaseinfo> findByAllocation(int allocation);
	
	@Query("from BsBookcaseinfo t where t.beWarehouse in ?1 and "
			+ "(t.caseName like ?2 or t.caseCode like ?3 or t.beUser.userName like ?4 or t.caseAddress like ?5) and t.status in ?6")
	public Page<BsBookcaseinfo> 
	queryByWarehouseIdInAndCaseNameLikeOrCaseCodeLikeOrUserNameLikeOrCaseAddressLikeAndStatusIn
	(ArrayList<Integer> warehouseId, String caseName, String caseCode, String userName, String caseAddress, 
			ArrayList<Byte> status, Pageable pageable);
	
	@Query("from BsBookcaseinfo t where t.beWarehouse.warehouseId = ?1")
	public List<BsBookcaseinfo> queryByWarehouseId(int warehouseId);
	
}
