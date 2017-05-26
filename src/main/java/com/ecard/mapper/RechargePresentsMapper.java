package com.ecard.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ecard.entity.RechargePresentsStoredValueEntity;
import com.ecard.entity.RechargePresentsVoucherEntity;

public interface RechargePresentsMapper {
	//批量新增充值赠送抵用券信息
	public int batchInsertRechargePresentsVoucher(@Param("listRechargePresentsVoucherEntity")List<RechargePresentsVoucherEntity> listRechargePresentsVoucherEntity) throws Exception;
	//更新 充值赠送抵用券信息
	public int updateRechargePresentsVoucher(RechargePresentsVoucherEntity rechagePresentsVoucherEntity) throws Exception;
	//删除 充值赠送抵用券信息
	public int deleteRechargePresentsVoucher(String strRechargePresentsVoucherId) throws Exception;
	//查询 充值赠送抵用券信息
	public List<RechargePresentsVoucherEntity> selectAllRechargePresentsVoucher() throws Exception;
	//批量新增 充值送储值信息
	public int batchInsertRechargePresentsStoredValue(@Param("listRechargePresentsStoredValueEntity")List<RechargePresentsStoredValueEntity> listRechargePresentsStoredValueEntity) throws Exception;
	//批量修改 充值赠送抵用券信息
	public int updateRechargePresentsStoredValue(RechargePresentsStoredValueEntity rechargePresentsStoredValueEntity) throws Exception;
	//删除 充值赠送储值
	public int deleteRechargePresentsStoredValue(String strPresentsStoredValueId) throws Exception;
	//查询 充值赠送储值信息
	public List<RechargePresentsStoredValueEntity> selectAllRechargePresentsStoredValue() throws Exception;
}
