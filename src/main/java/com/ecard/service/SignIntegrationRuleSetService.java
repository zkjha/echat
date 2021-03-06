package com.ecard.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecard.entity.SignIntegrationRuleEntity;
import com.commontools.data.DataTool;
import com.ecard.config.ResultCode;
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
		String strSignId="";
		int rcdNum=0;
		//检查数据库中是否已经存在数据，如果存在则执行更新，不存在则写入
		strSignId=signIntegrationRuleSetMapper.findTheRecord();
		if(strSignId.isEmpty())
			rcdNum=signIntegrationRuleSetMapper.insertSignIntegrationRule(signIntegrationRuleEntity);
		else
		{
			//更新
			signIntegrationRuleEntity.setStrSignId(strSignId);
			rcdNum=signIntegrationRuleSetMapper.updateSignIntegrationRule(signIntegrationRuleEntity);
		}
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
	
	//更新非连续性积分规则;循环更新
	@Transactional
	public String updateSignIntegrationRules(List<SignIntegrationRuleEntity> listSignIntegrationRuleEntity) throws Exception
	{
			
		int updateNum=0;
		for(int i=0;i<listSignIntegrationRuleEntity.size();i++)
		{
			SignIntegrationRuleEntity signIntegrationRuleEntity=listSignIntegrationRuleEntity.get(i);
			int affectNum=signIntegrationRuleSetMapper.updateSignIntegrationRule(signIntegrationRuleEntity);
			if(affectNum==1)
				updateNum++;
		}
		
		if(updateNum==listSignIntegrationRuleEntity.size())
			return DataTool.constructResponse(ResultCode.OK,"更新非连续性签到积分规则成功", null);
		else
			return DataTool.constructResponse(ResultCode.UNKNOW_ERROR,"更新非连续性签到积分规则失败", null);
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
	//显示全部非连续性签到积分规则
	public List<SignIntegrationRuleEntity> findAllSignIntegrationRules() throws Exception
	{
		return signIntegrationRuleSetMapper.findAllSignIntegrationRules();
		
	}
	
	//显示全部连续性签到积分规则
	public List<SignIntegrationRuleEntity> findAllSignIntegrationRule() throws Exception
	{
		return signIntegrationRuleSetMapper.findAllSignIntegrationRule();
			
	}
	//查询全部积分抵现规则
	public List<IntegrationCashRuleEntity> findAllIntegrationCashRule() throws Exception
	{
		return signIntegrationRuleSetMapper.findAllIntegrationCashRule();
	}
	
	//删除签到积分规则
	public int deleteSignIntegrationRule(String strSignId) throws Exception
	{

		return signIntegrationRuleSetMapper.deleteSignIntegrationRule(strSignId);
	}
	
	//删除积分抵现规则
	public int deleteIntegrationCashRule(String strId) throws Exception
	{

	return signIntegrationRuleSetMapper.deleteIntegrationCashRule(strId);
	}
	
}
