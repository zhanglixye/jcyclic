$(function() {
	var surl =$.getJumpPath()+"common/suppliers_retrieval.html";
	$.layerShow("searchClient",surl);
	reportInit();
	//暂时的代码
//	setTimeout(function(){
//		$('.time_two a').click()
//		$('#startDate').datebox('setValue',$('#endDate').datebox('getText'));
//		$('.second_cont_one a').click()
//		$('.second_cont_one a').click()
//		$('#dlvDate').next('span').find("input[type='text']").blur();
//	})
	
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
			} else {
				showErrorFunHandler(data[$.getRequestMetaName()].result, "ERROR", "ERROR");
			}
		},
		error: function(msg) {
			window.location.href = $.getJumpPath();
		}
	});
}
$(function(){
	//初始化操作
	var initialize_time = $.getAccountDate();//会计月
	//var initialize_time = '2019/01';
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
//	console.log(a);
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
//	$('#endDate').datebox().datebox('calendar').calendar({
//		validator: function(date) {	
//			if(date <= initialize_time_c){
//				return date;
//			}
//		}
//	});
	$('#dlvDate').datebox().datebox('calendar').calendar({
		validator: function(date) {	
			if(date <= cldiv_cd_time_c){
				return date;
			}
		}
	});
	//默认显示当前月份
	$('#startDate').datebox('setValue',startDate_time+'-01');	
	$('#endDate').datebox('setValue',startDate_time+'-01');
	$('#dlvDate').datebox('setValue',cldiv_cd_time+'-01');
	//防止清空重新赋值
	$('#startDate').datebox('setText',startDate_time);	
	$('#endDate').datebox('setText',startDate_time);
	$('#dlvDate').datebox('setText',cldiv_cd_time);
	//$("#endDate").attr('data_fl',1);
})
function exportExcelOnChoose(radiosName){
	if(radiosName == "radiosTop" && !checkDateIsNull())
	{
		return ;
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
	if(fileName != "")
	{
		exportExcelReport(fileName,endDate,startDate,cldivID);	
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
		monthList.each(function(index,ele){
			var month = parseInt($(ele).text());
			var Cdate = new Date(year+'-'+month+'-01 00:00:00');
			//if(Cdate <= initialize_time_e){
				$(ele).css('opacity','1');
				$(ele).css('cursor','pointer');
			//}else{
			//	$(ele).css('opacity','0.6');
			//	$(ele).css('cursor','default');
			//}
		})
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
	}
	//后退绑定
	$('#dlvDate').datebox('panel').find('.calendar-nav.calendar-menu-prev').click(function(){
		//后退 1 前进 0
		objV.d(getAccDate_t($.getAccountDate()),1);
	})
	//前进绑定
	$('#dlvDate').datebox('panel').find('.calendar-nav.calendar-menu-next').click(function(){
		//后退 1 前进 0
		objV.d(getAccDate_t($.getAccountDate()),0);
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
