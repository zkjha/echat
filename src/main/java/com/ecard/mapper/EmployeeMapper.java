package com.ecard.mapper;

import com.ecard.entity.EmployeeEntity;

/**
 * 员工操作mapper
 */
public interface EmployeeMapper {
	
	//根据登录用户名查询用户信息
	EmployeeEntity getEmployeeByLoginname(String strLoginname) throws Exception;
}
