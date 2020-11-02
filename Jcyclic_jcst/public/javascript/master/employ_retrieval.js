$(function() {
	initDataGridHandler('employeeWindowTab', 10, 'userCD', 'top', false, '');
})

function searchEmployeeList() {
	employeePopInitHandler();
}

function employeePopInitHandler() {
	var departId = $('#departList').val();
	/*
	if($('#clientID').val() != "" && departId == "") {
		departId = $.getDepartID();
	}
	*/
	var pars = {
		"sClientID": $('#clientID').val(),
		"departCD": departId,
		"nickname": $('#userName').val(),
		"pd": $('#pd').val()
	};
	var path = $.getAjaxPath() + "employeePopSearch";

	$.ajax({
		url: path,
		headers: {
			"requestID": $.getRequestID(),
			"requestName": $.getRequestNameByMst()
		},
		beforeSend: function(){
			parent.$('.layui-layer.layui-layer-iframe').css('opacity',0)
			var str = part_language_change_new('LOADING_MSG');
			parent.$('.layui-layer-shade').addClass('hidden');
		    parent.$("<div class=\"datagrid-mask\"></div>").css({display:"block",width:"100%",height:"100%"}).appendTo("body");   
		    parent.$("<div class=\"datagrid-mask-msg\" style=\"height:45px;\"></div>").html(str).appendTo("body").css({display:"block",'z-index':999999999,left:'42.5%',top:'47%'}); 
		},
		data: JSON.stringify(pars),
		success: function(data) {
			if(data[$.getRequestMetaName()].result == $.getRequestStatusName()) {
				//ajax问题
				parent.$('.layui-layer-shade').removeClass('hidden');
	     		parent.$(".datagrid-mask").eq(0).remove();   
	     		parent.$(".datagrid-mask-msg").eq(0).remove(); 
	     		parent.$('.layui-layer.layui-layer-iframe').css('opacity',1)
				$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
				if(data[$.getRequestDataName()] != null) {
					var userData = data[$.getRequestDataName()]['userList'];
					var total = userData.length;
					var usercd = [];
					var filterArr = [];
					var dArr = new Array();
					parent.$('#per .person').each(function(index, element) {
						if($(element).find("div .usercd").text() != '' && $(element).find("div .usercd").text() != null && $(element).find("div .usercd").text() != undefined) {
							usercd.push($(element).find("div .usercd").text());
						}
					});
					if(usercd.length > 0) {
						for(var i = 0; i < userData.length; i++) {
                            if(![].inArray(usercd,userData[i]['userCD'])){
                            	filterArr.push(userData[i]);
                            }
						}
						var dataList = {
							total: filterArr.length,
							rows: filterArr
						}

					} else {
						var dataList = {
							total: total,
							rows: userData
						}

					}

					$('#employeeWindowTab').datagrid('loadData', dataList);
					var departData = data[$.getRequestDataName()]['departList'];
					var departcd = data[$.getRequestUserInfoName()].departCD;
					var level = data[$.getRequestDataName()].level;
					
					if($('#pd').val() == 'searchCus' && userData.length >= 0) { //timesheet
						if(level=='2'){
							for(var i = 0; i < departData.length; i++) {
								if(departData[i].itemcd == departcd)
									{
										dArr.push(departData[i]);
									}
								if(departData[i].itemcd != departcd) {
									departData.splice(i, 1);
									i--;
								}
							}
							dataList['departList'] = departData;
							SelectObj.selectData = dataList;
						}
						if(level=='1'){
							var departlevel = ['004','005','006','007'];
								for(var i = 0; i < departData.length; i++){
									if(departData[i].itemcd == departId)
									{
										dArr.push(departData[i]);
									}
									if(departlevel.indexOf(departData[i].itemcd) == -1) {									
											departData.splice(i, 1);
											i--;
									}									
							}
								SelectObj.setDefaultData = departData;
								dataList['departList'] = departData;
								SelectObj.selectData = dataList;
						}
						
					}

					var columnArr = new Array();
					SelectObj.setStringFlg = "_";
					/****************************************/
					if($('#pd').val() == 'isJuser' && departData.length >= 0) {
						var dataList = data[$.getRequestDataName()];
						for(var i = 0; i < departData.length; i++) {
							if(departData[i].itemcd != "004") {
								departData.splice(i, 1);
								i--;
							}
						}
						SelectObj.setDefaultData = departData;
						dataList['departList'] = departData;
						SelectObj.selectData = dataList;
					}else if($('#pd').val() == 'isJmd' && departData.length >= 0) {
						var dataList = data[$.getRequestDataName()];
						
						for(var i = 0; i < departData.length; i++) {
							if(departData[i].itemcd == departId)
							{
								dArr.push(departData[i]);
							}
							if(departData[i].itemcd != "004" && departData[i].itemcd != "005" && 
							departData[i].itemcd != "006" && departData[i].itemcd != "007") {
								departData.splice(i, 1);
								i--;
							}
						}
						SelectObj.setDefaultData = dArr;
						dataList['departList'] = departData;
						SelectObj.selectData = dataList;
					} 
					else{
						SelectObj.setDefaultData = dArr;
						SelectObj.selectData = data[$.getRequestDataName()];
					}
					/****************************************/
					columnArr.push("departList");
					SelectObj.setSelectID = columnArr;
					SelectObj.setSelectOfLog();
					if($('#pd').val() == 'isJuser' && departData.length >= 0) {
						$("#departList").find("option[value='']").addClass("hidden");
					}
				} else {
					showErrorHandler("PAGE_INIT_FAIL", "ERROR", "ERROR");
					window.history.back(-1);
				}
			} else {
				showErrorFunHandler(data[$.getRequestMetaName()].result, "ERROR", "ERROR");
			}
		},
		error: function(data) {
			showErrorFunHandler("CONNECTION_SERVER_ERROR", "ERROR", "ERROR");
		}
	});
}

function setChooseInfo() {
	var rows = $('#employeeWindowTab').datagrid('getSelections');
	
	if(rows.length < 1) {
		showErrorHandler('NOT_CHOOSE_FAIL', 'info', 'info');
		return;
	}
	var thisID = $('#pageFlg').val();
	var selectVal =  parent.$("#"+thisID).parents(".person").find("select").val();
	var flgs = $('#pageFlg').val().split("-");
	if(rows.length>1){
	    var isLast = true; 
		var nowNum = parseInt(flgs[2]);//当前点击的卡片序号
		parent.$("#per").find('.person').each(function(index, element) {
		    //$(element).find("i.icon-sousuo").attr("id")
		     if(nowNum<parseInt($(element).find('.person-m-r').find("i").attr("id").split("-")[2])){
		     isLast=false;
		     }
		
		});
		
		if(!isLast){
		$('#closeBtnInput').click();
		parent.showErrorHandler('PLEAS_ADD_AT_LAST_CARD', 'info', 'info');
		 return ;
		
		}
	
	}
	
	var pd = $('#pd').val()
	if(flgs.length > 1) {
		if(flgs[0] == "jobCreat") {

			var langTyp = parent.$('#language').val();
			//
			for(var i=0;i<rows.length;i++){
				if(i!=0){
				    
				    sessionStorage.setItem("nf",selectVal);
					parent.$(".addp").click();
                   //2代表 多条添加
//                  addCard(thisDom,2);
					parent.$('#jobCreat-userlevel-' + (parseInt(flgs[2])+i)).text(rows[i]['departName' + langTyp]);
				    parent.$('#jobCreat-usercd-' + (parseInt(flgs[2])+i)).text(rows[i]['userCD']);
				    parent.$('#jobCreat-username-' + (parseInt(flgs[2])+i)).text(rows[i]['nickname']);
				    parent.$('#person-color-' + (parseInt(flgs[2])+i)).css('color',rows[i]['usercolor']);
				}else{
					var cardMessage = parent.objStorage.getCardMessage();
					if(cardMessage!=null){
						var arrDelUser = parent.objStorage.getDelCard();
					var usercd = parent.$('#jobCreat-usercd-' + flgs[2])[0].innerText;
					
					var isFind = searchValue(cardMessage,'usercd',usercd,"usercd");
					
					
						//不添加重复的人
					if(![].inArray(arrDelUser,usercd)&&isFind!=""&&usercd!=""){
							arrDelUser.push(usercd);
						}
					}
					
		
				    parent.$('#jobCreat-userlevel-' + flgs[2]).text(rows[i]['departName' + langTyp]);
					parent.$('#jobCreat-usercd-' + flgs[2]).text(rows[i]['userCD']);
					parent.$('#jobCreat-username-' + flgs[2]).text(rows[i]['nickname']);
					parent.$('#person-color-' + flgs[2]).css('color',rows[i]['usercolor']);
				}
				
			}
			sessionStorage.setItem("nf","");
			$('#closeBtnInput').click();

		}

	}
	
	
	if(pd == "searchDD") {
		var langTyp = parent.$('#language').val();
		parent.$('#ddcd').val(rows[0]['userCD']);
		parent.$('#ddname').val(rows[0]['nickname']);
		$('#closeBtnInput').click();
		//如果新选择的值，与登录 者相同，移除担当falg的选中状态
		if($.getUserID!=rows[0]['userCD']){
		 parent.$("#ddflag").prop("checked",false);
		}
	}
	if(pd == "searchCus") {
		var langTyp = parent.$('#language').val();
		var departname = rows[0]['departName' + langTyp]
		var color = rows[0]['colorV']
//		if(rows[0].level == '001') {
			var name =departname + "|" + rows[0]['userCD'] + "|" + rows[0]['nickname'];
//		} 
//		else if(rows[0].level == '002') {
//			var name = "管理 |" + rows[0]['userCD'] + "|" + rows[0]['nickname'];
//		}

		//"管理 | 1234567 | 安部　伊吹"
		parent.$('#customcd').val(rows[0]['userCD']);
		parent.$('#customname').val(name);
		if(color==null||color==''){
			color='1';
		}
		parent.$('#colorv').val(color);
		parent.$("#customcolor").css("color",color);
		$('#closeBtnInput').click();
		parent.initTitle();
	}
	if(pd == "searchLend") {
		var langTyp = parent.$('#language').val();
		var departname = rows[0]['departName' + langTyp]
//		if(rows[0].level == '001') {
			var name = departname + "|" + rows[0]['userCD'] + "|" + rows[0]['nickname'];
//		} 
//		else if(rows[0].level == '002') {
//			var name = "管理 |" + rows[0]['userCD'] + "|" + rows[0]['nickname'];
//		}

		//"管理 | 1234567 | 安部　伊吹"
		parent.$('#customcd').val(rows[0]['userCD']);
		parent.$('#userid').val(rows[0]['userCD']);
		parent.$('#customname').val(name);
		$('#closeBtnInput').click();

	}
}