package com.ecard.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ecard.entity.MemberexpandinformationEntity;

/**
 * 会员拓展资料操作mapper
 */
public interface MemberexpandinformationMapper {
	
	//新增会员拓展资料
	void insertMemberexpandinformation(MemberexpandinformationEntity memberexpandinformationEntity) throws Exception;
	
	//修改会员拓展资料
	void updateMemberexpandinformation(MemberexpandinformationEntity memberexpandinformationEntity) throws Exception;
	
	//根据会员拓展资料ID查询拓展资料信息
	MemberexpandinformationEntity getMemberexpandinformationById(@Param("strInformationid")String strInformationid) throws Exception;
	
	//查询会员拓展资料列表
	List<MemberexpandinformationEntity> listMemberexpandinformation(Map<String,Object> queryMap) throws Exception;
	
	//删除会员拓展资料
	void deleteMemberexpandinformation(@Param("strInformationid")String strInformationid) throws Exception;
	
}
