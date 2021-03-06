package com.ecard.mapper;

import java.util.List;
import java.util.Map;

import com.ecard.entity.GoodsInfoEntity;
import com.ecard.entity.IntegralModRecord;
import com.ecard.entity.MemberEntity;
import com.ecard.entity.MemberLevelsRightsMappingEntity;
import com.ecard.entity.PurchaseOrderDetailEntity;
import com.ecard.entity.PurchaseOrderEntity;
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
	//按会员ID搜索会员信息
	public MemberEntity selectMemberInfo(String strMemberId) throws Exception;
	//查找商品或服务的优惠信息
	public MemberLevelsRightsMappingEntity getPreferentialInfo(Map<String,Object> queryMap) throws Exception;
	//查询特定会员在特定级别下购买特定服务的历史记录
	public List<PurchaseOrderDetailEntity> selectPurchaseOrderInfo(Map<String,Object> queryMap) throws Exception;
	//写入订单详情信息
	public int generatePurchaseOrder(PurchaseOrderDetailEntity insertOrderEntityList) throws Exception;

	//根据商品ID 查询商品中否有优惠
	public GoodsInfoEntity selectGoodsPreferentialType(String strGoodsId) throws Exception;
	//取出兑换商品所需积分
	public int selectGoodsIntegration(Map<String,Object> queryMap) throws Exception;
	//根据服务 ID查询商品是否有优惠
	public ServiceInfoEntity selectServicePreferentialType(String strServiceInfoId) throws Exception;
	//根据服务 ID 查服务优惠表中的所需积分
	public int selectServiceIntegration(Map<String,Object> queryMap) throws Exception;
	//支付完毕 修改订单状态--详情表
	public int editOrderDetailPaymentStatus(Map<String,Object> orderStatusMap) throws Exception;
	//支付完毕 修改订单状态--总表
	public int editOrderPaymentStatus(Map<String,Object> orderStatusMap) throws Exception;
	//插入订单信息汇总信息
	public int insertOrderInfo(PurchaseOrderEntity PurchaseOrderEntity) throws Exception;
	
	//查询订单(总表)信息
	public PurchaseOrderEntity selectPurchaseOrder(String strOrderId) throws Exception;
	//根据会员ID查会员表和级别名称表
	public MemberVO selectMemberInfoById(String strMemberId) throws Exception;
	//根据订单号查询订单详情
	public List<PurchaseOrderDetailEntity>selectPurchaseOrderDetailEntity(String strMemberId) throws Exception;
	//删除订单详情
	public int deletePurchaseOrderDetailEntity(String strOrderId) throws Exception;
	//删除订单总表
	public int deletePurchaseOrderEntity(String strOrderId) throws Exception;
	
	//根据订单号 查询订单详情
	public List<PurchaseOrderDetailEntity> selectOrderDetailEntity(String strOrderId) throws Exception;
	//更新订单详情表 积分优惠信息
	public int updateOrderDetailInfo(PurchaseOrderDetailEntity OrderDetailEntity) throws Exception;
	//更新订单表积分优惠信息
	public int updateOrderInfo(Map<String,Object> updateMap) throws Exception;
	//修改会员表积分信息
	public int modifyMemberIntegrationInfo(Map<String,Object> orderStatusMap) throws Exception;
	//修改积分流水
	public int modifyIntegrationChangeFlow(IntegralModRecord integrationChangeEntity) throws Exception;
	//修改改会员表的现金余额属性
	public int modifyMemberDbalanceInfo(Map<String,Object> orderStatusMap) throws Exception;
	//根据会员ID查会员积分
	public int selectMemberIntegration(String strMemberId) throws Exception;
	
}
