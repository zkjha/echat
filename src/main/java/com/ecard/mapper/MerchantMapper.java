package com.ecard.mapper;

import org.apache.ibatis.annotations.Param;

import com.ecard.entity.MerchantEntity;

/**
 * 商家资料操作mapper
 */
public interface MerchantMapper {
	
	//修改商家信息
	void updateMerchant(MerchantEntity merchantEntity) throws Exception;
	
	//根据商家ID查询商家信息
	MerchantEntity getMerchantById(@Param("strMerchantid")String strMerchantid) throws Exception;
	
	//商家系统版本升级
	void upgradeMerchantSystem(MerchantEntity merchantEntity) throws Exception;
	
}
