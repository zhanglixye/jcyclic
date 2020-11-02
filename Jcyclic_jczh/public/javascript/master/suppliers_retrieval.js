$(function() {
	isCheckLogin(1);
	initDataGridHandler('clientPopTab', 10, 'userCD', 'top', false, '');
})

function clientPopInitHandler() {
	var departCD = $.getDepartID();

	var pars = {
		"departCD": departCD,
		"divnm": $('#exampleInputEmail1').val(),
		"pd": $('#pd').val()
	};
	var path = $.getAjaxPath() + "clPopSearch";

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
					var userData = data[$.getRequestDataName()];
					var total = userData.length;
					var dataList = {
						total: total,
						rows: userData
					}
					$('#clientPopTab').datagrid('loadData', dataList);
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
	var rows = $('#clientPopTab').datagrid('getSelections');
	if(rows.length < 1) {
		showErrorHandler('NOT_CHOOSE_FAIL', 'info', 'info');
		return;
	}
	var parentUrl = parent.location.href;
	var pd = $('#pd').val();
	switch(pd) {
		case "searchClient":
		    var initVal = parent.$('#cldiv_cd').val();
			
			if(parent.$('#report_button').exist())
			{
				parent.$('#cldiv_cd').val(rows[0]['cldivcd']);
				parent.$('#cldiv_name').val(rows[0]['account_cd']);
			}else{
				parent.$('#cldiv_cd').val(rows[0]['cldivcd']);
				parent.$('#cldiv_name').val(rows[0]['divnm']);
			}
			//
			if(rows[0]['client_flg']==1&&rows[0]['pay_flg']==1&&parentUrl.match(/\/job_registration.html/) != null){
				parent.$('#payer_cd').val(rows[0]['cldivcd']);
			    parent.$('#payer_name').val(rows[0]['divnm']);
			}
             //job登录更新画面  得意先改变 卡片清空
            if(initVal!=rows[0]['cldivcd']){
            	 resetCard(parentUrl);
            }
			break;
		case "searchContra":
			parent.$('#payee_cd').val(rows[0]['cldivcd']);
			parent.$('#payee_name').val(rows[0]['divnm']);
			parent.$('#payee_cd').change();
			break;
		case "searchPay":
			parent.$('#payer_cd').val(rows[0]['cldivcd']);
			parent.$('#payer_name').val(rows[0]['divnm']);
			break;
		case "searchHDY":
			parent.$('#g_company').val(rows[0]['cldivcd']);
			parent.$('#g_company_name').val(rows[0]['divnm']);
			break;
	}
	$('#closeBtnInput').click();
}
function resetCard(parentUrl){
	//var parentUrl = parent.location.href;
	if(parentUrl.match(/\/job_registration.html/) != null || parentUrl.match(/\/job_update.html/) != null) {
		var nodelist = JSON.parse(parent.localStorage.nodeList);
		var bear_ren;
		if(enPromotion(nodelist)){
			bear_ren = '<option value="3" class="i18n" name="jobregistration_bear_ren">'+part_language_change_new('jobregistration_bear_ren')+'</option>';
		}else{
			bear_ren ="";
		}
				parent.$("#per").children(':not(.addp)').remove();
				var str = '<div class="col-md-4 panel person" style="padding: 5px; margin-bottom: 0; width: 225px;background: #fff;">' +
					'<div class="person-t">' +
					'<div class="person-t-l">' +
					'<select name="boostrap-select " class="form-control" id="jobCreat-select-1">' +
					'<option value="2" class="i18n" name="jobregistration_business_ren">'+part_language_change_new('jobregistration_business_ren')+'</option>'+ 
					bear_ren
					+'</select>' +
					'</div>' +
					'<div class="person-t-r">' +
					'<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×' +
					'</button>' +
					'</div>' +
					'</div>' +
					'<div class="person-m">' +
					'<div class="person-m-l">' +
					'<i class="iconfont icon-ren" id="person-color-1"></i>' +
					'</div>' +
					'<div class="person-m-m">' +
					'<div id="jobCreat-userlevel-1" class="userlevel">' +
					'</div>' +
					'<div id="jobCreat-usercode-1" class="usercode hidden"></div>' +
					'<div id="jobCreat-usercd-1" class="usercd"></div>' +
					'<div id="jobCreat-username-1" class="username">' +
					'</div>' +
					'</div>' +
					'<div class="person-m-r">' +
					'<i class="iconfont icon-sousuo" id="jobCreat-user-1"></i>' +
					'</div>' +
					'</div>' +
					'<div class="person-b">' +
					'<label>' +
					'<input type="checkbox" id="jobCreat-checkID-1" class="level_flg" value="option4" checked="checked">' +
					'<span class="iconfont icon-duihaok"></span><span class="i18n" name="jobregistration_person_in_charge">'+part_language_change_new('jobregistration_person_in_charge')+'</span>' +
					'</label>' +
					'</div>' +
					'</div>';
				parent.$(".addp").before(str);
				parent.clearCard("jobCreat-select-1","jobCreat-usercd-1","jobCreat-username-1","jobCreat-userlevel-1");
				var url_user = $.getJumpPath() + 'common/employ_retrieval.html';
				parent.$.layerShow("jobCreat-user-1", url_user);
				var md_flg = parent.$("#md-flg").prop("checked");
				var mdReqPower = isHavePower(JSON.parse(parent.$.getNodeList()),[4]);
				if(md_flg) {
					parent.$('#per').find('.person select:nth-child(n) option[value="3"]').addClass("hidden");
				} else {
					if(!mdReqPower){
						$('#per').find('.person select:nth-child(n) option[value="3"]').addClass("hidden");
					}else{
						$('#per').find('.person select:nth-child(n) option[value="3"]').removeClass("hidden");
					}
				}
				//加载语言翻译
//		 		var insertEle = parent.$(".i18n");
//				insertEle.each(function() {
//			    // 根据i18n元素的 name 获取内容写入
//			        $(this).html(part_language_change_new($(this).attr('name')));
//			    });
			}
}

function  enPromotion(nodeList){
	var flg = false;
	for(var i = 0;i<nodeList.length;i++){
		if(nodeList[3].nodeID==4){
			flg = true;
		}
	}
	return flg;
}


