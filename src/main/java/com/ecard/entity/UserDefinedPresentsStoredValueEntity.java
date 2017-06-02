package com.ecard.entity;

import java.math.BigDecimal;

public class UserDefinedPresentsStoredValueEntity
{
	private String strPresentsStoredValueId;           //关键字
	private String strActivityId;           //关联活动ID
	private BigDecimal dPresentsAmount;        //赠送储值金额
	private int iEnabled;           //是否启用 1启用 0 禁用
	private String strEmployeeId;           //请添加对该变量的描述
	private String strEmployeeName;           //请添加对该变量的描述
	private String strEmployeeRealName;           //请添加对该变量的描述
	private String strCreationTime;           //请添加对该变量的描述
	private String strLastAccessedTime;           //请添加对该变量的描述


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

	public void setdPresentsAmount(BigDecimal dPresentsAmount)
	{
		this.dPresentsAmount=dPresentsAmount;
	}

	public BigDecimal getdPresentsAmount()
	{
		return dPresentsAmount;
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
