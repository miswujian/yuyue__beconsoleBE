package com.yuyue.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yuyue.pojo.RsCurborrowrecord;
import com.yuyue.pojo.RsCurdonaterecord;
import com.yuyue.pojo.RsHisborrowrecord;
import com.yuyue.service.BsBookcellinfoService;
import com.yuyue.service.BsBookinfoService;
import com.yuyue.service.BsUserdynamicService;
import com.yuyue.service.RsCurborrowrecordService;
import com.yuyue.service.RsCurdonaterecordService;
import com.yuyue.service.RsHisborrowrecordService;
import com.yuyue.util.Page4Navigator;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 还捐审单未实现 其他的查询和操作未实现
 * 订单管理
 * @author 吴俭
 *
 */
@RestController
@Api(value="订单管理接口",tags={"订单管理接口"})
public class OrderController {

	@Autowired
	private RsCurborrowrecordService rsCurborrowrecordService;
	
	@Autowired
	private RsCurdonaterecordService rsCurdonatercordService;
	
	@Autowired
	private RsHisborrowrecordService rsHisborrowrecordService;
	
	@Autowired
	private BsBookinfoService bsBookinfoService;
	
	@Autowired
	private BsUserdynamicService bsUserdynamicService;
	
	@Autowired
	private BsBookcellinfoService bsBookcellinfoService;
	
	/**
	 * 借阅订单集合
	 * @param start
	 * @param size
	 * @return
	 */
	@GetMapping("/curborrowrecords")
	@ApiOperation(value="借阅订单查询",notes="借阅订单查询")
	public Page4Navigator<RsCurborrowrecord> listCurborrowrecord(
			@ApiParam(name="start",required=false)@RequestParam(value = "start",defaultValue="0")int start,
			@ApiParam(name="size",required=false)@RequestParam(value = "size",defaultValue="10")int size) {
		start=start<0?0:start;
		Page4Navigator<RsCurborrowrecord> rcbs = rsCurborrowrecordService.list(start, size, 5);
		bsBookinfoService.setBookinfoNull(rcbs.getContent());
		return rcbs;
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
			@ApiParam(name="size",required=false)@RequestParam(value = "size",defaultValue="10")int size){
		start=start<0?0:start;
		Page4Navigator<RsCurdonaterecord> rcds = rsCurdonatercordService.list(start, size, 5);
		return rcds;
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
			@ApiParam(name="size",required=false)@RequestParam(value = "size",defaultValue="10")int size){
		start = start<0?0:start;
		Page4Navigator<RsHisborrowrecord> rhbs = rsHisborrowrecordService.list(start, size, 5);
		bsBookinfoService.setHBookinfoNull(rhbs.getContent());
		bsUserdynamicService.setUserdynamicNull(rhbs.getContent());
		bsBookcellinfoService.setBookcellinfoNull(rhbs.getContent());
		return rhbs;
	}
	
}
