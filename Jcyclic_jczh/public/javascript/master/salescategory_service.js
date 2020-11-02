//页面初始化时判断，是否是更新操作，若是更新，需查询数据
var beUseInJobNumber;
var tran_lend;
var lock_flg;
$(function() {
	isCheckLogin(1);
	//页面初始化加载下拉列表
	var sale_cd =urlPars.parm("sale_cd");
	//判断是否为更新操作，若是更新操作需要查询数据
	if(sale_cd!=undefined&&sale_cd!=null&&sale_cd!=""){
		//$('.fs18').attr('name','salescategory_registration_title_ch'); 
		//$('.btn_change').attr('name','salescategoryList_change');   
		$('.fs18').html(part_language_change_new('salescategory_registration_title_ch'))
		$('.fs18').attr('name','salescategory_registration_title_ch');
		$('.btn_change').html(part_language_change_new('salescategoryList_change'))
		$('title').html(part_language_change_new('salescategory_registration_title_ch'))
		$('title').attr('name','salescategory_registration_title_ch');
		
		var sale_cd=urlPars.parm("sale_cd");
		$("#sale_cd").text(sale_cd);
		salemstUpdInit(sale_cd);
	}else{
		$("#updhidden").addClass("hidden")
		initSalescategory();
	}/*else{
		url = "master/salescategory_list.html";
		showInfoMsgHandler("DATA_IS_NOT_EXIST","info","info",url);
		return;
	}*/
	//加载model后，为增值税的dategraid赋值
		$('#myModal').on('shown.bs.modal', function () {
		  // 执行一些动作...
		  //当是你修改的时候，模态框表格才执行查询ajax
		 if(sale_cd!=undefined&&sale_cd!=null&&sale_cd!=""){
		  getVatrate();	
		 }
		
		})
		//加载model后，为shuilv的dategraid赋值
		$('#myModal_T').on('shown.bs.modal', function () {
		  // 执行一些动作...
		   //当是你修改的时候，模态框表格才执行查询ajax
		  if(sale_cd!=undefined&&sale_cd!=null&&sale_cd!=""){
		  getSaleRate();
		 }
		})	
})
//获取共同业务类别下拉选框
function initSalescategory(){
	var sale_cd = $('#sale_cd').text();
	var path = $.getAjaxPath()+"initSalescategory";
	var pars = {"sale_cd":sale_cd};
	$.ajax({
		async:false,
		url:path,
		data:JSON.stringify(pars),
		headers: {"requestID":$.getRequestID(),"requestName":$.getRequestNameByMst()},
		success:function(data){
           if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
           	$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
           
		     //判断是否为登录操作，若是登录操作需要赋值sale_cd
               rateNameByLangTyp(data[$.getRequestDataName()]);
                var jsData = {};
				jsData['common_type'] = data[$.getRequestDataName()]['commontypeList'];
				//setDomChildList(jsData,"cost_foreign_type");
				SelectObj.selectData = jsData;
				var selectID = [];
				selectID.push("common_type");
				SelectObj.setStringFlg='_';
				SelectObj.setSelectID = selectID;
				SelectObj.setSelectOfLog();  
				
           }else{
           	showErrorFunHandler(data[$.getRequestMetaName()].result,"ERROR","ERROR");
           }
       },
       error:function(data){
       		showErrorHandler("CONNECTION_SERVER_ERROR","ERROR","ERROR");
       }
	});
}
function rateNameByLangTyp(saleData)
{
	var sale;
	if(saleData != undefined && saleData != null)
	{
		sale = saleData;
		localStorage.setItem("saleData",JSON.stringify(saleData));
	}else{
		sale = JSON.parse(localStorage.getItem("saleData"));
	}
	
	var languageSelect = $.getLangTyp();
	$('#saleRegistration_tax1').html(sale['tax2Name'+languageSelect]);
	$('#saleRegistration_tax2').html(sale['tax3Name'+languageSelect]);
	initDataGridHandler("salescategory_T",10,"","top",false,"isHasFn",sale);
	initDataGridHandler("salescategory",10,"","top",false,"isHasFn");
	setDomNameByLanguage("salescategory");
	//表格语言国际化  不能onsuccess所以只能初始化改变
	var dom = $('#myModal .datagrid-header .datagrid-htable tr span:not(.datagrid-sort-icon)');
	dom.addClass('i18n');
	dom.eq(0).attr("name", "salescategoryRegistration_application_start_day");
	dom.eq(1).attr("name", "salescategoryRegistration_end_day");
	dom.eq(2).attr("name", "salescategoryRegistration_vlue_added_tax_rate");
	var dom1 = $('#myModal_T .datagrid-header .datagrid-htable tr span:not(.datagrid-sort-icon)');
	dom1.addClass('i18n');
	dom1.eq(0).attr("name", "salescategoryRegistration_application_start_day");
	dom1.eq(1).attr("name", "salescategoryRegistration_end_day");
	dom1.eq(2).attr("name", "salescategoryRegistration_tax1");
	dom1.eq(3).attr("name", "salescategoryRegistration_tax2");
}
//查询增值税历史数据
function getVatrate(){
	var pd = $.getAccountDate();
	var sale_cd = $('#sale_cd').text();
	var path = $.getAjaxPath()+"VatRateSel";
	var pars = {"sale_cd":sale_cd,"pd":pd};
	$.ajax({
		url:path,
		data:JSON.stringify(pars),
		headers: {"requestID":$.getRequestID(),"requestName":$.getRequestNameByMst()},
		success:function(data){
           if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
           	$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
				//拼接一个decide用来判断是否是从数据库查出来的标志位。
				for(var i = 0;i<data[$.getRequestDataName()].length;i++) {
			    data[$.getRequestDataName()][i]['decide']=1;
			    data[$.getRequestDataName()][i]['vat_rate']=floatObj.multiply(data[$.getRequestDataName()][i]['vat_rate'],100);
		        }
				//rateNameByLangTyp(data[$.getRequestDataName()]);
				//主界面增值税开始时间不等于模态框开始时间时，代表用户已经在模态框中新增数据
				if(data[$.getRequestDataName()][0]['start_date']!=$("#vat_start_date").val()){
				}else{
					var total = data[$.getRequestDataName()].length;
					var dataFil = {
					total:total,
					rows:data[$.getRequestDataName()]
					    }
         		  	$('#salescategory').datagrid({loadFilter: pagerFilter}).datagrid('loadData',dataFil);
				}
           }else{
           	showErrorFunHandler(data[$.getRequestMetaName()].result,"ERROR","ERROR");
           }
       },
       error:function(data){
       		showErrorHandler("CONNECTION_SERVER_ERROR","ERROR","ERROR");
       }
	});
}

//查询税率历史数据
function getSaleRate(){
	var pd = $.getAccountDate();
	var sale_cd = $('#sale_cd').text();
	var path = $.getAjaxPath()+"RateSel";
	var pars = {"sale_cd":sale_cd,"pd":pd};
//	console.log(sale_cd);
	$.ajax({
		url:path,
		data:JSON.stringify(pars),
		headers: {"requestID":$.getRequestID(),"requestName":$.getRequestNameByMst()},
		success:function(data){
           if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
           	$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
//				console.log(data[$.getRequestDataName()]);
				for(var i = 0;i<data[$.getRequestDataName()].length;i++) {
			    data[$.getRequestDataName()][i]['decide']=1;
			    data[$.getRequestDataName()][i]['rate2']=floatObj.multiply(data[$.getRequestDataName()][i]['rate2'],100);
			     data[$.getRequestDataName()][i]['rate3']=floatObj.multiply(data[$.getRequestDataName()][i]['rate3'],100);
		        }
			    //主界面增值税开始时间不等于模态框开始时间时，代表用户已经在模态框中新增数据
				if(data[$.getRequestDataName()][0]['start_date']!=$("#start_date").val()){
				}else{
					var total = data[$.getRequestDataName()].length;
					var dataFil = {
					total:total,
					rows:data[$.getRequestDataName()]
					    }
         		  	$('#salescategory_T').datagrid({loadFilter: pagerFilter}).datagrid('loadData',dataFil);
				}
           }else{
           	showErrorFunHandler(data[$.getRequestMetaName()].result,"ERROR","ERROR");
           }
       },
       error:function(data){
       		showErrorHandler("CONNECTION_SERVER_ERROR","ERROR","ERROR");
       }
	});
}

//变更操作时，前置的查询赋值。
function salemstUpdInit(sale_cd){
	var salemst = $.getAccountDate();
	var path = $.getAjaxPath()+"salemstUpdInit";
	var pars = {"sale_cd":sale_cd,"pd": salemst};
	
	$.ajax({
		url:path,
		data:JSON.stringify(pars),
		headers: {"requestID":$.getRequestID(),"requestName":$.getRequestNameByMst()},
		success:function(data){
           if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
           	    
           	    $.resetRequestID(data[$.getRequestUserInfoName()].requestID);
           	    if(data[$.getRequestDataName()]=="3"){
           	    	url = "master/salescategory_list.html";
					showInfoMsgHandler("DATA_IS_NOT_EXIST","info","info",url);
					return;
           	    }
           	    //下拉复制
           	    var jsData = {};
				jsData['common_type'] = data[$.getRequestDataName()]['commontypeList'];
				//setDomChildList(jsData,"cost_foreign_type");
				SelectObj.selectData = jsData;
				var selectID = [];
				selectID.push("common_type");
				SelectObj.setStringFlg='_';
				SelectObj.setSelectID = selectID;
				SelectObj.setSelectOfLog();  
				rateNameByLangTyp(data[$.getRequestDataName()]);
                beUseInJobNumber= data[$.getRequestDataName()]['beUseInJobNumber'];
				$("#sale_account_cd").val(data[$.getRequestDataName()]['sale_account_cd']);
				$("#sale_name").val(data[$.getRequestDataName()]['sale_name']);
				//$("#sale_cd").text(data[$.getRequestDataName()]['sale_cd']);
				$("#vat_start_date").val(data[$.getRequestDataName()]['vat_start_date']);
				$("#vat_end_date").val(data[$.getRequestDataName()]['vat_end_date']);
				$("#start_date").val(data[$.getRequestDataName()]['start_date']);
				$("#end_date").val(data[$.getRequestDataName()]['end_date']);
				$("#vat_rate").val(floatObj.multiply(data[$.getRequestDataName()]['vat_rate'],100));
				$("#rate2").val(floatObj.multiply(data[$.getRequestDataName()]['rate2'],100));
				$("#rate3").val(floatObj.multiply(data[$.getRequestDataName()]['rate3'],100));
				$("#p_vat_rate").val(floatObj.multiply(data[$.getRequestDataName()]['vat_rate'],100));
				$("input[type='radio'][value='"+data[$.getRequestDataName()]['tran_lend']+"']").attr("checked",true);
				tran_lend = data[$.getRequestDataName()]['tran_lend'];
				$("#common_type").find("option[value='"+data[$.getRequestDataName()]['common_type']+"']").attr("selected",true);
				if(data[$.getRequestDataName()]['del_flg']=='1'){
				  $("#del_flg").attr("checked",true);
				}
           		lock_flg =data[$.getRequestDataName()]['lock_flg'];
           }else{
           	showErrorFunHandler(data[$.getRequestMetaName()].result,"ERROR","ERROR");
           }
       },
       error:function(data){
       		showErrorHandler("CONNECTION_SERVER_ERROR","ERROR","ERROR");
       }
	});
}




//共通新增/修改的保存
function saleSave(){
    pars={};
    pars['salemst'] = $.getInputVal();
	pars['salemst']['sale_cd'] = $("#sale_cd").text();
	pars['salemst']['vat_start_date'] = $("#vat_start_date").val();
	pars['salemst']['vat_end_date'] = $("#vat_end_date").val();
	pars['salemst']['start_date'] = $("#start_date").val();
	pars['salemst']['end_date'] = $("#end_date").val();
	pars['salemst']['vat_rate'] = floatObj.divide($("#vat_rate").val(),100);
	pars['salemst']['rate2']= floatObj.divide($("#rate2").val(),100);
	pars['salemst']['rate3']= floatObj.divide($("#rate3").val(),100);
   	pars['salemst']['common_type']=  $("#common_type ").val();
   	pars['salemst']['sale_account_cd']=  $("#sale_account_cd").val();
   	 if($("#del_flg").prop("checked")==true)
   {
   		if(Number(beUseInJobNumber)>0){
		showErrorHandler('PAYEE_TYP_MUCH','info','info');
		return;
	 	}
     	pars['salemst']['del_flg'] = 1;
     
   }else{
   	 pars['salemst']['del_flg']=0;
   }
   	pars['salemst']['lock_flg']=lock_flg;
   	pars['lock_flg']=lock_flg;
   //下面是执行的ajax。由于变更和追加数据都一样，只是借口不一样
	var urlsale_cd =urlPars.parm("sale_cd");
	//var pd = "$('#pd').val();
	//url路径修改
	if(urlsale_cd!=undefined&&urlsale_cd!=null&&urlsale_cd!=""){//参数sale_cd有值时，为更新
		    if(tran_lend==1&&pars['salemst']['tran_lend']==0){
			 showErrorHandler('TRAN_LEND_VALIDATE','info','info');
			 return;
			}else{
				var path = $.getAjaxPath()+"salemstUpd";
			}
	}else{//当参数pd为1的时候为变更。
		var path = $.getAjaxPath()+"salemstAdd";
	}/*else{
		url = "master/salescategory_list.html";
		showInfoMsgHandler("DATA_IS_NOT_EXIST","info","info",url);
		return;
	}*/
		
	if(!validataRequired())
	{
		return ;
	}
	$.ajax({
		url:path,
		data:JSON.stringify(pars),
		headers: {"requestID":$.getRequestID(),"requestName":$.getRequestNameByMst()},
		success:function(data){
             if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
           		 $.resetRequestID(data[$.getRequestUserInfoName()].requestID);
	           	if(data[$.getRequestDataName()]==3){
	           		getRedc($("#sale_account_cd"),true);
	           		showErrorHandler('SALE_ACCOUNT_CODE_SAM','info','info');	
	           	}else if(data[$.getRequestDataName()]==2){
	           		 showLockInfoMsgHandler("STATUS_VALIDATEPOWER_ERROR","master/salescategory_list.html");
	           	}else if(data[$.getRequestDataName()]==1){
			    	url = $.getJumpPath()+"master/salescategory_list.html";//此处拼接内容
			        window.location.href = url;	
	           	}else{
				 	 	 showErrorHandler("EXECUTE_FAIL","error","error");
				 	 }
	        }else{
           		showErrorFunHandler(data[$.getRequestMetaName()].result,"ERROR","ERROR");
            }
       },
       error:function(data){
       		showErrorHandler("CONNECTION_SERVER_ERROR","ERROR","ERROR");
       }
	});
}







