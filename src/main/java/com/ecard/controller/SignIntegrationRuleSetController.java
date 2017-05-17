package com.ecard.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.commontools.data.DataTool;
import com.commontools.date.DateStyle;
import com.commontools.date.DateTool;
import com.commontools.validate.ValidateTool;
import com.ecard.service.SignIntegrationRuleSetService;
import com.ecard.config.ResultCode;
import com.ecard.entity.EmployeeEntity;
import com.ecard.entity.IntegrationCashRuleEntity;
import com.ecard.entity.SignIntegrationRuleEntity;
import com.ecard.util.WebSessionUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin/biz/RuleSetting")
public class SignIntegrationRuleSetController {
	@Autowired
	private SignIntegrationRuleSetService signIntegrationRuleSetService;
	@Autowired
	private WebSessionUtil webSessionUtil;
	//插入连续签到积分规则
	//localhost:8083/admin/biz/RuleSetting/insertSignIntegrationRule?strSignDays=5&iIntegration=100&strEnabled=1
	@ResponseBody
	@RequestMapping("insertSignIntegrationRule")
	public String insertSignIntegrationRule(HttpServletRequest request,HttpServletResponse response)
	{
		// 取得连续签到积分规则参数
		int iIntegration;
		String strSignId=DataTool.getUUID();												
		String strSignDays= request.getParameter("strSignDays");							//签到天数校验
		String strStatus ="1";																//连续性签 到状态为1
		String strIntegration =request.getParameter("iIntegration");						//校验并转换类型
		String strEnabled = request.getParameter("strEnabled");
		
		if (ValidateTool.isEmptyStr(strSignDays)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"请填写签到天数", null);
		}
		
		if(!isNumber(strSignDays))
			return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"签到天数应填写数字", null);
	
		if (ValidateTool.isEmptyStr(strIntegration))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"积分不能为空", null);
		
		if(!isNumber(strIntegration))
			return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"积分应填写数字", null);
		else
			iIntegration=Integer.parseInt(strIntegration);
		
		if (ValidateTool.isEmptyStr(strEnabled)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"规则是否启用不能为空", null);
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
		///
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
		// 装进SignIntegrationRuleEntity对象
		SignIntegrationRuleEntity signIntegrationRuleEntity = new SignIntegrationRuleEntity();
		signIntegrationRuleEntity.setStrSignId(strSignId);
		signIntegrationRuleEntity.setStrSignDays(strSignDays);
		signIntegrationRuleEntity.setStrStatus(strStatus);
		signIntegrationRuleEntity.setIIntegration(iIntegration);
		signIntegrationRuleEntity.setStrEnabled(strEnabled);
		signIntegrationRuleEntity.setStrEmployeeId(strEmployeeId);
		signIntegrationRuleEntity.setStrEmployeeName(strEmployeeName);
		signIntegrationRuleEntity.setStrEmployeeRealName(strEmployeeRealName);
		signIntegrationRuleEntity.setStrCreationTime(strCreationTime);
		signIntegrationRuleEntity.setStrLastAccessedTime(strLastAccessedTime);
		
		try {
			int result = signIntegrationRuleSetService.insertSignIntegrationRule(signIntegrationRuleEntity);
			if(result!=0)
				 return DataTool.constructResponse(ResultCode.OK,"插入连续签到积分规则成功", null);
			else
				 return DataTool.constructResponse(ResultCode.UNKNOW_ERROR,"插入连续签到积分规则失败", null);
			
		} catch (Exception e) {
			e.printStackTrace();			
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"操作数据库失败", null);
		}
	}
	
	
		
		//插入非连续签到积分规则
		//localhost:8083/admin/biz/RuleSetting/insertSignIntegrationRules?strSignDays=10&iIntegration=100&strEnabled=0
		@ResponseBody
		@RequestMapping("insertSignIntegrationRules")
		public String insertSignIntegrationRules(HttpServletRequest request,HttpServletResponse response)
		{

			// 取得非连续签到积分规则
			int iIntegration;
			String strSignDays= request.getParameter("strSignDays");							//校验数据有效性????????????
			String strStatus ="0";																	//非连续性签到状态为0
			String strIntegration = request.getParameter("iIntegration");					//校验数据有效性???????????
			String strEnabled = request.getParameter("strEnabled");	
			
			if (ValidateTool.isEmptyStr(strSignDays)) {
				return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"请填写签到天数", null);
			}
			
			if(!isNumber(strSignDays))
				return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR, "签到天数应填写数字", null);
			
			if (ValidateTool.isEmptyStr(strIntegration))
				return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"积分不能为空", null);
			
			if(isNumber(strIntegration))
				iIntegration=Integer.parseInt(strIntegration);
			else
				return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR, "积分应填写数字", null);

			if (ValidateTool.isEmptyStr(strEnabled)) {
				return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"规则是否启用不能为空", null);
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
			
			// 装进SignIntegrationRuleEntity
			SignIntegrationRuleEntity signIntegrationRuleEntity = new SignIntegrationRuleEntity();
			signIntegrationRuleEntity.setStrSignId(DataTool.getUUID());
			signIntegrationRuleEntity.setStrSignDays(strSignDays);
			signIntegrationRuleEntity.setStrStatus(strStatus);
			signIntegrationRuleEntity.setIIntegration(iIntegration);
			signIntegrationRuleEntity.setStrEnabled(strEnabled);
			signIntegrationRuleEntity.setStrEmployeeId(strEmployeeId);
			signIntegrationRuleEntity.setStrEmployeeName(strEmployeeName);
			signIntegrationRuleEntity.setStrEmployeeRealName(strEmployeeRealName);
			signIntegrationRuleEntity.setStrCreationTime(strCreationTime);
			signIntegrationRuleEntity.setStrLastAccessedTime(strLastAccessedTime);
			
			try {
				int result = signIntegrationRuleSetService.insertSignIntegrationRule(signIntegrationRuleEntity);
				if(result!=0)
					return DataTool.constructResponse(ResultCode.OK,"插入非连续签到积分规则成功", null);
				else
					return DataTool.constructResponse(ResultCode.UNKNOW_ERROR,"插入非连续签到积分规则失败", null);

			} catch (Exception e) {
				e.printStackTrace();				
				return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"操作数据库失败", null);
			}
		}
		
		
		
		//插入积分抵现规则
		//localhost:8083/admin/biz/RuleSetting/insertIntegrationCashRule?iIntegration=10000&dCash=2.5&strEnabled=1
		@ResponseBody
		@RequestMapping("insertIntegrationCashRule")
		public String insertIntegrationCashRule(HttpServletRequest request,HttpServletResponse response)
		{
			int iIntegration;
			double dCash;
			String strIntegration = request.getParameter("iIntegration");	//校验有效性?????????????
			String strCash =request.getParameter("dCash");				//校验有效性？？？、？
			String strEnabled = request.getParameter("strEnabled");
		
			if(ValidateTool.isEmptyStr(strIntegration)) {
				return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"积分不能为空", null);
			}
			
			if(isNumber(strIntegration))
				iIntegration=Integer.parseInt(strIntegration);
			else
				return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"积分处请填写数字", null);
			
			if (ValidateTool.isEmptyStr(strCash)) {
				return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"抵现不能为空", null);
			}
			
			if(isNumber(strCash))
				dCash=Double.parseDouble(strCash);
			else
				return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"请填写数字", null);
			
			if (ValidateTool.isEmptyStr(strEnabled)) {
				return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"启用状态不能为空", null);
			}
			
			/*
			EmployeeEntity employeeEntity = null;
			try {
				employeeEntity = (EmployeeEntity) webSessionUtil.getWebSession(
						request, response).getAttribute("employeeEntity");
			} catch (Exception e) {
				e.printStackTrace();
				return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
			}
			if (employeeEntity==null) {
				return DataTool.constructResponse(ResultCode.NO_DATA, "操作员不存在", null);
			}
			//
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
			
			// 属性装进ENTITY
			IntegrationCashRuleEntity integrationCashRuleEntity = new IntegrationCashRuleEntity();
			integrationCashRuleEntity.setstrId(DataTool.getUUID());
			integrationCashRuleEntity.setIIntegration(iIntegration);
			integrationCashRuleEntity.setDCash(dCash);
			integrationCashRuleEntity.setStrEnabled(strEnabled);
			integrationCashRuleEntity.setStrEmployeeId(strEmployeeId);
			integrationCashRuleEntity.setStrEmployeeName(strEmployeeName);
			integrationCashRuleEntity.setStrEmployeeRealName(strEmployeeRealName);
			integrationCashRuleEntity.setStrCreationTime(strCreationTime);
			integrationCashRuleEntity.setStrLastAccessedTime(strLastAccessedTime);
			
			// 对取得的数据进行写入操作
			try {
			int result = signIntegrationRuleSetService.insertIntegrationCashRule(integrationCashRuleEntity);
			if(result!=0)
				return DataTool.constructResponse(ResultCode.OK,"插入积分抵现规则成功", null);

			else
				return DataTool.constructResponse(ResultCode.UNKNOW_ERROR,"插入积分抵现规则失败", null);
			
			} catch (Exception e) {
				e.printStackTrace();

				return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"操作数据库失败", null);
			}
		}
		
		
		//更新连续签到积分规则
		//localhost:8083/admin/biz/RuleSetting/updateSignIntegrationRules?strId=01204fd6bc79402dadb2b7b2092b6863&strSignDays=10&iIntegration=100&strEnabled=0
			@ResponseBody
			@RequestMapping("updateSignIntegrationRules")
			public String updateSignIntegrationRules(HttpServletRequest request,HttpServletResponse response)
			{
				int iIntegration;
				String strSignId = request.getParameter("strId"); 
				String strSignDays= request.getParameter("strSignDays");							//检验?????????
				String strStatus ="1";																	//连续性签 到状态为1
				String strIntegration =request.getParameter("iIntegration");						//检验??????
				String strEnabled = request.getParameter("strEnabled");	
				
				if (ValidateTool.isEmptyStr(strSignId)) {
					return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"id不能为空", null);
				}
				
					
				if (ValidateTool.isEmptyStr(strSignDays)) {
					return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"请填写签到天数", null);
				}
				
				if(!isNumber(strSignDays))
					return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"请填写数字", null);

				if (ValidateTool.isEmptyStr(strIntegration))
					return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"积分不能为空", null);
				
				if(isNumber(strIntegration))
					iIntegration=Integer.parseInt(strIntegration);
				else
					return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"请填写数字", null);

				if (ValidateTool.isEmptyStr(strEnabled)) {
					return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"规则是否启用不能为空", null);
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
				///
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
				
				// 装进SignIntegrationRuleEntity
				SignIntegrationRuleEntity signIntegrationRuleEntity = new SignIntegrationRuleEntity();
				signIntegrationRuleEntity.setStrSignId(strSignId);
				signIntegrationRuleEntity.setStrSignDays(strSignDays);
				signIntegrationRuleEntity.setStrStatus(strStatus);
				signIntegrationRuleEntity.setIIntegration(iIntegration);
				signIntegrationRuleEntity.setStrEnabled(strEnabled);
				signIntegrationRuleEntity.setStrEmployeeId(strEmployeeId);
				signIntegrationRuleEntity.setStrEmployeeName(strEmployeeName);
				signIntegrationRuleEntity.setStrEmployeeRealName(strEmployeeRealName);
				signIntegrationRuleEntity.setStrLastAccessedTime(strLastAccessedTime);
				try {
					int result = signIntegrationRuleSetService.updateSignIntegrationRule(signIntegrationRuleEntity);
					if(result!=0)
						return DataTool.constructResponse(ResultCode.OK,"更新连续性签到积分规则成功", null);
					else
						return DataTool.constructResponse(ResultCode.UNKNOW_ERROR,"更新连续性签到积分规则失败", null);
				} catch (Exception e) {
					e.printStackTrace();
					return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"操作数据库失败", null);
				}
			}
			
			
			
			//循环更新非续性签到积分表
			//localhost:8083/admin/biz/RuleSetting/updateSignIntegrationRule?strId=fbd49bed886043a7b0454a4f77698ebc&strSignDays=10&iIntegration=1000&strEnabled=0
				@ResponseBody
				@RequestMapping("updateSignIntegrationRule")
				public String updateSignIntegrationRule(HttpServletRequest request,HttpServletResponse response)
				{	
					//取得的参数有可能是数组，申明数组变量
					int arrayLength=0;
					String strSignId[] = request.getParameter("strId").split(","); 
					String strSignDays[]= request.getParameter("strSignDays").split(",");					//校验
					String strStatus="0";															//非连续性签到状态为0
					String strIntegration[]= request.getParameter("iIntegration").split(",");				//检验
					String strEnabled[] = request.getParameter("strEnabled").split(",");
					arrayLength=strSignId.length;
					int[] iIntegration=new int[arrayLength];
					if(arrayLength!=strSignDays.length||arrayLength!=strIntegration.length||arrayLength!=strEnabled.length)
						return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"参数缺失",null);
					for(int i=0;i<arrayLength;i++)
					{	
						if (ValidateTool.isEmptyStr(strSignId[i])) 
							return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"id不能为空", null);
							
					}
					
					for(int i=0;i<arrayLength;i++)
					{
						if (ValidateTool.isEmptyStr(strSignDays[i])) 
							return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"请填写非连续签到天数", null);
							
					}
					
					for(int i=0;i<arrayLength;i++)
					{
						if(!isNumber(strSignDays[i]))
							return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"请填写数字", null);
							
					}
					
					for(int i=0;i<arrayLength;i++)
					{
						if (ValidateTool.isEmptyStr(strIntegration[i]))
							return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"积分不能为空", null);
							
					}
					
					for(int i=0;i<arrayLength;i++)
					{
						if(isNumber(strIntegration[i]))
							{
								iIntegration[i]=Integer.parseInt(strIntegration[i]);
							}
						else
							return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"请填写数字", null);
							
					}
					
					for(int i=0;i<arrayLength;i++)
					{
						if (ValidateTool.isEmptyStr(strEnabled[i])) 
							return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"是否启用不能为空", null);
							
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
					///
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
					
					// 装进SignIntegrationRuleEntity
					List<SignIntegrationRuleEntity> listSignIntegrationRuleEntity=new ArrayList<SignIntegrationRuleEntity>();
					for(int i=0;i<arrayLength;i++)
					{
						SignIntegrationRuleEntity signIntegrationRuleEntity=new SignIntegrationRuleEntity();
						signIntegrationRuleEntity.setStrSignId(strSignId[i]);
						signIntegrationRuleEntity.setStrSignDays(strSignDays[i]);
						signIntegrationRuleEntity.setStrStatus(strStatus);
						signIntegrationRuleEntity.setIIntegration(iIntegration[i]);
						signIntegrationRuleEntity.setStrEnabled(strEnabled[i]);
						signIntegrationRuleEntity.setStrEmployeeId(strEmployeeId);
						signIntegrationRuleEntity.setStrEmployeeName(strEmployeeName);
						signIntegrationRuleEntity.setStrEmployeeRealName(strEmployeeRealName);
						signIntegrationRuleEntity.setStrLastAccessedTime(strLastAccessedTime);
						listSignIntegrationRuleEntity.add(signIntegrationRuleEntity);
					}
					
					try {
						return signIntegrationRuleSetService.updateSignIntegrationRules(listSignIntegrationRuleEntity);
						
					} catch (Exception e) {
						e.printStackTrace();
						return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"操作数据库失败", null);
					}
				}
				
				
				
				//更新积分抵现规则
				//localhost:8083/admin/biz/RuleSetting/updateIntegrationCashRule?strId=5482650fda334e32a03a972fe3dab11a&iIntegration=100&dCash=1&strEnabled=0
				@ResponseBody
				@RequestMapping("updateIntegrationCashRule")
				public String updateIntegrationCashRule(HttpServletRequest request,HttpServletResponse response)
				{
					int iIntegration;
					double dCash;				
					String strId = request.getParameter("strId");
					String strIntegration =request.getParameter("iIntegration");						//校验？？？？
					String strCash =request.getParameter("dCash");									//校验?????
					String strEnabled = request.getParameter("strEnabled");	
					
					if (ValidateTool.isEmptyStr(strId)) {
						return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"id不能为空", null);
					}
					
					if (ValidateTool.isEmptyStr(strIntegration)) {
						return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"积分不能为空", null);
					}
					
					if(isNumber(strIntegration))
						iIntegration=Integer.parseInt(strIntegration);
					else
						return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"请填写数字",null);
					
					if (ValidateTool.isEmptyStr(strCash)) {
						return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"抵现不能为空", null);
					}
					
					if(isNumber(strCash))
						dCash=Double.parseDouble(strCash);
					else
						return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"请填写数字",null);
					
					if (ValidateTool.isEmptyStr(strEnabled)) {
						return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"积分抵现启用状态不能为空", null);
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
					///
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
					
					// 属性装进ENTITY
					IntegrationCashRuleEntity integrationCashRuleEntity = new IntegrationCashRuleEntity();
					integrationCashRuleEntity.setstrId(strId);
					integrationCashRuleEntity.setIIntegration(iIntegration);
					integrationCashRuleEntity.setDCash(dCash);
					integrationCashRuleEntity.setStrEnabled(strEnabled);
					integrationCashRuleEntity.setStrEmployeeId(strEmployeeId);
					integrationCashRuleEntity.setStrEmployeeName(strEmployeeName);
					integrationCashRuleEntity.setStrEmployeeRealName(strEmployeeRealName);
					integrationCashRuleEntity.setStrLastAccessedTime(strLastAccessedTime);
					
					// 对取得的数据进行写入操作
					try {
					int result = signIntegrationRuleSetService.updateIntegrationCashRule(integrationCashRuleEntity);
					if(result!=0)
						return DataTool.constructResponse(ResultCode.OK,"更新积分抵现规则成功", null);
					else
						return DataTool.constructResponse(ResultCode.UNKNOW_ERROR,"更新积分抵现规则失败", null);
					
					} catch (Exception e) {
						e.printStackTrace();
						return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"操作数据库失败", null);
					}
				}
				
				
	//显示全部非连续签到积分规则
	//localhost:8083/admin/biz/RuleSetting/findAllSignIntegrationRules
	@ResponseBody
	@RequestMapping("findAllSignIntegrationRules")
	public String findAllSignIntegrationRules(HttpServletResponse response,HttpServletRequest request)
	{
		//检验身份有效性
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
			List<SignIntegrationRuleEntity> listSignIntegrationRuleEntity=signIntegrationRuleSetService.findAllSignIntegrationRules();
			
			if(listSignIntegrationRuleEntity==null||listSignIntegrationRuleEntity.size()==0)
				return DataTool.constructResponse(ResultCode.NO_DATA,"暂无数据",null);
			else
			{
				Map<String,Object> resultMap=new HashMap<String,Object>();
				resultMap.put("listSignIntegrationRuleEntity", listSignIntegrationRuleEntity);
				return DataTool.constructResponse(ResultCode.OK,"查询成功",resultMap);
			}
		}catch(Exception e)
		{
		e.printStackTrace();
		return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
		}
	}
	
	

		//显示全部连续签到积分规则
		//localhost:8083/admin/biz/RuleSetting/findAllSignIntegrationRule
		@ResponseBody
		@RequestMapping("findAllSignIntegrationRule")
		public String findAllSignIntegrationRule(HttpServletResponse response,HttpServletRequest request)
		{
			//检验身份有效性
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
				List<SignIntegrationRuleEntity> listSignIntegrationRuleEntity=signIntegrationRuleSetService.findAllSignIntegrationRule();
				
				if(listSignIntegrationRuleEntity==null||listSignIntegrationRuleEntity.size()==0)
					return DataTool.constructResponse(ResultCode.NO_DATA,"暂无数据",null);
				else
				{
					Map<String,Object> resultMap=new HashMap<String,Object>();
					resultMap.put("listSignIntegrationRuleEntity", listSignIntegrationRuleEntity);
					return DataTool.constructResponse(ResultCode.OK,"查询连续签到成功",resultMap);
				}
			}catch(Exception e)
			{
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
			}
		}
		
	//显示全部积分抵现规则
	//localhost:8083/admin/biz/RuleSetting/findAllIntegrationCashRule
	@ResponseBody
	@RequestMapping("findAllIntegrationCashRule")
	public String findAllIntegrationCashRule(HttpServletResponse response,HttpServletRequest request)
	{
	try{
		List<IntegrationCashRuleEntity> listIntegrationCashRuleEntity=signIntegrationRuleSetService.findAllIntegrationCashRule();
		if(listIntegrationCashRuleEntity==null||listIntegrationCashRuleEntity.size()==0)
			return DataTool.constructResponse(ResultCode.NO_DATA,"还没有数据",null);
		else
			{
			Map<String,Object> resultMap=new HashMap<String,Object>();
			resultMap.put("listIntegrationCashRuleEntity", listIntegrationCashRuleEntity);
			return DataTool.constructResponse(ResultCode.OK, "查询成功",resultMap);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
			
		}
	}
	
	//删除连续签到和非连续签到积分规则
	@ResponseBody
	@RequestMapping("deleteSignIntegrationRule")
	//localhost:8083/admin/biz/RuleSetting/deleteSignIntegrationRule?strSignId=8e2729bcea0845788cdd8b88e128b575
	public String deleteSignIntegrationRule(HttpServletRequest request,HttpServletResponse response)
	{

		String strSignId=request.getParameter("strSignId");
		if(ValidateTool.isEmptyStr(strSignId))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"记录ID不能为空",null);
		
		//检验身份
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
			
			int affectNum=signIntegrationRuleSetService.deleteSignIntegrationRule(strSignId);
			if(affectNum!=0)
				return DataTool.constructResponse(ResultCode.OK,"删除成功!",null);
			else
				return DataTool.constructResponse(ResultCode.UNKNOW_ERROR,"删除失败",null);
			
		}catch(Exception e)
		{

			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
		}
		
	}
	
	
		//删除积分抵现规则
		@ResponseBody
		@RequestMapping("deleteIntegrationCashRule")
		//localhost:8083/admin/biz/RuleSetting/deleteIntegrationCashRule?strId=85b7fe27fe704e7d88b51498e78d7709
		public String deleteIntegrationCashRule(HttpServletRequest request,HttpServletResponse response)
		{

			String strId=request.getParameter("strId");
			if(ValidateTool.isEmptyStr(strId))
				return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"记录ID不能为空",null);
			
			//检验身份
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
				
				int affectNum=signIntegrationRuleSetService.deleteIntegrationCashRule(strId);
				if(affectNum!=0)
					return DataTool.constructResponse(ResultCode.OK,"删除成功!",null);
				else
					return DataTool.constructResponse(ResultCode.UNKNOW_ERROR,"删除失败",null);
				
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
		Pattern p1 = Pattern.compile("^[1-9]\\d*$|^[1-9]\\d*\\.\\d{1,2}$|^0\\.\\d{1,2}$");
		Matcher matcher=p1.matcher(strCheckString);
		flag=matcher.matches();   
		return flag;
	}
	
}
