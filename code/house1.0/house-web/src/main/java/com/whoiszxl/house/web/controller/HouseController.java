package com.whoiszxl.house.web.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whoiszxl.house.biz.service.AgencyService;
import com.whoiszxl.house.biz.service.CityService;
import com.whoiszxl.house.biz.service.CommentService;
import com.whoiszxl.house.biz.service.HouseService;
import com.whoiszxl.house.biz.service.RecommendService;
import com.whoiszxl.house.common.constants.CommonConstants;
import com.whoiszxl.house.common.constants.HouseUserType;
import com.whoiszxl.house.common.model.Comment;
import com.whoiszxl.house.common.model.House;
import com.whoiszxl.house.common.model.HouseUser;
import com.whoiszxl.house.common.model.User;
import com.whoiszxl.house.common.model.UserMsg;
import com.whoiszxl.house.common.page.PageData;
import com.whoiszxl.house.common.page.PageParams;
import com.whoiszxl.house.common.result.ResultMsg;
import com.whoiszxl.house.web.interceptor.UserContext;

/**
 * 房屋控制器
 * 
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
	
	@Autowired
	private CommentService commentService;

	/**
	 * 1.实现分页 2.支持小区搜索、类型搜索 3.支持排序 4.支持展示图片、价格、标题、地址等信息
	 * 
	 * @return
	 */
	@RequestMapping("/house/list")
	public String houseList(Integer pageSize, Integer pageNum, House query, ModelMap modelMap) {
		PageData<House> ps = houseService.queryHouse(query, PageParams.build(pageSize, pageNum));
		List<House> hotHouses = recommendService.getHotHouse(CommonConstants.RECOM_SIZE);
		modelMap.put("recomHouses", hotHouses);
		modelMap.put("ps", ps);
		modelMap.put("vo", query);
		return "house/listing";
	}

	/**
	 * 1. 查询房屋详情 2. 查询关联的经纪人
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("house/detail")
	public String houseDetail(Long id, ModelMap modelMap) {
		House house = houseService.queryOneHouse(id);
		HouseUser houseUser = houseService.getHouseUser(id);

		recommendService.increase(id);
		
		List<Comment> comments = commentService.getHouseComments(id,8);

		if (houseUser.getUserId() != null && houseUser.getUserId().equals(0)) {
			modelMap.put("agent", agencyService.getAgentDetail(house.getUserId()));
		}
		List<House> hotHouses = recommendService.getHotHouse(CommonConstants.RECOM_SIZE);
		modelMap.put("recomHouses", hotHouses);
		modelMap.put("house", house);
		modelMap.put("commentList", comments);
		return "/house/detail";
	}

	@RequestMapping("house/leaveMsg")
	public String houseMsg(UserMsg userMsg) {
		houseService.addUserMsg(userMsg);
		return "redirect:/house/detail?id=" + userMsg.getHouseId();
	}

	/**
	 * 评分
	 * 
	 * @param rating
	 * @param id
	 * @return
	 */
	@RequestMapping("house/rating")
	@ResponseBody
	public ResultMsg houseRate(Double rating, Long id) {
		houseService.updateRating(id, rating);
		return ResultMsg.successMsg("it's ok");
	}

	/**
	 * 收藏
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("house/bookmark")
	public ResultMsg bookmark(Long id) {
		User user = UserContext.getUser();
		houseService.bindUser2House(id, user.getId(), true);
		return ResultMsg.successMsg("it's ok");
	}

	/**
	 * 删除收藏
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("house/unbookmark")
	public ResultMsg unbookmark(Long id) {
		User user = UserContext.getUser();
		houseService.unbindUser2House(id, user.getId(), HouseUserType.BOOKMARK);
		return ResultMsg.successMsg("it's ok");
	}

	// 4.收藏列表
	@RequestMapping("house/bookmarked")
	public String bookmarked(House house, Integer pageNum, Integer pageSize, ModelMap modelMap) {
		User user = UserContext.getUser();
		house.setBookmarked(true);
		house.setUserId(user.getId());
		modelMap.put("ps", houseService.queryHouse(house, PageParams.build(pageSize, pageNum)));
		modelMap.put("pageType", "book");
		return "/house/ownlist";
	}

	@RequestMapping("house/toAdd")
	public String toAdd(ModelMap map) {
		map.put("citys", cityService.getAllCitys());
		map.put("communitys", houseService.getAllCommunitys());
		return "house/add";
	}

	/**
	 * 1. 获取当前用户 2. 设置房产状态 3. 添加房产
	 * 
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("house/add")
	public String doAdd(House house) {
		User user = UserContext.getUser();
		house.setState(CommonConstants.HOUSE_STATE_UP);
		houseService.addHouse(house, user);
		return "redirect:/house/ownlist";
	}
	
	/**
	 * 删除房子
	 * @param id
	 * @param pageType
	 * @return
	 */
	@RequestMapping(value="house/del")
	public String delsale(Long id,String pageType){
	   User user = UserContext.getUser();
	   houseService.unbindUser2House(id,user.getId(),pageType.equals("own")?HouseUserType.SALE:HouseUserType.BOOKMARK);
	   return "redirect:/house/ownlist";
	}

	/**
	 * 个人的房产列表
	 * @param house
	 * @param pageNum
	 * @param pageSize
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("house/ownlist")
	public String ownlist(House house, Integer pageNum, Integer pageSize, ModelMap modelMap) {
		User user = UserContext.getUser();
		house.setUserId(user.getId());
		house.setBookmarked(false);

		modelMap.put("ps", houseService.queryHouse(house, PageParams.build(pageSize, pageNum)));
		modelMap.put("pageType", "own");
		return "house/ownlist";
	}

}
