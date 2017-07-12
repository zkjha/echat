package com.ecard.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.commontools.data.DataTool;
import com.commontools.validate.ValidateTool;
import com.ecard.config.ResultCode;
import com.ecard.config.StaticValue;
import com.ecard.entity.MemberEntity;
import com.ecard.entity.RechargeOrderEntity;
import com.ecard.mapper.WeiXinRechargeCenterMapper;

@Service
public class WeiXinRechargeCenterService {
	@Autowired
	private WeiXinRechargeCenterMapper weiXinRechargeCenterMapper;
	//查询会员储值 信息
	@Transactional
	public String selectMemberInfo(String strMemberId) throws Exception
	{
		MemberEntity memberEntity=weiXinRechargeCenterMapper.selectMemberInfo(strMemberId);
		if(memberEntity==null)
			return DataTool.constructResponse(ResultCode.NO_DATA,"暂无数据",null);
		if("".equals(memberEntity.getStrMemberid()))
			return DataTool.constructResponse(ResultCode.NO_DATA,"暂无数据",null);
		Map<String,Object> resultMap=new HashMap<String,Object>();
		resultMap.put("memberEntity", memberEntity);
		return DataTool.constructResponse(ResultCode.OK,"查询成功",resultMap);
	}
	
	//查会员充值历史
	@Transactional
	public String selectMemberRechargeHistory(Map<String,Object> queryMap) throws Exception
	{
		int iPageNum=(Integer)queryMap.get("iPageNum");
		int iPageSize=(Integer)queryMap.get("iPageSize");
		int iTotalRecord=0;
		int iPageFrom=0;
		int iTotalPage=0;
		iTotalRecord=weiXinRechargeCenterMapper.selectCountByMemberId(queryMap);
		if(iTotalRecord==0)
			return DataTool.constructResponse(ResultCode.NO_DATA,"暂无数据",null);
		iTotalPage=iTotalRecord%iPageSize==0?iTotalRecord/iPageSize:iTotalRecord/iPageSize+1;
		if(iPageNum>iTotalPage)
			iPageNum=iTotalPage;
		iPageFrom=(iPageNum-1)*iPageSize;
		queryMap.put("iPageFrom",iPageFrom);
		List<RechargeOrderEntity> listRechargeOrderEntity=weiXinRechargeCenterMapper.selectMemberRechargeHistory(queryMap);
		if(listRechargeOrderEntity==null)
			return DataTool.constructResponse(ResultCode.NO_DATA,"暂无数据",null);
		if(listRechargeOrderEntity.size()==0)
			return DataTool.constructResponse(ResultCode.NO_DATA,"暂无数据",null);
		Map<String,Object> resultMap=new HashMap<String,Object>();
		resultMap.put("listRechargeOrderEntity", listRechargeOrderEntity);
		resultMap.put("iTotalRecord",iTotalRecord);
		resultMap.put("iTotalPage",iTotalPage);
		return DataTool.constructResponse(ResultCode.OK,"查询成功",resultMap);
	}
	

}
