package com.ecard.mapper;

import com.ecard.entity.ConsumePresentsActivityEntity;

public interface ConsumePresentsMapper
{
	//查询 指定会员级别在指定活动类型下是否已经存在数据(新增活动数据时调用)
	public int isExistsTheActivityRecord(ConsumePresentsActivityEntity consumePresentsActivityEntity) throws Exception;
	//新增 活动信息
	public int insertConsumePresentsActivityEntity(ConsumePresentsActivityEntity consumePresentsActivityEntity) throws Exception;
	
	//判断该会员级别在该活动类型下是否已经存在规则数据，若已存在则报错，不存在则执行写入(更新活动数据时调用)
	public String isExistsTheActivityRecordId(ConsumePresentsActivityEntity consumePresentsActivityEntity) throws Exception;
	//更新 消费赠送活动信息
	public int updateConsumePresentsActivityInfo(ConsumePresentsActivityEntity consumePresentsActivityEntity) throws Exception;
}
