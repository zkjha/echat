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
            //scope:{
            //    strEnabledsss:'='
            //},
            //scope: false,
            templateUrl: "/static/temp/admin/activity/integralrulesDirective.html",
            controller:function($scope, $element, $attrs){
                var z_qy =  $element[0].children[0].children[0].children[0].children[0];
                var z_jz = $element[0].children[0].children[1].children[0].children[0];
                $scope.strEnabledsss = $scope.panduanxuanzhong;
                z_qy.onclick = function(){
                    $scope.strEnabledsss = 1;
                    z_qy.checked = true;
                    z_jz.checked = false;
                    $scope.panduanxuanzhong = 1;
                };
                z_jz.onclick = function(){
                    $scope.strEnabledsss = 0;
                    z_jz.checked = true;
                    z_qy.checked = false;
                    $scope.panduanxuanzhong = 0;
                };
            }
        };

    }]).directive("panduanXflx",[function(){
        return {
            restrict: "E", //default: EA
            replace: true,
            //scope:false,
            //scope: false,
            templateUrl: "/static/temp/admin/activity/xiaofeileixingDirective.html",
            controller:function($scope, $element, $attrs){
                var zjl_xjczxf = document.getElementById("zjl_xjczxf");
                var zjl_xjxsxf = document.getElementById("zjl_xjxsxf");
                $scope.zjl_xjczxf = function(){
                    $(zjl_xjczxf).addClass('zjl_xfzs_pd');
                    $(zjl_xjczxf).removeClass('zjl_xfzs_pdO');
                    $(zjl_xjxsxf).removeClass('zjl_xfzs_pd');
                    $(zjl_xjxsxf).addClass('zjl_xfzs_pdO');
                    $scope.panduanXfzsXy = 0;
                    console.info($scope.panduanXfzsXy)
                }
                $scope.zjl_xjxsxf = function(){
                    $(zjl_xjczxf).addClass('zjl_xfzs_pdO');
                    $(zjl_xjczxf).removeClass('zjl_xfzs_pd');
                    $(zjl_xjxsxf).removeClass('zjl_xfzs_pdO');
                    $(zjl_xjxsxf).addClass('zjl_xfzs_pd');
                    $scope.panduanXfzsXy = 1;
                    console.info($scope.panduanXfzsXy)
                }
            }
        };

    }])
});