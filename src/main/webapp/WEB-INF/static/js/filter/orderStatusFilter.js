/**
 * Created by liupengyan on 17/3/17.
 * 过滤订单状态
 */
define(['lib/angular'],function(angular){
    'use strict'
    return angular.module("orderStatusFilter",[]).filter('orderStatusFilter',function(){
        return function(intStatus){

            if(intStatus==1){
                return "已支付 ";
            }else if(intStatus==0){
                return "待支付";
            }else if(intStatus==2){
                return "已发货 ";
            }else if(intStatus==3){
                return "已完成 ";
            }else{
                return "未知";
            }
        }

    });
});