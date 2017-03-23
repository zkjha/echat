

/**
 * fun:
 * author:qidongbo
 * create time:2017年3月23日
 */
package com.ecard.mapper;

import java.util.List;
import java.util.Map;

import com.ecard.entity.LoopAdvPicEntity;

/**
 * @author apple
 *
 */
public interface LoopAdvPicMapper {
	//分页获取列表
	public List<LoopAdvPicEntity> getListLoopAdvPic() throws RuntimeException;

	//获取单个对象
	public LoopAdvPicEntity getLoopAdvPic(String strAdvPicId) throws RuntimeException;

	//新增单条记录
	public int insertLoopAdvPic(LoopAdvPicEntity bean) throws RuntimeException;

	//更新单条记录
	public int updateLoopAdvPic(LoopAdvPicEntity obj) throws RuntimeException;

	//删除单条记录
	public int deleteLoopAdvPicById(String strAdvPicId) throws RuntimeException;

	//查询总记录数
	public int getLoopAdvPicTotalCount(Map<String, Object> queryMap) throws RuntimeException;

	//查询记录是否存在
	public int isLoopAdvPicExists(String strAdvPicId) throws RuntimeException;


}
