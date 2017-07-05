package com.ecard.wexin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.commontools.data.DataTool;
import com.commontools.validate.ValidateTool;
import com.ecard.config.ResultCode;
import com.ecard.config.StaticValue;
import com.ecard.entity.GoodsTypeConfigEntity;
import com.ecard.service.WeiXinIntegrationMallService;
import com.ecard.util.WebSessionUtil;

@Controller
@RequestMapping("/weixin/biz/integrationMall/")
public class WeiXinIntegrationMallController
{
	@Autowired 
	private WebSessionUtil webSessionUtil;
	@Autowired 
	WeiXinIntegrationMallService weiXinIntegrationMallService;
	//查询登陆会员信息
	@ResponseBody
	@RequestMapping("selectGoordsInfo")
	//localhost:8083/weixin/biz/integrationMall/selectGoordsInfo?iSearchGoodsState=0
	//localhost:8083/weixin/biz/integrationMall/selectGoordsInfo?strSearchGoodsTypeId=99d9b574c8f340d0ad8102ad8f46a6ce
	public String selectGoordsInfo(HttpServletRequest request,HttpServletResponse response)
	{
		//要么按商品状态查询 要么按商品类别查询
		//当strSearchGoodsTypeId不传值时默认查询全部类别商品
		String strMemberId="";
		String strSearchGoodsState="";
		String strSearchGoodsTypeId="";//商品类别ID,可以为空（查询全部类别商品)
		String strPageNum=request.getParameter("iPageNum");	//查询起始页数
		String strPageSize=request.getParameter("iPageSize");	//每页显示记录条数
		int iSearchGoodsState=0;//商品的状态:0热门商品；1本周新品,2表查全部
		int iPageNum,iPageSize,iPageFrom;
		
		//strSearchGoodsTypeId:值为0表示查全部类别商品
		strSearchGoodsTypeId=request.getParameter("strSearchGoodsTypeId");//为空则搜索全部类别商品
		//如果按种类查询 则不查新品和热门商品
		if(ValidateTool.isEmptyStr(strSearchGoodsTypeId))
		{
				strSearchGoodsState=request.getParameter("iSearchGoodsState");
				if(ValidateTool.isEmptyStr(strSearchGoodsState))
					iSearchGoodsState=0;	//搜索热门商品
				else
					iSearchGoodsState=Integer.parseInt(strSearchGoodsState);
		}
		else
			iSearchGoodsState=2;
			
		if(ValidateTool.isEmptyStr(strPageNum))
			iPageNum=1;
		else
		{
			if(isNumber(strPageNum))
				iPageNum=Integer.parseInt(strPageNum);
			else
				return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"起始页数格式错误",null);
		}

		if(ValidateTool.isEmptyStr(strPageSize))
			iPageSize=StaticValue.PAGE_SIZE;
		else
		{	if(isNumber(strPageSize))
				iPageSize=Integer.valueOf(strPageSize);
			else
				return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"每页数量格式错误",null);
		}
		
		try {
				// 当前登录的用户信息
				//strMemberId = (String) webSessionUtil.getWeixinSession(request, response).getAttribute("memberid");
				//以下会员ID为测试数据
				strMemberId="377f37a5871f4874a2879dd77758e075";
				if(ValidateTool.isEmptyStr(strMemberId))
					return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"请重新登录",null);
			} catch (Exception e)
			{
				e.printStackTrace();
				return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
			} 
		
		iPageFrom=(iPageNum-1)*iPageSize;
		Map<String,Object> queryMap=new HashMap<String,Object>();
		queryMap.put("iPageFrom",iPageFrom);
		queryMap.put("iPageSize",iPageSize);
		queryMap.put("iSearchGoodsState",iSearchGoodsState);
		queryMap.put("strSearchGoodsTypeId",strSearchGoodsTypeId);
		queryMap.put("strMemberId",strMemberId);
		try{
			return weiXinIntegrationMallService.selectGoordsInfo(queryMap);
			
		}catch(Exception e)
		{
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
			
		}

	}
	
	
	//查单条商品详情
	@ResponseBody
	@RequestMapping("selectGoodsDetailInfo")
	//localhost:8083/weixin/biz/integrationMall/selectGoodsDetailInfo?strGoodsId=84848johoghgh

	public String selectGoodsDetailInfo(HttpServletRequest request,HttpServletResponse response)
	{
		String strMemberId="";
		String strGoodsId=request.getParameter("strGoodsId");
		if(ValidateTool.isEmptyStr(strGoodsId))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"查询关键字不能为空",null);
		
		try {
			// 当前登录的用户信息
			strMemberId = (String) webSessionUtil.getWeixinSession(request, response).getAttribute("memberid");
			//以下会员ID为测试数据
			//strMemberId="377f37a5871f4874a2879dd77758e075";
			if(ValidateTool.isEmptyStr(strMemberId))
				return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"请重新登录",null);
		} catch (Exception e)
		{
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
		} 
		
		Map<String,Object> queryMap=new HashMap<String,Object>();
		queryMap.put("strMemberId", strMemberId);
		queryMap.put("strGoodsId",strGoodsId);
		try{
			return weiXinIntegrationMallService.selectGoodsDetailInfo(queryMap);
		}catch(Exception e)
		{
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
		}
	}

	
	//查询商品类别列表goodsTypeConfigEntity
	@ResponseBody
	@RequestMapping("selectGoodsTypeConfigEntityInfo")
	//localhost:8083/weixin/biz/integrationMall/selectGoodsTypeConfigEntityInfo
	public String selectGoodsTypeConfigEntityInfo(HttpServletRequest request,HttpServletResponse response)
	{
		GoodsTypeConfigEntity goodsTypeConfigEntity=null;
	try{
		List<GoodsTypeConfigEntity> listGoodsTypeConfigEntity=weiXinIntegrationMallService.selectGoodsTypeConfigEntityInfo();
		if(listGoodsTypeConfigEntity==null)
			return DataTool.constructResponse(ResultCode.NO_DATA,"暂无数据",null);
		if(listGoodsTypeConfigEntity.size()==0)
			return DataTool.constructResponse(ResultCode.NO_DATA,"暂无数据",null);
		Map<String,Object> resultMap=new HashMap<String,Object>();
		resultMap.put("listGoodsTypeConfigEntity",listGoodsTypeConfigEntity);
		return DataTool.constructResponse(ResultCode.OK,"查询成功",resultMap);
	}catch(Exception e)
		{
		e.printStackTrace();
		return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
	}
	}
	//校验
	public static boolean isNumber(String strCheckString)
	{
		boolean flag=false;
		Pattern p1 = Pattern.compile("^[0-9]\\d*$|^[1-9]\\d*\\.\\d{1,2}$|^0\\.\\d{1,2}$");
		Matcher matcher=p1.matcher(strCheckString);
		flag=matcher.matches();   
		return flag;
	}
	
}
