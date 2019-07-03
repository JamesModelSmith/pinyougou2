 //控制层 
app.controller('itemController' ,function($scope,$controller   ,itemService){	
	
	$controller('baseController',{$scope:$scope});//继承
	
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		itemService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
	
	//分页
	$scope.findPage=function(page,rows){			
		itemService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	
	//查询实体 
	$scope.findOne=function(id){				
		itemService.findOne(id).success(
			function(response){
				$scope.entity= response;					
			}
		);				
	}
	
	//保存 
	$scope.save=function(){				
		var serviceObject;//服务层对象  				
		if($scope.entity.id!=null){//如果有ID
			serviceObject=itemService.update( $scope.entity ); //修改  
		}else{
			serviceObject=itemService.add( $scope.entity  );//增加 
		}				
		serviceObject.success(
			function(response){
				if(response.success){
					//重新查询 
		        	$scope.reloadList();//重新加载
				}else{
					alert(response.message);
				}
			}		
		);				
	}
	
	 
	//批量删除 
	$scope.dele=function(){			
		//获取选中的复选框			
		itemService.dele( $scope.selectIds ).success(
			function(response){
				if(response.success){
					$scope.reloadList();//刷新列表
					$scope.selectIds=[];
				}						
			}		
		);				
	}
	
	$scope.searchEntity={};//定义搜索对象 
	
	//搜索
	$scope.search=function(page,rows){			
		itemService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
    //数量加减
	$scope.addNum=function (x) {
		$scope.num+=x;
		if($scope.num<1){
			$scope.num=1;
		}

    }
  $scope.specificationItems={};//存储用户选择的规格
    //用户选择规格
	$scope.selectSpecification=function (key,value) {
		$scope.specificationItems[key]=value;
		searchSku();//查寻SKU
    }
    //判断某规格是否被选中
	$scope.isSelected=function (key,value) {
		if($scope.specificationItems[key]==value){
			return true;
		}else{
			return false;
		}
    }
    $scope.sku={};//当前选择的SKU
	//加载默认的SKU
	$scope.loadSku=function () {
		$scope.sku=skuList[0];
		$scope.specificationItems=JSON.parse(JSON.stringify($scope.sku.spec));
    }
   //匹配两个对象是否相等
	matchObject=function (map1,map2) {
		for(var k in map1){
			if(map1[k]!=map2[k]){
				return false;
			}
		}
		for(var k in map2){
			if(map2[k]!=map1[k]){
				return false;
			}
		}
		return true;
    }
    //根据规格查询SKU
	searchSku=function () {
		for(var i=0;i<skuList.length;i++){
			if(matchObject(skuList[i].spec,$scope.specificationItems)){
				$scope.sku=skuList[i];
				return;
			}
		}
		$scope.sku={id:0,title:'----',price:0};
    }
    //添加商品到购物车
	$scope.addToCart=function () {
		alert('SKUID:'+$scope.sku.id);
		$http.get('../cart/addGoodsToCartList?itemId='+$scope.sku.id+'&num='+$scope.num,{'withCredentials':true}).success(
			function (response) {
				if(response.success){
					location.href='cart.html';
				}else {
					alert(response.message);
				}
            }
		);
    }
});	
