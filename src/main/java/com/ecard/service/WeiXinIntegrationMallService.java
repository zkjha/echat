package com.ecard.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.commontools.data.DataTool;
import com.commontools.validate.ValidateTool;
import com.ecard.config.ResultCode;
import com.ecard.mapper.WeiXinIntegrationMallMapper;
import com.ecard.vo.GoodsVO;

@Service
public class WeiXinIntegrationMallService
{
	@Autowired
	private WeiXinIntegrationMallMapper weiXinIntegrationMallMapper;
	
	//查询商品信息
	@Transactional
	public String selectGoordsInfo(Map<String,Object> queryMap) throws Exception
	{
		String strLevelsId="";
		int iTotalRecord,iTotalPage;
		int iPageSize=(Integer)queryMap.get("iPageSize");
		
		//查询会员级别 ID
		strLevelsId=weiXinIntegrationMallMapper.selectMemberLevelsId((String)queryMap.get("strMemberId"));
		//查商品总条数
		iTotalRecord=weiXinIntegrationMallMapper.selectGoodsAmount(queryMap);
		iTotalPage=iTotalRecord%iPageSize==0?iTotalRecord/iPageSize:iTotalRecord/iPageSize+1;
		//查商品属性
		List<GoodsVO> listGoodsVO=weiXinIntegrationMallMapper.selectGoordsInfo(queryMap);
		if(ValidateTool.isNull(listGoodsVO))
			return DataTool.constructResponse(ResultCode.NO_DATA,"暂无数据",null);
		if(listGoodsVO.size()==0)
			return DataTool.constructResponse(ResultCode.NO_DATA,"暂无数据",null);
		
		for(int i=0;i<listGoodsVO.size();i++)
		{
			BigDecimal dDiscount;	//折扣
			BigDecimal dSalePrice;	//原价
			BigDecimal dPreferentialPrice;//会员按级别优惠价
			int iBaseIntegration=0;	//兑换商品所需积分
			int iLevelIntegration=0;//兑换商品所需积分，按会员等级计算
			//查会员等级权益表，看该商品是否有打折价
			String strGoodsId=listGoodsVO.get(i).getStrGoodsId();
			iBaseIntegration=listGoodsVO.get(i).getiRequiredIntegral();
			int iPreferentialType=listGoodsVO.get(i).getiPreferentialType();
			dSalePrice=listGoodsVO.get(i).getdSalePrice();
			queryMap.put("strLevelsId",strLevelsId);
			queryMap.put("strGoodsId",strGoodsId);
			dDiscount=new BigDecimal(String.valueOf(weiXinIntegrationMallMapper.findDiscountInfo(queryMap)));
			dPreferentialPrice=dDiscount.multiply(dSalePrice);
			//查商品优惠表：获取兑换商品的等级积分
			switch(iPreferentialType)
			{
				case 0:
					//不优惠的情况
					iLevelIntegration=iBaseIntegration;
					break;
				case 1:
					//有积分优惠的情况：
					iLevelIntegration=weiXinIntegrationMallMapper.findLevelsIntegrationInfo(queryMap);
					if(iLevelIntegration==0)
						iLevelIntegration=iBaseIntegration;
					break;
			}
			
			listGoodsVO.get(i).setdPreferentialPrice(dPreferentialPrice);
			listGoodsVO.get(i).setiLevelIntegration(iLevelIntegration);
		}
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("listGoodsVO",listGoodsVO);
		resultMap.put("iTotalRecord",iTotalRecord);
		resultMap.put("iTotalPage",iTotalPage);
		return DataTool.constructResponse(ResultCode.OK,"查询成功",resultMap);
	}
}
