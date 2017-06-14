package com.ecard.config;

/**
 * 存放全局常量
 * @author liupengyan
 *
 */
public final class StaticValue {

	/**
	 * 用户session失效时间
	 */
	public static final Long USER_SESSION_EXPIRE_TIME = 604800L;
	/**
	 * 验证码过期时间（秒）
	 */
	public static final Long CAPTCHA_VALID_TIME = 300L;
	/**
	 * cookie name
	 */
	public static final String COOKIENAME ="ecardid";
	/**
	 * cookie name（微信端）
	 */
	public static final String WX_COOKIENAME = "wxecardid";
	/**
	 * cookie domain
	 */
	//public static final String DOMAIN="fushoukeji.com";
	/**
	 * 文件上传根路径
	 */
	public static final String FILE_UPLOAD_URL = "d:\\4";
	/**
	 * 图片根路径
	 */
	public static final String IMAGE_ROOT_PATH = "http://linux.fushoukeji.com:88/picpath/";
	/**
	 * 项目根路径
	 */
	public static final String PROJECT_ROOT_PATH = "/";
	/**
	 * 分页默认每页显示条数
	 */
	public static final int PAGE_SIZE = 10;
	
}
