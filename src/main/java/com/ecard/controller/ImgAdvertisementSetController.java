package com.ecard.controller;

import java.util.Date;

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
	 * 上移图片广告:参照：最上层：图片序号为1,向下序号依次为2，3,4...
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("imgAdvertisementForward")
	//localhost:8083/admin/biz/advSetting/imgAdvertisementForward?strImgId=xxxx
	public String imgAdvertisementForward(HttpServletResponse response,HttpServletRequest request)
	{
		String strImgId=request.getParameter("strImgId");
		if(ValidateTool.isEmptyStr(strImgId))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"ID不能为空", null);
		try{
			int rcdNum=imgAdvertisementSetService.imgAdvertisementForward(strImgId);
			if(rcdNum!=0)
				return DataTool.constructResponse(ResultCode.OK,"图片广告上移成功",null);
			else
				return DataTool.constructResponse(ResultCode.NO_DATA,"图片广告上移失败",null);
		}catch(Exception e)
		{	
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
		}
		
	}
	

}
