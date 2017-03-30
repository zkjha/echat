

/**
 * fun:
 * author:qidongbo
 * create time:2017年3月23日
 */
package com.ecard.mapper;

import java.util.List;

import com.ecard.entity.IntegralclearRuleEntity;
import com.ecard.entity.StoredticketRuleEntity;

/**
 * @author apple
 *
 */
public interface ActivitySettingMapper {
	//获取单个对象
	public IntegralclearRuleEntity getIntegralclearRule() throws RuntimeException;

	//更新单条记录
	public int updateIntegralclearRule(IntegralclearRuleEntity obj) throws RuntimeException;
	
	//获取一条储值卡规则对象
	public StoredticketRuleEntity getStoredticketRule(String strTicketId) throws RuntimeException;
	
	//更新单条储值卡规则
	public int updateStoredticketRule(StoredticketRuleEntity obj) throws RuntimeException;
	
	//获取所有储值卡规则信息
	public List<StoredticketRuleEntity> getListStoredticketRule() throws RuntimeException;
	
}
