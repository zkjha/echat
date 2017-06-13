package com.ecard.entity;

import java.math.BigDecimal;

public class RechargePresentsStoredValueEntity {
	private String strPresentsStoredValueId;	//关键字
	private String strActivityId;	//活动关键字 ，关联活动表
	private BigDecimal dRechargeAmount;	//充值金额
	private BigDecimal dPresentsAmount;//赠送金额
	private String strValidateBeginTime;	//有效期开始时间
	private String strValidateEndTime;//有效期结束时间
	private int iEnabled=0;						//是否启用 1启用 0禁用
	private String strEmployeeId;				//管理员ID
	private String strEmployeeName;				//管理员账号
	private String strEmployeeRealName;			//管理员姓名
	private String strCreationTime;				//创建记录时间
	private String strLastAccessedTime;			//修改时间
	
	public void setStrPresentsStoredValueId(String strPresentsStoredValueId)
	{
		this.strPresentsStoredValueId=strPresentsStoredValueId;
	}
	
	public String getStrPresentsStoredValueId()
	{
		return strPresentsStoredValueId;
	}
	
	public void setStrActivityId(String strActivityId)
	{
		this.strActivityId=strActivityId;
	}
	
	public String getStrActivityId()
	{
		return strActivityId;
	}
	
	public void setdPresentsAmount(BigDecimal bgPresentsAmount)
	{
		this.dPresentsAmount=bgPresentsAmount;
	}
	
	public BigDecimal getdPresentsAmount()
	{
		return dPresentsAmount;
	}
	
	
	public void setdRechargeAmount(BigDecimal bgRechargeAmount)
	{
		this.dRechargeAmount=bgRechargeAmount;
	}
	
	public BigDecimal getdRechargeAmount()
	{
		return dRechargeAmount;
	}
	
	
	public void setStrValidateBeginTime(String strValidateBeginTime)
	{
		this.strValidateBeginTime=strValidateBeginTime;
	}
	
	public String getStrValidateBeginTime()
	{
		return strValidateBeginTime;
	}
	
	public void setStrValidateEndTime(String strValidateEndTime)
	{
		this.strValidateEndTime=strValidateEndTime;
	}
	
	public String getStrValidateEndTime()
	{
		return strValidateEndTime;
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
