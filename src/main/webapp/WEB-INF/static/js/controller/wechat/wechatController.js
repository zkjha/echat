define(['lib/remoteUrl'],function(remoteUrl){
	
	
	var WeChatController={
		
		
		 wechatCtr:function($scope,$http,$location){
		 
		 	//显示公众号管理在页面上
			$http.post(remoteUrl.getWechantconfig)
			.then(
				function(result){
					var wc = result.data;
					var code = wc.code;
					if(code == 1){
						$scope.strAppid = wc.data.strAppid;//公众号
						$scope.strAppname = wc.data.strAppname;//公众账号
						$scope.strEncodingaeskey = wc.data.strEncodingaeskey;//key
						$scope.strToken = wc.data.strToken;//Token
						$scope.strUrl = wc.data.strUrl;//Url
						$scope.intAuthoriNames = [{Id:0,value:'未授权'},{Id:1,value:'已授权'}];//select
						$scope.intAuthoriNamesstatus = wc.data.intAuthorizationstatus;//授权状态
						//$scope.$watch('intAuthoriNamesstatus',function(news,olds){
						//	console.info(news);
						//	console.info(olds);
						//});
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
				function(result) {
					
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
			
			//公众号管理保存
			
				$scope.wechatBz = function(){
				$scope.wechatIf = false;//表单验证是否为空
				$scope.strSuthorizationstatus = $scope.intAuthoriNamesstatus;
				console.info($scope.strSuthorizationstatus);
				var submitdata = {
					'strAppid':$scope.strAppid,//公众号ID
					'strAppname':$scope.strAppname,//公众号账号
					'strEncodingaeskey':$scope.strEncodingaeskey,//key
					'strSuthorizationstatus':$scope.strSuthorizationstatus,//状态
					'strToken':$scope.strToken,//token
					'strUrl':$scope.strUrl//url
				};
				$http.post(remoteUrl.updateWechantconfig,submitdata).then(
					function(result){
						var rs = result.data;
						var code = rs.code;
						if (code == 1) {
							$scope.showAlert(
								'保存成功',
								function() {
									window.location
											.reload();
								});
						} else if (code == -1) {
							window.location.href = "/admin/login?url="
									+ window.location.pathname
									+ window.location.search
									+ window.location.hash;
							// 未登录
						} else if (code == -2) {
							// 必填字段未填写
							$scope.showAlert(rs.msg);
						} else if (code == -8) {
							// 暂无数据
						}
					},
					function(result) {
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
				)
			};
			
			
			//清空配置
			$scope.wechatSc = function(){
				$scope.wechatIf = true;//表单验证是否为空
				//清空页面表单数据
				$scope.strAppid = "";
				$scope.strAppname = "";
				$scope.strEncodingaeskey = "";
				$scope.intAuthoriNamesstatus = 0;
				$scope.strToken = "";
				$scope.strUrl = "";
			};
			
		 }
		
	};
	
		return WeChatController;	
	

});