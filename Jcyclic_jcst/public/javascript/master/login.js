$(function() {
	remPassword(false)
	isCheckLogin(0);	
});
function keyDownHandler()
{
  if (event.keyCode == 13)
  {
    userLogin();
  }
}
/**
* 方法名 userLogin
* 方法的说明 用户登陆
* @param 
* @return 
* @author作者 王岩
* @date 日期 2018.05.09
*/
function userLogin()
{
	if(!validataRequired(true))
	{
		return ;
	}
	var path = $.getAjaxPath()+"login";
	//var path = $.getPdfAjaxPath()+"jobLogOutPut";
	var pars = $.getInputVal();
	//pars.fileName="jobLand";
	//pars.job_cd="J181000005";
	//pars.langTyp=localStorage.getItem('language');
	$.ajax({
		url:path,
		data:JSON.stringify(pars),
		headers: {"requestName":$.getRequestNameByMst()},
		//headers: {"requestName":$.getRequestNameByJcZH()},
		success:function(data){
           	if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
           		if(data[$.getRequestDataName()] != null && data[$.getRequestDataName()].userCD != null && data[$.getRequestDataName()].userCD != "" &&
           			data[$.getRequestDataName()].langtyp != null && data[$.getRequestDataName()].langtyp != "" &&
           			data[$.getRequestUserInfoName()].requestID != null && data[$.getRequestUserInfoName()].requestID != "")
           		{
           			if(data[$.getRequestDataName()].sysLockMsg == "locked")
           			{
           				//$.messager.alert("ERROR","系统已经锁定，暂时不能登陆，请确认！","ERROR");
           				showErrorFunHandler("SYSTEM_LOCK","ERROR","ERROR");
           			}else{
           				remPassword(true);
           				$.setRequestID(data[$.getRequestUserInfoName()].requestID);
	           			$.setUserID(data[$.getRequestDataName()].userCD);
	           			$.setUserName(data[$.getRequestDataName()].nickname);
	           			$.setCompanyID(data[$.getRequestDataName()].companyCD);
	           			$.setLangTyp(data[$.getRequestDataName()].langtyp);
	           			$.setDepartID(data[$.getRequestDataName()].departCD);
	           			$.setAccountDate(data[$.getRequestDataName()].accountDate);
	           			var i18nLanguage = data[$.getRequestDataName()].langtyp;
	           			init_login_language_load(i18nLanguage);
	           			if(data[$.getRequestDataName()].companyCD == '999')
	           			{
	           				window.location.href=$.getJumpPath()+"master/ltd_list.html";	
	           			}else{
	           				window.location.href=$.getJumpPath()+"jcst/top_registration.html";
	           			}
           			}
					
           		}else{
           			//$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
           			//$.messager.alert("ERROR","用户不存在，请检查用户名和密码","ERROR");
           			showErrorHandler("CLIENT_LOGIN_ERROR","ERROR","ERROR");
           		}
           	}else{
           		//$.messager.alert("ERROR","系统错误，请重试","ERROR");
           		showErrorFunHandler(data['metaInfo'].result,"ERROR","ERROR");
           	}
       }
	});
}

/**
* 方法名 userLogin
* 方法的说明 密码变更
* @param 
* @return 
* @author作者 王岩
* @date 日期 2018.05.09
*/
function changePwdHandler()
{
	var msg = "";
	var confirmTitle = $.getConfirmMsgTitle();
	if(!validataRequired(true))
	{
		return ;
	}
	if($('#newPassword').val() != $('#againPwd').val())
	{
		//$.messager.alert("ERROR","请检查新密码和旧密码是否一致","ERROR");
		showErrorHandler("CHANGE_PWD_WARING","Warning","Warning");
		return ;
	}
	
	//msg = showConfirmMsgHandler("IS_CHANGE_PWD");
	//$.messager.confirm(confirmTitle,msg, function(r){
		//if(r)
		//{
			var path = $.getAjaxPath()+"changePwd";
			var pars = $.getInputVal();
			$.ajax({
				url:path,
				data:JSON.stringify(pars),
				headers: {"requestName":$.getRequestNameByMst()},
				success:function(data){
		           if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
		           	if(data[$.getRequestDataName()] == 1)
		           	{
		           		window.location.href=$.getJumpPath();
		           	}else{
		           		//$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
		           		//$.messager.alert("ERROR","变更密码失败，请重试","ERROR");
		           		showErrorHandler("CLIENT_LOGIN_ERROR","ERROR","ERROR");
		           	}
		           }else{
		           		//$.messager.alert("ERROR","系统错误，请重试","ERROR");
		           		showErrorFunHandler(data['metaInfo'].result,"ERROR","ERROR");
		           }
		       },
		       error:function(data){
		       		showErrorHandler("CONNECTION_SERVER_ERROR","ERROR","ERROR");
		       		//$.messager.alert("ERROR","服务器连接失败，请重试","ERROR");
		       }
			});
		//}
	//});
}
/**
* 方法名 remPassword
* 方法的说明 记住密码
* @param void
* @return 
* @author作者 张立学
* @date 日期 2018.12.24
*/
function remPassword(flag){
	if(flag){
		var on_off = $('input[type="checkbox"]').prop('checked');
		if(on_off){
			var username = $('#inputEmail').val();
			var pw = $('#inputPwd').val();
			localStorage.setItem('email',username);
			localStorage.setItem('password',pw);
		}else{
			localStorage.removeItem('email');
			localStorage.removeItem('password');
			return;
		}
	}else{
		var email = localStorage.getItem('email');
		var passwd = localStorage.getItem('password');
		if(email != null && passwd != null){
			$('#inputEmail').val(email);
			$('#inputPwd').val(passwd);
			$('input[type="checkbox"]').prop('checked',true);
		}
	}
}
