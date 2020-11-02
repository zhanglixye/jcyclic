$(function() {
	//var language = $('#language').val();
	initcuntomList(localStorage.getItem('language'),'2','0');
	var flag =getQueryStringValue('view');
	var flagtwo = getQueryStringValue('flg')
	//初始化转圈问题
	if(flag!=0 && flagtwo!=0 && flagtwo!=1){		
		showLockInfoMsgHandler("DATA_IS_NOT_EXIST","/jcst/top_registration.html");
		return;
	}
	if(flag=='0'){//导航进入一览,不显示返回按钮
		$("#cusconcost").addClass('hidden');
		$("#cusconjob").addClass('hidden');
	}
	
	if(flagtwo==1){//原价一览点进来的，默认是job
		$('.jobyl').removeClass('active');
		$('.costyl').addClass('active');
		$('div.tab-content').toggleClass('hidden');
	}
	$('.list_of_items').arrangeable({
		"dragEndEvent": "click"
	});
	$('ul.nav.nav-tabs').on('click','li',function(e){
		$(this).addClass('active');
		$(this).siblings('li').removeClass('active');
		if($(this).attr('role') == '1'){
			$('div.tab-content.cost').addClass('hidden');
			$('div.tab-content.job').removeClass('hidden');
		}else{
			$('div.tab-content.cost').removeClass('hidden');
			$('div.tab-content.job').addClass('hidden');
		}
	})
	$('.dropdown').eq(1).on('hidden.bs.dropdown', function showDropdown() {
		var language = $('#language').val();
    	initcuntomList(language,'2','0');
	});
	
	number();
	var jobclosenum;
	var costclosenum;
	initSwitchEvent();
	$(".language_conversion").bind('change', function() {
		//获取语言
		var lan = $('#language').val();
		if(lan=='zc'){
			lantype='COLUMN_SHOW_NAME';
		}
		else if(lan=='jp'){
			lantype='COLUMN_SHOW_NAME_JP';
		}
		else if(lan=='zt'){
			lantype='COLUMN_SHOW_NAME_HK';
		}
		else {
			lantype='COLUMN_SHOW_NAME_EN';
		}
		//job
        $('.job .customname').each(function(index){
        	$(this).html(languagetotal[index][lantype]);
        })   
        //cost
        $('.cost .customname').each(function(index){
        	$(this).html(languagetotalcost[index][lantype]);
        }) 
    });
});
//main.js等函数  初始化按钮运行
function jq_run_switch(){
	jQuery.getScript("../public/lib/boostrap-switch/highlight.js")
	    .fail(function() { 
//	        console.log('highlight.js加载失败');
		}); 
	jQuery.getScript("../public/lib/bootstrap3.3.0/bootstrap-switch.js")
	    .fail(function() { 
//	        console.log('bootstrap-switch.js加载失败');
		}); 
	jQuery.getScript("../public/lib/boostrap-switch/main.js") 
	    .done(function() { 
	        number();
	        initSwitchEvent();
	        $('.list_of_items').arrangeable({
				"dragEndEvent": "click"
			});
	    }) 
	    .fail(function() { 
//	        console.log('main.js加载失败');
		}); 
}
//switch事件初始化
function initSwitchEvent(){
		$('.listjob #switch-state').on('switchChange.bootstrapSwitch', function (e,state) {
			if(state) { //״̬Ϊon
				$(this).offsetParent().parent().parent().find(".list_of_items_no").attr("class", "list_of_items_no on");
				number();
			} else { //״̬Ϊ0FF
				if(flagCheck('.listjob #switch-state')){
					$(e.target).bootstrapSwitch('toggleState');
					showErrorHandler("SETONE","info","info");
					return;
				}
				$(this).offsetParent().parent().parent().find(".list_of_items_no").attr("class", "list_of_items_no off");
				number();
			}
	    });
		$('.listcost #switch-state').on('switchChange.bootstrapSwitch', function (e,state) {
			if(state) { //״̬Ϊon
				$(this).offsetParent().parent().parent().find(".list_of_items_no").attr("class", "list_of_items_no on");
				number();
			} else { //״̬Ϊ0FF
				if(flagCheck('.listcost #switch-state')){
					$(e.target).bootstrapSwitch('toggleState');
					showErrorHandler("SETONE","info","info");
					return;
				}
				$(this).offsetParent().parent().parent().find(".list_of_items_no").attr("class", "list_of_items_no off");
				number();
			}
	    });
	}
function number() {
 	//1即DESC从大到小，0即ASC从小到大
 	var orderIdArray = [];
 	var orderIdArrayT = [];
 	var idIndex = [];
 	var idIndexT = [];
 	var orderid = $(".tab-content.job").find(".list_of_items");
 	var orderidT = $(".tab-content.cost").find(".list_of_items");
 	orderid.each(function(i) {
 		var id = $(this).index();
 		idIndex[id] = i; //orderid的序号
 		orderIdArray.push(id); //orderid的值
 	});
 	orderidT.each(function(i) {
 		var id = $(this).index();
 		idIndexT[id] = i; //orderid的序号
 		orderIdArrayT.push(id); //orderid的值
 	});
 	orderIdArray = orderIdArray.sort(function(a, b) {
 		return (a > b) ? 1 : -1
 	}); //从小到大排序
 	orderIdArrayT = orderIdArrayT.sort(function(a, b) {
 		return (a > b) ? 1 : -1
 	}); //从小到大排序
 	//alert(orderIdArray+", "+idIndex);
 	var _length = orderIdArray.length;
 	for (var i = 0; i < _length; i++) {
 		$(".tab-content.job").find(".on").eq(i).html(i + 1);
 	}
 	var _lengthT = orderIdArrayT.length;
 	for (var i = 0; i < _lengthT; i++) {
 		$(".tab-content.cost").find(".on").eq(i).html(i + 1);
 	}
 };
function initcuntomList(language,level,type)
{
		var path = $.getAjaxPath()+"ListColumnQuery";
		if(type==1){
			var pars = {level:type};
		}else{
			var pars = {};
		}
		
		$.ajax({
		url:path,
		async:false,
		headers: {"requestID":$.getRequestID(),"requestName":$.getRequestNameByMst()},
		data:JSON.stringify(pars),
		success:function(data){
			$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
           	if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
				var total = data[$.getRequestDataName()]['listColumnList'];
				var totalcost = data[$.getRequestDataName()]['listColumnListCost'];
				languagetotal = data[$.getRequestDataName()]['listColumnList'];
				languagetotalcost = data[$.getRequestDataName()]['listColumnListCost'];
				var str = "";//JOB
				var str1 = "";//成本
				var nametype = "";
				if(language=='zc'){
					nametype='COLUMN_SHOW_NAME';
				}
				else if(language=='jp'){
					nametype='COLUMN_SHOW_NAME_JP';
				}
				else if(language=='zt'){
					nametype='COLUMN_SHOW_NAME_HK';
				}
				else {
					nametype='COLUMN_SHOW_NAME_EN';
				}
				for(var i = 0;i < total.length;i++)
				{
					str += '<div class="list_of_items listjob">'+
								'<div class="list_of_items_top">'+
									'<div class="list_of_items_top_left">'
					var flag = total[i].flag;
					if(flag=='0'){
						str += '<input data-on="success" class="boostrap-switch onoff" id="switch-state" type="checkbox" data-size="mini">'+
								'</div>'+
									'<div class="list_of_items_top_right">'+
										'<div class="list_of_items_no off">'
					}
					else{
						str += '<input data-on="success" class="boostrap-switch onoff" id="switch-state" type="checkbox" checked="checked" data-size="mini">'+
								'</div>'+
								'<div class="list_of_items_top_right">'+
									'<div class="list_of_items_no on">'
					}
					str +=			'1'+
								'</div>'+
							'</div>'+
							'</div>'+
							'<div class="list_of_items_bottom">'+
								'<div class="list_of_items_bottom_top" >'+
								'<span class="customid hidden">'+total[i].COLUMN_ID+'</span>'+
								'<span class="customname">'+total[i][nametype]+'</span>'+
								'</div>'+
								'<div style="position:relative" class="list_of_items_bottom_bottom">'+
									'<input type="number" oninput="this.value = max_val(this.value)" class="list_of_items_bottom_bottom_left bordN pixel" value="'+total[i].DEFAULT_WIDE+'">'+
									'<div class="list_of_items_bottom_bottom_right i18n" name="customlist_PIXEL">'+
											'PIXEL'+
									'</div>'+
								'</div>'+
							'</div>'+
						'</div>'				
						
				}
				for(var i = 0;i < totalcost.length;i++)
				{
					str1 += '<div class="list_of_items listcost">'+
								'<div class="list_of_items_top">'+
									'<div class="list_of_items_top_left">'
					var flag = totalcost[i].flag;
					if(flag=='0'){
						str1 += '<input data-on="success" class="boostrap-switch onoff" id="switch-state" type="checkbox" data-size="mini">'+
								'</div>'+
									'<div class="list_of_items_top_right">'+
										'<div class="list_of_items_no off">'
					}
					else{
						str1 += '<input data-on="success" class="boostrap-switch onoff" id="switch-state" type="checkbox" checked="checked" data-size="mini">'+
								'</div>'+
											'<div class="list_of_items_top_right">'+
												'<div class="list_of_items_no on">'
					}
										
					str1 += 		'1'+
								'</div>'+
							'</div>'+
							'</div>'+
							'<div class="list_of_items_bottom">'+
								'<div class="list_of_items_bottom_top" >'+
								'<span class="customid hidden">'+totalcost[i].COLUMN_ID+'</span>'+
								'<span class="customname">'+totalcost[i][nametype]+'</span>'+
								'</div>'+
								'<div style="position:relative" class="list_of_items_bottom_bottom">'+
									'<input type="number" oninput="this.value = max_val(this.value)" class="list_of_items_bottom_bottom_left bordN pixel" value="'+totalcost[i].DEFAULT_WIDE+'">'+
									'<div class="list_of_items_bottom_bottom_right i18n" name="customlist_PIXEL">'+
											'PIXEL'+
									'</div>'+
								'</div>'+
							'</div>'+
						'</div>'				
						
				}
				if(level==0){//job
					$('#myModalCuntom').empty();
					$('#myModalCuntom').append($(str));
					$("#shownumjob").val('20');
					jq_run_switch();
				}else if(level==1){//cost
					$('#myModalCuntomT').empty();
					$('#myModalCuntomT').append($(str1));
					$("#shownumcost").val('20');
					jq_run_switch();
				}else{//初始化
					$('#myModalCuntomT').empty();
					$('#myModalCuntomT').append($(str1));
					$('#myModalCuntom').empty();
					$('#myModalCuntom').append($(str));
					selectNumber(total[0].SHOW_NUMBERS,'shownumjob');
					selectNumber(totalcost[0].SHOW_NUMBERS,'shownumcost');
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
function addcustom(){
	var level=$("ul.jobtypeone li.active a.jobtype").attr('value');//列表类型获取,job:0,成本:1;
	var cusnum;
	var a;
	if(level==0){
		cusnum = $('.listjob').get().length;
	}
	if(level==1){
		cusnum = $('.listcost').get().length;
	}
	var ListArray = [];	
	for(var i=0;i<cusnum;i++){
		if(level==0){
			a = $('.listjob').eq(i);
			var list_column_id = a.find('.list_of_items_bottom_top').find('span.customid').text();
			var column_show_name = a.find('.list_of_items_bottom_top').find('span.customname').text();
			var column_width = a.find('.list_of_items_bottom_bottom').find('input.pixel').val();
			var onflag = $('.listjob .boostrap-switch').eq(i).bootstrapSwitch('state');		
			var show_num=$("#shownumjob").val();
			var column = i+1;
			var flag = '0';
			if (onflag==true){
				flag = '1';
			}
		}
		if(level==1){
			a = $('.listcost').eq(i);
			var list_column_id = a.find('.list_of_items_bottom_top').find('span.customid').text();
			var column_show_name = a.find('.list_of_items_bottom_top').find('span.customname').text();
			var column_width = a.find('.list_of_items_bottom_bottom').find('input.pixel').val();
			var onflag = $('.listcost .boostrap-switch').eq(i).bootstrapSwitch('state');		
			var show_num=$("#shownumcost").val();
			var column = i+1;
			var flag = '0';
			if (onflag==true){
				flag = '1';
			}
		}
		
		var ListColumnList = {
					column_id:list_column_id,
					onflag:flag,
					column_width:column_width,
					level:level,
					show_numbers:show_num,
					column_order:column
				};
		ListArray.push(ListColumnList);

	}
	var path = $.getAjaxPath()+"ListColumnUpdate";
	var pars = {
		listcolumninput:ListArray
	};
	$.ajax({
	  type: "POST",
	  url:path,
	  contentType:"application/json",
	  data:JSON.stringify(pars),
	  dataType:"JSON",
	  headers: {"requestID":$.getRequestID(),"requestName":$.getRequestNameByMst()},
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
function init(){
//	if(!showConfirmMsgHandler('CUSTOM_INIT')){
//		return false;
//	};
	var msg = "";
	var confirmTitle = $.getConfirmMsgTitle();
	msg = showConfirmMsgHandler("CUSTOM_INIT");
	$.messager.confirm(confirmTitle,msg, function(r){
		if(r)
		{
			//列表类型获取,job:0,成本:1;
			var level=$("ul.jobtypeone li.active a.jobtype").attr('value');
			initcuntomList(localStorage.getItem('language'),level,'1');
		}
	});
	
}
function selectNumber(num,id){
	//var num = $("#num").val();   //获取input中输入的数字
	var numbers = $("#"+id).find("option"); //获取select下拉框的所有值
	for (var j = 1; j < numbers.length; j++) {
		if ($(numbers[j]).val() == num) {
			$(numbers[j]).attr("selected", "selected");
		}
	} 
}
function clserselect(id){
	$("#"+id).each(function(i){
        if ($(this).text() == "micorosft") {
            $(this).attr("selected", "");
        }
   });
}
//最大值500
function max_val(num){
	if(num > 500){
		num = num.substring(0,num.length-1);
		return num;
	}else{
		return num;
	}
}
