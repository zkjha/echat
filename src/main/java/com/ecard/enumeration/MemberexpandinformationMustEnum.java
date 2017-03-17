package com.ecard.enumeration;
/**
 * 会员拓展资料是否必填Enum
 * @author zhengwei
 *
 */
public enum MemberexpandinformationMustEnum {
	/**
	 * 非必填
	 */
	MUST_FALSE(0),
	/**
	 * 必填
	 */
	MUST_TRUE(1);
	private int value;

	MemberexpandinformationMustEnum(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

}
