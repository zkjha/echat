package com.ecard.controller;

import java.util.Date;
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
import com.ecard.entity.ConsumePresentsActivityEntity;
import com.ecard.entity.EmployeeEntity;
import com.ecard.service.ConsumePresentsService;
import com.ecard.util.WebSessionUtil;

@Controller

@RequestMapping("/admin/consumePresentsSetting")
public class ConsumePresentsController
{
	@Autowired
	private ConsumePresentsService consumePresentsService;
	@Autowired
	private WebSessionUtil webSessionUtil;
	//新增一条consumePresentsActivityEntity记录
	@ResponseBody
	@RequestMapping("insertConsumePresentsActivityEntity")
	//http://localhost:8083/admin/consumePresentsSetting/insertConsumePresentsActivityEntity?strActivityName=十周年店庆&strLevelsId=1&strActivityBeginTime=2017-4-30&strActivityEndTime=2018-4-30&iActivityKinds=1&strIsCumulation=1
	public String insertConsumePresentsActivityEntity(HttpServletResponse response,HttpServletRequest request)
	{

	//获取参数
	String strActivityId=DataTool.getUUID();
	String strActivityName=request.getParameter("strActivityName");
	String strLevelsId=request.getParameter("strLevelsId");
	String strActivityBeginTime=request.getParameter("strActivityBeginTime");
	String strActivityEndTime=request.getParameter("strActivityEndTime");
	String iActivityKinds=request.getParameter("iActivityKinds");
	String strIsCumulation=request.getParameter("strIsCumulation");

	//检测字符串有效性

	if(ValidateTool.isEmptyStr(strActivityName))
		return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "活动名称不能为空", null);
	if(ValidateTool.isEmptyStr(strLevelsId))
		return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "会员级别ID不能为空", null);
	
	if(ValidateTool.isEmptyStr(strActivityBeginTime))
		return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "活动开始时间不能为空", null);
	strActivityBeginTime=DateTool.StringToString(strActivityBeginTime,DateStyle.YYYY_MM_DD);
	
	if(ValidateTool.isEmptyStr(strActivityEndTime))
		return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "活动截止时间不能为空", null);
	strActivityEndTime=DateTool.StringToString(strActivityEndTime,DateStyle.YYYY_MM_DD);
	
	if(ValidateTool.isEmptyStr(iActivityKinds))
		return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "活动类型不能为空", null);
	if(!isNumber(iActivityKinds))
		return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR, "活动类型格式错误", null);
	
	if(ValidateTool.isEmptyStr(strIsCumulation))
		return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "单笔消费是否累计不能为空", null);
	if(!isNumber(strIsCumulation))
		return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR, "单笔消费是否累计格式错误", null);
	
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
	ConsumePresentsActivityEntity consumePresentsActivityEntity=new ConsumePresentsActivityEntity();
	consumePresentsActivityEntity.setStrActivityId(strActivityId);
	consumePresentsActivityEntity.setStrActivityName(strActivityName);
	consumePresentsActivityEntity.setStrLevelsId(strLevelsId);
	consumePresentsActivityEntity.setStrActivityBeginTime(strActivityBeginTime);
	consumePresentsActivityEntity.setStrActivityEndTime(strActivityEndTime);
	consumePresentsActivityEntity.setiActivityKinds(Integer.parseInt(iActivityKinds));
	consumePresentsActivityEntity.setStrIsCumulation(strIsCumulation);
	consumePresentsActivityEntity.setStrEmployeeId(strEmployeeId);
	consumePresentsActivityEntity.setStrEmployeeName(strEmployeeName);
	consumePresentsActivityEntity.setStrEmployeeRealName(strEmployeeRealName);
	consumePresentsActivityEntity.setStrCreationTime(strCreationTime);
	consumePresentsActivityEntity.setStrLastAccessedTime(strLastAccessedTime);

	try{
		return consumePresentsService.insertConsumePresentsActivityEntity(consumePresentsActivityEntity);
	}
	catch(Exception e)
	{	e.printStackTrace();
		return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
	}
  }


	

	
	@ResponseBody
	@RequestMapping("updateConsumePresentsActivityEntity")
	//http://localhost:8083/admin/consumePresentsSetting/updateConsumePresentsActivityEntity?strActivityName=十周年店庆&strLevelsId=1&strActivityBeginTime=2017-4-30&strActivityEndTime=2018-4-30&iActivityKinds=1&strIsCumulation=1
	public String updateConsumePresentsActivityEntity(HttpServletResponse response,HttpServletRequest request)
	{

	//获取参数
	String strActivityId=request.getParameter("strActivityId");
	String strActivityName=request.getParameter("strActivityName");
	String strLevelsId=request.getParameter("strLevelsId");
	String strActivityBeginTime=request.getParameter("strActivityBeginTime");
	String strActivityEndTime=request.getParameter("strActivityEndTime");
	String iActivityKinds=request.getParameter("iActivityKinds");
	String strIsCumulation=request.getParameter("strIsCumulation");

	//检测字符串有效性
	if(ValidateTool.isEmptyStr(strActivityId))
		return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "活动ID不能为空", null);

	if(ValidateTool.isEmptyStr(strActivityName))
		return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "活动名称不能为空", null);
	if(ValidateTool.isEmptyStr(strLevelsId))
		return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "会员级别ID不能为空", null);
	
	if(ValidateTool.isEmptyStr(strActivityBeginTime))
		return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "活动开始时间不能为空", null);
	strActivityBeginTime=DateTool.StringToString(strActivityBeginTime,DateStyle.YYYY_MM_DD);
	
	if(ValidateTool.isEmptyStr(strActivityEndTime))
		return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "活动截止时间不能为空", null);
	strActivityEndTime=DateTool.StringToString(strActivityEndTime,DateStyle.YYYY_MM_DD);
	
	if(ValidateTool.isEmptyStr(iActivityKinds))
		return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "活动类型不能为空", null);
	if(!isNumber(iActivityKinds))
		return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR, "活动类型格式错误", null);
	
	if(ValidateTool.isEmptyStr(strIsCumulation))
		return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "单笔消费是否累计不能为空", null);
	if(!isNumber(strIsCumulation))
		return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR, "单笔消费是否累计格式错误", null);
	
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
	ConsumePresentsActivityEntity consumePresentsActivityEntity=new ConsumePresentsActivityEntity();
	consumePresentsActivityEntity.setStrActivityId(strActivityId);
	consumePresentsActivityEntity.setStrActivityName(strActivityName);
	consumePresentsActivityEntity.setStrLevelsId(strLevelsId);
	consumePresentsActivityEntity.setStrActivityBeginTime(strActivityBeginTime);
	consumePresentsActivityEntity.setStrActivityEndTime(strActivityEndTime);
	consumePresentsActivityEntity.setiActivityKinds(Integer.parseInt(iActivityKinds));
	consumePresentsActivityEntity.setStrIsCumulation(strIsCumulation);
	consumePresentsActivityEntity.setStrEmployeeId(strEmployeeId);
	consumePresentsActivityEntity.setStrEmployeeName(strEmployeeName);
	consumePresentsActivityEntity.setStrEmployeeRealName(strEmployeeRealName);
	consumePresentsActivityEntity.setStrLastAccessedTime(strLastAccessedTime);

	try{
		return consumePresentsService.updateConsumePresentsActivityEntity(consumePresentsActivityEntity);
	}
	catch(Exception e)
	{	e.printStackTrace();
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
