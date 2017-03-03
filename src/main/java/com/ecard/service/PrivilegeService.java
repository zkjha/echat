package com.ecard.service;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.commontools.validate.ValidateTool;
import com.ecard.entity.PrivilegeEntity;
import com.ecard.mapper.PrivilegeMapper;
import com.ecard.vo.FloorPrivilegeDTO;
import com.ecard.vo.FloorPrivilegeVO;
import com.ecard.vo.PrivilegeVO;
import com.ecard.vo.TopPrivilegeVO;

/**
 * 权限操作service
 */
@Service
@Transactional
public class PrivilegeService {
	
	@Autowired
	private PrivilegeMapper privilegeMapper;
	
	//根据职务ID查询该职务所有的权限访问路径
	public List<String> listDutyPrivilegeUrl(String strDutyid) throws Exception {
		return privilegeMapper.listDutyPrivilegeUrl(strDutyid);
	}

	//查询所有的权限信息并组装成前端需要的数据结构
	public List<PrivilegeVO> listAllPrivilege(String strDutyid) throws Exception {
		//1.如果strDutyid不为空查询该职务已经拥有的权限ID
		List<String> alreadyhavePrivileges = new LinkedList<String>();
		if(!ValidateTool.isEmptyStr(strDutyid)) {
			alreadyhavePrivileges = privilegeMapper.listAlreadyhavePrivilegeByDutyId(strDutyid);
		}
		
		List<PrivilegeEntity> privilegeList = privilegeMapper.listAllPrivilege();
		if(privilegeList==null||privilegeList.size()<=0) {
			return null;
		}
		
		List<PrivilegeVO> resultPrivilegeList = constructPrivilegeResult(alreadyhavePrivileges, privilegeList);
		return resultPrivilegeList;
	}

	//构造权限返回数据结构
	private List<PrivilegeVO> constructPrivilegeResult(List<String> alreadyhavePrivileges, List<PrivilegeEntity> privilegeList) {
		
		List<PrivilegeVO> resultPrivilegeList = new LinkedList<PrivilegeVO>();
		List<String> topmenunameList = new ArrayList<String>();
		
		String strTopmenuname = "";
		for(int i=0;i<privilegeList.size();i++) {
			strTopmenuname = privilegeList.get(i).getStrTopmenuname();
			if(!topmenunameList.contains(strTopmenuname)) {
				PrivilegeVO privilegeVO = new PrivilegeVO();
				privilegeVO.setStrTopmenuname(strTopmenuname);
				List<TopPrivilegeVO> topPrivilegeList = constructtopPrivilegeList(strTopmenuname, privilegeList,alreadyhavePrivileges);
				privilegeVO.setTopPrivilegeList(topPrivilegeList);
				topmenunameList.add(privilegeList.get(i).getStrTopmenuname());
				resultPrivilegeList.add(privilegeVO);
			}
		}
		return resultPrivilegeList;
	}

	//根据顶层菜单名称构造改菜单下的所有的顶层权限
	private List<TopPrivilegeVO> constructtopPrivilegeList(String strTopmenuname, List<PrivilegeEntity> privilegeList, List<String> alreadyhavePrivileges) {
		List<TopPrivilegeVO> topPrivilegeList = new LinkedList<TopPrivilegeVO>();
		PrivilegeEntity privilegeEntity = null;
		for(int i=0;i<privilegeList.size();i++) {
			privilegeEntity = privilegeList.get(i);
			if(strTopmenuname.equals(privilegeEntity.getStrTopmenuname())&&"top".equals(privilegeEntity.getStrParentid())) {
				TopPrivilegeVO topPrivilegeVO = new TopPrivilegeVO();
				topPrivilegeVO.setStrPrivilegeid(privilegeEntity.getStrPrivilegeid());
				topPrivilegeVO.setStrPrivilegename(privilegeEntity.getStrPrivilegename());
				FloorPrivilegeDTO floorPrivilegeDTO = constructFloorPrivilegeList(privilegeEntity.getStrPrivilegeid(), privilegeList, alreadyhavePrivileges);
				topPrivilegeVO.setChecked(floorPrivilegeDTO.isChecked());
				topPrivilegeVO.setChildrenPrivilegeList(floorPrivilegeDTO.getChildrenPrivilegeList());
				topPrivilegeList.add(topPrivilegeVO);
			}
		}
		return topPrivilegeList;
	}

	//根据顶层权限ID构造改权限下的子权限信息
	private FloorPrivilegeDTO constructFloorPrivilegeList(String strPrivilegeid, List<PrivilegeEntity> privilegeList, List<String> alreadyhavePrivileges) {
		FloorPrivilegeDTO floorPrivilegeDTO = new FloorPrivilegeDTO();
		List<FloorPrivilegeVO> floorPrivilegeList = new LinkedList<FloorPrivilegeVO>();
		PrivilegeEntity privilegeEntity = null;
		boolean parentIschecked = true;
		for(int i=0;i<privilegeList.size();i++) {
			privilegeEntity = privilegeList.get(i);
			if(strPrivilegeid.equals(privilegeEntity.getStrParentid())) {
				FloorPrivilegeVO floorPrivilegeVO = new FloorPrivilegeVO();
				floorPrivilegeVO.setStrPrivilegeid(privilegeEntity.getStrPrivilegeid());
				floorPrivilegeVO.setStrParentid(strPrivilegeid);
				floorPrivilegeVO.setStrPrivilegename(privilegeEntity.getStrPrivilegename());
				if(alreadyhavePrivileges.contains(privilegeEntity.getStrPrivilegeid())) {
					//已经有该权限了
					floorPrivilegeVO.setChecked(true);
				} else {
					parentIschecked = false;
				}
				floorPrivilegeList.add(floorPrivilegeVO);
			}
		}
		floorPrivilegeDTO.setChecked(parentIschecked);
		floorPrivilegeDTO.setChildrenPrivilegeList(floorPrivilegeList);
		return floorPrivilegeDTO;
	}
	
}

