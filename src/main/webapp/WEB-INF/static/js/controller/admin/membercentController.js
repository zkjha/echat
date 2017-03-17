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

            ////////////////////会员章程设置部分开始/////////////////////////
            //会员章程展示控制器
            regulationsController:function($scope, $http, $sce){
                MemberCenter.getRegulationsinfo($scope, $http, $sce, 1);
            },
            //修改会员章程控制器
            editRegulationsController:function($scope, $http, $sce){

                // 回显数据
                MemberCenter.getRegulationsinfo($scope, $http, $sce, 2);
                $scope.frontinfoHtml = {};

                $scope.editFrontinfoisActive = true;

                $scope.saveFrontinfo = function () {
                    if ($scope.editFrontinfoisActive == false) {
                        return;
                    }
                    $scope.editFrontinfoisActive = false;
                    // 判断用户是否输入用户名
                    if (!$scope.strContent) {
                        $scope.showToast("内容必须填写");
                        $scope.editFrontinfoisActive = true;
                        return;
                    }
                    $http.post(remoteUrl.updateMemberarticles,
                        {
                            'strContent': $scope.strContent
                        })
                        .then(
                        function (result) {
                            $scope.editFrontinfoisActive = true;
                            var rs = result.data;
                            var code = rs.code;
                            if (code == 1) {
                                window.location.href = "#!/regulations";
                            } else if (code == -1) {
                                window.location.href = "/admin/login?url="
                                + window.location.pathname
                                + window.location.search
                                + window.location.hash;
                                // 未登录
                            } else if (code <= -2 && code >= -7) {
                                // 必填字段未填写
                                $scope.showAlert(rs.msg);
                            } else if (code == -8) {
                                // 暂无数据
                            }

                        },
                        function (result) {
                            $scope.editFrontinfoisActive = true;
                            var status = result.status;
                            if (status == -1) {
                                $scope.showAlert("服务器错误")
                            } else if (status >= 404
                                && status < 500) {
                                $scope.showAlert("请求路径错误")
                            } else if (status >= 500) {
                                $scope.showAlert("服务器错误")
                            }
                        }
                    );
                }
            },
            // 回显章程数据
            getRegulationsinfo: function ($scope, $http, $sce, type) {

                $http
                    .post(remoteUrl.getMemberarticles)
                    .then(
                    function (result) {
                        var rs = result.data;

                        var code = rs.code;
                        var data = rs.data;
                        if (code == 1) {

                            if (type == 1) {// 显示到普通html上
                                $scope.strContent = $sce
                                    .trustAsHtml(data.strContent);
                            } else {
                                $scope.strContent = data.strContent;
                            }

                        } else if (code == -1) {
                            // 未登录
                            window.location.href = "/admin/login?url="
                            + window.location.pathname
                            + window.location.search
                            + window.location.hash;
                        } else if (code <= -2 && code >= -7) {
                            // 必填字段未填写
                            $scope.showAlert(rs.msg);
                        } else if (code == -8) {
                            // 暂无数据
                            $scope.strContent = "暂无数据";
                        }

                    },
                    function (result) {
                        $scope.editFrontinfoisActive = true;
                        var status = result.status;
                        if (status == -1) {
                            $scope.showAlert("服务器错误")
                        } else if (status >= 404
                            && status < 500) {
                            $scope.showAlert("请求路径错误")
                        } else if (status >= 500) {
                            $scope.showAlert("服务器错误")
                        }
                    }
                );

            },

            ////////////////////会员章程设置部分结束/////////////////////////






            ////////////////////会员拓展资料部分开始/////////////////////////

            expandinfoController:function($scope, $http){


                $scope.currentPage = 1;
                $scope.pageSize = 5;
                $scope.isShowListMenu = [];
                $scope.memberexpandinformationEntity={};
                $scope.mustInputTypes=[{"id":1,value:"必填"},{"id":0,value:"非必填"}];
                //$scope.inputTypes=[{"id":2,value:"多选"},{"id":1,value:"下拉选择"},{"id":0,value:"输入框"}];
                $scope.inputTypes=[{"id":1,value:"下拉选择"},{"id":0,value:"输入框"}];
                //选择列表
                $scope.optionsArray=[];

                $scope.memberexpandinformationEntity.options=[];
                $scope.typeChange=function(){

                    if($scope.memberexpandinformationEntity.intType==0){
                        $scope.optionsArray.length=0;
                    }else{
                        $scope.optionsArray.push(0);

                    }
                };
                //保存
                $scope.submitExpandinfo=function(){

                    var intType =$scope.memberexpandinformationEntity.intType;
                    if(intType==1||intType==2){
                        $scope.memberexpandinformationEntity.strOptions = $scope.memberexpandinformationEntity.options.join(",");

                    }

                    console.log($scope.memberexpandinformationEntity);
                    //调用保存接口
                   MemberCenter.saveExpand($scope.memberexpandinformationEntity,$scope, $http);

                };

                //减少选项
                $scope.subOption=function(index){
                    console.log(index);

                    $scope.optionsArray.splice(index,1);
                    $scope.memberexpandinformationEntity.options.splice(index,1);

                    if($scope.optionsArray.length>1){
                        $scope.optionIsMoreOne=true;
                    }else{
                        $scope.optionIsMoreOne=false;
                    }

                };

                //增加选项
                $scope.addOption=function(index){

                    $scope.optionsArray.push(Math.random());
                    if($scope.optionsArray.length>1){
                        $scope.optionIsMoreOne=true;
                    }else{
                        $scope.optionIsMoreOne=false;
                    }
                };


                MemberCenter.getExpandInfoList($scope, $http);
                $scope.onPageChange = function () {
                    // ajax request to load data
                    $scope.memberexpandinformationEntityList = {};
                    MemberCenter.getExpandInfoList($scope, $http);

                };

                $scope.openCtrMenu = function ($index, type) {
                    for (var i = 0; i < $scope.memberexpandinformationEntityList.length; i++) {
                        $scope.isShowListMenu[i] = false;
                    }
                    if (type == 'over') {
                        $scope.isShowListMenu[$index] = !$scope.isShowListMenu[$index];
                    }

                };
                //关闭窗口
                $scope.clostExpandWindow=function(){
                    $scope.showExpandInfoWindow=false;
                    $scope.memberexpandinformationEntity={};
                };
                //新建会员拓展资料点击按钮事件
                $scope.newExpandinginfo=function(){

                    $scope.showExpandInfoWindow=true;
                    $scope.isAddNewExpand=true;
                    //初始化是否必填选择框
                    $scope.memberexpandinformationEntity.intIsmust=0;
                    //初始化拓展类型
                    $scope.memberexpandinformationEntity.intType=0;
                    $scope.optionsArray=[];
                };
                //修改会员拓展资料点击按钮事件
                $scope.updataExpand=function(strInformationid){

                    $scope.showExpandInfoWindow=true;
                    $scope.isAddNewExpand=false;
                    MemberCenter.selectExpand(strInformationid,$scope, $http);
                };
                //删除拓展资料
                $scope.delectExpand=function(strInformationid,strInformationname){

                    $scope.showConfirm("确认要删除" +strInformationname+"？",function(rs){
                        //调用删除拓展资料函数
                        if(rs){
                            MemberCenter.deleteExpand(strInformationid,$scope, $http);
                        }

                    })
                }

            },
            //查询拓展资料详情
            selectExpand:function(strInformationid,$scope, $http) {

                var data={"strInformationid":strInformationid}
                $http.post(remoteUrl.getMemberexpandinformationById, data).then(
                    function (result) {

                        var rs = result.data;
                        var code = rs.code;
                        var data = rs.data;

                        if (code == 1) {
                            $scope.optionsArray=[];
                            data.options= data.strOptions.split(",");
                            //console.log(data);
                            for(var i=0;i< data.options.length;i++){
                                $scope.optionsArray.push(Math.random());
                            }
                            $scope.memberexpandinformationEntity=data ;

                            if($scope.optionsArray.length>1){
                                $scope.optionIsMoreOne=true;
                            }else{
                                $scope.optionIsMoreOne=false;
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
            //保存拓展资料
            saveExpand:function(data,$scope, $http){
                $http.post(remoteUrl.insertMemberexpandinformation, data).then(
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
            //删除拓展资料请求
            deleteExpand:function(strInformationid,$scope, $http){

                var data = {
                    'strInformationid': strInformationid

                };

                $http.post(remoteUrl.deleteMemberexpandinformation, data).then(
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
            //获取会员拓展资料列表
            getExpandInfoList: function ($scope, $http) {

                var data = {
                    'pagenum': $scope.currentPage,
                    "pagesize": $scope.pageSize,
                    "strSearchkey":""
                };

                $http.post(remoteUrl.listMemberexpandinformation, data).then(
                    function (result) {

                        var rs = result.data;
                        var code = rs.code;
                        var data = rs.data;

                        //code=-8;
                        if (code == 1) {
                            //图片跟路径

                            $scope.pageCount = data.iTotalPage;
                            $scope.memberexpandinformationEntityList = data.memberexpandinformationEntityList;

                            for (var i = 0; i < $scope.memberexpandinformationEntityList.length; i++) {
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
            ////////////////////会员拓展资料部分结束/////////////////////////








            ////////////////////会员管理部分开始/////////////////////////

            memberSetController:function($scope, $http,$interval,$sce){



                $scope.isShowListMenu = [];
                $scope.currentPage = 1;
                $scope.pageSize = 5;
                $scope.member={};
                $scope.sexs=[{"id":0,"value":"男"},{"id":1,"value":"女"}];
                $scope.memberstatusList=[{"id":0,"value":"禁用"},{"id":1,"value":"激活"}];




               // MemberCenter.initExpandInfoList($scope, $http);
                MemberCenter.getMemberList($scope, $http);
                $scope.onPageChange = function () {
                    // ajax request to load data

                    MemberCenter.getMemberList($scope, $http);

                };

                $scope.openCtrMenu = function ($index, type) {
                    for (var i = 0; i < $scope.memberList.length; i++) {
                        $scope.isShowListMenu[i] = false;
                    }
                    if (type == 'over') {
                        $scope.isShowListMenu[$index] = !$scope.isShowListMenu[$index];
                    }

                };
                //新增会员信息
                $scope.newMemberinfo=function(){
                    $scope.isDisableSubmit=false;
                    $scope.showEditMemberInfoWindow=true;
                    $scope.isAddNewMember=true;
                    $scope.nowStep=1;
                    $scope.member={};
                    $scope.member2={};
                    //默认会员激活状态

                    //初始化会员级别信息
                   // MemberCenter.initMemberLevelsSelectData($scope, $http);
                    $scope.strMemberid="";
                    MemberCenter.getMemberById($scope, $http);

                };
                //显示会员详情弹窗
                $scope.memberDetail=function(strMemberid){


                    $scope.strMemberid=strMemberid;
                    MemberCenter.getMemberById($scope, $http);

                };
                //关闭会员详情弹窗
                $scope.closeDetailMemberInfoWindow=function(){
                    $scope.showDetailMemberInfoWindow=false;
                    MemberCenter.getMemberList($scope, $http);
                };
                //关闭编辑弹窗
                $scope.closeEditMemberInfoWindow=function(){
                    $scope.showEditMemberInfoWindow=false;
                };

                //打开编辑会员弹窗
                $scope.showUpdateMemberWindow=function(){
                    $scope.isDisableSubmit=false;
                    $scope.showEditMemberInfoWindow=true;
                    $scope.isAddNewMember=false;
                    $scope.nowStep=1;


                };
                //回到第一步
                $scope.backToFirstStep=function(){
                    $scope.nowStep=1;
                };
                //第一步提交
                $scope.submitMemberFirstinfo=function(){

                    $scope.nowStep=2;

                };

                //显示储值弹窗
                $scope.openvirtualRechargWindow=function(){
                    $scope.showVirtualRechargWindow=true;
                    $scope.virtualRecharg={};
                };
                //关闭储值弹窗
                $scope.closeVirtualRechargWindow=function(){
                    $scope.showVirtualRechargWindow=false;
                };
                //提交售后储值
                $scope.submitVirtualRecharg=function(){
                    $scope.isDisableSubmit=true;
                    var data={};
                    data.strMemberId=$scope.strMemberid;;
                    data.strRechargeAmount=$scope.virtualRecharg.strRechargeAmount;
                    MemberCenter.virtualRecharg($scope, $http,data);
                };

                //打开现金充值窗口
                $scope.openCashRechargWindow=function(){
                    $scope.paySuccessInterval;
                    $scope.cashRecharg={};
                    $scope.showCashRechargWindow=true;
                    $scope.cashRecharg.strPayType='1';
                    $scope.hadPay="NO";
                };
                //关闭现金充值窗口
                $scope.closeCashRechargWindow=function(){

                    $scope.showCashRechargWindow=false;
                    $interval.cancel( $scope.paySuccessInterval );
                };

                //提交现金支付信息
                $scope.submitCashRecharg=function(){

                    $scope.isDisableSubmit=true;
                    $scope.cashRecharg.strMemberId=$scope.strMemberid;
                    MemberCenter.cashRecharg($scope, $http,$interval,$sce,$scope.cashRecharg);
                };



                //展示用户订单列表
                $scope.openOrderListWindow=function(){

                    $scope.orderPageSize=4;
                    $scope.showOrderListWindow=true;
                    $scope.select={};
                    $scope.select.strPayType="-1";
                    MemberCenter.getOrderList($scope, $http);
                };

                //点击根据条件查询会员消费记录
                $scope.selectAction=function(){
                    $scope.orderCurrentPage=1;
                    MemberCenter.getOrderList($scope, $http);
                };

                //用户订单列表翻页事件
                $scope.orderOnPageChange = function () {

                    MemberCenter.getOrderList($scope, $http);
                };

                //关闭用户订单列表
                $scope.closeOrderListWindow=function(){
                    $scope.showOrderListWindow=false;
                };



                //禁用或者启用用户
                $scope.updateMemberStatus=function(intStatus){
                    var msg;
                    if(intStatus==0){
                        msg= "确定要启用该用户?";
                    }else{
                        msg= "确定要禁用该用户?";
                    }
                    $scope.showConfirm(msg,function(rs){
                        if(rs){
                            var data={};
                            data.strOperateType=intStatus;
                            data.strMemberId=$scope.strMemberid;
                            MemberCenter.changeMemberStatus($scope, $http,data);
                        }
                    })
                };
                //修改用户积分
                $scope.updateMemberIntegral=function(){

                    $scope.showMemberintegralInfoWindow=true;
                    $scope.integral.strIntegralNum=0;
                    $scope.integral.strDesc=""
                };

                //关闭用户积分修改弹窗
                $scope.closeMemberintegralInfoWindow=function(){
                    $scope.showMemberintegralInfoWindow=false;
                };

                //修改用户积分
                $scope.submitIntegralinfo=function(){

                    $scope.integral.strMemberId=$scope.strMemberid;

                    $http.post(remoteUrl.modMemberIntegral,$scope.integral).then(
                        function (result) {

                            var rs = result.data;
                            var code = rs.code;
                            var data = rs.data;


                            if (code == 1) {

                                $scope.showAlert("变更积分成功",function(){
                                    $scope.closeMemberintegralInfoWindow();
                                })


                            } else if (code == -1) {
                                window.location.href = "/admin/login?url="
                                + window.location.pathname
                                + window.location.search
                                + window.location.hash;
                                //未登录
                            } else if (code == 100013) {

                                $scope.showAlert(rs.msg);
                            } else if (code <= -2 && code >= -7) {
                                //必填字段未填写
                                $scope.showAlert(rs.msg);
                            } else if (code == -8) {
                                //暂无数据


                            }
                            $scope.isDisableSubmit=false;
                        }, function (result) {

                            var status = result.status;
                            if (status == -1) {
                                $scope.showAlert("服务器错误")
                            } else if (status >= 404 && status < 500) {
                                $scope.showAlert("请求路径错误")
                            } else if (status >= 500) {
                                $scope.showAlert("服务器错误")
                            }
                            $scope.isDisableSubmit=false;
                        });

                };


                //提交数据
                $scope.submitMemberSecondinfo=function(){

                    $scope.isDisableSubmit=true;

                    var url;
                    //新增会员
                    if( $scope.isAddNewMember==true){
                        url=remoteUrl.insertMember
                    }else{
                        $scope.member.strMemberid=$scope.strMemberid;
                        url=remoteUrl.updateMember;
                    }



                   // $scope.modelDataObject={};
                    for(var key in $scope.modelData){
                        $scope.member[key]=$scope.modelData[key];
                    }

                    $http.post(remoteUrl.insertMember,$scope.member).then(
                        function (result) {

                            var rs = result.data;
                            var code = rs.code;
                            var data = rs.data;


                            if (code == 1) {

                                $scope.showAlert("新增会员成功",function(){
                                    window.location.reload();
                                })


                            } else if (code == -1) {
                                window.location.href = "/admin/login?url="
                                + window.location.pathname
                                + window.location.search
                                + window.location.hash;
                                //未登录
                            } else if (code == 100007|| code == 100012) {

                                $scope.showAlert(rs.msg);
                            } else if (code <= -2 && code >= -7) {
                                //必填字段未填写
                                $scope.showAlert(rs.msg);
                            } else if (code == -8) {
                                //暂无数据


                            }
                            $scope.isDisableSubmit=false;
                        }, function (result) {

                            var status = result.status;
                            if (status == -1) {
                                $scope.showAlert("服务器错误")
                            } else if (status >= 404 && status < 500) {
                                $scope.showAlert("请求路径错误")
                            } else if (status >= 500) {
                                $scope.showAlert("服务器错误")
                            }
                            $scope.isDisableSubmit=false;
                        });


                };


                $scope.$on(
                    "$destroy",
                    function( event ) {
                        $interval.cancel( $scope.paySuccessInterval );

                    }
                );


            },
            //获取订单列表
            getOrderList:function($scope, $http){
                var data = {
                    'pagenum': $scope.orderCurrentPage,
                    "pagesize": $scope.orderPageSize,
                    "strMemberId":$scope.strMemberid,
                    "strPayType" :$scope.select.strPayType,
                    "strOrderId"  : $scope.select.strOrderId,
                    "strStartTime"  : $scope.select.strStartTime,
                    "strEndTime"  : $scope.select.strEndTime



                };

                $http.post(remoteUrl.listMemberGoodsOrder, data).then(
                    function (result) {

                        var rs = result.data;
                        var code = rs.code;
                        var data = rs.data;

                        //code=-8;
                        if (code == 1) {
                            //图片跟路径

                            $scope.isNoOrderData=false;
                            $scope.orderPageCount = data.iTotalPage;
                            $scope.orderList = data.orderList;

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
                            $scope.isNoOrderData=true;
                            $scope.orderPageCount = 0;
                            $scope.orderList = [];
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
            //查询支付结果
            getPayStatus:function(strOrderId,$http,$scope,$interval){

                var data={"strOrderId":strOrderId};
                $http.post(remoteUrl.checkIsPayed,data).then(
                    function (result) {

                        var rs = result.data;
                        var code = rs.code;

                        if (code == 1) {
                            $scope.cashRecharg.strPayType='ok';
                            $interval.cancel( $scope.paySuccessInterval );



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


                        }
                        $scope.isDisableSubmit=true;
                    }, function (result) {

                        var status = result.status;
                        if (status == -1) {
                            $scope.showAlert("服务器错误")
                        } else if (status >= 404 && status < 500) {
                            $scope.showAlert("请求路径错误")
                        } else if (status >= 500) {
                            $scope.showAlert("服务器错误")
                        }
                        $scope.isDisableSubmit=true;
                    });
            },
            //完成现金支付
            cashRecharg:function($scope, $http,$interval,$sce,data){

                $http.post(remoteUrl.cashMoneyRechargForMember,data).then(
                    function (result) {


                        var rs = result.data;
                        var code = rs.code;
                        var data = rs.data;


                        if (code == 1) {
                            $scope.hadPay="YES";
                            var cashRecharg =  $scope.cashRecharg.strPayType= data.strPayType;




                            if(cashRecharg=='0'){
                                //现金
                            }else if(cashRecharg=='1'){//微信
                                //支付订单号
                                var strOrderId=data.strOrderId;
                                $scope.qrCode=data.qrCode;
                                var getPayStatus = function () {

                                    MemberCenter.getPayStatus(strOrderId,$http,$scope,$interval);

                                };
                              $scope.paySuccessInterval =  $interval(getPayStatus, 3000);

                            }else if(cashRecharg=='2'){//支付宝
                                //支付订单号
                                var strOrderId=data.strOrderId;
                                var getPayStatus = function () {

                                    MemberCenter.getPayStatus(strOrderId,$http,$scope,$interval);

                                };
                                $scope.paySuccessInterval =  $interval(getPayStatus, 3000);

                                $scope.sHtmlText = $sce
                                    .trustAsHtml(data.sHtmlText);

                            }


                        } else if (code == -1) {
                            window.location.href = "/admin/login?url="
                            + window.location.pathname
                            + window.location.search
                            + window.location.hash;
                            //未登录
                        } else if (code == 100014) {
                            //微信生成与支付订单失败
                            $scope.showAlert(rs.msg);
                        }else if (code <= -2 && code >= -7) {
                            //必填字段未填写
                            $scope.showAlert(rs.msg);
                        } else if (code == -8) {
                            //暂无数据


                        }
                        $scope.isDisableSubmit=false;
                    }, function (result) {

                        var status = result.status;
                        if (status == -1) {
                            $scope.showAlert("服务器错误")
                        } else if (status >= 404 && status < 500) {
                            $scope.showAlert("请求路径错误")
                        } else if (status >= 500) {
                            $scope.showAlert("服务器错误")
                        }
                        $scope.isDisableSubmit=false;
                    });
            },
            //售后充值
            virtualRecharg:function($scope, $http,data){


                $http.post(remoteUrl.backgroundRechargForMember,data).then(
                    function (result) {

                        var rs = result.data;
                        var code = rs.code;
                        var data = rs.data;


                        if (code == 1) {
                            $scope.showAlert("操作成功",function(){
                                $scope.closeVirtualRechargWindow();
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
                            //暂无数据


                        }
                        $scope.isDisableSubmit=false;
                    }, function (result) {

                        var status = result.status;
                        if (status == -1) {
                            $scope.showAlert("服务器错误")
                        } else if (status >= 404 && status < 500) {
                            $scope.showAlert("请求路径错误")
                        } else if (status >= 500) {
                            $scope.showAlert("服务器错误")
                        }
                        $scope.isDisableSubmit=false;
                    });
            },
            //修改会员启用禁用状态
          changeMemberStatus:function($scope, $http,data){
                $http.post(remoteUrl.updateMemberStatus,data).then(
                    function (result) {

                        var rs = result.data;
                        var code = rs.code;
                        var data = rs.data;


                        if (code == 1) {
                            $scope.member.intStatus=$scope.intStatus=data;
                            $scope.showAlert("操作成功");
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
            //根据会员ID查询会员信息
            getMemberById:function($scope, $http){
                var data={'strMemberid':$scope.strMemberid};

                $http.post(remoteUrl.getMemberById,data).then(
                    function (result) {

                        var rs = result.data;
                        var code = rs.code;
                        var data = rs.data;


                        if (code == 1) {



                            $scope.member=data.memberEntity;

                            $scope.member2=data.memberdetailEntity;
                            $scope.expandData= data.memberexpandinformationVOList;
                            $scope.memberlevelsEntityList=  data.memberlevelsEntityList;
                            if($scope.memberlevelsEntityList.length>0){
                                $scope.member.strLevelsid=$scope.memberlevelsEntityList[0].strLevelsid;
                            }
                            //新增
                            if($scope.strMemberid==""){
                                $scope.member.intStatus=1;
                                $scope.member.intSex=0;
                            }else{
                                $scope.showDetailMemberInfoWindow=true;
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

            //查询会员列表
            getMemberList:function($scope, $http){
                var data = {
                    'pagenum': $scope.currentPage,
                    "pagesize": $scope.pageSize,
                    "strSearchkey":""
                };

                $http.post(remoteUrl.listMember, data).then(
                    function (result) {

                        var rs = result.data;
                        var code = rs.code;
                        var data = rs.data;

                        //code=-8;
                        if (code == 1) {
                            //图片跟路径

                            $scope.pageCount = data.iTotalPage;
                            $scope.memberList = data.memberList;

                            for (var i = 0; i < $scope.memberList.length; i++) {
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
            }




            ////////////////////会员管理部分结束/////////////////////////

        };

        return MemberCenter;

    });