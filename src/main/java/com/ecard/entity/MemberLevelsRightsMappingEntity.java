package com.ecard.entity;
//会员级别权益对应表实体
public class MemberLevelsRightsMappingEntity{
	private String strLevelsRightsMappingId;				//记录关键字
	@SuppressWarnings("unused")
	private String strLevelsId;		//会员级别ID
	private String strRightsId;			//会员权益ID
	private int iRightsStatus=0;	//会员权益状态：0 表购买商品 1表示购买服务
	private double dDiscount;	//折扣率
	private int iPreferentialTimes;	//优惠次数
	private String strEmployeeId;	//登录员工ID
	private String strEmployeeName;//登录员工帐号
	private String strEmployeeRealName;//登录员工真实姓名
	private String strCreationTime;	//创建记录时间
	private String strLastAccessedTime;	//修改时间
	
	public void setStrLevelsRightsMappingId(String strLevelsRightsMappingId)
	{
		this.strLevelsRightsMappingId=strLevelsRightsMappingId;
	}
	
	public String getStrLevelsRightsMappingId()
	{
		return strLevelsRightsMappingId;
	}
	
	public void setStrLevelsId(String strLevelsId)
	{
		this.strLevelsId=strLevelsId;
	}
	
	public String getStrLevelsId(String strLevelsId)
	{
		return strLevelsId;
	}
	
	public void setStrRightsId(String strRightsId)
	{
		this.strRightsId=strRightsId;
	}
	
	public String getStrRightsId()
	{
		return strRightsId;
	}
	
	public void setIrightsStatus(int iRightsStatus)
	{
		this.iRightsStatus=iRightsStatus;
	}
	
	public int getIrightsStatus()
	{
		return iRightsStatus;
	}
	
	public void setDdiscount(double dDiscount)
	{
		this.dDiscount=dDiscount;
	}
	
	public double getDdiscount()
	{
		return dDiscount;
	}
	
	public void setIpreferentialTimes(int iPreferentialTimes)
	{
		this.iPreferentialTimes=iPreferentialTimes;
	}
	
	public int getIpreferentialTimes()
	{
		return iPreferentialTimes;
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
