package com.ecard.vo;

import java.util.List;

public class PrivilegeVO {
	
	/**
	 * 头部菜单名称
	 */
	private String strTopmenuname;
	/**
	 * 顶级权限列表
	 */
	private List<TopPrivilegeVO> topPrivilegeList;
	public String getStrTopmenuname() {
		return strTopmenuname;
	}
	public void setStrTopmenuname(String strTopmenuname) {
		this.strTopmenuname = strTopmenuname;
	}
	public List<TopPrivilegeVO> getTopPrivilegeList() {
		return topPrivilegeList;
	}
	public void setTopPrivilegeList(List<TopPrivilegeVO> topPrivilegeList) {
		this.topPrivilegeList = topPrivilegeList;
	}
	
	
} 
