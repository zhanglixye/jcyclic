$(function() {
	roleData = {roleListDataByInit: null};
	roleInitHandler();
	//分隔两部分拖拽效果
	$('.list_of_items.drag_allow').arrangeable({
		"dragEndEvent": "click",
		//'dragCheck': drag_arr
	});
	$(".onoff").on('switchChange.bootstrapSwitch', function(e, state) {
		if(state) { //״̬Ϊon
			$(this).offsetParent().parent().parent().find(".list_of_items_no").attr("class", "list_of_items_no on");
			$(this).offsetParent().parent().parent().parent().find('.iconfont.icon-renwu').removeClass('disabled');
			number();
		} else { //״̬Ϊ0FF
			if(flagCheck('.onoff')){
				$(e.target).bootstrapSwitch('toggleState');
				showErrorHandler("SETONE","info","info");
				return;
			}
			$(this).offsetParent().parent().parent().find(".list_of_items_no").attr("class", "list_of_items_no off");
			$(this).offsetParent().parent().parent().parent().find('.iconfont.icon-renwu').addClass('disabled');
			number();
		}
	});
	number();
	$(".language_conversion").bind('change', function() {
        changeRoleListInitDataByLange();
    });
    
});

function number() {
	//1即DESC从大到小，0即ASC从小到大
	var orderIdArray = [];
	var idIndex = [];
	var orderid = $(".list_of_items");
	orderid.each(function(i) {
		var id = $(this).index();
		idIndex[id] = i; //orderid的序号
		orderIdArray.push(id); //orderid的值
	});
	orderIdArray = orderIdArray.sort(function(a, b) {
		return(a > b) ? 1 : -1
	}); //从小到大排序
	//alert(orderIdArray+", "+idIndex);
	var _length = orderIdArray.length;
	for(var i = 0; i < _length; i++) {
		$(".on").eq(i).html(i + 1);
	}
};
function roleInitHandler()
{
	var pars = {};
	var path = $.getAjaxPath()+"roleInit";
	
	$.ajax({
		async:false,
		url:path,
		headers: {"requestID":$.getRequestID(),"requestName":$.getRequestNameByMst()},
		data:JSON.stringify(pars),
		success:function(data){
           if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
           	$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
           	if(data[$.getRequestDataName()] != null)
           	{
           		roleData.roleListDataByInit = data[$.getRequestDataName()];
           		for(var i=0;i<data[$.getRequestUserInfoName()].userCompanyList.length;i++)
           		{
           			if(data[$.getRequestUserInfoName()].userCompanyList[i].company_cd == data[$.getRequestUserInfoName()].companyID)
           			{
           				roleData.companyType = data[$.getRequestUserInfoName()].userCompanyList[i].companyType;
           			}
           		}
           		setBlockHandler(data[$.getRequestDataName()]);
           		if(!isHavePower(data[$.getRequestUserInfoName()].uNodeList,[80]))
           		{
           			$('#roleAddBtn').hide();
           			$('#roleReportBtn').hide();
           		}
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
function changeRoleListInitDataByLange()
{
	var roleList = roleData.roleListDataByInit;
	setBlockHandler(roleList);
}
function setBlockHandler(roleList)
{
	var roleNameLast = $("#language").val();
	if(roleNameLast == null)
	{
		roleNameLast = localStorage.getItem('language');
	}
	
	var str = '';
	for(var i = 0;i < roleList.length;i++)
	{
		if(roleList[i]['roleID'] > 100)
		{
			str += '<div class="list_of_items drag_allow">';
		}else{
			//所属グループ:2：システム管理、1：グループ管理、0：グループ所属会社
			if(roleData.companyType ==1 && roleList[i]['roleID'] == 1)
			{
				continue;
			}
			if(roleData.companyType ==0 && (roleList[i]['roleID'] == 1 || roleList[i]['roleID'] == 2))
			{
				continue;
			}
			str += '<div class="list_of_items" style="border: 1px solid #ecf0f1!important;cursor:default;">';
		}
		
		str +=	'<div class="list_of_items_top">'+
					'<div class="list_of_items_top_left">';
		if(roleList[i]['isOn'] == 1)
		{
			str += '<input data-on="success" class="boostrap-switch onoff" id="switch-state" type="checkbox" checked="checked" data-size="mini">';
		}else{
			str += '<input data-on="success" class="boostrap-switch onoff" id="switch-state" type="checkbox" data-size="mini">';
		}
		str += '</div>'+
					'<div class="list_of_items_top_right">'+
						'<div class="list_of_items_no on" style="border: 1px solid #24b195!important;">'+
						'1'+
						'</div>'+
					'</div>'+
				'</div>'+
				'<div class="list_of_items_bottom">'+
					'<div class="list_of_items_bottom_top" tyle="text-decoration:underline;">';
		if(roleList[i]['roleID'] > 100)
		{
			str +=	'<span class="roleName color_0" style="color:#1abc9c;text-decoration:underline;cursor:pointer;" onclick="editRoleInto('+roleList[i]['roleID']+')">'+roleList[i]['roleName'+roleNameLast]+'</span>';
		}else{
			str +=	'<span class="roleName role_color">'+roleList[i]['roleName'+roleNameLast]+'</span>';
		}
		str +=	'<span class="roleID" style="display:none;">'+roleList[i]['roleID']+'</span>'+
					'</div>'+
					'<div class="list_of_items_bottom_bottom">'+
						'<span onclick="searchUserByRoleID('+roleList[i]['roleID']+')" class="iconfont icon-renwu"></span>'+
					'</div>'+
				'</div>'+
			'</div>';
	}
	$('#roleListBlock').html(str);	
	number();
}
function searchUserByRoleID(roleID)
{
	var path = $.getJumpPath()+'master/employee_list.html?roleID='+roleID+"&sideBar=sideBar";
	location.href = path;
}
function saveRoleList()
{
	var cusnum = $('.list_of_items').get().length;
	var roleListArr = [];	
	var isOnFlg = false;
	for(var i=0;i<cusnum;i++)
	{
		var a = $('.list_of_items').eq(i)
		var roleName = a.find('.list_of_items_bottom_top').find('span.roleName').text();
		var roleID = a.find('.list_of_items_bottom_top').find('span.roleID').text();
		var onflag = $('.boostrap-switch').eq(i).bootstrapSwitch('state');
		var column = $(".list_of_items_no").eq(i).text();
		
		
		var flag = '0';
		if (onflag==true){
			flag = '1';
			isOnFlg = true;
		}
		var roleObj = {
					isOn:flag,
					roleID:roleID,
					roleName:roleName,
					roleOrder:column
				};
		roleListArr.push(roleObj);
	}
	var path = $.getAjaxPath()+"saveRoleList";
	var pars = {
		roleList:roleListArr
	};
	if(isOnFlg)
	{
		$.ajax({
			url:path,
			headers: {"requestID":$.getRequestID(),"requestName":$.getRequestNameByMst()},
			data:JSON.stringify(pars),
			success:function(data){
				if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
					$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
					showErrorHandler("EXECUTE_SUCCESS","info","info");
					//window.location.href  = $.getJumpPath()+"master/role_list.html";
				}else{
					showErrorFunHandler(data[$.getRequestMetaName()].result,"ERROR","ERROR");
				}	
			},
		    error:function(data){
		    	showErrorHandler("CONNECTION_SERVER_ERROR","ERROR","ERROR");
		    }
		 });
	}else{
		showErrorHandler("NOT_CLOSE_ALL_ROLE","info","info");
	}
}
function editRoleInto(roleID)
{
	if(roleID != "")
	{
		window.location.href = $.getJumpPath()+'master/role_login_table.html?roleID='+roleID;	
	}else{
		window.location.href = $.getJumpPath()+'master/role_login_table.html';
	}
	
}

