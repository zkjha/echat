package com.ecard.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
