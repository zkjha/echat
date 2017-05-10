package com.ecard.mapper;

import java.util.List;

import com.ecard.entity.ImgAdvertisementEntity;

public interface ImgAdvertisementSetMapper {
	//找出iImgOrder中值最大的记录，取出该字段值
	public int findRecordBeforeInsertion() throws Exception;
	//插入前端首页图片广告
	public int insertImgAdvertisement(ImgAdvertisementEntity imgAdvertisementEntity) throws Exception;
	//上移图片广告--取得上移影响的实例
	public List<ImgAdvertisementEntity> getForwardOrderEntity(String strId) throws Exception;
	//上移图片广告--将交换序号的结果写入数据库
	public int imgAdvertisementForward(List<ImgAdvertisementEntity> listImgAdvertisementEntity) throws Exception;
	

}
