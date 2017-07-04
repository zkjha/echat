package com.ecard.wexin.controller;

import java.math.BigDecimal;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.commontools.data.DataTool;
import com.commontools.date.DateStyle;
import com.commontools.date.DateTool;
import com.commontools.validate.ValidateTool;
import com.ecard.config.ResultCode;
import com.ecard.entity.WeiXinGoodsOrderEntity;
import com.ecard.entity.WeiXinReceiveGoodsAddressEntity;
import com.ecard.service.WeiXinPaymentService;
import com.ecard.util.WebSessionUtil;

@Controller
@RequestMapping("/weixin/biz/")
public class WeiXinPaymentController
{
	@Autowired 
	private WebSessionUtil webSessionUtil;
	@Autowired
	WeiXinPaymentService weiXinPaymentService;
	
	
	@RequestMapping("generateWeiXinOrder")
	@ResponseBody
	//localhost:8083/weixin/biz/generateWeiXinOrder?strGoodsId=84848johoghgh&dPreferentialPrice=390.33&iPurchaseAmount=2&strGoodsName=汽车空调&iReceiveType=0&strReceiveTime=&iPayType=0&strUnitName=套&iPurchaseIntegrationAmount=20000&iIntegrationAmount=10000
	//如果是快递收货方式则增加以下参数
	//&strReceiveName=test&strPhone=15123876442&strPostalCode=38475002&strReceiveAddress=重庆市渝北区茨竹镇
	public String generateWeiXinOrder(HttpServletRequest request,HttpServletResponse response)
	{
		// 当前登录的用户信息
		String strMemberId="";
		int iPurchaseAmount;
		int iIntegrationAmount=0;
		int iPurchaseIntegrationAmount;
		int iStatus=0;//待支付状态
		int iPayStandard=0;//支付标准：0 会员价（优惠价)支付,1原价支付
		int iPayType;
		int iReceiveType;
		BigDecimal dPreferentialPrice;
		BigDecimal dPreferentialCashTotalAmount;
		/*
		try{
			strMemberId = (String) webSessionUtil.getWeixinSession(request, response).getAttribute("memberid");
		}catch(Exception e)
		{
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
		}
		*/	
		//以下会员ID为测试数据

		strMemberId="377f37a5871f4874a2879dd77758e075";
		if(ValidateTool.isEmptyStr(strMemberId))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"请重新登录",null);
		
		String strOrderId=DataTool.getUUID();
		String strGoodsId=request.getParameter("strGoodsId");
		String strPreferentialPrice=request.getParameter("dPreferentialPrice");
		String strPurchaseAmount=request.getParameter("iPurchaseAmount");
		String strGoodsName=request.getParameter("strGoodsName");
		String strReceiveType=request.getParameter("iReceiveType");
		String strReceiveTime=request.getParameter("strReceiveTime");
		String strPayType=request.getParameter("iPayType");
		String strUnitName=request.getParameter("strUnitName");
		String strPurchaseIntegrationAmount=request.getParameter("iPurchaseIntegrationAmount");
		String strIntegrationAmount=request.getParameter("iIntegrationAmount");
		if(ValidateTool.isEmptyStr(strIntegrationAmount))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"兑换商品的积分不能为空",null);
		else
			iIntegrationAmount=Integer.parseInt(strIntegrationAmount);	
		
		if(ValidateTool.isEmptyStr(strPurchaseIntegrationAmount))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"兑换商品的总积分不能为空",null);
		else
			iPurchaseIntegrationAmount=Integer.parseInt(strPurchaseIntegrationAmount);	
		
		if(ValidateTool.isEmptyStr(strGoodsId))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"商品ID不能为空",null);
		
		if(ValidateTool.isEmptyStr(strPreferentialPrice))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"商品会员价不能为空",null);
		else
			dPreferentialPrice=new BigDecimal(strPreferentialPrice);
		
		if(ValidateTool.isEmptyStr(strPurchaseAmount))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"购买数量不能为空",null);
		else
			iPurchaseAmount=Integer.parseInt(strPurchaseAmount);
		
		if(ValidateTool.isEmptyStr(strReceiveType))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"收货方式不能为空",null);
		else
			iReceiveType=Integer.parseInt(strReceiveType);
		
		if(ValidateTool.isEmptyStr(strPayType))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"支付方式不能为空",null);
		else
			iPayType=Integer.parseInt(strPayType);
		
		if(ValidateTool.isEmptyStr(strUnitName))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"计量单位不能为空",null);
		
		String strCreationTime=DateTool.DateToString(new Date(),DateStyle.YYYY_MM_DD_HH_MM);
		String strLastAccessedTime=DateTool.DateToString(new Date(),DateStyle.YYYY_MM_DD_HH_MM);
		String strPayTime=DateTool.DateToString(new Date(),DateStyle.YYYY_MM_DD_HH_MM);
		
		dPreferentialCashTotalAmount=dPreferentialPrice.multiply(new BigDecimal(iPurchaseAmount));
		WeiXinReceiveGoodsAddressEntity weiXinReceiveGoodsAddressEntity=null;
		if(iReceiveType==0)//快递收货方式
		{
			//取得收件人的详情
			//获取参数
			String strRecordId=DataTool.getUUID();
			String strReceiveName=request.getParameter("strReceiveName");
			String strPhone=request.getParameter("strPhone");
			String strPostalCode=request.getParameter("strPostalCode");
			String strReceiveAddress=request.getParameter("strReceiveAddress");
			String strInsertTime=DateTool.DateToString(new Date(),DateStyle.YYYY_MM_DD_HH_MM);
			//检测字符串有效性
			if(ValidateTool.isEmptyStr(strReceiveName))
				return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "收件人姓名不能为空", null);
			if(ValidateTool.isEmptyStr(strPhone))
				return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "收件人电话不能为空", null);
			if(ValidateTool.isTelephoneOrMobile(strPhone)==false)
				return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR, "电话号码格式错误!", null);
			if(ValidateTool.isEmptyStr(strReceiveAddress))
				return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "收件人地址不能为空", null);
			if(!ValidateTool.isEmptyStr(strPostalCode))
				if(ValidateTool.isPostCode(strPostalCode)==false)
					return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR, "邮编格式错误", null);
			weiXinReceiveGoodsAddressEntity=new WeiXinReceiveGoodsAddressEntity();
			weiXinReceiveGoodsAddressEntity.setStrRecordId(strRecordId);
			weiXinReceiveGoodsAddressEntity.setStrReceiveName(strReceiveName);
			weiXinReceiveGoodsAddressEntity.setStrPhone(strPhone);
			weiXinReceiveGoodsAddressEntity.setStrPostalCode(strPostalCode);
			weiXinReceiveGoodsAddressEntity.setStrReceiveAddress(strReceiveAddress);
			weiXinReceiveGoodsAddressEntity.setStrMemberId(strMemberId);
			weiXinReceiveGoodsAddressEntity.setStrOrderId(strOrderId);
			weiXinReceiveGoodsAddressEntity.setStrInsertTime(strInsertTime);
			
		}
		//属性组装进对象
		WeiXinGoodsOrderEntity weiXinGoodsOrderEntity=new WeiXinGoodsOrderEntity();
		weiXinGoodsOrderEntity.setStrOrderId(strOrderId);
		weiXinGoodsOrderEntity.setStrMemberId(strMemberId);
		weiXinGoodsOrderEntity.setStrGoodsId(strGoodsId);
		weiXinGoodsOrderEntity.setStrGoodsName(strGoodsName);
		weiXinGoodsOrderEntity.setiPurchaseAmount(iPurchaseAmount);
		weiXinGoodsOrderEntity.setStrUnitName(strUnitName);
		weiXinGoodsOrderEntity.setdPreferentialPrice(dPreferentialPrice);
		weiXinGoodsOrderEntity.setdPreferentialCashTotalAmount(dPreferentialCashTotalAmount);
		weiXinGoodsOrderEntity.setiIntegrationAmount(iIntegrationAmount);
		weiXinGoodsOrderEntity.setiPurchaseIntegrationAmount(iPurchaseIntegrationAmount);
		weiXinGoodsOrderEntity.setiStatus(iStatus);
		weiXinGoodsOrderEntity.setiPayStandard(iPayStandard);
		weiXinGoodsOrderEntity.setStrPayTime(strPayTime);
		weiXinGoodsOrderEntity.setiPayType(iPayType);
		weiXinGoodsOrderEntity.setiReceiveType(iReceiveType);
		weiXinGoodsOrderEntity.setStrReceiveTime(strReceiveTime);
		weiXinGoodsOrderEntity.setStrCreationTime(strCreationTime);
		weiXinGoodsOrderEntity.setStrLastAccessedTime(strLastAccessedTime);
	
		try{
			return weiXinPaymentService.generateWeiXinOrder(weiXinGoodsOrderEntity,weiXinReceiveGoodsAddressEntity);
		}
		catch(Exception e)
		{	e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
		}
	}
		
	
}
