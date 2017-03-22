

/**
 * fun:
 * author:qidongbo
 * create time:2017年3月21日
 */
package com.ecard.entity;

import java.math.BigDecimal;

/**
 * author kinglong
 *
 */
public class GoodsInfoEntity {
	private String strGoodsId="";
	private String strGoodsName="";
	
	private String strGoodsTypeId="";
	private String strGoodsTypeName="";
	
	private BigDecimal dEnterPrice;
	private BigDecimal dSalePrice;
	private int iStock=0;
	private String strEmployeeId="";
	private String strEmployeeName="";
	private String strEmployeeLoginName="";
	private String strInsertTime="";
	private String strUpdateTime="";
	

	/**
	 * create time:2017年3月22日
	 * @return the strGoodsTypeId
	 */
	public String getStrGoodsTypeId() {
		return strGoodsTypeId;
	}

	
	/**
	 * create time:2017年3月22日
	 * @param strGoodsTypeId the strGoodsTypeId to set
	 */
	public void setStrGoodsTypeId(String strGoodsTypeId) {
		this.strGoodsTypeId = strGoodsTypeId;
	}

	
	/**
	 * create time:2017年3月22日
	 * @return the strGoodsTypeName
	 */
	public String getStrGoodsTypeName() {
		return strGoodsTypeName;
	}

	
	/**
	 * create time:2017年3月22日
	 * @param strGoodsTypeName the strGoodsTypeName to set
	 */
	public void setStrGoodsTypeName(String strGoodsTypeName) {
		this.strGoodsTypeName = strGoodsTypeName;
	}

	/**
	 * create time:2017年3月21日
	 * @return the strGoodsId
	 */
	public String getStrGoodsId() {
		return strGoodsId;
	}
	
	/**
	 * create time:2017年3月21日
	 * @param strGoodsId the strGoodsId to set
	 */
	public void setStrGoodsId(String strGoodsId) {
		this.strGoodsId = strGoodsId;
	}
	
	/**
	 * create time:2017年3月21日
	 * @return the strDoodsName
	 */
	public String getStrGoodsName() {
		return strGoodsName;
	}
	
	/**
	 * create time:2017年3月21日
	 * @param strDoodsName the strDoodsName to set
	 */
	public void setStrGoodsName(String strGoodsName) {
		this.strGoodsName = strGoodsName;
	}
	
	/**
	 * create time:2017年3月21日
	 * @return the dEnterPrice
	 */
	public BigDecimal getdEnterPrice() {
		return dEnterPrice;
	}
	
	/**
	 * create time:2017年3月21日
	 * @param dEnterPrice the dEnterPrice to set
	 */
	public void setdEnterPrice(BigDecimal dEnterPrice) {
		this.dEnterPrice = dEnterPrice;
	}
	
	/**
	 * create time:2017年3月21日
	 * @return the dSalePrice
	 */
	public BigDecimal getdSalePrice() {
		return dSalePrice;
	}
	
	/**
	 * create time:2017年3月21日
	 * @param dSalePrice the dSalePrice to set
	 */
	public void setdSalePrice(BigDecimal dSalePrice) {
		this.dSalePrice = dSalePrice;
	}
	
	/**
	 * create time:2017年3月21日
	 * @return the iStock
	 */
	public int getiStock() {
		return iStock;
	}
	
	/**
	 * create time:2017年3月21日
	 * @param iStock the iStock to set
	 */
	public void setiStock(int iStock) {
		this.iStock = iStock;
	}
	
	/**
	 * create time:2017年3月21日
	 * @return the strEmployeeId
	 */
	public String getStrEmployeeId() {
		return strEmployeeId;
	}
	
	/**
	 * create time:2017年3月21日
	 * @param strEmployeeId the strEmployeeId to set
	 */
	public void setStrEmployeeId(String strEmployeeId) {
		this.strEmployeeId = strEmployeeId;
	}
	
	/**
	 * create time:2017年3月21日
	 * @return the strEmployeeName
	 */
	public String getStrEmployeeName() {
		return strEmployeeName;
	}
	
	/**
	 * create time:2017年3月21日
	 * @param strEmployeeName the strEmployeeName to set
	 */
	public void setStrEmployeeName(String strEmployeeName) {
		this.strEmployeeName = strEmployeeName;
	}
	
	/**
	 * create time:2017年3月21日
	 * @return the strEmployeeLoginName
	 */
	public String getStrEmployeeLoginName() {
		return strEmployeeLoginName;
	}
	
	/**
	 * create time:2017年3月21日
	 * @param strEmployeeLoginName the strEmployeeLoginName to set
	 */
	public void setStrEmployeeLoginName(String strEmployeeLoginName) {
		this.strEmployeeLoginName = strEmployeeLoginName;
	}
	
	/**
	 * create time:2017年3月21日
	 * @return the strInsertTime
	 */
	public String getStrInsertTime() {
		return strInsertTime;
	}
	
	/**
	 * create time:2017年3月21日
	 * @param strInsertTime the strInsertTime to set
	 */
	public void setStrInsertTime(String strInsertTime) {
		this.strInsertTime = strInsertTime;
	}
	
	/**
	 * create time:2017年3月21日
	 * @return the strUpdateTime
	 */
	public String getStrUpdateTime() {
		return strUpdateTime;
	}
	
	/**
	 * create time:2017年3月21日
	 * @param strUpdateTime the strUpdateTime to set
	 */
	public void setStrUpdateTime(String strUpdateTime) {
		this.strUpdateTime = strUpdateTime;
	}
	
	
	
}
