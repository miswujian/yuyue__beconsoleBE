package com.yuyue.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.yuyue.pojo.BeAdvertisement;

public interface BeAdvertisementDAO extends JpaRepository<BeAdvertisement, Integer> {

	@Query("from BeAdvertisement t where t.bsBookcaseinfo.caseId = ?1 and t.name like ?2")
	public Page<BeAdvertisement> queryByCaseIdEqualsAndNameLike(int caseId, String name, Pageable pageable);
	
	public Page<BeAdvertisement> findByNameLike(String name, Pageable pageable);
	
}
