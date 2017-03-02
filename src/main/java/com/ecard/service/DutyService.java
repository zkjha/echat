package com.ecard.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.commontools.data.DataTool;
import com.ecard.config.ResultCode;
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
	
	
	//判断职务名称是否已经存在
	public String judgeDutyNameIsExist(String strDutyid, String strDutyname) throws Exception {
		return dutyMapper.judgeDutyNameIsExist(strDutyid, strDutyname);
	}
	
	//新增职务
	@Transactional(rollbackFor=Exception.class)
	public String insertDuty(DutyEntity dutyEntity, String[] arrPrivilegeid) throws Exception {
		
		String flag = dutyMapper.judgeDutyNameIsExist(dutyEntity.getStrDutyid(), dutyEntity.getStrDutyname());
		if("false".equals(flag)) {
			//该职务名称不存在
			//1.新增职务
			dutyMapper.insertDuty(dutyEntity);
			//2.新增职务权限关系
			List<DutyPrivilegeEntity> privilegeEntityList = new ArrayList<DutyPrivilegeEntity>();
			for(int i=0;i<arrPrivilegeid.length;i++) {
				DutyPrivilegeEntity dutyPrivilegeEntity = new DutyPrivilegeEntity();
				dutyPrivilegeEntity.setStrRelationid(DataTool.getUUID());
				dutyPrivilegeEntity.setStrDutyid(dutyEntity.getStrDutyid());
				dutyPrivilegeEntity.setStrPrivilegeid(arrPrivilegeid[i]);
				privilegeEntityList.add(dutyPrivilegeEntity);
			}
			dutyMapper.batchInsertDutyPrivilege(privilegeEntityList);
			return DataTool.constructResponse(ResultCode.OK, "新增成功", null);
		} else {
			//该职务名称存在
			return DataTool.constructResponse(ResultCode.CANTNOTREPEAT_PARAM_ISREPEAT, "职务名称已经存在", null);
		}
	}

	//修改职务
	@Transactional(rollbackFor=Exception.class)
	public String updateDuty(DutyEntity dutyEntity, String[] arrPrivilegeid) throws Exception {
		String flag = dutyMapper.judgeDutyNameIsExist(dutyEntity.getStrDutyid(), dutyEntity.getStrDutyname());
		if("false".equals(flag)) {
			//该职务名称不存在
			//1.修改职务
			dutyMapper.updateDuty(dutyEntity);
			//2.删除职务对应的所有权限
			dutyMapper.deleteDutyPrivilege(null, dutyEntity.getStrDutyid());
			//3.新增职务权限关系
			List<DutyPrivilegeEntity> privilegeEntityList = new ArrayList<DutyPrivilegeEntity>();
			for(int i=0;i<arrPrivilegeid.length;i++) {
				DutyPrivilegeEntity dutyPrivilegeEntity = new DutyPrivilegeEntity();
				dutyPrivilegeEntity.setStrRelationid(DataTool.getUUID());
				dutyPrivilegeEntity.setStrDutyid(dutyEntity.getStrDutyid());
				dutyPrivilegeEntity.setStrPrivilegeid(arrPrivilegeid[i]);
				privilegeEntityList.add(dutyPrivilegeEntity);
			}
			dutyMapper.batchInsertDutyPrivilege(privilegeEntityList);
			return DataTool.constructResponse(ResultCode.OK, "修改成功", null);
		} else {
			//该职务名称存在
			return DataTool.constructResponse(ResultCode.CANTNOTREPEAT_PARAM_ISREPEAT, "职务名称已经存在", null);
		}
		
	}
	
	//根据职务ID查询职务信息
	public DutyEntity getDutyById(String strDutyid) throws Exception {
		return dutyMapper.getDutyById(strDutyid);
	}
	
	//查询职务列表
	public List<DutyEntity> listDuty(int pageFrom, int pageSize) throws Exception {
		return  dutyMapper.listDuty(pageFrom, pageSize);
	}
	
	//查询职务总数量
	public int getDutyTotalCount() throws Exception {
		return dutyMapper.getDutyTotalCount();
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

