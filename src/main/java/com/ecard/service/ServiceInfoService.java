

/**
 * fun:
 * author:qidongbo
 * create time:2017年3月22日
 */
package com.ecard.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.commontools.data.DataTool;
import com.ecard.config.ResultCode;
import com.ecard.entity.ServiceInfoEntity;
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

}
