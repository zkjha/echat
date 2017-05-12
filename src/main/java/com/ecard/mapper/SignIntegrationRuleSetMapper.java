package com.ecard.mapper;
import com.ecard.entity.SignIntegrationRuleEntity;

import java.util.List;

import com.ecard.entity.IntegrationCashRuleEntity;
public interface SignIntegrationRuleSetMapper {
	//向sign_integration_rule表中添加规则
	public int insertSignIntegrationRule(SignIntegrationRuleEntity signIntegrationRuleEntity) throws Exception;
	//更新sign_integration_rule表
	public int updateSignIntegrationRule(SignIntegrationRuleEntity signIntegrationRuleEntity) throws Exception;
	//向integration_cash_rule表中添加规则
	public int insertIntegrationCashRule(IntegrationCashRuleEntity integrationCashRuleEntity) throws Exception;
	//更新integration_cash_rule表
	public int updateIntegrationCashRule(IntegrationCashRuleEntity integrationCashEntity) throws Exception;
	//显示全部非连续签到积分规则
	public List<SignIntegrationRuleEntity> findAllSignIntegrationRules() throws Exception;
	//显示全部连续签到积分规则
	public List<SignIntegrationRuleEntity> findAllSignIntegrationRule() throws Exception;
}
