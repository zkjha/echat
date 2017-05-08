package com.ecard.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.commontools.data.DataTool;
import com.ecard.config.ResultCode;
import com.ecard.config.StaticValue;
import com.ecard.entity.EmployeeEntity;
import com.ecard.util.Session;
import com.ecard.util.WebSessionUtil;

//登录拦截器
public class LoginInterceptor implements HandlerInterceptor {
	@Autowired
	private WebSessionUtil webSessionUtil;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//PrintUtil.println("登录拦截器");
		response.setContentType("text/html;charset=UTF-8");
		String x_requested_with=request.getHeader("x-requested-with");
		Session session = webSessionUtil.getWebSession(request, response);
    	
		if (session == null) {
			if(x_requested_with==null){
				response.sendRedirect(StaticValue.PROJECT_ROOT_PATH + "admin/login");
          	}else{
	       		response.getWriter().write(DataTool
						.constructResponse(
								ResultCode.NOT_LOGIN,
								"未登陆", null));
			
          	}
			return false;
		}
		try {
			EmployeeEntity employeeEntity = (EmployeeEntity)session.getAttribute("employeeEntity");
			if (employeeEntity == null) {
				if(x_requested_with==null){
					response.sendRedirect(StaticValue.PROJECT_ROOT_PATH + "admin/login");
	          	}else{
		       		response.getWriter().write(DataTool
							.constructResponse(
									ResultCode.NOT_LOGIN,
									"未登陆", null));
				
	          	}
				return false;
			}
			return true;
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
