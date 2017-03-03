package com.ecard.vo;

import java.util.List;

public class TopPrivilegeVO {
	
	/**
	 * 主键
	 */
	private String strPrivilegeid;
	/**
	 * 权限名称
	 */
	private String strPrivilegename;
	/**
	 * 是否选择 true选中 false非选中
	 */
	private boolean isChecked;
	/**
	 * 子权限列表
	 */
	private List<FloorPrivilegeVO> childrenPrivilegeList;
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
	public boolean isChecked() {
		return isChecked;
	}
	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}
	public List<FloorPrivilegeVO> getChildrenPrivilegeList() {
		return childrenPrivilegeList;
	}
	public void setChildrenPrivilegeList(List<FloorPrivilegeVO> childrenPrivilegeList) {
		this.childrenPrivilegeList = childrenPrivilegeList;
	}
	
} 
