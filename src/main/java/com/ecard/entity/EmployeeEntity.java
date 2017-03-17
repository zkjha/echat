package com.ecard.entity;

import java.io.Serializable;

/**
 * 员工信息实体
 * @author dinghongxing
 *
 */
public class EmployeeEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 主键	
	 */
	private String strEmployeeid;
	/**
	 * 登录管理后台账户名
	 */
	private String strLoginname;
	/**
	 * 登录管理后台密码
	 */
	private String strPassword;
	/**
	 * 员工头像
	 */
	private String strHeadportrait;
	/**
	 * 员工真实姓名
	 */
	private String strRealname;
	/**
	 * 员工手机号码
	 */
	private String strMobile;//员工手机号码
	/**
	 * 员工状态0：禁用 1：激活
	 */
	private int intStatus;
	/**
	 * 职务ID
	 */
	private String strDutyid;
	/**
	 * 商家ID
	 */
	private String strMerchantid;
	/**
	 * 录入时间
	 */
	private String strInserttime;
	/**
	 * 修改时间
	 */
	private String strUpdatetime;
	public String getStrEmployeeid() {
		return strEmployeeid;
	}
	public void setStrEmployeeid(String strEmployeeid) {
		this.strEmployeeid = strEmployeeid;
	}
	public String getStrLoginname() {
		return strLoginname;
	}
	public void setStrLoginname(String strLoginname) {
		this.strLoginname = strLoginname;
	}
	public String getStrPassword() {
		return strPassword;
	}
	public void setStrPassword(String strPassword) {
		this.strPassword = strPassword;
	}
	public String getStrHeadportrait() {
		return strHeadportrait;
	}
	public void setStrHeadportrait(String strHeadportrait) {
		this.strHeadportrait = strHeadportrait;
	}
	public String getStrRealname() {
		return strRealname;
	}
	public void setStrRealname(String strRealname) {
		this.strRealname = strRealname;
	}
	public String getStrMobile() {
		return strMobile;
	}
	public void setStrMobile(String strMobile) {
		this.strMobile = strMobile;
	}
	public int getIntStatus() {
		return intStatus;
	}
	public void setIntStatus(int intStatus) {
		this.intStatus = intStatus;
	}
	public String getStrDutyid() {
		return strDutyid;
	}
	public void setStrDutyid(String strDutyid) {
		this.strDutyid = strDutyid;
	}
	public String getStrMerchantid() {
		return strMerchantid;
	}
	public void setStrMerchantid(String strMerchantid) {
		this.strMerchantid = strMerchantid;
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
