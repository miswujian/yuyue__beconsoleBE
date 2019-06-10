package com.yuyue.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.yuyue.pojo.RsStoragerecord;

public interface RsStoragerecordDAO extends JpaRepository<RsStoragerecord, Integer> {

	public Page<RsStoragerecord> findByRecordType(byte recordType, Pageable pageable);
	
}
