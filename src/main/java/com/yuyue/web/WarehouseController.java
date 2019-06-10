package com.yuyue.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yuyue.pojo.BeWarehouse;
import com.yuyue.pojo.BookCaseStatus;
import com.yuyue.pojo.Bookinstore;
import com.yuyue.pojo.BsBookcaseinfo;
import com.yuyue.pojo.RsBookcaserecord;
import com.yuyue.pojo.RsStoragerecord;
import com.yuyue.pojo.RsTransferrecord;
import com.yuyue.service.BeLocationService;
import com.yuyue.service.BeUserService;
import com.yuyue.service.BeWarehouseService;
import com.yuyue.service.BsBookcaseinfoService;
import com.yuyue.service.BsBookinstoreService;
import com.yuyue.service.RsBookcaserecordService;
import com.yuyue.service.RsStoragerecordService;
import com.yuyue.service.RsTransferrecordService;
import com.yuyue.util.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 问题最多的地方
 * 仓库管理
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
	
	@Autowired
	private RsStoragerecordService rsStoragerecordService;
	
	@Autowired
	private BeUserService beUserService;
	
	@Autowired
	private BeLocationService beLocationService;
	
	@Autowired
	private RsTransferrecordService rsTransferrecordService;
	
	@Autowired
	private RsBookcaserecordService rsBookcaserecordService;
	
	@GetMapping("/userwarehouses")
	@ApiOperation(value="用户仓库信息获取", notes="当前用户拥有的仓库权限")
	public Object warehouseinfo(HttpSession session) {
		return beUserService.getUserWarehouse(session);
	}
	
	@GetMapping("/locations/{warehouseId}")
	@ApiOperation(value="货位获取", notes="货位获取")
	public Object location(@PathVariable("warehouseId")int warehouseId) {
		return beLocationService.getByWarehouse(warehouseId);
	}
	
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
	
	/**
	 * 根据用户的来 用in
	 * @param start
	 * @param size
	 * @return
	 */
	@GetMapping("/bookinstores")
	@ApiOperation(value="库存查询", notes="库存查询")
	public Object bookinstore(
			@ApiParam(name="start",required=false)@RequestParam(value = "start", defaultValue = "0") int start,
			@ApiParam(name="size",required=false)@RequestParam(value = "size", defaultValue = "10") int size,
			HttpSession session) {
		start = start>0?start:0;
		return bsBookinstoreService.list(start, size, 5, session);
	}

	@GetMapping("/bookinstores/{rfid}")
	@ApiOperation(value="检测rfid是否已经存在", notes="检测rfid是否已经存在")
	public Object countByRfid(@PathVariable("rfid")String rfid) {
		int flag = bsBookinstoreService.countByRfid(rfid);
		if(flag>0)
			return Result.fail("该rfid已经存在");
		return Result.success();
	}
	
	/**
	 * 入库类型 0-其他 1-新书 2-捐书 3-还书 出库类型 0-其他 1-退货 2-报损 3-领用
	 * @param start
	 * @param size
	 * @param recordType
	 * @return
	 */
	@GetMapping("/storagerecords")
	@ApiOperation(value="出入库单", notes="出入库单")
	public Object storagerecord(
			@ApiParam(name="start",required=false)@RequestParam(value = "start", defaultValue = "0") int start,
			@ApiParam(name="size",required=false)@RequestParam(value = "size", defaultValue = "10") int size,
			@RequestParam(value = "recordType",defaultValue="0")byte recordType) {
		start = start>=0?start:0;
		return rsStoragerecordService.list(start, size, 5, recordType);
	}
	
	@PostMapping("/storagerecords")
	@ApiOperation(value = "增加库单", notes = "增加库单")
	public Object addStoragerecord(@RequestBody RsStoragerecord rsStoragerecord, HttpSession session) throws Exception {
		int flag = rsStoragerecordService.add(rsStoragerecord, session);
		if (flag > 0)
			return Result.success(flag);
		return Result.fail("添加失败");
	}
	
	@PutMapping("/storagerecords")
	@ApiOperation(value = "更新库单", notes = "更新库单")
	public Object updateStoragerecord(@RequestBody RsStoragerecord rsStoragerecord) throws Exception {
		int flag = rsStoragerecordService.update(rsStoragerecord);
		if(flag > 0)
			return Result.success();
		return Result.fail("更新失败");
	}
	
	/**
	 * 订单状态 1-草稿 2-待审核 3-已审核
	 * @return
	 */
	@PutMapping("/storagerecords/{storageId}/{status}")
	@ApiOperation(value = "修改库单状态", notes = "修改库单状态")
	public Object changeStoragerecordStatus(
			@PathVariable("storageId")int storageId,
			@PathVariable("status")byte status, HttpSession session) {
		int flag = rsStoragerecordService.changeStatus(storageId, status, session);
		if(flag > 0)
			return Result.success();
		if(status==1)
			return Result.fail("保存失败");
		else if(status==2)
			return Result.fail("提交失败");
		else if(status==3)
			return Result.fail("审核失败");
		return Result.fail("未知错误");
	}
	
	@DeleteMapping("/storagerecords/{storageId}")
	@ApiOperation(value = "删除库单", notes = "删除库单")
	public Object deleteStoragerecord(@PathVariable("storageId")int storageId) {
		int flag = rsStoragerecordService.delete(storageId);
		if(flag > 0)
			return Result.success();
		return Result.fail("删除失败");
	}
	
	@GetMapping("/storagerecords/{storageId}")
	@ApiOperation(value = "获取库单", notes = "获取库单")
	public Object getStoragerecord(@PathVariable("storageId")int storageId) {
		return rsStoragerecordService.get(storageId);
	}
	
	@GetMapping("/bookinstores/{code}")
	@ApiOperation(value = "根据条码获取商品信息", notes = "根据条码获取商品信息")
	public Object getBookinstore(@PathVariable String code) {
		Bookinstore bi = bsBookinstoreService.getBsBookinstore(code);
		if(bi==null)
			return -1;
		return bi;
	}
	
	@GetMapping("/transferrecords/{transferId}")
	@ApiOperation(value = "获取移库单", notes = "获取移库单")
	public Object getTransferrecord(@PathVariable(name = "transferId")int transferId) {
		return rsTransferrecordService.get(transferId);
	}
	
	@PostMapping("/transferrecords")
	@ApiOperation(value = "增加移库单", notes = "增加移库单")
	public Object addTransferrecord(@RequestBody RsTransferrecord rsTransferrecord, HttpSession session) throws Exception {
		int flag = rsTransferrecordService.add(rsTransferrecord, session);
		if(flag > 0)
			return Result.success(flag);
		return Result.fail("添加失败");
	}
	
	@PutMapping("/transferrecords")
	@ApiOperation(value = "更新移库单", notes = "更新移库单")
	public Object updateTransferrecord(@RequestBody RsTransferrecord rsTransferrecord) throws Exception {
		int flag = rsTransferrecordService.update(rsTransferrecord);
		if(flag > 0)
			return Result.success();
		return Result.fail("更新失败");
	}
	
	/**
	 * 订单状态 1-草稿 2-待审核 3-已审核
	 * @return
	 */
	@PutMapping("/transferrecords/{transferId}/{status}")
	@ApiOperation(value = "修改移库单状态", notes = "修改移库单状态")
	public Object changeTransferrecordStatus(@PathVariable("transferId")int transferId,
			@PathVariable("status")byte status, HttpSession session) {
		int flag = rsTransferrecordService.changeStatus(transferId, status, session);
		if(flag > 0)
			return Result.success();
		if(status==1)
			return Result.fail("保存失败");
		else if(status==2)
			return Result.fail("提交失败");
		else if(status==3)
			return Result.fail("审核失败");
		return Result.fail("未知错误");
	}
	
	@DeleteMapping("/transferrecords/{transferId}")
	@ApiOperation(value = "删除移库单", notes = "删除移库单")
	public Object deleteTransferrecord(@PathVariable("transferId")int transferId) {
		int flag = rsTransferrecordService.delete(transferId);
		if(flag > 0)
			return Result.success();
		return Result.fail("删除失败");
	}
	
	@GetMapping("/transferrecords")
	@ApiOperation(value = "移库单查询", notes = "移库单查询")
	public Object listTransferrecord(
			@ApiParam(name="start",required=false)@RequestParam(value = "start", defaultValue = "0") int start,
			@ApiParam(name="size",required=false)@RequestParam(value = "size", defaultValue = "10") int size) {
		start = start>0?start : 0;
		return rsTransferrecordService.list(start, size, 5);
	}
	
	@GetMapping("/bookcaserecords")
	@ApiOperation(value = "调拨单查询", notes = "调拨单查询")
	public Object listBookcaserecord(
			@ApiParam(name="start",required=false)@RequestParam(value = "start", defaultValue = "0") int start,
			@ApiParam(name="size",required=false)@RequestParam(value = "size", defaultValue = "10") int size,
			@RequestParam(value = "type",defaultValue="1")byte type) {
		start = start>0?start:0;
		return rsBookcaserecordService.list(start, size, 5, type);
	}
	
	@PostMapping("/bookcaserecords")
	@ApiOperation(value = "增加调拨单", notes = "增加调拨单")
	public Object addBookcaserecord(@RequestBody RsBookcaserecord rsBookcaserecord, HttpSession session) throws Exception {
		int flag = rsBookcaserecordService.add(rsBookcaserecord, session);
		if(flag > 0)
			return Result.success();
		return Result.fail("增加失败");
	}
	
	@PutMapping("/bookcaserecords")
	@ApiOperation(value = "更新调拨单", notes = "更新调拨单")
	public Object updateBookcaserecord(@RequestBody RsBookcaserecord rsBookcaserecord) throws Exception {
		int flag = rsBookcaserecordService.update(rsBookcaserecord);
		if(flag > 0)
			return Result.success();
		return Result.fail("更新失败");
	}
	
	@PutMapping("/bookcaserecord")
	@ApiOperation(value = "修改调拨单状态", notes = "修改调拨单状态")
	public Object changeBookcaserecord(@RequestBody BookCaseStatus bookCaseStatus, HttpSession session) throws Exception {
		int flag = rsBookcaserecordService.changeStatus(bookCaseStatus, session);
		if(flag > 0)
			return Result.success();
		return Result.fail("更换状态失败");
	}
	
	@DeleteMapping("/bookcaserecords/{bookcaseId}")
	@ApiOperation(value = "删除调拨单", notes = "删除调拨单")
	public Object deleteBookcaserecord(@PathVariable("bookcaseId")int bookcaseId) throws Exception {
		int flag = rsBookcaserecordService.delete(bookcaseId);
		if(flag > 0)
			return Result.success();
		return Result.fail("删除失败");
	}
	
	@GetMapping("/bookcaserecords/{bookcaseId}")
	@ApiOperation(value = "获取调拨单", notes = "获取调拨单")
	public Object getBookcaserecord(@PathVariable("bookcaseId")int bookcaseId) {
		return rsBookcaserecordService.get(bookcaseId);
	}
	
}
