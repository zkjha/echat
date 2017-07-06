package com.ecard.entity;

import java.util.Date;

//全国省份
public class ProvinceEntity 
{
	private String pId="";	//自动编号，标识列
	private String pcode="";//省份代码
	private String pname="";//省份名称
	private String nationCode="";//国家代码
	private Date precordCreationDate=null;//记录创建时间
	private String precordCreator="";//记录创建者
	private String precordVersion="";//记录版本
	
	public void setPID(String pId)
	{
		this.pId=pId;
	}
	
	public String getPId()
	{
		return pId;
	}
	
	public void setPcode(String pcode)
	{
		this.pcode=pcode;
	}
	
	public String getPcode()
	{
		return pcode;
	}
	
	public void setPname(String pname)
	{
		this.pname=pname;
	}
	
	public String getPname()
	{
		return pname;
	}
	
	public void setNationCode(String nationCode)
	{
		this.nationCode=nationCode;
	}
	
	public String getNationCode()
	{
		return nationCode;
	}
	
	public void setPrecordCreationDate(Date precordCreationDate)
	{
		this.precordCreationDate=precordCreationDate;
	}
	
	public Date getPrecordCreationDate()
	{
		return precordCreationDate;
	}
	
	public void setPrecordCreator(String precordCreator)
	{
	this.precordCreator=precordCreator;	
	}
	
	public String getPrecordCreator()
	{
		return precordCreator;
	}
	
	public void setPrecordVersion(String precordVersion)
	{
		this.precordVersion=precordVersion;
	}
	
	public String getPrecordVersion()
	{
		return precordVersion;
	}
	

}
