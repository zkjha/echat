package com.ecard.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecard.entity.EmployeeEntity;
import com.ecard.mapper.EmployeeMapper;

/**
 * 员工操作service
 */
@Service
@Transactional
public class EmployeeService {
	
	@Autowired
	private EmployeeMapper employeeMapper;
	
	//根据登录用户名查询用户信息
	public EmployeeEntity getEmployeeByLoginname(String strLoginname) throws Exception {
		return employeeMapper.getEmployeeByLoginname(strLoginname);
	}
}

