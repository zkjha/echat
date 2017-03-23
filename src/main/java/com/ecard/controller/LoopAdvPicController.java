

/**
 * fun:
 * author:qidongbo
 * create time:2017年3月23日
 */
package com.ecard.controller;

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
import com.ecard.entity.LoopAdvPicEntity;
import com.ecard.service.LoopAdvPicService;


/**
 * @author apple
 *
 */

@Controller
@RequestMapping("/admin/biz/advpic")
public class LoopAdvPicController {
	
	@Resource
	private LoopAdvPicService tLoopAdvPicService;
	
	//生成调用请求
	//获取一条LoopAdvPic记录
	// localhost:8082/admin/biz/advpic/getLoopAdvPic?strAdvPicId=18e48cc6a7484588bc08d521fb8b4789
	@ResponseBody
	@RequestMapping("getLoopAdvPic")
	public String getLoopAdvPic(HttpServletRequest request, HttpServletResponse response){

	    String strAdvPicId = request.getParameter("strAdvPicId");
	    Map<String,Object> resultMap = new HashMap<String, Object>();
	  
	    if(strAdvPicId == null || strAdvPicId.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数strAdvPicId不能为空", null);
	    }
	    try{
	        LoopAdvPicEntity tLoopAdvPic= tLoopAdvPicService.getLoopAdvPic(strAdvPicId);
	        if (tLoopAdvPic == null || tLoopAdvPic.getStrAdvPicId().isEmpty() || tLoopAdvPic.getStrAdvPicId() == null)
	        {
	            return DataTool.constructResponse(ResultCode.NO_DATA, "数据不存在", null);
	        }
	        else
	        {
	            resultMap.put("LoopAdvPic", tLoopAdvPic);
	            return DataTool.constructResponse(ResultCode.OK, "查询成功", resultMap);
	         }
	    }catch(Exception e) {
	        e.printStackTrace();
	        return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "查询失败", null);
	    }
	}


	//新增一条LoopAdvPic记录
	//localhost:8082/admin/biz/advpic/insertLoopAdvPic?strAdvPicName=nihaodafafeaa&iAdvPicWeight=1&strAdvLinkPage=www.baidu.com
	@ResponseBody
	@RequestMapping("insertLoopAdvPic")
	public String insertLoopAdvPic(HttpServletRequest request, HttpServletResponse response){

	    //获取传入参数
	    //String strAdvPicId = request.getParameter("strAdvPicId");
	    String strAdvPicName = request.getParameter("strAdvPicName");
	    String iAdvPicWeight = request.getParameter("iAdvPicWeight");
	    String strAdvLinkPage = request.getParameter("strAdvLinkPage");
	    //String strInsertTime = request.getParameter("strInsertTime");
	    String strReserved = request.getParameter("strReserved");


	    //判断参数有效性
	    if(strAdvPicName == null || strAdvPicName.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数strAdvPicName不能为空", null);
	    }
	    if(iAdvPicWeight == null || iAdvPicWeight.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数iAdvPicWeight不能为空", null);
	    }
	    /*
	    if(strAdvLinkPage == null || strAdvLinkPage.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数strAdvLinkPage不能为空", null);
	    }
	    */


	    //对象设置
	    LoopAdvPicEntity tLoopAdvPic=new LoopAdvPicEntity();
	    tLoopAdvPic.setStrAdvPicId(DataTool.getUUID());
	    tLoopAdvPic.setStrAdvPicName(strAdvPicName);
	    tLoopAdvPic.setiAdvPicWeight(Integer.parseInt(iAdvPicWeight));
	    tLoopAdvPic.setStrAdvLinkPage(strAdvLinkPage);
	    tLoopAdvPic.setStrInsertTime(DateTool.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
	    tLoopAdvPic.setStrReserved(strReserved);


	    try{
	        return tLoopAdvPicService.insertLoopAdvPic(tLoopAdvPic);
	    }
	    catch(Exception e)
	    {
	        e.printStackTrace();
	        return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "新增失败", null);
	    }
	}


	//更新一条LoopAdvPic记录
	//localhost:8082/admin/biz/advpic/updateLoopAdvPic?strAdvPicId=18e48cc6a7484588bc08d521fb8b4789&strAdvPicName=nihaodafafeaa&iAdvPicWeight=1&strAdvLinkPage=www.baidu.com
	@ResponseBody
	@RequestMapping("updateLoopAdvPic")
	public String updateLoopAdvPic(HttpServletRequest request, HttpServletResponse response){

	    String strAdvPicId = request.getParameter("strAdvPicId");
	    String strAdvPicName = request.getParameter("strAdvPicName");
	    String iAdvPicWeight = request.getParameter("iAdvPicWeight");
	    String strAdvLinkPage = request.getParameter("strAdvLinkPage");
	    String strReserved = request.getParameter("strReserved");
	    if(strAdvPicId == null || strAdvPicId.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数strAdvPicId不能为空", null);
	    }
	    if(strAdvPicName == null || strAdvPicName.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数strAdvPicName不能为空", null);
	    }
	    if(iAdvPicWeight == null || iAdvPicWeight.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数iAdvPicWeight不能为空", null);
	    }
	    
	    LoopAdvPicEntity tLoopAdvPic=new LoopAdvPicEntity();
	    tLoopAdvPic.setStrAdvPicId(strAdvPicId);
	    tLoopAdvPic.setStrAdvPicName(strAdvPicName);
	    tLoopAdvPic.setiAdvPicWeight(Integer.parseInt(iAdvPicWeight));
	    tLoopAdvPic.setStrAdvLinkPage(strAdvLinkPage);
	    tLoopAdvPic.setStrInsertTime(DateTool.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
	    tLoopAdvPic.setStrReserved(strReserved);
	    try{
	        return tLoopAdvPicService.updateLoopAdvPic(tLoopAdvPic);
	    }
	    catch(Exception e)
	    {
	        e.printStackTrace();
	        return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "更新失败", null);
	    }
	}


	//删除一条LoopAdvPic记录
	//localhost:8082/admin/biz/advpic/delLoopAdvPic?strAdvPicId=18e48cc6a7484588bc08d521fb8b4789
	@ResponseBody
	@RequestMapping("delLoopAdvPic")
	public String delLoopAdvPic(HttpServletRequest request, HttpServletResponse response){

	    String strAdvPicId = request.getParameter("strAdvPicId");
	    if(strAdvPicId == null || strAdvPicId.isEmpty())
	    {
	        return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "参数strAdvPicId不能为空", null);
	    }
	    try{
	        return tLoopAdvPicService.deleteLoopAdvPic(strAdvPicId);
	    }
	    catch(Exception e)
	    {
	        e.printStackTrace();
	        return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "删除失败", null);
	    }
	}


	//获取LoopAdvPic列表
	//localhost:8082/admin/biz/advpic/getListLoopAdvPic
	@ResponseBody
	@RequestMapping("getListLoopAdvPic")
	public String getListLoopAdvPic(HttpServletRequest request, HttpServletResponse response){

	    String pagenum = "1";
	    String pagesize = "100";

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
	        List<LoopAdvPicEntity> listLoopAdvPic= tLoopAdvPicService.getListLoopAdvPic();
	        if (ValidateTool.isNull(listLoopAdvPic) || listLoopAdvPic.size() <= 0)
	        {
	            return DataTool.constructResponse(ResultCode.NO_DATA, "暂无数据", null);
	        }
	        else
	        {
	            int totalrecord = tLoopAdvPicService.getLoopAdvPicTotalCount(queryMap);
	            Map<String, Object> resultMap = new HashMap<String, Object>();
	            resultMap.put("listLoopAdvPic", listLoopAdvPic);
	            resultMap.put("iTotalRecord", totalrecord);
	            //resultMap.put("iTotalPage", totalrecord % iPagesize == 0 ? totalrecord / iPagesize : totalrecord / iPagesize + 1);
	            return DataTool.constructResponse(ResultCode.OK, "查询成功", resultMap);
	        }
	    }catch(Exception e) {
	        e.printStackTrace();
	        return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "查询失败", null);
	    }
	}

}
