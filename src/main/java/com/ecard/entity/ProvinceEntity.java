package com.ecard.entity;
//全国省份
public class ProvinceEntity 
{
	private int iProvinceId;	//自动编号，标识列
	private int iProvinceCode;//省份代码
	private String strProvinceName="";//省份名称
	private int iNationCode;//国家代码
	public void setiProvinceId(int iProvinceId)
	{
		this.iProvinceId=iProvinceId;
	}
	
	public int getiProvinceId()
	{
		return iProvinceId;
	}
	
	public void setiProvinceCode(int iProvinceCode)
	{
		this.iProvinceCode=iProvinceCode;
	}
	
	public int getiProvinceCode()
	{
		return iProvinceCode;
	}
	
	public void setstrProvinceName(String strProvinceName)
	{
		this.strProvinceName=strProvinceName;
	}
	
	public String getstrProvinceName()
	{
		return strProvinceName;
	}
	
	public void setiNationCode(int iNationCode)
	{
		this.iNationCode=iNationCode;
	}
	
	public int getiNationCode()
	{
		return iNationCode;
	}
	
}
