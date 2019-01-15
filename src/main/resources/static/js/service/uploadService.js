app.service('uploadService',function($http){
	
	//上传文件
	this.uploadFile=function(){
		var formdata=new FormData();//文件上传用的类
		formdata.append('file',file.files[0]);//file 文件上传框的name
		
		return $http({
			url:'../upload',
			method:'post',
			data:formdata,
			headers:{ 'Content-Type':undefined },
			transformRequest: angular.identity	//对表单进行二进制序列化
		});
		
	}
	
	
});