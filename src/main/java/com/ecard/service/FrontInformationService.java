package com.ecard.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecard.entity.FrontInformationEntity;
import com.ecard.mapper.FrontInformationMapper;

/**
 * 前端注册页面资料操作service
 */
@Service
@Transactional
public class FrontInformationService {
	
	@Autowired
	private FrontInformationMapper frontInformationMapper;
	
	//新增前端注册页面资料
	@Transactional(rollbackFor=Exception.class)
	public void insertFrontInformation(FrontInformationEntity frontInformationEntity) throws Exception {
		//1.删除所有的前端注册页面资料信息
		frontInformationMapper.deleteAllFrontInformation();
		//2.录入最新的前端注册页面资料信息
		frontInformationMapper.insertFrontInformation(frontInformationEntity);
	}
	
	//查询前端注册页面资料详细信息
	public FrontInformationEntity getOneFrontInformation() throws Exception {
		return frontInformationMapper.getOneFrontInformation();
	}
}

