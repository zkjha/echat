

/**
 * fun:
 * author:qidongbo
 * create time:2017年3月31日
 */
package com.ecard.entity;

/**
 * author kinglong
 * 抵用券信息
 */
public class VoucherticketRuleEntity {
	private String strVoucherTicketId="";	//主键
	private String strVoucherTicketName="";	//储值券名称
	private String strValidEndTime="";	//有效期截止时间
	private int iIsValid=0;		//是否生效 0 禁用 1 启用
	private int iCanUseCount=0;	//可使用次数
	private String strUseCountDesc="";	//使用次数描述
	private String strRuleDesc="";	//储值券使用规则描述
	private String strReserved="";
	
	/**
	 * create time:2017年3月31日
	 * @return the strVoucherTicketId
	 */
	public String getStrVoucherTicketId() {
		return strVoucherTicketId;
	}
	
	/**
	 * create time:2017年3月31日
	 * @param strVoucherTicketId the strVoucherTicketId to set
	 */
	public void setStrVoucherTicketId(String strVoucherTicketId) {
		this.strVoucherTicketId = strVoucherTicketId;
	}
	
	/**
	 * create time:2017年3月31日
	 * @return the strVoucherTicketName
	 */
	public String getStrVoucherTicketName() {
		return strVoucherTicketName;
	}
	
	/**
	 * create time:2017年3月31日
	 * @param strVoucherTicketName the strVoucherTicketName to set
	 */
	public void setStrVoucherTicketName(String strVoucherTicketName) {
		this.strVoucherTicketName = strVoucherTicketName;
	}
	
	/**
	 * create time:2017年3月31日
	 * @return the strValidEndTime
	 */
	public String getStrValidEndTime() {
		return strValidEndTime;
	}
	
	/**
	 * create time:2017年3月31日
	 * @param strValidEndTime the strValidEndTime to set
	 */
	public void setStrValidEndTime(String strValidEndTime) {
		this.strValidEndTime = strValidEndTime;
	}
	
	/**
	 * create time:2017年3月31日
	 * @return the iIsValid
	 */
	public int getiIsValid() {
		return iIsValid;
	}
	
	/**
	 * create time:2017年3月31日
	 * @param iIsValid the iIsValid to set
	 */
	public void setiIsValid(int iIsValid) {
		this.iIsValid = iIsValid;
	}
	
	/**
	 * create time:2017年3月31日
	 * @return the iCanUseCount
	 */
	public int getiCanUseCount() {
		return iCanUseCount;
	}
	
	/**
	 * create time:2017年3月31日
	 * @param iCanUseCount the iCanUseCount to set
	 */
	public void setiCanUseCount(int iCanUseCount) {
		this.iCanUseCount = iCanUseCount;
	}
	
	/**
	 * create time:2017年3月31日
	 * @return the strUseCountDesc
	 */
	public String getStrUseCountDesc() {
		return strUseCountDesc;
	}
	
	/**
	 * create time:2017年3月31日
	 * @param strUseCountDesc the strUseCountDesc to set
	 */
	public void setStrUseCountDesc(String strUseCountDesc) {
		this.strUseCountDesc = strUseCountDesc;
	}
	
	/**
	 * create time:2017年3月31日
	 * @return the strRuleDesc
	 */
	public String getStrRuleDesc() {
		return strRuleDesc;
	}
	
	/**
	 * create time:2017年3月31日
	 * @param strRuleDesc the strRuleDesc to set
	 */
	public void setStrRuleDesc(String strRuleDesc) {
		this.strRuleDesc = strRuleDesc;
	}
	
	/**
	 * create time:2017年3月31日
	 * @return the strReserved
	 */
	public String getStrReserved() {
		return strReserved;
	}
	
	/**
	 * create time:2017年3月31日
	 * @param strReserved the strReserved to set
	 */
	public void setStrReserved(String strReserved) {
		this.strReserved = strReserved;
	}

	
}
