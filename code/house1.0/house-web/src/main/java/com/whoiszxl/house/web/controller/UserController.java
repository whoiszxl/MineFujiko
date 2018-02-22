package com.whoiszxl.house.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whoiszxl.house.biz.service.UserService;
import com.whoiszxl.house.common.model.User;
import com.whoiszxl.house.common.result.ResultMsg;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	/**
	 * 1.注册验证
	 * 2.发送邮件
	 * 3.验证失败重定向到注册页面
	 * @param account
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("accounts/register")
	public String accountsRegister(User account, ModelMap modelMap) {
		if(account == null || account.getName() == null) {
			return "user/accounts/register";
		}
		//1.用户验证
		ResultMsg resultMsg = UserHelper.validate(account);
		if(resultMsg.isSuccess() && userService.addAcount(account)) {
			return "/user/accounts/registerSubmit";
		}else {
			return "redirect:/accounts/register?"+resultMsg.asUrlParams();
		}
	}
	
	@RequestMapping("accounts/verify")
	public String verify(String key) {
		boolean result = userService.enable(key);
		if(result) {
			return "redirect:/index?"+ResultMsg.successMsg("激活成功").asUrlParams();
		}else {
			return "redirect:/accounts/register?"+ResultMsg.errorMsg("激活失败，请确认链接是否过期").asUrlParams();
		}
	}

}
