/**
 * Created by liupengyan on 17/3/9.
 */
/**
 * Created by liupengyan on 17/3/9.
 * 处理会员中心控制器
 */
define(
    ['lib/remoteUrl'], function (remoteUrl) {
        'use strict'

        var MemberCenter = {
        	 ////////////////////商品分类开始/////////////////////////

            goodsTypeController:function($scope, $http){
				
                $scope.currentPage = 1;
                $scope.pageSize = 5;
                $scope.isShowListMenu = [];
                $scope.goodsTypeList={};
                
                //保存
                $scope.submitExpandinfo=function(){
					if($scope.typePanduan){
						//调用修改接口
						 MemberCenter.updateGoodsType($scope.goodsTypeList,$scope, $http);
					}
					else{
						 //调用保存接口
                   		MemberCenter.insertGoodsType($scope.goodsTypeList,$scope, $http);
					}
                   

                };


                MemberCenter.selGoodsTypeList($scope, $http);
                $scope.onPageChange = function () {
                    // ajax request to load data
                    $scope.goodsTypeList = {};
                    MemberCenter.selGoodsTypeList($scope, $http);

                };

                $scope.openCtrMenu = function ($index, type) {
                    for (var i = 0; i < $scope.goodsTypeList.length; i++) {
                        $scope.isShowListMenu[i] = false;
                    }
                    if (type == 'over') {
                        $scope.isShowListMenu[$index] = !$scope.isShowListMenu[$index];
                    }

                };
                //关闭窗口
                $scope.clostExpandWindow=function(){
                    $scope.showExpandInfoWindow=false;
                    $scope.goodsTypeList={};
                    MemberCenter.selGoodsTypeList($scope, $http);
                };
                //新建商品分类点击按钮事件
                $scope.newExpandinginfo=function(){

                    $scope.showExpandInfoWindow=true;
                    $scope.isAddNewExpand=true;
                    //判断执行添加还是修改
                    $scope.typePanduan = false;
                };
                //修改商品分类点击按钮事件
                $scope.updataExpand=function(strGoodsTypeId){

                    $scope.showExpandInfoWindow=true;
                    $scope.isAddNewExpand=false;
                    //判断执行添加还是修改
                    $scope.typePanduan = true;
                    //调用接口
                    MemberCenter.selGoodsTypeDetail(strGoodsTypeId,$scope, $http);
                };
                //删除商品分类
                $scope.delectExpand=function(strGoodsTypeId,strGoodsTypeName){

                    $scope.showConfirm("确认要删除" +strGoodsTypeName+"？",function(rs){
                        //调用删除拓展资料函数
                        if(rs){
                        	console.info(rs)
                            MemberCenter.delGoodsType(strGoodsTypeId,$scope, $http);
                        }

                    })
                }

            },
            //查询商品分类详情
            selGoodsTypeDetail:function(strGoodsTypeId,$scope, $http) {

                var data={"strGoodsTypeId":strGoodsTypeId}
                $http.post(remoteUrl.selGoodsTypeDetail, data).then(
                    function (result) {

                        var rs = result.data;
                        var code = rs.code;
                        var data = rs.data;
                        var goodsTypes = data.goodsType;
						console.info(data)
                        if (code == 1) {
							angular.forEach(goodsTypes,function(val,key){
								$scope.goodsTypeList[key] = val;
							})
							
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
                    });
            }
             ,
             //修改商品分类
            updateGoodsType:function(data,$scope, $http){
                $http.post(remoteUrl.updateGoodsType, data).then(
                    function (result) {

                        var rs = result.data;
                        var code = rs.code;
                        var data = rs.data;

                        if (code == 1) {

                            $scope.showAlert("保存成功",function(){
                                window.location.reload();
                            });

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
                 });
            }
            ,
            //新增商品分类
            insertGoodsType:function(data,$scope, $http){
                $http.post(remoteUrl.insertGoodsType, data).then(
                    function (result) {

                        var rs = result.data;
                        var code = rs.code;
                        var data = rs.data;

                        if (code == 1) {

                            $scope.showAlert("保存成功",function(){
                                window.location.reload();
                            });

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
                 });
            }
            ,
            //删除商品分类请求
            delGoodsType:function(strGoodsTypeId,$scope, $http){

                var data = {
                    'strGoodsTypeId': strGoodsTypeId
                };
                $http.post(remoteUrl.delGoodsType,data).then(
                    function (result) {
						console.info(strGoodsTypeId);
                        var rs = result.data;
                        var code = rs.code;
                        var data = rs.data;
						console.info(result)
                        if (code == 1) {
							
                            window.location.reload();

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
							$scope.showAlert(rs.msg);	
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
                    });
            }
            ,
            //获取商品分类列表
            selGoodsTypeList: function ($scope, $http) {

                var data = {
                    'pagenum': $scope.currentPage,
                    "pagesize": $scope.pageSize,
                };

                $http.post(remoteUrl.selGoodsTypeList, data).then(
                    function (result) {

                        var rs = result.data;
                        var code = rs.code;
                        var data = rs.data;
						console.info(data)
                        //code=-8;
                        if (code == 1) {
                            //图片跟路径
                            $scope.pageCount = data.iTotalPage;
                            $scope.goodsTypeList = data.goodsTypeList;
							console.info( $scope.pageCount)
                            for (var i = 0; i < $scope.goodsTypeList.length; i++) {
                                $scope.isShowListMenu[i] = false;
                            }

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
                    });
            },
            ////////////////////商品分类部分结束/////////////////////////
            
        	
        	
        	
        	 ////////////////////计量单位开始/////////////////////////

            goodsMeasurement:function($scope, $http){
				

                $scope.currentPage = 1;
                $scope.pageSize = 5;
                $scope.isShowListMenu = [];
                $scope.unitList={};
                
                //保存
                $scope.submitExpandinfo=function(){
					if($scope.typePanduan){
						//调用修改接口
						 MemberCenter.updateUnit($scope.unitList,$scope, $http);
					}
					else{
						 //调用保存接口
                   		MemberCenter.insertUnit($scope.unitList,$scope, $http);
					}
                   

                };


                MemberCenter.selUnitList($scope, $http);
                $scope.onPageChange = function () {
                    // ajax request to load data
                    $scope.unitList = {};
                    MemberCenter.selUnitList($scope, $http);

                };

                $scope.openCtrMenu = function ($index, type) {
                    for (var i = 0; i < $scope.unitList.length; i++) {
                        $scope.isShowListMenu[i] = false;
                    }
                    if (type == 'over') {
                        $scope.isShowListMenu[$index] = !$scope.isShowListMenu[$index];
                    }

                };
                //关闭窗口
                $scope.clostExpandWindow=function(){
                    $scope.showExpandInfoWindow=false;
                    $scope.unitList={};
                    MemberCenter.selUnitList($scope, $http);
                };
                //新建计量单位点击按钮事件
                $scope.newExpandinginfo=function(){

                    $scope.showExpandInfoWindow=true;
                    $scope.isAddNewExpand=true;
                    //判断执行添加还是修改
                    $scope.typePanduan = false;
                };
                //修改计量单位点击按钮事件
                $scope.updataExpand=function(strUnitId){

                    $scope.showExpandInfoWindow=true;
                    $scope.isAddNewExpand=false;
                    //判断执行添加还是修改
                    $scope.typePanduan = true;
                    //调用接口-查询详情
                    MemberCenter.selUnitDetail(strUnitId,$scope, $http);
                };
                //删除计量单位
                $scope.delectExpand=function(strUnitId,strUnitName){

                    $scope.showConfirm("确认要删除" +strUnitName+"？",function(rs){
                        //调用删除拓展资料函数
                        if(rs){
                        	console.info(rs)
                            MemberCenter.delUnit(strUnitId,$scope, $http);
                        }

                    })
                }

            },
            //查询计量单位详情
            selUnitDetail:function(strUnitId,$scope, $http) {

                var data={"strUnitId":strUnitId}
                $http.post(remoteUrl.selUnitDetail, data).then(
                    function (result) {

                        var rs = result.data;
                        var code = rs.code;
                        var data = rs.data;
                        var MeasurementUnit = data.MeasurementUnit;
						console.info(data)
                        if (code == 1) {
                        	angular.forEach(MeasurementUnit,function(val,key){
								$scope.unitList[key] = val;
							})
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
                    });
            }
             ,
             //修改计量单位
            updateUnit:function(data,$scope, $http){
                $http.post(remoteUrl.updateUnit, data).then(
                    function (result) {

                        var rs = result.data;
                        var code = rs.code;
                        var data = rs.data;

                        if (code == 1) {

                            $scope.showAlert("保存成功",function(){
                                window.location.reload();
                            });

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
                 });
            }
            ,
            //新增计量单位
            insertUnit:function(data,$scope, $http){
                $http.post(remoteUrl.insertUnit, data).then(
                    function (result) {

                        var rs = result.data;
                        var code = rs.code;
                        var data = rs.data;

                        if (code == 1) {

                            $scope.showAlert("保存成功",function(){
                                window.location.reload();
                            });

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
                 });
            }
            ,
            //删除计量单位请求
            delUnit:function(strUnitId,$scope, $http){

                var data = {
                    'strUnitId': strUnitId
                };
                $http.post(remoteUrl.delUnit,data).then(
                    function (result) {
						console.info(strUnitId)
                        var rs = result.data;
                        var code = rs.code;
                        var data = rs.data;
						console.info(result)
                        if (code == 1) {
							
                            window.location.reload();

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
							$scope.showAlert(rs.msg);	
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
                    });
            }
            ,
            //获取计量单位列表
            selUnitList: function ($scope, $http) {

                var data = {
                    'pagenum': $scope.currentPage,
                    "pagesize": $scope.pageSize,
                };

                $http.post(remoteUrl.selUnitList, data).then(
                    function (result) {

                        var rs = result.data;
                        var code = rs.code;
                        var data = rs.data;
						console.info(data)
                        //code=-8;
                        if (code == 1) {
                            //图片跟路径
                            $scope.pageCount = data.iTotalPage;
                            $scope.unitList = data.unitList;
                            for (var i = 0; i < $scope.unitList.length; i++) {
                                $scope.isShowListMenu[i] = false;
                            }

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
                    });
            },
            ////////////////////计量单位部分结束/////////////////////////
        	
        	

            ////////////////////商品管理开始/////////////////////////

            goodsAdministrationController::function($scope, $http){
				

                $scope.currentPage = 1;
                $scope.pageSize = 5;
                $scope.isShowListMenu = [];
                $scope.listGoodsInfo={};
                
                //保存
                $scope.submitExpandinfo=function(){
					if($scope.typePanduan){
						//调用修改接口
						 MemberCenter.updateGoodsInfo($scope.listGoodsInfo,$scope, $http);
					}
					else{
						 //调用保存接口
                   		MemberCenter.insertGoodsInfo($scope.listGoodsInfo,$scope, $http);
					}
                   

                };


                MemberCenter.getListGoodsInfoByPage($scope, $http);
                $scope.onPageChange = function () {
                    // ajax request to load data
                    $scope.listGoodsInfo = {};
                    MemberCenter.getListGoodsInfoByPage($scope, $http);

                };

                $scope.openCtrMenu = function ($index, type) {
                    for (var i = 0; i < $scope.listGoodsInfo.length; i++) {
                        $scope.isShowListMenu[i] = false;
                    }
                    if (type == 'over') {
                        $scope.isShowListMenu[$index] = !$scope.isShowListMenu[$index];
                    }

                };
                //关闭窗口
                $scope.clostExpandWindow=function(){
                    $scope.showExpandInfoWindow=false;
                    $scope.listGoodsInfo={};
                    MemberCenter.getListGoodsInfoByPage($scope, $http);
                };
                //新建商品点击按钮事件
                $scope.newExpandinginfo=function(){

                    $scope.showExpandInfoWindow=true;
                    $scope.isAddNewExpand=true;
                    //判断执行添加还是修改
                    $scope.typePanduan = false;
                };
                //修改商品点击按钮事件
                $scope.updataExpand=function(strGoodsId){

                    $scope.showExpandInfoWindow=true;
                    $scope.isAddNewExpand=false;
                    //判断执行添加还是修改
                    $scope.typePanduan = true;
                    //调用接口-查询详情
                    MemberCenter.getGoodsInfo(strGoodsId,$scope, $http);
                };
                //删除商品
                $scope.delectExpand=function(strGoodsId,strGoodsName){

                    $scope.showConfirm("确认要删除" +strGoodsName+"？",function(rs){
                        //调用删除拓展资料函数
                        if(rs){
                        	console.info(rs)
                            MemberCenter.delGoodsInfo(strGoodsId,$scope, $http);
                        }

                    })
                }

            },
            //查询商品详情
            getGoodsInfo:function(strGoodsId,$scope, $http) {

                var data={"strGoodsId":strGoodsId}
                $http.post(remoteUrl.getGoodsInfo, data).then(
                    function (result) {

                        var rs = result.data;
                        var code = rs.code;
                        var data = rs.data;
                        var GoodsInfo = data.GoodsInfo;
						console.info(data)
                        if (code == 1) {
                        	angular.forEach(GoodsInfo,function(val,key){
								$scope.listGoodsInfo[key] = val;
							})
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
                    });
            }
             ,
             //修改商品
            updateGoodsInfo:function(data,$scope, $http){
                $http.post(remoteUrl.updateGoodsInfo, data).then(
                    function (result) {

                        var rs = result.data;
                        var code = rs.code;
                        var data = rs.data;

                        if (code == 1) {

                            $scope.showAlert("保存成功",function(){
                                window.location.reload();
                            });

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
                 });
            }
            ,
            //新增商品
            insertGoodsInfo:function(data,$scope, $http){
                $http.post(remoteUrl.insertGoodsInfo, data).then(
                    function (result) {

                        var rs = result.data;
                        var code = rs.code;
                        var data = rs.data;

                        if (code == 1) {

                            $scope.showAlert("保存成功",function(){
                                window.location.reload();
                            });

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
                 });
            }
            ,
            //删除商品请求
            delGoodsInfo:function(strGoodsId,$scope, $http){

                var data = {
                    'strGoodsId': strGoodsId
                };
                $http.post(remoteUrl.delGoodsInfo,data).then(
                    function (result) {
						console.info(strGoodsId)
                        var rs = result.data;
                        var code = rs.code;
                        var data = rs.data;
						console.info(result)
                        if (code == 1) {
							
                            window.location.reload();

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
							$scope.showAlert(rs.msg);	
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
                    });
            }
            ,
            //获取商品列表
            getListGoodsInfoByPage: function ($scope, $http) {

                var data = {
                    'pagenum': $scope.currentPage,
                    "pagesize": $scope.pageSize,
                };

                $http.post(remoteUrl.getListGoodsInfoByPage, data).then(
                    function (result) {

                        var rs = result.data;
                        var code = rs.code;
                        var data = rs.data;
						console.info(data)
                        //code=-8;
                        if (code == 1) {
                            //图片跟路径
                            $scope.pageCount = data.iTotalPage;
                            $scope.listGoodsInfo = data.listGoodsInfo;
                            for (var i = 0; i < $scope.listGoodsInfo.length; i++) {
                                $scope.isShowListMenu[i] = false;
                            }

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
                    });
            },
            ////////////////////商品管理部分结束/////////////////////////
            
            
            cont:function($scope, $http){
            	var data = {
            		'strGoodsId': "fcd7c396a09147e0b124976e9e224a1b"
            	}
            	$http.post(remoteUrl.getGoodsInfo,data).then(function(result){
            		console.info(result)
            	},function(){
            		
            	})
            }

        };

        return MemberCenter;

    });