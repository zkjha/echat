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
        'lib/angular-cookie':{
            deps:['lib/angular']
        },
        'lib/laydate':{
            exports: 'lib/laydate'
        }
    }
});
requirejs(
    [ 'lib/angular',
        'controller/admin/activityDeskController',
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
        'dirctive/admin/activityInterDirective',//朱-指令
        'lib/jquery',
        'metaumeditor/lib/umeditor/dist/utf8-php/umeditor',
        'metaumeditor/lib/umeditor/dist/utf8-php/umeditor.config',
        'metaumeditor/src/meta.umeditor',
        'lib/angular-cookie'
    ],
    function(angular,activityDeskController,remoteUrl) {
        var app = angular.module("menbercent", ['ngRoute','tips','httphelper','menu','fileuploadModel','fileReaderModel','ng-pagination','meta.umeditor','rt.resize','ExpandingType','EmployeeFilter','expandList','defLaydate','payTypeFilter','orderStatusFilter','integralrulesDirective']);

        //路由配置
        app.config(["$routeProvider", function($routeProvider) {
            $routeProvider.when("/userbottom", {
                templateUrl: "/static/temp/admin/cashierDesk/userbottom.html",
                controller:activityDeskController.userbottom

            }).when("/goShopStore", {
                templateUrl: "/static/temp/admin/cashierDesk/goShopStore.html",
                controller:activityDeskController.goShopStore

            }).otherwise({
                redirectTo: "/userbottom"
            });
            
        //goShopStore
        }]);


        app.controller('cashierCtrl',['$scope','$http','$window','resize',function($scope,$http,$window,resize){

        	////////////////////////////刘哥写的代码///////////////////////
            $scope.config = {};
            //初始化菜单数据
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
        ///////////////////////////朱写的代码/////////////////////////////


        app.controller('searchTellphone',['$scope','$http',function($scope,$http){
            $scope.searchChargeconsol = null;//输入框的值
            $scope.ifshowuserdetail = false;//保证用户详细信息是否显示


            $scope.selectMemberInfoFromSearch =  function($scope,$http,searchText){
                var data = {
                    //searchText:searchText
                    searchText:searchText
                }
                $http.post(remoteUrl.selectMemberInfoFromSearch,data).then(
                    function(result){
                        var rs = result.data;
                        var data = result.data;
                        var code = data.code;
                        console.info(result);
                        if (code == 1) {
                            $scope.ifshowuserdetail = true;
                            $scope.listMemberVO = data.data.listMemberVO[0]
                        } else if (code == -1) {
                            window.location.href = "/admin/login?url="
                            + window.location.pathname
                            + window.location.search
                            + window.location.hash;
                            //未登录
                        } else if (code <= -2 && code >= -7) {
                            //必填字段未填写
                            $scope.showAlert(rs.msg);
                        } else if (code == -8) {
                            //暂无数据
                            $scope.showAlert(rs.msg)
                            $scope.isNoData=true;
                            $scope.pageCount = 0;
                        }

                    }, function (result) {


                        var status = result.status;
                        if (status == -1) {
                            $scope.showAlert("服务器错误")
                        } else if (status >= 404 && status < 500) {
                            $scope.showAlert("请求路径错误")
                        } else if (status >= 500) {
                            $scope.showAlert("服务器错误")
                        }
                    }
                )
            };
            $scope.vipUserSearch = function(){//点击放大镜
                console.info($scope.searchChargeconsol);
                var searchText =$scope.searchChargeconsol;//输入的内容
                $scope.selectMemberInfoFromSearch($scope,$http,searchText)//输入手机号等调用的查询接口
            }

        //    跳转到会员中心页面
            $scope.runrecharge = function(){
                window.location.pathname = '/admin/page/membercenter';
                console.info()
            }
        }])

        angular.bootstrap(document, ["menbercent"]);
    });
