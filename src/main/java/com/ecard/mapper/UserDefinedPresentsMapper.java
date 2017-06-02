package com.ecard.mapper;

import com.ecard.entity.UserDefinedPresentsActivityEntity;
import com.ecard.entity.UserDefinedPresentsIntegrationEntity;
import com.ecard.entity.UserDefinedPresentsStoredValueEntity;

public interface UserDefinedPresentsMapper {
	//查询 指定会员级别在指定活动类型下是否已经存在数据(新增活动数据时调用)
	public int isExistsTheActivityRecord(UserDefinedPresentsActivityEntity userDefinedPresentsActivityEntity) throws Exception;
	//新增 活动信息
	public int insertUserDefinedPresentsActivityInfo(UserDefinedPresentsActivityEntity userDefinedPresentsActivityEntity) throws Exception;
	
	//判断该会员级别在该活动类型下是否已经存在规则数据，若已存在则报错，不存在则执行写入(更新活动数据时调用)
	public String isExistsTheActivityRecordId(UserDefinedPresentsActivityEntity userDefinedPresentsActivityEntity) throws Exception;
	//更新 活动信息
	public int updateUserDefinedPresentsActivityInfo(UserDefinedPresentsActivityEntity userDefinedPresentsActivityEntity) throws Exception;
	//删除 自定义活动送积分信息
	public int deleteUserDefinedPresentsIntegrationEntity(String strPresentsIntegrationId) throws Exception;
	
	//新增 自定义赠送积分规则信息
	public int insertUserDefinedPresentsIntegration(UserDefinedPresentsIntegrationEntity userDefinedPresentsIntegrationEntity) throws Exception;
	//判断 赠送积分表中是否已经存在相关活动的已启用记录,存在则更新，不存在则新增
	public String isExistsTheRecord(String strActivityId) throws Exception;
	//更新 自定义赠送积分规则信息
	public int updateUserDefinedPresentsIntegration(UserDefinedPresentsIntegrationEntity userDefinedPresentsIntegrationEntity) throws Exception;
	//查询 自定义活动单条信息
	public UserDefinedPresentsActivityEntity selectUserDefinedPresentActivityById(String strActivityId) throws Exception;
	
	//新增 自定义赠送储值信息之前，先查找关联此活动的记录是否存在，存在则取出
	public String findPresentsStoredValueId(String strActivity) throws Exception;
	//新增一条自定义赠送储值信息UserDefinedPresentsStoredValueEntity记录
	public int insertUserDefinedPresentsStoredValueEntity(UserDefinedPresentsStoredValueEntity userDefinedPresentsStoredValueEntity) throws Exception;
	//更新一条自定义赠送储值信息
	public int updateUserDefinedPresentsStoredValueEntity(UserDefinedPresentsStoredValueEntity userDefinedPresentsStoredValueEntity) throws Exception;
	//删除一条UserDefinedPresentsStoredValueEntity记录
	public int deleteUserDefinedPresentsStoredValueEntity(String strPresentsStoredValueId) throws Exception;
	//查询一条 自定义活动赠送储值 信息
	public UserDefinedPresentsStoredValueEntity selectAllUserDefinedPresentsStoredValueEntity(String strActivityId) throws Exception;
	//查询一条自定义赠送积分信息
	public UserDefinedPresentsIntegrationEntity selectAllUserDefinedPresentsIntegration(String strActivityId) throws Exception;

		

}
