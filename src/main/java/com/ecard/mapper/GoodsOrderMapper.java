package com.ecard.mapper;

import java.util.List;
import java.util.Map;

import com.ecard.entity.GoodsOrderEntity;

/**
 * 职务操作mapper
 */
public interface GoodsOrderMapper {
	
	//查询订单列表
	List<GoodsOrderEntity> listOrder(Map<String, Object> queryMap) throws Exception;

	//查询订单总数量
	int getOrderTotalCount(Map<String, Object> queryMap) throws Exception;
}
