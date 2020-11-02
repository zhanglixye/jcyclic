$(function() {
	isCheckLogin(1);
	initUserInfo();
	$(".language_conversion").bind('change', function() {
        changeInitDataByLange();
    });
	
});
function changeInitDataByLange()
{
	var data = ucData.userCreateDataByInit; 
	setPageLangTypRadio(data['langTypList'],data['langtyp']);
	setPageDepartRadio(data['departList'],data['departCD']);
	setPageLevelRadio(data['levelList'],data['level']);
	setPageCompanyCheck(data['comapnyList'],data['checkedComapnyList']);
	setUserRoleList(data['roleList'],data['userRoleList']);
	setRoleByDepartAndLevel();
	/*
	if(!getCompanyPower(77))
	{
		$('#cuCompanyList').hide();
	}
	*/
	setCompanyIsHide(data['userRoleList']);
}
function setRoleByDepartAndLevel()
{
	if(!urlPars.hasParm("userID"))
	{
		var departV = "";
		var levelV = "";
		var departList = $("input[type='radio']input[name='departCD']");
		var levelList = $("input[type='radio']input[name='level']");
		var roleList = $("input[type='checkbox']input[name='roles']");
		for(var i = 0;i < departList.length;i++)
		{
			if(departList[i].checked)
			{
				departV = departList[i].value;
				break;
			}
		}
		for(var i = 0;i < levelList.length;i++)
		{
			if(levelList[i].checked)
			{
				levelV = levelList[i].value;
				break;
			}
		}
		for(var i = 0;i < roleList.length;i++)
		{
			$('#'+roleList[i].id).prop("checked",false);
		}
		switch (departV){
			case "001":
				$('#roleID3').prop("checked",true);
				break;
			case "002":
				$('#roleID4').prop("checked",true);
				break;
			case "003":
				if(levelV == "001")
				{
					$('#roleID5').prop("checked",true);
				}else{
					$('#roleID6').prop("checked",true);
				}
				break;
			case "004":
				if(levelV == "001")
				{
					$('#roleID7').prop("checked",true);
				}else{
					$('#roleID8').prop("checked",true);
				}
				break;
			case "005":
			case "006":
			case "007":
				if(levelV == "001")
				{
					$('#roleID9').prop("checked",true);
				}else{
					$('#roleID10').prop("checked",true);
				}
				break;
		}
	}
}
function setCompanyIsHide(userRoleList)
{
	var roleList = "";
	var flg = true;
	if(userRoleList != "" && userRoleList != null)
	{
		roleList = userRoleList;
		for(var i=0;i<roleList.length;i++)
		{
			if(roleList[i]['roleID'] < 101)
			{
				flg = false;
				break;
			}
			
		}
	}else{
		var roleList = $("input[type='checkbox']input[name='roles']");
		if(roleList.length > 0)
		{
			for(var i = 0;i < roleList.length;i++)
			{
				if(roleList[i].checked && roleList[i].value < 101)
				{
					flg = false;
					break;
				}
			}
		}
	}
	if(flg)
	{
		$('#cuCompanyList').hide();
	}else{
		$('#cuCompanyList').show();
	}
}
function getCompanyPower(powerItem)
{
	var flg = false;
	var nodeList = ucData.userPowerList;
	for(var i = 0;i < nodeList.length;i++)
	{
		if(nodeList[i].nodeID == powerItem)
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
	//$('#employeeBtn').attr("name","suppliersRegistration_login");
	if(urlPars.hasParm("userID"))
	{
		userID = urlPars.parm("userID");
		$('#userIDByInput').val(userID);
		$('#userPwd').removeClass("required");
		$('#employeeBtn').attr("name","salescategoryList_change");
		$('#employeeRegistrationTitleID').attr("name","employee_edit_title");
		readLanguageFile();
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
           			//解决密码保存问题
           			$('input#nickId').val(data[$.getRequestDataName()].loginName);
           			$('input.email').val(data[$.getRequestDataName()].email);
           			$('input#nickPwd').val('');
           			$('#userPwd').val('********');
           			$('#lockFlg').val(data[$.getRequestDataName()].lockFlg);
           		}
           		ucData.userCreateDataByInit = data[$.getRequestDataName()];
           		ucData.userPowerList = data[$.getRequestUserInfoName()].uNodeList;
           		/*
           		for(var i=0;i<data['userInfo'].userCompanyList;i++)
           		{
           			if(data['userInfo'].userCompanyList[i].company_cd == data['userInfo'].companyID)
           			{
           				ucData.companyType = data['userInfo'].userCompanyList[i].companyType;
           			}
           		}
           		*/
           		changeInitDataByLange();
           		//setPageLangTypRadio(data[$.getRequestDataName()]['langTypList'],data[$.getRequestDataName()]['langtyp']);
           		//setPageDepartRadio(data[$.getRequestDataName()]['departList'],data[$.getRequestDataName()]['departCD']);
           		//setPageLevelRadio(data[$.getRequestDataName()]['levelList'],data[$.getRequestDataName()]['level']);
           		//setPageCompanyCheck(data[$.getRequestDataName()]['comapnyList'],data[$.getRequestDataName()]['checkedComapnyList']);
           		setUserHistoryList(data[$.getRequestDataName()]['userHistoryByUp']);
           		//setUserRoleList(data[$.getRequestDataName()]['roleList'],data[$.getRequestDataName()]['userRoleList']);
           		if(userID == "")
           		{
           			setRoleByDepartAndLevel();	
           			if(!getCompanyPower(77))
					{
						$('#userChooseCompanyBlock').hide();
					}
           		}else{
           			if(!getCompanyPower(77))
					{
						if(data[$.getRequestDataName()]['checkedComapnyList'] == null || 
							data[$.getRequestDataName()]['checkedComapnyList'] == "" ||
							data[$.getRequestDataName()]['checkedComapnyList'].length < 2)
						{
							$('#userChooseCompanyBlock').hide();	
						}
					}
           		}
           		
				if(!getCompanyPower(76))
				{
					$('#userBaseBlock').hide();
				}
           	}else{
           		showInfoMsgHandler("DATA_IS_NOT_EXIST","ERROR","ERROR","master/employee_list.html","");
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
		checkSre = isRadioCheckedHandler(useLangtyp,langTypList[i]["itemcd"],topLangeToNumber,"lang");
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

function isRadioCheckedHandler(useLangtyp,itemID,topLangeToNumber,uFlg)
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
		if(uFlg == "level")
		{
			if(itemID == "001")
			{
				checkSre = 'checked = "checked"';	
			}
		}else{
			if(topLangeToNumber == itemID)
			{
				checkSre = 'checked = "checked"';	
			}else{
				checkSre = "";	
			}
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
		checkSre = isRadioCheckedHandler(useLangtyp,departList[i]["itemcd"],topLangeToNumber,"depart");
        str +=  '<div class="radio block">'+
                '<label class="radio">'+
					'<input onclick="setRoleByDepartAndLevel()" type="radio" inputradio="" value="'+departList[i]["itemcd"]+'" name="departCD" '+checkSre+'>'+
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
		checkSre = isRadioCheckedHandler(useLangtyp,levelList[i]["itemcd"],topLangeToNumber,"level");
        str += '<div class="col-md-3">'+
                  '<div class="radio">'+
                    '<label class="work-level-radio custom-radio nowrap radio">'+
						'<input onclick="setRoleByDepartAndLevel()" type="radio" name="level" inputradio="" value="'+levelList[i]["itemcd"]+'"  '+checkSre+'>'+
						'<span class="i18n">'+levelList[i][showLange]+'</span>'+
					'</label>'+
                  '</div>'+
                '</div>';
	}
	$('#levelList').html(str);
}
function setPageCompanyCheck(companyList,checkedComapnyList)
{
	var isDisabled = "";
	var str = "";
	var isShowFlg = false;
	
	str += '<label class="control-label i18n" >参照可能会社</label>';
	for(var i=0;i<companyList.length;i++)
	{
		isShowFlg = false;
		//没有权限并且是登陆，直接跳出，全都不显示
		if(!getCompanyPower(77) && !urlPars.hasParm("userID"))
		{
			break;
		}
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
		//如果没有权限，是更新页面，只显示自己参照的。
		if(!getCompanyPower(77) && urlPars.hasParm("userID") && isChecked == "checked='checked'")
		{
			isDisabled = 'disabled="disabled"';
			isShowFlg = true;
		}
		if(getCompanyPower(77))
		{
			//所属グループ:2：システム管理、0：グループ管理、1：グループ所属会社
			if(getCompanyPower(84))
			{
				isShowFlg = true;
			}else if(getCompanyPower(85) && companyList[i]['companyType'] != 2)
			{
				isShowFlg = true;
			}else if(getCompanyPower(86) && companyList[i]['companyType'] == 1)
			{
				isShowFlg = true;
			}else{
				isShowFlg = false;
			}
		}
		if(isShowFlg)
		{
			str +=	'<div class="checkbox">'+
      	  		'<label>'+
		  			'<input '+isChecked+ isDisabled +' type="checkbox" name="companyList" value="'+companyList[i]['company_cd']+'">'+
		  			'<span class="iconfont icon-duihaok" ></span><span>'+companyList[i]['company_name']+'</span>'+
		  		'</label>'+
        	'</div>';
		}
	}
	$('#cuCompanyList').html(str);
}
function setUserRoleList(roleList,userRoleList)
{
	var roleNameLast = localStorage.getItem('language');
	str = '<label class="control-label i18n" name="employeeRegistration_role"></label>';
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
				'<input id="roleID'+roleList[i]['roleID']+'" onclick="setCompanyIsHide(\'\')" '+isChecked+' type="checkbox" id="inlineCheckbox1" class="required" name="roles" value="'+roleList[i]['roleID']+'"><span class="iconfont icon-duihaok"></span><span>'+roleList[i]['roleName'+roleNameLast]+'</span>'+
			   '</label></div>';
	}
	$('#cuRoleList').html(str);
	readLanguageFile();
}

function createUser()
{
	if(!validataRequired())
	{
		return ;
	}
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
		pars.lockFlg = $('#lockFlg').val();
	}else{
		path += "createUser";
	}
	pars.flag = "userEditInfo";
	if(validataChoosePowerByCompany(pars))
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
	           		if(data[$.getRequestDataName()] == 998)
	           		{
	           			getRedc($('#nickId'),true);
	           			showErrorHandler("LOGIN_NAME_IS_HAVE","INFO","INFO");
	           		}else if(data[$.getRequestDataName()] == 997)
	           		{
	           			getRedc($('#memberID'),true);
	           			showErrorHandler("MEMBER_ID_IS_HAVE","INFO","INFO");
	           		}else if(data[$.getRequestDataName()] == 996)
	           		{
	           			showLockInfoMsgHandler("STATUS_VALIDATEPOWER_ERROR","master/employee_list.html");
	           		}else{
		           		if(data[$.getRequestDataName()] == 999)
		           		{
		           			//showErrorHandler("EXECUTE_SUCCESS","INFO","INFO");
		           			logoutHandler('editUser');
		           		}else{
		           			//showInfoMsgHandler("EXCUTE_SUCCESS","INFO","INFO","master/employee_list.html","");
		           			window.location.href  = $.getJumpPath()+"master/employee_list.html";
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
function validataChoosePowerByCompany(pars)
{
	var flg = false;
	var isHaveMP = false;
	if(getCompanyPower(77))
	{
		if(pars.companyList != undefined && pars.companyList != "")
		{
			var arr = pars.roles.split(",");
			for(var i=0;i<arr.length;i++)
			{
				if(arr[i] < 101 && arr[i] != "")
				{
					isHaveMP = true;
					break;
				}
			}
			if(isHaveMP)
			{
				flg = true;
			}
		}else{
			flg = true;
		}
	}else{
		flg = true;
	}
	return flg;
}
function setUserHistoryList(upList)
{
	var str = "";
	var statusFlg = "";
	for(var i = 0;i < upList.length;i++)
	{
		
		statusFlg = '<span class="i18n" name="employeeRegistration_upload">変更</span>'
		if(upList[i].statusFlg == 1)
		{
			
			statusFlg = '<span class="i18n" name="suppliersRegistration_login">登録</span>';
		}
		str += '<div class="log-box">'+
                  '<div class="log-user">'+
                    '<div class="log-tag"><i class="iconfont icon-pencil"></i>'+
                    	statusFlg+
                    '</div>'+
                    '<div class="user-box">'+
                      '<div class="user-icon"><i class="iconfont fontLg icon-ren" style="color:'+upList[i].colorV+';"></i></div>'+
                      '<div>'+
                        '<div class="user-username">'+upList[i].upUserName+'</div>'+
                        '<div class="user-timestamp">'+upList[i].upTime+'</div>'+
                      '</div>'+
                    '</div>'+
                  '</div>'+
                  '<div class="log-contents">'+
                    '<i class="iconfont icon-angle-double-right"></i>'+upList[i].memberID+
                    '<span class="i18n" name="employeeRegistration_staff"> '+upList[i].nickname+'</span>'+
                  '</div>'+
                '</div>';
	}
	$('#userHistory').html(str);
}
function backEmpList()
{
	window.location.href  = $.getJumpPath()+"master/employee_list.html";
}
