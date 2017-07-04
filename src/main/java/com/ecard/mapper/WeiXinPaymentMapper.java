package com.ecard.mapper;

import com.ecard.entity.MemberEntity;
import com.ecard.entity.WeiXinGoodsOrderEntity;
import com.ecard.entity.WeiXinReceiveGoodsAddressEntity;

public interface WeiXinPaymentMapper
{
	//查会员信息
	public MemberEntity selectMemberInfo(String strMemberId) throws Exception;
	//查商品售价
	public double selectSalePrice(String strGoodsId) throws Exception;
	//生成订单 
	public int generateWeiXinOrder(WeiXinGoodsOrderEntity weiXinGoodsOrderEntity) throws Exception;
	//新增 收件人信息
	public int insertWeiXinReceiveGoodsAddressEntity(WeiXinReceiveGoodsAddressEntity weiXinReceiveGoodsAddressEntity) throws Exception;

}
