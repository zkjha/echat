package com.ecard.entity;

import java.math.BigDecimal;

//微信端 订单表
public class WeiXinGoodsOrderEntity
{
	private String strOrderId;           //主键
	private String strMemberId;           //会员ID
	private String strMemberCardNumber;           //会员卡号
	private String strMemberName;           //会员姓名 
	private String strLevelsId;           //会员级别ID
	private String strGoodsId;           //商品ID
	private String strGoodsName;           //商品名称
	private int iPurchaseAmount;           //购买数量
	private BigDecimal dPrice;        //商品单价（原价）
	private String strUnitName;           //计量单 位
	private BigDecimal dPurchaseCashTotalAmount;        //订单总金额（原价）
	private BigDecimal dPreferentialPrice;        //会员单价
	private BigDecimal dPreferentialCashTotalAmount;        //订单会员优惠总金额
	private int iIntegrationAmount;           //单个商品的需积分数量
	private int iPurchaseIntegrationAmount;           //订单所需积分总数量
	private int iStatus;           //订单状态0：待支付 1：已支付 2：已发货 3：已完成
	private int iPayStandard;           //支付标准：0 会员价（优惠价)支付,1原价支付
	private String strPayTime;           //支付时间
	private int iPayType;           //支付方式 0：积分兑换 1：微信支付 2：支付宝支付,3现金支付,4会员卡余额支付
	private String strThirdPartyTradeFlow;           //三方支付流水号
	private String strExpressNumber;           //快递单号
	private String strExpressCompany;           //快递公司
	private String strComment;           //备注字段
	private int iReceiveType;           //收货方式  0 快递 1 到店取货
	private String strReceiveTime;           //到店取货时间
	private String strCreationTime;           //记录创建时间
	private String strLastAccessedTime;           //记录修改时间
	private int iExtendedAttribute;		//扩展属性（结合iPayType使用) 例：若iPayType=0（积分支付) ,则该字段保存的为剩余积分数量,其它同理
	private int iPurchaseType;	//购买种类0商品 1服务
	public void setiPurchaseType(int iPurchaseType)
	{
		this.iPurchaseType=iPurchaseType;
	}
	
	public int getiPurchaseType()
	{
		return iPurchaseType;
	}
	public void setiExtendedAttribute(int iExtendedAttribute)
	{
		this.iExtendedAttribute=iExtendedAttribute;
	}
	
	public int getiExtendedAttribute()
	{
		return iExtendedAttribute;
	}


	public void setStrOrderId(String strOrderId)
	{
		this.strOrderId=strOrderId;
	}

	public String getStrOrderId()
	{
		return strOrderId;
	}

	public void setStrMemberId(String strMemberId)
	{
		this.strMemberId=strMemberId;
	}

	public String getStrMemberId()
	{
		return strMemberId;
	}

	public void setStrMemberCardNumber(String strMemberCardNumber)
	{
		this.strMemberCardNumber=strMemberCardNumber;
	}

	public String getStrMemberCardNumber()
	{
		return strMemberCardNumber;
	}

	public void setStrMemberName(String strMemberName)
	{
		this.strMemberName=strMemberName;
	}

	public String getStrMemberName()
	{
		return strMemberName;
	}

	public void setStrLevelsId(String strLevelsId)
	{
		this.strLevelsId=strLevelsId;
	}

	public String getStrLevelsId()
	{
		return strLevelsId;
	}

	public void setStrGoodsId(String strGoodsId)
	{
		this.strGoodsId=strGoodsId;
	}

	public String getStrGoodsId()
	{
		return strGoodsId;
	}

	public void setStrGoodsName(String strGoodsName)
	{
		this.strGoodsName=strGoodsName;
	}

	public String getStrGoodsName()
	{
		return strGoodsName;
	}

	public void setiPurchaseAmount(int iPurchaseAmount)
	{
		this.iPurchaseAmount=iPurchaseAmount;
	}

	public int getiPurchaseAmount()
	{
		return iPurchaseAmount;
	}

	public void setdPrice(BigDecimal dPrice)
	{
		this.dPrice=dPrice;
	}

	public BigDecimal getdPrice()
	{
		return dPrice;
	}

	public void setStrUnitName(String strUnitName)
	{
		this.strUnitName=strUnitName;
	}

	public String getStrUnitName()
	{
		return strUnitName;
	}

	public void setdPurchaseCashTotalAmount(BigDecimal dPurchaseCashTotalAmount)
	{
		this.dPurchaseCashTotalAmount=dPurchaseCashTotalAmount;
	}

	public BigDecimal getdPurchaseCashTotalAmount()
	{
		return dPurchaseCashTotalAmount;
	}

	public void setdPreferentialPrice(BigDecimal dPreferentialPrice)
	{
		this.dPreferentialPrice=dPreferentialPrice;
	}

	public BigDecimal getdPreferentialPrice()
	{
		return dPreferentialPrice;
	}

	public void setdPreferentialCashTotalAmount(BigDecimal dPreferentialCashTotalAmount)
	{
		this.dPreferentialCashTotalAmount=dPreferentialCashTotalAmount;
	}

	public BigDecimal getdPreferentialCashTotalAmount()
	{
		return dPreferentialCashTotalAmount;
	}

	public void setiIntegrationAmount(int iIntegrationAmount)
	{
		this.iIntegrationAmount=iIntegrationAmount;
	}

	public int getiIntegrationAmount()
	{
		return iIntegrationAmount;
	}

	public void setiPurchaseIntegrationAmount(int iPurchaseIntegrationAmount)
	{
		this.iPurchaseIntegrationAmount=iPurchaseIntegrationAmount;
	}

	public int getiPurchaseIntegrationAmount()
	{
		return iPurchaseIntegrationAmount;
	}

	public void setiStatus(int iStatus)
	{
		this.iStatus=iStatus;
	}

	public int getiStatus()
	{
		return iStatus;
	}

	public void setiPayStandard(int iPayStandard)
	{
		this.iPayStandard=iPayStandard;
	}

	public int getiPayStandard()
	{
		return iPayStandard;
	}

	public void setStrPayTime(String strPayTime)
	{
		this.strPayTime=strPayTime;
	}

	public String getStrPayTime()
	{
		return strPayTime;
	}

	public void setiPayType(int iPayType)
	{
		this.iPayType=iPayType;
	}

	public int getiPayType()
	{
		return iPayType;
	}

	public void setStrThirdPartyTradeFlow(String strThirdPartyTradeFlow)
	{
		this.strThirdPartyTradeFlow=strThirdPartyTradeFlow;
	}

	public String getStrThirdPartyTradeFlow()
	{
		return strThirdPartyTradeFlow;
	}

	public void setStrExpressNumber(String strExpressNumber)
	{
		this.strExpressNumber=strExpressNumber;
	}

	public String getStrExpressNumber()
	{
		return strExpressNumber;
	}

	public void setStrExpressCompany(String strExpressCompany)
	{
		this.strExpressCompany=strExpressCompany;
	}

	public String getStrExpressCompany()
	{
		return strExpressCompany;
	}

	public void setStrComment(String strComment)
	{
		this.strComment=strComment;
	}

	public String getStrComment()
	{
		return strComment;
	}

	public void setiReceiveType(int iReceiveType)
	{
		this.iReceiveType=iReceiveType;
	}

	public int getiReceiveType()
	{
		return iReceiveType;
	}

	public void setStrReceiveTime(String strReceiveTime)
	{
		this.strReceiveTime=strReceiveTime;
	}

	public String getStrReceiveTime()
	{
		return strReceiveTime;
	}

	public void setStrCreationTime(String strCreationTime)
	{
		this.strCreationTime=strCreationTime;
	}

	public String getStrCreationTime()
	{
		return strCreationTime;
	}

	public void setStrLastAccessedTime(String strLastAccessedTime)
	{
		this.strLastAccessedTime=strLastAccessedTime;
	}

	public String getStrLastAccessedTime()
	{
		return strLastAccessedTime;
	}	

}
