package com.ecard.service;


import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.commontools.data.DataTool;
import com.commontools.validate.ValidateTool;
import com.ecard.config.ResultCode;
import com.ecard.entity.MemberEntity;
import com.ecard.entity.MemberRechargeRecord;
import com.ecard.entity.MemberdetailEntity;
import com.ecard.entity.MemberexpandattributeEntity;
import com.ecard.entity.MemberexpandinformationEntity;
import com.ecard.entity.MemberlevelsEntity;
import com.ecard.enumeration.MemberexpandinformationMustEnum;
import com.ecard.mapper.MemberMapper;
import com.ecard.mapper.MemberexpandinformationMapper;
import com.ecard.vo.MemberVO;
import com.ecard.vo.MemberexpandinformationVO;

/**
 * 会员操作service
 */
@Service
@Transactional
public class MemberService {
	
	@Autowired
	private MemberMapper memberMapper;
	@Autowired
	private MemberexpandinformationMapper memberexpandinformationMapper;
	
	//判断用户手机号是否存在
	public String judgeMobileIsExist(String strMemberid, String strMobile) throws Exception {
		return memberMapper.judgeMobileIsExist(strMemberid, strMobile);
	}

	//根据手机号查询会员信息
	public MemberEntity getMemberEntityByMobile(String strMobile) throws Exception {
		return memberMapper.getMemberEntityByMobile(strMobile);
	}

	//根据会员ID查询会员详细信息
	public MemberEntity getMemberEntityById(String strMemberid) throws Exception {
		return memberMapper.getMemberEntityById(strMemberid);
	}

	//查询会员ID查询会员登录信息
	public MemberEntity getLoginUserInfoById(String strMemberid) throws Exception {
		return memberMapper.getLoginUserInfoById(strMemberid);
	}
	
	//查询所有可用的会员级别
	public List<MemberlevelsEntity> listAllMemberLevels(int intStatus) throws Exception {
		return memberMapper.listAllMemberLevels(intStatus);
	}

	//查询会员列表
	public List<MemberVO> listMember(Map<String, Object> queryMap) throws Exception {
		return memberMapper.listMember(queryMap);
	}

	//查询会员总数量
	public int getMemberTotalCount(Map<String, Object> queryMap) throws Exception {
		return memberMapper.getMemberTotalCount(queryMap);
	}

	//新增会员
	@Transactional(rollbackFor=Exception.class)
	public void insertMember(MemberEntity memberEntity, MemberdetailEntity memberdetailEntity,
			List<MemberexpandattributeEntity> attributeList) throws Exception {
		//1.新增会员
		memberMapper.insertMember(memberEntity);
		//2.新增会员详细信息
		memberMapper.insertMemberDetail(memberdetailEntity);
		if(attributeList!=null&&attributeList.size()>0) {
			//3.新增会员详细信息对应的拓展资料
			memberMapper.batchInsertMemberexpandattribute(attributeList);
		}
	}

	//查询会员基本信息
	public MemberEntity getMemberById(String strMemberid)  throws Exception {
		return memberMapper.getMemberById(strMemberid);
	}

	//查询会员详细信息
	public MemberdetailEntity getMemberdetailById(String strMemberid) throws Exception {
		return memberMapper.getMemberdetailById(strMemberid);
	}
	
	//查询会员拓展资料信息List
	List<MemberexpandattributeEntity> listMemberExpandInfoById(String strMemberid) throws Exception {
		return memberMapper.listMemberExpandInfoById(strMemberid);
	}
	
	//修改会员
	@Transactional(rollbackFor=Exception.class)
	public void updateMember(MemberEntity memberEntity, MemberdetailEntity memberdetailEntity,
			List<MemberexpandattributeEntity> attributeList) throws Exception {
		//1.修改会员
		memberMapper.updateMember(memberEntity);
		//2.修改会员详细信息
		memberMapper.updateMemberDetail(memberdetailEntity);
		//3.修改会员详细信息对应的拓展资料
		memberMapper.deleteMemberexpandattribute(memberEntity.getStrMemberid());
		if(attributeList!=null&&attributeList.size()>0) {
			//4.录入会员详细信息对应的拓展资料
			memberMapper.batchInsertMemberexpandattribute(attributeList);
		}
	}

	//查询拓展资料信息
	public List<MemberexpandinformationVO> listAllExpandInfo(String strMemberid) throws Exception {
		List<MemberexpandinformationEntity> memberexpandinfoList = memberexpandinformationMapper.listMemberexpandinformation(null);
		List<MemberexpandinformationVO> memberexpandinformationVOList = new LinkedList<MemberexpandinformationVO>();
		if(memberexpandinfoList!=null&&memberexpandinfoList.size()>0) {
			MemberexpandinformationEntity memberexpandinformationEntity = null;
			if(ValidateTool.isEmptyStr(strMemberid)) {
				//会员ID为空
				for(int i=0;i<memberexpandinfoList.size();i++) {
					memberexpandinformationEntity = memberexpandinfoList.get(i);
					MemberexpandinformationVO memberexpandinformationVO = new MemberexpandinformationVO();
					memberexpandinformationVO.setStrInformationid(memberexpandinformationEntity.getStrInformationid());
					memberexpandinformationVO.setStrInformationname(memberexpandinformationEntity.getStrInformationname());
					memberexpandinformationVO.setStrOptions(buildOptionsArr(memberexpandinformationEntity.getStrOptions()));
					memberexpandinformationVO.setIntIsmust(memberexpandinformationEntity.getIntIsmust());
					memberexpandinformationVO.setIntType(memberexpandinformationEntity.getIntType());
					if(memberexpandinformationEntity.getIntIsmust()==MemberexpandinformationMustEnum.MUST_TRUE.getValue()&&memberexpandinformationEntity.getIntType()==1) {
						memberexpandinformationVO.setStrValue(buildOptionsArr(memberexpandinformationEntity.getStrOptions())[0]);
					} else {
						memberexpandinformationVO.setStrValue("");
					}
					
					memberexpandinformationVOList.add(memberexpandinformationVO);
				}
			} else {
				//会员ID不为空
				List<MemberexpandattributeEntity> memberexpandattributeList = memberMapper.listMemberExpandInfoById(strMemberid);
				for(int i=0;i<memberexpandinfoList.size();i++) {
					memberexpandinformationEntity = memberexpandinfoList.get(i);
					MemberexpandinformationVO memberexpandinformationVO = new MemberexpandinformationVO();
					memberexpandinformationVO.setStrInformationid(memberexpandinformationEntity.getStrInformationid());
					memberexpandinformationVO.setStrInformationname(memberexpandinformationEntity.getStrInformationname());
					memberexpandinformationVO.setStrOptions(buildOptionsArr(memberexpandinformationEntity.getStrOptions()));
					memberexpandinformationVO.setIntIsmust(memberexpandinformationEntity.getIntIsmust());
					memberexpandinformationVO.setIntType(memberexpandinformationEntity.getIntType());
					memberexpandinformationVO.setStrValue(findExpandinfo(memberexpandattributeList, memberexpandinformationEntity.getStrInformationid()));
					memberexpandinformationVOList.add(memberexpandinformationVO);
				}
			}
			
		}
		return memberexpandinformationVOList;
	}
	
	//根据用户保存的拓展资料信息和拓展资料ID查询用户对应每一项拓展资料保存的值
	private String findExpandinfo(List<MemberexpandattributeEntity> memberexpandattributeList, String strInformationid) {
		String value = "";
		if(memberexpandattributeList!=null&&memberexpandattributeList.size()>0) {
			for(int i=0;i<memberexpandattributeList.size();i++) {
				if(strInformationid.equals(memberexpandattributeList.get(i).getStrInformationid())) {
					value = memberexpandattributeList.get(i).getStrAttributevalue();
					break;
				}
			}
			
		}
		return value;
	}
	
	//构建选项数组
	private String[] buildOptionsArr(String strOptions) {
		String [] options = {};
		if(!ValidateTool.isEmptyStr(strOptions)) {
			options = strOptions.split(",");
		}
		return options;
	}
	
	
	// 后台管理人员为会员充值
	@Transactional(rollbackFor=Exception.class)
	public String backGroundRechargForMember(MemberRechargeRecord tMemberRechargeRecord) throws Exception
	{
		
		// 查询会员用户信息
		MemberEntity memberEntity = memberMapper.getMemberEntityById(tMemberRechargeRecord.getStrMemberId());
		if (memberEntity==null) {
			return DataTool.constructResponse(ResultCode.NO_DATA, "会员不存在", null);
		}
		tMemberRechargeRecord.setStrMemberCardNum(memberEntity.getStrMembercardnum());
		tMemberRechargeRecord.setStrMemberName(memberEntity.getStrRealname());
		
		// 用户余额增加
		memberMapper.updateMemberBgrechargeById(tMemberRechargeRecord);
	
		// 增加充值记录
		memberMapper.insertMemberRechargeRecord(tMemberRechargeRecord);
		
		return DataTool.constructResponse(ResultCode.NO_DATA, "储值充值成功", null);
	}

	//禁用或者启用会员
	public String forbiddenMember(String strMemberId, String strOperateType) throws Exception {
		if ("1".equals(strOperateType)) {
			//禁用会员
			memberMapper.updateMemberStatus(strMemberId, 0);
			return DataTool.constructResponse(ResultCode.OK, "操作成功", 0);
			
		} else if ("0".equals(strOperateType)) {
			//启用会员
			memberMapper.updateMemberStatus(strMemberId, 1);
			return DataTool.constructResponse(ResultCode.OK, "操作成功", 1);
		} else {
			return DataTool.constructResponse(ResultCode.UNKNOW_ERROR, "未知操作类型", null);
		}
		
	}

}

