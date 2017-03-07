package com.ecard.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecard.entity.WechantconfigEntity;
import com.ecard.mapper.WechantconfigMapper;

/**
 * 微信配置操作service
 */
@Service
@Transactional
public class WechantconfigService {
	
	@Autowired
	private WechantconfigMapper wechantconfigMapper;
	
	
	//新增微信配置
	@Transactional(rollbackFor=Exception.class)
	public void insertWechantconfig(WechantconfigEntity wechantconfigEntity) throws Exception {
		//1.删除所有的前端注册页面资料信息
		wechantconfigMapper.deleteAllWechantconfig();
		//2.录入最新的前端注册页面资料信息
		wechantconfigMapper.insertWechantconfig(wechantconfigEntity);
	}
		
	//查询微信配置信息
	public WechantconfigEntity getOneWechantconfig() throws Exception {
		return wechantconfigMapper.getOneWechantconfig();
	}
	
}

