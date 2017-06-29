<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
        <% String basePath = request.getContextPath();%>
    <!DOCTYPE html>
    <html>
    <head>
    <%@ include  file="../version.jsp" %>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
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
    +'<link type="text/css" rel="stylesheet" href="'+ EK.STATIC_ROOT+'css/pages/weixin/memberSign.css">'
    document.write(css);
    })();
    </script>

    <title>微信首页</title>
    </head>
    <body ng-controller="mycontrolller">
    <alert></alert>
    <comfirm></comfirm>
    <toast></toast>
    <div ng-include="'/static/temp/weixin/memberSign.html'"></div>
    </body>
    </html>
    <script type="text/javascript">
    (function(){
    var footer = '<script src="'+ EK.STATIC_ROOT+'js/lib/require.js" data-main="'+ EK.STATIC_ROOT+'js/weixin/memberSign"><\/script>';
    document.write(footer);
    })();

    </script>