

/**
 * fun:
 * author:qidongbo
 * create time:2017年3月23日
 */
package com.ecard.entity;

/**
 * @author apple
 *
 */
public class IntegralclearRuleEntity {
	private String strValidBeginTime="";
	private String strValidEndTime="";
	private int iIsValid=0;
	private String strReserved="";
	
	
	
	
	/**
	 * create time:2017年3月23日
	 * @return the iIsValid
	 */
	public int getiIsValid() {
		return iIsValid;
	}

	
	/**
	 * create time:2017年3月23日
	 * @param iIsValid the iIsValid to set
	 */
	public void setiIsValid(int iIsValid) {
		this.iIsValid = iIsValid;
	}

	/**
	 * create time:2017年3月23日
	 * @return the strValidBeginTime
	 */
	public String getStrValidBeginTime() {
		return strValidBeginTime;
	}
	
	/**
	 * create time:2017年3月23日
	 * @param strValidBeginTime the strValidBeginTime to set
	 */
	public void setStrValidBeginTime(String strValidBeginTime) {
		this.strValidBeginTime = strValidBeginTime;
	}
	
	/**
	 * create time:2017年3月23日
	 * @return the strValidEndTime
	 */
	public String getStrValidEndTime() {
		return strValidEndTime;
	}
	
	/**
	 * create time:2017年3月23日
	 * @param strValidEndTime the strValidEndTime to set
	 */
	public void setStrValidEndTime(String strValidEndTime) {
		this.strValidEndTime = strValidEndTime;
	}
	
	/**
	 * create time:2017年3月23日
	 * @return the strReserved
	 */
	public String getStrReserved() {
		return strReserved;
	}
	
	/**
	 * create time:2017年3月23日
	 * @param strReserved the strReserved to set
	 */
	public void setStrReserved(String strReserved) {
		this.strReserved = strReserved;
	}
	
	
}
