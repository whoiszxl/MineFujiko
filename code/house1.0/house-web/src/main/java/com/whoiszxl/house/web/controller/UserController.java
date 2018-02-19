package com.whoiszxl.house.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whoiszxl.house.biz.service.UserService;
import com.whoiszxl.house.common.model.User;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/users")
	@ResponseBody
	public List<User> hello() {
		return userService.getUsers();
	}

}
