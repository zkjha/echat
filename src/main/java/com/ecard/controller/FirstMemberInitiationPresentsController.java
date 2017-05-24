package com.ecard.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
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
import com.ecard.entity.FirstMemberInitiationIntegrationPresentsEntity;
import com.ecard.entity.FirstMemberInitiationStoredTicketPresentsEntity;
import com.ecard.entity.FirstMemberInitiationVoucherTicketPresentsEntity;
import com.ecard.service.FirstMemberInitiationPresentsService;
import com.ecard.util.WebSessionUtil;

@Controller
@RequestMapping("/admin/biz/presentsSetting")
public class FirstMemberInitiationPresentsController {
	@Autowired
	private FirstMemberInitiationPresentsService firstMemberInitiationPresentsService;
	@Autowired
	private WebSessionUtil webSessionUtil;
	
	
	/**
	 * 新增--顾客首次入会获赠积分信息
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("insertFirstMemberInitiationIntegrationPresents")
	//localhost:8083/admin/biz/presentsSetting/insertFirstMemberInitiationIntegrationPresents?iIntegrationPresentsValue=1000&iEnabled=1
	public String insertIntegrationPresents(HttpServletResponse response,HttpServletRequest request)
	{
		int iIntegrationPresentsValue,iEnabled;
		String strIntegrationPresentsId=DataTool.getUUID();
		String strIntegrationPresentsValue=request.getParameter("iIntegrationPresentsValue");
		String strEnabled=request.getParameter("iEnabled");
		if(ValidateTool.isEmptyStr(strIntegrationPresentsValue))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"请输入积分数量",null);
		else
		{
			if(isNumber(strIntegrationPresentsValue))
				{
				iIntegrationPresentsValue=Integer.parseInt(strIntegrationPresentsValue);
				if(iIntegrationPresentsValue==0)
					return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"积分不能为0",null);
				}
			else
				return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"积分数量格式错误",null);
		}
		
		if(ValidateTool.isEmptyStr(strEnabled))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"启用状态不能为空",null);
		else
		{
			if(isNumber(strEnabled))
				iEnabled=Integer.parseInt(strEnabled);
			else
				return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"启用状态格式错误",null);
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
		//取得登录员工信息，并增加修改时间记录
		String strEmployeeId=employeeEntity.getStrEmployeeid();
		String strEmployeeName=employeeEntity.getStrLoginname();
		String strEmployeeRealName=employeeEntity.getStrRealname();
	*/
		String strCreationTime=DateTool.DateToString(new Date(),DateStyle.YYYY_MM_DD_HH_MM);
		String strLastAccessedTime=DateTool.DateToString(new Date(),DateStyle.YYYY_MM_DD_HH_MM);
		//以下3个为测试用数据
		String strEmployeeId=DataTool.getUUID();
		String strEmployeeName="admin";
		String strEmployeeRealName="david li";
		
		FirstMemberInitiationIntegrationPresentsEntity IntegrationPresentsEntity=new FirstMemberInitiationIntegrationPresentsEntity();
		IntegrationPresentsEntity.setStrIntegrationPresentsId(strIntegrationPresentsId);
		IntegrationPresentsEntity.setIIntegrationPresentsValue(iIntegrationPresentsValue);
		IntegrationPresentsEntity.setIEnabled(iEnabled);
		IntegrationPresentsEntity.setStrEmployeeId(strEmployeeId);
		IntegrationPresentsEntity.setStrEmployeeName(strEmployeeName);
		IntegrationPresentsEntity.setStrEmployeeRealName(strEmployeeRealName);
		IntegrationPresentsEntity.setStrCreationTime(strCreationTime);
		IntegrationPresentsEntity.setStrLastAccessedTime(strLastAccessedTime);
		//调用SERVICE层进行操作
		try{
			return firstMemberInitiationPresentsService.insertIntegrationPresents(IntegrationPresentsEntity);
			
		}catch(Exception e)
		{
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误", null);
		}
		
	}
	
	
	
	/**
	 * 查询--顾客首次入会获赠积分信息
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("selectFirstMemberInitiationIntegrationPresents")
	//localhost:8083/admin/biz/presentsSetting/selectFirstMemberInitiationIntegrationPresents
	public String selectFirstMemberInitiationIntegrationPresents(HttpServletResponse response,HttpServletRequest request)
	{
	/*  检验身份有效性
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
			FirstMemberInitiationIntegrationPresentsEntity integrationPresentsEntity=firstMemberInitiationPresentsService.selectFirstMemberInitiationIntegrationPresents();
			if(integrationPresentsEntity==null)
				return DataTool.constructResponse(ResultCode.NO_DATA,"暂无数据",null);
			else
			{
				Map<String,Object> resultMap=new HashMap<String,Object>();
				resultMap.put("integrationPresentsEntity", integrationPresentsEntity);
				return DataTool.constructResponse(ResultCode.OK,"查询成功",resultMap);
			}
		}catch (Exception e)
			{
				e.printStackTrace();
				return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
			}
	}
	
	/**
	 * 更新--顾客首次入会获赠积分信息
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("updateFirstMemberInitiationIntegrationPresents")
	//localhost:8083/admin/biz/presentsSetting/updateFirstMemberInitiationIntegrationPresents?strIntegrationPresentsId=c3e54a729922446ab19070d1a4041541&iIntegrationPresentsValue=10&iEnabled=0
	public String updateFirstMemberInitiationIntegrationPresents(HttpServletResponse response,HttpServletRequest request)
	{
		int iIntegrationPresentsValue,iEnabled;
		String strIntegrationPresentsId=request.getParameter("strIntegrationPresentsId");
		String strIntegrationPresentsValue=request.getParameter("iIntegrationPresentsValue");
		String strEnabled=request.getParameter("iEnabled");
		if(ValidateTool.isEmptyStr(strIntegrationPresentsId))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"记录ID不能为空",null);
		if(ValidateTool.isEmptyStr(strIntegrationPresentsValue))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"请输入积分数量",null);
		else
		{
			if(isNumber(strIntegrationPresentsValue))
				{
				iIntegrationPresentsValue=Integer.parseInt(strIntegrationPresentsValue);
				if(iIntegrationPresentsValue==0)
					return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"积分不能为0",null);
				}
				
			else
				return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"积分数量格式错误",null);
		}
		
		if(ValidateTool.isEmptyStr(strEnabled))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"启用状态不能为空",null);
		else
		{
			if(isNumber(strEnabled))
				iEnabled=Integer.parseInt(strEnabled);
			else
				return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"启用状态格式错误",null);
		}
		
		
	/*	检验身份有效性
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
		
		String strLastAccessedTime=DateTool.DateToString(new Date(),DateStyle.YYYY_MM_DD_HH_MM);
		//以下3个为测试用数据
		String strEmployeeId=DataTool.getUUID();
		String strEmployeeName="admin";
		String strEmployeeRealName="david li";
		
		FirstMemberInitiationIntegrationPresentsEntity IntegrationPresentsEntity=new FirstMemberInitiationIntegrationPresentsEntity();
		IntegrationPresentsEntity.setStrIntegrationPresentsId(strIntegrationPresentsId);
		IntegrationPresentsEntity.setIIntegrationPresentsValue(iIntegrationPresentsValue);
		IntegrationPresentsEntity.setIEnabled(iEnabled);
		IntegrationPresentsEntity.setStrEmployeeId(strEmployeeId);
		IntegrationPresentsEntity.setStrEmployeeName(strEmployeeName);
		IntegrationPresentsEntity.setStrEmployeeRealName(strEmployeeRealName);
		IntegrationPresentsEntity.setStrLastAccessedTime(strLastAccessedTime);
		//调用SERVICE层进行操作
		try{
			return firstMemberInitiationPresentsService.updateIntegrationPresents(IntegrationPresentsEntity);
			
		}catch(Exception e)
		{
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误", null);
		}
	}
	
	/**
	 * 删除--顾客首次入会获赠积分信息
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("deleteFirstMemberInitiationIntegrationPresents")
	//localhost:8083/admin/biz/presentsSetting/deleteFirstMemberInitiationIntegrationPresents?strIntegrationPresentsId=c3e54a729922446ab19070d1a4041541
	public String deleteFirstMemberInitiationIntegrationPresents(HttpServletResponse response,HttpServletRequest request)
	{
		String strIntegrationPresentsId=request.getParameter("strIntegrationPresentsId");
		if(ValidateTool.isEmptyStr(strIntegrationPresentsId))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"关键字不能为空",null);
		/*  检验身份有效性
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
			return firstMemberInitiationPresentsService.deleteFirstMemberInitiationIntegrationPresents(strIntegrationPresentsId);
		}catch (Exception e)
		{
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
		}
	}
	
	
	/**
	 * 新增 顾客首次入会获赠储值卷
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("insertStoredTicketPresentsInfo")
	//localhost:8083/admin/biz/presentsSetting/insertStoredTicketPresentsInfo?iStoredValuePresents=100&iTotalStoredTicketNum=500&strValidateBeginTime=2017/5/15&strValidateEndTime=2017/8/30&iEnabled=1
	public String insertStoredTicketPresentsInfo(HttpServletResponse response,HttpServletRequest request)
	{
		int iStoredValuePresents,iTotalStoredTicketNum,iRestStoredTicketNum,iEnabled;
		String strStoredTicketPresentsId=DataTool.getUUID();
		String strStoredValuePresents=request.getParameter("iStoredValuePresents");
		String strTotalStoredTicketNum=request.getParameter("iTotalStoredTicketNum");
		//String strRestStoredTicketNum=request.getParameter("iRestStoredTicketNum");   新增数据时，剩余张数=总张数
		String strValidateBeginTime=request.getParameter("strValidateBeginTime");
		String strValidateEndTime=request.getParameter("strValidateEndTime");
		String strEnabled=request.getParameter("iEnabled");
		if(ValidateTool.isEmptyStr(strStoredValuePresents))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"储值量不能为空",null);
		else
		{
			if(isNumber(strStoredValuePresents))
				{
				iStoredValuePresents=Integer.parseInt(strStoredValuePresents);
				if(iStoredValuePresents==0)
					return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"请填写储值数量",null);
				}
			else
				return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"数据格式错误",null);
		}
		
		if(ValidateTool.isEmptyStr(strTotalStoredTicketNum))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"总张数不能为空",null);
		else
		{
			if(isNumber(strTotalStoredTicketNum))
			{
				iTotalStoredTicketNum=Integer.parseInt(strTotalStoredTicketNum);
				if(iTotalStoredTicketNum==0)
					return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"总张数不能为空",null);
			}
			else
				return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"数据格式错误",null);
		}
		
		iRestStoredTicketNum=iTotalStoredTicketNum;					//新增规则时：剩余张数=总张数
		if(ValidateTool.isEmptyStr(strValidateBeginTime))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"有效期起始时间不能为空",null);
		else
		{
			//格式化时间如设计图YYYY_MM_DD格式
			strValidateBeginTime=DateTool.StringToString(strValidateBeginTime, DateStyle.YYYY_MM_DD);
		}
		if(ValidateTool.isEmptyStr(strValidateEndTime))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"有效期终止时间不能为空",null);
		else
		{
			//格式化时间如设计图YYYY_MM_DD格式
			strValidateEndTime=DateTool.StringToString(strValidateEndTime, DateStyle.YYYY_MM_DD);
		}
		if(ValidateTool.isEmptyStr(strEnabled))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"启用状态不能为空",null);
		else
		{
			if(isNumber(strEnabled))
				iEnabled=Integer.parseInt(strEnabled);
			else
				return  DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR, "启用状态格式错误",null);
		}
		
	/* 检验身份有效性
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
		String strCreationTime=DateTool.DateToString(new Date(),DateStyle.YYYY_MM_DD_HH_MM);
		String strLastAccessedTime=DateTool.DateToString(new Date(),DateStyle.YYYY_MM_DD_HH_MM);
		//以下3个为测试用数据
		String strEmployeeId=DataTool.getUUID();
		String strEmployeeName="admin";
		String strEmployeeRealName="david li";
		
	//新建实体对象，写入数据
	FirstMemberInitiationStoredTicketPresentsEntity storedTicketPresentsEntity=new FirstMemberInitiationStoredTicketPresentsEntity();
	storedTicketPresentsEntity.setStrStoredTicketPresentsId(strStoredTicketPresentsId);
	storedTicketPresentsEntity.setIstoredValuePresents(iStoredValuePresents);
	storedTicketPresentsEntity.setItotalStoredTicketNum(iTotalStoredTicketNum);
	storedTicketPresentsEntity.setIrestStoredTicketNum(iRestStoredTicketNum);
	storedTicketPresentsEntity.setStrValidateBeginTime(strValidateBeginTime);
	storedTicketPresentsEntity.setStrValidateEndTime(strValidateEndTime);
	storedTicketPresentsEntity.setIEnabled(iEnabled);
	storedTicketPresentsEntity.setStrEmployeeId(strEmployeeId);
	storedTicketPresentsEntity.setStrEmployeeName(strEmployeeName);
	storedTicketPresentsEntity.setStrEmployeeRealName(strEmployeeRealName);
	storedTicketPresentsEntity.setStrCreationTime(strCreationTime);
	storedTicketPresentsEntity.setStrLastAccessedTime(strLastAccessedTime);
	
	try{
		return firstMemberInitiationPresentsService.insertStoredTicketPresentsInfo(storedTicketPresentsEntity);
	}catch(Exception e)
	{
		e.printStackTrace();
		return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误",null);
		
	}
}	
	
	
	/**
	 * 查询 顾客首次入会获赠储值卷
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("selectStoredTicketPresentsInfo")
	//localhost:8083/admin/biz/presentsSetting/selectStoredTicketPresentsInfo
	public String selectStoredTicketPresentsInfo(HttpServletResponse response,HttpServletRequest request)
	{
		
		/*  检验身份有效性
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
		List<FirstMemberInitiationStoredTicketPresentsEntity> listStoredTicketPresentsEntity=null;
		try{
			listStoredTicketPresentsEntity=firstMemberInitiationPresentsService.selectStoredTicketPresentsInfo();
			if(listStoredTicketPresentsEntity==null||listStoredTicketPresentsEntity.size()==0)
				return DataTool.constructResponse(ResultCode.NO_DATA,"数据库还没有数据",null);
			else
			{
			Map<String,Object> resultMap=new HashMap<String,Object>();
			resultMap.put("listStoredTicketPresentsEntity", listStoredTicketPresentsEntity);
			return DataTool.constructResponse(ResultCode.OK,"查询成功",resultMap);
			}
			}catch(Exception e)
		{
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
		}
	}
	
	
	
	/**
	 * 更新 顾客首次入会获赠储值卷[1条记录]
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("updateStoredTicketPresentsInfo")
	//localhost:8083/admin/biz/presentsSetting/updateStoredTicketPresentsInfo?strStoredTicketPresentsId=fb33e4a47ca24bbc82e23e6c9a214591&iStoredValuePresents=10&iTotalStoredTicketNum=500&strstrValidateBeginTime=2017/5/15&strValidateEndTime=2017/5/16&iEnabled=1
	public String updateStoredTicketPresentsInfo(HttpServletRequest request,HttpServletResponse response)
	{
		int iStoredValuePresents,iTotalStoredTicketNum,iEnabled,iRestStoredTicketNum;
		String strStoredTicketPresentsId=request.getParameter("strStoredTicketPresentsId");
		String strStoredValuePresents=request.getParameter("iStoredValuePresents");
		String strTotalStoredTicketNum=request.getParameter("iTotalStoredTicketNum");
		 
		String strValidateBeginTime=request.getParameter("strstrValidateBeginTime");
		String strValidateEndTime=request.getParameter("strValidateEndTime");
		String strEnabled=request.getParameter("iEnabled");
		if(ValidateTool.isEmptyStr(strStoredTicketPresentsId))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"ID不能为空",null);
		
		if(ValidateTool.isEmptyStr(strStoredValuePresents))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"储值量不能为空",null);
		else
		{
			if(isNumber(strStoredValuePresents))
				{
				iStoredValuePresents=Integer.parseInt(strStoredValuePresents);
				if(iStoredValuePresents==0)
					return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"请填写储值数量",null);
				}
			else
				return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"数据格式错误",null);
		}
		
		if(ValidateTool.isEmptyStr(strTotalStoredTicketNum))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"总张数不能为空",null);
		else
		{
			if(isNumber(strTotalStoredTicketNum))
			{
				iTotalStoredTicketNum=Integer.parseInt(strTotalStoredTicketNum);
				if(iTotalStoredTicketNum==0)
					return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"总张数不能为空",null);
			}
			else
				return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"数据格式错误",null);
		}
		
		iRestStoredTicketNum=iTotalStoredTicketNum;//对剩余张数赋值，默认等于总张数
		if(ValidateTool.isEmptyStr(strValidateBeginTime))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"有效期起始时间不能为空",null);
		else
		{
			//格式化时间如设计图YYYY_MM_DD格式
			strValidateEndTime=DateTool.StringToString(strValidateBeginTime, DateStyle.YYYY_MM_DD);
		}
		
		
		if(ValidateTool.isEmptyStr(strValidateEndTime))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"有效期终止时间不能为空",null);
		else
		{
			//格式化时间如设计图YYYY_MM_DD格式
			strValidateEndTime=DateTool.StringToString(strValidateEndTime, DateStyle.YYYY_MM_DD);
		}
		if(ValidateTool.isEmptyStr(strEnabled))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"启用状态不能为空",null);
		else
		{
			if(isNumber(strEnabled))
				iEnabled=Integer.parseInt(strEnabled);
			else
				return  DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR, "启用状态格式错误",null);
		}
		
	/* 检验身份有效性
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
		String strLastAccessedTime=DateTool.DateToString(new Date(),DateStyle.YYYY_MM_DD_HH_MM);
		//以下3个为测试用数据
		String strEmployeeId=DataTool.getUUID();
		String strEmployeeName="admin";
		String strEmployeeRealName="david li";
		
	//新建实体对象，写入数据
	FirstMemberInitiationStoredTicketPresentsEntity storedTicketPresentsEntity=new FirstMemberInitiationStoredTicketPresentsEntity();
	storedTicketPresentsEntity.setStrStoredTicketPresentsId(strStoredTicketPresentsId);
	storedTicketPresentsEntity.setIstoredValuePresents(iStoredValuePresents);
	storedTicketPresentsEntity.setItotalStoredTicketNum(iTotalStoredTicketNum);
	storedTicketPresentsEntity.setIrestStoredTicketNum(iRestStoredTicketNum);
	storedTicketPresentsEntity.setStrValidateBeginTime(strValidateBeginTime);
	storedTicketPresentsEntity.setStrValidateEndTime(strValidateEndTime);
	storedTicketPresentsEntity.setIEnabled(iEnabled);
	storedTicketPresentsEntity.setStrEmployeeId(strEmployeeId);
	storedTicketPresentsEntity.setStrEmployeeName(strEmployeeName);
	storedTicketPresentsEntity.setStrEmployeeRealName(strEmployeeRealName);	
	storedTicketPresentsEntity.setStrLastAccessedTime(strLastAccessedTime);
	
	try{
		return firstMemberInitiationPresentsService.updateStoredTicketPresentsInfo(storedTicketPresentsEntity);
	}catch(Exception e)
	{
		e.printStackTrace();
		return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误",null);
		
	}		
	
}
	
	/**
	 * 删除 首次入会赠送储值信息
	 * @param response
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("deleteStoredTicketPresentsInfo")
	//localhost:8083/admin/biz/presentsSetting/deleteStoredTicketPresentsInfo?strStoredTicketPresentsId=fb33e4a47ca24bbc82e23e6c9a214591
	public String deleteStoredTicketPresentsInfo(HttpServletResponse response,HttpServletRequest request)
	{
		String strStoredTicketPresentsId=request.getParameter("strStoredTicketPresentsId");
		if(ValidateTool.isEmptyStr(strStoredTicketPresentsId))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"ID值不能为空",null);
		
		/* 检验身份有效性
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
			
			return firstMemberInitiationPresentsService.deleteStoredTicketPresentsInfo(strStoredTicketPresentsId);
			
		}catch(Exception e)
		{
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误",null);
		}
	}
	
	
	
	/**
	 * 新增 首次入会赠送抵用卷信息
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("insertVoucherTicketPresentsInfo")
	//localhost:8083/admin/biz/presentsSetting/insertVoucherTicketPresentsInfo?strVoucherTicketKindId=0010&iTotalVoucherTicketNum=50&iEnabled=1
	public String insertVoucherTicketPresentsInfo(HttpServletResponse response,HttpServletRequest request)
	{	
		int iTotalVoucherTicketNum,iRestVoucherTicketNum,iEnabled;
		String strVoucherTicketPresentsId=DataTool.getUUID();
		String strVoucherTicketKindId=request.getParameter("strVoucherTicketKindId");
		String strTotalVoucherTicketNum=request.getParameter("iTotalVoucherTicketNum");
		//新增时，默认剩余张数=总张数
		String strEnabled=request.getParameter("iEnabled");
		
		if(ValidateTool.isEmptyStr(strVoucherTicketKindId))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"抵用卷种类不能为空",null);
		
		if(ValidateTool.isEmptyStr(strTotalVoucherTicketNum))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"总张数不能为空",null);
		else
		{
			if(isNumber(strTotalVoucherTicketNum))
				iTotalVoucherTicketNum=Integer.parseInt(strTotalVoucherTicketNum);
			else
				return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"总张数格式错误",null);
		}
			
		iRestVoucherTicketNum=iTotalVoucherTicketNum;	//新增规则时，默认剩余张数=总张数
		
		if(ValidateTool.isEmptyStr(strEnabled))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"启用状态不能为空",null);
		else
		{
			if(isNumber(strEnabled))
				iEnabled=Integer.parseInt(strEnabled);
			else
				return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"启用状态格式错误",null);
		}
		
		/* 检验身份有效性
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
		String strCreationTime=DateTool.DateToString(new Date(),DateStyle.YYYY_MM_DD_HH_MM);
		String strLastAccessedTime=DateTool.DateToString(new Date(),DateStyle.YYYY_MM_DD_HH_MM);
		//以下3个为测试用数据
		String strEmployeeId=DataTool.getUUID();
		String strEmployeeName="admin";
		String strEmployeeRealName="david li";
		
	//建立实体对象，装入数据
		FirstMemberInitiationVoucherTicketPresentsEntity voucherTicketPresentsEntity=new FirstMemberInitiationVoucherTicketPresentsEntity();
		voucherTicketPresentsEntity.setStrVoucherTicketPresentsId(strVoucherTicketPresentsId);
		voucherTicketPresentsEntity.setStrVoucherTicketKindId(strVoucherTicketKindId);
		voucherTicketPresentsEntity.setItotalVoucherTicketNum(iTotalVoucherTicketNum);
		voucherTicketPresentsEntity.setIrestVoucherTicketNum(iRestVoucherTicketNum);
		voucherTicketPresentsEntity.setIEnabled(iEnabled);
		voucherTicketPresentsEntity.setStrEmployeeId(strEmployeeId);
		voucherTicketPresentsEntity.setStrEmployeeName(strEmployeeName);
		voucherTicketPresentsEntity.setStrEmployeeRealName(strEmployeeRealName);
		voucherTicketPresentsEntity.setStrCreationTime(strCreationTime);
		voucherTicketPresentsEntity.setStrLastAccessedTime(strLastAccessedTime);
	//调用serivice层方法
		try{
			return firstMemberInitiationPresentsService.insertVoucherTicketPresentsInfo(voucherTicketPresentsEntity);
			
		}catch(Exception e)
		{
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
		}
	}
	
	
	
	/**
	 * 查询  首次入会赠送抵用卷信息
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("selectVoucherTicketPresentsInfo")
	//localhost:8083/admin/biz/presentsSetting/selectVoucherTicketPresentsInfo
	public String selectVoucherTicketPresentsInfo(HttpServletResponse response,HttpServletRequest request)
	{
		/* 检验身份有效性
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
			List<FirstMemberInitiationVoucherTicketPresentsEntity> listVoucherTicketPresentsEntity=firstMemberInitiationPresentsService.selectVoucherTicketPresentsInfo();
			if(listVoucherTicketPresentsEntity.size()==0||listVoucherTicketPresentsEntity==null)
				return DataTool.constructResponse(ResultCode.NO_DATA,"暂无数据据",null);
			else
			{
				Map<String,Object> resultMap=new HashMap<String,Object>();
				resultMap.put("listVoucherTicketPresentsEntity", listVoucherTicketPresentsEntity);
				return DataTool.constructResponse(ResultCode.OK,"查询成功",resultMap);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
		}
	}
	
	
	
	/**
	 * 批量更新  首次入会赠送抵用卷信息
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("updateVoucherTicketPresentsInfo")
	//localhost:8083/admin/biz/presentsSetting/updateVoucherTicketPresentsInfo?strVoucherTicketPresentsId=b7f65e7c255947a7997c1139c3e1cc5e&strVoucherTicketKindId=1010&iTotalVoucherTicketNum=5&iEnabled=1
	public String updateVoucherTicketPresentsInfo(HttpServletResponse response,HttpServletRequest request)
	{
	int iEachParamLength;
	String[] strVoucherTicketPresentsId=request.getParameter("strVoucherTicketPresentsId").split(",");
	String[] strVoucherTicketKindId=request.getParameter("strVoucherTicketKindId").split(",");
	String[] strTotalVoucherTicketNum=request.getParameter("iTotalVoucherTicketNum").split(",");
	String[] strEnabled=request.getParameter("iEnabled").split(",");
	iEachParamLength=strVoucherTicketPresentsId.length;
	int[] iTotalVoucherTicketNum=new int[iEachParamLength];
	int[] iRestVoucherTicketNum=new int[iEachParamLength];
	int[] iEnabled=new int[iEachParamLength];
	if(iEachParamLength!=strVoucherTicketKindId.length||iEachParamLength!=strTotalVoucherTicketNum.length||iEachParamLength!=strEnabled.length)
		return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"参数值缺失",null);
	for(int i=0;i<iEachParamLength;i++)
	{
		if(ValidateTool.isEmptyStr(strVoucherTicketKindId[i]))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"抵用卷种类不能为空",null);
	}
	
	for(int i=0;i<iEachParamLength;i++)
	{
		if(ValidateTool.isEmptyStr(strTotalVoucherTicketNum[i]))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"总张数不能为空",null);
		else
		{
			if(isNumber(strTotalVoucherTicketNum[i]))
				{
				iTotalVoucherTicketNum[i]=Integer.parseInt(strTotalVoucherTicketNum[i]);
				iRestVoucherTicketNum[i]=iTotalVoucherTicketNum[i];	//新增规则时，默认剩余张数=总张数
				}
			else
				return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"总张数格式错误",null);
		}
	}	
	

	for(int i=0;i<iEachParamLength;i++)
	{
		if(ValidateTool.isEmptyStr(strEnabled[i]))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"启用状态不能为空",null);
		else
		{
			if(isNumber(strEnabled[i]))
				iEnabled[i]=Integer.parseInt(strEnabled[i]);
			else
				return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"启用状态格式错误",null);
		}
	}
	
	/* 检验身份有效性
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

	String strLastAccessedTime=DateTool.DateToString(new Date(),DateStyle.YYYY_MM_DD_HH_MM);
	//以下3个为测试用数据
	String strEmployeeId=DataTool.getUUID();
	String strEmployeeName="admin";
	String strEmployeeRealName="david li";
	
	//建立实体对象，装入数据
	List <FirstMemberInitiationVoucherTicketPresentsEntity> listVoucherTicketPresentsEntity=new ArrayList<FirstMemberInitiationVoucherTicketPresentsEntity>();
	for(int i=0;i<iEachParamLength;i++)
	{
	FirstMemberInitiationVoucherTicketPresentsEntity voucherTicketPresentsEntity=new FirstMemberInitiationVoucherTicketPresentsEntity();
	voucherTicketPresentsEntity.setStrVoucherTicketPresentsId(strVoucherTicketPresentsId[i]);
	voucherTicketPresentsEntity.setStrVoucherTicketKindId(strVoucherTicketKindId[i]);
	voucherTicketPresentsEntity.setItotalVoucherTicketNum(iTotalVoucherTicketNum[i]);
	voucherTicketPresentsEntity.setIrestVoucherTicketNum(iRestVoucherTicketNum[i]);
	voucherTicketPresentsEntity.setIEnabled(iEnabled[i]);
	voucherTicketPresentsEntity.setStrEmployeeId(strEmployeeId);
	voucherTicketPresentsEntity.setStrEmployeeName(strEmployeeName);
	voucherTicketPresentsEntity.setStrEmployeeRealName(strEmployeeRealName);
	voucherTicketPresentsEntity.setStrLastAccessedTime(strLastAccessedTime);
	listVoucherTicketPresentsEntity.add(voucherTicketPresentsEntity);
	}
//调用serivice层方法
	try{
		return firstMemberInitiationPresentsService.updateVoucherTicketPresentsInfo(listVoucherTicketPresentsEntity);
		
	}catch(Exception e)
	{
		e.printStackTrace();
		return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
	}
   }
	
	
	
	/**
	 * 删除 首次入会赠送抵用卷信息
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("deleteVoucherTicketPresentsInfo")
	//localhost:8083/admin/biz/presentsSetting/deleteVoucherTicketPresentsInfo?strVoucherTicketPresentsId=b7f65e7c255947a7997c1139c3e1cc5e
	public String deleteVoucherTicketPresentsInfo(HttpServletRequest request,HttpServletResponse response)
	{
		String strVoucherTicketPresentsId=request.getParameter("strVoucherTicketPresentsId");
		if(ValidateTool.isEmptyStr(strVoucherTicketPresentsId))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"ID关键字不能为空",null);
		/* 检验身份有效性
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
			return firstMemberInitiationPresentsService.deleteVoucherTicketPresentsInfo(strVoucherTicketPresentsId);
			
			
		}catch(Exception e)
		{
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
		}
	}
	
	
	
	/**
	 * 新增 修改 首次入会赠送抵用卷信息
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("insertAndUpdateVoucherTicketPresentsInfo")
	//批量新增:localhost:8083/admin/biz/presentsSetting/insertAndUpdateVoucherTicketPresentsInfo?stringParam=0,0009,1000,1|0,0010,1000,0
	//批量更新:localhost:8083/admin/biz/presentsSetting/insertAndUpdateVoucherTicketPresentsInfo?stringParam=31879bcc64f54ccaa4f6e9fd64101593,0009,1111,1|e078cc3125894ceda4226fecc38a24f0,0010,1111,0
	public String insertAndUpdateVoucherTicketPresentsInfo(HttpServletRequest request,HttpServletResponse response)
	{
		//和前台约定新增时strVoucherTicketPresentsId=0,所有数据都存放在stringParam里
		//stringParam内容格式 ：记录1ID,字段1，字段2，|记录2ID，字段1，字段2
		String[] entityAttributes;	//每第记录的所有字段集合
		String strVoucherTicketKindId;
		String strTotalVoucherTicketNum;
		String strVoucherTicketPresentsId;
		String strEnabled;
		int iTotalVoucherTicketNum=0;
		int iRestVoucherTicketNum=0;
		int iEnabled=0;
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
		String strCreationTime=DateTool.DateToString(new Date(),DateStyle.YYYY_MM_DD_HH_MM);
		String strLastAccessedTime=DateTool.DateToString(new Date(),DateStyle.YYYY_MM_DD_HH_MM);
		//以下3个为测试用数据
		String strEmployeeId=DataTool.getUUID();
		String strEmployeeName="admin";
		String strEmployeeRealName="david li";
		
		String stringParam=request.getParameter("stringParam");
		if(ValidateTool.isEmptyStr(stringParam))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"参数不能为空",null);
		String[] entitys=stringParam.split("\\|");
		if(entitys!=null)
		{
			int flag=1;

			List<FirstMemberInitiationVoucherTicketPresentsEntity> insertVoucherTicketPresentsEntityList=new ArrayList<FirstMemberInitiationVoucherTicketPresentsEntity>();
			List<FirstMemberInitiationVoucherTicketPresentsEntity> updateVoucherTicketPresentsEntityList=new ArrayList<FirstMemberInitiationVoucherTicketPresentsEntity>();
			for(int i=0;i<entitys.length;i++)
			{	
				FirstMemberInitiationVoucherTicketPresentsEntity insertVoucherTicketPresentsEntity=new FirstMemberInitiationVoucherTicketPresentsEntity();
				FirstMemberInitiationVoucherTicketPresentsEntity updateVoucherTicketPresentsEntity=new FirstMemberInitiationVoucherTicketPresentsEntity();
				entityAttributes=entitys[i].split(",");		//取出每第记录
				//取出每第条记录的各个属性
				
					if(ValidateTool.isNull(entityAttributes)||entityAttributes.length!=4)
					{
						flag=0;
						break;
					}
					strVoucherTicketPresentsId=entityAttributes[0];
					strVoucherTicketKindId=entityAttributes[1];
					strTotalVoucherTicketNum=entityAttributes[2];
					strEnabled=entityAttributes[3];
					//对取得数据进行检验和转换
					if(isNumber(strTotalVoucherTicketNum))
						iTotalVoucherTicketNum=Integer.parseInt(strTotalVoucherTicketNum);
					else
						return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"总张数格式错误",null);
					iRestVoucherTicketNum=iTotalVoucherTicketNum;
					
					if(isNumber(strEnabled))
						iEnabled=Integer.parseInt(strEnabled);
					else
						return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"启用状态格式错误",null);
					
					//判断是新增还是修改
					if("0".equals(strVoucherTicketPresentsId))	//新增
					{
						strVoucherTicketPresentsId=DataTool.getUUID();
						insertVoucherTicketPresentsEntity.setStrVoucherTicketPresentsId(strVoucherTicketPresentsId);
						insertVoucherTicketPresentsEntity.setStrVoucherTicketKindId(strVoucherTicketKindId);
						insertVoucherTicketPresentsEntity.setItotalVoucherTicketNum(iTotalVoucherTicketNum);
						insertVoucherTicketPresentsEntity.setIrestVoucherTicketNum(iRestVoucherTicketNum);
						insertVoucherTicketPresentsEntity.setIEnabled(iEnabled);
						insertVoucherTicketPresentsEntity.setStrEmployeeId(strEmployeeId);
						insertVoucherTicketPresentsEntity.setStrEmployeeName(strEmployeeName);
						insertVoucherTicketPresentsEntity.setStrEmployeeRealName(strEmployeeRealName);
						insertVoucherTicketPresentsEntity.setStrCreationTime(strCreationTime);
						insertVoucherTicketPresentsEntity.setStrLastAccessedTime(strLastAccessedTime);
						insertVoucherTicketPresentsEntityList.add(insertVoucherTicketPresentsEntity);
					}
					else
					{
						updateVoucherTicketPresentsEntity.setStrVoucherTicketPresentsId(strVoucherTicketPresentsId);
						updateVoucherTicketPresentsEntity.setStrVoucherTicketKindId(strVoucherTicketKindId);
						updateVoucherTicketPresentsEntity.setItotalVoucherTicketNum(iTotalVoucherTicketNum);
						updateVoucherTicketPresentsEntity.setIrestVoucherTicketNum(iRestVoucherTicketNum);
						updateVoucherTicketPresentsEntity.setIEnabled(iEnabled);
						updateVoucherTicketPresentsEntity.setStrEmployeeId(strEmployeeId);
						updateVoucherTicketPresentsEntity.setStrEmployeeName(strEmployeeName);
						updateVoucherTicketPresentsEntity.setStrEmployeeRealName(strEmployeeRealName);
						updateVoucherTicketPresentsEntity.setStrLastAccessedTime(strLastAccessedTime);
						updateVoucherTicketPresentsEntityList.add(updateVoucherTicketPresentsEntity);
						
					}
				}
			if(flag==0)
				return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"参数格式错误",null);
			try{
				
				return firstMemberInitiationPresentsService.insertAndUpdateVoucherTicketPresentsInfo(insertVoucherTicketPresentsEntityList,updateVoucherTicketPresentsEntityList);
			
			}catch(Exception e)
			{
				e.printStackTrace();
				return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
			}
		}
		else
			{
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"参数不能为空",null);
			}
	}
	
	
	
	//校验
		public static boolean isNumber(String strCheckString)
		{
			boolean flag=false;
			Pattern p1 = Pattern.compile("^[0-9]\\d*$");
			Matcher matcher=p1.matcher(strCheckString);
			flag=matcher.matches();   
			return flag;
		}	

}
