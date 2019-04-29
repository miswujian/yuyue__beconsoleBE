package com.yuyue.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yuyue.pojo.BsInvitecode;
import com.yuyue.pojo.RsUsercredit;
import com.yuyue.pojo.RsVipplanorder;
import com.yuyue.service.BsInvitecodeService;
import com.yuyue.service.BsIvtuserinfoService;
import com.yuyue.service.BsUserdynamicService;
import com.yuyue.service.RsUsercreditService;
import com.yuyue.service.RsVipplanorderService;
import com.yuyue.util.Page4Navigator;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 会员信息管理 字段缺少过多 支付订单的查询需要更加完善 邀请码的查询和操作 积分管理的查询和手动增减
 * 会员管理
 * @author 吴俭
 *
 */
@RestController
@Api(value="会员管理接口", tags="会员管理接口")
public class VipController {

	@Autowired
	private RsVipplanorderService rsVipplanorderService; 
	
	@Autowired
	private BsInvitecodeService bsInvitecodeService;
	
	@Autowired
	private BsIvtuserinfoService bsIvtuserinfoService;
	
	@Autowired
	private RsUsercreditService rsUsercreditService;
	
	@Autowired
	private BsUserdynamicService bsUserdynamicService;
	
	/**
	 * 支付订单
	 * @param start
	 * @param size
	 * @return
	 */
	@GetMapping("/vipplanorders")
	@ApiOperation(value="支付订单", notes="支付订单")
	public Page4Navigator<RsVipplanorder> listVipplanorder(
			@ApiParam(name="start",required=false)@RequestParam(value = "statr", defaultValue="0")int start,
			@ApiParam(name="size",required=false)@RequestParam(value="size",defaultValue="10")int size){
		start = start<0?0:start;
		return rsVipplanorderService.list(start,size,5);
	}
	
	/**
	 * 通过状态查询支付订单
	 * @param status
	 * @param start
	 * @param size
	 * @return
	 */
	@GetMapping("/vipplanorders/{status}")
	@ApiOperation(value="通过状态查询支付订单", notes="通过url的status状态来查询支付订单")
	public Page4Navigator<RsVipplanorder> listVipplanorderByStatus(@PathVariable(value = "status")byte status,
			@ApiParam(name="start",required=false)@RequestParam(value = "statr",defaultValue="0")int start, 
			@ApiParam(name="size",required=false)@RequestParam(value = "size",defaultValue="10")int size){
		start = start<0?0:start;
		return rsVipplanorderService.listBystatus(status, start, size, 5);
	}
	
	/**
	 * 邀请码
	 * @param start
	 * @param size
	 * @return
	 */
	@GetMapping("invitecodes")
	@ApiOperation(value="邀请码", notes="邀请码")
	public Page4Navigator<BsInvitecode> listInvitecode(
			@ApiParam(name="start",required=false)@RequestParam(value = "statr", defaultValue="0")int start,
			@ApiParam(name="size",required=false)@RequestParam(value="size",defaultValue="10")int size){
		start = start<0?0:start;
		Page4Navigator<BsInvitecode> bics = bsInvitecodeService.list(start, size, 5);
		bsIvtuserinfoService.setBsIvtuserinfo(bics.getContent());
		return bics;
	}
	
	/**
	 * 积分管理
	 * @param start
	 * @param size
	 * @return
	 */
	@GetMapping("usercredits")
	@ApiOperation(value="积分管理", notes="积分管理")
	public Page4Navigator<RsUsercredit> listUsercredit(
			@ApiParam(name="start",required=false)@RequestParam(value = "statr", defaultValue="0")int start,
			@ApiParam(name="size",required=false)@RequestParam(value="size",defaultValue="10")int size){
		start = start<0?0:start;
		Page4Navigator<RsUsercredit> rucs = rsUsercreditService.list(start, size, 5);
		bsUserdynamicService.setUsercreditUserdynamicNull(rucs.getContent());
		return rucs;
	}
	
}
