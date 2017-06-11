

/**
 * fun:
 * author:qidongbo
 * create time:2017年3月20日
 */
package com.ecard.entity;

/**
 * @author kinglong
 *
 */
//商品分类
public class GoodsTypeConfigEntity {
	private String strGoodsTypeId="";	//商品类别 主键
	private String strGoodsTypeName="";	//商品类别名称
	private String strGoodsTypeDesc="";	//商品类别描述
	private String strEmployeeId="";
	private String strEmployeeName="";
	private String strEmployeeLoginName="";
	private String strReserved="";
	
	
	
	/**
	 * create time:2017年3月27日
	 * @return the strEmployeeId
	 */
	public String getStrEmployeeId() {
		return strEmployeeId;
	}

	
	/**
	 * create time:2017年3月27日
	 * @param strEmployeeId the strEmployeeId to set
	 */
	public void setStrEmployeeId(String strEmployeeId) {
		this.strEmployeeId = strEmployeeId;
	}

	
	/**
	 * create time:2017年3月27日
	 * @return the strEmployeeName
	 */
	public String getStrEmployeeName() {
		return strEmployeeName;
	}

	
	/**
	 * create time:2017年3月27日
	 * @param strEmployeeName the strEmployeeName to set
	 */
	public void setStrEmployeeName(String strEmployeeName) {
		this.strEmployeeName = strEmployeeName;
	}

	
	/**
	 * create time:2017年3月27日
	 * @return the strEmployeeLoginName
	 */
	public String getStrEmployeeLoginName() {
		return strEmployeeLoginName;
	}

	
	/**
	 * create time:2017年3月27日
	 * @param strEmployeeLoginName the strEmployeeLoginName to set
	 */
	public void setStrEmployeeLoginName(String strEmployeeLoginName) {
		this.strEmployeeLoginName = strEmployeeLoginName;
	}

	/**
	 * create time:2017年3月20日
	 * @return the strGoodsTypeId
	 */
	public String getStrGoodsTypeId() {
		return strGoodsTypeId;
	}
	
	/**
	 * create time:2017年3月20日
	 * @param strGoodsTypeId the strGoodsTypeId to set
	 */
	public void setStrGoodsTypeId(String strGoodsTypeId) {
		this.strGoodsTypeId = strGoodsTypeId;
	}
	
	/**
	 * create time:2017年3月20日
	 * @return the strGoodsTypeName
	 */
	public String getStrGoodsTypeName() {
		return strGoodsTypeName;
	}
	
	/**
	 * create time:2017年3月20日
	 * @param strGoodsTypeName the strGoodsTypeName to set
	 */
	public void setStrGoodsTypeName(String strGoodsTypeName) {
		this.strGoodsTypeName = strGoodsTypeName;
	}
	
	/**
	 * create time:2017年3月20日
	 * @return the strGoodsTypeDesc
	 */
	public String getStrGoodsTypeDesc() {
		return strGoodsTypeDesc;
	}
	
	/**
	 * create time:2017年3月20日
	 * @param strGoodsTypeDesc the strGoodsTypeDesc to set
	 */
	public void setStrGoodsTypeDesc(String strGoodsTypeDesc) {
		this.strGoodsTypeDesc = strGoodsTypeDesc;
	}
	
	/**
	 * create time:2017年3月20日
	 * @return the strReserved
	 */
	public String getStrReserved() {
		return strReserved;
	}
	
	/**
	 * create time:2017年3月20日
	 * @param strReserved the strReserved to set
	 */
	public void setStrReserved(String strReserved) {
		this.strReserved = strReserved;
	}

	
}
