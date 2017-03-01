<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String basePath = request.getContextPath();%>
<!DOCTYPE html>
<html ng-app="homepage">
<head>
	<link href="<%=basePath %>/static/css/libs/base.css" rel="stylesheet" type="text/css"/>
	<link href="<%=basePath %>/static/css/libs/global.css" rel="stylesheet" type="text/css"/>
	<link type="text/css" href="<%=basePath %>/static/metaumeditor/lib/umeditor/dist/utf8-php/themes/default/css/umeditor.css"
          rel="stylesheet">
	<link href="<%=basePath %>/static/css/pages/admin/global.css" rel="stylesheet" type="text/css"/>
	<link href="<%=basePath %>/static/css/pages/admin/homepage.css" rel="stylesheet" type="text/css"/>
	<link href="<%=basePath %>/static/css/dirctive/leftmenu.css" rel="stylesheet" type="text/css"/>
	  <script type="text/javascript" src="<%=basePath %>/static/js/lib/angular.js"></script>
	<script src="<%=basePath %>/static/metaumeditor/lib/umeditor/dist/utf8-php/third-party/jquery.min.js"></script>
	<script src="<%=basePath %>/static/metaumeditor/lib/umeditor/dist/utf8-php/umeditor.js"></script>
	<script src="<%=basePath %>/static/metaumeditor/lib/umeditor/dist/utf8-php/umeditor.config.js"></script>
	<script src="<%=basePath %>/static/metaumeditor/src/meta.umeditor.js"></script>
   <script type="text/javascript" src="<%=basePath %>/static/js/lib/angular-route.js"></script>
   <script type="text/javascript" src="<%=basePath %>/static/js/lib/angular-sanitize.js"></script>
   <script type="text/javascript" src="<%=basePath %>/static/js/lib/requestParamUtill.js"></script>
   <script type="text/javascript" src="<%=basePath %>/static/js/dirctive/tipsDirctive.js"></script>
   <script type="text/javascript" src="<%=basePath %>/static/js/dirctive/menuDirective.js"></script>    
   <script type="text/javascript" src="<%=basePath %>/static/js/admin/homepage.js"></script>
   <script type="text/javascript" src="<%=basePath %>/static/js/controller/admin/homepageController.js"></script>
   
   
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="renderer" content="webkit">
    <meta name="applicable-device" content="pc">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <meta http-equiv="Cache-Control" content="no-transform">
    <meta name="application-name" content="">
    <meta name="description" content="E-card后台管理">

<title>E-card后台管理</title>
</head>
<body  ng-controller="homepageCtrl" >
    
    <alert></alert>
	<comfirm></comfirm>
	<toast></toast>
	
	
	<div ng-include="'/static/temp/admin/header.html'"></div>

	<div class="-lpy-main-content " >
		<div class="left-menu fl" >
			<menu></menu>
		</div>
		
		<div class="right-content fl" >
		    <div ng-view></div>
		</div>
		<div class="clearfix"></div>
    </div>
    
    
    <div ng-include="'/static/temp/admin/footer.html'"></div>
</body>
</html>