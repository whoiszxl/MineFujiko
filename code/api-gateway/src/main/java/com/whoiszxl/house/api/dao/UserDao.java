package com.whoiszxl.house.api.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Repository;

import com.whoiszxl.house.api.common.RestResponse;
import com.whoiszxl.house.api.config.GenericRest;

@Repository
public class UserDao {

	
	@Autowired
	private GenericRest rest;
	
	public String getUsername(Long id) {
		String url = "http://user/getusername?id="+id;
		RestResponse<String> response = rest.get(url, new ParameterizedTypeReference<RestResponse<String>>() {}).getBody();
		return response.getResult();
	}
}
