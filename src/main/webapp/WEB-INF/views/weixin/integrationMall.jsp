    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
            <% String basePath = request.getContextPath();%>
        <!DOCTYPE html>
        <html>
        <head>
        <%@ include file="../version.jsp" %>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0,
        maximum-scale=1.0">
        <meta name="apple-mobile-web-app-capable" content="yes">
        <meta name="apple-mobile-web-app-status-bar-style" content="black">
        <meta name="renderer" content="webkit">
        <meta name="applicable-device" content="mobile">
        <meta http-equiv="X-UA-Compatible" content="IE=Edge">
        <meta http-equiv="Cache-Control" content="no-transform">
        <meta name="application-name" content="e-card">
        <meta name="description" content="e-card">

        <script type="text/javascript">

        (function(){
        var hotcss = '<script src="'+ EK.STATIC_ROOT+'js/lib/mobile_hotcss.js" ><\/script>';
        document.write(hotcss);

        var css ='<link type="text/css" rel="stylesheet" href="'+ EK.STATIC_ROOT+'css/libs/base.css">'
        +'<link type="text/css" rel="stylesheet" href="'+ EK.STATIC_ROOT+'css/libs/global.css">'
        +'<link type="text/css" rel="stylesheet" href="'+ EK.STATIC_ROOT+'css/pages/weixin/weixinGlobal.css">'
        +'<link type="text/css" rel="stylesheet" href="'+ EK.STATIC_ROOT+'css/pages/weixin/exchange.css">'
        document.write(css);
        })();
        </script>

        <title>微信首页</title>
        </head>
        <body ng-controller="integrationMall">
        <alert></alert>
        <comfirm></comfirm>
        <toast></toast>
        <%--<div ng-include="'/static/temp/weixin/integrationMall/rechangeRight.html'"></div>--%>
        <input type="text" ng-value="selectValue = listGoodsTypeConfigEntity" ng-model="selectValue" ng-hide="true"/>
        <rechange-right></rechange-right>

        <div class="mobile_content">
        <div class="z-exchange-top">
        <div class="z-exchange-top-left">
        <p class="z-exchange-top-left-number"><span>{{intIntegration}}</span>积分</p>
        <p class="z-exchange-top-left-day">已连续签到{{iSignCount}}天</p>
        </div>
        <div class="z-exchange-top-right">
        <div class="z-exchange-top-right-exchange">
        <a href="/weixin/biz/memberSign"><div class="z-exchange-top-right-gray z-exchange-top-right-exchange-white">签到+3积分</div></a>
        <div class="z-exchange-top-right-gray"></div>
        </div>
        </div>
        </div>
        <div class="z-exchange-popularShop">
        <a href="#!/listNewRechargeShop"><span ng-class="{true: 'z-exchange-popularShop-orange', false: 'z-exchange-popularShop-gray'}[isActiveOne]" ng-click="newShop()">本周新品</span></a>
        <a href="#!/hotRechargeShop"><span ng-class="{true: 'z-exchange-popularShop-orange', false: 'z-exchange-popularShop-gray'}[isActiveTwo]"  ng-click="hotShop()">热门商品</span></a>
        </div>
        <%--变化的部分--%>
        <div ng-view></div>
        <div class="tc" ><img src="/static/images/weixin/Loading1.gif"/></div>
        </div>
        </body>
        </html>
        <script type="text/javascript">
        (function(){
        var footer = '<script src="'+ EK.STATIC_ROOT+'js/lib/require.js" data-main="'+EK.STATIC_ROOT+'js/weixin/integrationMall"><\/script>';
        document.write(footer);
        })();

        </script>