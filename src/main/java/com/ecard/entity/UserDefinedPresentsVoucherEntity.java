package com.ecard.entity;

public class UserDefinedPresentsVoucherEntity
{
	private String strPresentsVoucherId;           //主键
	private String strVoucherTicketId;           //关联抵用券IDtb_voucherticket_infomanage
	private String strActivityId;           //关联自定义赠送活动ID
	private int iTotalNum;           //可对所有会员赠送的抵用券总量
	private int iRestNum;           //抵用券剩余数量
	private int iEnabled;           //是否启用
	private String strEmployeeId;           //COMMENT
	private String strEmployeeName;           //COMMENT
	private String strEmployeeRealName;           //COMMENT
	private String strCreationTime;           //COMMENT
	private String strLastAccessedTime;           //COMMENT


	public void setStrPresentsVoucherId(String strPresentsVoucherId)
	{
		this.strPresentsVoucherId=strPresentsVoucherId;
	}

	public String getStrPresentsVoucherId()
	{
		return strPresentsVoucherId;
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
