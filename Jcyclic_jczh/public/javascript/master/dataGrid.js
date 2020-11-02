
$(function(){
	/**上传文件input样式  -- 按需调用*/
	file_i18n();
	
//		userchange: function() {
////			alert("1111");
////			return true;
//			//var row = $('#ltd_list').datagrid('getSelected');
//			console.log(row);
////			if(row == null || row == "") {
////				$.messager.alert("警告", '请先选择数据', 'warning');
////			} else {
////				var company = JSON.stringify(row);
////				sessionStorage.setItem('upload', 1);
////				sessionStorage.setItem('company_cd', row.company_cd);
////				sessionStorage.setItem('company', company);
//
//				window.location.href = '../master/wedgemembers_change.html';
//
////			}
//		},
		
		
//		search: function() {
//			var company = {
//				/*company: {
//					company_full_name: $('.company_full_name').val(),
//					del_flg: $('.del_flg [name="radio"]:checked').val()
//				}*/
//			}
//			
//			$.ajax({
//				url: $.getAjaxPath() + "companyMstSelect",
//				data: JSON.stringify(company),
//				headers: {"requestID":$.getRequestID()},
//				success: function(data) {
////					$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
//					if(data[$.getRequestMetaName()].result == $.getRequestStatusName()) {
//						dataGrid(data[$.getRequestDataName()]);
//						$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
//					} else {
//						showErrorFunHandler(data[$.getRequestMetaName()].result, "ERROR", "ERROR");
//					}
//				},
//				error: function(msg) {
//					showErrorHandler("CONNECTION_SERVER_ERROR", "ERROR", "ERROR");
//				}
//			})
//		},
//		turnLog:function(){
//			window.location.href = '../master/ltd_registration.html';
//		}
//	};
	initDataGridHandler("suppliersListTab",10,"data","top",true,"isHasFn")
})