package com.ecard.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ecard.entity.DutyEntity;
import com.ecard.entity.DutyPrivilegeEntity;

/**
 * 职务操作mapper
 */
public interface DutyMapper {
	
	//新增职务
	void insertDuty(DutyEntity dutyEntity) throws Exception;
	
	//修改职务
	void updateDuty(DutyEntity dutyEntity) throws Exception;
	
	//根据职务ID查询职务信息
	DutyEntity getDutyById(@Param("strDutyid")String strDutyid) throws Exception;
	
	//查询职务列表
	List<DutyEntity> listDuty(Map<String,Object> queryMap) throws Exception;
	
	//新增职务权限关系
	void insertDutyPrivilege(DutyPrivilegeEntity dutyPrivilegeEntity) throws Exception;
	
	//删除职务权限关系
	void deleteDutyPrivilege(@Param("strPrivilegeid")String strPrivilegeid, @Param("strDutyid")String strDutyid) throws Exception;
	
	//根据职务ID查询该职务所有的权限访问路径
	List<String> listDutyPrivilegeUrl(@Param("strDutyid")String strDutyid) throws Exception;
}
