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
import com.ecard.entity.DutyEntity;
import com.ecard.entity.DutyPrivilegeEntity;
import com.ecard.service.DutyService;
import com.ecard.util.WebSessionUtil;
/**
 * 职务控制层
 * @author dinghongxing
 *
 */
@Controller
@RequestMapping("/admin/biz/duty")
public class DutyController {
	
	@Resource
	private DutyService dutyService;
	@Resource
	private WebSessionUtil webSessionUtil;
	
	/**
	 * 判断职务名称是否存在
	 * @param request
	 * @param response
	 * @return
	 */

	@ResponseBody
	@RequestMapping("judgeDutyNameIsExist")
	public String judgeDutyNameIsExist(HttpServletRequest request, HttpServletResponse response) {
		String strDutyid = request.getParameter("strDutyid");
		String strDutyname = request.getParameter("strDutyname");
		if(ValidateTool.isEmptyStr(strDutyname)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "职务名称不能为空", null);
		}
		try {
			String flag = dutyService.judgeDutyNameIsExist(strDutyid, strDutyname.trim());
			return DataTool.constructResponse(ResultCode.OK, "查询成功", flag);
		} catch (Exception e) {
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
		}
	}
	
	/**
	 * 新增职务
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("insertDuty")
	public String insertDuty(HttpServletRequest request, HttpServletResponse response) {
		String strDutyname = request.getParameter("strDutyname");
		String privilegeids = request.getParameter("privilegeids");
		if(ValidateTool.isEmptyStr(strDutyname)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "职务名称不能为空", null);
		}
		if(ValidateTool.isEmptyStr(privilegeids)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "权限不能为空", null);
		}
		String [] arrPrivilegeid = privilegeids.split(",");
		if(arrPrivilegeid==null||arrPrivilegeid.length<0) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "权限不能为空", null);
		}
		try {
			
			return dutyService.insertDuty(strDutyname.trim(),arrPrivilegeid);
		} catch (Exception e) {
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
		}
	}
	
	/**
	 * 根据职务ID查询职务
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getDutyById")
	public String getDutyById(HttpServletRequest request, HttpServletResponse response) {
		String strDutyid = request.getParameter("strDutyid");
		if(ValidateTool.isEmptyStr(strDutyid)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "职务ID不能为空", null);
		}
		try {
			DutyEntity dutyEntity = dutyService.getDutyById(strDutyid);
			if(ValidateTool.isNull(dutyEntity)) {
				return DataTool.constructResponse(ResultCode.NO_DATA, "职务不存在", dutyEntity);
			}
			List<String> privilegeids = dutyService.listPrivilegeIdByDutyId(strDutyid);
			Map<String,Object> resultMap = new HashMap<String, Object>();
			resultMap.put("dutyEntity", dutyEntity);
			resultMap.put("privilegeids", privilegeids);
			return DataTool.constructResponse(ResultCode.OK, "查询成功", resultMap);
		} catch (Exception e) {
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
		}
	}
	
	/**
	 * 修改职务
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("updateDuty")
	public String updateDuty(HttpServletRequest request, HttpServletResponse response) {
		String strDutyid = request.getParameter("strDutyid");
		String strDutyname = request.getParameter("strDutyname");
		String privilegeids = request.getParameter("privilegeids");
		if(ValidateTool.isEmptyStr(strDutyid)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "职务ID不能为空", null);
		}
		if(ValidateTool.isEmptyStr(strDutyname)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "职务名称不能为空", null);
		}
		if(ValidateTool.isEmptyStr(privilegeids)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "权限不能为空", null);
		}
		String [] arrPrivilegeid = privilegeids.split(",");
		if(arrPrivilegeid==null||arrPrivilegeid.length<0) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "权限不能为空", null);
		}
		
		try {
			return dutyService.updateDuty(strDutyid, strDutyname.trim(),arrPrivilegeid);
		} catch (Exception e) {
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
		}
	}
	
	/**
	 * 查询职务列表
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("listDuty")
	public String listDuty(HttpServletRequest request, HttpServletResponse response) {
		String pagenum = request.getParameter("pagenum");
		String pagesize = request.getParameter("pagesize");
		
		if(ValidateTool.isEmptyStr(pagenum)) {
			pagenum = "1";
		}
		int iPagesize = StaticValue.PAGE_SIZE;
		if(!ValidateTool.isEmptyStr(pagesize)) {
			iPagesize = Integer.valueOf(pagesize);
		}
		
		int pageFrom = (Integer.parseInt(pagenum)-1)*iPagesize;
		
		
		try {
			
			List<DutyEntity> dutyEntityList = dutyService.listDuty(pageFrom, iPagesize); //分页查询职务列表
			if(ValidateTool.isNull(dutyEntityList)||dutyEntityList.size()<=0) {
				return DataTool.constructResponse(ResultCode.NO_DATA, "暂无职务", null);
			} else {
				int totalrecord = dutyService.getDutyTotalCount(); //查询职务总数量
				Map<String,Object> resultMap = new HashMap<String, Object>();
				resultMap.put("dutyEntityList", dutyEntityList);
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
	 * 新增职务权限关系
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("insertDutyPrivilege")
	public String insertDutyPrivilege(HttpServletRequest request, HttpServletResponse response) {
		String strDutyid = request.getParameter("strDutyid");
		String strPrivilegeid = request.getParameter("strPrivilegeid");
		if(ValidateTool.isEmptyStr(strDutyid)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "职务ID不能为空", null);
		}
		if(ValidateTool.isEmptyStr(strPrivilegeid)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "权限ID不能为空", null);
		}
		try {
			DutyPrivilegeEntity dutyPrivilegeEntity = new DutyPrivilegeEntity();
			dutyPrivilegeEntity.setStrRelationid(DataTool.getUUID());
			dutyPrivilegeEntity.setStrDutyid(strDutyid);
			dutyPrivilegeEntity.setStrPrivilegeid(strPrivilegeid);
			dutyService.insertDutyPrivilege(dutyPrivilegeEntity);
			return DataTool.constructResponse(ResultCode.OK, "新增成功", null);
		} catch (Exception e) {
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
		}
	}
	
	/**
	 * 删除职务权限关系
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("deleteDutyPrivilege")
	public String deleteDutyPrivilege(HttpServletRequest request, HttpServletResponse response) {
		String strDutyid = request.getParameter("strDutyid");
		String strPrivilegeid = request.getParameter("strPrivilegeid");
		if(ValidateTool.isEmptyStr(strDutyid)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "职务ID不能为空", null);
		}
		if(ValidateTool.isEmptyStr(strPrivilegeid)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "权限ID不能为空", null);
		}
		try {
			dutyService.deleteDutyPrivilege(strPrivilegeid, strDutyid);
			return DataTool.constructResponse(ResultCode.OK, "删除成功", null);
		} catch (Exception e) {
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
		}
	}
	
}
