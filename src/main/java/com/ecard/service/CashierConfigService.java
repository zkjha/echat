

/**
 * fun:后台 收银相关配置信息服务
 * author:qidongbo
 * create time:2017年3月19日
 */
package com.ecard.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.commontools.data.DataTool;
import com.ecard.config.ResultCode;
import com.ecard.entity.MeasurementUnitEntity;
import com.ecard.mapper.CashierConfigMapper;

/**
 * author:qidongbo
 *
 */
@Service
@Transactional
public class CashierConfigService {

	@Autowired
	private CashierConfigMapper tCashierConfigMapper;
	
	// 新增加一条计量单位
	@Transactional(rollbackFor=Exception.class)
	public String insertMeasurementUnitRecord(MeasurementUnitEntity tMeasurementUnitEntity) throws Exception 
	{
       int iAffectRecord = 0;
	   try {
		   iAffectRecord = tCashierConfigMapper.addMeasurementUnit(tMeasurementUnitEntity);
	   } catch (Exception e) {
		// TODO Auto-generated catch block
		  e.printStackTrace();
		  
		  return DataTool.constructResponse(ResultCode.LACK_INTEGRAL, "数据库操作失败", null); 
	   }
       
       if (0 == iAffectRecord)
       {
    	   return DataTool.constructResponse(ResultCode.LACK_INTEGRAL, "数据库操作失败", null);
       }
		
       return DataTool.constructResponse(ResultCode.OK, "添加计量单位成功", null); 
	}
	
	
    // 修改一条计量单位 
	public String updateMeasurementUnitRecord(MeasurementUnitEntity tMeasurementUnitEntity) throws Exception 
	{
       int iAffectRecord = 0;
	   try {
		   iAffectRecord = tCashierConfigMapper.updateMeasurementUnit(tMeasurementUnitEntity);
	   } catch (Exception e) {
		// TODO Auto-generated catch block
		  e.printStackTrace();
		  
		  return DataTool.constructResponse(ResultCode.LACK_INTEGRAL, "数据库操作失败", null); 
	   }
       
       if (0 == iAffectRecord)
       {
    	   return DataTool.constructResponse(ResultCode.LACK_INTEGRAL, "数据库操作失败", null);
       }
		
       return DataTool.constructResponse(ResultCode.OK, "添加计量单位成功", null); 
	}
	
	// 删除一条计量单位
	@Transactional(rollbackFor=Exception.class)
	public String deleteMeasurementUnitRecord(String strUnitId) throws Exception 
	{
       int iAffectRecord = 0;
	   try {
		   iAffectRecord = tCashierConfigMapper.delMeasurementUnitById(strUnitId);
	   } catch (Exception e) {
		// TODO Auto-generated catch block
		  e.printStackTrace();
		  
		  return DataTool.constructResponse(ResultCode.LACK_INTEGRAL, "数据库操作失败", null); 
	   }
       
       if (0 == iAffectRecord)
       {
    	   return DataTool.constructResponse(ResultCode.LACK_INTEGRAL, "该计量单位不存在", null);
       }
		
       return DataTool.constructResponse(ResultCode.OK, "添加计量单位成功", null); 
	}
	
	// 查询计量单位详情 
	public MeasurementUnitEntity getMeasurementUnitDetailById(String strUnitId) throws Exception 
	{
		try {
			return tCashierConfigMapper.getMeasurementUnitById(strUnitId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	//查询会员总数量
	public int getMeasurementUnitTotalCount(Map<String, Object> queryMap) throws Exception {
		return tCashierConfigMapper.getMeasurementUnitTotalCount(queryMap);
	}
	
	// 分页查询计量单位信息
	public List<MeasurementUnitEntity> listMeasurementUnit(Map<String, Object> queryMap) throws Exception {
		return tCashierConfigMapper.getMeasurementUnitList(queryMap);
	}
	
}
