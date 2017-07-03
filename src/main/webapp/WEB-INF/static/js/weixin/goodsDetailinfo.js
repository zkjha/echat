/**
 * Created by Administrator on 2017/7/3.
 */
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
    app.controller('goodsDetailinfo', ['$scope', '$http', function($scope, $http) {
        console.info(window.location.search.split("=")[1])
        var strGoodsId = window.location.search.split("=")[1];
        (function(strGoodsId){
            var date={
                strGoodsId:strGoodsId
            }
            $http.post(remoteUrl.selectGoodsDetailInfo, date).then(function(result) {
                var rs = result.data;
                var code = rs.code;
                var data = rs.data;
                console.info(result)
                if(code == 1) {
                    $scope.goodsVOEntity = data.goodsVOEntity;

                    console.info(data.goodsVOEntity)
                } else if(code ==-1){
                    //得到登录路径
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
                    var status=result.status;
                    if(status==-1){
                        $scope.showAlert("服务器错误")
                    }else if(status>=404&&status<500){
                        $scope.showAlert("请求路径错误")
                    }else if(status>=500){
                        $scope.showAlert("服务器错误")
                    }
            })
        })(strGoodsId)
    }])
    angular.bootstrap(document, ['myApp'])
})