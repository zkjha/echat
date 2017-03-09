require.config({
	baseUrl : EK.STATIC_ROOT + "js" ,
	shim:{
        'lib/angular':{
            exports:'angular'
        },
        'lib/angular-route':{
        	deps:['lib/angular'],
        	exports: 'angular-route'
        }
      },
});


requirejs([
	'lib/angular', 
	'lib/remoteUrl',
	'controller/wechat/wechatController',
	'dirctive/tipsDirctive',
	'lib/requestParamUtill',
	'dirctive/menuDirective',
	'lib/angular-route'
],function(angular,remoteUrl,WeChatController){
	
	var app = angular.module('wechatApp',['ngRoute','menu','tips','httphelper']);
	app.config(['$routeProvider',function($routeProvider){
		$routeProvider.when('/wechatRight',{
			templateUrl: "/static/temp/admin/wechat/wechatRight.html",//跳转到公众号管理界面
			controller:WeChatController.wechatCtr
			
		}).otherwise({
		redirectTo: '/wechatRight'
	  });
	}])
	

app.controller('wechatCtr',['$scope','$http',function($scope,$http){
	$scope.config = {};
	//初始化菜单数据
	$scope.menuData=[
	                 {id:'wechatRight',link: '#!/wechatRight', name: "公众号管理",hasnext:false,next:[]}
	               ];
	
	//初始化用户信息
	 $http.post(remoteUrl.getLoginUserInfo).then(
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
	angular.bootstrap(document,['wechatApp'])
});
