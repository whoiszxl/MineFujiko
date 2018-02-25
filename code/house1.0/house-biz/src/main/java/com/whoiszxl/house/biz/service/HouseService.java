package com.whoiszxl.house.biz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whoiszxl.house.common.model.House;
import com.whoiszxl.house.common.page.PageParams;

@Service
public class HouseService {

	@Autowired
	private HouseMapper houseMapper;
	
	public void queryHouse(House query, PageParams build) {
		// TODO Auto-generated method stub
		
	}

}
