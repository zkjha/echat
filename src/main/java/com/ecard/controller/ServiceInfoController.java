

/**
 * fun:
 * author:qidongbo
 * create time:2017年3月22日
 */
package com.ecard.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.commontools.data.DataTool;
import com.commontools.date.DateStyle;
import com.commontools.date.DateTool;
import com.commontools.validate.ValidateTool;
import com.ecard.config.ResultCode;
import com.ecard.config.StaticValue;
import com.ecard.entity.ServiceInfoEntity;
import com.ecard.service.ServiceInfoService;

/**
 * @author apple
 *
 */
@Controller
@RequestMapping("/admin/biz/Service")
public class ServiceInfoController {
	
	@Resource
	private ServiceInfoService tServiceInfoService;
	
	//插入一条服务项目信息
	// http://localhost:8082/admin/biz/Service/insertServiceInfo?strServiceInfoName=qidongbo&strServiceTypeId=20e3c241740e47438f25d91bdefc9d9d&strServiceInfoDesc=nfiahfoahf&strServiceTypeName=你好好&dSalePrice=300
	@ResponseBody
	@RequestMapping("insertServiceInfo")
	public String insertServiceInfo(HttpServletRequest request, HttpServletResponse response){

	    //获取传入参数
	    String strServiceInfoName = request.getParameter("strServiceInfoName");
	    String strServiceTypeId = request.getParameter("strServiceTypeId");
	    String strServiceTypeName = request.getParameter("strServiceTypeName");
	    String dSalePrice = request.getParameter("dSalePrice");
	    /*
	    String strEmployeeId = request.getParameter("strEmployeeId");
	    String strEmployeeName = request.getParameter("strEmployeeName");
	    String strEmployeeLoginName = request.getParameter("strEmployeeLoginName");
	    */
	    String strServiceInfoDesc = request.getParameter("strServiceInfoDesc");
	    String strReserved = request.getParameter("strReserved");


	    //判断参数有效性
	    if(strServiceInfoName == null || strServiceInfoName.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数strServiceInfoName不能为空", null);
	    }
	    if(strServiceTypeId == null || strServiceTypeId.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数strServiceTypeId不能为空", null);
	    }
	    if(strServiceTypeName == null || strServiceTypeName.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数strServiceTypeName不能为空", null);
	    }
	    if(dSalePrice == null || dSalePrice.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数dSalePrice不能为空", null);
	    }
	    if(strServiceInfoDesc == null || strServiceInfoDesc.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数strServiceInfoDesc不能为空", null);
	    }


	    //对象设置
	    ServiceInfoEntity tServiceInfo=new ServiceInfoEntity();
	    tServiceInfo.setStrServiceInfoId(DataTool.getUUID());
	    tServiceInfo.setStrServiceInfoName(strServiceInfoName);
	    tServiceInfo.setStrServiceTypeId(strServiceTypeId);
	    tServiceInfo.setStrServiceTypeName(strServiceTypeName);
	    
	    BigDecimal bgAmount = new BigDecimal(dSalePrice);
	    tServiceInfo.setdSalePrice(bgAmount);
	    
	    tServiceInfo.setStrEmployeeId("11111");
	    tServiceInfo.setStrEmployeeName("11111");
	    tServiceInfo.setStrEmployeeLoginName("11111");
	    tServiceInfo.setStrServiceInfoDesc(strServiceInfoDesc);
	    tServiceInfo.setStrInsertTime(DateTool.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
	    tServiceInfo.setStrUpdateTime(DateTool.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
	    tServiceInfo.setStrReserved(strReserved);


	    try{
	        return tServiceInfoService.insertServiceInfo(tServiceInfo);
	    }
	    catch(Exception e)
	    {
	        e.printStackTrace();
	        return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "新增失败", null);
	    }
	}


	//更新一条ServiceInfo记录 29e9ab1eb97444348b8540d161bae5f8
	// http://localhost:8082/admin/biz/Service/updateServiceInfo?strServiceInfoId=29e9ab1eb97444348b8540d161bae5f8&strServiceInfoName=qidddddongbo&strServiceTypeId=20e3c241740e47438f25d91bdefc9d9d&strServiceInfoDesc=nfiahfoahf&strServiceTypeName=你好好&dSalePrice=300
	@ResponseBody
	@RequestMapping("updateServiceInfo")
	public String updateServiceInfo(HttpServletRequest request, HttpServletResponse response){

	    String strServiceInfoId = request.getParameter("strServiceInfoId");
	    String strServiceInfoName = request.getParameter("strServiceInfoName");
	    String strServiceTypeId = request.getParameter("strServiceTypeId");
	    String strServiceTypeName = request.getParameter("strServiceTypeName");
	    String dSalePrice = request.getParameter("dSalePrice");
	    String strReserved = request.getParameter("strReserved");
	    if(strServiceInfoId == null || strServiceInfoId.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数strServiceInfoId不能为空", null);
	    }
	    ServiceInfoEntity tServiceInfo=new ServiceInfoEntity();
	    tServiceInfo.setStrServiceInfoId(strServiceInfoId);
	    tServiceInfo.setStrServiceInfoName(strServiceInfoName);
	    tServiceInfo.setStrServiceTypeId(strServiceTypeId);
	    tServiceInfo.setStrServiceTypeName(strServiceTypeName);
	    
	    BigDecimal bgAmount = new BigDecimal(dSalePrice);
	    tServiceInfo.setdSalePrice(bgAmount);

	    tServiceInfo.setStrEmployeeName("22222");
	    tServiceInfo.setStrEmployeeLoginName("22222");
	    tServiceInfo.setStrServiceInfoDesc("22222");
	    tServiceInfo.setStrUpdateTime(DateTool.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
	    tServiceInfo.setStrReserved(strReserved);
	    try{
	        return tServiceInfoService.updateServiceInfo(tServiceInfo);
	    }
	    catch(Exception e)
	    {
	        e.printStackTrace();
	        return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "更新失败", null);
	    }
	}


	//删除一条ServiceInfo记录
	// http://localhost:8082/admin/biz/Service/delServiceInfo?strServiceInfoId=29e9ab1eb97444348b8540d161bae5f8
	@ResponseBody
	@RequestMapping("delServiceInfo")
	public String delServiceInfo(HttpServletRequest request, HttpServletResponse response){

	    String strServiceInfoId = request.getParameter("strServiceInfoId");
	    if(strServiceInfoId == null || strServiceInfoId.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数strServiceInfoId不能为空", null);
	    }
	    try{
	        return tServiceInfoService.deleteServiceInfo(strServiceInfoId);
	    }
	    catch(Exception e)
	    {
	        e.printStackTrace();
	        return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "删除失败", null);
	    }
	}


	//获取ServiceInfo列表
	// http://localhost:8082/admin/biz/Service/getListServiceInfo?pagenum=1&pagesize=1
	@ResponseBody
	@RequestMapping("getListServiceInfo")
	public String getListServiceInfo(HttpServletRequest request, HttpServletResponse response){

	    String pagenum = request.getParameter("pagenum");
	    String pagesize = request.getParameter("pagesize");

	    if (ValidateTool.isEmptyStr(pagenum))
	    {
	        pagenum = "1";
	    }
	    int iPagesize = StaticValue.PAGE_SIZE;
	    if (!ValidateTool.isEmptyStr(pagesize))
	    {
	        iPagesize = Integer.valueOf(pagesize);
	    }
	    int pageFrom = (Integer.parseInt(pagenum) - 1) * iPagesize;
	    Map<String, Object> queryMap = new HashMap<String, Object>();
	    queryMap.put("pageFrom", pageFrom);
	    queryMap.put("pageSize", iPagesize);
	    try{
	        List<ServiceInfoEntity> listServiceInfo= tServiceInfoService.getListServiceInfo(queryMap);
	        if (ValidateTool.isNull(listServiceInfo) || listServiceInfo.size() <= 0)
	        {
	            return DataTool.constructResponse(ResultCode.NO_DATA, "暂无数据", null);
	        }
	        else
	        {
	            int totalrecord = tServiceInfoService.getServiceInfoTotalCount(queryMap);
	            Map<String, Object> resultMap = new HashMap<String, Object>();
	            resultMap.put("listServiceInfo", listServiceInfo);
	            resultMap.put("iTotalRecord", totalrecord);
	            resultMap.put("iTotalPage", totalrecord % iPagesize == 0 ? totalrecord / iPagesize : totalrecord / iPagesize + 1);
	            return DataTool.constructResponse(ResultCode.OK, "查询成功", resultMap);
	        }
	    }catch(Exception e) {
	        e.printStackTrace();
	        return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "查询失败", null);
	    }
	}
}
