

/**
 * fun:
 * author:qidongbo
 * create time:2017年3月30日
 */
package com.ecard.entity;

/**
 * @author apple
 *储值卡规则信息
 */
public class StoredticketRuleEntity {
	private String strTicketId="";	//主键
	private String strTicketName="";	//储值券名称
	private int iTicketType=0;		//储值券类型  0 售后储值 1 现金储值
	private String strValidEndTime="";	//有效期截止时间  只有售后充值才有有效期 售后充值就是赠送给用户的
	private int iIsValid=0;	//是否生效 0 禁用 1 启用
	private String strTicketRuleDesc="";	//储值券使用规则描述
	private String strReserved="";
	
	/**
	 * create time:2017年3月31日
	 * @return the strTicketId
	 */
	public String getStrTicketId() {
		return strTicketId;
	}
	
	/**
	 * create time:2017年3月31日
	 * @param strTicketId the strTicketId to set
	 */
	public void setStrTicketId(String strTicketId) {
		this.strTicketId = strTicketId;
	}
	
	/**
	 * create time:2017年3月31日
	 * @return the strTicketName
	 */
	public String getStrTicketName() {
		return strTicketName;
	}
	
	/**
	 * create time:2017年3月31日
	 * @param strTicketName the strTicketName to set
	 */
	public void setStrTicketName(String strTicketName) {
		this.strTicketName = strTicketName;
	}
	
	/**
	 * create time:2017年3月31日
	 * @return the iTicketType
	 */
	public int getiTicketType() {
		return iTicketType;
	}
	
	/**
	 * create time:2017年3月31日
	 * @param iTicketType the iTicketType to set
	 */
	public void setiTicketType(int iTicketType) {
		this.iTicketType = iTicketType;
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
	 * @return the strTicketRuleDesc
	 */
	public String getStrTicketRuleDesc() {
		return strTicketRuleDesc;
	}
	
	/**
	 * create time:2017年3月31日
	 * @param strTicketRuleDesc the strTicketRuleDesc to set
	 */
	public void setStrTicketRuleDesc(String strTicketRuleDesc) {
		this.strTicketRuleDesc = strTicketRuleDesc;
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
