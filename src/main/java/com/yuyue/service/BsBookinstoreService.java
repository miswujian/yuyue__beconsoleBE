package com.yuyue.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.yuyue.dao.BsBookinstoreDAO;
import com.yuyue.pojo.BsBookinfo;
import com.yuyue.pojo.BsBookinstore;
import com.yuyue.util.Page4Navigator;
import com.yuyue.util.StringUtil;

@Service
public class BsBookinstoreService {

	@Autowired
	private BsBookinstoreDAO bsBookinstoreDAO;
	
	@Autowired
	private BsPublishinfoService bsPublishinfoService;
	
	public Page4Navigator<BsBookinstore> list(int start, int size, int navigatePages){
		Sort sort = new Sort(Sort.Direction.DESC,"bookId");
		Pageable pageable= new PageRequest(start, size, sort);
		Page<BsBookinstore> pageFromJPA = bsBookinstoreDAO.findAll(pageable);
		return new Page4Navigator<>(pageFromJPA, navigatePages);
	}
	
	public List<BsBookinstore> list(){
		return bsBookinstoreDAO.findAll();
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
	
}
