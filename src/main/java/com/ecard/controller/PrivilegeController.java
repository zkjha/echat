package com.ecard.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
