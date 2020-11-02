$(function(){
	//修改操作
	var flg =getQueryStringValue('flg');
//	//初始化转圈问题
//	if(flg!=0&&flg!=1){
//		showLockInfoMsgHandler("DATA_IS_NOT_EXIST","/master/taxcost_list.html");	
//		return;		
//	}
	if(flg==1){//变更
		changeinit();
		$('#taxadd').addClass('hidden')
		
	}else{
		initform();
		$('#taxup').addClass('hidden')
	}
})
var locknum;
var userid;
function addaa() {
	if(!validataRequired())
	{
		return ;
	}

	var user_tax_type1 = getQueryStringValue('user_tax_type'); 
	var invoice_type1 = getQueryStringValue('invoice_type'); 
	var invoice_text1 = getQueryStringValue('invoice_text');
	var id = getQueryStringValue('id');
	var user_tax_type  = $("#userListone").val();
	var invoice_type  = $("#invoicetypeList").val();
	var invoice_text  = $("#invoicetextList").val();
	var deduction='1';//是否扣除
	var vat_rate  =floatObj.divide($(".vat_rate").val(),100);
	var del_flg  = 0;
	if ($("#del_flg").is(":checked")){
		del_flg  = $("#del_flg").val();
	} 
	//增值税率为0时，控除否，增值税率有值时，控除可。
	if(vat_rate==0){
		deduction='0'
	}
 	var allData = {
 			   
         	"payeetaxmst": {
             	"user_tax_type1": user_tax_type1,
             	"invoice_type1": invoice_type1,
             	"invoice_text1": invoice_text1,
             	"user_tax_type": user_tax_type,
             	"invoice_type": invoice_type,
             	"invoice_text": invoice_text, 
             	"vat_rate": vat_rate, 
             	"deduction":deduction,
             	"del_flg": del_flg,
             	"lock_flg": locknum,
             	"id":id,
             	"addusercd":userid,
             	"updusercd":userid
    				},
				"vat_rate": vat_rate, 
				"user_tax_type": user_tax_type,
             	"invoice_type": invoice_type,
             	"invoice_text": invoice_text
 				}
	if (user_tax_type1 !==null && user_tax_type1!=='' && user_tax_type1!==undefined){
		var path = $.getAjaxPath()+"PayeeTaxChange";
	}
	else{ 	
 	 	var path = $.getAjaxPath()+"PayeeTaxAdd";
	}
 $.ajax({
  type: "POST",
  url:path,
  contentType:"application/json",
  data:JSON.stringify(allData),
  dataType:"JSON",
  headers: {"requestID":$.getRequestID(),"requestName":$.getRequestNameByMst()},
  success:function(data){
        
           if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
           $.resetRequestID(data[$.getRequestUserInfoName()].requestID);
           if(data.data==-1){
           	showErrorHandler("PAYEEHAVE","info","info");
           	getRedc($("#userListone"),true);
           	getRedc($("#invoicetypeList"),true);
           	getRedc($("#invoicetextList"),true);
           	return false;
           }
           if(data.data==-2){
           	showLockInfoMsgHandler("STATUS_VALIDATEPOWER_ERROR","/master/taxcost_list.html");
           	return false;
           }
           //showErrorHandler("EXECUTE_SUCCESS","info","info");
           back();
           }else{
            	showErrorFunHandler(data[$.getRequestMetaName()].result,"ERROR","ERROR");
           }
       },
       error:function(data){
       		showErrorHandler("CONNECTION_SERVER_ERROR","ERROR","ERROR");
       }

 });
//	sessionStorage.clear();
	sessionStorage.removeItem('user_tax_type'); 
	sessionStorage.removeItem('invoice_type'); 
	sessionStorage.removeItem('invoice_text');
	sessionStorage.removeItem('id');
};
function initform(){
	$(".vat_rate").val('');
	var allData = {
		"payeetaxmst": {
		}
	}
 	var path = $.getAjaxPath()+"PayeeChangeinit";
	
 $.ajax({
  type: "POST",
  url:path,
  contentType:"application/json",
  data:JSON.stringify(allData),
  dataType:"JSON",
  headers: {"requestID":$.getRequestID(),"requestName":$.getRequestNameByMst()},
		success:function(data){
		
           if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
           	userid = data.userInfo.userID;
           var columnArr = new Array(); 
       			SelectObj.selectData =data[$.getRequestDataName()];
       			columnArr.push("userListone");
       			columnArr.push("invoicetypeList");
       			columnArr.push("invoicetextList");      			
       			SelectObj.setSelectID = columnArr;
       			SelectObj.setStringFlg="_";
       			SelectObj.setSelectOfLog();
            $.resetRequestID(data[$.getRequestUserInfoName()].requestID);
           }else{
           	showErrorFunHandler(data[$.getRequestMetaName()].result,"ERROR","ERROR");
           }
       },
       error:function(data){
       		showErrorHandler("CONNECTION_SERVER_ERROR","ERROR","ERROR");
       }
 });

}
function back() {
	//sessionStorage.clear();
	window.location.href = '../master/taxcost_list.html';
};
function changeinit(){
	//判断是否是修改
		var user_tax_type1 = getQueryStringValue('user_tax_type'); 
		var invoice_type1 = getQueryStringValue('invoice_type'); 
		var invoice_text1 = getQueryStringValue('invoice_text'); 
		var id = getQueryStringValue('id');
		var user_tax_type  = $("#userListone").val();
		var invoice_type  = $("#invoicetypeList").val();
		var invoice_text  = $("#invoicetextList").val();
		var vat_rate  = $(".vat_rate").val();
		var del_flg  = 0;
	if ($("#del_flg").is(":checked")){
		del_flg  = $("#del_flg").val();
	} 
		var allData = {  
         	"payeetaxmst": {
             	"user_tax_type1": user_tax_type1,
             	"invoice_type1": invoice_type1,
             	"invoice_text1": invoice_text1,
             	"user_tax_type": user_tax_type,
             	"invoice_type": invoice_type,
             	"invoice_text": invoice_text, 
             	"vat_rate": vat_rate, 
             	"del_flg": del_flg,
             	"id":id
    				}
 				}
 	 	
 	 	var path = $.getAjaxPath()+"PayeeChangeinit";
	
 $.ajax({
  	type: "POST",
  	url:path,
    contentType:"application/json",
    data:JSON.stringify(allData),
    dataType:"JSON",
	headers: {"requestID":$.getRequestID(),"requestName":$.getRequestNameByMst()},
		success:function(data){
		
           if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
           	userid = data.userInfo.userID;
           	//初始化转圈问题
	            if(data.data==null){
	            	$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
	            	showLockInfoMsgHandler("DATA_IS_NOT_EXIST","/master/taxcost_list.html");
		           	return false;
	            }
			//赋值
			var columnArr = new Array(); 
       			SelectObj.selectData =data[$.getRequestDataName()];       			
       			columnArr.push("userListone");
       			columnArr.push("invoicetypeList");
       			columnArr.push("invoicetextList");
       			SelectObj.setSelectID = columnArr;
       			SelectObj.setStringFlg="_";
       			SelectObj.setSelectOfLog();
			$("#userListone option[value='"+data[$.getRequestDataName()]['payeetaxmst'].user_tax_type+"']").prop("selected",true);
			$("#invoicetypeList option[value='"+data[$.getRequestDataName()]['payeetaxmst'].invoice_type+"']").prop("selected",true); 
			$("#invoicetextList option[value='"+data[$.getRequestDataName()]['payeetaxmst'].invoice_text+"']").prop("selected",true);
			var rate = data[$.getRequestDataName()]['payeetaxmst'].vat_rate;
			locknum = data[$.getRequestDataName()]['payeetaxmst'].lock_flg;
			rate= floatObj.multiply(rate,100);
			$(".vat_rate").val(rate);
			if(data[$.getRequestDataName()]['payeetaxmst'].deduction=='1'){
					$("input[name=delValid]:eq(1)").attr("checked",'checked');//0:不扣除；1：扣除
			}
			var del = data[$.getRequestDataName()]['payeetaxmst'].del_flg ;
			if (del==1)
 			{ 
				$('#del_flg').prop("checked",true);
			}
 			
			$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
           }else{
           	showErrorFunHandler(data[$.getRequestMetaName()].result,"ERROR","ERROR");
           }
       },
       error:function(data){
       		showErrorHandler("CONNECTION_SERVER_ERROR","ERROR","ERROR");
       }
 	});
	
}
function accMul(arg1, arg2) {
 var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
 try { m += s1.split(".")[1].length } catch (e) { }
 try { m += s2.split(".")[1].length } catch (e) { }
 return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m)
}