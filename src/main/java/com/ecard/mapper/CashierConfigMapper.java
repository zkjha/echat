

/**
 * fun:后台  收银相关配置的数据库操作
 * author:qidongbo
 * create time:2017年3月19日
 */
package com.ecard.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ecard.entity.MeasurementUnitEntity;

/**
 * author:qidongbo
 *
 */
public interface CashierConfigMapper {
	//分页获取计量单位列表
	public int getMeasurementUnitTotalCount(Map<String, Object> queryMap) throws Exception;

	//分页获取计量单位列表
	public List<MeasurementUnitEntity> getMeasurementUnitList(Map<String, Object> queryMap) throws Exception;

	//获取单个计量单位对象
	public MeasurementUnitEntity getMeasurementUnitById(@Param(value="strUnitId")String strUnitId) throws Exception;

	//新增单条计量单位记录
	public int addMeasurementUnit(MeasurementUnitEntity tMeasurementUnitEntity) throws Exception;

	//更新单条计量单位记录
	public int updateMeasurementUnit(MeasurementUnitEntity tMeasurementUnitEntity) throws Exception;

	//删除单条计量单位记录
	public int delMeasurementUnitById(@Param(value="strUnitId") String strUnitId) throws Exception;

}
