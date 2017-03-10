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
import com.ecard.entity.MemberlevelsEntity;
import com.ecard.service.MemberlevelsService;
import com.ecard.util.WebSessionUtil;
/**
 * 会员级别控制层
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/admin/biz/memberlevels")
public class MemberlevelsController {
	
	@Resource
	private MemberlevelsService memberlevelsService;
	@Resource
	private WebSessionUtil webSessionUtil;
	
	/**
	 * 判断会员级别名称是否存在
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("judgeLevelsNameIsExist")
	public String judgeLevelsNameIsExist(HttpServletRequest request, HttpServletResponse response) {
		String strLevelsid = request.getParameter("strLevelsid");
		String strLevelsname = request.getParameter("strLevelsname");
		if(ValidateTool.isEmptyStr(strLevelsname)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "会员级别名称不能为空", null);
		}
		try {
			String flag = memberlevelsService.judgeLevelsNameIsExist(strLevelsid, strLevelsname.trim());
			return DataTool.constructResponse(ResultCode.OK, "查询成功", flag);
		} catch (Exception e) {
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
		}
	}
	
	/**
	 * 新增会员级别
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("insertMemberlevelsEntity")
	public String insertMemberlevelsEntity(HttpServletRequest request, HttpServletResponse response) {
		String strLevelsname = request.getParameter("strLevelsname");
		if(ValidateTool.isEmptyStr(strLevelsname)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "会员级别名称不能为空", null);
		}
		try {
			return memberlevelsService.insertMemberlevelsEntity(strLevelsname.trim());
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
			MemberlevelsEntity memberlevelsEntity = memberlevelsService.getMemberlevelsById(strLevelsid);
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
			return memberlevelsService.updateMemberlevelsEntity(strLevelsid, strLevelsname.trim());
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
			List<MemberlevelsEntity> memberlevelsEntityList = memberlevelsService.listMemberlevels(queryMap);
			if(ValidateTool.isNull(memberlevelsEntityList)||memberlevelsEntityList.size()<=0) {
				return DataTool.constructResponse(ResultCode.NO_DATA, "暂无会员级别", null);
			} else {
				int totalrecord = memberlevelsService.getMemberlevelsTotalCount(queryMap); //查询员工总数量
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
			return memberlevelsService.forbiddenMemberlevels(strLevelsid);
		} catch (Exception e) {
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
		}
	}
}
