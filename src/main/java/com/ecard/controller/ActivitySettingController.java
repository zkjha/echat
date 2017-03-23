

/**
 * fun:
 * author:qidongbo
 * create time:2017年3月23日
 */
package com.ecard.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.commontools.data.DataTool;
import com.ecard.config.ResultCode;
import com.ecard.entity.IntegralclearRuleEntity;
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

}
