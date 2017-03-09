/**
 * Created by liupengyan on 17/3/8.
 * 替换员工状态
 */
define(['lib/angular'],function(angular){
    'use strict'
    return angular.module("EmployeeFilter",[]).filter('replaceEmployeeStatus',function(){
        return function(intStatus){

            if(intStatus==1){
                return "激活";
            }else if(intStatus==0){
                return "禁用";
            }else{
                return "未知";
            }
        }

    });
});