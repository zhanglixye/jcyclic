$(function(){
	/**上传文件input样式  -- 按需调用*/
	file_i18n();
	isCheckLogin(1);
	initDataGridHandler('employee_list',10,'userCD','top',true,'isHasFn');
	if(!urlPars.hasParm("sideBar"))
	{
		getSessionByEmpSearch();
	}else{
		employeeInitHandler('initFlg');
	}
	var powerList = JSON.parse($.getNodeList());
	
	if(!isHavePower(powerList,[76,77]))
	{
		$('.empFile').hide();	
	}
})
function employeeInitHandler(searchFlg)
{
	var path = $.getAjaxPath()+"employeeInitInfo";
	var pars = $.getInputVal();
	if(searchFlg != "searchFlg")
	{
		if(urlPars.hasParm("roleID"))
		{
			pars.roleID = urlPars.parm("roleID");
		}
	}else{
		setSessionByEmpSearch();
	}
	pars.searchFLg = searchFlg;
	$.ajax({
		url:path,
		headers: {"requestID":$.getRequestID(),"requestName":$.getRequestNameByMst()},
		data:JSON.stringify(pars),
		success:function(data){
           if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
           	if(data[$.getRequestDataName()] != null)
           	{
           		var selectDomIds = new Array();
           		var userData = data[$.getRequestDataName()]['userList'];
				var total = userData.length;
				var dataList = {
					total:total,
					rows:userData
				}
				if(dataList.total == 0){
					$('.switch_table_none').removeClass('hidden');
					$('.switch_table_none center').text(part_language_change_new("TABLE_CHECK_NO"));
				}else{
					$('.switch_table_none').addClass('hidden');
					$('.switch_table_none center').text(part_language_change_new("TABLE_CHECK_NO"));
				}
				$('#employee_list').datagrid('options').pageNumber = 1;
           		$('#employee_list').datagrid({loadFilter: pagerFilter}).datagrid('loadData',dataList);
           		
           		if(searchFlg == "initFlg")
           		{
           			sessionStorage.setItem("dataList",JSON.stringify(data[$.getRequestDataName()]));
           		}
           		
           		if(data[$.getRequestDataName()]['roleList'] == null || data[$.getRequestDataName()]['levelList'] == null)
           		{
           			//setDepartEmpAndRole(JSON.parse(sessionStorage.getItem("dataList")));
           		}else{
           			setDepartEmpAndRole(data[$.getRequestDataName()]);
           		}
           		if(!isHavePower(data[$.getRequestUserInfoName()].uNodeList,[76]))
           		{
           			$('#empAddBtn').hide();
           			$('#employeeEditBtn').hide();
           			$('#employeeRelevanceBtn').hide();
           		}
           		//table高度  默认加高5px
				var hei = $('.panel-body-noheader').css('height');
				if(hei) {
					var hei = parseInt(hei) + 5;
				}
				$('.panel-body-noheader').css('max-height', hei);
           	}else{
           		showErrorHandler('PAGE_INIT_FAIL','info','info');
           	}
           	$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
           }else{
           	showErrorFunHandler(data[$.getRequestMetaName()].result,"ERROR","ERROR");
           }
       },
		error:function(data){
	   		showErrorFunHandler("CONNECTION_SERVER_ERROR","ERROR","ERROR");
		}
	});
}
function setRoleList(roleList)
{
	var roleID = '';
	if(urlPars.hasParm("roleID"))
	{
		roleID = urlPars.parm("roleID");
	}
	var roleSelectChilds = '<option value="" ></option>';
	var roleNameLast = localStorage.getItem('language');
	var roleName = "";
	for(var i = 0; i < roleList.length;i++)
	{
		roleName = roleList[i]['roleName' + roleNameLast];
		if(roleID != "" && roleID == roleList[i]['roleID'])
		{
			roleSelectChilds += '<option selected="true" value="'+roleList[i]['roleID']+'">'+roleName+'</option>';	
		}else{
			roleSelectChilds += '<option value="'+roleList[i]['roleID']+'">'+roleName+'</option>';
		}
	}
	//$('#roleSelect').html(roleSelectChilds);
	$('#roleList').html(roleSelectChilds);
}
function jumpPage(flg)
{
	if(flg == "add"){
		window.location.href = $.getJumpPath()+'master/employee_registration.html';
	}else if(flg == "relevance")
	{
		var rows = $('#employee_list').datagrid('getSelections');
		//var rows = $('#employee_list').datagrid('getSelections');
		if(rows.length < 1)
		{
			showErrorHandler('NOT_CHOOSE_FAIL','info','info');
			return ;
		}
		if(rows[0].departCD != "004")
		{
			showErrorHandler('NOT_DEPART_FAIL','info','info');
			return ;
		}
		var toplange = $('#language').val();
		var levelName=rows[0]['level'+toplange];
		/*
		switch(toplange){
			case "jp":
				levelName = rows[0].leveljp;
				break;
			case "zc":
				levelName = rows[0].levelcn;
				break;
			case "zt":
				levelName = rows[0].levelhk;
				break;
			case "en":
				levelName = rows[0].levelen;
				break;
			
		};*/
		window.location.href = $.getJumpPath()+'master/customerwedge_change.html?userID='+rows[0].userCD;
	}
	else{
		var rows = $('#employee_list').datagrid('getSelections');
		if(rows.length < 1)
		{
			showErrorHandler('NOT_CHOOSE_FAIL','info','info');
			return ;
		}
		window.location.href = $.getJumpPath()+'master/employee_registration.html?userID='+rows[0].userCD;
	}
	
}
function clearSearchInfoHandler()
{
	$('#userCD').val('');
	$('#nickname').val('');
	$('#departList').val('');
	$('#levelList').val('');
	//$('#delValid').attr("checked","checked");
	$('#roleList').val('');
    $('#delValid').prop('checked',true);
}
function setSessionByEmpSearch()
{
	sessionStorage.setItem("userCD",$('#userCD').val());
	sessionStorage.setItem("nickname",$('#nickname').val());
	sessionStorage.setItem("departList",$('#departList').val());
	sessionStorage.setItem("levelList",$('#levelList').val());
	sessionStorage.setItem("roleList",$('#roleList').val());
	
	sessionStorage.setItem("empDelFlg",$("input[name='delFlg']:checked").val());
	/*
	if($("input[name='delFlg']:checked").val() == 0)
	{
		sessionStorage.setItem("delValid",$('#delValid').attr("checked"));
		sessionStorage.removeItem("allValid");
		sessionStorage.removeItem("delInvalid");
	}
	if($("input[name='delFlg']:checked").val() == 1)
	{
		sessionStorage.setItem("delInvalid",$('#delInvalid').attr("checked"));
		sessionStorage.removeItem("allValid");
		sessionStorage.removeItem("delValid");
	}
	if($("input[name='delFlg']:checked").val() == "")
	{
		sessionStorage.setItem("allValid",$('#allValid').attr("checked"));
		sessionStorage.removeItem("delValid");
		sessionStorage.removeItem("delInvalid");
	}
	*/
}
function getSessionByEmpSearch()
{
	setDepartEmpAndRole(JSON.parse(sessionStorage.getItem("dataList")));
	$('#userCD').val(sessionStorage.getItem("userCD"));
	$('#nickname').val(sessionStorage.getItem("nickname"));
	$('#departList').val(sessionStorage.getItem("departList"));
	$('#levelList').val(sessionStorage.getItem("levelList"));
	$('#roleList').val(sessionStorage.getItem("roleList"));
	
	if(sessionStorage.getItem("empDelFlg") == 0)
	{
		$('#delValid').prop("checked",true);
	}
	if(sessionStorage.getItem("empDelFlg") == 1)
	{
		$('#delInvalid').prop("checked",true);
	}
	if(sessionStorage.getItem("empDelFlg") == "")
	{
		$('#allValid').prop("checked",true);
	}
	employeeInitHandler("searchFlg");
}
function resetDefaultVal()
{
	var defaultData = {};
	if($('#departList').val() != "")
	{
		defaultData.departList = $('#departList').val();
	}
	if($('#levelList').val() != "")
	{
		defaultData.levelList = $('#levelList').val();
	}
	if($('#roleList').val() != "")
	{
		defaultData.roleList = $('#roleList').val();
	}
	SelectObj.setDefaultData = defaultData;
}
function setDepartEmpAndRole(dataList)
{
	var columnArr = new Array(); 
	
	SelectObj.setStringFlg="_";
	SelectObj.selectData =dataList;
	columnArr.push("levelList");
	columnArr.push("departList");
	columnArr.push("roleList");
	SelectObj.setSelectID = columnArr;
	SelectObj.setSelectOfLog();
	setRoleList(dataList['roleList']);
}
