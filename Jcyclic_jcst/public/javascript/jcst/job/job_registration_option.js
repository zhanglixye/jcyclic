 //売上种目切换时获取当前选择的税金
 function getSaleRate(sale_id) {
 	$(".log_job").attr('disabled', false);
 	var date1 = $('#scheduled-date').datebox('getValue');
 	if(date1 == '' || date1 == null) {
 		//showErrorHandler("JOB_DATE_NOT_FILL", "info", "info");
 		zeroSet();
 		return false;
 	}
 	//	var saleMessage = objStorage.getSaleMessage();
 	var saleVatRate = new Array();
 	//	for(var i in saleMessage) {
 	//		if(saleMessage[i].sale_cd == sale_id) {
 	//			saleVatRate['vat_rate'] = saleMessage[i].var_rate;
 	//			saleVatRate['rate1'] = saleMessage[i].rate1;
 	//			saleVatRate['rate2'] = saleMessage[i].rate2;
 	//			saleVatRate['rate3'] = saleMessage[i].rate3;
 	//
 	//		}
 	//	}
 	var par = {
 		dalday: date1,
 		salecd: sale_id
 	}
 	$.ajax({
 		url: $.getAjaxPath() + "getRateByDateAndSaleID",
 		data: JSON.stringify(par),
 		headers: {
 			"requestID": $.getRequestID(),
 			"requestName": $.getRequestNameByJcZH()
 		},
 		async: false,
 		success: function(data) {
// 			console.log(data);
 			if(data[$.getRequestMetaName()].result == $.getRequestStatusName()) {
 				$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
 				if(data[$.getRequestDataName()] != null) {
 					var saleMessage = data[$.getRequestDataName()];
 					if(saleMessage == null) {
 						showErrorHandler("NOT_FOUND_RATE", "warning", "warning");

 						return false;
 					}
 					if(saleMessage.var_rate == null) {
 						showErrorHandler("NOT_FOUND_RATE", "warning", "warning");
 						//$('#scheduled-date').datebox('setValue','');
 						return false;
 					}
 					if(saleMessage.rate2 == null) {
 						showErrorHandler("NOT_FOUND_RATE", "warning", "warning");
 						//$('#scheduled-date').datebox('setValue','');
 						return false;
 					}
 					if(saleMessage.rate3 == null) {
 						showErrorHandler("NOT_FOUND_RATE", "warning", "warning");
 						//$('#scheduled-date').datebox('setValue','');
 						return false;
 					}
 					saleVatRate['vat_rate'] = saleMessage.var_rate;
 					saleVatRate['rate1'] = saleMessage.rate1;
 					saleVatRate['rate2'] = saleMessage.rate2;
 					saleVatRate['rate3'] = saleMessage.rate3;
 					//objStorage.rateMsg = saleVatRate;
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
 	if(saleVatRate['vat_rate'] == undefined || saleVatRate['vat_rate'] == null || saleVatRate['vat_rate'] == "" ||
 		saleVatRate['rate2'] == undefined || saleVatRate['rate2'] == null || saleVatRate['rate2'] == "" ||
 		saleVatRate['rate3'] == undefined || saleVatRate['rate3'] == null || saleVatRate['rate3'] == "") {
 		return false;
 	} else {
 		return saleVatRate;
 	}

 }
 //外货下拉切换时获取信息
 function getMoneyCode(itemcd) {
 	var moneyMessage = objStorage.getMoneyMessage();
 	var saleCode = null;
 	var salePoint = null;
 	var val = null;
 	var toplange = $('#language').val();
 	for(var i in moneyMessage) {
 		if(moneyMessage[i].itemcd == itemcd) {
 			saleCode = moneyMessage[i].changeunit;
 			salePoint = moneyMessage[i].itemvalue;
 			switch(toplange) {
 				case "jp":
 					val = moneyMessage[i]['itemname_en'];
 					break;
 				case "zc":
 					val = moneyMessage[i]['itmname'];
 					break;
 				case "zt":
 					val = moneyMessage[i]['itemname_hk'];
 					break;
 				case "en":
 					val = moneyMessage[i]['itemname_en'];
 					break;
 			};

 		}
 	}
 	return {
 		"val": val,
 		"saleCode": saleCode,
 		"salePoint": salePoint
 	};
 }
 //计算売上金额
 function calculationSale(saleIsHave) {
 	var flg = true;
 	var saleMonet = $('.saleMoment').val();
 	var costMonet = $("#cost_foreign_amt").val();
 	var sale_id = $("#jobSaleList").val();
 	if(sale_id == undefined || sale_id == null || sale_id == '') {
 		//showErrorHandler("SALE_TYPE_NOT_FOUND", "info", "info");
 		zeroSet();
 		flg = false;
 		$(".log_job").attr('disabled', true);
 		return flg;
 	}
 	//  卖上外货code 查询 本公司货币 关联共同表查到itemValue 与 aicd 
 	//  var itemcd = $('#plansale_foreign_code').val();
 	var itemcd = $('#plansale_foreign_code').val();
 	var moneyObj = getMoneyCode(itemcd);
 	//  $('.sale_person_code').text(moneyObj["val"]);
 	var point = $.getDefaultPoint();
 	if(saleMonet == "" || saleMonet == undefined || saleMonet == null) {
 		$('.plan_sale_foreign_amt').text(Number(0).toFixed(point));
 		$('.plansale_tax').text(Number(0).toFixed(point));
 		$('.plan_req_amt').text(Number(0).toFixed(point));
 		$('.tax_total').text(Number(0).toFixed(point));
 		$('.get_total').text(Number(0).toFixed(point));
 		if(parseInt($('#sale_foreign_amt').text()) == 0) {
 			$(".sumRate").text("[INF%]");
 		} else {
 			$(".sumRate").text('[0.00%]');
 		}
 		flg = false;
 		return flg;
 	}
 	//页面上汇率（换算）
 	var saleCurCode = $('.plan_sale_cure_code').val();
 	//卖上增值税率
 	var arrSaleRate = getSaleRate(sale_id);
 	if(!arrSaleRate) {
 		flg = false;
 		//$(".log_job").attr('disabled', true);
 		$(".plan_sale_foreign_amt").html("0.0");
		$(".plansale_tax").html("0.0");
		$(".plan_req_amt").html("0.0");
		//$('input.cal-sale-box').next('span').find('input').html("0.0");
 		return flg;
 	}
 	var saleVatRate = arrSaleRate['vat_rate'];

 	//税入 税拔
 	if(saleIsHave == undefined) {
 		saleIsHave = $('.sale_rate_click').find('.active').find('input').val()
 	}

 	//	 * 方法名 calculateSaleHandler
 	// * 方法的说明  计算卖上金额、卖上增值税、请求金额。使用saleCurCode参数来判断是否为外货
 	// * 
 	// * @param saleMonet 卖上入力金额
 	// * @param saleCurCode 卖上换算CODE
 	// * @param saleCode 卖上外货code
 	// * @param saleVatRate 卖上增值税率
 	// * @param salePoint 本国货币小数点位数
 	// * @param saleIsHave 税入税拔，0：税拔；1：税入
 	// * * ***********add params by wy 2018.09.04
 	// * @param foreignFormatFlg 外货端数flg，0051 001
 	// * @param saleVatFormatFlg 卖上增值税端数flg 0052 001
 	// * 

 	var point = objStorage.getPersonMoneyCode()['pointNumber']; //本国小数点
 	//var pointForeign = moneyObj['salePoint'];//外货小数点
 	var saleVatFormatFlg = searchValue(objStorage.getListTax(), "itemcd", "001", "aidcd");
 	var foreignFormatFlg = searchValue(objStorage.getListForeignTax(), "itemcd", "001", "aidcd");
 	var sale_obj = calculateSaleHandler(saleMonet, saleCurCode, moneyObj['saleCode'], saleVatRate, point, saleIsHave, foreignFormatFlg, saleVatFormatFlg);
 	$('.plan_sale_foreign_amt').text(formatNumber(sale_obj['saleBase']));
 	$('.plansale_tax').text(formatNumber(sale_obj['saleVatBase']));
 	$('.plan_req_amt').text(formatNumber(sale_obj['reqAmtBase']));
 	//设置卖上增值税 与 flg
 	objStorage.saleTax = formatNumber(sale_obj['saleVatBase']);
 	objStorage.saleTaxFlg = 0;
 	//计算税金
 	if(costMonet == "" || costMonet == undefined || costMonet == null) {
 		flg = false;
 		$('.tax_total').text(Number(0).toFixed(point));
 		$('.get_total').text(Number(0).toFixed(point));
 		if(parseInt($('#sale_foreign_amt').text()) == 0) {
 			$(".sumRate").text("[INF%]");
 		} else {
 			$(".sumRate").text('[0.00%]');
 		}
 		return flg;
 	}
 	var planSale = recoveryNumber($('.plan_sale_foreign_amt').text());
 	var costMonet = recoveryNumber($('.plan_cost_foreign_amt').text());
 	var saleVatAmt = recoveryNumber($('.plansale_tax').text());
 	//var costVatAmt = recoveryNumber($('.plancost_tax').text());
 	var reqAmt = recoveryNumber($('.plan_req_amt').text());
 	//var payAmt = recoveryNumber($('.plan_vat_amt').text());
 	var rate = arrSaleRate['rate2'];
 	var rate1 = arrSaleRate['rate3'];
 	var taxFormatFlg = searchValue(objStorage.getListTax(), "itemcd", "003", "aidcd");
 	var costFinishFlg = objStorage.getCostFinishFlg();
 	var costNum = objStorage.getCostNum();
 	var realCost = objStorage.getRealCost();

 	//原价完了

 	//	if(costFinishFlg==1){
 	//		//没有实际成本 0
 	//	 if(costNum==0){
 	//	 	var payAmt = 0;
 	//	 	var costVatAmt = 0;
 	//	 	//实际原价
 	//	    var priceTrue = 0;
 	//	 }else{
 	//	 	//有实际成本 用实际
 	//	 	 var payAmt =  recoveryNumber(realCost['paysum']);
 	//   	 var costVatAmt = recoveryNumber(realCost['sumvat']);
 	//	 	 var priceTrue =recoveryNumber(realCost['sumamt']);
 	//	 }
 	//		
 	//	}else{
 	//原价未完了  没有实际成本 用预计
 	// if(costNum==0){
 	var payAmt = recoveryNumber($('.plan_vat_amt').text());
 	var costVatAmt = recoveryNumber($('.plancost_tax').text());
 	var priceTrue = costMonet;

 	// }else{
 	//有实际成本用实际
 	//       var payAmt =  recoveryNumber(realCost['paysum']);
 	//   	 var costVatAmt = recoveryNumber(realCost['sumvat']);
 	//	 	 var priceTrue =recoveryNumber(realCost['sumamt']);

 	// }
 	//}
 	var rateObj = calculateTaxHandler(planSale, "", costMonet, priceTrue, reqAmt, payAmt, saleVatAmt,
 		costVatAmt, rate, rate1, costFinishFlg, costNum, point, taxFormatFlg, foreignFormatFlg);
 	//页面税金
 	$('.tax_total').text(formatNumber(rateObj['taxTotal']));
 	//页面营业额
 	$('.get_total').text(formatNumber(rateObj['profit']));
 	//如果 卖上金额为0 营业收益率 显示为INF
 	if(parseInt($('#sale_foreign_amt').text()) == 0) {
 		$(".sumRate").text("[INF%]");
 	} else {
 		$(".sumRate").text('[' + rateObj['profitRate'] + '%]');
 	}
 	//	$(".sumRate").text('['+rateObj['profitRate']+'%]');
 	//将税金1和2 存在session中 插入时获取
 	sessionStorage.setItem('sumRate1', rateObj['tax']);
 	sessionStorage.setItem('sumRate2', rateObj['tax1']);
 	return flg;
 }
 //计算成本金额
 function calculationCost(costIsHave) {
 	var costMonet = $("#cost_foreign_amt").val()
 	var saleMonet = $('.saleMoment').val();
 	var sale_id = $("#jobSaleList").val();
 	if(sale_id == undefined || sale_id == null || sale_id == '') {
 		//showErrorHandler("SALE_TYPE_NOT_FOUND", "info", "info");
 		zeroSet();
 		$(".log_job").attr('disabled', true);
 		return;
 	}
 	//  卖上外货code 查询 本公司货币 关联共同表查到itemValue 与 aicd 
 	// var itemcd = $('#plansale_foreign_code').val();
 	var itemcd = $('#cost_foreign_type').val();
 	var moneyObj = getMoneyCode(itemcd);
 	var point = $.getDefaultPoint();
 	//$('.cost_person_code').text(moneyObj["val"]);
 	if(costMonet == "" || costMonet == undefined || costMonet == null) {
 		$('.plan_cost_foreign_amt').text(Number(0).toFixed(point));
 		$('.plancost_tax').text(Number(0).toFixed(point));
 		$('.plan_vat_amt').text(Number(0).toFixed(point));
 		$('.tax_total').text(Number(0).toFixed(point));
 		$('.get_total').text(Number(0).toFixed(point));
 		if(parseInt($('#sale_foreign_amt').text()) == 0) {
 			$(".sumRate").text("[INF%]");
 		} else {
 			$(".sumRate").text('[0.00%]');
 		}
 		return;
 	}
 	//页面上汇率（换算）
 	var costCurCode = $('.plan_cost_cure_code').val();
 	//增值税率
 	var arrCostRate = getSaleRate(sale_id);
 	if(!arrCostRate) {
 		//$(".log_job").attr('disabled', true);
 		$(".plan_sale_foreign_amt").val("");
		$(".plansale_tax").val("");
		$(".plan_req_amt").val("");
 		return;
 	}
 	var saleVatRate = objStorage.getCostRate();
 	//税入 税拔
 	if(costIsHave == undefined) {
 		costIsHave = $('.cost_rate_click').find('.active').find('input').val();
 	}

 	/**
 	 * 方法名 calculateCostHandler
 	 * 方法的说明  计算原价金额、支付增值税、支付金额。使用costCurCode参数来判断是否为外货
 	 * 
 	 * @param costMonet 入力金额
 	 * @param costCurCode 换算CODE
 	 * @param costCode 外货code
 	 * @param costVatRate 成本增值税率
 	 * @param costPoint 本国货币小数点位数
 	 * @param costIsHave 税入税拔，0：税拔；1：税入
 	 * * ***********add params by wy 2018.09.04
 	 * @param foreignFormatFlg 外货端数flg，0051 001
 	 * @param costVatFormatFlg 仕入增值税端数flg 0052 002
 	 *
 	 */
 	var point = objStorage.getPersonMoneyCode()['pointNumber']; //本国小数点
 	//var pointForeign = moneyObj['salePoint'];//外货小数点
 	var costVatFormatFlg = searchValue(objStorage.getListTax(), "itemcd", "002", "aidcd");
 	var foreignFormatFlg = searchValue(objStorage.getListForeignTax(), "itemcd", "001", "aidcd");
 	var cost_obj = calculateCostHandler(costMonet, costCurCode, moneyObj['saleCode'], saleVatRate, point, costIsHave, foreignFormatFlg, costVatFormatFlg);
 	$('.plan_cost_foreign_amt').text(formatNumber(cost_obj['costBase']));
 	$('.plancost_tax').text(formatNumber(cost_obj['costVatBase']));
 	$('.plan_vat_amt').text(formatNumber(cost_obj['payBase']));
 	//仕入增值税 
 	objStorage.costTax = formatNumber(cost_obj['costVatBase']);
 	objStorage.costTaxFlg = 0;
 	//计算税金
 	if(saleMonet == "" || saleMonet == undefined || saleMonet == null) {
 		$('.tax_total').text(Number(0).toFixed(point));
 		$('.get_total').text(Number(0).toFixed(point));
 		if(parseInt($('#sale_foreign_amt').text()) == 0) {
 			$(".sumRate").text("[INF%]");
 		} else {
 			$(".sumRate").text('[0.00%]');
 		}
 		return;
 	}

 	var planSale = recoveryNumber($('.plan_sale_foreign_amt').text());
 	var costMonet = recoveryNumber($('.plan_cost_foreign_amt').text());
 	var saleVatAmt = recoveryNumber($('.plansale_tax').text());
 	//var costVatAmt = recoveryNumber($('.plancost_tax').text());
 	var reqAmt = recoveryNumber($('.plan_req_amt').text());
 	//var payAmt = recoveryNumber($('.plan_vat_amt').text());
 	var rate = arrCostRate['rate2'];
 	var rate1 = arrCostRate['rate3'];
 	var taxFormatFlg = searchValue(objStorage.getListTax(), "itemcd", "003", "aidcd");
 	var costFinishFlg = objStorage.getCostFinishFlg();
 	var costNum = objStorage.getCostNum();
 	var realCost = objStorage.getRealCost();

 	//原价完了

 	//	if(costFinishFlg==1){
 	//		//没有实际成本 0
 	//	 if(costNum==0){
 	//	 	var payAmt = 0;
 	//	 	var costVatAmt = 0;
 	//	 	//实际原价
 	//	    var priceTrue = 0;
 	//	 }else{
 	//	 	//有实际成本 用实际
 	//	 	 var payAmt =  recoveryNumber(realCost['paysum']);
 	//   	 var costVatAmt = recoveryNumber(realCost['sumvat']);
 	//	 	 var priceTrue =recoveryNumber(realCost['sumamt']);
 	//	 }
 	//		
 	//	}else{
 	//	//原价未完了  没有实际成本 用预计
 	//  if(costNum==0){
 	var payAmt = recoveryNumber($('.plan_vat_amt').text());
 	var costVatAmt = recoveryNumber($('.plancost_tax').text());
 	var priceTrue = costMonet;
 	//	    
 	//  }else{
 	//有实际成本用实际
 	//       var payAmt =  recoveryNumber(realCost['paysum']);
 	//   	 var costVatAmt = recoveryNumber(realCost['sumvat']);
 	//	 	 var priceTrue =recoveryNumber(realCost['sumamt']);

 	//  }
 	//	}
 	var rateObj = calculateTaxHandler(planSale, "", costMonet, priceTrue, reqAmt, payAmt, saleVatAmt,
 		costVatAmt, rate, rate1, costFinishFlg, costNum, point, taxFormatFlg, foreignFormatFlg);
 	//页面税金
 	$('.tax_total').text(formatNumber(rateObj['taxTotal']));
 	//页面营业额
 	$('.get_total').text(formatNumber(rateObj['profit']));
 	if(parseInt($('#sale_foreign_amt').text()) == 0) {
 		$(".sumRate").text("[INF%]");
 	} else {
 		$(".sumRate").text('[' + rateObj['profitRate'] + '%]');
 	}
 	//	$(".sumRate").text('['+rateObj['profitRate']+'%]');
 	//将税金1和2 存在session中 插入时获取
 	sessionStorage.setItem('sumRate1', rateObj['tax']);
 	sessionStorage.setItem('sumRate2', rateObj['tax1']);
 }
 function zeroSet(){
        var point = $.getDefaultPoint();
        $('.plan_sale_foreign_amt').text(Number(0).toFixed(point));
 		$('.plansale_tax').text(Number(0).toFixed(point));
 		$('.plan_req_amt').text(Number(0).toFixed(point));
        $('.plan_cost_foreign_amt').text(Number(0).toFixed(point));
 		$('.plancost_tax').text(Number(0).toFixed(point));
 		$('.plan_vat_amt').text(Number(0).toFixed(point));
 		$('.tax_total').text(Number(0).toFixed(point));
 		$('.get_total').text(Number(0).toFixed(point));
 		$(".sumRate").text("[INF%]");
 }
 //遍历担当者模块
 function loopUserCard() {
 	var usercd = [];
 	var level = [];
 	$('#per .person').each(function(index, element) {
 		if($(element).find("input").prop("checked") == true) {
 			level.push(1);
 		} else {
 			level.push($(element).find('select').val());
 		}

 		usercd.push($(element).find("div .usercd").text());
 	});
 	var card = [];
 	for(var i = 0; i < level.length; i++) {

 		var people = {
 			level_flg: level[i],
 			user_cd: usercd[i],
 			juser_del_flg: 1
 		}
 		card.push(people);

 	}
 	return card;
 }
 //job更新 需要添加的卡片
 function jobUpGetNewAddCards() {
 	var usercdArr = [];
 	var level = [];
 	$('#per .person').each(function(index, element) {
 		var usercd = $(element).find("div .usercd").text();
 		//if(searchValue(objStorage.getCardMessage(), "usercd", usercd, "usercd") == "") {
 		if($(element).find("input").prop("checked") == true) {
 			level.push(1);
 		} else {
 			level.push($(element).find('select').val());
 		}
 		usercdArr.push($(element).find("div .usercd").text());
 		//}
 	});
 	var cardNew = [];
 	var cardDel = [];
 	for(var i = 0; i < level.length; i++) {

 		var people = {
 			level_flg: level[i],
 			user_cd: usercdArr[i],
 			juser_del_flg: 1
 		}
 		cardNew.push(people);

 	}
 	//整理删除的数组
 	var arrDelUser = objStorage.getDelCard();
 	for(var k = 0; k < arrDelUser.length; k++) {
 		var people = {
 			user_cd: arrDelUser[k]
 		}
 		if(searchValue(cardNew, "user_cd", arrDelUser[k], "user_cd") == "") {
 			cardDel.push(people);
 		} else {
 			continue;
 		}

 	}

 	return {
 		"cardNew": cardNew,
 		"cardDel": cardDel
 	};
 }

 function initShow() {
 	//机上日变化 计算 防止冲突 暂时注释掉
   	$('#scheduled-date').datebox({
   		onChange: function() {
   			var plan_dlvday = $('#scheduled-date').datebox('getValue');
   			if(checkDateV(plan_dlvday)){
	 			var flg = calculationSale();
	   			if(flg) {
	   				calculationCost();
	   			}
	 		}  			
   		}
   	});
 	var jobcd = null;
 	var nowUrl = window.location.href;
 	var path = $.getAjaxPath() + "jobInitSelect";
 	if(nowUrl.match(/\/job_update.html/) != null) {
 		jobcd = getQueryString("jobcd", "jcst/job_update.html");
 		path = $.getAjaxPath() + "jobUpdateInit";
 	}
 	var job = {
 		job_cd: jobcd
 	}
 	$.ajax({
 		url: path,
 		data: JSON.stringify(job),
 		headers: {
 			"requestID": $.getRequestID(),
 			"requestName": $.getRequestNameByJcZH()
 		},
// 		async:false,
 		success: function(data) {
// 			console.log(data);
 			if(data[$.getRequestMetaName()].result == $.getRequestStatusName()) {
 				$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
 				if(data[$.getRequestDataName()] != null) {
 					var skip = data[$.getRequestDataName()]['jobland'].skip;
 					var powerList = data['userInfo'].uNodeList;
 					//此公司 md分配跳过
 					if(skip.md) {
 						$("#md-flg").prop("checked", "checked");
 						var md_flg = $("#md-flg").prop("checked");
 						if(md_flg) {
 							$('#per').find('.person select:nth-child(n) option[value="3"]').addClass("hidden");
 						} else {
 							$('#per').find('.person select:nth-child(n) option[value="3"]').removeClass("hidden");
 						}
 					}
 					//如果是job登录页面 有这个id的日期控件
 					if($("#scheduled-date") != undefined) {
 						$.dateLimit("scheduled-date", data[$.getRequestDataName()]['accountyear'][0].itemvalue, 0);
 					}

 					//根据权限显示
 					//sessionStorage.setItem("costSkip", skip.cost);
 					objStorage.skip = skip;
 					objStorage.personMoneyCode = data["userInfo"];
 					objStorage.moneyMessage = data[$.getRequestDataName()]['list_comst'];
 					objStorage.lableMessage = data[$.getRequestDataName()]['list_lable'];
 					objStorage.saleMessage = data[$.getRequestDataName()]['list_sale'];
 					objStorage.listTax = data[$.getRequestDataName()]['list_tax'];
 					objStorage.listForeignTax = data[$.getRequestDataName()]['list_foreign_tax'];
 					objStorage.costRate = data[$.getRequestDataName()]['costRate'];
 					//成本终止flg
 					objStorage.costFinishFlg = data[$.getRequestDataName()]['jobland']['costFinshFlg'];
 					//成本条数
 					objStorage.costNum = data[$.getRequestDataName()]['list_cost'][0].costnum;
 					objStorage.realCost = data[$.getRequestDataName()]['list_cost'][0];
 					objStorage.costRate = data[$.getRequestDataName()]['costRate'];
 					SelectObj.setLableList = data[$.getRequestDataName()]['list_lable'];
 					//加载初始下拉
 					selectInit(data);
 					//页面设置默认货币
 					setPersonMoneyCode(objStorage.getPersonMoneyCode());
 					//标签数据重构
 					labelToMySelect(objStorage.getLableMessage());
 					
 					var point = parseInt($.getDefaultPoint());
 					//如果是更新设置页面值
 					if(jobcd != null) {
   						var arrDelUser = objStorage.getDelCard();
   						var saleFinishFlg = data[$.getRequestDataName()]['jobland'].isSaleFinishFlg;
   						sessionStorage.setItem("isSaleFinishFlg", saleFinishFlg);
 						$('.upd-jobcd').text(jobcd);
 						var job = data[$.getRequestDataName()]['jobland'];
                        objStorage.lockFlg =job['lockFlg'];   
 						SelectObj.setChooseLableList = job['jlTrn'];
 						objStorage.cardMessage = job['julable'];
 						loadJobMsg(job);
                        cardInit(arrDelUser,saleFinishFlg);
                        
 						//						

 						//原价条数大于0 卖上为完了 不可删除job
 						if(data[$.getRequestDataName()]['list_cost'][0].costnum > 0 || data[$.getRequestDataName()]['jobland'].isSaleFinishFlg > 0) {
 							$("#del_job").attr("disabled", true);
 						}
   						
// 						if(!mdReqPowerUp()) {
// 						 $("#per").find(".addp").addClass('hidden');
// 						}
//                    

 					} else {
 						var bl = isHavePower(powerList, [4]);
 						var bl2 = isHavePower(powerList, [3]);
 						if(!bl && bl2) {
 							//没有担当割当权限  只能选择担当者 有基本情报权限
 							powerDecCardSelct();
 						}

 					}
 					//张票出力是否默认选中
 					if(data[$.getRequestDataName()]['jobland'].outFlg_list[0].itemvalue == "1") {
 						$(".lastChild").find("input").prop("checked", "checked");
 					} else {
 						$(".lastChild").find("input").prop("checked", false);
 					}
 					edit_common(point, point);
 					lableShowByPower();
 					bindAction();

 				} else {
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
 //job更新只有割当权限其他不可编辑
 function powerGD() {
 	$("input:not(.level_flg)").attr("disabled", true);
 	$("select:not(.emp)").attr("disabled", true);
 	$("i:not(.cardfind)").attr("disabled", true);
 	$(".hiddclass").numberbox({
 		"disabled": true
 	});
 	//日期不可编辑
 	$('#common-dayT').next('span').find('a').remove();
 	$('#scheduled-date').next('span').find('a').remove();
 	$('#common-day').next('span').find('a').remove();
 	//外货开关
 	$('#switch-state').bootstrapSwitch('disabled', true);
 	$('#switch-state1').bootstrapSwitch('disabled', true);
 	//税入税拔开关
 	$('.sale_rate_click').attr('disabled', true);
 	$('.cost_rate_click').attr('disabled', true);
 }
 //job 更新时 页面加载回写数据
 function loadJobMsg(job) {
 	//件名
 	$('.job_name').val(job['job_name']);
 	//売上种目
 	$('#jobSaleList').val(job['sale_typ']);
 	//得意先
 	$('#cldiv_cd').val(job['cldiv_cd']);
 	$('.cldiv_name').text(job['divnm']);
 	$('.cldiv_name').css("font-size", 20);
 	$('#payer_cd').val(job['payer_cd']);
 	$('.payer_cd').val(job['payername']);
 	$('#g_company').val(job['g_company']);
 	$('.g_company').val(job['gcompanyname']);
 	//预计终了日
 	$('#scheduled-date').datebox('setValue', strToDate(job['plan_dalday'], 1));
 	if(job['plan_sale_cure_code'] != null && job['plan_sale_cure_code'] != "" && job['plan_sale_cure_code'] != undefined) {
 		$('#switch-state').bootstrapSwitch('state', 1);
 	}
 	//売上外货code
 	$('#plansale_foreign_code').val(job['plansale_foreign_code']);
 	//job更新时 输入框的单位 跟下拉一直 
 	if(job['plansale_foreign_code'] != "") {
 		$('span#need_change_code1').text(getMoneyCode(job['plansale_foreign_code'])['val'])
 	}
 	//成本
 	//成本外货code
 	if(job['plan_cost_foreign_code'] != null && job['plan_cost_foreign_code'] != "" && job['plan_cost_foreign_code'] != undefined) {
 		$('#switch-state1').bootstrapSwitch('state', 1);
 	}
 	$('#cost_foreign_type').val(job['plan_cost_foreign_code']);
 	//job更新时 输入框的单位 跟下拉一直 
 	if(job['plan_cost_foreign_code'] != "") {
 		$('#cost_need_change_money1').text(getMoneyCode(job['plan_cost_foreign_code'])['val']);
 	}

 	// job更新 根据实际登录 设置 输入小数点位数限制
 	var point = $.getDefaultPoint();
 	var retention_digit = parseInt(point);
 	var retention_digit_p = parseInt(point);
 	if(job['plansale_foreign_code'] != null && job['plansale_foreign_code'] != "") {
 		retention_digit = parseInt(getMoneyCode(job['plansale_foreign_code'])['salePoint']); //売上保留小数
 	}
 	if(job['plan_cost_foreign_code'] != null && job['plan_cost_foreign_code'] != "") {
 		retention_digit_p = parseInt(getMoneyCode(job['plan_cost_foreign_code'])['salePoint']);
 	}

 	bus_price_common(retention_digit, retention_digit_p);
 	//売上金额
 	$('#pcost').numberbox('setValue', job['plan_sale_foreign_amt']);
 	//换算
 	if(job['plan_sale_cure_code'] == null || job['plan_sale_cure_code'] == "") {
 		$('.plan_sale_cure_code').val("");
 	} else {
 		$('.plan_sale_cure_code').val(Number(job['plan_sale_cure_code']));
 	}

 	//売上试用日
 	if(job['plan_sale_use_date'] == null || job['plan_sale_use_date'] == "") {
 		$('#common-day').datebox("setValue", "");
 	} else {
 		$('#common-day').datebox("setValue", strToDate(job['plan_sale_use_date'], 1));
 	}

 	//税入 税拔
 	$('.sale_rate_click').find('.active').find('input').each(function(index, element) {
 		if(element.value != job['plan_sale_ishave']) {
 			$(element).parents('label').toggleClass('active');
 			$(element).parents('label').siblings('label').toggleClass('active');
 		}
 	});

 	//参照先
 	$('.plan_sale_refer').val(job['plan_sale_refer']);
 	//预计売上金额
 	$('#sale_foreign_amt').text(formatNumber(job['plan_sale']));
 	//增值锐
 	$('#sale_tax').text(formatNumber(job['plan_vat_amt']));
 	//请求金额
 	$('#req_amt').text(formatNumber(job['plan_req_amt']));

 	//成本金额
 	$('input#cost_foreign_amt').numberbox('setValue', job['plan_cost_foreign_amt']);
 	//成本换算
 	//$('.plan_cost_cure_code').val(Number(job['plan_cost_cure_code']));
 	if(job['plan_cost_cure_code'] == null || job['plan_cost_cure_code'] == "") {
 		$('.plan_cost_cure_code').val("");
 	} else {
 		$('.plan_cost_cure_code').val(Number(job['plan_cost_cure_code']));
 	}
 	//成本试用日
 	if(job['plan_cost_use_date'] == null || job['plan_cost_use_date'] == "") {
 		$('#common-dayT').datebox("setValue", "");
 	} else {
 		$('#common-dayT').datebox("setValue", strToDate(job['plan_cost_use_date'], 1));
 	}

 	//税入税拔
 	$('.cost_rate_click').find('.active').find('input').each(function(index, element) {
 		if(element.value != job['plan_cost_ishave']) {
 			$(element).parents('label').toggleClass('active');
 			$(element).parents('label').siblings('label').toggleClass('active');
 		}
 	});
 	//参照先
 	$('.plan_cost_refer').val(job['plan_cost_refer']);
 	//预计成本金额
 	$('span#cost_foreign_amt').text(job['plancostamt'] == null ? Number(0).toFixed(point) : formatNumber(job['plancostamt']));
 	//增值税
 	$("#cost_tax").text(job['plancost_tax'] == null ? Number(0).toFixed(point) : formatNumber(job['plancost_tax']))
 	//支付金额
 	$('#pay_amt').text(job['plan_pay_amt'] == null ? Number(0).toFixed(point) : formatNumber(job['plan_pay_amt']));
 	//		//税金1
 	//		job['plan_sale_tax1'] = sessionStorage.getItem('sumRate1');
 	//		//税金2
 	//		job['plan_sale_tax2'] = sessionStorage.getItem('sumRate2');
 	//税金合计
 	$('#plansale_tax_total').text(job['plansale_tax_total'] == null ? Number(0).toFixed(point) : formatNumber(job['plansale_tax_total']));
 	//
 	var point = objStorage.getPersonMoneyCode()['pointNumber']; //本国小数点
 	var foreignFormatFlg = searchValue(objStorage.getListForeignTax(), "itemcd", "001", "aidcd");
 	//营收 = 卖上金额-（原价金额+税金合计）
 	var costAtax = floatObj.add(job['plancostamt'], job['plansale_tax_total']);
 	var profit = floatObj.subtract(job['plan_sale'], costAtax);
 	//var profit = Number(job['plan_sale']) - (Number(job['plancostamt']) + Number(job['plansale_tax_total']));
 	profit = pointFormatHandler(profit, foreignFormatFlg, point);
 	//营收率=营收/卖上金额 * 100
 	var preProfitRate = floatObj.divide(profit, job['plan_sale']);
 	var profitRate = floatObj.multiply(preProfitRate, 100);
 	//profitRate = pointFormatHandler(profitRate, foreignFormatFlg, point);
 	//4舍5入
   	profitRate = profitRate.toFixed(2);
 	//
 	$("span.get_total").text(formatNumber(profit));
 	if(isNaN(preProfitRate)){
 			$(".sumRate").text('[INF%]');
 	}else{
 			$(".sumRate").text('[' + profitRate + '%]');
 	}
 
 	//备考
 	$('.remark').val(job['remark']);
 	$('#adddate').text(strToDate(job['adddate'], 3));
 	$('#adduser').text(job['adduser']);

 	$("#addcolor").css("color", job['addcolor']);
 	$("#updcolor").css("color", job['updcolor']);

 	if(job['adddate'] != job['upddate']) {
 		$('#update').text(strToDate(job['upddate'], 3));
 		$('#upduser').text(job['upduser']);
 	} else {
 		$("#updhidden").addClass("hidden");
 	}

 	//割当flg
 	if(!job['assignflg']) {
 		$("#md-flg").prop("checked", false);
 	} else {
 		$("#md-flg").prop("checked", true);
 	}
 	setUserCard(job['julable']);
 	setLableArea(job['jlTrn']);
 }
 //初始化页面加载的下拉
 function selectInit(data) {
 	var list_sale = objStorage.getSaleMessage();
 	var str = '<option value="" class=""></option>';
 	for(var i = 0; i < list_sale.length; i++) {
 		str += '<option value="' + list_sale[i]['sale_cd'] + '" class="">' + list_sale[i]['sale_name'] + '</option>';
 	}
 	$('#jobSaleList').html(str);
 	var list = data[$.getRequestDataName()];
 	list['cost_foreign_type'] = list['list_comst'];
 	list['plansale_foreign_code'] = list['list_comst'];
 	SelectObj.setStringFlg = "_";
 	SelectObj.selectData = list;
 	SelectObj.setSelectID = ['plansale_foreign_code', 'cost_foreign_type'];
 	SelectObj.setSelectOfLog();
 }
 //初始化加载卡片
 function setUserCard(user_list) {
 	var str = "";
 	var iid = "";
 	var levelid = "";
 	var userid = "";
 	var selectID = "";
 	var username = "";
 	var levelname = "";
 	var levelValue = "";
 	var btnId = "";
 	var colorid = "";
 	var colorStyle = "";
 	var checkID = "";
 	var saleFinishFlg = sessionStorage.getItem("isSaleFinishFlg");
 	var url_user = $.getJumpPath() + 'common/employ_retrieval.html';
 	var md_flg = $("#md-flg").prop("checked");
 	for(var i = 0; i < user_list.length; i++) {
 		iid = "jobCreat-user-" + parseInt(i + 1);
 		levelid = "jobCreat-userlevel-" + parseInt(i + 1);
 		userid = "jobCreat-usercd-" + parseInt(i + 1);
 		selectID = "jobCreat-select-" + parseInt(i + 1);
 		username = "jobCreat-username-" + parseInt(i + 1);
 		checkID = "jobCreat-checkID-" + parseInt(i + 1);
 		btnId = "btn" + parseInt(i + 1);
 		colorid = "person-color-" + parseInt(i + 1);
 		colorStyle = "color:" + user_list[i]['usercolor'];
 		if(user_list[i]['level_flg'] == 1 || user_list[i]['level_flg'] == 2) {
 			levelValue = 2
 		} else {
 			levelValue = 3
 		}

 		str = '<div class="col-md-4 panel person" style="padding:5px;margin-bottom:0;width:225px;background:#fff">' +
 			'<div class="person-t"><div class="person-t-l">';
 		if(levelValue == 2) {
 			str += '<select name="boostrap-select " class="form-control emp" id="' + selectID + '">' +
 				'<option name="jobregistration_business_ren" class="i18n" value="2" selected=true ></option><option name="jobregistration_bear_ren" class="i18n" value="3" ></option></select>';
 		} else {
 			str += '<select name="boostrap-select " class="form-control emp" id="' + selectID + '">' +
 				'<option name="jobregistration_business_ren" class="i18n" value="2" ></option><option name="jobregistration_bear_ren" class="i18n" value="3"  selected=true ></option></select>';
 		}

 		str += '</div><div class="person-t-r">';
   		if(saleFinishFlg == 1) {
   			
 				str += '<button type="button" class="close hidden-flg" data-dismiss="modal" aria-hidden="true" id="' + btnId + '"></button>'+
 			'</div></div><div class="person-m"><div class="person-m-l">' +
 			'<i class="iconfont icon-ren cardfind" id="' + colorid + '" style="' + colorStyle + '"></i>' +
 			'</div><div class="person-m-m"><div name="userlevel" id="' + levelid + '">' + user_list[i]["itmname"] + '</div>' +
 			'<div class="usercd" id="' + userid + '">' + user_list[i]["usercd"] + '</div><div class="username" id="' + username + '">' + user_list[i]["user_name"] + '</div>'+
 			'</div><div class="person-m-r"><i class="" id="' + iid + '"></i></div></div>';
 			
 			
 		} else {
 			//没有割当担当权限 有 job更新权限
 			if(!mdReqPowerUp()&&jobPowerUp()){
 				//割当卡片
 				if(levelValue==3){
 					str += '<button type="button" class="close hidden-flg" data-dismiss="modal" aria-hidden="true" id="' + btnId + '"></button>'+
 			'</div></div><div class="person-m"><div class="person-m-l">' +
 			'<i class="iconfont icon-ren cardfind" id="' + colorid + '" style="' + colorStyle + '"></i>' +
 			'</div><div class="person-m-m"><div name="userlevel" id="' + levelid + '">' + user_list[i]["itmname"] + '</div>' +
 			'<div class="usercd" id="' + userid + '">' + user_list[i]["usercd"] + '</div><div class="username" id="' + username + '">' + user_list[i]["user_name"] + '</div>'+
 			'</div><div class="person-m-r"><i class="" id="' + iid + '"></i></div></div>';
 				}else{
 					str += '<button type="button" class="close hidden-flg" data-dismiss="modal" aria-hidden="true" id="' + btnId + '">×</button>' +
 			'</div></div><div class="person-m"><div class="person-m-l">' +
 			'<i class="iconfont icon-ren cardfind" id="' + colorid + '" style="' + colorStyle + '"></i>' +
 			'</div><div class="person-m-m"><div name="userlevel" id="' + levelid + '">' + user_list[i]["itmname"] + '</div>' +
 			'<div class="usercd" id="' + userid + '">' + user_list[i]["usercd"] + '</div><div class="username" id="' + username + '">' + user_list[i]["user_name"] + '</div>'+
 			'</div><div class="person-m-r"><i class="iconfont icon-sousuo" id="' + iid + '"></i></div></div>';
 				}
 			}
 			//有割当担当权限  没有 job更新权限
 			if(mdReqPowerUp()&&!jobPowerUp()){
 				//dan挡卡片
 				if(levelValue==2){
 							str += '<button type="button" class="close hidden-flg" data-dismiss="modal" aria-hidden="true" id="' + btnId + '"></button>'+
 			'</div></div><div class="person-m"><div class="person-m-l">' +
 			'<i class="iconfont icon-ren cardfind" id="' + colorid + '" style="' + colorStyle + '"></i>' +
 			'</div><div class="person-m-m"><div name="userlevel" id="' + levelid + '">' + user_list[i]["itmname"] + '</div>' +
 			'<div class="usercd" id="' + userid + '">' + user_list[i]["usercd"] + '</div><div class="username" id="' + username + '">' + user_list[i]["user_name"] + '</div>'+
 			'</div><div class="person-m-r"><i class="" id="' + iid + '"></i></div></div>';
 				}else{
 					str += '<button type="button" class="close hidden-flg" data-dismiss="modal" aria-hidden="true" id="' + btnId + '">×</button>' +
 			'</div></div><div class="person-m"><div class="person-m-l">' +
 			'<i class="iconfont icon-ren cardfind" id="' + colorid + '" style="' + colorStyle + '"></i>' +
 			'</div><div class="person-m-m"><div name="userlevel" id="' + levelid + '">' + user_list[i]["itmname"] + '</div>' +
 			'<div class="usercd" id="' + userid + '">' + user_list[i]["usercd"] + '</div><div class="username" id="' + username + '">' + user_list[i]["user_name"] + '</div>'+
 			'</div><div class="person-m-r"><i class="iconfont icon-sousuo" id="' + iid + '"></i></div></div>';
 				}
 			}
 			if(mdReqPowerUp()&&jobPowerUp()){
 				   str += '<button type="button" class="close hidden-flg" data-dismiss="modal" aria-hidden="true" id="' + btnId + '">×</button>' +
 			'</div></div><div class="person-m"><div class="person-m-l">' +
 			'<i class="iconfont icon-ren cardfind" id="' + colorid + '" style="' + colorStyle + '"></i>' +
 			'</div><div class="person-m-m"><div name="userlevel" id="' + levelid + '">' + user_list[i]["itmname"] + '</div>' +
 			'<div class="usercd" id="' + userid + '">' + user_list[i]["usercd"] + '</div><div class="username" id="' + username + '">' + user_list[i]["user_name"] + '</div>'+
 			'</div><div class="person-m-r"><i class="iconfont icon-sousuo" id="' + iid + '"></i></div></div>';
 			}
 			
 		}

 		if(user_list[i]['level_flg'] == 1) {
 			str += '<div class="person-b"><label><input type="checkbox" class="level_flg" id="'+checkID+'" value="option4" checked="checked">' +
 				'<span class="iconfont icon-duihaok"></span><span class="i18n" name="jobregistration_person_in_charge">责任者</span></label></div></div>';
 		} else {
 			str += '<div class="person-b"><label><input type="checkbox" class="level_flg" id="'+checkID+'" value="option4" >' +
 				'<span class="iconfont icon-duihaok"></span><span class="i18n" name="jobregistration_person_in_charge">责任者</span></label></div></div>';
 		}

 		$("#per").append($(str));
 		//job更新 如果卖上完了 不可编辑  取消绑定事件

 		if(saleFinishFlg == 1) {
 			$("#per").find("select").attr("disabled", "disabled");
 			$("#per").find("input").attr("disabled", "disabled");
 			$("#per").unbind('click');
 		} else {
 			if(!mdReqPowerUp()&&jobPowerUp()) {
 				if(levelValue==3){
 					$("#"+selectID).attr("disabled", "disabled");
   			        $("#"+checkID).attr("disabled", "disabled");
   			        $("#" + iid).unbind("click");
 				}
   		     }
 			if(mdReqPowerUp()&&!jobPowerUp()){
   		     	if(levelValue==2){
 					$("#"+selectID).attr("disabled", "disabled");
   			        $("#"+checkID).attr("disabled", "disabled");
   			        $("#" + iid).unbind("click");
 				}
   		     }
 			$.layerShow(iid, url_user);
 		}
   		
        //如果没有 除了 格挡担当的其它权限
// 		if(!jobPowerUp()) {
// 			$("#per").find("select").attr("disabled", "disabled");
// 			$("#per").find("input").attr("disabled", "disabled");
// 			$("#" + iid).unbind("click");
// 		}
 		cardLogic(saleFinishFlg);
   		clearCard(selectID, userid, username, levelid);
 		//加载语言翻译
 		var insertEle = $(".i18n");
		insertEle.each(function() {
	    // 根据i18n元素的 name 获取内容写入
	        $(this).html(part_language_change_new($(this).attr('name')));
	    });
 	}
 	//重新给 + 号绑定事件
 	var ass = $('.addp').clone();
 	$('.addp').remove();
 	$("#per").append(ass);
 	$(".addp").on("click", function() {
 		var personList = $("#per").find('.person');
 		if(personList.length >= 15) {
 			var index = layer.index;
 			layer.close(index);
 			showErrorHandler("MAX_CARD_15", 'info', 'info');
 			return;
 		}
 		var beforeID = $(this).prev().find('i').eq(1).attr('id');
 		if(beforeID == undefined) {
 			var num = 0;
 		} else {
 			var num = parseInt(beforeID.substring(14));
 		}
 		var iid = "jobCreat-user-" + (num + 1);
 		var levelid = "jobCreat-userlevel-" + (num + 1);
 		var userid = "jobCreat-usercd-" + (num + 1);
 		var selectID = "jobCreat-select-" + (num + 1);
 		var username = "jobCreat-username-" + (num + 1);
 		var colorid = "person-color-" + (num + 1);
 		var checkID = "jobCreat-checkID-" + (num + 1);
 		var selectVal = sessionStorage.getItem("nf");
 		var cardStr ="";
 		if(saleFinishFlg==1){
 			cardStr+='<div class="col-md-4 panel person" style="padding:5px;margin-bottom:0;width:225px;background:#fff">' +
 			'<div class="person-t"><div class="person-t-l" id="' + selectID + '">' +
 			'<select name="boostrap-select " class="form-control">' +
 			'<option name="jobregistration_bear_ren" class="i18n" value="3" ></option></select>' +
 			'<input class="sale-add" type="hidden" value="1">'+
 			'</div><div class="person-t-r">' +
 			'<button type="button"  class="close new-card" data-dismiss="modal" aria-hidden="true">×</button>' +
 			'</div></div><div class="person-m"><div class="person-m-l">' +
 			'<i class="iconfont icon-ren" id="' + colorid + '"></i>' +
 			'</div><div class="person-m-m"><div name="userlevel" id="' + levelid + '"></div>' +
 			'<div class="usercd" id="' + userid + '"></div><div class="username" id="' + username + '"></div>' +
 			'</div><div class="person-m-r"><i class="iconfont icon-sousuo" id="' + iid + '"></i></div></div>' +
 			'<div class="person-b"><label><input type="checkbox" class="level_flg" id="inlineCheckbox44" value="option4">' +
 			'<span class="iconfont icon-duihaok"></span><span class="i18n" name="jobregistration_person_in_charge">责任者</span></label></div></div>'
 		}else{
 			cardStr+='<div class="col-md-4 panel person" style="padding:5px;margin-bottom:0;width:225px;background:#fff">' +
 			'<div class="person-t"><div class="person-t-l" id="' + selectID + '">' +
 			'<select name="boostrap-select " class="form-control">' +
 			'<option name="jobregistration_business_ren" class="i18n" value="2" ></option><option name="jobregistration_bear_ren" class="i18n" value="3" ></option></select>' +
 			'</div><div class="person-t-r">' +
 			'<button type="button"  class="close new-card" data-dismiss="modal" aria-hidden="true">×</button>' +
 			'</div></div><div class="person-m"><div class="person-m-l">' +
 			'<i class="iconfont icon-ren" id="' + colorid + '"></i>' +
 			'</div><div class="person-m-m"><div name="userlevel" id="' + levelid + '"></div>' +
 			'<div class="usercd" id="' + userid + '"></div><div class="username" id="' + username + '"></div>' +
 			'</div><div class="person-m-r"><i class="iconfont icon-sousuo" id="' + iid + '"></i></div></div>' +
 			'<div class="person-b"><label><input type="checkbox" class="level_flg" id="'+checkID+'" value="option4">' +
 			'<span class="iconfont icon-duihaok"></span><span class="i18n" name="jobregistration_person_in_charge">责任者</span></label></div></div>'
 		}
 		$(this).before(cardStr);
 		var domI = $(this).prev().find('i').eq(1);
 		$("#" + domI.attr('id')).parents(".person").find("select").val(selectVal);
 		$.layerShow(domI.attr('id'), $.getJumpPath() + 'common/employ_retrieval.html');
   		cardLogic(saleFinishFlg,'mdFlg-click');
 		
   		clearCard(selectID, userid, username, levelid);
 		//加载语言翻译
 		var insertEle = $(".i18n");
		insertEle.each(function() {
	    // 根据i18n元素的 name 获取内容写入
	        $(this).html(part_language_change_new($(this).attr('name')));
	    });
 	});

 }

 //登陆
 function logJob() {

 	if(!inputVerfy()) {
 		return;
 	}
 	var flg = validataRequired();
 	if(!flg) {
 		return;
 	}
 	if(submitVerify() == false) {
 		//showErrorHandler("NO_CHOSE_ADMIN", "info", "info");
 		return;
 	}
 	var sale_id = $("#jobSaleList").val();
 	var arrSaleRate = getSaleRate(sale_id);
   	if(!arrSaleRate) {
	//	$(".saleOp").attr("disabled", true);
	    $(".plan_sale_foreign_amt").html("0.0");
		$(".plansale_tax").html("0.0");
		$(".plan_req_amt").html("0.0");
		return ;
	}
 	if(submitVerify() == "NO_MD") {
 		var msg = showConfirmMsgHandler("NO_CHOSE_MD");
 		var confirmTitle = $.getConfirmMsgTitle();
 		$.messager.confirm(confirmTitle, msg, function(r) {
 			if(!r) {
 				return;
 			} else {
 				var jobcd = $(".upd-jobcd").text();
 				var url = $.getAjaxPath() + "jobLogin";
 				var job = $.getInputVal();
 				var lableList = SelectObj.lableidCollection;
 				var card = loopUserCard();
 				if(jobcd != null && jobcd != undefined && jobcd != "") {
 					job['job_cd'] = jobcd
 					url = $.getAjaxPath() + "jobUpdate";
 					lableList = getLable();
 					var delCard = jobUpGetNewAddCards()['cardDel'];
 					var cardNew = jobUpGetNewAddCards()['cardNew'];
 					job['delcard'] = delCard;
 					job['cardnew'] = cardNew;
 					job['lockFlg'] = objStorage.getLockFlg();
 				}
 				job['julable'] = card;
 				job['cldiv_cd'] = $("#cldiv_cd").val();
 				job['payer_cd'] = $("#payer_cd").val();
 				job['g_company'] = $("#g_company").val();
 				//売上种目
 				job['sale_typ'] = $('#jobSaleList').val();
 				//预计终了日
 				job['plan_dalday'] = $('#scheduled-date').val() == "" ? null : $('#scheduled-date').val();
 				//売上外货code
 				job['plansale_foreign_code'] = $('#plansale_foreign_code').val();
 				//売上金额
 				job['plan_sale'] = $('.saleMoment').val();
 				if($('#switch-state').bootstrapSwitch('state')) {
 					//换算
 					job['plan_sale_cure_code'] = $('.plan_sale_cure_code').val() == "" ? null : $('.plan_sale_cure_code').val();
 					//売上试用日
 					job['plan_sale_use_date'] = $('#common-day').val() == "" ? null : $('#common-day').val();
 					//参照先
 					job['plan_sale_refer'] = $('.plan_sale_refer').val();
 				} else {
 					//换算
 					job['plan_sale_cure_code'] = null;
 					//売上试用日
 					job['plan_sale_use_date'] = null;
 					//参照先
 					job['plan_sale_refer'] = null;
 				}
 				//税入 税拔
 				job['plan_sale_ishave'] = $('.sale_rate_click').find('.active').find('input').val();
 				//预计売上金额
 				job['plan_sale_foreign_amt'] = recoveryNumber($('.plan_sale_foreign_amt').text());
 				//增值锐
 				job['plan_vat_amt'] = recoveryNumber($('#sale_tax').text());
 				//请求金额
 				job['plan_req_amt'] = recoveryNumber($('.plan_req_amt').text());
 				//成本
 				//成本外货code
 				job['plan_cost_foreign_code'] = $('#cost_foreign_type').val();
 				//成本金额
 				job['plancostamt'] = $("input#cost_foreign_amt").numberbox('getValue');
 				if($('#switch-state1').bootstrapSwitch('state')) {
 					//成本换算
 					job['plan_cost_cure_code'] = $('.plan_cost_cure_code').val() == "" ? null : $('.plan_cost_cure_code').val();
 					//成本试用日
 					job['plan_cost_use_date'] = $('#common-dayT').val() == "" ? null : $('#common-dayT').val();

 					//参照先
 					job['plan_cost_refer'] = $('.plan_cost_refer').val();
 				}
 				//税入税拔
 				job['plan_cost_ishave'] = $('.cost_rate_click').find('.active').find('input').val();
 				//预计成本金额
 				job['plan_cost_foreign_amt'] = recoveryNumber($('.plan_cost_foreign_amt').text());
 				//支付金额
 				job['plan_pay_amt'] = recoveryNumber($('.plan_vat_amt').text());
 				//驶入增值税
 				job['plancost_tax'] = recoveryNumber($('#cost_tax').text());
 				//
 				//税金1
 				job['plan_sale_tax1'] = sessionStorage.getItem('sumRate1');
 				//税金2
 				job['plan_sale_tax2'] = sessionStorage.getItem('sumRate2');
 				//税金合计
 				job['plansale_tax_total'] = $('span#plansale_tax_total').text() == "" ? null : recoveryNumber($('span#plansale_tax_total').text());
 				//备考
 				job['remark'] = $('.remark').val();

 				//joblabletrn
 				job['jlTrn'] = lableList;
 				//割当flg
 				job['assignflg'] = $("#md-flg").prop("checked") ? 1 : 0;
 				//原价完了是否跳过
 				/*JOB登陆时原价完了跳过
 				var skip = objStorage.getSkip();
 				if(skip.cost == 1) {
 					job['costFinshFlg'] = 1;
 				} else {
 					job['costFinshFlg'] = 0;
 				}
 				*/
 				job['saleVatChangeFlg'] = objStorage.getSaleTaxFlg();
 				job['costVatChangeFlg'] = objStorage.getCostTaxFlg();
 				var cd = {
 					job_land: job
 				}
// 				console.log(job)
 				$.ajax({
 					url: url,
 					data: JSON.stringify(cd),
 					headers: {
 						"requestID": $.getRequestID(),
 						"requestName": $.getRequestNameByJcZH()
 					},
 					success: function(data) {
 						clearSession();
 						if(data[$.getRequestMetaName()].result == $.getRequestStatusName()) {
 							$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
 							if(data[$.getRequestDataName()] != null) {
 								//showInfoMsgHandler("EXCUTE_SUCCESS", "info", "info");
 								if(data[$.getRequestDataName()] == 540) {
 									var url =  "jcst/job_registration_list.html?view=init&menu=se";
 										showLockInfoMsgHandler('STATUS_VALIDATEPOWER_ERROR',url);
 									return;
 								}
 								if(data[$.getRequestDataName()] == 550) {
 									showErrorHandler('SYS_VALIDATEPOWER_ERROR', 'info', 'info');
 									window.location.href = $.getJumpPath() + "jcst/top_registration.html";
 									return;
 								}
 								if(data[$.getRequestDataName()] == 560) {
 									showErrorHandler('CLIENT_DELETE_ERR', 'info', 'info');
 									//window.location.href = $.getJumpPath() + "jcst/top_registration.html";
 									return;
 								}
 								if(data[$.getRequestDataName()] == 999) {
 									showErrorHandler('DATE_RANGE_ERROR', 'info', 'info');
 									return;
 								}
 								var pdfout = $('.job-pdf-flg input').prop("checked");
 								var jobcd = $(".upd-jobcd").text();
 								if(jobcd != null && jobcd != undefined && jobcd != "") {
 									var url = "jcst/job_registration_list.html?view=init&menu=se";
 									if(pdfout) {
 										OutPutPdfHandler(jobcd, "", "", "jobEdit","","","",0)
 									}
 									if(pdfout != "1") {
 										window.location.href = $.getJumpPath() + url;
 									}

 								} else {
 									var jobcd = data[$.getRequestDataName()];
 									var url = "jcst/job_registration.html";
 									//					 	if(pdfout){
 									//						OutPutPdfHandler(jobcd,"","","jobLand")
 									//					 	}
 									//					 	if(pdfout!="1"){
 									//					 		window.location.href  = $.getJumpPath()+url;
 									//					 	}
 									showInfoMsgHandler("EXECUTE_SUCCESS", "SUCCESS", "SUCCESS", url, "", pdfout, jobcd, "", "", "jobEdit");
 								}
 							} else {

 								showErrorHandler("JOB_LAND_ERROR", "ERROR", "ERROR");

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
 		});
 	} else {

 		var jobcd = $(".upd-jobcd").text();
 		var url = $.getAjaxPath() + "jobLogin";
 		var job = $.getInputVal();
 		var lableList = SelectObj.lableidCollection;
 		var card = loopUserCard();
 		if(jobcd != null && jobcd != undefined && jobcd != "") {
 			job['job_cd'] = jobcd
 			url = $.getAjaxPath() + "jobUpdate";
 			lableList = getLable();
 			var delCard = jobUpGetNewAddCards()['cardDel'];
 			var cardNew = jobUpGetNewAddCards()['cardNew'];
 			job['delcard'] = delCard;
 			job['cardnew'] = cardNew;
 			job['lockFlg'] = objStorage.getLockFlg();
 		}
 		job['julable'] = card;
 		job['cldiv_cd'] = $("#cldiv_cd").val();
 		job['payer_cd'] = $("#payer_cd").val();
 		job['g_company'] = $("#g_company").val();
 		//売上种目
 		job['sale_typ'] = $('#jobSaleList').val();
 		//预计终了日
 		job['plan_dalday'] = $('#scheduled-date').val() == "" ? null : $('#scheduled-date').val();
 		//売上外货code
 		job['plansale_foreign_code'] = $('#plansale_foreign_code').val();
 		//売上金额
 		job['plan_sale'] = $('.saleMoment').val();
 		if($('#switch-state').bootstrapSwitch('state')) {
 			//换算
 			job['plan_sale_cure_code'] = $('.plan_sale_cure_code').val() == "" ? null : $('.plan_sale_cure_code').val();
 			//売上试用日
 			job['plan_sale_use_date'] = $('#common-day').val() == "" ? null : $('#common-day').val();
 			//参照先
 			job['plan_sale_refer'] = $('.plan_sale_refer').val();
 		} else {
 			//换算
 			job['plan_sale_cure_code'] = null;
 			//売上试用日
 			job['plan_sale_use_date'] = null;
 			//参照先
 			job['plan_sale_refer'] = null;
 		}
 		//税入 税拔
 		job['plan_sale_ishave'] = $('.sale_rate_click').find('.active').find('input').val();
 		//预计売上金额
 		job['plan_sale_foreign_amt'] = recoveryNumber($('.plan_sale_foreign_amt').text());
 		//增值锐
 		job['plan_vat_amt'] = recoveryNumber($('#sale_tax').text());
 		//请求金额
 		job['plan_req_amt'] = recoveryNumber($('.plan_req_amt').text());
 		//成本
 		//成本外货code
 		job['plan_cost_foreign_code'] = $('#cost_foreign_type').val();
 		//成本金额
 		job['plancostamt'] = $("input#cost_foreign_amt").numberbox('getValue');
 		if($('#switch-state1').bootstrapSwitch('state')) {
 			//成本换算
 			job['plan_cost_cure_code'] = $('.plan_cost_cure_code').val();
 			//成本试用日
 			job['plan_cost_use_date'] = $('#common-dayT').val() == "" ? null : $('#common-dayT').val();

 			//参照先
 			job['plan_cost_refer'] = $('.plan_cost_refer').val();
 		}
 		//税入税拔
 		job['plan_cost_ishave'] = $('.cost_rate_click').find('.active').find('input').val();
 		//预计成本金额
 		job['plan_cost_foreign_amt'] = recoveryNumber($('.plan_cost_foreign_amt').text());
 		//支付金额
 		job['plan_pay_amt'] = recoveryNumber($('.plan_vat_amt').text());
 		//驶入增值税
 		job['plancost_tax'] = recoveryNumber($('#cost_tax').text());
 		//
 		//税金1
 		job['plan_sale_tax1'] = sessionStorage.getItem('sumRate1');
 		//税金2
 		job['plan_sale_tax2'] = sessionStorage.getItem('sumRate2');
 		//税金合计
 		job['plansale_tax_total'] = $('span#plansale_tax_total').text() == "" ? null : recoveryNumber($('span#plansale_tax_total').text());
 		//备考
 		job['remark'] = $('.remark').val();

 		//joblabletrn
 		job['jlTrn'] = lableList;
 		//割当flg
 		job['assignflg'] = $("#md-flg").prop("checked") ? 1 : 0;
 		//原价完了是否跳过
 		/*
 		var skip = objStorage.getSkip();
 		if(skip.cost == 1) {
 			job['costFinshFlg'] = 1;
 		} else {
 			job['costFinshFlg'] = 0;
 		}
 		*/
 		job['saleVatChangeFlg'] = objStorage.getSaleTaxFlg();
 		job['costVatChangeFlg'] = objStorage.getCostTaxFlg();
 		var cd = {
 			job_land: job
 		}
// 		console.log(job)
 		$.ajax({
 			url: url,
 			data: JSON.stringify(cd),
 			headers: {
 				"requestID": $.getRequestID(),
 				"requestName": $.getRequestNameByJcZH()
 			},
 			success: function(data) {
 				clearSession();
 				if(data[$.getRequestMetaName()].result == $.getRequestStatusName()) {
 					$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
 					if(data[$.getRequestDataName()] != null) {
 						//showInfoMsgHandler("EXCUTE_SUCCESS", "info", "info");
 						if(data[$.getRequestDataName()] == 540) {
 							var url =  "jcst/job_registration_list.html?view=init&menu=se";
 								showLockInfoMsgHandler('STATUS_VALIDATEPOWER_ERROR',url);
 							return;
 						}
 						if(data[$.getRequestDataName()] == 550) {
 							showErrorHandler('SYS_VALIDATEPOWER_ERROR', 'info', 'info');
 							window.location.href = $.getJumpPath() + "jcst/top_registration.html";
 							return;
 						}
 						if(data[$.getRequestDataName()] == 560) {
							showErrorHandler('CLIENT_DELETE_ERR', 'info', 'info');
							//window.location.href = $.getJumpPath() + "jcst/top_registration.html";
							return;
 						}
 						if(data[$.getRequestDataName()] == 999) {
							showErrorHandler('DATE_RANGE_ERROR', 'info', 'info');
							//window.location.href = $.getJumpPath() + "jcst/top_registration.html";
							return;
 						}
 						var pdfout = $('.job-pdf-flg input').prop("checked");
 						var jobcd = $(".upd-jobcd").text();
 						if(jobcd != null && jobcd != undefined && jobcd != "") {
 							var url = "jcst/job_registration_list.html?view=init&menu=se";
 							if(pdfout) {
 								OutPutPdfHandler(jobcd, "", "", "jobEdit","","","",0)
 							}
 							if(pdfout != "1") {
 								window.location.href = $.getJumpPath() + url;
 							}
 							// showInfoMsgHandler("EXCUTE_SUCCESS","info","info",url,"",pdfout,jobcd,"","","jobUpdate");
 						} else {
 							var jobcd = data[$.getRequestDataName()];
 							var url = "jcst/job_registration.html";
 				
 							showInfoMsgHandler("EXECUTE_SUCCESS", "SUCCESS", "SUCCESS", url, "", pdfout, jobcd, "", "", "jobCreate");
 						}
 					} else {

 						showErrorHandler("JOB_LAND_ERROR", "ERROR", "ERROR");

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

 }
 //提交 登录验证
 function submitVerify() {

 	var md_flg = $("#md-flg").prop("checked");
 	var bl = false;
 	var isChosePerson = false;
 	var mdflg = true;
 	//责任者flg
 	var blAdmin = false;
 	$("#per").find('.person').each(function(element, index) {
 		if($(this).find('select').val() == 3) {
 			bl = true;

 		}
 		if($(this).find('.person-m-m').find(".usercd").text() == "") {
 			isChosePerson = true;

 		}
 		if($(this).find('input').prop("checked")) {
 			blAdmin = true;

 		}
 	});

 	if(isChosePerson) {
 		showErrorHandler("NO_CHOSE_PERSON", "info", "info");
 		return false;
 	}
 	if(!blAdmin) {
 		showErrorHandler("NO_CHOSE_ADMIN", "info", "info");
 		return false;
 	}
 	if(!md_flg && !bl) {
 		return "NO_MD";
 	}
 	if(blAdmin && !isChosePerson) {
 		return true;
 	}

 }

 function inputVerfy() {
 	var flg = true;
 	var saleIsHave = $('#switch-state').bootstrapSwitch('state');
 	if(saleIsHave == 1) {
 		//$('#common-day').addClass("date-box-required");
 		$(".plan_sale_cure_code").addClass("required");
 		//$(".plan_sale_refer").addClass("required");
 	} else {
 		//$('#common-day').removeClass("date-box-required");
 		$(".plan_sale_cure_code").removeClass("required");
 		//$(".plan_sale_refer").removeClass("required");
 	}

 	var costIsHave = $('#switch-state1').bootstrapSwitch('state');
 	if(costIsHave == 1) {
 		//$('#common-dayT').addClass("date-box-required");
 		$(".plan_cost_cure_code").addClass("required");
 		//$(".plan_cost_refer").addClass("required");
 	} else {
 		//$('#common-dayT').removeClass("date-box-required");
 		$(".plan_cost_cure_code").removeClass("required");
 		//$(".plan_cost_refer").removeClass("required");
 	}
 	if($('#pcost').numberbox('getValue') == "" ||
 		(!(/^\d{0,1}(\d{0,11})\.{0,1}(\d{1,2})?$/.test($('#pcost').numberbox('getValue'))) &&
 			!(/^\d{0,1}(\d{0,11})\.{0,1}(\d{1,2})?$/.test(($('#pcost').numberbox('getValue') * -1)))
 		)) {
 		var point = $.getDefaultPoint();
 		$('#pcost').numberbox('setValue', '');
 		$("span.tax_total").text(Number(0).toFixed(point));
 		$("span.get_total").text(Number(0).toFixed(point));
 		$("span.sumRate").text("[0.00%]");
 		$(".sale-input").setBorderRed();
 		validate($(".sale-input"), part_language_change_new('NODATE'));
 		//$(".sale-input").find("span.numberbox")[0].scrollIntoView(true);
 		//$(".sale-input").find("span.numberbox")[0].scrollIntoView(false);
 		$('#content').scrollTop(0);
 		if(!$('.panel.window.panel-htop.messager-window').exist()) {
 			showErrorHandler("NOT_EMPTY", "ERROR", "ERROR");
 		}
 		//flg = false
 		//return flg;
 	} else {
 		$(".sale-input").setBorderBlack();
 		$(".sale-input").tooltip('destroy');
 	}
 	if($('input#cost_foreign_amt').numberbox('getValue') == "" ||
 		(!(/^\d{0,1}(\d{0,11})\.{0,1}(\d{1,2})?$/.test($('input#cost_foreign_amt').numberbox('getValue'))) &&
 			!(/^\d{0,1}(\d{0,11})\.{0,1}(\d{1,2})?$/.test(($('input#cost_foreign_amt').numberbox('getValue') * -1))))) {
 		var point = $.getDefaultPoint();
 		$('input#cost_foreign_amt').numberbox('setValue', '');
 		$("span.tax_total").text(Number(0).toFixed(point));
 		$("span.get_total").text(Number(0).toFixed(point));
 		$("span.sumRate").text("[0.00%]");
 		$(".price-input").setBorderRed();
 		validate($(".price-input"), part_language_change_new('NODATE'));
 		//$(".price-input").find("span.numberbox")[0].scrollIntoView(true);
 		//$(".price-input").find("span.numberbox")[0].scrollIntoView(false);
 		$('#content').scrollTop(0);
 		if(!$('.panel.window.panel-htop.messager-window').exist()) {
 			showErrorHandler("NOT_EMPTY", "ERROR", "ERROR");
 		}
 		//flg = false
 		//return flg;
 	} else {
 		$(".price-input").setBorderBlack();
 		$(".price-input").tooltip('destroy');
 	}

 	return flg;
 }
 //删除job
 function delJob() {

 	var jobcd = $(".upd-jobcd").text();
 	var lockFlg = objStorage.getLockFlg();
 	var cd = {
 		job_cd: jobcd,
 		lockFlg:lockFlg
 	}
 	$.ajax({
 		url: $.getAjaxPath() + "delJob",
 		data: JSON.stringify(cd),
 		headers: {
 			"requestID": $.getRequestID(),
 			"requestName": $.getRequestNameByJcZH()
 		},
 		success: function(data) {
 			clearSession();
 			if(data[$.getRequestMetaName()].result == $.getRequestStatusName()) {
 				$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
 				if(data[$.getRequestDataName()] > 0) {
 					//showInfoMsgHandler("EXCUTE_SUCCESS", "info", "info");
 					if(data[$.getRequestDataName()] == 540) {
 						var  url = "jcst/job_registration_list.html?view=init&menu=se";
 							showLockInfoMsgHandler('STATUS_VALIDATEPOWER_ERROR',url);
 						return;
 					}
 					if(data[$.getRequestDataName()] == 550) {
 						showErrorHandler('SYS_VALIDATEPOWER_ERROR', 'info', 'info');
 						window.location.href = $.getJumpPath() + "jcst/top_registration.html";
 						return;
 					}
 					var url = "jcst/job_registration_list.html?view=init&menu=se";
 					//showInfoMsgHandler("EXCUTE_SUCCESS", "info", "info", url);
 					window.location.href = $.getJumpPath() + url;
 				} else {

 					showErrorHandler("JOB_LAND_ERROR", "ERROR", "ERROR");

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
 //时间绑定
 function bindAction() {
 	//$("#test").click(jobOutPut);
 	$(".log_job").click(logJob);
 	$("#add_lable").click(function() {

 		addLable("new_lable", "options_lable", "0");

 	});
 	//売上种目下拉变的时候
 	$("#jobSaleList").change(function() {
 		var flg = calculationSale();
 		if(flg) {
 			calculationCost();
 		}

 	});
 	$('#switch-state').on('switchChange.bootstrapSwitch', function(event, state) {
 		//calculationSale();
 		//外货开关变得时候  需要把 外货下拉变为空  金额单位变为本国货币 汇率 什么的都清空 计算

 		resetInputArea(1, state);

 		var itemcdSale = $('#plansale_foreign_code').val();
 		var itemcd = $('#cost_foreign_type').val();
 		var moneyObjSale = getMoneyCode(itemcdSale);
 		var moneyObj = getMoneyCode(itemcd);
 		$('#need_change_code1').text(moneyObjSale["val"]);

 		var retention_digit_p = parseInt($.getDefaultPoint());
 		var retention_digit = parseInt($.getDefaultPoint());
 		if(moneyObjSale['salePoint'] != null && moneyObjSale['salePoint'] != "") {
 			retention_digit = parseInt(moneyObjSale['salePoint']); //売上保留小数
 		}
 		if(moneyObj['salePoint'] != null && moneyObj['salePoint'] != "") {
 			retention_digit_p = parseInt(moneyObj['salePoint']); //売上保留小数
 		}

 		bus_price_common(retention_digit, retention_digit_p);

 	});
 	$('#switch-state1').on('switchChange.bootstrapSwitch', function(event, state) {
 		//calculationCost();
 		resetInputArea(0, state);
 		var itemcdSale = $('#plansale_foreign_code').val();
 		var itemcd = $('#cost_foreign_type').val();
 		var moneyObjSale = getMoneyCode(itemcdSale);
 		var moneyObj = getMoneyCode(itemcd);
 		$('#cost_need_change_money1').text(moneyObj["val"]);
 		// job更新 根据实际登录 设置 输入小数点位数限制
 		var retention_digit_p = parseInt($.getDefaultPoint());
 		var retention_digit = parseInt($.getDefaultPoint());
 		if(moneyObjSale['salePoint'] != null && moneyObjSale['salePoint'] != "") {
 			retention_digit = parseInt(moneyObjSale['salePoint']); //売上保留小数
 		}
 		if(moneyObj['salePoint'] != null && moneyObj['salePoint'] != "") {
 			retention_digit_p = parseInt(moneyObj['salePoint']); //売上保留小数
 		}

 		bus_price_common(retention_digit, retention_digit_p);

 	});
 	$(".sale_rate_click").click(function(e) {
 		var value = $(e.target).closest('label').find('input').val();
 		var textval = $('.sale_rate_click').find('.active').find('input').val();
 		if(value == textval) {
 			return;
 		}
 		calculationSale(value);
 	});
 	$(".cost_rate_click").click(function(e) {
 		var value = $(e.target).closest('label').find('input').val();
 		var textval = $('.cost_rate_click').find('.active').find('input').val();
 		if(value == textval) {
 			return;
 		}
 		calculationCost(value);
 	});
 	$('.cal-sale').change(function() {
 		calculationSale();
 	});
 	$('.cal-cost').change(function() {
 		calculationCost();
 	});
 	$('#plansale_foreign_code').change(function() {
 		var itemcdSale = $('#plansale_foreign_code').val();
 		var itemcd = $('#cost_foreign_type').val();
 		var moneyObjSale = getMoneyCode(itemcdSale);
 		var moneyObj = getMoneyCode(itemcd);
 		$('#need_change_code1').text(moneyObjSale["val"]);
 		calculationSale();
 		var retention_digit_p = parseInt($.getDefaultPoint());
 		var retention_digit = parseInt($.getDefaultPoint());
 		if(moneyObjSale['salePoint'] != null && moneyObjSale['salePoint'] != "") {
 			retention_digit = parseInt(moneyObjSale['salePoint']); //売上保留小数
 		}
 		if(moneyObj['salePoint'] != null && moneyObj['salePoint'] != "") {
 			retention_digit_p = parseInt(moneyObj['salePoint']); //売上保留小数
 		}

 		bus_price_common(retention_digit, retention_digit_p);

 	});
 	$('#cost_foreign_type').change(function() {
 		var itemcdSale = $('#plansale_foreign_code').val();
 		var itemcd = $('#cost_foreign_type').val();
 		var moneyObjSale = getMoneyCode(itemcdSale);
 		var moneyObj = getMoneyCode(itemcd);
 		$('#cost_need_change_money1').text(moneyObj["val"]);
 		calculationCost();
 		// job更新 根据实际登录 设置 输入小数点位数限制
 		var retention_digit_p = parseInt($.getDefaultPoint());
 		var retention_digit = parseInt($.getDefaultPoint());
 		if(moneyObjSale['salePoint'] != null && moneyObjSale['salePoint'] != "") {
 			retention_digit = parseInt(moneyObjSale['salePoint']); //売上保留小数
 		}
 		if(moneyObj['salePoint'] != null && moneyObj['salePoint'] != "") {
 			retention_digit_p = parseInt(moneyObj['salePoint']); //売上保留小数
 		}

 		bus_price_common(retention_digit, retention_digit_p);
 		//calculationSale();

 	});
 	$('input.cal-sale-box').numberbox({
 		"onChange": function() {
 			calculationSale();
 		}
 	});

 	$('input.cal-cost-box').numberbox({
 		"onChange": function() {
 			calculationCost();
 		}
 	});

 	//千分符数字还原为默认
 	$('input.cal-cost-box').next('span').find('input').focus(function(e) {
 		var value = e.target.value;
 		if(value != '') {
 			var new_value = recoveryNumber(value);
 			$(this).val(new_value);
 		}
 	})

 	$('input.cal-sale-box').next('span').find('input').focus(function(e) {
 		var value = e.target.value;
 		if(value != '') {
 			var new_value = recoveryNumber(value);
 			$(this).val(new_value);
 		}
 	});
 	//输入框正则验证小数小位数
 	$(".plan_sale_cure_code").keyup(function() {
 		var flag = number_fl_ck(this.value, 10, 5);
 		if(!flag) {
 			this.value = number_va_length(this.value, 10, 5);
 			//showErrorHandler("CURE_STYLE_ERR", 'info', 'info');
 			return;
 		}
 	});
 	$(".plan_cost_cure_code").keyup(function() {
 		var flag = number_fl_ck(this.value, 10, 5);
 		if(!flag) {
 			this.value = number_va_length(this.value, 10, 5);
 			//showErrorHandler("CURE_STYLE_ERR", 'info', 'info');
 			return;
 		}
 	});
 	$(".filter-lable").click(function() {

 		var str = $("#lableStr").val();
 		filterLable(str);
 	});
 	//删除job
 	$("#del_job").click(function() {
 		var msg = showConfirmMsgHandler("SURE_DELETE");
 		var confirmTitle = $.getConfirmMsgTitle();
 		$.messager.confirm(confirmTitle, msg, function(r) {
 			if(r) {
 				delJob();
 			} else {
 				return;
 			}
 		});
 	});
 	$("#set-tax").click(function(e) {
 		var haveVatFlg = $('.sale_rate_click').find('.active').find('input').val();
 		var saleCostAmt = recoveryNumber($('#sale_foreign_amt').text());
 		var reqPayAmt = recoveryNumber($('#req_amt').text());
 		var vatAmt = recoveryNumber($('.edit-tax').val());
 		if(vatAmt == "") {
 			//			showErrorHandler("NO_SET_TAX",'info','info');
 			//$(".sale-border").find("span.numberbox").css("border-color", "red");
 			$(".sale-border").setBorderRed();
 			validate($(".sale-border"), part_language_change_new('NODATE'));
 			if(!$('.panel.window.panel-htop.messager-window').exist()) {
 				showErrorHandler("NOT_EMPTY", "ERROR", "ERROR");
 			}
 			return;
 		}
 		var obj = calculateMoneyByVatChangeHandler(haveVatFlg, saleCostAmt, reqPayAmt, vatAmt);
 		$('#sale_foreign_amt').text(formatNumber(obj["saleCostAmt"]));
 		$('#sale_tax').text(formatNumber(obj["vatAmt"]));
 		$('#req_amt').text(formatNumber(obj["reqPayAmt"]));
 		upRateArea();
 		var baseSaleTax = objStorage.getSaleTax();
 		if(vatAmt != baseSaleTax) {
 			objStorage.saleTaxFlg = 1;
 		}
 		var index = layer.index;
 		layer.close(index);
 	});
 	//增值税编辑 原价部分
 	$("#set-tax-price").click(function(e) {
 		if(!calByTaxChange()) {
 			return;
 		}
 		upRateArea();
 		var baseCostTax = objStorage.getCostTax();
 		var costAmt = recoveryNumber($('.edit-tax-price').numberbox('getValue'));
 		if(baseCostTax != costAmt) {
 			objStorage.costTaxFlg = 1;
 		}
 		var index = layer.index;
 		layer.close(index);
 	});

 }
 //开关改变重置一些值 flg 判断是卖上还是原价
 function resetInputArea(flg, state) {
 	if(flg == 1) {
 		if(state == 0) {
 			$("#plansale_foreign_code").val("");
 		} else {
 			$("#plansale_foreign_code").find("option[value='']").addClass("hidden");
 			$("#plansale_foreign_code").val($("#plansale_foreign_code").find("option").eq(1).val());
 		}

 		$(".plan_sale_cure_code").val("");
 		$("#common-day").datebox('setValue', "");
 		$(".plan_sale_refer").val("");
 		$('#need_change_code1').text(sessionStorage.getItem("localMoneyCode"));
 		calculationSale();
 	}
 	if(flg == 0) {
 		if(state == 0) {
 			$("#cost_foreign_type").val("");
 		} else {
 			$("#cost_foreign_type").find("option[value='']").addClass("hidden");
 			$("#cost_foreign_type").val($("#cost_foreign_type").find("option").eq(1).val())
 		}

 		$(".plan_cost_cure_code").val("");
 		$("#common-dayT").datebox('setValue', "");
 		$(".plan_cost_refer").val("");
 		$('#cost_need_change_money1').text(sessionStorage.getItem("localMoneyCode"));
 		calculationCost();
 	}

 }
 //job登录根据权限 控制 卡片下拉选项
 function powerDecCardSelct() {
 	//把卡片选中 （选中了不能选择割当）
 	//$('#md-flg').prop('checked',true);
 	$('#md-flg').attr("disabled", true);
 	//$('#per').find('.person select:nth-child(n) option[value="3"]').addClass("hidden");
 	$('#per').find('.person select:nth-child(n) option[value="3"]').addClass("hidden");
 }
 //增值税变化 重新计算税金和营业额
 function upRateArea() {
 	/*计算税金
 	 *@param planSale		预计卖上金额
 	 * @param saleAmt		实际卖上金额
 	 * @param planCost		预计成本合计
 	 * @param costTotal		实际成本合计
 	 * @param reqAmt		请求金额
 	 * @param payAmt		支付金额
 	 * @param saleVatAmt	卖上增值税
 	 * @param costVatAmt	仕入增值税（成本增值税）
 	 * @param rate			文化税率
 	 * @param rate1			附加税率
 	 * @param isCostFinsh	是否成本录入终止，0：未终止；1：终止
 	 * @param costCountNums	成本条数
 	 * ***********add params by wy 2018.09.04
 	 * @param point	本国货币小数点位数
 	 * @param taxFormatFlg	0052 003
 	 */
 	var sale_id = $("#jobSaleList").val();
 	var arrSaleRate = getSaleRate(sale_id);
 	if(!arrSaleRate) {
 		//$(".log_job").attr('disabled', true);
 		$(".plan_sale_foreign_amt").html("0.0");
		$(".plansale_tax").html("0.0");
		$(".plan_req_amt").html("0.0");
	//$('input.cal-sale-box').next('span').find('input').html("0.0");
 		return;
 	}
 	var planSale = recoveryNumber($('#sale_foreign_amt').text());
 	var planCost = recoveryNumber($('span#cost_foreign_amt').text());
 	var reqAmt = recoveryNumber($('#req_amt').text());
 	//var payAmt = recoveryNumber($('#pay_amt').text());
 	var saleVatAmt = recoveryNumber($('#sale_tax').text());
 	//var costVatAmt = recoveryNumber($('#cost_tax').text());
 	var rate = arrSaleRate['rate2'];
 	var rate1 = arrSaleRate['rate3'];
 	var point = objStorage.getPersonMoneyCode()['pointNumber'];
 	var taxFormatFlg = searchValue(objStorage.getListTax(), "itemcd", "003", "aidcd");
 	var foreignFormatFlg = searchValue(objStorage.getListForeignTax(), "itemcd", "001", "aidcd");
 	var costFinishFlg = objStorage.getCostFinishFlg();
 	var costNum = objStorage.getCostNum();
 	var realCost = objStorage.getRealCost();

 	//	if(costFinishFlg==1){
 	//		//没有实际成本 0
 	//	 if(costNum==0){
 	//	 	var payAmt = 0;
 	//	 	var costVatAmt = 0;
 	//	 	//实际原价
 	//	    var priceTrue = 0;
 	//	 }else{
 	//	 	//有实际成本 用实际
 	//	 	 var payAmt =  recoveryNumber(realCost['paysum']);
 	//   	 var costVatAmt = recoveryNumber(realCost['sumvat']);
 	//	 	 var priceTrue =recoveryNumber(realCost['sumamt']);
 	//	 }
 	//		
 	//	}else{
 	//	//原价未完了  没有实际成本 用预计
 	//  if(costNum==0){
 	var payAmt = recoveryNumber($('.plan_vat_amt').text());
 	var costVatAmt = recoveryNumber($('.plancost_tax').text());
 	var priceTrue = planCost;

 	//  }else{
 	//  	//有实际成本用实际
 	//       var payAmt =  recoveryNumber(realCost['paysum']);
 	//   	 var costVatAmt = recoveryNumber(realCost['sumvat']);
 	//	 	 var priceTrue =recoveryNumber(realCost['sumamt']);
 	//   	 
 	//  }
 	//	}

 	var taxObj = calculateTaxHandler(planSale, "", planCost, priceTrue, reqAmt, payAmt, saleVatAmt,
 		costVatAmt, rate, rate1, costFinishFlg, costNum, point, taxFormatFlg, foreignFormatFlg);
 	//重置sesssionStorage 中的税金1和税金2

 	sessionStorage.setItem('sumRate1', taxObj['tax']);
 	sessionStorage.setItem('sumRate2', taxObj['tax1']);
 	$("span#plansale_tax_total").text(formatNumber(taxObj['taxTotal']));
 	$('.get_total').text(formatNumber(taxObj['profit']));
 	//如果 卖上金额为0 营业收益率 显示为INF
 	if(parseInt(recoveryNumber($('#sale_foreign_amt').text())) == 0) {
 		$(".sumRate").text("[INF%]");
 	} else {
 		$(".sumRate").text('[' + taxObj['profitRate'] + '%]');
 	}

 }
 //select切换 清空
 function clearCard(selectID, userid, username, levelid) {

 	$("#" + selectID).change(function() {
 		$("#" + userid).text("");
 		$("#" + username).text("");
 		$("#" + levelid).text("");
 	});

 }
 function clearSession(){
	sessionStorage.removeItem('saleRate1');
	sessionStorage.removeItem('saleRate2');
	sessionStorage.removeItem('sumRate1');
	sessionStorage.removeItem('saleRate2');
	sessionStorage.removeItem('nf');
    sessionStorage.removeItem('isSaleFinishFlg');
}

 //创建个对象储存返回来的值
 /*
  * saleMessage 储存初始化加载的売上种目信息
  * lableMessage 标签信息
  * moneyMessage 货币信息
  * personMoneyCode 个人货币信息
  * lableIDCollection 标签的id数组
  */
 objStorage = {
 	saleMessage: null,
 	lableMessage: null,
 	moneyMessage: null,
 	personMoneyCode: null,
 	lableIdCollection: null,
 	listTax: null,
 	listForeignTax: null,
 	costRate: null,
 	cardMessage: null,
 	delCard: [],
 	costFinishFlg: 0,
 	costNum: 0,
 	realCost: null,
 	skip: null,
 	rateMsg: null,
 	saleTaxFlg: 0,
 	saleTax: null,
 	costTax: null,
 	costTaxFlg: 0,
 	lockFlg:0,
 	getSaleMessage: function() {
 		return objStorage.saleMessage;
 	},
 	getLableMessage: function() {
 		return objStorage.lableMessage;
 	},
 	getMoneyMessage: function() {
 		return objStorage.moneyMessage;
 	},
 	getPersonMoneyCode: function() {
 		return objStorage.personMoneyCode;
 	},
 	getLableIdCollection: function() {
 		return objStorage.lableIdCollection;
 	},
 	getListTax: function() {
 		return this.listTax;
 	},
 	getListForeignTax: function() {
 		return this.listForeignTax;
 	},
 	getCostRate: function() {
 		return this.costRate;
 	},
 	getCardMessage: function() {
 		return this.cardMessage;
 	},
 	getDelCard: function() {
 		return this.delCard;
 	},
 	getCostFinishFlg: function() {
 		return this.costFinishFlg;
 	},
 	getCostNum: function() {
 		return this.costNum;
 	},
 	getRealCost: function() {
 		return this.realCost;
 	},
 	getSkip: function() {
 		return this.skip;
 	},
 	getRateMsg: function() {
 		return this.rateMsg;
 	},
 	getSaleTaxFlg: function() {
 		return this.saleTaxFlg;
 	},
 	getSaleTax: function() {
 		return this.saleTax;
 	},
 	getCostTaxFlg: function() {
 		return this.costTaxFlg;
 	},
 	getCostTax: function() {
 		return this.costTax;
 	},
 	getLockFlg:function(){
 		return this.lockFlg;
 	}
 }