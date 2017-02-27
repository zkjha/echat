package com.ecard.entity;

/**
 * 权限信息实体
 * @author Administrator
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
	 * 描述
	 */
	private String strPrivilegedesc;
	/**
	 * 访问路径
	 */
	private String strPrivilegeurl;
	/**
	 * 父级权限ID
	 */
	private String strParentid;
	/**
	 * 录入时间
	 */
	private String strInserttime;
	/**
	 * 修改时间
	 */
	private String strUpdatetime;
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
	public String getStrPrivilegedesc() {
		return strPrivilegedesc;
	}
	public void setStrPrivilegedesc(String strPrivilegedesc) {
		this.strPrivilegedesc = strPrivilegedesc;
	}
	public String getStrPrivilegeurl() {
		return strPrivilegeurl;
	}
	public void setStrPrivilegeurl(String strPrivilegeurl) {
		this.strPrivilegeurl = strPrivilegeurl;
	}
	public String getStrParentid() {
		return strParentid;
	}
	public void setStrParentid(String strParentid) {
		this.strParentid = strParentid;
	}
	public String getStrInserttime() {
		return strInserttime;
	}
	public void setStrInserttime(String strInserttime) {
		this.strInserttime = strInserttime;
	}
	public String getStrUpdatetime() {
		return strUpdatetime;
	}
	public void setStrUpdatetime(String strUpdatetime) {
		this.strUpdatetime = strUpdatetime;
	}
	
	
} 
