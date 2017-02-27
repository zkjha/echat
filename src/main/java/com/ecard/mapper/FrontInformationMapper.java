package com.ecard.mapper;

import com.ecard.entity.FrontInformationEntity;

/**
 * 前端注册页面资料操作mapper
 */
public interface FrontInformationMapper {
	
	//新增前端注册页面资料
	void insertFrontInformation(FrontInformationEntity frontInformationEntity) throws Exception;
	
	//删除所有前端注册页面资料
	void deleteAllFrontInformation() throws Exception;
	
	//查询前端注册页面资料详细信息
	FrontInformationEntity getOneFrontInformation() throws Exception;
	
}
