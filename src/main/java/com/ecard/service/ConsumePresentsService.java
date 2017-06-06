package com.ecard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.commontools.data.DataTool;
import com.commontools.validate.ValidateTool;
import com.ecard.config.ResultCode;
import com.ecard.entity.ConsumePresentsActivityEntity;
import com.ecard.mapper.ConsumePresentsMapper;
@Service
public class ConsumePresentsService
{
	@Autowired
	private ConsumePresentsMapper consumePresentsMapper;
	//新增 消费赠送活动信息
	@Transactional
	public String insertConsumePresentsActivityEntity(ConsumePresentsActivityEntity consumePresentsActivityEntity) throws Exception
	{
		int iInsertNum=0;
		int isExists=0;
		//判断该会员级别在该活动类型下是否已经存在活动数据，若已存在则报错，不存在则执行写入
		if(consumePresentsActivityEntity==null)
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"参数不能为空",null);
			
		isExists=consumePresentsMapper.isExistsTheActivityRecord(consumePresentsActivityEntity);
		if(isExists!=0)
			return DataTool.constructResponse(ResultCode.UNKNOW_ERROR,"新增失败:该会员级别在该活动类型下的活动表记录信息已经存在",null);
			
		iInsertNum=consumePresentsMapper.insertConsumePresentsActivityEntity(consumePresentsActivityEntity);
		if(iInsertNum==0)
			return DataTool.constructResponse(ResultCode.UNKNOW_ERROR,"新增失败",null);
		else
				return DataTool.constructResponse(ResultCode.OK,"新增成功",null);	
	}
	
	
	//更新消费赠送活动信息
	@Transactional
	public String updateConsumePresentsActivityEntity(ConsumePresentsActivityEntity consumePresentsActivityEntity) throws Exception
	{
		int iUpdateNum=0;
		String isExistsStrActivityId=null;
		if(consumePresentsActivityEntity==null)
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"参数不能为空",null);
		
		//判断该会员级别在该活动类型下的活动表记录是否已存在，若已存在则报错，不存在则执行写入
		isExistsStrActivityId=consumePresentsMapper.isExistsTheActivityRecordId(consumePresentsActivityEntity);
		if(!ValidateTool.isEmptyStr(isExistsStrActivityId)&&!isExistsStrActivityId.equals(consumePresentsActivityEntity.getStrActivityId()))	
			return DataTool.constructResponse(ResultCode.UNKNOW_ERROR,"更新失败：该会员级别在该活动类型下的活动表记录已经存在",null);
		
		iUpdateNum=consumePresentsMapper.updateConsumePresentsActivityInfo(consumePresentsActivityEntity);
		if(iUpdateNum==0)
			return DataTool.constructResponse(ResultCode.UNKNOW_ERROR,"更新失败",null);
		
		else
			return DataTool.constructResponse(ResultCode.OK,"更新成功",null);	
		
	}

}
