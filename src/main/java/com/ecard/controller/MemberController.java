package com.ecard.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ecard.service.MemberService;
import com.ecard.util.WebSessionUtil;
/**
 * 会员级别控制层
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/admin/biz/member")
public class MemberController {
	
	@Resource
	private MemberService memberService;
	@Resource
	private WebSessionUtil webSessionUtil;
	
}
