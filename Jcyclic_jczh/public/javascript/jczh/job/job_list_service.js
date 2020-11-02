var menuNode=[];
var skip={};
var userID ='';
var pointNumber ='';
var taxFormatFlg='';
var foreignFormatFlg='';
var recflglanguage=[];
var jsflaglanguage=[];
var reqflglanguage=[];
var invflglanguage=[];
var assignflglanguage=[];
var costfinishflglanguage=[];
var view="";
var userinfo=[];
var rjcl="";
var rjcl_falg="";
//页面加载后执行的全部检索
$(function() {
	if(urlPars.parm("view") == 'init' || urlPars.parm("view") == 'job'){
		$("#setb2").click();
	}
	//默认删除label弹窗的icon
	$('.iconfont.icon-sheding1').addClass('hidden');
	$('#ddd_job').datebox({
		onSelect:function(beginDate){
			$.dateLimit('d1_job', beginDate, 1);
		}
	});
	//lable追加
	$('.add_lable').click(function() {
        listLableAdd("job_login_list",0,0);		
	});
	$(".lable-set").click(function(){
		listLableInit("job_login_list");
	})
	//lable 追加初始化
	$("#zj").click(function() {
		lableAddInit("job_login_list");
	});
	//多选lable 追加初始化
	$("#zjM").click(function() {
		lableAddInit("job_login_list");
	});
	//lable设定
	$('#set').click(function() {
		listLableSet("job_login_list",0);
	});
	//lable模糊查询
	$(".filter-lable").click(function() {
		var str = $("#lableStr").val();
		filterLable(str, 1);
	});
	var surl =$.getJumpPath()+"common/suppliers_retrieval.html";
	$.layerShow("searchClient",surl);
	$.layerShow("searchHDY",surl);
	$.layerShow("searchPay",surl);
	var eurl =$.getJumpPath()+"common/employ_retrieval.html";
	$.layerShow("searchDD",eurl);
	//divflag更改后，担当input操作
	$('#ddflag').change(function(){
		//添加所需要执行的操作代码
		 if($("#ddflag").prop("checked")==true){
				$('#ddname').val($.getUserName);
				$('#ddcd').val($.getUserID);
				}else{
				$('#ddname').val('');
				$('#ddcd').val('');
				}
	});
	$('#invoiceNo').on('keyup',function (event,value) {
		$(this).val($(this).val().replace(/[^\d\,a-zA-Z]/g,''))
	});
	//first comma bug solve
	$('#invoiceNo').on('input',function(event,value){
		$(this).val($(this).val().replace(/^\,/g,''))
	})
	// $('#invoiceNo').on('input',function () {
	// 	var reg = /^(([\da-zA-Z]{1,20}),?(,[\da-zA-Z]{1,20})*,?),*$/;
	// 	var value = $(this).val();
	// 	if (reg.test(value)){
	// 		var validateSpecialString = value.split(',')[1];
	// 		var validateSpecialStringT = value.split(',')[2];
	// 		if (validateSpecialString == '' && (validateSpecialStringT != '' && validateSpecialStringT != undefined)){
	// 			showErrorHandler("NOT_EMPTY", "ERROR", "ERROR");
	// 		}
	// 	}else {
	// 		showErrorHandler("NOT_EMPTY", "ERROR", "ERROR");
	// 	}
	// })
	// $('#invoiceNo').on('blur',function () {
	// 	var reg = /^(([\da-zA-Z]{1,20})(,[\da-zA-Z]{1,20})*)$/;
	// 	var value = $(this).val();
	// 	if (reg.test(value)){
	// 		//code ...
	// 	}else {
	// 		$(this).val('');
	// 	}
	// })
/*	//当营业担当获得焦点时，担当flag不可选择
	$('#ddname').focus(function(){
		//添加所需要执行的操作代码
		$('#ddflag').attr("disabled",true);
		$('#ddcd').val('');
	});*/
	//当营业担当失去焦点时，
/*	$('#ddname').blur(function(){
	    //若input中有值，担当flag不可选择
		if($('#ddname').val()!=null&&$('#ddname').val()!=''){
		$('#ddflag').attr("disabled",true);	
		}else{
		$('#ddflag').attr("disabled",false);		
		}
	});*/
	$('#ddname').change(function(){
		//添加所需要执行的操作代码
		var ddname =$('#ddname').val();
		var ddcd =$('#ddcd').val();
		//当担当名不等于登录名时，不是查询本人，担当flag取消勾选
		if(ddname!=$.getUserName){
			$("#ddflag").prop("checked",false);
		}
		//当担当cd不等于登录登录cd，不是查询本人，担当flag取消勾选
		else if(ddcd!=$.getUserID){
		    $("#ddflag").prop("checked",false);
		}else{
			$("#ddflag").prop("checked",true);
		}
	});
	/*//请求发票，状态判断
	$('#invpd').change(function(){
		//添加所需要执行的操作代码
		var	dlvfalg=$('#dlvfalg').val();
		if(dlvfalg=="002"){
			showErrorHandler('DLVFALG_VALIDATION','info','info');
		}
	});
	//入金状态检索框判断
	$('#rjpd').change(function(){
		//添加所需要执行的操作代码
		var	dlvfalg=$('#dlvfalg').val();
		if(dlvfalg=="002"){
			showErrorHandler('DLVFALG_VALIDATION','info','info');
		}
	});*/
	
	//检索条件入力框获得焦点时，清空id（得意先，相手先G会社，請求先）
	$('#cldiv_name').focus(function(){
		//添加所需要执行的操作代码
		$('#cldiv_cd').val('');
	});
	$('#g_company_name').focus(function(){
		//添加所需要执行的操作代码
		$('#g_company').val('');
	});
	$('#payer_name').focus(function(){
		//添加所需要执行的操作代码
		$('#payer_cd').val('');
	});
	isCheckLogin(1);
		//请求发票，状态判断
	$('#invpd').change(function(){
		//添加所需要执行的操作代码
		remberSelect();
	});
	//入金状态检索框判断
	$('#rjpd').change(function(){
		//添加所需要执行的操作代码
		remberSelect();
	});
	//计上状态检索框判断
	$('#dlvfalg').change(function(){
		//添加所需要执行的操作代码
		remberSelect();
	});
	//计上状态检索框判断
	$('#assign_flg').change(function(){
		//添加所需要执行的操作代码
		remberSelect();
	});
	//计上状态检索框判断
	$('#cost_finish_flg').change(function(){
		//添加所需要执行的操作代码
		remberSelect();
	});
	//売上種目
	$('#sale_cd').change(function(){
		//添加所需要执行的操作代码
		remberSelect();
	});
	
	//检索下拉框切换语言记录选项
	function remberSelect(){
	
			var	costSelect_sale_cd = $('#sale_cd').val();
			var	costSelect_cost_finish_flg = $('#cost_finish_flg').val();
			var costSelect_assign_flg = $("#assign_flg").val();
			var costSelect_dlvfalg = $("#dlvfalg").val();
			var costSelect_rjpd = $("#rjpd").val();
			var costSelect_invpd = $("#invpd").val();
			var obj={"costSelect_sale_cd":costSelect_sale_cd,"costSelect_cost_finish_flg":costSelect_cost_finish_flg,"costSelect_assign_flg":costSelect_assign_flg,"costSelect_dlvfalg":costSelect_dlvfalg,"costSelect_rjpd":costSelect_rjpd,"costSelect_invpd":costSelect_invpd};
			
			SelectObj.setDefaultData=obj;
			//SelectObj.setStringFlg="_";
			SelectObj.setSelectID = ['dlvfalg','assign_flg','invpd','cost_finish_flg','rjpd','sellInvoiceflg'];
	//SelectObj.setSelectOfLog();
}
	
	//判断是否是从倒还栏跳转到job一览中。
	 view = urlPars.parm("view");
	
	 if(view!="init"&&view!="cost"&&view!="top"&&view!="job"&&view!="jobdetailsearch"){
	 	var backPage= urlPars.parm("backPage");
	 	if(backPage=="checkOut"){
	 		url = "jczh/monthbalance.html";
	 	}else{
	 		url = "jczh/top_registration.html";
	 	}
		showInfoMsgHandler("DATA_IS_NOT_EXIST","info","info",url);
		return;
	 }
	//检索条件判断
	var pars =JSON.parse(sessionStorage.getItem('pars'));
	var sideBar = urlPars.parm("sideBar");
	var parsList = jumpPageSearchList(view);
	if(view == "top" && parsList.keyword != "")
	 {    //从top页面跳转到job有一览，session重新赋值，记录检索条件
		  sessionStorage.setItem('pars',JSON.stringify(parsList));
		  $('#keyword').val(parsList.keyword);
		  //之前不好使没有这个方法不执行查询  刘实加
		  searchJoblistInit(parsList,parsList['searchFlg']);
	 }else if(view == "job"){
	 	 //从top/和月结页面跳转到job有一览，session重新赋值，记录检索条件
	 	 var dlvfalg= parsList['dlvfalg'];
	 	 var dlvday= parsList['dlvday'];
	 	 var cost_finish_flg= parsList['cost_finish_flg'];
	 	 var dlvfalgtest = /^[0-9]*$/;
	 	 var dlvdaytest = /^(?:(?!0000)[0-9]{4} -(?:(?:0[1-9]|1[0-2]) -(?:0[1-9]|1[0-9]|2[0-8]) |(?:0[13-9]|1[0-2]) -(?:29|30) |(?:0[13578]|1[02]) -31 )|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00) -02 -29 )$/;
	 	 
	 	 //验证url中的计上状态的值一定为数字
	 	 if(dlvfalg!=undefined&&dlvfalg!=null){
	 	 	if(!dlvfalgtest.test(dlvfalg)){
	 	 	var backPage= urlPars.parm("backPage");
		 	if(backPage=="checkOut"){
		 		url = "jczh/monthbalance.html";
		 	}else{
		 		url = "jczh/top_registration.html";
		 	}
			showInfoMsgHandler("DATA_IS_NOT_EXIST","info","info",url);
			return;
	 	 	}
	 	 }
	 	
	 	  //验证url中的计上状态的值一定为数字
	 	 if(cost_finish_flg!=undefined&&cost_finish_flg!=null){
	 	 	if(!dlvfalgtest.test(cost_finish_flg)){
	 	 	var backPage= urlPars.parm("backPage");
		 	if(backPage=="checkOut"){
		 		url = "jczh/monthbalance.html";
		 	}else{
		 		url = "jczh/top_registration.html";
		 	}
			showInfoMsgHandler("DATA_IS_NOT_EXIST","info","info",url);
			return;
	 	 	}
	 	 }
	 	 //验证url中传入的dlvday是否是时间格式
	 	 if(dlvday!=undefined&&dlvday!=null){
	 	 	if(!isNaN(dlvday)||isNaN(Date.parse(dlvday))){
　　			var backPage= urlPars.parm("backPage");
		 	if(backPage=="checkOut"){
		 		url = "jczh/monthbalance.html";
		 	}else{
		 		url = "jczh/top_registration.html";
		 	}
			showInfoMsgHandler("DATA_IS_NOT_EXIST","info","info",url);
			return;
          }
	 	 }
	 	 // 校验dlvday长度 之前没有判断dlvday是否为undefined 会报错
	 	 if(dlvday!=undefined&&dlvday!=null){
	 	 	 if(dlvday.length>7){
	 	 	if(backPage=="checkOut"){
		 		url = "jczh/monthbalance.html";
		 	}else{
		 		url = "jczh/top_registration.html";
		 	}
			showInfoMsgHandler("DATA_IS_NOT_EXIST","info","info",url);
			return;
	 	 }
	 	 }
	 	
	 	 
		 sessionStorage.setItem('pars',JSON.stringify(parsList));
		 searchJoblistInit(parsList,parsList['searchFlg']);
	 }else if(sideBar=="sideBar"||pars==null){
	   searchJoblistInit(parsList);  	
	 }else{
	 	if(pars['searchFlg']!=""){
	 	 searchJoblistInit(pars,pars['searchFlg']);  		
	 	}else{
	 	 searchJoblistInit(parsList);  		
	 	}
	 	
	 };
	
})
function searchJoblistInit (parList,selflg){
	if(selflg=='jobdetailsearch'){
		var pars =JSON.parse(sessionStorage.getItem('pars'));
		if($.getDepartID()=="003"){
			pars.assign_flg = "001"
		}
		pars['searchFlg']=selflg;
	}else if(selflg=='keyword'){
		  var pars =JSON.parse(sessionStorage.getItem('pars'));
		  $('#keyword').val(pars['keyword']);
		  $("#setb1").click();
	}else if(selflg=="job"){//top页面跳转到job一览，判断取值
		 var pars = parList;
	}
	else{
		var pars = parList;
		sessionStorage.removeItem('pars');
	}
	var path = $.getAjaxPath()+"searchJoblistInit";
	$.ajax({
	url:path,
	data:JSON.stringify(pars),
	headers: {"requestID":$.getRequestID(),"requestName":$.getRequestNameByJcZH()},
	success:function(data){
       if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
			$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
			userinfo=data['userInfo'];
			if(data[$.getRequestDataName()]['validatePower']==='0'){
				 var url = "jczh/top_registration.html";
				 showInfoMsgHandler("SYS_VALIDATEPOWER_ERROR","info","info",url);
				 return;
			}
			
			var dataColumns=data[$.getRequestDataName()]['columns'];
       		//动态加载表头
       		if(dataColumns[0].length==0||dataColumns[1].length==0||dataColumns[2].length==0||dataColumns[3].length==0){
       			showErrorHandler('CHOOSE_COLUMNS','info','info')
       		}else{
       		  initDataGridHandler('job_login_list',dataColumns[0][0]['page'],null,'top',true,'isHasFn',dataColumns,false,true);	
       		}
       		
			//下拉框赋值  売上種目
			setChildList(data[$.getRequestDataName()]['sale'], "sale_cd");
			labelToMySelect(data[$.getRequestDataName()]['lableall']);
			SelectObj.mstLableList = data[$.getRequestDataName()]['lableall'];
			var list = data[$.getRequestDataName()];
			list['dlvfalg'] = list['dlvfalg'];
			list['assign_flg'] = list['assignflg'];
			list['invpd'] = list['invpd'];
			list['cost_finish_flg'] = list['costfinishflg'];
			list['rjpd'] = list['rjpd'];
			SelectObj.selectData = list;
			SelectObj.setSelectID = ['dlvfalg','assign_flg','invpd','cost_finish_flg','rjpd','sellInvoiceflg'];
			SelectObj.setStringFlg='';
			SelectObj.setSelectOfLog();
       		if(data.data.TooMuchData!=undefined && data.data.TooMuchData==0){
				$('.switch_table').css('display','none');
				$('.switch_table + .switch_table_none').removeClass('hidden');
       			$('.switch_table_none center').text(part_language_change_new("TABLE_CHECK_NO"));
				$('.switch_table_none center').attr("name","TABLE_CHECK_NO");
				$('.switch_table_none center').attr("class","i18n");
				 showErrorHandlerActive('TOO_MUCH_DATA_HEAD',2000,'TOO_MUCH_DATA_TAIL','info','info')
      		   return ;
      	 }
			//把本国货币小数点位数赋值到全局变量中
			pointNumber = data['userInfo']['pointNumber'];
			taxFormatFlg = data[$.getRequestDataName()]['taxFormatFlg'];
			foreignFormatFlg = data[$.getRequestDataName()]['foreignFormatFlg'];
			//一览表示用
			var dlvfalg_data = data[$.getRequestDataName()]['dlvfalg_data'];
			var invpd = data[$.getRequestDataName()]['invpd_data'];
			var rjpd = data[$.getRequestDataName()]['rjpd_data'];
			//表格赋值
			var total = data[$.getRequestDataName()]['joblist'].length;
			var joblist =data[$.getRequestDataName()]['joblist'];
       		jsflaglanguage =data[$.getRequestDataName()]['jsflaglanguage'];
       		reqflglanguage =data[$.getRequestDataName()]['reqflglanguage'];
       		recflglanguage =data[$.getRequestDataName()]['recflglanguage'];
       		invflglanguage =data[$.getRequestDataName()]['invflglanguage'];
       		assignflglanguage=data[$.getRequestDataName()]['assignflglanguage'];
			costfinishflglanguage=data[$.getRequestDataName()]['costfinishflglanguage'];
			var joblist =data[$.getRequestDataName()]['joblist'];
			var initselect =data[$.getRequestDataName()]['initselect'];
			//循环每一条数据，计算税金，和营业额等
			for(var i=0;i<total;i++){
				var planSale= data[$.getRequestDataName()]['joblist'][i]['plansale'];     //预计卖上金额
				var saleAmt= data[$.getRequestDataName()]['joblist'][i]['saleamt'];      //实际卖上金额
				var planCost= data[$.getRequestDataName()]['joblist'][i]['plancostamt'];    //预计成本合计
				var costTotal= data[$.getRequestDataName()]['joblist'][i]['costTotalAmt'];     //实际成本合计
				var reqAmt= data[$.getRequestDataName()]['joblist'][i]['reqamt'];        //请求金额
				var payAmt= data[$.getRequestDataName()]['joblist'][i]['payAmtSum'];       //支付金额
				var saleVatAmt=  data[$.getRequestDataName()]['joblist'][i]['vatamt'];   //卖上增值税
				var costVatAmt= data[$.getRequestDataName()]['joblist'][i]['costVatTotal'];    //仕入增值税（成本增值税）
				var rate=  data[$.getRequestDataName()]['joblist'][i]['rate2'];         //文化税率
				var rate1= data[$.getRequestDataName()]['joblist'][i]['rate3'];         //附加税率
				var isCostFinsh=  data[$.getRequestDataName()]['joblist'][i]['costfinishflg'];  //是否成本录入终止，0：未终止;1：终止
				var costCountNums = joblist[i]['costnum']//成本条数
				//格式化job登录时间
				joblist[i]["adddate"]=new Date(joblist[i]["adddate"]).format_extend('yyyy-MM-dd');
				joblist[i]["saleadddate"]=new Date(joblist[i]["saleadddate"]).format_extend('yyyy-MM-dd');
				joblist[i]["saleadmitdate"]=new Date(joblist[i]["saleadmitdate"]).format_extend('yyyy-MM-dd');
				 /* var assignflglanguage = getGridLanguage("assignflg",joblist[i]["assignflg"]);//担当割当状态
				  var reqflglanguage = getGridLanguage("reqflg",joblist[i]["reqflg"]);	//请求状态
				  var recflglanguage = getGridLanguage("recflg",joblist[i]["recflg"]);	//入金状态
				  var invflglanguage = getGridLanguage("invflg",joblist[i]["invflg"]);//发票状态
				  var costendflglanguage = getGridLanguage("costfinishflg",joblist[i]["costfinishflg"]);//原価完了状态
				  joblist[i]["assignflglanguage"]=assignflglanguage;
				  joblist[i]["reqflglanguage"]=reqflglanguage;
				  joblist[i]["recflglanguage"]=recflglanguage;
				  joblist[i]["invflglanguage"]=invflglanguage;
				  joblist[i]["costendflglanguage"]=costendflglanguage;*/
				
			   //成本条数小于0，仕入增值税合计等于预计仕入增值税合计
					if(costCountNums<=0){
			  	            //没有成本时，计算用仕入增值税等于预计增值税
							if(isCostFinsh==0){//成本未终了
							//仕入增值税（成本增值税）
							data[$.getRequestDataName()]['joblist'][i]['costVatTotal'] =data[$.getRequestDataName()]['joblist'][i]['plancosttax']//仕入增值税合计等于预计仕入增值税合计
							//实际成本合计
							data[$.getRequestDataName()]['joblist'][i]['costTotalAmt'] =data[$.getRequestDataName()]['joblist'][i]['plancostamt']//原价合计等于预计原价合计
							costVatAmt= joblist[i]['plancosttax'];//仕入增值税（成本增值税）
							costTotal=planCost;//实际成本等于预计成本
							payAmt=joblist[i]['planpayamt'];//实际支付额等于预计支付额
							}else{
							//仕入增值税（成本增值税）
							data[$.getRequestDataName()]['joblist'][i]['costVatTotal'] ="0.00"
							//实际成本合计
							data[$.getRequestDataName()]['joblist'][i]['costTotalAmt'] ="0.00"
							costVatAmt= "0.00";//仕入增值税（成本增值税）
							costTotal="0.00";//实际成本等于预计成本
							payAmt="0.00";//实际支付额等于预计支付额
							}
					}
			   /* //没做支付登录
			    if(joblist[i]['status']!="1"&&joblist[i]['status']!="2"&&joblist[i]['status']!="3"&&joblist[i]['status']!="4"){
			    payAmt=joblist[i]['planpayamt']
			    }*/
			    //如果没做壳上登录
			    if(data[$.getRequestDataName()]['joblist'][i]['seladduser']==null||data[$.getRequestDataName()]['joblist'][i]['seladduser']==''){
			    saleAmt='';
			    }
			    
			    
			    
			    if(joblist[i]["fromjpp"]=="1"){
					joblist[i]['tax2']=0;//税金2
					joblist[i]['tax3']=0//税金3
					var vatamt = joblist[i]['plansaletaxtotal'];
					if(data[$.getRequestDataName()]['joblist'][i].accountflg!=0){
				        var conf = calTax(planSale,saleAmt,planCost,costTotal,reqAmt,payAmt,saleVatAmt,
						costVatAmt,data[$.getRequestDataName()]['joblist'][i]['historyrate2'],data[$.getRequestDataName()]['joblist'][i]['historyrate3'],isCostFinsh,costCountNums,pointNumber,taxFormatFlg,foreignFormatFlg,vatamt);
				    }else{
				        var conf = calTax(planSale,saleAmt,planCost,costTotal,reqAmt,payAmt,saleVatAmt,
						costVatAmt,rate,rate1,isCostFinsh,costCountNums,pointNumber,taxFormatFlg,foreignFormatFlg,vatamt);
				    }
				}else{
				    if(data[$.getRequestDataName()]['joblist'][i].accountflg!=0){
				        var conf = calculateTaxHandler(planSale,saleAmt,planCost,costTotal,reqAmt,payAmt,saleVatAmt,
						costVatAmt,data[$.getRequestDataName()]['joblist'][i]['historyrate2'],data[$.getRequestDataName()]['joblist'][i]['historyrate3'],isCostFinsh,costCountNums,pointNumber,taxFormatFlg,foreignFormatFlg);
				    }else{
				        var conf = calculateTaxHandler(planSale,saleAmt,planCost,costTotal,reqAmt,payAmt,saleVatAmt,
						costVatAmt,rate,rate1,isCostFinsh,costCountNums,pointNumber,taxFormatFlg,foreignFormatFlg);
				    }
				    joblist[i]['tax2']=conf['tax'];//税金2
					joblist[i]['tax3']=conf['tax1']//税金3
				}
				data[$.getRequestDataName()]['joblist'][i]['plantaxTotal']=conf['taxTotal']//预计税金合计
				data[$.getRequestDataName()]['joblist'][i]['planprofitRate']=conf['profitRate']//预计税金合计	
				data[$.getRequestDataName()]['joblist'][i]['planprofit']=conf['profit']//预计税金合计	
			   //job未终了，税金实时计算，终了时，税金从数据库取出
			   /* if(isCostFinsh=='0'){//未终了*/
					
					data[$.getRequestDataName()]['joblist'][i]['taxTotal']=conf['taxTotal']//税金3
					data[$.getRequestDataName()]['joblist'][i]['profit']=conf['profit']//营业合计
					data[$.getRequestDataName()]['joblist'][i]['profitRate']=conf['profitRate']//营业率
			  	/*}else{
					data[$.getRequestDataName()]['joblist'][i]['profit']=conf['profit']//营业合计
					data[$.getRequestDataName()]['joblist'][i]['profitRate']=conf['profitRate']//营业率
			    }*/
			  	//格式化表格数字科学计数法
				joblist[i]["saleamt"]=formatNumber(joblist[i]["saleamt"]);
				joblist[i]["vatamt"]=formatNumber(joblist[i]["vatamt"]);
				joblist[i]["reqamt"]=formatNumber(joblist[i]["reqamt"]);
				joblist[i]["plancostamt"]=formatNumber(joblist[i]["plancostamt"]);
				joblist[i]["costTotalAmt"]=formatNumber(joblist[i]["costTotalAmt"]);
				joblist[i]["costVatTotal"]=formatNumber(joblist[i]["costVatTotal"]);
				joblist[i]["tax2"]=formatNumber(joblist[i]["tax2"]);
				joblist[i]["tax3"]=formatNumber(joblist[i]["tax3"]);
				joblist[i]["taxTotal"]=formatNumber(joblist[i]["taxTotal"]);
				joblist[i]["profit"]=formatNumber(joblist[i]["profit"]);
				joblist[i]["profitRate"]=joblist[i]["profitRate"]+"%";
				joblist[i]["plansale"]=formatNumber(joblist[i]["plansale"]);
				var oldcldivcd = joblist[i].cldivcd;
				joblist[i].cldivcd= joblist[i].accountcd;
				joblist[i].accountcd = oldcldivcd;
			 	}
				//一览状态位
				var joblist = loopFun(joblist,[jsflaglanguage,reqflglanguage,recflglanguage,invflglanguage,assignflglanguage,costfinishflglanguage],['jsflag','reqflg','recflg','invflg','assignflg','costfinishflg']);
				joblist = lableSpan(joblist);
				var dataFil = {
					total:total,
					rows:joblist
				}
				if(dataFil.total == 0){
					$('.switch_table').css('display','none');
					$('.switch_table + .switch_table_none').removeClass('hidden');
					$('.switch_table_none center').text(part_language_change_new("TABLE_CHECK_NO_T"));
					$('.switch_table_none center').attr("name","TABLE_CHECK_NO_T");
					$('.switch_table_none center').attr("class","i18n");
					if(initselect == 0){
						$('.switch_table_none center').text(part_language_change_new("TABLE_CHECK_NO"));
						$('.switch_table_none center').attr("name","TABLE_CHECK_NO");
						$('.switch_table_none center').attr("class","i18n");
						
					}
				}else{
					$('#job_login_list').datagrid({loadFilter: pagerFilter}).datagrid('loadData',dataFil);
					tableHidden();
				}				
				//合计计算
				sumsale(data[$.getRequestDataName()]['joblist'],'saleamt');
		        sumsale(data[$.getRequestDataName()]['joblist'],'taxTotal');
		        sumsale(data[$.getRequestDataName()]['joblist'],'plancostamt');
		        sumsale(data[$.getRequestDataName()]['joblist'],'costTotalAmt');
		        sumsale(data[$.getRequestDataName()]['joblist'],'profit');
		        var profit= $("#profitsum").text();
		        var saleamt= $("#saleamtsum").text();
		        //var profitRate =pointFormatHandler( floatObj.multiply(floatObj.divide(recoveryNumber(profit),recoveryNumber(saleamt)),100),3,2);
		        //var profitRate =pointFormatHandler(Number(recoveryNumber(profit)) / Number(recoveryNumber(saleamt))*100,foreignFormatFlg,pointNumber);
		        var profitRate =pointFormatHandler( floatObj.divide(recoveryNumber(profit),recoveryNumber(saleamt)),3,4);
		        profitRate = formatNumber(floatObj.multiply(profitRate,100),2,false);
		        $("#profitRatesum").text(profitRate+"%");
			    
			    //製作部門，勾选担当flag
			    var department =data[$.getRequestDataName()]['department'][0]['departcd'];
				if(department=='004'){
					$("#ddflag").prop("checked",true);
					$('#ddname').attr("disabled",true);
					$('#ddname').val($.getUserName);
					 $('#ddcd').val($.getUserID);
				}
				//当营业部门登录时，默认勾选，担当flag不可操作。担当选择框，不可操作。
				if(department=='005'||department=='006'||department=='007'){
					$("#ddflag").prop("checked",true);
					$("#ddflag").attr("disabled",true);
					$('#ddname').attr("disabled",true);
					$('#ddname').val($.getUserName);
			      	$('#ddcd').val($.getUserID);
					$('#searchDD').unbind("click");
				}
	            //menu权限组复制到页面隐藏域
	            menuNode=data['userInfo']['uNodeList'];
	            skip=data[$.getRequestDataName()]['skip'];
	            if(selflg=="jobdetailsearch"||view=="job"||selflg == "job"){
	            	var pars =JSON.parse(sessionStorage.getItem('pars'));
	            	$("#ddcd").val(pars['ddcd']);
					$("#g_company").val(pars['g_company']);//相手先g会社编号
					$("#payer_cd").val(pars['payer_cd']);
					$("#cldiv_cd").val(pars['cldiv_cd']);
					if(pars['dlvday']!=undefined&&pars ['dlvday']!=""){
					$("#ddda_job").datebox('setValue',pars['dlvday']);
					$("#option2").click();
					}
					if(pars['dlvday']!=undefined&&pars['dlvmon_sta']!=""){
					$("#ddd_job").datebox('setValue',pars['dlvmon_sta']);	
					}
					if(pars['dlvday']!=undefined&&pars['dlvmon_end']!=""){
					$("#d1_job").datebox('setValue',pars['dlvmon_end']);	
					}
					if(pars['dlvday']!=undefined&&pars['ddflag'] ==1)
				    {
				       $("#ddflag").attr("checked",true);
				    }else{
				   	   $("#ddflag").attr("checked",false);
				    }
					$('#sellInvoiceflg').val(pars["sellInvoiceFlg"]);
				    $("#assign_flg").val(pars["assign_flg"]);
					$("#cldiv_name").val(pars["cldiv_name"]);
					$("#cost_finish_flg").val(pars["cost_finish_flg"]);
					$("#ddflag").val(pars["ddflag"]);
					$("#ddname").val(pars["ddname"]);
					$("#dlvfalg").val(pars["dlvfalg"]);
					$("#g_company_name").val(pars["g_company_name"]);
					$("#invpd").val(pars["invpd"]);
					$("#job_cd").val(pars["job_cd"]);
					$("#job_name").val(pars["job_name"]);
					$("#keyword").val(pars["keyword"]);
					$("#label_text").val(pars["label_text"]);
					$("#payer_name").val(pars["payer_name"]);
					$("#rjpd").val(pars["rjpd"]);
					$("#sale_cd").val(pars["sale_cd"]);
					$("#searchFlg").val(pars["searchFlg"]);
					$('#keyword').val('');
	            }

	    		if($.getDepartID()=="003"){
	    			$("#assign_flg").val("001");
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
//详细检索
function selectDetail(){
		var pars = $.getInputVal();
		pars['ddcd'] = $("#ddcd").val();
		pars['g_company'] = $("#g_company").val();//相手先g会社编号
		pars['payer_cd'] = $("#payer_cd").val();
		pars['cldiv_cd'] = $("#cldiv_cd").val();
		pars['sellInvoiceFlg'] = $('#sellInvoiceflg').val();
		if($("#jzy").hasClass("active")){
			pars['dlvday'] = $("input[name='dlvday']").val();
		}else{
			pars['dlvmon_sta'] = $("input[name='dlvmon_sta']").val();
			pars['dlvmon_end'] = $("input[name='dlvmon_end']").val();
		}
		pars['jInvNo'] = $('#jobInvoiceNo').val();
		if($("#ddflag").prop("checked")==true)
	    {
	        pars['ddflag'] = 1;
	    }else{
	   	    pars['ddflag']=0;
	    }
	    pars['searchFlg'] ="jobdetailsearch";
	    pars['condition']=seachCondition(pars);
        sessionStorage.setItem('pars',JSON.stringify(pars));
        
	var path = $.getAjaxPath()+"searchJobList";	
	$.ajax({
		url:path,
		data:JSON.stringify(pars),
		headers: {"requestID":$.getRequestID(),"requestName":$.getRequestNameByJcZH()},
		success:function(data){
//			console.log(data);
			$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
           if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
        	   if(data.data.TooMuchData!=undefined && data.data.TooMuchData==0){
        		   showErrorHandlerActive('TOO_MUCH_DATA_HEAD',2000,'TOO_MUCH_DATA_TAIL','info','info')
        	          		   return ;
        	          	   }
           		//数据填充到表格中
           		var joblist =data[$.getRequestDataName()]['joblist'];
           		jsflaglanguage =data[$.getRequestDataName()]['jsflaglanguage'];
           		reqflglanguage =data[$.getRequestDataName()]['reqflglanguage'];
           		recflglanguage =data[$.getRequestDataName()]['recflglanguage'];
           		invflglanguage =data[$.getRequestDataName()]['invflglanguage'];
           		assignflglanguage=data[$.getRequestDataName()]['assignflglanguage'];
				costfinishflglanguage=data[$.getRequestDataName()]['costfinishflglanguage'];
           		labelToMySelect(data[$.getRequestDataName()]['lableall']);
           		SelectObj.mstLableList = data[$.getRequestDataName()]['lableall'];
           		var total = data[$.getRequestDataName()]['joblist'].length;
           		var joblist = data[$.getRequestDataName()]['joblist'];
           		var initselect =data[$.getRequestDataName()]['initselect'];
           		for(var i=0;i<total;i++){
					var planSale= joblist[i]['plansale'];     //预计卖上金额
					var saleAmt= joblist[i]['saleamt'];      //实际卖上金额
					var planCost= joblist[i]['plancostamt'];    //预计成本合计
					var costTotal= joblist[i]['costTotalAmt'];     //实际成本合计
					var reqAmt= joblist[i]['reqamt'];        //请求金额
					var payAmt= joblist[i]['payAmtSum'];       //支付金额
					var saleVatAmt=  joblist[i]['vatamt'];   //卖上增值税
					var costVatAmt= joblist[i]['costVatTotal'];    //仕入增值税（成本增值税）
					var rate=  joblist[i]['rate2'];         //文化税率
					var rate1= joblist[i]['rate3'];         //附加税率
					var isCostFinsh=  joblist[i]['costfinishflg'];  //是否成本录入终止，0：未终止;1：终止	
					var costCountNums = joblist[i]['costnum']//成本条数//成本条数
					//格式化job登录时间
					joblist[i]["adddate"]=new Date(joblist[i]["adddate"]).format_extend('yyyy-MM-dd');
					joblist[i]["saleadddate"]=new Date(joblist[i]["saleadddate"]).format_extend('yyyy-MM-dd');
					joblist[i]["saleadmitdate"]=new Date(joblist[i]["saleadmitdate"]).format_extend('yyyy-MM-dd');
					var oldcldivcd = joblist[i].cldivcd;
					joblist[i].cldivcd= joblist[i].accountcd;
					joblist[i].accountcd = oldcldivcd;
					//循环获取一览表示中状态位项目的多国语言
					 /* var assignflglanguage = getGridLanguage("assignflg",joblist[i]["assignflg"]);//担当割当状态
					  var reqflglanguage = getGridLanguage("reqflg",joblist[i]["reqflg"]);	//请求状态
					  var recflglanguage = getGridLanguage("recflg",joblist[i]["recflg"]);	//入金状态
					  var invflglanguage = getGridLanguage("invflg",joblist[i]["invflg"]);//发票状态
					  var costendflglanguage = getGridLanguage("costfinishflg",joblist[i]["costfinishflg"]);//原価完了状态
					  joblist[i]["assignflglanguage"]=assignflglanguage;
					  joblist[i]["reqflglanguage"]=reqflglanguage;
					  joblist[i]["recflglanguage"]=recflglanguage;
					  joblist[i]["invflglanguage"]=invflglanguage;
					  joblist[i]["costendflglanguage"]=costendflglanguage;*/
					
					/*//成本条数小于0，仕入增值税合计等于预计仕入增值税合计
					if(costCountNums<=0){
					data[$.getRequestDataName()]['joblist'][i]['costVatTotal'] =data[$.getRequestDataName()]['joblist'][i]['plancosttax']//仕入增值税合计等于预计仕入增值税合计
					data[$.getRequestDataName()]['joblist'][i]['costTotalAmt'] =data[$.getRequestDataName()]['joblist'][i]['plancostamt']//原价合计等于预计原价合计
					//没有成本时，计算用仕入增值税等于预计增值税
					costVatAmt= joblist[i]['plancosttax'];
					}*/
					//成本条数小于0，仕入增值税合计等于预计仕入增值税合计
					if(costCountNums<=0){
							
							//没有成本时，计算用仕入增值税等于预计增值税
							if(isCostFinsh==0){//成本未终了
							//仕入增值税（成本增值税）
							data[$.getRequestDataName()]['joblist'][i]['costVatTotal'] =data[$.getRequestDataName()]['joblist'][i]['plancosttax']//仕入增值税合计等于预计仕入增值税合计
							//实际成本合计
							data[$.getRequestDataName()]['joblist'][i]['costTotalAmt'] =data[$.getRequestDataName()]['joblist'][i]['plancostamt']//原价合计等于预计原价合计
							costVatAmt= joblist[i]['plancosttax'];//仕入增值税（成本增值税）
							costTotal=planCost;//实际成本等于预计成本
							payAmt=joblist[i]['planpayamt'];//实际支付额等于预计支付额
							}else{
							//仕入增值税（成本增值税）
							data[$.getRequestDataName()]['joblist'][i]['costVatTotal'] ="0.00"
							//实际成本合计
							data[$.getRequestDataName()]['joblist'][i]['costTotalAmt'] ="0.00"
							costVatAmt= "0.00";//仕入增值税（成本增值税）
							costTotal="0.00";//实际成本等于预计成本
							payAmt="0.00";//实际支付额等于预计支付额
							}
					}
				    /*//没做支付登录
				    if(joblist[i]['status']!="1"&&joblist[i]['status']!="2"&&joblist[i]['status']!="3"&&joblist[i]['status']!="4"){
				    payAmt=joblist[i]['planpayamt']
				    }*/
				    //如果没做壳上登录
				    if(data[$.getRequestDataName()]['joblist'][i]['seladduser']==null||data[$.getRequestDataName()]['joblist'][i]['seladduser']==''){
				    saleAmt='';
				    }
				    //如果壳上承认了，税率取，税率历史记录表中的数据
				    
				    //data[$.getRequestDataName()]['joblist'][i]['saleadmitdate']!=undefined&&data[$.getRequestDataName()]['joblist'][i]['saleadmitdate']!=null&&data[$.getRequestDataName()]['joblist'][i]['saleadmitdate']!=""
				    
				
				    
				    
					if(joblist[i]["fromjpp"]=="1"){
						joblist[i]['tax2']=0;//税金2
						joblist[i]['tax3']=0//税金3
						var vatamt = joblist[i]['plansaletaxtotal'];
						if(data[$.getRequestDataName()]['joblist'][i].accountflg!=0){
					        var conf = calTax(planSale,saleAmt,planCost,costTotal,reqAmt,payAmt,saleVatAmt,
							costVatAmt,data[$.getRequestDataName()]['joblist'][i]['historyrate2'],data[$.getRequestDataName()]['joblist'][i]['historyrate3'],isCostFinsh,costCountNums,pointNumber,taxFormatFlg,foreignFormatFlg,vatamt);
					    }else{
					        var conf = calTax(planSale,saleAmt,planCost,costTotal,reqAmt,payAmt,saleVatAmt,
							costVatAmt,rate,rate1,isCostFinsh,costCountNums,pointNumber,taxFormatFlg,foreignFormatFlg,vatamt);
					    }
					}else{
					    if(data[$.getRequestDataName()]['joblist'][i].accountflg!=0){
					        var conf = calculateTaxHandler(planSale,saleAmt,planCost,costTotal,reqAmt,payAmt,saleVatAmt,
							costVatAmt,data[$.getRequestDataName()]['joblist'][i]['historyrate2'],data[$.getRequestDataName()]['joblist'][i]['historyrate3'],isCostFinsh,costCountNums,pointNumber,taxFormatFlg,foreignFormatFlg);
					    }else{
					        var conf = calculateTaxHandler(planSale,saleAmt,planCost,costTotal,reqAmt,payAmt,saleVatAmt,
							costVatAmt,rate,rate1,isCostFinsh,costCountNums,pointNumber,taxFormatFlg,foreignFormatFlg);
					    }
					    joblist[i]['tax2']=conf['tax'];//税金2
						joblist[i]['tax3']=conf['tax1']//税金3
					}
					
					joblist[i]['plantaxTotal']=conf['taxTotal']//预计税金合计
					joblist[i]['planprofitRate']=conf['profitRate']//预计税金合计	
					joblist[i]['planprofit']=conf['profit']//预计税金合计	
				  //job未终了，税金实时计算，终了时，税金从数据库取出
				   /* if(isCostFinsh=='0'){//未终了*/
//					if(joblist[i]){
//						
//					}
						joblist[i]['taxTotal']=conf['taxTotal']//税金合計
						joblist[i]['profit']=conf['profit']//营业合计
						joblist[i]['profitRate']=conf['profitRate']//营业率
				  /*	}else{
						joblist[i]['profit']=conf['profit']//营业合计
						joblist[i]['profitRate']=conf['profitRate']//营业率
				    }*/
				  	
				  	//格式化表格数字科学计数法
					joblist[i]["saleamt"]=formatNumber(joblist[i]["saleamt"]);
					joblist[i]["vatamt"]=formatNumber(joblist[i]["vatamt"]);
					joblist[i]["reqamt"]=formatNumber(joblist[i]["reqamt"]);
					joblist[i]["plancostamt"]=formatNumber(joblist[i]["plancostamt"]);
					joblist[i]["costTotalAmt"]=formatNumber(joblist[i]["costTotalAmt"]);
					joblist[i]["costVatTotal"]=formatNumber(joblist[i]["costVatTotal"]);
					joblist[i]["tax2"]=formatNumber(joblist[i]["tax2"]);
					joblist[i]["tax3"]=formatNumber(joblist[i]["tax3"]);
					joblist[i]["taxTotal"]=formatNumber(joblist[i]["taxTotal"]);
					joblist[i]["profit"]=formatNumber(joblist[i]["profit"]);
					joblist[i]["profitRate"]=joblist[i]["profitRate"]+"%";
					joblist[i]["plansale"]=formatNumber(joblist[i]["plansale"]);
				 	}
           		 joblist = loopFun(joblist,[jsflaglanguage,reqflglanguage,recflglanguage,invflglanguage,assignflglanguage,costfinishflglanguage],['jsflag','reqflg','recflg','invflg','assignflg','costfinishflg']);
				 var newjoblist = lableSpan(joblist);
				var dataFil = {
					total:total,
					rows:newjoblist
				}
				if(dataFil.total != 0){
					$('.switch_table').css('display','block');
					$('.switch_table + .switch_table_none').addClass('hidden');
					
				}else{
					$('.switch_table').css('display','none');
					$('.switch_table + .switch_table_none').removeClass('hidden');
					$('.switch_table_none center').text(part_language_change_new("TABLE_CHECK_NO_T"));
					$('.switch_table_none center').attr("name","TABLE_CHECK_NO_T");
					$('.switch_table_none center').attr("class","i18n");
					if(initselect == 0){
						$('.switch_table_none center').text(part_language_change_new("TABLE_CHECK_NO"));
						$('.switch_table_none center').attr("name","TABLE_CHECK_NO");
						$('.switch_table_none center').attr("class","i18n");
						
					}
				}
				//$('.l-btn-small.l-btn-plain').eq(0).find('.l-btn-left.l-btn-icon-left').click()
				$('#job_login_list').datagrid('options').pageNumber = 1;
				$('#job_login_list').datagrid({loadFilter: pagerFilter}).datagrid('loadData',dataFil);
				tableHidden();
		        sumsale(joblist,'saleamt');
		        sumsale(joblist,'taxTotal');
		        sumsale(joblist,'plancostamt');
		        sumsale(joblist,'costTotalAmt');
		        sumsale(joblist,'profit');
		        var profit= $("#profitsum").text();
		        var saleamt= $("#saleamtsum").text();
		        //var profitRate =pointFormatHandler(Number(recoveryNumber(profit)) / Number(recoveryNumber(saleamt))*100,foreignFormatFlg,pointNumber);
		        //var profitRate =pointFormatHandler( floatObj.multiply(floatObj.divide(recoveryNumber(profit),recoveryNumber(saleamt)),100),3,2);
		        var profitRate =pointFormatHandler( floatObj.divide(recoveryNumber(profit),recoveryNumber(saleamt)),3,4);
		        profitRate = formatNumber(floatObj.multiply(profitRate,100),2,false);
		        $("#profitRatesum").text(profitRate+"%");
	           }else{
	           	showErrorFunHandler(data[$.getRequestMetaName()].result,"ERROR","ERROR");
	           }
       },
       error:function(data){
       		showErrorHandler("CONNECTION_SERVER_ERROR","ERROR","ERROR");
       }
	});
	  
}

//关键字模糊查询
function selectByKeyWord(){
    var pars = $.getInputVal();
    pars['searchFlg'] ="keyword"
	sessionStorage.setItem('pars',JSON.stringify(pars));
//	console.log(pars)
	var path = $.getAjaxPath()+"jobSearchByKeyWord";	
	$.ajax({
		url:path,
		data:JSON.stringify(pars),
		headers: {"requestID":$.getRequestID(),"requestName":$.getRequestNameByJcZH()},
		success:function(data){
           if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
           	  	$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
	           	 if(data.data.TooMuchData!=undefined && data.data.TooMuchData==0){
	           		 showErrorHandlerActive('TOO_MUCH_DATA_HEAD',2000,'TOO_MUCH_DATA_TAIL','info','info')
	        		   return ;
	        	   }
           		//数据填充到表格中
           		var joblist =data[$.getRequestDataName()]['joblist'];
           		jsflaglanguage =data[$.getRequestDataName()]['jsflaglanguage'];
           		reqflglanguage =data[$.getRequestDataName()]['reqflglanguage'];
           		recflglanguage =data[$.getRequestDataName()]['recflglanguage'];
           		invflglanguage =data[$.getRequestDataName()]['invflglanguage'];
           		assignflglanguage=data[$.getRequestDataName()]['assignflglanguage'];
				costfinishflglanguage=data[$.getRequestDataName()]['costfinishflglanguage'];
           		var total = data[$.getRequestDataName()]['joblist'].length;
           		var initselect =data[$.getRequestDataName()]['initselect'];
           		labelToMySelect(data[$.getRequestDataName()]['lableall']);
           		SelectObj.mstLableList = data[$.getRequestDataName()]['lableall'];
           		for(var i=0;i<total;i++){
					var planSale= joblist[i]['plansale'];     //预计卖上金额
					var saleAmt= joblist[i]['saleamt'];      //实际卖上金额
					var planCost= joblist[i]['plancostamt'];    //预计成本合计
					var costTotal= joblist[i]['costTotalAmt'];     //实际成本合计
					var reqAmt= joblist[i]['reqamt'];        //请求金额（这个返回的值为有实际返回实际，没实际返回预计）
					var payAmt= joblist[i]['payAmtSum'];       //支付金额（这个返回的值为有实际返回实际，没实际返回预计）
					var saleVatAmt=  joblist[i]['vatamt'];   //卖上增值税（这个返回的值为有实际返回实际，没实际返回预计）
					var costVatAmt= joblist[i]['costVatTotal'];    //仕入增值税（成本增值税）实际值
					var rate=  joblist[i]['rate2'];         //文化税率
					var rate1= joblist[i]['rate3'];         //附加税率
					var isCostFinsh=  joblist[i]['costfinishflg'];  //是否成本录入终止，0：未终止;1：终止
					var costCountNums = joblist[i]['costnum']//成本条数
					//格式化job登录时间
					joblist[i]["adddate"]=new Date(joblist[i]["adddate"]).format_extend('yyyy-MM-dd');
					joblist[i]["saleadddate"]=new Date(joblist[i]["saleadddate"]).format_extend('yyyy-MM-dd');
					joblist[i]["saleadmitdate"]=new Date(joblist[i]["saleadmitdate"]).format_extend('yyyy-MM-dd');
					var oldcldivcd = joblist[i].cldivcd;
					joblist[i].cldivcd= joblist[i].accountcd;
					joblist[i].accountcd = oldcldivcd;
					//一览状态位
					 /* var assignflglanguage = getGridLanguage("assignflg",joblist[i]["assignflg"]);//担当割当状态
					  var reqflglanguage = getGridLanguage("reqflg",joblist[i]["reqflg"]);	//请求状态
					  var recflglanguage = getGridLanguage("recflg",joblist[i]["recflg"]);	//入金状态
					  var invflglanguage = getGridLanguage("invflg",joblist[i]["invflg"]);//发票状态
					  var costendflglanguage = getGridLanguage("costfinishflg",joblist[i]["costfinishflg"]);//原価完了状态
					  joblist[i]["assignflglanguage"]=assignflglanguage;
					  joblist[i]["reqflglanguage"]=reqflglanguage;
					  joblist[i]["recflglanguage"]=recflglanguage;
					  joblist[i]["invflglanguage"]=invflglanguage;
					  joblist[i]["costendflglanguage"]=costendflglanguage;*/
					
					
					//成本条数小于0，仕入增值税合计等于预计仕入增值税合计
					if(costCountNums<=0){
							//没有成本时，计算用仕入增值税等于预计增值税
							if(isCostFinsh==0){//成本未终了
							//仕入增值税（成本增值税）
							data[$.getRequestDataName()]['joblist'][i]['costVatTotal'] =data[$.getRequestDataName()]['joblist'][i]['plancosttax']//仕入增值税合计等于预计仕入增值税合计
							//实际成本合计
							data[$.getRequestDataName()]['joblist'][i]['costTotalAmt'] =data[$.getRequestDataName()]['joblist'][i]['plancostamt']//原价合计等于预计原价合计
							costVatAmt= joblist[i]['plancosttax'];//仕入增值税（成本增值税）
							costTotal=planCost;//实际成本等于预计成本
							payAmt=joblist[i]['planpayamt'];//实际支付额等于预计支付额
							}else{
							//仕入增值税（成本增值税）
							data[$.getRequestDataName()]['joblist'][i]['costVatTotal'] ="0.00"
							//实际成本合计
							data[$.getRequestDataName()]['joblist'][i]['costTotalAmt'] ="0.00"
							costVatAmt= "0.00";//仕入增值税（成本增值税）
							costTotal="0.00";//实际成本等于预计成本
							payAmt="0.00";//实际支付额等于预计支付额
							}
					}
				   /* //没做支付登录
				    if(joblist[i]['status']!="1"&&joblist[i]['status']!="2"&&joblist[i]['status']!="3"&&joblist[i]['status']!="4"){
				    payAmt=joblist[i]['planpayamt']
				    }*/
				    //如果没做壳上登录
				    if(data[$.getRequestDataName()]['joblist'][i]['seladduser']==null||data[$.getRequestDataName()]['joblist'][i]['seladduser']==''){
				    saleAmt='';
				    }
				    
				    
				    if(joblist[i]["fromjpp"]=="1"){
						joblist[i]['tax2']=0;//税金2
						joblist[i]['tax3']=0//税金3
						var vatamt = joblist[i]['plansaletaxtotal'];
						if(data[$.getRequestDataName()]['joblist'][i].accountflg!=0){
					        var conf = calTax(planSale,saleAmt,planCost,costTotal,reqAmt,payAmt,saleVatAmt,
							costVatAmt,data[$.getRequestDataName()]['joblist'][i]['historyrate2'],data[$.getRequestDataName()]['joblist'][i]['historyrate3'],isCostFinsh,costCountNums,pointNumber,taxFormatFlg,foreignFormatFlg,vatamt);
					    }else{
					        var conf = calTax(planSale,saleAmt,planCost,costTotal,reqAmt,payAmt,saleVatAmt,
							costVatAmt,rate,rate1,isCostFinsh,costCountNums,pointNumber,taxFormatFlg,foreignFormatFlg,vatamt);
					    }
					}else{
					    if(data[$.getRequestDataName()]['joblist'][i].accountflg!=0){
					        var conf = calculateTaxHandler(planSale,saleAmt,planCost,costTotal,reqAmt,payAmt,saleVatAmt,
							costVatAmt,data[$.getRequestDataName()]['joblist'][i]['historyrate2'],data[$.getRequestDataName()]['joblist'][i]['historyrate3'],isCostFinsh,costCountNums,pointNumber,taxFormatFlg,foreignFormatFlg);
					    }else{
					        var conf = calculateTaxHandler(planSale,saleAmt,planCost,costTotal,reqAmt,payAmt,saleVatAmt,
							costVatAmt,rate,rate1,isCostFinsh,costCountNums,pointNumber,taxFormatFlg,foreignFormatFlg);
					    }
					    joblist[i]['tax2']=conf['tax'];//税金2
						joblist[i]['tax3']=conf['tax1']//税金3
					}
				    
				    
//				    //如果壳上承认了，税率取，税率历史记录表中的数据
//				    if(data[$.getRequestDataName()]['joblist'][i].accountflg!=0){
//				        var conf = calculateTaxHandler(planSale,saleAmt,planCost,costTotal,reqAmt,payAmt,saleVatAmt,
//						costVatAmt,data[$.getRequestDataName()]['joblist'][i]['historyrate2'],data[$.getRequestDataName()]['joblist'][i]['historyrate3'],isCostFinsh,costCountNums,pointNumber,taxFormatFlg,foreignFormatFlg);
//				    }else{
//				        var conf = calculateTaxHandler(planSale,saleAmt,planCost,costTotal,reqAmt,payAmt,saleVatAmt,
//						costVatAmt,rate,rate1,isCostFinsh,costCountNums,pointNumber,taxFormatFlg,foreignFormatFlg);
//				    }
//					joblist[i]['plantaxTotal']=conf['taxTotal']//预计税金合计
//					joblist[i]['planprofitRate']=conf['profitRate']//预计税金合计	
//					joblist[i]['planprofit']=conf['profit']//预计税金合计	
//				    //job未终了，税金实时计算，终了时，税金从数据库取出
//				   /* if(isCostFinsh=='0'){//未终了*/
//						joblist[i]['tax2']=conf['tax'];//税金2
//						joblist[i]['tax3']=conf['tax1']//税金3
//						joblist[i]['taxTotal']=conf['taxTotal']//税金合计
//						joblist[i]['profit']=conf['profit']//营业合计
//						joblist[i]['profitRate']=conf['profitRate']//营业率
//				  	/*}else{
//						joblist[i]['profit']=conf['profit']//营业合计
//						joblist[i]['profitRate']=conf['profitRate']//营业率
//				    }*/
				    joblist[i]['plantaxTotal']=conf['taxTotal']//预计税金合计
					joblist[i]['planprofitRate']=conf['profitRate']//预计税金合计	
					joblist[i]['planprofit']=conf['profit']//预计税金合计	
				  //job未终了，税金实时计算，终了时，税金从数据库取出
				   /* if(isCostFinsh=='0'){//未终了*/
//					if(joblist[i]){
//						
//					}
						joblist[i]['taxTotal']=conf['taxTotal']//税金合計
						joblist[i]['profit']=conf['profit']//营业合计
						joblist[i]['profitRate']=conf['profitRate']//营业率
					//格式化表格数字科学计数法
					joblist[i]["saleamt"]=formatNumber(joblist[i]["saleamt"]);
					joblist[i]["vatamt"]=formatNumber(joblist[i]["vatamt"]);
					joblist[i]["reqamt"]=formatNumber(joblist[i]["reqamt"]);
					joblist[i]["plancostamt"]=formatNumber(joblist[i]["plancostamt"]);
					joblist[i]["costTotalAmt"]=formatNumber(joblist[i]["costTotalAmt"]);
					joblist[i]["costVatTotal"]=formatNumber(joblist[i]["costVatTotal"]);
					joblist[i]["tax2"]=formatNumber(joblist[i]["tax2"]);
					joblist[i]["tax3"]=formatNumber(joblist[i]["tax3"]);
					joblist[i]["taxTotal"]=formatNumber(joblist[i]["taxTotal"]);
					joblist[i]["profit"]=formatNumber(joblist[i]["profit"]);
					joblist[i]["profitRate"]=joblist[i]["profitRate"]+"%";
					joblist[i]["plansale"]=formatNumber(joblist[i]["plansale"]);
				  	
				 	}
           		 joblist = loopFun(joblist,[jsflaglanguage,reqflglanguage,recflglanguage,invflglanguage,assignflglanguage,costfinishflglanguage],['jsflag','reqflg','recflg','invflg','assignflg','costfinishflg']);
				//var joblist = loopFun(joblist,[reqflglanguage],['reqflg']);
				var newjoblist = lableSpan(joblist);
				var dataFil = {
					total:total,
					rows:newjoblist
				}
				if(dataFil.total != 0){
					$('.switch_table').css('display','block');
					$('.switch_table + .switch_table_none').addClass('hidden');
				}else{
					$('.switch_table').css('display','none');
					$('.switch_table + .switch_table_none').removeClass('hidden');
					$('.switch_table_none center').text(part_language_change_new("TABLE_CHECK_NO_T"));
					$('.switch_table_none center').attr("name","TABLE_CHECK_NO_T");
					$('.switch_table_none center').attr("class","i18n");
					if(initselect == 0){
						$('.switch_table_none center').text(part_language_change_new("TABLE_CHECK_NO"));
						$('.switch_table_none center').attr("name","TABLE_CHECK_NO");
						$('.switch_table_none center').attr("class","i18n");
						
					}
				}
				$('#job_login_list').datagrid({loadFilter: pagerFilter}).datagrid('loadData',dataFil);
				tableHidden();
				//由于第一页的total和其他页的total不相等，easyui会重新发起第一页的请求！加全局变量datagridPageNumber控制
				if(window.datagridPageNumber !== null){
					$('#job_login_list').datagrid({
						loadFilter: pagerFilter
					}).datagrid('loadData', dataFil);
				}
		      
		        sumsale(joblist,'saleamt');
		        sumsale(joblist,'taxTotal');
		        sumsale(joblist,'plancostamt');
		        sumsale(joblist,'costTotalAmt');
		        sumsale(joblist,'profit');
		        var profit= $("#profitsum").text();
		        var saleamt= $("#saleamtsum").text();
		       // var profitRate =pointFormatHandler(Number(recoveryNumber(profit)) / Number(recoveryNumber(saleamt))*100,foreignFormatFlg,pointNumber);
		         //var profitRate =pointFormatHandler( floatObj.multiply(floatObj.divide(recoveryNumber(profit),recoveryNumber(saleamt)),100),3,2);
		        var profitRate =pointFormatHandler( floatObj.divide(recoveryNumber(profit),recoveryNumber(saleamt)),3,4);
		        profitRate = formatNumber(floatObj.multiply(profitRate,100),2,false);
		        $("#profitRatesum").text(profitRate+"%");
	           }else{
	           	showErrorFunHandler(data[$.getRequestMetaName()].result,"ERROR","ERROR");
	           }
       },
       error:function(data){
       		showErrorHandler("CONNECTION_SERVER_ERROR","ERROR","ERROR");
       }
	});
	  
}
function clearSearch(){
	$("#ddflag").attr("checked",false);
	$("#cldiv_cd").val('');
	$("#cldiv_name").val('');
	$("#cost_finish_flg").val('');
	$("#ddcd").val('');
	$("#ddname").val('');
	$("#g_company").val('');//相手先g会社编号
	$("#g_company_name").val('');//相手先g会社编号
	$("#invpd").val('');
	$("#job_cd").val('');
	$("#job_name").val('');
	$("#keyword").val('');
	$("#label_text").val('');
	$("#payer_cd").val('');
	$("#payer_name").val('');
	$("#rjpd").val('');
	$("#sale_cd").val('');
	$("#assign_flg").val('');
	$("#dlvfalg").val('');
	$("#jobInvoiceNo").val('');
	$("#zy").find("span.textbox.combo.datebox").find('input[type="text"]').val('');
	$("#zy").find("span.textbox.combo.datebox").find('input[type="hidden"]').val('');
	$("#zd").find("span.textbox.combo.datebox").find('input[type="text"]').val('');
	$("#zd").find("span.textbox.combo.datebox").find('input[type="hidden"]').val('');
}

//壳上下拉框拼接方法
function setChildList(list, domID) {
	str = '<option value="" class="i18n"></option>';
	for(var i = 0; i < list.length; i++) {
		str += '<option value="' + list[i]['salecd'] + '" class="i18n">' + list[i]['salename'] + '</option>';
	}
	$('#' + domID).prepend(str);
}
//合计计算
function sumsale (data,item){
				
				if(data!=undefined&&data!=null&&data.length>0)
				{  
					var total= data.length
					itemsum=0;
					for(var i=0;i<total;i++){
					//壳上合计
					if(data[i][item]!=null&&data[i][item]!=''){
					//var itemsum = Number(recoveryNumber(data[i][item]))*10000+parseInt(itemsum);	
					var itemsum = floatObj.add(floatObj.multiply(recoveryNumber(data[i][item]),100000),itemsum);	
					}
				}
				//$("#"+item+"sum").text(formatNumber(Number(itemsum)/10000));
				$("#"+item+"sum").text(formatNumber(floatObj.divide(itemsum,100000)));
				}else{
				   	$("#"+item+"sum").text(formatNumber(0.00));
				}
}

function clickMenu(id){
	var rowDataAll = $('#job_login_list').datagrid('getSelected'); //获取所有选择的数据
	var jobcd = rowDataAll['jobcd'];
	var costno = rowDataAll['costno'];
	var saleno = rowDataAll['saleno'];
	var costTotalAmt = rowDataAll['costTotalAmt'];
	var plantaxTotal = rowDataAll['plantaxTotal']; //预计税金
	var taxTotal = rowDataAll['taxTotal']; //实际税金
	var planprofitRate = rowDataAll['planprofitRate']; //预计营收率
	var planprofit = rowDataAll['planprofit']; //预计营收
	var profitRate = rowDataAll['profitRate']; //营收率
	var profit = rowDataAll['profit']; //营收
	var lockflg = rowDataAll['lockflg']; //锁状态
	var reclockflg = rowDataAll['reclockflg']; //锁状态
	var confirmTitle = $.getConfirmMsgTitle();
	
	if(id=='costlist'){//原价一览
		if(!validatePowerByClick("job_login_list","costList")){
		showErrorHandler("SYS_VALIDATEPOWER_ERROR","info","info");
		  return;	
		}
		url = $.getJumpPath()+"jczh/cost_list.html?jobcd="+jobcd;
        window.location.href = url;
	}
	else if(id=='jobupdate'){//job更新
		if(!validatePowerByClick("job_login_list","jobUpdate")){
		showErrorHandler("SYS_VALIDATEPOWER_ERROR","info","info");
		  return;	
		}
		url = $.getJumpPath()+"jczh/job_update.html?jobcd="+jobcd;
        window.location.href = url;
	}else if (id=='mdfp') {//Md分配
		
		url = $.getJumpPath()+"jczh/job_update.html?jobcd="+jobcd+"&md=nomdfp";
        window.location.href = url;
	}else if (id=='yjwl') {//原件完了
		if(!validatePowerByClick("job_login_list","costEnd")){
		showErrorHandler("SYS_VALIDATEPOWER_ERROR","info","info");
		  return;	
		}
			costFinish();
	}else if (id=='yjwlqx') {//原価完了取消
		if(!validatePowerByClick("job_login_list","costEndCancel")){
		showErrorHandler("SYS_VALIDATEPOWER_ERROR","info","info");
		  return;	
		}
			costFinishCancel();
	}else if (id=='saleEdit') {//业务登录
		if(!validatePowerByClick("job_login_list","saleLogin")){
		showErrorHandler("SYS_VALIDATEPOWER_ERROR","info","info");
		  return;	
		}
		if(!validatePowerByClick("job_login_list","reqApply")){
			//请求申请 权限
		  sessionStorage.setItem("reqPower","unhave");	
		}
		if(!validatePowerByClick("job_login_list","invoiceApply")){
		  sessionStorage.setItem("invPower","unhave");
		}
		url = $.getJumpPath()+"jczh/salescategory_registration.html?jobcd="+jobcd;
        window.location.href = url;
	}
	else if (id=='saleUpdate') {//业务更新
		if(!validatePowerByClick("job_login_list","saleUpdate")){
		showErrorHandler("SYS_VALIDATEPOWER_ERROR","info","info");
		  return;	
		}
		if(validatePowerByClick("job_login_list","reqApply")){
			//请求申请 权限
		  sessionStorage.setItem("reqPower","have");	
		}else{
		  sessionStorage.setItem("reqPower","unhave");	
		}
		if(validatePowerByClick("job_login_list","invoiceApply")){
		  sessionStorage.setItem("invPower","have");
		}else{
		   sessionStorage.setItem("invPower","unhave");	
		}
		url = $.getJumpPath()+"jczh/salescategory_update.html?jobcd="+jobcd;
        window.location.href = url;
	}
	else if (id=='saleCancel') {//売上取消
		var msg =showConfirmMsgHandler('SURE_CANCEL');
		$.messager.confirm(confirmTitle,msg, function(r){
		if(r)
		{
			saleCancel(jobcd,saleno,lockflg,reclockflg);
		
		}
		}
		);
	}
	else if (id=='saleAdmit') {//売上承認、可以选择取消，取消后，菜单不显示
		if(!validatePowerByClick("job_login_list","saleAdmin")){
		showErrorHandler("SYS_VALIDATEPOWER_ERROR","info","info");
		  return;	
		}
		url = $.getJumpPath()+"jczh/salescategory_approval.html?jobcd="+jobcd;
        window.location.href = url;
	}else if (id=='saleAdmitCancel') {//売上承認取消、可以选择取消，取消后，菜单不显示
		/*var confirmtrue =showConfirmMsgHandler('MESSAGE_FOR_CONFIRM');
		if(confirmtrue==true){
			saleAdmitCancel(jobcd,saleno);
		}*/
		if(!validatePowerByClick("job_login_list","saleAdminCancel")){
		showErrorHandler("SYS_VALIDATEPOWER_ERROR","info","info");
		  return;	
		}
		url = $.getJumpPath()+"jczh/salescategory_approval.html?jobcd="+jobcd;
        window.location.href = url;
	}else if (id=='qqsq') {//請求申請 可以选择取消，取消后，菜单不显示
		if(!validatePowerByClick("job_login_list","reqApply")){
		showErrorHandler("SYS_VALIDATEPOWER_ERROR","info","info");
		  return;	
		}
			OutPutPdfHandler(jobcd,"","","billOrder","1");
	}else if (id=='fpfx') {//発票発行申請 可以选择取消，取消后，菜单不显示
		if(!validatePowerByClick("job_login_list","invoiceApply")){
		showErrorHandler("SYS_VALIDATEPOWER_ERROR","info","info");
		  return;	
		}
			//刘实添加
			OutPutPdfHandler(jobcd,"","","invoiceApplication","1");
	}else if (id=='wfdl') {//外发登录  
		if(!validatePowerByClick("job_login_list","costLogin")){
		showErrorHandler("SYS_VALIDATEPOWER_ERROR","info","info");
		  return;	
		}
		url = $.getJumpPath()+"jczh/order_register.html?jobcd="+jobcd;
        window.location.href = url;
	}else if(id=='liti'){//立体
		if(!validatePowerByClick("job_login_list","lendLogin")){
		showErrorHandler("SYS_VALIDATEPOWER_ERROR","info","info");
		  return;	
		}
		url = $.getJumpPath()+"jczh/advance_registration.html?jobcd="+jobcd;
        window.location.href = url;
	}else if (id=='zhenti') {//振提
		url = $.getJumpPath()+"jczh/transfer_registration.html?jobcd="+jobcd;
        window.location.href = url;
	}else if (id=='mmdlp') {//売買登録票
		if(!validatePowerByClick("job_login_list","businessOutPut")){
		showErrorHandler("SYS_VALIDATEPOWER_ERROR","info","info");
		  return;	
		}
		OutPutPdfHandler(jobcd,"","","jobDetailSale","1");
	}else if (id=='jobdetail') {//JOB詳細
		url = $.getJumpPath()+"jczh/job_details.html?jobcd="+jobcd+"&jobdetailflag=joblist";
        window.location.href = url;
	}
}
//多条menu点击事件
function clickMenuMore(id){
	var rowDataAll = $('#job_login_list').datagrid('getSelections'); //获取所有选择的数据
	if (id=='yjwlM') {//多条原件完了
		 costFinish(rowDataAll);
	}else if(id=='yjwlqxM'){//原件完了取消
		costFinishCancel(rowDataAll);
	}else if(id=='qqsqM'){
		var jobCdArr = new Array();
		if(rowDataAll.length>10){
			showErrorHandler("MAX_DEAL_NUMBER", "INFO", "INFO");
		}else{
			for(var i=0;i<rowDataAll.length;i++){
			jobCdArr.push(rowDataAll[i].jobcd);
		}
		OutPutPdfHandlerMore(jobCdArr,"","","billOrder","1");
		}
		
	}else if(id=='fpfxsqM'){
		var jobCdArr = new Array();
		if(rowDataAll.length>10){
			showErrorHandler("MAX_DEAL_NUMBER", "INFO", "INFO");
		}else{
			for(var i=0;i<rowDataAll.length;i++){
			jobCdArr.push(rowDataAll[i].jobcd);
		}
		OutPutPdfHandlerMore(jobCdArr,"","","invoiceApplication","1");
		}
		
	}else if(id=='rujinM'){
		//recEdit();
	}
}
//入金保存
function recEdit(){
	if(!validataRequired())
	{
		return ;
	}
	 var rowDataAll =$('#job_login_list').datagrid('getSelections');//获取所有选择的数据
	 var total =rowDataAll.length;
	 var deleteRecdate ="0";
	 var deletepd=$('#deleteRecdate').prop('checked');
	 var recremark =$('#recremark').val();
	 var recdate = $("input[name='rec_date']").val();
	 if($('#rec_flg').prop('checked')==true){
	 var recstatus ="1";	
	 }else{
	 var recstatus ="0"	;	
	 }
	 if(deletepd==false&&recdate==""){
		 deleteRecdate=1;
	 }
	 else{
		 deleteRecdate=0;
	 }
	 if(rowDataAll[0]['reccd']!=undefined&&rowDataAll[0]['reccd']!=null&&rowDataAll[0]['reccd']!=''){
	 	//备考和入金时间都不空时，为更新
	 	var udpflg='0';
	 	rjcl="1";
	 }else{
	 	var udpflg='1';
	 }
	 
	 var pars={};
	 var a={};
	 var b=[];
	 //循环拼接数据List
	 for(var i=0;i<total;i++){
		//如果recdate不为空,且deletepd没有check,且recstatus没有选中,要把原数据中入金日赋回来
         //也就是选中多条的时候,如果入金日不一样,date为空,这个时候更新会把日期置空
         if(rowDataAll[i]["recdate"]!="" && deletepd==false && recstatus=="0"
         &&recdate==""){
                 var rec_date = rowDataAll[i]["recdate"];
                 var remark = rowDataAll[i]["recremark"];
         }else{
                 rec_date =recdate;
                 remark = recremark;
         }
	 	var jobcd =rowDataAll[i]["jobcd"];
	 	var reqamt =recoveryNumber(rowDataAll[i]["reqamt"]);
	 	var saleno =rowDataAll[i]["saleno"];
	 	var reccd =rowDataAll[i]["reccd"];
	 	var lockflg =rowDataAll[i]["lockflg"];
	 	var reclockflg =rowDataAll[i]["reclockflg"];
	
	 	 a={
	 	job_cd:jobcd,
	 	rec_cd:reccd,
	 	rec_date:rec_date,
        recremark:remark,
	 	rec_status:recstatus,
	 	deleteRecdate:deleteRecdate,
	 	rec_amt:reqamt,
	 	udpflg:udpflg,
	 	lockflg:lockflg,
	 	reclockflg:reclockflg,
	 	sale_no:saleno
	    }
	 	b.push(a)
	 }
	 var  pars={
	 	recList:b
	 }
	 var path = $.getAjaxPath()+"recTrnEdit";	
	 $.ajax({
		url:path,
		data:JSON.stringify(pars),
		headers: {"requestID":$.getRequestID(),"requestName":$.getRequestNameByJcZH()},
		success:function(data){
           if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
        	 //存储选中数组的行数
   				var indexArr = new Array();
           	     $.resetRequestID(data[$.getRequestUserInfoName()].requestID);
//           	     if(data[$.getRequestDataName()]=="2"){
//           	 		 showLockInfoMsgHandler("STATUS_VALIDATEPOWER_ERROR","jczh/job_registration_list.html?view=init&menu=se");
//					 return;
//           	 	}else 
           	 		
           	 	if(data[$.getRequestDataName()]=="0") {
           	 		 showErrorHandler("EXECUTE_FAIL", "SUCCESS", "SUCCESS");
           	 	}else{
           	     showErrorHandler("EXECUTE_SUCCESS", "SUCCESS", "SUCCESS");
           	     var rows = $('#job_login_list').datagrid("getSelections"); //获取你选择的所有行 
				 //循环所选的行,更改选中行dategrid中的原价完了状态为已完了
				 for(var i =0;i<rows.length;i++){
					var  par = b;
                    var s = par[i].rec_date;
				    var rowIndex = $('#job_login_list').datagrid('getRowIndex',rows[i]);//获取某行的行号
					indexArr.push(rowIndex);
					var insertflag = data.data[i]['insertflag'];
	                if(insertflag=="1"){//没做过入金
	                        var reclockflg=Number(rows[i]['reclockflg']);
	                }else{
	                        var reclockflg=Number(rows[i]['reclockflg'])+1;        
	                }
					//var reclockflg=Number(rows[i]['reclockflg'])+1;
					 if(data[$.getRequestDataName()]==999){
						 reclockflg = 0;
					 }
					 if($("#rjpd").val()=="001"&&recstatus==1){
							$('#job_login_list').datagrid('deleteRow', rowIndex);
						} else if($("#rjpd").val()=="003"&&recstatus==0){
							$('#job_login_list').datagrid('deleteRow', rowIndex);
						}else{
								 if(rows.length<=1){
								 	
					 				//更新多条的时候,更新remark,更新单条的时候不更新remark
						 			$('#job_login_list').datagrid('updateRow',{
									index:rowIndex,
									row:{
									recflg:recstatus,
									recremark:recremark,
									recdate:recdate,
									reclockflg:reclockflg,
									reccd:data.data[i]['rec_cd']
						     		}
						     })
							 }else{
								 
								 if(deleteRecdate==1){
									 $('#job_login_list').datagrid('updateRow',{
											index:rowIndex,
											row:{
											recflg:recstatus,
											//recdate:recdate,
											reclockflg:reclockflg,
											reccd:data.data[i]['rec_cd']
											}
									})
									 
								 }else{
									 
									 $('#job_login_list').datagrid('updateRow',{
											index:rowIndex,
											row:{
											recflg:recstatus,
											recdate:par[i].rec_date,
											reclockflg:reclockflg,
											reccd:data.data[i]['rec_cd']
											}
									})
									 
								 }
						 			
					 }
						}
				
				 } 
				 
				var rowAll = $('#job_login_list').datagrid("getData")['originalRows'];
				var total =rowAll.length;
				if(total == 0){
					$('.switch_table').css('display','none');
					$('.switch_table + .switch_table_none').removeClass('hidden');
					$('.switch_table_none center').text(part_language_change_new("TABLE_CHECK_NO"));
					}else{
						var joblist = loopFun(rowAll,[recflglanguage],['recflg']);
						var dataFil = {
						total:total,
						rows:joblist
						}
						$('#job_login_list').datagrid({loadFilter: pagerFilter}).datagrid('loadData',dataFil);
						//job_scroll_fun()
						tableHidden();
					}	
				
				//更新表格数据
				/*var view = getQueryString("view","jczh/job_registration_list.html");
	            var pars =JSON.parse(sessionStorage.getItem('pars'));
				var sideBar = urlPars.parm("sideBar");
				var parsList = jumpPageSearchList(view);
				if(view == "top" && parsList.keyword != "")
				 {
				  $('#keyword').val(parsList.keyword);
				 }
				 if(pars['searchFlg']!=""){
				 	 searchJoblistInit(parsList,pars['searchFlg']);  		
				 	}else{
				 	 searchJoblistInit(parsList);  		
				 	}*/
				//修复 入金完了中最后一条数据 做 入金处理时的错误
				if(dataFil!=null&&dataFil!=undefined){
					$('#job_login_list').datagrid({loadFilter: pagerFilter}).datagrid('loadData',dataFil);
					job_scroll_fun()
				}
				
				tableHidden();
	            	var index = layer.index;
					layer.close(index);
					if($("#rjpd").val()!="001"){
						if($("#rjpd").val()!="003"){
							reselect('#job_login_list',indexArr);
							}
						}
					if($("#rjpd").val()=="001"&&recstatus!=1){
						reselect('#job_login_list',indexArr);
					}
					if($("#rjpd").val()=="003"&&recstatus!=0){
						reselect('#job_login_list',indexArr);
					}
				}	
           	 	
           	 if($("#rjpd").val()=="001"&&recstatus==1){
           		$("#selectDetail").click();
           	 }else if($("#rjpd").val()=="003"&&recstatus==0){
           		$("#selectDetail").click();
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
//壳上取消
function saleCancel(jobcd,saleno,lockflg,reclockflg) { 
	var path = $.getAjaxPath()+"delSale";	
	var pars = {job_cd:jobcd,sale_no:saleno,saleLockFlg:lockflg,recLockFlg:reclockflg};
	$.ajax({
		url:path,
		data:JSON.stringify(pars),
		headers: {"requestID":$.getRequestID(),"requestName":$.getRequestNameByJcZH()},
		success:function(data){
           if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
           	 	$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
           	 	if(data[$.getRequestDataName()]=="540"){
           	 		 showErrorHandler("STATUS_VALIDATEPOWER_ERROR","error","error");
					 return;
           	 	}else{
           	 		//存储选中数组的行数
       				var indexArr = new Array();
	           	 	showErrorHandler("EXECUTE_SUCCESS", "SUCCESS", "SUCCESS");
		            var rowIndex = $('#job_login_list').datagrid('getRowIndex', $('#job_login_list').datagrid('getSelected'));
		            indexArr.push(rowIndex);
		            if($("#dlvfalg").val()=="003"||$("#dlvfalg").val()=="004"){
							$('#job_login_list').datagrid('deleteRow', rowIndex);
						}else{
			           	    $('#job_login_list').datagrid('updateRow',{
								index:rowIndex,
								row:{
									saleaddflg:0,
									lockflg:Number(lockflg)+1,
									jsflag:"001",
									seladduser:"",
									saleadddate:""
								}
							})
	           	   }
	           	    
	           	    var rowAll = $('#job_login_list').datagrid("getData")['originalRows'];
					var total =rowAll.length;
					if(total == 0){
					$('.switch_table').css('display','none');
					$('.switch_table + .switch_table_none').removeClass('hidden');
					$('.switch_table_none center').text(part_language_change_new("TABLE_CHECK_NO"));
					}else{
						var joblist = loopFun(rowAll,[jsflaglanguage],['jsflag']);
						var dataFil = {
						total:total,
						rows:joblist
						}
						$('#job_login_list').datagrid({loadFilter: pagerFilter}).datagrid('loadData',dataFil);
						tableHidden();
					}	
	        /*   	    var view = getQueryString("view","jczh/job_registration_list.html");
		            var pars =JSON.parse(sessionStorage.getItem('pars'));  
					var sideBar = urlPars.parm("sideBar");
					var parsList = jumpPageSearchList(view);
					if(view == "top" && parsList.keyword != "")
					 {
					  $('#keyword').val(parsList.keyword);
					 }
					 if(pars['searchFlg']!=""){
					 	 searchJoblistInit(parsList,pars['searchFlg']);  		
					 	}else{
					 	 searchJoblistInit(parsList);  		
					 	}*/
					reselect('#job_login_list',indexArr);
					if($("#dlvfalg").val()=="003"||$("#dlvfalg").val()=="004"){
						$("#selectDetail").click();
					}
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

function saleAdmitCancel(jobcd,saleno){
var path = $.getAjaxPath()+"saleAdmitCancel";	
	var pars = {job_cd:jobcd,sale_no:saleno};
	$.ajax({
		url:path,
		data:JSON.stringify(pars),
		headers: {"requestID":$.getRequestID(),"requestName":$.getRequestNameByJcZH()},
		success:function(data){
           if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
           	    $.resetRequestID(data[$.getRequestUserInfoName()].requestID);
           	     showErrorHandler("EXECUTE_SUCCESS", "SUCCESS", "SUCCESS");
           	    var rowIndex = $('#job_login_list').datagrid('getRowIndex', $('#job_login_list').datagrid('getSelected'));
           	    $('#job_login_list').datagrid('updateRow',{
					index:rowIndex,
					row:{
						saleadmitflg:0
					}
				})
           	    var view = getQueryString("view","jczh/job_registration_list.html");
	            var pars =JSON.parse(sessionStorage.getItem('pars'));
				var sideBar = urlPars.parm("sideBar");
				var parsList = jumpPageSearchList(view);
				if(view == "top" && parsList.keyword != "")
				 {
				  $('#keyword').val(parsList.keyword);
				 }
				 if(pars['searchFlg']!=""){
				 	 searchJoblistInit(parsList,pars['searchFlg']);  		
				 	}else{
				 	 searchJoblistInit(parsList);  		
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

//原价完了
function costFinish(){
	var rowDataAll = $('#job_login_list').datagrid('getSelections');
	var path = $.getAjaxPath()+"costFinish";
	var pars = {};
	pars['joblistinput']=rowDataAll;
	$.ajax({
		url:path,
		data:JSON.stringify(pars),
		headers: {"requestID":$.getRequestID(),"requestName":$.getRequestNameByJcZH()},
		success:function(data){
           if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
           		$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
           	     showErrorHandler("EXECUTE_SUCCESS", "SUCCESS", "SUCCESS");
           	     var indexArr = new Array();
           	     var rows = $('#job_login_list').datagrid("getSelections"); //获取你选择的所有行 
				 //循环所选的行,更改选中行dategrid中的原价完了状态为已完了
				for(var i =0;i<rows.length;i++){
					var planSale= rows[i]['plansale'].replace(",","");     //预计卖上金额
					var saleAmt= rows[i]['saleamt'].replace(",",""); ;      //实际卖上金额
					var planCost= rows[i]['plancostamt'].replace(",",""); ;    //预计成本合计
					var costTotal= rows[i]['costTotalAmt'].replace(",",""); ;     //实际成本合计
					var reqAmt= rows[i]['reqamt'].replace(",",""); ;        //请求金额
					var payAmt= rows[i]['payAmtSum'].replace(",",""); ;       //支付金额
					var saleVatAmt=  rows[i]['vatamt'].replace(",",""); ;   //卖上增值税
					var costVatAmt= rows[i]['costVatTotal'].replace(",",""); ;    //仕入增值税（成本增值税）
					var rate=  rows[i]['rate2'].replace(",",""); ;         //文化税率
					var rate1= rows[i]['rate3'].replace(",",""); ;         //附加税率
					var isCostFinsh= 1;  //是否成本录入终止，0：未终止;1：终止	
					var costCountNums = rows[i]['costnum']//成本条数//成本条数
					//成本条数小于0，仕入增值税合计等于预计仕入增值税合计
					if(costCountNums<=0){
							//仕入增值税（成本增值税）
							rows[i]['costVatTotal'] ="0.00"
							//实际成本合计
							rows[i]['costTotalAmt'] ="0.00"
							costVatAmt= "0.00";//仕入增值税（成本增值税）
							costTotal="0.00";//实际成本等于预计成本
							payAmt="0.00";//实际支付额等于预计支付额
					}
				    //如果没做壳上登录
				    if(rows[i]['seladduser']==null||rows[i]['seladduser']==''){
				    saleAmt='';
				    }
					if(rows[i]["fromjpp"]=="1"){
						rows[i]['tax2']=0;//税金2
						rows[i]['tax3']=0//税金3
						var vatamt = rows[i]['plansaletaxtotal'];
						if(rows[i].accountflg!=0){
					        var conf = calTax(planSale,saleAmt,planCost,costTotal,reqAmt,payAmt,saleVatAmt,
							costVatAmt,rows[i]['historyrate2'],rows[i]['historyrate3'],isCostFinsh,costCountNums,pointNumber,taxFormatFlg,foreignFormatFlg,vatamt);
					    }else{
					        var conf = calTax(planSale,saleAmt,planCost,costTotal,reqAmt,payAmt,saleVatAmt,
							costVatAmt,rate,rate1,isCostFinsh,costCountNums,pointNumber,taxFormatFlg,foreignFormatFlg,vatamt);
					    }
					}else{
					    if(rows[i].accountflg!=0){
					        var conf = calculateTaxHandler(planSale,saleAmt,planCost,costTotal,reqAmt,payAmt,saleVatAmt,
							costVatAmt,rows[i]['historyrate2'],rows[i]['historyrate3'],isCostFinsh,costCountNums,pointNumber,taxFormatFlg,foreignFormatFlg);
					    }else{
					        var conf = calculateTaxHandler(planSale,saleAmt,planCost,costTotal,reqAmt,payAmt,saleVatAmt,
							costVatAmt,rate,rate1,isCostFinsh,costCountNums,pointNumber,taxFormatFlg,foreignFormatFlg);
					    }
					    rows[i]['tax2']=conf['tax'];//税金2
						rows[i]['tax3']=conf['tax1']//税金3
					}
					
					rows[i]['plantaxTotal']=conf['taxTotal']//预计税金合计
					rows[i]['planprofitRate']=conf['profitRate']//预计税金合计	
					rows[i]['planprofit']=conf['profit']//预计税金合计	
				  //job未终了，税金实时计算，终了时，税金从数据库取出
				   /* if(isCostFinsh=='0'){//未终了*/
//					if(rows[i]){
//						
//					}
					rows[i]['taxTotal']=conf['taxTotal']//税金合計
					rows[i]['profit']=conf['profit']//营业合计
					rows[i]['profitRate']=conf['profitRate']//营业率
					rows[i]["saleamt"]=formatNumber(rows[i]["saleamt"]);
					rows[i]["vatamt"]=formatNumber(rows[i]["vatamt"]);
					rows[i]["reqamt"]=formatNumber(rows[i]["reqamt"]);
					rows[i]["plancostamt"]=formatNumber(rows[i]["plancostamt"]);
					rows[i]["costTotalAmt"]=formatNumber(rows[i]["costTotalAmt"]);
					rows[i]["costVatTotal"]=formatNumber(rows[i]["costVatTotal"]);
					rows[i]["tax2"]=formatNumber(rows[i]["tax2"]);
					rows[i]["tax3"]=formatNumber(rows[i]["tax3"]);
					rows[i]["taxTotal"]=formatNumber(rows[i]["taxTotal"]);
					rows[i]["profit"]=formatNumber(rows[i]["profit"]);
					rows[i]["profitRate"]=rows[i]["profitRate"]+"%";
					rows[i]["plansale"]=formatNumber(rows[i]["plansale"]);
					
					
					 
				     var rowIndex = $('#job_login_list').datagrid('getRowIndex',rows[i]);//获取某行的行号
					 indexArr.push(rowIndex);
					 if($("#cost_finish_flg").val()=="001"){
							$('#job_login_list').datagrid('deleteRow', rowIndex);
						}else{
								$('#job_login_list').datagrid('updateRow',{
									index:rowIndex,
									row:{
										costfinishflg:"1",
										costTotalAmt:rows[i]["costTotalAmt"],
										costVatTotal:rows[i]["costVatTotal"],
										tax2:rows[i]["tax2"],
										tax3:rows[i]["tax3"],
										taxTotal:rows[i]["taxTotal"],
										profit:rows[i]["profit"],
										profitRate:rows[i]["profitRate"],
									}
							    })
						}
					  }
           	     
           	  if($("#cost_finish_flg").val()=="001"){
           		var rowAll = $('#job_login_list').datagrid("getData")['rows'];
           	  }else{
           		var rowAll = $('#job_login_list').datagrid("getData")['originalRows'];
           	  }
				
				var total =rowAll.length;
				if(total == 0){
					$('.switch_table').css('display','none');
					$('.switch_table + .switch_table_none').removeClass('hidden');
					$('.switch_table_none center').text(part_language_change_new("TABLE_CHECK_NO"));
					sumsale(joblist,'saleamt');
                    sumsale(joblist,'taxTotal');
                    sumsale(joblist,'plancostamt');
                    sumsale(joblist,'costTotalAmt');
                    sumsale(joblist,'profit');
                    $("#profitRatesum").text("0.00%");
				}else{
					var joblist = loopFun(rowAll,[costfinishflglanguage],['costfinishflg']);
					var dataFil = {
					total:total,
					rows:joblist
					}
					$('#job_login_list').datagrid({loadFilter: pagerFilter}).datagrid('loadData',dataFil);
					tableHidden();
					//由于第一页的total和其他页的total不相等，easyui会重新发起第一页的请求！加全局变量datagridPageNumber控制
					if(window.datagridPageNumber !== null){
						$('#job_login_list').datagrid({
							loadFilter: pagerFilter
						}).datagrid('loadData', dataFil);
					}
					sumsale(joblist,'saleamt');
			        sumsale(joblist,'taxTotal');
			        sumsale(joblist,'plancostamt');
			        sumsale(joblist,'costTotalAmt');
			        sumsale(joblist,'profit');
			        var profit= $("#profitsum").text();
			        var saleamt= $("#saleamtsum").text();
			       // var profitRate =pointFormatHandler(Number(recoveryNumber(profit)) / Number(recoveryNumber(saleamt))*100,foreignFormatFlg,pointNumber);
			         //var profitRate =pointFormatHandler( floatObj.multiply(floatObj.divide(recoveryNumber(profit),recoveryNumber(saleamt)),100),3,2);
			        var profitRate =pointFormatHandler( floatObj.divide(recoveryNumber(profit),recoveryNumber(saleamt)),3,4);
			        profitRate = formatNumber(floatObj.multiply(profitRate,100),2,false);
			        $("#profitRatesum").text(profitRate+"%");
			        
				}	
				
				if($("#cost_finish_flg").val()!="001"){
					reselect('#job_login_list',indexArr);
				}
				
				if($("#cost_finish_flg").val()=="001"){
					$("#selectDetail").click();
				}	
				
//				changeDateByRow ('job_login_list','costfinishflg',1);
	           }else{
	           	showErrorFunHandler(data[$.getRequestMetaName()].result,"ERROR","ERROR");
	           }
       },
       error:function(data){
       		showErrorHandler("CONNECTION_SERVER_ERROR","ERROR","ERROR");
       }
	});	
}
//原价完了取消
function costFinishCancel(){
	var rowDataAll = $('#job_login_list').datagrid('getSelections');
	var path = $.getAjaxPath()+"costFinishCancel";	
	var pars = {};
	pars['joblistinput']=rowDataAll;
	$.ajax({
		url:path,
		data:JSON.stringify(pars),
		headers: {"requestID":$.getRequestID(),"requestName":$.getRequestNameByJcZH()},
		success:function(data){
           if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
           	    $.resetRequestID(data[$.getRequestUserInfoName()].requestID);
		 	      showErrorHandler("EXECUTE_SUCCESS", "SUCCESS", "SUCCESS");
		 	     var indexArr = new Array();
		 	     var rows = $('#job_login_list').datagrid("getSelections"); //获取你选择的所有行 
				 //循环所选的行,更改选中行dategrid中的原价完了状态为已完了
				 for(var i =0;i<rows.length;i++){
					var planSale= rows[i]['plansale'].replace(",","");     //预计卖上金额
					var saleAmt= rows[i]['saleamt'].replace(",","");       //实际卖上金额
					var planCost= rows[i]['plancostamt'].replace(",","");     //预计成本合计
					var costTotal= rows[i]['costTotalAmt'].replace(",","");      //实际成本合计
					var reqAmt= rows[i]['reqamt'].replace(",","");         //请求金额
					var payAmt= rows[i]['payAmtSum'].replace(",","");        //支付金额
					var saleVatAmt=  rows[i]['vatamt'].replace(",","");    //卖上增值税
					var costVatAmt= rows[i]['costVatTotal'].replace(",","");     //仕入增值税（成本增值税）
					var rate=  rows[i]['rate2'].replace(",","");          //文化税率
					var rate1= rows[i]['rate3'].replace(",","");          //附加税率
					var isCostFinsh=  0;  //是否成本录入终止，0：未终止;1：终止	
					var costCountNums = rows[i]['costnum']//成本条数//成本条数
					//成本条数小于0，仕入增值税合计等于预计仕入增值税合计
					if(costCountNums<=0){
							//仕入增值税（成本增值税）
							rows[i]['costVatTotal'] =rows[i]['plancosttax']//仕入增值税合计等于预计仕入增值税合计
							//实际成本合计
							rows[i]['costTotalAmt'] =rows[i]['plancostamt']//原价合计等于预计原价合计
							costVatAmt= rows[i]['plancosttax'].replace(",","");//仕入增值税（成本增值税）
							costTotal=planCost.replace(",","");//实际成本等于预计成本
							payAmt=rows[i]['planpayamt'].replace(",","");//实际支付额等于预计支付额
					}
				    //如果没做壳上登录
				    if(rows[i]['seladduser']==null||rows[i]['seladduser']==''){
				    saleAmt='';
				    }
					if(rows[i]["fromjpp"]=="1"){
						rows[i]['tax2']=0;//税金2
						rows[i]['tax3']=0//税金3
						var vatamt = rows[i]['plansaletaxtotal'];
						if(rows[i].accountflg!=0){
					        var conf = calTax(planSale,saleAmt,planCost,costTotal,reqAmt,payAmt,saleVatAmt,
							costVatAmt,rows[i]['historyrate2'],rows[i]['historyrate3'],isCostFinsh,costCountNums,pointNumber,taxFormatFlg,foreignFormatFlg,vatamt);
					    }else{
					        var conf = calTax(planSale,saleAmt,planCost,costTotal,reqAmt,payAmt,saleVatAmt,
							costVatAmt,rate,rate1,isCostFinsh,costCountNums,pointNumber,taxFormatFlg,foreignFormatFlg,vatamt);
					    }
					}else{
					    if(rows[i].accountflg!=0){
					        var conf = calculateTaxHandler(planSale,saleAmt,planCost,costTotal,reqAmt,payAmt,saleVatAmt,
							costVatAmt,rows[i]['historyrate2'],rows[i]['historyrate3'],isCostFinsh,costCountNums,pointNumber,taxFormatFlg,foreignFormatFlg);
					    }else{
					        var conf = calculateTaxHandler(planSale,saleAmt,planCost,costTotal,reqAmt,payAmt,saleVatAmt,
							costVatAmt,rate,rate1,isCostFinsh,costCountNums,pointNumber,taxFormatFlg,foreignFormatFlg);
					    }
					    rows[i]['tax2']=conf['tax'];//税金2
						rows[i]['tax3']=conf['tax1']//税金3
					}
					
					rows[i]['plantaxTotal']=conf['taxTotal']//预计税金合计
					rows[i]['planprofitRate']=conf['profitRate']//预计税金合计	
					rows[i]['planprofit']=conf['profit']//预计税金合计	
				  //job未终了，税金实时计算，终了时，税金从数据库取出
				   /* if(isCostFinsh=='0'){//未终了*/
//					if(rows[i]){
//						
//					}
					rows[i]['taxTotal']=conf['taxTotal']//税金合計
					rows[i]['profit']=conf['profit']//营业合计
					rows[i]['profitRate']=conf['profitRate']//营业率
					rows[i]["saleamt"]=formatNumber(rows[i]["saleamt"]);
					rows[i]["vatamt"]=formatNumber(rows[i]["vatamt"]);
					rows[i]["reqamt"]=formatNumber(rows[i]["reqamt"]);
					rows[i]["plancostamt"]=formatNumber(rows[i]["plancostamt"]);
					rows[i]["costTotalAmt"]=formatNumber(rows[i]["costTotalAmt"]);
					rows[i]["costVatTotal"]=formatNumber(rows[i]["costVatTotal"]);
					rows[i]["tax2"]=formatNumber(rows[i]["tax2"]);
					rows[i]["tax3"]=formatNumber(rows[i]["tax3"]);
					rows[i]["taxTotal"]=formatNumber(rows[i]["taxTotal"]);
					rows[i]["profit"]=formatNumber(rows[i]["profit"]);
					rows[i]["profitRate"]=rows[i]["profitRate"]+"%";
					rows[i]["plansale"]=formatNumber(rows[i]["plansale"]);
					
				     var rowIndex = $('#job_login_list').datagrid('getRowIndex',rows[i]);//获取某行的行号
					indexArr.push(rowIndex);
					  if($("#cost_finish_flg").val()=="002"){
							$('#job_login_list').datagrid('deleteRow', rowIndex);
						}else{
								$('#job_login_list').datagrid('updateRow',{
									index:rowIndex,
									row:{
										costfinishflg:"0",
										costTotalAmt:rows[i]["costTotalAmt"],
										costVatTotal:rows[i]["costVatTotal"],
										tax2:rows[i]["tax2"],
										tax3:rows[i]["tax3"],
										taxTotal:rows[i]["taxTotal"],
										profit:rows[i]["profit"],
										profitRate:rows[i]["profitRate"],
									}
							    })
						} 
				
				 }
		 	    if($("#cost_finish_flg").val()=="002"){
		 	    	var rowAll = $('#job_login_list').datagrid("getData")['rows'];
		 	    }else{
		 	    	var rowAll = $('#job_login_list').datagrid("getData")['originalRows'];
		 	    }
				
				var total =rowAll.length;
				if(total == 0){
					$('.switch_table').css('display','none');
					$('.switch_table + .switch_table_none').removeClass('hidden');
					$('.switch_table_none center').text(part_language_change_new("TABLE_CHECK_NO"));
					sumsale(joblist,'saleamt');
                    sumsale(joblist,'taxTotal');
                    sumsale(joblist,'plancostamt');
                    sumsale(joblist,'costTotalAmt');
                    sumsale(joblist,'profit');
                    $("#profitRatesum").text("0.00%");
				}else{
					var joblist = loopFun(rowAll,[costfinishflglanguage],['costfinishflg']);
					var dataFil = {
					total:total,
					rows:joblist
					}
					$('#job_login_list').datagrid({loadFilter: pagerFilter}).datagrid('loadData',dataFil);
					tableHidden();
					//由于第一页的total和其他页的total不相等，easyui会重新发起第一页的请求！加全局变量datagridPageNumber控制
					if(window.datagridPageNumber !== null){
						$('#job_login_list').datagrid({
							loadFilter: pagerFilter
						}).datagrid('loadData', dataFil);
					}
					sumsale(joblist,'saleamt');
			        sumsale(joblist,'taxTotal');
			        sumsale(joblist,'plancostamt');
			        sumsale(joblist,'costTotalAmt');
			        sumsale(joblist,'profit');
			        var profit= $("#profitsum").text();
			        var saleamt= $("#saleamtsum").text();
			       // var profitRate =pointFormatHandler(Number(recoveryNumber(profit)) / Number(recoveryNumber(saleamt))*100,foreignFormatFlg,pointNumber);
			         //var profitRate =pointFormatHandler( floatObj.multiply(floatObj.divide(recoveryNumber(profit),recoveryNumber(saleamt)),100),3,2);
			        var profitRate =pointFormatHandler(floatObj.divide(recoveryNumber(profit),recoveryNumber(saleamt)),3,4);
			        profitRate = formatNumber(floatObj.multiply(profitRate,100),2,false);
			        $("#profitRatesum").text(profitRate+"%");
					
				}	
				if($("#cost_finish_flg").val()!="002"){
					reselect('#job_login_list',indexArr);
				}
				/*var view = getQueryString("view","jczh/job_registration_list.html");
	            var pars =JSON.parse(sessionStorage.getItem('pars'));
				var sideBar = urlPars.parm("sideBar");
				var parsList = jumpPageSearchList(view);
				if(view == "top" && parsList.keyword != "")
				 {
				  $('#keyword').val(parsList.keyword);
				 }
				 if(pars['searchFlg']!=""){
				 	 searchJoblistInit(parsList,pars['searchFlg']);  		
				 	}else{
				 	 searchJoblistInit(parsList);  		
				 	}*/
				/*changeDateByRow ('job_login_list','costfinishflg',0);*/
				
				if($("#cost_finish_flg").val()=="002"){
					$("#selectDetail").click();
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



/**
* 方法名 saveJobWidth
* 方法的说明 保存job一览的列宽
* @return ArrayJson
* @author作者 张立学
* @date 日期 2018.09.27
*/
function saveJobWidth(){
	var arr=[];
	var columnObj = $("#job_login_list").datagrid("options").columns[0];
	//获取冻结列的宽度
	var frozenColumnWidth = $('#job_login_list').datagrid("getColumnOption","jobcd").width;
	arr.push({'field':'jobcd','width':frozenColumnWidth});
	for(var i = 0;i < columnObj.length;i ++){
		var obj = {};
		obj['field']=columnObj[i].field;
		obj['width']=columnObj[i].width;
		arr.push(obj);
	}
	arr = JSON.stringify(arr);
//	console.log(arr);
	return arr;
}

function loopFun(data, listArr, arr) {
	var data = data;
	for(var i = 0; i < arr.length; i++) {
		data = formatStatus(data, listArr[i], arr[i]);
	}
	return data;
}
//对表格中的状态位进行处理
function formatStatus(data, list, colname) {

	var val = "";
	var valEn = "";
	var valJp = "";
	var valHk = "";
	switch(colname) {
		case 'jsflag': //请求状态
			for(var i = 0; i < data.length; i++) {
			
					switch(data[i][colname]) {
						case "001":
							val = searchValue(list, "itemcd", "001", 'itmname');
							valEn = searchValue(list, "itemcd", "001", 'itemnameen');
							valJp = searchValue(list, "itemcd", "001", 'itemnamejp');
							valHk = searchValue(list, "itemcd", "001", 'itemnamehk');
							break;
						case "002":
							val = searchValue(list, "itemcd", "002", 'itmname');
							valEn = searchValue(list, "itemcd", "002", 'itemnameen');
							valJp = searchValue(list, "itemcd", "002", 'itemnamejp');
							valHk = searchValue(list, "itemcd", "002", 'itemnamehk');
							break;
						case "003":
							val = searchValue(list, "itemcd", "003", 'itmname');
							valEn = searchValue(list, "itemcd", "003", 'itemnameen');
							valJp = searchValue(list, "itemcd", "003", 'itemnamejp');
							valHk = searchValue(list, "itemcd", "003", 'itemnamehk');
							break;
						case "004":
							val = searchValue(list, "itemcd", "004", 'itmname');
							valEn = searchValue(list, "itemcd", "004", 'itemnameen');
							valJp = searchValue(list, "itemcd", "004", 'itemnamejp');
							valHk = searchValue(list, "itemcd", "004", 'itemnamehk');
							break;
						case "005":
							val = searchValue(list, "itemcd", "005", 'itmname');
							valEn = searchValue(list, "itemcd", "005", 'itemnameen');
							valJp = searchValue(list, "itemcd", "005", 'itemnamejp');
							valHk = searchValue(list, "itemcd", "005", 'itemnamehk');
							break;
					}
				data[i].jsflaglanguagezc = val;
				data[i].jsflaglanguageen = valEn;
				data[i].jsflaglanguagejp = valJp;
				data[i].jsflaglanguagezt = valHk;
			}
			break;
		case 'reqflg': //请求状态
			for(var i = 0; i < data.length; i++) {
			
					switch(data[i][colname]) {
						case "0":
							val = searchValue(list, "itemcd", "001", 'itmname');
							valEn = searchValue(list, "itemcd", "001", 'itemnameen');
							valJp = searchValue(list, "itemcd", "001", 'itemnamejp');
							valHk = searchValue(list, "itemcd", "001", 'itemnamehk');
							break;
						case "1":
							val = searchValue(list, "itemcd", "003", 'itmname');
							valEn = searchValue(list, "itemcd", "003", 'itemnameen');
							valJp = searchValue(list, "itemcd", "003", 'itemnamejp');
							valHk = searchValue(list, "itemcd", "003", 'itemnamehk');
							break;
					}
				data[i].reqflglanguagezc = val;
				data[i].reqflglanguageen = valEn;
				data[i].reqflglanguagejp = valJp;
				data[i].reqflglanguagezt = valHk;
			}
			break;
		case 'recflg': //请求状态
			for(var i = 0; i < data.length; i++) {
			
					switch(data[i][colname]) {
						case "0":
							val = searchValue(list, "itemcd", "001", 'itmname');
							valEn = searchValue(list, "itemcd", "001", 'itemnameen');
							valJp = searchValue(list, "itemcd", "001", 'itemnamejp');
							valHk = searchValue(list, "itemcd", "001", 'itemnamehk');
							break;
						case "1":
							val = searchValue(list, "itemcd", "003", 'itmname');
							valEn = searchValue(list, "itemcd", "003", 'itemnameen');
							valJp = searchValue(list, "itemcd", "003", 'itemnamejp');
							valHk = searchValue(list, "itemcd", "003", 'itemnamehk');
							break;
					}
				data[i].recflglanguagezc = val;
				data[i].recflglanguageen = valEn;
				data[i].recflglanguagejp = valJp;
				data[i].recflglanguagezt = valHk;
			}
			break;
		case 'invflg': //请求状态
			for(var i = 0; i < data.length; i++) {
			
					switch(data[i][colname]) {
						case "0":
							val = searchValue(list, "itemcd", "001", 'itmname');
							valEn = searchValue(list, "itemcd", "001", 'itemnameen');
							valJp = searchValue(list, "itemcd", "001", 'itemnamejp');
							valHk = searchValue(list, "itemcd", "001", 'itemnamehk');
							break;
						case "1":
							val = searchValue(list, "itemcd", "003", 'itmname');
							valEn = searchValue(list, "itemcd", "003", 'itemnameen');
							valJp = searchValue(list, "itemcd", "003", 'itemnamejp');
							valHk = searchValue(list, "itemcd", "003", 'itemnamehk');
							break;
					}
				data[i].invflglanguagezc = val;
				data[i].invflglanguageen = valEn;
				data[i].invflglanguagejp = valJp;
				data[i].invflglanguagezt = valHk;
			}
			break;
		case 'assignflg': //割当担当
			for(var i = 0; i < data.length; i++) {
			
					switch(data[i][colname]) {
						case "0":
							val = searchValue(list, "itemcd", "001", 'itmname');
							valEn = searchValue(list, "itemcd", "001", 'itemnameen');
							valJp = searchValue(list, "itemcd", "001", 'itemnamejp');
							valHk = searchValue(list, "itemcd", "001", 'itemnamehk');
							break;
						case "1":
							val = searchValue(list, "itemcd", "002", 'itmname');
							valEn = searchValue(list, "itemcd", "002", 'itemnameen');
							valJp = searchValue(list, "itemcd", "002", 'itemnamejp');
							valHk = searchValue(list, "itemcd", "002", 'itemnamehk');
							break;
							break;
					}
				data[i].assignflglanguagezc = val;
				data[i].assignflglanguageen = valEn;
				data[i].assignflglanguagejp = valJp;
				data[i].assignflglanguagezt = valHk;
			}
			break;
		case 'costfinishflg': //原价完了状态
			for(var i = 0; i < data.length; i++) {
			
					switch(data[i][colname]) {
						case "0":
							val = searchValue(list, "itemcd", "001", 'itmname');
							valEn = searchValue(list, "itemcd", "001", 'itemnameen');
							valJp = searchValue(list, "itemcd", "001", 'itemnamejp');
							valHk = searchValue(list, "itemcd", "001", 'itemnamehk');
							break;
						case "1":
							val = searchValue(list, "itemcd", "002", 'itmname');
							valEn = searchValue(list, "itemcd", "002", 'itemnameen');
							valJp = searchValue(list, "itemcd", "002", 'itemnamejp');
							valHk = searchValue(list, "itemcd", "002", 'itemnamehk');
							break;
					}
				data[i].costendflglanguagezc = val;
				data[i].costendflglanguageen = valEn;
				data[i].costendflglanguagejp = valJp;
				data[i].costendflglanguagezt = valHk;
			}
			break;
	}
	//objStorage.reData = data;
	return data;
}
//datagrid tooltip icon语言国际化
function toolTipLanguage(language){
	if(language == undefined){
		language = localStorage.getItem('language');
	}
	var arr_language = [];
	var icon_language = [];
	var toolTipO = part_language_change_new('JOB_TABLE_INFO_O'),
	toolTip2 = part_language_change_new('JOB_TABLE_INFO_T'),
	toolTip3 = part_language_change_new('JOB_TABLE_INFO_W'),
	toolTip4 = part_language_change_new('JOB_TABLE_INFO_F');	
	arr_language=[toolTipO,toolTip2,toolTip3,toolTip4];
	icon_language=['.icon-jobicon-','.icon-bianji','.icon-xiazai1','.icon-qiehuan'];
	for(var i = 0;i < icon_language.length;i ++){
		$(icon_language[i]).tooltip({
		    position: 'left',
		    content: '<span style=\"color:#fff\">'+arr_language[i]+'</span>',
		    onShow: function(){
				$(this).tooltip('tip').css({
					backgroundColor: '#666',
					borderColor: '#666'
				});
		    }
		});
	}
}

function lableSpan(data){
	for(var i=0;i<data.length;i++){
		var lable = data[i].lable;
		var lableLevel = data[i].lablelevel;
		if(lable!=''&&lable!=null){
			var lableStr = '';
			var lableUse = '';
			var lableArr = lable.split('  ');
			var lableLevelArr = lableLevel.split('  ');
			for(var k=0;k<lableArr.length;k++){
				
			 if(lableLevelArr[k]==1){
			 	if(k==0){
					lableStr = "<span class='manager'>"+lableArr[k]+"</span>";
					lableUse = lableArr[k]
				}else{
					lableStr+='  '+"<span class='manager'>"+lableArr[k]+"</span>";
					lableUse+='  '+lableArr[k];
				}
			 }else{
			 	if(k==0){
					lableStr = "<span class='requestor'>"+lableArr[k]+"</span>";
					lableUse = lableArr[k]
				}else{
					lableStr+='  '+"<span class='requestor'>"+lableArr[k]+"</span>";
					lableUse+= '  '+lableArr[k];
				}
			 }
			 //lableStr = lableStr.replace(/,/g,'');
			 data[i].lable = lableStr;
			 data[i].lableUse = lableUse;
	 			}
		}
	}
	
	return data;
}

function tableHidden(){
	if($('.datagrid-view').hasClass('top-40')){
		var heiTop = $('.datagrid-view').height()+40;
		$('.datagrid-view').height(heiTop);
	}
}
function createInvoiceNoHandler(flg)
{
	var dataValue = $('#invoiceNo').val();
	//有效性验证
	if (flg != 'cancelInvoice'){
		if(!invoice_require_check()){
			return;
		}
		if(!invoiceInputVeri($('#invoiceNo').val())){
			//不是必填的时候允许为空
			if (dataValue != ''){
				showErrorHandler("NOT_EMPTY", "ERROR", "ERROR");
				return;
			}else {
				var dataValueNew = dataValue;
			}
		}else {
			var dataValueNew = deleteComma(dataValue);
		}
	}
	if(flg == "cancelInvoice")
	{
		$('#invoiceNo').val("");
	}
	//var rowDataAll = $('#job_login_list').datagrid('getSelections');
	var rowDataAll = $('#job_login_list').datagrid('getSelected');
	var rowIndex = $('#job_login_list').datagrid('getRowIndex',rowDataAll);
	var path = $.getAjaxPath()+"costFinish";
	var pars = {};
	pars['searchFlg']="createInvoiceNo";
	pars['job_cd']=rowDataAll["jobcd"];
	pars['sale_no']=dataValueNew;
	pars['searchFlg']=flg;
	$.ajax({
		url:path,
		data:JSON.stringify(pars),
		headers: {"requestID":$.getRequestID(),"requestName":$.getRequestNameByJcZH()},
		success:function(data){
			if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
				//存储选中数组的行数
				var indexArr = new Array();
           		$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
           		if(data[$.getRequestDataName()] == "VALIDATE_FORMAT_ERROR")
           		{
           			showErrorHandler(data[$.getRequestDataName()],"ERROR","ERROR");
           		}else{
           			var rows = $('#job_login_list').datagrid("getSelections");
           			for(var i =0;i<rows.length;i++){
                        var rowIndex = $('#job_login_list').datagrid('getRowIndex',rows[i]);//获取某行的行号
                        indexArr.push(rowIndex);
           			}
                        
                        
                        
           			
           			rowDataAll['jobInvNo'] = data[$.getRequestDataName()];
               		$('#job_login_list').datagrid('reload', {
        				index: rowIndex,
        				row: rowDataAll
        			})
           			$('#job_login_list').datagrid('refreshRow', rowIndex);
               		showErrorHandler("EXECUTE_SUCCESS", "SUCCESS", "SUCCESS");
               		
               		if(($('#sellInvoiceflg').val() == "002" && flg == "cancelInvoice") || 
               				($('#sellInvoiceflg').val() == "001" && flg != "cancelInvoice"))
               		{
               			$('#job_login_list').datagrid('deleteRow', rowIndex);
               		}
               		
               		var rowAll = $('#job_login_list').datagrid("getData")['rows'];
					var total =rowAll.length;
					if(total == 0){
						$('.switch_table').css('display','none');
						$('.switch_table + .switch_table_none').removeClass('hidden');
						$('.switch_table_none center').text(part_language_change_new("TABLE_CHECK_NO"));
					}
           		}
           		var index = layer.index;
				layer.close(index);
				reselect('#cost_list_Two',indexArr);
			}else{
	           	showErrorFunHandler(data[$.getRequestMetaName()].result,"ERROR","ERROR");
			}
       },
       error:function(data){
       		showErrorHandler("CONNECTION_SERVER_ERROR","ERROR","ERROR");
       }
	});	
}

function calTax(planSale,saleAmt,planCost,costTotal,reqAmt,payAmt,saleVatAmt,
		costVatAmt,rate,rate1,isCostFinsh,costCountNums,point,taxFormatFlg,foreignFormatFlg,vatamt)
{
	var tax = 0.0;
	var tax1 = 0.0;
	var taxTotal = 0.0;
	var profit = 0.0;
	var profitRate = 0.0;
	
	var saleBaseAmt = planSale;
	var costTotalAmt = planCost;
	//实际卖上不为空，使用实际
	if(saleAmt != "")
	{
	saleBaseAmt = parseFloat(saleAmt);
	}
	//是否有成本
	if(costCountNums > 0)
	{
	//使用实际成本
	costTotalAmt = costTotal;
	}else{
	//没有成本并且成本终止录入，实际成本为0
	if(isCostFinsh == 1)
	{
	costTotalAmt = 0.0;
	}
	}
	//文化建设税 = （请求金额-支付金额）*税率1
	tax = floatObj.multiply(floatObj.subtract(parseFloat(reqAmt) , parseFloat(payAmt)),parseFloat(rate))
	tax = pointFormatHandler(tax,taxFormatFlg,point);
	//增值附加税 = （卖上增值税-支付增值税）*税率2
	tax1 = floatObj.multiply(floatObj.subtract(parseFloat(saleVatAmt),parseFloat(costVatAmt)),parseFloat(rate1));
	tax1 = pointFormatHandler(tax1,taxFormatFlg,point);
	//税金合计 = 文化+增值税附加
	
	taxTotal = floatObj.add(parseFloat(tax) , parseFloat(tax1));
	taxTotal = pointFormatHandler(taxTotal,taxFormatFlg,point);
	
	if(vatamt!=null){
		taxTotal=vatamt;
	}
	//营收 = 卖上金额-（原价金额+税金合计）
	profit = floatObj.subtract(parseFloat(saleBaseAmt),floatObj.add(parseFloat(costTotalAmt) , parseFloat(taxTotal)));
	profit = pointFormatHandler(profit,foreignFormatFlg,point);
	//营收率=营收/卖上金额 * 100
	if(saleBaseAmt == 0)
	{
		profitRate = "INF";
	}else{
		//profitRate = floatObj.multiply(floatObj.divide(parseFloat(profit),parseFloat(saleBaseAmt)),100);
		profitRate = floatObj.divide(parseFloat(profit),parseFloat(saleBaseAmt));
		profitRate = pointFormatHandler(profitRate,3,4);
		profitRate = formatNumber(floatObj.multiply(profitRate,100),2,false);
	}
	
	return {"tax":tax,"tax1":tax1,"taxTotal":taxTotal,"profit":profit,"profitRate":profitRate};
}

function invoiceInputVeri(value) {
	var reg = /^(([\da-zA-Z]{1,20}),?(,[\da-zA-Z]{1,20})*,?),*$/;
	if (reg.test(value)){
		var validateSpecialString = value.split(',')[1];
		var validateSpecialStringT = value.split(',')[2];
		if (validateSpecialString == '' && (validateSpecialStringT != '' && validateSpecialStringT != undefined)){
			return false;
		}
		return true;
	}else {
		return false;
	}
}
function deleteComma(dataValue) {
	var lengthConst = dataValue.length;
	for (var i = 0;i < lengthConst;i ++){
		var lastIndex = dataValue.lastIndexOf(',');
		var length = dataValue.length - 1;
		if (lastIndex > -1 && lastIndex == length) {
			dataValue = dataValue.substring(0, lastIndex);
		}else {
			return dataValue;
		}
	}
}
//如果没有输入任何检索条件 返回 true 反之返回 false
function seachCondition(pars){
	var conditionFlg = false;
		if(pars['searchFlg'] =="jobdetailsearch"){
			if($.getInputVal().job_name==""&&$.getInputVal().sale_cd==""&&$.getInputVal().ddname==""&&$.getInputVal().assign_flg==""&&$.getInputVal().cldiv_name==""
				&&$.getInputVal().cost_finish_flg==""&&$.getInputVal().dlvfalg==""&&$.getInputVal().g_company_name==""&&$.getInputVal().invpd==""&&$.getInputVal().job_cd==""
					&&$.getInputVal().label_text==""&&$.getInputVal().payer_name==""&&$.getInputVal().rjpd==""){
				conditionFlg = true;
			}else{
				return false
			}
			if(pars['ddcd']==""&&pars['g_company']==""&&pars['payer_cd']==""&&pars['cldiv_cd']==""&&pars['sellInvoiceFlg']==""&&pars['jInvNo']==""){
				conditionFlg = true;
			}else{
				return false
			}
			if($("#jzy").hasClass("active")){
				if(pars['dlvday']==""){
					conditionFlg = true;
				}else{
					return false
				}
			}else{
				if(pars['dlvmon_sta']==""&&pars['dlvmon_end']==""){
					conditionFlg = true;
				}else{
					return false
				}
			}
			if($("#ddflag").prop("checked")==false)
		    {
				conditionFlg = true;
		    }
		}
		return conditionFlg;
	}
