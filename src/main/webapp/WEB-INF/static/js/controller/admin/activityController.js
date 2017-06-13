/**
 * Created by zhujunliang on 17/5/1
 */
define(
	['lib/remoteUrl'],
	function(remoteUrl) {
		'use strict'

		var activityCtrl = {




            ///////////////////消费赠送-开始////////////////////////
            consumptiongift:function($scope,$http,$q){
                //分页查询
                $scope.currentPage = 1;
                $scope.pageSize = 5;
                $scope.strSearchEnabledStatus = null;
                $scope.strSearchMemberLevelId = null;
                $scope.actiStatus = [{id:1,name:"正常"},{id:0,name:"过期"}];;//活动状态
                $scope.leijixiaofei = [{id:"1",name:"是"},{id:"0",name:"否"}];
                //$scope.consumePresentsActivityEntity[0].iActivityKinds = 0;
                //$scope.consumePresentsActivityEntity.strIsCumulation = 1;//设置默认值

                $scope.isShowListMenu = [];//二级菜单显示必要的一步
                $scope.panduanshixinzenghaishiqitaxiaoxiugai = false;//判断是新增还是其它修改
                //调用会员级别查询
                activityCtrl.listAllMemberLevels($scope,$http);
                // 会员级别，活动状态赛选
                $scope.saixuanVip = function () {
                    $scope.strSearchMemberLevelId = $scope.VipGrade;
                    activityCtrl.selectAllConsumePresentsActivity($scope,$http,$scope.strSearchEnabledStatus,$scope.strSearchMemberLevelId)
                }
                $scope.saixuanStu = function () {
                    $scope.strSearchEnabledStatus = $scope.actStatus;
                    activityCtrl.selectAllConsumePresentsActivity($scope,$http,$scope.strSearchEnabledStatus,$scope.strSearchMemberLevelId)
                }
                //    二级菜单
                $scope.openCtrMenu = function ($index, type) {
                    for (var i = 0; i < $scope.isShowListMenu.length; i++) {
                        $scope.isShowListMenu[i] = false;
                    }
                    if (type == 'over') {
                        $scope.isShowListMenu[$index] = !$scope.isShowListMenu[$index];
                    }
                    $scope.huodongquzhigudingId = $index;
                };
                //分页查询
                $scope.onPageChange = function () {
                    // ajax request to load data
                    $scope.resultMap = {};
                    activityCtrl.selectAllConsumePresentsActivity($scope,$http);

                };
                //分页部分删除
                $scope.delectExpand = function(strActivityId,strActivityName){
                    $scope.showConfirm("确认删除"+strActivityName,function(rs){
                        if(rs){
                            activityCtrl.deleteConsumePresentsActivityInfo($scope,$http,strActivityId)
                        }
                    })
                }
                // 点击新增按钮
                $scope.newExpandinginfo = function(){
                    $scope.panduanshixinzenghaishiqitaxiaoxiugai = true;//判断修改进来还是新增进来
                    $scope.consumePresentsActivityEntity.iActivityKinds = 1;//让分类查询显示内容
                    //清空所有查询的内容
                    $scope.consumePresentsActivityEntity = {};
                    $scope.listConsumePresentsIntegrationEntity = {};
                    $scope.listConsumePresentsIntegrationEntityJf = {};
                    $scope.listUserDefinedPresentsVoucherEntity = {};
                    $scope.listConsumePresentsStoredValueEntity = {};
                    $scope.listConsumePresentsStoredValueEntityCz = {};
                    $scope.listConsumePresentsVoucherEntity = {};
                    $scope.listConsumePresentsVoucherEntityDyq = {};

                    $scope.showExpandInfoWindow = true;
                }
                //    点击关闭按钮
                $scope.clostExpandWindow = function(){
                    //清空所有查询的内容
                    $scope.consumePresentsActivityEntity = {};
                    $scope.listConsumePresentsIntegrationEntity = {};
                    $scope.listConsumePresentsIntegrationEntityJf = {};
                    $scope.listUserDefinedPresentsVoucherEntity = {};
                    $scope.listConsumePresentsStoredValueEntity = {};
                    $scope.listConsumePresentsStoredValueEntityCz = {};
                    $scope.listConsumePresentsVoucherEntity = {};
                    $scope.listConsumePresentsVoucherEntityDyq = {};

                    $scope.showExpandInfoWindow = false;
                    $scope.yihouougaiyaoyongdehuodongId = null;
                }
                //    自定义赠送点击修改按钮   $scope.updataExpand($scope.yihouougaiyaoyongdehuodongId)
                $scope.updataExpand = function(strActivityId){
                    ////调用接口-活动
                    activityCtrl.selectConsumePresentsActivityEntity($scope,$http,strActivityId);
                    //////调用接口-积分
                    activityCtrl.selectAllConsumePresentsIntegrationEntity($scope,$http,strActivityId);
                    //////调用接口-储值
                    activityCtrl.selectAllConsumePresentsStoredValueEntity($scope,$http,strActivityId);
                    //////调用接口-抵用券
                    activityCtrl.selectAllConsumePresentsVoucherEntity($scope,$http,strActivityId);
                    ////显示模态框
                    $scope.panduanshixinzenghaishiqitaxiaoxiugai = false;//判断修改进来还是新增进来
                    $scope.chaxunchulaihougonggongdehuodongId = null;//清空新增时候查询的活动Id
                    $scope.showExpandInfoWindow = true;
                    $scope.yihouougaiyaoyongdehuodongId = strActivityId;
                };
                //    点击模态框内部积分加号   showExpandInfoWindowDyq
                $scope.newExpandinginfoJf = function(){
                    $scope.showExpandInfoWindowJf = true;
                    $scope.showExpandInfoWindow = false;
                    $scope.jifenchangdu = Object.keys($scope.listConsumePresentsIntegrationEntity).length
                    $scope.jifenxingzeng = {};
                }
                $scope.addjifen = function(){
                    if(!$scope.panduanshixinzenghaishiqitaxiaoxiugai){

                        $scope.jifenxingzeng.strActivityId = $scope.yihouougaiyaoyongdehuodongId;
                        $scope.jifenxingzeng.strEnabledsssId = $scope.listConsumePresentsIntegrationEntity[0].iEnabled;
                        //$scope.diyongquanxinzeng.strConsumePresentsVoucherId = $scope.listConsumePresentsVoucherEntity[0].strConsumePresentsVoucherId;
                        var diyongquanxinzengArr = [];
                        diyongquanxinzengArr[0] = $scope.jifenxingzeng
                        console.info(diyongquanxinzengArr)//为了满足后面的格式；
                        activityCtrl.batchInsertConsumePresentsIntegrationEntity($scope,$http,diyongquanxinzengArr)
                    }
                    $scope.listConsumePresentsIntegrationEntity[$scope.jifenchangdu] = $scope.jifenxingzeng;
                    $scope.showExpandInfoWindowJf = false;
                    $scope.showExpandInfoWindow = true;
                }

                //    点击模态框内部抵用券加号   showExpandInfoWindowDyq
                $scope.newExpandinginfoDyq = function(){
                    $scope.showExpandInfoWindowDyq = true;
                    $scope.showExpandInfoWindow = false;

                    $scope.diyongquanchangdu = Object.keys($scope.listConsumePresentsVoucherEntity).length
                    $scope.diyongquanxinzeng = {};
                }
                $scope.adddiyongquan = function(){
                    if(!$scope.panduanshixinzenghaishiqitaxiaoxiugai){

                        $scope.diyongquanxinzeng.strActivityId = $scope.yihouougaiyaoyongdehuodongId;
                        $scope.diyongquanxinzeng.strEnabledsssId = $scope.listConsumePresentsVoucherEntity[0].iEnabled;
                        //$scope.diyongquanxinzeng.strConsumePresentsVoucherId = $scope.listConsumePresentsVoucherEntity[0].strConsumePresentsVoucherId;
                        var diyongquanxinzengArr = [];
                        diyongquanxinzengArr[0] = $scope.diyongquanxinzeng
                        console.info(diyongquanxinzengArr)
                        activityCtrl.batchInsertConsumePresentsVoucherEntity($scope,$http,diyongquanxinzengArr)
                    }
                    $scope.listConsumePresentsVoucherEntity[$scope.diyongquanchangdu] = $scope.diyongquanxinzeng;
                    $scope.showExpandInfoWindowDyq = false;
                    $scope.showExpandInfoWindow = true;
                }
                //    点击模态框内部储值加号   showExpandInfoWindowDyq
                $scope.newExpandinginfoCz = function(){
                    $scope.showExpandInfoWindowCz = true;
                    $scope.showExpandInfoWindow = false;

                    $scope.chuziquanchangdu = Object.keys($scope.listConsumePresentsStoredValueEntity).length
                    $scope.chuzhiquanxinzeng = {};
                }
                $scope.addchuzhiquan = function(){
                    if(!$scope.panduanshixinzenghaishiqitaxiaoxiugai){
                        $scope.chuzhiquanxinzeng.strActivityId = $scope.yihouougaiyaoyongdehuodongId;
                        $scope.chuzhiquanxinzeng.strEnabledsssId = $scope.listConsumePresentsStoredValueEntity[0].iEnabled;
                        //$scope.diyongquanxinzeng.strConsumePresentsVoucherId = $scope.listConsumePresentsVoucherEntity[0].strConsumePresentsVoucherId;
                        var diyongquanxinzengArr = [];
                        diyongquanxinzengArr[0] = $scope.chuzhiquanxinzeng
                        console.info(diyongquanxinzengArr)//为了满足后面的格式；
                        activityCtrl.batchInsertConsumePresentsStoredValueEntity($scope,$http,diyongquanxinzengArr)
                    }

                    $scope.listConsumePresentsStoredValueEntity[$scope.chuziquanchangdu] = $scope.chuzhiquanxinzeng;

                    $scope.showExpandInfoWindowCz = false;
                    $scope.showExpandInfoWindow = true;
                }
            //关闭第二次新增
                $scope.CloseExpandInfoWindowDyq = function(){
                    $scope.showExpandInfoWindowDyq = false;
                    $scope.showExpandInfoWindowCz = false;
                    $scope.showExpandInfoWindowDyq = false;
                    $scope.showExpandInfoWindowJf = false;
                    $scope.showExpandInfoWindow = true;
                }

            //分页查询内容新增与修改
                $scope.submitExpandinfoAll = function(){
                    if($scope.panduanshixinzenghaishiqitaxiaoxiugai){//判断新增还是修改--新增
                        $scope.showConfirm("确定新增",function(rs){
                            if(rs){
                                activityCtrl.insertConsumePresentsActivityEntity($scope,$http,$scope.consumePresentsActivityEntity);
                            }
                        })
                    }else{//--x修改   updateConsumePresentsActivityEntity
                        activityCtrl.updateConsumePresentsActivityEntity($scope,$http,$scope.consumePresentsActivityEntity);
                        var deferred = $q.defer();
                        var promise = deferred.promise;
                        promise.then(function (result) {
                            activityCtrl.updateConsumePresentsActivityEntity($scope,$http,$scope.consumePresentsActivityEntity);;//活动信息更
                        }).then(function (result) {
                            activityCtrl.batchUpdateConsumePresentsIntegrationEntity($scope, $http, $scope.listConsumePresentsIntegrationEntity);//积分信息更新
                        }).then(function (result) {
                            activityCtrl.batchUpdateConsumePresentsStoredValueEntity($scope,$http,$scope.listConsumePresentsStoredValueEntity)//储值券信息更新
                        }).then(function (result) {
                            activityCtrl.batchUpdateConsumePresentsVoucherEntity($scope,$http,$scope.listConsumePresentsVoucherEntity);//抵用券信息更新
                        }).then(function (result){
                            $scope.showAlert("更新成功",function(){
                                window.location.reload();
                            })
                        },function(){
                            $scope.showAlert("更新失败",function(){
                                activityCtrl.deleteConsumePresentsActivityInfo($scope,$http,$scope.chaxunchulaihougonggongdehuodongId);
                            });
                        })
                        if(true){
                            deferred.resolve("执行成功");
                        }else{
                            deferred.reject("sorry, it lost!");
                        }
                    }
                }
            //剩下三个页面的新增与修改
                $scope.jifenczdyqxz = function(){
                    var deferred = $q.defer();
                    var promise = deferred.promise;
                    promise.then(function (result) {
                        activityCtrl.batchInsertConsumePresentsIntegrationEntity($scope, $http, $scope.listConsumePresentsIntegrationEntity);//积分新增
                    }).then(function (result) {
                        activityCtrl.batchInsertConsumePresentsStoredValueEntity($scope,$http,$scope.listConsumePresentsStoredValueEntity)//储值券新增
                    }).then(function (result) {
                        activityCtrl.batchInsertConsumePresentsVoucherEntity($scope,$http,$scope.listConsumePresentsVoucherEntity);//抵用券
                    }).then(function (result){
                        $scope.showAlert("新增成功",function(){
                            window.location.reload();
                        })
                    },function(){
                        $scope.showAlert("新增失败",function(){
                            activityCtrl.deleteConsumePresentsActivityInfo($scope,$http,$scope.chaxunchulaihougonggongdehuodongId);
                        });
                    })
                    if(true){
                        deferred.resolve("执行成功");
                    }else{
                        deferred.reject("sorry, it lost!");
                    }
                }
                //页面内容的删除
                //积分删除
                $scope.z_deleteJf = function(id,paixu){
                    if($scope.listConsumePresentsIntegrationEntity.length==1){
                        $scope.showAlert("最后一条数据不能删除！")
                    }else{
                        console.info($scope.listConsumePresentsIntegrationEntity[paixu])
                        activityCtrl.deleteConsumePresentsIntegrationEntity($scope,$http,id);
                        $scope.listConsumePresentsIntegrationEntity.splice(paixu,1)
                    }
                }
                //储值删除
                $scope.z_deleteCz = function(id,paixu){
                    if($scope.listConsumePresentsStoredValueEntity.length==1){
                        $scope.showAlert("最后一条数据不能删除！")
                    }else{
                        console.info($scope.listConsumePresentsStoredValueEntity[paixu])
                        activityCtrl.deleteConsumePresentsStoredValueEntity($scope,$http,id);
                        $scope.listConsumePresentsStoredValueEntity.splice(paixu,1)
                    }
                }
                //抵用券删除
                $scope.z_deleteDyq = function(id,paixu){
                    if($scope.listConsumePresentsVoucherEntity.length==1){
                        $scope.showAlert("最后一条数据不能删除！")
                    }else{
                        console.info($scope.listConsumePresentsVoucherEntity[paixu])
                        activityCtrl.deleteConsumePresentsVoucherEntity($scope,$http,id);
                        $scope.listConsumePresentsVoucherEntity.splice(paixu,1)
                    }
                }
                //调用分页查询接口
                activityCtrl.selectAllConsumePresentsActivity($scope,$http);
                //抵用券维护 -- 抵用券详情 -- 分页查询
                activityCtrl.selectVoucherTicketInfo($scope,$http);
            },
            //消费赠送 -- 赠送抵用券 -- 删除
            deleteConsumePresentsVoucherEntity:function($scope,$http,strConsumePresentsVoucherId){
                var data = {
                    strConsumePresentsVoucherId:strConsumePresentsVoucherId
                }
                $http.post(remoteUrl.deleteConsumePresentsVoucherEntity,data).then(
                    function(result){
                        var rs = result.data;
                        var data = result.data;
                        var code = data.code;
                        console.info(result);
                        if (code == 1) {
                            $scope.showAlert("删除成功")
                        } else if (code == -1) {
                            window.location.href = "/admin/login?url="
                            + window.location.pathname
                            + window.location.search
                            + window.location.hash;
                            //未登录
                        } else if (code <= -2 && code >= -7) {
                            //必填字段未填写
                            $scope.showAlert(rs.msg);
                        } else if (code == -8) {
                            //暂无数据
                            $scope.isNoData=true;
                            $scope.pageCount = 0;
                        }

                    }, function (result) {


                        var status = result.status;
                        if (status == -1) {
                            $scope.showAlert("服务器错误")
                        } else if (status >= 404 && status < 500) {
                            $scope.showAlert("请求路径错误")
                        } else if (status >= 500) {
                            $scope.showAlert("服务器错误")
                        }
                    }
                )
            },
            //消费赠送 -- 赠送储值 -- 删除
            deleteConsumePresentsStoredValueEntity:function($scope,$http,strStoredTicketId){
                var data = {
                    strStoredTicketId:strStoredTicketId
                }
                $http.post(remoteUrl.deleteConsumePresentsStoredValueEntity,data).then(
                    function(result){
                        var rs = result.data;
                        var data = result.data;
                        var code = data.code;
                        console.info(result);
                        if (code == 1) {
                            $scope.showAlert("删除成功")
                        } else if (code == -1) {
                            window.location.href = "/admin/login?url="
                            + window.location.pathname
                            + window.location.search
                            + window.location.hash;
                            //未登录
                        } else if (code <= -2 && code >= -7) {
                            //必填字段未填写
                            $scope.showAlert(rs.msg);
                        } else if (code == -8) {
                            //暂无数据
                            $scope.isNoData=true;
                            $scope.pageCount = 0;
                        }

                    }, function (result) {


                        var status = result.status;
                        if (status == -1) {
                            $scope.showAlert("服务器错误")
                        } else if (status >= 404 && status < 500) {
                            $scope.showAlert("请求路径错误")
                        } else if (status >= 500) {
                            $scope.showAlert("服务器错误")
                        }
                    }
                )
            },
            //消费赠送 -- 赠送积分 -- 删除
            deleteConsumePresentsIntegrationEntity:function($scope,$http,strIntegrationId){
                var data = {
                    strIntegrationId:strIntegrationId
                }
                $http.post(remoteUrl.deleteConsumePresentsIntegrationEntity,data).then(
                    function(result){
                        var rs = result.data;
                        var data = result.data;
                        var code = data.code;
                        console.info(result);
                        if (code == 1) {
                            $scope.showAlert("删除成功")
                        } else if (code == -1) {
                            window.location.href = "/admin/login?url="
                            + window.location.pathname
                            + window.location.search
                            + window.location.hash;
                            //未登录
                        } else if (code <= -2 && code >= -7) {
                            //必填字段未填写
                            $scope.showAlert(rs.msg);
                        } else if (code == -8) {
                            //暂无数据
                            $scope.isNoData=true;
                            $scope.pageCount = 0;
                        }

                    }, function (result) {


                        var status = result.status;
                        if (status == -1) {
                            $scope.showAlert("服务器错误")
                        } else if (status >= 404 && status < 500) {
                            $scope.showAlert("请求路径错误")
                        } else if (status >= 500) {
                            $scope.showAlert("服务器错误")
                        }
                    }
                )
            },
            //消费赠送 -- 赠送抵用券 -- 批量更新
            batchUpdateConsumePresentsVoucherEntity:function($scope,$http,listConsumePresentsVoucherEntity){
                var dConsumeCashAmounts = [];
                var iEnableds = [];
                var iPresentsIntegrationAmounts = [];
                var strActivityIds=[];
                var strVoucherTicketIds = [];
                var strConsumePresentsVoucherIds = [];
                for(let i in listConsumePresentsVoucherEntity){
                    dConsumeCashAmounts[i] = listConsumePresentsVoucherEntity[i].dConsumeCashAmount;
                    strConsumePresentsVoucherIds[i] = listConsumePresentsVoucherEntity[i].strConsumePresentsVoucherId;
                    iEnableds[i] = listConsumePresentsVoucherEntity[0].strEnabledsssId || 0;
                    iPresentsIntegrationAmounts[i] = listConsumePresentsVoucherEntity[i].iPresentsIntegrationAmount;
                    strActivityIds[i] =$scope.yihouougaiyaoyongdehuodongId;
                    strVoucherTicketIds[i] = listConsumePresentsVoucherEntity[i].strVoucherTicketId
                }
                var data = {
                    dConsumeCashAmount:dConsumeCashAmounts.join(","),
                    iEnabled:iEnableds.join(","),
                    iPresentsIntegrationAmount:iPresentsIntegrationAmounts.join(","),
                    strActivityId:strActivityIds.join(","),
                    strConsumePresentsVoucherId:strConsumePresentsVoucherIds.join(","),
                    strVoucherTicketId:strVoucherTicketIds.join(",")
                }
                $http.post(remoteUrl.batchUpdateConsumePresentsVoucherEntity,data).then(
                    function(result){
                        var rs = result.data;
                        var data = result.data;
                        var code = data.code;
                        console.info(result);
                        if (code == 1) {

                            console.info("储值修改成功")

                        } else if (code == -1) {
                            window.location.href = "/admin/login?url="
                            + window.location.pathname
                            + window.location.search
                            + window.location.hash;
                            //未登录
                        } else if (code <= -2 && code >= -7) {
                            //必填字段未填写
                            $scope.showAlert(rs.msg);
                        } else if (code == -8) {
                            //暂无数据
                            $scope.isNoData=true;
                            $scope.pageCount = 0;
                        }

                    }, function (result) {


                        var status = result.status;
                        if (status == -1) {
                            $scope.showAlert("服务器错误")
                        } else if (status >= 404 && status < 500) {
                            $scope.showAlert("请求路径错误")
                        } else if (status >= 500) {
                            $scope.showAlert("服务器错误")
                        }
                    }
                )
            },
            //消费赠送 -- 赠送储值 -- 批量更新
            batchUpdateConsumePresentsStoredValueEntity:function($scope,$http,listConsumePresentsStoredValueEntity){
                var dConsumeCashAmounts = [];
                var iEnableds = [];
                var iPresentsIntegrationAmounts = [];
                var strActivityIds=[];
                var strStoredTicketIds = [];
                for(let i in listConsumePresentsStoredValueEntity){
                    dConsumeCashAmounts[i] = listConsumePresentsStoredValueEntity[i].dConsumeCashAmount;
                    iEnableds[i] = listConsumePresentsStoredValueEntity[0].strEnabledsssId ||0 ;
                    iPresentsIntegrationAmounts[i] = listConsumePresentsStoredValueEntity[i].iPresentsIntegrationAmount;
                    strActivityIds[i] = $scope.yihouougaiyaoyongdehuodongId;
                    strStoredTicketIds[i] = listConsumePresentsStoredValueEntity[i].strStoredTicketId;
                }
                var data = {
                    dConsumeCashAmount:dConsumeCashAmounts.join(","),
                    iEnabled:iEnableds.join(","),
                    iPresentsIntegrationAmount:iPresentsIntegrationAmounts.join(","),
                    strStoredTicketId:strStoredTicketIds.join(","),
                    strActivityId:strActivityIds.join(",")
                }
                $http.post(remoteUrl.batchUpdateConsumePresentsStoredValueEntity,data).then(
                    function(result){
                        var rs = result.data;
                        var data = result.data;
                        var code = data.code;
                        console.info(result);
                        if (code == 1) {

                            console.info("储值修改成功")
                        } else if (code == -1) {
                            window.location.href = "/admin/login?url="
                            + window.location.pathname
                            + window.location.search
                            + window.location.hash;
                            //未登录
                        } else if (code <= -2 && code >= -7) {
                            //必填字段未填写
                            $scope.showAlert(rs.msg);
                        } else if (code == -8) {
                            //暂无数据
                            $scope.isNoData=true;
                            $scope.pageCount = 0;
                        }

                    }, function (result) {
                        var status = result.status;
                        if (status == -1) {
                            $scope.showAlert("服务器错误")
                        } else if (status >= 404 && status < 500) {
                            $scope.showAlert("请求路径错误")
                        } else if (status >= 500) {
                            $scope.showAlert("服务器错误")
                        }
                    }
                )
            },
            //消费赠送 -- 赠送积分 -- 批量修改
            batchUpdateConsumePresentsIntegrationEntity:function($scope,$http,listConsumePresentsIntegrationEntity){
                var dConsumeCashAmounts = [];
                var iEnableds = [];
                var iPresentsIntegrationAmounts = [];
                var strActivityIds=[];
                var strIntegrationIds = [];
                for(let i in listConsumePresentsIntegrationEntity){
                    dConsumeCashAmounts[i] = listConsumePresentsIntegrationEntity[i].dConsumeCashAmount;
                    iEnableds[i] = listConsumePresentsIntegrationEntity[0].strEnabledsssId ||0 ;
                    iPresentsIntegrationAmounts[i] = listConsumePresentsIntegrationEntity[i].iPresentsIntegrationAmount;
                    strActivityIds[i] = $scope.yihouougaiyaoyongdehuodongId;
                    strIntegrationIds[i] =  listConsumePresentsIntegrationEntity[i].strIntegrationId;
                }
                var data = {
                    dConsumeCashAmount:dConsumeCashAmounts.join(","),
                    iEnabled:iEnableds.join(","),
                    iPresentsIntegrationAmount:iPresentsIntegrationAmounts.join(","),
                    strIntegrationId:strIntegrationIds.join(","),
                    strActivityId:strActivityIds.join(",")
                }
                $http.post(remoteUrl.batchUpdateConsumePresentsIntegrationEntity,data).then(
                    function(result){
                        var rs = result.data;
                        var data = result.data;
                        var code = data.code;
                        console.info(result);
                        if (code == 1) {

                            console.info("积分修改成功")
                        } else if (code == -1) {
                            window.location.href = "/admin/login?url="
                            + window.location.pathname
                            + window.location.search
                            + window.location.hash;
                            //未登录
                        } else if (code <= -2 && code >= -7) {
                            //必填字段未填写
                            $scope.showAlert(rs.msg);
                        } else if (code == -8) {
                            //暂无数据
                            $scope.isNoData=true;
                            $scope.pageCount = 0;
                        }

                    }, function (result) {


                        var status = result.status;
                        if (status == -1) {
                            $scope.showAlert("服务器错误")
                        } else if (status >= 404 && status < 500) {
                            $scope.showAlert("请求路径错误")
                        } else if (status >= 500) {
                            $scope.showAlert("服务器错误")
                        }
                    }
                )
            },
            //消费赠送 -- 活动信息 -- 更新
            updateConsumePresentsActivityEntity:function($scope,$http,consumePresentsActivityEntity){
                var data = {
                    strActivityBeginTime:consumePresentsActivityEntity.strActivityBeginTime.toLocaleDateString(),
                    strActivityEndTime:consumePresentsActivityEntity.strActivityEndTime.toLocaleDateString(),
                    iActivityKinds:(function(){
                                            if(consumePresentsActivityEntity.xuyaostrActivityKinds == undefined){
                                                return consumePresentsActivityEntity.iActivityKinds
                                            }else{
                                                return consumePresentsActivityEntity.xuyaostrActivityKinds
                                            }
                                        })(),
                    strActivityName:consumePresentsActivityEntity.strActivityName,
                    strIsCumulation:consumePresentsActivityEntity.strIsCumulation,
                    strLevelsId:consumePresentsActivityEntity.strLevelsId,
                    strActivityId:$scope.yihouougaiyaoyongdehuodongId
                }
                $http.post(remoteUrl.updateConsumePresentsActivityEntity,data).then(
                    function(result){
                        var rs = result.data;
                        var data = result.data;
                        var code = data.code;
                        console.info(result)
                        if (code == 1) {

                            console.info("活动信息更新成功")
                        } else if (code == -1) {
                            window.location.href = "/admin/login?url="
                            + window.location.pathname
                            + window.location.search
                            + window.location.hash;
                            //未登录
                        } else if (code <= -2 && code >= -7) {
                            //必填字段未填写
                            $scope.showAlert(rs.msg);
                        } else if (code == -8) {
                            //暂无数据
                            $scope.isNoData=true;
                            $scope.pageCount = 0;
                        }

                    }, function (result) {


                        var status = result.status;
                        if (status == -1) {
                            $scope.showAlert("服务器错误")
                        } else if (status >= 404 && status < 500) {
                            $scope.showAlert("请求路径错误")
                        } else if (status >= 500) {
                            $scope.showAlert("服务器错误")
                        }
                    }
                )
            },

            //消费赠送 -- 活动信息 -- 查询 单条 刚新建的活动信息
            selectConsumePresentsActivityInfo:function($scope,$http){
                var data = {}
                $http.post(remoteUrl.selectConsumePresentsActivityInfo,data).then(
                    function(result){
                        var rs = result.data;
                        var data = result.data;
                        var code = data.code;
                        if (code == 1) {
                            $scope.chaxunchulaihougonggongdehuodongId = data.data.consumePresentsActivityEntity.strActivityId;
                            $scope.jifenczdyqxz();
                        } else if (code == -1) {
                            window.location.href = "/admin/login?url="
                            + window.location.pathname
                            + window.location.search
                            + window.location.hash;
                            //未登录
                        } else if (code <= -2 && code >= -7) {
                            //必填字段未填写
                            $scope.showAlert(rs.msg);
                        } else if (code == -8) {
                            //暂无数据
                            $scope.isNoData=true;
                            $scope.pageCount = 0;
                        }

                    }, function (result) {


                        var status = result.status;
                        if (status == -1) {
                            $scope.showAlert("服务器错误")
                        } else if (status >= 404 && status < 500) {
                            $scope.showAlert("请求路径错误")
                        } else if (status >= 500) {
                            $scope.showAlert("服务器错误")
                        }
                    }
                )
            },
            //消费赠送 -- 赠送抵用券 -- 批量新增
            batchInsertConsumePresentsVoucherEntity:function($scope,$http,listConsumePresentsVoucherEntity){
                var dConsumeCashAmounts = [];
                var iEnableds = [];
                var iPresentsIntegrationAmounts = [];
                var strActivityIds=[];
                var strVoucherTicketIds = [];
                for(let i in listConsumePresentsVoucherEntity){
                    dConsumeCashAmounts[i] = listConsumePresentsVoucherEntity[i].dConsumeCashAmount;
                    iEnableds[i] = listConsumePresentsVoucherEntity[0].strEnabledsssId || 0;
                    iPresentsIntegrationAmounts[i] = listConsumePresentsVoucherEntity[i].iPresentsIntegrationAmount;
                    strActivityIds[i] = $scope.chaxunchulaihougonggongdehuodongId || $scope.yihouougaiyaoyongdehuodongId;
                    strVoucherTicketIds[i] = listConsumePresentsVoucherEntity[i].strVoucherTicketId
                }
                var data = {
                    dConsumeCashAmount:dConsumeCashAmounts.join(","),
                    iEnabled:iEnableds.join(","),
                    iPresentsIntegrationAmount:iPresentsIntegrationAmounts.join(","),
                    strActivityId:strActivityIds.join(","),
                    strVoucherTicketId:strVoucherTicketIds.join(",")
                }
                $http.post(remoteUrl.batchInsertConsumePresentsVoucherEntity,data).then(
                    function(result){
                        var rs = result.data;
                        var data = result.data;
                        var code = data.code;
                        if (code == 1) {
                            if(!$scope.panduanshixinzenghaishiqitaxiaoxiugai){//修改进来的，然后执行这个方便以后删除新增的这一条数据
                                $scope.updataExpand($scope.yihouougaiyaoyongdehuodongId)
                            }
                        } else if (code == -1) {
                            window.location.href = "/admin/login?url="
                            + window.location.pathname
                            + window.location.search
                            + window.location.hash;
                            //未登录
                        } else if (code <= -2 && code >= -7) {
                            //必填字段未填写
                            $scope.showAlert(rs.msg);
                        } else if (code == -8) {
                            //暂无数据
                            $scope.isNoData=true;
                            $scope.pageCount = 0;
                        }

                    }, function (result) {


                        var status = result.status;
                        if (status == -1) {
                            $scope.showAlert("服务器错误")
                        } else if (status >= 404 && status < 500) {
                            $scope.showAlert("请求路径错误")
                        } else if (status >= 500) {
                            $scope.showAlert("服务器错误")
                        }
                    }
                )
            },
            //消费赠送 -- 赠送储值 -- 批量新增
            batchInsertConsumePresentsStoredValueEntity:function($scope,$http,listConsumePresentsStoredValueEntity){
                var dConsumeCashAmounts = [];
                var iEnableds = [];
                var iPresentsIntegrationAmounts = [];
                var strActivityIds=[];
                for(let i in listConsumePresentsStoredValueEntity){
                    dConsumeCashAmounts[i] = listConsumePresentsStoredValueEntity[i].dConsumeCashAmount;
                    iEnableds[i] = listConsumePresentsStoredValueEntity[0].strEnabledsssId ||0 ;
                    iPresentsIntegrationAmounts[i] = listConsumePresentsStoredValueEntity[i].iPresentsIntegrationAmount;
                    strActivityIds[i] = $scope.chaxunchulaihougonggongdehuodongId || $scope.yihouougaiyaoyongdehuodongId;
                }
                var data = {
                    dConsumeCashAmount:dConsumeCashAmounts.join(","),
                    iEnabled:iEnableds.join(","),
                    iPresentsIntegrationAmount:iPresentsIntegrationAmounts.join(","),
                    strActivityId:strActivityIds.join(",")
                }
                $http.post(remoteUrl.batchInsertConsumePresentsStoredValueEntity,data).then(
                    function(result){
                        var rs = result.data;
                        var data = result.data;
                        var code = data.code;
                        if (code == 1) {
                            if(!$scope.panduanshixinzenghaishiqitaxiaoxiugai){//修改进来的，然后执行这个方便以后删除新增的这一条数据
                                $scope.updataExpand($scope.yihouougaiyaoyongdehuodongId)
                            }
                        } else if (code == -1) {
                            window.location.href = "/admin/login?url="
                            + window.location.pathname
                            + window.location.search
                            + window.location.hash;
                            //未登录
                        } else if (code <= -2 && code >= -7) {
                            //必填字段未填写
                            $scope.showAlert(rs.msg);
                        } else if (code == -8) {
                            //暂无数据
                            $scope.isNoData=true;
                            $scope.pageCount = 0;
                        }

                    }, function (result) {


                        var status = result.status;
                        if (status == -1) {
                            $scope.showAlert("服务器错误")
                        } else if (status >= 404 && status < 500) {
                            $scope.showAlert("请求路径错误")
                        } else if (status >= 500) {
                            $scope.showAlert("服务器错误")
                        }
                    }
                )
            },
            //消费赠送 -- 赠送积分 -- 批量新增
            batchInsertConsumePresentsIntegrationEntity:function($scope,$http,listConsumePresentsIntegrationEntity){
                var dConsumeCashAmounts = [];
                var iEnableds = [];
                var iPresentsIntegrationAmounts = [];
                var strActivityIds=[];
                for(let i in listConsumePresentsIntegrationEntity){
                    dConsumeCashAmounts[i] = listConsumePresentsIntegrationEntity[i].dConsumeCashAmount;
                    iEnableds[i] = listConsumePresentsIntegrationEntity[0].strEnabledsssId ||0 ;
                    iPresentsIntegrationAmounts[i] = listConsumePresentsIntegrationEntity[i].iPresentsIntegrationAmount;
                    strActivityIds[i] = $scope.chaxunchulaihougonggongdehuodongId || $scope.yihouougaiyaoyongdehuodongId;
                }
                var data = {
                    dConsumeCashAmount:dConsumeCashAmounts.join(","),
                    iEnabled:iEnableds.join(","),
                    iPresentsIntegrationAmount:iPresentsIntegrationAmounts.join(","),
                    strActivityId:strActivityIds.join(",")
                }
                $http.post(remoteUrl.batchInsertConsumePresentsIntegrationEntity,data).then(
                    function(result){
                        var rs = result.data;
                        var data = result.data;
                        var code = data.code;
                        if (code == 1) {
                            if(!$scope.panduanshixinzenghaishiqitaxiaoxiugai){//修改进来的，然后执行这个方便以后删除新增的这一条数据
                                $scope.updataExpand($scope.yihouougaiyaoyongdehuodongId)
                            }
                        } else if (code == -1) {
                            window.location.href = "/admin/login?url="
                            + window.location.pathname
                            + window.location.search
                            + window.location.hash;
                            //未登录
                        } else if (code <= -2 && code >= -7) {
                            //必填字段未填写
                            $scope.showAlert(rs.msg);
                        } else if (code == -8) {
                            //暂无数据
                            $scope.isNoData=true;
                            $scope.pageCount = 0;
                        }

                    }, function (result) {


                        var status = result.status;
                        if (status == -1) {
                            $scope.showAlert("服务器错误")
                        } else if (status >= 404 && status < 500) {
                            $scope.showAlert("请求路径错误")
                        } else if (status >= 500) {
                            $scope.showAlert("服务器错误")
                        }
                    }
                )
            },
            //消费赠送 -- 活动信息 -- 新增
            insertConsumePresentsActivityEntity:function($scope,$http,consumePresentsActivityEntity){
            var data = {
                strActivityBeginTime:consumePresentsActivityEntity.strActivityBeginTime.toLocaleDateString(),
                strActivityEndTime:consumePresentsActivityEntity.strActivityEndTime.toLocaleDateString(),
                iActivityKinds:consumePresentsActivityEntity.xuyaostrActivityKinds || 0,
                strActivityName:consumePresentsActivityEntity.strActivityName,
                strIsCumulation:consumePresentsActivityEntity.strIsCumulation,
                strLevelsId:consumePresentsActivityEntity.strLevelsId
            }
            $http.post(remoteUrl.insertConsumePresentsActivityEntity,data).then(
                function(result){
                    var rs = result.data;
                    var data = result.data;
                    var code = data.code;
                    if (code == 1) {
                        activityCtrl.selectConsumePresentsActivityInfo($scope,$http);
                    } else if (code == -1) {
                        window.location.href = "/admin/login?url="
                        + window.location.pathname
                        + window.location.search
                        + window.location.hash;
                        //未登录
                    } else if (code <= -2 && code >= -7) {
                        //必填字段未填写
                        $scope.showAlert(rs.msg);
                    } else if (code == -8) {
                        //暂无数据
                        $scope.isNoData=true;
                        $scope.pageCount = 0;
                    }

                }, function (result) {


                    var status = result.status;
                    if (status == -1) {
                        $scope.showAlert("服务器错误")
                    } else if (status >= 404 && status < 500) {
                        $scope.showAlert("请求路径错误")
                    } else if (status >= 500) {
                        $scope.showAlert("服务器错误")
                    }
                }
            )
        },
            //消费赠送 -- 抵用券 -- 查询列表
            selectAllConsumePresentsVoucherEntity:function($scope,$http,strActivityId){
                var data = {
                    strActivityId:strActivityId
                }
                $http.post(remoteUrl.selectAllConsumePresentsVoucherEntity,data).then(
                    function(result){
                        var rs = result.data;
                        var data = result.data;
                        var code = data.code;
                        if (code == 1) {
                            $scope.listConsumePresentsVoucherEntity = data.data.listConsumePresentsVoucherEntity;
                            $scope.listConsumePresentsVoucherEntityDyq = [];
                            $scope.listConsumePresentsVoucherEntityDyq[0] = data.data.listConsumePresentsVoucherEntity[0];
                        } else if (code == -1) {
                            window.location.href = "/admin/login?url="
                            + window.location.pathname
                            + window.location.search
                            + window.location.hash;
                            //未登录
                        } else if (code <= -2 && code >= -7) {
                            //必填字段未填写
                            $scope.showAlert(rs.msg);
                        } else if (code == -8) {
                            //暂无数据
                            $scope.isNoData=true;
                            $scope.pageCount = 0;
                        }

                    }, function (result) {


                        var status = result.status;
                        if (status == -1) {
                            $scope.showAlert("服务器错误")
                        } else if (status >= 404 && status < 500) {
                            $scope.showAlert("请求路径错误")
                        } else if (status >= 500) {
                            $scope.showAlert("服务器错误")
                        }
                    }
                )
            },
            //消费赠送 -- 赠送储值 -- 查询列表
            selectAllConsumePresentsStoredValueEntity:function($scope,$http,strActivityId){
                var data = {
                    strActivityId:strActivityId
                }
                $http.post(remoteUrl.selectAllConsumePresentsStoredValueEntity,data).then(
                    function(result){
                        var rs = result.data;
                        var data = result.data;
                        var code = data.code;
                        if (code == 1) {
                            $scope.listConsumePresentsStoredValueEntity = data.data.listConsumePresentsStoredValueEntity;
                            $scope.listConsumePresentsStoredValueEntityCz = [];
                            $scope.listConsumePresentsStoredValueEntityCz[0] = data.data.listConsumePresentsStoredValueEntity[0];
                        } else if (code == -1) {
                            window.location.href = "/admin/login?url="
                            + window.location.pathname
                            + window.location.search
                            + window.location.hash;
                            //未登录
                        } else if (code <= -2 && code >= -7) {
                            //必填字段未填写
                            $scope.showAlert(rs.msg);
                        } else if (code == -8) {
                            //暂无数据
                            $scope.isNoData=true;
                            $scope.pageCount = 0;
                        }

                    }, function (result) {


                        var status = result.status;
                        if (status == -1) {
                            $scope.showAlert("服务器错误")
                        } else if (status >= 404 && status < 500) {
                            $scope.showAlert("请求路径错误")
                        } else if (status >= 500) {
                            $scope.showAlert("服务器错误")
                        }
                    }
                )
            },
            //消费赠送 -- 赠送积分 -- 查询 列表
            selectAllConsumePresentsIntegrationEntity:function($scope,$http,strActivityId){
                var data = {
                    strActivityId:strActivityId
                }
                $http.post(remoteUrl.selectAllConsumePresentsIntegrationEntity,data).then(
                    function(result){
                        var rs = result.data;
                        var data = result.data;
                        var code = data.code;
                        if (code == 1) {
                            $scope.listConsumePresentsIntegrationEntity = data.data.listConsumePresentsIntegrationEntity;
                            $scope.listConsumePresentsIntegrationEntityJf = [];
                            $scope.listConsumePresentsIntegrationEntityJf[0] = data.data.listConsumePresentsIntegrationEntity[0];
                        } else if (code == -1) {
                            window.location.href = "/admin/login?url="
                            + window.location.pathname
                            + window.location.search
                            + window.location.hash;
                            //未登录
                        } else if (code <= -2 && code >= -7) {
                            //必填字段未填写
                            $scope.showAlert(rs.msg);
                        } else if (code == -8) {
                            //暂无数据
                            $scope.isNoData=true;
                            $scope.pageCount = 0;
                        }

                    }, function (result) {


                        var status = result.status;
                        if (status == -1) {
                            $scope.showAlert("服务器错误")
                        } else if (status >= 404 && status < 500) {
                            $scope.showAlert("请求路径错误")
                        } else if (status >= 500) {
                            $scope.showAlert("服务器错误")
                        }
                    }
                )
            },
            //消费赠送 -- 活动信息 -- 查询单条
            selectConsumePresentsActivityEntity:function($scope,$http,strActivityId){
                var data = {
                    strActivityId:strActivityId
                }
                $http.post(remoteUrl.selectConsumePresentsActivityEntity,data).then(
                    function(result){
                        var rs = result.data;
                        var data = result.data;
                        var code = data.code;
                        if (code == 1) {
                            $scope.consumePresentsActivityEntity = data.data.consumePresentsActivityEntity;
                            $scope.consumePresentsActivityEntity.strActivityBeginTime = new Date(data.data.consumePresentsActivityEntity.strActivityBeginTime)
                            $scope.consumePresentsActivityEntity.strActivityEndTime = new Date(data.data.consumePresentsActivityEntity.strActivityEndTime)
                            //$scope.pageCount = data.data.iTotalPage;
                        } else if (code == -1) {
                            window.location.href = "/admin/login?url="
                            + window.location.pathname
                            + window.location.search
                            + window.location.hash;
                            //未登录
                        } else if (code <= -2 && code >= -7) {
                            //必填字段未填写
                            $scope.showAlert(rs.msg);
                        } else if (code == -8) {
                            //暂无数据
                            $scope.isNoData=true;
                            $scope.pageCount = 0;
                        }

                    }, function (result) {


                        var status = result.status;
                        if (status == -1) {
                            $scope.showAlert("服务器错误")
                        } else if (status >= 404 && status < 500) {
                            $scope.showAlert("请求路径错误")
                        } else if (status >= 500) {
                            $scope.showAlert("服务器错误")
                        }
                    }
                )
            },
            //消费赠送 -- 活动 -- 分页查询
            selectAllConsumePresentsActivity:function($scope,$http){
                var data = {
                    iPageNum: $scope.currentPage,
                    iPageSize: $scope.pageSize,
                    strSearchEnabledStatus:$scope.strSearchEnabledStatus,
                    strSearchMemberLevelId: $scope.strSearchMemberLevelId
                }
                $http.post(remoteUrl.selectAllConsumePresentsActivity,data).then(
                    function(result){
                        var rs = result.data;
                        var data = result.data;
                        var code = data.code;
                        if (code == 1) {
                            $scope.resultMap = data.data.resultMap;
                            $scope.pageCount = data.data.iTotalPage;
                        } else if (code == -1) {
                            window.location.href = "/admin/login?url="
                            + window.location.pathname
                            + window.location.search
                            + window.location.hash;
                            //未登录
                        } else if (code <= -2 && code >= -7) {
                            //必填字段未填写
                            $scope.showAlert(rs.msg);
                        } else if (code == -8) {
                            //暂无数据
                            $scope.isNoData=true;
                            $scope.pageCount = 0;
                        }

                    }, function (result) {


                        var status = result.status;
                        if (status == -1) {
                            $scope.showAlert("服务器错误")
                        } else if (status >= 404 && status < 500) {
                            $scope.showAlert("请求路径错误")
                        } else if (status >= 500) {
                            $scope.showAlert("服务器错误")
                        }
                    }
                )
            },
            //消费赠送 -- 活动 -- 分页删除
            deleteConsumePresentsActivityInfo:function($scope,$http,strActivityId){
                var data = {
                    strActivityId:strActivityId
                }
                $http.post(remoteUrl.deleteConsumePresentsActivityInfo,data).then(
                    function(result){
                        var rs = result.data;
                        var data = result.data;
                        var code = data.code;
                        if (code == 1) {
                            $scope.showAlert(rs.msg,function(){
                                window.location.reload()
                            })
                        } else if (code == -1) {
                            window.location.href = "/admin/login?url="
                            + window.location.pathname
                            + window.location.search
                            + window.location.hash;
                            //未登录
                        } else if (code <= -2 && code >= -7) {
                            //必填字段未填写
                            $scope.showAlert(rs.msg);
                        } else if (code == -8) {
                            //暂无数据
                            $scope.isNoData=true;
                            $scope.pageCount = 0;
                        }

                    }, function (result) {


                        var status = result.status;
                        if (status == -1) {
                            $scope.showAlert("服务器错误")
                        } else if (status >= 404 && status < 500) {
                            $scope.showAlert("请求路径错误")
                        } else if (status >= 500) {
                            $scope.showAlert("服务器错误")
                        }
                    }
                )
            },


            ////////////////////消费赠送-结束////////////////////////
            ////////////////////自定义赠送-开始////////////////////////
            customgift:function($scope,$http,$q){
                //分页查询
                $scope.currentPage = 1;
                $scope.pageSize = 5;
                $scope.strSearchEnabledStatus = null;
                $scope.strSearchMemberLevelId = null;

                $scope.actiStatus = [{id:1,name:"正常"},{id:0,name:"过期"}];;//活动状态

                $scope.isShowListMenu = [];//二级菜单显示必要的一步
                $scope.panduanshixinzenghaishiqitaxiaoxiugai = false;//判断是新增还是其它修改
                //调用会员级别查询
                activityCtrl.listAllMemberLevels($scope,$http);
                // 会员级别，活动状态赛选
                $scope.saixuanVip = function () {
                    $scope.strSearchMemberLevelId = $scope.VipGrade;
                    activityCtrl.selectAllUserDefinedPresentsActivity($scope,$http,$scope.strSearchEnabledStatus,$scope.strSearchMemberLevelId)
                }
                $scope.saixuanStu = function () {
                    $scope.strSearchEnabledStatus = $scope.actStatus;
                    activityCtrl.selectAllUserDefinedPresentsActivity($scope,$http,$scope.strSearchEnabledStatus,$scope.strSearchMemberLevelId)
                }
                //    二级菜单
                $scope.openCtrMenu = function ($index, type) {
                    for (var i = 0; i < $scope.isShowListMenu.length; i++) {
                        $scope.isShowListMenu[i] = false;
                    }
                    if (type == 'over') {
                        $scope.isShowListMenu[$index] = !$scope.isShowListMenu[$index];
                    }
                    $scope.huodongquzhigudingId = $index;
                };
                //分页查询
                $scope.onPageChange = function () {
                    // ajax request to load data
                    $scope.resultMap = {};
                    activityCtrl.selectAllUserDefinedPresentsActivity($scope,$http);

                };
                // 点击新增按钮
                $scope.newExpandinginfo = function(){
                    $scope.panduanshixinzenghaishiqitaxiaoxiugai = true;//判断修改进来还是新增进来
                    //清空所有查询的内容
                    $scope.rechargePresentsActivityEntity = {};
                    $scope.uerDefinedPresentsStoredValueEntity = {};
                    $scope.uerDefinedPresentsStoredValueEntityCz = {};
                    $scope.listUserDefinedPresentsVoucherEntity = {};
                    $scope.showExpandInfoWindow = true;
                }
                //    点击关闭按钮
                $scope.clostExpandWindow = function(){
                    //清空所有查询的内容
                    $scope.rechargePresentsActivityEntity = {};
                    $scope.uerDefinedPresentsStoredValueEntity = {};
                    $scope.uerDefinedPresentsStoredValueEntityCz = {};
                    $scope.listUserDefinedPresentsVoucherEntity = {};

                    $scope.showExpandInfoWindow = false;
                }
                //    自定义赠送点击修改按钮
                $scope.updataExpand = function(strActivityId){
                    //调用接口-活动
                    activityCtrl.selectUserDefinedPresentActivityById($scope,$http,strActivityId);
                    ////调用接口-积分
                    activityCtrl.selectAllUserDefinedPresentsIntegration($scope,$http,strActivityId);
                    ////调用接口-储值
                    activityCtrl.selectAllUserDefinedPresentsStoredValueEntity($scope,$http,strActivityId);
                    ////调用接口-抵用券
                    activityCtrl.selectAllUserDefinedPresentsVoucherEntity($scope,$http,strActivityId);
                    //显示模态框
                    $scope.panduanshixinzenghaishiqitaxiaoxiugai = false;//判断修改进来还是新增进来
                    $scope.showExpandInfoWindow = true;
                };
                //充值赠送删除
                $scope.delectExpand = function(strActivityId,strActivityName){
                    $scope.showConfirm("确认删除"+strActivityName,function(rs){
                        if(rs){
                            activityCtrl.deleteUserDefinedActivityInfo($scope,$http,strActivityId)
                        }
                    })
                }
                //模态框出现后的新增
                $scope.jianchadiyongquanpaixu = 0;
                $scope.addStoreZs = function(paixu,id){
                    $scope.gonggongschaxunhouxinzengtrActivityId = id; //取到查询出来的公共id，为以后新增做准备
                    $scope.diyongquanxinzeng = {};
                    //$scope.chuzhiweikong;
                    //if(!$scope.diyongquanweikong){
                    //    $scope.showAlert("抵用券券相关内容不能为空！");
                    //}else{
                    if(!paixu && !$scope.jianchadiyongquanpaixu){
                        $scope.jianchadiyongquanpaixu = 1;
                        if(!$scope.diyongquanweikong){
                            $scope.diyongquanweikong = $scope.listUserDefinedPresentsVoucherEntity[0];
                        }else{
                            $scope.listUserDefinedPresentsVoucherEntity[0] = $scope.diyongquanweikong;
                        }
                    }
                    $scope.showExpandInfoWindow = false;
                    $scope.showExpandInfoWindowStore = true;
                    //}

                }
                $scope.clostExpandWindowStore = function(){
                    $scope.showExpandInfoWindow = true;
                    $scope.showExpandInfoWindowStore = false;
                }
                ////    添加抵用券
                $scope.adddiyongquantianjianumber =1;
                $scope.adddiyongquan = function(){
                    if($scope.panduanshixinzenghaishiqitaxiaoxiugai){//总体新增执行的操作
                        if($scope.panduandiaoyungdiyongquanjiekou){
                            $scope.panduandiaoyungdiyongquanjiekou = false;//判断调用了储值券接口没
                            $scope.adddiyongquantianjianumber = $scope.listUserDefinedPresentsVoucherEntity.length;
                        }
                        $scope.listUserDefinedPresentsVoucherEntity[$scope.adddiyongquantianjianumber] = $scope.diyongquanxinzeng;

                    }else{
                        $scope.adddiyongquantianjianumber = $scope.listUserDefinedPresentsVoucherEntity.length;
                        var diyongquan = $scope.diyongquanxinzeng;
                        diyongquan.strEnabledsssId = $scope.listUserDefinedPresentsVoucherEntity[0].iEnabled;
                        diyongquan.strActivityId = $scope.gonggongschaxunhouxinzengtrActivityId;
                        var diyongquans = [];//为了跟后面的格式达成共识
                        diyongquans.push(diyongquan)
                        activityCtrl.batchInsertUserDefinedPresentsVoucherEntity($scope,$http,diyongquans)//抵用券新增

                        //activityCtrl.selectRechargePresentsStoredValueEntity($scope,$http);
                        $scope.listUserDefinedPresentsVoucherEntity[$scope.adddiyongquantianjianumber] = diyongquans[0];
                    }
                    $scope.adddiyongquantianjianumber += 1;
                    $scope.diyongquanweikong = {};
                    $scope.showExpandInfoWindow = true;
                    $scope.showExpandInfoWindowStore = false;
                }
                //新增
                $scope.submitExpandinfoAll = function(){
                    //充值赠送新增
                    if($scope.panduanshixinzenghaishiqitaxiaoxiugai){
                        $scope.showConfirm("确定新增？",function(rs){
                            if(rs){
                                activityCtrl.insertUserDefinedPresentsActivityInfo($scope,$http,$scope.rechargePresentsActivityEntity);
                            }
                        })
                    }else {
                        var deferred = $q.defer();
                        var promise = deferred.promise;
                        promise.then(function (result) {
                            activityCtrl.updateUserDefinedPresentsActivityInfo($scope, $http, $scope.rechargePresentsActivityEntity);//活动信息修改
                        }).then(function (result) {
                            activityCtrl.updateUserDefinedPresentsStoredValueEntity($scope,$http,$scope.uerDefinedPresentsStoredValueEntityCz)//储值券新增
                        }).then(function (result) {
                            activityCtrl.updateUserDefinedPresentsIntegration($scope,$http,$scope.uerDefinedPresentsStoredValueEntity);//积分信息新增
                        }).then(function (result) {
                            activityCtrl.batchUpdateUserDefinedPresentsVoucherEntity($scope,$http,$scope.listUserDefinedPresentsVoucherEntity);//抵用券新增
                        }).then(function (result){
                            $scope.showAlert("修改成功",function(){
                                window.location.reload();
                            })
                        },function(){
                            $scope.showAlert("修改失败",function(){
                                window.location.reload();
                            });
                        })
                        if(true){
                            deferred.resolve("执行成功");
                        }else{
                            deferred.reject("sorry, it lost!");
                        }

                    }
                };
                //其它接口的新增
                $scope.qitachaozuoxingzeng = function(){
                    var deferred = $q.defer();
                    var promise = deferred.promise;

                    promise.then(function (result) {
                        activityCtrl.insertUserDefinedPresentsStoredValueEntity($scope,$http,$scope.uerDefinedPresentsStoredValueEntityCz)//储值券新增
                    }).then(function (result) {
                        activityCtrl.insertUserDefinedPresentsIntegration($scope,$http,$scope.uerDefinedPresentsStoredValueEntity);//积分信息新增
                    }).then(function (result) {
                        activityCtrl.batchInsertUserDefinedPresentsVoucherEntity($scope,$http,$scope.listUserDefinedPresentsVoucherEntity);//抵用券新增
                    }).then(function (result){
                        $scope.showAlert("新增成功",function(){
                            window.location.reload();
                        })
                    },function(){
                        $scope.showAlert("新增失败",function(){
                            activityCtrl.deleteUserDefinedActivityInfo($scope,$http,$scope.chaxunhouxinzenggonggongstrActivityId)
                        });
                        //window.location.reload();
                    })
                    if(true){
                        deferred.resolve("执行成功");
                    }else{
                        deferred.reject("sorry, it lost!");
                    }
                }
                $scope.z_deletedyq = function(strPresentsVoucherId,strActivityId,shanchujitiao,who){
                    if($scope.panduanshixinzenghaishiqitaxiaoxiugai){//查询前面新增执行的操作
                        var diyongquan = $scope.listRechargePresentsVoucherEntity;
                        delete diyongquan[shanchujitiao];
                        $scope.listRechargePresentsVoucherEntity=diyongquan;
                    }else{
                        if($scope.listUserDefinedPresentsVoucherEntity.length == 1){
                            $scope.showAlert("最后一条数据不能删除！")
                        }else{
                            $scope.showConfirm("确认删除"+who+"?",function(rs){
                                if(rs){
                                    activityCtrl.deleteUserDefinedPresentsVoucherEntity($scope,$http,strPresentsVoucherId,strActivityId,shanchujitiao);
                                }
                            })
                        }
                    }


                }

                //自定义赠送-活动信息-分页查询
                activityCtrl.selectAllUserDefinedPresentsActivity($scope,$http);
            //抵用券维护 -- 抵用券详情 -- 分页查询
            activityCtrl.selectVoucherTicketInfo($scope,$http);
            },
            //自定义赠送 -- 抵用券 -- 更新
            batchUpdateUserDefinedPresentsVoucherEntity:function($scope,$http,listUserDefinedPresentsVoucherEntity){
                var strVoucherTicketIds = [];
                var iTotalNums = [];
                var iEnableds = [];
                var strActivityIds = [];
                var strPresentsVoucherIds = [];
                for(let i in listUserDefinedPresentsVoucherEntity){
                    strVoucherTicketIds[i] = listUserDefinedPresentsVoucherEntity[i].strVoucherTicketId;
                    iTotalNums[i] = listUserDefinedPresentsVoucherEntity[i].iTotalNum;
                    iEnableds[i] = (function(listUserDefinedPresentsVoucherEntity){
                        if( listUserDefinedPresentsVoucherEntity[0].strEnabledsssId == undefined){
                            return listUserDefinedPresentsVoucherEntity[0].iEnabled;
                        }else{
                            return listUserDefinedPresentsVoucherEntity[0].strEnabledsssId;
                        }
                    })(listUserDefinedPresentsVoucherEntity),
                    strActivityIds[i] = $scope.rechargePresentsActivityEntity.strActivityId;
                    strPresentsVoucherIds[i] = listUserDefinedPresentsVoucherEntity[i].strPresentsVoucherId;
                }
                var data = {
                    strVoucherTicketId:strVoucherTicketIds.join(","),
                    iTotalNum:iTotalNums.join(","),
                    iEnabled:iEnableds.join(","),
                    strPresentsVoucherId:strPresentsVoucherIds.join(","),
                    strActivityId:strActivityIds.join(",")
                };
                $http.post(remoteUrl.batchUpdateUserDefinedPresentsVoucherEntity,data).then(
                    function(result){
                        var rs = result.data;
                        var data = result.data;
                        var code = data.code;
                        if (code == 1) {
                            //activityCtrl.selectUserDefinedPresentsActivityEntity($scope,$http);//调用刚刚新增的内容的查询接口
                        } else if (code == -1) {
                            window.location.href = "/admin/login?url="
                            + window.location.pathname
                            + window.location.search
                            + window.location.hash;
                            //未登录
                        } else if (code <= -2 && code >= -7) {
                            //必填字段未填写
                            $scope.showAlert(rs.msg);
                        } else if (code == -8) {
                            //暂无数据
                            $scope.isNoData=true;
                            $scope.pageCount = 0;
                        }

                    }, function (result) {


                        var status = result.status;
                        if (status == -1) {
                            $scope.showAlert("服务器错误")
                        } else if (status >= 404 && status < 500) {
                            $scope.showAlert("请求路径错误")
                        } else if (status >= 500) {
                            $scope.showAlert("服务器错误")
                        }
                    }
                )
            },
            //自定义赠送 -- 储值券 -- 更新
            updateUserDefinedPresentsStoredValueEntity:function($scope,$http,uerDefinedPresentsStoredValueEntityCz){
                var data = {
                    dPresentsAmount:uerDefinedPresentsStoredValueEntityCz.dPresentsAmount,
                    iEnabled:(function(uerDefinedPresentsStoredValueEntityCz){
                        if(uerDefinedPresentsStoredValueEntityCz.strEnabledsssId == undefined){
                            return uerDefinedPresentsStoredValueEntityCz.iEnabled;
                        }else{
                            return uerDefinedPresentsStoredValueEntityCz.strEnabledsssId;
                        }
                    })(uerDefinedPresentsStoredValueEntityCz),
                    strActivityId:$scope.rechargePresentsActivityEntity.strActivityId,
                    strPresentsStoredValueId:uerDefinedPresentsStoredValueEntityCz.strPresentsStoredValueId
                };
                $http.post(remoteUrl.updateUserDefinedPresentsStoredValueEntity,data).then(
                    function(result){
                        var rs = result.data;
                        var data = result.data;
                        var code = data.code;
                        if (code == 1) {
                            //activityCtrl.selectUserDefinedPresentsActivityEntity($scope,$http);//调用刚刚新增的内容的查询接口
                        } else if (code == -1) {
                            window.location.href = "/admin/login?url="
                            + window.location.pathname
                            + window.location.search
                            + window.location.hash;
                            //未登录
                        } else if (code <= -2 && code >= -7) {
                            //必填字段未填写
                            $scope.showAlert(rs.msg);
                        } else if (code == -8) {
                            //暂无数据
                            $scope.isNoData=true;
                            $scope.pageCount = 0;
                        }

                    }, function (result) {


                        var status = result.status;
                        if (status == -1) {
                            $scope.showAlert("服务器错误")
                        } else if (status >= 404 && status < 500) {
                            $scope.showAlert("请求路径错误")
                        } else if (status >= 500) {
                            $scope.showAlert("服务器错误")
                        }
                    }
                )
            },
            //自定义赠送 -- 积分 -- 更新
            updateUserDefinedPresentsIntegration:function($scope,$http,rechargePresentsActivityEntity){
                var data = {
                    iPresentsIntegration:rechargePresentsActivityEntity.iPresentsIntegration,
                    iEnabled:(function(rechargePresentsActivityEntity){
                                if(rechargePresentsActivityEntity.strEnabledsssId == undefined){
                                    return rechargePresentsActivityEntity.iEnabled;
                                }else{
                                  return rechargePresentsActivityEntity.strEnabledsssId;
                                }
                            })(rechargePresentsActivityEntity),
                    strActivityId:$scope.rechargePresentsActivityEntity.strActivityId,
                    strPresentsIntegrationId:rechargePresentsActivityEntity.strPresentsIntegrationId
                };
                $http.post(remoteUrl.updateUserDefinedPresentsIntegration,data).then(
                    function(result){
                        var rs = result.data;
                        var data = result.data;
                        var code = data.code;
                        if (code == 1) {
                            //activityCtrl.selectUserDefinedPresentsActivityEntity($scope,$http);//调用刚刚新增的内容的查询接口
                        } else if (code == -1) {
                            window.location.href = "/admin/login?url="
                            + window.location.pathname
                            + window.location.search
                            + window.location.hash;
                            //未登录
                        } else if (code <= -2 && code >= -7) {
                            //必填字段未填写
                            $scope.showAlert(rs.msg);
                        } else if (code == -8) {
                            //暂无数据
                            $scope.isNoData=true;
                            $scope.pageCount = 0;
                        }

                    }, function (result) {


                        var status = result.status;
                        if (status == -1) {
                            $scope.showAlert("服务器错误")
                        } else if (status >= 404 && status < 500) {
                            $scope.showAlert("请求路径错误")
                        } else if (status >= 500) {
                            $scope.showAlert("服务器错误")
                        }
                    }
                )
            },
            //自定义赠送 -- 活动信息 -- 更新
            updateUserDefinedPresentsActivityInfo:function($scope,$http,rechargePresentsActivityEntity){
                var data = {
                    strActivityBeginTime:rechargePresentsActivityEntity.strActivityBeginTime.toLocaleDateString(),
                    strActivityEndTime:rechargePresentsActivityEntity.strActivityEndTime.toLocaleDateString(),
                    strActivityName:rechargePresentsActivityEntity.strActivityName,
                    strActivityId:rechargePresentsActivityEntity.strActivityId,
                    strLevelsId:rechargePresentsActivityEntity.strLevelsId
                };
                $http.post(remoteUrl.updateUserDefinedPresentsActivityInfo,data).then(
                    function(result){
                        var rs = result.data;
                        var data = result.data;
                        var code = data.code;
                        if (code == 1) {
                            //activityCtrl.selectUserDefinedPresentsActivityEntity($scope,$http);//调用刚刚新增的内容的查询接口
                        } else if (code == -1) {
                            window.location.href = "/admin/login?url="
                            + window.location.pathname
                            + window.location.search
                            + window.location.hash;
                            //未登录
                        } else if (code <= -2 && code >= -7) {
                            //必填字段未填写
                            $scope.showAlert(rs.msg);
                        } else if (code == -8) {
                            //暂无数据
                            $scope.isNoData=true;
                            $scope.pageCount = 0;
                        }

                    }, function (result) {


                        var status = result.status;
                        if (status == -1) {
                            $scope.showAlert("服务器错误")
                        } else if (status >= 404 && status < 500) {
                            $scope.showAlert("请求路径错误")
                        } else if (status >= 500) {
                            $scope.showAlert("服务器错误")
                        }
                    }
                )
            },

            //自定义赠送 -- 赠送抵用券 -- 删除
            deleteUserDefinedPresentsVoucherEntity:function($scope,$http,strPresentsVoucherId,strActivityId){
                var data = {
                    strPresentsVoucherId:strPresentsVoucherId
                };
                $http.post(remoteUrl.deleteUserDefinedPresentsVoucherEntity,data).then(
                    function(result){
                        var rs = result.data;
                        var data = result.data;
                        var code = data.code;
                        if (code == 1) {
                            $scope.showAlert("删除成功！",function(){
                                $scope.updataExpand(strActivityId);
                            });
                        } else if (code == -1) {
                            window.location.href = "/admin/login?url="
                            + window.location.pathname
                            + window.location.search
                            + window.location.hash;
                            //未登录
                        } else if (code <= -2 && code >= -7) {
                            //必填字段未填写
                            $scope.showAlert(rs.msg);
                        } else if (code == -8) {
                            //暂无数据
                            $scope.isNoData=true;
                            $scope.pageCount = 0;
                        }

                    }, function (result) {


                        var status = result.status;
                        if (status == -1) {
                            $scope.showAlert("服务器错误")
                        } else if (status >= 404 && status < 500) {
                            $scope.showAlert("请求路径错误")
                        } else if (status >= 500) {
                            $scope.showAlert("服务器错误")
                        }
                    }
                )
            },
            //自定义赠送 -- 分页显示 --删除
            deleteUserDefinedActivityInfo:function($scope,$http,strActivityId){
                var data = {
                    strActivityId:strActivityId
                };
                $http.post(remoteUrl.deleteUserDefinedActivityInfo,data).then(
                    function(result){
                        var rs = result.data;
                        var data = result.data;
                        var code = data.code;
                        if (code == 1) {
                          $scope.showAlert("删除成功！",function(){
                              window.location.reload();
                          });
                        } else if (code == -1) {
                            window.location.href = "/admin/login?url="
                            + window.location.pathname
                            + window.location.search
                            + window.location.hash;
                            //未登录
                        } else if (code <= -2 && code >= -7) {
                            //必填字段未填写
                            $scope.showAlert(rs.msg);
                        } else if (code == -8) {
                            //暂无数据
                            $scope.isNoData=true;
                            $scope.pageCount = 0;
                        }

                    }, function (result) {


                        var status = result.status;
                        if (status == -1) {
                            $scope.showAlert("服务器错误")
                        } else if (status >= 404 && status < 500) {
                            $scope.showAlert("请求路径错误")
                        } else if (status >= 500) {
                            $scope.showAlert("服务器错误")
                        }
                    }
                )
            },

            //自定义赠送 -- 抵用券 -- 新增
            batchInsertUserDefinedPresentsVoucherEntity:function($scope,$http,listUserDefinedPresentsVoucherEntity){
                var strVoucherTicketIds = [];
                var iTotalNums = [];
                var iEnableds = [];
                var strActivityIds = [];
                for(let i in listUserDefinedPresentsVoucherEntity){
                    strVoucherTicketIds[i] = listUserDefinedPresentsVoucherEntity[i].strVoucherTicketId;
                    iTotalNums[i] = listUserDefinedPresentsVoucherEntity[i].iTotalNum;
                    iEnableds[i] = listUserDefinedPresentsVoucherEntity[0].strEnabledsssId || 0;
                    strActivityIds[i] = $scope.chaxunhouxinzenggonggongstrActivityId || listUserDefinedPresentsVoucherEntity[0].strActivityId;
                }
                var data = {
                    strVoucherTicketId:strVoucherTicketIds.join(","),
                    iTotalNum:iTotalNums.join(","),
                    iEnabled:iEnableds.join(","),
                    strActivityId:strActivityIds.join(",")
                };
                $http.post(remoteUrl.batchInsertUserDefinedPresentsVoucherEntity,data).then(
                    function(result){
                        var rs = result.data;
                        var data = result.data;
                        var code = data.code;
                        if (code == 1) {
                            if(!$scope.panduanshixinzenghaishiqitaxiaoxiugai){
                                $scope.updataExpand(listUserDefinedPresentsVoucherEntity[0].strActivityId);//小更新成功后能显示模态框
                            }

                        } else if (code == -1) {
                            window.location.href = "/admin/login?url="
                            + window.location.pathname
                            + window.location.search
                            + window.location.hash;
                            //未登录
                        } else if (code <= -2 && code >= -7) {
                            //必填字段未填写
                            $scope.showAlert(rs.msg);
                        } else if (code == -8) {
                            //暂无数据
                            $scope.isNoData=true;
                            $scope.pageCount = 0;
                        }

                    }, function (result) {


                        var status = result.status;
                        if (status == -1) {
                            $scope.showAlert("服务器错误")
                        } else if (status >= 404 && status < 500) {
                            $scope.showAlert("请求路径错误")
                        } else if (status >= 500) {
                            $scope.showAlert("服务器错误")
                        }
                    }
                )
            },
            //自定义赠送 -- 储值券 -- 新增
            insertUserDefinedPresentsStoredValueEntity:function($scope,$http,uerDefinedPresentsStoredValueEntityCz){
                var data = {
                    dPresentsAmount:uerDefinedPresentsStoredValueEntityCz.dPresentsAmount,
                    iEnabled:uerDefinedPresentsStoredValueEntityCz.strEnabledsssId || 0,
                    strActivityId:$scope.chaxunhouxinzenggonggongstrActivityId
                };
                $http.post(remoteUrl.insertUserDefinedPresentsStoredValueEntity,data).then(
                    function(result){
                        var rs = result.data;
                        var data = result.data;
                        var code = data.code;
                        if (code == 1) {
                        } else if (code == -1) {
                            window.location.href = "/admin/login?url="
                            + window.location.pathname
                            + window.location.search
                            + window.location.hash;
                            //未登录
                        } else if (code <= -2 && code >= -7) {
                            //必填字段未填写
                            $scope.showAlert(rs.msg);
                        } else if (code == -8) {
                            //暂无数据
                            $scope.isNoData=true;
                            $scope.pageCount = 0;
                        }

                    }, function (result) {


                        var status = result.status;
                        if (status == -1) {
                            $scope.showAlert("服务器错误")
                        } else if (status >= 404 && status < 500) {
                            $scope.showAlert("请求路径错误")
                        } else if (status >= 500) {
                            $scope.showAlert("服务器错误")
                        }
                    }
                )
            },
            //自定义赠送 -- 积分信息 -- 新增
            insertUserDefinedPresentsIntegration:function($scope,$http,uerDefinedPresentsStoredValueEntity){
                var data = {
                    iPresentsIntegration:uerDefinedPresentsStoredValueEntity.iPresentsIntegration,
                    iEnabled:uerDefinedPresentsStoredValueEntity.strEnabledsssId || 0,
                    strActivityId:$scope.chaxunhouxinzenggonggongstrActivityId
                };
                $http.post(remoteUrl.insertUserDefinedPresentsIntegration,data).then(
                    function(result){
                        var rs = result.data;
                        var data = result.data;
                        var code = data.code;
                        if (code == 1) {
                        } else if (code == -1) {
                            window.location.href = "/admin/login?url="
                            + window.location.pathname
                            + window.location.search
                            + window.location.hash;
                            //未登录
                        } else if (code <= -2 && code >= -7) {
                            //必填字段未填写
                            $scope.showAlert(rs.msg);
                        } else if (code == -8) {
                            //暂无数据
                            $scope.isNoData=true;
                            $scope.pageCount = 0;
                        }

                    }, function (result) {


                        var status = result.status;
                        if (status == -1) {
                            $scope.showAlert("服务器错误")
                        } else if (status >= 404 && status < 500) {
                            $scope.showAlert("请求路径错误")
                        } else if (status >= 500) {
                            $scope.showAlert("服务器错误")
                        }
                    }
                )
            },
            //自定义赠送 -- 活动信息 -- 新增
            insertUserDefinedPresentsActivityInfo:function($scope,$http,rechargePresentsActivityEntity){
                var data = {
                    strActivityBeginTime:rechargePresentsActivityEntity.strActivityBeginTime.toLocaleDateString(),
                    strActivityEndTime:rechargePresentsActivityEntity.strActivityEndTime.toLocaleDateString(),
                    strActivityName:rechargePresentsActivityEntity.strActivityName,
                    strLevelsId:rechargePresentsActivityEntity.strLevelsId
                };
                $http.post(remoteUrl.insertUserDefinedPresentsActivityInfo,data).then(
                    function(result){
                        var rs = result.data;
                        var data = result.data;
                        var code = data.code;
                        if (code == 1) {
                            activityCtrl.selectUserDefinedPresentsActivityEntity($scope,$http);//调用刚刚新增的内容的查询接口
                        } else if (code == -1) {
                            window.location.href = "/admin/login?url="
                            + window.location.pathname
                            + window.location.search
                            + window.location.hash;
                            //未登录
                        } else if (code <= -2 && code >= -7) {
                            //必填字段未填写
                            $scope.showAlert(rs.msg);
                        } else if (code == -8) {
                            //暂无数据
                            $scope.isNoData=true;
                            $scope.pageCount = 0;
                        }

                    }, function (result) {


                        var status = result.status;
                        if (status == -1) {
                            $scope.showAlert("服务器错误")
                        } else if (status >= 404 && status < 500) {
                            $scope.showAlert("请求路径错误")
                        } else if (status >= 500) {
                            $scope.showAlert("服务器错误")
                        }
                    }
                )
            },
            //自定义赠送 -- 活动信息 -- 新建记录查询单条
            selectUserDefinedPresentsActivityEntity:function($scope,$http){
                var data = {}
                $http.post(remoteUrl.selectUserDefinedPresentsActivityEntity,data).then(
                    function(result){
                        var rs = result.data;
                        var data = result.data;
                        var code = data.code;
                        if (code == 1) {
                            $scope.chaxunhouxinzenggonggongstrActivityId = data.data.userDefinedPresentsActivityEntity.strActivityId;
                            $scope.qitachaozuoxingzeng();
                        } else if (code == -1) {
                            window.location.href = "/admin/login?url="
                            + window.location.pathname
                            + window.location.search
                            + window.location.hash;
                            //未登录
                        } else if (code <= -2 && code >= -7) {
                            //必填字段未填写
                            $scope.showAlert(rs.msg);
                        } else if (code == -8) {
                            //暂无数据
                            $scope.isNoData=true;
                            $scope.pageCount = 0;
                        }

                    }, function (result) {


                        var status = result.status;
                        if (status == -1) {
                            $scope.showAlert("服务器错误")
                        } else if (status >= 404 && status < 500) {
                            $scope.showAlert("请求路径错误")
                        } else if (status >= 500) {
                            $scope.showAlert("服务器错误")
                        }
                    }
                )
            },
            //自定义赠送 --赠送抵用券 --查询
            selectAllUserDefinedPresentsVoucherEntity:function($scope,$http,strActivityId){
                var data = {
                    strActivityId:strActivityId
                }
                $http.post(remoteUrl.selectAllUserDefinedPresentsVoucherEntity,data).then(
                    function(result){
                        var rs = result.data;
                        var data = result.data;
                        var code = data.code;
                        if (code == 1) {
                            $scope.listUserDefinedPresentsVoucherEntity = data.data.listUserDefinedPresentsVoucherEntity;
                            $scope.listUserDefinedPresentsVoucherEntitydyq = [];
                            $scope.listUserDefinedPresentsVoucherEntitydyq[0] = data.data.listUserDefinedPresentsVoucherEntity[0];
                        } else if (code == -1) {
                            window.location.href = "/admin/login?url="
                            + window.location.pathname
                            + window.location.search
                            + window.location.hash;
                            //未登录
                        } else if (code <= -2 && code >= -7) {
                            //必填字段未填写
                            $scope.showAlert(rs.msg);
                        } else if (code == -8) {
                            //暂无数据
                            $scope.isNoData=true;
                            $scope.pageCount = 0;
                        }

                    }, function (result) {


                        var status = result.status;
                        if (status == -1) {
                            $scope.showAlert("服务器错误")
                        } else if (status >= 404 && status < 500) {
                            $scope.showAlert("请求路径错误")
                        } else if (status >= 500) {
                            $scope.showAlert("服务器错误")
                        }
                    }
                )
            },
            //自定义赠送 -- 赠送储值券 -- 查询单条
            selectAllUserDefinedPresentsStoredValueEntity:function($scope,$http,strActivityId){
                var data = {
                    strActivityId:strActivityId
                }
                $http.post(remoteUrl.selectAllUserDefinedPresentsStoredValueEntity,data).then(
                    function(result){
                        var rs = result.data;
                        var data = result.data;
                        var code = data.code;
                        if (code == 1) {
                            $scope.uerDefinedPresentsStoredValueEntityCz = data.data.uerDefinedPresentsStoredValueEntity;
                            $scope.uerDefinedPresentsStoredValueEntityczq = [];
                            $scope.uerDefinedPresentsStoredValueEntityczq[0] = data.data.uerDefinedPresentsStoredValueEntity;
                        } else if (code == -1) {
                            window.location.href = "/admin/login?url="
                            + window.location.pathname
                            + window.location.search
                            + window.location.hash;
                            //未登录
                        } else if (code <= -2 && code >= -7) {
                            //必填字段未填写
                            $scope.showAlert(rs.msg);
                        } else if (code == -8) {
                            //暂无数据
                            $scope.isNoData=true;
                            $scope.pageCount = 0;
                        }

                    }, function (result) {


                        var status = result.status;
                        if (status == -1) {
                            $scope.showAlert("服务器错误")
                        } else if (status >= 404 && status < 500) {
                            $scope.showAlert("请求路径错误")
                        } else if (status >= 500) {
                            $scope.showAlert("服务器错误")
                        }
                    }
                )
            },
            //自定义赠送 -- 赠送积分 -- 查询单条
            selectAllUserDefinedPresentsIntegration:function($scope,$http,strActivityId){
                var data = {
                    strActivityId:strActivityId
                }
                $http.post(remoteUrl.selectAllUserDefinedPresentsIntegration,data).then(
                    function(result){
                        var rs = result.data;
                        var data = result.data;
                        var code = data.code;
                        if (code == 1) {
                            $scope.uerDefinedPresentsStoredValueEntity = data.data.uerDefinedPresentsStoredValueEntity;
                        } else if (code == -1) {
                            window.location.href = "/admin/login?url="
                            + window.location.pathname
                            + window.location.search
                            + window.location.hash;
                            //未登录
                        } else if (code <= -2 && code >= -7) {
                            //必填字段未填写
                            $scope.showAlert(rs.msg);
                        } else if (code == -8) {
                            //暂无数据
                            $scope.isNoData=true;
                            $scope.pageCount = 0;
                        }

                    }, function (result) {


                        var status = result.status;
                        if (status == -1) {
                            $scope.showAlert("服务器错误")
                        } else if (status >= 404 && status < 500) {
                            $scope.showAlert("请求路径错误")
                        } else if (status >= 500) {
                            $scope.showAlert("服务器错误")
                        }
                    }
                )
            },
            //自定义赠送 -- 活动信息 -- 查询单条
            selectUserDefinedPresentActivityById:function($scope,$http,strActivityId){
                var data = {
                    strActivityId:strActivityId
                }
                $http.post(remoteUrl.selectUserDefinedPresentActivityById,data).then(
                    function(result){
                        var rs = result.data;
                        var data = result.data;
                        var code = data.code;
                        if (code == 1) {
                            $scope.rechargePresentsActivityEntity = data.data.rechargePresentsActivityEntity;
                            $scope.rechargePresentsActivityEntity.strActivityBeginTime = new Date($scope.rechargePresentsActivityEntity.strActivityBeginTime)
                            $scope.rechargePresentsActivityEntity.strActivityEndTime = new Date($scope.rechargePresentsActivityEntity.strActivityEndTime)
                        } else if (code == -1) {
                            window.location.href = "/admin/login?url="
                            + window.location.pathname
                            + window.location.search
                            + window.location.hash;
                            //未登录
                        } else if (code <= -2 && code >= -7) {
                            //必填字段未填写
                            $scope.showAlert(rs.msg);
                        } else if (code == -8) {
                            //暂无数据
                            $scope.isNoData=true;
                            $scope.pageCount = 0;
                        }

                    }, function (result) {


                        var status = result.status;
                        if (status == -1) {
                            $scope.showAlert("服务器错误")
                        } else if (status >= 404 && status < 500) {
                            $scope.showAlert("请求路径错误")
                        } else if (status >= 500) {
                            $scope.showAlert("服务器错误")
                        }
                    }
                )
            },
            //自定义赠送-活动信息-分页查询
            selectAllUserDefinedPresentsActivity:function($scope,$http){
              var data = {
                  iPageNum: $scope.currentPage,
                  iPageSize: $scope.pageSize,
                  strSearchEnabledStatus:$scope.strSearchEnabledStatus,
                  strSearchMemberLevelId: $scope.strSearchMemberLevelId
              }
                $http.post(remoteUrl.selectAllUserDefinedPresentsActivity,data).then(
                    function(result){
                        var rs = result.data;
                        var data = result.data;
                        var code = data.code;
                        if (code == 1) {
                            $scope.resultMap = data.data.resultMap;
                            $scope.pageCount = data.data.iTotalPage;
                        } else if (code == -1) {
                            window.location.href = "/admin/login?url="
                            + window.location.pathname
                            + window.location.search
                            + window.location.hash;
                            //未登录
                        } else if (code <= -2 && code >= -7) {
                            //必填字段未填写
                            $scope.showAlert(rs.msg);
                        } else if (code == -8) {
                            //暂无数据
                            $scope.isNoData=true;
                            $scope.pageCount = 0;
                        }

                    }, function (result) {


                        var status = result.status;
                        if (status == -1) {
                            $scope.showAlert("服务器错误")
                        } else if (status >= 404 && status < 500) {
                            $scope.showAlert("请求路径错误")
                        } else if (status >= 500) {
                            $scope.showAlert("服务器错误")
                        }
                    }
                )
            },











            ////////////////////自定义赠送-结束////////////////////////

            ////////////////////充值赠送-开始////////////////////////
            recharge:function($scope,$http,$q){
                $scope.jifendiaoyongchenggong = null;
                $scope.diyongquandiaoyongchenggong = null;
                $scope.chuzhiquandiaoyongchenggong =null;//新增部分，判断是否全部调用成功

                $scope.gonggongstrActivityId = null;//取到查询出来公共id
                $scope.panduanshixinzenghaishiqitaxiaoxiugai = false;//判断是新增还是其它小修改
                $scope.panduandiaoyungchuzhiquanjiekou = false;//判断是否调用了储值券接口没
                $scope.panduandiaoyungdiyongquanjiekou = false;//判断是否调用了抵用券接口没
                $scope.iIsValid=[{"id":1,"name":"启用"},{"id":0,"name":"禁用"}];
                $scope.currentPage = 1;
                $scope.pageSize = 5;
                $scope.isShowListMenu = [];//二级菜单显示必要的一步
                $scope.strSearchEnabledStatus = null;
                $scope.strSearchEnabledStatus = null;
                //活动状态判断
                $scope.actiStatus = [{id:"NORMAL",name:"正常"},{id:"EXPIRED",name:"过期"}];
                // 点击新增按钮
                $scope.newExpandinginfo = function(){
                    $scope.addchuziquantianjianumber =1; //储值券中取值的判断
                    $scope.adddiyongquantianjianumber =1; //抵用券取值的判断
                    //清空所有查询的内容
                    $scope.rechargePresentsActivityEntity = {};
                    $scope.listRechargePresentsIntegrationEntity = {};
                    $scope.listRechargePresentsStoredValueEntity = {};
                    $scope.listRechargePresentsVoucherEntity = {};
                    $scope.showExpandInfoWindow = true;

                    $scope.panduanshixinzenghaishiqitaxiaoxiugai = true;//判断是新增还是其它小修改
                }
                //分页查询
                $scope.onPageChange = function () {
                    // ajax request to load data
                    $scope.resultMap = {};
                    activityCtrl.selectRechargePresentsActivityInfo($scope,$http);

                };
            //    点击关闭按钮
                $scope.clostExpandWindow = function(){
                    $scope.panduandiaoyungchuzhiquanjiekou = false;//判断是否调用了储值券接口没
                    $scope.panduandiaoyungdiyongquanjiekou = false;//判断是否调用了抵用券接口没

                    //清空所有查询的内容
                    $scope.rechargePresentsActivityEntity = {};
                    $scope.listRechargePresentsIntegrationEntity = {};
                    $scope.listRechargePresentsStoredValueEntity = {};
                    $scope.listRechargePresentsVoucherEntity = {};

                    $scope.panduanshixinzenghaishiqitaxiaoxiugai = false;//判断是新增还是其它小修改



                    $scope.showExpandInfoWindow = false;
                }
            // 分页查询赛选
                $scope.saixuanVip = function () {
                    $scope.strSearchMemberLevel = $scope.VipGrade;
                    activityCtrl.selectRechargePresentsActivityInfo($scope,$http,$scope.strSearchEnabledStatus,$scope.strSearchMemberLevel)
                }
                $scope.saixuanStu = function () {
                    $scope.strSearchEnabledStatus = $scope.actStatus;
                    activityCtrl.selectRechargePresentsActivityInfo($scope,$http,$scope.strSearchEnabledStatus,$scope.strSearchMemberLevel)
                }

                // 调用充值赠送 -- 分页查询
                activityCtrl.selectRechargePresentsActivityInfo($scope,$http)
                //调用会员级别查询
                activityCtrl.listAllMemberLevels($scope,$http)
                //抵用券维护 -- 抵用券详情 -- 分页查询
                activityCtrl.selectVoucherTicketInfo($scope,$http);
                //    二级菜单
                $scope.openCtrMenu = function ($index, type) {
                    for (var i = 0; i < $scope.isShowListMenu.length; i++) {
                        $scope.isShowListMenu[i] = false;
                    }
                    if (type == 'over') {
                        $scope.isShowListMenu[$index] = !$scope.isShowListMenu[$index];
                    }
                    $scope.huodongquzhigudingId = $index;
                };
                //充值赠送删除
                $scope.delectExpand = function(strActivityId,strActivityName){
                    $scope.showConfirm("确认删除"+strActivityName,function(rs){
                        if(rs){
                            activityCtrl.deleteRechargePresentsActivityInfo($scope,$http,strActivityId)
                        }
                    })
                }
            //    充值赠送修改
                $scope.updataExpand = function(strActivityId){
                    //显示模态框
                    $scope.panduanshixinzenghaishiqitaxiaoxiugai = false;
                    $scope.showExpandInfoWindow = true;
                    //调用接口-活动
                    activityCtrl.selectAllRechargePresentsActivityEntity($scope,$http,strActivityId);
                    //调用接口-积分
                    activityCtrl.selectAllRechargePresentsIntegration($scope,$http,strActivityId);
                    //调用接口-储值
                    activityCtrl.selectAllRechargePresentsStoredValue($scope,$http,strActivityId);
                    //调用接口-抵用券
                    activityCtrl.selectAllRechargePresentsVoucher($scope,$http,strActivityId);
                };

            //    添加储值券
                $scope.addchuziquantianjianumber =1;
                $scope.adddichuzhiquan = function(){
                    if($scope.panduanshixinzenghaishiqitaxiaoxiugai){//总体新增执行的操作
                        if($scope.panduandiaoyungchuzhiquanjiekou){
                            $scope.panduandiaoyungchuzhiquanjiekou = false;//判断调用了储值券接口没
                            $scope.addchuziquantianjianumber = $scope.listRechargePresentsStoredValueEntity.length;
                        }
                        $scope.listRechargePresentsStoredValueEntity[$scope.addchuziquantianjianumber] = $scope.addchuziquantianjia;

                    }else{
                        $scope.addchuziquantianjianumber = $scope.listRechargePresentsStoredValueEntity.length;
                        var chuzhiquan = $scope.addchuziquantianjia;
                        chuzhiquan.strEnabledsssId = $scope.listRechargePresentsStoredValueEntityCzq[0].iEnabled;
                        chuzhiquan.strActivityId = $scope.gonggongstrActivityId;
                        var chuzhiquans = [];//为了跟后面的格式达成共识
                        chuzhiquans.push(chuzhiquan)
                        activityCtrl.batchInsertRechargePresentsStoredValue($scope,$http,chuzhiquans)//储值券新增

                        //activityCtrl.selectRechargePresentsStoredValueEntity($scope,$http);
                        $scope.listRechargePresentsStoredValueEntity[$scope.addchuziquantianjianumber] = chuzhiquans[0];

                    }

                    $scope.addchuziquantianjianumber += 1;
                    $scope.chuzhiweikong = {};
                    $scope.showExpandInfoWindow = true;
                    $scope.showExpandInfoWindowCz = false;
                };
            //    添加抵用券
                $scope.adddiyongquantianjianumber =1;
                $scope.adddiyongquan = function(){
                    if($scope.panduanshixinzenghaishiqitaxiaoxiugai){//总体新增执行的操作
                        if($scope.panduandiaoyungdiyongquanjiekou){
                            $scope.panduandiaoyungdiyongquanjiekou = false;//判断调用了储值券接口没
                            $scope.adddiyongquantianjianumber = $scope.listRechargePresentsVoucherEntity.length;
                        }
                        $scope.listRechargePresentsVoucherEntity[$scope.adddiyongquantianjianumber] = $scope.diyongquanxinzeng;

                    }else{
                        $scope.adddiyongquantianjianumber = $scope.listRechargePresentsVoucherEntity.length;
                        var diyongquan = $scope.diyongquanxinzeng;
                        diyongquan.strEnabledsssId = $scope.listRechargePresentsVoucherEntitydyq[0].iEnabled;
                        diyongquan.strActivityId = $scope.gonggongstrActivityId;
                        var diyongquans = [];//为了跟后面的格式达成共识
                        diyongquans.push(diyongquan)
                        activityCtrl.batchInsertRechargePresentsVoucher($scope,$http,diyongquans)//储值券新增

                        //activityCtrl.selectRechargePresentsStoredValueEntity($scope,$http);
                        $scope.listRechargePresentsVoucherEntity[$scope.adddiyongquantianjianumber] = diyongquans[0];
                    }
                    $scope.adddiyongquantianjianumber += 1;
                    $scope.diyongquanweikong = {};
                    $scope.showExpandInfoWindow = true;
                    $scope.showExpandInfoWindowStore = false;
                }

            //    调用抵用券模态框
                $scope.jianchadiyongquanpaixu = 0;
                $scope.addStoreZs = function(paixu,id){
                    $scope.gonggongstrActivityId = id; //取到查询出来的公共id，为以后新增做准备
                    $scope.diyongquanxinzeng = {};
                    //$scope.chuzhiweikong;
                    //if(!$scope.diyongquanweikong){
                    //    $scope.showAlert("抵用券券相关内容不能为空！");
                    //}else{
                        if(!paixu && !$scope.jianchadiyongquanpaixu){
                            $scope.jianchadiyongquanpaixu = 1;
                            if(!$scope.diyongquanweikong){
                                $scope.diyongquanweikong = $scope.listRechargePresentsVoucherEntity[0];
                            }else{
                                $scope.listRechargePresentsVoucherEntity[0] = $scope.diyongquanweikong;
                            }
                        }
                        $scope.showExpandInfoWindow = false;
                        $scope.showExpandInfoWindowStore = true;
                    //}

                }
                $scope.clostExpandWindowStore = function(){
                    $scope.showExpandInfoWindow = true;
                    $scope.showExpandInfoWindowStore = false;
                }
                //    调用储值券模态框
                $scope.jianchachuzhipaixu = 0;
                $scope.addStoreCz = function(paixu,id){
                    $scope.gonggongstrActivityId = id //取到id值
                    $scope.addchuziquantianjia = {};
                    //$scope.chuzhiweikong;
                    //if(!$scope.listRechargePresentsStoredValueEntity[0]){
                    //    $scope.showAlert("储值券相关内容不能为空！");
                    //}else{

                        if(!paixu && !$scope.jianchachuzhipaixu){
                            $scope.jianchapaixu = 1;
                            if(!$scope.chuzhiweikong){
                                $scope.chuzhiweikong = $scope.listRechargePresentsStoredValueEntity[0];
                            }else{
                                $scope.listRechargePresentsStoredValueEntity[0] = $scope.chuzhiweikong;
                            }
                        }

                        $scope.showExpandInfoWindow = false;
                        $scope.showExpandInfoWindowCz = true;
                    //}

                };
                //点击取消后显示的模态框
                $scope.clostExpandWindowStores = function(){
                    $scope.showExpandInfoWindow = true;
                    $scope.showExpandInfoWindowCz = false;
                    $scope.showExpandInfoWindowStore = false;
                }
                //关闭储值券模态框
                $scope.clostExpandWindowCz = function(){
                    $scope.showExpandInfoWindow = true;
                    $scope.showExpandInfoWindowCz = false;
                };
                //新增
                $scope.submitExpandinfoAll = function(){
                    //充值赠送新增
                    if($scope.panduanshixinzenghaishiqitaxiaoxiugai){
                        $scope.showConfirm("确定新增？",function(rs){
                            if(rs){
                                activityCtrl.insertPresentsActivityInfo($scope,$http,$scope.rechargePresentsActivityEntity);
                            }
                        })
                    }else{
                        var deferred = $q.defer();
                        var promise = deferred.promise;

                        promise.then(function (result) {
                            activityCtrl.updatePresentsActivityInfo($scope,$http,$scope.rechargePresentsActivityEntity);
                        }).then(function (result) {
                            activityCtrl.updateRechargePresentsIntegration($scope,$http,$scope.listRechargePresentsIntegrationEntity);
                        }).then(function (result) {
                            activityCtrl.batchUpdateRechargePresentsStoredValue($scope,$http,$scope.listRechargePresentsStoredValueEntity);
                        }).then(function (result){
                            activityCtrl.updateRechargePresentsVoucher($scope,$http,$scope.listRechargePresentsVoucherEntity);
                        }).then(function(){
                            $scope.showAlert("修改成功",function(){
                                window.location.reload()
                            })

                        })
                        if(true){
                            deferred.resolve("执行成功");
                        }else{
                            deferred.reject("sorry, it lost!");
                        }

                        //活动更新接口调用
                    //    activityCtrl.updatePresentsActivityInfo($scope,$http,$scope.rechargePresentsActivityEntity);
                    //    activityCtrl.updateRechargePresentsIntegration($scope,$http,$scope.listRechargePresentsIntegrationEntity);
                    //    //储值券修改
                    //    activityCtrl.batchUpdateRechargePresentsStoredValue($scope,$http,$scope.listRechargePresentsStoredValueEntity);
                    ////    抵用券修改
                    //    activityCtrl.updateRechargePresentsVoucher($scope,$http,$scope.listRechargePresentsVoucherEntity);
                    }
                };
            //    执行其他新增操作
                $scope.qitachaozuoxingzeng = function(){
                    var deferred = $q.defer();
                    var promise = deferred.promise;

                    promise.then(function (result) {
                       activityCtrl.batchInsertRechargePresentsStoredValue($scope,$http,$scope.listRechargePresentsStoredValueEntity)//储值券新增

                    }).then(function (result) {
                        activityCtrl.batchInsertRechargePresentsVoucher($scope,$http,$scope.listRechargePresentsVoucherEntity);//抵用券新增
                    }).then(function (result) {
                        activityCtrl.insertRechargePresentsIntegration($scope,$http,$scope.listRechargePresentsIntegrationEntity);//积分新增
                    }).then(function (result){
                       $scope.showAlert("新增成功",function(){
                           window.location.reload();
                       })
                    })
                    if(true){
                        deferred.resolve("执行成功");
                    }else{
                        deferred.reject("sorry, it lost!");
                    }

                    //activityCtrl.batchInsertRechargePresentsStoredValue($scope,$http,$scope.listRechargePresentsStoredValueEntity)//储值券新增
                    //activityCtrl.batchInsertRechargePresentsVoucher($scope,$http,$scope.listRechargePresentsVoucherEntity);//抵用券新增
                    //activityCtrl.insertRechargePresentsIntegration($scope,$http,$scope.listRechargePresentsIntegrationEntity);//积分新增
                    //var id = setInterval(function(){//检验是不是全部新增成功
                    //    if($scope.jifendiaoyongchenggong && $scope.diyongquandiaoyongchenggong && $scope.chuzhiquandiaoyongchenggong){
                    //        clearInterval(id);
                    //        window.location.reload();
                    //    }
                    //},100);
                }
            //    调用删除储值接口

                $scope.z_delete = function(strPresentsStoredValueId,strActivityId,shanchujitiao,who){
                    if($scope.panduanshixinzenghaishiqitaxiaoxiugai) {//查询前面新增执行的操作
                        var chuzhiquan = $scope.listRechargePresentsStoredValueEntity;
                        delete chuzhiquan[shanchujitiao];
                        $scope.listRechargePresentsStoredValueEntity=chuzhiquan;
                    }else{//查询后删除执行的操作
                        if ($scope.listRechargePresentsStoredValueEntity.length == 1) {
                            $scope.showAlert("最后一条数据不能删除！")
                        } else {
                            $scope.showConfirm("确认删除" + who + "?", function (rs) {
                                if (rs) {
                                    activityCtrl.deleteRechargePresentsStoredValue($scope, $http, strPresentsStoredValueId, strActivityId, shanchujitiao);
                                }
                            })
                        }
                    }

                }
                //    调用删除抵用券接口

                $scope.z_deletedyq = function(strRechargePresentsVoucherId,strActivityId,shanchujitiao,who){
                    if($scope.panduanshixinzenghaishiqitaxiaoxiugai){//查询前面新增执行的操作
                        var diyongquan = $scope.listRechargePresentsVoucherEntity;
                        delete diyongquan[shanchujitiao];
                        $scope.listRechargePresentsVoucherEntity=diyongquan;
                    }else{
                        if($scope.listRechargePresentsVoucherEntity.length == 1){
                            $scope.showAlert("最后一条数据不能删除！")
                        }else{
                            $scope.showConfirm("确认删除"+who+"?",function(rs){
                                if(rs){
                                    activityCtrl.deleteRechargePresentsVoucher($scope,$http,strRechargePresentsVoucherId,strActivityId,shanchujitiao);
                                }
                            })
                        }
                    }


                }
            },
            //抵用券更新
            updateRechargePresentsVoucher:function($scope,$http,listRechargePresentsVoucherEntity){
                var dMinimumRechargeAmounts = [];
                var dMoreRechargeAmounts = [];
                var iEnableds = [];
                var iMinimumPresentsVoucherNumbers = [];
                var iMorePresentsVoucherNumbers = [];
                var strActivityIds = [];
                var strBasePresentsVoucherTicketIds=[];
                var strMorePresentsVoucherTicketIds = [];
                var strRechargePresentsVoucherIds = [];
                for(let i in listRechargePresentsVoucherEntity){
                    dMinimumRechargeAmounts[i] =  listRechargePresentsVoucherEntity[i].dMinimumRechargeAmount;
                    dMoreRechargeAmounts[i] = listRechargePresentsVoucherEntity[i].dMoreRechargeAmount;
                    iEnableds[i] = (function(listRechargePresentsVoucherEntity){
                                        if(listRechargePresentsVoucherEntity[0].strEnabledsssId ==undefined){
                                            return listRechargePresentsVoucherEntity[0].iEnabled
                                        }else{
                                            return listRechargePresentsVoucherEntity[0].strEnabledsssId
                                        }

                                    })(listRechargePresentsVoucherEntity);
                    iMinimumPresentsVoucherNumbers[i] =  listRechargePresentsVoucherEntity[i].iMinimumPresentsVoucherNumber;
                    iMorePresentsVoucherNumbers[i] = listRechargePresentsVoucherEntity[i].iMorePresentsVoucherNumber;
                    strActivityIds[i] = listRechargePresentsVoucherEntity[i].strActivityId;
                    strBasePresentsVoucherTicketIds[i] = listRechargePresentsVoucherEntity[i].strBasePresentsVoucherTicketId;
                    strMorePresentsVoucherTicketIds[i] = listRechargePresentsVoucherEntity[i].strMorePresentsVoucherTicketId;
                    strRechargePresentsVoucherIds[i] = listRechargePresentsVoucherEntity[i].strRechargePresentsVoucherId;
                }
                var data = {
                    dMinimumRechargeAmount:dMinimumRechargeAmounts.join(","),
                    dMoreRechargeAmount:dMoreRechargeAmounts.join(",") ,
                    iEnabled:iEnableds.join(","),
                    iMinimumPresentsVoucherNumber:iMinimumPresentsVoucherNumbers.join(","),
                    iMorePresentsVoucherNumber:iMorePresentsVoucherNumbers.join(",") ,
                    strActivityId:strActivityIds.join(","),
                    strBasePresentsVoucherTicketId:strBasePresentsVoucherTicketIds.join(","),
                    strMorePresentsVoucherTicketId:strMorePresentsVoucherTicketIds.join(","),
                    strRechargePresentsVoucherId:strRechargePresentsVoucherIds.join(",")
                }

                $http.post(remoteUrl.updateRechargePresentsVoucher,data).then(
                    function(result){
                        var rs = result.data;
                        var data = result.data;
                        var code = data.code;
                        if (code == 1) {
                            //activityCtrl.selectRechargePresentsActivityEntity($scope,$http);//调用刚刚新增的内容
                        } else if (code == -1) {
                            window.location.href = "/admin/login?url="
                            + window.location.pathname
                            + window.location.search
                            + window.location.hash;
                            //未登录
                        } else if (code <= -2 && code >= -7) {
                            //必填字段未填写
                            $scope.showAlert(rs.msg);
                        } else if (code == -8) {
                            //暂无数据
                            $scope.isNoData=true;
                            $scope.pageCount = 0;
                        }

                    }, function (result) {


                        var status = result.status;
                        if (status == -1) {
                            $scope.showAlert("服务器错误")
                        } else if (status >= 404 && status < 500) {
                            $scope.showAlert("请求路径错误")
                        } else if (status >= 500) {
                            $scope.showAlert("服务器错误")
                        }
                    }
                )
            },
            //储值批量更新
            batchUpdateRechargePresentsStoredValue:function($scope,$http,listRechargePresentsStoredValueEntity){
                var dPresentsAmounts = [];
                var dRechargeAmounts = [];
                var iEnableds = [];
                var strActivityIds = [];
                var strPresentsStoredValueIds = [];
                for(let i in listRechargePresentsStoredValueEntity){
                    dPresentsAmounts[i] =  listRechargePresentsStoredValueEntity[i].dPresentsAmount;
                    dRechargeAmounts[i] = listRechargePresentsStoredValueEntity[i].dRechargeAmount;
                    iEnableds[i] = (function(listRechargePresentsStoredValueEntity){
                                        if(listRechargePresentsStoredValueEntity[0].strEnabledsssId ==undefined){
                                            return listRechargePresentsStoredValueEntity[0].iEnabled
                                        }else{
                                            return listRechargePresentsStoredValueEntity[0].strEnabledsssId
                                        }

                                    })(listRechargePresentsStoredValueEntity);
                    listRechargePresentsStoredValueEntity[0].strEnabledsssId;
                    strActivityIds[i] = listRechargePresentsStoredValueEntity[i].strActivityId;
                    strPresentsStoredValueIds[i] = listRechargePresentsStoredValueEntity[i].strPresentsStoredValueId;
                }
                var data = {
                    dPresentsAmount:dPresentsAmounts.join(","),
                    dRechargeAmount:dRechargeAmounts.join(",") ,
                    iEnabled:iEnableds.join(","),
                    strActivityId:strActivityIds.join(","),
                    strPresentsStoredValueId:strPresentsStoredValueIds.join(",")
                }
                $http.post(remoteUrl.batchUpdateRechargePresentsStoredValue,data).then(
                    function(result){
                        var rs = result.data;
                        var data = result.data;
                        var code = data.code;
                        if (code == 1) {
                            //activityCtrl.selectRechargePresentsActivityEntity($scope,$http);//调用刚刚新增的内容
                        } else if (code == -1) {
                            window.location.href = "/admin/login?url="
                            + window.location.pathname
                            + window.location.search
                            + window.location.hash;
                            //未登录
                        } else if (code <= -2 && code >= -7) {
                            //必填字段未填写
                            $scope.showAlert(rs.msg);
                        } else if (code == -8) {
                            //暂无数据
                            $scope.isNoData=true;
                            $scope.pageCount = 0;
                        }

                    }, function (result) {


                        var status = result.status;
                        if (status == -1) {
                            $scope.showAlert("服务器错误")
                        } else if (status >= 404 && status < 500) {
                            $scope.showAlert("请求路径错误")
                        } else if (status >= 500) {
                            $scope.showAlert("服务器错误")
                        }
                    }
                )
            },
            //充值赠送积分更新
            updateRechargePresentsIntegration:function($scope,$http,listRechargePresentsIntegrationEntity){
                var data = {
                    dLeastRechargeAmount:listRechargePresentsIntegrationEntity.dLeastRechargeAmount || listRechargePresentsIntegrationEntity[0].dLeastRechargeAmount  ,
                    dPerTimeRechargeAmount:listRechargePresentsIntegrationEntity.dPerTimeRechargeAmount ||listRechargePresentsIntegrationEntity[0].dPerTimeRechargeAmount ,
                    iEnabled:(function(listRechargePresentsIntegrationEntity){
                        if(listRechargePresentsIntegrationEntity.strEnabledsssId ==undefined){
                            return listRechargePresentsIntegrationEntity[0].iEnabled
                        }else{
                            return listRechargePresentsIntegrationEntity.strEnabledsssId
                        }

                    })(listRechargePresentsIntegrationEntity),
                    strActivityId:listRechargePresentsIntegrationEntity[0].strActivityId,
                    strPresentsIntegrationId:listRechargePresentsIntegrationEntity[0].strPresentsIntegrationId
                }
                $http.post(remoteUrl.updateRechargePresentsIntegration,data).then(
                    function(result){
                        var rs = result.data;
                        var data = result.data;
                        var code = data.code;
                        if (code == 1) {

                            //activityCtrl.selectRechargePresentsActivityEntity($scope,$http);//调用刚刚新增的内容
                        } else if (code == -1) {
                            window.location.href = "/admin/login?url="
                            + window.location.pathname
                            + window.location.search
                            + window.location.hash;
                            //未登录
                        } else if (code <= -2 && code >= -7) {
                            //必填字段未填写
                            $scope.showAlert(rs.msg);
                        } else if (code == -8) {
                            //暂无数据
                            $scope.isNoData=true;
                            $scope.pageCount = 0;
                        }

                    }, function (result) {


                        var status = result.status;
                        if (status == -1) {
                            $scope.showAlert("服务器错误")
                        } else if (status >= 404 && status < 500) {
                            $scope.showAlert("请求路径错误")
                        } else if (status >= 500) {
                            $scope.showAlert("服务器错误")
                        }
                    }
                )
            },
            //充值赠送 -- 赠送抵用券 -- 删除
            deleteRechargePresentsVoucher:function($scope,$http,strRechargePresentsVoucherId,strActivityId,shanchujitiao){
                var data = {
                    strRechargePresentsVoucherId:strRechargePresentsVoucherId
                }
                $http.post(remoteUrl.deleteRechargePresentsVoucher,data).then(
                    function(result){
                        var rs = result.data;
                        var data = result.data;
                        var code = data.code;
                        if (code == 1) {
                            $scope.showAlert(rs.msg)
                            var diyongquan = $scope.listRechargePresentsVoucherEntity;
                            diyongquan.splice(shanchujitiao,1);
                            $scope.listRechargePresentsVoucherEntity=diyongquan;
                            //activityCtrl.selectRechargePresentsActivityEntity($scope,$http);//调用刚刚新增的内容
                        } else if (code == -1) {
                            window.location.href = "/admin/login?url="
                            + window.location.pathname
                            + window.location.search
                            + window.location.hash;
                            //未登录
                        } else if (code <= -2 && code >= -7) {
                            //必填字段未填写
                            $scope.showAlert(rs.msg);
                        } else if (code == -8) {
                            //暂无数据
                            $scope.isNoData=true;
                            $scope.pageCount = 0;
                        }

                    }, function (result) {


                        var status = result.status;
                        if (status == -1) {
                            $scope.showAlert("服务器错误")
                        } else if (status >= 404 && status < 500) {
                            $scope.showAlert("请求路径错误")
                        } else if (status >= 500) {
                            $scope.showAlert("服务器错误")
                        }
                    }
                )
            },
            //充值赠送 -- 储值券 -- 删除
            deleteRechargePresentsStoredValue:function($scope,$http,strPresentsStoredValueId,strActivityId,shanchujitiao){
                var data = {
                    strPresentsStoredValueId:strPresentsStoredValueId
                }
                $http.post(remoteUrl.deleteRechargePresentsStoredValue,data).then(
                    function(result){
                        var rs = result.data;
                        var data = result.data;
                        var code = data.code;
                        if (code == 1) {
                            $scope.showAlert(rs.msg)
                            var chuzhiquan = $scope.listRechargePresentsStoredValueEntity;
                            chuzhiquan.splice(shanchujitiao,1);
                            $scope.listRechargePresentsStoredValueEntity=chuzhiquan;
                            //activityCtrl.selectRechargePresentsActivityEntity($scope,$http);//调用刚刚新增的内容
                        } else if (code == -1) {
                            window.location.href = "/admin/login?url="
                            + window.location.pathname
                            + window.location.search
                            + window.location.hash;
                            //未登录
                        } else if (code <= -2 && code >= -7) {
                            //必填字段未填写
                            $scope.showAlert(rs.msg);
                        } else if (code == -8) {
                            //暂无数据
                            $scope.isNoData=true;
                            $scope.pageCount = 0;
                        }

                    }, function (result) {


                        var status = result.status;
                        if (status == -1) {
                            $scope.showAlert("服务器错误")
                        } else if (status >= 404 && status < 500) {
                            $scope.showAlert("请求路径错误")
                        } else if (status >= 500) {
                            $scope.showAlert("服务器错误")
                        }
                    }
                )
            },
            //充值赠送 -- 赠送积分 -- 新增
            insertRechargePresentsIntegration:function($scope,$http,listRechargePresentsIntegrationEntity){
                var data = {
                    dLeastRechargeAmount:listRechargePresentsIntegrationEntity.dLeastRechargeAmount,
                    dPerTimeRechargeAmount:listRechargePresentsIntegrationEntity.dPerTimeRechargeAmount,
                    iEnabled:listRechargePresentsIntegrationEntity.strEnabledsssId || 0,
                    strActivityId:$scope.gonggongstrActivityId
                };
                $http.post(remoteUrl.insertRechargePresentsIntegration,data).then(
                    function(result){
                        var rs = result.data;
                        var data = result.data;
                        var code = data.code;
                        if (code == 1) {
                            $scope.jifendiaoyongchenggong =1;
                            //activityCtrl.selectRechargePresentsActivityEntity($scope,$http);//调用刚刚新增的内容
                        } else if (code == -1) {
                            window.location.href = "/admin/login?url="
                            + window.location.pathname
                            + window.location.search
                            + window.location.hash;
                            //未登录
                        } else if (code <= -2 && code >= -7) {
                            //必填字段未填写
                            $scope.showAlert(rs.msg);
                        } else if (code == -8) {
                            //暂无数据
                            $scope.isNoData=true;
                            $scope.pageCount = 0;
                        }

                    }, function (result) {


                        var status = result.status;
                        if (status == -1) {
                            $scope.showAlert("服务器错误")
                        } else if (status >= 404 && status < 500) {
                            $scope.showAlert("请求路径错误")
                        } else if (status >= 500) {
                            $scope.showAlert("服务器错误")
                        }
                    }
                )
            },
            //充值赠送 --抵用券赠送 -- 批量新增
            batchInsertRechargePresentsVoucher:function($scope,$http,listRechargePresentsStoredValueEntity){
                var dMinimumRechargeAmounts = [];
                var dMoreRechargeAmounts = [];
                var iEnableds = [];
                var iMinimumPresentsVoucherNumbers = [];
                var iMorePresentsVoucherNumbers = [];
                var gonggongstrActivityIds = [];
                var strBasePresentsVoucherTicketIds = [];
                var strMorePresentsVoucherTicketIds = [];
                for(let i in listRechargePresentsStoredValueEntity){
                    dMinimumRechargeAmounts[i] =  listRechargePresentsStoredValueEntity[i].dMinimumRechargeAmount;
                    dMoreRechargeAmounts[i] = listRechargePresentsStoredValueEntity[i].dMoreRechargeAmount;
                    iEnableds[i] =listRechargePresentsStoredValueEntity[0].strEnabledsssId || 0;
                    iMinimumPresentsVoucherNumbers[i] = listRechargePresentsStoredValueEntity[i].iMinimumPresentsVoucherNumber;
                    iMorePresentsVoucherNumbers[i] =  listRechargePresentsStoredValueEntity[i].iMorePresentsVoucherNumber;
                    gonggongstrActivityIds[i] = $scope.gonggongstrActivityId;
                    strBasePresentsVoucherTicketIds[i] = listRechargePresentsStoredValueEntity[i].strBasePresentsVoucherTicketId;
                    strMorePresentsVoucherTicketIds[i] = listRechargePresentsStoredValueEntity[i].strMorePresentsVoucherTicketId;
                }
                var data = {
                    dMinimumRechargeAmount:dMinimumRechargeAmounts.join(","),
                    dMoreRechargeAmount:dMoreRechargeAmounts.join(","),
                    iEnabled:iEnableds.join(","),
                    iMinimumPresentsVoucherNumber:iMinimumPresentsVoucherNumbers.join(","),
                    iMorePresentsVoucherNumber:iMorePresentsVoucherNumbers.join(","),
                    strActivityId:gonggongstrActivityIds.join(","),
                    strBasePresentsVoucherTicketId:strBasePresentsVoucherTicketIds.join(","),
                    strMorePresentsVoucherTicketId:strMorePresentsVoucherTicketIds.join(",")
                };
                $http.post(remoteUrl.batchInsertRechargePresentsVoucher,data).then(
                    function(result){
                        var rs = result.data;
                        var data = result.data;
                        var code = data.code;
                        if (code == 1) {
                            $scope.diyongquandiaoyongchenggong = 1;
                            $scope.updataExpand($scope.gonggongstrActivityId);//修改新增成功后能删除事件
                            //activityCtrl.selectRechargePresentsActivityEntity($scope,$http);//调用刚刚新增的内容
                        } else if (code == -1) {
                            window.location.href = "/admin/login?url="
                            + window.location.pathname
                            + window.location.search
                            + window.location.hash;
                            //未登录
                        } else if (code <= -2 && code >= -7) {
                            //必填字段未填写
                            $scope.showAlert(rs.msg);
                        } else if (code == -8) {
                            //暂无数据
                            $scope.isNoData=true;
                            $scope.pageCount = 0;
                        }

                    }, function (result) {


                        var status = result.status;
                        if (status == -1) {
                            $scope.showAlert("服务器错误")
                        } else if (status >= 404 && status < 500) {
                            $scope.showAlert("请求路径错误")
                        } else if (status >= 500) {
                            $scope.showAlert("服务器错误")
                        }
                    }
                )
            },
            //充值赠送 -- 储值 -- 新增
            batchInsertRechargePresentsStoredValue:function($scope,$http,listRechargePresentsStoredValueEntity){
                var dPresentsAmounts = [];
                var dRechargeAmounts = [];
                var iEnableds = [];
                var gonggongstrActivityId = [];
                for(let i in listRechargePresentsStoredValueEntity){
                     dPresentsAmounts[i] =  listRechargePresentsStoredValueEntity[i].dPresentsAmount;
                     dRechargeAmounts[i] = listRechargePresentsStoredValueEntity[i].dRechargeAmount;
                     iEnableds[i] = listRechargePresentsStoredValueEntity[0].strEnabledsssId || 0;
                    gonggongstrActivityId[i] = $scope.gonggongstrActivityId;
                }
                var data = {
                    dPresentsAmount:dPresentsAmounts.join(","),
                    dRechargeAmount:dRechargeAmounts.join(","),
                    iEnabled:iEnableds.join(","),
                    strActivityId:gonggongstrActivityId.join(",")
                };
                $http.post(remoteUrl.batchInsertRechargePresentsStoredValue,data).then(
                    function(result){
                        var rs = result.data;
                        var data = result.data;
                        var code = data.code;
                        if (code == 1) {
                            $scope.chuzhiquandiaoyongchenggong = 1;//判断是不是新增成功
                            $scope.updataExpand($scope.gonggongstrActivityId);//修改新增成功后能删除事件
                            //$scope.showAlert(rs.msg,function(){
                            //    window.location.reload()
                            //})
                            ////activityCtrl.selectRechargePresentsActivityEntity($scope,$http);//调用刚刚新增的内容
                        } else if (code == -1) {
                            window.location.href = "/admin/login?url="
                            + window.location.pathname
                            + window.location.search
                            + window.location.hash;
                            //未登录
                        } else if (code <= -2 && code >= -7) {
                            //必填字段未填写
                            $scope.showAlert(rs.msg);
                        } else if (code == -8) {
                            //暂无数据
                            $scope.isNoData=true;
                            $scope.pageCount = 0;
                        }

                    }, function (result) {


                        var status = result.status;
                        if (status == -1) {
                            $scope.showAlert("服务器错误")
                        } else if (status >= 404 && status < 500) {
                            $scope.showAlert("请求路径错误")
                        } else if (status >= 500) {
                            $scope.showAlert("服务器错误")
                        }
                    }
                )
            },
            //充值赠送 -- 活动信息 -- 新增
            insertPresentsActivityInfo:function($scope,$http,rechargePresentsActivityEntity){
                var data = {
                    strActivityBeginTime:rechargePresentsActivityEntity.strActivityBeginTime.toLocaleDateString(),
                    strActivityEndTime:rechargePresentsActivityEntity.strActivityEndTime.toLocaleDateString(),
                    strActivityName:rechargePresentsActivityEntity.strActivityName,
                    strLevelsId:rechargePresentsActivityEntity.strLevelsId
                };
                $http.post(remoteUrl.insertPresentsActivityInfo,data).then(
                    function(result){
                        var rs = result.data;
                        var data = result.data;
                        var code = data.code;
                        if (code == 1) {
                            activityCtrl.selectRechargePresentsActivityEntity($scope,$http);//调用刚刚新增的内容的查询接口
                        } else if (code == -1) {
                            window.location.href = "/admin/login?url="
                            + window.location.pathname
                            + window.location.search
                            + window.location.hash;
                            //未登录
                        } else if (code <= -2 && code >= -7) {
                            //必填字段未填写
                            $scope.showAlert(rs.msg);
                        } else if (code == -8) {
                            //暂无数据
                            $scope.isNoData=true;
                            $scope.pageCount = 0;
                        }

                    }, function (result) {


                        var status = result.status;
                        if (status == -1) {
                            $scope.showAlert("服务器错误")
                        } else if (status >= 404 && status < 500) {
                            $scope.showAlert("请求路径错误")
                        } else if (status >= 500) {
                            $scope.showAlert("服务器错误")
                        }
                    }
                )
            },
            //充值赠送 -- 赠送活动 -- 刚新建活动信息查询 单条
            selectRechargePresentsActivityEntity:function($scope,$http){
                var data = {};
                $http.post(remoteUrl.selectRechargePresentsActivityEntity,data).then(
                    function(result){
                        var rs = result.data;
                        var data = result.data;
                        var code = data.code;
                        if (code == 1) {
                            $scope.rechargePresentsActivityEntity = data.data.rechargePresentsActivityEntity;
                            $scope.gonggongstrActivityId = data.data.rechargePresentsActivityEntity.strActivityId;
                            //date时间格式的调整
                            $scope.rechargePresentsActivityEntity.strActivityBeginTime = new Date($scope.rechargePresentsActivityEntity.strActivityBeginTime)
                            $scope.rechargePresentsActivityEntity.strActivityEndTime = new Date($scope.rechargePresentsActivityEntity.strActivityEndTime)
                            $scope.qitachaozuoxingzeng()//执行其它新增操作
                        } else if (code == -1) {
                            window.location.href = "/admin/login?url="
                            + window.location.pathname
                            + window.location.search
                            + window.location.hash;
                            //未登录
                        } else if (code <= -2 && code >= -7) {
                            //必填字段未填写
                            $scope.showAlert(rs.msg);
                        } else if (code == -8) {
                            //暂无数据
                            $scope.isNoData=true;
                            $scope.pageCount = 0;
                        }

                    }, function (result) {


                        var status = result.status;
                        if (status == -1) {
                            $scope.showAlert("服务器错误")
                        } else if (status >= 404 && status < 500) {
                            $scope.showAlert("请求路径错误")
                        } else if (status >= 500) {
                            $scope.showAlert("服务器错误")
                        }
                    }
                )
            },
            //充值赠送 -- 赠送查询详情
            selectAllRechargePresentsVoucher:function($scope,$http,strActivityId){
                var data = {
                    strActivityId:strActivityId
                };
                $http.post(remoteUrl.selectAllRechargePresentsVoucher,data).then(
                    function(result){
                        var rs = result.data;
                        var data = result.data;
                        var code = data.code;
                        if (code == 1) {
                            $scope.listRechargePresentsVoucherEntity = data.data.listRechargePresentsVoucherEntity;
                            $scope.panduandiaoyungdiyongquanjiekou = false;//判断是否调用了抵用券接口没
                            $scope.listRechargePresentsVoucherEntitydyq =[];
                            $scope.listRechargePresentsVoucherEntitydyq[0] = data.data.listRechargePresentsVoucherEntity[0];
                        } else if (code == -1) {
                            window.location.href = "/admin/login?url="
                            + window.location.pathname
                            + window.location.search
                            + window.location.hash;
                            //未登录
                        } else if (code <= -2 && code >= -7) {
                            //必填字段未填写
                            $scope.showAlert(rs.msg);
                        } else if (code == -8) {
                            //暂无数据
                            $scope.isNoData=true;
                            $scope.pageCount = 0;
                        }

                    }, function (result) {


                        var status = result.status;
                        if (status == -1) {
                            $scope.showAlert("服务器错误")
                        } else if (status >= 404 && status < 500) {
                            $scope.showAlert("请求路径错误")
                        } else if (status >= 500) {
                            $scope.showAlert("服务器错误")
                        }
                    }
                )
            },
            //充值赠送 -- 储值查询详情
            selectAllRechargePresentsStoredValue:function($scope,$http,strActivityId){
                var data = {
                    strActivityId:strActivityId
                };
                $http.post(remoteUrl.selectAllRechargePresentsStoredValue,data).then(
                    function(result){
                        $scope.panduandiaoyungchuzhiquanjiekou = true;
                        var rs = result.data;
                        var data = result.data;
                        var code = data.code;
                        if (code == 1) {
                            $scope.listRechargePresentsStoredValueEntity = data.data.listRechargePresentsStoredValueEntity;
                            $scope.listRechargePresentsStoredValueEntityCzq =[];
                            $scope.listRechargePresentsStoredValueEntityCzq[0] = data.data.listRechargePresentsStoredValueEntity[0];
                        } else if (code == -1) {
                            window.location.href = "/admin/login?url="
                            + window.location.pathname
                            + window.location.search
                            + window.location.hash;
                            //未登录
                        } else if (code <= -2 && code >= -7) {
                            //必填字段未填写
                            $scope.showAlert(rs.msg);
                        } else if (code == -8) {
                            //暂无数据
                            $scope.isNoData=true;
                            $scope.pageCount = 0;
                        }

                    }, function (result) {


                        var status = result.status;
                        if (status == -1) {
                            $scope.showAlert("服务器错误")
                        } else if (status >= 404 && status < 500) {
                            $scope.showAlert("请求路径错误")
                        } else if (status >= 500) {
                            $scope.showAlert("服务器错误")
                        }
                    }
                )
            },
            //充值赠送 -- 积分查询详情
            selectAllRechargePresentsIntegration:function($scope,$http,strActivityId){
                var data = {
                    strActivityId:strActivityId
                };
                $http.post(remoteUrl.selectAllRechargePresentsIntegration,data).then(
                    function(result){
                        var rs = result.data;
                        var data = result.data;
                        var code = data.code;
                        if (code == 1) {
                            $scope.listRechargePresentsIntegrationEntity = data.data.listRechargePresentsIntegrationEntity;
                        } else if (code == -1) {
                            window.location.href = "/admin/login?url="
                            + window.location.pathname
                            + window.location.search
                            + window.location.hash;
                            //未登录
                        } else if (code <= -2 && code >= -7) {
                            //必填字段未填写
                            $scope.showAlert(rs.msg);
                        } else if (code == -8) {
                            //暂无数据
                            $scope.isNoData=true;
                            $scope.pageCount = 0;
                        }

                    }, function (result) {


                        var status = result.status;
                        if (status == -1) {
                            $scope.showAlert("服务器错误")
                        } else if (status >= 404 && status < 500) {
                            $scope.showAlert("请求路径错误")
                        } else if (status >= 500) {
                            $scope.showAlert("服务器错误")
                        }
                    }
                )
            },
            //充值赠送 -- 查询详情
            selectAllRechargePresentsActivityEntity:function($scope,$http,strActivityId){
                var data = {
                    strActivityId:strActivityId
                };
                $http.post(remoteUrl.selectAllRechargePresentsActivityEntity,data).then(
                    function(result){
                        var rs = result.data;
                        var data = result.data;
                        var code = data.code;
                        if (code == 1) {
                            $scope.rechargePresentsActivityEntity = data.data.rechargePresentsActivityEntity;
                            $scope.rechargePresentsActivityEntity.strActivityBeginTime = new Date($scope.rechargePresentsActivityEntity.strActivityBeginTime)
                            $scope.rechargePresentsActivityEntity.strActivityEndTime = new Date($scope.rechargePresentsActivityEntity.strActivityEndTime)
                        } else if (code == -1) {
                            window.location.href = "/admin/login?url="
                            + window.location.pathname
                            + window.location.search
                            + window.location.hash;
                            //未登录
                        } else if (code <= -2 && code >= -7) {
                            //必填字段未填写
                            $scope.showAlert(rs.msg);
                        } else if (code == -8) {
                            //暂无数据
                            $scope.isNoData=true;
                            $scope.pageCount = 0;
                        }

                    }, function (result) {


                        var status = result.status;
                        if (status == -1) {
                            $scope.showAlert("服务器错误")
                        } else if (status >= 404 && status < 500) {
                            $scope.showAlert("请求路径错误")
                        } else if (status >= 500) {
                            $scope.showAlert("服务器错误")
                        }
                    }
                )
            },
            //充值赠送 -- 更新活动
            updatePresentsActivityInfo:function($scope,$http,rechargePresentsActivityEntity){
                var data = {
                    strActivityBeginTime:(rechargePresentsActivityEntity.strActivityBeginTime).toLocaleDateString(),
                    strActivityEndTime:rechargePresentsActivityEntity.strActivityEndTime.toLocaleDateString(),
                    strActivityId:rechargePresentsActivityEntity.strActivityId,
                    strActivityName:rechargePresentsActivityEntity.strActivityName,
                    strLevelsId:rechargePresentsActivityEntity.strLevelsId
                };
                $http.post(remoteUrl.updatePresentsActivityInfo,data).then(
                    function(result){
                        var rs = result.data;
                        var data = result.data;
                        var code = data.code;
                        if (code == 1) {
                        } else if (code == -1) {
                            window.location.href = "/admin/login?url="
                            + window.location.pathname
                            + window.location.search
                            + window.location.hash;
                            //未登录
                        } else if (code <= -2 && code >= -7) {
                            //必填字段未填写
                            $scope.showAlert(rs.msg);
                        } else if (code == -8) {
                            //暂无数据
                            $scope.isNoData=true;
                            $scope.pageCount = 0;
                        }

                    }, function (result) {


                        var status = result.status;
                        if (status == -1) {
                            $scope.showAlert("服务器错误")
                        } else if (status >= 404 && status < 500) {
                            $scope.showAlert("请求路径错误")
                        } else if (status >= 500) {
                            $scope.showAlert("服务器错误")
                        }
                    }
                )
            },
            //充值赠送删除
            deleteRechargePresentsActivityInfo:function($scope,$http,strActivityId){
                var data = {
                    strActivityId:strActivityId
                };
                $http.post(remoteUrl.deleteRechargePresentsActivityInfo,data).then(
                    function(result){
                        var rs = result.data;
                        var data = result.data;
                        var code = data.code;
                        if (code == 1) {
                            window.location.reload();
                        } else if (code == -1) {
                            window.location.href = "/admin/login?url="
                            + window.location.pathname
                            + window.location.search
                            + window.location.hash;
                            //未登录
                        } else if (code <= -2 && code >= -7) {
                            //必填字段未填写
                            $scope.showAlert(rs.msg);
                        } else if (code == -8) {
                            //暂无数据
                            $scope.isNoData=true;
                            $scope.pageCount = 0;
                        }

                    }, function (result) {


                        var status = result.status;
                        if (status == -1) {
                            $scope.showAlert("服务器错误")
                        } else if (status >= 404 && status < 500) {
                            $scope.showAlert("请求路径错误")
                        } else if (status >= 500) {
                            $scope.showAlert("服务器错误")
                        }
                    }
                )
            },
            //会员级别查询
            listAllMemberLevels:function($scope,$http){
                var data = {};
                $http.post(remoteUrl.listAllMemberLevels,data).then(
                    function(result){
                        var rs = result.data;
                        var data = result.data;
                        var code = data.code;
                        if (code == 1) {
                            $scope.memberlevelsEntityList = data.data.memberlevelsEntityList
                        } else if (code == -1) {
                            window.location.href = "/admin/login?url="
                            + window.location.pathname
                            + window.location.search
                            + window.location.hash;
                            //未登录
                        } else if (code <= -2 && code >= -7) {
                            //必填字段未填写
                            $scope.showAlert(rs.msg);
                        } else if (code == -8) {
                            //暂无数据
                            $scope.isNoData=true;
                            $scope.pageCount = 0;
                        }

                    }, function (result) {


                        var status = result.status;
                        if (status == -1) {
                            $scope.showAlert("服务器错误")
                        } else if (status >= 404 && status < 500) {
                            $scope.showAlert("请求路径错误")
                        } else if (status >= 500) {
                            $scope.showAlert("服务器错误")
                        }
                    }
                )
            },
            //充值赠送分页查询
            selectRechargePresentsActivityInfo:function($scope,$http,strSearchEnabledStatus,strSearchMemberLevel){
                var data = {
                    iPageNum:$scope.currentPage,
                    iPageSize:$scope.pageSize,
                    strSearchEnabledStatus:strSearchEnabledStatus,
                    strSearchMemberLevel:strSearchMemberLevel
                };
                $http.post(remoteUrl.selectRechargePresentsActivityInfo,data).then(
                    function(result){
                        var rs = result.data;
                        var data = result.data;
                        var code = data.code;
                        if (code == 1) {
                            $scope.resultMap = data.data.listRechargePresentsActivityEntity;
                            $scope.pageCount = data.data.iTotalPage;
                        } else if (code == -1) {
                            window.location.href = "/admin/login?url="
                            + window.location.pathname
                            + window.location.search
                            + window.location.hash;
                            //未登录
                        } else if (code <= -2 && code >= -7) {
                            //必填字段未填写
                            $scope.showAlert(rs.msg);
                        } else if (code == -8) {
                            //暂无数据
                            $scope.isNoData=true;
                            $scope.pageCount = 0;
                            $scope.showAlert(rs.msg);
                        }

                    }, function (result) {


                        var status = result.status;
                        if (status == -1) {
                            $scope.showAlert("服务器错误")
                        } else if (status >= 404 && status < 500) {
                            $scope.showAlert("请求路径错误")
                        } else if (status >= 500) {
                            $scope.showAlert("服务器错误")
                        }
                    }
                )
            },

            ////////////////////充值赠送-结束////////////////////////




            ////////////////////首次入会-开始////////////////////////
            firsttime:function($scope,$http,$q){
                $scope.iIsValid=[{"id":1,"name":"启用"},{"id":0,"name":"禁用"}];
                //查询
                activityCtrl.selectFirstMemberInitiationIntegrationPresents($scope,$http);
                activityCtrl.selectStoredTicketPresentsInfo($scope,$http);
                activityCtrl.getListBoxtVoucherTicketInfo($scope,$http);
                activityCtrl.selectVoucherTicketPresentsInfo($scope,$http);
                //删除
                //activityCtrl.deleteVoucherTicketPresentsInfo($scope,$http,strVoucherTicketPresentsId)
                $scope.z_delete = function (strVoucherTicketPresentsId) {
                    $scope.showConfirm("确认删除?",function(rs){
                        if(rs){
                            if($scope.listVoucherTicketPresentsEntity.length ==1){
                                $scope.showAlert("最后一条数据不能删除")
                            }else{
                                activityCtrl.deleteVoucherTicketPresentsInfo($scope,$http,strVoucherTicketPresentsId);
                            }

                        }
                    })
                }
                //打开模态框
                $scope.z_add_firsttime = function(){
                    $scope.showExpandInfoWindow = true;
                }
                //新增
                $scope.submitExpandinfo = function(){
                    var listVoucherTicketPresentsEntity = $scope.listVoucherTicketPresentsEntity;
                    activityCtrl.insertVoucherTicketPresentsInfo($scope,$http,listVoucherTicketPresentsEntity)
                }
                //关闭窗口
                $scope.clostExpandWindow = function(){
                    $scope.showExpandInfoWindow = false;
                }
                //点击提交按钮
                $scope.baocun = function(){
                    var integrationPresentsEntity = $scope.integrationPresentsEntity;
                    //储值
                    var listStoredTicketPresentsEntity = $scope.listStoredTicketPresentsEntity[0];
                    //抵用券
                    var listVoucherTicketPresentsEntity = $scope.listVoucherTicketPresentsEntity;
                    var stringParam = []
                    for(let i=0;i<listVoucherTicketPresentsEntity.length;i++){
                        stringParam[i] = listVoucherTicketPresentsEntity[i].strVoucherTicketPresentsId+","
                        +listVoucherTicketPresentsEntity[i].strVoucherTicketKindId+","
                        +listVoucherTicketPresentsEntity[i].iTotalVoucherTicketNum+","
                        +listVoucherTicketPresentsEntity[i].iEnabled;
                    }
                    //记录id,抵用券ID,抵用券张数,启用状态|记录id,抵用券ID,抵用券张数,启用状态
                    stringParam = stringParam.join("|");

                    $scope.jfqypanduan =  function(){
                        if (integrationPresentsEntity.strEnabledsssId == undefined) {
                           return integrationPresentsEntity.iEnabled;
                        }else{
                           return integrationPresentsEntity.strEnabledsssId
                        }
                    }
                    $scope.showConfirm("确认修改？",function(rs){
                        if(rs){
                            //window.location.reload();
                            var deferred = $q.defer();
                            var promise = deferred.promise;
                            promise.then(function (result) {
                                activityCtrl.updateStoredTicketPresentsInfo($scope,$http,listStoredTicketPresentsEntity)
                            }).then(function (result) {
                                activityCtrl.updateFirstMemberInitiationIntegrationPresents($scope,$http,integrationPresentsEntity)
                            }).then(function (result) {
                                activityCtrl.insertAndUpdateVoucherTicketPresentsInfo($scope,$http,stringParam)
                            }).then(function (result){
                                $scope.showAlert("修改成功",function(){
                                    window.location.reload();
                                })
                            },function(){
                                $scope.showAlert("修改失败");
                            })
                            if(true){
                                deferred.resolve("执行成功");
                            }else{
                                deferred.reject("sorry, it lost!");
                            }

                        }
                    })

                };
            },
            //抵用券修改
            insertAndUpdateVoucherTicketPresentsInfo:function($scope,$http,stringParam) {
                var data = {
                    stringParam:stringParam
                };
                $http.post(remoteUrl.insertAndUpdateVoucherTicketPresentsInfo,data).then(
                    function(result){
                        var rs = result.data;
                        var data = result.data;
                        var code = data.code;
                        if (code == 1) {
                            //$scope.showAlert(rs.msg,function(){
                            //    //window.location.reload();
                            //})
                        } else if (code == -1) {
                            window.location.href = "/admin/login?url="
                            + window.location.pathname
                            + window.location.search
                            + window.location.hash;
                            //未登录
                        } else if (code <= -2 && code >= -7) {
                            //必填字段未填写
                            $scope.showAlert(rs.msg);
                        } else if (code == -8) {
                            //暂无数据
                            $scope.isNoData=true;
                            $scope.pageCount = 0;
                        }

                    }, function (result) {


                        var status = result.status;
                        if (status == -1) {
                            $scope.showAlert("服务器错误")
                        } else if (status >= 404 && status < 500) {
                            $scope.showAlert("请求路径错误")
                        } else if (status >= 500) {
                            $scope.showAlert("服务器错误")
                        }
                    }
                )
            },
            //抵用券新增
            insertVoucherTicketPresentsInfo:function($scope,$http,listVoucherTicketPresentsEntity) {
                var data = {
                    iEnabled:1,
                    //iEnabled:listVoucherTicketPresentsEntity.strEnabledsssId,
                    iTotalVoucherTicketNum:listVoucherTicketPresentsEntity.iTotalVoucherTicketNum,
                    strVoucherTicketKindId:listVoucherTicketPresentsEntity.strVoucherTicketKindId
                };
                $http.post(remoteUrl.insertVoucherTicketPresentsInfo,data).then(
                    function(result){
                        var rs = result.data;
                        var data = result.data;
                        var code = data.code;
                        if (code == 1) {
                            $scope.showAlert(rs.msg,function(){
                                window.location.reload();
                            })
                        } else if (code == -1) {
                            window.location.href = "/admin/login?url="
                            + window.location.pathname
                            + window.location.search
                            + window.location.hash;
                            //未登录
                        } else if (code <= -2 && code >= -7) {
                            //必填字段未填写
                            $scope.showAlert(rs.msg);
                        } else if (code == -8) {
                            //暂无数据
                            $scope.isNoData=true;
                            $scope.pageCount = 0;
                        }

                    }, function (result) {


                        var status = result.status;
                        if (status == -1) {
                            $scope.showAlert("服务器错误")
                        } else if (status >= 404 && status < 500) {
                            $scope.showAlert("请求路径错误")
                        } else if (status >= 500) {
                            $scope.showAlert("服务器错误")
                        }
                    }
                )
            },
            //储值修改
            updateStoredTicketPresentsInfo:function($scope,$http,listStoredTicketPresentsEntity) {
                var chuzidate =new Date(listStoredTicketPresentsEntity.chuzidate).toLocaleDateString()
                var data = {
                    iEnabled:listStoredTicketPresentsEntity.strEnabledsssId,
                    iStoredValuePresents:listStoredTicketPresentsEntity.iStoredValuePresents,
                    iTotalStoredTicketNum:listStoredTicketPresentsEntity.iTotalStoredTicketNum,
                    strStoredTicketPresentsId:listStoredTicketPresentsEntity.strStoredTicketPresentsId,
                    strValidateEndTime:chuzidate,
                    strstrValidateBeginTime:new Date().toLocaleDateString()
                };
                $http.post(remoteUrl.updateStoredTicketPresentsInfo,data).then(
                    function(result){
                        var rs = result.data;
                        var data = result.data;
                        var code = data.code;
                        console.info(result)
                        if (code == 1) {
                            console.info(result)
                            //$scope.showAlert(rs.msg,function(){
                            //    window.location.reload();
                            //})
                        } else if (code == -1) {
                            window.location.href = "/admin/login?url="
                            + window.location.pathname
                            + window.location.search
                            + window.location.hash;
                            //未登录
                        } else if (code <= -2 && code >= -7) {
                            //必填字段未填写
                            $scope.showAlert(rs.msg);
                        } else if (code == -8) {
                            //暂无数据
                            $scope.isNoData=true;
                            $scope.pageCount = 0;
                        }

                    }, function (result) {


                        var status = result.status;
                        if (status == -1) {
                            $scope.showAlert("服务器错误")
                        } else if (status >= 404 && status < 500) {
                            $scope.showAlert("请求路径错误")
                        } else if (status >= 500) {
                            $scope.showAlert("服务器错误")
                        }
                    }
                )
            },
            //积分修改
            updateFirstMemberInitiationIntegrationPresents:function($scope,$http,integrationPresentsEntity) {
                var data = {
                    iEnabled:$scope.jfqypanduan(),
                    iIntegrationPresentsValue:integrationPresentsEntity.iIntegrationPresentsValue,
                    strIntegrationPresentsId:integrationPresentsEntity.strIntegrationPresentsId
                };
                $http.post(remoteUrl.updateFirstMemberInitiationIntegrationPresents,data).then(
                    function(result){
                        var rs = result.data;
                        var data = result.data;
                        var code = data.code;
                        if (code == 1) {
                            console.info(result)
                            //$scope.showAlert(rs.msg,function(){
                            //    window.location.reload();
                            //})
                        } else if (code == -1) {
                            window.location.href = "/admin/login?url="
                            + window.location.pathname
                            + window.location.search
                            + window.location.hash;
                            //未登录
                        } else if (code <= -2 && code >= -7) {
                            //必填字段未填写
                            $scope.showAlert(rs.msg);
                        } else if (code == -8) {
                            //暂无数据
                            $scope.isNoData=true;
                            $scope.pageCount = 0;
                        }

                    }, function (result) {


                        var status = result.status;
                        if (status == -1) {
                            $scope.showAlert("服务器错误")
                        } else if (status >= 404 && status < 500) {
                            $scope.showAlert("请求路径错误")
                        } else if (status >= 500) {
                            $scope.showAlert("服务器错误")
                        }
                    }
                )
            },
            //抵用券删除
            deleteVoucherTicketPresentsInfo:function($scope,$http,strVoucherTicketPresentsId) {
                var data = {
                    strVoucherTicketPresentsId:strVoucherTicketPresentsId
                };
                $http.post(remoteUrl.deleteVoucherTicketPresentsInfo,data).then(
                    function(result){
                        var rs = result.data;
                        var data = result.data;
                        var code = data.code;
                        if (code == 1) {
                            $scope.showAlert(rs.msg,function(){
                                window.location.reload();
                            })
                        } else if (code == -1) {
                            window.location.href = "/admin/login?url="
                            + window.location.pathname
                            + window.location.search
                            + window.location.hash;
                            //未登录
                        } else if (code <= -2 && code >= -7) {
                            //必填字段未填写
                            $scope.showAlert(rs.msg);
                        } else if (code == -8) {
                            //暂无数据
                            $scope.isNoData=true;
                            $scope.pageCount = 0;
                        }

                    }, function (result) {


                        var status = result.status;
                        if (status == -1) {
                            $scope.showAlert("服务器错误")
                        } else if (status >= 404 && status < 500) {
                            $scope.showAlert("请求路径错误")
                        } else if (status >= 500) {
                            $scope.showAlert("服务器错误")
                        }
                    }
                )
            },
            //    抵用券列表查询
            selectVoucherTicketPresentsInfo:function($scope,$http){
                var data = {};
                $http.post(remoteUrl.selectVoucherTicketPresentsInfo,data).then(
                    function(result){
                        var rs = result.data;
                        var data = result.data;
                        var code = data.code;
                        if (code == 1) {
                            $scope.listVoucherTicketPresentsEntity = data.data.listVoucherTicketPresentsEntity;
                        } else if (code == -1) {
                            window.location.href = "/admin/login?url="
                            + window.location.pathname
                            + window.location.search
                            + window.location.hash;
                            //未登录
                        } else if (code <= -2 && code >= -7) {
                            //必填字段未填写
                            $scope.showAlert(rs.msg);
                        } else if (code == -8) {
                            //暂无数据
                            $scope.isNoData=true;
                            $scope.pageCount = 0;
                        }

                    }, function (result) {


                        var status = result.status;
                        if (status == -1) {
                            $scope.showAlert("服务器错误")
                        } else if (status >= 404 && status < 500) {
                            $scope.showAlert("请求路径错误")
                        } else if (status >= 500) {
                            $scope.showAlert("服务器错误")
                        }
                    }
                )
            },
            //    抵用券下拉列表查询
            getListBoxtVoucherTicketInfo:function($scope,$http){
                var data = {};
                $http.post(remoteUrl.getListBoxtVoucherTicketInfo,data).then(
                    function(result){
                        var rs = result.data;
                        var data = result.data;
                        var code = data.code;
                        if (code == 1) {
                            $scope.listVoucherTicketInfoEntity = data.data.listVoucherTicketInfoEntity;
                        } else if (code == -1) {
                            window.location.href = "/admin/login?url="
                            + window.location.pathname
                            + window.location.search
                            + window.location.hash;
                            //未登录
                        } else if (code <= -2 && code >= -7) {
                            //必填字段未填写
                            $scope.showAlert(rs.msg);
                        } else if (code == -8) {
                            //暂无数据
                            $scope.isNoData=true;
                            $scope.pageCount = 0;
                        }

                    }, function (result) {


                        var status = result.status;
                        if (status == -1) {
                            $scope.showAlert("服务器错误")
                        } else if (status >= 404 && status < 500) {
                            $scope.showAlert("请求路径错误")
                        } else if (status >= 500) {
                            $scope.showAlert("服务器错误")
                        }
                    }
                )
            },
            //    储值查询
            selectStoredTicketPresentsInfo:function($scope,$http){
                var data = {};
                $http.post(remoteUrl.selectStoredTicketPresentsInfo,data).then(
                    function(result){
                        var rs = result.data;
                        var data = result.data;
                        var code = data.code;
                        if (code == 1) {
                            $scope.listStoredTicketPresentsEntity = data.data.listStoredTicketPresentsEntity;

                        } else if (code == -1) {
                            window.location.href = "/admin/login?url="
                            + window.location.pathname
                            + window.location.search
                            + window.location.hash;
                            //未登录
                        } else if (code <= -2 && code >= -7) {
                            //必填字段未填写
                            $scope.showAlert(rs.msg);
                        } else if (code == -8) {
                            //暂无数据
                            $scope.isNoData=true;
                            $scope.pageCount = 0;
                        }

                    }, function (result) {


                        var status = result.status;
                        if (status == -1) {
                            $scope.showAlert("服务器错误")
                        } else if (status >= 404 && status < 500) {
                            $scope.showAlert("请求路径错误")
                        } else if (status >= 500) {
                            $scope.showAlert("服务器错误")
                        }
                    }
                )
            },
            //    赠送积分查询
            selectFirstMemberInitiationIntegrationPresents:function($scope,$http){
                var data = {};
                $http.post(remoteUrl.selectFirstMemberInitiationIntegrationPresents,data).then(
                    function(result){
                        var rs = result.data;
                        var data = result.data;
                        var code = data.code;
                        if (code == 1) {
                            $scope.integrationPresentsEntity = data.data.integrationPresentsEntity;
                        } else if (code == -1) {
                            window.location.href = "/admin/login?url="
                            + window.location.pathname
                            + window.location.search
                            + window.location.hash;
                            //未登录
                        } else if (code <= -2 && code >= -7) {
                            //必填字段未填写
                            $scope.showAlert(rs.msg);
                        } else if (code == -8) {
                            //暂无数据
                            $scope.isNoData=true;
                            $scope.pageCount = 0;
                        }

                    }, function (result) {


                        var status = result.status;
                        if (status == -1) {
                            $scope.showAlert("服务器错误")
                        } else if (status >= 404 && status < 500) {
                            $scope.showAlert("请求路径错误")
                        } else if (status >= 500) {
                            $scope.showAlert("服务器错误")
                        }
                    }
                )
            },
            ////////////////////首次入会-结束/////////////////////////



            ////////////////////抵用券维护-开始/////////////////////////
            dyqxz:function($scope,$http){
                $scope.currentPage = 1;
                $scope.pageSize = 5;
                $scope.isShowListMenu = [];
                $scope.listVoucherTicketInfoEntity = {};
                $scope.iIsValid=[{"id":1,"name":"启用"},{"id":0,"name":"禁用"}];
                //$scope.datass =new Date($scope.strValidEndTime)
                //调用分页查询接口
                activityCtrl.selectVoucherTicketInfo($scope,$http)
                $scope.onPageChange = function () {
                    // ajax request to load data
                    $scope.listVoucherTicketInfoEntity = {};
                    activityCtrl.selectVoucherTicketInfo($scope,$http);

                };
                //删除
                $scope.delectExpand=function(strVoucherTicketId,strVoucherTicketName){
                    $scope.showConfirm("确认要删除" +strVoucherTicketName+"？",function(rs){
                        //调用删除拓展资料函数
                        if(rs){
                            activityCtrl.deleteVoucherTicketInfo(strVoucherTicketId,$scope, $http);
                        }

                    })
                }
            //    二级菜单
                $scope.openCtrMenu = function ($index, type) {
                    for (var i = 0; i < $scope.isShowListMenu.length; i++) {
                        $scope.isShowListMenu[i] = false;
                    }
                    if (type == 'over') {
                        $scope.isShowListMenu[$index] = !$scope.isShowListMenu[$index];
                    }
                };
                //修改点击按钮事件
                $scope.updataExpand=function(strVoucherTicketId){
                    $scope.showExpandInfoWindow=true;
                    $scope.isAddNewExpand=false;
                    //判断执行添加还是修改
                    $scope.typePanduan = true;
                    //调用接口
                    activityCtrl.findVoucherTicketInfoById(strVoucherTicketId,$scope, $http);
                    $scope.strVoucherTicketId = strVoucherTicketId;
                };
            //    新增
                //新建服务分类点击按钮事件
                $scope.newExpandinginfo=function(){

                    $scope.showExpandInfoWindow=true;
                    $scope.isAddNewExpand=true;
                    //判断执行添加还是修改
                    $scope.typePanduan = false;
                };
                //关闭窗口
                $scope.clostExpandWindow=function(){
                    $scope.showExpandInfoWindow=false;
                    $scope.listVoucherTicketInfoEntity={};
                    //调用分页查询接口
                    activityCtrl.selectVoucherTicketInfo($scope,$http)
                };
                //保存
                $scope.submitExpandinfo=function(){
                    if($scope.typePanduan){
                        //调用修改接口
                        activityCtrl.updateVoucherTicketInfo($scope.listVoucherTicketInfoEntity,$scope, $http);
                    }
                    else{
                        //调用保存接口
                        activityCtrl.insertVoucherTicketInfo($scope.listVoucherTicketInfoEntity,$scope, $http);
                    }


                };

            },
            //单条数据删除
            deleteVoucherTicketInfo:function(strVoucherTicketId,$scope,$http){

                var data={
                    strVoucherTicketId:strVoucherTicketId
                };
                $http.post(remoteUrl.deleteVoucherTicketInfo,data).then(
                    function (result) {

                        var rs = result.data;
                        var code = rs.code;
                        var data = rs.data;
                        if (code == 1) {
                            window.location.reload();
                        } else if (code == -1) {
                            window.location.href = "/admin/login?url="
                            + window.location.pathname
                            + window.location.search
                            + window.location.hash;
                            //未登录
                        } else if (code <= -2 && code >= -7) {
                            //必填字段未填写
                            $scope.showAlert(rs.msg);
                        } else if (code == -8) {
                            //暂无数据
                            $scope.isNoData=true;
                            $scope.pageCount = 0;
                        }

                    }, function (result) {


                        var status = result.status;
                        if (status == -1) {
                            $scope.showAlert("服务器错误")
                        } else if (status >= 404 && status < 500) {
                            $scope.showAlert("请求路径错误")
                        } else if (status >= 500) {
                            $scope.showAlert("服务器错误")
                        }
                    });
            },
            //单条数据查询
            findVoucherTicketInfoById:function(strVoucherTicketId,$scope,$http){

                var data={
                    strVoucherTicketId:strVoucherTicketId
                };
                $http.post(remoteUrl.findVoucherTicketInfoById,data).then(
                    function (result) {

                        var rs = result.data;
                        var code = rs.code;
                        var data = rs.data;
                        if (code == 1) {
                            $scope.listVoucherTicketInfoEntity = data.voucherTicketInfoEntity;
                            $scope.listVoucherTicketInfoEntity.strValidEndTime = new Date($scope.listVoucherTicketInfoEntity.strValidEndTime)
                        } else if (code == -1) {
                            window.location.href = "/admin/login?url="
                            + window.location.pathname
                            + window.location.search
                            + window.location.hash;
                            //未登录
                        } else if (code <= -2 && code >= -7) {
                            //必填字段未填写
                            $scope.showAlert(rs.msg);
                        } else if (code == -8) {
                            //暂无数据
                            $scope.isNoData=true;
                            $scope.pageCount = 0;
                        }

                    }, function (result) {


                        var status = result.status;
                        if (status == -1) {
                            $scope.showAlert("服务器错误")
                        } else if (status >= 404 && status < 500) {
                            $scope.showAlert("请求路径错误")
                        } else if (status >= 500) {
                            $scope.showAlert("服务器错误")
                        }
                    });
            },
            //抵用券维护修改
            updateVoucherTicketInfo:function(listVoucherTicketInfoEntity,$scope,$http){
                var riqi =new Date(listVoucherTicketInfoEntity.strValidEndTime).toLocaleString();
                riqi = riqi.split(/\s/)[0];
                var data={
                    dVoucherTicketAmount:listVoucherTicketInfoEntity.dVoucherTicketAmount,
                    iIsValid:listVoucherTicketInfoEntity.iIsValid,
                    strRuleDesc:listVoucherTicketInfoEntity.strRuleDesc,
                    strValidEndTime:riqi,
                    strVoucherTicketName:listVoucherTicketInfoEntity.strVoucherTicketName,
                    strVoucherTicketId:$scope.strVoucherTicketId
                };
                $http.post(remoteUrl.updateVoucherTicketInfo,data).then(
                    function (result) {

                        var rs = result.data;
                        var code = rs.code;
                        var data = rs.data;
                        if (code == 1) {
                            $scope.showAlert(rs.msg,function(){
                                window.location.reload();
                            });
                        } else if (code == -1) {
                            window.location.href = "/admin/login?url="
                            + window.location.pathname
                            + window.location.search
                            + window.location.hash;
                            //未登录
                        } else if (code <= -2 && code >= -7) {
                            //必填字段未填写
                            $scope.showAlert(rs.msg);
                        } else if (code == -8) {
                            //暂无数据
                            $scope.isNoData=true;
                            $scope.pageCount = 0;
                        }

                    }, function (result) {


                        var status = result.status;
                        if (status == -1) {
                            $scope.showAlert("服务器错误")
                        } else if (status >= 404 && status < 500) {
                            $scope.showAlert("请求路径错误")
                        } else if (status >= 500) {
                            $scope.showAlert("服务器错误")
                        }
                    });
            },
            //抵用券维护新增
            insertVoucherTicketInfo:function(listVoucherTicketInfoEntity,$scope,$http){
                    var riqi =new Date(listVoucherTicketInfoEntity.strValidEndTime).toLocaleString();
                    riqi = riqi.split(/\s/)[0];
                var data={
                    dVoucherTicketAmount:listVoucherTicketInfoEntity.dVoucherTicketAmount,
                    iIsValid:listVoucherTicketInfoEntity.iIsValid,
                    strRuleDesc:listVoucherTicketInfoEntity.strRuleDesc,
                    strValidEndTime:riqi,
                    //strValidEndTime:listVoucherTicketInfoEntity.strValidEndTime,
                    strVoucherTicketName:listVoucherTicketInfoEntity.strVoucherTicketName
                };
                $http.post(remoteUrl.insertVoucherTicketInfo,data).then(
                    function (result) {

                        var rs = result.data;
                        var code = rs.code;
                        var data = rs.data;
                        if (code == 1) {

                            $scope.showAlert(rs.msg,function(){
                                window.location.reload();
                            });
                        } else if (code == -1) {
                            window.location.href = "/admin/login?url="
                            + window.location.pathname
                            + window.location.search
                            + window.location.hash;
                            //未登录
                        } else if (code <= -2 && code >= -7) {
                            //必填字段未填写
                            $scope.showAlert(rs.msg);
                        } else if (code == -8) {
                            //暂无数据
                            $scope.isNoData=true;
                            $scope.pageCount = 0;
                        }

                    }, function (result) {


                        var status = result.status;
                        if (status == -1) {
                            $scope.showAlert("服务器错误")
                        } else if (status >= 404 && status < 500) {
                            $scope.showAlert("请求路径错误")
                        } else if (status >= 500) {
                            $scope.showAlert("服务器错误")
                        }
                    });
            },
            //抵用券维护分页查询
            selectVoucherTicketInfo:function($scope,$http){
                var data={
                    //iPageNum:$scope.currentPage,
                    iPageNum:$scope.currentPage,
                    //pagesize:$scope.pageSize
                    iPageSize:$scope.pageSize
                };
                $http.post(remoteUrl.selectVoucherTicketInfo,data).then(
                    function (result) {
                        var rs = result.data;
                        var code = rs.code;
                        var data = rs.data;
                        //code=-8;
                        if (code == 1) {
                            //图片跟路径
                            $scope.pageCount = data.iTotalPage;
                            $scope.listVoucherTicketInfoEntity = data.listVoucherTicketInfoEntity;
                            for (var i = 0; i < $scope.isShowListMenu.length; i++) {
                                $scope.isShowListMenu[i] = false;
                            }
                        } else if (code == -1) {
                            window.location.href = "/admin/login?url="
                            + window.location.pathname
                            + window.location.search
                            + window.location.hash;
                            //未登录
                        } else if (code <= -2 && code >= -7) {
                            //必填字段未填写
                            $scope.showAlert(rs.msg);
                        } else if (code == -8) {
                            //暂无数据
                            $scope.isNoData=true;
                            $scope.pageCount = 0;
                        }

                    }, function (result) {


                        var status = result.status;
                        if (status == -1) {
                            $scope.showAlert("服务器错误")
                        } else if (status >= 404 && status < 500) {
                            $scope.showAlert("请求路径错误")
                        } else if (status >= 500) {
                            $scope.showAlert("服务器错误")
                        }
                    });
            },
            ////////////////////抵用券维护-结束/////////////////////////






            ////////////////////积分规则-开始/////////////////////////
            integralrules: function ($scope,$http) {
                //新增是否禁用
                $scope.ifstrEnabled=[{"id":"1","name":"启用"},{"id":"0","name":"禁用"}];
                //查询
                activityCtrl.findAllSignIntegrationRules($scope,$http);
                activityCtrl.findAllSignIntegrationRule($scope,$http);
                activityCtrl.findAllIntegrationCashRule($scope,$http);
                // 连续非连续删除
                $scope.z_delete = function(strSignId){
                    if($scope.feilianxulen == 1){
                        $scope.showAlert("最后一条，不能删除")
                    }else{
                        $scope.showConfirm("确认要删除" + "？", function(rs) {
                            if(rs) {
                                activityCtrl.deleteSignIntegrationRule($scope,$http,strSignId);
                            }
                        })
                    };

                }
                //非连续签到新增
                $scope.submitExpandinfo = function(insertSignIntegrationRules){
                    activityCtrl.insertSignIntegrationRules($scope,$http,insertSignIntegrationRules);
                }
                //积分抵现删除
                $scope.z_jfdelete = function(strId){
                    $scope.showConfirm("确认要删除" + "？", function(rs) {
                        //调用删除删除图片轮播函数
                        if(rs) {
                            if($scope.zhuangtai.length < 4){
                                let  data = "请保留一条数据"
                                $scope.showAlert(data);
                            }
                            activityCtrl.deleteIntegrationCashRule($scope,$http,strId);
                        }

                    })
                }
                //新增
                $scope.newExpandinginfo=function(){

                    $scope.showExpandInfoWindow=true;
                    $scope.isAddNewExpand=true;
                    //判断执行添加还是修改
                    //$scope.typePanduan = false;
                };
                //关闭窗口
                $scope.clostExpandWindow=function(){
                    $scope.showExpandInfoWindow=false;
                };

                //点击保存按钮
                $scope.zhuangtai = [];
                //var lianxu = $scope.listSignIntegrationRuleEntity;
                //var blianxu = $scope.listSignIntegrationRuleEntity;

                $scope.baocun = function(){




                    //非连续
                    let updateSignIntegrationRule = {}
                    updateSignIntegrationRule.iIntegration = [];
                    updateSignIntegrationRule.strEnabled = [];
                    updateSignIntegrationRule.strId = [];
                    updateSignIntegrationRule.strSignDays = [];
                    for(let i= 0;i<$scope.listSignIntegrationRuleEntity.length;i++){
                        updateSignIntegrationRule.strId[i] = $scope.panduanxuanzhong || $scope.listSignIntegrationRuleEntity[i].strSignId;
                        updateSignIntegrationRule.iIntegration[i] = $scope.listSignIntegrationRuleEntity[i].iIntegration;
                        updateSignIntegrationRule.strEnabled[i] = $scope.listSignIntegrationRuleEntity[i].strEnabledsssId;
                        updateSignIntegrationRule.strSignDays[i] = $scope.listSignIntegrationRuleEntity[i].strSignDays;
                    }
                    updateSignIntegrationRule.iIntegration = updateSignIntegrationRule.iIntegration.join(",");
                    updateSignIntegrationRule.strEnabled = updateSignIntegrationRule.strEnabled.join(",");
                    updateSignIntegrationRule.strId = updateSignIntegrationRule.strId.join(",");
                    updateSignIntegrationRule.strSignDays = updateSignIntegrationRule.strSignDays.join(",");



                    //连续

                    let updateSignIntegrationRules = {}
                    updateSignIntegrationRules.iIntegration = [];
                    updateSignIntegrationRules.StrEnabled = [];
                    updateSignIntegrationRules.strId = [];
                    updateSignIntegrationRules.strSignDays = [];
                    for(let i= 0;i<$scope.listSignIntegrationRuleEntitys.length;i++){
                        updateSignIntegrationRules.strId[i] = $scope.listSignIntegrationRuleEntitys[i].strSignId;
                        updateSignIntegrationRules.iIntegration[i] = $scope.listSignIntegrationRuleEntitys[i].iIntegration;
                        updateSignIntegrationRules.StrEnabled[i] = $scope.listSignIntegrationRuleEntitys[i].strEnabledsssId;
                        updateSignIntegrationRules.strSignDays[i] = $scope.listSignIntegrationRuleEntitys[i].strSignDays;
                    }
                    updateSignIntegrationRules.iIntegration = updateSignIntegrationRules.iIntegration.join(",");
                    updateSignIntegrationRules.strEnabled = updateSignIntegrationRules.StrEnabled.join(",");
                    updateSignIntegrationRules.strId = updateSignIntegrationRules.strId.join(",");
                    updateSignIntegrationRules.strSignDays = updateSignIntegrationRules.strSignDays.join(",");



                    //积分抵现
                    let updateIntegrationCashRule = {}
                    updateIntegrationCashRule.iIntegration = [];
                    updateIntegrationCashRule.strEnabled = [];
                    updateIntegrationCashRule.strId = [];
                    updateIntegrationCashRule.dCash = [];
                    for(let i= 0;i<$scope.listSignIntegrationRuleEntityT.length;i++){
                        updateIntegrationCashRule.strId[i] = $scope.listSignIntegrationRuleEntityT[i].strId;
                        updateIntegrationCashRule.iIntegration[i] = $scope.listSignIntegrationRuleEntityT[i].iIntegration;
                        updateIntegrationCashRule.strEnabled[i] =$scope.listSignIntegrationRuleEntityT[i].strEnabledsssId;
                        updateIntegrationCashRule.dCash[i] = $scope.listSignIntegrationRuleEntityT[i].dCash;
                    }

                    //updateIntegrationCashRule.strEnabled.length = updateIntegrationCashRule.strEnabled.length-1;
                    updateIntegrationCashRule.iIntegration = updateIntegrationCashRule.iIntegration.join(",");
                    updateIntegrationCashRule.strEnabled = updateIntegrationCashRule.strEnabled.join(",");
                    updateIntegrationCashRule.strId = updateIntegrationCashRule.strId.join(",");
                    updateIntegrationCashRule.dCash = updateIntegrationCashRule.dCash.join(",");

                    $scope.showConfirm("确认修改？",function(rs){
                        if(rs){
                            //调用更改接口-底线
                            activityCtrl.updateIntegrationCashRule($scope,$http,updateIntegrationCashRule);
                            //调用更改接口-非连续
                            activityCtrl.updateSignIntegrationRule($scope,$http,updateSignIntegrationRule);

                            //调用更改接口-连续
                            activityCtrl.updateSignIntegrationRules($scope,$http,updateSignIntegrationRules);

                        }
                    })

                }

            },
            ///积分规则 -- 底线 -- 更新
            updateIntegrationCashRule:function($scope,$http,updateIntegrationCashRule){
                var data=updateIntegrationCashRule;
                $http.post(remoteUrl.updateIntegrationCashRule,data).then(
                    function (result) {
                        var rs = result.data;
                        var code = rs.code;
                        var data = rs.data;
                        //code=-8;
                        if (code == 1) {
                            //$scope.showAlert(rs.msg,function(){
                            //    //window.location.reload();
                            //});
                        } else if (code == -1) {
                            window.location.href = "/admin/login?url="
                            + window.location.pathname
                            + window.location.search
                            + window.location.hash;
                            //未登录
                        } else if (code <= -2 && code >= -7) {
                            //必填字段未填写
                            $scope.showAlert(rs.msg);
                        } else if (code == -8) {
                            //暂无数据
                            $scope.isNoData=true;
                            $scope.pageCount = 0;
                        }

                    }, function (result) {

                        var status = result.status;
                        if (status == -1) {
                            $scope.showAlert("服务器错误")
                        } else if (status >= 404 && status < 500) {
                            $scope.showAlert("请求路径错误")
                        } else if (status >= 500) {
                            $scope.showAlert("服务器错误")
                        }
                    });
            },
            ///积分规则 -- 连续签到 -- 更改
            updateSignIntegrationRules:function($scope,$http,updateSignIntegrationRules){
                var data=updateSignIntegrationRules;
                $http.post(remoteUrl.updateSignIntegrationRules,data).then(
                    function (result) {
                        var rs = result.data;
                        var code = rs.code;
                        var data = rs.data;
                        //code=-8;
                        if (code == 1) {
                            //$scope.showAlert(rs.msg,function(){
                            //    //window.location.reload();
                            //});
                        } else if (code == -1) {
                            window.location.href = "/admin/login?url="
                            + window.location.pathname
                            + window.location.search
                            + window.location.hash;
                            //未登录
                        } else if (code <= -2 && code >= -7) {
                            //必填字段未填写
                            $scope.showAlert(rs.msg);
                        } else if (code == -8) {
                            //暂无数据
                            $scope.isNoData=true;
                            $scope.pageCount = 0;
                        }

                    }, function (result) {

                        var status = result.status;
                        if (status == -1) {
                            $scope.showAlert("服务器错误")
                        } else if (status >= 404 && status < 500) {
                            $scope.showAlert("请求路径错误")
                        } else if (status >= 500) {
                            $scope.showAlert("服务器错误")
                        }
                    });
            },
            ///积分规则 -- 非连续签到 -- 更改
            updateSignIntegrationRule:function($scope,$http,updateSignIntegrationRule){
                var data=updateSignIntegrationRule;
                $http.post(remoteUrl.updateSignIntegrationRule,data).then(
                    function (result) {
                        var rs = result.data;
                        var code = rs.code;
                        var data = rs.data;
                        //code=-8;
                        if (code == 1) {
                            //$scope.showAlert(rs.msg,function(){
                            //    //window.location.reload();
                            //});
                        } else if (code == -1) {
                            window.location.href = "/admin/login?url="
                            + window.location.pathname
                            + window.location.search
                            + window.location.hash;
                            //未登录
                        } else if (code <= -2 && code >= -7) {
                            //必填字段未填写
                            $scope.showAlert(rs.msg);
                        } else if (code == -8) {
                            //暂无数据
                            $scope.isNoData=true;
                            $scope.pageCount = 0;
                        }

                    }, function (result) {

                        var status = result.status;
                        if (status == -1) {
                            $scope.showAlert("服务器错误")
                        } else if (status >= 404 && status < 500) {
                            $scope.showAlert("请求路径错误")
                        } else if (status >= 500) {
                            $scope.showAlert("服务器错误")
                        }
                    });
            },
            ///积分规则 -- 非连续签到 -- 新增
            insertSignIntegrationRules:function($scope,$http,insertSignIntegrationRules){
                var data={
                    iIntegration:$scope.insertSignIntegrationRules.iIntegration,
                    strEnabled:$scope.insertSignIntegrationRules.ifstrEnabled,
                    strSignDays:$scope.insertSignIntegrationRules.strSignDays
                };
                $http.post(remoteUrl.insertSignIntegrationRules,data).then(
                    function (result) {

                        var rs = result.data;
                        var code = rs.code;
                        var data = rs.data;
                        //code=-8;
                        if (code == 1) {
                            $scope.showAlert(rs.msg,function(){
                                window.location.reload();
                            });
                        } else if (code == -1) {
                            window.location.href = "/admin/login?url="
                            + window.location.pathname
                            + window.location.search
                            + window.location.hash;
                            //未登录
                        } else if (code <= -2 && code >= -7) {
                            //必填字段未填写
                            $scope.showAlert(rs.msg);
                        } else if (code == -8) {
                            //暂无数据
                            $scope.isNoData=true;
                            $scope.pageCount = 0;
                        }

                    }, function (result) {

                        var status = result.status;
                        if (status == -1) {
                            $scope.showAlert("服务器错误")
                        } else if (status >= 404 && status < 500) {
                            $scope.showAlert("请求路径错误")
                        } else if (status >= 500) {
                            $scope.showAlert("服务器错误")
                        }
                    });
            },
            ///积分规则 -- 积分抵现 -- 删除
            deleteIntegrationCashRule:function($scope,$http,strId){
                var data={strId:strId};
                $http.post(remoteUrl.deleteIntegrationCashRule,data).then(
                    function (result) {

                        var rs = result.data;
                        var code = rs.code;
                        var data = rs.data;
                        //code=-8;
                        if (code == 1) {
                            //$scope.showAlert(rs.msg);
                            window.location.reload();
                        } else if (code == -1) {
                            window.location.href = "/admin/login?url="
                            + window.location.pathname
                            + window.location.search
                            + window.location.hash;
                            //未登录
                        } else if (code <= -2 && code >= -7) {
                            //必填字段未填写
                            $scope.showAlert(rs.msg);
                        } else if (code == -8) {
                            //暂无数据
                            $scope.isNoData=true;
                            $scope.pageCount = 0;
                        }

                    }, function (result) {

                        var status = result.status;
                        if (status == -1) {
                            $scope.showAlert("服务器错误")
                        } else if (status >= 404 && status < 500) {
                            $scope.showAlert("请求路径错误")
                        } else if (status >= 500) {
                            $scope.showAlert("服务器错误")
                        }
                    });
            },
            ///积分规则 -- 连续非连续 -- 删除
            deleteSignIntegrationRule:function($scope,$http,strSignId){
                var data={strSignId:strSignId};
                $http.post(remoteUrl.deleteSignIntegrationRule,data).then(
                    function (result) {

                        var rs = result.data;
                        var code = rs.code;
                        var data = rs.data;
                        //code=-8;
                        if (code == 1) {
                            //$scope.showAlert(rs.msg);
                            window.location.reload();
                        } else if (code == -1) {
                            window.location.href = "/admin/login?url="
                            + window.location.pathname
                            + window.location.search
                            + window.location.hash;
                            //未登录
                        } else if (code <= -2 && code >= -7) {
                            //必填字段未填写
                            $scope.showAlert(rs.msg);
                        } else if (code == -8) {
                            //暂无数据
                            $scope.isNoData=true;
                            $scope.pageCount = 0;
                        }

                    }, function (result) {

                        var status = result.status;
                        if (status == -1) {
                            $scope.showAlert("服务器错误")
                        } else if (status >= 404 && status < 500) {
                            $scope.showAlert("请求路径错误")
                        } else if (status >= 500) {
                            $scope.showAlert("服务器错误")
                        }
                    });
            },
            ///积分规则 -- 积分抵现规则 -- 查询
            findAllIntegrationCashRule:function($scope,$http){
                var data={};
                $http.post(remoteUrl.findAllIntegrationCashRule,data).then(
                    function (result) {

                        var rs = result.data;
                        var code = rs.code;
                        var data = rs.data;
                        //code=-8;
                        if (code == 1) {
                            $scope.listSignIntegrationRuleEntityT = data.listIntegrationCashRuleEntity;

                        } else if (code == -1) {
                            window.location.href = "/admin/login?url="
                            + window.location.pathname
                            + window.location.search
                            + window.location.hash;
                            //未登录
                        } else if (code <= -2 && code >= -7) {
                            //必填字段未填写
                            $scope.showAlert(rs.msg);
                        } else if (code == -8) {
                            //暂无数据
                            $scope.isNoData=true;
                            $scope.pageCount = 0;
                        }

                    }, function (result) {


                        var status = result.status;
                        if (status == -1) {
                            $scope.showAlert("服务器错误")
                        } else if (status >= 404 && status < 500) {
                            $scope.showAlert("请求路径错误")
                        } else if (status >= 500) {
                            $scope.showAlert("服务器错误")
                        }
                    });
            },
            ///积分规则 -- 非连续签到积分规则 -- 查询
            //findAllSignIntegrationRule:"/admin/biz/RuleSetting/findAllSignIntegrationRule",
            findAllSignIntegrationRules:function($scope,$http){
                var data={};
                $http.post(remoteUrl.findAllSignIntegrationRules,data).then(
                    function (result) {

                        var rs = result.data;
                        var code = rs.code;
                        var data = rs.data;
                        //code=-8;
                        if (code == 1) {
                            $scope.listSignIntegrationRuleEntity = data.listSignIntegrationRuleEntity;
                            for(let j=0;j<$scope.listSignIntegrationRuleEntity.length;j++){
                                $scope.strEnabledsss = $scope.listSignIntegrationRuleEntity[j].strEnabled;
                            }
                        } else if (code == -1) {
                            window.location.href = "/admin/login?url="
                            + window.location.pathname
                            + window.location.search
                            + window.location.hash;
                            //未登录
                        } else if (code <= -2 && code >= -7) {
                            //必填字段未填写
                            $scope.showAlert(rs.msg);
                        } else if (code == -8) {
                            //暂无数据
                            $scope.isNoData=true;
                            $scope.pageCount = 0;
                        }

                    }, function (result) {

                        var status = result.status;
                        if (status == -1) {
                            $scope.showAlert("服务器错误")
                        } else if (status >= 404 && status < 500) {
                            $scope.showAlert("请求路径错误")
                        } else if (status >= 500) {
                            $scope.showAlert("服务器错误")
                        }
                    });
            },
            //findAllSignIntegrationRule
            ///积分规则 -- 连续签到积分规则 -- 查询
            findAllSignIntegrationRule:function($scope,$http){
                var data={};
                $http.post(remoteUrl.findAllSignIntegrationRule,data).then(
                    function (result) {

                        var rs = result.data;
                        var code = rs.code;
                        var data = rs.data;
                        //code=-8;
                        if (code == 1) {
                            $scope.listSignIntegrationRuleEntitys = data.listSignIntegrationRuleEntity;
                        } else if (code == -1) {
                            window.location.href = "/admin/login?url="
                            + window.location.pathname
                            + window.location.search
                            + window.location.hash;
                            //未登录
                        } else if (code <= -2 && code >= -7) {
                            //必填字段未填写
                            $scope.showAlert(rs.msg);
                        } else if (code == -8) {
                            //暂无数据
                            $scope.isNoData=true;
                            $scope.pageCount = 0;
                        }

                    }, function (result) {

                        var status = result.status;
                        if (status == -1) {
                            $scope.showAlert("服务器错误")
                        } else if (status >= 404 && status < 500) {
                            $scope.showAlert("请求路径错误")
                        } else if (status >= 500) {
                            $scope.showAlert("服务器错误")
                        }
                    });
            },
            ////////////////////积分规则-结束/////////////////////////

			////////////////////广告轮播-图片开始/////////////////////////
			uploadImages: function($scope, $http, fileReader) {
				$scope.LoopAdvPic = {};
				//删除广告轮播-单条查询
				$scope.delectExpand = function(strAdvPicId, strAdvPicName) {

					$scope.showConfirm("确认要删除" + strAdvPicName + "？", function(rs) {
						//调用删除删除图片轮播函数
						if(rs) {
							$scope.delLoopAdvPic(strAdvPicId, $scope, $http);
						}

					})
				}
				//保存
				$scope.submitExpandinfo = function() {
					if($scope.typePanduan) {
						//调用修改接口
						$scope.updateLoopAdvPic($scope.LoopAdvPic, $scope, $http);
					} else {
						//调用保存接口
						$scope.insertLoopAdvPic($scope.LoopAdvPic, $scope, $http);
					}
				};
				//关闭窗口
				$scope.clostExpandWindow = function() {
					$scope.showExpandInfoWindow = false;
					$scope.goodsTypeList = {};
					$scope.getListLoopAdvPic($scope, $http);
				};
				//新增
				$scope.newExpandinginfo = function() {

					$scope.showExpandInfoWindow = true;
					$scope.isAddNewExpand = true;
					//判断执行添加还是修改
					$scope.typePanduan = false;
				};
				//修改广告轮播图片
				$scope.updataExpand = function(strAdvPicId) {

					$scope.showExpandInfoWindow = true;
					$scope.isAddNewExpand = false;
					//判断执行添加还是修改
					$scope.typePanduan = true;
					//调用接口
					$scope.getLoopAdvPic(strAdvPicId, $scope, $http);
				};

				//删除广告轮播单条数据
				$scope.delLoopAdvPic = function(strAdvPicId, $scope, $http) {

					var data = {
						'strAdvPicId': strAdvPicId
					};
					$http.post(remoteUrl.delLoopAdvPic, data).then(
						function(result) {
							var rs = result.data;
							var code = rs.code;
							var data = rs.data;
							if(code == 1) {

								window.location.reload();

							} else if(code == -1) {
								window.location.href = "/admin/login?url=" +
									window.location.pathname +
									window.location.search +
									window.location.hash;
								//未登录
							} else if(code <= -2 && code >= -7) {
								//必填字段未填写
								$scope.showAlert(rs.msg);
							} else if(code == -8) {
								$scope.showAlert(rs.msg);
							}

						},
						function(result) {

							var status = result.status;
							if(status == -1) {
								$scope.showAlert("服务器错误")
							} else if(status >= 404 && status < 500) {
								$scope.showAlert("请求路径错误")
							} else if(status >= 500) {
								$scope.showAlert("服务器错误")
							}
						});
				};
				//广告轮播修改
				$scope.updateLoopAdvPic = function(data, $scope, $http) {
					$http.post(remoteUrl.updateLoopAdvPic, data).then(
						function(result) {

							var rs = result.data;
							var code = rs.code;
							var data = rs.data;

							if(code == 1) {

								$scope.showAlert("保存成功", function() {
									window.location.reload();
								});

							} else if(code == -1) {
								window.location.href = "/admin/login?url=" +
									window.location.pathname +
									window.location.search +
									window.location.hash;
								//未登录
							} else if(code <= -2 && code >= -7) {
								//必填字段未填写
								$scope.showAlert(rs.msg);
							} else if(code == -8) {

							}

						},
						function(result) {

							var status = result.status;
							if(status == -1) {
								$scope.showAlert("服务器错误")
							} else if(status >= 404 && status < 500) {
								$scope.showAlert("请求路径错误")
							} else if(status >= 500) {
								$scope.showAlert("服务器错误")
							}
						});
				};

				//广告轮播－查询单条
				$scope.getLoopAdvPic = function(strAdvPicId, $scope, $http) {

					var data = { "strAdvPicId": strAdvPicId }
					$http.post(remoteUrl.getLoopAdvPic, data).then(
						function(result) {

							var rs = result.data;
							var code = rs.code;
							var data = rs.data;
							var LoopAdvPic = data.LoopAdvPic;
							if(code == 1) {
								angular.forEach(LoopAdvPic, function(val, key) {
									$scope.LoopAdvPic[key] = val;
								})

							} else if(code == -1) {
								window.location.href = "/admin/login?url=" +
									window.location.pathname +
									window.location.search +
									window.location.hash;
								//未登录
							} else if(code <= -2 && code >= -7) {
								//必填字段未填写
								$scope.showAlert(rs.msg);
							} else if(code == -8) {

							}

						},
						function(result) {

							var status = result.status;
							if(status == -1) {
								$scope.showAlert("服务器错误")
							} else if(status >= 404 && status < 500) {
								$scope.showAlert("请求路径错误")
							} else if(status >= 500) {
								$scope.showAlert("服务器错误")
							}
						});
				};
				//广告轮播-新增图片
				$scope.insertLoopAdvPic = function(data, $scope, $http) {
					$http.post(remoteUrl.insertLoopAdvPic, data).then(
						function(result) {

							var rs = result.data;
							var code = rs.code;
							var data = rs.data;

							if(code == 1) {

								$scope.showAlert("保存成功", function() {
									window.location.reload();
								});

							} else if(code == -1) {
								window.location.href = "/admin/login?url=" +
									window.location.pathname +
									window.location.search +
									window.location.hash;
								//未登录
							} else if(code <= -2 && code >= -7) {
								//必填字段未填写
								$scope.showAlert(rs.msg);
							} else if(code == -8) {

							}

						},
						function(result) {

							var status = result.status;
							if(status == -1) {
								$scope.showAlert("服务器错误")
							} else if(status >= 404 && status < 500) {
								$scope.showAlert("请求路径错误")
							} else if(status >= 500) {
								$scope.showAlert("服务器错误")
							}
						});
				};
				// 回显图片
				$scope.getFile = function() {
					fileReader.readAsDataUrl($scope.file, $scope).then(
						function(result) {
							$scope.imageSrc = result;
							$scope.uploadimage();
						});
				};
				// 上传图片
				$scope.uploadimage = function() {
					// 组装表单数据
					var postData = {
						file: $scope.myFile
					};
					var promise = postMultipart(remoteUrl.uploadImage, postData);
					promise.then(function(result) {
						var rs = result.data;
						var code = rs.code;
						if(code == 1) {
							$scope.newExpandinginfo();
							$scope.strAdvPicName = rs.data.strImgpath;
						} else if(code == -1) {
							window.location.href = "/admin/login?url=" +
								window.location.pathname +
								window.location.search +
								window.location.hash;
							// 未登录
						} else if(code <= -2 && code >= -7) {
							// 必填字段未填写
							$scope.showAlert(rs.msg);
						} else if(code == 100004) {
							$scope.showAlert(rs.msg);
						}

					}, function(result) {
						$scope.editFrontinfoisActive = true;
						var status = result.status;
						if(status == -1) {
							$scope.showAlert("服务器错误")
						} else if(status >= 404 && status < 500) {
							$scope.showAlert("请求路径错误")
						} else if(status >= 500) {
							$scope.showAlert("服务器错误")
						}
					});

					function postMultipart(url, data) {
						var fd = new FormData();
						angular.forEach(data, function(val, key) {
							fd.append(key, val);
						});
						var args = {
							method: 'POST',
							url: url,
							data: fd,
							headers: {
								'Content-Type': undefined
							},
							transformRequest: angular.identity
						};
						return $http(args);
					}
				};

				//广告轮播查询所有
				$scope.getListLoopAdvPic = function($scope, $http) {
					$http.post(remoteUrl.getListLoopAdvPic).then(
						function(result) {
							var rs = result.data;
							var code = rs.code;
							var data = rs.data;
							if(code == 1) {
								$scope.listLoopAdvPic = data.listLoopAdvPic
							} else if(code == -1) {
								window.location.href = "/admin/login?url=" +
									window.location.pathname +
									window.location.search +
									window.location.hash;
								//未登录
							} else if(code <= -2 && code >= -7) {
								//必填字段未填写
								$scope.showAlert(rs.msg);
							} else if(code == -8) {
								//暂无数据
								$scope.isNoData = true;
								$scope.pageCount = 0;
							}

						},
						function(result) {

							var status = result.status;
							if(status == -1) {
								$scope.showAlert("服务器错误")
							} else if(status >= 404 && status < 500) {
								$scope.showAlert("请求路径错误")
							} else if(status >= 500) {
								$scope.showAlert("服务器错误")
							}
						});
				};
				$scope.getListLoopAdvPic($scope, $http);
			}
			////////////////////广告轮播-图片结束/////////////////////////
		}
		return activityCtrl;

	});