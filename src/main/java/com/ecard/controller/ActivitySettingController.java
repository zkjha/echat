

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
import com.ecard.entity.IntegralclearRuleEntity;
import com.ecard.entity.StoredticketRuleEntity;
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
	

}
