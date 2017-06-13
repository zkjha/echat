package com.ecard.util;

import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.ecard.config.StaticValue;

@Component
public class WebSessionUtil {

	@Resource
	private RedisUtil redisUtil;
	/**
	 * 为管理用户提供session
	 * @param sessionid
	 * @return Session
	 * @throws Exception 
	 */
	public Session getWebSession(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		if(request==null){
			return null;
		}
		if(response==null){
			return null;
		}
		String sessionid=null;
		Cookie[] cookies=request.getCookies();
		if(cookies!=null){
			for (Cookie cookie : cookies) {
				if(cookie.getName().equals(StaticValue.COOKIENAME)){
					sessionid=cookie.getValue();
					
				};
			}
		}
		
		
		if(sessionid==null){
			
			sessionid=this.creatSessionId();
			Cookie cookie=new Cookie(StaticValue.COOKIENAME,sessionid);
			cookie.setPath("/");
			cookie.setDomain(StaticValue.DOMAIN);
			response.addCookie(cookie);
			return creatSession(sessionid);			
		}
		
		Session session=(Session)  redisUtil.get("websessionid_"+sessionid);
		
		if(session==null){		
			
			return creatSession("websessionid_"+sessionid);
		}else{
			updateSession(session);
			return session;
		}
	}
	
	/**
	 * 为微信用户提供session
	 * @param sessionid
	 * @return Session
	 * @throws Exception 
	 */
	public Session getWeixinSession(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		if(request==null){
			return null;
		}
		if(response==null){
			return null;
		}
		String sessionid=null;
		Cookie[] cookies=request.getCookies();
		if(cookies!=null){
			for (Cookie cookie : cookies) {
				if(cookie.getName().equals(StaticValue.WX_COOKIENAME)){
					sessionid=cookie.getValue();
				};
			}
		}
		
		
		if(sessionid==null){
			
			sessionid=this.creatSessionId();
			Cookie cookie=new Cookie(StaticValue.WX_COOKIENAME,sessionid);
			cookie.setPath("/");
			cookie.setDomain(StaticValue.DOMAIN);
			response.addCookie(cookie);
			return creatSession(sessionid);			
		}
		
		Session session=(Session)  redisUtil.get("wxsessionid_"+sessionid);
		
		if(session==null){		
			
			return creatSession("wxsessionid_"+sessionid);
		}else{
			updateSession(session);
			return session;
		}
		
	}
	
	/**
	 * 更新session
	 * @param sessionid
	 * @param session
	 * @throws Exception 
	 */
	public void updateSession(Session session) throws Exception{
		
		try {
			redisUtil.set(session.getSessionId(), session,StaticValue.USER_SESSION_EXPIRE_TIME);
		} catch (Exception e) {
			
			e.printStackTrace();
			throw e;
		}
		
	}
	
	/**
	 * 移除session对象
	 * @param session
	 * @throws Exception
	 */
	public void removeWebSession(Session session) throws Exception{
		
		if(session!=null){
			try {
				redisUtil.remove(session.getSessionId());
			} catch (Exception e) {
				
				e.printStackTrace();
				throw e;
			}
		}
		
	}
	
	private String creatSessionId(){
		String sessionid = UUID.randomUUID().toString().replace("-", "");
		return sessionid;
	}
	
	private Session creatSession(String sessionid) throws Exception{
		Session newsession=new Session(sessionid);
		
		
		try {
			redisUtil.set(sessionid, newsession,StaticValue.USER_SESSION_EXPIRE_TIME);
		} catch (Exception e) {
			
			e.printStackTrace();
			throw e;
		};
		
		return newsession;
	}
}
