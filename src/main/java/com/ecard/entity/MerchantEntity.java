package com.ecard.entity;

/**
 * 商家信息实体
 * @author dinghongxing
 *
 */
public class MerchantEntity {

	/**
	 * 主键
	 */
	private String strMerchantid;
	/**
	 * 商家名称
	 */
	private String strMerchantname;
	/**
	 * 商家地址
	 */
	private String strMerchantaddress;
	/**
	 * 商家LOGO
	 */
	private String strMerchantlogo;
	/**
	 * 系统版本
	 */
	private String strSystemversion;
	/**
	 * 到期日期
	 */
	private String strExpirationtime;
	/**
	 * 系统有效期天数
	 */
	private int intValiddays;
	/**
	 * 会员最大允许数量
	 */
	private int intMembercount;
	/**
	 * 系统秘钥
	 */
	private String strSystemsecret;
	/**
	 * 录入时间
	 */
	private String strInserttime;
	/**
	 * 修改时间
	 */
	private String strUpdatetime;
	public String getStrMerchantid() {
		return strMerchantid;
	}
	public void setStrMerchantid(String strMerchantid) {
		this.strMerchantid = strMerchantid;
	}
	public String getStrMerchantname() {
		return strMerchantname;
	}
	public void setStrMerchantname(String strMerchantname) {
		this.strMerchantname = strMerchantname;
	}
	public String getStrMerchantaddress() {
		return strMerchantaddress;
	}
	public void setStrMerchantaddress(String strMerchantaddress) {
		this.strMerchantaddress = strMerchantaddress;
	}
	public String getStrMerchantlogo() {
		return strMerchantlogo;
	}
	public void setStrMerchantlogo(String strMerchantlogo) {
		this.strMerchantlogo = strMerchantlogo;
	}
	public String getStrSystemversion() {
		return strSystemversion;
	}
	public void setStrSystemversion(String strSystemversion) {
		this.strSystemversion = strSystemversion;
	}
	public String getStrExpirationtime() {
		return strExpirationtime;
	}
	public void setStrExpirationtime(String strExpirationtime) {
		this.strExpirationtime = strExpirationtime;
	}
	public int getIntValiddays() {
		return intValiddays;
	}
	public void setIntValiddays(int intValiddays) {
		this.intValiddays = intValiddays;
	}
	public int getIntMembercount() {
		return intMembercount;
	}
	public void setIntMembercount(int intMembercount) {
		this.intMembercount = intMembercount;
	}
	public String getStrSystemsecret() {
		return strSystemsecret;
	}
	public void setStrSystemsecret(String strSystemsecret) {
		this.strSystemsecret = strSystemsecret;
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
