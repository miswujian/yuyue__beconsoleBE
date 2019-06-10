package com.yuyue.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.yuyue.dao.BsBookinstoreDAO;
import com.yuyue.pojo.Bookinstore;
import com.yuyue.pojo.BsBookcaseinfo;
import com.yuyue.pojo.BsBookcellinfo;
import com.yuyue.pojo.BsBookinfo;
import com.yuyue.pojo.BsBookinstore;
import com.yuyue.pojo.User;
import com.yuyue.util.Page4Navigator;
import com.yuyue.util.StringUtil;
import net.sf.json.JSONObject;

@Service
public class BsBookinstoreService {

	@Autowired
	private BsBookinstoreDAO bsBookinstoreDAO;
	
	@Autowired
	private BsPublishinfoService bsPublishinfoService;
	
	@Autowired
	private BsBookcellinfoService bsBookcellinfoService;
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate = null;
	
	@Autowired
	private BeUserService beUserService;
	
	public Page4Navigator<BsBookinstore> list(int start, int size, int navigatePages, HttpSession session){
		String u = stringRedisTemplate.opsForValue().get(session.getId().toString());
		JSONObject json = JSONObject.fromObject(u);
	    User user = (User) JSONObject.toBean(json,User.class);
	    List<Integer> caseIds = beUserService.getArray(user.getUid());
	    System.err.println("case:"+caseIds.size());
	    List<Integer> cellIds = bsBookcellinfoService.list(caseIds);
	    System.err.println("cell:"+cellIds.size());
	    if(cellIds.isEmpty()) {
	    	Page4Navigator<BsBookinstore> bbs = new Page4Navigator<>();
	    	List<BsBookinstore> list = new ArrayList<>();
	    	bbs.setContent(list);
	    	return bbs;
	    }
		Sort sort = new Sort(Sort.Direction.DESC,"bookId");
		Pageable pageable= new PageRequest(start, size, sort);
		Page<BsBookinstore> pageFromJPA = bsBookinstoreDAO.findByCellIdIn(cellIds, pageable);
		Page4Navigator<BsBookinstore> bbss = new Page4Navigator<>(pageFromJPA, navigatePages);
		setCaseAndCell(bbss.getContent());
		return bbss;
	}
	
	public List<BsBookinstore> list(){
		return bsBookinstoreDAO.findAll();
	}
	
	public BsBookinstore getBsBookinstore(BsBookcellinfo bbc) {
		List<BsBookinstore> bbis = bsBookinstoreDAO.findByBsBookcellinfo(bbc);
		if(bbis==null||bbis.isEmpty())
			return null;
		return bbis.get(0);
	}
	
	public Bookinstore getBsBookinstore(String code) {
		List<BsBookinstore> bbis = bsBookinstoreDAO.findByCode(code);
		if(bbis==null||bbis.isEmpty())
			return null;
		Bookinstore bookinstore = new Bookinstore();
		bookinstore.setBookId(bbis.get(0).getBookId());
		bookinstore.setCode(bbis.get(0).getCode());
		bookinstore.setIsbn(bbis.get(0).getBsBookinfo().getIsbn());
		bookinstore.setRfid(bbis.get(0).getRfid());
		bookinstore.setPrice(bbis.get(0).getBsBookinfo().getPrice());
		return bookinstore;
	}
	
	public Page4Navigator<BsBookinstore> list(int start, int size, int navigatePages, BsBookcaseinfo bbc){
		ArrayList<BsBookcellinfo> bbcs = (ArrayList<BsBookcellinfo>) bsBookcellinfoService.list(bbc);
		Sort sort = new Sort(Sort.Direction.DESC,"bookId");
		Pageable pageable = new PageRequest(start, size, sort);
		Page<BsBookinstore> pageFromJPA = bsBookinstoreDAO.findByBsBookcellinfoIn(bbcs, pageable);
		Page4Navigator<BsBookinstore> bbss = new Page4Navigator<>(pageFromJPA, navigatePages);
		setNullForCell(bbss.getContent());
		return bbss;
	}
	
	public Page4Navigator<BsBookinstore> list(int start, int size, int navigatePages, String keyword){
		Sort sort = new Sort(Sort.Direction.DESC,"bookId");
		Pageable pageable = new PageRequest(start, size, sort);
		Page<BsBookinstore> pageFromJPA = bsBookinstoreDAO.queryByBookNameLikeOrIsbnLikeOrAuthorLike("%"+keyword+"%", "%"+keyword+"%", "%"+keyword+"%",pageable);
		return new Page4Navigator<>(pageFromJPA, navigatePages);
	}
	
	public Page4Navigator<BsBookinstore> list(int start, int size, int navigatePages, String categoryId, String status,
			String pubId, String keyword){
		Sort sort = new Sort(Sort.Direction.DESC,"bookId");
		Pageable pageable = new PageRequest(start, size, sort);
		ArrayList<Byte> statuslist = new ArrayList<>();
		if(!StringUtil.isNumeric(status))
			categoryId = "";
		if(!status.equals(""))
			statuslist.add((byte)Integer.parseInt(status));
		else {
			statuslist.add((byte)0);
			statuslist.add((byte)1);
			statuslist.add((byte)2);
			statuslist.add((byte)9);
		}
		ArrayList<Integer> pubIdlist = new ArrayList<>();
		if(pubId.equals(""))
			pubIdlist = bsPublishinfoService.getPubIds();
		else
			pubIdlist.add(Integer.parseInt(pubId));
		Page<BsBookinstore> pageFromJPA = null;
		if(categoryId.equals(""))
			pageFromJPA = bsBookinstoreDAO.queryByCategoryIdLikeAndStatusInAndPubIdInAndBookNameLikeOrIsbnLikeorAuthorLike
			("%"+categoryId+"%", statuslist, pubIdlist, "%"+keyword+"%", "%"+keyword+"%", "%"+keyword+"%", pageable);
		else
			pageFromJPA = bsBookinstoreDAO.queryByCategoryIdLikeAndStatusInAndPubIdInAndBookNameLikeOrIsbnLikeorAuthorLike
			(categoryId, statuslist, pubIdlist, "%"+keyword+"%", "%"+keyword+"%", "%"+keyword+"%", pageable);
		return new Page4Navigator<>(pageFromJPA, navigatePages);
	}
	
	public int countByRfid(String rfid) {
		return bsBookinstoreDAO.countByRfid(rfid);
	}
	
	public int changeStatus(BigInteger bookId, String status) {
		try {
			if(StringUtil.isNumeric(status))
				return 0;
			BsBookinstore bb = bsBookinstoreDAO.findOne(bookId);
			if((status.equals("2")&&bb.getStatus()==(byte)0)||(status.equals("0")&&bb.getStatus()==(byte)2)) {
				bb.setStatus((byte)Integer.parseInt(status));
				bsBookinstoreDAO.save(bb);
				return 1;
			}
			return 0;
		} catch (Exception e) {
			return 0;
		}
	}
	
	public int addBookinstore(BsBookinstore bbs) {
		try {
			BsBookinstore bb = bsBookinstoreDAO.save(bbs);
			return bb.getBookId().intValue();
		} catch (Exception e) {
			return 0;
		}
		
	}
	
	public BsBookinstore add(BsBookinstore bbs) {
		try {
			BsBookinstore bb = bsBookinstoreDAO.save(bbs);
			return bb;
		} catch (Exception e) {
			return null;
		}
		
	}
	
	public int updateBookinstore(BsBookinstore bbs) {
		try {
			bsBookinstoreDAO.save(bbs);
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}
	
	public BsBookinstore getBookinstore(BigInteger bookId) {
		return bsBookinstoreDAO.findOne(bookId);
	}
	
	public void setBsBookinstore(BsBookinfo bbi) {
		List<BsBookinstore> bsBookinstores = bsBookinstoreDAO.findByBsBookinfo(bbi);
		bbi.setBsBookinstores(bsBookinstores);
		for(BsBookinstore bbs : bsBookinstores)
			bbs.setBsBookinfo(null);
	}
	
	public void setBsBookinstore(List<BsBookinfo> bbis) {
		for(BsBookinfo bbi : bbis) {
			setBsBookinstore(bbi);
		}
	}
	
	public void setNullForCell(BsBookinstore bbs) {
		if(bbs.getBsBookcellinfo()!=null)
			bbs.getBsBookcellinfo().setBsBookcaseinfo(null);
		if(bbs.getBsBookinfo()!=null) {
			bbs.getBsBookinfo().setBsBookinstores(null);
		}	
	}
	
	public void setNullForCell(List<BsBookinstore> bbss) {
		for(BsBookinstore bbs :bbss)
			setNullForCell(bbs);
	}
	
	public void setCaseAndCell(List<BsBookinstore> bbss) {
		for(BsBookinstore bbs :bbss) {
			setCaseAndCell(bbs);
		}
	}
	
	public void setCaseAndCell(BsBookinstore bbs) {
		if(bbs.getBsBookcellinfo()!=null) {
			bbs.setCaseName(bbs.getBsBookcellinfo().getBsBookcaseinfo().getCaseName()+
					bbs.getBsBookcellinfo().getBsBookcaseinfo().getCaseId());
			bbs.setCellId(bbs.getBsBookcellinfo().getCellId());
			bbs.setBsBookcellinfo(null);
		}
		if(bbs.getBsBookinfo()!=null) {
			bbs.setCategoryName(bbs.getBsBookinfo().getBsBookcategory().getCategoryName());
		}
	}
	
}
