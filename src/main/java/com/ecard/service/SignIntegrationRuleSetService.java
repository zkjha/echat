package com.ecard.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecard.entity.SignIntegrationRuleEntity;
import com.ecard.entity.IntegrationCashRuleEntity;
import com.ecard.mapper.SignIntegrationRuleSetMapper;
@Service
@Transactional
public class SignIntegrationRuleSetService {
	@Autowired
	private SignIntegrationRuleSetMapper signIntegrationRuleSetMapper;
	
	//插入连续签到积分规则
	public int insertSignIntegrationRule(SignIntegrationRuleEntity signIntegrationRuleEntity) throws Exception
	{
		int rcdNum=signIntegrationRuleSetMapper.insertSignIntegrationRule(signIntegrationRuleEntity);		
		return rcdNum;
	}
	//插入非连续签到积分规则
	public int insertSignIntegrationRules(SignIntegrationRuleEntity signIntegrationRuleEntity) throws Exception
	{
		int rcdNum=signIntegrationRuleSetMapper.insertSignIntegrationRule(signIntegrationRuleEntity);		
		return rcdNum;
	}
	//更新连续性签到积分规则sign_integration_rule
	public int updateSignIntegrationRule(SignIntegrationRuleEntity signIntegrationRuleEntity) throws Exception
	{
		int rcdNum=signIntegrationRuleSetMapper.updateSignIntegrationRule(signIntegrationRuleEntity);
		return rcdNum;
	}
	//更新非连续性积分规则
	public int updateSignIntegrationRules(SignIntegrationRuleEntity signIntegrationRuleEntity) throws Exception
	{
		int rcdNum=signIntegrationRuleSetMapper.updateSignIntegrationRule(signIntegrationRuleEntity);
		return rcdNum;
	}

	//插入积分抵现规则
	public int insertIntegrationCashRule(IntegrationCashRuleEntity integrationCashRuleEntity) throws Exception
	{
		int rcdNum=signIntegrationRuleSetMapper.insertIntegrationCashRule(integrationCashRuleEntity);
		return rcdNum;
	}
	//修改积分抵现规则
	public int updateIntegrationCashRule(IntegrationCashRuleEntity integrationCashRuleEntity) throws Exception
	{
		int rcdNum=signIntegrationRuleSetMapper.updateIntegrationCashRule(integrationCashRuleEntity);
		return rcdNum;
	}
	

}
