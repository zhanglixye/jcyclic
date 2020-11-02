$(function() {
	/*
	 * 加载页面时 加载公司数据
	 * edit 修改
	 * search 按条件查找公司数据
	 */

	$(document).ready(function() {
//		sessionStorage.setItem('add', '');
//		sessionStorage.setItem('company_cd', '');
//		sessionStorage.setItem('upload', '');

		$('#ltd_list').datagrid({
			singleSelect: true
		});
		var sideBar = urlPars.parm("sideBar");
		if(sideBar==undefined){
			var keyWord = sessionStorage.getItem("keyWordCompany_K");
			var companyFlg = sessionStorage.getItem("companyDelFlg_K");
			$('.company_full_name').val(keyWord);
	        $('.del_flg [name="radio"]').eq(companyFlg).click();
	        ltdListObj.search();
		}else{
			 sessionStorage.removeItem("keyWordCompany_K");
			 sessionStorage.removeItem("companyDelFlg_K");
			 ltdListObj.search();
		}
		
		initDataGridHandler("ltd_list", 9999999, '', "top", false, "isHasFn");
		var powerList = JSON.parse($.getNodeList());
		if(!isHavePower(powerList,[87])){
			$(".company-add").addClass("hidden");
		}
		if(!isHavePower(powerList,[88,89,90,91])){
			$(".company-change").addClass("hidden");
		}
	});
	ltdListObj = {
		listData:null,
		edit: function() {
			var row = $('#ltd_list').datagrid('getSelected');
			if(row == null || row == "") {
				showErrorHandler("NOT_CHOOSE_FAIL", "ERROR", "ERROR");
			} else {
//				sessionStorage.setItem('upload', 1);
//				sessionStorage.setItem('company_cd', row.company_cd);
				window.location.href = $.getJumpPath() + 'master/ltd_registration.html?company_cd='+row.company_cd;
			}
		},
		search: function() {
			 sessionStorage.setItem("keyWordCompany_K",$('.company_full_name').val());
			 sessionStorage.setItem("companyDelFlg_K",$('.del_flg [name="radio"]:checked').val()==""?2:$('.del_flg [name="radio"]:checked').val());
		
			
			var company = {
				company_full_name: $('.company_full_name').val(),
				del_flg: parseInt($('.del_flg [name="radio"]:checked').val())
			}
			$.ajax({
				url: $.getAjaxPath() + "companyMstSelect",
				data: JSON.stringify(company),
				headers: {
					"requestID": $.getRequestID(),
					"requestName": $.getRequestNameByMst()
				},
				success: function(data) {
//					console.log(data)
					if(data[$.getRequestMetaName()].result == $.getRequestStatusName()) {
						$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
						if(data[$.getRequestDataName()] != null) {
                            var data =  data[$.getRequestDataName()];
                            if(data.length == 0){
								$('.switch_table_none').removeClass('hidden');
								$('.switch_table_none center').text(part_language_change_new("TABLE_CHECK_NO"));
							}else{
								$('.switch_table_none').addClass('hidden');
								$('.switch_table_none center').text(part_language_change_new("TABLE_CHECK_NO"));
							}
							$('#ltd_list').datagrid({loadFilter: pagerFilter}).datagrid('loadData', data);
							ltdListObj.changeData = data;
						} 
					} else {
						showErrorFunHandler(data[$.getRequestMetaName()].result, "ERROR", "ERROR");
					}
				},
				error: function(msg) {
					showErrorHandler("CONNECTION_SERVER_ERROR", "ERROR", "ERROR");
				}
			})
		},
		turnLog: function() {
			//sessionStorage.setItem('add', 1);
			window.location.href = $.getJumpPath() + 'master/ltd_registration.html';
		},
		downloadExcel: function() {
			exportExcelByTab("ltd_list", "CompanyList");
		}
	}
})

