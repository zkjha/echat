

/**
 * fun:后台  收银相关配置的数据库操作
 * author:qidongbo
 * create time:2017年3月19日
 */
package com.ecard.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ecard.entity.GoodsTypeConfigEntity;
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
	
	
	
	// 收银-商品分类，分类的新增，修改，查询列表，根据ID查询详情，删除（如果该分类下有商品应该不能删除）
	//分页获取列表
	public List<GoodsTypeConfigEntity> getListGoodsTypeConfigEntity(Map<String, Object> queryMap) throws RuntimeException;

	//获取单个对象
	public GoodsTypeConfigEntity getGoodsTypeConfigEntity(String strGoodsTypeId) throws RuntimeException;

	//新增单条记录
	public int insertGoodsTypeConfigEntity(GoodsTypeConfigEntity bean) throws RuntimeException;

	//更新单条记录
	public int updateGoodsTypeConfigEntity(GoodsTypeConfigEntity obj) throws RuntimeException;

	//删除单条记录
	public int deleteGoodsTypeConfigEntity(String strGoodsTypeId) throws RuntimeException;
	
	//获取商品总数量
	public int getGoodsTypeTotalCount(Map<String, Object> queryMap) throws Exception;

}
