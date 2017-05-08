

/**
 * fun:
 * author:qidongbo
 * create time:2017年4月1日
 */
package com.ecard.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.commontools.data.DataTool;
import com.ecard.config.ResultCode;
import com.ecard.entity.VoucherticketUseInfoEntity;
import com.ecard.mapper.VoucherticketUseInfoMapper;

/**
 * @author apple
 * 抵用券操作业务
 */
@Service
@Transactional
public class VoucherticketUseInfoService {
	//生成service层方法
	//获取一条VoucherticketUseInfo记录
	@Autowired
	private VoucherticketUseInfoMapper tVoucherticketUseInfoMapper;

	@Transactional(rollbackFor=Exception.class)
	public VoucherticketUseInfoEntity getVoucherticketUseInfo(String strVoucherUseInfoId) throws Exception{
	    return tVoucherticketUseInfoMapper.getVoucherticketUseInfo(strVoucherUseInfoId);
	}


	//新增一条VoucherticketUseInfo记录
	@Transactional(rollbackFor=Exception.class)
	public String insertVoucherticketUseInfo(VoucherticketUseInfoEntity tVoucherticketUseInfo) throws Exception{
	    int iAffectNum = tVoucherticketUseInfoMapper.insertVoucherticketUseInfo(tVoucherticketUseInfo);
	    if (0 == iAffectNum)
	    {
	        return DataTool.constructResponse(ResultCode.LACK_INTEGRAL, "数据库操作失败", null);
	    }
	    return DataTool.constructResponse(ResultCode.OK,"添加成功", null);
	}


	//更新一条VoucherticketUseInfo记录
	@Transactional(rollbackFor=Exception.class)
	public String updateVoucherticketUseInfo(VoucherticketUseInfoEntity tVoucherticketUseInfo) throws Exception{
	    int iAffectNum = tVoucherticketUseInfoMapper.updateVoucherticketUseInfo(tVoucherticketUseInfo);
	    if (0 == iAffectNum)
	    {
	        return DataTool.constructResponse(ResultCode.NO_DATA, "数据记录ID不存在", null);
	    }
	    return DataTool.constructResponse(ResultCode.OK,"修改成功", null);
	}


	//删除一条VoucherticketUseInfo记录
	@Transactional(rollbackFor=Exception.class)
	public String deleteVoucherticketUseInfo(String strVoucherUseInfoId) throws Exception{
	    int iAffectNum = tVoucherticketUseInfoMapper.deleteVoucherticketUseInfoById(strVoucherUseInfoId);
	    if (0 == iAffectNum)
	    {
	        return DataTool.constructResponse(ResultCode.NO_DATA, "无数据", null);
	    }
	    return DataTool.constructResponse(ResultCode.OK,"删除成功", null);
	}


	//获取VoucherticketUseInfo列表
	public List<VoucherticketUseInfoEntity> getListVoucherticketUseInfo(Map<String, Object> queryMap) throws Exception{
	    return tVoucherticketUseInfoMapper.getListVoucherticketUseInfo(queryMap);
	}

	
	//根据会员ID和优惠券状态获取会员的优惠券列表
	public List<VoucherticketUseInfoEntity> getListVoucherticketUseInfoByMemberId(String strMemberId, int iStat) throws Exception{
	    return tVoucherticketUseInfoMapper.getListVoucherticketUseInfoByMemberId(strMemberId,iStat);
	}

}
