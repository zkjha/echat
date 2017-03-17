package com.ecard.entity;

/**
 * 前端注册页面资料信息实体
 * @author dinghongxing
 *
 */
public class FrontInformationEntity {

	/**
	 * 主键
	 */
	private String strInformationid;
	/**
	 * 职务名称
	 */
	private String strContent;
	/**
	 * 录入时间
	 */
	private String strInserttime;
	public String getStrInformationid() {
		return strInformationid;
	}
	public void setStrInformationid(String strInformationid) {
		this.strInformationid = strInformationid;
	}
	public String getStrContent() {
		return strContent;
	}
	public void setStrContent(String strContent) {
		this.strContent = strContent;
	}
	public String getStrInserttime() {
		return strInserttime;
	}
	public void setStrInserttime(String strInserttime) {
		this.strInserttime = strInserttime;
	}
} 
