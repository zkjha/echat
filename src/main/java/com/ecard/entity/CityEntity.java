package com.ecard.entity;

import java.util.Date;

//全国各大城市
public class CityEntity {
	private String cId="";//自动编号，标识列
	private String ccode="";//城市代码
	private String cname="";//城市名称
	private String provinceCode="";//省份代码
	private Date crecordCreationDate;//记录创建时间
	private String crecordCreator="";//记录创建者
	private String crecordVersion="";//记录版本
	
	public void setCID(String cId)
	{
		this.cId=cId;
	}
	
	public String getCID()
	{
		return cId;
	}
	
	public void setCcode(String ccode)
	{
		this.ccode=ccode;
	}
	
	public String getCcode()
	{
		return ccode;
	}
	
	public void setCname(String cname)
	{
		this.cname=cname;
	}
	
	public String getCname()
	{
		return cname;
	}
	
	public void setProvinceCode(String provinceCode)
	{
		this.provinceCode=provinceCode;
	}
	
	public String getProvinceCode()
	{
		return provinceCode;
	}

	
	public void setCrecordCreationDate(Date crecordCreationDate )
	{
		this.crecordCreationDate=crecordCreationDate;
	}
	
	public Date getCrecordCreationDate()
	{
		return crecordCreationDate;
	}
	
	public void setCrecordCreator(String crecordCreator)
	{
		this.crecordCreator=crecordCreator;
	}
	
	public String getCrecordCreator()
	{
		return crecordCreator;
	}
	
	public void setCrecordVersion(String crecordVersion)
	{
		this.crecordVersion=crecordVersion;
	}
	
	public String getCrecordVersion()
	{
		return crecordVersion;
	}
	
	


}
