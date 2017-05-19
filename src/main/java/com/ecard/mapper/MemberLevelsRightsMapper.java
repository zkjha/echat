package com.ecard.mapper;

import java.util.List;
import java.util.Map;

import com.ecard.entity.MemberLevelsRightsMappingEntity;

public interface MemberLevelsRightsMapper {
	//新增 会员等级及其对应权益关系信息
	public int insertMemberLevelsRightsMappingInfo(MemberLevelsRightsMappingEntity memberLevelsRightsMappingEntity) throws Exception;
	//查出 会员等级及其权益关系总记录条数
	public int findTotalRecordCount() throws Exception;
	//分页查询会员等级及其权益信息
	public List<MemberLevelsRightsMappingEntity> selectAllMemberLevelsRightsMappingInfo(Map<String,Object> queryMap) throws Exception;
	//更新 等级权益信息表
	public int updateMemberLevelsRightsMappingInfo(MemberLevelsRightsMappingEntity memberLevelsRightsMappingEntity) throws Exception;
	//删除 等 级权益信息表
	public int deleteMemberLevelsRightsMappingInfo(String strLevelsRightsMappingId) throws Exception;
}
