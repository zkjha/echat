var app = angular.module("homepage", ['ngRoute']);
requestParamUtill(app);
tipsDictive(app);

//路由配置
app.config(["$routeProvider", function($routeProvider) {
	$routeProvider.when("/", {
		templateUrl: "/static/temp/admin/index.html"
	}).when("/footer", {
		templateUrl: "/static/temp/admin/footer.html"
	}).when("/header", {
		templateUrl: "/static/temp/admin/header.html"
	}).otherwise({
		redirectTo: "/"
	});
}]);


app.controller('homepageCtrl',['$scope','$http',function($scope,$http){
	//初始化用户信息
	 $http.post("getLoginUserInfo").then(
			 function(result){
				var data =result.data;
				
				var code =data.code;
				if(code==1){
					
				  //得到登录路径
				//  window.location.href=  "/admin/login?url="+window.location.pathname+window.location.search;
				alert(window.location.pathname+window.location.search)	    
					
				}else if(code ==-1){
					
					window.location.href="/admin/login";
					//未登录
				}else if(code<=-2&&code>=-7){
					//必填字段未填写
					$scope.showAlert(data.msg);
				}else if(code ==-8){
					//暂无数据
				}
				
	         },
	         function(result){
				 var status=result.status;
	        	 if(status==-1){
	        		 $scope.showAlert("服务器错误")
	        	 }else if(status>=404&&status<500){
	        		 $scope.showAlert("请求路径错误")
				 }else if(status>=500){
					 $scope.showAlert("服务器错误")
				 }
	         }
	 
	 );
}]);