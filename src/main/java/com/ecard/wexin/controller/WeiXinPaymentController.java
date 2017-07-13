package com.ecard.wexin.controller;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.ecard.entity.ProvinceEntity;
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
	
	//快递收货方式--生成订单
	@RequestMapping("generateWeiXinOrderExpress")
	@ResponseBody
	//localhost:8083/weixin/biz/generateWeiXinOrderExpress?strGoodsId=84848johoghgh&iPurchaseAmount=2&strReceiveName=test&strPhone=15123876442&strPostalCode=401132&strReceiveAddress=重庆市渝北区茨竹镇
	public String generateWeiXinOrderExpress(HttpServletRequest request,HttpServletResponse response)
	{
		// 当前登录的用户信息
		String strMemberId="";
		int iPurchaseAmount;
		int iStatus=0;//待支付状态
		int iPayStandard=0;//支付标准：0 会员价（优惠价)支付,1原价支付
		int iReceiveType=0;//快递方式
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
		String strOrderId=DataTool.getUUID();
		if(ValidateTool.isEmptyStr(strGoodsId))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"商品ID不能为空",null);
		
		if(ValidateTool.isEmptyStr(strPurchaseAmount))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"购买数量不能为空",null);
		else
			iPurchaseAmount=Integer.parseInt(strPurchaseAmount);
	
		String strCreationTime=DateTool.DateToString(new Date(),DateStyle.YYYY_MM_DD_HH_MM);
		String strLastAccessedTime=DateTool.DateToString(new Date(),DateStyle.YYYY_MM_DD_HH_MM);
		WeiXinReceiveGoodsAddressEntity weiXinReceiveGoodsAddressEntity=null;
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

		//属性组装进对象
		WeiXinGoodsOrderEntity weiXinGoodsOrderEntity=new WeiXinGoodsOrderEntity();
		weiXinGoodsOrderEntity.setStrOrderId(strOrderId);
		weiXinGoodsOrderEntity.setStrMemberId(strMemberId);
		weiXinGoodsOrderEntity.setStrGoodsId(strGoodsId);
		weiXinGoodsOrderEntity.setiPurchaseAmount(iPurchaseAmount);
		weiXinGoodsOrderEntity.setiStatus(iStatus);
		weiXinGoodsOrderEntity.setiPayStandard(iPayStandard);
		weiXinGoodsOrderEntity.setiReceiveType(iReceiveType);
		weiXinGoodsOrderEntity.setStrCreationTime(strCreationTime);
		weiXinGoodsOrderEntity.setStrLastAccessedTime(strLastAccessedTime);

		try{
			return weiXinPaymentService.generateWeiXinOrderExpress(weiXinGoodsOrderEntity,weiXinReceiveGoodsAddressEntity);
		}
		catch(Exception e)
		{	e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
		}
	}
	
	//到店领货 --生成订单
	//localhost:8083/weixin/biz/generateWeiXinOrderToShop?strGoodsId=84848johoghgh&iPurchaseAmount=2&strReceiveTime=2016-08-08 10:00
	@RequestMapping("generateWeiXinOrderToShop")
	@ResponseBody
	public String generateWeiXinOrderToShop(HttpServletRequest request,HttpServletResponse response)
	{
		// 当前登录的用户信息
		String strMemberId="";
		int iPurchaseAmount;
		int iStatus=0;//待支付状态
		int iPayStandard=0;//支付标准：0 会员价（优惠价)支付,1原价支付
		int iReceiveType=1;//到底取货
	
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
		String strReceiveTime=request.getParameter("strReceiveTime");
		String strOrderId=DataTool.getUUID();
		
		if(ValidateTool.isEmptyStr(strGoodsId))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"商品ID不能为空",null);
		
		if(ValidateTool.isEmptyStr(strPurchaseAmount))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"购买数量不能为空",null);
		else
			iPurchaseAmount=Integer.parseInt(strPurchaseAmount);
		
		String strCreationTime=DateTool.DateToString(new Date(),DateStyle.YYYY_MM_DD_HH_MM);
		String strLastAccessedTime=DateTool.DateToString(new Date(),DateStyle.YYYY_MM_DD_HH_MM);
		if(ValidateTool.isEmptyStr(strReceiveTime))
				return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "取货时间不能为空", null);
		strReceiveTime=DateTool.StringToString(strReceiveTime, DateStyle.YYYY_MM_DD);
		//属性组装进对象
		WeiXinGoodsOrderEntity weiXinGoodsOrderEntity=new WeiXinGoodsOrderEntity();
		weiXinGoodsOrderEntity.setStrOrderId(strOrderId);
		weiXinGoodsOrderEntity.setStrMemberId(strMemberId);
		weiXinGoodsOrderEntity.setStrGoodsId(strGoodsId);
		weiXinGoodsOrderEntity.setiPurchaseAmount(iPurchaseAmount);
		weiXinGoodsOrderEntity.setiStatus(iStatus);
		weiXinGoodsOrderEntity.setiPayStandard(iPayStandard);
		weiXinGoodsOrderEntity.setiReceiveType(iReceiveType);
		weiXinGoodsOrderEntity.setStrReceiveTime(strReceiveTime);
		weiXinGoodsOrderEntity.setStrCreationTime(strCreationTime);
		weiXinGoodsOrderEntity.setStrLastAccessedTime(strLastAccessedTime);
		weiXinGoodsOrderEntity.setStrReceiveTime(strReceiveTime);
	
		try{
			return weiXinPaymentService.generateWeiXinOrderToShop(weiXinGoodsOrderEntity);
		}
		catch(Exception e)
		{	e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
		}
	}
	
	
	//根据会员 ID查询订单详情
	@ResponseBody
	@RequestMapping("selectWeiXinGoodsOrderEntity")
	//localhost:8083/weixin/biz/selectWeiXinGoodsOrderEntity
	public String selectWeiXinGoodsOrderEntity(HttpServletResponse response,HttpServletRequest request)
	{
		String strMemberId="";
	
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
		WeiXinGoodsOrderEntity weiXinGoodsOrderEntity=null;
		try{
			weiXinGoodsOrderEntity=weiXinPaymentService.selectWeiXinGoodsOrderEntity(strMemberId);
			if(weiXinGoodsOrderEntity==null)
				return DataTool.constructResponse(ResultCode.NO_DATA,"暂无数据",null);
			Map<String,Object> resultMap=new HashMap<String,Object>();
			resultMap.put("weiXinGoodsOrderEntity",weiXinGoodsOrderEntity);
			return DataTool.constructResponse(ResultCode.OK,"查询成功",resultMap);
		}catch(Exception e)
			{
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
			}
	}
	
	//积分支付
	@ResponseBody
	@RequestMapping("payGoodsOrderWithIntegration")
	//localhost:8083/weixin/biz/payGoodsOrderWithIntegration?strOrderId=683f699f16c5417fb0929f3ff60ed086
	public String payGoodsOrderWithIntegration(HttpServletResponse response,HttpServletRequest request)
	{
		String strOrderId=request.getParameter("strOrderId");
		String strMemberId="";

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
		
		if(ValidateTool.isEmptyStr(strOrderId))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"订单号不能为空",null);
		Map<String,Object> queryMap=new HashMap<String,Object>();
		queryMap.put("strOrderId",strOrderId);
		queryMap.put("strMemberId",strMemberId);
		try{
			return weiXinPaymentService.payGoodsOrderWithIntegration(queryMap);
		}catch(Exception e)
		{
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
		}
	}
	
	
	//会员卡余额 支付
	@ResponseBody
	@RequestMapping("payGoodsOrderWithCardCash")
	//localhost:8083/weixin/biz/payGoodsOrderWithCardCash?strOrderId=683f699f16c5417fb0929f3ff60ed086
	public String payGoodsOrderWithCardCash(HttpServletResponse response,HttpServletRequest request)
	{
		String strOrderId=request.getParameter("strOrderId");
		String strMemberId="";

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
	
		if(ValidateTool.isEmptyStr(strOrderId))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"订单号不能为空",null);
		Map<String,Object> queryMap=new HashMap<String,Object>();
		queryMap.put("strOrderId",strOrderId);
		queryMap.put("strMemberId",strMemberId);
		try{
			return weiXinPaymentService.payGoodsOrderWithCardCash(queryMap);
		}catch(Exception e)
		{
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
		}
	}

	//查询全国省份--列表 
	@ResponseBody
	@RequestMapping("selectProvince")
	//localhost:8083/weixin/biz/selectProvince
	public String selectProvince(HttpServletResponse response,HttpServletRequest request)
	{
		try{
		List<ProvinceEntity> listProvinceEntity=weiXinPaymentService.selectProvince();
		if(listProvinceEntity==null)
			return DataTool.constructResponse(ResultCode.NO_DATA,"暂无数据",null);
		if(listProvinceEntity.size()==0)
			return DataTool.constructResponse(ResultCode.NO_DATA,"暂无数据",null);
		Map<String,Object> resultMap=new HashMap<String,Object>();
		resultMap.put("listProvinceEntity",listProvinceEntity);
		return DataTool.constructResponse(ResultCode.OK,"查询成功",listProvinceEntity);
		}catch(Exception e)
		{
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
		}
	}
	
	
	//根据省份代码查询所有城市--列表
	@ResponseBody
	@RequestMapping("selectCityInfo")
	//localhost:8083/weixin/biz/selectCityInfo?iProvinceCode=450000
	public String selectCityInfo(HttpServletResponse response,HttpServletRequest request)
	{
		int iProvinceCode=0;	
		String strProvinceCode=request.getParameter("iProvinceCode");
		if(ValidateTool.isEmptyStr(strProvinceCode))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"省份代码不能为空",null);
		
		if(isNumber(strProvinceCode))
			iProvinceCode=Integer.parseInt(strProvinceCode);
		else
			return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"省份代码格式错误",null);
		
		try{
			return weiXinPaymentService.selectCityInfo(iProvinceCode);
			
		}catch(Exception e)
		{
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
		}
	}
	
	
	//根据城市代码查询所有区县--列表
	@ResponseBody
	@RequestMapping("selectAreaCountyInfo")
	//localhost:8083/weixin/biz/selectAreaCountyInfo?iCityCode=500100
	public String selectAreaCountyInfo(HttpServletResponse response,HttpServletRequest request)
	{
		int iCityCode=0;
		String strCityCode=request.getParameter("iCityCode");
		if(ValidateTool.isEmptyStr(strCityCode))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"城市代码不能为空",null);
		
		if(isNumber(strCityCode))
			iCityCode=Integer.parseInt(strCityCode);
		else
			return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"城市代码格式错误",null);
		
		try{
			return weiXinPaymentService.selectAreaCountyInfo(iCityCode);
				
		}catch(Exception e)
		{
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
		}
	}
	
	
	//查询商家店铺地址
	@ResponseBody
	@RequestMapping("selectMerchantAddress")
	//localhost:8083/weixin/biz/selectMerchantAddress
	public String selectMerchantAddress(HttpServletResponse response,HttpServletRequest request)
	{
		try{
			String strAddress=weiXinPaymentService.selectMerchantAddress();
			Map<String,String> resultMap=new HashMap<String,String>();
			resultMap.put("strAddress",strAddress);
			return  DataTool.constructResponse(ResultCode.OK,"查询成功",resultMap);
			}catch(Exception e)
		{
				e.printStackTrace();
				return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
			}
		
	}
	
	
	//支付成功后的查询
	@ResponseBody
	@RequestMapping("selectPayOverInfo")
	//localhost:8083/weixin/biz/selectPayOverInfo?strOrderId=f6a69354b9fd4c28be6c5acf88c094ac
	public String selectPayOverInfo(HttpServletResponse response,HttpServletRequest request)
	{
		String strOrderId=request.getParameter("strOrderId");
		if(ValidateTool.isEmptyStr(strOrderId))
			return  DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"订单号不能为空",null);
		try{
			WeiXinGoodsOrderEntity weiXinGoodsOrderEntity=weiXinPaymentService.selectPayOverInfo(strOrderId);
			if(weiXinGoodsOrderEntity==null)
				return DataTool.constructResponse(ResultCode.NO_DATA,"暂无数据",null);
			int iPayType=weiXinGoodsOrderEntity.getiPayType();
			String strPayType="";
			//0：积分兑换 1：微信支付 2：支付宝支付,3现金支付,4会员卡余额支付
			switch (iPayType)
			{
			case 0:
				strPayType="积分兑换";
				break;
			case 1:
				strPayType="微信支付";
				break;
			case 2:
				strPayType="支付宝支付";
				break;
			case 3:
				strPayType="现金支付";
				break;
			case 4:
				strPayType="会员卡余额支付";
				break;
			}
			Map<String,Object> resultMap=new HashMap<String,Object>();
			resultMap.put("strOrderId",weiXinGoodsOrderEntity.getStrOrderId());
			resultMap.put("strPayType",strPayType);
			resultMap.put("strThirdPartyTradeFlow", weiXinGoodsOrderEntity.getStrThirdPartyTradeFlow());
			resultMap.put("strPayTime",weiXinGoodsOrderEntity.getStrPayTime());
			return DataTool.constructResponse(ResultCode.OK,"查询成功",resultMap);
		}catch(Exception e)
		{
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);	
		}
		
	}
	
	
	//校验
		public static boolean isNumber(String strCheckString)
		{
			boolean flag=false;
			Pattern p1 = Pattern.compile("^[0-9]\\d*$|^[1-9]\\d*\\.\\d{1,2}$|^0\\.\\d{1,2}$");
			Matcher matcher=p1.matcher(strCheckString);
			flag=matcher.matches();   
			return flag;
		}
	
}
