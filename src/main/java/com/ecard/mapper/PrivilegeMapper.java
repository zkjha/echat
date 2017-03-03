package com.ecard.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ecard.entity.PrivilegeEntity;

/**
 * 权限操作mapper
 */
public interface PrivilegeMapper {
	
	//根据职务ID查询该职务所有的权限访问路径
	List<String> listDutyPrivilegeUrl(@Param("strDutyid")String strDutyid) throws Exception;

	//根据职务ID查询该职务所有的权限ID
	List<String> listAlreadyhavePrivilegeByDutyId(@Param("strDutyid")String strDutyid) throws Exception;

	//查询所有的权限信息
	List<PrivilegeEntity> listAllPrivilege() throws Exception;

	
}
