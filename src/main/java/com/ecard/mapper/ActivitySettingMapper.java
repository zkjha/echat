

/**
 * fun:
 * author:qidongbo
 * create time:2017年3月23日
 */
package com.ecard.mapper;

import com.ecard.entity.IntegralclearRuleEntity;

/**
 * @author apple
 *
 */
public interface ActivitySettingMapper {
	//获取单个对象
	public IntegralclearRuleEntity getIntegralclearRule() throws RuntimeException;

	//更新单条记录
	public int updateIntegralclearRule(IntegralclearRuleEntity obj) throws RuntimeException;
	
	
}
