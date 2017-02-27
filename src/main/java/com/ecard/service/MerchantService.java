package com.ecard.service;


import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecard.entity.MerchantEntity;
import com.ecard.mapper.MerchantMapper;

/**
 * 商家资料操作service
 */
@Service
@Transactional
public class MerchantService {
	
	@Autowired
	private MerchantMapper merchantMapper;
	

	//修改商家信息
	public void updateMerchant(MerchantEntity merchantEntity) throws Exception {
		merchantMapper.updateMerchant(merchantEntity);
	}
	
	//根据商家ID查询商家信息
	public MerchantEntity getMerchantById(@Param("strMerchantid")String strMerchantid) throws Exception {
		return merchantMapper.getMerchantById(strMerchantid);
	}
	
	//商家系统版本升级
	public void upgradeMerchantSystem(MerchantEntity merchantEntity) throws Exception {
		merchantMapper.upgradeMerchantSystem(merchantEntity);
	}
	
	public MerchantEntity getMerchantSystemVersionInfo() throws Exception {
		return merchantMapper.getMerchantSystemVersionInfo();
	}
	
}

