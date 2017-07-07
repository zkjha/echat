package com.ecard.entity;

//全国各个区县
public class AreaCountyEntity {
	private int iAreaCountryId;           //区县主键
	private int iAreaCountryCode;           //区县代码
	private String strAreaCountryName;           //区县名称
	private int iCityCode;           //城市代码


	public void setiAreaCountryId(int iAreaCountryId)
	{
		this.iAreaCountryId=iAreaCountryId;
	}

	public int getiAreaCountryId()
	{
		return iAreaCountryId;
	}

	public void setiAreaCountryCode(int iAreaCountryCode)
	{
		this.iAreaCountryCode=iAreaCountryCode;
	}

	public int getiAreaCountryCode()
	{
		return iAreaCountryCode;
	}

	public void setStrAreaCountryName(String strAreaCountryName)
	{
		this.strAreaCountryName=strAreaCountryName;
	}

	public String getStrAreaCountryName()
	{
		return strAreaCountryName;
	}

	public void setiCityCode(int iCityCode)
	{
		this.iCityCode=iCityCode;
	}

	public int getiCityCode()
	{
		return iCityCode;
	}
	
	

}
