package com.ecard.wechant.tool;

import java.io.File;

import com.ecard.config.StaticValue;

/**
 * 微信支付基础配置类，设置帐户有关信息及返回路径
 * @author dinghongxing
 *
 */
public class WechantConfig {
	
	//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	//证书安装位置
	public static final String APICLIENT_CERT_URL = File.separator + "home" + File.separator + "apiclient_cert.p12";
	//微信公众号支付分配的商户号
	public static final String WX_MCH_ID = "1335413101";
	//微信公众号支付签名需要的密码
	public static final String WX_APIKEY = "85515fcdc7b84671b4d8342854d67c91";
	
	//微信分配的公众账号ID
	public static final String WX_APPID = "wx2de0d0ba7356408c";
	//微信公众账号APP_APPSECRET
	public static final String WX_APPSECRET = "9625456fb99841651c4dad70635ae920";
	
	//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
	
	
	//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	//微信支付成功回调地址（微信现金充值购买）
	public static final String RECHARGE_CALLBACK_URL = StaticValue.PROJECT_ROOT_PATH + "callback/callbackRechargeBywechant";
	
	//微信支付生成预支付订单访问路径
	public static final String WX_PREPAY_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	
	//微信申请退款接口
	public static final String WX_REFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
	
	//微信查询退款信息接口
	public static final String WX_QUERY_REFUND_URL = "https://api.mch.weixin.qq.com/pay/refundquery";
	

//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
}

