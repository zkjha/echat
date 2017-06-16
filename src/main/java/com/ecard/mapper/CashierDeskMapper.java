package com.ecard.mapper;

import java.util.List;
import java.util.Map;

import com.ecard.entity.GoodsInfoEntity;
import com.ecard.entity.ServiceInfoEntity;
import com.ecard.vo.MemberVO;

public interface CashierDeskMapper
{
	//根据手机号或会员卡号、姓名搜索会员信息
	public List<MemberVO> selectMemberInfoFromSearch(String searchText) throws Exception;
	//根据商品类别ID查具体的商品信息
	public List<GoodsInfoEntity> selectGoodsInfoEntity(Map<String,Object> queryMap) throws Exception;
	
	//查询服务信息详情ServiceInfoEntity记录数量
	public int getServiceInfoEntityTotalRecordCount(Map<String,Object> queryMap) throws Exception;
	//根据服务类别ID查具体的服务信息
	public List<ServiceInfoEntity> selectAllServiceInfoEntity(Map<String,Object> queryMap) throws Exception;
	
	//查询商品信息详情GoodsInfoEntity记录数量
	public int getGoodsInfoEntityTotalRecordCount(Map<String,Object> queryMap) throws Exception;
	//查询商品信息详情GoodsInfoEntity列表
	public List<GoodsInfoEntity> selectAllGoodsInfoEntity(Map<String,Object> queryMap) throws Exception;
	
}
