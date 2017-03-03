package com.ecard.vo;

import java.util.List;

public class FloorPrivilegeDTO {
	
	/**
	 * 是否选择 true选中 false非选中
	 */
	private boolean isChecked;
	/**
	 * 子权限列表
	 */
	private List<FloorPrivilegeVO> childrenPrivilegeList;
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
