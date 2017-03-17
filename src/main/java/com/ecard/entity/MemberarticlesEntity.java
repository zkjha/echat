package com.ecard.entity;

/**
 * 会员章程信息实体
 * @author dinghongxing
 *
 */
public class MemberarticlesEntity {

	/**
	 * 主键
	 */
	private String strArticlesid;
	/**
	 * 内容
	 */
	private String strContent;
	/**
	 * 录入时间
	 */
	private String strInserttime;
	/**
	 * 修改时间
	 */
	private String strUpdatetime;
	public String getStrArticlesid() {
		return strArticlesid;
	}
	public void setStrArticlesid(String strArticlesid) {
		this.strArticlesid = strArticlesid;
	}
	public String getStrContent() {
		return strContent;
	}
	public void setStrContent(String strContent) {
		this.strContent = strContent;
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
