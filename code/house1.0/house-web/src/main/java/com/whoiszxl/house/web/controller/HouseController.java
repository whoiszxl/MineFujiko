package com.whoiszxl.house.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.whoiszxl.house.biz.service.AgencyService;
import com.whoiszxl.house.biz.service.CityService;
import com.whoiszxl.house.biz.service.HouseService;
import com.whoiszxl.house.biz.service.RecommendService;
import com.whoiszxl.house.common.constants.CommonConstants;
import com.whoiszxl.house.common.model.House;
import com.whoiszxl.house.common.model.HouseUser;
import com.whoiszxl.house.common.model.UserMsg;
import com.whoiszxl.house.common.page.PageData;
import com.whoiszxl.house.common.page.PageParams;

/**
 * 房屋控制器
 * @author whoiszxl
 *
 */
@Controller
public class HouseController {

	@Autowired
	private HouseService houseService;
	
	@Autowired
	private AgencyService agencyService;
	
	@Autowired
	private RecommendService recommendService;
	
	@Autowired
	private CityService cityService;
	
	
	/**
	 * 1.实现分页
	 * 2.支持小区搜索、类型搜索
	 * 3.支持排序
	 * 4.支持展示图片、价格、标题、地址等信息
	 * @return
	 */
	@RequestMapping("/house/list")
	public String houseList(Integer pageSize,Integer pageNum,House query,ModelMap modelMap) {
		PageData<House> ps = houseService.queryHouse(query,PageParams.build(pageSize, pageNum));
		List<House> hotHouses = recommendService.getHotHouse(CommonConstants.RECOM_SIZE);
		modelMap.put("recomHouses", hotHouses);
		modelMap.put("ps", ps);
		modelMap.put("vo", query);
		return "house/listing";
	}
	
	
	/**
	 * 1. 查询房屋详情
	 * 2. 查询关联的经纪人
	 * @param id
	 * @return
	 */
	@RequestMapping("house/detail")
	public String houseDetail(Long id, ModelMap modelMap) {
		House house = houseService.queryOneHouse(id);
		HouseUser houseUser = houseService.getHouseUser(id);
		
		recommendService.increase(id);
		 
		if(houseUser.getUserId() != null && houseUser.getUserId().equals(0)) {
			modelMap.put("agent", agencyService.getAgentDetail(house.getUserId()));
		}
		List<House> hotHouses = recommendService.getHotHouse(CommonConstants.RECOM_SIZE);
		modelMap.put("recomHouses", hotHouses);
		modelMap.put("house", house);
		return "/house/detail";
	}
	
	
	@RequestMapping("house/leaveMsg")
	public String houseMsg(UserMsg userMsg) {
		houseService.addUserMsg(userMsg);
		return "redirect:/house/detail?id="+userMsg.getHouseId();
	}
	
	@RequestMapping("house/toAdd")
	public String toAdd(ModelMap map) {
		map.put("citys", cityService.getAllCitys());
		map.put("communitys", houseService.getAllCommunitys());		
		return "house/add";
	}
	
	
}
