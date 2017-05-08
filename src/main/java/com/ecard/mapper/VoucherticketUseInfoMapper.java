

/**
 * fun:
 * author:qidongbo
 * create time:2017年3月31日
 */
package com.ecard.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ecard.entity.VoucherticketUseInfoEntity;

/**
 * @author apple
 *
 */
public interface VoucherticketUseInfoMapper {

	//分页获取列表
	public List<VoucherticketUseInfoEntity> getListVoucherticketUseInfo(Map<String, Object> queryMap) throws RuntimeException;

	//获取单个对象
	public VoucherticketUseInfoEntity getVoucherticketUseInfo(String strVoucherUseInfoId) throws RuntimeException;

	//新增单条记录
	public int insertVoucherticketUseInfo(VoucherticketUseInfoEntity bean) throws RuntimeException;

	//更新单条记录
	public int updateVoucherticketUseInfo(VoucherticketUseInfoEntity obj) throws RuntimeException;

	//删除单条记录
	public int deleteVoucherticketUseInfoById(String strVoucherUseInfoId) throws RuntimeException;


	//根据会员ID获取抵用券列表
	public List<VoucherticketUseInfoEntity> getListVoucherticketUseInfoByMemberId(@Param(value="strMemberId")String strMemberId, @Param(value="iStat")int iStat) throws RuntimeException;

}
