$(function(){
	isCheckLogin(1);
	//初始化操作
	var initialize_time = $.getAccountDate();//会计月
	//f_month_event(initialize_time);
	var surl =$.getJumpPath()+"common/suppliers_retrieval.html";
	$.layerShow("searchClient",surl);
	reportInit();
	toolTipLanguage();
});
var getKuaiDate = function(obj)
{
	var d = obj.datebox('getText')+'-01';
	obj.datebox('setValue',d);
}
function reportInit()
{
	var pdfInfo = {fileName:"reportInit"}
	$.ajax({
		url: $.getAjaxPath() + "exportExcel",
		data: JSON.stringify(pdfInfo),
		headers: {"requestID": $.getRequestID(),"requestName": $.getRequestNameByJcZH()},
		success: function(data) {
			if(data[$.getRequestMetaName()].result == $.getRequestStatusName()) {
				$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
				if(data[$.getRequestDataName()] != "error")
				{
					//201912
					var maxD = data[$.getRequestDataName()];
					var maxDate;
					var sss = (maxD == '' || maxD == undefined)?null:maxD;
					if (sss !== null){
						var year = sss.substr(0,4);
						var month = sss.substr(4,2);
						maxDate = new Date(year + '-' +month);
					}
					window.max_date_global = maxDate;
					var initialize_time = $.getAccountDate();//会计月
					f_month_event(initialize_time,maxDate);
					//radio 绑定change事件
					// 点击事件change
					$('input[type="radio"][name="radiosBottom"]').change(function () {
						// 获取input radio选中值，方法二
						var my_value = $(this).val();
						if (my_value == 'madeCostList'){
							var new_d = f_add_one_month($.getAccountDate().replace('/','-'));
							var new_d_r = new_d.replace('-','/');
							//console.log(new_d_r);
							f_month_event(new_d_r,maxDate);
							window.radioChangeFlag = true;
							var new_date = f_sub_one_month(initialize_time);
							$('#dlvDate').datebox('setValue',new_date);
							var new_strDate = initialize_time.replace('/','-')+"-01";
							$('#startDate').datebox('setValue',new_strDate);
							$('#endDate').datebox('setValue',new_strDate);
							
						}else {
							f_month_event(initialize_time,maxDate);
							window.radioChangeFlag = false;
						}
					});
				}else{
					showErrorHandler("PAGE_INIT_FAIL","ERROR","ERROR");
	           		window.history.back(-1); 
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

function exportExcelOnChoose(radiosName){
	if(radiosName == "radiosTop" && !checkDateIsNull())
	{
		return ;
	}
	var flg = validataRequired();
	if(!flg) {
		return;
	}
	var radios = $("input[type='radio']input[name = '" + radiosName + "']:checked");
	var fileName = radios.val();
	//页面元素ID反了，开始日和结束日需要反着取
	var startDate = $('#endDate').datebox('getText')+ "-" + "01";	
	var endDate = "";
	if(radiosName == "radiosTop")
	{
		endDate = $('#startDate').datebox('getText') + "-" + "01";
	}else{
		endDate = $('#dlvDate').datebox('getText') + "-" + "01";
	}
	var cldivID = $('#cldiv_cd').val();
	var   accountCd = $('#cldiv_name').val();
	if(fileName != "")
	{
		exportExcelReport(fileName,endDate,startDate,cldivID,accountCd);	
	}		
}
function checkDateIsNull()
{
	var start = $('#endDate').datebox('getText');
	var end = $("#startDate").datebox('getText');
	var strat_number = start.split("-")[1];
	var end_number = end.split("-")[1];
	var start_year = start.split("-")[0];
	var end_year = end.split("-")[0];
	if(end==""){
		$(".first_time1 .datebox").css("border","1px solid red");
		return false;
	}
	if(end_year - start_year == 0){
		if(end_number<strat_number){
			$(".first_time1 .datebox").css("border","1px solid red");
			$(".time_two .datebox").css("border","1px solid red");
			return false;
		}else if(end_number - strat_number >= 3){
			$(".first_time1 .datebox").css("border","1px solid red");
			$(".time_two .datebox").css("border","1px solid red");
			return false;
		}else{
			$(".first_time1 .datebox").css("border","");
			$(".time_two .datebox").css("border","");
		}
	}else if(end_year - start_year != 0){
		if(end_year<start_year){
			$(".first_time1 .datebox").css("border","1px solid red");
			$(".time_two .datebox").css("border","1px solid red");
			return false;
		}else if(end_number - strat_number == -10 || end_number - strat_number == -11){
			$(".first_time1 .datebox").css("border","");
			$(".time_two .datebox").css("border","");			
		}else{
			$(".first_time1 .datebox").css("border","1px solid red");
			$(".time_two .datebox").css("border","1px solid red");
			return false;
		}
	}
	return true;
}

function validateShowInit(){
	var objV = {};
	//开始日validate展示显示
	objV.s = function(endDate,num){
		if(endDate.split('-')[1] == '11'){
				var ert = '01';
				var ert2 = Number(endDate.split('-')[0]) + 1;
			}else if(endDate.split('-')[1] == '12'){
				var ert = '02';
				var ert2 = Number(endDate.split('-')[0]) + 1;
			}else{
				var ert =  Number(endDate.split('-')[1]) + 2<10?'0'+(Number(endDate.split('-')[1]) + 2):Number(endDate.split('-')[1]) + 2;
				var ert2 = endDate.split('-')[0];
			}
		var a = ert2 +'-'+ ert + '-01 00:00:00';//小三个月
		var monthDateS = new Date(a);
		var monthDateE = new Date(endDate+'-01 00:00:00');
		var p = $('#startDate').datebox('panel');
		var monthList = p.find('div.calendar-menu-month-inner td');
		//删除不用的按钮
		p.find('.calendar-nav.calendar-prevmonth,.calendar-nav.calendar-nextmonth,.calendar-nav.calendar-nextyear,.calendar-nav.calendar-prevyear').remove();
		if(num == 1){
			var year = Number(p.find('.calendar-menu-year').val()) - 1;
		}else if(num == 0){
			var year = Number(p.find('.calendar-menu-year').val()) + 1;
		}else{
			var year = p.find('.calendar-menu-year').val();
		}	
		monthList.each(function(index,ele){
			var month = parseInt($(ele).text());
			//2019-11-20 repair
            if(isNaN(month)){
                    month = index + 1;
            }
			var Cdate = new Date(year+'-'+month+'-01 00:00:00');
			if(Cdate <= monthDateS && Cdate >= monthDateE){
				$(ele).css('opacity','1');
				$(ele).css('cursor','pointer');
			}else{
				$(ele).css('opacity','0.6');
				$(ele).css('cursor','default');
			}
		})
	}
	//后退绑定
	$('#startDate').datebox('panel').find('.calendar-nav.calendar-menu-prev').click(function(){
		//后退 1 前进 0
		objV.s($('#endDate').datebox('getText'),1);
	})
	//前进绑定
	$('#startDate').datebox('panel').find('.calendar-nav.calendar-menu-next').click(function(){
		//后退 1 前进 0
		objV.s($('#endDate').datebox('getText'),0);
	})
	//结束日validate展示显示
	objV.e = function(endDate,num){
		var initialize_time_e = new Date(endDate);
		var p = $('#endDate').datebox('panel');
		var monthList = p.find('div.calendar-menu-month-inner td');
		//删除不用的按钮
		p.find('.calendar-nav.calendar-prevmonth,.calendar-nav.calendar-nextmonth,.calendar-nav.calendar-nextyear,.calendar-nav.calendar-prevyear').remove();
		if(num == 1){
			var year = Number(p.find('.calendar-menu-year').val()) - 1;
		}else if(num == 0){
			var year = Number(p.find('.calendar-menu-year').val()) + 1;
		}else{
			var year = p.find('.calendar-menu-year').val();
		}
		if (window.max_date_global == undefined){
			monthList.each(function(index,ele){
				var month = parseInt($(ele).text());
				var Cdate = new Date(year+'-'+month+'-01 00:00:00');
				$(ele).css('opacity','1');
				$(ele).css('cursor','pointer');
			})
		}else {
			monthList.each(function(index,ele){
				var month = parseInt($(ele).text());
				var Cdate = new Date(year+'-'+month+'-01 00:00:00');
				if(Cdate > window.max_date_global){
					$(ele).css('opacity','1');
					$(ele).css('cursor','pointer');
				}else{
					$(ele).css('opacity','0.6');
					$(ele).css('cursor','default');
				}
			})
		}
	}
	//后退绑定
	$('#endDate').datebox('panel').find('.calendar-nav.calendar-menu-prev').click(function(){
		//后退 1 前进 0
		objV.e($.getAccountDate(),1);
	})
	//前进绑定
	$('#endDate').datebox('panel').find('.calendar-nav.calendar-menu-next').click(function(){
		//后退 1 前进 0
		objV.e($.getAccountDate(),0);
	})
	//结束日validate展示显示
	objV.d = function(endDate,num){
		var initialize_time_e = new Date(endDate);
		var p = $('#dlvDate').datebox('panel');
		var monthList = p.find('div.calendar-menu-month-inner td');
		//删除不用的按钮
		p.find('.calendar-nav.calendar-prevmonth,.calendar-nav.calendar-nextmonth,.calendar-nav.calendar-nextyear,.calendar-nav.calendar-prevyear').remove();
		if(num == 1){
			var year = Number(p.find('.calendar-menu-year').val()) - 1;
		}else if(num == 0){
			var year = Number(p.find('.calendar-menu-year').val()) + 1;
		}else{
			var year = p.find('.calendar-menu-year').val();
		}
		if (window.max_date_global == undefined){
			monthList.each(function(index,ele){
				var month = parseInt($(ele).text());
				var Cdate = new Date(year+'-'+month+'-01 00:00:00');
				if(Cdate <= initialize_time_e){
					$(ele).css('opacity','1');
					$(ele).css('cursor','pointer');
				}else{
					$(ele).css('opacity','0.6');
					$(ele).css('cursor','default');
				}
			})
		}else {
			monthList.each(function(index,ele){
				var month = parseInt($(ele).text());
				var Cdate = new Date(year+'-'+month+'-01 00:00:00');
				if(Cdate <= initialize_time_e && Cdate > window.max_date_global){
					$(ele).css('opacity','1');
					$(ele).css('cursor','pointer');
				}else{
					$(ele).css('opacity','0.6');
					$(ele).css('cursor','default');
				}
			})
		}
	}
	//后退绑定
	$('#dlvDate').datebox('panel').find('.calendar-nav.calendar-menu-prev').click(function(){
		//后退 1 前进 0
		if (window.radioChangeFlag){
			objV.d($.getAccountDate().replace('/','-'),1);
		}else {
			objV.d(getAccDate_t($.getAccountDate()),1);
		}
	})
	//前进绑定
	$('#dlvDate').datebox('panel').find('.calendar-nav.calendar-menu-next').click(function(){
		//后退 1 前进 0
		if (window.radioChangeFlag){
			objV.d($.getAccountDate().replace('/','-'),0);
		}else {
			objV.d(getAccDate_t($.getAccountDate()),0);
		}
	})
	return objV;	
}
function getAccDate_t(initialize_time){
	var initialize_time_cldiv_year = initialize_time.split("/")[0];
	var initialize_time_mouth= initialize_time.split("/")[1];
	var initialize_time_cldiv = initialize_time.split("/")[1]-1 == 0 ? 12:initialize_time.split("/")[1]-1;
	if(initialize_time_cldiv == 12){
		initialize_time_cldiv_year -= 1;
	}
	var startDate_time = initialize_time_cldiv_year +"-"+initialize_time_mouth;
	if(initialize_time_cldiv < 10){
		var cldiv_cd_time =  initialize_time_cldiv_year +"-"+ "0" + initialize_time_cldiv;
	}else{
		var cldiv_cd_time =  initialize_time_cldiv_year +"-"+ initialize_time_cldiv;
	}
	return cldiv_cd_time;
}
//会计月日期控制
function f_month_event(initialize_time,maxDate) {
	var initialize_time_cldiv_year = initialize_time.split("/")[0];
	var initialize_time_mouth= initialize_time.split("/")[1];
	var initialize_time_cldiv = initialize_time.split("/")[1]-1 == 0 ? 12:initialize_time.split("/")[1]-1;
	if(initialize_time_cldiv == 12){
		initialize_time_cldiv_year -= 1;
	}
	var startDate_time = initialize_time.split("/")[0] +"-"+initialize_time_mouth;
	if(initialize_time_cldiv < 10){
		var cldiv_cd_time =  initialize_time_cldiv_year +"-"+ "0" + initialize_time_cldiv;
	}else{
		var cldiv_cd_time =  initialize_time_cldiv_year +"-"+ initialize_time_cldiv;
	}
	//日期控件的范围选择
	var b = startDate_time;//结束日期
	if(b.split('-')[1] == '11'){
		var ert = '01';
		var ert2 = Number(b.split('-')[0]) + 1;
	}else if(b.split('-')[1] == '12'){
		var ert = '02';
		var ert2 = Number(b.split('-')[0]) + 1;
	}else{
		var ert = Number(b.split('-')[1]) + 2<10?'0'+(Number(b.split('-')[1]) + 2):Number(b.split('-')[1]) + 2;
		var ert2 = b.split('-')[0];
	}
	var a = ert2 +'-'+ ert + '-01 00:00:00';//小三个月
	var aa = new Date(a);
	var bb = new Date(b+'-01 00:00:00');//结束日期
	var initialize_time_c = new Date(initialize_time);
	var cldiv_cd_time_c = new Date(cldiv_cd_time);
	$('#startDate').datebox().datebox('calendar').calendar({
		validator: function(date) {
			if(date <= aa && date >= bb){
				return date;
			}
		}
	});
	if (maxDate == undefined){
		$('#dlvDate').datebox().datebox('calendar').calendar({
			validator: function(date) {
				if(date <= cldiv_cd_time_c){
					return date;
				}
			}
		});
	}else {
		$('#endDate').datebox().datebox('calendar').calendar({
			validator: function(date) {
				if(date > maxDate){
					return date;
				}
			}
		});
		$('#dlvDate').datebox().datebox('calendar').calendar({
			validator: function(date) {
				if((date <= cldiv_cd_time_c) && (date > maxDate)){
					return date;
				}
			}
		});
	}
	//默认显示当前月份
	$('#startDate').datebox('setValue',startDate_time+'-01');
	$('#endDate').datebox('setValue',startDate_time+'-01');
	$('#dlvDate').datebox('setValue',cldiv_cd_time+'-01');
	//防止清空重新赋值
	$('#startDate').datebox('setText',startDate_time);
	$('#endDate').datebox('setText',startDate_time);
	$('#dlvDate').datebox('setText',cldiv_cd_time);
	if (maxDate != undefined){
        if (cldiv_cd_time_c.getTime() == maxDate.getTime()){
            $('#dlvDate').datebox('setValue','');
        }
    }
}
//会计月加1
function f_add_one_month(dataString) {
	var d=new Date(dataString);
	var m=d.getMonth()+2;
	var year = d.getFullYear();
	if (m < 10){
		m = '0' + m;
	}
	if(m>12){
		year = Number(d.getFullYear())+1;
		m = '01';
	}
	return year+'-'+m;
}
//会计月减1
function f_sub_one_month(dataString) {
	var d=new Date(dataString);
	var m=d.getMonth();
	var year = d.getFullYear();
	
	if (m == 0){
		m = '12'
		year = Number(d.getFullYear())-1;
	}else if(m<10){
			m = '0' + m;
	}
	return year+'-'+m+'-01';
}
//会计月
function f_one_month(dataString) {
	var d=new Date(dataString);
	var m=d.getMonth();
	var year = d.getFullYear();
	return year+'-'+m+'-01';
}
//tooltip
function toolTipLanguage(language){
	if(language == undefined){
		language = localStorage.getItem('language');
	}
	var toolTipO = part_language_change_new('ticket_output_tooltipR');
	$('.toolTipR').tooltip({
		position: 'right',
		content: '<span style=\"color:#fff\">'+toolTipO+'</span>',
		onShow: function(){
			$(this).tooltip('tip').css({
				backgroundColor: '#666',
				borderColor: '#666'
			});
		}
	});
}
