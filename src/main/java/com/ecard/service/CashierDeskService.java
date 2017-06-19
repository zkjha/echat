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
import com.ecard.entity.MemberEntity;
import com.ecard.entity.MemberLevelsRightsMappingEntity;
import com.ecard.entity.MemberPurchaseOrderEntity;
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
	public String generatePurchaseOrder(List<MemberPurchaseOrderEntity> listMemberPurchaseOrderEntity) throws Exception
	{
		int iLoopTimes=0;
		if(listMemberPurchaseOrderEntity==null)
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"订单信息不能为空",null);
		
		iLoopTimes=listMemberPurchaseOrderEntity.size();
		if(iLoopTimes==0)
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"订单信息不能为空",null);
		//查询会员级别信息写入订单
		String strMemberId=listMemberPurchaseOrderEntity.get(0).getStrMemberId();
		for(int i=0;i<iLoopTimes;i++)
		{
			MemberEntity memberEntity=cashierDeskMapper.selectMemberInfo(strMemberId);
			String strMemberCardNumber=memberEntity.getStrMembercardnum();
			String strMemberName=memberEntity.getStrRealname();
			String strLevelsId=memberEntity.getStrLevelsid();
			listMemberPurchaseOrderEntity.get(i).setStrMemberCardNumber(strMemberCardNumber);
			listMemberPurchaseOrderEntity.get(i).setStrMemberName(strMemberName);
			listMemberPurchaseOrderEntity.get(i).setStrLevelsId(strLevelsId);
		}
		//调用方法，查找购买服务或商品的折扣价。并返回处理后的最终订单对象
		List<MemberPurchaseOrderEntity> insertOrderEntityList=selectPreferentialInfo(listMemberPurchaseOrderEntity);
		try{
			int iAffectNum=0;
			int iFlag=0;
			for(int i=0;i<insertOrderEntityList.size();i++)
			{
				iAffectNum=cashierDeskMapper.generatePurchaseOrder(insertOrderEntityList.get(i));
				if(iAffectNum!=0)
					iFlag++;
			}
			if(iFlag==insertOrderEntityList.size())
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
	public List<MemberPurchaseOrderEntity> selectPreferentialInfo(List<MemberPurchaseOrderEntity> listMemberPurchaseOrderEntity) throws Exception
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
		List<MemberPurchaseOrderEntity> listAddServiceOrder=new ArrayList<MemberPurchaseOrderEntity>();
		Map<String,Object> queryMap=new HashMap<String,Object>();
		iOrderRecordNum=listMemberPurchaseOrderEntity.size();
		for(int i=0;i<iOrderRecordNum;i++)
		{
			MemberPurchaseOrderEntity memberPurchaseOrderEntity=listMemberPurchaseOrderEntity.get(i);//取出订单记录对象
			iPurchaseType=memberPurchaseOrderEntity.getiPurchaseType();	//取得购买类型：0商品 1服务
			strProductServiceId=memberPurchaseOrderEntity.getStrProductServiceId();//取得购买的商品或服务ID
			strLevelsId=memberPurchaseOrderEntity.getStrLevelsId();	//取得会员级别ID
			strMemberId=memberPurchaseOrderEntity.getStrMemberId();//取得会员ID
			bgBasePrice=memberPurchaseOrderEntity.getdPrice(); //商品或服务原价
			iPurchaseAmount=memberPurchaseOrderEntity.getiPurchaseAmount();	//购买商品或服务数量
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
						memberPurchaseOrderEntity.setdPurchaseCashTotalAmount(bgPurchaseTotalAmount);
						memberPurchaseOrderEntity.setdPreferentialPrice(bgPreferentialPrice);
						memberPurchaseOrderEntity.setdPreferentialCashTotalAmount(bgPreferentialTotalAmount);
						memberPurchaseOrderEntity.setStrComment("该客户在本次消费中享有"+iPurchaseAmount+"次打折服务");
						break;
				
					case 1:
						//查该会员的购买历史
						List<MemberPurchaseOrderEntity> listMemberPurchaseHistory=cashierDeskMapper.selectPurchaseOrderInfo(queryMap);
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
								MemberPurchaseOrderEntity historyOrderObj=listMemberPurchaseHistory.get(j);
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
								memberPurchaseOrderEntity.setdPurchaseCashTotalAmount(bgPurchaseTotalAmount);
								memberPurchaseOrderEntity.setdPreferentialPrice(bgPreferentialPrice);
								memberPurchaseOrderEntity.setdPreferentialCashTotalAmount(bgPreferentialTotalAmount);
								memberPurchaseOrderEntity.setStrComment("该客户在本次消费中免单<在优惠次数内>");
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
									//存下服务订单信息
									MemberPurchaseOrderEntity purchaseServiceEntity=new MemberPurchaseOrderEntity();
									//存免费信息
									purchaseServiceEntity=memberPurchaseOrderEntity;
									bgPurchaseTotalAmount=bgBasePrice.multiply(new BigDecimal(iFreeTimes));//原价计算 的订单总金额
									purchaseServiceEntity.setStrOrderId(DataTool.getUUID());
									purchaseServiceEntity.setdPreferentialPrice(new BigDecimal("0"));
									purchaseServiceEntity.setdPreferentialCashTotalAmount(new BigDecimal("0"));
									purchaseServiceEntity.setiPurchaseAmount(iFreeTimes);
									purchaseServiceEntity.setdPurchaseCashTotalAmount(bgPurchaseTotalAmount);
									purchaseServiceEntity.setStrComment("该客户在本次消费中享有"+iFreeTimes+"次免费服务");
									listAddServiceOrder.add(0,purchaseServiceEntity);

									//存下打折信息
									MemberPurchaseOrderEntity ServiceEntity=new MemberPurchaseOrderEntity();
									bgPurchaseTotalAmount=bgBasePrice.multiply(new BigDecimal(iDiscountTimes));//原价计算 的订单总金额
									ServiceEntity.setStrOrderId(DataTool.getUUID());
									ServiceEntity.setStrOrderNum(memberPurchaseOrderEntity.getStrOrderNum());
									ServiceEntity.setStrMemberId(memberPurchaseOrderEntity.getStrMemberId());
									ServiceEntity.setStrMemberCardNumber(memberPurchaseOrderEntity.getStrMemberCardNumber());
									ServiceEntity.setStrMemberName(memberPurchaseOrderEntity.getStrMemberName());
									ServiceEntity.setStrLevelsId(memberPurchaseOrderEntity.getStrLevelsId());
									ServiceEntity.setStrProductServiceId(memberPurchaseOrderEntity.getStrProductServiceId());
									ServiceEntity.setStrProductServiceName(memberPurchaseOrderEntity.getStrProductServiceName());
									ServiceEntity.setiPurchaseType(1);
									ServiceEntity.setStrUnitName(memberPurchaseOrderEntity.getStrUnitName());
									ServiceEntity.setiStatus(0);
									ServiceEntity.setdPreferentialPrice(bgPreferentialPrice);
									ServiceEntity.setdPreferentialCashTotalAmount(bgPreferentialTotalAmount);
									ServiceEntity.setdPrice(bgBasePrice);
									ServiceEntity.setiPurchaseAmount(iDiscountTimes);
									ServiceEntity.setdPurchaseCashTotalAmount(bgPurchaseTotalAmount);
									ServiceEntity.setStrEmployeeId(memberPurchaseOrderEntity.getStrEmployeeId());
									ServiceEntity.setStrEmployeeName(memberPurchaseOrderEntity.getStrEmployeeName());
									ServiceEntity.setStrEmployeeRealName(memberPurchaseOrderEntity.getStrEmployeeRealName());
									ServiceEntity.setStrCreationTime(memberPurchaseOrderEntity.getStrCreationTime());
									ServiceEntity.setStrLastAccessedTime(memberPurchaseOrderEntity.getStrLastAccessedTime());
									ServiceEntity.setStrComment("该客户在本次消费中享有"+iDiscountTimes+"次打折服务");
									listAddServiceOrder.add(1,ServiceEntity);
								}
								else
								{
									bgPurchaseTotalAmount=bgBasePrice.multiply(new BigDecimal(iPurchaseAmount));	//购买（总金额）原价
									memberPurchaseOrderEntity.setdPurchaseCashTotalAmount(bgPurchaseTotalAmount);
									memberPurchaseOrderEntity.setdPreferentialPrice(bgPreferentialPrice);
									memberPurchaseOrderEntity.setdPreferentialCashTotalAmount(bgPreferentialTotalAmount);
									memberPurchaseOrderEntity.setStrComment("该客户在本次消费中享有"+iDiscountTimes+"次打折服务");
									
								}
									
								
							}
						}
						else
						{
							//该会员在该级别下无购买该服务的历史的情况:
							int iFlag=0;
							if(iPreferentialTimes==0)	//权益规定优惠次数为0
							{
								//打折
								iFlag=1;
								bgPreferentialPrice=bgBasePrice.multiply(bgDiscount);//优惠价
								bgPreferentialTotalAmount=bgPreferentialPrice.multiply(new BigDecimal("1"));
							}
							else
							{
								//免费
								bgPreferentialPrice=new BigDecimal("0");
								bgPreferentialTotalAmount=new BigDecimal("0");
							}
							bgPurchaseTotalAmount=bgBasePrice.multiply(new BigDecimal(iPurchaseAmount));//购买（总金额）原价
							memberPurchaseOrderEntity.setdPurchaseCashTotalAmount(bgPurchaseTotalAmount);
							memberPurchaseOrderEntity.setdPreferentialPrice(bgPreferentialPrice);
							memberPurchaseOrderEntity.setdPreferentialCashTotalAmount(bgPreferentialTotalAmount);
							if(iFlag==1)
								memberPurchaseOrderEntity.setStrComment("该客户在本次消费中享有"+iPurchaseAmount+"次打折服务");
							else
								memberPurchaseOrderEntity.setStrComment("该客户在本次消费中享有"+iPurchaseAmount+"次免单服务");
								
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
			
			memberPurchaseOrderEntity.setdPurchaseCashTotalAmount(bgPurchaseTotalAmount);
			memberPurchaseOrderEntity.setdPreferentialPrice(bgPreferentialPrice);
			memberPurchaseOrderEntity.setdPreferentialCashTotalAmount(bgPreferentialTotalAmount);
			memberPurchaseOrderEntity.setStrComment("未找到相关优惠信息");
			}
			
		}
		//订单优惠信息检查完毕（for完)
		//对订单信息进行整理后，将最终结果存入listInsertOrderInfoEntity对象
		List<MemberPurchaseOrderEntity> listInsertOrderInfoEntity=new ArrayList<MemberPurchaseOrderEntity>();
		String[] removeIndexArray=strIndex.split(",");
		if(removeIndexArray!=null&&!(removeIndexArray[0].trim().isEmpty()))
		{
			for(int i=0;i<listMemberPurchaseOrderEntity.size();i++)
			{	
				int iFlag=0;	//为0 将订单信息放入listInsertOrderInfoEntity，1 不放入
				for(int j=0;j<removeIndexArray.length;j++)
				{
					if(i==Integer.parseInt(removeIndexArray[i]))
						{
						iFlag=1;
						break;
						}
				}
				
				if(iFlag==0)
				{
					MemberPurchaseOrderEntity orderObj=listMemberPurchaseOrderEntity.get(i);
					listInsertOrderInfoEntity.add(orderObj);
				}
			}
		}
		else
		{
			for(int i=0;i<listMemberPurchaseOrderEntity.size();i++)
			{	
				MemberPurchaseOrderEntity orderObj=listMemberPurchaseOrderEntity.get(i);
				listInsertOrderInfoEntity.add(orderObj);
			}
			
		}
		//将处理后的服务订单信息写入listInsertOrderInfoEntity
		if(listAddServiceOrder!=null&&listAddServiceOrder.size()!=0)
		{
			for(int i=0;i<listAddServiceOrder.size();i++)
			{
				MemberPurchaseOrderEntity orderObj=listAddServiceOrder.get(i);
				listInsertOrderInfoEntity.add(orderObj);
			}
		}
		//处理完毕，返回最终订单对象
		return listInsertOrderInfoEntity;
	}
	
	
}
