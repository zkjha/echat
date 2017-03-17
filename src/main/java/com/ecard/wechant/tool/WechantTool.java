package com.ecard.wechant.tool;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.commontools.data.DataTool;
import com.commontools.xml.XmlTool;
import com.ecard.util.HttpRequestTool;

/**
 * 微信支付进行签名
 * @author dinghongxing
 *
 */
public class WechantTool {

	/**
	 * 生成微信预支付订单(WEB)
	 * @param out_trade_no
	 * @param subject
	 * @param spbill_create_ip
	 * @param total_fee
	 * @param notifyurl
	 * @param tradeType
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> tradePrecreate(String out_trade_no,
			String subject, String spbill_create_ip, int total_fee,
			String notifyurl, String tradeType) throws Exception {

		Map<String, Object> sParaTemp = new HashMap<String, Object>();
		sParaTemp.put("appid", WechantConfig.WX_APPID); // 微信appid
		sParaTemp.put("mch_id", WechantConfig.WX_MCH_ID); // 商户ID
		sParaTemp.put("nonce_str", DataTool.getUUID());// 随机字符串
		sParaTemp.put("body", subject); // 商品描述
		sParaTemp.put("out_trade_no", out_trade_no); // 平台订单号
		sParaTemp.put("total_fee", total_fee); // 订单总金额，单位为分
		sParaTemp.put("spbill_create_ip", spbill_create_ip); // APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP。
		sParaTemp.put("notify_url", notifyurl); // 接收微信支付异步通知回调地址,不能携带参数
		sParaTemp.put("trade_type", tradeType); // 交易类型
												// ，取值如下：JSAPI（公众号支付），NATIVE（原生扫码支付），APP（APP支付）

		String wechant_sign = WechantSign.getpaySign(sParaTemp,
				WechantConfig.WX_APIKEY); // 生成签名
		sParaTemp.put("sign", wechant_sign);
		
		String params = XmlTool.mapToXml(sParaTemp); // 把参数map转化为xml格式的字符串
		// 生成预支付的订单
		String pre_path = WechantConfig.WX_PREPAY_URL; // 微信生成预支付订单接口
		// 微信的返回结果
		String str_result = HttpRequestTool.sendPost(pre_path, params); // 调用微信预支付订单生成接口
		// 处理返回的结果
		Map<String, Object> responseMap = XmlTool.xmlElements(str_result); // 把微信返回的xml结果解析为map

		return responseMap;

	}
	
	/**
	 * 生成微信预支付订单(JSAPI)
	 * @param out_trade_no
	 * @param subject
	 * @param spbill_create_ip
	 * @param total_fee
	 * @param notifyurl
	 * @param tradeType
	 * @param openid
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> tradePrecreate(String out_trade_no,
			String subject, String spbill_create_ip, int total_fee,
			String notifyurl, String tradeType, String openid) throws Exception {

		Map<String, Object> sParaTemp = new HashMap<String, Object>();
		sParaTemp.put("appid", WechantConfig.WX_APPID); // 微信appid
		sParaTemp.put("mch_id", WechantConfig.WX_MCH_ID); // 商户ID
		sParaTemp.put("nonce_str", DataTool.getUUID());// 随机字符串
		sParaTemp.put("body", subject); // 商品描述
		sParaTemp.put("out_trade_no", out_trade_no); // 平台订单号
		sParaTemp.put("total_fee", total_fee); // 订单总金额，单位为分
		sParaTemp.put("spbill_create_ip", spbill_create_ip); // APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP。
		sParaTemp.put("notify_url", notifyurl); // 接收微信支付异步通知回调地址,不能携带参数
		sParaTemp.put("trade_type", tradeType); // 交易类型
												// ，取值如下：JSAPI（公众号支付），NATIVE（原生扫码支付），APP（APP支付）
		sParaTemp.put("openid", openid); // 交易类型
		String wechant_sign = WechantSign.getpaySign(sParaTemp,
				WechantConfig.WX_APIKEY); // 生成签名
		sParaTemp.put("sign", wechant_sign);
		
		String params = XmlTool.mapToXml(sParaTemp); // 把参数map转化为xml格式的字符串
		// 生成预支付的订单
		String pre_path = WechantConfig.WX_PREPAY_URL; // 微信生成预支付订单接口
		// 微信的返回结果
		String str_result = HttpRequestTool.sendPost(pre_path, params); // 调用微信预支付订单生成接口
		// 处理返回的结果
		Map<String, Object> responseMap = XmlTool.xmlElements(str_result); // 把微信返回的xml结果解析为map

		return responseMap;

	}
	
	/**
	 * 公众号支付退款
	 * @param out_trade_no
	 * @param total_fee
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> refund(String out_trade_no, int total_fee) throws Exception {

		Map<String, Object> sParaTemp = new HashMap<String, Object>();
		sParaTemp.put("appid", WechantConfig.WX_APPID); // 微信appid
		sParaTemp.put("mch_id", WechantConfig.WX_MCH_ID); // 商户ID
		sParaTemp.put("nonce_str", DataTool.getUUID());// 随机字符串
		sParaTemp.put("out_trade_no", out_trade_no); // 平台订单号
		sParaTemp.put("out_refund_no", out_trade_no); // 退款订单号
		sParaTemp.put("total_fee", total_fee); // 订单总金额，单位为分
		sParaTemp.put("refund_fee", total_fee); // 订单总金额，单位为分
		sParaTemp.put("op_user_id", WechantConfig.WX_MCH_ID); // 接收微信支付异步通知回调地址,不能携带参数
												// ，取值如下：JSAPI（公众号支付），NATIVE（原生扫码支付），APP（APP支付）
		String wechant_sign = WechantSign.getpaySign(sParaTemp,
				WechantConfig.WX_APIKEY); // 生成签名
		sParaTemp.put("sign", wechant_sign);
		
		String params = XmlTool.mapToXml(sParaTemp); // 把参数map转化为xml格式的字符串
		// 退款接口
		String refund_path = WechantConfig.WX_REFUND_URL; // 微信申请退款接口
		
		String cert = WechantConfig.APICLIENT_CERT_URL; //证书位置
		KeyStore keyStore = KeyStore.getInstance("PKCS12");
		FileInputStream instream = new FileInputStream(new File(cert));
	 	try { 
			//指定PKCS12的密码(商户ID) 
	 		keyStore.load(instream, WechantConfig.WX_MCH_ID.toCharArray()); 
	 	}
	 	finally { 
	 		instream.close();
	 	} 
	 	
	 	SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, WechantConfig.WX_MCH_ID.toCharArray()).build(); //指定TLS版本 
	 	SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory( sslcontext,new String[] { "TLSv1" },null, 
 				SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER); //设置httpclient的SSLSocketFactory 
				 	CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
		// 微信的返回结果
		String str_result = HttpRequestTool.sendPost(httpclient, refund_path, params); // 调用微信退款接口
		// 处理返回的结果
		Map<String, Object> responseMap = XmlTool.xmlElements(str_result); // 把微信返回的xml结果解析为map

		return responseMap;

	}
	
	/**
	 * 查询退款信息
	 * @param out_trade_no
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> queryRefundinfo(String out_trade_no) throws Exception {

		Map<String, Object> sParaTemp = new HashMap<String, Object>();
		sParaTemp.put("appid", WechantConfig.WX_APPID); // 微信appid
		sParaTemp.put("mch_id", WechantConfig.WX_MCH_ID); // 商户ID
		sParaTemp.put("nonce_str", DataTool.getUUID());// 随机字符串
		sParaTemp.put("out_trade_no", out_trade_no); // 平台订单号
		String wechant_sign = WechantSign.getpaySign(sParaTemp,
				WechantConfig.WX_APIKEY); // 生成签名
		sParaTemp.put("sign", wechant_sign);
		
		String params = XmlTool.mapToXml(sParaTemp); // 把参数map转化为xml格式的字符串
		// 查询退款接口
		String query_refund_path = WechantConfig.WX_QUERY_REFUND_URL; // 微信查询退款信息
		
		// 微信的返回结果
		String str_result = HttpRequestTool.sendPost(query_refund_path, params); // 调用微信退款接口
		// 处理返回的结果
		Map<String, Object> responseMap = XmlTool.xmlElements(str_result); // 把微信返回的xml结果解析为map

		return responseMap;

	}
	

}
