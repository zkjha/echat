/**
 * Created by liupengyan on 17/3/17.
 * 支付方式
 */
define(['lib/angular'],function(angular){
    'use strict'
    return angular.module("payTypeFilter",[]).filter('payTypeFilter',function(){
        return function(intStatus){

            if(intStatus==1){
                return "微信支付 ";
            }else if(intStatus==0){
                return "积分兑换";
            }else if(intStatus==2){
                return "支付宝支付";
            }else{
                return "未知";
            }
        }

    });
});