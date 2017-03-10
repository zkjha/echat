package com.ecard.service;


import java.util.List;
import java.util.Map;

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
	
	//判断登录名是否存在
	public String judgeEmployeeLoginNameIsExist(String strEmployeeid, String strLoginname) throws Exception {
		return employeeMapper.judgeEmployeeLoginNameIsExist(strEmployeeid, strLoginname);
	}
	
	//新增员工
	public void insertEmployee(EmployeeEntity employeeEntity) throws Exception {
		employeeMapper.insertEmployee(employeeEntity);
	}
	
	//修改员工
	public void updateEmployee(EmployeeEntity employeeEntity) throws Exception {
		employeeMapper.updateEmployee(employeeEntity);
	}
	
	//重置用户密码
	public void resetEmployeePassword(EmployeeEntity employeeEntity) throws Exception {
		employeeMapper.resetEmployeePassword(employeeEntity);
	}
	
	//根据登录用户名查询用户信息
	public EmployeeEntity getEmployeeByLoginname(String strLoginname) throws Exception {
		return employeeMapper.getEmployeeByLoginname(strLoginname);
	}
	
	//根据ID查询用户信息
	public EmployeeEntity getEmployeeById(String strEmployeeid) throws Exception {
		return employeeMapper.getEmployeeById(strEmployeeid);
	}
		
	//查询员工列表
	public List<EmployeeEntity> listEmployeeEntity(Map<String,Object> queryMap) throws Exception {
		return employeeMapper.listEmployeeEntity(queryMap);
	}

	//查询员工总数量
	public int getEmployeeTotalCount(Map<String, Object> queryMap) throws Exception {
		return employeeMapper.getEmployeeTotalCount(queryMap);
	}

}

