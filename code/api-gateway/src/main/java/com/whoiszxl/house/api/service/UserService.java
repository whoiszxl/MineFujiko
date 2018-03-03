package com.whoiszxl.house.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whoiszxl.house.api.dao.UserDao;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;

	public String getusername(Long id) {
		return userDao.getUsername(id);
	}
}
