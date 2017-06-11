

/**
 * fun:
 * author:qidongbo
 * create time:2017年3月23日
 */
package com.ecard.entity;

/**
 * @author apple
 *首页广告轮播图片信息
 */
public class LoopAdvPicEntity {
	private String strAdvPicId="";		//主键
	private String strAdvPicName="";	//广告图片名称
	private int iAdvPicWeight=0;		//广告图片权重
	private String strAdvLinkPage="";	//页面链接地址
	private String strInsertTime="";	//录入时间
	private String strReserved="";
	
	/**
	 * create time:2017年3月23日
	 * @return the strAdvPicId
	 */
	public String getStrAdvPicId() {
		return strAdvPicId;
	}
	
	/**
	 * create time:2017年3月23日
	 * @param strAdvPicId the strAdvPicId to set
	 */
	public void setStrAdvPicId(String strAdvPicId) {
		this.strAdvPicId = strAdvPicId;
	}
	
	/**
	 * create time:2017年3月23日
	 * @return the strAdvPicName
	 */
	public String getStrAdvPicName() {
		return strAdvPicName;
	}
	
	/**
	 * create time:2017年3月23日
	 * @param strAdvPicName the strAdvPicName to set
	 */
	public void setStrAdvPicName(String strAdvPicName) {
		this.strAdvPicName = strAdvPicName;
	}
	
	/**
	 * create time:2017年3月23日
	 * @return the iAdvPicWeight
	 */
	public int getiAdvPicWeight() {
		return iAdvPicWeight;
	}
	
	/**
	 * create time:2017年3月23日
	 * @param iAdvPicWeight the iAdvPicWeight to set
	 */
	public void setiAdvPicWeight(int iAdvPicWeight) {
		this.iAdvPicWeight = iAdvPicWeight;
	}
	
	/**
	 * create time:2017年3月23日
	 * @return the strAdvLinkPage
	 */
	public String getStrAdvLinkPage() {
		return strAdvLinkPage;
	}
	
	/**
	 * create time:2017年3月23日
	 * @param strAdvLinkPage the strAdvLinkPage to set
	 */
	public void setStrAdvLinkPage(String strAdvLinkPage) {
		this.strAdvLinkPage = strAdvLinkPage;
	}
	
	/**
	 * create time:2017年3月23日
	 * @return the strInsertTime
	 */
	public String getStrInsertTime() {
		return strInsertTime;
	}
	
	/**
	 * create time:2017年3月23日
	 * @param strInsertTime the strInsertTime to set
	 */
	public void setStrInsertTime(String strInsertTime) {
		this.strInsertTime = strInsertTime;
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
