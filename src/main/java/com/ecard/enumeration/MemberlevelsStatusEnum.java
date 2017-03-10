package com.ecard.enumeration;
/**
 * 会员级别状态枚举类
 * @author Administrator
 *
 */
public enum MemberlevelsStatusEnum {
	/**
	 * 激活状态
	 */
	ACTIVATE(1),
	/**
	 * 禁用状态
	 */
	FORBID(0);
	private int value;

	MemberlevelsStatusEnum(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

}
