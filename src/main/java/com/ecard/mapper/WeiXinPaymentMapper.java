package com.ecard.mapper;

import com.ecard.entity.MemberEntity;
import com.ecard.entity.WeiXinGoodsOrderEntity;

public interface WeiXinPaymentMapper
{
	//查会员信息
	public MemberEntity selectMemberInfo(String strMemberId) throws Exception;
	//查商品售价
	public double selectSalePrice(String strGoodsId) throws Exception;
	//生成订单 
	public int generateWeiXinOrder(WeiXinGoodsOrderEntity weiXinGoodsOrderEntity) throws Exception;

}
