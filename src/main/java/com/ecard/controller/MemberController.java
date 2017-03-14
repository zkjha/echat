package com.ecard.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.commontools.data.DataTool;
import com.commontools.date.DateStyle;
import com.commontools.date.DateTool;
import com.commontools.validate.ValidateTool;
import com.ecard.config.ResultCode;
import com.ecard.config.StaticValue;
import com.ecard.entity.MemberEntity;
import com.ecard.entity.MemberdetailEntity;
import com.ecard.entity.MemberexpandattributeEntity;
import com.ecard.entity.MemberexpandinformationEntity;
import com.ecard.entity.MemberlevelsEntity;
import com.ecard.enumeration.MemberexpandinformationMustEnum;
import com.ecard.enumeration.MemberlevelsStatusEnum;
import com.ecard.service.MemberService;
import com.ecard.service.MemberexpandinformationService;
import com.ecard.util.WebSessionUtil;
import com.ecard.vo.MemberVO;
import com.ecard.vo.MemberexpandinformationVO;
/**
 * 会员级别控制层
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/admin/biz/member")
public class MemberController {
	
	@Resource
	private MemberService memberService;
	@Resource
	private MemberexpandinformationService memberexpandinformationService;
	@Resource
	private WebSessionUtil webSessionUtil;
	
	
	/**
	 * 新增会员
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("insertMember")
	public String insertMember(HttpServletRequest request, HttpServletResponse response) {
		//会员基本信息开始
		String strRealname = request.getParameter("strRealname");
		String strIdcard = request.getParameter("strIdcard");
		String strMobile = request.getParameter("strMobile");
		String strAge = request.getParameter("intAge");
		//String strMembercardnum = request.getParameter("strMembercardnum");
		int intSex = Integer.valueOf(request.getParameter("intSex"));
		String strLevelsid = request.getParameter("strLevelsid");
		int intStatus = Integer.valueOf(request.getParameter("intStatus"));
		if(ValidateTool.isEmptyStr(strRealname)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "登录名称不能为空", null);
		}
		if(ValidateTool.isEmptyStr(strIdcard)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "登录密码不能为空", null);
		}
		if(ValidateTool.isEmptyStr(strMobile)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "职务ID不能为空", null);
		}
		if(!ValidateTool.isMobile(strMobile)) {
			return DataTool.constructResponse(ResultCode.MOBILE_FORMAT_ERROR, "电话格式错误", null);
		}
		if(ValidateTool.isEmptyStr(strAge)) {
			strAge = "0";
		}
		/*if(ValidateTool.isEmptyStr(strMembercardnum)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "会员卡号不能为空", null);
		}*/
		if(ValidateTool.isEmptyStr(strLevelsid)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "会员级别不能为空", null);
		}
		//会员基本信息结束
		//会员详细信息开始
		String strModelofcar = request.getParameter("strModelofcar");
		String strCarlicense = request.getParameter("strCarlicense");
		String strCarcolor = request.getParameter("strCarcolor");
		String strCartype = request.getParameter("strCartype");
		String dBuyprice = request.getParameter("dBuyprice");
		String strBuydate = request.getParameter("strBuydate");
		String strAddress = request.getParameter("strAddress");
		String strUsenature = request.getParameter("strUsenature");
		String strRegisterdate = request.getParameter("strRegisterdate");
		String strCaridentifycode = request.getParameter("strCaridentifycode");
		String strDateofissue = request.getParameter("strDateofissue");
		String strEnginenumber = request.getParameter("strEnginenumber");
		String strPostprovince = request.getParameter("strPostprovince");
		String strPostcity = request.getParameter("strPostcity");
		String strPostarea = request.getParameter("strPostarea");
		String strPoststreet = request.getParameter("strPoststreet");
		String strPostdetailaddress = request.getParameter("strPostdetailaddress");
		String strPostcode = request.getParameter("strPostcode");
		if(ValidateTool.isEmptyStr(strModelofcar)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, " 车辆型号不能为空", null);
		}
		if(ValidateTool.isEmptyStr(strCarlicense)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "车牌号码不能为空", null);
		}
		if(ValidateTool.isEmptyStr(strCartype)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "车辆类型不能为空", null);
		}
		if(ValidateTool.isEmptyStr(strAddress)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "住址不能为空", null);
		}
		if(ValidateTool.isEmptyStr(strUsenature)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "使用性质不能为空", null);
		}
		if(ValidateTool.isEmptyStr(strCaridentifycode)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "车辆识别代码不能为空", null);
		}
		if(ValidateTool.isEmptyStr(strEnginenumber)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "发动机号码不能为空", null);
		}
		
		//会员详细信息结束
		try {
			String strMemberid = DataTool.getUUID();
			String strInserttime = DateTool.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS);
			List<MemberexpandinformationEntity> memberexpandinfoList = memberexpandinformationService.listMemberexpandinformation(null);
			List<MemberexpandattributeEntity> attributeList = new LinkedList<MemberexpandattributeEntity>();
			if(memberexpandinfoList!=null&&memberexpandinfoList.size()>0) {
				MemberexpandinformationEntity memberexpandinformationEntity = null;
				for(int i=0;i<memberexpandinfoList.size();i++) {
					memberexpandinformationEntity = memberexpandinfoList.get(i);
					String params = request.getParameter(memberexpandinformationEntity.getStrInformationid());
					int intIsmust = memberexpandinformationEntity.getIntIsmust(); //是否必填
					if(intIsmust==MemberexpandinformationMustEnum.MUST_TRUE.getValue()) {
						//必填
						if(ValidateTool.isEmptyStr(params)) {
							return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, memberexpandinformationEntity.getStrInformationname() + "不能为空", null);
						}
					}
					if(!ValidateTool.isEmptyStr(params)) {
						MemberexpandattributeEntity memberexpandattributeEntity = new MemberexpandattributeEntity();
						memberexpandattributeEntity.setStrAttributeid(DataTool.getUUID());
						memberexpandattributeEntity.setStrMemberid(strMemberid);
						memberexpandattributeEntity.setStrInformationid(memberexpandinformationEntity.getStrInformationid());
						memberexpandattributeEntity.setStrAttributevalue(params.trim());
						memberexpandattributeEntity.setStrInserttime(strInserttime);
						attributeList.add(memberexpandattributeEntity);
					}
				}
			}
			
			String flag = memberService.judgeMobileIsExist("", strMobile.trim());
			if("false".equals(flag)) {
				//会员信息
				MemberEntity memberEntity = new MemberEntity();
				memberEntity.setStrMemberid(strMemberid);
				memberEntity.setStrRealname(strRealname.trim());
				memberEntity.setStrIdcard(strIdcard.trim());
				memberEntity.setStrMobile(strMobile.trim());
				memberEntity.setIntAge(Integer.valueOf(strAge));
				memberEntity.setStrMembercardnum(DataTool.generatRandomDigit(8));
				memberEntity.setIntSex(intSex);
				memberEntity.setStrLevelsid(strLevelsid.trim());
				memberEntity.setIntStatus(intStatus);
				memberEntity.setStrInserttime(strInserttime);
				
				//会员详细资料
				MemberdetailEntity memberdetailEntity = new MemberdetailEntity();
				memberdetailEntity.setStrMemberdetailid(DataTool.getUUID());
				memberdetailEntity.setStrMemberid(strMemberid);
				memberdetailEntity.setStrModelofcar(strModelofcar.trim());
				memberdetailEntity.setStrCarlicense(strCarlicense.trim());
				memberdetailEntity.setStrCarcolor(DataTool.trimStr(strCarcolor));
				memberdetailEntity.setStrCartype(strCartype.trim());
				memberdetailEntity.setdBuyprice(new BigDecimal(DataTool.trimStr(dBuyprice)));
				memberdetailEntity.setStrBuydate(DataTool.trimStr(strBuydate));
				memberdetailEntity.setStrAddress(strAddress.trim());
				memberdetailEntity.setStrUsenature(strUsenature.trim());
				memberdetailEntity.setStrRegisterdate(DataTool.trimStr(strRegisterdate));
				memberdetailEntity.setStrCaridentifycode(strCaridentifycode.trim());
				memberdetailEntity.setStrDateofissue(DataTool.trimStr(strDateofissue));
				memberdetailEntity.setStrEnginenumber(strEnginenumber.trim());
				memberdetailEntity.setStrPostprovince(DataTool.trimStr(strPostprovince));
				memberdetailEntity.setStrPostcity(DataTool.trimStr(strPostcity));
				memberdetailEntity.setStrPostarea(DataTool.trimStr(strPostarea));
				memberdetailEntity.setStrPoststreet(DataTool.trimStr(strPoststreet));
				memberdetailEntity.setStrPostdetailaddress(DataTool.trimStr(strPostdetailaddress));
				memberdetailEntity.setStrPostcode(DataTool.trimStr(strPostcode));
				memberdetailEntity.setStrInserttime(strInserttime);
				
				
				memberService.insertMember(memberEntity, memberdetailEntity, attributeList);
				return DataTool.constructResponse(ResultCode.OK, "新增成功", null);
			} else {
				//该手机号已经存在
				return DataTool.constructResponse(ResultCode.MOBILE_IS_MEMBER, "该手机号已经是会员了", null);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
		}
	}
	
	

	/**
	 * 根据会员ID查询会员信息（如果ID不传就返回新增需要初始化的信息，例如会员等级，会员拓展资料）
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getMemberById")
	public String getMemberById(HttpServletRequest request, HttpServletResponse response) {
		String strMemberid = request.getParameter("strMemberid");
		try {
			
			Map<String,Object> resultMap = new HashMap<String, Object>();
			if(!ValidateTool.isEmptyStr(strMemberid)) {
				MemberEntity memberEntity = memberService.getMemberById(strMemberid);
				if(ValidateTool.isNull(memberEntity)) {
					return DataTool.constructResponse(ResultCode.NO_DATA, "会员不存在", null);
				}
				MemberdetailEntity memberdetailEntity = memberService.getMemberdetailById(strMemberid);
				if(ValidateTool.isNull(memberdetailEntity)) {
					return DataTool.constructResponse(ResultCode.NO_DATA, "会员不存在", null);
				}
				resultMap.put("memberEntity", memberEntity);
				resultMap.put("memberdetailEntity", memberdetailEntity);
			} else {
				resultMap.put("memberEntity", new Object());
				resultMap.put("memberdetailEntity", new Object());
			}
			List<MemberlevelsEntity> memberlevelsEntityList = memberService.listAllMemberLevels(MemberlevelsStatusEnum.ACTIVATE.getValue()); //查询所有可用的会员级别
			if(memberlevelsEntityList==null||memberlevelsEntityList.size()<=0) {
				return DataTool.constructResponse(ResultCode.NO_DATA, "暂无会员级别", null);
			}
			resultMap.put("memberlevelsEntityList", memberlevelsEntityList);
			
			List<MemberexpandinformationVO> memberexpandinformationVOList = memberService.listAllExpandInfo(strMemberid); //查询并组装会员拓展资料信息
			if(memberexpandinformationVOList==null||memberexpandinformationVOList.size()<=0) {
				return DataTool.constructResponse(ResultCode.NO_DATA, "暂无会员拓展资料", null);
			}
			resultMap.put("memberexpandinformationVOList", memberexpandinformationVOList);
			return DataTool.constructResponse(ResultCode.OK, "查询成功", resultMap);
			
		} catch (Exception e) {
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
		}
	}
	
	
	/**
	 * 修改会员
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("updateMember")
	public String updateMember(HttpServletRequest request, HttpServletResponse response) {
		//会员基本信息开始
		String strMemberid = request.getParameter("strMemberid");
		String strRealname = request.getParameter("strRealname");
		String strIdcard = request.getParameter("strIdcard");
		//String strMobile = request.getParameter("strMobile");
		String strAge = request.getParameter("intAge");
		//String strMembercardnum = request.getParameter("strMembercardnum");
		int intSex = Integer.valueOf(request.getParameter("intSex"));
		String strLevelsid = request.getParameter("strLevelsid");
		int intStatus = Integer.valueOf(request.getParameter("intStatus"));
		if(ValidateTool.isEmptyStr(strMemberid)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "会员ID不能为空", null);
		}
		if(ValidateTool.isEmptyStr(strRealname)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "登录名称不能为空", null);
		}
		if(ValidateTool.isEmptyStr(strIdcard)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "登录密码不能为空", null);
		}
		/*if(ValidateTool.isEmptyStr(strMobile)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "职务ID不能为空", null);
		}
		if(!ValidateTool.isMobile(strMobile)) {
			return DataTool.constructResponse(ResultCode.MOBILE_FORMAT_ERROR, "电话格式错误", null);
		}*/
		if(ValidateTool.isEmptyStr(strAge)) {
			strAge = "0";
		}
		/*if(ValidateTool.isEmptyStr(strMembercardnum)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "会员卡号不能为空", null);
		}*/
		if(ValidateTool.isEmptyStr(strLevelsid)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "会员级别不能为空", null);
		}
		//会员基本信息结束
		//会员详细信息开始
		String strModelofcar = request.getParameter("strModelofcar");
		String strCarlicense = request.getParameter("strCarlicense");
		String strCarcolor = request.getParameter("strCarcolor");
		String strCartype = request.getParameter("strCartype");
		String dBuyprice = request.getParameter("dBuyprice");
		String strBuydate = request.getParameter("strBuydate");
		String strAddress = request.getParameter("strAddress");
		String strUsenature = request.getParameter("strUsenature");
		String strRegisterdate = request.getParameter("strRegisterdate");
		String strCaridentifycode = request.getParameter("strCaridentifycode");
		String strDateofissue = request.getParameter("strDateofissue");
		String strEnginenumber = request.getParameter("strEnginenumber");
		String strPostprovince = request.getParameter("strPostprovince");
		String strPostcity = request.getParameter("strPostcity");
		String strPostarea = request.getParameter("strPostarea");
		String strPoststreet = request.getParameter("strPoststreet");
		String strPostdetailaddress = request.getParameter("strPostdetailaddress");
		String strPostcode = request.getParameter("strPostcode");
		if(ValidateTool.isEmptyStr(strModelofcar)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, " 车辆型号不能为空", null);
		}
		if(ValidateTool.isEmptyStr(strCarlicense)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "车牌号码不能为空", null);
		}
		if(ValidateTool.isEmptyStr(strCartype)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "车辆类型不能为空", null);
		}
		if(ValidateTool.isEmptyStr(strAddress)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "住址不能为空", null);
		}
		if(ValidateTool.isEmptyStr(strUsenature)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "使用性质不能为空", null);
		}
		if(ValidateTool.isEmptyStr(strCaridentifycode)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "车辆识别代码不能为空", null);
		}
		if(ValidateTool.isEmptyStr(strEnginenumber)) {
			return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, "发动机号码不能为空", null);
		}
		
		//会员详细信息结束
		try {
			String strUpdatetime = DateTool.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS);
			List<MemberexpandinformationEntity> memberexpandinfoList = memberexpandinformationService.listMemberexpandinformation(null);
			List<MemberexpandattributeEntity> attributeList = new LinkedList<MemberexpandattributeEntity>();
			if(memberexpandinfoList!=null&&memberexpandinfoList.size()>0) {
				MemberexpandinformationEntity memberexpandinformationEntity = null;
				for(int i=0;i<memberexpandinfoList.size();i++) {
					memberexpandinformationEntity = memberexpandinfoList.get(i);
					String params = request.getParameter(memberexpandinformationEntity.getStrInformationid());
					int intIsmust = memberexpandinformationEntity.getIntIsmust(); //是否必填
					if(intIsmust==MemberexpandinformationMustEnum.MUST_TRUE.getValue()) {
						//必填
						if(ValidateTool.isEmptyStr(params)) {
							return DataTool.constructResponse(ResultCode.CAN_NOT_NULL, memberexpandinformationEntity.getStrInformationname() + "不能为空", null);
						}
					}
					if(!ValidateTool.isEmptyStr(params)) {
						MemberexpandattributeEntity memberexpandattributeEntity = new MemberexpandattributeEntity();
						memberexpandattributeEntity.setStrAttributeid(DataTool.getUUID());
						memberexpandattributeEntity.setStrMemberid(strMemberid);
						memberexpandattributeEntity.setStrInformationid(memberexpandinformationEntity.getStrInformationid());
						memberexpandattributeEntity.setStrAttributevalue(params.trim());
						memberexpandattributeEntity.setStrInserttime(strUpdatetime);
						attributeList.add(memberexpandattributeEntity);
					}
				}
			}
			
			//会员信息
			MemberEntity memberEntity = new MemberEntity();
			memberEntity.setStrMemberid(strMemberid);
			memberEntity.setStrRealname(strRealname.trim());
			memberEntity.setStrIdcard(strIdcard.trim());
			//memberEntity.setStrMobile(strMobile.trim());
			memberEntity.setIntAge(Integer.valueOf(strAge));
			//memberEntity.setStrMembercardnum(strMembercardnum.trim());
			memberEntity.setIntSex(intSex);
			memberEntity.setStrLevelsid(strLevelsid.trim());
			memberEntity.setIntStatus(intStatus);
			memberEntity.setStrUpdatetime(strUpdatetime);
			
			//会员详细资料
			MemberdetailEntity memberdetailEntity = new MemberdetailEntity();
			memberdetailEntity.setStrMemberdetailid(DataTool.getUUID());
			memberdetailEntity.setStrMemberid(strMemberid);
			memberdetailEntity.setStrModelofcar(strModelofcar.trim());
			memberdetailEntity.setStrCarlicense(strCarlicense.trim());
			memberdetailEntity.setStrCarcolor(DataTool.trimStr(strCarcolor));
			memberdetailEntity.setStrCartype(strCartype.trim());
			memberdetailEntity.setdBuyprice(new BigDecimal(DataTool.trimStr(dBuyprice)));
			memberdetailEntity.setStrBuydate(DataTool.trimStr(strBuydate));
			memberdetailEntity.setStrAddress(strAddress.trim());
			memberdetailEntity.setStrUsenature(strUsenature.trim());
			memberdetailEntity.setStrRegisterdate(DataTool.trimStr(strRegisterdate));
			memberdetailEntity.setStrCaridentifycode(strCaridentifycode.trim());
			memberdetailEntity.setStrDateofissue(DataTool.trimStr(strDateofissue));
			memberdetailEntity.setStrEnginenumber(strEnginenumber.trim());
			memberdetailEntity.setStrPostprovince(DataTool.trimStr(strPostprovince));
			memberdetailEntity.setStrPostcity(DataTool.trimStr(strPostcity));
			memberdetailEntity.setStrPostarea(DataTool.trimStr(strPostarea));
			memberdetailEntity.setStrPoststreet(DataTool.trimStr(strPoststreet));
			memberdetailEntity.setStrPostdetailaddress(DataTool.trimStr(strPostdetailaddress));
			memberdetailEntity.setStrPostcode(DataTool.trimStr(strPostcode));
			memberdetailEntity.setStrUpdatetime(strUpdatetime);
			
			
			memberService.updateMember(memberEntity, memberdetailEntity, attributeList);
			return DataTool.constructResponse(ResultCode.OK, "修改成功", null);
			
		} catch (Exception e) {
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
		}
	}
	
	
	/**
	 * 查询所有的职务信息
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("listAllMemberLevels")
	public String listAllMemberLevels(HttpServletRequest request, HttpServletResponse response) {
		try {
			List<MemberlevelsEntity> memberlevelsEntityList = memberService.listAllMemberLevels(MemberlevelsStatusEnum.ACTIVATE.getValue()); //查询所有可用的会员级别
			if(memberlevelsEntityList==null||memberlevelsEntityList.size()<=0) {
				return DataTool.constructResponse(ResultCode.NO_DATA, "暂无会员级别", null);
			}
			Map<String,Object> resultMap = new HashMap<String, Object>();
			resultMap.put("memberlevelsEntityList", memberlevelsEntityList);
			return DataTool.constructResponse(ResultCode.OK, "查询成功", resultMap);
		} catch (Exception e) {
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
		}
	}
	
	/**
	 * 查询所有的拓展资料信息
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("listAllExpandInfo")
	public String listAllExpandInfo(HttpServletRequest request, HttpServletResponse response) {
		
		String strMemberid = request.getParameter("strMemberid");
		try {
			List<MemberexpandinformationVO> memberexpandinformationVOList = memberService.listAllExpandInfo(strMemberid); //查询并组装会员拓展资料信息
			if(memberexpandinformationVOList==null||memberexpandinformationVOList.size()<=0) {
				return DataTool.constructResponse(ResultCode.NO_DATA, "暂无会员拓展资料", null);
			}
			Map<String,Object> resultMap = new HashMap<String, Object>();
			resultMap.put("memberexpandinformationVOList", memberexpandinformationVOList);
			return DataTool.constructResponse(ResultCode.OK, "查询成功", resultMap);
		} catch (Exception e) {
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
		}
	}
	
	
	/**
	 * 查询员工列表
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("listMember")
	public String listMember(HttpServletRequest request, HttpServletResponse response) {
		String pagenum = request.getParameter("pagenum");
		String pagesize = request.getParameter("pagesize");
		String strSearchkey = request.getParameter("strSearchkey");
		
		if(ValidateTool.isEmptyStr(pagenum)) {
			pagenum = "1";
		}
		int iPagesize = StaticValue.PAGE_SIZE;
		if(!ValidateTool.isEmptyStr(pagesize)) {
			iPagesize = Integer.valueOf(pagesize);
		}
		
		int pageFrom = (Integer.parseInt(pagenum)-1)*iPagesize;
		try {
			Map<String,Object> queryMap = new HashMap<String, Object>();
			queryMap.put("pageFrom", pageFrom);
			queryMap.put("pageSize", iPagesize);
			queryMap.put("strSearchkey", strSearchkey);
			List<MemberVO> memberList = memberService.listMember(queryMap);
			if(ValidateTool.isNull(memberList)||memberList.size()<=0) {
				return DataTool.constructResponse(ResultCode.NO_DATA, "暂无会员", null);
			} else {
				int totalrecord = memberService.getMemberTotalCount(queryMap); //查询会员总数量
				Map<String,Object> resultMap = new HashMap<String, Object>();
				resultMap.put("memberList", memberList);
				resultMap.put("iTotalRecord", totalrecord);
				resultMap.put("iTotalPage", totalrecord%iPagesize == 0 ? totalrecord/iPagesize : totalrecord/iPagesize+1);
				return DataTool.constructResponse(ResultCode.OK, "查询成功", resultMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return DataTool.constructResponse(ResultCode.SYSTEM_ERROR, "系统错误", null);
		}
		
	}
	
}
