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
	//快递收货方式
	//localhost:8083/weixin/biz/generateWeiXinOrder?strGoodsId=84848johoghgh&iPurchaseAmount=2&iReceiveType=0&strReceiveTime=&iPayType=0&strReceiveName=test&strPhone=15123876442&strPostalCode=401132&strReceiveAddress=重庆市渝北区茨竹镇

	//到店领货
	//localhost:8083/weixin/biz/generateWeiXinOrder?strGoodsId=84848johoghgh&iPurchaseAmount=2&iReceiveType=1&strReceiveTime=2017-08-08&iPayType=1
	public String generateWeiXinOrder(HttpServletRequest request,HttpServletResponse response)
	{
		// 当前登录的用户信息
		String strMemberId="";
		int iPurchaseAmount;
		int iStatus=0;//待支付状态
		int iPayStandard=0;//支付标准：0 会员价（优惠价)支付,1原价支付
		int iPayType;
		int iReceiveType;
	
	
		try{
			strMemberId = (String) webSessionUtil.getWeixinSession(request, response).getAttribute("memberid");
		}catch(Exception e)
		{
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
		}
			
		//以下会员ID为测试数据

		//strMemberId="377f37a5871f4874a2879dd77758e075";
		if(ValidateTool.isEmptyStr(strMemberId))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"请重新登录",null);
		//接收参数 
		String strGoodsId=request.getParameter("strGoodsId");
		String strPurchaseAmount=request.getParameter("iPurchaseAmount");
		String strReceiveType=request.getParameter("iReceiveType");
		String strPayType=request.getParameter("iPayType");
		String strReceiveTime=request.getParameter("strReceiveTime");
		
		String strOrderId=DataTool.getUUID();
		
		if(ValidateTool.isEmptyStr(strGoodsId))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"商品ID不能为空",null);
		
		
		
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
		
		String strCreationTime=DateTool.DateToString(new Date(),DateStyle.YYYY_MM_DD_HH_MM);
		String strLastAccessedTime=DateTool.DateToString(new Date(),DateStyle.YYYY_MM_DD_HH_MM);
		String strPayTime=DateTool.DateToString(new Date(),DateStyle.YYYY_MM_DD_HH_MM);
		
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
		else
			{
			if(ValidateTool.isEmptyStr(strReceiveTime))
				return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "取货时间不能为空", null);
			
			}

		//属性组装进对象
		WeiXinGoodsOrderEntity weiXinGoodsOrderEntity=new WeiXinGoodsOrderEntity();
		weiXinGoodsOrderEntity.setStrOrderId(strOrderId);
		weiXinGoodsOrderEntity.setStrMemberId(strMemberId);
		weiXinGoodsOrderEntity.setStrGoodsId(strGoodsId);
		weiXinGoodsOrderEntity.setiPurchaseAmount(iPurchaseAmount);
		weiXinGoodsOrderEntity.setiStatus(iStatus);
		weiXinGoodsOrderEntity.setiPayStandard(iPayStandard);
		weiXinGoodsOrderEntity.setStrPayTime(strPayTime);
		weiXinGoodsOrderEntity.setiPayType(iPayType);
		weiXinGoodsOrderEntity.setiReceiveType(iReceiveType);
		weiXinGoodsOrderEntity.setStrReceiveTime(strReceiveTime);
		weiXinGoodsOrderEntity.setStrCreationTime(strCreationTime);
		weiXinGoodsOrderEntity.setStrLastAccessedTime(strLastAccessedTime);
		weiXinGoodsOrderEntity.setStrReceiveTime(strReceiveTime);
	
		try{
			return weiXinPaymentService.generateWeiXinOrder(weiXinGoodsOrderEntity,weiXinReceiveGoodsAddressEntity);
		}
		catch(Exception e)
		{	e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
		}
	}
		
	
}