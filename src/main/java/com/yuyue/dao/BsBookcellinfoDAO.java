package com.yuyue.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.yuyue.pojo.BsBookcaseinfo;
import com.yuyue.pojo.BsBookcellinfo;

public interface BsBookcellinfoDAO extends JpaRepository<BsBookcellinfo, Integer> {

	List<BsBookcellinfo> findByRepair(Integer repair);
	
	Page<BsBookcellinfo> findByBsBookcaseinfo(BsBookcaseinfo bsBookcaseinfo, Pageable pageable);
	
	List<BsBookcellinfo> findByBsBookcaseinfo(BsBookcaseinfo bsBookcaseinfo);
	
	@Query("from BsBookcellinfo t where t.bsBookcaseinfo.caseId = ?1")
	List<BsBookcellinfo> findByCaseId(int caseId);
	
}
