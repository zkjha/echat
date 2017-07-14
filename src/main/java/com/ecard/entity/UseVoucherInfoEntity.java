package com.ecard.entity;

//抵用券的使用信息
public class UseVoucherInfoEntity {
	private String strRecordId;           //关键
	private String strVoucherTicketId;           //抵用券ID
	private String strOrderId;           //订单ID
	private int iOrderIdFrom;           //订单ID来源 :0 从生台订单表取得 1 从手机端订单取得
	private String strMemberId;           //会员ID
	private String strConsumeDate;           //消费日期
	private int iPayOrderUseCount;           //付款所使用的抵用券次数
	
	private String strVoucherTicketName="";	//抵用券名称
	
	public void setStrVoucherTicketName(String strVoucherTicketName)
	{
		this.strVoucherTicketName=strVoucherTicketName;
	}
	
	public String getStrVoucherTicketName()
	{
		return strVoucherTicketName;
	}


	public void setStrRecordId(String strRecordId)
	{
		this.strRecordId=strRecordId;
	}

	public String getStrRecordId()
	{
		return strRecordId;
	}

	public void setStrVoucherTicketId(String strVoucherTicketId)
	{
		this.strVoucherTicketId=strVoucherTicketId;
	}

	public String getStrVoucherTicketId()
	{
		return strVoucherTicketId;
	}

	public void setStrOrderId(String strOrderId)
	{
		this.strOrderId=strOrderId;
	}

	public String getStrOrderId()
	{
		return strOrderId;
	}

	public void setiOrderIdFrom(int iOrderIdFrom)
	{
		this.iOrderIdFrom=iOrderIdFrom;
	}

	public int getiOrderIdFrom()
	{
		return iOrderIdFrom;
	}

	public void setStrMemberId(String strMemberId)
	{
		this.strMemberId=strMemberId;
	}

	public String getStrMemberId()
	{
		return strMemberId;
	}

	public void setStrConsumeDate(String strConsumeDate)
	{
		this.strConsumeDate=strConsumeDate;
	}

	public String getStrConsumeDate()
	{
		return strConsumeDate;
	}

	public void setiPayOrderUseCount(int iPayOrderUseCount)
	{
		this.iPayOrderUseCount=iPayOrderUseCount;
	}

	public int getiPayOrderUseCount()
	{
		return iPayOrderUseCount;
	}

}
