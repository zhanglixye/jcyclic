$(function(){
	$.jobCommon();
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
	//JOB弹出框
//	var saleAddFlag = sessionStorage.getItem("addflg");
//	if(saleAddFlag=='0'){
//	  $.layerShowDiv('job_detail','1000','650',2,'../common/business/job_detail/job_detail.shtml');
//	  sessionStorage.clear();
//	}
})
var locknum;
var oldinputno;
function inittransferform(){
	var input_no =urlPars.parm('inputno');
	var job_cd =urlPars.parm('jobcd');
	//正则验证不能输入日语
		var reg = /^[A-Za-z0-9]+$/;
        var r = input_no.match(reg);     
        if(r==null){
            showLockInfoMsgHandler("DATA_IS_NOT_EXIST","/jczh/cost_list.html?view=6");
			return false;    
        }
        var s = job_cd.match(reg);     
        if(s==null){
            showLockInfoMsgHandler("DATA_IS_NOT_EXIST","/jczh/cost_list.html?view=6");
			return false;    
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
	           	$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
	           	objStorage.personMoneyCode = data["userInfo"];
	           	objStorage.old_input_no = data.data.TrantrnInput.trantrnList[0].input_no
	           	//页面设置默认货币
				setPersonMoneyCode();
				//本国货币保留小数位数
				var len = data["userInfo"].pointNumber;
				bus_price_common(1,len);
				
				//初始化转圈问题
	            if(data.data.TrantrnInput==null){
	            	$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
	            	showLockInfoMsgHandler("DATA_IS_NOT_EXIST","/jczh/cost_list.html?view=6");
		           	return false;
	            }
				$('.inputDate').datebox('setValue', data.data.TrantrnInput.trantrnList[0].tran_date);	
				$('.tranamt').numberbox('setValue',data.data.TrantrnInput.trantrnList[0].tran_amt);
				$(".remark").val(data.data.TrantrnInput.trantrnList[0].remark);
				$(".tranname").val(data.data.TrantrnInput.trantrnList[0].tran_name);
				$(".saleMoment").val(data.data.TrantrnInput.trantrnList[0].itmname);	
				locknum=data.data.TrantrnInput.trantrnList[0].lock_flg;
				var columnArr = new Array(); 
           			SelectObj.selectData =data[$.getRequestDataName()]['TrantrnInput'];
           			columnArr.push("itemList");
           			SelectObj.setSelectID = columnArr;
           			SelectObj.setStringFlg="_";
           			SelectObj.setSelectOfLog();
				$("#itemList option[value='"+data.data.TrantrnInput.trantrnList[0].item_code+"']").prop("selected",true);
				//获取PDF出力是否选中		
				if(data.data.TrantrnInput.pdfflagcri==1){
					$("#PayConfirmOutPDF").attr("checked",true);
				}
				//小人颜色
				var jobupdusercolor = data[$.getRequestDataName()]['CLDIV'][0]['updusercolorv'];
				if(jobupdusercolor!=null&&jobupdusercolor!=''){
					$("#jobupdusercolor").css("color",data[$.getRequestDataName()]['CLDIV'][0]['updusercolorv']);
				}
				var costupdusercolor = data[$.getRequestDataName()]['sumcost'][0]['updusercolorv'];
				if(costupdusercolor!=null&&costupdusercolor!=''){
					$("#costupdusercolor").css("color",data[$.getRequestDataName()]['sumcost'][0]['updusercolorv']);
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
				//job信息区块，的得意先，与更新时间
				$("#divnm").text(data[$.getRequestDataName()]['CLDIV'][0]['divnm']);
				$("#upddate").text(data[$.getRequestDataName()]['CLDIV'][0]['upddate']);
				$("#username").text(data[$.getRequestDataName()]['CLDIV'][0]['username']);
				$("#job_name").text(data[$.getRequestDataName()]['CLDIV'][0]['jobname']);//项目名称				
				//sessionStorage.setItem("addflg",data[$.getRequestDataName()]['CLDIV'][0]['saleAddFlag']);//壳上登录状态为1：已经登录  2：未登录
    			//$("#jobcd").text(data[$.getRequestDataName()]['CLDIV'][0]['jobcd']);
				$("#sumamt").text(formatNumber(data[$.getRequestDataName()]['sumcost'][0]['sumamt']));
				$("#updatetime").text(data[$.getRequestDataName()]['sumcost'][0]['updatetime']);
				$("#costupdname").text(data[$.getRequestDataName()]['sumcost'][0]['costupdname']);
				$.layerShowDiv('icon-i','1000','600',2,'../common/jczh/cost_list.shtml',job_cd);
				//标签数据重构
				SelectObj.setLableList = data.data.TrantrnInput.list_lable;
				SelectObj.setChooseLableList = data.data.TrantrnInput.lableList;
				labelToMySelect(data.data.TrantrnInput.list_lable);
				setLableArea(data.data.TrantrnInput.lableList);
				var jobcd =job_cd;
				var powerList = data['userInfo'].uNodeList;
	            var bl = isHavePower(powerList, [5,6,7,8]);
	            if(bl){
					if(data[$.getRequestDataName()]['CLDIV'][0]['saleAddFlag']=='1'){
						window.job_cd=jobcd;
						$.layerShowDiv('job_detail','70%','80%',2,'../jczh/job_detail.shtml');	
						//sessionStorage.clear();
					}else{
						window.job_cd=jobcd;
						$.layerShowDiv('job_detail','70%','80%',2,'../jczh/job_detail_nosale.html');	
						//sessionStorage.clear();
					}
					//sessionStorage.removeItem("addflg");
				}
	            //判断原价情报是否可点击
			    if(data[$.getRequestDataName()]['sumcost'][0]['updatetime']==undefined||data[$.getRequestDataName()]['sumcost'][0]['updatetime']==null||data[$.getRequestDataName()]['sumcost'][0]['updatetime']==""){
			    $(".tip_hover").attr("style","text-decoration: none;color: #2c3e50;cursor: text!important;");
//			     $('.tip_hover').removeClass("icon-i");
				$('.tip_hover').unbind('click');
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
function transferUpdate(){
	var input_no =urlPars.parm('inputno');
	var old_input_no = objStorage.old_input_no;
	var job_cd =urlPars.parm('jobcd');
	var adddate = $(".inputDate").val();
	var tranname = $(".tranname").val();
	var tranamt = $(".tranamt").val();
	var remark = $(".remark").val();
	var item_code  = $("#itemList").val();
	var flgR = true;
//	OutPutPdfHandler('J180900001','','T180930001','tranCreatPdf')
//	return;
// 	$("input[type='text'].required,input[type='password'].required,select.required").each(function(index, element) {
// 		if(element.value == "")
// 		{
// 			flgR = false;
// 			element.style.borderColor = "red";
// 			validate($(element),part_language_change_new('NODATE'));
// 		}else{
// 			element.style.borderColor = "#cccccc";
// 			$(element).tooltip('destroy');
// 		}
//     });
    
    if(adddate==''){    
        $('#order_date').siblings("span.textbox.combo.datebox").css("border-color", "red");
		validate($('#order_date').siblings("span.textbox.combo.datebox"),part_language_change_new('NODAY'));
		//return true;
    }else{
    	$('#order_date').siblings("span.textbox.combo.datebox").css("border-color", "#cccccc");
	    $('#order_date').siblings("span.textbox.combo.datebox").tooltip('destroy');
    }
    if(tranamt==''||tranamt==null){
    	$('.cal-sale-box').next('span').addClass('border_red');
    	$('.cal-sale-box').next('span').next('span').addClass('border_red');
    	validate($('.cal-sale-box').closest('div'),part_language_change_new('NODATE'));
    	//return true;
    }else{
    	$('.cal-sale-box').next('span').removeClass('border_red');
    	$('.cal-sale-box').next('span').next('span').removeClass('border_red');
    	$('.cal-sale-box').closest('div').tooltip('destroy');
    }
	if(!validataRequired())
	{
		return ;
	}
    if(tranamt==''||tranamt==null || adddate=='' || flgR == false){
    	return true;
    }
	var allData = {
		"trantrn":{
			tran_date:adddate,
			item_code:item_code,
			tran_amt:tranamt,
			tran_name:tranname,
			remark:remark,
			job_cd:job_cd,
			input_no:input_no,
			lock_flg:locknum
		},
			tran_date:adddate,
			item_code:item_code,
			tran_amt:tranamt,
			tran_name:tranname,
			remark:remark,
			job_cd:job_cd,
			input_no:input_no,
			lock_flg:locknum,
			old_input_no:old_input_no 
	}
	
	var lableList = getLable();
	allData['lableList'] =lableList;//标签
 	var path = $.getAjaxPath()+"TrantrnUpdate";	
	 $.ajax({
	  type: "POST",
	  url:path,
	  contentType:"application/json",
	  data:JSON.stringify(allData),
	  dataType:"JSON",
	  headers: {"requestID":$.getRequestID(),"requestName":$.getRequestNameByJcZH()},
			success:function(data){			
	           if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
//	           	showErrorHandler("EXECUTE_SUCCESS","info","info");
	           	$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
	           	if(data.data==-1){//lockflg验证
		           	showLockInfoMsgHandler("STATUS_VALIDATEPOWER_ERROR","/jczh/cost_list.html?view=6");
		           	return false;
		        }
	           	if(data.data==-2){//使用日验证
			           	$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
			           	showInfoMsgHandlerstop("DATE_RANGE_ERROR","/jczh/cost_list.html?view=6");
			           	return false;
		            }
	           	//插入成功后执行导出pdf
           	 	if ($('#PayConfirmOutPDF').prop("checked")){
           	 		new_input_no = data.data;
		     		OutPutPdfHandler(job_cd,'',new_input_no,'onCostCreate',"","","",1);
		     		
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
function transferDelete(){
//	if(!showConfirmMsgHandler("DELETECONFIRM")){
//		return false;
//	};
	var msg = "";
	var confirmTitle = $.getConfirmMsgTitle();
	msg = showConfirmMsgHandler("SURE_DELETE");
	$.messager.confirm(confirmTitle,msg, function(r){
		if(r)
		{
		var input_no =urlPars.parm('inputno');
		var job_cd =urlPars.parm('jobcd');
	
		var allData = {
			"trantrn":{
				job_cd:job_cd,
				input_no:input_no,
				lock_flg:locknum
			}
		}
		var lableList = getLable();
		allData['lableList'] =lableList;//标签
	 	var path = $.getAjaxPath()+"TrantrnDelete";	
		 $.ajax({
		  type: "POST",
		  url:path,
		  contentType:"application/json",
		  data:JSON.stringify(allData),
		  dataType:"JSON",
		  headers: {"requestID":$.getRequestID(),"requestName":$.getRequestNameByJcZH()},
				success:function(data){			
		           if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
		           	//showErrorHandler("EXECUTE_SUCCESS","info","info");
		           	$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
		           	if(data.data==-1){//lockflg验证
			           	showLockInfoMsgHandler("STATUS_VALIDATEPOWER_ERROR","/jczh/cost_list.html?view=6");
			           	return false;
		            }
		           	goCostlist();
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
/*
 * saleMessage 储存初始化加载的売上种目信息
 * lableMessage 标签信息
 * moneyMessage 货币信息
 * personMoneyCode 个人货币信息
 * lableIDCollection 标签的id数组
 */
objStorage = {
	saleMessage: null,
	lableMessage: null,
	moneyMessage: null,
	personMoneyCode: null,
	lableIdCollection: null,
	old_input_no:null,
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
	$('.cost_person_code').text(val);
	$('.person_code').text(val);
	$('.sale_person_code').text(val);
}  

