package com.ecard.mapper;

import java.util.List;
import java.util.Map;

import com.ecard.entity.EmployeeEntity;

/**
 * 员工操作mapper
 */
public interface EmployeeMapper {
	
	//新增员工
	void insertEmployee(EmployeeEntity employeeEntity) throws Exception;
	
	//修改员工
	void updateEmployee(EmployeeEntity employeeEntity) throws Exception;
	
	//根据登录用户名查询用户信息
	EmployeeEntity getEmployeeByLoginname(String strLoginname) throws Exception;
	
	//根据ID查询用户信息
	EmployeeEntity getEmployeeById(String strEmployeeid) throws Exception;
	
	//查询员工列表
	List<EmployeeEntity> listEmployeeEntity(Map<String,Object> queryMap) throws Exception;
}
