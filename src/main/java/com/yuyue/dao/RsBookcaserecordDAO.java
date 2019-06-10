package com.yuyue.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.yuyue.pojo.RsBookcaserecord;

public interface RsBookcaserecordDAO extends JpaRepository<RsBookcaserecord, Integer> {

	public Page<RsBookcaserecord> findByType(byte type, Pageable pageable);
	
}
