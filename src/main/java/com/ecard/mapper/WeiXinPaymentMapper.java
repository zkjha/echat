package com.ecard.mapper;

import java.util.List;
import java.util.Map;

import com.ecard.entity.AreaCountyEntity;
import com.ecard.entity.CityEntity;
import com.ecard.entity.IntegralModRecord;
import com.ecard.entity.MemberEntity;
import com.ecard.entity.ProvinceEntity;
import com.ecard.entity.WeiXinGoodsOrderEntity;
import com.ecard.entity.WeiXinReceiveGoodsAddressEntity;
import com.ecard.vo.GoodsVO;

public interface WeiXinPaymentMapper
{
	//查会员信息
	public MemberEntity selectMemberInfo(String strMemberId) throws Exception;
	//生成订单 
	public int generateWeiXinOrder(WeiXinGoodsOrderEntity weiXinGoodsOrderEntity) throws Exception;
	//新增 收件人信息
	public int insertWeiXinReceiveGoodsAddressEntity(WeiXinReceiveGoodsAddressEntity weiXinReceiveGoodsAddressEntity) throws Exception;
	//查询商品信息
	public GoodsVO selectGoordsInfoById(String strGoodsId) throws Exception;
	//查找等级权益 优惠信息
	public double findDiscountInfo(Map<String,Object> queryMap) throws Exception;
	//查找商品的优惠积分信息
	public int findLevelsIntegrationInfo(Map<String,Object> queryMap) throws Exception;
	//根据会员ID查询订单信息
	public WeiXinGoodsOrderEntity selectWeiXinGoodsOrderEntity(String strOrderId) throws Exception;
	//查询订单所需积分
	public int selectOrderIntegration(Map<String,Object> queryMap) throws Exception;
	//查会员积分余额
	public MemberEntity selectMemberIntegrationInfo(Map<String,Object> queryMap) throws Exception;
	//更新订单信息
	public int updateOrderInfo(Map<String,Object> updateMap) throws Exception;
	//插入积分变更流水信息
	public int insertIntegrationChangedRecord(IntegralModRecord integralModRecord) throws Exception;
	//修改会员剩余积分信息
	public int updateMemberIntegrationInfo(Map<String,Object> queryMap) throws Exception;
	//查询订单总金额
	public double selectGoodsTotalCash(Map<String,Object> queryMap) throws Exception;
	//查询会员表所有属性
	public MemberEntity selectMemberDetailInfo(Map<String,Object> queryMap) throws Exception;
	//更新会员表储值 属性
	public int updateMemberBalance(Map<String,Object> updateMap) throws Exception;
	//查询全国省份--列表 
	public List<ProvinceEntity> selectProvince() throws Exception;
	//根据省份代码查询所有城市--列表
	public List<CityEntity> selectCityInfo(int iProvinceCode) throws Exception;
	//根据城市代码查询所有区县--列表
	public List<AreaCountyEntity> selectAreaCountyInfo(int iCityCode) throws Exception;
}
