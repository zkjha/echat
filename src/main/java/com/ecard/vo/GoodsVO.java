package com.ecard.vo;

import java.math.BigDecimal;

//在原商品属性的基础上增加了 按等级计算的商品优惠价，和按等级计算的商品积分属性
public class GoodsVO
{
	private String strGoodsId="";			//主键
	private String strGoodsBarCode="";		//商品条码
	private String strUnitId="";			//计量单位ID
	private String strUnitName="";			//商品计量单位名称
	private String strGoodsName="";			//商品名称
	private String strGoodsTypeId="";		//商品所属种类ID
	private String strGoodsTypeName="";		//商品种类名称
	private String strSupplierName="";		//商品供应商名称
	private int iRequiredIntegral=0;		//兑换商品所需积分
	private BigDecimal dEnterPrice;			//商品进价
	private BigDecimal dSalePrice;			//商品销售价格
	private int iStock=0;					//商品库存
	private int iPreferentialType=0;		//商品优惠类型 0 不优惠 1 按照会员等级优惠
	private int iState=0;					//商品状态 0 不使用 1 使用
	private String txtGoodsDesc="";			//富文本描述信息
	private String txtGoodsDescDetail="";	//富文本描述信息
	private String strGoodsImg="";	//商品图片地址
	private String strEmployeeId="";
	private String strEmployeeName="";
	private String strEmployeeLoginName="";
	private String strInsertTime="";
	private String strUpdateTime="";
	private BigDecimal dPreferentialPrice;	//按等级计算后的商品价钱
	private int iLevelIntegration=0;	//按等级计算后的商品兑换积分
	private int iIsNewGoods=0;	//商品是否属于本周新品 0 不是 1  是
	private int iIsHotGoods=0;	//商品是否属于热销商品  0 不是 1 是
	
	/**
	 * create time:2017年3月30日
	 * @return the strGoodsId
	 */
	public String getStrGoodsId() {
		return strGoodsId;
	}
	
	/**
	 * create time:2017年3月30日
	 * @param strGoodsId the strGoodsId to set
	 */
	public void setStrGoodsId(String strGoodsId) {
		this.strGoodsId = strGoodsId;
	}
	
	/**
	 * create time:2017年3月30日
	 * @return the strGoodsBarCode
	 */
	public String getStrGoodsBarCode() {
		return strGoodsBarCode;
	}
	
	/**
	 * create time:2017年3月30日
	 * @param strGoodsBarCode the strGoodsBarCode to set
	 */
	public void setStrGoodsBarCode(String strGoodsBarCode) {
		this.strGoodsBarCode = strGoodsBarCode;
	}
	
	/**
	 * create time:2017年3月30日
	 * @return the strUnitId
	 */
	public String getStrUnitId() {
		return strUnitId;
	}
	
	/**
	 * create time:2017年3月30日
	 * @param strUnitId the strUnitId to set
	 */
	public void setStrUnitId(String strUnitId) {
		this.strUnitId = strUnitId;
	}
	
	/**
	 * create time:2017年3月30日
	 * @return the strUnitName
	 */
	public String getStrUnitName() {
		return strUnitName;
	}
	
	/**
	 * create time:2017年3月30日
	 * @param strUnitName the strUnitName to set
	 */
	public void setStrUnitName(String strUnitName) {
		this.strUnitName = strUnitName;
	}
	
	/**
	 * create time:2017年3月30日
	 * @return the strGoodsName
	 */
	public String getStrGoodsName() {
		return strGoodsName;
	}
	
	/**
	 * create time:2017年3月30日
	 * @param strGoodsName the strGoodsName to set
	 */
	public void setStrGoodsName(String strGoodsName) {
		this.strGoodsName = strGoodsName;
	}
	
	/**
	 * create time:2017年3月30日
	 * @return the strGoodsTypeId
	 */
	public String getStrGoodsTypeId() {
		return strGoodsTypeId;
	}
	
	/**
	 * create time:2017年3月30日
	 * @param strGoodsTypeId the strGoodsTypeId to set
	 */
	public void setStrGoodsTypeId(String strGoodsTypeId) {
		this.strGoodsTypeId = strGoodsTypeId;
	}
	
	/**
	 * create time:2017年3月30日
	 * @return the strGoodsTypeName
	 */
	public String getStrGoodsTypeName() {
		return strGoodsTypeName;
	}
	
	/**
	 * create time:2017年3月30日
	 * @param strGoodsTypeName the strGoodsTypeName to set
	 */
	public void setStrGoodsTypeName(String strGoodsTypeName) {
		this.strGoodsTypeName = strGoodsTypeName;
	}
	
	/**
	 * create time:2017年3月30日
	 * @return the strSupplierName
	 */
	public String getStrSupplierName() {
		return strSupplierName;
	}
	
	/**
	 * create time:2017年3月30日
	 * @param strSupplierName the strSupplierName to set
	 */
	public void setStrSupplierName(String strSupplierName) {
		this.strSupplierName = strSupplierName;
	}
	
	/**
	 * create time:2017年3月30日
	 * @return the iRequiredIntegral
	 */
	public int getiRequiredIntegral() {
		return iRequiredIntegral;
	}
	
	/**
	 * create time:2017年3月30日
	 * @param iRequiredIntegral the iRequiredIntegral to set
	 */
	public void setiRequiredIntegral(int iRequiredIntegral) {
		this.iRequiredIntegral = iRequiredIntegral;
	}
	
	/**
	 * create time:2017年3月30日
	 * @return the dEnterPrice
	 */
	public BigDecimal getdEnterPrice() {
		return dEnterPrice;
	}
	
	/**
	 * create time:2017年3月30日
	 * @param dEnterPrice the dEnterPrice to set
	 */
	public void setdEnterPrice(BigDecimal dEnterPrice) {
		this.dEnterPrice = dEnterPrice;
	}
	
	/**
	 * create time:2017年3月30日
	 * @return the dSalePrice
	 */
	public BigDecimal getdSalePrice() {
		return dSalePrice;
	}
	
	/**
	 * create time:2017年3月30日
	 * @param dSalePrice the dSalePrice to set
	 */
	public void setdSalePrice(BigDecimal dSalePrice) {
		this.dSalePrice = dSalePrice;
	}
	
	/**
	 * create time:2017年3月30日
	 * @return the iStock
	 */
	public int getiStock() {
		return iStock;
	}
	
	/**
	 * create time:2017年3月30日
	 * @param iStock the iStock to set
	 */
	public void setiStock(int iStock) {
		this.iStock = iStock;
	}
	
	/**
	 * create time:2017年3月30日
	 * @return the iPreferentialType
	 */
	public int getiPreferentialType() {
		return iPreferentialType;
	}
	
	/**
	 * create time:2017年3月30日
	 * @param iPreferentialType the iPreferentialType to set
	 */
	public void setiPreferentialType(int iPreferentialType) {
		this.iPreferentialType = iPreferentialType;
	}
	
	/**
	 * create time:2017年3月30日
	 * @return the iState
	 */
	public int getiState() {
		return iState;
	}
	
	/**
	 * create time:2017年3月30日
	 * @param iState the iState to set
	 */
	public void setiState(int iState) {
		this.iState = iState;
	}
	
	/**
	 * create time:2017年3月30日
	 * @return the txtGoodsDesc
	 */
	public String getTxtGoodsDesc() {
		return txtGoodsDesc;
	}
	
	/**
	 * create time:2017年3月30日
	 * @param txtGoodsDesc the txtGoodsDesc to set
	 */
	public void setTxtGoodsDesc(String txtGoodsDesc) {
		this.txtGoodsDesc = txtGoodsDesc;
	}
	
	/**
	 * create time:2017年3月30日
	 * @return the txtGoodsDescDetail
	 */
	public String getTxtGoodsDescDetail() {
		return txtGoodsDescDetail;
	}
	
	/**
	 * create time:2017年3月30日
	 * @param txtGoodsDescDetail the txtGoodsDescDetail to set
	 */
	public void setTxtGoodsDescDetail(String txtGoodsDescDetail) {
		this.txtGoodsDescDetail = txtGoodsDescDetail;
	}
	
	/**
	 * create time:2017年3月30日
	 * @return the strEmployeeId
	 */
	public String getStrEmployeeId() {
		return strEmployeeId;
	}
	
	/**
	 * create time:2017年3月30日
	 * @param strEmployeeId the strEmployeeId to set
	 */
	public void setStrEmployeeId(String strEmployeeId) {
		this.strEmployeeId = strEmployeeId;
	}
	
	/**
	 * create time:2017年3月30日
	 * @return the strEmployeeName
	 */
	public String getStrEmployeeName() {
		return strEmployeeName;
	}
	
	/**
	 * create time:2017年3月30日
	 * @param strEmployeeName the strEmployeeName to set
	 */
	public void setStrEmployeeName(String strEmployeeName) {
		this.strEmployeeName = strEmployeeName;
	}
	
	/**
	 * create time:2017年3月30日
	 * @return the strEmployeeLoginName
	 */
	public String getStrEmployeeLoginName() {
		return strEmployeeLoginName;
	}
	
	/**
	 * create time:2017年3月30日
	 * @param strEmployeeLoginName the strEmployeeLoginName to set
	 */
	public void setStrEmployeeLoginName(String strEmployeeLoginName) {
		this.strEmployeeLoginName = strEmployeeLoginName;
	}
	
	/**
	 * create time:2017年3月30日
	 * @return the strInsertTime
	 */
	public String getStrInsertTime() {
		return strInsertTime;
	}
	
	/**
	 * create time:2017年3月30日
	 * @param strInsertTime the strInsertTime to set
	 */
	public void setStrInsertTime(String strInsertTime) {
		this.strInsertTime = strInsertTime;
	}
	
	/**
	 * create time:2017年3月30日
	 * @return the strUpdateTime
	 */
	public String getStrUpdateTime() {
		return strUpdateTime;
	}
	
	/**
	 * create time:2017年3月30日
	 * @param strUpdateTime the strUpdateTime to set
	 */
	public void setStrUpdateTime(String strUpdateTime) {
		this.strUpdateTime = strUpdateTime;
	}
	
	public void setdPreferentialPrice(BigDecimal dPreferentialPrice)
	{
		this.dPreferentialPrice=dPreferentialPrice;
	}
	
	public BigDecimal getdPreferentialPrice()
	{
		return dPreferentialPrice;
	}
	
	public void setiLevelIntegration(int iLevelIntegration)
	{
		this.iLevelIntegration=iLevelIntegration;
	}
	
	public int getiLevelIntegration()
	{
		return iLevelIntegration;
	}
	
	public void setStrGoodsImg(String strGoodsImg)
	{
		this.strGoodsImg=strGoodsImg;
	}
	
	public String getStrGoodsImg()
	{
		return strGoodsImg;
	}
	
	public void iIsNewGoods(int iIsNewGoods)
	{
		this.iIsNewGoods=iIsNewGoods;
	}
	
	public int getiIsNewGoods()
	{
		return iIsNewGoods;
	}

	public void setiIsHotGoods(int iIsHotGoods)
	{
		this.iIsHotGoods=iIsHotGoods;
	}
	
	public int getiIsHotGoods()
	{
		return iIsHotGoods;
	}
}
