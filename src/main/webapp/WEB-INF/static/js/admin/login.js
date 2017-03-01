var app = angular.module("login",[]);
requestParamUtill(app);
tipsDictive(app);
app.controller('loginCtrl',['$scope','$http',function($scope,$http){
	$scope.user={};
	$scope.isActive=true;
	$scope.keydown=function($event){
		 if($event.keyCode==13){//回车
			 $scope.loginAction();
	      }
	}
	$scope.loginAction=function(){
		if($scope.isActive==false){
			return;
		}
		$scope.isActive=false;
		console.log($scope.user)
		//判断用户是否输入用户名
		if(!$scope.user.strLoginname ){
			$scope.useridIsError=true;
			$scope.useridErrorInfo='工号必须填写';
			$scope.isActive=true;
			return;
		}else{
			$scope.useridIsError=false;
			$scope.useridErrorInfo='';
		}
		//判断用户是否输入密码
		if(!$scope.user.strPassword ){
			$scope.passwordIsError=true;
			$scope.passwordErrorInfo='密码必须填写';
			$scope.isActive=true;
			return;
		}else{
			$scope.passwordIsError=false;
			$scope.passwordErrorInfo='';
		}
		 $http.post("loginCertification",$scope.user).then(
				 function(result){
					 $scope.isActive=true;
					var data =result.data;
					
					var code =data.code;
					if(code==1){
						var url =$scope.url;
					
						if(url==""){
							window.location.href="/admin/biz/homepage";
						}else{
							window.location.href=url;
							
						}
						
					}else if(code ==100001){
						$scope.useridIsError=true;
						$scope.passwordIsError=false;
						$scope.useridErrorInfo=data.msg;
						//登陆账户不存在
					}else if(code ==100002){
						$scope.useridIsError=false;
						$scope.passwordIsError=true;
						$scope.passwordErrorInfo=data.msg;
						//登陆密码不存在
					}else if(code ==100003){
						$scope.useridIsError=true;
						$scope.passwordIsError=false;
						$scope.useridErrorInfo=data.msg;
						//登陆账户被禁用
					}else if(code ==-1){
						//未登录
					}else if(code<=-2&&code>=-7){
						//必填字段未填写
						$scope.showAlert(data.msg);
					}else if(code ==-8){
						//暂无数据
					}
					
		         },
		         function(result){
		        	 $scope.isActive=true;
					 var status=result.status;
		        	 if(status==-1){
		        		 $scope.showAlert("服务器错误")
		        	 }else if(status>=404&&status<500){
		        		 $scope.showAlert("请求路径错误")
					 }else if(status>=500){
						 $scope.showAlert("服务器错误")
					 }
		         }
		 
		 );
	}
}]);

