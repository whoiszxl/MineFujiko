package com.whoiszxl.house.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whoiszxl.house.common.model.User;
import com.whoiszxl.house.mapper.UserMapper;
import com.whoiszxl.house.service.UserService;

/**
* @author whoiszxl:
* @version 创建时间：2018年2月14日 下午9:35:03
* @description 
*/
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;
	
	@Override
	public List<User> getUserList() {
		return userMapper.selectUsers();
	}

	
}
