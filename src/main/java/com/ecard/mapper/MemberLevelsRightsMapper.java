package com.ecard.mapper;

import com.ecard.entity.MemberLevelsRightsMappingEntity;

public interface MemberLevelsRightsMapper {
	//新增 会员等级及其对应权益关系信息
	public int insertMemberLevelsRightsMappingInfo(MemberLevelsRightsMappingEntity memberLevelsRightsMappingEntity);

}
