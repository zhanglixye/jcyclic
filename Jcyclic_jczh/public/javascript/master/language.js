/**
 * 获取浏览器语言类型
 * @return {string} 浏览器国家语言
 */
var getNavLanguage = function(){
    if(navigator.appName == "Netscape"){
        var navLanguage = navigator.language;
        return navLanguage;
    }
    return false;
}

/**
 * 设置语言类型： 默认为中文
 */
//var i18nLanguage = "zc";

/*
设置一下网站支持的语言种类
 */
var webLanguage = ['zc', 'zt', 'en','jp'];

/**
 * 执行页面i18n方法
 * @return
 */ 
var execI18n = function(){
    /*
    获取一下资源文件名
     */
    var optionEle = $("#i18n_pagename");
    if (optionEle.length < 1) {
//      console.log("未找到页面名称元素，请在页面写入\n <meta id=\"i18n_pagename\" content=\"页面名(对应语言包的语言文件名)\">");
        return false;
    };
    var sourceName = optionEle.attr('content');
    sourceName = sourceName.split('-');
    /*
    首先获取用户浏览器设备之前选择过的语言类型
     */
    if (localStorage.getItem('language')) {
        i18nLanguage = localStorage.getItem('language');
    } else {
        // 获取浏览器语言
        var navLanguage = getNavLanguage();
//        console.log(navLanguage);
        if (navLanguage) {
        	if(navLanguage == 'zh-CN'){
        		navLanguage = 'zc';
        	}
            // 判断是否在网站支持语言数组里
            var charSize = $.inArray(navLanguage, webLanguage);
            if (charSize > -1) {
                i18nLanguage = navLanguage;
                // 存到缓存中
                //localStorage.setItem('language',navLanguage);
            };
        } else{
//          console.log("not navigator");
            return false;
        }
    }
    /* 需要引入 i18n 文件*/
    if ($.i18n == undefined) {
//      console.log("请引入i18n js 文件")
        return false;
    };
	//语言字体的判断
	switch (i18nLanguage)
		{
		case 'en':
		case '004':
			i18nLanguage = 'en';
		  //easyUiLanguage(i18nLanguage);
		  $('body').css('font-family','Roboto,"メイリオ",Meiryo,"ヒラギノ角ゴ ProN W3","Hiragino Kaku Gothic ProN","ＭＳ Ｐゴシック","MS PGothic",sans-serif');
		  break;
		case 'jp':
		case '001':
		i18nLanguage = 'jp';
		//easyui databox语言的切换功能
		 // easyUiLanguage(i18nLanguage);
		  $('body').css("font-family",'Roboto,"メイリオ",Meiryo,"ヒラギノ角ゴ ProN W3","Hiragino Kaku Gothic ProN","ＭＳ Ｐゴシック","MS PGothic",sans-serif');
		  break;
		case 'zc':
		case '002':
		i18nLanguage = 'zc';
		//easyui databox语言的切换功能
		 // easyUiLanguage(i18nLanguage);
		  $('body').css("font-family",'Roboto,"Microsoft Yahei","微软雅黑",SimSun,"宋体","华文细黑",STXihei,"PingFang SC Regular",sans-serif');
		  break;
		case 'zt':
		case '003':
		i18nLanguage = 'zt';
		//  easyUiLanguage(i18nLanguage);
		  $('body').css("font-family",'Roboto,PMingLiU,"Microsoft Yahei",微软雅黑,SimSun,宋体,"华文细黑",STXihei,"PingFang SC Regular",sans-serif');
		  break;
		}
		localStorage.setItem('language',i18nLanguage);
    // localStorage 语言重写加载
	init_login_language_load(i18nLanguage,true);
	easyUiLanguage(i18nLanguage);
	//转换语言之前checkbox的状态
	var exist_tip = $('#PayConfirmOutPDF').exist();
	if(exist_tip == true){
		var check_tip =	$('#PayConfirmOutPDF').prop('checked');
	}
	//callback重写
	var insertEle = $(".i18n");
	insertEle.each(function() {
    // 根据i18n元素的 name 获取内容写入
        $(this).html(part_language_change_new($(this).attr('name')));
    });
    var insertInputEle = $(".i18n-input");
    insertInputEle.each(function() {
        var selectAttr = $(this).attr('selectattr');
        if (!selectAttr) {
            selectAttr = "value";
        };
        $(this).attr(selectAttr, part_language_change_new($(this).attr('selectname')));
    });   
    //转换语言之后更改checkbox的状态
    if(check_tip == true){
    	$('#PayConfirmOutPDF').attr("checked",'true');
    }
}
//读取语言属性文件
var readLanguageFile = function(flag){
	//var language = $('select.selectpicker.language_conversion').val();
	var language = localStorage.getItem('language');
    localStorage.setItem('language',language);
    execI18n();
    $('.language_conversion').find("[value="+i18nLanguage+"]").prop("selected",true);
    $('.language_conversion').selectpicker('refresh');
    flushToChangeLang();
    SelectObj.setSelectOfLog();
    //tooltip国际化触发 tooltip国际化调用 
    var href = window.location.href;
    if(href.match(/\/payment_registration/) || href.match(/\/payment_update/) 
    || href.match(/\/payment_info/) || href.match(/\/payment_approval/) 
    || href.match(/\/suppliers_registration/) || href.match(/\/ltd_registration/) || href.match(/\/report_output/))
    {
    	toolTipLanguage(language);
    }
}
/*页面执行加载执行*/
var comInit = function(){
	 /*执行I18n翻译*/
    execI18n();
    /*将语言选择默认选中缓存中的值*/
    $('.language_conversion').find("[value="+i18nLanguage+"]").prop("selected",true);
    $('.language_conversion').selectpicker('refresh');
    //datagrid表格内容国际化
    $('select#language').on('changed.bs.select', function(e) {
   		var language = $('select.selectpicker.language_conversion').val();
		localStorage.setItem('language',language);
		readLanguageFile(); 		
		/*******************切换语言时更新数据库***************************
		var langT = "001";
		switch (language)
		{
			case 'en':
				langT = '004';
		  		break;
			case 'jp':
				langT = '001';
				break;
			case 'zc':
				langT = '002';
				break;
			case 'zt':
				langT = '003';
				break;
		}
		
		var pars = {"langtyp":langT,"flag":"changeLanguage","nickname":"changeLanguage"};
		$.ajax({
			url:$.getAjaxPath()+"editUserInfo",
			headers: {"requestID":$.getRequestID(),"requestName":$.getRequestNameByMst()},
			data:JSON.stringify(pars),
			success:function(data){
	           if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
	           	$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
	           	if(data[$.getRequestDataName()] != 0 && data[$.getRequestDataName()] != null)
	           	{
	           		changeLanguage();
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
		*******************切换语言时更新数据库***************************/
		setPersonMoneyCode(JSON.parse($.getMoneyUnit()));
		changeLanguage();
   });
	/*下拉框样式实例化*/
	$('.selectpicker').selectpicker();
}
//模板挂在后初始化
$(function(){
	//alt 效果
	$('[data-toggle="tooltip"]').tooltip();
	//公有模块(语言,select,file)加载
	comInit();
})
function changeLanguage()
{
    //confirm 弹框多国语言设定
    msgConfirmInformation();
	var hrefltd = window.location.href;
	if(hrefltd.match(/\/ltd_registration/)){
		//window.location.reload();
	}
	if($('#saleRegistration_tax1').exist() && $('#saleRegistration_tax2').exist())
	{
		rateNameByLangTyp();
	}
	$('table.datagrid-f').each(function(index,ele){
		if($(this).exist()){
      		var tableId = $(this).attr('id');
      		if(tableId != 'registrationUi' && tableId != 'salescategory' && tableId != 'salescategory_T'){
      			var objPam = JSON.parse(localStorage.getItem('parameter_storage'));
      			//客户表修改添加
      			if(tableId == 'irrelevantTab' || tableId == 'relevancedTab'|| tableId =='wedgemembersleft' || tableId =='wedgemembersright'){
      				objPam.tabId = tableId;
      			}
	        	initDataGridHandler(objPam.tabId,objPam.pageSizeJ,objPam.sortNameJ,objPam.pagePositionJ,objPam.paginationJ,objPam.isHasFn,objPam.dataColumns,objPam.fitColumn,objPam.frozen);
	        	var gridData =  $('#'+tableId).datagrid("getData")['originalRows'];
	        	if(gridData==undefined){
	        		gridData = $('#'+tableId).datagrid("getRows");
	        	}
	        	if(tableId != 'cost_list_job_details'){
	        		if(tableId == 'timesheet_list'){
                        let obj = {
                                'total':gridData.length,
                                'rows':gridData
                        }
                        $('#'+tableId).datagrid('loadData',obj);
                        return;
                }
	        		if (tableId == 'ltd_list'){
						$('#'+tableId).datagrid('loadData',gridData);
					}else {
						$('#'+tableId).datagrid({loadFilter: pagerFilter}).datagrid('loadData',gridData);
					}
	        	}else{
	        		$("#cost_list_job_details").datagrid("loadData", gridData);
	        	}
	        	//job一览与原价一览的表格超出问题
	        	if(tableId == 'cost_list_Two' || tableId == 'job_login_list'){
	        		if($('.datagrid-view').hasClass('top-40')){
	            		var heiTop = $('.datagrid-view').height()+40;
	            		$('.datagrid-view').height(heiTop);
	            	}
	        	}
		      	if(hrefltd.match(/\/label_management/)){
					window.location.reload();
				}
		      	//客户表修改添加
		      	if(tableId == 'irrelevantTab' || tableId == 'relevancedTab' || tableId == 'wedgemembersleft' || tableId == 'wedgemembersright'){
			      	//$('.datagrid-view').eq(0).css('height','370');
			      	//$('.datagrid-view').eq(1).css('height','370');
			      	$('.panel-title').eq(0).html(part_language_change_new('customerwedgeChange_title'));
			      	$('.panel-title').eq(1).html(part_language_change_new('customerwedgeChange_title2'));
		      	}
      		}else{
      			window.location.reload();
      		}
        }
	});
}
