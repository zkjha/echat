/**
 * Created by zhujunliang on 17/3/9.
 */

require.config({
    baseUrl : EK.STATIC_ROOT + "js" ,
    shim:{
        'lib/angular':{
            exports:'angular'
        },
        //integrationMallCont
        'lib/angular-route':{
            deps:['lib/angular'],
            exports: 'angular-route'
        }
    }
});
require(['lib/angular','controller/weixin/integrationMallcontroller', 'lib/remoteUrl','lib/angular-route', 'lib/requestParamUtill', 'dirctive/tipsDirctive','lib/jquery','dirctive/weixin/rightShow'], function(angular,integrationMallCont,remoteUrl) {
    console.info(integrationMallCont)
        var app = angular.module("menbercent", ['httphelper','ngRoute', 'tips','rightShow']);

        //路由配置   listHotRechargeShop
        app.config(["$routeProvider", function($routeProvider) {
            $routeProvider.when("/hotRechargeShop", {
                templateUrl: "/static/temp/weixin/integrationMall/hotRechargeShop.html",
                controller:integrationMallCont.hotRechargeShop

            }).when("/listHotRechargeShop", {
                templateUrl: "/static/temp/weixin/integrationMall/listHotRechargeShop.html",
                controller:integrationMallCont.hotRechargeShop

            }).otherwise({
                redirectTo: "/hotRechargeShop"
            });


        }]);
        //    滚动加载
    app.directive('whenScrolled', function() {
            return function(scope, elm, attr) {
                // 内层DIV的滚动加载
                $(window).scroll(function () {
                    //滚动条距离顶部的距离
                    var scrollTop = $(window).scrollTop();
                    //滚动条的高度
                    var scrollHeight = $(document).height();
                    //窗口的高度
                    var windowHeight = $(window).height();
                    //console.info(windowHeight+"..."+scrollHeight+",,,,,"+scrollTop)
                    if (scrollTop + windowHeight >= scrollHeight) {
                        console.info("我爱你")
                        scope.$apply(attr.whenScrolled);
                    }
                });
            };
        });

        app.controller('integrationMall',['$scope','$http',"$rootScope",function($scope,$http,$rootScope){
            //默认打开热门商品
            $scope.isActiveOne = false;
            $scope.isActiveTwo = true;

            $scope.hotShop = function(){
                $scope.listGoodsVO = {};
                $scope.isActiveOne = false;
                $scope.isActiveTwo = true;
                $scope.iSearchGoodsState = 0;
                $rootScope.jiazaihenaho();
                //$scope.loadMore();
            }
            $scope.newShop = function(){
                $scope.listGoodsVO = {};
                $scope.isActiveOne = true;
                $scope.isActiveTwo = false;
                $scope.iSearchGoodsState = 1;
                $rootScope.jiazaihenaho();
                //$scope.loadMore();
            }


        //  .////////////////////////////登录会员信息 -- 查询
            $http.post(remoteUrl.selectMemberInfo).then(
                function(result){

                    var rs =result.data;
                    var code =rs.code;
                    var data=rs.data;
                    console.info(result)
                    if(code==1){
                        $scope.intIntegration = data.intIntegration
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
                ////////////////////////////登会员中心 -- 签到信息(最近一次签到信息) -- 查询
                $http.post(remoteUrl.selectSignDays).then(
                function(result){

                    var rs =result.data;
                    var code =rs.code;
                    var data=rs.data;
                    console.info(result)
                    if(code==1){
                        $scope.iSignCount = data.weiXinMemberSignEntity.iSignCount
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
            ////////////////////////////赛选信息查询
            var selGoodsTypeListDetails= {
                pagenum:1,
                pagesize:99
            }
            $http.post(remoteUrl.selGoodsTypeList,selGoodsTypeListDetails).then(
                function(result){

                    var rs =result.data;
                    var code =rs.code;
                    var data=rs.data;
                    console.info(result)
                    if(code==1){

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

        }]);

        angular.bootstrap(document, ["menbercent"]);
    });
