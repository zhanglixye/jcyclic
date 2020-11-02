$(document).ready(function(){
	//boostrasp collapse监听
	$('#collapseOne').on('show.bs.collapse', function () {
	    $('.iconfont.icon-sanjiao').closest('span').addClass('deg180');
	})
	$('#collapseOne').on('hidden.bs.collapse', function () {
	    $('.iconfont.icon-sanjiao').closest('span').removeClass('deg180');
	})
	$("#job_i").click(function() {
		if($("#job_i").attr("value") == 1) {
			$("#rotat").css({
				"margin-top":"-1px",
				"transform":"rotate(180deg)",
				"transition": "all 0s ease-in-out",
				"display": "inline-block",
				"float": "right",
				"height":"25px"
			});
			$("#job_i").attr("value", 0);
		} else {
			$("#rotat").css({
				"margin-top":"-1px",
				"transform":"rotate(0deg)",
				"transition": "all 0s ease-in-out",
				"display": "inline-block",
				"float": "right",
				"height":"25px"
			});
			$("#job_i").attr("value", 1);
		}
	})
})
$(function() {
	var jobcd=getQueryString("jobcd","jcst/salescategory_approval.html");
	//var saleno=getQueryString("saleno","jcst/salescategory_approval.html");
	//var saleno = sessionStorage.getItem("saleno");
	
	var job = {
		job_cd: jobcd,
		sale_remark: $("#sale_remark").val()
		//saleno: saleno
	}
	$.jobCommon();
	$.layerShowDiv('setb2','29%','auto',1,$('#setbig1'));
	initShow(job);
	

});
function afterAjax(job){
	var saleLockFlg = objStorage.getSaleLockFlg();
	job['saleLockFlg'] = saleLockFlg;
	$('.admin').click(function() {
		job['ad_flg'] = 1;
		job['sale_admit_remark'] = $("#sale_remark").val();
		job['jlTrn'] = getLable();
		doSaleAdmin(job);
	});
	$('.cancel').click(function() {
		job['ad_flg'] = 0;
		job['sale_admit_remark'] = $("#sale_remark").val();
		job['jlTrn'] = getLable();
		var msg = showConfirmMsgHandler("DELETECONFIRM");
	    var confirmTitle = $.getConfirmMsgTitle();
	    $.messager.confirm(confirmTitle,msg, function(r){
		if(r){
			doSaleAdmin(job);
		}else{
			return ;
		}
		});
	});
	$('#language').change(function() {
		setPersonMoneyCode();
		if(objStorage.getSaleMoneyRow().length>0){
		changeMoneyStyle(objStorage.getSaleMoneyRow(), "real_foreign_money_code");
	}
	if(objStorage.getPlanMoneyRow().length>0){
		changeMoneyStyle(objStorage.getPlanMoneyRow(), "plan_foreign_money_code");
	}
	});
	$('#sale-ad-back').click(function(){
		window.location.href = $.getJumpPath()+"jcst/job_registration_list.html?view=init&menu=se";
	});
	
	$(".add_lable").click(function() {
		addLable("new_lable", "options_lable","0");
	});
}
function doSaleAdmin(job) {
	$.ajax({
		url: $.getAjaxPath() + "doSaleAdmin",
		data: JSON.stringify(job),
		headers: {
			"requestID": $.getRequestID(),
			"requestName": $.getRequestNameByJcZH()
		},
		success: function(data) {
//			console.log(data);
			if(data[$.getRequestMetaName()].result == $.getRequestStatusName()) {
				$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
				if(data[$.getRequestDataName()] > 0) {
					
				   if(data[$.getRequestDataName()]==540){
				   	var url =  "jcst/job_registration_list.html?view=init&menu=se";
				   	showLockInfoMsgHandler('STATUS_VALIDATEPOWER_ERROR',url);
                   	return ;
                   }
                   if(data[$.getRequestDataName()]==550){
                   	showErrorHandler('SYS_VALIDATEPOWER_ERROR','info','info');
                   	window.location.href = $.getJumpPath()+"jcst/top_registration.html";
                   	return ;
                   }
                    var jobcd = $("#jobcd").text();
                    var url = "jcst/job_registration_list.html?view=init&menu=se";
                    var outFlg = $(".adminPdf-flg").prop("checked");
                    if(outFlg){
						OutPutPdfHandler(jobcd,"","","saleAdminPdf","","","",0);
				 	}
				 	if(outFlg!="1"){
				 		window.location.href  = $.getJumpPath()+url;
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

function initShow(job) {
	$.ajax({
		url: $.getAjaxPath() + "selectSaleAdmin",
		data: JSON.stringify(job),
		headers: {
			"requestID": $.getRequestID(),
			"requestName": $.getRequestNameByJcZH()
		},
		success: function(data) {
			if(data[$.getRequestMetaName()].result == $.getRequestStatusName()) {
				$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
				if(data[$.getRequestDataName()] != null) {
					var result = data[$.getRequestDataName()];
					objStorage.saleMoneyRow = result['sale_foreign'];
					objStorage.planMoneyRow = result['plan_sale_foreign'];
					objStorage.moneyMessage = data['userInfo'];
					objStorage.listTax = result['list_tax'];
					objStorage.listForeignTax = result['list_foreign_tax'];
					objStorage.saleLockFlg = result['saleLockFlg'];
					var lable_list = result['list_lable'];
					SelectObj.setLableList = lable_list;
					setPageMessage(result);
					if(result['sale_no'] != "" || result['sale_no'] != null) {
						var jobcd = job['job_cd'];
						window.job_cd=jobcd;
					$.layerShowDiv('job_detail','70%','80%',2,'../jcst/job_detail.shtml');
					}else{
						var jobcd = job['job_cd'];
     					window.job_cd=jobcd;
					$.layerShowDiv('job_detail','70%','80%',2,'../jcst/job_detail_nosale.html');
					}
					labelToMySelect(lable_list);
				    SelectObj.setChooseLableList = result['jlTrn'];
				    setLableArea(result['jlTrn']);
					lableShowByPower();
					//账票出力checkbox
					if(data[$.getRequestDataName()].outFlg_list[0].itemvalue == "1") {
						$(".lastChild").find("input").prop("checked", "checked");
					} else {
						$(".lastChild").find("input").prop("checked", false);
					}
					if(result['isAdmin']!=0){
						$(".lastChild").find("input").prop("checked", false);
						$(".lastChild").addClass("hidden");
					}
					
					afterAjax(job);
					
				} else {
//					showErrorHandler("EXECUTE_FAIL", "ERROR", "ERROR");
                    var url = "jcst/job_registration_list.html?view=init&menu=se";
				    showLockInfoMsgHandler('DATA_IS_NOT_EXIST',url);
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

function setPageMessage(result) {
	//根据结果中的 isAdmin值判断显示什么按钮 0 显示承认，1 却下
	$("#sale-title").addClass("i18n");
	if(result['isAdmin'] == 0) {
		$('.admin').removeClass("hidden");
		$('#sale-ad-title').removeClass("hidden");
		$("#sale-title").attr("name","common__sideBar_businessRec");
		
		readLanguageFile();
		
	} else {
		$('.cancel').removeClass("hidden");
		$("#sale-cancel-title").removeClass("hidden");
		$("#sale-title").attr("name","menu7");
		readLanguageFile();
	}
	//记上月
	$('.dlvday').text(strToDate(result['dlvday'], 0));
	//请款预定日
	$('.plan_req_date').text(strToDate(result['dlvday'], 1));
	//实际売上金额
	$('#sale_foreign_amt').text(formatNumber(result['sale_foreign_amt']));
	//实际增值税
	$('#sale_vat_amt').text(formatNumber(result['sale_vat_amt']));
	//实际请求金额
	$('#req_amt').text(formatNumber(result['req_amt']));
	//
	$('#sale_foreign_amt1').text(formatNumber(result['sale_foreign_amt']));
	$('#sale_vat_amt1').text(formatNumber(result['sale_vat_amt']));
	$('#req_amt1').text(formatNumber(result['req_amt']));
	//预计売上金额
	$('#plan_sale_foreign_amt').text(formatNumber(result['plan_sale_foreign_amt']));
	//预计增值税
	$('#plantax').text(formatNumber(result['planvatamt']));
	//预计请求金额
	$('#plan_req_amt').text(formatNumber(result['plan_req_amt']));
	//
	$('#plan_sale_foreign_amt1').text(formatNumber(result['plan_sale_foreign_amt']));
	$('#plantax1').text(formatNumber(result['planvatamt']));
	$('#plan_req_amt1').text(formatNumber(result['plan_req_amt']));
//  var dif_sale = parseFloat(result['sale_foreign_amt']) - parseFloat(result['plan_sale_foreign_amt']);
//  var dif_tax = parseFloat(result['sale_vat_amt']) - parseFloat(result['planvatamt']);
//  var dif_req = parseFloat(result['req_amt']) - parseFloat(result['plan_req_amt']);
    var dif_sale = floatObj.subtract(result['sale_foreign_amt'],result['plan_sale_foreign_amt']);
    var dif_tax = floatObj.subtract(result['sale_vat_amt'],result['planvatamt']);
    var dif_req = floatObj.subtract(result['req_amt'],result['plan_req_amt']);
	//差额
	$('#dif_sale').text(formatNumber(Math.abs(dif_sale)));
	$('#dif_tax').text(formatNumber(Math.abs(dif_tax)));
	$('#dif_req').text(formatNumber(Math.abs(dif_req)));
	if(parseInt(dif_sale)<0){
		$('#icon-dif-sale').removeClass("deg180");
		$('#icon-dif-sale i').css("color","orangered");
	}
	if(parseInt(dif_sale)==0){
		$('#icon-dif-sale i').removeClass("icon-xiajiang");
		$('#icon-dif-sale i').addClass("icon-shang");
		$('#icon-dif-sale i').css("transform","rotate(90deg)");
		$('#icon-dif-sale i').css("display","inline-block");
		$('#icon-dif-sale i').css("color","#1ABC9C");
	}
	if(parseInt(dif_tax)<0){
		$('#icon-dif-tax').removeClass("deg180");
		$('#icon-dif-tax i').css("color","orangered");
	}
	if(parseInt(dif_tax)==0){
		$('#icon-dif-tax i').removeClass("icon-xiajiang");
		$('#icon-dif-tax i').addClass("icon-shang");
		$('#icon-dif-tax i').css("transform","rotate(90deg)");
		$('#icon-dif-tax i').css("display","inline-block");
		$('#icon-dif-tax i').css("color","#1ABC9C");
		
	}
	if(parseInt(dif_req)<0){
		$('#icon-dif-req').removeClass("deg180");
		$('#icon-dif-req i').css("color","orangered");
	}
	if(parseInt(dif_req)==0){
		$('#icon-dif-req i').removeClass("icon-xiajiang");
		$('#icon-dif-req i').addClass("icon-shang");
		$('#icon-dif-req i').css("transform","rotate(90deg)");
		$('#icon-dif-req i').css("display","inline-block");
		$('#icon-dif-req i').css("color","#1ABC9C");
	}
	
	
	//売上备考
	$('#sale_remark').val(result['sale_admit_remark'])
	//入力情报
	//实际
	$('#real_foreign_money_code').text(result['sale_foreign']);
	//
	$('#sale_amt').text(formatNumber(result['sale_amt']));
	//外汇
	$('#sale_cure_code').text(result['sale_cure_code']==null?'':Number(result['sale_cure_code']));
	//试用日
	$('#sale_use_date').text(result['sale_use_date']==null?'':strToDate(result['sale_use_date'], 1));
	//参照先
	$('#sale_refer').text(result['sale_refer']==null?'':result['sale_refer']);

	//预计
	$('#plan_foreign_money_code').text(result['plan_sale_foreign_type']);
	//入力金额
	$('#plan_sale').text(formatNumber(result['plan_sale']));
	//外汇
	$('#plan_sale_cure_code').text(result['plan_sale_cure_code']==null?'':Number(result['plan_sale_cure_code']));
	//试用日
	$('#plan_sale_use_date').text(result['plan_sale_use_date']==null?'':strToDate(result['plan_sale_use_date'], 1));
	//参照先
	$('#plan_sale_refer').text(result['plan_sale_refer']==null?'':result['plan_sale_refer']);

	//jobcd 
	$('#jobcd').text(result['job_cd']);
	//job名
	$('#job_name').text(result['job_name']);
	//得意先名
	$('#divnm').text(result['divnm']);
	//job更新者
	$('#username').text(result['uper']);
	//job更新时间
	$('#upddate').text(strToDate(result['upddate'],3));
	$("#jobupdusercolor").css("color",result['jobcolor']);
	//预计税入税拔
	$('#plan-sale-real-ishave').find('.activeT').find('input').each(function(index, element) {
		if(element.value != result['planishave']) {
			$(element).parents('label').toggleClass('activeT');
			$(element).parents('label').siblings('label').toggleClass('activeT');
		}
	});
	//实际税入税拔
	$('#sale-real-ishave').find('.activeT').find('input').each(function(index, element) {
		if(element.value != result['saleishave']) {
			$(element).parents('label').toggleClass('activeT');
			$(element).parents('label').siblings('label').toggleClass('activeT');
		}
	});
	//实际 job
	//调用方法计算税金
	var rate2 = result['salemstvo'].rate2;
	var rate3 = result['salemstvo'].rate3;
	//小数点
	var point = objStorage.getMoneyMessage()['pointNumber'];
	var taxFormatFlg = searchValue(objStorage.getListTax(), "itemcd", "003", "aidcd");
	var foreignFormatFlg = searchValue(objStorage.getListForeignTax(), "itemcd", "001", "aidcd");
	//实际部分税金计算
//	var rateObj = calculateTaxHandler(planSale, "", costMonet, "", reqAmt, payAmt, saleVatAmt,
//		costVatAmt, rate, rate1, costFinishFlg, costNum, point, taxFormatFlg, foreignFormatFlg);
	var obj_real = calculateTaxHandler("",
		result['sale_foreign_amt'], "", result['cost'][0].sumamt, result['req_amt'], result['cost'][0].paysum, result['sale_vat_amt'],
		result['cost'][0].sumvat, rate2, rate3, result['costfinishflg'], result['cost'][0].costnum, point, taxFormatFlg, foreignFormatFlg);

    



	//预计部分税金计算
	var obj_job = calculateTaxHandler(result['plan_sale_foreign_amt'], "", result['plancostamt'], result['plancostamt'], result['plan_req_amt'], result['planpayamt'], result['planvatamt'],
		result['plantax'], rate2, rate3, result['costfinishflg'], result['cost'][0].costnum, point, taxFormatFlg, foreignFormatFlg);
	//原价 
	$("#cost-amt").text(formatNumber(result['cost'][0].sumamt));
	$("#job-amt").text(formatNumber(result['plancostamt']));
	//增值税
	$("#cost-vat-amt").text(formatNumber(result['cost'][0].sumvat));
	$("#job-vat-amt").text(formatNumber(result['plantax']));
	//支付金额
	$("#cost-pay-amt").text(formatNumber(result['cost'][0].paysum));
	$("#job-pay-amt").text(formatNumber(result['planpayamt']));
	//税金
	$("#cost-rate-amt").text(formatNumber(obj_real['taxTotal']));
	$("#job-rate-amt").text(formatNumber(obj_job['taxTotal']));
	//营业收益
	$("#cost-get-amt").text(formatNumber(obj_real['profit']));
	$("#job-get-amt").text(formatNumber(obj_job['profit']));
	//营业收益率
	$("#cost-get-rate").text(obj_real['profitRate']);
	$("#job-get-rate").text(obj_job['profitRate']);

	//売上备考
	$('#sale_remark1 pre').html(result['sale_remark']);
	//売上更新者
	$('#sale_uper').text(result['saleuper']);
	//売上更新日
	$('#sale_uptime').text(strToDate(result['saleupddate'],3));
    $("#payupdcolor").css("color",result['salecolor']);
	//job备考
	$('#job_remark pre').html(result['remark']);
	//job更新者
	$("#job_upname").text(result['uper']);
	//job更新日
	$('#job_uptime').text(strToDate(result['upddate'],3));
    $("#jobupdusercolor1").css("color",result['jobcolor']);
	setPersonMoneyCode();
		if(objStorage.getSaleMoneyRow().length>0){
		changeMoneyStyle(objStorage.getSaleMoneyRow(), "real_foreign_money_code");
	}
	if(objStorage.getPlanMoneyRow().length>0){
		changeMoneyStyle(objStorage.getPlanMoneyRow(), "plan_foreign_money_code");
	}
	//原价完了标识
	var costFinishFlg = result['costfinishflg'];
	//原价条数标识
	var costNum = result['cost'][0].costnum;
	if(costFinishFlg==0&&costNum==0){
		
		$("#cost-amt").text("");
		$("#cost-vat-amt").text("");
		$("#cost-pay-amt").text("");
		$("#cost-rate-amt").text("");
		$("#cost-get-amt").text("");
		$("#cost-get-rate").text("");
	}
	//备考文字超出效果
	textOverhidden();	
}
//加载货币默认值 
function setPersonMoneyCode() {
	//获取登陆者货币
	var personMessage = objStorage.getMoneyMessage();
	var toplange = $('#language').val();
	var val = '';
	switch(toplange) {
		case "jp":
			val = personMessage['moneyjp'];
			break;
		case "zc":
			val = personMessage['moneyzc'];
			break;
		case "zt":
			val = personMessage['moneyzt'];
			break;
		case "en":
			val = personMessage['moneyen'];
			break;
	};
	if(val == "null" || val == '') {
		val = "RMB";
	}
	$('.money-code').text(val);

}
//外货 货币切换语言显示
function changeMoneyStyle(MoneyRow, clName) {
    
	var toplange = $('#language').val();
	var val = '';
	switch(toplange) {
		case "jp":
			val = MoneyRow[0]['itemname_jp'];
			break;
		case "zc":
			val = MoneyRow[0]['itmname'];
			break;
		case "zt":
			val = MoneyRow[0]['itemname_hk'];
			break;
		case "en":
			val = MoneyRow[0]['itemname_en'];
			break;
	};
	$("." + clName).text(val);

}

objStorage = {
	moneyMessage: null,
	saleMoneyRow: null,
	planMoneyRow: null,
	listTax: null,
	listForeignTax: null,
	saleLockFlg:0,
	getMoneyMessage: function() {
		return objStorage.moneyMessage;
	},
	getSaleMoneyRow: function() {
		return objStorage.saleMoneyRow;
	},
	getPlanMoneyRow: function() {
		return objStorage.planMoneyRow;
	},
	getListTax: function() {
		return this.listTax;
	},
	getListForeignTax: function() {
		return this.listForeignTax;
	},
	getSaleLockFlg:function(){
		return  this.saleLockFlg;
	}
}