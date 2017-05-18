package com.ecard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.commontools.data.DataTool;
import com.commontools.validate.ValidateTool;
import com.ecard.config.ResultCode;
import com.ecard.entity.MemberLevelsRightsMappingEntity;
import com.ecard.mapper.MemberLevelsRightsMapper;

@Service
public class MemberLevelsRightsMappingService {
	@Autowired
	private MemberLevelsRightsMapper memberLevelsRightsMapper;
	
	//新增 会员等级及其对应权益关系信息
	@Transactional
	public String insertMemberLevelsRightsMappingInfo(MemberLevelsRightsMappingEntity memberLevelsRightsMappingEntity)
	{	
		
		int rcdNum;
		//插入 级别权益数据
		try{
			rcdNum=memberLevelsRightsMapper.insertMemberLevelsRightsMappingInfo(memberLevelsRightsMappingEntity);
			if(rcdNum==0)
				return DataTool.constructResponse(ResultCode.UNKNOW_ERROR,"插入失败",null);
			else
				return DataTool.constructResponse(ResultCode.OK,"插入成功",null);
			
		}catch(Exception e)
		{
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
		}
	}

}
