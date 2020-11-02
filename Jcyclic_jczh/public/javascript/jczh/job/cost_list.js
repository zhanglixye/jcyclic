$(function() {
	//直接跳转到详细检索
	$("#setb2").click();
	//得意先 change
	$("#cldiv_name").change(function(){
		$("#cldiv_cd").val("");
	});
	
	$("#payee_name").change(function(){
		$("#payee_cd").val("");
	});
	$("#search-detail1").click(function() {
        sessionSet("BASE");
	});
	$("#search-detail").click(function() {
		 sessionSet("OTHER");
	});
	$("#clear-condition").click(function() {
		$("#myTabContent").find("input").val("");
		$("#myTabContent").find("select").val("");
	});
	$("#clear-condition1").click(function() {
		$("#myTabContent").find("input").val("");
		$("#myTabContent").find("select").val("");
	});
	$('#vogueSearch').click(function() {
		 sessionSet("VOGUE");
	});

	$("#deal").click(function() {
		if(!validataRequired()){
			return ;
		}else{
		var cflg = $("#payflg").prop("checked");
		payDeal(cflg);
		var index = layer.index;
		layer.close(index);
		}
		
	});
	$(".filter-lable").click(function() {

		var str = $("#lableStr").val();
		filterLable(str, 1);
	})

	//发注更新
	$('.cost-up').click(function() {
		if(!validatePowerByClick("cost_list_Two","costUpdate")){
			showErrorHandler("SYS_VALIDATEPOWER_ERROR",'info','info');
			return ;
		}
		var row = $('#cost_list_Two').datagrid('getSelected');
		window.location.href = $.getJumpPath() + 'jczh/order_update.html?jobcd=' + row.jobcd + '&&costno=' + row.costno;
	});
	$('.price-detail').click(function() {
		var row = $('#cost_list_Two').datagrid('getSelected');
		var jobdetailflag = null;
		var no = null;
		var jobName =row.jobName;
		switch(row.discd) {
			case "001":
				jobdetailflag = "cost";
				no = row.costno;
				break;
			case "002":
				jobdetailflag = "lend";
				no = row.inputno;
				break;
			case "003":
				jobdetailflag = "tran";
				no = row.inputno;
				break;
		}
		window.location.href = $.getJumpPath() + 'jczh/job_details.html?jobcd=' + row.jobcd + '&&inputno=' + no + '&&jobdetailflag=' + jobdetailflag;
	});

	//支付情报登录
	$('.pay-msg').click(function() {
		if(!validatePowerByClick("cost_list_Two","payLogin")){
			showErrorHandler("SYS_VALIDATEPOWER_ERROR",'info','info');
			return ;
		}
		var row = $('#cost_list_Two').datagrid('getSelected');
		window.location.href = $.getJumpPath() + 'jczh/payment_registration.html?jobcd=' + row.jobcd + '&&costno=' + row.costno;
	});
	//支付情报gengxin
	$('.pay-msg-up').click(function() {
		if(!validatePowerByClick("cost_list_Two","payUpdate")){
			showErrorHandler("SYS_VALIDATEPOWER_ERROR",'info','info');
			return ;
		}
		var row = $('#cost_list_Two').datagrid('getSelected');
		window.location.href = $.getJumpPath() + 'jczh/payment_update.html?jobcd=' + row.jobcd + '&&costno=' + row.costno;
	});
	//支付申请
	$('.pay-req').click(function() {
		var row = $('#cost_list_Two').datagrid('getSelected');
		window.location.href = $.getJumpPath() + 'jczh/payment_info.html?jobcd=' + row.jobcd + '&&inputno=' + row.inputno;
	});
	//支付承认
	$('.pay-ad').click(function() {
		var row = $('#cost_list_Two').datagrid('getSelected');
		window.location.href = $.getJumpPath() + 'jczh/payment_approval.html?jobcd=' + row.jobcd + '&&inputno=' + row.inputno + '&&flg=1';
	});
	//支付取消
	$('.pay-cal').click(function() {
		var row = $('#cost_list_Two').datagrid('getSelected');
		window.location.href = $.getJumpPath() + 'jczh/payment_approval.html?jobcd=' + row.jobcd + '&&inputno=' + row.inputno + '&&flg=0';
	});
	//立替更新
	$('.lend-up').click(function() {
		if(!validatePowerByClick("cost_list_Two","lendUpdate")){
			showErrorHandler("SYS_VALIDATEPOWER_ERROR",'info','info');
			return ;
		}
		var row = $('#cost_list_Two').datagrid('getSelected');
		window.location.href = $.getJumpPath() + 'jczh/advance_update.html?jobcd=' + row.jobcd + '&&inputno=' + row.inputno;
	});
	//立替承认
	$('.lend-ad').click(function() {
		var row = $('#cost_list_Two').datagrid('getSelected');
		window.location.href = $.getJumpPath() + 'jczh/advance_approval.html?jobcd=' + row.jobcd + '&&inputno=' + row.inputno + '&&flg=1';
	});
	//立替取消
	$('.lend-cal').click(function() {
		var row = $('#cost_list_Two').datagrid('getSelected');
		window.location.href = $.getJumpPath() + 'jczh/advance_approval.html?jobcd=' + row.jobcd + '&&inputno=' + row.inputno + '&&flg=0';
	});
	//振替更新
	$('.tran-up').click(function() {
		var row = $('#cost_list_Two').datagrid('getSelected');
		window.location.href = $.getJumpPath() + 'jczh/transfer_update.html?jobcd=' + row.jobcd + '&&inputno=' + row.inputno;
	});
	//振替承认
	$('.tran-ad').click(function() {
		var row = $('#cost_list_Two').datagrid('getSelected');
		window.location.href = $.getJumpPath() + 'jczh/transfer_approval.html?jobcd=' + row.jobcd + '&&inputno=' + row.inputno + '&&flg=1';
	});
	//振替取消
	$('.tran-cal').click(function() {
		var row = $('#cost_list_Two').datagrid('getSelected');
		window.location.href = $.getJumpPath() + 'jczh/transfer_approval.html?jobcd=' + row.jobcd + '&&inputno=' + row.inputno + '&&flg=0';
	});
	//发注书出力
	$('.cost-out').click(function() {
		//var row = $('#cost_list_Two').datagrid('getSelected');
		
		var rowDataAll = $('#cost_list_Two').datagrid('getSelections'); 
		var jobCdArr = new Array();
		var costCdArr = new Array();
		var inputCdArr = new Array();
		if(rowDataAll.length>10){
			showErrorHandler("MAX_DEAL_NUMBER", "INFO", "INFO");
		}else if(rowDataAll.length>1){
			for(var i=0;i<rowDataAll.length;i++){
				jobCdArr.push(rowDataAll[i].jobcd);
				costCdArr.push(rowDataAll[i].costno);
			}
			OutPutPdfHandlerMore(jobCdArr,costCdArr,"","reportCreate","1")
		}else{
			OutPutPdfHandler(rowDataAll[0].jobcd,rowDataAll[0].costno,rowDataAll[0].inputno,"reportCreate","1");
		}
		
		

		});
		

	
	//lable追加
	$('.add_lable').click(function() {
		listLableAdd("cost_list_Two", 1, 1);
	});
	//lable 追加初始化
	$(".lable-add").click(function() {
		lableAddInit("cost_list_Two");
	});
	$(".label-add-T").click(function() {
		lableAddInit("cost_list_Two");
	});
	$(".lable-set").click(function() {
		listLableInit("cost_list_Two");
	})
	//lable设定
	$('#set').click(function() {
		listLableSet("cost_list_Two", 1);
	});
	$('#dalday').datebox({
		onSelect:function(beginDate){
			$.dateLimit('dalday1', beginDate, 1);
		}
	});

	//lableList 点击的时候

	var url = $.getJumpPath() + 'common/suppliers_retrieval.html';
	$.layerShow("searchClient", url);
	$.layerShow("searchContra", url);
	
	$("#status").change(function(){
		setSelectDefault();
	});
	$("#dis").change(function(){
		setSelectDefault();
	});
	$("#pay_status").change(function(){
		setSelectDefault();
	});
 	
	prePageLoad();
})
function setSelectDefault(){
	
	var	status = $("#status").val();
	var dis = $("#dis").val();
	var pay_status = $("#pay_status").val();
	var obj={"costSelect_status":status,"costSelect_dis":dis,"costSelect_pay_status":pay_status};
	
	SelectObj.setDefaultData=obj;
	SelectObj.setStringFlg="_";
	SelectObj.setSelectID = ['status','dis','pay_status'];
	//SelectObj.setSelectOfLog();
}
//支付处理
function payDeal(cFlg) {
	//都是未处理  支付完了 flg 未选中 日期是空   清空日期可选   都没选 什么也没干  
	//1.都没选 啥也没干  2.支付flg没选,日期空,清空日期选了 ,,支付日期 清空 3.支付没选,日期填写,清空日期空  更新支付日
	//4.支付没选,日期填写了,清空打钩,日期清空灰化. 5.支付flg选中  清空flg灰化  日是空,报错.
	//7.支付选中,日期也写了. 清空没选,支付状态 1(济),支付日就是填写的日期 . 

	//选中的案件 有支付日是空,还有支付日不是空  前提都是未支付   画面显示, flg没选,日期是空,清空可点
	// 1.flg没选,日期空,清空没选,2.flg f  日期空 , clear t ,支付日期 清空
	// 3.flg f 日期写了, clear f  支付日期 4.flg f 清空打钩,日期清空灰化.
	// 5.flg t  日期是空的 , clear灰化,报错. 7.支付选中,日期也写了. 清空没选,支付状态 1(济),支付日就是填写的日期 .
	//单挑
	//flg f 日期空.啥也不干
	//flg f 日期不是空 支付日更新
	//flg t 日期没写 报错
	//flg t 日期写了 插入
	var payInput = [];
	var payflg = $("#payflg").prop("checked") ? "1" : "0";
	var paydate = $("#order_date").val()==""?null:$("#order_date").val();
	var payremark = $("#payremark").val();
	var rows = $('#cost_list_Two').datagrid('getSelections'); //获取所有选择的数据
	 var paycanceldate =$('#paycanceldate').prop('checked')? "1" : "0";
	if(rows.length>1){
	  for(var i=0;i<rows.length;i++){
		//如果recdate不为空,且deletepd没有check,且recstatus没有选中,要把原数据中入金日赋回来
          //也就是选中多条的时候,如果入金日不一样,date为空,这个时候更新会把日期置空
           if(rows[i]["paydate"]!="" && paycanceldate=="0" && payflg=="0"
          &&(paydate==""||paydate==null)){
                  var rec_date = rows[i]["paydate"];
                  var remark = rows[i]["remark"];
          }else{
                  rec_date =paydate;
                  remark = payremark;
          }
    	var pay = {
				inputno: rows[i].inputno,
				paylockflg:rows[i].paylockflg,
				paydate: paydate,
				paydate: rec_date,
				payflg: payflg
			}
    	payInput.push(pay);
        }
	}else{
	 var row= $('#cost_list_Two').datagrid('getSelected'); 
	 var pay = {
	 	       inputno: row.inputno,
	 	       paylockflg:row.paylockflg,
				paydate: paydate,
				payflg: payflg,
				payremark: payremark
	    }
	 payInput.push(pay);
	}
   	
	var payDeal = {
		payInput: payInput
	}
	if(rows.length > 1) {
		var arrItem =new Array();
		var arrValue = new Array();
		for(var i = 0;i<rows.length;i++){
			arrValue.push('paydate')
			arrValue.push('payflgcd')
			arrValue.push('paylockflg')
			arrValue.push(payInput[i].paydate)
			arrValue.push(payInput[i].payflg)
			arrValue.push(payInput[i].paylockflg)
		}
		
	} else {
		var arrItem = ['paydate', 'payremark', 'payflgcd','paylockflg'];
		var arrValue = [payInput[0].paydate, payInput[0].payremark, payInput[0].payflg,payInput[0].paylockflg];
	}
	$.ajax({
		url: $.getAjaxPath() + "payDeal",
		data: JSON.stringify(payDeal),
		headers: {
			"requestID": $.getRequestID(),
			"requestName": $.getRequestNameByJcZH()
		},
		success: function(data) {
			//存储选中数组的行数
			var indexArr = new Array();
//			console.log(data);
			if(data[$.getRequestMetaName()].result == $.getRequestStatusName()) {
				$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
				if(data[$.getRequestDataName()] > 0) {
					if(data[$.getRequestDataName()] == 540) {
						var url = "jczh/cost_list.html?view=6";
						showLockInfoMsgHandler('STATUS_VALIDATEPOWER_ERROR',url);
 						return;
 					}
					
				var flg_class = $('#myTab').find('.active').find('center').prop('class').split(" ")[1];
				
				if(flg_class=="other"){
					var payDealStatus  = $('#pay_status').val();
					var dealDate = $('#payhopedate').datebox("getValue");
					//如果不是按照支付状态查询  改变状态 否则 移除行
				if(payDealStatus==""&&dealDate==""){
					var indexArr = new Array();
					//changeDateByRows("cost_list_Two", arrItem, arrValue, false);
					var rows = $('#cost_list_Two').datagrid("getData")['originalRows'];
					var costrows = $('#cost_list_Two').datagrid("getSelections"); //获取你选择的所有行
                    for(var i =0;i<payInput.length;i++){
                    var rowIndex = $('#cost_list_Two').datagrid('getRowIndex',costrows[i]);//获取某行的行号
                    indexArr.push(rowIndex);
                            if($("#paytypelist").val()=="001"&&payflg==1){
                            $('#cost_list_Two').datagrid('deleteRow', rowIndex);
                            }else if($("#paytypelist").val()=="002"&&payflg==0){
                                    $('#cost_list_Two').datagrid('deleteRow', rowIndex);
                            }else{
                                    if(payInput.length > 1) {                                                        
                                    $('#cost_list_Two').datagrid('updateRow',{
                                                    index:rowIndex,
                                                    row:{
                                                    paydate:payInput[i].paydate,
                                                    payflgcd:payInput[i].payflg,
                                                    paylockflg:payInput[i].paylockflg+1
                                                    }
                                    })

                                    } else {
                                            var arrItem = ['paydate', 'payremark', 'payflgcd','paylockflg'];
                                            var arrValue = [payInput[0].paydate, payInput[0].payremark, payInput[0].payflg,payInput[0].paylockflg+1];
                                            changeDateByRows("cost_list_Two", arrItem, arrValue, false);
                                    }
                                    
                            }
                    }
					
					if(rows==undefined){
					 rows = $('#cost_list_Two').datagrid('getRows');
					}
					var data = loopFun(rows, [objStorage.getPayStatusShow()], ['payflgcd']);
					var data = lableSpan(data);
				   var dataFil = {
						total: data.length,
						rows: data
					};
					if(dataFil.total != 0){
						$('.switch_table').css('display','block');
						$('.switch_table + .switch_table_none').addClass('hidden');
					}else{
						$('.switch_table').css('display','none');
						$('.switch_table + .switch_table_none').removeClass('hidden');
						$('.switch_table_none div').text(part_language_change_new("TABLE_CHECK_NO"));
						$('.switch_table_none div').attr("name","TABLE_CHECK_NO");
						$('.switch_table_none div').attr("class","i18n");
					}
					$('#cost_list_Two').datagrid({
						loadFilter: pagerFilter
					}).datagrid('loadData', dataFil);
					reselect('#cost_list_Two',indexArr);
					tableHiddenC()
					}else{
						if(arrItem!=null){
							changeDateByRows("cost_list_Two", arrItem, arrValue, false);
						}
						var rows = $('#cost_list_Two').datagrid("getSelections"); //获取你选择的所有行
	                    for(var i =0;i<payInput.length;i++){
	                    var rowIndex = $('#cost_list_Two').datagrid('getRowIndex',rows[i]);//获取某行的行号
	                            if($("#paytypelist").val()=="001"&&payflg==1){
	                            $('#cost_list_Two').datagrid('deleteRow', rowIndex);
	                            }else if($("#paytypelist").val()=="002"&&payflg==0){
	                                    $('#cost_list_Two').datagrid('deleteRow', rowIndex);
	                            }else{
	                                    if(payInput.length > 1) {                                                        
	                                    $('#cost_list_Two').datagrid('updateRow',{
	                                                    index:rowIndex,
	                                                    row:{
	                                                    paydate:payInput[i].paydate,
	                                                    payflgcd:payInput[i].payflg,
	                                                    paylockflg:payInput[i].paylockflg+1
	                                                    }
	                                    })

	                                    } else {
	                                            var arrItem = ['paydate', 'payremark', 'payflgcd','paylockflg'];
	                                            var arrValue = [payInput[0].paydate, payInput[0].payremark, payInput[0].payflg,payInput[0].paylockflg+1];
	                                            changeDateByRows("cost_list_Two", arrItem, arrValue, false);
	                                    }
	                                    
	                            }
	                    }
						   removeDealRows("cost_list_Two",payDealStatus,cFlg,dealDate);
					}
				}else{
/*					changeDateByRows("cost_list_Two", arrItem, arrValue, false);
					var rows = $('#cost_list_Two').datagrid("getData")['originalRows'];
					
					*/
					var rows = $('#cost_list_Two').datagrid("getSelections"); //获取你选择的所有行
                    for(var i =0;i<payInput.length;i++){
                    var rowIndex = $('#cost_list_Two').datagrid('getRowIndex',rows[i]);//获取某行的行号
                    indexArr.push(rowIndex);
                            if($("#paytypelist").val()=="001"&&payflg==1){
                            $('#cost_list_Two').datagrid('deleteRow', rowIndex);
                            }else if($("#paytypelist").val()=="002"&&payflg==0){
                                    $('#cost_list_Two').datagrid('deleteRow', rowIndex);
                            }else{
                                    if(payInput.length > 1) {                                                        
                                    $('#cost_list_Two').datagrid('updateRow',{
                                                    index:rowIndex,
                                                    row:{
                                                    paydate:payInput[i].paydate,
                                                    payflgcd:payInput[i].payflg,
                                                    paylockflg:payInput[i].paylockflg+1
                                                    }
                                    })

                                    } else {
                                            var arrItem = ['paydate', 'payremark', 'payflgcd','paylockflg'];
                                            var arrValue = [payInput[0].paydate, payInput[0].payremark, payInput[0].payflg,payInput[0].paylockflg+1];
                                            changeDateByRows("cost_list_Two", arrItem, arrValue, false);
                                    }
                                    
                            }
                    }
                    var rows = $('#cost_list_Two').datagrid("getData")['originalRows'];
                    
					if(rows==undefined){
					 rows = $('#cost_list_Two').datagrid('getRows');
					}
					var data = loopFun(rows, [objStorage.getPayStatusShow()], ['payflgcd']);
					var data = lableSpan(data);
				   var dataFil = {
						total: data.length,
						rows: data
					};
					if(dataFil.total != 0){
						$('.switch_table').css('display','block');
						$('.switch_table + .switch_table_none').addClass('hidden');
					}else{
						$('.switch_table').css('display','none');
						$('.switch_table + .switch_table_none').removeClass('hidden');
						$('.switch_table_none div').text(part_language_change_new("TABLE_CHECK_NO"));
						$('.switch_table_none div').attr("name","TABLE_CHECK_NO");
						$('.switch_table_none div').attr("class","i18n");
					}
					$('#cost_list_Two').datagrid({
						loadFilter: pagerFilter
					}).datagrid('loadData', dataFil);
					reselect('#cost_list_Two',indexArr);
					tableHiddenC()
				}							
//					var index = layer.index;
//					layer.close(index);
					showErrorHandler("EXECUTE_SUCCESS", "SUCCESS", "SUCCESS");
					if($('#pay_status').val()=="001"||$('#pay_status').val()=="002"){
						$("#search-detail").click();
					}
				} else {
					showErrorHandler("EXECUTE_FAIL", "ERROR", "ERROR");
				}
			} else {
				showErrorFunHandler(data[$.getRequestMetaName()].result, "ERROR", "ERROR");
			}
		},
		error: function(msg) {
			window.location.href = $.getJumpPath();
		}
	});
}

function  removeDealRows(tableId,payDealStatus,cFlg,dealDate){
	var rows = $('#cost_list_Two').datagrid('getSelections');
	var flg = false;
	//var cflg = $("#payflg").prop("checked");
	for(var i=0;i<rows.length;i++){
		var rowIndex = $('#cost_list_Two').datagrid('getRowIndex',rows[i]);
		var rowDdate = rows[i].paydate;
		var rowPPdate =  rows[i].payplandate;
		var rowHdate = rows[i].payhopedate;
		if((payDealStatus=="002"&&!cFlg)||(payDealStatus=="001"&&cFlg)){
				 $('#cost_list_Two').datagrid('deleteRow', rowIndex);
				 flg=true;
		}else{
			if(dealDate!=""){
				if(rowDdate!=dealDate&&dealDate!=rowHdate&&dealDate!=rowPPdate){
					$('#cost_list_Two').datagrid('deleteRow', rowIndex);
				 flg=true;
				}
			}
		}
	
	}
   	var data = $('#cost_list_Two').datagrid("getData")['originalRows'];
   	var column = $('#cost_list_Two').datagrid("getData")['rows'];
					if(data==undefined){
						 data = $('#cost_list_Two').datagrid('getRows');
					}
					if(data.length==rows.length&&flg){
						$('.switch_table').css('display','none');
						$('.switch_table + .switch_table_none').removeClass('hidden');
						$('.switch_table_none div').text(part_language_change_new("TABLE_CHECK_NO"));
						$('.switch_table_none div').attr("name","TABLE_CHECK_NO");
						$('.switch_table_none div').attr("class","i18n");
					}
//					//如果页面上没有数据了则隐藏滚动条等
//					if(column.length==0){
//						$('.switch_table').css('display','none');
//						$('.switch_table + .switch_table_none').removeClass('hidden');
//						$('.switch_table_none div').text(part_language_change_new("TABLE_CHECK_NO"));
//						$('.switch_table_none div').attr("name","TABLE_CHECK_NO");
//						$('.switch_table_none div').attr("class","i18n");
//					}
					
}

function pageLoad() {
	//获取url参数
	var urlFrom = window.location.href;
	if(urlFrom.indexOf("jobcd") > -1) {
		var ad = urlPars.parm("jobcd");
		$("#job_cd").val(ad);
		sessionSet("BASE","jobSearch");
		
		
	} else {
	
		var ad = urlPars.parm("view");
		var backPage = urlPars.parm("backPage");
		var dalday =  urlPars.parm("dalday");
//		
		if(ad == 4 && backPage == "checkOut")
		{
			$("#jzy").click();
		 	$("#ddda").datebox("setValue",$.getAccountDate());
		}
	 	
		//url路径修改
		if(ad=="5"||ad>6){
			var url = "jczh/top_registration.html";
			if(backPage=="checkOut"){
				url = "jczh/monthbalance.html";
			}
			showLockInfoMsgHandler('DATA_IS_NOT_EXIST',url);
		}
		
	}
	var costListVo = {};
	if(ad==2&& backPage=="checkOut"&&dalday=="dalday1"){
		$("#jzd").click();
		var dalday1 = localStorage.getItem("accountDate").split("/");
		var year = dalday1[0];
		var month  = dalday1[1];
		var new_year = year;
		var new_month = month;
		if(month>12){
		　　　　new_month -=12; 
		　　　　new_year++; 
		　　}
		
		var new_date = new Date(new_year,new_month,1);
		var day = (new Date(new_date.getTime()-1000*60*60*24)).getDate();
		var dalday = year+"-"+month+"-"+day
		$('#dalday1').datebox("setValue",dalday);
		costListVo['dalday1'] = localStorage.getItem("accountDate");
	}
	
    
    costListVo['ad'] = ad;
	var cost = {
		costListVo: costListVo
	}
	$.ajax({
		url: $.getAjaxPath() + "costPageLoad",
		data: JSON.stringify(cost),
		headers: {
			"requestID": $.getRequestID(),
			"requestName": $.getRequestNameByJcZH()
		},
		success: function(data) {
			//console.log(data);
			if(data[$.getRequestMetaName()].result == $.getRequestStatusName()) {
				if(ad!=2&& backPage!="checkOut"&&dalday!="dalday1"){
					$("#jzy").click();
				}
				$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
				objStorage.skip = data[$.getRequestDataName()].skip;
				objStorage.uNodeList = data[$.getRequestUserInfoName()].uNodeList;
				objStorage.costStatus = data[$.getRequestDataName()]['costFlg'];
				objStorage.payStatus = data[$.getRequestDataName()].payStatus;
				objStorage.payStatusShow = data[$.getRequestDataName()].payStatusShow;
				objStorage.tranCode = data[$.getRequestDataName()].tranCode;
				objStorage.outPutStatus = data[$.getRequestDataName()].output;
				SelectObj.mstLableList = data[$.getRequestDataName()].lableList;
				//SelectObj.selectData = data[$.getRequestDataName()];
				var list = {};
				list['pay_status'] = data[$.getRequestDataName()].payStatus;
				//list['costtype'] = data[$.getRequestDataName()].costType;
				list['status'] = data[$.getRequestDataName()].baseStatus;
				list['dis'] = data[$.getRequestDataName()].orgPriceList;
               
				SelectObj.setStringFlg = "_";
				SelectObj.selectData = list;
				SelectObj.setSelectID = ['pay_status', 'status', 'dis'];
				SelectObj.setSelectOfLog();
				//页面初始化存到session中 ，因为放到objStorage 中跳转回来 不刷新页面 执行默认检索拿不到
//				sessionStorage.setItem("costListSkip",JSON.stringify(data[$.getRequestDataName()].skip));
//				sessionStorage.setItem("costListNodeList",JSON.stringify(data[$.getRequestDataName()].uNodeList));
			    sessionStorage.setItem("initList",JSON.stringify(list));
				sessionStorage.setItem("costStatusList",JSON.stringify(data[$.getRequestDataName()]['costFlg']));
				sessionStorage.setItem("payStatusList",JSON.stringify(data[$.getRequestDataName()].payStatus));
//				sessionStorage.setItem("costListTranCode",JSON.stringify(data[$.getRequestDataName()].tranCode));
//				sessionStorage.setItem("costListMstLableList",JSON.stringify(data[$.getRequestDataName()].lableList));
//				sessionStorage.setItem("costListPageNum",data[$.getRequestDataName()].colmst[0][0].page);
//				sessionStorage.setItem("costListColmst",JSON.stringify(data[$.getRequestDataName()].colmst));
				initDataGridHandler('cost_list_Two', data[$.getRequestDataName()].colmst[0][0].page, null, 'top', true, 'isHasFn', data[$.getRequestDataName()].colmst, false, true);
			//	if(data[$.getRequestDataName()].costListByTop.length > 0) {
			    var isSelect = data[$.getRequestDataName()].isSelect;
					var total = data[$.getRequestDataName()].costListByTop.length;
					var sumObj = calSum(data[$.getRequestDataName()].costListByTop);
					$("#saleamtsum").text(formatNumber(sumObj['sumSale']));
			    	$("#plancostamtsum").text(formatNumber(sumObj['sumCost']));
					var afterFormatMoneyDate = formatMoneyAndDate(data[$.getRequestDataName()].costListByTop);
				var data = loopFun(afterFormatMoneyDate, [objStorage.getCostStatus(), objStorage.getPayStatusShow(), objStorage.getOutPutStatus(),
					[],
					[]
				], ['statuscd', 'payflgcd', 'outputflgcd', 'jobendflgcd', 'accountflgcd']);
				//objStorage.reData = data;
				var data = lableSpan(data);
					var dataFil = {
						total: total,
						rows: data
					};
					if(dataFil.total == 0){
							$('.switch_table').css('display','none');
							$('.switch_table + .switch_table_none').removeClass('hidden');
						if(isSelect){
					       $('.switch_table_none div').text(part_language_change_new("TABLE_CHECK_NO"));
					       $('.switch_table_none div').attr("name","TABLE_CHECK_NO");
							$('.switch_table_none div').attr("class","i18n");
					      }else{
							$('.switch_table_none div').text(part_language_change_new("TABLE_CHECK_NO_T"));
							$('.switch_table_none div').attr("name","TABLE_CHECK_NO_T");
							$('.switch_table_none div').attr("class","i18n");
					    }
					}else{
						$('#cost_list_Two').datagrid({
							loadFilter: pagerFilter
						}).datagrid('loadData', dataFil);
						tableHiddenC()
					}
				//}
				labelToMySelect(SelectObj.getMstLableList());
			   
                if(Number(ad)<6&&Number(ad)!=5){
			switchCostSearchAd(ad);
		}
                if($.getDepartID()=="002"){
					$("#status").val("003");
				}
                if($.getDepartID()=="003"){
	    			$("#status").val("002");
	    		}

			} else {
				showErrorFunHandler(data[$.getRequestMetaName()].result, "ERROR", "ERROR");
			}
		},
		error: function(msg) {
			window.location.href = $.getJumpPath();
		}
	});
}
function switchCostSearchAd(ad){
	
	switch(ad){
		case "0":
		  $("#setb2").click();
		 $("#status").val("004");
		break;
		case "1":
		 $("#setb2").click();
		 $("#status").val("003");
		break;
		case "2":
		 $("#setb2").click();
		 $("#dis").val("001");
		 $("#status").val("003");
		break;
		case "3":
		 $("#setb2").click();
		 $("#dis").val("002");
		 $("#status").val("006");
		break;
		case "4":
		 $("#setb2").click();
		 $("#dis").val("003");
		 $("#status").val("003");
		break;
	}
	sessionSet("BASE",1);
}
function getCostList() {

	var list = {
		costListVo: getSearchCondition()
	};
//	console.log(list);
	$.ajax({
		url: $.getAjaxPath() + "getCostList",
		data: JSON.stringify(list),
		headers: {
			"requestID": $.getRequestID(),
			"requestName": $.getRequestNameByJcZH()
		},
		success: function(data) {
//			console.log(data);
			if(data[$.getRequestMetaName()].result == $.getRequestStatusName()) {
				$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
				 if(data.data.TooMuchData!=undefined && data.data.TooMuchData==0){
					 
					 showErrorHandlerActive('TOO_MUCH_DATA_HEAD',3000,'TOO_MUCH_DATA_TAIL','info','info')
	          		   //showErrorHandler('TOO_MUCH_DATA_HEAD'+3000+'TOO_MUCH_DATA_TAIL','info','info');	
	          		   return ;
	          	   }
                objStorage.skip = data[$.getRequestDataName()].skip;
				objStorage.uNodeList = data[$.getRequestUserInfoName()].uNodeList;
				objStorage.costStatus = data[$.getRequestDataName()]['costFlg'];
				objStorage.payStatus = data[$.getRequestDataName()].payStatus;
				objStorage.payStatusShow = data[$.getRequestDataName()].payStatusShow;
				objStorage.tranCode = data[$.getRequestDataName()].tranCode;
				objStorage.outPutStatus = data[$.getRequestDataName()].output;
				SelectObj.mstLableList = data[$.getRequestDataName()].lableList;
				//SelectObj.selectData = data[$.getRequestDataName()];
				initDataGridHandler('cost_list_Two', data[$.getRequestDataName()].colmst[0][0].page, null, 'top', true, 'isHasFn', data[$.getRequestDataName()].colmst, false, true);
			
				var list = {};
				list['pay_status'] = data[$.getRequestDataName()].payStatus;
				//list['costtype'] = data[$.getRequestDataName()].costType;
				list['status'] = data[$.getRequestDataName()].baseStatus;
				list['dis'] = data[$.getRequestDataName()].orgPriceList;
				sessionStorage.setItem("initList",JSON.stringify(list));
				sessionStorage.setItem("costStatusList",JSON.stringify(data[$.getRequestDataName()]['costFlg']));
				sessionStorage.setItem("payStatusList",JSON.stringify(data[$.getRequestDataName()].payStatus));
					//计算合计
				var sumObj = calSum(data[$.getRequestDataName()].costListVo);
				$("#saleamtsum").text(formatNumber(sumObj['sumSale']));
				$("#plancostamtsum").text(formatNumber(sumObj['sumCost']));
				var total = data[$.getRequestDataName()].costListVo.length;
				
				var afterFormatMoneyDate = formatMoneyAndDate(data[$.getRequestDataName()].costListVo);
				var data = loopFun(afterFormatMoneyDate, [objStorage.getCostStatus(), objStorage.getPayStatusShow(), objStorage.getOutPutStatus(),
					[],
					[]
				], ['statuscd', 'payflgcd', 'outputflgcd', 'jobendflgcd', 'accountflgcd']);
               // objStorage.reData = data;
               var data = lableSpan(data);
//				console.log(data);
				var dataFil = {
					total: total,
					rows: data
				}
				if(dataFil.total != 0){
					$('.switch_table').css('display','block');
					$('.switch_table + .switch_table_none').addClass('hidden');
				}else{
					$('.switch_table').css('display','none');
					$('.switch_table + .switch_table_none').removeClass('hidden');
					$('.switch_table_none div').text(part_language_change_new("TABLE_CHECK_NO"));
					$('.switch_table_none div').attr("name","TABLE_CHECK_NO");
					$('.switch_table_none div').attr("class","i18n");
					
				}
				$('#cost_list_Two').datagrid({
					loadFilter: pagerFilter
				}).datagrid('loadData', dataFil);
				//由于第一页的total和其他页的total不相等，easyui会重新发起第一页的请求！加全局变量datagridPageNumber控制
				if(window.datagridPageNumber !== null) {
					$('#cost_list_Two').datagrid({
						loadFilter: pagerFilter
					}).datagrid('loadData', dataFil);
				}
				tableHiddenC()
				labelToMySelect(SelectObj.getMstLableList());
			} else {
				showErrorFunHandler(data[$.getRequestMetaName()].result, "ERROR", "ERROR");
			}
		},
		error: function(msg) {
			window.location.href = $.getJumpPath();
		}
	});
}
//格式化 日期和金钱
function formatMoneyAndDate(data) {
	for(var i = 0; i < data.length; i++) {
		//格式化金钱		
		data[i]['amt'] = formatNumber(data[i]['amt']);
		data[i]['payamt'] = formatNumber(data[i]['payamt']);
		data[i]['saleamt'] = formatNumber(data[i]['saleamt']);
		data[i]['vatamt'] = formatNumber(data[i]['vatamt']);
		//data[i]['amt'] = formatNumber(data[i]['amt']);
		//格式化日期
		data[i]['confirmdate'] = (data[i]['confirmdate'] == null || data[i]['confirmdate'] == "") ? "" : strToDate(data[i]['confirmdate'], 1);
		data[i]['adddate'] = (data[i]['adddate'] == null || data[i]['adddate'] == "") ? "" : strToDate(data[i]['adddate'], 1);
		data[i]['payreqdate'] = (data[i]['payreqdate'] == null || data[i]['payreqdate'] == "") ? "" : strToDate(data[i]['payreqdate'], 1);
	}
	return data;
}
//循环获取状态位对应显示
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
		//原价状态
		case 'statuscd':
			for(var i = 0; i < data.length; i++) {
				if(data[i].discd == "001") {
					switch(data[i][colname]) {
						case "0":
							val = searchValue(list, "itemcd", "001", 'itmname');
							valEn = searchValue(list, "itemcd", "001", 'itemname_en');
							valJp = searchValue(list, "itemcd", "001", 'itemname_jp');
							valHk = searchValue(list, "itemcd", "001", 'itemname_hk');
							break;
						case "1":
							val = searchValue(list, "itemcd", "002", 'itmname');
							valEn = searchValue(list, "itemcd", "002", 'itemname_en');
							valJp = searchValue(list, "itemcd", "002", 'itemname_jp');
							valHk = searchValue(list, "itemcd", "002", 'itemname_hk');
							break;
						case "2":
							val = searchValue(list, "itemcd", "003", 'itmname');
							valEn = searchValue(list, "itemcd", "003", 'itemname_en');
							valJp = searchValue(list, "itemcd", "003", 'itemname_jp');
							valHk = searchValue(list, "itemcd", "003", 'itemname_hk');
							break;
						case "3":
							val = searchValue(list, "itemcd", "004", 'itmname');
							valEn = searchValue(list, "itemcd", "004", 'itemname_en');
							valJp = searchValue(list, "itemcd", "004", 'itemname_jp');
							valHk = searchValue(list, "itemcd", "004", 'itemname_hk');
							break;
						case "4":
							val = searchValue(list, "itemcd", "004", 'itmname');
							valEn = searchValue(list, "itemcd", "004", 'itemname_en');
							valJp = searchValue(list, "itemcd", "004", 'itemname_jp');
							valHk = searchValue(list, "itemcd", "004", 'itemname_hk');
							break;
					}
				}
				if(data[i].discd == "002") {
					if(data[i][colname] == '3') {
						val = searchValue(list, "itemcd", "004", 'itmname');
						valEn = searchValue(list, "itemcd", "004", 'itemname_en');
						valJp = searchValue(list, "itemcd", "004", 'itemname_jp');
						valHk = searchValue(list, "itemcd", "004", 'itemname_hk');
					} else {
						if(data[i]['bl'] == '1' && data[i][colname] != '1') {
							val = searchValue(list, "itemcd", "005", 'itmname');
							valEn = searchValue(list, "itemcd", "005", 'itemname_en');
							valJp = searchValue(list, "itemcd", "005", 'itemname_jp');
							valHk = searchValue(list, "itemcd", "005", 'itemname_hk');
						} else {
							switch(data[i][colname]) {
								case "0":
									val = searchValue(list, "itemcd", "002", 'itmname');
									valEn = searchValue(list, "itemcd", "002", 'itemname_en');
									valJp = searchValue(list, "itemcd", "002", 'itemname_jp');
									valHk = searchValue(list, "itemcd", "002", 'itemname_hk');
									break;
								case "1":

									val = searchValue(list, "itemcd", "004", 'itmname');
									valEn = searchValue(list, "itemcd", "004", 'itemname_en');
									valJp = searchValue(list, "itemcd", "004", 'itemname_jp');
									valHk = searchValue(list, "itemcd", "004", 'itemname_hk');
									break;
								case "2":

									//							  	val = searchValue(list, "itemcd", "005", 'itmname');
									//								valEn = searchValue(list, "itemcd", "005", 'itemname_en');
									//								valJp = searchValue(list, "itemcd", "005", 'itemname_jp');
									//								valHk = searchValue(list, "itemcd", "005", 'itemname_hk');

									break;

							}
						}

					}

				}
				if(data[i].discd == "003") {
					switch(data[i][colname]) {
						case "0":
							val = searchValue(list, "itemcd", "002", 'itmname');
							valEn = searchValue(list, "itemcd", "002", 'itemname_en');
							valJp = searchValue(list, "itemcd", "002", 'itemname_jp');
							valHk = searchValue(list, "itemcd", "002", 'itemname_hk');
							break;
						case "1":
							val = searchValue(list, "itemcd", "004", 'itmname');
							valEn = searchValue(list, "itemcd", "004", 'itemname_en');
							valJp = searchValue(list, "itemcd", "004", 'itemname_jp');
							valHk = searchValue(list, "itemcd", "004", 'itemname_hk');
							break;
						case "2":
							val = searchValue(list, "itemcd", "004", 'itmname');
							valEn = searchValue(list, "itemcd", "004", 'itemname_en');
							valJp = searchValue(list, "itemcd", "004", 'itemname_jp');
							valHk = searchValue(list, "itemcd", "004", 'itemname_hk');
							break;
					}
				}
				data[i].statuszc = val;
				data[i].statusen = valEn;
				data[i].statusjp = valJp;
				data[i].statuszt = valHk;
			}

			break;
			//支付状态
		case 'payflgcd':
			for(var i = 0; i < data.length; i++) {
				if(data[i].discd == "001") {
					switch(data[i][colname]) {
						case "0":
							val = searchValue(list, "itemcd", "001", 'itmname');
							valEn = searchValue(list, "itemcd", "001", 'itemname_en');
							valJp = searchValue(list, "itemcd", "001", 'itemname_jp');
							valHk = searchValue(list, "itemcd", "001", 'itemname_hk');
							break;
						case "1":
							val = searchValue(list, "itemcd", "002", 'itmname');
							valEn = searchValue(list, "itemcd", "002", 'itemname_en');
							valJp = searchValue(list, "itemcd", "002", 'itemname_jp');
							valHk = searchValue(list, "itemcd", "002", 'itemname_hk');
							break;
					}
					if(data[i][colname]==null){
						val = searchValue(list, "itemcd", "001", 'itmname');
						valEn = searchValue(list, "itemcd", "001", 'itemname_en');
						valJp = searchValue(list, "itemcd", "001", 'itemname_jp');
						valHk = searchValue(list, "itemcd", "001", 'itemname_hk');
					}
					data[i].payflgzc = val;
					data[i].payflgen = valEn;
					data[i].payflgjp = valJp;
					data[i].payflgzt = valHk;
				}
			}
			break;
			//经费科目
//					case 'tranitemcode':
//						for(var i = 0; i < data.length; i++) {
//							switch(data[i][colname]) {
//								case "001":
//									val = searchValue(list, "itemcd", "001", 'itmname');
//									valEn = searchValue(list, "itemcd", "001", 'itemname_en');
//									valJp = searchValue(list, "itemcd", "001", 'itemname_jp');
//									valHk = searchValue(list, "itemcd", "001", 'itemname_hk');
//									break;
//								case "002":
//									val = searchValue(list, "itemcd", "002", 'itemname');
//									valEn = searchValue(list, "itemcd", "002", 'itemname_en');
//									valJp = searchValue(list, "itemcd", "002", 'itemname_jp');
//									valHk = searchValue(list, "itemcd", "002", 'itemname_hk');
//									break;
//								case "003":
//									val = searchValue(list, "itemcd", "003", 'itemname');
//									valEn = searchValue(list, "itemcd", "003", 'itemname_en');
//									valJp = searchValue(list, "itemcd", "003", 'itemname_jp');
//									valHk = searchValue(list, "itemcd", "003", 'itemname_hk');
//									break;
//								case "004":
//									val = searchValue(list, "itemcd", "004", 'itmname');
//									valEn = searchValue(list, "itemcd", "004", 'itemname_en');
//									valJp = searchValue(list, "itemcd", "004", 'itemname_jp');
//									valHk = searchValue(list, "itemcd", "004", 'itemname_hk');
//									break;
//							}
//							data[i].tranitemnamezc = val;
//							data[i].tranitemnameen = valEn;
//							data[i].tranitemnamejp = valJp;
//							data[i].tranitemnamezt = valHk;
//						}
//						break;
			//印刷出力
		case 'outputflgcd':
			for(var i = 0; i < data.length; i++) {
				if(data[i].discd == "001") {
					switch(data[i][colname]) {
						case "0":
							val = searchValue(list, "itemcd", "001", 'itmname');
							valEn = searchValue(list, "itemcd", "001", 'itemname_en');
							valJp = searchValue(list, "itemcd", "001", 'itemname_jp');
							valHk = searchValue(list, "itemcd", "001", 'itemname_hk');
							break;
						case "1":
							val = searchValue(list, "itemcd", "002", 'itmname');
							valEn = searchValue(list, "itemcd", "002", 'itemname_en');
							valJp = searchValue(list, "itemcd", "002", 'itemname_jp');
							valHk = searchValue(list, "itemcd", "002", 'itemname_hk');
							break;
					}
					data[i].outputflgzc = val;
				    data[i].outputflgen = valEn;
				    data[i].outputflgjp = valJp;
				    data[i].outputflgzt = valHk;
				}
				

			}
			break;
			//jobendflg
		case 'jobendflgcd':
			for(var i = 0; i < data.length; i++) {

				switch(data[i][colname]) {
					case "0":
						val = '未';
						break;
					case "1":
						val = '终了';
						break;

				}
				data[i].jobendflg = val;
			}
			break;
			//締め終了フラグ 0:未；1：终了
		case 'accountflgcd':
			for(var i = 0; i < data.length; i++) {

				switch(data[i][colname]) {
					case "0":
						val = '未';
						break;
					case "1":
						val = '终了';
						break;

				}
				data[i].accountflg = val;
			}
			break;

	}
	//objStorage.reData = data;
	return data;
}
function prePageLoad(){
	 var sideBar = urlPars.parm("sideBar");
	 var view =  urlPars.parm("view");
	 if(sideBar==undefined){
	 	if(view==6){
	 			sessionGet();
	 	}else{
	 			clearSession();
	 	pageLoad();
	 	}
	 }else{
	 	clearSession();
	 	pageLoad();
	 	
	 }
}
function sessionGet(){
	           var list = JSON.parse(sessionStorage.getItem("initList"));
	           objStorage.costStatus = JSON.parse(sessionStorage.getItem("costStatusList"));
	           objStorage.payStatus = JSON.parse(sessionStorage.getItem("payStatusList"));
//	           objStorage.skip =  JSON.parse(sessionStorage.getItem("costListSkip"));
//	           objStorage.uNodeList = JSON.parse(sessionStorage.getItem("costListNodeList"));
//			   objStorage.tranCode = JSON.parse(sessionStorage.getItem("costListTranCode"));
			 //  SelectObj.mstLableList =  JSON.parse(sessionStorage.getItem("costListMstLableList"));
//			   sessionStorage.setItem("costListPageNum",data[$.getRequestDataName()].colmst[0][0].page);
//			   sessionStorage.setItem("costListColmst",JSON.stringify(data[$.getRequestDataName()].colmst));
//	          
//	          
//	         
	            SelectObj.setStringFlg = "_";
				SelectObj.selectData = list;
				SelectObj.setSelectID = ['pay_status', 'status', 'dis'];
				SelectObj.setSelectOfLog();
//				 initDataGridHandler('cost_list_Two', sessionStorage.getItem("costListPageNum"), null, 'top', true, 'isHasFn', data[$.getRequestDataName()].colmst, false, true);
//				labelToMySelect(SelectObj.getMstLableList());
	var searchType_K= sessionStorage.getItem("searchType_K");
	switch(searchType_K){
	  case "0":
		var costListKeyWord_K = sessionStorage.getItem("costListKeyWord_K");
			$("#setb1").click();
		$("#keyword").val(costListKeyWord_K);
		$('#vogueSearch').click();
		break;
	  case "1":
	    $("#setb2").click();
	    var costListJobNo_K = sessionStorage.getItem("costListJobNo_K");
		var costListCldivName_K	 = sessionStorage.getItem("costListCldivName_K");
		var	costListCldivNo_K	=  sessionStorage.getItem("costListCldivNo_K");
		var	costListDlvDayS_K	=sessionStorage.getItem("costListDlvDayS_K");
		var	costListDlvDayE_K	=sessionStorage.getItem("costListDlvDayE_K");
		var	costListDlvMonth_K	=sessionStorage.getItem("costListDlvMonth_K");
		var	costListDis_K	 =sessionStorage.getItem("costListDis_K");
		var	costListPayeeName_K	 =sessionStorage.getItem("costListPayeeName_K");
		var	costListPayeeNo_K	= sessionStorage.getItem("costListPayeeNo_K");
		var	costListCostNo_K	=sessionStorage.getItem("costListCostNo_K");
		var	costListInputNo_K	= sessionStorage.getItem("costListInputNo_K");
		var	costListCaseName_K	=sessionStorage.getItem("costListCaseName_K");
		var	costListLabel_K	 =sessionStorage.getItem("costListLabel_K");
		var	costListStatus_K = sessionStorage.getItem("costListStatus_K");
		var	costListPayDlVDay_K	=sessionStorage.getItem("costListPayDlVDay_K");
		 $("#job_cd").val(costListJobNo_K);
		 $("#cldiv_name").val(costListCldivName_K);
		 $("#cldiv_cd").val(costListCldivNo_K);
		 $("#dis").val(costListDis_K);
		 $("#payee_name").val(costListPayeeName_K);
		 $("#payee_cd").val(costListPayeeNo_K);
		 $("#cost_no").val(costListCostNo_K);
		 $("#input_no").val(costListInputNo_K);
		 $("#name").val(costListCaseName_K);
		 $("#lable").val(costListLabel_K);
		 $("#status").val(costListStatus_K);	
		 $('#paydlyday').datebox("setValue",costListPayDlVDay_K);
         if(costListDlvMonth_K==""){
		 	 $('#dalday').datebox("setValue",costListDlvDayS_K);
		     $('#dalday1').datebox("setValue",costListDlvDayE_K);
		 }else{
		 	$("#jzy").click();
		 	$("#ddda").datebox("setValue",costListDlvMonth_K!=null?costListDlvMonth_K:"");
		 }
		 
		 $("#search-detail1").click();
		break;
	  case "2":
	   $("#setb2").click();
	   $(".other").click();
	    var costListInvoiceNo_K= sessionStorage.getItem("costListInvoiceNo_K");
		var costListInvDateS_K = sessionStorage.getItem("costListInvDateS_K");
		var costListInvDateE_K =sessionStorage.getItem("costListInvDateE_K");
		var costListHopeDate_K =sessionStorage.getItem("costListHopeDate_K");
		var costListPaySatus_K =sessionStorage.getItem("costListPaySatus_K");
		var costListCostType_K =sessionStorage.getItem("costListCostType_K");
		
		 $('#invoicno').val(costListInvoiceNo_K);
		 $('#invoicedate').datebox("setValue",costListInvDateS_K);
		$('#invoicedate1').datebox("setValue",costListInvDateE_K);
		$('#payhopedate').datebox("setValue",costListHopeDate_K);
		$('#pay_status').val(costListPaySatus_K);
		$('#costtype').val(costListCostType_K);
		 $("#search-detail").click();
		break;
	  default:
		clearSession();
	 	pageLoad();
	}
}
function sessionSet(disVal,searchFlg){
	 if(disVal == "BASE"){
		var costListJobNo_K = $("#job_cd").val();
		var costListCldivName_K	 = $("#cldiv_name").val();
		var	costListCldivNo_K	= $("#cldiv_cd").val();
		var	costListDlvDayS_K	= $('#dalday').datebox("getValue");
		var	costListDlvDayE_K	= $('#dalday1').datebox("getValue");
		var	costListDlvMonth_K	= $("#ddda").datebox("getValue");
		var	costListDis_K	 = $("#dis").val();
		var	costListPayeeName_K	 = $("#payee_name").val();
		var	costListPayeeNo_K	= $("#payee_cd").val();
		var	costListCostNo_K	= $("#cost_no").val();
		var	costListInputNo_K	= $("#input_no").val();
		var	costListCaseName_K	= $("#name").val();
		var	costListLabel_K	 = $("#lable").val();
		var	costListStatus_K = $("#status").val();	
		var	costListPayDlVDay_K	= $('#paydlyday').datebox("getValue");

		sessionStorage.setItem("costListJobNo_K",costListJobNo_K);
		sessionStorage.setItem("costListCldivName_K",costListCldivName_K);
		sessionStorage.setItem("costListCldivNo_K",costListCldivNo_K);
		sessionStorage.setItem("costListDlvDayS_K",costListDlvDayS_K);
		sessionStorage.setItem("costListDlvDayE_K",costListDlvDayE_K);
		sessionStorage.setItem("costListDlvMonth_K",costListDlvMonth_K);
		sessionStorage.setItem("costListDis_K",costListDis_K);
		sessionStorage.setItem("costListPayeeName_K",costListPayeeName_K);
		sessionStorage.setItem("costListPayeeNo_K",costListPayeeNo_K);
		sessionStorage.setItem("costListCostNo_K",costListCostNo_K);
		sessionStorage.setItem("costListInputNo_K",costListInputNo_K);
		sessionStorage.setItem("costListCaseName_K",costListCaseName_K);
		sessionStorage.setItem("costListLabel_K",costListLabel_K);
		sessionStorage.setItem("costListStatus_K",costListStatus_K);
		sessionStorage.setItem("costListPayDlVDay_K",costListPayDlVDay_K);
		
		sessionStorage.setItem("searchType_K",1);
	  }else if(disVal == "OTHER"){
	  	var costListInvoiceNo_K	 = $('#invoicno').val();
		var costListInvDateS_K = $('#invoicedate').datebox("getValue");
		var costListInvDateE_K = $('#invoicedate1').datebox("getValue");
		var costListHopeDate_K = $('#payhopedate').datebox("getValue");
		var costListPaySatus_K = $('#pay_status').val();
		var costListCostType_K = $('#costtype').val();
		
		sessionStorage.setItem("costListInvoiceNo_K",costListInvoiceNo_K);
		sessionStorage.setItem("costListInvDateS_K",costListInvDateS_K);
		sessionStorage.setItem("costListInvDateE_K",costListInvDateE_K);
		sessionStorage.setItem("costListHopeDate_K",costListHopeDate_K);
		sessionStorage.setItem("costListPaySatus_K",costListPaySatus_K);
		sessionStorage.setItem("costListCostType_K",costListCostType_K);
		
		sessionStorage.setItem("searchType_K",2);
	  }else if(disVal == "VOGUE"){
	  	var costListKeyWord_K = $('#keyword').val();
	  	
	  	sessionStorage.setItem("costListKeyWord_K",costListKeyWord_K);
	  	sessionStorage.setItem("searchType_K",0);
	  }
	if(searchFlg==undefined){
		 getCostList();
	}
	 
}
//获取检索条件 
function getSearchCondition() {
	var costListVo = {};
	//检索方式
	var type_val = $(".sale_rate_click").find(".active").find("input").val();
	if(type_val == "1") {
		costListVo['keyword'] = $('#keyword').val();
		costListVo['searchType'] = 2;
		return costListVo;
	}
	//l检索类型flg
	var flg_class = $('#myTab').find('.active').find('center').prop('class').split(" ")[1];
	//详细检索 基本
	if(type_val == "0" && flg_class == "base") {
		costListVo['jobcd'] = $('#job_cd').val();
		costListVo['cldivcd'] = $('#cldiv_cd').val();
		costListVo['cldivname'] = $('#cldiv_name').val();
		if($("#jzd").hasClass("active")){
			costListVo['dalday'] = $('#dalday').datebox("getValue");
			costListVo['dalday1'] = $('#dalday1').datebox("getValue");
		}else{
			costListVo['dalmon'] = $('#ddda').datebox("getValue");
		}
		costListVo['discd'] = $('#dis').val();
		costListVo['paycd'] = $('#payee_cd').val();
		costListVo['payeename'] = $('#payee_name').val();
		costListVo['costno'] = $('#cost_no').val();
		costListVo['inputno'] = $('#input_no').val();
		costListVo['name'] = $('#name').val();
		costListVo['lable'] = $('#lable').val();
		costListVo['status'] = $('#status').val();
		costListVo['paydlyday'] = $('#paydlyday').datebox("getValue");
		costListVo['lable'] = $('#lable').val();
		//后台判断走哪个查询
		costListVo['searchType'] = 1;
		costListVo['conditionFlg']=seachCondition(costListVo);
		return costListVo;
	}
	//详细检索 其他
	if(type_val == "0" && flg_class == "other") {
		costListVo['invoicno'] = $('#invoicno').val();
		costListVo['invoicedate'] = $('#invoicedate').datebox("getValue");
		costListVo['invoicedate1'] = $('#invoicedate1').datebox("getValue");
		costListVo['payhopedate'] = $('#payhopedate').datebox("getValue");
		costListVo['payflg'] = $('#pay_status').val();
		costListVo['costtype'] = $('#costtype').val();
		costListVo['searchType'] = 0;
		costListVo['conditionFlg']=seachCondition(costListVo);
		return costListVo;
	}

}
//计算合计
function calSum(data) {
	var sum = {};
	//根据jobcd 去除相同数据 计算job卖上
	var arrForSumSale = unique(data);
	var sumSale = 0;
	var sumCost = 0;
	for(var i = 0; i < arrForSumSale.length; i++) {
		//sumSale += parseFloat(arrForSumSale[i]['saleamt']);
		sumSale = floatObj.add(sumSale,arrForSumSale[i]['saleamt']);
	}
	for(var k = 0; k < data.length; k++) {
		if(data[k]['amt'] != "" && data[k]['amt'] != null) {
			//sumCost += parseFloat(data[k]['amt']);
			sumCost = floatObj.add(sumCost,data[k]['amt']);
		}
	}
	var point = $.getDefaultPoint();
	return {
		"sumSale": sumSale.toFixed(point),
		"sumCost": sumCost.toFixed(point)
	};
}
//去除重复数据
function unique(list) {
	var arr = [];
	for(var i = 0; i < list.length; i++) {
		if(i == 0) arr.push(list[i]);
		b = false;
		if(arr.length > 0 && i > 0) {
			for(var j = 0; j < arr.length; j++) {
				if(arr[j].jobcd == list[i].jobcd) {
					b = true;
					//break;
				}
			}
			if(!b) {
				arr.push(list[i]);
			}
		}
	}
	return arr;
}
//datagrid tooltip icon语言国际化
function toolTipLanguage(language){
	if(language == undefined){
		language = localStorage.getItem('language');
	}
	var arr_language = [];
	var icon_language = [];
	switch (language){
		case 'jp':
			var toolTipO = "一覧の列幅を更新する",
			toolTip2 = '処理メニューを表示する',
			toolTip3 = '一覧をダウンロードする',
			toolTip4 = '一覧カスタマイズ画面へ遷移する';
			break;
		case 'zc':
			var toolTipO = "调整一览表的列宽",
			toolTip2 = '显示处理菜单',
			toolTip3 = '下载搜索的结果',
			toolTip4 = '跳转到一览自定义设定画面';
			break;
		case 'en':
			var toolTipO = "Update the column width of the list.",
			toolTip2 = 'Display processable Menu.',
			toolTip3 = 'Download Data.',
			toolTip4 = 'Transit to Customize List.';
			break;
		case 'zt':
			var toolTipO = "調整一覽表的列寬",
			toolTip2 = '顯示處理菜單',
			toolTip3 = '下載搜索的結果',
			toolTip4 = '跳轉到一覽自定義設定畫面';
			break;
	}	
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
function tableHiddenC(){
	if($('.datagrid-view').hasClass('top-40')){
		var heiTop = $('.datagrid-view').height()+40;
		$('.datagrid-view').height(heiTop);
	}
}
function clearSession(){
	sessionStorage.removeItem("searchType_K");
	sessionStorage.removeItem("costListKeyWord_K");
	sessionStorage.removeItem("costListJobNo_K");
	sessionStorage.removeItem("costListCldivName_K");
	sessionStorage.removeItem("costListCldivNo_K");
	sessionStorage.removeItem("costListDlvDayS_K");
	sessionStorage.removeItem("costListDlvDayE_K");
	sessionStorage.removeItem("costListDlvMonth_K");
	sessionStorage.removeItem("costListDis_K");
	sessionStorage.removeItem("costListPayeeName_K");
	sessionStorage.removeItem("costListPayeeNo_K");
	sessionStorage.removeItem("costListCostNo_K");
	sessionStorage.removeItem("costListInputNo_K");
	sessionStorage.removeItem("costListCaseName_K");
	sessionStorage.removeItem("costListLabel_K");
	sessionStorage.removeItem("costListStatus_K");
	sessionStorage.removeItem("costListPayDlVDay_K");
	sessionStorage.removeItem("costListInvoiceNo_K");
	sessionStorage.removeItem("costListInvDateS_K");
	sessionStorage.removeItem("costListInvDateE_K");
	sessionStorage.removeItem("costListHopeDate_K");
	sessionStorage.removeItem("costListPaySatus_K");
	sessionStorage.removeItem("costListCostType_K");
}
//如果没有输入任何检索条件 返回 true 反之返回 false
function seachCondition(costListVo) {
	var conditionFlg = false;
	if(costListVo['searchType'] == 1){
		if(costListVo['jobcd']==""&&costListVo['cldivcd']==""&&costListVo['cldivname']==""&&costListVo['discd']==""&&costListVo['paycd']==""&&
				   costListVo['payeename']==""&&costListVo['costno']==""&&costListVo['inputno']==""&&costListVo['name']==""&&costListVo['lable']==""&&
				   costListVo['status']==""&&costListVo['paydlyday']==""&&costListVo['lable']==""){
			conditionFlg = true;
			
		}else{
			return false;
		}
		if($("#jzd").hasClass("active")){
			if(costListVo['dalday']==""&&costListVo['dalday1']==""){
				conditionFlg = true;
			}else{
				return false;
			}
		}else{
			if(costListVo['dalmon']==""){
				conditionFlg = true;
			}else{
				return false;
			}
		}
	}
	if(costListVo['searchType'] == 0){
		
		costListVo['invoicno'] = $('#invoicno').val();
		costListVo['invoicedate'] = $('#invoicedate').datebox("getValue");
		costListVo['invoicedate1'] = $('#invoicedate1').datebox("getValue");
		costListVo['payhopedate'] = $('#payhopedate').datebox("getValue");
		costListVo['payflg'] = $('#pay_status').val();
		costListVo['costtype'] = $('#costtype').val();
		if(costListVo['invoicno']==""&&costListVo['invoicedate']==""&&costListVo['invoicedate1']==""&&costListVo['payhopedate']==""&&
				costListVo['payflg']==""&&costListVo['costtype']==""){
			conditionFlg = true;
		}else{
			return false;
		}
	}
	return  conditionFlg;
}
objStorage = {
	payStatus: null,
	costType: null,
	baseStatus: null,
	skip: null,
	uNodeList: null,
	orgPriceList: null,
	costStatus: null,
	tranCode: null,
	reData: null,
	list:null,
	outPutStatus:null,
	payStatusShow:null,
	getPayStatus: function() {
		return this.payStatus;
	},
	getCostType: function() {
		return this.costType;
	},
	getSkip: function() {
		return this.skip;
	},
	getUNodeList: function() {
		return this.uNodeList;
	},
	getBaseStatus: function() {
		return this.baseStatus;
	},
	getOrgPriceList: function() {
		return this.orgPriceList;
	},
	getCostStatus: function() {
		return this.costStatus;
	},
	getTranCode: function() {
		return this.tranCode;
	},
	getReData: function() {
		return this.reData;
	},
	getList:function(){
		return this.list;
	},
	getOutPutStatus:function(){
		return this.outPutStatus;
	},
	getPayStatusShow:function(){
		return this.payStatusShow;
	}
}