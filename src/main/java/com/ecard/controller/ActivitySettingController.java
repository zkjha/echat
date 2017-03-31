

/**
 * fun:
 * author:qidongbo
 * create time:2017年3月23日
 */
package com.ecard.controller;

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
import com.commontools.validate.ValidateTool;
import com.ecard.config.ResultCode;
import com.ecard.config.StaticValue;
import com.ecard.entity.IntegralclearRuleEntity;
import com.ecard.entity.StoredticketRuleEntity;
import com.ecard.entity.VoucherticketRuleEntity;
import com.ecard.service.ActivitySettingService;

/**
 * @author apple
 *
 */
@Controller
@RequestMapping("/admin/biz/ActivitySetting")
public class ActivitySettingController {
	
	@Resource
	private ActivitySettingService tActivitySettingService;
	
	//获取积分清除信息
	//localhost:8082/admin/biz/ActivitySetting/getIntegralclearRule
	@ResponseBody
	@RequestMapping("getIntegralclearRule")
	public String getIntegralclearRule(HttpServletRequest request, HttpServletResponse response){

	    Map<String,Object> resultMap = new HashMap<String, Object>();
	  
	    try{
	        IntegralclearRuleEntity tIntegralclearRule= tActivitySettingService.getIntegralclearRule();
	        if (tIntegralclearRule == null || tIntegralclearRule.getStrValidBeginTime().isEmpty() || tIntegralclearRule.getStrValidBeginTime() == null)
	        {
	            return DataTool.constructResponse(ResultCode.NO_DATA, "数据不存在", null);
	        }
	        else
	        {
	            resultMap.put("IntegralclearRule", tIntegralclearRule);
	            return DataTool.constructResponse(ResultCode.OK, "查询成功", resultMap);
	         }
	    }catch(Exception e) {
	        e.printStackTrace();
	        return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "查询失败", null);
	    }
	}
	
	
	
	//更新一条IntegralclearRule记录
	// localhost:8082/admin/biz/ActivitySetting/updateIntegralclearRule?strValidBeginTime=2017-03-02 00:00:00&strValidEndTime=2017-03-02 00:00:00
	@ResponseBody
	@RequestMapping("updateIntegralclearRule")
	public String updateIntegralclearRule(HttpServletRequest request, HttpServletResponse response){

	    String strValidBeginTime = request.getParameter("strValidBeginTime");
	    String strValidEndTime = request.getParameter("strValidEndTime");
	    String strIsValid = request.getParameter("strIsValid");
	    String strReserved = request.getParameter("strReserved");
	    
	    if(strValidBeginTime == null || strValidBeginTime.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数ValidBeginTime不能为空", null);
	    }
	    
	    if(strValidEndTime == null || strValidEndTime.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数ValidEndTime不能为空", null);
	    }
	    
	    if(strIsValid == null || strIsValid.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数IsValid不能为空", null);
	    }
	    
	    IntegralclearRuleEntity tIntegralclearRule=new IntegralclearRuleEntity();
	    tIntegralclearRule.setStrValidBeginTime(strValidBeginTime);
	    tIntegralclearRule.setStrValidEndTime(strValidEndTime);
	    tIntegralclearRule.setiIsValid(Integer.parseInt(strIsValid));
	    tIntegralclearRule.setStrReserved(strReserved);
	    try{
	        return tActivitySettingService.updateIntegralclearRule(tIntegralclearRule);
	    }
	    catch(Exception e)
	    {
	        e.printStackTrace();
	        return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "更新失败", null);
	    }
	}
	
	
	//localhost:8082/admin/biz/ActivitySetting/updateStoredticketRule?strTicketId=100001&strTicketName=售后充值1&iTicketType=0&strValidEndTime=20198838888&iIsValid=1&strTicketRuleDesc=nihaonihao
	@ResponseBody
	@RequestMapping("updateStoredticketRule")
	public String updateStoredticketRule(HttpServletRequest request, HttpServletResponse response){
		//获取传入参数
	    String strTicketName = request.getParameter("strTicketName");
	    String strTicketType = request.getParameter("iTicketType");
	    String strValidEndTime = request.getParameter("strValidEndTime");
	    String strIsValid = request.getParameter("iIsValid");
	    String strTicketRuleDesc = request.getParameter("strTicketRuleDesc");
	    String strReserved = request.getParameter("strReserved");

	    String strTicketId = request.getParameter("strTicketId");

	    if(strTicketId == null || strTicketId.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数strTicketId不能为空", null);
	    }
	    if(strTicketName == null || strTicketName.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数strTicketName不能为空", null);
	    }
	    if(strTicketType == null || strTicketType.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数iTicketType不能为空", null);
	    }
	    if (strTicketType.equals("0"))
	    {
		    if(strValidEndTime == null || strValidEndTime.isEmpty())
		    {
		        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数strValidEndTime不能为空", null);
		    }
	    }

	    if(strIsValid == null || strIsValid.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数iIsValid不能为空", null);
	    }
	    if(strTicketRuleDesc == null || strTicketRuleDesc.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数strTicketRuleDesc不能为空", null);
	    }
		
		
		StoredticketRuleEntity tStoredticketRuleEntity = new StoredticketRuleEntity();
		
		tStoredticketRuleEntity.setStrTicketId(strTicketId);
		tStoredticketRuleEntity.setStrTicketName(strTicketName);
		tStoredticketRuleEntity.setiTicketType(Integer.parseInt(strTicketType));
		tStoredticketRuleEntity.setStrValidEndTime(strValidEndTime);
		tStoredticketRuleEntity.setiIsValid(Integer.parseInt(strIsValid));
	    tStoredticketRuleEntity.setStrTicketRuleDesc(strTicketRuleDesc);
	    tStoredticketRuleEntity.setStrReserved(strReserved);
		
		try {
			return tActivitySettingService.updateStoredticketRule(tStoredticketRuleEntity);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "更新失败", null);
		}
	}	
		
		
    //查询所有的储值卡规则信息
    //localhost:8082/admin/biz/ActivitySetting/getStoredticketRules
	@ResponseBody
	@RequestMapping("getStoredticketRules")
	public String getStoredticketRules(HttpServletRequest request, HttpServletResponse response){
	    try{
	        List<StoredticketRuleEntity> listStoredticketRules= tActivitySettingService.getListStoredticketRule();
	        if (ValidateTool.isNull(listStoredticketRules) || listStoredticketRules.size() <= 0)
	        {
	            return DataTool.constructResponse(ResultCode.NO_DATA, "暂无数据", null);
	        }
	        else
	        {
	            Map<String, Object> resultMap = new HashMap<String, Object>();
	            resultMap.put("listStoredticketRules", listStoredticketRules);
                return DataTool.constructResponse(ResultCode.OK, "查询成功", resultMap);
	        }
	    }catch(Exception e) {
	        e.printStackTrace();
	        return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "查询失败", null);
	    }
	}
	
    //抵用券相关操作
	//localhost:8082/admin/biz/ActivitySetting/getVoucherticketRule?strVoucherTicketId=8a316067fcce4d4f9fa15e91f6dc93bb
	@ResponseBody
	@RequestMapping("getVoucherticketRule")
	public String getVoucherticketRule(HttpServletRequest request, HttpServletResponse response){

	    String strVoucherTicketId = request.getParameter("strVoucherTicketId");
	    Map<String,Object> resultMap = new HashMap<String, Object>();
	  
	    if(strVoucherTicketId == null || strVoucherTicketId.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数strVoucherTicketId不能为空", null);
	    }
	    try{
	        VoucherticketRuleEntity tVoucherticketRule= tActivitySettingService.getVoucherticketRule(strVoucherTicketId);
	        if (tVoucherticketRule == null || tVoucherticketRule.getStrVoucherTicketId().isEmpty() || tVoucherticketRule.getStrVoucherTicketId() == null)
	        {
	            return DataTool.constructResponse(ResultCode.NO_DATA, "数据不存在", null);
	        }
	        else
	        {
	            resultMap.put("VoucherticketRule", tVoucherticketRule);
	            return DataTool.constructResponse(ResultCode.OK, "查询成功", resultMap);
	         }
	    }catch(Exception e) {
	        e.printStackTrace();
	        return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "查询失败", null);
	    }
	}

    //localhost:8082/admin/biz/ActivitySetting/insertVoucherticketRule?strVoucherTicketName=洗车&strValidEndTime=20991213&iIsValid=1&iCanUseCount=3&strUseCountDesc=三次
	//新增一条VoucherticketRule记录 8a316067fcce4d4f9fa15e91f6dc93bb
	@ResponseBody
	@RequestMapping("insertVoucherticketRule")
	public String insertVoucherticketRule(HttpServletRequest request, HttpServletResponse response){
	    //获取传入参数
	    String strVoucherTicketName = request.getParameter("strVoucherTicketName");
	    String strValidEndTime = request.getParameter("strValidEndTime");
	    String strIsValid = request.getParameter("iIsValid");
	    String strCanUseCount = request.getParameter("iCanUseCount");
	    String strUseCountDesc = request.getParameter("strUseCountDesc");
	    String strRuleDesc = request.getParameter("strRuleDesc");
	    String strReserved = request.getParameter("strReserved");

	    //判断参数有效性
	    if(strVoucherTicketName == null || strVoucherTicketName.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数strVoucherTicketName不能为空", null);
	    }
	    if(strValidEndTime == null || strValidEndTime.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数strValidEndTime不能为空", null);
	    }
	    if(strIsValid == null || strIsValid.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数iIsValid不能为空", null);
	    }
	    if(strCanUseCount == null || strCanUseCount.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数iCanUseCount不能为空", null);
	    }
	    if(strUseCountDesc == null || strUseCountDesc.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数strUseCountDesc不能为空", null);
	    }
	    /*
	    if(strRuleDesc == null || strRuleDesc.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数strRuleDesc不能为空", null);
	    }
	    */


	    //对象设置
	    VoucherticketRuleEntity tVoucherticketRule=new VoucherticketRuleEntity();
	    tVoucherticketRule.setStrVoucherTicketId(DataTool.getUUID());
	    tVoucherticketRule.setStrVoucherTicketName(strVoucherTicketName);
	    tVoucherticketRule.setStrValidEndTime(strValidEndTime);
	    tVoucherticketRule.setiIsValid(Integer.parseInt(strIsValid));
	    tVoucherticketRule.setiCanUseCount(Integer.parseInt(strCanUseCount));
	    tVoucherticketRule.setStrUseCountDesc(strUseCountDesc);
	    tVoucherticketRule.setStrRuleDesc(strRuleDesc);
	    tVoucherticketRule.setStrReserved(strReserved);


	    try{
	        return tActivitySettingService.insertVoucherticketRule(tVoucherticketRule);
	    }
	    catch(Exception e)
	    {
	        e.printStackTrace();
	        return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "新增失败", null);
	    }
	}

    //localhost:8082/admin/biz/ActivitySetting/updateVoucherticketRule?strVoucherTicketId=8a316067fcce4d4f9fa15e91f6dc93bb&strVoucherTicketName=洗车2&strValidEndTime=209912132222&iIsValid=1&iCanUseCount=4&strUseCountDesc=三4次
	//更新一条VoucherticketRule记录
	@ResponseBody
	@RequestMapping("updateVoucherticketRule")
	public String updateVoucherticketRule(HttpServletRequest request, HttpServletResponse response){
	    String strVoucherTicketId = request.getParameter("strVoucherTicketId");
	    String strVoucherTicketName = request.getParameter("strVoucherTicketName");
	    String strValidEndTime = request.getParameter("strValidEndTime");
	    String strIsValid = request.getParameter("iIsValid");
	    String strCanUseCount = request.getParameter("iCanUseCount");
	    String strUseCountDesc = request.getParameter("strUseCountDesc");
	    String strRuleDesc = request.getParameter("strRuleDesc");
	    String strReserved = request.getParameter("strReserved");

	    //判断参数有效性
	    if(strVoucherTicketId == null || strVoucherTicketId.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数strVoucherTicketId不能为空", null);
	    }
	    if(strVoucherTicketName == null || strVoucherTicketName.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数strVoucherTicketName不能为空", null);
	    }
	    if(strValidEndTime == null || strValidEndTime.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数strValidEndTime不能为空", null);
	    }
	    if(strIsValid == null || strIsValid.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数iIsValid不能为空", null);
	    }
	    if(strCanUseCount == null || strCanUseCount.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数iCanUseCount不能为空", null);
	    }
	    if(strUseCountDesc == null || strUseCountDesc.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数strUseCountDesc不能为空", null);
	    }
	    /*
	    if(strRuleDesc == null || strRuleDesc.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数strRuleDesc不能为空", null);
	    }
	    if(strReserved == null || strReserved.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数strReserved不能为空", null);
	    }
	    */
	    
	    VoucherticketRuleEntity tVoucherticketRule=new VoucherticketRuleEntity();
	    tVoucherticketRule.setStrVoucherTicketId(strVoucherTicketId);
	    tVoucherticketRule.setStrVoucherTicketName(strVoucherTicketName);
	    tVoucherticketRule.setStrValidEndTime(strValidEndTime);
	    tVoucherticketRule.setiIsValid(Integer.parseInt(strIsValid));
	    tVoucherticketRule.setiCanUseCount(Integer.parseInt(strCanUseCount));
	    tVoucherticketRule.setStrUseCountDesc(strUseCountDesc);
	    tVoucherticketRule.setStrRuleDesc(strRuleDesc);
	    tVoucherticketRule.setStrReserved(strReserved);
	    try{
	        return tActivitySettingService.updateVoucherticketRule(tVoucherticketRule);
	    }
	    catch(Exception e)
	    {
	        e.printStackTrace();
	        return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "更新失败", null);
	    }
	}


	//删除一条VoucherticketRule记录
	//localhost:8082/admin/biz/ActivitySetting/delVoucherticketRule?strVoucherTicketId=8a316067fcce4d4f9fa15e91f6dc93bb

	@ResponseBody
	@RequestMapping("delVoucherticketRule")
	public String delVoucherticketRule(HttpServletRequest request, HttpServletResponse response){

	    String strVoucherTicketId = request.getParameter("strVoucherTicketId");
	    if(strVoucherTicketId == null || strVoucherTicketId.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数strVoucherTicketId不能为空", null);
	    }
	    try{
	        return tActivitySettingService.deleteVoucherticketRule(strVoucherTicketId);
	    }
	    catch(Exception e)
	    {
	        e.printStackTrace();
	        return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "删除失败", null);
	    }
	}


	//获取VoucherticketRule列表
	//localhost:8082/admin/biz/ActivitySetting/getListVoucherticketRule?pagenum=1&pagesize=1
	@ResponseBody
	@RequestMapping("getListVoucherticketRule")
	public String getListVoucherticketRule(HttpServletRequest request, HttpServletResponse response){

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
	        List<VoucherticketRuleEntity> listVoucherticketRule= tActivitySettingService.getListVoucherticketRule(queryMap);
	        if (ValidateTool.isNull(listVoucherticketRule) || listVoucherticketRule.size() <= 0)
	        {
	            return DataTool.constructResponse(ResultCode.NO_DATA, "暂无数据", null);
	        }
	        else
	        {
	            int totalrecord = tActivitySettingService.getVoucherticketRuleTotalCount(queryMap);
	            Map<String, Object> resultMap = new HashMap<String, Object>();
	            resultMap.put("listVoucherticketRule", listVoucherticketRule);
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
