package com.ecard.mapper;

import java.util.Map;

import com.ecard.entity.IntegralModRecord;
import com.ecard.entity.MemberEntity;
import com.ecard.entity.WeiXinGoodsOrderEntity;
import com.ecard.entity.WeiXinReceiveGoodsAddressEntity;
import com.ecard.vo.GoodsVO;

public interface WeiXinPaymentMapper
{
	//查会员信息
	public MemberEntity selectMemberInfo(String strMemberId) throws Exception;
	//生成订单 
	public int generateWeiXinOrder(WeiXinGoodsOrderEntity weiXinGoodsOrderEntity) throws Exception;
	//新增 收件人信息
	public int insertWeiXinReceiveGoodsAddressEntity(WeiXinReceiveGoodsAddressEntity weiXinReceiveGoodsAddressEntity) throws Exception;
	//查询商品信息
	public GoodsVO selectGoordsInfoById(String strGoodsId) throws Exception;
	//查找等级权益 优惠信息
	public double findDiscountInfo(Map<String,Object> queryMap) throws Exception;
	//查找商品的优惠积分信息
	public int findLevelsIntegrationInfo(Map<String,Object> queryMap) throws Exception;
	//根据会员ID查询订单信息
	public WeiXinGoodsOrderEntity selectWeiXinGoodsOrderEntity(String strOrderId) throws Exception;
	//查询订单所需积分
	public int selectOrderIntegration(Map<String,Object> queryMap) throws Exception;
	//查会员积分余额
	public MemberEntity selectMemberIntegration(Map<String,Object> queryMap) throws Exception;
	//更新订单信息
	public int updateOrderInfo(Map<String,Object> updateMap) throws Exception;
	//插入积分变更流水信息
	public int insertIntegrationChangedRecord(IntegralModRecord integralModRecord) throws Exception;
	//修改会员剩余积分信息
	public int updateMemberIntegrationInfo(Map<String,Object> queryMap) throws Exception;

}
