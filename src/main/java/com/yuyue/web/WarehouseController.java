package com.yuyue.web;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yuyue.pojo.BeWarehouse;
import com.yuyue.pojo.BsBookcaseinfo;
import com.yuyue.service.BeWarehouseService;
import com.yuyue.service.BsBookcaseinfoService;
import com.yuyue.service.BsBookinstoreService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 问题最多的地方
 * 仓库管理
 * @author 吴俭
 *
 */
@RestController
@RequestMapping("/warehouse")
@Api(value="仓库管理接口", tags="仓库管理接口")
public class WarehouseController {
	
	@Autowired
	private BsBookcaseinfoService bsBookcaseinfoService;
	
	@Autowired
	private BeWarehouseService beWarehouseService;
	
	@Autowired
	private BsBookinstoreService bsBookinstoreService;
	
	@GetMapping("/warehouses")
	@ApiOperation(value="仓库信息获取", notes="仓库键值对")
	public Object warehouseinfo() {
		List<BeWarehouse> bws = beWarehouseService.list();
		Map<Integer, String> map = new HashMap<>();
		for(BeWarehouse bw : bws) {
			map.put(bw.getWarehouseId(), bw.getWarehouseName());
		}
		return map;
	}
	
	@GetMapping("/bookcaseinfos")
	@ApiOperation(value="柜子信息获取", notes="柜子键值对")
	public Object bookcaseinfo() {
		List<BsBookcaseinfo> bbcs = bsBookcaseinfoService.list();
		Map<Integer, String> map = new HashMap<>();
		for(BsBookcaseinfo bbc : bbcs) {
			map.put(bbc.getCaseId(), bbc.getCaseName());
		}
		return map;
	}
	
	@GetMapping("/bookcaseinfos/{warehouseId}")
	@ApiOperation(value="仓库的柜子信息获取", notes="仓库的柜子键值对")
	public Object bookcaseinfoBywarehouseinfo(@PathVariable(name = "warehouseId")Integer warehouseId) {
		List<BsBookcaseinfo> bbcs = bsBookcaseinfoService.list(warehouseId);
		Map<Integer, String> map = new HashMap<>();
		for(BsBookcaseinfo bbc : bbcs) {
			map.put(bbc.getCaseId(), bbc.getCaseName());
		}
		return map;
	}
	
	@GetMapping("/bookinstores")
	@ApiOperation(value="库存查询", notes="库存查询")
	public Object bookinstore(
			@ApiParam(name="start",required=false)@RequestParam(value = "start", defaultValue = "0") int start,
			@ApiParam(name="size",required=false)@RequestParam(value = "size", defaultValue = "10") int size) {
		start = start>0?start:0;
		return bsBookinstoreService.list(start, size, 5);
	}

}
