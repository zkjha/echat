package com.ecard.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ecard.entity.FirstMemberInitiationIntegrationPresentsEntity;
import com.ecard.entity.FirstMemberInitiationStoredTicketPresentsEntity;
import com.ecard.entity.FirstMemberInitiationVoucherTicketPresentsEntity;

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
	//更新 顾客首次入会获赠储值郑信息
	public int updateStoredTicketPresentsInfo(FirstMemberInitiationStoredTicketPresentsEntity storedTicketPresentsEntity);
	//删除 顾客首次入会获赠储值信息
	public int deleteStoredTicketPresentsInfo(String strStoredTicketPresentsId) throws Exception;
	//新增 顾客首次入会获赠抵用券信息
	public int insertVoucherTicketPresentsInfo(FirstMemberInitiationVoucherTicketPresentsEntity voucherTicketPresentsEntity) throws Exception;
	//查询 顾客首次入会获赠抵用卷信息
	public List<FirstMemberInitiationVoucherTicketPresentsEntity> selectVoucherTicketPresentsInfo() throws Exception;
	//批量更新 顾客首次入会获赠抵用卷信息
	public int updateVoucherTicketPresentsInfo(FirstMemberInitiationVoucherTicketPresentsEntity voucherTicketPresentsEntity) throws Exception;
	//删除 顾客首次入会获赠抵用卷信息
	public int deleteVoucherTicketPresentsInfo(String strVoucherTicketPresentsId) throws Exception;
	//批量新增 顾客首次入会获赠抵用券信息
	public int batchInsertVoucherTicketPresentsInfo(@Param("insertVoucherTicketPresentsEntityList")List<FirstMemberInitiationVoucherTicketPresentsEntity> insertVoucherTicketPresentsEntityList) throws Exception;
	
}
