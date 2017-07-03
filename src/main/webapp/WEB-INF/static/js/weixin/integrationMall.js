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
require(['lib/angular','controller/weixin/integrationMallcontroller', 'lib/remoteUrl','lib/angular-route', 'lib/requestParamUtill', 'dirctive/tipsDirctive','lib/jquery'], function(angular,integrationMallCont,remoteUrl) {
    console.info(integrationMallCont)
        var app = angular.module("menbercent", ['httphelper','ngRoute', 'tips']);

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
                //console.info(elm)
                //attr.whenScrolled
                //var raw = elm[0];
                //window.scroll
                //elm.bind('menbercent', function() {
                //    if (raw.scrollTop + raw.offsetHeight >= raw.scrollHeight) {
                //        scope.$apply(attr.whenScrolled);
                //    }
                //
                //});
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

        }]);

        angular.bootstrap(document, ["menbercent"]);
    });
