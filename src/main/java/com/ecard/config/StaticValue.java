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
	 * cookie name
	 */
	public static final String COOKIENAME ="ecardid";
	/**
	 * cookie domain
	 */
	public static final String DOMAIN="fushoukeji.com";
	/**
	 * 文件上传根路径
	 */
	public static final String FILE_UPLOAD_URL = "/mnt/picpath/";
	/**
	 * 图片根路径
	 */
	public static final String IMAGE_ROOT_PATH = "http://linux.fushoukeji.com:88/picpath/";
	/**
	 * 项目根路径
	 */
	public static final String PROJECT_ROOT_PATH = "http://local.fushoukeji.com:8080/";
	/**
	 * 分页默认每页显示条数
	 */
	public static final int PAGE_SIZE = 10;
	
}
