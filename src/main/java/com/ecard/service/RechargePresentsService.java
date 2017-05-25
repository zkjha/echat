package com.ecard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.commontools.data.DataTool;
import com.ecard.config.ResultCode;
import com.ecard.entity.RechargePresentsVoucherEntity;
import com.ecard.mapper.RechargePresentsMapper;

@Service
public class RechargePresentsService {
	@Autowired
	private RechargePresentsMapper rechargePresentsMapper;
	
	//批量新增充值赠送抵用券信息
	public String batchInsertRechargePresentsVoucher(List<RechargePresentsVoucherEntity> listRechargePresentsVoucherEntity) throws Exception
	{
		int rcdNum=0;
		rcdNum=rechargePresentsMapper.batchInsertRechargePresentsVoucher(listRechargePresentsVoucherEntity);
		if(rcdNum!=0)
			return DataTool.constructResponse(ResultCode.OK,"新增成功",null);
		else
			return DataTool.constructResponse(ResultCode.OK,"新增失败",null);
	}

}
