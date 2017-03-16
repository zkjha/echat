package com.ecard.alipay.tool;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import com.commontools.validate.ValidateTool;


/* *
 *类名：AlipayTool
 *功能：参数签名
 */

public class AlipayTool {
    
	/**
	 * 加密方式
	 */
	private static final String ALGORITHM = "RSA";
	/**
	 * 签名方式
	 */
	private static final String SIGN_ALGORITHMS = "SHA1WithRSA";
	/**
	 * 默认编码
	 */
	private static final String DEFAULT_CHARSET = "UTF-8";
	
	/**
	 * 
	 * @功能： 进行参数签名，并返回表单参数
	 * @作者：dinghongxing
	 * @文件名：AlipayTool.java 
	 * @包名：com.alipay.util 
	 * @项目名：tongkeapi
	 * @部门：伏守科技项目开发部
	 * @日期：2016年4月15日 下午8:39:44 
	 * @版本：V1.0 
	 * @param out_trade_no 商户订单号，商户网站订单系统中唯一订单号，必填
	 * @param subject  产品主题，必填
	 * @param total_fee  付款金额，必填，单位元，精确到小数点后两位
	 * @param body 商品描述，可空
	 * @param deadtime 支付失效时间
	 * @return
	 */
	public static String tradePrecreate(String out_trade_no, String subject, 
			                                String total_fee, String body,
			                                  String notify_url, String return_url, String deadtime) {
		
		//把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", AlipayConfig.service);
        sParaTemp.put("partner", AlipayConfig.partner);
        sParaTemp.put("seller_id", AlipayConfig.seller_id);
        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
		sParaTemp.put("payment_type", AlipayConfig.payment_type);
		sParaTemp.put("notify_url", notify_url);
		sParaTemp.put("return_url", return_url);
		sParaTemp.put("anti_phishing_key", AlipayConfig.anti_phishing_key);
		sParaTemp.put("exter_invoke_ip", AlipayConfig.exter_invoke_ip);
		sParaTemp.put("out_trade_no", out_trade_no);
		sParaTemp.put("subject", subject);
		sParaTemp.put("total_fee", total_fee);
		sParaTemp.put("body", body);
		if(!ValidateTool.isEmptyStr(deadtime)) {
			sParaTemp.put("it_b_pay", deadtime);
		}
		
		//建立请求
		String sHtmlText = AlipaySubmit.buildRequest(sParaTemp,"get","确认");
		return sHtmlText;
	}
	
	/**
	 * 
	 * @功能： 创建订单信息（手机支付）
	 * @作者：dinghongxing
	 * @文件名：AlipayTool.java 
	 * @包名：com.alipay.util 
	 * @项目名：tongkeapi
	 * @部门：伏守科技项目开发部
	 * @日期：2016年4月15日 下午8:39:44 
	 * @版本：V1.0 
	 * @param out_trade_no 商户订单号，商户网站订单系统中唯一订单号，必填
	 * @param subject  产品主题，必填
	 * @param total_fee  付款金额，必填，单位元，精确到小数点后两位
	 * @param body 商品描述，不可空
	 * @param notify_url 异步回调地址
	 * @param return_url 同步回调地址
	 * @return
	 */
	public static String mobileTradePrecreate(String out_trade_no, String subject, 
			                                     String total_fee, String body, 
			                                       String notify_url,String return_url) {
		StringBuffer sb = new StringBuffer();
		// 签约合作者身份ID
		sb.append("partner=" + "\"" + AlipayConfig.partner + "\"");
		// 签约卖家支付宝账号
		sb.append("&seller_id=" + "\"" + AlipayConfig.seller_id + "\"");
		// 商户网站唯一订单号
		sb.append("&out_trade_no=" + "\"" + out_trade_no + "\"");
		// 商品名称
		sb.append("&subject=" + "\"" + subject + "\"");
		// 商品详情
		sb.append("&body=" + "\"" + body + "\"");
		// 商品金额
		sb.append("&total_fee=" + "\"" + total_fee + "\"");
		// 服务器异步通知页面路径
		sb.append("&notify_url=" + "\"" + notify_url + "\"");
		// 服务接口名称， 固定值
		sb.append("&service=\"mobile.securitypay.pay\"");
		// 支付类型， 固定值
		sb.append("&payment_type=\"1\"");
		// 参数编码， 固定值
		sb.append("&_input_charset=\"utf-8\"");
		// 设置未付款交易的超时时间
		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
		sb.append("&it_b_pay=\"30m\"");
		// extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
		// orderInfo += "&extern_token=" + "\"" + extern_token + "\"";
		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
		//sb.append("&return_url=\""+return_url+"\"");
		// 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
		// orderInfo += "&paymethod=\"expressGateway\"";
		String sign = sign(sb.toString(),AlipayConfig.private_key);
		
		try {
			/**
			 * 仅需对sign做URL编码
			 */
			sign = URLEncoder.encode(sign, "UTF-8");
			return sb.toString() + "&sign=\"" + sign + "\"&" + getSignType();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 
	 * @功能：生成签名
	 * @作者：dinghongxing
	 * @文件名：AlipayTool.java 
	 * @包名：com.alipay.util 
	 * @项目名：tongkeapi
	 * @部门：伏守科技项目开发部
	 * @日期：2016年5月3日 上午10:11:15 
	 * @版本：V1.0 
	 * @param content
	 * @param privateKey
	 * @return
	 */
	public static String sign(String content, String privateKey) {
		try {
			PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(
					Base64.decode(privateKey));
			KeyFactory keyf = KeyFactory.getInstance(ALGORITHM);
			PrivateKey priKey = keyf.generatePrivate(priPKCS8);

			java.security.Signature signature = java.security.Signature
					.getInstance(SIGN_ALGORITHMS);

			signature.initSign(priKey);
			signature.update(content.getBytes(DEFAULT_CHARSET));

			byte[] signed = signature.sign();

			return Base64.encode(signed);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @功能：获取签名方式
	 * @作者：dinghongxing
	 * @文件名：AlipayTool.java 
	 * @包名：com.alipay.util 
	 * @项目名：tongkeapi
	 * @部门：伏守科技项目开发部
	 * @日期：2016年5月3日 上午10:10:55 
	 * @版本：V1.0 
	 * @return
	 */
	private static String getSignType() {
		return "sign_type=\"RSA\"";
	}
	
}
