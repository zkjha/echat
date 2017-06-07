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
import com.ecard.entity.ConsumePresentsActivityEntity;
import com.ecard.entity.ConsumePresentsIntegrationEntity;
import com.ecard.entity.ConsumePresentsStoredValueEntity;
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


	
	//查询一条消费赠送活动规则信息consumePresentsActivityEntity
	@ResponseBody
	@RequestMapping("selectConsumePresentsActivityEntity")
	//http://localhost:8083/admin/consumePresentsSetting/selectConsumePresentsActivityEntity?strActivityId=3b3139856a4743e48e711cd416336aab
	public String selectConsumePresentsActivityEntity(HttpServletResponse response,HttpServletRequest request)
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

	String strActivityId=request.getParameter("strActivityId");
	if(ValidateTool.isEmptyStr("strActivityId"))
		return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"参数strActivityId不能为空",null);
	ConsumePresentsActivityEntity consumePresentsActivityEntity=null;
	try{
		consumePresentsActivityEntity=consumePresentsService.selectConsumePresentsActivityEntity(strActivityId);
		if(consumePresentsActivityEntity==null)
		return DataTool.constructResponse(ResultCode.NO_DATA,"暂无数据",null);
		Map<String,Object> resultMap=new HashMap<String,Object>();
		resultMap.put("consumePresentsActivityEntity",consumePresentsActivityEntity);
		return DataTool.constructResponse(ResultCode.OK,"查询成功",resultMap);
	}catch(Exception e)
	{
		e.printStackTrace();
		return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
	}
	}
	
	//查询一条刚新建的消费赠送活动规则信息consumePresentsActivityEntity	
	@ResponseBody
	@RequestMapping("selectConsumePresentsActivityInfo")
	//http://localhost:8083/admin/consumePresentsSetting/selectConsumePresentsActivityInfo
	public String selectConsumePresentsActivityInfo(HttpServletResponse response,HttpServletRequest request)
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

	ConsumePresentsActivityEntity consumePresentsActivityEntity=null;
	try{
		consumePresentsActivityEntity=consumePresentsService.selectConsumePresentsActivityInfo();
		if(consumePresentsActivityEntity==null)
		return DataTool.constructResponse(ResultCode.NO_DATA,"暂无数据",null);
		Map<String,Object> resultMap=new HashMap<String,Object>();
		resultMap.put("consumePresentsActivityEntity",consumePresentsActivityEntity);
		return DataTool.constructResponse(ResultCode.OK,"查询成功",resultMap);
	}catch(Exception e)
	{
		e.printStackTrace();
		return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
	}
	}

	
	//批量插入消费赠送积分规则信息consumePresentsIntegrationEntity记录
	@ResponseBody
	@RequestMapping("batchInsertConsumePresentsIntegrationEntity")
	//http://localhost:8083/admin/consumePresentsSetting/batchInsertConsumePresentsIntegrationEntity?strActivityId=1,1&dConsumeCashAmount=200,400&iPresentsIntegrationAmount=50,150&iEnabled=1,1
	public String batchInsertConsumePresentsIntegrationEntity(HttpServletResponse response,HttpServletRequest request)
	{
	//声明数组
	int[] iPresentsIntegrationAmountArray;
	int[] iEnabledArray;
	
	BigDecimal[] bgConsumeCashAmountArray;
	
	String[] strActivityIdArray;
	String[] strConsumeCashAmountArray;
	String[] strPresentsIntegrationAmountArray;
	String[] strEnabledArray;

	//获取参数
	String strActivityId=request.getParameter("strActivityId");
	String strConsumeCashAmount=request.getParameter("dConsumeCashAmount");
	String strPresentsIntegrationAmount=request.getParameter("iPresentsIntegrationAmount");
	String strEnabled=request.getParameter("iEnabled");
	//检测字符串有效性

	if(ValidateTool.isEmptyStr(strActivityId))
		return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "活动ID不能为空", null);
	
	if(ValidateTool.isEmptyStr(strConsumeCashAmount))
		return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "消费金额不能为空", null);
	
	if(ValidateTool.isEmptyStr(strPresentsIntegrationAmount))
		return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "赠送积分数量不能为空", null);
	if(ValidateTool.isEmptyStr(strEnabled))
		return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "是否启用不能为空", null);
	//将字符串折成数组
	strActivityIdArray=strActivityId.split(",");
	strConsumeCashAmountArray=strConsumeCashAmount.split(",");
	strPresentsIntegrationAmountArray=strPresentsIntegrationAmount.split(",");
	strEnabledArray=strEnabled.split(",");
	
	int iArrayLength=strEnabledArray.length;
	if(iArrayLength!=strPresentsIntegrationAmountArray.length||iArrayLength!=strConsumeCashAmountArray.length||iArrayLength!=strActivityIdArray.length)
		return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数值有遗漏", null);
	
	bgConsumeCashAmountArray=new BigDecimal[iArrayLength];
	for(int i=0;i<iArrayLength;i++)
	{
		if(isNumber(strConsumeCashAmountArray[i]))
			bgConsumeCashAmountArray[i]=new BigDecimal(Double.parseDouble(strConsumeCashAmountArray[i]));
		else
			return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR, "消费金额格式错误", null);
	}
	
	iEnabledArray=new int[iArrayLength];
	for(int i=0;i<iArrayLength;i++)
	{
		if(isNumber(strEnabledArray[i]))
			iEnabledArray[i]=Integer.parseInt((strEnabledArray[i]));
		else
			return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR, "消费金额格式错误", null);
	}
	
	iPresentsIntegrationAmountArray=new int[iArrayLength];
	for(int i=0;i<iArrayLength;i++)
	{
		if(isNumber(strPresentsIntegrationAmountArray[i]))
			iPresentsIntegrationAmountArray[i]=Integer.parseInt((strPresentsIntegrationAmountArray[i]));
		else
			return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR, "赠送积分数量格式错误", null);
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
	List<ConsumePresentsIntegrationEntity> listConsumePresentsIntegrationEntity=new ArrayList<ConsumePresentsIntegrationEntity>();
	for(int i=0;i<iArrayLength;i++)
	{
	ConsumePresentsIntegrationEntity consumePresentsIntegrationEntity=new ConsumePresentsIntegrationEntity();
	consumePresentsIntegrationEntity.setStrIntegrationId(DataTool.getUUID());
	consumePresentsIntegrationEntity.setStrActivityId(strActivityIdArray[i]);
	consumePresentsIntegrationEntity.setdConsumeCashAmount(bgConsumeCashAmountArray[i]);
	consumePresentsIntegrationEntity.setiPresentsIntegrationAmount(iPresentsIntegrationAmountArray[i]);
	consumePresentsIntegrationEntity.setiEnabled(iEnabledArray[i]);
	consumePresentsIntegrationEntity.setStrEmployeeId(strEmployeeId);
	consumePresentsIntegrationEntity.setStrEmployeeName(strEmployeeName);
	consumePresentsIntegrationEntity.setStrEmployeeRealName(strEmployeeRealName);
	consumePresentsIntegrationEntity.setStrCreationTime(strCreationTime);
	consumePresentsIntegrationEntity.setStrLastAccessedTime(strLastAccessedTime);
	listConsumePresentsIntegrationEntity.add(consumePresentsIntegrationEntity);
	}
	try{
		return consumePresentsService.batchInsertConsumePresentsIntegrationEntity(listConsumePresentsIntegrationEntity);
	}
	catch(Exception e)
	{	e.printStackTrace();
		return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
	}
  }

	//批量更新消费赠送积分信息
	@ResponseBody
	@RequestMapping("batchUpdateConsumePresentsIntegrationEntity")
	//http://localhost:8083/admin/consumePresentsSetting/batchUpdateConsumePresentsIntegrationEntity?strIntegrationId=394273d8d8c84ca4bfdbd73214a3609d,6a85bec3b28f4e46b2bcb25249a8b882&strActivityId=3b3139856a4743e48e711cd416336aab,606d2822dcf24024bfa850f58948bbc3&dConsumeCashAmount=500,500&iPresentsIntegrationAmount=10,10&iEnabled=0,0
	public String batchUpdateConsumePresentsIntegrationEntity(HttpServletResponse response,HttpServletRequest request)
	{
	//声明数组
	int[] iPresentsIntegrationAmountArray;
	int[] iEnabledArray;
	
	BigDecimal[] bgConsumeCashAmountArray;
	String[] strIntegrationIdArray;
	String[] strActivityIdArray;
	String[] strConsumeCashAmountArray;
	String[] strPresentsIntegrationAmountArray;
	String[] strEnabledArray;

	//获取参数
	String strIntegrationId=request.getParameter("strIntegrationId");
	String strActivityId=request.getParameter("strActivityId");
	String strConsumeCashAmount=request.getParameter("dConsumeCashAmount");
	String strPresentsIntegrationAmount=request.getParameter("iPresentsIntegrationAmount");
	String strEnabled=request.getParameter("iEnabled");
	//检测字符串有效性
	if(ValidateTool.isEmptyStr(strIntegrationId))
		return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "积分ID不能为空", null);
	
	if(ValidateTool.isEmptyStr(strActivityId))
		return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "活动ID不能为空", null);
	
	if(ValidateTool.isEmptyStr(strConsumeCashAmount))
		return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "消费金额不能为空", null);
	
	if(ValidateTool.isEmptyStr(strPresentsIntegrationAmount))
		return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "赠送积分数量不能为空", null);
	if(ValidateTool.isEmptyStr(strEnabled))
		return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "是否启用不能为空", null);
	//将字符串折成数组
	strIntegrationIdArray=strIntegrationId.split(",");
	strActivityIdArray=strActivityId.split(",");
	strConsumeCashAmountArray=strConsumeCashAmount.split(",");
	strPresentsIntegrationAmountArray=strPresentsIntegrationAmount.split(",");
	strEnabledArray=strEnabled.split(",");
	
	int iArrayLength=strEnabledArray.length;
	if(iArrayLength!=strPresentsIntegrationAmountArray.length||iArrayLength!=strConsumeCashAmountArray.length||iArrayLength!=strActivityIdArray.length||strIntegrationIdArray.length!=iArrayLength)
		return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数值有遗漏", null);
	
	bgConsumeCashAmountArray=new BigDecimal[iArrayLength];
	for(int i=0;i<iArrayLength;i++)
	{
		if(isNumber(strConsumeCashAmountArray[i]))
			bgConsumeCashAmountArray[i]=new BigDecimal(Double.parseDouble(strConsumeCashAmountArray[i]));
		else
			return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR, "消费金额格式错误", null);
	}
	
	iEnabledArray=new int[iArrayLength];
	for(int i=0;i<iArrayLength;i++)
	{
		if(isNumber(strEnabledArray[i]))
			iEnabledArray[i]=Integer.parseInt((strEnabledArray[i]));
		else
			return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR, "消费金额格式错误", null);
	}
	
	iPresentsIntegrationAmountArray=new int[iArrayLength];
	for(int i=0;i<iArrayLength;i++)
	{
		if(isNumber(strPresentsIntegrationAmountArray[i]))
			iPresentsIntegrationAmountArray[i]=Integer.parseInt((strPresentsIntegrationAmountArray[i]));
		else
			return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR, "赠送积分数量格式错误", null);
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
	List<ConsumePresentsIntegrationEntity> listConsumePresentsIntegrationEntity=new ArrayList<ConsumePresentsIntegrationEntity>();
	for(int i=0;i<iArrayLength;i++)
	{
	ConsumePresentsIntegrationEntity consumePresentsIntegrationEntity=new ConsumePresentsIntegrationEntity();
	consumePresentsIntegrationEntity.setStrIntegrationId(strIntegrationIdArray[i]);
	consumePresentsIntegrationEntity.setStrActivityId(strActivityIdArray[i]);
	consumePresentsIntegrationEntity.setdConsumeCashAmount(bgConsumeCashAmountArray[i]);
	consumePresentsIntegrationEntity.setiPresentsIntegrationAmount(iPresentsIntegrationAmountArray[i]);
	consumePresentsIntegrationEntity.setiEnabled(iEnabledArray[i]);
	consumePresentsIntegrationEntity.setStrEmployeeId(strEmployeeId);
	consumePresentsIntegrationEntity.setStrEmployeeName(strEmployeeName);
	consumePresentsIntegrationEntity.setStrEmployeeRealName(strEmployeeRealName);
	consumePresentsIntegrationEntity.setStrLastAccessedTime(strLastAccessedTime);
	listConsumePresentsIntegrationEntity.add(consumePresentsIntegrationEntity);
	}
	try{
		return consumePresentsService.batchUpdateConsumePresentsIntegrationEntity(listConsumePresentsIntegrationEntity);
	}
	catch(Exception e)
	{	e.printStackTrace();
		return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
	}
  }

	
	//删除一条消费赠送积分规则信息consumePresentsIntegrationEntity记录
	@ResponseBody
	@RequestMapping("deleteConsumePresentsIntegrationEntity")
	//http://localhost:8083/admin/consumePresentsSetting/deleteConsumePresentsIntegrationEntity?strIntegrationId=394273d8d8c84ca4bfdbd73214a3609d
	public String deleteConsumePresentsIntegrationEntity(HttpServletRequest request, HttpServletResponse response)
	{
	String strIntegrationId=request.getParameter("strIntegrationId");
	if(ValidateTool.isEmptyStr(strIntegrationId))
		return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "积分ID不能为空",null);
	
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
		return consumePresentsService.deleteConsumePresentsIntegrationEntity(strIntegrationId);
	}
	catch(Exception e)
	{
		e.printStackTrace();
		return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"删除失败", null);
	}
	}


	
	//查询多条消费赠送积分规则信息consumePresentsIntegrationEntity
	@ResponseBody
	@RequestMapping("selectAllConsumePresentsIntegrationEntity")
	//http://localhost:8083/admin/consumePresentsSetting/selectAllConsumePresentsIntegrationEntity?strActivityId=1606d2822dcf24024bfa850f58948bbc3
	public String selectAllConsumePresentsIntegrationEntity(HttpServletResponse response,HttpServletRequest request)
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

	String strActivityId=request.getParameter("strActivityId");
	if(ValidateTool.isEmptyStr("strActivityId"))
		return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"活动ID不能为空",null);
	try{
		List<ConsumePresentsIntegrationEntity> listConsumePresentsIntegrationEntity=consumePresentsService.selectAllConsumePresentsIntegrationEntity(strActivityId);
		if(listConsumePresentsIntegrationEntity==null||listConsumePresentsIntegrationEntity.size()==0)
		return DataTool.constructResponse(ResultCode.NO_DATA,"暂无数据",null);
		Map<String,Object> resultMap=new HashMap<String,Object>();
		resultMap.put("listConsumePresentsIntegrationEntity",listConsumePresentsIntegrationEntity);
		return DataTool.constructResponse(ResultCode.OK,"查询成功",resultMap);
	}catch(Exception e)
	{
		e.printStackTrace();
		return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
	}
	}


	//批量插入消费赠送储值规则信息consumePresentsStoredValueEntity记录
	@ResponseBody
	@RequestMapping("batchInsertConsumePresentsStoredValueEntity")
	//http://localhost:8083/admin/consumePresentsSetting/batchInsertConsumePresentsStoredValueEntity?strActivityId=606d2822dcf24024bfa850f58948bbc3,3b3139856a4743e48e711cd416336aab&dConsumeCashAmount=300,500&iPresentsIntegrationAmount=10,10&iEnabled=1,1
	public String batchInsertConsumePresentsStoredValueEntity(HttpServletResponse response,HttpServletRequest request)
	{
		//声明数组
	int[] iPresentsIntegrationAmountArray;
	int[] iEnabledArray;
	
	BigDecimal[] bgConsumeCashAmountArray;
	
	String[] strActivityIdArray;
	String[] strConsumeCashAmountArray;
	String[] strPresentsIntegrationAmountArray;
	String[] strEnabledArray;
	//获取参数
	String strActivityId=request.getParameter("strActivityId");
	String strConsumeCashAmount=request.getParameter("dConsumeCashAmount");
	String strPresentsIntegrationAmount=request.getParameter("iPresentsIntegrationAmount");
	String strEnabled=request.getParameter("iEnabled");
	//检测字符串有效性

	if(ValidateTool.isEmptyStr(strActivityId))
		return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "关联活动ID不能为空", null);
	if(ValidateTool.isEmptyStr(strConsumeCashAmount))
		return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "消费金额不能为空", null);
	if(ValidateTool.isEmptyStr(strPresentsIntegrationAmount))
		return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "赠送积分数量不能为空", null);
	if(ValidateTool.isEmptyStr(strEnabled))
		return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "是否启用不能为空", null);


	//将字符串折成数组
	strActivityIdArray=strActivityId.split(",");
	strConsumeCashAmountArray=strConsumeCashAmount.split(",");
	strPresentsIntegrationAmountArray=strPresentsIntegrationAmount.split(",");
	strEnabledArray=strEnabled.split(",");
	int arrayLength=strEnabledArray.length;
	if(strActivityIdArray.length!=arrayLength||strConsumeCashAmountArray.length!=arrayLength||strPresentsIntegrationAmountArray.length!=arrayLength)
		return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"参数有遗漏",null);
	bgConsumeCashAmountArray=new BigDecimal[arrayLength];
	for(int i=0;i<arrayLength;i++)
	{
		if(isNumber(strConsumeCashAmountArray[i]))
			bgConsumeCashAmountArray[i]=new BigDecimal(Double.parseDouble(strConsumeCashAmountArray[i]));
		else
			return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR, "消费金额格式错误", null);
	}
	
	iPresentsIntegrationAmountArray=new int[arrayLength];
	for(int i=0;i<arrayLength;i++)
	{
		if(isNumber(strPresentsIntegrationAmountArray[i]))
			iPresentsIntegrationAmountArray[i]=Integer.parseInt(strPresentsIntegrationAmountArray[i]);
		else
			return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR, "赠送积分数量格式错误", null);
	}
	
	iEnabledArray=new int[arrayLength];
	for(int i=0;i<arrayLength;i++)
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
	List<ConsumePresentsStoredValueEntity> listConsumePresentsStoredValueEntity=new ArrayList<ConsumePresentsStoredValueEntity>();
	for(int i=0;i<arrayLength;i++)
	{
	ConsumePresentsStoredValueEntity consumePresentsStoredValueEntity=new ConsumePresentsStoredValueEntity();
	consumePresentsStoredValueEntity.setStrStoredTicketId(DataTool.getUUID());
	consumePresentsStoredValueEntity.setStrActivityId(strActivityIdArray[i]);
	consumePresentsStoredValueEntity.setdConsumeCashAmount(bgConsumeCashAmountArray[i]);
	consumePresentsStoredValueEntity.setiPresentsIntegrationAmount(iPresentsIntegrationAmountArray[i]);
	consumePresentsStoredValueEntity.setiEnabled(iEnabledArray[i]);
	consumePresentsStoredValueEntity.setStrEmployeeId(strEmployeeId);
	consumePresentsStoredValueEntity.setStrEmployeeName(strEmployeeName);
	consumePresentsStoredValueEntity.setStrEmployeeRealName(strEmployeeRealName);
	consumePresentsStoredValueEntity.setStrCreationTime(strCreationTime);
	consumePresentsStoredValueEntity.setStrLastAccessedTime(strLastAccessedTime);
	listConsumePresentsStoredValueEntity.add(consumePresentsStoredValueEntity);
	}
	try{
		return consumePresentsService.batchInsertConsumePresentsStoredValueEntity(listConsumePresentsStoredValueEntity);
	}
	catch(Exception e)
	{	e.printStackTrace();
		return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
	}
	}
	
	
	//批量更新 消费增送储值
	@ResponseBody
	@RequestMapping("batchUpdateConsumePresentsStoredValueEntity")
	//http://localhost:8083/admin/consumePresentsSetting/batchUpdateConsumePresentsStoredValueEntity
	public String batchUpdateConsumePresentsStoredValueEntity(HttpServletResponse response,HttpServletRequest request)
	{
		//声明数组
	int[] iPresentsIntegrationAmountArray;
	int[] iEnabledArray;
	
	BigDecimal[] bgConsumeCashAmountArray;
	
	String[] strActivityIdArray;
	String[] strConsumeCashAmountArray;
	String[] strPresentsIntegrationAmountArray;
	String[] strEnabledArray;
	String[] strStoredTicketIdArray;
	//获取参数
	String strStoredTicketId=request.getParameter("strStoredTicketId");
	String strActivityId=request.getParameter("strActivityId");
	String strConsumeCashAmount=request.getParameter("dConsumeCashAmount");
	String strPresentsIntegrationAmount=request.getParameter("iPresentsIntegrationAmount");
	String strEnabled=request.getParameter("iEnabled");
	//检测字符串有效性
	if(ValidateTool.isEmptyStr(strStoredTicketId))
		return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "储值ID不能为空", null);
	
	if(ValidateTool.isEmptyStr(strActivityId))
		return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "关联活动ID不能为空", null);
	if(ValidateTool.isEmptyStr(strConsumeCashAmount))
		return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "消费金额不能为空", null);
	if(ValidateTool.isEmptyStr(strPresentsIntegrationAmount))
		return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "赠送积分数量不能为空", null);
	if(ValidateTool.isEmptyStr(strEnabled))
		return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "是否启用不能为空", null);


	//将字符串折成数组
	strStoredTicketIdArray=strStoredTicketId.split(",");
	strActivityIdArray=strActivityId.split(",");
	strConsumeCashAmountArray=strConsumeCashAmount.split(",");
	strPresentsIntegrationAmountArray=strPresentsIntegrationAmount.split(",");
	strEnabledArray=strEnabled.split(",");
	int arrayLength=strEnabledArray.length;
	if(strActivityIdArray.length!=arrayLength||strConsumeCashAmountArray.length!=arrayLength||strPresentsIntegrationAmountArray.length!=arrayLength||strStoredTicketIdArray.length!=arrayLength)
		return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"参数有遗漏",null);
	bgConsumeCashAmountArray=new BigDecimal[arrayLength];
	for(int i=0;i<arrayLength;i++)
	{
		if(isNumber(strConsumeCashAmountArray[i]))
			bgConsumeCashAmountArray[i]=new BigDecimal(Double.parseDouble(strConsumeCashAmountArray[i]));
		else
			return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR, "消费金额格式错误", null);
	}
	
	iPresentsIntegrationAmountArray=new int[arrayLength];
	for(int i=0;i<arrayLength;i++)
	{
		if(isNumber(strPresentsIntegrationAmountArray[i]))
			iPresentsIntegrationAmountArray[i]=Integer.parseInt(strPresentsIntegrationAmountArray[i]);
		else
			return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR, "赠送积分数量格式错误", null);
	}
	
	iEnabledArray=new int[arrayLength];
	for(int i=0;i<arrayLength;i++)
	{
		if(isNumber(strEnabledArray[i]))
			iEnabledArray[i]=Integer.parseInt(strEnabledArray[i]);
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
	List<ConsumePresentsStoredValueEntity> listConsumePresentsStoredValueEntity=new ArrayList<ConsumePresentsStoredValueEntity>();
	for(int i=0;i<arrayLength;i++)
	{
	ConsumePresentsStoredValueEntity consumePresentsStoredValueEntity=new ConsumePresentsStoredValueEntity();
	consumePresentsStoredValueEntity.setStrStoredTicketId(strStoredTicketIdArray[i]);
	consumePresentsStoredValueEntity.setStrActivityId(strActivityIdArray[i]);
	consumePresentsStoredValueEntity.setdConsumeCashAmount(bgConsumeCashAmountArray[i]);
	consumePresentsStoredValueEntity.setiPresentsIntegrationAmount(iPresentsIntegrationAmountArray[i]);
	consumePresentsStoredValueEntity.setiEnabled(iEnabledArray[i]);
	consumePresentsStoredValueEntity.setStrEmployeeId(strEmployeeId);
	consumePresentsStoredValueEntity.setStrEmployeeName(strEmployeeName);
	consumePresentsStoredValueEntity.setStrEmployeeRealName(strEmployeeRealName);
	consumePresentsStoredValueEntity.setStrLastAccessedTime(strLastAccessedTime);
	listConsumePresentsStoredValueEntity.add(consumePresentsStoredValueEntity);
	}
	try{
		return consumePresentsService.batchUpdateConsumePresentsStoredValueEntity(listConsumePresentsStoredValueEntity);
	}
	catch(Exception e)
	{	e.printStackTrace();
		return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
	}
	}	

	
	//删除一条消费赠送储值规则信息consumePresentsStoredValueEntity记录
	@ResponseBody
	@RequestMapping("deleteConsumePresentsStoredValueEntity")
	//http://localhost:8083/admin/consumePresentsSetting/deleteConsumePresentsStoredValueEntity?strStoredTicketId=
	public String deleteConsumePresentsStoredValueEntity(HttpServletRequest request, HttpServletResponse response)
	{
	String strStoredTicketId=request.getParameter("strStoredTicketId");
	if(ValidateTool.isEmptyStr(strStoredTicketId))
		return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数不能为空",null);

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
		return consumePresentsService.deleteConsumePresentsStoredValueEntity(strStoredTicketId);
	}
	catch(Exception e)
	{
		e.printStackTrace();
		return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"删除失败", null);
	}
	}

	
	//查询消费赠送储值规则信息ConsumePresentsStoredValueEntity列表
	@ResponseBody
	@RequestMapping("selectAllConsumePresentsStoredValueEntity")
	//http://localhost:8083/admin/consumePresentsSetting/selectAllConsumePresentsStoredValueEntity?strActivityId=3b3139856a4743e48e711cd416336aab
	public String selectAllConsumePresentsStoredValueEntity(HttpServletRequest request,HttpServletResponse response)
	{
	String strActivityId=request.getParameter("strActivityId");
	if(ValidateTool.isEmptyStr(strActivityId))
		return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"活动ID不能为空",null);
	
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
		List<ConsumePresentsStoredValueEntity> listConsumePresentsStoredValueEntity=consumePresentsService.selectAllConsumePresentsStoredValueEntity(strActivityId);
		if(ValidateTool.isNull(listConsumePresentsStoredValueEntity)||listConsumePresentsStoredValueEntity.size()<=0)
			return DataTool.constructResponse(ResultCode.NO_DATA,"暂无数据",null);
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("listConsumePresentsStoredValueEntity",listConsumePresentsStoredValueEntity);
		return DataTool.constructResponse(ResultCode.OK,"查询成功",resultMap);
	
	}catch(Exception e)
	{
		e.printStackTrace();
		return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"查询失败",null);
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
