package com.ecard.service;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.commontools.data.DataTool;
import com.commontools.date.DateStyle;
import com.commontools.date.DateTool;
import com.ecard.entity.MemberRechargeRecord;
import com.ecard.entity.RechargeOrderEntity;
import com.ecard.mapper.MemberMapper;
import com.ecard.mapper.RechargeOrderMapper;

/**
 * 充值订单操作service
 */
@Service
@Transactional
public class RechargeOrderService {
	
	@Autowired
	private RechargeOrderMapper rechargeOrderMapper;
	@Autowired
	private MemberMapper memberMapper;

	//查询订单详细信息
	public RechargeOrderEntity getRechargeOrderById(String strOrderId) throws Exception {
		return rechargeOrderMapper.getRechargeOrderById(strOrderId);
	}

	//支付成功回调
	@Transactional(rollbackFor=Exception.class)
	public void callbackRechargeOrder(RechargeOrderEntity rechargeOrderEntity, String strThirdPartyTradeFlow, int intPayType) throws Exception {
		
		//1修改订单状态
		rechargeOrderEntity.setIntStatus(1); //支付成功
		rechargeOrderEntity.setStrPayTime(DateTool.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
		rechargeOrderEntity.setIntPayType(intPayType);
		rechargeOrderEntity.setStrThirdPartyTradeFlow(strThirdPartyTradeFlow);
		rechargeOrderMapper.updateOrderPayInfo(rechargeOrderEntity);
		//2录入充值记录
		MemberRechargeRecord tMemberRechargeRecord=new MemberRechargeRecord();
		tMemberRechargeRecord.setStrRechargeId(DataTool.getUUID());
		tMemberRechargeRecord.setStrMemberId(rechargeOrderEntity.getStrMemberId());
		tMemberRechargeRecord.setdBalance(rechargeOrderEntity.getdBalance());
		tMemberRechargeRecord.setStrEmployeeId(rechargeOrderEntity.getStrEmployeeId());
		tMemberRechargeRecord.setStrEmployeeRealName(rechargeOrderEntity.getStrEmployeeRealName());
		tMemberRechargeRecord.setStrEmployeeLoginName(rechargeOrderEntity.getStrEmployeeLoginName());
		tMemberRechargeRecord.setStrInsertTime(rechargeOrderEntity.getStrPayTime());
		tMemberRechargeRecord.setiRechargeType(0); //表示是现金充值
		tMemberRechargeRecord.setStrReserved("");
		tMemberRechargeRecord.setStrMemberCardNum(rechargeOrderEntity.getStrMemberCardNum());
		tMemberRechargeRecord.setStrMemberName(rechargeOrderEntity.getStrMemberName());
		memberMapper.insertMemberRechargeRecord(tMemberRechargeRecord);
		//3修改用户现金余额
		memberMapper.updateMemberCashAmountById(tMemberRechargeRecord);
		
	}
	
	
}

