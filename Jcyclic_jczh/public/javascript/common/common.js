$(document).ready(function(){
	//解决google更新 on/off 隐藏问题
	setTimeout(function () {
		$('.position_sticky').css('position','relative');
		$('.position_30').css('margin-top','0');
	},500)
});
/**
 * 方法名 $.layerShow
 * 方法的说明 layer弹出效果
 * @param btn#id url
 * @return 无返回
 * @author作者 张立学
 * @date 日期 2018.06.27
 */
$.layerShow = function(btnId, url) {
	//弹出一个iframe层
	var jumdFlg = "";
	$('#' + btnId).on('click', function() {

		var btnIds = btnId.split("-");
		if(btnIds.length > 1) {
			if(btnIds[0] == "jobCreat") {
				jumdFlg = $("#per").find("i#" + btnId).parents(".person").find("select").val();
				if($('#cldiv_cd').val() == "" && jumdFlg == "2") {
					showErrorHandler("NOT_CHOOSE_CLINET_FAIL", "info", "info");
					return;
				}
			}
		}
		var index = layer.open({
			type: 2,
			//title: ['取引先','font-size:16px;font-weight:bold;'],
			title: false,
			closeBtn: 0,
			shadeClose: true, //点击遮罩关闭层
			area: ['500px', 'auto'],
			//move:false,
			content: url,
			//content: '../master/suppliers_retrieval.html',
			success: function(layero, index) {
				var iframeWin = window[layero.find('iframe')[0]['name']];
				var closeBtn = layer.getChildFrame('.iconfont.icon-guanbi,#closeBtnInput', index);
				var btnIds = btnId.split("-");
				if(btnIds.length > 1) {
					if(btnIds[0] == "jobCreat") {
						jumdFlg = $("#per").find("i#" + btnId).parents(".person").find("select").val();
						if($('#cldiv_cd').val() == "" && jumdFlg == 2) {
							showErrorHandler("NOT_CHOOSE_CLINET_FAIL", "info", "info");
						}
						var client = layer.getChildFrame('#clientID', index);
						client.val($('#cldiv_cd').val());
						var pageFlg = layer.getChildFrame('#pageFlg', index);
						pageFlg.val(btnId);

						var jumd = layer.getChildFrame('#pd', index);
						if(jumdFlg == "2") {
							jumd.val("isJuser");
						} else {
							jumd.val("isJmd");
						}
						iframeWin.employeePopInitHandler();
					}
				} else if(btnId == "searchDD") {
					var client = layer.getChildFrame('#pd', index);
					client.val(btnId);
					iframeWin.employeePopInitHandler();
				} else if(btnId == "searchCus") {
					var client = layer.getChildFrame('#pd', index);
					client.val(btnId);
					iframeWin.employeePopInitHandler();
				} else if(btnId == "searchLend") {
					var client = layer.getChildFrame('#pd', index);
					client.val(btnId);
					iframeWin.employeePopInitHandler();
				} else {
					var client = layer.getChildFrame('#pd', index);
					client.val(btnId);
					iframeWin.clientPopInitHandler();
				}
				/*
				else if(btnId == "searchClient")//查询客户
				{
					var client = layer.getChildFrame('#pd', index);
				    client.val("searchClient");
				    iframeWin.clientPopInitHandler();
				}
				else if(btnId == "searchContra")//查询承包方
				{
					var client = layer.getChildFrame('#pd', index);
				    client.val("searchContra");
				    iframeWin.clientPopInitHandler();
				}
				else if(btnId == "searchPay")//查询付款方
				{
					var client = layer.getChildFrame('#pd', index);
				    client.val("searchPay");
				    iframeWin.clientPopInitHandler();
				}
				else if(btnId == "searchHDY")//查询HDY
				{
					var client = layer.getChildFrame('#pd', index);
				    client.val("searchHDY");
				    iframeWin.clientPopInitHandler();
				}
				*/
				//var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
				//				console.log(iframeWin)
				closeBtn.on('click', function() {
					layer.close(index);
				})
				//delete_validate_red();
			}
		});
		window.layer_index = index;
	});
}
/**
 * 方法名 $.foreignGoodsShow
 * 方法的说明 外货switch效果
 * @param 
 * @return 无返回
 * @author作者 张立学
 * @date 日期 2018.06.27
 */
$.foreignGoodsShow = function() {
	$("#switch-state").on('switchChange.bootstrapSwitch', function(e, state) {
		if(state) { //״̬Ϊon
			$("#exchange_rate").css("display", "block");
			$("#plansale_foreign_code").removeAttr("disabled");
			$("#plansale_foreign_code option").removeAttr("disabled");
			$("#plansale_foreign_code option").css("display", "block");
			$("#plansale_foreign_code").css("background", "#fff");
		} else { //״̬Ϊ0FF
			$("#exchange_rate").css("display", "none");
			$("#plansale_foreign_code").attr("disabled", "disabled");
			$("#plansale_foreign_code").css("background", "#eeeeee");
			$("#plansale_foreign_code option").attr("disabled", "disabled");
			$("#plansale_foreign_code option").css("display", "none");
		}
	});
	$("#switch-state1").on('switchChange.bootstrapSwitch', function(e, state) {
		if(state) { //״̬Ϊon
			$("#exchange_rate1").css("display", "block");
			$("#cost_foreign_type").removeAttr("disabled");
			$("#cost_foreign_type option").removeAttr("disabled");
			$("#cost_foreign_type option").css("display", "block");
			$("#cost_foreign_type").css("background", "#fff");
			$("#cost_foreign_type").find("option[value='']").addClass("hidden");
			$("#cost_foreign_type").val($("#cost_foreign_type").find("option").eq(1).val())
			$(".plan_cost_cure_code").addClass("required");
			//$("#common-dayT").addClass("date-box-required");
			//$(".plan_cost_refer").addClass("required");
			$('#cost_need_change_money1').text($('#cost_foreign_type').find("option:selected").text());
			//换算code，五位小数验证
			$(".plan_cost_cure_code").keyup(function() {
				//				if(!/^\d{0,1}(\d{0,5})\.{0,1}(\d{1,4})?$/.test(this.value)) {
				//					this.value = "";
				//					showErrorHandler("CURE_STYLE_ERR", 'info', 'info');
				//					return;
				//				}
				var flag = number_fl_ck(this.value, 10, 5);
				if(!flag) {
					this.value = number_va_length(this.value, 10, 5);
					//showErrorHandler("CURE_STYLE_ERR", 'info', 'info');
					return;
				}
			});
		} else { //״̬Ϊ0FF
			$("#exchange_rate1").css("display", "none");
			$("#cost_foreign_type").attr("disabled", "disabled");
			$("#cost_foreign_type").css("background", "#eeeeee");
			$("#cost_foreign_type option").attr("disabled", "disabled");
			$("#cost_foreign_type option").css("display", "none");
			$("#cost_foreign_type").val("");
			$(".plan_cost_cure_code").val("");
			$("#common-dayT").datebox('setValue', "");
			$(".plan_cost_refer").val("");
			$('#cost_need_change_money1').text(sessionStorage.getItem("localMoneyCode"));
			$(".plan_cost_cure_code").removeClass("required");
			//$("#common-dayT").removeClass("date-box-required");
			//$(".plan_cost_refer").removeClass("required");
		}
	});
}
/**
 * 方法名 $.fn.exist
 * 方法的说明 判断元素是否存在
 * @param 
 * @return Boolean
 * @author作者 张立学
 * @date 日期 2018.05.09
 */
$.fn.exist = function() {
	if($(this).length >= 1) {
		return true;
	}
	return false;
};
/**
 * 方法名 $.extend
 * 方法的说明 扩展jq增加setInputVal与getInputVal函数实现自动get与setinput值
 * @param json
 * @return json必须是遵循对象的相应要求的格式否则返回false
 * @author作者 张立学
 * @date 日期 2018.05.09
 */
$.extend({　　
	setInputVal: function(json) {
		if(typeof json == 'object') {
			for(var i in json[0]) {
				$("input[type = 'text']input[name = '" + i + "'],input[type = 'number']input[name = '" + i + "'],textarea[name=" + i + "]").val(json[0][i]);
				//$("input[type='radio']input[name = '"+i+"'],input[type='checkbox']input[name='"+i+"']").attr('checked',Boolean(Number(json[0][i])));
				$("input[type='radio']input[name = '" + i + "'][value='" + json[0][i] + "']").prop('checked', true);
				$("input[type='checkbox']input[name='" + i + "']").prop('checked', Boolean(Number(json[0][i])));
			}
		} else {
			return false;
		}
	},
	getInputVal: function() {
		var obj = {};
		var checkObj = [];
		var objInputTextLen = $("select,input[type = 'text'],input[type = 'radio'],input[type = 'checkbox'],textarea,input[type = 'email'],input[type = 'password'],input[type = 'number']");
		objInputTextLen.each(function() {
			var name = this.name;
			var val = this.value;
			var check = this.checked;
			if(name != '') {
				if(this.type == 'select-one' || this.type == 'text' || this.type == 'textarea' || this.type == 'email' || this.type == 'password' || this.type == 'number') {
					obj[name] = val;
				} else if(this.type == 'radio') {
					if(check) {
						obj[name] = val;
					}
				} else {
					if(check) {
						var objNew = {};
						objNew['name'] = name;
						objNew['value'] = val;
						checkObj.push(objNew);
					}
				}
			}
		})
		checkObj.forEach(function(o, index) {
			var str = obj[o['name']] || '';
			str += o['value'] + ',';
			obj[o['name']] = str;
		});
		return obj;
	},
	rangeDateBox: function() {
		var a = $("#registrationUi,#salescategory").datagrid('getColumnOption', 'start_date');
		var c = a.cellClass;
		var d = $('.' + c + '.datagrid-editable').find('.datagrid-editable-input');
		var buttons = $.extend([], $.fn.datebox.defaults.buttons);
		buttons.splice(1, 0, {
			text: '清除',
			handler: function(target) {
				$(target).datebox("setValue", "");
			}
		});
		d.datebox({
			buttons: buttons
		});
	},
	rangeDateBoxOne: function() {
		var a = $("#registrationUi,#salescategory_T").datagrid('getColumnOption', 'start_date');
		var c = a.cellClass;
		var d = $('.' + c + '.datagrid-editable').find('.datagrid-editable-input');
		var buttons = $.extend([], $.fn.datebox.defaults.buttons);
		buttons.splice(1, 0, {
			text: '清除',
			handler: function(target) {
				$(target).datebox("setValue", "");
			}
		});
		d.datebox({
			buttons: buttons
		});
	},
	dateLimit: function(dateId, str, flg) {
		var d = $("#" + dateId);
		if(typeof(str) == 'string') {
			var setd = str.split("-");
		} else {
			var setd = str.format('yyyy-mm-dd').split("-");
		}
		d.datebox().datebox('calendar').calendar({
			validator: function(date) {
				if(flg == 0) {
					var d1 = new Date(setd[0], setd[1] - 1);
				} else {
					var d1 = new Date(setd[0], setd[1] - 1, setd[2]);
				}
				return d1 <= date;
			}
		});
	},
	dateLimit_f: function(dateId, str, flg) {
		var d = $("#" + dateId);
		if(typeof(str) == 'string') {
			var setd = str.split("-");
		} else {
			var setd = str.format('yyyy-mm-dd').split("-");
		}
		d.datebox().datebox('calendar').calendar({
			validator: function(date) {
				if(flg == 0) {
					var d1 = new Date(setd[0], setd[1] - 1);
				} else {
					var d1 = new Date(setd[0], setd[1] - 1, setd[2]);
				}
				return d1 >= date;
			}
		});
	}
});

/**
 * 方法名 pagerFilter
 * 方法的说明 前端实现分页功能函数   需要一次加载全部数据
 * @param data
 * @return object
 * @author作者 张立学
 * @date 日期 2018.05.08
 */

function pagerFilter(data) {

	if(typeof data.length == 'number' && typeof data.splice == 'function') { //判断数据是否是数组
		data = {
			total: data.length,
			rows: data
		}
	}
	var dg = $(this);
	var opts = dg.datagrid('options');
	var pager = dg.datagrid('getPager');
	//2020/08/20当init初始化时第一页pageSize为51时会出现页码器问题  会有数据丢失 将init pageSize还原为50 pagerFilter时 重新赋值ops对象并refresh
	if (dg.attr('id') == 'label_management' || dg.attr("id") === 'wedgemembersleft' || dg.attr("id") === 'wedgemembersright' || dg.attr("id") === 'irrelevantTab' || dg.attr("id") === 'relevancedTab'){
		if (opts.pageNumber == 1){
			opts.pageSize = 51;
			opts.pageList = [51];
			pager.pagination('refresh', {
				pageNumber: opts.pageNumber,
				pageSize: opts.pageSize
			});
		}
	}
	//记录检索input的value值
	if (dg.attr("id") === 'wedgemembersleft'){
		var search_value = $('#'+opts.sortName + 'L').val();
		window.search_input_value = search_value;
	}else if (dg.attr("id") === 'wedgemembersright'){
		var search_value = $('#'+opts.sortName + 'R').val();
		window.search_input_value = search_value;
	}else if (dg.attr("id") === 'irrelevantTab'){
		var search_value = $('#'+opts.sortName + 'L').val();
		window.search_input_value = search_value;
	}else if (dg.attr("id") === 'relevancedTab'){
		var search_value = $('#'+opts.sortName + 'R').val();
		window.search_input_value = search_value;
	} else {
		var search_value = $('#'+opts.sortName).val();
		window.search_input_value = search_value;
	}
	pager.pagination({
		onSelectPage: function(pageNum, pageSize) {
			// if (dg.attr('id') == 'label_management' && pageNum > 1){
			// 	pageSize = 50;
			// }
			if (dg.attr('id') == 'label_management' || dg.attr("id") === 'wedgemembersleft' || dg.attr("id") === 'wedgemembersright' || dg.attr("id") === 'irrelevantTab' || dg.attr("id") === 'relevancedTab'){
				if (pageNum > 1){
					pageSize = 50;
					opts.pageList = [50];
				}
			}
			opts.pageNumber = pageNum;
			opts.pageSize = pageSize;
			pager.pagination('refresh', {
				pageNumber: pageNum,
				pageSize: pageSize
			});
			dg.datagrid('loadData', data);
			if (data.originalRows[0].moveTableSign){
                dg.datagrid('freezeRow', 0);
            }
			//分页切换时重新绑定keyup事件
			if (dg.attr("id") === 'wedgemembersleft' || dg.attr("id") === 'wedgemembersright' || dg.attr("id") === 'irrelevantTab' || dg.attr("id") === 'relevancedTab'){
				$('input.rng').unbind('keyup');
				$('input.rng').on('keyup',function(event){
					if (event.keyCode == "13") {
						var values = this.value;
						var id = this.id;
						var gridname = $(this).closest('.datagrid-view2').next('table').attr('id');
						if (gridname == undefined){
							var gridname = dg.attr("id");
						}
						if($(this).hasClass('right')){
							var dataFil = rowright
						}else{
							var dataFil = rowleft;
						}
						var that = this.value;
						for (var i = 0;i < 2;i++){
							search(dataFil,gridname,values,id,that)
						};
					}
				})
			}
			if (dg.attr("id") === 'label_management'){
				$('input.rng').unbind('keyup');
				for (var i = 0;i < 2;i++){
					search(data);
				};
			}
		}
	});
	if(!data.originalRows) {
		data.originalRows = (data.rows);
	}
	var start = (opts.pageNumber - 1) * parseInt(opts.pageSize);
	var end = start + parseInt(opts.pageSize);
	// if (dg.attr('id') == 'label_management' && dg.datagrid('options').pageNumber > 1){
	// 	start += 1;
	// 	end += 1;
	// }
	if (dg.attr('id') == 'label_management' || dg.attr("id") === 'wedgemembersleft' || dg.attr("id") === 'wedgemembersright' || dg.attr("id") === 'irrelevantTab' || dg.attr("id") === 'relevancedTab'){
		if (dg.datagrid('options').pageNumber > 1){
			start += 1;
			end += 1;
		}
	}
	data.rows = (data.originalRows.slice(start, end));
	var initFirstRow = data.originalRows[0],pagerNumberZ = opts.pageNumber;
	solvePageFreeze(initFirstRow,pagerNumberZ,data.rows);
	return data;
}
/**
 * 方法名 solvePageFreeze
 * 方法的说明 分页后检索框消失
 * @param
 * @return
 * @author作者 zlx
 * @date 日期 2020.06.15
 */
function solvePageFreeze(initFirstRow,pagerNumberZ,rows) {
	if (initFirstRow == undefined){
		return;
	}else {
		if (initFirstRow.moveTableSign){
			if (pagerNumberZ != 1){
				rows.unshift(initFirstRow);
			}
		}
	}
}
/**
 * 方法名 userLogin
 * 方法的说明 退出
 * @param 
 * @return 
 * @author作者 王岩
 * @date 日期 2018.05.09
 */
function logoutHandler(flg) {
	var valiFlg = false;
	var msg = "";
	var confirmTitle = $.getConfirmMsgTitle();
	if(flg == "editUser") {
		logoutFun();
	} else {
		msg = showConfirmMsgHandler("IS_LOGOUT");
		$.messager.confirm(confirmTitle, msg, function(r) {
			if(r) {
				logoutFun();
			}
		});
	}
}
/**
 * 方法名 msgConfirmInformation
 * 方法的说明 弹出框多国语言设定
 * @param
 * @return
 * @author作者 张立学
 * @date 日期 2018.05.09
 */
function msgConfirmInformation() {
	if($.messager) {
		if(localStorage.getItem('language') == 'jp' || localStorage.getItem('language') == '001') {
			$.messager.defaults.ok = 'はい';
			$.messager.defaults.cancel = 'いいえ';
		} else if(localStorage.getItem('language') == 'zc' || localStorage.getItem('language') == '002') {
			$.messager.defaults.ok = '是';
			$.messager.defaults.cancel = '否';
		}else if (localStorage.getItem('language') == 'zt'){
			$.messager.defaults.ok = '是';
			$.messager.defaults.cancel = '否';
		}else if (localStorage.getItem('language') == 'en'){
			$.messager.defaults.ok = 'Ok';
			$.messager.defaults.cancel = 'Cancel';
		}else {
			$.messager.defaults.ok = 'はい';
			$.messager.defaults.cancel = 'いいえ';
		}
	}
}
function logoutFun() {
	var path = $.getAjaxPath() + "logout";
	var pars = {};
	$.ajax({
		url: path,
		headers: {
			"requestID": $.getRequestID()
		},
		data: JSON.stringify(pars),
		success: function(data) {
			if(data[$.getRequestMetaName()].result == $.getRequestStatusName()) {
				//localStorage.clear();
				clear_localstorage();
				window.location.href = $.getJumpPath();
			} else {
				showErrorFunHandler(data[$.getRequestMetaName()].result, "ERROR", "ERROR");
			}
			$.setRequestID(data[$.getRequestUserInfoName()].requestID);
		},
		error: function(data) {
			showErrorHandler("CONNECTION_SERVER_ERROR", "ERROR", "ERROR");
			//logoutHandler();
		}
	});
}

function sysLock() {
	var msg = showConfirmMsgHandler("IS_LOCK_SYS");
	var confirmTitle = $.getConfirmMsgTitle();
	if($.messager) {
		if(localStorage.getItem('language') == 'jp' || localStorage.getItem('language') == '001') {
			$.messager.defaults.ok = 'はい';
			$.messager.defaults.cancel = 'いいえ';
		} else if(localStorage.getItem('language') == 'zc' || localStorage.getItem('language') == '002') {
			$.messager.defaults.ok = '是';
			$.messager.defaults.cancel = '否';
		}
	}
	$.messager.confirm(confirmTitle, msg, function(r) {
		if(r) {
			var lockPar = $('#sysLockFlg').val();
			if(lockPar == "unlock") {
				lockPar = 1;
			} else {
				lockPar = 0;
			}
			var path = $.getAjaxPath() + "systemlock";
			var pars = {
				"sysLockFlg": lockPar
			};
			$.ajax({
				url: path,
				headers: {
					"requestID": $.getRequestID(),
					"requestName": $.getRequestNameByMst()
				},
				data: JSON.stringify(pars),
				success: function(data) {
					if(data[$.getRequestMetaName()].result == $.getRequestStatusName()) {
						showErrorHandler("EXECUTE_SUCCESS", "INFO", "INFO");
						$.setRequestID(data[$.getRequestUserInfoName()].requestID);
					} else {
						showErrorFunHandler(data[$.getRequestMetaName()].result, "ERROR", "ERROR");
					}
				},
				error: function(data) {
					showErrorHandler("CONNECTION_SERVER_ERROR", "ERROR", "ERROR");
					//logoutHandler();
				}
			});
		}
	});
}
/**
 * 方法名 userLogin
 * 方法的说明 检查登陆状态
 * @param flg 0:登陆页面;1:其他页面
 * @return 
 * @author作者 王岩
 * @date 日期 2018.05.09
 */
function isCheckLogin(flg) {
	if($.getRequestID() == null && $.getUserID() == null && $.getCompanyID() == null) {
		if(flg != 0) {
			//localStorage.clear();
			clear_localstorage();
			window.location.href = "http://127.0.0.1/Jcyclic_jczh/";//$.getJumpPath();
			throw new Error('5555');
		}
	}
}
/**
 * 方法名 setDomChildList
 * 方法的说明 设置select的options
 * @param list 对象数组,domID select元素ID
 * @return 
 * @author 王岩
 * @date  2018.05.16
 */
function setDomChildList(list, domID, strFlg) {
	var langInfoBytop = getCommonCodeByLange(strFlg);
	var showLange = langInfoBytop.showLange;
	str = '';
	if(domID == "cost_foreign_type" || domID == "plansale_foreign_code")
	{
		str = '<option class="hidden" value=""></option>';
	}else{
		str = '<option value=""></option>';
	}
	if(domID == "roleList") {
		var roleNameLast = localStorage.getItem('language');
		for(var i = 0; i < list.length; i++) {
			str += '<option value="' + list[i]['roleID'] + '">' + list[i]['roleName' + roleNameLast] + '</option>';
		}
	} else {
		for(var i = 0; i < list.length; i++) {
			str += '<option value="' + list[i]['itemcd'] + '">' + list[i][showLange] + '</option>';
		}
	}
	if((typeof domID == 'string') && domID.constructor == String) {
		$('#' + domID).html(str);
	} else {
		domID.empty();
		domID.append($(str));
	}
}
/**
 * 方法名 getCommonCodeByLange
 * 方法的说明 根据top位置的语言选择获取当前语言
 * @param 
 * @return Object
 * @author作者 王岩
 * @date 日期 2018.05.16
 */
function getCommonCodeByLange(strFlg) {
	var showLange = "";
	var topLangeToNumber = "";
	var toplange = $('#language').val();
	if(toplange == undefined) {
		toplange = parent.$('#language').val();
	}
	switch(toplange) {
		case "jp":
			showLange = 'itemname' + strFlg + 'jp';
			topLangeToNumber = "001";
			break;
		case "zc":
			showLange = 'itmname';
			topLangeToNumber = "002";
			break;
		case "zt":
			showLange = 'itemname' + strFlg + 'hk';
			topLangeToNumber = "003";
			break;
		case "en":
			showLange = 'itemname' + strFlg + 'en';
			topLangeToNumber = "004";
			break;

	};
	return {
		"showLange": showLange,
		"topLangeToNumber": topLangeToNumber
	}
}
/**
 * 方法名 changeClientLeftOrRight
 * 方法的说明 用户和客户绑定页面左右表格数据切换
 * @param left 左或者右,leftTabName 左侧表格ID,rightTabName 右侧表格ID
 * @return Object
 * @author作者 王岩
 * @date 日期 2018.05.16
 */
function changeClientLeftOrRight(flg, leftTabName, rightTabName, flag,testFlg) {
	if(flg == "left") {
		//从左到右
		var rows = $('#' + leftTabName).datagrid('getSelections');
		if(!rows[0].cldivcd && rows[0].divnm) {
			var rows = rows.slice(1);
		}
		var user_flag = rows[0].member_id;
		if(flag == undefined) {
			if(isNaN(user_flag) == true) {
				var rows = rows.slice(1);
			}
		}
		if(String(rows[0].account_cd).indexOf("<") != -1) {
			showErrorHandler("WEDGEMEMBERSNO", "info", "info");
			return;
		}
		if(rows.length > 0) {
			for(var i = 0; i < rows.length; i++) {
				rowright.push(rows[i]);
				var index = $('#' + leftTabName).datagrid('getRowIndex', rows[i]);
				$('#' + rightTabName).datagrid('insertRow', {
					row: rows[i]
				});
				$('#' + leftTabName).datagrid('deleteRow', index);
				//重新加载数据
				var changeGridRightData = {
					rows:rowright,
					total:rowright.length
				}
				$('#'+rightTabName).datagrid('loadData',changeGridRightData);
				$('#'+rightTabName).datagrid('freezeRow', 0);
			}
			if($('#' + leftTabName).datagrid('getData')['originalRows'].length < 1) {
				$('#' + leftTabName).datagrid('uncheckAll');
			}
			for(var j = 0; j < rows.length; j++) {
				for(var i = 0; i < rowleft.length; i++) {
					var uscd = rows[j].cldivcd;
					var arr_account_cd = rowleft[i].cldivcd;
					if(uscd == undefined) {
						uscd = rows[j].user_cd;
						arr_account_cd = rowleft[i].user_cd;
					}
					if(String(arr_account_cd).indexOf(uscd) != -1) {
						rowleft.splice(i, 1);
						i--;
					}
				}
			}
			$("input[type=checkbox]").prop("checked", false);
			$('input.rng').keyup();
			if (flg == 'right'){
                //$('#relevancedTab').datagrid('freezeRow', 0);
            }
			if (flg == 'left'){
                //$('#irrelevantTab').datagrid('freezeRow', 0);
            }
			var maxPageText = $('#' + leftTabName).parent().next('.datagrid-pager.pagination').find('.pagination-num').parent().next().find('span').text();
			var pageNumberTable = $('#' + leftTabName).datagrid('options').pageNumber;
			if (pageNumberTable == Number(maxPageText) + 1){
				$('#' + leftTabName).datagrid('options').pageNumber -= 1;
			}
			$('#' + leftTabName).datagrid('loadData',{'rows':rowleft,'total':rowleft.length});
            $('#' + leftTabName).datagrid('freezeRow', 0);
			if (testFlg){
				//easyInsertH(rightTabName);
			}
            $('input.rng').unbind('keyup');
			//重新绑定事件
			$('input.rng').on('keyup',function(event){
				if (event.keyCode == "13") {
					var values = this.value;
					var id = this.id;
					if($(this).hasClass('right')){
						var dataFil = rowright
						var gridname = rightTabName;
					}else{
						var dataFil = rowleft;
						var gridname = leftTabName;
					}
					var that = this.value;
					search(dataFil,gridname,values,id,that)
				}
			})
		} else {
			showErrorHandler("NOT_CHOOSE_FAIL", "info", "info");
		}
	} else {
		//从右到左
		var rows = $('#' + rightTabName).datagrid('getSelections');
		if(!rows[0].cldivcd && rows[0].divnm) {
			var rows = rows.slice(1);
		}
		var user_flag = rows[0].member_id;
		if(flag == undefined) {
			if(isNaN(user_flag) == true) {
				var rows = rows.slice(1);
			}
		}
		if(String(rows[0].account_cd).indexOf("<") != -1) {
			showErrorHandler("WEDGEMEMBERSNO", "info", "info");
			return;
		}
		if(rows.length > 0) {
			for(var i = 0; i < rows.length; i++) {
				rowleft.push(rows[i]);
				var index = $('#' + rightTabName).datagrid('getRowIndex', rows[i]);
				$('#' + leftTabName).datagrid('insertRow', {
					row: rows[i]
				});
				$('#' + rightTabName).datagrid('deleteRow', index);
				//重新加载数据
				var changeGridLeftData = {
					rows:rowleft,
					total:rowleft.length
				}
				$('#'+leftTabName).datagrid('loadData',changeGridLeftData);
				$('#'+leftTabName).datagrid('freezeRow', 0);
			}
			if($('#' + rightTabName).datagrid('getData')['originalRows'].length < 1) {
				$('#' + rightTabName).datagrid('uncheckAll');
			}
			for(var j = 0; j < rows.length; j++) {
				for(var i = 0; i < rowright.length; i++) {
					var uscd = rows[j].cldivcd;
					var arr_account_cd = rowright[i].cldivcd;
					if(uscd == undefined) {
						uscd = rows[j].user_cd;
						arr_account_cd = rowright[i].user_cd;
					}
					if(String(arr_account_cd).indexOf(uscd) != -1) {
						rowright.splice(i, 1);
						i--;
					}
				}
			}
			$("input[type=checkbox]").prop("checked", false);
			$('input.rng').keyup();
			if (flg == 'right'){
                //$('#relevancedTab').datagrid('freezeRow', 0);
            }
			if (flg == 'left'){
                //$('#irrelevantTab').datagrid('freezeRow', 0);
            }
            var maxPageText = $('#' + rightTabName).parent().next('.datagrid-pager.pagination').find('.pagination-num').parent().next().find('span').text();
			var pageNumberTable = $('#' + rightTabName).datagrid('options').pageNumber;
			if (pageNumberTable == Number(maxPageText) + 1){
				$('#' + rightTabName).datagrid('options').pageNumber -= 1;
			}
			$('#' + rightTabName).datagrid('loadData',{'rows':rowright,'total':rowright.length});
            $('#' + rightTabName).datagrid('freezeRow', 0);
			if (testFlg){
				 //easyInsertH(leftTabName);
			}
            $('input.rng').unbind('keyup');
            //重新绑定事件
            $('input.rng').on('keyup',function(event){
                if (event.keyCode == "13") {
                    var values = this.value;
                    var id = this.id;
                    if($(this).hasClass('right')){
                        var dataFil = rowright
                        var gridname = rightTabName;
                    }else{
                        var dataFil = rowleft;
                        var gridname = leftTabName;
                    }
                    var that = this.value;
                    search(dataFil,gridname,values,id,that)
                }
            })
		} else {
			showErrorHandler("NOT_CHOOSE_FAIL", "info", "info");
		}
	}
}
/**
 * 方法名 $.file_i18n
 * 方法的说明 上传文件input样式实例化
 * @param 
 * @return 
 * @author作者 张立学
 * @date 日期 2018.05.09
 */
var file_i18n = function() {
	$('#files').filestyle({
		text: 'ファイル選択',
		dragdrop: false,
	});
	if($('.group-span-filestyle.input-group-btn').exist()) {
		$('.group-span-filestyle.input-group-btn').attr('tabindex', 9);
	}
	$('.buttonText').addClass('i18n');
	$('.buttonText').attr("name", "suppliersList_select_file");
}

function goBackPageHandler() {
	//获取flag
	var flag = $('ul.nav.nav-tabs li.active').attr('role');
	if(flag == 1){
		window.location.href = $.getJumpPath() + 'jczh/job_registration_list.html?view=init&menu=se';
	}else if(flag == 2){
		window.location.href = $.getJumpPath() + 'jczh/cost_list.html?view=6';
	}else{
		window.history.go(-1);
	}
}
/**
 * 方法名goCostlist
 * 方法的说明 返回原价一览界面
 * @param 
 * @return 
 * @author作者 付清泉
 * @date 日期 2018.05.30
 */
function goCostlist() {
	window.location.href = $.getJumpPath() + "jczh/cost_list.html?view=6";
}
/**
 * 方法名goCostlist
 * 方法的说明 返回job一览界面
 * @param 
 * @return 
 * @author作者 付清泉
 * @date 日期 2018.05.30
 */
function goJoblist() {
	window.location.href = $.getJumpPath() + "jczh/job_registration_list.html?view=init&menu=se";
}
/**
 * 方法名goCostlist
 * 方法的说明 返回top界面
 * @param 
 * @return 
 * @author作者 付清泉
 * @date 日期 2018.05.30
 */
function goToplist() {
	window.location.href = $.getJumpPath() + "jczh/top_registration.html";
}

/**
 * 方法名 $.file_i18n
 * 方法的说明 截取http中上一个页面传递的参数值
 * @param 
 * @return 
 * @author作者 马有翼
 * @date 日期 2018.05.30
 */
function getQueryString(name, url) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	var r = window.location.search.substr(1).match(reg);
	if(r != null) {
		return unescape(r[2]);
	} else {
		var url = "index.html";
		showInfoMsgHandler("ILLEGAL_OPERATION_TOLOGIN", "info", "info", url);
	}
}

/**
 * 方法名 $.file_i18n
 * 方法的说明 表格中,把数字换成四国语言字符展示在页面中
 * @param 
 * @return 
 * @author作者 马有翼
 * @date 日期 2018.05.31
 */
function getGridLanguage(name, value) {
	var language = $('#language').val();
	var cof = "";
	switch(name) {
		case "del_flg":
			if(value == "0") {
				cof = part_language_change_new("employeeList_effective");
			} else if(value == "1") {
				cof = part_language_change_new("employeeList_invalid");
			} else if(value == null) {
				cof = "--";
			}
			break;
		case "tran_lend":
			if(value == "1") {
				cof = part_language_change_new("salescategoryRegistration_Variable");
			} else if(value == "0") {
				cof = part_language_change_new("salescategoryRegistration_Invisible");
			} else if(value == null) {
				cof = "--";
			}
			break;
		case 'company_type':
			if(value == "1") {
				cof = part_language_change_new("ltdRegistration_but_DY_hakuhodo_group");
			} else if(value == "0") {
				cof = part_language_change_new("ltdRegistration_hakuhodo_DY_group");
			} else if(value == "2") {
				cof = part_language_change_new("ltdRegistration_for_management");
			} else if(value == null) {
				cof = "--";
			}
			break;
		case "number_rules":
			if(value == "1") {
				cof = part_language_change_new("ltdRegistration_through_the_moon");
			} else if(value == "0") {
				cof = part_language_change_new("ltdRegistration_through_the_years");
			} else if(value == null) {
				cof = "--";
			}
			break;
		case "del_flgclmst":
			if(value == "0") {
				cof = part_language_change_new("suppliersList_effective");
			} else if(value == "1") {
				cof = part_language_change_new("suppliersList_invalid");
			}
			break;
		case "msgtrn":
			if(value == "1") {
				cof = part_language_change_new("board_system");
			} else if(value == "0") {
				cof = part_language_change_new("board_success");
			}
			break;
		case "label_level":
			if(value == "0") {
				cof = part_language_change_new("jobregistration_my");
			} else if(value == "1") {
				cof = part_language_change_new("jobregistration_ma");
			} else if(value == null) {
				cof = "--";
			}
			break;
		case "isdeduction":
			if(value == "1") {
				cof = part_language_change_new("liti_off");
			} else if(value == "0") {
				cof = part_language_change_new("invoice_edit_flag");
			} else if(value == null) {
				cof = "--";
			}
			break;
		case "assignflg":
			if(value == "1") {
				cof = part_language_change_new("jobregistration_nop");
			} else if(value == "0") {
				cof = part_language_change_new("jobregistration_noj");
			} else if(value == null) {
				cof = "--";
			}
			break;
		case "reqflg":
			if(value == "1") {
				cof = part_language_change_new("wei");
			} else if(value == "0") {
				cof = part_language_change_new("ji");
			} else if(value == null) {
				cof = "--";
			}
			break;
		case "recflg":
			if(value == "1") {
				cof = part_language_change_new("weirujin");
			} else if(value == "0") {
				cof = part_language_change_new("ji");
			} else if(value == null) {
				cof = "--";
			}
			break;
		case "invflg":
			if(value == "1") {
				cof = part_language_change_new("wei");
			} else if(value == "0") {
				cof = part_language_change_new("ji");
			} else if(value == null) {
				cof = "--";
			}
			break;
		case "costfinishflg":
			if(value == "1") {
				cof = part_language_change_new("wei");
			} else if(value == "0") {
				cof = part_language_change_new("yi");
			} else if(value == null) {
				cof = "--";
			}
			break;
		case "deductionflg":
			if(value == "1") {
				cof = part_language_change_new("paymentregistration_deduction");
			} else if(value == "0") {
				cof = part_language_change_new("paymentregistration_not_deduction");
			} else if(value == null) {
				cof = "--";
			}
			break;
	}
	return cof;
}
/**
 * 方法名 flushToChangeLang
 * 方法的说明 切换语言 表格内容重新加载
 * @param 
 * @return 
 * @author作者 刘实
 * @date 日期 2018.06.1
 */
function flushToChangeLang() {
	var id = $('.datagrid-f').prop('id');
	if(!id) {
		return;
	}
	/*********修改重新加在表格 王岩 2018.06.15*******************/
	$('#' + id).datagrid("reload");
	/*
	initDataGridHandler(id, 10, "", "top", false, "");
	var rows = $('#' + id).datagrid('getData').rows;
	var data = rows;
	$('#' + id).datagrid('loadData', data);
	*/
}

/**
 * 对象SelectObj
 * 方法名 setSelectOfLog
 * 属性 selectData 保存请求回来的下拉数据  setSelectID 页面select ID数组
 * setSelectOfLog  获取属性值 SelectObj的属性值 进行赋值
 * 在页面语言切换时,用保存的数据赋值
 * @param 
 * @return 
 * @author作者 刘实
 * @date 日期 2018.06.1
 */
SelectObj = {
	lableidCollection: [],
	selectData: null,
	setDefaultData: null,
	setSelectID: null,
	setLableList: [],
	setStringFlg: "",
	setIfram: null,
	//一览页面初始化lablelist
	mstLableList: [],
	//一览页面选中时获取lable交集
	mstLableSet: [],
	setChooseLableList: [],
	getMstLableList: function() {
		return this.mstLableList;
	},
	getMstLableSet: function() {
		return this.mstLableSet;
	},
	setSelectOfLog: function() {
		var strFlg = SelectObj.setStringFlg;
		//获取下拉选项
		var data = SelectObj.selectData;
		//下拉默认选项
		var deflault = SelectObj.setDefaultData;
		//需要赋值的下拉ID数组
		var selectID = SelectObj.setSelectID;
		//
		var iFram = SelectObj.setIfram;
		if(selectID != null) {
			for(var i = 0; i < selectID.length; i++) {
				var defaultBefore = $("#" + selectID[i]).val();
				//ID名要与 数据的键值一致
				if(iFram != null) {
					setDomChildList(data[selectID[i]], iFram.$("#" + selectID[i]), strFlg);
					SelectObj.setIfram = null;
				} else {
					setDomChildList(data[selectID[i]], selectID[i], strFlg);
				}

					//下拉设置默认值
					if(deflault != null) {
	                    // 第二个class名要与 默认值的键一致
	
	                    var selectClass = "";
	                    if($("#" + selectID[i]).className == undefined) {
	                            selectClass = $("#" + selectID[i])[0].className.split(" ")[1];
	                            if(deflault[0] == undefined) {
	                                    if(deflault instanceof Array) {
	                                            $("." + selectClass).val("");
	                                    } else {
	                                    	$("." + selectClass).val(deflault[selectClass]);
	                                    }
	
	                            } else {
	                                    $("." + selectClass).val(deflault[0][selectClass]);
	                            }
	                    } else {
	                            selectClass = $("#" + selectID[i]).className.split(" ")[1];
	                            $("." + selectClass).val(deflault[selectClass]);
	                    }
	
	            }else {
	                    $("#" + selectID[i]).val(defaultBefore);
	            }
			}
		}
	}
}
/**
 * 方法名 addLable
 * 方法的说明 追加标签
 * @param input_class 标签input框的class名
 * @param level_name 标签的level按钮的 name
 * @author作者 刘实
 * @date 日期 2018.06.28
 */
function addLable(input_class, level_name, labletype) {
	var url_update = window.location.href;
	var bl = false;
	if(url_update.indexOf("update") > -1) {
		bl = true;
	}
	var newLable_text = $('.' + input_class).val();
	if(newLable_text == "" || newLable_text == null || newLable_text == undefined) {
		//前端验证
		$('.' + input_class).addClass('border_red');
		$('.' + input_class).next('span').addClass('border_red_left');
		validate($('.' + input_class).closest('div'), part_language_change_new('NODATE'));
		showErrorHandler("NOT_EMPTY", "info", "info");
		return;
	} else {
		$('.' + input_class).removeClass("border_red");
		$('.' + input_class).next('span').removeClass('border_red_left');
		$('.' + input_class).closest('div').tooltip('destroy');
	}
	var newLable_level = $("input[type='radio']input[name='" + level_name + "']:checked").val();
	var lable = {
		lable: {
			labletext: newLable_text,
			lablelevel: newLable_level,
			labletype: labletype
		}
	}
	$.ajax({
		url: $.getAjaxPath() + "jobAddLable",
		data: JSON.stringify(lable),
		headers: {
			"requestID": $.getRequestID(),
			"requestName": $.getRequestNameByMst()
		},
		success: function(data) {
			//			console.log(data);
			if(data[$.getRequestMetaName()].result == $.getRequestStatusName()) {
				$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
				if(data[$.getRequestDataName()] != null && data[$.getRequestDataName()] != 250) {

					//获取新追加标签的 名,id,类型
					var newlablename = $(".new_lable").val();

					var newlablelevel = $('.lable-level-switch').find('.active').find('input').val();
					var newlableId = data[$.getRequestDataName()];
					//获取 初始化时的lable集合 与初始化时选择的lable集合
					var baseLableList = SelectObj.setLableList;
					var baseChooseList = SelectObj.setChooseLableList;
					baseLableList.push({
						"labletext": newlablename,
						"lablelevel": newlablelevel,
						"lableid": newlableId
					});
					SelectObj.setLableList = baseLableList;
					labelToMySelect(baseLableList);
					//如果 选中的集合不是空的说明是更新页面
					if(baseChooseList != [] && bl) {
						//将新追加的 lablecd 放入要选中的数组中  之后保存到全局变量中  为了追加多个不替换上次追加的
						baseChooseList.push({
							"lablecd": newlableId
						});
						setLableArea(baseChooseList);
					} else {
						//说明是登录页面  实例化 baseChooseList 不实例化会报空指针 ,在lablelist中找到这个ID的选中 
						//,再将新的选中lable集合保存到全局变量 为了追加多个不替换上次追加的
						var baseChooseList = SelectObj.lableidCollection;
						baseChooseList.push({
							"lablecd": newlableId
						});
						SelectObj.setChooseLableList = baseChooseList;
						setLableArea(baseChooseList)

					}
					//showErrorHandler("EXECUTE_SUCCESS", "info", "info");
					return;
				} else if(data[$.getRequestDataName()] == 250) {
					// getRedc($(".new_lable"),true);
					$(".new_lable").addClass('border_red');
					$(".new_lable").next('span').addClass('border_red_left');
					validate($(".new_lable").closest('div'), part_language_change_new('NODATE'));
					showErrorHandler("ALREAD_HAVE_SAME_TEXT", "info", "info");

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

/**
 * 方法名 labelToMySelect
 * 方法的说明 将查到的标签封装成 myselect的option数据类型模式
 * @param lable_data 标签集合 
 * @return res 选择的标签ID数组
 * @author作者 刘实
 * @date 日期 2018.06.28
 */
function labelToMySelect(lable_data) {
	//先看权限 ,再根据lablelevel取值
	var arrLableList = [];
	for(var i = 0; i < lable_data.length; i++) {
		var lable = {
			label: lable_data[i].labletext,
			value: lable_data[i].lableid,
			type: lable_data[i].lablelevel == 0 ? "s1" : "s2"
		}

		arrLableList.push(lable);
	}

	var mySelect = $("#mySelect").mySelect({
		mult: true, //true为多选,false为单选
		option: arrLableList,
		onChange: function(res) {
			//选择框值变化返回结果
			var lableIdCollection = [];
			for(var i = 0; i < res.length; i++) {
				var lableid = {
					lablecd: res[i]
				}
				lableIdCollection.push(lableid);
			}
			SelectObj.lableidCollection = lableIdCollection;
		}
	});
}
//获取标签id
function getLable() {
	var lableIdCollection = [];
	$("#setblock ul li").each(function(index, element) {
		var lableid = {
			lablecd: $(this).find("span").attr("value")
		}
		lableIdCollection.push(lableid);
	})
	return lableIdCollection;
}
/*
 * 方法名 setLableArea
 * 方法的说明 将查到的标签封装成 myselect的option数据类型模式
 * @param lable_list 选中集合
 * @author作者 刘实
 * @date 日期 2018.07.04 
 */
function setLableArea(lable_list) {
	var mySelectVal = $(".mySelect-option").children();
	for(var i = 0; i < lable_list.length; i++) {
		for(var k = 0; k < mySelectVal.length; k++) {
			if(mySelectVal.eq(k).attr("data-value") == lable_list[i]["lablecd"]) {
				mySelectVal.eq(k).click();
			}
		}
	}
	$('#set').click();
}

/**
 * 方法名 watch_label
 * 说明 watch标签的方法
 * @param 
 * @return array
 * @author作者 张立学
 * @date 日期 2018.08.30
 */
function watch_label() {
	var a = $('.mySelect-option').find('input');
	var b = [];
	for(var i = 0; i < a.length; i++) {
		if(a.eq(i).prop('checked')) {
			var lableid = {
				lablecd: a.eq(i).closest('.mySelect-option-li').attr('data-value'),
			}
			b.push(lableid);
		}
	}
	return b;
}

/**
 * 方法名 jobCommon
 * 说明 job登录 売上登录右侧共同的事件绑定
 * @param 
 * @return 
 * @author作者 张立学
 * @date 日期 2018.06.29
 */
$.jobCommon = function() {
	//右侧事件绑定
	bodyh = $(document).height(); //浏览器当前窗口文档的高度
	navh = bodyh - 135;
	$("#navright").css("height", navh + "px");
	$("#set").on("click", function() {
		SelectObj.setChooseLableList = watch_label();
		var number = SelectObj.lableidCollection.length;
		if(number != 0) {
			$("#setnig2").css("margin-bottom", "15px");
		}
		set = $(".inputWrap").html();
		$("#setnig2").show();
		$("#setblock").html(set);
		var index = layer.index;
		layer.close(index)
	});
	$("#setbigclose").on("click", function() {
		$("#setnig2").css("display", "none");
		$("#setb1").button('toggle')
	});
	$("#setbigclose1").on("click", function() {
		$("#setnig2").css("display", "none");
		$("#setb1").button('toggle')
	});
	$("#setblock").on("click", ".close", function() {
		var lid = $(this).prev('span').attr("value");
		var mySelectVal = $(".mySelect-option").children();
		for(var i = 0; i < mySelectVal.length; i++) {
			if(mySelectVal.eq(i).attr("data-value") == lid) {
				mySelectVal.eq(i).click();
			}
		}
		$(this).parent().remove();
		SelectObj.setChooseLableList = watch_label();
		var close_length = $("#setblock ul li").length;
		if(close_length == 0) {
			$("#setnig2").css("margin-bottom", "0");
		}
	});
}
/*
 * 方法名 formatNumber
 * 方法的说明 判断标签数量
 * @param Number num 数字
 * @author作者 张立学
 * @date 日期 2018.08.27
 */
$(document).ready(function() {
	var length = $("#setblock ul li").length;
	if(length == 0) {
		$("#setnig2").css("margin-bottom", "0");
	}
})
/*
 * 方法名 formatNumber
 * 方法的说明 将千分符字符串格式化为数字
 * @param Number num 数字
 * @author作者 张立学
 * @date 日期 2018.08.27
 */

function recoveryNumber(num) {
	if((num.split(',').length - 1) > 0) {
		var x = num.split(',');
		//return parseFloat(x.join(""));
		return x.join("");
	} else {
		return num;
	}
}

/*
 * 方法名 formatNumber
 * 方法的说明 将数字格式化为千分符字符串
 * @param Number num 数字,Number cent 保留小数点位数 默认2,Boolean isThousand 是否有千分符 默认true
 * @author作者 张立学
 * @date 日期 2018.07.04 
 */
function formatNumber(num, cent, isThousand) {
	if(num == -Infinity || num == Infinity) {
		return num;
	}
	if(num == undefined) {
		//		console.log('请传入number参数')
		return;
	}
	if(cent == undefined) {
		var cent = $.getDefaultPoint();
	}
	if(isThousand == undefined) {
		var isThousand = true;
	}

	num = num.toString().replace(/\$|\,/g, '');

	// 检查传入数值为数值类型
	if(isNaN(num))
		num = "0";

	// 获取符号(正/负数)
	sign = (num == (num = Math.abs(num)));

	num = Math.floor(num * Math.pow(10, cent) + 0.50000000001); // 把指定的小数位先转换成整数.多余的小数位四舍五入
	cents = num % Math.pow(10, cent); // 求出小数位数值
	num = Math.floor(num / Math.pow(10, cent)).toString(); // 求出整数位数值
	cents = cents.toString(); // 把小数位转换成字符串,以便求小数位长度

	// 补足小数位到指定的位数
	while(cents.length < cent)
		cents = "0" + cents;

	if(isThousand) {
		// 对整数部分进行千分位格式化.
		for(var i = 0; i < Math.floor((num.length - (1 + i)) / 3); i++)
			num = num.substring(0, num.length - (4 * i + 3)) + ',' + num.substring(num.length - (4 * i + 3));
	}

	if(cent > 0)
		return(((sign) ? '' : '-') + num + '.' + cents);
	else
		return(((sign) ? '' : '-') + num);
}
/*
 * 方法名 strToDate
 * 方法的说明  日期格式化
 * @param str 日期毫秒数 flg 日期格式  1 yyyy-mm-dd 0 yyyy-mm 其他 yyyy-mm-dd hh:ii
 * @author作者 刘实
 * @date 日期 2018.07.04 
 */
function strToDate(str, flg) {
	var oDate = new Date(str);
	var month = oDate.getMonth() + 1;
	var day = oDate.getDate();
	var hour = oDate.getHours();
	var min = oDate.getMinutes();
	var ss = oDate.getSeconds();
	if(month < 10) {
		month = "0" + month;
	}
	if(day < 10) {
		day = "0" + day;
	}
	if(hour < 10) {
		hour = "0" + hour;
	}
	if(min < 10) {
		min = "0" + min;
	}
	if(ss < 10) {
		ss = "0" + ss;
	}
	if(flg == 1) {
		var oTime = oDate.getFullYear() + '-' + month + '-' + day;
	} else if(flg == 0) {
		var oTime = oDate.getFullYear() + '-' + month;
	} else if(flg == 2) {
		var oTime = hour + ":" + min;
	} else if(flg == 3) {
		var oTime = oDate.getFullYear() + '-' + month + '-' + day + " " + hour + ":" + min + ":" + ss;
	} else if(flg == 4) {
		var oTime = oDate.getFullYear() + '' + month + '' + day + "" + hour + "" + min;
	} else {
		var oTime = oDate.getFullYear() + '-' + month + '-' + day + " " + hour + ":" + min;
	}
	return oTime;
};
/**
 * 方法名 layerClick
 * 方法的说明 外发登录窗口弹出
 * @param classId 绑定的classId,width 弹出长度窗口的宽度,height 弹出长度窗口的高度,flag 状态 1:html 2:url,urlDom url或者dom
 * @return void
 * @author作者 张立学
 * @date 日期 2018.07.05
 */
$.layerShowDiv = function(classId, width, height, flag, urlDom, jobcd) {
	if(String(width).match('%')) {
		var width = width;
	} else {
		var width = width + 'px';
	}
	if(String(height).match('%')) {
		var height = height;
	} else {
		var height = height + 'px';
	}
	$('.' + classId).on('click', function(e) {
		layer.open({
			type: flag,
			//title: ['取引先','font-size:16px;font-weight:bold;'],
			title: false,
			closeBtn: 0,
			shadeClose: true, //点击遮罩关闭层
			area: [width, height],
			//move:false,
			content: urlDom,
			success: function(layero, index) {
				if(flag == 1) {
					var closeBtn = layero.find('.iconfont.icon-guanbi,#closeBtnInput');
					//var closeBtn1 = layero.find('#deal');

				} else {
					var iframeWin = window[layero.find('iframe')[0]['name']];
					var closeBtn = layer.getChildFrame('.iconfont.icon-guanbi,#closeBtnInput', index);
					//var closeBtn1 = layer.getChildFrame('#deal', index);

				}
				closeBtn.on('click', function() {
					layer.close(index);
				})
				if(classId == "cost_handle1") {
					recHandle();
				}
				if(classId == "cost_handle2") {
					recHandle();
				}
				if(classId == "pay_handle") {
					payHandle();
				}
				if(classId == "pay_handle2") {
					payHandle();
				}
				if(classId == "icon-i") {
					var c_s = iframeWin.$('#cost_list');
					selCostInfo(jobcd, c_s, iframeWin);
				}
				if(classId == "icon-tianjia1") {
					pingZhengFlag = false;
				}
				if(classId == "add_tax_bus") {
					var val = recoveryNumber(parent.$("#sale_tax").text());
					$(".sale-border").find("span.numberbox").css("border-color", "#cccccc");
					$(".sale-border").find("span.numberbox").next('span').css("border-color", "#cccccc");
					$(".sale-border").tooltip('destroy');
					//用于实时更新消费税编辑小弹窗当中的单位   原本是在页面上  写死的 
					layero.find(".sale_person_code").text(sessionStorage.getItem("localMoneyCode"));
					layero.find(".edit-tax").numberbox('setValue', val);
				}
				if(classId == "add_tax_price_on") {
					var val = recoveryNumber(parent.$("#cost_tax").text());
					$(".price-border").find("span.numberbox").css("border-color", "#cccccc");
					$(".price-border").find("span.numberbox").next('span').css("border-color", "#cccccc");
					$(".price-border").tooltip('destroy');
					layero.find(".edit-tax-price").numberbox('setValue', val);
				}
				if(classId == 'zjM' || classId == 'zj' || classId == 'lable-add' || classId == 'label-add-T') {
					$('#lable-input').removeClass("border_red");
					$('#lable-input').next('span').removeClass('border_red_left');
					$('#lable-input').closest('div').tooltip('destroy');
				}
				//				closeBtn1.on('click', function() {
				//					layer.close(index);
				//				})
				
				if(classId == 'sum_report_btn'){
					//$('#endDate').datebox('setValue',initDate());
				}
				if (classId == 'createInvoiceNo'){
					var rowDataAll = $('#job_login_list').datagrid('getSelected');
					var flagRedStar = rowDataAll["jobInvoiceFlg"];
					if (flagRedStar == 1){
						$('#menu_pop_title').addClass('red_star');
						$('#invoiceNo').addClass('required_flag');
					}else {
						$('#menu_pop_title').removeClass('red_star');
                        $('#invoiceNo').removeClass('required_flag');
					}
				}

				delete_validate_red();
			}
		});
	})
}
/**
 * 方法名 textOverhidden
 * 方法的说明 文字弹出显示和隐藏
 * @return void
 * @author作者 张立学
 * @date 日期 2018.07.05
 */
function textOverhidden() {
	//文字超出事件
	$(document).on('click', '.iconfont.icon-xiala', function() {
		if($(this).closest('.text-center').hasClass('rotate-90')) {
			$(this).closest('.text-center').siblings('.job_text').removeClass('over-anim');
			$(this).closest('.text-center').removeClass('rotate-90');
		} else {
			$(this).closest('.text-center').siblings('.job_text').addClass('over-anim');
			$(this).closest('.text-center').addClass('rotate-90');
		}
	})
	//文字超出
	$('.job_text').each(function(index, ele) {
		//		if(!$(this).hasClass('big') || ($(this).closest('div.form-group').width() == 366)){
		//			$(this).closest('div.form-group').width($('#content>.row>.col-md-8').width()*0.45);
		//		}		
		var hei = $(this).find('pre').height();
		$(this).nextAll('hr').addClass('hidden');
		if(hei > 60) {
			$(this).addClass('over-anim');
			$(this).nextAll('hr').removeClass('hidden');
			$(this).nextAll('.rotate-90').removeClass('hidden');
		}
	})
	//	$('textarea.form-control').each(function(index,ele){
	//		var width = $(this).width();
	//		if(width == 356){
	//			$(this).width($('#content>.row>.col-md-8').width()*0.45 - 10);
	//		}
	//	})
}

/**
 * 方法名 textOverhidden
 * 方法的说明 入金处理
 * @return void
 * @author作者 马有翼
 * @date 日期 2018.08.07
 */
function recHandle() {
	//入金完了checkbox监听
	$('#rec_flg').change(function() {
		//添加所需要执行的操作代码
		if($("#rec_flg").prop("checked") == true) {
			var rec_date = $("#rec_date").datebox('getValue');
			$("#rec_date").datebox({
				disabled: false
			});
			$("#rec_date").datebox('setValue', rec_date);
			$('#deleteRecdate').prop("checked", false); //时间清空checkbox取消选中
			$('#deleteRecdate').attr("disabled", true); //时间清空checkbox灰化
			$("#rec_date").addClass("date-box-required"); //入金完了时,入金时间必填

		} else {
			$('#deleteRecdate').attr("disabled", false); //时间清空checkbox灰化
			$("#rec_date").removeClass("date-box-required"); //入金完了取消选中,入金时间取消必填
			$("#rec_date").next('span').css('border-color', "#cccccc");
            $("#rec_date").next('span').tooltip('destroy');
		}
	});
	$('#deleteRecdate').change(function() {
		if($("#deleteRecdate").prop("checked") == true) {
			$("#rec_date").datebox('setValue', '');
			$("#rec_date").datebox({
				disabled: true
			});
		} else {
			$("#rec_date").datebox({
				disabled: false
			});
		}
	});
	//入金处理
	var rowDataAll = $('#job_login_list').datagrid('getSelections'); //获取所有选择的数据
	rowDataAllLength = rowDataAll.length;
	if(rowDataAllLength > 1) { //多选
		var numT = 0;
		var numF = 0;
		for(var i = 0; i < rowDataAllLength; i++) {
			var recflg = rowDataAll[i]['recflg']
			if(recflg == "1") { //已经入金
				numT = numT + 1
			} else if(recflg == "0") {
				numF = numF + 1
			}
		}
		if(numT == rowDataAllLength) { //如果已入金条数总和，等于选中的条数
			//初始化入金日期
			var recdate = "";
			//全是已经入金
			$("#rec_date").datebox({
				disabled: false
			});
			
			//2019-11-04 判断日期是否相等肉有一个不等则不显示入金日期
			for(var i = 1; i < rowDataAllLength; i++) {
				if(rowDataAll[i]['recdate']!=rowDataAll[i-1]['recdate']){
					recdate = ""
					break;
				}
				else{
					recdate = rowDataAll[i]['recdate']
				}
			}
			$.layerShowDiv('cost_handle', 400, 'auto', 1, $('.rujin'));
			$('#rec_flg').prop("checked", true); //选中
			$('#deleteRecdate').attr("disabled", true); //时间清空checkbox灰化
			$('#recremark').attr("disabled", true); //备考不可用
			$('#recremark').val('');
			$("#rec_date").datebox('setValue', recdate);
			$("#rec_date").addClass("date-box-required"); //入金完了时,入金时间必填
			$("#rec_date").next('span').css('border-color', "#cccccc");
            $("#rec_date").next('span').tooltip('destroy');
			$('#deleteRecdate').prop("checked", false); //时间清空checkbox取消选中
			
		} else if(numF == rowDataAllLength) {
			//初始化入金日期
			var recdate = "";
			$("#rec_date").datebox({
				disabled: false
			});
			//2019-11-04 判断日期是否相等肉有一个不等则不显示入金日期
			for(var i = 1; i < rowDataAllLength; i++) {
				if(rowDataAll[i]['recdate']!=rowDataAll[i-1]['recdate']){
					recdate = ""
					break;
				}
				else{
					recdate = rowDataAll[i]['recdate']
				}
			}
			$('#recremark').val('');
			$('#recremark').attr("disabled", true); //备考不可用
			$("#rec_date").datebox('setValue', recdate);
			$('#deleteRecdate').prop('checked', false); //时间清空checkbox取消选中
			$('#deleteRecdate').prop('disabled', false); //时间清空disable取消
			$("#rec_flg").prop('checked', false);
			$("#rec_date").removeClass("date-box-required"); //入金完了取消选中,入金时间取消必填
			$("#rec_date").next('span').css('border-color', "#cccccc");
            $("#rec_date").next('span').tooltip('destroy');
		}
		/*else {
			//有入金也有未入金的
			showErrorHandler("REC_ERROR", "info", "info");
		}*/
	} else { //单条入金
		$("#rec_date").datebox({
			disabled: false
		});
		var rowDataAll = $('#job_login_list').datagrid('getSelected'); //获取所有选择的数据
		var recflg = rowDataAll['recflg'];
		var recdate = rowDataAll['recdate'];
		var recremark = rowDataAll['recremark'];
		$("#rec_date").datebox('setValue', recdate);
		$('#recremark').val(recremark);
		$('#recremark').attr("disabled", false); //备考可用
		$('#deleteRecdate').prop("checked", false); //时间清空checkbox取消选中
		if(recflg == "1") {
			$('#rec_flg').prop("checked", true); //选中
			$('#deleteRecdate').attr("disabled", true); //时间清空checkbox灰化
			$("#rec_date").addClass("date-box-required"); //入金完了取消选中,入金时间取消必填
			$("#rec_date").next('span').css('border-color', "#cccccc");
            $("#rec_date").next('span').tooltip('destroy');
		} else {
			$('#rec_flg').prop("checked", false); //不选中
			$('#deleteRecdate').attr("disabled", false); //时间清空checkbox灰化
			$("#rec_date").removeClass("date-box-required"); //入金完了取消选中,入金时间取消必填
			$("#rec_date").next('span').css('border-color', "#cccccc");
            $("#rec_date").next('span').tooltip('destroy');
		}
	}
}
/**
 * 方法名 textOverhidden
 * 方法的说明 支付处理
 * @return void
 * @author作者 
 * @date 日期 2018.08.09
 */
function payHandle() {
	//入金完了checkbox监听
	$('#payflg').change(function() {
		//添加所需要执行的操作代码
		if($("#payflg").prop("checked") == true) {
			var rec_date = $("#order_date").datebox('getValue');
			$("#order_date").datebox({
				disabled: false
			});
			$("#order_date").datebox('setValue', rec_date);
			$('#paycanceldate').prop("checked", false); //时间清空checkbox取消选中
			$('#paycanceldate').attr("disabled", true); //时间清空checkbox灰化
			$("#order_date").addClass("date-box-required"); //入金完了时,入金时间必填

		} else {
			$('#paycanceldate').attr("disabled", false); //时间清空checkbox灰化
			$("#order_date").removeClass("date-box-required"); //入金完了取消选中,入金时间取消必填
			$("#order_date").next('span').css('border-color', "#cccccc");
			$("#order_date").next('span').tooltip('destroy');
		}
	});
	$('#paycanceldate').change(function() {
		if($("#paycanceldate").prop("checked") == true) {
			$("#order_date").datebox('setValue', '');
			$("#order_date").datebox({
				disabled: true
			});
		} else {
			$("#order_date").datebox({
				disabled: false
			});
		}
	});
	//入金处理
	var rowDataAll = $('#cost_list_Two').datagrid('getSelections'); //获取所有选择的数据
	rowDataAllLength = rowDataAll.length;
	if(rowDataAllLength > 1) { //多选
		var recflg = rowDataAll[0]['payflgcd'];
        if(recflg == "1") {
                $('#payflg').prop("checked", true); //选中
        }else{
                $('#payflg').prop("checked", false);
        }
		var numT = 0;
		var numF = 0;
		var recDate = "";
        var recDateFit = true;
		for(var i = 0; i < rowDataAllLength; i++) {
			var recflg = rowDataAll[i]['payflgcd']
			if(recDate == "")
            {
                    recDate = rowDataAll[i]['paydate'];
            }else{
                    if(recDate != rowDataAll[i]['paydate']){
                            recDateFit = false;
                    }
            }
			if(recflg == "1") { //已经入金
				numT = numT + 1
			} else if(recflg == "0") {
				numF = numF + 1
			}
		}
		if(numT == rowDataAllLength) { //如果已入金条数总和，等于选中的条数
			//全是已经入金
			//$.layerShowDiv('cost_handle', 400, 'auto', 1, $('.rujin'));
			$("#order_date").datebox({
				disabled: false
			});
			$('#payflg').prop("checked", true); //选中
			$('#paycanceldate').attr("disabled", true); //时间清空checkbox灰化
			$('#payremark').attr("disabled", true); //备考不可用
			$('#payremark').val('');
			$("#order_date").addClass("date-box-required"); //入金完了时,入金时间必填
			$("#order_date").datebox('setValue', '');
			$('#paycanceldate').prop("checked", false);
			$("#order_date").next('span').css('border-color', "#cccccc");
			$("#order_date").next('span').tooltip('destroy');
		} else if(numF == rowDataAllLength) {
			$("#order_date").datebox({
				disabled: false
			});
			$('#payremark').val('');
			$('#payremark').attr("disabled", true); //备考不可用
			$("#order_date").datebox('setValue', '');
			$('#paycanceldate').prop("checked", false);
			$('#paycanceldate').prop('disabled', false); //时间清空disable取消
			$("#order_date").removeClass("date-box-required"); //入金完了取消选中,入金时间取消必填
			$("#order_date").next('span').css('border-color', "#cccccc");
			$("#order_date").next('span').tooltip('destroy');

		}

		/*else {
			//有入金也有未入金的
			showErrorHandler("REC_ERROR", "info", "info");
		}*/
		if(recDateFit)
        {
                $("#order_date").datebox('setValue', recDate);
        }
	} else { //单条入金
		$("#order_date").datebox({
			disabled: false
		});
		var rowDataAll = $('#cost_list_Two').datagrid('getSelected'); //获取所有选择的数据
		var recflg = rowDataAll['payflgcd'];
		var recdate = rowDataAll['paydate'];
		var recremark = rowDataAll['payremark'];
		$("#order_date").datebox('setValue', recdate);
		$('#payremark').val(recremark);
		$('#payremark').attr("disabled", false); //备考可用
		$('#paycanceldate').prop("checked", false);
		if(recflg == "1") {
			$('#payflg').prop("checked", true); //选中
			$('#paycanceldate').attr("disabled", true); //时间清空checkbox灰化
			$("#order_date").addClass("date-box-required"); //入金完了取消选中,入金时间取消必填
			$("#order_date").next('span').css('border-color', "#cccccc");
			$("#order_date").next('span').tooltip('destroy');
		} else {
			$('#payflg').prop("checked", false); //不选中
			$('#paycanceldate').attr("disabled", false); //时间清空checkbox灰化
			$("#order_date").removeClass("date-box-required"); //入金完了取消选中,入金时间取消必填
			$("#order_date").next('span').css('border-color', "#cccccc");
			$("#order_date").next('span').tooltip('destroy');
		}
	}
}
/**
 * 方法名 clone
 * 方法的说明 克隆对象
 * @return void
 * @author作者 付清泉
 * @date 日期 2018.07.05
 */
var clone = function(obj) {
	if(obj === null) return null
	if(typeof obj !== 'object') return obj;
	if(obj.constructor === Date) return new Date(obj);
	var newObj = new obj.constructor(); //保持继承链
	for(var key in obj) {
		if(obj.hasOwnProperty(key)) { //不遍历其原型链上的属性
			var val = obj[key];
			newObj[key] = typeof val === 'object' ? arguments.callee(val) : val; // 使用arguments.callee解除与函数名的耦合
		}
	}
	return newObj;
};

/**
 * 方法名  isHavePower
 * 方法的说明    对比权限
 * @return boolean
 * @author作者 刘实
 * @param uList 登陆者拥有的权限  cList 要对比的权限数组
 * @date 日期 2018.08.21
 */
function isHavePower(uList, cList) {
	var bl = false;
	if(uList!=null){
		for(var i = 0; i < uList.length; i++) {
			for(var k = 0; k < cList.length; k++) {
				if(uList[i].nodeID == cList[k]) {
					bl = true;
					break;
				}
			}
		}
	}
	
	return bl;
}
/**
 * 方法名  selCostInfo
 * 方法的说明   查询job的原价列表
 * @return boolean
 * @author作者 马有意
 * @param jobcd job编号  cost_list 
 * @date 日期 2018.08.25
 */
function selCostInfo(jobcd, cost_list, iframeWin) {
	//	console.log(cost_list);
	file_i18n();
	var path = $.getAjaxPath() + "getCostList";
	var pars = {
		costListVo: {
			jobcd: jobcd,
			searchType: 1
		}
	};
	$.ajax({
		url: path,
		data: JSON.stringify(pars),
		headers: {
			"requestID": $.getRequestID(),
			"requestName": $.getRequestNameByJcZH()
		},
		success: function(data) {
			if(data[$.getRequestMetaName()].result == $.getRequestStatusName()) {
				$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
				//数据填充到表格中
				var dataColumns = data.data['costListVo'];
				for(var i = 0; i < dataColumns.length; i++) {
					if(dataColumns[i]['confirmdate'] == undefined || dataColumns[i]['confirmdate'] == null) {
						dataColumns[i]['confirmdate'] = "";
					}
				}
				initDataGridHandler(cost_list,9999999, null, 'top', false, '', dataColumns, true);
				var sum = 0;
				for(var i = 0; i < dataColumns.length; i++) {
					sum += Number(dataColumns[i]['amt']);
				}
				for(var k = 0; k < dataColumns.length; k++) {
					dataColumns[k]['sumCost'] = formatNumber(sum);
					dataColumns[k]['saleamt'] = formatNumber(dataColumns[k]['saleamt']);
					dataColumns[k]['confirmdate'] = new Date(dataColumns[k]['confirmdate']).format_extend('yyyy-MM-dd');
					dataColumns[k]['payreqdate'] = new Date(dataColumns[k]['payreqdate'] == null ? "" : dataColumns[k]['payreqdate']).format_extend('yyyy-MM-dd');
				}
				var joblist = {};
				if(dataColumns.length > 0) {
					var joblist = loopFunForjobdetail(dataColumns, [data.data['costFlg']], ['statuscd']);
				}
				for(var i = 0; i < joblist.length; i++) {
					joblist[i].amt = formatNumber(joblist[i].amt);
				}
				cost_list.datagrid("loadData", joblist);
				//表格内多国语言切换
				var dom = iframeWin.$('.datagrid-header .datagrid-htable tr span:not(.datagrid-sort-icon)');
				dom.addClass('i18n');
				dom.eq(0).html(part_language_change_new('cost_list_No'));
				dom.eq(1).html(part_language_change_new('cost_list_tickto'));
				dom.eq(2).html(part_language_change_new('suppliersRegistration_type'));
				dom.eq(3).html(part_language_change_new('suppliersList_contractor'));
				dom.eq(4).html(part_language_change_new('jobregistration_subject'));
				dom.eq(5).html(part_language_change_new('cost_list_price'));
				dom.eq(6).html(part_language_change_new('cost_list_type'));
				dom.eq(7).html(part_language_change_new('cost_list_date'));
			} else {
				showErrorFunHandler(data[$.getRequestMetaName()].result, "ERROR", "ERROR");
			}
		},
		error: function(data) {
			showErrorHandler("CONNECTION_SERVER_ERROR", "ERROR", "ERROR");
		}
	});
}

/**
 * 方法名  getQueryString
 * 方法的说明   获取url中的值
 * @return string
 * @author作者 付清泉
 * @param name 参数名称
 * @date 日期 2018.08.25
 */
function getQueryStringValue(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	var r = window.location.search.substr(1).match(reg);
	if(r != null) return unescape(r[2]);
	return null;
}

/**
 * 方法名  inArray
 * 方法的说明   判断元素是否在数组中
 * @return bool
 * @author作者 张立学
 * @param name 参数名称
 * @date 日期 2018.08.27
 */
Array.prototype.inArray = function(arr, item) {

	for(var i = 0; i < arr.length; i++) {

		if(arr[i] == item) {

			return true;

		}

	}

	return false;

}
/**
 * @author作者 张立学
 * @date 日期 2018.08.28
 */
$(function() {
	$('input').attr('autocomplete', 'off');
})

/*
 * 
 *方法名  listSetChoseLable
 * 方法的说明   一览页面lable列表加载默认选中
 * @return 
 * @author作者 刘实
 * @param choseLableList 参数名称  需要勾选标签的数组
 * @date 日期 2018.08.30
 */
function listSetChoseLable(choseLableList) {
	var mySelectVal = $(".mySelect-option").children();
	var list = $(".mySelect-option").find('input');
	for(var i = 0; i < list.length; i++) {
		list.eq(i).prop('checked', false);
	}

	for(var i = 0; i < choseLableList.length; i++) {
		for(var k = 0; k < mySelectVal.length; k++) {
			if(mySelectVal.eq(k).attr("data-value") == choseLableList[i]) {
				mySelectVal.eq(k).removeClass("selected");
				mySelectVal.eq(k).click();
			}
		}
	}
}
/**
 * 方法名  changeDateByRow
 * 方法的说明   不加载后台,直接更换表格某行,某列的数据
 * @return bool
 * @author作者 马有翼
 * @param name 参数名称
 * @date 日期 2018.08.29
 */
function changeDateByRow(tableid, rowName, value) {
	var rows = $('#' + tableid).datagrid("getSelections");  //获取你选择的所有行 
	  //循环所选的行,更改选中行dategrid中的原价完了状态为已完了
	 
	for(var i = 0; i < rows.length; i++) {  
		var rowIndex = $('#' + tableid).datagrid('getRowIndex', rows[i]); //获取某行的行号
		rows[i].lable = value;
		var rownew = rows[i];
		$('#' + tableid).datagrid('updateRow', {
			index: rowIndex,
			row: rownew
		})
		$('#' + tableid).datagrid('refreshRow', rowIndex);
	}
}
/**
 * 方法名  changeDateByRow
 * 方法的说明   判断多选表格状态是否相同
 * @return boolean 
 * @author作者 马有翼
 * @param name 参数名称
 * @date 日期 2018.08.29
 */
function checkStatusForRows(rowDateAll, rowItemName) {
	var cont = '';
	var flag = true;
	var rows = rowDateAll;
	for(var i = 0; i < rows.length; i++) {
		if(i == 0) {
			cont = rows[i][rowItemName];
		}
		if(i > 0 && cont != rows[i][rowItemName]) {
			flag = false;
			break;
		}
	}
	return flag;
}
/**
 * 方法名  listLableAdd
 * 方法的说明   一览页面标签追加
 * @return 新追加的id
 * @author作者 刘实
 * @param name 参数名称 tableid 表格ID flg 1原价 0job lableType标签类型 1原价 0 job
 * @date 日期 2018.08.29
 */
function listLableAdd(tableid, lableType, flg) {
	var rows = $('#' + tableid).datagrid('getSelections');
	var newlablename = $(".new_lable").val();
	var newlablelevel = $('.lable-level-switch').find('.active').find('input').val();
	if(newlablename == "" || newlablename == null || newlablename == undefined) {
		$('#lable-input').addClass("border_red");
		$('#lable-input').next('span').addClass('border_red_left');
		validate($('#lable-input').closest('div'), part_language_change_new('NODATE'));
		showErrorHandler("NOT_EMPTY", "info", "info");
		return;
	} else {
		$('#lable-input').removeClass("border_red");
		$('#lable-input').next('span').removeClass('border_red_left');
		$('#lable-input').closest('div').tooltip('destroy');
	}
	var incostno;
	var incostnolevel;
	var jobcd;
	var arr = [];
	for(var i = 0; i < rows.length; i++) {
		

		if(flg == 1) {
			if(rows[i].discd == "001") {
				incostno = rows[i].costno;
				incostnolevel = 0;
			} else if(rows[i].discd == "002") {
				if(rows[i].oldinputno!=""){
					incostno = rows[i].oldinputno
				}else{
					incostno = rows[i].inputno;
				}
				
				incostnolevel = 2;
			} else {
				if(rows[i].oldinputno!=""){
					incostno = rows[i].oldinputno
				}else{
					incostno = rows[i].inputno;
				}
				incostnolevel = 3;
			}
			var selectJob = {
				incostno: incostno,
				incostnolevel: incostnolevel,
				jobcd: null,
				inputno:rows[i].jobcd,
			}
		}

		if(flg == 0) {
			var selectJob = {
				incostno: null,
				incostnolevel: null,
				jobcd: rows[i].jobcd
			}
		}

		arr.push(selectJob);
	}
	//插入mst的标签对象

	var lablemst = {
		lable: {
			labletext: newlablename,
			lablelevel: newlablelevel,
			labletype: lableType
		}
	}
 
	//arr为绑定数据
	lablemst['bindlable'] = arr;
	var mm = {
		listlable: lablemst
	}
	var url = $.getAjaxPath() + "listLableAdd";
	$.ajax({
		url: url,
		data: JSON.stringify(mm),
		headers: {
			"requestID": $.getRequestID(),
			"requestName": $.getRequestNameByMst()
		},
		success: function(data) {
			if(data[$.getRequestMetaName()].result == $.getRequestStatusName()) {
				$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
				if(data[$.getRequestDataName()].length != null && data[$.getRequestDataName()] != 250) {
					//获取新追加标签的 名,id,类型
					var newlablename = $(".new_lable").val();
					var newlablelevel = $('.lable-level-switch').find('.active').find('input').val();
					var newlableId = data[$.getRequestDataName()];
					var lableList = SelectObj.getMstLableList();
					if(newlablelevel == 1) {
						lableList.splice(0, 0, {
							"labletext": newlablename,
							"lablelevel": newlablelevel,
							"lableid": newlableId
						});
					} else {
						lableList.push({
							"labletext": newlablename,
							"lablelevel": newlablelevel,
							"lableid": newlableId
						});
					}
                        var newLabelObj={};
					    newLabelObj['newIdArr'] = [newlableId];
						newLabelObj['newTextArr'] = [newlablename];
						if(newlablelevel=="1"){
							newLabelObj['newLevelArr'] = ["s2"];
						}else{
							newLabelObj['newLevelArr'] = ["s1"];
						}
						
					SelectObj.mstLableList = lableList;
					labelToMySelect(SelectObj.getMstLableList());
					//将追加成功的标签 文本 还有 id移到表格 newlablename
					//changeDateByRows(tableid, ["lable", "lableid"], [newlablename, newlableId], true);
					 //添加lable数据
					    
					
					lableSetToMyTable(tableid,["lable", "lableid"],"","",newLabelObj);
					var index = layer.index;
					layer.close(index);
					showErrorHandler("EXECUTE_SUCCESS", "SUCCESS", "SUCCESS");

				} else if(data[$.getRequestDataName()] == 250) {
					//                    getRedc($(".new_lable"),true);
					$(".new_lable").addClass('border_red');
					$(".new_lable").next('span').addClass('border_red_left');
					validate($(".new_lable").closest('div'), part_language_change_new('NODATE'));
					showErrorHandler("ALREAD_HAVE_SAME_TEXT", "info", "info");

				} else {
					showErrorHandler(data[$.getRequestDataName()], "ERROR", "ERROR");
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

/**
 * 方法名  listLableInit
 * 方法的说明   一览页面标签设定初始化
 * @return 
 * @author作者 刘实
 * @param name 参数名称 tableid 表格ID
 * @date 日期 2018.08.29
 */
function listLableInit(tableid) {
	window.arrJi = [];
	var rows = $('#' + tableid).datagrid('getSelections');
	//lableID去重之后的并集
	var arr = [];
	//选中job的lable二维数组
	var job = [];
	for(var i = 0; i < rows.length; i++) {
		//
		if(rows[i].lableid != null && rows[i].lableid != '') {

			job.push(rows[i].lableid.split("  "));
		} else {
			job.push("");
		}
	}
	for(var k = 0; k < job.length; k++) {
		for(var m = 0; m < job[k].length; m++) {
			if(![].inArray(arr, job[k][m])) {
				arr.push(job[k][m]);
			}
		}
	}
	var lableSame = lableIntersection(arr, job);
	SelectObj.mstLableSet = null;
	SelectObj.mstLableSet = lableSame;
	listSetChoseLable(lableSame);
}
//挑选 选中job的lable交集
function lableIntersection(arr, job) {

	var flg = false;
	var dd = [];
	var flg2 = true;
	var arrFlg;
	for(var i = 0; i < arr.length; i++) {
		flg2 = true;
		arrFlg = [];

		for(var k = 0; k < job.length; k++) {
			flg = false;

			for(var m = 0; m < job[k].length; m++) {
				if(arr[i] == job[k][m]) {
					flg = true;
				}
			}
			arrFlg.push(flg);
		}

		for(var l = 0; l < arrFlg.length; l++) {
			if(!arrFlg[l]) {
				flg2 = false;
			}
		}

		if(flg2) {
			dd.push(arr[i]);
		}
	}
	return dd;
}
/**
 * 方法名  listLableSet
 * 方法的说明   一览页面标签设定进行数据库 绑定
 * @return 
 * @author作者 刘实
 * @param name 参数名称 tableid 表格ID flg 1原价 0job 
 * @date 日期 2018.08.29
 */
function listLableSet(tableid, flg) {
	var rows = $('#' + tableid).datagrid('getSelections');
	
	var a = $('.mySelect-option').find('input');
	var b = [];
	var arr = [];
	//要删除的lableid对象
	var c = [];
	var jobcd;
	var incostno;
	var incostnolevel;
	for(var i = 0; i < a.length; i++) {
		if(a.eq(i).prop('checked')) {
			var lableid = {
				lableid: a.eq(i).closest('.mySelect-option-li').attr('data-value'),
			}
			b.push(lableid);
		}
	}
	for(var m = 0; m < rows.length; m++) {
		
		if(flg == 1) {
			if(rows[m].discd == "001") {
				incostno = rows[m].costno;
				incostnolevel = 0;
			} else if(rows[m].discd == "002") {
				if(rows[m].oldinputno!=""){
					incostno =  rows[m].oldinputno;
				}else{
					incostno = rows[m].inputno;
				}
				
				incostnolevel = 2;
			} else {
				if(rows[m].oldinputno!=""){
					incostno =  rows[m].oldinputno;
				}else{
					incostno = rows[m].inputno;
				}
				incostnolevel = 3;
			}
			var selectJob = {
				incostno: incostno,
				incostnolevel: incostnolevel,
				jobcd: null,
				inputno:rows[m].jobcd,
			}
		}

		if(flg == 0) {
			var selectJob = {
				jobcd: rows[m].jobcd,
				incostno: null,
				incostnolevel: null,
			}
		}

		arr.push(selectJob);
	}
	var arrJi = window.arrJi == undefined ? [] : window.arrJi;
	//放要删除lable文本的数组  与要删除lableid的数组
	var lableDelTxt = [];
	var lableDelId = [];
	for(var i = 0; i < arrJi.length; i++) {
		lableDelTxt.push(arrJi[i]['text']);
		lableDelId.push(arrJi[i]['value']);
		var del = {
			lableid: arrJi[i]['value']
		}
		c.push(del);
	}
	var lablemst = {};
	lablemst['listlableid'] = b;
	lablemst['bindlable'] = arr;
	lablemst['listdellable'] = c;
	var ss = {
		listlable: lablemst
	}
	var url = $.getAjaxPath() + "listLableSet";
	$.ajax({
		url: url,
		data: JSON.stringify(ss),
		headers: {
			"requestID": $.getRequestID(),
			"requestName": $.getRequestNameByMst()
		},
		success: function(data) {
			//			console.log(data)
			if(data[$.getRequestMetaName()].result == $.getRequestStatusName()) {
				$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
				if(data[$.getRequestDataName()] == 1) {
					lableSetToMyTable(tableid, ['lable', 'lableid'], lableDelId, lableDelTxt);
					var index = layer.index;
					layer.close(index);
					showErrorHandler("EXECUTE_SUCCESS", "SUCCESS", "SUCCESS");

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
/**
 * 方法名  changeDateByRows
 * 方法的说明   一览页面标签追加成功后 将数据写回到表格
 * @return 
 * @author作者 刘实
 * @param name 参数名称 tableid 表格ID arrRowName 要操作的列名 数组  saveFlg 是否保留之前的标识
 *  arrValue 要放入的值数组  需要与 arrRowName对应  
 * @date 日期 2018.08.29
 */
function changeDateByRows(tableid, arrRowName, arrValue, saveFlg) {

	var rows = $('#' + tableid).datagrid("getSelections");  //获取你选择的所有行 

	for(var i = 0; i < rows.length; i++) {  
		var rowIndex = $('#' + tableid).datagrid('getRowIndex', rows[i]); //获取某行的行号
		for(var k = 0; k < arrRowName.length; k++) {
			var rowname = arrRowName[k];
			if(saveFlg && rows[i][rowname] != undefined && rows[i][rowname] != null && rows[i][rowname] != "") {

				rows[i][rowname] = rows[i][rowname] + "  " + arrValue[k];

			} else {
				//如果是制御标识 列 ,将标识+1
				if(rowname == 'paylockflg') {
					rows[i][rowname] = rows[i][rowname] + 1;
				} else {
					rows[i][rowname] = arrValue[k];
				}

			}

		}
		var newrow = rows[i];
		$('#' + tableid).datagrid('reload', {
			index: rowIndex,
			row: newrow
		})
		$('#' + tableid).datagrid('refreshRow', rowIndex);
	}
}

/**
 * 方法名  lableSetToMyTable
 * 方法的说明   一览页面标签设定将修改后的 标签结果显示到表格中
 * @return 
 * @author作者 刘实
 * @param name 参数名称 tableid 表格ID arrRowName 要更新的列名数组  
 *  arrDelID要删除的lableid数组 arrDelText 要删除的labletext数组
 * @date 日期 2018.08.29
 */
function lableSetToMyTable(tableid, arrRowName, arrDelID, arrDelText,newLabelObj) {

	var rows = $('#' + tableid).datagrid("getSelections");  //获取你选择的所有行 
	
	var arrSelect = newLabelObj;
	if(newLabelObj==undefined){
		arrSelect = getSetChose();
	}
	arrRowName.push("lableUse");
	arrRowName.push("lablelevel");
	if(arrDelID==undefined){
		arrDelID = [];
		arrSelect()
	}
	if(arrDelText==undefined){
		arrDelText=[];
	}
	for(var i = 0; i < rows.length; i++) {  
		var filtArrID = [];
		var filtArrText = [];
		var filtArrLevel = [];
		if(rows[i]['lableid']==""){
			rows[i]['lableid']=null;
		}
		var rowIndex = $('#' + tableid).datagrid('getRowIndex', rows[i]); //获取某行的行号
		//拆分选中的行的lable文本 与 lableid 获取数组
		var idArr = rows[i]['lableid'] == null ? [] : rows[i]['lableid'].split("  ");
		var textArr = rows[i]['lable'] == null ? [] : rows[i]['lable'].split("  ");
		var levelArr = rows[i]['lablelevel'] == null ? [] : rows[i]['lablelevel'].split("  ");
		var lableUseArr = rows[i]['lableUse'] == null ? [] : rows[i]['lableUse'].split("  ");
		//循环此行的lable数组 与 要删除的数组比对  如果不等 就拼加在 strId 与 strText 后面重新赋值给此行
		for(var b = 0; b < idArr.length; b++) {
			if(![].inArray(arrDelID, idArr[b])) {
				filtArrID.push(idArr[b]);
				// filtArrText.push(textArr[b]);
				filtArrText.push(lableUseArr[b]);
				filtArrLevel.push(levelArr[b]);
			}
		}

		//取并集之后去重
		var tempArr = arrSelect.newLevelArr;
		for(var n=0;n<tempArr.length;n++){
			if(tempArr[n]=="s2"){
				filtArrLevel.push("1");
			}else{
				filtArrLevel.push("0");
			}
			
		}
		var arrIdLast = array_remove_repeat_ID(filtArrID.concat(arrSelect.newIdArr),arrSelect);
		var arrTextLast = array_remove_repeat(filtArrText.concat(arrSelect.newTextArr),filtArrLevel,arrSelect,filtArrID.concat(arrSelect.newIdArr));
		//var arrTextLast = array_remove_repeat(filtArrText.concat(arrSelect.newTextArr),filtArrLevel,arrSelect);
		var arrTextLastStr="";
		for(var h=0;h<arrTextLast.length;h++){
			arrTextLastStr+=arrTextLast[h].temp;
		}
		var flg = true;
		var lableKey = $('#lable').val();
		if(tableid == "job_login_list") {
			lableKey = $('#label_text').val();
		}
		if(lableKey != "") {
			if(arrTextLastStr.toUpperCase().indexOf(lableKey.toUpperCase())<0) {
				flg = false;
			}
		}
		if(flg) {
			var arrValue = [];
			var strText = "";
			var lableUseText = "";
			var lableLevel = "";
			var strId = "";
			
			//将最终结果拼接 成为字符串
			for(var m = 0; m < arrIdLast.length; m++) {
				if(strId == "") {
					strId = arrIdLast[m];
					if(arrTextLast[m].tempLevel==1){
						
						strText = "<span class='manager'>"+arrTextLast[m].temp+"</span>";
					}else{
						strText = "<span class='requestor'>"+arrTextLast[m].temp+"</span>";
					}
					lableLevel = arrTextLast[m].tempLevel;
					lableUseText = arrTextLast[m].temp;
				} else {
					strId += "  " + arrIdLast[m];
					if(arrTextLast[m].tempLevel==1){
						strText  +='  '+"<span class='manager'>"+arrTextLast[m].temp+"</span>";
					}else{
						strText +='  '+"<span class='requestor'>"+arrTextLast[m].temp+"</span>";
					}
					lableLevel += "  " + arrTextLast[m].tempLevel;
					lableUseText += "  " + arrTextLast[m].temp;
				}
			}
			arrValue.push(strText, strId,lableUseText,lableLevel);
			
			for(var k = 0; k < arrRowName.length; k++) {
				var rowname = arrRowName[k];
				rows[i][rowname] = arrValue[k];
			}

			var newrow = rows[i];
			$('#' + tableid).datagrid('reload', {
				index: rowIndex,
				row: newrow
			})
			$('#' + tableid).datagrid('refreshRow', rowIndex);
		}else{
			removeLableRows(tableid,rowIndex);
		}

	}


}

function removeLableRows(tableid,rowIndex) {
	$('#' + tableid).datagrid('deleteRow', rowIndex);
	
	var data = $('#' + tableid).datagrid('getRows');
	if(data.length == 0) {
		$('.switch_table').css('display', 'none');
		$('.switch_table + .switch_table_none').removeClass('hidden');
		$('.switch_table_none div').text(part_language_change_new("TABLE_CHECK_NO"));
	}

}
/**
 * 方法名  getSetChose
 * 方法的说明   获取一览页面 点击设定时 的选中lable 
 * @return  arr  存有 选中ID与 选中text数组的对象
 * @author作者 刘实
 * @param 
 * @date 日期 2018.08.29
 */
function getSetChose() {
	//获取 最后要加入到列表中的 id 和 text
	var arr = {};
	var list = $(".mySelect-option").children();
	var newIdArr = [];
	var newTextArr = [];
	var newLevelArr= [];
	for(var i = 0; i < list.length; i++) {
		if(list.eq(i).find('input').prop('checked')) {
			newIdArr.push(list.eq(i).attr("data-value"));
			newTextArr.push(list.eq(i).find('span').text());
			newLevelArr.push(list.eq(i)[0].className.split(" ")[1]);
		}
	}
	arr['newIdArr'] = newIdArr;
	arr['newTextArr'] = newTextArr;
	arr['newLevelArr'] = newLevelArr;
	return arr;
}

/**
 * 方法名  array_remove_repeat
 * 方法的说明   数组去重
 * @return r 去重之后的数组
 * @author作者 刘实
 * @param a 数组
 * @date 日期 2018.08.29
 */
function array_remove_repeat(a,b,c,e) { // 去重
    var r  = [];
    for(var i = 0; i < a.length; i++) {
            var flag = true;
            var temp = a[i];
            var tempLevel ;
            var tempId = e[i];
            var lableRow;
            lableRow={};
            tempLevel= b[i];
            for(var j = 0; j < r.length; j++) {
                    if(r[j]==undefined){
                            r[j] ={};
                    }
                    if(temp == r[j].temp && tempLevel==r[j].tempLevel && tempId==r[j].tempId) {
                            flag = false;
                            break;
                    }
            }
            if(flag){
                    lableRow = {"temp":temp,"tempLevel":tempLevel,"tempId":tempId};
                    r.push(lableRow);

            }
    }
    /*
    for(var i=0;i<c.newTextArr.length;i++)
    {
            for(var j=0;j<r.length;j++)
            {
                    if(c.newTextArr[i] == r[j].temp)
                    {
                            e.push(r[j]);
                            break;
                    }
            }
    }
    return e;
    */
    return r;
}
function array_remove_repeat_ID(a,b)
{
	var r  = [];
	var c = [];
	for(var i = 0; i < a.length; i++) {
		var flag = true;
		var temp = a[i];
		for(var j = 0; j < r.length; j++) {
			if(temp === r[j]) {
				flag = false;
				break;
			}
		}
		if(flag){
			r.push(temp);
		}
	}
	/*
	for(var i=0;i<b.newIdArr.length;i++)
	{
		for(var j=0;j<r.length;j++)
		{
			if(b.newIdArr[i] == r[j])
			{
				c.push(r[j]);
				break;
			}
		}
	}
	return c;
	*/
	return r;
}
/*
 * 方法名  filterLable
 * 说明 过滤lable列表
 * 参数 str 要检索的关键字
 * @author作者 刘实
 * @date 日期 2018.08.29
 */
function filterLable(str, flg) {
	var lableList;
	if(flg == 1) {
		lableList = SelectObj.getMstLableList();
	} else {
		lableList = SelectObj.setLableList;
	}

	var arr = [];
	if(str == "") {
		labelToMySelect(lableList);
		return;
	}
	for(var i = 0; i < lableList.length; i++) {
		if(lableList[i]['labletext'].match(str) != null) {
			arr.push(lableList[i]);
		}
	}
	labelToMySelect(arr);
}
/*
 * 方法名  easyUiLanguageDatagrid
 * 说明  datagrid easyUi语言切换
 * 参数 language string
 */
function easyUiLanguageDatagrid(language) {
	//easyui datebox语言切换
	$('span.datebox a.textbox-icon').click();
	$('.datebox-button table tr td a.datebox-button-a[datebox-button-index=1]').click();
	$('.datebox input.textbox-text').blur();
	//按钮语言修改
	$('.datebox-button table tr td a.datebox-button-a').each(function(index, ele) {
		if($(this).attr('datebox-button-index') == 0) {
			$(this).text(part_language_change_new('boxtoday'));
		} else if($(this).attr('datebox-button-index') == 1) {
			$(this).text(part_language_change_new('boxclose'));
		}
	})
}
/*
 * 方法名  easyUiLanguageDatagrid2
 * 说明  datagrid easyUi语言切换
 * 参数 language string
 */
function easyUiLanguageDatagrid2(language) {
	//easyui datebox语言切换
	$('span.datebox a.textbox-icon').click();
	$('.datebox-button table tr td a.datebox-button-a[datebox-button-index=2]').click();
	$('.datebox input.textbox-text').blur();
	//按钮语言修改
	$('.datebox-button table tr td a.datebox-button-a').each(function(index, ele) {
		if($(this).attr('datebox-button-index') == 0) {
			$(this).text(part_language_change_new('boxtoday'));
		} else if($(this).attr('datebox-button-index') == 1) {
			$(this).text(part_language_change_new('boxclear'));
		} else {
			$(this).text(part_language_change_new('boxclose'));
		}
	})
}
/*
 * 方法名  easyUiLanguage
 * 说明  eeasyUi语言切换
 * 参数 language string
 */
function easyUiLanguage(language) {
	supp_ltd_language(language);
	//easyui datebox语言切换
	$('span.datebox a.textbox-icon').click();
	//$('.datebox-button table tr td a.datebox-button-a[datebox-button-index=1]').click();
	$('.datebox-button table tr td a.datebox-button-a[datebox-button-index=2]').click();
	$('.datebox input.textbox-text').blur();
	//按钮语言修改
	$('.datebox-button table tr td a.datebox-button-a').each(function(index, ele) {
		if($(this).attr('datebox-button-index') == 0) {
			$(this).text(part_language_change_new('boxtoday'));
		} else if($(this).attr('datebox-button-index') == 1) {
			$(this).text(part_language_change_new('boxclear'));
		} else {
			$(this).text(part_language_change_new('boxclose'));
		}
	})
}
/*
 * 方法名  searchValue
 * 说明  根据 一个键的value 返回 在集合中另一个键的值
 * 参数 obj集合  key 要比对的键   value要比对的值   rkey返回的值
 * @author作者 刘实
 * @date 日期 2018.08.29
 */
function searchValue(obj, key, value, rkey) {
	var a = "";
	for(var i = 0; i < obj.length; i++) {
		if(value == obj[i][key]) {
			a = obj[i][rkey];
			break;
		}
	}
	return a;
}

/*
 * 设置本国货币
 */
function setPersonMoneyCode(personMessage) {
	//获取登陆者货
	var toplange = $('#language').val();
	if(toplange == undefined) {
		toplange = parent.$('#language').val();
	}
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
	var saleVal = val;
	var saleOffVal = val;
	var costVal = val;
	
	if (($("#switch-state1").exist() && $('#switch-state1').bootstrapSwitch('state')) || 
			($("#switch-state-job").exist() && $('#switch-state-job').bootstrapSwitch('state')==true)){
		costVal = $("#cost_foreign_type option:selected").text()==""?$("#plan_cost_foreign_code option:selected").text():$("#cost_foreign_type option:selected").text();
	}
	if ($("#switch-state").exist() && $('#switch-state').bootstrapSwitch('state')){
		saleVal = $("#plansale_foreign_code option:selected").text();
	}
	if($("#switch-state-off").exist() && $('#switch-state-off').bootstrapSwitch('state')==true)
	{
		saleOffVal = $("#plansale_foreign_code_off option:selected").text();
	}
	if($('.business').hasClass("hidden") || $('.business_off').hasClass("hidden"))
	{
		$('.business_off').find('.sale_person_code').text(saleOffVal);
		$('.business').find('.sale_person_code').text(saleVal);
	}else{
		$('.sale_person_code').text(saleVal);
	}
	$('.cost_person_code').text(costVal);
	$('.person_code').text(val);
	$('.rmb').text(val);
	if(window.location.href.match(/\/job_details/)!=null){
		$('.money-code,.pay_d_t').text(val);
	}
	
	sessionStorage.setItem("localMoneyCode", val);

}
//原价部分 增值税编辑设定计算
function calByTaxChange() {
	var flg = true;
	var haveVatFlg = $('.cost_rate_click').find('.active').find('input').val();
	var saleCostAmt = recoveryNumber($('span#cost_foreign_amt').text());
	var reqPayAmt = recoveryNumber($('#pay_amt').text());
	var vatAmt = recoveryNumber($('.edit-tax-price').val());
	if(vatAmt == "") {
		//			showErrorHandler("NO_SET_TAX",'info','info');
		//  Judgment_empty("price-border");
		$(".price-border").setBorderRed();
		validate($(".price-border"), part_language_change_new('NODATE'));
		flg = false;
		return flg;
	}
	var obj = calculateMoneyByVatChangeHandler(haveVatFlg, saleCostAmt, reqPayAmt, vatAmt);
	$('span#cost_foreign_amt').text(formatNumber(obj["saleCostAmt"]));
	$('#cost_tax').text(formatNumber(obj["vatAmt"]));
	$('#pay_amt').text(formatNumber(obj["reqPayAmt"]));
	return flg;
}
/**
 * 方法名 
 * 方法的说明 获取当前语言类型并转换
 * @param columname 数组里要替换的列明,list 数据
 * @return 
 * @author作者 FQQ
 * @date 日期 2018.09.18
 */
function getGridLanguagetext(columname, listt) {
	var language = $('#language').val();
	var name = '';
	if(listt != null && listt != '') {
		var lo = listt.length;
		if(columname == 'tax_typename') {
			for(var i = 0; i < lo; i++) {
				if(language == 'zc') {
					listt[i].tax_typename = listt[i].itmname;
				} else if(language == 'jp') {
					listt[i].tax_typename = listt[i].itemname_jp;
				} else if(language == 'zt') {
					listt[i].tax_typename = listt[i].itemname_hk;
				} else {
					listt[i].tax_typename = listt[i].itemname_en;
				}
			}
		}
		if(columname == 'timesheet') {
			for(var i = 0; i < lo; i++) {
				if(language == 'zc') {
					listt[i].departname = listt[i].departname;
				} else if(language == 'jp') {
					listt[i].departname = listt[i].departnamejp;
				} else if(language == 'zt') {
					listt[i].departname = listt[i].departnameft;
				} else {
					listt[i].departname = listt[i].departnameen;
				}
			}
		}

	}

	return listt;
}
/**
 * 方法名 
 * 方法的说明 获取当前时间，格式YYYY-MM-DD
 * @param 
 * @return 
 * @author作者 FQQ
 * @date 日期 2018.09.18
 */
function getNowFormatDate() {
	var date = new Date();
	var seperator1 = "-";
	var year = date.getFullYear();
	var month = date.getMonth() + 1;
	var strDate = date.getDate();
	if(month >= 1 && month <= 9) {
		month = "0" + month;
	}
	if(strDate >= 0 && strDate <= 9) {
		strDate = "0" + strDate;
	}
	var currentdate = year + seperator1 + month + seperator1 + strDate;
	return currentdate;
}

function changeCompany() {
	var path = $.getAjaxPath() + "changeCompany";
	var pars = {
		changeCompanyID: $('#changeCompany').val()
	};
	$.ajax({
		url: path,
		headers: {
			"requestID": $.getRequestID(),
			"requestName": $.getRequestNameByMst()
		},
		data: JSON.stringify(pars),
		success: function(data) {
			if(data[$.getRequestMetaName()].result == $.getRequestStatusName()) {
				$.setRequestID(data[$.getRequestUserInfoName()].requestID);
				window.location.href = $.getJumpPath() + "jczh/top_registration.html";
			} else {
				showErrorFunHandler(data[$.getRequestMetaName()].result, "ERROR", "ERROR");
			}
		},
		error: function(data) {
			showErrorHandler("CONNECTION_SERVER_ERROR", "ERROR", "ERROR");
			//logoutHandler();
		}
	});
}

function getNowFormatDate() {
	var date = new Date();
	var seperator1 = "-";
	var year = date.getFullYear();
	var month = date.getMonth() + 1;
	var strDate = date.getDate();
	if(month >= 1 && month <= 9) {
		month = "0" + month;
	}
	if(strDate >= 0 && strDate <= 9) {
		strDate = "0" + strDate;
	}
	var currentdate = year + seperator1 + month + seperator1 + strDate;
	return currentdate;
}
/**
 * 方法名  showLocale
 * 方法的说明 显示系统时间
 * @param obj 带有系统时间的对象
 * @return void
 * @author作者 zlx
 * @date 日期 2018.09.19
 */
function showLocale(obj) {
	var objD = obj.b;
	var hh = objD.hour;
	if(hh < 10) hh = '0' + hh;
	var mm = objD.minute;
	if(mm < 10) mm = '0' + mm;
	var str = hh + ":" + mm;
	if($("#localtime").exist()) {
		$("#localtime").html(str);
		updateTime(obj.b)
	}
}
function updateTime(obj){
	obj.second++;
	if(obj.second==60){
		obj.second = 0;
		obj.minute++;
	}
	if(obj.minute==60){
		obj.minute = 0;
		obj.hour++;
	}
	if(obj.hour==24){
		obj.hour = 0;
	}
}

function changeColorByUser(colorV) {
	var path = $.getAjaxPath() + "changeColorByUser";
	var pars = {
		colorV: colorV
	};
	$.ajax({
		url: path,
		headers: {
			"requestID": $.getRequestID(),
			"requestName": $.getRequestNameByMst()
		},
		data: JSON.stringify(pars),
		success: function(data) {
			if(data[$.getRequestMetaName()].result == $.getRequestStatusName()) {
				$.setRequestID(data[$.getRequestUserInfoName()].requestID);
				showErrorHandler("EXECUTE_SUCCESS", "INFO", "INFO");
			} else {
				showErrorFunHandler(data[$.getRequestMetaName()].result, "ERROR", "ERROR");
			}
		},
		error: function(data) {
			showErrorHandler("CONNECTION_SERVER_ERROR", "ERROR", "ERROR");
			//logoutHandler();
		}
	});
}

function jumpPageSearchList(pageFlg) {
	var pars = {};
	pars.searchFlg = pageFlg;
	switch(pageFlg) {
		case "job":
			if(urlPars.hasParm("statusCode")) {
				pars.dlvfalg = urlPars.parm("statusCode");
			}
			if(urlPars.hasParm("invStatusCode")) {
				pars.sellInvoiceFlg = urlPars.parm("invStatusCode");
			}
			if(urlPars.hasParm("costCode")) {
				pars.cost_finish_flg = urlPars.parm("costCode");
			}
			if(urlPars.hasParm("labelText")) {
				pars.label_text = urlPars.parm("labelText");
			}
			if(urlPars.hasParm("topFlg")) {
				pars.topFlg = urlPars.parm("topFlg");
			}
			if(urlPars.hasParm("dlvday")) {
				pars.dlvday = urlPars.parm("dlvday");
			} else {
				if(urlPars.hasParm("labelText") != true) {
					var AccountDate = $.getAccountDate().replace("/", "-");
					pars.dlvday = AccountDate;
				}
			}
			break;
		case "top":
			if(urlPars.hasParm("keyword")) {
				pars.keyword = urlPars.parm("keyword");
			}
			break;
		case "cost":
			//pars.dlvfalg = urlPars.parm("statusCode");
			break;
		case "init":
			pars.searchFlg = "";
			break;
			
	}
	return pars;
}
//至少选择一个的验证  ロール一覧 与 一覧カスタマイズ调用
function flagCheck(classId) {
	var sum = 0;
	var onoffLen = $(classId).length;
	$(classId).each(function(index, event) {
		var flag = $(this).prop('checked');
		if(!flag) {
			sum++;
		}
	})
	if(sum == onoffLen) {
		return true;
	} else {
		return false;
	}
}

function lableAddInit(tableID) {
	lableShowByPower("list");
	var rows = $('#' + tableID).datagrid('getSelections');
	var nowDate = new Date();
	var strTime = strToDate(nowDate, 4);
	if(rows.length > 1) {
		if(tableID == "cost_list_Two") {
			var hpname = "COST" + strTime;
			$("#lable-input").val(hpname);
		}
		if(tableID == "job_login_list") {
			var jobcd = rows[0].jobcd;
			var max = jobcd.substr(1);
			for(var i = 0; i < rows.length; i++) {
				if(rows[i].jobcd.substr(1) < max) {
					max = rows[i].jobcd.substr(1)
					jobcd = rows[i].jobcd.substr(1)
				}
			}
			/*修改默认是添加时间最小的改为JOB号最小的
			var jobcd = rows[0].jobcd;
			var max = rows[0].adddate;
			for(var i = 0; i < rows.length; i++) {
				if(rows[i].adddate > max) {
					max = rows[i].adddate;
					jobcd = rows[i].jobcd;
				}
			}
			*/
			var hpname = "U" + jobcd.substr(1);
			$("#lable-input").val(hpname);
		}
	} else {
		if(tableID == "cost_list_Two") {
			//var jobcd = rows[0].jobcd;
			var hpname = "COST" + strTime;
			$("#lable-input").val(hpname);
		}
		if(tableID == "job_login_list") {
			var jobcd = rows[0].jobcd;
			var hpname = "U" + jobcd.substr(1);
			$("#lable-input").val(hpname);
		}

	}
}
//更改表头
function saveCostWidth(tableID) {
	var bl = isHavePower(JSON.parse($.getNodeList()), [70]);
	if(!bl) {
		showErrorHandler("SYS_VALIDATEPOWER_ERROR", 'info', 'info');
		return;
	}
	var arr = [];
	var columnObj = $("#" + tableID).datagrid("options").columns[0];
	//获取冻结列的宽度
	var cols = $("#" + tableID).datagrid("getColumnFields", true);
	var grid = $("#" + tableID).datagrid("getColumnOption", cols[1]);
	if(grid != null) {
		var frozenColumnWidth = grid.width;
		var columnid = grid.columnid;
		var companycd = grid.companycd;
		var level = grid.level;
		var usercd = grid.usercd;
		arr.push({
			'field': 'jobcd',
			'columnwidth': frozenColumnWidth,
			'columnid': columnid,
			'companycd': companycd,
			'level': level,
			'usercd': usercd
		});
	}

	for(var i = 0; i < columnObj.length; i++) {
		var obj = {};
		obj['field'] = columnObj[i].field;
		obj['columnwidth'] = columnObj[i].width;
		obj['columnid'] = columnObj[i].columnid;
		obj['companycd'] = columnObj[i].companycd;
		obj['level'] = columnObj[i].level;
		obj['usercd'] = columnObj[i].usercd;
		arr.push(obj);
	}
	//arr = JSON.stringify(arr);
	//	console.log(arr)
	var o = {
		listTitleMsg: arr
	}
	$.ajax({
		url: $.getAjaxPath() + "listTitleUp",
		data: JSON.stringify(o),
		headers: {
			"requestID": $.getRequestID(),
			"requestName": $.getRequestNameByJcZH()
		},
		success: function(data) {
			//			console.log(data);
			if(data[$.getRequestMetaName()].result == $.getRequestStatusName()) {
				$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
				if(data[$.getRequestDataName()] > 0) {
					showErrorHandler("EXECUTE_SUCCESS", "SUCCESS", "SUCCESS");
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
//job 売上 共通0.00
$(function() {
	default_decimal_reservation();
})
/**
 * 方法名  showLocale
 * 方法的说明 显示系统时间
 * @param obj 获取当前时间
 * @return void
 * @author作者 fqq
 * @date 日期 2018.09.19
 */
function getToday() {
	var date = new Date();
	var seperator1 = "-";
	var year = date.getFullYear();
	var month = date.getMonth() + 1;
	var strDate = date.getDate();
	if(month >= 1 && month <= 9) {
		month = "0" + month;
	}
	if(strDate >= 0 && strDate <= 9) {
		strDate = "0" + strDate;
	}
	var currentdate = year + seperator1 + month + seperator1 + strDate;
	return currentdate;
}
/**
 * 方法名  showLocale
 * 方法的说明 js中两个小数相乘
 * @param obj 
 * @return void
 * @author作者 fqq
 * @date 日期 2018.09.19
 */
function accMul(arg1, arg2) {
	var m = 0,
		s1 = arg1.toString(),
		s2 = arg2.toString();
	try {
		m += s1.split(".")[1].length
	} catch(e) {

	}
	try {
		m += s2.split(".")[1].length
	} catch(e) {

	}
	return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m)
}

//权限
function validatePowerByClick(tableID, str) {
	var row = $('#' + tableID).datagrid('getSelected');
	var powerList1 = JSON.parse($.getNodeList());
	var cldivList = row.cldivList;
	var requestList = row.reqList;
	var mdList = row.mdList;
	var userId = $.getUserID();
	if(tableID =="job_login_list"){
		var cldivcd = row.accountcd;
	}
	else{
		var cldivcd = row.cldivcd;
	}
	
	var flg = false;
	var powerList = [];
	for(var i = 0; i < powerList1.length; i++) {
		powerList.push(powerList1[i].nodeID);
	}
	switch(str) {
		case 'jobUpdate':
			//扭付
			if([].inArray(powerList, 9)) {
				flg = true;
			}
			if([].inArray(powerList, 10)) {
				if([].inArray(cldivList, cldivcd)) {
					flg = true;
				}
			}
			if([].inArray(powerList, 11)) {
				if([].inArray(requestList, userId)) {
					flg = true;
				}
			}
			if([].inArray(powerList, 12)) {
				if([].inArray(mdList, userId)) {
					flg = true;
				}
			}
			break;
		case 'costEnd':
			//扭付
			if([].inArray(powerList, 14)) {
				flg = true;
			}
			if([].inArray(powerList, 15)) {
				if([].inArray(cldivList, cldivcd)) {
					flg = true;
				}
			}
			if([].inArray(powerList, 16)) {
				if([].inArray(requestList, userId)) {
					flg = true;
				}
			}
			if([].inArray(powerList, 17)) {
				if([].inArray(mdList, userId)) {
					flg = true;
				}
			}
			break;
		case 'costEndCancel':
			//扭付
			if([].inArray(powerList, 14)) {
				flg = true;
			}
			if([].inArray(powerList, 15)) {
				if([].inArray(cldivList, cldivcd)) {
					flg = true;
				}
			}
			if([].inArray(powerList, 16)) {
				if([].inArray(requestList, userId)) {
					flg = true;
				}
			}
			if([].inArray(powerList, 17)) {
				if([].inArray(mdList, userId)) {
					flg = true;
				}
			}
			break;
		case 'saleLogin':
			//扭付
			if([].inArray(powerList, 18)) {
				flg = true;
			}
			if([].inArray(powerList, 19)) {
				if([].inArray(cldivList, cldivcd)) {
					flg = true;
				}
			}
			if([].inArray(powerList, 20)) {
				if([].inArray(requestList, userId)) {
					flg = true;
				}
			}
			if([].inArray(powerList, 21)) {
				if([].inArray(mdList, userId)) {
					flg = true;
				}
			}
			break;
		case 'saleUpdate':
			//扭付
			if([].inArray(powerList, 18)) {
				flg = true;
			}
			if([].inArray(powerList, 19)) {
				if([].inArray(cldivList, cldivcd)) {
					flg = true;
				}
			}
			if([].inArray(powerList, 20)) {
				if([].inArray(requestList, userId)) {
					flg = true;
				}
			}
			if([].inArray(powerList, 21)) {
				if([].inArray(mdList, userId)) {
					flg = true;
				}
			}
			break;
		case 'saleAdmin':
			//扭付
			if([].inArray(powerList, 23)) {
				flg = true;
			}
			if([].inArray(powerList, 24)) {
				if([].inArray(cldivList, cldivcd)) {
					flg = true;
				}
			}
			if([].inArray(powerList, 25)) {
				if([].inArray(requestList, userId)) {
					flg = true;
				}
			}
			if([].inArray(powerList, 26)) {
				if([].inArray(mdList, userId)) {
					flg = true;
				}
			}
			break;
		case 'saleAdminCancel':
			//扭付
			if([].inArray(powerList, 23)) {
				flg = true;
			}
			if([].inArray(powerList, 24)) {
				if([].inArray(cldivList, cldivcd)) {
					flg = true;
				}
			}
			if([].inArray(powerList, 25)) {
				if([].inArray(requestList, userId)) {
					flg = true;
				}
			}
			if([].inArray(powerList, 26)) {
				if([].inArray(mdList, userId)) {
					flg = true;
				}
			}
			break;
		case 'reqApply':
			//扭付
			if([].inArray(powerList, 27)) {
				flg = true;
			}
			if([].inArray(powerList, 28)) {
				if([].inArray(cldivList, cldivcd)) {
					flg = true;
				}
			}
			if([].inArray(powerList, 29)) {
				if([].inArray(requestList, userId)) {
					flg = true;
				}
			}
			if([].inArray(powerList, 30)) {
				if([].inArray(mdList, userId)) {
					flg = true;
				}
			}
			break;
		case 'invoiceApply':
			//扭付
			if([].inArray(powerList, 31)) {
				flg = true;
			}
			if([].inArray(powerList, 32)) {
				if([].inArray(cldivList, cldivcd)) {
					flg = true;
				}
			}
			if([].inArray(powerList, 33)) {
				if([].inArray(requestList, userId)) {
					flg = true;
				}
			}
			if([].inArray(powerList, 34)) {
				if([].inArray(mdList, userId)) {
					flg = true;
				}
			}
			break;
		case 'businessOutPut':
			//扭付
			if([].inArray(powerList, 36)) {
				flg = true;
			}
			if([].inArray(powerList, 37)) {
				if([].inArray(cldivList, cldivcd)) {
					flg = true;
				}
			}
			if([].inArray(powerList, 38)) {
				if([].inArray(requestList, userId)) {
					flg = true;
				}
			}
			if([].inArray(powerList, 39)) {
				if([].inArray(mdList, userId)) {
					flg = true;
				}
			}
			break;
		case 'costList':
			//扭付
			if([].inArray(powerList, 40)) {
				flg = true;
			}
			if([].inArray(powerList, 41)) {
				if([].inArray(cldivList, cldivcd)) {
					flg = true;
				}
			}
			if([].inArray(powerList, 42)) {
				if([].inArray(requestList, userId)) {
					flg = true;
				}
			}
			if([].inArray(powerList, 43)) {
				if([].inArray(mdList, userId)) {
					flg = true;
				}
			}
			break;
		case 'jobList':
			//扭付
			if([].inArray(powerList, 5)) {
				flg = true;
			}
			if([].inArray(powerList, 6)) {
				if([].inArray(cldivList, cldivcd)) {
					flg = true;
				}
			}
			if([].inArray(powerList, 7)) {
				if([].inArray(requestList, userId)) {
					flg = true;
				}
			}
			if([].inArray(powerList, 8)) {
				if([].inArray(mdList, userId)) {
					flg = true;
				}
			}
			break;
		case 'costLogin':
			//扭付
			if([].inArray(powerList, 44)) {
				flg = true;
			}
			if([].inArray(powerList, 45)) {
				if([].inArray(cldivList, cldivcd)) {
					flg = true;
				}
			}
			if([].inArray(powerList, 46)) {
				if([].inArray(requestList, userId)) {
					flg = true;
				}
			}
			if([].inArray(powerList, 47)) {
				if([].inArray(mdList, userId)) {
					flg = true;
				}
			}
			break;
		case 'costUpdate':
			//扭付
			if([].inArray(powerList, 44)) {
				flg = true;
			}
			if([].inArray(powerList, 45)) {
				if([].inArray(cldivList, cldivcd)) {
					flg = true;
				}
			}
			if([].inArray(powerList, 46)) {
				if([].inArray(requestList, userId)) {
					flg = true;
				}
			}
			if([].inArray(powerList, 47)) {
				if([].inArray(mdList, userId)) {
					flg = true;
				}
			}
			break;
		case 'payLogin':
			//扭付
			if([].inArray(powerList, 49)) {
				flg = true;
			}
			if([].inArray(powerList, 50)) {
				if([].inArray(cldivList, cldivcd)) {
					flg = true;
				}
			}
			if([].inArray(powerList, 51)) {
				if([].inArray(requestList, userId)) {
					flg = true;
				}
			}
			if([].inArray(powerList, 52)) {
				if([].inArray(mdList, userId)) {
					flg = true;
				}
			}
			break;
		case 'payUpdate':
			//扭付
			if([].inArray(powerList, 49)) {
				flg = true;
			}
			if([].inArray(powerList, 50)) {
				if([].inArray(cldivList, cldivcd)) {
					flg = true;
				}
			}
			if([].inArray(powerList, 51)) {
				if([].inArray(requestList, userId)) {
					flg = true;
				}
			}
			if([].inArray(powerList, 52)) {
				if([].inArray(mdList, userId)) {
					flg = true;
				}
			}
			break;
		case 'lendLogin':
			//扭付
			if([].inArray(powerList, 56)) {
				flg = true;
			}
			if([].inArray(powerList, 57)) {
				if([].inArray(cldivList, cldivcd)) {
					flg = true;
				}
			}
			if([].inArray(powerList, 58)) {
				if([].inArray(requestList, userId)) {
					flg = true;
				}
			}
			if([].inArray(powerList, 59)) {
				if([].inArray(mdList, userId)) {
					flg = true;
				}
			}
			break;
		case 'lendUpdate':
			//扭付
			if([].inArray(powerList, 56)) {
				flg = true;
			}
			if([].inArray(powerList, 57)) {
				if([].inArray(cldivList, cldivcd)) {
					flg = true;
				}
			}
			if([].inArray(powerList, 58)) {
				if([].inArray(requestList, userId)) {
					flg = true;
				}
			}
			if([].inArray(powerList, 59)) {
				if([].inArray(mdList, userId)) {
					flg = true;
				}
			}
			break;
	}
	return flg;
}
/**
 * 方法名  per_verificate
 * 方法的说明  权限限制hover效果
 * @param obj 
 * @return void
 * @author作者 zlx
 * @date 日期 2018.10.23
 */
function per_verificate(obj) {
	if(obj[0].tagName == 'DIV') {
		obj.removeAttr('onmouseover');
	}
	obj.hover(function() {
		if(obj[0].tagName == 'SPAN') {
			obj.css('text-decoration', "none")
		} else if(obj[0].tagName == 'DIV') {
			obj.removeAttr('onmouseover');
		}
	})
}


/**
 * 方法名  escapeHtml
 * 方法的说明  easyui datagrid防止标签污染
 * @param String 
 * @return String
 * @author作者 zlx
 * @date 日期 2018.10.29
 */
function escapeHtml(s) {
	if(s === undefined) {
		return s;
	} else {
		var href = window.location.href;
		if(href.match(/\/job_registration_list.html|\/cost_list.html/) !== null) {
			s = String(s === null ? "" : s);
			return s.replace(/&/g, "&amp;").replace(/</g, "&lt;").replace(/>/g, "&gt;")
				.replace(/"/g, "&quot;").replace(/'/g, "&#39;"); // &，大于，小于，双引号，单引号
		} else {
			return s;
		}
	}
}
/**
 * 方法名  匿名函数
 * 方法的说明  sidebar颜色显示
 * @param void
 * @return void
 * @author作者 zlx
 * @date 日期 2018.10.30
 */
$(function() {
	var href = window.location.href;
	if(href.match(/\/job_registration_list.html|\/job_registration.html|\/job_details.html|\/job_update.html|jczh\/salescategory_[a-z]+.html/) !== null) {
		$('li#jobBlock,li#jobBlock>a').css("color", "#1abc9c");
	} else if(href.match(/\/cost_list.html|\/payment_[a-z]+.html|\/order_[a-z]+.html|\/transfer_[a-z]+.html|\/advance_[a-z]+.html/)) {
		$('li#costBlock,li#costBlock>a').css("color", "#1abc9c");
	} else if(href.match(/\/monthbalance.html/)) {
		$('li#monthlyClosingBlock,li#monthlyClosingBlock>a').css("color", "#1abc9c");
	} else if(href.match(/\/report_output.html/)) {
		$('li#reportBlock,li#reportBlock>a').css("color", "#1abc9c");
	} else if(href.match(/\/timesheet_list.html/)) {
		$('li#timesheetBlock,li#timesheetBlock>a').css("color", "#1abc9c");
	} else if(href.match(/\/custom_list.html|\/label_management.html/)) {
		$('li#userSettingBlock,li#userSettingBlock>a').css("color", "#1abc9c");
	} else if(href.match(/\/suppliers_list.html|\/employee_list.html|\/role_list.html|\/salescategory_list.html|\/taxcost_list.html|\/commoncode_list.html|\/ltd_list.html|\/suppliers_registration.html|\/ltd_registration.html|\/wedgemembers_change.html|\/employee_registration.html|\/customerwedge_change.html|\/role_login_table.html|\/salescategory_registration.html|\/taxcost_registration.html|\/commoncode_registration.html/)) {
		$('li#masterBlock,li#masterBlock>a').css("color", "#1abc9c");
	} else if(href.match(/\/Invoice_output.html/)) {
		$('li#reportBlock,li#reportBlock>a').css("color", "#1abc9c");
	}
})
/**
 * 方法名  supp_ltd_language
 * 方法的说明  取引先与会社发注书语言切换
 * @param String
 * @return void
 * @author作者 zlx
 * @date 日期 2018.10.31
 */
function supp_ltd_language(language) {
	//nav-tabs-zc 语言切换
	if(language == 'jp') {
		$('li[role="nav-tabs-jp"]').addClass('active');
		$('ul.nav.nav-tabs').prepend($('li[role="nav-tabs-jp"]'));
		$('li[role="nav-tabs-jp"]').siblings('li').removeClass('active');
		if($('.tab-pane').hasClass('nav-tabs-jp')) {
			$('.nav-tabs-jp').siblings('#tab1').addClass('tab-pane');
			$('.tab-pane.nav-tabs-jp').removeClass('tab-pane');
		}
	} else if(language == 'zc') {
		$('li[role="nav-tabs-zc"]').addClass('active');
		$('ul.nav.nav-tabs').prepend($('li[role="nav-tabs-zc"]'));
		$('li[role="nav-tabs-zc"]').siblings('li').removeClass('active');
		if($('.tab-pane').hasClass('nav-tabs-zc')) {
			$('.nav-tabs-zc').siblings('#tab1').addClass('tab-pane');
			$('.tab-pane.nav-tabs-zc').removeClass('tab-pane');
		}
	} else if(language == 'en') {
		$('li[role="nav-tabs-en"]').addClass('active');
		$('ul.nav.nav-tabs').prepend($('li[role="nav-tabs-en"]'));
		$('li[role="nav-tabs-en"]').siblings('li').removeClass('active');
		if($('.tab-pane').hasClass('nav-tabs-en')) {
			$('.nav-tabs-en').siblings('#tab1').addClass('tab-pane');
			$('.tab-pane.nav-tabs-en').removeClass('tab-pane');
		}
	} else {
		$('li[role="nav-tabs-zt"]').addClass('active');
		$('ul.nav.nav-tabs').prepend($('li[role="nav-tabs-zt"]'));
		$('li[role="nav-tabs-zt"]').siblings('li').removeClass('active');
		if($('.tab-pane').hasClass('nav-tabs-zt')) {
			$('.nav-tabs-zt').siblings('#tab1').addClass('tab-pane');
			$('.tab-pane.nav-tabs-zt').removeClass('tab-pane');
		}
	}
}

//lable追加 类别权限限制
function lableShowByPower(list) {
	var powerList = JSON.parse($.getNodeList());
	var myPower = isHavePower(powerList, [72]);
	var managerPower = isHavePower(powerList, [71]);
	if(list == undefined) {
		if(!myPower) {
			$(".my").addClass("hidden");
			$(".manager").addClass("active");
		}
		if(!managerPower) {
			$(".manager").addClass("hidden");
			$(".my").addClass("active");
		}
		if(!myPower && !managerPower) {
			$("#lable-add-part").addClass("hidden");
		}
	} else {
		if(!myPower) {
			$(".my-list").addClass("hidden");
			$(".manager-list").addClass("active");
		}
		if(!managerPower) {
			$(".manager-list").addClass("hidden");
			$(".my-list").addClass("active");
		}

	}

}
/**
 * 方法名  show_menu_job
 * 方法的说明  单选job菜单弹出
 * @param Object,String
 * @return void
 * @author作者 zlx
 * @date 日期 2018.11.20
 */
function show_menu_job(event, index) {
	var e = event;
	e.stopPropagation(); //阻止事件冒泡
	$('#job_login_list').datagrid("unselectAll");
	$('#job_login_list').datagrid("selectRow", index);
	$('#menu').menu('show', {
		//显示右键菜单  
		left: e.pageX, //在鼠标点击处显示菜单  
		top: e.pageY
	});
	menuInit();
}
/**
 * 方法名  show_menu_cost
 * 方法的说明  单选cost菜单弹出
 * @param Object,String
 * @return void
 * @author作者 zlx
 * @date 日期 2018.11.20
 */
function show_menu_cost(event, index) {
	var e = event;
	e.stopPropagation(); //阻止事件冒泡
	$('#cost_list_Two').datagrid("unselectAll");
	$('#cost_list_Two').datagrid("selectRow", index);
	$('#menu').menu('show', {
		//显示右键菜单  
		left: e.pageX, //在鼠标点击处显示菜单  
		top: e.pageY
	});
	menuLoad();
}
/**
 * 方法名  auto_tableHei
 * 方法的说明  表格高度自适应逻辑
 * @param String,String,String,Object
 * @return void
 * @author作者 zlx
 * @date 日期 2018.11.20
 */
var auto_tableHei = function(objTop, objBot, num, tableObj) {
	var sum_height = $('aside#content').height();
	var height = sum_height - objTop - objBot - num;
	tableObj.css('max-height', height);
}
/**
 * 方法名  show_menu_cost
 * 方法的说明  numbox限制位数输入2
 * @param event,dom,String
 * @return bool
 * @author作者 zlx
 * @date 日期 2018.11.29
 */
function checkNumber(e, txt, retention_digit) {
	retention_digit = Number(retention_digit);
	switch(retention_digit) {
		case 0:
			txt.value = txt.value.replace(/^(\-)*(\d+)\.().*$/, '$1$2');
			break;
		case 1:
			txt.value = txt.value.replace(/^(\-)*(\d+)\.(\d).*$/, '$1$2.$3');
			break;
		case 2:
			txt.value = txt.value.replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3');
			break;
	}
}
/**
 * 方法名  auto_small_remark
 * 方法的说明  小备考自适应
 * @param String
 * @return void
 * @author作者 zlx
 * @date 日期 2018.11.30
 */
function auto_small_remark(stringId, flag) {
	if(flag == 1) {
		var sale_pay_remark_width = parent.$('aside#content .row .col-md-8').width() * 0.5 - 47.17;
	} else {
		var sale_pay_remark_width = $('aside#content .row .col-md-8').width() * 0.5 - 47.17;
	}
	$('.' + stringId).width(sale_pay_remark_width);
}
//备考计算加强版
function auto_smalll_remark_T() {
	var variable = 13;
	if($('aside#content').exist()) {
		var obj_w = $('aside#content').width();
	} else {
		var obj_w = parent.$('aside#content').width();
	}
	var width_p = ((obj_w + variable) * 0.6666666667 - 60) * 0.5 - 32;
	return width_p;
}
//支付申请调用备考计算逻辑
function auto_small_remark_pay() {
	var width_p = document.body.clientWidth * 0.29 - 30;
	return width_p;
}
/**
 * 方法名  part_language_change
 * 方法的说明  部分语言读取
 * @param dom
 * @return void
 * @author作者 zlx
 * @date 日期 2018.12.12
 */
function part_language_change(dom) {
	var optionEle = $("#i18n_pagename");
	var sourceName = optionEle.attr('content');
	sourceName = sourceName.split('-');
	var i18nLanguage = localStorage.getItem('language');
	jQuery.i18n.properties({
		name: sourceName, //资源文件名称
		path: '../public/language/jczh/' + i18nLanguage + '/', //资源文件路径  
		mode: 'map', //用Map的方式使用资源文件中的值
		language: i18nLanguage,
		callback: function() { //加载成功后设置显示内容
			var insertEle = dom;
			insertEle.each(function() {
				// 根据i18n元素的 name 获取内容写入
				$(this).html($.i18n.prop($(this).attr('name')));
			});
		}
	});
}
/**
 * 方法名  part_language_change_new
 * 方法的说明  部分语言读取
 * @param name
 * @return void
 * @author作者 zlx
 * @date 日期 2018.1.21
 */
function part_language_change_new(name_pro) {
	
	var res = JSON.parse(localStorage.getItem('i18n'))[name_pro];
	//判断是否返回的是数组类型
	if((typeof res == 'object') && res.constructor == Array) {
		res = res[0]
	}
	return res;
}
/**
 * 方法名  bus_price_common
 * 方法的说明  设置numbox保留小数位数
 * @param retention_digit,retention_digit_p
 * @return void
 * @author作者 zlx
 * @date 日期 2018.12.17
 */
function bus_price_common(retention_digit, retention_digit_p) {
	var bussiness_fix = $('.setFixRate').eq(0); //売上
	var price_fix = $('.setFixRate').eq(1); //原价
	if(!price_fix.exist()) {
		var price_fix = $('.setFixRate').eq(0);
	}
	//兼容基本浏览器 禁止粘贴
	// $('.setFixRate').on('paste', function() {
	// 	return false;
	// })
	//job登录 売上登录等金额numbox框14,2的验证
	$('.setFixRate').on('input', function() {
		var valueFix = number_va_length($(this).find('span').find('input[type="text"]').val(), 14, 2);
		$(this).find('span').find('input[type="text"]').val(valueFix);
	})
	if(retention_digit != undefined) {
		bussiness_fix.find('.saleMoment').numberbox({
			precision: retention_digit
		})
		bussiness_fix.unbind('keyup');
		bussiness_fix.keyup(function(event) {
			event.stopPropagation();
			return checkNumber(event, $(this).find('span').find('input')[0], retention_digit);
		})
	}
	if(retention_digit_p != undefined) {
		price_fix.find('.saleMoment').numberbox({
			precision: retention_digit_p
		})
		price_fix.unbind('keyup');
		price_fix.keyup(function(event) {
			event.stopPropagation();
			return checkNumber(event, $(this).find('span').find('input')[0], retention_digit_p);
		})
	}
	deleteQian();
}
/**
 * 方法名  deleteQian
 * 方法的说明  去掉numberbox千分符
 * @param void
 * @return void
 * @author作者 zlx
 * @date 日期 2018.1.22
 */
function deleteQian() {
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
}
/**
 * 方法名  edit_common
 * 方法的说明  设置编辑弹窗numbox保留小数位数
 * @param Number num1 売上 num2 原价
 * @return void
 * @author作者 zlx
 * @date 日期 2018.12.19
 */
function edit_common(num1, num2) {
	var bussiness_edit = $('.setFixRateEdit').eq(0); //売上
	var price_edit = $('.setFixRateEdit').eq(1); //原价
	if(!price_edit.exist()) {
		var price_edit = $('.setFixRateEdit').eq(0);
	}
	//兼容基本浏览器 禁止粘贴
	// $('.setFixRateEdit').on('paste', function() {
	// 	return false;
	// })
	$('.setFixRateEdit').on('input', function() {
		//  	var maxlength = 14;
		//     if($('.setFixRateEdit').find('span').find('input[type="text"]').val().indexOf('.') != -1){
		//      	maxlength = 15;
		//     }
		// 	   $('.setFixRateEdit').find('span').find('input[type="text"]').attr('maxlength',maxlength);
		var valueRateFix = number_va_length($(this).find('span').find('input[type="text"]').val(), 14, 2);
		$(this).find('span').find('input[type="text"]').val(valueRateFix);
	})
	if(num1 != undefined) {
		bussiness_edit.find('.edit-tax').numberbox({
			precision: num1
		})
		bussiness_edit.unbind('keyup');
		bussiness_edit.keyup(function(event) {
			event.stopPropagation();
			return checkNumber(event, $(this).find('span').find('input')[0], num1);
		})
	}
	if(num2 != undefined) {
		price_edit.find('.edit-tax-price').numberbox({
			precision: num2
		})
		price_edit.unbind('keyup');
		price_edit.keyup(function(event) {
			event.stopPropagation();
			return checkNumber(event, $(this).find('span').find('input')[0], num2);
		})
	}
	$('input.edit-tax').next('span').find('input').focus(function(e) {
		var value = e.target.value;
		if(value != '') {
			var new_value = recoveryNumber(value);
			$(this).val(new_value);
		}
	})
	$('input.edit-tax-price').next('span').find('input').focus(function(e) {
		var value = e.target.value;
		if(value != '') {
			var new_value = recoveryNumber(value);
			$(this).val(new_value);
		}
	});
}
/**
 * 方法名  validate
 * 方法的说明  有效性验证
 * @param obj txt
 * @return void
 * @author作者 zlx
 * @date 日期 2018.12.24
 */
function validate(obj, text) {
	obj.tooltip({
		position: 'right',
		content: '<span style=\"color:#fff\">' + text + '</span>',
		onShow: function() {
			$(this).tooltip('tip').css({
				backgroundColor: 'red',
				borderColor: 'red',
				zIndex: '99999999'
			});
		}
	});
}
/**
 * 方法名  $.fn.extend
 * 方法的说明  扩展增值税编辑框变红
 * @param void
 * @return void
 * @author作者 zlx
 * @date 日期 2018.12.24
 */
$.fn.extend({
	setBorderRed: function() {
		this.find("span.numberbox").css("border-color", "red");
		this.find("span.numberbox").next('span').css("border-top-color", "red");
		this.find("span.numberbox").next('span').css("border-right-color", "red");
		this.find("span.numberbox").next('span').css("border-bottom-color", "red");
		if(window.countG == 1) {
			//this.find("span.numberbox")[0].scrollIntoView(true);
			$('#content').scrollTop(0);
			if(!$('.panel.window.panel-htop.messager-window').exist()) {
				showErrorHandler("NOT_EMPTY", "ERROR", "ERROR");
			}
		}
	},
	setBorderBlack: function() {
		this.find("span.numberbox").css("border-color", "#cccccc");
		this.find("span.numberbox").next('span').css("border-top-color", "#cccccc");
		this.find("span.numberbox").next('span').css("border-right-color", "#cccccc");
		this.find("span.numberbox").next('span').css("border-bottom-color", "#cccccc");
	}
})
/**
 * 方法名  addEventListener
 * 方法的说明  阻止ajaxload时,enter与空格提交问题
 * @param void
 * @return void
 * @author作者 zlx
 * @date 日期 2018.12.24
 */
document.addEventListener('keydown', function(event) {
	//空格问题 未解决
	if(event.keyCode == 13 || event.keyCode == 32) {
		if($(event.target).prop("nodeName") == 'BUTTON') {
			event.stopPropagation();
			event.preventDefault();
		}
	}
})
/**
 * 方法名  clear_localstorage
 * 方法的说明  清空除用户名与密码的localStorage数据
 * @param void
 * @return void
 * @author作者 zlx
 * @date 日期 2018.12.24
 */
function clear_localstorage() {
	for(var i = localStorage.length - 1; i >= 0; i--) {
		var key = localStorage.key(i);
		if(key != "email" && key != "password") {
			localStorage.removeItem(key)
		}
	}
}
/**
 * 方法名  floatObj
 * 方法的说明  前端精度计算
 * @param void
 * @return String
 * @author作者 zlx
 * @date 日期 2018.01.18
 */
var floatObj = function() {

	/*
	 * 判断obj是否为一个整数
	 */
	function isInteger(obj) {
		return Math.floor(obj) === obj
	}

	/*
	 * 将一个浮点数转成整数，返回整数和倍数。如 3.14 >> 314，倍数是 100
	 * @param floatNum {number} 小数
	 * @return {object}
	 *   {times:100, num: 314}
	 */
	function toInteger(floatNum) {
		var ret = {
			times: 1,
			num: 0
		}
		if(isInteger(floatNum)) {
			ret.num = floatNum
			return ret
		}
		var strfi = floatNum + ''
		var dotPos = strfi.indexOf('.')
		var fushu = strfi.indexOf('-');
		var len = strfi.substr(dotPos + 1).length
		var times = Math.pow(10, len)
		//var intNum = parseInt(floatNum * times + 0.5, 10)
		if(fushu != -1) {
			var intNum = parseInt(floatNum * times - 0.5, 10)
		} else {
			var intNum = parseInt(floatNum * times + 0.5, 10)
		}
		ret.times = times
		ret.num = intNum
		return ret
	}

	/*
	 * 核心方法，实现加减乘除运算，确保不丢失精度
	 * 思路：把小数放大为整数（乘），进行算术运算，再缩小为小数（除）
	 *
	 * @param a {number} 运算数1
	 * @param b {number} 运算数2
	 * @param digits {number} 精度，保留的小数点数，比如 2, 即保留为两位小数
	 * @param op {string} 运算类型，有加减乘除（add/subtract/multiply/divide）
	 *
	 */
	function operation(a, b, digits, op) {
		a = scientificToNumber(a)
        b = scientificToNumber(b)
		var o1 = toInteger(a)
		var o2 = toInteger(b)
		var n1 = o1.num
		var n2 = o2.num
		var t1 = o1.times
		var t2 = o2.times
		var max = t1 > t2 ? t1 : t2
		var result = null
		switch(op) {
			case 'add':
				if(t1 === t2) { // 两个小数位数相同
					result = n1 + n2
				} else if(t1 > t2) { // o1 小数位 大于 o2
					result = n1 + n2 * (t1 / t2)
				} else { // o1 小数位 小于 o2
					result = n1 * (t2 / t1) + n2
				}
				return result / max
			case 'subtract':
				if(t1 === t2) {
					result = n1 - n2
				} else if(t1 > t2) {
					result = n1 - n2 * (t1 / t2)
				} else {
					result = n1 * (t2 / t1) - n2
				}
				return result / max
				
			case 'multiply':
                result = (scientificToNumber(n1 * n2)) / (scientificToNumber(t1 * t2))
                return result
        case 'divide':
                result = (n1 / n2) * (scientificToNumber(t2 / t1))  
//                
//			case 'multiply':
//				result = (n1 * n2) / (t1 * t2)
//				return result
//			case 'divide':
//				result = (n1 / n2) * (t2 / t1)
				result = operation((n1 / n2), (t2 / t1), digits, 'multiply');
				return result
		}
	}

	// 加减乘除的四个接口
	//加法
	function add(a, b, digits) {
		return operation(a, b, digits, 'add')
	}
	//减法
	function subtract(a, b, digits) {
		return operation(a, b, digits, 'subtract')
	}
	//乘法
	function multiply(a, b, digits) {
		return operation(a, b, digits, 'multiply')
	}
	//除法
	function divide(a, b, digits) {
		return operation(a, b, digits, 'divide')
	}

	// exports
	return {
		add: add,
		subtract: subtract,
		multiply: multiply,
		divide: divide
	}
}();
//卖上登录更新页面  预定部分输入框的小数点设置
function bus_price_common_plan(retention_digit, retention_digit_p) {
	var bussiness_fix = $('.setFixRate-plan').eq(0); //売上
	var price_fix = $('.setFixRate-plan').eq(1); //原价
	if(!price_fix.exist()) {
		var price_fix = $('.setFixRate-plan').eq(0);
	}

	if(retention_digit != undefined) {
		bussiness_fix.find('.saleMoment-off').numberbox({
			precision: retention_digit
		})
	}
	if(retention_digit_p != undefined) {
		price_fix.find('.saleMoment-off').numberbox({
			precision: retention_digit_p
		})
	}
	deleteQian();
}
/**
 * 方法名  init_login_language_load
 * 方法的说明  登录页面初始化时语言文件加载
 * @param void
 * @return void
 * @author作者 zlx
 * @date 日期 2018.01.24
 */
function init_login_language_load(i18nLanguage, flag) {
	var optionEle = $("#i18n_pagename");
	if(optionEle.length < 1) {
		return false;
	};
	var sourceName = optionEle.attr('content');
	sourceName = sourceName.split('-');
	switch(i18nLanguage) {
		case 'en':
		case '004':
			i18nLanguage = 'en';
			break;
		case 'jp':
		case '001':
			i18nLanguage = 'jp';
			break;
		case 'zc':
		case '002':
			i18nLanguage = 'zc';
			break;
		case 'zt':
		case '003':
			i18nLanguage = 'zt';
			break;
	}
	if(flag) {
		var path = '../public/language/jczh/' + i18nLanguage + '/';
	} else {
		var path = 'public/language/jczh/' + i18nLanguage + '/';
	}
	jQuery.i18n.properties({
		name: sourceName, //资源文件名称
		path: path, //资源文件路径  
		mode: 'map', //用Map的方式使用资源文件中的值
		language: i18nLanguage,
		callback: function() {
			var i18n = JSON.stringify($.i18n.map);
			localStorage.setItem("i18n", i18n);
		}
	});
}
/**
 * 方法的说明 扩展Date 格式化
 * @return 返回日期字符串
 * @pars formatStr 格式字符串 “yyyy-mm-dd”
 * @author作者 王岩
 * @date 日期 2018.05.09
 */
Date.prototype.format = function(formatStr) {
	var str = formatStr;
	var Week = ['日', '一', '二', '三', '四', '五', '六'];
	str = str.replace(/yyyy|YYYY/, this.getFullYear());
	str = str.replace(/MM|mm/, (this.getMonth() + 1) > 9 ? (this.getMonth() + 1).toString() : '0' + (this.getMonth() + 1));
	str = str.replace(/dd|DD/, this.getDate() > 9 ? this.getDate().toString() : '0' + this.getDate());
	return str;
}

/**
 * 方法的说明 扩展Date 格式化
 * @return 返回日期字符串
 * @pars format_extend 格式字符串 “yyyy-MM-dd hh:mm:ss”
 * @author作者 王岩
 * @date 日期 2018.05.09
 */
Date.prototype.format_extend = function(formatStr) {
	if(this == "Invalid Date") {
		return '';
	}
	var str = formatStr;
	var Week = ['日', '一', '二', '三', '四', '五', '六'];
	str = str.replace(/yyyy|YYYY/, this.getFullYear());
	str = str.replace(/MM/, (this.getMonth() + 1) > 9 ? (this.getMonth() + 1).toString() : '0' + (this.getMonth() + 1));
	str = str.replace(/dd|DD/, this.getDate() > 9 ? this.getDate().toString() : '0' + this.getDate());
	str = str.replace(/hh|HH/, this.getHours() > 9 ? this.getHours().toString() : '0' + this.getHours());
	str = str.replace(/mm/, this.getMinutes() > 9 ? this.getMinutes().toString() : '0' + this.getMinutes());
	str = str.replace(/ss/, this.getSeconds() > 9 ? this.getSeconds().toString() : '0' + this.getSeconds());
	return str;
}
/**
 * 方法的说明 验证项变红
 * @return void
 * @pars $(ele),flag true 表示变红 false 表示还原
 * @author作者 zlx
 * @date 日期 2018.01.31
 */
function getRedc(ele, flag) {
	if(flag) {
		ele.addClass('border_red');
		$('#content').scrollTop(0);
	} else {
		ele.removeClass('border_red');
	}
}
/**
 * 方法的说明 返回url字符串
 * @return void
 * @pars $(ele),flag true 表示变红 false 表示还原
 * @author作者 fqq
 * @date 日期 2018.01.31
 */
function returnURL(proofPath) {
	var pathT = proofPath.substring(0, 5);
	var path = "";
	if(pathT == 'http:' || pathT == 'https') {
		path = proofPath;
	} else {
		path = "http:\/\/" + proofPath;
	}
	return path;
}
/**
 * 方法的说明 easyui执行插入检索行时,分页样式还原问题
 * @return void
 * @pars tabId
 * @author作者 zlx
 * @date 日期 2018.02.15
 */
function easyInsertH(tabId) {
	//修改分页的样式
	var pageson = $('#' + tabId).parents('.datagrid-view').siblings('.datagrid-pager.pagination');
	var page = pageson.find('.pagination-num').parent().next().children('span')[0].innerHTML.replace(/[^0-9]/ig, '');
	pageson.find('.pagination-num').parent().next().children('span')[0].innerHTML = '/' + page;
	var info_text = pageson.find('.pagination-info').text();
	var pageDefault = obj.pageDefault(info_text,tabId);
	pageson.find('.pagination-info').html(pageDefault);
}
/**
 * 方法的说明 数组浅拷贝
 * @return void
 * @pars void
 * @author作者 zlx
 * @date 日期 2019.03.26
 */
Array.prototype.clone = function(){
    let a=[];
    for(let i=0,l=this.length;i<l;i++) {
        a.push(this[i]);
    }
    return a;
}
/**
 * 方法的说明 master一览检索  window.onresize下边线问题
 * @return void
 * @pars tabId
 * @author作者 zlx
 * @date 日期 2019.04.22
 */
function resizeTableLine(tabId){
	//添加onwindow事件
	window.onresize = function(){
		var timeoutId;
		clearTimeout(timeoutId);
		timeoutId = setTimeout(function(){
			var total = $('#'+tabId).datagrid('getData').total;
			if(total === 0){
				$('.datagrid-view').css('height','40px');
			}
		}, 100);
	}
}

function goBackcostlist(){
	url = "jczh/cost_list.html?view=6";//此处拼接内容
	window.location.href  = $.getJumpPath()+url;
}

/**
 * 方法的说明 检查日期的有效性
 * @return boolean
 * @pars String
 * @author作者 zlx
 * @date 日期 2019.05.30
 */
function checkDateV(date){
	var reg_number = /^((?:19|20)\d\d)-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$/;
	if(date.match(reg_number)){
		return (new Date(date).getDate()==date.substring(date.length-2));
	}else{
		return false;
	}
}
//日期yyyy-mm的有效性
function checkDateV_h(date){
	var reg_number = /^((?:19|20)\d\d)-(0[1-9]|1[012])$/;
	if(date.match(reg_number)){
		return true;
	}else{
		return false;
	}
}

//yyyy-mm 验证
function format_u(date){
	if(date == null){
		return;
	}
    var y = date.getFullYear();
    var m = date.getMonth()+1;
    return y+'-'+(m<10?('0'+m):m);
}

function parser_u(s){
    var reg_number2 = /^((?:19|20)\d\d)(0[1-9]|1[012])$/;
	var reg_heng2 = /^((?:19|20)\d\d)-(0[1-9]|1[012])$/;
	var reg_shu2 = /^((?:19|20)\d\d)\/(0[1-9]|1[012])$/;
	if(s.match(reg_number2)){
		var y = s.substr(0,4);
		var m = s.substr(4,2);
		return new Date(y,m-1);
	}else if(s.match(reg_heng2)){
		var ss = s.split('-');
		var m = parseInt(ss[1],10);
		var y = parseInt(ss[0],10);
		return new Date(y,m-1);
	}else if(s.match(reg_shu2)){
		var ss = s.split('/');
		var m = parseInt(ss[1],10);
		var y = parseInt(ss[0],10);	
		return new Date(y,m-1);
	}else{
		return new Date();
	}
}

function loopFunForjobdetail(data, listArr, arr) {
	var data = data;
	for(var i = 0; i < arr.length; i++) {
		data = formatStatusForjobdetail(data, listArr[i], arr[i]);
	}
	return data;
}

function formatStatusForjobdetail(data, list, colname) {

	var val = "";
	var valEn = "";
	var valJp = "";
	var valHk = "";
	switch(colname) {
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
	}
	//objStorage.reData = data;
	return data;
}
//JS函数用于获取url参数
function getQueryVariable(variable)
{
	var query = window.location.search.substring(1);
	var vars = query.split("&");
	for (var i=0;i<vars.length;i++) {
		var pair = vars[i].split("=");
		if(pair[0] == variable){return pair[1];}
	}
	return(false);
}

function taxrate()
{
	$('label.btn.btn-primary.activeT').siblings('label').css('display','none');
	$('label.btn.btn-primary').removeClass('activeT');
}

function taxrateJobDetail(){
	$('label.btn.btn-primary.out_first_sr').next().css('display','none')
	$('label.btn.btn-primary.out_first_sr').removeClass('active')
	$('label.btn.btn-primary.out_first_sr').removeClass('out_first_sr')
}
function helpDocument() {
	var langtyp = $.getLangTyp();
	window.open($.getJumpPath()+'public/helppdf/helpdocument'+langtyp+'.pdf');
}
function getForgMoneyUnit(list,itemID)
{
	var toplange = $('#language').val();
	var lang = "";
	if(toplange != "zc")
	{
		lang = toplange;
	}
	if(toplange == "zt")
	{
		lang = "hk";
	}
	var moneyTyp = "";
	for(var i = 0;i < list.length;i++)
	{
		if(list[i].itemcd == itemID)
		{
			if(lang != "")
			{
				moneyTyp =  list[i]['itemname'+lang];
			}else{
				moneyTyp =  list[i]['itmname'+lang];
			}
			break;
		}
	}
	return moneyTyp;
}
/**
 * 方法的说明 检查日期的有效性
 * @return void
 * @pars void
 * @author作者 zlx
 * @date 日期 2020.04.01
 */
function delete_validate_red() {
	$("input[type='text'].required,input[type='password'].required,input[type='number'].required,input[type='email'].required,select.required,.date-box-required,.cal-cost-box,.cal-sale-box").each(function (index, element) {
		if (element.style.borderColor == 'red'){
			element.style.borderColor = "#cccccc";
			//销毁tooltip事件
			$(element).tooltip('destroy');
		}
	})
	$("input[type='text']#invoiceNo.required_flag").each(function (index, element) {
		if (element.style.borderColor == 'red'){
			element.style.borderColor = "#cccccc";
			//销毁tooltip事件
			$(element).tooltip('destroy');
		}
	})
}
/**
 * 方法的说明 解决alert弹框多国语言问题
 * @return void
 * @author zlx
 * @date 2020.05.06
 */
function information_alert_language() {
	//bug 确认框，汉语条件下 也显示的日文
	if ($.messager){
		if(localStorage.getItem('language') == 'jp' || localStorage.getItem('language') == '001'){
			if($('.messager-window .dialog-button .l-btn-text').exist()){
				$('.messager-window .dialog-button .l-btn-text').text('はい');
			}
		}else if(localStorage.getItem('language') == 'zc' || localStorage.getItem('language') == '002'){
			if($('.messager-window .dialog-button .l-btn-text').exist()){
				$('.messager-window .dialog-button .l-btn-text').text('是');
			}
		}
	}
}
/**
 * 方法的说明 给 job、原价一览 增加选中状态 
 * @return void
 * @author xy
 * @date 2020.05.22
 */
function reselect(type,index){
	if(window.cost_sort){
		return ;
	}else{
		for(let i = 0; i<index.length;i++){
			$(type).datagrid('selectRow',index[i]);
		}
	}
}

/**
 * 方法说明 job一览滚动条事件函数抽离
 * @return void
 * @author zlx
 * @date 2020.05.27
 */
function job_scroll_fun() {
	$('.datagrid-pager-top').width($('.datagrid .panel-body').width()-32);
	//获取当前滚动栏scroll的高度并赋值
	var top_job = $('.datagrid-pager-top').offset().top;
	var topTitle = $('.datagrid-header').offset().top;
	var table_cl_top = $('.datagrid-wrap.panel-body.panel-body-noheader').offset().top;
	var CONST_N = 152.66,CONST_T = 150;
	if(topTitle < CONST_N) {
		$('.datagrid-pager-top').addClass('table_fix_top');
		$('.datagrid-header').addClass('table_fix_header');
		$('.datagrid-view').addClass('top-40');
		$(".pagination-next").click(function(){
			$('.datagrid-pager-top').removeClass('table_fix_top');
			$('.datagrid-header').removeClass('table_fix_header');
			$('.datagrid-view').removeClass('top-40');
		})
		$(".pagination-last").click(function(){
			$('.datagrid-pager-top').removeClass('table_fix_top');
			$('.datagrid-header').removeClass('table_fix_header');
			$('.datagrid-view').removeClass('top-40');
		})
		$(".l-btn-icon-left").click(function(){
			$('.datagrid-pager-top').removeClass('table_fix_top');
			$('.datagrid-header').removeClass('table_fix_header');
			$('.datagrid-view').removeClass('top-40');
		})
		$(".l-btn-empty").click(function(){
			$('.datagrid-pager-top').removeClass('table_fix_top');
			$('.datagrid-header').removeClass('table_fix_header');
			$('.datagrid-view').removeClass('top-40');
		})
	}
	if(table_cl_top > CONST_T){ //否则清空悬浮
		$('.datagrid-pager-top').removeClass('table_fix_top');
		$('.datagrid-header').removeClass('table_fix_header');
		$('.datagrid-view').removeClass('top-40');
	}
}
//将小数位科学计算法表示数转数字
function scientificToNumber(num) {  
  if(num == "null" || num == ''|| num == null) {
          return num;
      }
  else{
  var str = num.toString();
  var reg = /^(\d+)(e)([\-]?\d+)$/;
  var arr, len,
    zero = '';

  /*6e7或6e+7 都会自动转换数值*/
  if (!reg.test(str)) {
    return num;
  } else {
    /*6e-7 需要手动转换*/
    arr = reg.exec(str);
    len = Math.abs(arr[3]) - 1;
    for (var i = 0; i < len; i++) {
      zero += '0';
    }
    return '0.' + zero + arr[1];
  }
  }
}
