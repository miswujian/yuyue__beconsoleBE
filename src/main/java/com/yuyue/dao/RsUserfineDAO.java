package com.yuyue.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yuyue.pojo.RsUserfine;

public interface RsUserfineDAO extends JpaRepository<RsUserfine, String>{

	public List<RsUserfine> findByOrderNo(String orderNo);
	
}
