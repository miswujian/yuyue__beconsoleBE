package com.yuyue.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.yuyue.dao.BeAdvertisementDAO;
import com.yuyue.pojo.BeAdvertisement;
import com.yuyue.util.Page4Navigator;

//save具有延时性 高并发建议 saveandflush
@Service
public class BeAdvertisementService {

	@Autowired
	private BeAdvertisementDAO beAdvertisementDAO;
	
	public Page4Navigator<BeAdvertisement> list(int start, int size, int navigatePages,int caseId, String keyword){
		Sort sort = new Sort(Sort.Direction.DESC,"advId");
		Pageable pageable = new PageRequest(start, size, sort);
		Page<BeAdvertisement> pageFromJPA;
		if(caseId <= 0)
			pageFromJPA = beAdvertisementDAO.findByNameLike("%"+keyword+"%", pageable);
		else
			pageFromJPA = beAdvertisementDAO.queryByCaseIdEqualsAndNameLike(caseId, "%"+keyword+"%", pageable);
		return new Page4Navigator<>(pageFromJPA, navigatePages);
	}
	
	public int add(BeAdvertisement beAdvertisement) {
		try {
			BeAdvertisement ba = beAdvertisementDAO.save(beAdvertisement);
			return ba.getAdvId();
		} catch (Exception e) {
			return 0;
		}
		
	}
	
	public int update(BeAdvertisement beAdvertisement) {
		try {
			BeAdvertisement ba = beAdvertisementDAO.save(beAdvertisement);
			return ba.getAdvId();
		} catch (Exception e) {
			return 0;
		}
		
	}
	
	public int delete(int advId) {
		try {
			beAdvertisementDAO.delete(advId);
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}
	
	public BeAdvertisement get(int advId) {
		return beAdvertisementDAO.findOne(advId);
	}
	
}
