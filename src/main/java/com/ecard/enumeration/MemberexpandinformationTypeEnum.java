package com.ecard.enumeration;
/**
 * 会员拓展资料类型Enum
 * @author Administrator
 *
 */
public enum MemberexpandinformationTypeEnum {
	/**
	 * 输入框
	 */
	INPUT(0),
	/**
	 * 下拉选择
	 */
	SELECT(1),
	/**
	 * 多选
	 */
	CHECKBOX(2);
	private int value;

	MemberexpandinformationTypeEnum(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

}
