package com.ecard.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.commontools.data.DataTool;
import com.ecard.config.ResultCode;
import com.ecard.entity.PurchaseOrderDetailEntity;
import com.ecard.entity.VoucherTicketInfoEntity;
import com.ecard.entity.WeiXinGoodsOrderEntity;
import com.ecard.mapper.WeiXinConsumeRecordMapper;
import com.ecard.vo.OrderVo;

@Service
public class WeiXinConsumeRecordService {
	
	@Autowired
	private WeiXinConsumeRecordMapper weiXinConsumeRecordMapper;
	
	//分页查询积分消费记录
	@Transactional
	public String selectIntegrationConsume(Map<String,Object> queryMap) throws Exception
	{
		int iPageFromIndex=0;
		int iPageEndIndex=0;
		int iListMaxIndex=0;
		int iTotalRecord=0;
		int iTotalPage=0;
		int iPageSize=(Integer)queryMap.get("iPageSize");
		int iPageNum=(Integer)queryMap.get("iPageNum");
		int isEmptyListPurchaseOrderDetailEntity=0;
		int isEmptyListWeiXinGoodsOrderEntity=0;
		int iFlag=0;
		List<OrderVo> ListOrderVoEntity=new ArrayList<OrderVo>();
		List<OrderVo> listPurchaseOrderDetailEntity=weiXinConsumeRecordMapper.selectIntegrationConsumeFromOrderDetailInfo(queryMap);
		List<OrderVo> listWeiXinGoodsOrderEntity=weiXinConsumeRecordMapper.selectIntegrationConsumeFromWeiXinGoodsOrderInfo(queryMap);
		if(listPurchaseOrderDetailEntity==null)
			isEmptyListPurchaseOrderDetailEntity=1;
		if(listPurchaseOrderDetailEntity.size()==0)
			isEmptyListPurchaseOrderDetailEntity=1;
		
		if(listWeiXinGoodsOrderEntity==null)
			isEmptyListWeiXinGoodsOrderEntity=1;
		if(listWeiXinGoodsOrderEntity.size()==0)
			isEmptyListWeiXinGoodsOrderEntity=1;
		if(isEmptyListPurchaseOrderDetailEntity==1&&isEmptyListWeiXinGoodsOrderEntity==1)
			return DataTool.constructResponse(ResultCode.NO_DATA,"你还没有积分消费数据",null);
		//如果微信订单里有积分消费记录的情况:
		if(isEmptyListPurchaseOrderDetailEntity==1&&isEmptyListWeiXinGoodsOrderEntity==0)
			ListOrderVoEntity=listWeiXinGoodsOrderEntity;
		if(isEmptyListPurchaseOrderDetailEntity==0&&isEmptyListWeiXinGoodsOrderEntity==1)
			ListOrderVoEntity=listPurchaseOrderDetailEntity;
		
		if(isEmptyListPurchaseOrderDetailEntity==0&&isEmptyListWeiXinGoodsOrderEntity==0)
		{
			//两个表里都有数据的情况下:
			String strOrderDetailTime="";
			String strWeiXinOrderTime="";
			while(iFlag==0)
			{
				strOrderDetailTime=listPurchaseOrderDetailEntity.get(0).getStrCreationTime();
				strWeiXinOrderTime=listWeiXinGoodsOrderEntity.get(0).getStrCreationTime();
				if(strOrderDetailTime.compareTo(strWeiXinOrderTime)>=0)
				{
					OrderVo orderVo=new OrderVo();
					orderVo.setStrOrderId(listPurchaseOrderDetailEntity.get(0).getStrOrderId());
					orderVo.setStrMemberId(listPurchaseOrderDetailEntity.get(0).getStrMemberId());
					orderVo.setStrMemberName(listPurchaseOrderDetailEntity.get(0).getStrMemberName());
					orderVo.setStrMemberCardNumber(listPurchaseOrderDetailEntity.get(0).getStrMemberCardNumber());
					orderVo.setStrLevelsId(listPurchaseOrderDetailEntity.get(0).getStrLevelsId());
					orderVo.setStrGoodsOrServiceId(listPurchaseOrderDetailEntity.get(0).getStrGoodsOrServiceId());
					orderVo.setStrGoodsOrServiceName(listPurchaseOrderDetailEntity.get(0).getStrGoodsOrServiceName());
					orderVo.setiPurchaseAmount(listPurchaseOrderDetailEntity.get(0).getiPurchaseAmount());
					orderVo.setdPrice(listPurchaseOrderDetailEntity.get(0).getdPrice());
					orderVo.setStrUnitName(listPurchaseOrderDetailEntity.get(0).getStrUnitName());
					orderVo.setdPurchaseCashTotalAmount(listPurchaseOrderDetailEntity.get(0).getdPurchaseCashTotalAmount());
					orderVo.setdPreferentialPrice(listPurchaseOrderDetailEntity.get(0).getdPreferentialPrice());
					orderVo.setdPreferentialCashTotalAmount(listPurchaseOrderDetailEntity.get(0).getdPreferentialCashTotalAmount());
					orderVo.setiIntegrationAmount(listPurchaseOrderDetailEntity.get(0).getiIntegrationAmount());
					orderVo.setiPurchaseIntegrationAmount(listPurchaseOrderDetailEntity.get(0).getiPurchaseIntegrationAmount());
					orderVo.setiStatus(listPurchaseOrderDetailEntity.get(0).getiStatus());
					orderVo.setiPayStandard(listPurchaseOrderDetailEntity.get(0).getiPayStandard());
					orderVo.setStrPayTime(listPurchaseOrderDetailEntity.get(0).getStrPayTime());
					orderVo.setiPayType(listPurchaseOrderDetailEntity.get(0).getiPayType());
					orderVo.setStrThirdPartyTradeFlow(listPurchaseOrderDetailEntity.get(0).getStrThirdPartyTradeFlow());
					orderVo.setStrExpressNumber(listPurchaseOrderDetailEntity.get(0).getStrExpressNumber());
					orderVo.setStrExpressCompany(listPurchaseOrderDetailEntity.get(0).getStrExpressCompany());
					orderVo.setStrComment(listPurchaseOrderDetailEntity.get(0).getStrComment());
					orderVo.setiReceiveType(listPurchaseOrderDetailEntity.get(0).getiReceiveType());
					orderVo.setStrReceiveTime(listPurchaseOrderDetailEntity.get(0).getStrReceiveTime());
					orderVo.setStrCreationTime(listPurchaseOrderDetailEntity.get(0).getStrCreationTime());
					orderVo.setStrLastAccessedTime(listPurchaseOrderDetailEntity.get(0).getStrLastAccessedTime());
					ListOrderVoEntity.add(orderVo);
					
					listPurchaseOrderDetailEntity.remove(0);
					if(listPurchaseOrderDetailEntity!=null&&listPurchaseOrderDetailEntity.size()==0)
						{
							iFlag=1;
							isEmptyListPurchaseOrderDetailEntity=1;
						}
					
				}
				else
				{
					OrderVo orderVo=new OrderVo();
					orderVo.setStrOrderId(listWeiXinGoodsOrderEntity.get(0).getStrOrderId());
					orderVo.setStrMemberId(listWeiXinGoodsOrderEntity.get(0).getStrMemberId());
					orderVo.setStrMemberName(listWeiXinGoodsOrderEntity.get(0).getStrMemberName());
					orderVo.setStrMemberCardNumber(listWeiXinGoodsOrderEntity.get(0).getStrMemberCardNumber());
					orderVo.setStrLevelsId(listWeiXinGoodsOrderEntity.get(0).getStrLevelsId());
					orderVo.setStrGoodsOrServiceId(listWeiXinGoodsOrderEntity.get(0).getStrGoodsOrServiceId());
					orderVo.setStrGoodsOrServiceName(listWeiXinGoodsOrderEntity.get(0).getStrGoodsOrServiceName());
					orderVo.setiPurchaseAmount(listWeiXinGoodsOrderEntity.get(0).getiPurchaseAmount());
					orderVo.setdPrice(listWeiXinGoodsOrderEntity.get(0).getdPrice());
					orderVo.setStrUnitName(listWeiXinGoodsOrderEntity.get(0).getStrUnitName());
					orderVo.setdPurchaseCashTotalAmount(listWeiXinGoodsOrderEntity.get(0).getdPurchaseCashTotalAmount());
					orderVo.setdPreferentialPrice(listWeiXinGoodsOrderEntity.get(0).getdPreferentialPrice());
					orderVo.setdPreferentialCashTotalAmount(listWeiXinGoodsOrderEntity.get(0).getdPreferentialCashTotalAmount());
					orderVo.setiIntegrationAmount(listWeiXinGoodsOrderEntity.get(0).getiIntegrationAmount());
					orderVo.setiPurchaseIntegrationAmount(listWeiXinGoodsOrderEntity.get(0).getiPurchaseIntegrationAmount());
					orderVo.setiStatus(listWeiXinGoodsOrderEntity.get(0).getiStatus());
					orderVo.setiPayStandard(listWeiXinGoodsOrderEntity.get(0).getiPayStandard());
					orderVo.setStrPayTime(listWeiXinGoodsOrderEntity.get(0).getStrPayTime());
					orderVo.setiPayType(listWeiXinGoodsOrderEntity.get(0).getiPayType());
					orderVo.setStrThirdPartyTradeFlow(listWeiXinGoodsOrderEntity.get(0).getStrThirdPartyTradeFlow());
					orderVo.setStrExpressNumber(listWeiXinGoodsOrderEntity.get(0).getStrExpressNumber());
					orderVo.setStrExpressCompany(listWeiXinGoodsOrderEntity.get(0).getStrExpressCompany());
					orderVo.setStrComment(listWeiXinGoodsOrderEntity.get(0).getStrComment());
					orderVo.setiReceiveType(listWeiXinGoodsOrderEntity.get(0).getiReceiveType());
					orderVo.setStrReceiveTime(listWeiXinGoodsOrderEntity.get(0).getStrReceiveTime());
					orderVo.setStrCreationTime(listWeiXinGoodsOrderEntity.get(0).getStrCreationTime());
					orderVo.setStrLastAccessedTime(listWeiXinGoodsOrderEntity.get(0).getStrLastAccessedTime());
					ListOrderVoEntity.add(orderVo);
					
					listWeiXinGoodsOrderEntity.remove(0);
					if(listWeiXinGoodsOrderEntity!=null&&listWeiXinGoodsOrderEntity.size()==0)
						{
						iFlag=1;
						isEmptyListWeiXinGoodsOrderEntity=1;
						}
				}
			}
			
			//将比较时间后剩余的数据加入ListOrderVoEntity
			if(isEmptyListWeiXinGoodsOrderEntity==1&&isEmptyListPurchaseOrderDetailEntity==0)
			{
				//listPurchaseOrderDetailEntity不为空的情况
				for(int i=0;i<listPurchaseOrderDetailEntity.size();i++)
				{
					OrderVo orderVo=new OrderVo();
					orderVo.setStrOrderId(listPurchaseOrderDetailEntity.get(i).getStrOrderId());
					orderVo.setStrMemberId(listPurchaseOrderDetailEntity.get(i).getStrMemberId());
					orderVo.setStrMemberName(listPurchaseOrderDetailEntity.get(i).getStrMemberName());
					orderVo.setStrMemberCardNumber(listPurchaseOrderDetailEntity.get(i).getStrMemberCardNumber());
					orderVo.setStrLevelsId(listPurchaseOrderDetailEntity.get(i).getStrLevelsId());
					orderVo.setStrGoodsOrServiceId(listPurchaseOrderDetailEntity.get(i).getStrGoodsOrServiceId());
					orderVo.setStrGoodsOrServiceName(listPurchaseOrderDetailEntity.get(i).getStrGoodsOrServiceName());
					orderVo.setiPurchaseAmount(listPurchaseOrderDetailEntity.get(i).getiPurchaseAmount());
					orderVo.setdPrice(listPurchaseOrderDetailEntity.get(i).getdPrice());
					orderVo.setStrUnitName(listPurchaseOrderDetailEntity.get(i).getStrUnitName());
					orderVo.setdPurchaseCashTotalAmount(listPurchaseOrderDetailEntity.get(i).getdPurchaseCashTotalAmount());
					orderVo.setdPreferentialPrice(listPurchaseOrderDetailEntity.get(i).getdPreferentialPrice());
					orderVo.setdPreferentialCashTotalAmount(listPurchaseOrderDetailEntity.get(i).getdPreferentialCashTotalAmount());
					orderVo.setiIntegrationAmount(listPurchaseOrderDetailEntity.get(i).getiIntegrationAmount());
					orderVo.setiPurchaseIntegrationAmount(listPurchaseOrderDetailEntity.get(i).getiPurchaseIntegrationAmount());
					orderVo.setiStatus(listPurchaseOrderDetailEntity.get(i).getiStatus());
					orderVo.setiPayStandard(listPurchaseOrderDetailEntity.get(i).getiPayStandard());
					orderVo.setStrPayTime(listPurchaseOrderDetailEntity.get(i).getStrPayTime());
					orderVo.setiPayType(listPurchaseOrderDetailEntity.get(i).getiPayType());
					orderVo.setStrThirdPartyTradeFlow(listPurchaseOrderDetailEntity.get(i).getStrThirdPartyTradeFlow());
					orderVo.setStrExpressNumber(listPurchaseOrderDetailEntity.get(i).getStrExpressNumber());
					orderVo.setStrExpressCompany(listPurchaseOrderDetailEntity.get(i).getStrExpressCompany());
					orderVo.setStrComment(listPurchaseOrderDetailEntity.get(i).getStrComment());
					orderVo.setiReceiveType(listPurchaseOrderDetailEntity.get(i).getiReceiveType());
					orderVo.setStrReceiveTime(listPurchaseOrderDetailEntity.get(i).getStrReceiveTime());
					orderVo.setStrCreationTime(listPurchaseOrderDetailEntity.get(i).getStrCreationTime());
					orderVo.setStrLastAccessedTime(listPurchaseOrderDetailEntity.get(i).getStrLastAccessedTime());
					ListOrderVoEntity.add(orderVo);
				}
				
			}
			
			if(isEmptyListWeiXinGoodsOrderEntity==0&&isEmptyListPurchaseOrderDetailEntity==1)
			{
				//listWeiXinGoodsOrderEntity不为空的情况
				for(int i=0;i<listWeiXinGoodsOrderEntity.size();i++)
				{
					OrderVo orderVo=new OrderVo();
					orderVo.setStrOrderId(listWeiXinGoodsOrderEntity.get(i).getStrOrderId());
					orderVo.setStrMemberId(listWeiXinGoodsOrderEntity.get(i).getStrMemberId());
					orderVo.setStrMemberName(listWeiXinGoodsOrderEntity.get(i).getStrMemberName());
					orderVo.setStrMemberCardNumber(listWeiXinGoodsOrderEntity.get(i).getStrMemberCardNumber());
					orderVo.setStrLevelsId(listWeiXinGoodsOrderEntity.get(i).getStrLevelsId());
					orderVo.setStrGoodsOrServiceId(listWeiXinGoodsOrderEntity.get(i).getStrGoodsOrServiceId());
					orderVo.setStrGoodsOrServiceName(listWeiXinGoodsOrderEntity.get(i).getStrGoodsOrServiceName());
					orderVo.setiPurchaseAmount(listWeiXinGoodsOrderEntity.get(i).getiPurchaseAmount());
					orderVo.setdPrice(listWeiXinGoodsOrderEntity.get(i).getdPrice());
					orderVo.setStrUnitName(listWeiXinGoodsOrderEntity.get(i).getStrUnitName());
					orderVo.setdPurchaseCashTotalAmount(listWeiXinGoodsOrderEntity.get(i).getdPurchaseCashTotalAmount());
					orderVo.setdPreferentialPrice(listWeiXinGoodsOrderEntity.get(i).getdPreferentialPrice());
					orderVo.setdPreferentialCashTotalAmount(listWeiXinGoodsOrderEntity.get(i).getdPreferentialCashTotalAmount());
					orderVo.setiIntegrationAmount(listWeiXinGoodsOrderEntity.get(i).getiIntegrationAmount());
					orderVo.setiPurchaseIntegrationAmount(listWeiXinGoodsOrderEntity.get(i).getiPurchaseIntegrationAmount());
					orderVo.setiStatus(listWeiXinGoodsOrderEntity.get(i).getiStatus());
					orderVo.setiPayStandard(listWeiXinGoodsOrderEntity.get(i).getiPayStandard());
					orderVo.setStrPayTime(listWeiXinGoodsOrderEntity.get(i).getStrPayTime());
					orderVo.setiPayType(listWeiXinGoodsOrderEntity.get(i).getiPayType());
					orderVo.setStrThirdPartyTradeFlow(listWeiXinGoodsOrderEntity.get(i).getStrThirdPartyTradeFlow());
					orderVo.setStrExpressNumber(listWeiXinGoodsOrderEntity.get(i).getStrExpressNumber());
					orderVo.setStrExpressCompany(listWeiXinGoodsOrderEntity.get(i).getStrExpressCompany());
					orderVo.setStrComment(listWeiXinGoodsOrderEntity.get(i).getStrComment());
					orderVo.setiReceiveType(listWeiXinGoodsOrderEntity.get(i).getiReceiveType());
					orderVo.setStrReceiveTime(listWeiXinGoodsOrderEntity.get(i).getStrReceiveTime());
					orderVo.setStrCreationTime(listWeiXinGoodsOrderEntity.get(i).getStrCreationTime());
					orderVo.setStrLastAccessedTime(listWeiXinGoodsOrderEntity.get(i).getStrLastAccessedTime());
					ListOrderVoEntity.add(orderVo);
				}
			}
		}
		
		//处理分页问题
	
		iTotalRecord=ListOrderVoEntity.size();
		iTotalPage=iTotalRecord%iPageSize==0?iTotalRecord/iPageSize:iTotalRecord/iPageSize+1;
		if(iPageNum>iTotalPage)
			iPageNum=iTotalPage;
		iListMaxIndex=ListOrderVoEntity.size()-1;
		iPageFromIndex=(iPageNum-1)*iPageSize;
		iPageEndIndex=iPageFromIndex+iPageSize-1;
		List<OrderVo> returnListOrderVo=new ArrayList<OrderVo>();
		if(iListMaxIndex>=iPageEndIndex)
		{
			for(int i=0;i<=iListMaxIndex;i++)
			{
				if(i>=iPageFromIndex&&i<=iPageEndIndex)
					returnListOrderVo.add(ListOrderVoEntity.get(i));
			}
			
		}
		else
		{
			for(int i=0;i<=iListMaxIndex;i++)
			{
				if(i>=iPageFromIndex)
					returnListOrderVo.add(ListOrderVoEntity.get(i));
			}
		}
		Map<String,Object> resultMap=new HashMap<String,Object>();
		resultMap.put("returnListOrderVo", returnListOrderVo);
		resultMap.put("iTotalRecord",iTotalRecord);
		resultMap.put("iTotalPage",iTotalPage);
		return DataTool.constructResponse(ResultCode.OK,"查询成功",resultMap);
	}
	
	
	

	//分页查询储值 消费记录
	@Transactional
	public String selectStoredValueConsume(Map<String,Object> queryMap) throws Exception
	{
		int iPageFromIndex=0;
		int iPageEndIndex=0;
		int iListMaxIndex=0;
		int iTotalRecord=0;
		int iTotalPage=0;
		int iPageSize=(Integer)queryMap.get("iPageSize");
		int iPageNum=(Integer)queryMap.get("iPageNum");
		int isEmptyListPurchaseOrderDetailEntity=0;
		int isEmptyListWeiXinGoodsOrderEntity=0;
		int iFlag=0;
		List<OrderVo> ListOrderVoEntity=new ArrayList<OrderVo>();
		List<OrderVo> listPurchaseOrderDetailEntity=weiXinConsumeRecordMapper.selectStoredValueConsumeFromOrderDetailInfo(queryMap);
		List<OrderVo> listWeiXinGoodsOrderEntity=weiXinConsumeRecordMapper.selectStoredValueConsumeFromWeiXinGoodsOrderInfo(queryMap);
		if(listPurchaseOrderDetailEntity==null)
			isEmptyListPurchaseOrderDetailEntity=1;
		if(listPurchaseOrderDetailEntity.size()==0)
			isEmptyListPurchaseOrderDetailEntity=1;
		
		if(listWeiXinGoodsOrderEntity==null)
			isEmptyListWeiXinGoodsOrderEntity=1;
		if(listWeiXinGoodsOrderEntity.size()==0)
			isEmptyListWeiXinGoodsOrderEntity=1;
		if(isEmptyListPurchaseOrderDetailEntity==1&&isEmptyListWeiXinGoodsOrderEntity==1)
			return DataTool.constructResponse(ResultCode.NO_DATA,"你还没有储值消费数据",null);
		//如果微信订单里有积分消费记录的情况:
		if(isEmptyListPurchaseOrderDetailEntity==1&&isEmptyListWeiXinGoodsOrderEntity==0)
			ListOrderVoEntity=listWeiXinGoodsOrderEntity;
		if(isEmptyListPurchaseOrderDetailEntity==0&&isEmptyListWeiXinGoodsOrderEntity==1)
			ListOrderVoEntity=listPurchaseOrderDetailEntity;
		
		if(isEmptyListPurchaseOrderDetailEntity==0&&isEmptyListWeiXinGoodsOrderEntity==0)
		{
			//两个表里都有数据的情况下:
			String strOrderDetailTime="";
			String strWeiXinOrderTime="";
			while(iFlag==0)
			{
				strOrderDetailTime=listPurchaseOrderDetailEntity.get(0).getStrCreationTime();
				strWeiXinOrderTime=listWeiXinGoodsOrderEntity.get(0).getStrCreationTime();
				if(strOrderDetailTime.compareTo(strWeiXinOrderTime)>=0)
				{
					OrderVo orderVo=new OrderVo();
					orderVo.setStrOrderId(listPurchaseOrderDetailEntity.get(0).getStrOrderId());
					orderVo.setStrMemberId(listPurchaseOrderDetailEntity.get(0).getStrMemberId());
					orderVo.setStrMemberName(listPurchaseOrderDetailEntity.get(0).getStrMemberName());
					orderVo.setStrMemberCardNumber(listPurchaseOrderDetailEntity.get(0).getStrMemberCardNumber());
					orderVo.setStrLevelsId(listPurchaseOrderDetailEntity.get(0).getStrLevelsId());
					orderVo.setStrGoodsOrServiceId(listPurchaseOrderDetailEntity.get(0).getStrGoodsOrServiceId());
					orderVo.setStrGoodsOrServiceName(listPurchaseOrderDetailEntity.get(0).getStrGoodsOrServiceName());
					orderVo.setiPurchaseAmount(listPurchaseOrderDetailEntity.get(0).getiPurchaseAmount());
					orderVo.setdPrice(listPurchaseOrderDetailEntity.get(0).getdPrice());
					orderVo.setStrUnitName(listPurchaseOrderDetailEntity.get(0).getStrUnitName());
					orderVo.setdPurchaseCashTotalAmount(listPurchaseOrderDetailEntity.get(0).getdPurchaseCashTotalAmount());
					orderVo.setdPreferentialPrice(listPurchaseOrderDetailEntity.get(0).getdPreferentialPrice());
					orderVo.setdPreferentialCashTotalAmount(listPurchaseOrderDetailEntity.get(0).getdPreferentialCashTotalAmount());
					orderVo.setiIntegrationAmount(listPurchaseOrderDetailEntity.get(0).getiIntegrationAmount());
					orderVo.setiPurchaseIntegrationAmount(listPurchaseOrderDetailEntity.get(0).getiPurchaseIntegrationAmount());
					orderVo.setiStatus(listPurchaseOrderDetailEntity.get(0).getiStatus());
					orderVo.setiPayStandard(listPurchaseOrderDetailEntity.get(0).getiPayStandard());
					orderVo.setStrPayTime(listPurchaseOrderDetailEntity.get(0).getStrPayTime());
					orderVo.setiPayType(listPurchaseOrderDetailEntity.get(0).getiPayType());
					orderVo.setStrThirdPartyTradeFlow(listPurchaseOrderDetailEntity.get(0).getStrThirdPartyTradeFlow());
					orderVo.setStrExpressNumber(listPurchaseOrderDetailEntity.get(0).getStrExpressNumber());
					orderVo.setStrExpressCompany(listPurchaseOrderDetailEntity.get(0).getStrExpressCompany());
					orderVo.setStrComment(listPurchaseOrderDetailEntity.get(0).getStrComment());
					orderVo.setiReceiveType(listPurchaseOrderDetailEntity.get(0).getiReceiveType());
					orderVo.setStrReceiveTime(listPurchaseOrderDetailEntity.get(0).getStrReceiveTime());
					orderVo.setStrCreationTime(listPurchaseOrderDetailEntity.get(0).getStrCreationTime());
					orderVo.setStrLastAccessedTime(listPurchaseOrderDetailEntity.get(0).getStrLastAccessedTime());
					ListOrderVoEntity.add(orderVo);
					
					listPurchaseOrderDetailEntity.remove(0);
					if(listPurchaseOrderDetailEntity!=null&&listPurchaseOrderDetailEntity.size()==0)
						{
							iFlag=1;
							isEmptyListPurchaseOrderDetailEntity=1;
						}
					
				}
				else
				{
					OrderVo orderVo=new OrderVo();
					orderVo.setStrOrderId(listWeiXinGoodsOrderEntity.get(0).getStrOrderId());
					orderVo.setStrMemberId(listWeiXinGoodsOrderEntity.get(0).getStrMemberId());
					orderVo.setStrMemberName(listWeiXinGoodsOrderEntity.get(0).getStrMemberName());
					orderVo.setStrMemberCardNumber(listWeiXinGoodsOrderEntity.get(0).getStrMemberCardNumber());
					orderVo.setStrLevelsId(listWeiXinGoodsOrderEntity.get(0).getStrLevelsId());
					orderVo.setStrGoodsOrServiceId(listWeiXinGoodsOrderEntity.get(0).getStrGoodsOrServiceId());
					orderVo.setStrGoodsOrServiceName(listWeiXinGoodsOrderEntity.get(0).getStrGoodsOrServiceName());
					orderVo.setiPurchaseAmount(listWeiXinGoodsOrderEntity.get(0).getiPurchaseAmount());
					orderVo.setdPrice(listWeiXinGoodsOrderEntity.get(0).getdPrice());
					orderVo.setStrUnitName(listWeiXinGoodsOrderEntity.get(0).getStrUnitName());
					orderVo.setdPurchaseCashTotalAmount(listWeiXinGoodsOrderEntity.get(0).getdPurchaseCashTotalAmount());
					orderVo.setdPreferentialPrice(listWeiXinGoodsOrderEntity.get(0).getdPreferentialPrice());
					orderVo.setdPreferentialCashTotalAmount(listWeiXinGoodsOrderEntity.get(0).getdPreferentialCashTotalAmount());
					orderVo.setiIntegrationAmount(listWeiXinGoodsOrderEntity.get(0).getiIntegrationAmount());
					orderVo.setiPurchaseIntegrationAmount(listWeiXinGoodsOrderEntity.get(0).getiPurchaseIntegrationAmount());
					orderVo.setiStatus(listWeiXinGoodsOrderEntity.get(0).getiStatus());
					orderVo.setiPayStandard(listWeiXinGoodsOrderEntity.get(0).getiPayStandard());
					orderVo.setStrPayTime(listWeiXinGoodsOrderEntity.get(0).getStrPayTime());
					orderVo.setiPayType(listWeiXinGoodsOrderEntity.get(0).getiPayType());
					orderVo.setStrThirdPartyTradeFlow(listWeiXinGoodsOrderEntity.get(0).getStrThirdPartyTradeFlow());
					orderVo.setStrExpressNumber(listWeiXinGoodsOrderEntity.get(0).getStrExpressNumber());
					orderVo.setStrExpressCompany(listWeiXinGoodsOrderEntity.get(0).getStrExpressCompany());
					orderVo.setStrComment(listWeiXinGoodsOrderEntity.get(0).getStrComment());
					orderVo.setiReceiveType(listWeiXinGoodsOrderEntity.get(0).getiReceiveType());
					orderVo.setStrReceiveTime(listWeiXinGoodsOrderEntity.get(0).getStrReceiveTime());
					orderVo.setStrCreationTime(listWeiXinGoodsOrderEntity.get(0).getStrCreationTime());
					orderVo.setStrLastAccessedTime(listWeiXinGoodsOrderEntity.get(0).getStrLastAccessedTime());
					ListOrderVoEntity.add(orderVo);
					
					listWeiXinGoodsOrderEntity.remove(0);
					if(listWeiXinGoodsOrderEntity!=null&&listWeiXinGoodsOrderEntity.size()==0)
						{
						iFlag=1;
						isEmptyListWeiXinGoodsOrderEntity=1;
						}
				}
			}
			
			//将比较时间后剩余的数据加入ListOrderVoEntity
			if(isEmptyListWeiXinGoodsOrderEntity==1&&isEmptyListPurchaseOrderDetailEntity==0)
			{
				//listPurchaseOrderDetailEntity不为空的情况
				for(int i=0;i<listPurchaseOrderDetailEntity.size();i++)
				{
					OrderVo orderVo=new OrderVo();
					orderVo.setStrOrderId(listPurchaseOrderDetailEntity.get(i).getStrOrderId());
					orderVo.setStrMemberId(listPurchaseOrderDetailEntity.get(i).getStrMemberId());
					orderVo.setStrMemberName(listPurchaseOrderDetailEntity.get(i).getStrMemberName());
					orderVo.setStrMemberCardNumber(listPurchaseOrderDetailEntity.get(i).getStrMemberCardNumber());
					orderVo.setStrLevelsId(listPurchaseOrderDetailEntity.get(i).getStrLevelsId());
					orderVo.setStrGoodsOrServiceId(listPurchaseOrderDetailEntity.get(i).getStrGoodsOrServiceId());
					orderVo.setStrGoodsOrServiceName(listPurchaseOrderDetailEntity.get(i).getStrGoodsOrServiceName());
					orderVo.setiPurchaseAmount(listPurchaseOrderDetailEntity.get(i).getiPurchaseAmount());
					orderVo.setdPrice(listPurchaseOrderDetailEntity.get(i).getdPrice());
					orderVo.setStrUnitName(listPurchaseOrderDetailEntity.get(i).getStrUnitName());
					orderVo.setdPurchaseCashTotalAmount(listPurchaseOrderDetailEntity.get(i).getdPurchaseCashTotalAmount());
					orderVo.setdPreferentialPrice(listPurchaseOrderDetailEntity.get(i).getdPreferentialPrice());
					orderVo.setdPreferentialCashTotalAmount(listPurchaseOrderDetailEntity.get(i).getdPreferentialCashTotalAmount());
					orderVo.setiIntegrationAmount(listPurchaseOrderDetailEntity.get(i).getiIntegrationAmount());
					orderVo.setiPurchaseIntegrationAmount(listPurchaseOrderDetailEntity.get(i).getiPurchaseIntegrationAmount());
					orderVo.setiStatus(listPurchaseOrderDetailEntity.get(i).getiStatus());
					orderVo.setiPayStandard(listPurchaseOrderDetailEntity.get(i).getiPayStandard());
					orderVo.setStrPayTime(listPurchaseOrderDetailEntity.get(i).getStrPayTime());
					orderVo.setiPayType(listPurchaseOrderDetailEntity.get(i).getiPayType());
					orderVo.setStrThirdPartyTradeFlow(listPurchaseOrderDetailEntity.get(i).getStrThirdPartyTradeFlow());
					orderVo.setStrExpressNumber(listPurchaseOrderDetailEntity.get(i).getStrExpressNumber());
					orderVo.setStrExpressCompany(listPurchaseOrderDetailEntity.get(i).getStrExpressCompany());
					orderVo.setStrComment(listPurchaseOrderDetailEntity.get(i).getStrComment());
					orderVo.setiReceiveType(listPurchaseOrderDetailEntity.get(i).getiReceiveType());
					orderVo.setStrReceiveTime(listPurchaseOrderDetailEntity.get(i).getStrReceiveTime());
					orderVo.setStrCreationTime(listPurchaseOrderDetailEntity.get(i).getStrCreationTime());
					orderVo.setStrLastAccessedTime(listPurchaseOrderDetailEntity.get(i).getStrLastAccessedTime());
					ListOrderVoEntity.add(orderVo);
				}
				
			}
			
			if(isEmptyListWeiXinGoodsOrderEntity==0&&isEmptyListPurchaseOrderDetailEntity==1)
			{
				//listWeiXinGoodsOrderEntity不为空的情况
				for(int i=0;i<listWeiXinGoodsOrderEntity.size();i++)
				{
					OrderVo orderVo=new OrderVo();
					orderVo.setStrOrderId(listWeiXinGoodsOrderEntity.get(i).getStrOrderId());
					orderVo.setStrMemberId(listWeiXinGoodsOrderEntity.get(i).getStrMemberId());
					orderVo.setStrMemberName(listWeiXinGoodsOrderEntity.get(i).getStrMemberName());
					orderVo.setStrMemberCardNumber(listWeiXinGoodsOrderEntity.get(i).getStrMemberCardNumber());
					orderVo.setStrLevelsId(listWeiXinGoodsOrderEntity.get(i).getStrLevelsId());
					orderVo.setStrGoodsOrServiceId(listWeiXinGoodsOrderEntity.get(i).getStrGoodsOrServiceId());
					orderVo.setStrGoodsOrServiceName(listWeiXinGoodsOrderEntity.get(i).getStrGoodsOrServiceName());
					orderVo.setiPurchaseAmount(listWeiXinGoodsOrderEntity.get(i).getiPurchaseAmount());
					orderVo.setdPrice(listWeiXinGoodsOrderEntity.get(i).getdPrice());
					orderVo.setStrUnitName(listWeiXinGoodsOrderEntity.get(i).getStrUnitName());
					orderVo.setdPurchaseCashTotalAmount(listWeiXinGoodsOrderEntity.get(i).getdPurchaseCashTotalAmount());
					orderVo.setdPreferentialPrice(listWeiXinGoodsOrderEntity.get(i).getdPreferentialPrice());
					orderVo.setdPreferentialCashTotalAmount(listWeiXinGoodsOrderEntity.get(i).getdPreferentialCashTotalAmount());
					orderVo.setiIntegrationAmount(listWeiXinGoodsOrderEntity.get(i).getiIntegrationAmount());
					orderVo.setiPurchaseIntegrationAmount(listWeiXinGoodsOrderEntity.get(i).getiPurchaseIntegrationAmount());
					orderVo.setiStatus(listWeiXinGoodsOrderEntity.get(i).getiStatus());
					orderVo.setiPayStandard(listWeiXinGoodsOrderEntity.get(i).getiPayStandard());
					orderVo.setStrPayTime(listWeiXinGoodsOrderEntity.get(i).getStrPayTime());
					orderVo.setiPayType(listWeiXinGoodsOrderEntity.get(i).getiPayType());
					orderVo.setStrThirdPartyTradeFlow(listWeiXinGoodsOrderEntity.get(i).getStrThirdPartyTradeFlow());
					orderVo.setStrExpressNumber(listWeiXinGoodsOrderEntity.get(i).getStrExpressNumber());
					orderVo.setStrExpressCompany(listWeiXinGoodsOrderEntity.get(i).getStrExpressCompany());
					orderVo.setStrComment(listWeiXinGoodsOrderEntity.get(i).getStrComment());
					orderVo.setiReceiveType(listWeiXinGoodsOrderEntity.get(i).getiReceiveType());
					orderVo.setStrReceiveTime(listWeiXinGoodsOrderEntity.get(i).getStrReceiveTime());
					orderVo.setStrCreationTime(listWeiXinGoodsOrderEntity.get(i).getStrCreationTime());
					orderVo.setStrLastAccessedTime(listWeiXinGoodsOrderEntity.get(i).getStrLastAccessedTime());
					ListOrderVoEntity.add(orderVo);
				}
			}
		}
		
		//处理分页问题
	
		iTotalRecord=ListOrderVoEntity.size();
		iTotalPage=iTotalRecord%iPageSize==0?iTotalRecord/iPageSize:iTotalRecord/iPageSize+1;
		if(iPageNum>iTotalPage)
			iPageNum=iTotalPage;
		iListMaxIndex=ListOrderVoEntity.size()-1;
		iPageFromIndex=(iPageNum-1)*iPageSize;
		iPageEndIndex=iPageFromIndex+iPageSize-1;
		List<OrderVo> returnListOrderVo=new ArrayList<OrderVo>();
		if(iListMaxIndex>=iPageEndIndex)
		{
			for(int i=0;i<=iListMaxIndex;i++)
			{
				if(i>=iPageFromIndex&&i<=iPageEndIndex)
					returnListOrderVo.add(ListOrderVoEntity.get(i));
			}
			
		}
		else
		{
			for(int i=0;i<=iListMaxIndex;i++)
			{
				if(i>=iPageFromIndex)
					returnListOrderVo.add(ListOrderVoEntity.get(i));
			}
		}
		Map<String,Object> resultMap=new HashMap<String,Object>();
		resultMap.put("returnListOrderVo", returnListOrderVo);
		resultMap.put("iTotalRecord",iTotalRecord);
		resultMap.put("iTotalPage",iTotalPage);
		return DataTool.constructResponse(ResultCode.OK,"查询成功",resultMap);
	}
	
	
	//查询现金，支付宝，微信支付的消费记录 分页查询
	@Transactional
	public String selectCashConsume(Map<String,Object> queryMap) throws Exception
	{
		int iPageFromIndex=0;
		int iPageEndIndex=0;
		int iListMaxIndex=0;
		int iTotalRecord=0;
		int iTotalPage=0;
		int iPageSize=(Integer)queryMap.get("iPageSize");
		int iPageNum=(Integer)queryMap.get("iPageNum");
		int isEmptyListPurchaseOrderDetailEntity=0;
		int isEmptyListWeiXinGoodsOrderEntity=0;
		int iFlag=0;
		List<OrderVo> ListOrderVoEntity=new ArrayList<OrderVo>();
		List<OrderVo> listPurchaseOrderDetailEntity=weiXinConsumeRecordMapper.selectCashConsumeFromOrderDetailInfo(queryMap);
		List<OrderVo> listWeiXinGoodsOrderEntity=weiXinConsumeRecordMapper.selectCashConsumeFromWeiXinGoodsOrderInfo(queryMap);
		if(listPurchaseOrderDetailEntity==null)
			isEmptyListPurchaseOrderDetailEntity=1;
		if(listPurchaseOrderDetailEntity.size()==0)
			isEmptyListPurchaseOrderDetailEntity=1;
		
		if(listWeiXinGoodsOrderEntity==null)
			isEmptyListWeiXinGoodsOrderEntity=1;
		if(listWeiXinGoodsOrderEntity.size()==0)
			isEmptyListWeiXinGoodsOrderEntity=1;
		if(isEmptyListPurchaseOrderDetailEntity==1&&isEmptyListWeiXinGoodsOrderEntity==1)
			return DataTool.constructResponse(ResultCode.NO_DATA,"你还没有现金消费数据",null);
		//如果微信订单里有积分消费记录的情况:
		if(isEmptyListPurchaseOrderDetailEntity==1&&isEmptyListWeiXinGoodsOrderEntity==0)
			ListOrderVoEntity=listWeiXinGoodsOrderEntity;
		if(isEmptyListPurchaseOrderDetailEntity==0&&isEmptyListWeiXinGoodsOrderEntity==1)
			ListOrderVoEntity=listPurchaseOrderDetailEntity;
		
		if(isEmptyListPurchaseOrderDetailEntity==0&&isEmptyListWeiXinGoodsOrderEntity==0)
		{
			//两个表里都有数据的情况下:
			String strOrderDetailTime="";
			String strWeiXinOrderTime="";
			while(iFlag==0)
			{
				strOrderDetailTime=listPurchaseOrderDetailEntity.get(0).getStrCreationTime();
				strWeiXinOrderTime=listWeiXinGoodsOrderEntity.get(0).getStrCreationTime();
				if(strOrderDetailTime.compareTo(strWeiXinOrderTime)>=0)
				{
					OrderVo orderVo=new OrderVo();
					orderVo.setStrOrderId(listPurchaseOrderDetailEntity.get(0).getStrOrderId());
					orderVo.setStrMemberId(listPurchaseOrderDetailEntity.get(0).getStrMemberId());
					orderVo.setStrMemberName(listPurchaseOrderDetailEntity.get(0).getStrMemberName());
					orderVo.setStrMemberCardNumber(listPurchaseOrderDetailEntity.get(0).getStrMemberCardNumber());
					orderVo.setStrLevelsId(listPurchaseOrderDetailEntity.get(0).getStrLevelsId());
					orderVo.setStrGoodsOrServiceId(listPurchaseOrderDetailEntity.get(0).getStrGoodsOrServiceId());
					orderVo.setStrGoodsOrServiceName(listPurchaseOrderDetailEntity.get(0).getStrGoodsOrServiceName());
					orderVo.setiPurchaseAmount(listPurchaseOrderDetailEntity.get(0).getiPurchaseAmount());
					orderVo.setdPrice(listPurchaseOrderDetailEntity.get(0).getdPrice());
					orderVo.setStrUnitName(listPurchaseOrderDetailEntity.get(0).getStrUnitName());
					orderVo.setdPurchaseCashTotalAmount(listPurchaseOrderDetailEntity.get(0).getdPurchaseCashTotalAmount());
					orderVo.setdPreferentialPrice(listPurchaseOrderDetailEntity.get(0).getdPreferentialPrice());
					orderVo.setdPreferentialCashTotalAmount(listPurchaseOrderDetailEntity.get(0).getdPreferentialCashTotalAmount());
					orderVo.setiIntegrationAmount(listPurchaseOrderDetailEntity.get(0).getiIntegrationAmount());
					orderVo.setiPurchaseIntegrationAmount(listPurchaseOrderDetailEntity.get(0).getiPurchaseIntegrationAmount());
					orderVo.setiStatus(listPurchaseOrderDetailEntity.get(0).getiStatus());
					orderVo.setiPayStandard(listPurchaseOrderDetailEntity.get(0).getiPayStandard());
					orderVo.setStrPayTime(listPurchaseOrderDetailEntity.get(0).getStrPayTime());
					orderVo.setiPayType(listPurchaseOrderDetailEntity.get(0).getiPayType());
					orderVo.setStrThirdPartyTradeFlow(listPurchaseOrderDetailEntity.get(0).getStrThirdPartyTradeFlow());
					orderVo.setStrExpressNumber(listPurchaseOrderDetailEntity.get(0).getStrExpressNumber());
					orderVo.setStrExpressCompany(listPurchaseOrderDetailEntity.get(0).getStrExpressCompany());
					orderVo.setStrComment(listPurchaseOrderDetailEntity.get(0).getStrComment());
					orderVo.setiReceiveType(listPurchaseOrderDetailEntity.get(0).getiReceiveType());
					orderVo.setStrReceiveTime(listPurchaseOrderDetailEntity.get(0).getStrReceiveTime());
					orderVo.setStrCreationTime(listPurchaseOrderDetailEntity.get(0).getStrCreationTime());
					orderVo.setStrLastAccessedTime(listPurchaseOrderDetailEntity.get(0).getStrLastAccessedTime());
					ListOrderVoEntity.add(orderVo);
					
					listPurchaseOrderDetailEntity.remove(0);
					if(listPurchaseOrderDetailEntity!=null&&listPurchaseOrderDetailEntity.size()==0)
						{
							iFlag=1;
							isEmptyListPurchaseOrderDetailEntity=1;
						}
					
				}
				else
				{
					OrderVo orderVo=new OrderVo();
					orderVo.setStrOrderId(listWeiXinGoodsOrderEntity.get(0).getStrOrderId());
					orderVo.setStrMemberId(listWeiXinGoodsOrderEntity.get(0).getStrMemberId());
					orderVo.setStrMemberName(listWeiXinGoodsOrderEntity.get(0).getStrMemberName());
					orderVo.setStrMemberCardNumber(listWeiXinGoodsOrderEntity.get(0).getStrMemberCardNumber());
					orderVo.setStrLevelsId(listWeiXinGoodsOrderEntity.get(0).getStrLevelsId());
					orderVo.setStrGoodsOrServiceId(listWeiXinGoodsOrderEntity.get(0).getStrGoodsOrServiceId());
					orderVo.setStrGoodsOrServiceName(listWeiXinGoodsOrderEntity.get(0).getStrGoodsOrServiceName());
					orderVo.setiPurchaseAmount(listWeiXinGoodsOrderEntity.get(0).getiPurchaseAmount());
					orderVo.setdPrice(listWeiXinGoodsOrderEntity.get(0).getdPrice());
					orderVo.setStrUnitName(listWeiXinGoodsOrderEntity.get(0).getStrUnitName());
					orderVo.setdPurchaseCashTotalAmount(listWeiXinGoodsOrderEntity.get(0).getdPurchaseCashTotalAmount());
					orderVo.setdPreferentialPrice(listWeiXinGoodsOrderEntity.get(0).getdPreferentialPrice());
					orderVo.setdPreferentialCashTotalAmount(listWeiXinGoodsOrderEntity.get(0).getdPreferentialCashTotalAmount());
					orderVo.setiIntegrationAmount(listWeiXinGoodsOrderEntity.get(0).getiIntegrationAmount());
					orderVo.setiPurchaseIntegrationAmount(listWeiXinGoodsOrderEntity.get(0).getiPurchaseIntegrationAmount());
					orderVo.setiStatus(listWeiXinGoodsOrderEntity.get(0).getiStatus());
					orderVo.setiPayStandard(listWeiXinGoodsOrderEntity.get(0).getiPayStandard());
					orderVo.setStrPayTime(listWeiXinGoodsOrderEntity.get(0).getStrPayTime());
					orderVo.setiPayType(listWeiXinGoodsOrderEntity.get(0).getiPayType());
					orderVo.setStrThirdPartyTradeFlow(listWeiXinGoodsOrderEntity.get(0).getStrThirdPartyTradeFlow());
					orderVo.setStrExpressNumber(listWeiXinGoodsOrderEntity.get(0).getStrExpressNumber());
					orderVo.setStrExpressCompany(listWeiXinGoodsOrderEntity.get(0).getStrExpressCompany());
					orderVo.setStrComment(listWeiXinGoodsOrderEntity.get(0).getStrComment());
					orderVo.setiReceiveType(listWeiXinGoodsOrderEntity.get(0).getiReceiveType());
					orderVo.setStrReceiveTime(listWeiXinGoodsOrderEntity.get(0).getStrReceiveTime());
					orderVo.setStrCreationTime(listWeiXinGoodsOrderEntity.get(0).getStrCreationTime());
					orderVo.setStrLastAccessedTime(listWeiXinGoodsOrderEntity.get(0).getStrLastAccessedTime());
					ListOrderVoEntity.add(orderVo);
					
					listWeiXinGoodsOrderEntity.remove(0);
					if(listWeiXinGoodsOrderEntity!=null&&listWeiXinGoodsOrderEntity.size()==0)
						{
						iFlag=1;
						isEmptyListWeiXinGoodsOrderEntity=1;
						}
				}
			}
			
			//将比较时间后剩余的数据加入ListOrderVoEntity
			if(isEmptyListWeiXinGoodsOrderEntity==1&&isEmptyListPurchaseOrderDetailEntity==0)
			{
				//listPurchaseOrderDetailEntity不为空的情况
				for(int i=0;i<listPurchaseOrderDetailEntity.size();i++)
				{
					OrderVo orderVo=new OrderVo();
					orderVo.setStrOrderId(listPurchaseOrderDetailEntity.get(i).getStrOrderId());
					orderVo.setStrMemberId(listPurchaseOrderDetailEntity.get(i).getStrMemberId());
					orderVo.setStrMemberName(listPurchaseOrderDetailEntity.get(i).getStrMemberName());
					orderVo.setStrMemberCardNumber(listPurchaseOrderDetailEntity.get(i).getStrMemberCardNumber());
					orderVo.setStrLevelsId(listPurchaseOrderDetailEntity.get(i).getStrLevelsId());
					orderVo.setStrGoodsOrServiceId(listPurchaseOrderDetailEntity.get(i).getStrGoodsOrServiceId());
					orderVo.setStrGoodsOrServiceName(listPurchaseOrderDetailEntity.get(i).getStrGoodsOrServiceName());
					orderVo.setiPurchaseAmount(listPurchaseOrderDetailEntity.get(i).getiPurchaseAmount());
					orderVo.setdPrice(listPurchaseOrderDetailEntity.get(i).getdPrice());
					orderVo.setStrUnitName(listPurchaseOrderDetailEntity.get(i).getStrUnitName());
					orderVo.setdPurchaseCashTotalAmount(listPurchaseOrderDetailEntity.get(i).getdPurchaseCashTotalAmount());
					orderVo.setdPreferentialPrice(listPurchaseOrderDetailEntity.get(i).getdPreferentialPrice());
					orderVo.setdPreferentialCashTotalAmount(listPurchaseOrderDetailEntity.get(i).getdPreferentialCashTotalAmount());
					orderVo.setiIntegrationAmount(listPurchaseOrderDetailEntity.get(i).getiIntegrationAmount());
					orderVo.setiPurchaseIntegrationAmount(listPurchaseOrderDetailEntity.get(i).getiPurchaseIntegrationAmount());
					orderVo.setiStatus(listPurchaseOrderDetailEntity.get(i).getiStatus());
					orderVo.setiPayStandard(listPurchaseOrderDetailEntity.get(i).getiPayStandard());
					orderVo.setStrPayTime(listPurchaseOrderDetailEntity.get(i).getStrPayTime());
					orderVo.setiPayType(listPurchaseOrderDetailEntity.get(i).getiPayType());
					orderVo.setStrThirdPartyTradeFlow(listPurchaseOrderDetailEntity.get(i).getStrThirdPartyTradeFlow());
					orderVo.setStrExpressNumber(listPurchaseOrderDetailEntity.get(i).getStrExpressNumber());
					orderVo.setStrExpressCompany(listPurchaseOrderDetailEntity.get(i).getStrExpressCompany());
					orderVo.setStrComment(listPurchaseOrderDetailEntity.get(i).getStrComment());
					orderVo.setiReceiveType(listPurchaseOrderDetailEntity.get(i).getiReceiveType());
					orderVo.setStrReceiveTime(listPurchaseOrderDetailEntity.get(i).getStrReceiveTime());
					orderVo.setStrCreationTime(listPurchaseOrderDetailEntity.get(i).getStrCreationTime());
					orderVo.setStrLastAccessedTime(listPurchaseOrderDetailEntity.get(i).getStrLastAccessedTime());
					ListOrderVoEntity.add(orderVo);
				}
				
			}
			
			if(isEmptyListWeiXinGoodsOrderEntity==0&&isEmptyListPurchaseOrderDetailEntity==1)
			{
				//listWeiXinGoodsOrderEntity不为空的情况
				for(int i=0;i<listWeiXinGoodsOrderEntity.size();i++)
				{
					OrderVo orderVo=new OrderVo();
					orderVo.setStrOrderId(listWeiXinGoodsOrderEntity.get(i).getStrOrderId());
					orderVo.setStrMemberId(listWeiXinGoodsOrderEntity.get(i).getStrMemberId());
					orderVo.setStrMemberName(listWeiXinGoodsOrderEntity.get(i).getStrMemberName());
					orderVo.setStrMemberCardNumber(listWeiXinGoodsOrderEntity.get(i).getStrMemberCardNumber());
					orderVo.setStrLevelsId(listWeiXinGoodsOrderEntity.get(i).getStrLevelsId());
					orderVo.setStrGoodsOrServiceId(listWeiXinGoodsOrderEntity.get(i).getStrGoodsOrServiceId());
					orderVo.setStrGoodsOrServiceName(listWeiXinGoodsOrderEntity.get(i).getStrGoodsOrServiceName());
					orderVo.setiPurchaseAmount(listWeiXinGoodsOrderEntity.get(i).getiPurchaseAmount());
					orderVo.setdPrice(listWeiXinGoodsOrderEntity.get(i).getdPrice());
					orderVo.setStrUnitName(listWeiXinGoodsOrderEntity.get(i).getStrUnitName());
					orderVo.setdPurchaseCashTotalAmount(listWeiXinGoodsOrderEntity.get(i).getdPurchaseCashTotalAmount());
					orderVo.setdPreferentialPrice(listWeiXinGoodsOrderEntity.get(i).getdPreferentialPrice());
					orderVo.setdPreferentialCashTotalAmount(listWeiXinGoodsOrderEntity.get(i).getdPreferentialCashTotalAmount());
					orderVo.setiIntegrationAmount(listWeiXinGoodsOrderEntity.get(i).getiIntegrationAmount());
					orderVo.setiPurchaseIntegrationAmount(listWeiXinGoodsOrderEntity.get(i).getiPurchaseIntegrationAmount());
					orderVo.setiStatus(listWeiXinGoodsOrderEntity.get(i).getiStatus());
					orderVo.setiPayStandard(listWeiXinGoodsOrderEntity.get(i).getiPayStandard());
					orderVo.setStrPayTime(listWeiXinGoodsOrderEntity.get(i).getStrPayTime());
					orderVo.setiPayType(listWeiXinGoodsOrderEntity.get(i).getiPayType());
					orderVo.setStrThirdPartyTradeFlow(listWeiXinGoodsOrderEntity.get(i).getStrThirdPartyTradeFlow());
					orderVo.setStrExpressNumber(listWeiXinGoodsOrderEntity.get(i).getStrExpressNumber());
					orderVo.setStrExpressCompany(listWeiXinGoodsOrderEntity.get(i).getStrExpressCompany());
					orderVo.setStrComment(listWeiXinGoodsOrderEntity.get(i).getStrComment());
					orderVo.setiReceiveType(listWeiXinGoodsOrderEntity.get(i).getiReceiveType());
					orderVo.setStrReceiveTime(listWeiXinGoodsOrderEntity.get(i).getStrReceiveTime());
					orderVo.setStrCreationTime(listWeiXinGoodsOrderEntity.get(i).getStrCreationTime());
					orderVo.setStrLastAccessedTime(listWeiXinGoodsOrderEntity.get(i).getStrLastAccessedTime());
					ListOrderVoEntity.add(orderVo);
				}
			}
		}
		
		//处理分页问题
	
		iTotalRecord=ListOrderVoEntity.size();
		iTotalPage=iTotalRecord%iPageSize==0?iTotalRecord/iPageSize:iTotalRecord/iPageSize+1;
		if(iPageNum>iTotalPage)
			iPageNum=iTotalPage;
		iListMaxIndex=ListOrderVoEntity.size()-1;
		iPageFromIndex=(iPageNum-1)*iPageSize;
		iPageEndIndex=iPageFromIndex+iPageSize-1;
		List<OrderVo> returnListOrderVo=new ArrayList<OrderVo>();
		if(iListMaxIndex>=iPageEndIndex)
		{
			for(int i=0;i<=iListMaxIndex;i++)
			{
				if(i>=iPageFromIndex&&i<=iPageEndIndex)
					returnListOrderVo.add(ListOrderVoEntity.get(i));
			}
			
		}
		else
		{
			for(int i=0;i<=iListMaxIndex;i++)
			{
				if(i>=iPageFromIndex)
					returnListOrderVo.add(ListOrderVoEntity.get(i));
			}
		}
		Map<String,Object> resultMap=new HashMap<String,Object>();
		resultMap.put("returnListOrderVo", returnListOrderVo);
		resultMap.put("iTotalRecord",iTotalRecord);
		resultMap.put("iTotalPage",iTotalPage);
		return DataTool.constructResponse(ResultCode.OK,"查询成功",resultMap);
	}
	
	
	
	//查询抵用券支付记录
	@Transactional
	public String selectVoucherTicketConsume(Map<String,Object> queryMap) throws Exception
	{
		int iPageFromIndex=0;
		int iPageEndIndex=0;
		int iListMaxIndex=0;
		int iTotalRecord=0;
		int iTotalPage=0;
		int iPageSize=(Integer)queryMap.get("iPageSize");
		int iPageNum=(Integer)queryMap.get("iPageNum");
		int isEmptyListPurchaseOrderDetailEntity=0;
		int isEmptyListWeiXinGoodsOrderEntity=0;
		int iFlag=0;
		List<OrderVo> ListOrderVoEntity=new ArrayList<OrderVo>();
		List<OrderVo> listPurchaseOrderDetailEntity=weiXinConsumeRecordMapper.selectVoucherTicketConsumeFromOrderDetailInfo(queryMap);
		List<OrderVo> listWeiXinGoodsOrderEntity=weiXinConsumeRecordMapper.selectVoucherTicketConsumeFromWeiXinGoodsOrderInfo(queryMap);
		if(listPurchaseOrderDetailEntity==null)
			isEmptyListPurchaseOrderDetailEntity=1;
		if(listPurchaseOrderDetailEntity.size()==0)
			isEmptyListPurchaseOrderDetailEntity=1;
		
		if(listWeiXinGoodsOrderEntity==null)
			isEmptyListWeiXinGoodsOrderEntity=1;
		if(listWeiXinGoodsOrderEntity.size()==0)
			isEmptyListWeiXinGoodsOrderEntity=1;
		if(isEmptyListPurchaseOrderDetailEntity==1&&isEmptyListWeiXinGoodsOrderEntity==1)
			return DataTool.constructResponse(ResultCode.NO_DATA,"你还没有抵用券消费数据",null);
		//如果微信订单里有积分消费记录的情况:
		if(isEmptyListPurchaseOrderDetailEntity==1&&isEmptyListWeiXinGoodsOrderEntity==0)
			ListOrderVoEntity=listWeiXinGoodsOrderEntity;
		if(isEmptyListPurchaseOrderDetailEntity==0&&isEmptyListWeiXinGoodsOrderEntity==1)
			ListOrderVoEntity=listPurchaseOrderDetailEntity;
		
		if(isEmptyListPurchaseOrderDetailEntity==0&&isEmptyListWeiXinGoodsOrderEntity==0)
		{
			//两个表里都有数据的情况下:
			String strOrderDetailTime="";
			String strWeiXinOrderTime="";
			while(iFlag==0)
			{
				strOrderDetailTime=listPurchaseOrderDetailEntity.get(0).getStrCreationTime();
				strWeiXinOrderTime=listWeiXinGoodsOrderEntity.get(0).getStrCreationTime();
				if(strOrderDetailTime.compareTo(strWeiXinOrderTime)>=0)
				{
					OrderVo orderVo=new OrderVo();
					orderVo.setStrOrderId(listPurchaseOrderDetailEntity.get(0).getStrOrderId());
					orderVo.setStrMemberId(listPurchaseOrderDetailEntity.get(0).getStrMemberId());
					orderVo.setStrMemberName(listPurchaseOrderDetailEntity.get(0).getStrMemberName());
					orderVo.setStrMemberCardNumber(listPurchaseOrderDetailEntity.get(0).getStrMemberCardNumber());
					orderVo.setStrLevelsId(listPurchaseOrderDetailEntity.get(0).getStrLevelsId());
					orderVo.setStrGoodsOrServiceId(listPurchaseOrderDetailEntity.get(0).getStrGoodsOrServiceId());
					orderVo.setStrGoodsOrServiceName(listPurchaseOrderDetailEntity.get(0).getStrGoodsOrServiceName());
					orderVo.setiPurchaseAmount(listPurchaseOrderDetailEntity.get(0).getiPurchaseAmount());
					orderVo.setdPrice(listPurchaseOrderDetailEntity.get(0).getdPrice());
					orderVo.setStrUnitName(listPurchaseOrderDetailEntity.get(0).getStrUnitName());
					orderVo.setdPurchaseCashTotalAmount(listPurchaseOrderDetailEntity.get(0).getdPurchaseCashTotalAmount());
					orderVo.setdPreferentialPrice(listPurchaseOrderDetailEntity.get(0).getdPreferentialPrice());
					orderVo.setdPreferentialCashTotalAmount(listPurchaseOrderDetailEntity.get(0).getdPreferentialCashTotalAmount());
					orderVo.setiIntegrationAmount(listPurchaseOrderDetailEntity.get(0).getiIntegrationAmount());
					orderVo.setiPurchaseIntegrationAmount(listPurchaseOrderDetailEntity.get(0).getiPurchaseIntegrationAmount());
					orderVo.setiStatus(listPurchaseOrderDetailEntity.get(0).getiStatus());
					orderVo.setiPayStandard(listPurchaseOrderDetailEntity.get(0).getiPayStandard());
					orderVo.setStrPayTime(listPurchaseOrderDetailEntity.get(0).getStrPayTime());
					orderVo.setiPayType(listPurchaseOrderDetailEntity.get(0).getiPayType());
					orderVo.setStrThirdPartyTradeFlow(listPurchaseOrderDetailEntity.get(0).getStrThirdPartyTradeFlow());
					orderVo.setStrExpressNumber(listPurchaseOrderDetailEntity.get(0).getStrExpressNumber());
					orderVo.setStrExpressCompany(listPurchaseOrderDetailEntity.get(0).getStrExpressCompany());
					orderVo.setStrComment(listPurchaseOrderDetailEntity.get(0).getStrComment());
					orderVo.setiReceiveType(listPurchaseOrderDetailEntity.get(0).getiReceiveType());
					orderVo.setStrReceiveTime(listPurchaseOrderDetailEntity.get(0).getStrReceiveTime());
					orderVo.setStrCreationTime(listPurchaseOrderDetailEntity.get(0).getStrCreationTime());
					orderVo.setStrLastAccessedTime(listPurchaseOrderDetailEntity.get(0).getStrLastAccessedTime());
					ListOrderVoEntity.add(orderVo);
					
					listPurchaseOrderDetailEntity.remove(0);
					if(listPurchaseOrderDetailEntity!=null&&listPurchaseOrderDetailEntity.size()==0)
						{
							iFlag=1;
							isEmptyListPurchaseOrderDetailEntity=1;
						}
					
				}
				else
				{
					OrderVo orderVo=new OrderVo();
					orderVo.setStrOrderId(listWeiXinGoodsOrderEntity.get(0).getStrOrderId());
					orderVo.setStrMemberId(listWeiXinGoodsOrderEntity.get(0).getStrMemberId());
					orderVo.setStrMemberName(listWeiXinGoodsOrderEntity.get(0).getStrMemberName());
					orderVo.setStrMemberCardNumber(listWeiXinGoodsOrderEntity.get(0).getStrMemberCardNumber());
					orderVo.setStrLevelsId(listWeiXinGoodsOrderEntity.get(0).getStrLevelsId());
					orderVo.setStrGoodsOrServiceId(listWeiXinGoodsOrderEntity.get(0).getStrGoodsOrServiceId());
					orderVo.setStrGoodsOrServiceName(listWeiXinGoodsOrderEntity.get(0).getStrGoodsOrServiceName());
					orderVo.setiPurchaseAmount(listWeiXinGoodsOrderEntity.get(0).getiPurchaseAmount());
					orderVo.setdPrice(listWeiXinGoodsOrderEntity.get(0).getdPrice());
					orderVo.setStrUnitName(listWeiXinGoodsOrderEntity.get(0).getStrUnitName());
					orderVo.setdPurchaseCashTotalAmount(listWeiXinGoodsOrderEntity.get(0).getdPurchaseCashTotalAmount());
					orderVo.setdPreferentialPrice(listWeiXinGoodsOrderEntity.get(0).getdPreferentialPrice());
					orderVo.setdPreferentialCashTotalAmount(listWeiXinGoodsOrderEntity.get(0).getdPreferentialCashTotalAmount());
					orderVo.setiIntegrationAmount(listWeiXinGoodsOrderEntity.get(0).getiIntegrationAmount());
					orderVo.setiPurchaseIntegrationAmount(listWeiXinGoodsOrderEntity.get(0).getiPurchaseIntegrationAmount());
					orderVo.setiStatus(listWeiXinGoodsOrderEntity.get(0).getiStatus());
					orderVo.setiPayStandard(listWeiXinGoodsOrderEntity.get(0).getiPayStandard());
					orderVo.setStrPayTime(listWeiXinGoodsOrderEntity.get(0).getStrPayTime());
					orderVo.setiPayType(listWeiXinGoodsOrderEntity.get(0).getiPayType());
					orderVo.setStrThirdPartyTradeFlow(listWeiXinGoodsOrderEntity.get(0).getStrThirdPartyTradeFlow());
					orderVo.setStrExpressNumber(listWeiXinGoodsOrderEntity.get(0).getStrExpressNumber());
					orderVo.setStrExpressCompany(listWeiXinGoodsOrderEntity.get(0).getStrExpressCompany());
					orderVo.setStrComment(listWeiXinGoodsOrderEntity.get(0).getStrComment());
					orderVo.setiReceiveType(listWeiXinGoodsOrderEntity.get(0).getiReceiveType());
					orderVo.setStrReceiveTime(listWeiXinGoodsOrderEntity.get(0).getStrReceiveTime());
					orderVo.setStrCreationTime(listWeiXinGoodsOrderEntity.get(0).getStrCreationTime());
					orderVo.setStrLastAccessedTime(listWeiXinGoodsOrderEntity.get(0).getStrLastAccessedTime());
					ListOrderVoEntity.add(orderVo);
					
					listWeiXinGoodsOrderEntity.remove(0);
					if(listWeiXinGoodsOrderEntity!=null&&listWeiXinGoodsOrderEntity.size()==0)
						{
						iFlag=1;
						isEmptyListWeiXinGoodsOrderEntity=1;
						}
				}
			}
			
			//将比较时间后剩余的数据加入ListOrderVoEntity
			if(isEmptyListWeiXinGoodsOrderEntity==1&&isEmptyListPurchaseOrderDetailEntity==0)
			{
				//listPurchaseOrderDetailEntity不为空的情况
				for(int i=0;i<listPurchaseOrderDetailEntity.size();i++)
				{
					OrderVo orderVo=new OrderVo();
					orderVo.setStrOrderId(listPurchaseOrderDetailEntity.get(i).getStrOrderId());
					orderVo.setStrMemberId(listPurchaseOrderDetailEntity.get(i).getStrMemberId());
					orderVo.setStrMemberName(listPurchaseOrderDetailEntity.get(i).getStrMemberName());
					orderVo.setStrMemberCardNumber(listPurchaseOrderDetailEntity.get(i).getStrMemberCardNumber());
					orderVo.setStrLevelsId(listPurchaseOrderDetailEntity.get(i).getStrLevelsId());
					orderVo.setStrGoodsOrServiceId(listPurchaseOrderDetailEntity.get(i).getStrGoodsOrServiceId());
					orderVo.setStrGoodsOrServiceName(listPurchaseOrderDetailEntity.get(i).getStrGoodsOrServiceName());
					orderVo.setiPurchaseAmount(listPurchaseOrderDetailEntity.get(i).getiPurchaseAmount());
					orderVo.setdPrice(listPurchaseOrderDetailEntity.get(i).getdPrice());
					orderVo.setStrUnitName(listPurchaseOrderDetailEntity.get(i).getStrUnitName());
					orderVo.setdPurchaseCashTotalAmount(listPurchaseOrderDetailEntity.get(i).getdPurchaseCashTotalAmount());
					orderVo.setdPreferentialPrice(listPurchaseOrderDetailEntity.get(i).getdPreferentialPrice());
					orderVo.setdPreferentialCashTotalAmount(listPurchaseOrderDetailEntity.get(i).getdPreferentialCashTotalAmount());
					orderVo.setiIntegrationAmount(listPurchaseOrderDetailEntity.get(i).getiIntegrationAmount());
					orderVo.setiPurchaseIntegrationAmount(listPurchaseOrderDetailEntity.get(i).getiPurchaseIntegrationAmount());
					orderVo.setiStatus(listPurchaseOrderDetailEntity.get(i).getiStatus());
					orderVo.setiPayStandard(listPurchaseOrderDetailEntity.get(i).getiPayStandard());
					orderVo.setStrPayTime(listPurchaseOrderDetailEntity.get(i).getStrPayTime());
					orderVo.setiPayType(listPurchaseOrderDetailEntity.get(i).getiPayType());
					orderVo.setStrThirdPartyTradeFlow(listPurchaseOrderDetailEntity.get(i).getStrThirdPartyTradeFlow());
					orderVo.setStrExpressNumber(listPurchaseOrderDetailEntity.get(i).getStrExpressNumber());
					orderVo.setStrExpressCompany(listPurchaseOrderDetailEntity.get(i).getStrExpressCompany());
					orderVo.setStrComment(listPurchaseOrderDetailEntity.get(i).getStrComment());
					orderVo.setiReceiveType(listPurchaseOrderDetailEntity.get(i).getiReceiveType());
					orderVo.setStrReceiveTime(listPurchaseOrderDetailEntity.get(i).getStrReceiveTime());
					orderVo.setStrCreationTime(listPurchaseOrderDetailEntity.get(i).getStrCreationTime());
					orderVo.setStrLastAccessedTime(listPurchaseOrderDetailEntity.get(i).getStrLastAccessedTime());
					ListOrderVoEntity.add(orderVo);
				}
				
			}
			
			if(isEmptyListWeiXinGoodsOrderEntity==0&&isEmptyListPurchaseOrderDetailEntity==1)
			{
				//listWeiXinGoodsOrderEntity不为空的情况
				for(int i=0;i<listWeiXinGoodsOrderEntity.size();i++)
				{
					OrderVo orderVo=new OrderVo();
					orderVo.setStrOrderId(listWeiXinGoodsOrderEntity.get(i).getStrOrderId());
					orderVo.setStrMemberId(listWeiXinGoodsOrderEntity.get(i).getStrMemberId());
					orderVo.setStrMemberName(listWeiXinGoodsOrderEntity.get(i).getStrMemberName());
					orderVo.setStrMemberCardNumber(listWeiXinGoodsOrderEntity.get(i).getStrMemberCardNumber());
					orderVo.setStrLevelsId(listWeiXinGoodsOrderEntity.get(i).getStrLevelsId());
					orderVo.setStrGoodsOrServiceId(listWeiXinGoodsOrderEntity.get(i).getStrGoodsOrServiceId());
					orderVo.setStrGoodsOrServiceName(listWeiXinGoodsOrderEntity.get(i).getStrGoodsOrServiceName());
					orderVo.setiPurchaseAmount(listWeiXinGoodsOrderEntity.get(i).getiPurchaseAmount());
					orderVo.setdPrice(listWeiXinGoodsOrderEntity.get(i).getdPrice());
					orderVo.setStrUnitName(listWeiXinGoodsOrderEntity.get(i).getStrUnitName());
					orderVo.setdPurchaseCashTotalAmount(listWeiXinGoodsOrderEntity.get(i).getdPurchaseCashTotalAmount());
					orderVo.setdPreferentialPrice(listWeiXinGoodsOrderEntity.get(i).getdPreferentialPrice());
					orderVo.setdPreferentialCashTotalAmount(listWeiXinGoodsOrderEntity.get(i).getdPreferentialCashTotalAmount());
					orderVo.setiIntegrationAmount(listWeiXinGoodsOrderEntity.get(i).getiIntegrationAmount());
					orderVo.setiPurchaseIntegrationAmount(listWeiXinGoodsOrderEntity.get(i).getiPurchaseIntegrationAmount());
					orderVo.setiStatus(listWeiXinGoodsOrderEntity.get(i).getiStatus());
					orderVo.setiPayStandard(listWeiXinGoodsOrderEntity.get(i).getiPayStandard());
					orderVo.setStrPayTime(listWeiXinGoodsOrderEntity.get(i).getStrPayTime());
					orderVo.setiPayType(listWeiXinGoodsOrderEntity.get(i).getiPayType());
					orderVo.setStrThirdPartyTradeFlow(listWeiXinGoodsOrderEntity.get(i).getStrThirdPartyTradeFlow());
					orderVo.setStrExpressNumber(listWeiXinGoodsOrderEntity.get(i).getStrExpressNumber());
					orderVo.setStrExpressCompany(listWeiXinGoodsOrderEntity.get(i).getStrExpressCompany());
					orderVo.setStrComment(listWeiXinGoodsOrderEntity.get(i).getStrComment());
					orderVo.setiReceiveType(listWeiXinGoodsOrderEntity.get(i).getiReceiveType());
					orderVo.setStrReceiveTime(listWeiXinGoodsOrderEntity.get(i).getStrReceiveTime());
					orderVo.setStrCreationTime(listWeiXinGoodsOrderEntity.get(i).getStrCreationTime());
					orderVo.setStrLastAccessedTime(listWeiXinGoodsOrderEntity.get(i).getStrLastAccessedTime());
					ListOrderVoEntity.add(orderVo);
				}
			}
		}
		
		//处理分页问题
	
		iTotalRecord=ListOrderVoEntity.size();
		iTotalPage=iTotalRecord%iPageSize==0?iTotalRecord/iPageSize:iTotalRecord/iPageSize+1;
		if(iPageNum>iTotalPage)
			iPageNum=iTotalPage;
		iListMaxIndex=ListOrderVoEntity.size()-1;
		iPageFromIndex=(iPageNum-1)*iPageSize;
		iPageEndIndex=iPageFromIndex+iPageSize-1;
		List<OrderVo> returnListOrderVo=new ArrayList<OrderVo>();
		if(iListMaxIndex>=iPageEndIndex)
		{
			for(int i=0;i<=iListMaxIndex;i++)
			{
				if(i>=iPageFromIndex&&i<=iPageEndIndex)
					returnListOrderVo.add(ListOrderVoEntity.get(i));
			}
			
		}
		else
		{
			for(int i=0;i<=iListMaxIndex;i++)
			{
				if(i>=iPageFromIndex)
					returnListOrderVo.add(ListOrderVoEntity.get(i));
			}
		}
		Map<String,Object> resultMap=new HashMap<String,Object>();
		resultMap.put("returnListOrderVo", returnListOrderVo);
		resultMap.put("iTotalRecord",iTotalRecord);
		resultMap.put("iTotalPage",iTotalPage);
		return DataTool.constructResponse(ResultCode.OK,"查询成功",resultMap);
	}
}
