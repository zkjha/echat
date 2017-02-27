package com.ecard.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ecard.service.DutyService;
import com.ecard.util.WebSessionUtil;
/**
 * 职务控制层
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/admin/biz/duty")
public class DutyController {
	
	@Resource
	private DutyService dutyService;
	@Resource
	private WebSessionUtil webSessionUtil;
}
