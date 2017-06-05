package com.ecard.mapper;


import java.util.List;
import java.util.Map;

import com.ecard.entity.UserDefinedPresentsActivityEntity;
import com.ecard.entity.UserDefinedPresentsIntegrationEntity;
import com.ecard.entity.UserDefinedPresentsStoredValueEntity;
import com.ecard.entity.UserDefinedPresentsVoucherEntity;

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
	//批量插入自定义活动赠送抵用券信息userDefinedPresentsVoucherEntity
	public int insertUserDefinedPresentsVoucherEntity(UserDefinedPresentsVoucherEntity userDefinedPresentsVoucherEntity) throws Exception;
	//批量更新自定义活动赠送抵用券信息userDefinedPresentsVoucherEntity
	public int updateUserDefinedPresentsVoucherEntity(UserDefinedPresentsVoucherEntity userDefinedPresentsVoucherEntity) throws Exception;
	//查询自定义赠送抵用券UserDefinedPresentsVoucherEntity列表
	public List<UserDefinedPresentsVoucherEntity> selectAllUserDefinedPresentsVoucherEntity(String strActivityId) throws Exception;
	//删除一条自定义赠送抵用券信息userDefinedPresentsVoucherEntity记录
	public int deleteUserDefinedPresentsVoucherEntity(String strPresentsVoucherId) throws Exception;

	//查询自定义赠送活动在特定会员级别及特定状态下的记录条数
	public int findTheRecordCount(Map<String,Object> queryMap) throws Exception;
	//查询自定义赠送活动在特定会员级别及特定状态下的记录
	public List<UserDefinedPresentsActivityEntity> selectAllUserDefinedPresentsActivity(Map<String,Object> queryMap) throws Exception;

}
