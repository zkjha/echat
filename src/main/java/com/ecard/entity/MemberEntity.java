package com.ecard.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 会员信息实体
 * @author Administrator
 *
 */
public class MemberEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
	 * 身份证号码
	 */
	private String strIdcard;
	/**
	 * 手机号
	 */
	private String strMobile;
	/**
	 * 年龄
	 */
	private int intAge;
	/**
	 * 会员卡号
	 */
	private String strMembercardnum;
	/**
	 * 会员级别
	 */
	private String strLevelsid;
	/**
	 * 性别0：男 1：女
	 */
	private int intSex;
	/**
	 * 会员卡状态0：禁用 1：激活
	 */
	private int intStatus;
	/**
	 * 会员卡积分
	 */
	private int intIntegral;
	/**
	 * 会员卡余额
	 */
	private BigDecimal dBalance;
	/**
	 * 录入时间
	 */
	private String strInserttime;
	/**
	 * 修改时间
	 */
	private String strUpdatetime;
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
	public String getStrIdcard() {
		return strIdcard;
	}
	public void setStrIdcard(String strIdcard) {
		this.strIdcard = strIdcard;
	}
	public String getStrMobile() {
		return strMobile;
	}
	public void setStrMobile(String strMobile) {
		this.strMobile = strMobile;
	}
	public int getIntAge() {
		return intAge;
	}
	public void setIntAge(int intAge) {
		this.intAge = intAge;
	}
	public String getStrMembercardnum() {
		return strMembercardnum;
	}
	public void setStrMembercardnum(String strMembercardnum) {
		this.strMembercardnum = strMembercardnum;
	}
	public String getStrLevelsid() {
		return strLevelsid;
	}
	public void setStrLevelsid(String strLevelsid) {
		this.strLevelsid = strLevelsid;
	}
	public int getIntSex() {
		return intSex;
	}
	public void setIntSex(int intSex) {
		this.intSex = intSex;
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
	public BigDecimal getdBalance() {
		return dBalance;
	}
	public void setdBalance(BigDecimal dBalance) {
		this.dBalance = dBalance;
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
