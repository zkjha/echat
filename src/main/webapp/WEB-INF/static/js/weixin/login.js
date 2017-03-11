require.config({
	baseUrl : EK.STATIC_ROOT + "js",
	shim:{
　　　　　　'lib/angular':{
		    exports:'angular'
		},
		'lib/angular-route':{
			deps:['lib/angular'],
			exports: 'angular-route'
		}
　　　　}
});

require(['lib/angular','lib/remoteUrl','lib/requestParamUtill','dirctive/tipsDirctive'],function(angular,remoteUrl,WeChatController){
	var app = angular.module('myApp',['httphelper','tips']);
	app.controller('myController',['$scope','$http',function($scope,$http){
		$scope.strMobile = "";
		$scope.strAuthcode = "";
		$scope.send = function(){
			var strMobile = {
				'strMobile':$scope.strMobile
			};
			console.info(12);
			$http.post(remoteUrl.weixinsendAuthcode,strMobile).then(function(result){
				var rs =result.data;
				var code =rs.code;
				var data=rs.data;
				if(code==1){
					$scope.showAlert(rs.msg);
				}else if(code ==-1){
					//得到登录路径
					window.location.href="/admin/login?url="+window.location.pathname+window.location.search+window.location.hash;
					//未登录
				}else if(code<=-2&&code>=-7){
					//必填字段未填写
					$scope.showAlert(rs.msg);
				}else if(code ==100007){
					//暂无数据
					$scope.showAlert(rs.msg,function() {
									window.location
											.reload();
								});
				}else if(code ==100009){
					//暂无数据
					$scope.showAlert(rs.msg,function() {
									window.location
											.reload();
								});
				}
			},function(){
				
			})
		}
		
	
	
	//登陆认证
	
		$scope.login = function(){
			var strMobile = {
				'strMobile':$scope.strMobile,
				'strAuthcode':$scope.strAuthcode
			}
			$http.post(remoteUrl.weixinloginCertification,strMobile).then(function(result){
				var rs =result.data;
				var code = rs.code;
				var data=rs.data;
				if(code==1){
					console.info(123)
					$scope.showAlert(
								'登录成功',
								function() {
									window.location
											.reload();
								});
				}else if(code ==-1){
					//得到登录路径
					window.location.href="/admin/login?url="+window.location.pathname+window.location.search+window.location.hash;
					//未登录
				}else if(code>=2 && code<=7){
					//必填字段未填写
					$scope.sureClick == true;
					$scope.showAlert(rs.msg);
				}else if(code ==-8){
					//暂无数据
				}else if(code ==100007){
					//手机号格式错误
					$scope.showAlert(rs.msg);
				}else if(code ==100009){
					//该手机号还不是会员
					$scope.showAlert(rs.msg);
				}else if(code ==100010){
					//验证码过期
					$scope.showAlert(rs.msg);
				}else if(code ==100011){
					//验证码错误
					$scope.showAlert(rs.msg);
				}
			},function(){
				
			})
		}
	}])	
	
	
	
	//倒计时指令
	app.directive('resendCode',function($interval){
			return{
				restrict:'E',
				template:'<a>{{paracont}}</a>',
				replace:true,
				link:function(scope,iElement,iAttrs,controller){
					scope.paracont = "获取验证码";
						iElement[0].onclick = function(){
						
				       scope.paraclass = "but_null";  
				       scope.paraevent = true;  
				       scope.rember = iElement[0].onclick;
				       var second = 60,  
				            timePromise = undefined; 
					        timePromise = $interval(function(){  
					          if(second<=0){  
					            $interval.cancel(timePromise);  
					            timePromise = undefined;  
					  
					            second = 60;  
					            scope.paracont = "重发验证码";  
					            scope.paraclass = "but_null";  
					            scope.paraevent = true; 
					             iElement[0].onclick =scope.rember;
					          }else{  
					            scope.paracont = second + "秒后可重发";  
					            scope.paraclass = "not but_null";  
					            second--; 
					            iElement[0].onclick =false;
					          }  
					        },1000,100);
					}
					
				}
			}
		})
	angular.bootstrap(document,['myApp'])
})