package com.whoiszxl.house.biz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whoiszxl.house.biz.mapper.UserMapper;
import com.whoiszxl.house.biz.service.UserService;
import com.whoiszxl.house.common.model.User;


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
