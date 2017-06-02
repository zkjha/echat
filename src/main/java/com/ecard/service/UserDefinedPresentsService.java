package com.ecard.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.commontools.data.DataTool;
import com.commontools.validate.ValidateTool;
import com.ecard.config.ResultCode;
import com.ecard.entity.UserDefinedPresentsActivityEntity;
import com.ecard.entity.UserDefinedPresentsIntegrationEntity;
import com.ecard.entity.UserDefinedPresentsStoredValueEntity;
import com.ecard.mapper.UserDefinedPresentsMapper;


@Service
public class UserDefinedPresentsService {
	@Autowired
	private UserDefinedPresentsMapper userDefinedPresentsMapper;
	
	//新增 活动信息
	@Transactional
	public String insertUserDefinedPresentsActivityInfo(UserDefinedPresentsActivityEntity userDefinedPresentsActivityEntity) throws Exception
	{
		int iInsertNum=0;
		int isExists=0;
		//判断该会员级别在该活动类型下是否已经存在活动数据，若已存在则报错，不存在则执行写入
		if(userDefinedPresentsActivityEntity==null)
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"参数不能为空",null);
			
			isExists=userDefinedPresentsMapper.isExistsTheActivityRecord(userDefinedPresentsActivityEntity);
			if(isExists!=0)
				return DataTool.constructResponse(ResultCode.UNKNOW_ERROR,"新增失败:该会员级别在该活动类型下的活动表记录已经存在",null);
			
			iInsertNum=userDefinedPresentsMapper.insertUserDefinedPresentsActivityInfo(userDefinedPresentsActivityEntity);
			if(iInsertNum==0)
				return DataTool.constructResponse(ResultCode.UNKNOW_ERROR,"新增失败",null);
			else
				return DataTool.constructResponse(ResultCode.OK,"新增成功",null);	
		}

	
	
		//更新 活动信息
		@Transactional
		public String updateUserDefinedPresentsActivityInfo(UserDefinedPresentsActivityEntity userDefinedPresentsActivityEntity) throws Exception
		{
			int iUpdateNum=0;
			String isExistsStrActivityId=null;
			if(userDefinedPresentsActivityEntity==null)
				return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"参数不能为空",null);
			
			//判断该会员级别在该活动类型下的活动表记录是否已存在，若已存在则报错，不存在则执行写入
			isExistsStrActivityId=userDefinedPresentsMapper.isExistsTheActivityRecordId(userDefinedPresentsActivityEntity);
			if(!ValidateTool.isEmptyStr(isExistsStrActivityId)&&!isExistsStrActivityId.equals(userDefinedPresentsActivityEntity.getStrActivityId()))	
				return DataTool.constructResponse(ResultCode.UNKNOW_ERROR,"更新失败：该会员级别在该活动类型下的活动表记录已经存在",null);
			
			iUpdateNum=userDefinedPresentsMapper.updateUserDefinedPresentsActivityInfo(userDefinedPresentsActivityEntity);
			if(iUpdateNum==0)
				return DataTool.constructResponse(ResultCode.UNKNOW_ERROR,"更新失败",null);
			
			else
				return DataTool.constructResponse(ResultCode.OK,"更新成功",null);	
		
		}
	
	//查询 自定义活动单条信息
	public UserDefinedPresentsActivityEntity selectUserDefinedPresentActivityById(String strActivityId) throws Exception
	{
	return userDefinedPresentsMapper.selectUserDefinedPresentActivityById(strActivityId);
	}
		
	//插入自定义赠送积分
	public String insertUserDefinedPresentsIntegration(UserDefinedPresentsIntegrationEntity userDefinedPresentsIntegrationEntity) throws Exception
	{
		int iUpdateNum=0;
		String strActivityId=userDefinedPresentsIntegrationEntity.getStrActivityId();
		String strPresentsIntegrationId=userDefinedPresentsMapper.isExistsTheRecord(strActivityId);
		//如果记录存在，则履盖原有数据，不存在则新增

		if(ValidateTool.isEmptyStr(strPresentsIntegrationId))
			iUpdateNum=userDefinedPresentsMapper.insertUserDefinedPresentsIntegration(userDefinedPresentsIntegrationEntity);//新增
		else
			{
			//更改实体对象里的关键字行
			userDefinedPresentsIntegrationEntity.setStrPresentsIntegrationId(strPresentsIntegrationId);
			iUpdateNum=userDefinedPresentsMapper.updateUserDefinedPresentsIntegration(userDefinedPresentsIntegrationEntity);//更新
			
			}
		if(iUpdateNum==0)
			return DataTool.constructResponse(ResultCode.UNKNOW_ERROR,"操作失败",null);
		
		else
			return DataTool.constructResponse(ResultCode.OK,"操作成功",null);	
	
	}
	
	
	
	//更新 自定义赠送积分信息 
	public String updateUserDefinedPresentsIntegration(UserDefinedPresentsIntegrationEntity userDefinedPresentsIntegrationEntity) throws Exception
	{
		int iUpdateNum=0;
		iUpdateNum=userDefinedPresentsMapper.updateUserDefinedPresentsIntegration(userDefinedPresentsIntegrationEntity);//更新
		if(iUpdateNum==0)
			return DataTool.constructResponse(ResultCode.UNKNOW_ERROR,"操作失败",null);
		else
			return DataTool.constructResponse(ResultCode.OK,"操作成功",null);	
	}
	
	//删除 自定义赠送积分信息
	public String deleteUserDefinedPresentsIntegrationEntity(String strPresentsIntegrationId) throws Exception
	{
	int iDeleteNum=0;
	iDeleteNum=userDefinedPresentsMapper.deleteUserDefinedPresentsIntegrationEntity(strPresentsIntegrationId);
	if(iDeleteNum==0)
		return DataTool.constructResponse(ResultCode.UNKNOW_ERROR,"操作失败",null);
	else
		return DataTool.constructResponse(ResultCode.OK,"操作成功",null);	
	}
	
	//查询一条自定义赠送积分信息
	public UserDefinedPresentsIntegrationEntity selectAllUserDefinedPresentsIntegration(String strActivityId) throws Exception
	{
		if(ValidateTool.isEmptyStr(strActivityId))
			return null;
		else
			return userDefinedPresentsMapper.selectAllUserDefinedPresentsIntegration(strActivityId);
	
	}
	
	//新增一条UserDefinedPresentsStoredValueEntity记录
	@Transactional(rollbackFor=Exception.class)
	public String insertUserDefinedPresentsStoredValueEntity(UserDefinedPresentsStoredValueEntity userDefinedPresentsStoredValueEntity) throws Exception
	{
		int iAffectNum=0;
		//查出关联自定义赠送活动的赠送储积信息，如果已经存在则履盖，不存在则新增
		String strActivity=userDefinedPresentsStoredValueEntity.getStrActivityId();
		if(ValidateTool.isEmptyStr(strActivity))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数不能为空", null);
		String strPresentsStoredValueId=userDefinedPresentsMapper.findPresentsStoredValueId(strActivity);
		
		if(ValidateTool.isEmptyStr(strPresentsStoredValueId))
			 iAffectNum =userDefinedPresentsMapper.insertUserDefinedPresentsStoredValueEntity(userDefinedPresentsStoredValueEntity);
		else
			 {
			userDefinedPresentsStoredValueEntity.setStrPresentsStoredValueId(strPresentsStoredValueId);
			iAffectNum =userDefinedPresentsMapper.updateUserDefinedPresentsStoredValueEntity(userDefinedPresentsStoredValueEntity);
			 }
			
		if (0 == iAffectNum)
	    {
	        return DataTool.constructResponse(ResultCode.UNKNOW_ERROR, "数据库操作失败", null);
	    }
	    return DataTool.constructResponse(ResultCode.OK,"添加成功", null);
	}

	
	//更新一条自定义赠送储值信息UserDefinedPresentsStoredValueEntity记录
	public String updateUserDefinedPresentsStoredValueEntity(UserDefinedPresentsStoredValueEntity userDefinedPresentsStoredValueEntity) throws Exception
	{
	    int iAffectNum =userDefinedPresentsMapper.updateUserDefinedPresentsStoredValueEntity(userDefinedPresentsStoredValueEntity);
	    if (0 == iAffectNum)
	    {
	        return DataTool.constructResponse(ResultCode.NO_DATA, "数据记录ID不存在", null);
	    }
	    return DataTool.constructResponse(ResultCode.OK,"修改成功", null);
	}
	
	//删除一条UserDefinedPresentsStoredValueEntity记录
	public String deleteUserDefinedPresentsStoredValueEntity(String strPresentsStoredValueId) throws Exception
	{
	    int iAffectNum = userDefinedPresentsMapper.deleteUserDefinedPresentsStoredValueEntity(strPresentsStoredValueId);
	    if (0 == iAffectNum)
	    {
	        return DataTool.constructResponse(ResultCode.NO_DATA, "无数据", null);
	    }
	    return DataTool.constructResponse(ResultCode.OK,"删除成功", null);
	}
	
	//查询一条 自定义活动赠送储值 信息
	public UserDefinedPresentsStoredValueEntity selectAllUserDefinedPresentsStoredValueEntity(String strActivityId) throws Exception
	{
		if(ValidateTool.isEmptyStr(strActivityId))
			return null;
		else
			return userDefinedPresentsMapper.selectAllUserDefinedPresentsStoredValueEntity(strActivityId);
	}

}
