package com.ecard.service;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.commontools.data.DataTool;
import com.ecard.config.ResultCode;
import com.ecard.entity.MemberlevelsEntity;
import com.ecard.mapper.MemberlevelsMapper;

/**
 * 会员操作service
 */
@Service
@Transactional
public class MemberService {
	
	@Autowired
	private MemberlevelsMapper memberlevelsMapper;
	
	//判断会员级别名称是否存在
	public String judgeLevelsNameIsExist(String strLevelsid, String strLevelsname) throws Exception {
		return memberlevelsMapper.judgeLevelsNameIsExist(strLevelsid, strLevelsname);
	}
	
	//新增会员级别
	public String insertMemberlevelsEntity(MemberlevelsEntity memberlevelsEntity) throws Exception {
		String flag = memberlevelsMapper.judgeLevelsNameIsExist(memberlevelsEntity.getStrLevelsid(), memberlevelsEntity.getStrLevelsname());
		if("false".equals(flag)) {
			memberlevelsMapper.insertMemberlevelsEntity(memberlevelsEntity);
			return DataTool.constructResponse(ResultCode.OK, "新增成功", null);
		} else {
			//该会员级别名称存在
			return DataTool.constructResponse(ResultCode.CANTNOTREPEAT_PARAM_ISREPEAT, "会员级别名称已经存在", null);
		}
	}
	
	//修改会员级别
	public String updateMemberlevelsEntity(MemberlevelsEntity memberlevelsEntity) throws Exception {
		String flag = memberlevelsMapper.judgeLevelsNameIsExist(memberlevelsEntity.getStrLevelsid(), memberlevelsEntity.getStrLevelsname());
		if("false".equals(flag)) {
			memberlevelsMapper.updateMemberlevelsEntity(memberlevelsEntity);
			return DataTool.constructResponse(ResultCode.OK, "修改成功", null);
		} else {
			//该会员级别名称存在
			return DataTool.constructResponse(ResultCode.CANTNOTREPEAT_PARAM_ISREPEAT, "会员级别名称已经存在", null);
		}
	}
	
	//根据级别ID查询会员级别
	public MemberlevelsEntity getMemberlevelsById(String strLevelsid) throws Exception {
		return memberlevelsMapper.getMemberlevelsById(strLevelsid);
	}
	
	//查询会员级别列表
	public List<MemberlevelsEntity> listMemberlevels(Map<String,Object> queryMap) throws Exception {
		return memberlevelsMapper.listMemberlevels(queryMap);
	}
	
	//查询会员级别总数量
	public int getMemberlevelsTotalCount(Map<String,Object> queryMap) throws Exception {
		return memberlevelsMapper.getMemberlevelsTotalCount(queryMap);
	}
	
	//禁用会员级别
	@Transactional(rollbackFor=Exception.class)
	public String forbiddenMemberlevels(String strLevelsid) throws Exception {
		String flag = memberlevelsMapper.judgeLevelsHaveMembers(strLevelsid); //判断该级别是否有会员
		if("false".equals(flag)) {
			memberlevelsMapper.forbiddenMemberlevels(strLevelsid);
			return DataTool.constructResponse(ResultCode.OK, "禁用成功", null);
		} else {
			//该会员级别名称存在
			return DataTool.constructResponse(ResultCode.LEVELS_HAVE_MEMBERS, "该会员级别已经有会员使用", null);
		}
	}
	
}

