$(document).ready(function(){
	bodyh = $(document).height(); //浏览器当前窗口文档的高度 
	navh = bodyh - 135;
	$("#navright").css("height", navh + "px");
})
$(function(){
	$.jobCommon();
	initpaymentinfo();
	//JOB弹出框
//	var saleAddFlag = sessionStorage.getItem("addflg");
//	if(saleAddFlag=='0'){
//	  $.layerShowDiv('job_detail','1000', '650',2,'../jcst/job_detail.shtml');
//	  sessionStorage.clear();
//	}
	$.jobCommon();
	$.layerShowDiv('icon-tianjia1', '400', 'auto', 1, $('.payment_pay'));
	$.layerShowDiv('icon-sheding1','400','auto',1,$('.label_set_t'));
	var eurl =$.getJumpPath()+"common/employ_retrieval.html";
	$.layerShow("searchDD",eurl);
	//标签追加
	$(".add_lable").click(function() {
		addLable("new_lable", "options_lable","1");
	});
	$(".filter-lable").click(function(){
	  var str = $("#lableStr").val();
	  filterLable(str,null);
 	})
	lableShowByPower();
})
var locknum;
var mymoney;
function initpaymentinfo(){
	var input_no =getQueryStringValue('inputno');
	var job_cd =getQueryStringValue('jobcd');
	var flag = getQueryStringValue('flg')
	$('#jobcd').text(job_cd);
	var pdfflag = 'init';
	var path = $.getAjaxPath()+"PaytrnQuery";
	//初始化转圈问题
	if(flag!=0 && flag!=1 && flag!=null){		
		showLockInfoMsgHandler("DATA_IS_NOT_EXIST","/jcst/cost_list.html?view=6");
		return;
	}
	if(flag=='0'){//取消
		$('.payapproval').addClass('hidden');
		$('.payaback').addClass('hidden');
		$('.paycancel').addClass('not_hidden');
		$('.payplandate').datebox({'disabled':true});
		$('.confirmark').addClass('hidden');
		$('#confirmarkClear').removeClass('hidden');
		$('#menu25').removeClass('hidden');
		$('title').attr('name','menu25');
		$('.lastChild').addClass('hidden');
		var language = localStorage.getItem('language');
		localStorage.setItem('language',language);
		execI18n();
		$('.language_conversion').find("[value="+i18nLanguage+"]").prop("selected",true);
		$('.language_conversion').selectpicker('refresh');
		flushToChangeLang();
		SelectObj.setSelectOfLog();	
		path = $.getAjaxPath()+"PaytrnQuerytwo";
	}
	if(flag=='1'){//承认
		$('.payapproval').addClass('not_hidden');
		$('.paycancel').addClass('hidden');
		$('#payment_approval').removeClass('hidden');
		path = $.getAjaxPath()+"PaytrnQuerytwo";
		pdfflag = 'pro';
	}
//	if(input_no==null||input_no==''){
//		showConfirmMsgHandler("NOTFOUND");
//		goBackPageHandler();
//		
//	}
	$('#piinput').text(input_no);
	var allData = {
		"paytrn":{
			input_no:input_no,
			job_cd:job_cd
		}
	}
 		
	$.ajax({
	  type: "POST",
	  url:path,
	  contentType:"application/json",
	  data:JSON.stringify(allData),
	  dataType:"JSON",
	  headers: {"requestID":$.getRequestID(),"requestName":$.getRequestNameByJcZH()},
			success:function(data){			
	           if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
	           	if(data.data.PaytrnInput.length==0){
//	           		showConfirmMsgHandler("NOTFOUND")
					showInfoMsgHandlerstop('PAGE_INIT_FAIL',"ERROR","ERROR",'','')
	           		goBackPageHandler();
	           	}
	           	//初始化转圈问题
	           	if(data.data.clmst==null||data.data.PaytrnInput.paytrnList.length==0||data.data.PaytrnInput.costtrnList.length==0){
	            	$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
	            	showLockInfoMsgHandler("DATA_IS_NOT_EXIST","/jcst/cost_list.html?view=6");
		           	return false;
	            }
	           	objStorage.personMoneyCode = data["userInfo"];
	           	//页面设置默认货币
	           	
				setPersonMoneyCode();
				//本国货币保留小数位数
				var len = data["userInfo"].pointNumber;
				//外国货币保留小数位数
				var foreignlen = data.data.PaytrnInput.paytrnList[0].foreignlen;
				var foreignlentwo = data.data.PaytrnInput.costtrnList[0].foreignlen;
//				$('.rmb').text(data.data.PaytrnInput.paytrnList[0].PAYFOREIGNTYPE);
				var payforeigntype = data.data.PaytrnInput.paytrnList[0].payforeigntype;
				var costforeigntype = data.data.PaytrnInput.costtrnList[0].costforeigntype;
				locknum = data.data.PaytrnInput.paytrnList[0].lock_flg;
				if(payforeigntype==null || payforeigntype==''){
					$('.rmbspone').text(mymoney);
					foreignlen=len;
				}else{
					$('.rmbspone').text(payforeigntype);
				}
				if(costforeigntype==null || costforeigntype==''){
					$('.rmbsptwo').text(mymoney);
					foreignlentwo = len;
				}else{
					$('.rmbsptwo').text(costforeigntype);
				}
	           	var payflag=data.data.PaytrnInput.skip.pay;
	           	if(payflag=='1'){
	           		$('.payaback').addClass('hidden');
	           	}
				$('.cost_name').text(data.data.PaytrnInput.costtrnList[0].cost_name);//案件名
				$("#job_name").text(data.data.PaytrnInput.costtrnList[0].cost_name);//案件名
				$("#job_namecost").text(data.data.PaytrnInput.costtrnList[0].cost_name);//案件名
				$(".zhifu #job_name").text(data.data.PaytrnInput.costtrnList[0].cost_name);//案件名
				$('.cost_type').text(data.data.PaytrnInput.costtrnList[0].cost_type);//项目分类
				$('.payee_cd').text(data.data.PaytrnInput.costtrnList[0].payee_cd);//发注先
				$('.pay_dlday').text(data.data.PaytrnInput.paytrnList[0].pay_dlyday);//交付日
				$('.pay_hope_date').text(data.data.PaytrnInput.paytrnList[0].pay_hope_date);//支払希望日
				if(pdfflag = 'init'){
					//获取PDF出力是否选中		
					if(data.data.PaytrnInput.pdfflagcri==1){
					$("#payRequestPdf").attr("checked",true);
					}
				}
				if(pdfflag = 'pro'){
					//获取PDF出力是否选中		
					if(data.data.PaytrnInput.pdfflagpro==1){
					$("#payConfirmPdf").attr("checked",true);
					}
				}
				//小人颜色
				var jobupdusercolor = data[$.getRequestDataName()]['CLDIV'][0]['updusercolorv'];
				if(jobupdusercolor!=null&&jobupdusercolor!=''){
					$("#jobupdusercolor").css("color",data[$.getRequestDataName()]['CLDIV'][0]['updusercolorv']);
				}
				//$('.confirmark').text(data.data.PaytrnInput.paytrnList[0].PAYREQREMARK);//备注
				if(data.data.PaytrnInput.invoiceintrnList[0]!=undefined){
					$('#payremark pre').html(data.data.PaytrnInput.invoiceintrnList[0].REMARK);//备注
					//支払補足情報
					$('.invoicedate').text(data.data.PaytrnInput.invoiceintrnList[0].INVOICE_DATE);//発票受領日
					$('.incoiceno').text(data.data.PaytrnInput.invoiceintrnList[0].INVOICE_NO);//発票番号
					$('#mark_info pre').html(data.data.PaytrnInput.invoiceintrnList[0].REMARK);//备注
				}
				
				$('.payplandate').datebox('setValue',data.data.PaytrnInput.paytrnList[0].pay_plan_date)//支払予定日
				//支付预定日自动赋值
				//付款预定日-自动设定0：不自动；1：自动 
				var pay_auto_setting = data.data.clmst.pay_auto_setting;
				if(pay_auto_setting==1 && flag=='1'){//支付承认+自动设定
					var npdate = data.data.PaytrnInput.paytrnList[0].PAY_DLYDAY;//纳品日
					var zfdate = data.data.clmst.pay_add_date;//支付情报登陆日
					//自动支付日类型：0：支付信息登陆日；1：交付日
					var pay_date_flg = data.data.clmst.pay_date_flg;
					var pay_auto_month = data.data.clmst.pay_auto_month;
					var pay_auto_day = data.data.clmst.pay_auto_day;
					if(pay_date_flg==0){
						autoDate(zfdate,pay_auto_month,pay_auto_day);
					}else{
						autoDate(npdate,pay_auto_month,pay_auto_day);
					}
					
				}
				
				//列表模块
				var differenceone = "0";//差额
				var differencetwo = "0";//差额
				var differencethree = "0";//差额
				$('.payamt').text(formatNumber(Number(data.data.PaytrnInput.paytrnList[0].pay_amt).toFixed(len)));//発注原価
				$('.costrmb').text(formatNumber(Number(data.data.PaytrnInput.costtrnList[0].cost_rmb).toFixed(len)));//発注原価
				differenceone = floatObj.subtract(data.data.PaytrnInput.paytrnList[0].pay_amt,data.data.PaytrnInput.costtrnList[0].cost_rmb,len);

				if(differenceone==0){
					$('#settab1').removeClass('icon-xiajiang');
					$('#settab1').addClass('icon-shang');
					$('#settab1').parent().css('transform','rotate(90deg)');
					//$('#settab1').css('color','red');
				
				}
				if(differenceone>0){
					$('#settab1').parent().addClass('deg180')
					$('#settab1').css('color','#fa0101');
				}
				$('.differenceone').text(formatNumber(differenceone));//差额
				$('#picost').text(data.data.PaytrnInput.paytrnList[0].cost_no)
				$('.payvatamt').text(formatNumber(Number(data.data.PaytrnInput.paytrnList[0].pay_vat_amt).toFixed(len)));//增值税
				$('.costvatamt').text(formatNumber(Number(data.data.PaytrnInput.costtrnList[0].cost_vat_amt).toFixed(len)));//增值税
				differencetwo=floatObj.subtract(data.data.PaytrnInput.paytrnList[0].pay_vat_amt,data.data.PaytrnInput.costtrnList[0].cost_vat_amt,len);
				if(differencetwo==0){
					$('#settab2').removeClass('icon-xiajiang');
					$('#settab2').addClass('icon-shang');
					$('#settab2').parent().css('transform','rotate(90deg)');
					//$('#settab2').css('color','red');
				
				}
				if(differencetwo>0){
					$('#settab2').parent().addClass('deg180')
					$('#settab2').css('color','#fa0101');
				}
				$('.differencetwo').text(formatNumber(differencetwo));//差额
				$('.payrmb').text(formatNumber(Number(data.data.PaytrnInput.paytrnList[0].pay_rmb).toFixed(len)));//発注金額(税込)
				$('.costpayamt').text(formatNumber(Number(data.data.PaytrnInput.costtrnList[0].cost_pay_amt).toFixed(len)));//発注金額(税込)
				differencethree=floatObj.subtract(data.data.PaytrnInput.paytrnList[0].pay_rmb,data.data.PaytrnInput.costtrnList[0].cost_pay_amt,len);
				if(differencethree==0){
					$('#settab3').removeClass('icon-xiajiang');
					$('#settab3').addClass('icon-shang');
					$('#settab3').parent().css('transform','rotate(90deg)');
					//$('#settab3').css('color','red');
				
				}
				if(differencethree>0){
					$('#settab3').parent().addClass('deg180')
					$('#settab3').css('color','#fa0101');
				}
				$('.differencethree').text(formatNumber(differencethree));//差额
				//承认取消的备注赋值
				$('#confirmarkClear #confirmark pre').html(data.data.PaytrnInput.paytrnList[0].confirmark);
				//支払金額模块
				$('.payrmbpercent').text(accMul(data.data.PaytrnInput.paytrnList[0].pay_rate,100));//增值税率
				//$('.payforeignamt').text(formatNumber(Number(data.data.PaytrnInput.paytrnList[0].pay_foreign_amt).toFixed(foreignlen)));//入力金額
				$('.payforeignamt').text(pointFormatHandler(data.data.PaytrnInput.paytrnList[0].pay_foreign_amt,2,foreignlen));//入力金額
				saleIsHave(data.data.PaytrnInput.paytrnList[0].pay_ishave,'pay')//卖上是否税入;0：税拔；1：税入；
				//発注金額模块
				$('#picost').text(data.data.PaytrnInput.costtrnList[0].cost_no);//発注编号
				$('.orderdate').text(data.data.PaytrnInput.costtrnList[0].order_date);//発注日
				$('.plandlvday').text(data.data.PaytrnInput.costtrnList[0].plan_dlvday);//预计交付日
				$('.costrate').text(accMul(data.data.PaytrnInput.costtrnList[0].cost_rate,100));//增值税率
				//$('.costforeignamt').text(formatNumber(Number(data.data.PaytrnInput.costtrnList[0].cost_foreign_amt).toFixed(foreignlen)));//入力金額
				$('.costforeignamt').text(pointFormatHandler(data.data.PaytrnInput.costtrnList[0].cost_foreign_amt,2,foreignlentwo));//入力金額
				saleIsHave(data.data.PaytrnInput.costtrnList[0].cost_ishave,'cost')//卖上是否税入;0：税拔；1：税入；
				
				//発票
				var deduction_flg=getGridLanguage("deductionflg",0);//不扣除
				if(data.data.PaytrnInput.paytrnList[0].deduction_flg=='1'){
					deduction_flg=getGridLanguage("deductionflg",1);
				}
				var deduction_flg_cost=getGridLanguage("deductionflg",0);//不扣除
				if(data.data.PaytrnInput.costtrnList[0].deduction_flg=='1'){
					deduction_flg_cost=getGridLanguage("deductionflg",1);
				}
				//支払補足情報
//				$('.invoicedate').text(data.data.PaytrnInput.invoiceintrnList[0].INVOICE_DATE);//発票受領日
//				$('.incoiceno').text(data.data.PaytrnInput.invoiceintrnList[0].INVOICE_NO);//発票番号
				var languagetext = $('#language').val();
				switch (languagetext){
					case 'jp':
						data.data.PaytrnInput.paytrnList[0].invoice_type = data.data.PaytrnInput.paytrnList[0].invoice_type_jp;
						data.data.PaytrnInput.paytrnList[0].invoice_text = data.data.PaytrnInput.paytrnList[0].invoice_text_jp;
						data.data.PaytrnInput.costtrnList[0].invoice_text = data.data.PaytrnInput.costtrnList[0].invoice_text_jp;
						data.data.PaytrnInput.costtrnList[0].invoice_type = data.data.PaytrnInput.costtrnList[0].invoice_type_jp;
						break;
					case 'en':
						data.data.PaytrnInput.paytrnList[0].invoice_type = data.data.PaytrnInput.paytrnList[0].invoice_type_en;
						data.data.PaytrnInput.paytrnList[0].invoice_text = data.data.PaytrnInput.paytrnList[0].invoice_text_en;
						data.data.PaytrnInput.costtrnList[0].invoice_text = data.data.PaytrnInput.costtrnList[0].invoice_text_en;
						data.data.PaytrnInput.costtrnList[0].invoice_type = data.data.PaytrnInput.costtrnList[0].invoice_type_en;
						break;
					case 'zt':
						data.data.PaytrnInput.paytrnList[0].invoice_type = data.data.PaytrnInput.paytrnList[0].invoice_type_hk;
						data.data.PaytrnInput.paytrnList[0].invoice_text = data.data.PaytrnInput.paytrnList[0].invoice_text_hk;
						data.data.PaytrnInput.costtrnList[0].invoice_text = data.data.PaytrnInput.costtrnList[0].invoice_text_hk;
						data.data.PaytrnInput.costtrnList[0].invoice_type = data.data.PaytrnInput.costtrnList[0].invoice_type_hk;
						break;
				}
				$('.invoicetype').text(data.data.PaytrnInput.paytrnList[0].invoice_type);//発票種類
				$('.invoicetext').text(data.data.PaytrnInput.paytrnList[0].invoice_text);//発票内容
				$('.deduction').text(deduction_flg);//控除可否				
				var massageone = data.data.PaytrnInput.paytrnList[0].invoice_type+' '+data.data.PaytrnInput.paytrnList[0].invoice_text+' '+deduction_flg;
				var massagetwo = data.data.PaytrnInput.costtrnList[0].invoice_type+' '+data.data.PaytrnInput.costtrnList[0].invoice_text+' '+deduction_flg_cost;
				$('.payrete').text(accMul(data.data.PaytrnInput.paytrnList[0].pay_rate,100));//增值税率
//				$('#mark_info pre').html(data.data.PaytrnInput.invoiceintrnList[0].REMARK);//备注				
	            //job信息区块，的得意先，与更新时间
				$("#divnm").text(data[$.getRequestDataName()]['CLDIV'][0]['divnm']);
				$("#upddate").text(data[$.getRequestDataName()]['CLDIV'][0]['upddate']);
				$("#username").text(data[$.getRequestDataName()]['CLDIV'][0]['username']);
				sessionStorage.setItem("addflg",data[$.getRequestDataName()]['CLDIV'][0]['saleAddFlag']);//壳上登录状态为1：已经登录  2：未登录
    			//$("#jobcd").text(data[$.getRequestDataName()]['CLDIV'][0]['jobcd']);
				$("#sumamt").text(data[$.getRequestDataName()]['sumcost'][0]['sumamt']);
				$("#updatetime").text(data[$.getRequestDataName()]['sumcost'][0]['updatetime']);
				$("#costupdname").text(data[$.getRequestDataName()]['sumcost'][0]['costupdname']);
				//支払情報
				$(".fukuan #sale_remark1 pre").html(data[$.getRequestDataName()]['remark']['REMARK']);
				$(".fukuan #sale_uper").text(data[$.getRequestDataName()]['remark']['UPUSER']);
				$(".fukuan #sale_uptime").text(data[$.getRequestDataName()]['remark']['UPDATEPAY']);
				//支付申请区块
				$(".zhifu #sale_remark1 pre").html(data[$.getRequestDataName()]['remark']['PAYREQREMARK']);
				$(".zhifu #sale_uper").text(data[$.getRequestDataName()]['remark']['PAYREQEMP']);
				$(".zhifu #sale_uptime").text(data[$.getRequestDataName()]['remark']['PAYREQDATE']);
				//外发信息区块
				$("#job_remark pre").html(data[$.getRequestDataName()]['remark']['COST_REMARK']);
				$("#job_upname").text(data[$.getRequestDataName()]['remark']['COSTUPUSER']);
				$("#job_uptime").text(data[$.getRequestDataName()]['remark']['COSTUPDATE']);
				//小人颜色
				var jobupdusercolor = data[$.getRequestDataName()]['CLDIV'][0]['updusercolorv'];
				if(jobupdusercolor!=null&&jobupdusercolor!=''){
					$("#jobupdusercolor").css("color",data[$.getRequestDataName()]['CLDIV'][0]['updusercolorv']);
				}
				$(".zhifu .colorone").css("color",data[$.getRequestDataName()]['remark']['payreqempcolor']);
				$(".fukuan .colorone").css("color",data[$.getRequestDataName()]['remark']['upusercolor']);
				$(".colorthree").css("color",data[$.getRequestDataName()]['remark']['costupcolor']);
				//标签数据重构
				labelToMySelect(data.data.PaytrnInput.list_lable);
				setLableArea(data.data.PaytrnInput.costlable);
				//凭证信息初始化
				
				var rooftrn = data[$.getRequestDataName()]['prooftrn'];
				var str = "";
				var path = "";
				for(var i = 0;i < rooftrn.length;i++){
					path = returnURL(rooftrn[i].PROOF_URL);
					str +='<a onclick="window.open(\''+path+'\')">'+'<li class="s1">'+
							'<span>'+rooftrn[i].PROOF_TITLE+'</span> '+							
							'<input type="hidden" name="" id="" value="'+path+'" />'+
						'</li>'+'</a>';
				}
				
				$('.paument_url').empty();
				$('.paument_url').append($(str));				
	            $.resetRequestID(data[$.getRequestUserInfoName()].requestID);
	            //初始化toolTip语言切换不需要传参数
				toolTipLanguage('',massageone,massagetwo);
				textOverhidden();
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
	           }else{
	           	showErrorFunHandler(data[$.getRequestMetaName()].result,"ERROR","ERROR");
	           }
	       },
	       error:function(data){
	       		showErrorHandler("CONNECTION_SERVER_ERROR","ERROR","ERROR");
	       }
	 });

}
function saleIsHave(flag,type){
	var aDompay = $('.btn-grouppay');
	var aDomcost = $('.btn-groupcost');
	if(flag == 1){
		flag = true;
	}else{
		flag = false;
	}
	if(type=='pay'){//pay模块
		if(flag){
		aDompay.find('label').eq(0).addClass('activeT');
		aDompay.find('label').eq(1).removeClass('activeT');
		}else{
		aDompay.find('label').eq(1).addClass('activeT');
		aDompay.find('label').eq(0).removeClass('activeT');
		}
	}
	else{//cost模块
		if(flag){
		aDomcost.find('label').eq(0).addClass('activeT');
		aDomcost.find('label').eq(1).removeClass('activeT');
		}else{
		aDomcost.find('label').eq(1).addClass('activeT');
		aDomcost.find('label').eq(0).removeClass('activeT');
		}
	}

}
function payapply(){
	var input_no =getQueryStringValue('inputno');
	var job_cd =getQueryStringValue('jobcd');
	var remark = $(".remark").val();
	var costno =$('#picost').text();
	var allData = {
		"paytrn":{
			input_no:input_no,
			payreqremark:remark,
			job_cd:job_cd,
			costno:costno,
			lock_flg:locknum
		},
		payreqremark:remark
	}
	var lableList = getLable();
	allData['lableList'] =lableList;//标签
 	var path = $.getAjaxPath()+"PaytrnApply";	
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
	           	if(data.data==-2){//lockflg验证
		           	showLockInfoMsgHandler("STATUS_VALIDATEPOWER_ERROR","/jcst/cost_list.html?view=6");
		           	return false;
		        }
	           	if(data[$.getRequestDataName()]==-1){
	           		showLockInfoMsgHandler("STATUS_VALIDATEPOWER_ERROR","/jcst/cost_list.html?view=6");
	           		return;
			    }else{
			    	 //插入成功后执行导出pdf
           	 	   if ($('#payRequestPdf').prop("checked")) {

			        OutPutPdfHandler(job_cd,'',input_no,'payRequest',"","","",1);
			       }else{
			        	goCostlist();
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

//付款信息承认
function payapproval(){
	var input_no =getQueryStringValue('inputno');
	var job_cd =getQueryStringValue('jobcd');
	var costno =$('#picost').text();
	var remark = $(".confirmark").val();
	var payplandate = $(".payplandate").val();
	var allData = {
		"paytrn":{
			input_no:input_no,
			confirmark:remark,
			pay_plan_date:payplandate,
			job_cd:job_cd,
			costno:costno,
			lock_flg:locknum
		}
	}
	var lableList = getLable();
	allData['lableList'] =lableList;//标签
 	var path = $.getAjaxPath()+"PaytrnApproval";
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
	           	if(data.data==-2){//lockflg验证
		           	showLockInfoMsgHandler("STATUS_VALIDATEPOWER_ERROR","/jcst/cost_list.html?view=6");
		           	return false;
		        }
	           	if(data.data==-3){//使用日验证
			           	$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
			           	showInfoMsgHandlerstop("DATE_RANGE_ERROR","/jcst/cost_list.html?view=6");
			           	return false;
		            }
	           	if(data[$.getRequestDataName()]==-1){
	           		showLockInfoMsgHandler("STATUS_VALIDATEPOWER_ERROR","/jcst/cost_list.html?view=6");
	           		return;
			    }else{
			    	 //插入成功后执行导出pdf
           	 	   	if ($('#payConfirmPdf').prop("checked")) {

			        OutPutPdfHandler(job_cd,'',input_no,'confirmPay',"","","",1);
			        }else{
			        	goCostlist();
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
//付款信息驳回
function payapprovalback(){
	var msg = "";
	var confirmTitle = $.getConfirmMsgTitle();
	msg = showConfirmMsgHandler("QXCONFIRM");
	$.messager.confirm(confirmTitle,msg, function(r){
		if(r)
		{
			var input_no =getQueryStringValue('inputno');
			var job_cd =getQueryStringValue('jobcd');
			var remark = $(".confirmark").val();
			var costno =$('#picost').text();
			var allData = {
				"paytrn":{
					input_no:input_no,
					confirmark:remark,
					job_cd:job_cd,
					costno:costno,
					lock_flg:locknum
				}
			}
			var lableList = getLable();
			allData['lableList'] =lableList;//标签
		 	var path = $.getAjaxPath()+"PaytrnApprovalBack";	
			$.ajax({
			  type: "POST",
			  url:path,
			  contentType:"application/json",
			  data:JSON.stringify(allData),
			  dataType:"JSON",
			  headers: {"requestID":$.getRequestID(),"requestName":$.getRequestNameByJcZH()},
					success:function(data){
					
			           if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
			           	if(data[$.getRequestDataName()]==-2){
			           		showLockInfoMsgHandler("STATUS_VALIDATEPOWER_ERROR","/jcst/cost_list.html?view=6");
			           		$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
			           		return false;
			           	}
			           	if(data.data==-1){//lockflg验证
				           	$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
				           	showLockInfoMsgHandler("STATUS_VALIDATEPOWER_ERROR","/jcst/cost_list.html?view=6");
				           	return false;
		            	}
			           	$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
		//				showErrorHandler("EXECUTE_SUCCESS","info","info");
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
//支付承认取消
function payapprovalcalcel(){
	var msg = "";
	var confirmTitle = $.getConfirmMsgTitle();
	msg = showConfirmMsgHandler("DELETECONFIRM");
	$.messager.confirm(confirmTitle,msg, function(r){
		if(r)
		{
			var input_no =getQueryStringValue('inputno');
			var job_cd =getQueryStringValue('jobcd');
			var remark = $(".confirmark").val();
			var costno =$('#picost').text();
			var allData = {
				"paytrn":{
					input_no:input_no,
					confirmark:remark,
					job_cd:job_cd,
					costno:costno,
					lock_flg:locknum
				}
			}
			var lableList = getLable();
			allData['lableList'] =lableList;//标签
		 	var path = $.getAjaxPath()+"PaytrnApprovalCancel";	
			$.ajax({
			  type: "POST",
			  url:path,
			  contentType:"application/json",
			  data:JSON.stringify(allData),
			  dataType:"JSON",
			  headers: {"requestID":$.getRequestID(),"requestName":$.getRequestNameByJcZH()},
					success:function(data){
					
			           if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
			           	if(data[$.getRequestDataName()]==-2){
			           		showLockInfoMsgHandler("STATUS_VALIDATEPOWER_ERROR","/jcst/cost_list.html?view=6");
			           		$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
			           		return false;
			           	}
			           	if(data.data==-1){//lockflg验证
				           	$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
				           	showLockInfoMsgHandler("STATUS_VALIDATEPOWER_ERROR","/jcst/cost_list.html?view=6");
				           	return false;
		            	}
			           	$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
		//				showErrorHandler("EXECUTE_SUCCESS","info","info");
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
//	if(!showConfirmMsgHandler("DELETECONFIRM")){
//		return false;
//	}
	
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
	$('.rmb').text(val);
	$('.rmbsp').text(val);
	mymoney = val;
} 

function toolTipLanguage(language,massageone,massagetwo){
	if(language == undefined || language == ''){
		language = localStorage.getItem('language');
	}
	if(massageone == undefined && massagetwo == undefined){
		window.location.reload();
	}
	var toolTipO = massageone;
	var toolTipT = massagetwo;
	
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
}
function isLeapYear(year) {  
	return (year % 4 == 0) && (year % 100 != 0 || year % 400 == 0);
}
function autoDate(date,addMon,addDate){
			var new_date ;
		    var date = new Date(date);
			var year = date.getFullYear();
			var mon = date.getMonth()+1;
			var month = mon+parseInt(addMon);
			if(month>12){
				year++;
				month-=12;
			}
			if("1,3,5,7,8,10,12".indexOf(month)>-1){
					if(addDate>31){
						new_date = year+"-"+month+"-"+getLastDay(year,month);
					}else{
						new_date = year+"-"+month+"-"+addDate;
					}
				}
		    if("4,6,9,11".indexOf(month)>-1){
					if(addDate>30){
						new_date = year+"-"+month+"-"+getLastDay(year,month);
					}else{
						new_date = year+"-"+month+"-"+addDate;
					}
				}
		    if(month=="2"){
					if(isLeapYear(year)){
					  if(addDate>29){
					  	new_date = year+"-"+month+"-"+getLastDay(year,month);
					  }else{
						new_date = year+"-"+month+"-"+addDate;
					}
				   }else{
				   	  if(addDate>28){
					  	new_date = year+"-"+month+"-"+getLastDay(year,month);
					  }else{
						new_date = year+"-"+month+"-"+addDate;
					}
				   }
				}
			
			$('.payplandate').datebox('setValue',new_date)//支払予定日
			
}
function getLastDay(year,month) {         
             var new_year = year;    //取当前的年份          
             var new_month = month++;//取下一个月的第一天，方便计算（最后一天不固定）          
             if(month>12) {         
              new_month -=12;        //月份减          
              new_year++;            //年份增          
             }         
             var new_date = new Date(new_year,new_month,1);                //取当年当月中的第一天          
             return (new Date(new_date.getTime()-1000*60*60*24)).getDate();//获取当月最后一天日期          
}

function getnum(s,num)
{
var num = s+"";
var result = num.substring(0,s.indexOf(".")+s,num);
return result;
}


 
