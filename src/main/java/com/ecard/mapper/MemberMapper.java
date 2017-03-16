package com.ecard.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ecard.entity.IntegralModEntity;
import com.ecard.entity.MemberEntity;
import com.ecard.entity.MemberRechargeRecord;
import com.ecard.entity.MemberdetailEntity;
import com.ecard.entity.MemberexpandattributeEntity;
import com.ecard.entity.MemberlevelsEntity;
import com.ecard.vo.MemberVO;

/**
 * 会员操作mapper
 */
public interface MemberMapper {
	
	//判断手机号是否存在
	String judgeMobileIsExist(@Param("strMemberid")String strMemberid, @Param("strMobile")String strMobile) throws Exception;
	
	//根据手机号查询会员信息
	MemberEntity getMemberEntityByMobile(String strMobile) throws Exception;

	//根据会员ID查询会员详细信息
	MemberEntity getMemberEntityById(String strMemberid) throws Exception;

	//根据会员ID查询会员详细信息
	MemberEntity getLoginUserInfoById(String strMemberid) throws Exception;
	
	//查询所有可用的会员级别
	List<MemberlevelsEntity> listAllMemberLevels(int intStatus) throws Exception;
	
	//查询会员列表
	List<MemberVO> listMember(Map<String, Object> queryMap) throws Exception;

	//查询会员总数量
	public int getMemberTotalCount(Map<String, Object> queryMap) throws Exception;

	//新增会员
	void insertMember(MemberEntity memberEntity) throws Exception;

	//新增会员详细信息
	void insertMemberDetail(MemberdetailEntity memberdetailEntity) throws Exception;

	//批量插入会员拓展属性
	void batchInsertMemberexpandattribute(@Param("attributeList")List<MemberexpandattributeEntity> attributeList) throws Exception;
	
	//查询会员基本信息
	MemberEntity getMemberById(String strMemberid)  throws Exception;
	
	//查询会员详细信息
	public MemberdetailEntity getMemberdetailById(String strMemberid) throws Exception;
	
	//查询会员拓展资料信息List
	List<MemberexpandattributeEntity> listMemberExpandInfoById(String strMemberid) throws Exception;

	//修改会员基本信息
	void updateMember(MemberEntity memberEntity) throws Exception;

	//修改会员详细信息
	void updateMemberDetail(MemberdetailEntity memberdetailEntity) throws Exception;

	//删除会员拓展资料信息
	void deleteMemberexpandattribute(String strMemberid) throws Exception;
	
	//会员充值  added by qidongbo 20170315 10:40
	void insertMemberRechargeRecord(MemberRechargeRecord tMemberRechargeRecord) throws Exception;
	
	//修改会员积分  added by qidongbo 20170315 12:40
	int  updateMemberIntegral(IntegralModEntity integralModEntity) throws Exception;
	
	//修改会员售后充值金额（后台人员操作）  added by qidongbo 20170315 12:40
	void  updateMemberBgrechargeById(MemberRechargeRecord tMemberRechargeRecord) throws Exception;

	//修改会员状态
	void updateMemberStatus(@Param("strMemberId")String strMemberId, @Param("intStatus")int intStatus) throws Exception;

	//修改会员现金充值金额
	void updateMemberCashAmountById(MemberRechargeRecord memberRechargeRecord) throws Exception;
}
