/**
 * Created by zhujunliang on 2017/7/6.
 */
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
require(['lib/angular','lib/remoteUrl','controller/weixin/generateOrderController','lib/angular-route', 'lib/requestParamUtill', 'dirctive/tipsDirctive','lib/jquery'], function(angular,remoteUrl,generateOrderController) {
    console.info(generateOrderController)
    var app = angular.module("menbercent", ['httphelper','ngRoute', 'tips']);

    //路由配置
    app.config(["$routeProvider", function($routeProvider) {
        $routeProvider.when("/generateOrderPre", {
            templateUrl: "/static/temp/weixin/generateOrder/generateOrderPre.html",
            controller:generateOrderController.generateOrderPre

        }).when("/generateOrderDetail", {
            templateUrl: "/static/temp/weixin/generateOrder/generateOrderDetail.html",
            controller:generateOrderController.generateOrderDetail

        }).otherwise({
            redirectTo: "/generateOrderPre"
        });


    }]);

    app.controller('generateOrder',['$scope','$http',function($scope,$http){


    }]);

    angular.bootstrap(document, ["menbercent"]);
});
