require.config({
	baseUrl: EK.STATIC_ROOT + "js",
	shim: {　　　　　　
		'lib/angular': {
			exports: 'angular'
		},
		'lib/angular-route': {
			deps: ['lib/angular'],
			exports: 'angular-route'
		}　　　　
	}
});

require(['lib/angular', 'lib/remoteUrl', 'lib/requestParamUtill', 'dirctive/tipsDirctive'], function(angular, remoteUrl, WeChatController) {
    var app = angular.module("myApp",['httphelper', 'tips'])
    app.controller("mycontrolller",["$scope","$http",function($scope,$http){
        //个人信息展示
        $http.post(remoteUrl.selectMemberInfo).then(function(result) {
            var rs = result.data;
            var code = rs.code;
            var data = rs.data;
            if(code == 1) {
                $scope.intIntegration = data.intIntegration;//积分
            } else if(code == -1) {
                //得到登录路径
                window.location.href = "/weixin/login?url=" + window.location.pathname + window.location.search + window.location.hash;
                //未登录
            } else if(code <= -2 && code >= -7) {
                //必填字段未填写
                $scope.showToast(rs.msg);
            }
        }, function(result) {
            var status = result.status;
            if(status == -1) {
                $scope.showToast("服务器错误")
            } else if(status >= 404 &&
                status < 500) {
                $scope.showToast("请求路径错误")
            } else if(status >= 500) {
                $scope.showToast("服务器错误")
            }
        })
        //会员中心 -- 签到信息(最近一次签到信息) -- 查询
        $http.post(remoteUrl.selectSignDays).then(function(result) {
            var rs = result.data;
            var code = rs.code;
            var data = rs.data;
            console.info(result)
            if(code == 1) {
                console.info(data)
                $scope.weiXinMemberSignEntity = data.weiXinMemberSignEntity;//余额
            } else if(code == -1) {
                //得到登录路径
                window.location.href = "/weixin/login?url=" + window.location.pathname + window.location.search + window.location.hash;
                //未登录
            } else if(code <= -2 && code >= -7) {
                //必填字段未填写
                $scope.showToast(rs.msg);
            }
        }, function(result) {
            var status = result.status;
            if(status == -1) {
                $scope.showToast("服务器错误")
            } else if(status >= 404 &&
                status < 500) {
                $scope.showToast("请求路径错误")
            } else if(status >= 500) {
                $scope.showToast("服务器错误")
            }
        })

        //会员中心 -- 签到信息（按月) -- 查询
        function selectSignDaysByMonth(strSearchDate){
            $scope.Alldays = [];
            data = {
                strSearchDate:strSearchDate
            }
            $http.post(remoteUrl.selectSignDaysByMonth,data).then(function(result) {
                var rs = result.data;
                var code = rs.code;
                var data = rs.data;
                console.info(result)
                if(code == 1) {
                    var days = data.listWeiXinMemberSignEntity;
                    var Alldays = [];
                    for(let i in days){
                        var everymouths = strSearchDate.split("-")[1];
                        console.info(everymouths)
                        var everyday = new Date(days[i].strSignTime).toString().split(" ")[2];
                        //var everyday = "05"
                        if(everyday.split("")[0] == "0"){
                            everyday = everyday.split("")[1]
                        }
                        Alldays[i] = everyday;
                    }
                    var nowAllDay = $scope.nowAllDay;
                    console.info(nowAllDay)
                    var Trues = []
                    for(let k =0;k<Alldays.length;k++){
                        for(let j =0;j<nowAllDay.length;j++){
                            if(nowAllDay[j] == Alldays[k]){
                                Trues[j] = Alldays[k];
                            }else{
                                //Trues[j] = -1;
                            }
                        }
                    }
                    $scope.Alldays = Trues;
                    console.info(Trues)
                } else if(code == -1) {
                    //得到登录路径
                    window.location.href = "/weixin/login?url=" + window.location.pathname + window.location.search + window.location.hash;
                    //未登录
                } else if(code <= -2 && code >= -7) {
                    //必填字段未填写
                    $scope.showToast(rs.msg);
                }
            }, function(result) {
                var status = result.status;
                if(status == -1) {
                    $scope.showToast("服务器错误")
                } else if(status >= 404 &&
                    status < 500) {
                    $scope.showToast("请求路径错误")
                } else if(status >= 500) {
                    $scope.showToast("服务器错误")
                }
            })
        }
        //签到信息新增
        $scope.newQiandaoNews = function(){
            $http.post(remoteUrl.insertMemberSignInfo).then(function(result) {
                var rs = result.data;
                var code = rs.code;
                var data = rs.data;
                console.info(result)
                if(code == 1) {
                    $scope.showToast(rs.msg,function(){
                        window.location.reload()
                    })//余额
                } else if(code == -1) {
                    //得到登录路径
                    window.location.href = "/weixin/login?url=" + window.location.pathname + window.location.search + window.location.hash;
                    //未登录
                } else if(code <= -2 && code >= -7) {
                    //必填字段未填写
                    $scope.showToast(rs.msg);
                }
            }, function(result) {
                var status = result.status;
                if(status == -1) {
                    $scope.showToast("服务器错误")
                } else if(status >= 404 &&
                    status < 500) {
                    $scope.showToast("请求路径错误")
                } else if(status >= 500) {
                    $scope.showToast("服务器错误")
                }
            })
        }

        //////////////////////////////////////日期控制显示//////////////////////////////
        var date = new Date();
        date.setDate(1);
        $scope.mouthOneWeekedOneDay = date.getDay();

        //当月
        $scope.nowMouth = date.getMonth()+1;
        $scope.nowYears = date.getFullYear();
        $scope.nowAllDay = mouthday($scope.nowMouth,$scope.nowYears)
        var strSearchDate = $scope.nowYears+"-"+$scope.nowMouth+"-01"
        selectSignDaysByMonth(strSearchDate)//会员中心 -- 签到信息（按月) -- 查询
        //上一月
        $scope.preMouth = $scope.nowMouth - 1;
        $scope.preYears = date.getFullYear();
        if($scope.preMouth == 0){
            $scope.preMouth = 12;
            $scope.preYears = $scope.nowYears -1;
        }
        $scope.preAllDay = mouthday($scope.preMouth,$scope.preYears)
        //下一月
        $scope.nextMouth = $scope.nowMouth + 1;
        $scope.nextYears = $scope.nowYears;
        if($scope.nextMouth == 13){
            $scope.nextMouth = 1;
            $scope.nextYears = $scope.nowYears + 1;
        }
        $scope.nextAllDay = mouthday($scope.nextMouth,$scope.nextYears);

        //当月的上一月内容
        $scope.preViewDay = $scope.preAllDay.slice($scope.preAllDay.length-$scope.mouthOneWeekedOneDay , $scope.preAllDay.length);
        var allLength = 42 - $scope.preViewDay.length - $scope.nowAllDay.length;
        $scope.nextViewDay = $scope.nextAllDay.slice(0 , allLength);


        //到上月日历点击事件
        $scope.preClickMouth = function(){
            $scope.nowMouth = $scope.nowMouth - 1;
            if($scope.nowMouth == 0){
                $scope.nowMouth = 12;
                $scope.nowYears = $scope.nowYears - 1;
            }
            //上一月
            $scope.preMouth = $scope.nowMouth - 1;
            $scope.preYears = date.getFullYear();
            if($scope.preMouth == 0){
                $scope.preMouth = 12;
                $scope.preYears = $scope.nowYears -1;
            }
            //		设置当前月份,当年年份

            date.setFullYear($scope.nowYears);
            date.setMonth($scope.nowMouth-1);
            $scope.preMouthOneWeekedOneDay =  date.getDay();

            //前一月月份等
            $scope.preAllDay = mouthday($scope.preMouth,$scope.preYears)
            $scope.nowAllDay = mouthday($scope.nowMouth,$scope.nowYears);

            $scope.preViewDay = $scope.preAllDay.slice($scope.preAllDay.length-$scope.preMouthOneWeekedOneDay , $scope.preAllDay.length);
            var allLength = 42 - $scope.preViewDay.length - $scope.nowAllDay.length;
            $scope.nextViewDay = $scope.nextAllDay.slice(0 , allLength);

            var strSearchDatepre = $scope.nowYears+"-"+$scope.nowMouth+"-01"
            selectSignDaysByMonth(strSearchDatepre)//会员中心 -- 签到信息（按月) -- 查询
        };

        //到下一月点击事件
        $scope.nextClickMouth = function(){
            $scope.nowMouth = $scope.nowMouth + 1;
            if($scope.nowMouth == 13){
                $scope.nowMouth = 1;
                $scope.nowYears = $scope.nowYears + 1;
            }
            //上一月
            $scope.preMouth = $scope.nowMouth - 1;
            $scope.preYears = date.getFullYear();
            if($scope.preMouth == 0){
                $scope.preMouth = 12;
                $scope.preYears = $scope.nowYears -1;
            }
            //		设置当前月份,当年年份

            date.setFullYear($scope.nowYears);
            date.setMonth($scope.nowMouth-1);
            $scope.preMouthOneWeekedOneDay =  date.getDay();

            //前一月月份等
            $scope.preAllDay = mouthday($scope.preMouth,$scope.preYears)
            $scope.nowAllDay = mouthday($scope.nowMouth,$scope.nowYears);

            $scope.preViewDay = $scope.preAllDay.slice($scope.preAllDay.length-$scope.preMouthOneWeekedOneDay , $scope.preAllDay.length);
            var allLength = 42 - $scope.preViewDay.length - $scope.nowAllDay.length;
            $scope.nextViewDay = $scope.nextAllDay.slice(0 , allLength);

            var strSearchDateNext = $scope.nowYears+"-"+$scope.nowMouth+"-01"
            selectSignDaysByMonth(strSearchDateNext)//会员中心 -- 签到信息（按月) -- 查询
        }

        var nowDatas = new Date();
        $scope.Nowdays = nowDatas.getDate();
        $scope.Nowmouths = nowDatas.getMonth()+1;
        //////////////////////////////////////日期控制显示//////////////////////////////
    }])

    angular.bootstrap(document,["myApp"]);





    function mouthday(mouths,years){
        var thirtyOne = [];
        for(var i = 0;i <= 30;i++){
            thirtyOne[i] = i+1;
        }
        var thirty = [];
        for(var k = 0;k <= 29;k++){
            thirty[k] = k+1;
        }
        var twentyNight = [];
        for(var a = 0;a <= 28;a++){
            twentyNight[a] = a+1;
        }
        var twentyeight = [];
        for(var b= 0;b <= 27;b++){
            twentyeight[b] = b+1;
        }
        //判断每月几天
        if(mouths == 1 ||mouths == 3 ||mouths == 5 ||mouths == 7 ||mouths == 8 ||mouths == 10 ||mouths == 12){
            return thirtyOne;
        }else if(mouths == 4 ||mouths == 6 ||mouths == 9 ||mouths == 11){
            return thirty;
        }else if(mouths == 2){
            if((years%4== 0 && years % 100!=0) || (years%400 == 0)){
                return twentyNight;
            }else{
                return twentyeight;
            }
        }
    }
})