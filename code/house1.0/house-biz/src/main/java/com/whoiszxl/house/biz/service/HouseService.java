package com.whoiszxl.house.biz.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.whoiszxl.house.biz.mapper.HouseMapper;
import com.whoiszxl.house.common.model.Community;
import com.whoiszxl.house.common.model.House;
import com.whoiszxl.house.common.model.HouseUser;
import com.whoiszxl.house.common.model.User;
import com.whoiszxl.house.common.model.UserMsg;
import com.whoiszxl.house.common.page.PageData;
import com.whoiszxl.house.common.page.PageParams;
import com.whoiszxl.house.common.utils.BeanHelper;

@Service
public class HouseService {
	
	@Value("${file.prefix}")
	private String imgPrefix;

	@Autowired
	private HouseMapper houseMapper;
	
	@Autowired
	private AgencyService agencyService;
	
	@Autowired
	private MailService mailService;
	
	/**
	 * 1. 查询小区
	 * 2. 添加图片服务器地址前缀
	 * 3. 构建分页结果
	 * @param query
	 * @param build
	 */
	public PageData<House> queryHouse(House query, PageParams pageParams) {
		List<House> houses = Lists.newArrayList();
		if(!Strings.isNullOrEmpty(query.getName())) {
			Community community = new Community();
			community.setName(query.getName());
			List<Community> communities = houseMapper.selectCommunity(community);
			if(!communities.isEmpty()) {
				query.setCommunityId(communities.get(0).getId());
			}
		}
		
		houses = queryAndSetImg(query, pageParams);
		Long count = houseMapper.selectPageCount(query);
		return PageData.buildPage(houses, count, pageParams.getPageSize(), pageParams.getPageNum());
	}

	public List<House> queryAndSetImg(House query, PageParams pageParams) {
		List<House> houses = houseMapper.selectPageHouses(query, pageParams);
		houses.forEach(h ->{
			h.setFirstImg(imgPrefix + h.getFirstImg());
			h.setImageList(h.getImageList().stream().map(img -> imgPrefix + img).collect(Collectors.toList()));
			h.setFloorPlanList(h.getFloorPlanList().stream().map(pic -> imgPrefix + pic).collect(Collectors.toList()));
		});
		return houses;
	}

	public House queryOneHouse(Long id) {
		House query = new House();
		query.setId(id);
		List<House> houses = queryAndSetImg(query, PageParams.build(1, 1));
		if(!houses.isEmpty()) {
			return houses.get(0);
		}
		return null;
	}

	public void addUserMsg(UserMsg userMsg) {
		BeanHelper.onInsert(userMsg);
		houseMapper.insertUserMsg(userMsg);
		User user = agencyService.getAgentDetail(userMsg.getAgentId());
		mailService.sendMail("来自user:"+userMsg.getEmail()+"的留言", userMsg.getMsg(), user.getEmail());
		
	}

	public HouseUser getHouseUser(Long houseId) {
		return houseMapper.selectSaleHouseUser(houseId);
	}

}
