/**
* 方法名 showErrorHandler
* 方法的说明 利用easyUI，弹出提示信息，不带方法操作
* @param resultMsg String 提示信息，msgT String 标题,msgL String 信息级别 error、question、info、warning。
* @return 无
* @author作者 王岩
* @date 日期 2018.05.08
*/

function showErrorHandler(resultMsg,msgT,msgL)
{
	var msg = part_language_change_new(resultMsg);
	msgT = part_language_change_new(resultMsg+"_TITLE");
	msgL = part_language_change_new(resultMsg+"_ICON");
	//判定弹框只弹一次
	if($('.window-mask').exist() && ($('.window-mask').css('display')=='block') && ($('.window-mask').css('position')=='fixed')){
		return '弹框多个error';
	}else{
		$.messager.alert(msgT,msg,msgL);
		//bug 确认框，汉语条件下 也显示的日文
		if ($.messager){
			if(localStorage.getItem('language') == 'jp' || localStorage.getItem('language') == '001'){
				if($('.messager-window .dialog-button .l-btn-text').exist()){
					$('.messager-window .dialog-button .l-btn-text').text('はい');
				}
			}else if(localStorage.getItem('language') == 'zc' || localStorage.getItem('language') == '002'){
				if($('.messager-window .dialog-button .l-btn-text').exist()){
					$('.messager-window .dialog-button .l-btn-text').text('是');
				}
			}
		}
	}
	
}
/**
* 方法名 showErrorHandler
* 方法的说明 利用easyUI，弹出提示信息，带操作，清空本地存储信息，跳转到登陆页面
* @param resultMs g String 提示信息，msgT String 标题,msgL String 信息级别 error、question、info、warning。
* @return 无
* @author作者 王岩
* @date 日期 2018.05.08
*/
function showErrorFunHandler(resultMsg,msgT,msgL)
{
	var msg = part_language_change_new(resultMsg);
	msgT = part_language_change_new(resultMsg+"_TITLE");
	msgL = part_language_change_new(resultMsg+"_ICON");
	if(resultMsg == "VALIDATE_FORMAT_ERROR")
	{
		$.messager.alert(msgT,msg,msgL);
	}else{
		$.messager.alert(msgT,msg,msgL,function(){
			//localStorage.clear();
			clear_localstorage();
			var index = "";
			if(parent.layer != undefined)
			{
				index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
			}
			if(index != "" && index != undefined)
			{
				parent.layer.close(index); //再执行关闭
				parent.window.location.href=$.getJumpPath();
			}else{
				window.location.href=$.getJumpPath();	
			}
		});
	}
	information_alert_language();
}
/**
* 方法名 showConfirmMsgHandler
* 方法的说明 弹出确认操作框。
* @param resultMsg String 提示信息
* @return Boolean
* @author作者 王岩
* @date 日期 2018.05.08
*/
function showConfirmMsgHandler(resultMsg)
{
	var msg = part_language_change_new(resultMsg);
	return msg;
}
/**
* 方法名 showInfoMsgHandler
* 方法的说明 利用easyUI，弹出提示信息，不带方法操作
* @param resultMsg String 提示信息，msgT String 标题,msgL String 信息级别  error、question、info、warning。, url 要跳转的页面 str 后台返回的信息
* @return 无
* @author作者 刘实
* @date 日期 2018.05.30
*/
function showInfoMsgHandler(resultMsg,msgT,msgL,url,str,pdfFlg,jobNo,costNo,inputNo,pdfname){
	var msg = "";
	if(resultMsg == "EXCUTE_SUCCESS_COMPANYADD")
	{
	 	msg = str;
	 	msgT = part_language_change_new(msgT+"_TITLE");
		msgL = part_language_change_new(msgL+"_ICON");
	}else{
		msg = part_language_change_new(resultMsg); 
		msgT = part_language_change_new(resultMsg+"_TITLE");
		msgL = part_language_change_new(resultMsg+"_ICON");
	}
	 $.messager.alert(msgT,msg,msgL,function(){
	 	if(pdfFlg)(
			OutPutPdfHandler(jobNo,costNo,inputNo,pdfname)
	 	)
	 	if(pdfFlg!="1"){
	 		window.location.href  = $.getJumpPath()+url;
	 	}
	 });
	information_alert_language();
}

/**
* 方法名 showInfoMsgHandler
* 方法的说明 利用easyUI，弹出提示信息，不带方法操作,停留当前页面
* @param resultMsg String 提示信息，msgT String 标题,msgL String 信息级别 error、question、info、warning。, url 要跳转的页面 str 后台返回的信息
* @return 无
* @author作者 fqq
* @date 日期 2018.05.30
*/
function showInfoMsgHandlerstop(resultMsg,msgT,msgL,url,str,pdfFlg,jobNo,costNo,inputNo,pdfname){
	var msg = part_language_change_new(resultMsg); 
	msgT = part_language_change_new(resultMsg+"_TITLE");
	msgL = part_language_change_new(resultMsg+"_ICON");
	 $.messager.alert(msgT,msg,msgL,function(){
		return false;
	 });
    information_alert_language();
}
/**
* 方法名 showLockInfoMsgHandler
* 方法的说明 利用easyUI，弹出提示信息，返回到url页面
* @param resultMsg String 提示信息
* @return 无
* @author作者 fqq
* @date 日期 2019.02.25
*/
function showLockInfoMsgHandler(resultMsg,url){
	var msg = part_language_change_new(resultMsg); 
	var msgT = part_language_change_new(resultMsg+"_TITLE");
	var msgL = part_language_change_new(resultMsg+"_ICON");
	 $.messager.alert(msgT,msg,msgL,function(){
		window.location.href  = $.getJumpPath()+url;
	 });
    information_alert_language();
}

/**
* 方法名 showErrorFunHandlerActive
* 方法的说明 利用easyUI，弹出提示信息，带操作，清空本地存储信息，跳转到登陆页面
* @param beforeStr g String 前拼接提示信息
* @param beforeResultMsg g String 前拼接提示信息
* @param centerStr g String 中接提示信息
* @param afterResultMsg  g String 后拼接提示信息
* @param afterStr g String 后拼接提示信息
* @param msgT String 标题,msgL String 信息级别 error、question、info、warning。
* @return 无
* @author作者 李洪涛
* @date 日期 2019.11.10
*/
function showErrorHandlerActive(beforeResultMsg,centerStr,afterResultMsg,msgT,msgL)
{
        var beforeMsg = part_language_change_new(beforeResultMsg);
        var afterMsg = part_language_change_new(afterResultMsg);
        var msg =  beforeMsg + centerStr + afterMsg ;
        msgT = part_language_change_new(beforeResultMsg+"_TITLE");
        msgL = part_language_change_new(beforeResultMsg+"_ICON");
        $.messager.alert(msgT,msg,msgL);
        //bug 确认框，汉语条件下 也显示的日文
        if ($.messager){
                if(localStorage.getItem('language') == 'jp' || localStorage.getItem('language') == '001'){
                        if($('.messager-window .dialog-button .l-btn-text').exist()){
                                $('.messager-window .dialog-button .l-btn-text').text('はい');
                        }
                }else if(localStorage.getItem('language') == 'zc' || localStorage.getItem('language') == '002'){
                        if($('.messager-window .dialog-button .l-btn-text').exist()){
                                $('.messager-window .dialog-button .l-btn-text').text('是');
                        }
                }
        }
}


