package com.ecard.wexin.controller;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.ecard.config.StaticValue;
import com.ecard.entity.MemberEntity;
import com.ecard.entity.MemberLevelsRightsMappingEntity;
import com.ecard.entity.MemberarticlesEntity;
import com.ecard.entity.MemberlevelsEntity;
import com.ecard.entity.WeiXinMemberSignEntity;
import com.ecard.service.WeiXinMemberCenterService;
import com.ecard.util.WebSessionUtil;
import com.ecard.vo.MemberVO;

@Controller
@RequestMapping("/weixin/biz/")
public class WeiXinMemberCenterController
{
	@Autowired
	private WeiXinMemberCenterService weiXinMemberCenterService;
	
	@Autowired 
	private WebSessionUtil webSessionUtil;
	
	//查询登陆会员信息
	@ResponseBody
	@RequestMapping("selectMemberInfo")
	//localhost:8083/weixin/biz/selectMemberInfo
	public String selectMemberInfo(HttpServletRequest request,HttpServletResponse response)
	{
		try {
			// 当前登录的用户信息
			String strMemberId = (String) webSessionUtil.getWeixinSession(request, response).getAttribute("memberid");
		
			//以下会员ID为测试数据
			//String strMemberId="377f37a5871f4874a2879dd77758e075";
			if(strMemberId==null)
				return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"请重新登录",null);
			if(strMemberId.isEmpty())
				return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"请重新登录",null);
			
			return weiXinMemberCenterService.selectMemberInfo(strMemberId);

		} catch (Exception e) {
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
		} 		
	}
	
	//插入会员签到信息
	@ResponseBody
	@RequestMapping("insertMemberSignInfo")
	//localhost:8083/weixin/biz/insertMemberSignInfo
	public String insertMemberSignInfo(HttpServletRequest request,HttpServletResponse response)
	{
		// 当前登录的用户信息
		String strMemberId="";
		try{
			strMemberId = (String) webSessionUtil.getWeixinSession(request, response).getAttribute("memberid");
		}catch(Exception e)
		{
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
		}
	
		//以下会员ID为测试数据
		//String strMemberId="377f37a5871f4874a2879dd77758e075";
		if(ValidateTool.isEmptyStr(strMemberId))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"请重新登录",null);
		String strSignId=DataTool.getUUID();
		String strSignTime=DateTool.DateToString(new Date(),DateStyle.YYYY_MM_DD_HH_MM_SS);
		WeiXinMemberSignEntity memberSignEntity=new WeiXinMemberSignEntity();
		memberSignEntity.setStrSignId(strSignId);
		memberSignEntity.setStrMemberId(strMemberId);
		memberSignEntity.setStrSignTime(strSignTime);
		try{
			return weiXinMemberCenterService.insertMemberSignInfo(memberSignEntity);
		}catch(Exception e)
		{
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
		}
	}
	
	//查询最近一次签到记录-单条（签到天数)
	@ResponseBody
	@RequestMapping("selectSignDays")
	//localhost:8083/weixin/biz/selectSignDays
	public String selectSignDays(HttpServletRequest request,HttpServletResponse response)
	{
		// 当前登录的用户信息
		String strMemberId="";
		try{
			strMemberId = (String) webSessionUtil.getWeixinSession(request, response).getAttribute("memberid");
			}catch(Exception e)
			{
				e.printStackTrace();
				return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
			}
		
		//以下会员ID为测试数据
		//String strMemberId="377f37a5871f4874a2879dd77758e075";
		if(ValidateTool.isEmptyStr(strMemberId))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"请重新登录",null);
		WeiXinMemberSignEntity weiXinMemberSignEntity=null;
		try{
			weiXinMemberSignEntity=weiXinMemberCenterService.selectSignDays(strMemberId);
			if(weiXinMemberSignEntity==null)
				return DataTool.constructResponse(ResultCode.NO_DATA,"暂无数据",null);
			Map<String,Object> resultMap=new HashMap<String,Object>();
			resultMap.put("weiXinMemberSignEntity",weiXinMemberSignEntity);
			return DataTool.constructResponse(ResultCode.OK,"查询成功",resultMap);
		}catch(Exception e)
			{
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
		}

	}
	
	//按月份查询某人的 签到信息
	@ResponseBody
	@RequestMapping("selectSignDaysByMonth")
	//localhost:8083/weixin/biz/selectSignDaysByMonth?strSearchDate=2017-05-01
	public String selectSignDaysByMonth(HttpServletRequest request,HttpServletResponse response)
	{
		// 当前登录的用户信息
		String strMemberId="";
		try{
			strMemberId = (String) webSessionUtil.getWeixinSession(request, response).getAttribute("memberid");
			}catch(Exception e)
			{
				e.printStackTrace();
				return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
			}
			
		
		//以下会员ID为测试数据
		//String strMemberId="377f37a5871f4874a2879dd77758e075";
		String strStartTime="";
		String strEndTime="";
		//strSearchDate="YYYY-MM-dd"
		String strSearchDate=request.getParameter("strSearchDate");
		if(ValidateTool.isEmptyStr(strMemberId))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"请重新登录",null);
		if(ValidateTool.isEmptyStr(strSearchDate))
		{
			Calendar calendar=Calendar.getInstance();
			int month = calendar.get(Calendar.MONTH) + 1;
			int year = calendar.get(Calendar.YEAR);
			strStartTime=String.valueOf(year)+"-"+String.valueOf(month)+"-01";
			strEndTime=String.valueOf(year)+"-"+String.valueOf(month+1)+"-01";
			
		}
		else
		{
			String[] strDateString=strSearchDate.split("-");
			String strYear=strDateString[0];
			String strMonth=strDateString[1];
			//查询日期组装 
			strStartTime=strYear+"-"+strMonth+"-01";
			strEndTime=strYear+"-"+(Integer.parseInt(strMonth)+1)+"-01";
			
		}
		strStartTime=DateTool.StringToString(strStartTime,DateStyle.YYYY_MM_DD_HH_MM_SS);
		strEndTime=DateTool.StringToString(strEndTime,DateStyle.YYYY_MM_DD_HH_MM_SS);
		Map<String,Object> queryMap=new HashMap<String,Object>();
		queryMap.put("strMemberId", strMemberId);
		queryMap.put("strStartTime", strStartTime);
		queryMap.put("strEndTime", strEndTime);
		try{
			List<WeiXinMemberSignEntity> listWeiXinMemberSignEntity=weiXinMemberCenterService.selectSignDaysByMonth(queryMap);
			if(listWeiXinMemberSignEntity==null)
				return DataTool.constructResponse(ResultCode.NO_DATA,"暂无数据",null);
			if(listWeiXinMemberSignEntity.size()==0)
				return DataTool.constructResponse(ResultCode.NO_DATA,"暂无数据",null);
			Map<String,Object> resultMap=new HashMap<String,Object>();
			resultMap.put("listWeiXinMemberSignEntity",listWeiXinMemberSignEntity);
			return DataTool.constructResponse(ResultCode.OK,"查询成功",resultMap);
		}catch(Exception e)
			{
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
		}

	}
	
	//查询未使用抵用券信息 --列表
	@ResponseBody
	@RequestMapping("selectUnUsedVoucherTicketInfo")
	//localhost:8083/weixin/biz/selectUnUsedVoucherTicketInfo
	public String selectUnUsedVoucherTicketInfo(HttpServletRequest request,HttpServletResponse response)
	{
		String strMemberId="";
	
		try{
				strMemberId=(String) webSessionUtil.getWeixinSession(request, response).getAttribute("memberid");
			}catch(Exception e)
			{
				e.printStackTrace();
				return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
			}
		
			//以下会员ID为测试数据
	
			//strMemberId="377f37a5871f4874a2879dd77758e075";
			if(ValidateTool.isEmptyStr(strMemberId))
				return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"请重新登录",null);
			try{
				
				return weiXinMemberCenterService.selectUnUsedVoucherTicketInfo(strMemberId);
			}catch(Exception e)
			{
				e.printStackTrace();
				return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
			}
	}
	
	
	//分页查询已使用抵用券信息 --列表
	@ResponseBody
	@RequestMapping("selectUsedVoucherTicketInfo")
	//localhost:8083/weixin/biz/selectUsedVoucherTicketInfo?iPageSize=1&iPageNum=1
	public String selectUsedVoucherTicketInfo(HttpServletRequest request,HttpServletResponse response)
	{
		String strPageSize=request.getParameter("iPageSize");
		String strPageNum=request.getParameter("iPageNum");
		int iPageNum=1;
		int iPageSize=0;
		String strMemberId="";

		try{
				strMemberId=(String) webSessionUtil.getWeixinSession(request, response).getAttribute("memberid");
			}catch(Exception e)
			{
				e.printStackTrace();
				return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
			}

			//以下会员ID为测试数据
			//strMemberId="377f37a5871f4874a2879dd77758e075";
			if(ValidateTool.isEmptyStr(strMemberId))
				return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"请重新登录",null);
			
			if(ValidateTool.isEmptyStr(strPageNum))
				iPageNum=1;
			else
			{
				if(isNumber(strPageNum))
					iPageNum=Integer.parseInt(strPageNum);
				else
					return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"起始页数格式错误",null);
			}

			if(ValidateTool.isEmptyStr(strPageSize))
				iPageSize=StaticValue.PAGE_SIZE;
			else
			{	if(isNumber(strPageSize))
					iPageSize=Integer.valueOf(strPageSize);
				else
					return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"每页数量格式错误",null);
			}
			Map<String,Object> queryMap=new HashMap<String,Object>();
			queryMap.put("iPageNum",iPageNum);
			queryMap.put("iPageSize",iPageSize);
			queryMap.put("strMemberId",strMemberId);
			try{
				
				return weiXinMemberCenterService.selectUsedVoucherTicketInfo(queryMap);
			}catch(Exception e)
			{
				e.printStackTrace();
				return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
			}
	}	
	
	
	
	//分页查询已过期抵用券信息 --列表
	@ResponseBody
	@RequestMapping("selectExpiredVoucherTicketInfo")
	//localhost:8083/weixin/biz/selectExpiredVoucherTicketInfo?iPageSize=1&iPageNum=1
	public String selectExpiredVoucherTicketInfo(HttpServletRequest request,HttpServletResponse response)
	{
		String strPageSize=request.getParameter("iPageSize");
		String strPageNum=request.getParameter("iPageNum");
		int iPageNum=1;
		int iPageSize=0;
		String strMemberId="";

		try{
				strMemberId=(String) webSessionUtil.getWeixinSession(request, response).getAttribute("memberid");
			}catch(Exception e)
			{
				e.printStackTrace();
				return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
			}

			//以下会员ID为测试数据
			//strMemberId="377f37a5871f4874a2879dd77758e075";
			if(ValidateTool.isEmptyStr(strMemberId))
				return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"请重新登录",null);
				
			if(ValidateTool.isEmptyStr(strPageNum))
				iPageNum=1;
			else
			{
				if(isNumber(strPageNum))
					iPageNum=Integer.parseInt(strPageNum);
				else
					return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"起始页数格式错误",null);
			}

			if(ValidateTool.isEmptyStr(strPageSize))
					iPageSize=StaticValue.PAGE_SIZE;
			else
			{	if(isNumber(strPageSize))
					iPageSize=Integer.valueOf(strPageSize);
				else
					return DataTool.constructResponse(ResultCode.PARAMER_TYPE_ERROR,"每页数量格式错误",null);
			}
			Map<String,Object> queryMap=new HashMap<String,Object>();
			queryMap.put("iPageNum",iPageNum);
			queryMap.put("iPageSize",iPageSize);
			queryMap.put("strMemberId",strMemberId);
			try{
					
				return weiXinMemberCenterService.selectExpiredVoucherTicketInfo(queryMap);
			}catch(Exception e)
			{
				e.printStackTrace();
				return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
			}
	}	
	
	//查询所有的级别信息
	@ResponseBody
	@RequestMapping("selectAllLevelsInfo")
	//localhost:8083/weixin/biz/selectAllLevelsInfo
	public String selectAllLevelsInfo(HttpServletRequest request,HttpServletResponse response)
	{
		try{
			List<MemberlevelsEntity> listMemberlevelsEntity=weiXinMemberCenterService.selectAllLevelsInfo();
			if(listMemberlevelsEntity==null)
				return DataTool.constructResponse(ResultCode.NO_DATA,"暂无数据",null);
			if(listMemberlevelsEntity.size()==0)
				return DataTool.constructResponse(ResultCode.NO_DATA,"暂无数据",null);
			Map<String,Object> resultMap=new HashMap<String,Object>();
			resultMap.put("listMemberlevelsEntity",listMemberlevelsEntity);
			return DataTool.constructResponse(ResultCode.OK,"查询成功",resultMap);
		}catch(Exception e)
		{
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
		}
	}
	
	//查询会员的级别权益信息
	@ResponseBody
	@RequestMapping("selectMemberLevelsRightsMappingEntityInfo")
	//localhost:8083/weixin/biz/selectMemberLevelsRightsMappingEntityInfo
	public String selectMemberLevelsRightsMappingEntityInfo(HttpServletRequest request,HttpServletResponse response)
	{
		String strMemberId="";

		try{
				strMemberId=(String) webSessionUtil.getWeixinSession(request, response).getAttribute("memberid");
			}catch(Exception e)
			{
				e.printStackTrace();
				return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
			}

		//以下会员ID为测试数据
		//strMemberId="377f37a5871f4874a2879dd77758e075";
		if(ValidateTool.isEmptyStr(strMemberId))
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL,"请重新登录",null);		

		try{
			List<MemberLevelsRightsMappingEntity> listMemberLevelsRightsMappingEntity=weiXinMemberCenterService.selectMemberLevelsRightsMappingEntityInfo(strMemberId);
			if(listMemberLevelsRightsMappingEntity==null)
				return DataTool.constructResponse(ResultCode.NO_DATA,"暂无数据",null);
			if(listMemberLevelsRightsMappingEntity.size()==0)
				return DataTool.constructResponse(ResultCode.NO_DATA,"暂无数据",null);
			Map<String,Object> resultMap=new HashMap<String,Object>();
			resultMap.put("listMemberLevelsRightsMappingEntity",listMemberLevelsRightsMappingEntity);
			return DataTool.constructResponse(ResultCode.OK,"查询成功",resultMap);
			}catch(Exception e)
			{
				e.printStackTrace();
				return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
			}
	}
	
	
	//查询会员章程
	@ResponseBody
	@RequestMapping("selectMemberArticlesInfo")
	//localhost:8083/weixin/biz/selectMemberArticlesInfo
	public String selectMemberArticlesInfo(HttpServletRequest request,HttpServletResponse response)
	{
		try{
			
			List<MemberarticlesEntity> listMemberarticlesEntity=weiXinMemberCenterService.selectMemberArticlesInfo();
			if(listMemberarticlesEntity==null)
				return DataTool.constructResponse(ResultCode.NO_DATA,"暂无数据",null);
			if(listMemberarticlesEntity.size()==0)
				return DataTool.constructResponse(ResultCode.NO_DATA,"暂无数据",null);
		
			Map<String,Object> resultMap=new HashMap<String,Object>();
			resultMap.put("listMemberarticlesEntity",listMemberarticlesEntity);
			return DataTool.constructResponse(ResultCode.OK,"查询成功",resultMap);
			}catch(Exception e)
			{
				e.printStackTrace();
				return DataTool.constructResponse(ResultCode.SYSTEM_ERROR,"系统错误",null);
			}
		
	}
	
	
	//校验
	public static boolean isNumber(String strCheckString)
	{
		boolean flag=false;
		Pattern p1 = Pattern.compile("^[0-9]\\d*$|^[1-9]\\d*\\.\\d{1,2}$|^0\\.\\d{1,2}$");
		Matcher matcher=p1.matcher(strCheckString);
		flag=matcher.matches();   
		return flag;
	}

}

