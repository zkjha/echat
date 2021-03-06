package com.ecard.entity;

import java.math.BigDecimal;

/**
 * 现金充值订单
 * @author dinghongxing
 *
 */
public class RechargeOrderEntity {
	
	private String strOrderId;		//主键
	private String strMemberId;		//会员ID
	private String strMemberCardNum;	//会员卡号
	private String strMemberName;	//用户姓名
	private String strDesc;			//
	private BigDecimal dBalance;	//充值金额
	private String strEmployeeId;
	private String strEmployeeRealName;
	private String strEmployeeLoginName;
	private String strInsertTime;
	private int intStatus;//订单状态0：待支付 1：已支付
	private String  strPayTime;//支付时间
	private int intPayType; //支付方式 0：现金支付 1：微信支付 2：支付宝支付
	private String strThirdPartyTradeFlow; //三方支付流水号
	
	private BigDecimal dRestBalance;	//剩余储值 金额
	private BigDecimal dRestAfterBalance;	//剩余售后储值 金额
	
	public void setdRestBalance(BigDecimal dRestBalance)
	{
		this.dRestBalance=dRestBalance;
	}
	
	public BigDecimal getdRestBalance()
	{
		return dRestBalance;
	}
	
	public void setdRestAfterBalance(BigDecimal dRestAfterBalance)
	{
		this.dRestAfterBalance=dRestAfterBalance;
	}
	
	public BigDecimal getdRestAfterBalance()
	{
		return dRestAfterBalance;
	}
	
	public String getStrOrderId() {
		return strOrderId;
	}
	public void setStrOrderId(String strOrderId) {
		this.strOrderId = strOrderId;
	}
	public String getStrMemberId() {
		return strMemberId;
	}
	public void setStrMemberId(String strMemberId) {
		this.strMemberId = strMemberId;
	}
	public String getStrMemberCardNum() {
		return strMemberCardNum;
	}
	public void setStrMemberCardNum(String strMemberCardNum) {
		this.strMemberCardNum = strMemberCardNum;
	}
	public String getStrMemberName() {
		return strMemberName;
	}
	public void setStrMemberName(String strMemberName) {
		this.strMemberName = strMemberName;
	}
	public String getStrDesc() {
		return strDesc;
	}
	public void setStrDesc(String strDesc) {
		this.strDesc = strDesc;
	}
	public BigDecimal getdBalance() {
		return dBalance;
	}
	public void setdBalance(BigDecimal dBalance) {
		this.dBalance = dBalance;
	}
	public String getStrEmployeeId() {
		return strEmployeeId;
	}
	public void setStrEmployeeId(String strEmployeeId) {
		this.strEmployeeId = strEmployeeId;
	}
	public String getStrEmployeeRealName() {
		return strEmployeeRealName;
	}
	public void setStrEmployeeRealName(String strEmployeeRealName) {
		this.strEmployeeRealName = strEmployeeRealName;
	}
	public String getStrEmployeeLoginName() {
		return strEmployeeLoginName;
	}
	public void setStrEmployeeLoginName(String strEmployeeLoginName) {
		this.strEmployeeLoginName = strEmployeeLoginName;
	}
	public String getStrInsertTime() {
		return strInsertTime;
	}
	public void setStrInsertTime(String strInsertTime) {
		this.strInsertTime = strInsertTime;
	}
	public int getIntStatus() {
		return intStatus;
	}
	public void setIntStatus(int intStatus) {
		this.intStatus = intStatus;
	}
	public String getStrPayTime() {
		return strPayTime;
	}
	public void setStrPayTime(String strPayTime) {
		this.strPayTime = strPayTime;
	}
	public int getIntPayType() {
		return intPayType;
	}
	public void setIntPayType(int intPayType) {
		this.intPayType = intPayType;
	}
	public String getStrThirdPartyTradeFlow() {
		return strThirdPartyTradeFlow;
	}
	public void setStrThirdPartyTradeFlow(String strThirdPartyTradeFlow) {
		this.strThirdPartyTradeFlow = strThirdPartyTradeFlow;
	}
	
}
