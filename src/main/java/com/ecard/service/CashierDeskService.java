package com.ecard.service;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.commontools.validate.ValidateTool;
import com.ecard.entity.GoodsInfoEntity;
import com.ecard.entity.ServiceInfoEntity;
import com.ecard.mapper.CashierDeskMapper;
import com.ecard.vo.MemberVO;

@Service
public class CashierDeskService
{
	@Autowired
	private CashierDeskMapper cashierDeskMapper;
	//根据手机号或会员卡号、姓名搜索会员信息
	public List<MemberVO> selectMemberInfoFromSearch(String searchText) throws Exception
	{
		if(ValidateTool.isEmptyStr(searchText))
			return null;
		else
			return cashierDeskMapper.selectMemberInfoFromSearch(searchText);
		
	}
	
	@Transactional
	//查询服务信息详情ServiceInfoEntity记录数量
	public int getServiceInfoEntityTotalRecordCount(Map<String,Object> queryMap) throws Exception
	{
		return cashierDeskMapper.getServiceInfoEntityTotalRecordCount(queryMap);
	}

	//查询服务信息详情ServiceInfoEntity列表
	public List<ServiceInfoEntity> selectAllServiceInfoEntity(Map<String,Object> queryMap) throws Exception
	{
		return cashierDeskMapper.selectAllServiceInfoEntity(queryMap);
	}
	
	//查询商品信息详情GoodsInfoEntity记录数量
	public int getGoodsInfoEntityTotalRecordCount(Map<String,Object> queryMap) throws Exception
	{
		return cashierDeskMapper.getGoodsInfoEntityTotalRecordCount(queryMap);
	}

	//查询商品信息详情GoodsInfoEntity列表
	public List<GoodsInfoEntity> selectAllGoodsInfoEntity(Map<String,Object> queryMap) throws Exception
	{
		return cashierDeskMapper.selectAllGoodsInfoEntity(queryMap);
	}
	
	
	
}
