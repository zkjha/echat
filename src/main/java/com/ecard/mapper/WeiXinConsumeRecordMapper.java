package com.ecard.mapper;

import java.util.List;
import java.util.Map;

import com.ecard.vo.OrderVo;

public interface WeiXinConsumeRecordMapper {
	
	//从表tb_orderDetail_info中查询用积分消费的订单记录
	public List<OrderVo> selectIntegrationConsumeFromOrderDetailInfo(Map<String,Object> queryMap) throws Exception;
	//从WeiXin_Goods_order表里查询用积分消费的订单记录
	public List<OrderVo> selectIntegrationConsumeFromWeiXinGoodsOrderInfo(Map<String,Object> queryMap) throws Exception;

}
