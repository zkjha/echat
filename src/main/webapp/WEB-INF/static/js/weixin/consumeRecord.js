/**
 * Created by zhujunliang on 2017/7/6.
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
require(['lib/angular','lib/remoteUrl','controller/weixin/consumeRecordController','lib/angular-route', 'lib/requestParamUtill', 'dirctive/tipsDirctive','lib/jquery'], function(angular,remoteUrl,consumeRecordController) {
    console.info(consumeRecordController)
    var app = angular.module("menbercent", ['httphelper','ngRoute', 'tips']);

    //路由配置
    app.config(["$routeProvider", function($routeProvider) {
        $routeProvider.when("/allconsumeRecord", {
            templateUrl: "/static/temp/weixin/consumeRecord/allconsumeRecord.html",
            controller:consumeRecordController.allconsumeRecord

        }).when("/integralconsumeRecord", {
            templateUrl: "/static/temp/weixin/consumeRecord/integralconsumeRecord.html",
            controller:consumeRecordController.integralconsumeRecord

        }).when("/StoreconsumeRecord", {
            templateUrl: "/static/temp/weixin/consumeRecord/StoreconsumeRecord.html",
            controller:consumeRecordController.StoreconsumeRecord

        }).when("/CashconsumeRecord", {
            templateUrl: "/static/temp/weixin/consumeRecord/CashconsumeRecord.html",
            controller:consumeRecordController.CashconsumeRecord

        }).when("/DyqconsumeRecord", {
            templateUrl: "/static/temp/weixin/consumeRecord/DyqconsumeRecord.html",
            controller:consumeRecordController.DyqconsumeRecord

        }).otherwise({
            redirectTo: "/allconsumeRecord"
        });
        //DyqconsumeRecord
    }]);

    app.controller('voucherTicketInfo',['$scope','$http',function($scope,$http){


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

                    setTimeout(function(){
                        console.info("我爱你");
                        scope.$apply(attr.whenScrolled);
                    },500)

                    //scope.loadMore();
                }
            });
        };
    });

    angular.bootstrap(document, ["menbercent"]);
});
