package com.ecard.service;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecard.entity.MemberEntity;
import com.ecard.entity.MemberlevelsEntity;
import com.ecard.mapper.MemberMapper;
import com.ecard.vo.MemberVO;

/**
 * 会员操作service
 */
@Service
@Transactional
public class MemberService {
	
	@Autowired
	private MemberMapper memberMapper;
	
	//判断用户手机号是否存在
	public String judgeMobileIsExist(String strMemberid, String strMobile) throws Exception {
		return memberMapper.judgeMobileIsExist(strMemberid, strMobile);
	}

	//根据手机号查询会员信息
	public MemberEntity getMemberEntityByMobile(String strMobile) throws Exception {
		return memberMapper.getMemberEntityByMobile(strMobile);
	}

	//根据会员ID查询会员详细信息
	public MemberEntity getMemberEntityById(String strMemberid) throws Exception {
		return memberMapper.getMemberEntityById(strMemberid);
	}

	//查询会员ID查询会员登录信息
	public MemberEntity getLoginUserInfoById(String strMemberid) throws Exception {
		return memberMapper.getLoginUserInfoById(strMemberid);
	}
	
	//查询所有可用的会员级别
	public List<MemberlevelsEntity> listAllMemberLevels(int intStatus) throws Exception {
		return memberMapper.listAllMemberLevels(intStatus);
	}

	//查询会员列表
	public List<MemberVO> listMember(Map<String, Object> queryMap) throws Exception {
		return memberMapper.listMember(queryMap);
	}

	//查询会员总数量
	public int getMemberTotalCount(Map<String, Object> queryMap) throws Exception {
		return memberMapper.getMemberTotalCount(queryMap);
	}

}

