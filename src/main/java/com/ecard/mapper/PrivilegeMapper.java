package com.ecard.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ecard.entity.PrivilegeEntity;

/**
 * 权限操作mapper
 */
public interface PrivilegeMapper {
	
	//新增权限
	void insertPrivilege(PrivilegeEntity privilegeEntity) throws Exception;
	
	//删除权限
	void deletePrivilege(@Param("strPrivilegeid")String strPrivilegeid) throws Exception;
	
	//修改权限
	void updatePrivilege(PrivilegeEntity privilegeEntity) throws Exception;
	
	//根据权限ID查询权限信息
	PrivilegeEntity getPrivilegeById(@Param("strPrivilegeid")String strPrivilegeid) throws Exception;
	
	//查询权限列表
	List<PrivilegeEntity> listPrivilege(Map<String,Object> queryMap) throws Exception;
	
}
