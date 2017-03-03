package com.ecard.vo;

public class FloorPrivilegeVO {
	
	/**
	 * 主键
	 */
	private String strPrivilegeid;
	/**
	 * 权限名称
	 */
	private String strPrivilegename;
	/**
	 * 父级权限ID
	 */
	private String strParentid;
	/**
	 * 是否选择 true选中 false非选中
	 */
	private boolean isChecked;
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
	public String getStrParentid() {
		return strParentid;
	}
	public void setStrParentid(String strParentid) {
		this.strParentid = strParentid;
	}
	public boolean isChecked() {
		return isChecked;
	}
	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}
	
} 
