package com.ecard.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.commontools.validate.ValidateTool;
import com.commontools.xml.XmlTool;
import com.ecard.alipay.tool.AlipayConfig;
import com.ecard.alipay.tool.AlipayNotify;
import com.ecard.entity.RechargeOrderEntity;
import com.ecard.service.RechargeOrderService;
import com.ecard.wechant.tool.WechantConfig;
import com.ecard.wechant.tool.WechantSign;
/**
 * 支付成功回调
 * @author dinghongxing
 *
 */
@Controller
@RequestMapping("/callback")
public class CallbackController {
	
	
	@Resource
	private  RechargeOrderService rechargeOrderService;
	
	/**
	 * 现金充值支付宝支付成功回调
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("callbackRechargeByalipay")
	public String callbackRechargeByalipay(HttpServletRequest request) {
		//获取支付宝POST过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			params.put(name, valueStr);
		}
		
		//商户订单号
		String out_trade_no = request.getParameter("out_trade_no");
		//支付宝交易号
		String trade_no = request.getParameter("trade_no");
		//交易状态
		String trade_status = request.getParameter("trade_status");
		//商户号
		String seller_id = request.getParameter("seller_id");
		//订单金额
		String total_fee = request.getParameter("total_fee");
		if(AlipayNotify.verify(params)){//验证成功
			//////////////////////////////////////////////////////////////////////////////////////////
			if("TRADE_FINISHED".equals(trade_status)||"TRADE_SUCCESS".equals(trade_status)){
				try {
					RechargeOrderEntity rechargeOrderEntity = rechargeOrderService.getRechargeOrderById(out_trade_no);
					if(ValidateTool.isNull(rechargeOrderEntity)) {
						//订单不存在
						return "fail";
					} else {
						
						if(1==rechargeOrderEntity.getIntStatus()) {
							//当前订单状态为支付成功，说明已经回调过
							return "success";
						} else {
							if(!AlipayConfig.partner.equals(seller_id)) {
								//请求时的商户号和通知时的商户号不一致
								return "fail";
							} else {
								//判断金额
								int passback_amount = (int)(Double.parseDouble(total_fee)*100);  //回传回来的金额*100
								BigDecimal bAmount = rechargeOrderEntity.getdBalance(); //订单的金额
								int order_amount = bAmount.multiply(new BigDecimal(100)).intValue(); //订单的金额*100
								if(passback_amount==order_amount) {
									//回传金额和订单金额相等
									rechargeOrderService.callbackRechargeOrder(rechargeOrderEntity,trade_no,2);
									return "success";
								} else {
									//回传金额和订单金额不相等
									return "fail";
								}
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					return "fail";
				}
			} else {
				return "success";
			}
		}else{//验证失败
			return "fail";
		}
	}
	
	/**
	 * 现金充值微信支付成功回调
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("callbackRechargeBywechant")
    public void callbackRechargeBywechant(HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object> returnMap = new HashMap<String, Object>();
		try {
			StringBuffer sb = new StringBuffer();
			InputStream is = request.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);   
			BufferedReader br = new BufferedReader(isr); 
			String s = "" ; 
			while((s=br.readLine())!=null){ 
				sb.append(s) ; 
			} 
			String result_str = sb.toString(); 
			Map<String,Object> map = XmlTool.xmlElements(result_str);
			String sign = (String)map.get("sign"); //取得传回来的签名
			map.remove("sign"); //把签名字段去掉
			String own_sign = WechantSign.getpaySign(map, WechantConfig.WX_APIKEY); //按照规则重新生成签名
			//判断签名
			if(sign.equals(own_sign)) {
				//重新生成的签名和微信回传的签名相同，表明是微信在回调
				String out_trade_no = (String)map.get("out_trade_no");  //商户订单号
				String transaction_id = (String)map.get("transaction_id"); //微信支付订单号
				String return_code = (String)map.get("return_code"); //支付结果
				String total_fee = (String)map.get("total_fee"); //支付金额 
				if ("SUCCESS".equals(return_code)) {
					//付款成功，订单状态进行修改
					
					try {
						RechargeOrderEntity rechargeOrderEntity = rechargeOrderService.getRechargeOrderById(out_trade_no);
						if(ValidateTool.isNull(rechargeOrderEntity)) {
							//痛点订单不存在
							returnMap.put("return_code", "FAIL");
						} else {
							if(1==rechargeOrderEntity.getIntStatus()) {
								//当前订单状态为支付成功或者已经使用，说明已经回调过
								returnMap.put("return_code", "SUCCESS");
							} else {
								//判断金额
								int passback_amount = Integer.parseInt(total_fee);  //回传回来的金额，单位为分
								BigDecimal bAmount = rechargeOrderEntity.getdBalance(); //订单的金额
								int order_amount = bAmount.multiply(new BigDecimal(100)).intValue(); //订单的金额*100，单位为分
								if(passback_amount==order_amount) {
									//回传金额和订单金额相等
									rechargeOrderService.callbackRechargeOrder(rechargeOrderEntity,transaction_id,1);
									returnMap.put("return_code", "SUCCESS");
								} else {
									//回传金额和订单金额不相等
									returnMap.put("return_code", "FAIL");
								}
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
						returnMap.put("return_code", "FAIL");
					}
				} else {
					//支付失败
					returnMap.put("return_code", "SUCCESS");
				}
			} else {
				returnMap.put("return_code", "FAIL");
			}
		}  catch (Exception e) {
			returnMap.put("return_code", "FAIL");
		} 
		String xmlResult = XmlTool.mapToXml(returnMap); //给微信回调结果通知
		try {
			response.getWriter().print(xmlResult);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
