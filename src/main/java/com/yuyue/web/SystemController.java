package com.yuyue.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yuyue.pojo.BeDepartment;
import com.yuyue.pojo.BeInstitution;
import com.yuyue.pojo.BePermission;
import com.yuyue.pojo.BeRole;
import com.yuyue.pojo.BeUser;
import com.yuyue.pojo.BeWarehouse;
import com.yuyue.pojo.BsBookcaseinfo;
import com.yuyue.pojo.RsRolepermission;
import com.yuyue.pojo.User;
import com.yuyue.service.BeDepartmentService;
import com.yuyue.service.BeInstitutionService;
import com.yuyue.service.BeOperationlogService;
import com.yuyue.service.BePermissionService;
import com.yuyue.service.BeRoleService;
import com.yuyue.service.BeUserService;
import com.yuyue.service.BeWarehouseService;
import com.yuyue.service.BsBookcaseinfoService;
import com.yuyue.service.BsBookcellinfoService;
import com.yuyue.service.BsBookinstoreService;
import com.yuyue.service.RsRolepermissionService;
import com.yuyue.service.RsUserwarehouseService;
import com.yuyue.util.Lists;
import com.yuyue.util.Page4Navigator;
import com.yuyue.util.Result;
import com.yuyue.util.UpdateUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.sf.json.JSONObject;

/**
 * 机柜管理
 * 系统管理
 *
 */
@RestController
@RequestMapping("/system")
@Api(value="系统管理接口", tags="系统管理接口")
public class SystemController {

	@Autowired
	private BePermissionService bePermissionService;
	
	@Autowired
	private RsRolepermissionService rsRolepermissionService;
	
	@Autowired
	private BeRoleService beRoleService;
	
	@Autowired
	private BeInstitutionService beInstitutionService;
	
	@Autowired
	private BeUserService beUserService;
	
	@Autowired
	private BeWarehouseService beWarehouseService;
	
	@Autowired
	private BeDepartmentService beDepartmentService;
	
	@Autowired
	private BsBookcaseinfoService bsBookcaseinfoService;
	
	@Autowired
	private RsUserwarehouseService rsUserwarehouseService;
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate = null;
	
	@Autowired
	private BsBookcellinfoService bsBookcellinfoService;
	
	@Autowired
	private BeOperationlogService beOperationlogService;
	
	@Autowired
	private BsBookinstoreService bsBookinstoreService;
	
	public BeUser beUserTransform(User user) {
		BeUser beUser = beUserService.getById(user.getUid());
		return beUser;
	}
	
	public BeUser getUser(HttpSession session) {
		String u = stringRedisTemplate.opsForValue().get(session.getId().toString());
		JSONObject json = JSONObject.fromObject(u);
		User user = (User) JSONObject.toBean(json,User.class);
		return beUserTransform(user);
	}
	
	@GetMapping("/bookcaseinfos")
	@ApiOperation(value = "机柜查询", notes="机柜查询")
	public Object listBookcaseinfo(
			@ApiParam(name="start",required=false)@RequestParam(value = "start",defaultValue = "0")int start,
			@ApiParam(name="size",required=false)@RequestParam(value="size",defaultValue="10")int size){
		start = start<0?0:start;
		return bsBookcaseinfoService.list(start, size, 5);
	}
	
	@GetMapping("/bookcaseinfos/{caseId}")
	@ApiOperation(value = "获得机柜", notes="获得机柜")
	public Object getBookcaseinfo(@PathVariable(name = "caseId")int caseId) {
		return bsBookcaseinfoService.get(caseId);
	}
	
	@PostMapping("/bookcaseinfos")
	@ApiOperation(value = "添加机柜", notes="添加机柜")
	public Object addBookcaseinfo(@RequestBody BsBookcaseinfo bbc ,HttpSession session) throws Exception {
		int flag = bsBookcaseinfoService.add(bbc, getUser(session));
		if(flag > 0)
			return Result.success();
		return Result.fail("增加失败");
	}
	
	@PutMapping("/bookcaseinfos")
	@ApiOperation(value = "更新机柜", notes="更新机柜")
	public Object updateBookcaseinfo(@RequestBody BsBookcaseinfo bbc ,HttpSession session) throws Exception {
		int flag = bsBookcaseinfoService.update(bbc, getUser(session));
		if(flag > 0)
			return Result.success();
		return Result.fail("更新失败");
	}
	
	@DeleteMapping("/bookcaseinfos/{caseId}")
	@ApiOperation(value = "添加机柜", notes="添加机柜")
	public Object deleteBookcaseinfo(@PathVariable("caseId")int caseId, HttpSession session) throws Exception {
		int flag = bsBookcaseinfoService.delete(caseId, getUser(session));
		if(flag > 0)
			return Result.success();
		return Result.fail("更新失败");
	}
	
	@GetMapping("/bookcellinfos/{caseId}")
	@ApiOperation(value = "获得机柜格子", notes="获得机柜格子")
	public Object list(
			@ApiParam(name="start",required=false)@RequestParam(value = "start",defaultValue = "0")int start,
			@ApiParam(name="size",required=false)@RequestParam(value="size",defaultValue="10")int size,
			@PathVariable("caseId")int caseId) {
		BsBookcaseinfo bbc = bsBookcaseinfoService.getBookcaseinfo(caseId);
		return bsBookcellinfoService.list(start, size, 5, bbc);
	}
	
	/**
	 * 权限集合
	 * @return
	 */
	@GetMapping("/permissions")
	@ApiOperation(value="权限集合", notes="权限集合")
	public List<BePermission> listPermission(){
		return bePermissionService.list();
	}
	
	/**
	 * 角色权限集合
	 * @return
	 */
	@GetMapping("/rolePermissions")
	@ApiOperation(value="角色权限集合", notes="角色权限集合")
	public List<RsRolepermission> listRolePermission(){
		return rsRolepermissionService.list();
	}
	
	/**
	 * 获取一个角色的全部权限  角色权限
	 */
	@GetMapping("/permissions/{roleName}")
	@ApiOperation(value="获取一个角色的全部权限  角色权限", notes="通过url的roleName来获取一个角色的全部权限  角色权限")
	public Object getPermission(@PathVariable("roleName")String roleName) {
		if(roleName==null||"".equals(roleName))
			return Result.fail("未输入角色");
		BeRole br = beRoleService.getPermission(roleName);
		if(br == null)
			return Result.fail("不存在此角色");
		rsRolepermissionService.setRolepermission(br);
		return br;
	}
	
	/**
	 * 机构管理
	 * @return
	 */
	@GetMapping("/institutions")
	@ApiOperation(value="机构管理", notes="机构管理")
	public List<BeInstitution> listInstitution(){
		return beInstitutionService.list();
	}
	
	/**
	 * 增加机构
	 * @return
	 */
	@PostMapping("/institutions")
	@ApiOperation(value="增加机构", notes="增加机构")
	public Object addInstitution(@RequestBody BeInstitution beInstitution) {
		int flag = beInstitutionService.add(beInstitution);
		if(flag > 0)
			return Result.success();
		return Result.fail("增加失败");
	}
	
	/**
	 * 删除机构
	 * @return
	 */
	@DeleteMapping("/institutions/{id}")
	@ApiOperation(value="删除机构", notes="删除机构")
	public Object delete(@PathVariable(name="id")int id) {
		if(id<=0)
			return Result.fail("删除失败");
		int flag = beInstitutionService.delete(id);
		if(flag>0)
			return Result.success();
		return Result.fail("删除失败");
	}
	
	/**
	 * 更新机构
	 * @return
	 */
	@PutMapping("/institutions")
	@ApiOperation(value="更新机构", notes="更新机构")
	public Object update(@RequestBody BeInstitution beInstitution) {
		int flag = beInstitutionService.update(beInstitution);
		if(flag > 0)
			return Result.success();
		return Result.fail("增加失败");
	}
	
	@GetMapping("/institutions/{caseId}")
	@ApiOperation(value="获得机构", notes="获得机构")
	public Object getInstitution(int caseId) {
		return beInstitutionService.get(caseId);
	}
	
	/**
	 * 员工管理列表
	 * @return
	 */
	@GetMapping("/users")
	@ApiOperation(value="员工管理查询", notes="员工管理查询")
	public Page4Navigator<BeUser> listUser(
			@ApiParam(name="start",required=false)@RequestParam(value = "start", defaultValue = "0") int start,
			@ApiParam(name="size",required=false)@RequestParam(value = "size", defaultValue = "10") int size){
		start = start>0?start:0;
		return beUserService.list(start, size, 5);
	}
	
	/**
	 * 新增员工所需信息
	 * @return
	 */
	@GetMapping("/systeminfos")
	@ApiOperation(value = "新增员工所需信息", notes="新增员工所需信息")
	public Object systeminfo() {
		List<BeInstitution> bis = beInstitutionService.listinfo();
		List<BeDepartment> bds = beDepartmentService.list();
		List<BeRole> brs = beRoleService.list();
		Map<String, Object> map = new HashMap<>();
		map.put("institution", bis);
		map.put("department", bds);
		map.put("role", brs);
		return map;
	}
	
	/**
	 * 增加书柜所需信息
	 * @return
	 */
	@GetMapping("/userinfos")
	@ApiOperation(value = "增加书柜所需信息", notes="增加书柜所需信息")
	public Object userinfos() {
		return beUserService.listUser();
	}
	
	/**
	 * 新增员工
	 * @param beUser
	 * @return
	 */
	@PostMapping("/users")
	@ApiOperation(value = "新增员工", notes = "新增员工")
	public Object addUser(@RequestBody BeUser beUser) {
		int flag = beUserService.add(beUser);
		if(flag > 0)
			return Result.success();
		return Result.fail("新增失败");
	}
	
	/**
	 * 员工信息获取
	 * @param id
	 * @return
	 */
	@GetMapping("/users/{id}")
	@ApiOperation(value="员工信息获取", notes="员工信息获取")
	public Object getUser(@PathVariable(name="id")int id) {
		return beUserService.getById(id);
	}
	
	/**
	 * 修改员工信息
	 * @param beUser
	 * @param session
	 * @return
	 */
	@PutMapping("/users")
	@ApiOperation(value = "修改员工信息", notes = "修改员工信息")
	public Object updateUser(@RequestBody BeUser beUser) {
		int flag = beUserService.update(beUser);
		if(flag > 0)
			return Result.success();
		return Result.fail("修改失败");
	}
	
	@DeleteMapping("/users/{uid}")
	@ApiOperation(value = "删除员工", notes = "删除员工")
	public Object deleteUser(@PathVariable("uid")int uid) {
		int flag = beUserService.delete(uid);
		if(flag > 0)
			return Result.success();
		return Result.fail("删除失败");
	}
	
	/**
	 * 仓库查询
	 * @param start
	 * @param size
	 * @param keyword
	 * @param id
	 * @return
	 */
	@GetMapping("/warehouses")
	@ApiOperation(value="仓库查询", notes="仓库查询")
	public Page4Navigator<BeWarehouse> listWarehouse(
			@ApiParam(name="start",required=false)@RequestParam(value = "start", defaultValue = "0") int start,
			@ApiParam(name="size",required=false)@RequestParam(value = "size", defaultValue = "10") int size, 
			@ApiParam(name="keyword",required=false)@RequestParam(value = "keyword", defaultValue = "")String keyword, 
			@ApiParam(name="id",required=false)@RequestParam(value = "id", defaultValue = "0")int id){
		start = start>0?start:0;
		if(id <= 0)
			return beWarehouseService.list(start, size, 5, keyword);
		return beWarehouseService.list(start, size, 5, keyword, id);
	}
	
	/**
	 * 增加仓库
	 * @param beWarehouse
	 * @return
	 */
	@PostMapping("/warehouses")
	@ApiOperation(value="增加仓库", notes="用json来增加仓库")
	public Object addWarehouse(@RequestBody BeWarehouse beWarehouse) {
		if(beWarehouse == null)
			return Result.fail("未获取到参数");
		if(beWarehouse.getWarehouseCode()==null||beWarehouse.getLatitude()==null||beWarehouse.getLongitude()==null)
			return Result.fail("仓库编码与经纬度不能为空");
		if(beWarehouse.getCreateTime()==null)
			beWarehouse.setCreateTime(new Date());
		beWarehouse.setUpdateTime(new Date());
		if(beWarehouse.getDepartmentId()>0&&beWarehouse.getDepartmentId()!=null) {
			BeDepartment bd = new BeDepartment();
			bd.setId(beWarehouse.getDepartmentId());
			beWarehouse.setBeDepartment(bd);
		}
		int flag = beWarehouseService.addWarehouse(beWarehouse);
		if(flag > 0)
			return Result.success();
		return Result.fail("添加失败");
	}
	
	/**
	 * 更新仓库
	 * @param beWarehouse
	 * @return
	 */
	@PutMapping("/warehouses")
	@ApiOperation(value="更新仓库", notes="用json来更新仓库")
	public Object updateWarehouse(@RequestBody BeWarehouse beWarehouse) {
		if(beWarehouse == null)
			return Result.fail("未获取到参数");
		if(beWarehouse.getWarehouseId() == null||beWarehouse.getWarehouseId() <= 0)
			return Result.fail("请输入正确的id");
		BeWarehouse bw = beWarehouseService.getWarehouse(beWarehouse.getWarehouseId());
		UpdateUtil.copyNullProperties(bw, beWarehouse);
		/*if(beWarehouse.getWarehouseCode()==null)
			beWarehouse.setWarehouseCode(bw.getWarehouseCode());
		if(beWarehouse.getLatitude()==null)
			beWarehouse.setLatitude(bw.getLatitude());
		if(beWarehouse.getLongitude()==null)
			beWarehouse.setLongitude(bw.getLongitude());
		if(beWarehouse.getCreateTime()==null)
			beWarehouse.setCreateTime(bw.getCreateTime());*/
		beWarehouse.setUpdateTime(new Date());
		if(beWarehouse.getDepartmentId()>0&&beWarehouse.getDepartmentId()!=null) {
			BeDepartment bd = new BeDepartment();
			bd.setId(beWarehouse.getDepartmentId());
			beWarehouse.setBeDepartment(bd);
		}
		int flag = beWarehouseService.addWarehouse(beWarehouse);
		if(flag > 0)
			return Result.success();
		return Result.fail("更新失败");
	}
	
	/**
	 * 获取仓库
	 * @param warehouseId
	 * @return
	 */
	@GetMapping("/warehouses/{warehouseId}")
	@ApiOperation(value="获取仓库", notes="通过url的warehouseId来获取仓库")
	public Object getWarehouse(@PathVariable("warehouseId")int warehouseId) {
		if(warehouseId <= 0)
			return Result.fail("请输入正确的id");
		return beWarehouseService.getWarehouse(warehouseId);
	}
	
	/**
	 * 删除仓库
	 * @param warehouseId
	 * @return
	 */
	@DeleteMapping("/warehouses/{warehouseId}")
	@ApiOperation(value="删除仓库", notes="通过url的warehouseId来删除仓库")
	public Object deleteWarehouse(@PathVariable("warehouseId")int warehouseId) {
		if(warehouseId <= 0)
			return Result.fail("请输入正确的id");
		int flag = beWarehouseService.deleteWarehouse(warehouseId);
		if(flag > 0)
			return Result.success();
		return Result.fail("删除失败");
	}
	
	/**
	 * 部门信息
	 * @return
	 */
	@GetMapping("/departments")
	@ApiOperation(value="部门信息", notes="部门信息")
	public Object getDepartment() {
		return beDepartmentService.list();
	}
	
	/*@GetMapping("/warehousecases/{uid}")
	@ApiOperation(value="仓库书柜信息", notes="仓库书柜信息")
	public Object getWarehouseCase(@PathVariable(name = "uid")int uid) {
		List<BeWarehouse> bws = beUserService.get(uid);
		List<Warehouse> warehouse = new ArrayList<>();
		for(BeWarehouse bw : bws) {
			Warehouse w = new Warehouse();
			w.setWarehouseId(bw.getWarehouseId());
			w.setWarehouseName(bw.getWarehouseName());
			if(bw.getBsBookcaseinfos()!=null) {
				Map<Integer, String> bookcase = new HashMap<>();
				for(BsBookcaseinfo bbc : bw.getBsBookcaseinfos()) {
					bookcase.put(bbc.getCaseId(), bbc.getCaseName());
				}
				w.setBookcase(bookcase);
			}
			warehouse.add(w);
		}
		return warehouse;
	}*/
	
	/**
	 * 仓库书柜信息 用户所有
	 * @param uid
	 * @return
	 */
	@GetMapping("/warehousecase/{uid}")
	@ApiOperation(value="仓库书柜信息", notes="仓库书柜信息")
	public Object getWarehouseCaseByUid(@PathVariable(name = "uid")int uid) {
		return beUserService.get(uid);
	}
	
	/**
	 * 全部的仓库书柜信息 以及用户拥有的caseId
	 * @param uid
	 * @return
	 */
	@GetMapping("/warehousecases/{uid}")
	@ApiOperation(value="全仓库书柜信息", notes="全仓库书柜信息")
	public Object getWarehouseCase(@PathVariable(name = "uid")int uid) {
		Map<String, Object> map = new HashMap<>();
		map.put("datas", beUserService.get());
		map.put("array", beUserService.getArray(uid));
		return map;
	}
	
	@PostMapping("/warehousecases")
	@ApiOperation(value="更新用户书柜关系", notes="更新用户书柜关系")
	public Object updateWarehouseCase(@RequestBody Lists lists) {
		int flag = rsUserwarehouseService.update(lists);
		if(flag > 0)
			return Result.success();
		return Result.fail("更新失败");
	}
	
	@DeleteMapping("/warehousecases/{userId}/{caseId}")
	@ApiOperation(value="删除用户书柜关系", notes="删除用户书柜关系")
	public Object deleteWarehouseCase(@PathVariable("userId")int userId, @PathVariable("caseId")int caseId) {
		int flag = rsUserwarehouseService.delete(userId, caseId);
		if(flag > 0)
			return Result.success();
		return Result.fail("删除失败");
	}
	
	@GetMapping("/operationlog")
	@ApiOperation(value = "操作记录", notes = "操作记录")
	public Object listOperationlog(
			@ApiParam(name="start",required=false)@RequestParam(value = "start", defaultValue = "0") int start,
			@ApiParam(name="size",required=false)@RequestParam(value = "size", defaultValue = "10") int size) {
		start = start>0?start:0;
		return beOperationlogService.list(start, size, 5);
	}
	
	/*@DeleteMapping("/warehousecases")
	@ApiOperation(value="增加用户书柜关系", notes="增加用户书柜关系")
	public Object deleteWarehouseCase(@RequestBody Lists lists) {
		int flag = rsUserwarehouseService.add(lists);
		if(flag <= 0)
			return Result.fail("增加失败");
		return Result.success();
	}*/
	
	@GetMapping("/bookinstores/{code}")
	@ApiOperation(value = "根据条码获取信息", notes="根据条码获取信息")
	public Object getBookinstore(@PathVariable(name = "code")String code) {
		return bsBookinstoreService.getBsBookinstore(code);
	}
	
}
