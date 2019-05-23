package com.yuyue.dao;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.yuyue.pojo.RsVipplanorder;

public interface RsVipplanorderDAO extends JpaRepository<RsVipplanorder, String> {

	public Page<RsVipplanorder> findByStatus(byte status, Pageable pageable);
	
	@Query("from RsVipplanorder t where t.payType in ?1 and t.status in ?2 and t.payTime between ?3 and ?4 "
			+ "and (t.bsUserinfo.nickname like ?5 or t.bsUserinfo.mobilePhone like ?6 or t.orderNo like ?7 or t.tradeNo like ?8)")
	public Page<RsVipplanorder> 
	quertByPayTypeAndStatusAndPayTimeBetweenAndNikeNameLikeOrVipNoLikeOrOrderNoLikeOrTradeNoLike
	(ArrayList<Byte> paytype, ArrayList<Byte> status, Date starttime, Date endtime, 
			String nikeName, String vipNo, String orderNo, String tradeNo, Pageable pageable);
	
}
