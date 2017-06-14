package com.ecard.entity;

//创建会员入会获赠积分类
public class FirstMemberInitiationIntegrationPresentsEntity {
	private String strIntegrationPresentsId;	//关键字
	private int iIntegrationPresentsValue;		//积分赠送量
	private int iEnabled=0;						//是否启用该条记录 1启用 0禁用		
	private String strEmployeeId;				//管理员ID
	private String strEmployeeName;				//管理员账号
	private String strEmployeeRealName;			//管理员姓名
	private String strCreationTime;				//创建记录时间
	private String strLastAccessedTime;			//修改时间
	
	public void setStrIntegrationPresentsId(String strIntegrationPresentsId)
	{
		this.strIntegrationPresentsId=strIntegrationPresentsId;
	}
	
	public String getStrIntegrationPresentsId()
	{
		return strIntegrationPresentsId;
	}
	
	public void setIIntegrationPresentsValue(int iIntegrationPresentsValue)
	{
		this.iIntegrationPresentsValue=iIntegrationPresentsValue;
	}

	public int getIIntegrationPresentsValue()
	{
		return iIntegrationPresentsValue;
	}
	
	public void setIEnabled(int iEnabled)
	{
		this.iEnabled=iEnabled;
	}
	
	public int getIEnabled()
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
