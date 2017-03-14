package com.ecard.entity;


/*
STRRECORDID               VARCHAR(50) NOT NULL,      -- 主键
STRMEMBERID               VARCHAR(50) NOT NULL,      -- 会员ID
STRMEMBERCARDNUM          VARCHAR(50) NOT NULL,      -- 会员卡号
STRUSERNAME               VARCHAR(50) NOT NULL,      -- 用户姓名   
INTINTEGRAL               INT DEFAULT 0,             -- 变更积分值
STREMPLOYEEID             VARCHAR(50) NOT NULL,      -- 操作员工ID
STREMPLOYEEREALNAME       VARCHAR(50) NOT NULL,      -- 操作员工姓名
STREMPLOYEELOGINNAME      VARCHAR(50) NOT NULL,      -- 操作员工登录账号
STRINSERTTIME             VARCHAR(50) NOT NULL,      -- 录入时间
*/


/**
 * 积分修改记录
 * author:qidongbo
 *
 */
public class IntegralModRecord {
	
	// 记录id
	private String strRecordId = null;
	// 会员id
	private String strMemberId = null;
	// 会员卡号
	private String strMemberCardNum = null;
	// 会员姓名
	private String strMemberName = null;
	// 积分变更值
	private int  iIntegralNum = 0;
	
	// 操作员id
	private String strEmployId = null;
	// 操作员姓名
	private String strEmployName = null;
	// 操作员登录账号
	private String strEmployLoginName = null;
	// 录入时间
	private String strInsertTime = null;
	// 增加积分描述
	private String strDesc = null;	
	
	/**
	 * create time:2017年3月14日
	 * @return the strRecordId
	 */
	public String getStrRecordId() {
		return strRecordId;
	}
	
	/**
	 * create time:2017年3月14日
	 * @param strRecordId the strRecordId to set
	 */
	public void setStrRecordId(String strRecordId) {
		this.strRecordId = strRecordId;
	}
	
	/**
	 * create time:2017年3月14日
	 * @return the strMemberId
	 */
	public String getStrMemberId() {
		return strMemberId;
	}
	
	/**
	 * create time:2017年3月14日
	 * @param strMemberId the strMemberId to set
	 */
	public void setStrMemberId(String strMemberId) {
		this.strMemberId = strMemberId;
	}
	
	/**
	 * create time:2017年3月14日
	 * @return the strMemberCardNum
	 */
	public String getStrMemberCardNum() {
		return strMemberCardNum;
	}
	
	/**
	 * create time:2017年3月14日
	 * @param strMemberCardNum the strMemberCardNum to set
	 */
	public void setStrMemberCardNum(String strMemberCardNum) {
		this.strMemberCardNum = strMemberCardNum;
	}
	
	/**
	 * create time:2017年3月14日
	 * @return the strMemberName
	 */
	public String getStrMemberName() {
		return strMemberName;
	}
	
	/**
	 * create time:2017年3月14日
	 * @param strMemberName the strMemberName to set
	 */
	public void setStrMemberName(String strMemberName) {
		this.strMemberName = strMemberName;
	}
	
	/**
	 * create time:2017年3月14日
	 * @return the iIntegralNum
	 */
	public int getiIntegralNum() {
		return iIntegralNum;
	}
	
	/**
	 * create time:2017年3月14日
	 * @param iIntegralNum the iIntegralNum to set
	 */
	public void setiIntegralNum(int iIntegralNum) {
		this.iIntegralNum = iIntegralNum;
	}
	
	/**
	 * create time:2017年3月14日
	 * @return the strEmployId
	 */
	public String getStrEmployId() {
		return strEmployId;
	}
	
	/**
	 * create time:2017年3月14日
	 * @param strEmployId the strEmployId to set
	 */
	public void setStrEmployId(String strEmployId) {
		this.strEmployId = strEmployId;
	}
	
	/**
	 * create time:2017年3月14日
	 * @return the strEmployName
	 */
	public String getStrEmployName() {
		return strEmployName;
	}
	
	/**
	 * create time:2017年3月14日
	 * @param strEmployName the strEmployName to set
	 */
	public void setStrEmployName(String strEmployName) {
		this.strEmployName = strEmployName;
	}
	
	/**
	 * create time:2017年3月14日
	 * @return the strEmployLoginName
	 */
	public String getStrEmployLoginName() {
		return strEmployLoginName;
	}
	
	/**
	 * create time:2017年3月14日
	 * @param strEmployLoginName the strEmployLoginName to set
	 */
	public void setStrEmployLoginName(String strEmployLoginName) {
		this.strEmployLoginName = strEmployLoginName;
	}
	
	/**
	 * create time:2017年3月14日
	 * @return the strInsertTime
	 */
	public String getStrInsertTime() {
		return strInsertTime;
	}
	
	/**
	 * create time:2017年3月14日
	 * @param strInsertTime the strInsertTime to set
	 */
	public void setStrInsertTime(String strInsertTime) {
		this.strInsertTime = strInsertTime;
	}

	
	/**
	 * create time:2017年3月14日
	 * @return the strDesc
	 */
	public String getStrDesc() {
		return strDesc;
	}

	
	/**
	 * create time:2017年3月14日
	 * @param strDesc the strDesc to set
	 */
	public void setStrDesc(String strDesc) {
		this.strDesc = strDesc;
	}

}
