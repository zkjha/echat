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
                $scope.inputTypes=[{"id":2,value:"多选"},{"id":1,value:"下拉选择"},{"id":0,value:"输入框"}];
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

            memberSetController:function($scope, $http){

                $scope.isShowListMenu = [];
                $scope.currentPage = 1;
                $scope.pageSize = 5;

                MemberCenter.getMemberList($scope, $http);
                $scope.onPageChange = function () {
                    // ajax request to load data

                    MemberCenter.getExpandInfoList($scope, $http);

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

                    $scope.showEditMemberInfoWindow=true;
                    $scope.isAddNewMember=true;
                };
                $scope.closeEditMemberInfoWindow=function(){
                    $scope.showEditMemberInfoWindow=false;
                }

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