

/**
 * fun:
 * author:qidongbo
 * create time:2017年3月31日
 */
package com.ecard.entity;

/**
 * @author kinglong
 * 抵用券使用情况记录
 */
public class VoucherticketUseInfoEntity {
	private String strVoucherUseInfoId="";	//主键
	private String strVoucherTicketId="";	//关联抵用券ID
	private String strVoucherTicketName="";	//储值券名称
	private String strValidEndTime="";	//有效期截止时间
	private String strMemberId="";	//领用会员ID
	private String strMemberName="";	//领用会员姓名
	private int iStat=0;	//抵用券状态 1未使用 2已使用 3已过期
	private int iCanUseCount=0;	//可使用次数
	private int iUsedCount=0;	//已经使用次数
	private int iIsValid=0;	//是否生效 0 禁用 1 启用
	private String strUseCountDesc="";//使用次数描述
	private String strRuleDesc="";//储值券使用规则描述
	private String strReserved="";
	
	/**
	 * create time:2017年4月1日
	 * @return the strVoucherUseInfoId
	 */
	public String getStrVoucherUseInfoId() {
		return strVoucherUseInfoId;
	}
	
	/**
	 * create time:2017年4月1日
	 * @param strVoucherUseInfoId the strVoucherUseInfoId to set
	 */
	public void setStrVoucherUseInfoId(String strVoucherUseInfoId) {
		this.strVoucherUseInfoId = strVoucherUseInfoId;
	}
	
	/**
	 * create time:2017年4月1日
	 * @return the strVoucherTicketId
	 */
	public String getStrVoucherTicketId() {
		return strVoucherTicketId;
	}
	
	/**
	 * create time:2017年4月1日
	 * @param strVoucherTicketId the strVoucherTicketId to set
	 */
	public void setStrVoucherTicketId(String strVoucherTicketId) {
		this.strVoucherTicketId = strVoucherTicketId;
	}
	
	/**
	 * create time:2017年4月1日
	 * @return the strVoucherTicketName
	 */
	public String getStrVoucherTicketName() {
		return strVoucherTicketName;
	}
	
	/**
	 * create time:2017年4月1日
	 * @param strVoucherTicketName the strVoucherTicketName to set
	 */
	public void setStrVoucherTicketName(String strVoucherTicketName) {
		this.strVoucherTicketName = strVoucherTicketName;
	}
	
	/**
	 * create time:2017年4月1日
	 * @return the strValidEndTime
	 */
	public String getStrValidEndTime() {
		return strValidEndTime;
	}
	
	/**
	 * create time:2017年4月1日
	 * @param strValidEndTime the strValidEndTime to set
	 */
	public void setStrValidEndTime(String strValidEndTime) {
		this.strValidEndTime = strValidEndTime;
	}
	
	/**
	 * create time:2017年4月1日
	 * @return the strMemberId
	 */
	public String getStrMemberId() {
		return strMemberId;
	}
	
	/**
	 * create time:2017年4月1日
	 * @param strMemberId the strMemberId to set
	 */
	public void setStrMemberId(String strMemberId) {
		this.strMemberId = strMemberId;
	}
	
	/**
	 * create time:2017年4月1日
	 * @return the strMemberName
	 */
	public String getStrMemberName() {
		return strMemberName;
	}
	
	/**
	 * create time:2017年4月1日
	 * @param strMemberName the strMemberName to set
	 */
	public void setStrMemberName(String strMemberName) {
		this.strMemberName = strMemberName;
	}
	
	/**
	 * create time:2017年4月1日
	 * @return the iStat
	 */
	public int getiStat() {
		return iStat;
	}
	
	/**
	 * create time:2017年4月1日
	 * @param iStat the iStat to set
	 */
	public void setiStat(int iStat) {
		this.iStat = iStat;
	}
	
	/**
	 * create time:2017年4月1日
	 * @return the iCanUseCount
	 */
	public int getiCanUseCount() {
		return iCanUseCount;
	}
	
	/**
	 * create time:2017年4月1日
	 * @param iCanUseCount the iCanUseCount to set
	 */
	public void setiCanUseCount(int iCanUseCount) {
		this.iCanUseCount = iCanUseCount;
	}
	
	/**
	 * create time:2017年4月1日
	 * @return the iUsedCount
	 */
	public int getiUsedCount() {
		return iUsedCount;
	}
	
	/**
	 * create time:2017年4月1日
	 * @param iUsedCount the iUsedCount to set
	 */
	public void setiUsedCount(int iUsedCount) {
		this.iUsedCount = iUsedCount;
	}
	
	/**
	 * create time:2017年4月1日
	 * @return the iIsValid
	 */
	public int getiIsValid() {
		return iIsValid;
	}
	
	/**
	 * create time:2017年4月1日
	 * @param iIsValid the iIsValid to set
	 */
	public void setiIsValid(int iIsValid) {
		this.iIsValid = iIsValid;
	}
	
	/**
	 * create time:2017年4月1日
	 * @return the strUseCountDesc
	 */
	public String getStrUseCountDesc() {
		return strUseCountDesc;
	}
	
	/**
	 * create time:2017年4月1日
	 * @param strUseCountDesc the strUseCountDesc to set
	 */
	public void setStrUseCountDesc(String strUseCountDesc) {
		this.strUseCountDesc = strUseCountDesc;
	}
	
	/**
	 * create time:2017年4月1日
	 * @return the strRuleDesc
	 */
	public String getStrRuleDesc() {
		return strRuleDesc;
	}
	
	/**
	 * create time:2017年4月1日
	 * @param strRuleDesc the strRuleDesc to set
	 */
	public void setStrRuleDesc(String strRuleDesc) {
		this.strRuleDesc = strRuleDesc;
	}
	
	/**
	 * create time:2017年4月1日
	 * @return the strReserved
	 */
	public String getStrReserved() {
		return strReserved;
	}
	
	/**
	 * create time:2017年4月1日
	 * @param strReserved the strReserved to set
	 */
	public void setStrReserved(String strReserved) {
		this.strReserved = strReserved;
	}
	
	
}
