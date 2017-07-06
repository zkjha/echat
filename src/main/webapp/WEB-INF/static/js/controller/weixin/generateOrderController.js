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
            generateOrderPre:function($scope,$http){
                //调用查询商品详情接口
                var strGoodsIds = window.location.search.split("=")[1];
                generateOrderController.selectGoodsDetailInfo($scope,$http,strGoodsIds)
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
            },
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
