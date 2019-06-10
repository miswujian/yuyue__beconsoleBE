package com.yuyue.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuyue.dao.BeLocationDAO;
import com.yuyue.pojo.BeLocation;

@Service
public class BeLocationService {

	@Autowired
	private BeLocationDAO beLocationDAO;
	
	public List<BeLocation> getByWarehouse(int warehouseId){
		return beLocationDAO.findByWarehouseId(warehouseId);
	}
	
	public BeLocation get(int locationId) {
		return beLocationDAO.findOne(locationId);
	}
	
}
