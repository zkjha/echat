

/**
 * fun:
 * author:qidongbo
 * create time:2017年3月22日
 */
package com.ecard.entity;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author apple
 *服务项目信息
 */
public class ServiceInfoEntity {
	private BigDecimal dSalePrice;	//服务销售价格
	private String strServiceInfoId="";	//主键
	private String strServiceInfoName="";	//服务类别名称
	private String strServiceTypeId="";		//关联的服务类别 ID
	private String strServiceTypeName="";	//关联的服务类别名称
	private String strServiceBarCode="";//编号条码
	private String strUnitId="";	//服务单位ID
	private String strUnitName="";	//服务计量单位名称
	private String strSupplierName="";	//供应商名称
	private int iPreferentialType=0;	//商品优惠类型 0 不优惠 1 按照会员等级优惠
	private int iState=0;		//商品状态 0 不使用 1 使用 
	private String txtServiceDesc="";	//富文本描述信息
	private String txtServiceDescDetail="";	//富文本详情描述信息
	private String strEmployeeId="";
	private String strEmployeeName="";
	private String strEmployeeLoginName="";
	private String strInsertTime="";
	private String strUpdateTime="";
	private String strReserved="";
	private int iRequiredIntegral=0;//	兑换服务所需积分数量	
	private List<ServicePreferentialEntity> listServicePreferentialEntity;
	private String strServiceImgName;//服务图片名称
	
	public void setStrServiceImgName(String strServiceImgName)
	{
		this.strServiceImgName=strServiceImgName;
	}
	
	public String getStrServiceImgName()
	{
		return strServiceImgName;
	}
	public int getiRequiredIntegral()
	{
		return iRequiredIntegral;
	}
	
	public void setiRequiredIntegral(int iRequiredIntegral)
	{
		this.iRequiredIntegral=iRequiredIntegral;
	}
	
	
	
	/**
	 * create time:2017年3月30日
	 * @return the listServicePreferentialEntity
	 */
	public List<ServicePreferentialEntity> getListServicePreferentialEntity() {
		return listServicePreferentialEntity;
	}

	
	/**
	 * create time:2017年3月30日
	 * @param listServicePreferentialEntity the listServicePreferentialEntity to set
	 */
	public void setListServicePreferentialEntity(
			List<ServicePreferentialEntity> listServicePreferentialEntity) {
		this.listServicePreferentialEntity = listServicePreferentialEntity;
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
	 * @return the strServiceInfoId
	 */
	public String getStrServiceInfoId() {
		return strServiceInfoId;
	}
	
	/**
	 * create time:2017年3月30日
	 * @param strServiceInfoId the strServiceInfoId to set
	 */
	public void setStrServiceInfoId(String strServiceInfoId) {
		this.strServiceInfoId = strServiceInfoId;
	}
	
	/**
	 * create time:2017年3月30日
	 * @return the strServiceInfoName
	 */
	public String getStrServiceInfoName() {
		return strServiceInfoName;
	}
	
	/**
	 * create time:2017年3月30日
	 * @param strServiceInfoName the strServiceInfoName to set
	 */
	public void setStrServiceInfoName(String strServiceInfoName) {
		this.strServiceInfoName = strServiceInfoName;
	}
	
	/**
	 * create time:2017年3月30日
	 * @return the strServiceTypeId
	 */
	public String getStrServiceTypeId() {
		return strServiceTypeId;
	}
	
	/**
	 * create time:2017年3月30日
	 * @param strServiceTypeId the strServiceTypeId to set
	 */
	public void setStrServiceTypeId(String strServiceTypeId) {
		this.strServiceTypeId = strServiceTypeId;
	}
	
	/**
	 * create time:2017年3月30日
	 * @return the strServiceTypeName
	 */
	public String getStrServiceTypeName() {
		return strServiceTypeName;
	}
	
	/**
	 * create time:2017年3月30日
	 * @param strServiceTypeName the strServiceTypeName to set
	 */
	public void setStrServiceTypeName(String strServiceTypeName) {
		this.strServiceTypeName = strServiceTypeName;
	}
	
	/**
	 * create time:2017年3月30日
	 * @return the strServiceBarCode
	 */
	public String getStrServiceBarCode() {
		return strServiceBarCode;
	}
	
	/**
	 * create time:2017年3月30日
	 * @param strServiceBarCode the strServiceBarCode to set
	 */
	public void setStrServiceBarCode(String strServiceBarCode) {
		this.strServiceBarCode = strServiceBarCode;
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
	 * @return the txtServiceDesc
	 */
	public String getTxtServiceDesc() {
		return txtServiceDesc;
	}
	
	/**
	 * create time:2017年3月30日
	 * @param txtServiceDesc the txtServiceDesc to set
	 */
	public void setTxtServiceDesc(String txtServiceDesc) {
		this.txtServiceDesc = txtServiceDesc;
	}
	
	/**
	 * create time:2017年3月30日
	 * @return the txtServiceDescDetail
	 */
	public String getTxtServiceDescDetail() {
		return txtServiceDescDetail;
	}
	
	/**
	 * create time:2017年3月30日
	 * @param txtServiceDescDetail the txtServiceDescDetail to set
	 */
	public void setTxtServiceDescDetail(String txtServiceDescDetail) {
		this.txtServiceDescDetail = txtServiceDescDetail;
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
	
	/**
	 * create time:2017年3月30日
	 * @return the strReserved
	 */
	public String getStrReserved() {
		return strReserved;
	}
	
	/**
	 * create time:2017年3月30日
	 * @param strReserved the strReserved to set
	 */
	public void setStrReserved(String strReserved) {
		this.strReserved = strReserved;
	}
	

		
}
