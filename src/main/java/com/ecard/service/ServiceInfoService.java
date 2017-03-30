

/**
 * fun:
 * author:qidongbo
 * create time:2017年3月22日
 */
package com.ecard.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.commontools.data.DataTool;
import com.commontools.date.DateStyle;
import com.commontools.date.DateTool;
import com.ecard.config.ResultCode;
import com.ecard.entity.ServiceInfoEntity;
import com.ecard.entity.ServicePreferentialEntity;
import com.ecard.mapper.CashierConfigMapper;
import com.ecard.mapper.ServiceInfoMapper;

/**
 * @author apple
 *
 */
@Service
@Transactional
public class ServiceInfoService {
	
	@Autowired
	private ServiceInfoMapper tServiceInfoMapper;
	
	@Autowired
	private CashierConfigMapper tCashierConfigMapper;

	@Transactional(rollbackFor=Exception.class)
	public ServiceInfoEntity getServiceInfo(String strServiceInfoId) throws Exception{
	    return tServiceInfoMapper.getServiceInfo(strServiceInfoId);
	}


	//新增一条ServiceInfo记录
	@Transactional(rollbackFor=Exception.class)
	public String insertServiceInfo(ServiceInfoEntity tServiceInfo) throws Exception{
		
		// 判断服务类型是否存在
		if (tCashierConfigMapper.isServiceTypeExists(tServiceInfo.getStrServiceTypeId()) <= 0)
		{
			return DataTool.constructResponse(ResultCode.NO_DATA, "服务类型不存在", null);
		}
		
		//服务会员优惠信息
		if (tServiceInfo.getiPreferentialType() == 1)
		{
			List<ServicePreferentialEntity> listServicePreferentialEntity = tServiceInfo.getListServicePreferentialEntity();
			if (listServicePreferentialEntity == null || listServicePreferentialEntity.size() == 0)
			{
				return DataTool.constructResponse(ResultCode.NO_DATA, "请填写分级别优惠信息", null);
			}
			
			for (int iLoop = 0; iLoop < listServicePreferentialEntity.size(); iLoop++)
			{
				listServicePreferentialEntity.get(iLoop).setStrPreferentialId(DataTool.getUUID());
				
				listServicePreferentialEntity.get(iLoop).setStrServiceInfoId(tServiceInfo.getStrServiceInfoId());
				listServicePreferentialEntity.get(iLoop).setStrServiceInfoName(tServiceInfo.getStrServiceInfoName());
				
				// 填充操作人员信息
				listServicePreferentialEntity.get(iLoop).setStrEmployeeId(tServiceInfo.getStrEmployeeId());
				listServicePreferentialEntity.get(iLoop).setStrEmployeeLoginName(tServiceInfo.getStrEmployeeLoginName());
				listServicePreferentialEntity.get(iLoop).setStrEmployeeName(tServiceInfo.getStrEmployeeName());
				listServicePreferentialEntity.get(iLoop).setStrInsertTime(DateTool.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
				
				tServiceInfoMapper.insertServicePreferential(listServicePreferentialEntity.get(iLoop));
			}
		}
		
	    int iAffectNum = tServiceInfoMapper.insertServiceInfo(tServiceInfo);
	    if (0 == iAffectNum)
	    {
	        return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "数据库操作失败", null);
	    }
	    return DataTool.constructResponse(ResultCode.OK,"添加成功", null);
	}


	//更新一条ServiceInfo记录
	@Transactional(rollbackFor=Exception.class)
	public String updateServiceInfo(ServiceInfoEntity tServiceInfo) throws Exception{
		// 判断服务类型是否存在
		if (tCashierConfigMapper.isServiceTypeExists(tServiceInfo.getStrServiceTypeId()) <= 0)
		{
			return DataTool.constructResponse(ResultCode.NO_DATA, "服务类型不存在", null);
		}
		
		
		//服务会员优惠信息
		if (tServiceInfo.getiPreferentialType() == 1)
		{
			List<ServicePreferentialEntity> listServicePreferentialEntity = tServiceInfo.getListServicePreferentialEntity();
			if (listServicePreferentialEntity == null || listServicePreferentialEntity.size() == 0)
			{
				return DataTool.constructResponse(ResultCode.NO_DATA, "请填写分级别优惠信息", null);
			}
			
			for (int iLoop = 0; iLoop < listServicePreferentialEntity.size(); iLoop++)
			{
				listServicePreferentialEntity.get(iLoop).setStrServiceInfoId(tServiceInfo.getStrServiceInfoId());
				listServicePreferentialEntity.get(iLoop).setStrServiceInfoName(tServiceInfo.getStrServiceInfoName());
				
				// 填充操作人员信息
				listServicePreferentialEntity.get(iLoop).setStrEmployeeId(tServiceInfo.getStrEmployeeId());
				listServicePreferentialEntity.get(iLoop).setStrEmployeeLoginName(tServiceInfo.getStrEmployeeLoginName());
				listServicePreferentialEntity.get(iLoop).setStrEmployeeName(tServiceInfo.getStrEmployeeName());
				listServicePreferentialEntity.get(iLoop).setStrInsertTime(DateTool.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
				
				tServiceInfoMapper.updateServicePreferential(listServicePreferentialEntity.get(iLoop));
			}
		}
		
	    int iAffectNum = tServiceInfoMapper.updateServiceInfo(tServiceInfo);
	    if (0 == iAffectNum)
	    {
	        return DataTool.constructResponse(ResultCode.NO_DATA, "数据记录ID不存在", null);
	    }
	    return DataTool.constructResponse(ResultCode.OK,"修改成功", null);
	}


	//删除一条ServiceInfo记录
	@Transactional(rollbackFor=Exception.class)
	public String deleteServiceInfo(String strServiceInfoId) throws Exception{
	    int iAffectNum = tServiceInfoMapper.deleteServiceInfoById(strServiceInfoId);
	    if (0 == iAffectNum)
	    {
	        return DataTool.constructResponse(ResultCode.NO_DATA, "无数据", null);
	    }
	    return DataTool.constructResponse(ResultCode.OK,"删除成功", null);
	}


	//获取ServiceInfo列表
	public List<ServiceInfoEntity> getListServiceInfo(Map<String, Object> queryMap) throws Exception{
	    return tServiceInfoMapper.getListServiceInfo(queryMap);
	}


	//获取ServiceInfo记录数量
	public int getServiceInfoTotalCount(Map<String, Object> queryMap) throws Exception{
	    return tServiceInfoMapper.getServiceInfoTotalCount(queryMap);
	}
	
	
	//获取service会员级别优惠记录
	public List<ServicePreferentialEntity> getListServicePreferentialByServiceId(String strServiceInfoId) throws Exception{
	    return tServiceInfoMapper.getListServicePreferentialByServiceId(strServiceInfoId);
	}

}
