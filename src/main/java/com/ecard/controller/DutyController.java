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
import com.ecard.entity.DutyEntity;
import com.ecard.entity.DutyPrivilegeEntity;
import com.ecard.service.DutyService;
import com.ecard.util.WebSessionUtil;
/**
 * 职务控制层
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/admin/biz/duty")
public class DutyController {
	
	@Resource
	private DutyService dutyService;
	@Resource
	private WebSessionUtil webSessionUtil;
	
	/**
	 * 新增职务
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("insertDuty")
	public String insertDuty(HttpServletRequest request, HttpServletResponse response) {
		String strDutyname = request.getParameter("strDutyname");
		if(ValidateTool.isEmptyStr(strDutyname)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "职务名称不能为空", null);
		}
		try {
			DutyEntity dutyEntity = new DutyEntity();
			dutyEntity.setStrDutyid(DataTool.getUUID());
			dutyEntity.setStrDutyname(strDutyname.trim());
			dutyEntity.setStrInserttime(DateTool.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
			dutyService.insertDuty(dutyEntity);
			return DataTool.constructResponse(ResultCode.OK, "新增成功", null);
		} catch (Exception e) {
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
		}
	}
	
	/**
	 * 根据职务ID查询职务
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getDutyById")
	public String getDutyById(HttpServletRequest request, HttpServletResponse response) {
		String strDutyid = request.getParameter("strDutyid");
		if(ValidateTool.isEmptyStr(strDutyid)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "职务ID不能为空", null);
		}
		try {
			DutyEntity dutyEntity = dutyService.getDutyById(strDutyid);
			return DataTool.constructResponse(ResultCode.OK, "查询成功", dutyEntity);
		} catch (Exception e) {
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
		}
	}
	
	/**
	 * 修改职务
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("updateDuty")
	public String updateDuty(HttpServletRequest request, HttpServletResponse response) {
		String strDutyid = request.getParameter("strDutyid");
		String strDutyname = request.getParameter("strDutyname");
		if(ValidateTool.isEmptyStr(strDutyid)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "职务ID不能为空", null);
		}
		if(ValidateTool.isEmptyStr(strDutyname)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "职务名称不能为空", null);
		}
		try {
			DutyEntity dutyEntity = new DutyEntity();
			dutyEntity.setStrDutyid(strDutyid);
			dutyEntity.setStrDutyname(strDutyname.trim());
			dutyEntity.setStrUpdatetime(DateTool.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
			dutyService.updateDuty(dutyEntity);
			return DataTool.constructResponse(ResultCode.OK, "新增成功", null);
		} catch (Exception e) {
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
		}
	}
	
	/**
	 * 查询职务列表
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("listDuty")
	public String listDuty(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			Map<String,Object> queryMap = new HashMap<String, Object>();
			List<DutyEntity> dutyEntityList = dutyService.listDuty(queryMap);
			Map<String,Object> resultMap = new HashMap<String, Object>();
			resultMap.put("dutyEntityList", dutyEntityList);
			return DataTool.constructResponse(ResultCode.OK, "查询成功", resultMap);
		} catch (Exception e) {
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
		}
		
	}
	
	/**
	 * 新增职务权限关系
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("insertDutyPrivilege")
	public String insertDutyPrivilege(HttpServletRequest request, HttpServletResponse response) {
		String strDutyid = request.getParameter("strDutyid");
		String strPrivilegeid = request.getParameter("strPrivilegeid");
		if(ValidateTool.isEmptyStr(strDutyid)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "职务ID不能为空", null);
		}
		if(ValidateTool.isEmptyStr(strPrivilegeid)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "权限ID不能为空", null);
		}
		try {
			DutyPrivilegeEntity dutyPrivilegeEntity = new DutyPrivilegeEntity();
			dutyPrivilegeEntity.setStrRelationid(DataTool.getUUID());
			dutyPrivilegeEntity.setStrDutyid(strDutyid);
			dutyPrivilegeEntity.setStrPrivilegeid(strPrivilegeid);
			dutyService.insertDutyPrivilege(dutyPrivilegeEntity);
			return DataTool.constructResponse(ResultCode.OK, "新增成功", null);
		} catch (Exception e) {
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
		}
	}
	
	/**
	 * 删除职务权限关系
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("deleteDutyPrivilege")
	public String deleteDutyPrivilege(HttpServletRequest request, HttpServletResponse response) {
		String strDutyid = request.getParameter("strDutyid");
		String strPrivilegeid = request.getParameter("strPrivilegeid");
		if(ValidateTool.isEmptyStr(strDutyid)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "职务ID不能为空", null);
		}
		if(ValidateTool.isEmptyStr(strPrivilegeid)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "权限ID不能为空", null);
		}
		try {
			dutyService.deleteDutyPrivilege(strPrivilegeid, strDutyid);
			return DataTool.constructResponse(ResultCode.OK, "删除成功", null);
		} catch (Exception e) {
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
		}
	}
	
}
