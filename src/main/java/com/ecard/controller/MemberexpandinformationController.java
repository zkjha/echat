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
import com.ecard.entity.MemberexpandinformationEntity;
import com.ecard.enumeration.MemberexpandinformationTypeEnum;
import com.ecard.service.MemberexpandinformationService;
/**
 * 会员拓展资料信息控制层
 * @author zhengwei
 *
 */
@Controller
@RequestMapping("/admin/biz/memberexpandinformation")
public class MemberexpandinformationController {
	
	@Resource
	private MemberexpandinformationService memberexpandinformationService;
	

	/**
	 * 新增拓展资料信息
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("insertMemberexpandinformation")
	public String insertMemberexpandinformation(HttpServletRequest request, HttpServletResponse response) {
		String strInformationname = request.getParameter("strInformationname");
		String strIsmust = request.getParameter("intIsmust");
		String strType = request.getParameter("intType");
		String strOptions = request.getParameter("strOptions");
		if(ValidateTool.isEmptyStr(strInformationname)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "拓展资料名称不能为空", null);
		}
		if(ValidateTool.isEmptyStr(strIsmust)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "是否必填不能为空", null);
		}
		if(ValidateTool.isEmptyStr(strType)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "类型不能为空", null);
		}
		int intType = Integer.valueOf(strType);
		if(intType==MemberexpandinformationTypeEnum.SELECT.getValue()||intType==MemberexpandinformationTypeEnum.CHECKBOX.getValue()) {
			//select checkbox
			if(ValidateTool.isEmptyStr(strOptions)) {
				return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "选项不能为空", null);
			}
		}
		
		try {
			MemberexpandinformationEntity memberexpandinformationEntity = new MemberexpandinformationEntity();
			memberexpandinformationEntity.setStrInformationid(DataTool.getUUID());
			memberexpandinformationEntity.setStrInformationname(strInformationname.trim());
			memberexpandinformationEntity.setIntIsmust(Integer.valueOf(strIsmust));
			memberexpandinformationEntity.setIntType(intType);
			memberexpandinformationEntity.setStrOptions(DataTool.trimStr(strOptions));
			memberexpandinformationEntity.setStrInserttime(DateTool.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
			memberexpandinformationService.insertMemberexpandinformation(memberexpandinformationEntity);
			return DataTool.constructResponse(ResultCode.OK, "新增成功", null);
		} catch (Exception e) {
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
		}
	}
	
	/**
	 * 根据拓展资料ID查询拓展资料信息
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getMemberexpandinformationById")
	public String getMemberexpandinformationById(HttpServletRequest request, HttpServletResponse response) {
		String strInformationid = request.getParameter("strInformationid");
		if(ValidateTool.isEmptyStr(strInformationid)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "拓展资料ID不能为空", null);
		}
		try {
			MemberexpandinformationEntity memberexpandinformationEntity = memberexpandinformationService.getMemberexpandinformationById(strInformationid);
			if(ValidateTool.isNull(memberexpandinformationEntity)) {
				return DataTool.constructResponse(ResultCode.NO_DATA, "拓展资料不存在", null);
			}
			return DataTool.constructResponse(ResultCode.OK, "查询成功", memberexpandinformationEntity);
		} catch (Exception e) {
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
		}
	}
	
	/**
	 * 修改拓展资料
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("updateMemberexpandinformation")
	public String updateMemberexpandinformation(HttpServletRequest request, HttpServletResponse response) {
		String strInformationid = request.getParameter("strInformationid");
		String strInformationname = request.getParameter("strInformationname");
		String strIsmust = request.getParameter("intIsmust");
		String strType = request.getParameter("intType");
		String strOptions = request.getParameter("strOptions");
		if(ValidateTool.isEmptyStr(strInformationid)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "拓展资料ID不能为空", null);
		}
		if(ValidateTool.isEmptyStr(strInformationname)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "拓展资料名称不能为空", null);
		}
		if(ValidateTool.isEmptyStr(strType)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "类型不能为空", null);
		}
		if(ValidateTool.isEmptyStr(strIsmust)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "是否必填不能为空", null);
		}
		int intType = Integer.valueOf(strType);
		if(intType==MemberexpandinformationTypeEnum.SELECT.getValue()||intType==MemberexpandinformationTypeEnum.CHECKBOX.getValue()) {
			//select checkbox
			if(ValidateTool.isEmptyStr(strOptions)) {
				return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "选项不能为空", null);
			}
		}
		try {
			MemberexpandinformationEntity memberexpandinformationEntity = new MemberexpandinformationEntity();
			memberexpandinformationEntity.setStrInformationid(strInformationid);
			memberexpandinformationEntity.setStrInformationname(strInformationname.trim());
			memberexpandinformationEntity.setIntIsmust(Integer.valueOf(strIsmust));
			memberexpandinformationEntity.setIntType(intType);
			memberexpandinformationEntity.setStrOptions(DataTool.trimStr(strOptions));
			memberexpandinformationEntity.setStrUpdatetime(DateTool.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
			memberexpandinformationService.updateMemberexpandinformation(memberexpandinformationEntity);
			return DataTool.constructResponse(ResultCode.OK, "修改成功", null);
		} catch (Exception e) {
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
		}
	}
	
	/**
	 * 查询拓展资料列表
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("listMemberexpandinformation")
	public String listMemberexpandinformation(HttpServletRequest request, HttpServletResponse response) {
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
			List<MemberexpandinformationEntity> memberexpandinformationEntityList = memberexpandinformationService.listMemberexpandinformation(queryMap);//查询会员拓展资料
			if(ValidateTool.isNull(memberexpandinformationEntityList)||memberexpandinformationEntityList.size()<=0) {
				return DataTool.constructResponse(ResultCode.NO_DATA, "暂无会员拓展资料", null);
			} else {
				int totalrecord = memberexpandinformationService.getMemberexpandinformationTotalCount(queryMap); //查询会员拓展资料总数量
				Map<String,Object> resultMap = new HashMap<String, Object>();
				resultMap.put("memberexpandinformationEntityList", memberexpandinformationEntityList);
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
	 * 删除拓展资料
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("deleteMemberexpandinformation")
	public String deleteMemberexpandinformation(HttpServletRequest request, HttpServletResponse response) {
		String strInformationid = request.getParameter("strInformationid");
		if(ValidateTool.isEmptyStr(strInformationid)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "拓展资料ID不能为空", null);
		}
		try {
			memberexpandinformationService.deleteMemberexpandinformation(strInformationid);
			return DataTool.constructResponse(ResultCode.OK, "删除成功", null);
			
		} catch (Exception e) {
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
		}
		
	}
	
	
}
