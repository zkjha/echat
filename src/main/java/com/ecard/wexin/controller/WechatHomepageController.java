package com.ecard.wexin.controller;

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
import com.ecard.entity.MemberEntity;
import com.ecard.service.MemberService;
import com.ecard.util.QRCodeTool;
import com.ecard.util.WebSessionUtil;
/**
 * 微信端首页
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/")
public class WechatHomepageController {
	
	@Resource
	private MemberService memberService;
	@Resource
	private WebSessionUtil webSessionUtil;
	/**
	 * 登录成功后跳转到微信端主页面
	 * @return
	 */
	@RequestMapping("/")
	public ModelAndView homepage(HttpServletRequest request, HttpServletResponse response) {
		
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("weixin/homepage");
		
		return mv;
		
	}
	
	/**
	 * 个人中心二维码页面
	 * @return
	 */
	@RequestMapping("/weixin/biz/userBarCode")
	public ModelAndView userBarCode(HttpServletRequest request, HttpServletResponse response) {
		
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("weixin/userBarCode");
		
		return mv;
		
	}
	
	/**
	 * 微信签到
	 * @return
	 */
	@RequestMapping("/weixin/biz/memberSign")
	public ModelAndView memberSign(HttpServletRequest request, HttpServletResponse response) {
		
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("weixin/memberSign");
		
		return mv;
		
	}
	
	/**
	 * 积分商城
	 * @return
	 */
	@RequestMapping("/weixin/biz/integrationMall")
	public ModelAndView integrationMall(HttpServletRequest request, HttpServletResponse response) {
		
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("weixin/integrationMall");
		
		return mv;
		
	}
	
	/**
	 * 商品详情
	 * @return
	 */
	@RequestMapping("/weixin/biz/goodsDetailInfo")
	public ModelAndView goodsDetailInfo(HttpServletRequest request, HttpServletResponse response) {
		
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("weixin/goodsDetailInfo");
		
		return mv;
		
	}
	
	/**
	 * 查询会员基本信息
	 * @return
	 */
	@RequestMapping("/weixin/biz/getLoginUserInfo")
	@ResponseBody
	public String getLoginUserInfo(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			// 当前登录的用户信息
			String memberid = (String) webSessionUtil.getWeixinSession(
					request, response).getAttribute("memberid");
			Map<String,Object> resultMap = new HashMap<String,Object>();
			if(!ValidateTool.isEmptyStr(memberid)) {
				//登录用户不为空，查询登录用户对应的用户信息
				MemberEntity memberEntity = memberService.getLoginUserInfoById(memberid);
				if(!ValidateTool.isNull(memberEntity)) {
					resultMap.put("strMembercardnum", memberEntity.getStrMembercardnum());
					resultMap.put("intIntegral", memberEntity.getIntIntegral());
					resultMap.put("dBalance", memberEntity.getdBalance());
					resultMap.put("strLevelsname", memberEntity.getStrLevelsid());
					String code_url_base64 = QRCodeTool.getQRCode(memberid);
					resultMap.put("strQrCode", "data:image/png;base64," + code_url_base64);
					resultMap.put("intVouchers", 5);
				}
			}
			return DataTool.constructResponse(ResultCode.OK, "查询成功", resultMap);
		} catch (Exception e) {
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
		} 		
	}
}
