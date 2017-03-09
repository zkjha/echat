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

            //会员章程展示控制器
            regulationsController:function($scope, $http, $sce){
                MemberCenter.getRegulationsinfo($scope, $http, $sce, 1)
            },
            //修改会员章程控制器
            editRegulationsController:function($scope, $http, $sce){

                // 回显数据
                MemberCenter.getRegulationsinfo($scope, $http, $sce, 2)
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

            }
        };

        return MemberCenter;

    });