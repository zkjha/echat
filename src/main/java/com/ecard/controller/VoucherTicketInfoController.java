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
import com.ecard.entity.VoucherTicketInfoEntity;
import com.ecard.service.VoucherTicketInfoService;
import com.ecard.util.WebSessionUtil;

@Controller
@RequestMapping("/admin/biz/voucherTickeSetting")
public class VoucherTicketInfoController {
	@Autowired
	private VoucherTicketInfoService voucherTicketInfoService;
	@Autowired
	private WebSessionUtil webSessionUtil;
	/**
	 * 新增 抵用卷信息
	 * @param response
	 * @param request
	 * @return
	 */
	//localhost:8083/admin/biz/voucherTickeSetting/insertVoucherTicketInfo?strVoucherTicketName="这是一个测试"&dVoucherTicketAmount=0.88&strValidEndTime=2017/5/20&iIsValid=1&strRuleDesc=这是一个测试
	@ResponseBody
	@RequestMapping("insertVoucherTicketInfo")
	public String insertVoucherTicketInfo(HttpServletResponse response,HttpServletRequest request)
	{
		BigDecimal dVoucherTicketAmount;
		int iIsValid;
		String strVoucherTicketId=DataTool.getUUID();
		String strVoucherTicketName=request.getParameter("strVoucherTicketName");
		String strVoucherTicketAmount=request.getParameter("dVoucherTicketAmount");
		String strValidEndTime=request.getParameter("strValidEndTime");
		String strIsValid=request.getParameter("iIsValid");
		String strRuleDesc=request.getParameter("strRuleDesc");
		
		if(ValidateTool.isEmptyStr(strVoucherTicketName))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"抵用卷内容不能为空",null);
		
		if(ValidateTool.isEmptyStr(strVoucherTicketAmount))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"抵扣金额不能为空",null);
		else
		{
			if(isNumber(strVoucherTicketAmount))
				dVoucherTicketAmount=new BigDecimal(Double.parseDouble(strVoucherTicketAmount));
			else
				return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"抵扣金额格式错误",null);
		}
		
		if(ValidateTool.isEmptyStr(strValidEndTime))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"截止时间不能为空",null);
		
		strValidEndTime=DateTool.StringToString(strValidEndTime,DateStyle.YYYY_MM_DD_HH_MM);
		
		if(ValidateTool.isEmptyStr(strIsValid))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"启用状态不能为空",null);
		else
		{
			if(isNumber(strIsValid))
				iIsValid=Integer.parseInt(strIsValid);
			else
				return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"启用状态格式错误",null);
			
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
		//将数据写入实体
		VoucherTicketInfoEntity voucherTicketInfoEntity=new VoucherTicketInfoEntity();
		voucherTicketInfoEntity.setStrVoucherTicketId(strVoucherTicketId);
		voucherTicketInfoEntity.setStrVoucherTicketName(strVoucherTicketName);
		voucherTicketInfoEntity.setDvoucherTicketAmount(dVoucherTicketAmount);
		voucherTicketInfoEntity.setStrValidEndTime(strValidEndTime);
		voucherTicketInfoEntity.setIisValid(iIsValid);
		voucherTicketInfoEntity.setStrRuleDesc(strRuleDesc);
		voucherTicketInfoEntity.setStrEmployeeId(strEmployeeId);
		voucherTicketInfoEntity.setStrEmployeeName(strEmployeeName);
		voucherTicketInfoEntity.setStrEmployeeRealName(strEmployeeRealName);
		voucherTicketInfoEntity.setStrCreationTime(strCreationTime);
		voucherTicketInfoEntity.setStrLastAccessedTime(strLastAccessedTime);
		try{
			return voucherTicketInfoService.insertVoucherTicketInfo(voucherTicketInfoEntity);
			
		}catch(Exception e)
		{
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
		}
		
		
	}
	
	/**
	 * 分页查询 抵用卷信息
	 * @param response
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("selectVoucherTicketInfo")
	//http://localhost:8083/admin/biz/voucherTickeSetting/selectVoucherTicketInfo?iPageNum=0&iPageSize=0
	public String selectVoucherTicketInfo(HttpServletResponse response,HttpServletRequest request)
	{
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
		*/
		int iPageNum,iPageSize,iPageFrom,iTotalRecord,iTotalPage;
		String strPageNum=request.getParameter("iPageNum");
		String strPageSize=request.getParameter("iPageSize");
		if(ValidateTool.isEmptyStr(strPageNum))
			iPageNum=1;
		else
			{
			if(isNumber(strPageNum))
				{
				iPageNum=Integer.parseInt(strPageNum);
				if(iPageNum==0)
					iPageNum=1;
				}
			else
				return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"起始页数格式不对",null);
			}
		
		if(ValidateTool.isEmptyStr(strPageSize))
			iPageSize=StaticValue.PAGE_SIZE;
		else
		{
			if(isNumber(strPageSize))
				{
				iPageSize=Integer.parseInt(strPageSize);
				if(iPageSize==0)
					iPageSize=StaticValue.PAGE_SIZE;
				}
			else
				return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"每页要显示的记录条数格式不对",null);
			
		}
		
		try{
			iTotalRecord=voucherTicketInfoService.findRecordCount();	//查记录总条数
			if(iTotalRecord!=0)
			{
				iTotalPage=(iTotalRecord%iPageSize==0)?(iTotalRecord/iPageSize):(iTotalRecord/iPageSize)+1;	//计算总页数
				if(iPageNum>iTotalPage)
					iPageFrom=iTotalPage;							//如果请求页数超过计算出的总页数，则默认是请求最后一页
				iPageFrom=(iPageNum-1)*iPageSize;
				Map<String,Object> queryMap = new HashMap<String,Object>();
				queryMap.put("iPageFrom", iPageFrom);
				queryMap.put("iPageSize", iPageSize);
				List<VoucherTicketInfoEntity> listVoucherTicketInfoEntity=voucherTicketInfoService.selectVoucherTicketInfo(queryMap);
				if(listVoucherTicketInfoEntity==null||listVoucherTicketInfoEntity.size()==0)
					return DataTool.constructResponse(ResultCode.NO_DATA,"暂无数据",null);
				else
				{
					Map<String,Object> resultMap=new HashMap<String,Object>();
					resultMap.put("listVoucherTicketInfoEntity", listVoucherTicketInfoEntity);
					resultMap.put("iTotalRecord", iTotalRecord);
					resultMap.put("iTotalPage", iTotalPage);
					return DataTool.constructResponse(ResultCode.OK,"查询成功",resultMap);
				}
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
	 * 更新 抵用券信息
	 * @param response
	 * @param request
	 * @return
	 */
	
	@ResponseBody
	@RequestMapping("updateVoucherTicketInfo")
	//localhost:8083/admin/biz/voucherTickeSetting/updateVoucherTicketInfo?strVoucherTicketId=bbcb63355d7d49a69e41e5e405cd84cf&strVoucherTicketName=this is a test&dVoucherTicketAmount=1000&strValidEndTime=2017/5/20&iIsValid=1&strRuleDesc=this is a test
	public String updateVoucherTicketInfo(HttpServletResponse response,HttpServletRequest request)
	{
		BigDecimal dVoucherTicketAmount;
		int iIsValid;
		String strVoucherTicketId=request.getParameter("strVoucherTicketId");
		String strVoucherTicketName=request.getParameter("strVoucherTicketName");
		String strVoucherTicketAmount=request.getParameter("dVoucherTicketAmount");
		String strValidEndTime=request.getParameter("strValidEndTime");
		String strIsValid=request.getParameter("iIsValid");
		String strRuleDesc=request.getParameter("strRuleDesc");
		if(ValidateTool.isEmptyStr(strVoucherTicketId))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"抵用卷ID不能为空",null);	
		
		if(ValidateTool.isEmptyStr(strVoucherTicketName))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"抵用卷内容不能为空",null);
			
		if(ValidateTool.isEmptyStr(strVoucherTicketAmount))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"抵扣金额不能为空",null);
		else
		{
			if(isNumber(strVoucherTicketAmount))
				dVoucherTicketAmount=new BigDecimal(Double.parseDouble(strVoucherTicketAmount));
			else
				return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"抵扣金额格式错误",null);
		}
			
		if(ValidateTool.isEmptyStr(strValidEndTime))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"截止时间不能为空",null);
		
		strValidEndTime=DateTool.StringToString(strValidEndTime,DateStyle.YYYY_MM_DD_HH_MM);
			
		if(ValidateTool.isEmptyStr(strIsValid))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"启用状态不能为空",null);
		else
		{
			if(isNumber(strIsValid))
				iIsValid=Integer.parseInt(strIsValid);
			else
				return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"启用状态格式错误",null);
				
		}
		
	/*检验身份有效性
		EmployeeEntity employeeEntity = null;
		try{
			employeeEntity=(EmployeeEntity)webSessionUtil.getWebSession(request, response).getAttribute("employeeEntity");
			} catch (Exception e) {
				e.printStackTrace();
				return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
			}
		if(employeeEntity==null) {
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
		//将数据写入实体
		VoucherTicketInfoEntity voucherTicketInfoEntity=new VoucherTicketInfoEntity();
		voucherTicketInfoEntity.setStrVoucherTicketId(strVoucherTicketId);
		voucherTicketInfoEntity.setStrVoucherTicketName(strVoucherTicketName);
		voucherTicketInfoEntity.setDvoucherTicketAmount(dVoucherTicketAmount);
		voucherTicketInfoEntity.setStrValidEndTime(strValidEndTime);
		voucherTicketInfoEntity.setIisValid(iIsValid);
		voucherTicketInfoEntity.setStrRuleDesc(strRuleDesc);
		voucherTicketInfoEntity.setStrEmployeeId(strEmployeeId);
		voucherTicketInfoEntity.setStrEmployeeName(strEmployeeName);
		voucherTicketInfoEntity.setStrEmployeeRealName(strEmployeeRealName);
		voucherTicketInfoEntity.setStrLastAccessedTime(strLastAccessedTime);
		try{
			return voucherTicketInfoService.updateVoucherTicketInfo(voucherTicketInfoEntity);
		}catch(Exception e)
			{
				e.printStackTrace();
				return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
			}
			
			
		}
	
	
	
	
	/**
	 * 查询单条 抵用券信息
	 * @param response
	 * @param request
	 * @return
	 */
	
	@ResponseBody
	@RequestMapping("findVoucherTicketInfoById")
	//localhost:8083/admin/biz/voucherTickeSetting/findVoucherTicketInfoById?strVoucherTicketId=b06c5cdc4936473384a9465e3b900731
	public String findVoucherTicketInfoById(HttpServletResponse response,HttpServletRequest request)
	{
		String strVoucherTicketId=request.getParameter("strVoucherTicketId");
		if(ValidateTool.isEmptyStr(strVoucherTicketId))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"抵用卷ID不能为空",null);	
		/*检验身份有效性
		EmployeeEntity employeeEntity = null;
		try{
			employeeEntity=(EmployeeEntity)webSessionUtil.getWebSession(request, response).getAttribute("employeeEntity");
			} catch (Exception e) {
				e.printStackTrace();
				return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
			}
		if(employeeEntity==null) {
			return DataTool.constructResponse(ResultCode.NO_DATA, "操作员不存在", null);
			}
		*/	
		try{
			VoucherTicketInfoEntity voucherTicketInfoEntity=voucherTicketInfoService.findVoucherTicketInfoById(strVoucherTicketId);
			if(voucherTicketInfoEntity==null)
				return DataTool.constructResponse(ResultCode.NO_DATA,"还没有数据",null);
			else
			{
				Map<String,Object> resultMap=new HashMap<String,Object>();
				resultMap.put("voucherTicketInfoEntity", voucherTicketInfoEntity);
				return DataTool.constructResponse(ResultCode.OK,"查询成功",resultMap);
			}
		}catch(Exception e)
			{
				e.printStackTrace();
				return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
			}
			
	}
			
	/**
	 * 删除 抵用券信息
	 * @param response
	 * @param request
	 * @return
	 */
	
	@ResponseBody
	@RequestMapping("deleteVoucherTicketInfo")
	//localhost:8083/admin/biz/voucherTickeSetting/deleteVoucherTicketInfo?strVoucherTicketId=bbcb63355d7d49a69e41e5e405cd84cf
	public String deleteVoucherTicketInfo(HttpServletResponse response,HttpServletRequest request)
	{
		/*检验身份有效性
		EmployeeEntity employeeEntity = null;
		try{
			employeeEntity=(EmployeeEntity)webSessionUtil.getWebSession(request, response).getAttribute("employeeEntity");
			} catch (Exception e) {
				e.printStackTrace();
				return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
			}
		if(employeeEntity==null) {
			return DataTool.constructResponse(ResultCode.NO_DATA, "操作员不存在", null);
			}
		*/
		String strVoucherTicketId=request.getParameter("strVoucherTicketId");
		if(ValidateTool.isEmptyStr(strVoucherTicketId))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"主键不能为空",null);
		try{
			return voucherTicketInfoService.deleteVoucherTicketInfo(strVoucherTicketId);
		}catch(Exception e)
		{
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
		}
		
	}
	
	
	
	//数字校验
	public static boolean isNumber(String strCheckString)
	{
		boolean flag=false;
		Pattern p1 = Pattern.compile("^[0-9]\\d*$|^[1-9]\\d*\\.\\d{1,2}$|^0\\.\\d{1,2}$");
		Matcher matcher=p1.matcher(strCheckString);
		flag=matcher.matches();   
		return flag;
	}

}
