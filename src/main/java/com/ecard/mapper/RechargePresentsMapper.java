package com.ecard.mapper;

import java.util.List;

import com.ecard.entity.RechargePresentsVoucherEntity;

public interface RechargePresentsMapper {
	//批量新增充值赠送抵用券信息
	public int batchInsertRechargePresentsVoucher(List<RechargePresentsVoucherEntity> rechargePresentsVoucherEntityList) throws Exception;

}
