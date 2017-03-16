package com.ecard.entity;

/**
 * 商品订单实体
 * 
 * @author dinghongxing
 *
 */
public class GoodsOrderEntity {

	private String strOrderId; // 主键
	private String strMemberId; // 会员ID
	private String strMemberCardNum; // 会员卡号
	private String strMemberName; // 用户姓名
	private String strGoodsId; // 商品ID
	private String strGoodsName; // 商品名称
	private String intCount; // 数量
	private String dPrice; // 商品单价
	private String dAmount; // 订单总金额
	private String strInsertTime; // 录入时间
	private String intStatus; // 订单状态0：待支付 1：已支付 2：已发货 3：已完成
	private String strPayTime; // 支付时间
	private String intPayType; // 支付方式 0：积分兑换 1：微信支付 2：支付宝支付
	private String strThirdPartyTradeFlow; // 三方支付流水号
	private String strExpressNumber; // 快递单号
	private String strExpressCompany; // 快递公司
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
	public String getStrGoodsId() {
		return strGoodsId;
	}
	public void setStrGoodsId(String strGoodsId) {
		this.strGoodsId = strGoodsId;
	}
	public String getStrGoodsName() {
		return strGoodsName;
	}
	public void setStrGoodsName(String strGoodsName) {
		this.strGoodsName = strGoodsName;
	}
	public String getIntCount() {
		return intCount;
	}
	public void setIntCount(String intCount) {
		this.intCount = intCount;
	}
	public String getdPrice() {
		return dPrice;
	}
	public void setdPrice(String dPrice) {
		this.dPrice = dPrice;
	}
	public String getdAmount() {
		return dAmount;
	}
	public void setdAmount(String dAmount) {
		this.dAmount = dAmount;
	}
	public String getStrInsertTime() {
		return strInsertTime;
	}
	public void setStrInsertTime(String strInsertTime) {
		this.strInsertTime = strInsertTime;
	}
	public String getIntStatus() {
		return intStatus;
	}
	public void setIntStatus(String intStatus) {
		this.intStatus = intStatus;
	}
	public String getStrPayTime() {
		return strPayTime;
	}
	public void setStrPayTime(String strPayTime) {
		this.strPayTime = strPayTime;
	}
	public String getIntPayType() {
		return intPayType;
	}
	public void setIntPayType(String intPayType) {
		this.intPayType = intPayType;
	}
	public String getStrThirdPartyTradeFlow() {
		return strThirdPartyTradeFlow;
	}
	public void setStrThirdPartyTradeFlow(String strThirdPartyTradeFlow) {
		this.strThirdPartyTradeFlow = strThirdPartyTradeFlow;
	}
	public String getStrExpressNumber() {
		return strExpressNumber;
	}
	public void setStrExpressNumber(String strExpressNumber) {
		this.strExpressNumber = strExpressNumber;
	}
	public String getStrExpressCompany() {
		return strExpressCompany;
	}
	public void setStrExpressCompany(String strExpressCompany) {
		this.strExpressCompany = strExpressCompany;
	}
	
}
