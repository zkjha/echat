

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
import com.ecard.entity.GoodsTypeConfigEntity;
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
	// localhost:8080/admin/biz/Cashier/insertUnit?strUnitName=克&strUnitDesc=单位描述
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
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "新增失败", null);
		}
	}
	
    // 修改计量单位
	// localhost:8080/admin/biz/Cashier/updateUnit?strUnitId=25836c37bf76437aa4d7a14755daf5d1&strUnitName=千克&strUnitDesc=你好好
	@ResponseBody
	@RequestMapping("updateUnit")
	public String updateUnit(HttpServletRequest request, HttpServletResponse response) {
		String strUnitId = request.getParameter("strUnitId");
		String strUnitName = request.getParameter("strUnitName");
		String strUnitDesc = request.getParameter("strUnitDesc");
		//String strReserved = request.getParameter("strReserved");

		if(strUnitId ==null || strUnitId.isEmpty())
		{
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "计量单位ID不能为空", null);
		}
		if(strUnitName ==null || strUnitName.isEmpty()){
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "计量单位名称不能为空", null);
		}

		if(strUnitDesc ==null || strUnitDesc.isEmpty()){
			//return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "计量描述不能为空", null);
			strUnitDesc = " ";
		}
		

		MeasurementUnitEntity tMeasurementUnitEntity=new MeasurementUnitEntity();
		tMeasurementUnitEntity.setStrUnitId(strUnitId);
		tMeasurementUnitEntity.setStrUnitName(strUnitName);
		tMeasurementUnitEntity.setStrUnitDesc(strUnitDesc);
		tMeasurementUnitEntity.setStrReserved("");
		
		
		try {
			return tCashierConfigService.updateMeasurementUnitRecord(tMeasurementUnitEntity);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "修改失败", null);
		}
	}
	
	// 删除计量单位
	// localhost:8080/admin/biz/Cashier/delUnit?strUnitId=123
	@ResponseBody
	@RequestMapping("delUnit")
	public String delUnit(HttpServletRequest request, HttpServletResponse response) {
		String strUnitId = request.getParameter("strUnitId");
		try {
			return tCashierConfigService.deleteMeasurementUnitRecord(strUnitId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "删除失败", null);
		}
	}
	
	// 查询计量详情
	// localhost:8080/admin/biz/Cashier/selUnitDetail?strUnitId=25836c37bf76437aa4d7a14755daf5d1
	@ResponseBody
	@RequestMapping("selUnitDetail")
	public String selUnitDetail(HttpServletRequest request, HttpServletResponse response) {
		String strUnitId = request.getParameter("strUnitId");		
		Map<String,Object> resultMap = new HashMap<String, Object>();

		if(strUnitId ==null || strUnitId.isEmpty())
		{
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "计量单位ID不能为空", null);
		}
		
		try {
			MeasurementUnitEntity tMeasurementUnitEntity = tCashierConfigService.getMeasurementUnitDetailById(strUnitId);
			
			if (tMeasurementUnitEntity == null || tMeasurementUnitEntity.getStrUnitId().isEmpty() || tMeasurementUnitEntity.getStrUnitId() == null)
			{
				return DataTool.constructResponse(ResultCode.NO_DATA, "会员不存在", null);
			}
			else 
			{
				resultMap.put("MeasurementUnit", tMeasurementUnitEntity);
				return DataTool.constructResponse(ResultCode.OK, "查询成功", resultMap);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "删除失败", null);
		}
	}
	
	// 分页查询计量列表
	// localhost:8080/admin/biz/Cashier/selUnitList?pagenum=1&pagesize=2
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
				return DataTool.constructResponse(ResultCode.NO_DATA, "暂无数据", null);
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
			
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "查询失败", null);
		}
		
		//return DataTool.constructResponse(ResultCode.OK, "新增成功", null);
	}
	
	
	// 插入一条商品类型
	// localhost:8080/admin/biz/Cashier/insertGoodsType?strGoodsTypeName=shangp1&strGoodsTypeDesc=nihao
	@ResponseBody
	@RequestMapping("insertGoodsType")
	public String insertGoodsType(HttpServletRequest request, HttpServletResponse response) {
		//String strGoodsTypeId = request.getParameter("strGoodsTypeId");
		String strGoodsTypeName = request.getParameter("strGoodsTypeName");
		String strGoodsTypeDesc = request.getParameter("strGoodsTypeDesc");
		String strReserved = request.getParameter("strReserved");

		if(strGoodsTypeName ==null || strGoodsTypeName.isEmpty()){
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "商品类型名称不能为空", null);
		}

		GoodsTypeConfigEntity tGoodsTypeConfigEntity=new GoodsTypeConfigEntity();
		tGoodsTypeConfigEntity.setStrGoodsTypeId(DataTool.getUUID());
		tGoodsTypeConfigEntity.setStrGoodsTypeName(strGoodsTypeName);
		tGoodsTypeConfigEntity.setStrGoodsTypeDesc(strGoodsTypeDesc);
		tGoodsTypeConfigEntity.setStrReserved(strReserved);


		//生成调用请求
		//获取一条GoodsTypeConfigEntity记录
		//tCashierConfigService.getGoodsTypeConfigEntity(strGoodsTypeId);
		//新增一条GoodsTypeConfigEntity记录
		try {
			tCashierConfigService.insertGoodsTypeConfigEntity(tGoodsTypeConfigEntity);
			return DataTool.constructResponse(ResultCode.OK, "新增成功", null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "新增失败", null);
		}
		//更新一条GoodsTypeConfigEntity记录
		//tCashierConfigService.updateGoodsTypeConfigEntity(tGoodsTypeConfigEntity);
		//删除一条GoodsTypeConfigEntity记录
		//tCashierConfigService.deleteGoodsTypeConfigEntity(strGoodsTypeId);

	}
	
	// 更新一条商品类型
	// localhost:8080/admin/biz/Cashier/updateGoodsType?strGoodsTypeName=shangp11111&strGoodsTypeDesc=nihao1111&strGoodsTypeId=ba4480c93a81487cb743e054ac48d4c2
	@ResponseBody
	@RequestMapping("updateGoodsType")
	public String updateGoodsType(HttpServletRequest request, HttpServletResponse response) {
		String strGoodsTypeId = request.getParameter("strGoodsTypeId");
		String strGoodsTypeName = request.getParameter("strGoodsTypeName");
		String strGoodsTypeDesc = request.getParameter("strGoodsTypeDesc");
		String strReserved = request.getParameter("strReserved");
		
		if(strGoodsTypeId ==null || strGoodsTypeId.isEmpty()){
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "商品类型ID不能为空", null);
		}

		if(strGoodsTypeName ==null || strGoodsTypeName.isEmpty()){
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "商品类型名称不能为空", null);
		}

		GoodsTypeConfigEntity tGoodsTypeConfigEntity=new GoodsTypeConfigEntity();
		tGoodsTypeConfigEntity.setStrGoodsTypeId(strGoodsTypeId);
		tGoodsTypeConfigEntity.setStrGoodsTypeName(strGoodsTypeName);
		tGoodsTypeConfigEntity.setStrGoodsTypeDesc(strGoodsTypeDesc);
		tGoodsTypeConfigEntity.setStrReserved(strReserved);


		//生成调用请求
		//获取一条GoodsTypeConfigEntity记录
		//tCashierConfigService.getGoodsTypeConfigEntity(strGoodsTypeId);
		//新增一条GoodsTypeConfigEntity记录
		try {
			return tCashierConfigService.updateGoodsTypeConfigEntity(tGoodsTypeConfigEntity);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "更新失败", null);
		}
		//更新一条GoodsTypeConfigEntity记录
		//tCashierConfigService.updateGoodsTypeConfigEntity(tGoodsTypeConfigEntity);
		//删除一条GoodsTypeConfigEntity记录
		//tCashierConfigService.deleteGoodsTypeConfigEntity(strGoodsTypeId);

	}
	
	// 删除一条商品类型
	// localhost:8080/admin/biz/Cashier/delGoodsType?strGoodsTypeId=ba4480c93a81487cb743e054ac48d4c2
	@ResponseBody
	@RequestMapping("delGoodsType")
	public String delGoodsType(HttpServletRequest request, HttpServletResponse response) {
		String strGoodsTypeId = request.getParameter("strGoodsTypeId");

		if(strGoodsTypeId ==null || strGoodsTypeId.isEmpty()){
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "商品类型ID不能为空", null);
		}

		//生成调用请求
		try {
			return tCashierConfigService.deleteGoodsTypeConfigEntity(strGoodsTypeId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "删除失败", null);
		}
	}
	
	
	// 分页查询商品类型列表
	//localhost:8080/admin/biz/Cashier/selGoodsTypeList?pagenum=1&pagesize=2
	@ResponseBody
	@RequestMapping("selGoodsTypeList")
	public String selGoodsTypeList(HttpServletRequest request, HttpServletResponse response) {
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
			List<GoodsTypeConfigEntity> goodsTypeList = tCashierConfigService.getListGoodsTypeConfigEntity(queryMap);
		
			if(ValidateTool.isNull(goodsTypeList)||goodsTypeList.size()<=0) {
				return DataTool.constructResponse(ResultCode.NO_DATA, "暂无商品类型", null);
			} else {
				int totalrecord = tCashierConfigService.getGoodsTypeTotalCount(queryMap); //查询会员总数量
				Map<String,Object> resultMap = new HashMap<String, Object>();
				resultMap.put("goodsTypeList", goodsTypeList);
				resultMap.put("iTotalRecord", totalrecord);
				resultMap.put("iTotalPage", totalrecord%iPagesize == 0 ? totalrecord/iPagesize : totalrecord/iPagesize+1);
				return DataTool.constructResponse(ResultCode.OK, "查询成功", resultMap);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "查询失败", null);
			
		}
	}
	
	// 查询计量详情
	// localhost:8080/admin/biz/Cashier/selGoodsTypeDetail?strGoodsTypeId=9a803c2e1c4844559c8b5ec58eb9a816
	@ResponseBody
	@RequestMapping("selGoodsTypeDetail")
	public String selGoodsTypeDetail(HttpServletRequest request, HttpServletResponse response) {
		String strGoodsTypeId = request.getParameter("strGoodsTypeId");	
		Map<String,Object> resultMap = new HashMap<String, Object>();

		if(strGoodsTypeId ==null || strGoodsTypeId.isEmpty())
		{
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "ID不能为空", null);
		}
		
		try {
			GoodsTypeConfigEntity tGoodsTypeConfigEntity = tCashierConfigService.getGoodsTypeConfigEntity(strGoodsTypeId);
			
			if (tGoodsTypeConfigEntity == null || tGoodsTypeConfigEntity.getStrGoodsTypeId().isEmpty() || tGoodsTypeConfigEntity.getStrGoodsTypeId() == null)
			{
				return DataTool.constructResponse(ResultCode.NO_DATA, "不存在数据", null);
			}
			else 
			{
				resultMap.put("goodsType", tGoodsTypeConfigEntity);
				return DataTool.constructResponse(ResultCode.OK, "查询成功", resultMap);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "查询失败", null);
		}
	}

}
