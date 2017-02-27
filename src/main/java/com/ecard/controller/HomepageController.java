package com.ecard.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.commontools.data.DataTool;
import com.commontools.validate.ValidateTool;
import com.ecard.config.ResultCode;
import com.ecard.config.StaticValue;
import com.ecard.entity.EmployeeEntity;
import com.ecard.entity.MerchantEntity;
import com.ecard.service.MerchantService;
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
	private MerchantService merchantService;
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
			if(!ValidateTool.isNull(employeeEntity)) {
				//登录用户不为空，查询登录用户对用的商家信息
				MerchantEntity merchantEntity = merchantService.getMerchantById(employeeEntity.getStrMerchantid());
				mv.addObject("merchantEntity", merchantEntity);
				mv.addObject("strImgrootpath", StaticValue.IMAGE_ROOT_PATH);
			}
			mv.addObject("employeeEntity", employeeEntity);
			mv.setViewName("admin/homepage");
		} catch (Exception e) {
			mv.setViewName("admin/error");
			e.printStackTrace();
		} 
		
		return mv;
		
	}
	
	/**
	 * 得到后端登录用户信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getLoginUserInfo")
	public String getLoginUserInfo(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			// 当前登录的用户信息
			EmployeeEntity employeeEntity = (EmployeeEntity) webSessionUtil.getWebSession(
					request, response).getAttribute("employeeEntity");
			Map<String,Object> resultMap = new HashMap<String,Object>();
			
			if(!ValidateTool.isNull(employeeEntity)) {
				//登录用户不为空，查询登录用户对用的商家信息
				MerchantEntity merchantEntity = merchantService.getMerchantById(employeeEntity.getStrMerchantid());
				resultMap.put("strMerchantlogo", merchantEntity.getStrMerchantlogo());
				resultMap.put("strImgrootpath", StaticValue.IMAGE_ROOT_PATH);
				resultMap.put("strMerchantname", merchantEntity.getStrMerchantname());
				resultMap.put("strMerchantid", merchantEntity.getStrMerchantid());
				resultMap.put("strEmployeeid", employeeEntity.getStrEmployeeid());
				resultMap.put("strRealname", employeeEntity.getStrRealname());
			}
			return DataTool.constructResponse(ResultCode.OK, "查询成功", resultMap);
		} catch (Exception e) {
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
		} 
		
	}
}
