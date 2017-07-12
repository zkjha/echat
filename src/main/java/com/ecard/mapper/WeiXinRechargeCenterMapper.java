package com.ecard.mapper;

import java.util.List;
import java.util.Map;

import com.ecard.entity.MemberEntity;
import com.ecard.entity.RechargeOrderEntity;

public interface WeiXinRechargeCenterMapper {
	//查询会员储值 信息
	public MemberEntity selectMemberInfo(String strMemberId) throws Exception;
	//查会员充值历史
	public List<RechargeOrderEntity> selectMemberRechargeHistory(Map<String,Object> queryMap) throws Exception;
	//查会员的充值历史记录条数
	public int selectCountByMemberId(Map<String,Object> queryMap) throws Exception;
}
