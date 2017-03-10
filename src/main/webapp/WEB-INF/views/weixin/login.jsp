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
   			+'<link type="text/css" rel="stylesheet" href="'+ EK.STATIC_ROOT+'css/pages/weixin/login.css">'
    document.write(css);
    })();
    </script>
<title>微信登录</title>
</head>
<body>
	<div class="mobile_content" ng-controller="myController">
		<alert></alert>
		<comfirm></comfirm>
		<toast></toast>
		<div class="z-login-top">
			<div class="z-login-top-center">
				<p class="z-login-commonText">手机号：</p>
				<p class="z-login-input"><input type="" name="" id="" value="strMobile" ng-model="strMobile"/> <button class="z-login-click" ng-click="send()"><resend-code></resend-code></button></p>
				<p class="z-login-commonText">验证码：</p>
				<p class="z-login-input"><input type="" name="" id="" value="strAuthcode" ng-model="strAuthcode"/></p>
				<p class="z-login-last-true"><span class="z-login-click-ture" ng-click="login()">认证 </span></p>
			</div>
		</div>
		<div class="z-login-body">
			<ul>
				<li class="z-login-commonText">进行车辆认证后，你将获得更多服务:</li>
				<li class="z-login-orderBy">1、无需。。。。</li>
			</ul>
			<p class="z-login-commonText z-login-warm">注意：<br />若你输入的手机号码与预留号码不一致，则不能认证成功。</p>
		</div>
   </div>

</body>
</html>
<script type="text/javascript">
    (function(){
    var footer = '<script src="'+ EK.STATIC_ROOT+'js/lib/require.js" data-main="'+ EK.STATIC_ROOT+'js/weixin/login"><\/script>';
    document.write(footer);
    })();

</script>