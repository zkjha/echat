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
require(['lib/angular','lib/remoteUrl','controller/weixin/voucherTicketInfoController','lib/angular-route', 'lib/requestParamUtill', 'dirctive/tipsDirctive','lib/jquery'], function(angular,remoteUrl,voucherTicketInfoController) {
    console.info(voucherTicketInfoController)
    var app = angular.module("menbercent", ['httphelper','ngRoute', 'tips']);

    //路由配置
    app.config(["$routeProvider", function($routeProvider) {
        $routeProvider.when("/voucherTicketInfoNotuse", {
            templateUrl: "/static/temp/weixin/voucherTicketInfo/voucherTicketInfoNotuse.html",
            controller:voucherTicketInfoController.voucherTicketInfoNotuse

        }).when("/voucherTicketInfoUse", {
            templateUrl: "/static/temp/weixin/voucherTicketInfo/voucherTicketInfoUse.html",
            controller:voucherTicketInfoController.voucherTicketInfoUse

        }).when("/voucherTicketInfoGuoqi", {
            templateUrl: "/static/temp/weixin/voucherTicketInfo/voucherTicketInfoGuoqi.html",
            controller:voucherTicketInfoController.voucherTicketInfoGuoqi

        }).otherwise({
            redirectTo: "/voucherTicketInfoNotuse"
        });


    }]);

    app.controller('voucherTicketInfo',['$scope','$http',function($scope,$http){


    }]);

    angular.bootstrap(document, ["menbercent"]);
});
