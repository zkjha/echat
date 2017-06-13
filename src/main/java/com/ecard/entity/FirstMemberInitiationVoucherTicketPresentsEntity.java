package com.ecard.entity;

//首次入会赠送抵用卷实体
public class FirstMemberInitiationVoucherTicketPresentsEntity {
	private String strVoucherTicketPresentsId;	//关键字
	private String strVoucherTicketKindId;	//赠送抵用卷种类ID 关联其他表
	private int iTotalVoucherTicketNum;			//可对初次入会会员赠送的总张数
	private int iRestVoucherTicketNum;			//剩余张数
	private int iEnabled=0;						//是否启用 1启用 0禁用
	private String strEmployeeId;				//管理员ID
	private String strEmployeeName;				//管理员账号
	private String strEmployeeRealName;			//管理员姓名
	private String strCreationTime;				//创建记录时间
	private String strLastAccessedTime;			//修改时间
	
	public void setStrVoucherTicketPresentsId(String strVoucherTicketPresentsId)
	{
		this.strVoucherTicketPresentsId=strVoucherTicketPresentsId;
	}
	
	public String getStrVoucherTicketPresentsId()
	{
		return strVoucherTicketPresentsId;
	}
	
	public void setStrVoucherTicketKindId(String strVoucherTicketKindId)
	{
		this.strVoucherTicketKindId=strVoucherTicketKindId;
	}
	
	public String getStrVoucherTicketKindId()
	{
		return strVoucherTicketKindId;
	}
	
	public void setItotalVoucherTicketNum(int iTotalVoucherTicketNum)
	{
		this.iTotalVoucherTicketNum=iTotalVoucherTicketNum;
	}
	
	public int getItotalVoucherTicketNum()
	{
		return iTotalVoucherTicketNum;
	}
	
	public void setIrestVoucherTicketNum(int iRestVoucherTicketNum)
	{
		this.iRestVoucherTicketNum=iRestVoucherTicketNum;
	}
	public int getIrestVoucherTicketNum()
	{
		return iRestVoucherTicketNum;
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
