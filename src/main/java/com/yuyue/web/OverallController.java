package com.yuyue.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import com.yuyue.pojo.BeRole;
import com.yuyue.pojo.BeUser;
import com.yuyue.pojo.User;
import com.yuyue.service.BeRoleService;
import com.yuyue.service.BeUserService;
import com.yuyue.service.BsBookcaseinfoService;
import com.yuyue.service.BsBookcellinfoService;
import com.yuyue.service.RsRolepermissionService;
import com.yuyue.util.LoginResult;
import com.yuyue.util.Md5Util;
import com.yuyue.util.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 全局管理
 * @author 吴俭
 *
 */
@RestController
@Api(value="全局管理接口",tags={"全局管理接口"})
public class OverallController {

	@Autowired
	private BeRoleService beRoleService;

	@Autowired
	private BsBookcaseinfoService bsBookcaseinfoService;

	@Autowired
	private BsBookcellinfoService bsBookcellinfoService;

	@Autowired
	private BeUserService beUserService;
	
	@Autowired
	private RsRolepermissionService rsRolepermissionService;
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate = null;

	/**
	 * 角色集合
	 * @return
	 */
	@GetMapping("/roles")
	@ApiIgnore
	public List<BeRole> list() {
		List<BeRole> br = beRoleService.list();
		rsRolepermissionService.setRolepermission(br);
		return br;
	}
	
	/**
	 * 首页
	 * @return
	 */
	@GetMapping("/home")
	@ApiOperation(value="首页", notes="首页")
	public Object home() {
		int allocation = bsBookcaseinfoService.getAllocation();
		int repair = bsBookcellinfoService.getRepair();
		Map<String, Object> map = new HashMap<>();
		map.put("allocation", allocation);
		map.put("repair", repair);
		return Result.success(map);
	}
	
	/**
	 * 登录
	 * @param beUser
	 * @param session
	 * @return
	 */
	@PostMapping(value="/login")
	@ApiOperation(value="登录", notes="登录")
	public Object login(@RequestBody BeUser beUser, HttpSession session) throws IOException {
		if(beUser.getUserName()==null||beUser.getPassword()==null)
			Result.fail("请输入账号或密码");
		String name = beUser.getUserName();
		name = HtmlUtils.htmlEscape(name);
		String password = Md5Util.md5(beUser.getPassword());
		BeUser bu = beUserService.login(name, password);
		if (bu != null) {
			//session.setAttribute("user", bu);
			BeUser user = new BeUser();
			user = bu;
			user.setPassword(null);
			User u = userTransform(user);
			JSONObject json = JSONObject.fromObject(u);
			System.err.println(json.toString());
			stringRedisTemplate.opsForValue().set(session.getId().toString(), json.toString(),5,TimeUnit.HOURS);
			System.err.println(session.getId().toString()+" : "+json.toString());
			return LoginResult.success(user,session.getId());
		}
		String message = "账号或密码错误";
		return Result.fail(message);
	}

	@PostMapping(value="logout")
	@ApiOperation(value="注销", notes="注销")
	public Object logout(HttpSession session) {
		String u = stringRedisTemplate.opsForValue().get(session.getId().toString());
		if(u==null)
			return Result.fail("登录状态不存在");
		stringRedisTemplate.delete(session.getId().toString());
		return Result.success();
	}
	
	/**
	 * 获得登录角色的权限
	 * @param session
	 * @return
	 */
	@GetMapping("/userPermissions")
	@ApiIgnore
	public Object getUserPermission(HttpSession session) {
		String u = stringRedisTemplate.opsForValue().get(session.getId().toString());
		JSONObject json = JSONObject.fromObject(u);
		User user = (User) JSONObject.toBean(json,User.class);
		BeUser bu = beUserTransform(user);
		return beUserService.getUserPermission(bu.getUid());
	}
	
	/**
	 * 修改密码
	 * @param oldpassword
	 * @param newpassword
	 * @param session
	 * @return
	 */
	// @PostMapping("/changePassword")
	@PostMapping(value = "/passwords")
	@ApiOperation(value="修改密码", notes="修改密码")
	public Object changePassword(String oldpassword, String newpassword, HttpSession session) throws IOException{
		String u = stringRedisTemplate.opsForValue().get(session.getId().toString());
		JSONObject json = JSONObject.fromObject(u);
		User user = (User) JSONObject.toBean(json,User.class);
		BeUser bu = beUserTransform(user);
		if(!Md5Util.md5(oldpassword).equals(bu.getPassword())) {
			String message = "原始密码错误";
			return Result.fail(message);
		}
		String password = Md5Util.md5(newpassword);
		bu.setPassword(password);
		int flag = beUserService.changePassword(bu);
		if(flag <= 0)
			return Result.fail("修改密码失败");
		user = userTransform(bu);
		json = JSONObject.fromObject(user);
		stringRedisTemplate.opsForValue().set(session.getId().toString(), json.toString(),5,TimeUnit.HOURS);
		return Result.success();
	}
	
	public User userTransform(BeUser beUser) {
		User user = new User();
		user.setUid(beUser.getUid());
		user.setUserName(beUser.getUserName());
		user.setTelephone(beUser.getTelephone());
		user.setPermissions(beUser.getPermissions());
		user.setRole(beUser.getRole());
		user.setRoleType(beUser.getRoleType());
		return user;
	}
	
	public BeUser beUserTransform(User user) {
		BeUser beUser = beUserService.getById(user.getUid());
		return beUser;
	}

}
