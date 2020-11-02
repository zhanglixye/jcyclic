$(function(){
	file_i18n();
	initDataGridHandler("taxCostListTab",50,"","top",false,"isHasFn");	
	isCheckLogin(1);
	init(0)
	$('#language').change(function(){
		init(2);
	})
	$('.buttontypeyx').addClass('hidden');
})
function init(del_flg){
	var path = $.getAjaxPath()+"PayeeTaxList";
	var pars = {
		"payeetaxmst": {
             	"del_flg": del_flg
    				}
		};
		$.ajax({
		url:path,
		data:JSON.stringify(pars),
		headers: {"requestID":$.getRequestID(),"requestName":$.getRequestNameByMst()},		
		success:function(data){

           if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
           
				var total = data[$.getRequestDataName()].length;
				//if(total==0){
				//	showErrorHandler('NO_FOUND_CLMST','info','info');
				//}
				for(var i =0;i<total;i++){
				    var del =	getGridLanguage("del_flg",data[$.getRequestDataName()][i]["del_flg"]);	
				    data[$.getRequestDataName()][i]["del_flg"]=del;
				    var language = $('#language').val();
					switch(language) {
						case "jp":
							data[$.getRequestDataName()][i]["user_tax_type"]=data[$.getRequestDataName()][i]["user_tax_type_jp"];
							data[$.getRequestDataName()][i]["invoice_type"]=data[$.getRequestDataName()][i]["invoice_type_jp"];
							data[$.getRequestDataName()][i]["invoice_text"]=data[$.getRequestDataName()][i]["invoice_text_jp"];
							break;
						case "zt":
							data[$.getRequestDataName()][i]["user_tax_type"]=data[$.getRequestDataName()][i]["user_tax_type_hk"];
							data[$.getRequestDataName()][i]["invoice_type"]=data[$.getRequestDataName()][i]["invoice_type_hk"];
							data[$.getRequestDataName()][i]["invoice_text"]=data[$.getRequestDataName()][i]["invoice_text_hk"];
							break;
						case "en":
							data[$.getRequestDataName()][i]["user_tax_type"]=data[$.getRequestDataName()][i]["user_tax_type_en"];
							data[$.getRequestDataName()][i]["invoice_type"]=data[$.getRequestDataName()][i]["invoice_type_en"];
							data[$.getRequestDataName()][i]["invoice_text"]=data[$.getRequestDataName()][i]["invoice_text_en"];
							break;
					}
				}
				
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
				$('#taxCostListTab').datagrid({loadFilter: pagerFilter}).datagrid('loadData',dataFil);
				//语言翻译
				readLanguageFile();
				$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
           }else{
            	showErrorFunHandler(data[$.getRequestMetaName()].result,"ERROR","ERROR");
            	//$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
           }
       },
       error:function(data){
       		showErrorHandler("CONNECTION_SERVER_ERROR","ERROR","ERROR");
       }
	});
}
function change() {
  	var row = $('#taxCostListTab').datagrid('getSelected');
		if(row == null || row == "") {
		showErrorHandler("NOT_CHOOSE_FAIL", "ERROR", "ERROR");
		return;
		}
		var code = row.invoice_type_code;
		if(code=='901'||code=='902'){
		showErrorHandler("TASK_SYSTEM", "ERROR", "ERROR");
		return;
		}
		sessionStorage.removeItem('user_tax_type');
		sessionStorage.removeItem('invoice_type');
		sessionStorage.removeItem('invoice_text');
		sessionStorage.removeItem('id');
			sessionStorage.setItem('user_tax_type', row.user_tax_type_code);
			sessionStorage.setItem('invoice_type', row.invoice_type_code);
			sessionStorage.setItem('invoice_text', row.invoice_text_code);
			sessionStorage.setItem('id', row.id);
			window.location.href = '../master/taxcost_registration.html?flg=1&user_tax_type='+row.user_tax_type_code+"&invoice_type="+row.invoice_type_code+"&invoice_text="+row.invoice_text_code+"&id="+row.id;
		

			
	};
function add() {
				window.location.href = '../master/taxcost_registration.html';
		};
		
function wxquery(){
	$('.buttontypewx').addClass('hidden')
	$('.buttontypeyx').removeClass('hidden')
	init('1');
}
function yxquery(){
	$('.buttontypeyx').addClass('hidden')
	$('.buttontypewx').removeClass('hidden')
	init('0');
}
function query(del_flg){
	var path = $.getAjaxPath()+"PayeeTaxList";
	var pars = {
			"payeetaxmst": {
             	"del_flg": del_flg
    				}
		};
		$.ajax({
		url:path,
		data:JSON.stringify(pars),
		headers: {"requestID":$.getRequestID(),"requestName":$.getRequestNameByMst()},
		success:function(data){
		   $.resetRequestID(data[$.getRequestUserInfoName()].requestID);
           if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
           
				var total = data[$.getRequestDataName()].length;
				//if(total=0){
					//showErrorHandler('NO_FOUND_CLMST','info','info');
				//}
				var dataFil = {
					total:total,
					rows:data[$.getRequestDataName()]
				}
				if(dataFil.total == 0){
					$('.switch_table_none').removeClass('hidden');
					$('.switch_table_none center').text(part_language_change_new("TABLE_CHECK_NO"));
				}else{
					$('.switch_table_none').addClass('hidden');
				}
				$('#taxCostListTab').datagrid({loadFilter: pagerFilter}).datagrid('loadData',dataFil);
           }else{
            	showErrorFunHandler(data[$.getRequestMetaName()].result,"ERROR","ERROR");
           }
       },
       error:function(data){
       		showErrorHandler("CONNECTION_SERVER_ERROR","ERROR","ERROR");
       }
	});
}