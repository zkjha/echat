package com.ecard.service;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.commontools.data.DataTool;
import com.ecard.config.ResultCode;
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
	public String insertEmployee(EmployeeEntity employeeEntity) throws Exception {
		
		String flag = employeeMapper.judgeEmployeeLoginNameIsExist(employeeEntity.getStrEmployeeid(), employeeEntity.getStrLoginname());
		if("false".equals(flag)) {
			employeeMapper.insertEmployee(employeeEntity);
			return DataTool.constructResponse(ResultCode.OK, "新增成功", null);
		} else {
			//该职务名称存在
			return DataTool.constructResponse(ResultCode.CANTNOTREPEAT_PARAM_ISREPEAT, "登录名已经存在", null);
		}
		
	}
	
	//修改员工
	public String updateEmployee(EmployeeEntity employeeEntity) throws Exception {
		employeeMapper.updateEmployee(employeeEntity);
		return DataTool.constructResponse(ResultCode.OK, "修改成功", null);
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

