define([ 'lib/angular'],     function (angular) {  
	'use strict'
	return angular.module("menu", []).directive("menu", function() {
        return {
            restrict: "E", //default: EA
            replace: true,
            templateUrl: "/static/temp/admin/leftmenu.html",
            link:function($scope, $element, $attrs, $transclude){
            	$scope.isShowMenu=[];
            	$scope.isClicked={};
            	
            	for(var i=0;i<$scope.menuData.length;i++){
            		
            			$scope.isShowMenu[i]=false;
            	}
            	
            	
            	var url_hash = window.location.hash;
            	for(var y=0;y<$scope.menuData.length;y++){
            		var firstmenu=$scope.menuData[y];
            		  if( firstmenu.hasnext==true){
            				var next= firstmenu.next;
            				for(var z=0;z<next.length;z++){
            					if(next[z].link==url_hash){
                					$scope.isClicked[next[z].id]=true;
                					$scope.isShowMenu[y]=true;
                					break;
                				}
                			}
            			 
            			
            		  }else if(firstmenu.hasnext==false){
            			  if(firstmenu.link==url_hash){
            				  $scope.isClicked[firstmenu.id]=true;
            				  break;
            			  }
            		  }
            		  
            		  
            		  
            		
            		
            	}
            	//id 链接地址 length 下一级长度
                $scope.toggle=function(link,hasnext,index,id){
                	//下面是否有子菜单 有
            	   if(hasnext){
            		   //循环所有一级菜单 如果是当前这个索引  就转换
            		   for(var x=0;x<$scope.isShowMenu.length;x++){
                   		if(x==index){
                   			
                   		    $scope.isShowMenu[index]=!$scope.isShowMenu[index];
                   			continue;
                   		}else{
                   			$scope.isShowMenu[x]=false;  
                   			
                   		}
                   	}
            	   }else{//下面是否有子菜单 否
            		   for(y in  $scope.isClicked){
            			   $scope.isClicked[y]=false;
            		   }
            		   $scope.isClicked[id]=true;
            		   window.location.href=link;
            	   }
               }
            }
        };
    });
   
});
