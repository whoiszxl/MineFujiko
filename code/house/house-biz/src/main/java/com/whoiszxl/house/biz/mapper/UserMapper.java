package com.whoiszxl.house.biz.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.whoiszxl.house.common.model.User;

/**
* @author whoiszxl:
* @version 创建时间：2018年2月14日 下午5:42:42
* @description 
*/
@Mapper
public interface UserMapper {

	public List<User> selectUsers();
}
