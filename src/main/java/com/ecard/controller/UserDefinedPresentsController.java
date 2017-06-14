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
import com.ecard.entity.UserDefinedPresentsActivityEntity;
import com.ecard.entity.UserDefinedPresentsIntegrationEntity;
import com.ecard.entity.UserDefinedPresentsStoredValueEntity;
import com.ecard.entity.UserDefinedPresentsVoucherEntity;
import com.ecard.service.UserDefinedPresentsService;
import com.ecard.util.WebSessionUtil;

@Controller
@RequestMapping("/admin/UserDefinedPresentsSetting")
public class UserDefinedPresentsController
{
	@Autowired
	private WebSessionUtil webSessionUtil;
	@Autowired
	private UserDefinedPresentsService userDefinedPresentsService;
	/**
	 * 新增 优惠活动详细信息
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("insertUserDefinedPresentsActivityInfo")
	//http://localhost:8083/admin/UserDefinedPresentsSetting/insertUserDefinedPresentsActivityInfo?strActivityName=十周年店庆&strLevelsId=10000&strActivityBeginTime=2017/5/27&strActivityEndTime=2017/5/27
	public String insertUserDefinedPresentsActivityInfo(HttpServletRequest request,HttpServletResponse response)
	{
		String strActivityId=DataTool.getUUID();
		String strActivityName=request.getParameter("strActivityName");
		String strLevelsId=request.getParameter("strLevelsId");
		String strActivityBeginTime=request.getParameter("strActivityBeginTime");
		String strActivityEndTime=request.getParameter("strActivityEndTime");
		int iActivityKinds=0; // 自定义活动类型 
		
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
		
		/*身份检测
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
		
		UserDefinedPresentsActivityEntity userDefinedPresentsActivityEntity=new UserDefinedPresentsActivityEntity();
		userDefinedPresentsActivityEntity.setStrActivityId(strActivityId);
		userDefinedPresentsActivityEntity.setStrActivityName(strActivityName);
		userDefinedPresentsActivityEntity.setStrLevelsId(strLevelsId);
		userDefinedPresentsActivityEntity.setStrActivityBeginTime(strActivityBeginTime);
		userDefinedPresentsActivityEntity.setStrActivityEndTime(strActivityEndTime);
		userDefinedPresentsActivityEntity.setiActivityKinds(iActivityKinds);
		userDefinedPresentsActivityEntity.setStrEmployeeId(strEmployeeId);
		userDefinedPresentsActivityEntity.setStrEmployeeName(strEmployeeName);
		userDefinedPresentsActivityEntity.setStrEmployeeRealName(strEmployeeRealName);
		userDefinedPresentsActivityEntity.setStrCreationTime(strCreationTime);
		userDefinedPresentsActivityEntity.setStrLastAccessedTime(strLastAccessedTime);
		try{
			return userDefinedPresentsService.insertUserDefinedPresentsActivityInfo(userDefinedPresentsActivityEntity);
		}catch(Exception e)
		{
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
		}
		
	}

	
	
	/**
	 * 更新 自定义活动详细信息
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("updateUserDefinedPresentsActivityInfo")
	//http://localhost:8083/admin/UserDefinedPresentsSetting/updateUserDefinedPresentsActivityInfo?strActivityId=a2b1adf79dd0436f8d28ee6c043d712c&strActivityName=this is a test&strLevelsId=1&strActivityBeginTime=2017/5/27&strActivityEndTime=2017/5/27
	public String updateUserDefinedPresentsActivityInfo(HttpServletRequest request,HttpServletResponse response)
	{
		String strActivityId=request.getParameter("strActivityId");
		String strActivityName=request.getParameter("strActivityName");
		String strLevelsId=request.getParameter("strLevelsId");
		String strActivityBeginTime=request.getParameter("strActivityBeginTime");
		String strActivityEndTime=request.getParameter("strActivityEndTime");
		int iActivityKinds=0; // 自定义活动类型 
		
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
		
		UserDefinedPresentsActivityEntity userDefinedPresentsActivityEntity=new UserDefinedPresentsActivityEntity();
		userDefinedPresentsActivityEntity.setStrActivityId(strActivityId);
		userDefinedPresentsActivityEntity.setStrActivityName(strActivityName);
		userDefinedPresentsActivityEntity.setStrLevelsId(strLevelsId);
		userDefinedPresentsActivityEntity.setStrActivityBeginTime(strActivityBeginTime);
		userDefinedPresentsActivityEntity.setStrActivityEndTime(strActivityEndTime);
		userDefinedPresentsActivityEntity.setiActivityKinds(iActivityKinds);
		userDefinedPresentsActivityEntity.setStrEmployeeId(strEmployeeId);
		userDefinedPresentsActivityEntity.setStrEmployeeName(strEmployeeName);
		userDefinedPresentsActivityEntity.setStrEmployeeRealName(strEmployeeRealName);
		userDefinedPresentsActivityEntity.setStrLastAccessedTime(strLastAccessedTime);
		try{
		return userDefinedPresentsService.updateUserDefinedPresentsActivityInfo(userDefinedPresentsActivityEntity);
		}catch(Exception e)
		{
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
		}
		
	}
	
	
	/**
	 * 查询 自定义活动详细信息 单条
	 * @param request
	 * @param response
	 * @return
	 */

	@ResponseBody
	@RequestMapping("selectUserDefinedPresentActivityById")
	//http://localhost:8083/admin/UserDefinedPresentsSetting/selectUserDefinedPresentActivityById?strActivityId=4e47aadafcbf4177a9d81a1c2e4006a9
	public String selectUserDefinedPresentActivityById(HttpServletResponse response,HttpServletRequest request)
	{
		
		/*
		 * 检验身份有效性
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
		
		UserDefinedPresentsActivityEntity userDefinedPresentsActivityEntity=new UserDefinedPresentsActivityEntity();
		String strActivityId=request.getParameter("strActivityId");
		if(ValidateTool.isEmptyStr("strActivityId"))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"Id不能为空",null);
		try{
			userDefinedPresentsActivityEntity=userDefinedPresentsService.selectUserDefinedPresentActivityById(strActivityId);
			if(userDefinedPresentsActivityEntity==null)
				return DataTool.constructResponse(ResultCode.NO_DATA,"暂无数据",null);
			Map<String,Object> resultMap=new HashMap<String,Object>();
			resultMap.put("rechargePresentsActivityEntity",userDefinedPresentsActivityEntity);
			return DataTool.constructResponse(ResultCode.OK,"查询成功",resultMap);
			
		}catch(Exception e)
		{
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
		}
		
		
	}
	/**
	 * 新增 自定义活动赠送积分信息
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("insertUserDefinedPresentsIntegration")
	//http://localhost:8083/admin/UserDefinedPresentsSetting/insertUserDefinedPresentsIntegration?strActivityId=a2b1adf79dd0436f8d28ee6c043d712c&iPresentsIntegration=100&iEnabled=1
	public String insertUserDefinedPresentsIntegration(HttpServletRequest request,HttpServletResponse response)
	{
		int iPresentsIntegration,iEnabled;
		String strPresentsIntegrationId=DataTool.getUUID();
		String strActivityId=request.getParameter("strActivityId");
		String strPresentsIntegration=request.getParameter("iPresentsIntegration");
		String strEnabled=request.getParameter("iEnabled");
	
		if(ValidateTool.isEmptyStr(strActivityId))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "活动ID不能为空", null);
		
		if(ValidateTool.isEmptyStr(strPresentsIntegration))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "赠送积分数量不能为空", null);
		else
		{
			if(isNumber(strPresentsIntegration))
				iPresentsIntegration=Integer.parseInt(strPresentsIntegration);
			else
				return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR, "赠送积分数量格式错误", null);
		}
		if(ValidateTool.isEmptyStr(strEnabled))
			return  DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "启用不能为空", null);
		else
		{
			if(isNumber(strEnabled))
				iEnabled=Integer.parseInt(strEnabled);
			else
				return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR, "启用状态格式错误", null);
		}
		
		String strCreationTime=DateTool.DateToString(new Date(),DateStyle.YYYY_MM_DD_HH_MM);
		String strLastAccessedTime=DateTool.DateToString(new Date(),DateStyle.YYYY_MM_DD_HH_MM);
		
		//身份检测
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
		UserDefinedPresentsIntegrationEntity userDefinedPresentsIntegrationEntity=new UserDefinedPresentsIntegrationEntity();
		userDefinedPresentsIntegrationEntity.setStrPresentsIntegrationId(strPresentsIntegrationId);
		userDefinedPresentsIntegrationEntity.setStrActivityId(strActivityId);
		userDefinedPresentsIntegrationEntity.setiPresentsIntegration(iPresentsIntegration);
		userDefinedPresentsIntegrationEntity.setiEnabled(iEnabled);
		userDefinedPresentsIntegrationEntity.setStrEmployeeId(strEmployeeId);
		userDefinedPresentsIntegrationEntity.setStrEmployeeName(strEmployeeName);
		userDefinedPresentsIntegrationEntity.setStrEmployeeRealName(strEmployeeRealName);
		userDefinedPresentsIntegrationEntity.setStrCreationTime(strCreationTime);
		userDefinedPresentsIntegrationEntity.setStrLastAccessedTime(strLastAccessedTime);
		try{
			return userDefinedPresentsService.insertUserDefinedPresentsIntegration(userDefinedPresentsIntegrationEntity);
		}catch(Exception e)
		{
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
		}
		
   }
	
	/**
	 * 修改 自定义活动赠送积分信息
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("updateUserDefinedPresentsIntegration")
	//http://localhost:8083/admin/UserDefinedPresentsSetting/updateUserDefinedPresentsIntegration?strPresentsIntegrationId=704c9e7d0c074aacb016f8f8b6da023b&strActivityId=a2b1adf79dd0436f8d28ee6c043d712c&iPresentsIntegration=1&iEnabled=1
	public String updateUserDefinedPresentsIntegration(HttpServletRequest request,HttpServletResponse response)
	{
		int iPresentsIntegration,iEnabled;
		String strPresentsIntegrationId=request.getParameter("strPresentsIntegrationId");
		String strActivityId=request.getParameter("strActivityId");
		String strPresentsIntegration=request.getParameter("iPresentsIntegration");
		String strEnabled=request.getParameter("iEnabled");
		if(ValidateTool.isEmptyStr(strPresentsIntegrationId))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "赠送积分关键字不能为空", null);
	
		if(ValidateTool.isEmptyStr(strActivityId))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "活动ID不能为空", null);
		
		if(ValidateTool.isEmptyStr(strPresentsIntegration))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "赠送积分数量不能为空", null);
		else
		{
			if(isNumber(strPresentsIntegration))
				iPresentsIntegration=Integer.parseInt(strPresentsIntegration);
			else
				return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR, "赠送积分数量格式错误", null);
		}
		if(ValidateTool.isEmptyStr(strEnabled))
			return  DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "启用不能为空", null);
		else
		{
			if(isNumber(strEnabled))
				iEnabled=Integer.parseInt(strEnabled);
			else
				return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR, "启用状态格式错误", null);
		}
		
		String strLastAccessedTime=DateTool.DateToString(new Date(),DateStyle.YYYY_MM_DD_HH_MM);
		
		//身份检测
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
		UserDefinedPresentsIntegrationEntity userDefinedPresentsIntegrationEntity=new UserDefinedPresentsIntegrationEntity();
		userDefinedPresentsIntegrationEntity.setStrPresentsIntegrationId(strPresentsIntegrationId);
		userDefinedPresentsIntegrationEntity.setStrActivityId(strActivityId);
		userDefinedPresentsIntegrationEntity.setiPresentsIntegration(iPresentsIntegration);
		userDefinedPresentsIntegrationEntity.setiEnabled(iEnabled);
		userDefinedPresentsIntegrationEntity.setStrEmployeeId(strEmployeeId);
		userDefinedPresentsIntegrationEntity.setStrEmployeeName(strEmployeeName);
		userDefinedPresentsIntegrationEntity.setStrEmployeeRealName(strEmployeeRealName);
		userDefinedPresentsIntegrationEntity.setStrLastAccessedTime(strLastAccessedTime);
		try{
			return userDefinedPresentsService.updateUserDefinedPresentsIntegration(userDefinedPresentsIntegrationEntity);
		}catch(Exception e)
		{
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
		}
		
   }
	
	/**
	 * 查询 自定义活动赠送积分 单条
	 * @param request
	 * @param response
	 * @return
	 */

	@ResponseBody
	@RequestMapping("selectAllUserDefinedPresentsIntegration")
	//http://localhost:8083/admin/UserDefinedPresentsSetting/selectAllUserDefinedPresentsIntegration?strActivityId=4e47aadafcbf4177a9d81a1c2e4006a9
	public String selectAllUserDefinedPresentsIntegration(HttpServletResponse response,HttpServletRequest request)
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
		UserDefinedPresentsIntegrationEntity userDefinedPresentsIntegrationEntity=null;
		String strActivityId=request.getParameter("strActivityId");
		if(ValidateTool.isEmptyStr("strActivityId"))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"活动Id不能为空",null);
		try{
			userDefinedPresentsIntegrationEntity=userDefinedPresentsService.selectAllUserDefinedPresentsIntegration(strActivityId);
			if(userDefinedPresentsIntegrationEntity==null)
				return DataTool.constructResponse(ResultCode.NO_DATA,"暂无数据",null);
			Map<String,Object> resultMap=new HashMap<String,Object>();
			resultMap.put("uerDefinedPresentsStoredValueEntity",userDefinedPresentsIntegrationEntity);
			return DataTool.constructResponse(ResultCode.OK,"查询成功",resultMap);
			
		}catch(Exception e)
		{
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
		}
		
	}
	
	
	/**
	 * 删除 自定义活动赠送积分信息[分页显示删除时应用]
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("deleteUserDefinedPresentsIntegrationEntity")
	//http://localhost:8083/admin/UserDefinedPresentsSetting/deleteUserDefinedPresentsIntegrationEntity?strPresentsIntegrationId=704c9e7d0c074aacb016f8f8b6da023b
	public String deleteUserDefinedPresentsIntegrationEntity(HttpServletRequest request,HttpServletResponse response)
	{
		//身份检测
		/*
		 * 
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
		String strPresentsIntegrationId=request.getParameter("strPresentsIntegrationId");
		if(ValidateTool.isEmptyStr(strPresentsIntegrationId))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"删除关键字不能为空",null);
		
		try{
			return userDefinedPresentsService.deleteUserDefinedPresentsIntegrationEntity(strPresentsIntegrationId);
		
		}catch(Exception e)
		{
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
		}
	}
	

	//新增一条自定义活动赠送储值信息UserDefinedPresentsStoredValueEntity记录
	@ResponseBody
	@RequestMapping("insertUserDefinedPresentsStoredValueEntity")
	//http://localhost:8083/admin/UserDefinedPresentsSetting/insertUserDefinedPresentsStoredValueEntity?strActivityId=4e47aadafcbf4177a9d81a1c2e4006a9&dPresentsAmount=600&iEnabled=1
	public String insertUserDefinedPresentsStoredValueEntity(HttpServletRequest request, HttpServletResponse response)
	{

	    //获取传入参数
		BigDecimal bgPresentsAmount=null;
		int iEnabled=0;
	    String strPresentsStoredValueId=DataTool.getUUID();
	    String strActivityId = request.getParameter("strActivityId");
	    String dPresentsAmount = request.getParameter("dPresentsAmount");
	    String strEnabled = request.getParameter("iEnabled");
	    //判断参数有效性
	    if(strActivityId == null || strActivityId.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "活动ID不能为空", null);
	    }
	    if(dPresentsAmount == null || dPresentsAmount.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "积分赠送量不能为空", null);
	        
	    }
	    if(isNumber(dPresentsAmount))
	    	bgPresentsAmount=new BigDecimal(Double.parseDouble(dPresentsAmount));
	    
	    if(strEnabled == null || strEnabled.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "是否启用不能为空", null);
	    }
	    
	    if(isNumber(strEnabled))
	    	iEnabled=Integer.parseInt(strEnabled);
	    else
	    	 return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR, "启用状态格式错误", null);
	    
	    String strLastAccessedTime=DateTool.DateToString(new Date(),DateStyle.YYYY_MM_DD_HH_MM);
	    String strCreationTime=DateTool.DateToString(new Date(),DateStyle.YYYY_MM_DD_HH_MM);
		
		//身份检测
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

	    //对象设置
	    UserDefinedPresentsStoredValueEntity tUserDefinedPresentsStoredValueEntity=new UserDefinedPresentsStoredValueEntity();
	    tUserDefinedPresentsStoredValueEntity.setStrPresentsStoredValueId(strPresentsStoredValueId);
	    tUserDefinedPresentsStoredValueEntity.setStrActivityId(strActivityId);
	    tUserDefinedPresentsStoredValueEntity.setdPresentsAmount(bgPresentsAmount);
	    tUserDefinedPresentsStoredValueEntity.setiEnabled(iEnabled);
	    tUserDefinedPresentsStoredValueEntity.setStrEmployeeId(strEmployeeId);
	    tUserDefinedPresentsStoredValueEntity.setStrEmployeeName(strEmployeeName);
	    tUserDefinedPresentsStoredValueEntity.setStrEmployeeRealName(strEmployeeRealName);
	    tUserDefinedPresentsStoredValueEntity.setStrCreationTime(strCreationTime);
	    tUserDefinedPresentsStoredValueEntity.setStrLastAccessedTime(strLastAccessedTime);
	    try{
	        return userDefinedPresentsService.insertUserDefinedPresentsStoredValueEntity(tUserDefinedPresentsStoredValueEntity);
	    }
	    catch(Exception e)
	    {
	        e.printStackTrace();
	        return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "新增失败", null);
	    }
	}


	//更新一条自定义赠送储值信息UserDefinedPresentsStoredValueEntity记录
	@ResponseBody
	@RequestMapping("updateUserDefinedPresentsStoredValueEntity")
	//http://localhost:8083/admin/UserDefinedPresentsSetting/updateUserDefinedPresentsStoredValueEntity?strPresentsStoredValueId=626603e102e141a8b2a3a6bc7cf688f1&strActivityId=4e47aadafcbf4177a9d81a1c2e4006a9&dPresentsAmount=400&iEnabled=1
	public String updateUserDefinedPresentsStoredValueEntity(HttpServletRequest request, HttpServletResponse response)
	{

	    String strPresentsStoredValueId = request.getParameter("strPresentsStoredValueId");
	    String strActivityId = request.getParameter("strActivityId");
	    String dPresentsAmount = request.getParameter("dPresentsAmount");
	    String strEnabled = request.getParameter("iEnabled");
	    int iEnabled=0;
	    BigDecimal bgPresentsAmount=null;

	    if(strPresentsStoredValueId == null || strPresentsStoredValueId.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数赠送储值ID不能为空", null);
	    }
	    

	    if(strActivityId == null || strActivityId.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数活动ID不能为空", null);
	    }
	    

	    if(dPresentsAmount == null || dPresentsAmount.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数赠送金额不能为空", null);
	    }
	    
	    if(isNumber(dPresentsAmount))
	    	 bgPresentsAmount=new BigDecimal(Double.parseDouble(dPresentsAmount));
	    else
	    	return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR, "赠送金额格式错误", null);

	    if(strEnabled == null || strEnabled.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数启用状态不能为空", null);
	    }
	    
	    if(isNumber(strEnabled))
	    	 iEnabled=Integer.parseInt(strEnabled);
	    else
	    	return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR, "是否启用格式错误", null);
	    
	    String strLastAccessedTime=DateTool.DateToString(new Date(),DateStyle.YYYY_MM_DD_HH_MM);
	    //身份检测
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
	    UserDefinedPresentsStoredValueEntity userDefinedPresentsStoredValueEntity=new UserDefinedPresentsStoredValueEntity();
	    userDefinedPresentsStoredValueEntity.setStrPresentsStoredValueId(strPresentsStoredValueId);
	    userDefinedPresentsStoredValueEntity.setStrActivityId(strActivityId);
	    userDefinedPresentsStoredValueEntity.setdPresentsAmount(bgPresentsAmount);
	    userDefinedPresentsStoredValueEntity.setiEnabled(iEnabled);
	    userDefinedPresentsStoredValueEntity.setStrEmployeeId(strEmployeeId);
	    userDefinedPresentsStoredValueEntity.setStrEmployeeName(strEmployeeName);
	    userDefinedPresentsStoredValueEntity.setStrEmployeeRealName(strEmployeeRealName);
	    userDefinedPresentsStoredValueEntity.setStrLastAccessedTime(strLastAccessedTime);
	    try{
	        return userDefinedPresentsService.updateUserDefinedPresentsStoredValueEntity(userDefinedPresentsStoredValueEntity);
	    }
	    catch(Exception e)
	    {
	        e.printStackTrace();
	        return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "更新失败", null);
	    }
	}
	

	//删除一条自定义活动赠送储值UserDefinedPresentsStoredValueEntity记录
	@ResponseBody
	@RequestMapping("deleteUserDefinedPresentsStoredValueEntity")
	//http://localhost:8083/admin/UserDefinedPresentsSetting/deleteUserDefinedPresentsStoredValueEntity?strPresentsStoredValueId=626603e102e141a8b2a3a6bc7cf688f1
	public String deleteUserDefinedPresentsStoredValueEntity(HttpServletRequest request, HttpServletResponse response)
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
  		if (employeeEntity==null) {
  			return DataTool.constructResponse(ResultCode.NO_DATA, "操作员不存在", null);
  		}
  		*/
	    String strPresentsStoredValueId = request.getParameter("strPresentsStoredValueId");
	    if(strPresentsStoredValueId == null || strPresentsStoredValueId.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "主键不能为空", null);
	    }
	    try{
	        return userDefinedPresentsService.deleteUserDefinedPresentsStoredValueEntity(strPresentsStoredValueId);
	    }
	    catch(Exception e)
	    {
	        e.printStackTrace();
	        return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "删除失败", null);
	    }
	}
	
	
	/**
	 * 查询 自定义活动赠送储值 单条
	 * @param request
	 * @param response
	 * @return
	 */

	@ResponseBody
	@RequestMapping("selectAllUserDefinedPresentsStoredValueEntity")
	//http://localhost:8083/admin/UserDefinedPresentsSetting/selectAllUserDefinedPresentsStoredValueEntity?strActivityId=4e47aadafcbf4177a9d81a1c2e4006a9
	public String selectAllUserDefinedPresentsStoredValueEntity(HttpServletResponse response,HttpServletRequest request)
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
		UserDefinedPresentsStoredValueEntity uerDefinedPresentsStoredValueEntity=null;
		String strActivityId=request.getParameter("strActivityId");
		if(ValidateTool.isEmptyStr("strActivityId"))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"活动Id不能为空",null);
		try{
			uerDefinedPresentsStoredValueEntity=userDefinedPresentsService.selectAllUserDefinedPresentsStoredValueEntity(strActivityId);
			if(uerDefinedPresentsStoredValueEntity==null)
				return DataTool.constructResponse(ResultCode.NO_DATA,"暂无数据",null);
			Map<String,Object> resultMap=new HashMap<String,Object>();
			resultMap.put("uerDefinedPresentsStoredValueEntity", uerDefinedPresentsStoredValueEntity);
			return DataTool.constructResponse(ResultCode.OK,"查询成功",resultMap);
			
		}catch(Exception e)
		{
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
		}
		
	}
		
	//批量插入自定义活动赠送抵用券信息userDefinedPresentsVoucherEntity记录
	@ResponseBody
	@RequestMapping("batchInsertUserDefinedPresentsVoucherEntity")
	//http://localhost:8083/admin/UserDefinedPresentsSetting/batchInsertUserDefinedPresentsVoucherEntity?strVoucherTicketId=27064b6a0ccc4c12baa28472be0e75d1,51507b9d157d45deb83cbeef07e2647f&strActivityId=4e47aadafcbf4177a9d81a1c2e4006a9,9d139dbd9c364193b6cd1e5782e0770c&iTotalNum=100,50&iEnabled=1,1
	public String batchInsertUserDefinedPresentsVoucherEntity(HttpServletResponse response,HttpServletRequest request)
	{

		//声明数组
		int iArrayLength;	//定义数组长度
		int[] iTotalNumArray;
		int[] iRestNumArray;
		int[] iEnabledArray;
		String[] strVoucherTicketIdArray;
		String[] strActivityIdArray;
		String[] strTotalNumArray;

		String[] strEnabledArray;

		//获取参数
		String strVoucherTicketId=request.getParameter("strVoucherTicketId");
		String strActivityId=request.getParameter("strActivityId");
		String strTotalNum=request.getParameter("iTotalNum");
		String strEnabled=request.getParameter("iEnabled");
		//检测字符串有效性

		if(ValidateTool.isEmptyStr(strVoucherTicketId))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "关联抵用券ID不能为空", null);
		if(ValidateTool.isEmptyStr(strActivityId))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "关联活动ID不能为空", null);
		if(ValidateTool.isEmptyStr(strTotalNum))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "总张数不能为空", null);
		if(ValidateTool.isEmptyStr(strEnabled))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "是否启用不能为空", null);

		//将字符串折成数组
		strVoucherTicketIdArray=strVoucherTicketId.split(",");
		strActivityIdArray=strActivityId.split(",");
		strTotalNumArray=strTotalNum.split(",");
		strEnabledArray=strEnabled.split(",");
		
		iArrayLength=strVoucherTicketIdArray.length;
		if(iArrayLength!=strActivityIdArray.length||iArrayLength!=strTotalNumArray.length||iArrayLength!=strEnabledArray.length)
			return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR, "数据有遗漏", null);

		iTotalNumArray=new int[iArrayLength];
		iRestNumArray=new int[iArrayLength];
		iEnabledArray=new int[iArrayLength];
		for(int i=0;i<iArrayLength;i++)
		{
			if(isNumber(strTotalNumArray[i]))
			{
				iTotalNumArray[i]=Integer.parseInt(strTotalNumArray[i]);
				iRestNumArray[i]=iTotalNumArray[i];
				
			}
			else
				return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR, "总张数格式错误", null);
			
		}
		
		
		for(int i=0;i<iArrayLength;i++)
		{
			if(isNumber(strEnabledArray[i]))
				iEnabledArray[i]=Integer.parseInt(strEnabledArray[i]);
			else
				return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR, "启用状态格式错误", null);
			
		}
		
		String strCreationTime=DateTool.DateToString(new Date(),DateStyle.YYYY_MM_DD_HH_MM);
		String strLastAccessedTime=DateTool.DateToString(new Date(),DateStyle.YYYY_MM_DD_HH_MM);

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
		//取得登录员工信息
		/*String strEmployeeId=employeeEntity.getStrEmployeeid();
		String strEmployeeName=employeeEntity.getStrLoginname();
		String strEmployeeRealName=employeeEntity.getStrRealname();
		*/

		//以下3个为测试用数据

		String strEmployeeId=DataTool.getUUID();
		String strEmployeeName="test";
		String strEmployeeRealName="test";


		//属性组装进对象
		List<UserDefinedPresentsVoucherEntity> listUserDefinedPresentsVoucherEntity=new ArrayList<UserDefinedPresentsVoucherEntity>();
		for(int i=0;i<iArrayLength;i++)
		{
		UserDefinedPresentsVoucherEntity userDefinedPresentsVoucherEntity=new UserDefinedPresentsVoucherEntity();
		userDefinedPresentsVoucherEntity.setStrPresentsVoucherId(DataTool.getUUID());
		userDefinedPresentsVoucherEntity.setStrVoucherTicketId(strVoucherTicketIdArray[i]);
		userDefinedPresentsVoucherEntity.setStrActivityId(strActivityIdArray[i]);
		userDefinedPresentsVoucherEntity.setiTotalNum(iTotalNumArray[i]);
		userDefinedPresentsVoucherEntity.setiRestNum(iRestNumArray[i]);
		userDefinedPresentsVoucherEntity.setiEnabled(iEnabledArray[i]);
		userDefinedPresentsVoucherEntity.setStrEmployeeId(strEmployeeId);
		userDefinedPresentsVoucherEntity.setStrEmployeeName(strEmployeeName);
		userDefinedPresentsVoucherEntity.setStrEmployeeRealName(strEmployeeRealName);
		userDefinedPresentsVoucherEntity.setStrCreationTime(strCreationTime);
		userDefinedPresentsVoucherEntity.setStrLastAccessedTime(strLastAccessedTime);
		listUserDefinedPresentsVoucherEntity.add(userDefinedPresentsVoucherEntity);
		}
		try{
			return userDefinedPresentsService.batchInsertUserDefinedPresentsVoucherEntity(listUserDefinedPresentsVoucherEntity);
		}
		catch(Exception e)
		{	e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
		}
	}

	//批量更新自定义活动赠送抵用券信息userDefinedPresentsVoucherEntity记录
	@ResponseBody
	@RequestMapping("batchUpdateUserDefinedPresentsVoucherEntity")
	//http://localhost:8083/admin/UserDefinedPresentsSetting/batchUpdateUserDefinedPresentsVoucherEntity?strPresentsVoucherId=1f13429431264fd38ed743325f669163,303e51e59d53490491705fe3b2002146&strVoucherTicketId=27064b6a0ccc4c12baa28472be0e75d1,51507b9d157d45deb83cbeef07e2647f&strActivityId=4e47aadafcbf4177a9d81a1c2e4006a9,9d139dbd9c364193b6cd1e5782e0770c&iTotalNum=500,500&iEnabled=0,0
	public String batchUpdateUserDefinedPresentsVoucherEntity(HttpServletResponse response,HttpServletRequest request)
	{
		//声明数组
		int iArrayLength;	//定义数组长度
		int[] iTotalNumArray;
		int[] iRestNumArray;
		int[] iEnabledArray;
		String[] strVoucherTicketIdArray;
		String[] strActivityIdArray;
		String[] strTotalNumArray;
		String[] strPresentsVoucherIdArray;
		String[] strEnabledArray;

		//获取参数
		String strVoucherTicketId=request.getParameter("strVoucherTicketId");
		String strActivityId=request.getParameter("strActivityId");
		String strTotalNum=request.getParameter("iTotalNum");
		String strEnabled=request.getParameter("iEnabled");
		String strPresentsVoucherId=request.getParameter("strPresentsVoucherId");
		//检测字符串有效性
		if(ValidateTool.isEmptyStr(strPresentsVoucherId))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "修改记录关键字不能为空", null);
		if(ValidateTool.isEmptyStr(strVoucherTicketId))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "关联抵用券ID不能为空", null);
		if(ValidateTool.isEmptyStr(strActivityId))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "关联活动ID不能为空", null);
		if(ValidateTool.isEmptyStr(strTotalNum))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "总张数不能为空", null);
		if(ValidateTool.isEmptyStr(strEnabled))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "是否启用不能为空", null);

		//将字符串折成数组
		strPresentsVoucherIdArray=strPresentsVoucherId.split(",");
		strVoucherTicketIdArray=strVoucherTicketId.split(",");
		strActivityIdArray=strActivityId.split(",");
		strTotalNumArray=strTotalNum.split(",");
		strEnabledArray=strEnabled.split(",");
				
		iArrayLength=strVoucherTicketIdArray.length;
		if(iArrayLength!=strActivityIdArray.length||iArrayLength!=strTotalNumArray.length||iArrayLength!=strEnabledArray.length||iArrayLength!=strPresentsVoucherIdArray.length)
			return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR, "数据有遗漏", null);

		iTotalNumArray=new int[iArrayLength];
		iRestNumArray=new int[iArrayLength];
		iEnabledArray=new int[iArrayLength];
		for(int i=0;i<iArrayLength;i++)
		{
			if(isNumber(strTotalNumArray[i]))
			{
				iTotalNumArray[i]=Integer.parseInt(strTotalNumArray[i]);
				iRestNumArray[i]=iTotalNumArray[i];
						
			}
			else
				return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR, "总张数格式错误", null);
					
		}
				
				
		for(int i=0;i<iArrayLength;i++)
		{
			if(isNumber(strEnabledArray[i]))
				iEnabledArray[i]=Integer.parseInt(strEnabledArray[i]);
			else
				return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR, "是否启用式错误", null);
					
		}
				
		String strLastAccessedTime=DateTool.DateToString(new Date(),DateStyle.YYYY_MM_DD_HH_MM);

	//身份检测
		/*
		EmployeeEntity employeeEntity = null;
		try {
			employeeEntity=(EmployeeEntity)webSessionUtil.getWebSession(request, response).getAttribute("employeeEntity");
		} catch (Exception e) {
					e.printStackTrace();
					return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
				}
		if(employeeEntity==null)
		{
			return DataTool.constructResponse(ResultCode.NO_DATA, "操作员不存在", null);
		}
		*/
		//取得登录员工信息
		/*
		String strEmployeeId=employeeEntity.getStrEmployeeid();
		String strEmployeeName=employeeEntity.getStrLoginname();
		String strEmployeeRealName=employeeEntity.getStrRealname();
		*/

		//以下3个为测试用数据

		String strEmployeeId=DataTool.getUUID();
		String strEmployeeName="test";
		String strEmployeeRealName="test";


		//属性组装进对象
		List<UserDefinedPresentsVoucherEntity> listUserDefinedPresentsVoucherEntity=new ArrayList<UserDefinedPresentsVoucherEntity>();
		for(int i=0;i<iArrayLength;i++)
		{
			UserDefinedPresentsVoucherEntity userDefinedPresentsVoucherEntity=new UserDefinedPresentsVoucherEntity();
			userDefinedPresentsVoucherEntity.setStrPresentsVoucherId(strPresentsVoucherIdArray[i]);
			userDefinedPresentsVoucherEntity.setStrVoucherTicketId(strVoucherTicketIdArray[i]);
			userDefinedPresentsVoucherEntity.setStrActivityId(strActivityIdArray[i]);
			userDefinedPresentsVoucherEntity.setiTotalNum(iTotalNumArray[i]);
			userDefinedPresentsVoucherEntity.setiRestNum(iRestNumArray[i]);
			userDefinedPresentsVoucherEntity.setiEnabled(iEnabledArray[i]);
			userDefinedPresentsVoucherEntity.setStrEmployeeId(strEmployeeId);
			userDefinedPresentsVoucherEntity.setStrEmployeeName(strEmployeeName);
			userDefinedPresentsVoucherEntity.setStrEmployeeRealName(strEmployeeRealName);
			userDefinedPresentsVoucherEntity.setStrLastAccessedTime(strLastAccessedTime);
			listUserDefinedPresentsVoucherEntity.add(userDefinedPresentsVoucherEntity);
		}
		try{
			return userDefinedPresentsService.batchUpdateUserDefinedPresentsVoucherEntity(listUserDefinedPresentsVoucherEntity);
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
			}
	}	

	
	//查询自定义赠送抵用券信息UserDefinedPresentsVoucherEntity列表
	@ResponseBody
	@RequestMapping("selectAllUserDefinedPresentsVoucherEntity")
	//http://localhost:8083/admin/UserDefinedPresentsSetting/selectAllUserDefinedPresentsVoucherEntity
	public String selectAllUserDefinedPresentsVoucherEntity(HttpServletRequest request,HttpServletResponse response)
	{
		String strActivityId=request.getParameter("strActivityId");
		if(ValidateTool.isEmptyStr(strActivityId))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "查询记录关键字不能为空", null);

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
	try{
		List<UserDefinedPresentsVoucherEntity> listUserDefinedPresentsVoucherEntity=userDefinedPresentsService.selectAllUserDefinedPresentsVoucherEntity(strActivityId);
		if(ValidateTool.isNull(listUserDefinedPresentsVoucherEntity))
			return DataTool.constructResponse(ResultCode.NO_DATA,"暂无数据",null);
		if(listUserDefinedPresentsVoucherEntity.size()==0)
			return DataTool.constructResponse(ResultCode.NO_DATA,"暂无数据",null);
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("listUserDefinedPresentsVoucherEntity",listUserDefinedPresentsVoucherEntity);
		return DataTool.constructResponse(ResultCode.OK,"查询成功",resultMap);
		
	}catch(Exception e)
	{
		e.printStackTrace();
		return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"查询失败",null);
	}
  }
	
	
	//删除一条自定义赠送抵用券信息userDefinedPresentsVoucherEntity记录
	@ResponseBody
	@RequestMapping("deleteUserDefinedPresentsVoucherEntity")
	//http://localhost:8083/admin/UserDefinedPresentsSetting/deleteUserDefinedPresentsVoucherEntity?strPresentsVoucherId=1f13429431264fd38ed743325f669163
	public String deleteUserDefinedPresentsVoucherEntity(HttpServletRequest request, HttpServletResponse response)
	{
	String strPresentsVoucherId=request.getParameter("strPresentsVoucherId");
	if(ValidateTool.isEmptyStr(strPresentsVoucherId))
		return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "删除关键字不能为空",null);
	

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
	try{
		return userDefinedPresentsService.deleteUserDefinedPresentsVoucherEntity(strPresentsVoucherId);
	}
	catch(Exception e)
	{
		e.printStackTrace();
		return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"删除失败", null);
	}
  }

	

	/**
	 *  分页查询 自定义赠送活动信息
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("selectAllUserDefinedPresentsActivity")
	//http://localhost:8083/admin/UserDefinedPresentsSetting/selectAllUserDefinedPresentsActivity?iPageNum=1&iPageSize=1
 	public String selectAllUserDefinedPresentsActivity(HttpServletRequest request,HttpServletResponse response)
	
	{
		//取得搜索字段
		//strSearchEnabledStatus=活动状态：接收参数值  All:(或为 空值)表全部,为0 表过期:EXPIRED，1正常:NORMAL
		//strSearchMemberLevel=接收参数值:ALL(或空值) 表示全部会员级别
		int iPageNum,iPageSize,iTotalPage,iTotalRecord=0,iPageFrom;
		String strSearchMemberLevelId=request.getParameter("strSearchMemberLevelId");
		String strSearchEnabledStatus=request.getParameter("strSearchEnabledStatus");
		String strPageNum=request.getParameter("iPageNum");
		String strPageSize=request.getParameter("iPageSize");
		String strCurrentDate=DateTool.DateToString(new Date(),DateStyle.YYYY_MM_DD);
		
		if(strSearchMemberLevelId==null||"".equals(strSearchMemberLevelId.trim()))
			strSearchMemberLevelId="";
		
		if("ALL".equals(strSearchMemberLevelId))
			strSearchMemberLevelId="";
		
		if(strSearchEnabledStatus==null||"".equals(strSearchEnabledStatus.trim()))
			strSearchEnabledStatus="ALL";
		
		if("0".equals(strSearchEnabledStatus))
			strSearchEnabledStatus="EXPIRED";
		
		if("1".equals(strSearchEnabledStatus))
			strSearchEnabledStatus="NORMAL";
		
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
			queryMap.put("strSearchMemberLevelId", strSearchMemberLevelId);
			queryMap.put("strSearchEnabledStatus",strSearchEnabledStatus);
			queryMap.put("strCurrentDate", strCurrentDate);
			iTotalRecord=userDefinedPresentsService.findTheRecordCount(queryMap);

			if(iTotalRecord!=0)
			{
				List<UserDefinedPresentsActivityEntity> listUserDefinedPresentsActivityEntity=null;
				iTotalPage=(iTotalRecord%iPageSize==0?iTotalRecord/iPageSize:iTotalRecord/iPageSize+1);
				if(iPageNum>iTotalPage)
					iPageNum=iTotalPage;
				iPageFrom=(iPageNum-1)*iPageSize;
				queryMap.put("iPageFrom",iPageFrom);
				queryMap.put("iPageSize",iPageSize);
				
				listUserDefinedPresentsActivityEntity=userDefinedPresentsService.selectAllUserDefinedPresentsActivity(queryMap);
				if(listUserDefinedPresentsActivityEntity==null||listUserDefinedPresentsActivityEntity.size()==0)
					return DataTool.constructResponse(ResultCode.NO_DATA,"暂无数据",null);
				Map<String,Object> resultMap=new HashMap<String,Object>();
				resultMap.put("iTotalRecord",iTotalRecord);
				resultMap.put("iTotalPage",iTotalPage);
				resultMap.put("resultMap",listUserDefinedPresentsActivityEntity);
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
	 

		//查询一条自定义赠送活动信息rechargePresentsActivityEntity
		@ResponseBody
		@RequestMapping("selectUserDefinedPresentsActivityEntity")
		//localhost:8083/admin/UserDefinedPresentsSetting/selectUserDefinedPresentsActivityEntity
		public String selectUserDefinedPresentsActivityEntity(HttpServletResponse response,HttpServletRequest request)
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

			UserDefinedPresentsActivityEntity userDefinedPresentsActivityEntity=null;
		try{
			userDefinedPresentsActivityEntity=userDefinedPresentsService.selectUserDefinedPresentsActivityEntity();
			if(userDefinedPresentsActivityEntity==null)
			return DataTool.constructResponse(ResultCode.NO_DATA,"暂无数据",null);
			Map<String,Object> resultMap=new HashMap<String,Object>();
			resultMap.put("userDefinedPresentsActivityEntity",userDefinedPresentsActivityEntity);
			return DataTool.constructResponse(ResultCode.OK,"查询成功",resultMap);
		}catch(Exception e)
		{
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
		}
	}

		
	//删除除活动，及活动所关联的积分，储值信息
	@ResponseBody
	@RequestMapping("deleteUserDefinedActivityInfo")
	//localhost:8083/admin/UserDefinedPresentsSetting/deleteUserDefinedActivityInfo?strActivityId=tyurhh445556
	public String deleteUserDefinedActivityInfo(HttpServletRequest request,HttpServletResponse response)
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
			return userDefinedPresentsService.deleteUserDefinedPresentsActivityInfo(strActivityId);
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
