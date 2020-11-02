$(function(){
	$.jobCommon();
	//初始化页面
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

function inittransferform(){
	var job_cd =urlPars.parm('jobcd');
	$('#jobcd').text(job_cd);
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
 	var path = $.getAjaxPath()+"TrantrnInit";	
	 $.ajax({
	  type: "POST",
	  url:path,
	  contentType:"application/json",
	  data:JSON.stringify(allData),
	  dataType:"JSON",
	  headers: {"requestID":$.getRequestID(),"requestName":$.getRequestNameByJcZH()},
			success:function(data){			
	           if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
	            //setDomChildList(data[$.getRequestDataName()]['TrantrnInput']['itemList'],"itemList");
	            var columnArr = new Array(); 
           			SelectObj.selectData =data[$.getRequestDataName()]['TrantrnInput'];
           			columnArr.push("itemList");
           			SelectObj.setSelectID = columnArr;
           			SelectObj.setStringFlg="_";
           			SelectObj.setSelectOfLog(); 
           		objStorage.personMoneyCode = data["userInfo"];
	           	//页面设置默认货币
				setPersonMoneyCode();
				//本国货币保留小数位数
				var len = data["userInfo"].pointNumber;
				bus_price_common(1,len);
	            //初始化转圈问题
	            if(data[$.getRequestDataName()]['CLDIV'].length==0){
	            	$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
	            	showLockInfoMsgHandler("DATA_IS_NOT_EXIST","/jcst/job_registration_list.html?view=init&menu=se");
		           	return false;
	            }
	            //job信息区块，的得意先，与更新时间
				$("#divnm").text(data[$.getRequestDataName()]['CLDIV'][0]['divnm']);
				$("#upddate").text(data[$.getRequestDataName()]['CLDIV'][0]['upddate']);
				$("#username").text(data[$.getRequestDataName()]['CLDIV'][0]['username']);
				$("#sumamt").text(formatNumber(data[$.getRequestDataName()]['sumcost'][0]['sumamt']));
				$("#updatetime").text(data[$.getRequestDataName()]['sumcost'][0]['updatetime']);
				$("#costupdname").text(data[$.getRequestDataName()]['sumcost'][0]['costupdname']);
				$("#job_name").text(data[$.getRequestDataName()]['CLDIV'][0]['jobname']);//项目名称
				$.layerShowDiv('icon-i','1000','600',2,'../common/jcst/cost_list.shtml',job_cd);
				labelToMySelect(data.data.TrantrnInput.list_lable);
				SelectObj.setLableList = data.data.TrantrnInput.list_lable;
	            $.resetRequestID(data[$.getRequestUserInfoName()].requestID);
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
	            var today= getToday();
				$('.todaynew').datebox('setValue',today)
				var jobcd =job_cd;
				var powerList = data['userInfo'].uNodeList;
	            var bl = isHavePower(powerList, [5,6,7,8]);
	            if(bl){
//					if(data[$.getRequestDataName()]['CLDIV'][0]['saleAddFlag']=='1'){
//						window.job_cd=jobcd;
//						$.layerShowDiv('job_detail','70%','80%',2,'../jcst/job_detail.shtml');
//						//sessionStorage.clear();
//					}else{
//						window.job_cd=jobcd;
//						$.layerShowDiv('job_detail','70%','80%',2,'../jcst/job_detail_nosale.html');
//						//sessionStorage.clear();
//					}

				if(data[$.getRequestDataName()]['CLDIV'][0]['saleAddFlag']=='1'&&data[$.getRequestDataName()]['sumcost']>0){
					window.job_cd=jobcd;
					$.layerShowDiv('job_detail','70%','80%',2,'../jcst/job_detail.shtml');
				}else{
					if(data[$.getRequestDataName()]['CLDIV'][0]['saleAddFlag']=='1'){
					window.job_cd=jobcd;
					$.layerShowDiv('job_detail','70%','80%',2,'../jcst/job_detail.shtml');
					}else{
					window.job_cd=jobcd;
					$.layerShowDiv('job_detail','70%','80%',2,'../jcst/job_detail_nosale.html');
					}
				}
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
function transferAdd(){
	if(!validataRequired())
	{
		return ;
	}
	var job_cd =urlPars.parm('jobcd');
	var adddate = $(".inputDate").val();
	var tranname = $(".tranname").val();
	var tranamt = $(".saleMoment").val();
	var remark = $(".remark").val();
	var tran_status ='0';
	var item_code  = $("#itemList").val();
	var lableList = getLable();
//	if(adddate==''||adddate==null){
//		showErrorHandler("TRAN_DATE_NOTNULL","info","info");
//		return true;
//	}
//	if(tranamt==''||tranamt==null){
//		showErrorHandler("TRAN_AMT_NOTNULL","info","info");
//		return true;
//	}
	var allData = {
		"trantrn":{
			tran_date:adddate,
			item_code:item_code,
			tran_amt:tranamt,
			tran_name:tranname,
			remark:remark,
			tran_status:tran_status,
			job_cd:job_cd
		},
		tran_date:adddate,
		item_code:item_code,
		tran_amt:tranamt,
		tran_name:tranname,
		remark:remark,
		tran_status:tran_status,
		job_cd:job_cd
	}
//	var trantrn = $.getInputVal();
//	trantrn['tran_date']=adddate;
//	trantrn['item_code']=item_code;
//	trantrn['tran_amt']=tranamt;
//	trantrn['tran_name']=tranname;
//	trantrn['remark']=remark;
//	trantrn['tran_status']=tran_status;
//	trantrn['job_cd']=job_cd;
//	var allData ={
//		trantrn:trantrn
//	}
	var lableList = getLable();
	allData['lableList'] =lableList;//标签
	//allData['trantrn'] = trantrn;
 	var path = $.getAjaxPath()+"TrantrnAdd";
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
				if(data[$.getRequestDataName()]==0){
	           		showErrorHandler("ORDER_CREATE_ERROR","info","info");
	           		$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
	           		return false;
	           	}
				if(data.data==-2){//使用日验证
			           	$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
			           	showInfoMsgHandlerstop("DATE_RANGE_ERROR","/jcst/cost_list.html?view=6");
			           	return false;
		            }
	           	$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
	           	$('.tranamt').numberbox('setValue','');
				$(".tranname").val('');
				$(".remark").val('');
				$("#itemList").val('');
				 //插入成功后执行导出pdf
           	 	if ($('#PayConfirmOutPDF').prop("checked")){
           	 		var input_no = data.data;
		     		OutPutPdfHandler(job_cd,'',input_no,'onCostCreate',"","","",0);
		     		
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
	$('.cost_person_code').text(val);
	$('.sale_person_code').text(val);
}  