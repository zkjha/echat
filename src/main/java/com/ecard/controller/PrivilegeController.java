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
import com.ecard.service.PrivilegeService;
import com.ecard.util.WebSessionUtil;
import com.ecard.vo.PrivilegeVO;
/**
 * 权限控制层
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/admin/biz/privilege")
public class PrivilegeController {
	
	@Resource
	private PrivilegeService privilegeService;
	@Resource
	private WebSessionUtil webSessionUtil;
	
	
	/**
	 * 查询所有全下信息，并组装（用于给职务赋权限的时候）
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("listPrivilege")
	public String listPrivilege(HttpServletRequest request, HttpServletResponse response) {
		String strDutyid = request.getParameter("strDutyid");
		try {
			
			List<PrivilegeVO> privilegeList = privilegeService.listAllPrivilege(strDutyid); //查询所有的权限信息
			if(ValidateTool.isNull(privilegeList)||privilegeList.size()<=0) {
				return DataTool.constructResponse(ResultCode.NO_DATA, "暂无权限", null);
			} else {
				Map<String,Object> resultMap = new HashMap<String,Object>();
				resultMap.put("privilegeList", privilegeList);
				return DataTool.constructResponse(ResultCode.OK, "查询成功", privilegeList);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
		}
		
	}
	
}
