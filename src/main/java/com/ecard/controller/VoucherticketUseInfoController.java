

/**
 * fun:
 * author:qidongbo
 * create time:2017年4月1日
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
import com.ecard.entity.VoucherticketUseInfoEntity;
import com.ecard.service.VoucherticketUseInfoService;

/**
 * @author apple
 *
 */
@Controller
@RequestMapping("/admin/biz/Voucher")
public class VoucherticketUseInfoController {
	//生成调用请求
	@Resource
	private VoucherticketUseInfoService tVoucherticketUseInfoService;
	
	//获取一条VoucherticketUseInfo记录
	@ResponseBody
	@RequestMapping("getVoucherticketUseInfo")
	public String getVoucherticketUseInfo(HttpServletRequest request, HttpServletResponse response){

	    String strVoucherUseInfoId = request.getParameter("strVoucherUseInfoId");
	    Map<String,Object> resultMap = new HashMap<String, Object>();
	  
	    if(strVoucherUseInfoId == null || strVoucherUseInfoId.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数strVoucherUseInfoId不能为空", null);
	    }
	    try{
	        VoucherticketUseInfoEntity tVoucherticketUseInfo= tVoucherticketUseInfoService.getVoucherticketUseInfo(strVoucherUseInfoId);
	        if (tVoucherticketUseInfo == null || tVoucherticketUseInfo.getStrVoucherUseInfoId().isEmpty() || tVoucherticketUseInfo.getStrVoucherUseInfoId() == null)
	        {
	            return DataTool.constructResponse(ResultCode.NO_DATA, "数据不存在", null);
	        }
	        else
	        {
	            resultMap.put("VoucherticketUseInfo", tVoucherticketUseInfo);
	            return DataTool.constructResponse(ResultCode.OK, "查询成功", resultMap);
	         }
	    }catch(Exception e) {
	        e.printStackTrace();
	        return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "查询失败", null);
	    }
	}


	//新增一条VoucherticketUseInfo记录
	//localhost:8082/admin/biz/Voucher/insertVoucherticketUseInfo?strVoucherTicketId=d0905e56e3a84e0381c553c0004f48dc&strVoucherTicketName=洗车&strValidEndTime=1111090&strMemberId=e8b9c2cabd364e15ade4cce6480c7b7d&strMemberName=刘鹏彦&iStat=0&iCanUseCount=3&iUsedCount=1&iIsValid=1&strUseCountDesc=三次
	@ResponseBody
	@RequestMapping("insertVoucherticketUseInfo")
	public String insertVoucherticketUseInfo(HttpServletRequest request, HttpServletResponse response){

	    //获取传入参数
	    //String strVoucherUseInfoId = request.getParameter("strVoucherUseInfoId");
	    String strVoucherTicketId = request.getParameter("strVoucherTicketId");
	    String strVoucherTicketName = request.getParameter("strVoucherTicketName");
	    String strValidEndTime = request.getParameter("strValidEndTime");
	    String strMemberId = request.getParameter("strMemberId");
	    String strMemberName = request.getParameter("strMemberName");
	    String iStat = request.getParameter("iStat");
	    String iCanUseCount = request.getParameter("iCanUseCount");
	    String iUsedCount = request.getParameter("iUsedCount");
	    String iIsValid = request.getParameter("iIsValid");
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
	    if(strMemberId == null || strMemberId.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数strMemberId不能为空", null);
	    }
	    if(strMemberName == null || strMemberName.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数strMemberName不能为空", null);
	    }
	    if(iStat == null || iStat.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数iStat不能为空", null);
	    }
	    if(iCanUseCount == null || iCanUseCount.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数iCanUseCount不能为空", null);
	    }
	    if(iUsedCount == null || iUsedCount.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数iUsedCount不能为空", null);
	    }
	    if(iIsValid == null || iIsValid.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数iIsValid不能为空", null);
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


	    //对象设置
	    VoucherticketUseInfoEntity tVoucherticketUseInfo=new VoucherticketUseInfoEntity();
	    tVoucherticketUseInfo.setStrVoucherUseInfoId(DataTool.getUUID());
	    tVoucherticketUseInfo.setStrVoucherTicketId(strVoucherTicketId);
	    tVoucherticketUseInfo.setStrVoucherTicketName(strVoucherTicketName);
	    tVoucherticketUseInfo.setStrValidEndTime(strValidEndTime);
	    tVoucherticketUseInfo.setStrMemberId(strMemberId);
	    tVoucherticketUseInfo.setStrMemberName(strMemberName);
	    tVoucherticketUseInfo.setiStat(Integer.parseInt(iStat));
	    tVoucherticketUseInfo.setiCanUseCount(Integer.parseInt(iCanUseCount));
	    tVoucherticketUseInfo.setiUsedCount(Integer.parseInt(iUsedCount));
	    tVoucherticketUseInfo.setiIsValid(Integer.parseInt(iIsValid));
	    tVoucherticketUseInfo.setStrUseCountDesc(strUseCountDesc);
	    tVoucherticketUseInfo.setStrRuleDesc(strRuleDesc);
	    tVoucherticketUseInfo.setStrReserved(strReserved);


	    try{
	        return tVoucherticketUseInfoService.insertVoucherticketUseInfo(tVoucherticketUseInfo);
	    }
	    catch(Exception e)
	    {
	        e.printStackTrace();
	        return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "新增失败", null);
	    }
	}


	//更新一条VoucherticketUseInfo记录
	@ResponseBody
	@RequestMapping("updateVoucherticketUseInfo")
	public String updateVoucherticketUseInfo(HttpServletRequest request, HttpServletResponse response){

	    String strVoucherUseInfoId = request.getParameter("strVoucherUseInfoId");
	    String strVoucherTicketId = request.getParameter("strVoucherTicketId");
	    String strVoucherTicketName = request.getParameter("strVoucherTicketName");
	    String strValidEndTime = request.getParameter("strValidEndTime");
	    String strMemberId = request.getParameter("strMemberId");
	    String strMemberName = request.getParameter("strMemberName");
	    String iStat = request.getParameter("iStat");
	    String iCanUseCount = request.getParameter("iCanUseCount");
	    String iUsedCount = request.getParameter("iUsedCount");
	    String iIsValid = request.getParameter("iIsValid");
	    String strUseCountDesc = request.getParameter("strUseCountDesc");
	    String strRuleDesc = request.getParameter("strRuleDesc");
	    String strReserved = request.getParameter("strReserved");
	    
	    if(strVoucherUseInfoId == null || strVoucherUseInfoId.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数strVoucherUseInfoId不能为空", null);
	    }
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
	    if(strMemberId == null || strMemberId.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数strMemberId不能为空", null);
	    }
	    if(strMemberName == null || strMemberName.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数strMemberName不能为空", null);
	    }
	    if(iStat == null || iStat.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数iStat不能为空", null);
	    }
	    if(iCanUseCount == null || iCanUseCount.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数iCanUseCount不能为空", null);
	    }
	    if(iUsedCount == null || iUsedCount.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数iUsedCount不能为空", null);
	    }
	    if(iIsValid == null || iIsValid.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数iIsValid不能为空", null);
	    }
	    if(strUseCountDesc == null || strUseCountDesc.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数strUseCountDesc不能为空", null);
	    }
	    
	    VoucherticketUseInfoEntity tVoucherticketUseInfo=new VoucherticketUseInfoEntity();
	    tVoucherticketUseInfo.setStrVoucherUseInfoId(strVoucherUseInfoId);
	    tVoucherticketUseInfo.setStrVoucherTicketId(strVoucherTicketId);
	    tVoucherticketUseInfo.setStrVoucherTicketName(strVoucherTicketName);
	    tVoucherticketUseInfo.setStrValidEndTime(strValidEndTime);
	    tVoucherticketUseInfo.setStrMemberId(strMemberId);
	    tVoucherticketUseInfo.setStrMemberName(strMemberName);
	    tVoucherticketUseInfo.setiStat(Integer.parseInt(iStat));
	    tVoucherticketUseInfo.setiCanUseCount(Integer.parseInt(iCanUseCount));
	    tVoucherticketUseInfo.setiUsedCount(Integer.parseInt(iUsedCount));
	    tVoucherticketUseInfo.setiIsValid(Integer.parseInt(iIsValid));
	    tVoucherticketUseInfo.setStrUseCountDesc(strUseCountDesc);
	    tVoucherticketUseInfo.setStrRuleDesc(strRuleDesc);
	    tVoucherticketUseInfo.setStrReserved(strReserved);
	    try{
	        return tVoucherticketUseInfoService.updateVoucherticketUseInfo(tVoucherticketUseInfo);
	    }
	    catch(Exception e)
	    {
	        e.printStackTrace();
	        return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "更新失败", null);
	    }
	}


	//删除一条VoucherticketUseInfo记录
	//localhost:8082/admin/biz/Voucher/delVoucherticketUseInfo?strVoucherUseInfoId=d1d7a7528b174303b8f8cf5a946df7ab
	@ResponseBody
	@RequestMapping("delVoucherticketUseInfo")
	public String delVoucherticketUseInfo(HttpServletRequest request, HttpServletResponse response){

	    String strVoucherUseInfoId = request.getParameter("strVoucherUseInfoId");
	    if(strVoucherUseInfoId == null || strVoucherUseInfoId.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数strVoucherUseInfoId不能为空", null);
	    }
	    try{
	        return tVoucherticketUseInfoService.deleteVoucherticketUseInfo(strVoucherUseInfoId);
	    }
	    catch(Exception e)
	    {
	        e.printStackTrace();
	        return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "删除失败", null);
	    }
	}


	//获取VoucherticketUseInfo列表
	//localhost:8082/admin/biz/Voucher/getListVoucherticketUseInfoByMemberId?strMemberId=e8b9c2cabd364e15ade4cce6480c7b7d&iStat=0
	@ResponseBody
	@RequestMapping("getListVoucherticketUseInfoByMemberId")
	public String getListVoucherticketUseInfoByMemberId(HttpServletRequest request, HttpServletResponse response){
	    String strMemberId = request.getParameter("strMemberId");
	    String iStat = request.getParameter("iStat");
	    if(strMemberId == null || strMemberId.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数strMemberId不能为空", null);
	    }
	    if(iStat == null || iStat.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数iStat不能为空", null);
	    }	    

	    try{
	        List<VoucherticketUseInfoEntity> listVoucherticketUseInfo= tVoucherticketUseInfoService.getListVoucherticketUseInfoByMemberId(strMemberId,Integer.parseInt(iStat));
	        if (ValidateTool.isNull(listVoucherticketUseInfo) || listVoucherticketUseInfo.size() <= 0)
	        {
	            return DataTool.constructResponse(ResultCode.NO_DATA, "暂无数据", null);
	        }
	        else
	        {
            Map<String, Object> resultMap = new HashMap<String, Object>();
	            resultMap.put("listVoucherticketUseInfo", listVoucherticketUseInfo);
	            return DataTool.constructResponse(ResultCode.OK, "查询成功", resultMap);
	        }
	    }catch(Exception e) {
	        e.printStackTrace();
	        return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "查询失败", null);
	    }
	}

}
