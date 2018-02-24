package com.whoiszxl.house.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.whoiszxl.house.biz.service.UserService;
import com.whoiszxl.house.common.model.User;

/**
 * 測試用例
 * @author whoiszxl
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthTest {

	@Autowired
	private UserService userService;
	
	@Test
	public void testAuth() {
		User user = userService.auth("zhouxiaolong@sina.com", "123456");
		
		assert user != null;
	}
	
}
