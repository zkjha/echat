
require.config({
	baseUrl : EK.STATIC_ROOT + "js" ,
	shim:{
        'lib/angular':{
            exports:'angular'
        },
        'lib/angular-route':{
            deps:['lib/angular'],
            exports: 'angular-route'
        },
        'metaumeditor/lib/umeditor/dist/utf8-php/umeditor':{
        	 deps:['lib/jquery']
        },
        'metaumeditor/lib/umeditor/dist/utf8-php/umeditor.config':{
        	 deps:['metaumeditor/lib/umeditor/dist/utf8-php/umeditor']
        },
        'metaumeditor/src/meta.umeditor':{
        	 deps:['lib/angular','lib/jquery']
        }
    }
});
requirejs(
		[ 'lib/angular', 
		  'controller/admin/homepageController',
		  'lib/remoteUrl',
		  'dirctive/tipsDirctive',
		  'lib/requestParamUtill',
		  'dirctive/menuDirective',
		  'dirctive/fileUploadDirective',
		  'service/fileReaderService',
		  'lib/angular-route',
		  'dirctive/ng-pagination',
		  'lib/jquery',
          'filter/replaceEmployeeFilter',
		  'metaumeditor/lib/umeditor/dist/utf8-php/umeditor',
		  'metaumeditor/lib/umeditor/dist/utf8-php/umeditor.config',
		  'metaumeditor/src/meta.umeditor'
		  ],
		function(angular,homepageController,remoteUrl) {

var app = angular.module("homepage", ['ngRoute','tips','httphelper','menu','fileuploadModel','fileReaderModel','ng-pagination','meta.umeditor','EmployeeFilter']);

//路由配置
app.config(["$routeProvider", function($routeProvider) {
	$routeProvider.when("/index", {
		templateUrl: "/static/temp/admin/homepage/index.html"
		
	}).when("/merchantinfo", {//商家管理
		templateUrl: "/static/temp/admin/homepage/merchantinfo.html",
		controller:homepageController.merchantinfoController
		
	}).when("/frontinfo", {//前端资料展示
		templateUrl: "/static/temp/admin/homepage/frontinfo.html",
		controller:homepageController.frontinfoController
	}).when("/frontinfo/editFrontinfo", {//前端资料编辑
		templateUrl: "/static/temp/admin/homepage/editfrontinfo.html",
		controller:homepageController.editFrontinfoController
		
	}).when("/staffinfo", {//员工管理列表
		templateUrl: "/static/temp/admin/homepage/staffinfo.html",
		controller:homepageController.staffinfoController
		
	}).when("/duty", {//职务管理列表
		templateUrl: "/static/temp/admin/homepage/duty.html",
		controller:homepageController.dutyController
		
	}).otherwise({
		redirectTo: "/index"
	});
	
}]);


app.controller('homepageCtrl',['$scope','$http',function($scope,$http){
	$scope.config = {};
	//初始化菜单数据
	$scope.menuData=[
	                 {id:'index',link: '#!/index', name: "首页",hasnext:false,next:[]},
	                 {id:'merchantinfo',link: '#!/merchantinfo', name: "商家资料",hasnext:false,next:[]},
	                 {id:'1',link: '###', name: "员工管理",hasnext:true,
	                	 next:[ {id:'staffinfo',link: '#!/staffinfo', name: "员工管理",hasnext:false}
	                	      ,{id:'duty',link: '#!/duty', name: "职务管理",hasnext:false}
	                	      ]
	                 },
	                 {id:'frontinfo',link: '#!/frontinfo', name: "前端资料维护",hasnext:false}
	               ]
	
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

angular.bootstrap(document, ["homepage"]);  
});
