/**
 * Created by zhujunliang on 2017/7/6.
 */
/**
 * Created by Administrator on 2017/7/3.
 */

/**
 * Created by zhujunliang on 17/4/3.
 * 处理收银控制器
 */
define(
    ['lib/remoteUrl'], function (remoteUrl) {
        'use strict'
        console.info(122444)
        var generateOrderController = {
            generateOrderDetail:function($scope,$http){
                console.info(4884848484848484)
                generateOrderController.selectWeiXinGoodsOrderEntity($scope,$http)//查询订单详情

                $scope.PayChanges = 0;//默认积分支付
                $scope.PayFangshi = function(Pay){//支付方式
                    $scope.PayChanges = Pay;
                };
                $scope.surePay = function(strOrderId){
                    if($scope.PayChanges == 0){
                        generateOrderController.payGoodsOrderWithIntegration($scope,$http,strOrderId)//积分支付
                    }
                }
            },
            //////////////////////积分支付////////////////////
            payGoodsOrderWithIntegration:function($scope,$http,strOrderId){
                var date = {
                    strOrderId:strOrderId
                };
                $http.post(remoteUrl.payGoodsOrderWithIntegration,date).then(
                    function(result){
                        var rs =result.data;
                        var code =rs.code;
                        var data=rs.data;
                        console.info(result)
                        if(code==1){
                            alert(rs.msg)
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
                )
            },
            //////////////////////查询订单详情////////////////
            selectWeiXinGoodsOrderEntity:function($scope,$http){
                $http.post(remoteUrl.selectWeiXinGoodsOrderEntity).then(
                    function(result){
                        var rs =result.data;
                        var code =rs.code;
                        var data=rs.data;
                        console.info(result)
                        if(code==1){
                            $scope.weiXinGoodsOrderEntity = data.weiXinGoodsOrderEntity;
                            console.info($scope.weiXinGoodsOrderEntity)
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
                )
            },
            ////////////////////////////////下订单///////////////////////
            generateOrderPre:function($scope,$http){
                $scope.strReceiveTime = new Date();
                console.info($scope.strReceiveTime)
                //调用查询商品详情接口
                var strGoodsIds = window.location.search.split("=")[1];
                generateOrderController.selectGoodsDetailInfo($scope,$http,strGoodsIds)
                //商家详细信息单条
                generateOrderController.selectMerchantAddress($scope,$http);
                //调用省会地址
                generateOrderController.selectProvince($scope,$http);
                //到店领取显示与隐藏
                $scope.GotoShopGetShow = true;//默认显示
                $scope.GotoShopSpeedShow = false;

                $scope.GotoShopGet = function(){
                    $scope.GotoShopGetShow = true;//显示到店自取
                    $scope.GotoShopSpeedShow = false;//关闭快递地址选择
                }
                //省会信息变化对应的城市信息查询  iCityCodeChecked
                $scope.iProvinceCodeChecked = function(iProvinceCode){
                    generateOrderController.selectCityInfo($scope,$http,iProvinceCode);
                }
                //城市信息变化对应的区县信息查询  iCityCodeChecked
                $scope.iCityCodeChecked = function(iCityCode){
                    generateOrderController.selectAreaCountyInfo($scope,$http,iCityCode);
                }
                //快递配送显示与隐藏
                $scope.GotoShopSpeed = function(){
                    $scope.GotoShopGetShow = false;//关闭到店自取.
                    $scope.GotoShopSpeedShow = true;//显示快递地址选择
                    //console.info($scope.GotoShopSpeedShow)
                }
                //商品数量改变
                $scope.buyNumber = 1;//默认商品数量为1
                $scope.numberSlow = function(){

                    if($scope.buyNumber > 1){
                        $scope.buyNumber -= 1;
                    }
                    else{
                        return
                    }
                }
                $scope.numberAdd = function(){
                    console.info(typeof  $scope.buyNumber)
                    $scope.buyNumber = parseInt($scope.buyNumber)
                    $scope.buyNumber += 1;
                }
                //到店领取订单调用
                $scope.sureShengChengdongdan =function(){
                    if($scope.GotoShopGetShow){
                        console.info($scope.buyNumber +":"+strGoodsIds +":"+ $scope.strReceiveTime )
                        var strReceiveTime =  $scope.strReceiveTime.toLocaleString();
                        console.info(strReceiveTime)

                        generateOrderController.generateWeiXinOrderToShop($scope,$http,$scope.buyNumber,strGoodsIds,strReceiveTime);
                    }
                    else if($scope.GotoShopSpeedShow){
                        console.info(456789)
                    }

                    //generateOrderController.generateWeiXinOrderToShop($scope,$http,iCityCode);
                }
            },
            //到店领取生成订单
            generateWeiXinOrderToShop:function($scope,$http,buyNumber,strGoodsIds,strReceiveTime){
                var date = {
                    iPurchaseAmount:buyNumber,
                    strGoodsId:strGoodsIds,
                    strReceiveTime:strReceiveTime
                }
                $http.post(remoteUrl.generateWeiXinOrderToShop,date).then(
                    function(result){
                        var rs =result.data;
                        var code =rs.code;
                        var data=rs.data;
                        console.info(result)
                        if(code==1){
                            window.location.hash ="#!/generateOrderDetail";//跳转页面
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
                )
            },
            //调用区县地址
            selectAreaCountyInfo:function($scope,$http,iCityCode){
                var date = {
                    iCityCode:iCityCode
                }
                $http.post(remoteUrl.selectAreaCountyInfo,date).then(
                    function(result){
                        var rs =result.data;
                        var code =rs.code;
                        var data=rs.data;
                        console.info(result)
                        if(code==1){
                            $scope.AreaCountyInfo = data
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
                )
            },
            //调用城市地址
            selectCityInfo:function($scope,$http,iProvinceCode){
                var date = {
                    iProvinceCode:iProvinceCode
                }
                $http.post(remoteUrl.selectCityInfo,date).then(
                    function(result){
                        var rs =result.data;
                        var code =rs.code;
                        var data=rs.data;
                        console.info(result)
                        if(code==1){
                            $scope.ShowCityInfo = data
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
                )
            },
            //调用省会地址
            selectProvince:function($scope,$http){
                $http.post(remoteUrl.selectProvince).then(
                    function(result){
                        var rs =result.data;
                        var code =rs.code;
                        var data=rs.data;
                        console.info(result)
                        if(code==1){
                            $scope.ShowProvince = data
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
                )
            },
            //商家地址查询-单条
            selectMerchantAddress:function($scope,$http){
                $http.post(remoteUrl.selectMerchantAddress).then(
                    function(result){
                        var rs =result.data;
                        var code =rs.code;
                        var data=rs.data;
                        console.info(result)
                        if(code==1){
                            $scope.strAddress = data.strAddress
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
                )
            },
            //查询商品详细信息
            selectGoodsDetailInfo:function($scope,$http,strGoodsIds){
                var data={
                    strGoodsId:strGoodsIds
                }
                $http.post(remoteUrl.selectGoodsDetailInfo,data).then(
                    function(result){

                        var rs =result.data;
                        var code =rs.code;
                        var data=rs.data;
                        console.info(result)
                        if(code==1){
                            $scope.goodsVOEntity = data.goodsVOEntity;
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
                )
            }
        }
        return generateOrderController;

    });
