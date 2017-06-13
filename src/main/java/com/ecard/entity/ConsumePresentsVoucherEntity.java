package com.ecard.entity;

import java.math.BigDecimal;

public class ConsumePresentsVoucherEntity
{
	private String strConsumePresentsVoucherId;           //关键字
	private String strVoucherTicketId;           //关联抵用券ID
	private String strActivityId;           //关联活动ID
	private BigDecimal dConsumeCashAmount;        //消费现金量
	private int iPresentsIntegrationAmount;           //赠送积分量
	private int iEnabled;           //是否启有 1启用 0禁用
	private String strEmployeeId;           //COMMENT
	private String strEmployeeName;           //COMMENT
	private String strEmployeeRealName;           //COMMENT
	private String strCreationTime;           //COMMENT
	private String strLastAccessedTime;           //COMMENT


	public void setStrConsumePresentsVoucherId(String strConsumePresentsVoucherId)
	{
		this.strConsumePresentsVoucherId=strConsumePresentsVoucherId;
	}

	public String getStrConsumePresentsVoucherId()
	{
		return strConsumePresentsVoucherId;
	}

	public void setStrVoucherTicketId(String strVoucherTicketId)
	{
		this.strVoucherTicketId=strVoucherTicketId;
	}

	public String getStrVoucherTicketId()
	{
		return strVoucherTicketId;
	}

	public void setStrActivityId(String strActivityId)
	{
		this.strActivityId=strActivityId;
	}

	public String getStrActivityId()
	{
		return strActivityId;
	}

	public void setdConsumeCashAmount(BigDecimal dConsumeCashAmount)
	{
		this.dConsumeCashAmount=dConsumeCashAmount;
	}

	public BigDecimal getdConsumeCashAmount()
	{
		return dConsumeCashAmount;
	}

	public void setiPresentsIntegrationAmount(int iPresentsIntegrationAmount)
	{
		this.iPresentsIntegrationAmount=iPresentsIntegrationAmount;
	}

	public int getiPresentsIntegrationAmount()
	{
		return iPresentsIntegrationAmount;
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
