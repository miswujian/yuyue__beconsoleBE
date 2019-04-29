package com.yuyue.web;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.yuyue.pojo.BeSensitive;
import com.yuyue.pojo.BsUserdynamic;
import com.yuyue.pojo.BsUserinfo;
import com.yuyue.service.BeSensitiveService;
import com.yuyue.service.BsUserdynamicService;
import com.yuyue.service.BsUserdynamiccmntService;
import com.yuyue.service.BsUserinfoService;
import com.yuyue.util.Lists;
import com.yuyue.util.Page4Navigator;
import com.yuyue.util.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 鱼群管理
 * @author 吴俭
 *
 */
@RestController
@Api(value="鱼群管理接口", tags="鱼群管理接口")
public class ShoalController {

	@Autowired
	private BsUserinfoService bsUserinfoService;
	
	@Autowired
	private BsUserdynamicService bsUserdynamicService;
	
	@Autowired
	private BsUserdynamiccmntService bsUserdynamiccmntService;
	
	@Autowired
	private BeSensitiveService beSensitiveService;
	
	/**
	 * 用户信息集合
	 * @return
	 */
	@GetMapping("/userinfos")
	@ApiIgnore
	public List<BsUserinfo> listUser(){
		return bsUserinfoService.list();
	}
	
	/**
	 * 鱼群动态表 书籍
	 * @return
	 */
	@GetMapping("/bUserdynamics")
	@ApiOperation(value="鱼群动态表 书籍 查询", notes="鱼群动态表 书籍 查询")
	public Page4Navigator<BsUserdynamic> listBUserdynamic(
			@ApiParam(name="start",required=false)@RequestParam(name = "start",defaultValue="0") int start,
			@ApiParam(name="size",required=false)@RequestParam(name = "size",defaultValue="10") int size, 
			@ApiParam(name="bookName",required=false)String bookName, 
			@ApiParam(name="content",required=false)String content, 
			@ApiParam(name="userName",required=false)String userName,
			@ApiParam(name="starttime",required=false)Date starttime, 
			@ApiParam(name="endtime",required=false)Date endtime){
		start = start>0?start:0;
		long time = System.currentTimeMillis();
		if(bookName == null)
			bookName = "";
		if(content == null)
			content = "";
		if(userName == null)
			userName = "";
		if(starttime == null)
			starttime = new Date(time/10);
		if(endtime == null)
			endtime = new Date(time);
		return bsUserdynamicService.list(start, size, 5, bookName, content, userName, starttime, endtime);
	}
	
	/**
	 * 鱼群动态表 评论
	 * @return
	 */
	@GetMapping("/pUserdynamic")
	@ApiOperation(value="鱼群动态表 评论 查询", notes="鱼群动态表 评论 查询")
	public Page4Navigator<BsUserdynamic> listPUserdynamic(
			@ApiParam(name="start",required=false)@RequestParam(name = "start",defaultValue="0") int start,
			@ApiParam(name="size",required=false)@RequestParam(name = "size",defaultValue="10") int size, 
			@ApiParam(name="content",required=false)String content, 
			@ApiParam(name="userName",required=false)String userName,
			@ApiParam(name="starttime",required=false)Date starttime, 
			@ApiParam(name="endtime",required=false)Date endtime){
		start = start>0?start:0;
		long time = System.currentTimeMillis();
		if(content == null)
			content = "";
		if(userName == null)
			userName = "";
		if(starttime == null)
			starttime = new Date(time/10);
		if(endtime == null)
			endtime = new Date(time);
		return bsUserdynamicService.list(start, size, 5, content, userName, starttime, endtime);
	}
	
	/**
	 * 删除动态信息 会产生对应的级联删除
	 * @param lists
	 * @return
	 */
	@PostMapping(value = "/userdynamic")
	@ApiOperation(value="删除动态信息", notes="删除动态信息 给dynamicIds的json集合 会产生级联删除")
	public Object deleteUserdynamic(@RequestBody Lists lists) {
		int size = lists.getDynamicIds().size();
		int flag = bsUserdynamicService.deletemore(lists.getDynamicIds());
		if(flag <= 0)
			return Result.fail("全部删除失败");
		if(flag != size)
			return Result.fail("删除失败"+(size-flag)+"条");
		return Result.success();
	}
	
	/**
	 * 删除动态评论
	 * @param lists
	 * @return
	 */
	@PostMapping(value = "/userdynamiccmnts")
	@ApiOperation(value="删除动态评论", notes="删除动态评论 给commentIds的json集合")
	public Object deleteUserdynamiccmnt(@RequestBody Lists lists) {
		int size = lists.getCommentIds().size();
		int flag = bsUserdynamiccmntService.deletemore(lists.getCommentIds());
		if(flag <= 0)
			return Result.fail("全部删除失败");
		if(flag != size)
			return Result.fail("删除失败"+(size-flag)+"条");
		return Result.success();
	}
	
	/**
	 * 鱼群动态评论表
	 * @return
	 */
	@GetMapping("/userdynamiccmnts")
	@ApiOperation(value="鱼群动态评论表查询", notes="鱼群动态评论表查询")
	public Object listUserdynamiccmnt(
			@ApiParam(name="start",required=false)@RequestParam(name = "start",defaultValue="0") int start,
			@ApiParam(name="size",required=false)@RequestParam(name = "size",defaultValue="10") int size, 
			@ApiParam(name="content",required=false)String content, 
			@ApiParam(name="userName",required=false)String userName,
			@ApiParam(name="starttime",required=false)Date starttime, 
			@ApiParam(name="endtime",required=false)Date endtime){
		start = start>0?start:0;
		long time = System.currentTimeMillis();
		if(content == null)
			content = "";
		if(userName == null)
			userName = "";
		if(starttime == null)
			starttime = new Date(time/10);
		if(endtime == null)
			endtime = new Date(time);
		return bsUserdynamiccmntService.list(start, size, 5, content, userName, starttime, endtime);
	}
	
	/**
	 * 获取某动态的动态评论表
	 * @param start
	 * @param size
	 * @param dynamicId
	 * @param content
	 * @param userName
	 * @param starttime
	 * @param endtime
	 * @return
	 */
	@GetMapping("/userdynamiccmnts/{dynamicId}")
	@ApiOperation(value="获取某动态的动态评论表", notes="获取某动态的动态评论表")
	public Object listUserdynamiccmntByDynamicId(
			@ApiParam(name="start",required=false)@RequestParam(name = "start",defaultValue="0") int start,
			@ApiParam(name="size",required=false)@RequestParam(name = "size",defaultValue="10") int size, 
			@PathVariable("dynamicId")String dynamicId, 
			@ApiParam(name="content",required=false)String content, 
			@ApiParam(name="userName",required=false)String userName, 
			@ApiParam(name="starttime",required=false)Date starttime, 
			@ApiParam(name="endtime",required=false)Date endtime) {
		start = start>0?start:0;
		long time = System.currentTimeMillis();
		BsUserdynamic bu = bsUserdynamicService.getUserdynamic(dynamicId);
		if(bu == null)
			return Result.fail("此评论不存在");
		if(content == null)
			content = "";
		if(userName == null)
			userName = "";
		if(starttime == null)
			starttime = new Date(time/10);
		if(endtime == null)
			endtime = new Date(time);
		return bsUserdynamiccmntService.findByDynamicId(start, size, 5, dynamicId, content, userName, starttime, endtime);
	}
	
	/**
	 * 展现敏感词库
	 * @return
	 */
	@GetMapping("/sensitives")
	@ApiOperation(value="敏感词库查询", notes="敏感词库查询")
	public List<BeSensitive> listSensitive(){
		return beSensitiveService.list();
	}
	
	/**
	 * 增加敏感词
	 * @param words
	 * @return
	 * @throws IOException
	 */
	/*@PostMapping(value="addSensitive")
	public Object addSensitive(@RequestBody ArrayList<BeSensitive> words) throws IOException{
		if(words.isEmpty())
			return Result.fail("请输入增加的词");
		int size = words.size();
		int flag = beSensitiveService.addSensitives(words);
		if(size != flag)
			return Result.fail("添加失败"+(size-flag)+"个");
		return Result.success();
	}*/
	
	/**
	 * 增加敏感词库
	 * @param words
	 * @return
	 */
	@PostMapping(value="sensitives")
	@ApiOperation(value="增加敏感词库", notes="用josn增加敏感词库")
	public Object addSensitive(@RequestBody BeSensitive words) throws IOException{
		if(words == null)
			return Result.fail("请输入增加的词");
		int flag = beSensitiveService.addSensitive(words);
		if(flag <= 0)
			return Result.fail("添加失败");
		return Result.success(flag);
	}
	
	/**
	 * 删除敏感词库
	 * @param id
	 * @return
	 */
	@DeleteMapping("/sensitives/{id}")
	@ApiOperation(value="删除敏感词库", notes="通过url的id来删除敏感词库")
	public Object deleteSensitive(@PathVariable("id")int id) {
		int flag = beSensitiveService.deleteSensitive(id);
		if(flag <= 0)
			return Result.fail("删除失败");
		return Result.success();
	}
	
}
