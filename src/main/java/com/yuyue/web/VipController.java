package com.yuyue.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yuyue.pojo.BsInvitecode;
import com.yuyue.pojo.BsUserinfo;
import com.yuyue.pojo.RsUsercredit;
import com.yuyue.pojo.RsVipplanorder;
import com.yuyue.pojo.User;
import com.yuyue.service.BsInvitecodeService;
import com.yuyue.service.BsIvtuserinfoService;
import com.yuyue.service.BsUserdynamicService;
import com.yuyue.service.BsUserinfoService;
import com.yuyue.service.RsUsercreditService;
import com.yuyue.service.RsVipplanorderService;
import com.yuyue.util.Page4Navigator;
import com.yuyue.util.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.sf.json.JSONObject;

/**
 * 会员信息管理 字段缺少过多 支付订单的查询需要更加完善 邀请码的查询和操作 积分管理的查询和手动增减
 * 规则设置在sysdict 按一种类型来查询
 * 会员管理
 *
 */
@RestController
@RequestMapping("vip")
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
	
	@Autowired
	private BsUserinfoService bsUserinfoService;
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate = null;
	
	@GetMapping("/userinfos")
	@ApiOperation(value="会员信息", notes="会员信息")
	public Object listUserinfo(
			@ApiParam(name="start",required=false)@RequestParam(value = "start", defaultValue = "0") int start,
			@ApiParam(name="size",required=false)@RequestParam(value = "size", defaultValue = "10") int size, 
			@ApiParam(name="type",required=false)@RequestParam(value = "type", defaultValue = "")String type,
			@ApiParam(name="status",required=false)@RequestParam(value = "status", defaultValue = "")String status,
			@ApiParam(name="ivtcodeId",required=false)@RequestParam(value = "ivtcodeId", defaultValue = "")String ivtcodeId,
			@ApiParam(name="id",required=false)@RequestParam(value = "id", defaultValue = "")String id,
			@ApiParam(name="local",required=false)@RequestParam(value = "local", defaultValue = "")String local,
			@ApiParam(name="starttime",required=false)Date starttime, 
			@ApiParam(name="endtime",required=false)Date endtime, 
			@ApiParam(name="keyowrd",required=false)@RequestParam(value = "keyowrd", defaultValue = "")String keyowrd
			) {
		start = start<0?0:start;
		return bsUserinfoService.list(start, size, 5);
	}
	
	/**
	 * 支付订单
	 * @param start
	 * @param size
	 * @return
	 */
	@GetMapping("/vipplanorders")
	@ApiOperation(value="支付订单", notes="支付订单")
	public Page4Navigator<RsVipplanorder> listVipplanorder(
			@ApiParam(name="start",required=false)@RequestParam(value = "start", defaultValue = "0") int start,
			@ApiParam(name="size",required=false)@RequestParam(value = "size", defaultValue = "10") int size,
			@ApiParam(name="payType",required=false)@RequestParam(value = "payType", defaultValue = "") String payType,
			@ApiParam(name="statu",required=false)@RequestParam(value = "statu", defaultValue = "") String status,
			@ApiParam(name="starttime",required=false) Date starttime,
			@ApiParam(name="endtime",required=false) Date endtime,
			@ApiParam(name="keyword",required=false)@RequestParam(value = "keyword", defaultValue = "") String keyword){
		start = start<0?0:start;
		Page4Navigator<RsVipplanorder> rvs = 
				rsVipplanorderService.list(start, size, 5, payType, status, starttime, endtime, keyword);
		rsVipplanorderService.setUserinfoNull(rvs.getContent());
		return rvs;
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
			@ApiParam(name="start",required=false)@RequestParam(value = "start",defaultValue="0")int start, 
			@ApiParam(name="size",required=false)@RequestParam(value = "size",defaultValue="10")int size){
		start = start<0?0:start;
		return rsVipplanorderService.listBystatus(status, start, size, 5);
	}
	
	/**
	 * 邀请码
	 * @param start
	 * @param size
	 * 统计邀请用户 就是有效人数
	 * 
	 * @return
	 */
	@GetMapping("/invitecodes")
	@ApiOperation(value="邀请码", notes="邀请码")
	public Page4Navigator<BsInvitecode> listInvitecode(
			@ApiParam(name="start",required=false)@RequestParam(value = "start", defaultValue="0")int start,
			@ApiParam(name="size",required=false)@RequestParam(value="size",defaultValue="10")int size,
			@ApiParam(name="status",required=false)@RequestParam(value = "status", defaultValue="")String status,
			@ApiParam(name="starttime",required=false)Date starttime,
			@ApiParam(name="endtime",required=false)Date endtime,
			@ApiParam(name="keyword",required=false)@RequestParam(value = "keyword", defaultValue="")String keyword){
		start = start<0?0:start;
		Page4Navigator<BsInvitecode> bics = bsInvitecodeService.list(start, size, 5, status, starttime, endtime, keyword);
		bsIvtuserinfoService.setBsIvtuserinfo(bics.getContent());
		return bics;
	}
	
	/**
	 * 积分管理
	 * @param start
	 * @param size
	 * @return
	 */
	@GetMapping("/usercredits")
	@ApiOperation(value="积分管理", notes="积分管理")
	public Page4Navigator<RsUsercredit> listUsercredit(
			@ApiParam(name="start",required=false)@RequestParam(value = "start", defaultValue="0")int start,
			@ApiParam(name="size",required=false)@RequestParam(value="size",defaultValue="10")int size,
			@ApiParam(name="type",required=false)@RequestParam(value = "type", defaultValue="")String type,
			@ApiParam(name="starttime",required=false)Date starttime,
			@ApiParam(name="endtime",required=false)Date endtime,
			@ApiParam(name="keyword",required=false)@RequestParam(value = "keyword", defaultValue="")String keyword){
		start = start<0?0:start;
		Page4Navigator<RsUsercredit> rucs = rsUsercreditService.list(start, size, 5, type, starttime, endtime, keyword);
		bsUserdynamicService.setUsercreditUserdynamicNull(rucs.getContent());
		return rucs;
	}
	
	@PostMapping("/usercredits/{userId}/{type}/{credit}")
	@ApiOperation(value="修改积分", notes="修改积分")
	public Object changeCredit(
			@PathVariable(value = "userId")int userId, 
			@PathVariable(value = "type")String type, 
			@PathVariable(value = "credit")String credit,
			HttpServletRequest httpServletRequest) {
		String u = stringRedisTemplate.opsForValue().get(httpServletRequest.getSession().getId().toString());
		JSONObject json = JSONObject.fromObject(u);
        User user = (User) JSONObject.toBean(json,User.class);
        int flag = bsUserinfoService.changeCredit(userId, type, credit, user.getUid());
        if(flag>0)
        	return Result.success();
        return Result.fail("积分修改失败");
	}
	
	@GetMapping("/userinfos/{vipNo}")
	@ApiOperation(value="获取用户信息", notes="获取用户信息通过会员号")
	public Object getUserinfoByVipNo(@PathVariable(value = "vipNo")String vipNo) {
		BsUserinfo bu = bsUserinfoService.getUserinfoByVipNo(vipNo);
		if(bu == null)
			return Result.fail("会员账号不存在");
		return bu;
	}
	
}
