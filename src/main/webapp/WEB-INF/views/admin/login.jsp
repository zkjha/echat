<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String basePath = request.getContextPath();%>
<!DOCTYPE html>
<html ng-app="login">
<head>
	<link href="<%=basePath %>/static/css/libs/base.css" rel="stylesheet" type="text/css"/>
	<link href="<%=basePath %>/static/css/libs/global.css" rel="stylesheet" type="text/css"/>
	<link href="<%=basePath %>/static/css/pages/admin/login.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="<%=basePath %>/static/js/lib/angular.js"></script>
    <script type="text/javascript" src="<%=basePath %>/static/js/lib/requestParamUtill.js"></script>
    <script type="text/javascript" src="<%=basePath %>/static/js/dirctive/tipsDirctive.js"></script>  
    <script type="text/javascript" src="<%=basePath %>/static/js/admin/login.js"></script>
   
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="renderer" content="webkit">
    <meta name="applicable-device" content="pc">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <meta http-equiv="Cache-Control" content="no-transform">
    <meta name="application-name" content="">
    <meta name="description" content="登录">

<title>登陆</title>
</head>
<body  ng-controller="loginCtrl"  ng-init="useridIsError=false;passwordIsError=false"  ng-keydown="keydown($event)">
	<alert></alert>
	<comfirm></comfirm>
	<toast></toast>
	
	<input type="hidden" ng-model="url"  ng-init="url='${url}'" value=""/>
    <div class="-lpy-login-form">
			<h3>E-card 一键开启轻松管理模式</h3>
			<div class="-lpy-input-container">
				<span>工  号：</span><input type="text" ng-model="user.strLoginname" placeholder="请输入工号"/>
				<div class="error-container" ng-show="useridIsError" >
					<span class="error-ico fl"></span> <span class="error-info fl">{{useridErrorInfo}}</span>
				</div>
			</div>
			<div class="-lpy-input-container">
				<span>密  码：</span><input type="password" ng-model="user.strPassword" placeholder="请输入密码"/>
				<div class="error-container" ng-show="passwordIsError"> 
					<span class="error-ico fl"></span> <span class="error-info fl">{{passwordErrorInfo}}</span>
				</div>
			</div>
			
			<div ng-class="{true: 'btn-primary ', false: 'btn-busy'}[isActive]" class="btn -lpy-login-btn"  ng-click="loginAction()" >登陆</div>
			
    </div>
    <div class="footer">
    	<div class="footer-first-line">
    		<div class="finst-line-center">
    			<div class="fl mr20 mt10"><span class="ico-phone"></span><span>热线：15829843512</span></div>
    			<div  class="fl mr20 mt10"><span class="ico-addr"></span><span>地址：贵州省贵阳市南明区龙洞堡机场太升国际大厦A2栋9层7号</span></div>
    			<div  class="fl mt10"><span class="ico-email"></span><span class="ml5">邮箱：1582984@163.com</span></div>
    			<div class="clearfix"></div>
    		</div>
    	</div>
    	<div class="footer-seconde-line">
    	  Copyright&copy; 2017 数联铭品 川icp备23233号-6
    	</div>
    </div>
    
</body>
</html>