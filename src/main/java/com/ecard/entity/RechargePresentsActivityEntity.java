package com.ecard.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RechargePresentsActivityEntity {
		private String strActivityId;	//活动ID
		private String strActivityName;	//活动名称
		private String strLevelsId;	//会员级别ID
		private String strActivityBeginTime;	//活动开始时间
		private String strActivityEndTime;	//活动强束时间
		private int iActivityKinds;	//活动类别：:0自定义赠送型，1消费赠送型，2充值 赠送型
		private String strEmployeeId;				//管理员ID
		private String strEmployeeName;				//管理员账号
		private String strEmployeeRealName;			//管理员姓名
		private String strCreationTime;				//创建记录时间
		private String strLastAccessedTime;			//修改时间
		
		//-------------------------------
		private String strMemberLevelName;
		private List<RechargePresentsIntegrationEntity> listRechargePresentsIntegrationEntity;
		private List<RechargePresentsVoucherEntity> listRechargePresentsVoucherEntity;
		private List<RechargePresentsStoredValueEntity> listRechargePresentsStoredValueEntity;
		
		public void setStrMemberLevelName(String strMemberLevelName)
		{
			this.strMemberLevelName=strMemberLevelName;
		}
		
		public String getStrMemberLevelName()
		{
			return strMemberLevelName;
		}
		
		public void setListRechargePresentsIntegrationEntity(List<RechargePresentsIntegrationEntity> listRechargePresentsIntegrationEntity)
		{
			this.listRechargePresentsIntegrationEntity=listRechargePresentsIntegrationEntity;
		}
		
		public List<RechargePresentsIntegrationEntity> getListRechargePresentsIntegrationEntity()
		{
			return listRechargePresentsIntegrationEntity;
		}
		
		public void setListRechargePresentsVoucherEntity(List<RechargePresentsVoucherEntity> listRechargePresentsVoucherEntity)
		{
			this.listRechargePresentsVoucherEntity=listRechargePresentsVoucherEntity;
		}
		
		public List<RechargePresentsVoucherEntity> getListRechargePresentsVoucherEntity()
		{
			return listRechargePresentsVoucherEntity;
		}
		
		public void setListRechargePresentsStoredValueEntity(List<RechargePresentsStoredValueEntity> listRechargePresentsStoredValueEntity)
		{
			this.listRechargePresentsStoredValueEntity=listRechargePresentsStoredValueEntity;
		}
		
		public List<RechargePresentsStoredValueEntity> getListRechargePresentsStoredValueEntity()
		{
			return listRechargePresentsStoredValueEntity;
		}
		
		public void setStrActivityId(String strActivityId)
		{
			this.strActivityId=strActivityId;
		}
		
		public String getStrActivityId()
		{
			return strActivityId;
		}
		
		public void setStrActivityName(String strActivityName)
		{
			this.strActivityName=strActivityName;
		}
		
		public String getStrActivityName()
		{
			return strActivityName;
		}
		
		public void setStrLevelsId(String strLevelsId)
		{
			this.strLevelsId=strLevelsId;
		}
		
		public String getStrLevelsId()
		{
			return strLevelsId;
		}
		
		public void setStrActivityBeginTime(String strActivityBeginTime)
		{
			this.strActivityBeginTime=strActivityBeginTime;
		}
		
		public String getStrActivityBeginTime()
		{
			return strActivityBeginTime;
		}
		
		public void setStrActivityEndTime(String strActivityEndTime)
		{
			this.strActivityEndTime=strActivityEndTime;
		}
		
		public String getStrActivityEndTime()
		{
			return strActivityEndTime;
		}
		
		public void setiActivityKinds(int iActivityKinds)
		{
			this.iActivityKinds=iActivityKinds;
		}
		
		public int getiActivityKinds()
		{
			return iActivityKinds;
		}
		
		public void setStrEmployeeId(String strEmployeeId)
		{
			this.strEmployeeId=strEmployeeId;
		}
		
		public String getStrEmployeeId()
		{
			return strEmployeeId;
		}
		
		public void setStrEmployeeName(String strEmployeeName)
		{
			this.strEmployeeName=strEmployeeName;
		}
		
		public String getStrEmployeeName()
		{
			return strEmployeeName;
		}
		
		public void setStrEmployeeRealName(String strEmployeeRealName)
		{
			this.strEmployeeRealName=strEmployeeRealName;
		}
		
		public String getStrEmployeeRealName()
		{
			return strEmployeeRealName;
		}
		
		public void setStrCreationTime(String strCreationTime)
		{
			this.strCreationTime=strCreationTime;
		}
		
		public String getStrCreationTime()
		{
			return strCreationTime;
		}
		
		public void setStrLastAccessedTime(String strLastAccessedTime)
		{
			this.strLastAccessedTime=strLastAccessedTime;
		}
		
		public String getStrLastAccessedTime()
		{
			return strLastAccessedTime;
		}
		
		
		
}



