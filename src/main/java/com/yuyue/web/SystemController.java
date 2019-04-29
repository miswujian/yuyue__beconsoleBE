package com.yuyue.web;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yuyue.pojo.BeDepartment;
import com.yuyue.pojo.BeInstitution;
import com.yuyue.pojo.BePermission;
import com.yuyue.pojo.BeRole;
import com.yuyue.pojo.BeUser;
import com.yuyue.pojo.BeWarehouse;
import com.yuyue.pojo.RsRolepermission;
import com.yuyue.service.BeDepartmentService;
import com.yuyue.service.BeInstitutionService;
import com.yuyue.service.BePermissionService;
import com.yuyue.service.BeRoleService;
import com.yuyue.service.BeUserService;
import com.yuyue.service.BeWarehouseService;
import com.yuyue.service.RsRolepermissionService;
import com.yuyue.util.Page4Navigator;
import com.yuyue.util.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 员工管理的增加和修改  机柜管理和仓库管理
 * 系统管理
 * @author 吴俭
 *
 */
@RestController
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
		if(flag <= 0)
			return Result.fail("添加失败");
		return Result.success();
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
		if(beWarehouse.getWarehouseCode()==null)
			beWarehouse.setWarehouseCode(bw.getWarehouseCode());
		if(beWarehouse.getLatitude()==null)
			beWarehouse.setLatitude(bw.getLatitude());
		if(beWarehouse.getLongitude()==null)
			beWarehouse.setLongitude(bw.getLongitude());
		if(beWarehouse.getCreateTime()==null)
			beWarehouse.setCreateTime(bw.getCreateTime());
		beWarehouse.setUpdateTime(new Date());
		if(beWarehouse.getDepartmentId()>0&&beWarehouse.getDepartmentId()!=null) {
			BeDepartment bd = new BeDepartment();
			bd.setId(beWarehouse.getDepartmentId());
			beWarehouse.setBeDepartment(bd);
		}
		int flag = beWarehouseService.addWarehouse(beWarehouse);
		if(flag <= 0)
			return Result.fail("更新失败");
		return Result.success();
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
		if(flag <= 0)
			return Result.fail("删除失败");
		return Result.success();
	}
	
	/**
	 * 部门信息
	 * @return
	 */
	@GetMapping("departments")
	@ApiOperation(value="部门信息", notes="部门信息")
	public Object getDepartment() {
		return beDepartmentService.list();
	}
	
}
