package com.ecard.wexin.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ecard.service.DutyService;
import com.ecard.service.EmployeeService;
import com.ecard.service.PrivilegeService;
import com.ecard.util.WebSessionUtil;
/**
 * 微信端首页
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/")
public class WechatHomepageController {
	
	@Resource
	private EmployeeService employeeService;
	@Resource
	private DutyService dutyService;
	@Resource
	private PrivilegeService privilegeService;
	@Resource
	private WebSessionUtil webSessionUtil;
	/**
	 * 登录成功后跳转到微信端主页面
	 * @return
	 */
	@RequestMapping("/")
	public ModelAndView homepage(HttpServletRequest request, HttpServletResponse response) {
		
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("weixin/homepage");
		
		return mv;
		
	}
}
