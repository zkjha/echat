package com.ecard.vo;

public class TokenVO {
	/**
	 * 令牌
	 */
	private String access_token;
	/**
	 * 有效时间
	 */
	private Long expire_time;
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public Long getExpire_time() {
		return expire_time;
	}
	public void setExpire_time(Long expire_time) {
		this.expire_time = expire_time;
	}
	
} 
