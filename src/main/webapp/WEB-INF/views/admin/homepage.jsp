<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String basePath = request.getContextPath();%>
<!DOCTYPE html>
<html >
<script>
    function logoImgError(_this)
    {
     _this.src="/static/images/logo.png";
    };
    function userHeadImgError(_this){
    _this.src="/static/images/defaulHeader.png";
    };
</script>
<head>
    <%@ include  file="../version.jsp" %>
   <script type="text/javascript">
 
    (function(){
    
    var css ='<link type="text/css" rel="stylesheet" href="'+ EK.STATIC_ROOT+'css/libs/base.css">'
    +'<link type="text/css" rel="stylesheet" href="'+ EK.STATIC_ROOT+'css/libs/global.css">'
    +'<link type="text/css" rel="stylesheet" href="'+ EK.STATIC_ROOT+'css/pages/admin/global.css">'
    +'<link type="text/css" rel="stylesheet" href="'+ EK.STATIC_ROOT+'css/dirctive/leftmenu.css">'
    +'<link type="text/css" rel="stylesheet" href="'+ EK.STATIC_ROOT+'css/dirctive/ng-pagination.css">'
    +'<link type="text/css" rel="stylesheet" href="'+ EK.STATIC_ROOT+'js/metaumeditor/lib/umeditor/dist/utf8-php/themes/default/css/umeditor.css">'
    +'<link type="text/css" rel="stylesheet" href="'+ EK.STATIC_ROOT+'css/pages/admin/homepage.css">'
    document.write(css);
    })();
    </script>
   
 

  <%--  <script type="text/javascript" src="<%=basePath %>/static/js/lib/angular-route.js"></script>--%>

   
   
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

<script type="text/javascript">
    (function(){
    var footer = '<script src="'+ EK.STATIC_ROOT+'js/lib/require.js" data-main="'+ EK.STATIC_ROOT+'js/admin/homepage"><\/script>';
    document.write(footer);
    })();

</script>