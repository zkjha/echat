/**
 * Created by liupengyan on 17/3/17.
 * 过滤订单状态
 */
define(['lib/angular'],function(angular){
    'use strict'
    return angular.module("numberFilter",[]).filter('numberFilter',function(){
        return function(isnum){
            isnum = parseInt(isnum);

            return isnum;
        }

    });
});