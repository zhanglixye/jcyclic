var sumcost="";
var foreign={};
var pay={};
var costList={};
var jobinfo={};
var saleInfo={};
var payStatus={};
var costdeductionflg ='';
var paydeductionflg ='';
var payno='';
var input_no='';
var trantrnlist={};
var lenfifo={};
var jobdetailflag='';
var jobuserlist={};
var inputLt="";
var inputZt="";
var inputZf="";

$(document).ready(function() {
	function cont() {
		$("#cont_fir").show();
		$('.collapsea').collapse();
	}

	function conta() {
		$("#cont_sec").show();
		$('.collapseb').collapse();
	}

	function cont_thir() {
		$("#cont_thir_info").show();
		$('.collapsez').collapse();
	}
	function init() {
		$('#myTab li:eq(0) a').tab('show');
		$('#myTab2 li:eq(0) a').tab('show');
		$('a[href="#first"][data-toggle="tab"]').on('shown.bs.tab', function(e) {
			selCostList();
		})
		//备考切换
		$('a[href="#ios"][data-toggle="tab"],a[href="#and"][data-toggle="tab"],a[href="#iosT"][data-toggle="tab"],a[href="#finish"][data-toggle="tab"]').on('shown.bs.tab', function(e) {
			if($(e.target).attr('href') == '#iosT') {
				var job_text = $(e.target).closest('#myTab2').siblings('#iosT').find('.job_text');
				var rotate = $(e.target).closest('#myTab2').siblings('#iosT').find('.rotate-90');
			} else if($(e.target).attr('href') == '#and') {
				var job_text = $(e.target).closest('#myTab2').siblings('#and').find('.job_text');
				var rotate = $(e.target).closest('#myTab2').siblings('#and').find('.rotate-90');
			} else if($(e.target).attr('href') == '#finish') {
				var job_text = $(e.target).closest('#myTab2').siblings('#finish').find('.job_text');
				var rotate = $(e.target).closest('#myTab2').siblings('#finish').find('.rotate-90');
			} else {
				var job_text = $(e.target).closest('#myTab').siblings('#ios').find('.job_text');
				var rotate = $(e.target).closest('#myTab').siblings('#ios').find('.rotate-90');
			}
			var hei = job_text.height();
			if(hei > 60) {
				job_text.addClass('over-anim');
				rotate.removeClass('hidden');
			}
		})
		var jump = document.getElementById('jump');
		jump.onclick = function() {
			var jump_to = document.getElementById('jumpTo');
			jump_to.scrollIntoView();
		}
		var jumpJob = document.getElementById('jumpJob');
		jumpJob.onclick = function() {
			var jump_to = document.getElementById('jumpToJob');
			jump_to.scrollIntoView();
		}
		
	}
	$(".second_img").click(function() {
		$(".second_img").attr('src', "../public/images/details_title_icon2.png");
		$(".first_img").attr('src', "../public/images/details_title_icon1.png");
	})
	$(".first_img").click(function() {
		$(".second_img").attr('src', "../public/images/details_title_icon2-.png");
		$(".first_img").attr('src', "../public/images/details_title_icon1-.png");
	})
	$("#tip_show").click(function() {
		if($("#tip_show").attr("value") == 1) {
			//验证原价一览是否有权限，无权限原价下拉不展开
			var powerList = JSON.parse($.getNodeList());
			if(!isHavePower(powerList, [40, 41, 42, 43])) {
				showErrorHandler("SYS_VALIDATEPOWER_ERROR", "info", "info");
				$('#collapseTwo').remove();
				return;
			} else {
				$("#tip_rotat").css({
					"transition": "all 0.5s ease-in-out",
					"display": "inline-block",
					"float": "right"
				});
				$("#tip_rotat").toggleClass('rotate-180');
				$("#tip_show").attr("value", 0);
			}
		} else {
			$("#tip_rotat").css({
				"transition": "all 0.5s ease-in-out",
				"display": "inline-block",
				"float": "right"
			});
			$("#tip_rotat").toggleClass('rotate-180');
			$("#tip_show").attr("value", 1);

		}
	})
	$("#job_i").click(function() {
		if($("#job_i").attr("value") == 1) {
			$("#ratating").css({
				"transition": "all 0.5s ease-in-out",
				"display": "inline-block",
				"float": "right"
			});
			$("#ratating").toggleClass('rotate-180');
			$("#job_i").attr("value", 0);
		} else {
			$("#ratating").css({
				"transition": "all 0.5s ease-in-out",
				"display": "inline-block",
				"float": "right"
			});
			$("#ratating").toggleClass('rotate-180');
			$("#job_i").attr("value", 1);
		}
	})
	init();
})
$(function() {
	$('#language').change(function() {
		var language = $('select.selectpicker.language_conversion').val();
		localStorage.setItem('language',language);
    	execI18n();
		var plansaleforeignmoney =setJobdetailForeignMoneyCode(foreign,'itemname',jobinfo['plansaleforeigncode'])['laug'];
        if(plansaleforeignmoney!=undefined&&plansaleforeignmoney!=null&&plansaleforeignmoney!=''){
		    $('.plan_foreign_money_code').text(plansaleforeignmoney);
		}
        var saleforeignmoney =setJobdetailForeignMoneyCode(foreign,'itemname',saleInfo['saleforeigntype'])['laug'];
        if(saleforeignmoney!=undefined&&saleforeignmoney!=null&&saleforeignmoney!=''){
		    $('.real_foreign_money_code').text(saleforeignmoney);
		}
	    var costforeignmoney =setJobdetailForeignMoneyCode(foreign,'itemname',costList['costforeigntype'])['laug'];
        if(costforeignmoney!=undefined&&costforeignmoney!=null&&costforeignmoney!=''){
		    $('.cost_foreign_money_code').text(costforeignmoney);
		}
        var payforeignmoney =setJobdetailForeignMoneyCode(foreign,'itemname',pay['payforeigntype'])['laug'];
        if(costforeignmoney!=undefined&&costforeignmoney!=null&&costforeignmoney!=''){
		     $('.pay_foreign_money_code').text(payforeignmoney);
		}
        var invoicetypename =setJobdetailForeignMoneyCode(costList,'invoicetypename')['laug'];
	    $("#invoiceTypeName").text(invoicetypename);
	    var invoicetextname =setJobdetailForeignMoneyCode(costList,'invoicetextname')['laug'];
	    $("#invoiceTextName").text(invoicetextname);
	    var invoicetypename =setJobdetailForeignMoneyCode(pay,'invoicetypename')['laug'];
	    $("#invoiceTypeName").text(invoicetypename);
	    var invoicetextname =setJobdetailForeignMoneyCode(pay,'invoicetextname')['laug'];
	    $("#invoiceTextName").text(invoicetextname);
	    var  tranke=setJobdetailForeignMoneyCode(trantrnlist,"itemname")['laug'];
	    $("#jifei").text(tranke);
	    var  lendke =setJobdetailForeignMoneyCode(lenfifo,"itemname")['laug']
	    $("#itmname").text(lendke);
	    if(language=="zc"){
	    	$("#guanli").text(lenfifo["departname"]);
	    }else{
	    	$("#guanli").text(lenfifo["departname"+language]);
	    }
	    
	    if(paydeductionflg!=undefined&&paydeductionflg!=null&&paydeductionflg!=''){
	     var deductionflgtext =getGridLanguage('deductionflg',paydeductionflg);	
	    }else{
	    	if(costdeductionflg!=undefined&&costdeductionflg!=null&&costdeductionflg!=''){
	    	var deductionflgtext =getGridLanguage('deductionflg',costdeductionflg);	
	    	}else{
	    		var deductionflgtext ='';
	    	}
	    	
	    }
	    $("#deductionflg").text(deductionflgtext);
	    var langtyp =$.getLangTyp();
		var itemname ="";
		var costitemname ="";
		var payitemname="";
	    for(var i = 0; i < jobuserlist.length;i++){
			if(langtyp == "jp"){
				itemname = jobuserlist[i]["itemname_jp"]				
			}
			else if(langtyp == "en"){
				itemname = jobuserlist[i]["itemname_en"]
			}
			else if(langtyp == "zc"){
				itemname = jobuserlist[i]["itmname"]
			}
			else if(langtyp == "zt"){
				itemname = jobuserlist[i]["itemname_hk"]
			}
			$('#jobuserTable .person-m .userlevel').eq(i).text(itemname);
	    }   
	  
	    setInvoiceTip();
	    setInoviceTipByOrderInfo();
	    if(costList.costforeigntype!=null&&costList.costforeigntype!=""){
	    	if(langtyp == "jp"){
	    		costitemname = 	costList.itemnamejp			
			}
			else if(langtyp == "en"){
				costitemname = costList.itemnameen
			}
			else if(langtyp == "zc"){
				costitemname = costList.itemnamezc
			}
			else if(langtyp == "zt"){
				costitemname = costList.itemnamehk
			}
	    	$("#costitemname").text(costitemname);
	    }else{
	    	var lange = JSON.parse(localStorage.getItem('moneyUnit'));
	    	if(langtyp == "jp"){
	    		costitemname = 	lange.moneyjp	
			}
			else if(langtyp == "en"){
				costitemname = lange.moneyen
			}
			else if(langtyp == "zc"){
				costitemname = lange.moneyzc
			}
			else if(langtyp == "zt"){
				costitemname = lange.moneyzt
			}
	    	$("#costitemname").text(costitemname);
	    }
	    if(pay.payforeigntype!=null&&pay.payforeigntype!=""){
	    	if(langtyp == "jp"){
	    		payitemname = 	pay.itemnamejp			
			}
			else if(langtyp == "en"){
				payitemname = pay.itemnameen
			}
			else if(langtyp == "zc"){
				payitemname = pay.itemnamezc
			}
			else if(langtyp == "zt"){
				payitemname = pay.itemnamehk
			}
	    	$("#payitemname").text(payitemname);
	    }else{
	    	var lange = JSON.parse(localStorage.getItem('moneyUnit'));
	    	if(langtyp == "jp"){
	    		payitemname = 	lange.moneyjp	
			}
			else if(langtyp == "en"){
				payitemname = lange.moneyen
			}
			else if(langtyp == "zc"){
				payitemname = lange.moneyzc
			}
			else if(langtyp == "zt"){
				payitemname = lange.moneyzt
			}
	    	$("#payitemname").text(payitemname);
	    }
	    
	})
	
    jobdetailflag =urlPars.parm("jobdetailflag");
	var jobcd =urlPars.parm("jobcd");
	var inputno =urlPars.parm("inputno");
	var path = $.getAjaxPath()+"getJobDetailNoList";
	var pars = {job_cd:jobcd};
	$("#lend_i").hide();
	$("#tran_i").hide();
	$("#cost_i").hide();
	$("#job_i").hide();
	if(jobdetailflag == "joblist"){
		$('.minute_title_span1').css('color','#1abc9c');
		$('#tip_rotat').removeClass('rotate-180');
	}else{
		$('.minute_title_span2').css('color','#1abc9c');
	}
	$.ajax({
		url:path,
		data:JSON.stringify(pars),
		headers: {"requestID":$.getRequestID(),"requestName":$.getRequestNameByJcZH()},
		success:function(data){
           if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
           	$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
           		//数据填充到表格中
           		var idlist=data.data["NOList"];
           		if(idlist==undefined||idlist==null||idlist.length<1){
           		 //url路径修改
					if(jobdetailflag=='joblist'){
						url = "jczh/job_registration_list.html?view=init&menu=se";
					}else if(jobdetailflag=='cost'||jobdetailflag=='tran'||jobdetailflag=='lend'||jobdetailflag=='cost'){
				 	    url = "jczh/cost_list.html?view=6";
				 	}else {
				 		//如果参数中未找到判断flag，页面无法判断正常调取哪个方法，所以要给弹提示线西
				 		//“参数未找到”之类的
				 		//TO DO
					 	url = "jczh/cost_list.html?view=6";
						showInfoMsgHandler("DATA_IS_NOT_EXIST","info","info",url);
						return;
				 	}
					 showInfoMsgHandler("DATA_IS_NOT_EXIST","info","info",url);
					 return;
           		}
           		payStatus=data.data["payStatus"];
           		sumcost=data.data["sumcost"];
           		if(sumcost=="0"){
           		$('.first_img').css('display','none');	
           		}
           		//设置本国货币
				setJobdetailMoneyCode(data["userInfo"]);
				$("#jobname").text(idlist[1]['jobname']);
           	 	//导航
				 var coststr = '';
				 var lendstr = '';
				 var transtr = '';
				 for(var i =0;i<idlist.length;i++){
				 	if(idlist[i]['orderTyp']=='cost'&&idlist[i]['costno']!=null&&idlist[i]['costno']!=''){
				 	 coststr += '<span  class="jobdetai_number" onclick="searchCostInfo(this.innerText)" style="font-size:14px !important;font-weight:300 !important;">'+idlist[i]['costno']+'</span><span>'+idlist[i]['costname']+'</span>'+'<span class="gang"> / </span>'
				     }
				 	if(idlist[i]['orderTyp']=='lend'&&idlist[i]['costno']!=null&&idlist[i]['costno']!=''){
				 	 lendstr += '<span  class="jobdetai_number" onclick="searchLendInfo(this.innerText)" style="font-size:14px !important;font-weight:300 !important;">'+idlist[i]['costno']+'</span><span>'+idlist[i]['costname']+'</span>'+'<span class="gang"> / </span>'
				      }
				 	if(idlist[i]['orderTyp']=='tran'&&idlist[i]['costno']!=null&&idlist[i]['costno']!=''){
				 	 transtr += '<span  class="jobdetai_number" onclick="searchTranInfo(this.innerText)" style="font-size:14px !important;font-weight:300 !important;">'+idlist[i]['costno']+'</span><span>'+idlist[i]['costname']+'</span>'+'<span class="gang"> / </span>'
				      }
				 	}
				 
				$('.costTab').append(coststr);
				$('.lendTab').append(lendstr);
				$('.tranTab').append(transtr);
				if(jobdetailflag=='joblist'){
					getJobDetails(jobcd);
				}else if(jobdetailflag=='cost'){
			 		searchCostInfo(inputno);
			 	}else if(jobdetailflag=='lend'){
			 		searchLendInfo(inputno)
			 	}else if(jobdetailflag=='tran'){
			 		searchTranInfo(inputno)
			 	}else{
			 		//url路径修改
			 		//如果参数中未找到判断flag，页面无法判断正常调取哪个方法，所以要给弹提示线西
			 		//“参数未找到”之类的
			 		//TO DO
				 	url = "jczh/cost_list.html?view=6";
					showInfoMsgHandler("DATA_IS_NOT_EXIST","info","info",url);
					return;
			 	}
		         data["userInfo"]

	           }else{
	           	showErrorFunHandler(data[$.getRequestMetaName()].result,"ERROR","ERROR");
	           }
       	$('.xychange.xycr').find('.activeT').find('input').each(function(index,element){
    		if(element.value!= idlist[0].plansaleishave){
    			$(element).parents('label').toggleClass('activeT');
    			$(element).parents('label').siblings('label').toggleClass('activeT');
    		}
    	});
    	$('.xychange.xycy').find('.activeT').find('input').each(function(index,element){
    		if(element.value!= idlist[0].plancostishave){
    			$(element).parents('label').toggleClass('activeT');
    			$(element).parents('label').siblings('label').toggleClass('activeT');
    		}
    	});
    	$('.xychange label.btn.btn-primary.activeT').siblings('label').css('display','none');
    	$('.xychange label.btn.btn-primary').removeClass('activeT');
       },
       error:function(data){
       		showErrorHandler("CONNECTION_SERVER_ERROR","ERROR","ERROR");
       }
	});
})

function getJobDetails(jobcd){
	if($('#ratating i').css('display') != 'block'){
		$("#job_i").show();
		$('#ratating').removeClass('rotate-180');
	}	
	if(!$('#ratating').hasClass('rotate-180')){
		$('#ratating i').click();		
	}
	//原价展开
	$("#tip_show").click();
	var powerList = JSON.parse($.getNodeList());
	if(!isHavePower(powerList,[5,6,7,8])){
		showErrorHandler("SYS_VALIDATEPOWER_ERROR","info","info");
		  return;	
	}
	var jobcd =urlPars.parm("jobcd");
	var path = $.getAjaxPath()+"jobDetails";	
	var pars = {job_cd:jobcd};
	$.ajax({
		url:path,
		data:JSON.stringify(pars),
		headers: {"requestID":$.getRequestID(),"requestName":$.getRequestNameByJcZH()},
		success:function(data){
           if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
           		 $.resetRequestID(data[$.getRequestUserInfoName()].requestID);
           		//数据填充到表格中
           		jobinfo =data[$.getRequestDataName()]["jobInfo"];
           		saleInfo =data[$.getRequestDataName()]["saleInfo"];
           		var jobLableList =data[$.getRequestDataName()]["jobLableList"];
		        var payProoList =data[$.getRequestDataName()]["payProoList"];
		        var JobUserList = data[$.getRequestDataName()]["jobUserList"];
		        var skip = data[$.getRequestDataName()]["skip"];
		        var costCountNums =sumcost;
		        foreign = data[$.getRequestDataName()]["foreign"];
		 /*       for(var i=0;i<foreign.length;i++){
		         //job登录外货
		         if(foreign[i]['itemcd']==jobinfo['plansaleforeigncode']){
		         var foreign =setJobdetailForeignMoneyCode(foreign[i],'itmname','plansaleforeigncode');
		         $('.plan_foreign_money_code').text(foreign);
		         }
		         //壳上登录的外货
		         if(foreign[i]['itemcd']==saleInfo['saleforeigntype']){
		         var foreign =setJobdetailForeignMoneyCode(foreign[i],'itmname','saleforeigntype');
		         $('.real_foreign_money_code').text(foreign);
		         }
		        }*/
		        var plansaleforeignmoney =setJobdetailForeignMoneyCode(foreign,'itemname',jobinfo['plansaleforeigncode'])['laug'];
		        if(plansaleforeignmoney!=undefined&&plansaleforeignmoney!=null&&plansaleforeignmoney!=''){
		        	$('.plan_foreign_money_code').text(plansaleforeignmoney);
		        }
		        var saleforeignmoney =setJobdetailForeignMoneyCode(foreign,'itemname',saleInfo['saleforeigntype'])['laug'];
		        if(saleforeignmoney!=undefined&&saleforeignmoney!=null&&saleforeignmoney!=''){
		        	$('.real_foreign_money_code').text(saleforeignmoney);
		        }
		        //计算用小数点位数
		        point = data['userInfo']['pointNumber'];
		        if(jobinfo["rate2"]!=undefined&&jobinfo["rate3"]!=undefined&&jobinfo["rate2"]!=null&&jobinfo["rate3"]!=null&&jobinfo["rate2"]!=''&&jobinfo["rate3"]!=''&&jobinfo["rate2"]!='0'&&jobinfo["rate3"]!='0'){
	        	//如果不设置成0  无法参与计算  因为不是数字
	        	if(jobinfo["truePayAmtSum"]==""){
	        		jobinfo["truePayAmtSum"]=0
	        	}
	        	if(jobinfo["truecostVatTotal"]==""){
	        		jobinfo["truecostVatTotal"]=0
	        	}
	        	 //实际
		         //如果壳上承认了了，rate2，rate3。去税率历史履历表中数据
		        if(jobinfo["accountflg"]!=0){
					 //实际
		        	var truecon = calculateTaxHandler('',jobinfo["saleamt"],'',jobinfo["trueCostTotalAmt"],saleInfo["reqamt"],jobinfo["truePayAmtSum"],saleInfo["salevatamt"],
							jobinfo["truecostVatTotal"],jobinfo["historyrate2"],jobinfo["historyrate3"],jobinfo["costFinshFlg"],costCountNums,point,jobinfo["foreignFormatFlg"],jobinfo["taxFormatFlg"]);
				}else{
					 //实际
		        	var truecon = calculateTaxHandler('',jobinfo["saleamt"],'',jobinfo["trueCostTotalAmt"],saleInfo["reqamt"],jobinfo["truePayAmtSum"],saleInfo["salevatamt"],
							jobinfo["truecostVatTotal"],jobinfo["rate2"],jobinfo["rate3"],jobinfo["costFinshFlg"],costCountNums,point,jobinfo["foreignFormatFlg"],jobinfo["taxFormatFlg"]);
				}
		        var reTaxTotal = .0;
		        if(jobinfo["jobFromJpp"] == 1)
		        {
		        	$("#taxTotal").text(formatNumber(jobinfo["planSaleTaxTotal"]));		//实际税金*  
		        	reTaxTotal = jobinfo["planSaleTaxTotal"];
		        }else{
		        	$("#taxTotal").text(formatNumber(truecon['taxTotal']));		//实际税金*  
		        	reTaxTotal = truecon['taxTotal'];
		        }
		        
		        
		        var reProfit = recalculateProfit('',jobinfo["saleamt"],'',jobinfo["trueCostTotalAmt"],jobinfo["costFinshFlg"],costCountNums,point,jobinfo["foreignFormatFlg"],jobinfo["taxFormatFlg"],reTaxTotal);
		    	
		        $("#profit").text(formatNumber(reProfit['profit']));		    //实际营业收益    
		        $("#profitRate").text(reProfit['profitRate']);		 //实际营业收益率	 
		        }
		        if(jobinfo["planrate2"]!=undefined&&jobinfo["planrate3"]!=undefined&&jobinfo["planrate2"]!=null&&jobinfo["planrate3"]!=null&&jobinfo["planrate2"]!=''&&jobinfo["planrate3"]!=''&&jobinfo["planrate2"]!='0'&&jobinfo["planrate3"]!='0'){
		        //预计，原价完了状态永远时0。当原件完了状态时1的时候，所有预计的值会被实际值覆盖
		        //如果壳上承认了了，rate2，rate3。去税率历史履历表中数据
		        if(jobinfo["accountflg"]!=0){
					 var plancon =calculateTaxHandler(jobinfo["plansale"],'',jobinfo["plancostamt"],'',jobinfo["planreqamt"],jobinfo["planpayamt"],jobinfo["planSaleVat"],
							jobinfo["plancosttax"],jobinfo["historyrate2"],jobinfo["historyrate3"],0,0,point,jobinfo["foreignFormatFlg"],jobinfo["taxFormatFlg"])				
				}else{
					 var plancon =calculateTaxHandler(jobinfo["plansale"],'',jobinfo["plancostamt"],'',jobinfo["planreqamt"],jobinfo["planpayamt"],jobinfo["planSaleVat"],
							jobinfo["plancosttax"],jobinfo["planrate2"],jobinfo["planrate3"],0,0,point,jobinfo["foreignFormatFlg"],jobinfo["taxFormatFlg"])				
		        }
		         $("#plantaxTotal").text(formatNumber(plancon['taxTotal']));//预计税金*		        
				 $("#planprofit").text(formatNumber(plancon['profit']));	//预计营业收益
				 $("#planprofitRate").text(plancon['profitRate']);	//预计营业收益率
		        }
		       
		         //job标签
				var str ="";
 			  	var Astr ="";
 			  	var Pstr ="";
 			   if(jobLableList.length>0){
 			  	for(var i =0 ;i<jobLableList.length;i++){
 			  		if(jobLableList[i]['lablelevel']=='0'){
 			  		   Astr +='<button class="btn btn-success  log_job" name="salescategoryRegistration_login" style="background-color: rgb(1,176,241);border: none;height: 20px;line-height: 10px;">'+jobLableList[i]['labletext']+'</button>';	
 			  		}else if(jobLableList[i]['lablelevel']=='1'){
 			  		   Pstr +='<button class="btn btn-success  log_job" name="salescategoryRegistration_login" style="background-color: rgb(250,121,34);border: none;height: 20px;line-height: 10px;">'+jobLableList[i]['labletext']+'</button>';
 			  		}
 			  	}
 		      	 str=Pstr+Astr;
 		       }
 			   $('#joblible').html(str);
		       
		        //原价终了
		        if(saleInfo["costfinishdate"]!=undefined&&saleInfo["costfinishdate"]!=null&&saleInfo["costfinishdate"]!=''){
		        	 //实施文字
			        $('#costendflagText').attr('name', 'job_detail_remind_labelT');
			        $('#costendflagText').attr('style', 'background-color: #707070;color: white;');
			        $(".language_conversion").change();
		        }
		        //入金完了状态表示
		        if(jobinfo["recflg"]=="1"){
		        $('#advance_finish').attr('name', 'job_detail_remind_labelT');
		        $('#advance_finish').attr('style', 'background-color: #707070;color: white;');
		        $(".language_conversion").change();	
		        }
		         $("#jobcd").text(jobinfo["jobcd"]);
		         $("#jobname").text(jobinfo["jobname"]);
		         $("#saleName").text(jobinfo["saleName"]);	
		         $("#clName").text(jobinfo["clName"]);	   
		         $("#payerName").text(jobinfo["payerName"]);	   
		         $("#gName").text(jobinfo["gName"]);	   
				 $("#Jobremark pre").html(jobinfo["remark"]);		        
				 $("#dlvday").text(jobinfo["dlvday"]);		        
				 $("#plandalday").text(jobinfo["plandalday"]);		        
				 $("#planreqdate").text(jobinfo["planreqdate"]);//请求予定日
				 $("#recdate").text(jobinfo["recdate"]);//入金日
				 $("#saleamt").text(formatNumber(jobinfo["saleamt"]));//壳上金额实际
				 $("#plansale").text(formatNumber(jobinfo["plansale"]));//预计壳上金额实际
				 $("#salevatamt").text(formatNumber(saleInfo["salevatamt"]));//壳上增值税	        
				 $("#plansalevat").text(formatNumber(jobinfo["planSaleVat"]));//预计壳上增值税		        
				 $("#reqamt").text(formatNumber(saleInfo["reqamt"]));	//实际请求金额	        
				 $("#planreqamt").text(formatNumber(jobinfo["planreqamt"]));	//预计请求金额	  
			     //壳上预计实际金额差值
			    $("#saledifference").text(formatNumber(jobinfo["saledifference"]));
			     //壳上增值税预计实际差值
			    $("#vatamtdifference").text(formatNumber(jobinfo["vatamtdifference"]));
			     //请求金额预计实际金额差值 
		        $("#reqdifference").text(formatNumber(jobinfo["reqdifference"]));
			    //壳上预计实际金额差值箭头表示
			    if(jobinfo["saleamt"]==undefined||jobinfo["saleamt"]==null||jobinfo["saleamt"]===''||jobinfo["plansale"]==undefined||jobinfo["plansale"]==null||jobinfo["plansale"]===''){
			    	 $("#saledifferenceicon").addClass("hidden");
			    }else{
			    	 if(Number(jobinfo["saleamt"])>Number(jobinfo["plansale"])){
			    	  $("#saledifferenceicon").addClass("rotate-90");
			    	 }else{
			    	 $("#saledifferenceicon i").attr("style","color:#E00;");	
			    	 }
			    	 if(Number(jobinfo["saleamt"])==Number(jobinfo["plansale"])){
			    	    $('#saledifferenceicon i').removeClass("icon-xiajiang");
						$('#saledifferenceicon i').addClass("icon-shang");
						$('#saledifferenceicon i').css("transform","rotate(90deg)");
						$('#saledifferenceicon i').css("display","inline-block");
						$('#saledifferenceicon i').css("color","#1ABC9C");
			    	  }
			    }
			    //壳上增值税预计实际差值箭头表示
			    if(saleInfo["salevatamt"]==undefined||saleInfo["salevatamt"]==null||saleInfo["salevatamt"]===''||jobinfo["planSaleVat"]==undefined||jobinfo["planSaleVat"]==null||jobinfo["planSaleVat"]===''){
			    	 $("#vatamtdifferenceicon").addClass("hidden");
			    }else{
			    	 if(Number(saleInfo["salevatamt"])>Number(jobinfo["planSaleVat"])){
			    	  $("#vatamtdifferenceicon").addClass("rotate-90");
			    	 }else{
			    	 	$("#vatamtdifferenceicon i").attr("style","color:#E00;");
			    	 }
			    	 if(Number(saleInfo["salevatamt"])==Number(jobinfo["planSaleVat"])){
			    	    $('#vatamtdifferenceicon i').removeClass("icon-xiajiang");
						$('#vatamtdifferenceicon i').addClass("icon-shang");
						$('#vatamtdifferenceicon i').css("transform","rotate(90deg)");
						$('#vatamtdifferenceicon i').css("display","inline-block");
						$('#vatamtdifferenceicon i').css("color","#1ABC9C");
			    	  }
			    }
			    //请求金额预计实际金额差值箭头表示
			    if(saleInfo["reqamt"]==undefined||saleInfo["reqamt"]==null||saleInfo["reqamt"]===''||jobinfo["planreqamt"]==undefined||jobinfo["planreqamt"]==null||jobinfo["planreqamt"]===''){
			    	 $("#reqdifferenceicon").addClass("hidden");
			    }else{
			    	 if(Number(saleInfo["reqamt"])>Number(jobinfo["planreqamt"])){
			    	  $("#reqdifferenceicon").addClass("rotate-90");
			    	 }else{
			    	 	 $("#reqdifferenceicon i").attr("style","color:#E00;");
			    	 }
			    	 if(Number(saleInfo["reqamt"])==Number(jobinfo["planreqamt"])){
			    	    $('#reqdifferenceicon i').removeClass("icon-xiajiang");
						$('#reqdifferenceicon i').addClass("icon-shang");
						$('#reqdifferenceicon i').css("transform","rotate(90deg)");
						$('#reqdifferenceicon i').css("display","inline-block");
						$('#reqdifferenceicon i').css("color","#1ABC9C");
			    	  }
			    }
		         //入力金额 --  start
			    if(saleInfo.saleforeigntype==""||saleInfo.saleforeigntype==null){
			    	$("#saleforeignamt").text(formatNumber(saleInfo["saleforeignamt"]));//金额
			    }else{
			    	$("#saleforeignamt").text(formatNumber(saleInfo["saleforeignamt"],saleInfo.itmvalue));//金额
			    }		        
				 $("#plansaleforeignamt").text(formatNumber(jobinfo["plansaleforeignamt"],jobinfo.itemvalue));//预计金额	
				 if(jobinfo["plansaleamt"]!=null&&jobinfo["plansaleamt"]!=""){
					 $("#plansaleforeignamt").text(formatNumber(jobinfo["plansaleamt"],jobinfo.itemvalue))
				 }
				 $("#salecurecode").text(Number(saleInfo["salecurecode"]));//实际换算cod	        
				 $("#plansalecurecode").text(Number(jobinfo["plansalecurecode"]));//预计换算code	        
				 $("#saleusedate").text(saleInfo["saleusedate"]);//适用日	        
				 $("#plansaleusedate").text(jobinfo["plansaleusedate"]);//适用日			        
				 $("#salerefer").text(saleInfo["salerefer"]);		        
				 $("#plansalerefer").text(jobinfo["plansalerefer"]);	
				 //入力金额 --  end 
				 $("#costTotalAmt").text(formatNumber(jobinfo["trueCostTotalAmt"]));	//实际原价*	        
				 $("#plancostamt").text(formatNumber(jobinfo["plancostamt"]));//预计原价*		        
				 $("#trueCostTotalAmt").text(formatNumber(jobinfo["truecostVatTotal"]));	//实际增值税*	        
				 $("#planvatamt").text(formatNumber(jobinfo["plancosttax"]));	//预计增值税*	        
				 
				 $("#truePayAmtSum").text(formatNumber(jobinfo["truePayAmtSum"]));	//实际支付金额 ，*
				 $("#planpayamt").text(formatNumber(jobinfo["planpayamt"]));//预计支付金额合计*	
			     //如果成为未终止，原价条数为零：计算的实际值全充值为空。costFinshFlg:0：未终止；1：终止
			     if(jobinfo["costFinshFlg"]==0&&costCountNums==0){
			     $("#costTotalAmt").text("");	//实际原价*	        
				 $("#trueCostTotalAmt").text("");//实际增值税*	        
				 $("#truePayAmtSum").text("");	//实际支付金额 ，*
			     $("#taxTotal").text("");		//实际税金*        
		         $("#profit").text("");		    //实际营业收益    
		         $("#profitRate").text("");		 //实际营业收益率	 	
			     }
				 //壳上备考区域
				 $("#saleremark pre").html(saleInfo["saleremark"]);//壳上登录备注
				 $("#saleadmitremark pre").html(jobinfo["saleadmitremark"]);//壳上承认备注（没有）
				 $("#recremark pre").html(jobinfo["recremark"]);//入金备注
				 //没有内容备考点取消
				 var saleremark=$("#saleremark pre").text();
				　var saleadmitremark=$("#saleadmitremark pre").text();
				 if(saleremark.replace(/\s+/g, "").length==0){
				 $("#saleremark_tab_point").addClass('hidden');	
				 }else{
				 	$("#saleremark_tab_point").removeClass('hidden');	
				 }
				 if(saleadmitremark.replace(/\s+/g, "").length==0){
				 $("#saleadmitremark_tab_point").addClass('hidden')	
				 }else{
				  $("#saleadmitremark_tab_point").removeClass('hidden')		
				 }
				 //job作业者
				 if(jobinfo["adddate"]!=null&&jobinfo["adddate"]!=''){
				  $("#jobadduser").text(jobinfo["addUserName"]);//job登录者
				  $("#jobadddate").text(new Date(jobinfo["adddate"]).format_extend('yyyy-MM-dd hh:mm:ss'));//job登录时间	
				  $("#jobadddatecolor").css("color",jobinfo["addusercolorv"]);
				 }else{
				 	 $("#jobaddhidden").addClass('hidden');
				 	 $("#jobupdhidden").addClass('hidden')
				 }
				 if(jobinfo["upddate"]!=null&&jobinfo["upddate"]!=''){
				  $("#jobupduser").text(jobinfo["upUserName"]);//job更新者
				  $("#jobupdate").text(new Date(jobinfo["upddate"]).format_extend('yyyy-MM-dd hh:mm:ss'));//job更新时间
				  $("#jobupdusercolor").css("color",jobinfo["upUsercolorv"]);
				  
				 }else{
				 	 $("#jobupdhidden").addClass('hidden');
				 }
				 if(saleInfo["saleadddate"]!=null&&saleInfo["saleadddate"]!=''){
				  $("#saleadduser").text(saleInfo["saleadduserName"]);//壳上登录
				  $("#saleadddate").text(new Date(saleInfo["saleadddate"]).format_extend('yyyy-MM-dd hh:mm:ss'));
				  $("#saleaddusercolor").css("color",saleInfo["saleadduserNamecolor"]);
				 }else{
				 	 $("#saleaddhidden").addClass('hidden');
				 	 $("#saleupdhidden").addClass('hidden');
				 }
				if(saleInfo["saleupddate"]!=null&&saleInfo["saleupddate"]!=''){
					$("#saleupduser").text(saleInfo["saleupduserName"]);//壳上更新
				    $("#saleupdate").text(new Date(saleInfo["saleupddate"]).format_extend('yyyy-MM-dd hh:mm:ss'));
					$("#saleupdusercolor").css("color",saleInfo["saleupduserNamecolor"]);
				}else{
					$("#saleupdhidden").addClass('hidden');
				}
				//如果没壳上承认 ,则承认,承认取消都不显示
				if(saleInfo["saleadmitdate"]!=null&&saleInfo["saleadmitdate"]!=''){
					$("#saleadmituserName").text(saleInfo["saleadmituserName"]);//壳上承认者
				 	$("#saleadmitdate").text(new Date(saleInfo["saleadmitdate"]).format_extend('yyyy-MM-dd hh:mm:ss'));
					$("#saleadmituserNamecolor").css("color",saleInfo["saleadmituserNamecolor"]);
				}else{
					$("#saleadmitdatehidden").addClass('hidden');
					$("#saleCancelhidden").addClass("hidden");
				}
				 
				if(saleInfo["saleadmitcanceluserName"]!=null&&saleInfo["saleadmitcanceluserName"]!=''){//壳上承认取消
				 	 $("#saleadmitcanceluserName").text(saleInfo["saleadmitcanceluserName"]);
				 	 $("#salecanceldate").text(new Date(saleInfo["salecanceldate"]).format_extend('yyyy-MM-dd hh:mm:ss'));
				     $("#saleadmitcanceluserNamecolor").css("color",saleInfo["saleadmitcanceluserNamecolor"]);
				}else{
				 	$("#saleCancelhidden").addClass('hidden');
				}
				if(saleInfo["reqdate"]!=null&&saleInfo["reqdate"]!=''){
				 	 $("#reqUserName").text(saleInfo["reqUserName"]);//请求申请着
				 	 $("#reqdate").text(new Date(saleInfo["reqdate"]).format_extend('yyyy-MM-dd hh:mm:ss'));
				     $("#reqtimes").text(saleInfo["reqtimes"]==""?0:saleInfo["reqtimes"]);
				     $("#reqUserNamecolor").css("color",saleInfo["reqUserNamecolor"]);
				
				}else{
				 	 $("#reqhidden").addClass('hidden');
				}
				if(saleInfo["invoicedate"]!=null&&saleInfo["invoicedate"]!=''){
				 	 $("#invoiceUserName").text(saleInfo["invoiceUserName"]);//发票发行
				 	 $("#invoicedate1").text(new Date(saleInfo["invoicedate"]).format_extend('yyyy-MM-dd hh:mm:ss'));
				 	 $("#invoicetimes").text(saleInfo["invoicetimes"]==""?0:saleInfo["invoicetimes"]);
				 	 $("#invoiceUserNamecolor").css("color",saleInfo["invoiceUserNamecolor"]);
				
				}else{
				 	 $("#invoicehidden").addClass('hidden');
				} 
				if(saleInfo["costfinishdate"]!=null&&saleInfo["costfinishdate"]!=''){
					 $("#costfinishuserName").text(saleInfo["costfinishuserName"]);//原价完了登录这
					 $("#costfinishdate").text(new Date(saleInfo["costfinishdate"]).format_extend('yyyy-MM-dd hh:mm:ss'));
				     $("#costfinishuserNamecolor").css("color",saleInfo["costfinishuserNamecolor"]);
				}else{
					 $("#costfinishhidden").addClass('hidden');
				}
				if(jobinfo["recupddate"]!=null&&jobinfo["recupddate"]!=''){
					 $("#recupdName").text(jobinfo["recupdName"]);//入金登录者
				 	 $("#recupddate").text(new Date(jobinfo["recupddate"]).format_extend('yyyy-MM-dd hh:mm:ss'));
				 	 $("#recupdNamecolor").css("color",jobinfo["reccolorv"]);
				}else{
				 	 $("#rechidden").addClass('hidden');
				}

				//当job更新时间和登陆时间一致的时候，更新不显示
				if(jobinfo["adddate"]==jobinfo["upddate"]){
				$("#jobupdhidden").addClass("hidden");
				
				}else{
					//若有job更新则 不显示job登入票 只显示job登入(变更票)
					  $("#jobaddhidden .min-hei-60").addClass("hidden");
				}
				////当壳上更新时间和登陆时间一致的时候，更新不显示
				if(saleInfo["saleadddate"]==saleInfo["saleupddate"]){
				$("#saleupdhidden").addClass("hidden");
				}
				//如果没承认 承认不显示
				if(saleInfo["saleadmitdate"]==undefined||saleInfo["saleadmitdate"]==null||saleInfo["saleadmitdate"]==''){
				$("#saleadmitdatehidden").addClass("hidden");
				$("#saleCancelhidden").addClass("hidden");
				}
				
				if(jobinfo["jobFromJpp"] == 1 || jobinfo["jobFromJpp"] == 2)
				{
					$("#jobaddhidden .min-hei-60").addClass("hidden");
					$("#jobupdhidden .min-hei-60").addClass("hidden");
					$("#saleaddhidden .min-hei-60").addClass("hidden");
					$("#saleupdhidden .min-hei-60").addClass("hidden");
					$("#saleadmitdatehidden .min-hei-60").addClass("hidden");
					$("#saleCancelhidden .min-hei-60").addClass("hidden");
					$("#reqhidden .min-hei-60").addClass("hidden");
					$("#invoicehidden .min-hei-60").addClass("hidden");
					$("#costfinishhidden .min-hei-60").addClass("hidden");
					$("#rechidden .min-hei-60").addClass("hidden");
				}
				
				//状态图片标签
		        if(skip['confirm']==1){  //壳上承认跳过
		        	$("#saleadmitremark_tab").addClass('hidden');
		        	$("#saleadmitdatehidden").addClass("hidden");
					$("#saleCancelhidden").addClass("hidden");
			        if(jobinfo["jobEndFlg"]=="1"){
			        //job终了,状态图片头
			        $('#jobflagImg').attr('src', '../public/images/job1.2.4.png');
			        } //计上计
			        else if( jobinfo["accountflg"]=="1"){
			        $('#jobflagImg').attr('src', '../public/images/job1.2.3.png');
			        }//登录计
			        else if(saleInfo["seladdflag"]=="1"){
			         $('#jobflagImg').attr('src', '../public/images/job1.2.2.png');	
			        }
			        //登录待
			        else if(saleInfo["seladdflag"]!="1"){
			         $('#jobflagImg').attr('src', '../public/images/job1.2.1.png');
			        }
		        }
		       //壳上承认bu跳过 
		        else{
			        if(jobinfo["jobEndFlg"]=="1"){
			        //job终了,状态图片头
			        $('#jobflagImg').attr('src', '../public/images/job1.1.5.png');
			        //承认remark的Tab初期显示
			        $('#myTab li:eq(1) a').tab('show');
			        }
			        //计上计
			        else if( jobinfo["accountflg"]=="1"){
			        $('#jobflagImg').attr('src', '../public/images/job1.1.4.png');
			        //承认remark的Tab初期显示
			        $('#myTab li:eq(1) a').tab('show');
			        }
			        //承认
			        else if(saleInfo["seladmitflag"]=="1"){
			        $('#jobflagImg').attr('src', '../public/images/job1.1.3.png');
			        //承认remark的Tab初期显示
			        $('#myTab li:eq(1) a').tab('show');
			        }
			        //登录计
			        else if(saleInfo["seladdflag"]=="1"){
			         $('#jobflagImg').attr('src', '../public/images/job1.1.2.png');	
			        }
			        //登录待
			        else if(saleInfo["seladdflag"]!="1"){
			         $('#jobflagImg').attr('src', '../public/images/job1.1.1.png');
			        }
		        }
				///循环加载卡片。
				var jobYstr = "";
				var jobGstr = "";
				var joZstr = "";
				var jobstr = "";
				var url_user = $.getJumpPath() + 'common/employ_retrieval.html';
				var langtyp =$.getLangTyp();
				var itemname ="";
				jobuserlist = JobUserList;
				 
				
				
				for(var i = 0; i < JobUserList.length;i++){
					if(langtyp == "jp"){
						itemname = JobUserList[i]["itemname_jp"]
					}
					else if(langtyp == "en"){
						itemname = JobUserList[i]["itemname_en"]
					}
					else if(langtyp == "zc"){
						itemname = JobUserList[i]["itmname"]
					}
					else if(langtyp == "zt"){
						itemname = JobUserList[i]["itemname_hk"]
					}
                        if(JobUserList[i]["level_flg"]=='1'){
						        joZstr +='<div class="col-md-3 panel person" style="padding: 5px; margin-bottom: 0; width: 225px; background: #fff;margin-left: 30px;">'
								+'<div class="person-t"><span class="text-center block i18n" name="jobregistration_business_ren" ></span></div>'
								+'<div class="person-m"><div class="person-m-l"><i class="iconfont icon-ren" style="color:'+JobUserList[i]["usercolor"]+';"></i></div>'
							    +'<div class="person-m-m"><div class="userlevel">' + itemname + '</div>'
								+'<div class="usercd">' + JobUserList[i]["memberid"] + '</div>'
								+'<div class="username">' + JobUserList[i]["user_name"] + '</div></div></div><div class="person-b"><label>'
								+'<input type="checkbox" checked="checked" id="inlineCheckbox44" class="level_flg" value="option4" disabled="disabled">'
								+'<span class="iconfont icon-duihaok"></span><span class="i18n" name="jobregistration_person_in_charge">责任者</span>'
								+'</label></div></div>'
								
								}else if(JobUserList[i]["level_flg"]=='2'){
							   	jobYstr +='<div class="col-md-3 panel person" style="padding: 5px; margin-bottom: 0; width: 225px; background: #fff;margin-left: 30px;">'
								+'<div class="person-t"><span class="text-center block i18n" name="jobregistration_business_ren" ></span></div>'
								+'<div class="person-m"><div class="person-m-l"><i class="iconfont icon-ren" style="color:'+JobUserList[i]["usercolor"]+';"></i></div>'
							    +'<div class="person-m-m"><div class="userlevel">' + itemname + '</div>'
								+'<div class="usercd">' + JobUserList[i]["memberid"] + '</div>'
								+'<div class="username">' + JobUserList[i]["user_name"] + '</div></div></div><div class="person-b"><label>'
								+'<input type="checkbox" id="inlineCheckbox44" class="level_flg" value="option4" disabled="disabled">'
								+'<span class="iconfont icon-duihaok"></span><span class="i18n" name="jobregistration_person_in_charge">责任者</span>'
								+'</label></div></div>'
							
					           }else if(JobUserList[i]["level_flg"]=='3'){
					           	 jobGstr +='<div class="col-md-3 panel person" style="padding: 5px; margin-bottom: 0; width: 225px; background: #fff;margin-left: 30px;">'
								+'<div class="person-t"><span class="text-center block i18n" name="jobregistration_bear_ren" ></span></div>'
								+'<div class="person-m"><div class="person-m-l"><i class="iconfont icon-ren" style="color:'+JobUserList[i]["usercolor"]+';"></i></div>'
							    +'<div class="person-m-m"><div class="userlevel">' + itemname + '</div>'
								+'<div class="usercd">' + JobUserList[i]["memberid"] + '</div>'
								+'<div class="username">' + JobUserList[i]["user_name"] + '</div></div></div><div class="person-b"><label>'
								+'<input type="checkbox" id="inlineCheckbox44" class="level_flg" value="option4" disabled="disabled">'
								+'<span class="iconfont icon-duihaok"></span><span class="i18n" name="jobregistration_person_in_charge">责任者</span>'
								+'</label></div></div>'
					           }
					
				}
				jobstr=joZstr+jobYstr+jobGstr;
				$("#jobuserTable").html($(jobstr));
				if(jobinfo['assignflg']=='1'){
					$("#md-flg").prop("checked", true);
				}else{
					$("#md-flg").prop("checked", false);
				}
				if(skip['md']=='1'){
					$("#md-flg").prop("checked", true);
				}
		       //解析语言文件
		       readLanguageFile(true);
		       $('.iconfont.icon-xiala').unbind('click');
		       text_over_detail();
		       var detail_woer_obj = $('.workerD_job').find('.minute_per').not('.hidden');
		       flag_worker_over(detail_woer_obj);
		       var border_last = $('.workerD_job').find('.minute_per').not('.hidden').last();		       
		       border_last.css('border-right','1px solid');
	           }else{
	           	showErrorFunHandler(data[$.getRequestMetaName()].result,"ERROR","ERROR");
	           }
       },
       error:function(data){
       		showErrorHandler("CONNECTION_SERVER_ERROR","ERROR","ERROR");
       }
	});

}

function flag_worker_over(obj){
	var width = document.documentElement.clientWidth;
	var length = obj.length;
	var num = 3;
	//主要分辨率1360与1920
	if(width == 1366 || width == 1440){
		num = 3;
	}else if(width == 1920){
		num = 4;		
	}else{
		num = 5;
	}
	if(length > num){
		for(var i = 1;i <= length; i ++){
			if((i%num == 0) && (i != 1)){
				obj.eq(i - 1).css('border-right','1px solid');
			}
		}
	}
}

function text_over_detail() {
	//文字超出事件
	$('.iconfont.icon-xiala').on('click',function() {
		if($(this).closest('.text-center').hasClass('rotate-90')) {
			$(this).closest('.text-center').siblings('.job_text').removeClass('over-anim');
			$(this).closest('.text-center').removeClass('rotate-90');
		} else {
			$(this).closest('.text-center').siblings('.job_text').addClass('over-anim');
			$(this).closest('.text-center').addClass('rotate-90');
		}
	})
	//文字超出
	$('.job_text').each(function(index,ele){
		var hei = $(this).find('pre').height();
		if($(this).closest('.form-group').css('display') == 'none'){
			$(this).closest('.form-group').addClass('bl');
			hei = $(this).find('pre').height();
			$(this).closest('.form-group').removeClass('bl');
		}
		$(this).nextAll('hr').addClass('hidden');
		$(this).nextAll('.rotate-90').addClass('hidden');
		if(hei > 60) {
			$(this).addClass('over-anim');
			$(this).nextAll('hr').removeClass('hidden');
			$(this).nextAll('.rotate-90').removeClass('hidden');
		}
	})	
}


function searchCostInfo(costno){
	//为pdf的input——no重新赋值
	input_no=costno;
	inputZf = costno;
	if($('#cost_i').css('display') != 'inline'){
		$("#cost_i").show();
		$('#cost_i_div').addClass('rotate-180');
		$('#collapseTwo').addClass('in');
	}	
	if($('#cost_i_div').hasClass('rotate-180')){
		$('#cost_i').click();		
	}
    var jobcd =urlPars.parm("jobcd");
    var path = $.getAjaxPath()+"searchCostInfo";	
	var pars = {job_cd:jobcd,cost_no:costno};
	$("#jobcd").text(jobcd);
	$.ajax({
		url:path,
		data:JSON.stringify(pars),
		headers: {"requestID":$.getRequestID(),"requestName":$.getRequestNameByJcZH()},
		success:function(data){
           if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
           	//		$("#payfinshusernamehidden").addClass("hidden");//当支付处理时间为空时,隐藏支付处理
					$("#updhidden").removeClass("hidden");
				    $("#costpdfhidden").removeClass("hidden");
				 	$("#confirmusernameCancelhidden").removeClass("hidden");
				 	$('#confirmuserhidden').removeClass('hidden');
	            	$("#confirmusernameCancelhidden").removeClass("hidden");
	            	$('#payreqhidden').removeClass('hidden');
	            	$('#payhidden').removeClass('hidden');
	            	$('#payaddhidden').removeClass('hidden');
	            	$('#updhidden').removeClass('hidden');
	            	$('#addupdhidden').removeClass('hidden');
	            	$("#payrmbdifferenceicon").removeClass("hidden");
			    	$("#payvatamttdifferenceicon").removeClass("hidden");
			    	$("#payamtdifferenceicon").removeClass("hidden");
				    var langtyp =$.getLangTyp();
					var costitemname ="";
					var payitemname ="";
			    	if(data.data.costList[0].costforeigntype!=null&&data.data.costList[0].costforeigntype!=""){
				    	if(langtyp == "jp"){
				    		costitemname = 	data.data.costList[0].itemnamejp			
						}
						else if(langtyp == "en"){
							costitemname = data.data.costList[0].itemnameen
						}
						else if(langtyp == "zc"){
							costitemname = data.data.costList[0].itemnamezc
						}
						else if(langtyp == "zt"){
							costitemname = data.data.costList[0].itemnamezt
						}
				    	$("#costitemname").text(costitemname);
				    }else{
				    	if(langtyp == "jp"){
				    		costitemname = 	data.userInfo.moneyjp			
						}
						else if(langtyp == "en"){
							costitemname = data.userInfo.moneyen
						}
						else if(langtyp == "zc"){
							costitemname = data.userInfo.moneyzc
						}
						else if(langtyp == "zt"){
							costitemname =data.userInfo.moneyzt
						}
				    	$("#costitemname").text(costitemname);
				    	
				    }
			    	if(data.data.pay[0]!=null){
			    		 if(data.data.pay[0].payforeigntype!=null&&data.data.pay[0].payforeigntype!=""){
					 	    	if(langtyp == "jp"){
					 	    		payitemname = 	data.data.pay[0].itemnamejp		
					 			}
					 			else if(langtyp == "en"){
					 				payitemname = data.data.pay[0].itemnameen
					 			}
					 			else if(langtyp == "zc"){
					 				payitemname = data.data.pay[0].itemnamezc
					 			}
					 			else if(langtyp == "zt"){
					 				payitemname = data.data.pay[0].itemnamehk
					 			}
					 	    	$("#payitemname").text(payitemname);
					 	    }else{
					 	    	
					 	    	if(langtyp == "jp"){
					 	    		payitemname = 	data.userInfo.moneyjp	
					 			}
					 			else if(langtyp == "en"){
					 				payitemname = data.userInfo.moneyen
					 			}
					 			else if(langtyp == "zc"){
					 				payitemname = data.userInfo.moneyzc
					 			}
					 			else if(langtyp == "zt"){
					 				payitemname = data.userInfo.moneyjzt
					 			}
					 	    	$("#payitemname").text(payitemname);
					 	    }
			    	}
           	 $.resetRequestID(data[$.getRequestUserInfoName()].requestID);
 		       /*var cost =data[$.getRequestDataName()]['cost'][0];
 		       var Invoiceintrn =data[$.getRequestDataName()]['Invoiceintrn'][0];*/
 		     
 		      if(data[$.getRequestDataName()]["costList"].length>0){
 		      	  costList =data[$.getRequestDataName()]["costList"][0];
 		      }else{
 		      	 if(jobdetailflag=='joblist'){
						url = "jczh/job_registration_list.html?view=init&menu=se";
					}else if(jobdetailflag=='cost'||jobdetailflag=='tran'||jobdetailflag=='lend'||jobdetailflag=='cost'){
				 	    url = "jczh/cost_list.html?view=6";
				 	}else {
				 		//如果参数中未找到判断flag，页面无法判断正常调取哪个方法，所以要给弹提示线西
				 		//“参数未找到”之类的
				 		//TO DO
					 	url = "jczh/cost_list.html?view=6";
						showInfoMsgHandler("DATA_IS_NOT_EXIST","info","info",url);
						return;
				 	}
					 showInfoMsgHandler("DATA_IS_NOT_EXIST","info","info",url);
					 return;
 		      }
 		      /*if(data[$.getRequestDataName()]["pay"].length>0){
 		      	  pay =data[$.getRequestDataName()]["pay"][0];
 		      	   payno =data[$.getRequestDataName()]["pay"][0]['inputno'];
 		      }else{
 		       if(jobdetailflag=='joblist'){
						url = "jczh/job_registration_list.html?view=init&menu=se";
					}else if(jobdetailflag=='cost'||jobdetailflag=='tran'||jobdetailflag=='lend'||jobdetailflag=='cost'){
				 	    url = "jczh/cost_list.html?view=6";
				 	}else {
				 		//如果参数中未找到判断flag，页面无法判断正常调取哪个方法，所以要给弹提示线西
				 		//“参数未找到”之类的
				 		//TO DO
					 	url = "jczh/cost_list.html?view=6";
						showInfoMsgHandler("DATA_IS_NOT_EXIST","info","info",url);
						return;
				 	}
					 showInfoMsgHandler("DATA_IS_NOT_EXIST","info","info",url);
					 return;
 		      }*/
 		       if(data[$.getRequestDataName()]["pay"].length>0){
 		      	  pay =data[$.getRequestDataName()]["pay"][0];
 		      	  payno =data[$.getRequestDataName()]["pay"][0]['inputno'];
 		        }else{
 		        pay ={};	
 		       }
 		      
 		      var rooftrn = data[$.getRequestDataName()]['prooftrn'];
 		      var Invoiceintrn =data[$.getRequestDataName()]["Invoiceintrn"];
 		      var costlable =data[$.getRequestDataName()]["costlable"];
 		      //设置外货
 		      var foreign = data[$.getRequestDataName()]["Foreign"];
	          var costforeignmoney =setJobdetailForeignMoneyCode(foreign,'itemname',costList['costforeigntype'])['laug'];
	          var payforeignmoney =setJobdetailForeignMoneyCode(foreign,'itemname',pay['payforeigntype'])['laug'];
	           if(costforeignmoney==undefined||costforeignmoney==null||costforeignmoney==''){
	          	   setJobdetailMoneyCode(data["userInfo"]);
		        }
	            if(payforeignmoney==undefined||payforeignmoney==null||payforeignmoney==''){
		        	  setJobdetailMoneyCode(data["userInfo"]);
		        }
	           if(costforeignmoney!=undefined&&costforeignmoney!=null&&costforeignmoney!=''){
		        	$('.cost_foreign_money_code').text(costforeignmoney);
		        }
	           if(payforeignmoney!=undefined&&payforeignmoney!=null&&payforeignmoney!=''){
		        	$('.pay_foreign_money_code').text(payforeignmoney);
		        }
 		      //job标签
				var str ="";
 			  	var Astr ="";
 			  	var Pstr ="";
 			   if(costlable.length>0){
 			  	for(var i =0 ;i<costlable.length;i++){
 			  		if(costlable[i]['lablelevel']=='0'){
 			  		   Astr +='<button class="btn btn-success  log_job" name="salescategoryRegistration_login" style="background-color: rgb(1,176,241);border: none;height: 20px;line-height: 10px;">'+costlable[i]['labletext']+'</button>';	
 			  		}else if(costlable[i]['lablelevel']=='1'){
 			  		   Pstr +='<button class="btn btn-success  log_job" name="salescategoryRegistration_login" style="background-color: rgb(250,121,34);border: none;height: 20px;line-height: 10px;">'+costlable[i]['labletext']+'</button>';
 			  		}
 			  	}
 		      	 str=Pstr+Astr;
 		       }
 			   $('#costlable').html(str);
 		      
 		      $("#orderdate").text(costList["orderdate"]);//发注日
 		      $("#payeename").text(costList["divnm"]);//发注先
 		      $("#costno").text(costno);//外发编号
 		      $("#inputno").text(pay["inputno"]==undefined?"":pay["inputno"]);//支付编号
 		      $("#plandlvday").text(costList["plandlvday"]);//纳品预订日
 		      $("#paydlyday").text(pay["paydlyday"]==undefined?"":pay["paydlyday"]);//实际纳日
 		      $("#payhopedate").text(pay["payhopedate"]==undefined?"":pay["payhopedate"]);//支払希望日
 		      $("#payplandate").text(pay["payplandate"]==undefined?"":pay["payplandate"]);//支払予定日
 		      $("#paydate").text(pay["paydate"]==undefined?"":pay["paydate"]);   //支払日
 		      // 外发表格区域 --start
 		      $("#payamt").text(formatNumber(pay["payamt"])==undefined?"":formatNumber(pay["payamt"]));//支付原价（税拔）
 		      $("#costrmb").text(formatNumber(costList["costrmb"]));//发注原价（税拔）
 		      $("#payvatamt").text(formatNumber(pay["payvatamt"])==undefined?"":formatNumber(pay["payvatamt"]));//支付增值税
 		      $("#costvatamt").text(formatNumber(costList["costvatamt"]));//发注增值税
 		      $("#payrmb").text(formatNumber(pay["payrmb"])==undefined?"":formatNumber(pay["payrmb"]));//支付金额（税入）
 		      $("#costpayamt").text(formatNumber(costList["costpayamt"]));//发注金额（税入）
 		      $("#payamtdifference").text(formatNumber(pay["payamtdifference"])==undefined?"":formatNumber(pay["payamtdifference"]));//发注原件与支付原价的查
 		      $("#payvatamttdifference").text(formatNumber(pay["payvatamttdifference"])==undefined?"":formatNumber(pay["payvatamttdifference"]));//发注增值税，与支付增值税的查
 		      $("#payrmbdifference").text(formatNumber(pay["payrmbdifference"])==undefined?"":formatNumber(pay["payrmbdifference"]));//发注金额与支付金额的查
 		     
 		     
 		        //发注原件与支付原价的查箭头表示
			    if(pay["payamt"]==null||pay["payamt"]===''||pay["payamt"]==undefined||costList["costrmb"]==undefined||costList["costrmb"]==null||costList["costrmb"]===''){
			    	 $("#payamtdifferenceicon").addClass("hidden");
			    }else{
			    	 if(Number(pay["payamt"])>Number(costList["costrmb"])){
			    	  $("#payamtdifferenceicon").addClass("rotate-90");	
			    	  $("#payamtdifferenceicon i").attr("style","color:red;");
			    	 }
			    	 if(Number(pay["payamt"])==Number(costList["costrmb"])){
			    	    $('#payamtdifferenceicon i').removeClass("icon-xiajiang");
						$('#payamtdifferenceicon i').addClass("icon-shang");
						$('#payamtdifferenceicon i').css("transform","rotate(90deg)");
						$('#payamtdifferenceicon i').css("display","inline-block");
						$('#payamtdifferenceicon i').css("color","#1ABC9C");
			    	  }
			    }
			    //发注增值税，与支付增值税的查箭头表示
			    if(pay["payvatamt"]==undefined||pay["payvatamt"]==null||pay["payvatamt"]===''||costList["costvatamt"]==undefined||costList["costvatamt"]==null||costList["costvatamt"]===''){
			    	 $("#payvatamttdifferenceicon").addClass("hidden");
			    }else{
			    	 if(Number(pay["payvatamt"])>Number(costList["costvatamt"])){
			    	  $("#payvatamttdifferenceicon").addClass("rotate-90");	
			    	  $("#payvatamttdifferenceicon i").attr("style","color:red;");
			    	 }
			    	  if(Number(pay["payvatamt"])==Number(costList["costvatamt"])){
			    	    $('#payvatamttdifferenceicon i').removeClass("icon-xiajiang");
						$('#payvatamttdifferenceicon i').addClass("icon-shang");
						$('#payvatamttdifferenceicon i').css("transform","rotate(90deg)");
						$('#payvatamttdifferenceicon i').css("display","inline-block");
						$('#payvatamttdifferenceicon i').css("color","#1ABC9C");
			    	  }
			    }
			    //发注金额与支付金额的查箭头表示
			    if(pay["payrmb"]==undefined||pay["payrmb"]==null||pay["payrmb"]===''||costList["costpayamt"]==undefined||costList["costpayamt"]==null||costList["costpayamt"]===''){
			    	 $("#payrmbdifferenceicon").addClass("hidden");
			    }else{
			    	 if(Number(pay["payrmb"])>Number(costList["costpayamt"])){
			    	  $("#payrmbdifferenceicon").addClass("rotate-90");	
			    	  $("#payrmbdifferenceicon i").attr("style","color:red;");
			    	 }
			    	 if(Number(pay["payrmb"])==Number(costList["costpayamt"])){
			    	    $('#payrmbdifferenceicon i').removeClass("icon-xiajiang");
						$('#payrmbdifferenceicon i').addClass("icon-shang");
						$('#payrmbdifferenceicon i').css("transform","rotate(90deg)");
						$('#payrmbdifferenceicon i').css("display","inline-block");
						$('#payrmbdifferenceicon i').css("color","#1ABC9C");
			    	  }
			    }
 		     
 		     // 外发表格区域 --end
 		     
 		    //类似入力共同区域 pay   start
 		      $("#payamt2").text(formatNumber(pay["payamt"])==undefined?"":formatNumber(pay["payamt"]));//支付原价（税拔）
 		      $("#payvatamt2").text(formatNumber(pay["payvatamt"])==undefined?"":formatNumber(pay["payvatamt"]));//支付增值税
 		      $("#payrmb2").text(formatNumber(pay["payrmb"])==undefined?"":formatNumber(pay["payrmb"]));//支付金额（税入）
 		      $("#payrate").text(pay["payrate"]==undefined?"":floatObj.multiply(pay["payrate"],100));//支付增值税率
 		      //判断外货小数点位数
 		      if(setJobdetailForeignMoneyCode(foreign,'itemname',pay['payforeigntype'])['point']==''){
 		      	   var paypoint = data['userInfo']['pointNumber'];
 		      }else{
 		      	   var paypoint = setJobdetailForeignMoneyCode(foreign,'itemname',pay['payforeigntype'])['point'];
 		      }
 		      $("#payforeignamt").text(formatNumber(pay["payforeignamt"],paypoint)!=undefined?formatNumber(pay["payforeignamt"],paypoint):'');//支付增值税率
 		      $('.payishave').find('.activeT').find('input').each(function(index,element){
					if(element.value!=pay['payishave']){
						$(element).parents('label').toggleClass('activeT');
						$(element).parents('label').siblings('label').toggleClass('activeT');
					}
				});
 		    // 类似入力共同区域 pay --end 
 		    
 		    // 类似入力共同区域 cost --star 
 		      $("#costrmb2").text(formatNumber(costList["costrmb"]));//发注原价（税拔）
 		      $("#costvatamt2").text(formatNumber(costList["costvatamt"]));//发注增值税
 		      $("#costpayamt2").text(formatNumber(costList["costpayamt"]));//发注金额（税入）
 		      $("#costrate").text(floatObj.multiply(costList["costrate"],100));//外发增值税率
 		     //判断外货小数点位数
 		     if(setJobdetailForeignMoneyCode(foreign,'itemname',costList['costforeigntype'])['point']==''){
 		      	var costpoint =data['userInfo']['pointNumber'];
 		      }else{
 		      	var costpoint =setJobdetailForeignMoneyCode(foreign,'itemname',costList['costforeigntype'])['point'];
 		      }
 		      $("#costforeignamt").text(formatNumber(costList["costforeignamt"],costpoint));//发注入力金额
 		      $('.costishave').find('.activeT').find('input').each(function(index,element){
					if(element.value!=costList['costishave']){
						$(element).parents('label').toggleClass('activeT');
						$(element).parents('label').siblings('label').toggleClass('activeT');
					}
				});
 		     taxrate();
 		    // 类似入力共同区域 cost --end 
 		    //凭证区域 
				var str = '';
				if(rooftrn.length>0){
				  for(var i = 0;i < rooftrn.length;i++){
					str +='<a onclick="window.open(\''+returnURL(rooftrn[i].PROOF_URL)+'\')">'+'<button class="btn btn-success  log_job" name="salescategoryRegistration_login" style="margin-left: 20px;height: 20px;line-height: 10px;">'+rooftrn[i].PROOF_TITLE+'</button><input type="hidden" class="urlvalue" value="' + rooftrn[i].PROOF_URL +'" />'+'</a>';
					}
				$('#rooftrn').empty();
				$('#rooftrn').html($(str));  	
				}
				
 		      //发票赋值
 		      if(pay["invoiceno"]!=undefined&&pay["invoiceno"]!=null){
 		      	 	$("#invoicedate").text(pay["invoicedate"]);
 		      		$("#invoiceno").text(pay["invoiceno"]);
 		      		var invoicetypename =setJobdetailForeignMoneyCode(pay,'invoicetypename')['laug'];
 		    	    $("#invoiceTypeName").text(invoicetypename);
 		    	    var invoicetextname =setJobdetailForeignMoneyCode(pay,'invoicetextname')['laug'];
 		    	    $("#invoiceTextName").text(invoicetextname);
 		    	   paydeductionflg = pay["deductionflg"];
 		    	    if(paydeductionflg==1){
 		    	    	$("#deductionflg").text(getGridLanguage('deductionflg','1'));
 		    	    	var paydeutext =getGridLanguage('deductionflg','1');
 		    	    }else{
 		    	    	$("#deductionflg").text(getGridLanguage('deductionflg','0'));
 		    	    	var paydeutext =getGridLanguage('deductionflg','0');
 		    	    }
 		    	    
 		    	    $("#Trate").text(floatObj.multiply(pay["payrate"],100));
 		     		
 		      }else{
 		      	    $("#invoicedate").text(costList["invoicedate"]);
 		      		$("#invoiceno").text(costList["invoiceno"]);
 		      		var invoicetypename =setJobdetailForeignMoneyCode(costList,'invoicetypename')['laug'];
 		    	    $("#invoiceTypeName").text(invoicetypename);
 		    	    var invoicetextname =setJobdetailForeignMoneyCode(costList,'invoicetextname')['laug'];
 		    	    $("#invoiceTextName").text(invoicetextname);     
 		    	    if(costList["deductionflg"]==1){
 		    	    	$("#deductionflg").text(getGridLanguage('deductionflg','1'));
 		    	    	var costdeutext =getGridLanguage('deductionflg','1');
 		    	    }else{
 		    	    	$("#deductionflg").text(getGridLanguage('deductionflg','0'));
 		    	    	var costdeutext =getGridLanguage('deductionflg','0');
 		    	    }
 		    	    
 		    	    $("#Trate").text(floatObj.multiply(costList["costrate"],100));
 		        }
 		      costdeductionflg= costList["deductionflg"];
 		       if(costList["deductionflg"]==1){
 		    	    	
 		    	    	var costdeutext =getGridLanguage('deductionflg','1');
 		    	    }else{
 		    	    	
 		    	    	var costdeutext =getGridLanguage('deductionflg','0');
 		    	    }
 		    	if(pay["deductionflg"]==1){
 		    	    	
 		    	    	var paydeutext =getGridLanguage('deductionflg','1');
 		    	    }else{
 		    	    	
 		    	    	var paydeutext =getGridLanguage('deductionflg','0');
 		    	    }
 		        //tooltip问号显示
 		        var smony,smony1,smony2,smony3;
 		        setJobdetailForeignMoneyCode(pay,'invoicetypename')['laug'] == undefined?smony='':smony = setJobdetailForeignMoneyCode(pay,'invoicetypename')['laug'];
 		        setJobdetailForeignMoneyCode(pay,'invoicetextname')['laug']== undefined?smony1='':smony1 = setJobdetailForeignMoneyCode(pay,'invoicetextname')['laug'];
 		      	var mes1 =smony+' '+smony1+' '+paydeutext;
 		      	setJobdetailForeignMoneyCode(costList,'invoicetypename')['laug'] == undefined?smony2='':smony2 = setJobdetailForeignMoneyCode(costList,'invoicetypename')['laug'];
 		        setJobdetailForeignMoneyCode(costList,'invoicetextname')['laug'] == undefined?smony3='':smony3 = setJobdetailForeignMoneyCode(costList,'invoicetextname')['laug'];
 		       var mes2 =smony2+' '+smony3+' '+costdeutext;
 		        /******************************/
 		       
 		     setInoviceTipByOrderInfo();
 		       
 		   //toolTipLanguage('',mes1,mes2);
 		       
 		       /***************************/
 		        
 		      	
 		        //外发备考区域
 		        $("#costremark pre").html(costList["costremark"]);
	            $("#payremark pre").html(pay["payremark"]==undefined?"":pay["payremark"]);//支付登录备考
	            $("#payreqremark pre").html(pay["reqRemark"]==undefined?"":pay["reqRemark"]);//支付申请备考
	            $("#confirmark pre").html(pay["confirmRemark"]==undefined?"":pay["confirmRemark"]);//支付承认备考
	            $("#payFinshRemark pre").html(pay["payFinshRemark"]==undefined?"":pay["payFinshRemark"]);//支付备考
	            //判断备考是否为空,为空时取消黑点
	            if( $("#payremark pre").text().replace(/\s+/g, "").length==0){
				 $("#payremark_point").addClass('hidden')	
				}else{
				$("#payremark_point").removeClass('hidden')		
				}
	            if( $("#payreqremark pre").text().replace(/\s+/g, "").length==0){
				 $("#payreqremark_point").addClass('hidden')	
				}else{
				$("#payreqremark_point").removeClass('hidden')	
				}
	            if( $("#confirmark pre").text().replace(/\s+/g, "").length==0){
				 $("#confirmark_point").addClass('hidden')	
				}else{
				$("#confirmark_point").removeClass('hidden')		
				}
	            if( $("#payFinshRemark pre").text().replace(/\s+/g, "").length==0){
				 $("#payFinshRemark_point").addClass('hidden')	
				}else{
				$("#payFinshRemark_point").removeClass('hidden')		
				}
	            //外发作业者区域
	            /*
	             * */
	            $('#costpdfhidden .min-hei-60').removeClass('hidden');
	            $('#payaddhidden .min-hei-60').removeClass('hidden');
				$('#payreqhidden .min-hei-60').removeClass('hidden');
				$('#confirmuserhidden .min-hei-60').removeClass('hidden');
				
	            if(costList["costadddate"]==null||costList["costadddate"]==''){
	            	$('#addupdhidden').addClass('hidden');
	            }else{
	            	$("#addupdusername").text(costList["addupdusername"]);//外发登录者
	            	$("#costadddate").text(new Date(costList["costadddate"]).format_extend('yyyy-MM-dd hh:mm:ss'));
	                $("#addupdusernamecolor").css("color",costList["costaddcolorv"]);
	            }
	            if(costList["costupdate"]==null||costList["costupdate"]==''){
	            	$('#updhidden').addClass('hidden');
	            }else{
		            $("#updusername").text(costList["updusername"]);//外发更新者
		            $("#costupdate").text(new Date(costList["costupdate"]).format_extend('yyyy-MM-dd hh:mm:ss'));
	            	$("#updusernamecolor").css("color",costList["updusercolorv"]);
	            }
	            if(pay["adddate"]==null||pay["adddate"]==''){
	            	$('#payaddhidden').addClass('hidden');
	            }else{
		            $("#addUserName").text(pay["addUserName"]);//支付登录者
		            $("#payadddate").text(new Date(pay["adddate"]).format_extend('yyyy-MM-dd hh:mm:ss'));
		            $("#addUserNamecolor").css("color",pay["payaddusercolor"]);
		            
	            }
	            if(pay["update"]==null||pay["update"]==''){
	            	$('#payhidden').addClass('hidden');
	            }else{
		            $("#upUserName").text(pay["upUserName"]);//支付更新者
	            	$("#payupdate").text(new Date(pay["update"]).format_extend('yyyy-MM-dd hh:mm:ss'));
	            	$("#upUserNamecolor").css("color",pay["payupdusercolor"]);
	            }
	            if(pay["payreqdate"]==null||pay["payreqdate"]==''){
	            	$('#payreqhidden').addClass('hidden');
	            }else{
		            $("#payrequsername").text(pay["payReqUserName"]);//支付申请者
	                $("#payreqdate").text(new Date(pay["payreqdate"]).format_extend('yyyy-MM-dd hh:mm:ss'));
	            	$("#payrequsernamecolor").css("color",pay["payReqUsercolor"]);
	            }
	            if(pay["confirmdate"]==null||pay["confirmdate"]==''){
	            	$('#confirmuserhidden').addClass('hidden');
	            	//$("#confirmusernameCancelhidden").addClass("hidden");
	            	
	            }else{
		            $("#confirmusername").text(pay["confirmUserName"]);//支付承认者
	            	$("#confirmdate").text(new Date(pay["confirmdate"]).format_extend('yyyy-MM-dd hh:mm:ss'));
	            	$("#confirmusernamecolor").css("color",pay["confirmusercolor"]);
	            }
	            //支付承认取消者
	            if(pay["payconfirmcanceldate"]==undefined||pay["payconfirmcanceldate"]==null||pay["payconfirmcanceldate"]==''){//壳上承认取消
				 	  $("#confirmusernameCancelhidden").addClass("hidden");
				 	  
				 }else{
				 	  $("#payconfirmcancelUserName").text(pay["payconfirmcancelUserName"]);//支付承认取消者
	            	  $("#payconfirmcanceldate").text(new Date(pay["payconfirmcanceldate"]).format_extend('yyyy-MM-dd hh:mm:ss'));
					  $("#payconfirmcancelUserNamecolor").css("color",pay["payconfirmcancelusercolor"]);
				 }
	            //发注书发行者 
	            if(costList["costpdfdate"]==undefined||costList["costpdfdate"]==null||costList["costpdfdate"]==''){//发注书发行者
				 	  $("#costpdfhidden").addClass("hidden");
				 	  
				 }else{
				 	  $("#costpdfaddusername").text(costList["costpdfaddusername"]);//发注书发行者
	            	  $("#costpdfdate").text(new Date(costList["costpdfdate"]).format_extend('yyyy-MM-dd hh:mm:ss'));
				      $("#costpdfaddusernamecolor").css("color",costList["costpdfaddusernamecolor"]);
				 }

	            //当更新时间和登陆时间一致的时候，更新不显示
				if(costList["costadddate"]==costList["costupdate"]){
				    $("#updhidden").addClass("hidden");
				    
				}
				if(pay["update"]==pay["adddate"]){
					$('#payhidden').addClass('hidden');	
					
				}else{
					$('#payaddpdfhidden').addClass('hidden');//支付登录pdf出力隐藏
				}
				if(pay["payupdate"]==undefined||pay["payupdate"]==null||pay["payupdate"]==''){
				$("#payfinshusernamehidden").addClass("hidden");//当支付处理时间为空时,隐藏支付处理
				
				}
				else if(pay["payupdate"]!=null&&pay["payupdate"]!=''){
				$("#payfinshusername").text(pay["payupuser"]);//支付处理者
	            $("#payfinshdate").text(new Date(pay["payupdate"]).format_extend('yyyy-MM-dd hh:mm:ss'));
	            $("#payfinshusernamecolor").css("color",pay["payupusercolor"]);
				}
				
				if(costList["costFromJpp"] == 1)
				{
					$('#costpdfhidden .min-hei-60').addClass('hidden');
				}
				if(pay["payFromJpp"] == 1)
				{
					$('#payaddhidden .min-hei-60').addClass('hidden');
					$('#payreqhidden .min-hei-60').addClass('hidden');
					$('#confirmuserhidden .min-hei-60').addClass('hidden');
				}
		            //支付完了状态表示
			        if(pay["payflg"]=='1'){
			        $('#payfinish').attr('name', 'job_detail_remind_labelT');
			        $('#payfinish').attr('style', 'background-color: #707070;color: white;');
			        $(".language_conversion").change();	
			        }
		            
			      //if(costList["costfinishflg"]=="1"){
                    if(costList["status"]=="4"){
                //原价终了
                $('#costflagimg').attr('src', '../public/images/job2.1.png');
                }else if(pay["confirmdate"]!=null && pay["confirmdate"] != ""){
                //承认
                $('#costflagimg').attr('src', '../public/images/job2.2.png');
                }else if(pay["payreqdate"]!=null && pay["payreqdate"] != ""){
                //申请
                $('#costflagimg').attr('src', '../public/images/job2.3.png');
                }else if(pay["adddate"]!=null && pay["adddate"] != ""){
                //登录
                $('#costflagimg').attr('src', '../public/images/job2.4.png');
                }else if(costList["costadddate"]!=null && costList["costadddate"] != ""){
                //发注
                $('#costflagimg').attr('src', '../public/images/job2.5.png');
                }
	           }
	           else{
	           	showErrorFunHandler(data[$.getRequestMetaName()].result,"ERROR","ERROR");
	           }
	           $('.iconfont.icon-xiala').unbind('click');
	           text_over_detail();
	           addAdvEvent();
	           var detail_woer_obj = $('.workerD_fazhu').find('.minute_per').not('.hidden');
		       flag_worker_over(detail_woer_obj);
	           var border_last = $('.workerD_fazhu').find('.minute_per').not('.hidden').last();
		       border_last.css('border-right','1px solid');
       },
       error:function(data){
       		showErrorHandler("CONNECTION_SERVER_ERROR","ERROR","ERROR");
       }
	}); 
}
function addAdvEvent(){
	$('ul.advance_url li span').on('click',function(e){
		var name = $(this).text(),
		url = $(this).siblings('input[type="hidden"]').val();
		var indexLi = $(this).parents("ul.advance_url").find("li span").index(this);
//		console.log(indexLi);
		layer.open({
			type: 1,
			title: false,
			closeBtn: 0,
			shadeClose: true, //点击遮罩关闭层
			area: ['400px', 'auto'],
			content: $('div.zhengquan'),
			success: function(layero, index) {
				layero.find('input#save_name').val(name);
				layero.find('input#save_url').val(url);
				layero.find('input#save_name').attr('index',indexLi);
				var closeBtn = layero.find('.iconfont.icon-guanbi,div.zhengquan #closeBtnInput');
				closeBtn.on('click', function() {
					layer.close(index);
				})				
			}
		});
	})
}
//凭证跳转
function advanceURL(){
	if(!Judgment_empty('advancetitle')){
		return;
	};
	if(!Judgment_empty('advanceurl')){
		return;
	};
	var advance_title=$(".advancetitle").val();
	var advance_url=$(".advanceurl").val();
	var indexF = $(".advancetitle").attr('index');
	/*if(advance_url.length>8000){
		showErrorHandler("URLTOOLONG","info","info");
	}*/
	var liDom = $('.advance_url').find('li span').eq(indexF);
	liDom.text(advance_title);
	liDom.siblings('input[type="hidden"]').val(advance_url);	
	$("div.zhengquan #closeBtnInput").click();
	$(".advanceurl").val('');
	$(".advancetitle").val('');
	removeAdvEvent();
	addAdvEvent();
}
function removeAdvEvent(){
	$('ul.advance_url li span').unbind('click');
}
//立替
function searchLendInfo(lendno){
	//为pdf的input——no重新赋值
	input_no=lendno;
	inputLt = lendno;
	if($('#lend_i').css('display') != 'inline'){
		$("#lend_i").show();
		$('#collapseTwo').addClass('in');
		$('#lend_i_div').addClass('rotate-180');
	}	
	if($('#lend_i_div').hasClass('rotate-180')){
		$('#lend_i').click();		
	}	
     var jobcd =urlPars.parm("jobcd");
     $("#jobcd").text(jobcd);
    var path = $.getAjaxPath()+"LendtrnQuery";	
	var pars = {lendtrn:{job_cd:jobcd,input_no:lendno}};
	$.ajax({
		url:path,
		data:JSON.stringify(pars),
		headers: {"requestID":$.getRequestID(),"requestName":$.getRequestNameByJcZH()},
		success:function(data){
           if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
           	 $.resetRequestID(data[$.getRequestUserInfoName()].requestID);
            	lenfifo = data[$.getRequestDataName()]['LendtrnInput']['lendtrnList'][0];
   		        var lendlable =data[$.getRequestDataName()]['LendtrnInput']['lableList']
   		        var lendtrnDate =data[$.getRequestDataName()]['LendtrnInput']['lendtrnDate']
   		         //job标签
				var str ="";
 			  	var Astr ="";
 			  	var Pstr ="";
 			   if(lendlable.length>0){
 			  	for(var i =0 ;i<lendlable.length;i++){
 			  		if(lendlable[i]['lablelevel']=='0'){
 			  		   Astr +='<button class="btn btn-success  log_job" name="salescategoryRegistration_login" style="background-color: rgb(1,176,241);border: none;height: 20px;line-height: 10px;">'+lendlable[i]['labletext']+'</button>';	
 			  		}else if(lendlable[i]['lablelevel']=='1'){
 			  		   Pstr +='<button class="btn btn-success  log_job" name="salescategoryRegistration_login" style="background-color: rgb(250,121,34);border: none;height: 20px;line-height: 10px;">'+lendlable[i]['labletext']+'</button>';
 			  		}
 			  	}
 		      	 str=Pstr+Astr;
 		       }
 			   $('#lendlable').html(str);
   		        
   		        
   		        $("#lend_use_date").text(lenfifo['lend_date']!=undefined&&['lend_use_date']!=null?lenfifo['lend_date']:'');
				$("#lend_name").text(lenfifo['lend_name']!=undefined&&lenfifo['lend_name']!=null?lenfifo['lend_name']:'');
				$("#itmname").text(lenfifo['itmname']!=undefined&&lenfifo['itmname']!=null?setJobdetailForeignMoneyCode(lenfifo,"itemname")['laug']:'');
				$("#username").text(lenfifo['username']!=undefined&&lenfifo['username']!=null?lenfifo['username']:'');
				$("#lendremark pre").html(lenfifo['remark']!=undefined&&lenfifo['remark']!=null?lenfifo['remark']:'');
		        $("#memberid").text(lenfifo['memberid']!=undefined&&lenfifo['username']!=null?lenfifo['memberid']:'');
		        $("#lend_amt").text(lenfifo['lend_amt']!=undefined&&lenfifo['lend_amt']!=null?formatNumber(lenfifo['lend_amt']):'');//立替原价
		        $("#lend_vat_amt").text(lenfifo['lend_vat_amt']!=undefined&&lenfifo['lend_vat_amt']!=null?formatNumber(lenfifo['lend_vat_amt']):'');//立替增值税
		        $("#lend_pay_amt").text(lenfifo['lend_pay_amt']!=undefined&&lenfifo['lend_pay_amt']!=null?formatNumber(lenfifo['lend_pay_amt']):'');//立替金额（税入）
		        language = localStorage.getItem('language');
		        if(language=="zc"){
			    	$("#guanli").text(lenfifo["departname"]);
			    }else{
			    	$("#guanli").text(lenfifo["departname"+language]);
			    }
		       if(lenfifo['lend_foreign_type']==undefined||lenfifo['lend_foreign_type']==null||lenfifo['lend_foreign_type']==''){
		       var lendpoint = data['userInfo']['pointNumber'];	
		       }else{
		       	var lendpoint = lenfifo['foreignlen'];	
		       }
		        $("#lend_foreign_amt").text(lenfifo['lend_foreign_amt']!=undefined&&lenfifo['lend_foreign_amt']!=null?formatNumber(lenfifo['lend_foreign_amt'],lendpoint):'');//立替金额（税入）
		        if(lenfifo['colorv']!=undefined||lenfifo['colorv']!=null||lenfifo['colorv']!=''){
		         var obj = document.getElementById("personicon");
  					 obj.style.color=lenfifo['colorv'];	
		        }
		        $("#memberid").text(lenfifo['memberid']!=undefined&&lenfifo['username']!=null?lenfifo['memberid']:'');
		      /*  外貨赋值*/
		        var list = data[$.getRequestDataName()]['LendtrnInput'];
				var jsData = {};
				jsData['plan_cost_foreign_code'] = list['plan_cost_foreign_code'];
				SelectObj.selectData = jsData;
				var selectID = [];
				selectID.push("plan_cost_foreign_code");
				SelectObj.setStringFlg='_';
				SelectObj.setSelectID = selectID;
				SelectObj.setSelectOfLog();
		        $("#plan_cost_foreign_code").val(lenfifo['lend_foreign_type']);
		        if(lenfifo['lend_foreign_type']!=undefined&&lenfifo['lend_foreign_type']!=null&&lenfifo['lend_foreign_type']!=''){
		        /*	$('.cost_rate_click').find('.active').find('input').each(function(index,element){
						if(element.value!=cost['costishave']){
							$(element).parents('label').toggleClass('active');
							$(element).parents('label').siblings('label').toggleClass('active');
						}
					});	*/
		        }
		        if(lenfifo['lend_foreign_type']!=undefined&&lenfifo['lend_foreign_type']!=null&&lenfifo['lend_foreign_type']!=''){
		        	 $('#switch-state-off').bootstrapSwitch('disabled',false);
		        	  $(".lend_foreign_code").text($("#plan_cost_foreign_code option:selected").text());
		        	  $('#switch-state-off').bootstrapSwitch('state',1);
		        	  $('#switch-state-off').bootstrapSwitch('disabled',true);
		        }else{
		        	$('#switch-state-off').bootstrapSwitch('disabled',false);
		        	$('#switch-state-off').bootstrapSwitch('state',0);
		        	$('#switch-state-off').bootstrapSwitch('disabled',true);
		        }
		      
		        //
	           $('.lendishave').find('.activeL').find('input').each(function(index,element){
					if(element.value!=lenfifo['ishave']){
						$(element).parents('label').toggleClass('activeL');
						$(element).parents('label').siblings('label').toggleClass('activeL');
					}
				});
	           //$('label.btn.btn-primary.activeL').siblings('label').css('display','none');
	           //$('label.btn.btn-primary').removeClass('activeL');
				var rooftrn =data[$.getRequestDataName()]['prooftrn']
				//凭证区域 
				var str = '';
				if(rooftrn.length>0){
				  for(var i = 0;i < rooftrn.length;i++){
					str +='<a onclick="window.open(\''+returnURL(rooftrn[i].PROOF_URL)+'\')">'+'<button class="btn btn-success  log_job" name="salescategoryRegistration_login" style="margin-left: 20px;height: 20px;line-height: 10px;">'+rooftrn[i].PROOF_TITLE+'</button><input type="hidden" class="urlvalue" value="' + rooftrn[i].PROOF_URL +'" />'+'</a>';
					}
				$('#lendrooftrn').html(str);  	
				}
				
				$('#lendaddhidden .min-hei-60').removeClass('hidden');
            	$('#lenduphidden .min-hei-60').removeClass('hidden');
            	$('#lendconfirmhidden .min-hei-60').removeClass('hidden');
            	$('#lendcancelconfirmhidden .min-hei-60').removeClass('hidden');
            	
				if(lendtrnDate["adddate"]==null||lendtrnDate["adddate"]==''){
				 	$('#lendaddhidden').addClass('hidden');
				}else{
				  $("#lendadduser").text(lenfifo["adduser"]);//登录者
				  $("#lendadddate").text(new Date(lendtrnDate["adddate"]).format_extend('yyyy-MM-dd hh:mm:ss'));
				  $('#lendaddhidden').removeClass('hidden');
				  $("#lendadddatecolor").css("color",lenfifo["addusernamecolor"]);
				}
				if(lendtrnDate["update"]==null||lendtrnDate["update"]==''){
				 	$('#lenduphidden').addClass('hidden');
				}else{
				 	$("#lendupduser").text(lenfifo["upduser"]);//更新者
				    $("#lendupdate").text(new Date(lendtrnDate["update"]).format_extend('yyyy-MM-dd hh:mm:ss'));
				    $('#lenduphidden').removeClass('hidden');
				    $("#lendupdusercolor").css("color",lenfifo["upusernamecolor"]);
				}
				if(lendtrnDate["confirmdate"]==null||lendtrnDate["confirmdate"]==''){
				 	$('#lendconfirmhidden').addClass('hidden');
				 	$('#lendcancelconfirmhidden').addClass('hidden');
				}else{
				 	$("#lendadminuserlend").text(lenfifo["adminuserlend"]);//承认者
					$("#lendconfirmdate").text(new Date(lendtrnDate["confirmdate"]).format_extend('yyyy-MM-dd hh:mm:ss'));
					$('#lendconfirmhidden').removeClass('hidden');
					$("#lendadminuserlendcolor").css("color",lenfifo["adminuserlendcolor"]);
				} 
				if(lendtrnDate["cancelconfirmdate"]==null||lendtrnDate["cancelconfirmdate"]==''){
				 	$('#lendcancelconfirmhidden').addClass('hidden');
				}else{
				 	$("#lendcanceluserlend").text(lenfifo["canceluserlend"]);//承认取消者
				    $("#lendcancelconfirmdate").text(new Date(lendtrnDate["cancelconfirmdate"]).format_extend('yyyy-MM-dd hh:mm:ss'));
					$('#lendcancelconfirmhidden').removeClass('hidden');
					$("#lendcanceluserlendcolor").css("color",lenfifo["canceluserlenddcolor"]);
				}
                if(lenfifo["adddate"]==lenfifo["update"]){
                	$('#lenduphidden').addClass('hidden');
                	$('#lendaddpdfhidden').removeClass('hidden');
                }else{
                	$('#lenduphidden').removeClass('hidden');
                	$('#lendaddpdfhidden').addClass('hidden');
                	
                }
                
                if(lenfifo["lendFromJpp"] == 1)
                {
                	$('#lendaddhidden .min-hei-60').addClass('hidden');
                	$('#lenduphidden .min-hei-60').addClass('hidden');
                	$('#lendconfirmhidden .min-hei-60').addClass('hidden');
                	$('#lendcancelconfirmhidden .min-hei-60').addClass('hidden');
                }
                
                //if(costList["costfinishflg"]=="1"){
                if(lenfifo["status"]=="3"){
		            //原价终了
		             $('#lendflagimg').attr('src', '../public/images/job3.4.png');
		        }
		        else if(lenfifo["confirmdate"]!=null){
                	//承认
                	 $('#lendflagimg').attr('src', '../public/images/job3.3.png');
                }
		        else if(lenfifo["blflg"]=="1"||lenfifo["status"]=="2"){
		            //保留
		            $('#lendflagimg').attr('src', '../public/images/job3.2.png');
		        }
		        else if(lenfifo["adddate"]!=null){
		            //登录
		            $('#lendflagimg').attr('src', '../public/images/job3.1.png');
		        }
           		$('.iconfont.icon-xiala').unbind('click');
		       	text_over_detail();
		       	var detail_woer_obj = $('.workerD_liti').find('.minute_per').not('.hidden');
		        flag_worker_over(detail_woer_obj);
		       	var border_last = $('.workerD_liti').find('.minute_per').not('.hidden').last();
		        border_last.css('border-right','1px solid');
           }else{
	           	showErrorFunHandler(data[$.getRequestMetaName()].result,"ERROR","ERROR");
	           }
	    },
       error:function(data){
       		showErrorHandler("CONNECTION_SERVER_ERROR","ERROR","ERROR");
       }
	}); 
}
//振替
function searchTranInfo(tranno){
	//为pdf的input——no重新赋值
	input_no=tranno;
	inputZt = tranno;
	if($('#tran_i').css('display') != 'inline'){
		$("#tran_i").show();
		$('#collapseTwo').addClass('in');
		$('#tran_i_div').addClass('rotate-180');
	}	
	if($('#tran_i_div').hasClass('rotate-180')){
		$('#tran_i').click();		
	}	
	var jobcd =urlPars.parm("jobcd");
	$("#jobcd").text(jobcd);
    var path = $.getAjaxPath()+"TrantrnQuery";	
	var pars = {trantrn:{job_cd:jobcd,input_no:tranno}};
	$.ajax({
		url:path,
		data:JSON.stringify(pars),
		headers: {"requestID":$.getRequestID(),"requestName":$.getRequestNameByJcZH()},
		success:function(data){
          if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
 		       //var cost =data[$.getRequestDataName()]['cost'][0];
 		      // var Invoiceintrn =data[$.getRequestDataName()]['Invoiceintrn'][0];
 		     $.resetRequestID(data[$.getRequestUserInfoName()].requestID);
 			  if(data[$.getRequestDataName()]['TrantrnInput']['trantrnList'].length>0){
 		      	  trantrnlist =data[$.getRequestDataName()]['TrantrnInput']['trantrnList'][0];
 		       }
 			  var trantrnDate =data[$.getRequestDataName()]['TrantrnInput']['trantrnDate'];
 			  var str ="";
 			  var Astr ="";
 			  var Pstr ="";
 			  //标签
 			  if(data[$.getRequestDataName()]['TrantrnInput']['lableList'].length>0){
 			  	var TranlableList =data[$.getRequestDataName()]['TrantrnInput']['lableList'];
 			  	for(var i =0 ;i<TranlableList.length;i++){
 			  		if(TranlableList[i]['lablelevel']=='0'){
 			  		   Astr +='<button class="btn btn-success  log_job" name="salescategoryRegistration_login" style="background-color: rgb(1,176,241);border: none;height: 20px;line-height: 10px;">'+TranlableList[i]['labletext']+'</button>';	
 			  		}else if(TranlableList[i]['lablelevel']=='1'){
 			  		   Pstr +='<button class="btn btn-success  log_job" name="salescategoryRegistration_login" style="background-color: rgb(250,121,34);border: none;height: 20px;line-height: 10px;">'+TranlableList[i]['labletext']+'</button>';
 			  		}
 			  	}
 		      	 str=Pstr+Astr;
 		       }
 			   $('#tranlable').empty();
 			   $('#tranlable').append(str);  	
 			   $("#tran_date").text(trantrnlist['tran_date']!=undefined&&['tran_date']!=null?trantrnlist['tran_date']:'');
 			   $("#tran_amt").text(trantrnlist['tran_amt']!=undefined&&trantrnlist['tran_amt']!=null?formatNumber(trantrnlist['tran_amt']):'');
 			   $("#jifei").text(trantrnlist['itmname']!=undefined&&trantrnlist['itmname']!=null?setJobdetailForeignMoneyCode(trantrnlist,"itemname")['laug']:'');
 			   $("#tran_name").text(trantrnlist['tran_name']!=undefined&&trantrnlist['tran_name']!=null?trantrnlist['tran_name']:'');
 			   $("#tranremark pre").html(trantrnlist['remark']!=undefined&&trantrnlist['remark']!=null?trantrnlist['remark']:'');
 			   
 			  $("#tranaddhidden .min-hei-60").removeClass("hidden");
			  $("#tranupdhidden .min-hei-60").removeClass("hidden");
			  $("#tranconfirmhidden .min-hei-60").removeClass("hidden");
			  $("#trancancelhidden .min-hei-60").removeClass("hidden");
 			   
 			   if(trantrnDate['adddate']==null||trantrnDate['adddate']==''){
 			   		$("#tranaddhidden").addClass("hidden");
 			   		$("#tranupdhidden").addClass("hidden");
 			   }else{
 			   		$("#tranaddusername").text(trantrnlist['addusername']);//登录者
 			    	$("#tranadddate").text(new Date(trantrnDate['adddate']).format_extend('yyyy-MM-dd hh:mm:ss'));
 			    	$("#tranaddhidden").removeClass("hidden");
 			    	$("#tranaddusernamecolor").css("color",trantrnlist["addusernamecolor"]);
 			   }
 			   if(trantrnDate['upddate']==null||trantrnDate['upddate']==''){
 			   		$("#tranupdhidden").addClass("hidden");
 			   }else{
 			   		$("#tranupdateuser").text(trantrnlist['updateuser']);//更新者
	           		$("#tranupdate").text(new Date(trantrnDate['upddate']).format_extend('yyyy-MM-dd hh:mm:ss'));
	           		$("#tranupdhidden").removeClass("hidden");
	           		$("#tranupdateusercolor").css("color",trantrnlist["upusernamecolor"]);
 			   }
	           if(trantrnDate['confirmdate']==null||trantrnDate['confirmdate']==''){
 			   		$("#tranconfirmhidden").addClass("hidden");
 			   		$("#trancancelhidden").addClass("hidden");
 			   }else{
 			   		$("#tranconfirUserName").text(trantrnlist['confirusername']);//承认者
	           		$("#tranconfirmdate").text(new Date(trantrnDate['confirmdate']).format_extend('yyyy-MM-dd hh:mm:ss'));
	           		$("#tranconfirmhidden").removeClass("hidden");
	           		$("#tranconfirUserNamecolor").css("color",trantrnlist["confirusernamecolor"]);
 			   }
 			    if(trantrnDate['cancelconfirmdate']==null||trantrnDate['cancelconfirmdate']==''){
 			   		$("#trancancelhidden").addClass("hidden");
 			   }else{
 			   		$("#cancelUserName").text(trantrnlist['cancelUserName']);//承认取消
	             	$("#cancel_confirm_date").text(new Date(trantrnDate['cancelconfirmdate']).format_extend('yyyy-MM-dd hh:mm:ss'));
 			   		$("#trancancelhidden").removeClass("hidden");
 			   		$("#cancelUserNamecolor").css("color",trantrnlist["cancelUserNamecolor"]);
 			   }
 			   //若登录时间等于更新时间,更新不显示
 			   if(trantrnlist['adddate']==trantrnlist['upddate']){
 			   	$("#tranupdhidden").addClass("hidden");
 			   	$("#tranaddpdfhidden").removeClass("hidden");
 			   }else{
 			   	$("#tranaddpdfhidden").addClass("hidden");
 			   	$("#tranupdhidden").removeClass("hidden");
 			   }
 			   
 			   if(trantrnlist['tranFromJpp'] == 1)
 			   {
 				  $("#tranaddhidden .min-hei-60").addClass("hidden");
 				  $("#tranupdhidden .min-hei-60").addClass("hidden");
 				  $("#tranconfirmhidden .min-hei-60").addClass("hidden");
 				  $("#trancancelhidden .min-hei-60").addClass("hidden");
 			   }
 			   //状态位图片
 			   	if(trantrnlist["tran_status"]=="2"){
		            //原价终了
		             $('#tranflagimg').attr('src', '../public/images/job4.3.png');
		        }else if(trantrnlist['confirmdate']!=null){
                	//承认
                	 $('#tranflagimg').attr('src', '../public/images/job4.2.png');
                }else if(trantrnlist['adddate']!=null){
		            //保留
		            $('#tranflagimg').attr('src', '../public/images/job4.1.png');
		        } 
	           $('.iconfont.icon-xiala').unbind('click');
		       	text_over_detail();
		       	var detail_woer_obj = $('.workerD_zhenti').find('.minute_per').not('.hidden');
		        flag_worker_over(detail_woer_obj);
	            var border_last = $('.workerD_zhenti').find('.minute_per').not('.hidden').last();
		        border_last.css('border-right','1px solid');
          }else{
	           	showErrorFunHandler(data[$.getRequestMetaName()].result,"ERROR","ERROR");
	           }
	     },
       error:function(data){
       		showErrorHandler("CONNECTION_SERVER_ERROR","ERROR","ERROR");
       }
	}); 
}

	
function selCostList(){
	var jobcd =urlPars.parm("jobcd");
	var path = $.getAjaxPath() + "getCostList";
	var pars = {
		costListVo: {
			jobcd: jobcd,
			searchType: 1
		}
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
				//数据填充到表格中
				var dataColumns = data.data['costListVo']
				for(var i =0;i<dataColumns.length;i++){
					if(dataColumns[i]['confirmdate']==undefined||dataColumns[i]['confirmdate']==null){
						dataColumns[i]['confirmdate']="";
					}
				}
				initDataGridHandler("cost_list_job_details", 9999999, null, 'top', false, 'isHasFn', dataColumns, true);
				var sum = 0;
				for(var i = 0; i < dataColumns.length; i++) {
					sum += Number(dataColumns[i]['amt']);
				}
				for(var k = 0; k < dataColumns.length; k++) {
					dataColumns[k]['sumCost'] = sum;
					dataColumns[k]['saleamt'] = formatNumber(dataColumns[k]['saleamt']);
					dataColumns[k]['confirmdate']= new Date(dataColumns[k]['confirmdate']).format_extend('yyyy-MM-dd');
					dataColumns[k]['payreqdate']= new Date(dataColumns[k]['payreqdate']==null?"":dataColumns[k]['payreqdate']).format_extend('yyyy-MM-dd');
					dataColumns[k].amt = formatNumber(dataColumns[k].amt);
				}
			    var joblist = loopFunForjobdetail(dataColumns,[data.data['costFlg']],['statuscd']);
				$("#cost_list_job_details").datagrid({loadFilter: pagerFilter}).datagrid("loadData", joblist);
			} else {
				showErrorFunHandler(data[$.getRequestMetaName()].result, "ERROR", "ERROR");
			}
		},
		error: function(data) {
			showErrorHandler("CONNECTION_SERVER_ERROR", "ERROR", "ERROR");
		}
	});
}

function setMoneyCode(personMessage) {
	//获取登陆者货
	var toplange = $('#language').val();
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

	$('.money-code').text(val);
	sessionStorage.setItem("localMoneyCode", val);
	
	
}

/*
 * 设置本国货币
 */
function setJobdetailMoneyCode(personMessage) {
	
	//获取登陆者货
	var toplange = $('#language').val();
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
	$('.money-code').text(val);
	$('.pay_d_t').text(val);
	$('.plan_foreign_money_code').text(val);
	$('.real_foreign_money_code').text(val);
	$('.pay_foreign_money_code').text(val);
	$('.cost_foreign_money_code').text(val);
	 $(".lend_foreign_code").text(val);
}
function setJobdetailForeignMoneyCode(personMessage,item,foreigntype) {
		//获取登陆者货
	    var toplange = $('#language').val();
		var laug = '';
		var point = '';
		//如果foreigntype有值,代表外货币种符号,需要循环
		 if(foreigntype!=undefined&&foreigntype!=null &&foreigntype!=''){//如果foreigntype时空字符串,代表没有填写外货,是本国货币,不代表发票国际化.
			for(var i=0;i<personMessage.length;i++){
			         	if(personMessage[i]['itemcd']==foreigntype){
							switch(toplange) {
								case "jp":
									laug = personMessage[i][item+'jp'];
									point = personMessage[i]['itmvalue'];
									break;
								case "zc":
									laug = personMessage[i]['itmname'];
									point = personMessage[i]['itmvalue'];
									break;
								case "zt":
									laug = personMessage[i][item+'hk'];
									point = personMessage[i]['itmvalue'];
									break;
								case "en":
									laug = personMessage[i][item+'en'];
									point = personMessage[i]['itmvalue'];
									break;
								};
			        	}
			}
		}else{
			//如果foreigntype无值,代表发票或者立替种类,不需要循环
			switch(toplange) {
					case "jp":
						laug = personMessage[item+'jp'];
						//laug = JSON.parse($.getMoneyUnit()).moneyjp;
						break;
					case "zc":
						laug = personMessage[item];
						//laug = JSON.parse($.getMoneyUnit()).moneyzc;
						break;
					case "zt":
						laug = personMessage[item+'hk'];
						//laug = JSON.parse($.getMoneyUnit()).moneyzt;
						break;
					case "en":
						laug = personMessage[item+'en'];
						//laug = JSON.parse($.getMoneyUnit()).moneyen;
						break;
				};
		}
		return {"laug":laug,"point":point};
	//return val;
}









//tooltip语言国家化
function toolTipLanguage(language,massageone,massagetwo){
	if(language == undefined || language == ''){
		language = localStorage.getItem('language');
	}
	switch (language){
		case 'jp':
			var toolTipO = massageone;
			var toolTipT = massagetwo;
			break;
		case 'zc':
			var toolTipO = massageone;
			var toolTipT = massagetwo;
			break;
		case 'en':
			var toolTipO = massageone;
			var toolTipT = massagetwo;
			break;
		case 'zt':
			var toolTipO = massageone;
			var toolTipT = massagetwo;
			break;
	}	
	$('.icon-tishi.order_cost_iconO').tooltip({
	    position: 'right',
	    content: '<span style=\"color:#fff\">'+toolTipO+'</span>',
	    onShow: function(){
			$(this).tooltip('tip').css({
				backgroundColor: '#666',
				borderColor: '#666'
			});
	    }
	});
	$('.icon-tishi.order_cost_iconT').tooltip({
	    position: 'right',
	    content: '<span style=\"color:#fff\">'+toolTipT+'</span>',
	    onShow: function(){
			$(this).tooltip('tip').css({
				backgroundColor: '#666',
				borderColor: '#666'
			});
	    }
	});
	setInvoiceTip();
}
function setInoviceTipByOrderInfo()
{
	var pay = this.pay;
	var cost = this.costList;
	var language = localStorage.getItem('language');
    if(language=="jp"){
  	  if(pay.invoicetypenamejp!=undefined&&pay.invoicetypenamejp!=undefined){
  			mes1 =pay.invoicetypenamejp+ " "+pay.invoicetypenamejp;
  	  }
  	  else{
  		 mes1=""
  	  }
   		mes2 =cost.invoicetypenamejp+" "+cost.invoicetextnamejp;
   	}else if(language=="zc"){
   		if(pay.invoicetypename!=undefined&&pay.invoicetypename!=undefined){
  			mes1 =pay.invoicetypename+ " "+pay.invoicetypename;
  	  }
  	  else{
  		 mes1=""
  	  }
   		mes2 =cost.invoicetypename+" "+cost.invoicetextname;
   	}else if(language=="en"){
   		if(pay.invoicetypenameen!=undefined&&pay.invoicetypenameen!=undefined){
  			mes1 =pay.invoicetypenameen+ " "+pay.invoicetypenameen;
  	  }
  	  else{
  		 mes1=""
  	  }
   		mes2 =cost.invoicetypenameen+" "+cost.invoicetextnameen;
   	}else if(language=="zt"){
   		if(pay.invoicetypenamehk!=undefined&&pay.invoicetypenamehk!=undefined){
  			mes1 =pay.invoicetypenamehk+ " "+pay.invoicetypenamehk;
  	  }
  	  else{
  		 mes1=""
  	  }
   		mes2 =cost.invoicetypenamehk+" "+cost.invoicetextnamehk;
   	}
    toolTipLanguage("",mes1,mes2);
}
function setInvoiceTip(pay,cost,paydeutext,costdeutext)
{
	$('.invoiceTip').tooltip({
	    position: 'right',
	    content: '<span style=\"color:#fff\">'+part_language_change_new("PAY_UPDATE_INFO")+'</span>',
	    onShow: function(){
			$(this).tooltip('tip').css({
				backgroundColor: '#666',
				borderColor: '#666'
			});
	    }
	});
}
function OutOfPDF(pdfname){
	var job_cd =urlPars.parm("jobcd");
	if(pdfname=="deboursCreate"){
		OutPutPdfHandler(job_cd,'',inputLt,'deboursCreate',"1") //立替更新
	}else if(pdfname=="deboursCreate"){
		OutPutPdfHandler(job_cd,'',inputLt,'deboursCreate',"1") //立替登录
	}else if(pdfname=="confirmDebours"){
		OutPutPdfHandler(job_cd,'',inputLt,'confirmDebours',"1") //立替承认
	}else if(pdfname=="onCostCreate"){
		OutPutPdfHandler(job_cd,'',inputZt,'onCostCreate',"1") //振替更新
	}else if(pdfname=="onCostCreate"){
		OutPutPdfHandler(job_cd,'',inputZt,'onCostCreate',"1") //振替登录
	}else if(pdfname=="confirmOncost"){
		OutPutPdfHandler(job_cd,'',inputZt,'confirmOncost',"1") //振替承认
	}else if(pdfname=="jobCreate"){
		OutPutPdfHandler(job_cd,"","","jobCreate","1")//job登录 
	}else if(pdfname=="jobEdit"){
		OutPutPdfHandler(job_cd,"","","jobEdit",'1')//job更新 
	}else if(pdfname=="billOrder"){
		OutPutPdfHandler(job_cd,"","","billOrder","1","","jobDetails");//请求书 
	}else if(pdfname=="invoiceApplication"){
		OutPutPdfHandler(job_cd,"","","invoiceApplication","1","","jobDetails");//发票发行
	}else if(pdfname=="saleAdminPdf"){
		OutPutPdfHandler(job_cd,"","","saleAdminPdf",'1');//卖上承认
	}else if(pdfname=="jobDetailSale"){
		OutPutPdfHandler(job_cd,"","","jobDetailSale","1");//买卖票 
	}else if(pdfname=="payCreate"){
		OutPutPdfHandler(job_cd,'',payno,'payCreate',"1");//支付登录票
	}else if(pdfname=="confirmPay"){
		OutPutPdfHandler(job_cd,'',payno,'confirmPay',"1");//支付承认
	}else if(pdfname=="payRequest"){
		OutPutPdfHandler(job_cd,'',payno,'payRequest',"1");//支付申请
	}else if(pdfname=="reportCreate"){
		OutPutPdfHandler(job_cd,inputZf,"","reportCreate","1","","jobDetails");;//发注书
	}
	
}
function first_img_show() {
	if(!$('#tip_rotat').hasClass('rotate-180')) {
		$("#tip_show").click();
	}
}
function recalculateProfit(planSale,saleAmt,planCost,costTotal,isCostFinsh,costCountNums,point,taxFormatFlg,foreignFormatFlg,reTaxTotal)
{
	var tax = 0.0;
	var tax1 = 0.0;
	var taxTotal = 0.0;
	var profit = 0.0;
	var profitRate = 0.0;
	
	var saleBaseAmt = planSale;
	var costTotalAmt = planCost;
	//实际卖上不为空，使用实际
	if(saleAmt!=""||saleAmt===0)
	{
		saleBaseAmt = parseFloat(saleAmt);
	}
	//是否有成本
	if(costCountNums > 0)
	{
		//使用实际成本
		costTotalAmt = costTotal;
	}else{
		//没有成本并且成本终止录入，实际成本为0
		if(isCostFinsh == 1)
		{
			costTotalAmt = 0.0;
		}
	}
	
	taxTotal = reTaxTotal;
	//营收 = 卖上金额-（原价金额+税金合计）
	profit = floatObj.subtract(parseFloat(saleBaseAmt),floatObj.add(parseFloat(costTotalAmt) , parseFloat(taxTotal)));
	profit = pointFormatHandler(profit,foreignFormatFlg,point);
	//营收率=营收/卖上金额 * 100
	if(saleBaseAmt == 0)
	{
		profitRate = "INF";
	}else{
		profitRate = floatObj.divide(parseFloat(profit),parseFloat(saleBaseAmt));
		profitRate = pointFormatHandler(profitRate,3,4);
		profitRate = floatObj.multiply(profitRate,100);
	}
	return {"profit":profit,"profitRate":profitRate};
}
