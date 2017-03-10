package com.ecard.wexin.controller;

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
import com.ecard.entity.MemberEntity;
import com.ecard.service.MemberService;
import com.ecard.util.RedisUtil;
import com.ecard.util.Session;
import com.ecard.util.WebSessionUtil;
/**
 * 微信端登录
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/weixin/")
public class WechatLoginController {
	
	@Resource
	private MemberService memberService;
	@Resource
	private WebSessionUtil webSessionUtil;
	@Resource
	private RedisUtil redisUtil;
	/**
	 * 跳转到登录界面
	 * @return
	 */
	@RequestMapping("login")
	public ModelAndView login(HttpServletRequest request) {
	
		ModelAndView mv = new ModelAndView();
		String url = request.getParameter("url");
		mv.setViewName("weixin/login");
		if(null==url){
			url ="";
		}
		mv.addObject("url", url);
		return mv;
	}
	
	/**
	 * 发送验证码
	 * @param strMobile
	 * @return
	 */
	@RequestMapping("sendAuthcode")
	@ResponseBody
	public String sendAuthcode(HttpServletRequest request, HttpServletResponse response) {
		
		String strMobile = request.getParameter("strMobile");
		if(ValidateTool.isEmptyStr(strMobile)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "手机号不能为空", null);
		}
		if(!ValidateTool.isMobile(strMobile)) {
			return DataTool.constructResponse(ResultCode.MOBILE_FORMAT_ERROR, "电话格式错误", null);
		}
		
		try {
			//验证该手机号是否已经被注册
			String flag = memberService.judgeMobileIsExist("", strMobile.trim());
			if("false".equals(flag)) {
				//手机号未注册了
				return DataTool.constructResponse(ResultCode.MOBILE_ISNOT_MEMBER, "该手机号暂不是会员", null);
			}
			String str_sems_code = DataTool.generatRandomDigit(4); //生成4位数随机验证码
			redisUtil.set("login_sms_" + strMobile.trim(), str_sems_code, StaticValue.CAPTCHA_VALID_TIME);

			return DataTool.constructResponse(ResultCode.OK, "发送成功", str_sems_code);
		} catch (Exception e) {
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
		}
	}
	
	/**
	 * 登录验证
	 * @param strMobile
	 * @param strAuthcode
	 * @return
	 */
	@RequestMapping("loginCertification")
	@ResponseBody
	public String loginCertification(HttpServletRequest request, HttpServletResponse response) {
		
		String strMobile = request.getParameter("strMobile");
		String strAuthcode = request.getParameter("strAuthcode");
		if(ValidateTool.isEmptyStr(strMobile)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "手机号不能为空", null);
		}
		if(!ValidateTool.isMobile(strMobile)) {
			return DataTool.constructResponse(ResultCode.MOBILE_FORMAT_ERROR, "电话格式错误", null);
		}
		if(ValidateTool.isEmptyStr(strAuthcode)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "验证码不能为空", null);
		}
		
		try {
			
			String flag = memberService.judgeMobileIsExist("", strMobile.trim());
			if("false".equals(flag)) {
				//手机号未注册了
				return DataTool.constructResponse(ResultCode.MOBILE_ISNOT_MEMBER, "该手机号暂不是会员", null);
			}
			//取得验证码
			String session_sms_code = (String)redisUtil.get("login_sms_"+strMobile.trim());
			if(ValidateTool.isEmptyStr(session_sms_code)) {
				//验证码失效了
				return DataTool.constructResponse(ResultCode.CAPTCHA_FAILURE, "验证码失效", null);
			}
			if(!strAuthcode.equals(session_sms_code)) {
				//验证码不正确
				return DataTool.constructResponse(ResultCode.CAPTCHA_ERROR, "验证码错误", null);
			}
			
			MemberEntity memberEntity = memberService.getMemberEntityByMobile(strMobile.trim());
			
			Session session = webSessionUtil.getWeixinSession(request,
					response);
			session.setAttribute("memberid", memberEntity.getStrMemberid());
			webSessionUtil.updateSession(session);
			return DataTool.constructResponse(ResultCode.OK, "登录成功", null);
		} catch (Exception e) {
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
		}
	}
}
