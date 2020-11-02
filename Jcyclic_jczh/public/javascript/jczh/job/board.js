$(function(){
	sessionStorage.setItem("board_login_state", 0);
	//登录
	$('#myModalBoard_t').on('shown.bs.modal', function () {
		if(sessionStorage.getItem("board_login_state") == 0){
			//新增前先清空标题和内容
			$('.message-board-title').val('');
			$('.message-board-title').css('border-color','#ccc');
			$('.message-board-title').tooltip('destroy');
			$('.edui-container').css('border-color','#ccc');
			$('.edui-container').tooltip('destroy');
			$.flagCheck=null;
			initBoardListAdd();
			var um = iniLoadboard();
			initBoardEvent(um);
			//更新按钮变登录按钮  需要语言判断
			$(".board_title").html(part_language_change_new('board_login'));
			var language = localStorage.getItem('language');
			$("#submitsave").attr("value",part_language_change_new('ltdRegistration_login'));			
			sessionStorage.setItem("board_login_state", 0);
			//设置留言板高度
            UE.getEditor('myEditor').setHeight(100);
		}else{
			$(".board_title").html(part_language_change_new('board_title_change'));
		}
	})
	//修改
	$('#myModalBoard').on('shown.bs.modal', function () {
		initBoardList();
		var um = iniLoadboard();
		initBoardEvent(um);
	})
	$('.icon-J-LOGO').on('click',function(){
		goToplist();
	})
})
var deparcd="";
var updatelevel;
var locknum;
function iniLoadboard(){
	var language=localStorage.getItem('language'),language_editor;
	if (language == 'en'){
		language_editor = 'en';
	}else if (language == 'jp'){
		language_editor = 'jp';
	}else if (language == 'zc'){
		language_editor = 'zh-cn';
	}else if (language == 'zt'){
		language_editor = 'zt';
	} else {
		language_editor = 'zh-cn';
	}
	//实例化编辑器
	var um = UE.getEditor('myEditor',{
		//这里可以选择自己需要的工具按钮名称,此处仅选择如下五个
		toolbars:[['bold','underline','forecolor','fontfamily','fontsize','link','unlink']],
		//focus时自动清空初始化时的内容
		autoClearinitialContent:true,
		//关闭字数统计
		wordCount:false,
		//关闭elementPath
		elementPathEnabled:false,
		//默认的编辑区域高度
		initialFrameHeight:100,
		lang:language_editor,
		zIndex : 999999
	});
	$(".edui-container").css("width", "100%");
	obj = {};
	$.flagCheck = null;	
	//代替editor.execCommand('cleardoc');
	um.ready(function () {
		UE.getEditor('myEditor').setContent('');
	})
	$('input.message-board-title').val('');
	return um;
	//initBoardList(um);
}
function initBoardEvent(um){
	//解绑事件
	$(".panel-history,#alter,#delect,div.panel-body.messageBoard input[type='button'],.iconfont.icon-xianshigengduo").unbind('click');
	$('#alter').on('click',function(){			
		var idChaneg = $('.panel-history').find("input[type='hidden']").val();
		updatelevel = $('.panel-history').find('.panel-heading').find('input[type="hidden"]').val();
		var manageflag = $('#manageflag').val();//管理组权限
		var companylevel = $('#companylevel').val();//公司级别
		var roletype = $('#roletype').val();//留言板权限
		var msg_level = "";
		if(manageflag=="1"){
			msg_level="1";
		}
		else{
			msg_level="0";
		}   		
		path = $.getAjaxPath()+"MsgtrnCheck";
		allData = {  	 			   
	     	"msgtrn": {
	     		"num": idChaneg,
	         	"msg_level": updatelevel,
	         	"manageflag":manageflag,
	         	"companylevel":companylevel,
	         	"roletype":roletype
					}
			}
	   		
	   	$.ajax({
		  type: "POST",
		  url:path,
		  contentType:"application/json",
		  data:JSON.stringify(allData),
		  dataType:"JSON",
		  headers: {"requestID":$.getRequestID(),"requestName":$.getRequestNameByMst()},
		  success:function(data){
		           if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
		        	   $.resetRequestID(data[$.getRequestUserInfoName()].requestID);
		           	if(data[$.getRequestDataName()]=="NOCHANGE"){
		           		showErrorHandler("SYS_VALIDATEPOWER_ERROR","info","info");
		           		return;
		           	}
					$.flagCheck = idChaneg;
					var headerText = $('.panel-content').find('h5').text();
					var bodyText = $('.panel-content').find('p.msgtexts').prop("outerHTML");
					var length = $('.panel-content').find('.panel-content').children().length;
					var iniLen = 2;
					$('#myModalBoard').modal('hide');
					$('#myModalBoard_t').modal('show');
					//获取留言板高度
				    var boardAlterHeight = $('#myModalBoard').find('p.msgtexts').height();
					sessionStorage.setItem("board_login_state", 1);
					while(iniLen < length){
						bodyText += $(this).find('.panel-content').children().eq(iniLen).prop("outerHTML");
						iniLen ++;
					}
					setContent(headerText,bodyText,boardAlterHeight);
					//点击修改，修改那快的按钮从登陆变成修改
					$("#submitsave").val($("#alter").val());
		           }else{
		            	showErrorFunHandler(data[$.getRequestMetaName()].result,"ERROR","ERROR");
		           }	           
		       },
		       error:function(data){
		       		showErrorHandler("CONNECTION_SERVER_ERROR","ERROR","ERROR");
		       }
		 });			  		
	})
	//删除事件
	$('#delect').on('click',function(){
		var manageflag = $('#manageflag').val();//管理组权限
   		var companylevel = $('#companylevel').val();//公司级别
   		var roletype = $('#roletype').val();//留言板权限
		var c = $('.panel-history');
		var msg = "";
		var confirmTitle = $.getConfirmMsgTitle();
		msg = showConfirmMsgHandler("SURE_DELETE");
		if($.messager) {
			if(localStorage.getItem('language') == 'jp' || localStorage.getItem('language') == '001') {
				$.messager.defaults.ok = 'はい';
				$.messager.defaults.cancel = 'いいえ';
			} else if(localStorage.getItem('language') == 'zc' || localStorage.getItem('language') == '002') {
				$.messager.defaults.ok = '是';
				$.messager.defaults.cancel = '否';
			}
		}

		$.messager.confirm(confirmTitle,msg, function(r){
			if(r)
			{
			var id = c.find("input[type='hidden']").val();
			var msg_level = c.find("#msglevel").val();
			var locknum = c.find("#msglevel").attr('zhiyu');
			var path = $.getAjaxPath()+"MsgtrnDelete";
		 	var allData = {  	 			   
		         	"msgtrn": {
		             	"num": id,
	             		"manageflag":manageflag,
	             		"companylevel":companylevel,
	             		"roletype":roletype,
	             		"msg_level": msg_level,
	             		"lock_flg":locknum
		    				}
		 		}
		 	//提交
			keepboard(path,allData);	
			}
		});
	})
	//留言板登录点击事件
	$("div.panel-body.messageBoard input[type='button']").on('click',function(e){
		var content='';
		var boardStrU;
		//获取编辑器的内容
		um.ready(function() {
		    boardStrU = $(um.getContent());
		    var str = '<br/>';
		    for(var i = 0;i < boardStrU.length;i ++){
		    	content += boardStrU.eq(i).html() + str;
		    }
		});
//		console.log(content)
		//获取标题
		var title = $('input.message-board-title').val();
		if (title ==null || title==''){
   			$('.message-board-title').css('border-color','red');
   			validate($('.message-board-title'),part_language_change_new('NODATE'));
   			if(!$('.panel.window.panel-htop.messager-window').exist()){
	 			showErrorHandler("NOT_EMPTY", "ERROR", "ERROR");
	 		}
			//return false;
		}else{
			$('.message-board-title').css('border-color','#ccc');
			$('.message-board-title').tooltip('destroy');
		}
   		if (content ==null || content==''){
   			$('.edui-container').css('border-color','red');
   			validate($('.edui-container'),part_language_change_new('NODATE'));
   			if(!$('.panel.window.panel-htop.messager-window').exist()){
	 			showErrorHandler("NOT_EMPTY", "ERROR", "ERROR");
	 		}
			//return false;
		}else{
			$('.edui-container').css('border-color','#ccc');
			$('.edui-container').tooltip('destroy');
		}
		if(content ==null || content=='' || title ==null || title==''){
			return false;
		}
   		var contentBr = um.getContentTxt();
   		if(contentBr.length > 500){
   			showErrorHandler("TEXTAREA_500","info","info");
			return false;
   		}  		
   		var path;
   		var allData;
   		var manageflag = $('#manageflag').val();//管理组权限
   		var companylevel = $('#companylevel').val();//公司级别
   		var roletype = $('#roletype').val();//留言板权限
   		var c = $('.panel-history');
   		var locknum = c.find("#msglevel").attr('zhiyu');
   		var msg_level = "";
   		if(manageflag=="1"){
   			msg_level="1";
   		}
   		else{
   			msg_level="0";
   		}
   		if ($.flagCheck!=null && $.flagCheck!=''){
   			var id = $.flagCheck;
   			 path = $.getAjaxPath()+"MsgtrnUpdate";
   			 allData = {  	 			   
	         	"msgtrn": {
	         		"num": id,
	             	"msg_level": updatelevel,
	             	"msg_title": title,
	             	"msg_text": content,
	             	"manageflag":manageflag,
	             	"companylevel":companylevel,
	             	"roletype":roletype,
	             	"lock_flg":locknum
	    				},
	    			"num": id,
	             	"msg_level": updatelevel,
	             	"msg_title": title,
	             	"msg_text": content,
	             	"manageflag":manageflag,
	             	"companylevel":companylevel,
	             	"roletype":roletype,
	             	"lock_flg":locknum,
		             "contentBr":contentBr
	 			}
   		}
   		else{
   		   	 path = $.getAjaxPath()+"MsgtrnAdd";
   			 allData = {  	 			   
	         	"msgtrn": {
	             	"msg_level": msg_level,
	             	"msg_title": title,
	             	"msg_text": content,
	             	"manageflag":manageflag,
	             	"companylevel":companylevel,
	             	"roletype":roletype
	    				},
	    			"msg_level": msg_level,
	             	"msg_title": title,
	             	"msg_text": content,
	             	"manageflag":manageflag,
	             	"companylevel":companylevel,
	             	"roletype":roletype,
		            "contentBr":contentBr		
	 			}	
   		}

	 	
	 	//提交
	 	keepboard(path,allData);
		})
}
function setContent(headerText,bodyText,boardAlterHeight){
	UE.getEditor('myEditor').setContent(bodyText);
    $('.messageBoard .form-group input.form-control').val(headerText);
    if (boardAlterHeight > 100){
		UE.getEditor('myEditor').setHeight(boardAlterHeight + 25);
	}else {
		UE.getEditor('myEditor').setHeight(100);
	}
}
function checkFlag(){
	var count = 0;
		$('.panel-history').each(function(index,dom){
			var flag = $(dom).find('.panel-heading').hasClass('color');
			if(flag){
				count ++;
			}
		})
	return count;
}
function initBoardList()
{
		var path = $.getAjaxPath()+"MsgtrnQuery";
		var pars = {
			id:sessionStorage.getItem("panid")
		};
		$.ajax({
		url:path,
		headers: {"requestID":$.getRequestID(),"requestName":$.getRequestNameByMst()},
		data:JSON.stringify(pars),
		success:function(data){			
           	if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
           		$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
           		deparcd = data[$.getRequestDataName()].companylevel;
           		locknum = data[$.getRequestDataName()].lock_flg;
           		//是否有管理组权限
           		$('#manageflag').val(data[$.getRequestDataName()].manageflag)
           		//公司级别0：博報堂、1：博报堂系以外、2：管理用
           		$('#companylevel').val(data[$.getRequestDataName()].companylevel)
           		$("#roletype").val('1')//留言板权限 0:没有 ,1:有
				var total = data[$.getRequestDataName()].msgtrnList;
				var str = "";
					str += '<div class="panel-history" style="margin-top: 10px;">'+
						'<div class="panel panel-default">'+
						'<input type="hidden" value= "'+total[0].id+'"/>'+
						'<a id="board'+total[0].id+'"></a>'+
							'<div class="panel-heading clear" style="background-color: #ecf0f1;">';
							var MSG_LEVEL = total[0].msglevel;
							var NOTICE = getGridLanguage("msgtrn",MSG_LEVEL);
							if (MSG_LEVEL=='0'){
								str +='<b><i class="iconfont icon-gerentongzhi"></i><span style="font-size:15px;font-weight:bold;top:-2px;" name="" class=""><'+NOTICE+'></span></b>'+
								'<input type="hidden" id="msglevel" zhiyu='+locknum+' value= "0" />';
							}
							else{
								str +='<b><i class="iconfont icon-xitongtongzhi"></i><span name="" class="" style="font-size:15px;font-weight:bold;top:-2px;"><'+NOTICE+'></span></b>'+
								'<input type="hidden" id="msglevel" zhiyu='+locknum+' value= "1" />';
							}								
								str +='<span class="date pull-right">'+
									'<span style="color:#999999;">'+total[0].update.substring(0,10)+'</span>'+
									'<small class="text-muted">	'+total[0].update.substring(11,16)+'</small>'+
								'</span>'+
							'</div>'+
							'<div class="panel-content" style="padding: 15px;">'+
								'<h5 style="font-size:18px;word-break: break-all;">'+total[0].msgtitle+'</h5>'+
								'<p class="msgtexts active">'+
									'<span id="">'+total[0].msgtext+'</span>'+		
								'</p>'+
							'</div>'+
						'</div>'+
					'</div>';
				$('#myModalBoardTwo').empty();
				$('#myModalBoardTwo').append($(str));
				//var um = iniLoadboard();
				//initboard();
				//添加事件
				//initBoardEvent(um);
				$("#delect").removeClass("hidden");
				$("#alter").removeClass("hidden");
				//判断权限
				if(isHavePower(data[$.getRequestDataName()].roleList,[1])==false){
					$("#roletype").val('0')
					//$("#add").addClass("hidden");
					$("#delect").addClass("hidden");
					$("#alter").addClass("hidden");
				}
				//非管理组权限的人，看管理信息 应该把变更和删除按钮删除
				if(data[$.getRequestDataName()].manageflag!=1&&MSG_LEVEL=='1'){
					$("#delect").addClass("hidden");
					$("#alter").addClass("hidden");
				}
				//管理组权限的人，操作普通信息，应该把变更和删除按钮删除
				if(data[$.getRequestDataName()].manageflag==1&&MSG_LEVEL=='0'){
					$("#delect").addClass("hidden");
					$("#alter").addClass("hidden");
				}
				//判断是否为空
				var panidnum = sessionStorage.getItem("panid");
				if(panidnum == "" || panidnum == null){
				  return;
				}
					var isread = sessionStorage.getItem("isread");
					var msgId = sessionStorage.getItem("msgId");
				if(isread==0){
					 changeMsgStatus(msgId);
				}
				var arr = [];
				for(var i =  0;i < $('.panel-history').length;i++){
					arr.push($('.panel-history').eq(i).find('input[type="hidden"]').val());
				}
				var index = arr.indexOf(panidnum);
				var indexClick = parseInt(index/3);
				if(indexClick == 0){
					return;
				}
				for(var i = 1;i <= indexClick;i ++){
					$('.iconfont.icon-xianshigengduo').click();
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
function keepboard(path,allData){
	var keeppath = path;
 	var keepData = allData;
	 $.ajax({
	  type: "POST",
	  url:keeppath,
	  contentType:"application/json",
	  data:JSON.stringify(keepData),
	  dataType:"JSON",
	  headers: {"requestID":$.getRequestID(),"requestName":$.getRequestNameByMst()},
	  success:function(data){
	           if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
	        	   $.resetRequestID(data[$.getRequestUserInfoName()].requestID);
	           	if(data.data==-1){//lockflg验证
		           	$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
		           	showLockInfoMsgHandler("STATUS_VALIDATEPOWER_ERROR","/jczh/top_registration.html");
		           	return false;
		        }
	           	if(data[$.getRequestDataName()]=="NOCHANGE"){
	           		showErrorHandler("SYS_VALIDATEPOWER_ERROR","info","info");
	           		return false;
	           	}
	           	if(data[$.getRequestDataName()]=="NOADD"){
	           		showErrorHandler("SYS_VALIDATEPOWER_ERROR","info","info");
	           		return false;
	           	}
	           	if(data[$.getRequestDataName()]=="NODELETE"){
	           		showErrorHandler("SYS_VALIDATEPOWER_ERROR","info","info");
	           		return false;
	           	}
	           showErrorHandler("EXECUTE_SUCCESS","info","info");	           
	           var board_stra = true;
	           $('.messager-button a.l-btn-small').click(function(){
	           		if(board_stra){
	           			//处理成功后消失页面
	           			$('#board_close,#board_clo').click();
	           			window.location.reload();
	           		}
	           })
	           //重新加载留言板页面 保证实时更新
//	           $.ajax({
//					url: $.getAjaxPath() + "topLoad",
//					data: JSON.stringify({}),
//					headers: {
//						"requestID": $.getRequestID(),
//						"requestName": $.getRequestNameByJcZH()
//					},
//					success: function(data) {
//						panelPart(data[$.getRequestDataName()]['msgTrn']);
//						//留言板 点击跳到留言板页面 传参 点击条的 id
//						$('.news').click(function() {
//							var id = $(this).find('input').val();
//							var isread = $(this).find('input.isread').val();
//							sessionStorage.setItem("panid", id);
//							sessionStorage.setItem("isread",isread);
//							sessionStorage.setItem("msgId",id);
//						});
//					},
//					error: function(msg) {
//						window.location.href = $.getJumpPath();
//					}
//				});
	           }else{
	            	showErrorFunHandler(data[$.getRequestMetaName()].result,"ERROR","ERROR");
	           }	           
	           //iniLoadboard();
	           //initBoardList();
	       },
	       error:function(data){
	       		showErrorHandler("CONNECTION_SERVER_ERROR","ERROR","ERROR");
	       }
	 });
}
function checkquery(idChaneg){//校验权限
	var checkflg;
	var manageflag = $('#manageflag').val();//管理组权限
	var companylevel = $('#companylevel').val();//公司级别
	var roletype = $('#roletype').val();//留言板权限
	var msg_level = "";
	if(manageflag=="1"){
		msg_level="1";
	}
	else{
		msg_level="0";
	}   		
	path = $.getAjaxPath()+"MsgtrnCheck";
	allData = {  	 			   
     	"msgtrn": {
     		"num": idChaneg,
         	"msg_level": updatelevel,
         	"manageflag":0,
         	"companylevel":companylevel,
         	"roletype":roletype
				}
		}
   		
   	$.ajax({
	  type: "POST",
	  url:path,
	  contentType:"application/json",
	  data:JSON.stringify(allData),
	  dataType:"JSON",
	  headers: {"requestID":$.getRequestID(),"requestName":$.getRequestNameByMst()},
	  success:function(data){
	           if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
	        	   $.resetRequestID(data[$.getRequestUserInfoName()].requestID);
	           	if(data[$.getRequestDataName()]=="NOCHANGE"){
	           		showErrorHandler("SYS_VALIDATEPOWER_ERROR","info","info");
	           		checkflg =0;
	           	}
	           }else{
	            	showErrorFunHandler(data[$.getRequestMetaName()].result,"ERROR","ERROR");
	           }	           
	       },
	       error:function(data){
	       		showErrorHandler("CONNECTION_SERVER_ERROR","ERROR","ERROR");
	       }
	 });
	 return checkflg;
}
function initBoardListAdd()
{
		var path = $.getAjaxPath()+"MsgtrnQuery";
		var pars = {
			id:sessionStorage.getItem("panid")
		};
		$.ajax({
		url:path,
		headers: {"requestID":$.getRequestID(),"requestName":$.getRequestNameByMst()},
		data:JSON.stringify(pars),
		success:function(data){			
           	if(data[$.getRequestMetaName()].result == $.getRequestStatusName()){
           		$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
           		deparcd = data[$.getRequestDataName()].companylevel;
           		locknum = data[$.getRequestDataName()].lock_flg;
           		//是否有管理组权限
           		$('#manageflag').val(data[$.getRequestDataName()].manageflag)
           		//公司级别0：博報堂、1：博报堂系以外、2：管理用
           		$('#companylevel').val(data[$.getRequestDataName()].companylevel)
           		$("#roletype").val('1')//留言板权限 0:没有 ,1:有
           }else{
            	showErrorFunHandler(data[$.getRequestMetaName()].result,"ERROR","ERROR");
           }
       },
       error:function(data){
       		showErrorHandler("CONNECTION_SERVER_ERROR","ERROR","ERROR");
       }
	});
	
	
}