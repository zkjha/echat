package com.ecard.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ecard.service.FrontInformationService;
/**
 * 前端注册页面资料信息控制层
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/admin/biz/frontinformation")
public class FrontInformationController {
	
	@Resource
	private FrontInformationService frontInformationService;
	
	
}
