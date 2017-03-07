package com.ecard.mapper;

import com.ecard.entity.WechantconfigEntity;

/**
 * 微信配置操作mapper
 */
public interface WechantconfigMapper {
	
	
	//新增微信配置
	void insertWechantconfig(WechantconfigEntity wechantconfigEntity) throws Exception;
	
	//删除所有微信配置信息
	void deleteAllWechantconfig() throws Exception;
	
	//查询微信配置信息
	WechantconfigEntity getOneWechantconfig() throws Exception;
	
}
