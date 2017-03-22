

/**
 * fun:
 * author:qidongbo
 * create time:2017年3月21日
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

import org.apache.commons.logging.Log;
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
import com.ecard.entity.GoodsInfoEntity;
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
	// localhost:8080/admin/biz/goods/getGoodsInfo?strGoodsId=c1f5f284869d4e70b61d1bbaeb30d33b
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
	// localhost:8080/admin/biz/goods/insertGoodsInfo?strGoodsName=1111&dEnterPrice=3&dSalePrice=6&strStock=100
	@ResponseBody
	@RequestMapping("insertGoodsInfo")
	public String insertGoodsInfo(HttpServletRequest request, HttpServletResponse response){

	    //获取传入参数
	    String strGoodsName = request.getParameter("strGoodsName");
	    String strGoodsTypeId = request.getParameter("strGoodsTypeId");
	    String strGoodsTypeName = request.getParameter("strGoodsTypeName");
	    
	    String dEnterPrice = request.getParameter("dEnterPrice");
	    String dSalePrice = request.getParameter("dSalePrice");
	    String strStock = request.getParameter("strStock");
	    
	    String strEmployeeId = request.getParameter("strEmployeeId");
	    String strEmployeeName = request.getParameter("strEmployeeName");
	    String strEmployeeLoginName = request.getParameter("strEmployeeLoginName");


	    //判断参数有效性
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
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数iStock不能为空", null);
	    }
	    if(strEmployeeId == null || strEmployeeId.isEmpty())
	    {
	    	strEmployeeId = "123456";
	        //return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数strEmployeeId不能为空", null);
	    }
	    if(strEmployeeName == null || strEmployeeName.isEmpty())
	    {
	    	strEmployeeName = "2222";
	        //return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数strEmployeeName不能为空", null);
	    }
	    if(strEmployeeLoginName == null || strEmployeeLoginName.isEmpty())
	    {
	    	strEmployeeLoginName = "1111";
	        //return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数strEmployeeLoginName不能为空", null);
	    }


	    //对象设置
	    GoodsInfoEntity tGoodsInfo=new GoodsInfoEntity();
	    BigDecimal bgEnterAmount = new BigDecimal(dEnterPrice);
	    tGoodsInfo.setdEnterPrice(bgEnterAmount);
	    BigDecimal bgSaleAmount = new BigDecimal(dSalePrice);
	    tGoodsInfo.setdSalePrice(bgSaleAmount);
	    
	    tGoodsInfo.setStrGoodsId(DataTool.getUUID());
	    tGoodsInfo.setStrGoodsName(strGoodsName);
	    tGoodsInfo.setStrGoodsTypeId(strGoodsTypeId);
	    tGoodsInfo.setStrGoodsTypeName(strGoodsTypeName);
	    tGoodsInfo.setiStock(Integer.parseInt(strStock));
	    
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

	    tGoodsInfo.setStrEmployeeId(strEmployeeId);
	    tGoodsInfo.setStrEmployeeName(strEmployeeName);
	    tGoodsInfo.setStrEmployeeLoginName(strEmployeeLoginName);
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
	// localhost:8080/admin/biz/goods/updateGoodsInfo?strGoodsId=c1f5f284869d4e70b61d1bbaeb30d33b&strGoodsName=1111&dEnterPrice=3&dSalePrice=6&strStock=100
	@ResponseBody
	@RequestMapping("updateGoodsInfo")
	public String updateGoodsInfo(HttpServletRequest request, HttpServletResponse response){

	    //获取传入参数
	    String strGoodsId = request.getParameter("strGoodsId");
	    String strGoodsName = request.getParameter("strGoodsName");
	    String strGoodsTypeId = request.getParameter("strGoodsTypeId");
	    String strGoodsTypeName = request.getParameter("strGoodsTypeName");
	    String dEnterPrice = request.getParameter("dEnterPrice");
	    String dSalePrice = request.getParameter("dSalePrice");
	    String strStock = request.getParameter("strStock");
	    
	    //对象设置
	    GoodsInfoEntity tGoodsInfo=new GoodsInfoEntity();
	    tGoodsInfo.setStrGoodsId(strGoodsId);
	    tGoodsInfo.setStrGoodsName(strGoodsName);
	    tGoodsInfo.setStrGoodsTypeId(strGoodsTypeId);
	    tGoodsInfo.setStrGoodsTypeName(strGoodsTypeName);
	    
	    
	    BigDecimal bgEnterAmount = new BigDecimal(dEnterPrice);
	    tGoodsInfo.setdEnterPrice(bgEnterAmount);
	    BigDecimal bgSaleAmount = new BigDecimal(dSalePrice);
	    tGoodsInfo.setdSalePrice(bgSaleAmount);
	    
	    tGoodsInfo.setiStock(Integer.parseInt(strStock));
	    tGoodsInfo.setStrEmployeeId("1111");
	    tGoodsInfo.setStrEmployeeName("2222");
	    tGoodsInfo.setStrEmployeeLoginName("3333");
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
	        
	        if (ValidateTool.isNull(listGoodsInfo) || listGoodsInfo.size() <= 0)
	        {
	            return DataTool.constructResponse(ResultCode.NO_DATA, "暂无数据", null);
	        }
	        else
	        {
	            int totalrecord = tGoodsInfoService.getGoodsInfoTotalCount(queryMap);
	            Map<String, Object> resultMap = new HashMap<String, Object>();
	            resultMap.put("listGoodsInfo", listGoodsInfo);
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
