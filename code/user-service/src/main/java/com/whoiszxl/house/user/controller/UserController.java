package com.whoiszxl.house.user.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.whoiszxl.house.user.common.RestResponse;

@RestController
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Value("${server.port}")
	private Integer port;
	
	@RequestMapping("getusername")
	public RestResponse<String> getUsername(Long id) {
		logger.info("request getUsername method");
		return RestResponse.success("test getUsername port:" + port);
	}
}
