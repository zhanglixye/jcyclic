$(document).ready(function(){
	$(".switchot_off").css("height", '133px');
	$('input[type=checkbox].boostrap-switch').bootstrapSwitch({
		onSwitchChange: function() {
			$(".switchot_off").css("height", "333px");
			var state1 = $('.judge1').bootstrapSwitch('state');
			var state2 = $('.judge2').bootstrapSwitch('state');
//			console.log(state1);console.log(state2);
			if(state2 == false) {
				$(".switchot_off").css("height", '133px');
			}
		}
	});
})
var locknum;
$(function(){
	$('.after_css').addClass('hidden');
	$.jobCommon();
	initadvanceform();
	$.layerShowDiv('icon-tianjia1', '400', 'auto', 1, $('.payment_pay'));
	$.layerShowDiv('icon-sheding1','400','auto',1,$('.label_set_t'));
	$(".add_lable").click(function() {
		addLable("new_lable", "options_lable","1");
	});
	$(".filter-lable").click(function(){
	  var str = $("#lableStr").val();
	  filterLable(str,null);
 	})
//	//JOB弹出框
//	var saleAddFlag = sessionStorage.getItem("addflg");
//	if(saleAddFlag=='0'){
//	  $.layerShowDiv('job_detail','1000','650',2,'../common/business/job_detail/job_detail.shtml');
//	  sessionStorage.clear();
//	}
	$('#saleCurCode,#common-dayT-off,#cost_refer,#_easyui_textbox_input1').attr('disabled','disabled');
	 lableShowByPower();
})
var fortype;
var trantype='';
function shouForgienInfo()
{
	$('#switch-state-job').bootstrapSwitch('disabled',false);
	$('#switch-state-job').bootstrapSwitch('state', 1);
	$('#exchange_rate2').css("display","block");
	$('#switch-state-job').bootstrapSwitch('disabled',true);
}
function initadvanceform(){
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
		$('#menu29').removeClass('hidden');
		$('title').attr('name','menu29');
		$('#jobregistration_person').addClass('hidden');
		//readLanguageFile();
	}
	if(flag=='1'){//承认
		$('.cancel').addClass('hidden');		
		$('#advanceapproval').removeClass('hidden');
		trantype='1';
	}
	
//	if(input_no==null||input_no==''){
//		showConfirmMsgHandler("NOTFOUND");
//		goBackPageHandler();
//		
//	}
	$('#aujob').text(input_no);
	$('#jobcd').text(job_cd);
	var allData = {
		"lendtrn":{
			input_no:input_no,
			job_cd:job_cd
		}
	}
 	var path = $.getAjaxPath()+"LendtrnQuery";	
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
	           	if(data.data.LendtrnInput==null){
	            	$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
	            	showLockInfoMsgHandler("DATA_IS_NOT_EXIST","/jcst/cost_list.html?view=6");
		           	return false;
	            }
	           	objStorage.personMoneyCode = data["userInfo"];
	           	//页面设置默认货币
				setPersonMoneyCode();
				//本国货币保留小数位数
				var len = data["userInfo"].pointNumber;
				$('#cost_remark .job_text pre').html(data.data.LendtrnInput.lendtrnList[0].remark);//备注
				$('.inputDate').html(data.data.LendtrnInput.lendtrnList[0].lend_date);	//发生日
				$('.advancename').html(data.data.LendtrnInput.lendtrnList[0].lend_name);//项目名称
				locknum = data.data.LendtrnInput.lendtrnList[0].lock_flg;
				$("#job_name").text(data[$.getRequestDataName()]['CLDIV'][0]['jobname']);//项目名称
				var toplange = $('#language').val();
				var itmname;
				switch(toplange) {
				case "jp":
					itmname = data.data.LendtrnInput.lendtrnList[0].itemname_jp;
					break;
				case "zc":
					itmname = data.data.LendtrnInput.lendtrnList[0].itmname;
					break;
				case "zt":
					itmname = data.data.LendtrnInput.lendtrnList[0].itemname_hk;
					break;
				case "en":
					itmname = data.data.LendtrnInput.lendtrnList[0].itemname_en;
					break;
				};
				$('.itemList').html(itmname);//经费科目
				//$('.advanceperson').html(data.data.LendtrnInput.lendtrnList[0].LEND_USER);//代垫人	
				var isdeduction =	getGridLanguage("isdeduction",data.data.LendtrnInput.lendtrnList[0].isdeduction);	
				data.data.LendtrnInput.lendtrnList[0].isdeduction=isdeduction;
				var lend_code = data.data.LendtrnInput.lendtrnList[0].lend_cure_code;
				if(lend_code>0){
					shouForgienInfo();//是否外货
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
				//入力金额外货显示
				var payforeigntype =data.data.LendtrnInput.lendtrnList[0].payforeigntype;
				if(payforeigntype==null || payforeigntype==''){
					$('.cost_person_code').text(fortype);
				}else{
					$('.cost_person_code').text(payforeigntype);
				}
				//获取PDF出力是否选中	
				if(trantype==1){
					if(data.data.LendtrnInput.lendtrnList[0].pdfflagpro==1){
						$("#PayConfirmOutPDF").attr("checked",true);
					}	
				}
				
				$('.isdeduction').html(isdeduction);//0不扣除；1：扣除
				var columnArr = new Array(); 
           			SelectObj.selectData =data[$.getRequestDataName()]['LendtrnInput'];
           			columnArr.push("plan_cost_foreign_code");//待修改
           			SelectObj.setSelectID = columnArr;
           			SelectObj.setStringFlg="_";
           			SelectObj.setSelectOfLog();          			
	            //$('#plan_cost_foreign_code option:first').remove();
	            $("#plan_cost_foreign_code").val(data.data.LendtrnInput.lendtrnList[0].lend_foreign_type)
				//$('#plan_cost_foreign_code').prepend('<option>'+data.data.LendtrnInput.lendtrnList[0].lend_foreign_type+'</option>');
	            $("#cost_need_change_money1").text($(".pricrsel option:selected").text())//同步金额后面的单位
//				$('.plan_cost_foreign_amt').html(data.data.LendtrnInput.lendtrnList[0].LEND_AMT);//原価金額numberbox
//				$('.saleMoment').numberbox('setValue',data.data.LendtrnInput.lendtrnList[0].LEND_FOREIGN_AMT);//金額*
//				$('.plancost_tax').html(data.data.LendtrnInput.lendtrnList[0].LEND_VAT_AMT);//增值税
//				saleIsHave(data.data.LendtrnInput.lendtrnList[0].ISHAVE)//卖上是否税入;0：税拔；1：税入；	
				$('.plan_cost_foreign_amt').text(formatNumber(data.data.LendtrnInput.lendtrnList[0].lend_amt.toFixed(len)));//原価金額numberbox
				$('.saleMoment').numberbox('setValue',data.data.LendtrnInput.lendtrnList[0].lend_foreign_amt.toFixed(len));//金額*
				$('.plancost_tax').text(formatNumber(data.data.LendtrnInput.lendtrnList[0].lend_vat_amt.toFixed(len)));//增值税
				saleIsHave(data.data.LendtrnInput.lendtrnList[0].ishave)//卖上是否税入;0：税拔；1：税入；
				//$(".cal-cost option[value='"+data.data.LendtrnInput.lendtrnList[0].LEND_FOREIGN_TYPE+"']").prop("selected",true);//经费科目
				$('.plan_cost_cure_code').val(data.data.LendtrnInput.lendtrnList[0].lend_cure_code);//换算code
				$('.use_date').val(data.data.LendtrnInput.lendtrnList[0].lend_use_date);//使用日
				$('.plan_cost_refer').val(data.data.LendtrnInput.lendtrnList[0].lend_refer);//参照先  
				$('.plan_vat_amt').text(formatNumber(data.data.LendtrnInput.lendtrnList[0].lend_pay_amt.toFixed(len)));//最终外发金额	
	            //job信息区块，的得意先，与更新时间
				$("#divnm").text(data[$.getRequestDataName()]['CLDIV'][0]['divnm']);
				$("#upddate").text(data[$.getRequestDataName()]['CLDIV'][0]['upddate']);
				$("#username").text(data[$.getRequestDataName()]['CLDIV'][0]['username']);
				sessionStorage.setItem("addflg",data[$.getRequestDataName()]['CLDIV'][0]['saleAddFlag']);//壳上登录状态为1：已经登录  2：未登录
    			//$("#jobcd").text(data[$.getRequestDataName()]['CLDIV'][0]['jobcd']);
				$("#sumamt").text(formatNumber(data[$.getRequestDataName()]['sumcost'][0]['sumamt']));
				$("#updatetime").text(data[$.getRequestDataName()]['sumcost'][0]['updatetime']);
				$("#costupdname").text(data[$.getRequestDataName()]['sumcost'][0]['costupdname']);
				$.layerShowDiv('icon-i','1000','600',2,'../common/jcst/cost_list.shtml',job_cd);
				//标签数据重构
				labelToMySelect(data.data.LendtrnInput.list_lable);
				setLableArea(data.data.LendtrnInput.lableList);
				SelectObj.setLableList = data.data.LendtrnInput.list_lable;
				//登陆者和更新者模块
				$('#adduser').text(data.data.LendtrnInput.lendtrnList[0].addusername)
				$('#adddate').text(data.data.LendtrnInput.lendtrnList[0].adddate)
				$('#upduser').text(data.data.LendtrnInput.lendtrnList[0].upusername)
				$('#update').text(data.data.LendtrnInput.lendtrnList[0].update)
				$("#addcolor").css("color",data.data.LendtrnInput.lendtrnList[0].addusernamecolor);
				$("#updcolor").css("color",data.data.LendtrnInput.lendtrnList[0].upusernamecolor);
				if(data.data.LendtrnInput.lendtrnList[0].adddate==data.data.LendtrnInput.lendtrnList[0].update){
					$('#updhidden').addClass('hidden');
				}
				$('#languageNameAdd').attr('name','liti_jobLogon');
				$('#languageNameUpd').attr('name','liti_jobUpdate');
				part_language_change($('#languageNameAdd'));
				part_language_change($('#languageNameUpd'));
				//凭证信息初始化
				var rooftrn = data[$.getRequestDataName()]['prooftrn'];
				var str = '';
				var path = "";			
				for(var i = 0;i < rooftrn.length;i++){
					path = returnURL(rooftrn[i].PROOF_URL);
					str +='<a onclick="window.open(\''+path+'\')">'+'<li class="s1">'+
						'<span>'+rooftrn[i].PROOF_TITLE+'</span> '+							
						'<input type="hidden" name="" id="" value="'+rooftrn[i].PROOF_URL+'" />'+
					'</li>'+'</a>';
				}
				$('.advance_url').empty();
				$('.advance_url').append($(str));
				textOverhidden();
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
			    // $('.tip_hover').removeClass("icon-i");
			    $('.tip_hover').unbind('click');
			    }
	            var languagetype = $('#language').val();
	            var cof = "";
	            switch(languagetype) {
					case "jp":
						cof = data.data.LendtrnInput.lendtrnList[0].departnamejp;
						break;
					case "zc":
						cof = data.data.LendtrnInput.lendtrnList[0].departname;
						break;
					case "zt":
						cof = data.data.LendtrnInput.lendtrnList[0].departnamehk;
						break;
					case "en":
						cof = data.data.LendtrnInput.lendtrnList[0].departnameen;
						break;
				}
	            //立体社员模块
	            $('#userlevel').html(cof);
	            $('#memberid').html(data.data.LendtrnInput.lendtrnList[0].memberid);
	            $('#username').html(data.data.LendtrnInput.lendtrnList[0].username);
	            var colorv = data.data.LendtrnInput.lendtrnList[0].colorv;
	            var username = data.data.LendtrnInput.lendtrnList[0].username;
	            var memberid = data.data.LendtrnInput.lendtrnList[0].memberid;
	            if(colorv!=undefined&&colorv!=null&&colorv!=''){
		           		var obj = document.getElementById("personicon");
				        obj.style.color=colorv; 
				    }

					$("#username").text(username!=undefined&&username!=null?username:'');
					$("#memberid").text(memberid!=undefined&&memberid!=null?memberid:'');			
	           }else{
	           	showErrorFunHandler(data[$.getRequestMetaName()].result,"ERROR","ERROR");
	           }
	       },
	       error:function(data){
	       		showErrorHandler("CONNECTION_SERVER_ERROR","ERROR","ERROR");
	       }
	 });

}
function advanceApproval(){
	var input_no =urlPars.parm('inputno');
	var job_cd =urlPars.parm('jobcd');
	var lend_amt = recoveryNumber($('.plan_cost_foreign_amt').text());//原価金額
	var lend_vat_amt= recoveryNumber($('.plancost_tax').text());//增值税
	var lend_pay_amt = recoveryNumber($('.plan_vat_amt').text());//最终外发金额
	var pars = {}
		pars.lendtrn={
				input_no:input_no,
				job_cd:job_cd,
				lend_amt:lend_amt,
				lend_pay_amt:lend_pay_amt,
				lend_vat_amt:lend_vat_amt,
				lock_flg:locknum
		}
	var lableList = getLable();
	pars['lableList'] =lableList;//标签
	var path = $.getAjaxPath()+"LendtrnApproval";	
	$.ajax({
	  type: "POST",
	  url:path,
	  contentType:"application/json",
	  data:JSON.stringify(pars),
	  dataType:"JSON",
	  headers: {"requestID":$.getRequestID(),"requestName":$.getRequestNameByJcZH()},
			success:function(data){			
	           if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
	           	if(data.data==-1){//lockflg验证
			           	$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
			           	showLockInfoMsgHandler("STATUS_VALIDATEPOWER_ERROR","/jcst/cost_list.html?view=6");
			           	return false;
		        }
	           	if(data[$.getRequestDataName()]==0){
	           		showLockInfoMsgHandler("STATUS_VALIDATEPOWER_ERROR","info","info");
	           		$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
	           		return false;
	           	}
//	           	showErrorHandler("EXECUTE_SUCCESS","info","info");
	           	$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
	           	//插入成功后执行导出pdf
           	 	if ($('#PayConfirmOutPDF').prop("checked")){
           	 		//var input_no = data.data;
		     		OutPutPdfHandler(job_cd,'',input_no,'confirmDebours',"","","",1);
		     		
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
function advanceCancel(){
	var msg = "";
	var confirmTitle = $.getConfirmMsgTitle();
	msg = showConfirmMsgHandler("DELETECONFIRM");
	$.messager.confirm(confirmTitle,msg, function(r){
		if(r)
		{
		var input_no =urlPars.parm('inputno');
		var job_cd =urlPars.parm('jobcd');
		var pars = {}
			pars.lendtrn={
					input_no:input_no,
					job_cd:job_cd,
					lock_flg:locknum
			}
		var lableList = getLable();
		pars['lableList'] =lableList;//标签	
		var path = $.getAjaxPath()+"cancelLend";	
		$.ajax({
		  type: "POST",
		  url:path,
		  contentType:"application/json",
		  data:JSON.stringify(pars),
		  dataType:"JSON",
		  headers: {"requestID":$.getRequestID(),"requestName":$.getRequestNameByJcZH()},
				success:function(data){			
		           if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
		           	if(data.data==-1){//lockflg验证
			           	$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
			           	showLockInfoMsgHandler("STATUS_VALIDATEPOWER_ERROR","/jcst/cost_list.html?view=6");
			           	return false;
		           }
		           	if(data[$.getRequestDataName()]==0){
		           		showLockInfoMsgHandler("STATUS_VALIDATEPOWER_ERROR","/jcst/cost_list.html?view=6");
		           		$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
		           		return false;
		           	}
	//	           	showErrorHandler("EXECUTE_SUCCESS","info","info");
		           	$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
		           	//插入成功后执行导出pdf
	           	 	if ($('#PayConfirmOutPDF').prop("checked")){
	           	 		//var input_no = data.data;
			     		OutPutPdfHandler(job_cd,'',input_no,'confirmDebours',"","","",1);
			     		
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
//	if(!showConfirmMsgHandler("DELETECONFIRM")){
//		return false;
//	}
	
	
}
function saleIsHave(flag){
	var aDom = $('.btn-group');
	if(flag == 1){
		flag = true;
	}else{
		flag = false;
	}
	if(flag){
		aDom.find('label').eq(0).addClass('activeT');
		aDom.find('label').eq(1).removeClass('activeT');
	}else{
		aDom.find('label').eq(1).addClass('activeT');
		aDom.find('label').eq(0).removeClass('activeT');
	}
}
//创建个对象储存返回来的值
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
	$('.sale_person_code').text(val);
	$('#moneytype').html(val);
	fortype=val;
}