package com.ecard.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.commontools.data.DataTool;
import com.commontools.secret.MD5Tool;
import com.ecard.config.ResultCode;
import com.ecard.config.StaticValue;
import com.ecard.entity.MerchantEntity;
import com.ecard.service.MerchantService;
import com.ecard.util.PrintUtil;

//系统全局拦截器
public class SystemInterceptor implements HandlerInterceptor {
	@Autowired
	private MerchantService merchantService;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		PrintUtil.println("系统状态拦截器");
		response.setContentType("text/html;charset=UTF-8");
		
		String x_requested_with=request.getHeader("x-requested-with");
    	
		try {
			MerchantEntity merchantEntity = merchantService.getMerchantSystemVersionInfo();
			if(merchantEntity == null) {
				if(x_requested_with==null){
					response.sendRedirect(StaticValue.PROJECT_ROOT_PATH + "admin/systemoutdate");
	          	}else{
		       		response.getWriter().write(DataTool
							.constructResponse(
									ResultCode.SYSTEM_OUTDATED,
									"系统过期，请升级", null));
				
	          	}
				return false;
			}
			int intValiddays = merchantEntity.getIntValiddays(); //有效天数
			int intMembercount = merchantEntity.getIntMembercount(); //会员数量
			String strNewsystemsecret = MD5Tool.createMd5("ecard_" + intValiddays + "_" + intMembercount);
			if(!strNewsystemsecret.equals(merchantEntity.getStrSystemsecret())) {
				//系统参数信息被改了
				if(x_requested_with==null){
					response.sendRedirect(StaticValue.PROJECT_ROOT_PATH + "admin/systemoutdate");
	          	}else{
		       		response.getWriter().write(DataTool
							.constructResponse(
									ResultCode.SYSTEM_OUTDATED,
									"系统过期，请升级", null));
				
	          	}
				return false;
			}
			if(intValiddays>0) {
				return true;
			} else {
				if(x_requested_with==null){
					response.sendRedirect(StaticValue.PROJECT_ROOT_PATH + "admin/systemoutdate");
	          	}else{
		       		response.getWriter().write(DataTool
							.constructResponse(
									ResultCode.SYSTEM_OUTDATED,
									"系统过期，请升级", null));
				
	          	}
				return false;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().write(DataTool.constructResponse(
							ResultCode.SYSTEM_ERROR, "系统错误", null));
			return false;
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}
}
