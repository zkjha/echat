

/**
 * fun:数据库积分记录操作
 * author:qidongbo
 * create time:2017年3月14日
 */
package com.ecard.mapper;

import com.ecard.entity.IntegralModRecord;

/**
 * author:qidongbo
 *
 */
public interface IntegralRecordOperMapper {
	
	 // 新插入记录
     void insertNewRecord(IntegralModRecord integralModRecord) throws Exception;
     
     
}
