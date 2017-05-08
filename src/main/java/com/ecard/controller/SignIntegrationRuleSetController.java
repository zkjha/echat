package com.ecard.controller;

import java.util.Date;

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
	//插入连续签到积分规则
	//localhost:8083/admin/biz/RuleSetting/insertSignIntegrationRule?strSignDays=5&iIntegration=100&strEnabled=1
	@ResponseBody
	@RequestMapping("insertSignIntegrationRule")
	public String insertSignIntegrationRule(HttpServletRequest request,HttpServletResponse response)
	{
		// 取得连续签到积分规则参数
		String strSignId=DataTool.getUUID();													//自动生成ID
		String strSignDays= request.getParameter("strSignDays").trim();							//签到天数校验?????????????
		String strStatus ="1";																	//连续性签 到状态为1
		int iIntegration = Integer.parseInt(request.getParameter("iIntegration").trim());		//校验并转换类型??????????
		String strEnabled = request.getParameter("strEnabled").trim();
		if (ValidateTool.isEmptyStr(strSignDays)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"请填写签到天数", null);
		}
		if (iIntegration == 0)
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"积分不能为空", null);
		if (ValidateTool.isEmptyStr(strEnabled)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"规则是否启用不能为空", null);
		}
		
		/*
		EmployeeEntity employeeEntity = null;
		WebSessionUtil webSessionUtil=new WebSessionUtil();
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
				 return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"插入连续签到积分规则成功", null);
			else
				 return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"插入连续签到积分规则失败", null);
			
		} catch (Exception e) {
			e.printStackTrace();			
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"操作数据库失败", null);
		}
	}
	
	
		
		//取得非连续签到积分规则
		//localhost:8083/admin/biz/RuleSetting/insertSignIntegrationRules?strSignDays=10&iIntegration=100&strEnabled=0
		@ResponseBody
		@RequestMapping("insertSignIntegrationRules")
		public String insertSignIntegrationRules(HttpServletRequest request,HttpServletResponse response)
		{

			// 取得非连续签到积分规则
			String strSignDays= request.getParameter("strSignDays").trim();							//校验数据有效性????????????
			String strStatus ="0";																	//非连续性签到状态为0
			int iIntegration = Integer.parseInt(request.getParameter("iIntegration").trim());		//校验数据有效性???????????
			String strEnabled = request.getParameter("strEnabled").trim();		
			if (ValidateTool.isEmptyStr(strSignDays)) {
				return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"请填写签到天数", null);
			}

			if (iIntegration == 0)
				return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"积分不能为空", null);

			if (ValidateTool.isEmptyStr(strEnabled)) {
				return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"规则是否启用不能为空", null);
			}
			
			/*
			EmployeeEntity employeeEntity = null;
			WebSessionUtil webSessionUtil=new WebSessionUtil();
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
					return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"插入非连续签到积分规则成功", null);
				else
					return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"插入非连续签到积分规则失败", null);

			} catch (Exception e) {
				e.printStackTrace();				
				return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"操作数据库失败", null);
			}
		}
		
		
		
		//插入积分抵现规则
		//localhost:8083/admin/biz/RuleSetting/insertIntegrationCashRule?iIntegration=10000&dCash=1&strEnabled=1
		@ResponseBody
		@RequestMapping("insertIntegrationCashRule")
		public String insertIntegrationCashRule(HttpServletRequest request,HttpServletResponse response)
		{
			int iIntegration = Integer.parseInt(request.getParameter("iIntegration").trim());	//校验有效性?????????????
			double dCash = Integer.parseInt(request.getParameter("dCash").trim());				//校验有效性？？？、？
			String strEnabled = request.getParameter("strEnabled").trim();
		
			if (iIntegration == 0) {
				return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"积分不能为空", null);
			}
			if (dCash == 0) {
				return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"抵现不能为空", null);
			}
			if (ValidateTool.isEmptyStr(strEnabled)) {
				return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"启用状态不能为空", null);
			}
			
			/*
			EmployeeEntity employeeEntity = null;
			WebSessionUtil webSessionUtil=new WebSessionUtil();
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
				return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"插入积分抵现规则成功", null);

			else
				return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"插入积分抵现规则失败", null);
			
			} catch (Exception e) {
				e.printStackTrace();

				return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"操作数据库失败", null);
			}
		}
		
		
		//更新连续签到积分规则
		//localhost:8083/admin/biz/RuleSetting/updateSignIntegrationRules?strId=01204fd6bc79402dadb2b7b2092b6863&strSignDays=10&iIntegration=100&strEnabled=0
			@ResponseBody
			@RequestMapping("updateSignIntegrationRules")
			public String updateSignIntegrationRules(HttpServletRequest request,HttpServletResponse response)
			{
				String strSignId = request.getParameter("strId").trim(); 
				String strSignDays= request.getParameter("strSignDays").trim();							//检验?????????
				String strStatus ="1";//连续性签 到状态为1
				int iIntegration = Integer.parseInt(request.getParameter("iIntegration").trim());		//检验??????
				String strEnabled = request.getParameter("strEnabled").trim();							
				if (ValidateTool.isEmptyStr(strSignId)) {
					return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"id不能为空", null);
				}

				if (ValidateTool.isEmptyStr(strSignDays)) {
					return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"请填写签到天数", null);
				}

				if (iIntegration == 0)
					return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"积分不能为空", null);

				if (ValidateTool.isEmptyStr(strEnabled)) {
					return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"规则是否启用不能为空", null);
				}
				
				/*
				EmployeeEntity employeeEntity = null;
				WebSessionUtil webSessionUtil=new WebSessionUtil();
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
				signIntegrationRuleEntity.setStrCreationTime(strCreationTime);
				signIntegrationRuleEntity.setStrLastAccessedTime(strLastAccessedTime);
				try {
					int result = signIntegrationRuleSetService.updateSignIntegrationRule(signIntegrationRuleEntity);
					if(result!=0)
						return "更新连续签到积分规则成功";
					else
						return "更新连续签到积分规则失败";
				} catch (Exception e) {
					e.printStackTrace();
					return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"操作数据库失败", null);
				}
			}
			
			
			
			//更新非续性签到积分表
			//localhost:8083/admin/biz/RuleSetting/updateSignIntegrationRule?strId=fbd49bed886043a7b0454a4f77698ebc&strSignDays=10&iIntegration=1000&strEnabled=0
				@ResponseBody
				@RequestMapping("updateSignIntegrationRule")
				public String updateSignIntegrationRule(HttpServletRequest request,HttpServletResponse response)
				{
					String strSignId = request.getParameter("strId").trim(); 
					String strSignDays= request.getParameter("strSignDays").trim();							//校验????
					String strStatus ="0";																	//非连续性签到状态为0
					int iIntegration = Integer.parseInt(request.getParameter("iIntegration").trim());		//检验？？
					String strEnabled = request.getParameter("strEnabled").trim();
					if (ValidateTool.isEmptyStr(strSignId)) {
						return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"id不能为空", null);
					}

					if (ValidateTool.isEmptyStr(strSignDays)) {
						return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"请填写非连续签到天数", null);
					}

					if (iIntegration == 0)
						return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"积分不能为空", null);

					if (ValidateTool.isEmptyStr(strEnabled)) {
						return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"是否启用不能为空", null);
					}
					
					/*
					EmployeeEntity employeeEntity = null;
					WebSessionUtil webSessionUtil=new WebSessionUtil();
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
					signIntegrationRuleEntity.setStrCreationTime(strCreationTime);
					signIntegrationRuleEntity.setStrLastAccessedTime(strLastAccessedTime);

					try {
						int result = signIntegrationRuleSetService.updateSignIntegrationRule(signIntegrationRuleEntity);
						if(result!=0)
							return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"更新非连续性签到积分规则成功", null);
						else
							return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"更新非连续性签到积分规则失败", null);
					} catch (Exception e) {
						e.printStackTrace();
						
						return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"操作数据库失败", null);
					}
				}
				
				
				
				//更新积分抵现规则
				//localhost:8083/admin/biz/RuleSetting/updateIntegrationCashRule?strId=5482650fda334e32a03a972fe3dab11a&iIntegration=100&dCash=1&strEnabled=0
				@ResponseBody
				@RequestMapping("updateIntegrationCashRule")
				public String updateIntegrationCashRule(HttpServletRequest request,HttpServletResponse response)
				{
					String strId = request.getParameter("strId").trim();
					int iIntegration = Integer.parseInt(request.getParameter("iIntegration").trim());		//校验？？？？
					double dCash = Integer.parseInt(request.getParameter("dCash").trim());					//校验?????
					String strEnabled = request.getParameter("strEnabled").trim();			
					if (ValidateTool.isEmptyStr(strId)) {
						return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"id不能为空", null);
					}
					if (iIntegration == 0) {
						return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"积分不能为空", null);
					}
					if (dCash == 0) {
						return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"抵现不能为空", null);
					}
					if (ValidateTool.isEmptyStr(strEnabled)) {
						return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"积分抵现启用状态不能为空", null);
					}
					
					/*
					EmployeeEntity employeeEntity = null;
					WebSessionUtil webSessionUtil=new WebSessionUtil();
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
					
					// 属性装进ENTITY
					IntegrationCashRuleEntity integrationCashRuleEntity = new IntegrationCashRuleEntity();
					integrationCashRuleEntity.setstrId(strId);
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
					int result = signIntegrationRuleSetService.updateIntegrationCashRule(integrationCashRuleEntity);
					if(result!=0)
						return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"更新积分抵现规则成功", null);
					else
						return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"更新积分抵现规则失败", null);
					
					} catch (Exception e) {
						e.printStackTrace();
						return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"操作数据库失败", null);
					}
				}
}
