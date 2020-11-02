var file_i18n = function() { /**上传文件input样式实例化*/
		$('.buttonText').addClass('i18n');
		$('.buttonText').attr("name", "suppliersList_select_file");
	}
function ss() { /**上传文件input样式  -- 按需调用*/
	$('.buttonText').addClass('i18n');
		$('.buttonText').attr("name", "suppliersList_select_file");
	var path = $.getAjaxPath()+"seljobColumns";	
	var pars = {};
	$.ajax({
		url:path,
		data:JSON.stringify(pars),
		headers: {"requestID":$.getRequestID(),"requestName":$.getRequestNameByJcZH()},
		success:function(data){
           if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
           		//数据填充到表格中
           		var dataColumns=data.data
           	   initDataGridHandler('job_login_list',10,null,'top',true,'isHasFn',dataColumns,false,true);
		       $.resetRequestID(data[$.getRequestUserInfoName()].requestID);
	           }else{
	           	showErrorFunHandler(data[$.getRequestMetaName()].result,"ERROR","ERROR");
	           }
       },
       error:function(data){
       		showErrorHandler("CONNECTION_SERVER_ERROR","ERROR","ERROR");
       }
	});
	
}