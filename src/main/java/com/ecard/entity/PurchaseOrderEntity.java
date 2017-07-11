package com.ecard.entity;

import java.math.BigDecimal;

public class PurchaseOrderEntity
{
	private String strOrderId;           //订单号
	private String strMemberId;           //会员ID
	private String strMemberCardNumber;           //会员卡号
	private String strMemberName;           //会员姓名 
	private String strLevelsId;           //会员级别ID
	private BigDecimal dPurchaseCashTotalAmount;        //原价总消费金额
	private BigDecimal dPreferentialCashTotalAmount;        //总消费金额优惠价
	private int iPurchaseIntegrationAmount;           //购买产品或服务所需积分数
	private int iStatus;           //订单状态0：待支付 1：已支付 2：已发货 3：已完成
	private int iPayStandard;           //支付标准：0 会员价（优惠价)支付,1原价支付
	private String strPayTime;           //支付时间
	private int iPayType;           //支付方式 0：积分兑换 1：微信支付 2：支付宝支付,3现金支付,4会员卡余额支付
	private String strThirdPartyTradeFlow;           //三方流水号
	private String strExpressNumber;           //快递单号
	private String strExpressCompany;           //快递公司
	private String strComment;           //备注 
	private String strEmployeeId;           //管理员
	private String strEmployeeName;           //管理员帐号
	private String strEmployeeRealName;           //管理员姓名
	private String strCreationTime;           //创建时间
	private String strLastAccessedTime;           //修改时间
	private int iReceiveType;		//收货方式
	private String strReceiveTime;	//收货时间

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

	public void setdPurchaseCashTotalAmount(BigDecimal dPurchaseCashTotalAmount)
	{
		this.dPurchaseCashTotalAmount=dPurchaseCashTotalAmount;
	}

	public BigDecimal getdPurchaseCashTotalAmount()
	{
		return dPurchaseCashTotalAmount;
	}

	public void setdPreferentialCashTotalAmount(BigDecimal dPreferentialCashTotalAmount)
	{
		this.dPreferentialCashTotalAmount=dPreferentialCashTotalAmount;
	}

	public BigDecimal getdPreferentialCashTotalAmount()
	{
		return dPreferentialCashTotalAmount;
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

	public void setStrEmployeeId(String strEmployeeId)
	{
		this.strEmployeeId=strEmployeeId;
	}

	public String getStrEmployeeId()
	{
		return strEmployeeId;
	}

	public void setStrEmployeeName(String strEmployeeName)
	{
		this.strEmployeeName=strEmployeeName;
	}

	public String getStrEmployeeName()
	{
		return strEmployeeName;
	}

	public void setStrEmployeeRealName(String strEmployeeRealName)
	{
		this.strEmployeeRealName=strEmployeeRealName;
	}

	public String getStrEmployeeRealName()
	{
		return strEmployeeRealName;
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

}
