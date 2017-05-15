package com.ecard.entity;

//顾客初次入会赠送储值卷类
public class FirstMemberInitiationStoredTicketPresentsEntity {
	private String strStoredTicketPresentsId;	//关键字
	private int iStoredValuePresents;			//赠送储值量
	private int iTotalStoredTicketNum;			//对首次入会的所有会员可以发放的储值卷总张数
	private int iRestStoredTicketNum;			//对首次入会的所有会员可以发放的储值卷剩余张数
	private String strValidateBeginTime;		//储值卷有效期开始时间
	private String strValidateEndTime;			//储值卷有效期结束时间
	private int iEnabled=0;						//是否启用 1启用 0禁用
	private String strEmployeeId;				//管理员ID
	private String strEmployeeName;				//管理员账号
	private String strEmployeeRealName;			//管理员姓名
	private String strCreationTime;				//创建记录时间
	private String strLastAccessedTime;			//修改时间
	
	public void setStrStoredTicketPresentsId(String strStoredTicketPresentsId)
	{
		this.strStoredTicketPresentsId=strStoredTicketPresentsId;
	}
	
	public String getStrStoredTicketPresentsId()
	{
		return strStoredTicketPresentsId;
	}
	
	public void setIstoredValuePresents(int iStoredValuePresents)
	{
		this.iStoredValuePresents=iStoredValuePresents;
	}
	
	public int getIstoredValuePresents()
	{
		return iStoredValuePresents;
	}
	
	public void setItotalStoredTicketNum(int iTotalStoredTicketNum)
	{
		this.iTotalStoredTicketNum=iTotalStoredTicketNum;
	}
	
	public int getItotalStoredTicketNum()
	{
		return iTotalStoredTicketNum;
	}
	
	public void setIrestStoredTicketNum(int iRestStoredTicketNum)
	{
		this.iRestStoredTicketNum=iRestStoredTicketNum;
	}
	
	public int getIrestStoredTicketNum()
	{
		return iRestStoredTicketNum;
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
	
	public void setIEnabled(int iEnabled)
	{
		this.iEnabled=iEnabled;
	}
	
	public int getIenabled()
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
