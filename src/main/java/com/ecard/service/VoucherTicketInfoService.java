package com.ecard.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.commontools.data.DataTool;
import com.ecard.config.ResultCode;
import com.ecard.entity.VoucherTicketInfoEntity;
import com.ecard.mapper.VoucherTicketInfoMapper;

@Service
public class VoucherTicketInfoService {
	@Autowired
	private VoucherTicketInfoMapper voucherTicketInfoMapper;
	
	//增加抵用卷详细信息
	public String insertVoucherTicketInfo(VoucherTicketInfoEntity voucherTicketInfoEntity) throws Exception
	{
		int rcdNum=voucherTicketInfoMapper.insertVoucherTicketInfo(voucherTicketInfoEntity);
		if(rcdNum==0)
			return DataTool.constructResponse(ResultCode.UNKNOW_ERROR,"新增失败",null);
		else
			return DataTool.constructResponse(ResultCode.OK,"新增成功",null);
	}
	
	//查询抵用卷信息的总条数
	public int findRecordCount() throws Exception
	{
		return voucherTicketInfoMapper.findRecordCount();
	}
	
	//分页查询抵用卷详细信息
	public List<VoucherTicketInfoEntity> selectVoucherTicketInfo(Map<String,Object> queryMap) throws Exception
	{
		return voucherTicketInfoMapper.selectVoucherTicketInfo(queryMap);
	}
	
	//更新 抵用券详细信息
	public String updateVoucherTicketInfo(VoucherTicketInfoEntity voucherTicketInfoEntity) throws Exception
	{
		int rcdNum=voucherTicketInfoMapper.updateVoucherTicketInfo(voucherTicketInfoEntity);
		if(rcdNum==0)
			return DataTool.constructResponse(ResultCode.UNKNOW_ERROR,"更新失败",null);
		else
			return DataTool.constructResponse(ResultCode.OK,"更新成功",null);
	}
}
