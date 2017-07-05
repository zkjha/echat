package com.ecard.entity;
//签到表
public class WeiXinMemberSignEntity
{
	private String strSignId;           //主键
	private String strMemberId;           //会员ID
	private String strSignTime;           //签到时间
	private int iSignCount;           //连续签到次数
	private int iTotalSignCount;           //累计连续签到次数


	public void setStrSignId(String strSignId)
	{
		this.strSignId=strSignId;
	}

	public String getStrSignId()
	{
		return strSignId;
	}

	public void setStrMemberId(String strMemberId)
	{
		this.strMemberId=strMemberId;
	}

	public String getStrMemberId()
	{
		return strMemberId;
	}

	public void setStrSignTime(String strSignTime)
	{
		this.strSignTime=strSignTime;
	}

	public String getStrSignTime()
	{
		return strSignTime;
	}

	public void setiSignCount(int iSignCount)
	{
		this.iSignCount=iSignCount;
	}

	public int getiSignCount()
	{
		return iSignCount;
	}

	public void setiTotalSignCount(int iTotalSignCount)
	{
		this.iTotalSignCount=iTotalSignCount;
	}

	public int getiTotalSignCount()
	{
		return iTotalSignCount;
	}

}
