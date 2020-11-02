var aidcd=new Array();
var change_utin=new Array();
var itmvalue=new Array();
var pointNumber ='';
var foreignFormatFlg='';
var saleVatFormatFlg='';
var orderInvoiceFlg;
$(document).ready(function(){
	
$('#language').change(function() {
		var paydeductionflg= $("#deduction").text();
		 if(paydeductionflg!=undefined&&paydeductionflg!=null&&paydeductionflg!=''){
	     var deductionflgtext =getGridLanguage('deductionflg',paydeductionflg);	
	    }else{
	    	var deductionflgtext ='';
	    }
	    $("#deduction").text(deductionflgtext);
		})
	
	$(".switchot").css("height", 'auto');
	$('input[type=checkbox].boostrap-switch').bootstrapSwitch({
		onSwitchChange: function() {
			$(".switchot").css("height", "auto");
			var state1 = $('.judge1').bootstrapSwitch('state');
			var state2 = $('.judge2').bootstrapSwitch('state');
			if(state2 == false) {
				$(".switchot").css("height", 'auto');
			}
		}
	});
})
$(function(){
	//页面初始化方法
	initCostOutSoure();
	$.jobCommon();
	//这是我修改的
	$.foreignGoodsShow();
	//lible選擇tanchuang
	$.layerShowDiv('icon-sheding1','400','auto',1,$('.label_set_t'));
	$(".add_lable").click(function() {
		addLable("new_lable", "options_lable", 1);
	});
	//lable弹窗
	$.layerShowDiv('setb2','29%','auto',1,$('#setbig1'));
	//承包方弹窗
	var surl =$.getJumpPath()+"common/suppliers_retrieval.html";
	$.layerShow("searchContra",surl);
	
	//予定纳品日变更重新查询税率

	$('#plan_dlvday').datebox({
	  "onChange": function() {
	    var invoice_type = $('#invoice_type').val();
	 	var invoice_text = $('#invoice_text').val();
		var payee_cd = $("#payee_cd").val();
	 	var plan_dlvday = $("#plan_dlvday").val();
	 	if(checkDateV(plan_dlvday)&&plan_dlvday.length==10){
	 	selecttTaxbypayee(plan_dlvday,payee_cd,invoice_text,invoice_type);		
	 	}
		
	  }
	 });
	 //发注先变更重新查询税率
	 $('#payee_cd').change(function(){
	 
	    var invoice_type = $('#invoice_type').val();
	 	var invoice_text = $('#invoice_text').val();
		var payee_cd = $("#payee_cd").val();
	 	var plan_dlvday = $("#plan_dlvday").val();
	 	
	 		selecttTaxbypayee(plan_dlvday,payee_cd,invoice_text,invoice_type);		
	 	
		})
	 
	//外货下拉变更时，计算外发成本
	$('#cost_foreign_type').change(function(){
		var money =$('#cost_foreign_type option:selected').text();//选中的文本
		$('#cost_need_change_money1').text(money);
		var index =document.getElementById("cost_foreign_type").selectedIndex;
		bus_price_common(1,itmvalue[parseInt(index)-1]);
		calculateOutSoure();
	   }); 
	

	$('input#cost_foreign_amt').numberbox({
	  "onChange": function() {
	  calculateOutSoure();	
	  }
	 });
	 
	$(".cost_rate_click").click(function(e) {
	  var value = $(e.target).closest('label').find('input').val();
	  var textval = $('.cost_rate_click').find('.active').find('input').val();
	  if(value == textval){
	   return;
	  }
	  calculateOutSoure(value);
	 });
	//汇率变更，计算外发成本
	$('#saleCurCode').change(function(){
		 calculateOutSoure();	
	});
	
	$("#switch-state1").on('switchChange.bootstrapSwitch', function(e, state) {
		 if(state) { //״̬Ϊon
		 	var index =document.getElementById("cost_foreign_type").selectedIndex;
			bus_price_common(1,itmvalue[parseInt(index)-1]);
		} else { //״̬Ϊ0FF
		 bus_price_common(1,pointNumber);
		 calculateOutSoure();
		}
	})
	 //监听张票弹出框，下拉列表变更，重新计算税率
	$('#common-dayT').datebox({});	
	
	
	//增值税弹出
	$.layerShowDiv('add_tax_price_on', 'auto', 'auto', 1, $('.add_tax_price'));
	layerClick('mes-edit','30%','610',2,'../common/jczh/invoice_edit.shtml');

	
	//增值税编辑 原价部分
	$("#set-tax-price").click(function(e) {
		var haveVatFlg = $('.cost_rate_click').find('.active').find('input').val();
		var saleCostAmt = recoveryNumber($('span#cost_foreign_amt').text());
		var reqPayAmt = recoveryNumber($('#pay_amt').text());
		var vatAmt = recoveryNumber($('.edit-tax-price').val());
		if(vatAmt == "") {
			$(".price-border").find("span.numberbox").css("border-color", "red");
			return;
		}
		var obj = calculateMoneyByVatChangeHandler(haveVatFlg, saleCostAmt, reqPayAmt, vatAmt);
		$('span#cost_foreign_amt').text(formatNumber(obj["saleCostAmt"]));
		$('#cost_tax').text(formatNumber(obj["vatAmt"]));
		$('#pay_amt').text(formatNumber(obj["reqPayAmt"]));
		if(vatAmt!=calculateTax){
		 changeFlg = 1;
		}
		var index = layer.index;
		layer.close(index);
	});
	//lible权限验证
	 lableShowByPower();
})
/**
 * 方法名 layerClick
 * 方法的说明 外发登录窗口弹出
 * @param classId 绑定的classId,width 弹出长度窗口的宽度,height 弹出长度窗口的高度,flag 状态 1:html 2:url,urlDom url或者dom
 * @return void
 * @author作者 张立学
 * @date 日期 2018.07.05
 */
function layerClick(classId,width,height,flag,urlDom){	
	if(height.indexOf('%') != -1){
		var height = height;
		var client_width = document.documentElement.clientWidth;
		if(client_width > 1440){
			height = '640px';
		}
	}else{
		var height = height + 'px';
	}
	if(width.indexOf('%') != -1){
		var width = width;
	}else{
		var width = width + 'px';
	}
	$('.'+classId).on('click',function(e){
		//发票弹窗初始化方法
	 if(classId=="mes-edit"){
	 	var payee_cd = $("#payee_cd").val();
	 	var plan_dlvday = $("#plan_dlvday").val();
	 	//验证承包方很预定交付日不为空
	 	if(payee_cd!=null&&payee_cd!=''&&plan_dlvday!=null&&plan_dlvday!=''){
	 		layer.open({
			type: flag,
			//title: ['取引先','font-size:16px;font-weight:bold;'],
			title:false,
			closeBtn: 0,
			shadeClose: true, //点击遮罩关闭层
			area: [width,height],
			//move:false,
			content:urlDom,
			success: function(layero, index){
				var iframeWin = window[layero.find('iframe')[0]['name']]; 
				var closeBtn = layer.getChildFrame('.iconfont.icon-guanbi,#closeBtnInput', index);
			   	//初始化加载下拉框内容
			   	initInvoice(payee_cd,plan_dlvday,iframeWin);
			   	//下拉框变更是监听,查询税率,在payment_common.js
			     commonInvoiceChange(plan_dlvday,payee_cd,invoice_text,invoice_type,iframeWin);
			     //是否有小红星
                // if (orderInvoiceFlg == 1){
                //     iframeWin.$('.invoice_edit_red_point').removeClass('hidden');
                // }else {
                    iframeWin.$('.invoice_edit_red_point').addClass('hidden');
                //}
			   	//显示按钮监听，把弹窗中的数据付到父页面的隐藏域中
			   	 iframeWin.$('#invoicebtn').click(function(){
			   		// if(orderInvoiceFlg == 1 && iframeWin.$('#invoice_no').val() == "")
			   		// {
			   		// 	iframeWin.$('#invoice_no').css("border-color", "red");
					// 	//iframeWin.$('#invoice_no').tooltip('destroy');
					// 	iframeWin.$('#invoice_no').tooltip({
					// 		position: 'left',
					// 		content: '<span style=\"color:#fff\">' + part_language_change_new('NODATE') + '</span>',
					// 		onShow: function() {
					// 			iframeWin.$(this).tooltip('tip').css({
					// 				backgroundColor: 'red',
					// 				borderColor: 'red',
					// 				top:'148.717px'
					// 			});
					// 		}
					// 	});
					// 	showErrorHandler("NOT_EMPTY", "ERROR", "ERROR");
			   		// 	return ;
			   		// }else{
			   		// 	iframeWin.$('#invoice_no').css("border-color", "#cccccc");
					// 	iframeWin.$('#invoice_no').tooltip('destroy');
			   		// }
			   	 	$('#pd').val(1);
			   	 	$('#invoice_date').val(iframeWin.$('#invoice_date').val());
			   	 	$('#invoice_no').val(iframeWin.$('#invoice_no').val());
			   	 	$('#invoice_type').val(iframeWin.$('#invoice_type').val());
			   	 	$('#invoice_text').val(iframeWin.$('#invoice_text').val());
			   	 	$('#cost_rate').text (iframeWin.$('#vat_rate').val());
			   	 	$('#remark pre')[0].innerHTML = iframeWin.$('#remark').val();
			   	 	$('#deduction').text(iframeWin.$('#deduction').text());
			   	 	$('#deductionvalue').text(iframeWin.$('#deductionvalue').text());
			   	     calculateOutSoure();
			   	    layer.close(index);
			   	 });
			   	closeBtn.on('click',function(){
					layer.close(index);
				})
			}
		});
			
	 	}else{
	 	 showErrorHandler('NOT_CHOOSE_COST','info','info');	
	 	}
	 }
	 else{
	 		layer.open({
			type: flag,
			//title: ['取引先','font-size:16px;font-weight:bold;'],
			title:false,
			closeBtn: 0,
			shadeClose: true, //点击遮罩关闭层
			area: [width,height],
			//move:false,
			content:urlDom,
			success: function(layero, index){
				var iframeWin = window[layero.find('iframe')[0]['name']]; 
				var closeBtn = layer.getChildFrame('.iconfont.icon-guanbi,#closeBtnInput', index);
			}
		});
	 }

	})
	
}


function insertOutSoure(){
	if(!validataRequired()||!Judgment_time('order_date','plan_dlvday','PLAN_DATEMORE'))
	{
		return ;
	}
	//验证发票弹窗中必填项
	var cost_rate=$("#cost_rate").text();
	if(cost_rate==undefined||cost_rate==null||cost_rate==''){
		showErrorHandler("NOTFINDE_TAX","info","info");
		return;
	}
	// if(orderInvoiceFlg == 1 && $('#invoice_no').val() == "")
	// {
	// 	showErrorHandler("NOT_EMPTY","info","info");
	// 	$('.mes-edit').addClass('color_red_text');
	// 	return;
	// }else {
	// 	$('.mes-edit').removeClass('color_red_text');
	// }
	var jobcd =urlPars.parm("jobcd");
	var path = $.getAjaxPath()+"insertOutSoure";	
	var pars = $.getInputVal();
	var lableList = getLable();
	pars['job_cd']=jobcd;
	pars['order_date'] = $("#order_date").val();
	pars['plan_dlvday'] = $("#plan_dlvday").val();
	pars['cost_remark'] = $("#cost_remark").val();//备考
	pars['cost_rate'] = floatObj.divide($("#cost_rate").text(),100);;//税率
	pars['cost_vat_amt'] = recoveryNumber($('.plancost_tax').text());//增值税
	pars['cost_rmb'] = recoveryNumber($("span#cost_foreign_amt").text());//发注金额本国货币(发注原价）
	pars['cost_pay_amt'] = recoveryNumber($('#pay_amt').text());//支付金额
	pars['cost_foreign_amt'] = $("input#cost_foreign_amt").numberbox('getValue');//入力金额
	pars['lableList'] =lableList;//标签
	pars['invoice_date'] = $("#invoice_date").val();
	pars['invoice_no'] = $("#invoice_no").val();
	pars['invoice_type'] = $("#invoice_type").val();
	pars['invoice_text'] = $("#invoice_text").val();
	pars['cost_foreign_type'] = $("#cost_foreign_type").val();
	pars['cost_ishave']= $(".cost_rate_click .active input[name='saleIsHave']").val();
	var deduction_flg= $("#deduction").text();
	/*if(deduction_flg=="扣除"){
		pars['deduction_flg']="1";
	}else{
		pars['deduction_flg']="0";
	}*/
	if($("#deductionvalue").text()!=""){
	pars['deduction_flg']=$("#deductionvalue").text();	
	}
	pars['remark'] = $("#remark pre").html();
	pars['vat_change_flg'] = changeFlg;
	if($('#switch-state1').bootstrapSwitch('state')) {
 			pars['cost_cure_code'] = $("#saleCurCode").val();//换算code、、汇率
			pars['cost_use_date'] = $("#common-dayT").val();//適用日
			pars['cost_refer'] = $("#cost_refer").val();//参照先
 }
	$.ajax({
		url:path,
		data:JSON.stringify(pars),
		headers: {"requestID":$.getRequestID(),"requestName":$.getRequestNameByJcZH()},
		success:function(data){
           if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
			     $.resetRequestID(data[$.getRequestUserInfoName()].requestID);
				 var pdfout =$("#OrderPDF").val();
				 //后台验证数据状态
				 if(data[$.getRequestDataName()]['messge']=='ORDER_CREATE_ERROR'){
				 	 showLockInfoMsgHandler("STATUS_VALIDATEPOWER_ERROR","jczh/job_registration_list.html?view=init&menu=se");
					 return;
				 }else if(data[$.getRequestDataName()]['messge']=='STATUS_VALIDATEPOWER_ERROR'){
				 	 showLockInfoMsgHandler("STATUS_VALIDATEPOWER_ERROR","jczh/job_registration_list.html?view=init&menu=se");
					 return;
				 }else if (data[$.getRequestDataName()]['messge']=='CLIENT_DELETE_ERR'){
				 	showErrorHandler("CLIENT_DELETE_ERR","info","info");
					return;
				 }else if (data[$.getRequestDataName()]['messge']=='PLAN_DATEMORE'){
				 	showErrorHandler("PLAN_DATEMORE","info","info");
					return;
				 }else{
				 	//更新条数
				 	 if(data[$.getRequestDataName()]['num']==1){
				 	 	url = "jczh/job_registration_list.html?view=init";//此处拼接内容
				 	    window.location.href  = $.getJumpPath()+url;
				 	 }else{
				 	 	 showErrorHandler("EXECUTE_FAIL","error","error");
				 	 }
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
//外发初始化
function initCostOutSoure(){
	var jobcd =urlPars.parm("jobcd");
	var path = $.getAjaxPath()+"initOutSoure";	
	var pars = {"job_cd":jobcd}; 
	$.ajax({
		url:path,
		data:JSON.stringify(pars),
		headers: {"requestID":$.getRequestID(),"requestName":$.getRequestNameByJcZH()},
		success:function(data){
           if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
			$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
				if(data[$.getRequestDataName()]['validatePower']==='0'){
				 url = "jczh/job_registration_list.html?view=init";
				 showInfoMsgHandler("SYS_VALIDATEPOWER_ERROR","info","info",url);
				 return;
				}
				if(data[$.getRequestDataName()]['messge']=="DATA_IS_NOT_EXIST"){
				 url = "jczh/job_registration_list.html?view=init";
				 showInfoMsgHandler("DATA_IS_NOT_EXIST","info","info",url);
				 return;	
				}
				orderInvoiceFlg = data[$.getRequestDataName()]['orderNoIsMustFlg'];
				//发票初始赋值
				$("#invoice_type").val("901");
				$("#invoice_text").val("901");
				//加载货币种类
				setPersonMoneyCode(data["userInfo"]);
			    //把本国货币小数点位数赋值到全局变量中
			 	pointNumber = data['userInfo']['pointNumber'];
			 	edit_common(1,Number(pointNumber));
			 	//初始化时，给numbbox设置小数点位数，为本国货币位数
			 	bus_price_common(1,pointNumber);
			 	foreignFormatFlg = data[$.getRequestDataName()]['foreignFormatFlg'];
			 	saleVatFormatFlg = data[$.getRequestDataName()]['saleVatFormatFlg'];
			    var jsData = {};
				jsData['cost_foreign_type'] = data[$.getRequestDataName()]['Foreign'];
				
				//setDomChildList(jsData,"cost_foreign_type");
				SelectObj.selectData = jsData;
				var selectID = [];
				selectID.push("cost_foreign_type");
				SelectObj.setStringFlg='';
				SelectObj.setSelectID = selectID;
				SelectObj.setSelectOfLog();
				for(var i=0;i<data[$.getRequestDataName()]['Foreign'].length;i++){
				aidcd.splice(i,0,data[$.getRequestDataName()]['Foreign'][i]['aidcd']);	
				change_utin.splice(i,0,data[$.getRequestDataName()]['Foreign'][i]['changeutin']);
				itmvalue.splice(i,0,data[$.getRequestDataName()]['Foreign'][i]['itmvalue']);
				}
				$('#order_date').datebox('setValue',data[$.getRequestDataName()]['systemDate'])
				//job信息区块，的得意先，与更新时间
				/*sessionStorage.setItem("addflg",data[$.getRequestDataName()]['CLDIV'][0]['saleAddFlag']);//壳上登录状态为1：已经登录  2：未登录
				sessionStorage.setItem("jobcd",data[$.getRequestDataName()]['CLDIV'][0]['jobcd']);//jobcd
				sessionStorage.setItem("sumamt",data[$.getRequestDataName()]['sumcost'][0]['sumamt']);//成本合计*/
				$("#job_name").text(data[$.getRequestDataName()]['CLDIV'][0]['jobname']);
				$("#jobcd").text(data[$.getRequestDataName()]['CLDIV'][0]['jobcd']);
				$("#divnm").text(data[$.getRequestDataName()]['CLDIV'][0]['divnm'])
				$("#upddate").text(data[$.getRequestDataName()]['CLDIV'][0]['upddate'])
				$("#username").text(data[$.getRequestDataName()]['CLDIV'][0]['username'])
				if(data[$.getRequestDataName()]['CLDIV'][0]['updusercolorv']!=""){
					$("#jobupdusercolor").css("color",data[$.getRequestDataName()]['CLDIV'][0]['updusercolorv']);
				}
				$("#sumamt").text(formatNumber(data[$.getRequestDataName()]['sumcost'][0]['sumamt']))
				$("#updatetime").text(data[$.getRequestDataName()]['sumcost'][0]['updatetime'])
				$("#costupdname").text(data[$.getRequestDataName()]['sumcost'][0]['costupdname'])
				if(data[$.getRequestDataName()]['sumcost'][0]['updusercolorv']!=""){
					$("#costupdusercolor").css("color",data[$.getRequestDataName()]['sumcost'][0]['updusercolorv']);
				}
				//判断原价情报是否可点击
				if(data[$.getRequestDataName()]['sumcost'][0]['updatetime']==undefined||data[$.getRequestDataName()]['sumcost'][0]['updatetime']==null||data[$.getRequestDataName()]['sumcost'][0]['updatetime']==""){
					$(".tip_hover").attr("style","text-decoration: none;color: #2c3e50;cursor: text!important;");
					$('.tip_hover').removeClass("icon-i");
				}
				//标签数据重构
				SelectObj.setLableList = data[$.getRequestDataName()]['lable'];
				labelToMySelect(data[$.getRequestDataName()]['lable']);
				
				//千分符数字还原为默认
				 $('input.cal-cost-box').next('span').find('input').focus(function(e) {
				  var value = e.target.value;
				  if(value != '') {
				   var new_value = recoveryNumber(value);
				   $(this).val(new_value);
				  }
				 })
				
				 $('input.cal-sale-box').next('span').find('input').focus(function(e) {
				  var value = e.target.value;
				  if(value != '') {
				   var new_value = recoveryNumber(value);
				   $(this).val(new_value);
				  }
				 });
				
			 	//job信息
				var jobcd =data[$.getRequestDataName()]['CLDIV'][0]['jobcd'];
				var powerList = JSON.parse($.getNodeList());
				//if(!isHavePower(powerList,[5,6,7,8])){
					if(data[$.getRequestDataName()]['CLDIV'][0]['saleAddFlag']=='1'&&data[$.getRequestDataName()]['sumcost']>0){
						window.job_cd=jobcd;
						$.layerShowDiv('job_detail','70%','80%',2,'../jczh/job_detail.shtml');	
					}else{
						if(data[$.getRequestDataName()]['CLDIV'][0]['saleAddFlag']=='1'){
						window.job_cd=jobcd;
						$.layerShowDiv('job_detail','70%','80%',2,'../jczh/job_detail.shtml');	
						}else{
						window.job_cd=jobcd;
						$.layerShowDiv('job_detail','70%','80%',2,'../jczh/job_detail_nosale.html');	
						}
					}
				//}
					$.layerShowDiv('icon-i','1000','600',2,'../common/jczh/cost_list.shtml',jobcd);
	           }else{
	           	showErrorFunHandler(data[$.getRequestMetaName()].result,"ERROR","ERROR");
	           }
       },
       error:function(data){
       		showErrorHandler("CONNECTION_SERVER_ERROR","ERROR","ERROR");
       }
	});
}

//发票弹窗初始化
function initInvoice(payee_cd,plan_dlvday,iframeWin){
	var path = $.getAjaxPath()+"initInvoice";	
	var pars = {"payee_cd":payee_cd,"plan_dlvday":plan_dlvday}; 
	$.ajax({
		url:path,
		data:JSON.stringify(pars),
		headers: {"requestID":$.getRequestID(),"requestName":$.getRequestNameByJcZH()},
		success:function(data){
           if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
           	    var payee = iframeWin.$('#invoice_type');
           	    var invoice_text = iframeWin.$('#invoice_text');
				$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
				var list = data[$.getRequestDataName()];
				list['invoice_type'] = list['invoice_type'];
				list['invoice_text'] = list['invoice_text'];
				for(var i=0;i<list['invoice_type'].length;i++){
					if(list['invoice_type'][i]['itemcd']=="902"){
					 list['invoice_type'].splice(i, 1);	
					}
				}
				for(var i=0;i<list['invoice_text'].length;i++){
					if(list['invoice_text'][i]['itemcd']=="902"){
					 list['invoice_text'].splice(i, 1);	
					}
				}
				list['cost_foreign_type'] = SelectObj.selectData.cost_foreign_type;
				SelectObj.selectData = list;
				SelectObj.setSelectID = ['invoice_type','invoice_text','cost_foreign_type'];
				SelectObj.setStringFlg='';
				SelectObj.setIfram = iframeWin;
				SelectObj.setSelectOfLog();
	       var pd = $('#pd').val();
			   	if(pd==1){
			   		var invoice_date =$('#invoice_date').val();
			   		var invoice_type =$('#invoice_type').val();
			   		var invoice_text =$('#invoice_text').val();
			   		var cost_rate =$('#cost_rate').text();
			   	    iframeWin.$('#invoice_date').datebox('setValue',invoice_date);
			   	 	iframeWin.$('#invoice_no').val($('#invoice_no').val());
			   	 	iframeWin.$('#invoice_type').val(invoice_type);
			   	 	iframeWin.$('#invoice_text').val(invoice_text);
			   	 	iframeWin.$('#vat_rate').val(cost_rate);
			   	 	iframeWin.$('#remark').val($('#remark pre').html());
			   	 	var deduction = $('#deduction').text();
			   	 	var deductionvalue = $('#deductionvalue').text();
					if(deduction!=''&&deduction!='[deduction]'){
					iframeWin.$('div.hidden').removeClass("hidden");
					iframeWin.$('#deduction').text(deduction);
					iframeWin.$('#deductionvalue').text(deductionvalue);
					
					}			   	 		
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



	