package com.ecard.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;


import com.ecard.entity.RechargePresentsActivityEntity;
import com.ecard.entity.RechargePresentsIntegrationEntity;
import com.ecard.entity.RechargePresentsStoredValueEntity;
import com.ecard.entity.RechargePresentsVoucherEntity;

public interface RechargePresentsMapper {
	//批量新增充值赠送抵用券信息
	public int batchInsertRechargePresentsVoucher(@Param("listRechargePresentsVoucherEntity")List<RechargePresentsVoucherEntity> listRechargePresentsVoucherEntity) throws Exception;
	//更新 充值赠送抵用券信息
	public int updateRechargePresentsVoucher(RechargePresentsVoucherEntity rechagePresentsVoucherEntity) throws Exception;
	//删除 充值赠送抵用券信息
	public int deleteRechargePresentsVoucher(String strRechargePresentsVoucherId) throws Exception;
	//查询 充值赠送抵用券信息
	public List<RechargePresentsVoucherEntity> selectAllRechargePresentsVoucher(String strActivityId) throws Exception;
	//批量新增 充值送储值信息
	public int batchInsertRechargePresentsStoredValue(@Param("listRechargePresentsStoredValueEntity")List<RechargePresentsStoredValueEntity> listRechargePresentsStoredValueEntity) throws Exception;
	//批量修改 充值赠送抵用券信息
	public int updateRechargePresentsStoredValue(RechargePresentsStoredValueEntity rechargePresentsStoredValueEntity) throws Exception;
	//删除 充值赠送储值
	public int deleteRechargePresentsStoredValue(String strPresentsStoredValueId) throws Exception;
	//查询 充值赠送储值信息
	public List<RechargePresentsStoredValueEntity> selectAllRechargePresentsStoredValue(String strActivityId) throws Exception;
	//新增 充值赠送积分规则信息
	public int insertRechargePresentsIntegration(RechargePresentsIntegrationEntity rechargePresentsIntegrationEntity) throws Exception;
	//判断 赠送积分表中是否已经存在相关活动的已启用记录
	public String isExistsTheRecord(String strActivityId) throws Exception;
	//更新 充值赠送积分规则信息
	public int updateRechargePresentsIntegration(RechargePresentsIntegrationEntity rechargePresentsIntegrationEntity) throws Exception;
	//查询 充值赠送积分信息
	public List<RechargePresentsIntegrationEntity> selectAllRechargePresentsIntegration(String strActivityId) throws Exception;
	//新增 活动信息
	public int insertPresentsActivityInfo(RechargePresentsActivityEntity presentsActivityEntity) throws Exception;
	//查询 指定会员级别在指定活动类型下是否已经存在数据
	public int isExistsTheActivityRecord(RechargePresentsActivityEntity presentsActivityEntity) throws Exception;
	//判断该会员级别在该活动类型下是否已经存在规则数据，若已存在则报错，不存在则执行写入
	public String isExistsTheActivityRecordId(RechargePresentsActivityEntity presentsActivityEntity) throws Exception;
	//更新 活动信息
	public int updatePresentsActivityInfo(RechargePresentsActivityEntity presentsActivityEntity) throws Exception;
	//查询，活动表总记录条数
	public int findCount(Map<String,Object> queryMap) throws Exception;
	//查询，过期活动表总记录条数
	public int findExpiredCount(Map<String,Object> queryMap) throws Exception;
	
	//查询，正常活动表总记录条数
	public int findNormalCount(Map<String,Object> queryMap) throws Exception;
	
	//分页查询活动状态为"全部"的活动表及活动的所有属性（如：充值赠送积分，赠送抵用券，赠送储值 
	public List<RechargePresentsActivityEntity> selectRechargePresentsActivityInfo(Map<String,Object> queryMap) throws Exception;
	
	//分页查询 活动状态为"过期" 的活动表及活动的所有属性（如：充值赠送积分，赠送抵用券，赠送储值 
	public List<RechargePresentsActivityEntity> selectExpiredRechargePresentsActivityInfo(Map<String,Object> queryMap) throws Exception;
	
	//分页查询 活动状态为"正常" 的活动表及活动的所有属性（如：充值赠送积分，赠送抵用券，赠送储值 
	public List<RechargePresentsActivityEntity> selectNormalRechargePresentsActivityInfo(Map<String,Object> queryMap) throws Exception;
	//取得会员级别名称
	public String getLevelsNameById(String strLevelsId) throws Exception;
	//分页查询--删除 
	public int deleteRechargePresentsActivityInfo(String strActivityId) throws Exception;
	
	//分页查询--删除积分信息
	public void deleteRechargePresentsIntegration(String strActivityId) throws Exception;
	//分页查询--删除储值券信息
	public void batchDeleteRechargePresentsStoredValue(String strActivityId) throws Exception;
	//分页查询--删除抵用券信息
	public void batchDeleteRechargePresentsVoucher(String strActivityId) throws Exception;
	//查询活动信息详情单条
	public RechargePresentsActivityEntity selectAllRechargePresentsActivityEntity(String strActivityId) throws Exception;
	//查询刚写入的充值赠送活动信息
	public RechargePresentsActivityEntity selectRechargePresentsActivityEntity() throws Exception;
	//查询一条刚新建的充值赠送储值规则信息rechargePresentsStoredValueEntity记录
	public RechargePresentsStoredValueEntity selectRechargePresentsStoredValueEntity() throws Exception;
	
	
	
}
