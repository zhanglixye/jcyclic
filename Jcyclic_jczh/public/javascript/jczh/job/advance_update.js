var aidcd=new Array();
var change_utin=new Array();
var foreignFormatFlg; //外货端数flg，0051 001
var saleVatFormatFlg; //卖上增值税端数flg 0052 001
var pointNumber ='';
var updateinitflag ='';
var fortype;
var foreignLen;
var lenone;
var locknum;
$(document).ready(function(){
	$(".switchot").css("height", '133px');
	$('input[type=checkbox].boostrap-switch').bootstrapSwitch({
		onSwitchChange: function() {
			$(".switchot").css("height", "333px");
			var state1 = $('.judge1').bootstrapSwitch('state');
			var state2 = $('.judge2').bootstrapSwitch('state');
//			console.log(state1);
//			console.log(state2);
			if(state2 == false) {
				$(".switchot").css("height", '133px');
			}
		}
	});
	$('#cost_foreign_type').change(function(){
		resetChooseVal();
	});
	$('#itemList').change(function(){
		resetChooseVal();
	});
	//formater enter事件调用fouseOutv的方法 setTimeout保证事件的执行顺序
//	setTimeout(function(){
//		var target = $('#order_date').textbox('textbox'); 
//		 $(target).unbind("keydown");
//		 $(target).keydown(function (event) {
//		    if (event.keyCode == 13) {
//		        fouseOutv(event,false);
//		    }
//		}); 
//	})
	$("#language").change(function() {
		var defaultData = {};
		if(SelectObj.setDefaultData!=null){
			 defaultData.itemList= SelectObj.setDefaultData.itemList;
			    defaultData.cost_foreign_type= $('#cost_foreign_type').val();
			    SelectObj.setDefaultData = defaultData;
		}
	   
    });
})
function resetChooseVal()
{
	var defaultData = {};
	if($('#itemList').val() != "")
	{
		defaultData.itemList = $('#itemList').val();
	}
    
    if($('#cost_foreign_type').val() != "")
    {
    	defaultData.cost_foreign_type = $('#cost_foreign_type').val();
    }
    SelectObj.setDefaultData = defaultData;
}
$(function(){
	var adtype= $("#advancetype").val()//页面名称
	if(adtype=='up'){//更新页面
	//初始化页面
	initupdateform();	
	};
	if(adtype=='new'){//新增页面
	//初始化页面
	initadvanceform();	
	}
	//初始化job信息
	initAdvance();	
	var eurl =$.getJumpPath()+"common/employ_retrieval.html";
	$.layerShow("searchLend",eurl);
	$.jobCommon();
	$.layerShowDiv('icon-sheding1','400','auto',1,$('.label_set_t'));
	$(".add_lable").click(function() {
		addLable("new_lable", "options_lable","1");
	});
	$(".filter-lable").click(function(){
	  var str = $("#lableStr").val();
	  filterLable(str,null);
 	})
	//限制使用日
	$.dateLimit_f("order_date",getToday(),1);
//	//JOB弹出框
//	var saleAddFlag = sessionStorage.getItem("addflg");
//	if(saleAddFlag=='0'){
//	  $.layerShowDiv('job_detail','1000','650',2,'../jczh/job_detail_nosale.html');
//	  sessionStorage.clear();
//	}
	//外货下拉变更时，计算外发成本
	$('#cost_foreign_type').change(function(){
		calculateOutSoure();	
		resetChooseVal();
		$(".cost_person_code").text($(".cal-cost option:selected").text())//同步金额后面的单位
		setfroeginlength();
	  });  
	$('#switch-state1').on('switchChange.bootstrapSwitch', function(event, state) {
		calculateOutSoure();	
	});  
	//输入金额变更，计算外发成本
	$('#cost_foreign_amt').numberbox({
	  "onChange": function() {
	  calculateOutSoure();	
	  }
	 });
	 //税入，税拔变更，计算外发成本
	$(".cost_rate_click").click(function(e){
		var val = $(e.target).closest('label').find('input').val();
		calculateOutSoure(val);	
	   });  
	//汇率变更，计算外发成本
	$('#saleCurCode').change(function(){
		calculateOutSoure();
	})
//	
//	$('#switch-state1').on('switchChange.bootstrapSwitch', function(event, state) {
//		//calculateOutSoure();
//		setPersonMoneyCode()
//	});
	$('#switch-state1').on('switchChange.bootstrapSwitch', function(event, state) {
		//calculationCost();
		//resetInputArea(0, state);
//		calculateOutSoure();
		if(!state){
			setPersonMoneyCode();
		}
		var itemcd = $('#cost_foreign_type').val();
		var moneyObj = getMoneyCode(itemcd);
		if(moneyObj["val"]!=null&&moneyObj["val"]!=''){
			$('#cost_need_change_money1').text(moneyObj["val"]);
		}
		setfroeginlength();
		
	});
	$(".cal-cost").change(function(){
		$(".cost_person_code").text($(".cal-cost option:selected").text())//同步金额后面的单位
		setfroeginlength();
	})
	//增值税弹窗及设定
	$.layerShowDiv('add_tax_price_on', 'auto', 'auto', 1, $('.add_tax_price'));
	$('#set-tax-price').on('click',function(){
//		var saleCostAmt=$(".saleMoment").val();//卖上入力金额
//		var haveVatFlg= $(".cost_rate_click .active input[name='saleIsHave']").val();//税入税拔，0：税拔；1：税入
//		var reqPayAmt = $(".plan_vat_amt").val();//支付金额
//		var vatAmt=$('#_easyui_textbox_input3').val();//增值税
//		var cost_obj=calculateMoneyByVatChangeHandler(haveVatFlg,saleCostAmt,reqPayAmt,vatAmt);
//		$('.saleMoment').text(cost_obj['saleCostAmt']);
//		$('#cost_tax').text(cost_obj['vatAmt']);
//		$('.plan_vat_amt').text(cost_obj['reqPayAmt']);
		calByTaxChange();

		var index = layer.index;
		layer.close(index);
		var vatAmt = recoveryNumber($('#cost_tax').text());
		var baseRealSaleTax = objStorage.getRealSaleTax();
		if(vatAmt!=baseRealSaleTax){
		 objStorage.realSaleTaxFlg = 1;
		}
	})
	$('.inputDate').datebox('setValue',getNowFormatDate())
	lableShowByPower();
})
function initAdvance(){
	$('#common-dayT').datebox({
		width:'110px'
	});
	$.jobCommon();
	$.foreignGoodsShow();
	$('ul li.s1').on('click',function(){
		$(this).remove();
	})
	//証憑登録弹窗
	$.layerShowDiv('icon-tianjia1','400','auto',1,$('.zhengquan'));
}    
//更新页面初始化
function initupdateform(){
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
//	if(job_cd==null||job_cd==''){
//		showConfirmMsgHandler("NOTFOUND");
//		goBackPageHandler();
//		
//	}
	$('#aujob').text(input_no);
	$('#jobcd').text(job_cd);
	//$("#job_name").text(job_cd);
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
	           if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){      	//初始化转圈问题
		           	if(data[$.getRequestDataName()]['LendtrnInput']==null){
		            	$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
		            	showLockInfoMsgHandler("DATA_IS_NOT_EXIST","/jczh/cost_list.html?view=6");
			           	return false;
		            }
		           	//data.data.LendtrnInput.lendtrnList[0].vat_rate
		        	$.dateLimit_f("order_date",data.data.LendtrnInput.lendtrnList[0].lend_date,1);
		        	$('.inputDate').datebox('setValue',data.data.LendtrnInput.lendtrnList[0].lend_date)
		            var ifCostRate = part_language_change_new('IF_COST_RATE');
		           	if( data[$.getRequestDataName()]['LendtrnInput'].lendtrnList[0].vat_rate != data[$.getRequestDataName()].rate){
		           		$.messager.confirm('Confirmation', ifCostRate, function(r) {
							if(r) {
								objStorage.vatrate=data[$.getRequestDataName()].rate;
					           	objStorage.personMoneyCode = data["userInfo"];
					           	objStorage.moneyMessage = data[$.getRequestDataName()]['LendtrnInput'].cost_foreign_type;
					           	//页面设置默认货币
								setPersonMoneyCode();
								//本国货币保留小数位数
								var len = data["userInfo"].pointNumber;
								
					           	//setDomChildList(data[$.getRequestDataName()]['LendtrnInput']['foreign'],"cost_foreign_type");
					            //setDomChildList(data[$.getRequestDataName()]['LendtrnInput']['itemList'],"selectadvance")
					            var columnArr = new Array(); 
				           			SelectObj.selectData =data[$.getRequestDataName()]['LendtrnInput']; 
				           			columnArr.push("cost_foreign_type");//待修改
				           			columnArr.push("itemList");
				           			SelectObj.setSelectID = columnArr;
				           			SelectObj.setStringFlg="_";
				           			SelectObj.setSelectOfLog();  
//					            $('#cost_foreign_type option:first').remove();
//					            $('#itemList option:first').remove();
								var jobupdusercolor = data[$.getRequestDataName()]['CLDIV'][0]['updusercolorv'];
								if(jobupdusercolor!=null&&jobupdusercolor!=''){
									$("#jobupdusercolor").css("color",data[$.getRequestDataName()]['CLDIV'][0]['updusercolorv']);
								}
								var costupdusercolor = data[$.getRequestDataName()]['sumcost'][0]['updusercolorv'];
								if(costupdusercolor!=null&&costupdusercolor!=''){
									$("#costupdusercolor").css("color",data[$.getRequestDataName()]['sumcost'][0]['updusercolorv']);
								}
								
								//各个外货对应小数点长度
								foreignLen = data[$.getRequestDataName()]['LendtrnInput']['commonmst'];
					            foreignFormatFlg=data[$.getRequestDataName()]['LendtrnInput']['foreignFormatFlg'];
					            saleVatFormatFlg=data[$.getRequestDataName()]['LendtrnInput']['saleVatFormatFlg'];
								for(var i=0;i<data[$.getRequestDataName()]['LendtrnInput']['cost_foreign_type'].length;i++){
									aidcd.splice(i,0,data[$.getRequestDataName()]['LendtrnInput']['cost_foreign_type'][i].aidcd);	
									change_utin.splice(i,0,data[$.getRequestDataName()]['LendtrnInput']['cost_foreign_type'][i].changeutin);
								}
								$('.remark').val(data.data.LendtrnInput.lendtrnList[0].remark);//备注
								$('.inputDate').datebox('setValue', data.data.LendtrnInput.lendtrnList[0].lend_date);	//发生日
								$('.advancename').val(data.data.LendtrnInput.lendtrnList[0].lend_name);//项目名称
								$("#job_name").text(data[$.getRequestDataName()]['CLDIV'][0]['jobname']);//项目名称
								$("#itemList option[value='"+data.data.LendtrnInput.lendtrnList[0].item_code+"']").prop("selected",true);//经费科目
								//获取PDF出力是否选中		
								if(data.data.LendtrnInput.lendtrnList[0].pdfflagcri==1){
									$("#PayConfirmOutPDF").attr("checked",true);
								}
								
								//多国语言
								getGridLanguagetext('timesheet',data.data.LendtrnInput.timesheetuser);
								var name = data.data.LendtrnInput.timesheetuser[0].departname+" | " + data.data.LendtrnInput.lendtrnList[0].memberid + " | " + data.data.LendtrnInput.lendtrnList[0].username;
								$('.advanceperson').val(name);//代垫人
								$('#userid').val(data.data.LendtrnInput.lendtrnList[0].lend_user)
								$('#advancepersoncode').val(data.data.LendtrnInput.lendtrnList[0].lend_user);//代垫人
								if(data.data.LendtrnInput.lendtrnList[0].isdeduction=='1'){
									$("input[name=radio_xiao]:eq(0)").attr("checked",'checked');//0不扣除；1：扣除
								}
								$("#cost_rate").text(data[$.getRequestDataName()].rate);
								if(fortype==payforeigntype){
									$('.saleMoment').numberbox('setValue',data.data.LendtrnInput.lendtrnList[0].lend_foreign_amt.toFixed(len));//金額*
								}else{//外货
									$('.saleMoment').numberbox('setValue',data.data.LendtrnInput.lendtrnList[0].lend_foreign_amt.toFixed(lenone));//金額*
								}
								$.layerShowDiv('icon-i','1000','600',2,'../common/jczh/cost_list.shtml',job_cd);
								//$(".cal-cost option[value='"+data.data.LendtrnInput.lendtrnList[0].LEND_FOREIGN_TYPE+"']").prop("selected",true);//经费科目
								$('.plan_cost_foreign_amt').text(formatNumber(data.data.LendtrnInput.lendtrnList[0].lend_amt.toFixed(len)));//原価金額numberbox
								$('.plancost_tax').text(formatNumber(data.data.LendtrnInput.lendtrnList[0].lend_vat_amt.toFixed(len)));//增值税
								//实际增值税
								objStorage.realSaleTax = formatNumber(data.data.LendtrnInput.lendtrnList[0].lend_vat_amt);
				    			objStorage.realSaleTaxFlg = 0;
								saleIsHave(data.data.LendtrnInput.lendtrnList[0].ishave)//卖上是否税入;0：税拔；1：税入；
								var lend_code = data.data.LendtrnInput.lendtrnList[0].lend_cure_code;
								if(lend_code>0){
									$('#switch-state1').bootstrapSwitch('state',true);//是否外货
								}
								//设置外货类别
								$("#cost_foreign_type").val(data.data.LendtrnInput.lendtrnList[0].lend_foreign_type)
								//入力金额外货显示
								var payforeigntype =data.data.LendtrnInput.lendtrnList[0].payforeigntype;
								if(payforeigntype==null || payforeigntype==''|| payforeigntype==0){
									$('.cost_person_code').text(fortype);
									setfroeginlength()
								}else{
									//$('.cost_person_code').text(payforeigntype);
									$('.cost_person_code ').text(payforeigntype)
									setfroeginlength()
								}
								//非外货
								
								//增值税编辑小数点控制
								edit_common(1,len);
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
								locknum = data.data.LendtrnInput.lendtrnList[0].lock_flg;
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
								
								$('.plan_cost_cure_code').val(data.data.LendtrnInput.lendtrnList[0].lend_cure_code);//换算code
								$('.use_date').datebox('setValue', data.data.LendtrnInput.lendtrnList[0].lend_use_date);//使用日
								$('.plan_cost_refer').val(data.data.LendtrnInput.lendtrnList[0].lend_refer);//参照先  
								$('.plan_vat_amt').text(formatNumber(data.data.LendtrnInput.lendtrnList[0].lend_pay_amt.toFixed(len)));//最终外发金额				
					            //job信息区块，的得意先，与更新时间
								$("#divnm").text(data[$.getRequestDataName()]['CLDIV'][0]['divnm']);
								$("#upddate").text(data[$.getRequestDataName()]['CLDIV'][0]['upddate']);
								$("#username").text(data[$.getRequestDataName()]['CLDIV'][0]['username']);
								sessionStorage.setItem("addflg",data[$.getRequestDataName()]['CLDIV'][0]['saleAddFlag']);//壳上登录状态为1：已经登录  2：未登录
				    			//$("#jobcd").text(data[$.getRequestDataName()]['CLDIV'][0]['jobname']);
								$("#sumamt").text(formatNumber(data[$.getRequestDataName()]['sumcost'][0]['sumamt']));
								$("#updatetime").text(data[$.getRequestDataName()]['sumcost'][0]['updatetime']);
								$("#costupdname").text(data[$.getRequestDataName()]['sumcost'][0]['costupdname']);
								//标签数据重构
								labelToMySelect(data.data.LendtrnInput.list_lable);
								setLableArea(data.data.LendtrnInput.lableList);
								SelectObj.setLableList = data.data.LendtrnInput.list_lable;
								SelectObj.setChooseLableList = data.data.LendtrnInput.lableList;
								
								//把本国货币小数点位数赋值到全局变量中
							 	pointNumber = data['userInfo']['pointNumber'];
							 	//税率赋值
							 	if(!data[$.getRequestDataName()].rate>0){
							 		showInfoMsgHandler('NOTAXRATE',"ERROR","ERROR","jczh/cost_list.html?view=6","");
							 		//goCostlist();
							 	}
							 	//判断原价情报是否可点击
							    if(data[$.getRequestDataName()]['sumcost'][0]['updatetime']==undefined||data[$.getRequestDataName()]['sumcost'][0]['updatetime']==null||data[$.getRequestDataName()]['sumcost'][0]['updatetime']==""){
							     $(".tip_hover").attr("style","text-decoration: none;color: #2c3e50;cursor: text!important;");
							     //$('.tip_hover').removeClass("icon-i");
							     $('.tip_hover').unbind('click');
							    }
							    calculateOutSoure();
								//凭证信息初始化
								var rooftrn = data[$.getRequestDataName()]['prooftrn'];
								var str = '';
								for(var i = 0;i < rooftrn.length;i++){
									str +='<li class="s1">'+
											'<span>'+rooftrn[i].PROOF_TITLE+'</span> '+
												'<i class="iconfont icon-guanbi" style="font-size: 11px;color: white;"></i>'+
												'<input type="hidden" name="" id="" value="'+rooftrn[i].PROOF_URL+'" />'+
											'</li>';
									}
								$('.advance_url').empty();
								$('.advance_url').append($(str));
								addAdvEvent();
								updateinitflag='';
					            $.resetRequestID(data[$.getRequestUserInfoName()].requestID);
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
									//	sessionStorage.clear();
									}
									sessionStorage.removeItem("addflg");
					            }
								//判断是否计上，计上的不能删除
								var jobstatus = data[$.getRequestDataName()]['jobstatus'];
								if(jobstatus=='0'){
									//隐藏删除按钮
									$("button[name='delete']").addClass('hidden');
								}
								
							}else{
								objStorage.vatrate = data[$.getRequestDataName()]['LendtrnInput'].lendtrnList[0].vat_rate;
					           	updateinitflag='1';
					           	objStorage.personMoneyCode = data["userInfo"];
					           	objStorage.moneyMessage = data[$.getRequestDataName()]['LendtrnInput'].cost_foreign_type;
					           	//页面设置默认货币
								setPersonMoneyCode();
								//本国货币保留小数位数
								var len = data["userInfo"].pointNumber;
								
					           	//setDomChildList(data[$.getRequestDataName()]['LendtrnInput']['foreign'],"cost_foreign_type");
					            //setDomChildList(data[$.getRequestDataName()]['LendtrnInput']['itemList'],"selectadvance")
					            var columnArr = new Array(); 
				           			SelectObj.selectData =data[$.getRequestDataName()]['LendtrnInput'];           			
				           			columnArr.push("cost_foreign_type");//待修改
				           			columnArr.push("itemList");
				           			SelectObj.setSelectID = columnArr;
				           			SelectObj.setStringFlg="_";
				           			SelectObj.setSelectOfLog();  
//					            $('#cost_foreign_type option:first').remove();
//					            $('#itemList option:first').remove();
								var jobupdusercolor = data[$.getRequestDataName()]['CLDIV'][0]['updusercolorv'];
								if(jobupdusercolor!=null&&jobupdusercolor!=''){
									$("#jobupdusercolor").css("color",data[$.getRequestDataName()]['CLDIV'][0]['updusercolorv']);
								}
								var costupdusercolor = data[$.getRequestDataName()]['sumcost'][0]['updusercolorv'];
								if(costupdusercolor!=null&&costupdusercolor!=''){
									$("#costupdusercolor").css("color",data[$.getRequestDataName()]['sumcost'][0]['updusercolorv']);
								}
								
								//各个外货对应小数点长度
								foreignLen = data[$.getRequestDataName()]['LendtrnInput']['commonmst'];
					            foreignFormatFlg=data[$.getRequestDataName()]['LendtrnInput']['foreignFormatFlg'];
					            saleVatFormatFlg=data[$.getRequestDataName()]['LendtrnInput']['saleVatFormatFlg'];
								for(var i=0;i<data[$.getRequestDataName()]['LendtrnInput']['cost_foreign_type'].length;i++){
									aidcd.splice(i,0,data[$.getRequestDataName()]['LendtrnInput']['cost_foreign_type'][i].aidcd);	
									change_utin.splice(i,0,data[$.getRequestDataName()]['LendtrnInput']['cost_foreign_type'][i].changeutin);
								}
								$('.remark').val(data.data.LendtrnInput.lendtrnList[0].remark);//备注
								$('.inputDate').datebox('setValue', data.data.LendtrnInput.lendtrnList[0].lend_date);	//发生日
								$('.advancename').val(data.data.LendtrnInput.lendtrnList[0].lend_name);//项目名称
								$("#job_name").text(data[$.getRequestDataName()]['CLDIV'][0]['jobname']);//项目名称
								$("#itemList option[value='"+data.data.LendtrnInput.lendtrnList[0].item_code+"']").prop("selected",true);//经费科目
								//获取PDF出力是否选中		
								if(data.data.LendtrnInput.lendtrnList[0].pdfflagcri==1){
									$("#PayConfirmOutPDF").attr("checked",true);
								}
								
								//多国语言
								getGridLanguagetext('timesheet',data.data.LendtrnInput.timesheetuser);
								var name = data.data.LendtrnInput.timesheetuser[0].departname+" | " + data.data.LendtrnInput.timesheetuser[0].memberid + " | " + data.data.LendtrnInput.lendtrnList[0].username;
								$('.advanceperson').val(name);//代垫人
								$('#userid').val(data.data.LendtrnInput.lendtrnList[0].lend_user)
								$('#advancepersoncode').val(data.data.LendtrnInput.lendtrnList[0].lend_user);//代垫人
								if(data.data.LendtrnInput.lendtrnList[0].isdeduction=='1'){
									$("input[name=radio_xiao]:eq(0)").attr("checked",'checked');//0不扣除；1：扣除
								}
								$.layerShowDiv('icon-i','1000','600',2,'../common/jczh/cost_list.shtml',job_cd);
								//$(".cal-cost option[value='"+data.data.LendtrnInput.lendtrnList[0].LEND_FOREIGN_TYPE+"']").prop("selected",true);//经费科目
								$('.plan_cost_foreign_amt').text(formatNumber(data.data.LendtrnInput.lendtrnList[0].lend_amt.toFixed(len)));//原価金額numberbox
								$('.plancost_tax').text(formatNumber(data.data.LendtrnInput.lendtrnList[0].lend_vat_amt.toFixed(len)));//增值税
								//实际增值税
								objStorage.realSaleTax = formatNumber(data.data.LendtrnInput.lendtrnList[0].lend_vat_amt);
				    			objStorage.realSaleTaxFlg = 0;
								saleIsHave(data.data.LendtrnInput.lendtrnList[0].ishave)//卖上是否税入;0：税拔；1：税入；
								var lend_code = data.data.LendtrnInput.lendtrnList[0].lend_cure_code;
								if(lend_code>0){
									$('#switch-state1').bootstrapSwitch('state',true);//是否外货
								}
								//设置外货类别
								$("#cost_foreign_type").val(data.data.LendtrnInput.lendtrnList[0].lend_foreign_type)
								//入力金额外货显示
								var payforeigntype =data.data.LendtrnInput.lendtrnList[0].payforeigntype;
								if(payforeigntype==null || payforeigntype==''|| payforeigntype==0){
                                    $('.cost_person_code').text(fortype);
                                    setfroeginlength(len);
	                            }else{
	                            	var langtyp = $.getLangTyp();
	    	                    	if(langtyp=="jp"){
	    	                    		  $('.cost_person_code').text(data.data.LendtrnInput.lendtrnList[0].payforeigntypejp);
	    		                          setfroeginlength(data.data.LendtrnInput.lendtrnList[0].foreignlen);
	    		                          payforeigntype=data.data.LendtrnInput.lendtrnList[0].payforeigntypejp
	    	                    	}else if(langtyp=="zc"){
	    	                    		$('.cost_person_code').text(data.data.LendtrnInput.lendtrnList[0].payforeigntype);
	    		                          setfroeginlength(data.data.LendtrnInput.lendtrnList[0].foreignlen);
	    		                          payforeigntype=data.data.LendtrnInput.lendtrnList[0].payforeigntype
	    	                    	}else if(langtyp=="zt"){
	    	                    		$('.cost_person_code').text(data.data.LendtrnInput.lendtrnList[0].payforeigntypehk);
	    		                          setfroeginlength(data.data.LendtrnInput.lendtrnList[0].foreignlen);
	    		                          payforeigntype=data.data.LendtrnInput.lendtrnList[0].payforeigntypehk
	    	                    	}else if(langtyp=="en"){
	    	                    		$('.cost_person_code').text(data.data.LendtrnInput.lendtrnList[0].payforeigntypeen);
	    		                          setfroeginlength(data.data.LendtrnInput.lendtrnList[0].foreignlen);
	    		                          payforeigntype=data.data.LendtrnInput.lendtrnList[0].payforeigntypeen
	    	                    	} 
	                            }
								//非外货
								if(fortype==payforeigntype){
									$('.saleMoment').numberbox('setValue',data.data.LendtrnInput.lendtrnList[0].lend_foreign_amt.toFixed(len));//金額*
								}else{//外货
									$('.saleMoment').numberbox('setValue',data.data.LendtrnInput.lendtrnList[0].lend_foreign_amt.toFixed(lenone));//金額*
								}
								//增值税编辑小数点控制
								edit_common(1,len);
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
								locknum = data.data.LendtrnInput.lendtrnList[0].lock_flg;
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
								
								$('.plan_cost_cure_code').val(data.data.LendtrnInput.lendtrnList[0].lend_cure_code);//换算code
								$('.use_date').datebox('setValue', data.data.LendtrnInput.lendtrnList[0].lend_use_date);//使用日
								$('.plan_cost_refer').val(data.data.LendtrnInput.lendtrnList[0].lend_refer);//参照先  
								$('.plan_vat_amt').text(formatNumber(data.data.LendtrnInput.lendtrnList[0].lend_pay_amt.toFixed(len)));//最终外发金额				
					            //job信息区块，的得意先，与更新时间
								$("#divnm").text(data[$.getRequestDataName()]['CLDIV'][0]['divnm']);
								$("#upddate").text(data[$.getRequestDataName()]['CLDIV'][0]['upddate']);
								$("#username").text(data[$.getRequestDataName()]['CLDIV'][0]['username']);
								sessionStorage.setItem("addflg",data[$.getRequestDataName()]['CLDIV'][0]['saleAddFlag']);//壳上登录状态为1：已经登录  2：未登录
				    			//$("#jobcd").text(data[$.getRequestDataName()]['CLDIV'][0]['jobname']);
								$("#sumamt").text(formatNumber(data[$.getRequestDataName()]['sumcost'][0]['sumamt']));
								$("#updatetime").text(data[$.getRequestDataName()]['sumcost'][0]['updatetime']);
								$("#costupdname").text(data[$.getRequestDataName()]['sumcost'][0]['costupdname']);
								//标签数据重构
								labelToMySelect(data.data.LendtrnInput.list_lable);
								setLableArea(data.data.LendtrnInput.lableList);
								SelectObj.setLableList = data.data.LendtrnInput.list_lable;
								SelectObj.setChooseLableList = data.data.LendtrnInput.lableList;
								
								//把本国货币小数点位数赋值到全局变量中
							 	pointNumber = data['userInfo']['pointNumber'];
							 	//税率赋值
							 	if(!data[$.getRequestDataName()].rate>0){
							 		showInfoMsgHandler('NOTAXRATE',"ERROR","ERROR","jczh/cost_list.html?view=6","");
							 		//goCostlist();
							 	}
							 	//判断原价情报是否可点击
							    if(data[$.getRequestDataName()]['sumcost'][0]['updatetime']==undefined||data[$.getRequestDataName()]['sumcost'][0]['updatetime']==null||data[$.getRequestDataName()]['sumcost'][0]['updatetime']==""){
							     $(".tip_hover").attr("style","text-decoration: none;color: #2c3e50;cursor: text!important;");
							     //$('.tip_hover').removeClass("icon-i");
							     $('.tip_hover').unbind('click');
							    }
							 	$("#cost_rate").text(data[$.getRequestDataName()]['LendtrnInput'].lendtrnList[0].vat_rate);
								//凭证信息初始化
								var rooftrn = data[$.getRequestDataName()]['prooftrn'];
								var str = '';
								for(var i = 0;i < rooftrn.length;i++){
									str +='<li class="s1">'+
											'<span>'+rooftrn[i].PROOF_TITLE+'</span> '+
												'<i class="iconfont icon-guanbi" style="font-size: 11px;color: white;"></i>'+
												'<input type="hidden" name="" id="" value="'+rooftrn[i].PROOF_URL+'" />'+
											'</li>';
									}
								$('.advance_url').empty();
								$('.advance_url').append($(str));
								addAdvEvent();
								updateinitflag='';
					            $.resetRequestID(data[$.getRequestUserInfoName()].requestID);
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
									//	sessionStorage.clear();
									}
									sessionStorage.removeItem("addflg");
					            }
								//判断是否计上，计上的不能删除
								var jobstatus = data[$.getRequestDataName()]['jobstatus'];
								if(jobstatus=='0'){
									//隐藏删除按钮
									$("button[name='delete']").addClass('hidden');
								}
							}
						},1);
						//off 默认选中
						$('.pop_z').closest('.messager-body').next('.messager-button').find('.l-btn').eq(0).attr('id','p_update_off');
		           		
		           	}
		           	else{

		           		objStorage.vatrate = data[$.getRequestDataName()]['LendtrnInput'].lendtrnList[0].vat_rate;
			           	updateinitflag='1';
			           	objStorage.personMoneyCode = data["userInfo"];
			           	objStorage.moneyMessage = data[$.getRequestDataName()]['LendtrnInput'].cost_foreign_type;
			           	//页面设置默认货币
						setPersonMoneyCode();
						//本国货币保留小数位数
						var len = data["userInfo"].pointNumber;
						
			           	//setDomChildList(data[$.getRequestDataName()]['LendtrnInput']['foreign'],"cost_foreign_type");
			            //setDomChildList(data[$.getRequestDataName()]['LendtrnInput']['itemList'],"selectadvance")
			            var columnArr = new Array(); 
		           			SelectObj.selectData =data[$.getRequestDataName()]['LendtrnInput'];     
		           			
		           			var defaultData = {};
		                    defaultData.itemList= data.data.LendtrnInput.lendtrnList[0].item_code;
		                    if(data.data.LendtrnInput.lendtrnList[0].lend_foreign_type != "")
		                    {
		                    	defaultData.cost_foreign_type= data.data.LendtrnInput.lendtrnList[0].item_code;
		                    }
		                    SelectObj.setDefaultData = defaultData;
		                    
		           			columnArr.push("cost_foreign_type");//待修改
			           		 var defaultData = {};
			                 defaultData.itemList= data.data.LendtrnInput.lendtrnList[0].item_code;
			                 SelectObj.setDefaultData = defaultData;
		           			columnArr.push("itemList");
		           			SelectObj.setSelectID = columnArr;
		           			SelectObj.setStringFlg="_";
		           			SelectObj.setSelectOfLog();  
//			            $('#cost_foreign_type option:first').remove();
//			            $('#itemList option:first').remove();
						var jobupdusercolor = data[$.getRequestDataName()]['CLDIV'][0]['updusercolorv'];
						if(jobupdusercolor!=null&&jobupdusercolor!=''){
							$("#jobupdusercolor").css("color",data[$.getRequestDataName()]['CLDIV'][0]['updusercolorv']);
						}
						var costupdusercolor = data[$.getRequestDataName()]['sumcost'][0]['updusercolorv'];
						if(costupdusercolor!=null&&costupdusercolor!=''){
							$("#costupdusercolor").css("color",data[$.getRequestDataName()]['sumcost'][0]['updusercolorv']);
						}
						
						//各个外货对应小数点长度
						foreignLen = data[$.getRequestDataName()]['LendtrnInput']['commonmst'];
			            foreignFormatFlg=data[$.getRequestDataName()]['LendtrnInput']['foreignFormatFlg'];
			            saleVatFormatFlg=data[$.getRequestDataName()]['LendtrnInput']['saleVatFormatFlg'];
						for(var i=0;i<data[$.getRequestDataName()]['LendtrnInput']['cost_foreign_type'].length;i++){
							aidcd.splice(i,0,data[$.getRequestDataName()]['LendtrnInput']['cost_foreign_type'][i].aidcd);	
							change_utin.splice(i,0,data[$.getRequestDataName()]['LendtrnInput']['cost_foreign_type'][i].changeutin);
						}
						$('.remark').val(data.data.LendtrnInput.lendtrnList[0].remark);//备注
						$('.inputDate').datebox('setValue', data.data.LendtrnInput.lendtrnList[0].lend_date);	//发生日
						$('.advancename').val(data.data.LendtrnInput.lendtrnList[0].lend_name);//项目名称
						$("#job_name").text(data[$.getRequestDataName()]['CLDIV'][0]['jobname']);//项目名称
						$("#itemList option[value='"+data.data.LendtrnInput.lendtrnList[0].item_code+"']").prop("selected",true);//经费科目
						//获取PDF出力是否选中		
						if(data.data.LendtrnInput.lendtrnList[0].pdfflagcri==1){
							$("#PayConfirmOutPDF").attr("checked",true);
						}
						
						//多国语言
						getGridLanguagetext('timesheet',data.data.LendtrnInput.timesheetuser);
						var langtyp = $.getLangTyp();
						if(langtyp == 'zc'){
							var name = data.data.LendtrnInput.lendtrnList[0].departname+" | " + data.data.LendtrnInput.lendtrnList[0].memberid + " | " + data.data.LendtrnInput.lendtrnList[0].username;
						}else if(langtyp == 'jp'){
							var name = data.data.LendtrnInput.lendtrnList[0].departnamejp+" | " + data.data.LendtrnInput.lendtrnList[0].memberid + " | " + data.data.LendtrnInput.lendtrnList[0].username;
						}
						else if(langtyp == 'en'){
							var name = data.data.LendtrnInput.lendtrnList[0].departnameen+" | " + data.data.LendtrnInput.lendtrnList[0].memberid + " | " + data.data.LendtrnInput.lendtrnList[0].username;
						}else if(langtyp == 'hk'){
							var name = data.data.LendtrnInput.lendtrnList[0].departnamehk+" | " + data.data.LendtrnInput.lendtrnList[0].memberid + " | " + data.data.LendtrnInput.lendtrnList[0].username;
						}
						
						$('.advanceperson').val(name);//代垫人
						$('#userid').val(data.data.LendtrnInput.lendtrnList[0].lend_user)
						$('#advancepersoncode').val(data.data.LendtrnInput.lendtrnList[0].lend_user);//代垫人
						if(data.data.LendtrnInput.lendtrnList[0].isdeduction=='1'){
							$("input[name=radio_xiao]:eq(0)").attr("checked",'checked');//0不扣除；1：扣除
						}
						$.layerShowDiv('icon-i','1000','600',2,'../common/jczh/cost_list.shtml',job_cd);
						//$(".cal-cost option[value='"+data.data.LendtrnInput.lendtrnList[0].LEND_FOREIGN_TYPE+"']").prop("selected",true);//经费科目
						$('.plan_cost_foreign_amt').text(formatNumber(data.data.LendtrnInput.lendtrnList[0].lend_amt.toFixed(len)));//原価金額numberbox
						$('.plancost_tax').text(formatNumber(data.data.LendtrnInput.lendtrnList[0].lend_vat_amt.toFixed(len)));//增值税
						//实际增值税
						objStorage.realSaleTax = formatNumber(data.data.LendtrnInput.lendtrnList[0].lend_vat_amt);
		    			objStorage.realSaleTaxFlg = 0;
						saleIsHave(data.data.LendtrnInput.lendtrnList[0].ishave)//卖上是否税入;0：税拔；1：税入；
						var lend_code = data.data.LendtrnInput.lendtrnList[0].lend_cure_code;
						if(lend_code>0){
							$('#switch-state1').bootstrapSwitch('state',true);//是否外货
						}
						//设置外货类别
						$("#cost_foreign_type").val(data.data.LendtrnInput.lendtrnList[0].lend_foreign_type)
						//入力金额外货显示
						var payforeigntype =data.data.LendtrnInput.lendtrnList[0].payforeigntype;
						if(payforeigntype==null || payforeigntype==''|| payforeigntype==0){
                            $('.cost_person_code').text(fortype);
                            setfroeginlength(len);
	                    }else{
	                    	var langtyp = $.getLangTyp();
	                    	if(langtyp=="jp"){
	                    		  $('.cost_person_code').text(data.data.LendtrnInput.lendtrnList[0].payforeigntypejp);
		                          setfroeginlength(data.data.LendtrnInput.lendtrnList[0].foreignlen);
		                          payforeigntype=data.data.LendtrnInput.lendtrnList[0].payforeigntypejp
	                    	}else if(langtyp=="zc"){
	                    		$('.cost_person_code').text(data.data.LendtrnInput.lendtrnList[0].payforeigntype);
		                          setfroeginlength(data.data.LendtrnInput.lendtrnList[0].foreignlen);
		                          payforeigntype=data.data.LendtrnInput.lendtrnList[0].payforeigntype
	                    	}else if(langtyp=="zt"){
	                    		$('.cost_person_code').text(data.data.LendtrnInput.lendtrnList[0].payforeigntypehk);
		                          setfroeginlength(data.data.LendtrnInput.lendtrnList[0].foreignlen);
		                          payforeigntype=data.data.LendtrnInput.lendtrnList[0].payforeigntypehk
	                    	}else if(langtyp=="en"){
	                    		$('.cost_person_code').text(data.data.LendtrnInput.lendtrnList[0].payforeigntypeen);
		                          setfroeginlength(data.data.LendtrnInput.lendtrnList[0].foreignlen);
		                          payforeigntype=data.data.LendtrnInput.lendtrnList[0].payforeigntypeen
	                    	}   
	                    }
						//非外货
						if(fortype==payforeigntype){
							$('.saleMoment').numberbox('setValue',data.data.LendtrnInput.lendtrnList[0].lend_foreign_amt.toFixed(len));//金額*
						}else{//外货
							$('.saleMoment').numberbox('setValue',data.data.LendtrnInput.lendtrnList[0].lend_foreign_amt.toFixed(lenone));//金額*
						}
						//增值税编辑小数点控制
						edit_common(1,len);
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
						locknum = data.data.LendtrnInput.lendtrnList[0].lock_flg;
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
						
						$('.plan_cost_cure_code').val(data.data.LendtrnInput.lendtrnList[0].lend_cure_code);//换算code
						$('.use_date').datebox('setValue', data.data.LendtrnInput.lendtrnList[0].lend_use_date);//使用日
						$('.plan_cost_refer').val(data.data.LendtrnInput.lendtrnList[0].lend_refer);//参照先  
						$('.plan_vat_amt').text(formatNumber(data.data.LendtrnInput.lendtrnList[0].lend_pay_amt.toFixed(len)));//最终外发金额				
			            //job信息区块，的得意先，与更新时间
						$("#divnm").text(data[$.getRequestDataName()]['CLDIV'][0]['divnm']);
						$("#upddate").text(data[$.getRequestDataName()]['CLDIV'][0]['upddate']);
						$("#username").text(data[$.getRequestDataName()]['CLDIV'][0]['username']);
						sessionStorage.setItem("addflg",data[$.getRequestDataName()]['CLDIV'][0]['saleAddFlag']);//壳上登录状态为1：已经登录  2：未登录
		    			//$("#jobcd").text(data[$.getRequestDataName()]['CLDIV'][0]['jobname']);
						$("#sumamt").text(formatNumber(data[$.getRequestDataName()]['sumcost'][0]['sumamt']));
						$("#updatetime").text(data[$.getRequestDataName()]['sumcost'][0]['updatetime']);
						$("#costupdname").text(data[$.getRequestDataName()]['sumcost'][0]['costupdname']);
						//标签数据重构
						labelToMySelect(data.data.LendtrnInput.list_lable);
						setLableArea(data.data.LendtrnInput.lableList);
						SelectObj.setLableList = data.data.LendtrnInput.list_lable;
						SelectObj.setChooseLableList = data.data.LendtrnInput.lableList;
						
						//把本国货币小数点位数赋值到全局变量中
					 	pointNumber = data['userInfo']['pointNumber'];
					 	//税率赋值
					 	if(!data[$.getRequestDataName()].rate>0){
					 		showInfoMsgHandler('NOTAXRATE',"ERROR","ERROR","jczh/cost_list.html?view=6","");
					 		//goCostlist();
					 	}
					 	//判断原价情报是否可点击
					    if(data[$.getRequestDataName()]['sumcost'][0]['updatetime']==undefined||data[$.getRequestDataName()]['sumcost'][0]['updatetime']==null||data[$.getRequestDataName()]['sumcost'][0]['updatetime']==""){
					     $(".tip_hover").attr("style","text-decoration: none;color: #2c3e50;cursor: text!important;");
					     //$('.tip_hover').removeClass("icon-i");
					     $('.tip_hover').unbind('click');
					    }
					 	$("#cost_rate").text(data[$.getRequestDataName()].rate);
						//凭证信息初始化
						var rooftrn = data[$.getRequestDataName()]['prooftrn'];
						var str = '';
						for(var i = 0;i < rooftrn.length;i++){
							str +='<li class="s1">'+
									'<span>'+rooftrn[i].PROOF_TITLE+'</span> '+
										'<i class="iconfont icon-guanbi" style="font-size: 11px;color: white;"></i>'+
										'<input type="hidden" name="" id="" value="'+rooftrn[i].PROOF_URL+'" />'+
									'</li>';
							}
						$('.advance_url').empty();
						$('.advance_url').append($(str));
						addAdvEvent();
						updateinitflag='';
			            $.resetRequestID(data[$.getRequestUserInfoName()].requestID);
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
							//	sessionStorage.clear();
							}
							sessionStorage.removeItem("addflg");
			            }
						//判断是否计上，计上的不能删除
						var jobstatus = data[$.getRequestDataName()]['jobstatus'];
						if(jobstatus=='0'){
							//隐藏删除按钮
							$("button[name='delete']").addClass('hidden');
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
//新增页面初始化
function initadvanceform(){
	var job_cd =urlPars.parm('jobcd');
//	if(job_cd==null||job_cd==''){
//		showConfirmMsgHandler("NOTFOUND");
//		goBackPageHandler();
//		
//	}
	var allData = {
		"trantrn":{
			job_cd:job_cd
		}
	}	
 	var path = $.getAjaxPath()+"LendtrnInit";	
	$.ajax({
	  type: "POST",
	  url:path,
	  contentType:"application/json",
	  data:JSON.stringify(allData),
	  dataType:"JSON",
	  headers: {"requestID":$.getRequestID(),"requestName":$.getRequestNameByJcZH()},
			success:function(data){
			
	           if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
	        	  //加时区系统时间时间
	        	$.dateLimit_f("order_date",data[$.getRequestDataName()]['systemDate'],1);
	        	$('.inputDate').datebox('setValue',data[$.getRequestDataName()]['systemDate'])
	           	objStorage.personMoneyCode = data["userInfo"];
	           	//本国货币保留小数位数
				var len = data["userInfo"].pointNumber;
	           	//增值税编辑小数点控制
				edit_common(1,len);
	           	//页面设置默认货币
				setPersonMoneyCode();	            
	            var columnArr = new Array(); 
	            var  list = [];
           			SelectObj.selectData =data[$.getRequestDataName()]['TrantrnInput'];
           			columnArr.push("cost_foreign_type");//待修改
           			columnArr.push("itemList");
           			SelectObj.setSelectID = columnArr;
           			SelectObj.setStringFlg="_";
           			SelectObj.setSelectOfLog();
           		//各个外货对应小数点长度
				foreignLen = data[$.getRequestDataName()]['TrantrnInput']['commonmst'];
           		foreignFormatFlg=data[$.getRequestDataName()]['TrantrnInput']['foreignFormatFlg'];
	            saleVatFormatFlg=data[$.getRequestDataName()]['TrantrnInput']['saleVatFormatFlg'];
				for(var i=0;i<data[$.getRequestDataName()]['TrantrnInput']['cost_foreign_type'].length;i++){
				aidcd.splice(i,0,data[$.getRequestDataName()]['TrantrnInput']['cost_foreign_type'][i].aidcd);	
				change_utin.splice(i,0,data[$.getRequestDataName()]['TrantrnInput']['cost_foreign_type'][i].changeutin);	
				}
				//获取PDF出力是否选中		
				if(data[$.getRequestDataName()]['TrantrnInput']['pdfflagcri']==1){
					$("#PayConfirmOutPDF").attr("checked",true);
				}
				//初始化转圈问题
	            if(data[$.getRequestDataName()]['CLDIV'].length==0){
	            	$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
	            	showLockInfoMsgHandler("DATA_IS_NOT_EXIST","/jczh/job_registration_list.html?view=init&menu=se");
		           	return false;
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
				
	            //job信息区块，的得意先，与更新时间
				$("#divnm").text(data[$.getRequestDataName()]['CLDIV'][0]['divnm'])
				$("#upddate").text(data[$.getRequestDataName()]['CLDIV'][0]['upddate'])
				$("#username").text(data[$.getRequestDataName()]['CLDIV'][0]['username'])
				sessionStorage.setItem("addflg",data[$.getRequestDataName()]['CLDIV'][0]['saleAddFlag']);//壳上登录状态为1：已经登录  2：未登录
    			$("#jobcd").text(data[$.getRequestDataName()]['CLDIV'][0]['jobcd']);
    			$("#job_name").text(data[$.getRequestDataName()]['CLDIV'][0]['jobname']);//项目名称
				$("#sumamt").text(formatNumber(data[$.getRequestDataName()]['sumcost'][0]['sumamt']))
				$("#updatetime").text(data[$.getRequestDataName()]['sumcost'][0]['updatetime'])
				$("#costupdname").text(data[$.getRequestDataName()]['sumcost'][0]['costupdname'])
				$.layerShowDiv('icon-i','1000','600',2,'../common/jczh/cost_list.shtml',job_cd);
				//把本国货币小数点位数赋值到全局变量中
			 	pointNumber = data['userInfo']['pointNumber'];
			 	//税率赋值
			 	if(!data[$.getRequestDataName()].rate>0){
//			 		showConfirmMsgHandler('NOTAXRATE');
					var urlad = 'jczh/job_registration_list.html?view=init&menu=se';
					showInfoMsgHandler('NOTAXRATE',"ERROR","ERROR",urlad,'')
//			 		goBackPageHandler();
			 	}
			 	$("#cost_rate").text(data[$.getRequestDataName()].rate);
				//标签数据重构
				labelToMySelect(data.data.TrantrnInput.list_lable);
				SelectObj.setLableList = data.data.TrantrnInput.list_lable;
				var jobcd =job_cd;
				var powerList = data['userInfo'].uNodeList;
	            var bl = isHavePower(powerList, [5,6,7,8]);
	            if(bl){
//					if(data[$.getRequestDataName()]['CLDIV'][0]['saleAddFlag']=='1'){
//						window.job_cd=jobcd;
//						$.layerShowDiv('job_detail','70%','80%',2,'../jczh/job_detail.shtml');	
//						//sessionStorage.clear();
//					}else{
//						window.job_cd=jobcd;
//						$.layerShowDiv('job_detail','70%','80%',2,'../jczh/job_detail_nosale.html');	
//						//sessionStorage.clear();
//					}
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
					sessionStorage.removeItem("addflg");
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
	            //判断原价情报是否可点击
			    if(data[$.getRequestDataName()]['sumcost'][0]['updatetime']==undefined||data[$.getRequestDataName()]['sumcost'][0]['updatetime']==null||data[$.getRequestDataName()]['sumcost'][0]['updatetime']==""){
			    $(".tip_hover").attr("style","text-decoration: none;color: #2c3e50;cursor: text!important;");
			    //$('.tip_hover').removeClass("icon-i");
			    $('.tip_hover').unbind('click');
			    }
			    setfroeginlength(pointNumber);
				//setLableArea(data.data.TrantrnInput.lableList);
	            $.resetRequestID(data[$.getRequestUserInfoName()].requestID);
	           }else{
	           	showErrorFunHandler(data[$.getRequestMetaName()].result,"ERROR","ERROR");
	           }
	       },
	       error:function(data){
	       		showErrorHandler("CONNECTION_SERVER_ERROR","ERROR","ERROR");
	       }
	 });

}
function advanceUpdate(){
	if(!validataRequired())
	{
		return;
	}	
	// 凭证信息部分
	var parss = [];
	var urllist=$('.advance_url li');
	for(var i=0;i<urllist.length;i++){
		var urlvalue = urllist.eq(i).find('input[type="hidden"]').val();
		var urltitle = urllist.eq(i).text();
		var list_rooftrn={
			proof_url:urlvalue,
			proof_title:urltitle
		}
		parss.push(list_rooftrn);
	}
	var input_no =urlPars.parm('inputno');
	var job_cd =urlPars.parm('jobcd');
	var remark = $('.remark').val();//备注
	var inputdate = $('.inputDate').val();//发生日
	var advancename = $('.advancename').val();//项目名称
	var item_code  = $("#itemList").val();//经费科目
	var status = '0';//登录未承认
	var del_flg = '0';//0：正常、1：削除
	var lend_user =$('#userid').val();//代垫人
	var isdeduction = $('input:radio[id="delflag"]:checked').val();//0：不扣除；1：扣除
	var tranamt = recoveryNumber($('.plan_cost_foreign_amt').text());//原価金額numberbox
	var lend_foreign_amt = recoveryNumber($('.saleMoment').val());//金額*
	var lend_vat_amt= recoveryNumber($('.plancost_tax').text());//增值税
	var ishave = $(".cost_rate_click .active input[name='saleIsHave']").val();//卖上是否税入;0：税拔；1：税入；
	var lend_cure_code = $('.plan_cost_cure_code').val();//换算code
	var lend_use_date = $('.use_date').val();//使用日
	var lend_refer = $('.plan_cost_refer').val();//参照先  
	var lend_foreign_type = $('.cal-cost').val();//外货货币种类编号
	var lend_pay_amt = recoveryNumber($('.plan_vat_amt').text());//最终外发金额
	var advanceperson = $('#advancepersoncode').val();	
//	if((lend_user==''||lend_user==null)&&(advanceperson==''||advanceperson==null)){
//	showErrorHandler("LENDUSER_NOTNULL","info","info");
//	return true;
//	}
	var vat_change_flg = objStorage.realSaleTaxFlg;
	var vat_rate = objStorage.vatrate;
	var pars = {
		lendtrn:{
			input_no:input_no,
			job_cd:job_cd,
			lend_date:inputdate,
			item_code:item_code,
			lend_name:advancename,
			lend_user:lend_user,
			isdeduction:isdeduction,
			remark:remark,
			lend_amt:tranamt,
			lend_pay_amt:lend_pay_amt,
			lend_foreign_amt:lend_foreign_amt,
			lend_vat_amt:lend_vat_amt,
			ishave:ishave,
			lend_cure_code:lend_cure_code,
			lendusedate:lend_use_date,
			lend_refer:lend_refer,
			lend_foreign_type:lend_foreign_type,
			status:status,
			vat_change_flg:vat_change_flg,
			lock_flg:locknum,
			vat_rate:vat_rate
		},
		list_rooftrn:parss,
		input_no:input_no,
			job_cd:job_cd,
			lend_date:inputdate,
			item_code:item_code,
			lend_name:advancename,
			lend_user:lend_user,
			isdeduction:isdeduction,
			remark:remark,
			lend_amt:tranamt,
			lend_pay_amt:lend_pay_amt,
			lend_foreign_amt:lend_foreign_amt,
			lend_vat_amt:lend_vat_amt,
			ishave:ishave,
			lend_cure_code:lend_cure_code,
			lendusedate:lend_use_date,
			lend_refer:lend_refer,
			lend_foreign_type:lend_foreign_type,
			status:status,
			vat_change_flg:vat_change_flg,
			lock_flg:locknum
	}
	var lableList = getLable();
	pars['lableList'] =lableList;//标签
	var path = $.getAjaxPath()+"LendtrnUpdate";	
	 $.ajax({
	  type: "POST",
	  url:path,
	  contentType:"application/json",
	  data:JSON.stringify(pars),
	  dataType:"JSON",
	  headers: {"requestID":$.getRequestID(),"requestName":$.getRequestNameByJcZH()},
			success:function(data){			
	           if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
		           	$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
		           	if(data.data==-1){//lockflg验证
			           	$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
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
			     		OutPutPdfHandler(job_cd,'',new_input_no,'deboursCreate',"","","",1);
			     		
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
function advanceAdd(){
	if(!validataRequired())
	{
		return;
	}
	var parss = [];
	var urllist=$('.advance_url li');
	for(var i=0;i<urllist.length;i++){
		var urlvalue = urllist.eq(i).find('input[type="hidden"]').val();
		var urltitle = urllist.eq(i).text();
		var list_rooftrn={
			proof_url:urlvalue,
			proof_title:urltitle
		}
		parss.push(list_rooftrn);
	}
	var cost_rate=$("#cost_rate").text();
	var job_cd =urlPars.parm('jobcd');
	var remark = $('.remark').val();//备注
	var inputdate = $('.inputDate').val();//发生日
	var advancename = $('.advancename').val();//项目名称
	var item_code  = $("#itemList").val();//经费科目
	var status = '0';//登录未承认
	var del_flg = '0';//0：正常、1：削除
	var lend_user =$('#userid').val();//代垫人
	var isdeduction = $('input:radio[id="delflag"]:checked').val();//0：不扣除；1：扣除
	var tranamt =recoveryNumber( $('.plan_cost_foreign_amt').text());//原価金額numberbox
	var lend_foreign_amt = recoveryNumber($('.saleMoment').val());//金額*
	var lend_vat_amt= recoveryNumber($('.plancost_tax').text());//增值税
	var ishave = $(".cost_rate_click .active input[name='saleIsHave']").val();//卖上是否税入;0：税拔；1：税入；
	var lend_cure_code = $('.plan_cost_cure_code').val();//换算code
	var lend_use_date = $('.use_date').val();//使用日
	if(lend_use_date==''){
		lend_use_date=null;
	}
	var lend_refer = $('.plan_cost_refer').val();//参照先  
	var lend_foreign_type = $('.cal-cost').val();//外货货币种类编号
	var lend_pay_amt = recoveryNumber($('.plan_vat_amt').text());//最终外发金额
	var vat_change_flg = objStorage.realSaleTaxFlg;
	var pars = {
		lendtrn:{	
			job_cd:job_cd,
			lend_date:inputdate,
			item_code:item_code,
			lend_name:advancename,
			lend_user:lend_user,
			isdeduction:isdeduction,
			remark:remark,
			lend_amt:tranamt,
			lend_pay_amt:lend_pay_amt,
			lend_foreign_amt:lend_foreign_amt,
			lend_vat_amt:lend_vat_amt,
			ishave:ishave,
			lend_cure_code:lend_cure_code,
			lend_use_date:lend_use_date,
			lend_refer:lend_refer,
			lend_foreign_type:lend_foreign_type,
			status:status,
			del_flg:del_flg,
			vat_change_flg:vat_change_flg,
			vat_rate:cost_rate
		},
		list_rooftrn:parss,
		job_cd:job_cd,
			lend_date:inputdate,
			item_code:item_code,
			lend_name:advancename,
			lend_user:lend_user,
			isdeduction:isdeduction,
			remark:remark,
			lend_amt:tranamt,
			lend_pay_amt:lend_pay_amt,
			lend_foreign_amt:lend_foreign_amt,
			lend_vat_amt:lend_vat_amt,
			ishave:ishave,
			lend_cure_code:lend_cure_code,
			lend_use_date:lend_use_date,
			lend_refer:lend_refer,
			lend_foreign_type:lend_foreign_type,
			status:status,
			del_flg:del_flg,
			vat_change_flg:vat_change_flg
	}
	var lableList = getLable();
	pars['lableList'] =lableList;//标签
 	var path = $.getAjaxPath()+"LendtrnAdd";	
	$.ajax({
	  type: "POST",
	  url:path,
	  contentType:"application/json",
	  data:JSON.stringify(pars),
	  dataType:"JSON",
	  headers: {"requestID":$.getRequestID(),"requestName":$.getRequestNameByJcZH()},
			success:function(data){			
	           if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
//	           	showErrorHandler("EXECUTE_SUCCESS","info","info");
				if(data[$.getRequestDataName()]==0){
	           		showErrorHandler("ORDER_CREATE_ERROR","info","info");
	           		$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
	           		return false;
	           	}
				if(data.data==-2){//使用日验证
			           	$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
			           	showInfoMsgHandlerstop("DATE_RANGE_ERROR","/jczh/cost_list.html?view=6");
			           	return false;
		            }
	           	$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
	           	//插入成功后执行导出pdf
	           	 	if ($('#PayConfirmOutPDF').prop("checked")){
	           	 		var input_no = data.data;
			     		OutPutPdfHandler(job_cd,'',input_no,'deboursCreate',"","","",0);
			     		
			        }else{
			        	goJoblist();
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
//计算外发方法
function calculateOutSoure(value){
	if(updateinitflag=='1'){//更新初始化不需要计算
		return;
	}
    var index =document.getElementById("cost_foreign_type").selectedIndex;
	//alert(qj[0]);
	//浮点型精确计算costVatRate
	var m=0;
	var costIsHave;
	var cost_rate=$("#cost_rate").text();
	try{m+=cost_rate.split(".")[1].length}catch(e){}
	try{m+="0.01".split(".")[1].length}catch(e){} 
	var costVatRate=Number(cost_rate.replace(".",""))*Number("0.01".replace(".",""))/Math.pow(10,m) //卖上增值税率
	var costCode=change_utin[parseInt(index-1)];//卖上外货code
	var costPoint=pointNumber;//本国货币小数点
	var costMonet=$("#cost_foreign_amt").val();//卖上入力金额
	var costCurCode=$("#saleCurCode").val();//卖上换算CODE
	//var costVatRate=number($("#cost_rate").text())*0.01;
	value == undefined ?costIsHave= $(".cost_rate_click .active input[name='saleIsHave']").val():costIsHave= value;
    //计算税率
    var cost_obj=calculateCostHandler(costMonet,costCurCode,costCode,cost_rate,costPoint,costIsHave,foreignFormatFlg,saleVatFormatFlg);
	$('.plan_cost_foreign_amt').text(formatNumber(cost_obj['costBase']));
	$('.plancost_tax').text(formatNumber(cost_obj['costVatBase']));
	$('.plan_vat_amt').text(formatNumber(cost_obj['payBase']));
}
//含税/不含税方法
function saleIsHave(flag){
	var aDom = $('.cost_rate_click[data-toggle="buttons"]');
	if(flag == 1){
		flag = true;
	}else{
		flag = false;
	}
	if(flag){
		aDom.find('label').eq(0).addClass('active');
		aDom.find('label').eq(1).removeClass('active');
	}else{
		aDom.find('label').eq(1).addClass('active');
		aDom.find('label').eq(0).removeClass('active');
	}
}
//是否是外货方法
function IsDeduction(flag){
	var aDom = $('.cost_rate_click[data-toggle="buttons"]');
	if(flag == 1){
		flag = true;
	}else{
		flag = false;
	}
	if(flag){
		aDom.find('label').eq(0).addClass('active');
		aDom.find('label').eq(1).removeClass('active');
	}else{
		aDom.find('label').eq(1).addClass('active');
		aDom.find('label').eq(0).removeClass('active');
	}
}
function advanceURL(){
	if(!Judgment_empty('advancetitle','advanceurl')){
		return;
	};
//	if(!Judgment_empty('advanceurl')){
//		return;
//	};
	var str = '';
	var advance_title=$(".advancetitle").val();
	var advance_url=$(".advanceurl").val();
	var indexF = $(".advancetitle").attr('index');
	if(advance_url.length>5000){
		showErrorHandler("URLTOOLONG","info","info");
	}
	if(!pingZhengFlag){
		var numLi = $('.advance_url li').length;
		str = '<li class="s1">' +
		'<span value="'+numLi+'">' + advance_title + '</span> ' +
		'<i class="iconfont icon-guanbi" style="font-size: 11px;color: white;"></i>' +
		'<input type="hidden" class="urlvalue" name="" id="" value="' + advance_url + '" />' +
		'</li>';
	$('.advance_url').append($(str));
	}else{
		var liDom = $('.advance_url').find('li').find('span').eq(indexF);
		liDom.text(advance_title);
		liDom.siblings('input[type="hidden"]').val(advance_url);
	}		
	$("div.zhengquan #closeBtnInput").click();
	$(".advanceurl").val('');
	$(".advancetitle").val('');
	removeAdvEvent();
	addAdvEvent();
}
function addAdvEvent(){
	//更改凭证变成状态
	$(".icon-tianjia1").on('click',function(){
		$(".advancetitle").css('border-color', "#cccccc");
		$(".advanceurl").css('border-color', "#cccccc");
		$('.panel #breadcrumb li').attr('name','credential_registration'); 
		$('#save').attr('name','salescategoryRegistration_login'); 
		$("#save_name").val("");
		$("#save_url").val("");
		var nameDom = $(".panel .breadcrumb li.i18n");
		part_language_change(nameDom); 
		part_language_change($("#save"));   
		part_language_change($('.panel #breadcrumb li')); 
	})
	$('li.s1 span').on('click',function(){
		$('.panel #breadcrumb li').attr('name','credential_change'); 
		$('#save').attr('name','button_change');
		$(".advancetitle").css('border-color', "#cccccc");
		$(".advanceurl").css('border-color', "#cccccc");
		var nameDom = $(".panel .breadcrumb li.i18n #save");
		part_language_change(nameDom);     
		part_language_change($("#save"));   
		part_language_change($('.panel #breadcrumb li'));     
	})
	$('ul.advance_url li .icon-guanbi').on('click',function(e){
		$(this).closest('li').remove();
	})
	$('ul.advance_url li span').on('click',function(e){
		pingZhengFlag = true;
		$(".advancetitle").css('border-color', "#cccccc");
		$(".advanceurl").css('border-color', "#cccccc");
		var name = $(this).text(),
		url = $(this).siblings('input[type="hidden"]').val();
		var indexLi = $(this).parents("ul.advance_url").find("li span").index(this);
		layer.open({
			type: 1,
			title: false,
			closeBtn: 0,
			shadeClose: true, //点击遮罩关闭层
			area: ['400px', 'auto'],
			content: $('div.zhengquan'),
			success: function(layero, index) {
				layero.find('input#save_name').val(name);
//				console.log(layero.find('input#save_name').val(name))
				layero.find('input#save_name').attr('index',indexLi);
				layero.find('input#save_url').val(url);
				var closeBtn = layero.find('.iconfont.icon-guanbi,div.zhengquan #closeBtnInput');
				closeBtn.on('click', function() {
					layer.close(index);
				})				
			}
		});
	})
}
function removeAdvEvent(){
	$('ul.advance_url li .icon-guanbi').unbind('click');
	$('ul.advance_url li span').unbind('click');
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
	vatrate:null,
	realSaleTax:0,
	realSaleTaxFlg:0,
	getRealSaleTax:function(){
	   return this.realSaleTax;
	},
	getRealSaleTaxFlg:function(){
	  return this.realSaleTaxFlg; 
	},
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
	if ($('#switch-state1').bootstrapSwitch('state')){
		var valOther = $("#cost_foreign_type option:selected").text();
	}else {
		var valOther = personMessage['money'+toplange];
	}
	var val = personMessage['money'+toplange];
	if(val == "null" || val == '') {
		val = "RMB";
	}
	$('.sale_person_code').text(val);
    $('.cost_person_code').text(valOther);
    $('.person_code').text(val);
    $('#moneytype').html(val);
    fortype=val;
}
function advanceDelete(){
	var msg = "";
	var confirmTitle = $.getConfirmMsgTitle();
	msg = showConfirmMsgHandler("SURE_DELETE");
	$.messager.confirm(confirmTitle,msg, function(r){
		if(r)
		{
			var input_no =urlPars.parm('inputno');
			var job_cd =urlPars.parm('jobcd');
		
			var allData = {
				"lendtrn":{
					job_cd:job_cd,
					input_no:input_no,
					lock_flg:locknum
				}
			}
			var lableList = getLable();
			allData['lableList'] =lableList;//标签
		 	var path = $.getAjaxPath()+"LendtrnDelete";	
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
			           	$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
			           	showLockInfoMsgHandler("STATUS_VALIDATEPOWER_ERROR","/jczh/cost_list.html?view=6");
			           	return false;
		            	}
			           	if(data.data==-2){//计上验证
			           	$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
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
//	if(!showConfirmMsgHandler("DELETECONFIRM")){
//		return false;
//	}
	

}
//获取当前时间，格式YYYY-MM-DD
    function getNowFormatDate() {
        var date = new Date();
        var seperator1 = "-";
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        var strDate = date.getDate();
        if (month >= 1 && month <= 9) {
            month = "0" + month;
        }
        if (strDate >= 0 && strDate <= 9) {
            strDate = "0" + strDate;
        }
        var currentdate = year + seperator1 + month + seperator1 + strDate;
        return currentdate;
    }
//外货下拉切换时获取信息
function getMoneyCode(itemcd) {
	var moneyMessage = objStorage.getMoneyMessage();
	var saleCode = null;
	var salePoint = null;
	var val = null;
	var toplange = $('#language').val();
	for(var i in moneyMessage) {
		if(moneyMessage[i].itemcd == itemcd) {
			saleCode = moneyMessage[i].changeunit;
			salePoint = moneyMessage[i].itemvalue;
			switch(toplange) {
				case "jp":
					val = moneyMessage[i]['itemname_jp'];
					break;
				case "zc":
					val = moneyMessage[i]['itmname'];
					break;
				case "zt":
					val = moneyMessage[i]['itemname_hk'];
					break;
				case "en":
					val = moneyMessage[i]['itemname_en'];
					break;
			};

		}
	}
	return {
		"val": val,
		"saleCode": saleCode,
		"salePoint": salePoint
	};
}
function setfroeginlength(len){
	
	if(len != undefined || !$('#switch-state1').bootstrapSwitch('state'))
    {
            lenone = len==undefined?pointNumber:len;
            bus_price_common(1,len==undefined?pointNumber:len);
    }else{
    	var languagefor = $('#cost_foreign_type').val(); 
		for(var i=0;i<foreignLen.length;i++){
			if(languagefor==foreignLen[i].itemcd){
				lenone = foreignLen[i].itmvalue;
				bus_price_common(1,foreignLen[i].itmvalue);
				return;
			}
		}
    }
	
};
		
