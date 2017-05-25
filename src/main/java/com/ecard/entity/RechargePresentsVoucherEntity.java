package com.ecard.entity;

import java.math.BigDecimal;

//充值赠送抵用券实体
public class RechargePresentsVoucherEntity {
	public String strRechargePresentsVoucherId;	//主键
	public String strVoucherTicketId;				//抵用券id
	public String strActivityId;	//活动ID
	public String strLevelsId;	//会员级别ID
	public BigDecimal dPerTimeRechargeAmount;		//充值多少金额，可获得一张抵用券
	public BigDecimal dMoreRechargeAmount;			//每多充值多少钱，可多得一张抵用券
	public int iTotalNum;				//抵用券总张数
	public int iRestNum;				//抵用券剩余张数
	public String strValidateBeginTime;	//抵用券有效期开始时间
	public String strValidateEndTime;//抵用券有效期结束时间
	private int iEnabled=0;						//是否启用 1启用 0禁用
	private String strEmployeeId;				//管理员ID
	private String strEmployeeName;				//管理员账号
	private String strEmployeeRealName;			//管理员姓名
	private String strCreationTime;				//创建记录时间
	private String strLastAccessedTime;			//修改时间
	
	public void setStrRechargePresentsVoucherId(String strRechargePresents_voucherId)
	{
		this.strRechargePresentsVoucherId=strRechargePresents_voucherId;
	}
	
	public String getStrRechargePresentsVoucherId()
	{
		return strRechargePresentsVoucherId;
	}
	
	public void setStrVoucherTicketId(String strVoucherTicketId)
	{
		this.strVoucherTicketId=strVoucherTicketId;
	}
	
	public String getStrVoucherTicketId()
	{
		return strVoucherTicketId;
	}
	
	public void setStrActivityId(String strActivityId)
	{
		this.strActivityId=strActivityId;
	}
	
	public String getStrActivityId()
	{
		return strActivityId;
	}
	
	public void setStrLevelsId(String strLevelsId)
	{
		this.strLevelsId=strLevelsId;
	}
	
	public String getStrLevelsId()
	{
		return strLevelsId;
	}
	
	public void setdPerTimeRechargeAmount(BigDecimal dPerTimeRechargeAmount)
	{
		this.dPerTimeRechargeAmount=dPerTimeRechargeAmount;
	}
	
	public BigDecimal getdPerTimeRechargeAmount()
	{
		return dPerTimeRechargeAmount;
	}
	
	public void setdMoreRechargeAmount(BigDecimal dMoreRechargeAmount)
	{
		this.dMoreRechargeAmount=dMoreRechargeAmount;
	}
	
	public BigDecimal getdMoreRechargeAmount()
	{
		return dMoreRechargeAmount;
	}
	
	public void setiTotalNum(int iTotalNum)
	{
		this.iTotalNum=iTotalNum;
	}
	
	public int getiTotalNum()
	{
		return iTotalNum;
	}
	
	public void setiRestNum(int iRestNum)
	{
		this.iRestNum=iRestNum;
	}
	
	public int getiRestNum()
	{
		return iRestNum;
	}
	
	public void setStrValidateBeginTime(String strValidateBeginTime)
	{
		this.strValidateBeginTime=strValidateBeginTime;
	}
	
	public String getStrValidateBeginTime()
	{
		return strValidateBeginTime;
	}
	
	public void setStrValidateEndTime(String strValidateEndTime)
	{
		this.strValidateEndTime=strValidateEndTime;
	}
	
	public String getStrValidateEndTime()
	{
		return strValidateEndTime;
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
