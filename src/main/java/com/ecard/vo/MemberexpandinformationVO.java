package com.ecard.vo;

/**
 * 会员拓展资料VO
 * @author zhengwei
 *
 */
public class MemberexpandinformationVO {

	/**
	 * 主键
	 */
	private String strInformationid;
	/**
	 * 拓展资料名称
	 */
	private String strInformationname;
	/**
	 * 拓展资料是否必填0：否 1：是
	 */
	private int intIsmust;
	/**
	 * 拓展资料类型0；input 1：select 2：checkbox
	 */
	private int intType;
	/**
	 * 选项JSON数据
	 */
	private String [] strOptions;
	/**
	 * 存放会员已经保存了的该拓展资料的值
	 */
	private String strValue;
	public String getStrInformationid() {
		return strInformationid;
	}
	public void setStrInformationid(String strInformationid) {
		this.strInformationid = strInformationid;
	}
	public String getStrInformationname() {
		return strInformationname;
	}
	public void setStrInformationname(String strInformationname) {
		this.strInformationname = strInformationname;
	}
	public int getIntIsmust() {
		return intIsmust;
	}
	public void setIntIsmust(int intIsmust) {
		this.intIsmust = intIsmust;
	}
	public int getIntType() {
		return intType;
	}
	public void setIntType(int intType) {
		this.intType = intType;
	}
	public String[] getStrOptions() {
		return strOptions;
	}
	public void setStrOptions(String[] strOptions) {
		this.strOptions = strOptions;
	}
	public String getStrValue() {
		return strValue;
	}
	public void setStrValue(String strValue) {
		this.strValue = strValue;
	}
} 
