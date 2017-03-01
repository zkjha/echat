package com.ecard.mapper;

import com.ecard.entity.MemberarticlesEntity;

/**
 * 会员章程操作mapper
 */
public interface MemberarticlesMapper {
	
	//新增会员章程资料
	void insertMemberarticles(MemberarticlesEntity memberarticlesEntity) throws Exception;
	
	//删除所有会员章程资料
	void deleteAllMemberarticles() throws Exception;
	
	//查询会员章程资料详细信息
	MemberarticlesEntity getOneMemberarticles() throws Exception;
}
