package com.ecard.service;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.commontools.data.DataTool;
import com.commontools.validate.ValidateTool;
import com.ecard.config.ResultCode;
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
		int iImgOrder=imgAdvertisementEntity.getIImgOrder();
		ImgAdvertisementEntity affectImgAdvertisementEntity = null;

		//解决可不可以移动的问题---------------------
		if("0".equals(strStatus))			//上移
			{
			affectImgAdvertisementEntity=imgAdvertisementSetMapper.upwardImageAdvertisement(iImgOrder);//查出要移动对象及受上移影响的记录是否都存在
			if(ValidateTool.isNull(affectImgAdvertisementEntity))
			return -1;						//返回"此图片广告已处在最上层"信息.	
			}
		if("1".equals(strStatus))			//下移
			{
			affectImgAdvertisementEntity=imgAdvertisementSetMapper.downwardImageAdvertisement(iImgOrder);//查出要移动对象及受上移影响的记录是否都存在
			if(ValidateTool.isNull(affectImgAdvertisementEntity))
			return 1;						//返回"此图片广告已处在最下层"信息.
			}
		
		imgAdvertisementEntity.setIImgOrder(affectImgAdvertisementEntity.getIImgOrder());
	
		affectImgAdvertisementEntity.setIImgOrder(iImgOrder);
		affectImgAdvertisementEntity.setStrImgId(affectImgAdvertisementEntity.getStrImgId());
		affectImgAdvertisementEntity.setStrLastAccessedTime(imgAdvertisementEntity.getStrLastAccessedTime());
		affectImgAdvertisementEntity.setStrEmployeeId(imgAdvertisementEntity.getStrEmployeeId());
		affectImgAdvertisementEntity.setStrEmployeeName(imgAdvertisementEntity.getStrEmployeeName());
		affectImgAdvertisementEntity.setStrEmployeeRealName(imgAdvertisementEntity.getStrEmployeeRealName());
		
		flag=imgAdvertisementSetMapper.moveImgAdvertisement(affectImgAdvertisementEntity);
		if(flag!=0)
			rcdNum++;
		flag=imgAdvertisementSetMapper.moveImgAdvertisement(imgAdvertisementEntity);
		if(flag!=0)
			rcdNum++;
		return rcdNum;
	}
	
	
	//删除图片广告
	public int delImgAdvertisement(String strImgId) throws Exception
	{
		return imgAdvertisementSetMapper.delImgAdvertisement(strImgId);
	}
	
	//查询所有图片广告信息
	public List<ImgAdvertisementEntity> findAllImaggeAdvertisement() throws Exception
	{
		return imgAdvertisementSetMapper.findAllImaggeAdvertisement();
	}

}
