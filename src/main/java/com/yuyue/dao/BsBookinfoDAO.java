package com.yuyue.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.yuyue.pojo.BsBookcategory;
import com.yuyue.pojo.BsBookinfo;

public interface BsBookinfoDAO extends JpaRepository<BsBookinfo, Integer> {

	public Page<BsBookinfo> findByBookNameLikeOrIsbnLikeOrAuthorLike(String bookName, String isbn, String author, Pageable pageable);
	
	public List<BsBookinfo> findByBsBookcategory(BsBookcategory bsBookcategory);
	
	public List<BsBookinfo> findByBsBookcategoryAndBookNameLike(BsBookcategory bsBookcategory, String bookName);
	
	public List<BsBookinfo> findByBookNameLike(String bookName);
	
	public List<BsBookinfo> findByIsbn(String isbn);
	
	@Query("from BsBookinfo t where t.bsBookcategory.categoryId like ?1 and t.recommend in ?2 and t.bsPublishinfo.pubId in ?3"
			+ " and (t.bookName like ?4 or t.isbn like ?5 or t.author like ?6)")
	public Page<BsBookinfo> queryByCategoryIdLikeAndRecommendInAndPubIdInAndBookNameLikeOrIsbnLikeorAuthorLike
	(String categoryId, ArrayList<Byte> recommend, ArrayList<Integer> pubId, 
			String bookName, String isbn, String author, Pageable pageable);
	
}
