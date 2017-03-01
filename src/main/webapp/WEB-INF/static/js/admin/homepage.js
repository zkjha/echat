var app = angular.module("homepage", ['ngRoute','ngSanitize','meta.umeditor']);
requestParamUtill(app);
tipsDictive(app);
menueDictive(app);
//路由配置
app.config(["$routeProvider", function($routeProvider) {
	$routeProvider.when("/index", {
		templateUrl: "/static/temp/admin/homepage/index.html"
		
	}).when("/merchantinfo", {
		templateUrl: "/static/temp/admin/homepage/merchantinfo.html",
		//controller:merchantinfoController
		
	}).when("/merchantinfo/editMerchantinfo", {
		templateUrl: "/static/temp/admin/homepage/editMerchantinfo.html",
		//controller:editMerchantinfoController
		
	}).when("/frontinfo", {
		templateUrl: "/static/temp/admin/homepage/frontinfo.html",
		controller:frontinfoController
	}).when("/staffinfo/editStaffinfo", {
		templateUrl: "/static/temp/admin/homepage/editstaffinfo.html",
		controller:editFrontinfoController
		
	}).otherwise({
		redirectTo: "/index"
	});
	
}]);


app.controller('homepageCtrl',['$scope','$http',function($scope,$http){
	$scope.config = {};
	$scope.menuData=[
	                 {id:1,link: '#!/index', name: "首页",hasnext:false,next:[]},
	                 {id:4,link: '#!/merchantinfo', name: "商家资料",hasnext:false,next:[]},
	                 {id:6,link: '#!/staffinfo', name: "员工管理",hasnext:false},
	                 {id:7,link: '#!/frontinfo', name: "前端资料维护",hasnext:false}
	               ]
	
	//初始化用户信息
	 $http.post("getLoginUserInfo").then(
			 function(result){
				 
				var rs =result.data;
				var code =rs.code;
				var data=rs.data;
				if(code==1){
				    $scope.strRealname=data.strRealname;
					var strMerchantname= data.strMerchantname;
					if(strMerchantname==""){
						strMerchantname="4s名称"
					}
					$scope.strMerchantname=strMerchantname;
					
					if( !data.strMerchantlogo){
						$scope.had4sImage=false;
					}else{
						
						$scope.had4sImage=true;
						$scope.strMerchantlogo=data.strMerchantlogo
						$scope.strImgrootpath=data.strImgrootpath;
					}
					
				}else if(code ==-1){
					//得到登录路径
					window.location.href="/admin/login?url="+window.location.pathname+window.location.search+window.location.hash;
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