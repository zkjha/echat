package com.ecard.config;

public final class ResultCode {
	
	//========================全局请求返回状态标识开始=======================
	/**
	 * 操作成功
	 */
	public static final int OK = 1;
	/**
	 * 用户未登陆
	 */
	public static final int NOT_LOGIN= -1;
	/**
	 * 不能为空
	 */
	public static final int CAN_NOT_NULL = -2;
	/**
	 * 参数类型错误
	 */
	public static final int PARAMER_TYPE_ERROR = -3;
	/**
	 * 未知错误
	 */
	public static final int UNKNOW_ERROR = -4;
	/**
	 * 系统错误
	 */
	public static final int SYSTEM_ERROR = -5;
	/**
	 * 无访问权限
	 */
	public static final int NO_ACCESS_PRIVILEGE = -6;
	/**
	 * 参数超长
	 */
	public static final int TOO_LANG = -7;
	/**
	 * 暂无数据
	 */
	public static final int NO_DATA = -8;
	/**
	 * 前端不需要处理的错误
	 */
	public static final int NOT_NEED_DEAL = -9;
	/**
	 * 系统过期
	 */
	public static final int SYSTEM_OUTDATED = -10;
	//========================全局请求返回状态标识结束=======================
	
	//========================接口返回状态标识开始==========================
	/**
	 * M端登录账户不存在
	 */
	public static final int USER_NOT_EXIST = 100001;
	/**
	 * M端登录密码错误
	 */
	public static final int PASSWORD_IS_WRONG = 100002;
	/**
	 * M端登录账号被禁用
	 */
	public static final int ACCOUNT_IS_FORBID = 100003;
	/**
	 * 上传文件不是指定的图片类型
	 */
	public static final int IS_NOT_PICTURE = 100004;
	/**
	 * 升级激活码无效
	 */
	public static final int IS_INVALIDATION_CODE = 100005;
	/**
	 * 数据库不能重复字段重复
	 */
	public static final int CANTNOTREPEAT_PARAM_ISREPEAT = 100006;
	/**
	 * 手机号格式错误
	 */
	public static final int MOBILE_FORMAT_ERROR = 100007;
	/**
	 * 会员级别已经被使用
	 */
	public static final int LEVELS_HAVE_MEMBERS = 100008;
	/**
	 * 手机号暂不是会员
	 */
	public static final int MOBILE_ISNOT_MEMBER = 100009;
	/**
	 * 验证码失效
	 */
	public static final int CAPTCHA_FAILURE = 100010;
	/**
	 * 验证码错误
	 */
	public static final int CAPTCHA_ERROR = 100011;
	
	
	//========================接口返回状态标识结束==========================
	

}
