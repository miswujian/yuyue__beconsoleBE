package com.yuyue.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.yuyue.pojo.BsSysdict;

public interface BsSysdictDAO extends JpaRepository<BsSysdict, Integer> {

	public Page<BsSysdict> findByDictType(String dictType, Pageable pageable);
	
}
