package com.yuyue.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.yuyue.pojo.BsGene;

public interface BsGeneDAO extends JpaRepository<BsGene, Integer>{

	public Page<BsGene> findByName(String name, Pageable pageable);
	
}
