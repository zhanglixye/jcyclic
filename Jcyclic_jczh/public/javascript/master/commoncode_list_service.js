//页面加载后执行的全部检索	
$(function() {
	
	initDataGridHandler("commonListTab",9999999,'','top',false,'isHasFn');
	isCheckLogin(1);
	var path = $.getAjaxPath()+"commonmstSelAll";
	var pars = {};
		$.ajax({
		url:path,
		data:JSON.stringify(pars),
		headers: {"requestID":$.getRequestID(),"requestName":$.getRequestNameByMst()},
		success:function(data){
           if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
				$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
				var total = data[$.getRequestDataName()].length;
				var dataFil = {
					total:total,
					rows:data[$.getRequestDataName()]
				}
				if(dataFil.total == 0){
					$('.switch_table_none').removeClass('hidden');
					$('.switch_table_none center').text(part_language_change_new("TABLE_CHECK_NO"));
				}else{
					$('.switch_table_none').addClass('hidden');
					$('.switch_table_none center').text(part_language_change_new("TABLE_CHECK_NO"));
				}
				$('#commonListTab').datagrid({loadFilter: pagerFilter}).datagrid('loadData',dataFil);
           }else{
            	showErrorFunHandler(data[$.getRequestMetaName()].result,"ERROR","ERROR");
           }
       },
       error:function(data){
       		showErrorHandler("CONNECTION_SERVER_ERROR","ERROR","ERROR");
       }
	});
	
//	------------
	
})


//共通新增/修改的跳转
function saleEdit(pd){
	var mstcd ="";
	if(pd=="0"){//参数pd为0时，为追加
		url = $.getJumpPath()+"master/commoncode_registration.html";//此处拼接内容
        window.location.href = url;
	}else{//当参数pd为1的时候为变更。
		var row = $('#commonListTab').datagrid('getSelected');
		if(row!=null&&row!=""){//判断参数row,row为表单选中的数据，有值才允许变更
			mstcd =row.mstcd;
			itemcd=row.itemcd;
			company_cd=row.company_cd;
			url = $.getJumpPath()+"master/commoncode_registration.html?mstcd="+mstcd+"&itemcd="+itemcd+"&company_cd="+company_cd;//此处拼接内容
            window.location.href = url;
		}else{
			showErrorHandler('NOT_CHOOSE_FAIL','info','info');
		}
	}
}

