var app = angular.module("homepage", ['ngRoute',]);
requestParamUtill(app);
tipsDictive(app);
menueDictive(app);
//路由配置
app.config(["$routeProvider", function($routeProvider) {
	$routeProvider.when("/player/list", {
		templateUrl: "/static/temp/admin/homepage/index.html"
		
	}).when("/player/edit", {
		templateUrl: "/static/temp/admin/footer.html"
		
	}).otherwise({
		redirectTo: "/player/list"
	});
	
}]);


app.controller('homepageCtrl',['$scope','$http',function($scope,$http){
	$scope.menuData=[
	                 {id:1,link: '', name: "aa",hasnext:true,next:[{id:3,link: '#!/player/list', name: "header",hasnext:false}]},
	                 {id:4,link: '', name: "header",hasnext:true,next:[{id:9,link: '#!/player/list4', name: "header",hasnext:false}]},
	                 {id:6,link: '#!/player/list2', name: "header",hasnext:false},
	                 {id:7,link: '', name: "header",hasnext:true,next:[{id:8,link: '#!/player/list5', name: "header",hasnext:false},{id:10,link: '#!/player/edit6', name: "header",hasnext:false}]}
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