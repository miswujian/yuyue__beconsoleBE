package com.yuyue.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.yuyue.dao.BsBookinfoDAO;
import com.yuyue.pojo.Bookinfo;
import com.yuyue.pojo.BsBookcategory;
import com.yuyue.pojo.BsBookinfo;
import com.yuyue.util.Page4Navigator;
import com.yuyue.util.StringUtil;

@Service
public class BsBookinfoService {

	@Autowired
	private BsBookinfoDAO bsBookinfoDAO;
	
	@Autowired
	private BsPublishinfoService bsPublishinfoService;

	public Page4Navigator<BsBookinfo> list(int start, int size, int navigatePages) {
		Sort sort = new Sort(Sort.Direction.DESC, "bookinfoId");
		Pageable pageable = new PageRequest(start, size, sort);
		Page<BsBookinfo> pageFromJPA = bsBookinfoDAO.findAll(pageable);
		Page4Navigator<BsBookinfo> bbis = new Page4Navigator<>(pageFromJPA, navigatePages);
		setCategoryName(bbis.getContent());
		return bbis;
	}

	public Page4Navigator<BsBookinfo> list(int start, int size, int navigatePages, String keyword) {
		Sort sort = new Sort(Sort.Direction.DESC, "bookinfoId");
		Pageable pageable = new PageRequest(start, size, sort);
		Page<BsBookinfo> pageFromJPA = bsBookinfoDAO.findByBookNameLikeOrIsbnLikeOrAuthorLike("%" + keyword + "%",
				"%" + keyword + "%", "%" + keyword + "%", pageable);
		Page4Navigator<BsBookinfo> bbis = new Page4Navigator<>(pageFromJPA, navigatePages);
		setCategoryName(bbis.getContent());
		return bbis;
	}

	public Page4Navigator<BsBookinfo> list(int start, int size, int navigatePages, String categoryId, String recommend,
			String pubId, String keyword) {
		Sort sort = new Sort(Sort.Direction.DESC, "bookinfoId");
		Pageable pageable = new PageRequest(start, size, sort);
		ArrayList<Byte> recommendlist = new ArrayList<>();
		if (!StringUtil.isNumeric(recommend))
			recommend = "";
		if (!recommend.equals(""))
			recommendlist.add((byte) Integer.parseInt(recommend));
		else {
			recommendlist.add((byte) 0);
			recommendlist.add((byte) 1);
		}
		ArrayList<Integer> pubIdlist = new ArrayList<>();
		if(!StringUtil.isNumeric(pubId))
			pubId = "";
		if(pubId.equals(""))
			 pubIdlist = bsPublishinfoService.getPubIds();
		else
			pubIdlist.add(Integer.parseInt(pubId));
		Page<BsBookinfo> pageFromJPA = null;
		if (categoryId.equals(""))
			pageFromJPA = bsBookinfoDAO
					.queryByCategoryIdLikeAndRecommendInAndPubIdInAndBookNameLikeOrIsbnLikeorAuthorLike("%"+categoryId+"%",
							recommendlist, pubIdlist, "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%",
							pageable);
		else
			pageFromJPA = bsBookinfoDAO
					.queryByCategoryIdLikeAndRecommendInAndPubIdInAndBookNameLikeOrIsbnLikeorAuthorLike(categoryId,
							recommendlist, pubIdlist, "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%",
							pageable);
		Page4Navigator<BsBookinfo> bbis = new Page4Navigator<>(pageFromJPA, navigatePages);
		setCategoryName(bbis.getContent());
		return bbis;
	}

	public List<Bookinfo> getByBookcategoryAndBookName(BsBookcategory bsBookcategory, String bookName) {
		List<Bookinfo> bs = new ArrayList<>();
		List<BsBookinfo> bbs = bsBookinfoDAO.findByBsBookcategoryAndBookNameLike(bsBookcategory, "%" + bookName + "%");
		for (BsBookinfo bb : bbs) {
			Bookinfo b = new Bookinfo();
			b.setBookinfoId(bb.getBookinfoId());
			b.setBookName(bb.getBookName());
			bs.add(b);
		}
		return bs;
	}

	/**
	 * 
	 * @param bookName
	 * @return
	 */
	public List<String> getIsbnsByBookName(String bookName) {
		List<BsBookinfo> bbs = bsBookinfoDAO.findByBookNameLike("%" + bookName + "%");
		if (bbs.isEmpty())
			return null;
		List<String> isbns = new ArrayList<>();
		for (BsBookinfo bb : bbs)
			isbns.add(bb.getIsbn());
		return isbns;
	}

	/**
	 * 通过isbn找到书籍的名字
	 * 
	 * @param isbn
	 * @return
	 */
	public String getBookNameByIsbn(String isbn) {
		List<BsBookinfo> bbs = bsBookinfoDAO.findByIsbn(isbn);
		if (bbs == null || bbs.isEmpty())
			return null;
		return bbs.get(0).getBookName();
	}
	
	public BsBookinfo getByIsbn(String isbn) {
		List<BsBookinfo> bbs = bsBookinfoDAO.findByIsbn(isbn);
		if (bbs == null || bbs.isEmpty())
			return null;
		return bbs.get(0);
	}

	public int deletebook(int id) {
		try {
			bsBookinfoDAO.delete(id);
			return 1;
		} catch (Exception e) {
			return 0;
		}

	}

	public void setCategoryName(BsBookinfo bbi) {
		if (bbi.getBsBookcategory() != null)
			bbi.setCategoryId(bbi.getBsBookcategory().getCategoryId());
	}

	public void setCategoryName(List<BsBookinfo> bbis) {
		for (BsBookinfo bbi : bbis)
			setCategoryName(bbi);
	}

	public void setBookinfo(List<BsBookcategory> bbcs) {
		for (BsBookcategory bbc : bbcs) {
			setBookinfo(bbc);
		}
	}

	public void setBookinfo(BsBookcategory bbc) {
		List<BsBookinfo> bbis = bsBookinfoDAO.findByBsBookcategory(bbc);
		List<Bookinfo> bis = new ArrayList<>();
		for (BsBookinfo bbi : bbis) {
			Bookinfo bi = new Bookinfo();
			bi.setBookinfoId(bbi.getBookinfoId());
			bi.setBookName(bbi.getBookName());
			bis.add(bi);
		}
		bbc.setBsBookinfos(bis);
	}

	public int addbook(BsBookinfo bsBookinfo) {
		try {
			BsBookinfo bb = bsBookinfoDAO.save(bsBookinfo);
			return bb.getBookinfoId();
		} catch (Exception e) {
			return 0;
		}

	}

	public BsBookinfo getBook(int bookinfoId) {
		return bsBookinfoDAO.findOne(bookinfoId);
	}

	public int updateBook(BsBookinfo bsBookinfo) {
		try {
			BsBookinfo bb = bsBookinfoDAO.save(bsBookinfo);
			return bb.getBookinfoId();
		} catch (Exception e) {
			return 0;
		}
	}
	
}
