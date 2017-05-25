<<<<<<< HEAD
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

    }])
=======
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

    }])
>>>>>>> branch 'master' of git@linux.fushoukeji.com:/opt/gitdata/ecard.git
});