

/**
 * fun:
 * author:qidongbo
 * create time:2017年3月19日
 */
package com.ecard.entity;

/**
 * author qidongbo
 *计量单位表
 */
public class MeasurementUnitEntity {
	private String strUnitId="";	//主键
	private String strUnitName="";	//计量单位名称
	private String strUnitDesc="";	//计量单位描述
	private String strReserved="";
	
	/**
	 * create time:2017年3月19日
	 * @return the strUnitId
	 */
	public String getStrUnitId() {
		return strUnitId;
	}
	
	/**
	 * create time:2017年3月19日
	 * @param strUnitId the strUnitId to set
	 */
	public void setStrUnitId(String strUnitId) {
		this.strUnitId = strUnitId;
	}
	
	/**
	 * create time:2017年3月19日
	 * @return the strUnitName
	 */
	public String getStrUnitName() {
		return strUnitName;
	}
	
	/**
	 * create time:2017年3月19日
	 * @param strUnitName the strUnitName to set
	 */
	public void setStrUnitName(String strUnitName) {
		this.strUnitName = strUnitName;
	}
	
	/**
	 * create time:2017年3月19日
	 * @return the strUnitDesc
	 */
	public String getStrUnitDesc() {
		return strUnitDesc;
	}
	
	/**
	 * create time:2017年3月19日
	 * @param strUnitDesc the strUnitDesc to set
	 */
	public void setStrUnitDesc(String strUnitDesc) {
		this.strUnitDesc = strUnitDesc;
	}
	
	/**
	 * create time:2017年3月19日
	 * @return the strReserved
	 */
	public String getStrReserved() {
		return strReserved;
	}
	
	/**
	 * create time:2017年3月19日
	 * @param strReserved the strReserved to set
	 */
	public void setStrReserved(String strReserved) {
		this.strReserved = strReserved;
	}

	
}
