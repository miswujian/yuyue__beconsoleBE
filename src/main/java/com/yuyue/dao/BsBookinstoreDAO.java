package com.yuyue.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.yuyue.pojo.BsBookinfo;
import com.yuyue.pojo.BsBookinstore;

public interface BsBookinstoreDAO extends JpaRepository<BsBookinstore, BigInteger> {
	
	public List<BsBookinstore> findByBsBookinfo(BsBookinfo bsBookinfo);
	
	/**
	 * 多表多条件级联查询
	 * @param bookName
	 * @param isbn
	 * @param author
	 * @param pageable
	 * @return
	 */
	@Query("from BsBookinstore t where t.bsBookinfo.bookName like ?1 or t.bsBookinfo.isbn like ?2 or t.bsBookinfo.author like ?3")
	public Page<BsBookinstore> queryByBookNameLikeOrIsbnLikeOrAuthorLike
	(String bookName, String isbn, String author, Pageable pageable);
	
	@Query("from BsBookinstore t where t.bsBookinfo.bsBookcategory.categoryId like ?1 and t.status in ?2 "
			+ "and t.bsBookinfo.bsPublishinfo.pubId in ?3 and "
			+ "(t.bsBookinfo.bookName like ?4 or t.bsBookinfo.isbn like ?5 or t.bsBookinfo.author like ?6)")
	public Page<BsBookinstore> queryByCategoryIdLikeAndStatusInAndPubIdInAndBookNameLikeOrIsbnLikeorAuthorLike
	(String categoryId, ArrayList<Byte> Status, ArrayList<Integer> pubId, 
			String bookName, String isbn, String author, Pageable pageable);

}
