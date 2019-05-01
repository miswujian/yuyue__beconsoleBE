package com.yuyue.web;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.yuyue.pojo.BeAdvertisement;
import com.yuyue.pojo.BsBookcategory;
import com.yuyue.pojo.BsBookinfo;
import com.yuyue.pojo.BsBooksubject;
import com.yuyue.pojo.BsPicture;
import com.yuyue.pojo.RsBookinsubject;
import com.yuyue.properties.FileUploadProperteis;
import com.yuyue.service.BeAdvertisementService;
import com.yuyue.service.BsBookcategoryService;
import com.yuyue.service.BsBookinfoService;
import com.yuyue.service.BsBooksubjectService;
import com.yuyue.service.BsPictureService;
import com.yuyue.service.RsBookinsubjectService;
import com.yuyue.util.ImageUtil;
import com.yuyue.util.Page4Navigator;
import com.yuyue.util.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 网站管理 广告管理还差测试
 * 
 * @author 吴俭
 *
 */
@RestController
@Api(value="网站管理接口", tags="网站管理接口")
public class WebsiteController {

	private String path = "119.3.231.11:8080/yuyue/img/";
	//private String path = "localhost:8080/yuyue/img/";
	@Autowired
	private BsBooksubjectService bsBooksubjectService;

	@Autowired
	private RsBookinsubjectService rsBookinsubjectService;

	@Autowired
	private BsBookcategoryService bsBookcategoryService;

	@Autowired
	private BsPictureService bsPictureService;

	@Autowired
	private BsBookinfoService bsBookinfoService;
	
	@Autowired
    private FileUploadProperteis fileUploadProperteis;
	
	@Autowired
	private BeAdvertisementService beAdvertisementService;

	/**
	 * 专题设置
	 * 
	 * @param start
	 * @param size
	 * @return
	 */
	@GetMapping("/booksubjects")
	@ApiOperation(value="专题设置查询", notes="专题设置查询")
	public Page4Navigator<BsBooksubject> subject(
			@ApiParam(name="start",required=false)@RequestParam(value = "start", defaultValue = "0") int start,
			@ApiParam(name="size",required=false)@RequestParam(value = "size", defaultValue = "5") int size) {
		start = start < 0 ? 0 : start;
		return bsBooksubjectService.list(start, size, 5);
	}

	/**
	 * 增加专题
	 * 
	 * @param bsBooksubject
	 * @return
	 */
	@PostMapping(value = "/booksubjects")
	@ApiOperation(value="增加专题", notes="用json增加专题")
	public Object addSubject(@RequestBody BsBooksubject bsBooksubject) throws IOException {
		if (bsBooksubject == null)
			return Result.fail("未获取到输入值");
		bsBooksubject.setCreateTime(new Date());
		bsBooksubject.setLimitNum(5);
		int flag = bsBooksubjectService.addSubject(bsBooksubject);
		if (flag <= 0)
			return Result.fail("增加失败");
		return Result.success();
	}

	/**
	 * 更新专题
	 * 
	 * @param bsBooksubject
	 * @return
	 */
	@PutMapping(value = "/booksubject")
	@ApiOperation(value="更新专题", notes="用json来更新专题")
	public Object updateSubject(@RequestBody BsBooksubject bsBooksubject) throws IOException {
		if (bsBooksubject == null)
			return Result.fail("未获取到输入值");
		if (bsBooksubject.getBooksubjectId() <= 0)
			return Result.fail("请输入正确的booksubjectId");
		BsBooksubject bbs = bsBooksubjectService.getSubject(bsBooksubject.getBooksubjectId());
		bbs.setSort(bsBooksubject.getSort());
		bbs.setSubjectName(bsBooksubject.getSubjectName());
		bbs.setIsShow(bsBooksubject.getIsShow());
		bbs.setUpdateTime(new Date());
		int flag = bsBooksubjectService.updateSubject(bbs);
		if (flag <= 0)
			return Result.fail("更新失败");
		return Result.success();
	}

	/**
	 * 删除专题
	 * 
	 * @param booksubjectId
	 * @return
	 */
	@DeleteMapping("/booksubjects/{booksubjectId}")
	@ApiOperation(value="删除专题", notes="通过url的booksubjectId来删除专题")
	public Object deleteSubject(@PathVariable("booksubjectId")int booksubjectId) {
		int flag = bsBooksubjectService.deleteSubject(booksubjectId);
		if (flag <= 0)
			return Result.fail("删除失败");
		return Result.success();
	}

	/**
	 * 获得专题内容
	 * 
	 * @param bsBooksubject
	 * @return
	 */
	@GetMapping("/bookinsubjects/{booksubjectId}")
	@ApiOperation(value="获得专题内容", notes="通过url的booksubjectId来获得专题内容")
	public Object getBooks(@PathVariable("booksubjectId")int booksubjectId) {
		BsBooksubject bsBooksubject = new BsBooksubject();
		bsBooksubject.setBooksubjectId(booksubjectId);
		HashMap<String, Object> map = new HashMap<>();
		List<RsBookinsubject> rbss = rsBookinsubjectService.getByBsBooksubject(bsBooksubject);
		BsBooksubject subject = bsBooksubjectService.getSubject(bsBooksubject.getBooksubjectId());
		map.put("subject", subject);
		map.put("list", rbss);
		return map;
	}

	/**
	 * 删除专题与书籍的关系
	 * 
	 * @param bookinsubjectId
	 * @return
	 */
	@DeleteMapping("/bookinsubjects/{bookinsubjectId}")
	@ApiOperation(value="删除专题与书籍的关系", notes="通过url的bookinsubjectId来删除专题与书籍的关系")
	public Object deleteBookinsubject(@PathVariable("bookinsubjectId")int bookinsubjectId) throws IOException {
		int flag = rsBookinsubjectService.delete(bookinsubjectId);
		if (flag <= 0)
			return Result.fail("删除失败");
		return Result.success();
	}

	/**
	 * 书籍分类和及其子分类
	 * 
	 * @return
	 */
	@GetMapping("/bookcategories")
	@ApiOperation(value="书籍分类和及其子分类", notes="书籍分类和及其子分类查询")
	public Object listBookcategory() {
		return bsBookcategoryService.list();
	}

	/**
	 * 获得书籍
	 * @param bsBookcategory
	 * @param bookName
	 * @return
	 */
	@GetMapping("/bookinfo/{categoryId}")
	@ApiOperation(value="获得书籍", notes="通过url的categoryId来获得书籍")
	public Object getByCategoryAndBookName(@PathVariable("categoryId")String categoryId,
			@ApiParam(name="bookName",required=false)@RequestParam(value = "bookName", defaultValue = "") String bookName) {
		if (categoryId == null||Integer.parseInt(categoryId)<=0)
			return Result.fail("分类id出错，查询失败");
		BsBookcategory bsBookcategory = new BsBookcategory();
		bsBookcategory.setCategoryId(categoryId);
		return bsBookinfoService.getByBookcategoryAndBookName(bsBookcategory, bookName);
	}

	/**
	 * bsBookinfoId bsBooksubjectId 增加书籍和主题的关系
	 * 
	 * @param rsBookinsubject
	 * @return
	 */
	@PostMapping("/bookinsubjects")
	@ApiOperation(value="增加书籍和主题的关系", notes="增加书籍和主题的关系")
	public Object addBookinsubject(@RequestBody BsBookinfo bsBookinfo, @RequestBody BsBooksubject bsBooksubject) {
		if (bsBookinfo == null || bsBooksubject == null)
			return Result.fail("未获取到输入值");
		RsBookinsubject rsBookinsubject = new RsBookinsubject();
		rsBookinsubject.setBsBookinfo(bsBookinfo);
		rsBookinsubject.setBsBooksubject(bsBooksubject);
		int flag = rsBookinsubjectService.addBookinsubject(rsBookinsubject);
		if (flag <= 0)
			return Result.fail("增加失败");
		return Result.success();
	}

	/**
	 * banner管理
	 * 
	 * @param start
	 * @param size
	 * @return
	 */
	@GetMapping("/pictures")
	@ApiOperation(value="banner管理", notes="banner管理查询")
	public Page4Navigator<BsPicture> listPicture(
			@ApiParam(name="start",required=false)@RequestParam(value = "start", defaultValue = "0") int start,
			@ApiParam(name="size",required=false)@RequestParam(value = "size", defaultValue = "10") int size,
			@ApiParam(name="type",required=false)@RequestParam(value = "type", defaultValue = "0")byte type,
			@ApiParam(name="keyword",required=false)@RequestParam(value = "keyword", defaultValue = "")String keyword) {
		start = start < 0 ? 0 : start;
		return bsPictureService.list(start, size, 5, type, keyword);
	}
	
	@GetMapping("/pictures/{picId}")
	@ApiOperation(value = "获取banner", notes="根据picId获取banner")
	public Object getPicture(@PathVariable("picId")int picId) {
		if(picId<=0)
			return Result.fail("请输入picId");
		BsPicture bp = bsPictureService.get(picId);
		if(bp==null)
			return Result.fail("没有此banner");
		return bp;
	}

	/**
	 * 添加banner
	 * @param image
	 * @param bsPicture
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@PostMapping("/pictures")
	@ApiOperation(value="添加banner", notes="添加banner")
	public Object addPicture(@ApiParam(name="image",required=false)MultipartFile image, 
			BsPicture bsPicture) throws IOException {
		saveOrUpdateOrDeleteImageFile(image, bsPicture, 1);
		int flag = bsPictureService.add(bsPicture);
		if (flag <= 0)
			return Result.fail("增加失败");
		return Result.success();
	}
	
	/**
	 * 删除banner
	 * @param picId
	 * @return
	 * @throws IOException
	 */
	@DeleteMapping("/pictures/{picId}")
	@ApiOperation(value="删除banner", notes="通过url的picId来删除banner")
	public Object deletePicture(@PathVariable("picId") int picId) throws IOException {
		BsPicture bp = bsPictureService.get(picId);
		saveOrUpdateOrDeleteImageFile(null, bp, 3);
		int flag = bsPictureService.delete(picId);
		if (flag <= 0)
			return Result.fail("删除失败");
		return Result.success();
	}

	/**
	 * 更新banner
	 * @param bsPicture
	 * @param image
	 * @return
	 * @throws IOException
	 */
	@PutMapping("/pictures")
	@ApiOperation(value="更新banner", notes="更新banner")
	public Object updatePicture(BsPicture bsPicture, 
			@ApiParam(name="image",required=false)MultipartFile image) throws IOException {
		saveOrUpdateOrDeleteImageFile(image, bsPicture, 2);
		int flag = bsPictureService.add(bsPicture);
		if (flag <= 0)
			return Result.fail("更新失败");
		return Result.success();
	}
	
	/**
	 * 更换banner状态
	 * @param picId
	 * @return
	 */
	@PostMapping("/pictures/{picId}")
	@ApiOperation(value = "更换banner状态", notes="更换banner状态")
	public Object changeStatus(@PathVariable("picId") int picId) {
		int flag = bsPictureService.changeStatus(picId);
		if(flag <= 0)
			return Result.fail("更换状态失败");
		return Result.success();
	}

	/**
	 * @param image 图片资源
	 * @param bsPicture pojo
	 * @param size 1-增加 2-更新 3-删除
	 * @throws IOException
	 */
	public void saveOrUpdateOrDeleteImageFile(MultipartFile image, BsPicture bsPicture, int size) throws IOException {
		if (size == 1) {
			String fileName = new Date().getTime() + (int) (Math.random() * 100) + ".jpg";
			File file = new File(fileUploadProperteis.getUploadFolder() +"/picture/"+ fileName);
			System.err.println(file.getAbsolutePath());
			if (!file.getParentFile().exists())
				file.getParentFile().mkdirs();

			if (image != null) {
				image.transferTo(file);
				BufferedImage img = ImageUtil.change2jpg(file);
				ImageIO.write(img, "jpg", file);
				bsPicture.setPicUrl(path + "picture/" + fileName);
			} else
				bsPicture.setPicUrl("暂无图片");

		} else if (size == 2) {
			String fileName = bsPicture.getPicUrl();
			if (!fileName.equals("暂无图片")) {
				File file = new File(fileName.split(path)[1]);
				file.delete();
			}
			fileName = new Date().getTime() + (int) (Math.random() * 100) + ".jpg";
			File file = new File("priture/" + fileName);
			if (!file.getParentFile().exists())
				file.getParentFile().mkdirs();
			if (image != null) {
				image.transferTo(file);
				BufferedImage img = ImageUtil.change2jpg(file);
				ImageIO.write(img, "jpg", file);
				bsPicture.setPicUrl(path + "pciture/" + fileName);
			} else
				bsPicture.setPicUrl("暂无图片");
		} else if (size == 3) {
			String fileName = bsPicture.getPicUrl();
			if (!fileName.equals("暂无图片")) {
				File file = new File(fileName.split(path)[1]);
				file.delete();
			}
		}

	}
	
	/**
	 * @param image
	 * @param beAdvertisement
	 * @param size 1-增加 2-更新 3-删除
	 * @throws IOException
	 */
	public void saveOrUpdateOrDeleteImageFile2(MultipartFile image, BeAdvertisement beAdvertisement, int size) throws IOException {
		if (size == 1) {
			String fileName = new Date().getTime() + (int) (Math.random() * 100) + ".jpg";
			File file = new File(fileUploadProperteis.getUploadFolder() +"/adv/"+ fileName);
			System.err.println(file.getAbsolutePath());
			if (!file.getParentFile().exists())
				file.getParentFile().mkdirs();

			if (image != null) {
				image.transferTo(file);
				BufferedImage img = ImageUtil.change2jpg(file);
				ImageIO.write(img, "jpg", file);
				beAdvertisement.setAdvUrl((path + "adv/" + fileName));
			} else
				beAdvertisement.setAdvUrl("暂无图片");

		} else if (size == 2) {
			String fileName = beAdvertisement.getAdvUrl();
			if (!fileName.equals("暂无图片")) {
				File file = new File(fileName.split(path)[1]);
				file.delete();
			}
			fileName = new Date().getTime() + (int) (Math.random() * 100) + ".jpg";
			File file = new File("adv/" + fileName);
			if (!file.getParentFile().exists())
				file.getParentFile().mkdirs();
			if (image != null) {
				image.transferTo(file);
				BufferedImage img = ImageUtil.change2jpg(file);
				ImageIO.write(img, "jpg", file);
				beAdvertisement.setAdvUrl(path + "adv/" + fileName);
			} else
				beAdvertisement.setAdvUrl("暂无图片");
		} else if (size == 3) {
			String fileName = beAdvertisement.getAdvUrl();
			if (!fileName.equals("暂无图片")) {
				File file = new File(fileName.split(path)[1]);
				file.delete();
			}
		}

	}
	
	/**
	 * 获取广告信息
	 * @param advId
	 * @return
	 */
	@GetMapping("/advertisements/{advId}")
	@ApiOperation(value="获取广告信息", notes="通过advId获取广告信息")
	public Object getAdvertisement(@PathVariable("advId")int advId) {
		if(advId == 0)
			return Result.fail("请输入advId");
		BeAdvertisement ba = beAdvertisementService.get(advId);
		if(ba == null)
			return Result.fail("此advertisement不存在");
		return ba;
	}
	
	/**
	 * 广告查询
	 * @param start
	 * @param size
	 * @param caseId
	 * @param keyword
	 * @return
	 */
	@GetMapping("/advertisements")
	@ApiOperation(value = "广告查询", notes="广告查询")
	public Object listAdvertisement(
			@ApiParam(value="start", required=false)@RequestParam(value="start", defaultValue="0")int start, 
			@ApiParam(value="size", required=false)@RequestParam(value="size",defaultValue="10")int size, 
			@ApiParam(value="caseId", required=false)@RequestParam(value="caseId",defaultValue="0") int caseId, 
			@ApiParam(value="keyword", required=false)@RequestParam(value="keyword", defaultValue="")String keyword) {
		start = start<0?0:start;
		return beAdvertisementService.list(start, size, 5, caseId, keyword);
	}
	
	/**
	 * 增加广告
	 * @param image
	 * @param beAdvertisement
	 * @return
	 * @throws IOException
	 */
	@PostMapping("/advertisements")
	//@ApiOperation(value="增加广告", notes="增加广告")
	@ApiIgnore
	public Object addAdvertisement(MultipartFile image, 
			 BeAdvertisement beAdvertisement) throws IOException {
		if(beAdvertisement == null)
			return Result.fail("未获取到输入值");
		if(beAdvertisement.getAdvId() <= 0)
			return Result.fail("请输入正确的advId");
		saveOrUpdateOrDeleteImageFile2(image, beAdvertisement, 1);
		int flag = beAdvertisementService.add(beAdvertisement);
		if(flag<=0)
			return Result.fail("增加广告失败");
		return Result.success();
	}
	
	/**
	 * 更新广告
	 * @param image
	 * @param beAdvertisement
	 * @return
	 * @throws IOException
	 */
	@PutMapping("/advertisements")
	//@ApiOperation(value = "更新广告", notes="更新广告")
	@ApiIgnore
	public Object updateAdvertisement(MultipartFile image, 
			BeAdvertisement beAdvertisement) throws IOException {
		if(beAdvertisement == null)
			return Result.fail("未获取到输入值");
		saveOrUpdateOrDeleteImageFile2(image, beAdvertisement, 2);
		int flag = beAdvertisementService.update(beAdvertisement);
		if(flag<=0)
			return Result.fail("更新广告失败");
		return Result.success();
	}
	
	/**
	 * 删除广告
	 * @param advId
	 * @return
	 * @throws IOException
	 */
	@DeleteMapping("/advertisements/{advId}")
	@ApiOperation(value = "删除广告", notes = "删除广告")
	public Object deleteAdvertisement(@PathVariable("advId")int advId) throws IOException {
		if(advId<=0)
			return Result.fail("请输入正确的advId");
		BeAdvertisement beAdvertisement = beAdvertisementService.get(advId);
		saveOrUpdateOrDeleteImageFile2(null, beAdvertisement, 3);
		int flag = beAdvertisementService.delete(advId);
		if(flag <= 0)
			return Result.fail("删除失败");
		return Result.success();
	}

}
