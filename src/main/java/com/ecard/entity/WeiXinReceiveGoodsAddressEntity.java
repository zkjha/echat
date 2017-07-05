package com.ecard.entity;


//微信页面 -- 收件人信息
public class WeiXinReceiveGoodsAddressEntity
{
	private String strRecordId;           //主键
	private String strReceiveName;           //收货人姓名
	private String strPhone;           //收货人电话
	private String strPostalCode;           //邮政编码
	private String strReceiveAddress;           //邮寄地址
	private String strMemberId;           //会员ID
	private String strOrderId;           //订单ID
	private String strInsertTime;           //录入时间


	public void setStrRecordId(String strRecordId)
	{
		this.strRecordId=strRecordId;
	}

	public String getStrRecordId()
	{
		return strRecordId;
	}

	public void setStrReceiveName(String strReceiveName)
	{
		this.strReceiveName=strReceiveName;
	}

	public String getStrReceiveName()
	{
		return strReceiveName;
	}

	public void setStrPhone(String strPhone)
	{
		this.strPhone=strPhone;
	}

	public String getStrPhone()
	{
		return strPhone;
	}

	public void setStrPostalCode(String strPostalCode)
	{
		this.strPostalCode=strPostalCode;
	}

	public String getStrPostalCode()
	{
		return strPostalCode;
	}

	public void setStrReceiveAddress(String strReceiveAddress)
	{
		this.strReceiveAddress=strReceiveAddress;
	}

	public String getStrReceiveAddress()
	{
		return strReceiveAddress;
	}

	public void setStrMemberId(String strMemberId)
	{
		this.strMemberId=strMemberId;
	}

	public String getStrMemberId()
	{
		return strMemberId;
	}

	public void setStrOrderId(String strOrderId)
	{
		this.strOrderId=strOrderId;
	}

	public String getStrOrderId()
	{
		return strOrderId;
	}

	public void setStrInsertTime(String strInsertTime)
	{
		this.strInsertTime=strInsertTime;
	}

	public String getStrInsertTime()
	{
		return strInsertTime;
	}
}
