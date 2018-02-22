package com.whoiszxl.house.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author Administrator
 *
 */
@Controller
public class HomePageController {

	@RequestMapping("index")
	public String accountsRegister(ModelMap modelMap) {
		return "/homepage/index";
	}

	@RequestMapping("")
	public String index(ModelMap modelMap) {
		return "redirect:/index";
	}
}
