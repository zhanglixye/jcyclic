/**
* 方法名 validataRequired
* 方法的说明 遍历页面所有class名为required的input元素，进行是否为空的验证。
*			 存在必填项为空时，input元素边框变为红色，并且返回false，反之返回true
* @param null
* @return Boolean 
* @author作者 王岩
* @date 日期 2018.05.08
*/
function validataRequired(login)
{
	var reg = new RegExp("^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$"); //正则表达式
	var regPassword = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,}$/;
	var flg = true;
	var count = 0;
	window.countG = 0;
	$("input[type='text'].required,input[type='password'].required,input[type='number'].required,input[type='email'].required,select.required,.date-box-required,.cal-cost-box,.cal-sale-box").each(function(index, element) {
		if($(element).hasClass('date-box-required')){
			if($(element).datebox('getValue') == "")
	    	{
	    		$(element).siblings("span.textbox.combo.datebox").css("border-color", "red");
				flg =  false;
				validate($(element).siblings("span.textbox.combo.datebox"),part_language_change_new('NODAY'));
				count++;
				window.countG = count;
				if(count == 1){
					//滚动到指定红色的位置
					//$(element).siblings("span.textbox.combo.datebox")[0].scrollIntoView(true);
					//$(element).siblings("span.textbox.combo.datebox")[0].scrollIntoView(false);
					$('#content').scrollTop(0);
					if(!$('.panel.window.panel-htop.messager-window').exist()){
			 			showErrorHandler("NOT_EMPTY", "ERROR", "ERROR");
			 		}
				}
	    	}else{
	    		$(element).siblings("span.textbox.combo.datebox").css("border-color", "#cccccc");
	    		$(element).siblings("span.textbox.combo.datebox").tooltip('destroy');
	    	}
		}else if($(element).hasClass('cal-cost-box') || $(element).hasClass('cal-sale-box')){
			 //if($('.cal-cost-box,.cal-sale-box').exist()){
	    		if($(element).numberbox('getValue') == ""){
	    			count++;
					window.countG = count;
		    		$(element).closest('div').setBorderRed();
		    		validate($(element).closest('div'),part_language_change_new('NODATE'));
		    		flg =  false;
		    	}else{
		    		$(element).closest('div').setBorderBlack();
					$(element).closest('div').tooltip('destroy');
		    	} 	  	
		    //}
		}
		else{
			if(element.value == "")
			{
				if($(element).next('span').exist() && $(element).next('span').find('i.iconfont.icon-sousuo').exist()){
					$(element).next('span').css('border-color',"red");
					validate($(element).next('span').closest('div'),part_language_change_new('NODATE'));
					//验证是否为搜索框
					var isSourceFlag = true;
				}else {
					var isSourceFlag = false;
				}
				flg = false;
				element.style.borderColor = "red";
				if($(element).prev('span').find('.iconfont.icon-ren').exist()){
					$(element).prev('span').css('border-color',"red");
				}
				count++;
				window.countG = count;
				if(count == 1){
					//滚动到指定红色的位置
					//$(element)[0].scrollIntoView(true);
					//$(element)[0].scrollIntoView(false);
					$('#content').scrollTop(0);
					if(login != true){
						if(!$('.panel.window.panel-htop.messager-window').exist()){
				 			showErrorHandler("NOT_EMPTY", "ERROR", "ERROR");
				 		}
					}					
				}
				if (!isSourceFlag){
					validate($(element),part_language_change_new('NODATE'));
				}
			}else{
				if($(element).next('span').exist() && $(element).next('span').find('i.iconfont.icon-sousuo').exist()){
					$(element).next('span').css('border-color',"#cccccc");
					$(element).next('span').closest('div').tooltip('destroy');
				}
				if($(element).prev('span').find('.iconfont.icon-ren').exist()){
					$(element).prev('span').css('border-color',"#cccccc");
					$(element).prev('span').closest('div').tooltip('destroy');
				}
				element.style.borderColor = "#cccccc";
				//销毁tooltip事件
				$(element).tooltip('destroy');
			}
		}		
   });
    $("input.email").each(function(index, element) {
    	if(element.value != "")
    	{
    		if(!reg.test(element.value))
			{
				flg = false;
				element.style.borderColor = "red";
			}else{
				element.style.borderColor = "#cccccc";
			}
    	}
		
    });
    var checkFlg = validataCheckBoxIsChose(flg);
    if(flg){
    	flg = checkFlg;
    }     
    $("input[type='checkbox'].required").each(function(index, element) {
    	element.parentElement.style.color = "black";
		if(!checkFlg)
		{
			element.parentElement.style.color = "red";
		}else{
			element.parentElement.style.color = "black";
		}
    });
//  $(".date-box-required").each(function(index, element) {
//  	if($(element).datebox('getValue') == "")
//  	{
//  		$(element).siblings("span.textbox.combo.datebox").css("border-color", "red");
//			flg =  false;
//			validate($(element).siblings("span.textbox.combo.datebox"),part_language_change_new('NODAY'));
//			count++;
//			window.countG = count;
//			if(count == 1){
//				//滚动到指定红色的位置
//				$(element).siblings("span.textbox.combo.datebox")[0].scrollIntoView(true);
//			}
//  	}else{
//  		$(element).siblings("span.textbox.combo.datebox").css("border-color", "#cccccc");
//  		$(element).siblings("span.textbox.combo.datebox").tooltip('destroy');
//  	}
//		
//  });
//  if($('.cal-cost-box,.cal-sale-box').exist()){
//  	$('.cal-cost-box,.cal-sale-box').each(function(index, element){
//  		if($(element).numberbox('getValue') == ""){
//	    		$(element).closest('div').setBorderRed();
//	    		validate($(element).closest('div'),part_language_change_new('NODATE'));
//	    		flg =  false;
//	    	}else{
//	    		$(element).closest('div').setBorderBlack();
//				$(element).closest('div').tooltip('destroy');
//	    	}
//  	})   	  	
//  }
    $(".money-infinity").each(function(index, element){
    	if(!isFinite(recoveryNumber($(element).text()))){
    		$(element).css("color","red");
    		flg =  false;
    	}else{
    		$(element).css("color","black");
    	}
    });
    
    $("input[type='password'].form-control:not('.login_pwd')").each(function(index, element) {
    	if(element.value != "" && element.value != "********")
    	{
    		if(!regPassword.test(element.value))
			{
				flg = false;
				element.style.borderColor = "red";
				validate($(element),part_language_change_new('PASSWORD_NUMBER_E'));
				$('#content').scrollTop(0);
			}else{
				element.style.borderColor = "#cccccc";
				$(element).tooltip('destroy');
			}
    	}
		
    });
    
    /*
    if($(".date-box-required").datebox('getValue')==""){
		$(".date-box-required").siblings("span.textbox.combo.datebox").css("border-color", "red");
		flg =  false;
	}else{
		$(".date-box-required").siblings("span.textbox.combo.datebox").css("border-color", "#cccccc");
	}
	*/
    return flg;
}

function validataCheckBoxIsChose(otherFlg)
{
	var flg = false;
	$("input[type='checkbox'].required").each(function(index, element) {
		if(element.checked)
		{
			flg = true;
		}
    });
    if($("input[type='checkbox'].required").length < 1)
    {
    	flg = otherFlg;
    }
    return flg;
}
/**
* 方法名 verify
* 方法的说明 遍历页面控件是否为空，判断input输入框长度是否超过8位
*			 存在必填项为空时，input元素边框变为红色，并且返回false。当输入框，时间空间内容改变后，边框颜色还原
* @param null
* @return Boolean 
* @author作者 崔晋
* @date 日期 2018.08.31
*/
//判断为空，显示红框
function Judgment_empty(className) {
	var a = arguments;
	var flg = true;
	for(var i = 0, len = a.length; i < len; i++) {
		var Judgment_class = "." + a[i];
		var Judgment_val = $(Judgment_class).val();
		if(Judgment_val == ""){
			$(Judgment_class).css("border-color","red");
			validate($(Judgment_class),part_language_change_new('NODATE'));
			flg = false;
		}else{
			$(Judgment_class).css("border-color","#cccccc");
			$(Judgment_class).tooltip('destroy');
		}
	}
	return flg;
}
//判断字符输入长度
function Judgment_length(className) {
	$('.'+className).each(function(index, element) {
		if(element.value.length > element.maxlength) {
			//alert("字符长度不能大于8个字符");
			element.style.borderColor = "red";
			validate($(element),"字符长度不能大于8个字符");
			return false;
		}else{
			element.style.borderColor = "#cccccc";
			$(element).tooltip('destroy');
		}
		
	});
}
//判断日期大小 
//classname 第一个class名   classnameT  第二个class名
function Judgment_time(classname,classnameT,mes) {
	var classObj= new Date($('.'+classname).datebox('getValue')).getTime();
	var classObjT = new Date($('.'+classnameT).datebox('getValue')).getTime();
	if(classObjT < classObj) {
		//$('.'+classname).next('span').css("border", "1px solid red");
		getRedc($('.'+classname).next('span'),true);
		//$('.'+classnameT).next('span').css("border", "1px solid red");
		getRedc($('.'+classnameT).next('span'),true);
		showErrorHandler(mes,"info","info");
		return false;
	}else{
		getRedc($('.'+classname).next('span'),false);
		//$('.'+classnameT).next('span').css("border", "1px solid #cccccc");
		getRedc($('.'+classnameT).next('span'),false);
		return true;
	}	
}
/**
 * 方法名  ToCDB
 * 方法的说明  全角转换半角 英数字
 * @param String str
 * @return String
 * @author作者 zlx
 * @date 日期 2018.12.29
 */
function ToCDB(str,flag) {
  var tmp = "";
  var reg = /^[A-Za-z0-9\.]+$/;
  var reg2 = /^[\d()\-\+\.\s]+$/;
  var reg3 = /^[A-Za-z0-9]+$/;
  var reg4 = /^[A-Za-z0-9\-\_\.\@\&]+$/;
  if(flag == 1){
  	reg = reg2;
  }else if(flag == 2){
  	reg = reg3;
  }else if(flag == 3){
  	reg = reg4;
  }
  if(reg.test(str)){
//	console.log(reg.test(str))
  	for(var i=0;i<str.length;i++){
	    if (str.charCodeAt(i) == 12288){
	      tmp += String.fromCharCode(str.charCodeAt(i)-12256);
	      continue;
	    }
	    if(str.charCodeAt(i) > 65280 && str.charCodeAt(i) < 65375){
	      tmp += String.fromCharCode(str.charCodeAt(i)-65248);
	    }
	    else{
	      tmp += String.fromCharCode(str.charCodeAt(i));
	    }
	  }
  }else{
  	tmp = str.replace(/[^A-Za-z0-9\.]/g, '');
  	if(flag == 1){
	  	tmp = str.replace(/[^\d()\.\-\+\s]/g, '');
	  }
  }
  return tmp
}
/**
 * 方法名  number_va_length
 * 方法的说明  type number 有效性验证长度
 * @param String value,number
 * @return String
 * @author作者 zlx
 * @date 日期 2018.12.29
 */
function number_va_length(value,number,sec){
	if(sec != undefined){
	 var flag = number_fl_ck(value,number,sec);
	 if(!flag){	 	
	 	while(!number_fl_ck(value,number,sec)){
	 		value=value.substring(0,value.length-1);
	 	}
	 }
	 return value;
	}
	if(value.indexOf('.') != -1){
		number ++;
		if(value.length > number){
			value = value.slice(0,number);
			return value;
		}else{
			return value;
		}
	}else{
		if(value.length > number){
			value = value.slice(0,number);
			return value;
		}else{
			return value;
		}
	}		
}
/**
 * 方法名  number_va_length
 * 方法的说明  14,2 10,5状态判断
 * @param String value,count
 * @return String
 * @author作者 zlx
 * @date 日期 2018.12.29
 */
function number_fl_ck(value,count,sec){
	if(value.indexOf('.') != -1){
		var arr = value.split('.');
		if(arr.length > 2){
			return false;
		}else{
			var a = arr[0],b = arr[1];
			if((a.length + b.length) >count || b.length > sec || a.length > (count - sec)){
				return false;
			}else{
				return true;
			}
		}		
	}else{
		if(sec == undefined){
			sec = 0;
		}
		var maxlength = count - sec;
		if(value.length > maxlength){
			return false;
		}else{
			return true;
		}
	}
}
//added 发票非空验证抽离  prevent 入金逻辑冲突
function invoice_require_check() {
	var flg = true;
	$("input[type='text']#invoiceNo.required_flag").each(function (index,element) {
		if(element.value == ""){
			flg = false;
			element.style.borderColor = "red";
			validate($(element),part_language_change_new('NODATE'));
			if(!$('.panel.window.panel-htop.messager-window').exist()){
				showErrorHandler("NOT_EMPTY", "ERROR", "ERROR");
			}
		}else {
			element.style.borderColor = "#cccccc";
			//销毁tooltip事件
			$(element).tooltip('destroy');
		}
	})
	return flg;
}

