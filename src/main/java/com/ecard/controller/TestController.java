package com.ecard.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecard.service.EmployeeService;
import com.ecard.util.RedisUtil;
@Controller
@RequestMapping("/")
public class TestController {
	
	@Resource
	private RedisUtil redisUtil;
	@Resource
    private RabbitTemplate rabbitTemplate;
	
	
	@Resource
	private EmployeeService tokenService;
	
	@RequestMapping("test")
	@ResponseBody
	public String test(String access_token) {
		System.out.println("test");
		@SuppressWarnings("unchecked")
		List<String> priviliegeList = (List<String>) redisUtil
				.get("access_token_" + access_token);
		for(String s : priviliegeList) {
			System.out.println(s);
		}
		return "test success";
	}
	
	@RequestMapping("test3")
	@ResponseBody
	public String test3() {
		String context = "hello " + new Date();
        System.out.println("Sender : " + context);
        rabbitTemplate.convertAndSend("hello", context);
        String context1 = "hello1 " + new Date();
        System.out.println("Sender : " + context1);
        rabbitTemplate.convertAndSend("hello1", context1);
        return "send success";
	}
}
