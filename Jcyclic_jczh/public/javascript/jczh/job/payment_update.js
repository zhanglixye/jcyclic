var aidcd = new Array();
var change_utin = new Array();
var itmvalue = new Array();
var pointNumber ='';
var foreignFormatFlg='';
var saleVatFormatFlg='';
var lockflg;
var moneyUtin="";
var uInfo = {};
var orderInvoiceFlg;
$(function() {
	//初始化
	initPayedit();
	//初始化页面所有监听时间
	paymentInit();
	//初始化toolTip语言切换不需要传参数
	toolTipLanguage();
	$("#language").change(function() {
		
		if(moneyUtin != "")
		{
			var  costmoney=getForgMoneyUnit(SelectObj.selectData.Foreign,moneyUtin);
			$('#cost_foreign_money').text(costmoney); //右侧外发信息显示的外货
		}else{
			setpayPersonMoneyCode(uInfo);
		}
		
    });
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
function layerClick(classId, width, height, flag, urlDom) {
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
	$('.' + classId).on('click', function(e) {
		//发票弹窗初始化方法
		if(classId == "mes-edit") {
			var payee_cd = $("#payee_cd").val();
			var pay_dlyday = $("#pay_dlyday").val();
			//验证承包方很交付日不为空
			if(payee_cd != null && payee_cd != '' && pay_dlyday != null && pay_dlyday != '') {
				layer.open({
					type: flag,
					//title: ['取引先','font-size:16px;font-weight:bold;'],
					title: false,
					closeBtn: 0,
					shadeClose: true, //点击遮罩关闭层
					area: [width, height],
					//move:false,
					content: urlDom,
					success: function(layero, index) {
						var iframeWin = window[layero.find('iframe')[0]['name']];
						var closeBtn = layer.getChildFrame('.iconfont.icon-guanbi,#closeBtnInput', index);
						//初始化加载下拉框内容
						initInvoice(payee_cd, pay_dlyday, iframeWin);
						//下拉框变更是监听,查询税率
					    //下拉框变更是监听,查询税率,在payment_common.js
			    		 commonInvoiceChange(pay_dlyday,payee_cd,invoice_text,invoice_type,iframeWin);
                        //是否有小红星
                        if (orderInvoiceFlg == 1){
                            iframeWin.$('.invoice_edit_red_point').removeClass('hidden');
                        }else {
                            iframeWin.$('.invoice_edit_red_point').addClass('hidden');
                        }
						//显示按钮监听，把弹窗中的数据付到父页面的中
						iframeWin.$('#invoicebtn').click(function() {
							if(orderInvoiceFlg == 1 && iframeWin.$('#invoice_no').val() == "")
					   		{
					   			iframeWin.$('#invoice_no').css("border-color", "red");
								iframeWin.$('#invoice_no').tooltip({
									position: 'left',
									content: '<span style=\"color:#fff\">' + part_language_change_new('NODATE') + '</span>',
									onShow: function() {
										iframeWin.$(this).tooltip('tip').css({
											backgroundColor: 'red',
											borderColor: 'red',
											top:'148.717px'
										});
									}
								});
								showErrorHandler("NOT_EMPTY", "ERROR", "ERROR");
					   			return ;
					   		}else{
					   			iframeWin.$('#invoice_no').css("border-color", "#cccccc");
								iframeWin.$('#invoice_no').tooltip('destroy');
					   		}
							$('#pd').val(1);
							$('#invoice_date').text(iframeWin.$('#invoice_date').val());
							$('#invoice_no').text(iframeWin.$('#invoice_no').val());
							$('#invoice_type').val(iframeWin.$('#invoice_type').val());
							$('#invoice_text').val(iframeWin.$('#invoice_text').val());
							$('#cost_rate').text(iframeWin.$('#vat_rate').val());
							$('#v_remark pre').text(iframeWin.$('#remark').val());
							$('#deduction').text(iframeWin.$('#deduction').text());
							$('#deductionvalue').text(iframeWin.$('#deductionvalue').text());
							$('.rate').text($('#cost_rate').text());
							$('#invoice_type').change();
							$('#invoice_text').change();
							calculateOutSoure();
							layer.close(index);
						});
						closeBtn.on('click', function() {
							layer.close(index);
						})
					}
				});

			} else {
				showErrorHandler('NOT_CHOOSE_PLYDATE', 'Warning', 'Warning');
			}
		} else {
			layer.open({
				type: flag,
				//title: ['取引先','font-size:16px;font-weight:bold;'],
				title: false,
				closeBtn: 0,
				shadeClose: true, //点击遮罩关闭层
				area: [width, height],
				//move:false,
				content: urlDom,
				success: function(layero, index) {
					var iframeWin = window[layero.find('iframe')[0]['name']];
					var closeBtn = layer.getChildFrame('.iconfont.icon-guanbi,#closeBtnInput', index);
				}
			});
		}

	})

}
//初始化加载
function initPayedit() {
	var jobcd =urlPars.parm("jobcd");
	var costno =urlPars.parm("costno");
	var path = $.getAjaxPath() + "initPayInfo";
	var pars = {
		"job_cd": jobcd,"cost_no": costno,
	};
	$.ajax({
		url: path,
		data: JSON.stringify(pars),
		headers: {
			"requestID": $.getRequestID(),
			"requestName": $.getRequestNameByJcZH()
		},
		success: function(data) {
			//页面跳到头部
	 		$('aside#content').scrollTop(0);
			if(data[$.getRequestMetaName()].result == $.getRequestStatusName()) {
			  	$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
			  	
			  	if(data[$.getRequestDataName()]['validatePower']==='0'){
				 var url = "jczh/cost_list.html?view=6";//此处拼接内容
				 showInfoMsgHandler("SYS_VALIDATEPOWER_ERROR","info","info",url);
				 return;
				}
			  	if(data[$.getRequestDataName()]['messge']=='DATA_IS_NOT_EXIST'||data[$.getRequestDataName()]['messge']=='DATA_IS_NOT_EXIST_PAYUPDATE'){
				 var url = "jczh/cost_list.html?view=6";//此处拼接内容
				 showInfoMsgHandler("DATA_IS_NOT_EXIST","info","info",url);
				 return;
				}
			  	orderInvoiceFlg = data[$.getRequestDataName()]['orderNoIsMustFlg'];
			  	//给pdf的checkbox设置默认值
			  	if(data[$.getRequestDataName()]['pdfflg']==1){
			  	$("#PayConfirmOutPDF").prop("checked", true);	
			  	};
			  	//更新锁
			  	lockflg =data[$.getRequestDataName()]['pay'][0]['lockflg']
			  	 //加载货币种类
				setPersonMoneyCode(data["userInfo"]);
				//把本国货币小数点位数赋值到全局变量中
			 	pointNumber = data['userInfo']['pointNumber'];
			 	edit_common(1,Number(pointNumber));
			 	foreignFormatFlg = data[$.getRequestDataName()]['foreignFormatFlg'];
			 	saleVatFormatFlg = data[$.getRequestDataName()]['saleVatFormatFlg'];
			    var list = data[$.getRequestDataName()];
				list['cost_foreign_type'] = list['Foreign'];
				list['invoice_type'] = list['invoice_type'];
				list['invoice_text'] = list['invoice_text'];
				$("#costtype").text(data[$.getRequestDataName()]['outinfo'][0]['costtype']);
				//过了发注不明，立替不明
				for(var i=0;i<list['invoice_type'].length;i++){
						if(list['invoice_type'][i]['itemcd']=="902"){
							 list['invoice_type'].splice(i, 1);
							 for(var i=0;i<list['invoice_type'].length;i++){
							 	if(list['invoice_type'][i]['itemcd']=="901"){
								 list['invoice_type'].splice(i, 1);	
								}
							 }
						}
						
					}
				for(var k=0;k<list['invoice_text'].length;k++){
						if(list['invoice_text'][k]['itemcd']=="902"){
						 list['invoice_text'].splice(k, 1);	
						 for(var k=0;k<list['invoice_text'].length;k++){
						 	if(list['invoice_text'][k]['itemcd']=="901"){
							 list['invoice_text'].splice(k, 1);	
							}
						 }
						}
						
				}
				SelectObj.selectData = list;
				SelectObj.setSelectID = ['cost_foreign_type','invoice_type','invoice_text'];
				SelectObj.setStringFlg='';
				SelectObj.setSelectOfLog();
				for(var i = 0; i < data[$.getRequestDataName()]['Foreign'].length; i++) {
					aidcd.splice(i,0,data[$.getRequestDataName()]['Foreign'][i]['aidcd']);
					change_utin.splice(i,0,data[$.getRequestDataName()]['Foreign'][i]['changeutin']);
					itmvalue.splice(i,0,data[$.getRequestDataName()]['Foreign'][i]['itmvalue']);
				}
				
				 var ifCostRate = part_language_change_new('IF_COST_RATE');
				if(data[$.getRequestDataName()]['pay'][0]['payrate']!= data[$.getRequestDataName()]['outinfo'][0]['costVatRate']){
					
					$.messager.confirm('Confirmation', ifCostRate, function(r) {
						if(r) {
							//外发信息显示
							if(data[$.getRequestDataName()]['outinfo'].length>0){
								$("#cost_no").text(data[$.getRequestDataName()]['outinfo'][0]['costno']);
								$("#cost_name").text(data[$.getRequestDataName()]['outinfo'][0]['costname']);
								$("#costrate").text(floatObj.multiply(data[$.getRequestDataName()]['outinfo'][0]['costVatRate'],100));
								
								var  costmoneypointindex=$("#cost_foreign_type option[value = '"+data[$.getRequestDataName()]['outinfo'][0]['costforeigntype']+"']")[0].index;
								costpoint =itmvalue[costmoneypointindex-1];
								$("#costforeignamt").text(formatNumber(data[$.getRequestDataName()]['outinfo'][0]['costforeignamt'],costpoint));
								$("#costrmb").text(formatNumber(data[$.getRequestDataName()]['pay'][0]['payamt']));
								$("#costvatamt").text(formatNumber(data[$.getRequestDataName()]['pay'][0]['payvatamt']));
								$("#outsoureamt").text(formatNumber(data[$.getRequestDataName()]['pay'][0]['payrmb']));
								$("#plandlvday").text(data[$.getRequestDataName()]['outinfo'][0]['plandlvday']);
								$("#orderdate").text(data[$.getRequestDataName()]['outinfo'][0]['orderdate']);
								$("#outsoureuser").text(data[$.getRequestDataName()]['outinfo'][0]['outsoureuser']);
								$("#costremark pre").html(data[$.getRequestDataName()]['outinfo'][0]['costremark']);
								$("#costupdate").text(data[$.getRequestDataName()]['outinfo'][0]['costupdate']);
								$("#costupdusercolor").css("color",data[$.getRequestDataName()]['outinfo'][0]['updusercolorv']);
								$("#costupdusername").text(data[$.getRequestDataName()]['outinfo'][0]['username']);
								$('.outsource').find('.activeT').find('input').each(function(index, element) {
								if(element.value != data[$.getRequestDataName()]['outinfo'][0]['costishave']) {
										$(element).parents('label').toggleClass('activeT');
										$(element).parents('label').siblings('label').toggleClass('activeT');
										$(element).parents('label').hide();
									}
								});
							}
							
							//凭证信息初始化
							var rooftrn = data[$.getRequestDataName()]['prooftrn'];
							var str = '';
							for(var i = 0; i < rooftrn.length; i++) {
								str += '<li class="s1">' +
									'<span value="'+i+'">' + rooftrn[i].PROOF_TITLE + '</span> ' +
									'<i class="iconfont icon-guanbi" style="font-size: 11px;color: white;"></i>' +
									'<input type="hidden" name="" id="" value="' + rooftrn[i].PROOF_URL + '" />' +
									'</li>';
							}
							$('.advance_url').empty();
							$('.advance_url').append($(str));
							//刪除憑證事件
							addAdvEvent();
							if(data[$.getRequestDataName()]['CLDIV'].length>0){
								//job信息区块，的得意先，与更新时间
								///sessionStorage.setItem("addflg", data[$.getRequestDataName()]['CLDIV'][0]['saleAddFlag']); //壳上登录状态为1：已经登录  2：未登录				
								$("#jobcd").text(data[$.getRequestDataName()]['CLDIV'][0]['jobcd']);
								$("#job_name").text(data[$.getRequestDataName()]['CLDIV'][0]['jobname']);
								$("#divnm").text(data[$.getRequestDataName()]['CLDIV'][0]['divnm']);
								$("#upddate").text(data[$.getRequestDataName()]['CLDIV'][0]['upddate']);
								$("#username").text(data[$.getRequestDataName()]['CLDIV'][0]['username']);
								if(data[$.getRequestDataName()]['CLDIV'][0]['updusercolorv']!=""){
									$("#jobupdusercolor").css("color",data[$.getRequestDataName()]['CLDIV'][0]['updusercolorv']);
								}
							
								//job信息
								var jobcd =data[$.getRequestDataName()]['CLDIV'][0]['jobcd'];
								var powerList = JSON.parse($.getNodeList());
								if(!isHavePower(powerList,[5,6,7,8])){
								showErrorHandler("SYS_VALIDATEPOWER_ERROR","Warning","Warning");
								  return;	
								}else{
								if(data[$.getRequestDataName()]['CLDIV'][0]['saleAddFlag']=='1'){
									window.job_cd=jobcd;
									$.layerShowDiv('job_detail','70%','80%',2,'../jczh/job_detail.shtml');	
								}else{
									window.job_cd=jobcd;
									$.layerShowDiv('job_detail','70%','80%',2,'../jczh/job_detail_nosale.html');	
								}
								$.layerShowDiv('icon-i','1000','600',2,'../common/jczh/cost_list.shtml',jobcd);	
								}
							}
							if(data[$.getRequestDataName()]['sumcost'].length>0){
								$("#sumamt").text(data[$.getRequestDataName()]['sumcost'][0]['sumamt'])
								$("#updatetime").text(data[$.getRequestDataName()]['sumcost'][0]['updatetime'])
								$("#costupdname").text(data[$.getRequestDataName()]['sumcost'][0]['costupdname'])	
								//判断成本信息感叹号，是否显示
							 	if(Number(data[$.getRequestDataName()]['sumcost'][0]['sumamt'])>0){
										$('.icon-i').removeClass("hidden")
								}
							}
							
							
							//标签数据重构
							SelectObj.setLableList = data[$.getRequestDataName()]['lable'];
							SelectObj.setChooseLableList = data[$.getRequestDataName()]['paylable'];
							labelToMySelect(data[$.getRequestDataName()]['lable']);
							setLableArea(data[$.getRequestDataName()]['paylable']);
							if(data[$.getRequestDataName()]['pay'].length>0){
								//支付信息显示
								var pay = data[$.getRequestDataName()]['pay'][0];
								$("#costname").text(data[$.getRequestDataName()]['pay'][0]['payname']);
								$('.outsource').find('.active').find('input').each(function(index, element) {
									if(element.value != data[$.getRequestDataName()]['pay'][0]['payishave']) {
										$(element).parents('label').toggleClass('active');
										$(element).parents('label').siblings('label').toggleClass('active');
									}
								});
								$("#saleCurCode").val(pay['paycurecode']!=""?Number(pay['paycurecode']):""); //换算code、、汇率
								$(".rate").text(floatObj.multiply(data[$.getRequestDataName()]['outinfo'][0]['costVatRate'],100));
								$("#cost_rate").text(floatObj.multiply(data[$.getRequestDataName()]['outinfo'][0]['costVatRate'],100));
								$("#input_no").text(pay['inputno']);
								$("#payee_cd").val(pay['payeecd']);
								$("#pay_dlyday").datebox('setValue', pay['paydlyday'])
								$("#pay_hope_date").datebox('setValue', pay['payhopedate']) //希望交付日
								$("#payremark").val(pay['payremark']);
								$("#cost_foreign_type").val(pay['payforeigntype']); //外货
								$("input#cost_foreign_amt").numberbox('setValue',pay['payforeignamt']);//输入金额
								$("#costtype").text(data[$.getRequestDataName()]['outinfo'][0]['costtype']);
								$("#common-dayT").datebox('setValue', pay['payusedate']) //適用日
								$("#cost_refer").val(pay['payrefer']); //参照先
								$("#invoice_type").val(pay['invoicetype']);
							    $("#invoice_text").val(pay['invoicetext']);
							    $("#deductionvalue").text(pay['deductionflg']);
							    if(pay['deductionflg']=="1"){
								  $("#deduction").text(getGridLanguage('deductionflg',pay['deductionflg']));
								}else{
								  $("#deduction").text(getGridLanguage('deductionflg',pay['deductionflg']));
								}
							    
							    //判断是否有外货
							    var money =$('#cost_foreign_type option:selected').text();//选中的文本
								if(pay['paycurecode'] != ''||pay['payrefer'] != ''||pay['payusedate']!= '') {
									$('#switch-state1').bootstrapSwitch('state', 1);
									//外货符号初始化加载
									$('#cost_need_change_money1').text(money);
									//重新为外货下拉复制，不可删除，删掉代码会有逻辑问题
									$("#cost_foreign_type").val(pay['payforeigntype']); //外货
									var index =document.getElementById("cost_foreign_type").selectedIndex;
									//初始化时，给numbbox设置小数点位数，为本国货币位数
							 		bus_price_common(1,itmvalue[parseInt(index)-1]);
								}else{
									//初始化时，给numbbox设置小数点位数，为本国货币位数
						 			bus_price_common(1,pointNumber);
								}
								
								/*else if(pay['payrefer'] != null && pay['payrefer'] != '') {
									$('#switch-state1').bootstrapSwitch('state', 1);
									$('#cost_need_change_money1').text(money);
									$("#cost_foreign_type").val(pay['payforeigntype']); //外货
									var index =document.getElementById("cost_foreign_type").selectedIndex;
									//初始化时，给numbbox设置小数点位数，为本国货币位数
							 			bus_price_common(1,itmvalue[parseInt(index)-1]);
								} else if(pay['payusedate'] != null && pay['payrefer'] != '') {
									$('#switch-state1').bootstrapSwitch('state', 1);
									$('#cost_need_change_money1').text(money);
									$("#cost_foreign_type").val(pay['payforeigntype']); //外货
									var index =document.getElementById("cost_foreign_type").selectedIndex;
									//初始化时，给numbbox设置小数点位数，为本国货币位数
							 			bus_price_common(1,itmvalue[parseInt(index)-1]);
								}*/
								//支付区域计算结果最后赋值
							
								$('#pay_amt').text(formatNumber(pay['payrmb']));
							}
							//重新为外货下拉复制，不可删除，删掉代码会有逻辑问题
							$("#cost_foreign_type").val(pay['payforeigntype']); //外货
							//外发货币赋值
							if(data[$.getRequestDataName()]['outinfo'][0]['costcurecode']==""){
								setpayPersonMoneyCode(data["userInfo"]);
							}else{
								var  costmoney=$("#cost_foreign_type option[value = '"+data[$.getRequestDataName()]['outinfo'][0]['costforeigntype']+"']").text();
								$('#cost_foreign_money').text(costmoney); //右侧外发信息显示的外货
							}
							
							
							//给发票弹窗是否有值的判断位赋值
							$("#pd").val(1);
							if(data[$.getRequestDataName()]['Invoiceintrn'].length>0){
								//发票值
				/*				$("#invoice_date").datebox('setValue', data[$.getRequestDataName()]['Invoiceintrn'][0]['invoicedate'])
								$("#invoice_no").val(data[$.getRequestDataName()]['Invoiceintrn'][0]['invoiceno']);	
								$("#v_remark").val(data[$.getRequestDataName()]['Invoiceintrn'][0]['remark']);*/
								$("#invoice_date").text(data[$.getRequestDataName()]['Invoiceintrn'][0]['invoicedate'])
								$("#invoice_no").text(data[$.getRequestDataName()]['Invoiceintrn'][0]['invoiceno']);	
								$("#v_remark pre").text(data[$.getRequestDataName()]['Invoiceintrn'][0]['remark']);
							}
							  
							  $("#languageNameUpd").attr("name","");
							 //登录者更新者卡片逻辑
							 //支付作业者
							 if(pay["adddate"]!=null&&pay["adddate"]!=''){
								  $("#adduser").text(pay["addUserName"]);//支付登录者
								  $("#adddate").text(new Date(pay["adddate"]).format_extend('yyyy-MM-dd hh:mm:ss'));//外发登录时间	
							 	  $("#languageNameAdd").attr("name","paymentregistration_jobLogon");
							 	  $("#addcolor").css("color",pay["payaddusercolor"]);
							 }else{
							 	 $("#addhidden").addClass('hidden');
							 	 $("#updhidden").addClass('hidden')
							 }
							 if(pay["update"]!=null&&pay["update"]!=''){
								  $("#upduser").text(pay["upUserName"]);//支付更新者
								  $("#update").text(new Date(pay["update"]).format_extend('yyyy-MM-dd hh:mm:ss'));//外发更新时间
							 	  $("#languageNameUpd").attr("name","paymentregistration_jobUpdate");
							 	  $("#updcolor").css("color",pay["payupdusercolor"]);
							 }else{
							 	 $("#updhidden").addClass('hidden');
							 }
							 
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
							 
							 //当外发更新时间和登陆时间一致的时候，更新不显示
							if(pay["adddate"]==pay["update"]){
							   $("#updhidden").addClass("hidden");
							}
							//2019/2/21功能更改，支付更新画面，主界面的发票编辑功能，更改为发票显示功能。
							//追求最小代码改动量，故原发票编辑功能代码不变，页面编辑控件全部隐藏。追加显示项赋值代码
							var invoice_type = $('#invoice_type').find("option:selected").text();
							$('#invoice_type_show').text(invoice_type);
							var invoice_text = $('#invoice_text').find("option:selected").text();
							$('#invoice_text_show').text(invoice_text);
							 //计上计， 并且 支付的登陆日期实在结账之后可以取消   如果   支付的登陆日期在结账之前不可以取消   消除按钮非活性
							 if(data[$.getRequestDataName()]['joblist'].length>=1){
								 if(data[$.getRequestDataName()].pay[0].payAdddate<data[$.getRequestDataName()].checkDate){
									 $("[name='calcel']").attr("disabled",true);
								 }
				             }
							
						}else{
							//外发信息显示
							if(data[$.getRequestDataName()]['outinfo'].length>0){
								$("#cost_no").text(data[$.getRequestDataName()]['outinfo'][0]['costno']);
								$("#cost_name").text(data[$.getRequestDataName()]['outinfo'][0]['costname']);
								$("#costrate").text(floatObj.multiply(data[$.getRequestDataName()]['outinfo'][0]['costrate'],100));
								
								var  costmoneypointindex=$("#cost_foreign_type option[value = '"+data[$.getRequestDataName()]['outinfo'][0]['costforeigntype']+"']")[0].index;
								costpoint =itmvalue[costmoneypointindex-1];
								$("#costforeignamt").text(formatNumber(data[$.getRequestDataName()]['outinfo'][0]['costforeignamt'],costpoint));
								$("#costrmb").text(formatNumber(data[$.getRequestDataName()]['outinfo'][0]['costrmb']));
								$("#costvatamt").text(formatNumber(data[$.getRequestDataName()]['outinfo'][0]['costvatamt']));
								$("#outsoureamt").text(formatNumber(data[$.getRequestDataName()]['outinfo'][0]['outsoureamt']));
								$("#plandlvday").text(data[$.getRequestDataName()]['outinfo'][0]['plandlvday']);
								$("#orderdate").text(data[$.getRequestDataName()]['outinfo'][0]['orderdate']);
								$("#outsoureuser").text(data[$.getRequestDataName()]['outinfo'][0]['outsoureuser']);
								$("#costtype").text(data[$.getRequestDataName()]['outinfo'][0]['costtype']);
								$("#costremark pre").html(data[$.getRequestDataName()]['outinfo'][0]['costremark']);
								$("#costupdate").text(data[$.getRequestDataName()]['outinfo'][0]['costupdate']);
								$("#costupdusercolor").css("color",data[$.getRequestDataName()]['outinfo'][0]['updusercolorv']);
								$("#costupdusername").text(data[$.getRequestDataName()]['outinfo'][0]['username']);
								$('.outsource').find('.activeT').find('input').each(function(index, element) {
								if(element.value != data[$.getRequestDataName()]['outinfo'][0]['costishave']) {
										$(element).parents('label').toggleClass('activeT');
										$(element).parents('label').siblings('label').toggleClass('activeT');
										$(element).parents('label').hide();
									}
								});
							}
							
							//凭证信息初始化
							var rooftrn = data[$.getRequestDataName()]['prooftrn'];
							var str = '';
							for(var i = 0; i < rooftrn.length; i++) {
								str += '<li class="s1">' +
									'<span value="'+i+'">' + rooftrn[i].PROOF_TITLE + '</span> ' +
									'<i class="iconfont icon-guanbi" style="font-size: 11px;color: white;"></i>' +
									'<input type="hidden" name="" id="" value="' + rooftrn[i].PROOF_URL + '" />' +
									'</li>';
							}
							$('.advance_url').empty();
							$('.advance_url').append($(str));
							//刪除憑證事件
							addAdvEvent();
							if(data[$.getRequestDataName()]['CLDIV'].length>0){
								//job信息区块，的得意先，与更新时间
								///sessionStorage.setItem("addflg", data[$.getRequestDataName()]['CLDIV'][0]['saleAddFlag']); //壳上登录状态为1：已经登录  2：未登录				
								$("#jobcd").text(data[$.getRequestDataName()]['CLDIV'][0]['jobcd']);
								$("#job_name").text(data[$.getRequestDataName()]['CLDIV'][0]['jobname']);
								$("#divnm").text(data[$.getRequestDataName()]['CLDIV'][0]['divnm']);
								$("#upddate").text(data[$.getRequestDataName()]['CLDIV'][0]['upddate']);
								$("#username").text(data[$.getRequestDataName()]['CLDIV'][0]['username']);
								if(data[$.getRequestDataName()]['CLDIV'][0]['updusercolorv']!=""){
									$("#jobupdusercolor").css("color",data[$.getRequestDataName()]['CLDIV'][0]['updusercolorv']);
								}
							
								//job信息
								var jobcd =data[$.getRequestDataName()]['CLDIV'][0]['jobcd'];
								var powerList = JSON.parse($.getNodeList());
								if(!isHavePower(powerList,[5,6,7,8])){
								showErrorHandler("SYS_VALIDATEPOWER_ERROR","Warning","Warning");
								  return;	
								}else{
								if(data[$.getRequestDataName()]['CLDIV'][0]['saleAddFlag']=='1'){
									window.job_cd=jobcd;
									$.layerShowDiv('job_detail','70%','80%',2,'../jczh/job_detail.shtml');	
								}else{
									window.job_cd=jobcd;
									$.layerShowDiv('job_detail','70%','80%',2,'../jczh/job_detail_nosale.html');	
								}
								$.layerShowDiv('icon-i','1000','600',2,'../common/jczh/cost_list.shtml',jobcd);	
								}
							}
							if(data[$.getRequestDataName()]['sumcost'].length>0){
								$("#sumamt").text(data[$.getRequestDataName()]['sumcost'][0]['sumamt'])
								$("#updatetime").text(data[$.getRequestDataName()]['sumcost'][0]['updatetime'])
								$("#costupdname").text(data[$.getRequestDataName()]['sumcost'][0]['costupdname'])	
								//判断成本信息感叹号，是否显示
							 	if(Number(data[$.getRequestDataName()]['sumcost'][0]['sumamt'])>0){
										$('.icon-i').removeClass("hidden")
								}
							}
							
							
							//标签数据重构
							SelectObj.setLableList = data[$.getRequestDataName()]['lable'];
							SelectObj.setChooseLableList = data[$.getRequestDataName()]['paylable'];
							labelToMySelect(data[$.getRequestDataName()]['lable']);
							setLableArea(data[$.getRequestDataName()]['paylable']);
							if(data[$.getRequestDataName()]['pay'].length>0){
								//支付信息显示
								var pay = data[$.getRequestDataName()]['pay'][0];
								$("#costname").text(data[$.getRequestDataName()]['pay'][0]['payname']);
								$('.outsource').find('.active').find('input').each(function(index, element) {
									if(element.value != data[$.getRequestDataName()]['pay'][0]['payishave']) {
										$(element).parents('label').toggleClass('active');
										$(element).parents('label').siblings('label').toggleClass('active');
									}
								});
								$("#saleCurCode").val(pay['paycurecode']!=""?Number(pay['paycurecode']):""); //换算code、、汇率
								$(".rate").text(floatObj.multiply(pay['payrate'],100));
								$("#cost_rate").text(floatObj.multiply(pay['payrate'],100));
								$("#input_no").text(pay['inputno']);
								$("#payee_cd").val(pay['payeecd']);
								$("#pay_dlyday").datebox('setValue', pay['paydlyday'])
								$("#pay_hope_date").datebox('setValue', pay['payhopedate']) //希望交付日
								$("#payremark").val(pay['payremark']);
								$("#cost_foreign_type").val(pay['payforeigntype']); //外货
								$("input#cost_foreign_amt").numberbox('setValue',pay['payforeignamt']);//输入金额
							
								$("#common-dayT").datebox('setValue', pay['payusedate']) //適用日
								$("#cost_refer").val(pay['payrefer']); //参照先
								$("#invoice_type").val(pay['invoicetype']);
							    $("#invoice_text").val(pay['invoicetext']);
							    $("#deductionvalue").text(pay['deductionflg']);
							    if(pay['deductionflg']=="1"){
								  $("#deduction").text(getGridLanguage('deductionflg',pay['deductionflg']));
								}else{
								  $("#deduction").text(getGridLanguage('deductionflg',pay['deductionflg']));
								}
							    
							    //判断是否有外货
							    var money =$('#cost_foreign_type option:selected').text();//选中的文本
								if(pay['paycurecode'] != ''||pay['payrefer'] != ''||pay['payusedate']!= '') {
									$('#switch-state1').bootstrapSwitch('state', 1);
									//外货符号初始化加载
									$('#cost_need_change_money1').text(money);
									//重新为外货下拉复制，不可删除，删掉代码会有逻辑问题
									$("#cost_foreign_type").val(pay['payforeigntype']); //外货
									var index =document.getElementById("cost_foreign_type").selectedIndex;
									//初始化时，给numbbox设置小数点位数，为本国货币位数
							 		bus_price_common(1,itmvalue[parseInt(index)-1]);
								}else{
									//初始化时，给numbbox设置小数点位数，为本国货币位数
						 			bus_price_common(1,pointNumber);
								}
								
								/*else if(pay['payrefer'] != null && pay['payrefer'] != '') {
									$('#switch-state1').bootstrapSwitch('state', 1);
									$('#cost_need_change_money1').text(money);
									$("#cost_foreign_type").val(pay['payforeigntype']); //外货
									var index =document.getElementById("cost_foreign_type").selectedIndex;
									//初始化时，给numbbox设置小数点位数，为本国货币位数
							 			bus_price_common(1,itmvalue[parseInt(index)-1]);
								} else if(pay['payusedate'] != null && pay['payrefer'] != '') {
									$('#switch-state1').bootstrapSwitch('state', 1);
									$('#cost_need_change_money1').text(money);
									$("#cost_foreign_type").val(pay['payforeigntype']); //外货
									var index =document.getElementById("cost_foreign_type").selectedIndex;
									//初始化时，给numbbox设置小数点位数，为本国货币位数
							 			bus_price_common(1,itmvalue[parseInt(index)-1]);
								}*/
								//支付区域计算结果最后赋值
								$('.plancost_tax').text(formatNumber(pay['payvatamt'])); //增值税
								$('.plan_cost_foreign_amt').text(formatNumber(pay['payamt'])); //发注金额本国货币(发注原价）
								$('#pay_amt').text(formatNumber(pay['payrmb']));
							}
							//重新为外货下拉复制，不可删除，删掉代码会有逻辑问题
							$("#cost_foreign_type").val(pay['payforeigntype']); //外货
							//外发货币赋值
							if(data[$.getRequestDataName()]['outinfo'][0]['costcurecode']==""){
								setpayPersonMoneyCode(data["userInfo"]);
							}else{
								var  costmoney=$("#cost_foreign_type option[value = '"+data[$.getRequestDataName()]['outinfo'][0]['costforeigntype']+"']").text();
								$('#cost_foreign_money').text(costmoney); //右侧外发信息显示的外货
							}
							
							
							//给发票弹窗是否有值的判断位赋值
							$("#pd").val(1);
							if(data[$.getRequestDataName()]['Invoiceintrn'].length>0){
								//发票值
				/*				$("#invoice_date").datebox('setValue', data[$.getRequestDataName()]['Invoiceintrn'][0]['invoicedate'])
								$("#invoice_no").val(data[$.getRequestDataName()]['Invoiceintrn'][0]['invoiceno']);	
								$("#v_remark").val(data[$.getRequestDataName()]['Invoiceintrn'][0]['remark']);*/
								$("#invoice_date").text(data[$.getRequestDataName()]['Invoiceintrn'][0]['invoicedate'])
								$("#invoice_no").text(data[$.getRequestDataName()]['Invoiceintrn'][0]['invoiceno']);	
								$("#v_remark pre").text(data[$.getRequestDataName()]['Invoiceintrn'][0]['remark']);
							}
							  
							  $("#languageNameUpd").attr("name","");
							 //登录者更新者卡片逻辑
							 //支付作业者
							 if(pay["adddate"]!=null&&pay["adddate"]!=''){
								  $("#adduser").text(pay["addUserName"]);//支付登录者
								  $("#adddate").text(new Date(pay["adddate"]).format_extend('yyyy-MM-dd hh:mm:ss'));//外发登录时间	
							 	  $("#languageNameAdd").attr("name","paymentregistration_jobLogon");
							 	  $("#addcolor").css("color",pay["payaddusercolor"]);
							 }else{
							 	 $("#addhidden").addClass('hidden');
							 	 $("#updhidden").addClass('hidden')
							 }
							 if(pay["update"]!=null&&pay["update"]!=''){
								  $("#upduser").text(pay["upUserName"]);//支付更新者
								  $("#update").text(new Date(pay["update"]).format_extend('yyyy-MM-dd hh:mm:ss'));//外发更新时间
							 	  $("#languageNameUpd").attr("name","paymentregistration_jobUpdate");
							 	  $("#updcolor").css("color",pay["payupdusercolor"]);
							 }else{
							 	 $("#updhidden").addClass('hidden');
							 }
							 
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
							 
							 //当外发更新时间和登陆时间一致的时候，更新不显示
							if(pay["adddate"]==pay["update"]){
							   $("#updhidden").addClass("hidden");
							}
							//2019/2/21功能更改，支付更新画面，主界面的发票编辑功能，更改为发票显示功能。
							//追求最小代码改动量，故原发票编辑功能代码不变，页面编辑控件全部隐藏。追加显示项赋值代码
							var invoice_type = $('#invoice_type').find("option:selected").text();
							$('#invoice_type_show').text(invoice_type);
							var invoice_text = $('#invoice_text').find("option:selected").text();
							$('#invoice_text_show').text(invoice_text);
							//计上计， 并且 支付的登陆日期实在结账之后可以取消   如果   支付的登陆日期在结账之前不可以取消   消除按钮非活性
							 if(data[$.getRequestDataName()]['joblist'].length>=1){
								 if(data[$.getRequestDataName()].pay[0].payAdddate<data[$.getRequestDataName()].checkDate){
									 $("[name='calcel']").attr("disabled",true);
								 }
				             }
							
						}
					},1);
					//off 默认选中
					$('.pop_z').closest('.messager-body').next('.messager-button').find('.l-btn').eq(0).attr('id','p_update_off');
				}	
				else{
					//外发信息显示
					if(data[$.getRequestDataName()]['outinfo'].length>0){
						$("#cost_no").text(data[$.getRequestDataName()]['outinfo'][0]['costno']);
						$("#cost_name").text(data[$.getRequestDataName()]['outinfo'][0]['costname']);
						$("#costrate").text(floatObj.multiply(data[$.getRequestDataName()]['outinfo'][0]['costrate'],100));
						
						var  costmoneypointindex=$("#cost_foreign_type option[value = '"+data[$.getRequestDataName()]['outinfo'][0]['costforeigntype']+"']")[0].index;
						costpoint =itmvalue[costmoneypointindex-1];
						$("#costforeignamt").text(formatNumber(data[$.getRequestDataName()]['outinfo'][0]['costforeignamt'],costpoint));
						$("#costrmb").text(formatNumber(data[$.getRequestDataName()]['outinfo'][0]['costrmb']));
						$("#costvatamt").text(formatNumber(data[$.getRequestDataName()]['outinfo'][0]['costvatamt']));
						$("#outsoureamt").text(formatNumber(data[$.getRequestDataName()]['outinfo'][0]['outsoureamt']));
						$("#plandlvday").text(data[$.getRequestDataName()]['outinfo'][0]['plandlvday']);
						$("#orderdate").text(data[$.getRequestDataName()]['outinfo'][0]['orderdate']);
						$("#outsoureuser").text(data[$.getRequestDataName()]['outinfo'][0]['outsoureuser']);
						$("#costremark pre").html(data[$.getRequestDataName()]['outinfo'][0]['costremark']);
						$("#costupdate").text(data[$.getRequestDataName()]['outinfo'][0]['costupdate']);
						$("#costupdusercolor").css("color",data[$.getRequestDataName()]['outinfo'][0]['updusercolorv']);
						$("#costupdusername").text(data[$.getRequestDataName()]['outinfo'][0]['username']);
						$('.outsource').find('.activeT').find('input').each(function(index, element) {
						if(element.value != data[$.getRequestDataName()]['outinfo'][0]['costishave']) {
								$(element).parents('label').toggleClass('activeT');
								$(element).parents('label').siblings('label').toggleClass('activeT');
								$(element).parents('label').hide();
							}
						});
					}
					
					//凭证信息初始化
					var rooftrn = data[$.getRequestDataName()]['prooftrn'];
					var str = '';
					for(var i = 0; i < rooftrn.length; i++) {
						str += '<li class="s1">' +
							'<span value="'+i+'">' + rooftrn[i].PROOF_TITLE + '</span> ' +
							'<i class="iconfont icon-guanbi" style="font-size: 11px;color: white;"></i>' +
							'<input type="hidden" name="" id="" value="' + rooftrn[i].PROOF_URL + '" />' +
							'</li>';
					}
					$('.advance_url').empty();
					$('.advance_url').append($(str));
					//刪除憑證事件
					addAdvEvent();
					if(data[$.getRequestDataName()]['CLDIV'].length>0){
						//job信息区块，的得意先，与更新时间
						///sessionStorage.setItem("addflg", data[$.getRequestDataName()]['CLDIV'][0]['saleAddFlag']); //壳上登录状态为1：已经登录  2：未登录				
						$("#jobcd").text(data[$.getRequestDataName()]['CLDIV'][0]['jobcd']);
						$("#job_name").text(data[$.getRequestDataName()]['CLDIV'][0]['jobname']);
						$("#divnm").text(data[$.getRequestDataName()]['CLDIV'][0]['divnm']);
						$("#upddate").text(data[$.getRequestDataName()]['CLDIV'][0]['upddate']);
						$("#username").text(data[$.getRequestDataName()]['CLDIV'][0]['username']);
						if(data[$.getRequestDataName()]['CLDIV'][0]['updusercolorv']!=""){
							$("#jobupdusercolor").css("color",data[$.getRequestDataName()]['CLDIV'][0]['updusercolorv']);
						}
					
						//job信息
						var jobcd =data[$.getRequestDataName()]['CLDIV'][0]['jobcd'];
						var powerList = JSON.parse($.getNodeList());
						if(!isHavePower(powerList,[5,6,7,8])){
						showErrorHandler("SYS_VALIDATEPOWER_ERROR","Warning","Warning");
						  return;	
						}else{
						if(data[$.getRequestDataName()]['CLDIV'][0]['saleAddFlag']=='1'){
							window.job_cd=jobcd;
							$.layerShowDiv('job_detail','70%','80%',2,'../jczh/job_detail.shtml');	
						}else{
							window.job_cd=jobcd;
							$.layerShowDiv('job_detail','70%','80%',2,'../jczh/job_detail_nosale.html');	
						}
						$.layerShowDiv('icon-i','1000','600',2,'../common/jczh/cost_list.shtml',jobcd);	
						}
					}
					if(data[$.getRequestDataName()]['sumcost'].length>0){
						$("#sumamt").text(data[$.getRequestDataName()]['sumcost'][0]['sumamt'])
						$("#updatetime").text(data[$.getRequestDataName()]['sumcost'][0]['updatetime'])
						$("#costupdname").text(data[$.getRequestDataName()]['sumcost'][0]['costupdname'])	
						//判断成本信息感叹号，是否显示
					 	if(Number(data[$.getRequestDataName()]['sumcost'][0]['sumamt'])>0){
								$('.icon-i').removeClass("hidden")
						}
					}
					
					
					//标签数据重构
					SelectObj.setLableList = data[$.getRequestDataName()]['lable'];
					SelectObj.setChooseLableList = data[$.getRequestDataName()]['paylable'];
					labelToMySelect(data[$.getRequestDataName()]['lable']);
					setLableArea(data[$.getRequestDataName()]['paylable']);
					if(data[$.getRequestDataName()]['pay'].length>0){
						//支付信息显示
						var pay = data[$.getRequestDataName()]['pay'][0];
						$("#costname").text(data[$.getRequestDataName()]['pay'][0]['payname']);
						$('.outsource').find('.active').find('input').each(function(index, element) {
							if(element.value != data[$.getRequestDataName()]['pay'][0]['payishave']) {
								$(element).parents('label').toggleClass('active');
								$(element).parents('label').siblings('label').toggleClass('active');
							}
						});
						$("#saleCurCode").val(pay['paycurecode']!=""?Number(pay['paycurecode']):""); //换算code、、汇率
						$(".rate").text(floatObj.multiply(pay['payrate'],100));
						$("#cost_rate").text(floatObj.multiply(pay['payrate'],100));
						$("#input_no").text(pay['inputno']);
						$("#payee_cd").val(pay['payeecd']);
						$("#pay_dlyday").datebox('setValue', pay['paydlyday'])
						$("#pay_hope_date").datebox('setValue', pay['payhopedate']) //希望交付日
						$("#payremark").val(pay['payremark']);
						$("#cost_foreign_type").val(pay['payforeigntype']); //外货
						$("input#cost_foreign_amt").numberbox('setValue',pay['payforeignamt']);//输入金额
					
						$("#common-dayT").datebox('setValue', pay['payusedate']) //適用日
						$("#cost_refer").val(pay['payrefer']); //参照先
						$("#invoice_type").val(pay['invoicetype']);
					    $("#invoice_text").val(pay['invoicetext']);
					    $("#deductionvalue").text(pay['deductionflg']);
					    if(pay['deductionflg']=="1"){
						  $("#deduction").text(getGridLanguage('deductionflg',pay['deductionflg']));
						}else{
						  $("#deduction").text(getGridLanguage('deductionflg',pay['deductionflg']));
						}
					    
					    //判断是否有外货
					    var money =$('#cost_foreign_type option:selected').text();//选中的文本
						if(pay['paycurecode'] != ''||pay['payrefer'] != ''||pay['payusedate']!= '') {
							$('#switch-state1').bootstrapSwitch('state', 1);
							//外货符号初始化加载
							$('#cost_need_change_money1').text(money);
							//重新为外货下拉复制，不可删除，删掉代码会有逻辑问题
							$("#cost_foreign_type").val(pay['payforeigntype']); //外货
							var index =document.getElementById("cost_foreign_type").selectedIndex;
							//初始化时，给numbbox设置小数点位数，为本国货币位数
					 		bus_price_common(1,itmvalue[parseInt(index)-1]);
						}else{
							//初始化时，给numbbox设置小数点位数，为本国货币位数
				 			bus_price_common(1,pointNumber);
						}
						
						/*else if(pay['payrefer'] != null && pay['payrefer'] != '') {
							$('#switch-state1').bootstrapSwitch('state', 1);
							$('#cost_need_change_money1').text(money);
							$("#cost_foreign_type").val(pay['payforeigntype']); //外货
							var index =document.getElementById("cost_foreign_type").selectedIndex;
							//初始化时，给numbbox设置小数点位数，为本国货币位数
					 			bus_price_common(1,itmvalue[parseInt(index)-1]);
						} else if(pay['payusedate'] != null && pay['payrefer'] != '') {
							$('#switch-state1').bootstrapSwitch('state', 1);
							$('#cost_need_change_money1').text(money);
							$("#cost_foreign_type").val(pay['payforeigntype']); //外货
							var index =document.getElementById("cost_foreign_type").selectedIndex;
							//初始化时，给numbbox设置小数点位数，为本国货币位数
					 			bus_price_common(1,itmvalue[parseInt(index)-1]);
						}*/
						//支付区域计算结果最后赋值
						$('.plancost_tax').text(formatNumber(pay['payvatamt'])); //增值税
						$('.plan_cost_foreign_amt').text(formatNumber(pay['payamt'])); //发注金额本国货币(发注原价）
						$('#pay_amt').text(formatNumber(pay['payrmb']));
					}
					//重新为外货下拉复制，不可删除，删掉代码会有逻辑问题
					$("#cost_foreign_type").val(pay['payforeigntype']); //外货
					//外发货币赋值
					if(data[$.getRequestDataName()]['outinfo'][0]['costcurecode']==""){
						uInfo = data["userInfo"];
						setpayPersonMoneyCode(data["userInfo"]);
					}else{
						moneyUtin = data[$.getRequestDataName()]['outinfo'][0]['costforeigntype'];
						var  costmoney=$("#cost_foreign_type option[value = '"+data[$.getRequestDataName()]['outinfo'][0]['costforeigntype']+"']").text();
						$('#cost_foreign_money').text(costmoney); //右侧外发信息显示的外货
					}
					
					
					//给发票弹窗是否有值的判断位赋值
					$("#pd").val(1);
					if(data[$.getRequestDataName()]['Invoiceintrn'].length>0){
						//发票值
		/*				$("#invoice_date").datebox('setValue', data[$.getRequestDataName()]['Invoiceintrn'][0]['invoicedate'])
						$("#invoice_no").val(data[$.getRequestDataName()]['Invoiceintrn'][0]['invoiceno']);	
						$("#v_remark").val(data[$.getRequestDataName()]['Invoiceintrn'][0]['remark']);*/
						$("#invoice_date").text(data[$.getRequestDataName()]['Invoiceintrn'][0]['invoicedate'])
						$("#invoice_no").text(data[$.getRequestDataName()]['Invoiceintrn'][0]['invoiceno']);	
						$("#v_remark pre").text(data[$.getRequestDataName()]['Invoiceintrn'][0]['remark']);
					}
					  
					  $("#languageNameUpd").attr("name","");
					 //登录者更新者卡片逻辑
					 //支付作业者
					 if(pay["adddate"]!=null&&pay["adddate"]!=''){
						  $("#adduser").text(pay["addUserName"]);//支付登录者
						  $("#adddate").text(new Date(pay["adddate"]).format_extend('yyyy-MM-dd hh:mm:ss'));//外发登录时间	
					 	  $("#languageNameAdd").attr("name","paymentregistration_jobLogon");
					 	  $("#addcolor").css("color",pay["payaddusercolor"]);
					 }else{
					 	 $("#addhidden").addClass('hidden');
					 	 $("#updhidden").addClass('hidden')
					 }
					 if(pay["update"]!=null&&pay["update"]!=''){
						  $("#upduser").text(pay["upUserName"]);//支付更新者
						  $("#update").text(new Date(pay["update"]).format_extend('yyyy-MM-dd hh:mm:ss'));//外发更新时间
					 	  $("#languageNameUpd").attr("name","paymentregistration_jobUpdate");
					 	  $("#updcolor").css("color",pay["payupdusercolor"]);
					 }else{
					 	 $("#updhidden").addClass('hidden');
					 }
					 
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
					 
					 //当外发更新时间和登陆时间一致的时候，更新不显示
					if(pay["adddate"]==pay["update"]){
					   $("#updhidden").addClass("hidden");
					}
					//2019/2/21功能更改，支付更新画面，主界面的发票编辑功能，更改为发票显示功能。
					//追求最小代码改动量，故原发票编辑功能代码不变，页面编辑控件全部隐藏。追加显示项赋值代码
					var invoice_type = $('#invoice_type').find("option:selected").text();
					$('#invoice_type_show').text(invoice_type);
					var invoice_text = $('#invoice_text').find("option:selected").text();
					$('#invoice_text_show').text(invoice_text);
					//计上计， 并且 支付的登陆日期实在结账之后可以取消   如果   支付的登陆日期在结账之前不可以取消   消除按钮非活性
					 if(data[$.getRequestDataName()]['joblist'].length>=1){
						 if(data[$.getRequestDataName()].pay[0].payAdddate<data[$.getRequestDataName()].checkDate){
							 $("[name='calcel']").attr("disabled",true);
						 }
		             }
					
				}
				
				
				$('.outsource').find('.activeT').siblings('label').hide();
				
				
				
				textOverhidden();
				 //解析语言文件
		       part_language_change($("#languageNameAdd"));
				part_language_change($("#languageNameUpd"));
			} else {
				showErrorFunHandler(data[$.getRequestMetaName()].result, "ERROR", "ERROR");
			}
		},
		error: function(data) {
			showErrorHandler("CONNECTION_SERVER_ERROR", "ERROR", "ERROR");
		}
	});
}

//发票弹窗初始化
function initInvoice(payee_cd, pay_dlyday, iframeWin) {
	var path = $.getAjaxPath() + "initInvoice";
	var pars = {
		"payee_cd": payee_cd,
		"plan_dlvday": pay_dlyday
	};
	$.ajax({
		url: path,
		data: JSON.stringify(pars),
		headers: {
			"requestID": $.getRequestID(),
			"requestName": $.getRequestNameByJcZH()
		},
		success: function(data) {
			if(data[$.getRequestMetaName()].result == $.getRequestStatusName()) {
				$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
				var payee = iframeWin.$('#invoice_type');
				var invoice_text = iframeWin.$('#invoice_text');
				var list = data[$.getRequestDataName()];
				//过了发注不明，立替不明
					for(var i=0;i<list['invoice_type'].length;i++){
							if(list['invoice_type'][i]['itemcd']=="902"){
								 list['invoice_type'].splice(i, 1);
								for(var i=0;i<list['invoice_type'].length;i++){
								 	if(list['invoice_type'][i]['itemcd']=="901"){
									 list['invoice_type'].splice(i, 1);	
									}
								 }
							}
							
						}
					for(var k=0;k<list['invoice_text'].length;k++){
							if(list['invoice_text'][k]['itemcd']=="902"){
							 list['invoice_text'].splice(k, 1);	
							 for(var k=0;k<list['invoice_text'].length;k++){
							 	if(list['invoice_text'][k]['itemcd']=="901"){
								 list['invoice_text'].splice(k, 1);	
								}
							 }
							}
							
					}
				var voiceDate = SelectObj.selectData;
				SelectObj.selectData = voiceDate;
				SelectObj.setSelectID = ['cost_foreign_type','invoice_type','invoice_text'];
				SelectObj.setStringFlg='';
				SelectObj.setIfram = iframeWin;
				SelectObj.setSelectOfLog();
				var pd = $('#pd').val();
				if(pd == 1) {
					var invoice_date = $('#invoice_date').text();
					var invoice_type = $('#invoice_type').val();
					var invoice_text = $('#invoice_text').val();
					
					var cost_rate = $('#cost_rate').text();
					iframeWin.$('#invoice_date').datebox('setValue', invoice_date);
					iframeWin.$('#invoice_no').val($('#invoice_no').text());
					iframeWin.$('#invoice_type').val(invoice_type);
					iframeWin.$('#invoice_text').val(invoice_text);
					iframeWin.$('#vat_rate').val(cost_rate);
					iframeWin.$('#remark').val($('#v_remark pre').text());
					var deduction = $('#deduction').text();
					var deductionvalue = $('#deductionvalue').text();
					if(deduction!=''&&deduction!='[deduction]'){
					iframeWin.$('div.hidden').removeClass("hidden");
					iframeWin.$('#deduction').text(deduction);
					iframeWin.$('#deductionvalue').text(deductionvalue);
					}
				}

			} else {
				showErrorFunHandler(data[$.getRequestMetaName()].result, "ERROR", "ERROR");
			}
		},
		error: function(data) {
			showErrorHandler("CONNECTION_SERVER_ERROR", "ERROR", "ERROR");
		}
	});
}

/*function selectTax(pay_dlyday, payee_cd, invoice_text, invoice_type, iframeWin) {
	var path = $.getAjaxPath() + "selectTax";
	var pars = {
		"payee_cd": payee_cd,
		"plan_dlvday": pay_dlyday,
		"invoice_text": invoice_text,
		"invoice_type": invoice_type
	};
	$.ajax({
		url: path,
		data: JSON.stringify(pars),
		headers: {
			"requestID": $.getRequestID(),
			"requestName": $.getRequestNameByJcZH()
		},
		success: function(data) {
			if(data[$.getRequestMetaName()].result == $.getRequestStatusName()) {
				$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
				if(data[$.getRequestDataName()]==null||data[$.getRequestDataName()]==''){
	           	        //没有税率，清空数据，不可点击显示
	           	        showErrorHandler('NOTFINDE_TAX','ERROR','ERROR');
	           	        iframeWin.$("#invoicebtn").attr("disabled",true);
		       	        iframeWin.$('#invoice_date').datebox('setValue',null);
				   	 	iframeWin.$('#invoice_no')[0].value='';
				   	 	iframeWin.$('#invoice_type').val('');
				   	 	iframeWin.$('#invoice_text').val('');
				   	 	iframeWin.$('#vat_rate').val('');
				   	 	iframeWin.$('#remark')[0].value='';
				   	 	iframeWin.$('#deduction').addClass("hidden");
	           	    }else{
						iframeWin.$("#vat_rate").val(data[$.getRequestDataName()]["vatrate"]);
						var deduction = data[$.getRequestDataName()]["deduction"];
						if(deduction == '0') {
							iframeWin.$("#deduction").text('不扣除');
							iframeWin.$('div.hidden').removeClass("hidden");
						} else {
							iframeWin.$("#deduction").text('扣除');
							iframeWin.$('div.hidden').removeClass("hidden");
						}
				    }
			} else {
				showErrorFunHandler(data[$.getRequestMetaName()].result, "ERROR", "ERROR");
			}
		},
		error: function(data) {
			showErrorHandler("CONNECTION_SERVER_ERROR", "ERROR", "ERROR");
		}
	});
}*/

function updatePayInfo() {
	if(!validataRequired()||!Judgment_time('pay_dlyday','pay_hope_date','PAY_DLYDAYMORE'))
	{
		return ;
	}
	/*if(!validataRequired())
	{
		return ;
	}*/
	//发票必填项目
	var cost_rate=$("#cost_rate").text();
	if(cost_rate==undefined||cost_rate==null||cost_rate==''){
		showErrorHandler("NOTFINDE_TAX","info","info");
		return;
	}
	if(orderInvoiceFlg == 1 && $("#invoice_no").text() == "")
	{
		showErrorHandler("NOT_EMPTY","info","info");
		$('.mes-edit').addClass('color_red_text');
		return;
	}else {
		$('.mes-edit').removeClass('color_red_text');
	}
	var path = $.getAjaxPath() + "updatePayInfo";
	var pars = $.getInputVal();
	var lableList = getLable();
	//凭证获取
	var urllist = $('.advance_url li');
	var rooftrn=[];
	for(var i = 0; i < urllist.length; i++) {
		var urlvalue = urllist.eq(i).find('input[type="hidden"]').val();
		var urltitle = urllist.eq(i).text();
		var list_rooftrn = {
			proof_url: urlvalue,
			proof_title: urltitle
		}
		rooftrn.push(list_rooftrn);
	}
	pars['list_rooftrn']=rooftrn;
	pars['cost_no'] =$("#cost_no").text();
	pars['input_no'] =$("#input_no").text();
	pars['pay_dlyday'] = $("#pay_dlyday").val(); //纳品日
	pars['pay_hope_date'] = $("#pay_hope_date").val(); //预定交付日
	pars['payremark'] = $("#payremark").val(); //备考
	pars['pay_rate'] = floatObj.divide($(".rate").text(),100); //税率
	pars['pay_foreign_amt'] = $("input#cost_foreign_amt").numberbox('getValue');
	pars['pay_ishave'] = $(".cost_rate_click .active input[name='saleIsHave']").val();
	pars['pay_foreign_type'] = $("#cost_foreign_type").val(); //外货种类
	pars['pay_rmb'] = recoveryNumber($('#pay_amt').text()); //支付金额
	pars['pay_vat_amt'] = recoveryNumber($('.plancost_tax').text()); //增值税
	pars['pay_amt'] = recoveryNumber($("span#cost_foreign_amt").text());//发注原价
	pars['lableList'] = lableList; //标签
	pars['invoice_date'] = $("#invoice_date").text();
	pars['invoice_no'] = $("#invoice_no").text();
	pars['invoice_type'] = $("#invoice_type").val();
	pars['invoice_text'] = $("#invoice_text").val();
	pars['remark'] = $("#v_remark pre").text();
	pars['job_cd'] = $("#jobcd").text();
	var deduction_flg= $("#deduction").text();
/*	if(deduction_flg=="扣除"){
		pars['deduction_flg']="1";
	}else{
		pars['deduction_flg']="0";
	}*/
	if($("#deductionvalue").text()!=""){
	pars['deduction_flg']=$("#deductionvalue").text();	
	}
	pars['vat_change_flg'] =changeFlg;

	if($('#switch-state1').bootstrapSwitch('state')) {
 			pars['pay_cure_code'] = $("#saleCurCode").val(); //换算code、、汇率
			pars['pay_use_date'] = $("#common-dayT").val(); //適用日
			pars['pay_refer'] = $("#cost_refer").val(); //参照先
  }
	pars['lockflg'] = lockflg;
	$.ajax({
		url: path,
		data: JSON.stringify(pars),
		headers: {
			"requestID": $.getRequestID(),
			"requestName": $.getRequestNameByJcZH()
		},
		success: function(data) {
			if(data[$.getRequestMetaName()].result == $.getRequestStatusName()) {
				$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
				 if(data[$.getRequestDataName()]['messge']=='SYS_VALIDATEPOWER_ERROR'){
				 	 showLockInfoMsgHandler("STATUS_VALIDATEPOWER_ERROR","jczh/cost_list.html?view=6");
					 return;
				 }else if(data[$.getRequestDataName()]['messge']=='PAY_DLYDAYMORE'){
				 	 showErrorHandler("PAY_DLYDAYMORE");
					 return;
				 }else{
				//var pdfout =$("#PayConfirmOutPDF").val();
				var input_no= data[$.getRequestDataName()]['input_no'];
				var jobcd =urlPars.parm("jobcd");
           		if(data[$.getRequestDataName()]['num']==1){
	           		//插入成功后执行导出pdf
		           	if ($('#PayConfirmOutPDF').prop("checked")) {
   		             OutPutPdfHandler(jobcd,'',input_no,'payCreate',"","","",1);
   		            }else{
		           		url = "jczh/cost_list.html?view=6";//此处拼接内容
				 	   	window.location.href  = $.getJumpPath()+url;
		           		}
           		}else{
           			showErrorHandler("EXECUTE_FAIL","info","info");	//失败 
           		}
			}	
			} else {
				showErrorFunHandler(data[$.getRequestMetaName()].result, "ERROR", "ERROR");
			}
		},
		error: function(data) {
			showErrorHandler("CONNECTION_SERVER_ERROR", "ERROR", "ERROR");
		}
	});
}

function delPayInfo() {
	var msg = showConfirmMsgHandler('SURE_DELETE');
	var confirmTitle = $.getConfirmMsgTitle();
$.messager.confirm(confirmTitle,msg, function(r){
		if(r)
		{
		var inputno = $("#input_no").text()
		var path = $.getAjaxPath() + "deletePayInfo";
		var cost_no = $("#cost_no").text();
		var jobcd = urlPars.parm("jobcd");
		var pars = {
			"input_no": inputno,
			"cost_no": cost_no,
			"job_cd": jobcd,
			"lockflg":lockflg
		};
		$.ajax({
			url: path,
			data: JSON.stringify(pars),
			headers: {
				"requestID": $.getRequestID(),
				"requestName": $.getRequestNameByJcZH()
			},
			success: function(data) {
				if(data[$.getRequestMetaName()].result == $.getRequestStatusName()) {
					$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
					//当返回3的时候，代表不是正常状态的数据，不可删除/2代表计上济不可删除
					if(data[$.getRequestDataName()] == 2){
						showErrorHandler("ILLEGAL_OPERATION_TOLOGIN", "info", "info");
					}else if(data[$.getRequestDataName()] == 3){
						 showLockInfoMsgHandler("STATUS_VALIDATEPOWER_ERROR","jczh/cost_list.html?view=6");
					}else {
						url = "jczh/cost_list.html?view=6"; //此处拼接内容
						window.location.href  = $.getJumpPath()+url;
					}
				} else {
					showErrorFunHandler(data[$.getRequestMetaName()].result, "ERROR", "ERROR");
				}
			},
			error: function(data) {
				showErrorHandler("CONNECTION_SERVER_ERROR", "ERROR", "ERROR");
			}
		});
	}
		});
}


function PayConfirmOutPDF(input_no){
	var input_no = input_no;
	var fName = "jobLand";
	var langT = localStorage.getItem('language');
	var cd = {
		input_no:input_no,
		fileName:fName,
		langTyp:langT
	}
	$.ajax({
		url: $.getPdfAjaxPath() + "PayConfirmOutPDF",
		data: JSON.stringify(cd),
		headers: {
			"requestID": $.getRequestID(),
			"requestName": $.getRequestNameByJcZH()
		},
		success: function(data) {
//			console.log(data);
			if(data[$.getRequestMetaName()].result == $.getRequestStatusName()) {
				$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
				if(data[$.getRequestDataName()] != null) {
					downLoadPdf(data[$.getRequestDataName()],fName,"pdf");
				} else {

					showErrorHandler("JOB_LAND_ERROR", "ERROR", "ERROR");

				}
			} else {

				showErrorFunHandler(data[$.getRequestMetaName()].result, "ERROR", "ERROR");
			}
		},
		error: function(msg) {

			window.location.href = $.getJumpPath();
		}
	});
}
