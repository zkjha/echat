package com.ecard.service;

import java.util.List;


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
		//调用findRecordBeforeInsertion()接口，取出表中iImgOrder中的最大值 
		int iMaxImgOrder=imgAdvertisementSetMapper.findRecordBeforeInsertion();
		if(iMaxImgOrder==0)
			iMaxImgOrder=1;					//原表中无记录时
		else
			iMaxImgOrder+=1;				//原表中有记录时，则增加1
		imgAdvertisementEntity.setIImgOrder(iMaxImgOrder);
		//插入图片广告
		rcdNum=imgAdvertisementSetMapper.insertImgAdvertisement(imgAdvertisementEntity);
		return rcdNum;
	}
	
	
	
	//图片广告上移
	@Transactional
	public int imgAdvertisementForward(String strImgId) throws Exception
	{	
		int rcdNum=0;
		List<ImgAdvertisementEntity> listImgAdvEntity=imgAdvertisementSetMapper.getForwardOrderEntity(strImgId);
		if(listImgAdvEntity==null||listImgAdvEntity.size()==0)
			return rcdNum;
		if(listImgAdvEntity.size()>=2)
		{
			int[] imgOrder=new int[2];
			//取出imgOrder值 :即图片序号
			for(int i=0;i<listImgAdvEntity.size();i++)
			{
				imgOrder[i]=listImgAdvEntity.get(i).getIImgOrder();
				
			}
			//交换图片序号
			for(int i=0;i<listImgAdvEntity.size();i++)
			{
				int j=listImgAdvEntity.size()-1-i;
				listImgAdvEntity.get(i).setIImgOrder(imgOrder[j]);
			}
			//将对应的两条数据写入数据库
			int affectNum=imgAdvertisementSetMapper.imgAdvertisementForward(listImgAdvEntity);
			if(affectNum!=0)
				rcdNum=affectNum;
		}
		return rcdNum;
	}

}
