/**
 * 全局请求路径
 */

define([],function(){
	
	var remoteUrl={
			/**
			 * 后台请求
			 */
			//登陆认证
			loginCertification:"/admin/loginCertification",
			//得到登陆用户信息  用于展示在界面头部
			getLoginUserInfo:"/admin/biz/getLoginUserInfo",
			//查询前端资料信息
			getFrontinformation:"/admin/biz/frontinformation/getFrontinformation",
			//修改前端资料信息
			updateFrontinformation:"/admin/biz/frontinformation/updateFrontinformation",
			//修改商家信息
			updateMerchantInfo:"/admin/biz/merchant/updateMerchantInfo",
			//上传商家logo
			uploadWechantLogo:'/admin/biz/file/uploadWechantLogo',
			//得到商家信息
			getMerchantInfo:"/admin/biz/merchant/getMerchantInfo",
			//商家系统升级
			upgradeMerchantSystem:"/admin/biz/merchant/upgradeMerchantSystem",
			//查询职务列表
			listDuty:"/admin/biz/duty/listDuty"
			
	}
	return remoteUrl;
	
})