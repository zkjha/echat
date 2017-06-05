/**
 * Created by zhujunliang on 17/4/3.
 * 处理收银控制器
 */
define(
    ['lib/remoteUrl'], function (remoteUrl) {
        'use strict'

        var cashierCtrl = {
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
						 cashierCtrl.updateGoodsType($scope.goodsTypeList,$scope, $http);
					}
					else{
						 //调用保存接口
                   		cashierCtrl.insertGoodsType($scope.goodsTypeList,$scope, $http);
					}
                   

                };


                cashierCtrl.selGoodsTypeList($scope, $http);
                $scope.onPageChange = function () {
                    // ajax request to load data
                    $scope.goodsTypeList = {};
                    cashierCtrl.selGoodsTypeList($scope, $http);

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
                    cashierCtrl.selGoodsTypeList($scope, $http);
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
                    cashierCtrl.selGoodsTypeDetail(strGoodsTypeId,$scope, $http);
                };
                //删除商品分类
                $scope.delectExpand=function(strGoodsTypeId,strGoodsTypeName){

                    $scope.showConfirm("确认要删除" +strGoodsTypeName+"？",function(rs){
                        //调用删除拓展资料函数
                        if(rs){
                            cashierCtrl.delGoodsType(strGoodsTypeId,$scope, $http);
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
                        var rs = result.data;
                        var code = rs.code;
                        var data = rs.data;
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
                    "pagesize": $scope.pageSize
                };
                $http.post(remoteUrl.selGoodsTypeList, data).then(
                    function (result) {
						
                        var rs = result.data;
                        var code = rs.code;
                        var data = rs.data;
                        //code=-8;
                        if (code == 1) {
                            //图片跟路径
                            $scope.pageCount = data.iTotalPage;
                            $scope.goodsTypeList = data.goodsTypeList;
                            for (var i = 0; i < $scope.goodsTypeList.length; i++) {
                                $scope.isShowListMenu[i] = false;
                            }
//						//商品管理部分调用
//						console.info($scope.goodsTypeList[0])
//							$scope.listGoodsInfoType.strGoodsTypeName = $scope.goodsTypeList[0].strGoodsTypeName;
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
						 cashierCtrl.updateUnit($scope.unitList,$scope, $http);
					}
					else{
						 //调用保存接口
                   		cashierCtrl.insertUnit($scope.unitList,$scope, $http);
					}
                   

                };


                cashierCtrl.selUnitList($scope, $http);
                $scope.onPageChange = function () {
                    // ajax request to load data
                    $scope.unitList = {};
                    cashierCtrl.selUnitList($scope, $http);

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
                    cashierCtrl.selUnitList($scope, $http);
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
                    cashierCtrl.selUnitDetail(strUnitId,$scope, $http);
                };
                //删除计量单位
                $scope.delectExpand=function(strUnitId,strUnitName){

                    $scope.showConfirm("确认要删除" +strUnitName+"？",function(rs){
                        //调用删除拓展资料函数
                        if(rs){
                            cashierCtrl.delUnit(strUnitId,$scope, $http);
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
                        var rs = result.data;
                        var code = rs.code;
                        var data = rs.data;
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
                    "pagesize": $scope.pageSize
                };

                $http.post(remoteUrl.selUnitList, data).then(
                    function (result) {

                        var rs = result.data;
                        var code = rs.code;
                        var data = rs.data;
                        //code=-8;
                        if (code == 1) {
                            //图片跟路径
                            $scope.pageCount = data.iTotalPage;
                            $scope.unitList = data.unitList;
                            for (var i = 0; i < $scope.unitList.length; i++) {
                                $scope.isShowListMenu[i] = false;
                            }
							//商品管理部分调用
//							$scope.listGoodsInfoType.strUnitName = $scope.unitList[0].strUnitName;
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

            goodsAdministrationController:function($scope, $http){
				

                $scope.currentPage = 1;
                $scope.pageSize = 5;
                $scope.isShowListMenu = [];
                $scope.listGoodsInfo={};
                $scope.listGoodsInfoType={};
                //商品管理状态代码
                $scope.iStateSelect = [{id:1,name:"启用"},{id:0,name:"停用"}];
                //商品管理状态会员优惠方式代码
                $scope.iPreferentialTypeSelect = [{id:1,name:"会员等级优惠"},{id:0,name:"无优惠"}];
                //改变商品类型改变商品类型id
                	$scope.shoopClass = function(){
                		var baocun = $scope.listGoodsInfoType.strGoodsTypeName;
                		for(var i = 0; i < $scope.goodsTypeList.length;i ++ ){
                			console.info(i)
                			if( baocun == $scope.goodsTypeList[i].strGoodsTypeName){
                				$scope.listGoodsInfoType.strGoodsTypeId = $scope.goodsTypeList[i].strGoodsTypeId;
                			}
                			
                		}
                		
                	}
                //保存
                $scope.submitExpandinfo=function(){
					if($scope.typePanduan){
						var str = "";
						for(var i =0;i < $scope.listGoodsPreferential.length;i++){
							$scope.strPreferentialId = $scope.listGoodsPreferential[i].strPreferentialId;
							$scope.strLevelsId = $scope.listGoodsPreferential[i].strLevelsId;
							$scope.strLevelsName = $scope.listGoodsPreferential[i].strLevelsName;
							$scope.iRequiredIntegral = $scope.listGoodsPreferential[i].iRequiredIntegral;
							str = $scope.strPreferentialId +"," + $scope.strLevelsId +"," + $scope.strLevelsName +"," + $scope.iRequiredIntegral + "|";
						}
						//调用修改接口+
						$scope.listGoodsInfoType.strGoodsPreferentials = str;
						cashierCtrl.updateGoodsInfo($scope.listGoodsInfoType,$scope, $http);
					}
					else{
						var xinzengStr = ""
						for(var i = 0;i < $scope.addAdmincont.length;i++){
							$scope.strLevelsIdBaocun = $scope.addAdmincont[i].strLevelsIdBaocun;
							$scope.strLevelsNameBaocun = $scope.addAdmincont[i].strLevelsNameBaocun;
							$scope.iRequiredIntegralBaocun = $scope.addAdmincont[i].iRequiredIntegralBaocun;
							xinzengStr = $scope.strLevelsIdBaocun+","+$scope.strLevelsNameBaocun+","+$scope.iRequiredIntegralBaocun+"|";
						}
						$scope.listGoodsInfoType.strGoodsPreferentials = xinzengStr;
						console.info($scope.listGoodsInfoType);
                   		cashierCtrl.insertGoodsInfo($scope.listGoodsInfoType,$scope, $http);
                   	}
                };


                cashierCtrl.getListGoodsInfoByPage($scope, $http);
                $scope.onPageChange = function () {
                    // ajax request to load data
                    $scope.listGoodsInfoType = {};
                    cashierCtrl.getListGoodsInfoByPage($scope, $http);

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
                    cashierCtrl.getListGoodsInfoByPage($scope, $http);
                };
                //新建商品点击按钮事件
                $scope.newExpandinginfo=function(){
                	$scope.shangpinTianjia = true;
                	$scope.shangpinXiugai = false;
//              	清空修改查询带来的影响
                	
                	//增加商品分级别优惠
                	$scope.addAdmincont = [{
                		"strLevelsIdBaocun" :"",
                		"strLevelsNameBaocun" :"",
                		"iRequiredIntegralBaocun":""
                	}]
                	$scope.addAddAdmincont = function(){
                		$scope.addAdmincont.push({"strLevelsIdBaocun": "","strLevelsNameBaocun" :"","iRequiredIntegralBaocun": ""})
                	}
                	$scope.slownAddAdmincont = function(){
                		if($scope.addAdmincont.length > 1){
                			$scope.addAdmincont.pop(); 
                		}
                	}
                	
                	//判断模态框显示影藏
                    $scope.showExpandInfoWindow=true;
                    $scope.isAddNewExpand=true;
                    
                    $scope.listGoodsInfoType={}
                    //判断执行添加还是修改
                    $scope.typePanduan = false;
//                  //调用商品查询列表接口
//                	cashierCtrl.getListGoodsInfoByPage($scope, $http);
                    //调用商品类型查询列表接口
                    cashierCtrl.selGoodsTypeList($scope, $http);
                    //调用计量单位接口
                    cashierCtrl.selUnitList($scope, $http);
                    //状态调用-下拉列表
                    $scope.listGoodsInfoType.iState = $scope.iStateSelect[0].id;
                    $scope.listGoodsInfoType.iPreferentialType = $scope.iPreferentialTypeSelect[0].id;
                    
                };
                //修改商品点击按钮事件
                $scope.updataExpand=function(strGoodsId){
                	$scope.shangpinTianjia = false;
                	$scope.shangpinXiugai = true;
                	
                	//增加商品分级别优惠
//              	$scope.listGoodsPreferential = [{
//              		"strLevelsId" :"",
//              		"strLevelsName" :"",
//              		"iRequiredIntegral":""
//              	}]
//              	$scope.xiugaiAddAddAdmincont = function(){
//              		$scope.listGoodsPreferential.push({"strLevelsId": "","strLevelsName" :"","iRequiredIntegral": ""})
//              	}
//              	$scope.xiugaislownAddAdmincont = function(){
//              		if($scope.listGoodsPreferential.length > 1){
//              			$scope.listGoodsPreferential.pop(); 
//              		}
//              		else{
//              			$scope.listGoodsPreferential.strLevelsId = "";
//              			$scope.listGoodsPreferential.strLevelsName = "";
//              			$scope.listGoodsPreferential.iRequiredIntegral = "";
//              		}
//              	}
                	
                	
                    $scope.showExpandInfoWindow=true;
                    $scope.isAddNewExpand=false;
                    //判断执行添加还是修改
                    $scope.typePanduan = true;
                    //调用接口-商品查询详情
                    cashierCtrl.getGoodsInfo(strGoodsId,$scope, $http);
                   //调用商品分级优惠接口
                	cashierCtrl.getListGoodsPreferentialByGoodsId(strGoodsId,$scope, $http);
                    //调用商品类型查询列表接口
                    cashierCtrl.selGoodsTypeList($scope, $http);
                    //调用计量单位接口
                    cashierCtrl.selUnitList($scope, $http);
                };
                //删除商品
                $scope.delectExpand=function(strGoodsId,strGoodsName){

                    $scope.showConfirm("确认要删除" +strGoodsName+"？",function(rs){
                        //调用删除拓展资料函数
                        if(rs){
                            cashierCtrl.delGoodsInfo(strGoodsId,$scope, $http);
                        }

                    })
                }

            },
            //商品－查询会员分级别优惠信息
            getListGoodsPreferentialByGoodsId:function(strGoodsId,$scope, $http) {

                var data={"strGoodsId":strGoodsId}
                $http.post(remoteUrl.getListGoodsPreferentialByGoodsId, data).then(
                    function (result) {
                        var rs = result.data;
                        var code = rs.code;
                        var data = rs.data;
                       
                        if (code == 1) {
                        	$scope.listGoodsPreferential = data.listGoodsPreferential;
								
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
            //查询商品详情
            getGoodsInfo:function(strGoodsId,$scope, $http) {

                var data={"strGoodsId":strGoodsId}
                $http.post(remoteUrl.getGoodsInfo, data).then(
                    function (result) {
                        var rs = result.data;
                        var code = rs.code;
                        var data = rs.data;
                       
                        var GoodsInfo = data.GoodsInfo;
						
                        if (code == 1) {
                        	angular.forEach(GoodsInfo,function(val,key){
								$scope.listGoodsInfoType[key] = val;
								
							})
                        	console.info($scope.listGoodsInfoType)
//                      	$scope.listGoodsInfoType.strGoodsBarCode = GoodsInfo.strGoodsBarCode;
                        	//状态调用-下拉列表
		                    $scope.listGoodsInfoType.iState = $scope.iStateSelect[GoodsInfo.iState].id;
		                    $scope.listGoodsInfoType.iPreferentialType = $scope.iPreferentialTypeSelect[GoodsInfo.iPreferentialType].id;
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
                        console.info(result)
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
                        console.info(result)
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
                        } else if (code <= -8) {
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
            //删除商品请求
            delGoodsInfo:function(strGoodsId,$scope, $http){

                var data = {
                    'strGoodsId': strGoodsId
                };
                $http.post(remoteUrl.delGoodsInfo,data).then(
                    function (result) {
                        var rs = result.data;
                        var code = rs.code;
                        var data = rs.data;
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
            
             ////////////////////服务分类开始/////////////////////////

            serversTypeController:function($scope, $http){
				
                $scope.currentPage = 1;
                $scope.pageSize = 5;
                $scope.isShowListMenu = [];
                $scope.listServiceType={};
                
                //保存
                $scope.submitExpandinfo=function(){
					if($scope.typePanduan){
						//调用修改接口
						 cashierCtrl.updateServiceType($scope.listServiceType,$scope, $http);
					}
					else{
						 //调用保存接口
                   		cashierCtrl.insertServiceType($scope.listServiceType,$scope, $http);
					}
                   

                };


                cashierCtrl.getListServiceType($scope, $http);
                $scope.onPageChange = function () {
                    // ajax request to load data
                    $scope.listServiceType = {};
                    cashierCtrl.getListServiceType($scope, $http);

                };

                $scope.openCtrMenu = function ($index, type) {
                    for (var i = 0; i < $scope.listServiceType.length; i++) {
                        $scope.isShowListMenu[i] = false;
                    }
                    if (type == 'over') {
                        $scope.isShowListMenu[$index] = !$scope.isShowListMenu[$index];
                    }

                };
                //关闭窗口
                $scope.clostExpandWindow=function(){
                    $scope.showExpandInfoWindow=false;
                    $scope.listServiceType={};
                    cashierCtrl.getListServiceType($scope, $http);
                };
                //新建服务分类点击按钮事件
                $scope.newExpandinginfo=function(){

                    $scope.showExpandInfoWindow=true;
                    $scope.isAddNewExpand=true;
                    //判断执行添加还是修改
                    $scope.typePanduan = false;
                };
                //修改服务分类点击按钮事件
                $scope.updataExpand=function(strServiceTypeId){

                    $scope.showExpandInfoWindow=true;
                    $scope.isAddNewExpand=false;
                    //判断执行添加还是修改
                    $scope.typePanduan = true;
                    //调用接口
                    cashierCtrl.getServiceType(strServiceTypeId,$scope, $http);
                };
                //删除服务分类
                $scope.delectExpand=function(strServiceTypeId,strServiceTypeName){
                    $scope.showConfirm("确认要删除" +strServiceTypeName+"？",function(rs){
                        //调用删除拓展资料函数
                        if(rs){
                            cashierCtrl.delServiceType(strServiceTypeId,$scope, $http);
                        }

                    })
                }

            },
            //查询服务分类详情
            getServiceType:function(strServiceTypeId,$scope, $http) {

                var data={"strServiceTypeId":strServiceTypeId}
                $http.post(remoteUrl.getServiceType, data).then(
                    function (result) {

                        var rs = result.data;
                        var code = rs.code;
                        var data = rs.data;
                        var ServiceType = data.ServiceType;
                        if (code == 1) {
							angular.forEach(ServiceType,function(val,key){
								$scope.listServiceType[key] = val;
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
             //修改服务分类
            updateServiceType:function(data,$scope, $http){
                $http.post(remoteUrl.updateServiceType, data).then(
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
            //新增服务分类
            insertServiceType:function(data,$scope, $http){
                $http.post(remoteUrl.insertServiceType, data).then(
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
            //删除服务分类请求
            delServiceType:function(strServiceTypeId,$scope, $http){

                var data = {
                    'strServiceTypeId': strServiceTypeId
                };
                $http.post(remoteUrl.delServiceType,data).then(
                    function (result) {
                        var rs = result.data;
                        var code = rs.code;
                        var data = rs.data;
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
            //获取服务分类列表
            getListServiceType: function ($scope, $http) {

                var data = {
                    'pagenum': $scope.currentPage,
                    "pagesize": $scope.pageSize,
                };

                $http.post(remoteUrl.getListServiceType, data).then(
                    function (result) {

                        var rs = result.data;
                        var code = rs.code;
                        var data = rs.data;
                        //code=-8;
                        if (code == 1) {
                            //图片跟路径
                            $scope.pageCount = data.iTotalPage;
                            $scope.listServiceType = data.listServiceType;
                            for (var i = 0; i < $scope.listServiceType.length; i++) {
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
            ////////////////////服务分类结束/////////////////////////
            
            
            
            
             ////////////////////服务管理开始/////////////////////////
				serviceAdministrationController:function($scope, $http){
                $scope.currentPage = 1;
                $scope.pageSize = 5;
                $scope.isShowListMenu = [];
                $scope.listServiceInfo={};
                $scope.listServiceInfoType={};
                //商品管理状态代码
                $scope.iStateSelect = [{id:1,name:"启用"},{id:0,name:"停用"}];
                //商品管理状态会员优惠方式代码
                $scope.iPreferentialTypeSelect = [{id:1,name:"会员等级优惠"},{id:0,name:"无优惠"}];
                //改变商品类型改变商品类型id
                	$scope.shoopClass = function(){
                		var baocun = $scope.listServiceInfoType.strServiceTypeName;
                		for(var i = 0; i < $scope.listServiceType.length;i ++ ){
                			console.info(i)
                			if( baocun == $scope.listServiceType[i].strServiceTypeName){
                				$scope.listServiceInfoType.strServiceTypeId = $scope.listServiceType[i].strServiceTypeId;
                			}
                			
                		}
                		
                	}
                //保存
                $scope.submitExpandinfo=function(){
					if($scope.typePanduan){
						var str = "";
						for(var i =0;i < $scope.listServicePreferentialEntity.length;i++){
							$scope.strPreferentialId = $scope.listServicePreferentialEntity[i].strPreferentialId;
							$scope.strLevelsId = $scope.listServicePreferentialEntity[i].strLevelsId;
							$scope.strLevelsName = $scope.listServicePreferentialEntity[i].strLevelsName;
							$scope.iRequiredIntegral = $scope.listServicePreferentialEntity[i].iRequiredIntegral;
							str = $scope.strPreferentialId +"," + $scope.strLevelsId +"," + $scope.strLevelsName +"," + $scope.iRequiredIntegral + "|";
						}
						//调用修改接口+
						$scope.listServiceInfoType.strServicePreferentialInfos = str;
						cashierCtrl.updateServiceInfo($scope.listServiceInfoType,$scope, $http);
					}
					else{
						var xinzengStr = ""
						for(var i = 0;i < $scope.addAdmincont.length;i++){
							$scope.strLevelsIdBaocun = $scope.addAdmincont[i].strLevelsIdBaocun;
							$scope.strLevelsNameBaocun = $scope.addAdmincont[i].strLevelsNameBaocun;
							$scope.iRequiredIntegralBaocun = $scope.addAdmincont[i].iRequiredIntegralBaocun;
							xinzengStr = $scope.strLevelsIdBaocun+","+$scope.strLevelsNameBaocun+","+$scope.iRequiredIntegralBaocun+"|";
						}
						$scope.listServiceInfoType.strServicePreferentialInfos = xinzengStr;
                   		cashierCtrl.insertServiceInfo($scope.listServiceInfoType,$scope, $http);
                   	}
                };


                cashierCtrl.getListServiceInfo($scope, $http);
                $scope.onPageChange = function () {
                    // ajax request to load data
                    $scope.listServiceInfoType = {};
                    cashierCtrl.getListServiceInfo($scope, $http);

                };

                $scope.openCtrMenu = function ($index, type) {
                    for (var i = 0; i < $scope.listServiceInfo.length; i++) {
                        $scope.isShowListMenu[i] = false;
                    }
                    if (type == 'over') {
                        $scope.isShowListMenu[$index] = !$scope.isShowListMenu[$index];
                    }

                };
                //关闭窗口
                $scope.clostExpandWindow=function(){
                    $scope.showExpandInfoWindow=false;
                    $scope.listServiceInfo={};
                    cashierCtrl.getListServiceInfo($scope, $http);
                };
                //新建服务管理点击按钮事件
                $scope.newExpandinginfo=function(){
                	$scope.shangpinTianjia = true;
                	$scope.shangpinXiugai = false;
//              	清空修改查询带来的影响
                	
                	//增加服务管理分级别优惠
                	$scope.addAdmincont = [{
                		"strLevelsIdBaocun" :"",
                		"strLevelsNameBaocun" :"",
                		"iRequiredIntegralBaocun":""
                	}]
                	$scope.addAddAdmincont = function(){
                		$scope.addAdmincont.push({"strLevelsIdBaocun": "","strLevelsNameBaocun" :"","iRequiredIntegralBaocun": ""})
                	}
                	$scope.slownAddAdmincont = function(){
                		if($scope.addAdmincont.length > 1){
                			$scope.addAdmincont.pop(); 
                		}
                	}
                	
                	//判断模态框显示影藏
                    $scope.showExpandInfoWindow=true;
                    $scope.isAddNewExpand=true;
                    
                    $scope.listServiceInfoType={}
                    //判断执行添加还是修改
                    $scope.typePanduan = false;
//                  //调用服务管理查询列表接口
//                	cashierCtrl.getListServiceInfo($scope, $http);
                    //调用服务管理类型查询列表接口
                    cashierCtrl.getListServiceType($scope, $http);
                    //调用计量单位接口
                    cashierCtrl.selUnitList($scope, $http);
                    //状态调用-下拉列表
                    $scope.listServiceInfoType.iState = $scope.iStateSelect[0].id;
                    $scope.listServiceInfoType.iPreferentialType = $scope.iPreferentialTypeSelect[0].id;
                    
                };
                //修改服务管理点击按钮事件
                $scope.updataExpand=function(strServiceInfoId){
                	$scope.shangpinTianjia = false;
                	$scope.shangpinXiugai = true;
                    $scope.showExpandInfoWindow=true;
                    $scope.isAddNewExpand=false;
                    //判断执行添加还是修改
                    $scope.typePanduan = true;
                    //调用接口-服务管理查询详情
                    cashierCtrl.getServiceInfo(strServiceInfoId,$scope, $http);
                   //调用服务管理分级优惠接口
                	cashierCtrl.getListServicePreferentialByServiceId(strServiceInfoId,$scope, $http);
                    //调用服务管理类型查询列表接口
                    cashierCtrl.getListServiceType($scope, $http);
                    //调用计量单位接口
                    cashierCtrl.selUnitList($scope, $http);
                };
                //删除服务管理
                $scope.delectExpand=function(strServiceInfoId,strServiceTypeName){

                    $scope.showConfirm("确认要删除" +strServiceTypeName+"？",function(rs){
                        //调用删除拓展资料函数
                        if(rs){
                            cashierCtrl.delServiceInfo(strServiceInfoId,$scope, $http);
                        }

                    })
                }

            },
            //服务管理－查询会员分级别优惠信息
            getListServicePreferentialByServiceId:function(strServiceInfoId,$scope, $http) {

                var data={"strServiceInfoId":strServiceInfoId}
                $http.post(remoteUrl.getListServicePreferentialByServiceId, data).then(
                    function (result) {
                        var rs = result.data;
                        var code = rs.code;
                        var data = rs.data;
                       
                        if (code == 1) {
                        	$scope.listServicePreferentialEntity = data.listServicePreferentialEntity;
								
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
            //查询服务管理详情
            getServiceInfo:function(strServiceInfoId,$scope, $http) {

                var data={"strServiceInfoId":strServiceInfoId}
                $http.post(remoteUrl.getServiceInfo, data).then(
                    function (result) {
                        var rs = result.data;
                        var code = rs.code;
                        var data = rs.data;
                       
                        var ServiceInfo = data.ServiceInfo;
						
                        if (code == 1) {
                        	angular.forEach(ServiceInfo,function(val,key){
								$scope.listServiceInfoType[key] = val;
								
							})
                        	console.info($scope.listServiceInfoType)
//                      	$scope.listServiceInfoType.strGoodsBarCode = GoodsInfo.strGoodsBarCode;
                        	//状态调用-下拉列表
		                    $scope.listServiceInfoType.iState = $scope.iStateSelect[ServiceInfo.iState].id;
		                    $scope.listServiceInfoType.iPreferentialType = $scope.iPreferentialTypeSelect[ServiceInfo.iPreferentialType].id;
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
             //修改服务管理
            updateServiceInfo:function(data,$scope, $http){
                $http.post(remoteUrl.updateServiceInfo, data).then(
                    function (result) {

                        var rs = result.data;
                        var code = rs.code;
                        var data = rs.data;
                        console.info(result)
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
            //新增服务管理
            insertServiceInfo:function(data,$scope, $http){
                $http.post(remoteUrl.insertServiceInfo, data).then(
                    function (result) {

                        var rs = result.data;
                        var code = rs.code;
                        var data = rs.data;
                        console.info(result)
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
                        } else if (code <= -8) {
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
            //删除服务管理请求
            delServiceInfo:function(strServiceInfoId,$scope, $http){

                var data = {
                    'strServiceInfoId': strServiceInfoId
                };
                $http.post(remoteUrl.delServiceInfo,data).then(
                    function (result) {
                        var rs = result.data;
                        var code = rs.code;
                        var data = rs.data;
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
            //获取服务管理列表
            getListServiceInfo: function ($scope, $http) {

                var data = {
                    'pagenum': $scope.currentPage,
                    "pagesize": $scope.pageSize,
                };

                $http.post(remoteUrl.getListServiceInfo, data).then(
                    function (result) {

                        var rs = result.data;
                        var code = rs.code;
                        var data = rs.data;
                        //code=-8;
                        if (code == 1) {
                            //图片跟路径
                            $scope.pageCount = data.iTotalPage;
                            $scope.listServiceInfo = data.listServiceInfo;
                            for (var i = 0; i < $scope.listServiceInfo.length; i++) {
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
            ////////////////////服务管理部分结束/////////////////////////
            
            cont:function($scope, $http){
            	var data = {
            		'strServiceInfoId': "fcd7c396a09147e0b124976e9e224a1b"
            	}
            	$http.post(remoteUrl.getServiceInfo,data).then(function(result){
            	},function(){
            		
            	})
            }

        };

        return cashierCtrl;

    });