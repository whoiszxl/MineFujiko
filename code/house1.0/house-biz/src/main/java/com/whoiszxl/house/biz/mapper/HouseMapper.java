package com.whoiszxl.house.biz.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.whoiszxl.house.common.model.House;
import com.whoiszxl.house.common.page.PageParams;

/**
 * 房屋信息查询mapper
 * 
 * @author Administrator
 *
 */
@Mapper
public interface HouseMapper {

	public List<House> selectPageHouses(@Param("house") House house, @Param("pageParams") PageParams pageParams);

	public Long selectPageCount(@Param("house") House query);
	
	public int insert(House house);

}
