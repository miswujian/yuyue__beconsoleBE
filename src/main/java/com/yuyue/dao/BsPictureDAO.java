package com.yuyue.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.yuyue.pojo.BsPicture;

public interface BsPictureDAO extends JpaRepository<BsPicture, Integer>{
	
	@Query("from BsPicture t where t.type = ?1 and t.description like ?2")
	public Page<BsPicture> queryByTypeEqualsAndDescriptionLike(byte type, String description, Pageable pageable);
	
	public Page<BsPicture> findByDescriptionLike(String description, Pageable pageable);

}
