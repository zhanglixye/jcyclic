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
var i18nLanguage = "zc";

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
            // 判断是否在网站支持语言数组里
            var charSize = $.inArray(navLanguage, webLanguage);
            if (charSize > -1) {
                i18nLanguage = navLanguage;
                // 存到缓存中
                localStorage.setItem('language',navLanguage);
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
		  easyUiLanguage(i18nLanguage);
		  $('body').css('font-family','Roboto,"メイリオ",Meiryo,"ヒラギノ角ゴ ProN W3","Hiragino Kaku Gothic ProN","ＭＳ Ｐゴシック","MS PGothic",sans-serif');
		  break;
		case 'jp':
		case '001':
		i18nLanguage = 'jp';
		//easyui databox语言的切换功能
		  easyUiLanguage(i18nLanguage);
		  $('body').css("font-family",'Roboto,"メイリオ",Meiryo,"ヒラギノ角ゴ ProN W3","Hiragino Kaku Gothic ProN","ＭＳ Ｐゴシック","MS PGothic",sans-serif');
		  break;
		case 'zc':
		case '002':
		i18nLanguage = 'zc';
		//easyui databox语言的切换功能
		  easyUiLanguage(i18nLanguage);
		  $('body').css("font-family",'Roboto,"Microsoft Yahei","微软雅黑",SimSun,"宋体","华文细黑",STXihei,"PingFang SC Regular",sans-serif');
		  break;
		case 'zt':
		case '003':
		i18nLanguage = 'zt';
		  easyUiLanguage(i18nLanguage);
		  $('body').css("font-family",'Roboto,PMingLiU,"Microsoft Yahei",微软雅黑,SimSun,宋体,"华文细黑",STXihei,"PingFang SC Regular",sans-serif');
		  break;
		}
		localStorage.setItem('language',i18nLanguage);
    /*
    这里需要进行i18n的翻译
     */
    jQuery.i18n.properties({
        name : sourceName, //资源文件名称
        path:'../../public/language/jczh/' + i18nLanguage + '/', //资源文件路径  
        mode : 'map', //用Map的方式使用资源文件中的值
        language : i18nLanguage,
        callback : function() {//加载成功后设置显示内容
            var insertEle = $(".i18n");
            insertEle.each(function() {
                // 根据i18n元素的 name 获取内容写入
                $(this).html($.i18n.prop($(this).attr('name')));
            });
            var insertInputEle = $(".i18n-input");
            insertInputEle.each(function() {
                var selectAttr = $(this).attr('selectattr');
                if (!selectAttr) {
                    selectAttr = "value";
                };
                $(this).attr(selectAttr, $.i18n.prop($(this).attr('selectname')));
            });
        }
    });
        
}
//读取语言属性文件
var readLanguageFile = function(flag){
	var language = $('select.selectpicker.language_conversion').val();
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
    || href.match(/\/suppliers_registration/) || href.match(/\/ltd_registration/))
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
    /* 选择语言 */
    $(".language_conversion").bind('change', function() {
        readLanguageFile();      
    });
    //datagrid表格内容国际化
    $('select#language').on('changed.bs.select', function(e) {
    	var language = $('select.selectpicker.language_conversion').val();
    	localStorage.setItem('language',language);
    	$('table.datagrid-f').each(function(index,ele){
    		if($(this).exist()){
	      		var tableId = $(this).attr('id');
	      		var objPam = JSON.parse(localStorage.getItem('parameter_storage'));
	        	initDataGridHandler(objPam.tabId,objPam.pageSizeJ,objPam.sortNameJ,objPam.pagePositionJ,objPam.paginationJ,objPam.isHasFn,objPam.dataColumns,objPam.fitColumn,objPam.frozen);
	        	var gridData =  $('#'+tableId).datagrid("getData")['originalRows'];
	        	if(gridData==undefined){
	        		gridData = $('#'+tableId).datagrid("getRows");
	        	}
		      	$('#'+tableId).datagrid({loadFilter: pagerFilter}).datagrid('loadData',gridData);
	        }
    	})     
    })
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
