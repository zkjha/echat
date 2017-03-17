package com.ecard.enumeration;
/**
 * 员工状态枚举类
 * @author zhengwei
 *
 */
public enum EmployeeStatusEnum {
	/**
	 * 激活状态
	 */
	ACTIVATE(1),
	/**
	 * 禁用状态
	 */
	FORBID(0);
	private int value;

	EmployeeStatusEnum(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

}
