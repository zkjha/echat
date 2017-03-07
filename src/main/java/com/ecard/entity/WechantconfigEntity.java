package com.ecard.entity;

/**
 * 微信配置信息实体
 * @author Administrator
 *
 */
public class WechantconfigEntity {

	/**
	 * 数据库主键
	 */
	private String strConfigid;	
	/**
	 * APPID
	 */
	private String  strAppid;
	/**
	 * APPNAME
	 */
	private String  strAppname;
	/**
	 * URL
	 */
	private String  strUrl;
	/**
	 * TOKEN
	 */
	private String  strToken;
	/**
	 * 状态1：已授权 0：未授权
	 */
	private int  intAuthorizationstatus;
	/**
	 * key
	 */
	private String  strEncodingaeskey;
	/**
	 * 录入时间
	 */
	private String strInserttime;
	public String getStrConfigid() {
		return strConfigid;
	}
	public void setStrConfigid(String strConfigid) {
		this.strConfigid = strConfigid;
	}
	public String getStrAppid() {
		return strAppid;
	}
	public void setStrAppid(String strAppid) {
		this.strAppid = strAppid;
	}
	public String getStrAppname() {
		return strAppname;
	}
	public void setStrAppname(String strAppname) {
		this.strAppname = strAppname;
	}
	public String getStrUrl() {
		return strUrl;
	}
	public void setStrUrl(String strUrl) {
		this.strUrl = strUrl;
	}
	public String getStrToken() {
		return strToken;
	}
	public void setStrToken(String strToken) {
		this.strToken = strToken;
	}
	public int getIntAuthorizationstatus() {
		return intAuthorizationstatus;
	}
	public void setIntAuthorizationstatus(int intAuthorizationstatus) {
		this.intAuthorizationstatus = intAuthorizationstatus;
	}
	public String getStrEncodingaeskey() {
		return strEncodingaeskey;
	}
	public void setStrEncodingaeskey(String strEncodingaeskey) {
		this.strEncodingaeskey = strEncodingaeskey;
	}
	public String getStrInserttime() {
		return strInserttime;
	}
	public void setStrInserttime(String strInserttime) {
		this.strInserttime = strInserttime;
	}
} 
