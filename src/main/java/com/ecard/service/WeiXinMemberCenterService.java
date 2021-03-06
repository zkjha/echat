package com.ecard.service;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecard.vo.MemberVO;
import com.commontools.data.DataTool;
import com.commontools.date.DateStyle;
import com.commontools.date.DateTool;
import com.ecard.config.ResultCode;
import com.ecard.entity.IntegralModRecord;
import com.ecard.entity.MemberEntity;
import com.ecard.entity.MemberLevelsRightsMappingEntity;
import com.ecard.entity.MemberarticlesEntity;
import com.ecard.entity.MemberlevelsEntity;
import com.ecard.entity.SignIntegrationRuleEntity;
import com.ecard.entity.VoucherTicketInfoEntity;
import com.ecard.entity.VoucherticketUseInfoEntity;
import com.ecard.entity.WeiXinMemberSignEntity;
import com.ecard.mapper.WeiXinMemberCenterMapper;
import com.ecard.util.QRCodeTool;
@Service
public class WeiXinMemberCenterService
{
	@Autowired
	private WeiXinMemberCenterMapper weiXinMemberCenterMapper;
	
	//查询登陆会员信息
	@Transactional
	public String selectMemberInfo(String strMemberId) throws Exception
	{
		int iUnUsedVoucherTicketAmount=0;	//特定会员的未使用的抵用券张数
		int iUsedVoucherTicketAmount=0;	//特定会员的已使用，但还可以继续使用的抵用券张数
		//登录用户不为空，查询登录用户对应的会员信息
		MemberVO memberVOEntity = weiXinMemberCenterMapper.selectMemberInfo(strMemberId);
		if(memberVOEntity==null)
			return DataTool.constructResponse(ResultCode.NO_DATA,"无会员信息，请重新登录",null);
		iUnUsedVoucherTicketAmount=weiXinMemberCenterMapper.selectUnUsedTicketAmount(strMemberId);
		iUsedVoucherTicketAmount=weiXinMemberCenterMapper.selectUsedTicketAmount(strMemberId);
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("strMemberCardNum", memberVOEntity.getStrMembercardnum());
		resultMap.put("intIntegration", memberVOEntity.getIntIntegral());
		resultMap.put("dBalance", memberVOEntity.getdBalance());
		resultMap.put("dAfterStoredBalance",memberVOEntity.getdAfterstoredbalance() );
		resultMap.put("strLevelsName", memberVOEntity.getStrLevelsname());
		String code_url_base64 = QRCodeTool.getQRCode(strMemberId);
		resultMap.put("strQrCode", "data:image/png;base64," + code_url_base64);
		resultMap.put("intVouchers",iUnUsedVoucherTicketAmount+iUsedVoucherTicketAmount);
		
		return DataTool.constructResponse(ResultCode.OK,"查询成功",resultMap);
	}
	
	//插入会员签到信息
	@Transactional
	public String insertMemberSignInfo(WeiXinMemberSignEntity memberSignEntity) throws Exception
	{
		String strHistorySignTime="";		//最近一次历史签到记录时间
		String strMsg="";		//备注信息
		Date dtHistorySignTime;	//最近一次签到时间
		int iPresentsIntegrationAmount=0;	//应赠送积分数量
		int iHaveHistorySignRecord=1;	// 是否有历史签到记录 1 有 0 无
		int iSignCount=1;	//连续签到次数
		int iTotalSignCount=1;	//累计连续签到次数
		int iAllSignAmount=0;	//总的签到次数：包括连续性签到和不连续的。
		//判断该会员今天是 否已经有签到信息存在，如果已签到，则本次不执行写入数据，反之，写入签到信息。原则，一天只签到一次
		int count=0;
		count=weiXinMemberCenterMapper.findCountByIdAndTime(memberSignEntity);
	
		if(count!=0)
			return DataTool.constructResponse(ResultCode.UNKNOW_ERROR,"亲，今天你已签过到了！",null);
		//查出最近一条签到记录
		WeiXinMemberSignEntity historyMemberSignEntity=weiXinMemberCenterMapper.selectMemberSignEntityById(memberSignEntity);
		if(historyMemberSignEntity==null)
			iHaveHistorySignRecord=0;
		if(historyMemberSignEntity!=null&&!historyMemberSignEntity.getStrMemberId().trim().isEmpty())
			iHaveHistorySignRecord=1;
		Map<String,Object> queryMap=new HashMap<String,Object>();
		switch (iHaveHistorySignRecord)
		{
		case 0:
			//无历史签到记录的情况： 
			memberSignEntity.setiSignCount(iSignCount);
			memberSignEntity.setiTotalSignCount(iTotalSignCount);
			//第一次登记:积分的计算 按不连续签到处理------查签到送积分表 
			
			queryMap.put("strStatus","0");
			queryMap.put("strEnabled","1");
			List<SignIntegrationRuleEntity> listSignIntegrationRuleEntity=weiXinMemberCenterMapper.selectIntegrationRuleInfo(queryMap);
			if(listSignIntegrationRuleEntity!=null&&listSignIntegrationRuleEntity.size()!=0)	//有签到送积分规则，需要处理积分；无规则，不处理
			{
				for(SignIntegrationRuleEntity ruleEntity:listSignIntegrationRuleEntity)
					if("1".equals(ruleEntity.getStrSignDays()))
					{
						iPresentsIntegrationAmount=ruleEntity.getIIntegration();
						strMsg="第一次签到赠送积分";
						break;
					}
				
			}
			break;
		case 1:
			//有历史签到记录的情况:
			long lTime=0;	//签到时间差
			long lOneDay=24*60*60*1000;
			iSignCount=historyMemberSignEntity.getiSignCount();	//取出已连续签到的次数
			iTotalSignCount=historyMemberSignEntity.getiTotalSignCount();//取出累计连续签到的次数
			Date dtCurrentTime=new Date();
			strHistorySignTime=historyMemberSignEntity.getStrSignTime();
			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			dtHistorySignTime=df.parse(strHistorySignTime);
			dtCurrentTime=DateTool.StringToDate(df.format(dtCurrentTime),DateStyle.YYYY_MM_DD_HH_MM_SS);
			lTime=dtCurrentTime.getTime()-dtHistorySignTime.getTime();
			iAllSignAmount=weiXinMemberCenterMapper.getSignDaysByMemberId(memberSignEntity.getStrMemberId());
			if(lTime>=lOneDay&&lTime<2*lOneDay)
			{	
				//连续签到的情况下：
				
				//查连续签到积分规则
				queryMap.put("strStatus","1");
				queryMap.put("strEnabled","1");
				SignIntegrationRuleEntity signIntegrationRuleEntity=weiXinMemberCenterMapper.selectRuleInfo(queryMap);
				if(signIntegrationRuleEntity!=null&&!signIntegrationRuleEntity.getStrSignId().isEmpty())
				{
					//有连续签到积分规则的情况下：
					//判断是否满足连续签到送积分的规则
					if(iSignCount+1==Integer.parseInt(signIntegrationRuleEntity.getStrSignDays()))
					{
						//满足连续签到送积分规则的情况下：
						iPresentsIntegrationAmount=signIntegrationRuleEntity.getIIntegration();
						iSignCount=0;
						iTotalSignCount+=1;
					}
					else
					{
						//不满足连续签到送积分规则的情况下
						iPresentsIntegrationAmount=0;
						iSignCount+=1;
						iTotalSignCount+=1;
					}
			
				}
				else
				{
					//无连续签到积分规则的情况下
					iPresentsIntegrationAmount=0;
					iSignCount+=1;
					iTotalSignCount+=1;
					
				}
				
				//查不连续签到积分规则
				queryMap.put("strStatus","0");
				List<SignIntegrationRuleEntity> listSignIntegrationRulesEntity=weiXinMemberCenterMapper.selectIntegrationRuleInfo(queryMap);
				if(listSignIntegrationRulesEntity!=null&&listSignIntegrationRulesEntity.size()!=0)
				{
					//有不连续签到积分规则的情况下：无不连续签到积分规则不处理
					
					// 判断是否满足不连续积分规则
					for(SignIntegrationRuleEntity rulesObj:listSignIntegrationRulesEntity)
					{
						if(Integer.parseInt(rulesObj.getStrSignDays())==iAllSignAmount+1)
						{
							//满足不连续签到送积分规则的情况下：不满足不处理
							iPresentsIntegrationAmount+=rulesObj.getIIntegration();
							break;
						}
					}
							
				}
				
				//设置签到记录的属性
				memberSignEntity.setiSignCount(iSignCount);
				memberSignEntity.setiTotalSignCount(iTotalSignCount);
				strMsg="签到赠送积分";
			}
			else
			{
				//非连续签到的情况下：
				iSignCount=1;
				iTotalSignCount=1;
				iPresentsIntegrationAmount=0;
				//查非连续签到积分规则
				queryMap.put("strStatus","0");
				queryMap.put("strEnabled","1");
				List<SignIntegrationRuleEntity> listSignIntegrationRulesEntity=weiXinMemberCenterMapper.selectIntegrationRuleInfo(queryMap);
				if(listSignIntegrationRulesEntity!=null&&listSignIntegrationRulesEntity.size()!=0)
				{
					//有不连续签到积分规则的情况下：无不连续签到积分规则不处理
					
					// 判断是否满足不连续积分规则
					for(SignIntegrationRuleEntity rulesObj:listSignIntegrationRulesEntity)
					{
						if(Integer.parseInt(rulesObj.getStrSignDays())==iAllSignAmount+1)
						{
							//满足不连续签到送积分规则的情况下：不满足不处理
							iPresentsIntegrationAmount=rulesObj.getIIntegration();
							break;
						}
					}
							
				}
				
				//设置签到记录的属性
				memberSignEntity.setiSignCount(iSignCount);
				memberSignEntity.setiTotalSignCount(iTotalSignCount);
				strMsg="非连续签到赠送积分";
			}
			break;

		}
		
		//调用方法  将计算产生的积分信息分别写入tb_integral_change_record     【会员积分手动变更记录表】和会员表 [tb_member}
		writeIntegrationInfo(memberSignEntity,iPresentsIntegrationAmount,strMsg);
		return DataTool.constructResponse(ResultCode.OK,"签到成功",null);
	}

	
	//将应赠送的积分信息分别写入tb_integral_change_record和tb_member
	@Transactional
	public void writeIntegrationInfo (WeiXinMemberSignEntity memberSignEntity,int iPresentsIntegrationAmount,String strMsg) throws Exception
	{
		//查会员信息
		if(iPresentsIntegrationAmount!=0)
		{
			int iMemberIntegration=0;	//会员总积分数量
		
			String strMemberId=memberSignEntity.getStrMemberId();
			MemberEntity memberEntity = weiXinMemberCenterMapper.selectMemberEntity(strMemberId);	
			
			iMemberIntegration=memberEntity.getIntIntegral()+iPresentsIntegrationAmount;
			//组装IntegralModRecord属性（积分变更)
			IntegralModRecord integrationChangedEntity=new IntegralModRecord();
			integrationChangedEntity.setStrRecordId(DataTool.getUUID());
			integrationChangedEntity.setStrMemberId(memberEntity.getStrMemberid());
			integrationChangedEntity.setStrMemberCardNum(memberEntity.getStrMembercardnum());
			integrationChangedEntity.setStrMemberName(memberEntity.getStrRealname());
			integrationChangedEntity.setiIntegralNum(iPresentsIntegrationAmount);
			integrationChangedEntity.setStrDesc(strMsg);
			integrationChangedEntity.setStrInsertTime(DateTool.DateToString(new Date(),DateStyle.YYYY_MM_DD_HH_MM_SS));
			//写入积分变更信息
			weiXinMemberCenterMapper.insertIntegrationChangedInfo(integrationChangedEntity);
		
			//修改会员信息表中的积分字段 
			memberEntity.setIntIntegral(iMemberIntegration);
			memberEntity.setStrUpdatetime(DateTool.DateToString(new Date(),DateStyle.YYYY_MM_DD_HH_MM_SS));
			weiXinMemberCenterMapper.updateMemberInfo(memberEntity);
		}
			//写入签到信息 
			weiXinMemberCenterMapper.insertMemberSignInfo(memberSignEntity);
	}
	
	//查询最近一次签到记录-单条（签到天数)
	public WeiXinMemberSignEntity selectSignDays(String strMemberId) throws Exception
	{
		return weiXinMemberCenterMapper.selectSignDays(strMemberId);
	}
	
	//按月查询指定会员的签到记录
	public List<WeiXinMemberSignEntity> selectSignDaysByMonth(Map<String,Object> queryMap) throws Exception
	{
		return weiXinMemberCenterMapper.selectSignDaysByMonth(queryMap);
	}
	
	
	//查询未使用抵用券信息 --列表
	public String selectUnUsedVoucherTicketInfo(String strMemberId) throws Exception
	{
		int iLoopTimes=0;	
		int iState=1;//抵用券未使用
		String strVoucherTickedId="";
		Map<String,Object> queryMap=new HashMap<String,Object>();
		queryMap.put("strMemberId", strMemberId);
		queryMap.put("iState", iState);
		List<VoucherticketUseInfoEntity> listVoucherticketUseInfoEntity=weiXinMemberCenterMapper.selectVoucherticketUseInfoEntity(queryMap);
		if(listVoucherticketUseInfoEntity==null)
			return DataTool.constructResponse(ResultCode.NO_DATA,"暂无数据",null);
		if(listVoucherticketUseInfoEntity.size()==0)
			return DataTool.constructResponse(ResultCode.NO_DATA,"暂无数据",null);
		//根据抵用券的ID查询抵用券详情
		iLoopTimes=listVoucherticketUseInfoEntity.size();
		List<VoucherTicketInfoEntity> listVoucherTicketInfoEntity=new ArrayList<VoucherTicketInfoEntity>();
		for(int i=0;i<iLoopTimes;i++)
		{
			strVoucherTickedId=listVoucherticketUseInfoEntity.get(i).getStrVoucherTicketId();
			VoucherTicketInfoEntity voucherTicketInfoEntity=weiXinMemberCenterMapper.selectVoucherTicketDetailInfo(strVoucherTickedId);
			if(voucherTicketInfoEntity!=null)
				listVoucherTicketInfoEntity.add(voucherTicketInfoEntity);
		}
		
		if(listVoucherTicketInfoEntity.size()==0)
			return DataTool.constructResponse(ResultCode.NO_DATA,"暂无数据",null);
		Map<String,Object> resultMap=new HashMap<String,Object>();
		resultMap.put("listVoucherTicketInfoEntity", listVoucherTicketInfoEntity);
		return DataTool.constructResponse(ResultCode.OK,"查询成功",resultMap);
	}
	
	
	//查询已使用抵用券信息 --列表
	public String selectUsedVoucherTicketInfo(Map<String,Object> queryMap) throws Exception
	{
		int iLoopTimes=0;	
		int iState=2;//抵用券已使用
		int iTotalRecord=0;
		int iTotalPage=0;
		int iPageFromIndex=0;
		int iPageEndIndex=0;
		int iListMaxIndex=0;
		int iPageSize=(Integer)queryMap.get("iPageSize");
		int iPageNum=(Integer)queryMap.get("iPageNum");
		String strVoucherTickedId="";
		String strMemberId=(String)queryMap.get("strMemberId");
		Map<String,Object> voucherMap=new HashMap<String,Object>();
		voucherMap.put("strMemberId", strMemberId);
		voucherMap.put("iState", iState);
		List<VoucherticketUseInfoEntity> listVoucherticketUseInfoEntity=weiXinMemberCenterMapper.selectVoucherticketUseInfoEntity(voucherMap);
		if(listVoucherticketUseInfoEntity==null)
			return DataTool.constructResponse(ResultCode.NO_DATA,"暂无数据",null);
	
		if(listVoucherticketUseInfoEntity.size()==0)
			return DataTool.constructResponse(ResultCode.NO_DATA,"暂无数据",null);
		iTotalRecord=listVoucherticketUseInfoEntity.size();
		iTotalPage=iTotalRecord%iPageSize==0?iTotalRecord/iPageSize:iTotalRecord/iPageSize+1;
		if(iPageNum>iTotalPage)
			iPageNum=iTotalPage;
		iPageFromIndex=(iPageNum-1)*iPageSize;

		iPageEndIndex=iPageFromIndex+iPageSize-1;
		//根据抵用券的ID查询抵用券详情
		iLoopTimes=listVoucherticketUseInfoEntity.size();
		
		List<VoucherTicketInfoEntity> listVoucherTicketInfoEntity=new ArrayList<VoucherTicketInfoEntity>();
		for(int i=0;i<iLoopTimes;i++)
		{
			strVoucherTickedId=listVoucherticketUseInfoEntity.get(i).getStrVoucherTicketId();
			VoucherTicketInfoEntity voucherTicketInfoEntity=weiXinMemberCenterMapper.selectVoucherTicketDetailInfo(strVoucherTickedId);
			if(voucherTicketInfoEntity!=null)
				listVoucherTicketInfoEntity.add(voucherTicketInfoEntity);
		}
	
		if(listVoucherTicketInfoEntity.size()==0)
			return DataTool.constructResponse(ResultCode.NO_DATA,"暂无数据",null);
		
		List<VoucherTicketInfoEntity> listVoucherEntityOrderByValidEndTime=new ArrayList<VoucherTicketInfoEntity>();
		//对listVoucherTicketInfoEntity里的对象按strValidEndTime排序
		iListMaxIndex=listVoucherTicketInfoEntity.size()-1;
		String strValidEndTimeOrderByTimeDesc="";
		int iOrderByTimeIndex=0;
		for(int i=0;i<=iListMaxIndex;i++)
		{
			iOrderByTimeIndex=0;
			if(listVoucherTicketInfoEntity.size()!=1)
			{
				for(int j=1;j<listVoucherTicketInfoEntity.size();j++)
				{
					strValidEndTimeOrderByTimeDesc=listVoucherTicketInfoEntity.get(0).getStrValidEndTime();
					
					if(strValidEndTimeOrderByTimeDesc.compareTo(listVoucherTicketInfoEntity.get(j).getStrValidEndTime())<=0)
						iOrderByTimeIndex=j;
					
				}
				
				VoucherTicketInfoEntity moveEntity=listVoucherTicketInfoEntity.get(iOrderByTimeIndex);
				listVoucherEntityOrderByValidEndTime.add(moveEntity);
				listVoucherTicketInfoEntity.remove(iOrderByTimeIndex);
			}
			else
			{
					VoucherTicketInfoEntity moveEntity=listVoucherTicketInfoEntity.get(iOrderByTimeIndex);
					listVoucherEntityOrderByValidEndTime.add(moveEntity);
					listVoucherTicketInfoEntity.remove(iOrderByTimeIndex);
			}
		}
		//从listVoucherTicketInfoOrderByValidEndTimeEntity里取出iPageFrom，iPageSize指定的数据
		List<VoucherTicketInfoEntity> returnListVouncherEntity=new ArrayList<VoucherTicketInfoEntity>();
		if(iListMaxIndex>=iPageEndIndex)
		{
			for(int i=0;i<=iListMaxIndex;i++)
			{
				if(i>=iPageFromIndex&&i<=iPageEndIndex)
					returnListVouncherEntity.add(listVoucherEntityOrderByValidEndTime.get(i));
			}
			
		}
		else
		{
			for(int i=0;i<=iListMaxIndex;i++)
			{
				if(i>=iPageFromIndex)
					returnListVouncherEntity.add(listVoucherEntityOrderByValidEndTime.get(i));
			}
		}
		
		Map<String,Object> resultMap=new HashMap<String,Object>();
		resultMap.put("returnListVouncherEntity", returnListVouncherEntity);
		resultMap.put("iTotalRecord",iTotalRecord);
		resultMap.put("iTotalPage",iTotalPage);
		return DataTool.constructResponse(ResultCode.OK,"查询成功",resultMap);
	}
	
	
	//查询已过期抵用券信息
	public String selectExpiredVoucherTicketInfo(Map<String,Object> queryMap) throws Exception
	{
		int iLoopTimes=0;	
		int iExpired=1;//抵用券已过期
		int iTotalRecord=0;
		int iTotalPage=0;
		int iPageFromIndex=0;
		int iPageEndIndex=0;
		int iListMaxIndex=0;
		int iPageSize=(Integer)queryMap.get("iPageSize");
		int iPageNum=(Integer)queryMap.get("iPageNum");
		String strVoucherTickedId="";
		String strMemberId=(String)queryMap.get("strMemberId");
		Map<String,Object> voucherMap=new HashMap<String,Object>();
		voucherMap.put("strMemberId", strMemberId);
		voucherMap.put("iExpired",iExpired);
		List<VoucherticketUseInfoEntity> listVoucherticketUseInfoEntity=weiXinMemberCenterMapper.selectExpiredVoucherTicketInfo(voucherMap);
		if(listVoucherticketUseInfoEntity==null)
			return DataTool.constructResponse(ResultCode.NO_DATA,"暂无数据",null);
	
		if(listVoucherticketUseInfoEntity.size()==0)
			return DataTool.constructResponse(ResultCode.NO_DATA,"暂无数据",null);
		iTotalRecord=listVoucherticketUseInfoEntity.size();
		iTotalPage=iTotalRecord%iPageSize==0?iTotalRecord/iPageSize:iTotalRecord/iPageSize+1;
		if(iPageNum>iTotalPage)
			iPageNum=iTotalPage;
		iPageFromIndex=(iPageNum-1)*iPageSize;

		iPageEndIndex=iPageFromIndex+iPageSize-1;
		//根据抵用券的ID查询抵用券详情
		iLoopTimes=listVoucherticketUseInfoEntity.size();
		
		List<VoucherTicketInfoEntity> listVoucherTicketInfoEntity=new ArrayList<VoucherTicketInfoEntity>();
		for(int i=0;i<iLoopTimes;i++)
		{
			strVoucherTickedId=listVoucherticketUseInfoEntity.get(i).getStrVoucherTicketId();
			VoucherTicketInfoEntity voucherTicketInfoEntity=weiXinMemberCenterMapper.selectVoucherTicketDetailInfo(strVoucherTickedId);
			if(voucherTicketInfoEntity!=null)
				listVoucherTicketInfoEntity.add(voucherTicketInfoEntity);
		}
	
		if(listVoucherTicketInfoEntity.size()==0)
			return DataTool.constructResponse(ResultCode.NO_DATA,"暂无数据",null);
		
		List<VoucherTicketInfoEntity> listVoucherEntityOrderByValidEndTime=new ArrayList<VoucherTicketInfoEntity>();
		//对listVoucherTicketInfoEntity里的对象按strValidEndTime排序
		iListMaxIndex=listVoucherTicketInfoEntity.size()-1;
		String strValidEndTimeOrderByTimeDesc="";
		int iOrderByTimeIndex=0;
		for(int i=0;i<=iListMaxIndex;i++)
		{
			iOrderByTimeIndex=0;
			if(listVoucherTicketInfoEntity.size()!=1)
			{
				for(int j=1;j<listVoucherTicketInfoEntity.size();j++)
				{
					strValidEndTimeOrderByTimeDesc=listVoucherTicketInfoEntity.get(0).getStrValidEndTime();
					
					if(strValidEndTimeOrderByTimeDesc.compareTo(listVoucherTicketInfoEntity.get(j).getStrValidEndTime())<=0)
						iOrderByTimeIndex=j;
					
				}
				
				VoucherTicketInfoEntity moveEntity=listVoucherTicketInfoEntity.get(iOrderByTimeIndex);
				listVoucherEntityOrderByValidEndTime.add(moveEntity);
				listVoucherTicketInfoEntity.remove(iOrderByTimeIndex);
			}
			else
			{
					VoucherTicketInfoEntity moveEntity=listVoucherTicketInfoEntity.get(iOrderByTimeIndex);
					listVoucherEntityOrderByValidEndTime.add(moveEntity);
					listVoucherTicketInfoEntity.remove(iOrderByTimeIndex);
			}
		}
		//从listVoucherTicketInfoOrderByValidEndTimeEntity里取出iPageFrom，iPageSize指定的数据
		List<VoucherTicketInfoEntity> returnListVouncherEntity=new ArrayList<VoucherTicketInfoEntity>();
		if(iListMaxIndex>=iPageEndIndex)
		{
			for(int i=0;i<=iListMaxIndex;i++)
			{
				if(i>=iPageFromIndex&&i<=iPageEndIndex)
					returnListVouncherEntity.add(listVoucherEntityOrderByValidEndTime.get(i));
			}
			
		}
		else
		{
			for(int i=0;i<=iListMaxIndex;i++)
			{
				if(i>=iPageFromIndex)
					returnListVouncherEntity.add(listVoucherEntityOrderByValidEndTime.get(i));
			}
		}
		Map<String,Object> resultMap=new HashMap<String,Object>();
		resultMap.put("returnListVouncherEntity", returnListVouncherEntity);
		resultMap.put("iTotalRecord",iTotalRecord);
		resultMap.put("iTotalPage",iTotalPage);
		return DataTool.constructResponse(ResultCode.OK,"查询成功",resultMap);
	}
	
	//查询会员的级别权益信息
	public List<MemberLevelsRightsMappingEntity> selectMemberLevelsRightsMappingEntityInfo(String strMemberId) throws Exception
	{
		//查会员级别
		MemberEntity memberEntity = weiXinMemberCenterMapper.selectMemberEntity(strMemberId);
		if(memberEntity==null)
			return null;
		if("".equals(memberEntity.getStrMemberid()))
			return null;
		String strLevelsId=memberEntity.getStrLevelsid();
		//查等级权益信息
		return weiXinMemberCenterMapper.selectMemberLevelsRightsMappingEntityInfo(strLevelsId);
		
	}
	
	//查询所有的级别信息
	public List<MemberlevelsEntity> selectAllLevelsInfo() throws Exception
	{
	
		return weiXinMemberCenterMapper.selectAllLevelsInfo();
	}
	
	//查询会员章程
	public List<MemberarticlesEntity> selectMemberArticlesInfo() throws Exception
	{
		return weiXinMemberCenterMapper.selectMemberArticlesInfo();
	}
	
}
