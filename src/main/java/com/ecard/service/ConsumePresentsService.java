package com.ecard.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.commontools.data.DataTool;
import com.commontools.validate.ValidateTool;
import com.ecard.config.ResultCode;
import com.ecard.entity.ConsumePresentsActivityEntity;
import com.ecard.entity.ConsumePresentsIntegrationEntity;
import com.ecard.entity.ConsumePresentsStoredValueEntity;
import com.ecard.entity.ConsumePresentsVoucherEntity;
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
	

	
	//查询一条消费赠送活动规则信息consumePresentsActivityEntity记录
	@Transactional(rollbackFor=Exception.class)
	public ConsumePresentsActivityEntity selectConsumePresentsActivityEntity(String strActivityId) throws Exception
	{
	if(ValidateTool.isEmptyStr(strActivityId))
		return null;
	return consumePresentsMapper.selectConsumePresentsActivityEntity(strActivityId);
	}

	
	//查询一条刚新建的消费赠送活动规则信息consumePresentsActivityEntity记录
	@Transactional(rollbackFor=Exception.class)
	public ConsumePresentsActivityEntity selectConsumePresentsActivityInfo() throws Exception
	{
	
		return consumePresentsMapper.selectConsumePresentsActivityInfo();
	}
	
	//批量插入消费赠送积分规则信息consumePresentsIntegrationEntity
	@Transactional
	public String batchInsertConsumePresentsIntegrationEntity(List<ConsumePresentsIntegrationEntity> listConsumePresentsIntegrationEntity) throws Exception
	{
		int iAffectNum=0,iObjLength,iflag=0;
		iObjLength=listConsumePresentsIntegrationEntity.size();
		if(listConsumePresentsIntegrationEntity==null||iObjLength==0)
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"参数不能为空",null);
		else
		{
			for(int i=0;i<iObjLength;i++)
			{	
				ConsumePresentsIntegrationEntity consumePresentsIntegrationEntity=listConsumePresentsIntegrationEntity.get(i);
				iAffectNum=consumePresentsMapper.batchInsertConsumePresentsIntegrationEntity(consumePresentsIntegrationEntity);
				if(iAffectNum!=0)
					iflag=iflag+1;
			}
			if(iflag==iObjLength)
				return DataTool.constructResponse(ResultCode.OK,"批量插入成功",null);
			else
				return	DataTool.constructResponse(ResultCode.UNKNOW_ERROR,"操作失败",null);
		}
	}

	//批量更新消费赠送积分规则信息consumePresentsIntegrationEntity
	@Transactional
	public String batchUpdateConsumePresentsIntegrationEntity(List<ConsumePresentsIntegrationEntity> listConsumePresentsIntegrationEntity) throws Exception
	{
		int iAffectNum=0,iObjLength,iflag=0;
		iObjLength=listConsumePresentsIntegrationEntity.size();
		if(listConsumePresentsIntegrationEntity==null||iObjLength==0)
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"参数不能为空",null);
		else
		{
			for(int i=0;i<iObjLength;i++)
			{	
				ConsumePresentsIntegrationEntity consumePresentsIntegrationEntity=listConsumePresentsIntegrationEntity.get(i);
				iAffectNum=consumePresentsMapper.batchUpdateConsumePresentsIntegrationEntity(consumePresentsIntegrationEntity);
				if(iAffectNum!=0)
					iflag=iflag+1;

			}

			if(iflag==iObjLength)
				return DataTool.constructResponse(ResultCode.OK,"批量更新成功",null);
			else
				return	DataTool.constructResponse(ResultCode.UNKNOW_ERROR,"更新失败",null);
		}
	}

	
	//删除一条消费赠送积分规则信息consumePresentsIntegrationEntity记录
	@Transactional(rollbackFor=Exception.class)
	public String deleteConsumePresentsIntegrationEntity(String strIntegrationId) throws Exception
	{
		int iAffectNum=consumePresentsMapper.deleteConsumePresentsIntegrationEntity(strIntegrationId);
		if(0==iAffectNum)
			return DataTool.constructResponse(ResultCode.UNKNOW_ERROR, "数据库操作失败",null);
		else
			return DataTool.constructResponse(ResultCode.OK,"删除成功",null);
	}

	

	//查询消费赠送积分规则信息ConsumePresentsIntegrationEntity列表
	public List<ConsumePresentsIntegrationEntity> selectAllConsumePresentsIntegrationEntity(String strActivity) throws Exception
	{
		return consumePresentsMapper.selectAllConsumePresentsIntegrationEntity(strActivity);
	}
	
	//批量插入消费赠送储值规则信息consumePresentsStoredValueEntity
	@Transactional
	public String batchInsertConsumePresentsStoredValueEntity(List<ConsumePresentsStoredValueEntity> listConsumePresentsStoredValueEntity) throws Exception
	{
	int iAffectNum=0,iObjLength;
	iObjLength=listConsumePresentsStoredValueEntity.size();
	if(listConsumePresentsStoredValueEntity==null||iObjLength==0)
		return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"参数不能为空",null);
	for(int i=0;i<iObjLength;i++)
	{	ConsumePresentsStoredValueEntity consumePresentsStoredValueEntity=listConsumePresentsStoredValueEntity.get(i);
		iAffectNum=consumePresentsMapper.batchInsertConsumePresentsStoredValueEntity(consumePresentsStoredValueEntity);
	}
	if(iAffectNum==0)
		return DataTool.constructResponse(ResultCode.UNKNOW_ERROR,"操作失败",null);
	else
		return DataTool.constructResponse(ResultCode.OK,"批量插入成功",null);
	}

	
	//批量更新消费赠送储值规则信息consumePresentsStoredValueEntity
	@Transactional
	public String batchUpdateConsumePresentsStoredValueEntity(List<ConsumePresentsStoredValueEntity> listConsumePresentsStoredValueEntity) throws Exception
	{
	int iAffectNum=0,iObjLength;
	iObjLength=listConsumePresentsStoredValueEntity.size();
	if(listConsumePresentsStoredValueEntity==null||iObjLength==0)
		return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"参数不能为空",null);
	for(int i=0;i<iObjLength;i++)
	{	ConsumePresentsStoredValueEntity consumePresentsStoredValueEntity=listConsumePresentsStoredValueEntity.get(i);
		iAffectNum=consumePresentsMapper.batchUpdateConsumePresentsStoredValueEntity(consumePresentsStoredValueEntity);
	}
	if(iAffectNum==0)
		return DataTool.constructResponse(ResultCode.UNKNOW_ERROR,"操作失败",null);
	else
		return DataTool.constructResponse(ResultCode.OK,"批量更新成功",null);
	}
	
	//删除一条消费赠送储值规则信息consumePresentsStoredValueEntity记录
	@Transactional(rollbackFor=Exception.class)
	public String deleteConsumePresentsStoredValueEntity(String strStoredTicketId) throws Exception
	{
		int iAffectNum=consumePresentsMapper.deleteConsumePresentsStoredValueEntity(strStoredTicketId);
	if(0==iAffectNum)
		return DataTool.constructResponse(ResultCode.UNKNOW_ERROR, "数据库操作失败",null);
	else
		return DataTool.constructResponse(ResultCode.OK,"删除成功",null);
	}
	
	
	//查询消费赠送储值规则信息ConsumePresentsStoredValueEntity列表
	public List<ConsumePresentsStoredValueEntity> selectAllConsumePresentsStoredValueEntity(String strActivity) throws Exception
	{
		return consumePresentsMapper.selectAllConsumePresentsStoredValueEntity(strActivity);
	}
	
	//批量插入消费赠送抵用券规则信息consumePresentsVoucherEntity
	@Transactional
	public String batchInsertConsumePresentsVoucherEntity(List<ConsumePresentsVoucherEntity> listConsumePresentsVoucherEntity) throws Exception
	{
	int iAffectNum=0,iObjLength;
	iObjLength=listConsumePresentsVoucherEntity.size();
	if(listConsumePresentsVoucherEntity==null||iObjLength==0)
		return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"参数不能为空",null);
	for(int i=0;i<iObjLength;i++)
	{	
		ConsumePresentsVoucherEntity consumePresentsVoucherEntity=listConsumePresentsVoucherEntity.get(i);
		iAffectNum=consumePresentsMapper.batchInsertConsumePresentsVoucherEntity(consumePresentsVoucherEntity);
	}
	if(iAffectNum==0)
		return DataTool.constructResponse(ResultCode.UNKNOW_ERROR,"批量插入失败",null);
	else
		return DataTool.constructResponse(ResultCode.OK,"批量插入成功",null);
	}
	
	
	//批量更新消费赠送抵用券规则信息consumePresentsVoucherEntity
	@Transactional
	public String batchUpdateConsumePresentsVoucherEntity(List<ConsumePresentsVoucherEntity> listConsumePresentsVoucherEntity) throws Exception
	{
		int iAffectNum=0,iObjLength;
		iObjLength=listConsumePresentsVoucherEntity.size();
		if(listConsumePresentsVoucherEntity==null||iObjLength==0)
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"参数不能为空",null);
		for(int i=0;i<iObjLength;i++)
		{	
			ConsumePresentsVoucherEntity consumePresentsVoucherEntity=listConsumePresentsVoucherEntity.get(i);
			iAffectNum=consumePresentsMapper.batchUpdateConsumePresentsVoucherEntity(consumePresentsVoucherEntity);
		}
		if(iAffectNum==0)
			return DataTool.constructResponse(ResultCode.UNKNOW_ERROR,"批量更新失败",null);
		else
			return DataTool.constructResponse(ResultCode.OK,"批量更新成功",null);
	}
	
	//删除一条消费赠送抵用券规则信息consumePresentsVoucherEntity记录
	@Transactional(rollbackFor=Exception.class)
	public String deleteConsumePresentsVoucherEntity(String strConsumePresentsVoucherId) throws Exception
	{
	int iAffectNum=consumePresentsMapper.deleteConsumePresentsVoucherEntity(strConsumePresentsVoucherId);
	if(0==iAffectNum)
	{
		return DataTool.constructResponse(ResultCode.UNKNOW_ERROR, "数据库操作失败",null);
	}
	return DataTool.constructResponse(ResultCode.OK,"删除成功",null);
	}
	
	//查询消费赠送抵用券规则信息ConsumePresentsVoucherEntity列表
	public List<ConsumePresentsVoucherEntity> selectAllConsumePresentsVoucherEntity(String strActivity) throws Exception
	{
		return consumePresentsMapper.selectAllConsumePresentsVoucherEntity(strActivity);
	}
	
	//分页浏览---删除接口 删除 关联的积分，储值，抵用券规则信息
	@Transactional
	public String deleteConsumePresentsActivityInfo(String strActivityId) throws Exception
	{
		int iAffectNum=0;
		consumePresentsMapper.deleteConsumePresentsIntegrationInfo(strActivityId);
		consumePresentsMapper.deleteConsumePresentsStoredValueInfo(strActivityId);
		consumePresentsMapper.deleteConsumePresentsVoucherInfo(strActivityId);
		iAffectNum=consumePresentsMapper.deleteConsumePresentsActivityInfo(strActivityId);
		if(0==iAffectNum)
			return DataTool.constructResponse(ResultCode.UNKNOW_ERROR, "数据库操作失败",null);
		return DataTool.constructResponse(ResultCode.OK,"删除成功",null);
	}

	
	//查询消费赠送活动在特定会员级别及特定状态下的记录条数
	public int findTheRecordCount(Map<String,Object> queryMap) throws Exception
	{
		return consumePresentsMapper.findTheRecordCount(queryMap);
	}
		
	//分页查询消费赠送活动信息
	@Transactional
	public List<ConsumePresentsActivityEntity> selectAllConsumePresentsActivity(Map<String,Object> queryMap) throws Exception
	{
		int iObjNum=0;
		List<ConsumePresentsActivityEntity> listConsumePresentsActivityEntity=consumePresentsMapper.selectAllConsumePresentsActivity(queryMap);
		iObjNum=listConsumePresentsActivityEntity.size();
		if(listConsumePresentsActivityEntity==null||iObjNum==0)
			return null;
		for(int i=0;i<iObjNum;i++)
		{
			//将自定义活动状态属性写入对象
			String strActivityStatus="";
			String strActivityId="";
			int iIntegrationEnabled=0;
			int iStoredTicketEnabled=0;
			int iVoucherTicketEnabled=0;
			//不用String strMemberLevelId=(String)queryMap.get("strSearchMemberLevelId");
			String strMemberLevelName;
			String strActivityEndTime=listConsumePresentsActivityEntity.get(i).getStrActivityEndTime();
			strActivityId=listConsumePresentsActivityEntity.get(i).getStrActivityId();
			String strLevelsId=listConsumePresentsActivityEntity.get(i).getStrLevelsId();
			//对活动状态：如正常和过期进行判断
			String strCurrentTime=(String)queryMap.get("strCurrentDate");
			if(strActivityEndTime.compareTo(strCurrentTime)<0)
				strActivityStatus="过期";
			
			if(strActivityEndTime.compareTo(strCurrentTime)>=0)
				strActivityStatus="正常";
			ConsumePresentsActivityEntity consumePresentsActivityEntity=listConsumePresentsActivityEntity.get(i);
			consumePresentsActivityEntity.setStrActivityStatus(strActivityStatus);
			
			//分别对关联该活动的，积分，储值和抵用券的禁启用进行判断
			//默认查已关联该活动的积分，储值和抵用券的已启用状态信息，如果存在一个已启用的，则分页显示为打勾状态
			iIntegrationEnabled=consumePresentsMapper.selectIntegrationEnabled(strActivityId);
			iStoredTicketEnabled=consumePresentsMapper.selectStoredTicketEnabled(strActivityId);
			iVoucherTicketEnabled=consumePresentsMapper.selectVoucherTicketEnabled(strActivityId);
			consumePresentsActivityEntity.setiIntegrationEnabled(iIntegrationEnabled);
			consumePresentsActivityEntity.setiStoredTicketEnabled(iStoredTicketEnabled);
			consumePresentsActivityEntity.setiVoucherTicketEnabled(iVoucherTicketEnabled);
			
			//查出该条活动信息所对应的会员级别名称
			strMemberLevelName=consumePresentsMapper.findMemberLevelNameById(strLevelsId);
			consumePresentsActivityEntity.setStrMemberLevelName(strMemberLevelName);

		}
		
		return listConsumePresentsActivityEntity;
		
	}
	
}
