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
			var app = angular.module('myApp', ['httphelper', 'tips']);
			app.controller('homepage', ['$scope', '$http', function($scope, $http) {
				
				//个人信息展示
				$http.post(remoteUrl.weixingetLoginUserInfo).then(function(result) {
					var rs = result.data;
					var code = rs.code;
					var data = rs.data;
					if(code == 1) {
						$scope.dBalance = data.dBalance;//余额
						$scope.intIntegral = data.intIntegral;//积分
						$scope.intVouchers = data.intVouchers;//优惠券数量
						$scope.strLevelsname = data.strLevelsname;//会员卡级别
						$scope.strMembercardnum = data.strMembercardnum;//会员卡卡号
						$scope.strQrCode = data.strQrCode;
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
			}])
			angular.bootstrap(document, ['myApp']);
})