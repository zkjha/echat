package com.ecard.controller;

import java.io.IOException;
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
/*
import com.alipay.api.AlipayResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.demo.trade.config.Configs;
import com.alipay.demo.trade.model.ExtendParams;
import com.alipay.demo.trade.model.GoodsDetail;
import com.alipay.demo.trade.model.builder.AlipayTradePrecreateRequestBuilder;
import com.alipay.demo.trade.model.result.AlipayF2FPrecreateResult;
import com.alipay.demo.trade.service.AlipayMonitorService;
import com.alipay.demo.trade.service.AlipayTradeService;
import com.alipay.demo.trade.service.impl.AlipayMonitorServiceImpl;
import com.alipay.demo.trade.service.impl.AlipayTradeServiceImpl;
import com.alipay.demo.trade.service.impl.AlipayTradeWithHBServiceImpl;
import com.alipay.demo.trade.utils.ZxingUtils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
*/

import com.commontools.data.DataTool;
import com.commontools.date.DateStyle;
import com.commontools.date.DateTool;
import com.commontools.validate.ValidateTool;
import com.ecard.config.ResultCode;
import com.ecard.config.StaticValue;
import com.ecard.entity.EmployeeEntity;
import com.ecard.entity.GoodsInfoEntity;
import com.ecard.entity.IntegralModRecord;
import com.ecard.entity.PurchaseOrderDetailEntity;
import com.ecard.entity.PurchaseOrderEntity;
import com.ecard.entity.ServiceInfoEntity;
import com.ecard.service.CashierDeskService;
import com.ecard.util.QRCodeTool;
import com.ecard.util.WebSessionUtil;
import com.ecard.vo.MemberVO;
import com.google.zxing.WriterException;

@Controller
@RequestMapping("/admin/biz/CashierDesk")
public class CashierDeskController
{
	@Autowired
	private CashierDeskService cashierDeskService;
	@Autowired
	private WebSessionUtil webSessionUtil;
	
//	@Autowired
//	private static AlipayTradeService   tradeService;
//	@Autowired
    // 支付宝当面付2.0服务（集成了交易保障接口逻辑）
//    private static AlipayTradeService   tradeWithHBService;
//	@Autowired
    // 支付宝交易保障接口服务，供测试接口api使用，请先阅读readme.txt
 //   private static AlipayMonitorService monitorService;
	
//private static Log                  log = LogFactory.getLog(Main.class);
//	 static {
	        /** 一定要在创建AlipayTradeService之前调用Configs.init()设置默认参数
	         *  Configs会读取classpath下的zfbinfo.properties文件配置信息，如果找不到该文件则确认该文件是否在classpath目录
	         */
		 
	//        Configs.init("zfbinfo.properties");

	        /** 使用Configs提供的默认参数
	         *  AlipayTradeService可以使用单例或者为静态成员对象，不需要反复new
	         */
	  //      tradeService = new AlipayTradeServiceImpl.ClientBuilder().build();

	        // 支付宝当面付2.0服务（集成了交易保障接口逻辑）
	   //    tradeWithHBService = new AlipayTradeWithHBServiceImpl.ClientBuilder().build();

	        /** 如果需要在程序中覆盖Configs提供的默认参数, 可以使用ClientBuilder类的setXXX方法修改默认参数 否则使用代码中的默认设置 */
	  //      monitorService = new AlipayMonitorServiceImpl.ClientBuilder()
	//            .setGatewayUrl("http://mcloudmonitor.com/gateway.do").setCharset("GBK")
	//            .setFormat("json").build();
	//    }
	
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
	//localhost:8083/admin/biz/CashierDesk/generatePurchaseOrder?strMemberId=377f37a5871f4874a2879dd77758e075&strOrderNum=oeojgjg&orderInfo=ehiwhvoidieoi2395959,0,车载香蕉,2,8.5,个
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

	
	//积分支付--查询 
	@ResponseBody
	@RequestMapping("payWithIntegration")
	//localhost:8083/admin/biz/CashierDesk/payWithIntegration?strOrderId=10000
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

	
	//积分支付完毕--- 修改订单状态,会员信息，积分流水
	@ResponseBody
	@RequestMapping("integrationPayOverEditStatus")
	//localhost:8083/admin/biz/CashierDesk/integrationPayOverEditStatus?strOrderId=10000&iPurchaseIntegrationAmount=4400&strMemberId=651c5888e17c4fe08bd27551d7852fed&iMemberIntegration=10000&strMemberCardNum=65638664&strMemberName=Tom
	public String integrationPayOverEditStatus(HttpServletRequest request,HttpServletResponse response)
	{
		String strOrderId=request.getParameter("strOrderId");	//订单号
		int iPayStandard=0;	//支付标准备 0 会员价支付  1 原价支付  以后可能会修改
		int iPayType=0; //订单支付方式:0积分兑换 1 微信支付 2 支付宝支付 3 线下现金支付
		int iPurchaseIntegrationAmount=0;	//购买商品或服务所需积分数量
		int iMemberIntegration=0;	//会员卡积分余额
		String strMemberIntegration=request.getParameter("iMemberIntegration");
		String strMemberId=request.getParameter("strMemberId");
		String strPurchaseIntegrationAmount=request.getParameter("iPurchaseIntegrationAmount");
		String strMemberCardNum=request.getParameter("strMemberCardNum");
		String strMemberName=request.getParameter("strMemberName");

		String strPayTime=DateTool.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS);
		String strLastAccessedTime=DateTool.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS);
		if(ValidateTool.isEmptyStr(strMemberCardNum))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"会员卡纺号不能为空",null);
		if(ValidateTool.isEmptyStr(strMemberName))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"会员姓名不能为空",null);
		
		if(ValidateTool.isEmptyStr(strMemberIntegration))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"会员卡积分余额不能为空",null);
		if(isNumber(strMemberIntegration))
			iMemberIntegration=Integer.parseInt(strMemberIntegration);
		
		if(ValidateTool.isEmptyStr(strMemberId))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"会员编号不能为空",null);
		
		if(ValidateTool.isEmptyStr(strOrderId))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"订单 号不能为空",null);
		
		if(ValidateTool.isEmptyStr(strPurchaseIntegrationAmount))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"所需积分数量不能为空",null);
		
		if(isNumber(strPurchaseIntegrationAmount))
			iPurchaseIntegrationAmount=Integer.parseInt(strPurchaseIntegrationAmount);
		else
			return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"所需积分数量格式错误",null);
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
		Map<String,Object> orderStatusMap=new HashMap<String,Object>();
		orderStatusMap.put("strMemberId",strMemberId);
		orderStatusMap.put("strMemberCardNum",strMemberCardNum);
		orderStatusMap.put("strMemberName",strMemberName);
		orderStatusMap.put("strOrderId",strOrderId);
		orderStatusMap.put("iPayType",iPayType);
		orderStatusMap.put("iPayStandard",iPayStandard);
		orderStatusMap.put("strPayTime",strPayTime);
		orderStatusMap.put("iStatus",1);
		orderStatusMap.put("strLastAccessedTime",strLastAccessedTime);
		orderStatusMap.put("iPurchaseIntegrationAmount",iPurchaseIntegrationAmount);
		orderStatusMap.put("iMemberIntegration",iMemberIntegration);

		IntegralModRecord integrationChangeEntity=new IntegralModRecord();
		integrationChangeEntity.setStrRecordId(DataTool.getUUID());
		integrationChangeEntity.setStrMemberId(strMemberId);
		integrationChangeEntity.setStrMemberCardNum(strMemberCardNum);
		integrationChangeEntity.setStrMemberName(strMemberName);
		integrationChangeEntity.setiIntegralNum(-iPurchaseIntegrationAmount);
		integrationChangeEntity.setStrEmployId(strEmployeeId);
		integrationChangeEntity.setStrEmployLoginName(strEmployeeName);
		integrationChangeEntity.setStrEmployName(strEmployeeRealName);
		integrationChangeEntity.setStrInsertTime(strLastAccessedTime);
		integrationChangeEntity.setStrDesc("会员积分支付订单(单号:"+strOrderId+")");
		
		try{
			return cashierDeskService.integrationPayOverEditStatus(orderStatusMap,integrationChangeEntity);
		}catch(Exception e)
		{
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
		}
		
	}
	
	
	//会员卡余额支付---查询
	@ResponseBody
	@RequestMapping("payWithMemberCardCash")
	//localhost:8083/admin/biz/CashierDesk/payWithMemberCardCash?strOrderId=30000
	public String payWithMemberCardCash(HttpServletRequest request,HttpServletResponse response)
	{
		String strOrderId=request.getParameter("strOrderId");
		if(ValidateTool.isEmptyStr(strOrderId))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"订单 号不能为空",null);
		try{
			return cashierDeskService.payWithMemberCardCash(strOrderId) ;
			}catch(Exception e)
			{
				e.printStackTrace();
				return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
			}
		
	}
	
	//会员卡余额支付完毕,修改订单状态及会员信息
	@ResponseBody
	@RequestMapping("cardCashPayOverEditStatus")
	//localhost:8083/admin/biz/CashierDesk/cardCashPayOverEditStatus?strOrderId=30000&dbalance=20000&dTotalCashAmount=157.16&strMemberId=651c5888e17c4fe08bd27551d7852fed
	public String cardCashPayOverEditStatus(HttpServletRequest request,HttpServletResponse response)
	{
		String strOrderId=request.getParameter("strOrderId");	//订单号
		String strMemberId=request.getParameter("strMemberId");
		String strBalance=request.getParameter("dbalance");	//会员卡余额
		String strTotalCashAmount=request.getParameter("dTotalCashAmount");//订单总金额
		
		int iPayStandard=0;	//支付标准备 0 会员价支付  1 原价支付  以后可能会修改
		int iPayType=4; //订单支付方式:0积分兑换 1 微信支付 2 支付宝支付 3 线下现金支付,4会员卡余额支付
		
		BigDecimal bgRestAmount;//

		String strPayTime=DateTool.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS);
		String strLastAccessedTime=DateTool.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS);

		if(ValidateTool.isEmptyStr(strOrderId))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"订单 号不能为空",null);
		
		if(ValidateTool.isEmptyStr(strMemberId))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"会员ID不能为空",null);
		
		if(ValidateTool.isEmptyStr(strBalance))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"会员卡余额不能为空",null);
		
		if(ValidateTool.isEmptyStr(strTotalCashAmount))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"订单 金额不能为空",null);
		bgRestAmount=new BigDecimal(strBalance).subtract(new BigDecimal(strTotalCashAmount));
		if(bgRestAmount.compareTo(new BigDecimal("0"))==-1)
			return DataTool.constructResponse(ResultCode.UNKNOW_ERROR,"储值余额不足，请充值或采用其它方式付款",null);
		Map<String,Object> orderStatusMap=new HashMap<String,Object>();
		orderStatusMap.put("iStatus",1);
		orderStatusMap.put("iPayStandard",iPayStandard);
		orderStatusMap.put("strPayTime",strPayTime);
		orderStatusMap.put("iPayType",iPayType);
		orderStatusMap.put("strLastAccessedTime",strLastAccessedTime);
		orderStatusMap.put("strOrderId",strOrderId);
		
		orderStatusMap.put("strMemberId",strMemberId);
		orderStatusMap.put("bgRestAmount",bgRestAmount);
		try{
			return cashierDeskService.cardCashPayOverEditStatus(orderStatusMap);
		}catch(Exception e)
		{
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
		}
	}
	

	//现金支付--查询
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
	
	//现金支付完毕，修改订单状态 
	@ResponseBody
	@RequestMapping("cashPayOverEditStatus")
	//localhost:8083/admin/biz/CashierDesk/cashPayOverEditStatus?strOrderId=20000
	public String cashPayOverEditStatus(HttpServletRequest request,HttpServletResponse response)
	{
		String strOrderId=request.getParameter("strOrderId");	//订单号
		int iPayStandard=0;	//支付标准备 0 会员价支付  1 原价支付  以后可能会修改
		int iPayType=3; //订单支付方式:0积分兑换 1 微信支付 2 支付宝支付 3 线下现金支付

		String strPayTime=DateTool.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS);
		String strLastAccessedTime=DateTool.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS);

		if(ValidateTool.isEmptyStr(strOrderId))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"订单 号不能为空",null);
		
		Map<String,Object> orderStatusMap=new HashMap<String,Object>();
		orderStatusMap.put("iStatus",1);
		orderStatusMap.put("iPayStandard",iPayStandard);
		orderStatusMap.put("strPayTime",strPayTime);
		orderStatusMap.put("iPayType",iPayType);
		orderStatusMap.put("strLastAccessedTime",strLastAccessedTime);
		orderStatusMap.put("strOrderId",strOrderId);
		try{
				return cashierDeskService.cashPayOverEditStatus(orderStatusMap);
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

	
	
	//支付宝支付
	@ResponseBody
	@RequestMapping("payWithAliPayment")
	//localhost:8083/admin/biz/CashierDesk/payWithAliPayment?strOrderId=oeojgjg
/*	
	public String payWithAliPayment(HttpServletRequest request, HttpServletResponse response)
	{
		//读取订单信息
		String strOrderId=request.getParameter("strOrderId");
		if(ValidateTool.isEmptyStr(strOrderId))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"订单 号不能为空",null);
		Map<String,Object> orderMsgMap=new HashMap<String,Object>();
		try{
			orderMsgMap=cashierDeskService.payWithAliPayment(strOrderId) ;
		}catch(Exception e)
		{
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
		}
			
		//取出订单信息
		PurchaseOrderEntity purchaseOrderEntity=(PurchaseOrderEntity)orderMsgMap.get("purchaseOrderEntity");
		BigDecimal preferentialCashTotalAmount=purchaseOrderEntity.getdPreferentialCashTotalAmount();	//订单总金额
	// (必填) 商户网站订单系统中唯一订单号，64个字符以内，只能包含字母、数字、下划线，
        // 需保证商户系统端不能重复，建议通过数据库sequence生成，
        String outTradeNo = strOrderId;

        // (必填) 订单标题，粗略描述用户的支付目的。如“xxx品牌xxx门店当面付扫码消费”
        String subject = "门店当面付扫码消费";

        // (必填) 订单总金额，单位为元，不能超过1亿元
        // 如果同时传入了【打折金额】,【不可打折金额】,【订单总金额】三者,则必须满足如下条件:【订单总金额】=【打折金额】+【不可打折金额】
        String totalAmount = String.valueOf(preferentialCashTotalAmount);

        // (可选) 订单不可打折金额，可以配合商家平台配置折扣活动，如果酒水不参与打折，则将对应金额填写至此字段
        // 如果该值未传入,但传入了【订单总金额】,【打折金额】,则该值默认为【订单总金额】-【打折金额】
        String undiscountableAmount = "0";

        // 卖家支付宝账号ID，用于支持一个签约账号下支持打款到不同的收款账号，(打款到sellerId对应的支付宝账号)
        // 如果该字段为空，则默认为与支付宝签约的商户的PID，也就是appid对应的PID
        String sellerId = "2088102170146884";

        // 订单描述，可以对交易或商品进行一个详细地描述，比如填写"购买商品2件共15.00元"
        String body = "this is a test";

        // 商户操作员编号，添加此参数可以为商户操作员做销售统计
        String operatorId = "test";

        // (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持
        String storeId = "2088102170146884";

        // 业务扩展参数，目前可添加由支付宝分配的系统商编号(通过setSysServiceProviderId方法)，详情请咨询支付宝技术支持
        ExtendParams extendParams = new ExtendParams();
        extendParams.setSysServiceProviderId("2088100200300400500");

        // 支付超时，定义为120分钟
        String timeoutExpress = "120m";

        // 商品明细列表，需填写购买商品详细信息，
       // List<GoodsDetail> goodsDetailList = new ArrayList<GoodsDetail>();
        // 创建一个商品信息，参数含义分别为商品id（使用国标）、名称、单价（单位为分）、数量，如果需要添加商品类别，详见GoodsDetail
       // GoodsDetail goods1 = GoodsDetail.newInstance("goods_id001", "xxx小面包", 1000, 1);
        // 创建好一个商品后添加至商品明细列表
      //  goodsDetailList.add(goods1);

        // 继续创建并添加第一条商品信息，用户购买的产品为“黑人牙刷”，单价为5.00元，购买了两件
      //  GoodsDetail goods2 = GoodsDetail.newInstance("goods_id002", "xxx牙刷", 500, 2);
      //  goodsDetailList.add(goods2);

        // 创建扫码支付请求builder，设置请求参数
        AlipayTradePrecreateRequestBuilder builder = new AlipayTradePrecreateRequestBuilder()
            .setSubject(subject)
            .setTotalAmount(totalAmount)
            .setOutTradeNo(outTradeNo)
            .setUndiscountableAmount(undiscountableAmount)
            .setSellerId(sellerId)
            .setBody(body)
            .setOperatorId(operatorId)
            .setStoreId(storeId)
            .setExtendParams(extendParams)
            .setTimeoutExpress(timeoutExpress)
            .setNotifyUrl("http://localhost:8083/admin/biz/CashierDesk/payNotify");//支付宝服务器主动通知商户服务器里指定的页面http路径,根据需要设置
            //.setGoodsDetailList(goodsDetailList);

        AlipayF2FPrecreateResult result = tradeService.tradePrecreate(builder);
        switch (result.getTradeStatus()) {
            case SUCCESS:
                log.info("支付宝预下单成功: )");

                AlipayTradePrecreateResponse alipayResponse = result.getResponse();
                dumpResponse(alipayResponse);

                // 需要修改为运行机器上的路径
               // String filePath = String.format("/Users/sudo/Desktop/qr-%s.png",
                //String filePath = String.format("E:/aliPay_Code/qr-%s.png", alipayResponse.getOutTradeNo());
                //log.info("filePath:" + filePath);
               // ZxingUtils.getQRCodeImge(alipayResponse.getQrCode(), 256, filePath);
                
			try
			{
				String qrcode=QRCodeTool.getQRCode(alipayResponse.getQrCode());
				System.out.println("qrcode+++++++=>"+qrcode+"<<<<<<<<<<<<<<<");
				orderMsgMap.put("qrcode",qrcode) ;
			} catch (WriterException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				 return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"支付二维码生产错误",null);
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				 return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"支付二维码生产错误",null);
			} 
                break;

            case FAILED:
                log.error("支付宝预下单失败!!!");
                break;

            case UNKNOWN:
                log.error("系统异常，预下单状态未知!!!");
                break;

            default:
                log.error("不支持的交易状态，交易返回异常!!!");
                break;
        }		
        
        return DataTool.constructResponseNoHtmlEscaping(ResultCode.OK,"查询成功",orderMsgMap);
		
    }		
	
	*/
	

	// 简单打印应答
/*
    private void dumpResponse(AlipayResponse response) {
        if (response != null) {
            log.info(String.format("code:%s, msg:%s", response.getCode(), response.getMsg()));
            if (StringUtils.isNotEmpty(response.getSubCode())) {
                log.info(String.format("subCode:%s, subMsg:%s", response.getSubCode(),
                    response.getSubMsg()));
            }
            log.info("body:" + response.getBody());
        }
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
