package com.ecard.entity;

import java.util.Date;

//全国各个区县
public class AreaCountyEntity {
	private String aId="";//自动编号，标识列
	private String acode="";//区县代码
	private String aname="";//区县名称
	private String cityCode="";//城市代码
	private Date arecordCreationDate;//记录创建时间
	private String arecordCreator="";//记录创建者
	private String arecordVersion="";//记录版本
	
	public void setAId(String aId)
	{
		this.aId=aId;
	}
	
	public String getAId()
	{
		return aId;
	}
	
	
	public void setAcode(String acode)
	{
		this.acode=acode;
	}
	
	public String getAcode()
	{
		return acode;
	}
	
	public void setAname(String aname)
	{
		this.aname=aname;
	}
	
	public String getAname()
	{
		return aname;
	}
	
	public void setCityCode(String cityCode)
	{
		this.cityCode=cityCode;
	}
	
	public String getCityCode()
	{
		return cityCode;
	}
	
	public void setArecordCreationDate(Date arecordCreationDate)
	{
		this.arecordCreationDate=arecordCreationDate;
	}
	
	public Date getArecordCreationDate()
	{
		return arecordCreationDate;
	}
	
	public void setArecordCreator(String arecordCreator)
	{
		this.arecordCreator=arecordCreator;
	}
	
	public String getArecordCreator()
	{
		return arecordCreator;
	}
	
	public void setArecordVersion(String arecordVersion)
	{
		this.arecordVersion=arecordVersion;
	}
	
	public String getArecordVersion()
	{
		return arecordVersion;
	}
	
	

}
