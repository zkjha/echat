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
        }
    }
});
requirejs(
    [ 'lib/angular',
        'controller/admin/menbercentController',
        'lib/remoteUrl',
        'dirctive/tipsDirctive',
        'lib/requestParamUtill',
        'dirctive/menuDirective',
        'dirctive/fileUploadDirective',
        'service/fileReaderService',
        'lib/angular-route',
        'dirctive/ng-pagination',
        'lib/jquery',
        'metaumeditor/lib/umeditor/dist/utf8-php/umeditor',
        'metaumeditor/lib/umeditor/dist/utf8-php/umeditor.config',
        'metaumeditor/src/meta.umeditor'
    ],
    function(angular,menbercentController,remoteUrl) {

        var app = angular.module("menbercent", ['ngRoute','tips','httphelper','menu','fileuploadModel','fileReaderModel','ng-pagination','meta.umeditor']);

//路由配置
        app.config(["$routeProvider", function($routeProvider) {
            $routeProvider.when("/memberSet", {
                templateUrl: "/static/temp/admin/membercenter/memberSet.html"

            }).when("/levelSet", {//会员级别及权益设置
                templateUrl: "/static/temp/admin/membercenter/levelSet.html"

            }).when("/regulations", {//会员章程展示
                templateUrl: "/static/temp/admin/membercenter/regulations.html",
                controller:menbercentController.regulationsController

            }).when("/editRegulations", {//会员章程修改
                templateUrl: "/static/temp/admin/membercenter/editRegulations.html"

            }).when("/expandinfo", {//会员拓展资料
                templateUrl: "/static/temp/admin/membercenter/expandinfo.html"


            }).otherwise({
                redirectTo: "/memberSet"
            });

        }]);


        app.controller('menbercentCtrl',['$scope','$http',function($scope,$http){
            $scope.config = {};
            //初始化菜单数据
            $scope.menuData=[
                {id:'memberSet',link: '#!/memberSet', name: "会员管理",hasnext:false,next:[]},
                {id:'levelSet',link: '#!/levelSet', name: "会员级别及权益设置",hasnext:false,next:[]},
                {id:'regulations',link: '#!/regulations', name: "会员章程设置",hasnext:false, next:[]},
                {id:'expandinfo',link: '#!/expandinfo', name: "会员拓展资料",hasnext:false,next:[ ]}
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

        angular.bootstrap(document, ["menbercent"]);
    });
