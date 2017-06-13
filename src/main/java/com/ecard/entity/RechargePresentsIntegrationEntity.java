package com.ecard.entity;

import java.math.BigDecimal;

public class RechargePresentsIntegrationEntity {
	private String strPresentsIntegrationId;	//主键
	private String strActivityId;	//关联活动主键
	private BigDecimal dPerTimeRechargeAmount;	//每充值多少送积分
	private BigDecimal dLeastRechargeAmount;//最低充值多少送积分	
	private int iEnabled=0;						//是否启用 1启用 0禁用
	private String strEmployeeId;				//管理员ID
	private String strEmployeeName;				//管理员账号
	private String strEmployeeRealName;			//管理员姓名
	private String strCreationTime;				//创建记录时间
	private String strLastAccessedTime;			//修改时间
	public void setStrPresentsIntegrationId(String strPresentsIntegrationId)
	{
		this.strPresentsIntegrationId=strPresentsIntegrationId;
	}
	
	public String getStrPresentsIntegrationId()
	{
		return strPresentsIntegrationId;
	}
	
	public void setStrActivityId(String strActivityId)
	{
		this.strActivityId=strActivityId;
	}
	
	public String getStrActivityId()
	{
		return strActivityId;
	}
	
	public void setdPerTimeRechargeAmount(BigDecimal dPerTimeRechargeAmount)
	{
		this.dPerTimeRechargeAmount=dPerTimeRechargeAmount;
	}
	
	public BigDecimal getdPerTimeRechargeAmount()
	{
		return dPerTimeRechargeAmount;
	}
	
	public void setdLeastRechargeAmount(BigDecimal dLeastRechargeAmount)
	{
		this.dLeastRechargeAmount=dLeastRechargeAmount;
	}
	
	public BigDecimal getdLeastRechargeAmount()
	{
		return dLeastRechargeAmount;
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
