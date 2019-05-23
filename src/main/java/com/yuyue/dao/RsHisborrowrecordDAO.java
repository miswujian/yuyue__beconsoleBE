package com.yuyue.dao;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.yuyue.pojo.RsHisborrowrecord;

public interface RsHisborrowrecordDAO extends JpaRepository<RsHisborrowrecord, Integer>{
	
	@Query("from RsHisborrowrecord t where t.status in ?1 and t.startTime between ?2 and ?3 and t.deliverType in ?4 and "
			+ "t.returnWay in ?5 and (t.bsUserinfo.nickname like ?6 or t.bsUserinfo.mobilePhone like ?7 )and "
			+ "(t.bsBookinfo.bookName like ?8 or t.bsBookinstore.rfid like ?9)")
	public Page<RsHisborrowrecord> 
	queryByStatusInAndstartTimeBetweenAndDeliverTypeInAndReturnWayInAndNikeNameLikeOrVipNoLikeAndBookNameLikeOrRfidLike
	(ArrayList<Byte> status,Date starttime, Date endtime, ArrayList<Byte> deliverType,ArrayList<Byte> returnWay, 
			String nikeName, String vipNo, String bookName, String rfid, Pageable pageable);
	
	public RsHisborrowrecord findByOrderNo(String orderNo);
	
}
