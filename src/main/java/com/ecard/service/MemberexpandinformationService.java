package com.ecard.service;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecard.entity.MemberexpandinformationEntity;
import com.ecard.mapper.MemberexpandinformationMapper;

/**
 * 会员拓展资料操作service
 */
@Service
@Transactional
public class MemberexpandinformationService {
	
	@Autowired
	private MemberexpandinformationMapper memberexpandinformationMapper;
	
	//新增会员拓展资料
	public void insertMemberexpandinformation(MemberexpandinformationEntity memberexpandinformationEntity) throws Exception {
		memberexpandinformationMapper.insertMemberexpandinformation(memberexpandinformationEntity);
	}
		
	//修改会员拓展资料
	public void updateMemberexpandinformation(MemberexpandinformationEntity memberexpandinformationEntity) throws Exception {
		memberexpandinformationMapper.updateMemberexpandinformation(memberexpandinformationEntity);
	}
		
	//根据会员拓展资料ID查询拓展资料信息
	public MemberexpandinformationEntity getMemberexpandinformationById(String strInformationid) throws Exception {
		return memberexpandinformationMapper.getMemberexpandinformationById(strInformationid);
	}
		
	//查询会员拓展资料列表
	public List<MemberexpandinformationEntity> listMemberexpandinformation(Map<String,Object> queryMap) throws Exception {
		return memberexpandinformationMapper.listMemberexpandinformation(queryMap);
	}
	
	//查询会员拓展资料总数量
	public int getMemberexpandinformationTotalCount(Map<String, Object> queryMap) throws Exception {
		return memberexpandinformationMapper.getMemberexpandinformationTotalCount(queryMap);
	}
		
	//删除会员拓展资料
	public void deleteMemberexpandinformation(String strInformationid) throws Exception {
		memberexpandinformationMapper.deleteMemberexpandinformation(strInformationid);
	}

}

