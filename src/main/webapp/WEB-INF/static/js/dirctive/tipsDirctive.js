//
//function tipsDictive(app){
(function() {
	'use strict'
	angular.module("tips", []).directive("alert", function() {
		return {
			restrict : "E", //default: EA
			replace : true,
			templateUrl : "/static/temp/alertTemplate.html",
			link : function($scope, $element, $attrs, $transclude) {
				var callbackFn;
				$scope.okAction = function() {
					$scope.isShowAlert = false;
					if (callbackFn && typeof (callbackFn) == "function") {
						callbackFn(true)
					}

				};
				$scope.showAlert = function(msg, callback) {
					callbackFn = callback;
					$scope.isShowAlert = true;
					$scope.alertText = msg
				}
			}
		};
	}).directive("comfirm", function() {
		return {
			restrict : "E", //default: EA
			replace : true,
			templateUrl : "/static/temp/comfirmTemplate.html",
			link : function($scope, $element, $attrs, $transclude) {
				var callbackFn;
				$scope.comfirmAction = function() {
					$scope.isShowComfirm = false;
					if (callbackFn && typeof (callbackFn) == "function") {
						callbackFn(true)
					}

				};
				$scope.cancelAction = function() {
					$scope.isShowComfirm = false;
					if (callbackFn && typeof (callbackFn) == "function") {
						callbackFn(false)
					}

				};
				$scope.showComfirm = function(msg, callback) {
					callbackFn = callback;
					$scope.isShowComfirm = true;
					$scope.comfirmText = msg
				}
			}
		};
	}).directive("toast", function($timeout) {
		return {
			restrict : "E", //default: EA
			replace : true,
			templateUrl : "/static/temp/toastTemplate.html",
			link : function($scope, $element, $attrs, $transclude) {

				$scope.showToast = function(msg, callback) {
					$scope.isShowToast = true;
					$scope.toastText = msg
					$timeout(function() {
						$scope.isShowToast = false;
						if (callback && typeof (callback) == "function") {
							callback()
						}
					}, 1000);

				}
			}
		};
	});
	//	}
})();
