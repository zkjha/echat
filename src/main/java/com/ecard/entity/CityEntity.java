package com.ecard.entity;
//全国各大城市
public class CityEntity {
	private int iCityId;           //城市ID
	private int iCityCode;           //城市代码
	private String strCityName;           //城市名称
	private int iProvinceCode;           //省份代码


	public void setiCityId(int iCityId)
	{
		this.iCityId=iCityId;
	}

	public int getiCityId()
	{
		return iCityId;
	}

	public void setiCityCode(int iCityCode)
	{
		this.iCityCode=iCityCode;
	}

	public int getiCityCode()
	{
		return iCityCode;
	}

	public void setStrCityName(String strCityName)
	{
		this.strCityName=strCityName;
	}

	public String getStrCityName()
	{
		return strCityName;
	}

	public void setiProvinceCode(int iProvinceCode)
	{
		this.iProvinceCode=iProvinceCode;
	}

	public int getiProvinceCode()
	{
		return iProvinceCode;
	}
	


}
