function initShow() {
	var jobcd = parent.job_cd
	var saleno = null;
	var nowUrl = window.location.href ;
	$(".job_cd").text(jobcd);
 var job = {
	  job_cd:jobcd,
	  saleno:saleno
	}
$.ajax({
		url: $.getAjaxPath() + "initjobdetail",
		data: JSON.stringify(job),
		headers: {
			"requestID": $.getRequestID(),
			"requestName": $.getRequestNameByJcZH()
		},
		success: function(data) {
//			console.log(data);
			if(data[$.getRequestMetaName()].result == $.getRequestStatusName()) {
				$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
				if(data[$.getRequestDataName()] != null) {
					var saleno = data[$.getRequestDataName()].sale_no;
					saleStorage.saleVatAmt = saleno;
					saleStorage.saleLockFlg = data[$.getRequestDataName()].saleLockFlg;
					saleStorage.jobLockFlg = data[$.getRequestDataName()].jobLockFlg;
					var job = data[$.getRequestDataName()]["jobLandVo"].jobland;
					var point = parseInt($.getDefaultPoint());
					// var skip = data[$.getRequestDataName()]["jobLandVo"].jobland.skip;
					// saleStorage.skip = skip;
					var lable_list = data[$.getRequestDataName()]["jobLandVo"].list_lable;
					var sale_list = data[$.getRequestDataName()]["jobLandVo"].list_sale;
					saleStorage.personMoneyCode = data["userInfo"];
					saleStorage.saleVatRate = sale_list;
					var out_list = [];
					//限制会计年月
					//$.dateLimit("dalday", data[$.getRequestDataName()]["jobLandVo"]['accountyear'][0].itemvalue, 0);
					//$.dateLimit("plan_dalday", data[$.getRequestDataName()]["jobLandVo"]['accountyear'][0].itemvalue, 0);
					//默认货币
					setPersonMoneyCode(saleStorage.getPersonMoneyCode());
					//外货下拉
					out_list['plansale_foreign_code'] = data[$.getRequestDataName()]["jobLandVo"].list_comst;
					out_list['cost_foreign_type'] = data[$.getRequestDataName()]["jobLandVo"].list_comst;
					out_list['plansale_foreign_code_off'] = data[$.getRequestDataName()]["jobLandVo"].list_comst;
					out_list['plan_cost_foreign_code'] = data[$.getRequestDataName()]["jobLandVo"].list_comst;
					saleStorage.listTax = data[$.getRequestDataName()]["jobLandVo"].list_tax;
				    var costFinishFlg = data[$.getRequestDataName()]["jobLandVo"]['jobland'].costFinshFlg;
					saleStorage.costFinishFlg = costFinishFlg;
					var costNum = data[$.getRequestDataName()]["jobLandVo"]['list_cost'][0].costnum;
					saleStorage.costNum = costNum;
					saleStorage.listForeignTax = data[$.getRequestDataName()]["jobLandVo"].list_foreign_tax;
					saleStorage.companyMessage = data[$.getRequestDataName()]['company'];
					saleStorage.out_list = out_list;
					SelectObj.setStringFlg = "_";
					SelectObj.selectData = out_list;
					SelectObj.setSelectID = ['plansale_foreign_code', 'cost_foreign_type', 'plansale_foreign_code_off', 'plan_cost_foreign_code'];
					SelectObj.setSelectOfLog();
					SelectObj.setLableList = lable_list;
					//页面加载设置job信息
					setJobMessage(job, sale_list, saleno);
					//备考超出点击效果
					textOverhidden();
					
				    //$('.model_second,.model_fourth').click();
					//设置売上实际信息
					if(saleno != null && saleno != '' && saleno != undefined) {
						setRealSaleMessage(data[$.getRequestDataName()]);
						//变换卖上更新卡片的 name属性
						$(".box").eq(0).find("#languageNameAdd").attr("name", "job_de_sa_login");
						$(".box").eq(1).find("#languageNameUpd").attr("name", "order_update_ren");
						part_language_change($(".box").eq(0).find("#languageNameAdd"));
						part_language_change($(".box").eq(1).find("#languageNameUpd"));
						
					} else {
						$('#dalday').datebox('setValue', job['plan_dalday'] == null ? '' : strToDate(job['plan_dalday'], 1));
						//没有实际売上，默认显示预计tab
						if(costNum==0){
							$('.model_second,.model_fourth').click();
							bus_price_common(point, 3);
						    $('.sale_head label').attr('disabled',true);
						}else{
							$('.model_second').click();
							bus_price_common(point, 3);
						    $('#job_card label').attr('disabled',true);
						}
					}
					//设置实际原价
					setRealCostMessage(data[$.getRequestDataName()].realcost,costNum,costFinishFlg);
					labelToMySelect(lable_list);
					SelectObj.setChooseLableList = job['jlTrn'];
					setLableArea(job['jlTrn']);
					edit_common(point);
					sale_hide();
					bookCheckBox(data[$.getRequestDataName()]);
					lableShowByPower();
					
					$('.sale_head').change(function(){
						calculationSale();
					});
					$("#moveSet").click(function (){
						planMoveSet()
					});
					calculationSale();
					//initCal();
				} else {
					showErrorFunHandler("PAGE_INIT_FAIL", "ERROR", "ERROR");
				}
			} else {
				showErrorFunHandler(data[$.getRequestMetaName()].result, "ERROR", "ERROR");
			}
			reload_button();
		
		},
		error: function(msg) {
			//window.location.href=$.getJumpPath();
		}
	});
}


//隐藏原价表单控件
function sale_hide(){
	$(".add_tax_price_on").addClass('hidden');
	$(".add_tax_bus").addClass("hidden");
	$(".add_tax_price_on").hide();
	$(".price_on").find("#cost_foreign_type").hide();
	$(".price_on").find(".mb-5").hide();
	$(".price_on").find(".cost_rate_click").hide();
	$(".price_on").find(".bootstrap-switch-off").hide();
	$(".price_on").find("label[name='jobregistration_foreign_currency']").hide();
	$(".price_on").find("label[name='jobregistration_amount']").hide();	
	
	$("input").attr("disabled",true);
	$('#pcost').numberbox('disable', true);
	$('#common-day').datebox('disable', true);
	$("#reqflag").attr("disabled",true);
	$("#reqflag").attr("disabled",true);
	
	$("select").attr("disabled",true);
	$("textarea").attr("disabled",true);
	$(".sale_rate_click ").attr("disabled",true);
	$('#switch-state').bootstrapSwitch('disabled',true);
	$("#plansale_foreign_code").css("background", "#eee");
	$(".sale_rate_click").removeAttr("data-toggle");
	$('.panel-body.panel.switch').css('background-color','#fff');
	$('.panel-body.panel.switch').next().css('background','inherit');
}

function reload_button(){
		$(".business").addClass("active");
		$(".business_off").addClass("active");
		$(".price_on").addClass("active");
		$(".price").addClass("active");
		//state表示on/off状态
		var state1 = $('.judge1').bootstrapSwitch('state');
		var state2 = $('.judge4').bootstrapSwitch('state');
		var state3 = $('.judge2').bootstrapSwitch('state');
		var state4 = $('.judge3').bootstrapSwitch('state');
//		console.log(state1, state2, state3, state4)
		//tip=false表示当前点击状态
		var tip1 = $(".judge1").closest('.active').hasClass("hidden");
		var tip2 = $(".judge4").closest('.active').hasClass("hidden");
		var tip3 = $(".judge2").closest('.active').hasClass("hidden");
		var tip4 = $(".judge3").closest('.active').hasClass("hidden");
//		console.log(tip1, tip2, tip3, tip4)
		//进入页面判断初始状态
		if(tip1 == false) {
			if(tip3 == false) {
				if(state1 == true || state3 == true) {
					$(".switch").css("height", "333px");
					$(".switchot").css("height", "333px");
				} else if(state1 == false && state3 == false) {
					$(".switch").css("height", "130px");
					$(".switchot").css("height", "130px");
				}
			}
		}
	}

//权限控制 发行书部分
function bookCheckBox(data) {
	//请求书
	if(data['req'] == null || data['req'] == "") {
		$("#requpname").text("");
		$("#requpdate").text("");
		$("#reqtimes").text("0");
	} else {
		$("#requpname").text(data['req'].upname);
		$("#requpdate").text(strToDate(data['req'].update, 3));
		$("#reqtimes").text(data['req'].reqtimes);
		$("#reqcolor").css("color",data['req'].reqcolor);
	}
	if(data['inv'] == null || data['inv'] == "") {
		$("#invupname").text("");
		$("#invupdate").text("");
		$("#invtimes").text("0");
	} else {
		//发行书
		$("#invupname").text(data['inv'].upname);
		$("#invupdate").text(strToDate(data['inv'].update, 3));
		$("#invtimes").text(data['inv'].invoicetimes);
		$("#invcolor").css("color",data['inv'].invcolor);
	}
	var reqPower = sessionStorage.getItem("reqPower");
	var invPower = sessionStorage.getItem("invPower");
	if(reqPower == "unhave") {
		$("#reqFlg").attr("disabled", true);
	}
	if(invPower == "unhave") {
		$("#invFlg").attr("disabled", true);
	}
	sessionStorage.removeItem("reqPower");
	sessionStorage.removeItem("invPower");
}
//売上种目切换时获取当前选择的税金
function getSaleRate(sale_id) {
	$(".saleOp").attr("disabled", false);
	var date1 = $("#dalday").datebox('getValue');
	if(date1 == '' || date1 == null) {
		showErrorFunHandler("DATE_NOT_FILL", "info", "info");
		return false;
	}
	var saleVatRate = [];
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
//			console.log(data);
			if(data[$.getRequestMetaName()].result == $.getRequestStatusName()) {
				$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
				if(data[$.getRequestDataName()] != null) {
					var saleMessage = data[$.getRequestDataName()];
					if(saleMessage == null) {
						showErrorFunHandler("NOT_FOUND_RATE", "warning", "warning");
						// $("#dalday").datebox('setValue','');
						return;
					}
					if(saleMessage.var_rate == null) {
						showErrorFunHandler("NOT_FOUND_RATE", "warning", "warning");
						// $("#dalday").datebox('setValue','');
						return;
					}
					if(saleMessage.rate2 == null) {
						showErrorFunHandler("NOT_FOUND_RATE", "warning", "warning");
						// $("#dalday").datebox('setValue','');
						return;
					}
					if(saleMessage.rate3 == null) {
						showErrorFunHandler("NOT_FOUND_RATE", "warning", "warning");
						// $("#dalday").datebox('setValue','');
						return;
					}
					saleVatRate['vat_rate'] = saleMessage.var_rate;

					saleVatRate['rate1'] = saleMessage.rate1;
					saleVatRate['rate2'] = saleMessage.rate2;
					saleVatRate['rate3'] = saleMessage.rate3;
				} else {
					showErrorFunHandler("EXECUTE_FAIL", "ERROR", "ERROR");
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
	var moneyMessage = saleStorage.getOut_List()['plansale_foreign_code'];
	var saleCode = null;
	var salePoint = null;
	var val = null;
	var toplange = parent.$('#language').val();
	for(var i in moneyMessage) {
		if(moneyMessage[i].itemcd == itemcd) {
			saleCode = moneyMessage[i].changeunit;
			salePoint = moneyMessage[i].itemvalue;
			switch(toplange) {
				case "jp":
					val = moneyMessage[i]['itemname_jp'];
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

function setRealCostMessage(real,costNum,costFinishFlg) {
	var point = $.getDefaultPoint();
	$(".oncost").text(real['amt'] == null ? Number(0).toFixed(point) : formatNumber(real['amt']));
	$("#cost_tax").text(real['vatamt'] == null ? Number(0).toFixed(point) : formatNumber(real['vatamt']));
	$("#pay_amt").text(real['payamt'] == null ? Number(0).toFixed(point) : formatNumber(real['payamt']));
	if(costNum==0){
		if(costFinishFlg==0){
		    $(".model_fourth").click();
			$(".model_thired").attr("disabled","disabled");
		}
		
	}
	
}
//设置job部分的值
function setJobMessage(job, sale_list, saleno) {

	//$(".job_cd").text();
	$('.job_name').text(job['job_name']);
	$('.sale_cd').val(job['sale_typ']);
	$('.sale_type').text(sale_list[0].sale_name);
	$('.cldiv_cd').val(job['cldiv_cd']);
	$('.divname_full').text(job['divnm']);
	$('.payer_cd').val(job['payer_cd']);
	$('.payer_name').text(job['payername']== null ? "" : job['payername']);
	$('.g_company').val(job['gcompanyname'] == null ? "" : job['g_company']);
	$('.g_company_name').text(job['gcompanyname'] == null ? "" : job['gcompanyname']);
	$('.plan_dalday').text(strToDate(job['plan_dalday'], 1));
	$('.remark pre').html(job['remark']);
	
	
	
	if(saleno != null && saleno != '' && saleno != undefined) {
		$(".box").eq(2).find("#adddate").text(strToDate(job['adddate'], 3));
		$(".box").eq(2).find("#adduser").text(job['adduser']);
		$(".box").eq(2).find("#addcolor").css("color",job['addcolor']);
		$(".box").eq(3).find("#updcolor").css("color",job['updcolor']);
		if(job['upddate'] != job['adddate']) {
			$(".box").eq(3).find("#update").text(strToDate(job['upddate'], 3));
			$(".box").eq(3).find("#upduser").text(job['upduser']);
		} else {
			$(".box").eq(3).addClass("hidden");
		}
	} else {
		$(".box").eq(0).find("#adddate").text(strToDate(job['adddate'], 3));
		$(".box").eq(0).find("#adduser").text(job['adduser']);
		$(".box").eq(0).find("#addcolor").css("color",job['addcolor']);
		$(".box").eq(1).find("#updcolor").css("color",job['updcolor']);
		if(job['upddate'] != job['adddate']) {
			$(".box").eq(1).find("#update").text(strToDate(job['upddate'], 3));
			$(".box").eq(1).find("#upduser").text(job['upduser']);
		} else {
			$(".box").eq(1).addClass("hidden");
		}
	}

	//预计売上
	if(job['plan_sale_cure_code'] != null && job['plan_sale_cure_code'] != "" && job['plan_sale_cure_code'] != undefined) {
		$('#switch-state-off').bootstrapSwitch('disabled', false);
		$('#switch-state-off').bootstrapSwitch('state', 1);
		$('#switch-state-off').bootstrapSwitch('disabled', true);
		//显示外货部分 换算 参照先 适用日
		$('#exchange_rate1').css("display", "block");
		//默认外货货币单位

	}
	$('#plansale_foreign_code_off').val(job['plansale_foreign_code']);
	if(job['plansale_foreign_code'] != "" && job['plansale_foreign_code'] != null) {
		$("#need_change_code").text(getMoneyCode(job['plansale_foreign_code'])['val']);
	}
	/*************卖上预定小数点格式化*************/
	var point = $.getDefaultPoint();
	var retention_digit = parseInt($.getDefaultPoint());
 	var retention_digit_p = parseInt($.getDefaultPoint());
	if(job['plansale_foreign_code'] != null && job['plansale_foreign_code'] != "") {
 		retention_digit = parseInt(getMoneyCode(job['plansale_foreign_code'])['salePoint']); //売上保留小数
 	}
 	if(job['plan_cost_foreign_code'] != null && job['plan_cost_foreign_code'] != "") {
 		retention_digit_p = parseInt(getMoneyCode(job['plan_cost_foreign_code'])['salePoint']);
 	}

 	bus_price_common_plan(retention_digit, retention_digit_p);
	
	//売上金额
	$('.saleMoment-off').numberbox('setValue', job['plan_sale_foreign_amt']);
	//不可编辑
	$(".saleMoment-off").numberbox({
		disabled: 1
	});
	//换算

	if(job['plan_sale_cure_code'] == "" || job['plan_sale_cure_code'] == null) {
		$('.plan_sale_cure_code_off').val("");
	} else {
		$('.plan_sale_cure_code_off').val(Number(job['plan_sale_cure_code']));
	}
	$(".plan_sale_cure_code_off").attr("disabled", true);
	//売上试用日
	$('#common-day_off').datebox({
		disabled: 1
	})
	$('#common-day_off').datebox('setValue', job['plan_sale_use_date']==null?"":strToDate(job['plan_sale_use_date'], 1));

	//税入 税拔
	$('.sale_rate_click_off').find('.activeT').find('input').each(function(index, element) {
		if(element.value != job['plan_sale_ishave']) {
			$(element).parents('label').toggleClass('activeT');
			$(element).parents('label').siblings('label').toggleClass('activeT');
		}
	});

	$('.sale_rate_click').find('.active').find('input').each(function(index, element) {
		if(element.value != job['sale_ishave']) {
			$(element).parents('label').toggleClass('active');
			$(element).parents('label').siblings('label').toggleClass('active');
		}
	});
//	$('.sale_rate_click label.btn.btn-primary.active').siblings('label').css('display','none');
//	$('.sale_rate_click label.btn.btn-primary').removeClass('active');

	//参照先
	$('.plan_sale_refer_off').val(job['plan_sale_refer']);
	$('.plan_sale_refer_off').attr("disabled", true);
	//预计売上金额
	$('#plan_sale_foreign_amt').text(formatNumber(job['plan_sale']));
	//增值锐
	$('#plan_sale_tax').text(formatNumber(job['plan_vat_amt']));
	//请求金额
	$('#plan_req_amt').text(formatNumber(job['plan_req_amt']));
	//预计原价
	if(job['plan_cost_cure_code'] != null && job['plan_cost_cure_code'] != "" && job['plan_cost_cure_code'] != undefined) {
		$('#switch-state-job').bootstrapSwitch('disabled', false);
		$('#switch-state-job').bootstrapSwitch('state', 1);
		$('#switch-state-job').bootstrapSwitch('disabled', true);
		//显示外货部分 换算 参照先 适用日
		$('#exchange_rate2').css("display", "block");
		//默认外货货币单位
	}

	$('#plan_cost_foreign_code').val(job['plan_cost_foreign_code']);
	if(job['plan_cost_foreign_code'] != "" && job['plan_cost_foreign_code'] != null) {
		$("#cost_need_change_money_off").text(getMoneyCode(job['plan_cost_foreign_code'])['val']);
	}
	//原价金额外货
	$('#cost_foreign_amt_job').numberbox('setValue', job['plan_cost_foreign_amt']);
	//不可编辑
	$("#cost_foreign_amt_job").numberbox({
		disabled: 1
	});
	//原价换算
	if(job['plan_cost_cure_code'] == null || job['plan_cost_cure_code'] == "") {
		$('.plan_cost_cure_code').val("");
	} else {
		$('.plan_cost_cure_code').val(Number(job['plan_cost_cure_code']));
	}

	$('.plan_cost_cure_code').attr("disabled", true);
	$('#common-dayT-off').datebox({
		disabled: 1
	})
	//原价试用日
	
	$('#common-dayT-off').datebox('setValue', job['plan_cost_use_date']==null?"":strToDate(job['plan_cost_use_date'], 1));

	//税入 税拔
	$('.cost_rate_click_off').find('.activeT').find('input').each(function(index, element) {
		if(element.value != job['plan_cost_ishave']) {
			$(element).parents('label').toggleClass('activeT');
			$(element).parents('label').siblings('label').toggleClass('activeT');
		}
	});
	//$('.cost_rate_click_off label.btn.btn-primary.activeT').siblings('label').css('display','none');
	//$('.cost_rate_click_off label.btn.btn-primary').removeClass('activeT');
	//参照先
	$('.plan_cost_refer').val(job['plan_cost_refer']);
	$('.plan_cost_refer').attr("disabled", true);
	//预计原价金额
	$('#plan_cost_foreign_amt').text(job['plancostamt'] == null ? Number(0).toFixed(point) : formatNumber(job['plancostamt']));
	//增值锐
	$('#plan_cost_tax').text(job['plancost_tax'] == null ? Number(0).toFixed(point) : formatNumber(job['plancost_tax']));
	//请求金额
	$('#plan_vat_amt').text(job['plan_pay_amt'] == null ? Number(0).toFixed(point) : formatNumber(job['plan_pay_amt']));

	var card_list = job['julable'];
	var str = "";

	for(var i = 0; i < card_list.length; i++) {
     var colorStyle = "color:"+card_list[i]['usercolor'];
     var langtyp =$.getLangTyp();
		var itemname ="";
			if(langtyp == "jp"){
				itemname = card_list[i]["itemname_jp"]
			}
			else if(langtyp == "en"){
				itemname = card_list[i]["itemname_en"]
			}
			else if(langtyp == "zc"){
				itemname = card_list[i]["itmname"]
			}
			else if(langtyp == "zt"){
				itemname = card_list[i]["itemname_hk"]
			}
	    
		str +=

			'<div class="col-md-4 panel person" style="padding: 5px; margin-bottom: 0; width: 225px;background: #fff;">' +
			'<div class="person-t">';
		if(card_list[i]['level_flg'] == 1 || card_list[i]['level_flg'] == 2) {
			str += '<span class="text-center block i18n" name="ying_ye_change">'+part_language_change_new('ying_ye_change')+'</span>';
		} else {
			str += '<span class="text-center block i18n" name="job_gedang_change">'+part_language_change_new('job_gedang_change')+'</span>';
		}

		str += '</div>' +
			'<div class="person-m">' +
			'<div class="person-m-l">' +
			'<i class="iconfont icon-ren" style="'+colorStyle+'"></i>' +
			'</div>' +
			'<div class="person-m-m">' +
			'<div class="userlevel">' +
			itemname +
			'</div>' +
			'<div class="usercd">' + card_list[i]["memberid"] + '</div>' +
			'<div class="username">' +
			card_list[i]["user_name"] +
			'</div>' +
			'</div>' +
			'</div>';
		if(card_list[i]["level_flg"] == 1) {
			str += '<div class="person-b">' +
				'<label>' +
				'<input type="checkbox" id="inlineCheckbox44" class="level_flg" checked="checked" value="option4" disabled="disabled">' +
				'<span class="iconfont icon-duihaok"></span><span class="i18n" name="jobregistration_person_in_charge">'+part_language_change_new('jobregistration_person_in_charge')+'</span>' +
				'</label>' +
				'</div>' +
				'</div>' +
				'</div>';
		} else {
			str += '<div class="person-b">' +
				'<label>' +
				'<input type="checkbox" id="inlineCheckbox44" class="level_flg" value="option4" disabled="disabled">' +
				'<span class="iconfont icon-duihaok"></span><span class="i18n" name="jobregistration_person_in_charge">'+part_language_change_new('jobregistration_person_in_charge')+'</span>' +
				'</label>' +
				'</div>' +
				'</div>' +
				'</div>';
		}

	}
	$('#per').html(str);
	//taxrateJobDetail();
	//taxrate()

}

function initCal(flg){
	if($('#sale_foreign_amt').text()!=0&&$('#req_amt').text()!=0&&$('#sale_tax').text()!=0){
		 var saleMoney = recoveryNumber($('#sale_foreign_amt').text());
		 var reqAmt = recoveryNumber($('#req_amt').text());//请求金额
		 var saleVatAmt = recoveryNumber($('#sale_tax').text());//卖上增值税
	}else{
	    var saleMoney = recoveryNumber($('#plan_sale_foreign_amt').text());//卖上金额
		var reqAmt = recoveryNumber($('#plan_req_amt').text());//请求金额
		var saleVatAmt = recoveryNumber($('#plan_sale_tax').text());//卖上增值税
	}

	var costMoney;//原价金额
	var payAmt;//支付金额
	var costVatAmt;//仕入增值税
    var costActive = $('.sale_head').eq(1).find(".active").find("input").val();
    var point = saleStorage.getPersonMoneyCode()['pointNumber']; //本国小数点
	var taxFormatFlg = searchValue(saleStorage.getListTax(), "itemcd", "003", "aidcd");
	var saleVatFormatFlg = searchValue(saleStorage.getListTax(), "itemcd", "001", "aidcd");
	var foreignFormatFlg = searchValue(saleStorage.getListForeignTax(), "itemcd", "001", "aidcd");
		var sale_id = $('.sale_cd').val();
	var arrSaleRate = getSaleRate(sale_id);
	if(!arrSaleRate) {
		$(".saleOp").attr("disabled", true);
		return false;
	}
	var rate = arrSaleRate['rate2'];
	var rate1 = arrSaleRate['rate3'];
	if(costActive==1){
		costMoney = recoveryNumber($("span#cost_foreign_amt").text());
	    payAmt = recoveryNumber($("span#pay_amt").text());
	    costVatAmt = recoveryNumber($("span#cost_tax").text());
	}else{
		costMoney = recoveryNumber($("#plan_cost_foreign_amt").text());
		payAmt = recoveryNumber($("span#plan_vat_amt").text());
		costVatAmt = recoveryNumber($("#plan_cost_tax").text());
	}
	
	var rateObj = calculateTaxBySaleHandler(saleMoney,costMoney, reqAmt, payAmt, saleVatAmt,
		costVatAmt, rate, rate1, point, taxFormatFlg, foreignFormatFlg);
	//页面税金
	$('.tax_total').text(rateObj['taxTotal'] == null ? Number(0).toFixed(point) : formatNumber(rateObj['taxTotal']));
	//页面营业额
	$('.get_total').text(rateObj['profit'] == null ? Number(0).toFixed(point): formatNumber(rateObj['profit']));
	$(".sumRate").text('[' + rateObj['profitRate'] + '%]');
	//将税金1和2 存在session中 插入时获取
	sessionStorage.setItem('saleRate1', rateObj['tax']);
	sessionStorage.setItem('saleRate2', rateObj['tax1']);
	
}
//设置 实际売上值
function setRealSaleMessage(data) {
	$('.sale_no').val(data['sale_no']);

	//机上日  如果实际机上日是空 
	$('#dalday').datebox('setValue', data['dlvday'] == null ? '' : strToDate(data['dlvday'], 1));
	//请款预定日
	$('#plan_dalday').datebox('setValue', data['plan_req_date'] == null ? '' : strToDate(data['plan_req_date'], 1));
	if(data['sale_cure_code'] != null && data['sale_cure_code'] != "" && data['sale_cure_code'] != undefined) {
		$('#switch-state').bootstrapSwitch('state', 1);
	}
	//外货编号ID
	$('#plansale_foreign_code').val(data['sale_foreign_code']);
	if(data['sale_foreign_code'] != "") {
		var moneyObj = getMoneyCode(data['sale_foreign_code']);
		$('#need_change_code1').text(moneyObj["val"]);
	}

	//汇率
	if(data['sale_cure_code'] == null || data['sale_cure_code'] == "") {
		$('.plan_sale_cure_code').val("");
	} else {
		$('.plan_sale_cure_code').val(Number(data['sale_cure_code']));
	}

	//试用日
	$('#common-day').datebox('setValue', data['sale_use_date'] == null ? '' : strToDate(data['sale_use_date'], 1));
	//参照先
	$('.plan_sale_refer').val(data['sale_refer']);

	//备考
	$('.sale_remark').val(data['sale_remark']);

	//税入税拔
	//$('.sale_rate_click').find('input').val();

	//金额
	$('#pcost').numberbox('setValue', data['sale_amt']);
	var point = saleStorage.getPersonMoneyCode()['pointNumber']; //本国小数点

	//卖上登录时间 需要共同化
	$(".box").eq(0).find("#adddate").text(strToDate(data['saleadddate'], 3));
	$(".box").eq(0).find("#adduser").text(data['saleadduserName']);
	//卖上小人颜色
	$(".box").eq(0).find("#addcolor").css("color",data['saleaddcolor']);
	$(".box").eq(1).find("#updcolor").css("color",data['saleupcolor']);
	//売上更新时间
	if(data['saleupddate'] != data['saleadddate']) {
		$(".box").eq(1).find("#update").text(strToDate(data['saleupddate'], 3));
		//売上更新者
		$(".box").eq(1).find("#upduser").text(data['upname']);
	} else {
		$(".box").eq(1).addClass('hidden');
	}

	var retention_digit = parseInt(point);
	if(data['sale_foreign_code'] != null && data['sale_foreign_code'] != "") {
		retention_digit = parseInt(getMoneyCode(data['sale_foreign_code'])['salePoint']); //売上保留小数
	}

	bus_price_common(retention_digit, 3);

    $('#sale_foreign_amt').text(formatNumber(data['sale_foreign_amt']));
		$('.sale_rate_click').find('.active').find('input').each(function(index, element) {
		if(element.value != data['sale_ishave']) {
			$(element).parents('label').toggleClass('active');
			$(element).parents('label').siblings('label').toggleClass('active');
		}
	});
	//增值税
	$('#sale_tax').text(formatNumber(data['sale_vat_amt']));
	//请款金额
	$('#req_amt').text(formatNumber(data['req_amt']));
	//税金
	$('.tax_total').text(formatNumber(data['vat_amt']));
	//营业额
	
}
//获取売上登陆信息
function getSaleMessage() {
	var sale = {};
	//job-cd
	sale['job_cd'] = $('.job_cd').text();
	//终了日
	sale['dlvday'] = $('#dalday').datebox('getValue') == "" ? null : $('#dalday').datebox('getValue');
	//请款预定日
	sale['plan_req_date'] = $('#plan_dalday').datebox('getValue') == "" ? null : $('#plan_dalday').datebox('getValue');
	//外货开关
	//sale['out_button'] = $('#switch-state').bootstrapSwitch('state')?1:0;
	//外货编号ID
	sale['sale_foreign_code'] = $('#plansale_foreign_code').val();
	//金额
	sale['sale_amt'] = $('.saleMoment').val();
	if($('#switch-state').bootstrapSwitch('state')) {
		//汇率
		sale['sale_cure_code'] = $('.plan_sale_cure_code').val() == "" ? null : $('.plan_sale_cure_code').val();
		//试用日
		sale['sale_use_date'] = $('#common-day').datebox('getValue') == "" ? null : $('#common-day').datebox('getValue');
		//参照先
		sale['sale_refer'] = $('.plan_sale_refer').val();
	} else {
		//汇率
		sale['sale_cure_code'] = null;
		//试用日
		sale['sale_use_date'] = null
		//参照先
		sale['sale_refer'] = null;
	}

	//营业额
	sale['sale_foreign_amt'] = recoveryNumber($('#sale_foreign_amt').text());
	//增值税
	sale['sale_vat_amt'] = recoveryNumber($('#sale_tax').text());
	//请款金额
	sale['req_amt'] = recoveryNumber($('#req_amt').text());
	//备考
	sale['sale_remark'] = $('.sale_remark').val();
	//税金1
	sale['sale_tax1'] = sessionStorage.getItem('saleRate1');
	//税金2
	sale['sale_tax2'] = sessionStorage.getItem('saleRate2');
	//外货币种编号
	sale['sale_foreign_type'] = $('#plansale_foreign_code').val();
	//税入 税拔
	sale['sale_ishave'] = $('.sale_rate_click').find('.active').find('input').val();
	//税金合计
	sale['vat_amt'] = recoveryNumber($('.tax_total').text());
	//lable标签
	sale['jlTrn'] = getLable();
	//请求书
	sale['req'] = getReq();
	//发行数
	sale['inv'] = getInvoic();
	//请求书flg
	sale['reqFlg'] = $("#reqFlg").is(':checked') ? 1 : 0;
	//发行书flg
	sale['invFlg'] = $("#invFlg").is(':checked') ? 1 : 0;
	sale['saleVatChangeFlg'] = saleStorage.getRealSaleTaxFlg();
	//判断是否卖上 承认跳过
	//	if(saleStorage.getSkip().confirm==1){
	//	sale['confirmskipflg'] = 1;
	//	}
	return sale;
}
//获取请求书信息
function getReq() {
	var req = {
		upname: $("#requpname").text(),
		update: $("#requpdate").text(),
		reqtimes: $("#reqtimes").text()
	}
	return req;
}
//发行数信息
function getInvoic() {
	var invoice = {
		upname: $("#invupname").text(),
		update: $("#invupdate").text(),
		invoicetimes: $("#invtimes").text()
	}
	return invoice;
}

function log_sale() {
//	console.log(inputVerfy());
	if(!inputVerfy()) {
		return;
	}
	var flg = validataRequired();
	if(!flg) {
		//showErrorFunHandler("REQUIRED_ITEM_NOT_FILLED", "info", "info");
		return;
	}

	var path = "";
	//判断 saleno是否存在 存在更新  不存在插入
	if($('.sale_no').val() != "" && $('.sale_no').val() != null && $('.sale_no').val() != undefined) {
		path = $.getAjaxPath() + "saleTypeUpdate";
	} else {
		path = $.getAjaxPath() + "saleTypeInsert";
	}
	var saleMsg = getSaleMessage();
	saleMsg['saleLockFlg'] = saleStorage.getSaleLockFlg();
	saleMsg['jobLockFlg'] = saleStorage.getJobLockFlg();
	var sale = {
		saleType: saleMsg
	}

	$.ajax({
		url: path,
		data: JSON.stringify(sale),
		headers: {
			"requestID": $.getRequestID(),
			"requestName": $.getRequestNameByJcZH()
		},
		success: function(data) {
//			console.log(data);
			if(data[$.getRequestMetaName()].result == $.getRequestStatusName()) {
				$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
				if(data[$.getRequestDataName()] > 0) {
					if(data[$.getRequestDataName()] == 540) {
						var url = $.getJumpPath() + "jczh/job_registration_list.html?view=init&menu=se";
						 	showLockInfoMsgHandler('STATUS_VALIDATEPOWER_ERROR',url);
						return;
					}
					if(data[$.getRequestDataName()] == 550) {
						showErrorFunHandler('SYS_VALIDATEPOWER_ERROR', 'warning', 'warning');
						window.location.href = $.getJumpPath() + "jczh/top_registration.html";
						return;
					}
					var url = "jczh/job_registration_list.html?view=init&menu=se";
					var jobcd = $(".job_cd").text();
					var pdfFlg = $("#reqFlg").prop("checked");
					var pdfFlg1 = $("#invFlg").prop("checked");
					var str = "";
					var bol = 0; // 1.出发票pdf 不出请求出  2.出请求书 不出发票 3.都出
					if(pdfFlg1 && !pdfFlg) {
						bol = 1;
					}
					if(pdfFlg && !pdfFlg1) {
						bol = 2;
					}
					if(pdfFlg && pdfFlg1) {
						bol = 3;
					}
					switch(bol) {
						case 0:
							window.location.href = $.getJumpPath() + url;
							break;
						case 1:
							OutPutPdfHandler(jobcd, "", "", "invoiceOrderPdf", "");
							break;
						case 2:
							OutPutPdfHandler(jobcd, "", "", "billOrderPdf", "");
							break;
						case 3:
							str = "invoiceOrderPdf";
							OutPutPdfHandler(jobcd, "", "", "billOrderPdf", "", str);
							break;

					}

				} else {
					showErrorFunHandler("EXECUTE_FAIL", "ERROR", "ERROR");
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
//计算売上金额
//function calculationSale(saleIsHave) {
//
//	var saleMonet = $('.saleMoment').val();
//	//  卖上外货code 查询 本公司货币 关联共同表查到itemValue 与 aicd 
//	//  var itemcd = $('#plansale_foreign_code').val();
//	var itemcd = $('#plansale_foreign_code').val();
//	var moneyObj = getMoneyCode(itemcd);
//	var point = $.getDefaultPoint();
//	//  $('.sale_person_code').text(moneyObj["val"]);
//	if(saleMonet == "") {
//		$('.plan_sale_foreign_amt').text(Number(0).toFixed(point));
//		$('.plansale_tax').text(Number(0).toFixed(point));
//		$('.plan_req_amt').text(Number(0).toFixed(point));
//		$('.tax_total').text(Number(0).toFixed(point));
//		$('.get_total').text(Number(0).toFixed(point));
//		if(parseInt($('#sale_foreign_amt').text()) == 0) {
//			$(".sumRate").text("INF");
//		} else {
//			$(".sumRate").text('[0.00%]');
//		}
//		return false;
//	}
//	//页面上汇率（换算）
//	var saleCurCode = recoveryNumber($('.plan_sale_cure_code').val());
//	//卖上增值税率
//	var sale_id = $('.sale_cd').val();
//	var arrSaleRate = getSaleRate(sale_id);
//	if(!arrSaleRate) {
//		$(".saleOp").attr("disabled", true);
//		return false;
//	}
//	var saleVatRate = arrSaleRate['vat_rate'];
//
//	//税入 税拔
//	if(saleIsHave == undefined) {
//		saleIsHave = $('.sale_rate_click').find('.active').find('input').val()
//	}
//	//本国货币小数点
//	var point = saleStorage.getPersonMoneyCode()['pointNumber'];
//	//
//	var saleVatFormatFlg = searchValue(saleStorage.getListTax(), "itemcd", "001", "aidcd");
//	var foreignFormatFlg = searchValue(saleStorage.getListForeignTax(), "itemcd", "001", "aidcd");
//	var sale_obj = calculateSaleHandler(saleMonet, saleCurCode, moneyObj['saleCode'], saleVatRate, point, saleIsHave, foreignFormatFlg, saleVatFormatFlg);
//	$('.plan_sale_foreign_amt').text(sale_obj['saleBase'] == null ? Number(0).toFixed(point) : formatNumber(sale_obj['saleBase']));
//	$('.plansale_tax').text(sale_obj['saleVatBase'] == null ? Number(0).toFixed(point): formatNumber(sale_obj['saleVatBase']));
//	$('.plan_req_amt').text(sale_obj['reqAmtBase'] == null ? Number(0).toFixed(point) : formatNumber(sale_obj['reqAmtBase']));
//	//实际卖上增值税
//	saleStorage.realSaleTax = formatNumber(sale_obj['saleVatBase']);
//	saleStorage.realSaleTaxFlg = 0;
//	// 预计卖上金额
//	var planSale = recoveryNumber($("#plan_sale_foreign_amt").text());
//	//实际卖上金额
//	var realSale = recoveryNumber($("#sale_foreign_amt").text());
//	//预计成金额
//	var planCost = recoveryNumber($("#plan_cost_foreign_amt").text());
//	//实际成本
//	var realCost = recoveryNumber($("span#cost_foreign_amt").text());
//	//请求金额
//	var reqAmt = recoveryNumber($("span#req_amt").text());
//
//	//卖上增值税
//	var saleVatAmt = recoveryNumber($("span#sale_tax").text());
//	var costFinishFlg = saleStorage.getCostFinishFlg();
//	var costNum = saleStorage.getCostNum();
//	//原价完了
//	if(costFinishFlg == 1) {
//		//没有实际成本 0
//		if(costNum == 0) {
//			var payAmt = 0;
//			var costVatAmt = 0
//		} else {
//			//有实际成本 用实际
//			var payAmt = recoveryNumber($("span#pay_amt").text());
//			var costVatAmt = recoveryNumber($("span#cost_tax").text());
//
//		}
//
//	} else {
//		//原价未完了  没有实际成本 用预计
//		if(costNum == 0) {
//			var payAmt = recoveryNumber($("span#plan_vat_amt").text());
//			var costVatAmt = recoveryNumber($("span#plan_cost_tax").text());
//		} else {
//			//有实际成本用实际
//			var payAmt = recoveryNumber($("span#pay_amt").text());
//			var costVatAmt = recoveryNumber($("span#cost_tax").text());
//		}
//	}
//
//	var rate = arrSaleRate['rate2'];
//	var rate1 = arrSaleRate['rate3'];
//	var taxFormatFlg = searchValue(saleStorage.getListTax(), "itemcd", "003", "aidcd");
//	var foreignFormatFlg = searchValue(saleStorage.getListForeignTax(), "itemcd", "001", "aidcd");
//


//	var rateObj = calculateTaxHandler(planSale, realSale, planCost, realCost, reqAmt, payAmt, saleVatAmt,
//		costVatAmt, rate, rate1, costFinishFlg, costNum, point, taxFormatFlg, foreignFormatFlg);


//	//页面税金
//	$('.tax_total').text(rateObj['taxTotal'] == null ? Number(0).toFixed(point) : formatNumber(rateObj['taxTotal']));
//	//页面营业额
//	$('.get_total').text(rateObj['profit'] == null ? Number(0).toFixed(point): formatNumber(rateObj['profit']));
//	$(".sumRate").text('[' + rateObj['profitRate'] + '%]');
//	//将税金1和2 存在session中 插入时获取
//	sessionStorage.setItem('saleRate1', rateObj['tax']);
//	sessionStorage.setItem('saleRate2', rateObj['tax1']);
//	return true;
//}

function calculationSale(saleIsHave){
	var saleMoney;//卖上金额
	var costMoney;//原价金额
	var reqAmt;//请求金额
	var saleVatAmt;//卖上增值税
	var payAmt;//支付金额
	var costVatAmt;//仕入增值税
	var saleActive = $('.sale_head').eq(0).find(".active").find("input").val();
	var costActive = $('.sale_head').eq(1).find(".active").find("input").val();
	var point = $.getDefaultPoint();
	var taxFormatFlg = searchValue(saleStorage.getListTax(), "itemcd", "003", "aidcd");
	var saleVatFormatFlg = searchValue(saleStorage.getListTax(), "itemcd", "001", "aidcd");
	var foreignFormatFlg = searchValue(saleStorage.getListForeignTax(), "itemcd", "001", "aidcd");
	var sale_id = $('.sale_cd').val();
	var arrSaleRate = getSaleRate(sale_id);
	if(!arrSaleRate) {
		$(".saleOp").attr("disabled", true);
		return false;
	}
	var rate = arrSaleRate['rate2'];
	var rate1 = arrSaleRate['rate3'];
	//画面显示实际卖上
  if(saleActive==1){
  	 var saleMonet = $('.saleMoment').val();
	//  卖上外货code 查询 本公司货币 关联共同表查到itemValue 与 aicd 
	//  var itemcd = $('#plansale_foreign_code').val();
	 var itemcd = $('#plansale_foreign_code').val();
	 var moneyObj = getMoneyCode(itemcd);
	//  $('.sale_person_code').text(moneyObj["val"]);
	if(saleMonet == "") {
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
		return false;
	}
	//页面上汇率（换算）
	 var saleCurCode = recoveryNumber($('.plan_sale_cure_code').val());
	//卖上增值税率
	 var saleVatRate = arrSaleRate['vat_rate'];
	//税入 税拔
	if(saleIsHave == undefined) {
		saleIsHave = $('.sale_rate_click').find('.active').find('input').val()
	}
	//
	var sale_obj = calculateSaleHandler(saleMonet, saleCurCode, moneyObj['saleCode'], saleVatRate, point, saleIsHave, foreignFormatFlg, saleVatFormatFlg);
	if(sale_obj['saleBase']==$('.plan_sale_foreign_amt').text()){
		$('.plan_sale_foreign_amt').text(sale_obj['saleBase'] == null ? Number(0).toFixed(point) : formatNumber(sale_obj['saleBase']));
	}
	if(sale_obj['saleVatBase']==$('.plansale_tax').text()){
		$('.plansale_tax').text(sale_obj['saleVatBase'] == null ? Number(0).toFixed(point): formatNumber(sale_obj['saleVatBase']));
	}
	if(sale_obj['reqAmtBase']==$('.plan_req_amt').text()){
		$('.plan_req_amt').text(sale_obj['reqAmtBase'] == null ? Number(0).toFixed(point) : formatNumber(sale_obj['reqAmtBase']));
	}
	//实际卖上增值税
	saleStorage.realSaleTax = formatNumber(sale_obj['saleVatBase']);
	saleStorage.realSaleTaxFlg = 0;
	//实际卖上金额
	 saleMoney = recoveryNumber($("#sale_foreign_amt").text());
	//请求金额
	 reqAmt = recoveryNumber($("span#req_amt").text());
	//卖上增值税
	 saleVatAmt = recoveryNumber($("span#sale_tax").text());
	if(costActive==1){
		costMoney = recoveryNumber($("span#cost_foreign_amt").text());
		payAmt = recoveryNumber($("span#pay_amt").text());
		costVatAmt = recoveryNumber($("span#cost_tax").text());
	}else{
		costMoney = recoveryNumber($("#plan_cost_foreign_amt").text());
		payAmt = recoveryNumber($("span#plan_vat_amt").text());
		costVatAmt = recoveryNumber($("#plan_cost_tax").text());
	}
	//预计卖上
	}else{
		saleMoney =recoveryNumber($(".plan_sale_foreign_amt_off").text());
		reqAmt = recoveryNumber($(".plan_req_amt_off").text());
		saleVatAmt = recoveryNumber($(".plansale_tax_off").text());
		
		if(costActive==1){
			costMoney = recoveryNumber($("span#cost_foreign_amt").text());
			payAmt = recoveryNumber($("span#pay_amt").text());
			costVatAmt = recoveryNumber($("span#cost_tax").text());
		}else{
			costMoney = recoveryNumber($("#plan_cost_foreign_amt").text());
			payAmt = recoveryNumber($("span#plan_vat_amt").text());
			costVatAmt = recoveryNumber($("#plan_cost_tax").text());
		}
	}

	var rateObj = calculateTaxBySaleHandler(saleMoney,costMoney, reqAmt, payAmt, saleVatAmt,
		costVatAmt, rate, rate1, point, taxFormatFlg, foreignFormatFlg);
	//页面税金
	$('.tax_total').text(rateObj['taxTotal'] == null ? Number(0).toFixed(point) : formatNumber(rateObj['taxTotal']));
	//页面营业额
	$('.get_total').text(rateObj['profit'] == null ? Number(0).toFixed(point): formatNumber(rateObj['profit']));
	$(".sumRate").text('[' + rateObj['profitRate'] + '%]');
	//将税金1和2 存在session中 插入时获取
	sessionStorage.setItem('saleRate1', rateObj['tax']);
	sessionStorage.setItem('saleRate2', rateObj['tax1']);
	return true;
}

function bindAction() {
	$('#sale_update').click(function() {
		log_sale();
	});
	$('#sale_del').click(function() {
		var msg = showConfirmMsgHandler("SURE_CANCEL");
		var confirmTitle = $.getConfirmMsgTitle();
		$.messager.confirm(confirmTitle, msg, function(r) {
			if(r) {
				delSale();
			} else {
				return;
			}
		});
	});
	$('.log_sale').click(function() {
		log_sale();
	});
	$(".add_lable").click(function() {

		addLable("new_lable", "options_lable", "0");

	});
	//	$('#switch-state').on('switchChange.bootstrapSwitch', function(event, state) {
	//		calculationSale();
	//	});
	$(".sale_rate_click").click(function(e) {
		var value = $(e.target).closest('label').find('input').val();
		var textval = $('.sale_rate_click').find('.active').find('input').val();
		if(value == textval) {
			return;
		}
		calculationSale(value);
	});
	$('.cal-sale').change(function() {
		calculationSale();
	});
	$('#plansale_foreign_code').change(function() {
		var itemcd = $('#plansale_foreign_code').val();
		var moneyObj = getMoneyCode(itemcd);
		$('#need_change_code1').text(moneyObj["val"]);
		calculationSale();

		var retention_digit = parseInt($.getDefaultPoint());
		if(moneyObj['salePoint'] != null && moneyObj['salePoint'] != "") {
			retention_digit = parseInt(moneyObj['salePoint']); //売上保留小数
		}
		bus_price_common(retention_digit, 3);
	});
	$('input.cal-sale-box').numberbox({
		"onChange": function() {
			calculationSale();
		}
	});
	$(".filter-lable").click(function() {
		var str = $("#lableStr").val();
		filterLable(str);
	});
	$(".plan_sale_cure_code").keyup(function() {
		var flag = number_fl_ck(this.value, 10, 5);
		if(!flag) {
			this.value = number_va_length(this.value, 10, 5);
			return;
		}
	});
	$("#set-tax").click(function(e) {
		var haveVatFlg = $('.sale_rate_click').find('.active').find('input').val();
		var saleCostAmt = recoveryNumber($('#sale_foreign_amt').text());
		var reqPayAmt = recoveryNumber($('#req_amt').text());
		var vatAmt = recoveryNumber($('.edit-tax').val());
		if(vatAmt == "") {
			$(".sale-border").setBorderRed();
			validate($(".sale-border"), part_language_change_new('NODATE'));
			//$(".sale-border").find("span.numberbox")[0].scrollIntoView(true);
			//$(".sale-border").find("span.numberbox")[0].scrollIntoView(false);
			$('#content').scrollTop(0);
			if(!$('.panel.window.panel-htop.messager-window').exist()){
	 			showErrorFunHandler("NOT_EMPTY", "ERROR", "ERROR");
	 		}
			return;
		} else {
			$(".sale-border").setBorderBlack();
			$(".sale-border").tooltip('destroy');
		}
		var obj = calculateMoneyByVatChangeHandler(haveVatFlg, saleCostAmt, reqPayAmt, vatAmt);
		$('#sale_foreign_amt').text(formatNumber(obj["saleCostAmt"]));
		$('#sale_tax').text(formatNumber(obj["vatAmt"]));
		$('#req_amt').text(formatNumber(obj["reqPayAmt"]));
		upRateArea();
		var baseRealSaleTax = saleStorage.getRealSaleTax();
		if(vatAmt != baseRealSaleTax) {
			saleStorage.realSaleTaxFlg = 1;
		}
		var index = layer.index;
		layer.close(index);
	});

	$('#switch-state').on('switchChange.bootstrapSwitch', function(event, state) {
		//calculationSale();
		//外货开关变得时候  需要把 外货下拉变为空  金额单位变为本国货币 汇率 什么的都清空 计算
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

		var itemcd = $('#plansale_foreign_code').val();
		var moneyObj = getMoneyCode(itemcd);
		$('#need_change_code1').text(moneyObj["val"]);
		calculationSale();
		var retention_digit = parseInt($.getDefaultPoint());
		if(moneyObj['salePoint'] != null && moneyObj['salePoint'] != "") {
			retention_digit = parseInt(moneyObj['salePoint']); //売上保留小数
		}
		bus_price_common(retention_digit, 3);
	});
	

	//请求预定日逻辑	 

	$('#dalday').datebox({
		onChange: function(date) {
			autoDate(date);
			//获取税率
			calculationSale();
		}
	});
	
}

function isLeapYear(year) {
	return(year % 4 == 0) && (year % 100 != 0 || year % 400 == 0);
}

function autoDate(date) {
	var new_date;
	var company = saleStorage.getCompanyMessage();
	if(company.registrationautomatic == 1) {
		var addMon = company.automonth;
		var addDate = company.autoday;
		var date = new Date(date);
		var year = date.getFullYear();
		var mon = date.getMonth() + 1;
		var month = mon + parseInt(addMon);
		if(month > 12) {
			year++;
			month -= 12;
		}
		if("1,3,5,7,8,10,12".indexOf(month) > -1) {
			if(addDate > 31) {
				new_date = year + "-" + month + "-" + getLastDay(year, month);
			} else {
				new_date = year + "-" + month + "-" + addDate;
			}
		}
		if("4,6,9,11".indexOf(month) > -1) {
			if(addDate > 30) {
				new_date = year + "-" + month + "-" + getLastDay(year, month);
			} else {
				new_date = year + "-" + month + "-" + addDate;
			}
		}
		if(month == "2") {
			if(isLeapYear(year)) {
				if(addDate > 29) {
					new_date = year + "-" + month + "-" + getLastDay(year, month);
				} else {
					new_date = year + "-" + month + "-" + addDate;
				}
			} else {
				if(addDate > 28) {
					new_date = year + "-" + month + "-" + getLastDay(year, month);
				} else {
					new_date = year + "-" + month + "-" + addDate;
				}
			}
		}

		$('#plan_dalday').datebox('setValue', new_date);
	} else {
		return;
	}
}

function getLastDay(year, month) {
	var new_year = year; //取当前的年份          
	var new_month = month++; //取下一个月的第一天，方便计算（最后一天不固定）          
	if(month > 12) {
		new_month -= 12; //月份减          
		new_year++; //年份增          
	}
	var new_date = new Date(new_year, new_month, 1); //取当年当月中的第一天          
	return(new Date(new_date.getTime() - 1000 * 60 * 60 * 24)).getDate(); //获取当月最后一天日期          
}


//增值税变化 重新计算税金和营业额
function upRateArea(first) {
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
	var sale_id = $(".sale_cd").val();
	var arrSaleRate = getSaleRate(sale_id);
	if(!arrSaleRate) {
		$(".saleOp").attr("disabled", true);
		return;
	}
	// 预计卖上金额
	var planSale = recoveryNumber($("#plan_sale_foreign_amt").text());
	//实际卖上金额
	var realSale = recoveryNumber($("#sale_foreign_amt").text());
	//预计成金额
	var planCost = recoveryNumber($("#plan_cost_foreign_amt").text());
	//实际成本
	var realCost = recoveryNumber($("span#cost_foreign_amt").text());
	//请求金额
	var reqAmt = recoveryNumber($("span#req_amt").text());
	//卖上增值税
	var saleVatAmt = recoveryNumber($("span#sale_tax").text());
	if(first=="Turnover"){
		//请求金额
		var reqAmt = recoveryNumber($("span#req_amt").text());
		//卖上增值税
		var saleVatAmt = recoveryNumber($("span#sale_tax").text());
	} else if(first=="Estimate"){
		var reqAmt = recoveryNumber($("span#plan_req_amt").text());
		var saleVatAmt = recoveryNumber($("span#plan_sale_tax").text());
	}
	var costFinishFlg = saleStorage.getCostFinishFlg();
	var costNum = saleStorage.getCostNum();
	var rate = arrSaleRate['rate2'];
	var rate1 = arrSaleRate['rate3'];
	var point = saleStorage.getPersonMoneyCode()['pointNumber'];
	var taxFormatFlg = searchValue(saleStorage.getListTax(), "itemcd", "003", "aidcd");
	var foreignFormatFlg = searchValue(saleStorage.getListForeignTax(), "itemcd", "001", "aidcd");
	var salemoney = recoveryNumber($("span#req_amt").text());
	var costmoney = recoveryNumber($("span#plan_req_amt").text());
	//原价完了
	if(costFinishFlg == 1) {
		//没有实际成本 0
		if(costNum == 0) {
			var payAmt = 0;
			var costVatAmt = 0
		} else {
			//有实际成本 用实际
			var payAmt = recoveryNumber($("span#pay_amt").text());
			var costVatAmt = recoveryNumber($("span#cost_tax").text());

		}

	} else {
		//原价未完了  没有实际成本 用预计
		if(costNum == 0) {
			var payAmt = recoveryNumber($("span#plan_vat_amt").text());
			var costVatAmt = recoveryNumber($("span#plan_cost_tax").text());
		} else {
			//有实际成本用实际
			var payAmt = recoveryNumber($("span#pay_amt").text());
			var costVatAmt = recoveryNumber($("span#cost_tax").text());
		}
		
		if(first=="costEstimate"){
			
			var payAmt = recoveryNumber($("span#plan_vat_amt").text());
			var costVatAmt = recoveryNumber($("span#plan_cost_tax").text());
		}
		
	}

	var taxObj = calculateTaxHandler(planSale, realSale, planCost, realCost, reqAmt, payAmt, saleVatAmt,
		costVatAmt, rate, rate1, costFinishFlg, costNum, point, taxFormatFlg, foreignFormatFlg);
	//重置sesssionStorage 中的税金1和税金2

	sessionStorage.setItem('sumRate1', taxObj['tax']);
	sessionStorage.setItem('sumRate2', taxObj['tax1']);
	$("span.tax_total").text(formatNumber(taxObj['taxTotal']));
	$('span.get_total').text(formatNumber(taxObj['profit']));
	var rateObj = calculateTaxBySaleHandler(realSale,planCost, reqAmt, payAmt, saleVatAmt,
			costVatAmt, rate, rate1, point, taxFormatFlg, foreignFormatFlg);
	//页面税金
	$('.tax_total').text(rateObj['taxTotal'] == null ? Number(0).toFixed(point) : formatNumber(rateObj['taxTotal']));
	//页面营业额
	$('.get_total').text(rateObj['profit'] == null ? Number(0).toFixed(point): formatNumber(rateObj['profit']));
	//如果 卖上金额为0 营业收益率 显示为INF
	if(parseInt($('#sale_foreign_amt').text()) == 0) {
		$(".sumRate").text("[INF%]");
	} else {
		$(".sumRate").text('[' + taxObj['profitRate'] + '%]');
	}
	

}
//卖上更新 将预定值设置我实际值
function planMoveSet(){
	//所在页面
	var saleActive = $('.sale_head').eq(0).find(".active").find("input").val();
    //	外货code
    var moneyCode =  $('#plansale_foreign_code_off').val();
	//税入税拔
	var rateInOut = $('.sale_rate_click_off').find('.activeT').find('input').val();
	//输入金额
	var inputMoney = $('.saleMoment-off').numberbox('getValue');
	//预定换算code
	var cureCode = $('.plan_sale_cure_code_off').val();
	//适用日
	var commonDay = $('#common-day_off').datebox('getValue');
	//参照先
	var refer = $('.plan_sale_refer_off').val();
	var retention_digit = parseInt($.getDefaultPoint());
	
	
	
	$('#pcost').numberbox('setValue',inputMoney);
	
	
	if(cureCode != null && cureCode!= "" && cureCode != undefined) {
		$('#switch-state').bootstrapSwitch('state', 1);
		//显示外货部分 换算 参照先 适用日
		$('#exchange_rate').css("display", "block");
		//默认外货货币单位
        $('.plan_sale_cure_code').val(cureCode);
        $('#plansale_foreign_code').val(moneyCode);
        $('#common-day').datebox('setValue',commonDay==null?"":commonDay);
        $('.plan_sale_refer').val(refer);
	}else{
		$('#switch-state').bootstrapSwitch('state', 0);
		$('#plansale_foreign_code').val("");
		$('#common-day').datebox('setValue',null);
		$('.plan_sale_refer').val("");
	}
	
	
	if(moneyCode!= "" && moneyCode != null) {
		var moneyObj = getMoneyCode(moneyCode);
		$("#need_change_code1").text(moneyObj['val']);
		retention_digit = parseInt(moneyObj['salePoint']); 
	}
	bus_price_common(retention_digit, 3);
	
	
	
	$('.sale_rate_click').find('.active').find('input').each(function(index, element) {
		if(element.value != rateInOut) {
			$(element).parents('label').toggleClass('active');
			$(element).parents('label').siblings('label').toggleClass('active');
		}
	});
   $(".model_first").click();
}
saleStorage = {
	//売上税金
	saleVatRate: null,
	//外货货币种类
	out_list: null,
	//加载时默认的货币种类
	personMoneyCode: null,
	//skip
	skip: null,
	listTax: null,
	listForeignTax: null,
	costFinishFlg: 0,
	costNum: 0,
	companyMessage: null,
	realSaleTax: 0,
	realSaleTaxFlg: 0,
	SaleNo: null,
	saleLockFlg:0,
	jobLockFlg:0,
	getRealSaleTax: function() {
		return this.realSaleTax;
	},
	getRealSaleTaxFlg: function() {
		return this.realSaleTaxFlg;
	},
	getOut_List: function() {
		return this.out_list;
	},
	getPersonMoneyCode: function() {
		return this.personMoneyCode;
	},
	getSaleVatRate: function() {
		return this.saleVatRate;
	},
	getSkip: function() {
		return this.skip;
	},
	getListTax: function() {
		return this.listTax;
	},
	getListForeignTax: function() {
		return this.listForeignTax;
	},
	getCostFinishFlg: function() {
		return this.costFinishFlg;
	},
	getCostNum: function() {
		return this.costNum;
	},
	getCompanyMessage: function() {
		return this.companyMessage;
	},
	getSaleNo: function() {
		return this.SaleNo;
	},
	getSaleLockFlg:function(){
		return this.saleLockFlg;
	},
	getJobLockFlg:function(){
		return this.jobLockFlg;
	}
}