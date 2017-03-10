package com.ecard.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ecard.entity.MemberEntity;
import com.ecard.vo.MemberVO;

/**
 * 会员操作mapper
 */
public interface MemberMapper {
	
	//判断手机号是否存在
	String judgeMobileIsExist(@Param("strMemberid")String strMemberid, @Param("strMobile")String strMobile) throws Exception;
	
	//根据手机号查询会员信息
	MemberEntity getMemberEntityByMobile(String strMobile) throws Exception;

	//根据会员ID查询会员详细信息
	MemberEntity getMemberEntityById(String strMemberid) throws Exception;

	//根据会员ID查询会员详细信息
	MemberEntity getLoginUserInfoById(String strMemberid) throws Exception;
	
	//查询会员列表
	List<MemberVO> listMember(Map<String, Object> queryMap) throws Exception;

	//查询会员总数量
	public int getMemberTotalCount(Map<String, Object> queryMap) throws Exception;
}
