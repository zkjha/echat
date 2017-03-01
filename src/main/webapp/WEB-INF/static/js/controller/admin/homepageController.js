
//展示前端个人资料
function frontinfoController($scope, $http, $routeParams, $location,$sce){
	getFromeinfo($scope, $http,$sce,1)
	
}

//回显数据
function getFromeinfo($scope, $http,$sce,type){


	 $http.post("/admin/biz/frontinformation/getFrontinformation").then(
			 function(result){
			  console.log(result)
				var rs =result.data;
				
				var code =rs.code;
				
				if(code==1){
					var data=rs.data;
					if(type==1){//显示到普通html上
						 $scope.strContent = $sce.trustAsHtml(data.strContent);
					}else{
						 $scope.strContent = data.strContent;
					}
				 
				}else if(code ==-1){
					//未登录
					window.location.href="/admin/login?url="+window.location.pathname+window.location.search+window.location.hash;
				}else if(code<=-2&&code>=-7){
					//必填字段未填写
					$scope.showAlert(data.msg);
				}else if(code ==-8){
					//暂无数据
					$scope.strContent ="暂无数据";
				}
				
	         },
	         function(result){
	        	 $scope.editFrontinfoisActive=true;
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

//编辑前端个人资料
function editFrontinfoController($scope, $http, $routeParams, $location,$sce){
	    //回显数据
	    getFromeinfo($scope, $http,$sce,2)
		$scope.frontinfoHtml={};
		
		$scope.editFrontinfoisActive=true;
		
		$scope.saveFrontinfo=function(){
			if($scope.editFrontinfoisActive==false){
				return;
			}
			$scope.editFrontinfoisActive=false;
			//判断用户是否输入用户名
			if(!$scope.strContent ){
				$scope.showToast("内容必须填写");
				$scope.editFrontinfoisActive=true;
				return;
			}
			console.log($scope.strContent)
			 $http.post("/admin/biz/frontinformation/updateFrontinformation",{'strContent':$scope.strContent}).then(
					 function(result){
						 $scope.editFrontinfoisActive=true;
						var data =result.data;
						var code =data.code;
						if(code==1){
								window.location.href="#!/frontinfo";
						}else if(code ==-1){
							window.location.href="/admin/login?url="+window.location.pathname+window.location.search+window.location.hash;
							//未登录
						}else if(code<=-2&&code>=-7){
							//必填字段未填写
							$scope.showAlert(data.msg);
						}else if(code ==-8){
							//暂无数据
						}
						
			         },
			         function(result){
			        	 $scope.editFrontinfoisActive=true;
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


}

//商家信息控制器
function merchantinfoController($scope, $http){
	
	
}