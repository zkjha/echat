/**
 * Created by liupengyan on 17/3/9.
 */

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
        },
        'lib/laydate':{
            exports: 'lib/laydate'
        }
    }
});
requirejs(
    [ 'lib/angular',
        'controller/admin/cashierController',
        'lib/remoteUrl',
        'dirctive/layDateDirective',
        'dirctive/tipsDirctive',
        'lib/requestParamUtill',
        'dirctive/menuDirective',
        'dirctive/fileUploadDirective',
        'service/fileReaderService',
        'lib/angular-route',
        'lib/angular-resize',
        'filter/replaceExpandTypeFilter',
        'filter/replaceEmployeeFilter',
        'filter/payTypeFilter',
        'filter/orderStatusFilter',
        'dirctive/ng-pagination',
        'dirctive/ExpandListDirective',
        'lib/jquery',
        'metaumeditor/lib/umeditor/dist/utf8-php/umeditor',
        'metaumeditor/lib/umeditor/dist/utf8-php/umeditor.config',
        'metaumeditor/src/meta.umeditor'
    ],
    function(angular,memberCentController,remoteUrl) {

        var app = angular.module("menbercent", ['ngRoute','tips','httphelper','menu','fileuploadModel','fileReaderModel','ng-pagination','meta.umeditor','rt.resize','ExpandingType','EmployeeFilter','expandList','defLaydate','payTypeFilter','orderStatusFilter']);

        //路由配置
        app.config(["$routeProvider", function($routeProvider) {
            $routeProvider.when("/goodsType", {
                templateUrl: "/static/temp/admin/cashier/goodsType.html",
                controller:memberCentController.goodsTypeController

            }).when("/goodsAdministration", {
                templateUrl: "/static/temp/admin/cashier/goodsAdministration.html",
                controller:memberCentController.goodsAdministrationController
                
            }).when("/goodsMeasurement", {
                templateUrl: "/static/temp/admin/cashier/goodsMeasurement.html",
                controller:memberCentController.goodsMeasurement
                
            }).otherwise({
                redirectTo: "/goodsType"
            });

        }]);


        app.controller('cashierCtrl',['$scope','$http','$window','resize',function($scope,$http,$window,resize){
        	
        	
            $scope.config = {};
            //初始化菜单数据
            $scope.menuData=[
	                 {id:'',link: '', name: "商品",hasnext:true,
	                 	next:[{id:'goodsType',link: '#!/goodsType', name: "商品分类",hasnext:false}
	                	      ,{id:'goodsAdministration',link: '#!/goodsAdministration', name: "商品管理",hasnext:false}
	                	      ,{id:'111',link: '#!/111', name: "库存管理",hasnext:false}
	                	      ,{id:'goodsMeasurement',link: '#!/goodsMeasurement', name: "计量单位",hasnext:false}
	                 	]
	                 },
	                 {id:'1',link: '###', name: "服务",hasnext:true,
	                	 next:[ {id:'staffinfo',link: '#!/staffinfo', name: "服务分类",hasnext:false}
	                	      ,{id:'duty',link: '#!/duty', name: "服务管理",hasnext:false}
	                	      ,{id:'duty',link: '#!/duty', name: "其他设置",hasnext:false}
	                	      ]
	                 },
	                 {id:'1',link: '###', name: "收银台",hasnext:false}
	               ];


            $scope.lpyMainCotentStyle={

                "min-height":  $window.innerHeight-210
            };

            resize($scope).call(function () {

                $scope.lpyMainCotentStyle={

                    "min-height":  $window.innerHeight-210
                };
            });
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

        angular.bootstrap(document, ["menbercent"]);
    });
