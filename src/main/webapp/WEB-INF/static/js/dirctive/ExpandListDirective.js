/**
 * Created by liupengyan on 17/3/13.
 * 用于处理会员信息中显示拓展列表
 */

define(['lib/angular'],function(angular){
    'use strict'
    angular.module("expandList",[]).directive("expandlist",[function(){
        return {
            restrict: "E", //default: EA
            replace: true,
            templateUrl: "/static/temp/admin/expandList.html",
            scope:{

                expandData:"=",
                modelData:"=",
                getModelDataObject:"&",
                testFunction:"&"

            },
            controller:function($scope, $element, $attrs){

                $scope.modelData=[];
                $scope.modelDataObject={};





            }
        };

    }])
});