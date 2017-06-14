package com.ecard.entity;

import java.math.BigDecimal;

public class IntegrationCashRuleEntity {
	private String strId;
	private int iIntegration;				//消费积分
	private BigDecimal dCash;					//抵扣现金
	private String strEnabled;				//是否启用：'1'表启用,'0'表禁用
	private String strEmployeeId;			//登录员工ID
	private String strEmployeeName;			//登录员工帐号
	private String strEmployeeRealName;		//登录员工姓名
	private String strCreationTime;			//创建规则时间
	private String strLastAccessedTime;		//最后修改时间
	
	public void setstrId(String strId)
	{
		this.strId=strId;
	}

	public String getStrId()
	{
		return strId;
	}
	
	public void setIIntegration(int iIntegration)
	{
		this.iIntegration=iIntegration;
	}
	
	public int getIIntegration()
	{
		return iIntegration;
	}
	
	public void setDcash(BigDecimal dCash)
	{
		this.dCash=dCash;
	}
	
	public BigDecimal getDCash()
	{
		return dCash;
	}
	
	public void setStrEnabled(String strEnabled)
	{
		this.strEnabled=strEnabled;
	}
	
	public String getStrEnabled()
	{
		return strEnabled;
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

