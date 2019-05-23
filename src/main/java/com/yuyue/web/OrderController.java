package com.yuyue.web;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yuyue.pojo.RsCurborrowrecord;
import com.yuyue.pojo.RsCurdonaterecord;
import com.yuyue.pojo.RsHisborrowrecord;
import com.yuyue.service.BsBookcellinfoService;
import com.yuyue.service.RsCurborrowrecordService;
import com.yuyue.service.RsCurdonaterecordService;
import com.yuyue.service.RsHisborrowrecordService;
import com.yuyue.util.Page4Navigator;
import com.yuyue.util.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 还捐审单未实现 除了借阅 其他的高级查询都未实现
 * 订单管理
 * @author 吴俭
 *
 */
@RestController
@RequestMapping("/order")
@Api(value="订单管理接口",tags={"订单管理接口"})
public class OrderController {

	@Autowired
	private RsCurborrowrecordService rsCurborrowrecordService;
	
	@Autowired
	private RsCurdonaterecordService rsCurdonatercordService;
	
	@Autowired
	private RsHisborrowrecordService rsHisborrowrecordService;
	
	@Autowired
	private BsBookcellinfoService bsBookcellinfoService;
	
	/**
	 * 借阅订单集合 电子标签为 bookinstore 里面的rfid  isbn为bookinfo里面的isbn
	 * @param start
	 * @param size
	 * @return
	 */
	@GetMapping("/curborrowrecords")
	@ApiOperation(value="借阅订单查询",notes="借阅订单查询")
	public Page4Navigator<RsCurborrowrecord> listCurborrowrecord(
			@ApiParam(name="start",required=false)@RequestParam(value = "start",defaultValue="0")int start,
			@ApiParam(name="size",required=false)@RequestParam(value = "size",defaultValue="10")int size,
			@ApiParam(name="stage",required=false)@RequestParam(value = "stage",defaultValue="")String stage,
			@ApiParam(name="starttime",required=false)Date starttime, 
			@ApiParam(name="endtime",required=false)Date endtime, 
			@ApiParam(name="deliverType",required=false)@RequestParam(value = "deliverType",defaultValue="")String deliverType,
			@ApiParam(name="expressNo",required=false)String expressNo, 
			@ApiParam(name="keyword1",required=false)@RequestParam(value = "keyword1",defaultValue="")String keyword1, 
			@ApiParam(name="keyword2",required=false)@RequestParam(value = "keyword2",defaultValue="")String keyword2) {
		start=start<0?0:start;
		Page4Navigator<RsCurborrowrecord> rcbs = rsCurborrowrecordService.
				list(start, size, 5, stage, starttime, endtime, deliverType, expressNo, keyword1, keyword2);
		rsCurborrowrecordService.setBookinstoreBookNull(rcbs.getContent());
		rsCurborrowrecordService.setUserinfoNull(rcbs.getContent());
		return rcbs;
	}
	
	@GetMapping("/borrowrecords/{orderNo}")
	@ApiOperation(value="借阅订单详情",notes="借阅订单详情")
	public Object getBorrowrecord(@PathVariable(name = "orderNo")String orderNo) {
		Map<String, Object> map = new HashMap<>();
		RsCurborrowrecord rcb = rsCurborrowrecordService.getByOrderNo(orderNo);
		if(rcb!=null) {
			rsCurborrowrecordService.setUserfine(rcb);
			rsCurborrowrecordService.setBookinstoreBookNull(rcb);
			rsCurborrowrecordService.setUserinfoNull(rcb);
		}
		RsHisborrowrecord rhb = rsHisborrowrecordService.getByOrderNo(orderNo);
		if(rhb!=null) {
			rsHisborrowrecordService.setUserinfoNull(rhb);
			rsHisborrowrecordService.setBookinstoreBookNull(rhb);
		}
		map.put("curborrowrecord", rcb);
		map.put("hisborrowrecord", rhb);
		return map;
	}
	
	/**
	 * 改变状态
	 * @param borrowId
	 * @param stage 1-发快递/发书柜 2-接单 3-上柜 4-审核通过 5-审核不通过 6-关闭订单
	 * @return
	 */
	@PostMapping("/curborrowrecords/{borrowId}/{stage}")
	@ApiOperation(value="借阅订单状态更新",notes="借阅订单状态更新 1-发快递/发书柜 2-接单 3-上柜 4-审核通过 5-审核不通过 6-关闭订单")
	public Object changeCurborrowrecordStage(@PathVariable("borrowId")int borrowId, @PathVariable("stage")int stage) {
		int flag = rsCurborrowrecordService.updateByStage(borrowId, stage);
		if(flag == -1)
			return Result.fail("订单不存在");
		else if(flag == 0)
			return Result.fail("更新状态失败");
		return Result.success();
	}
	
	/**
	 * 根据订单号来获取借阅的信息
	 * @param orderNo
	 * @return
	 */
	@GetMapping("/borrowrecords/{code}/{status}")
	@ApiOperation(value="还捐审单", notes="还捐审单 status:1-还书审单 2-捐书审单")
	public Object getBorrowrecordByOrderNo(@PathVariable("code")String code, @PathVariable("status")int status) {
		if(status == 1) {
			RsCurborrowrecord rcb = rsCurborrowrecordService.getByRfid(code);
			if(rcb == null)
				return Result.fail("无效条码");
			rsCurborrowrecordService.setBookinstoreBookNull(rcb);
			rsCurborrowrecordService.setUserinfoNull(rcb);
			bsBookcellinfoService.setBookcellinfoNull(rcb);
			return rcb;
		}else if(status == 2) {
			RsCurdonaterecord rcd = rsCurdonatercordService.getByIsbn(code);
			if(rcd == null)
				return Result.fail("无效条码");
			return rcd;
		}
		else {
			return Result.fail("无效状态");
		}
	}
	
	/**
	 * 捐书订单集合
	 * @param start
	 * @param size
	 * @return
	 */
	@GetMapping("/curdonaterecords")
	@ApiOperation(value="捐书订单查询", notes="捐书订单查询")
	public Page4Navigator<RsCurdonaterecord> listCurdonaterecord(
			@ApiParam(name="start",required=false)@RequestParam(value = "start",defaultValue="0")int start,
			@ApiParam(name="size",required=false)@RequestParam(value = "size",defaultValue="10")int size,
			@ApiParam(name="status",required=false)@RequestParam(value = "status",defaultValue="")String status,
			@ApiParam(name="starttime",required=false)Date starttime, 
			@ApiParam(name="endtime",required=false)Date endtime, 
			@ApiParam(name="keyword1",required=false)@RequestParam(value = "keyword1",defaultValue="")String keyword1, 
			@ApiParam(name="keyword2",required=false)@RequestParam(value = "keyword2",defaultValue="")String keyword2){
		start=start<0?0:start;
		Page4Navigator<RsCurdonaterecord> rcds = rsCurdonatercordService.list
				(start, size, 5, status, starttime, endtime, keyword1, keyword2);
		rsCurdonatercordService.setUserinfoNull(rcds.getContent());
		rsCurdonatercordService.setBookName(rcds.getContent());
		return rcds;
	}
	
	@GetMapping("donaterecords/{orderNo}")
	@ApiOperation(value="捐书订单详情",notes="捐书订单详情")
	public Object getDonaterecord(@PathVariable(name = "orderNo")String orderNo) {
		RsCurdonaterecord rcd = rsCurdonatercordService.getByOrder(orderNo);
		if(rcd!=null) {
			rsCurdonatercordService.setUserinfoNull(rcd);
			rsCurdonatercordService.setBookName(rcd);
		}
		return rcd;
	}
	
	/**
	 * 历史借阅集合
	 * @param start
	 * @param size
	 * @return
	 */
	@GetMapping("/hisborrowrecords")
	@ApiOperation(value="历史借阅查询", notes="历史借阅查询")
	public Page4Navigator<RsHisborrowrecord> listHisborrowrecord(
			@ApiParam(name="start",required=false)@RequestParam(value = "start",defaultValue="0")int start,
			@ApiParam(name="size",required=false)@RequestParam(value = "size",defaultValue="10")int size,
			@ApiParam(name="stage",required=false)@RequestParam(value = "stage",defaultValue="")String stage,
			@ApiParam(name="starttime",required=false)Date starttime, 
			@ApiParam(name="endtime",required=false)Date endtime, 
			@ApiParam(name="deliverType",required=false)@RequestParam(value = "deliverType",defaultValue="")String deliverType,
			@ApiParam(name="returnWay",required=false)@RequestParam(value = "returnWay",defaultValue="")String returnWay, 
			@ApiParam(name="keyword1",required=false)@RequestParam(value = "keyword1",defaultValue="")String keyword1, 
			@ApiParam(name="keyword2",required=false)@RequestParam(value = "keyword2",defaultValue="")String keyword2){
		start = start<0?0:start;
		Page4Navigator<RsHisborrowrecord> rhbs = rsHisborrowrecordService.list
				(start, size, 5, stage, starttime, endtime, deliverType, returnWay, keyword1, keyword2);;
		//rsHisborrowrecordService.setBookinfoNull(rhbs.getContent());
		rsHisborrowrecordService.setUserinfoNull(rhbs.getContent());
		rsHisborrowrecordService.setBookinstoreBookNull(rhbs.getContent());
		//bsUserdynamicService.setUserdynamicNull(rhbs.getContent());
		//bsBookcellinfoService.setBookcellinfoNull(rhbs.getContent());
		return rhbs;
	}
	
}
