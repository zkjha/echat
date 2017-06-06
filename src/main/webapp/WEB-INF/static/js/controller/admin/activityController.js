/**
 * Created by zhujunliang on 17/5/1
 */
define(
	['lib/remoteUrl'],
	function(remoteUrl) {
		'use strict'

		var activityCtrl = {



            ////////////////////充值赠送-开始////////////////////////
            recharge:function($scope,$http){
                console.info(123)
                //储值券是否启用判断
                $scope.listRechargePresentsStoredValueEntitydyq = [{}];
                //抵用券是否启用判断
                $scope.listRechargePresentsVoucherEntitydyq = [{}];
                //判断是否调用储值券单挑查询结果
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
            // 充值赠送 -- 赠送活动 -- 刚新建活动信息查询 单条
                activityCtrl.selectRechargePresentsActivityEntity($scope,$http);
            //    点击新增按钮
                $scope.newExpandinginfo = function(){
                    $scope.addchuziquantianjianumber =1; //储值券中取值的判断
                    $scope.adddiyongquantianjianumber =1; //抵用券取值的判断
                    //清空所有查询的内容
                    $scope.rechargePresentsActivityEntity = {};
                    $scope.listRechargePresentsIntegrationEntity = {};
                    $scope.listRechargePresentsStoredValueEntity = {};
                    $scope.listRechargePresentsVoucherEntity = {};
                    $scope.showExpandInfoWindow = true;
                }
            //    点击关闭按钮
                $scope.clostExpandWindow = function(){
                    $scope.panduandiaoyungchuzhiquanjiekou = false;//判断是否调用了储值券接口没
                    $scope.panduandiaoyungdiyongquanjiekou = false;//判断是否调用了抵用券接口没
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
            //    点击抵用券部分的加号
            //    $scope.addStoreZs = function(ids){
            //                console.info(ids)
            //        console.info($scope.listRechargePresentsVoucherEntity)
            //            }

            //    添加储值券
                $scope.addchuziquantianjianumber =1;
                $scope.adddichuzhiquan = function(){
                    if($scope.panduandiaoyungchuzhiquanjiekou){
                        $scope.panduandiaoyungchuzhiquanjiekou = false;//判断调用了储值券接口没
                        $scope.addchuziquantianjianumber = $scope.listRechargePresentsStoredValueEntity.length;
                        console.info("我是用来测试的")
                    }
                    $scope.listRechargePresentsStoredValueEntity[$scope.addchuziquantianjianumber] = $scope.addchuziquantianjia;
                    $scope.addchuziquantianjianumber += 1;
                    $scope.chuzhiweikong = {};
                    $scope.showExpandInfoWindow = true;
                    $scope.showExpandInfoWindowCz = false;
                };
            //    添加抵用券
                $scope.adddiyongquantianjianumber =1;
                $scope.adddiyongquan = function(){
                    //if(!$scope.listRechargePresentsVoucherEntity.length){
                    //
                    //}else{
                    //    $scope.adddiyongquantianjianumber = $scope.listRechargePresentsVoucherEntity.length;
                    //}
                    if($scope.panduandiaoyungdiyongquanjiekou){
                        $scope.panduandiaoyungdiyongquanjiekou = false;//判断调用了储值券接口没
                        $scope.adddiyongquantianjianumber = $scope.listRechargePresentsVoucherEntity.length;
                        console.info("我是用来测试的")
                    }
                    $scope.listRechargePresentsVoucherEntity[$scope.adddiyongquantianjianumber] = $scope.diyongquanxinzeng;
                    $scope.adddiyongquantianjianumber += 1;
                    $scope.diyongquanweikong = {};
                    $scope.showExpandInfoWindow = true;
                    $scope.showExpandInfoWindowStore = false;
                    console.info($scope.listRechargePresentsVoucherEntity)
                }

            ////    调用抵用券模态框
                $scope.addStoreZs = function(paixu){
                    $scope.diyongquanxinzeng = {};
                    //$scope.chuzhiweikong;
                    if(!$scope.diyongquanweikong&& ! $scope.listRechargePresentsVoucherEntity.length){
                        $scope.showAlert("抵用券券相关内容不能为空！");
                    }else{
                        if(!paixu){
                            if(!$scope.diyongquanweikong){
                                $scope.listRechargePresentsVoucherEntity[0] = $scope.listRechargePresentsVoucherEntity[0];
                                $scope.diyongquanweikong = $scope.listRechargePresentsVoucherEntity[0];
                            }else{
                                $scope.listRechargePresentsVoucherEntity[0] = $scope.diyongquanweikong;
                            }
                        }
                        //console.info($scope.listRechargePresentsStoredValueEntity)
                        $scope.showExpandInfoWindow = false;
                        $scope.showExpandInfoWindowStore = true;
                    }

                }
                $scope.clostExpandWindowStore = function(){
                    $scope.showExpandInfoWindow = true;
                    $scope.showExpandInfoWindowStore = false;
                }
                //    调用储值券模态框

                $scope.addStoreCz = function(paixu){
                    $scope.addchuziquantianjia = {};
                    //$scope.chuzhiweikong;
                    if(!$scope.chuzhiweikong && ! $scope.listRechargePresentsStoredValueEntity.length){
                        $scope.showAlert("储值券相关内容不能为空！");
                    }else{
                        if(!paixu){
                            if(!$scope.chuzhiweikong){
                                $scope.listRechargePresentsStoredValueEntity[0] = $scope.listRechargePresentsStoredValueEntity[0];
                                $scope.chuzhiweikong = $scope.listRechargePresentsStoredValueEntity[0];
                            }else{
                                $scope.listRechargePresentsStoredValueEntity[0] = $scope.chuzhiweikong;
                            }
                        }
                        console.info($scope.listRechargePresentsStoredValueEntity)
                        $scope.showExpandInfoWindow = false;
                        $scope.showExpandInfoWindowCz = true;
                    }

                };
                //关闭储值券模态框
                $scope.clostExpandWindowCz = function(){
                    $scope.showExpandInfoWindow = true;
                    $scope.showExpandInfoWindowCz = false;
                };
                //新增
                $scope.submitExpandinfoAll = function(){
                    console.info($scope.rechargePresentsActivityEntity)
                    var rechargePresentsActivityEntity = $scope.rechargePresentsActivityEntity;
                    activityCtrl.insertPresentsActivityInfo($scope,$http,$scope.rechargePresentsActivityEntity)
                };
            },
            //充值赠送 -- 活动信息 -- 新增
            insertPresentsActivityInfo:function($scope,$http,rechargePresentsActivityEntity){
                var data = {
                    strActivityBeginTime:rechargePresentsActivityEntity.strActivityBeginTime,
                    strActivityEndTime:rechargePresentsActivityEntity.strActivityEndTime,
                    strActivityName:rechargePresentsActivityEntity.strActivityName,
                    strLevelsId:rechargePresentsActivityEntity.strLevelsId
                };
                $http.post(remoteUrl.insertPresentsActivityInfo,data).then(
                    function(result){
                        var rs = result.data;
                        var data = result.data;
                        var code = data.code;
                        if (code == 1) {
                            console.info(rs.msg)
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
                            console.info($scope.rechargePresentsActivityEntity)
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
                        console.info(result)
                        if (code == 1) {
                            $scope.listRechargePresentsVoucherEntity = data.data.listRechargePresentsVoucherEntity;
                            $scope.listRechargePresentsVoucherEntitydyq.shift();
                            $scope.listRechargePresentsVoucherEntitydyq.push(data.data.listRechargePresentsVoucherEntity[0]) ;//用来判断是否已经启用
                            $scope.panduandiaoyungdiyongquanjiekou = false;//判断是否调用了抵用券接口没
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
                            $scope.listRechargePresentsStoredValueEntitydyq.shift();
                            $scope.listRechargePresentsStoredValueEntitydyq.push(data.data.listRechargePresentsStoredValueEntity[0]);//用来判断是否已经启用
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
            updatePresentsActivityInfo:function($scope,$http,resultMap){
                var data = {
                    strActivityBeginTime:resultMap.strActivityBeginTime,
                    strActivityEndTime:resultMap.strActivityEndTime,
                    strActivityId:resultMap.strActivityId,
                    strActivityName:resultMap.strActivityName,
                    strLevelsId:resultMap.strLevelsId
                };
                $http.post(remoteUrl.updatePresentsActivityInfo,data).then(
                    function(result){
                        var rs = result.data;
                        var data = result.data;
                        var code = data.code;
                        console.info(result)
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
                        console.info(result)
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
                        console.info(result)
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
                        console.info(result)
                        if (code == 1) {
                            $scope.resultMap = data.data.resultMap;
                            console.info($scope.resultMap)
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
            firsttime:function($scope,$http){
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
                            activityCtrl.deleteVoucherTicketPresentsInfo($scope,$http,strVoucherTicketPresentsId);
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
                            activityCtrl.updateStoredTicketPresentsInfo($scope,$http,listStoredTicketPresentsEntity)
                            activityCtrl.updateFirstMemberInitiationIntegrationPresents($scope,$http,integrationPresentsEntity)
                            activityCtrl.insertAndUpdateVoucherTicketPresentsInfo($scope,$http,stringParam)
                            window.location.reload();
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
                            $scope.showAlert(rs.msg,function(){
                                //window.location.reload();
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