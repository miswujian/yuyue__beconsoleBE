package com.yuyue.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.yuyue.dao.BeOperationlogDAO;
import com.yuyue.pojo.BeOperationlog;
import com.yuyue.pojo.User;
import com.yuyue.util.Page4Navigator;

@Service
public class BeOperationlogService {

	@Autowired
	private BeOperationlogDAO beOperationlogDAO;
	
	public void add(BeOperationlog bo) {
		beOperationlogDAO.save(bo);
	}
	
	public Page4Navigator<BeOperationlog> list(int start, int size, int navigatePages){
		Sort sort = new Sort(Sort.Direction.DESC,"logId");
		Pageable pageable = new PageRequest(start, size, sort);
		Page<BeOperationlog> pageFromJPA = beOperationlogDAO.findAll(pageable);
		Page4Navigator<BeOperationlog> bos = new Page4Navigator<>(pageFromJPA, navigatePages);
		setUser(bos.getContent());
		return bos;
	}
	
	public void setUser(BeOperationlog beOperationlog) {
		User user = new User();
		user.setUid(beOperationlog.getBeUser().getUid());
		user.setUserName(beOperationlog.getBeUser().getUserName());
		beOperationlog.setUser(user);
	}
	
	public void setUser(List<BeOperationlog> bos) {
		for(BeOperationlog bo : bos)
			setUser(bo);
	}
	
}
