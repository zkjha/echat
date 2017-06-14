package com.ecard.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.commontools.data.DataTool;
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
	
	//分页查询 会员等 级权益信息
	@Transactional
	public String selectAllMemberLevelsRightsMappingInfo(int iPageSize,int iPageNum,int iTotalPage,int iTotalRecord) throws Exception
	{
		//查出 等 级权益信息表中的记录总条数
		int iPageFrom;
		iTotalRecord=memberLevelsRightsMapper.findTotalRecordCount();
		if(iTotalRecord!=0)
		{
			iTotalPage=(iTotalRecord%iPageSize==0)?(iTotalRecord/iPageSize):(iTotalRecord/iPageSize+1);
			if(iPageNum>iTotalPage)
				iPageNum=iTotalPage;
			iPageFrom=(iPageNum-1)*iPageSize;
			Map<String,Object> queryMap=new HashMap<String,Object>();
			queryMap.put("iPageFrom",iPageFrom);
			queryMap.put("iPageSize",iPageSize);
			List<MemberLevelsRightsMappingEntity> listMemberLevelsRightsMappingEntity=memberLevelsRightsMapper.selectAllMemberLevelsRightsMappingInfo(queryMap);
			if(listMemberLevelsRightsMappingEntity==null)
				return DataTool.constructResponse(ResultCode.NO_DATA,"暂无数据",null);
			if(listMemberLevelsRightsMappingEntity.size()==0)
				return DataTool.constructResponse(ResultCode.NO_DATA,"暂无数据",null);
				
			Map<String,Object> resultMap=new HashMap<String,Object>();
			resultMap.put("iTotalRecord",iTotalRecord);
			resultMap.put("iTotalPage", iTotalPage);
			resultMap.put("listMemberLevelsRightsMappingEntity", listMemberLevelsRightsMappingEntity);
			return DataTool.constructResponse(ResultCode.OK,"查询成功",resultMap);
		}
		else
			return DataTool.constructResponse(ResultCode.NO_DATA,"查无数据据",null);
		
	}
	
	//更新 等级权益信息表
	public String updateMemberLevelsRightsMappingInfo(MemberLevelsRightsMappingEntity memberLevelsRightsMappingEntity) throws Exception
	{
		int iRcdNum=memberLevelsRightsMapper.updateMemberLevelsRightsMappingInfo(memberLevelsRightsMappingEntity);
		if(iRcdNum==0)
			return DataTool.constructResponse(ResultCode.UNKNOW_ERROR,"更新失败",null);
		else
			return DataTool.constructResponse(ResultCode.OK,"更新成功",null);
	}
	
	//删除 等 级权益信息表
	public String deleteMemberLevelsRightsMappingInfo(String strLevelsRightsMappingId) throws Exception
	{
		int iRcdNum=memberLevelsRightsMapper.deleteMemberLevelsRightsMappingInfo(strLevelsRightsMappingId);
		if(iRcdNum==0)
			return DataTool.constructResponse(ResultCode.UNKNOW_ERROR,"删除失败",null);
		else
			return DataTool.constructResponse(ResultCode.OK,"删除成功",null);
	
	}
	

}
