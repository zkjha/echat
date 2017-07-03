/**
 * Created by Administrator on 2017/7/3.
 */

/**
 * Created by zhujunliang on 17/4/3.
 * 处理收银控制器
 */
define(
    ['lib/remoteUrl'], function (remoteUrl) {
        'use strict'
        console.info(122444)
        var integrationMallCont = {
            hotRechargeShop:function($scope,$http,$rootScope){

                $rootScope.jiazaihenaho = function(){
                    integrationMallCont.selectGoordsInfo($scope, $http);
                }


                //integrationMallCont.selectGoordsInfo($scope, $http);
                //每页显示数量
                $scope.iPageSize = 2;
                // 当前页数
                $scope.currentPage = 1;
                // 总页数
                $scope.totalPages = 2;
                // 防止重复加载
                $scope.busy = false;
                // 存放数据
                $scope.items = [];
                // 请求数据方法
                $scope.loadMore = function() {
                    //$scope.listGoodsVO = {};

                    console.info($scope.currentPage)
                    $scope.jiazaiwancheng = ($scope.currentPage <= $scope.totalPages);
                    console.info($scope.currentPage <= $scope.totalPages)
                    if ($scope.currentPage <= $scope.totalPages) {
                        //if ($scope.busy) {
                        //    return false;
                        //}
                        //$scope.busy = true;
                        // 请求后台服务器
                        console.info("呵呵呵")
                        //var
                        var Allnews = $scope.currentPage* $scope.iPageSize
                        //integrationMallCont.selectGoordsInfo($scope, $http,$scope.currentPage);
                        integrationMallCont.selectGoordsInfo($scope, $http,Allnews);
                        $scope.currentPage++;

                    }
                };
                $scope.loadMore();
            },
            selectGoordsInfo:function($scope,$http,currentPage){
               var data={
                   iPageSize:currentPage,
                   iSearchGoodsState:$scope.iSearchGoodsState
                   //iPageSize:1,
                   //iPageNum:currentPage
                }
                $http.post(remoteUrl.selectGoordsInfo,data).then(
                    function(result){

                        var rs =result.data;
                        var code =rs.code;
                        var data=rs.data;
                        console.info(result)
                        if(code==1){
                            $scope.totalPages = data.iTotalPage;
                            $scope.iTotalRecord = data.iTotalRecord;
                            $scope.listGoodsVO = data.listGoodsVO;
                            console.info(data.listGoodsVO)
                           console.info(result)
                        }else if(code ==-1){
                            //得到登录路径
                            window.location.href="/admin/login?url="+window.location.pathname+window.location.search+window.location.hash;
                            //未登录
                        }else if(code<=-2&&code>=-7){
                            //必填字段未填写
                            $scope.showAlert(data.msg);
                        }else if(code ==-8){
                            //暂无数据
                        }

                    },
                    function(result){
                        var status=result.status;
                        if(status==-1){
                            $scope.showAlert("服务器错误")
                        }else if(status>=404&&status<500){
                            $scope.showAlert("请求路径错误")
                        }else if(status>=500){
                            $scope.showAlert("服务器错误")
                        }
                    }
                )
            }
            }
        return integrationMallCont;

    });
