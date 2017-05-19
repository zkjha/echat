/**
 * Created by liupengyan on 17/3/9.
 */
/**
 * Created by liupengyan on 17/3/9.
 * 处理活动控制器
 */
define(
	['lib/remoteUrl'],
	function(remoteUrl) {
		'use strict'

		var activityCtrl = {
            ////////////////////积分规则-开始/////////////////////////
            integralrules: function ($scope,$http) {
                //新增是否禁用
                $scope.ifstrEnabled=[{"id":"1","name":"启用"},{"id":"0","name":"禁用"}];
                //查询
                activityCtrl.findAllSignIntegrationRules($scope,$http);
                activityCtrl.findAllSignIntegrationRule($scope,$http);
                activityCtrl.findAllIntegrationCashRule($scope,$http);
                // 连续非连续删除
                $scope.z_delete = function(strSignId){
                    if($scope.feilianxulen == 1){
                        $scope.showAlert("最后一条，不能删除")
                    }else{
                        $scope.showConfirm("确认要删除" + "？", function(rs) {
                            if(rs) {
                                console.info(rs)
                                activityCtrl.deleteSignIntegrationRule($scope,$http,strSignId);
                            }
                        })
                    };

                }
                //非连续签到新增
                $scope.submitExpandinfo = function(insertSignIntegrationRules){
                    activityCtrl.insertSignIntegrationRules($scope,$http,insertSignIntegrationRules);
                }
                //积分抵现删除
                $scope.z_jfdelete = function(strId){
                    $scope.showConfirm("确认要删除" + "？", function(rs) {
                        //调用删除删除图片轮播函数
                        if(rs) {
                            console.info(rs)
                            if($scope.zhuangtai.length < 4){
                                let  data = "请保留一条数据"
                                $scope.showAlert(data);
                            }
                            activityCtrl.deleteIntegrationCashRule($scope,$http,strId);
                        }

                    })
                }
                //新增
                $scope.newExpandinginfo=function(){

                    $scope.showExpandInfoWindow=true;
                    $scope.isAddNewExpand=true;
                    //判断执行添加还是修改
                    //$scope.typePanduan = false;
                };
                //关闭窗口
                $scope.clostExpandWindow=function(){
                    $scope.showExpandInfoWindow=false;
                };

                //点击保存按钮
                $scope.zhuangtai = [];
                //var lianxu = $scope.listSignIntegrationRuleEntity;
                //var blianxu = $scope.listSignIntegrationRuleEntity;

                $scope.baocun = function(){
                    //启用禁用选择框遍历
                    let z_jys_ifs = [];
                    let z_jys_if = angular.element(".z_jys_if");
                    for(let j=0;j<$scope.zhuangtai.length;j++){
                        z_jys_ifs[j] = z_jys_if[j].value;
                    }
                   // 取到非连续签到天数,积分，是否启用，id'
                    let z_day_if_flxs = [];
                    let z_jf_if_flxs = [];
                    let z_id_if_flxs = [];
                    let z_day_if_flx = angular.element(".z_day_if_flx");
                    let z_jf_if_flx = angular.element(".z_jf_if_flx");
                    let z_id_if_flx = angular.element(".z_id_if_flx");

                    //非连续选择遍历
                    for(let i=0;i< $scope.feilianxulen;i++){
                         z_day_if_flxs[i] =  z_day_if_flx[i].value;
                         z_jf_if_flxs[i] = z_jf_if_flx[i].value;
                         z_id_if_flxs[i] = z_id_if_flx[i].value;
                    }
                    let flx = z_jys_ifs.slice($scope.lianxu,$scope.feilianxulen+$scope.lianxu)
                    let updateSignIntegrationRule ={};
                    updateSignIntegrationRule.iIntegration = z_jf_if_flxs.join(",");
                    updateSignIntegrationRule.strEnabled = flx.join(",");
                    updateSignIntegrationRule.strId = z_id_if_flxs.join(",");
                    updateSignIntegrationRule.strSignDays = z_day_if_flxs.join(",");
                    //调用更改接口-非连续
                    activityCtrl.updateSignIntegrationRule($scope,$http,updateSignIntegrationRule);


                    //连续

                    let z_id_if_lxs = [];
                    let z_id_if_lx = angular.element(".z_id_if_lx");
                    let z_jf_if_lxs = [];
                    let z_jf_if_lx = angular.element(".z_jf_if_lx");
                    let z_day_if_lxs = [];
                    let z_day_if_lx = angular.element(".z_day_if_lx");
                    for(let i=0;i< z_jf_if_lx.length;i++){
                        z_jf_if_lxs[i] =  z_jf_if_lx[i].value;
                        z_day_if_lxs[i] = z_day_if_lx[i].value;
                        z_id_if_lxs[i] =  z_id_if_lx[i].value;
                    }
                    let lx = z_jys_ifs.slice(0,$scope.lianxu)
                    let updateSignIntegrationRules = {}
                    updateSignIntegrationRules.iIntegration = z_jf_if_lxs.join(",");
                    updateSignIntegrationRules.strEnabled = lx.join(",");
                    updateSignIntegrationRules.strId = z_id_if_lxs.join(",");
                    updateSignIntegrationRules.strSignDays = z_day_if_lxs.join(",");
                    //调用更改接口-非连续
                    activityCtrl.updateSignIntegrationRules($scope,$http,updateSignIntegrationRules);


                    //积分抵现

                    let z_id_if_dxs = [];
                    let z_id_if_dx = angular.element(".z_id_if_dx");
                    let z_jf_if_dxs = [];
                    let z_jf_if_dx = angular.element(".z_jf_if_dx");
                    let z_doy_if_dxs = [];
                    let z_doy_if_dx = angular.element(".z_doy_if_dx");
                    for(let i=0;i< z_jf_if_lx.length;i++){
                        z_id_if_dxs[i] =  z_id_if_dx[i].value;
                        z_jf_if_dxs[i] = z_jf_if_dx[i].value;
                        z_doy_if_dxs[i] =  z_doy_if_dx[i].value;
                    }
                    let dx = z_jys_ifs.slice($scope.feilianxulen + $scope.lianxu,$scope.zhuangtai.length)
                    let updateIntegrationCashRule = {}
                    updateIntegrationCashRule.iIntegration = z_jf_if_dxs.join(",");
                    updateIntegrationCashRule.strEnabled = dx.join(",");
                    updateIntegrationCashRule.strId = z_id_if_dxs.join(",");
                    updateIntegrationCashRule.dCash = z_doy_if_dxs.join(",");
                    //调用更改接口-非连续
                    console.info(updateIntegrationCashRule)
                    activityCtrl.updateIntegrationCashRule($scope,$http,updateIntegrationCashRule);


                    $scope.showAlert("保存成功")
                }

            },
            ///积分规则 -- 连续签到 -- 更改
            updateIntegrationCashRule:function($scope,$http,updateIntegrationCashRule){
                console.info(remoteUrl.updateSignIntegrationRules)
                var data=updateIntegrationCashRule;
                $http.post(remoteUrl.updateIntegrationCashRule,data).then(
                    function (result) {
                        var rs = result.data;
                        var code = rs.code;
                        var data = rs.data;
                        //code=-8;
                        if (code == 1) {
                            //$scope.showAlert(rs.msg,function(){
                            //    window.location.reload();
                            //});
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
            ///积分规则 -- 连续签到 -- 更改
            updateSignIntegrationRules:function($scope,$http,updateSignIntegrationRules){
                console.info(remoteUrl.updateSignIntegrationRules)
                var data=updateSignIntegrationRules;
                console.info(data.strSignDays)
                $http.post(remoteUrl.updateSignIntegrationRules,data).then(
                    function (result) {
                        var rs = result.data;
                        var code = rs.code;
                        var data = rs.data;
                        //code=-8;
                        if (code == 1) {
                            //$scope.showAlert(rs.msg,function(){
                            //    window.location.reload();
                            //});
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
            ///积分规则 -- 非连续签到 -- 更改
            updateSignIntegrationRule:function($scope,$http,updateSignIntegrationRule){
                console.info(remoteUrl.updateSignIntegrationRule)
                var data=updateSignIntegrationRule;
                console.info(data.strSignDays)
                $http.post(remoteUrl.updateSignIntegrationRule,data).then(
                    function (result) {
                        var rs = result.data;
                        var code = rs.code;
                        var data = rs.data;
                        //code=-8;
                        if (code == 1) {
                            //$scope.showAlert(rs.msg,function(){
                            //    window.location.reload();
                            //});
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
            ///积分规则 -- 非连续签到 -- 新增
            insertSignIntegrationRules:function($scope,$http,insertSignIntegrationRules){
                console.info(remoteUrl.insertSignIntegrationRules)
                var data={
                    iIntegration:$scope.insertSignIntegrationRules.iIntegration,
                    strEnabled:$scope.insertSignIntegrationRules.ifstrEnabled,
                    strSignDays:$scope.insertSignIntegrationRules.strSignDays
                };
                $http.post(remoteUrl.insertSignIntegrationRules,data).then(
                    function (result) {

                        var rs = result.data;
                        var code = rs.code;
                        var data = rs.data;
                        //code=-8;
                        if (code == 1) {
                            $scope.showAlert(rs.msg,function(){
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
            ///积分规则 -- 积分抵现 -- 删除
            deleteIntegrationCashRule:function($scope,$http,strId){
                console.info(remoteUrl.deleteIntegrationCashRule)
                var data={strId:strId};
                $http.post(remoteUrl.deleteIntegrationCashRule,data).then(
                    function (result) {

                        var rs = result.data;
                        var code = rs.code;
                        var data = rs.data;
                        //code=-8;
                        if (code == 1) {
                            //$scope.showAlert(rs.msg);
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
            ///积分规则 -- 连续非连续 -- 删除
            deleteSignIntegrationRule:function($scope,$http,strSignId){
                console.info(remoteUrl.deleteSignIntegrationRule)
                var data={strSignId:strSignId};
                $http.post(remoteUrl.deleteSignIntegrationRule,data).then(
                    function (result) {

                        var rs = result.data;
                        var code = rs.code;
                        var data = rs.data;
                        //code=-8;
                        if (code == 1) {
                            //$scope.showAlert(rs.msg);
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
            ///积分规则 -- 积分抵现规则 -- 查询
            findAllIntegrationCashRule:function($scope,$http){
                console.info(remoteUrl.findAllIntegrationCashRule)
                var data={};
                $http.post(remoteUrl.findAllIntegrationCashRule,data).then(
                    function (result) {

                        var rs = result.data;
                        var code = rs.code;
                        var data = rs.data;
                        //code=-8;
                        if (code == 1) {
                            console.info(data)
                            $scope.listSignIntegrationRuleEntityT = data.listIntegrationCashRuleEntity;
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
            ///积分规则 -- 非连续签到积分规则 -- 查询
            //findAllSignIntegrationRule:"/admin/biz/RuleSetting/findAllSignIntegrationRule",
            findAllSignIntegrationRules:function($scope,$http){
                console.info(remoteUrl.findAllSignIntegrationRules)
                var data={};
                $http.post(remoteUrl.findAllSignIntegrationRules,data).then(
                    function (result) {

                        var rs = result.data;
                        var code = rs.code;
                        var data = rs.data;
                        //code=-8;
                        if (code == 1) {
                           console.info(data)
                            $scope.listSignIntegrationRuleEntity = data.listSignIntegrationRuleEntity;
                            $scope.feilianxulen = $scope.listSignIntegrationRuleEntity.length
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
            //findAllSignIntegrationRule
            ///积分规则 -- 连续签到积分规则 -- 查询
            findAllSignIntegrationRule:function($scope,$http){
                console.info(remoteUrl.findAllSignIntegrationRule)
                var data={};
                $http.post(remoteUrl.findAllSignIntegrationRule,data).then(
                    function (result) {

                        var rs = result.data;
                        var code = rs.code;
                        var data = rs.data;
                        //code=-8;
                        if (code == 1) {
                            $scope.listSignIntegrationRuleEntitys = data.listSignIntegrationRuleEntity;
                            $scope.lianxu = $scope.listSignIntegrationRuleEntitys.length
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
            ////////////////////积分规则-结束/////////////////////////

			////////////////////广告轮播-图片开始/////////////////////////
			uploadImages: function($scope, $http, fileReader) {
				$scope.LoopAdvPic = {};
				//删除广告轮播-单条查询
				$scope.delectExpand = function(strAdvPicId, strAdvPicName) {

					$scope.showConfirm("确认要删除" + strAdvPicName + "？", function(rs) {
						//调用删除删除图片轮播函数
						if(rs) {
							console.info(rs)
							$scope.delLoopAdvPic(strAdvPicId, $scope, $http);
						}

					})
				}
				//保存
				$scope.submitExpandinfo = function() {
					console.info($scope.LoopAdvPic)
					if($scope.typePanduan) {
						//调用修改接口
						$scope.updateLoopAdvPic($scope.LoopAdvPic, $scope, $http);
					} else {
						//调用保存接口
						$scope.insertLoopAdvPic($scope.LoopAdvPic, $scope, $http);
					}
				};
				//关闭窗口
				$scope.clostExpandWindow = function() {
					$scope.showExpandInfoWindow = false;
					$scope.goodsTypeList = {};
					$scope.getListLoopAdvPic($scope, $http);
				};
				//新增
				$scope.newExpandinginfo = function() {

					$scope.showExpandInfoWindow = true;
					$scope.isAddNewExpand = true;
					//判断执行添加还是修改
					$scope.typePanduan = false;
				};
				//修改广告轮播图片
				$scope.updataExpand = function(strAdvPicId) {

					$scope.showExpandInfoWindow = true;
					$scope.isAddNewExpand = false;
					//判断执行添加还是修改
					$scope.typePanduan = true;
					//调用接口
					$scope.getLoopAdvPic(strAdvPicId, $scope, $http);
				};

				//删除广告轮播单条数据
				$scope.delLoopAdvPic = function(strAdvPicId, $scope, $http) {

					var data = {
						'strAdvPicId': strAdvPicId
					};
					$http.post(remoteUrl.delLoopAdvPic, data).then(
						function(result) {
							console.info(strAdvPicId);
							var rs = result.data;
							var code = rs.code;
							var data = rs.data;
							console.info(result)
							if(code == 1) {

								window.location.reload();

							} else if(code == -1) {
								window.location.href = "/admin/login?url=" +
									window.location.pathname +
									window.location.search +
									window.location.hash;
								//未登录
							} else if(code <= -2 && code >= -7) {
								//必填字段未填写
								$scope.showAlert(rs.msg);
							} else if(code == -8) {
								$scope.showAlert(rs.msg);
							}

						},
						function(result) {

							var status = result.status;
							if(status == -1) {
								$scope.showAlert("服务器错误")
							} else if(status >= 404 && status < 500) {
								$scope.showAlert("请求路径错误")
							} else if(status >= 500) {
								$scope.showAlert("服务器错误")
							}
						});
				};
				//广告轮播修改
				$scope.updateLoopAdvPic = function(data, $scope, $http) {
					$http.post(remoteUrl.updateLoopAdvPic, data).then(
						function(result) {

							var rs = result.data;
							var code = rs.code;
							var data = rs.data;

							if(code == 1) {

								$scope.showAlert("保存成功", function() {
									window.location.reload();
								});

							} else if(code == -1) {
								window.location.href = "/admin/login?url=" +
									window.location.pathname +
									window.location.search +
									window.location.hash;
								//未登录
							} else if(code <= -2 && code >= -7) {
								//必填字段未填写
								$scope.showAlert(rs.msg);
							} else if(code == -8) {

							}

						},
						function(result) {

							var status = result.status;
							if(status == -1) {
								$scope.showAlert("服务器错误")
							} else if(status >= 404 && status < 500) {
								$scope.showAlert("请求路径错误")
							} else if(status >= 500) {
								$scope.showAlert("服务器错误")
							}
						});
				};

				//广告轮播－查询单条
				$scope.getLoopAdvPic = function(strAdvPicId, $scope, $http) {

					var data = { "strAdvPicId": strAdvPicId }
					$http.post(remoteUrl.getLoopAdvPic, data).then(
						function(result) {

							var rs = result.data;
							var code = rs.code;
							var data = rs.data;
							var LoopAdvPic = data.LoopAdvPic;
							console.info(data)
							if(code == 1) {
								angular.forEach(LoopAdvPic, function(val, key) {
									$scope.LoopAdvPic[key] = val;
								})

							} else if(code == -1) {
								window.location.href = "/admin/login?url=" +
									window.location.pathname +
									window.location.search +
									window.location.hash;
								//未登录
							} else if(code <= -2 && code >= -7) {
								//必填字段未填写
								$scope.showAlert(rs.msg);
							} else if(code == -8) {

							}

						},
						function(result) {

							var status = result.status;
							if(status == -1) {
								$scope.showAlert("服务器错误")
							} else if(status >= 404 && status < 500) {
								$scope.showAlert("请求路径错误")
							} else if(status >= 500) {
								$scope.showAlert("服务器错误")
							}
						});
				};
				//广告轮播-新增图片
				$scope.insertLoopAdvPic = function(data, $scope, $http) {
					$http.post(remoteUrl.insertLoopAdvPic, data).then(
						function(result) {

							var rs = result.data;
							var code = rs.code;
							var data = rs.data;

							if(code == 1) {

								$scope.showAlert("保存成功", function() {
									window.location.reload();
								});

							} else if(code == -1) {
								window.location.href = "/admin/login?url=" +
									window.location.pathname +
									window.location.search +
									window.location.hash;
								//未登录
							} else if(code <= -2 && code >= -7) {
								//必填字段未填写
								$scope.showAlert(rs.msg);
							} else if(code == -8) {

							}

						},
						function(result) {

							var status = result.status;
							if(status == -1) {
								$scope.showAlert("服务器错误")
							} else if(status >= 404 && status < 500) {
								$scope.showAlert("请求路径错误")
							} else if(status >= 500) {
								$scope.showAlert("服务器错误")
							}
						});
				};
				// 回显图片
				$scope.getFile = function() {
					fileReader.readAsDataUrl($scope.file, $scope).then(
						function(result) {
							$scope.imageSrc = result;
							$scope.uploadimage();
						});
				};
				// 上传图片
				$scope.uploadimage = function() {
					// 组装表单数据
					var postData = {
						file: $scope.myFile
					};
					console.info($scope.myFile);
					var promise = postMultipart(remoteUrl.uploadImage, postData);
					console.info(promise)
					promise.then(function(result) {
						var rs = result.data;
						var code = rs.code;
						console.info(result)
						if(code == 1) {
							$scope.newExpandinginfo();
							$scope.strAdvPicName = rs.data.strImgpath;
						} else if(code == -1) {
							window.location.href = "/admin/login?url=" +
								window.location.pathname +
								window.location.search +
								window.location.hash;
							// 未登录
						} else if(code <= -2 && code >= -7) {
							// 必填字段未填写
							$scope.showAlert(rs.msg);
						} else if(code == 100004) {
							$scope.showAlert(rs.msg);
						}

					}, function(result) {
						$scope.editFrontinfoisActive = true;
						var status = result.status;
						if(status == -1) {
							$scope.showAlert("服务器错误")
						} else if(status >= 404 && status < 500) {
							$scope.showAlert("请求路径错误")
						} else if(status >= 500) {
							$scope.showAlert("服务器错误")
						}
					});

					function postMultipart(url, data) {
						var fd = new FormData();
						angular.forEach(data, function(val, key) {
							fd.append(key, val);
						});
						console.info(fd)
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

				//广告轮播查询所有
				$scope.getListLoopAdvPic = function($scope, $http) {
					$http.post(remoteUrl.getListLoopAdvPic).then(
						function(result) {
							var rs = result.data;
							var code = rs.code;
							var data = rs.data;
							console.info(data)
							//code=-8;
							if(code == 1) {
								$scope.listLoopAdvPic = data.listLoopAdvPic
							} else if(code == -1) {
								window.location.href = "/admin/login?url=" +
									window.location.pathname +
									window.location.search +
									window.location.hash;
								//未登录
							} else if(code <= -2 && code >= -7) {
								//必填字段未填写
								$scope.showAlert(rs.msg);
							} else if(code == -8) {
								//暂无数据
								$scope.isNoData = true;
								$scope.pageCount = 0;
							}

						},
						function(result) {

							var status = result.status;
							if(status == -1) {
								$scope.showAlert("服务器错误")
							} else if(status >= 404 && status < 500) {
								$scope.showAlert("请求路径错误")
							} else if(status >= 500) {
								$scope.showAlert("服务器错误")
							}
						});
				};
				$scope.getListLoopAdvPic($scope, $http);
			}
			////////////////////广告轮播-图片结束/////////////////////////
		}
		return activityCtrl;

	});