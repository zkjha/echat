

/**
 * fun:
 * author:qidongbo
 * create time:2017年3月23日
 */
package com.ecard.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.commontools.data.DataTool;
import com.ecard.config.ResultCode;
import com.ecard.entity.IntegralclearRuleEntity;
import com.ecard.entity.StoredticketRuleEntity;
import com.ecard.entity.VoucherticketRuleEntity;
import com.ecard.mapper.ActivitySettingMapper;

/**
 * @author apple
 *
 */
@Service
@Transactional
public class ActivitySettingService {
	
	@Autowired
	private ActivitySettingMapper tActivitySettingMapper;
	
	@Transactional(rollbackFor=Exception.class)
	public IntegralclearRuleEntity getIntegralclearRule() throws Exception{
	    return tActivitySettingMapper.getIntegralclearRule();
	}
	
	//更新一条IntegralclearRule记录
	@Transactional(rollbackFor=Exception.class)
	public String updateIntegralclearRule(IntegralclearRuleEntity tIntegralclearRule) throws Exception{
	    int iAffectNum = tActivitySettingMapper.updateIntegralclearRule(tIntegralclearRule);
	    if (0 == iAffectNum)
	    {
	        return DataTool.constructResponse(ResultCode.NO_DATA, "数据记录ID不存在", null);
	    }
	    return DataTool.constructResponse(ResultCode.OK,"修改成功", null);
	}
	
	// 更新一条储值卡规则信息
	@Transactional(rollbackFor=Exception.class)
	public String updateStoredticketRule(StoredticketRuleEntity tStoredticketRuleEntity) throws Exception{
		int iAffectNum = tActivitySettingMapper.updateStoredticketRule(tStoredticketRuleEntity);
	    if (0 == iAffectNum)
	    {
	        return DataTool.constructResponse(ResultCode.NO_DATA, "数据记录ID不存在", null);
	    }
	    return DataTool.constructResponse(ResultCode.OK,"修改成功", null);
	}
	
	// 查询所有储值卡规则信息
	@Transactional(rollbackFor=Exception.class)
	public List<StoredticketRuleEntity> getListStoredticketRule() throws Exception{
	    return tActivitySettingMapper.getListStoredticketRule();
	}
	
	
	//抵用券信息相关操作
	//获取一条VoucherticketRule记录
	@Transactional(rollbackFor=Exception.class)
	public VoucherticketRuleEntity getVoucherticketRule(String strVoucherTicketId) throws Exception{
	    return tActivitySettingMapper.getVoucherticketRule(strVoucherTicketId);
	}


	//新增一条VoucherticketRule记录
	@Transactional(rollbackFor=Exception.class)
	public String insertVoucherticketRule(VoucherticketRuleEntity tVoucherticketRule) throws Exception{
	    int iAffectNum = tActivitySettingMapper.insertVoucherticketRule(tVoucherticketRule);
	    if (0 == iAffectNum)
	    {
	        return DataTool.constructResponse(ResultCode.LACK_INTEGRAL, "数据库操作失败", null);
	    }
	    return DataTool.constructResponse(ResultCode.OK,"添加成功", null);
	}


	//更新一条VoucherticketRule记录
	@Transactional(rollbackFor=Exception.class)
	public String updateVoucherticketRule(VoucherticketRuleEntity tVoucherticketRule) throws Exception{
	    int iAffectNum = tActivitySettingMapper.updateVoucherticketRule(tVoucherticketRule);
	    if (0 == iAffectNum)
	    {
	        return DataTool.constructResponse(ResultCode.NO_DATA, "数据记录ID不存在", null);
	    }
	    return DataTool.constructResponse(ResultCode.OK,"修改成功", null);
	}


	//删除一条VoucherticketRule记录
	@Transactional(rollbackFor=Exception.class)
	public String deleteVoucherticketRule(String strVoucherTicketId) throws Exception{
	    int iAffectNum = tActivitySettingMapper.deleteVoucherticketRuleById(strVoucherTicketId);
	    if (0 == iAffectNum)
	    {
	        return DataTool.constructResponse(ResultCode.NO_DATA, "无数据", null);
	    }
	    return DataTool.constructResponse(ResultCode.OK,"删除成功", null);
	}


	//获取VoucherticketRule列表
	public List<VoucherticketRuleEntity> getListVoucherticketRule(Map<String, Object> queryMap) throws Exception{
	    return tActivitySettingMapper.getListVoucherticketRule(queryMap);
	}


	//获取VoucherticketRule记录数量
	public int getVoucherticketRuleTotalCount(Map<String, Object> queryMap) throws Exception{
	    return tActivitySettingMapper.getVoucherticketRuleTotalCount(queryMap);
	}
	
	
}
