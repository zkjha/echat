/**
 * Created by zhujunliang on 2017/7/6.
 */
define(
    ['lib/remoteUrl'], function (remoteUrl) {
        'use strict'
        var voucherTicketInfoController = {
            ////////////////////////已经过期的抵用券///////////////////////
            voucherTicketInfoGuoqi:function($scope,$http){
                console.info("已经过期")
                $scope.ifdiyongquanhave = true;
                voucherTicketInfoController.selectExpiredVoucherTicketInfo($scope,$http);//过期抵用券 信息 -- 分页查询
            },
            //过期抵用券 信息 -- 分页查询
            selectExpiredVoucherTicketInfo:function($scope,$http){
                var date = {
                    iPageNum:1,
                    iPageSize:20
                }
                $http.post(remoteUrl.selectExpiredVoucherTicketInfo,date).then(
                    function(result){
                        var rs =result.data;
                        var code =rs.code;
                        var data=rs.data;
                        console.info(result)
                        if(code==1){
                            $scope.ifdiyongquanhave = false;
                            $scope.returnListVouncherEntity = data.returnListVouncherEntity;
                        }else if(code ==-1){
                            //得到登录路径
                            window.location.href="/admin/login?url="+window.location.pathname+window.location.search+window.location.hash;
                            //未登录
                        }else if(code<=-2&&code>=-7){
                            //必填字段未填写
                            $scope.showToast(rs.msg);
                        }else if(code ==-8){
                            //暂无数据
                            $scope.showToast(rs.msg);
                        }

                    },
                    function(result){
                        var status=result.status;
                        if(status==-1){
                            $scope.showToast("服务器错误")
                        }else if(status>=404&&status<500){
                            $scope.showToast("请求路径错误")
                        }else if(status>=500){
                            $scope.showToast("服务器错误")
                        }
                    }
                )
            },
            ////////////////////////已经使用的抵用券///////////////////////
            voucherTicketInfoUse:function($scope,$http){
                console.info("已经使用")
                $scope.ifdiyongquanhave = true;

                voucherTicketInfoController.selectUsedVoucherTicketInfo($scope,$http);//已使用抵用券信息 -- 分页查询
            },
            //已使用抵用券信息 -- 分页查询
            selectUsedVoucherTicketInfo:function($scope,$http){
                var date = {
                    iPageNum:1,
                    iPageSize:20
                }
                $http.post(remoteUrl.selectUsedVoucherTicketInfo,date).then(
                    function(result){
                        var rs =result.data;
                        var code =rs.code;
                        var data=rs.data;
                        console.info(result)
                        if(code==1){
                            $scope.ifdiyongquanhave = false;
                            $scope.returnListVouncherEntity = data.returnListVouncherEntity;
                        }else if(code ==-1){
                            //得到登录路径
                            window.location.href="/admin/login?url="+window.location.pathname+window.location.search+window.location.hash;
                            //未登录
                        }else if(code<=-2&&code>=-7){
                            //必填字段未填写
                            $scope.showToast(rs.msg);
                        }else if(code ==-8){
                            //暂无数据
                            $scope.showToast(rs.msg);
                        }

                    },
                    function(result){
                        var status=result.status;
                        if(status==-1){
                            $scope.showToast("服务器错误")
                        }else if(status>=404&&status<500){
                            $scope.showToast("请求路径错误")
                        }else if(status>=500){
                            $scope.showToast("服务器错误")
                        }
                    }
                )
            },

            ////////////////////////未使用的抵用券///////////////////////
            voucherTicketInfoNotuse:function($scope,$http){
                console.info("我爱你888");
                $scope.ifdiyongquanhave = true;
                voucherTicketInfoController.selectUnUsedVoucherTicketInfo($scope,$http);//已使用抵用券信息 -- 分页查询
            },
            //已使用抵用券信息 -- 分页查询
            selectUnUsedVoucherTicketInfo:function($scope,$http){
                $http.post(remoteUrl.selectUnUsedVoucherTicketInfo).then(
                    function(result){

                        var rs =result.data;
                        var code =rs.code;
                        var data=rs.data;
                        console.info(result)
                        if(code==1){
                            $scope.ifdiyongquanhave = false;
                            $scope.listVoucherTicketInfoEntity = data.listVoucherTicketInfoEntity;
                        }else if(code ==-1){
                            //得到登录路径
                            window.location.href="/admin/login?url="+window.location.pathname+window.location.search+window.location.hash;
                            //未登录
                        }else if(code<=-2&&code>=-7){
                            //必填字段未填写
                            $scope.showToast(rs.msg);
                        }else if(code ==-8){
                            //暂无数据
                            $scope.showToast(rs.msg);
                        }

                    },
                    function(result){
                        var status=result.status;
                        if(status==-1){
                            $scope.showToast("服务器错误")
                        }else if(status>=404&&status<500){
                            $scope.showToast("请求路径错误")
                        }else if(status>=500){
                            $scope.showToast("服务器错误")
                        }
                    }
                )
            }
        }
        return voucherTicketInfoController;

    });
