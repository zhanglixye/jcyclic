$(function(){	
	var eurl =$.getJumpPath()+"common/employ_retrieval.html";
	$.layerShow("searchCus",eurl);
	initTitle();
	var ren_width = (($('.usermanage').parent().width() + 30)*0.666666666667 - 68)/2 - 28;
	$('.usermanage').width(ren_width);
	$('#customcd').change(function(){
		initTitle();
	}); 
	//this is a dialog - made 19-11-20
	$.layerShowDiv('sum_report_btn','30%','auto',1,$('.sum_rep'));
	//获取当前日期是星期几
	var date = new Date()
	var month = date.getDay();
	if(month=="1"){
		$(".week-top ul").html("<li class='i18n' name='time_huo'></li>" +
				"<li class='i18n' name='time_shui'></li>" +
				"<li class='i18n' name='time_mu'></li>" +
				"<li class='i18n' name='time_jin'></li>" +
				"<li class='i18n' name='time_tu'></li>" +
				"<li class='i18n' name='time_ri'></li>" +
				"<li class='i18n' name='time_yue'></li>");	
	}
	if(month=="2"){
		$(".week-top ul").html("<li class='i18n' name='time_shui'></li>" +
				"<li class='i18n' name='time_mu'></li>" +
				"<li class='i18n' name='time_jin'></li>" +
				"<li class='i18n' name='time_tu'></li>" +
				"<li class='i18n' name='time_ri'></li>" +
				"<li class='i18n' name='time_yue'></li>" +
				"<li class='i18n' name='time_huo'></li>");	
	}
	if(month=="3"){
		$(".week-top ul").html("<li class='i18n' name='time_mu'></li>" +
		"<li class='i18n' name='time_jin'></li>" +
		"<li class='i18n' name='time_tu'></li>" +
		"<li class='i18n' name='time_ri'></li>" +
		"<li class='i18n' name='time_yue'></li>" +
		"<li class='i18n' name='time_huo'></li>" +
		"<li class='i18n' name='time_shui'></li>");	
	}
	if(month=="4"){
		$(".week-top ul").html("<li class='i18n' name='time_jin'></li>" +
				"<li class='i18n' name='time_tu'></li>" +
				"<li class='i18n' name=''>time_ri</li>" +
				"<li class='i18n' name='time_yue'></li>" +
				"<li class='i18n' name='time_huo'></li>" +
				"<li class='i18n' name='time_shui'></li>" +
				"<li class='i18n' name='time_mu'></li>");	
	}
	if(month=="5"){
		$(".week-top ul").html("<li class='i18n' name='time_tu'></li>" +
				"<li class='i18n' name='time_ri'></li>" +
				"<li class='i18n' name='time_yue'></li>" +
				"<li class='i18n' name='time_huo'></li>" +
				"<li class='i18n' name='time_shui'></li>" +
				"<li class='i18n' name='time_mu'></li>" +
				"<li class='i18n' name='time_jin'></li>");
	}
	if(month=="6"){
		$(".week-top ul").html("<li class='i18n' name='time_ri'></li>" +
				"<li class='i18n' name='time_yue'></li>" +
				"<li class='i18n' name='time_huo'></li>" +
				"<li class='i18n' name='time_shui'></li>" +
				"<li class='i18n' name='time_mu'></li>" +
				"<li class='i18n' name='time_jin'></li>" +
				"<li class='i18n' name='time_tu'></li>");	
	}
	if(month=="7"){
		$(".week-top ul").html("<li class='i18n' name='time_yue'></li>" +
				"<li class='i18n' name='time_huo'></li>" +
				"<li class='i18n' name='time_shui'></li>" +
				"<li class='i18n' name='time_mu'></li>" +
				"<li class='i18n' name='time_jin'></li>" +
				"<li class='i18n' name='time_tu'></li>" +
				"<li class='i18n' name='time_ri'></li>");	
	}
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
           	   initDataGridHandler('timesheet_list',999999,null,'',false,'isHasFn',dataColumns,false,true);
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
        	   if(data[$.getRequestDataName()].outputsheetqx=="0"){
        		   $("#sum_report_btn").hide()
        	   }
        	   
        		//var afterFormatMoney = formatMoney(data.data.joblist);
        	   //时区时间
        	   var sqdate = data.userInfo.dateCompanyZone;
        	   sqdate =sqdate.split(" ");
        	   var dateTime = sqdate[0];
        	   $('#endDate').datebox('setValue',dateTime);
				var total = data[$.getRequestDataName()].joblist.length;
				auto_table(total);
				var joblist =data[$.getRequestDataName()].joblist;
				//把本国货币小数点位数赋值到全局变量中
				var pointNumber = data['userInfo']['pointNumber'];
				var taxFormatFlg = data[$.getRequestDataName()]['taxFormatFlg'];
				var foreignFormatFlg = data[$.getRequestDataName()]['foreignFormatFlg'];
                jsflaglanguage =data[$.getRequestDataName()].allmap.jsflaglanguage;
		           reqflglanguage =data[$.getRequestDataName()].allmap.reqflglanguage;
		           recflglanguage =data[$.getRequestDataName()].allmap.recflglanguage;
		           invflglanguage =data[$.getRequestDataName()].allmap.invflglanguage;
		           assignflglanguage=data[$.getRequestDataName()].allmap.assignflglanguage;
                costfinishflglanguage=data[$.getRequestDataName()].allmap.costfinishflglanguage;
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
                
                
                
                
                
                
                joblist = loopFun(joblist,[jsflaglanguage,reqflglanguage,recflglanguage,invflglanguage,assignflglanguage,costfinishflglanguage],['jsflag','reqflg','recflg','invflg','assignflg','costfinishflg']);
                var newjoblist = lableSpan(joblist);
                var dataFil = {
                        total:total,
                        rows:newjoblist
                }
				
                
				window.total_time=false;
				if(total<=0){
//					showErrorHandler('NO_FOUND_CLMST','info','info');
					$('#timesheet_list').datagrid({loadFilter: pagerFilter}).datagrid('loadData',dataFil);
					$('.datagrid-view').height('40px');
					$('.time_sheet_info').removeClass('hidden');
					$('.time_sheet_info').text(part_language_change_new("TABLE_CHECK_NO"));
					window.total_time=true;
				}
				getGridLanguagetext('timesheet',data[$.getRequestDataName()].timesheetuser);
				var level = data[$.getRequestDataName()].timesheetuser[0].level;
				var name = data[$.getRequestDataName()].timesheetuser[0].departname+" | " + data[$.getRequestDataName()].timesheetuser[0].memberid + " | " + data[$.getRequestDataName()].timesheetuser[0].username;
				var usernum = $('#customcd').val();
				if(usernum==null||usernum==''){
					$('#customname').val(name);	
					$('#customcd').val(data[$.getRequestDataName()].timesheetuser[0].userid);
				}
				
				var powerList = data['userInfo'].uNodeList;
	            var bl = isHavePower(powerList, [65]);
//	            var b2 = isHavePower(powerList, [66]);
	            var b3 = isHavePower(powerList, [67]);
				if(b3||data[$.getRequestDataName()].companycd=='1'){//一般
					if(!bl){
						$('.usermanage').addClass('hidden');
						$('.userone').removeClass('hidden');
						$('#part').text(data[$.getRequestDataName()].timesheetuser[0].departname);
						$('#userid').text(data[$.getRequestDataName()].timesheetuser[0].userid);
						$('#username').text(data[$.getRequestDataName()].timesheetuser[0].username);
						$(".userone #customcolor").css("color",data[$.getRequestDataName()].timesheetuser[0].colorv);
					}
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
					$('#timesheet_list').datagrid({loadFilter: pagerFilter}).datagrid('loadData',dataFil);
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

//格式化 日期和金钱
function formatMoney(data) {
	for(var i = 0; i < data.length; i++) {
		//格式化金钱		
		data[i]['saleamt'] = formatNumber(data[i]['saleamt']);
		data[i]['vatamt'] = formatNumber(data[i]['vatamt']);
		data[i]['reqamt'] = formatNumber(data[i]['reqamt']);
		data[i]['plancostamt'] = formatNumber(data[i]['plancostamt']);
		data[i]['plansale'] = formatNumber(data[i]['plansale']);
		data[i]['costTotalAmt'] = formatNumber(data[i]['costTotalAmt']);
		data[i]['costVatTotal'] = formatNumber(data[i]['costVatTotal']);
		data[i]['payAmtSum'] = formatNumber(data[i]['payAmtSum']);
		data[i]['planpayamt'] = formatNumber(data[i]['planpayamt']);
		data[i]['profit'] = formatNumber(data[i]['profit']);
		data[i]['tax2'] = formatNumber(data[i]['tax2']);
		data[i]['tax3'] = formatNumber(data[i]['tax3']);
		data[i]['taxTotal'] = formatNumber(data[i]['taxTotal']);
		
	}
	return data;
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
			var usercd = $('#customcd').val();
			if(usercd==""){
				usercd=$('#userid').html();
			}
			var ListColumnList = {
					jobcd:jobcd,
					daynum:day,
					timenum:hours,
					usercd:usercd
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
		var jobuserdeflg =  list[i].jobuserdeflg
		if(delflg==1){//删除状态
			editimeDisabled(i,0,0);
		}else{
			if(jobuserdeflg==1){
				editimeDisabled(i,list[i].jobuseradddate,list[i].jobuserupdate);
			}else{
				editimeDisabled(i,list[i].jobuseradddate,list[i].jobenddate);
			}
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
	var row = rowNum;
	if(data==0&&dataT==0){
		for(var e=0;e<=11;e++){
			var week_dom = $('.week-bottom').eq(e).find('.week-bottom-content');
			var week_len = $('.week-bottom').eq(e).find('.week-bottom-content').length;
			for(var i= 0;i < week_len;i ++){
				week_dom.eq(i).find('ul li').eq(row).find('input').attr("disabled",true);
			}
		}
	}else{
		data = new Date(data).format("yyyy-MM-dd");
		var row = rowNum;
		var rowT = rowNum;
		//job结束时间
		var enddate1 ="";
		//用于存储结束日的模块位置
		var ende ="";
		//取到 结束日到当前时间有多少天 
		var endtate="";
		var now = new Date().format("yyyy-MM-dd");
		//计算相差的天数
		var d = new Date();
		d.setMonth(new Date(dataT).getMonth() + 2)
		var month = d.getMonth() + 1;
		if(month < 10){
			month = '0' + month;
		}
		var setDateNumend ="";
		//如果有结束时间
		if(dataT!=""){
			dataT = new Date(dataT).format("yyyy-MM-dd");
			setDateNumend = getDays(dataT,now) ;
			enddate1 =dataT;
			dataT = d.getFullYear()+'-'+month+'-'+'01'
			var endtate =  getDays(enddate1,now);
			//若job已经结束 则取结束日的模板位置
			ende= parseInt(endtate/7);
		}
		var setDateNum = getDays(data,now);
		var setDateNumT = getDays(dataT,now);
		//计算响应的模块位置
		var e = parseInt(setDateNum/7);
		var eT = parseInt(setDateNumT/7);
		
		
		
		//计算day的值
		var day = setDateNum%7;
		var dayT = setDateNumT%7;
		//计算需要对几个单元格操作
		var endDay = "";
		if(setDateNumend!=""){
			endDay=setDateNumend%7
		}
		var len = $('.week-bottom').eq(e).nextAll('.week-bottom').length;
		var lenT = $('.week-bottom').eq(eT).prevAll('.week-bottom').length;
		if(len == 0){
			if(endDay!=""){ 
				var week_dom = $('.week-bottom').eq(e).find('.week-bottom-content').eq(endDay).prevAll('.week-bottom-content');
				var week_len = $('.week-bottom').eq(e).find('.week-bottom-content').eq(endDay).prevAll('.week-bottom-content').length;
				for(var i= 0;i < week_len;i ++){
					week_dom.eq(i).find('ul li').eq(row).find('input').attr("disabled",true);
				}
			}
			var week_dom = $('.week-bottom').eq(e).find('.week-bottom-content').eq(day).nextAll('.week-bottom-content');
			var week_len = $('.week-bottom').eq(e).find('.week-bottom-content').eq(day).nextAll('.week-bottom-content').length;
			for(var i= 0;i < week_len;i ++){
				week_dom.eq(i).find('ul li').eq(row).find('input').attr("disabled",true);
			}
		}else{
			//同模块
			//若有结束日 则需要给 未来日后面的单元格进行隐藏操作
			if(endDay!=""||endDay==0){
				//如果结束日与开始日不在一个模板 则 取到结束日的模板位置 并赋隐藏
				if(ende!=e){
					//用于控制是否在结束日的模板位置  若在结束日后的模板则选择全部  若在结束日的模板位置  则 只对结束日后面的单元格进行隐藏 
					var jsdate = 0;
					for(ende;ende>=0;ende--){
						if(jsdate!=0){
							var week_dom = $('.week-bottom').eq(ende).find('.week-bottom-content');
							var week_len = $('.week-bottom').eq(ende).find('.week-bottom-content').length;
							for(var i= 0;i < week_len;i ++){
								week_dom.eq(i).find('ul li').eq(row).find('input').attr("disabled",true);
							}
						}
						else{
							var week_dom = $('.week-bottom').eq(ende).find('.week-bottom-content').eq(endDay).prevAll('.week-bottom-content');
							var week_len = $('.week-bottom').eq(ende).find('.week-bottom-content').eq(endDay).prevAll('.week-bottom-content').length;
							for(var i= 0;i < week_len;i ++){
								week_dom.eq(i).find('ul li').eq(row).find('input').attr("disabled",true);
							}
						}
						
						jsdate++;
					}
				}else{
					var jsdate = 0;
					for(ende;ende>=0;ende--){
					if(jsdate!=0){
							var week_dom = $('.week-bottom').eq(ende).find('.week-bottom-content');
							var week_len = $('.week-bottom').eq(ende).find('.week-bottom-content').length;
							for(var i= 0;i < week_len;i ++){
								week_dom.eq(i).find('ul li').eq(row).find('input').attr("disabled",true);
							}
					}else{
						var week_dom = $('.week-bottom').eq(e).find('.week-bottom-content').eq(endDay).prevAll('.week-bottom-content');
						var week_len = $('.week-bottom').eq(e).find('.week-bottom-content').eq(endDay).prevAll('.week-bottom-content').length;
						for(var i= 0;i < week_len;i ++){
							week_dom.eq(i).find('ul li').eq(row).find('input').attr("disabled",true);
							}
						}
						jsdate++;
					}
				}
			}
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
function outputtimesheet(){
	var startDate = $("#startDate").datebox('getValue');
	var endDate  = $("#endDate").datebox('getValue');
	var companyCD =$("#changeCompany").val();
	var userid = $.getUserID();
	var deptid = $.getDepartID();
	var LangTyp= $.getLangTyp();
	
	var par = {
			dlvmon_sta: startDate,
			dlvmon_end: endDate,
			departcd :deptid,
			company_cd:companyCD,
			userid:userid,
			LangTyp:LangTyp,
			timesheet:LangTyp
		}
	
	$.ajax({
		url: $.getAjaxPath() + "outPutTimeSheet",
		data: JSON.stringify(par),
		headers: {
			"requestID": $.getRequestID(),
			"requestName": $.getRequestNameByJcZH()
		},
		success: function(data) {
			if(data[$.getRequestMetaName()].result == $.getRequestStatusName()) {
				$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
				if(data[$.getRequestDataName()] != null) {
					if(data[$.getRequestDataName()] == "error") {
						showErrorHandler("OUT_EXCEL_ERROR", "ERROR", "ERROR");
					}else if (data[$.getRequestDataName()] == "DATE_RANGE_ERROR"){
						showErrorHandler("DATE_RANGE_ERROR", "ERROR", "ERROR");
					}else {
						var langTyp = $.getLangTyp();
						var fileName = getFileNameByExcel("TimeSheetReport");
//						if(langTyp=="jp") {
//							 fileName = getFileNameByExcel("TimeSheetReport");
//			    		}else if(langTyp=="en") {
//			    			fileName =getFileNameByExcel("時間表總計表");
//			    		}else if(langTyp=="zc") {
//			    			fileName = getFileNameByExcel("时间表总计表");
//			    		}else if(langTyp=="zt") {
//			    			fileName = getFileNameByExcel("時間表總計表");
//			    		}
						
						downLoadPdf(data[$.getRequestDataName()], fileName, "xlsx");
						$(".datagrid-view .datagrid-empty").hide();
					}
				} else {
					showErrorHandler("SYSTEM_ERROR", "ERROR", "ERROR");
				}
			} else {
				showErrorFunHandler(data[$.getRequestMetaName()].result, "ERROR", "ERROR");
			}
		},
//		error: function(msg) {
//			window.location.href = $.getJumpPath();
//		}
	});
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
