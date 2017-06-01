package com.ecard.entity;

import java.math.BigDecimal;

//充值赠送抵用券实体
public class RechargePresentsVoucherEntity {
	private String strRechargePresentsVoucherId;           //关键字
	private String strBasePresentsVoucherTicketId;           //基本充值赠送抵用券类型
	private String strMorePresentsVoucherTicketId;           //多充值多赠送抵用券类型
	private String strActivityId;           //关联活动ID
	private BigDecimal dMinimumRechargeAmount;        //最低充值金额
	private int iMinimumPresentsVoucherNumber ;//最低赠送数量
	private BigDecimal dMoreRechargeAmount;        //多充值金额
	private int iMoreRresentsVoucherNumber ;//多赠送数量
	private int iEnabled=0;						//是否启用 1启用 0禁用
	private String strEmployeeId;				//管理员ID
	private String strEmployeeName;				//管理员账号
	private String strEmployeeRealName;			//管理员姓名
	private String strCreationTime;				//创建记录时间
	private String strLastAccessedTime;			//修改时间
	public void setStrRechargePresentsVoucherId(String strRechargePresentsVoucherId)
	{
		this.strRechargePresentsVoucherId=strRechargePresentsVoucherId;
	}

	public String getStrRechargePresentsVoucherId()
	{
		return strRechargePresentsVoucherId;
	}

	public void setStrBasePresentsVoucherTicketId(String strBasePresentsVoucherTicketId)
	{
		this.strBasePresentsVoucherTicketId=strBasePresentsVoucherTicketId;
	}

	public String getStrBasePresentsVoucherTicketId()
	{
		return strBasePresentsVoucherTicketId;
	}

	public void setStrMorePresentsVoucherTicketId(String strMorePresentsVoucherTicketId)
	{
		this.strMorePresentsVoucherTicketId=strMorePresentsVoucherTicketId;
	}

	public String getStrMorePresentsVoucherTicketId()
	{
		return strMorePresentsVoucherTicketId;
	}

	public void setStrActivityId(String strActivityId)
	{
		this.strActivityId=strActivityId;
	}

	public String getStrActivityId()
	{
		return strActivityId;
	}

	public void setdMinimumRechargeAmount(BigDecimal dMinimumRechargeAmount)
	{
		this.dMinimumRechargeAmount=dMinimumRechargeAmount;
	}

public BigDecimal getdMinimumRechargeAmount()
{
	return dMinimumRechargeAmount;
}

	public void setiMinimumPresentsVoucherNumber(int iMinimumPresentsVoucherNumber )
	{
		this.iMinimumPresentsVoucherNumber=iMinimumPresentsVoucherNumber;
	}

	public int getiMinimumPresentsVoucherNumber()
	{
		return iMinimumPresentsVoucherNumber;
	}

	public void setdMoreRechargeAmount(BigDecimal dMoreRechargeAmount)
	{
		this.dMoreRechargeAmount=dMoreRechargeAmount;
	}

	public BigDecimal getdMoreRechargeAmount()
	{
		return dMoreRechargeAmount;
	}

	public void setiMoreRresentsVoucherNumber(int iMoreRresentsVoucherNumber )
	{
		this.iMoreRresentsVoucherNumber=iMoreRresentsVoucherNumber;
	}

	public double getiMoreRresentsVoucherNumber()
	{
		return iMoreRresentsVoucherNumber;
	}

	
	public void setiEnabled(int iEnabled)
	{
		this.iEnabled=iEnabled;
	}
	
	public int getiEnabled()
	{
		return iEnabled;
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
