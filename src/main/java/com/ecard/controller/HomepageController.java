package com.ecard.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ecard.entity.EmployeeEntity;
import com.ecard.util.WebSessionUtil;

/**
 * 主页
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/admin/biz/")
public class HomepageController {
	
	@Resource
	private WebSessionUtil webSessionUtil;
	/**
	 * 登录成功后跳转到主页面
	 * @return
	 */
	@RequestMapping("homepage")
	public ModelAndView homepage(HttpServletRequest request, HttpServletResponse response) {
		
		ModelAndView mv = new ModelAndView();
		try {
			// 当前登录的用户信息
			EmployeeEntity employeeEntity = (EmployeeEntity) webSessionUtil.getWebSession(
					request, response).getAttribute("employeeEntity");
			mv.addObject("employeeEntity", employeeEntity);
			mv.setViewName("admin/homepage");
		} catch (Exception e) {
			mv.setViewName("admin/error");
			e.printStackTrace();
		} 
		
		return mv;
		
	}
}
