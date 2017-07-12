package com.ecard.entity;

import java.math.BigDecimal;

/**
 * 会员等级信息实体
 * @author dinghongxing
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
	

	private int iTradeTimesDesc;           //会员卡级别描述：交易次数描述
	private BigDecimal dConsumeCashDesc;        //会员卡级别描述：交易金额描述
	
	private String strOtherDesc;//会员卡级别描述：其它文字性描述
	public void setStrOtherDesc(String strOtherDesc)
	{
		this.strOtherDesc=strOtherDesc;
	}
	
	
	public String getStrOtherDesc()
	{
		return strOtherDesc;
	}
	public void setdConsumeCashDesc(BigDecimal dConsumeCashDesc)
	{
		this.dConsumeCashDesc=dConsumeCashDesc;
	}
	
	public BigDecimal getdConsumeCashDesc()
	{
		return dConsumeCashDesc;
	}

	public int getiTradeTimesDesc() {
		return iTradeTimesDesc;
	}
	public void setiTradeTimesDesc(int iTradeTimesDesc) {
		this.iTradeTimesDesc = iTradeTimesDesc;
	}
	
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
