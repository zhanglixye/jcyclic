$(function(){	
	var eurl =$.getJumpPath()+"common/employ_retrieval.html";
	$.layerShow("searchCus",eurl);
	initTitle();
	var ren_width = (($('.usermanage').parent().width() + 30)*0.666666666667 - 68)/2 - 28;
	$('.usermanage').width(ren_width);
	$('#customcd').change(function(){
		initTitle();
	}); 
})
$(function() {
	var cells = document.getElementById('monitor').getElementsByTagName('div');
	var day1 = document.getElementById('curday');
	var day7 = document.getElementById('curday7');
	var clen = cells.length;
	var currentFirstDate;
	var myDate = new Date();
	countNum = 0;
	//获取当前日期
	var formatDate1 = function(ss) {
		var year = ss.getFullYear() + '年';
		var month = (ss.getMonth() + 1) + '月';
		var day1 = ss.getDate();
		var week = '(' + ['星期一', '星期二', '星期三', '星期四', '星期五', '星期六', '星期天'][ss.getDay()] + ')';
		return day1;
	};
	var formatDate = function(date) {
		var year = date.getFullYear() + '年';
		var month = (date.getMonth() + 1) + '月';
		var day = date.getDate();
		if(day < 10) {
			day = '0' + day; //补齐
		}
		var week = '(' + ['星期一', '星期二', '星期三', '星期四', '星期五', '星期六', '星期天'][date.getDay()] + ')';
		return day;
	};
	var addDate = function(date, n) {
		date.setDate(date.getDate() + n);
		return date;
	};
	var addDate1 = function(ss, n) {
		ss.getDate() + n;
		return ss;
	};
	var checkDate =  function(){
		var dateTime = $('td.padright div').text();
		var month = new Date().getMonth() + 1,day=new Date().getDate();
		month<10?month = '0'+month:month=month;
		day<10?day='0'+day:day=day;
		var nowDate = month + '\/' + day;
		if(nowDate != dateTime){
			return true;
		}else{
			return false;
		}
	}
	//params aa ->正  往后几天  负  往前几天
	function fun_date(aa){
        var date1 = new Date(),
        time1=date1.getFullYear()+"-"+(date1.getMonth()+1)+"-"+date1.getDate();//time1表示当前时间
        var date2 = new Date(date1);
        date2.setDate(date1.getDate()+aa);
        var month = date2.getMonth()+1;
        var day = date2.getDate();
        if(month < 10){
        	month = '0'+month;
        }
        if(day < 10){
        	day = '0'+day;
        }
        var time2 = month+"/"+day;
        return time2;
    }
	//日期向前的逻辑判断
	var checkDatePrev = function(){
		var prevDate = fun_date(-7*11);
		var rightDate = $('td.padright div').text();
		if(prevDate == rightDate){
			return true;
		}else{
			return false;
		}
	}
	//判断当天是星期几
	var weekCheck = function(){
		var week = new Date().getDay();
		switch (week){
			case 0:
				var arr = ['月','火','水','木','金','土','日'];
				break;
			case 1:
				var arr = ['火','水','木','金','土','日','月'];
				break;
			case 2:
				var arr = ['水','木','金','土','日','月','火'];
				break;
			case 3:
				var arr = ['木','金','土','日','月','火','水'];
				break;
			case 4:
				var arr = ['金','土','日','月','火','水','木'];
				break;
			case 5:
				var arr = ['土','日','月','火','水','木','金'];
				break;
			case 6:
				var arr = ['日','月','火','水','木','金','土'];
				break;
			default:
//				console.log('星期出错');
				break;
		}
		for(var i = 0;i < arr.length;i++){
			$('.week-top ul li').eq(i).text(arr[i]);
		}
	}
	var setDate = function(date) {
		var week = date.getDay();
		//date = addDate(date, week * -1 + 1);
		date = addDate(date,-6);
		currentFirstDate = new Date(date);
		for(var i = 0; i < clen; i++) {
			var today = myDate.getDate();
			if(today < 10) {
				today = '0' + today; //补齐
			}
			var mo = new Array();
			var wwek = date.getMonth() + 1;
			var twwek = myDate.getMonth() + 1;
			var a = i - 1;
			cells[i].innerHTML = formatDate(i == 0 ? date : addDate(date, 1));
			mo[i] = parseInt(cells[i].innerHTML);
			if(i > 0) {
				var mo0 = new Array();
				mo0[i] = cells[a].innerHTML.substr(-2);
				mo0[i] = parseInt(mo0[i]);
				if(mo[i] < mo0[i]) {
					wwek = wwek + 1;
					twwek = twwek + 1;
				}
			}
			if(wwek < 10) {
				wwek = '0' + wwek; //补齐
			}
			if(twwek < 10) {
				twwek = '0' + twwek; //补齐
			}
			cells[i].innerHTML = wwek + '/' + cells[i].innerHTML;
			if(cells[i].innerHTML == twwek + '/' + today) {
				cells[i].className = 'mark1';
			} else {
				cells[i].className = '';
			};
		}
	};
	$('#last-week').click(lastWeekEvent);
	$('#next-week').click(nextWeekEvent);
	setDate(new Date());
	weekCheck();	
	//默认解除事件
	//$('#next-week').unbind('click');
	$('#next-week').css('cursor','default');
	//next week添加事件函数
	function nextWeekEvent(e){
		if($(e.target).hasClass('unactive2')){
			return;
		}
		if(checkDate()){
			setDate(addDate(currentFirstDate, 13));	
			countNum--;
			$('.week-bottom').eq(countNum).removeClass('hidden');
			$('.week-bottom').eq(countNum).siblings('.week-bottom').addClass('hidden');
		}
		if(!checkDate()){
			$('.data-right').addClass('unactive2');
			//$('#next-week').unbind('click');
			$('#next-week').css('cursor','default');
		}
		if(!checkDatePrev()){
			$('.data-left').removeClass('unactive');
			//$('#last-week').click(lastWeekEvent);
			$('#last-week').css('cursor','pointer');
		}
		var text_val = (function () {
			var right = $(e.target);
			var textValue =right.siblings('.data-body').find('table #monitor td:last-of-type').text().replace(/(^\s+)|(\s+$)/g,"");
			var newtime=newdate(textValue);
			inittimesheet(newtime);
		} ())
	}
	//last week添加事件函数
	function lastWeekEvent(e){
		if($(e.target).hasClass('unactive')){
			return;
		}
		if(!checkDatePrev()){
			setDate(addDate(currentFirstDate, -1));
			countNum++;
			$('.week-bottom').eq(countNum).removeClass('hidden');
			$('.week-bottom').eq(countNum).siblings('.week-bottom').addClass('hidden');
		}
		if(checkDatePrev()){
			$('.data-left').addClass('unactive');
			//$('#last-week').unbind('click');
			$('#last-week').css('cursor','default');
		}
		if(checkDate()){
			$('.data-right').removeClass('unactive2');
			//$('#next-week').click(nextWeekEvent);
			$('#next-week').css('cursor','pointer');
		}
		var text_val = (function () {
			var right = $(e.target);
			var textValue =right.siblings('.data-body').find('table #monitor td:last-of-type').text().replace(/(^\s+)|(\s+$)/g,"");
			var newtime=newdate(textValue);
			inittimesheet(newtime);
		} ())
	}
});
function initTitle() { /**上传文件input样式  -- 按需调用*/
	file_i18n();
	var path = $.getAjaxPath()+"seljobColumns";	
	var pars = {};
	$.ajax({
		url:path,
		data:JSON.stringify(pars),
		headers: {"requestID":$.getRequestID(),"requestName":$.getRequestNameByJcZH()},
		success:function(data){
           if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
           		//数据填充到表格中
           		//var dataColumns=data.data.slice(0,10);
           		var dataColumns=data.data;
           	   initDataGridHandler('timesheet_list','',null,'',false,'isHasFn',dataColumns,false,true);
		       $.resetRequestID(data[$.getRequestUserInfoName()].requestID);
		       //初始化时间表格 获取所有日期数组
		       inittimesheet('');
	           }else{
	           	showErrorFunHandler(data[$.getRequestMetaName()].result,"ERROR","ERROR");
	           }
       },
       error:function(data){
       		showErrorHandler("CONNECTION_SERVER_ERROR","ERROR","ERROR");
       }
	});
	
	
}
function inittimesheet(datetime){			
		var path = $.getAjaxPath()+"TimesheetQuery";
		var date = new Date();//获取当前时间
   		var today = date.format("yyyy-MM-dd");//日期
   		var userid =$('#customcd').val();
		if(datetime!=null&&datetime!=''){
			today=datetime;
		}
		var timesheet = '1';
		var pars = {
			jobenddate:today,
			userid:userid,
			timesheet:timesheet
		};
		$.ajax({
		url:path,
		headers: {"requestID":$.getRequestID(),"requestName":$.getRequestNameByJcZH()},
		data:JSON.stringify(pars),
		success:function(data){		   
           if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
				var total = data[$.getRequestDataName()].joblist.length;
				auto_table(total);
				
				var dataFil = {
					total:total,
					rows:data[$.getRequestDataName()].joblist
				}
				if(total<=0){
//					showErrorHandler('NO_FOUND_CLMST','info','info');
					$('#timesheet_list').datagrid('loadData',dataFil);
					$('.datagrid-view').height('40px');
					$('.time_sheet_info').removeClass('hidden');
					$('.time_sheet_info').text(part_language_change_new("TABLE_CHECK_NO"));
				}
				getGridLanguagetext('timesheet',data[$.getRequestDataName()].timesheetuser);
				var level = data[$.getRequestDataName()].timesheetuser[0].level;
				var name = data[$.getRequestDataName()].timesheetuser[0].departname+" | " + data[$.getRequestDataName()].timesheetuser[0].userid + " | " + data[$.getRequestDataName()].timesheetuser[0].username;
				var usernum = $('#customcd').val();
				if(usernum==null||usernum==''){
					$('#customname').val(name);	
				}
				
				var powerList = data['userInfo'].uNodeList;
//	            var bl = isHavePower(powerList, [65]);
//	            var b2 = isHavePower(powerList, [66]);
	            var b3 = isHavePower(powerList, [67]);
				if(b3||data[$.getRequestDataName()].companycd=='1'){//一般
					$('.usermanage').addClass('hidden');
					$('.userone').removeClass('hidden');
					$('#part').text(data[$.getRequestDataName()].timesheetuser[0].departname);
					$('#userid').text(data[$.getRequestDataName()].timesheetuser[0].userid);
					$('#username').text(data[$.getRequestDataName()].timesheetuser[0].username);
					$(".userone #customcolor").css("color",data[$.getRequestDataName()].timesheetuser[0].colorv);
				}
				//小人颜色
				var backcolor = $('#colorv').val();
				if(backcolor==null || backcolor==''){
				$("#customcolor").css("color",data[$.getRequestDataName()].timesheetuser[0].colorv);					
				}
				if(backcolor=='1'){
					$("#customcolor").css("color",'#ec1717');
				}
				if(dataFil.total != 0){
					$('#timesheet_list').datagrid('loadData',dataFil);
					$('.time_sheet_info').addClass('hidden');
					$('.time_sheet_info').text(part_language_change_new("TABLE_CHECK_NO"));
				}				
				//右侧表格赋值
				timesheetlist(data[$.getRequestDataName()].joblist)
				$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
				for(var i = 0; i < 7; i++) {
					$('.week .week-bottom:not(".hidden") .week-bottom-content').eq(i).each(function() {
						$(this).find("input").blur();
					});
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
function add(){
	var ListArray = [];
	var jobtrnlis =$('#timesheet_list').datagrid('getRows');
	if(jobtrnlis.length<1){
		showErrorHandler("TIME_SHEET_NULL","ERROR","ERROR");
		return;
	}
	for(var j=0;j<jobtrnlis.length;j++){
		var jobcd=jobtrnlis[j].jobcd;
		var s=j+1;//从1开始
		var daylist=".week-bottom:not(.hidden) .week-bottom-content ul li:nth-child("+s+") input"
			daylist=$(daylist);
		var weekFlag = $('.week-bottom:not(.hidden)').attr('value');
		var arr = new Array();
		for(var k = 0;k < daylist.length;k++){
			arr.push(daylist.eq(k).val());
			}
		var newarr = arr;//新的数组
		for(var i = 0;i < newarr.length;i++){
			var date = new Date();//获取当前时间
	  		date.setDate(date.getDate()-7*weekFlag - i);//从第一天开始，83代表表格中一共有84天
	   		var day = date.format("yyyy-MM-dd");//日期
	   		var hours = newarr[i];//值		
			if(hours==''){//过滤没填工数的日期
				hours=0;
			}
			var ListColumnList = {
					jobcd:jobcd,
					daynum:day,
					timenum:hours
					};
				ListArray.push(ListColumnList);
		}
	}
	var path = $.getAjaxPath()+"TimesheetInsert";
	var pars = {
		insertlist:ListArray
	};
	 $.ajax({
	  type: "POST",
	  url:path,
	  contentType:"application/json",
	  data:JSON.stringify(pars),
	  dataType:"JSON",
	  headers: {"requestID":$.getRequestID(),"requestName":$.getRequestNameByJcZH()},
	  success:function(data){	        
	           if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
	           showErrorHandler("EXECUTE_SUCCESS","info","info");
	           }else{
	            	showErrorFunHandler(data[$.getRequestMetaName()].result,"ERROR","ERROR");
	           }	
	           $.resetRequestID(data[$.getRequestUserInfoName()].requestID);
	       },
	       error:function(data){
	       		showErrorHandler("CONNECTION_SERVER_ERROR","ERROR","ERROR");
	       }
	 });
	
}
function auto_table(total){
	//len变量需要后台提供返回的数据长度
	var len = total;
	var str = '';
	for(var i = 1;i <= len;i++){
		str+="<li><input type='text' class=row"+i+"></li>"
	}
	str += "<li></li>";
	for(var i = 0;i < 7;i++){
		$('.week .week-bottom:not(".hidden") .week-bottom-content ul').eq(i).html(str);
	}
	work_time_sum();	
}
function work_time_sum(){
	for(var i = 0; i < 7; i++) {
		$('.week .week-bottom:not(".hidden") .week-bottom-content').eq(i).each(function() {
			$(this).find("input").blur(function() {
				var r = /^\d*(?:.\d{0,2})?$/;　 //正整数 
				if($(this).val() != '' && !r.test($(this).val())) {
					$(this).val("");
					$(this).focus(); //正则表达式不匹配置空 
				} else if($(this).val() > 24 || isNaN($(this).val()/1)) {
					$(this).val("");
					$(this).focus(); //正则表达式不匹配置空 
				} else{
					var lis = $(this).parent().parent().find("li");
					var inputs = lis.find("input");
					var sum = 0;
					for(var a = 0; a < inputs.length; a++) {
						sum += Number(inputs[a].value);
					}
					if(sum > 24) {
						$(this).val("");
						$(this).focus();
					} else {
						if(sum == 0){
							sum = '';
						}
						$(this).parent().parent().children("li:last-child").html(sum);
					}
				}
			});
			//绑定oninput事件
			$(this).find("input").keyup(function() {
				var r = /^\d*(?:.\d{0,2})?$/;　 //正整数 
				if($(this).val() != '' && !r.test($(this).val())) {
					$(this).val("");
					$(this).focus(); //正则表达式不匹配置空 
				} else if($(this).val() > 24 || isNaN($(this).val()/1)) {
					$(this).val("");
					$(this).focus(); //正则表达式不匹配置空 
					$(this).parent().parent().children("li:last-child").html('');
				} else{
					var lis = $(this).parent().parent().find("li");
					var inputs = lis.find("input");
					var sum = 0;
					for(var a = 0; a < inputs.length; a++) {
						sum += Number(inputs[a].value);
					}
					if(sum > 24) {
						$(this).val("");
						$(this).focus();
					} else {
						if(sum == 0){
							sum = '';
						}
						$(this).parent().parent().children("li:last-child").html(sum);
					}
				}
			});
		});
	}
}
function timesheetlist(list){
	//填值
	for(var i=0;i<list.length;i++){
		var Listtrn =list[i].timesheettrn;
		for(var j=0;j<Listtrn.length;j++){
			editimelist(i,Listtrn[j].JOB_DATE,Listtrn[j].TIME_PER)
		}	
	}
	//控制不可输入部分
	for(var i=0;i<list.length;i++){
		var delflg = list[i].delflg;
		if(delflg==1){//删除状态
			editimeDisabled(i,list[i].adddate,list[i].upddate);
		}else{
			editimeDisabled(i,list[i].adddate,list[i].jobenddate);
		}
		
	}
}
function editimelist(row,data,date){
	var now = new Date().format("yyyy-MM-dd");
	//计算相差的天数
	var setDateNum = getDays(data,now);
	//计算响应的模块位置
	var e = parseInt(setDateNum/7);
	//计算day的值
	var day = setDateNum%7;
	$('.week-bottom').eq(e).find('.week-bottom-content').eq(day).find('ul li').eq(row).find('input').val(date);
}
function editimeDisabled(rowNum,data,dataT){
	data = new Date(data).format("yyyy-MM-dd");
	dataT = new Date(dataT).format("yyyy-MM-dd");
	var row = rowNum;
	var rowT = rowNum;
	var now = new Date().format("yyyy-MM-dd");
	//计算相差的天数
	var d = new Date();
	d.setMonth(new Date(dataT).getMonth() + 2)
	var month = d.getMonth() + 1;
	if(month < 10){
		month = '0' + month;
	}
	dataT = d.getFullYear()+'-'+month+'-'+'01'
	var setDateNum = getDays(data,now);
	var setDateNumT = getDays(dataT,now);
	//计算响应的模块位置
	var e = parseInt(setDateNum/7);
	var eT = parseInt(setDateNumT/7);
	//计算day的值
	var day = setDateNum%7;
	var dayT = setDateNumT%7;
	var len = $('.week-bottom').eq(e).nextAll('.week-bottom').length;
	var lenT = $('.week-bottom').eq(eT).prevAll('.week-bottom').length;
	if(len == 0){
		var week_dom = $('.week-bottom').eq(e).find('.week-bottom-content').eq(day).nextAll('.week-bottom-content');
		var week_len = $('.week-bottom').eq(e).find('.week-bottom-content').eq(day).nextAll('.week-bottom-content').length;
		for(var i= 0;i < week_len;i ++){
			week_dom.eq(i).find('ul li').eq(row).find('input').attr("disabled",true);
		}
	}else{
		//同模块
		var week_dom = $('.week-bottom').eq(e).find('.week-bottom-content').eq(day).nextAll('.week-bottom-content');
		var week_len = $('.week-bottom').eq(e).find('.week-bottom-content').eq(day).nextAll('.week-bottom-content').length;
		for(var i= 0;i < week_len;i ++){
			week_dom.eq(i).find('ul li').eq(row).find('input').attr("disabled",true);
		}
		//其他模块
		row++;
		var str = 'li:nth-child('+row+')';
		$('.week-bottom').eq(e).nextAll('.week-bottom').find('.week-bottom-content').find(str).find('input').attr("disabled",true);
	}
	if(setDateNumT > 0){
		//月份
		if(lenT == 0){
			var week_domT = $('.week-bottom').eq(eT).find('.week-bottom-content').eq(dayT).prevAll('.week-bottom-content');
			var week_lenT = $('.week-bottom').eq(eT).find('.week-bottom-content').eq(dayT).prevAll('.week-bottom-content').length;
			$('.week-bottom').eq(eT).find('.week-bottom-content').eq(dayT).eq(i).find('ul li').eq(rowT).find('input').attr("disabled",true);
			for(var i= 0;i < week_lenT;i ++){
				week_domT.eq(i).find('ul li').eq(rowT).find('input').attr("disabled",true);
			}
		}else{
			//同模块
			var week_domT = $('.week-bottom').eq(eT).find('.week-bottom-content').eq(dayT).prevAll('.week-bottom-content');
			var week_lenT = $('.week-bottom').eq(eT).find('.week-bottom-content').eq(dayT).prevAll('.week-bottom-content').length;
			$('.week-bottom').eq(eT).find('.week-bottom-content').eq(dayT).eq(i).find('ul li').eq(rowT).find('input').attr("disabled",true);
			for(var i= 0;i < week_lenT;i ++){
				week_domT.eq(i).find('ul li').eq(rowT).find('input').attr("disabled",true);
			}
			//其他模块
			rowT++;
			var str = 'li:nth-child('+rowT+')';
			$('.week-bottom').eq(eT).prevAll('.week-bottom').find('.week-bottom-content').find(str).find('input').attr("disabled",true);
		}
	}	
}
//获得两个日期之间相差的天数
function getDays(date1 , date2){
	var date1Str = date1.split("-");//将日期字符串分隔为数组,数组元素分别为年.月.日
	//根据年 . 月 . 日的值创建Date对象
	var date1Obj = new Date(date1Str[0],(date1Str[1]-1),date1Str[2]);
	var date2Str = date2.split("-");
	var date2Obj = new Date(date2Str[0],(date2Str[1]-1),date2Str[2]);
	var t1 = date1Obj.getTime();
	var t2 = date2Obj.getTime();
	var dateTime = 1000*60*60*24; //每一天的毫秒数
	var minusDays = Math.floor(((t2-t1)/dateTime));//计算出两个日期的天数差
	var days = minusDays;//取绝对值
	return days;
}
function newdate(datetime){
	var myDate = new Date();
	var tyear= myDate.getFullYear();
	var tmonth = datetime.substring(0,2);
	var tday = datetime.substring(3,5);
	var newday = tyear+"-"+tmonth+"-"+tday;
	return newday;
}
function back() {
//	sessionStorage.clear();
	window.history.back(-1); 
};