package com.ecard.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ecard.service.MerchantService;
/**
 * 商家资料控制层
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/admin/biz/merchant")
public class MerchantController {
	
	@Resource
	private MerchantService merchantService;
	
	
}
