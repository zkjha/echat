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
import com.ecard.entity.FrontInformationEntity;
import com.ecard.service.FrontInformationService;
/**
 * 前端注册页面资料信息控制层
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/admin/biz/frontinformation")
public class FrontInformationController {
	
	@Resource
	private FrontInformationService frontInformationService;
	
	/**
	 * 查询前端资料信息
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getFrontinformation")
	public String getFrontinformation() {
		
		try {
			FrontInformationEntity frontInformationEntity = frontInformationService.getOneFrontInformation();
			if(ValidateTool.isNull(frontInformationEntity)) {
				return DataTool.constructResponse(ResultCode.NO_DATA, "暂无前端资料信息", null);
			}
			
			
			return DataTool.constructResponse(ResultCode.OK, "查询成功", frontInformationEntity);
		} catch (Exception e) {
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
		}
	}
	
	/**
	 * 修改前端资料信息
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("updateFrontinformation")
	public String updateFrontinformation(HttpServletRequest request, HttpServletResponse response) {
		String strContent = request.getParameter("strContent");
		if(ValidateTool.isEmptyStr(strContent)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "前端资料类容不能为空", null);
		}
		try {
			FrontInformationEntity frontInformationEntity = new FrontInformationEntity();
			frontInformationEntity.setStrInformationid(DataTool.getUUID());
			frontInformationEntity.setStrContent(strContent.trim());
			frontInformationEntity.setStrInserttime(DateTool.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
			frontInformationService.insertFrontInformation(frontInformationEntity);
			
			return DataTool.constructResponse(ResultCode.OK, "保存成功", null);
		} catch (Exception e) {
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
		}
	}
	
	
}
