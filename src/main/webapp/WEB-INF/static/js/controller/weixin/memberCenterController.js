/**
 * Created by zhujunliang on 2017/7/6.
 */
define(
    ['lib/remoteUrl'], function (remoteUrl) {
        'use strict'

        //////////////判断圆点的个数  保证其浮动//////////
        function number(){
            //圆点的排布
            var num = document.getElementsByClassName("z-vipCenter-cycle");
            //圆点的排布
            //var num = document.getElementsByClassName("z-vipCenter-cycle");
            //黄色卡片处虚线
            var dash = document.getElementsByClassName("z-vipCenter-cycle-absolute");
            //小黄卡片
            var yellowcard = document.getElementsByClassName("z-vipCenter-cycle-position-absolute");
            //绿色字体
            var greenfont = document.getElementsByClassName("z-vipCenter-cycle-complain");
            var nums = num.length;
            var k = nums;
            var height = 4/(nums+1);
            var width = 7 / (nums+1);
            console.info(nums)
            for(var i = 0;i <nums;++i){
                num[i].style.top = height*(k--)+"rem";
                num[i].style.left = width*(i+1)+"rem";
                if(i%2 == 0){
                    dash[i].style.top = 0.3+"rem";
                    yellowcard[i].style.top = 1+"rem";
                    if(i == 0){
                        yellowcard[i].style.top = .8+"rem";
                    }
                    greenfont[i].style.top = -.5+"rem";
                }
            }
        }
        //////////////判断圆点的个数  保证其浮动//////////

        var memberCenterController = {
            ////////////////////////已经过期的抵用券///////////////////////
            memberCenter:function($scope,$http){

                memberCenterController.selectMemberInfo($scope,$http)//登录会员信息 -- 查询
                memberCenterController.selectAllLevelsInfo($scope,$http)//所有会员级别查询 -- 列表
                memberCenterController.selectMemberLevelsRightsMappingEntityInfo($scope,$http)//查询会员权益
                memberCenterController.selectMemberArticlesInfo($scope,$http)//会员章程查询


            },
            //会员章程查询
            selectMemberArticlesInfo:function($scope,$http){
                $http.post(remoteUrl.selectMemberArticlesInfo).then(
                    function(result){
                        var rs =result.data;
                        var code =rs.code;
                        var data=rs.data;
                        console.info(result)
                        if(code==1){
                            $scope.listMemberarticlesEntity = data.listMemberarticlesEntity;
                        }else if(code ==-1){
                            //得到登录路径
                            window.location.href="/admin/login?url="+window.location.pathname+window.location.search+window.location.hash;
                            //未登录
                        }else if(code<=-2&&code>=-7){
                            //必填字段未填写
                            $scope.showToast(rs.msg);
                        }else if(code ==-8){
                            //暂无数据
                            $scope.showToast(rs.msg);
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
            //查询会员权益
            selectMemberLevelsRightsMappingEntityInfo:function($scope,$http){
                $http.post(remoteUrl.selectMemberLevelsRightsMappingEntityInfo).then(
                    function(result){
                        var rs =result.data;
                        var code =rs.code;
                        var data=rs.data;
                        console.info(result)
                        if(code==1){
                            $scope.listMemberLevelsRightsMappingEntity = data.listMemberLevelsRightsMappingEntity;
                        }else if(code ==-1){
                            //得到登录路径
                            window.location.href="/admin/login?url="+window.location.pathname+window.location.search+window.location.hash;
                            //未登录
                        }else if(code<=-2&&code>=-7){
                            //必填字段未填写
                            $scope.showToast(rs.msg);
                        }else if(code ==-8){
                            //暂无数据
                            $scope.showToast(rs.msg);
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
            //登录会员信息 -- 查询
            selectMemberInfo:function($scope,$http){
                $http.post(remoteUrl.selectMemberInfo).then(
                    function(result){
                        var rs =result.data;
                        var code =rs.code;
                        var data=rs.data;
                        console.info(result)
                        if(code==1){
                            $scope.strLevelsName = data.strLevelsName;
                        }else if(code ==-1){
                            //得到登录路径
                            window.location.href="/admin/login?url="+window.location.pathname+window.location.search+window.location.hash;
                            //未登录
                        }else if(code<=-2&&code>=-7){
                            //必填字段未填写
                            $scope.showToast(rs.msg);
                        }else if(code ==-8){
                            //暂无数据
                            $scope.showToast(rs.msg);
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
            //所有会员级别查询 -- 列表
            selectAllLevelsInfo:function($scope,$http){
                $http.post(remoteUrl.selectAllLevelsInfo).then(
                    function(result){
                        var rs =result.data;
                        var code =rs.code;
                        var data=rs.data;
                        console.info(result)
                        if(code==1){
                            $scope.listMemberlevelsEntity = data.listMemberlevelsEntity;
                            var id = setTimeout(function(){
                                if($scope.listMemberlevelsEntity){
                                    number();
                                    clearInterval(id)
                                }
                            },10);
                        }else if(code ==-1){
                            //得到登录路径
                            window.location.href="/admin/login?url="+window.location.pathname+window.location.search+window.location.hash;
                            //未登录
                        }else if(code<=-2&&code>=-7){
                            //必填字段未填写
                            $scope.showToast(rs.msg);
                        }else if(code ==-8){
                            //暂无数据
                            $scope.showToast(rs.msg);
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
        return memberCenterController;

    });

