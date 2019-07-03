//控制层
app.controller('brandController',function ($scope,$http,brandService,$controller) {

    $controller('baseController',{$scope:$scope});
    //查询品牌列表
    $scope.findAll=function () {
        brandService.findAll().success(
            function (response) {
                $scope.list=response;//列表变量赋值
            }
        );
    };

    $scope.findPage=function (page,size) {
        brandService.findPage().success(
            function (response) {
                $scope.list=response.rows;//显示当前页数据
                $scope.paginationConf.totalItems=response.total;//更新总记录数


            }
        );
    };

    //新增
    $scope.save=function () {
        //var methodName='add';//方法名
        var object=null;
        if($scope.entity.id!=null){
            //methodName='update';
            object=brandService.update($scope.entity);
        }else {
            object=brandService.add($scope.entity);
        }
        object.success(
            function (response) {
                if(response.success){
                    $scope.reloadList();//刷新
                }else {
                    alert(response.message);
                }

            }
        );
    };
      $scope.entity=[];
    //查询实体
    $scope.findOne=function (id) {
        brandService.findOne(id).success(
            function (response) {
                $scope.entity=response;
            }
        );
    };
    //用户勾选复选框
    $scope.selectIds=[];//用户勾选的ID集合

    //删除
    $scope.dele=function () {
        brandService.dele($scope.selectIds).success(
            function (response) {
                // $scope.reloadList();
                if(response.success){
                    $scope.reloadList();//刷新
                }else {
                    alert(response.message);
                }
            }
        );
    };
    $scope.searchEntity={};
    //条件查询,控制前台显示数据的方法。page是当前页，rows是每页要显示的条数
    $scope.search=function (page,rows) {
        brandService.search(page,rows,$scope.searchEntity).success(
            function (response) {
                $scope.list=response.rows;//显示当前页数据
                $scope.paginationConf.totalItems=response.total;//更新总记录数


            }
        );
    };
});