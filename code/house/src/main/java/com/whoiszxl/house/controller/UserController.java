package com.whoiszxl.house.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.whoiszxl.house.common.model.User;
import com.whoiszxl.house.mapper.UserMapper;

/**
* @author whoiszxl:
* @version 创建时间：2018年2月14日 下午9:06:13
* @description 
*/
@RestController
public class UserController {
	
	@Autowired
	private UserMapper userMapper;
	
	@GetMapping("/userlist")
	public List<User> getAllUserId(){
		return userMapper.selectUsers();
	}
}
