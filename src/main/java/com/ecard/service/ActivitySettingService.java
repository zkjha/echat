

/**
 * fun:
 * author:qidongbo
 * create time:2017年3月23日
 */
package com.ecard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.commontools.data.DataTool;
import com.ecard.config.ResultCode;
import com.ecard.entity.IntegralclearRuleEntity;
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

}
