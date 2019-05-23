package com.yuyue.dao;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.yuyue.pojo.RsUsercredit;

public interface RsUsercreditDAO extends JpaRepository<RsUsercredit, String> {

	/**
	 * 高级嵌套查询
	 * @param type
	 * @param starttime
	 * @param endtime
	 * @param nikeName
	 * @param vipNo
	 * @param orderNo
	 * @param tradeNo
	 * @param pageable
	 * @return
	 */
	@Query("from RsUsercredit t where t.type in ?1 and t.createTime between ?2 and ?3 and "
			+ "(t.bsUserinfo.nickname like ?4 or t.bsUserinfo.mobilePhone like ?5 or t.usercreditId in "
			+ "(select r.creditId from RsVipplanorder r where r.orderNo like ?6 or r.tradeNo like ?7))")
	public Page<RsUsercredit> queryByTypeInAndCreateTimeBetweenAndNickNameLikeOrVipNoLikeOrOrderNoLikeOrTradeNoLike
	(ArrayList<Byte> type, Date starttime, Date endtime, 
			String nikeName, String vipNo, String orderNo, String tradeNo, Pageable pageable);
	
}
