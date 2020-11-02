var init = function() {
	$('.nav-tabs>li').on('click', function() {
		var name = $(this).attr('role');
		$(this).addClass('active');
		$(this).siblings().removeClass('active');
		$(this).parent().parent().find('.' + name).removeClass('tab-pane');
		$(this).parent().parent().find('.' + name).siblings().addClass('tab-pane');
	});
	var st = $('#switch-state').bootstrapSwitch('state');
	if(st){
		    $(".auto_month").attr("disabled",false);
			$(".auto_day").attr("disabled",false);
	}else{
			$(".auto_month").attr("disabled",true);
			$(".auto_day").attr("disabled",true);
	}
	$('input[name="number_rules"]').click(function(){
		if(this.value==1){
			$("#start_month").attr("disabled",true);
		}else{
			$("#start_month").attr("disabled",false);
		}
	});
	$('#switch-state').on('switchChange.bootstrapSwitch', function(event, state) {
		if(state==1){
			$(".auto_month").attr("disabled",false);
			$(".auto_day").attr("disabled",false);
		}else{
			$(".auto_month").val(1);
			$(".auto_day").val(31);
			$(".auto_month").attr("disabled",true);
			$(".auto_day").attr("disabled",true);
		}
	});
}
//获取页面加载下拉
function madeGet() {
var path = $.getAjaxPath() + "companyMstMade";
//var companycd = urlPars.parm("companycd");
var company = {
//		//company_cd: sessionStorage.getItem("company_cd")
//		company_cd: companycd
	}
	$.ajax({
		url: path,
		data:JSON.stringify(company),
		headers: {
			"requestID": $.getRequestID(),
			"requestName": $.getRequestNameByMst()
		},
		success: function(data) {
//			console.log(data[$.getRequestDataName()]);
			if(data[$.getRequestMetaName()].result == $.getRequestStatusName()) {
				$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
				if(data[$.getRequestDataName()]!=null) {
					//将数据保存在commonjs的SelectObj对象中 切换语言下拉用 
					SelectObj.setStringFlg = "_";
					SelectObj.selectData = data[$.getRequestDataName()];
					SelectObj.setSelectID = ['cl_list','tz_list','cc_list'];
					//下拉赋值
					SelectObj.setSelectOfLog();
                     var ccd = urlPars.parm("company_cd");
				    cpStorage.timeZone = data[$.getRequestDataName()]['tz_list'];
					//如果 session中有 upload 的 值
//					 if(add!= "1") {
//						if(upload_flg == "1" || ccd != "") {
//							getUpdateCompany(parseInt(ccd));
//						} else {
//							showErrorHandler("EXECUTE_FAIL", "ERROR", "ERROR");
//							window.location.href = $.getJumpPath() + 'master/ltd_list.html';
//						}
//					}
//				} else {
//					showErrorHandler("NO_FOUND_SELECT_MESSAGE", "ERROR", "ERROR");
//				}
                if(ccd!=undefined){
                	if(!/^[0-9]*$/.test(ccd)){
                		var url = "master/ltd_list.html";
				        showLockInfoMsgHandler('DATA_IS_NOT_EXIST',url);
                	}else{
                		getUpdateCompany(ccd,data);
                	}
                }
			} else {
				showErrorFunHandler(data[$.getRequestMetaName()].result, "ERROR", "ERROR");
			}
		}
		},
		error: function(data) {
			showErrorHandler("CONNECTION_SERVER_ERROR", "ERROR", "ERROR");
		}
	});
}
//登陆一个公司
function insertCompany(company) {
	var path = $.getAjaxPath() + "companyMstInsert";
	$.ajax({
		url: path,
		data: JSON.stringify(company),
		headers: {
			"requestID": $.getRequestID(),
			"requestName": $.getRequestNameByMst()
		},
		success: function(data) {
			if(data[$.getRequestMetaName()].result == $.getRequestStatusName()) {
				$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
				if(data[$.getRequestDataName()] != 0) {
					//clearSession();
					var url = "master/ltd_list.html";
					var str = data[$.getRequestDataName()];
					//返回的 密码与公司用户是按照 ： 分割
					var rArr = str.split(":");
					var mes = part_language_change_new("COMPANY_ADD_MESSAEGE_PART1")+part_language_change_new("COMPANY_ADD_MESSAEGE_Login")+rArr[0]+
					part_language_change_new("COMPANY_ADD_MESSAEGE_PART2")+rArr[1]+
					part_language_change_new("COMPANY_ADD_MESSAEGE_PART3");
					showInfoMsgHandler("EXCUTE_SUCCESS_COMPANYADD","COMPANY_SUCCESS_INFO","COMPANY_SUCCESS_INFO",url,mes);
					//window.location.href  = $.getJumpPath()+url;
				} else {
					showErrorHandler("EXECUTE_FAIL", "ERROR", "ERROR");
				}
			} else {
				//clearSession();
				showErrorFunHandler(data[$.getRequestMetaName()].result, "ERROR", "ERROR");
			}
		},
		error: function(data) {
			//clearSession();
			showErrorHandler("CONNECTION_SERVER_ERROR", "ERROR", "ERROR");
		}
	});
}
//修改公司
function updateCompany(company) {
	var path = $.getAjaxPath() + "companyMstUpd";
	$.ajax({
		url: path,
		data: JSON.stringify(company),
		headers: {
			"requestID": $.getRequestID(),
			"requestName": $.getRequestNameByMst()
		},
		success: function(data) {
			if(data[$.getRequestMetaName()].result == $.getRequestStatusName()) {
				$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
				if(data[$.getRequestDataName()] > 0) {
				var url = "master/ltd_list.html";
				if(data[$.getRequestDataName()] == 540) {
					
					showErrorHandler("SAME_MONTH_LOGGED_INVOICE", "warning", "warning");
//					showLockInfoMsgHandler('SAME_MONTH_LOGGED_INVOICE',url);
 					//showLockInfoMsgHandler('STATUS_VALIDATEPOWER_ERROR',url);
 						return;
 					}
					//clearSession();
					
				window.location.href  = $.getJumpPath()+url;
				} else {
					showErrorHandler("EXECUTE_FAIL", "ERROR", "ERROR");
				}
			} else {
				
				if("VALIDATE_FORMAT_ERROR"!=data[$.getRequestMetaName()].result){
					//clearSession();
				}
				showErrorFunHandler(data[$.getRequestMetaName()].result, "ERROR", "ERROR");
			}
		},
		error: function(data) {
			//clearSession();
			showErrorHandler("CONNECTION_SERVER_ERROR", "ERROR", "ERROR");
		}
	});
}
//根据company_cd 获取值
function getUpdateCompany(company_cd,userData) {
	var company = {
		company_cd: company_cd
	}
	
	$.ajax({
		url: $.getAjaxPath() + "companyMstGetOne",
		data: JSON.stringify(company),
		headers: {
			"requestID": $.getRequestID(),
			"requestName": $.getRequestNameByMst()
		},
		success: function(data) {
			if(data[$.getRequestMetaName()].result == $.getRequestStatusName()) {
				$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
				if(data[$.getRequestDataName()] != null) {
					setUpdateValue(data[$.getRequestDataName()]);
					SelectObj.setDefaultData = data[$.getRequestDataName()];
					cpStorage.companyLockFlg = data[$.getRequestDataName()].companyLockFlg;
					validateCostBook(data[$.getRequestDataName()]);
					powerLimit(data[$.getRequestDataName()],userData);
				} else {
					//showErrorHandler("PAGE_INIT_FAIL", "ERROR", "ERROR");
					var url = "master/ltd_list.html";
				    showLockInfoMsgHandler('DATA_IS_NOT_EXIST',url);
				}
			} else {
				showErrorFunHandler(data[$.getRequestMetaName()].result, "ERROR", "ERROR");
			}
		},
		error: function(data) {
			showErrorHandler("CONNECTION_SERVER_ERROR", "ERROR", "ERROR");
		}
	})
}
function validateCostBook(data){
	var cN = data['order_company_name']==null?"":data['order_company_name'];
	var oL1 = data['order_location']==null?"":data['order_location'];
	var oL2 = data['order_location1']==null?"":data['order_location1'];
	var oL3 = data['order_location2']==null?"":data['order_location2'];
	var oL4 = data['telnumber']==null?"":data['telnumber'];
	
	var cNen = data['order_company_name_en']==null?"":data['order_company_name_en'];
	var oL1en = data['order_location_en']==null?"":data['order_location_en'];
	var oL2en = data['order_location1_en']==null?"":data['order_location1_en'];
	var oL3en = data['order_location2_en']==null?"":data['order_location2_en'];
	var oL4en = data['telnumberen']==null?"":data['telnumberen'];
	
	var cNhk =data['order_company_name_hk']==null?"":data['order_company_name_hk'];
	var oL1hk = data['order_location_hk']==null?"":data['order_location_hk'];
	var oL2hk = data['order_location1_hk']==null?"":data['order_location1_hk'];
	var oL3hk = data['order_location2_hk']==null?"":data['order_location2_hk'];
	var oL4hk = data['telnumberhk']==null?"":data['telnumberhk'];
	
	var cNjp = data['order_company_name_jp']==null?"":data['order_company_name_jp'];
	var oL1jp = data['order_location_jp']==null?"":data['order_location_jp'];
	var oL2jp = data['order_location1_jp']==null?"":data['order_location1_jp'];
	var oL3jp = data['order_location2_jp']==null?"":data['order_location2_jp'];
	var oL4jp = data['telnumberjp']==null?"":data['telnumberjp'];
	
	if(cN!=""||oL1!=""||oL2!=""||oL3!=""||oL4!=""||
	   cNen!=""||oL1en!=""||oL2en!=""||oL3en!=""||oL4en!=""||
	   cNhk!=""||oL1hk!=""||oL2hk!=""||oL3hk!=""||oL4hk!=""||
	   cNjp!=""||oL1jp!=""||oL2jp!=""||oL3jp!=""||oL4jp!="")
	{
		$(".tip_show").click();
	}
}
function powerLimit(data,userData){
		var powerList = JSON.parse($.getNodeList());
		var companyType = data.company_type;
		var basePower = isHavePower(powerList, [88]);//基本情报权限
		var usercompanycd = userData['userInfo']['companyID'];
		var reportAllPower = isHavePower(powerList, [89]);//账票出力表记全会社权限
		var reportGroupPower = isHavePower(powerList, [90]);//账票出力表记group权限
		var reportPersonPower = isHavePower(powerList, [91]);//账票出力表记个人权限
		
	
		//没有基本情报权限 加遮罩
		if(!basePower){
			$(".maskOne").removeClass("dn");
			$(".maskThree").removeClass("dn");
		}
		var web_comany_cd = urlPars.parm("company_cd");
		
		//有
//		switch(companyType){
//			case 2:
//				if(!reportAllPower&&!reportGroupPower&&!reportPersonPower){
//				  $(".maskTwo").removeClass("dn");
//			    }
//			  break;
//			case 1:
//			    if(!reportGroupPower&&!reportPersonPower){
//				  $(".maskTwo").removeClass("dn");
//			    }
//			  break;
//			case 0:
//			    if(!reportPersonPower){
//				  $(".maskTwo").removeClass("dn");
//			    }
//			  break;
//		}
		
		if(!reportAllPower&&!reportGroupPower&&!reportPersonPower){
				  $(".maskTwo").removeClass("dn");
				  $(".maskFour").removeClass("dn");
			    }
		if(!reportAllPower&&reportGroupPower){
			if(companyType==1){
				
			}else{
				$(".maskTwo").removeClass("dn");
				$(".maskFour").removeClass("dn");
			}
		}
		if(!reportAllPower&&!reportGroupPower&&reportPersonPower){
			if(web_comany_cd == usercompanycd){
				
			}else{
				$(".maskTwo").removeClass("dn");
				$(".maskFour").removeClass("dn");	
			}
				
			
			
		}
		
}
//将选中的一条数据 赋值
function setUpdateValue(data) {
	var company = [data];
	$.setInputVal(company);
	$(".common_language").val(data.common_language);
	$(".time_zone").val(data.time_zone);
	$(".currency_code").val(data.currency_code);
	$(".start_month").val(data.start_month);
	$(".auto_month").val(data.auto_month);
	$(".auto_day").val(data.auto_day);
	$('#switch-state').bootstrapSwitch('state', data.registration_automatic);
	$("#cc_list").attr("disabled",true);
	$("#tz_list").attr("disabled",true);
	$("#cl_list").attr("disabled",true);
	$("input[type=radio][name='company_type']").attr("disabled",true);
	$("input[type=radio][name='number_rules']").attr("disabled",true);
	$("#start_month").attr("disabled",true);
	if(data.jobInvoiceFlg == 1)
	{  
		//$("#switch-state-must").bootstrapSwitch('state',true);
		$("#switch-state-must[value='1']").attr('checked','true');
	}
	if(data.orderInvoiceInFlg == 1)
	{
		$("#switch-state-must2[value='1']").attr('checked','true');
	}
		cpStorage.jobInvoiceFlg = data.jobInvoiceFlg;
//	$('.fs18').attr('name','ltd_registration_title_ch'); 
//	$('.submitCompany').attr('name','salescategoryList_change'); 
}
//清空sessionStorage
function clearSession(){
	sessionStorage.setItem('upload', '');
	sessionStorage.setItem('add', '');
	sessionStorage.setItem('company_cd', '');
}
function toolTipLanguage(language){
	if(language == undefined){
		language = localStorage.getItem('language');
	}
	var toolTipO = part_language_change_new('ITDREGI_INFO'),
	toolTipT= part_language_change_new('ITDREGI_INFO_T');
	
	$('.toolTipO').tooltip({
	    position: 'right',
	    content: '<span style=\"color:#fff\">'+toolTipO+'</span>',
	    onShow: function(){
			$(this).tooltip('tip').css({
				backgroundColor: '#666',
				borderColor: '#666'
			});
	    }
	});
	$('.toolTipT').tooltip({
	    position: 'right',
	    content: '<span style=\"color:#fff\">'+toolTipT+'</span>',
	    onShow: function(){
			$(this).tooltip('tip').css({
				backgroundColor: '#666',
				borderColor: '#666'
			});
	    }
	});
}
$(function() {
	init();
	isCheckLogin(1);
	madeGet();
	//初始化toolTip语言切换不需要传参数
	toolTipLanguage();
	$('.submitCompany').click(function() {
		var obj = $.getInputVal();
		obj['common_language'] = $(".common_language").val();
		obj['time_zone'] = $(".time_zone").val();
		obj['currency_code'] = $(".currency_code").val();
		obj['start_month'] = $(".start_month").val();
		obj['auto_month'] = $(".auto_month").val();
		obj['auto_day'] = $(".auto_day").val();
		obj['auto_day'] = $(".auto_day").val();
		obj['del_flg'] = $("input[type='checkbox']").eq(1).prop('checked') ? 1 : 0;
		obj['registration_automatic'] = $('#switch-state').bootstrapSwitch('state') ? 1 : 0;
		obj['timenum'] = searchValue(cpStorage.getTimeZone(),"itemcd",$(".time_zone").val(),"itmvalue");
		obj['langT'] = localStorage.getItem('language');
		obj['jobInvoiceFlg'] = cpStorage.jobInvoiceFlg;
		if($("#switch-state-must:checked").val()==cpStorage.jobInvoiceFlg){
			obj['changeInvoiceFlg'] = 0;
		}else{
			obj['changeInvoiceFlg'] = 1;
			obj['jobInvoiceFlg'] = $("#switch-state-must:checked").val();
		}
		
		obj['orderInvoiceInFlg'] = $("#switch-state-must2:checked").val();
		var flg = validataRequired();
		if(!flg) {
			return;
		}
          var ccd = urlPars.parm("company_cd");
		if(ccd!=undefined) {
			if(ccd == "") {
				showErrorHandler("EXECUTE_FAIL", "ERROR", "ERROR");
				window.location.href = $.getJumpPath() + 'master/ltd_list.html';
			}
		}
		if(ccd!=undefined) {
			obj['company_cd'] = ccd;
			obj['companyLockFlg'] = cpStorage.getCompanyLockFlg();
			var company = {
				company: obj
			}
			
			updateCompany(company);
			
		} else {
			var company = {
				company: obj
			}
			insertCompany(company);
		}

	});

})
cpStorage={
	changeInvoiceFlg:0,
	timeZone:null,
	companyLockFlg:0,
	getTimeZone:function (){
		return this.timeZone;
 },
   getCompanyLockFlg:function(){
   	return this.companyLockFlg;
   }
}


