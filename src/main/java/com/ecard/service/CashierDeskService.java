package com.ecard.service;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.commontools.data.DataTool;
import com.commontools.validate.ValidateTool;
import com.ecard.config.ResultCode;
import com.ecard.entity.GoodsInfoEntity;
import com.ecard.entity.IntegralModRecord;
import com.ecard.entity.MemberEntity;
import com.ecard.entity.MemberLevelsRightsMappingEntity;
import com.ecard.entity.PurchaseOrderDetailEntity;
import com.ecard.entity.PurchaseOrderEntity;
import com.ecard.entity.ServiceInfoEntity;
import com.ecard.mapper.CashierDeskMapper;
import com.ecard.vo.MemberVO;

@Service
public class CashierDeskService
{
	@Autowired
	private CashierDeskMapper cashierDeskMapper;
	//根据手机号或会员卡号、姓名搜索会员信息
	public List<MemberVO> selectMemberInfoFromSearch(String searchText) throws Exception
	{
		if(ValidateTool.isEmptyStr(searchText))
			return null;
		else
			return cashierDeskMapper.selectMemberInfoFromSearch(searchText);
		
	}
	
	
	//查询服务信息详情ServiceInfoEntity记录数量
	@Transactional
	public int getServiceInfoEntityTotalRecordCount(Map<String,Object> queryMap) throws Exception
	{
		return cashierDeskMapper.getServiceInfoEntityTotalRecordCount(queryMap);
	}

	//查询服务信息详情ServiceInfoEntity列表
	public List<ServiceInfoEntity> selectAllServiceInfoEntity(Map<String,Object> queryMap) throws Exception
	{
		return cashierDeskMapper.selectAllServiceInfoEntity(queryMap);
	}
	
	//查询商品信息详情GoodsInfoEntity记录数量
	public int getGoodsInfoEntityTotalRecordCount(Map<String,Object> queryMap) throws Exception
	{
		return cashierDeskMapper.getGoodsInfoEntityTotalRecordCount(queryMap);
	}

	//查询商品信息详情GoodsInfoEntity列表
	public List<GoodsInfoEntity> selectAllGoodsInfoEntity(Map<String,Object> queryMap) throws Exception
	{
		return cashierDeskMapper.selectAllGoodsInfoEntity(queryMap);
	}
	
	//生成购买订单
	@Transactional
	public String generatePurchaseOrder(List<PurchaseOrderDetailEntity> listPurchaseOrderDetailEntity) throws Exception
	{
		int iLoopTimes=0;
		if(listPurchaseOrderDetailEntity==null)
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"订单信息不能为空",null);
		
		iLoopTimes=listPurchaseOrderDetailEntity.size();
		if(iLoopTimes==0)
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"订单信息不能为空",null);
		//查询会员级别信息写入订单
		String strMemberId=listPurchaseOrderDetailEntity.get(0).getStrMemberId();
		for(int i=0;i<iLoopTimes;i++)
		{
			MemberEntity memberEntity=cashierDeskMapper.selectMemberInfo(strMemberId);
			String strMemberCardNumber=memberEntity.getStrMembercardnum();
			String strMemberName=memberEntity.getStrRealname();
			String strLevelsId=memberEntity.getStrLevelsid();
			listPurchaseOrderDetailEntity.get(i).setStrMemberCardNumber(strMemberCardNumber);
			listPurchaseOrderDetailEntity.get(i).setStrMemberName(strMemberName);
			listPurchaseOrderDetailEntity.get(i).setStrLevelsId(strLevelsId);
		}
		//调用方法，查找购买服务或商品的折扣价。并返回处理后的最终订单对象
		List<PurchaseOrderDetailEntity> insertOrderEntityList=selectPreferentialInfo(listPurchaseOrderDetailEntity);
		/*将添加 积分优惠方法转移至积分结算方法
		 * 调用方法，查找商品或服务是积分优惠信息，更改订单积分信息
		 * String orderStatus=addIntegrationInfo(insertOrderEntityList);
		if("ERROR1".equals(orderStatus))
			return DataTool.constructResponse(ResultCode.UNKNOW_ERROR,"请将有积分优惠的商品服务与无积分优惠的商品服务分开下单",null);
		if("ERROR2".equals(orderStatus))
			return DataTool.constructResponse(ResultCode.UNKNOW_ERROR,"请配置积分按等级优惠信息",null);
		*/
		try{
			int iAffectNum=0;
			int iFlag=0;
			for(int i=0;i<insertOrderEntityList.size();i++)
			{
				iAffectNum=cashierDeskMapper.generatePurchaseOrder(insertOrderEntityList.get(i));
				if(iAffectNum!=0)
					iFlag++;
			}
			
			//调用方法生成订单表(总表)
			int iInsertRecordNum=0;
			iInsertRecordNum=insertOrderInfo(insertOrderEntityList);
			if(iFlag==insertOrderEntityList.size()&&iInsertRecordNum!=0)
				return DataTool.constructResponse(ResultCode.OK,"插入订单信息成功",null);
			else
				return DataTool.constructResponse(ResultCode.UNKNOW_ERROR,"插入订单信息失败",null);
			
		}catch(Exception e)
		{
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
		}
		
	}
	
	//查询购买商品或服务的优惠信息
	@Transactional
	public List<PurchaseOrderDetailEntity> selectPreferentialInfo(List<PurchaseOrderDetailEntity> listPurchaseOrderDetailEntity) throws Exception
	{
		int iPreferentialTimes=0;	//优惠次数
		int iPurchaseType;		//购买的类型：0商品 1服务
		int iOrderRecordNum;	//订单条数
		int iPurchaseAmount;	//购买商品或服务数量
		BigDecimal bgDiscount;		//折扣
		BigDecimal bgBasePrice;		//原价
		BigDecimal bgPurchaseTotalAmount;	//原价购买后的总金额
		BigDecimal bgPreferentialPrice;		//优惠价
		BigDecimal bgPreferentialTotalAmount;	//优惠后的总金额
		
		String strProductServiceId;	//购买的商品或服务ID
		String strLevelsId;	//会员级别ID
		String strMemberId;	//会员ID
		String strIndex="";//记录下订单对订单对象中购买次数超过一次既享有免费一定次数和打折一定次数的记录的LIST索引
		//listAddServiceOrder需要添加的服务订单信息:将下单后的服务信息分解为免费信息和打折信息
		List<PurchaseOrderDetailEntity> listAddServiceOrder=new ArrayList<PurchaseOrderDetailEntity>();
		Map<String,Object> queryMap=new HashMap<String,Object>();
		iOrderRecordNum=listPurchaseOrderDetailEntity.size();
		for(int i=0;i<iOrderRecordNum;i++)
		{
			PurchaseOrderDetailEntity purchaseOrderDetailEntity=listPurchaseOrderDetailEntity.get(i);//取出订单记录对象
			iPurchaseType=purchaseOrderDetailEntity.getiPurchaseType();	//取得购买类型：0商品 1服务
			strProductServiceId=purchaseOrderDetailEntity.getStrProductServiceId();//取得购买的商品或服务ID
			strLevelsId=purchaseOrderDetailEntity.getStrLevelsId();	//取得会员级别ID
			strMemberId=purchaseOrderDetailEntity.getStrMemberId();//取得会员ID
			bgBasePrice=purchaseOrderDetailEntity.getdPrice(); //商品或服务原价
			iPurchaseAmount=purchaseOrderDetailEntity.getiPurchaseAmount();	//购买商品或服务数量
			queryMap.put("iPurchaseType",iPurchaseType);
			queryMap.put("strProductServiceId",strProductServiceId);
			queryMap.put("strLevelsId",strLevelsId);
			queryMap.put("strMemberId",strMemberId);
			
			MemberLevelsRightsMappingEntity memberLevelsRightsMappingEntity=cashierDeskMapper.getPreferentialInfo(queryMap);
			if(memberLevelsRightsMappingEntity!=null&&!memberLevelsRightsMappingEntity.getStrLevelsRightsMappingId().isEmpty())
			{
				//找到相关等级权益 情况：
				bgDiscount=memberLevelsRightsMappingEntity.getDdiscount();//取出商品或服务的折扣
				//取出购买商品或服务优惠次数
				iPreferentialTimes=memberLevelsRightsMappingEntity.getIpreferentialTimes();
				switch (iPurchaseType)
				{
					case 0:
						//计算商品优惠价
						bgPurchaseTotalAmount=bgBasePrice.multiply(new BigDecimal(iPurchaseAmount));	//购买（总金额）原价
						bgPreferentialPrice=bgBasePrice.multiply(bgDiscount);//优惠价=原价*折扣
						bgPreferentialTotalAmount=bgPreferentialPrice.multiply(new BigDecimal(iPurchaseAmount));//购买（总金额）优惠价
						purchaseOrderDetailEntity.setdPurchaseCashTotalAmount(bgPurchaseTotalAmount);
						purchaseOrderDetailEntity.setdPreferentialPrice(bgPreferentialPrice);
						purchaseOrderDetailEntity.setdPreferentialCashTotalAmount(bgPreferentialTotalAmount);
						purchaseOrderDetailEntity.setStrComment("该客户在本次消费中享有"+iPurchaseAmount+"次打折服务");
						break;
				
					case 1:
						//免单次数为不为0 ,既免单又打折的情况：
						//查该会员的购买历史
						List<PurchaseOrderDetailEntity> listMemberPurchaseHistory=cashierDeskMapper.selectPurchaseOrderInfo(queryMap);
						//如果listMemberPurchaseHistory不为空，即该会员有购买该服务的历史记录
						if(listMemberPurchaseHistory!=null&&listMemberPurchaseHistory.size()!=0)
						{
							//该会员在该级别下有购买该服务的历史的情况:
							int iLoopTimes=listMemberPurchaseHistory.size();
							int iPurchaseTimes=0;	//已购买服务的次数
							int iHistoryAndFutureTimes;	//已购服务次数+欲购服务次数之和
							int iFreeTimes=0;	//免费次数
							int iDiscountTimes=0;	//打折次数
							//统计购买该会员在该级别下购买该服务的次数
							for(int j=0;j<iLoopTimes;j++)
							{
								PurchaseOrderDetailEntity historyOrderObj=listMemberPurchaseHistory.get(j);
								iPurchaseTimes+=historyOrderObj.getiPurchaseAmount();
							}
							
							iHistoryAndFutureTimes=iPurchaseTimes+iPurchaseAmount;
							//判断该次服务该免费还是打折
							if(iHistoryAndFutureTimes<=iPreferentialTimes)	//如果欲购次数与已购次数之和小于=优惠次数
							{
								//免费
								bgPreferentialPrice=new BigDecimal("0");
								bgPreferentialTotalAmount=new BigDecimal("0");
								
								bgPurchaseTotalAmount=bgBasePrice.multiply(new BigDecimal(iPurchaseAmount));	//购买（总金额）原价
								purchaseOrderDetailEntity.setdPurchaseCashTotalAmount(bgPurchaseTotalAmount);
								purchaseOrderDetailEntity.setdPreferentialPrice(bgPreferentialPrice);
								purchaseOrderDetailEntity.setdPreferentialCashTotalAmount(bgPreferentialTotalAmount);
								purchaseOrderDetailEntity.setStrComment("该客户在本次消费中免单<在优惠次数内>");
							}
							else
							{
								//判断本次消费中有几次服务可以免费，几次可以打折
								iFreeTimes=iPreferentialTimes-iPurchaseTimes;
								if(iFreeTimes>0)
								{
									iDiscountTimes=iPurchaseAmount-iFreeTimes;
									bgPreferentialPrice=bgBasePrice.multiply(bgDiscount);//优惠价=原价*折扣
									bgPreferentialTotalAmount=bgPreferentialPrice.multiply(new BigDecimal(iDiscountTimes));//购买（总金额）优惠价
								}
								else
								{
									iDiscountTimes=iPurchaseAmount;
									bgPreferentialPrice=bgBasePrice.multiply(bgDiscount);//优惠价=原价*折扣
									bgPreferentialTotalAmount=bgPreferentialPrice.multiply(new BigDecimal(iDiscountTimes));//购买（总金额）优惠价
									
								}
								if(iFreeTimes>0)
								{
									//既免费又打折
									if(strIndex.isEmpty())
										strIndex=String.valueOf(i);
									else
										strIndex+=","+String.valueOf(i);
									//存免费信息
									PurchaseOrderDetailEntity freeServiceEntity=new PurchaseOrderDetailEntity();
									bgPurchaseTotalAmount=bgBasePrice.multiply(new BigDecimal(iFreeTimes));//原价计算 的订单总金额
									freeServiceEntity.setStrOrderId(DataTool.getUUID());
									freeServiceEntity.setStrOrderNum(purchaseOrderDetailEntity.getStrOrderNum());
									freeServiceEntity.setStrMemberId(purchaseOrderDetailEntity.getStrMemberId());
									freeServiceEntity.setStrMemberCardNumber(purchaseOrderDetailEntity.getStrMemberCardNumber());
									freeServiceEntity.setStrMemberName(purchaseOrderDetailEntity.getStrMemberName());
									freeServiceEntity.setStrLevelsId(purchaseOrderDetailEntity.getStrLevelsId());
									freeServiceEntity.setStrProductServiceId(purchaseOrderDetailEntity.getStrProductServiceId());
									freeServiceEntity.setStrProductServiceName(purchaseOrderDetailEntity.getStrProductServiceName());
									freeServiceEntity.setiPurchaseType(1);
									freeServiceEntity.setStrUnitName(purchaseOrderDetailEntity.getStrUnitName());
									freeServiceEntity.setiStatus(0);
									freeServiceEntity.setdPreferentialPrice(new BigDecimal("0"));
									freeServiceEntity.setdPreferentialCashTotalAmount(new BigDecimal("0"));
									freeServiceEntity.setdPrice(bgBasePrice);
									freeServiceEntity.setiPurchaseAmount(iFreeTimes);
									freeServiceEntity.setdPurchaseCashTotalAmount(bgPurchaseTotalAmount);
									freeServiceEntity.setStrEmployeeId(purchaseOrderDetailEntity.getStrEmployeeId());
									freeServiceEntity.setStrEmployeeName(purchaseOrderDetailEntity.getStrEmployeeName());
									freeServiceEntity.setStrEmployeeRealName(purchaseOrderDetailEntity.getStrEmployeeRealName());
									freeServiceEntity.setStrCreationTime(purchaseOrderDetailEntity.getStrCreationTime());
									freeServiceEntity.setStrLastAccessedTime(purchaseOrderDetailEntity.getStrLastAccessedTime());
									freeServiceEntity.setStrComment("该客户在本次消费中享有"+iFreeTimes+"次免单服务");
									listAddServiceOrder.add(0,freeServiceEntity);

									//存下打折信息
									PurchaseOrderDetailEntity discountServiceEntity=new PurchaseOrderDetailEntity();
									bgPurchaseTotalAmount=bgBasePrice.multiply(new BigDecimal(iDiscountTimes));//原价计算 的订单总金额
									discountServiceEntity.setStrOrderId(DataTool.getUUID());
									discountServiceEntity.setStrOrderNum(purchaseOrderDetailEntity.getStrOrderNum());
									discountServiceEntity.setStrMemberId(purchaseOrderDetailEntity.getStrMemberId());
									discountServiceEntity.setStrMemberCardNumber(purchaseOrderDetailEntity.getStrMemberCardNumber());
									discountServiceEntity.setStrMemberName(purchaseOrderDetailEntity.getStrMemberName());
									discountServiceEntity.setStrLevelsId(purchaseOrderDetailEntity.getStrLevelsId());
									discountServiceEntity.setStrProductServiceId(purchaseOrderDetailEntity.getStrProductServiceId());
									discountServiceEntity.setStrProductServiceName(purchaseOrderDetailEntity.getStrProductServiceName());
									discountServiceEntity.setiPurchaseType(1);
									discountServiceEntity.setStrUnitName(purchaseOrderDetailEntity.getStrUnitName());
									discountServiceEntity.setiStatus(0);
									discountServiceEntity.setdPreferentialPrice(bgPreferentialPrice);
									discountServiceEntity.setdPreferentialCashTotalAmount(bgPreferentialTotalAmount);
									discountServiceEntity.setdPrice(bgBasePrice);
									discountServiceEntity.setiPurchaseAmount(iDiscountTimes);
									discountServiceEntity.setdPurchaseCashTotalAmount(bgPurchaseTotalAmount);
									discountServiceEntity.setStrEmployeeId(purchaseOrderDetailEntity.getStrEmployeeId());
									discountServiceEntity.setStrEmployeeName(purchaseOrderDetailEntity.getStrEmployeeName());
									discountServiceEntity.setStrEmployeeRealName(purchaseOrderDetailEntity.getStrEmployeeRealName());
									discountServiceEntity.setStrCreationTime(purchaseOrderDetailEntity.getStrCreationTime());
									discountServiceEntity.setStrLastAccessedTime(purchaseOrderDetailEntity.getStrLastAccessedTime());
									discountServiceEntity.setStrComment("该客户在本次消费中享有"+iDiscountTimes+"次打折服务");
									listAddServiceOrder.add(1,discountServiceEntity);
								}
								else
								{
									//只打折的情况
									bgPurchaseTotalAmount=bgBasePrice.multiply(new BigDecimal(iPurchaseAmount));	//购买（总金额）原价
									purchaseOrderDetailEntity.setdPurchaseCashTotalAmount(bgPurchaseTotalAmount);
									purchaseOrderDetailEntity.setdPreferentialPrice(bgPreferentialPrice);
									purchaseOrderDetailEntity.setdPreferentialCashTotalAmount(bgPreferentialTotalAmount);
									purchaseOrderDetailEntity.setStrComment("该客户在本次消费中享有"+iDiscountTimes+"次打折服务");
									
								}
									
								
							}
						}
						else
						{
							//该会员在该级别下无购买该服务的历史的情况:
						
							if(iPreferentialTimes==0)	//权益规定优惠次数为0
							{
								//打折
								
								bgPreferentialPrice=bgBasePrice.multiply(bgDiscount);//优惠价
								bgPreferentialTotalAmount=bgPreferentialPrice.multiply(new BigDecimal(iPurchaseAmount));
								bgPurchaseTotalAmount=bgBasePrice.multiply(new BigDecimal(iPurchaseAmount));
								
								purchaseOrderDetailEntity.setdPurchaseCashTotalAmount(bgPurchaseTotalAmount);
								purchaseOrderDetailEntity.setdPreferentialPrice(bgPreferentialPrice);
								purchaseOrderDetailEntity.setdPreferentialCashTotalAmount(bgPreferentialTotalAmount);
								purchaseOrderDetailEntity.setStrComment("该客户在本次消费中享有"+iPurchaseAmount+"次打折服务");
							}
							else
							{
								//优惠次数不为0的情况
								if(iPurchaseAmount<=iPreferentialTimes)
								{
									//免单
									bgPreferentialPrice=new BigDecimal("0");//优惠价
									bgPreferentialTotalAmount=new BigDecimal("0");
									bgPurchaseTotalAmount=bgBasePrice.multiply(new BigDecimal(iPurchaseAmount));
									
									purchaseOrderDetailEntity.setdPurchaseCashTotalAmount(bgPurchaseTotalAmount);
									purchaseOrderDetailEntity.setdPreferentialPrice(bgPreferentialTotalAmount);
									purchaseOrderDetailEntity.setdPreferentialCashTotalAmount(bgPreferentialTotalAmount);
									purchaseOrderDetailEntity.setStrComment("该客户在本次消费中享有"+iPurchaseAmount+"次免单服务<在优惠次数内>");
								}
								else
								{
									//既免费又打折
									
									//iPurchaseAmount  欲买服务数量
									int iFreeTimes=iPreferentialTimes;	//免费次数
									int iDiscountTimes=iPurchaseAmount-iFreeTimes;	//打折次数
									bgPreferentialPrice=bgBasePrice.multiply(bgDiscount);//优惠价
									bgPreferentialTotalAmount=bgPreferentialPrice.multiply(new BigDecimal(iDiscountTimes));
									bgPurchaseTotalAmount=bgBasePrice.multiply(new BigDecimal(iDiscountTimes));
									
									if(strIndex.isEmpty())
										strIndex=String.valueOf(i);
									else
										strIndex+=","+String.valueOf(i);
									//存免费信息
									PurchaseOrderDetailEntity freeServiceEntity=new PurchaseOrderDetailEntity();
									bgPurchaseTotalAmount=bgBasePrice.multiply(new BigDecimal(iFreeTimes));//原价计算 的订单总金额
									freeServiceEntity.setStrOrderId(DataTool.getUUID());
									freeServiceEntity.setStrOrderNum(purchaseOrderDetailEntity.getStrOrderNum());
									freeServiceEntity.setStrMemberId(purchaseOrderDetailEntity.getStrMemberId());
									freeServiceEntity.setStrMemberCardNumber(purchaseOrderDetailEntity.getStrMemberCardNumber());
									freeServiceEntity.setStrMemberName(purchaseOrderDetailEntity.getStrMemberName());
									freeServiceEntity.setStrLevelsId(purchaseOrderDetailEntity.getStrLevelsId());
									freeServiceEntity.setStrProductServiceId(purchaseOrderDetailEntity.getStrProductServiceId());
									freeServiceEntity.setStrProductServiceName(purchaseOrderDetailEntity.getStrProductServiceName());
									freeServiceEntity.setiPurchaseType(1);
									freeServiceEntity.setStrUnitName(purchaseOrderDetailEntity.getStrUnitName());
									freeServiceEntity.setiStatus(0);
									freeServiceEntity.setdPreferentialPrice(new BigDecimal("0"));
									freeServiceEntity.setdPreferentialCashTotalAmount(new BigDecimal("0"));
									freeServiceEntity.setdPrice(bgBasePrice);
									freeServiceEntity.setiPurchaseAmount(iFreeTimes);
									freeServiceEntity.setdPurchaseCashTotalAmount(bgPurchaseTotalAmount);
									freeServiceEntity.setStrEmployeeId(purchaseOrderDetailEntity.getStrEmployeeId());
									freeServiceEntity.setStrEmployeeName(purchaseOrderDetailEntity.getStrEmployeeName());
									freeServiceEntity.setStrEmployeeRealName(purchaseOrderDetailEntity.getStrEmployeeRealName());
									freeServiceEntity.setStrCreationTime(purchaseOrderDetailEntity.getStrCreationTime());
									freeServiceEntity.setStrLastAccessedTime(purchaseOrderDetailEntity.getStrLastAccessedTime());
									freeServiceEntity.setStrComment("该客户在本次消费中享有"+iFreeTimes+"次免单服务");
									listAddServiceOrder.add(0,freeServiceEntity);

									//存下打折信息
									PurchaseOrderDetailEntity discountServiceEntity=new PurchaseOrderDetailEntity();
									bgPurchaseTotalAmount=bgBasePrice.multiply(new BigDecimal(iDiscountTimes));//原价计算 的订单总金额
									discountServiceEntity.setStrOrderId(DataTool.getUUID());
									discountServiceEntity.setStrOrderNum(purchaseOrderDetailEntity.getStrOrderNum());
									discountServiceEntity.setStrMemberId(purchaseOrderDetailEntity.getStrMemberId());
									discountServiceEntity.setStrMemberCardNumber(purchaseOrderDetailEntity.getStrMemberCardNumber());
									discountServiceEntity.setStrMemberName(purchaseOrderDetailEntity.getStrMemberName());
									discountServiceEntity.setStrLevelsId(purchaseOrderDetailEntity.getStrLevelsId());
									discountServiceEntity.setStrProductServiceId(purchaseOrderDetailEntity.getStrProductServiceId());
									discountServiceEntity.setStrProductServiceName(purchaseOrderDetailEntity.getStrProductServiceName());
									discountServiceEntity.setiPurchaseType(1);
									discountServiceEntity.setStrUnitName(purchaseOrderDetailEntity.getStrUnitName());
									discountServiceEntity.setiStatus(0);
									discountServiceEntity.setdPreferentialPrice(bgPreferentialPrice);
									discountServiceEntity.setdPreferentialCashTotalAmount(bgPreferentialTotalAmount);
									discountServiceEntity.setdPrice(bgBasePrice);
									discountServiceEntity.setiPurchaseAmount(iDiscountTimes);
									discountServiceEntity.setdPurchaseCashTotalAmount(bgPurchaseTotalAmount);
									discountServiceEntity.setStrEmployeeId(purchaseOrderDetailEntity.getStrEmployeeId());
									discountServiceEntity.setStrEmployeeName(purchaseOrderDetailEntity.getStrEmployeeName());
									discountServiceEntity.setStrEmployeeRealName(purchaseOrderDetailEntity.getStrEmployeeRealName());
									discountServiceEntity.setStrCreationTime(purchaseOrderDetailEntity.getStrCreationTime());
									discountServiceEntity.setStrLastAccessedTime(purchaseOrderDetailEntity.getStrLastAccessedTime());
									discountServiceEntity.setStrComment("该客户在本次消费中享有"+iDiscountTimes+"次打折服务");
									listAddServiceOrder.add(1,discountServiceEntity);
									
								}
							}
						}
						
						break;
				}	
		}
		else
		{
			//未找到相关等级权益 情况：
			bgPreferentialPrice=bgBasePrice;//优惠价=原价
			bgPurchaseTotalAmount=bgBasePrice.multiply(new BigDecimal(iPurchaseAmount));//购买（总金额）原价
			bgPreferentialTotalAmount=bgPreferentialPrice.multiply(new BigDecimal(iPurchaseAmount));
			
			purchaseOrderDetailEntity.setdPurchaseCashTotalAmount(bgPurchaseTotalAmount);
			purchaseOrderDetailEntity.setdPreferentialPrice(bgPreferentialPrice);
			purchaseOrderDetailEntity.setdPreferentialCashTotalAmount(bgPreferentialTotalAmount);
			purchaseOrderDetailEntity.setStrComment("未找到相关优惠信息");
			}
			
		}
		//订单优惠信息检查完毕（for完)
		//对订单信息进行整理后，将最终结果存入listInsertOrderInfoEntity对象
		List<PurchaseOrderDetailEntity> listInsertOrderInfoEntity=new ArrayList<PurchaseOrderDetailEntity>();
		String[] removeIndexArray=strIndex.split(",");
		if(removeIndexArray!=null&&!(removeIndexArray[0].trim().isEmpty()))
		{
			for(int i=0;i<listPurchaseOrderDetailEntity.size();i++)
			{	
				int iFlag=0;	//为0 将订单信息放入listInsertOrderInfoEntity，1 不放入
				for(int j=0;j<removeIndexArray.length;j++)
				{
					if(i==Integer.parseInt(removeIndexArray[j]))
						{
						iFlag=1;
						break;
						}
				}
				if(iFlag==0)
				{
					PurchaseOrderDetailEntity orderObj=listPurchaseOrderDetailEntity.get(i);
					listInsertOrderInfoEntity.add(orderObj);
				}
			}

		}
	
		else
		{
			for(int i=0;i<listPurchaseOrderDetailEntity.size();i++)
			{	
				PurchaseOrderDetailEntity orderObj=listPurchaseOrderDetailEntity.get(i);
				listInsertOrderInfoEntity.add(orderObj);
			}
			
		}
		
		//将处理后的服务订单信息写入listInsertOrderInfoEntity
		if(listAddServiceOrder!=null&&listAddServiceOrder.size()!=0)
		{
			for(int i=0;i<listAddServiceOrder.size();i++)
			{
				PurchaseOrderDetailEntity orderObj=listAddServiceOrder.get(i);
				listInsertOrderInfoEntity.add(orderObj);
			}
		}
		//处理完毕，返回最终订单对象
		return listInsertOrderInfoEntity;
	}
	
	
	
	//添加积分优惠信息
	@Transactional
	public Map<String,Object> addIntegrationInfo(List<PurchaseOrderDetailEntity> insertOrderEntityList) throws Exception
	{
		String strProductServiceId;	//购买商品或服务的ID
		int iPurchaseType;//购买的类别　0商品  1 服务
		Map<String,Object> queryMap=new HashMap<String,Object>();
		queryMap.put("strLevelsId",insertOrderEntityList.get(0).getStrLevelsId());
		boolean booBreak=false;	//是否退出程序执行  false 不退出  true 退出
		int iTotalIntegrationAmount=0;//	订单所需积分总数量
		Map<String,Object> orderStatusMap=new HashMap<String,Object>();
		int iMemberIntegration=0;	//会员剩余积分数量
		iMemberIntegration=cashierDeskMapper.selectMemberIntegration(insertOrderEntityList.get(0).getStrMemberId());
		for(PurchaseOrderDetailEntity orderDetailObj:insertOrderEntityList)
		{	
			int iPreferentialType=0;//优惠状态 0 不优惠 1 按会员等级优惠
			int iPurchaseAmount=orderDetailObj.getiPurchaseAmount();//购买商品或服务的数量
			int iItemIntegrationAmount=0;	//多个商品或服务所需积分数量
			int iUseIntegrationAmount=0;//	 有等级优惠的单个商品或服务所需积分数量
			int iRequiredIntegrationAmount=0;	//无等级优惠情况下，兑换商品或服务所需积分数量
			int iExtendedAttribute=0;//会员扩展属性
			strProductServiceId=orderDetailObj.getStrProductServiceId();
			iPurchaseType=orderDetailObj.getiPurchaseType();
			queryMap.put("strProductServiceId",strProductServiceId);
			String strErrorObj="";
			int  iErrorCode=0;
			
			//按类别进行相应的处理 0商品 1服务  有优惠就计算兑换商品或服务积分
			switch (iPurchaseType)
			{
				case 0:
					//查商品表tb_goods，看是否有优惠
					GoodsInfoEntity goodsInfoEntity=cashierDeskMapper.selectGoodsPreferentialType(strProductServiceId);
					iPreferentialType=goodsInfoEntity.getiPreferentialType();
					iRequiredIntegrationAmount=goodsInfoEntity.getiRequiredIntegral();
					if(iPreferentialType==0&&iRequiredIntegrationAmount==0)
					{
						//如果 商品表里无积分信息并且商品优惠表里也无积分信息 则退出积分结算
						iErrorCode=0;
						strErrorObj=orderDetailObj.getStrProductServiceName();	//错误对象
						orderStatusMap.put("iErrorCode", iErrorCode);
						orderStatusMap.put("strErrorObj", strErrorObj);
						booBreak=true;
						break;
					}
					
					//计算商品所需积分
					if(iPreferentialType!=0)
					{
						//有等级优惠的情况下:
						iUseIntegrationAmount=cashierDeskMapper.selectGoodsIntegration(queryMap);
						//判断商品积分优惠表中是否找到相应积分信息。若商品表中有 积分，但商品优惠表中未找到相关记录，则报错
						if(iUseIntegrationAmount==0)
						{
							iErrorCode=1;
							strErrorObj=orderDetailObj.getStrProductServiceName();	//错误对象
							orderStatusMap.put("iErrorCode", iErrorCode);
							orderStatusMap.put("strErrorObj", strErrorObj);
							booBreak=true;
							break;			//两表不一致报错
						}
						else
						{
							if(iUseIntegrationAmount>iRequiredIntegrationAmount)	//等级优惠后的商品或服务积分数量>无优惠的商品或服务积分数量的情况：
								iUseIntegrationAmount=iRequiredIntegrationAmount;
							iItemIntegrationAmount=iUseIntegrationAmount*iPurchaseAmount;
							iTotalIntegrationAmount+=iItemIntegrationAmount;
						}
					}
					else
					{
						//无等级优惠的情况下：
						iUseIntegrationAmount=iRequiredIntegrationAmount;
						iItemIntegrationAmount=iRequiredIntegrationAmount*iPurchaseAmount;
						iTotalIntegrationAmount+=iItemIntegrationAmount;
						
					}
					break;
				case 1:
					//如果是服务
					ServiceInfoEntity serviceInfoEntity=cashierDeskMapper.selectServicePreferentialType(strProductServiceId);
					iPreferentialType=serviceInfoEntity.getiPreferentialType();
					iRequiredIntegrationAmount=serviceInfoEntity.getiRequiredIntegral();
					if(iPreferentialType==0&&iRequiredIntegrationAmount==0)
					{
						iErrorCode=0;
						strErrorObj=orderDetailObj.getStrProductServiceName();	//错误对象
						orderStatusMap.put("iErrorCode", iErrorCode);
						orderStatusMap.put("strErrorObj", strErrorObj);
						booBreak=true;
						break;
					}
					
					if(iPreferentialType!=0)
					{	
						//有优惠的情况:
						iUseIntegrationAmount=cashierDeskMapper.selectServiceIntegration(queryMap);
						if(iUseIntegrationAmount==0)
						{
							booBreak=true;
							iErrorCode=1;
							strErrorObj=orderDetailObj.getStrProductServiceName();	//错误对象
							orderStatusMap.put("iErrorCode", iErrorCode);
							orderStatusMap.put("strErrorObj", strErrorObj);
							break;			//两表不致报错
						}
						else
						{
							if(iUseIntegrationAmount>iRequiredIntegrationAmount)	//等级优惠后的商品或服务积分数量>无优惠的商品或服务积分数量的情况：
								iUseIntegrationAmount=iRequiredIntegrationAmount;
							iItemIntegrationAmount=iUseIntegrationAmount*iPurchaseAmount;
							iTotalIntegrationAmount+=iItemIntegrationAmount;
						}
					}
					else
					{
						//无等级优惠的情况下：
						iUseIntegrationAmount=iRequiredIntegrationAmount;
						iItemIntegrationAmount=iRequiredIntegrationAmount*iPurchaseAmount;
						iTotalIntegrationAmount+=iItemIntegrationAmount;
						
					}
					break;
			}
			iMemberIntegration=iMemberIntegration-iItemIntegrationAmount;
			iExtendedAttribute=iMemberIntegration;
			if(booBreak==true)
				return orderStatusMap;
			orderDetailObj.setiPurchaseIntegrationAmount(iItemIntegrationAmount);
			orderDetailObj.setiIntegrationAmount(iUseIntegrationAmount);
			orderDetailObj.setiExtendedAttribute(iExtendedAttribute);
			//订单对象检查完毕 for 完
		}
		//将更新的积分信息写入订单详情，及汇总表
		Map<String,Object> updateMap=new HashMap<String,Object>();
		updateMap.put("strOrderId",insertOrderEntityList.get(0).getStrOrderNum());
		updateMap.put("iTotalIntegrationAmount",iTotalIntegrationAmount);
		updateMap.put("iExtendedAttribute",iMemberIntegration);
		int iDetailOrderNum=0;
		int iOrderNum=0;
		int iLoopTime=0;
		for(PurchaseOrderDetailEntity itemObj:insertOrderEntityList)
		{
			iDetailOrderNum=cashierDeskMapper.updateOrderDetailInfo(itemObj);//更新详情表
			if(iDetailOrderNum!=0)
				iLoopTime++;
		}
		
		iOrderNum=cashierDeskMapper.updateOrderInfo(updateMap);	//更新订单总表
		if(iOrderNum==0||iLoopTime!=insertOrderEntityList.size())
		{
			//更新积分信息失败
			orderStatusMap.put("iErrorCode",2);
			orderStatusMap.put("strErrorObj","");
			return orderStatusMap;
		}
		else
		{
			//更新积分信息成功
			orderStatusMap.put("iErrorCode",3);
			orderStatusMap.put("strErrorObj","");
			return orderStatusMap;
			
		}
			
	}
	
	
	
	//生成订单汇总表 tb_purchase_order
	public int insertOrderInfo(List<PurchaseOrderDetailEntity> listOrderDetailEntity) throws Exception
	{
		PurchaseOrderDetailEntity purchaseOrderDetailEntity=listOrderDetailEntity.get(0);//订单详情对象
		PurchaseOrderEntity purchaseOrderEntity=new PurchaseOrderEntity();	//订单汇总对象
		int integrationAmount=0;	//兑换此批订单所需积分数量
		BigDecimal baseTotalCashAmount=new BigDecimal("0");	//按原价计算此批订单总额
		BigDecimal preferentialTotalCashAmount=new BigDecimal("0");	//按会员优惠价计算此批订单总金额
		for(PurchaseOrderDetailEntity detailObj:listOrderDetailEntity)
		{
			integrationAmount+=detailObj.getiPurchaseIntegrationAmount();
			baseTotalCashAmount=baseTotalCashAmount.add(detailObj.getdPurchaseCashTotalAmount());
			preferentialTotalCashAmount=preferentialTotalCashAmount.add(detailObj.getdPreferentialCashTotalAmount());
			
		}
		
		//设置订单汇总表属性
		purchaseOrderEntity.setStrOrderId(purchaseOrderDetailEntity.getStrOrderNum());	//订单编号
		purchaseOrderEntity.setStrMemberId(purchaseOrderDetailEntity.getStrMemberId());	//会员 ID
		purchaseOrderEntity.setStrMemberCardNumber(purchaseOrderDetailEntity.getStrMemberCardNumber());	//会员卡编号
		purchaseOrderEntity.setStrMemberName(purchaseOrderDetailEntity.getStrMemberName());	//会员姓名 
		purchaseOrderEntity.setStrLevelsId(purchaseOrderDetailEntity.getStrLevelsId());	//会员级别 ID
		purchaseOrderEntity.setdPurchaseCashTotalAmount(baseTotalCashAmount);//订单原价总金额
		purchaseOrderEntity.setdPreferentialCashTotalAmount(preferentialTotalCashAmount);//订单优惠价总金额
		purchaseOrderEntity.setiPurchaseIntegrationAmount(integrationAmount);//订单所需积分数量
		purchaseOrderEntity.setiStatus(0);//订单未支付
		purchaseOrderEntity.setiPayStandard(0);//会员价支付
		purchaseOrderEntity.setiPayType(purchaseOrderDetailEntity.getiPayType());
		purchaseOrderEntity.setStrThirdPartyTradeFlow(purchaseOrderDetailEntity.getStrThirdPartyTradeFlow());
		purchaseOrderEntity.setStrEmployeeId(purchaseOrderDetailEntity.getStrEmployeeId());
		purchaseOrderEntity.setStrEmployeeName(purchaseOrderDetailEntity.getStrEmployeeName());
		purchaseOrderEntity.setStrEmployeeRealName(purchaseOrderDetailEntity.getStrEmployeeRealName());
		purchaseOrderEntity.setStrCreationTime(purchaseOrderDetailEntity.getStrCreationTime());
		purchaseOrderEntity.setStrLastAccessedTime(purchaseOrderDetailEntity.getStrLastAccessedTime());
		
		int iNum=cashierDeskMapper.insertOrderInfo(purchaseOrderEntity);
		return iNum;
	}
	
	
	//会员积分支付--订单积分查询
	@Transactional
	public String payWithIntegration(String strOrderId) throws Exception
	{
		String strMemberId="";
		//调用方法，查找商品或服务的积分优惠信息，更改订单积分信息
		List<PurchaseOrderDetailEntity> insertOrderDetailEntityList=cashierDeskMapper.selectOrderDetailEntity(strOrderId);
		Map<String,Object> orderStatusMap=addIntegrationInfo(insertOrderDetailEntityList);
		int iErrorCode=(Integer)orderStatusMap.get("iErrorCode");
		String strErrorObj=(String)orderStatusMap.get("strErrorObj");
		switch(iErrorCode)
		{
		case 0:
			return DataTool.constructResponse(ResultCode.UNKNOW_ERROR,"无可用积分配置信息,请先配置: "+strErrorObj+" 的积分信息或采用其它方式结算",null);
		
		case 1:
			return DataTool.constructResponse(ResultCode.UNKNOW_ERROR,"无等级积分兑换信息, 请先配置: "+strErrorObj+" 的积分等级兑换信息或采用其它方式结算",null);
		case 2:
			return DataTool.constructResponse(ResultCode.UNKNOW_ERROR,"积分优惠信息更新失败!",null);
		}
		
		PurchaseOrderEntity purchaseOrderEntity=cashierDeskMapper.selectPurchaseOrder(strOrderId);
		if(purchaseOrderEntity!=null&&!"".equals(purchaseOrderEntity.getStrOrderId()))
			strMemberId=purchaseOrderEntity.getStrMemberId();
		else
			return DataTool.constructResponse(ResultCode.NO_DATA,"暂无订单数据",null);
		if(ValidateTool.isEmptyStr(strMemberId))
			return DataTool.constructResponse(ResultCode.NO_DATA,"暂无会员信息",null);
	
		//根据会员ID 查会员信息
		MemberVO MemberVO=cashierDeskMapper.selectMemberInfoById(strMemberId);
		Map<String,Object> resultMap=new HashMap<String,Object>();
		resultMap.put("MemberVO",MemberVO);
		resultMap.put("purchaseOrderEntity",purchaseOrderEntity);
		return DataTool.constructResponse(ResultCode.OK,"查询订单积分支付信息成功",resultMap);
	}
	
	
	
	//会员卡余额支付
	@Transactional
	public String payWithMemberCardCash(String strOrderId) throws Exception
	{
		String strMemberId="";
		PurchaseOrderEntity purchaseOrderEntity=cashierDeskMapper.selectPurchaseOrder(strOrderId);
		if(purchaseOrderEntity!=null&&!"".equals(purchaseOrderEntity.getStrOrderId()))
			strMemberId=purchaseOrderEntity.getStrMemberId();
		else
			return DataTool.constructResponse(ResultCode.NO_DATA,"暂无订单数据",null);
		if(ValidateTool.isEmptyStr(strMemberId))
			return DataTool.constructResponse(ResultCode.NO_DATA,"暂无订单的会员信息",null);
		//根据会员ID 查会员信息
		MemberVO MemberVO=cashierDeskMapper.selectMemberInfoById(strMemberId);
		Map<String,Object> resultMap=new HashMap<String,Object>();
		resultMap.put("MemberVO",MemberVO);
		resultMap.put("purchaseOrderEntity",purchaseOrderEntity);
		return DataTool.constructResponse(ResultCode.OK,"查询订单会员卡支付信息成功",resultMap);
	}
	
	
	//会员卡余额支付完毕，修改订单信息及会员信息
	@Transactional
	public String cardCashPayOverEditStatus(Map<String,Object> orderStatusMap) throws Exception
	{
		int iOrderDetailAffectNum=0;	//订单详情表记录影响条数
		int iOrderAffectNum=0;	//订单总表记录影响条数
		cashierDeskMapper.modifyMemberDbalanceInfo(orderStatusMap);
		iOrderDetailAffectNum=cashierDeskMapper.editOrderDetailPaymentStatus(orderStatusMap);//修改订单详情表
		iOrderAffectNum=cashierDeskMapper.editOrderPaymentStatus(orderStatusMap);//修改订单总表
		if(iOrderDetailAffectNum!=0&&iOrderAffectNum!=0)
			return DataTool.constructResponse(ResultCode.OK,"该笔订单已用会员卡余额完成支付",null);
		else
			return DataTool.constructResponse(ResultCode.UNKNOW_ERROR,"订单付款状态修改失败",null);
		
	}
	
	//现金支付
	@Transactional
	public String payWithCash(String strOrderId) throws Exception
	{
		String strMemberId="";
		PurchaseOrderEntity purchaseOrderEntity=cashierDeskMapper.selectPurchaseOrder(strOrderId);
		if(purchaseOrderEntity!=null&&!"".equals(purchaseOrderEntity.getStrOrderId()))
			strMemberId=purchaseOrderEntity.getStrMemberId();
		else
			return DataTool.constructResponse(ResultCode.NO_DATA,"暂无订单数据",null);
		if(ValidateTool.isEmptyStr(strMemberId))
			return DataTool.constructResponse(ResultCode.NO_DATA,"暂无订单的会员信息",null);
		//根据会员ID 查会员信息
		MemberVO MemberVO=cashierDeskMapper.selectMemberInfoById(strMemberId);
		Map<String,Object> resultMap=new HashMap<String,Object>();
		resultMap.put("MemberVO",MemberVO);
		resultMap.put("purchaseOrderEntity",purchaseOrderEntity);
		return DataTool.constructResponse(ResultCode.OK,"查询订单现金支付信息成功",resultMap);
	}
	
	
	//支付完毕 修改订单状态
	/*
	@Transactional
	public String editOrderPaymentStatus(Map<String,Object> orderStatusMap) throws Exception
	{
		int iOrderDetailAffectNum=0;	//订单详情表记录影响条数
		int iOrderAffectNum=0;	//订单总表记录影响条数
		String strMsg="";
		int iPayType=0;//支付方式/订单支付方式:0积分兑换 1 微信支付 2 支付宝支付 3 线下现金支付
		iPayType=(Integer)orderStatusMap.get("iPayType");
		switch(iPayType)
		{
			case 0:
				strMsg="积分";
				break;
			case 1:
				strMsg="微信";
				break;
			case 2:
				strMsg="支付宝";
				break;
			case 3:
				strMsg="现金";
				break;
		}
			
		iOrderDetailAffectNum=cashierDeskMapper.editOrderDetailPaymentStatus(orderStatusMap);//修改详情表
		iOrderAffectNum=cashierDeskMapper.editOrderPaymentStatus(orderStatusMap);//修改总表
		if(iOrderDetailAffectNum!=0&&iOrderAffectNum!=0)
			return DataTool.constructResponse(ResultCode.OK,"该笔订单已用"+strMsg+"完成支付",null);
		else
			return DataTool.constructResponse(ResultCode.UNKNOW_ERROR,"订单付款状态修改失败",null);

	}
	*/
	
	//积分支付完毕，修改订单状态，积分流水变更，会员积分信息
	@Transactional
	public String integrationPayOverEditStatus(Map<String,Object> orderStatusMap,IntegralModRecord integrationChangeEntity) throws Exception
	{
		int iOrderDetailAffectNum=0;	//订单详情表记录影响条数
		int iOrderAffectNum=0;	//订单总表记录影响条数
		int iRestIntegration=0;	//会员积分余额
		
		//修改会员表信息，修改积分流水
		iRestIntegration=(Integer)orderStatusMap.get("iMemberIntegration")-(Integer)orderStatusMap.get("iPurchaseIntegrationAmount");
		if(iRestIntegration<0)
			return DataTool.constructResponse(ResultCode.UNKNOW_ERROR,"会员积分余额不足，请采用其它方式付款",null);
		orderStatusMap.put("iRestIntegration",iRestIntegration);
		cashierDeskMapper.modifyMemberIntegrationInfo(orderStatusMap);
		cashierDeskMapper.modifyIntegrationChangeFlow(integrationChangeEntity);
		iOrderDetailAffectNum=cashierDeskMapper.editOrderDetailPaymentStatus(orderStatusMap);//修改订单详情表
		iOrderAffectNum=cashierDeskMapper.editOrderPaymentStatus(orderStatusMap);//修改订单总表
		if(iOrderDetailAffectNum!=0&&iOrderAffectNum!=0)
			return DataTool.constructResponse(ResultCode.OK,"该笔订单已用积分完成支付",null);
		else
			return DataTool.constructResponse(ResultCode.UNKNOW_ERROR,"订单付款状态修改失败",null);

	}
	
	//现金支付完毕修改订单状态
	@Transactional
	public String cashPayOverEditStatus(Map<String,Object> orderStatusMap) throws Exception
	{
		int iOrderDetailAffectNum=0;	//订单详情表记录影响条数
		int iOrderAffectNum=0;	//订单总表记录影响条数
		iOrderDetailAffectNum=cashierDeskMapper.editOrderDetailPaymentStatus(orderStatusMap);//修改订单详情表
		iOrderAffectNum=cashierDeskMapper.editOrderPaymentStatus(orderStatusMap);//修改订单总表
		if(iOrderDetailAffectNum!=0&&iOrderAffectNum!=0)
			return DataTool.constructResponse(ResultCode.OK,"该笔订单已用现金完成支付",null);
		else
			return DataTool.constructResponse(ResultCode.UNKNOW_ERROR,"订单付款状态修改失败",null);
	}
	
	//查询订单详情列表purchaseOrderDetailEntity
	public List<PurchaseOrderDetailEntity> selectPurchaseOrderDetailEntityInfo(String strMemberId) throws Exception
	{
		return cashierDeskMapper.selectPurchaseOrderDetailEntity(strMemberId);
	}
	
	
	//删除一条订单详情purchaseOrderDetailEntity记录
	@Transactional(rollbackFor=Exception.class)
	public String deletePurchaseOrderEntity(String strOrderId) throws Exception
	{
		int iDetilAffectNum=cashierDeskMapper.deletePurchaseOrderDetailEntity(strOrderId);
		int iAffectNum=cashierDeskMapper.deletePurchaseOrderEntity(strOrderId);
		if(iDetilAffectNum!=0&&iAffectNum!=0)
			return DataTool.constructResponse(ResultCode.OK,"删除成功",null);
		else
			return DataTool.constructResponse(ResultCode.UNKNOW_ERROR, "删除失败",null);
	}
	
	//支付宝支付
	public Map<String,Object> payWithAliPayment(String strOrderId) throws Exception
	{
		
		Map<String,Object> resultMap=new HashMap<String,Object>();
		String strMemberId="";
		PurchaseOrderEntity purchaseOrderEntity=cashierDeskMapper.selectPurchaseOrder(strOrderId);
		if(purchaseOrderEntity!=null&&!"".equals(purchaseOrderEntity.getStrOrderId()))
			strMemberId=purchaseOrderEntity.getStrMemberId();
		else
			return resultMap;
		
		if(ValidateTool.isEmptyStr(strMemberId))
			return resultMap;
		//根据会员ID 查会员信息
		MemberVO MemberVO=cashierDeskMapper.selectMemberInfoById(strMemberId);
		resultMap.put("MemberVO",MemberVO);
		resultMap.put("purchaseOrderEntity",purchaseOrderEntity);
		return resultMap;
	}
	
	
	
    
	
}
