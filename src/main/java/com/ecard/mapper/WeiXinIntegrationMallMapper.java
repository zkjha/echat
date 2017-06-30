package com.ecard.mapper;

import java.util.List;
import java.util.Map;

import com.ecard.vo.GoodsVO;

public interface WeiXinIntegrationMallMapper
{
	//根据会员ID查询会员的级别ID
	public String selectMemberLevelsId(String strMemberId) throws Exception;
	//查商品总条数
	public int selectGoodsAmount(Map<String,Object> queryMap) throws Exception;
	//查询商品信息
	public List<GoodsVO> selectGoordsInfo(Map<String,Object> queryMap) throws Exception;
	//查找商品等级权益信息
	public double findDiscountInfo(Map<String,Object> queryMap) throws Exception;
	//查找商品按等级优惠的积分信息
	public int findLevelsIntegrationInfo(Map<String,Object> queryMap) throws Exception;
	//根据商品ID 查询商品信息
	public GoodsVO selectGoordsInfoById(Map<String,Object> queryMap) throws Exception;
}
