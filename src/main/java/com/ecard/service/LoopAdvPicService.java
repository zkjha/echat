

/**
 * fun:
 * author:qidongbo
 * create time:2017年3月23日
 */
package com.ecard.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.commontools.data.DataTool;
import com.ecard.config.ResultCode;
import com.ecard.entity.LoopAdvPicEntity;
import com.ecard.mapper.LoopAdvPicMapper;

/**
 * @author apple
 *
 */
@Service
@Transactional
public class LoopAdvPicService {

	//生成service层方法
	//获取一条LoopAdvPic记录
	@Autowired
	private LoopAdvPicMapper tLoopAdvPicMapper;

	@Transactional(rollbackFor=Exception.class)
	public LoopAdvPicEntity getLoopAdvPic(String strAdvPicId) throws Exception{
	    return tLoopAdvPicMapper.getLoopAdvPic(strAdvPicId);
	}


	//新增一条LoopAdvPic记录
	@Transactional(rollbackFor=Exception.class)
	public String insertLoopAdvPic(LoopAdvPicEntity tLoopAdvPic) throws Exception{
	    int iAffectNum = tLoopAdvPicMapper.insertLoopAdvPic(tLoopAdvPic);
	    if (0 == iAffectNum)
	    {
	        return DataTool.constructResponse(ResultCode.LACK_INTEGRAL, "数据库操作失败", null);
	    }
	    return DataTool.constructResponse(ResultCode.OK,"添加成功", null);
	}


	//更新一条LoopAdvPic记录
	@Transactional(rollbackFor=Exception.class)
	public String updateLoopAdvPic(LoopAdvPicEntity tLoopAdvPic) throws Exception{
	    int iAffectNum = tLoopAdvPicMapper.updateLoopAdvPic(tLoopAdvPic);
	    if (0 == iAffectNum)
	    {
	        return DataTool.constructResponse(ResultCode.NO_DATA, "数据记录ID不存在", null);
	    }
	    return DataTool.constructResponse(ResultCode.OK,"修改成功", null);
	}


	//删除一条LoopAdvPic记录
	@Transactional(rollbackFor=Exception.class)
	public String deleteLoopAdvPic(String strAdvPicId) throws Exception{
	    int iAffectNum = tLoopAdvPicMapper.deleteLoopAdvPicById(strAdvPicId);
	    if (0 == iAffectNum)
	    {
	        return DataTool.constructResponse(ResultCode.NO_DATA, "无数据", null);
	    }
	    return DataTool.constructResponse(ResultCode.OK,"删除成功", null);
	}


	//获取LoopAdvPic列表
	public List<LoopAdvPicEntity> getListLoopAdvPic() throws Exception{
	    return tLoopAdvPicMapper.getListLoopAdvPic();
	}


	//获取LoopAdvPic记录数量
	public int getLoopAdvPicTotalCount(Map<String, Object> queryMap) throws Exception{
	    return tLoopAdvPicMapper.getLoopAdvPicTotalCount(queryMap);
	}

}
