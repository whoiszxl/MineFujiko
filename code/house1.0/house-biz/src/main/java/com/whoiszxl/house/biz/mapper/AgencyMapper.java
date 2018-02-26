package com.whoiszxl.house.biz.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.whoiszxl.house.common.model.User;
import com.whoiszxl.house.common.page.PageParams;

/**
 * 经纪人model mapper
 * @author whoiszxl
 *
 */
@Mapper
public interface AgencyMapper {

	List<User> selectAgent(@Param("user") User user, @Param("pageParams") PageParams pageParams);

	Long selectAgentCount(@Param("user") User user);
	
}
