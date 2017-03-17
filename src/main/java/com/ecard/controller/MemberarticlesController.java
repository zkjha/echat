package com.ecard.controller;

import java.util.Date;

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
import com.ecard.entity.MemberarticlesEntity;
import com.ecard.service.MemberarticlesService;
/**
 * 会员章程资料信息控制层
 * @author zhengwei
 *
 */
@Controller
@RequestMapping("/admin/biz/memberarticles")
public class MemberarticlesController {
	
	@Resource
	private MemberarticlesService memberarticlesService;
	
	/**
	 * 查询会员章程资料信息
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getMemberarticles")
	public String getMemberarticles() {
		
		try {
			MemberarticlesEntity memberarticlesEntity = memberarticlesService.getOneMemberarticles();
			if(ValidateTool.isNull(memberarticlesEntity)) {
				return DataTool.constructResponse(ResultCode.NO_DATA, "暂无会员章程资料信息", null);
			}
			return DataTool.constructResponse(ResultCode.OK, "查询成功", memberarticlesEntity);
		} catch (Exception e) {
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
		}
	}
	
	/**
	 * 修改会员章程资料信息
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("updateMemberarticles")
	public String updateMemberarticles(HttpServletRequest request, HttpServletResponse response) {
		String strContent = request.getParameter("strContent");
		if(ValidateTool.isEmptyStr(strContent)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "会员章程资料类容不能为空", null);
		}
		try {
			MemberarticlesEntity memberarticlesEntity = new MemberarticlesEntity();
			memberarticlesEntity.setStrArticlesid(DataTool.getUUID());
			memberarticlesEntity.setStrContent(strContent.trim());
			memberarticlesEntity.setStrInserttime(DateTool.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
			memberarticlesService.insertMemberarticles(memberarticlesEntity);
			
			return DataTool.constructResponse(ResultCode.OK, "保存成功", null);
		} catch (Exception e) {
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
		}
	}
	
	
}
