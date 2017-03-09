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
			getLoginUserInfo:"/admin/page/getLoginUserInfo",
			//查询前端资料信息
			getFrontinformation:"/admin/biz/frontinformation/getFrontinformation",
			//修改前端资料信息
			updateFrontinformation:"/admin/biz/frontinformation/updateFrontinformation",
			//修改商家信息
			updateMerchantInfo:"/admin/biz/merchant/updateMerchantInfo",
			//上传图片
			uploadImage:'/admin/biz/file/uploadImage',
			//得到商家信息
			getMerchantInfo:"/admin/biz/merchant/getMerchantInfo",
			//商家系统升级
			upgradeMerchantSystem:"/admin/biz/merchant/upgradeMerchantSystem",
			//查询职务列表
			listDuty:"/admin/biz/duty/listDuty",
			//查询权限列表
			listPrivilege:"/admin/biz/privilege/listPrivilege",
			//根据ID查询职务详情
			getDutyById:" /admin/biz/duty/getDutyById",
			//新增职务
			insertDuty:" /admin/biz/duty/insertDuty",
			//修改职务
			updateDuty:"/admin/biz/duty/updateDuty",
			//保存公众号信息
			updateWechantconfig:"/admin/biz/wechantconfig/updateWechantconfig",
			//查询公众号信息
			getWechantconfig:"/admin/biz/wechantconfig/getWechantconfig",
			//查询员工列表
			listEmployee:"/admin/biz/employee/listEmployee",
			//根据ID查询员工信息
			getEmployeeById:"/admin/biz/employee/getEmployeeById",
            //查询所有职务
            listAllDuty:"/admin/biz/employee/listAllDuty"
	
	
	}
	return remoteUrl;
	
})