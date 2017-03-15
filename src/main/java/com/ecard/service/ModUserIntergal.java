

/**
 * fun:
 * author:qidongbo
 * create time:2017年3月14日
 */
package com.ecard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	public String InsertNewRecord(IntegralModRecord integralModRecord) throws Exception {
		
		// 根据会员卡号查询需要调整积分的用户基本信息写入到积分记录表中
		if (integralModRecord.getStrMemberId() != null)
		{
			// 查询会员用户信息
			MemberEntity memEntity = new MemberEntity();
			memEntity = memMapper.getMemberEntityById(integralModRecord.getStrMemberId());
			
			integralModRecord.setStrMemberCardNum(memEntity.getStrMembercardnum());
			integralModRecord.setStrMemberName(memEntity.getStrRealname());
			
			// 增加用户积分
			memMapper.updateMemberIntegral(integralModRecord);
		}
		else
		{
			return "用户会员卡号不能为空";
		}
		
		// 向数据库中插入数据
		integralRecordOperMapper.InsertNewRecord(integralModRecord);
		
		return null; 
	}
}
