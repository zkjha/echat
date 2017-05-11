package com.ecard.mapper;


import com.ecard.entity.ImgAdvertisementEntity;

public interface ImgAdvertisementSetMapper {
	//出找记录总条数
	public int findAllRecordCount() throws Exception;
	//找出iImgOrder中值最大的记录，取出该字段值
	public int findRecordBeforeInsertion() throws Exception;
	//插入前端首页图片广告
	public int insertImgAdvertisement(ImgAdvertisementEntity imgAdvertisementEntity) throws Exception;
	
	//上移图片广告--将交换序号的结果写入数据库
	//查出imgOrder
	public int findImgOrderById(String strImgId) throws Exception;
	//查出IMGiD
	public String findIdByOrder(int iImgOrder) throws Exception;
	//移动图片广告
	public int moveImgAdvertisement(ImgAdvertisementEntity imgAdvertisementEntity) throws Exception;
	//删除图片广告
	public int delImgAdvertisement(String strImgId) throws Exception;
	

}
