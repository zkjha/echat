package com.ecard.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.commontools.data.DataTool;
import com.commontools.validate.ValidateTool;
import com.ecard.config.ResultCode;
import com.ecard.entity.UserDefinedPresentsActivityEntity;
import com.ecard.mapper.UserDefinedPresentsMapper;
import com.ecard.entity.RechargePresentsIntegrationEntity;
import com.ecard.entity.RechargePresentsStoredValueEntity;
import com.ecard.entity.RechargePresentsVoucherEntity;

@Service
public class UserDefinedPresentsService {
	@Autowired
	private UserDefinedPresentsMapper userDefinedPresentsMapper;
	
	//新增 活动信息
	@Transactional
	public String inserPresentsActivityInfo(UserDefinedPresentsActivityEntity userDefinedPresentsActivityEntity) throws Exception
	{
		int iInsertNum=0;
		int isExists=0;
		//判断该会员级别在该活动类型下是否已经存在活动数据，若已存在则报错，不存在则执行写入
		if(userDefinedPresentsActivityEntity==null)
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"参数不能为空",null);
			
			isExists=userDefinedPresentsMapper.isExistsTheActivityRecord(userDefinedPresentsActivityEntity);
			if(isExists!=0)
				return DataTool.constructResponse(ResultCode.UNKNOW_ERROR,"新增失败:该会员级别在该活动类型下的活动表记录已经存在",null);
			
			iInsertNum=userDefinedPresentsMapper.inserPresentsActivityInfo(userDefinedPresentsActivityEntity);
			if(iInsertNum==0)
				return DataTool.constructResponse(ResultCode.UNKNOW_ERROR,"新增失败",null);
			else
				return DataTool.constructResponse(ResultCode.OK,"新增成功",null);	
		}
/*
		//更新 活动信息
		@Transactional
		public String updatePresentsActivityInfo(UserDefineduserDefinedPresentsActivityEntity userDefinedPresentsActivityEntity) throws Exception
		{
			int iUpdateNum=0;
			String isExistsStrActivityId=null;
			if(userDefinedPresentsActivityEntity==null)
				return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"参数不能为空",null);
			
			//判断该会员级别在该活动类型下的活动表记录已经存在中，若已存在则报错，不存在则执行写入
			isExistsStrActivityId=userDefinedPresentsMapper.isExistsTheActivityRecordId(userDefinedPresentsActivityEntity);
			if(!ValidateTool.isEmptyStr(isExistsStrActivityId)&&!isExistsStrActivityId.equals(userDefinedPresentsActivityEntity.getStrActivityId()))	
				return DataTool.constructResponse(ResultCode.UNKNOW_ERROR,"更新失败：该会员级别在该活动类型下的活动表记录已经存在",null);
			
			iUpdateNum=userDefinedPresentsMapper.updatePresentsActivityInfo(userDefinedPresentsActivityEntity);
			if(iUpdateNum==0)
				return DataTool.constructResponse(ResultCode.UNKNOW_ERROR,"更新失败",null);
			else
				return DataTool.constructResponse(ResultCode.OK,"更新成功",null);	
		
		}
		
		
		//查询，全部活动表总记录条数
		public int findCount(Map<String,Object> queryMap) throws Exception
		{
			return userDefinedPresentsMapper.findCount(queryMap);
		}
		

		//查询，过期活动表总记录条数
		public int findExpiredCount(Map<String,Object> queryMap) throws Exception
		{
			return userDefinedPresentsMapper.findExpiredCount(queryMap);
		}
		//查询，正常活动表总记录条数
		public int findNormalCount(Map<String,Object> queryMap) throws Exception
		{
			return userDefinedPresentsMapper.findNormalCount(queryMap);
		}
		
		//分页查询 活动状态为"全部" 的活动表及活动的所有属性（如：充值赠送积分，赠送抵用券，赠送储值 
		@Transactional
		public List<UserDefineduserDefinedPresentsActivityEntity> selectRechargePresentsActivityInfo(Map<String,Object> queryMap) throws Exception
		{
			int iListArrayLength=0;
			String strActivityId,strLevelsId,strLevelsName;
			List<UserDefineduserDefinedPresentsActivityEntity> listUserDefineduserDefinedPresentsActivityEntity=null;
			listUserDefineduserDefinedPresentsActivityEntity=userDefinedPresentsMapper.selectRechargePresentsActivityInfo(queryMap);
			if(listUserDefineduserDefinedPresentsActivityEntity!=null)
			{
				//查出活动记录关键字，组装各个属性
				iListArrayLength=listUserDefineduserDefinedPresentsActivityEntity.size();
				for(int i=0;i<iListArrayLength;i++)
				{
					//得到活动实体对象
					UserDefineduserDefinedPresentsActivityEntity UserDefineduserDefinedPresentsActivityEntity=listUserDefineduserDefinedPresentsActivityEntity.get(i);
					strActivityId=UserDefineduserDefinedPresentsActivityEntity.getStrActivityId();	
					//取得属性
					strLevelsId=UserDefineduserDefinedPresentsActivityEntity.getStrLevelsId();
					strLevelsName=userDefinedPresentsMapper.getLevelsNameById(strLevelsId);
					UserDefineduserDefinedPresentsActivityEntity.setStrMemberLevelName(strLevelsName);
					List<RechargePresentsIntegrationEntity> listRechargePresentsIntegrationEntity=userDefinedPresentsMapper.selectAllRechargePresentsIntegration(strActivityId);
					List<RechargePresentsStoredValueEntity> listRechargePresentsStoredValueEntity=userDefinedPresentsMapper.selectAllRechargePresentsStoredValue(strActivityId);
					List<RechargePresentsVoucherEntity> listRechargePresentsVoucherEntity=userDefinedPresentsMapper.selectAllRechargePresentsVoucher(strActivityId);
					UserDefineduserDefinedPresentsActivityEntity.setListRechargePresentsIntegrationEntity(listRechargePresentsIntegrationEntity);
					UserDefineduserDefinedPresentsActivityEntity.setListRechargePresentsStoredValueEntity(listRechargePresentsStoredValueEntity);
					UserDefineduserDefinedPresentsActivityEntity.setListRechargePresentsVoucherEntity(listRechargePresentsVoucherEntity);
					
					
				}
				
			}
			return listUserDefineduserDefinedPresentsActivityEntity;
			
		}
		

		//分页查询 活动状态为"过期" 的活动表及活动的所有属性（如：充值赠送积分，赠送抵用券，赠送储值 
			@Transactional
			public List<UserDefineduserDefinedPresentsActivityEntity> selectExpiredRechargePresentsActivityInfo(Map<String,Object> queryMap) throws Exception
			{
				int iListArrayLength=0;
				String strActivityId,strLevelsId,strLevelsName;
				List<UserDefineduserDefinedPresentsActivityEntity> listUserDefineduserDefinedPresentsActivityEntity=null;
				listUserDefineduserDefinedPresentsActivityEntity=userDefinedPresentsMapper.selectExpiredRechargePresentsActivityInfo(queryMap);
				if(listUserDefineduserDefinedPresentsActivityEntity!=null)
				{
					//查出活动记录关键字，组装各个属性
					iListArrayLength=listUserDefineduserDefinedPresentsActivityEntity.size();
					for(int i=0;i<iListArrayLength;i++)
					{
						//得到活动实体对象
						UserDefineduserDefinedPresentsActivityEntity UserDefineduserDefinedPresentsActivityEntity=listUserDefineduserDefinedPresentsActivityEntity.get(i);
						strActivityId=UserDefineduserDefinedPresentsActivityEntity.getStrActivityId();	
						//取得属性
						strLevelsId=UserDefineduserDefinedPresentsActivityEntity.getStrLevelsId();
						strLevelsName=userDefinedPresentsMapper.getLevelsNameById(strLevelsId);
						UserDefineduserDefinedPresentsActivityEntity.setStrMemberLevelName(strLevelsName);
						List<RechargePresentsIntegrationEntity> listRechargePresentsIntegrationEntity=userDefinedPresentsMapper.selectAllRechargePresentsIntegration(strActivityId);
						List<RechargePresentsStoredValueEntity> listRechargePresentsStoredValueEntity=userDefinedPresentsMapper.selectAllRechargePresentsStoredValue(strActivityId);
						List<RechargePresentsVoucherEntity> listRechargePresentsVoucherEntity=userDefinedPresentsMapper.selectAllRechargePresentsVoucher(strActivityId);
						UserDefineduserDefinedPresentsActivityEntity.setListRechargePresentsIntegrationEntity(listRechargePresentsIntegrationEntity);
						UserDefineduserDefinedPresentsActivityEntity.setListRechargePresentsStoredValueEntity(listRechargePresentsStoredValueEntity);
						UserDefineduserDefinedPresentsActivityEntity.setListRechargePresentsVoucherEntity(listRechargePresentsVoucherEntity);
						
						
					}
					
				}
				return listUserDefineduserDefinedPresentsActivityEntity;
				
			}
			//分页查询 活动状态为"正常" 的活动表及活动的所有属性（如：充值赠送积分，赠送抵用券，赠送储值 
			@Transactional
			public List<UserDefineduserDefinedPresentsActivityEntity> selectNormalRechargePresentsActivityInfo(Map<String,Object> queryMap) throws Exception
			{
				int iListArrayLength=0;
				String strActivityId,strLevelsId,strLevelsName;
				List<UserDefineduserDefinedPresentsActivityEntity> listUserDefineduserDefinedPresentsActivityEntity=null;
				listUserDefineduserDefinedPresentsActivityEntity=userDefinedPresentsMapper.selectNormalRechargePresentsActivityInfo(queryMap);
				if(listUserDefineduserDefinedPresentsActivityEntity!=null)
				{
				//查出活动记录关键字，组装各个属性
					iListArrayLength=listUserDefineduserDefinedPresentsActivityEntity.size();
					for(int i=0;i<iListArrayLength;i++)
					{
					//得到活动实体对象
					UserDefineduserDefinedPresentsActivityEntity UserDefineduserDefinedPresentsActivityEntity=listUserDefineduserDefinedPresentsActivityEntity.get(i);
					strActivityId=UserDefineduserDefinedPresentsActivityEntity.getStrActivityId();	
					//取得属性
					strLevelsId=userDefineduserDefinedPresentsActivityEntity.getStrLevelsId();
					strLevelsName=userDefinedPresentsMapper.getLevelsNameById(strLevelsId);
					UserDefineduserDefinedPresentsActivityEntity.setStrMemberLevelName(strLevelsName);
					List<RechargePresentsIntegrationEntity> listRechargePresentsIntegrationEntity=userDefinedPresentsMapper.selectAllRechargePresentsIntegration(strActivityId);
					List<RechargePresentsStoredValueEntity> listRechargePresentsStoredValueEntity=userDefinedPresentsMapper.selectAllRechargePresentsStoredValue(strActivityId);
					List<RechargePresentsVoucherEntity> listRechargePresentsVoucherEntity=userDefinedPresentsMapper.selectAllRechargePresentsVoucher(strActivityId);
					UserDefineduserDefinedPresentsActivityEntity.setListRechargePresentsIntegrationEntity(listRechargePresentsIntegrationEntity);
					UserDefineduserDefinedPresentsActivityEntity.setListRechargePresentsStoredValueEntity(listRechargePresentsStoredValueEntity);
					UserDefineduserDefinedPresentsActivityEntity.setListRechargePresentsVoucherEntity(listRechargePresentsVoucherEntity);
					}
				}
				return listUserDefineduserDefinedPresentsActivityEntity;
		}
*/
}
