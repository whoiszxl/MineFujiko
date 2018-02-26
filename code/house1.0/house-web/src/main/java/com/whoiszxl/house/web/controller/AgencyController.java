package com.whoiszxl.house.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.whoiszxl.house.biz.service.AgencyService;
import com.whoiszxl.house.biz.service.HouseService;
import com.whoiszxl.house.common.model.House;
import com.whoiszxl.house.common.model.User;
import com.whoiszxl.house.common.page.PageData;
import com.whoiszxl.house.common.page.PageParams;

/**
 * 经纪人控制器
 * @author whoiszxl
 *
 */
@Controller
public class AgencyController {
	
	@Autowired
	private AgencyService agencyService;
	
	@Autowired
	private HouseService houseService;
	
	@RequestMapping("/agency/agentList")
	public String agentList(Integer pageSize, Integer pageNum, ModelMap modelMap) {
		PageData<User> ps = agencyService.getAllAgent(PageParams.build(pageSize, pageNum));
		modelMap.put("ps", ps);
		return "/user/agent/agentList";
	}
	
	
	@RequestMapping("/agency/agentDetail")
	public String agentDetail(Long id, ModelMap modelMap) {
		User user = agencyService.getAgentDetail(id);
		House query = new House();
		query.setUserId(id);
		query.setBookmarked(false);
		
		PageData<House> bindHouse = houseService.queryHouse(query, new PageParams(3,1));
		if(bindHouse != null) {
			modelMap.put("bindHouses", bindHouse.getList());
		}
		
		modelMap.put("agent", user);
		return "/user/agent/agentDetail";
	}
	
}
