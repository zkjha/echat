/**
 * Created by zhujunliang on 17/5/1
 */
define(
    ['lib/remoteUrl'],
	function(remoteUrl) {
		'use strict'

		var activityCtrl = {


        ////////////////////////////添加购物车/////////////////////
            goShopStore:function($scope,$http){
                //$scope.shopStoreShow = localStorage.getItem("store");
                var localvalue =JSON.parse(localStorage.getItem("store")) ;
                var sjopStore = [];
                var serivcrStore = [];
                for(var i =0 ;i <localvalue.length;i++){
                    if(localvalue[i].strGoodsId){
                        sjopStore.push(localvalue[i]);
                    }else{
                        serivcrStore.push(localvalue[i]);
                    }
                }
                $scope.shopStoress = sjopStore;
                $scope.serivcrStoress = serivcrStore;


                $scope.seaveNews = function(){
                    console.info(123)
                    console.info($scope.shopStoress);
                    console.info($scope.serivcrStoress);

                }
                //商品部分删除
                $scope.shopDelete= function(id){
                    $scope.shopStoress.splice(id,1);
                }
                //服务部分删除
                $scope.serviceDelete= function(id){
                    $scope.serivcrStoress.splice(id,1);
                }
            },

            /////////////////////添加购物车结束////////////////////

            ///////////////////消费赠送-开始////////////////////////
            userbottom:function($scope,$http){
                $scope.pagenum= 1;
                $scope.pagesize = 1000;
                activityCtrl.getListServiceType($scope,$http)//输入手机号等调用的查询接口
                activityCtrl.selGoodsTypeList($scope,$http)//输入手机号等调用的查询接口
                $scope.ifslowmoney = [{id:1,name:'是'},{id:0,name:'否'}]//是否优惠
                $scope.ifslowmoneySure = 0;//默认不优惠
                $scope.ifBuyShopSure = 0;//默认不选择购买
                $scope.Selectifpanduan = true;//判断是否选择
                console.info(localStorage.store)


                //分页接口
                $scope.ipageNum = 1;
                $scope.ipageSize = 5;
                $scope.shopSelectsIdSure = function(){//点击商品选择
                    $scope.Selectifpanduan = false;
                    $scope.serviceSelectsId = null;
                    $scope.listServiceInfoEntity = {}
                    activityCtrl.selectAllGoodsInfoEntity($scope,$http,$scope.shopSelectsId)//输入手机号等调用的查询接口
                };
                $scope.serviceSelectsIdSure = function(){//点击服务选择
                    $scope.Selectifpanduan = false;
                    $scope.shopSelectsId = null;
                    $scope.listGoodsInfoEntity = {};
                    activityCtrl.selectAllServiceInfoEntity($scope,$http,$scope.serviceSelectsId)//输入手机号等调用的查询接口
                };

                //$scope.saveCookie = function(){
                //    localStorage.setItem("key",$scope.pagesize);
                //}
                //localStorage.store = [];
                var goStore = [];
                $scope.ifBuyselectShop = function(somthingBuy,id){
                    if(localStorage.store){
                        goStore = JSON.parse(localStorage.store);
                        if(id){
                            console.info(somthingBuy)
                            var flag= false;
                            for(var i =0 ;i <goStore.length;i++){
                                console.info(goStore[i])
                                if(somthingBuy.strGoodsId ==goStore[i].strGoodsId){
                                    goStore.length = 0;
                                    console.info("登录拦截成功")
                                    flag= true;
                                }
                            };
                            if(flag){
                                  return
                            }else{
                                goStore.push(somthingBuy);
                                localStorage.store = JSON.stringify(goStore);
                                console.info("我是来判断拦截成功没有")
                            }
                        }

                    }else{
                        goStore.push(somthingBuy)
                        console.info(goStore)
                        localStorage.store = JSON.stringify(goStore);
                    }
                };
                $scope.ifBuyselectService = function(somthingBuy,id){
                    if(localStorage.store){
                        goStore = JSON.parse(localStorage.store);
                        if(id){
                            console.info(somthingBuy)
                            var flag= false;
                            for(var i =0 ;i <goStore.length;i++){
                                console.info(goStore[i])
                                if(somthingBuy.strServiceInfoId ==goStore[i].strServiceInfoId){
                                    goStore.length = 0;
                                    console.info("登录拦截成功")
                                    flag= true;
                                }
                            };
                            if(flag){
                                return
                            }else{
                                goStore.push(somthingBuy);
                                localStorage.store = JSON.stringify(goStore);
                                console.info("我是来判断拦截成功没有")
                            }
                        }

                    }else{
                        goStore.push(somthingBuy)
                        console.info(goStore)
                        localStorage.store = JSON.stringify(goStore);
                    }
                }
                //清除本地存储
                $scope.clearStore = function(){
                    localStorage.removeItem('store');
                }
                //操作cookie
                $scope.saveCookie = function() {
                    window.location.hash = "#!/goShopStore";
                };
                //$scope.removeCookie = function() {
                //    $cookieStore.remove("a1");
                //    alert("删除cookie成功！");
                //};

                //$scope.vipUserSearch = function(){//点击放大镜
                //    console.info(123)
                //    var searchText = $scope.searchChargeconsol;//输入的内容
                //    activityCtrl.selectMemberInfoFromSearch($scope,$http,searchText)//输入手机号等调用的查询接口
                //}



            },
            //收银台 -- 按商品类别查询所有服务详情 -- 查询列表
            selectAllServiceInfoEntity:function($scope,$http,id){
                var data = {
                    //searchText:searchText
                    strServiceTypeId:id,
                    pagenum:$scope.ipageNum,
                    iPreferentialType: $scope.ifslowmoneySure,
                    pagesize:$scope.ipageSize
                }
                $http.post(remoteUrl.selectAllServiceInfoEntity,data).then(
                    function(result){
                        var rs = result.data;
                        var data = result.data;
                        var code = data.code;
                        console.info(result);
                        if (code == 1) {
                            $scope.iTotalPage = data.iTotalPage;
                            $scope.iTotalRecord = data.iTotalRecord;
                            $scope.listServiceInfoEntity = data.data.listServiceInfoEntity;
                            console.info($scope.listServiceInfoEntity)
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
            },
            //收银台 -- 按商品类别查询所有商品详情 -- 查询列表
            selectAllGoodsInfoEntity:function($scope,$http,id){
                var data = {
                    //searchText:searchText
                    strGoodsTypeId:id,
                    pagenum:$scope.ipageNum,
                    iPreferentialType: $scope.ifslowmoneySure,
                    pagesize:$scope.ipageSize
                }
                $http.post(remoteUrl.selectAllGoodsInfoEntity,data).then(
                    function(result){
                        var rs = result.data;
                        var data = result.data;
                        var code = data.code;
                        console.info(result);
                        if (code == 1) {
                            $scope.iTotalPage = data.iTotalPage;
                            $scope.iTotalRecord = data.iTotalRecord;
                            $scope.listGoodsInfoEntity = data.data.listGoodsInfoEntity;
                            console.info($scope.listGoodsInfoEntity)
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
            },
            //服务类型－分页查询
            selGoodsTypeList:function($scope,$http){
                var data = {
                    //searchText:searchText
                    pagenum:$scope.pagenum,
                    pagesize:$scope.pagesize
                }
                $http.post(remoteUrl.selGoodsTypeList,data).then(
                    function(result){
                        var rs = result.data;
                        var data = result.data;
                        var code = data.code;
                        console.info(result);
                        if (code == 1) {
                            $scope.goodsTypeList = data.data.goodsTypeList;
                            console.info($scope.goodsTypeList)
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
            },
            //服务类型－分页查询
            getListServiceType:function($scope,$http){
                var data = {
                    //searchText:searchText
                    pagenum:$scope.pagenum,
                    pagesize:$scope.pagesize
                }
                $http.post(remoteUrl.getListServiceType,data).then(
                    function(result){
                        var rs = result.data;
                        var data = result.data;
                        var code = data.code;
                        console.info(result);
                        if (code == 1) {
                            $scope.listServiceType = data.data.listServiceType;
                            console.info($scope.listServiceType)
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
            }



            ////////////////////消费赠送-结束////////////////////////

		}
		return activityCtrl;

	});