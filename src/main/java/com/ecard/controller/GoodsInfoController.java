

/**
 * fun:
 * author:qidongbo
 * create time:2017年3月21日
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
import com.ecard.entity.GoodsInfoEntity;
import com.ecard.entity.GoodsPreferentialEntity;
import com.ecard.service.GoodsInfoService;

/**
 * @author apple
 *
 */
@Controller
@RequestMapping("/admin/biz/goods")
public class GoodsInfoController {
	
	@Resource
	private GoodsInfoService tGoodsInfoService;
	
	//获取一条GoodsInfo记录
	// localhost:8082/admin/biz/goods/getGoodsInfo?strGoodsId=7d25959d50ab422cbfef93b7eba99676
	@ResponseBody
	@RequestMapping("getGoodsInfo")
	public String getGoodsInfo(HttpServletRequest request, HttpServletResponse reponse){

	    String strGoodsId = request.getParameter("strGoodsId");
	    Map<String,Object> resultMap = new HashMap<String, Object>();
	  
	    if(strGoodsId == null || strGoodsId.isEmpty())
	    {

	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数strGoodsId不能为空", null);
	    }
	    try{
	        GoodsInfoEntity tGoodsInfo= tGoodsInfoService.getGoodsInfo(strGoodsId);
	        if (tGoodsInfo == null || tGoodsInfo.getStrGoodsId().isEmpty() || tGoodsInfo.getStrGoodsId() == null)
	        {
	            return DataTool.constructResponse(ResultCode.NO_DATA, "数据不存在", null);
	        }
	        else
	        {
	            resultMap.put("GoodsInfo", tGoodsInfo);
	            return DataTool.constructResponse(ResultCode.OK, "查询成功", resultMap);
	         }
	    }catch(Exception e) {
	        e.printStackTrace();
	        return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "查询失败", null);
	    }
	}


	//新增一条GoodsInfo记录
	// http://localhost:8082/admin/biz/goods/insertGoodsInfo?strGoodsPreferentials=39111,好的,4666|222,好的2,56666|&txtGoodsDescDetail=idididididididi&txtGoodsDesc=nifahfiaidfhasdnfi&iState=1&iPreferentialType=1&iRequiredIntegral=50000&strSupplierName=qidongbo&strUnitName=张&strUnitId=123&strGoodsBarCode=123456789&strGoodsTypeId=99d9b574c8f340d0ad8102ad8f46a6ce&strGoodsTypeName=234&strGoodsName=1111&dEnterPrice=3&dSalePrice=6&iStock=100

	@ResponseBody
	@RequestMapping("insertGoodsInfo")
	public String insertGoodsInfo(HttpServletRequest request, HttpServletResponse response){

	    //获取传入参数
	    String strGoodsBarCode = request.getParameter("strGoodsBarCode");
	    String strUnitId = request.getParameter("strUnitId");
	    String strUnitName = request.getParameter("strUnitName");
	    String strGoodsName = request.getParameter("strGoodsName");
	    String strGoodsTypeId = request.getParameter("strGoodsTypeId");
	    String strGoodsTypeName = request.getParameter("strGoodsTypeName");
	    String strSupplierName = request.getParameter("strSupplierName");
	    String strRequiredIntegral = request.getParameter("iRequiredIntegral");
	    String dEnterPrice = request.getParameter("dEnterPrice");
	    String dSalePrice = request.getParameter("dSalePrice");
	    String strStock = request.getParameter("iStock");
	    String strPreferentialType = request.getParameter("iPreferentialType");
	    String strState = request.getParameter("iState");
	    String txtGoodsDesc = request.getParameter("txtGoodsDesc");
	    String txtGoodsDescDetail = request.getParameter("txtGoodsDescDetail");
	    
	    String strGoodsPreferentialInfos = request.getParameter("strGoodsPreferentials");

	    //判断参数有效性
	    if(strGoodsBarCode == null || strGoodsBarCode.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数strGoodsBarCode不能为空", null);
	    }
	    if(strUnitId == null || strUnitId.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数strUnitId不能为空", null);
	    }
	    if(strUnitName == null || strUnitName.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数strUnitName不能为空", null);
	    }
	    if(strGoodsName == null || strGoodsName.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数strGoodsName不能为空", null);
	    }
	    if(strGoodsTypeId == null || strGoodsTypeId.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数strGoodsTypeId不能为空", null);
	    }
	    if(strGoodsTypeName == null || strGoodsTypeName.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数strGoodsTypeName不能为空", null);
	    }
	    if(strSupplierName == null || strSupplierName.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数strSupplierName不能为空", null);
	    }
	    if(strRequiredIntegral == null || strRequiredIntegral.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数iRequiredIntegral不能为空", null);
	    }
	    if(dEnterPrice == null || dEnterPrice.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数dEnterPrice不能为空", null);
	    }
	    if(dSalePrice == null || dSalePrice.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数dSalePrice不能为空", null);
	    }
	    if(strStock == null || strStock.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数strStock不能为空", null);
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
		    if(strGoodsPreferentialInfos == null || strGoodsPreferentialInfos.isEmpty())
		    {
		        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数strGoodsPreferentialInfos不能为空", null);
		    }
		    
	    }


	    //对象设置
	    GoodsInfoEntity tGoodsInfo=new GoodsInfoEntity();
	    BigDecimal bgEnterAmount = new BigDecimal(dEnterPrice);
	    tGoodsInfo.setdEnterPrice(bgEnterAmount);
	    BigDecimal bgSaleAmount = new BigDecimal(dSalePrice);
	    tGoodsInfo.setdSalePrice(bgSaleAmount);
	    
	    
	    tGoodsInfo.setStrGoodsBarCode(strGoodsBarCode);
	    
	    tGoodsInfo.setStrGoodsId(DataTool.getUUID());
	    
	    tGoodsInfo.setStrUnitId(strUnitId);
	    tGoodsInfo.setStrUnitName(strUnitName);
	    tGoodsInfo.setStrGoodsName(strGoodsName);
	    tGoodsInfo.setStrGoodsTypeId(strGoodsTypeId);
	    tGoodsInfo.setStrGoodsTypeName(strGoodsTypeName);
	    tGoodsInfo.setStrSupplierName(strSupplierName);
	    tGoodsInfo.setiRequiredIntegral(Integer.parseInt(strRequiredIntegral));
	    tGoodsInfo.setiStock(Integer.parseInt(strStock));
	    tGoodsInfo.setiPreferentialType(Integer.parseInt(strPreferentialType));
	    tGoodsInfo.setiState(Integer.parseInt(strState));
	    tGoodsInfo.setTxtGoodsDesc(txtGoodsDesc);
	    tGoodsInfo.setTxtGoodsDescDetail(txtGoodsDescDetail);

	    tGoodsInfo.setStrGoodsName(strGoodsName);
	    tGoodsInfo.setStrGoodsTypeId(strGoodsTypeId);
	    tGoodsInfo.setStrGoodsTypeName(strGoodsTypeName);
	    tGoodsInfo.setiStock(Integer.parseInt(strStock));
	    
	    //解析商品会员优惠信息  会员ID1,会员名称1,兑换积分数1｜会员ID2,会员名称2,兑换积分数2｜
	    String [] arrGoodsPreferentialInfos = strGoodsPreferentialInfos.split("\\|");
	    //System.out.println(strGoodsPreferentialInfos);
	    List<GoodsPreferentialEntity> listGoodsPreferentialEntity = new ArrayList<GoodsPreferentialEntity>();
	    if (arrGoodsPreferentialInfos != null)
	    {
	    	int iRight = 1;
	    	
	    	for (int iLoop = 0; iLoop < arrGoodsPreferentialInfos.length; iLoop++ )
	    	{
	    		GoodsPreferentialEntity tGoodsPreferentialEntity = new GoodsPreferentialEntity();
	    		
	    		// 解析单行
	    		String [] arrOneLineStrings = arrGoodsPreferentialInfos[iLoop].split(",");
	    		System.out.println(arrGoodsPreferentialInfos[iLoop]);
	    		
	    		if (arrOneLineStrings == null)
	    		{
	    			iRight = 0;
	    			break;
	    		}
	    		
	    		if(arrOneLineStrings.length != 3)
	    		{
	    			iRight = 0;
	    			break;
	    		}
	    		tGoodsPreferentialEntity.setStrLevelsId(arrOneLineStrings[0]);
	    		tGoodsPreferentialEntity.setStrLevelsName(arrOneLineStrings[1]);
	    		tGoodsPreferentialEntity.setiRequiredIntegral(Integer.parseInt(arrOneLineStrings[2]));
	    		
	    		listGoodsPreferentialEntity.add(tGoodsPreferentialEntity);
	    	}
	    	
	    	if (0 == iRight)
	    	{
	    		return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "会员优惠数据格式错误", null);
	    	}
	    }
	    tGoodsInfo.setListGoodsPreferentialEntity(listGoodsPreferentialEntity);
	    
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

	    tGoodsInfo.setStrEmployeeId("test");
	    tGoodsInfo.setStrEmployeeName("test");
	    tGoodsInfo.setStrEmployeeLoginName("test");
	    tGoodsInfo.setStrInsertTime(DateTool.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
	    tGoodsInfo.setStrUpdateTime(DateTool.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));


	    try{
	        return tGoodsInfoService.insertGoodsInfo(tGoodsInfo);
	    }
	    catch(Exception e)
	    {
	        e.printStackTrace();
	        return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "新增失败", null);
	    }
	}


	//更新一条GoodsInfo记录
	//http://localhost:8082/admin/biz/goods/updateGoodsInfo?strGoodsId=5b2958155f544ba48397e4b1b3981a4d&strGoodsPreferentials=39814fbbfc844558b3d3a9de2436707e,111,好的,4666|5911e0f0d6c840c5ab2f4f85d3b16a8f,222,好的2,56666|&txtGoodsDescDetail=idididididididi&txtGoodsDesc=nifahfiaidfhasdnfi&iState=1&iPreferentialType=1&iRequiredIntegral=50000&strSupplierName=qidongbo&strUnitName=张&strUnitId=123&strGoodsBarCode=123456789&strGoodsTypeId=99d9b574c8f340d0ad8102ad8f46a6ce&strGoodsTypeName=234&strGoodsName=1111&dEnterPrice=3&dSalePrice=6&iStock=100
	@ResponseBody
	@RequestMapping("updateGoodsInfo")
	public String updateGoodsInfo(HttpServletRequest request, HttpServletResponse response){

	    //获取传入参数
	    String strGoodsId = request.getParameter("strGoodsId");

	    String strGoodsBarCode = request.getParameter("strGoodsBarCode");
	    String strUnitId = request.getParameter("strUnitId");
	    String strUnitName = request.getParameter("strUnitName");
	    String strGoodsName = request.getParameter("strGoodsName");
	    String strGoodsTypeId = request.getParameter("strGoodsTypeId");
	    String strGoodsTypeName = request.getParameter("strGoodsTypeName");
	    String strSupplierName = request.getParameter("strSupplierName");
	    String strRequiredIntegral = request.getParameter("iRequiredIntegral");
	    String dEnterPrice = request.getParameter("dEnterPrice");
	    String dSalePrice = request.getParameter("dSalePrice");
	    String strStock = request.getParameter("iStock");
	    String strPreferentialType = request.getParameter("iPreferentialType");
	    String strState = request.getParameter("iState");
	    String txtGoodsDesc = request.getParameter("txtGoodsDesc");
	    String txtGoodsDescDetail = request.getParameter("txtGoodsDescDetail");
	    
	    String strGoodsPreferentialInfos = request.getParameter("strGoodsPreferentials");
	    
	    //判断参数有效性
	    if(strGoodsId == null || strGoodsId.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数strGoodsId不能为空", null);
	    }
	    if(strGoodsBarCode == null || strGoodsBarCode.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数strGoodsBarCode不能为空", null);
	    }
	    if(strUnitId == null || strUnitId.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数strUnitId不能为空", null);
	    }
	    if(strUnitName == null || strUnitName.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数strUnitName不能为空", null);
	    }
	    if(strGoodsName == null || strGoodsName.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数strGoodsName不能为空", null);
	    }
	    if(strGoodsTypeId == null || strGoodsTypeId.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数strGoodsTypeId不能为空", null);
	    }
	    if(strGoodsTypeName == null || strGoodsTypeName.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数strGoodsTypeName不能为空", null);
	    }
	    if(strSupplierName == null || strSupplierName.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数strSupplierName不能为空", null);
	    }
	    if(strRequiredIntegral == null || strRequiredIntegral.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数iRequiredIntegral不能为空", null);
	    }
	    if(dEnterPrice == null || dEnterPrice.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数dEnterPrice不能为空", null);
	    }
	    if(dSalePrice == null || dSalePrice.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数dSalePrice不能为空", null);
	    }
	    if(strStock == null || strStock.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数strStock不能为空", null);
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
		    if(strGoodsPreferentialInfos == null || strGoodsPreferentialInfos.isEmpty())
		    {
		        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数strGoodsPreferentialInfos不能为空", null);
		    }
		    
	    }	    
	    
	    //对象设置
	    GoodsInfoEntity tGoodsInfo=new GoodsInfoEntity();
	    tGoodsInfo.setStrGoodsId(strGoodsId);
	    
	    
	    BigDecimal bgEnterAmount = new BigDecimal(dEnterPrice);
	    tGoodsInfo.setdEnterPrice(bgEnterAmount);
	    BigDecimal bgSaleAmount = new BigDecimal(dSalePrice);
	    tGoodsInfo.setdSalePrice(bgSaleAmount);
	    
	    tGoodsInfo.setStrUnitId(strUnitId);
	    tGoodsInfo.setStrUnitName(strUnitName);
	    tGoodsInfo.setStrGoodsName(strGoodsName);
	    tGoodsInfo.setStrGoodsTypeId(strGoodsTypeId);
	    tGoodsInfo.setStrGoodsTypeName(strGoodsTypeName);
	    tGoodsInfo.setStrSupplierName(strSupplierName);
	    tGoodsInfo.setiRequiredIntegral(Integer.parseInt(strRequiredIntegral));
	    tGoodsInfo.setiStock(Integer.parseInt(strStock));
	    tGoodsInfo.setiPreferentialType(Integer.parseInt(strPreferentialType));
	    tGoodsInfo.setiState(Integer.parseInt(strState));
	    tGoodsInfo.setTxtGoodsDesc(txtGoodsDesc);
	    tGoodsInfo.setTxtGoodsDescDetail(txtGoodsDescDetail);

	    tGoodsInfo.setStrGoodsName(strGoodsName);
	    tGoodsInfo.setStrGoodsTypeId(strGoodsTypeId);
	    tGoodsInfo.setStrGoodsTypeName(strGoodsTypeName);
	    tGoodsInfo.setiStock(Integer.parseInt(strStock));
	    
	    //解析商品会员优惠信息  会员ID1,会员名称1,兑换积分数1｜会员ID2,会员名称2,兑换积分数2｜
	    String [] arrGoodsPreferentialInfos = strGoodsPreferentialInfos.split("\\|");
	    List<GoodsPreferentialEntity> listGoodsPreferentialEntity = new ArrayList<GoodsPreferentialEntity>();
	    if (arrGoodsPreferentialInfos != null)
	    {
	    	int iRight = 1;
	    	
	    	for (int iLoop = 0; iLoop < arrGoodsPreferentialInfos.length; iLoop++ )
	    	{
	    		GoodsPreferentialEntity tGoodsPreferentialEntity = new GoodsPreferentialEntity();
	    		
	    		// 解析单行
	    		String [] arrOneLineStrings = arrGoodsPreferentialInfos[iLoop].split(",");
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
	    		tGoodsPreferentialEntity.setStrPreferentialId(arrOneLineStrings[0]);
	    		tGoodsPreferentialEntity.setStrLevelsId(arrOneLineStrings[1]);
	    		tGoodsPreferentialEntity.setStrLevelsName(arrOneLineStrings[2]);
	    		tGoodsPreferentialEntity.setiRequiredIntegral(Integer.parseInt(arrOneLineStrings[3]));
	    		
	    		listGoodsPreferentialEntity.add(tGoodsPreferentialEntity);
	    	}
	    	
	    	if (0 == iRight)
	    	{
	    		return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "会员优惠数据格式错误", null);
	    	}
	    }
	    tGoodsInfo.setListGoodsPreferentialEntity(listGoodsPreferentialEntity);
	    
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

	    tGoodsInfo.setStrEmployeeId("test");
	    tGoodsInfo.setStrEmployeeName("test");
	    tGoodsInfo.setStrEmployeeLoginName("test");
	    tGoodsInfo.setStrUpdateTime(DateTool.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
		 
	    try{
	        return tGoodsInfoService.updateGoodsInfo(tGoodsInfo);
	    }
	    catch(Exception e)
	    {
	        e.printStackTrace();
	        return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "更新失败", null);
	    }
	}


	//删除一条GoodsInfo记录
	// localhost:8082/admin/biz/goods/delGoodsInfo?strGoodsId=c1f5f284869d4e70b61d1bbaeb30d33b
	@ResponseBody
	@RequestMapping("delGoodsInfo")
	public String delGoodsInfo(HttpServletRequest request, HttpServletResponse response){
		String strGoodsId = request.getParameter("strGoodsId");
	    if(strGoodsId == null || strGoodsId.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数strGoodsId不能为空", null);
	    }
	    
		
	    try
	    {
	        return tGoodsInfoService.deleteGoodsInfo(strGoodsId);
	    }
	    catch(Exception e)
	    {
	        e.printStackTrace();
	        return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "删除失败", null);
	    }
	}
	
	//删除一条GoodsInfo记录
	// localhost:8082/admin/biz/goods/getListGoodsPreferentialByGoodsId?strGoodsId=5b2958155f544ba48397e4b1b3981a4d
	@ResponseBody
	@RequestMapping("getListGoodsPreferentialByGoodsId")
	public String getListGoodsPreferentialByGoodsId(HttpServletRequest request, HttpServletResponse response){
		String strGoodsId = request.getParameter("strGoodsId");
		
		//return tGoodsInfoService.getListGoodsPreferentialByGoodsId(strGoodsId); 
		
		 try{
		        List<GoodsPreferentialEntity> listGoodsPreferentialEntity= tGoodsInfoService.getListGoodsPreferentialByGoodsId(strGoodsId);
		        
		        if (ValidateTool.isNull(listGoodsPreferentialEntity) || listGoodsPreferentialEntity.size() <= 0)
		        {
		            return DataTool.constructResponse(ResultCode.NO_DATA, "暂无数据", null);
		        }
		        else
		        {
		            //int totalrecord = tGoodsInfoService.getGoodsInfoTotalCount(queryMap);
		            Map<String, Object> resultMap = new HashMap<String, Object>();
		            resultMap.put("listGoodsPreferential", listGoodsPreferentialEntity);
		            return DataTool.constructResponse(ResultCode.OK, "查询成功", resultMap);
		        }
		    }catch(Exception e) {
		        e.printStackTrace();
		        return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "查询失败", null);
		    }
	}


	//获取GoodsInfo列表
	// localhost:8082/admin/biz/goods/getListGoodsInfoByPage?pagenum=1&pagesize=2
	@ResponseBody
	@RequestMapping("getListGoodsInfoByPage")
	public String getListGoodsInfo(HttpServletRequest request, HttpServletResponse response){
		
		System.out.println("getListGoodsInfo");  

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
	        List<GoodsInfoEntity> listGoodsInfo= tGoodsInfoService.getListGoodsInfo(queryMap);
	        
	        if (ValidateTool.isNull(listGoodsInfo)) 
	            return DataTool.constructResponse(ResultCode.NO_DATA, "暂无数据", null);
	        
	        if(listGoodsInfo.size() <= 0)
	        	 return DataTool.constructResponse(ResultCode.NO_DATA, "暂无数据", null);
	       
	            int totalrecord = tGoodsInfoService.getGoodsInfoTotalCount(queryMap);
	            Map<String, Object> resultMap = new HashMap<String, Object>();
	            resultMap.put("listGoodsInfo", listGoodsInfo);
	            resultMap.put("iTotalRecord", totalrecord);
	            resultMap.put("iTotalPage", totalrecord % iPagesize == 0 ? totalrecord / iPagesize : totalrecord / iPagesize + 1);
	            return DataTool.constructResponse(ResultCode.OK, "查询成功", resultMap);
	        
	    }catch(Exception e) {
	        e.printStackTrace();
	        return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "查询失败", null);
	    }
	   
	}
}
