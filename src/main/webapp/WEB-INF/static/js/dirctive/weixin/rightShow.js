/**
 * Created by zhujunliang on 2017/7/3.
 */


define(['lib/angular'],function(angular){
    'use strict'
    angular.module("rightShow",[]).directive("rechangeRight",[function(){
        return {
            restrict: "E", //default: EA
            replace: true,
            templateUrl: "/static/temp/weixin/integrationMall/rechangeRight.html",
            controller:function($scope, $element, $attrs){
                $scope.rechangeRightShow =true
                console.info(123455784)
            }
        };

    }])
});