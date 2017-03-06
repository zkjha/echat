package com.ecard.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ecard.entity.DutyEntity;
import com.ecard.entity.DutyPrivilegeEntity;

/**
 * 职务操作mapper
 */
public interface DutyMapper {
	
	//判断职务名称是否存在
	String judgeDutyNameIsExist(@Param("strDutyid")String strDutyid, @Param("strDutyname")String strDutyname) throws Exception;
	
	//新增职务
	void insertDuty(DutyEntity dutyEntity) throws Exception;
	
	//修改职务
	void updateDuty(DutyEntity dutyEntity) throws Exception;
	
	//根据职务ID查询职务信息
	DutyEntity getDutyById(@Param("strDutyid")String strDutyid) throws Exception;
	
	//查询职务列表
	List<DutyEntity> listDuty(@Param("pageFrom")int pageFrom, @Param("pageSize")int pageSize) throws Exception;
	
	//查询职务总数量
	int getDutyTotalCount() throws Exception;
	
	//新增职务权限关系
	void insertDutyPrivilege(DutyPrivilegeEntity dutyPrivilegeEntity) throws Exception;
	
	//批量新增职务权限关系
	void batchInsertDutyPrivilege(@Param("listPrivilegeEntity")List<DutyPrivilegeEntity> listPrivilegeEntity) throws Exception;
	
	//删除职务权限关系
	void deleteDutyPrivilege(@Param("strPrivilegeid")String strPrivilegeid, @Param("strDutyid")String strDutyid) throws Exception;
	
	//查询职务拥有的权限ID List
	List<String> listPrivilegeIdByDutyId(String strDutyid) throws Exception;
}
