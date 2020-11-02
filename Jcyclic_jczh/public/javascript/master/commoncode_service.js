var lockflg =0;


//页面初始化时判断，是否是更新操作，若是更新，需查询数据
$(function() {
	isCheckLogin(1);
	var mstcd=urlPars.parm("mstcd");
	var itemcd=urlPars.parm("itemcd");
	var company_cd=urlPars.parm("company_cd");
	if(mstcd!=undefined&&itemcd!=undefined&&company_cd!=undefined){
		$('#mstcd').attr("disabled",true);
		$('#itemcd').attr("disabled",true);
		$('#titleName').attr('name','commoncode_registration_title_ch');
		part_language_change($('#titleName'));
		$('.fs18').attr('name','commoncode_registration_title_ch');  
		part_language_change($('.fs18'));
		$('.btn_change').attr('name','salescategoryList_change'); 
		part_language_change($('.btn_change'));
		var numbertest = /^[0-9]*$/;
		 if(!numbertest.test(company_cd)){
		 	url = "master/commoncode_list.html";
			showInfoMsgHandler("DATA_IS_NOT_EXIST","info","info",url);
			return;
		 }
		 if(!numbertest.test(mstcd)){
		 	url = "master/commoncode_list.html";
			showInfoMsgHandler("DATA_IS_NOT_EXIST","info","info",url);
			return;
		 }
		 if(!numbertest.test(itemcd)){
		 	url = "master/commoncode_list.html";
			showInfoMsgHandler("DATA_IS_NOT_EXIST","info","info",url);
			return;
		 }
		selectComByCd(mstcd,itemcd,company_cd);
		
	}else if(mstcd==undefined&&itemcd==undefined&&company_cd==undefined){
		//必须调用ajax，到后台查询。什么也不查，如果不调用ajax会导致，导航栏的会社选择为空。
		selectComByCd(null,null,null);
	}else{
		url = "master/commoncode_list.html";
		showInfoMsgHandler("DATA_IS_NOT_EXIST","info","info",url);
		return;
	}
})
//变更操作时，前置的查询赋值。
function selectComByCd(mstcd,itemcd,company_cd){
	var path = $.getAjaxPath()+"commonmstUpdSel";
	var pars = {"mstcd":mstcd,"itemcd":itemcd,"company_cd":company_cd};
	$.ajax({
		url:path,
		data:JSON.stringify(pars),
		headers: {"requestID":$.getRequestID(),"requestName":$.getRequestNameByMst()},
		success:function(data){
           if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
				 $.resetRequestID(data[$.getRequestUserInfoName()].requestID);
				var cof = data[$.getRequestDataName()];
				var pd =urlPars.parm("pd");
				//url路径修改
				if(mstcd!=undefined&&itemcd!=undefined&&company_cd!=undefined){
					if(cof.length<1){
					url = "master/commoncode_list.html";
					showInfoMsgHandler("DATA_IS_NOT_EXIST","info","info",url);
					return;
					}
				}
				
			    $.setInputVal(cof);
			    if(cof.length>0){
			    	 lockflg =cof[0]['lock_flg'];
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

//共通新增/修改的保存
function saleSave(){
	var pars = $.getInputVal();	
	if(!validataRequired()){
		return;
	}
	var mstcd= $('#mstcd').val();
	if(mstcd=='0050'){
		var change_utin= $('#change_utin').val();
		if(Number(change_utin)<=0){
		   showErrorHandler("CHANGE_UTIN","info","info");
		   return;
		}
		
	}
	var urlmstcd=urlPars.parm("mstcd");
	var urlitemcd=urlPars.parm("itemcd");
	var urlcompany_cd=urlPars.parm("company_cd");
	if(urlmstcd==undefined&&urlitemcd==undefined&&urlcompany_cd==undefined){//参数pd为0时，为追加
	var path = $.getAjaxPath()+"commonmstAdd";	
	}else if(urlmstcd!=undefined&&urlitemcd!=undefined&&urlcompany_cd!=undefined){//当参数pd为1的时候为变更。
	var path = $.getAjaxPath()+"commonmstUpd";	
	}else{
		//url路径修改
		url = "master/commoncode_list.html";
		showInfoMsgHandler("DATA_IS_NOT_EXIST","info","info",url);
		return;
	}
//	console.log($.getInputVal());
	//下面是执行的ajax。由于变更和追加数据都一样，只是借口不一样
	pars['commonmst'] = $.getInputVal();
	pars['lock_flg']=lockflg;
	pars['commonmst']['lock_flg']=lockflg;
	//获取状态位
	$.ajax({
		url:path,
		data:JSON.stringify(pars),
		headers: {"requestID":$.getRequestID(),"requestName":$.getRequestNameByMst()},
		success:function(data){
           if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
           	 $.resetRequestID(data[$.getRequestUserInfoName()].requestID);
			   if(data[$.getRequestDataName()] == "UPDATE_RALE_SAME")//验证返回的值没值，代表数据库没有mstcd，itemcd，company_cd相同的数据
	           	{   getRedc($("#itemcd"),true);
	           		getRedc($("#mstcd"),true);
	           		showErrorHandler('UPDATE_RALE_SAME','info','info');
		        }else if(data[$.getRequestDataName()]==2){
		        	 showLockInfoMsgHandler("STATUS_VALIDATEPOWER_ERROR","master/commoncode_list.html");
					 return;
		        }
	           	else if(data[$.getRequestDataName()]==1){
		           		//更新/新增保存成功
						url = $.getJumpPath()+"master/commoncode_list.html";//此处拼接内容
			            window.location.href = url;
		        }else{
				 	 	 showErrorHandler("EXECUTE_FAIL","error","error");
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
