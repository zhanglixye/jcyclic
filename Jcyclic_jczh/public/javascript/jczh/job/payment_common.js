var calculateTax ="";
var changeFlg = 0;
//发票弹窗中发票种类变革监听
function commonInvoiceChange(pay_dlyday,payee_cd,invoice_text,invoice_type,iframeWin){
        //下拉框变更是监听,查询税率
	   	iframeWin.$('#invoice_type').change(function(){
		   	var invoice_text =iframeWin.$('#invoice_text').val();
			var invoice_type =iframeWin.$('#invoice_type').val();
			
		    if(invoice_text!=undefined&&invoice_text!=null&&invoice_text!=''&&
		       invoice_type!=undefined&&invoice_type!=null&&invoice_type!=''){
			    selectTax(pay_dlyday,payee_cd,invoice_text,invoice_type,iframeWin);
		      }
		    if(invoice_text==undefined||invoice_text==null||invoice_text==''||
		       invoice_type==undefined||invoice_type==null||invoice_type==''){
			    iframeWin.$('#deduction').text("");
			    iframeWin.$('#deductionvalue').text("");
			    iframeWin.$('#deduction').addClass("hidden");
		      }
	   	});
	   	iframeWin.$('#invoice_text').change(function(){
		   	var invoice_text =iframeWin.$('#invoice_text').val();
			var invoice_type =iframeWin.$('#invoice_type').val();
			if(invoice_text!=undefined&&invoice_text!=null&&invoice_text!=''&&
		       invoice_type!=undefined&&invoice_type!=null&&invoice_type!=''){
			    selectTax(pay_dlyday,payee_cd,invoice_text,invoice_type,iframeWin);
		      }
		  	if(invoice_text==undefined||invoice_text==null||invoice_text==''||
		       invoice_type==undefined||invoice_type==null||invoice_type==''){
		       	iframeWin.$('#deduction').text("");
		       	iframeWin.$('#deductionvalue').text("");
		       	iframeWin.$('#deduction').addClass("hidden");
		       }
	   	});
}
//支付情报登录主页面的发票种类变革监听
function invoiceChange(){
	    var pay_dlyday = $('#pay_dlyday').val();
	    var payee_cd = $('#payee_cd').val();
	    var invoice_type = $('#invoice_type').val();
 		var invoice_text = $('#invoice_text').val();
        //下拉框变更是监听,查询税率
	   	$('#invoice_type').change(function(){
		   	var invoice_text =$('#invoice_text').val();
			var invoice_type =$('#invoice_type').val();
		    if(invoice_text!=undefined&&invoice_text!=null&&invoice_text!=''&&
		       invoice_type!=undefined&&invoice_type!=null&&invoice_type!=''){
			    selectPaymentTax(pay_dlyday,payee_cd,invoice_text,invoice_type);
		      }
	   	});
	   	$('#invoice_text').change(function(){
		   	var invoice_text =$('#invoice_text').val();
			var invoice_type =$('#invoice_type').val();
			if(invoice_text!=undefined&&invoice_text!=null&&invoice_text!=''&&
		       invoice_type!=undefined&&invoice_type!=null&&invoice_type!=''){
			    selectPaymentTax(pay_dlyday,payee_cd,invoice_text,invoice_type);
		      }
		  
	   	});
}
//发票天窗中计算税率的
function selectTax(pay_dlyday,payee_cd,invoice_text,invoice_type,iframeWin){
	if(invoice_type==''||invoice_text==''||pay_dlyday==''||payee_cd==''){
		return;
	}else{
		var path = $.getAjaxPath()+"selectTax";	
		var pars = {"payee_cd":payee_cd,"plan_dlvday":pay_dlyday,"invoice_text":invoice_text,"invoice_type":invoice_type}; 
		$.ajax({
			url:path,
			data:JSON.stringify(pars),
			headers: {"requestID":$.getRequestID(),"requestName":$.getRequestNameByJcZH()},
			success:function(data){
	           if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
	           	 	$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
	           	 	if(data[$.getRequestDataName()]["vatrate"]==null||data[$.getRequestDataName()]["vatrate"]===''){
		           	        //没有税率，清空数据，不可点击显示
		           	        showErrorHandler('NOTFINDE_TAX','ERROR','ERROR');
		                  /*iframeWin.$("#invoicebtn").attr("disabled",true);
			       	        iframeWin.$('#invoice_date').datebox('setValue',null);
					   	 	iframeWin.$('#invoice_no')[0].value='';
					   	 	iframeWin.$('#invoice_type').val('');
					   	 	iframeWin.$('#invoice_text').val('');
					   	 	iframeWin.$('#remark')[0].value='';*/
					    	iframeWin.$('#vat_rate').val('');
					   	 	iframeWin.$('#deduction').addClass("hidden");
					   	 	iframeWin.$('#deduction').text('');
					   	 	iframeWin.$('#deductionvalue').text('');
					   	 	iframeWin.$(".plan_cost_foreign_amt").text(formatNumber(0.00));
		           	        iframeWin.$("#cost_tax").text(formatNumber(0.00));
		           	        iframeWin.$("#pay_amt").text(formatNumber(0.00));
		           	    }else{
			           	    iframeWin.$("#vat_rate").val(floatObj.multiply(data[$.getRequestDataName()]["vatrate"],100));
			           	    var deduction = data[$.getRequestDataName()]["deduction"];
			           	   if(deduction=='0'){
			           	    iframeWin.$("#deduction").text(getGridLanguage('deductionflg',deduction));
			           	    iframeWin.$("#deductionvalue").text(deduction);
			           	    //iframeWin.$("#deduction").attr("name","paymentregistration_not_deduction");
			           	    iframeWin.$('div.hidden').removeClass("hidden");
			           	    }else{
			           	     iframeWin.$("#deduction").text(getGridLanguage('deductionflg',deduction));	
			           	     iframeWin.$("#deductionvalue").text(deduction);
			           	     //iframeWin.$("#deduction").attr("name","paymentregistration_not_deduction");
			           	     iframeWin.$('div.hidden').removeClass("hidden");
			           	     iframeWin.$("#invoicebtn").attr("disabled",false);
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
}

//支付情报登录主页面计算税率的
function selectPaymentTax(pay_dlyday,payee_cd,invoice_text,invoice_type){
	
	if(invoice_type==''||invoice_text==''||pay_dlyday==''||payee_cd==''){
		return;
	}else{
	var path = $.getAjaxPath()+"selectTax";
	var pars = {"payee_cd":payee_cd,"plan_dlvday":pay_dlyday,"invoice_text":invoice_text,"invoice_type":invoice_type}; 
	$.ajax({
		url:path,
		data:JSON.stringify(pars),
		headers: {"requestID":$.getRequestID(),"requestName":$.getRequestNameByJcZH()},
		success:function(data){
           if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
           	 	$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
           	 	if(data[$.getRequestDataName()]["vatrate"]==null||data[$.getRequestDataName()]["vatrate"]===''){
	           	        showErrorHandler('NOTFINDE_TAX','ERROR','ERROR');
	           	         $('#cost_rate').text('');
	           	         $('.rate').text('');
	           	         $('#deduction').addClass("hidden");
	           	         $("#deduction").text('');
	           	         $("#deductionvalue").text('');
	           	         $("#deductionvalue").val('');
	           	         $(".plan_cost_foreign_amt").text(formatNumber(0.00));
	           	         $("#cost_tax").text(formatNumber(0.00));
	           	         $("#pay_amt").text(formatNumber(0.00));
	           	    }else{
	           	    	$('#pd').val(1);
	           	    	if(floatObj.multiply(data[$.getRequestDataName()]["vatrate"],100)!=$('#cost_rate').text()){
	           	    		var ifCostRate = part_language_change_new('IF_COST_RATE');
							$.messager.confirm('Confirmation', ifCostRate, function(r) {
								if(r) {
									 $('#cost_rate').text(floatObj.multiply(data[$.getRequestDataName()]["vatrate"],100));
									 $('.rate').text(floatObj.multiply(data[$.getRequestDataName()]["vatrate"],100));
						           	    var   deduction = data[$.getRequestDataName()]["deduction"];
						           	    if(deduction=='0'){
						           	     $("#deduction").text(getGridLanguage('deductionflg',deduction));
						           	     $("#deductionvalue").text(deduction);
						           	     //$("#deduction").attr("name","paymentregistration_not_deduction");
						           	     $('#deduction').removeClass("hidden");
						           	    }else{
						           	      $("#deduction").text(getGridLanguage('deductionflg',deduction));
						           	      $("#deductionvalue").text(deduction);
						           	      //$("#deduction").attr("name","paymentregistration_not_deduction");
						           	      $('#deduction').removeClass("hidden");
						           	    }
						           	     //如果查询到税率，在页面赋上税率值后，重新计算外发信息
						           	    calculateOutSoure();
			           	    	}
							},1);
							//off 默认选中
							$('.pop_z').closest('.messager-body').next('.messager-button').find('.l-btn').eq(0).attr('id','p_update_off');
	           	    	}
		           	    //$('#cost_rate').text(floatObj.multiply(data[$.getRequestDataName()]["vatrate"],100));
		           	    $('.rate').text(floatObj.multiply(data[$.getRequestDataName()]["vatrate"],100));
		           	    var   deduction = data[$.getRequestDataName()]["deduction"];
		           	    if(deduction=='0'){
		           	     $("#deduction").text(getGridLanguage('deductionflg',deduction));
		           	     $("#deductionvalue").text(deduction);
		           	     //$("#deduction").attr("name","paymentregistration_not_deduction");
		           	     $('#deduction').removeClass("hidden");
		           	    }else{
		           	      $("#deduction").text(getGridLanguage('deductionflg',deduction));
		           	      $("#deductionvalue").text(deduction);
		           	      //$("#deduction").attr("name","paymentregistration_not_deduction");
		           	      $('#deduction').removeClass("hidden");
		           	    }
		           	     //如果查询到税率，在页面赋上税率值后，重新计算外发信息
		           	    calculateOutSoure();
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
}
//发注先变更了，予定纳品日变更了，重新查询税率
function selecttTaxbypayee(pay_dlyday,payee_cd,invoice_text,invoice_type){
	
	if(invoice_type==''||invoice_text==''||pay_dlyday==''||payee_cd==''){
		//showErrorHandler("COST_RATE_MUST","info","info");
		return;
	}else{
	var path = $.getAjaxPath()+"selectTax";
	var pars = {"payee_cd":payee_cd,"plan_dlvday":pay_dlyday,"invoice_text":invoice_text,"invoice_type":invoice_type}; 
	$.ajax({
		url:path,
		data:JSON.stringify(pars),
		headers: {"requestID":$.getRequestID(),"requestName":$.getRequestNameByJcZH()},
		success:function(data){
           if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
           	 	$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
           	 	if(data[$.getRequestDataName()]["vatrate"]==null||data[$.getRequestDataName()]["vatrate"]===''){
	           	        showErrorHandler('NOTFINDE_TAX','ERROR','ERROR');
	           	         $('#cost_rate').text('')
	           	         $('#deduction').addClass("hidden");
	           	         $("#deduction").text('');
	           	         $("#deductionvalue").text('');
	           	         $(".plan_cost_foreign_amt").text(formatNumber(0.00));
	           	         $("#cost_tax").text(formatNumber(0.00));
	           	         $("#pay_amt").text(formatNumber(0.00));
	           	    }else{
	           	    	$('#pd').val(1);
		           	    $('#cost_rate').text(floatObj.multiply(data[$.getRequestDataName()]["vatrate"],100));
		           	    var   deduction = data[$.getRequestDataName()]["deduction"];
		           	    if(deduction=='0'){
		           	    $("#deduction").attr("name","paymentregistration_not_deduction");
		           	    //$("#deduction").text(getGridLanguage('deductionflg',deduction));
		           	    $("#deductionvalue").text(deduction);
		           	    }else{
		           	     $("#deduction").text(getGridLanguage('deductionflg',deduction));
		           	     $("#deductionvalue").text(deduction);
		           	     //$("#deduction").attr("name","paymentregistration_deduction");
		           	    }
		           	    //如果查询到税率，在页面赋上税率值后，重新计算外发信息
		           	    calculateOutSoure();
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
}

function paymentInit(){
		$('#language').change(function() {
		var paydeductionflg= $("#deduction").text();
		 if(paydeductionflg!=undefined&&paydeductionflg!=null&&paydeductionflg!=''){
	     var deductionflgtext =getGridLanguage('deductionflg',paydeductionflg);	
	    }else{
	    	var deductionflgtext ='';
	    }
	    $("#deduction").text(deductionflgtext);
		})
	
    //頁面税种变化监听
	invoiceChange();
	//$.layerShowDiv('setb2','29%','auto',1,$('#setbig1'));
	//lible 選擇彈窗
	$.layerShowDiv('icon-sheding1','400','auto',1,$('.label_set_t'));
	//标签追加
	$(".add_lable").click(function() {
		addLable("new_lable", "options_lable", 1);
	});
	//外货下拉变更时，计算外发成本
	$('#cost_foreign_type').change(function() {
		var money =$('#cost_foreign_type option:selected').text();//选中的文本
		$('#cost_need_change_money1').text(money);
		var index =document.getElementById("cost_foreign_type").selectedIndex;
		bus_price_common(1,itmvalue[parseInt(index)-1]);
		calculateOutSoure();
	});
	//输入金额变更，计算 外发成本
	$('input#cost_foreign_amt').numberbox({
		"onChange": function() {
			calculateOutSoure();
		}
	});
	$('#invoice_type').change(function() {
		var invoice_type = $('#invoice_type').val();
	 	var invoice_text = $('#invoice_text').val();
		var payee_cd = $("#payee_cd").val();
	 	var pay_dlyday = $("#pay_dlyday").val();
		//selectPaymentTax(pay_dlyday,payee_cd,invoice_text,invoice_type);
		var invoice_type = $('#invoice_type').find("option:selected").text();
		$('#invoice_type_show').text(invoice_type);
				
	});
	$('#invoice_text').change(function() {
		var invoice_type = $('#invoice_type').val();
	 	var invoice_text = $('#invoice_text').val();
		var payee_cd = $("#payee_cd").val();
	 	var pay_dlyday = $("#pay_dlyday").val();
		//selectPaymentTax(pay_dlyday,payee_cd,invoice_text,invoice_type);	
		var invoice_text = $('#invoice_text').find("option:selected").text();
		$('#invoice_text_show').text(invoice_text);
	});
	//税入，税拔变更，计算外发成本
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
	//予定纳品日变更重新查询税率
	$('#pay_dlyday').datebox({
	  "onChange": function() {
	    var invoice_type = $('#invoice_type').val();
	 	var invoice_text = $('#invoice_text').val();
		var payee_cd = $("#payee_cd").val();
	 	var pay_dlyday = $("#pay_dlyday").val();
	 	if(checkDateV(pay_dlyday)&&pay_dlyday.length==10){
		selectPaymentTax(pay_dlyday,payee_cd,invoice_text,invoice_type);
		}
	  }
	 });
	$.jobCommon();
	$.foreignGoodsShow();
	$('#common-dayT').datebox({});
	bodyh = $(document).height(); //浏览器当前窗口文档的高度 
	navh = bodyh - 135;
	$("#navright").css("height", navh + "px");
	$('#pay_dlyday').datebox({});
	$('#pay_hope_date').datebox({});
	//証憑登録弹窗
	$.layerShowDiv('icon-tianjia1', '400', 'auto', 1, $('.payment_pay'));
	//税率弹窗
	layerClick('mes-edit', '30%', '610', 2, '../common/jczh/invoice_edit.shtml');
	
	//增值税弹出
	$.layerShowDiv('add_tax_price_on', 'auto', 'auto', 1, $('.add_tax_price'));

	$("#switch-state1").on('switchChange.bootstrapSwitch', function(e, state) {
		 if(state) { //״̬Ϊon
		 	var index =document.getElementById("cost_foreign_type").selectedIndex;
			bus_price_common(1,itmvalue[parseInt(index)-1]);
		} else { //״̬Ϊ0FF
			bus_price_common(1,pointNumber);
			calculateOutSoure();
			
		}
	})
	
	//增值税编辑 原价部分
	$("#set-tax-price").click(function(e) {
		var haveVatFlg = $('.cost_rate_click').find('.active').find('input').val();
		var saleCostAmt = recoveryNumber($('span#cost_foreign_amt').text());
		var reqPayAmt = recoveryNumber($('#pay_amt').text());
		var vatAmt = recoveryNumber($('.edit-tax-price').val());
		if(vatAmt == "") {
			//			showErrorHandler("NO_SET_TAX",'info','info');
			//  Judgment_empty("price-border");
			$(".price-border").setBorderRed();
			validate($(".price-border"),part_language_change_new('NODATE'));
			return;
		}
		$(".price-border").setBorderBlack();
		$(".price-border").tooltip('destroy');
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
}

function advanceURL() {
	var str = '';
	var advance_title = $(".advancetitle").val();
	var advance_url = $(".advanceurl").val();
	var indexF = $(".advancetitle").attr('index');
	if(advance_title == '' || advance_url == '') {
		if(advance_title == '') {
			$(".advancetitle").css("border-color", "red");
			validate($(".advancetitle"),part_language_change_new('NODATE'));
		} else {
			$(".advancetitle").css('border-color', "#cccccc");
			$(".advancetitle").tooltip('destroy');
		}
		if(advance_url == '') {
			$(".advanceurl").css("border-color", "red");
			validate($(".advanceurl"),part_language_change_new('NODATE'));
		} else {
			$(".advanceurl").css('border-color', "#cccccc");
			$(".advanceurl").tooltip('destroy');
		}
		if(!$('.panel.window.panel-htop.messager-window').exist()){
 			showErrorHandler("NOT_EMPTY", "ERROR", "ERROR");
 		}
	} else if(advance_title != '' || advance_url != '') {
		if(advance_title != '' && advance_url != '') {
			if(!pingZhengFlag){	
				var numLi = $('.advance_url li').length;
				str = '<li class="s1">' +
				'<span value="'+numLi+'">' + advance_title + '</span> ' +
				'<i class="iconfont icon-guanbi" style="font-size: 11px;color: white;"></i>' +
				'<input type="hidden" class="urlvalue" name="" id="" value="' + advance_url + '" />' +
				'</li>';
				$('.advance_url').append($(str));
			}else{
				var liDom = $('.advance_url').find('li').find('span[value="'+indexF+'"]');
				liDom.text(advance_title);
				liDom.siblings('input[type="hidden"]').val(advance_url);
			}			
			$('#closeBtnInput').click();
			$(".advanceurl").val('');
			$(".advancetitle").val('');
			removeAdvEvent();
			addAdvEvent();
		} else if(advance_url != '') {
			$(".advanceurl").css('border-color', "#cccccc");
		} else if(advance_title != '') {
			$(".advanceurl").css('border-color', "#cccccc");
		}
	}
}
function addAdvEvent(){
	$('li.s1 .icon-guanbi').on('click',function(e){
		$(this).closest('li').remove();
	})
	//更改凭证变成状态
	$(".icon-tianjia1").on('click',function(e){
		$('.panel #breadcrumb li').attr('name','credential_registration'); 
		$('#save').attr('name','salescategoryRegistration_login'); 
		$("#save_name").val("");
		$("#save_url").val("");
		var nameDom = $(".panel .breadcrumb li.i18n");
		part_language_change(nameDom);
		part_language_change($("#save"));   
		part_language_change($('.panel #breadcrumb li'));     
	})
	$('li.s1 span').on('click',function(e){
		$('.panel #breadcrumb li').attr('name','credential_change'); 
		$('#save').attr('name','button_change'); 
		$(".advancetitle").css('border-color', "#cccccc");
		$(".advanceurl").css('border-color', "#cccccc");
		var nameDom = $(".panel .breadcrumb li.i18n");
		part_language_change(nameDom);
		part_language_change($("#save"));   
		part_language_change($('.panel #breadcrumb li'));     
		pingZhengFlag = true;
		var name = $(this).text(),
		url = $(this).siblings('input[type="hidden"]').val();
		var indexLi = $(this).attr('value');
		$(".advancetitle").tooltip('destroy');
		$(".advanceurl").tooltip('destroy');
		layer.open({
			type: 1,
			title: false,
			closeBtn: 0,
			shadeClose: true, //点击遮罩关闭层
			area: ['400px', 'auto'],
			content: $('div.hid.payment_pay'),
			success: function(layero, index) {
				layero.find('input#save_name').val(name);
				layero.find('input#save_name').attr('index',indexLi);
				layero.find('input#save_url').val(url);
				var closeBtn = layero.find('.iconfont.icon-guanbi,#closeBtnInput');
				closeBtn.on('click', function() {
					layer.close(index);
				})				
			}
		});
	})
}

function removeAdvEvent(){
	$('li.s1 .icon-guanbi').unbind('click');
	$('li.s1 span').unbind('click');
}

function toolTipLanguage(language){
	if(language == undefined){
		language = localStorage.getItem('language');
	}
	var toolTipO = part_language_change_new('PAY_UPDATE_INFO');
	$('.icon-tishi').tooltip({
	    position: 'right',
	    content: '<span style=\"color:#fff\">'+toolTipO+'</span>',
	    onShow: function(){
			$(this).tooltip('tip').css({
				backgroundColor: '#666',
				borderColor: '#666'
			});
	    }
	});
}

//计算外发方法
function calculateOutSoure(costIsHave){
			var index =document.getElementById("cost_foreign_type").selectedIndex;
			//浮点型精确计算costVatRate
			var m=0;
			var cost_rate=$("#cost_rate").text();
			//try{m+=cost_rate.split(".")[1].length}catch(e){}
			//try{m+="0.01".split(".")[1].length}catch(e){} 
			//var costVatRate=Number(cost_rate.replace(".",""))*Number("0.01".replace(".",""))/Math.pow(10,m) //卖上增值税率
			if(cost_rate!=undefined&&cost_rate!=null&&cost_rate!==''){
				var costVatRate=Number(cost_rate)*0.01; //卖上增值税率
			}else{
				showErrorHandler("NOTFINDE_TAX","info","info");
				return;
			}
			var costCode=change_utin[parseInt(index)-1];//卖上外货code
			var costPoint=pointNumber;//基本货币小数点
			var costMonet=$("input#cost_foreign_amt").numberbox('getValue');//卖上入力金额
			var costCurCode=$("#saleCurCode").val();//卖上换算CODE
			//var costVatRate=number($("#cost_rate").text())*0.01;
			if(costIsHave==undefined){
			var costIsHave= $(".cost_rate_click .active input[name='saleIsHave']").val();//税入税拔，0：税拔；1：税入	
			}
		    //计算税率
		    var cost_obj=calculateCostHandler(costMonet,costCurCode,costCode,costVatRate,costPoint,costIsHave,foreignFormatFlg,saleVatFormatFlg);
			$('.plan_cost_foreign_amt').text(formatNumber(cost_obj['costBase']));
			$('.plancost_tax').text(formatNumber(cost_obj['costVatBase']));
			$('.plan_vat_amt').text(formatNumber(cost_obj['payBase']));
			
			calculateTax=cost_obj['costVatBase'];
			changeFlg =0;
}

function setpayPersonMoneyCode(personMessage) {
	//获取登陆者货
	var toplange = $('#language').val();
	if(toplange==undefined){
		toplange = parent.$('#language').val();
	}
	var val = '';
	switch(toplange) {
		case "jp":
			val = personMessage['moneyjp'];
			break;
		case "zc":
			val = personMessage['moneyzc'];
			break;
		case "zt":
			val = personMessage['moneyzt'];
			break;
		case "en":
			val = personMessage['moneyen'];
			break;
	};

	
	$('#cost_foreign_money').text(val);
	
}