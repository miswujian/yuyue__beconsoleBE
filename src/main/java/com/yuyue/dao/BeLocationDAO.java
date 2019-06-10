package com.yuyue.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.yuyue.pojo.BeLocation;

public interface BeLocationDAO extends JpaRepository<BeLocation, Integer> {

	@Query("from BeLocation t where t.beWarehouse.warehouseId = ?1")
	public List<BeLocation> findByWarehouseId(int warehouseId);
	
}
