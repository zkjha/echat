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
        'controller/admin/activityController',
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
        'filter/numberFilter',//字符串转化为数字
        'dirctive/ng-pagination',
        'dirctive/ExpandListDirective',
        'dirctive/admin/activityInterDirective',//积分规则中的选中判断
        'lib/jquery',
        'metaumeditor/lib/umeditor/dist/utf8-php/umeditor',
        'metaumeditor/lib/umeditor/dist/utf8-php/umeditor.config',
        'metaumeditor/src/meta.umeditor'
    ],
    function(angular,activityController,remoteUrl) {

        var app = angular.module("menbercent", ['ngRoute','tips','httphelper','menu','fileuploadModel','fileReaderModel','ng-pagination','meta.umeditor','rt.resize','ExpandingType','EmployeeFilter','expandList','defLaydate','payTypeFilter','orderStatusFilter','numberFilter','integralrulesDirective']);

        //路由配置
        app.config(["$routeProvider", function($routeProvider) {
            $routeProvider.when("/advertisementCycle", {
                templateUrl: "/static/temp/admin/activity/advertisementCycle.html",
	            controller:activityController.uploadImages

            }).when("/customgift", {//自定义赠送
                templateUrl: "/static/temp/admin/activity/customgift.html",
	            controller:activityController.uploadImages

            }).when("/consumptiongift", {//消费赠送
                templateUrl: "/static/temp/admin/activity/consumptiongift.html",
                controller:activityController.uploadImages

            }).when("/recharge", {//充值赠送
                templateUrl: "/static/temp/admin/activity/recharge.html",
                controller:activityController.uploadImages

            }).when("/firsttime", {//首次入会
                templateUrl: "/static/temp/admin/activity/firsttime.html",
                controller:activityController.uploadImages

            }).when("/integralrules", {//首次入会
                templateUrl: "/static/temp/admin/activity/integralrules.html",
                controller:activityController.integralrules

            }).when("/dyqxz", {//抵用券新增
                templateUrl: "/static/temp/admin/activity/dyqxz.html",
                controller:activityController.dyqxz
            })
                .otherwise({
                redirectTo: "/customgift"
            });
        //integralrules
        }]);


        app.controller('activityCtrl',['$scope','$http','$window','resize',function($scope,$http,$window,resize){
        	
        	
            $scope.config = {};
            //初始化菜单数据
            $scope.menuData=[
	                 {id:'customgift',link: '#!/customgift', name: "自定义赠送",hasnext:false}
	                 ,{id:'consumptiongift',link: '#!/consumptiongift', name: "消费赠送",hasnext:false}
	                 ,{id:'recharge',link: '#!/recharge', name: "充值赠送",hasnext:false}
	                 ,{id:'firsttime',link: '#!/firsttime', name: "首次入会",hasnext:false}
	                 ,{id:'integralrules',link: '#!/integralrules', name: "积分规则",hasnext:false}
	                 ,{id:'advertisementCycle',link: '#!/advertisementCycle', name: "前端首页广告",hasnext:false}
	                 ,{id:'advertisementCycle',link: '#!/advertisementCycle', name: "活动规则设置",hasnext:false}
                     ,{id:'dyqxz',link: '#!/dyqxz', name: "抵用券新增",hasnext:false}
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
