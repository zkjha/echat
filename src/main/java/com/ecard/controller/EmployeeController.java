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
import com.ecard.config.StaticValue;
import com.ecard.entity.DutyEntity;
import com.ecard.entity.EmployeeEntity;
import com.ecard.service.DutyService;
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
	private DutyService dutyService;
	@Resource
	private WebSessionUtil webSessionUtil;
	
	/**
	 * 判断登录名称是否存在
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("judgeEmployeeLoginNameIsExist")
	public String judgeEmployeeLoginNameIsExist(HttpServletRequest request, HttpServletResponse response) {
		String strEmployeeid = request.getParameter("strEmployeeid");
		String strLoginname = request.getParameter("strLoginname");
		if(ValidateTool.isEmptyStr(strLoginname)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "登录名不能为空", null);
		}
		try {
			String flag = employeeService.judgeEmployeeLoginNameIsExist(strEmployeeid, strLoginname.trim());
			return DataTool.constructResponse(ResultCode.OK, "查询成功", flag);
		} catch (Exception e) {
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
		}
	}
	
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
		int intStatus = Integer.valueOf(request.getParameter("intStatus"));
		if(ValidateTool.isEmptyStr(strLoginname)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "登录名称不能为空", null);
		}
		if(ValidateTool.isEmptyStr(strPassword)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "登录密码不能为空", null);
		}
		if(ValidateTool.isEmptyStr(strDutyid)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "职务ID不能为空", null);
		}
		if(ValidateTool.isEmptyStr(strMobile)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "电话不能为空", null);
		}
		if(ValidateTool.isMobile(strMobile)) {
			return DataTool.constructResponse(ResultCode.MOBILE_FORMAT_ERROR, "电话格式错误", null);
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
			employeeEntity.setStrHeadportrait(DataTool.trimStr(strHeadportrait));
			employeeEntity.setStrDutyid(strDutyid);
			employeeEntity.setStrMobile(strMobile.trim());
			employeeEntity.setStrRealname(strRealname.trim());
			employeeEntity.setIntStatus(intStatus);
			employeeEntity.setStrMerchantid(loginemployeeEntity.getStrMerchantid());
			employeeEntity.setStrInserttime(DateTool.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
			return employeeService.insertEmployee(employeeEntity);
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
			if(ValidateTool.isNull(employeeEntity)) {
				return DataTool.constructResponse(ResultCode.NO_DATA, "员工不存在", null);
			}
			List<DutyEntity> dutyEntityList = dutyService.listDuty(0, 0); //查询职务列表
			if(dutyEntityList==null||dutyEntityList.size()<=0) {
				return DataTool.constructResponse(ResultCode.NO_DATA, "暂无职务", null);
			}
			Map<String,Object> resultMap = new HashMap<String, Object>();
			resultMap.put("strImgrootpath", StaticValue.IMAGE_ROOT_PATH);
			resultMap.put("employeeEntity", employeeEntity);
			resultMap.put("dutyEntityList", dutyEntityList);
			return DataTool.constructResponse(ResultCode.OK, "查询成功", resultMap);
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
		String strHeadportrait = request.getParameter("strHeadportrait");
		String strDutyid = request.getParameter("strDutyid");
		String strMobile = request.getParameter("strMobile");
		String strRealname = request.getParameter("strRealname");
		int intStatus = Integer.valueOf(request.getParameter("intStatus"));
		if(ValidateTool.isEmptyStr(strEmployeeid)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "员工ID不能为空", null);
		}
		if(ValidateTool.isEmptyStr(strDutyid)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "职务ID不能为空", null);
		}
		if(ValidateTool.isEmptyStr(strMobile)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "电话不能为空", null);
		}
		if(ValidateTool.isMobile(strMobile)) {
			return DataTool.constructResponse(ResultCode.MOBILE_FORMAT_ERROR, "电话格式错误", null);
		}
		if(ValidateTool.isEmptyStr(strRealname)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "真实姓名不能为空", null);
		}
		try {
			EmployeeEntity employeeEntity = new EmployeeEntity();
			employeeEntity.setStrEmployeeid(strEmployeeid);
			employeeEntity.setStrHeadportrait(DataTool.trimStr(strHeadportrait));
			employeeEntity.setStrDutyid(strDutyid);
			employeeEntity.setStrMobile(strMobile.trim());
			employeeEntity.setStrRealname(strRealname.trim());
			employeeEntity.setIntStatus(intStatus);
			employeeEntity.setStrUpdatetime(DateTool.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
			return employeeService.updateEmployee(employeeEntity);
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
	@RequestMapping("resetEmployeePassword")
	public String resetEmployeePassword(HttpServletRequest request, HttpServletResponse response) {
		String strEmployeeid = request.getParameter("strEmployeeid");
		if(ValidateTool.isEmptyStr(strEmployeeid)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "员工ID不能为空", null);
		}
		try {
			EmployeeEntity employeeEntity = new EmployeeEntity();
			employeeEntity.setStrEmployeeid(strEmployeeid);
			employeeEntity.setStrPassword(MD5Tool.createMd5("123456"));
			employeeService.resetEmployeePassword(employeeEntity);
			return DataTool.constructResponse(ResultCode.OK, "重置成功", null);
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
		String pagenum = request.getParameter("pagenum");
		String pagesize = request.getParameter("pagesize");
		String strSearchkey = request.getParameter("strSearchkey");
		
		if(ValidateTool.isEmptyStr(pagenum)) {
			pagenum = "1";
		}
		int iPagesize = StaticValue.PAGE_SIZE;
		if(!ValidateTool.isEmptyStr(pagesize)) {
			iPagesize = Integer.valueOf(pagesize);
		}
		
		int pageFrom = (Integer.parseInt(pagenum)-1)*iPagesize;
		try {
			Map<String,Object> queryMap = new HashMap<String, Object>();
			queryMap.put("pageFrom", pageFrom);
			queryMap.put("pageSize", iPagesize);
			queryMap.put("strSearchkey", strSearchkey);
			List<EmployeeEntity> employeeEntityList = employeeService.listEmployeeEntity(queryMap);
			if(ValidateTool.isNull(employeeEntityList)||employeeEntityList.size()<=0) {
				return DataTool.constructResponse(ResultCode.NO_DATA, "暂无员工", null);
			} else {
				int totalrecord = employeeService.getEmployeeTotalCount(queryMap); //查询员工总数量
				Map<String,Object> resultMap = new HashMap<String, Object>();
				resultMap.put("employeeEntityList", employeeEntityList);
				resultMap.put("iTotalRecord", totalrecord);
				resultMap.put("iTotalPage", totalrecord%iPagesize == 0 ? totalrecord/iPagesize : totalrecord/iPagesize+1);
				return DataTool.constructResponse(ResultCode.OK, "查询成功", resultMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
		}
		
	}
}
