package com.ecard.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.ecard.interceptor.LoginInterceptor;
import com.ecard.interceptor.PrivilegeInterceptor;
import com.ecard.interceptor.SystemInterceptor;

@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {
	
	//提交生成登录拦截器bean
	@Bean
    public HandlerInterceptor getLoginInterceptor(){
        return new LoginInterceptor();
    }
	
	//系统有效期
	@Bean
    public HandlerInterceptor getSystemInterceptor(){
        return new SystemInterceptor();
    }
	
	//系统有效期
	@Bean
    public HandlerInterceptor getPrivilegeInterceptor(){
        return new PrivilegeInterceptor();
    }
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 多个拦截器组成一个拦截器链
		// addPathPatterns 用于添加拦截规则
		// excludePathPatterns 用户排除拦截
		registry.addInterceptor(getLoginInterceptor()).addPathPatterns("/admin/biz/**").addPathPatterns("/admin/privilegebiz/**");
		/*registry.addInterceptor(getPrivilegeInterceptor()).addPathPatterns("/admin/priviegebiz/**");
		registry.addInterceptor(getSystemInterceptor()).addPathPatterns("/admin/**").excludePathPatterns("/admin/systemoutdate");*/
		super.addInterceptors(registry);
	}
}
