

/**
 * fun:
 * author:qidongbo
 * create time:2017年3月23日
 */
package com.ecard.mapper;

import java.util.List;
import java.util.Map;

import com.ecard.entity.IntegralclearRuleEntity;
import com.ecard.entity.StoredticketRuleEntity;
import com.ecard.entity.VoucherticketRuleEntity;

/**
 * @author apple
 *
 */
public interface ActivitySettingMapper {
	//获取单个对象
	public IntegralclearRuleEntity getIntegralclearRule() throws RuntimeException;

	//更新单条记录
	public int updateIntegralclearRule(IntegralclearRuleEntity obj) throws RuntimeException;
	
	//获取一条储值卡规则对象
	public StoredticketRuleEntity getStoredticketRule(String strTicketId) throws RuntimeException;
	
	//更新单条储值卡规则
	public int updateStoredticketRule(StoredticketRuleEntity obj) throws RuntimeException;
	
	//获取所有储值卡规则信息
	public List<StoredticketRuleEntity> getListStoredticketRule() throws RuntimeException;
	
	
	//抵用券使用规则相关操纵
	//分页获取列表
	public List<VoucherticketRuleEntity> getListVoucherticketRule(Map<String, Object> queryMap) throws RuntimeException;

	//获取单个对象
	public VoucherticketRuleEntity getVoucherticketRule(String strVoucherTicketId) throws RuntimeException;

	//新增单条记录
	public int insertVoucherticketRule(VoucherticketRuleEntity bean) throws RuntimeException;

	//更新单条记录
	public int updateVoucherticketRule(VoucherticketRuleEntity obj) throws RuntimeException;

	//删除单条记录
	public int deleteVoucherticketRuleById(String strVoucherTicketId) throws RuntimeException;

	//查询总记录数
	public int getVoucherticketRuleTotalCount(Map<String, Object> queryMap) throws RuntimeException;

	//查询记录是否存在
	public int isVoucherticketRuleExists(String strVoucherTicketId) throws RuntimeException;


	
	
}
