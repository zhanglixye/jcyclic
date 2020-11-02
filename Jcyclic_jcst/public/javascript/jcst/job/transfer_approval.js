$(function(){
	//$.jobCommon();
	inittransferform();
	$.jobCommon();
	$.layerShowDiv('icon-tianjia1', '400', 'auto', 1, $('.payment_pay'));
	$.layerShowDiv('icon-sheding1','400','auto',1,$('.label_set_t'));
	$(".add_lable").click(function() {
		addLable("new_lable", "options_lable","1");
	});
	$(".filter-lable").click(function(){
	  var str = $("#lableStr").val();
	  filterLable(str,null);
 	})
	lableShowByPower();
//	//JOB弹出框
//	var saleAddFlag = sessionStorage.getItem("addflg");
//	if(saleAddFlag=='0'){
//	  $.layerShowDiv('job_detail','1000','650',2,'../common/business/job_detail/job_detail.shtml');
//	  sessionStorage.clear();
//	}
})
var locknum;
var trantype='';
function inittransferform(){
	var input_no =urlPars.parm('inputno');
	var job_cd =urlPars.parm('jobcd');
	var flag = urlPars.parm('flg')
	//正则验证不能输入日语
		var reg = /^[A-Za-z0-9]+$/;
        var r = input_no.match(reg);     
        if(r==null){
            showLockInfoMsgHandler("DATA_IS_NOT_EXIST","/jcst/cost_list.html?view=6");
			return false;    
        }
        var s = job_cd.match(reg);     
        if(s==null){
            showLockInfoMsgHandler("DATA_IS_NOT_EXIST","/jcst/cost_list.html?view=6");
			return false;    
        }
	//初始化转圈问题
	if(flag!=0 && flag!=1){		
		showLockInfoMsgHandler("DATA_IS_NOT_EXIST","/jcst/cost_list.html?view=6");
		return;
	}
	if(flag=='0'){//取消
		$('.approval').addClass('hidden');
		$('#menu32').removeClass('hidden');
		$('title').html(part_language_change_new('menu32'));
		$('#jobregistration_person').addClass('hidden');
		//readLanguageFile();
	}
	if(flag=='1'){//承认
		$('.cancel').addClass('hidden');
		$('#zhenti_approval').removeClass('hidden');
		trantype='1';
	}
//	if(input_no==null||input_no==''){
//		showConfirmMsgHandler("NOTFOUND");
//		goBackPageHandler();
//		
//	}
	$('#tfinput').text(input_no);
	$('#jobcd').text(job_cd);
	var allData = {
		"trantrn":{
			input_no:input_no,
			job_cd:job_cd
		}
	}
 	var path = $.getAjaxPath()+"TrantrnQuery";	
	 $.ajax({
	  type: "POST",
	  url:path,
	  contentType:"application/json",
	  data:JSON.stringify(allData),
	  dataType:"JSON",
	  headers: {"requestID":$.getRequestID(),"requestName":$.getRequestNameByJcZH()},
			success:function(data){			
	           if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
	           	//初始化转圈问题
	            if(data.data.TrantrnInput==null){
	            	$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
	            	showLockInfoMsgHandler("DATA_IS_NOT_EXIST","/jcst/cost_list.html?view=6");
		           	return false;
	            }
				$(".adddate").html(data.data.TrantrnInput.trantrnList[0].tran_date);
				$('.tranamt').html(formatNumber(data.data.TrantrnInput.trantrnList[0].tran_amt));
				$(".remark pre").html(data.data.TrantrnInput.trantrnList[0].remark);
				$(".tranname").html(data.data.TrantrnInput.trantrnList[0].tran_name);
				$("#job_name").text(data[$.getRequestDataName()]['CLDIV'][0]['jobname']);//项目名称
				locknum=data.data.TrantrnInput.trantrnList[0].lock_flg;
				var toplange = $('#language').val();
				var itmname;
				switch(toplange) {
				case "jp":
					itmname = data.data.TrantrnInput.trantrnList[0].itemname_jp;
					break;
				case "zc":
					itmname = data.data.TrantrnInput.trantrnList[0].itmname;
					break;
				case "zt":
					itmname = data.data.TrantrnInput.trantrnList[0].itemname_hk;
					break;
				case "en":
					itmname = data.data.TrantrnInput.trantrnList[0].itemname_en;
					break;
				};
				$(".saleMoment").html(itmname);	            
				//job信息区块，的得意先，与更新时间
				$.layerShowDiv('icon-i','1000','600',2,'../common/jcst/cost_list.shtml',job_cd);
				objStorage.personMoneyCode = data["userInfo"];
				setPersonMoneyCode();
				//获取PDF出力是否选中
				if(trantype==1){
					if(data.data.TrantrnInput.pdfflagpro==1){
						$("#PayConfirmOutPDF").attr("checked",true);
					}
				}	
				//登陆者和更新者模块
				$('#adduser').text(data.data.TrantrnInput.trantrnList[0].addusername)
				$('#adddate').text(data.data.TrantrnInput.trantrnList[0].adddate)
				$('#upduser').text(data.data.TrantrnInput.trantrnList[0].updateuser)
				$('#update').text(data.data.TrantrnInput.trantrnList[0].upddate)
				$("#addcolor").css("color",data.data.TrantrnInput.trantrnList[0].addusernamecolor);
				$("#updcolor").css("color",data.data.TrantrnInput.trantrnList[0].upusernamecolor);
				if(data.data.TrantrnInput.trantrnList[0].adddate==data.data.TrantrnInput.trantrnList[0].upddate){
					$('#updhidden').addClass('hidden');
				}
				$('#languageNameAdd').attr('name','zhenti_jobLogon');
				$('#languageNameUpd').attr('name','zhenti_jobUpdate');
				part_language_change($('#languageNameAdd'));
				part_language_change($('#languageNameUpd'));
				//小人颜色
				var jobupdusercolor = data[$.getRequestDataName()]['CLDIV'][0]['updusercolorv'];
				if(jobupdusercolor!=null&&jobupdusercolor!=''){
					$("#jobupdusercolor").css("color",data[$.getRequestDataName()]['CLDIV'][0]['updusercolorv']);
				}
				var costupdusercolor = data[$.getRequestDataName()]['sumcost'][0]['updusercolorv'];
				if(costupdusercolor!=null&&costupdusercolor!=''){
					$("#costupdusercolor").css("color",data[$.getRequestDataName()]['sumcost'][0]['updusercolorv']);
				}
				$("#divnm").text(data[$.getRequestDataName()]['CLDIV'][0]['divnm']);
				$("#upddate").text(data[$.getRequestDataName()]['CLDIV'][0]['upddate']);
				$("#username").text(data[$.getRequestDataName()]['CLDIV'][0]['username']);
				sessionStorage.setItem("addflg",data[$.getRequestDataName()]['CLDIV'][0]['saleAddFlag']);//壳上登录状态为1：已经登录  2：未登录
    			//$("#jobcd").text(data[$.getRequestDataName()]['CLDIV'][0]['jobcd']);
				$("#sumamt").text(formatNumber(data[$.getRequestDataName()]['sumcost'][0]['sumamt']));
				$("#updatetime").text(data[$.getRequestDataName()]['sumcost'][0]['updatetime']);
				$("#costupdname").text(data[$.getRequestDataName()]['sumcost'][0]['costupdname']);
				//标签数据重构
				SelectObj.setLableList = data.data.TrantrnInput.list_lable;
				SelectObj.setChooseLableList = data.data.TrantrnInput.lableList;
				labelToMySelect(data.data.TrantrnInput.list_lable);
				setLableArea(data.data.TrantrnInput.lableList);
				$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
				var jobcd =job_cd;
				
				var powerList = data['userInfo'].uNodeList;
	            var bl = isHavePower(powerList, [5,6,7,8]);
	            if(bl){
					if(data[$.getRequestDataName()]['CLDIV'][0]['saleAddFlag']=='1'){
						window.job_cd=jobcd;
						$.layerShowDiv('job_detail','70%','80%',2,'../jcst/job_detail.shtml');
						//sessionStorage.clear();
					}else{
						window.job_cd=jobcd;
						$.layerShowDiv('job_detail','70%','80%',2,'../jcst/job_detail_nosale.html');
						//sessionStorage.clear();
					}
					sessionStorage.removeItem("addflg");
				}
	            //判断原价情报是否可点击
			    if(data[$.getRequestDataName()]['sumcost'][0]['updatetime']==undefined||data[$.getRequestDataName()]['sumcost'][0]['updatetime']==null||data[$.getRequestDataName()]['sumcost'][0]['updatetime']==""){
			    $(".tip_hover").attr("style","text-decoration: none;color: #2c3e50;cursor: text!important;");
//			     $('.tip_hover').removeClass("icon-i");
				$('.tip_hover').unbind('click');
			    }
					textOverhidden();
	           }else{
	           	showErrorFunHandler(data[$.getRequestMetaName()].result,"ERROR","ERROR");
	           }
	       },
	       error:function(data){
	       		showErrorHandler("CONNECTION_SERVER_ERROR","ERROR","ERROR");
	       }
	 });

}
function transfercheck(){

	var tranamt = $(".saleMoment").val();
	var remark = $(".remark").val();
	var tran_status ='1';
	var input_no =urlPars.parm('inputno');
	var job_cd =urlPars.parm('jobcd');
//	OutPutPdfHandler('J180900001','','T180930001','tranConfirmPdf')
//	return;
	var allData = {
		"trantrn":{
			tran_amt:tranamt,
			input_no:input_no,
			remark:remark,
			tran_status:tran_status,
			job_cd:job_cd,
			lock_flg:locknum
		}
	}
	var lableList = getLable();
	allData['lableList'] =lableList;//标签
 	var path = $.getAjaxPath()+"TrantrnApproval";
	
	 $.ajax({
	  type: "POST",
	  url:path,
	  contentType:"application/json",
	  data:JSON.stringify(allData),
	  dataType:"JSON",
	  headers: {"requestID":$.getRequestID(),"requestName":$.getRequestNameByJcZH()},
			success:function(data){
			if(data.data==-1){//lockflg验证
				$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
	           	showLockInfoMsgHandler("STATUS_VALIDATEPOWER_ERROR","/jcst/cost_list.html?view=6");
	           	return false;
		       }
	           if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
	           	if(data[$.getRequestDataName()]==0){
	           		showLockInfoMsgHandler("STATUS_VALIDATEPOWER_ERROR","/jcst/cost_list.html?view=6");
	           		$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
	           		return false;
	           	}
//	           	showErrorHandler("EXECUTE_SUCCESS","info","info");
	           	$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
	           	$(".validatebox-text").val('');
				$(".tranname").val('');
				$(".remark").val('');
				$("#itemList").val('');
				 //插入成功后执行导出pdf
           	 	if ($('#PayConfirmOutPDF').prop("checked")){
		     		OutPutPdfHandler(job_cd,'',input_no,'confirmOncost',"","","",1);
		     		//goCostlist();
		        }else{
		        	goCostlist();
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
function transferCancel(){
//	if(!showConfirmMsgHandler("DELETECONFIRM")){
//		return false;
//	}
	var msg = "";
	var confirmTitle = $.getConfirmMsgTitle();
	msg = showConfirmMsgHandler("DELETECONFIRM");
	$.messager.confirm(confirmTitle,msg, function(r){
		if(r)
		{
		var input_no =urlPars.parm('inputno');
		var job_cd =urlPars.parm('jobcd');
		var pars = {
			"trantrn":{
				input_no:input_no,
				job_cd:job_cd,
				lock_flg:locknum
			}
		}
		var lableList = getLable();
		pars['lableList'] =lableList;//标签
		var path = $.getAjaxPath()+"cancelTran";	
		$.ajax({
		  type: "POST",
		  url:path,
		  contentType:"application/json",
		  data:JSON.stringify(pars),
		  dataType:"JSON",
		  headers: {"requestID":$.getRequestID(),"requestName":$.getRequestNameByJcZH()},
				success:function(data){			
		           if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
		           	if(data[$.getRequestDataName()]==0){
		           		showLockInfoMsgHandler("STATUS_VALIDATEPOWER_ERROR","/jcst/cost_list.html?view=6");
		           		$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
		           		return false;
		           	}
		           	if(data.data==-1){//lockflg验证
						$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
			           	showLockInfoMsgHandler("STATUS_VALIDATEPOWER_ERROR","/jcst/cost_list.html?view=6");
			           	return false;
				       }
	//	           	showErrorHandler("EXECUTE_SUCCESS","info","info");
		           	$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
		           	//插入成功后执行导出pdf
	           	 	if ($('#PayConfirmOutPDF').prop("checked")){
			     		OutPutPdfHandler(job_cd,'',input_no,'confirmOncost',"","","",1)
			     		//goCostlist();
			        }else{
			        	goCostlist();
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
	});
	
}
objStorage = {
	saleMessage: null,
	lableMessage: null,
	moneyMessage: null,
	personMoneyCode: null,
	lableIdCollection: null,
	getSaleMessage: function() {
		return objStorage.saleMessage;
	},
	getLableMessage: function() {
		return objStorage.lableMessage;
	},
	getMoneyMessage: function() {
		return objStorage.moneyMessage;
	},
	getPersonMoneyCode: function() {
		return objStorage.personMoneyCode;
	},
	getLableIdCollection: function() {
		return objStorage.lableIdCollection;
	}
}
function setPersonMoneyCode() {
	//获取登陆者货币
	var personMessage = objStorage.getPersonMoneyCode();
	var toplange = $('#language').val();
	var val = personMessage['money'+toplange];
	if(val == "null" || val == '') {
		val = "RMB";
	}
	$('.personcode').text(val);
	$('.sale_person_code').text(val);
}  