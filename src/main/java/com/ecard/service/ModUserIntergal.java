

/**
 * fun:
 * author:qidongbo
 * create time:2017年3月14日
 */
package com.ecard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.commontools.data.DataTool;
import com.ecard.config.ResultCode;
import com.ecard.entity.IntegralModRecord;
import com.ecard.entity.MemberEntity;
import com.ecard.mapper.IntegralRecordOperMapper;
import com.ecard.mapper.MemberMapper;

/**
 * @author apple
 *
 */
@Service
@Transactional
public class ModUserIntergal {

	@Autowired
	private IntegralRecordOperMapper integralRecordOperMapper;
	
	@Autowired
	private MemberMapper memMapper;
	
	@Transactional(rollbackFor=Exception.class)
	public String modMemberIntegral(IntegralModRecord integralModRecord) throws Exception {
		
		// 查询会员用户信息
		MemberEntity memEntity = memMapper.getMemberEntityById(integralModRecord.getStrMemberId());
		if (memEntity==null) {
			return DataTool.constructResponse(ResultCode.NO_DATA, "该会员不存在", null); 
		}
		integralModRecord.setStrMemberCardNum(memEntity.getStrMembercardnum());
		integralModRecord.setStrMemberName(memEntity.getStrRealname());
		
		// 增加用户积分
		memMapper.updateMemberIntegral(integralModRecord);
		// 向数据库中插入变更记录数据
		integralRecordOperMapper.insertNewRecord(integralModRecord);
		
		return DataTool.constructResponse(ResultCode.OK, "积分变更成功", null); 
	}
}
