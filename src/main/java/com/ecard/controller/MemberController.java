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
import com.ecard.entity.MemberlevelsEntity;
import com.ecard.service.MemberService;
import com.ecard.util.WebSessionUtil;
/**
 * 会员级别控制层
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/admin/biz/member")
public class MemberController {
	
	@Resource
	private MemberService memberService;
	@Resource
	private WebSessionUtil webSessionUtil;
	
	/**
	 * 新增会员
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("insertMember")
	public String insertMember(HttpServletRequest request, HttpServletResponse response) {
		String strRealname = request.getParameter("strRealname");
		String strIdcard = request.getParameter("strIdcard");
		String strMobile = request.getParameter("strMobile");
		String intAge = request.getParameter("intAge");
		String strMembercardnum = request.getParameter("strMembercardnum");
		String strLevelsid = request.getParameter("strLevelsid");
		
		
		
		if(ValidateTool.isEmptyStr(strRealname)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "姓名不能为空", null);
		}
		if(ValidateTool.isEmptyStr(strIdcard)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "身份证号不能为空", null);
		}
		if(ValidateTool.isEmptyStr(strMobile)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "手机号不能为空", null);
		}
		if(!ValidateTool.isMobile(strMobile)) {
			return DataTool.constructResponse(ResultCode.MOBILE_FORMAT_ERROR, "手机号格式错误", null);
		}
		if(ValidateTool.isEmptyStr(intAge)) {
			intAge = "0";
		}
		if(ValidateTool.isEmptyStr(strMembercardnum)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "会员卡号不能为空", null);
		}
		if(ValidateTool.isEmptyStr(strLevelsid)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "会员级别不能为空", null);
		}
		try {
			MemberlevelsEntity memberlevelsEntity = new MemberlevelsEntity();
			memberlevelsEntity.setStrLevelsid(DataTool.getUUID());
			memberlevelsEntity.setStrInserttime(DateTool.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
		}
	}
	
	/**
	 * 根据级别ID查询会员级别
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getMemberlevelsById")
	public String getMemberlevelsById(HttpServletRequest request, HttpServletResponse response) {
		String strLevelsid = request.getParameter("strLevelsid");
		if(ValidateTool.isEmptyStr(strLevelsid)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "会员级别ID不能为空", null);
		}
		try {
			MemberlevelsEntity memberlevelsEntity = memberService.getMemberlevelsById(strLevelsid);
			if(ValidateTool.isNull(memberlevelsEntity)) {
				return DataTool.constructResponse(ResultCode.NO_DATA, "会员级别不存在", null);
			}
			return DataTool.constructResponse(ResultCode.OK, "查询成功", memberlevelsEntity);
		} catch (Exception e) {
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
		}
	}
	
	/**
	 * 修改会员级别
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("updateMemberlevelsEntity")
	public String updateMemberlevelsEntity(HttpServletRequest request, HttpServletResponse response) {
		String strLevelsid = request.getParameter("strLevelsid");
		String strLevelsname = request.getParameter("strLevelsname");
		if(ValidateTool.isEmptyStr(strLevelsid)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "会员级别ID不能为空", null);
		}
		if(ValidateTool.isEmptyStr(strLevelsname)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "会员级别名称不能为空", null);
		}
		try {
			MemberlevelsEntity memberlevelsEntity = new MemberlevelsEntity();
			memberlevelsEntity.setStrLevelsid(DataTool.getUUID());
			memberlevelsEntity.setStrLevelsname(strLevelsname.trim());
			memberlevelsEntity.setStrUpdatetime(DateTool.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
		}
	}
	
	/**
	 * 查询会员级别列表
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("listMemberlevels")
	public String listMemberlevels(HttpServletRequest request, HttpServletResponse response) {
		String pagenum = request.getParameter("pagenum");
		String pagesize = request.getParameter("pagesize");
		String strSearchkey = request.getParameter("strSearchkey");
		
		if(ValidateTool.isEmptyStr(pagenum)) {
			pagenum = "1";
		}
		int iPagesize = StaticValue.PAGE_SIZE;
		if(!ValidateTool.isEmptyStr(pagesize)) {
			iPagesize = Integer.valueOf(pagesize);
		}
		
		int pageFrom = (Integer.parseInt(pagenum)-1)*iPagesize;
		try {
			Map<String,Object> queryMap = new HashMap<String, Object>();
			queryMap.put("pageFrom", pageFrom);
			queryMap.put("pageSize", iPagesize);
			queryMap.put("strSearchkey", strSearchkey);
			List<MemberlevelsEntity> memberlevelsEntityList = memberService.listMemberlevels(queryMap);
			if(ValidateTool.isNull(memberlevelsEntityList)||memberlevelsEntityList.size()<=0) {
				return DataTool.constructResponse(ResultCode.NO_DATA, "暂无会员级别", null);
			} else {
				int totalrecord = memberService.getMemberlevelsTotalCount(queryMap); //查询员工总数量
				Map<String,Object> resultMap = new HashMap<String, Object>();
				resultMap.put("memberlevelsEntityList", memberlevelsEntityList);
				resultMap.put("iTotalRecord", totalrecord);
				resultMap.put("iTotalPage", totalrecord%iPagesize == 0 ? totalrecord/iPagesize : totalrecord/iPagesize+1);
				return DataTool.constructResponse(ResultCode.OK, "查询成功", resultMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
		}
		
	}
	
	/**
	 * 禁用会员级别
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("forbiddenMemberlevels")
	public String forbiddenMemberlevels(HttpServletRequest request, HttpServletResponse response) {
		String strLevelsid = request.getParameter("strLevelsid");
		if(ValidateTool.isEmptyStr(strLevelsid)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "会员级别ID不能为空", null);
		}
		try {
			return memberService.forbiddenMemberlevels(strLevelsid);
		} catch (Exception e) {
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
		}
	}
}
