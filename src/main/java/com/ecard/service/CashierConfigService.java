
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
import com.ecard.entity.GoodsTypeConfigEntity;
import com.ecard.entity.MeasurementUnitEntity;
import com.ecard.entity.ServiceTypeEntity;
import com.ecard.mapper.CashierConfigMapper;
import com.ecard.mapper.GoodsInfoMapper;

/**
 * author:qidongbo
 *
 */
@Service
@Transactional
public class CashierConfigService {

	@Autowired
	private CashierConfigMapper tCashierConfigMapper;

	@Autowired
	private GoodsInfoMapper tGoodsInfoMapper;

	// 新增加一条计量单位
	@Transactional(rollbackFor = Exception.class)
	public String insertMeasurementUnitRecord(
			MeasurementUnitEntity tMeasurementUnitEntity) throws Exception {
		try {
			tCashierConfigMapper.addMeasurementUnit(tMeasurementUnitEntity);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return DataTool.constructResponse(ResultCode.LACK_INTEGRAL,
					"数据库操作失败", null);
		}

		return DataTool.constructResponse(ResultCode.OK, "添加计量单位成功", null);
	}

	// 修改一条计量单位
	@Transactional(rollbackFor = Exception.class)
	public String updateMeasurementUnitRecord(
			MeasurementUnitEntity tMeasurementUnitEntity) throws Exception {
		int iAffectRecord = 0;
		try {
			iAffectRecord = tCashierConfigMapper
					.updateMeasurementUnit(tMeasurementUnitEntity);

			if (0 == iAffectRecord) {
				return DataTool.constructResponse(ResultCode.NO_DATA, "无记录更新",
						null);
			}

			return DataTool.constructResponse(ResultCode.OK, "修改计量单位成功", null);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return DataTool.constructResponse(ResultCode.LACK_INTEGRAL,
					"数据库操作失败", null);
		}

		/*
		 * if (0 == iAffectRecord) { return
		 * DataTool.constructResponse(ResultCode.LACK_INTEGRAL, "该ID不存在", null);
		 * }
		 * 
		 * return DataTool.constructResponse(ResultCode.OK, "修改计量单位成功", null);
		 */

	}

	// 删除一条计量单位
	@Transactional(rollbackFor = Exception.class)
	public String deleteMeasurementUnitRecord(String strUnitId)
			throws Exception {
		int iAffectRecord = 0;
		try {
			iAffectRecord = tCashierConfigMapper
					.delMeasurementUnitById(strUnitId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return DataTool.constructResponse(ResultCode.LACK_INTEGRAL,
					"数据库操作失败", null);
		}

		if (0 == iAffectRecord) {
			return DataTool.constructResponse(ResultCode.NO_DATA, "该计量单位不存在",
					null);
		}

		return DataTool.constructResponse(ResultCode.OK, "删除计量单位成功", null);
	}

	// 查询计量单位详情
	public MeasurementUnitEntity getMeasurementUnitDetailById(String strUnitId)
			throws Exception {
		try {
			return tCashierConfigMapper.getMeasurementUnitById(strUnitId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	// 查询会员总数量
	public int getMeasurementUnitTotalCount(Map<String, Object> queryMap)
			throws Exception {
		return tCashierConfigMapper.getMeasurementUnitTotalCount(queryMap);
	}

	// 分页查询计量单位信息
	public List<MeasurementUnitEntity> listMeasurementUnit(
			Map<String, Object> queryMap) throws Exception {
		return tCashierConfigMapper.getMeasurementUnitList(queryMap);
	}

	
	
	
	
	// 获取一条GoodsTypeConfigEntity记录
	@Transactional(rollbackFor = Exception.class)
	public GoodsTypeConfigEntity getGoodsTypeConfigEntity(String strGoodsTypeId)
			throws Exception {
		return tCashierConfigMapper.getGoodsTypeConfigEntity(strGoodsTypeId);
	}

	// 新增一条GoodsTypeConfigEntity记录
	@Transactional(rollbackFor = Exception.class)
	public String insertGoodsTypeConfigEntity(
			GoodsTypeConfigEntity tGoodsTypeConfigEntity) throws Exception {
		int iAffectNum = tCashierConfigMapper
				.insertGoodsTypeConfigEntity(tGoodsTypeConfigEntity);
		if (0 == iAffectNum) {
			return DataTool.constructResponse(ResultCode.LACK_INTEGRAL,
					"数据库操作失败", null);
		}
		return DataTool.constructResponse(ResultCode.OK, "添加成功", null);
	}

	// 更新一条GoodsTypeConfigEntity记录
	public String updateGoodsTypeConfigEntity(
			GoodsTypeConfigEntity tGoodsTypeConfigEntity) throws Exception {
		int iAffectNum = tCashierConfigMapper
				.updateGoodsTypeConfigEntity(tGoodsTypeConfigEntity);
		if (0 == iAffectNum) {
			return DataTool
					.constructResponse(ResultCode.NO_DATA, "无数据更新", null);
		}
		return DataTool.constructResponse(ResultCode.OK, "修改成功", null);
	}

	// 删除一条GoodsTypeConfigEntity记录
	public String deleteGoodsTypeConfigEntity(String strGoodsTypeId)
			throws Exception {

		// 首先判断该商品类型下是否有商品 如果有商品 不能删除
		if (tGoodsInfoMapper.getGoodsInfoCountByGoodsType(strGoodsTypeId) > 0) {
			return DataTool.constructResponse(ResultCode.UNKNOW_ERROR,
					"该商品类型下存在商品,无法删除!", null);
		}

		int iAffectNum = tCashierConfigMapper
				.deleteGoodsTypeConfigEntity(strGoodsTypeId);
		if (0 == iAffectNum) {
			return DataTool
					.constructResponse(ResultCode.NO_DATA, "无数据更新", null);
		}
		return DataTool.constructResponse(ResultCode.OK, "删除成功", null);
	}

	// 获取GoodsTypeConfigEntity列表
	public List<GoodsTypeConfigEntity> getListGoodsTypeConfigEntity(
			Map<String, Object> queryMap) throws Exception {
		return tCashierConfigMapper.getListGoodsTypeConfigEntity(queryMap);
	}

	// 查询商品类型总数量
	public int getGoodsTypeTotalCount(Map<String, Object> queryMap)
			throws Exception {
		return tCashierConfigMapper.getGoodsTypeTotalCount(queryMap);
	}

	// 查询商品类型是否存在
	public int isGoodsTypeExists(String strGoodsTypeId) throws Exception {
		return tCashierConfigMapper.isGoodsTypeExists(strGoodsTypeId);
	}
	
	
	
	//服务类型相关业务操作
	//获取一条ServiceType记录

	@Transactional(rollbackFor = Exception.class)
	public ServiceTypeEntity getServiceType(String strServiceTypeId)
			throws Exception {
		return tCashierConfigMapper.getServiceType(strServiceTypeId);
	}

	// 新增一条ServiceType记录
	@Transactional(rollbackFor = Exception.class)
	public String insertServiceType(ServiceTypeEntity tServiceType)
			throws Exception {
		int iAffectNum = tCashierConfigMapper.insertServiceType(tServiceType);
		if (0 == iAffectNum) {
			return DataTool.constructResponse(ResultCode.LACK_INTEGRAL,
					"数据库操作失败", null);
		}
		return DataTool.constructResponse(ResultCode.OK, "添加成功", null);
	}

	// 更新一条ServiceType记录
	@Transactional(rollbackFor = Exception.class)
	public String updateServiceType(ServiceTypeEntity tServiceType)
			throws Exception {
		int iAffectNum = tCashierConfigMapper.updateServiceType(tServiceType);
		if (0 == iAffectNum) {
			return DataTool.constructResponse(ResultCode.NO_DATA, "数据记录ID不存在",
					null);
		}
		return DataTool.constructResponse(ResultCode.OK, "修改成功", null);
	}

	// 删除一条ServiceType记录
	@Transactional(rollbackFor = Exception.class)
	public String deleteServiceType(String strServiceTypeId) throws Exception {
		int iAffectNum = tCashierConfigMapper
				.deleteServiceTypeById(strServiceTypeId);
		if (0 == iAffectNum) {
			return DataTool.constructResponse(ResultCode.NO_DATA, "无数据", null);
		}
		return DataTool.constructResponse(ResultCode.OK, "删除成功", null);
	}

	// 获取ServiceType列表
	public List<ServiceTypeEntity> getListServiceType(
			Map<String, Object> queryMap) throws Exception {
		return tCashierConfigMapper.getListServiceType(queryMap);
	}

	// 获取ServiceType记录数量
	public int getServiceTypeTotalCount(Map<String, Object> queryMap)
			throws Exception {
		return tCashierConfigMapper.getServiceTypeTotalCount(queryMap);
	}
	
	// 查询服务类型是否存在
	public int isServiceTypeExists(String strServiceTypeId) throws Exception {
		return tCashierConfigMapper.isServiceTypeExists(strServiceTypeId);
	}
}
