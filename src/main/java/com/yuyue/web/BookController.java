package com.yuyue.web;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yuyue.pojo.BsBookinfo;
import com.yuyue.pojo.BsBookinstore;
import com.yuyue.pojo.BsPublishinfo;
import com.yuyue.service.BsBookinfoService;
import com.yuyue.service.BsBookinstoreService;
import com.yuyue.service.BsPublishinfoService;
import com.yuyue.util.Page4Navigator;
import com.yuyue.util.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 商品管理操作部分还没实现  书籍新增和修改的图片上传还没写  查询已经实现
 * 书籍管理
 * @author 吴俭
 *
 */
@RestController
@Api(value="书籍管理接口",tags={"书籍管理接口"})
public class BookController {
	
	private static Logger log = LoggerFactory.getLogger(BookController.class);

	@Autowired
	private BsBookinfoService bsBookinfoService;
	
	@Autowired
	private BsPublishinfoService bsPublishinfoService;
	
	@Autowired
	private BsBookinstoreService  bsBookinstoreService;
	
	/**
	 * 书目库信息查询
	 * @param start
	 * @param size
	 * @return
	 */
	@GetMapping(value="/bookinfos")
	@ApiOperation(value="书目库信息查询", notes="书目库信息查询")
	public Page4Navigator<BsBookinfo> listBookinfos(
			@ApiParam(name="start",required=false)@RequestParam(value = "start", defaultValue = "0") int start,
			@ApiParam(name="size",required=false)@RequestParam(value = "size", defaultValue = "10") int size,
			@ApiParam(name="keyword",required=false)@RequestParam(value = "keyword", defaultValue = "")String keyword){
		start = start<0?0:start;
		Page4Navigator<BsBookinfo> bbis = bsBookinfoService.list(start,size,5,keyword);
		bsBookinstoreService.setBsBookinstore(bbis.getContent());
		log.info("wujian"+"增加了书籍信息");
		return bbis;
	}
	
	/**
	 * 获取某书信息
	 * @param bookinfoId
	 * @return
	 */
	@ApiOperation(value="获取某书信息", notes="根据url的bookinfoId来获取某书信息")
	@GetMapping("/bookinfos/{bookinfoId}")
	public Object getBookinfo(@PathVariable(value="bookinfoId")int bookinfoId) throws Exception {
		if(bookinfoId == 0 )
			return Result.fail("请输入书籍bookinfo");
		BsBookinfo bbi = bsBookinfoService.getBook(bookinfoId);
		if(bbi == null)
			return Result.fail("没有此书籍");
		return bbi;
	}
	
	/**
	 * 增加书籍
	 * @param bbi
	 * @return
	 */
	@PostMapping(value="/bookinfos")
	@ApiOperation(value="增加书籍", notes="传json增加书籍")
	public Object addBookinfo(@RequestBody BsBookinfo bbi) throws IOException{
		if(bbi == null)
			return Result.fail("未获取到输入值");
		int flag = bsBookinfoService.addbook(bbi);
		if(flag <=0)
			return Result.fail("添加失败");
		return Result.success();
	}
	
	/**
	 * 更新书籍
	 * @param bbi
	 * @return
	 */
	@PutMapping(value="/bookinfos")
	@ApiOperation(value="更新书籍", notes="传json更新书籍")
	public Object updateBookinfo(@RequestBody BsBookinfo bbi) throws IOException{
		if(bbi == null)
			return Result.fail("未获取到输入值");
		if(!(bbi.getBookinfoId()>0))
			return Result.fail("请输入正确的bookinfoId");
		int flag = bsBookinfoService.updateBook(bbi);
		if(flag <=0)
			return Result.fail("更新失败");
		return Result.success();
	}
	
	/**
	 * 删除书籍
	 * @param bookinfoId
	 * @return
	 * @throws IOException
	 */
	@DeleteMapping("/bookinfos/{bookinfoId}")
	@ApiOperation(value="删除书籍", notes="通过url的bookinfoId来删除书籍")
	public Object deleteBookinfo(@PathVariable("bookinfoId")int bookinfoId) throws IOException{
		if(bookinfoId <= 0)
			return Result.fail("请输入正确的bookinfoId");
		int flag = bsBookinfoService.deletebook(bookinfoId);
		if(flag <= 0)
			return Result.fail("删除失败");
		return Result.success();
	}
	
	/**
	 * 出版社维护信息集合
	 * @param start
	 * @param size
	 * @return
	 */
	@GetMapping("/publishinfos")
	@ApiOperation(value="出版社维护信息查询", notes="出版社维护信息查询")
	public Page4Navigator<BsPublishinfo> listPublishinfo(
			@ApiParam(name="start",required=false)@RequestParam(value = "start",defaultValue = "0") int start,
			@ApiParam(name="size",required=false)@RequestParam(value="size",defaultValue="10")int size,
			@ApiParam(name="keyword",required=false)@RequestParam(value = "keyword", defaultValue = "")String keyword){
		start = start<0?0:start;
		return bsPublishinfoService.list(start, size, 5, keyword);
	}
	
	/**
	 * 获得出版社维护信息
	 * @param pubId
	 * @return
	 */
	@GetMapping("/publishinfos/{pubId}")
	@ApiOperation(value="获得出版社维护信息", notes="通过url的pubId来获得出版社维护信息")
	public Object getPublishinfo(@PathVariable("pubId")int pubId) {
		if(pubId == 0)
			return Result.fail("请输入出版社pubId");
		BsPublishinfo bpi = bsPublishinfoService.getPublishinfo(pubId);
		if(bpi == null)
			return Result.fail("此出版社不存在");
		return bpi;
	}
	
	/**
	 * 添加出版社信息
	 * @param bpi
	 * @return
	 */
	@PostMapping(value = "/publishinfos")
	@ApiOperation(value="添加出版社信息", notes="用json添加出版社信息")
	public Object addPublishinfo(@RequestBody BsPublishinfo bpi) throws IOException{
		if(bpi == null)
			return Result.fail("未获取到输入值");
		int flag = bsPublishinfoService.addPublishinfo(bpi);
		if(flag <=0)
			return Result.fail("添加失败");
		return Result.success();
	}
	
	/**
	 * 更新出版社信息
	 * @param bpi
	 * @return
	 */
	@PutMapping(value="/publishinfos")
	@ApiOperation(value="更新出版社信息", notes="用json更新出版社信息")
	public Object updatePublishinfo(@RequestBody BsPublishinfo bpi) throws IOException{
		if(bpi == null)
			return Result.fail("未获取到输入值");
		if(!(bpi.getPubId() >0))
			return Result.fail("请输入正确的pubId");
		int flag = bsPublishinfoService.updatePublishinfo(bpi);
		if(flag <=0)
			return Result.fail("更新失败");
		return Result.success();
	}
	
	/**
	 * 删除出版社信息
	 * @param pubId
	 * @return
	 */
	@DeleteMapping("/publishinfos/{pubId}")
	@ApiOperation(value="删除出版社信息", notes="通过url的pubId来删除出版社信息")
	public Object deletePublishinfo(@PathVariable("pubId")int pubId) {
		if(pubId <= 0)
			return Result.fail("请输入正确的pubId");
		int flag = bsPublishinfoService.deletePublishinfo(pubId);
		if(flag <= 0)
			return Result.fail("删除失败");
		return Result.success();
	}
	
	/**
	 * 商品管理集合
	 * @param start
	 * @param size
	 * @return
	 */
	@GetMapping("/bookinstores")
	@ApiOperation(value="商品管理查询", notes="商品管理查询")
	public Page4Navigator<BsBookinstore> listBsBookinstore(
			@ApiParam(name="start",required=false)@RequestParam(value="start", defaultValue="0")int start, 
			@ApiParam(name="size",required=false)@RequestParam(value="size",defaultValue="10")int size,
			@ApiParam(name="keyword",required=false)@RequestParam(value = "keyword", defaultValue = "")String keyword){
		start = start<0?0:start;
		return bsBookinstoreService.list(start, size, 5, keyword);
	}
	
}
