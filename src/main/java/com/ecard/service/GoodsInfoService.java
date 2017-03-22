

/**
 * fun:
 * author:qidongbo
 * create time:2017年3月21日
 */
package com.ecard.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.commontools.data.DataTool;
import com.ecard.config.ResultCode;
import com.ecard.entity.GoodsInfoEntity;
import com.ecard.mapper.CashierConfigMapper;
import com.ecard.mapper.GoodsInfoMapper;

/**
 * @author apple
 *
 */
@Service
@Transactional
public class GoodsInfoService {
	//获取一条GoodsInfo记录
	@Autowired
	private GoodsInfoMapper tGoodsInfoMapper;
	
	@Autowired
	private CashierConfigMapper tCashierConfigMapper;
	
	@Transactional(rollbackFor=Exception.class)
	public GoodsInfoEntity getGoodsInfo(String strGoodsId) throws Exception{
	    return tGoodsInfoMapper.getGoodsInfo(strGoodsId);
	}


	//新增一条GoodsInfo记录
	@Transactional(rollbackFor=Exception.class)
	public String insertGoodsInfo(GoodsInfoEntity tGoodsInfo) throws Exception{
		// 先判断typeid是否存在
		if (tCashierConfigMapper.isGoodsTypeExists(tGoodsInfo.getStrGoodsTypeId()) == 0)
		{
			return DataTool.constructResponse(ResultCode.NO_DATA, "商品类型不存在", null);
		}
		
	    int iAffectNum = tGoodsInfoMapper.insertGoodsInfo(tGoodsInfo);
	    if (0 == iAffectNum)
	    {
	        return DataTool.constructResponse(ResultCode.LACK_INTEGRAL, "数据库操作失败", null);
	    }
	    return DataTool.constructResponse(ResultCode.OK,"添加成功", null);
	}


	//更新一条GoodsInfo记录
	@Transactional(rollbackFor=Exception.class)
	public String updateGoodsInfo(GoodsInfoEntity tGoodsInfo) throws Exception{
	    int iAffectNum = tGoodsInfoMapper.updateGoodsInfo(tGoodsInfo);
	    if (0 == iAffectNum)
	    {
	        return DataTool.constructResponse(ResultCode.NO_DATA, "数据记录ID不存在", null);
	    }
	    return DataTool.constructResponse(ResultCode.OK,"修改成功", null);
	}


	//删除一条GoodsInfo记录
	@Transactional(rollbackFor=Exception.class)
	public String deleteGoodsInfo(String strGoodsId) throws Exception{
	    int iAffectNum = tGoodsInfoMapper.deleteGoodsInfoById(strGoodsId);
	    if (0 == iAffectNum)
	    {
	        return DataTool.constructResponse(ResultCode.NO_DATA, "无数据", null);
	    }
	    return DataTool.constructResponse(ResultCode.OK,"删除成功", null);
	}


	//获取GoodsInfo列表
	public List<GoodsInfoEntity> getListGoodsInfo(Map<String, Object> queryMap) throws Exception{
		
		//System.out.println("getListGoodsInfo2");  
	    return tGoodsInfoMapper.getListGoodsInfo(queryMap);
	}
	
	//获取GoodsInfo总数
	public int getGoodsInfoTotalCount(Map<String, Object> queryMap) throws Exception{
	    return tGoodsInfoMapper.getGoodsInfoTotalCount(queryMap);
	}
	
	//判断某种类型的商品是否存在
	public int getGoodsInfoCountByGoodsType(String strGoodsTypeId) throws Exception{
	    return tGoodsInfoMapper.getGoodsInfoCountByGoodsType(strGoodsTypeId);
	}

}
