package com.ecard.service;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecard.entity.DutyEntity;
import com.ecard.entity.DutyPrivilegeEntity;
import com.ecard.mapper.DutyMapper;

/**
 * 职务操作service
 */
@Service
@Transactional
public class DutyService {
	
	@Autowired
	private DutyMapper dutyMapper;
	
	//新增职务
	public void insertDuty(DutyEntity dutyEntity) throws Exception {
		dutyMapper.insertDuty(dutyEntity);
	}

	//修改职务
	public void updateDuty(DutyEntity dutyEntity) throws Exception {
		dutyMapper.updateDuty(dutyEntity);
	}
	
	//根据职务ID查询职务信息
	public DutyEntity getDutyById(String strDutyid) throws Exception {
		return dutyMapper.getDutyById(strDutyid);
	}
	
	//查询职务列表
	public List<DutyEntity> listDuty(Map<String,Object> queryMap) throws Exception {
		return  dutyMapper.listDuty(queryMap);
	}
	
	//新增职务权限关系
	public void insertDutyPrivilege(DutyPrivilegeEntity dutyPrivilegeEntity) throws Exception {
		dutyMapper.insertDutyPrivilege(dutyPrivilegeEntity);
	}
	
	//删除职务权限关系
	public void deleteDutyPrivilege(String strPrivilegeid, String strDutyid) throws Exception {
		dutyMapper.deleteDutyPrivilege(strPrivilegeid, strDutyid);
	}
	
	//根据职务ID查询该职务所有的权限访问路径
	public List<String> listDutyPrivilegeUrl(String strDutyid) throws Exception {
		return dutyMapper.listDutyPrivilegeUrl(strDutyid);
	}
}

