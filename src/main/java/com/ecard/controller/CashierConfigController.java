

/**
 * fun:
 * author:kinglong
 * create time:2017年3月19日
 */
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
import com.ecard.entity.MeasurementUnitEntity;
import com.ecard.service.CashierConfigService;

/**
 * author:kinglong
 *
 */
@Controller
@RequestMapping("/admin/biz/Cashier")
public class CashierConfigController {
    
	@Resource
	private CashierConfigService tCashierConfigService;
	
	// 新增计量单位
	@ResponseBody
	@RequestMapping("insertUnit")
	public String insertUnit(HttpServletRequest request, HttpServletResponse response) {
		//String strUnitId = request.getParameter("strUnitId");
		String strUnitName = request.getParameter("strUnitName");
		String strUnitDesc = request.getParameter("strUnitDesc");
		//String strReserved = request.getParameter("strReserved");

		if(strUnitName ==null || strUnitName.isEmpty()){
			return DataTool.constructResponse(ResultCode.OK, "计量单位名称不能为空", null);
		}
		if(strUnitDesc ==null || strUnitDesc.isEmpty()){
			strUnitDesc = " ";
		}

		MeasurementUnitEntity tMeasurementUnitEntity=new MeasurementUnitEntity();
		tMeasurementUnitEntity.setStrUnitId(DataTool.getUUID());
		tMeasurementUnitEntity.setStrUnitName(strUnitName);
		tMeasurementUnitEntity.setStrUnitDesc(strUnitDesc);
		tMeasurementUnitEntity.setStrReserved("");
		
		try {
			return tCashierConfigService.insertMeasurementUnitRecord(tMeasurementUnitEntity);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.OK, "新增失败", null);
		}
	}
	
    // 修改计量单位
	@ResponseBody
	@RequestMapping("updateUnit")
	public String updateUnit(HttpServletRequest request, HttpServletResponse response) {
		String strUnitId = request.getParameter("strUnitId");
		String strUnitName = request.getParameter("strUnitName");
		String strUnitDesc = request.getParameter("strUnitDesc");
		//String strReserved = request.getParameter("strReserved");

		if(strUnitId ==null || strUnitId.isEmpty())
		{
			return DataTool.constructResponse(ResultCode.OK, "计量单位ID不能为空", null);
		}
		if(strUnitName ==null || strUnitName.isEmpty()){
			return DataTool.constructResponse(ResultCode.OK, "计量单位名称不能为空", null);
		}
		if(strUnitDesc ==null || strUnitDesc.isEmpty()){
			strUnitDesc = " ";
		}

		MeasurementUnitEntity tMeasurementUnitEntity=new MeasurementUnitEntity();
		tMeasurementUnitEntity.setStrUnitId(DataTool.getUUID());
		tMeasurementUnitEntity.setStrUnitName(strUnitName);
		tMeasurementUnitEntity.setStrUnitDesc(strUnitDesc);
		tMeasurementUnitEntity.setStrReserved("");
		
		
		try {
			return tCashierConfigService.updateMeasurementUnitRecord(tMeasurementUnitEntity);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.OK, "修改失败", null);
		}
	}
	
	// 删除计量单位
	@ResponseBody
	@RequestMapping("delUnit")
	public String delUnit(HttpServletRequest request, HttpServletResponse response) {
		
		return DataTool.constructResponse(ResultCode.OK, "删除成功", null);
	}
	
	// 查询计量详情
	@ResponseBody
	@RequestMapping("selUnitDetail")
	public String selUnitDetail(HttpServletRequest request, HttpServletResponse response) {
		String strUnitId = request.getParameter("strUnitId");

		if(strUnitId ==null || strUnitId.isEmpty())
		{
			return DataTool.constructResponse(ResultCode.OK, "计量单位ID不能为空", null);
		}
		
		try {
			return tCashierConfigService.deleteMeasurementUnitRecord(strUnitId);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.OK, "删除失败", null);
		}
	}
	
	// 分页查询计量列表
	@ResponseBody
	@RequestMapping("selUnitList")
	public String selUnitList(HttpServletRequest request, HttpServletResponse response) {
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
		
		Map<String,Object> queryMap = new HashMap<String, Object>();
		queryMap.put("pageFrom", pageFrom);
		queryMap.put("pageSize", iPagesize);
		
		try {
			List<MeasurementUnitEntity> unitList = tCashierConfigService.listMeasurementUnit(queryMap);
			
			if(ValidateTool.isNull(unitList)||unitList.size()<=0) {
				return DataTool.constructResponse(ResultCode.NO_DATA, "暂无会员", null);
			} else {
				int totalrecord = tCashierConfigService.getMeasurementUnitTotalCount(queryMap); //查询会员总数量
				Map<String,Object> resultMap = new HashMap<String, Object>();
				resultMap.put("unitList", unitList);
				resultMap.put("iTotalRecord", totalrecord);
				resultMap.put("iTotalPage", totalrecord%iPagesize == 0 ? totalrecord/iPagesize : totalrecord/iPagesize+1);
				return DataTool.constructResponse(ResultCode.OK, "查询成功", resultMap);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return DataTool.constructResponse(ResultCode.OK, "查询失败", null);
		}
		
		//return DataTool.constructResponse(ResultCode.OK, "新增成功", null);
	}
}
