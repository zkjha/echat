package com.ecard.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecard.entity.ImgAdvertisementEntity;
import com.ecard.mapper.ImgAdvertisementSetMapper;


@Service
@Transactional
public class ImgAdvertisementSetService {
	@Autowired
	private ImgAdvertisementSetMapper imgAdvertisementSetMapper;
	//插入前端图片广告
	@Transactional
	public int insertImgAdvertisement(ImgAdvertisementEntity imgAdvertisementEntity) throws Exception
	{	int rcdNum=0;
		int iMaxImgOrder;
		//调用findRecordBeforeInsertion()接口，取出表中iImgOrder中的最大值 
		int isExistsRecord=imgAdvertisementSetMapper.findAllRecordCount();
		if(isExistsRecord!=0)
			iMaxImgOrder=imgAdvertisementSetMapper.findRecordBeforeInsertion()+1;	//原表中有记录时，则增加1
		else
			iMaxImgOrder=1;					//原表中无记录时
		imgAdvertisementEntity.setIImgOrder(iMaxImgOrder);
		//插入图片广告
		rcdNum=imgAdvertisementSetMapper.insertImgAdvertisement(imgAdvertisementEntity);
		return rcdNum;
	}
	
	
	
	//图片广告上移,下移
	@Transactional
	public int moveImgAdvertisement(ImgAdvertisementEntity imgAdvertisementEntity,String strStatus) throws Exception
	{	
		//查出图片序号iImgOrder
		int flag=0,rcdNum=0;
		int affectRecordOrder=0,totalRecordNum=0;
		int iIMgOrder=imgAdvertisementSetMapper.findImgOrderById(imgAdvertisementEntity.getStrImgId());
		totalRecordNum=imgAdvertisementSetMapper.findAllRecordCount();
		if("0".equals(strStatus))	//上移 
			{
			affectRecordOrder=iIMgOrder-1;
			if(affectRecordOrder==0)
				{
				rcdNum=-1;
				return rcdNum;
				}
			}
		
		if("1".equals(strStatus))	//下移
			{
			affectRecordOrder=iIMgOrder+1;
			if(affectRecordOrder>totalRecordNum)
				{
				rcdNum=1;
				return rcdNum;
				}
			}
		//查出受影响的记录Id
		String affectRecordId=imgAdvertisementSetMapper.findIdByOrder(affectRecordOrder);
		//设置imgAdvertisementEntity的图片序号
		imgAdvertisementEntity.setIImgOrder(affectRecordOrder);
		//新建一个实体
		ImgAdvertisementEntity affectImgAdvertisementEntity=new ImgAdvertisementEntity();
		affectImgAdvertisementEntity.setStrImgId(affectRecordId);
		affectImgAdvertisementEntity.setIImgOrder(iIMgOrder);
		affectImgAdvertisementEntity.setStrLastAccessedTime(imgAdvertisementEntity.getStrLastAccessedTime());
		affectImgAdvertisementEntity.setStrEmployeeId(imgAdvertisementEntity.getStrEmployeeId());
		affectImgAdvertisementEntity.setStrEmployeeName(imgAdvertisementEntity.getStrEmployeeName());
		affectImgAdvertisementEntity.setStrEmployeeRealName(imgAdvertisementEntity.getStrEmployeeRealName());
		flag=imgAdvertisementSetMapper.moveImgAdvertisement(imgAdvertisementEntity);
		if(flag!=0)
			rcdNum=rcdNum+1;
		rcdNum=imgAdvertisementSetMapper.moveImgAdvertisement(affectImgAdvertisementEntity);
		if(flag!=0)
			rcdNum=rcdNum+1;
		return rcdNum;
	}
	
	
	//删除图片广告
	public int delImgAdvertisement(String strImgId) throws Exception
	{
		return imgAdvertisementSetMapper.delImgAdvertisement(strImgId);
	}

}
