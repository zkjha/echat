package com.ecard.controller;

import java.math.BigDecimal;
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
	@RequestMapping("inserPresentsActivityInfo")
	//http://localhost:8083/admin/RechargePresentsSetting/inserPresentsActivityInfo?strActivityName=十周年店庆&strLevelsId=10000&strActivityBeginTime=2017/5/27&strActivityEndTime=2017/5/27
	public String inserPresentsActivityInfo(HttpServletRequest request,HttpServletResponse response)
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
			return userDefinedPresentsService.inserPresentsActivityInfo(userDefinedPresentsActivityEntity);
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
/*
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
	/*	
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
	*/
	
	/**
	 * 查询 充值赠送积分规则则信息
	 * @param request
	 * @param response
	 * @return
	 */
/*
	@ResponseBody
	@RequestMapping("selectAllRechargePresentsIntegration")
	//http://localhost:8083/admin/RechargePresentsSetting/selectAllRechargePresentsIntegration?strActivityId=1
	public String selectAllRechargePresentsIntegration(HttpServletRequest request,HttpServletResponse response)
	{*/
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
		/*
		String strActivityId=request.getParameter("strActivityId");
		if(ValidateTool.isEmptyStr(strActivityId))
			DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"活动关键字不能为空",null);
		try{
			List<RechargePresentsIntegrationEntity> listRechargePresentsIntegrationEntity=rechargePresentsService.selectAllRechargePresentsIntegration(strActivityId);
			if(listRechargePresentsIntegrationEntity==null||listRechargePresentsIntegrationEntity.size()==0)
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
	
*/
	
	/**
	 * 新增 优惠活动详细信息
	 * @param request
	 * @param response
	 * @return
	 */
	/*
	@ResponseBody
	@RequestMapping("inserPresentsActivityInfo")
	//http://localhost:8083/admin/RechargePresentsSetting/inserPresentsActivityInfo?strActivityName=十周年店庆&strLevelsId=10000&strActivityBeginTime=2017/5/27&strActivityEndTime=2017/5/27
	public String inserPresentsActivityInfo(HttpServletRequest request,HttpServletResponse response)
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
		*/
		
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
/*
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
			return rechargePresentsService.inserPresentsActivityInfo(presentsActivityEntity);
		}catch(Exception e)
		{
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
		}
		
	}
	
*/
	
	
	
	/**
	 * 更新 优惠活动详细信息
	 * @param request
	 * @param response
	 * @return
	 */
	/*
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
		*/
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
		/*
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
*/
	
	/**
	 * 分页查询 优惠活动详细信息
	 * @param request
	 * @param response
	 * @return
	 */
	/*
	@ResponseBody
	@RequestMapping("selectRechargePresentsActivityInfo")

	//http://localhost:8083/admin/RechargePresentsSetting/selectRechargePresentsActivityInfo?iPageNum=1&iPageSize=1&strSearchMemberLevel=1&strSearchEnabledStatus=ALL
	public String selectRechargePresentsActivityInfo(HttpServletRequest request,HttpServletResponse response)
	{
		//取得搜索字段
		//strSearchEnabledStatus=活动状态：全部：ALL；过期:EXPIRED，正常:NORMAL
		//strSearchMemberLevel=ALL 全部
		int iPageNum,iPageSize,iTotalPage,iTotalRecord=0,iPageFrom;
		String strSearchMemberLevel=request.getParameter("strSearchMemberLevel");
		String strSearchEnabledStatus=request.getParameter("strSearchEnabledStatus");
		String strPageNum=request.getParameter("iPageNum");
		String strPageSize=request.getParameter("iPageSize");
		String strCurrentDate=DateTool.DateToString(new Date(),DateStyle.YYYY_MM_DD);
		
		if(strSearchMemberLevel==null||strSearchMemberLevel.trim()=="")
			strSearchMemberLevel="";
		
		if("ALL".equals(strSearchMemberLevel))
			strSearchMemberLevel="";
		
		if(strSearchEnabledStatus==null||strSearchEnabledStatus.trim()=="")
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
					{
					listRechargePresentsActivityEntity=rechargePresentsService.selectNormalRechargePresentsActivityInfo(queryMap);
				
					}
				
				if(listRechargePresentsActivityEntity==null||listRechargePresentsActivityEntity.size()==0)
					return DataTool.constructResponse(ResultCode.NO_DATA,"暂无数据",null);
				Map<String,Object> resultMap=new HashMap<String,Object>();
				resultMap.put("iTotalRecord",iTotalRecord);
				resultMap.put("iTotalPage",iTotalPage);
				resultMap.put("resultMap",listRechargePresentsActivityEntity);
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
