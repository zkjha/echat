package com.ecard.entity;

public class UserDefinedPresentsIntegrationEntity
{
	private String strPresentsIntegrationId;           //关键字
	private String strActivityId;           //关联活动ID
	private int iPresentsIntegration;//赠送积分
	private int iEnabled;           //请是否启用
	private String strEmployeeId;           //登陆员工ID
	private String strEmployeeName;           //登陆员工帐号
	private String strEmployeeRealName;           //登陆员工真实姓名
	private String strCreationTime;           //记录创建时间
	private String strLastAccessedTime;           //修改记录时间


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

	public void setiPresentsIntegration(int iPresentsIntegration )
	{
		this.iPresentsIntegration=iPresentsIntegration;
	}

	public int getiPresentsIntegration()
	{
		return iPresentsIntegration;
	}

	public void setiEnabled(int iEnabled)
	{
		this.iEnabled=iEnabled;
	}

	public int getStriEnabled()
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
