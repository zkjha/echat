

/**
 * fun:
 * author:qidongbo
 * create time:2017年3月22日
 */
package com.ecard.mapper;

import java.util.List;
import java.util.Map;

import com.ecard.entity.ServiceInfoEntity;
import com.ecard.entity.ServicePreferentialEntity;

/**
 * @author apple
 *
 */
public interface ServiceInfoMapper {
	//分页获取列表
	public List<ServiceInfoEntity> getListServiceInfo(Map<String, Object> queryMap) throws RuntimeException;

	//获取单个对象
	public ServiceInfoEntity getServiceInfo(String strServiceInfoId) throws RuntimeException;

	//新增单条记录
	public int insertServiceInfo(ServiceInfoEntity bean) throws RuntimeException;

	//更新单条记录
	public int updateServiceInfo(ServiceInfoEntity obj) throws RuntimeException;

	//删除单条记录
	public int deleteServiceInfoById(String strServiceInfoId) throws RuntimeException;

	//查询总记录数
	public int getServiceInfoTotalCount(Map<String, Object> queryMap) throws RuntimeException;

	//查询记录是否存在
	public int isServiceInfoExists(String strServiceInfoId) throws RuntimeException;

	//查询某类服务类型下有多少服务项目
	public int getServiceInfoCountByTypeId(String strServiceTypeId) throws RuntimeException;
	
	
	
	// 以下是获取会员优惠信息的接口
	//根据服务ID获取会员优惠信息
	public List<ServicePreferentialEntity> getListServicePreferentialByServiceId(String strServiceInfoId) throws RuntimeException;

	//获取单个对象
	public ServicePreferentialEntity getServicePreferential(String strPreferentialId) throws RuntimeException;

	//新增单条记录
	public int insertServicePreferential(ServicePreferentialEntity bean) throws RuntimeException;

	//更新单条记录
	public int updateServicePreferential(ServicePreferentialEntity obj) throws RuntimeException;

	//删除单条记录
	public int deleteServicePreferentialById(String strPreferentialId) throws RuntimeException;
}
