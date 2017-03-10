package com.ecard.entity;

/**
 * 会员拓展资料信息实体
 * @author Administrator
 *
 */
public class MemberexpandattributeEntity {

	/**
	 * 主键
	 */
	private String strAttributeid;
	/**
	 * 会员ID
	 */
	private String strMemberid;
	/**
	 * 拓展资料ID
	 */
	private String strInformationid;
	/**
	 * 拓展资料属性值
	 */
	private String strAttributevalue;
	/**
	 * 录入时间
	 */
	private String strInserttime;
	public String getStrAttributeid() {
		return strAttributeid;
	}
	public void setStrAttributeid(String strAttributeid) {
		this.strAttributeid = strAttributeid;
	}
	public String getStrMemberid() {
		return strMemberid;
	}
	public void setStrMemberid(String strMemberid) {
		this.strMemberid = strMemberid;
	}
	public String getStrInformationid() {
		return strInformationid;
	}
	public void setStrInformationid(String strInformationid) {
		this.strInformationid = strInformationid;
	}
	public String getStrAttributevalue() {
		return strAttributevalue;
	}
	public void setStrAttributevalue(String strAttributevalue) {
		this.strAttributevalue = strAttributevalue;
	}
	public String getStrInserttime() {
		return strInserttime;
	}
	public void setStrInserttime(String strInserttime) {
		this.strInserttime = strInserttime;
	}
	
	
} 
