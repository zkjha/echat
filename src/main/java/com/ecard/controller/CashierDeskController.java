package com.ecard.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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
import com.commontools.date.DateStyle;
import com.commontools.date.DateTool;
import com.commontools.validate.ValidateTool;
import com.ecard.config.ResultCode;
import com.ecard.config.StaticValue;
import com.ecard.entity.EmployeeEntity;
import com.ecard.entity.GoodsInfoEntity;
import com.ecard.entity.MemberEntity;
import com.ecard.entity.PurchaseOrderDetailEntity;
import com.ecard.entity.ServiceInfoEntity;
import com.ecard.service.CashierDeskService;
import com.ecard.util.WebSessionUtil;
import com.ecard.vo.MemberVO;

@Controller
@RequestMapping("/admin/biz/CashierDesk")
public class CashierDeskController
{
	@Autowired
	private CashierDeskService cashierDeskService;
	@Autowired
	private WebSessionUtil webSessionUtil;
	
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
	//localhost:8083/admin/biz/CashierDesk/selectAllGoodsInfoEntity?strGoodsTypeId=99d9b574c8f340d0ad8102ad8f46a6ce&ipageNum=1&ipageSize=1&iPreferentialType=0
	public String selectAllGoodsInfoEntity(HttpServletRequest request,HttpServletResponse response)
	{
		int iPageNum,iPageSize,iPageFrom,iTotalRecord=0,iTotalPage=0;
		int iPreferentialType=2;	//0 查无优惠 1 查有优惠 2查全部
		String strPageNum=request.getParameter("ipageNum");
		String strPageSize=request.getParameter("ipageSize");
		String strGoodsTypeId=request.getParameter("strGoodsTypeId");
		String strPreferentialType=request.getParameter("iPreferentialType");
		if(!ValidateTool.isEmptyStr(strPreferentialType))
			iPreferentialType=Integer.parseInt(strPreferentialType);
		else
			iPreferentialType=2;
	
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
		queryMap.put("iPreferentialType",iPreferentialType);
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
	//localhost:8083/admin/biz/CashierDesk/selectAllServiceInfoEntity?ipageNum=1&ipageSize=1&strServiceTypeId=1a86d3a79c15437698255b72e4a0fde4&&iPreferentialType=0
	public String selectAllServiceInfoEntity(HttpServletRequest request,HttpServletResponse response)
	{
		int iPreferentialType=2;	//0 查无优惠 1 查有优惠 2查全部
		int iPageNum,iPageSize,iPageFrom,iTotalRecord=0,iTotalPage=0;
		String strPageNum=request.getParameter("ipageNum");
		String strPageSize=request.getParameter("ipageSize");
		
		String strServiceTypeId=request.getParameter("strServiceTypeId");
		String strPreferentialType=request.getParameter("iPreferentialType");
		if(!ValidateTool.isEmptyStr(strPreferentialType))
			iPreferentialType=Integer.parseInt(strPreferentialType);
		else
			iPreferentialType=2;
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
		queryMap.put("iPreferentialType",iPreferentialType);
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
	
	
	//生成购买订单表,订单详情表
	@ResponseBody
	@RequestMapping("generatePurchaseOrder")
	//localhost:8083/admin/biz/CashierDesk/generatePurchaseOrder
	public String generatePurchaseOrder(HttpServletRequest request,HttpServletResponse response)
	{
		//前台传入的数据格式约定为:orderInfo=商品服务ID,购买类别：0商品1服务,商品服务名称,购买数量,购买单价,计量单位|商品服务ID,购买类别：0商品1服务,商品服务名称,购买数量,购买单价,计量单位
		//会员ID:strMemberId
		int orderRecordNum=0;
		int iFlag=0;	//是否跳出循环标志	
		String[] orderRecordArray;	//订单记录数组
		String[] orderRecordFieldsArray;	//订单各字段数组
		String strMemberId=request.getParameter("strMemberId");	//会员ID
		String orderInfo=request.getParameter("orderInfo");	//订单详情
		String strOrderNum=request.getParameter("strOrderNum");	//订单编号
		if(ValidateTool.isEmptyStr(strMemberId))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"会员ID不能为空",null);
		if(ValidateTool.isEmptyStr(orderInfo))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"订单信息不能为空",null);
		if(ValidateTool.isEmptyStr(strOrderNum))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"订单编号不能为空",null);
		/*
		EmployeeEntity employeeEntity = null;
		try {
			employeeEntity=(EmployeeEntity)webSessionUtil.getWebSession(request, response).getAttribute("employeeEntity");
		} catch (Exception e) {
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
		}
		if(employeeEntity==null){
			return DataTool.constructResponse(ResultCode.NO_DATA, "操作员不存在", null);
		}
		 */
		//取得登录员工信息
		/*String strEmployeeId=employeeEntity.getStrEmployeeid();
		String strEmployeeName=employeeEntity.getStrLoginname();
		String strEmployeeRealName=employeeEntity.getStrRealname();
		*/
		//以下3个为测试用数据

		String strEmployeeId=DataTool.getUUID();
		String strEmployeeName="test";
		String strEmployeeRealName="david li";
		
		String strCreationTime=DateTool.DateToString(new Date(),DateStyle.YYYY_MM_DD_HH_MM);
		String strLastAccessedTime=DateTool.DateToString(new Date(),DateStyle.YYYY_MM_DD_HH_MM);
		//取出每条订单记录信息以及订单记录条数
		orderRecordArray=orderInfo.split("\\|");
		if(orderRecordArray==null)
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"订单信息不能为空",null);
		
		orderRecordNum=orderRecordArray.length;
		List<PurchaseOrderDetailEntity> listPurchaseOrderDetailEntity=new ArrayList<PurchaseOrderDetailEntity>();
		for(int i=0;i<orderRecordNum;i++)
		{
			
			PurchaseOrderDetailEntity purchaseOrderDetailEntity=new PurchaseOrderDetailEntity();
			orderRecordFieldsArray=orderRecordArray[i].split(",");
			if(orderRecordFieldsArray!=null)
			{
				int iFields=orderRecordFieldsArray.length;
				if(iFields!=6)
				{
					iFlag=1;
					break;
				}
			}
			else
			{
				iFlag=1;
				break;
			}
			
			//检查数据有效性
			if(!isNumber(orderRecordFieldsArray[1]))	//检查购买类型 0为购买商品 1为购买服务
				return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"商品服务类型格式错误",null);
			
			if(!isNumber(orderRecordFieldsArray[3]))	//检查购买数量
				return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"购买数量格式错误",null);
			
			if(!isNumber(orderRecordFieldsArray[4]))	//检查售价
				return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"售价格式错误",null);
			purchaseOrderDetailEntity.setStrOrderId(DataTool.getUUID());
			purchaseOrderDetailEntity.setStrMemberId(strMemberId);
			purchaseOrderDetailEntity.setStrOrderNum(strOrderNum);
			purchaseOrderDetailEntity.setStrProductServiceId(orderRecordFieldsArray[0]);
			purchaseOrderDetailEntity.setiPurchaseType(Integer.parseInt(orderRecordFieldsArray[1]));
			purchaseOrderDetailEntity.setStrProductServiceName(orderRecordFieldsArray[2]);
			purchaseOrderDetailEntity.setiPurchaseAmount(Integer.parseInt(orderRecordFieldsArray[3]));
			purchaseOrderDetailEntity.setdPrice(new BigDecimal(orderRecordFieldsArray[4]));
			purchaseOrderDetailEntity.setiStatus(0);//订单状态 0 待支付
			purchaseOrderDetailEntity.setStrUnitName(orderRecordFieldsArray[5]);
			purchaseOrderDetailEntity.setStrEmployeeId(strEmployeeId);
			purchaseOrderDetailEntity.setStrEmployeeName(strEmployeeName);
			purchaseOrderDetailEntity.setStrEmployeeRealName(strEmployeeRealName);
			purchaseOrderDetailEntity.setStrCreationTime(strCreationTime);
			purchaseOrderDetailEntity.setStrLastAccessedTime(strLastAccessedTime);
			
			listPurchaseOrderDetailEntity.add(purchaseOrderDetailEntity);
		}
		if(iFlag==1)
			return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"订单信息格式有误",null);
		
		try{
			return cashierDeskService.generatePurchaseOrder(listPurchaseOrderDetailEntity);
		}catch(Exception e)
		{
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
		}
		
	}

	
	//积分支付商品或服务 
	@ResponseBody
	@RequestMapping("payWithIntegration")
	//localhost:8083/admin/biz/CashierDesk/payWithIntegration?strOrderId=50000
	public String payWithIntegration(HttpServletRequest request,HttpServletResponse response)
	{
		String strOrderId=request.getParameter("strOrderId");
		if(ValidateTool.isEmptyStr(strOrderId))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"订单 号不能为空",null);
		try{
			return cashierDeskService.payWithIntegration(strOrderId) ;
			}catch(Exception e)
			{
				e.printStackTrace();
				return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
			}
		
	}

	
	//支付完毕 修改订单状态
	@ResponseBody
	@RequestMapping("editOrderPaymentStatus")
	//localhost:8083/admin/biz/CashierDesk/editOrderPaymentStatus?strOrderId=xorderI456788&iPayType=0&strThirdPartyTradeFlow=
	public String editOrderPaymentStatus(HttpServletRequest request,HttpServletResponse response)
	{
		String strOrderId=request.getParameter("strOrderId");	//订单号
		String strPayType=request.getParameter("iPayType");	//订单支付方式:0积分兑换 1 微信支付 2 支付宝支付 3 线下现金支付
		int iPayStandard=0;	//支付标准备 0 会员价支付  1 原价支付  以后可能会修改
		int iPayType=0;
		String strPayTime=DateTool.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS);
		String strLastAccessedTime=DateTool.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS);
		String strThirdPartyTradeFlow=request.getParameter("strThirdPartyTradeFlow").trim();
		if(ValidateTool.isEmptyStr(strOrderId))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"订单 号不能为空",null);
		if(ValidateTool.isEmptyStr(strPayType))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"支付方式不能为空",null);
		if(isNumber(strPayType))
			iPayType=Integer.parseInt(strPayType);
		else
			return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"支付方式格式错误",null);
		Map<String,Object> orderStatusMap=new HashMap<String,Object>();
		orderStatusMap.put("strOrderId",strOrderId);
		orderStatusMap.put("iPayType",iPayType);
		orderStatusMap.put("iPayStandard",iPayStandard);
		orderStatusMap.put("strPayTime",strPayTime);
		orderStatusMap.put("iStatus",1);
		orderStatusMap.put("strThirdPartyTradeFlow",strThirdPartyTradeFlow);
		orderStatusMap.put("strLastAccessedTime",strLastAccessedTime);
		
		try{
			return cashierDeskService.editOrderPaymentStatus(orderStatusMap);
		}catch(Exception e)
		{
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
		}
		
	}
	
	
	//会员卡余额支付
	@ResponseBody
	@RequestMapping("payWithMemberCard")
	//localhost:8083/admin/biz/CashierDesk/payWithMemberCard?strOrderId=xorderI456788
	public String payWithMemberCard(HttpServletRequest request,HttpServletResponse response)
	{
		String strOrderId=request.getParameter("strOrderId");
		if(ValidateTool.isEmptyStr(strOrderId))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"订单 号不能为空",null);
		try{
			return cashierDeskService.payWithMemberCard(strOrderId) ;
			}catch(Exception e)
			{
				e.printStackTrace();
				return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
			}
		
	}
	

	//现金支付
	@ResponseBody
	@RequestMapping("payWithCash")
	//localhost:8083/admin/biz/CashierDesk/payWithCash?strOrderId=xorderI456788
	public String payWithCash(HttpServletRequest request,HttpServletResponse response)
	{
		String strOrderId=request.getParameter("strOrderId");
		if(ValidateTool.isEmptyStr(strOrderId))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"订单 号不能为空",null);
		try{
			return cashierDeskService.payWithCash(strOrderId) ;
			}catch(Exception e)
			{
				e.printStackTrace();
				return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
			}
		
	}
	
	
	
	
	
	//查询订单详情列表purchaseOrderDetailEntity
	@ResponseBody
	@RequestMapping("selectPurchaseOrderDetailEntity")
	//localhost:8083/admin/biz/CashierDesk/selectPurchaseOrderDetailEntity?strMemberId=e8b9c2cabd364e15ade4cce6480c7b7d
	public String selectPurchaseOrderDetailEntity(HttpServletRequest request,HttpServletResponse response)
	{

		String strMemberId=request.getParameter("strMemberId");
		if(ValidateTool.isEmptyStr("strMemberId"))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"参数strOrderId不能为空",null);
		try{
			List<PurchaseOrderDetailEntity> listPurchaseOrderDetailEntity=cashierDeskService.selectPurchaseOrderDetailEntityInfo(strMemberId);
			if(listPurchaseOrderDetailEntity==null)
				return DataTool.constructResponse(ResultCode.NO_DATA,"暂无数据",null);
			if(listPurchaseOrderDetailEntity.size()==0)
				return DataTool.constructResponse(ResultCode.NO_DATA,"暂无数据",null);
			Map<String,Object> resultMap=new HashMap<String,Object>();
			resultMap.put("listPurchaseOrderDetailEntity",listPurchaseOrderDetailEntity);
			return DataTool.constructResponse(ResultCode.OK,"查询成功",resultMap);
		}catch(Exception e)
			{
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
			}
	}
	
	
	
	
	//删除一条订单详情purchaseOrderDetailEntity记录
	@ResponseBody
	@RequestMapping("deletePurchaseOrderEntity")
	//localhost:8083/admin/biz/CashierDesk/deletePurchaseOrderEntity?strOrderId=xorderI456788
	public String deletePurchaseOrderEntity(HttpServletRequest request, HttpServletResponse response)
	{
		String strOrderId=request.getParameter("strOrderId");
		if(ValidateTool.isEmptyStr(strOrderId))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数不能为空",null);
		try{
			return cashierDeskService.deletePurchaseOrderEntity(strOrderId);
		}catch(Exception e)
		{
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"删除失败", null);
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
