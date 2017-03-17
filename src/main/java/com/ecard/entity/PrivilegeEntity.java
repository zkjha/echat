package com.ecard.entity;

/**
 * 权限信息实体
 * @author dinghongxing
 *
 */
public class PrivilegeEntity {

	/**
	 * 主键
	 */
	private String strPrivilegeid;
	/**
	 * 权限名称
	 */
	private String strPrivilegename;
	/**
	 * 头部菜单名称
	 */
	private String strTopmenuname;
	/**
	 * 父级权限ID (顶级权限为top)
	 */
	private String strParentid;
	public String getStrPrivilegeid() {
		return strPrivilegeid;
	}
	public void setStrPrivilegeid(String strPrivilegeid) {
		this.strPrivilegeid = strPrivilegeid;
	}
	public String getStrPrivilegename() {
		return strPrivilegename;
	}
	public void setStrPrivilegename(String strPrivilegename) {
		this.strPrivilegename = strPrivilegename;
	}
	public String getStrTopmenuname() {
		return strTopmenuname;
	}
	public void setStrTopmenuname(String strTopmenuname) {
		this.strTopmenuname = strTopmenuname;
	}
	public String getStrParentid() {
		return strParentid;
	}
	public void setStrParentid(String strParentid) {
		this.strParentid = strParentid;
	}
	
} 
