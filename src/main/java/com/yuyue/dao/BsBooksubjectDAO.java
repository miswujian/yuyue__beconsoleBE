package com.yuyue.dao;

import java.util.ArrayList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.yuyue.pojo.BsBooksubject;

public interface BsBooksubjectDAO extends JpaRepository<BsBooksubject, Integer> {

	public Page<BsBooksubject> queryByIsShowInAndSubjectNameLike(ArrayList<Byte> isShow, String subjectName, Pageable pageable);
	
}
