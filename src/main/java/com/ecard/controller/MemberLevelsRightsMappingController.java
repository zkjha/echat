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
import com.ecard.entity.EmployeeEntity;
import com.ecard.entity.MemberLevelsRightsMappingEntity;
import com.ecard.service.MemberLevelsRightsMappingService;
import com.ecard.util.WebSessionUtil;

@Controller
@RequestMapping("/admin/biz/levelsRightsMapping")
public class MemberLevelsRightsMappingController {
	@Autowired
	private MemberLevelsRightsMappingService memberLevelsRightsMappingService;
	@Autowired
	private WebSessionUtil webSessionUtil;
	//新增 会员等级及其对应权益关系信息
	@ResponseBody
	@RequestMapping("insertMemberLevelsRightsMappingInfo")
	//http://localhost:8083/admin/biz/levelsRightsMapping/insertMemberLevelsRightsMappingInfo?strLevelsId=levels002&strRightsId=right005&iRightsStatus=1&dDiscount=0.85&iPreferentialTimes=2
	public String insertMemberLevelsRightsMappingInfo(HttpServletResponse response,HttpServletRequest request)
	{	
		int iRightsStatus,iPreferentialTimes=0;
		double dDiscount=0;
		String strLevelsId=request.getParameter("strLevelsId");
		String strRightsId=request.getParameter("strRightsId");
		String strRightsStatus=request.getParameter("iRightsStatus");
		String strDiscount=request.getParameter("dDiscount");
		String strPreferentialTimes=request.getParameter("iPreferentialTimes");
		
		String strLevelsRightsMappingId=DataTool.getUUID();
		if(ValidateTool.isEmptyStr(strRightsId))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"权利ID不能为空",null);
		
		if(ValidateTool.isEmptyStr(strRightsStatus))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"权利状态不能为空",null);
		else
		{
			if(isNumber(strRightsStatus))
				iRightsStatus=Integer.parseInt(strRightsStatus);
			else
				return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"权利状态参数格式不对",null);	
			
		}
		
		if(!ValidateTool.isEmptyStr(strDiscount))	//折扣不为空的情况下，检验是否为数字类型
		{
			if(isNumber(strDiscount))
				dDiscount=Double.parseDouble(strDiscount);
			else
				return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"折扣参数格式不对",null);	
			
		}
		
		if(!ValidateTool.isEmptyStr(strPreferentialTimes))
		{
			if(isNumber(strPreferentialTimes))
				iPreferentialTimes=Integer.parseInt(strPreferentialTimes);
			else
				return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"优惠次数格式不对",null);	
		}
		
	/*检验身份有效性
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
		//将数据录入实体
		MemberLevelsRightsMappingEntity memberLevelsRightsMappingEntity=new MemberLevelsRightsMappingEntity();
		memberLevelsRightsMappingEntity.setStrLevelsRightsMappingId(strLevelsRightsMappingId);
		memberLevelsRightsMappingEntity.setStrLevelsId(strLevelsId);
		memberLevelsRightsMappingEntity.setStrRightsId(strRightsId);
		memberLevelsRightsMappingEntity.setDdiscount(dDiscount);
		memberLevelsRightsMappingEntity.setIpreferentialTimes(iPreferentialTimes);
		memberLevelsRightsMappingEntity.setIrightsStatus(iRightsStatus);
		memberLevelsRightsMappingEntity.setStrEmployeeId(strEmployeeId);
		memberLevelsRightsMappingEntity.setStrEmployeeName(strEmployeeName);
		memberLevelsRightsMappingEntity.setStrEmployeeRealName(strEmployeeRealName);
		memberLevelsRightsMappingEntity.setStrCreationTime(strCreationTime);
		memberLevelsRightsMappingEntity.setStrLastAccessedTime(strLastAccessedTime);
		try{
			memberLevelsRightsMappingService.insertMemberLevelsRightsMappingInfo(memberLevelsRightsMappingEntity);
			return DataTool.constructResponse(ResultCode.OK,"插入成功",null);
					
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
