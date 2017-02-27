package com.ecard.entity;

/**
 * 职务权限关系实体
 * @author Administrator
 *
 */
public class DutyPrivilegeEntity {

	/**
	 * 主键
	 */
	private String strRelationid;
	/**
	 * 权限ID
	 */
	private String strPrivilegeid;
	/**
	 * 职务ID
	 */
	private String strDutyid;
	public String getStrRelationid() {
		return strRelationid;
	}
	public void setStrRelationid(String strRelationid) {
		this.strRelationid = strRelationid;
	}
	public String getStrPrivilegeid() {
		return strPrivilegeid;
	}
	public void setStrPrivilegeid(String strPrivilegeid) {
		this.strPrivilegeid = strPrivilegeid;
	}
	public String getStrDutyid() {
		return strDutyid;
	}
	public void setStrDutyid(String strDutyid) {
		this.strDutyid = strDutyid;
	}
	
	
	
} 
