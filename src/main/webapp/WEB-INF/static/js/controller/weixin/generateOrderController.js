/**
 * Created by zhujunliang on 2017/7/6.
 */
define(
    ['lib/remoteUrl'], function (remoteUrl) {
        'use strict'
        console.info(122444)
        var generateOrderController = {
            ///////////////////////////支付成功//////////////////
            generateOrderPaySuccess:function($scope,$http){
                var strOrderId = window.location.hash.split("=")[1];
                generateOrderController.selectPayOverInfo($scope,$http,strOrderId);
            },
            selectPayOverInfo:function($scope,$http,strOrderId){
                var date = {
                    strOrderId:strOrderId
                };
                $http.post(remoteUrl.selectPayOverInfo,date).then(
                    function(result){
                        var rs =result.data;
                        var code =rs.code;
                        var data=rs.data;
                        console.info(result)
                        if(code==1){
                            //window.location.hash ="#!/generateOrderPaySuccess";
                            ////$scope.showToast(rs.msg)
                            $scope.dingdanxinxifankui = rs.data;
                        }else if(code ==-1){
                            //得到登录路径
                            window.location.href="/admin/login?url="+window.location.pathname+window.location.search+window.location.hash;
                            //未登录
                        }else if(code<=-2&&code>=-7){
                            //必填字段未填写
                            $scope.showToast(rs.msg);
                        }else if(code ==-8){
                            //暂无数据
                        }

                    },
                    function(result){
                        var status=result.status;
                        if(status==-1){
                            $scope.showToast("服务器错误")
                        }else if(status>=404&&status<500){
                            $scope.showToast("请求路径错误")
                        }else if(status>=500){
                            $scope.showToast("服务器错误")
                        }
                    }
                )
            },
            /////////////////////选择支付方式//////////////
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
                    }else if($scope.PayChanges == 3){
                        generateOrderController.payGoodsOrderWithCardCash($scope,$http,strOrderId)//储值支付

                    }
                }
            },
            //////////////////////粗制支付  订单 --会员卡余额支付////////////////////
            payGoodsOrderWithCardCash:function($scope,$http,strOrderId){
                var date = {
                    strOrderId:strOrderId
                };
                $http.post(remoteUrl.payGoodsOrderWithCardCash,date).then(
                    function(result){
                        var rs =result.data;
                        var code =rs.code;
                        var data=rs.data;
                        console.info(result)
                        if(code==1){

                            window.location.hash ="#!/generateOrderPaySuccess?strOrderId="+strOrderId;//支付成功后跳转
                            //window.location.search = "?strOrderId=" + strOrderId;
                            //$scope.showToast(rs.msg)
                        }else if(code ==-1){
                            //得到登录路径
                            window.location.href="/admin/login?url="+window.location.pathname+window.location.search+window.location.hash;
                            //未登录
                        }else if(code<=-2&&code>=-7){
                            //必填字段未填写
                            $scope.showToast(rs.msg);
                        }else if(code ==-8){
                            //暂无数据
                        }

                    },
                    function(result){
                        var status=result.status;
                        if(status==-1){
                            $scope.showToast("服务器错误")
                        }else if(status>=404&&status<500){
                            $scope.showToast("请求路径错误")
                        }else if(status>=500){
                            $scope.showToast("服务器错误")
                        }
                    }
                )
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
                            $scope.showToast(rs.msg,function(){
                                window.location.hash ="#!/generateOrderPaySuccess?strOrderId="+strOrderId;//支付成功后跳转
                            })

                        }else if(code ==-1){
                            //得到登录路径
                            window.location.href="/admin/login?url="+window.location.pathname+window.location.search+window.location.hash;
                            //未登录
                        }else if(code<=-2&&code>=-7){
                            //必填字段未填写
                            $scope.showToast(rs.msg);
                        }else if(code ==-8){
                            //暂无数据
                        }

                    },
                    function(result){
                        var status=result.status;
                        if(status==-1){
                            $scope.showToast("服务器错误")
                        }else if(status>=404&&status<500){
                            $scope.showToast("请求路径错误")
                        }else if(status>=500){
                            $scope.showToast("服务器错误")
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
                            $scope.showToast(rs.msg);
                        }else if(code ==-8){
                            //暂无数据
                        }

                    },
                    function(result){
                        var status=result.status;
                        if(status==-1){
                            $scope.showToast("服务器错误")
                        }else if(status>=404&&status<500){
                            $scope.showToast("请求路径错误")
                        }else if(status>=500){
                            $scope.showToast("服务器错误")
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
                   for(var i=0;i< $scope.ShowProvince.length;i++){
                       if($scope.ShowProvince[i].iProvinceCode == iProvinceCode){
                         $scope.shouhuoxiadingdan.strProvinceName =  $scope.ShowProvince[i].strProvinceName;
                       }
                   }
                    console.info($scope.shouhuoxiadingdan.strProvinceName)
                }
                //城市信息变化对应的区县信息查询  iCityCodeChecked
                $scope.iCityCodeChecked = function(iCityCode){
                    generateOrderController.selectAreaCountyInfo($scope,$http,iCityCode);
                    for(var i=0;i< $scope.ShowCityInfo.length;i++){
                        if($scope.ShowCityInfo[i].iCityCode == iCityCode){
                            $scope.shouhuoxiadingdan.strCityName =  $scope.ShowCityInfo[i].strCityName;
                        }
                    }
                    console.info($scope.shouhuoxiadingdan.strCityName)
                }
                //xiangchn信息变化对应的区县信息查询  iCityCodeChecked
                $scope.iAreaCountryChecked = function(iAreaCountryCode){
                    for(var i=0;i< $scope.AreaCountyInfo.length;i++){
                        if($scope.AreaCountyInfo[i].iAreaCountryCode == iAreaCountryCode){
                            $scope.shouhuoxiadingdan.strAreaCountryName =  $scope.AreaCountyInfo[i].strAreaCountryName;
                        }
                    }
                    console.info($scope.shouhuoxiadingdan.strAreaCountryName)
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
                    //if($scope.GotoShopGetShow){
                        console.info($scope.buyNumber +":"+strGoodsIds +":"+ $scope.strReceiveTime )
                        var strReceiveTime =  $scope.strReceiveTime.toLocaleString();
                        console.info(strReceiveTime)

                        generateOrderController.generateWeiXinOrderToShop($scope,$http,$scope.buyNumber,strGoodsIds,strReceiveTime);
                    //}
                    //generateOrderController.generateWeiXinOrderToShop($scope,$http,iCityCode);
                }
                //地址获取下订单
                $scope.AddressSelect = function(){
                    generateOrderController.generateWeiXinOrderExpress($scope,$http,$scope.buyNumber,strGoodsIds,$scope.shouhuoxiadingdan);
                };
            },
            //快递方式生成订单
            generateWeiXinOrderExpress:function($scope,$http,buyNumber,strGoodsIds,shouhuoxiadingdan){
                var date = {
                    iPurchaseAmount:buyNumber,
                    strGoodsId:strGoodsIds,
                    strPhone:shouhuoxiadingdan.tellphone,
                    strPostalCode:shouhuoxiadingdan.eamailAddress,
                    strReceiveName:shouhuoxiadingdan.name + shouhuoxiadingdan.namess,
                    strReceiveAddress:shouhuoxiadingdan.strProvinceName+shouhuoxiadingdan.strCityName+shouhuoxiadingdan.strAreaCountryName+shouhuoxiadingdan.Areas+shouhuoxiadingdan.room
                }
                $http.post(remoteUrl.generateWeiXinOrderExpress,date).then(
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
                            $scope.showToast(rs.msg);
                        }else if(code ==-8){
                            //暂无数据
                        }
                    },
                    function(result){
                        var status=result.status;
                        if(status==-1){
                            $scope.showToast("服务器错误")
                        }else if(status>=404&&status<500){
                            $scope.showToast("请求路径错误")
                        }else if(status>=500){
                            $scope.showToast("服务器错误")
                        }
                    }
                )
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
                            $scope.showToast(rs.msg);
                        }else if(code ==-8){
                            //暂无数据
                        }
                    },
                    function(result){
                        var status=result.status;
                        if(status==-1){
                            $scope.showToast("服务器错误")
                        }else if(status>=404&&status<500){
                            $scope.showToast("请求路径错误")
                        }else if(status>=500){
                            $scope.showToast("服务器错误")
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
                            $scope.showToast(rs.msg);
                        }else if(code ==-8){
                            //暂无数据
                        }

                    },
                    function(result){
                        var status=result.status;
                        if(status==-1){
                            $scope.showToast("服务器错误")
                        }else if(status>=404&&status<500){
                            $scope.showToast("请求路径错误")
                        }else if(status>=500){
                            $scope.showToast("服务器错误")
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
                            $scope.showToast(rs.msg);
                        }else if(code ==-8){
                            //暂无数据
                        }

                    },
                    function(result){
                        var status=result.status;
                        if(status==-1){
                            $scope.showToast("服务器错误")
                        }else if(status>=404&&status<500){
                            $scope.showToast("请求路径错误")
                        }else if(status>=500){
                            $scope.showToast("服务器错误")
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
                            $scope.showToast(rs.msg);
                        }else if(code ==-8){
                            //暂无数据
                        }

                    },
                    function(result){
                        var status=result.status;
                        if(status==-1){
                            $scope.showToast("服务器错误")
                        }else if(status>=404&&status<500){
                            $scope.showToast("请求路径错误")
                        }else if(status>=500){
                            $scope.showToast("服务器错误")
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
                            $scope.showToast(rs.msg);
                        }else if(code ==-8){
                            //暂无数据
                        }

                    },
                    function(result){
                        var status=result.status;
                        if(status==-1){
                            $scope.showToast("服务器错误")
                        }else if(status>=404&&status<500){
                            $scope.showToast("请求路径错误")
                        }else if(status>=500){
                            $scope.showToast("服务器错误")
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
                            $scope.showToast(rs.msg);
                        }else if(code ==-8){
                            //暂无数据
                        }

                    },
                    function(result){
                        var status=result.status;
                        if(status==-1){
                            $scope.showToast("服务器错误")
                        }else if(status>=404&&status<500){
                            $scope.showToast("请求路径错误")
                        }else if(status>=500){
                            $scope.showToast("服务器错误")
                        }
                    }
                )
            }
        }
        return generateOrderController;

    });
