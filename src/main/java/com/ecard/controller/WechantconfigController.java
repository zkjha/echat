package com.ecard.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.commontools.data.DataTool;
import com.commontools.date.DateStyle;
import com.commontools.date.DateTool;
import com.commontools.validate.ValidateTool;
import com.ecard.config.ResultCode;
import com.ecard.entity.WechantconfigEntity;
import com.ecard.service.WechantconfigService;
/**
 * 微信配置信息控制层
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/admin/biz/wechantconfig")
public class WechantconfigController {
	
	@Resource
	private WechantconfigService wechantconfigService;
	
	/**
	 * 查询微信配置信息
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getWechantconfig")
	public String getWechantconfig() {
		
		try {
			WechantconfigEntity wechantconfigEntity = wechantconfigService.getOneWechantconfig();
			if(ValidateTool.isNull(wechantconfigEntity)) {
				return DataTool.constructResponse(ResultCode.NO_DATA, "暂无微信配置信息", null);
			}
			return DataTool.constructResponse(ResultCode.OK, "查询成功", wechantconfigEntity);
		} catch (Exception e) {
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
		}
	}
	/**
	 * 修改微信配置信息
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("updateWechantconfig")
	public String updateWechantconfig(HttpServletRequest request, HttpServletResponse response) {
		String strAppid = request.getParameter("strAppid");
		String strAppname = request.getParameter("strAppname");
		String strUrl = request.getParameter("strUrl");
		String strToken = request.getParameter("strToken");
		String strSuthorizationstatus = request.getParameter("strSuthorizationstatus");
		String strEncodingaeskey = request.getParameter("strEncodingaeskey");
		if(ValidateTool.isEmptyStr(strAppid)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "公众号ID不能为空", null);
		}
		if(ValidateTool.isEmptyStr(strAppname)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "公众号账号不能为空", null);
		}
		if(ValidateTool.isEmptyStr(strUrl)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "Url不能为空", null);
		}
		if(ValidateTool.isEmptyStr(strToken)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "Token不能为空", null);
		}
		if(ValidateTool.isEmptyStr(strSuthorizationstatus)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "授权状态不能为空", null);
		}
		if(ValidateTool.isEmptyStr(strEncodingaeskey)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "Key不能为空", null);
		}
		try {
			WechantconfigEntity wechantconfigEntity = new WechantconfigEntity();
			wechantconfigEntity.setStrConfigid(DataTool.getUUID());
			wechantconfigEntity.setStrAppid(strAppid.trim());
			wechantconfigEntity.setStrAppname(strAppname.trim());
			wechantconfigEntity.setStrUrl(strUrl.trim());
			wechantconfigEntity.setStrToken(strToken.trim());
			wechantconfigEntity.setStrEncodingaeskey(strEncodingaeskey.trim());
			wechantconfigEntity.setIntAuthorizationstatus(Integer.valueOf(strSuthorizationstatus));
			wechantconfigEntity.setStrInserttime(DateTool.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
			wechantconfigService.insertWechantconfig(wechantconfigEntity);
			
			return DataTool.constructResponse(ResultCode.OK, "保存成功", null);
		} catch (Exception e) {
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
		}
	}
	
	
}
