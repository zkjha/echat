package com.ecard.wexin.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.commontools.data.DataTool;
import com.commontools.secret.MD5Tool;
import com.commontools.validate.ValidateTool;
import com.ecard.config.ResultCode;
import com.ecard.entity.EmployeeEntity;
import com.ecard.enumeration.EmployeeStatusEnum;
import com.ecard.service.DutyService;
import com.ecard.service.EmployeeService;
import com.ecard.service.PrivilegeService;
import com.ecard.util.Session;
import com.ecard.util.WebSessionUtil;
/**
 * 微信端登录
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/weixin/")
public class WechatLoginController {
	
	@Resource
	private EmployeeService employeeService;
	@Resource
	private DutyService dutyService;
	@Resource
	private PrivilegeService privilegeService;
	@Resource
	private WebSessionUtil webSessionUtil;
	/**
	 * 跳转到登录界面
	 * @return
	 */
	@RequestMapping("login")
	public ModelAndView login(HttpServletRequest request) {
	
		ModelAndView mv = new ModelAndView();
		String url = request.getParameter("url");
		mv.setViewName("weixin/login");
		if(null==url){
			url ="";
		}
		mv.addObject("url", url);
		return mv;
	}
	
	/**
	 * 登录验证
	 * @param strLoginname
	 * @param strPassword
	 * @return
	 */
	@RequestMapping("loginCertification")
	@ResponseBody
	public String loginCertification(HttpServletRequest request, HttpServletResponse response) {
		
		String strLoginname = request.getParameter("strLoginname");
		String strPassword = request.getParameter("strPassword");
		if(ValidateTool.isEmptyStr(strLoginname)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "登录用户名不能为空", null);
		}
		if(ValidateTool.isEmptyStr(strPassword)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "登录密码不能为空", null);
		}
		
		try {
			
			//根据登录用户名查询用户信息
			EmployeeEntity employeeEntity = employeeService.getEmployeeByLoginname(strLoginname.trim());
			if(ValidateTool.isNull(employeeEntity)) {
				//结果为空，该登录用户不存在
				return DataTool.constructResponse(ResultCode.USER_NOT_EXIST, "登录用户名不存在", null);
			} else {
				
				int intStatus = employeeEntity.getIntStatus();
				if(intStatus==EmployeeStatusEnum.FORBID.getValue()) {
					return DataTool.constructResponse(ResultCode.ACCOUNT_IS_FORBID, "账号已被禁用", null);
				}
				String real_pwd = employeeEntity.getStrPassword(); //用户实际密码
				String input_pwd = MD5Tool.createMd5(strPassword.trim()); //用户输入的密码经MD5加密
				if(real_pwd.equals(input_pwd)) {
					//输入的密码和实际密码匹配成功，登录成功
					// 往session中放入用户对象信息
					List<String> privilegeList = privilegeService.listDutyPrivilegeUrl(employeeEntity.getStrDutyid()); //查询用户的权限信息
					Session session = webSessionUtil.getWebSession(request,
							response);
					session.setAttribute("employeeEntity", employeeEntity);
					session.setAttribute("privilegeList", privilegeList);
					webSessionUtil.updateWebSession(session);
					return DataTool.constructResponse(ResultCode.OK, "登录成功", null);
				} else {
					//密码不匹配
					return DataTool.constructResponse(ResultCode.PASSWORD_IS_WRONG, "密码错误", null);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
		}
	}
}
