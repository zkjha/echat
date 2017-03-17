package com.ecard.vo;

/**
 * 商品订单VO实体
 * 
 * @author dinghongxing
 *
 */
public class GoodsOrderVO {

	private String strOrderId; // 主键
	private String strMemberName; // 用户姓名
	private String strGoodsName; // 商品名称
	private String dAmount; // 订单总金额
	private String strInsertTime; // 录入时间
	private String intStatus; // 订单状态0：待支付 1：已支付 2：已发货 3：已完成
	private String intPayType; // 支付方式 0：积分兑换 1：微信支付 2：支付宝支付
	private String strExpressNumber; // 快递单号
	private String strExpressCompany; // 快递公司
	private String strThirdPartyTradeFlow; // 三方支付流水号
	public String getStrOrderId() {
		return strOrderId;
	}
	public void setStrOrderId(String strOrderId) {
		this.strOrderId = strOrderId;
	}
	public String getStrMemberName() {
		return strMemberName;
	}
	public void setStrMemberName(String strMemberName) {
		this.strMemberName = strMemberName;
	}
	public String getStrGoodsName() {
		return strGoodsName;
	}
	public void setStrGoodsName(String strGoodsName) {
		this.strGoodsName = strGoodsName;
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
	public String getIntPayType() {
		return intPayType;
	}
	public void setIntPayType(String intPayType) {
		this.intPayType = intPayType;
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
	public String getStrThirdPartyTradeFlow() {
		return strThirdPartyTradeFlow;
	}
	public void setStrThirdPartyTradeFlow(String strThirdPartyTradeFlow) {
		this.strThirdPartyTradeFlow = strThirdPartyTradeFlow;
	}
}
