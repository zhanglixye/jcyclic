/**
* 方法名 $.extend
* 方法的说明 扩展jq增加获取一些共通设置函数
* @return 返回字符串
* @author作者 王岩
* @date 日期 2018.05.09
*/
$.extend({
	//获取Ajax共通地址部分
	getAjaxPath:function(){
		//return "http://127.0.0.1:8080/jczh_jcyclic_front/main/dynamic/";
		//return "http://192.168.0.101:8080/jczh_jcyclic_front/main/dynamic/";
		return "http://192.168.0.37:8080/jczh_jcyclic_front/main/dynamic/";
		//return "http://192.168.0.37:8080/data_jczh_jcyclic_front/main/dynamic/";
		//return "https://testfront.j-cyclic.cn:8443/jcyclic_front/main/dynamic/";//test
		//return "https://demofront.j-cyclic.cn:18443/jcyclic_front/main/dynamic/";//test
		//return "https://front1.j-cyclic.cn:8443/jczh_jcyclic_front/main/dynamic/";//47 HC
		//return "https://front2.j-cyclic.cn:8443/jczh_jcyclic_front/main/dynamic/";//123 HC
		//return "https://front1.j-cyclic.com.cn:8443/jczh_jcyclic_front/main/dynamic/";//101 HW
		//return "https://front2.j-cyclic.com.cn:8443/jczh_jcyclic_front/main/dynamic/";//123 HW
	},
	getUploadFilePath:function(){
		//return "http://127.0.0.1:8080/jczh_jcyclic_front/uploadFile/jcUpCsvFile/";
		//return "http://192.168.0.101:8080/jczh_jcyclic_front/uploadFile/jcUpCsvFile/";
		return "http://192.168.0.37:8080/jczh_jcyclic_front/uploadFile/jcUpCsvFile/";
		//return "http://192.168.0.37:8080/data_jczh_jcyclic_front/uploadFile/jcUpCsvFile/";
		//return "https://testfront.j-cyclic.cn:8443/jcyclic_front/uploadFile/jcUpCsvFile/";//test
		//return "https://demofront.j-cyclic.cn:18443/jcyclic_front/uploadFile/jcUpCsvFile/";//test
		//return "https://front1.j-cyclic.cn:8443/jczh_jcyclic_front/uploadFile/jcUpCsvFile/";//47 HC
		//return "https://front2.j-cyclic.cn:8443/jczh_jcyclic_front/uploadFile/jcUpCsvFile/";//123 HC
		//return "https://front1.j-cyclic.com.cn:8443/jczh_jcyclic_front/uploadFile/jcUpCsvFile/";//101 HW
		//return "https://front2.j-cyclic.com.cn:8443/jczh_jcyclic_front/uploadFile/jcUpCsvFile/";//123 HW
	},
	//跳转页面共通地址部分
	getJumpPath:function(){
		return "/Jcyclic_jczh/";
	},
	getRequestNameByMst:function(){
		return "mst";
	},
	getRequestNameByJcZH:function(){
		return "jczh";
	},
	getRequestNameByJcJP:function(){
		return "jcjp";
	},
//	getNavTitle:function(){
//	    return "J-CYCLIC";
//	},
	 getNavTitle:function(){
	 	return "JPP2";
	 },
	getConfirmMsgTitle:function(){
		return part_language_change_new("IS_CONFIRMATION_TITLE");
	},
	//获取requestID
	getRequestID:function()
	{
		return localStorage.getItem("requestID");
	},
	//获取 userID
	getUserID:function()
	{
		return localStorage.getItem("userID");
	},
	//获取 companyID
	getCompanyID:function()
	{
		return localStorage.getItem("companyID");
	},
	getDepartID:function()
	{
		return localStorage.getItem("departID");
	},
	getLangTyp:function()
	{
		return localStorage.getItem("language");
	},
	getUserName:function(cVal)
	{
		return localStorage.getItem("userName");
	},
	setMoneyDefaultCode:function(cVal)
	{
		return localStorage.setItem("moneyDefaultCode",cVal);
	},
	getMoneyDefaultCode:function(){
		return localStorage.getItem("moneyDefaultCode");
	},
	getNodeList:function(){
		return localStorage.getItem("nodeList");
	},
	//设置 requestID
	setRequestID:function(rVal)
	{
		return localStorage.setItem("requestID",rVal);
	},
	//设置 userID
	setUserID:function(uVal)
	{
		return localStorage.setItem("userID",uVal);
	},
	//设置 companyID
	setCompanyID:function(cVal)
	{
		return localStorage.setItem("companyID",cVal);
	},
	setLangTyp:function(cVal)
	{
		return localStorage.setItem("language",cVal);
	},
	setDepartID:function(cVal)
	{
		return localStorage.setItem("departID",cVal);
	},
	setUserName:function(cVal)
	{
		return localStorage.setItem("userName",cVal);
	},
	setAccountDate:function(cVal)
	{
		return localStorage.setItem("accountDate",cVal);
	},
	setDefaultPoint:function(cVal)
	{
		return localStorage.setItem("defaultPoint",cVal);
	},
	setNodeList:function(cVal){
		return localStorage.setItem("nodeList",cVal);
	},
	getDefaultPoint:function(){
		return localStorage.getItem("defaultPoint");
	},
	getAccountDate:function(){
		return localStorage.getItem("accountDate");
	},
	//重置 requestID
	resetRequestID:function(rVal)
	{
		localStorage.removeItem("requestID");
		localStorage.setItem("requestID",rVal);
	},
	//获取返回值中data部分的名称
	getRequestDataName:function(){
		return "data";
	},
	//获取返回值中userInfo部分的名称
	getRequestUserInfoName:function(){
		return "userInfo";
	},
	//获取返回值中metaInfo部分的名称
	getRequestMetaName:function(){
		return "metaInfo";
	},
	//获取返回值中状态为OK的名称
	getRequestStatusName:function(){
		return "OK";
	},
	getMoneyUnit:function(){
        return localStorage.getItem("moneyUnit");
	},
	//设置 requestID
	setMoneyUnit:function(moneyUnit)
	{
	    return localStorage.setItem("moneyUnit",moneyUnit);
	}
	});
/******************Ajax 全局设置   requestID从本地存储获取请求ID，每次请求后变更，请求后重置该ID*******************************************/
//var requestID = $.getRequestID()==null?"":$.getRequestID();
$.ajaxSetup({
	timeout:180000,
	type: "POST",
	contentType:"application/json",
	dataType:"JSON",
	beforeSend: ajaxLoading,
	complete:ajaxLoadEnd, 
	xhrFields: {
      withCredentials: true
   }
});
/*************************************************************/
jQuery.support.cors = true;
function ajaxLoading(){ 
	if(JSON.parse(localStorage.getItem('i18n')) == null){
		return;
	}
	var str = JSON.parse(localStorage.getItem('i18n')).LOADING_MSG;
//	switch (str){
//		case 'jp':
//			str = "処理中ですので、少々お待ちください。"
//			break;
//		case 'en':
//			str = "Just waiting, please."
//			break;
//		case 'zc':
//		str = "正在处理，请稍候。。。"
//			break;
//		case 'zt':
//		str = "正在處理，請稍候。。。"
//			break;
//		default:
//		str = "正在处理，请稍候。。。"
//			break;
//	}
	$('.layui-layer-shade').addClass('hidden');
    $("<div class=\"datagrid-mask\"></div>").css({display:"block",width:"100%",height:$(window).height()}).appendTo("body");   
    $("<div class=\"datagrid-mask-msg\" style=\"height:45px;\"></div>").html(str).appendTo("body").css({display:"block",'z-index':999,left:($(document.body).outerWidth(true) - 190) / 2,top:($(window).height() - 45) / 2});   
}   
function ajaxLoadEnd(XMLHttpRequest, textStatus){
	 $('.layui-layer-shade').removeClass('hidden');
     $(".datagrid-mask").eq(0).remove();   
     $(".datagrid-mask-msg").eq(0).remove();               
     
     if(textStatus == "success")
     {
     	var xmlHttpRequestText = XMLHttpRequest.responseText;
		var reg = RegExp("userInfo");
		if(xmlHttpRequestText.match(reg)){
		   if($('#sidebarIsSetFlg').val() == "")
	     	{
	     		//暂时注释，根据权限显示具体导航
	     		settingSideBarMenus(XMLHttpRequest.responseText);
	     	}     
	     	setCompanyChildList(XMLHttpRequest.responseText);
	     	setAccountDateByHeade();
			//nav title 赋值
			$('.get_nav_title').text($.getNavTitle());
		}
     }
     else{
     	location.href = $.getJumpPath()+"common/error.html";
     }
} 
function settingSideBarMenus(data)
{
	
	var rs = JSON.parse(data);
	var pageNodeList = rs.userInfo.pageNodeList;
	var uNodeList = rs.userInfo.uNodeList;
	var companyID = rs.userInfo.companyID;
	var sideBarList = [];
	var jobList = [];
	var costList = [];
	var masterList = [];
	var usList = [];
	var pageID = 0;
	var langTFlg = false;
	$('#lockIcon').hide();
	for(var i = 0; i < pageNodeList.length;i++)
	{
		for(var j = 0;j < uNodeList.length;j++)
		{
			if(pageNodeList[i]['nodeID'] == uNodeList[j]['nodeID'])
			{
				pageID = parseInt(pageNodeList[i]['pageID']);
				sideBarList.push(pageID);
			}
			if(uNodeList[j]['nodeID'] == 2)
			{
				$('#lockIcon').show();
			}
			if(uNodeList[j]['nodeID'] == 76 || uNodeList[j]['nodeID'] == 77)
			{
				langTFlg = true;
			}
		}
	}
	//判断是否有用户变更权限，如果 有的话才可以切换语言
	/*
	if(langTFlg)
	{
		$('#language').removeAttr("disabled");
	}else{
		$('#language').attr("disabled","disabled");
	}
	*/
	setSideBarShow(sideBarList,companyID);
}

function setSideBarShow(barList,companyID)
{
	var sideBarList = ["","jobList","jobCreat","mdList","orderNoFinsh","saleNoCreat","saleNoConfirm","costList","costNoConfirm",
			"payNoApp","monthlyClosingBlock","reportBlock","timesheetBlock","customListBlock","labelManage",
			"toolsBlock","clmstList","userList","roleBarList","saleList","taxCostList","commonList","companyList"];
	var pageID = 0;
	for(var i = 0; i < sideBarList.length;i++)
	{
		$('#'+sideBarList[i]).hide();
	}
	if(companyID != '999')
	{
		$('#jobBlock').hide();
		$('#costBlock').hide();
		$('#masterBlock').hide();
		$('#userSettingBlock').hide();
		for(var i = 0; i < barList.length;i++)
		{
			pageID = barList[i];
			if(pageID > 0 && pageID < 7)
			{
				$('#jobBlock').show();
			}else if(pageID < 10 && pageID > 6)
			{
				$('#costBlock').show();
			}else if(pageID > 15 && pageID < 23)
			{
				$('#masterBlock').show();
			}else if(pageID == 13 || pageID == 14)
			{
				$('#userSettingBlock').show();
			}
			
			//345689  不显示
			if(barList[i] > 2 && barList[i] < 10 && barList[i] != 7)
			{
				$('#'+sideBarList[barList[i]]).hide();
			}else{
				$('#'+sideBarList[barList[i]]).show();
			}
		}
	}else{
		$('#jobBlock').hide();
		$('#costBlock').hide();
		$('#userSettingBlock').hide();
		$('#masterBlock').show();
		$('#companyList').show();
	}
}
function setCompanyChildList(data)
{
	var rs = JSON.parse(data);
	var langT = localStorage.getItem('language');  
	var departName = rs.userInfo['departName'+langT];
	var userName = $.getUserName();
	if(rs.userInfo.userCompanyList != null && rs.userInfo.userCompanyList != undefined)
	{
		var companyList = rs.userInfo.userCompanyList;
		var str = "";
		for(var i = 0;i < companyList.length;i++)
		{
			if(companyList[i]['company_cd'] == rs.userInfo.companyID)
			{
				str += '<option selected="true" value="'+companyList[i]['company_cd']+'">'+companyList[i]['company_name']+'</option>';	
			}else{
				str += '<option value="'+companyList[i]['company_cd']+'">'+companyList[i]['company_name']+'</option>';
			}
		}
		$('#changeCompany').html(str);
		$('#changeCompany').selectpicker('refresh');
	}
	$('#sysLockFlg').val(rs.userInfo.lockStatus);
	if(rs.userInfo.lockStatus == 'unlock'){
		$('#sysLockFlg').next('i').attr('class','iconfont icon-dakaisuo-');
	}else{
		$('#sysLockFlg').next('i').attr('class','iconfont icon-suo');
	}	
	$('#wheel-icon').val(rs.userInfo.colorV);
	hearUserIconInit(departName,userName);
	headTimeInit(rs.userInfo.dateCompanyZone);
	$('#timeZoneType').html("UTC"+rs.userInfo.timeZoneType);
}
function default_decimal_reservation(){
	$('.default_decimal_reservation').each(function(){
		var dec = localStorage.getItem('defaultPoint');
		var str = '0.';
		for(var i = 0;i < dec;i ++){
			str += '0'
		}
		if(str == '0.'){
			str = '0';
		}
		$(this).text(str);
	})
}
function hearUserIconInit(departName,userName)
{
	$('#wheel-icon').minicolors({

		control: 'hue',//确定控制的种类。有效的选项为色调，亮度， 饱和度，和车轮。

		defaultValue:'',//要强制默认的颜色，设置为一个有效的十六进制字符串。当用户清除了控制，它会恢复到这种颜色。

		inline:false,//设置为真给力拾色器出现内联
		
		changeDelay: 1500,//的时间，以毫秒为单位，推迟了变化，而 用户进行选择的射击事件。这是为了防止有用变化的事件频繁发射作为用户拖动周围的颜色选择器。

		letterCase:'lowercase',//确定的十六进制代码值的字母大小写。有效选项为大写 或小写。

		opacity:false,//设置为真，是不透明度滑块。

		position:'bottom left',//位置

		change: function(hex, opacity) {

			if( !hex ) return;

			if( opacity ) hex += ', ' + opacity;

			try {

				changeColorByUser(hex);

			} catch(e) {}

		},
		theme: 'bootstrap'
	});
	$(".minicolors-swatch-color .icon-ren").tooltip({
	    position: 'left',
	    content: '<span style=\"color:#fff\">'+userName + " " + departName +'</span>',
	    onShow: function(){
			$(this).tooltip('tip').css({
				backgroundColor: '#666',
				borderColor: '#666'
			});
	    }
	});
}
function headTimeInit(dateCompanyZone)
{
	//yyyy-mm-dd HH:mm:ss
	var ajaxStr = dateCompanyZone==undefined?'1970-01-01':dateCompanyZone;
	var timeAddObj = {};
	var a = new Date(ajaxStr);//ajax的系统时间
	timeAddObj.b = {
		hour:a.getHours(),
		minute:a.getMinutes(),
		second:a.getSeconds()
	}
	var timeId = setInterval(function(){showLocale(timeAddObj)},1000);	
	//clearTimeout(timeId)
}
function setAccountDateByHeade()
{
	$('#accountDate').html($.getAccountDate());
}
function sideBarClickFirst(event){
	var dom = $(event.target);
	if(dom.next('ul').exist()){
		dom.next('ul').find('li').each(function(){
			if($(this).css('display') == 'list-item'){
				var url = $(this).find('a').attr('href');
				window.location.href = url;
				return false;
			}
		})
	}
}
