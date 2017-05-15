package com.ecard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.commontools.data.DataTool;
import com.ecard.config.ResultCode;
import com.ecard.entity.FirstMemberInitiationIntegrationPresentsEntity;
import com.ecard.entity.FirstMemberInitiationStoredTicketPresentsEntity;
import com.ecard.mapper.FirstMemberInitiationPresentsMapper;

@Service
public class FirstMemberInitiationPresentsService {
	
	@Autowired
	private FirstMemberInitiationPresentsMapper firstMemberInitiationPresentsMapper;
	//新增 顾客首次入会获赠积分信息
	public String insertIntegrationPresents(FirstMemberInitiationIntegrationPresentsEntity IntegrationPresentsEntity) throws Exception
	{
		int affectNum=firstMemberInitiationPresentsMapper.insertIntegrationPresents(IntegrationPresentsEntity);
		if(affectNum!=0)
			return DataTool.constructResponse(ResultCode.OK,"插入首次入会获赠积分信息成功",null);
		else
			return DataTool.constructResponse(ResultCode.UNKNOW_ERROR,"插入首次入会获赠积分信息失败",null);
	
	}
	
	//查询顾客入会获赠积分信息
	public FirstMemberInitiationIntegrationPresentsEntity selectFirstMemberInitiationIntegrationPresents() throws Exception
	{
		return firstMemberInitiationPresentsMapper.selectFirstMemberInitiationIntegrationPresents();
	
	}
	//更新 顾客入会获赠积分信息
	public String updateIntegrationPresents(FirstMemberInitiationIntegrationPresentsEntity IntegrationPresentsEntity) throws Exception
	{
		int affectNum=firstMemberInitiationPresentsMapper.updateIntegrationPresents(IntegrationPresentsEntity);
		if(affectNum!=0)
			return DataTool.constructResponse(ResultCode.OK,"更新首次入会获赠积分信息成功",null);
		else
			return DataTool.constructResponse(ResultCode.UNKNOW_ERROR,"更新首次入会获赠积分失败",null);
	}
	
	//删除 入会获积分信息
	public String deleteFirstMemberInitiationIntegrationPresents(String strIntegrationPresentsId) throws Exception
	{
		int rcdNum=firstMemberInitiationPresentsMapper.deleteFirstMemberInitiationIntegrationPresents(strIntegrationPresentsId);
		if(rcdNum==0)
			return DataTool.constructResponse(ResultCode.UNKNOW_ERROR,"删除失败",null);
		else
			return DataTool.constructResponse(ResultCode.OK,"删除成功",null);
			
	}
	
	//新增 顾客首次入会获赠储值券信息
	public String insertStoredTicketPresentsInfo(FirstMemberInitiationStoredTicketPresentsEntity storedTicketPresentsEntity) throws Exception
	{
		int rcdNum=firstMemberInitiationPresentsMapper.insertStoredTicketPresentsInfo(storedTicketPresentsEntity);
		if(rcdNum==0)
			return DataTool.constructResponse(ResultCode.UNKNOW_ERROR,"新增失败",null);
		else
			return DataTool.constructResponse(ResultCode.OK,"新增成功",null);
			
	}

}
