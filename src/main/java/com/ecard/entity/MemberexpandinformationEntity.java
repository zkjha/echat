package com.ecard.entity;

/**
 * 会员拓展资料信息实体
 * @author dinghongxing
 *
 */
public class MemberexpandinformationEntity {

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
	private String strOptions;
	/**
	 * 新增时间
	 */
	private String strInserttime;
	/**
	 * 修改时间
	 */
	private String strUpdatetime;
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
	public String getStrOptions() {
		return strOptions;
	}
	public void setStrOptions(String strOptions) {
		this.strOptions = strOptions;
	}
	public String getStrInserttime() {
		return strInserttime;
	}
	public void setStrInserttime(String strInserttime) {
		this.strInserttime = strInserttime;
	}
	public String getStrUpdatetime() {
		return strUpdatetime;
	}
	public void setStrUpdatetime(String strUpdatetime) {
		this.strUpdatetime = strUpdatetime;
	}
	
} 
