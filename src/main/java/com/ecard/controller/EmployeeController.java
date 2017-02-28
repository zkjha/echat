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
import com.commontools.secret.MD5Tool;
import com.commontools.validate.ValidateTool;
import com.ecard.config.ResultCode;
import com.ecard.entity.EmployeeEntity;
import com.ecard.enumeration.EmployeeStatusEnum;
import com.ecard.service.EmployeeService;
import com.ecard.util.WebSessionUtil;
/**
 * 员工控制层
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/admin/biz/employee")
public class EmployeeController {
	
	@Resource
	private EmployeeService employeeService;
	@Resource
	private WebSessionUtil webSessionUtil;
	
	/**
	 * 新增员工
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("insertEmployee")
	public String insertEmployee(HttpServletRequest request, HttpServletResponse response) {
		String strLoginname = request.getParameter("strLoginname");
		String strPassword = request.getParameter("strPassword");
		String strHeadportrait = request.getParameter("strHeadportrait");
		String strDutyid = request.getParameter("strDutyid");
		String strMobile = request.getParameter("strMobile");
		String strRealname = request.getParameter("strRealname");
		if(ValidateTool.isEmptyStr(strLoginname)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "登录名称不能为空", null);
		}
		if(ValidateTool.isEmptyStr(strPassword)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "登录密码不能为空", null);
		}
		if(ValidateTool.isEmptyStr(strHeadportrait)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "头像不能为空", null);
		}
		if(ValidateTool.isEmptyStr(strDutyid)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "职务ID不能为空", null);
		}
		if(ValidateTool.isEmptyStr(strMobile)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "电话不能为空", null);
		}
		if(ValidateTool.isEmptyStr(strRealname)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "真实姓名不能为空", null);
		}
		try {
			EmployeeEntity loginemployeeEntity = (EmployeeEntity) webSessionUtil.getWebSession(
					request, response).getAttribute("employeeEntity");
			EmployeeEntity employeeEntity = new EmployeeEntity();
			employeeEntity.setStrEmployeeid(DataTool.getUUID());
			employeeEntity.setStrLoginname(strLoginname.trim());
			employeeEntity.setStrPassword(MD5Tool.createMd5(strPassword));
			employeeEntity.setStrHeadportrait(strHeadportrait.trim());
			employeeEntity.setStrDutyid(strDutyid);
			employeeEntity.setStrMobile(strMobile.trim());
			employeeEntity.setStrRealname(strRealname.trim());
			employeeEntity.setIntStatus(EmployeeStatusEnum.ACTIVATE.getValue());
			employeeEntity.setStrMerchantid(loginemployeeEntity.getStrMerchantid());
			employeeEntity.setStrInserttime(DateTool.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
			employeeService.insertEmployee(employeeEntity);
			return DataTool.constructResponse(ResultCode.OK, "新增成功", null);
		} catch (Exception e) {
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
		}
	}
	
	/**
	 * 根据员工ID查询员工信息
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getEmployeeById")
	public String getEmployeeById(HttpServletRequest request, HttpServletResponse response) {
		String strEmployeeid = request.getParameter("strEmployeeid");
		if(ValidateTool.isEmptyStr(strEmployeeid)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "员工ID不能为空", null);
		}
		try {
			EmployeeEntity employeeEntity = employeeService.getEmployeeById(strEmployeeid);
			return DataTool.constructResponse(ResultCode.OK, "查询成功", employeeEntity);
		} catch (Exception e) {
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
		}
	}
	
	/**
	 * 修改员工
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("updateEmployee")
	public String updateEmployee(HttpServletRequest request, HttpServletResponse response) {
		String strEmployeeid = request.getParameter("strEmployeeid");
		String strPassword = request.getParameter("strPassword");
		String strHeadportrait = request.getParameter("strHeadportrait");
		String strDutyid = request.getParameter("strDutyid");
		String strMobile = request.getParameter("strMobile");
		String strRealname = request.getParameter("strRealname");
		if(ValidateTool.isEmptyStr(strEmployeeid)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "员工ID不能为空", null);
		}
		if(ValidateTool.isEmptyStr(strPassword)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "登录密码不能为空", null);
		}
		if(ValidateTool.isEmptyStr(strHeadportrait)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "头像不能为空", null);
		}
		if(ValidateTool.isEmptyStr(strDutyid)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "职务ID不能为空", null);
		}
		if(ValidateTool.isEmptyStr(strMobile)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "电话不能为空", null);
		}
		if(ValidateTool.isEmptyStr(strRealname)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "真实姓名不能为空", null);
		}
		try {
			EmployeeEntity employeeEntity = new EmployeeEntity();
			employeeEntity.setStrEmployeeid(strEmployeeid);
			employeeEntity.setStrPassword(MD5Tool.createMd5(strPassword));
			employeeEntity.setStrHeadportrait(strHeadportrait.trim());
			employeeEntity.setStrDutyid(strDutyid);
			employeeEntity.setStrMobile(strMobile.trim());
			employeeEntity.setStrRealname(strRealname.trim());
			employeeEntity.setIntStatus(EmployeeStatusEnum.ACTIVATE.getValue());
			employeeEntity.setStrUpdatetime(DateTool.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
			employeeService.updateEmployee(employeeEntity);
			return DataTool.constructResponse(ResultCode.OK, "修改成功", null);
		} catch (Exception e) {
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
		}
	}
	
	/**
	 * 查询员工列表
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("listEmployee")
	public String listEmployee(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			Map<String,Object> queryMap = new HashMap<String, Object>();
			List<EmployeeEntity> employeeEntity = employeeService.listEmployeeEntity(queryMap);
			Map<String,Object> resultMap = new HashMap<String, Object>();
			resultMap.put("employeeEntity", employeeEntity);
			return DataTool.constructResponse(ResultCode.OK, "查询成功", resultMap);
		} catch (Exception e) {
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
		}
		
	}
}
