package com.whoiszxl.house.biz.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.whoiszxl.house.common.model.Community;
import com.whoiszxl.house.common.model.House;
import com.whoiszxl.house.common.model.HouseUser;
import com.whoiszxl.house.common.model.UserMsg;
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

	public List<Community> selectCommunity(Community community);

	public int insertUserMsg(UserMsg userMsg);

	public HouseUser selectSaleHouseUser(Long houseId);

	public HouseUser selectHouseUser(@Param("userId")Long userId, @Param("id")Long houseId, @Param("type")Integer integer);

	public int insertHouseUser(HouseUser houseUser);

	public int updateHouse(House updateHouse);

	public int downHouse(Long id);

	public int deleteHouseUser(@Param("id")Long id,@Param("userId") Long userId,@Param("type") Integer value);

}
