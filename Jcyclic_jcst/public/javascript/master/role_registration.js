$(function() {
	roleEditHandler();
	$(".language_conversion").bind('change', function() {
        changeRoleInitDataByLange();
    });
    roleData = {roleCreateDataByInit: null};
});
function drawTitleLine(){
	var titleWidth = $('.title td').width()+2,titleHeight = $('.title td').height()+22;
	var tiltWidth = Math.floor(Math.sqrt(Math.pow(titleWidth,2)+Math.pow(titleHeight,2)));
	var sina = titleHeight/tiltWidth;
	var sinaRadian = (Math.asin(titleHeight/tiltWidth)) * (180/Math.PI);
	$('tr.title .fir_tab div').css('width',tiltWidth);
	$('tr.title .fir_tab div').css('transform',"rotate("+sinaRadian+"deg)");
}
function getPars()
{
	var roleID = "";
	$('#roleBtn').attr('name','taxcostRegistration_login');
	if(urlPars.hasParm("roleID"))
	{
		roleID = urlPars.parm("roleID");
		
		if(roleID=="all")
		{
			$('#roleTitle').attr("name","role_list_all");
			$('#roleBtn').attr("name","customerwedgeChange_update");
			$('title').attr("name","role_list_all");
		}
		else if(roleID=="")
		{
			$('#roleBtn').attr("name","taxcostRegistration_login");
			$('#roleTitle').attr("name","new_custom_registration");
		}else{
			$('#roleBtn').attr('name','salescategoryList_change');
			$('#roleTitle').attr("name","new_custom_change");
		}
	}
	return roleID;
}
function roleEditHandler()
{
	
	var roleID = getPars();
	var pars = {"roleID":roleID};
	var path = $.getAjaxPath()+"roleEdit";
	
	$.ajax({
		url:path,
		headers: {"requestID":$.getRequestID(),"requestName":$.getRequestNameByMst()},
		data:JSON.stringify(pars),
		success:function(data){
           if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
           	$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
           	if(data[$.getRequestDataName()] != null)
           	{
           		roleData.roleCreateDataByInit = data[$.getRequestDataName()];
           		for(var i=0;i<data[$.getRequestUserInfoName()].userCompanyList.length;i++)
           		{
           			if(data[$.getRequestUserInfoName()].userCompanyList[i].company_cd == data[$.getRequestUserInfoName()].companyID)
           			{
           				roleData.companyType = data[$.getRequestUserInfoName()].userCompanyList[i].companyType;
           			}
           		}
           		setBlockHandler(data[$.getRequestDataName()],roleID);
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
function changeRoleInitDataByLange()
{
	var roleID = getPars();
	var roleList = roleData.roleCreateDataByInit;
	setBlockHandler(roleList,roleID);
}
function setBlockHandler(roleList,roleID)
{
	if(roleID != "" && roleList['roleList'].length < 1)
	{
		showLockInfoMsgHandler('DATA_IS_NOT_EXIST',"master/role_list.html");
	}else{
		var roleNameLast = $("#language").val();
		var str = '<td class="fir_tab" style="background-color:#fff;position: relative;">'+
					'<span class="title_span i18n" name="role_login">ロール</span>'+
						'<span class="title_span2 i18n" name="role_login_img">権限</span><div></div></td>';
		
		for(var i=0;i<roleList['nodeList'].length;i++)
		{
			var nodeNameArr = roleList['nodeList'][i]['nodeName'+roleNameLast].split("-");
			str += '<td style="background-color:'+roleList['nodeList'][i]['nodeColor']+';">'
			for(var j = 0;j<nodeNameArr.length;j++)
			{
				str += 	nodeNameArr[j];
				
				if(j != (nodeNameArr.length-1))
				{
					str += "<br/>";
				}else{
					str += "</td>";
				}
			}
		}
		
		var cStr = "";
		var disableStr = "";
		var showEditBtnFlg = false;
		for(var n = 0;n < roleList['roleList'].length;n++)
		{
			//所属グループ:2：システム管理、1：グループ管理、0：グループ所属会社
			if(roleData.companyType ==1 && roleList['roleList'][n]['roleID'] == 1)
			{
				continue;
			}
			if(roleData.companyType ==0 && (roleList['roleList'][n]['roleID'] == 1 || roleList['roleList'][n]['roleID'] == 2))
			{
				continue;
			}
			if(roleList['roleList'][n]['roleID'] < 101 && roleID != "")
			{
				//continue;
				disableStr = 'disabled="disabled"';
			}else{
				disableStr = "";
			}
			if(roleList['roleList'][n]['roleID'] > 100)
			{
				showEditBtnFlg = true;
			}
			cStr += '<tr class="check_box">';
			if(roleID == "")
			{
				n = roleList['roleList'].length-1;
				cStr += '<td style="background-color:rgb(238,238,238);"><div style="font-size: 11px;font-weight:600;margin: 5px;background-color: white;"><input maxlength="100" class="required" id="roleName" style="width: 100%;height:34px;padding-left:5px;" type="text"> </div></td>';
			}else{
				cStr += '<td style="background-color:rgb(238,238,238);"><div style="font-size: 11px;font-weight:600;margin: 5px;background-color: white;">'+
				'<input '+disableStr+' class="required"  value="'+roleList['roleList'][n]['roleName'+roleNameLast]+'" style="width: 100%;height:34px;padding-left:5px;" type="text"/> '+
				'<input type="hidden" value="'+roleList['roleList'][n]['roleID']+'"/>'+
				'<input type="hidden" value="'+roleList['roleList'][n]['lockFlg']+'"/></div></td>';
			}
			
			for(var i=0;i<roleList['nodeList'].length;i++)
			{
				if(roleID == "" || roleList['roleList'][n]['roleID'] > 100)
				{
					disableStr = "";
					if(roleList["nodeList"][i]["nodeID"] == 87 || roleList["nodeList"][i]["nodeID"] == 88 || roleList["nodeList"][i]["nodeID"] == 77)
					{
						disableStr = 'disabled="disabled"';
					}else{
						disableStr = '';
					}
				}
				if(roleID == "")
				{
					cStr += '<td><label><input '+disableStr+' value="'+roleList["nodeList"][i]["nodeID"]+'" type="checkbox"><span class="iconfont icon-duihaok"></span></label></td>';
				}else{
					var flg = false;
					for(var j = 0;j < roleList['nodeListByRole'].length;j++)
					{
						if(roleList['nodeListByRole'][j]['nodeID'] == roleList['nodeList'][i]['nodeID'] &&
							roleList['nodeListByRole'][j]['roleID'] == roleList['roleList'][n]['roleID'] )
						{
							flg = true;
							break;
						}
					}
					//公司参照和公司登录，和公司基本信息修改
					if(flg)
					{
						cStr += '<td><label><input '+disableStr+' value="'+roleList["nodeList"][i]["nodeID"]+'" type="checkbox" checked="checked"><span class="iconfont icon-duihaok"></span></label></td>';
					}else{
						cStr += '<td><label><input '+disableStr+' value="'+roleList["nodeList"][i]["nodeID"]+'" type="checkbox"><span class="iconfont icon-duihaok"></span></label></td>';
					}
				}
			}
			cStr += "</tr>";
		}
		if(roleID != "")
		{
			if(showEditBtnFlg)
			{
				$('#roleBtn').show();
			}else{
				$('#roleBtn').hide();
			}
		}
		
		//防止多国语言滚动条异常的问题
		var strTa = '<table border="1" id="fix-table"><thead><tr id="tTitle" class="title"></tr></thead><tbody id="tbodyList"><tr class="check_box"></tr></tbody></table>';
		$('.main_tab').html(strTa);
		$('#tTitle').html(str);	
		$('#tbodyList').html(cStr);	
		
		drawTitleLine();
		//固定第一行与第一列
		$("#fix-table").tableHeadFixer({"left" : 1});  
		var fix_father = $('#fix-table').parent();
		$(fix_father).css("overflow-y","hidden");
	}
}
function saveNodeList()
{
	var roleID = getPars();
	var parsList = "";
	var tr_arr = [];
	var td_arr =[];
	var td_arr_new=[];
		
	var tr_len = $('tbody#tbodyList tr').length;
	var td_len = $('tbody#tbodyList tr').eq(0).find('td').length;
	var firstBool = "";
	var firstName = "";
	var lockFlg="";
	var firstBoolObj = "";
	for(var i = 0;i < tr_len;i ++){
		for(var j = 0;j < td_len;j++){
			if(j == 0){
				if(roleID != "")
				{
					firstBool = $('tbody#tbodyList tr').eq(i).find('td').eq(j).find("input[type='hidden']").first().val();
					if(firstBool < 101)
					{
						break;
					}
					firstName = $('tbody#tbodyList tr').eq(i).find('td').eq(j).find("input[type='text']").val();
					lockFlg = $('tbody#tbodyList tr').eq(i).find('td').eq(j).find("input[type='hidden']").last().val();
					firstBoolObj={'roleID':firstBool,'roleName':firstName,'lockFlg':lockFlg};
					td_arr.push(firstBoolObj);
				}else{
					
					firstBoolObj={'roleName':$('#roleName').val()};
					td_arr.push(firstBoolObj);
				}
			}else{
				if($('tbody#tbodyList tr').eq(i).find('td').eq(j).find("input[type='checkbox']").prop('checked')){
					var bool = $('tbody#tbodyList tr').eq(i).find('td').eq(j).find("input[type='checkbox']").val();
					td_arr_new.push({'nodeID':bool,'roleID':firstBool});
					
				}
			}
		}
	}
	var roleName = "";
	var isRoleNameFlg = false;
	for(var i = 0;i < td_arr.length;i++)
	{
		if(i == 0)
		{
			roleName = 	td_arr[i].roleName;
		}else{
			if(roleName == td_arr[i].roleName)
			{
				isRoleNameFlg = true;
				break;
			}
		}
	}
	if(isRoleNameFlg)
	{
		showErrorHandler("ROLE_NAME_IS_HAVE","ERROR","ERROR");
		return;
	}
	tr_arr.push({roleList:td_arr});
	tr_arr.push({nodeList:td_arr_new});
	
	var path = $.getAjaxPath()+"saveNodeList";
	var pars = {roleList:tr_arr};
	var requiredFlg = validataRequired();
	if(pars.roleList[1].nodeList.length < 1)
	{
		showErrorHandler("NOT_CHOOSE_FAIL","ERROR","ERROR");
	}else{
		if(!requiredFlg)
		{
			return;
		}
		$.ajax({
			url:path,
			headers: {"requestID":$.getRequestID(),"requestName":$.getRequestNameByMst()},
			data:JSON.stringify(pars),
			success:function(data){
				if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
					$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
					if(data[$.getRequestDataName()].isNameRole == 0)
					{
						setRoleNameRedBorder(tr_len,td_len,data[$.getRequestDataName()].roleName,roleID);
						showErrorHandler("ROLE_NAME_IS_HAVE","ERROR","ERROR");
					}else if(data[$.getRequestDataName()].isNameRole == 996)
					{
						showLockInfoMsgHandler("STATUS_VALIDATEPOWER_ERROR","master/role_list.html");
					}
					else{
						//showErrorHandler("EXECUTE_SUCCESS","info","info");
						window.history.back(-1); 
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
}

function setRoleNameRedBorder(tr_len,td_len,isRoleName,roleID)
{
	var firstBool = "";
	var firstName = "";
	var roleNameList = isRoleName.split(",");
	for(var i = 0;i < tr_len;i ++){
		if(roleID != "")
		{
			firstBool = $('tbody#tbodyList tr').eq(i).find('td').eq(0).find("input[type='hidden']").val();
			if(firstBool < 101)
			{
				continue;
			}
			firstName = $('tbody#tbodyList tr').eq(i).find('td').eq(0).find("input[type='text']").val();
			for(var j = 0;j<roleNameList.length;j++)
			{
				if(roleNameList[j] != "" && firstName == roleNameList[j])
				{
					$('tbody#tbodyList tr').eq(i).find('td').eq(0).find("input[type='text']").addClass('border_red');
				}
			}
		}else{
			getRedc($('#roleName'),true);
		}
	}
}
