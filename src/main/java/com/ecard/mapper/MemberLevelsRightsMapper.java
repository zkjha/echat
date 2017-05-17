package com.ecard.mapper;

import com.ecard.entity.MemberLevelsRightsMappingEntity;

public interface MemberLevelsRightsMapper {
	//查出 权益ID
	public String findRightsIdByTbGoods(String strRightsName) throws Exception;
	//查出 权益ID
	public String findRightsIdByTbServiceType(String strRightsName) throws Exception;
	//查出会员级别所对应的级别ID
	public String findLevelsIdByStrLevelsName(String strLevelsName) throws Exception;
	//新增 会员等级及其对应权益关系信息
	public int insertMemberLevelsRightsMappingInfo(MemberLevelsRightsMappingEntity memberLevelsRightsMappingEntity);

}
