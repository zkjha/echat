

/**
 * fun:
 * author:qidongbo
 * create time:2017年3月21日
 */
package com.ecard.mapper;

import java.util.List;
import java.util.Map;

import com.ecard.entity.GoodsInfoEntity;
import com.ecard.entity.GoodsPreferentialEntity;

/**
 * @author apple
 *
 */
public interface GoodsInfoMapper {
	//分页获取列表
	public List<GoodsInfoEntity> getListGoodsInfo(Map<String, Object> queryMap) throws RuntimeException;

	//获取单个对象
	public GoodsInfoEntity getGoodsInfo(String strGoodsId) throws RuntimeException;

	//新增单条记录
	public int insertGoodsInfo(GoodsInfoEntity bean) throws RuntimeException;

	//更新单条记录
	public int updateGoodsInfo(GoodsInfoEntity obj) throws RuntimeException;

	//删除单条记录
	public int deleteGoodsInfoById(String strGoodsId) throws RuntimeException;

	//查询总记录数
	public int getGoodsInfoTotalCount(Map<String, Object> queryMap) throws RuntimeException;
	
	//判断某种类型下的商品存在的数量
	int getGoodsInfoCountByGoodsType(String strGoodsTypeId) throws Exception;
	
	
	
	//新增加商品会员优惠信息
	public int insertGoodsPreferential(GoodsPreferentialEntity obj) throws RuntimeException;
	
	//更新商品会员优惠信息
	public int updateGoodsPreferential(GoodsPreferentialEntity obj) throws RuntimeException;
	
	//根据商品ID查询会员优惠信息列表
	public List<GoodsPreferentialEntity> getGoodsPreferentialByGoodsId(String strGoodsId) throws RuntimeException;

}
