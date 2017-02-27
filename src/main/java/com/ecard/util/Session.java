package com.ecard.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Session implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Map<String,Object> sessionMap;
	
	private String sessionId;
	public Session() {
		
	}
	public Session(String sessionId){
		this.sessionId=sessionId;
		sessionMap=new HashMap<String, Object>();
		
	}
	public String getSessionId() {
		return sessionId;
	}
	
	/**
	 * 将value对象以name名称绑定到会话
	 * @param name:q:
	 * @param value
	 */
	public void setAttribute(String name,Object value){
		this.sessionMap.put(name, value);
	}
	/**
	 * 取得name的属性值，如果属性不存在则返回null
	 * @param name
	 * @return
	 */
	public Object getAttribute(String name){
		Object object=this.sessionMap.get(name);
		return object;
	}
	/**
	 * 从会话中删除name属性，如果不存在不会执行，也不会抛处错误.
	 * @param name
	 */
	public void removeAttribute(String name){
		this.sessionMap.remove(name);
	}
	/**
	 * 从回话中移除所有的属性
	 */
	public void removeAllAttribute(){
		this.sessionMap.clear();
	}
}
