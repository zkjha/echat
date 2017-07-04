package com.ecard.mapper;

import java.util.Map;

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

}
