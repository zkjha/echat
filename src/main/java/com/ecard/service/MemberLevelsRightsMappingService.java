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
	public String insertMemberLevelsRightsMappingInfo(MemberLevelsRightsMappingEntity memberLevelsRightsMappingEntity,String strRightsName,String strLevelsName)
	{	
		//查出会员拥有的权益ID
		String strRightsId;
		String strLevelsId;
		int rcdNum;
		int iRightsStatus=memberLevelsRightsMappingEntity.getIrightsStatus();	//取出权益状态：0表购买商品 1 表购买服务
		try{
			if(iRightsStatus==0)
				strRightsId=memberLevelsRightsMapper.findRightsIdByTbGoods(strRightsName);	//查商品表tb_goods，找出strGoodsId
			else
				strRightsId=memberLevelsRightsMapper.findRightsIdByTbServiceType(strRightsName);	//查服务表tb_service_type找出strServiceInfoId
		}catch(Exception e)
		{
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
		}
		//查询会员拥有的权益ID完毕
		
		//查出会员级别所对应的级别ID
		try{
			strLevelsId=memberLevelsRightsMapper.findLevelsIdByStrLevelsName(strLevelsName);
		}catch(Exception e)
		{
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
		}
		//会员级别所对应的级别ID查询完毕
		
		//将查到的值 进行校验，并装入实体
		if(ValidateTool.isEmptyStr(strRightsId)||ValidateTool.isEmptyStr(strLevelsId))
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误:无关键字",null);
		memberLevelsRightsMappingEntity.setStrRightsId(strRightsId);
		memberLevelsRightsMappingEntity.setstrLevelsId(strLevelsId);
		//插入 级别权益数据
		try{
			rcdNum=memberLevelsRightsMapper.insertMemberLevelsRightsMappingInfo(memberLevelsRightsMappingEntity);
			
		}catch(Exception e)
		{
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
		}
		
		if(rcdNum==0)
			return DataTool.constructResponse(ResultCode.UNKNOW_ERROR,"插入失败",null);
		else
			return DataTool.constructResponse(ResultCode.OK,"插入成功",null);
			
	}

}
