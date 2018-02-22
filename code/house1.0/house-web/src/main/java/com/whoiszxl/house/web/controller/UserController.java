package com.whoiszxl.house.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whoiszxl.house.biz.service.UserService;
import com.whoiszxl.house.common.constants.CommonConstants;
import com.whoiszxl.house.common.model.User;
import com.whoiszxl.house.common.result.ResultMsg;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * 1.注册验证 2.发送邮件 3.验证失败重定向到注册页面
	 * 
	 * @param account
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("accounts/register")
	public String accountsRegister(User account, ModelMap modelMap) {
		if (account == null || account.getName() == null) {
			return "user/accounts/register";
		}
		// 1.用户验证
		ResultMsg resultMsg = UserHelper.validate(account);
		if (resultMsg.isSuccess() && userService.addAcount(account)) {
			return "/user/accounts/registerSubmit";
		} else {
			return "redirect:/accounts/register?" + resultMsg.asUrlParams();
		}
	}

	@RequestMapping("accounts/verify")
	public String verify(String key) {
		boolean result = userService.enable(key);
		if (result) {
			return "redirect:/index?" + ResultMsg.successMsg("激活成功").asUrlParams();
		} else {
			return "redirect:/accounts/register?" + ResultMsg.errorMsg("激活失败，请确认链接是否过期").asUrlParams();
		}
	}

	///////////////////////// 用户登录实现//////////////////////////////////

	@RequestMapping("/accounts/signin")
	public String signin(HttpServletRequest request) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String target = request.getParameter("target");
		if (username == null || password == null) {
			request.setAttribute("target", target);
			return "/user/accounts/signin";
		}

		User user = userService.auth(username, password);
		if (user == null) {
			return "redirect:/account/signin?" + "target=" + target + "&username=" + username + "&"
					+ ResultMsg.errorMsg("用户名或密码错误").asUrlParams();
		}else {
			HttpSession session = request.getSession(true);
			session.setAttribute(CommonConstants.USER_ATTRIBUTE, user);
			session.setAttribute(CommonConstants.PLAIN_USER_ATTRIBUTE, user);
			
			return StringUtils.isNotBlank(target)?"redirect:" + target : "redirect:/index";
		}
	}
	
	/**
	 * 注销操作
	 * @param request
	 * @return
	 */
	@RequestMapping("accounts/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
		return "redirect:/index";
	}
}
