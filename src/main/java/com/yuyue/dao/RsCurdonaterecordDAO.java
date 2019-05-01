package com.yuyue.dao;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.yuyue.pojo.RsCurdonaterecord;

public interface RsCurdonaterecordDAO extends JpaRepository<RsCurdonaterecord, Integer>{

	@Query("from RsCurdonaterecord t where t.status = 0 and t.isbn = ?1")
	public RsCurdonaterecord findByStatusIsAndIsbnIs(String isbn);
	
	@Query("from RsCurdonaterecord t where t.status in ?1 and t.donateTime between ?2 and ?3 and "
			+ "(t.bsUserinfo.nickname like ?4 or t.bsUserinfo.mobilePhone like ?5) and "
			+ "(t.isbn like ?6 or t.isbn in ?7)")
	public Page<RsCurdonaterecord> queryByStatusInAndDonateTimeBetweenAndNikeNameLikeOrVipNoLikeAndBookNameLikeOrIsbnLike
	(ArrayList<Byte> status, Date starttime, Date endtime, String nikeName, 
			String vipNo, String isbn, ArrayList<String> isbns, Pageable pageable);
	
}
