package com.ecard.entity;

public class UserDefinedPresentsActivityEntity {
	
	private String strActivityId;	//活动ID
	private String strActivityName;	//活动名称
	private String strLevelsId;	//会员级别ID
	private String strActivityBeginTime;	//活动开始时间
	private String strActivityEndTime;	//活动强束时间
	private int iActivityKinds;	//活动类别：:0自定义赠送型，1消费赠送型，2充值 赠送型
	private String strEmployeeId;				//管理员ID
	private String strEmployeeName;				//管理员账号
	private String strEmployeeRealName;			//管理员姓名
	private String strCreationTime;				//创建记录时间
	private String strLastAccessedTime;			//修改时间
	
	//-------------------------------
	private String strActivityStatus;		//活动状态 :NORMAL正常EXPIRED过期;ALL全部 
	private String strMemberLevelName;		//会员级别名称
	private int iIntegrationEnabled;		//关联该活动的赠送积分启用情况
	private int iStoredTicketEnabled;		//关联该活动的赠送储值启用情况
	private int iVoucherTicketEnabled;		//关联该活动的赠送抵用券启用情况
	
	public void setiIntegrationEnabled(int iIntegrationEnabled)
	{
		this.iIntegrationEnabled=iIntegrationEnabled;
	}
	
	public int getiIntegrationEnabled()
	{
		return iIntegrationEnabled;
	}
	
	public void setiStoredTicketEnabled(int iStoredTicketEnabled)
	{
		this.iStoredTicketEnabled=iStoredTicketEnabled;
	}
	
	public int getiStoredTicketEnabled()
	{
		return iStoredTicketEnabled;
	}
	
	public void setiVoucherTicketEnabled(int iVoucherTicketEnabled)
	{
		this.iVoucherTicketEnabled=iVoucherTicketEnabled;
	}
	
	public int getiVoucherTicketEnabled()
	{
		return iVoucherTicketEnabled;
	}
	
	public void setStrActivityStatus(String strActivityStatus)
	{
		this.strActivityStatus=strActivityStatus;
	}
	
	public String getStrActivityStatus()
	{
		return strActivityStatus;
	}
	
	public void setStrMemberLevelName(String strMemberLevelName)
	{
		this.strMemberLevelName=strMemberLevelName;
	}
	
	public String getStrMemberLevelName()
	{
		return strMemberLevelName;
	}
	
	
	public void setStrActivityId(String strActivityId)
	{
		this.strActivityId=strActivityId;
	}
	
	public String getStrActivityId()
	{
		return strActivityId;
	}
	
	public void setStrActivityName(String strActivityName)
	{
		this.strActivityName=strActivityName;
	}
	
	public String getStrActivityName()
	{
		return strActivityName;
	}
	
	public void setStrLevelsId(String strLevelsId)
	{
		this.strLevelsId=strLevelsId;
	}
	
	public String getStrLevelsId()
	{
		return strLevelsId;
	}
	
	public void setStrActivityBeginTime(String strActivityBeginTime)
	{
		this.strActivityBeginTime=strActivityBeginTime;
	}
	
	public String getStrActivityBeginTime()
	{
		return strActivityBeginTime;
	}
	
	public void setStrActivityEndTime(String strActivityEndTime)
	{
		this.strActivityEndTime=strActivityEndTime;
	}
	
	public String getStrActivityEndTime()
	{
		return strActivityEndTime;
	}
	
	public void setiActivityKinds(int iActivityKinds)
	{
		this.iActivityKinds=iActivityKinds;
	}
	
	public int getiActivityKinds()
	{
		return iActivityKinds;
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
