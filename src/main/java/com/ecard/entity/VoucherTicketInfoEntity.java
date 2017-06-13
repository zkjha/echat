package com.ecard.entity;

import java.math.BigDecimal;

//抵用卷信息实体
public class VoucherTicketInfoEntity {
	private String strVoucherTicketId;	//抵用卷ID
	private String strVoucherTicketName;	//抵用卷名称
	private BigDecimal dVoucherTicketAmount;	//抵用卷金额
	private String strValidEndTime;//有效期截止时间
	private int iIsValid;	//是否启用：1启用 0禁用
	private String strRuleDesc;	//使用卷规则描述
	private String strReserved;	//预留字段 
	private String strCreationTime;		//创建时间
	private String strLastAccessedTime;	//修改时间
	private String strEmployeeId;			//登录员工ID
	private String strEmployeeName;		//登录员工姓名
	private String strEmployeeRealName;	//登录员工真实姓名
	
	public void setStrVoucherTicketId(String strVoucherTicketId)
	{
		this.strVoucherTicketId=strVoucherTicketId;
	}
	
	public String getStrVoucherTicketId()
	{
		return strVoucherTicketId;
	}
	
	public void setStrVoucherTicketName(String strVoucherTicketName)
	{
		this.strVoucherTicketName=strVoucherTicketName;
	}
	
	public String getStrVoucherTicketName()
	{
		return strVoucherTicketName;
	}
	
	public void setDvoucherTicketAmount(BigDecimal dVoucherTicketAmount)
	{
		this.dVoucherTicketAmount=dVoucherTicketAmount;
	}
	
	public BigDecimal getDvoucherTicketAmount()
	{
		return dVoucherTicketAmount;
	}
	
	public void setStrValidEndTime(String strValidEndTime)
	{
		this.strValidEndTime=strValidEndTime;
	}
	
	public String getStrValidEndTime()
	{
		return strValidEndTime;
	}
	
	public void setIisValid(int iIsValid)
	{
		this.iIsValid=iIsValid;
	}
	
	public int getIisValid()
	{
		return iIsValid;
	}
	
	public void setStrRuleDesc(String strRuleDesc)
	{
		this.strRuleDesc=strRuleDesc;
	}
	
	public String getStrRuleDesc()
	{
		return strRuleDesc;
	}
	
	public void setStrReserved(String strReserved)
	{
		this.strReserved=strReserved;
	}
	
	public String getStrReserved()
	{
		return strReserved;
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
