package com.ecard.controller;

import java.util.Date;
import java.util.HashMap;
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
import com.ecard.entity.EmployeeEntity;
import com.ecard.entity.MerchantEntity;
import com.ecard.service.MerchantService;
import com.ecard.util.WebSessionUtil;
/**
 * 商家资料控制层
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/admin/")
public class MerchantController {
	
	@Resource
	private MerchantService merchantService;
	@Resource
	private WebSessionUtil webSessionUtil;
	
	/**
	 * 查询商家信息
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("biz/merchant/getMerchantInfo")
	public String getMerchantInfo(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			EmployeeEntity employeeEntity = (EmployeeEntity) webSessionUtil.getWebSession(
					request, response).getAttribute("employeeEntity");
			MerchantEntity merchantEntity = merchantService.getMerchantById(employeeEntity.getStrMerchantid());
			Map<String,Object> resultMap = new HashMap<String,Object>();
			resultMap.put("strImgrootpath", StaticValue.IMAGE_ROOT_PATH);
			resultMap.put("merchantEntity", merchantEntity);
			return DataTool.constructResponse(ResultCode.OK, "查询成功", resultMap);
		} catch (Exception e) {
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
		}
	}
	
	/**
	 * 修改商家信息
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("biz/merchant/updateMerchantInfo")
	public String updateMerchantInfo(HttpServletRequest request, HttpServletResponse response) {
		
		String strMerchantname = request.getParameter("strMerchantname");
		String strMerchantaddress = request.getParameter("strMerchantaddress");
		String strMerchantlogo = request.getParameter("strMerchantlogo");
		if(ValidateTool.isEmptyStr(strMerchantname)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "商家名称不能为空", null);
		}
		if(ValidateTool.isEmptyStr(strMerchantaddress)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "商家地址不能为空", null);
		}
		if(ValidateTool.isEmptyStr(strMerchantlogo)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "商家LOGO不能为空", null);
		}
		try {
			EmployeeEntity employeeEntity = (EmployeeEntity) webSessionUtil.getWebSession(
					request, response).getAttribute("employeeEntity");
			
			MerchantEntity merchantEntity = new MerchantEntity();
			merchantEntity.setStrMerchantid(employeeEntity.getStrMerchantid());
			merchantEntity.setStrMerchantname(strMerchantname.trim());
			merchantEntity.setStrMerchantaddress(strMerchantaddress.trim());
			merchantEntity.setStrMerchantlogo(strMerchantlogo.trim());
			merchantEntity.setStrUpdatetime(DateTool.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
			merchantService.updateMerchant(merchantEntity);
			return DataTool.constructResponse(ResultCode.OK, "修改成功", merchantEntity);
		} catch (Exception e) {
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
		}
	}
	
}
