package com.ecard.entity;

/**
 * 职务信息实体
 * @author dinghongxing
 *
 */
public class DutyEntity {

	/**
	 * 主键
	 */
	private String strDutyid;
	/**
	 * 职务名称
	 */
	private String strDutyname;
	/**
	 * 录入时间
	 */
	private String strInserttime;
	/**
	 * 修改时间
	 */
	private String strUpdatetime;
	public String getStrDutyid() {
		return strDutyid;
	}
	public void setStrDutyid(String strDutyid) {
		this.strDutyid = strDutyid;
	}
	public String getStrDutyname() {
		return strDutyname;
	}
	public void setStrDutyname(String strDutyname) {
		this.strDutyname = strDutyname;
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
