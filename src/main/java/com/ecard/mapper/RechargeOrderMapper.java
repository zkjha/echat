package com.ecard.mapper;

import com.ecard.entity.RechargeOrderEntity;

/**
 * 职务操作mapper
 */
public interface RechargeOrderMapper {
	
	//新增现金充值订单
	void insertRechargeOrder(RechargeOrderEntity rechargeOrderEntity) throws Exception;

	//查询订单详细信息
	RechargeOrderEntity getRechargeOrderById(String strOrderId) throws Exception;

	//修改订单状态
	void updateOrderPayInfo(RechargeOrderEntity rechargeOrderEntity) throws Exception;
}
