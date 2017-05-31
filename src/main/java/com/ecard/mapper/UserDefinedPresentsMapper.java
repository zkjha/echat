package com.ecard.mapper;

import com.ecard.entity.UserDefinedPresentsActivityEntity;

public interface UserDefinedPresentsMapper {
	//查询 指定会员级别在指定活动类型下是否已经存在数据
	public int isExistsTheActivityRecord(UserDefinedPresentsActivityEntity userDefinedPresentsActivityEntity) throws Exception;
	//新增 活动信息
	public int inserPresentsActivityInfo(UserDefinedPresentsActivityEntity userDefinedPresentsActivityEntity) throws Exception;
		

}
