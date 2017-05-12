package com.ecard.mapper;


import java.util.List;

import com.ecard.entity.ImgAdvertisementEntity;

public interface ImgAdvertisementSetMapper {
	//出找记录总条数
	public int findAllRecordCount() throws Exception;
	//找出iImgOrder中值最大的记录，取出该字段值
	public int findRecordBeforeInsertion() throws Exception;
	//插入前端首页图片广告
	public int insertImgAdvertisement(ImgAdvertisementEntity imgAdvertisementEntity) throws Exception;
	
	//移动图片广告
	public int moveImgAdvertisement(ImgAdvertisementEntity imgAdvertisementEntity) throws Exception;
	//删除图片广告
	public int delImgAdvertisement(String strImgId) throws Exception;
	//查询所有记录
	public List<ImgAdvertisementEntity> findAllImaggeAdvertisement() throws Exception;
	//检查是否可以上移
	public ImgAdvertisementEntity upwardImageAdvertisement(int iImgOrder) throws Exception;
	//检查是否可以下移
	public ImgAdvertisementEntity downwardImageAdvertisement(int iImgOrder) throws Exception;
	

}
