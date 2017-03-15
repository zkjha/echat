package com.ecard.entity;


/**
 * 积分修改参数
 * author:qidongbo
 *
 */
public class IntegralModEntity {
	
	// 会员id
	private String strMemberId;
	// 积分变更值
	private int  iIntegralNum;
	//积分变更类型 increase reduce
	private String strType;
	public String getStrMemberId() {
		return strMemberId;
	}
	public void setStrMemberId(String strMemberId) {
		this.strMemberId = strMemberId;
	}
	public int getiIntegralNum() {
		return iIntegralNum;
	}
	public void setiIntegralNum(int iIntegralNum) {
		this.iIntegralNum = iIntegralNum;
	}
	public String getStrType() {
		return strType;
	}
	public void setStrType(String strType) {
		this.strType = strType;
	}

}
