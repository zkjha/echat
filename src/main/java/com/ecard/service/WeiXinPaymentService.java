package com.ecard.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.commontools.data.DataTool;
import com.ecard.config.ResultCode;
import com.ecard.entity.MemberEntity;
import com.ecard.entity.WeiXinGoodsOrderEntity;
import com.ecard.entity.WeiXinReceiveGoodsAddressEntity;
import com.ecard.mapper.WeiXinPaymentMapper;

@Service
public class WeiXinPaymentService
{
	@Autowired
	private WeiXinPaymentMapper weiXinPaymentMapper;
	//生成订单
	@Transactional
	public String generateWeiXinOrder(WeiXinGoodsOrderEntity weiXinGoodsOrderEntity,WeiXinReceiveGoodsAddressEntity weiXinReceiveGoodsAddressEntity) throws Exception
	{
		BigDecimal dSalePrice;
		BigDecimal dPurchaseCashTotalAmount;
		int iPurchaseAmount=weiXinGoodsOrderEntity.getiPurchaseAmount();
		int iAffectNum=0;
		//查会员信息
		String strMemberId=weiXinGoodsOrderEntity.getStrMemberId();
		MemberEntity memberEntity=weiXinPaymentMapper.selectMemberInfo(strMemberId);
		//查商品原价
		String strGoodsId=weiXinGoodsOrderEntity.getStrGoodsId();

		dSalePrice=new BigDecimal(String.valueOf(weiXinPaymentMapper.selectSalePrice(strGoodsId)));

		dPurchaseCashTotalAmount=dSalePrice.multiply(new BigDecimal(iPurchaseAmount));
		weiXinGoodsOrderEntity.setStrLevelsId(memberEntity.getStrLevelsid());
		weiXinGoodsOrderEntity.setStrMemberName(memberEntity.getStrRealname());
		weiXinGoodsOrderEntity.setStrMemberCardNumber(memberEntity.getStrMembercardnum());
		weiXinGoodsOrderEntity.setdPrice(dSalePrice);
		weiXinGoodsOrderEntity.setdPurchaseCashTotalAmount(dPurchaseCashTotalAmount);
		//写入收货人信息
		if(weiXinReceiveGoodsAddressEntity!=null)
			weiXinPaymentMapper.insertWeiXinReceiveGoodsAddressEntity(weiXinReceiveGoodsAddressEntity);
		iAffectNum=weiXinPaymentMapper.generateWeiXinOrder(weiXinGoodsOrderEntity);
		if(iAffectNum!=0)
			return DataTool.constructResponse(ResultCode.OK,"写入订单成功",null);
		else
			return DataTool.constructResponse(ResultCode.UNKNOW_ERROR,"写入订单失败",null);
		
	}
}
