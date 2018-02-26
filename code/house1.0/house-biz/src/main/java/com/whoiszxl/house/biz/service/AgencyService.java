package com.whoiszxl.house.biz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.whoiszxl.house.biz.mapper.AgencyMapper;
import com.whoiszxl.house.common.model.User;
import com.whoiszxl.house.common.page.PageData;
import com.whoiszxl.house.common.page.PageParams;

/**
 * 经纪人service
 * @author whoiszxl
 *
 */
@Service
public class AgencyService {

	
	@Autowired
	private AgencyMapper agencyMapper;
	
	@Value("${file.prefix}")
	private String imgPrefix;
	
	/**
	 * 访问user表获取详情
	 * @param userId
	 * @return
	 */
	public User getAgentDetail(Long userId) {
		
		User user = new User();
		user.setId(userId);
		user.setType(2);
		List<User> list = agencyMapper.selectAgent(user, PageParams.build(1, 1));
		setImg(list);
		
		if(!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	private void setImg(List<User> list) {
		list.forEach(i -> {
			i.setAvatar(imgPrefix + i.getAvatar());
		});
		
	}

	public PageData<User> getAllAgent(PageParams pageParams) {
		List<User> agents = agencyMapper.selectAgent(new User(), pageParams);
		setImg(agents);
		Long count = agencyMapper.selectAgentCount(new User());
		return PageData.buildPage(agents, count, pageParams.getPageSize(), pageParams.getPageNum());
	}

}
