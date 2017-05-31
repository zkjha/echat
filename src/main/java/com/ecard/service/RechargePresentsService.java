package com.ecard.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.commontools.data.DataTool;
import com.commontools.validate.ValidateTool;
import com.ecard.config.ResultCode;

import com.ecard.entity.RechargePresentsActivityEntity;
import com.ecard.entity.RechargePresentsIntegrationEntity;
import com.ecard.entity.RechargePresentsStoredValueEntity;
import com.ecard.entity.RechargePresentsVoucherEntity;
import com.ecard.mapper.RechargePresentsMapper;

@Service
public class RechargePresentsService {
	@Autowired
	private RechargePresentsMapper rechargePresentsMapper;
	
	//批量新增充值赠送抵用券信息
	@Transactional
	public String batchInsertRechargePresentsVoucher(List<RechargePresentsVoucherEntity> listRechargePresentsVoucherEntity) throws Exception
	{
		int rcdNum=0;
		rcdNum=rechargePresentsMapper.batchInsertRechargePresentsVoucher(listRechargePresentsVoucherEntity);
		if(rcdNum!=0)
			return DataTool.constructResponse(ResultCode.OK,"新增成功",null);
		else
			return DataTool.constructResponse(ResultCode.OK,"新增失败",null);
	}
	
	//批量 更新充值赠送抵用券信息
	@Transactional
	public String batchUpdateRechargePresentsVoucher(List<RechargePresentsVoucherEntity> listRechargePresentsVoucherEntity) throws Exception
	{
		int iRcdNum=0;
		int iLength=listRechargePresentsVoucherEntity.size();
		if(listRechargePresentsVoucherEntity==null||iLength==0)
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"参数不能为空",null);
		for(int i=0;i<iLength;i++)
		{
		iRcdNum=rechargePresentsMapper.updateRechargePresentsVoucher(listRechargePresentsVoucherEntity.get(i));
		}
		
		if(iRcdNum!=0)
			return DataTool.constructResponse(ResultCode.OK,"更新成功",null);
		else
			return DataTool.constructResponse(ResultCode.UNKNOW_ERROR,"更新失败",null);
	}
	
	//删除 充值赠送抵用券信息
	public String deleteRechargePresentsVoucher(String strRechargePresentsVoucherId) throws Exception
	{
		int iRcdNum=0;
		iRcdNum=rechargePresentsMapper.deleteRechargePresentsVoucher(strRechargePresentsVoucherId);
		if(iRcdNum==0)
			return DataTool.constructResponse(ResultCode.UNKNOW_ERROR,"删除失败",null);
		else
			return DataTool.constructResponse(ResultCode.OK,"删除成功",null);
	}
	
	//查询 充值赠送抵用券信息
	public List<RechargePresentsVoucherEntity> selectAllRechargePresentsVoucher(String strActivityId) throws Exception
	{
		return rechargePresentsMapper.selectAllRechargePresentsVoucher(strActivityId);
	}
	
	//批量新增 充值送储值信息
	public String batchInsertRechargePresentsStoredValue(List<RechargePresentsStoredValueEntity> listRechargePresentsStoredValueEntity) throws Exception
	{
		int iRcdNum=0;
		if(listRechargePresentsStoredValueEntity.size()==0||listRechargePresentsStoredValueEntity==null)
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"参数不能为空",null);
		iRcdNum=rechargePresentsMapper.batchInsertRechargePresentsStoredValue(listRechargePresentsStoredValueEntity);
		
		if(iRcdNum==0)
			return DataTool.constructResponse(ResultCode.UNKNOW_ERROR,"批量新增失败",null);
		else
			return DataTool.constructResponse(ResultCode.OK,"批量新增成功",null);
		
	}
	
	//批量修改 充值赠送储值信息
	public String batchUpdateRechargePresentsStoredValue(List<RechargePresentsStoredValueEntity> listRechargePresentsStoredValueEntity) throws Exception
	{
		int iRcdNum=0;
		int iLoopTime=listRechargePresentsStoredValueEntity.size();
		if(iLoopTime==0||listRechargePresentsStoredValueEntity==null)
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"参数不能为空",null);
		for(int i=0;i<iLoopTime;i++)
		{
			iRcdNum=rechargePresentsMapper.updateRechargePresentsStoredValue(listRechargePresentsStoredValueEntity.get(i));
		}
		if(iRcdNum==0)
			return DataTool.constructResponse(ResultCode.UNKNOW_ERROR,"批量更新失败",null);
		else
			return DataTool.constructResponse(ResultCode.OK,"批量更新成功",null);
	}

	//删除 充值赠送储值
	public String deleteRechargePresentsStoredValue(String strPresentsStoredValueId) throws Exception
	{
		int iRcdNum=0;
		iRcdNum=rechargePresentsMapper.deleteRechargePresentsStoredValue(strPresentsStoredValueId);
		if(iRcdNum==0)
			return DataTool.constructResponse(ResultCode.UNKNOW_ERROR,"删除失败",null);
		else
			return DataTool.constructResponse(ResultCode.OK,"删除成功",null);
	}
	
	//查询 充值赠送储值信息
	public List<RechargePresentsStoredValueEntity> selectAllRechargePresentsStoredValue(String strActivityId) throws Exception
	{
		return rechargePresentsMapper.selectAllRechargePresentsStoredValue(strActivityId);
	}
	
	//新增 充值赠送积分规则信息
	@Transactional
	public String insertRechargePresentsIntegration(RechargePresentsIntegrationEntity rechargePresentsIntegrationEntity) throws Exception
	{
		//先检测是否已经有对应相应活动的积分规则存在，如果存在则提示错误
		
		int iInsertNum=0,iUpdateNum=0;
		String strPresentsIntegrationId;
		strPresentsIntegrationId=rechargePresentsMapper.isExistsTheRecord(rechargePresentsIntegrationEntity.getStrActivityId());
		if(ValidateTool.isEmptyStr(strPresentsIntegrationId))
		{	
			iInsertNum=rechargePresentsMapper.insertRechargePresentsIntegration(rechargePresentsIntegrationEntity);
			if(iInsertNum==0)
				return DataTool.constructResponse(ResultCode.UNKNOW_ERROR,"新增失败",null);
			else
				return DataTool.constructResponse(ResultCode.OK,"新增成功",null);
		}
		else
		{
			//更新
			rechargePresentsIntegrationEntity.setStrPresentsIntegrationId(strPresentsIntegrationId);//对管理员来说是新增，对后台是履盖原来的记录
			iUpdateNum=rechargePresentsMapper.updateRechargePresentsIntegration(rechargePresentsIntegrationEntity);
			
			if(iUpdateNum==0)
				return DataTool.constructResponse(ResultCode.UNKNOW_ERROR,"新增失败",null);
			else
				return DataTool.constructResponse(ResultCode.OK,"新增成功",null);
		}
	}
	
	// 更新 充值赠送积分信息
	public String updateRechargePresentsIntegration(RechargePresentsIntegrationEntity rechargePresentsIntegrationEntity) throws Exception
	{
		int iUpdateNum=0;
		iUpdateNum=rechargePresentsMapper.updateRechargePresentsIntegration(rechargePresentsIntegrationEntity);
		
		if(iUpdateNum==0)
			return DataTool.constructResponse(ResultCode.UNKNOW_ERROR,"更新失败",null);
		else
			return DataTool.constructResponse(ResultCode.OK,"更新成功",null);
		
	}
	
	//查询 充值赠送积分信息
	public List<RechargePresentsIntegrationEntity> selectAllRechargePresentsIntegration(String strActivityId) throws Exception
	{
		return rechargePresentsMapper.selectAllRechargePresentsIntegration(strActivityId);
		
	}
	
	//新增 活动信息
	@Transactional
	public String inserPresentsActivityInfo(RechargePresentsActivityEntity presentsActivityEntity) throws Exception
	{
		int iInsertNum=0;
		int isExists=0;
		//判断该会员级别在该活动类型下是否已经存在活动数据，若已存在则报错，不存在则执行写入
		if(presentsActivityEntity==null)
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"参数不能为空",null);
		
		isExists=rechargePresentsMapper.isExistsTheActivityRecord(presentsActivityEntity);
		if(isExists!=0)
			return DataTool.constructResponse(ResultCode.UNKNOW_ERROR,"新增失败:该会员级别在该活动类型下的活动表记录已经存在",null);
		iInsertNum=rechargePresentsMapper.inserPresentsActivityInfo(presentsActivityEntity);
		if(iInsertNum==0)
			return DataTool.constructResponse(ResultCode.UNKNOW_ERROR,"新增失败",null);
		else
			return DataTool.constructResponse(ResultCode.OK,"新增成功",null);	
	}

	//更新 活动信息
	@Transactional
	public String updatePresentsActivityInfo(RechargePresentsActivityEntity presentsActivityEntity) throws Exception
	{
		int iUpdateNum=0;
		String isExistsStrActivityId=null;
		if(presentsActivityEntity==null)
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"参数不能为空",null);
		
		//判断该会员级别在该活动类型下的活动表记录已经存在中，若已存在则报错，不存在则执行写入
		isExistsStrActivityId=rechargePresentsMapper.isExistsTheActivityRecordId(presentsActivityEntity);
		if(!ValidateTool.isEmptyStr(isExistsStrActivityId)&&!isExistsStrActivityId.equals(presentsActivityEntity.getStrActivityId()))	
			return DataTool.constructResponse(ResultCode.UNKNOW_ERROR,"更新失败：该会员级别在该活动类型下的活动表记录已经存在",null);
		
		iUpdateNum=rechargePresentsMapper.updatePresentsActivityInfo(presentsActivityEntity);
		if(iUpdateNum==0)
			return DataTool.constructResponse(ResultCode.UNKNOW_ERROR,"更新失败",null);
		else
			return DataTool.constructResponse(ResultCode.OK,"更新成功",null);	
	
	}
	
	
	//查询，全部活动表总记录条数
	public int findCount(Map<String,Object> queryMap) throws Exception
	{
		return rechargePresentsMapper.findCount(queryMap);
	}
	

	//查询，过期活动表总记录条数
	public int findExpiredCount(Map<String,Object> queryMap) throws Exception
	{
		return rechargePresentsMapper.findCount(queryMap);
	}
	//查询，正常活动表总记录条数
	public int findNormalCount(Map<String,Object> queryMap) throws Exception
	{
		return rechargePresentsMapper.findCount(queryMap);
	}
	
	//分页查询 活动状态为"全部" 的活动表及活动的所有属性（如：充值赠送积分，赠送抵用券，赠送储值 
	@Transactional
	public List<RechargePresentsActivityEntity> selectRechargePresentsActivityInfo(Map<String,Object> queryMap) throws Exception
	{
		int iListArrayLength=0;
		String strActivityId;
		List<RechargePresentsActivityEntity> listRechargePresentsActivityEntity=null;
		listRechargePresentsActivityEntity=rechargePresentsMapper.selectRechargePresentsActivityInfo(queryMap);
		if(listRechargePresentsActivityEntity!=null)
		{
			//查出活动记录关键字，组装各个属性
			iListArrayLength=listRechargePresentsActivityEntity.size();
			for(int i=0;i<iListArrayLength;i++)
			{
				//得到活动实体对象
				RechargePresentsActivityEntity rechargePresentsActivityEntity=listRechargePresentsActivityEntity.get(i);
				strActivityId=rechargePresentsActivityEntity.getStrActivityId();	
				//查充值赠送积分表
				List<RechargePresentsIntegrationEntity> listRechargePresentsIntegrationEntity=rechargePresentsMapper.selectAllRechargePresentsIntegration(strActivityId);
				List<RechargePresentsStoredValueEntity> listRechargePresentsStoredValueEntity=rechargePresentsMapper.selectAllRechargePresentsStoredValue(strActivityId);
				List<RechargePresentsVoucherEntity> listRechargePresentsVoucherEntity=rechargePresentsMapper.selectAllRechargePresentsVoucher(strActivityId);
				rechargePresentsActivityEntity.setListRechargePresentsIntegrationEntity(listRechargePresentsIntegrationEntity);
				rechargePresentsActivityEntity.setListRechargePresentsStoredValueEntity(listRechargePresentsStoredValueEntity);
				rechargePresentsActivityEntity.setListRechargePresentsVoucherEntity(listRechargePresentsVoucherEntity);
				
				
			}
			
		}
		return listRechargePresentsActivityEntity;
		
	}
	

	//分页查询 活动状态为"过期" 的活动表及活动的所有属性（如：充值赠送积分，赠送抵用券，赠送储值 
		@Transactional
		public List<RechargePresentsActivityEntity> selectExpiredRechargePresentsActivityInfo(Map<String,Object> queryMap) throws Exception
		{
			int iListArrayLength=0;
			String strActivityId;
			List<RechargePresentsActivityEntity> listRechargePresentsActivityEntity=null;
			listRechargePresentsActivityEntity=rechargePresentsMapper.selectExpiredRechargePresentsActivityInfo(queryMap);
			if(listRechargePresentsActivityEntity!=null)
			{
				//查出活动记录关键字，组装各个属性
				iListArrayLength=listRechargePresentsActivityEntity.size();
				for(int i=0;i<iListArrayLength;i++)
				{
					//得到活动实体对象
					RechargePresentsActivityEntity rechargePresentsActivityEntity=listRechargePresentsActivityEntity.get(i);
					strActivityId=rechargePresentsActivityEntity.getStrActivityId();	
					//查充值赠送积分表
					List<RechargePresentsIntegrationEntity> listRechargePresentsIntegrationEntity=rechargePresentsMapper.selectAllRechargePresentsIntegration(strActivityId);
					List<RechargePresentsStoredValueEntity> listRechargePresentsStoredValueEntity=rechargePresentsMapper.selectAllRechargePresentsStoredValue(strActivityId);
					List<RechargePresentsVoucherEntity> listRechargePresentsVoucherEntity=rechargePresentsMapper.selectAllRechargePresentsVoucher(strActivityId);
					rechargePresentsActivityEntity.setListRechargePresentsIntegrationEntity(listRechargePresentsIntegrationEntity);
					rechargePresentsActivityEntity.setListRechargePresentsStoredValueEntity(listRechargePresentsStoredValueEntity);
					rechargePresentsActivityEntity.setListRechargePresentsVoucherEntity(listRechargePresentsVoucherEntity);
					
					
				}
				
			}
			return listRechargePresentsActivityEntity;
			
		}
		//分页查询 活动状态为"正常" 的活动表及活动的所有属性（如：充值赠送积分，赠送抵用券，赠送储值 
				@Transactional
				public List<RechargePresentsActivityEntity> selectNormalRechargePresentsActivityInfo(Map<String,Object> queryMap) throws Exception
				{
					int iListArrayLength=0;
					String strActivityId;
					List<RechargePresentsActivityEntity> listRechargePresentsActivityEntity=null;
					listRechargePresentsActivityEntity=rechargePresentsMapper.selectNormalRechargePresentsActivityInfo(queryMap);
					if(listRechargePresentsActivityEntity!=null)
					{
						//查出活动记录关键字，组装各个属性
						iListArrayLength=listRechargePresentsActivityEntity.size();
						for(int i=0;i<iListArrayLength;i++)
						{
							//得到活动实体对象
							RechargePresentsActivityEntity rechargePresentsActivityEntity=listRechargePresentsActivityEntity.get(i);
							strActivityId=rechargePresentsActivityEntity.getStrActivityId();	
							//查充值赠送积分表
							List<RechargePresentsIntegrationEntity> listRechargePresentsIntegrationEntity=rechargePresentsMapper.selectAllRechargePresentsIntegration(strActivityId);
							List<RechargePresentsStoredValueEntity> listRechargePresentsStoredValueEntity=rechargePresentsMapper.selectAllRechargePresentsStoredValue(strActivityId);
							List<RechargePresentsVoucherEntity> listRechargePresentsVoucherEntity=rechargePresentsMapper.selectAllRechargePresentsVoucher(strActivityId);
							rechargePresentsActivityEntity.setListRechargePresentsIntegrationEntity(listRechargePresentsIntegrationEntity);
							rechargePresentsActivityEntity.setListRechargePresentsStoredValueEntity(listRechargePresentsStoredValueEntity);
							rechargePresentsActivityEntity.setListRechargePresentsVoucherEntity(listRechargePresentsVoucherEntity);
							
							
						}
						
					}
					return listRechargePresentsActivityEntity;
					
				}
	

}
