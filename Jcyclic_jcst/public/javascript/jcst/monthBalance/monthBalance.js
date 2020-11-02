function mouseOver(){
	$(".underline").css("text-decoration","underline");
}
function mouseOut(){
	$(".underline").css("text-decoration","none");
}
function mouseOverf(){
	$(".underlinef").css("text-decoration","underline");
}
function mouseOutf(){
	$(".underlinef").css("text-decoration","none");
}
function mouseOvers(){
	$(".underlines").css("text-decoration","underline");
}
function mouseOuts(){
	$(".underlines").css("text-decoration","none");
}
//$(document).ready(function(){
//	$('.point').hover(
//		function(){
//			$(this).addClass('background-color');
//		},function(){
//			$(this).removeClass('background-color');	
//	})
//})
$(function(){
	monthBalanceInitHandler();
})
function monthBalanceInitHandler()
{
	var pars = {};
	var path = $.getAjaxPath()+"monthBalance";
	
	$.ajax({
		timeout:0,
		url:path,
		headers: {"requestID":$.getRequestID(),"requestName":$.getRequestNameByJcZH()},
		data:JSON.stringify(pars),
		success:function(data){
           if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
           	$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
           	if(data[$.getRequestDataName()] != null)
           	{
           		for(var i in data[$.getRequestDataName()]) {
       				if( i.match("Total") || i.match("Amt") )
       				{
       					$('#'+i).html(formatNumber(data[$.getRequestDataName()][i]));
       				}else{
       					$('#'+i).html(data[$.getRequestDataName()][i]);	
       				}
				}
           		objStorage.personMoneyCode = data[$.getRequestUserInfoName()];
           		setPersonMoneyCode(objStorage.getPersonMoneyCode());
           		if(data[$.getRequestDataName()].isSellconfirmFlg == 1)
           		{
           			$('.point').eq(1).addClass('hidden');
           		}
           		if(data[$.getRequestDataName()].isCostFinshFlg == 1)
           		{
           			$('.point').eq(2).addClass('hidden');
           		}
           	}else{
           		showErrorHandler("PAGE_INIT_FAIL","ERROR","ERROR");
           		window.history.back(-1); 
           	}
           }else{
           	showErrorFunHandler(data[$.getRequestMetaName()].result,"ERROR","ERROR");
           }
       },
		error:function(data){
	   		showErrorFunHandler("CONNECTION_SERVER_ERROR","ERROR","ERROR");
		}
	});
}
function checkOutMonth()
{
	var pars = {};
	var msg = "";
	var confirmTitle = $.getConfirmMsgTitle();
	var path = $.getAjaxPath()+"monthCheckOut";
	if($('#sysLockFlg').val() == "unlock")
	{
		showErrorHandler("SYSTEM_UNLOCK","ERROR","ERROR");
		return ;
	}
	msg = showConfirmMsgHandler("SURE_MONTH_CHECK_OUT");
	$.messager.confirm(confirmTitle,msg, function(r){
		if(r)
		{
			$.ajax({
				url:path,
				headers: {"requestID":$.getRequestID(),"requestName":$.getRequestNameByJcZH()},
				data:JSON.stringify(pars),
				success:function(data){
		           if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
		           	$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
		           	if(data[$.getRequestDataName()] == 1)
		           	{
		           		//结账成功
		           		var path = "jcst/top_registration.html";
		           		showInfoMsgHandler("EXECUTE_SUCCESS","SUCCESS","info",path,"");
		           		 
		           	}else if(data[$.getRequestDataName()] == 0){
		           		showErrorHandler("CAN_NOT_MONTHCHECKOUT","ERROR","ERROR");
		           	}else{
		           		showErrorHandler("EXECUTE_FAIL","ERROR","ERROR");
		           	}
		           }else{
		           		showErrorFunHandler(data[$.getRequestMetaName()].result,"ERROR","ERROR");	
		           }
		       },
				error:function(data){
			   		showErrorFunHandler("CONNECTION_SERVER_ERROR","ERROR","ERROR");
				}
			});
		}
	});
	$('.panel.window.panel-htop.messager-window').addClass('different');
}
function searchJobListByMonthCheckOut(pageFlg,parName,statusCode)
{
	var path = $.getJumpPath()
	if(pageFlg == "job")
	{
		path += "jcst/job_registration_list.html?backPage=checkOut&view=";
		path += pageFlg+"&"+parName+"="+statusCode;
	}else{
		path += "jcst/cost_list.html?backPage=checkOut&view="+statusCode;
	}
	location.href = path;
}
objStorage = {
	personMoneyCode: null,
	getPersonMoneyCode: function() {
		return objStorage.personMoneyCode;
	}
}