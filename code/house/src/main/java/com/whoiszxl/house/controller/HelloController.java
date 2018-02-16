package com.whoiszxl.house.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.whoiszxl.house.common.model.User;
import com.whoiszxl.house.service.UserService;

/**
* @author whoiszxl:
* @version 创建时间：2018年2月15日 下午11:29:10
* @description 
*/
@Controller
public class HelloController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/hello")
	public String hello(ModelMap modelMap) {
		List<User> userList = userService.getUserList();
		User one = userList.get(0);
		modelMap.put("user", one);
		return "hello";
	}
	
	@RequestMapping("/index")
	public String index() {
		return "homepage/index";
	}
	
}
