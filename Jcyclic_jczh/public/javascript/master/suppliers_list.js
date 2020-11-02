$(function(){
	/**上传文件input样式  -- 按需调用*/
	file_i18n();
	initDataGridHandler("suppliersListTab",50,"data","top",true,"isHasFn")
	var sideBar =getQueryStringValue('sideBar');
	$('#language').change(function(){
		queryform();
	})
	if(sideBar=='sideBar'){
		queryform();
	}else{
		var account_cd = sessionStorage.getItem("account_cd");
		var divnm = sessionStorage.getItem("divnm");
		var client_flg = sessionStorage.getItem("client_flg");
		var contra_flg = sessionStorage.getItem("contra_flg");
		var pay_flg = sessionStorage.getItem("pay_flg");
		var hdy_flg = sessionStorage.getItem("hdy_flg");
		var clientall = sessionStorage.getItem("clientall");
		var del_flg0 = sessionStorage.getItem("del_flg0");
		var del_flg1 = sessionStorage.getItem("del_flg1");
		var del_flgall = sessionStorage.getItem("del_flgall");
		
		$("#account_cd").val(account_cd);
		$("#divnm").val(divnm);
		if (client_flg=='1'){ 
			$('#client_flg').prop("checked",true);
		}
		if (contra_flg=='1'){ 
			$('#contra_flg').prop("checked",true);
		}
		if (pay_flg=='1'){ 
			$('#pay_flg').prop("checked",true);
		}
		if (hdy_flg=='1'){ 
			$('#hdy_flg').prop("checked",true);
		}
		if (clientall=='1'){ 
			$('#clientall').prop("checked",true);
		}
		if (del_flg0=='0'){ 
			$('#delflag').prop("checked",true);
		}
		if (del_flg1=='1'){ 
			$('#delflag1').prop("checked",true);
		}
		if (del_flgall=='2'){ 
			$('#delflagall').prop("checked",true);
		}
		queryform();
	}
	
})
function queryform(){
	var account_cd = $("#account_cd").val();
	var divnm =$("#divnm").val();
	var client_flg=$('input:radio[id="client_flg"]:checked').val();
	var contra_flg=$('input:radio[id="contra_flg"]:checked').val();
	var pay_flg=$('input:radio[id="pay_flg"]:checked').val();
	var hdy_flg=$('input:radio[id="hdy_flg"]:checked').val();
	var clientall=$('input:radio[id="clientall"]:checked').val();
	var del_flg0=$('input:radio[id="delflag"]:checked').val();
	var del_flg1=$('input:radio[id="delflag1"]:checked').val();
	var del_flgall=$('input:radio[id="delflagall"]:checked').val();
	
	
	var del_flg = '';
	if (del_flg0!='' && del_flg0!=null){
		del_flg ='2';
	}
	if (del_flg1!='' && del_flg1!=null){
		del_flg ='1';
	}

		var path = $.getAjaxPath()+"ClmstQuery";
		var pars = {
			
         	"clmst": {
             	"account_cd": account_cd,
             	"divnm": divnm,
             	"client_flg": client_flg,
             	"contra_flg": contra_flg,
             	"pay_flg": pay_flg,
             	"hdy_flg": hdy_flg,
             	"del_flg": del_flg
    				}
				};
		$.ajax({
		url:path,
		headers: {"requestID":$.getRequestID(),"requestName":$.getRequestNameByMst()},
		data:JSON.stringify(pars),
		success:function(data){
		   
           if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
           		
				var total = data[$.getRequestDataName()].clmstList.length;
				for(var i =0;i<total;i++){
				  var del =	getGridLanguage("del_flgclmst",data[$.getRequestDataName()]['clmstList'][i]["del_flg"]);	
				  data[$.getRequestDataName()]['clmstList'][i]["del_flg"]=del;
				  var address1 = data[$.getRequestDataName()]['clmstList'][i]["divadd1"];
				  var address2 = data[$.getRequestDataName()]['clmstList'][i]["divadd2"];
				  var address3 = data[$.getRequestDataName()]['clmstList'][i]["divadd"];
				  var address4 = data[$.getRequestDataName()]['clmstList'][i]["div_tel"];
				  
				  data[$.getRequestDataName()]['clmstList'][i]["divadd1"]=address1+" "+address2+" "+address3+" "+address4;
				  if(data[$.getRequestDataName()]['clmstList'][i]["client_flg"]=='1'){
				  		data[$.getRequestDataName()]['clmstList'][i]["client_flg"]='◯'
				  }else{
				  		data[$.getRequestDataName()]['clmstList'][i]["client_flg"]=' '
				  };
				  if(data[$.getRequestDataName()]['clmstList'][i]["contra_flg"]=='1'){
				  		data[$.getRequestDataName()]['clmstList'][i]["contra_flg"]='◯'
				  }else{
				  		data[$.getRequestDataName()]['clmstList'][i]["contra_flg"]=' '
				  };
				  if(data[$.getRequestDataName()]['clmstList'][i]["pay_flg"]=='1'){
				  		data[$.getRequestDataName()]['clmstList'][i]["pay_flg"]='◯'
				  }else{
				  		data[$.getRequestDataName()]['clmstList'][i]["pay_flg"]=' '
				  };
				  if(data[$.getRequestDataName()]['clmstList'][i]["hdy_flg"]=='1'){
				  		data[$.getRequestDataName()]['clmstList'][i]["hdy_flg"]='◯'
				  }else{
				  		data[$.getRequestDataName()]['clmstList'][i]["hdy_flg"]=' '
				  };
				}
				var dataFil = {
					total:total,
					rows:data[$.getRequestDataName()].clmstList
				}
				if(dataFil.total == 0){
					$('.switch_table_none').removeClass('hidden');
					$('.switch_table_none center').text(part_language_change_new("TABLE_CHECK_NO"));
				}else{
					$('.switch_table_none').addClass('hidden');
					$('.switch_table_none center').text(part_language_change_new("TABLE_CHECK_NO"));
				}
				//forever find first page
			    $('#suppliersListTab').datagrid('options').pageNumber = 1
				$('#suppliersListTab').datagrid({loadFilter: pagerFilter}).datagrid('loadData',dataFil);
				//权限处理	
				menuNode=data['userInfo']['uNodeList'];
				if(isHavePower(menuNode,[74])==false){
					$("#clmstchange").addClass("hidden");
					$("#userchange").addClass("hidden");
					$(".clmstadd").addClass("hidden");
					$(".lastChild").addClass("hidden");
					$(".uploadhid").addClass("hidden");
				}
				$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
				//table高度  默认加高5px
				var hei = $('.panel-body-noheader').css('height');
				if(hei) {
					var hei = parseInt(hei) + 5;
				}
				$('.panel-body-noheader').css('max-height', hei);
           }else{
            	showErrorFunHandler(data[$.getRequestMetaName()].result,"ERROR","ERROR");
           }
       },
       error:function(data){
       		showErrorHandler("CONNECTION_SERVER_ERROR","ERROR","ERROR");
       }
	});
}
function cleartest(){
		$("#account_cd").val('');
	  	$("#divnm").val('');
		$('#clientall').prop("checked",true);
		$('#delflag').prop("checked",true);
		sessionStorage.removeItem('cldivcd');
		sessionStorage.removeItem('account_cd');
		sessionStorage.removeItem('divnm');
		sessionStorage.removeItem('client_flg');
		sessionStorage.removeItem('contra_flg');
		sessionStorage.removeItem('pay_flg');
		sessionStorage.removeItem('hdy_flg');
		sessionStorage.removeItem('clientall');
		sessionStorage.removeItem('del_flg0');
		sessionStorage.removeItem('del_flg1');
		sessionStorage.removeItem('del_flgall');
		sessionStorage.removeItem('divname_full')
}
function clmstadd() {
		sessionStorage.removeItem('cldivcd');
		sessionStorage.removeItem('account_cd');
		sessionStorage.removeItem('divnm');
		sessionStorage.removeItem('client_flg');
		sessionStorage.removeItem('contra_flg');
		sessionStorage.removeItem('pay_flg');
		sessionStorage.removeItem('hdy_flg');
		sessionStorage.removeItem('clientall');
		sessionStorage.removeItem('del_flg0');
		sessionStorage.removeItem('del_flg1');
		sessionStorage.removeItem('del_flgall');
		sessionStorage.removeItem('divname_full')
		
		sessionStorage.setItem('account_cd', $("#account_cd").val());
		sessionStorage.setItem('divnm', $("#divnm").val());
		sessionStorage.setItem('client_flg', $('input:radio[id="client_flg"]:checked').val());
		sessionStorage.setItem('contra_flg', $('input:radio[id="contra_flg"]:checked').val());
		sessionStorage.setItem('pay_flg', $('input:radio[id="pay_flg"]:checked').val());
		sessionStorage.setItem('hdy_flg', $('input:radio[id="hdy_flg"]:checked').val());
		sessionStorage.setItem('clientall', $('input:radio[id="clientall"]:checked').val());
		sessionStorage.setItem('del_flg0', $('input:radio[id="delflag"]:checked').val());
		sessionStorage.setItem('del_flg1', $('input:radio[id="delflag1"]:checked').val());
		sessionStorage.setItem('del_flgall', $('input:radio[id="delflagall"]:checked').val());
		window.location.href = '../master/suppliers_registration.html';
		}
function clmstchange() {
		sessionStorage.removeItem('cldivcd');
		sessionStorage.removeItem('account_cd');
		sessionStorage.removeItem('divnm');
		sessionStorage.removeItem('client_flg');
		sessionStorage.removeItem('contra_flg');
		sessionStorage.removeItem('pay_flg');
		sessionStorage.removeItem('hdy_flg');
		sessionStorage.removeItem('clientall');
		sessionStorage.removeItem('del_flg0');
		sessionStorage.removeItem('del_flg1');
		sessionStorage.removeItem('del_flgall');
		sessionStorage.removeItem('divname_full')
	  	var row = $('#suppliersListTab').datagrid('getSelected');
			if(row == null || row == '') {
				showErrorHandler("NOT_CHOOSE_FAIL","ERROR","ERROR");
			} else {
				sessionStorage.setItem('cldivcd', row.cldivcd);
				sessionStorage.setItem('account_cd', $("#account_cd").val());
				sessionStorage.setItem('divnm', $("#divnm").val());
				sessionStorage.setItem('client_flg', $('input:radio[id="client_flg"]:checked').val());
				sessionStorage.setItem('contra_flg', $('input:radio[id="contra_flg"]:checked').val());
				sessionStorage.setItem('pay_flg', $('input:radio[id="pay_flg"]:checked').val());
				sessionStorage.setItem('hdy_flg', $('input:radio[id="hdy_flg"]:checked').val());
				sessionStorage.setItem('clientall', $('input:radio[id="clientall"]:checked').val());
				sessionStorage.setItem('del_flg0', $('input:radio[id="delflag"]:checked').val());
				sessionStorage.setItem('del_flg1', $('input:radio[id="delflag1"]:checked').val());
				sessionStorage.setItem('del_flgall', $('input:radio[id="delflagall"]:checked').val());
				
				window.location.href = '../master/suppliers_registration.html?flag=0'+"&cldivcd="+row.cldivcd;
			}
		}
function userchange() {
			sessionStorage.removeItem('cldivcd');
			sessionStorage.removeItem('account_cd');
			sessionStorage.removeItem('divnm');
			sessionStorage.removeItem('client_flg');
			sessionStorage.removeItem('contra_flg');
			sessionStorage.removeItem('pay_flg');
			sessionStorage.removeItem('hdy_flg');
			sessionStorage.removeItem('clientall');
			sessionStorage.removeItem('del_flg0');
			sessionStorage.removeItem('del_flg1');
			sessionStorage.removeItem('del_flgall');
			sessionStorage.removeItem('divname_full')
		  	var row = $('#suppliersListTab').datagrid('getSelected');
		  	if(row == null || row == '') {
				showErrorHandler("NOT_CHOOSE_FAIL","ERROR","ERROR");
				return false;
			}else{
				var client_flg = row.client_flg;
			  	if(client_flg==' '){
			  		showErrorHandler("NO_CLIENT","ERROR","ERROR");
			  		return false;
			  	}
				sessionStorage.setItem('cldivcd', row.cldivcd);
				sessionStorage.setItem('divname_full', row.divnm);
				sessionStorage.setItem('client_flg', row.client_flg);
				
				sessionStorage.setItem('account_cd', $("#account_cd").val());
				sessionStorage.setItem('divnm', $("#divnm").val());
				sessionStorage.setItem('contra_flg', $('input:radio[id="contra_flg"]:checked').val());
				sessionStorage.setItem('pay_flg', $('input:radio[id="pay_flg"]:checked').val());
				sessionStorage.setItem('hdy_flg', $('input:radio[id="hdy_flg"]:checked').val());
				sessionStorage.setItem('clientall', $('input:radio[id="clientall"]:checked').val());
				sessionStorage.setItem('del_flg0', $('input:radio[id="delflag"]:checked').val());
				sessionStorage.setItem('del_flg1', $('input:radio[id="delflag1"]:checked').val());
				sessionStorage.setItem('del_flgall', $('input:radio[id="delflagall"]:checked').val());
				window.location.href = '../master/wedgemembers_change.html?cldivcd='+row.cldivcd;
			}
		
		}
function uploadclmst(){
	//alert('11');
	uploadCsvFile('ClmstUpload','files')
	
}
