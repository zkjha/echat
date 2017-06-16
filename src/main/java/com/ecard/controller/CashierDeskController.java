package com.ecard.controller;

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
import com.ecard.entity.GoodsInfoEntity;
import com.ecard.entity.MemberEntity;
import com.ecard.entity.MemberPurchaseOrderEntity;
import com.ecard.entity.ServiceInfoEntity;
import com.ecard.service.CashierDeskService;
import com.ecard.vo.MemberVO;

@Controller
@RequestMapping("/admin/biz/CashierDesk")
public class CashierDeskController
{
	@Autowired
	private CashierDeskService cashierDeskService;
	
	//根据手机号或会员卡号、姓名搜索会员信息
	@ResponseBody
	@RequestMapping("selectMemberInfoFromSearch")
	//localhost:8083/admin/biz/CashierDesk/selectMemberInfoFromSearch?searchText=刘鹏彦
	public String selectMemberInfoFromSearch(HttpServletRequest request,HttpServletResponse response)
	{
		//取得搜索条件
		String searchText=request.getParameter("searchText");
		if(ValidateTool.isEmptyStr(searchText))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"搜索条件不能为空",null);
		try{
		List<MemberVO> listMemberVO=cashierDeskService.selectMemberInfoFromSearch(searchText);
		if(listMemberVO==null)
			return DataTool.constructResponse(ResultCode.NO_DATA,"此用户还不是会员，请先注册会员或帐号未激活，请先激活",null);
		if(listMemberVO.size()==0)
			return DataTool.constructResponse(ResultCode.NO_DATA,"此用户还不是会员，请先注册会员或帐号未激活，请先激活",null);
		Map<String,Object> resultMap=new HashMap<String,Object>();
		resultMap.put("listMemberVO", listMemberVO);
		return DataTool.constructResponse(ResultCode.OK,"查询成功",resultMap);
		}catch (Exception e)
		{
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
		}
	}
	
	//生成订单号：暂时使用
	@ResponseBody
	@RequestMapping("getOrderNum")
	//localhost:8083/admin/biz/CashierDesk/getOrderNum
	public String getOrderNum(HttpServletRequest request,HttpServletResponse response)
	{
		String strOrderNum=DataTool.getUUID();
		Map<String,Object> resultMap=new HashMap<String,Object>();
		resultMap.put("strOrderNum", strOrderNum);
		return DataTool.constructResponse(ResultCode.OK,"生成订单号成功",resultMap);
	}
	
	
	//查询商品信息详情GoodsInfoEntity列表
	@ResponseBody
	@RequestMapping("selectAllGoodsInfoEntity")
	//localhost:8083/admin/biz/CashierDesk/selectAllGoodsInfoEntity?strGoodsTypeId=99d9b574c8f340d0ad8102ad8f46a6ce&ipageNum=1&ipageSize=1
	public String selectAllGoodsInfoEntity(HttpServletRequest request,HttpServletResponse response)
	{
		int iPageNum,iPageSize,iPageFrom,iTotalRecord=0,iTotalPage=0;
		String strPageNum=request.getParameter("ipageNum");
		String strPageSize=request.getParameter("ipageSize");
		String strGoodsTypeId=request.getParameter("strGoodsTypeId");
		if(ValidateTool.isEmptyStr(strGoodsTypeId))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"商品类别ID不能为空",null);
		
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
		iPageFrom=(iPageNum-1)*iPageSize;
		Map<String,Object> queryMap=new HashMap<String,Object>();
		queryMap.put("iPageFrom",iPageFrom);
		queryMap.put("iPageSize",iPageSize);
		queryMap.put("strGoodsTypeId",strGoodsTypeId);
		try{
			List<GoodsInfoEntity> listGoodsInfoEntity=cashierDeskService.selectAllGoodsInfoEntity(queryMap);
			if(ValidateTool.isNull(listGoodsInfoEntity))
				return DataTool.constructResponse(ResultCode.NO_DATA,"暂无数据",null);
			if(listGoodsInfoEntity.size()==0)
				return DataTool.constructResponse(ResultCode.NO_DATA,"暂无数据",null);
			iTotalRecord=cashierDeskService.getGoodsInfoEntityTotalRecordCount(queryMap);
			iTotalPage=iTotalRecord%iPageSize==0?iTotalRecord/iPageSize:iTotalRecord/iPageSize+1;
			Map<String,Object> resultMap = new HashMap<String,Object>();
			resultMap.put("listGoodsInfoEntity",listGoodsInfoEntity);
			resultMap.put("iTotalRecord",iTotalRecord);
			resultMap.put("iTotalPage",iTotalPage);
			return DataTool.constructResponse(ResultCode.OK,"查询成功",resultMap);
		}catch(Exception e)
		{
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"查询失败",null);
		}
	}
	
	
	
	
	//根据服务类别查询所有服务项目信息详情ServiceInfoEntity列表
	@ResponseBody
	@RequestMapping("selectAllServiceInfoEntity")
	//localhost:8083/admin/biz/CashierDesk/selectAllServiceInfoEntity?ipageNum=1&ipageSize=1&strServiceTypeId=1a86d3a79c15437698255b72e4a0fde4
	public String selectAllServiceInfoEntity(HttpServletRequest request,HttpServletResponse response)
	{
		int iPageNum,iPageSize,iPageFrom,iTotalRecord=0,iTotalPage=0;
		String strPageNum=request.getParameter("ipageNum");
		String strPageSize=request.getParameter("ipageSize");
		
		String strServiceTypeId=request.getParameter("strServiceTypeId");
		if(ValidateTool.isEmptyStr(strServiceTypeId))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"服务类别ID不能为空",null);
		
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
		{	
			if(isNumber(strPageSize))
				iPageSize=Integer.parseInt(strPageSize);
			else
				return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"每页数量格式错误",null);
		}
		iPageFrom=(iPageNum-1)*iPageSize;
		Map<String,Object> queryMap=new HashMap<String,Object>();
		queryMap.put("iPageFrom",iPageFrom);
		queryMap.put("iPageSize",iPageSize);
		queryMap.put("strServiceTypeId",strServiceTypeId);
		try{
			List<ServiceInfoEntity> listServiceInfoEntity=cashierDeskService.selectAllServiceInfoEntity(queryMap);
			if(ValidateTool.isNull(listServiceInfoEntity))
				return DataTool.constructResponse(ResultCode.NO_DATA,"暂无数据",null);
			if(listServiceInfoEntity.size()==0)
				return DataTool.constructResponse(ResultCode.NO_DATA,"暂无数据",null);
			iTotalRecord=cashierDeskService.getServiceInfoEntityTotalRecordCount(queryMap);
			iTotalPage=iTotalRecord%iPageSize==0?iTotalRecord/iPageSize:iTotalRecord/iPageSize+1;
			Map<String,Object> resultMap = new HashMap<String,Object>();
			resultMap.put("listServiceInfoEntity",listServiceInfoEntity);
			resultMap.put("iTotalRecord",iTotalRecord);
			resultMap.put("iTotalPage",iTotalPage);
			return DataTool.constructResponse(ResultCode.OK,"查询成功",resultMap);
		}catch(Exception e)
		{
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"查询失败",null);
		}
	}
	
	
	//生成购买订单表
	@ResponseBody
	@RequestMapping("generatePurchaseOrder")
	//localhost:8083/admin/biz/CashierDesk/generatePurchaseOrder
/*
	public String generatePurchaseOrder(HttpServletRequest request,HttpServletResponse response)
	{
		//前台传入的数据格式约定为:
	}
*/	
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
