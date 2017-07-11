package com.ecard.mapper;

import java.util.List;
import java.util.Map;

import com.ecard.vo.OrderVo;

public interface WeiXinConsumeRecordMapper {
	
	//从表tb_orderDetail_info中查询用积分消费的订单记录
	public List<OrderVo> selectIntegrationConsumeFromOrderDetailInfo(Map<String,Object> queryMap) throws Exception;
	//从WeiXin_Goods_order表里查询用积分消费的订单记录
	public List<OrderVo> selectIntegrationConsumeFromWeiXinGoodsOrderInfo(Map<String,Object> queryMap) throws Exception;
	//从表tb_orderDetail_info中查询用储值消费的订单记录
	public List<OrderVo> selectStoredValueConsumeFromOrderDetailInfo(Map<String,Object> queryMap) throws Exception;
	//从WeiXin_Goods_order表里查询用储值 消费的订单记录
	public List<OrderVo> selectStoredValueConsumeFromWeiXinGoodsOrderInfo(Map<String,Object> queryMap) throws Exception;
	//从表tb_orderDetail_info中查询用现金，支付宝，微信消费的订单记录
	public List<OrderVo> selectCashConsumeFromOrderDetailInfo(Map<String,Object> queryMap) throws Exception;
	//从WeiXin_Goods_order表里查询用现金，支付宝，微信消费的订单记录
	public List<OrderVo> selectCashConsumeFromWeiXinGoodsOrderInfo(Map<String,Object> queryMap) throws Exception;
	//从表tb_orderDetail_info中查询用抵用券消费的订单记录
	public List<OrderVo> selectVoucherTicketConsumeFromOrderDetailInfo(Map<String,Object> queryMap) throws Exception;
	//从WeiXin_Goods_order表里查询用抵用券消费的订单记录
	public List<OrderVo> selectVoucherTicketConsumeFromWeiXinGoodsOrderInfo(Map<String,Object> queryMap) throws Exception;

}
