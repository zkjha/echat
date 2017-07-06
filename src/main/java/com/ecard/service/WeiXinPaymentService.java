package com.ecard.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.commontools.data.DataTool;
import com.commontools.date.DateStyle;
import com.commontools.date.DateTool;
import com.ecard.config.ResultCode;
import com.ecard.entity.IntegralModRecord;
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
	
	
	//积分支付
	@Transactional
	public String payGoodsOrderWithIntegration(Map<String,Object> queryMap) throws Exception
	{
		
		int iOrderIntegration=0;	//订单所需积分
		int iMemberIntegration=0;	//会员积分余额
		int iRestIntegration=0;
		int iAffectNum=0;
		int iOk=1;
		String strLastAccessTime=DateTool.DateToString(new Date(),DateStyle.YYYY_MM_DD_HH_MM);
		String strPayTime=DateTool.DateToString(new Date(),DateStyle.YYYY_MM_DD_HH_MM);
		//查订单积分
		iOrderIntegration=weiXinPaymentMapper.selectOrderIntegration(queryMap);
		if(iOrderIntegration==0)
			return DataTool.constructResponse(ResultCode.NO_DATA,"该笔订单不能用积分支付",null);
		
		//查会员信息,积分余额
		MemberEntity memberEntity=weiXinPaymentMapper.selectMemberIntegrationInfo(queryMap);
		
		if(memberEntity==null)
			return DataTool.constructResponse(ResultCode.NO_DATA,"暂无会员信息",null);
		
		if(memberEntity.getStrMemberid()==null)
			return DataTool.constructResponse(ResultCode.NO_DATA,"暂无会员信息",null);
	
		iMemberIntegration=memberEntity.getIntIntegral();
		if(iMemberIntegration==0)
			return DataTool.constructResponse(ResultCode.OK,"会员卡积分余额为0,不能用积分支付",null);
		
		iRestIntegration=iMemberIntegration-iOrderIntegration;
		if(iRestIntegration<0)
			return DataTool.constructResponse(ResultCode.OK,"会员卡积分余额不足,不能用积分支付",null);
		
		//修改改订单状态
		Map<String,Object> updateMap=new HashMap<String,Object>();
		updateMap.put("strLastAccessedTime", strLastAccessTime);
		updateMap.put("strPayTime",strPayTime);
		updateMap.put("iPayType",0);
		updateMap.put("strOrderId",queryMap.get("strOrderId"));
		iAffectNum=weiXinPaymentMapper.updateOrderInfo(updateMap);
		if(iAffectNum==0)
			iOk=0;
		
		//插入积分变更信息
		IntegralModRecord integralModRecord=new IntegralModRecord();
		integralModRecord.setStrRecordId(DataTool.getUUID());
		integralModRecord.setStrMemberId((String)queryMap.get("strMemberId"));
		integralModRecord.setStrMemberCardNum(memberEntity.getStrMembercardnum());
		integralModRecord.setStrMemberName(memberEntity.getStrRealname());
		integralModRecord.setiIntegralNum(-iRestIntegration);
		integralModRecord.setStrDesc("微信积分支付订单:(单号:"+(String)queryMap.get("strOrderId")+")");
		integralModRecord.setStrInsertTime(DateTool.DateToString(new Date(),DateStyle.YYYY_MM_DD_HH_MM));
		iAffectNum=weiXinPaymentMapper.insertIntegrationChangedRecord(integralModRecord);
		if(iAffectNum==0)
			iOk=0;
		//变更会员积分信息
		queryMap.put("iRestIntegration",iRestIntegration);
		iAffectNum=weiXinPaymentMapper.updateMemberIntegrationInfo(queryMap);
		if(iAffectNum==0)
			iOk=0;
		if(iOk==0)
			return DataTool.constructResponse(ResultCode.UNKNOW_ERROR,"积分支付失败",null);
		else
			return DataTool.constructResponse(ResultCode.OK,"积分支付成功",null);
		
	}
	
	//根据会员ID查询订单信息
	@Transactional(rollbackFor=Exception.class)
	public WeiXinGoodsOrderEntity selectWeiXinGoodsOrderEntity(String strOrderId) throws Exception
	{
	
		return weiXinPaymentMapper.selectWeiXinGoodsOrderEntity(strOrderId);
	}
	
	//会员卡储值支付
	public String payGoodsOrderWithCardCash(Map<String,Object> queryMap) throws Exception
	{
		int iAffectNum=0;
		int iOk=1;
		BigDecimal dMemberCardCash=new BigDecimal("0");	//会员 卡储值余额
		BigDecimal dMemberCardAfterCash=new BigDecimal("0");//会员卡售后余额
		BigDecimal dTotalCashAmount=new BigDecimal("0");	//订单金额
		BigDecimal dCanUseAmount=new BigDecimal("0");	//会员可以用的储值总额=会员卡余额+有效的售后储值 余额
		String strValidEndTime="";	//会员卡售后储值有效期
		
		//查会员信息
		MemberEntity memberEntity=weiXinPaymentMapper.selectMemberDetailInfo(queryMap);
		if(memberEntity==null)
			return DataTool.constructResponse(ResultCode.NO_DATA,"暂无会员信息",null);
		if(memberEntity.getStrMemberid()==null)
			return DataTool.constructResponse(ResultCode.NO_DATA,"暂无会员信息",null);
		dMemberCardCash=memberEntity.getdBalance();
		dMemberCardAfterCash=memberEntity.getdAfterstoredbalance();
		strValidEndTime=memberEntity.getStrValidEndTime();
		//判断售后储值是否还可效
		String strCurrentTime=DateTool.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS);
		if(strValidEndTime.compareTo(strCurrentTime)<=0)
			dCanUseAmount=dMemberCardCash.add(dMemberCardAfterCash);//售后储值 有效的情况:
		else
			dCanUseAmount=dMemberCardCash;//售后储值无效的情况：
		
		//查订单信息
		dTotalCashAmount=new BigDecimal(String.valueOf(weiXinPaymentMapper.selectGoodsTotalCash(queryMap)));
		//卡余额不足的情况:
		if(dCanUseAmount.subtract(dTotalCashAmount).doubleValue()<0)
			return DataTool.constructResponse(ResultCode.UNKNOW_ERROR,"会员卡储值余额不足，请充值或采用其它结算方式",null);
		//修改改订单状态
		String strLastAccessTime=DateTool.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS);
		String strPayTime=DateTool.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS);
		Map<String,Object> updateMap=new HashMap<String,Object>();
		updateMap.put("strLastAccessedTime", strLastAccessTime);
		updateMap.put("strPayTime",strPayTime);
		updateMap.put("iPayType",4);
		updateMap.put("strOrderId",queryMap.get("strOrderId"));
		iAffectNum=weiXinPaymentMapper.updateOrderInfo(updateMap);
		if(iAffectNum==0)
			iOk=0;
		//修改会员表信息
		//iAffectNum=weiXinPaymentMapper.updateMemberBalance(updateMap);
		return null; 
	}

}
