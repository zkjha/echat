package com.ecard.service;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecard.entity.GoodsOrderEntity;
import com.ecard.mapper.GoodsOrderMapper;

/**
 * 商品订单操作service
 */
@Service
@Transactional
public class GoodsOrderService {
	
	@Autowired
	private GoodsOrderMapper goodsOrderMapper;
	
	//查询订单列表
	public List<GoodsOrderEntity> listOrder(Map<String, Object> queryMap) throws Exception {
		return goodsOrderMapper.listOrder(queryMap);
	}

	//查询订单总数量
	public int getOrderTotalCount(Map<String, Object> queryMap) throws Exception {
		return goodsOrderMapper.getOrderTotalCount(queryMap);
	}
	
	
}

