package com.ecard.entity;

import java.math.BigDecimal;

public class MemberPurchaseOrderEntity
{
private String strOrderId;           //主键
private String strOrderNum;           //订单号
private String strMemberId;           //会员ID
private String strMemberCardNumber;           //会员卡编号
private String strMemberName;           //用户姓名
private String strLevelsId;			//会员级别ID
private String strProductServiceId;           //商品或服务ID
private String strProductServiceName;           //商品或服务名称
private int iPurchaseType;           //购买的类型：0商品 1服务
private int iPurchaseAmount;           //购买商品或服务数量
private String strUnitName;			//商品或服务的计量单位
private BigDecimal dPrice;        //商品或服务单价(原价）
private BigDecimal dPurchaseCashTotalAmount;        //订单总金额（原价计算得来)
private BigDecimal dPreferentialPrice;        //商品或服务的会员优惠价
private BigDecimal dPreferentialCashTotalAmount;        //优惠后的商品或服务总价
private int iStatus;           //订单状态0：待支付 1：已支付 2：已发货 3：已完成
private int iPayStandard;           //支付标准：0 会员价（优惠价)支付,1原价支付
private String strPayTime;           //支付时间
private int iPayType;           //支付方式 0：积分兑换 1：微信支付 2：支付宝支付
private String strThirdPartyTradeFlow;           //三方支付流水号
private String strComment;					//备注字段 
private String strExpressNumber;           //快递单号
private String strExpressCompany;           //快递公司
private String strEmployeeId;           //管理员ID
private String strEmployeeName;           //管理员账号
private String strEmployeeRealName;           //管理员姓名
private String strCreationTime;           //记录创建时间
private String strLastAccessedTime;           //记录修改时间


public void setStrOrderId(String strOrderId)
{
	this.strOrderId=strOrderId;
}

public String getStrOrderId()
{
	return strOrderId;
}

public void setStrOrderNum(String strOrderNum)
{
	this.strOrderNum=strOrderNum;
}

public String getStrOrderNum()
{
	return strOrderNum;
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

public void setStrProductServiceId(String strProductServiceId)
{
	this.strProductServiceId=strProductServiceId;
}

public String getStrProductServiceId()
{
	return strProductServiceId;
}

public void setStrProductServiceName(String strProductServiceName)
{
	this.strProductServiceName=strProductServiceName;
}

public String getStrProductServiceName()
{
	return strProductServiceName;
}

public void setiPurchaseType(int iPurchaseType)
{
	this.iPurchaseType=iPurchaseType;
}

public int getiPurchaseType()
{
	return iPurchaseType;
}

public void setiPurchaseAmount(int iPurchaseAmount)
{
	this.iPurchaseAmount=iPurchaseAmount;
}

public int getiPurchaseAmount()
{
	return iPurchaseAmount;
}

public void setStrUnitName(String strUnitName)
{
	this.strUnitName=strUnitName;
}

public String getStrUnitName()
{
	return strUnitName;
}

public void setdPrice(BigDecimal dPrice)
{
	this.dPrice=dPrice;
}

public BigDecimal getdPrice()
{
	return dPrice;
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

public void setStrComment(String strComment)
{
	this.strComment=strComment;
}

public String getStrComment()
{
	return strComment;
}


public void setStrExpressCompany(String strExpressCompany)
{
	this.strExpressCompany=strExpressCompany;
}

public String getStrExpressCompany()
{
	return strExpressCompany;
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

}
