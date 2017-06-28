package com.ecard.wexin.controller;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
import com.ecard.entity.WeiXinMemberSignEntity;
import com.ecard.service.WeiXinMemberCenterService;
import com.ecard.util.QRCodeTool;
import com.ecard.util.WebSessionUtil;
import com.ecard.vo.MemberVO;

@Controller
@RequestMapping("/weixin/biz/")
public class WeiXinMemberCenterController
{
	@Autowired
	private WeiXinMemberCenterService weiXinMemberCenterService;
	
	@Autowired WebSessionUtil webSessionUtil;
	
	//查询登陆会员信息
	@ResponseBody
	@RequestMapping("selectMemberInfo")
	//localhost:8083/weixin/biz/selectMemberInfo
	public String selectMemberInfo(HttpServletRequest request,HttpServletResponse response)
	{
		try {
			// 当前登录的用户信息
			/*
			String strMemberId = (String) webSessionUtil.getWeixinSession(
					request, response).getAttribute("memberid");
			*/
			//以下会员ID为测试数据
			String strMemberId="377f37a5871f4874a2879dd77758e075";
			if(!ValidateTool.isEmptyStr(strMemberId))
				return weiXinMemberCenterService.selectMemberInfo(strMemberId);
			else
				return DataTool.constructResponse(ResultCode.UNKNOW_ERROR, "未知错误", null);
		} catch (Exception e) {
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
		} 		
	}
	
	//插入会员签到信息
	@ResponseBody
	@RequestMapping("insertMemberSignInfo")
	//localhost:8083/weixin/biz/insertMemberSignInfo
	public String insertMemberSignInfo(HttpServletRequest request,HttpServletResponse response)
	{
		// 当前登录的用户信息
		/*
		String strMemberId = (String) webSessionUtil.getWeixinSession(
				request, response).getAttribute("memberid");
		*/	
		//以下会员ID为测试数据
		String strMemberId="377f37a5871f4874a2879dd77758e075";
		String strSignId=DataTool.getUUID();
		String strSignTime=DateTool.DateToString(new Date(),DateStyle.YYYY_MM_DD_HH_MM_SS);
		WeiXinMemberSignEntity memberSignEntity=new WeiXinMemberSignEntity();
		memberSignEntity.setStrSignId(strSignId);
		memberSignEntity.setStrMemberId(strMemberId);
		memberSignEntity.setStrSignTime(strSignTime);
		try{
			return weiXinMemberCenterService.insertMemberSignInfo(memberSignEntity);
		}catch(Exception e)
		{
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
		}
	}

}

