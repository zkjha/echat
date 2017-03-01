package com.ecard.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.ecard.entity.MemberexpandinformationEntity;
import com.ecard.service.MemberexpandinformationService;
/**
 * 会员拓展资料信息控制层
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/admin/biz/memberexpandinformation")
public class MemberexpandinformationController {
	
	@Resource
	private MemberexpandinformationService memberexpandinformationService;
	

	/**
	 * 新增拓展资料信息
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("insertMemberexpandinformation")
	public String insertMemberexpandinformation(HttpServletRequest request, HttpServletResponse response) {
		String strInformationname = request.getParameter("strInformationname");
		String strIsmust = request.getParameter("strIsmust");
		String strType = request.getParameter("intType");
		if(ValidateTool.isEmptyStr(strInformationname)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "拓展资料名称不能为空", null);
		}
		if(ValidateTool.isEmptyStr(strIsmust)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "是否必填不能为空", null);
		}
		if(ValidateTool.isEmptyStr(strType)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "类型不能为空", null);
		}
		try {
			MemberexpandinformationEntity memberexpandinformationEntity = new MemberexpandinformationEntity();
			memberexpandinformationEntity.setStrInformationid(DataTool.getUUID());
			memberexpandinformationEntity.setStrInformationname(strInformationname.trim());
			memberexpandinformationEntity.setIntIsmust(Integer.valueOf(strIsmust));
			memberexpandinformationEntity.setIntType(Integer.valueOf(strType));
			memberexpandinformationEntity.setStrInserttime(DateTool.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
			memberexpandinformationService.insertMemberexpandinformation(memberexpandinformationEntity);
			return DataTool.constructResponse(ResultCode.OK, "新增成功", null);
		} catch (Exception e) {
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
		}
	}
	
	/**
	 * 根据拓展资料ID查询拓展资料信息
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getMemberexpandinformationById")
	public String getMemberexpandinformationById(HttpServletRequest request, HttpServletResponse response) {
		String strInformationid = request.getParameter("strInformationid");
		if(ValidateTool.isEmptyStr(strInformationid)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "拓展资料ID不能为空", null);
		}
		try {
			MemberexpandinformationEntity memberexpandinformationEntity = memberexpandinformationService.getMemberexpandinformationById(strInformationid);
			return DataTool.constructResponse(ResultCode.OK, "查询成功", memberexpandinformationEntity);
		} catch (Exception e) {
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
		}
	}
	
	/**
	 * 修改拓展资料
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("updateMemberexpandinformation")
	public String updateMemberexpandinformation(HttpServletRequest request, HttpServletResponse response) {
		String strInformationid = request.getParameter("strInformationid");
		String strInformationname = request.getParameter("strInformationname");
		String strIsmust = request.getParameter("strIsmust");
		if(ValidateTool.isEmptyStr(strInformationid)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "拓展资料ID不能为空", null);
		}
		if(ValidateTool.isEmptyStr(strInformationname)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "拓展资料名称不能为空", null);
		}
		if(ValidateTool.isEmptyStr(strIsmust)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "是否必填不能为空", null);
		}
		try {
			MemberexpandinformationEntity memberexpandinformationEntity = new MemberexpandinformationEntity();
			memberexpandinformationEntity.setStrInformationid(strInformationid);
			memberexpandinformationEntity.setStrInformationname(strInformationname.trim());
			memberexpandinformationEntity.setIntIsmust(Integer.valueOf(strIsmust));
			memberexpandinformationEntity.setStrUpdatetime(DateTool.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
			memberexpandinformationService.updateMemberexpandinformation(memberexpandinformationEntity);
			return DataTool.constructResponse(ResultCode.OK, "修改成功", null);
		} catch (Exception e) {
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
		}
	}
	
	/**
	 * 查询拓展资料列表
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("listMemberexpandinformation")
	public String listMemberexpandinformation(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			Map<String,Object> queryMap = new HashMap<String, Object>();
			List<MemberexpandinformationEntity> memberexpandinformationEntity = memberexpandinformationService.listMemberexpandinformation(queryMap);
			Map<String,Object> resultMap = new HashMap<String, Object>();
			resultMap.put("memberexpandinformationEntity", memberexpandinformationEntity);
			return DataTool.constructResponse(ResultCode.OK, "查询成功", resultMap);
		} catch (Exception e) {
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
		}
		
	}
	
	
}
