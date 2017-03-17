package com.ecard.alipay.tool;

import com.ecard.config.StaticValue;


/**
 * 支付宝配置文件类
 * @author dinghongxing
 *
 */
public class AlipayConfig {
	
//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	// 合作身份者ID，签约账号，以2088开头由16位纯数字组成的字符串，查看地址：https://b.alipay.com/order/pidAndKey.htm
	public static String partner = "2088221665865610";
	
	// 收款支付宝账号，以2088开头由16位纯数字组成的字符串，一般情况下收款账号就是签约账号
	public static String seller_id = partner;

	//商户的私钥,需要PKCS8格式，RSA公私钥生成：https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.nBDxfy&treeId=58&articleId=103242&docType=1
	public static String private_key = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAONiO1cVl0bja13buzmd+3ehX8JwtUQx+aiJPqG/1OaY2ppePUYp49eW6NCVGBg9jDxQcSCJaTSLzczFrWN1BVad68wiXUpSVVEqmXzGH7cSVXLQEKOp+J41wUZfGvVc4P7/gjEy0muGCwa9Dzl2APbVGZjGS1zY4Bc66Gjm8yv/AgMBAAECgYEAkH1CcfMRXwVym4Dedx1xxzIvdJJETWJoX/VebFBHDIWDG+bYjvZdhwipRqoHVpf9vfHsAR3wUWipTP8sT+/zhEU++1oNzFgdbeA46rzuMtEgy9k10Y117I4u/8uZpL+z6hWINoQeAK66dtGfks2JC+yshuI7kaDAp85EMCzofdECQQD2vpSuepNojHEwBEucn4PPtWTFht8CZIx8myFEKH6ci3aRgtQvDqWMUWGTASrHCfaJJRFgbxeECfO7C7d4GOqZAkEA6+m8M3+62qbTXe9fJOJ2+DjQFWJEi5l9r4/mxN1/8gSQOLldaxu0PhMwIidCHDhLOgI9Z9MjDw1AQavCOgBCVwJAckge8RkQwTGbExRKOQagtEldnSc0jxUnBGeNZtYinjrx9b9Uq17rSiw/NykpOPriVu72N2X8zEkXvCBXH6basQJASqlFc7L36t0jRbZNETpO1o38ZNgdw2SHX3mWWjWTmX+SOTGI/C3fZEnkUNrF1HKIdEpjLqmmjcvuyxnVtU+BrwJBALs+LTVRxwtc2ks3ThA3XQLXDbKletuuCt1dP0uE6kz4Dha7hyu93XXn4GZGXlMnVFbX4AgCH16sZiCVuO3HNVE=";
	
	// 支付宝的公钥,查看地址：https://b.alipay.com/order/pidAndKey.htm
	public static String alipay_public_key  = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
	// 签名方式
	public static String sign_type = "RSA";
	
	// 调试用，创建TXT日志文件夹路径，见AlipayCore.java类中的logResult(String sWord)打印方法。
	public static String log_path = "C:\\";
		
	// 字符编码格式 目前支持 gbk 或 utf-8
	public static String input_charset = "utf-8";
		
	// 支付类型 ，无需修改
	public static String payment_type = "1";
		
	// 调用的接口名，无需修改
	public static String service = "create_direct_pay_by_user";
	
	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问(充值异步回调路径)
	public static String recharge_callback_url = StaticValue.PROJECT_ROOT_PATH + "callback/callbackRechargeByalipay";


	
//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
	
//↓↓↓↓↓↓↓↓↓↓ 请在这里配置防钓鱼信息，如果没开通防钓鱼功能，为空即可 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	
	// 防钓鱼时间戳  若要使用请调用类文件submit中的query_timestamp函数
	public static String anti_phishing_key = "";
	
	// 客户端的IP地址 非局域网的外网IP地址，如：221.0.0.1
	public static String exter_invoke_ip = "";
		
//↑↑↑↑↑↑↑↑↑↑请在这里配置防钓鱼信息，如果没开通防钓鱼功能，为空即可 ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
	
}

