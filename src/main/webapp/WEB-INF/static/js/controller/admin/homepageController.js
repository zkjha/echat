define(
    ['lib/remoteUrl'], function (remoteUrl) {
        'use strict'

        var HomePageContor = {
            // 展示前端个人资料
            frontinfoController: function ($scope, $http, $routeParams,
                                           $location, $sce) {
                HomePageContor.getFromeinfo($scope, $http, $sce, 1)

            },
            // 回显数据
            getFromeinfo: function ($scope, $http, $sce, type) {

                $http
                    .post(remoteUrl.getFrontinformation)
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
            // 编辑前端个人资料
            editFrontinfoController: function ($scope, $http, $routeParams,
                                               $location, $sce) {
                // 回显数据
                HomePageContor.getFromeinfo($scope, $http, $sce, 2);
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
                    $http
                        .post(remoteUrl.updateFrontinformation,
                        {
                            'strContent': $scope.strContent
                        })
                        .then(
                        function (result) {
                            $scope.editFrontinfoisActive = true;
                            var rs = result.data;
                            var code = rs.code;
                            if (code == 1) {
                                window.location.href = "#!/frontinfo";
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
            // 商家信息控制器
            merchantinfoController: function ($scope, $http, fileReader) {

                $scope.checkmerchantnameinfo = function () {
                    $scope.merchantnameIsError = $scope.merchantinfoForm.merchant_name.$error.required;

                };
                $scope.checkmerchantaddressinfo = function () {
                    $scope.merchantaddressIsError = $scope.merchantinfoForm.merchant_address.$error.required;
                };

                // 保存表单信息
                $scope.submitMerchantinfo = function () {
                    // 是否有提交错误
                    var hassubmiterror = !!$scope.merchantinfoForm.$error.required;
                    if (hassubmiterror) {
                        $scope.checkmerchantnameinfo();
                        $scope.checkmerchantaddressinfo();
                        return;
                    } else {

                        var submitdata = {
                            'strMerchantaddress': $scope.merchant_address,
                            'strMerchantlogo': $scope.strMerchantlogo,
                            'strMerchantname': $scope.merchant_name
                        };

                        $http
                            .post(remoteUrl.updateMerchantInfo, submitdata)
                            .then(
                            function (result) {

                                var rs = result.data;
                                var code = rs.code;
                                if (code == 1) {
                                    $scope
                                        .showAlert(
                                        '保存成功',
                                        function () {
                                            window.location
                                                .reload();
                                        });
                                } else if (code == -1) {
                                    window.location.href = "/admin/login?url="
                                    + window.location.pathname
                                    + window.location.search
                                    + window.location.hash;
                                    // 未登录
                                } else if (code <= -2
                                    && code >= -7) {
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

                };

                // 回显图片
                $scope.getFile = function () {
                    fileReader.readAsDataUrl($scope.file, $scope).then(
                        function (result) {
                            $scope.imageSrc = result;
                            $scope.uploadimage();
                        });
                };
                // 上传图片
                $scope.uploadimage = function () {
                    // 组装表单数据
                    var postData = {
                        file: $scope.myFile
                    };
                    var promise = postMultipart(remoteUrl.uploadImage, postData);
                    promise.then(function (result) {
                            var rs = result.data;
                            var code = rs.code;
                            if (code == 1) {
                                $scope.strMerchantlogo = rs.data.strImgpath;
                                ;
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

                        }, function (result) {
                            $scope.editFrontinfoisActive = true;
                            var status = result.status;
                            if (status == -1) {
                                $scope.showAlert("服务器错误")
                            } else if (status >= 404 && status < 500) {
                                $scope.showAlert("请求路径错误")
                            } else if (status >= 500) {
                                $scope.showAlert("服务器错误")
                            }
                        }
                    );

                    function postMultipart(url, data) {
                        var fd = new FormData();
                        angular.forEach(data, function (val, key) {
                            fd.append(key, val);
                        });
                        var args = {
                            method: 'POST',
                            url: url,
                            data: fd,
                            headers: {
                                'Content-Type': undefined
                            },
                            transformRequest: angular.identity
                        };
                        return $http(args);
                    }
                };

                // 初始化表单数据
                $http
                    .post(remoteUrl.getMerchantInfo)
                    .then(
                    function (result) {

                        var rs = result.data;
                        var code = rs.code;
                        var data = rs.data;

                        if (code == 1) {
                            // 图片跟路径
                            var strImgrootpath = data.strImgrootpath;
                            var merchantEntity = data.merchantEntity;
                            // 会员数量
                            $scope.intMembercount = merchantEntity.intMembercount;
                            // 过期时间
                            $scope.strExpirationtime = merchantEntity.strExpirationtime;
                            // 版本信息
                            $scope.strSystemversion = merchantEntity.strSystemversion;
                            // 商家名称
                            $scope.merchant_name = merchantEntity.strMerchantname;
                            // 商家地址
                            $scope.merchant_address = merchantEntity.strMerchantaddress;
                            // 图片
                            $scope.imageSrc = strImgrootpath
                            + merchantEntity.strMerchantlogo;
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

                // 升级版本
                $scope.updataversion = function () {
                    var registcode = $scope.registcode;
                    var data = {
                        'strActivationcode': registcode
                    };
                    $http
                        .post(remoteUrl.upgradeMerchantSystem, data)
                        .then(
                        function (result) {

                            var rs = result.data;
                            var code = rs.code;
                            var data = rs.data;
                            if (code == 1) {
                                // 图片跟路径
                                $scope.showAlert("升级成功",
                                    function () {
                                        window.location
                                            .reload();
                                    });

                            } else if (code == 100005) {
                                $scope.showAlert(rs.msg);
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
                                //暂无数据
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
            //职位控制器
            dutyController: function ($scope, $http) {
                $scope.currentPage = 1;
                $scope.pageSize = 5;
                $scope.isShowListMenu = [];
                HomePageContor.getDutyList($scope, $http);
                $scope.onPageChange = function () {
                    // ajax request to load data
                    $scope.dutyEntityList = {};
                    HomePageContor.getDutyList($scope, $http);

                };

                $scope.openCtrMenu = function ($index, type) {
                    for (var i = 0; i < $scope.dutyEntityList.length; i++) {
                        $scope.isShowListMenu[i] = false;
                    }
                    if (type == 'over') {
                        $scope.isShowListMenu[$index] = !$scope.isShowListMenu[$index];
                    }

                };
                //点击新增角色按钮
                $scope.newDuty = function () {
                    $scope.showDutyWindow = true;
                    $scope.isAddNewDuty = true;
                    $scope.strDutyid = "";
                    $scope.strDutyname = "";
                    $scope.AllSelectedPower = [];
                    $scope.AllSelectedPowerTitle = [];

                };
                //点击列表修改职务按钮
                $scope.updataDuty = function (strDutyid) {
                    $scope.AllSelectedPower = [];
                    $scope.AllSelectedPowerTitle = [];
                    $scope.strDutyid = strDutyid;
                    var data = {
                        'strDutyid': strDutyid
                    };

                    $http.post(remoteUrl.getDutyById, data).then(
                        function (result) {

                            var rs = result.data;
                            var code = rs.code;
                            var data = rs.data;
                            if (code == 1) {

                                $scope.strDutyname = data.dutyEntity.strDutyname;
                                $scope.AllSelectedPower = data.privilegeids;
                                $scope.showDutyWindow = true;
                                $scope.isAddNewDuty = false;


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
                                $scope.showAlert("暂无权限数据")
                            }

                        }, function (result) {
                            $scope.editFrontinfoisActive = true;
                            var status = result.status;
                            if (status == -1) {
                                $scope.showAlert("服务器错误")
                            } else if (status >= 404 && status < 500) {
                                $scope.showAlert("请求路径错误")
                            } else if (status >= 500) {
                                $scope.showAlert("服务器错误")
                            }
                        });
                };
                //全选权限
                $scope.toggleTopPrivilege = function (e, strPrivilegeid) {
                    var isChecked = e.target.checked;
                    var privilegeList = $scope.privilegeList;

                    for (var x = 0; x < privilegeList.length; x++) {
                        var topPrivilegeList = privilegeList[x].topPrivilegeList;
                        for (var i = 0; i < topPrivilegeList.length; i++) {
                            var topPrivilege = topPrivilegeList[i];

                            if (strPrivilegeid == topPrivilege.strPrivilegeid) {
                                topPrivilege.isChecked = isChecked;
                                var childrenPrivilegeList = topPrivilege.childrenPrivilegeList;
                                for (var j = 0; j < childrenPrivilegeList.length; j++) {
                                    childrenPrivilegeList[j].isChecked = isChecked;

                                }
                            }
                        }
                    }

                };

                //权限选择修改全选按钮效果
                $scope.togglePrivilege = function (e, strParentid, strPrivilegeid) {
                    var isChecked = e.target.checked;
                    var privilegeList = $scope.privilegeList;

                    for (var x = 0; x < privilegeList.length; x++) {
                        var topPrivilegeList = privilegeList[x].topPrivilegeList;
                        for (var i = 0; i < topPrivilegeList.length; i++) {
                            var topPrivilege = topPrivilegeList[i];

                            if (strParentid == topPrivilege.strPrivilegeid) {
                                var childrenPrivilegeList = topPrivilege.childrenPrivilegeList;
                                var isAllChecked = true;

                                for (var j = 0; j < childrenPrivilegeList.length; j++) {

                                    if (strPrivilegeid == childrenPrivilegeList[j].strPrivilegeid) {
                                        childrenPrivilegeList[j].isChecked = isChecked;
                                        break;
                                    }
                                }

                                for (var j = 0; j < childrenPrivilegeList.length; j++) {

                                    if (!childrenPrivilegeList[j].isChecked) {

                                        isAllChecked = false;
                                    }

                                }


                                topPrivilege.isChecked = isAllChecked;
                            }
                        }
                    }

                };


                //确认选择的权限
                $scope.confirmSelectPower = function () {
                    $scope.AllSelectedPower = [];
                    $scope.AllSelectedPowerTitle = [];
                    var privilegeList = $scope.privilegeList;
                    for (var x = 0; x < privilegeList.length; x++) {
                        var topPrivilegeList = privilegeList[x].topPrivilegeList;
                        for (var i = 0; i < topPrivilegeList.length; i++) {
                            var topPrivilege = topPrivilegeList[i];

                            var childrenPrivilegeList = topPrivilege.childrenPrivilegeList;

                            if (topPrivilege.isChecked) {
                                $scope.AllSelectedPowerTitle.push(topPrivilege.strPrivilegeid);
                            }

                            for (var j = 0; j < childrenPrivilegeList.length; j++) {
                                var childrenPrivilege = childrenPrivilegeList[j]
                                if (childrenPrivilege.isChecked) {
                                    var strPrivilegeid = childrenPrivilege.strPrivilegeid;
                                    $scope.AllSelectedPower.push(strPrivilegeid)
                                }

                            }

                        }
                    }
                    $scope.clostPowerWindow()

                };
                //关闭新增修改角色弹窗
                $scope.clostDutyWindow = function () {
                    $scope.showDutyWindow = false;
                };
                //显示设置权限弹窗
                $scope.showPowerWindowClick = function () {

                    var data = {
                        'strDutyid': $scope.strDutyid
                    };

                    $http.post(remoteUrl.listPrivilege, data).then(
                        function (result) {

                            var rs = result.data;
                            var code = rs.code;
                            var data = rs.data;

                            if (code == 1) {
                                $scope.privilegeList = data.privilegeList;

                                var privilegeList = $scope.privilegeList = data.privilegeList;
                                for (var x = 0; x < privilegeList.length; x++) {
                                    var topPrivilegeList = privilegeList[x].topPrivilegeList;
                                    for (var i = 0; i < topPrivilegeList.length; i++) {
                                        var topPrivilege = topPrivilegeList[i];
                                        var childrenPrivilegeList = topPrivilege.childrenPrivilegeList;
                                        //var strPrivilegeid = ;

                                        if ($scope.AllSelectedPowerTitle.indexOf(topPrivilege.strPrivilegeid) != -1) {
                                            topPrivilege.isChecked = true;
                                        }


                                        for (var j = 0; j < childrenPrivilegeList.length; j++) {
                                            var childrenPrivilege = childrenPrivilegeList[j];

                                            var strPrivilegeid = childrenPrivilege.strPrivilegeid;

                                            if ($scope.AllSelectedPower.indexOf(strPrivilegeid) != -1) {
                                                childrenPrivilege.isChecked = true;
                                            }


                                        }

                                    }
                                }
                                var allSelectedPower = $scope.AllSelectedPower;


                                $scope.showPowerWindow = true;


                                $scope.showPowerWindow = true;

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
                                $scope.showAlert("暂无权限数据")
                            }

                        }, function (result) {
                            $scope.editFrontinfoisActive = true;
                            var status = result.status;
                            if (status == -1) {
                                $scope.showAlert("服务器错误")
                            } else if (status >= 404 && status < 500) {
                                $scope.showAlert("请求路径错误")
                            } else if (status >= 500) {
                                $scope.showAlert("服务器错误")
                            }
                        });
                };
                $scope.clostPowerWindow = function () {
                    $scope.showPowerWindow = false;
                };

                //提交保存职务信息
                $scope.sumbitDuty = function () {
                    //职务名称
                    var strDutyname = $scope.strDutyname;
                    if (!strDutyname) {
                        $scope.dutynameIsError = true;
                        return;
                    } else {
                        $scope.dutynameIsError = false;
                    }

                    var selectedPowerlength = $scope.AllSelectedPower.length;
                    if (selectedPowerlength <= 0) {
                        $scope.powerIsError = true;
                        return;
                    } else {
                        $scope.powerIsError = false;
                    }

                    var privilegeids = $scope.AllSelectedPower.join(",");

                    var strDutyid = $scope.strDutyid

                    var data = {
                        'strDutyid': strDutyid,
                        'privilegeids': privilegeids,
                        'strDutyname': $scope.strDutyname
                    };
                    var url = "";
                    if (!strDutyid) {
                        url = remoteUrl.insertDuty
                    } else {
                        //修改职务
                        url = remoteUrl.updateDuty
                    }

                    $http.post(url, data).then(
                        function (result) {

                            var rs = result.data;
                            var code = rs.code;
                            var data = rs.data;

                            if (code == 1) {
                                $scope.clostDutyWindow();
                                window.location.reload();

                            } else if (code == -1) {
                                window.location.href = "/admin/login?url="
                                + window.location.pathname
                                + window.location.search
                                + window.location.hash;
                                //未登录
                            } else if (code == 100006) {
                                $scope.showAlert(rs.msg);

                            } else if (code <= -2 && code >= -7) {
                                //必填字段未填写
                                $scope.showAlert(rs.msg);
                            } else if (code == -8) {
                                //暂无数据
                                $scope.showAlert("暂无权限数据")
                            }

                        }, function (result) {
                            $scope.editFrontinfoisActive = true;
                            var status = result.status;
                            if (status == -1) {
                                $scope.showAlert("服务器错误")
                            } else if (status >= 404 && status < 500) {
                                $scope.showAlert("请求路径错误")
                            } else if (status >= 500) {
                                $scope.showAlert("服务器错误")
                            }
                        });


                };
                // set pagecount in $scope

            },
            getDutyList: function ($scope, $http) {

                var data = {
                    'pagenum': $scope.currentPage,
                    "pagesize": $scope.pageSize
                };

                $http.post(remoteUrl.listDuty, data).then(
                    function (result) {

                        var rs = result.data;
                        var code = rs.code;
                        var data = rs.data;
                        //code=-8;
                        if (code == 1) {
                            //图片跟路径

                            $scope.pageCount = data.iTotalPage;
                            $scope.dutyEntityList = data.dutyEntityList;

                            for (var i = 0; i < $scope.dutyEntityList.length; i++) {
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
            //员工列表控制器
            staffinfoController: function ($scope, $http,fileReader) {

                $scope.imageSrc="http://";
                $scope.employee = {};
                //初始化弹窗中 职务select数据
                HomePageContor.initDutySelectData($scope, $http)
                //初始化弹窗中 用户状态select 数据
                $scope.employeeStatus = [{id: 1, value: "激活"}, {id: 0, value: "禁用"}];

                $scope.isShowListMenu = [];
                $scope.currentPage = 1;
                $scope.pageSize = 5;
                HomePageContor.getStaffinfoList($scope, $http);
                //分页按钮监听
                $scope.onPageChange = function () {
                    // ajax request to load data
                    $scope.employeeEntityList = {};
                    HomePageContor.getStaffinfoList($scope, $http);

                };

                //搜索员工信息
                $scope.searchStaffinfo=function(){
                    $scope.currentPage = 1;
                    HomePageContor.getStaffinfoList($scope, $http);
                };
                //重置用户密码
                $scope.updataStaffPasswd=function(strEmployeeid){

                    $scope.showConfirm("确认重置密码为123456？",function(rs){
                        if(rs){

                            //重置用户密码为123456
                            HomePageContor.resetEmployeePassword($scope,$http,strEmployeeid);

                        }
                    })
                };
                //提交新增用户数据
                $scope.sumbitemployee = function () {
                    //表单数据不合法
                    if ($scope.addEmployeeForm.$invalid) {

                        return;
                    } else {

                        var data=$scope.employee;

                        //开始提交表单数据
                        HomePageContor.saveEmployee($scope,$http,data);


                    }

                };


                //列表显示
                $scope.openCtrMenu = function ($index, type) {
                    for (var i = 0; i < $scope.employeeEntityList.length; i++) {
                        $scope.isShowListMenu[i] = false;
                    }
                    if (type == 'over') {
                        $scope.isShowListMenu[$index] = !$scope.isShowListMenu[$index];
                    }

                };


                //关闭弹窗
                $scope.clostStaffWindow = function () {
                    $scope.showStaffWindow = false;

                };
                //新建员工按钮事件 打开弹窗
                $scope.newStaff = function () {
                    $scope.showStaffWindow = true;
                    $scope.isAddNewStaff = true;
                    $scope.strStaffid = "";
                    $scope.employee={};

                    if ($scope.dutyEntityList.length > 0) {
                        $scope.employee.strDutyid = $scope.dutyEntityList[0].strDutyid;
                    }
                    //默认选中用户状态为激活
                    $scope.employee.intStatus = 1;
                };
                //修改员工信心 打开弹窗
                $scope.updataStaff = function (strEmployeeid) {

                    $scope.strStaffid = strEmployeeid;
                    var data = {
                        'strEmployeeid': strEmployeeid
                    };

                    $http.post(remoteUrl.getEmployeeById, data).then(
                        function (result) {

                            var rs = result.data;
                            var code = rs.code;
                            var data = rs.data;
                            if (code == 1) {

                                console.log(data)
                                $scope.showStaffWindow = true;
                                $scope.isAddNewStaff = false;
                               var employeeEntity = data.employeeEntity;
                                employeeEntity.strMobile=parseInt(employeeEntity.strMobile);
                                $scope.employee =employeeEntity ;
                                $scope.imageSrc = data.strImgrootpath+employeeEntity.strHeadportrait


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
                                $scope.showAlert("暂无权限数据")
                            }

                        }, function (result) {
                            $scope.editFrontinfoisActive = true;
                            var status = result.status;
                            if (status == -1) {
                                $scope.showAlert("服务器错误")
                            } else if (status >= 404 && status < 500) {
                                $scope.showAlert("请求路径错误")
                            } else if (status >= 500) {
                                $scope.showAlert("服务器错误")
                            }
                        });
                };

                // 回显图片
                $scope.getFile = function () {
                    fileReader.readAsDataUrl($scope.file, $scope).then(
                        function (result) {
                            $scope.imageSrc = result;
                            $scope.uploadimage();
                        });
                };
                // 上传图片
                $scope.uploadimage = function () {
                    // 组装表单数据
                    var postData = {
                        file: $scope.myFile
                    };
                    var promise = postMultipart(remoteUrl.uploadImage, postData);
                    promise.then(function (result) {
                            var rs = result.data;
                            var code = rs.code;
                            if (code == 1) {
                                $scope.employee.strHeadportrait = rs.data.strImgpath;

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

                        }, function (result) {
                            $scope.editFrontinfoisActive = true;
                            var status = result.status;
                            if (status == -1) {
                                $scope.showAlert("服务器错误")
                            } else if (status >= 404 && status < 500) {
                                $scope.showAlert("请求路径错误")
                            } else if (status >= 500) {
                                $scope.showAlert("服务器错误")
                            }
                        }
                    );

                    function postMultipart(url, data) {
                        var fd = new FormData();
                        angular.forEach(data, function (val, key) {
                            fd.append(key, val);
                        });
                        var args = {
                            method: 'POST',
                            url: url,
                            data: fd,
                            headers: {
                                'Content-Type': undefined
                            },
                            transformRequest: angular.identity
                        };
                        return $http(args);
                    }
                };


            }


            //重置用户密码为123456
            ,resetEmployeePassword:function($scope,$http,strEmployeeid){
                var data={"strEmployeeid":strEmployeeid};
                $http.post(remoteUrl.resetEmployeePassword,data).then(
                    function (result) {

                        var rs = result.data;
                        var code = rs.code;
                        var data = rs.data;
                        if (code == 1) {

                            $scope.showAlert("密码重置成功");

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
                            $scope.showAlert("暂无权限数据")
                        }

                    }, function (result) {
                        $scope.editFrontinfoisActive = true;
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
            //保存员工信息
            ,saveEmployee:function($scope,$http,data,$location){
                var url="";
                if($scope.isAddNewStaff==true){
                    //新建
                    url=remoteUrl.insertEmployee
                }else{
                    data.strEmployeeid=$scope.strStaffid;
                    url=remoteUrl.updateEmployee
                }
                $http.post(url,data).then(
                    function (result) {

                        var rs = result.data;
                        var code = rs.code;
                        var data = rs.data;
                        if (code == 1) {

                            $scope.showAlert(rs.msg,function(rs){
                                if(rs){
                                    window.location.reload()
                                }
                            });



                        } else if (code == 100006) {

                            $scope.showAlert(rs.msg);

                        } else if (code == 100007) {

                            $scope.showAlert(rs.msg);

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
                            $scope.showAlert("暂无权限数据")
                        }

                    }, function (result) {
                        $scope.editFrontinfoisActive = true;
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

            //初始化职务类型select数据
            , initDutySelectData: function ($scope, $http) {

                $http.post(remoteUrl.listAllDuty).then(
                    function (result) {

                        var rs = result.data;
                        var code = rs.code;
                        var data = rs.data;
                        if (code == 1) {

                            $scope.dutyEntityList = data.dutyEntityList;




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
                            $scope.showAlert("暂无权限数据")
                        }

                    }, function (result) {
                        $scope.editFrontinfoisActive = true;
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
            //获取员工列表
            , getStaffinfoList: function ($scope, $http) {

                var data = {
                    'strSearchkey':$scope.strSearchkey,
                    'pagenum': $scope.currentPage,
                    "pagesize": $scope.pageSize
                };

                $http.post(remoteUrl.listEmployee, data).then(
                    function (result) {

                        var rs = result.data;
                        var code = rs.code;
                        var data = rs.data;
                        //code=-8;
                        if (code == 1) {
                            //图片跟路径

                            $scope.pageCount = data.iTotalPage;
                            $scope.employeeEntityList = data.employeeEntityList;

                            for (var i = 0; i < $scope.employeeEntityList.length; i++) {
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
                            $scope.employeeEntityList=[];
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


        };
        return HomePageContor;
    });