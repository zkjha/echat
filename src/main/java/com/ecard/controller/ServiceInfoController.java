

/**
 * fun:
 * author:qidongbo
 * create time:2017年3月22日
 */
package com.ecard.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
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
import com.ecard.entity.ServicePreferentialEntity;
import com.ecard.service.ServiceInfoService;

/**
 * author kinglong
 *
 */
@Controller
@RequestMapping("/admin/biz/Service")
public class ServiceInfoController {
	
	@Resource
	private ServiceInfoService tServiceInfoService;
	
	//插入一条服务项目信息
	// http://localhost:8082/admin/biz/Service/insertServiceInfo?strServicePreferentialInfos=39111,好的,4666|222,好的2,56666|&txtServiceDescDetail=idididididididi&txtServiceDesc=nifahfiaidfhasdnfi&iState=1&iPreferentialType=1&strSupplierName=qidongbo&strUnitName=张&strUnitId=123&strServiceBarCode=123456789&strServiceTypeId=1a86d3a79c15437698255b72e4a0fde4&strServiceTypeName=234&strServiceInfoName=1111&dSalePrice=6&iStock=100	
	@ResponseBody
	@RequestMapping("insertServiceInfo")
	public String insertServiceInfo(HttpServletRequest request, HttpServletResponse response){

	    //获取传入参数
	    String strServiceInfoName = request.getParameter("strServiceInfoName");
	    String strServiceTypeId = request.getParameter("strServiceTypeId");
	    String strServiceTypeName = request.getParameter("strServiceTypeName");
	    String dSalePrice = request.getParameter("dSalePrice");
	    String strServiceBarCode = request.getParameter("strServiceBarCode");
	    String strUnitId = request.getParameter("strUnitId");
	    String strUnitName = request.getParameter("strUnitName");
	    String strSupplierName = request.getParameter("strSupplierName");
	    String strPreferentialType = request.getParameter("iPreferentialType");
	    String strState = request.getParameter("iState");
	    String txtServiceDesc = request.getParameter("txtServiceDesc");
	    String txtServiceDescDetail = request.getParameter("txtServiceDescDetail");
	    String strReserved = request.getParameter("strReserved");
	    
	    String strServicePreferentialInfos = request.getParameter("strServicePreferentialInfos");


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
	    if(strServiceBarCode == null || strServiceBarCode.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数strServiceBarCode不能为空", null);
	    }
	    if(strUnitId == null || strUnitId.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数strUnitId不能为空", null);
	    }
	    if(strUnitName == null || strUnitName.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数strUnitName不能为空", null);
	    }
	    if(strSupplierName == null || strSupplierName.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数strSupplierName不能为空", null);
	    }
	    if(strPreferentialType == null || strPreferentialType.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数iPreferentialType不能为空", null);
	    }
	    if(strState == null || strState.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数iState不能为空", null);
	    }
	    if (strPreferentialType.equals("1"))
	    {
		    if(strServicePreferentialInfos == null || strServicePreferentialInfos.isEmpty())
		    {
		        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数strServicePreferentialInfos不能为空", null);
		    }
		    
	    }


	    //对象设置
	    ServiceInfoEntity tServiceInfo=new ServiceInfoEntity();
	    tServiceInfo.setStrServiceInfoId(DataTool.getUUID());
	    
	    BigDecimal bgAmount = new BigDecimal(dSalePrice);
	    tServiceInfo.setdSalePrice(bgAmount);

	    tServiceInfo.setStrServiceInfoName(strServiceInfoName);
	    tServiceInfo.setStrServiceTypeId(strServiceTypeId);
	    tServiceInfo.setStrServiceTypeName(strServiceTypeName);
	    tServiceInfo.setStrServiceBarCode(strServiceBarCode);
	    tServiceInfo.setStrUnitId(strUnitId);
	    tServiceInfo.setStrUnitName(strUnitName);
	    tServiceInfo.setStrSupplierName(strSupplierName);
	    tServiceInfo.setiPreferentialType(Integer.parseInt(strPreferentialType));
	    tServiceInfo.setiState(Integer.parseInt(strState));
	    tServiceInfo.setTxtServiceDesc(txtServiceDesc);
	    tServiceInfo.setTxtServiceDescDetail(txtServiceDescDetail);
	    
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
			*/
	    
	    tServiceInfo.setStrEmployeeId("11111");
	    tServiceInfo.setStrEmployeeName("11111");
	    tServiceInfo.setStrEmployeeLoginName("11111");
	    tServiceInfo.setStrInsertTime(DateTool.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
	    tServiceInfo.setStrUpdateTime(DateTool.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
	    tServiceInfo.setStrReserved(strReserved);
	    
	    
	    //解析商品会员优惠信息  会员ID1,会员名称1,兑换积分数1｜会员ID2,会员名称2,兑换积分数2｜
	    String [] arrServicePreferentialInfos = strServicePreferentialInfos.split("\\|");
	    System.out.println(strServicePreferentialInfos);
	    List<ServicePreferentialEntity> listServicePreferentialEntity = new ArrayList<ServicePreferentialEntity>();
	    if (arrServicePreferentialInfos != null)
	    {
	    	int iRight = 1;
	    	
	    	for (int iLoop = 0; iLoop < arrServicePreferentialInfos.length; iLoop++ )
	    	{
	    		ServicePreferentialEntity tServicePreferentialEntity = new ServicePreferentialEntity();
	    		
	    		// 解析单行
	    		String [] arrOneLineStrings = arrServicePreferentialInfos[iLoop].split(",");
	    		System.out.println(arrServicePreferentialInfos[iLoop]);
	    		
	    		if (arrOneLineStrings == null)
	    		{
	    			iRight = 0;
	    			break;
	    		}
	    		
	    		if( arrOneLineStrings.length != 3)
	    		{
	    			iRight = 0;
	    			break;
	    		}

	    		tServicePreferentialEntity.setStrLevelsId(arrOneLineStrings[0]);
	    		tServicePreferentialEntity.setStrLevelsName(arrOneLineStrings[1]);
	    		tServicePreferentialEntity.setiRequiredIntegral(Integer.parseInt(arrOneLineStrings[2]));
	    		
	    		listServicePreferentialEntity.add(tServicePreferentialEntity);
	    	}
	    	
	    	if (0 == iRight)
	    	{
	    		return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "会员优惠数据格式错误", null);
	    	}
	    }
	    tServiceInfo.setListServicePreferentialEntity(listServicePreferentialEntity);


	    try{
	        return tServiceInfoService.insertServiceInfo(tServiceInfo);
	    }
	    catch(Exception e)
	    {
	        e.printStackTrace();
	        return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "新增失败", null);
	    }
	}
	
	
	//获取一条ServiceInfo记录
	// http://localhost:8082/admin/biz/Service/getServiceInfo?strServiceInfoId=8e3a74c0e03a407ea5cecfbb79a76893
	@ResponseBody
	@RequestMapping("getServiceInfo")
	public String getServiceInfo(HttpServletRequest request, HttpServletResponse response){

	    String strServiceInfoId = request.getParameter("strServiceInfoId");
	    Map<String,Object> resultMap = new HashMap<String, Object>();
	  
	    if(strServiceInfoId == null || strServiceInfoId.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数strServiceInfoId不能为空", null);
	    }
	    try{
	        ServiceInfoEntity tServiceInfo= tServiceInfoService.getServiceInfo(strServiceInfoId);
	        if (tServiceInfo == null || tServiceInfo.getStrServiceInfoId().isEmpty() || tServiceInfo.getStrServiceInfoId() == null)
	        {
	            return DataTool.constructResponse(ResultCode.NO_DATA, "数据不存在", null);
	        }
	        else
	        {
	            resultMap.put("ServiceInfo", tServiceInfo);
	            return DataTool.constructResponse(ResultCode.OK, "查询成功", resultMap);
	         }
	    }catch(Exception e) {
	        e.printStackTrace();
	        return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "查询失败", null);
	    }
	}


	//更新一条ServiceInfo记录 29e9ab1eb97444348b8540d161bae5f8
	// http://localhost:8082/admin/biz/Service/updateServiceInfo?strServiceInfoId=8e3a74c0e03a407ea5cecfbb79a76893&strServicePreferentialInfos=45d5462e6ccf453c9ab967fe00650207,39111,好的,4666888|eb7bb36a12ef44429414b36bf15df694,222,好的2,58888|&txtServiceDescDetail=idididididididi&txtServiceDesc=nifahfiaidfhasdnfi&iState=1&iPreferentialType=1&strSupplierName=qidongbo&strUnitName=张&strUnitId=123&strServiceBarCode=123456789&strServiceTypeId=1a86d3a79c15437698255b72e4a0fde4&strServiceTypeName=234&strServiceInfoName=1111&dSalePrice=6&iStock=100	
	
	@ResponseBody
	@RequestMapping("updateServiceInfo")
	public String updateServiceInfo(HttpServletRequest request, HttpServletResponse response){

	    String strServiceInfoId = request.getParameter("strServiceInfoId");
	    String strServiceInfoName = request.getParameter("strServiceInfoName");
	    String strServiceTypeId = request.getParameter("strServiceTypeId");
	    String strServiceTypeName = request.getParameter("strServiceTypeName");
	    String dSalePrice = request.getParameter("dSalePrice");
	    String strServiceBarCode = request.getParameter("strServiceBarCode");
	    String strUnitId = request.getParameter("strUnitId");
	    String strUnitName = request.getParameter("strUnitName");
	    String strSupplierName = request.getParameter("strSupplierName");
	    String strPreferentialType = request.getParameter("iPreferentialType");
	    String strState = request.getParameter("iState");
	    String txtServiceDesc = request.getParameter("txtServiceDesc");
	    String txtServiceDescDetail = request.getParameter("txtServiceDescDetail");
	    String strReserved = request.getParameter("strReserved");
	    
	    String strServicePreferentialInfos = request.getParameter("strServicePreferentialInfos");


	    //判断参数有效性
	    if(strServiceInfoId == null || strServiceInfoId.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数strServiceInfoId不能为空", null);
	    }
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
	    if(strServiceBarCode == null || strServiceBarCode.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数strServiceBarCode不能为空", null);
	    }
	    if(strUnitId == null || strUnitId.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数strUnitId不能为空", null);
	    }
	    if(strUnitName == null || strUnitName.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数strUnitName不能为空", null);
	    }
	    if(strSupplierName == null || strSupplierName.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数strSupplierName不能为空", null);
	    }
	    if(strPreferentialType == null || strPreferentialType.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数iPreferentialType不能为空", null);
	    }
	    if(strState == null || strState.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数iState不能为空", null);
	    }
	    
	    if (strPreferentialType.equals("1"))
	    {
		    if(strServicePreferentialInfos == null || strServicePreferentialInfos.isEmpty())
		    {
		        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数strServicePreferentialInfos不能为空", null);
		    }
		    
	    }


	    //对象设置
	    ServiceInfoEntity tServiceInfo=new ServiceInfoEntity();
	    tServiceInfo.setStrServiceInfoId(strServiceInfoId);
	    
	    BigDecimal bgAmount = new BigDecimal(dSalePrice);
	    tServiceInfo.setdSalePrice(bgAmount);

	    tServiceInfo.setStrServiceInfoName(strServiceInfoName);
	    tServiceInfo.setStrServiceTypeId(strServiceTypeId);
	    tServiceInfo.setStrServiceTypeName(strServiceTypeName);
	    tServiceInfo.setStrServiceBarCode(strServiceBarCode);
	    tServiceInfo.setStrUnitId(strUnitId);
	    tServiceInfo.setStrUnitName(strUnitName);
	    tServiceInfo.setStrSupplierName(strSupplierName);
	    tServiceInfo.setiPreferentialType(Integer.parseInt(strPreferentialType));
	    tServiceInfo.setiState(Integer.parseInt(strState));
	    tServiceInfo.setTxtServiceDesc(txtServiceDesc);
	    tServiceInfo.setTxtServiceDescDetail(txtServiceDescDetail);
	    
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
			*/

	    tServiceInfo.setStrEmployeeId("2222");
	    tServiceInfo.setStrEmployeeName("22222");
	    tServiceInfo.setStrEmployeeLoginName("22222");
	    tServiceInfo.setStrUpdateTime(DateTool.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
	    tServiceInfo.setStrReserved(strReserved);
	    
	    
	    //解析商品会员优惠信息  优惠信息ID,会员ID1,会员名称1,兑换积分数1｜优惠信息ID,会员ID2,会员名称2,兑换积分数2｜
	    String [] arrServicePreferentialInfos = strServicePreferentialInfos.split("\\|");
	    List<ServicePreferentialEntity> listServicePreferentialEntity = new ArrayList<ServicePreferentialEntity>();
	    if (arrServicePreferentialInfos != null)
	    {
	    	int iRight = 1;
	    	
	    	for (int iLoop = 0; iLoop < arrServicePreferentialInfos.length; iLoop++ )
	    	{
	    		ServicePreferentialEntity tServicePreferentialEntity = new ServicePreferentialEntity();
	    		
	    		// 解析单行
	    		String [] arrOneLineStrings = arrServicePreferentialInfos[iLoop].split(",");
	    		if (arrOneLineStrings == null)
	    		{
	    			iRight = 0;
	    			break;
	    		}
	    		if(arrOneLineStrings.length != 4)
	    		{
	    			iRight = 0;
	    			break;
	    		}
	    		tServicePreferentialEntity.setStrPreferentialId(arrOneLineStrings[0]);
	    		tServicePreferentialEntity.setStrLevelsId(arrOneLineStrings[1]);
	    		tServicePreferentialEntity.setStrLevelsName(arrOneLineStrings[2]);
	    		tServicePreferentialEntity.setiRequiredIntegral(Integer.parseInt(arrOneLineStrings[3]));
	    		
	    		listServicePreferentialEntity.add(tServicePreferentialEntity);
	    	}
	    	
	    	if (0 == iRight)
	    	{
	    		return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "会员优惠数据格式错误", null);
	    	}
	    }
	    tServiceInfo.setListServicePreferentialEntity(listServicePreferentialEntity);
	    
	    
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
	        if (ValidateTool.isNull(listServiceInfo))
	            return DataTool.constructResponse(ResultCode.NO_DATA, "暂无数据", null);
	        
	        if(listServiceInfo.size() <= 0)
	        	return DataTool.constructResponse(ResultCode.NO_DATA, "暂无数据", null);
	       
	        int totalrecord = tServiceInfoService.getServiceInfoTotalCount(queryMap);
	        Map<String, Object> resultMap = new HashMap<String, Object>();
	        resultMap.put("listServiceInfo", listServiceInfo);
	        resultMap.put("iTotalRecord", totalrecord);
	        resultMap.put("iTotalPage", totalrecord % iPagesize == 0 ? totalrecord / iPagesize : totalrecord / iPagesize + 1);
	        return DataTool.constructResponse(ResultCode.OK, "查询成功", resultMap);
	        
	    }catch(Exception e) {
	        e.printStackTrace();
	        return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "查询失败", null);
	    }
	}
	
	
	//获取ServiceInfo列表
	// http://localhost:8082/admin/biz/Service/getListServicePreferentialByServiceId?strServiceInfoId=8e3a74c0e03a407ea5cecfbb79a76893
	@ResponseBody
	@RequestMapping("getListServicePreferentialByServiceId")
	public String getListServicePreferentialByServiceId(HttpServletRequest request, HttpServletResponse response){

	    String strServiceInfoId = request.getParameter("strServiceInfoId");
	    if(strServiceInfoId == null || strServiceInfoId.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数strServiceInfoId不能为空", null);
	    }
	    
	    try{
	        List<ServicePreferentialEntity> listServicePreferentialEntity= tServiceInfoService.getListServicePreferentialByServiceId(strServiceInfoId);
	        if (ValidateTool.isNull(listServicePreferentialEntity))
	            return DataTool.constructResponse(ResultCode.NO_DATA, "暂无数据", null);
	     
	        if( listServicePreferentialEntity.size() <= 0)
	        	return DataTool.constructResponse(ResultCode.NO_DATA, "暂无数据", null);
	        
	        Map<String, Object> resultMap = new HashMap<String, Object>();
	        resultMap.put("listServicePreferentialEntity", listServicePreferentialEntity);
             return DataTool.constructResponse(ResultCode.OK, "查询成功", resultMap);
	       
	    }catch(Exception e) {
	        e.printStackTrace();
	        return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "查询失败", null);
	    }
	}
}
