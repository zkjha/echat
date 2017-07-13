/**
 * Created by zhujunliang on 2017/7/6.
 */
define(
    ['lib/remoteUrl'], function (remoteUrl) {
        'use strict'
        var consumeRecordController = {
            //DyqconsumeRecord
            ///////////////////////抵用券消费记录-- 分页查询///////////////////////
            DyqconsumeRecord:function($scope,$http){
                console.info("我爱你888");

                consumeRecordController.selectMerchantAddress($scope,$http)//商家地址查询单条

                //$scope.ifdiyongquanhave = true;
                $scope.panduanshifoujiazai = true;//判断是否加载；
                $scope.iPageNum = 1;//起始页数
                $scope.bianhuaiPageNum = $scope.iPageNum;
                $scope.iPageSize = 5;//每页显示数量
                $scope.bianhuaiPageSize = $scope.iPageSize;
                $scope.loadMore = function(){
                    if($scope.panduanshifoujiazai){
                        consumeRecordController.selectVoucherTicketConsume($scope,$http);//抵用券消费记录-- 分页查询
                    }
                };
                $scope.loadMore();
            },
            //抵用券分页查询
            selectVoucherTicketConsume:function($scope,$http){
                var date={
                    iPageNum:$scope.iPageNum,
                    iPageSize:$scope.iPageSize
                };
                $http.post(remoteUrl.selectVoucherTicketConsume,date).then(
                    function(result){
                        var rs =result.data;
                        var code =rs.code;
                        var data=rs.data;
                        console.info(result)
                        if(code==1){
                            $scope.iTotalPage = data.iTotalPage;
                            $scope.iTotalRecord = data.iTotalRecord;
                            if($scope.iPageSize<$scope.iTotalRecord){//分页查询
                                $scope.bianhuaiPageNum  += 1;
                                $scope.iPageSize = $scope.bianhuaiPageSize * $scope.bianhuaiPageNum;
                            }else{
                                $scope.panduanshifoujiazai = false;
                            }
                            $scope.returnListOrderVo = data.returnListOrderVo;
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
            ///////////////////////现金消费记录-- 分页查询///////////////////////
            CashconsumeRecord:function($scope,$http){
                console.info("我爱你888");

                consumeRecordController.selectMerchantAddress($scope,$http)//商家地址查询单条

                //$scope.ifdiyongquanhave = true;
                $scope.panduanshifoujiazai = true;//判断是否加载；
                $scope.iPageNum = 1;//起始页数
                $scope.bianhuaiPageNum = $scope.iPageNum;
                $scope.iPageSize = 5;//每页显示数量
                $scope.bianhuaiPageSize = $scope.iPageSize;
                $scope.loadMore = function(){
                    if($scope.panduanshifoujiazai){
                        consumeRecordController.selectCashConsume($scope,$http);//现金消费记录-- 分页查询
                    }
                };
                $scope.loadMore();
            },
            //现金分页查询
            selectCashConsume:function($scope,$http){
                var date={
                    iPageNum:$scope.iPageNum,
                    iPageSize:$scope.iPageSize
                };
                $http.post(remoteUrl.selectCashConsume,date).then(
                    function(result){
                        var rs =result.data;
                        var code =rs.code;
                        var data=rs.data;
                        console.info(result)
                        if(code==1){
                            $scope.iTotalPage = data.iTotalPage;
                            $scope.iTotalRecord = data.iTotalRecord;
                            if($scope.iPageSize<$scope.iTotalRecord){//分页查询
                                $scope.bianhuaiPageNum  += 1;
                                $scope.iPageSize = $scope.bianhuaiPageSize * $scope.bianhuaiPageNum;
                            }else{
                                $scope.panduanshifoujiazai = false;
                            }
                            $scope.returnListOrderVo = data.returnListOrderVo;
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
            ////////////////////////储值消费记录-- 分页查询///////////////////////
            StoreconsumeRecord:function($scope,$http){
                console.info("我爱你888");

                consumeRecordController.selectMerchantAddress($scope,$http)//商家地址查询单条

                //$scope.ifdiyongquanhave = true;
                $scope.panduanshifoujiazai = true;//判断是否加载；
                $scope.iPageNum = 1;//起始页数
                $scope.bianhuaiPageNum = $scope.iPageNum;
                $scope.iPageSize = 5;//每页显示数量
                $scope.bianhuaiPageSize = $scope.iPageSize;
                $scope.loadMore = function(){
                    if($scope.panduanshifoujiazai){
                        consumeRecordController.selectStoredValueConsume($scope,$http);//储值消费记录-- 分页查询
                    }
                };
                $scope.loadMore();
            },
            //储值分页查询
            selectStoredValueConsume:function($scope,$http){
                var date={
                    iPageNum:$scope.iPageNum,
                    iPageSize:$scope.iPageSize
                };
                $http.post(remoteUrl.selectStoredValueConsume,date).then(
                    function(result){
                        var rs =result.data;
                        var code =rs.code;
                        var data=rs.data;
                        console.info(result)
                        if(code==1){
                            $scope.iTotalPage = data.iTotalPage;
                            $scope.iTotalRecord = data.iTotalRecord;
                            if($scope.iPageSize<$scope.iTotalRecord){//分页查询
                                $scope.bianhuaiPageNum  += 1;
                                $scope.iPageSize = $scope.bianhuaiPageSize * $scope.bianhuaiPageNum;
                            }else{
                                $scope.panduanshifoujiazai = false;
                            }
                            $scope.returnListOrderVo = data.returnListOrderVo;
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
            ////////////////////////积分消费记录-- 分页查询///////////////////////
            integralconsumeRecord:function($scope,$http){
                console.info("我爱你888");

                consumeRecordController.selectMerchantAddress($scope,$http)//商家地址查询单条

                //$scope.ifdiyongquanhave = true;
                $scope.panduanshifoujiazai = true;//判断是否加载；
                $scope.iPageNum = 1;//起始页数
                $scope.bianhuaiPageNum = $scope.iPageNum;
                $scope.iPageSize = 5;//每页显示数量
                $scope.bianhuaiPageSize = $scope.iPageSize;
                $scope.loadMore = function(){
                    if($scope.panduanshifoujiazai){
                        consumeRecordController.selectIntegrationConsume($scope,$http);//全部消费记录-- 分页查询
                    }
                };
                $scope.loadMore();
            },
            //商家地址查询单条
            selectMerchantAddress:function($scope,$http){
                $http.post(remoteUrl.selectMerchantAddress).then(
                    function(result){
                        var rs =result.data;
                        var code =rs.code;
                        var data=rs.data;
                        console.info(result)
                        if(code==1){
                           $scope.strAddress = data.strAddress
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
            //积分消费记录-- 分页查询
            selectIntegrationConsume:function($scope,$http){
                var date={
                    iPageNum:$scope.iPageNum,
                    iPageSize:$scope.iPageSize
                };
                $http.post(remoteUrl.selectIntegrationConsume,date).then(
                    function(result){
                        var rs =result.data;
                        var code =rs.code;
                        var data=rs.data;
                        console.info(result)
                        if(code==1){
                            $scope.iTotalPage = data.iTotalPage;
                            $scope.iTotalRecord = data.iTotalRecord;
                            if($scope.iPageSize<$scope.iTotalRecord){//分页查询
                                $scope.bianhuaiPageNum  += 1;
                                $scope.iPageSize = $scope.bianhuaiPageSize * $scope.bianhuaiPageNum;
                            }else{
                                $scope.panduanshifoujiazai = false;
                            }
                            $scope.returnListOrderVo = data.returnListOrderVo;
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

            ////////////////////////全部消费记录-- 分页查询///////////////////////
            allconsumeRecord:function($scope,$http){
                console.info("我爱你888");
                //$scope.ifdiyongquanhave = true;
                $scope.panduanshifoujiazai = true;//判断是否加载；
                $scope.iPageNum = 1;//起始页数
                $scope.bianhuaiPageNum = $scope.iPageNum;
                $scope.iPageSize = 15;//每页显示数量
                $scope.bianhuaiPageSize = $scope.iPageSize;
                $scope.loadMore = function(){
                    if($scope.panduanshifoujiazai){
                        consumeRecordController.selectAllConsumeInfo($scope,$http);//全部消费记录-- 分页查询
                    }
                };
                $scope.loadMore();
            },
            //全部消费记录-- 分页查询
            selectAllConsumeInfo:function($scope,$http){
               var date={
                    iPageNum:$scope.iPageNum,
                    iPageSize:$scope.iPageSize
                };
                $http.post(remoteUrl.selectAllConsumeInfo,date).then(
                    function(result){
                        var rs =result.data;
                        var code =rs.code;
                        var data=rs.data;
                        console.info(result)
                        if(code==1){
                            $scope.iTotalPage = data.iTotalPage;
                            $scope.iTotalRecord = data.iTotalRecord;
                            if($scope.iPageSize<$scope.iTotalRecord){//分页查询
                                $scope.bianhuaiPageNum  += 1;
                                $scope.iPageSize = $scope.bianhuaiPageSize * $scope.bianhuaiPageNum;
                            }else{
                                $scope.panduanshifoujiazai = false;
                            }
                            $scope.returnListOrderVo = data.returnListOrderVo;
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
        return consumeRecordController;

    });
