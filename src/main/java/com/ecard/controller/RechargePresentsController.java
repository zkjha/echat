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
import com.ecard.entity.RechargePresentsActivityEntity;
import com.ecard.entity.RechargePresentsIntegrationEntity;
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
	//localhost:8083/admin/RechargePresentsSetting/batchInsertRechargePresentsVoucher?strBasePresentsVoucherTicketId=抵用券名1&strMorePresentsVoucherTicketId=抵用券名2&strActivityId=a42c801d8a7b4daa86653bacf88273a5&dMinimumRechargeAmount=100&iMinimumPresentsVoucherNumber=1&dMoreRechargeAmount=50&iMorePresentsVoucherNumber=1&iEnabled=1
	public String batchInsertRechargePresentsVoucher(HttpServletResponse response,HttpServletRequest request)
	{
		//以下取得的字符串格式都为：字符串1,字符串2
		
		int arrayLength;
		int[] iEnabledArray;
		int[] iMinimumPresentsVoucherNumberArray;
		int[] iMorePresentsVoucherNumberArray;

		double dMoreRechargeAmount;
		double dMinimumRechargeAmount;
		
		BigDecimal[] dMoreRechargeAmountArray;
		BigDecimal[] dMinimumRechargeAmountArray;
		
		
		String[] strMinimumRechargeAmountArray;
		String[] strBasePresentsVoucherTicketIdArray;
		String[] strMorePresentsVoucherTicketIdArray;
		String[] strActivityIdArray;
		String[] strMoreRechargeAmountArray;
		String[] strEnabledArray;
		String[] strMinimumPresentsVoucherNumberArray;
		String[] strMorePresentsVoucherNumberArray;
		
		String strRechargePresentsVoucherId;
		String strBasePresentsVoucherTicketId=request.getParameter("strBasePresentsVoucherTicketId");
		String strMorePresentsVoucherTicketId=request.getParameter("strMorePresentsVoucherTicketId");
		String strActivityId=request.getParameter("strActivityId");
		String strMinimumRechargeAmount=request.getParameter("dMinimumRechargeAmount");
		String strMinimumPresentsVoucherNumber=request.getParameter("iMinimumPresentsVoucherNumber");
		String strMoreRechargeAmount=request.getParameter("dMoreRechargeAmount");
		String strMorePresentsVoucherNumber=request.getParameter("iMorePresentsVoucherNumber");
		String strEnabled=request.getParameter("iEnabled");
		if(ValidateTool.isEmptyStr(strBasePresentsVoucherTicketId))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"抵用券ID不能为空",null);
		
		if(ValidateTool.isEmptyStr(strMorePresentsVoucherTicketId))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"抵用券ID不能为空",null);
		
		if(ValidateTool.isEmptyStr(strActivityId))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"活动ID不能为空",null);
		
		if(ValidateTool.isEmptyStr(strMinimumRechargeAmount))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"最低充值金额不能为空",null);
		
		if(ValidateTool.isEmptyStr(strMinimumPresentsVoucherNumber))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"赠送数量不能为空",null);
		
		if(ValidateTool.isEmptyStr(strMoreRechargeAmount))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"金充值金额不能为空",null);
	
		if(ValidateTool.isEmptyStr(strMorePresentsVoucherNumber))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"多赠送数量不能为空",null);
		
		if(ValidateTool.isEmptyStr(strEnabled))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"启用状态不能为空",null);
		
		/*  暂不使用该属性
		if(ValidateTool.isEmptyStr(strValidateBeginTime))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"有效期开始时间不能为空",null);
		if(ValidateTool.isEmptyStr(strValidateEndTime))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"有效期结束时间不能为空",null);
			*/
		
		
		//取出各个字段值
		strBasePresentsVoucherTicketIdArray=strBasePresentsVoucherTicketId.split(",");
		strMorePresentsVoucherTicketIdArray=strMorePresentsVoucherTicketId.split(",");
		strActivityIdArray=strActivityId.split(",");
		
		strMinimumRechargeAmountArray=strMinimumRechargeAmount.split(",");
		dMinimumRechargeAmountArray=new BigDecimal[strMinimumRechargeAmountArray.length];
		for(int i=0;i<dMinimumRechargeAmountArray.length;i++)
		{
			if(!isNumber(strMinimumRechargeAmountArray[i]))
				return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"最低充值金额格式错误",null);
			
			dMinimumRechargeAmount=Double.parseDouble(strMinimumRechargeAmountArray[i]);
			dMinimumRechargeAmountArray[i]=new BigDecimal(dMinimumRechargeAmount);
			
		}
		
		strMoreRechargeAmountArray=strMoreRechargeAmount.split(",");
		dMoreRechargeAmountArray=new BigDecimal[strMoreRechargeAmountArray.length];
		for(int i=0;i<strMoreRechargeAmountArray.length;i++)
		{
			if(!isNumber(strMoreRechargeAmountArray[i]))
				return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"每多充值金额字段格式错误",null);
			dMoreRechargeAmount=Double.parseDouble(strMoreRechargeAmountArray[i]);
			dMoreRechargeAmountArray[i]=new BigDecimal(dMoreRechargeAmount);
		}
		
		strMinimumPresentsVoucherNumberArray=strMinimumPresentsVoucherNumber.split(",");
		iMinimumPresentsVoucherNumberArray=new int[strMinimumPresentsVoucherNumberArray.length];
		for(int i=0;i<strMinimumPresentsVoucherNumberArray.length;i++)
		{
			if(!isNumber(strMinimumPresentsVoucherNumberArray[i]))
				return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"赠送张数格式错误",null);
			iMinimumPresentsVoucherNumberArray[i]=Integer.parseInt(strMinimumPresentsVoucherNumberArray[i]);
		}

		strMorePresentsVoucherNumberArray=strMorePresentsVoucherNumber.split(",");
		iMorePresentsVoucherNumberArray=new int[strMorePresentsVoucherNumberArray.length];
		for(int i=0;i<strMorePresentsVoucherNumberArray.length;i++)
		{
			if(!isNumber(strMorePresentsVoucherNumberArray[i]))
				return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"多赠送张数格式错误",null);
			iMorePresentsVoucherNumberArray[i]=Integer.parseInt(strMorePresentsVoucherNumberArray[i]);
		}
		
		
		strEnabledArray=strEnabled.split(",");
		iEnabledArray=new int[strEnabledArray.length];
		for(int i=0;i<strEnabledArray.length;i++)
		{
			if(!isNumber(strEnabledArray[i]))
				return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"启用状态格式错误",null);
			iEnabledArray[i]=Integer.parseInt(strEnabledArray[i]);
		}
		
		
		//检测各个数组长度是否一致，不一致则报错
		arrayLength=strEnabledArray.length;
		if(arrayLength!=strMorePresentsVoucherNumberArray.length||arrayLength!=strMinimumPresentsVoucherNumberArray.length||arrayLength!=strMoreRechargeAmountArray.length||arrayLength!=strMinimumRechargeAmountArray.length||
				arrayLength!=strMorePresentsVoucherTicketIdArray.length||arrayLength!=strBasePresentsVoucherTicketIdArray.length	)
			return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"参数值有遗漏",null);
	
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
		String strEmployeeName="test";
		String strEmployeeRealName="test";
		//数据写入实体
		List<RechargePresentsVoucherEntity> listRechargePresentsVoucherEntity=new ArrayList<RechargePresentsVoucherEntity>();
		for(int i=0;i<arrayLength;i++)
		{
			RechargePresentsVoucherEntity rechargePresentsVoucherEntity=new RechargePresentsVoucherEntity();
			strRechargePresentsVoucherId=DataTool.getUUID();
			rechargePresentsVoucherEntity.setStrRechargePresentsVoucherId(strRechargePresentsVoucherId);
			rechargePresentsVoucherEntity.setStrBasePresentsVoucherTicketId(strBasePresentsVoucherTicketIdArray[i]);
			rechargePresentsVoucherEntity.setStrMorePresentsVoucherTicketId(strMorePresentsVoucherTicketIdArray[i]);
			rechargePresentsVoucherEntity.setStrActivityId(strActivityIdArray[i]);
			rechargePresentsVoucherEntity.setdMinimumRechargeAmount(dMinimumRechargeAmountArray[i]);
			rechargePresentsVoucherEntity.setiMinimumPresentsVoucherNumber(iMinimumPresentsVoucherNumberArray[i]);
			rechargePresentsVoucherEntity.setdMoreRechargeAmount(dMoreRechargeAmountArray[i]);
			rechargePresentsVoucherEntity.setiMorePresentsVoucherNumber(iMorePresentsVoucherNumberArray[i]);
			
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
	@RequestMapping("updateRechargePresentsVoucher")
	//localhost:8083/admin/RechargePresentsSetting/updateRechargePresentsVoucher?strRechargePresentsVoucherId=2b5a7a7b8198451484434c4114aca45f&strBasePresentsVoucherTicketId=categor1&strMorePresentsVoucherTicketId=bbn2&strActivityId=a42c801d8a7b4daa86653bacf88273a5&dMinimumRechargeAmount=100&iMinimumPresentsVoucherNumber=1&dMoreRechargeAmount=50&iMorePresentsVoucherNumber=1&iEnabled=1
	public String updateRechargePresentsVoucher(HttpServletResponse response,HttpServletRequest request)
	{
		//以下取得的字符串格式都为：字符串1,字符串2
		
		int arrayLength;
		int[] iEnabledArray;
		int[] iMinimumPresentsVoucherNumberArray;
		int[] iMorePresentsVoucherNumberArray;

		double dMoreRechargeAmount;
		double dMinimumRechargeAmount;
		
		BigDecimal[] dMoreRechargeAmountArray;
		BigDecimal[] dMinimumRechargeAmountArray;
		
		
		String[] strMinimumRechargeAmountArray;
		String[] strBasePresentsVoucherTicketIdArray;
		String[] strMorePresentsVoucherTicketIdArray;
		String[] strActivityIdArray;
		String[] strMoreRechargeAmountArray;
		String[] strEnabledArray;
		String[] strMinimumPresentsVoucherNumberArray;
		String[] strMorePresentsVoucherNumberArray;
		String[] strRechargePresentsVoucherIdArray;
		
		String strRechargePresentsVoucherId=request.getParameter("strRechargePresentsVoucherId");
		String strBasePresentsVoucherTicketId=request.getParameter("strBasePresentsVoucherTicketId");
		String strMorePresentsVoucherTicketId=request.getParameter("strMorePresentsVoucherTicketId");
		String strActivityId=request.getParameter("strActivityId");
		String strMinimumRechargeAmount=request.getParameter("dMinimumRechargeAmount");
		String strMinimumPresentsVoucherNumber=request.getParameter("iMinimumPresentsVoucherNumber");
		String strMoreRechargeAmount=request.getParameter("dMoreRechargeAmount");
		String strMorePresentsVoucherNumber=request.getParameter("iMorePresentsVoucherNumber");
		String strEnabled=request.getParameter("iEnabled");
		
		if(ValidateTool.isEmptyStr(strRechargePresentsVoucherId))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"要更新的记录ID不能为空",null);
		
		if(ValidateTool.isEmptyStr(strBasePresentsVoucherTicketId))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"抵用券ID不能为空",null);
		
		if(ValidateTool.isEmptyStr(strMorePresentsVoucherTicketId))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"抵用券ID不能为空",null);
		
		if(ValidateTool.isEmptyStr(strActivityId))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"活动ID不能为空",null);
		
		if(ValidateTool.isEmptyStr(strMinimumRechargeAmount))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"最低充值金额不能为空",null);
		
		if(ValidateTool.isEmptyStr(strMinimumPresentsVoucherNumber))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"赠送数量不能为空",null);
		
		if(ValidateTool.isEmptyStr(strMoreRechargeAmount))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"金充值金额不能为空",null);
		
		if(ValidateTool.isEmptyStr(strMorePresentsVoucherNumber))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"多赠送数量不能为空",null);
		
		if(ValidateTool.isEmptyStr(strEnabled))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"启用状态不能为空",null);
		
		//取出各个字段值
		strRechargePresentsVoucherIdArray=strRechargePresentsVoucherId.split(",");
		strBasePresentsVoucherTicketIdArray=strBasePresentsVoucherTicketId.split(",");
		strMorePresentsVoucherTicketIdArray=strMorePresentsVoucherTicketId.split(",");
		strActivityIdArray=strActivityId.split(",");
		
		strMinimumRechargeAmountArray=strMinimumRechargeAmount.split(",");
		dMinimumRechargeAmountArray=new BigDecimal[strMinimumRechargeAmountArray.length];
		for(int i=0;i<dMinimumRechargeAmountArray.length;i++)
		{
			if(!isNumber(strMinimumRechargeAmountArray[i]))
				return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"最低充值金额格式错误",null);
			
			dMinimumRechargeAmount=Double.parseDouble(strMinimumRechargeAmountArray[i]);
			dMinimumRechargeAmountArray[i]=new BigDecimal(dMinimumRechargeAmount);
			
		}
		
		strMoreRechargeAmountArray=strMoreRechargeAmount.split(",");
		dMoreRechargeAmountArray=new BigDecimal[strMoreRechargeAmountArray.length];
		for(int i=0;i<strMoreRechargeAmountArray.length;i++)
		{
			if(!isNumber(strMoreRechargeAmountArray[i]))
				return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"每多充值金额字段格式错误",null);
			dMoreRechargeAmount=Double.parseDouble(strMoreRechargeAmountArray[i]);
			dMoreRechargeAmountArray[i]=new BigDecimal(dMoreRechargeAmount);
		}
		
		strMinimumPresentsVoucherNumberArray=strMinimumPresentsVoucherNumber.split(",");
		iMinimumPresentsVoucherNumberArray=new int[strMinimumPresentsVoucherNumberArray.length];
		for(int i=0;i<strMinimumPresentsVoucherNumberArray.length;i++)
		{
			if(!isNumber(strMinimumPresentsVoucherNumberArray[i]))
				return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"赠送张数格式错误",null);
			iMinimumPresentsVoucherNumberArray[i]=Integer.parseInt(strMinimumPresentsVoucherNumberArray[i]);
		}

		strMorePresentsVoucherNumberArray=strMorePresentsVoucherNumber.split(",");
		iMorePresentsVoucherNumberArray=new int[strMorePresentsVoucherNumberArray.length];
		for(int i=0;i<strMorePresentsVoucherNumberArray.length;i++)
		{
			if(!isNumber(strMorePresentsVoucherNumberArray[i]))
				return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"多赠送张数格式错误",null);
			iMorePresentsVoucherNumberArray[i]=Integer.parseInt(strMorePresentsVoucherNumberArray[i]);
		}
		
		
		strEnabledArray=strEnabled.split(",");
		iEnabledArray=new int[strEnabledArray.length];
		for(int i=0;i<strEnabledArray.length;i++)
		{
			if(!isNumber(strEnabledArray[i]))
				return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"启用状态格式错误",null);
			iEnabledArray[i]=Integer.parseInt(strEnabledArray[i]);
		}
		
		
		//检测各个数组长度是否一致，不一致则报错
		arrayLength=strEnabledArray.length;
		if(arrayLength!=strMorePresentsVoucherNumberArray.length||arrayLength!=strMinimumPresentsVoucherNumberArray.length||arrayLength!=strMoreRechargeAmountArray.length||arrayLength!=strMinimumRechargeAmountArray.length||
				arrayLength!=strMorePresentsVoucherTicketIdArray.length||arrayLength!=strBasePresentsVoucherTicketIdArray.length||strRechargePresentsVoucherIdArray.length!=arrayLength)
			return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"参数值有遗漏",null);
	

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
		String strEmployeeName="test";
		String strEmployeeRealName="test";
		//数据写入实体
		List<RechargePresentsVoucherEntity> listRechargePresentsVoucherEntity=new ArrayList<RechargePresentsVoucherEntity>();
		for(int i=0;i<arrayLength;i++)
		{
			RechargePresentsVoucherEntity rechargePresentsVoucherEntity=new RechargePresentsVoucherEntity();
			rechargePresentsVoucherEntity.setStrRechargePresentsVoucherId(strRechargePresentsVoucherIdArray[i]);
			rechargePresentsVoucherEntity.setStrBasePresentsVoucherTicketId(strBasePresentsVoucherTicketIdArray[i]);
			rechargePresentsVoucherEntity.setStrMorePresentsVoucherTicketId(strMorePresentsVoucherTicketIdArray[i]);
			rechargePresentsVoucherEntity.setStrActivityId(strActivityIdArray[i]);
			rechargePresentsVoucherEntity.setdMinimumRechargeAmount(dMinimumRechargeAmountArray[i]);
			rechargePresentsVoucherEntity.setiMinimumPresentsVoucherNumber(iMinimumPresentsVoucherNumberArray[i]);
			rechargePresentsVoucherEntity.setdMoreRechargeAmount(dMoreRechargeAmountArray[i]);
			rechargePresentsVoucherEntity.setiMorePresentsVoucherNumber(iMorePresentsVoucherNumberArray[i]);
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
	 * 查询 充值赠送抵用券信息
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("selectAllRechargePresentsVoucher")
	//http://localhost:8083/admin/RechargePresentsSetting/selectAllRechargePresentsVoucher?strActivityId=a42c801d8a7b4daa86653bacf88273a5
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
		String strActivityId=request.getParameter("strActivityId");
		if(ValidateTool.isEmptyStr(strActivityId))
			DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"活动关键字不能为空",null);
		
		List<RechargePresentsVoucherEntity> listRechargePresentsVoucherEntity=new ArrayList<RechargePresentsVoucherEntity>();
		try{
			listRechargePresentsVoucherEntity=rechargePresentsService.selectAllRechargePresentsVoucher(strActivityId);
			if(listRechargePresentsVoucherEntity==null)
				return DataTool.constructResponse(ResultCode.NO_DATA,"暂时没有数据",null);
			if(listRechargePresentsVoucherEntity.size()==0)
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
		/*暂不使用该属性
		String[] strValidateBeginTimeArray;
		String[] strValidateEndTimeArray;
		*/
		String[] strEnabledArray;
		String[] strRechargeAmountArray;
		String[] strPresentsAmountArray;
		
		BigDecimal[] bgRechargeAmountArray;
		BigDecimal[] bgPresentsAmountArray;
	
		String strActivityId=request.getParameter("strActivityId");
		String strRechargeAmount=request.getParameter("dRechargeAmount");
		String strPresentsAmount=request.getParameter("dPresentsAmount");
		/*暂不使用该属性
		String strValidateBeginTime=request.getParameter("strValidateBeginTime");
		String strValidateEndTime=request.getParameter("strValidateEndTime");
		*/
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
		/*暂不使用该属性
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
		*/	
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
		if(arrayLength!=bgPresentsAmountArray.length||arrayLength!=bgRechargeAmountArray.length||arrayLength!=strActivityIdArray.length)
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
			/*
			rechargePresentsStoredValueEntity.setStrValidateBeginTime(strValidateBeginTimeArray[i]);
			rechargePresentsStoredValueEntity.setStrValidateEndTime(strValidateEndTimeArray[i]);
			*/
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
		/*暂不使用该属性
		String[] strValidateBeginTimeArray;
		String[] strValidateEndTimeArray;
		*/
		String[] strEnabledArray;
		String[] strRechargeAmountArray;
		String[] strPresentsAmountArray;
		
		BigDecimal[] bgRechargeAmountArray;
		BigDecimal[] bgPresentsAmountArray;
		
		String strPresentsStoredValueId=request.getParameter("strPresentsStoredValueId");
		String strActivityId=request.getParameter("strActivityId");
		String strRechargeAmount=request.getParameter("dRechargeAmount");
		String strPresentsAmount=request.getParameter("dPresentsAmount");
		/*暂不使用该属性
		String strValidateBeginTime=request.getParameter("strValidateBeginTime");
		String strValidateEndTime=request.getParameter("strValidateEndTime");
		*/
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
		/*暂不使用该属性
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
		*/	
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
		if(arrayLength!=strPresentsStoredValueIdArray.length||arrayLength!=bgPresentsAmountArray.length||arrayLength!=bgRechargeAmountArray.length||arrayLength!=strActivityIdArray.length)
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
			/*暂不使用该属性
			rechargePresentsStoredValueEntity.setStrValidateBeginTime(strValidateBeginTimeArray[i]);
			rechargePresentsStoredValueEntity.setStrValidateEndTime(strValidateEndTimeArray[i]);
			*/
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
	//http://localhost:8083/admin/RechargePresentsSetting/selectAllRechargePresentsStoredValue?strActivityId=005
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
		String strActivityId=request.getParameter("strActivityId");
		if(ValidateTool.isEmptyStr(strActivityId))
			DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"活动关键字不能为空",null);
		try{
			List<RechargePresentsStoredValueEntity> listRechargePresentsStoredValueEntity=rechargePresentsService.selectAllRechargePresentsStoredValue(strActivityId);
			if(listRechargePresentsStoredValueEntity==null)
				return DataTool.constructResponse(ResultCode.NO_DATA,"暂无数据",null);
			if(listRechargePresentsStoredValueEntity.size()==0)
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
	
	
	/**
	 * 新增 充值赠送积分规则则信息
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("insertRechargePresentsIntegration")
	//http://localhost:8083/admin/RechargePresentsSetting/insertRechargePresentsIntegration?strActivityId=1&dPerTimeRechargeAmount=100&dLeastRechargeAmount=100&iEnabled=1
	public String insertRechargePresentsIntegration(HttpServletResponse response,HttpServletRequest request)
	{
		BigDecimal dPerTimeRechargeAmount,dLeastRechargeAmount;
		int iEnabled;
		String strPresentsIntegrationId=DataTool.getUUID();
		String strActivityId=request.getParameter("strActivityId");
		String strPerTimeRechargeAmount=request.getParameter("dPerTimeRechargeAmount");
		String strLeastRechargeAmount=request.getParameter("dLeastRechargeAmount");
		String strEnabled=request.getParameter("iEnabled");
		
		if(ValidateTool.isEmptyStr(strActivityId))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"活动ID不能为空",null);
		
		if(ValidateTool.isEmptyStr(strPerTimeRechargeAmount))
			
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"充值金额不能为空",null);
		if(isNumber(strPerTimeRechargeAmount))
			dPerTimeRechargeAmount=new BigDecimal(Double.parseDouble(strPerTimeRechargeAmount));
		else
			return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"充值金额格式不对",null);
		
		if(ValidateTool.isEmptyStr(strLeastRechargeAmount))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"最低充值金额不能为空",null);
		if(isNumber(strLeastRechargeAmount))
			dLeastRechargeAmount=new BigDecimal(Double.parseDouble(strLeastRechargeAmount));
		else
			return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"最低充值金额格式不对",null);
		
		if(ValidateTool.isEmptyStr(strEnabled))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"启用状态不能为空",null);
		if(isNumber(strEnabled))
			iEnabled=Integer.parseInt(strEnabled);
		else
			return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"是否启用格式不对",null);
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
		
		RechargePresentsIntegrationEntity rechargePresentsIntegrationEntity=new RechargePresentsIntegrationEntity();
		rechargePresentsIntegrationEntity.setStrPresentsIntegrationId(strPresentsIntegrationId);
		rechargePresentsIntegrationEntity.setStrActivityId(strActivityId);
		rechargePresentsIntegrationEntity.setdPerTimeRechargeAmount(dPerTimeRechargeAmount);
		rechargePresentsIntegrationEntity.setdLeastRechargeAmount(dLeastRechargeAmount);
		rechargePresentsIntegrationEntity.setiEnabled(iEnabled);
		rechargePresentsIntegrationEntity.setStrEmployeeId(strEmployeeId);
		rechargePresentsIntegrationEntity.setStrEmployeeName(strEmployeeName);
		rechargePresentsIntegrationEntity.setStrEmployeeRealName(strEmployeeRealName);
		rechargePresentsIntegrationEntity.setStrCreationTime(strCreationTime);
		rechargePresentsIntegrationEntity.setStrLastAccessedTime(strLastAccessedTime);
		try{
			return rechargePresentsService.insertRechargePresentsIntegration(rechargePresentsIntegrationEntity);
		}catch(Exception e)
		{
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
		}
	}
	
	
	
	/**
	 * 更新 充值赠送积分规则则信息
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("updateRechargePresentsIntegration")
	//http://localhost:8083/admin/RechargePresentsSetting/updateRechargePresentsIntegration?strPresentsIntegrationId=a96bf856b7144d3db6bc4fd5b593f5f6&strActivityId=1&dPerTimeRechargeAmount=10000&dLeastRechargeAmount=10000&iEnabled=1
	public String updateRechargePresentsIntegration(HttpServletResponse response,HttpServletRequest request)
	{
		BigDecimal dPerTimeRechargeAmount,dLeastRechargeAmount;
		int iEnabled;
		String strPresentsIntegrationId=request.getParameter("strPresentsIntegrationId");
		String strActivityId=request.getParameter("strActivityId");
		String strPerTimeRechargeAmount=request.getParameter("dPerTimeRechargeAmount");
		String strLeastRechargeAmount=request.getParameter("dLeastRechargeAmount");
		String strEnabled=request.getParameter("iEnabled");
		
		if(ValidateTool.isEmptyStr(strPresentsIntegrationId))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"积分规则ID不能为空",null);
		
		if(ValidateTool.isEmptyStr(strActivityId))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"活动ID不能为空",null);
		
		if(ValidateTool.isEmptyStr(strPerTimeRechargeAmount))
			
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"充值金额不能为空",null);
		if(isNumber(strPerTimeRechargeAmount))
			dPerTimeRechargeAmount=new BigDecimal(Double.parseDouble(strPerTimeRechargeAmount));
		else
			return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"充值金额格式不对",null);
		
		if(ValidateTool.isEmptyStr(strLeastRechargeAmount))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"最低充值金额不能为空",null);
		if(isNumber(strLeastRechargeAmount))
			dLeastRechargeAmount=new BigDecimal(Double.parseDouble(strLeastRechargeAmount));
		else
			return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"最低充值金额格式不对",null);
		
		if(ValidateTool.isEmptyStr(strEnabled))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"启用状态不能为空",null);
		if(isNumber(strEnabled))
			iEnabled=Integer.parseInt(strEnabled);
		else
			return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"是否启用格式不对",null);
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
		
		RechargePresentsIntegrationEntity rechargePresentsIntegrationEntity=new RechargePresentsIntegrationEntity();
		rechargePresentsIntegrationEntity.setStrPresentsIntegrationId(strPresentsIntegrationId);
		rechargePresentsIntegrationEntity.setStrActivityId(strActivityId);
		rechargePresentsIntegrationEntity.setdPerTimeRechargeAmount(dPerTimeRechargeAmount);
		rechargePresentsIntegrationEntity.setdLeastRechargeAmount(dLeastRechargeAmount);
		rechargePresentsIntegrationEntity.setiEnabled(iEnabled);
		rechargePresentsIntegrationEntity.setStrEmployeeId(strEmployeeId);
		rechargePresentsIntegrationEntity.setStrEmployeeName(strEmployeeName);
		rechargePresentsIntegrationEntity.setStrEmployeeRealName(strEmployeeRealName);
		rechargePresentsIntegrationEntity.setStrLastAccessedTime(strLastAccessedTime);
		try{
			return rechargePresentsService.updateRechargePresentsIntegration(rechargePresentsIntegrationEntity);
		}catch(Exception e)
		{
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
		}
		
	}
	
	
	/**
	 * 查询 充值赠送积分规则则信息
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("selectAllRechargePresentsIntegration")
	//http://localhost:8083/admin/RechargePresentsSetting/selectAllRechargePresentsIntegration?strActivityId=1
	public String selectAllRechargePresentsIntegration(HttpServletRequest request,HttpServletResponse response)
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
		String strActivityId=request.getParameter("strActivityId");
		if(ValidateTool.isEmptyStr(strActivityId))
			DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"活动关键字不能为空",null);
		try{
			List<RechargePresentsIntegrationEntity> listRechargePresentsIntegrationEntity=rechargePresentsService.selectAllRechargePresentsIntegration(strActivityId);
			if(listRechargePresentsIntegrationEntity==null)
				return DataTool.constructResponse(ResultCode.NO_DATA,"暂无数据",null);
			if(listRechargePresentsIntegrationEntity.size()==0)
				return DataTool.constructResponse(ResultCode.NO_DATA,"暂无数据",null);
			Map<String,Object> resultMap=new HashMap<String,Object>();
			resultMap.put("listRechargePresentsIntegrationEntity", listRechargePresentsIntegrationEntity);
			return DataTool.constructResponse(ResultCode.OK,"查询成功",resultMap);
			}catch(Exception e)
			{
				e.printStackTrace();
				return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
			}
		
	}
	

	
	/**
	 * 新增 优惠活动详细信息
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("insertPresentsActivityInfo")
	//http://localhost:8083/admin/RechargePresentsSetting/inserPresentsActivityInfo?strActivityName=十周年店庆&strLevelsId=10000&strActivityBeginTime=2017/5/27&strActivityEndTime=2017/5/27
	public String insertPresentsActivityInfo(HttpServletRequest request,HttpServletResponse response)
	{
		String strActivityId=DataTool.getUUID();
		String strActivityName=request.getParameter("strActivityName");
		String strLevelsId=request.getParameter("strLevelsId");
		String strActivityBeginTime=request.getParameter("strActivityBeginTime");
		String strActivityEndTime=request.getParameter("strActivityEndTime");
		int iActivityKinds=2; // 
		
		if(ValidateTool.isEmptyStr(strActivityName))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"活动名称不能为空",null);
		
		if(ValidateTool.isEmptyStr(strLevelsId))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"会员级别不能为空,请先填写会员级别",null);
		
		if(ValidateTool.isEmptyStr(strActivityBeginTime))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"有效期开始时间不能为空",null);
		strActivityBeginTime=DateTool.StringToString(strActivityBeginTime,DateStyle.YYYY_MM_DD);
		
		if(ValidateTool.isEmptyStr(strActivityEndTime))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"有效期结束时间不能为空",null);
		strActivityEndTime=DateTool.StringToString(strActivityEndTime,DateStyle.YYYY_MM_DD);
		
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
		
		RechargePresentsActivityEntity presentsActivityEntity=new RechargePresentsActivityEntity();
		presentsActivityEntity.setStrActivityId(strActivityId);
		presentsActivityEntity.setStrActivityName(strActivityName);
		presentsActivityEntity.setStrLevelsId(strLevelsId);
		presentsActivityEntity.setStrActivityBeginTime(strActivityBeginTime);
		presentsActivityEntity.setStrActivityEndTime(strActivityEndTime);
		presentsActivityEntity.setiActivityKinds(iActivityKinds);
		presentsActivityEntity.setStrEmployeeId(strEmployeeId);
		presentsActivityEntity.setStrEmployeeName(strEmployeeName);
		presentsActivityEntity.setStrEmployeeRealName(strEmployeeRealName);
		presentsActivityEntity.setStrCreationTime(strCreationTime);
		presentsActivityEntity.setStrLastAccessedTime(strLastAccessedTime);
		try{
			return rechargePresentsService.insertPresentsActivityInfo(presentsActivityEntity);
		}catch(Exception e)
		{
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
		}
		
	}
	
	
	
	
	
	/**
	 * 更新 优惠活动详细信息
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("updatePresentsActivityInfo")
	//http://localhost:8083/admin/RechargePresentsSetting/updatePresentsActivityInfo?strActivityId=3f3a8fcdba014bfa9058622a09c8aa64&strActivityName=十周年店庆&strLevelsId=10000&strActivityBeginTime=2017/5/27&strActivityEndTime=2017/5/27
	public String updatePresentsActivityInfo(HttpServletRequest request,HttpServletResponse response)
	{
		String strActivityId=request.getParameter("strActivityId");
		String strActivityName=request.getParameter("strActivityName");
		String strLevelsId=request.getParameter("strLevelsId");
		String strActivityBeginTime=request.getParameter("strActivityBeginTime");
		String strActivityEndTime=request.getParameter("strActivityEndTime");
		int iActivityKinds=2; // 
		
		if(ValidateTool.isEmptyStr(strActivityName))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"活动名称不能为空",null);
		
		if(ValidateTool.isEmptyStr(strLevelsId))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"会员级别不能为空,请先填写会员级别",null);
		
		if(ValidateTool.isEmptyStr(strActivityBeginTime))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"有效期开始时间不能为空",null);
		strActivityBeginTime=DateTool.StringToString(strActivityBeginTime,DateStyle.YYYY_MM_DD);
		
		if(ValidateTool.isEmptyStr(strActivityEndTime))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"有效期结束时间不能为空",null);
		strActivityEndTime=DateTool.StringToString(strActivityEndTime,DateStyle.YYYY_MM_DD);
		
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
		
		RechargePresentsActivityEntity rechargePresentsActivityEntity=new RechargePresentsActivityEntity();
		rechargePresentsActivityEntity.setStrActivityId(strActivityId);
		rechargePresentsActivityEntity.setStrActivityName(strActivityName);
		rechargePresentsActivityEntity.setStrLevelsId(strLevelsId);
		rechargePresentsActivityEntity.setStrActivityBeginTime(strActivityBeginTime);
		rechargePresentsActivityEntity.setStrActivityEndTime(strActivityEndTime);
		rechargePresentsActivityEntity.setiActivityKinds(iActivityKinds);
		rechargePresentsActivityEntity.setStrEmployeeId(strEmployeeId);
		rechargePresentsActivityEntity.setStrEmployeeName(strEmployeeName);
		rechargePresentsActivityEntity.setStrEmployeeRealName(strEmployeeRealName);
		rechargePresentsActivityEntity.setStrLastAccessedTime(strLastAccessedTime);
		try{
			return rechargePresentsService.updatePresentsActivityInfo(rechargePresentsActivityEntity);
		}catch(Exception e)
		{
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
		}
		
	}

	
	/**
	 * 分页查询 优惠活动详细信息
	 * @param request
	 * @param response
	 * @return
	 */

	@ResponseBody
	@RequestMapping("selectRechargePresentsActivityInfo")
	//http://localhost:8083/admin/RechargePresentsSetting/selectRechargePresentsActivityInfo?iPageNum=1&iPageSize=1&strSearchMemberLevel=1&strSearchEnabledStatus=ALL
	public String selectRechargePresentsActivityInfo(HttpServletRequest request,HttpServletResponse response)
	{
		//取得搜索字段
		//strSearchEnabledStatus=接收参数值为：全部:"ALL(或空值)"；过期:"EXPIRED"，正常:"NORMAL"
		//strSearchMemberLevel=接收参数值为："ALL(或空值)" 全部；其它会员级别ID值
		int iPageNum,iPageSize,iTotalPage,iTotalRecord=0,iPageFrom;
		String strSearchMemberLevel=request.getParameter("strSearchMemberLevel");
		String strSearchEnabledStatus=request.getParameter("strSearchEnabledStatus");
		String strPageNum=request.getParameter("iPageNum");
		String strPageSize=request.getParameter("iPageSize");
		String strCurrentDate=DateTool.DateToString(new Date(),DateStyle.YYYY_MM_DD);
		
		if(strSearchMemberLevel==null||"".equals(strSearchMemberLevel.trim()))
			strSearchMemberLevel="";
		
		if("ALL".equals(strSearchMemberLevel))
			strSearchMemberLevel="";
		
		if(strSearchEnabledStatus==null||"".equals(strSearchEnabledStatus.trim()))
			strSearchEnabledStatus="ALL";
		
		if(ValidateTool.isEmptyStr(strPageNum))
			iPageNum=1;
		else
		{
			if(isNumber(strPageNum))
				iPageNum=Integer.parseInt(strPageNum);
			else
				iPageNum=1;
				
		}
		
		if(ValidateTool.isEmptyStr(strPageSize))
			iPageSize=StaticValue.PAGE_SIZE;
		else
		{
			if(isNumber(strPageSize))
				iPageSize=Integer.parseInt(strPageSize);
			else
				iPageSize=StaticValue.PAGE_SIZE;
		}
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
			
			Map<String,Object> queryMap=new HashMap<String,Object>();
			queryMap.put("strSearchMemberLevel", strSearchMemberLevel);
			queryMap.put("strCurrentDate", strCurrentDate);

			if("ALL".equals(strSearchEnabledStatus))
				iTotalRecord=rechargePresentsService.findCount(queryMap);
				
			if("EXPIRED".equals(strSearchEnabledStatus))
				iTotalRecord=rechargePresentsService.findExpiredCount(queryMap);
			
			if("NORMAL".equals(strSearchEnabledStatus))
				iTotalRecord=rechargePresentsService.findNormalCount(queryMap);
			
			if(iTotalRecord!=0)
			{
				List<RechargePresentsActivityEntity> listRechargePresentsActivityEntity=null;
				iTotalPage=(iTotalRecord%iPageSize==0?iTotalRecord/iPageSize:iTotalRecord/iPageSize+1);
				if(iPageNum>iTotalPage)
					iPageNum=iTotalPage;
				iPageFrom=(iPageNum-1)*iPageSize;
				
				queryMap.put("iPageFrom",iPageFrom);
				queryMap.put("iPageSize",iPageSize);

				if("ALL".equals(strSearchEnabledStatus))
					listRechargePresentsActivityEntity=rechargePresentsService.selectRechargePresentsActivityInfo(queryMap);
				
				if("EXPIRED".equals(strSearchEnabledStatus))
					listRechargePresentsActivityEntity=rechargePresentsService.selectExpiredRechargePresentsActivityInfo(queryMap);
				
				if("NORMAL".equals(strSearchEnabledStatus))
					listRechargePresentsActivityEntity=rechargePresentsService.selectNormalRechargePresentsActivityInfo(queryMap);
				if(listRechargePresentsActivityEntity==null)
					return DataTool.constructResponse(ResultCode.NO_DATA,"暂无数据",null);
				if(listRechargePresentsActivityEntity.size()==0)
					return DataTool.constructResponse(ResultCode.NO_DATA,"暂无数据",null);
				
				Map<String,Object> resultMap=new HashMap<String,Object>();
				resultMap.put("iTotalRecord",iTotalRecord);
				resultMap.put("iTotalPage",iTotalPage);
				resultMap.put("listRechargePresentsActivityEntity",listRechargePresentsActivityEntity);
				return DataTool.constructResponse(ResultCode.OK,"查询成功",resultMap);
			}
			else
				return DataTool.constructResponse(ResultCode.NO_DATA,"暂无数据",null);
		
		}catch(Exception e)
		{
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
		}
		
		
		
	}
	
	
	/**
	 * 分页显示--删除
	 * @param request
	 * @param response
	 * @return
	 */

	@ResponseBody
	@RequestMapping("deleteRechargePresentsActivityInfo")
	//http://localhost:8083/admin/RechargePresentsSetting/deleteRechargePresentsActivityInfo?strActivityId=ed723c91ad9045b1b712a1b7af62cfa9
	public String deleterRechargePresentsActivityInfo(HttpServletResponse response,HttpServletRequest request)
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
		String strActivityId=request.getParameter("strActivityId");
		if(ValidateTool.isEmptyStr("strActivityId"))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"参数不能为空",null);
		try{
			return rechargePresentsService.deleteRechargePresentsActivityInfo(strActivityId);
		}catch(Exception e)
		{
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
		}
	}
	
	/**
	 * 查询 优惠活动详细信息 单条
	 * @param request
	 * @param response
	 * @return
	 */

	@ResponseBody
	@RequestMapping("selectAllRechargePresentsActivityEntity")
	//http://localhost:8083/admin/RechargePresentsSetting/selectAllRechargePresentsActivityEntity?strActivityId=a42c801d8a7b4daa86653bacf88273a5
	public String selectAllRechargePresentsActivityEntity(HttpServletResponse response,HttpServletRequest request)
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
		RechargePresentsActivityEntity rechargePresentsActivityEntity=null;
		String strActivityId=request.getParameter("strActivityId");
		if(ValidateTool.isEmptyStr("strActivityId"))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"Id不能为空",null);
		try{
			rechargePresentsActivityEntity=rechargePresentsService.selectAllRechargePresentsActivityEntity(strActivityId);
			if(rechargePresentsActivityEntity==null)
				return DataTool.constructResponse(ResultCode.NO_DATA,"暂无数据",null);
			Map<String,Object> resultMap=new HashMap<String,Object>();
			resultMap.put("rechargePresentsActivityEntity", rechargePresentsActivityEntity);
			return DataTool.constructResponse(ResultCode.OK,"查询成功",resultMap);
			
		}catch(Exception e)
		{
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
		}
		
		
	}
	

	
	//查询一条充值赠送活动信息rechargePresentsActivityEntity
	@ResponseBody
	@RequestMapping("selectRechargePresentsActivityEntity")
	//http://localhost:8083/admin/RechargePresentsSetting/selectRechargePresentsActivityEntity
	public String selectRechargePresentsActivityEntity(HttpServletResponse response,HttpServletRequest request)
	{

	//身份检测
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

	RechargePresentsActivityEntity rechargePresentsActivityEntity=null;
	try{
		rechargePresentsActivityEntity=rechargePresentsService.selectRechargePresentsActivityEntity();
		if(rechargePresentsActivityEntity==null)
		return DataTool.constructResponse(ResultCode.NO_DATA,"暂无数据",null);
		Map<String,Object> resultMap=new HashMap<String,Object>();
		resultMap.put("rechargePresentsActivityEntity",rechargePresentsActivityEntity);
		return DataTool.constructResponse(ResultCode.OK,"查询成功",resultMap);
	}catch(Exception e)
	{
		e.printStackTrace();
		return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
	}
	}
	

	
	//查询一条刚新建的充值赠送储值规则信息rechargePresentsStoredValueEntity
	@ResponseBody
	@RequestMapping("selectRechargePresentsStoredValueEntity")
	//http://localhost:8083/admin/RechargePresentsSetting/selectRechargePresentsStoredValueEntity
	public String selectRechargePresentsStoredValueEntity(HttpServletResponse response,HttpServletRequest request)
	{

	//身份检测
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

	RechargePresentsStoredValueEntity rechargePresentsStoredValueEntity=null;
	try{
		rechargePresentsStoredValueEntity=rechargePresentsService.selectRechargePresentsStoredValueEntity();
		if(rechargePresentsStoredValueEntity==null)
		return DataTool.constructResponse(ResultCode.NO_DATA,"暂无数据",null);
		Map<String,Object> resultMap=new HashMap<String,Object>();
		resultMap.put("rechargePresentsStoredValueEntity",rechargePresentsStoredValueEntity);
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
