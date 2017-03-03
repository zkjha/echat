
//展示前端个人资料
function frontinfoController($scope, $http, $routeParams, $location,$sce){
	getFromeinfo($scope, $http,$sce,1)
	
}

//回显数据
function getFromeinfo($scope, $http,$sce,type){


	 $http.post("/admin/biz/frontinformation/getFrontinformation").then(
			 function(result){
				var rs =result.data;
				
				var code =rs.code;
				var data=rs.data;
				if(code==1){
					
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
					$scope.showAlert(rs.msg);
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
						var rs =result.data;
						var code =rs.code;
						if(code==1){
								window.location.href="#!/frontinfo";
						}else if(code ==-1){
							window.location.href="/admin/login?url="+window.location.pathname+window.location.search+window.location.hash;
							//未登录
						}else if(code<=-2&&code>=-7){
							//必填字段未填写
							$scope.showAlert(rs.msg);
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
function merchantinfoController($scope, $http,fileReader){
	
	$scope.checkmerchantnameinfo=function(){
		$scope.merchantnameIsError   =	$scope.merchantinfoForm.merchant_name.$error.required;
		
	}
	$scope.checkmerchantaddressinfo=function(){
		$scope.merchantaddressIsError=	$scope.merchantinfoForm.merchant_address.$error.required;
	}
	
	//保存表单信息	
	$scope.submitMerchantinfo=function(){
		//是否有提交错误
		var hassubmiterror=!!$scope.merchantinfoForm.$error.required ;
		if(hassubmiterror){
			$scope.checkmerchantnameinfo();
			$scope.checkmerchantaddressinfo();
			return;
		}else{
			
			var submitdata={'strMerchantaddress':$scope.merchant_address,'strMerchantlogo':$scope.strMerchantlogo,'strMerchantname':$scope.merchant_name};

			 $http.post("/admin/biz/merchant/updateMerchantInfo",submitdata).then(
					 function(result){
						
						var rs =result.data;
						var code =rs.code;
						if(code==1){
							$scope.showAlert('保存成功',function(){
								window.location.reload();
							});
						}else if(code ==-1){
							window.location.href="/admin/login?url="+window.location.pathname+window.location.search+window.location.hash;
							//未登录
						}else if(code<=-2&&code>=-7){
							//必填字段未填写
							$scope.showAlert(rs.msg);
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
	
	
	
	//回显图片
	$scope.getFile = function () {
         fileReader.readAsDataUrl($scope.file, $scope)
                       .then(function(result) {
                           $scope.imageSrc = result;
                           $scope.uploadimage();
                       });
     };
     //上传图片
	$scope.uploadimage=function(){
		// 组装表单数据
	    var postData = {
	        file: $scope.myFile,
	    };
	    var promise = postMultipart('/admin/biz/file/uploadWechantLogo', postData); 
	    promise.then(
				 function(result){
						var rs =result.data;
						var code =rs.code;
						if(code==1){
							$scope.strMerchantlogo=rs.data.strImgpath;;
						}else if(code ==-1){
							window.location.href="/admin/login?url="+window.location.pathname+window.location.search+window.location.hash;
							//未登录
						}else if(code<=-2&&code>=-7){
							//必填字段未填写
							$scope.showAlert(rs.msg);
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
	    
	    function postMultipart(url, data) {
	        var fd = new FormData();
	        angular.forEach(data, function(val, key) {
	            fd.append(key, val);
	        });
	        var args = {
	            method: 'POST',
	            url: url,
	            data: fd,
	            headers: {'Content-Type': undefined},
	            transformRequest: angular.identity
	        };
	        return $http(args);
	    }
	}
	
	
	
	
	//初始化表单数据
	$http.post("/admin/biz/merchant/getMerchantInfo").then(
			 function(result){
				
				var rs =result.data;
				var code =rs.code;
				var data=rs.data;
				console.log(data)
				if(code==1){
					//图片跟路径
					var strImgrootpath=data.strImgrootpath;
					var merchantEntity=data.merchantEntity;
					//会员数量
					$scope.intMembercount=merchantEntity.intMembercount;
					//过期时间
					$scope.strExpirationtime=merchantEntity.strExpirationtime;
					//版本信息
					$scope.strSystemversion=merchantEntity.strSystemversion;
					//商家名称
					$scope.merchant_name=merchantEntity.strMerchantname;
					//商家地址
					$scope.merchant_address=merchantEntity.strMerchantaddress;
					//图片
				    $scope.imageSrc=strImgrootpath+merchantEntity.strMerchantlogo;
				}else if(code ==-1){
					window.location.href="/admin/login?url="+window.location.pathname+window.location.search+window.location.hash;
					//未登录
				}else if(code<=-2&&code>=-7){
					//必填字段未填写
					$scope.showAlert(rs.msg);
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
	
	//升级版本
	$scope.updataversion=function(){
		var registcode=$scope.registcode;
		var data={'strActivationcode':registcode};
		$http.post("/admin/biz/merchant/upgradeMerchantSystem",data).then(
				 function(result){
					
					var rs =result.data;
					var code =rs.code;
					var data=rs.data;
					if(code==1){
						//图片跟路径
						 $scope.showAlert("升级成功",function(){
							window.location.reload();
						 });
						 
					}else if(code==100005){
						$scope.showAlert(rs.msg);
					}else if(code ==-1){
						window.location.href="/admin/login?url="+window.location.pathname+window.location.search+window.location.hash;
						//未登录
					}else if(code<=-2&&code>=-7){
						//必填字段未填写
						$scope.showAlert(rs.msg);
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

//职位控制器
function dutyController($scope, $http){
	 getDutyList($scope, $http,1);
	 $scope.onPageChange = function() {
	      // ajax request to load data
		 getDutyList($scope, $http,$scope.currentPage);
	      console.log();
	  };

	    // set pagecount in $scope
	  
}

function getDutyList($scope, $http,pagenum){
	var data={'pagenum':pagenum,"pagesize":5};
	
	$http.post("/admin/biz/duty/listDuty",data).then(
			 function(result){
				
				var rs =result.data;
				var code =rs.code;
				var data=rs.data;
				if(code==1){
					//图片跟路径
					$scope.havData=true;
					$scope.pageCount =data.iTotalPage;
					
					 
				}else if(code ==-1){
					window.location.href="/admin/login?url="+window.location.pathname+window.location.search+window.location.hash;
					//未登录
				}else if(code<=-2&&code>=-7){
					//必填字段未填写
					$scope.showAlert(rs.msg);
				}else if(code ==-8){
					//暂无数据
					$scope.havData=false;
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
