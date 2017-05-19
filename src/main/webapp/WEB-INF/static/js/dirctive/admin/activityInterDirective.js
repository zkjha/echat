/**
 * Created by liupengyan on 17/3/13.
 * 用于处理会员信息中显示拓展列表
 */

define(['lib/angular'],function(angular){
    'use strict'
    angular.module("integralrulesDirective",[]).directive("integralrulesDirective",[function(){
        return {
            restrict: "E", //default: EA
            replace: true,
            templateUrl: "/static/temp/admin/activity/integralrulesDirective.html",
            controller:function($scope, $element, $attrs){
                var z_qy =  $element[0].children[0].children[0].children[0].children[0];
                var z_jz = $element[0].children[0].children[1].children[0].children[0];
                $scope.strEnabled = $scope.panduanxuanzhong;
                let lens = angular.element(".z_pandun_if").length-1;
                console.info( $scope.panduanxuanzhong)
                z_qy.onclick = function(){
                    $scope.strEnabled = 1;
                    z_qy.checked = true;
                    z_jz.checked = false;
                    $scope.zhuangtai[lens] = 1;
                };
                z_jz.onclick = function(){
                    $scope.strEnabled = 0;
                    z_jz.checked = true;
                    z_qy.checked = false;
                    $scope.zhuangtai[lens] = 0;
                };
                $scope.zhuangtai.push($scope.strEnabled);
            }
        };

    }])
});