package com.ecard.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.commontools.data.DataTool;
import com.ecard.config.ResultCode;
import com.ecard.entity.MemberEntity;
import com.ecard.entity.WeiXinGoodsOrderEntity;
import com.ecard.entity.WeiXinReceiveGoodsAddressEntity;
import com.ecard.mapper.WeiXinPaymentMapper;
import com.ecard.vo.GoodsVO;

@Service
public class WeiXinPaymentService
{
	@Autowired
	private WeiXinPaymentMapper weiXinPaymentMapper;
	//生成订单
	@Transactional
	public String generateWeiXinOrder(WeiXinGoodsOrderEntity weiXinGoodsOrderEntity,WeiXinReceiveGoodsAddressEntity weiXinReceiveGoodsAddressEntity) throws Exception
	{
		BigDecimal dSalePrice;  //商品原售价
		BigDecimal dPurchaseCashTotalAmount; //订单总金额（原价）
		BigDecimal dPreferentialPrice;	//商品会员价
		BigDecimal dPreferentialCashTotalAmount;	//订单总金额（会员价)
		BigDecimal dDiscount;
		int iPurchaseIntegrationAmount=0;	//订单 所需积分总量
		int iPurchaseAmount=weiXinGoodsOrderEntity.getiPurchaseAmount();	//购商品数量
		int iAffectNum=0;
		int iPreferentialType=0;	//是否有积分优惠 0无优惠 1 有优惠
		int iLevelIntegration=0;	//购商品时的会员等级积分
		int iBaseIntegration=0;//购商品时的原积分
		String strGoodsName="";	//商品名称 
		String strUnitName="";//商品计量单位名称
		//查会员信息
		String strMemberId=weiXinGoodsOrderEntity.getStrMemberId();
		MemberEntity memberEntity=weiXinPaymentMapper.selectMemberInfo(strMemberId);
	
		//查商品信息
		String strGoodsId=weiXinGoodsOrderEntity.getStrGoodsId();
		GoodsVO goodsVOEntity=weiXinPaymentMapper.selectGoordsInfoById(strGoodsId);
		if(goodsVOEntity!=null&&!goodsVOEntity.getStrGoodsId().isEmpty())
		{
			//取出商品属性
			strGoodsName=goodsVOEntity.getStrGoodsName();
			strUnitName=goodsVOEntity.getStrUnitName();
			dSalePrice=goodsVOEntity.getdSalePrice();
			iBaseIntegration=goodsVOEntity.getiRequiredIntegral();
			iPreferentialType=goodsVOEntity.getiPreferentialType();
			
		}
		else
			return DataTool.constructResponse(ResultCode.NO_DATA,"暂无商品信息",null);
		
		//查等级权益 表
		Map<String,Object> queryMap=new HashMap<String,Object>();
		queryMap.put("strMemberId",strMemberId);
		queryMap.put("strGoodsId",strGoodsId);
		queryMap.put("strLevelsId",memberEntity.getStrLevelsid());
		dDiscount=new BigDecimal(String.valueOf(weiXinPaymentMapper.findDiscountInfo(queryMap)));
		dPreferentialPrice=dSalePrice.multiply(dDiscount);
		dPreferentialCashTotalAmount=dPreferentialPrice.multiply(new BigDecimal(iPurchaseAmount));
		dPurchaseCashTotalAmount=dSalePrice.multiply(new BigDecimal(iPurchaseAmount));
		//查商品积分优惠信息表
		//查商品优惠表：获取兑换商品的等级积分
		switch(iPreferentialType)
		{
			case 0:
				//不优惠的情况
				iLevelIntegration=iBaseIntegration;
				break;
			case 1:
				//有积分优惠的情况：
				iLevelIntegration=weiXinPaymentMapper.findLevelsIntegrationInfo(queryMap);
				if(iLevelIntegration==0)
					iLevelIntegration=iBaseIntegration;
				if(iLevelIntegration>iBaseIntegration)	//如果等级积会>原积分，则默认按原积分执行
					iLevelIntegration=iBaseIntegration;
				break;
		}
		//将订表属性装进对象
		iPurchaseIntegrationAmount=iLevelIntegration*iPurchaseAmount;
		
		weiXinGoodsOrderEntity.setStrLevelsId(memberEntity.getStrLevelsid());
		weiXinGoodsOrderEntity.setStrMemberName(memberEntity.getStrRealname());
		weiXinGoodsOrderEntity.setStrMemberCardNumber(memberEntity.getStrMembercardnum());
		weiXinGoodsOrderEntity.setStrGoodsName(strGoodsName);
		weiXinGoodsOrderEntity.setStrUnitName(strUnitName);
		weiXinGoodsOrderEntity.setdPreferentialPrice(dPreferentialPrice);
		weiXinGoodsOrderEntity.setdPreferentialCashTotalAmount(dPreferentialCashTotalAmount);
		weiXinGoodsOrderEntity.setiIntegrationAmount(iLevelIntegration);
		weiXinGoodsOrderEntity.setiPurchaseIntegrationAmount(iPurchaseIntegrationAmount);
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
