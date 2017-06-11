

/**
 * fun:会员充值记录
 * author:qidongbo
 * create time:2017年3月15日
 */
package com.ecard.entity;

import java.math.BigDecimal;

/**
 * author:qidongbo
 *后台售后充值记录表
 */
public class MemberRechargeRecord {

	private String strRechargeId="";	//主键
	private String strMemberId="";		//会员ID
	private String strMemberCardNum="";	//会员卡号
	private String strMemberName="";	//用户姓名
	private BigDecimal dBalance;		//金额
	private String strEmployeeId="";
	private String strEmployeeRealName="";
	private String strEmployeeLoginName="";
	private String strInsertTime="";
	private String strReserved="";
	private int iRechargeType=0;	//充值类型 0:现金充值 1售后储值充值

	
	/**
	 * create time:2017年3月15日
	 * @return the strRechargeId
	 */
	public String getStrRechargeId() {
		return strRechargeId;
	}
	
	/**
	 * create time:2017年3月15日
	 * @param strRechargeId the strRechargeId to set
	 */
	public void setStrRechargeId(String strRechargeId) {
		this.strRechargeId = strRechargeId;
	}
	
	/**
	 * create time:2017年3月15日
	 * @return the strMemberId
	 */
	public String getStrMemberId() {
		return strMemberId;
	}
	
	/**
	 * create time:2017年3月15日
	 * @param strMemberId the strMemberId to set
	 */
	public void setStrMemberId(String strMemberId) {
		this.strMemberId = strMemberId;
	}
	
	/**
	 * create time:2017年3月15日
	 * @return the strMemberCardNum
	 */
	public String getStrMemberCardNum() {
		return strMemberCardNum;
	}
	
	/**
	 * create time:2017年3月15日
	 * @param strMemberCardNum the strMemberCardNum to set
	 */
	public void setStrMemberCardNum(String strMemberCardNum) {
		this.strMemberCardNum = strMemberCardNum;
	}
	
	/**
	 * create time:2017年3月15日
	 * @return the strMemberName
	 */
	public String getStrMemberName() {
		return strMemberName;
	}
	
	/**
	 * create time:2017年3月15日
	 * @param strMemberName the strMemberName to set
	 */
	public void setStrMemberName(String strMemberName) {
		this.strMemberName = strMemberName;
	}
	
	/**
	 * create time:2017年3月15日
	 * @return the dBalance
	 */
	public BigDecimal getdBalance() {
		return dBalance;
	}
	
	/**
	 * create time:2017年3月15日
	 * @param dBalance the dBalance to set
	 */
	public void setdBalance(BigDecimal dBalance) {
		this.dBalance = dBalance;
	}
	
	/**
	 * create time:2017年3月15日
	 * @return the strEmployeeId
	 */
	public String getStrEmployeeId() {
		return strEmployeeId;
	}
	
	/**
	 * create time:2017年3月15日
	 * @param strEmployeeId the strEmployeeId to set
	 */
	public void setStrEmployeeId(String strEmployeeId) {
		this.strEmployeeId = strEmployeeId;
	}
	
	/**
	 * create time:2017年3月15日
	 * @return the strEmployeeRealName
	 */
	public String getStrEmployeeRealName() {
		return strEmployeeRealName;
	}
	
	/**
	 * create time:2017年3月15日
	 * @param strEmployeeRealName the strEmployeeRealName to set
	 */
	public void setStrEmployeeRealName(String strEmployeeRealName) {
		this.strEmployeeRealName = strEmployeeRealName;
	}
	
	/**
	 * create time:2017年3月15日
	 * @return the strEmployeeLoginName
	 */
	public String getStrEmployeeLoginName() {
		return strEmployeeLoginName;
	}
	
	/**
	 * create time:2017年3月15日
	 * @param strEmployeeLoginName the strEmployeeLoginName to set
	 */
	public void setStrEmployeeLoginName(String strEmployeeLoginName) {
		this.strEmployeeLoginName = strEmployeeLoginName;
	}
	
	/**
	 * create time:2017年3月15日
	 * @return the strInsertTime
	 */
	public String getStrInsertTime() {
		return strInsertTime;
	}
	
	/**
	 * create time:2017年3月15日
	 * @param strInsertTime the strInsertTime to set
	 */
	public void setStrInsertTime(String strInsertTime) {
		this.strInsertTime = strInsertTime;
	}
	
	/**
	 * create time:2017年3月15日
	 * @return the strReserved
	 */
	public String getStrReserved() {
		return strReserved;
	}
	
	/**
	 * create time:2017年3月15日
	 * @param strReserved the strReserved to set
	 */
	public void setStrReserved(String strReserved) {
		this.strReserved = strReserved;
	}

	
	/**
	 * create time:2017年3月15日
	 * @return the iRechargeType
	 */
	public int getiRechargeType() {
		return iRechargeType;
	}

	
	/**
	 * create time:2017年3月15日
	 * @param iRechargeType the iRechargeType to set
	 */
	public void setiRechargeType(int iRechargeType) {
		this.iRechargeType = iRechargeType;
	}
	
	
	

}
