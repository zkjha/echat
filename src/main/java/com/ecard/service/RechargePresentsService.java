package com.ecard.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.commontools.data.DataTool;
import com.ecard.config.ResultCode;
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
	public List<RechargePresentsVoucherEntity> selectAllRechargePresentsVoucher() throws Exception
	{
		return rechargePresentsMapper.selectAllRechargePresentsVoucher();
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
	public List<RechargePresentsStoredValueEntity> selectAllRechargePresentsStoredValue() throws Exception
	{
		return rechargePresentsMapper.selectAllRechargePresentsStoredValue();
	}
}
