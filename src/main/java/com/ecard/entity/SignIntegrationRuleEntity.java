package com.ecard.entity;

public class SignIntegrationRuleEntity{
	private String strSignId;				//主键
	private String strSignDays;				//签到天数
	private String strStatus;				//签到状态：'0'表累积签到天数,'1'表连续签到天数
	private int iIntegration;				//赠送积分
	private String strEnabled;				//是否启用,'1'表启用，‘0’表禁用
	private String strEmployeeId;			//登录员工ID
	private String strEmployeeName;			//登录员工帐号
	private String strEmployeeRealName;		//登录员工姓名
	private String strCreationTime;			//创建规则时间
	private String strLastAccessedTime;		//最后修改时间
	
	public void setStrSignId(String strSignId)
	{
		this.strSignId=strSignId;
	}
	
	public String getStrSignId()
	{
		return strSignId;
	}
	
	public void setStrSignDays(String strSignDays)
	{
		this.strSignDays=strSignDays;
	}
	
	public String getStrSignDays()
	{
		return strSignDays;
	}
	
	public void setStrStatus(String strStatus)
	{
		this.strStatus=strStatus;
	}

	public String getStrStatus()
	{
		return strStatus;
	}
	
	public void setIIntegration(int iIntegration)
	{
		this.iIntegration=iIntegration;
	}
	
	public int getIIntegration()
	{
		return iIntegration;
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
