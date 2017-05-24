package com.ecard.mapper;

import java.util.List;
import java.util.Map;

import com.ecard.entity.VoucherTicketInfoEntity;

public interface VoucherTicketInfoMapper {
	//增加抵用卷详细信息
	public int insertVoucherTicketInfo(VoucherTicketInfoEntity voucherTicketInfoEntity) throws Exception;
	//查询抵用卷信息的总条数
	public int findRecordCount() throws Exception;
	//分页查询抵用卷详细信息
	public List<VoucherTicketInfoEntity> selectVoucherTicketInfo(Map<String,Object> queryMap) throws Exception;
	//更新 抵用券详细信息
	public int updateVoucherTicketInfo(VoucherTicketInfoEntity voucherTicketInfoEntity) throws Exception;
	//删除 抵用券详细信息
	public int deleteVoucherTicketInfo(String strVoucherTicketId) throws Exception;
	//查询 单条记录
	public VoucherTicketInfoEntity findVoucherTicketInfoById(String strVoucherTicketId) throws Exception;
	//查询 下拉列表框
	public List<VoucherTicketInfoEntity> getListBoxtVoucherTicketInfo() throws Exception;
}
