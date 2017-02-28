package com.ecard.service;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecard.entity.PrivilegeEntity;
import com.ecard.mapper.DutyMapper;
import com.ecard.mapper.PrivilegeMapper;

/**
 * 权限操作service
 */
@Service
@Transactional
public class PrivilegeService {
	
	@Autowired
	private PrivilegeMapper privilegeMapper;
	@Autowired
	private DutyMapper dutyMapper;
	
	//新增权限
	public void insertPrivilege(PrivilegeEntity privilegeEntity) throws Exception {
		privilegeMapper.insertPrivilege(privilegeEntity);
	}
		
	//删除权限
	@Transactional(rollbackFor=Exception.class)
	public void deletePrivilege(@Param("strPrivilegeid")String strPrivilegeid) throws Exception {
		//删除权限
		privilegeMapper.deletePrivilege(strPrivilegeid);
		//删除权限和职务的关系
		dutyMapper.deleteDutyPrivilege(strPrivilegeid, null);
	}
	
	//修改权限
	public void updatePrivilege(PrivilegeEntity privilegeEntity) throws Exception {
		privilegeMapper.updatePrivilege(privilegeEntity);
	}
	
	//根据权限ID查询权限信息
	public PrivilegeEntity getPrivilegeById(String strPrivilegeid) throws Exception {
		return privilegeMapper.getPrivilegeById(strPrivilegeid);
	}
	
	//查询权限列表
	public List<PrivilegeEntity> listPrivilege(Map<String,Object> queryMap) throws Exception {
		return privilegeMapper.listPrivilege(queryMap);
	}
}

