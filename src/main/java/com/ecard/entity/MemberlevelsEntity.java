package com.ecard.entity;

/**
 * 会员等级信息实体
 * @author Administrator
 *
 */
public class MemberlevelsEntity {

	/**
	 * 主键
	 */
	private String strLevelsid;
	/**
	 * 级别名称
	 */
	private String strLevelsname;
	/**
	 * 会员卡级别状态0：禁用 1：启用
	 */
	private int intStatus; 
	/**
	 * 录入时间
	 */
	private String strInserttime;
	/**
	 * 修改时间
	 */
	private String strUpdatetime;
	public String getStrLevelsid() {
		return strLevelsid;
	}
	public void setStrLevelsid(String strLevelsid) {
		this.strLevelsid = strLevelsid;
	}
	public String getStrLevelsname() {
		return strLevelsname;
	}
	public void setStrLevelsname(String strLevelsname) {
		this.strLevelsname = strLevelsname;
	}
	public int getIntStatus() {
		return intStatus;
	}
	public void setIntStatus(int intStatus) {
		this.intStatus = intStatus;
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
