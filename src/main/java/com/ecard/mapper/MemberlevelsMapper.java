package com.ecard.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ecard.entity.MemberlevelsEntity;

/**
 * 会员等级操作mapper
 */
public interface MemberlevelsMapper {
	
	//判断会员级别名称是否存在
	String judgeLevelsNameIsExist(@Param("strLevelsid")String strLevelsid, @Param("strLevelsname")String strLevelsname) throws Exception;
	
	//新增会员级别
	void insertMemberlevelsEntity(MemberlevelsEntity memberlevelsEntity) throws Exception;
	
	//修改会员级别
	void updateMemberlevelsEntity(MemberlevelsEntity memberlevelsEntity) throws Exception;
	
	//根据级别ID查询会员级别
	MemberlevelsEntity getMemberlevelsById(@Param("strLevelsid")String strLevelsid) throws Exception;
	
	//查询会员级别列表
	List<MemberlevelsEntity> listMemberlevels(Map<String,Object> queryMap) throws Exception;
	
	//查询会员级别总数量
	int getMemberlevelsTotalCount(Map<String,Object> queryMap) throws Exception;
	
	//禁用会员级别
	void forbiddenMemberlevels(@Param("strLevelsid")String strLevelsid) throws Exception;

	//查询某个会员级别是否被会员使用
	String judgeLevelsHaveMembers(@Param("strLevelsid")String strLevelsid) throws Exception;
}
