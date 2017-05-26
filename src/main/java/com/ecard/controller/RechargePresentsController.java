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
import com.ecard.entity.EmployeeEntity;
import com.ecard.entity.RechargePresentsStoredValueEntity;
import com.ecard.entity.RechargePresentsVoucherEntity;
import com.ecard.service.RechargePresentsService;
import com.ecard.util.WebSessionUtil;

@Controller
@RequestMapping("/admin/RechargePresentsSetting")
public class RechargePresentsController {
	@Autowired
	private RechargePresentsService rechargePresentsService;
	@Autowired
	private WebSessionUtil webSessionUtil;
	//批量新增 充值赠送抵用券信息
	@ResponseBody
	@RequestMapping("batchInsertRechargePresentsVoucher")
	//http://localhost:8083/admin/RechargePresentsSetting/batchInsertRechargePresentsVoucher?strRechargePresentsVoucherId=123456,7895213&strVoucherTicketId=1,3&strActivityId=1,3&dPerTimeRechargeAmount=100,333&dMoreRechargeAmount=100,333&iTotalNum=50,33&strValidateBeginTime=2017/5/26,2017/4/1&strValidateEndTime=2017/5/31,2017/7/30&iEnabled=1,0
	public String batchInsertRechargePresentsVoucher(HttpServletResponse response,HttpServletRequest request)
	{
		//以下取得的字符串格式都为：字符串1,字符串2
		int[] iTotalNumArray;
		int[] iRestNumArray;
		int[] iEnabledArray;
		int arrayLength;
		
		double dPerTimeRechargeAmount;
		double dMoreRechargeAmount;
		
		BigDecimal[] bgPerTimeRechargeAmountArray;
		BigDecimal[] bgMoreRechargeAmountArray;
		String strRechargePresentsVoucherId;
		String[] strVoucherTicketIdArray;
		String[] strActivityIdArray;
		String[] strValidateBeginTimeArray;
		String[] strValidateEndTimeArray;
		String[] strPerTimeRechargeAmountArray;
		String[] strMoreRechargeAmountArray;
		String[] strTotalNumArray;
		String[] strEnabledArray;
		String strVoucherTicketId=request.getParameter("strVoucherTicketId");
		String strActivityId=request.getParameter("strActivityId");
		String strPerTimeRechargeAmount=request.getParameter("dPerTimeRechargeAmount");
		String strMoreRechargeAmount=request.getParameter("dMoreRechargeAmount");
		String strTotalNum=request.getParameter("iTotalNum");
		String strValidateBeginTime=request.getParameter("strValidateBeginTime");
		String strValidateEndTime=request.getParameter("strValidateEndTime");
		String strEnabled=request.getParameter("iEnabled");
		if(ValidateTool.isEmptyStr(strVoucherTicketId))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"抵用券ID不能为空",null);
		if(ValidateTool.isEmptyStr(strActivityId))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"活动ID不能为空",null);
		if(ValidateTool.isEmptyStr(strPerTimeRechargeAmount))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"充值金额不能为空",null);
		if(ValidateTool.isEmptyStr(strMoreRechargeAmount))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"每多充值金额字段不能为空",null);
		if(ValidateTool.isEmptyStr(strTotalNum))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"总张数不能为空",null);
		if(ValidateTool.isEmptyStr(strValidateBeginTime))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"有效期开始时间不能为空",null);
		if(ValidateTool.isEmptyStr(strValidateEndTime))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"有效期结束时间不能为空",null);
		if(ValidateTool.isEmptyStr(strEnabled))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"启用状态不能为空",null);
		
		//取出各个字段值
		strVoucherTicketIdArray=strVoucherTicketId.split(",");
		strActivityIdArray=strActivityId.split(",");
		strPerTimeRechargeAmountArray=strPerTimeRechargeAmount.split(",");
		bgPerTimeRechargeAmountArray=new BigDecimal[strPerTimeRechargeAmountArray.length];
		for(int i=0;i<strPerTimeRechargeAmountArray.length;i++)
		{
			if(!isNumber(strPerTimeRechargeAmountArray[i]))
				return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"充值金额格式错误",null);
			dPerTimeRechargeAmount=Double.parseDouble(strPerTimeRechargeAmountArray[i]);
			bgPerTimeRechargeAmountArray[i]=new BigDecimal(dPerTimeRechargeAmount);
			
		}
		strMoreRechargeAmountArray=strMoreRechargeAmount.split(",");
		bgMoreRechargeAmountArray=new BigDecimal[strMoreRechargeAmountArray.length];
		for(int i=0;i<strMoreRechargeAmountArray.length;i++)
		{
			if(!isNumber(strMoreRechargeAmountArray[i]))
				return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"每多充值金额字段格式错误",null);
			dMoreRechargeAmount=Double.parseDouble(strMoreRechargeAmountArray[i]);
			bgMoreRechargeAmountArray[i]=new BigDecimal(dMoreRechargeAmount);
		}
		
		strTotalNumArray=strTotalNum.split(",");
		iTotalNumArray=new int[strTotalNumArray.length];
		iRestNumArray=new int[strTotalNumArray.length];
		for(int i=0;i<strTotalNumArray.length;i++)
		{
			if(!isNumber(strTotalNumArray[i]))
				return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"发放张数格式错误",null);
			iTotalNumArray[i]=Integer.parseInt(strTotalNumArray[i]);
			//默认新增时剩余张数=总张数
			iRestNumArray[i]=iTotalNumArray[i];
		}
		strValidateBeginTimeArray=strValidateBeginTime.split(",");
		strValidateEndTimeArray=strValidateEndTime.split(",");
		
		strEnabledArray=strEnabled.split(",");
		iEnabledArray=new int[strEnabledArray.length];
		
		//检测各个数组长度是否一致，不一致则报错
		arrayLength=strValidateEndTimeArray.length;
		if(arrayLength!=strActivityIdArray.length||arrayLength!=strValidateBeginTimeArray.length||arrayLength!=strEnabledArray.length||arrayLength!=strTotalNumArray.length||arrayLength!=strMoreRechargeAmountArray.length||arrayLength!=strPerTimeRechargeAmountArray.length||arrayLength!=strVoucherTicketIdArray.length)
			return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"参数值有遗漏",null);
		//标准化有效期
		for(int i=0;i<arrayLength;i++)
			{
			strValidateBeginTimeArray[i]=DateTool.StringToString(strValidateBeginTimeArray[i],DateStyle.YYYY_MM_DD);
			strValidateEndTimeArray[i]=DateTool.StringToString(strValidateEndTimeArray[i],DateStyle.YYYY_MM_DD);
			}
		for(int i=0;i<strEnabledArray.length;i++)
		{
			if(!isNumber(strEnabledArray[i]))
				return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"启用状态格式错误",null);
			iEnabledArray[i]=Integer.parseInt(strEnabledArray[i]);
		}
		
		strValidateBeginTime=DateTool.StringToString(strValidateBeginTime,DateStyle.YYYY_MM_DD);
		strValidateEndTime=DateTool.StringToString(strValidateEndTime,DateStyle.YYYY_MM_DD);
		
		String strCreationTime=DateTool.DateToString(new Date(),DateStyle.YYYY_MM_DD_HH_MM);
		String strLastAccessedTime=DateTool.DateToString(new Date(),DateStyle.YYYY_MM_DD_HH_MM);
	/*
		EmployeeEntity employeeEntity = null;
		try {
			employeeEntity=(EmployeeEntity)webSessionUtil.getWebSession(request, response).getAttribute("employeeEntity");
		} catch (Exception e) {
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
		}
		if (employeeEntity==null) {
			return DataTool.constructResponse(ResultCode.NO_DATA, "操作员不存在", null);
		}
		//取得登录员工信息，并增加修改时间记录
		String strEmployeeId=employeeEntity.getStrEmployeeid();
		String strEmployeeName=employeeEntity.getStrLoginname();
		String strEmployeeRealName=employeeEntity.getStrRealname();
	*/
		//以下3个为测试用数据
		String strEmployeeId=DataTool.getUUID();
		String strEmployeeName="admin";
		String strEmployeeRealName="david li";
		//数据写入实体
		List<RechargePresentsVoucherEntity> listRechargePresentsVoucherEntity=new ArrayList<RechargePresentsVoucherEntity>();
		for(int i=0;i<arrayLength;i++)
		{
			RechargePresentsVoucherEntity rechargePresentsVoucherEntity=new RechargePresentsVoucherEntity();
			strRechargePresentsVoucherId=DataTool.getUUID();
			rechargePresentsVoucherEntity.setStrRechargePresentsVoucherId(strRechargePresentsVoucherId);
			rechargePresentsVoucherEntity.setStrVoucherTicketId(strVoucherTicketIdArray[i]);
			rechargePresentsVoucherEntity.setStrActivityId(strActivityIdArray[i]);
			rechargePresentsVoucherEntity.setdPerTimeRechargeAmount(bgPerTimeRechargeAmountArray[i]);
			rechargePresentsVoucherEntity.setdMoreRechargeAmount(bgMoreRechargeAmountArray[i]);
			rechargePresentsVoucherEntity.setiTotalNum(iTotalNumArray[i]);
			rechargePresentsVoucherEntity.setiRestNum(iRestNumArray[i]);
			rechargePresentsVoucherEntity.setStrValidateBeginTime(strValidateBeginTimeArray[i]);
			rechargePresentsVoucherEntity.setStrValidateEndTime(strValidateEndTimeArray[i]);
			rechargePresentsVoucherEntity.setiEnabled(iEnabledArray[i]);
			rechargePresentsVoucherEntity.setStrEmployeeId(strEmployeeId);
			rechargePresentsVoucherEntity.setStrEmployeeName(strEmployeeName);
			rechargePresentsVoucherEntity.setStrEmployeeRealName(strEmployeeRealName);
			rechargePresentsVoucherEntity.setStrCreationTime(strCreationTime);
			rechargePresentsVoucherEntity.setStrLastAccessedTime(strLastAccessedTime);
			listRechargePresentsVoucherEntity.add(rechargePresentsVoucherEntity);
		}
		
		try{
			return rechargePresentsService.batchInsertRechargePresentsVoucher(listRechargePresentsVoucherEntity);
			
		}catch(Exception e)
		{
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
		}
		
	}
	
	
	
		//批量更新 充值赠送抵用券信息
		@ResponseBody
		@RequestMapping("batchUpdateRechargePresentsVoucher")
		//http://localhost:8083/admin/RechargePresentsSetting/batchUpdateRechargePresentsVoucher?strRechargePresentsVoucherId=6ce848a2d96e4c9f96f99a490de014d4,8f19a79750ce4ca4ba9a78d25a622fe4&strVoucherTicketId=1,3&strActivityId=1,3&dPerTimeRechargeAmount=100,333&dMoreRechargeAmount=50,50&iTotalNum=150,150&strValidateBeginTime=2017/5/26,2017/5/26&strValidateEndTime=2017/7/31,2017/7/31&iEnabled=1,1
		public String batchUpdateRechargePresentsVoucher(HttpServletResponse response,HttpServletRequest request)
		{
			//以下取得的字符串格式都为：字符串1,字符串2
			int[] iTotalNumArray;
			int[] iEnabledArray;
			int arrayLength;
			
			double dPerTimeRechargeAmount;
			double dMoreRechargeAmount;
			
			BigDecimal[] bgPerTimeRechargeAmountArray;
			BigDecimal[] bgMoreRechargeAmountArray;
			String[] strRechargePresentsVoucherIdArray;
			String[] strVoucherTicketIdArray;
			String[] strActivityIdArray;
			String[] strValidateBeginTimeArray;
			String[] strValidateEndTimeArray;
			String[] strPerTimeRechargeAmountArray;
			String[] strMoreRechargeAmountArray;
			String[] strTotalNumArray;
			String[] strEnabledArray;
			String strRechargePresentsVoucherId=request.getParameter("strRechargePresentsVoucherId");
			String strVoucherTicketId=request.getParameter("strVoucherTicketId");
			String strActivityId=request.getParameter("strActivityId");
			String strPerTimeRechargeAmount=request.getParameter("dPerTimeRechargeAmount");
			String strMoreRechargeAmount=request.getParameter("dMoreRechargeAmount");
			String strTotalNum=request.getParameter("iTotalNum");
			String strRestNum=request.getParameter("iRestNum");
			String strValidateBeginTime=request.getParameter("strValidateBeginTime");
			String strValidateEndTime=request.getParameter("strValidateEndTime");
			String strEnabled=request.getParameter("iEnabled");
			if(ValidateTool.isEmptyStr(strRechargePresentsVoucherId))
				return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"更新记录的主键不能为空",null);
			if(ValidateTool.isEmptyStr(strVoucherTicketId))
				return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"抵用券ID不能为空",null);
			if(ValidateTool.isEmptyStr(strActivityId))
				return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"活动ID不能为空",null);
			if(ValidateTool.isEmptyStr(strPerTimeRechargeAmount))
				return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"充值金额不能为空",null);
			if(ValidateTool.isEmptyStr(strMoreRechargeAmount))
				return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"每多充值金额字段不能为空",null);
			if(ValidateTool.isEmptyStr(strTotalNum))
				return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"总张数不能为空",null);
			if(ValidateTool.isEmptyStr(strValidateBeginTime))
				return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"有效期开始时间不能为空",null);
			if(ValidateTool.isEmptyStr(strValidateEndTime))
				return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"有效期结束时间不能为空",null);
			if(ValidateTool.isEmptyStr(strEnabled))
				return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"启用状态不能为空",null);
			
			//取出各个字段值
			strRechargePresentsVoucherIdArray=strRechargePresentsVoucherId.split(",");
			strVoucherTicketIdArray=strVoucherTicketId.split(",");
			strActivityIdArray=strActivityId.split(",");
			strPerTimeRechargeAmountArray=strPerTimeRechargeAmount.split(",");
			bgPerTimeRechargeAmountArray=new BigDecimal[strPerTimeRechargeAmountArray.length];
			for(int i=0;i<strPerTimeRechargeAmountArray.length;i++)
			{
				if(!isNumber(strPerTimeRechargeAmountArray[i]))
					return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"充值金额格式错误",null);
				dPerTimeRechargeAmount=Double.parseDouble(strPerTimeRechargeAmountArray[i]);
				bgPerTimeRechargeAmountArray[i]=new BigDecimal(dPerTimeRechargeAmount);
				
			}
			strMoreRechargeAmountArray=strMoreRechargeAmount.split(",");
			bgMoreRechargeAmountArray=new BigDecimal[strMoreRechargeAmountArray.length];
			for(int i=0;i<strMoreRechargeAmountArray.length;i++)
			{
				if(!isNumber(strMoreRechargeAmountArray[i]))
					return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"每多充值金额字段格式错误",null);
				dMoreRechargeAmount=Double.parseDouble(strMoreRechargeAmountArray[i]);
				bgMoreRechargeAmountArray[i]=new BigDecimal(dMoreRechargeAmount);
			}
			
			strTotalNumArray=strTotalNum.split(",");
			iTotalNumArray=new int[strTotalNumArray.length];
			for(int i=0;i<strTotalNumArray.length;i++)
			{
				if(!isNumber(strTotalNumArray[i]))
					return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"发放张数格式错误",null);
				iTotalNumArray[i]=Integer.parseInt(strTotalNumArray[i]);
			}
			strValidateBeginTimeArray=strValidateBeginTime.split(",");
			strValidateEndTimeArray=strValidateEndTime.split(",");
			
			strEnabledArray=strEnabled.split(",");
			iEnabledArray=new int[strEnabledArray.length];
			
			//检测各个数组长度是否一致，不一致则报错
			arrayLength=strValidateEndTimeArray.length;
			if(arrayLength!=strActivityIdArray.length||arrayLength!=strRechargePresentsVoucherIdArray.length||arrayLength!=strValidateBeginTimeArray.length||arrayLength!=strEnabledArray.length||arrayLength!=strTotalNumArray.length||arrayLength!=strMoreRechargeAmountArray.length||arrayLength!=strPerTimeRechargeAmountArray.length||arrayLength!=strVoucherTicketIdArray.length)
				return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"参数值有遗漏",null);
			//标准化有效期
			for(int i=0;i<arrayLength;i++)
				{
				strValidateBeginTimeArray[i]=DateTool.StringToString(strValidateBeginTimeArray[i],DateStyle.YYYY_MM_DD);
				strValidateEndTimeArray[i]=DateTool.StringToString(strValidateEndTimeArray[i],DateStyle.YYYY_MM_DD);
				}
			for(int i=0;i<strEnabledArray.length;i++)
			{
				if(!isNumber(strEnabledArray[i]))
					return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"启用状态格式错误",null);
				iEnabledArray[i]=Integer.parseInt(strEnabledArray[i]);
			}
			
			strValidateBeginTime=DateTool.StringToString(strValidateBeginTime,DateStyle.YYYY_MM_DD);
			strValidateEndTime=DateTool.StringToString(strValidateEndTime,DateStyle.YYYY_MM_DD);
			String strLastAccessedTime=DateTool.DateToString(new Date(),DateStyle.YYYY_MM_DD_HH_MM);
		/*
			EmployeeEntity employeeEntity = null;
			try {
				employeeEntity=(EmployeeEntity)webSessionUtil.getWebSession(request, response).getAttribute("employeeEntity");
			} catch (Exception e) {
				e.printStackTrace();
				return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
			}
			if (employeeEntity==null) {
				return DataTool.constructResponse(ResultCode.NO_DATA, "操作员不存在", null);
			}
			//取得登录员工信息，并增加修改时间记录
			String strEmployeeId=employeeEntity.getStrEmployeeid();
			String strEmployeeName=employeeEntity.getStrLoginname();
			String strEmployeeRealName=employeeEntity.getStrRealname();
		*/
			//以下3个为测试用数据
			String strEmployeeId=DataTool.getUUID();
			String strEmployeeName="admin";
			String strEmployeeRealName="david li";
			//数据写入实体
			List<RechargePresentsVoucherEntity> listRechargePresentsVoucherEntity=new ArrayList<RechargePresentsVoucherEntity>();
			for(int i=0;i<arrayLength;i++)
			{
				RechargePresentsVoucherEntity rechargePresentsVoucherEntity=new RechargePresentsVoucherEntity();
				rechargePresentsVoucherEntity.setStrRechargePresentsVoucherId(strRechargePresentsVoucherIdArray[i]);
				rechargePresentsVoucherEntity.setStrVoucherTicketId(strVoucherTicketIdArray[i]);
				rechargePresentsVoucherEntity.setStrActivityId(strActivityIdArray[i]);
				rechargePresentsVoucherEntity.setdPerTimeRechargeAmount(bgPerTimeRechargeAmountArray[i]);
				rechargePresentsVoucherEntity.setdMoreRechargeAmount(bgMoreRechargeAmountArray[i]);
				rechargePresentsVoucherEntity.setiTotalNum(iTotalNumArray[i]);
				rechargePresentsVoucherEntity.setStrValidateBeginTime(strValidateBeginTimeArray[i]);
				rechargePresentsVoucherEntity.setStrValidateEndTime(strValidateEndTimeArray[i]);
				rechargePresentsVoucherEntity.setiEnabled(iEnabledArray[i]);
				rechargePresentsVoucherEntity.setStrEmployeeId(strEmployeeId);
				rechargePresentsVoucherEntity.setStrEmployeeName(strEmployeeName);
				rechargePresentsVoucherEntity.setStrEmployeeRealName(strEmployeeRealName);
				rechargePresentsVoucherEntity.setStrLastAccessedTime(strLastAccessedTime);
				listRechargePresentsVoucherEntity.add(rechargePresentsVoucherEntity);
			}
			
			try{
				return rechargePresentsService.batchUpdateRechargePresentsVoucher(listRechargePresentsVoucherEntity);
				
			}catch(Exception e)
			{
				e.printStackTrace();
				return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
			}
			
	}
	
	
	/**
	 * 删除 充值赠送抵用券信息
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("deleteRechargePresentsVoucher")
	//http://localhost:8083/admin/RechargePresentsSetting/deleteRechargePresentsVoucher?strRechargePresentsVoucherId=8f19a79750ce4ca4ba9a78d25a622fe4
	public String deleteRechargePresentsVoucher(HttpServletRequest request,HttpServletResponse response)
	{
		/*
		EmployeeEntity employeeEntity = null;
		try {
			employeeEntity=(EmployeeEntity)webSessionUtil.getWebSession(request, response).getAttribute("employeeEntity");
		} catch (Exception e) {
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
		}
		if (employeeEntity==null) {
			return DataTool.constructResponse(ResultCode.NO_DATA, "操作员不存在", null);
		}
	*/
		String strRechargePresentsVoucherId=request.getParameter("strRechargePresentsVoucherId");
		if(ValidateTool.isEmptyStr(strRechargePresentsVoucherId))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"删除记录主键不能为空",null);
		try{
			return rechargePresentsService.deleteRechargePresentsVoucher(strRechargePresentsVoucherId);
		}catch(Exception e)
		{
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
		}
	}
	
	
	
	

	/**
	 * 查询 充值赠送抵用券信息
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("selectAllRechargePresentsVoucher")
	//http://localhost:8083/admin/RechargePresentsSetting/selectAllRechargePresentsVoucher
	public String selectAllRechargePresentsVoucher(HttpServletResponse response,HttpServletRequest request)
	{
		/*
		EmployeeEntity employeeEntity = null;
		try {
			employeeEntity=(EmployeeEntity)webSessionUtil.getWebSession(request, response).getAttribute("employeeEntity");
		} catch (Exception e) {
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
		}
		if (employeeEntity==null) {
			return DataTool.constructResponse(ResultCode.NO_DATA, "操作员不存在", null);
		}
		*/		
		List<RechargePresentsVoucherEntity> listRechargePresentsVoucherEntity=new ArrayList<RechargePresentsVoucherEntity>();
		try{
			listRechargePresentsVoucherEntity=rechargePresentsService.selectAllRechargePresentsVoucher();
			if(listRechargePresentsVoucherEntity==null||listRechargePresentsVoucherEntity.size()==0)
			return DataTool.constructResponse(ResultCode.NO_DATA,"暂时没有数据",null);
			Map<String,Object> resultMap=new HashMap<String,Object>();
			resultMap.put("listRechargePresentsVoucherEntity",listRechargePresentsVoucherEntity);
			return DataTool.constructResponse(ResultCode.OK,"查询成功",resultMap);
		}catch(Exception e)
		{
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
		}
	}
	
	
	/**
	 * 批量新增 充值赠送储值信息
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("batchInsertRechargePresentsStoredValue")
	//http://localhost:8083/admin/RechargePresentsSetting/batchInsertRechargePresentsStoredValue?&strActivityId=002,003&dRechargeAmount=150,150&dPresentsAmount=89,89&strValidateBeginTime=207/1/1,2017/2/2&strValidateEndTime=2010/12/12,2017/5/5&iEnabled=1,0
	public String batchInsertRechargePresentsStoredValue(HttpServletResponse response,HttpServletRequest request)
	{
		//参数格式：参数名=参数值1，参数值2
		int arrayLength;
		int[] iEnabledArray;
		
		String[] strActivityIdArray;
		String[] strValidateBeginTimeArray;
		String[] strValidateEndTimeArray;
		String[] strEnabledArray;
		String[] strRechargeAmountArray;
		String[] strPresentsAmountArray;
		
		BigDecimal[] bgRechargeAmountArray;
		BigDecimal[] bgPresentsAmountArray;
	
		String strActivityId=request.getParameter("strActivityId");
		String strRechargeAmount=request.getParameter("dRechargeAmount");
		String strPresentsAmount=request.getParameter("dPresentsAmount");
		String strValidateBeginTime=request.getParameter("strValidateBeginTime");
		String strValidateEndTime=request.getParameter("strValidateEndTime");
		String strEnabled=request.getParameter("iEnabled");
		
		if(ValidateTool.isEmptyStr(strActivityId))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"活动ID不能为空",null);
		strActivityIdArray=strActivityId.split(",");
		
		if(ValidateTool.isEmptyStr(strRechargeAmount))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"充值金额不能为空",null);
		strRechargeAmountArray=strRechargeAmount.split(",");
		arrayLength=strRechargeAmountArray.length;
		bgRechargeAmountArray=new BigDecimal[arrayLength];
		for(int i=0;i<arrayLength;i++)
			{
			if(!isNumber(strRechargeAmountArray[i]))
				return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"充值金额格式错误",null);
			bgRechargeAmountArray[i]=new BigDecimal(Double.parseDouble(strRechargeAmountArray[i]));
			}
		
		if(ValidateTool.isEmptyStr(strPresentsAmount))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"赠送金额不能为空",null);
		strPresentsAmountArray=strPresentsAmount.split(",");
		arrayLength=strPresentsAmountArray.length;
		bgPresentsAmountArray=new BigDecimal[arrayLength];
		for(int i=0;i<arrayLength;i++)
		{
			if(!isNumber(strPresentsAmountArray[i]))
				return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"赠送金额格式错误",null);
			bgPresentsAmountArray[i]=new BigDecimal(Double.parseDouble(strPresentsAmountArray[i]));
		}
		
		if(ValidateTool.isEmptyStr(strValidateBeginTime))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"有效期开始时间不能为空",null);
		strValidateBeginTimeArray=strValidateBeginTime.split(",");
		arrayLength=strValidateBeginTimeArray.length;
		for(int i=0;i<arrayLength;i++)
			strValidateBeginTimeArray[i]=DateTool.StringToString(strValidateBeginTimeArray[i], DateStyle.YYYY_MM_DD);
			
		if(ValidateTool.isEmptyStr(strValidateEndTime))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"有效期结束时间不能为空",null);
		strValidateEndTimeArray=strValidateEndTime.split(",");
		arrayLength=strValidateEndTimeArray.length;
		for(int i=0;i<arrayLength;i++)
			strValidateEndTimeArray[i]=DateTool.StringToString(strValidateEndTimeArray[i], DateStyle.YYYY_MM_DD);
			
		if(ValidateTool.isEmptyStr(strEnabled))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"启用状态不能为空",null);
		strEnabledArray=strEnabled.split(",");
		arrayLength=strEnabledArray.length;
		iEnabledArray=new int[arrayLength];
		for(int i=0;i<arrayLength;i++)
		{
			if(!isNumber(strEnabledArray[i]))
				return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"启用状态格式错误",null);
			iEnabledArray[i]=Integer.parseInt(strEnabledArray[i]);
		}
		
		//检测各个数组长度是否一致
		if(arrayLength!=strValidateEndTimeArray.length||arrayLength!=strValidateBeginTimeArray.length||arrayLength!=bgPresentsAmountArray.length||arrayLength!=bgRechargeAmountArray.length||arrayLength!=strActivityIdArray.length)
			return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"传递参数值有遗漏",null);
		
		String strCreationTime=DateTool.DateToString(new Date(),DateStyle.YYYY_MM_DD_HH_MM);
		String strLastAccessedTime=DateTool.DateToString(new Date(),DateStyle.YYYY_MM_DD_HH_MM);
	/*
		EmployeeEntity employeeEntity = null;
		try {
			employeeEntity=(EmployeeEntity)webSessionUtil.getWebSession(request, response).getAttribute("employeeEntity");
		} catch (Exception e) {
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
		}
		if (employeeEntity==null) {
			return DataTool.constructResponse(ResultCode.NO_DATA, "操作员不存在", null);
		}
		//取得登录员工信息，并增加修改时间记录
		String strEmployeeId=employeeEntity.getStrEmployeeid();
		String strEmployeeName=employeeEntity.getStrLoginname();
		String strEmployeeRealName=employeeEntity.getStrRealname();
	*/
		//以下3个为测试用数据
		String strEmployeeId=DataTool.getUUID();
		String strEmployeeName="admin";
		String strEmployeeRealName="david li";
		List<RechargePresentsStoredValueEntity> listRechargePresentsStoredValueEntity=new ArrayList<RechargePresentsStoredValueEntity>();
		for(int i=0;i<arrayLength;i++)
		{
			RechargePresentsStoredValueEntity rechargePresentsStoredValueEntity=new RechargePresentsStoredValueEntity();
			rechargePresentsStoredValueEntity.setStrPresentsStoredValueId(DataTool.getUUID());
			rechargePresentsStoredValueEntity.setStrActivityId(strActivityIdArray[i]);
			rechargePresentsStoredValueEntity.setdRechargeAmount(bgRechargeAmountArray[i]);
			rechargePresentsStoredValueEntity.setdPresentsAmount(bgPresentsAmountArray[i]);
			rechargePresentsStoredValueEntity.setStrValidateBeginTime(strValidateBeginTimeArray[i]);
			rechargePresentsStoredValueEntity.setStrValidateEndTime(strValidateEndTimeArray[i]);
			rechargePresentsStoredValueEntity.setiEnabled(iEnabledArray[i]);
			rechargePresentsStoredValueEntity.setStrEmployeeId(strEmployeeId);
			rechargePresentsStoredValueEntity.setStrEmployeeName(strEmployeeName);
			rechargePresentsStoredValueEntity.setStrEmployeeRealName(strEmployeeRealName);
			rechargePresentsStoredValueEntity.setStrCreationTime(strCreationTime);
			rechargePresentsStoredValueEntity.setStrLastAccessedTime(strLastAccessedTime);
			listRechargePresentsStoredValueEntity.add(rechargePresentsStoredValueEntity);
		}
		
		try{
			return rechargePresentsService.batchInsertRechargePresentsStoredValue(listRechargePresentsStoredValueEntity);
		}catch(Exception e)
		{
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
		}
		
		
	}
	
	/**
	 * 批量更新 充值赠送储值信息
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("batchUpdateRechargePresentsStoredValue")
	//http://localhost:8083/admin/RechargePresentsSetting/batchUpdateRechargePresentsStoredValue?strPresentsStoredValueId=b44f19abf0cd4d198b50a11114961b59,e821b72bf2a6401980bbacb7c29ebf11&strActivityId=005,005&dRechargeAmount=155,155&dPresentsAmount=85,85&strValidateBeginTime=207/1/5,2017/2/5&strValidateEndTime=2010/12/5,2017/5/5&iEnabled=1,1
	public String batchUpdateRechargePresentsStoredValue(HttpServletResponse response,HttpServletRequest request)
	{
		//参数格式：参数名=参数值1，参数值2
		int arrayLength;
		int[] iEnabledArray;
		
		String[] strPresentsStoredValueIdArray;
		String[] strActivityIdArray;
		String[] strValidateBeginTimeArray;
		String[] strValidateEndTimeArray;
		String[] strEnabledArray;
		String[] strRechargeAmountArray;
		String[] strPresentsAmountArray;
		
		BigDecimal[] bgRechargeAmountArray;
		BigDecimal[] bgPresentsAmountArray;
		
		String strPresentsStoredValueId=request.getParameter("strPresentsStoredValueId");
		String strActivityId=request.getParameter("strActivityId");
		String strRechargeAmount=request.getParameter("dRechargeAmount");
		String strPresentsAmount=request.getParameter("dPresentsAmount");
		String strValidateBeginTime=request.getParameter("strValidateBeginTime");
		String strValidateEndTime=request.getParameter("strValidateEndTime");
		String strEnabled=request.getParameter("iEnabled");
		
		if(ValidateTool.isEmptyStr(strPresentsStoredValueId))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"修改记录ID不能为空",null);
		strPresentsStoredValueIdArray=strPresentsStoredValueId.split(",");
		
		if(ValidateTool.isEmptyStr(strActivityId))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"活动ID不能为空",null);
		strActivityIdArray=strActivityId.split(",");
		
		if(ValidateTool.isEmptyStr(strRechargeAmount))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"充值金额不能为空",null);
		strRechargeAmountArray=strRechargeAmount.split(",");
		arrayLength=strRechargeAmountArray.length;
		bgRechargeAmountArray=new BigDecimal[arrayLength];
		for(int i=0;i<arrayLength;i++)
			{
			if(!isNumber(strRechargeAmountArray[i]))
				return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"充值金额格式错误",null);
			bgRechargeAmountArray[i]=new BigDecimal(Double.parseDouble(strRechargeAmountArray[i]));
			}
		
		if(ValidateTool.isEmptyStr(strPresentsAmount))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"赠送金额不能为空",null);
		strPresentsAmountArray=strPresentsAmount.split(",");
		arrayLength=strPresentsAmountArray.length;
		bgPresentsAmountArray=new BigDecimal[arrayLength];
		for(int i=0;i<arrayLength;i++)
		{
			if(!isNumber(strPresentsAmountArray[i]))
				return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"赠送金额格式错误",null);
			bgPresentsAmountArray[i]=new BigDecimal(Double.parseDouble(strPresentsAmountArray[i]));
		}
		
		if(ValidateTool.isEmptyStr(strValidateBeginTime))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"有效期开始时间不能为空",null);
		strValidateBeginTimeArray=strValidateBeginTime.split(",");
		arrayLength=strValidateBeginTimeArray.length;
		for(int i=0;i<arrayLength;i++)
			strValidateBeginTimeArray[i]=DateTool.StringToString(strValidateBeginTimeArray[i], DateStyle.YYYY_MM_DD);
			
		if(ValidateTool.isEmptyStr(strValidateEndTime))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"有效期结束时间不能为空",null);
		strValidateEndTimeArray=strValidateEndTime.split(",");
		arrayLength=strValidateEndTimeArray.length;
		for(int i=0;i<arrayLength;i++)
			strValidateEndTimeArray[i]=DateTool.StringToString(strValidateEndTimeArray[i], DateStyle.YYYY_MM_DD);
			
		if(ValidateTool.isEmptyStr(strEnabled))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"启用状态不能为空",null);
		strEnabledArray=strEnabled.split(",");
		arrayLength=strEnabledArray.length;
		iEnabledArray=new int[arrayLength];
		for(int i=0;i<arrayLength;i++)
		{
			if(!isNumber(strEnabledArray[i]))
				return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"启用状态格式错误",null);
			iEnabledArray[i]=Integer.parseInt(strEnabledArray[i]);
		}
		
		//检测各个数组长度是否一致
		if(arrayLength!=strPresentsStoredValueIdArray.length||arrayLength!=strValidateEndTimeArray.length||arrayLength!=strValidateBeginTimeArray.length||arrayLength!=bgPresentsAmountArray.length||arrayLength!=bgRechargeAmountArray.length||arrayLength!=strActivityIdArray.length)
			return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"传递参数值有遗漏",null);
		
		String strLastAccessedTime=DateTool.DateToString(new Date(),DateStyle.YYYY_MM_DD_HH_MM);
	/*
		EmployeeEntity employeeEntity = null;
		try {
			employeeEntity=(EmployeeEntity)webSessionUtil.getWebSession(request, response).getAttribute("employeeEntity");
		} catch (Exception e) {
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
		}
		if (employeeEntity==null) {
			return DataTool.constructResponse(ResultCode.NO_DATA, "操作员不存在", null);
		}
		//取得登录员工信息，并增加修改时间记录
		String strEmployeeId=employeeEntity.getStrEmployeeid();
		String strEmployeeName=employeeEntity.getStrLoginname();
		String strEmployeeRealName=employeeEntity.getStrRealname();
	*/
		//以下3个为测试用数据
		String strEmployeeId=DataTool.getUUID();
		String strEmployeeName="admin";
		String strEmployeeRealName="david li";
		List<RechargePresentsStoredValueEntity> listRechargePresentsStoredValueEntity=new ArrayList<RechargePresentsStoredValueEntity>();
		for(int i=0;i<arrayLength;i++)
		{
			RechargePresentsStoredValueEntity rechargePresentsStoredValueEntity=new RechargePresentsStoredValueEntity();
			rechargePresentsStoredValueEntity.setStrPresentsStoredValueId(strPresentsStoredValueIdArray[i]);
			rechargePresentsStoredValueEntity.setStrActivityId(strActivityIdArray[i]);
			rechargePresentsStoredValueEntity.setdRechargeAmount(bgRechargeAmountArray[i]);
			rechargePresentsStoredValueEntity.setdPresentsAmount(bgPresentsAmountArray[i]);
			rechargePresentsStoredValueEntity.setStrValidateBeginTime(strValidateBeginTimeArray[i]);
			rechargePresentsStoredValueEntity.setStrValidateEndTime(strValidateEndTimeArray[i]);
			rechargePresentsStoredValueEntity.setiEnabled(iEnabledArray[i]);
			rechargePresentsStoredValueEntity.setStrEmployeeId(strEmployeeId);
			rechargePresentsStoredValueEntity.setStrEmployeeName(strEmployeeName);
			rechargePresentsStoredValueEntity.setStrEmployeeRealName(strEmployeeRealName);
			rechargePresentsStoredValueEntity.setStrLastAccessedTime(strLastAccessedTime);
			listRechargePresentsStoredValueEntity.add(rechargePresentsStoredValueEntity);
		}
		
		try{
			return rechargePresentsService.batchUpdateRechargePresentsStoredValue(listRechargePresentsStoredValueEntity);
		}catch(Exception e)
		{
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
		}
		
		
	}
	

	/**
	 * 删除 充值赠送储值信息
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("deleteRechargePresentsStoredValue")
	//http://localhost:8083/admin/RechargePresentsSetting/deleteRechargePresentsStoredValue?strPresentsStoredValueId=e821b72bf2a6401980bbacb7c29ebf11
    public String deleteRechargePresentsStoredValue(HttpServletResponse response,HttpServletRequest request)
    {
		/*
		EmployeeEntity employeeEntity = null;
		try {
			employeeEntity=(EmployeeEntity)webSessionUtil.getWebSession(request, response).getAttribute("employeeEntity");
		} catch (Exception e) {
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
		}
		if (employeeEntity==null) {
			return DataTool.constructResponse(ResultCode.NO_DATA, "操作员不存在", null);
		}
		*/		
		String strPresentsStoredValueId=request.getParameter("strPresentsStoredValueId");
		if(ValidateTool.isEmptyStr(strPresentsStoredValueId))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"删除关键字不能为空",null);
		try{
			return rechargePresentsService.deleteRechargePresentsStoredValue(strPresentsStoredValueId);
		}catch(Exception e)
		{
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
		}
    }
	
	

	/**
	 * 查询 充值赠送储值信息
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("selectAllRechargePresentsStoredValue")
	//http://localhost:8083/admin/RechargePresentsSetting/selectAllRechargePresentsStoredValue
	public String selectAllRechargePresentsStoredValue(HttpServletRequest request,HttpServletResponse response)
	{
		/*
		EmployeeEntity employeeEntity = null;
		try {
			employeeEntity=(EmployeeEntity)webSessionUtil.getWebSession(request, response).getAttribute("employeeEntity");
		} catch (Exception e) {
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
		}
		if (employeeEntity==null) {
			return DataTool.constructResponse(ResultCode.NO_DATA, "操作员不存在", null);
		}
		*/	
		try{
			List<RechargePresentsStoredValueEntity> listRechargePresentsStoredValueEntity=rechargePresentsService.selectAllRechargePresentsStoredValue();
			if(listRechargePresentsStoredValueEntity==null||listRechargePresentsStoredValueEntity.size()==0)
				return DataTool.constructResponse(ResultCode.NO_DATA,"暂无数据",null);
			Map<String,Object> resultMap=new HashMap<String,Object>();
			resultMap.put("listRechargePresentsStoredValueEntity", listRechargePresentsStoredValueEntity);
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
