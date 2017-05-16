package com.ecard.mapper;

import java.util.List;

import com.ecard.entity.FirstMemberInitiationIntegrationPresentsEntity;
import com.ecard.entity.FirstMemberInitiationStoredTicketPresentsEntity;

public interface FirstMemberInitiationPresentsMapper {
	//新增 顾客首次入会获赠积分信息
	public int insertIntegrationPresents(FirstMemberInitiationIntegrationPresentsEntity IntegrationPresentsEntity) throws Exception;
	//查询顾客入会获赠积分信息
	public FirstMemberInitiationIntegrationPresentsEntity selectFirstMemberInitiationIntegrationPresents() throws Exception;
	//更新 顾客入会获赠积分信息
	public int updateIntegrationPresents(FirstMemberInitiationIntegrationPresentsEntity IntegrationPresentsEntity) throws Exception;
	//删除 入会获积分信息
	public int deleteFirstMemberInitiationIntegrationPresents(String strIntegrationPresentsId) throws Exception;
	//新增 顾客首次入会获赠储值券信息
	public int insertStoredTicketPresentsInfo(FirstMemberInitiationStoredTicketPresentsEntity storedTicketPresentsEntity) throws Exception;
	//查询  顾客首次入会获赠储值卷信息
	public List<FirstMemberInitiationStoredTicketPresentsEntity> selectStoredTicketPresentsInfo() throws Exception;
}
