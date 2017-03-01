package com.ecard.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecard.entity.MemberarticlesEntity;
import com.ecard.mapper.MemberarticlesMapper;

/**
 * 职务操作service
 */
@Service
@Transactional
public class MemberarticlesService {
	
	@Autowired
	private MemberarticlesMapper memberarticlesMapper;
	
	//新增会员章程资料
	@Transactional(rollbackFor=Exception.class)
	public void insertMemberarticles(MemberarticlesEntity memberarticlesEntity) throws Exception {
		//1.删除所有的会员章程资料信息
		memberarticlesMapper.deleteAllMemberarticles();
		//2.录入最新的会员章程资料信息
		memberarticlesMapper.insertMemberarticles(memberarticlesEntity);
	}
	
	//查询会员章程资料详细信息
	public MemberarticlesEntity getOneMemberarticles() throws Exception {
		return memberarticlesMapper.getOneMemberarticles();
	}
}

