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
import com.ecard.enumeration.MemberlevelsStatusEnum;
import com.ecard.service.MemberService;
import com.ecard.util.WebSessionUtil;
import com.ecard.vo.MemberVO;
/**
 * 会员级别控制层
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/admin/biz/member/")
public class MemberController {
	
	@Resource
	private MemberService memberService;
	@Resource
	private WebSessionUtil webSessionUtil;
	
	
	
	/**
	 * 查询所有的职务信息
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("listAllMemberLevels")
	public String listAllMemberLevels(HttpServletRequest request, HttpServletResponse response) {
		try {
			List<MemberlevelsEntity> memberlevelsEntityList = memberService.listAllMemberLevels(MemberlevelsStatusEnum.ACTIVATE.getValue()); //查询所有可用的会员级别
			if(memberlevelsEntityList==null||memberlevelsEntityList.size()<=0) {
				return DataTool.constructResponse(ResultCode.NO_DATA, "暂无会员级别", null);
			}
			Map<String,Object> resultMap = new HashMap<String, Object>();
			resultMap.put("memberlevelsEntityList", memberlevelsEntityList);
			return DataTool.constructResponse(ResultCode.OK, "查询成功", resultMap);
		} catch (Exception e) {
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
		}
	}
	
	/**
	 * 查询员工列表
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("listMember")
	public String listMember(HttpServletRequest request, HttpServletResponse response) {
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
			List<MemberVO> memberList = memberService.listMember(queryMap);
			if(ValidateTool.isNull(memberList)||memberList.size()<=0) {
				return DataTool.constructResponse(ResultCode.NO_DATA, "暂无会员", null);
			} else {
				int totalrecord = memberService.getMemberTotalCount(queryMap); //查询会员总数量
				Map<String,Object> resultMap = new HashMap<String, Object>();
				resultMap.put("memberList", memberList);
				resultMap.put("iTotalRecord", totalrecord);
				resultMap.put("iTotalPage", totalrecord%iPagesize == 0 ? totalrecord/iPagesize : totalrecord/iPagesize+1);
				return DataTool.constructResponse(ResultCode.OK, "查询成功", resultMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
		}
		
	}
	
}
