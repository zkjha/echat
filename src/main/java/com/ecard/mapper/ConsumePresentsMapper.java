package com.ecard.mapper;
import java.util.List;
import java.util.Map;

import com.ecard.entity.ConsumePresentsActivityEntity;
import com.ecard.entity.ConsumePresentsIntegrationEntity;
import com.ecard.entity.ConsumePresentsStoredValueEntity;
import com.ecard.entity.ConsumePresentsVoucherEntity;
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
	//查询一条消费赠送活动规则信息consumePresentsActivityEntity记录
	public ConsumePresentsActivityEntity selectConsumePresentsActivityEntity(String strActivityId) throws Exception;
	//查询一条刚新建的消费赠送活动规则信息consumePresentsActivityEntity记录
	public ConsumePresentsActivityEntity selectConsumePresentsActivityInfo() throws Exception;
	//批量插入消费赠送积分规则信息consumePresentsIntegrationEntity
	public int batchInsertConsumePresentsIntegrationEntity(ConsumePresentsIntegrationEntity consumePresentsIntegrationEntity) throws Exception;
	//批量更新消费赠送积分规则信息consumePresentsIntegrationEntity
	public int batchUpdateConsumePresentsIntegrationEntity(ConsumePresentsIntegrationEntity consumePresentsIntegrationEntity) throws Exception;
	//删除一条消费赠送积分规则信息consumePresentsIntegrationEntity记录
	public int deleteConsumePresentsIntegrationEntity(String strIntegrationId) throws Exception;
	//查询消费赠送积分规则信息ConsumePresentsIntegrationEntity列表
	public List<ConsumePresentsIntegrationEntity> selectAllConsumePresentsIntegrationEntity(String strActivity) throws Exception;
	//批量插入消费赠送储值规则信息consumePresentsStoredValueEntity
	public int batchInsertConsumePresentsStoredValueEntity(ConsumePresentsStoredValueEntity consumePresentsStoredValueEntity) throws Exception;
	//批量更新消费赠送储值规则信息consumePresentsStoredValueEntity
	public int batchUpdateConsumePresentsStoredValueEntity(ConsumePresentsStoredValueEntity consumePresentsStoredValueEntity) throws Exception;
	//删除一条消费赠送储值规则信息consumePresentsStoredValueEntity记录
	public int deleteConsumePresentsStoredValueEntity(String strStoredTicketId) throws Exception;
	//查询消费赠送储值规则信息ConsumePresentsStoredValueEntity列表
	public List<ConsumePresentsStoredValueEntity> selectAllConsumePresentsStoredValueEntity(String strActivity) throws Exception;
	//批量插入消费赠送抵用券规则信息consumePresentsVoucherEntity
	public int batchInsertConsumePresentsVoucherEntity(ConsumePresentsVoucherEntity consumePresentsVoucherEntity) throws Exception;
	//批量更新消费赠送抵用券规则信息consumePresentsVoucherEntity
	public int batchUpdateConsumePresentsVoucherEntity(ConsumePresentsVoucherEntity consumePresentsVoucherEntity) throws Exception;
	//删除一条消费赠送抵用券规则信息consumePresentsVoucherEntity记录
	public int deleteConsumePresentsVoucherEntity(String strConsumePresentsVoucherId) throws Exception;
	//查询消费赠送抵用券规则信息ConsumePresentsVoucherEntity列表
	public List<ConsumePresentsVoucherEntity> selectAllConsumePresentsVoucherEntity(String strActivity) throws Exception;
	
	//分页浏览---删除接口 删除 关联的积分，储值，抵用券规则信息
	public int deleteConsumePresentsIntegrationInfo(String strActivityId) throws Exception;
	public int deleteConsumePresentsStoredValueInfo(String strActivityId) throws Exception;
	public int deleteConsumePresentsVoucherInfo(String strActivityId) throws Exception;
	public int deleteConsumePresentsActivityInfo(String strActivityId) throws Exception;
	
	//查询消费赠送活动在特定会员级别及特定状态下的记录条数
	public int findTheRecordCount(Map<String,Object> queryMap) throws Exception;
	//查询消费赠送活动在特定会员级别及特定状态下的记录
	public List<ConsumePresentsActivityEntity> selectAllConsumePresentsActivity(Map<String,Object> queryMap) throws Exception;
	
	//查询关联特定活动下的积分，储值，抵用券启用情况
	public int selectIntegrationEnabled(String strActivityId) throws Exception;
	public int selectStoredTicketEnabled(String strActivityId) throws Exception;
	public int selectVoucherTicketEnabled(String strActivityId) throws Exception;
	
	//查询会员级别所对应的级别名称
	public String findMemberLevelNameById(String strLevelsId) throws Exception;
}
