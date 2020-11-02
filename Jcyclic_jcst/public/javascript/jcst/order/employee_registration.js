$(function() {
	isCheckLogin(1);
	initUserInfo();
	$(".language_conversion").bind('change', function() {
        changeInitDataByLange();
    });
	
});
function changeInitDataByLange()
{
	var flg = checkUserNodeByNodeId(77);
	var data = ucData.userCreateDataByInit; 
	setPageLangTypRadio(data['langTypList'],data['langtyp']);
	setPageDepartRadio(data['departList'],data['departCD']);
	setPageLevelRadio(data['levelList'],data['level']);
	setPageCompanyCheck(data['comapnyList'],data['checkedComapnyList']);
	setUserRoleList(data['roleList'],data['userRoleList']);
	
	if(!flg)
	{
		$('#cuCompanyList').hide();
	}
}
function checkUserNodeByNodeId(nodeId)
{
	var userInfo = ucData.userInfo;
	var flg = false;
	for(var i=0;i < userInfo.uNodeList.length;i++)
	{
		if(userInfo.uNodeList[i]['nodeID'] == nodeId)
		{
			flg = true;
			break;
		}
	}
	return flg;
}
function initUserInfo()
{
	var userID = "";
	$('#employeeBtn').attr("name","suppliersRegistration_login");
	$('#employeeRegistrationTitleID').attr("name","employee_registration_title");
	
	if(urlPars.hasParm("userID"))
	{
		userID = urlPars.parm("userID");
		$('#userIDByInput').val(userID);
		$('#userPwd').removeClass("required");
		$('#employeeBtn').attr("name","employeeRegistration_upload");
		$('#employeeRegistrationTitleID').attr("name","employee_edit_title");
		readLanguageFile()
	}
	var pars = {"userCD":userID};
	var path = $.getAjaxPath()+"userInitInfo";
	
	$.ajax({
		url:path,
		headers: {"requestID":$.getRequestID(),"requestName":$.getRequestNameByMst()},
		data:JSON.stringify(pars),
		success:function(data){
           if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
           	$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
           	if(data[$.getRequestDataName()] != null)
           	{
           		if(userID != "")
           		{
           			$.setInputVal({'0':data[$.getRequestDataName()]});
           		}
           		ucData.userCreateDataByInit = data[$.getRequestDataName()];
           		ucData.userInfo = data["userInfo"];
           		changeInitDataByLange();
           		//setPageLangTypRadio(data[$.getRequestDataName()]['langTypList'],data[$.getRequestDataName()]['langtyp']);
           		//setPageDepartRadio(data[$.getRequestDataName()]['departList'],data[$.getRequestDataName()]['departCD']);
           		//setPageLevelRadio(data[$.getRequestDataName()]['levelList'],data[$.getRequestDataName()]['level']);
           		//setPageCompanyCheck(data[$.getRequestDataName()]['comapnyList'],data[$.getRequestDataName()]['checkedComapnyList']);
           		setUserHistoryList(data[$.getRequestDataName()]['userHistoryByUp']);
           		//setUserRoleList(data[$.getRequestDataName()]['roleList'],data[$.getRequestDataName()]['userRoleList']);
           	}else{
           		showErrorHandler("PAGE_INIT_FAIL","ERROR","ERROR");
           		window.history.back(-1); 
           	}
           }else{
           	showErrorFunHandler(data[$.getRequestMetaName()].result,"ERROR","ERROR");
           }
       },
		error:function(data){
	   		showErrorFunHandler("CONNECTION_SERVER_ERROR","ERROR","ERROR");
		}
	});
}
ucData = {userCreateDataByInit: null}
function setPageLangTypRadio(langTypList,useLangtyp)
{
	var langInfoBytop = getCommonCodeByLange("_");
	var showLange = langInfoBytop.showLange;
	var topLangeToNumber = langInfoBytop.topLangeToNumber;
	var str = '';
	var checkSre = "";
	
	for(var i=0;i<langTypList.length;i++)
	{
		checkSre = isRadioCheckedHandler(useLangtyp,langTypList[i]["itemcd"],topLangeToNumber);
		str += '<div class="col-md-3">'+
              	'<div class="radio">'+
              		'<label class="radio">'+
		                '<input type="radio" inputradio="" value="'+langTypList[i]["itemcd"]+'" name="langtyp" '+checkSre+'>'+
		                '<span class="i18n">'+langTypList[i][showLange]+'</span>'+
		            '</label>'+
              	'</div>'+			                    
              '</div>';
	}
	$('#langeList').html(str);
}

function isRadioCheckedHandler(useLangtyp,itemID,topLangeToNumber)
{
	var checkSre = "";
	if(useLangtyp != null )
	{
		if(itemID == useLangtyp)
		{
			checkSre = 'checked = "checked"';	
		}else{
			checkSre = "";	
		}
	}else{
		if(topLangeToNumber == itemID)
		{
			checkSre = 'checked = "checked"';	
		}else{
			checkSre = "";	
		}
	}
	return checkSre;
}
function setPageDepartRadio(departList,useLangtyp)
{
	var langInfoBytop = getCommonCodeByLange("_");
	var showLange = langInfoBytop.showLange;
	var topLangeToNumber = langInfoBytop.topLangeToNumber;
	var str = '<label class="control-label i18n" >所属部門</label>';
	var checkSre = "";
	for(var i=0;i<departList.length;i++)
	{
		checkSre = isRadioCheckedHandler(useLangtyp,departList[i]["itemcd"],topLangeToNumber);
        str +=  '<div class="radio block">'+
                '<label class="radio">'+
					'<input type="radio" inputradio="" value="'+departList[i]["itemcd"]+'" name="departCD" '+checkSre+'>'+
					'<span class="i18n" >'+departList[i][showLange]+'</span>'+
				'</label>'+
              '</div>';
	}
	$('#departList').html(str);
}

function setPageLevelRadio(levelList,useLangtyp)
{
	var langInfoBytop = getCommonCodeByLange("_");
	var showLange = langInfoBytop.showLange;
	var topLangeToNumber = langInfoBytop.topLangeToNumber;
	var str = '';
	var checkSre = "";
	for(var i=0;i<levelList.length;i++)
	{
		checkSre = isRadioCheckedHandler(useLangtyp,levelList[i]["itemcd"],topLangeToNumber);
        str += '<div class="col-md-3">'+
                  '<div class="radio">'+
                    '<label class="work-level-radio custom-radio nowrap radio">'+
						'<input type="radio" name="level" inputradio="" value="'+levelList[i]["itemcd"]+'"  '+checkSre+'>'+
						'<span class="i18n">'+levelList[i][showLange]+'</span>'+
					'</label>'+
                  '</div>'+
                '</div>';
	}
	$('#levelList').html(str);
}
function setPageCompanyCheck(companyList,checkedComapnyList)
{
	var str = '<label class="control-label i18n" >参照可能会社</label>';
	for(var i=0;i<companyList.length;i++)
	{
		var isChecked = "";
		if(checkedComapnyList != null && checkedComapnyList.length > 0)
		{
			for(var j = 0;j<checkedComapnyList.length;j++)
			{
				if(companyList[i]['company_cd'] == checkedComapnyList[j]['company_cd'])
				{
					isChecked = "checked='checked'";
					break;
				}
			}
		}
    	str +=	'<div class="checkbox">'+
          	  		'<label>'+
			  			'<input '+isChecked+' type="checkbox" name="companyList" value="'+companyList[i]['company_cd']+'">'+
			  			'<span class="i18n" >'+companyList[i]['company_name']+'</span>'+
			  		'</label>'+
            	'</div>';
	}
	
	$('#cuCompanyList').html(str);
}
function setUserRoleList(roleList,userRoleList)
{
	str = '<label class="control-label i18n" name="employeeRegistration_role">ロール</label>';
	for(var i=0;i<roleList.length;i++)
	{
		if(roleList[i]['isOn'] == 0)
		{
			continue;
		}
		var isChecked = "";
		if(userRoleList != null && userRoleList.length > 0)
		{
			for(var j = 0;j<userRoleList.length;j++)
			{
				if(roleList[i]['roleID'] == userRoleList[j]['roleID'])
				{
					isChecked = "checked='checked'";
					break;
				}
			}
		}
		str += '<div class="checkbox"><label>'+
				'<input '+isChecked+' type="checkbox" id="inlineCheckbox1" class="required" name="roles" value="'+roleList[i]['roleID']+'"><span class="i18n" name="">'+roleList[i]['roleName']+'</span>'+
			   '</label></div>';
	}
	$('#cuRoleList').html(str);
}

function createUser()
{
	if(!validataRequired())
	{
		return ;
	}
	var flg = checkUserNodeByNodeId(77);
	var isCompanyFlg = false;
	var path = $.getAjaxPath();
	var pars = $.getInputVal();
	if($('#inlineCheckboxF').prop('checked'))
	{
		pars.delFlg = 1;
	}else{
		pars.delFlg = 0;
	}
	if($('#userIDByInput').val() != "")
	{
		path += "editUserInfo";
		pars.userCD = $('#userIDByInput').val();
		
	}else{
		path += "createUser";
	}
	if(pars.companyList != "" && pars.companyList != undefined)
	{
		if(flg)
		{
			var roles = pars.roles;
			var arr = roles.split(","); 
			for(var i = 0;i < arr.length;i++)
			{
				if(arr[i] < 101 && arr[i] != "")
				{
					isCompanyFlg = true;
					break;
				}
			}
		}else{
			isCompanyFlg = true;
		}
	}else{
		isCompanyFlg = true;
	}
	
	if(isCompanyFlg)
	{
		$.ajax({
			url:path,
			headers: {"requestID":$.getRequestID(),"requestName":$.getRequestNameByMst()},
			data:JSON.stringify(pars),
			success:function(data){
	           if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
	           	$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
	           	if(data[$.getRequestDataName()] != 0 && data[$.getRequestDataName()] != null)
	           	{
	           		if(data[$.getRequestDataName()] == 999)
	           		{
	           			showErrorHandler("LOGIN_NAME_IS_HAVE","INFO","INFO");
	           		}else{
	           			showErrorHandler("EXECUTE_SUCCESS","INFO","INFO");
		           		if(data[$.getRequestDataName()] == 999)
		           		{
		           			logoutHandler('editUser');
		           		}else{
		           			window.history.back(-1); 
		           		}
	           		}
	           	}else{
	           		showErrorHandler("EXECUTE_FAIL","ERROR","ERROR");
	           	}
	           }else{
	           	showErrorFunHandler(data[$.getRequestMetaName()].result,"ERROR","ERROR");
	           }
	       },
			error:function(data){
		   		showErrorFunHandler("CONNECTION_SERVER_ERROR","ERROR","ERROR");
			}
		});
	}else{
		showErrorHandler("CHOOSE_NODE_COMPANY_ERROR","ERROR","ERROR");
	}
}

function setUserHistoryList(upList)
{
	str = "";
	for(var i = 0;i < upList.length;i++)
	{
		str += '<div class="log-box">'+
                  '<div class="log-user">'+
                    '<div class="log-tag"><i class="iconfont icon-pencil"></i>'+
                    	'<span class="i18n" name="employeeRegistration_upload">変更</span>'+
                    '</div>'+
                    '<div class="user-box">'+
                      '<div class="user-icon"><i class="iconfont icon-user" style="color:blue;"></i></div>'+
                      '<div>'+
                        '<div class="user-username">'+upList[i].nickname+'</div>'+
                        '<div class="user-timestamp">'+upList[i].upTime+'</div>'+
                      '</div>'+
                    '</div>'+
                  '</div>'+
                  '<div class="log-contents">'+
                    '<i class="iconfont icon-angle-double-right"></i>'+upList[i].upUserCD+
                    '<span class="i18n" name="employeeRegistration_staff">'+upList[i].upUserName+'</span>'+
                  '</div>'+
                '</div>';
	}
	$('#userHistory').html(str);
}
