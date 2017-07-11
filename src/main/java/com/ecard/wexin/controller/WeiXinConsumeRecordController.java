package com.ecard.wexin.controller;

import java.util.HashMap;
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
import com.commontools.validate.ValidateTool;
import com.ecard.config.ResultCode;
import com.ecard.config.StaticValue;
import com.ecard.service.WeiXinConsumeRecordService;
import com.ecard.util.WebSessionUtil;

@Controller
@RequestMapping("/weixin/biz/")
public class WeiXinConsumeRecordController {
	@Autowired
	private WeiXinConsumeRecordService weiXinConsumeRecordService;
	@Autowired 
	private WebSessionUtil webSessionUtil;
	//按积分 分页查询消费记录
	@ResponseBody
	@RequestMapping("selectIntegrationConsume")
	//localhost:8083/weixin/biz/selectIntegrationConsume?iPageSize=1&iPageNum=1
	public String selectIntegrationConsume(HttpServletRequest request,HttpServletResponse response)
	{
		String strMemberId="";
		String strPageSize=request.getParameter("iPageSize");
		String strPageNum=request.getParameter("iPageNum");
		int iPageNum=1;
		int iPageSize=0;
	
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
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"请重新登陆",null);
		if(ValidateTool.isEmptyStr(strPageNum))
			iPageNum=1;
		else
		{
			if(isNumber(strPageNum))
				iPageNum=Integer.parseInt(strPageNum);
			else
				return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"起始页数格式错误",null);
		}

		if(ValidateTool.isEmptyStr(strPageSize))
				iPageSize=StaticValue.PAGE_SIZE;
		else
		{	if(isNumber(strPageSize))
				iPageSize=Integer.valueOf(strPageSize);
			else
				return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"每页数量格式错误",null);
		}
		Map<String,Object> queryMap=new HashMap<String,Object>();
		queryMap.put("iPageNum",iPageNum);
		queryMap.put("iPageSize",iPageSize);
		queryMap.put("strMemberId",strMemberId);
		try{
			return weiXinConsumeRecordService.selectIntegrationConsume(queryMap);
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
