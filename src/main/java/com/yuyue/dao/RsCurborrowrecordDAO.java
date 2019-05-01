package com.yuyue.dao;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.yuyue.pojo.RsCurborrowrecord;

public interface RsCurborrowrecordDAO extends JpaRepository<RsCurborrowrecord, Integer>{
	
	public RsCurborrowrecord findByOrderNo(String orderNo);
	
	/**
	 * 
	 * @param status in 状态是否在一个数组里面 ArrayList<Byte> 默认全部
	 * @param starttime
	 * @param endtime
	 * @param deliverType 配送方式是否在一个数组里面 ArrayList<Byte>
	 * @param expressNo
	 * @param nikeName
	 * @param bookName
	 * @param rfid
	 * @param pageable
	 * @return
	 */
	@Query("from RsCurborrowrecord t where t.stage in ?1 and t.startTime between ?2 and ?3 and t.deliverType in ?4 and "
			+ "t.expressNo like ?5 and (t.bsUserinfo.nickname like ?6 or t.bsUserinfo.mobilePhone like ?7 )and "
			+ "(t.bsBookinfo.bookName like ?8 or t.bsBookinstore.rfid like ?9)")
	public Page<RsCurborrowrecord> 
	queryByStageInAndStartTimeBetweenAndDeliverTypeInAndExpressNoLikeAndNikeNameLikeOrVipNoLikeAndBookNameLikeOrRfidLike
	(ArrayList<Byte> stage,Date starttime, Date endtime, ArrayList<Byte> deliverType,String expressNo, 
			String nikeName, String vipNo, String bookName, String rfid, Pageable pageable);
	
	/**
	 * 同上 只是少了expressNo
	 * @param status
	 * @param starttime
	 * @param endtime
	 * @param deliverType
	 * @param nikeName
	 * @param bookName
	 * @param rfid
	 * @param pageable
	 * @return
	 */
	@Query("from RsCurborrowrecord t where t.stage in ?1 and t.startTime between ?2 and ?3 and t.deliverType in ?4 and "
			+ "(t.bsUserinfo.nickname like ?5 or t.bsUserinfo.mobilePhone like ?6)and "
			+ "(t.bsBookinfo.bookName like ?7 or t.bsBookinstore.rfid like ?8)")
	public Page<RsCurborrowrecord> 
	queryByStageInAndStartTimeBetweenAndDeliverTypeInAndNikeNameLikeOrVipNoLikeAndBookNameLikeOrRfidLike
	(ArrayList<Byte> stage,Date starttime, Date endtime, ArrayList<Byte> deliverType, 
			String nikeName, String vipNo, String bookName, String rfid, Pageable pageable);
	
	@Query("from RsCurborrowrecord t where t.stage = 6 and t.bsBookinstore.rfid = ?1")
	public RsCurborrowrecord findByStageIsAndRfidIs(String rfid);
	
}
