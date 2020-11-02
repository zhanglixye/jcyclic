var aidcd=new Array();
var change_utin=new Array();
var itmvalue=new Array();
var pointNumber ='';
var foreignFormatFlg='';
var saleVatFormatFlg='';
var lockflg;
var moneyUtin="";
var uInfo={};
var orderInvoiceFlg;

$(function(){
	$("#payee_cd").val(6);//此值为上个页面带出来的，现在写死
	//初始化数据
	initPayedit();
	//初始化页面所有监听时间
	paymentInit();
	//初始化toolTip语言切换不需要传参数
	toolTipLanguage();
	//lible权限验证
	 lableShowByPower();
	 $("#language").change(function() {
			
			if(moneyUtin != "")
			{
				var  costmoney=getForgMoneyUnit(SelectObj.selectData.Foreign,moneyUtin);
				$('#cost_foreign_money').text(costmoney); //右侧外发信息显示的外货
			}else{
				setpayPersonMoneyCode(uInfo);
			}
			
	    });
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
	 	var pay_dlyday = $("#pay_dlyday").val();
	 	//验证承包方很交付日不为空
	 	if(payee_cd!=null&&payee_cd!=''&&pay_dlyday!=null&&pay_dlyday!=''){
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
			   	initInvoice(payee_cd,pay_dlyday,iframeWin);
			   	//下拉框变更是监听,查询税率,在payment_common.js
			     commonInvoiceChange(pay_dlyday,payee_cd,invoice_text,invoice_type,iframeWin);
                //是否有小红星
                if (orderInvoiceFlg == 1){
                    iframeWin.$('.invoice_edit_red_point').removeClass('hidden');
                }else {
                    iframeWin.$('.invoice_edit_red_point').addClass('hidden');
                }
			   	//显示按钮监听，把弹窗中的数据付到父页面的中
			   	 iframeWin.$('#invoicebtn').click(function(){
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
			   	closeBtn.on('click',function(){
					layer.close(index);
				})
			}
		});
			
	 	}else{
	 	 showErrorHandler('NOT_CHOOSE_PLYDATE','info','info');	
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
//初始化加载
function initPayedit(){
	var jobcd =urlPars.parm("jobcd");
	var costno =urlPars.parm("costno");
	var path = $.getAjaxPath()+"initPayInfo";	
	var pars = {"job_cd":jobcd,"cost_no":costno}; 
	$.ajax({
		url:path,
		data:JSON.stringify(pars),
		headers: {"requestID":$.getRequestID(),"requestName":$.getRequestNameByJcZH()},
		success:function(data){
			 //页面跳到头部
	 		$('aside#content').scrollTop(0);
           if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
           		$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
           		if(data[$.getRequestDataName()]['validatePower']==='0'){
				 var url = "jczh/cost_list.html?view=6";//此处拼接内容
				 showInfoMsgHandler("SYS_VALIDATEPOWER_ERROR","info","info",url);
				 return;
				}
           		if(data[$.getRequestDataName()]['messge']=='DATA_IS_NOT_EXIST'){
				 var url = "jczh/cost_list.html?view=6";//此处拼接内容
				 showInfoMsgHandler("DATA_IS_NOT_EXIST","info","info",url);
				 return;
				}
           		orderInvoiceFlg = data[$.getRequestDataName()]['orderNoIsMustFlg'];
           		//给pdf的checkbox设置默认值
			  	if(data[$.getRequestDataName()]['pdfflg']==1){
			  	$("#PayConfirmOutPDF").prop("checked", true);	
			  	};
			  	//加载发注跟新锁
			  	lockflg =data[$.getRequestDataName()]['outinfo'][0]['lockflg']
           		 //加载本国货币种类
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
				for(var i=0;i<data[$.getRequestDataName()]['Foreign'].length;i++){
				aidcd.splice(i,0,data[$.getRequestDataName()]['Foreign'][i]['aidcd']);	
				change_utin.splice(i,0,data[$.getRequestDataName()]['Foreign'][i]['changeutin']);
				itmvalue.splice(i,0,data[$.getRequestDataName()]['Foreign'][i]['itmvalue']);
				}
				//job信息区块，的得意先，与更新时间
				if(data[$.getRequestDataName()]['CLDIV'].length>0){
					//sessionStorage.setItem("addflg",data[$.getRequestDataName()]['CLDIV'][0]['saleAddFlag']);//壳上登录状态为1：已经登录  2：未登录
					$("#job_name").text(data[$.getRequestDataName()]['CLDIV'][0]['jobname']);
					$("#jobcd").text(data[$.getRequestDataName()]['CLDIV'][0]['jobcd']);
					$("#divnm").text(data[$.getRequestDataName()]['CLDIV'][0]['divnm']);
					$("#upddate").text(data[$.getRequestDataName()]['CLDIV'][0]['upddate']);
					$("#username").text(data[$.getRequestDataName()]['CLDIV'][0]['username']);
					if(data[$.getRequestDataName()]['CLDIV'][0]['updusercolorv']!=""){
						$("#jobupdusercolor").css("color",data[$.getRequestDataName()]['CLDIV'][0]['updusercolorv']);
					}
					
					//job信息
					var jobcd =data[$.getRequestDataName()]['CLDIV'][0]['jobcd'];
					if(data[$.getRequestDataName()]['CLDIV'][0]['saleAddFlag']=='1'){
						window.job_cd=jobcd;
						$.layerShowDiv('job_detail','70%','80%',2,'../jczh/job_detail.shtml');	
					}else{
						window.job_cd=jobcd;
						$.layerShowDiv('job_detail','70%','80%',2,'../jczh/job_detail_nosale.html');	
					}
					$.layerShowDiv('icon-i','1000','600',2,'../common/jczh/cost_list.shtml',jobcd);	
				}
				
				if(data[$.getRequestDataName()]['sumcost'].length>0){
					sessionStorage.setItem("sumamt",data[$.getRequestDataName()]['sumcost'][0]['sumamt']);//成本合计
					$("#sumamt").text(data[$.getRequestDataName()]['sumcost'][0]['sumamt']);
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
				if(data[$.getRequestDataName()]['outinfo'].length>0){
								
						$("#payee_cd").val(data[$.getRequestDataName()]['outinfo'][0]['payeecd']);
						//外发信息显示
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
						$('.outsource').find('.activeT').find('input').each(function(index,element){
						if(element.value!=data[$.getRequestDataName()]['outinfo'][0]['costishave']){
							$(element).parents('label').toggleClass('activeT');
							$(element).parents('label').siblings('label').toggleClass('activeT');
						}
					});
						//税入税拔
						$('.outsource').find('.active').find('input').each(function(index, element) {
							if(element.value != data[$.getRequestDataName()]['outinfo'][0]['costishave']) {
								$(element).parents('label').toggleClass('active');
								$(element).parents('label').siblings('label').toggleClass('active');
							}
						});
						
						
						//支付录取区域初始赋值
						$("#saleCurCode").val(data[$.getRequestDataName()]['outinfo'][0]['costcurecode']!=""?Number(data[$.getRequestDataName()]['outinfo'][0]['costcurecode']):"");//换算code、、汇率
						$("#cost_rate").text(floatObj.multiply(data[$.getRequestDataName()]['outinfo'][0]['costrate'],100));//税率
						$("#cost_foreign_type").val(data[$.getRequestDataName()]['outinfo'][0]['costforeigntype']); //外货
						$("input#cost_foreign_amt").numberbox('setValue',data[$.getRequestDataName()]['outinfo'][0]['costforeignamt']);//入力金额
						$("#common-dayT").datebox('setValue',data[$.getRequestDataName()]['outinfo'][0]['costusedate']);//適用日
						$("#cost_refer").val(data[$.getRequestDataName()]['outinfo'][0]['costrefer']);//参照先
					
						//判断是否有外货
					    var money =$('#cost_foreign_type option:selected').text();//选中的文本
						if( data[$.getRequestDataName()]['outinfo'][0]['costcurecode'] != ''|| data[$.getRequestDataName()]['outinfo'][0]['costusedate'] != ''||data[$.getRequestDataName()]['outinfo'][0]['costrefer'] != '') {
							//外货符号初始化加载
							$('#switch-state1').bootstrapSwitch('state', 1);
						   $('#cost_need_change_money1').text(money);
						   //重新为外货下拉复制，不可删除，删掉代码会有逻辑问题
							$("#cost_foreign_type").val(data[$.getRequestDataName()]['outinfo'][0]['costforeigntype']); //外货
							var index =document.getElementById("cost_foreign_type").selectedIndex;
							//初始化时，给numbbox设置小数点位数，为本国货币位数
					 		bus_price_common(1,itmvalue[parseInt(index)-1]);
							moneyUtin = data[$.getRequestDataName()]['outinfo'][0]['costforeigntype'];
						}else{
							uInfo = data["userInfo"];
							//初始化时，给numbbox设置小数点位数，为本国货币位数
				 			bus_price_common(1,pointNumber);
						} /*else if(data[$.getRequestDataName()]['outinfo'][0]['costusedate'] != null && data[$.getRequestDataName()]['outinfo'][0]['costusedate'] != '') {
							$('#switch-state1').bootstrapSwitch('state', 1);
							$('#cost_need_change_money1').text(money);
						} else if(data[$.getRequestDataName()]['outinfo'][0]['costrefer'] != null && data[$.getRequestDataName()]['outinfo'][0]['costrefer'] != '') {
							$('#switch-state1').bootstrapSwitch('state', 1);
							$('#cost_need_change_money1').text(money);
						}*/
					  if(data[$.getRequestDataName()]['outinfo'][0]['costcurecode']==""){
					  		setpayPersonMoneyCode(data["userInfo"]);
					  }else{
					  	$('#cost_foreign_money').text($('#cost_foreign_type option:selected').text()); //右侧外发信息显示的外货
					  }
						
					    
					    $("span#cost_foreign_amt").text(formatNumber(data[$.getRequestDataName()]['outinfo'][0]['costrmb']));//发注原件
						$('.plancost_tax').text(formatNumber(data[$.getRequestDataName()]['outinfo'][0]['costvatamt'])); //增值税
						$('#pay_amt').text(formatNumber(data[$.getRequestDataName()]['outinfo'][0]['outsoureamt'])); //支付金额
					    //外发登录的发票内容，和发票种类
						$("#invoice_type").val(data[$.getRequestDataName()]['outinfo'][0]['invoicetype']);
					    $("#invoice_text").val(data[$.getRequestDataName()]['outinfo'][0]['invoicetext']);
					  /*  $("#invoice_text").val(data[$.getRequestDataName()]['outinfo'][0]['costrate']);*/
					    $('.rate').text(floatObj.multiply(data[$.getRequestDataName()]['outinfo'][0]['costrate'],100));//税率
					    var deduction=data[$.getRequestDataName()]['outinfo'][0]['deductionflg'];
					      $("#deductionvalue").text(deduction);
					     if(deduction=='0'){
			           	    	$("#deduction").text(getGridLanguage('deductionflg',deduction));
			           	    	$("#deduction").removeClass("hidden");
			           	    }else{
			           	    	$("#deduction").text(getGridLanguage('deductionflg',deduction));	
			           	    	$("#deduction").removeClass("hidden");
			           	}	
					}
				$('.outsource').find('.activeT').siblings('label').hide();
				
				//给发票弹窗是否有值的判断位赋值
				$("#pd").val(1);
				if(data[$.getRequestDataName()]['Invoiceintrn'].length>0){
					//发票值
					$("#invoice_date").text(data[$.getRequestDataName()]['Invoiceintrn'][0]['invoicedate'])
					$("#invoice_no").text(data[$.getRequestDataName()]['Invoiceintrn'][0]['invoiceno']);	
					$("#v_remark pre").text(data[$.getRequestDataName()]['Invoiceintrn'][0]['remark']);
				}
				//2019/2/21功能更改，支付更新画面，主界面的发票编辑功能，更改为发票显示功能。
				//追求最小代码改动量，故原发票编辑功能代码不变，页面编辑控件全部隐藏。追加显示项赋值代码
				var invoice_type = $('#invoice_type').find("option:selected").text();
				$('#invoice_type_show').text(invoice_type);
				var invoice_text = $('#invoice_text').find("option:selected").text();
				$('#invoice_text_show').text(invoice_text);
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

//发票弹窗初始化
function initInvoice(payee_cd,pay_dlyday,iframeWin){
	var path = $.getAjaxPath()+"initInvoice";	
	var pars = {"payee_cd":payee_cd,"plan_dlvday":pay_dlyday}; 
	$.ajax({
		url:path,
		data:JSON.stringify(pars),
		headers: {"requestID":$.getRequestID(),"requestName":$.getRequestNameByJcZH()},
		success:function(data){
           if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
//           	     console.log(iframeWin);
	           	    var payee = iframeWin.$('#invoice_type');
	           	    var invoice_text = iframeWin.$('#invoice_text');
					$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
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
					voiceDate['invoice_type']=list['invoice_type'];
					voiceDate['invoice_text']=list['invoice_text'];
					SelectObj.selectData = voiceDate;
					SelectObj.setSelectID = ['cost_foreign_type','invoice_type','invoice_text'];
					SelectObj.setStringFlg='';
					SelectObj.setIfram = iframeWin;
					SelectObj.setSelectOfLog();
			   		var invoice_date =$('#invoice_date').text();
			   		var invoice_type =$('#invoice_type').val();
			   		var invoice_text =$('#invoice_text').val();
			   		var deduction=$('#deduction').text();
			   		var deductionvalue=$('#deductionvalue').text();
			   		var cost_rate =$('#cost_rate').text();
			   	    iframeWin.$('#invoice_date').datebox('setValue',invoice_date);
			   	 	iframeWin.$('#invoice_no').val($('#invoice_no').text());
			   	 	iframeWin.$('#invoice_type').val(invoice_type);
			   	 	iframeWin.$('#invoice_text').val(invoice_text);
			   	 	iframeWin.$('#vat_rate').val(cost_rate);
			   	 	iframeWin.$('#remark').val($('#v_remark pre').text());
					if(deduction!=''&&deduction!='[deduction]'){
					iframeWin.$('div.hidden').removeClass("hidden");
					iframeWin.$('#deduction').text(deduction);
					iframeWin.$('#deductionvalue').text(deductionvalue);
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




function insertPayInfo(){
	
	if(!validataRequired()||!Judgment_time('pay_dlyday','pay_hope_date','PAY_DLYDAYMORE'))
	{
		return ;
	}
	//验证发票必填项目
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
	var path = $.getAjaxPath()+"insertPayInfo";	
	var pars = $.getInputVal();
	var lableList = getLable();
	var jobcd =urlPars.parm("jobcd");
	var costno =urlPars.parm("costno");
	//凭证获取
	var urllist=$('.advance_url li');
	var rooftrn=[];
	for(var i=0;i<urllist.length;i++){
		var urlvalue = urllist.eq(i).find('input[type="hidden"]').val();
		var urltitle = urllist.eq(i).text();
		var list_rooftrn={
			proof_url:urlvalue,
			proof_title:urltitle
		}
		rooftrn.push(list_rooftrn);
	}
	pars['list_rooftrn'] = rooftrn;
	pars['pay_dlyday'] = $("#pay_dlyday").val();//纳品日
	pars['pay_hope_date'] = $("#pay_hope_date").val();//预定交付日
	pars['payremark'] = $("#payremark").val();//备考
	pars['pay_rate'] =floatObj.divide($(".rate").text(),100);//税率
	pars['cost_foreign_type'] = $("#cost_foreign_type").val();
	pars['pay_ishave'] =  $(".cost_rate_click .active input[name='saleIsHave']").val();
	pars['pay_foreign_type'] = $("#cost_foreign_type").val();//外货种类
	pars['pay_foreign_amt'] = $("input#cost_foreign_amt").val();
	pars['pay_rmb'] = recoveryNumber($('#pay_amt').text()); //支付金额
	pars['pay_vat_amt'] = recoveryNumber($('.plancost_tax').text()); //增值税
	pars['pay_amt'] = recoveryNumber($("span#cost_foreign_amt").text());//发注原件，原价金额
	pars['lableList'] =lableList;//标签
	pars['invoice_date'] = $("#invoice_date").text();
	pars['invoice_no'] = $("#invoice_no").text();
	pars['invoice_type'] = $("#invoice_type").val();
	pars['invoice_text'] = $("#invoice_text").val();
	pars['remark'] = $("#v_remark pre").text();
	var deduction_flg= $("#deduction").text();
/*	if(deduction_flg=="扣除"){
		pars['deduction_flg']="1";
	}else{
		pars['deduction_flg']="0";
	}*/
	if($("#deductionvalue").text()!=""){
	pars['deduction_flg']=$("#deductionvalue").text();	
	}
	pars['cost_no'] =costno;
	pars['job_cd'] =jobcd;
	pars['vat_change_flg'] =changeFlg;
	if($('#switch-state1').bootstrapSwitch('state')) {
 			pars['pay_cure_code'] = $("#saleCurCode").val(); //换算code、、汇率
			pars['pay_use_date'] = $("#common-dayT").val(); //適用日
			pars['pay_refer'] = $("#cost_refer").val(); //参照先
    }
	pars['lockflg'] =lockflg;
	$.ajax({
		url:path,
		data:JSON.stringify(pars),
		headers: {"requestID":$.getRequestID(),"requestName":$.getRequestNameByJcZH()},
		success:function(data){
           if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
             	$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
             	var pdfout =$("#PayConfirmOutPDF").val();
             	//后台验证数据状态
				 if(data[$.getRequestDataName()]['messge']=='SYS_VALIDATEPOWER_ERROR'){
				 	 showLockInfoMsgHandler("STATUS_VALIDATEPOWER_ERROR","jczh/cost_list.html?view=6");
					 return;
				 }else if(data[$.getRequestDataName()]['messge']=='PAY_DLYDAYMORE'){
				 	 showErrorHandler("DATE_RANGE_ERROR");
					 return;
				 }
				 else{
					 	if(data[$.getRequestDataName()]['num']==1){
			           		//插入成功后执行导出pdf
			           		var jobcd =urlPars.parm("jobcd");
				           	if ($('#PayConfirmOutPDF').prop("checked")) {

					 	     OutPutPdfHandler(jobcd,'',data[$.getRequestDataName()]['input_no'],'payCreate',"","","",1);
		           				}else{
		           				url = "jczh/cost_list.html?view=6";//此处拼接内容
				 	   			window.location.href  = $.getJumpPath()+url;
		           				}
				           	}else{
				           		
		           			showErrorHandler("EXECUTE_FAIL","info","info");	//失败 
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
function cleanPay(){
		$("#pay_dlyday").datebox('setValue', '')
		$("#pay_hope_date").datebox('setValue', '') //希望交付日
		$("#payremark").val('');
		$("#cost_foreign_type").val(''); //外货
		$('.plancost_tax').text(''); //增值税
		$('.plan_cost_foreign_amt').text(''); //发注金额本国货币(发注原价）
		$('#pay_amt').text('');
		$("input#cost_foreign_amt").numberbox('setValue', '') //输入金额
		$("#saleCurCode").val(''); //换算code、、汇率
		$("#common-dayT").datebox('setValue', '') //適用日
		$("#cost_refer").val(''); //参照先
		$("#cost_rate").text('');
		$("#invoice_type").val('');
	    $("#invoice_text").val('');
	    $("#invoice_date").datebox('setValue', '')
		$("#invoice_no").val('');	
		$("#v_remark pre").val('');

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
