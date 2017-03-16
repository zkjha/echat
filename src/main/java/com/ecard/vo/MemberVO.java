package com.ecard.vo;

import java.math.BigDecimal;

public class MemberVO {
	
	/**
	/**
	 * 主键
	 */
	private String strMemberid;
	/**
	 * 姓名
	 */
	private String strRealname;
	/**
	 * 手机号
	 */
	private String strMobile;
	/**
	 * 会员卡号
	 */
	private String strMembercardnum;
	/**
	 * 会员级别
	 */
	private String strLevelsname;
	/**
	 * 会员卡状态0：禁用 1：激活
	 */
	private int intStatus;
	/**
	 * 会员卡积分
	 */
	private int intIntegral;
	/**
	 * 优惠券数量
	 */
	private int intVouchers;
	/**
	 * 会员卡余额
	 */
	private BigDecimal dBalance;
	/**
	 * 售后储值余额
	 */
	private BigDecimal dAfterstoredbalance;
	/**
	 * 录入时间
	 */
	private String strInserttime;
	public String getStrMemberid() {
		return strMemberid;
	}
	public void setStrMemberid(String strMemberid) {
		this.strMemberid = strMemberid;
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
	public String getStrMembercardnum() {
		return strMembercardnum;
	}
	public void setStrMembercardnum(String strMembercardnum) {
		this.strMembercardnum = strMembercardnum;
	}
	public String getStrLevelsname() {
		return strLevelsname;
	}
	public void setStrLevelsname(String strLevelsname) {
		this.strLevelsname = strLevelsname;
	}
	public int getIntStatus() {
		return intStatus;
	}
	public void setIntStatus(int intStatus) {
		this.intStatus = intStatus;
	}
	public int getIntIntegral() {
		return intIntegral;
	}
	public void setIntIntegral(int intIntegral) {
		this.intIntegral = intIntegral;
	}
	public int getIntVouchers() {
		return intVouchers;
	}
	public void setIntVouchers(int intVouchers) {
		this.intVouchers = intVouchers;
	}
	public BigDecimal getdBalance() {
		return dBalance;
	}
	public void setdBalance(BigDecimal dBalance) {
		this.dBalance = dBalance;
	}
	public BigDecimal getdAfterstoredbalance() {
		return dAfterstoredbalance;
	}
	public void setdAfterstoredbalance(BigDecimal dAfterstoredbalance) {
		this.dAfterstoredbalance = dAfterstoredbalance;
	}
	public String getStrInserttime() {
		return strInserttime;
	}
	public void setStrInserttime(String strInserttime) {
		this.strInserttime = strInserttime;
	}
	
} 
