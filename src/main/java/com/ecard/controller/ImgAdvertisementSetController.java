package com.ecard.controller;

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
import com.ecard.entity.EmployeeEntity;
import com.ecard.entity.ImgAdvertisementEntity;
import com.ecard.service.ImgAdvertisementSetService;
import com.ecard.util.WebSessionUtil;

@Controller
@RequestMapping("/admin/biz/advSetting")
public class ImgAdvertisementSetController {
	@Autowired
	private WebSessionUtil webSessionUtil;
	@Autowired
	private ImgAdvertisementSetService imgAdvertisementSetService;
	
	/**
	 * 新增前端图片广告
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("insertImgAdvertisement")
	//localhost:8083/admin/biz/advSetting/insertImgAdvertisement?strImgName=img001
	public String insertImgAdvertisement(HttpServletRequest request,HttpServletResponse response)
	{	
		String strImgId=DataTool.getUUID();
		String strImgName=request.getParameter("strImgName");
		//iImgOrder后台生成
		if(ValidateTool.isEmptyStr(strImgName))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"请添加图片", null);
			
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
		//将数据装进ENTITY
		ImgAdvertisementEntity imgAdvertisementEntity=new ImgAdvertisementEntity();
		imgAdvertisementEntity.setStrImgId(strImgId);
		imgAdvertisementEntity.setStrImgName(strImgName);
		//iImgOrder属性在Service层取得并设置
		imgAdvertisementEntity.setStrCreationTime(strCreationTime);
		imgAdvertisementEntity.setStrLastAccessedTime(strLastAccessedTime);
		imgAdvertisementEntity.setStrEmployeeId(strEmployeeId);
		imgAdvertisementEntity.setStrEmployeeName(strEmployeeName);
		imgAdvertisementEntity.setStrEmployeeRealName(strEmployeeRealName);
		//调用Service层方法添加数据
		try{
		int rcdNum=imgAdvertisementSetService.insertImgAdvertisement(imgAdvertisementEntity);
		if(rcdNum!=0)
			return DataTool.constructResponse(ResultCode.OK,"插入图片广告成功",null);
		else
			return DataTool.constructResponse(ResultCode.NO_DATA,"插入数据失败",null);
		}catch(Exception e)
		{	
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
		}
		
	}
	
	/**
	 * 移动图片广告:参照：最上层：图片序号为1,向下序号依次为2，3,4...
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("moveImgAdvertisement")
	//localhost:8083/admin/biz/advSetting/moveImgAdvertisement?strImgId=39f107725781423bb39b036aad482a14&iImgOrder=1&strStatus=0
	public String moveImgAdvertisement(HttpServletResponse response,HttpServletRequest request)
	{
		int i=0,iImgOrder;
		String strImgId=request.getParameter("strImgId");
		String strImgOrder=request.getParameter("iImgOrder");
		String strStatus=request.getParameter("strStatus");//状态量：0上移,1下移
		if(ValidateTool.isEmptyStr(strImgId))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"ID不能为空", null);
		if(ValidateTool.isEmptyStr(strStatus))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"状态不能为空", null);
		if(!isNumber(strStatus))
			return DataTool.constructResponse(ResultCode.NO_DATA,"请写数字:0表示向上移动,1 表示向下移动", null);
		else
			{
			i=Integer.parseInt(strStatus);
			if(i>=1)
				strStatus="1"; 
			if(i<=0)
				strStatus="0"; 
			}
		if(ValidateTool.isEmptyStr(strImgOrder))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"图片序号不能为空", null);
		else {
				if(isNumber(strImgOrder))
					iImgOrder=Integer.parseInt(strImgOrder);
				else
					return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"图片序号格式不对!", null);
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
		String strLastAccessedTime=DateTool.DateToString(new Date(),DateStyle.YYYY_MM_DD_HH_MM);
		//以下3个为测试用数据
		String strEmployeeId=DataTool.getUUID();
		String strEmployeeName="admin";
		String strEmployeeRealName="david li";
		//将数据装进ENTITY
		ImgAdvertisementEntity imgAdvertisementEntity=new ImgAdvertisementEntity();
		imgAdvertisementEntity.setStrImgId(strImgId);
		imgAdvertisementEntity.setIImgOrder(iImgOrder);
		imgAdvertisementEntity.setStrLastAccessedTime(strLastAccessedTime);
		imgAdvertisementEntity.setStrEmployeeId(strEmployeeId);
		imgAdvertisementEntity.setStrEmployeeName(strEmployeeName);
		imgAdvertisementEntity.setStrEmployeeRealName(strEmployeeRealName);
		//调用Service层方法添加数据
		try{
			int rcdNum=imgAdvertisementSetService.moveImgAdvertisement(imgAdvertisementEntity,strStatus);
			if(rcdNum==0)
				return DataTool.constructResponse(ResultCode.NO_DATA,"图片广告移动失败",null);
			else if(rcdNum==-1)
				return DataTool.constructResponse(ResultCode.NO_DATA,"该图片广告已经在最上层了！",null);
			else if(rcdNum==1)
				return DataTool.constructResponse(ResultCode.NO_DATA,"该图片广告已经在最下层了",null);
			else
				return DataTool.constructResponse(ResultCode.OK,"图片广告移动成功",null);
		}catch(Exception e)
		{	
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
		}
		
	}
	
	
	/**
	 *删除图片广告
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("delImgAdvertisement")
	//localhost:8083/admin/biz/advSetting/delImgAdvertisement?strImgId=7aece3d41568448999910ce38f16c0d9
	public String delImgAdvertisement(HttpServletRequest request,HttpServletResponse response)
	{
		int rcdNum=0;
		String strImgId=request.getParameter("strImgId");
		if(ValidateTool.isEmptyStr(strImgId))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "ID不能为空", null);
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
			rcdNum=imgAdvertisementSetService.delImgAdvertisement(strImgId);
		}catch(Exception e)
		{
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
		}
		if(rcdNum!=0)
			return DataTool.constructResponse(ResultCode.OK,"删除成功", null);
		else
			return DataTool.constructResponse(ResultCode.NO_DATA, "删除操作失败", null);
			
	}
	
	
	/**
	 *查询所有图片广告
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("findAllImaggeAdvertisement")
	//localhost:8083/admin/biz/advSetting/findAllImaggeAdvertisement
	public String findAllImaggeAdvertisement(HttpServletResponse response,HttpServletRequest request)
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
		try{
			List<ImgAdvertisementEntity> listImgAdvertisementEntity=imgAdvertisementSetService.findAllImaggeAdvertisement();
			if(listImgAdvertisementEntity==null||listImgAdvertisementEntity.size()==0)
				return DataTool.constructResponse(ResultCode.NO_DATA, "暂无数据", null);
			else
				{
					Map<String,Object> resultMap=new HashMap<String,Object>();
					resultMap.put("listImgAdvertisementEntity", listImgAdvertisementEntity);
					return DataTool.constructResponse(ResultCode.OK, "查询成功", resultMap);
				}
		}catch (Exception e)
		{
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
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
