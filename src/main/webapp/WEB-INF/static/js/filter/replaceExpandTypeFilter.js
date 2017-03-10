/**
 * Created by liupengyan on 17/3/8.
 * 替换员工状态
 */
define(['lib/angular'],function(angular){
    'use strict'
    return angular.module("ExpandingType",[]).filter('replaceExpandType',function(){
        return function(intStatus){

            if(intStatus==1){
                return "下拉列表";
            }else if(intStatus==0){
                return "输入框";
            }else if(intStatus==2){
                return "多选";
            }else{
                return "未知";
            }
        }

    });
});