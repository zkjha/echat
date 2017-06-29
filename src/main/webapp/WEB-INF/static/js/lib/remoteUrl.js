/**
 * 全局请求路径
 */

define([],function(){
	
	var remoteUrl={
			/**
			 * 后台请求
			 */
            ////////////homepage开始/////////////////////////////
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
            //查询员工列表
			listEmployee:"/admin/biz/employee/listEmployee",
			//根据ID查询员工信息
			getEmployeeById:"/admin/biz/employee/getEmployeeById",
            //查询所有职务
            listAllDuty:"/admin/biz/employee/listAllDuty",
            //新增员工信息
            insertEmployee:"/admin/biz/employee/insertEmployee",
            //修改员工信息
            updateEmployee:"/admin/biz/employee/updateEmployee",
            //重置员工密码
            resetEmployeePassword:"/admin/biz/employee/resetEmployeePassword",

        ////////////homepage结束/////////////////////////////

        ////////////微信互动开始/////////////////////////////
            //保存公众号信息
            updateWechantconfig:"/admin/biz/wechantconfig/updateWechantconfig",
            //查询公众号信息
            getWechantconfig:"/admin/biz/wechantconfig/getWechantconfig",

        ////////////微信互动结束/////////////////////////////



        ////////////会员部分开始/////////////////////////////
            //得到会员章程
            getMemberarticles:"/admin/biz/memberarticles/getMemberarticles",
            //保存会员章程
            updateMemberarticles:"/admin/biz/memberarticles/updateMemberarticles",
            //查询会员拓展资料列表
            listMemberexpandinformation:"/admin/biz/memberexpandinformation/listMemberexpandinformation",
            //删除会员拓展资料
            deleteMemberexpandinformation:"/admin/biz/memberexpandinformation/deleteMemberexpandinformation",
            //新增会员拓展资料
            insertMemberexpandinformation:"/admin/biz/memberexpandinformation/insertMemberexpandinformation",
            //根据ID查询会员拓展资料
            getMemberexpandinformationById:"/admin/biz/memberexpandinformation/getMemberexpandinformationById",
            //查询会员列表
            listMember:"/admin/biz/member/listMember",
            //查询会员级别select
            listAllMemberLevels:"/admin/biz/member/listAllMemberLevels",
            //会员拓展资料选项信息
            listAllExpandInfo:"/admin/biz/member/listAllExpandInfo",
            //新增会员
            insertMember:"/admin/biz/member/insertMember",
            //修改会员
            updateMember:"/admin/biz/member/updateMember",
            //根据会员ID查询会员信息
            getMemberById:"/admin/biz/member/getMemberById",
            //修改用户积分
            modMemberIntegral:"/admin/biz/member/modMemberIntegral",
            //修改会员禁用和启用状态
            updateMemberStatus:"/admin/biz/member/updateMemberStatus",
            //后台为会员售后充值
            backgroundRechargForMember:"/admin/biz/member/backgroundRechargForMember",
            //后台现金充值
            cashMoneyRechargForMember:"/admin/biz/member/cashMoneyRechargForMember",
            //查询充值订单是否支付成功
            checkIsPayed:"/admin/biz/member/checkIsPayed",
            //查询会员订单信息
            listMemberGoodsOrder:"/admin/biz/member/listMemberGoodsOrder",
        ////////////会员部分结束/////////////////////////////



		////////////会员收银管理/////////////////////////////
			//商品类型－修改
			updateGoodsType:"/admin/biz/Cashier/updateGoodsType",
			//商品类型－分页查询
			selGoodsTypeList:"/admin/biz/Cashier/selGoodsTypeList",
			//商品类型－删除
			delGoodsType:"/admin/biz/Cashier/delGoodsType",
			//商品类型－新增
			insertGoodsType:"/admin/biz/Cashier/insertGoodsType",
			//商品类型－查询详情
			selGoodsTypeDetail:"/admin/biz/Cashier/selGoodsTypeDetail",
			//商品－修改
			updateGoodsInfo:"/admin/biz/goods/updateGoodsInfo",
			//商品－查询会员分级别优惠信息
			getListGoodsPreferentialByGoodsId:"/admin/biz/goods/getListGoodsPreferentialByGoodsId",
			//商品－分页查询
			getListGoodsInfoByPage:"/admin/biz/goods/getListGoodsInfoByPage",
			//商品－删除
			delGoodsInfo:"/admin/biz/goods/delGoodsInfo",
			//商品－增加
			insertGoodsInfo:"/admin/biz/goods/insertGoodsInfo",
			//商品－查询详情
			getGoodsInfo:"/admin/biz/goods/getGoodsInfo",
			//服务类型－修改
			updateServiceType:"/admin/biz/Cashier/updateServiceType",
			//服务类型－分页查询
			getListServiceType:"/admin/biz/Cashier/getListServiceType",
			//服务类型－删除
			delServiceType:"/admin/biz/Cashier/delServiceType",
			//服务类型－新增
			insertServiceType:"/admin/biz/Cashier/insertServiceType",
			//服务类型－查询详情
			getServiceType:"/admin/biz/Cashier/getServiceType",
			//服务项目－修改
			updateServiceInfo:"/admin/biz/Service/updateServiceInfo",
			//服务项目－分页查询
			getListServiceInfo:"/admin/biz/Service/getListServiceInfo",
			//服务项目－删除
			delServiceInfo:"/admin/biz/Service/delServiceInfo",
			//服务项目－增加
			insertServiceInfo:"/admin/biz/Service/insertServiceInfo",
			//服务项目－分级别优惠列表
			getListServicePreferentialByServiceId:"/admin/biz/Service/getListServicePreferentialByServiceId",
			//服务项目－查询详情
			getServiceInfo:"/admin/biz/Service/getServiceInfo",
			//计量单位－修改
			updateUnit:"/admin/biz/Cashier/updateUnit",
			//计量单位－分页查询
			selUnitList:"/admin/biz/Cashier/selUnitList",
			//计量单位－删除
			delUnit:"/admin/biz/Cashier/delUnit",
			//计量单位－增加
			insertUnit:"/admin/biz/Cashier/insertUnit",
			//计量单位－查询详情
			selUnitDetail:"/admin/biz/Cashier/selUnitDetail",




		//	////////////李哥写的代码////////////////////////////
        //收银台 -- 按姓名 手机号 卡号 查询会员信息
        selectMemberInfoFromSearch:"/admin/biz/CashierDesk/selectMemberInfoFromSearch",
        //收银台 -- 按商品类别查询所有商品详情 -- 查询列表
        selectAllGoodsInfoEntity:"/admin/biz/CashierDesk/selectAllGoodsInfoEntity",
        //收银台 -- 按服务类别查询所有商品详情 -- 查询列表
        selectAllServiceInfoEntity:"/admin/biz/CashierDesk/selectAllServiceInfoEntity",
        //收银台 -- 生成订单号
        getOrderNum:"/admin/biz/CashierDesk/getOrderNum",
        //收银台 -- 生成订单
        generatePurchaseOrder:"/admin/biz/CashierDesk/generatePurchaseOrder",



		////////////会员收银管理结束/////////////////////////////
		
		////////////会员活动管理开始/////////////////////////////	
		
			//广告轮播－修改
			updateLoopAdvPic:"/admin/biz/advpic/updateLoopAdvPic",
			//广告轮播－删除单条
			delLoopAdvPic:"/admin/biz/advpic/delLoopAdvPic",
			//广告轮播－增加
			insertLoopAdvPic:"/admin/biz/advpic/insertLoopAdvPic",
			//广告轮播－查询单条
			getLoopAdvPic:"/admin/biz/advpic/getLoopAdvPic",
			//广告轮播－查询所有（权重降序）
			getListLoopAdvPic:"/admin/biz/advpic/getListLoopAdvPic",
			//积分清理规则－修改
			updateIntegralclearRule:"/admin/biz/ActivitySetting/updateIntegralclearRule",
			//积分清理规则－查询
			getIntegralclearRule:"/admin/biz/ActivitySetting/getIntegralclearRule",
		    //积分规则 -- 非连续签到积分规则 -- 查询
            findAllSignIntegrationRules:"/admin/biz/RuleSetting/findAllSignIntegrationRules",
			//积分规则 -- 连续签到积分规则 -- 查询
            findAllSignIntegrationRule:"/admin/biz/RuleSetting/findAllSignIntegrationRule",
            //积分规则 -- 积分抵现规则 -- 查询
            findAllIntegrationCashRule:"/admin/biz/RuleSetting/findAllIntegrationCashRule",
            // 积分规则 -- ( 连续 / 非连续) 签到积分规则 -- 删除
            deleteSignIntegrationRule:"/admin/biz/RuleSetting/deleteSignIntegrationRule",
            // 积分规则 -- 积分抵现规则 -- 删除
            deleteIntegrationCashRule:"/admin/biz/RuleSetting/deleteIntegrationCashRule",
            //积分规则 - 非连续签到积分规则 - 新增
            insertSignIntegrationRules:"/admin/biz/RuleSetting/insertSignIntegrationRules",
            //积分规则 - 非连续签到积分规则 - 更新
            updateSignIntegrationRule:"/admin/biz/RuleSetting/updateSignIntegrationRule",
            ///admin/biz/RuleSetting/updateIntegrationCashRule
            //积分规则 - 积分抵现规则 - 更新
            updateIntegrationCashRule:"/admin/biz/RuleSetting/updateIntegrationCashRule",
            //积分规则 - 连续签到积分规则 - 更新
            updateSignIntegrationRules:"/admin/biz/RuleSetting/updateSignIntegrationRules",
            //抵用券维护 -- 抵用券详情 -- 分页查询
            selectVoucherTicketInfo:"/admin/biz/voucherTickeSetting/selectVoucherTicketInfo",
            //抵用券维护 -- 抵用券详情 -- 删除
            deleteVoucherTicketInfo:"/admin/biz/voucherTickeSetting/deleteVoucherTicketInfo",
            //抵用券维护 -- 抵用券详情 -- 更新
            updateVoucherTicketInfo:"/admin/biz/voucherTickeSetting/updateVoucherTicketInfo",
            //抵用券维护 -- 抵用券详情 -- 查询单条
            findVoucherTicketInfoById:"/admin/biz/voucherTickeSetting/findVoucherTicketInfoById",
            //抵用券维护 -- 抵用券详情 -- 新增
            insertVoucherTicketInfo:"/admin/biz/voucherTickeSetting/insertVoucherTicketInfo",
            //首次入会 -- 赠送积分 -- 查询
            selectFirstMemberInitiationIntegrationPresents:"/admin/biz/presentsSetting/selectFirstMemberInitiationIntegrationPresents",//首次入会 -- 赠送积分 -- 查询
            //首次入会 -- 赠送积分 -- 更新
            updateFirstMemberInitiationIntegrationPresents:"/admin/biz/presentsSetting/updateFirstMemberInitiationIntegrationPresents",
            //首次入会 -- 赠送积分 -- 增加
            insertFirstMemberInitiationIntegrationPresents:"/admin/biz/presentsSetting/insertFirstMemberInitiationIntegrationPresents",
            //首次入会 -- 赠送积分 -- 删除
            deleteFirstMemberInitiationIntegrationPresents:"/admin/biz/presentsSetting/deleteFirstMemberInitiationIntegrationPresents",
            //首次入会 -- 储值 -- 查询
            selectStoredTicketPresentsInfo:"/admin/biz/presentsSetting/selectStoredTicketPresentsInfo",
            //首次入会 -- 储值 -- 更新
            updateStoredTicketPresentsInfo:"/admin/biz/presentsSetting/updateStoredTicketPresentsInfo",
            //首次入会 -- 储值 -- 增加
            insertStoredTicketPresentsInfo:"/admin/biz/presentsSetting/insertStoredTicketPresentsInfo",
            //首次入会 -- 储值 -- 删除
            deleteStoredTicketPresentsInfo:"/admin/biz/presentsSetting/deleteStoredTicketPresentsInfo",
            //抵用券维护 -- 抵用券详情 -- 查询 下拉列表
            getListBoxtVoucherTicketInfo:'/admin/biz/voucherTickeSetting/getListBoxtVoucherTicketInfo',
            //首次入会 -- 抵用券 -- 新增
            insertVoucherTicketPresentsInfo:"/admin/biz/presentsSetting/insertVoucherTicketPresentsInfo",
            //首次入会 -- 抵用券 -- 删除
            deleteVoucherTicketPresentsInfo:"/admin/biz/presentsSetting/deleteVoucherTicketPresentsInfo",
            //首次入会 -- 抵用券 -- 批量更新
            insertAndUpdateVoucherTicketPresentsInfo:"/admin/biz/presentsSetting/insertAndUpdateVoucherTicketPresentsInfo",
            //首次入会 -- 抵用券 -- 查询
            selectVoucherTicketPresentsInfo:"/admin/biz/presentsSetting/selectVoucherTicketPresentsInfo",
            //充值赠送 -- 分页查询
            selectRechargePresentsActivityInfo:"/admin/biz/RechargePresentsSetting/selectRechargePresentsActivityInfo",
            //充值赠送 -- 删除
            deleteRechargePresentsActivityInfo:"/admin/biz/RechargePresentsSetting/deleteRechargePresentsActivityInfo",
            //充值赠送 -- 更新活动
            updatePresentsActivityInfo:"/admin/biz/RechargePresentsSetting/updatePresentsActivityInfo",
            //充值赠送 -- 活动信息 -- 新增
            insertPresentsActivityInfo:"/admin/biz/RechargePresentsSetting/insertPresentsActivityInfo",
            //充值赠送 -- 活动信息 -- 查询
            selectAllRechargePresentsActivityEntity:"/admin/biz/RechargePresentsSetting/selectAllRechargePresentsActivityEntity",
            //充值赠送 -- 赠送储值 -- 删除
            deleteRechargePresentsStoredValue:"/admin/biz/RechargePresentsSetting/deleteRechargePresentsStoredValue",
            //充值赠送 -- 赠送储值 -- 批量新增
            batchInsertRechargePresentsStoredValue:"/admin/biz/RechargePresentsSetting/batchInsertRechargePresentsStoredValue",
            //充值赠送 -- 赠送储值 -- 批量更新
            batchUpdateRechargePresentsStoredValue:"/admin/biz/RechargePresentsSetting/batchUpdateRechargePresentsStoredValue",
            //充值赠送 -- 赠送储值 -- 查询
            selectAllRechargePresentsStoredValue:"/admin/biz/RechargePresentsSetting/selectAllRechargePresentsStoredValue",
            //充值赠送 -- 赠送抵用券 -- 删除
            deleteRechargePresentsVoucher:"/admin/biz/RechargePresentsSetting/deleteRechargePresentsVoucher",
            //充值赠送 -- 赠送抵用券 -- 批量修改
            updateRechargePresentsVoucher:"/admin/biz/RechargePresentsSetting/updateRechargePresentsVoucher",
            //充值赠送 -- 赠送抵用券 -- 查询
            selectAllRechargePresentsVoucher:"/admin/biz/RechargePresentsSetting/selectAllRechargePresentsVoucher",
            //充值赠送 -- 赠送积分 -- 新增
            insertRechargePresentsIntegration:"/admin/biz/RechargePresentsSetting/insertRechargePresentsIntegration",
            //充值赠送 -- 赠送积分 -- 更新
            updateRechargePresentsIntegration:"/admin/biz/RechargePresentsSetting/updateRechargePresentsIntegration",
            //充值赠送 -- 赠送积分 -- 查询
            selectAllRechargePresentsIntegration:"/admin/biz/RechargePresentsSetting/selectAllRechargePresentsIntegration",
            //充值赠送 --抵用券赠送 -- 批量新增
            batchInsertRechargePresentsVoucher:"/admin/biz/RechargePresentsSetting/batchInsertRechargePresentsVoucher",
            //充值赠送 -- 赠送活动 -- 刚新建活动信息查询 单条
            selectRechargePresentsActivityEntity:"/admin/biz/RechargePresentsSetting/selectRechargePresentsActivityEntity",
            //    充值赠送 -- 赠送活动 -- 抵用券查询刚新建的 单条
            selectRechargePresentsStoredValueEntity:"/admin/biz/RechargePresentsSetting/selectRechargePresentsStoredValueEntity",
         //   自定义赠送 --活动信息-- 分页查询
        selectAllUserDefinedPresentsActivity:"/admin/biz/UserDefinedPresentsSetting/selectAllUserDefinedPresentsActivity",
        //   自定义赠送 -- 分页显示 --删除
        deleteUserDefinedActivityInfo:"/admin/biz/UserDefinedPresentsSetting/deleteUserDefinedActivityInfo",
        //   自定义赠送 -- 活动信息 -- 新增
        insertUserDefinedPresentsActivityInfo:"/admin/biz/UserDefinedPresentsSetting/insertUserDefinedPresentsActivityInfo",
        //   自定义赠送 -- 活动信息 -- 新建记录查询单条
        selectUserDefinedPresentsActivityEntity:"/admin/biz/UserDefinedPresentsSetting/selectUserDefinedPresentsActivityEntity",
        //   自定义赠送 -- 活动信息 -- 更新
        updateUserDefinedPresentsActivityInfo:"/admin/biz/UserDefinedPresentsSetting/updateUserDefinedPresentsActivityInfo",
        //   自定义赠送 -- 活动信息 -- 查询单条
        selectUserDefinedPresentActivityById:"/admin/biz/UserDefinedPresentsSetting/selectUserDefinedPresentActivityById",
        //   自定义赠送 -- 赠送储值 -- 修改
        updateUserDefinedPresentsStoredValueEntity:"/admin/biz/UserDefinedPresentsSetting/updateUserDefinedPresentsStoredValueEntity",
        //   自定义赠送 -- 赠送储值 -- 删除
        deleteUserDefinedPresentsStoredValueEntity:"/admin/biz/UserDefinedPresentsSetting/deleteUserDefinedPresentsStoredValueEntity",
        //   自定义赠送 -- 赠送储值 -- 新增
        insertUserDefinedPresentsStoredValueEntity:"/admin/biz/UserDefinedPresentsSetting/insertUserDefinedPresentsStoredValueEntity",
        //   自定义赠送 -- 赠送储值 -- 查询 单条
        selectAllUserDefinedPresentsStoredValueEntity:"/admin/biz/UserDefinedPresentsSetting/selectAllUserDefinedPresentsStoredValueEntity",
        //   自定义赠送 -- 赠送抵用券 -- 删除
        deleteUserDefinedPresentsVoucherEntity:"/admin/biz/UserDefinedPresentsSetting/deleteUserDefinedPresentsVoucherEntity",
        //   自定义赠送 -- 赠送抵用券 -- 批量修改
        batchUpdateUserDefinedPresentsVoucherEntity:"/admin/biz/UserDefinedPresentsSetting/batchUpdateUserDefinedPresentsVoucherEntity",
        //   自定义赠送 -- 赠送抵用券 -- 批量新增
        batchInsertUserDefinedPresentsVoucherEntity:"/admin/biz/UserDefinedPresentsSetting/batchInsertUserDefinedPresentsVoucherEntity",
        //   自定义赠送 -- 赠送积分 -- 修改
        updateUserDefinedPresentsIntegration:"/admin/biz/UserDefinedPresentsSetting/updateUserDefinedPresentsIntegration",
        //   自定义赠送 -- 赠送积分 -- 删除
        deleteUserDefinedPresentsIntegrationEntity:"/admin/biz/UserDefinedPresentsSetting/deleteUserDefinedPresentsIntegrationEntity",
        // 自定义赠送 -- 赠送积分 -- 新增
        insertUserDefinedPresentsIntegration:"/admin/biz/UserDefinedPresentsSetting/insertUserDefinedPresentsIntegration",
        // 自定义赠送 -- 赠送积分 -- 查询单条
        selectAllUserDefinedPresentsIntegration:"/admin/biz/UserDefinedPresentsSetting/selectAllUserDefinedPresentsIntegration",
        // 自定义赠送 --赠送抵用券 --查询
        selectAllUserDefinedPresentsVoucherEntity:"/admin/biz/UserDefinedPresentsSetting/selectAllUserDefinedPresentsVoucherEntity",
        //消费赠送 -- 分页浏览 -- 删除
        deleteConsumePresentsActivityInfo:"/admin/biz/consumePresentsSetting/deleteConsumePresentsActivityInfo",
         //消费赠送 -- 活动 -- 分页查询
        selectAllConsumePresentsActivity:"/admin/biz/consumePresentsSetting/selectAllConsumePresentsActivity",
        //消费赠送 -- 活动信息 -- 新增
        insertConsumePresentsActivityEntity:"/admin/biz/consumePresentsSetting/insertConsumePresentsActivityEntity",
        //消费赠送 -- 活动信息 -- 更新
        updateConsumePresentsActivityEntity:"/admin/biz/consumePresentsSetting/updateConsumePresentsActivityEntity",
        //消费赠送 -- 活动信息 -- 查询 单条 刚新建的活动信息
        selectConsumePresentsActivityInfo:"/admin/biz/consumePresentsSetting/selectConsumePresentsActivityInfo",
        //消费赠送 -- 活动信息 -- 查询单条
        selectConsumePresentsActivityEntity:"/admin/biz/consumePresentsSetting/selectConsumePresentsActivityEntity",
        //消费赠送 -- 赠送储值 -- 删除
        deleteConsumePresentsStoredValueEntity:"/admin/biz/consumePresentsSetting/deleteConsumePresentsStoredValueEntity",
        //消费赠送 -- 赠送储值 -- 批量新增
        batchInsertConsumePresentsStoredValueEntity:"/admin/biz/consumePresentsSetting/batchInsertConsumePresentsStoredValueEntity",
        //消费赠送 -- 赠送储值 -- 批量更新
        batchUpdateConsumePresentsStoredValueEntity:"/admin/biz/consumePresentsSetting/batchUpdateConsumePresentsStoredValueEntity",
        //消费赠送 -- 赠送储值 -- 查询列表
        selectAllConsumePresentsStoredValueEntity:"/admin/biz/consumePresentsSetting/selectAllConsumePresentsStoredValueEntity",
        //消费赠送 -- 赠送抵用券 -- 删除
        deleteConsumePresentsVoucherEntity:"/admin/biz/consumePresentsSetting/deleteConsumePresentsVoucherEntity",
        //消费赠送 -- 赠送抵用券 -- 批量新增
        batchInsertConsumePresentsVoucherEntity:"/admin/biz/consumePresentsSetting/batchInsertConsumePresentsVoucherEntity",
         //消费赠送 -- 赠送抵用券 -- 批量更新
        batchUpdateConsumePresentsVoucherEntity:"/admin/biz/consumePresentsSetting/batchUpdateConsumePresentsVoucherEntity",
        //消费赠送 -- 赠送抵用券 -- 查询列表
        selectAllConsumePresentsVoucherEntity:"/admin/biz/consumePresentsSetting/selectAllConsumePresentsVoucherEntity",
        //消费赠送 -- 赠送积分 -- 删除
        deleteConsumePresentsIntegrationEntity:"/admin/biz/consumePresentsSetting/deleteConsumePresentsIntegrationEntity",
        //消费赠送 -- 赠送积分 -- 批量修改
        batchUpdateConsumePresentsIntegrationEntity:"/admin/biz/consumePresentsSetting/batchUpdateConsumePresentsIntegrationEntity",
        //消费赠送 -- 赠送积分 -- 批量新增
        batchInsertConsumePresentsIntegrationEntity:"/admin/biz/consumePresentsSetting/batchInsertConsumePresentsIntegrationEntity",
        //消费赠送 -- 赠送积分 -- 查询 列表
        selectAllConsumePresentsIntegrationEntity:"/admin/biz/consumePresentsSetting/selectAllConsumePresentsIntegrationEntity",












		////////////会员活动管理结束/////////////////////////////
        /**
         * 微信前端部分
         */
        ////////////会员中心/////////////////////////////
        //会员中心 -- 签到信息(最近一次签到信息) -- 查询
        selectSignDays:"/weixin/biz/selectSignDays",
        //会员中心 -- 签到信息（按月) -- 查询
        selectSignDaysByMonth:"/weixin/biz/selectSignDaysByMonth",
        //会员签到 -- 签到信息 -- 新增
        insertMemberSignInfo:"/weixin/biz/insertMemberSignInfo",
        //会员签到 -- 签到信息 -- 新增
        selectMemberInfo:"/weixin/biz/selectMemberInfo",






        ////////////会员中心/////////////////////////////

		////////////登陆认证/////////////////////////////
			
			//发送验证码
			weixinsendAuthcode:"/weixin/sendAuthcode",
			//认证
			weixinloginCertification:"/weixin/loginCertification",
			//得到登陆的用户信息
			 weixingetLoginUserInfo:" /weixin/biz/getLoginUserInfo"
        ////////////登陆认证/////////////////////////////



    };
	return remoteUrl;
	
});