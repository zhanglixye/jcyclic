$(function(){
	//初始化转圈问题
	if(flag!=0&&flag!=null){
		showLockInfoMsgHandler("DATA_IS_NOT_EXIST","/master/suppliers_list.html");
		return false;
	}
	initform();
	//初始化toolTip语言切换不需要传参数
	toolTipLanguage();
	$('.updateflag').addClass('hidden')
	
	if(flag==0){//修改
		$('.addflag').addClass('hidden')
		$('.updateflag').removeClass('hidden')
	}else{
		$('.cldivcdtitle').hide();//新增操作时 コード自动隐藏
	}
	$(".contra_flg").click(function() {
		if($(this).prop('checked')){
			$('#taxtypeset').show();
		}else{
			$('#taxtypeset').hide();
			$('#taxtypeset input.contra_tel_jp.required').removeClass('required');
		}		
	});
	$('#switch-state2').on('switchChange.bootstrapSwitch', function (e,state) {
		console.log(state)
		if(state==false){
			$(".pay_auto_day").val(31);
			$(".pay_auto_month").val(1);
			$("#selectone option[value='0']").prop("selected",true);
			$(".pay_auto_month").attr("disabled","disabled");
			$(".pay_auto_day").attr("disabled","disabled");
			$("#selectone ").attr("disabled","disabled");
		}
		else{
			$(".pay_auto_month").removeAttr("disabled");
			$(".pay_auto_day").removeAttr("disabled");
			$("#selectone ").removeAttr("disabled");
		}
	}
	
	)
})
var flag =getQueryStringValue('flag');

var locknum;
//初始化
//var addflag =2;
function initform(){
//isHasFn已经删除 2019。03。21。为了解决变更页面按钮点击无效的问题
	initDataGridHandler("registrationUi",10,"","bottom",false,"");
	//为easyUi table添加      ----  属性文件
	var dom = $('.datagrid-header .datagrid-htable tr span:not(.datagrid-sort-icon)');
	dom.addClass('i18n');
	dom.eq(0).attr("name", "salescategoryRegistration_application_start_day");
	dom.eq(1).attr("name", "salescategoryRegistration_end_day");
	dom.eq(2).attr("name", "suppliersRegistration_type_taxpayer");
	$("a[name='suppliersRegistration_login']").on('click',function(){
	//switch状态获取
		var flag = $('.switch_date').bootstrapSwitch('state');
	})
	if(flag==0){//修改
		$("#titleName").text(part_language_change_new('suppliers_registration_title_ch'))
	}
	$('.nav-tabs>li').on('click',function(){
		var name = $(this).attr('role');
		$(this).addClass('active');
		$(this).siblings().removeClass('active');
		$(this).parent().parent().find('.'+name).removeClass('tab-pane');
		$(this).parent().parent().find('.'+name).siblings().addClass('tab-pane');
	})	
	obj = {
		taxlistone :null,
		taxlisttwo :null,			 	
	 	editRow : undefined,
	 	 
	 	save:function(){
			//获取纳税人种类
			var tax_type = $('.datagrid-view2 .datagrid-body .datagrid-btable tr.datagrid-row:first-of-type td[field="tax_typename"] div').text() || $('.datagrid-view2 .datagrid-body .datagrid-btable tr.datagrid-row:first-of-type td[field="tax_typename"] input.textbox-text.validatebox-text').val();
			//将第一行设置为结束编辑
			$('#registrationUi').datagrid('endEdit',0);
			var datagridtest = $('#registrationUi').datagrid('getData').rows.length;
			if (datagridtest==0){
				showErrorHandler('TAXTYPENULL','info','info');
				return false;
			}
			var checkflag = check();
			if (checkflag==true){
				$('#myModal').css('display','none');
			}else{
				return;
			}
			var taxtypelist = $('#registrationUi').datagrid('getRows');
			$('.taxtypexs').val(tax_type);
			//$('#myModal').modal('hide');
		},
		add:function(){
			if(this.editRow != 0){
				$('#registrationUi').datagrid('insertRow',{
					index:0,
					row:{
						end_date:'9999-12-31'
					}
				})
			var eUi = $("#registrationUi").datagrid('getColumnOption', 'end_date');
                eUi.editor = {};
			var e = $("#registrationUi").datagrid('getColumnOption', 'tax_typename');

			e.formatter = function(value){
				var showtype =getlanguagename();
				for(var i=0; i<obj.taxlistone.length; i++){
					if (obj.taxlistone[i].itemcd == value) 
						var lange = $('#language').val()
						switch(lange) {
						case "jp":
							return obj.taxlistone[i].itemname_jp;
							break;
						case "zc":
							return obj.taxlistone[i].itemname;
							break;
						case "zt":
							return obj.taxlistone[i].itemname_hk;
							break;
						case "en":
							return obj.taxlistone[i].itemname_en;
						break;
						};
				}
				return value;
			};
			var showtype =getlanguagename();
          	e.editor = {	        
					type: 'combobox', 
					options: {
						valueField: 'itemcd',  
                        textField: showtype,
                        //required: true,  
                        data:obj.taxlistone,
                        editable:false,
					}
				};
				//将第一行设置成可编辑的状态
				$('#registrationUi').datagrid('beginEdit',0);
				this.editRow = 0;
				//控件范围寻找
				if(flag==0){
					var langu = localStorage.getItem('language');
					easyUiLanguageDatagrid(langu);
				}else{
					var langu = localStorage.getItem('language');
					easyUiLanguageDatagrid(langu);
				}
				
			}
		}
	 }
	  $('#myModal').on('shown.bs.modal', function () {
	  			if(obj.taxlisttwo == null){
	  				return;
	  			}
	   			var total = obj.taxlisttwo.length;
				var taxlist = {
					total:total,
					rows:obj.taxlisttwo
				}
				$('#registrationUi').datagrid('loadData',taxlist);
	})
	var allData = {"clmst": {
	             	"cldivcd": null
	    			}
				};
	var changeflag = getQueryStringValue('cldivcd');
	var changeflag3 = getQueryStringValue('flag');
	var changeflag2 = sessionStorage.getItem("divname_full");
	var updateflag = 0;//修改标志
	var divnm = $.getAccountDate();
	console.log(divnm);
	//修改操作
	if ((changeflag !==null && changeflag!=='' && changeflag!==undefined)&&
	(changeflag2 ==null || changeflag2 =='' || changeflag2 ==undefined)){
		updateflag = 1;
		var cldivcd = urlPars.parm('cldivcd');
		//正则验证不能输入日语
		var reg = /^[0-9]*$/;
        var r = cldivcd.match(reg);     
        if(r==null){
            showLockInfoMsgHandler("DATA_IS_NOT_EXIST","/master/suppliers_list.html");
			return false;    
        }   
		//getQueryStringValue('cldivcd');
		var allData = { 
				divnm:divnm,
	         	"clmst": {
	             	"cldivcd": cldivcd
	    			}
   				}
	}	
	var path = $.getAjaxPath()+"TaxTypeInit";
	$.ajax({

	  	type: "POST",
	  	url:path,
	  	contentType:"application/json",
	  	data:JSON.stringify(allData),
	  	dataType:"JSON",
	  	headers: {"requestID":$.getRequestID(),"requestName":$.getRequestNameByMst()},
				success:function(data){
			
	           if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
		           	if(changeflag3=='0'){//变更
		           		//初始化转圈问题
			            if(data.data.clmstList.length==0){
			            	$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
			            	showLockInfoMsgHandler("DATA_IS_NOT_EXIST","/master/suppliers_list.html");
				           	return false;
			            }
		           	}
		           	
					obj.taxlistone =data[$.getRequestDataName()]['taxtypeList'];
					setUserHistoryList(data[$.getRequestDataName()]['userHistoryByInsert']);
					//语言转换
					getGridLanguagetext('tax_typename',data[$.getRequestDataName()]['taxtypeListthree']);
				  	//data[$.getRequestDataName()]['clmstList'][i]["del_flg"]=del;
					if(updateflag==1){//修改操作
						init(data);
					}
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
function checkaccountcd(){
	if($(".contra_flg").is(":checked")){//选中  
 	 $('.taxtypexs').addClass('required')
	}
	if(!validataRequired())
	{
		return ;
	}
	var pay_auto_day = $(".pay_auto_day").val();
	var pay_auto_month = $(".pay_auto_month").val();
	if(pay_auto_month==""||pay_auto_day==""){
		showErrorFunHandler("VALIDATE_FORMAT_ERROR", "ERROR", "ERROR");
		return false;
	}
	var account_cd = $(".account_cd").val();
	var cldivcd = $(".cldivcd").html();
	var allData={account_cd:account_cd,cldivcd:cldivcd};
	var path = $.getAjaxPath()+"ClmstCheck";
	$.ajax({
	  type: "POST",
	  url:path,
	   headers: {"requestID":$.getRequestID(),"requestName":$.getRequestNameByMst()},
	  contentType:"application/json",
	  data:JSON.stringify(allData),
	  dataType:"JSON",
	  success:function(data){
	           if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
	            $.resetRequestID(data[$.getRequestUserInfoName()].requestID);
	            var s=data.data;
	            if(s>0){//客户财务系统编号已存在,是否继续提交
	            var msg = "";
				var confirmTitle = $.getConfirmMsgTitle();
				msg = showConfirmMsgHandler("ACCOUNT_CDCHECK");
				$.messager.confirm(confirmTitle,msg, function(r){
				if(r)
					{
					add();
					}
				});
	            }else{
	            	add();	
	            }
	            
	           }else{
	            showErrorFunHandler("SYSTEM_ERROR","ERROR","ERROR");
	           }
	       },
	       error:function(data){
	          showErrorFunHandler("SYSTEM_ERROR","ERROR","ERROR");
	       }
	 });
}
function add() {
	if(!validataRequired())
	{
		return ;
	}
  //基本信息
 	var cldivcd = $(".cldivcd").html();//新增时在后台生成
 	var account_cd = $(".account_cd").val();
 	var divnm = $(".divnm").val();
 	var divnm_en = $(".divnm_en").val();
 	var divname_full = $(".divname_full").val();
 	var divaddone = $(".addressone").val();
 	var divaddtwo = $(".addresstwo").val();
 	var divaddthree = $(".addressthree").val();
 	var div_tel = $(".div_tel").val();
 	//客户项目补充信息的四种语言 
 	var contacts_name = $(".contacts_name").val();
 	var contacts_name_en = $(".contacts_name_en").val();
 	var contacts_name_jp = $(".contacts_name_jp").val();
 	var contacts_name_hk = $(".contacts_name_hk").val();
 	var self_company_name = $(".self_company_name").val();
 	var self_company_name_en = $(".self_company_name_en").val();
 	var self_company_name_jp = $(".self_company_name_jp").val();
 	var self_company_name_hk = $(".self_company_name_hk").val();
 	var contacts_address = $(".contacts_address").val();
 	var contacts_address_en = $(".contacts_address_en").val();
 	var contacts_address_jp = $(".contacts_address_jp").val();
 	var contacts_address_hk = $(".contacts_address_hk").val();
 	var tel_number = $(".tel_number").val();
 	var tel_number_en = $(".tel_number_en").val();
 	var tel_number_jp = $(".tel_number_jp").val();
 	var tel_number_hk = $(".tel_number_hk").val();
 	var date_auto_setting = '0';
 	if($(".date_auto_setting").is(":checked")){//自动设定  
 	 date_auto_setting = '1';
	}
 	var auto_month = $(".auto_month").val();
 	var auto_day = $(".auto_day").val();
 	//承包方项目补充信息的四种语言
 	var contra_contacts_name = $(".contra_contacts_name").val();
 	var contra_contacts_name_en = $(".contra_contacts_name_en").val();
 	var contra_contacts_name_jp = $(".contra_contacts_name_jp").val();
 	var contra_contacts_name_hk = $(".contra_contacts_name_hk").val();
 	var contra_self_company_name = $(".contra_self_company_name").val();
 	var contra_self_company_name_en = $(".contra_self_company_name_en").val();
 	var contra_self_company_name_jp = $(".contra_self_company_name_jp").val();
 	var contra_self_company_name_hk = $(".contra_self_company_name_hk").val();
 	var contra_address = $(".contra_address").val();
 	var contra_address_en = $(".contra_address_en").val();
 	var contra_address_jp = $(".contra_address_jp").val();
 	var contra_address_hk = $(".contra_address_hk").val();
 	var contra_address1 = $(".contra_address1").val();
 	var contra_address_en1 = $(".contra_address_en1").val();
 	var contra_address_jp1 = $(".contra_address_jp1").val();
 	var contra_address_hk1 = $(".contra_address_hk1").val();
 	var contra_address2 = $(".contra_address2").val();
 	var contra_address_en2 = $(".contra_address_en2").val();
 	var contra_address_jp2 = $(".contra_address_jp2").val();
 	var contra_address_hk2 = $(".contra_address_hk2").val();
 	var contra_tel = $(".contra_tel").val();
 	var contra_tel_en = $(".contra_tel_en").val();
 	var contra_tel_jp = $(".contra_tel_jp").val();
 	var contra_tel_hk = $(".contra_tel_hk").val();
 	var pay_auto_setting = '0';
 	if($(".pay_auto_setting").is(":checked")){//自动设定  
 	 pay_auto_setting = 1;
	}

 	var pay_Date_flg = $(".pay_Date_flg").val();
 	var pay_auto_month = $(".pay_auto_month").val();
 	var pay_auto_day = $(".pay_auto_day").val();
 	//其他
 	var bank_into_one = $(".bank_into_one").val();
 	var bank_info_two = $(".bank_info_two").val();
 	var note = $(".note").val();
// 	var pay_info = $(".pay_info").val();
   	var other_note = $(".other_note").val();
   	var other_note1 = $(".other_note1").val();
   	var other_note2 = $(".other_note2").val();
 	//区分、分类
 	var client_flg = '0';
 	var contra_flg = '0';
 	var pay_flg = '0';
 	var hdy_flg = '0';
 	if($(".client_flg").is(":checked")){//选中  
 	 client_flg = $(".client_flg").val();
	}
	if($(".contra_flg").is(":checked")){//选中  
 	 contra_flg = $(".contra_flg").val();
	}
 	if($(".pay_flg").is(":checked")){//选中  
 	 pay_flg = $(".pay_flg").val();
 	}
 	if($(".hdy_flg").is(":checked")){//选中  
 	 hdy_flg = $(".hdy_flg").val();
	} 
	if(client_flg=='0'&&contra_flg=='0'&&pay_flg=='0'&&hdy_flg=='0'){
		showErrorHandler('CLIENT_FLAG','info','info');
		return false;
	}
 	var del_flg = '0';
 	if($(".del_flg").is(":checked")){//选中  
 	 del_flg = $(".del_flg").val();
	}  

 	var allData = {  
 			
         	"clmst": {
             	  "cldivcd": cldivcd,
              	"account_cd": account_cd, 
              	"divnm": divnm,
              	"divnm_en": divnm_en,
              	"divname_full": divname_full,
              	"divadd1": divaddone,
              	"divadd2": divaddtwo,
              	"divadd": divaddthree,
              	"div_tel": div_tel,
              	"contacts_name": contacts_name,
              	"contacts_name_en": contacts_name_en,
              	"contacts_name_jp": contacts_name_jp,
              	"contacts_name_hk": contacts_name_hk,
              	"self_company_name": self_company_name,
              	"self_company_name_en": self_company_name_en,
              	"self_company_name_jp": self_company_name_jp,
              	"self_company_name_hk": self_company_name_hk,
              	"contacts_address": contacts_address,
              	"contacts_address_en": contacts_address_en,
              	"contacts_address_jp": contacts_address_jp,
              	"contacts_address_hk": contacts_address_hk,
              	"tel_number": tel_number,
              	"tel_number_en": tel_number_en,
              	"tel_number_jp": tel_number_jp,
              	"tel_number_hk": tel_number_hk,
              	"date_auto_setting": date_auto_setting,
              	"auto_month": auto_month,
              	"auto_day": auto_day,
              	"contra_contacts_name": contra_contacts_name,
              	"contra_contacts_name_en": contra_contacts_name_en,
              	"contra_contacts_name_jp": contra_contacts_name_jp,
              	"contra_contacts_name_hk": contra_contacts_name_hk,
              	"contra_self_company_name": contra_self_company_name,
              	"contra_self_company_name_en": contra_self_company_name_en,
              	"contra_self_company_name_jp": contra_self_company_name_jp,
              	"contra_self_company_name_hk": contra_self_company_name_hk,
              	"contra_address": contra_address,
              	"contra_address_en": contra_address_en,
              	"contra_address_jp": contra_address_jp,
              	"contra_address_hk": contra_address_hk,
              	"contra_address1": contra_address1,
              	"contra_address_en1": contra_address_en1,
              	"contra_address_jp1": contra_address_jp1,
              	"contra_address_hk1": contra_address_hk1,
              	"contra_address2": contra_address2,
              	"contra_address_en2": contra_address_en2,
              	"contra_address_jp2": contra_address_jp2,
              	"contra_address_hk2": contra_address_hk2,
              	"contra_tel": contra_tel,
              	"contra_tel_en": contra_tel_en,
              	"contra_tel_jp": contra_tel_jp,
              	"contra_tel_hk": contra_tel_hk,
              	"pay_auto_setting": pay_auto_setting,
              	"pay_Date_flg": pay_Date_flg,
              	"pay_auto_month": pay_auto_month,
              	"pay_auto_day": pay_auto_day,
              	"bank_into_one": bank_into_one,
              	"bank_info_two": bank_info_two,
              	"note": note,
//            	"pay_info": pay_info,
              	"other_note": other_note,
              	"other_note1": other_note1,
              	"other_note2": other_note2,
              	"client_flg": client_flg,
              	"contra_flg": contra_flg,
              	"pay_flg": pay_flg,
              	"hdy_flg": hdy_flg,
              	"del_flg": del_flg,
             	"lock_flg": locknum
    				},
    				"cldivcd": cldivcd,
              	"account_cd": account_cd, 
              	"divnm": divnm,
              	"divnm_en": divnm_en,
              	"divname_full": divname_full,
              	"divadd1": divaddone,
              	"divadd2": divaddtwo,
              	"divadd": divaddthree,
              	"div_tel": div_tel,
              	"contacts_name": contacts_name,
              	"contacts_name_en": contacts_name_en,
              	"contacts_name_jp": contacts_name_jp,
              	"contacts_name_hk": contacts_name_hk,
              	"self_company_name": self_company_name,
              	"self_company_name_en": self_company_name_en,
              	"self_company_name_jp": self_company_name_jp,
              	"self_company_name_hk": self_company_name_hk,
              	"contacts_address": contacts_address,
              	"contacts_address_en": contacts_address_en,
              	"contacts_address_jp": contacts_address_jp,
              	"contacts_address_hk": contacts_address_hk,
              	"tel_number": tel_number,
              	"tel_number_en": tel_number_en,
              	"tel_number_jp": tel_number_jp,
              	"tel_number_hk": tel_number_hk,
              	"date_auto_setting": date_auto_setting,
              	"auto_month": auto_month,
              	"auto_day": auto_day,
              	"contra_contacts_name": contra_contacts_name,
              	"contra_contacts_name_en": contra_contacts_name_en,
              	"contra_contacts_name_jp": contra_contacts_name_jp,
              	"contra_contacts_name_hk": contra_contacts_name_hk,
              	"contra_self_company_name": contra_self_company_name,
              	"contra_self_company_name_en": contra_self_company_name_en,
              	"contra_self_company_name_jp": contra_self_company_name_jp,
              	"contra_self_company_name_hk": contra_self_company_name_hk,
              	"contra_address": contra_address,
              	"contra_address_en": contra_address_en,
              	"contra_address_jp": contra_address_jp,
              	"contra_address_hk": contra_address_hk,
              	"contra_address1": contra_address1,
              	"contra_address_en1": contra_address_en1,
              	"contra_address_jp1": contra_address_jp1,
              	"contra_address_hk1": contra_address_hk1,
              	"contra_address2": contra_address2,
              	"contra_address_en2": contra_address_en2,
              	"contra_address_jp2": contra_address_jp2,
              	"contra_address_hk2": contra_address_hk2,
              	"contra_tel": contra_tel,
              	"contra_tel_en": contra_tel_en,
              	"contra_tel_jp": contra_tel_jp,
              	"contra_tel_hk": contra_tel_hk,
              	"pay_auto_setting": pay_auto_setting,
              	"pay_Date_flg": pay_Date_flg,
              	"pay_auto_month": pay_auto_month,
              	"pay_auto_day": pay_auto_day,
              	"bank_into_one": bank_into_one,
              	"bank_info_two": bank_info_two,
              	"note": note,
//            	"pay_info": pay_info,
              	"other_note": other_note,
              	"other_note1": other_note1,
              	"other_note2": other_note2,
              	"client_flg": client_flg,
              	"contra_flg": contra_flg,
              	"pay_flg": pay_flg,
              	"hdy_flg": hdy_flg,
              	"del_flg": del_flg,
              	"lock_flg": locknum
 				}

		var taxtypeListthree = $('#registrationUi').datagrid('getRows');
		if(contra_flg!=0 && taxtypeListthree.length>0){
			for (x in taxtypeListthree)  
	  		{  
	   			var tax_type = taxtypeListthree[x].tax_type;
	     		if(tax_type == null){
	      		taxtypeListthree[x].tax_type =taxtypeListthree[x].tax_typename ;
	      		}
	  		}  
			allData.taxtypeListthree  = taxtypeListthree ;
//			 showErrorHandler('TAXTYPENEED','info','info');
//			 return true;
		}

 	var changeflag = getQueryStringValue('flag');
	//修改操作 
	if (changeflag =='0'){
		
		var path = $.getAjaxPath()+"clmstUpdate";	
	}
	else{
		//新增
 	 	var path = $.getAjaxPath()+"Clmstinsert";
 	}
	$.ajax({
	  type: "POST",
	  url:path,
	   headers: {"requestID":$.getRequestID(),"requestName":$.getRequestNameByMst()},
	  contentType:"application/json",
	  data:JSON.stringify(allData),
	  dataType:"JSON",
	  success:function(data){
	           if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
	            $.resetRequestID(data[$.getRequestUserInfoName()].requestID);
//	            showErrorHandler('EXECUTE_SUCCESS','info','info');
				if(data.data==-1){
		           	showLockInfoMsgHandler("STATUS_VALIDATEPOWER_ERROR","/master/suppliers_list.html");
		           	return false;
		           }
				if(data.data==-2){//日期验证
			           	$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
			           	showInfoMsgHandlerstop("TAXTYPE_DATETWO","/jczh/cost_list.html?view=6");
			           	return false;
		         }
	            back();
	           }else{
	            showErrorFunHandler(data[$.getRequestMetaName()].result,"ERROR","ERROR");
	           }
	       },
	       error:function(data){
	          showErrorFunHandler("SYSTEM_ERROR","ERROR","ERROR");
	       }
	 });
	sessionStorage.removeItem("cldivcd");
 	//sessionStorage.clear();
}; 
function init(uplist){
	//修改赋值
	var a= "1";
	locknum=uplist.data.clmstList[0].lock_flg;
	obj.taxlisttwo =uplist[$.getRequestDataName()]['taxtypeListthree'];	    
	$(".cldivcd").text(uplist.data.clmstList[0].cldivcd);
	$(".account_cd").val(uplist.data.clmstList[0].account_cd);//分类
	$(".divnm").val(uplist.data.clmstList[0].divnm); 
	$(".divnm_en").val(uplist.data.clmstList[0].divnm_en); 
	$(".divname_full").val(uplist.data.clmstList[0].divname_full); 
	$(".addressone").val(uplist.data.clmstList[0].divadd1); 
	$(".addresstwo").val(uplist.data.clmstList[0].divadd2); 
	$(".addressthree").val(uplist.data.clmstList[0].divadd); 
	$(".div_tel").val(uplist.data.clmstList[0].div_tel);
	
	$(".contacts_name").val(uplist.data.clmstList[0].contacts_name); 
	$(".contacts_name_en").val(uplist.data.clmstList[0].contacts_name_en); 
	$(".contacts_name_jp").val(uplist.data.clmstList[0].contacts_name_jp); 
	$(".contacts_name_hk").val(uplist.data.clmstList[0].contacts_name_hk); 
	
	$(".self_company_name").val(uplist.data.clmstList[0].self_company_name); 
	$(".self_company_name_en").val(uplist.data.clmstList[0].self_company_name_en); 
	$(".self_company_name_jp").val(uplist.data.clmstList[0].self_company_name_jp); 
	$(".self_company_name_hk").val(uplist.data.clmstList[0].self_company_name_hk); 
	
	$(".contacts_address").val(uplist.data.clmstList[0].contacts_address); 
	$(".contacts_address_en").val(uplist.data.clmstList[0].contacts_address_en); 
	$(".contacts_address_jp").val(uplist.data.clmstList[0].contacts_address_jp); 
	$(".contacts_address_hk").val(uplist.data.clmstList[0].contacts_address_hk); 
	
	$(".tel_number").val(uplist.data.clmstList[0].tel_number); 
	$(".tel_number_en").val(uplist.data.clmstList[0].tel_number_en); 
	$(".tel_number_jp").val(uplist.data.clmstList[0].tel_number_jp); 
	$(".tel_number_hk").val(uplist.data.clmstList[0].tel_number_hk); 
	
	if (uplist.data.clmstList[0].Date_auto_setting!=1){ 
				$('#switch-state').bootstrapSwitch('state',false);//日期　自动设定
		 }
	$(".auto_month").val(uplist.data.clmstList[0].auto_month); 
	$(".auto_day").val(uplist.data.clmstList[0].auto_day);
	
	$(".contra_contacts_name").val(uplist.data.clmstList[0].contra_contacts_name); 
	$(".contra_contacts_name_en").val(uplist.data.clmstList[0].contra_contacts_name_en); 
	$(".contra_contacts_name_jp").val(uplist.data.clmstList[0].contra_contacts_name_jp); 
	$(".contra_contacts_name_hk").val(uplist.data.clmstList[0].contra_contacts_name_hk); 
	
	$(".contra_self_company_name").val(uplist.data.clmstList[0].contra_self_company_name);
	$(".contra_self_company_name_en").val(uplist.data.clmstList[0].contra_self_company_name_en);
	$(".contra_self_company_name_jp").val(uplist.data.clmstList[0].contra_self_company_name_jp); 
	$(".contra_self_company_name_hk").val(uplist.data.clmstList[0].contra_self_company_name_hk); 
	
	$(".contra_address").val(uplist.data.clmstList[0].contra_address); 
	$(".contra_address_en").val(uplist.data.clmstList[0].contra_address_en);
	$(".contra_address_jp").val(uplist.data.clmstList[0].contra_address_jp); 
	$(".contra_address_hk").val(uplist.data.clmstList[0].contra_address_hk); 
	
	$(".contra_address1").val(uplist.data.clmstList[0].contra_address1); 
	$(".contra_address_en1").val(uplist.data.clmstList[0].contra_address_en1);
	$(".contra_address_jp1").val(uplist.data.clmstList[0].contra_address_jp1); 
	$(".contra_address_hk1").val(uplist.data.clmstList[0].contra_address_hk1); 
	
	$(".contra_address2").val(uplist.data.clmstList[0].contra_address2); 
	$(".contra_address_en2").val(uplist.data.clmstList[0].contra_address_en2);
	$(".contra_address_jp2").val(uplist.data.clmstList[0].contra_address_jp2); 
	$(".contra_address_hk2").val(uplist.data.clmstList[0].contra_address_hk2); 
	
	$(".contra_tel").val(uplist.data.clmstList[0].contra_tel); 
	$(".contra_tel_en").val(uplist.data.clmstList[0].contra_tel_en); 
	$(".contra_tel_jp").val(uplist.data.clmstList[0].contra_tel_jp); 
	$(".contra_tel_hk").val(uplist.data.clmstList[0].contra_tel_hk);
	
	if (uplist.data.clmstList[0].pay_auto_setting!=1){ 
				$('#switch-state2').bootstrapSwitch('state',false);//付款期望日　自动设定
		 }
	$("#selectone option[value='"+uplist.data.clmstList[0].pay_Date_flg+"']").prop("selected",true); //支付信息登录日
	$(".pay_auto_month").val(uplist.data.clmstList[0].pay_auto_month); 
	$(".pay_auto_day").val(uplist.data.clmstList[0].pay_auto_day);
	
	$(".bank_into_one").val(uplist.data.clmstList[0].bank_into_one); 
	$(".bank_info_two").val(uplist.data.clmstList[0].bank_info_two); 
	$(".note").val(uplist.data.clmstList[0].note); 
//	$(".pay_info").val(uplist.data.clmstList[0].pay_info); 
	$(".other_note").val(uplist.data.clmstList[0].other_note); 
	$(".other_note1").val(uplist.data.clmstList[0].other_note1); 
	$(".other_note2").val(uplist.data.clmstList[0].other_note2);
	if (uplist.data.clmstList[0].del_flg==1){ 
				$('.del_flg').prop("checked",true);
		 }
	if (uplist.data.clmstList[0].client_flg=='1'){ 
				$('.client_flg').prop("checked",true);
		 }
	if (uplist.data.clmstList[0].contra_flg=='1'){ 
				$('.contra_flg').prop("checked",true);
				$('#taxtypeset').show();
		 }
	if (uplist.data.clmstList[0].contra_flg!='1'){ 				
				$('#taxtypeset input.contra_tel_jp.required').removeClass('required');
		 }	  
	if (uplist.data.clmstList[0].pay_flg=='1'){ 
				$('.pay_flg').prop("checked",true);
		 }
	if (uplist.data.clmstList[0].hdy_flg=='1'){ 
				$('.hdy_flg').prop("checked",true);
		} 
	if(uplist.data.taxtypeListthree.length>0){
		var language = $("#language").val();
		if(language=="en"){
			$(".taxtypexs").val(uplist.data.other_note_en);
		}else if(language=="jp"){
			$(".taxtypexs").val(uplist.data.other_note_jp);
		}else if(language=="zt"){
			$(".taxtypexs").val(uplist.data.other_note_zt);
		}
		else if (language == "zc"){
			$(".taxtypexs").val(uplist.data.other_note);
		}
//		$(".taxtypexs").val(uplist.data.other_note);
	}
	//初始化判断発注先扩张项目状态
	fazhu_show();
}
function back() {
	//sessionStorage.clear();
	window.location.href = '../master/suppliers_list.html';
		};
function taxtypeadd(){
		var pars = {"cldivcd":1};
		pars.wedgemembersright = $('#registrationUi').datagrid('getRows');
		return true;
}
function check() {
	var len = $('#registrationUi').datagrid('getData').rows.length;
	if (len>0){
		//校验第一行开始日期
		var start_date_one =$('#registrationUi').datagrid('getData').rows[0].start_date;
		var dateone =Date.parse(start_date_one);
		var today =Date.parse(getNowFormatDate());//当前日期
//		if(dateone<today){
//			showErrorHandler('VATRATE_TIMEDAYS','info','info');
//			$('#registrationUi').datagrid('beginEdit',0);
//				return false;
//		}
	}	
	//一行的时候
	var len = $('#registrationUi').datagrid('getData').rows.length;
	if(len==1){
		var start_date =$('#registrationUi').datagrid('getData').rows[0].start_date;	
		var tax_typename =$('#registrationUi').datagrid('getData').rows[0].tax_typename;
		if(tax_typename==null||tax_typename==''){
			showErrorHandler('NOT_EMPTY','info','info');
			$('#registrationUi').datagrid('beginEdit',0);
			$('#registrationUi').datagrid('getPanel').find('.datagrid-body td[field="tax_typename"] div span.textbox').addClass('border_red');
			validate($('#registrationUi').datagrid('getPanel').find('.datagrid-body td[field="tax_typename"] div span.textbox'),part_language_change_new('NODATE'));
			if(start_date==null||start_date==''){
				$('#registrationUi').datagrid('getPanel').find('.datagrid-body td[field="start_date"] div span.textbox').addClass('border_red');
				validate($('#registrationUi').datagrid('getPanel').find('.datagrid-body td[field="start_date"] div span.textbox'),part_language_change_new('NODAY'));
			}
			//$.rangeDateBox()
			return false;
		}
		else if(start_date==null||start_date==''){
			showErrorHandler('NOT_EMPTY','info','info');
			$('#registrationUi').datagrid('beginEdit',0);
			$('#registrationUi').datagrid('getPanel').find('.datagrid-body td[field="start_date"] div span.textbox').addClass('border_red');
			validate($('#registrationUi').datagrid('getPanel').find('.datagrid-body td[field="start_date"] div span.textbox'),part_language_change_new('NODAY'));
			//控件范围寻找
			if(flag==0){
				$.rangeDateBox();
			}else{
				$.rangeDateBoxOne();
			}
			return false;
		}
	}
	//当条数大于两条时，需要做的判断，并更改第二行的结束时间为第一行的开始时间
	if(len>1){
	//判断第一行的开始时间要比第二号的开始时间大最少两天			
	var start_date =$('#registrationUi').datagrid('getData').rows[0].start_date;	
	var tax_typename =$('#registrationUi').datagrid('getData').rows[0].tax_typename;	
	var two_start_date =$('#registrationUi').datagrid('getData').rows[1].start_date;
	var one_start_date =Date.parse(start_date);
	var two_start_date =Date.parse(two_start_date);
	dateSpan = one_start_date - two_start_date;//第一行日期减第二行日期
	dateSpan = Math.abs(dateSpan);
	iDays = Math.floor(dateSpan / (24 * 3600 * 1000));

   	if(tax_typename==null||tax_typename==''){
		showErrorHandler('NOT_EMPTY','info','info');
		$('#registrationUi').datagrid('beginEdit',0);
		$('#registrationUi').datagrid('getPanel').find('.datagrid-body td[field="tax_typename"] div span.textbox').addClass('border_red');
		validate($('#registrationUi').datagrid('getPanel').find('.datagrid-body td[field="tax_typename"] div span.textbox'),part_language_change_new('NODATE'));
		if(start_date==null||start_date==''){
			$('#registrationUi').datagrid('getPanel').find('.datagrid-body td[field="start_date"] div span.textbox').addClass('border_red');
			validate($('#registrationUi').datagrid('getPanel').find('.datagrid-body td[field="start_date"] div span.textbox'),part_language_change_new('NODAY'));
		}
		//$.rangeDateBox()
		return false;
	}
	else if(start_date==null||start_date==''){
		showErrorHandler('NOT_EMPTY','info','info');
		$('#registrationUi').datagrid('beginEdit',0);
		$('#registrationUi').datagrid('getPanel').find('.datagrid-body td[field="start_date"] div span.textbox').addClass('border_red');
		validate($('#registrationUi').datagrid('getPanel').find('.datagrid-body td[field="start_date"] div span.textbox'),part_language_change_new('NODAY'));
		//控件范围寻找
		if(flag==0){
			$.rangeDateBox();
		}else{
			$.rangeDateBoxOne();
		}
		return false;
	}
	//验证新开始时间大于原开始时间
	else if(one_start_date - two_start_date<0){
		showErrorHandler('TAXTYPE_DATETWO','info','info');
		$('#registrationUi').datagrid('beginEdit',0);
		//测试datebox范围
		//控件范围寻找
		if(flag==0){
			$.rangeDateBox();
		}else{
			$.rangeDateBoxOne();
		}
		return false;
	//验证新开始时间大于原开始时间2天以上
	}else if(iDays<1){
		showErrorHandler('TAXTYPE_DATETWO','info','info');
		$('#registrationUi').datagrid('beginEdit',0);
		//测试datebox范围
		//控件范围寻找
		if(flag==0){
			$.rangeDateBox();
		}else{
			$.rangeDateBoxOne();
		}
		return false;
	}
	
	else{
		var row = $('#registrationUi').datagrid('getData').rows[0];
		//document.getElementById('vat_start_time').value = row.start_date;
		$("#vat_start_date").val(row.start_date);
		$("#vat_end_date").val(row.end_date);		
		var start_date = new Date(start_date);
		start_date.setTime(start_date.getTime()-24*60*60*1000);
		var new_enddate = new Date(start_date).format("yyyy-mm-dd");
		$('#registrationUi').datagrid('updateRow',{
			index:1,
			row:{
				end_date:new_enddate
			}
		})
	}
	
	
	}
	addflag = 0;
	return true;
};
function setUserHistoryList(addList)
{
	str = "";
	var statusFlg = "";
	for(var i = 0;i < addList.length;i++)
	{
		statusFlg = '<span class="i18n" name="employeeRegistration_upload">更新</span>'
		if(addList[i].statusFlg == 1)
		{
			
			statusFlg = '<span class="i18n" name="suppliersRegistration_login">登録</span>';
		}
		str += '<div class="log-box">'+
                  '<div class="log-user">'+
                    '<div class="log-tag"><i class="iconfont icon-pencil"></i>'+
                    	statusFlg+
                    '</div>'+
                    '<div class="user-box">'+
                      '<div class="user-icon"><i class="iconfont icon-ren usercolor" style="color:'+addList[i].usercolor+';"></i></div>'+
                      '<div>'+
                        '<div class="user-username">'+addList[i].addUserName+'</div>'+
                        '<div class="user-timestamp">'+addList[i].addTime+'</div>'+
                      '</div>'+
                    '</div>'+
                  '</div>'+
                  '<div class="log-contents">'+
                    '<i class="iconfont icon-angle-double-right"></i>'+addList[i].addUserCD+
                    '<span class="i18n" name="employeeRegistration_staff"> '+addList[i].nickname+'</span>'+
                  '</div>'+

                '</div>';
        //$(".usercolor").css("color",addList[i].usercolor);       
	}
	
	$('#userHistory').html(str);
}
function getlanguagename(){
		var tlange = $('#language').val()
			var strFlg = '_'
			var showtype;
			switch(tlange) {
			case "jp":
				showtype = 'itemname' + strFlg + 'jp';;
				break;
			case "zc":
				showtype = 'itmname';
				break;
			case "zt":
				showtype = 'itemname' + strFlg + 'hk';
				break;
			case "en":
				showtype = 'itemname' + strFlg + 'en';
			break;
			};
			return showtype;
}

function toolTipLanguage(language){
	if(language == undefined){
		language = localStorage.getItem('language');
	}
	var toolTipO = part_language_change_new('SUPPLER_RE_INFO'),
	toolTipT = part_language_change_new('SUPPLER_RE_INFO_T');
	$('.toolTipO').tooltip({
	    position: 'right',
	    content: '<span style=\"color:#fff\">'+toolTipO+'</span>',
	    onShow: function(){
			$(this).tooltip('tip').css({
				backgroundColor: '#666',
				borderColor: '#666'
			});
	    }
	});
	$('.toolTipT').tooltip({
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

function fazhu_show(){
	var bool = $('#inlineCheckbox2').prop('checked');
	if(bool){
		$(".no_animation").css({"transition":"all 0.5s ease-in-out","transform":"rotate(180deg)","display":"inline-block"});
		$(".supp_hide").show();
		$(".tip_show").attr("value",0);
	}else{
		$(".no_animation").css({"transition":"all 0.5s ease-out-in","transform":"rotate(0deg)","display":"inline-block"});
		$(".supp_hide").hide();
		$(".tip_show").attr("value",1);
	}
}
