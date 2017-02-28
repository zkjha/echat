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
import com.ecard.entity.PrivilegeEntity;
import com.ecard.service.PrivilegeService;
import com.ecard.util.WebSessionUtil;
/**
 * 权限控制层
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/admin/biz/privilege")
public class PrivilegeController {
	
	@Resource
	private PrivilegeService privilegeService;
	@Resource
	private WebSessionUtil webSessionUtil;
	
	
	/**
	 * 新增权限
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("insertPrivilege")
	public String insertPrivilege(HttpServletRequest request, HttpServletResponse response) {
		String strPrivilegename = request.getParameter("strPrivilegename");
		String strPrivilegedesc = request.getParameter("strPrivilegedesc");
		String strPrivilegeurl = request.getParameter("strPrivilegeurl");
		String strParentid = request.getParameter("strParentid");
		if(ValidateTool.isEmptyStr(strPrivilegename)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "权限名称不能为空", null);
		}
		if(ValidateTool.isEmptyStr(strPrivilegedesc)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "权限描述不能为空", null);
		}
		if(ValidateTool.isEmptyStr(strPrivilegeurl)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "权限路径不能为空", null);
		}
		if(ValidateTool.isEmptyStr(strParentid)) {
			strParentid = "top";
		}
		try {
			PrivilegeEntity privilegeEntity = new PrivilegeEntity();
			privilegeEntity.setStrPrivilegeid(DataTool.getUUID());
			privilegeEntity.setStrPrivilegename(strPrivilegename.trim());
			privilegeEntity.setStrPrivilegedesc(strPrivilegedesc.trim());
			privilegeEntity.setStrPrivilegeurl(strPrivilegeurl.trim());
			privilegeEntity.setStrParentid(strParentid);
			privilegeEntity.setStrInserttime(DateTool.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
			privilegeService.insertPrivilege(privilegeEntity);
			return DataTool.constructResponse(ResultCode.OK, "新增成功", null);
		} catch (Exception e) {
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
		}
	}

	/**
	 * 删除权限
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("deletePrivilege")
	public String deletePrivilege(HttpServletRequest request, HttpServletResponse response) {
		String strPrivilegeid = request.getParameter("strPrivilegeid");
		if(ValidateTool.isEmptyStr(strPrivilegeid)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "权限ID不能为空", null);
		}
		try {
			privilegeService.deletePrivilege(strPrivilegeid);
			return DataTool.constructResponse(ResultCode.OK, "删除成功", null);
		} catch (Exception e) {
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
		}
	}
	
	
	/**
	 * 根据权限ID查询权限
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getPrivilegeById")
	public String getPrivilegeById(HttpServletRequest request, HttpServletResponse response) {
		String strPrivilegeid = request.getParameter("strPrivilegeid");
		if(ValidateTool.isEmptyStr(strPrivilegeid)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "权限ID不能为空", null);
		}
		try {
			PrivilegeEntity privilegeEntity = privilegeService.getPrivilegeById(strPrivilegeid);
			return DataTool.constructResponse(ResultCode.OK, "查询成功", privilegeEntity);
		} catch (Exception e) {
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
		}
	}
	

	/**
	 * 修改权限
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("updatePrivilege")
	public String updatePrivilege(HttpServletRequest request, HttpServletResponse response) {
		String strPrivilegeid = request.getParameter("strPrivilegeid");
		String strPrivilegename = request.getParameter("strPrivilegename");
		String strPrivilegedesc = request.getParameter("strPrivilegedesc");
		String strPrivilegeurl = request.getParameter("strPrivilegeurl");
		String strParentid = request.getParameter("strParentid");
		if(ValidateTool.isEmptyStr(strPrivilegeid)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "权限ID不能为空", null);
		}
		if(ValidateTool.isEmptyStr(strPrivilegename)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "权限名称不能为空", null);
		}
		if(ValidateTool.isEmptyStr(strPrivilegedesc)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "权限描述不能为空", null);
		}
		if(ValidateTool.isEmptyStr(strPrivilegeurl)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "权限路径不能为空", null);
		}
		if(ValidateTool.isEmptyStr(strParentid)) {
			strParentid = "top";
		}
		try {
			PrivilegeEntity privilegeEntity = new PrivilegeEntity();
			privilegeEntity.setStrPrivilegeid(strPrivilegeid);
			privilegeEntity.setStrPrivilegename(strPrivilegename.trim());
			privilegeEntity.setStrPrivilegedesc(strPrivilegedesc.trim());
			privilegeEntity.setStrPrivilegeurl(strPrivilegeurl.trim());
			privilegeEntity.setStrParentid(strParentid);
			privilegeEntity.setStrUpdatetime(DateTool.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
			privilegeService.insertPrivilege(privilegeEntity);
			return DataTool.constructResponse(ResultCode.OK, "修改成功", null);
		} catch (Exception e) {
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
		}
	}
	
	/**
	 * 查询权限列表
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("listPrivilege")
	public String listPrivilege(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			Map<String,Object> queryMap = new HashMap<String, Object>();
			List<PrivilegeEntity> privilegeEntity = privilegeService.listPrivilege(queryMap);
			Map<String,Object> resultMap = new HashMap<String, Object>();
			resultMap.put("privilegeEntity", privilegeEntity);
			return DataTool.constructResponse(ResultCode.OK, "查询成功", resultMap);
		} catch (Exception e) {
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
		}
		
	}
	
	
}
