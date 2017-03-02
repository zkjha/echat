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
import com.commontools.secret.MD5Tool;
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
			if(ValidateTool.isNull(merchantEntity)) {
				return DataTool.constructResponse(ResultCode.NO_DATA, "暂无商家信息", null);
			}
			int intValiddays = merchantEntity.getIntValiddays();
			merchantEntity.setStrExpirationtime(DateTool.addDay(DateTool.DateToString(new Date(), DateStyle.YYYY_MM_DD), intValiddays));
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
		
		try {
			EmployeeEntity employeeEntity = (EmployeeEntity) webSessionUtil.getWebSession(
					request, response).getAttribute("employeeEntity");
			
			MerchantEntity merchantEntity = new MerchantEntity();
			merchantEntity.setStrMerchantid(employeeEntity.getStrMerchantid());
			merchantEntity.setStrMerchantname(strMerchantname.trim());
			merchantEntity.setStrMerchantaddress(strMerchantaddress.trim());
			merchantEntity.setStrMerchantlogo(strMerchantlogo);
			merchantEntity.setStrUpdatetime(DateTool.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
			merchantService.updateMerchant(merchantEntity);
			return DataTool.constructResponse(ResultCode.OK, "修改成功", merchantEntity);
		} catch (Exception e) {
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
		}
	}
	
	/**
	 * 商家系统升级
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("biz/merchant/upgradeMerchantSystem")
	public String upgradeMerchantSystem(HttpServletRequest request, HttpServletResponse response) {
		
		String strActivationcode = request.getParameter("strActivationcode");
		if(ValidateTool.isEmptyStr(strActivationcode)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "激活码不能为空", null);
		}
		try {
			EmployeeEntity employeeEntity = (EmployeeEntity) webSessionUtil.getWebSession(
					request, response).getAttribute("employeeEntity");
			//1.根据激活码远程调用激活系统查询激活码信息,可能返回激活码失效
			
			MerchantEntity merchantEntity = new MerchantEntity();
			merchantEntity.setStrMerchantid(employeeEntity.getStrMerchantid());
			merchantEntity.setStrSystemversion("中级版");
			merchantEntity.setIntValiddays(365);
			merchantEntity.setIntMembercount(500);
			merchantEntity.setStrSystemsecret(MD5Tool.createMd5("ecard_365_500")); //ecard_到期天数_会员数量
			merchantEntity.setStrUpdatetime(DateTool.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
			merchantService.upgradeMerchantSystem(merchantEntity);
			
			Map<String,Object> resultMap = new HashMap<String, Object>();
			resultMap.put("strSystemversion", merchantEntity.getStrSystemversion());
			resultMap.put("IntMembercount", merchantEntity.getIntMembercount());
			resultMap.put("strExpirationtime", DateTool.addDay(DateTool.DateToString(new Date(), DateStyle.YYYY_MM_DD), merchantEntity.getIntValiddays()));
			
			return DataTool.constructResponse(ResultCode.OK, "激活成功", resultMap);
		} catch (Exception e) {
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
		}
	}
	
	
}
