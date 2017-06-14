package com.ecard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.commontools.data.DataTool;
import com.ecard.config.ResultCode;
import com.ecard.entity.FirstMemberInitiationIntegrationPresentsEntity;
import com.ecard.entity.FirstMemberInitiationStoredTicketPresentsEntity;
import com.ecard.entity.FirstMemberInitiationVoucherTicketPresentsEntity;
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
	
	//查询  顾客首次入会获赠储值卷信息
	public List<FirstMemberInitiationStoredTicketPresentsEntity> selectStoredTicketPresentsInfo() throws Exception
	{
		return firstMemberInitiationPresentsMapper.selectStoredTicketPresentsInfo();
	}
	
	//更新 顾客首次入会获赠储值郑信息
	public String updateStoredTicketPresentsInfo(FirstMemberInitiationStoredTicketPresentsEntity storedTicketPresentsEntity)
	{
		int rcdNum=firstMemberInitiationPresentsMapper.updateStoredTicketPresentsInfo(storedTicketPresentsEntity);
		if(rcdNum==0)
			return DataTool.constructResponse(ResultCode.UNKNOW_ERROR,"更新失败",null);
		else
			return DataTool.constructResponse(ResultCode.OK,"更新成功",null);
	}
	
	//删除 顾客首次入会获赠储值信息
	public String deleteStoredTicketPresentsInfo(String strStoredTicketPresentsId) throws Exception
	{
		int rcdNum=firstMemberInitiationPresentsMapper.deleteStoredTicketPresentsInfo(strStoredTicketPresentsId);
		if(rcdNum==0)
			return DataTool.constructResponse(ResultCode.UNKNOW_ERROR,"删除失败",null);
		else
			return DataTool.constructResponse(ResultCode.OK,"删除成功",null);
	}
	
	//新增 顾客首次入会获赠抵用券信息
	public String insertVoucherTicketPresentsInfo(FirstMemberInitiationVoucherTicketPresentsEntity voucherTicketPresentsEntity) throws Exception
	{
		int rcdNum=firstMemberInitiationPresentsMapper.insertVoucherTicketPresentsInfo(voucherTicketPresentsEntity);
		if(rcdNum==0)
			return DataTool.constructResponse(ResultCode.UNKNOW_ERROR,"新增失败",null);
		else
			return DataTool.constructResponse(ResultCode.OK,"新增成功",null);
	}
	
	//查询 顾客首次入会获赠抵用卷信息
	public List<FirstMemberInitiationVoucherTicketPresentsEntity> selectVoucherTicketPresentsInfo() throws Exception
	{
		return firstMemberInitiationPresentsMapper.selectVoucherTicketPresentsInfo();
	
	}
	
	//批量更新 顾客首次入会获赠抵用卷信息
	@Transactional
	public String updateVoucherTicketPresentsInfo(List<FirstMemberInitiationVoucherTicketPresentsEntity> listVoucherTicketPresentsEntity) throws Exception
	{
		int rcdNum=0;
		int totalNum=0;
		if(listVoucherTicketPresentsEntity!=null)
			totalNum=listVoucherTicketPresentsEntity.size();
		else
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"参数不能为空",null);
		
		for(int i=0;i<totalNum;i++)
		{
			FirstMemberInitiationVoucherTicketPresentsEntity voucherTicketPresentsEntity=listVoucherTicketPresentsEntity.get(i);
			int flag=firstMemberInitiationPresentsMapper.updateVoucherTicketPresentsInfo(voucherTicketPresentsEntity);
			if(flag!=0)
				rcdNum++;
		}
		
		if(rcdNum<totalNum)
			return DataTool.constructResponse(ResultCode.UNKNOW_ERROR,"更新失败",null);
		else
			return DataTool.constructResponse(ResultCode.OK,"更新成功",null);
	}
	
	//删除 顾客首次入会获赠抵用卷信息
	public String deleteVoucherTicketPresentsInfo(String strVoucherTicketPresentsId) throws Exception
	{
		int rcdNum=firstMemberInitiationPresentsMapper.deleteVoucherTicketPresentsInfo(strVoucherTicketPresentsId);
		if(rcdNum==0)
			return DataTool.constructResponse(ResultCode.UNKNOW_ERROR,"删除失败",null);
		else
			return DataTool.constructResponse(ResultCode.OK,"删除成功",null);
	}
		
	
	//批量新增 顾客首次入会获赠抵用券信息+批量修改 顾客首次入会获赠抵用券信息
	@Transactional
	public String insertAndUpdateVoucherTicketPresentsInfo(List<FirstMemberInitiationVoucherTicketPresentsEntity> insertVoucherTicketPresentsEntityList,
			List<FirstMemberInitiationVoucherTicketPresentsEntity> updateVoucherTicketPresentsEntityList) throws Exception
	{
		int insertRcdNum=0;
		int updateRcdNum=0;
		//批量新增
		if(insertVoucherTicketPresentsEntityList!=null&&insertVoucherTicketPresentsEntityList.size()!=0)
			{
			insertRcdNum=firstMemberInitiationPresentsMapper.batchInsertVoucherTicketPresentsInfo(insertVoucherTicketPresentsEntityList);
			}
		//批量修改
		if(updateVoucherTicketPresentsEntityList!=null&&updateVoucherTicketPresentsEntityList.size()!=0)
			{
			for(int i=0;i<updateVoucherTicketPresentsEntityList.size();i++)
				{
				int flag=firstMemberInitiationPresentsMapper.updateVoucherTicketPresentsInfo(updateVoucherTicketPresentsEntityList.get(i));
				if(flag!=0)
					updateRcdNum++;
				}
			}
		//判断操作成败
		if(insertRcdNum!=0||updateRcdNum!=0)
			return DataTool.constructResponse(ResultCode.OK,"操作成功",null);
		else
			{
			return DataTool.constructResponse(ResultCode.UNKNOW_ERROR,"操作失败",null);
			}
		
	}

}
