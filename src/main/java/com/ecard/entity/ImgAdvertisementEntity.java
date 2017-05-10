package com.ecard.entity;

@SuppressWarnings("unused")

public class ImgAdvertisementEntity {
	private String strImgId;			//图片id
	private int iImgOrder;				//图片序号
	private String strImgName;			//图处名称
	private String strCreationTime;		//创建时间
	private String strLastAccessedTime;	//修改时间
	private String strEmployeeId;			//登录员工ID
	private String strEmployeeName;		//登录员工姓名
	private String strEmployeeRealName;	//登录员工真实姓名
	
	public void setStrImgId(String strImgId)
	{
		this.strImgId=strImgId;
	}
	
	public String getStrImgId()
	{
		return strImgId;
	}
	
	public void setStrImgName(String strImgName)
	{
		this.strImgName=strImgName;
	}
	
	public String getStrImgName(String strImgName)
	{
		return strImgName;
	}
	
	public void setIImgOrder(int iImgOrder)
	{
		this.iImgOrder=iImgOrder;
	}
	
	public int getIImgOrder()
	{
		return iImgOrder;
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
