package com.ecard.mapper;

import java.util.List;
import java.util.Map;

import com.ecard.entity.IntegralModRecord;
import com.ecard.entity.MemberEntity;
import com.ecard.entity.SignIntegrationRuleEntity;
import com.ecard.entity.WeiXinMemberSignEntity;
import com.ecard.vo.MemberVO;

public interface WeiXinMemberCenterMapper
{
	//查询登陆会员信息
	public MemberVO selectMemberInfo(String strMemberId) throws Exception;
	//查特定会员的已领用但还未使用的抵用券张数
	public int selectUnUsedTicketAmount(String strMemberId) throws Exception;
	//查特定会员的已使用还可继续使用的抵用券张数
	public int selectUsedTicketAmount(String strMemberId) throws Exception;
	//查询会员在今天是否有签到记录
	public int findCountByIdAndTime(WeiXinMemberSignEntity memberSignEntity) throws Exception;
	//查询最近一条会员签到记录
	public WeiXinMemberSignEntity selectMemberSignEntityById(WeiXinMemberSignEntity memberSignEntity) throws Exception;
	//插入签到信息
	public int insertMemberSignInfo(WeiXinMemberSignEntity memberSignEntity) throws Exception;
	//查非连续签到积分规则
	public List<SignIntegrationRuleEntity> selectIntegrationRuleInfo(Map<String,Object> queryMap) throws Exception;
	//向表tb_integral_change_record插入积分变更信息
	public int insertIntegrationChangedInfo(IntegralModRecord integrationChangedEntity) throws Exception;
	//在会员表里查询会员信息
	public MemberEntity selectMemberEntity(String strMemberId) throws Exception;
	//修改会员表信息
	public int updateMemberInfo(MemberEntity memberEntity) throws Exception;
	//查询连续签到积分规则
	public SignIntegrationRuleEntity selectRuleInfo(Map<String,Object> queryMap) throws Exception;
	//根据会员ID查出会员的历史签到总次数
	public int getSignDaysByMemberId(String strMemberId) throws Exception;
	//查询最近一次签到记录-单条（签到天数)
	public WeiXinMemberSignEntity selectSignDays(String strMemberId) throws Exception;
	//按月查询指定会员的签到记录
	public List<WeiXinMemberSignEntity> selectSignDaysByMonth(Map<String,Object> queryMap) throws Exception;
	
}
