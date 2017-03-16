package com.ecard.service;


import java.math.BigDecimal;
import java.net.InetAddress;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.commontools.data.DataTool;
import com.commontools.date.DateStyle;
import com.commontools.date.DateTool;
import com.commontools.validate.ValidateTool;
import com.ecard.alipay.tool.AlipayConfig;
import com.ecard.alipay.tool.AlipayTool;
import com.ecard.config.ResultCode;
import com.ecard.entity.EmployeeEntity;
import com.ecard.entity.MemberEntity;
import com.ecard.entity.MemberRechargeRecord;
import com.ecard.entity.MemberdetailEntity;
import com.ecard.entity.MemberexpandattributeEntity;
import com.ecard.entity.MemberexpandinformationEntity;
import com.ecard.entity.MemberlevelsEntity;
import com.ecard.entity.RechargeOrderEntity;
import com.ecard.enumeration.MemberexpandinformationMustEnum;
import com.ecard.mapper.MemberMapper;
import com.ecard.mapper.MemberexpandinformationMapper;
import com.ecard.mapper.RechargeOrderMapper;
import com.ecard.util.QRCodeTool;
import com.ecard.vo.MemberVO;
import com.ecard.vo.MemberexpandinformationVO;
import com.ecard.wechant.tool.WechantConfig;
import com.ecard.wechant.tool.WechantTool;

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
	@Autowired
	private RechargeOrderMapper rechargeOrderMapper;
	
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
		
		// 用户售后储值余额增加
		memberMapper.updateMemberBgrechargeById(tMemberRechargeRecord);
	
		// 增加充值记录
		memberMapper.insertMemberRechargeRecord(tMemberRechargeRecord);
		
		return DataTool.constructResponse(ResultCode.OK, "储值充值成功", null);
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

	//现金充值
	@Transactional(rollbackFor=Exception.class)
	public String cashMoneyRechargForMember(EmployeeEntity employeeEntity, String strMemberId, 
												BigDecimal bAmount, String strPayType) throws Exception {
		MemberEntity memberEntity = memberMapper.getMemberEntityById(strMemberId);
		if (memberEntity==null) {
			return DataTool.constructResponse(ResultCode.NO_DATA, "会员不存在", null);
		}
		//设置充值订单公共参数
		RechargeOrderEntity rechargeOrderEntity = new RechargeOrderEntity();
		rechargeOrderEntity.setStrOrderId(DataTool.getUUID());
		rechargeOrderEntity.setStrMemberId(strMemberId);
		rechargeOrderEntity.setStrMemberCardNum(memberEntity.getStrMembercardnum());
		rechargeOrderEntity.setStrMemberName(memberEntity.getStrRealname());
		rechargeOrderEntity.setStrDesc("现金充值");
		rechargeOrderEntity.setdBalance(bAmount);
		rechargeOrderEntity.setStrEmployeeId(employeeEntity.getStrEmployeeid());
		rechargeOrderEntity.setStrEmployeeRealName(employeeEntity.getStrRealname());
		rechargeOrderEntity.setStrEmployeeLoginName(employeeEntity.getStrLoginname());
		rechargeOrderEntity.setStrInsertTime(DateTool.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
		
		Map<String,Object> returnMap = new HashMap<String,Object>(); //返回参数
		returnMap.put("strOrderId", rechargeOrderEntity.getStrOrderId()); //返回订单号
		//1.删除所有未支付过的订单
		if ("0".equals(strPayType)) {
			//现金充值
			// 1.录入充值记录
			MemberRechargeRecord tMemberRechargeRecord=new MemberRechargeRecord();
			tMemberRechargeRecord.setStrRechargeId(DataTool.getUUID());
			tMemberRechargeRecord.setStrMemberId(strMemberId);
			tMemberRechargeRecord.setdBalance(bAmount);
			tMemberRechargeRecord.setStrEmployeeId(employeeEntity.getStrEmployeeid());
			tMemberRechargeRecord.setStrEmployeeRealName(employeeEntity.getStrRealname());
			tMemberRechargeRecord.setStrEmployeeLoginName(employeeEntity.getStrLoginname());
			tMemberRechargeRecord.setStrInsertTime(DateTool.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
			tMemberRechargeRecord.setiRechargeType(0); //表示是现金充值
			tMemberRechargeRecord.setStrReserved("");
			tMemberRechargeRecord.setStrMemberCardNum(memberEntity.getStrMembercardnum());
			tMemberRechargeRecord.setStrMemberName(memberEntity.getStrRealname());
			memberMapper.insertMemberRechargeRecord(tMemberRechargeRecord);
			
			// 设置充值订单私有参数
			rechargeOrderEntity.setIntPayType(0); //现金支付
			rechargeOrderEntity.setIntStatus(1);
			rechargeOrderEntity.setStrPayTime(rechargeOrderEntity.getStrInsertTime());
			
			// 3.用户现金储值余额增加
			memberMapper.updateMemberCashAmountById(tMemberRechargeRecord);
		} else if ("1".equals(strPayType)) {
			//微信支付
			//设置充值订单私有参数
			rechargeOrderEntity.setIntPayType(1); //微信支付
			rechargeOrderEntity.setIntStatus(0); //待支付
			rechargeOrderEntity.setStrPayTime("");
			
			//生成微信预支付订单
			String spbill_create_ip = InetAddress.getLocalHost().getHostAddress();
			int iAmount = bAmount.multiply(new BigDecimal(100)).intValue(); //订单总金额，单位为分
			Map<String,Object> wechant_resultmap = WechantTool.tradePrecreate(rechargeOrderEntity.getStrOrderId(), rechargeOrderEntity.getStrDesc(), spbill_create_ip, 
			                                     iAmount, WechantConfig.RECHARGE_CALLBACK_URL,"NATIVE"); //扫码支付
			String return_code  = (String)wechant_resultmap.get("return_code");
			String result_code = (String)wechant_resultmap.get("result_code");
			if("SUCCESS".equals(return_code)&&"SUCCESS".equals(result_code)) {
				//生成预支付订单成功
				//取得二维码
				String code_url = (String)wechant_resultmap.get("code_url");
				//生成二维码，变成base64的字节码，返回给前端
				String code_url_base64 = QRCodeTool.getQRCode(code_url);
				returnMap.put("qrCode", "data:image/png;base64," + code_url_base64);
			} else {
				//生成预支付订单失败
				String err_code = (String)wechant_resultmap.get("err_code");
				if("OUT_TRADE_NO_USED".equals(err_code)) {
					//商户订单号重复
					return DataTool.constructResponse(ResultCode.PAY_ERROR,"商户订单号重复", null);
				} else if("LACK_PARAMS".equals(err_code)) {
					//缺少参数
					return DataTool.constructResponse(ResultCode.PAY_ERROR,"缺少参数", null);
				} else if("SIGNERROR".equals(err_code)) {
					//签名错误
					return DataTool.constructResponse(ResultCode.PAY_ERROR,"签名错误", null);
				} else if("ORDERPAID".equals(err_code)) {
					//订单已经支付完成
					return DataTool.constructResponse(ResultCode.PAY_ERROR,"订单已经支付完成", null);
				} else if("SYSTEMERROR".equals(err_code)) {
					//系统错误，系统超时
					return DataTool.constructResponse(ResultCode.PAY_ERROR,"微信系统错误", null);
				} else {
					return DataTool.constructResponse(ResultCode.PAY_ERROR,"预支付订单生成失败", null);
				}
			}
		} else if ("2".equals(strPayType)) {
			//支付宝支付
			//设置充值订单私有参数
			rechargeOrderEntity.setIntPayType(2); //支付宝支付
			rechargeOrderEntity.setIntStatus(0); //待支付
			rechargeOrderEntity.setStrPayTime("");
			
			//生成支付宝预支付订单
			String strAmount = bAmount.toString();
			String sHtmlText = AlipayTool.tradePrecreate(rechargeOrderEntity.getStrOrderId(), rechargeOrderEntity.getStrDesc(), strAmount, "", 
					                                         AlipayConfig.recharge_callback_url,
					                                         "", ""); //根据参数生成签名，并生成表单
			returnMap.put("sHtmlText", sHtmlText);
			
		} else {
			return DataTool.constructResponse(ResultCode.UNKNOW_ERROR, "位置操作类型", null);
		}
		rechargeOrderMapper.insertRechargeOrder(rechargeOrderEntity);
		returnMap.put("strPayType", strPayType);
		return DataTool.constructResponse(ResultCode.OK, "充值下单成功", returnMap);
		
	}

}

