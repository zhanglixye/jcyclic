$(function() {
	topLoad();
	sessionStorage.clear();
});
//加载
function topLoad() {
	$.ajax({
		url: $.getAjaxPath() + "topLoad",
		data: JSON.stringify({}),
		headers: {
			"requestID": $.getRequestID(),
			"requestName": $.getRequestNameByJcZH()
		},
		success: function(data) {
			if(data[$.getRequestMetaName()].result == $.getRequestStatusName()) {
				$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
				if(data[$.getRequestDataName()] != null) {
					$.setAccountDate(data[$.getRequestDataName()]['accountDate']);
					TopObject.userInfo = data[$.getRequestUserInfoName()];
					//costPart();
					var powerList = data[$.getRequestUserInfoName()]['uNodeList'];
					if(data[$.getRequestDataName()]['myLable']!=null){
						lablePart(data[$.getRequestDataName()]['myLable'], 0);
					}
					if(data[$.getRequestDataName()]['sysLable']!=null){
						lablePart(data[$.getRequestDataName()]['sysLable'], 1);
					}
					
					panelPart(data[$.getRequestDataName()]['msgTrn']);
					$.setDefaultPoint(data[$.getRequestUserInfoName()]['pointNumber']);
				    $.setNodeList(JSON.stringify(data[$.getRequestUserInfoName()]['uNodeList']));
				    jobPart(data[$.getRequestDataName()]['adcount'], data[$.getRequestDataName()]['noadcount'],
				    data[$.getRequestDataName()]['progressCount'],data[$.getRequestDataName()]['isSellRegistrationNums'],data[$.getRequestDataName()]['notSellRegistrationNums'],
				   data[$.getRequestDataName()]['costNotFinshNums'], data[$.getRequestDataName()]['isSellRegistrationAmt'],data[$.getRequestDataName()]['notSellRegistrationAmt']);
					clickCllection();
					
				    if(!isHavePower(powerList,[5,6,7,8])){
				    	
				    	per_verificate($('#job-hover-show'));
				    	per_verificate($('#job-hover-show2'));
				    	per_verificate($('#job-hover-show3'));
				    	if(data[$.getRequestDataName()]['myLable']!=null){
				    		per_verificate($('#mylable li span'));
				    	    per_verificate($('#syslable li span'));
				    	    $('#mylable li').unbind("click");
				    	    $('#syslable li').unbind("click");
				    	}
				    	
				    	
				    	$('.sell-num').unbind("click");
				    	$('.not-sell-num').unbind("click");
				    	$('.cost-finishnum').unbind("click");
				    	$('.top-search').addClass("hidden");
				    	//$('.icon-sousuo').unbind("click");
//				    	$('#mylable li').unbind("click");
//				    	$('#syslable li').unbind("click");
				    	
				    }
				    if(!isHavePower(powerList,[40,41,42,43])){
				    	per_verificate($('#cost-ad-color'));
				    	per_verificate($('#cost-ad-color2'));
				    	$('.cost-ad').unbind("click");
				    	$('.cost-no-ad').unbind("click");
				    }
				    
				     if(!isHavePower(powerList,[1])){
				    	$(".iconfont.icon-tianjia").addClass("hidden");
				    }
				} else {
					showErrorHandler("EXECUTE_FAIL", "ERROR", "ERROR");
				}
			} else {
				showErrorFunHandler(data[$.getRequestMetaName()].result, "ERROR", "ERROR");
			}
			//右侧留言列表内容自适应宽度
			var cont_width = $(".newsz").width();
			var first_width = $(".news-l").width();
			var second_width = $(".news-r1").width();
			var thired_width = $(".news-r").width();
			var finally_width = cont_width - first_width - second_width - thired_width;
			$(".news-m").width(finally_width - 6);
		},
		error: function(msg) {
			window.location.href = $.getJumpPath();
		}
	});
}
//点击
function clickCllection() {
	//到job一览  查询业务登录job
	var AccountDate =$.getAccountDate().replace("/","-");
	$('.sell-num').click(function() {
		window.location.href = $.getJumpPath() + 'jcst/job_registration_list.html?view=job&statusCode=004&dlvday='+AccountDate;
	});
	//到job一览 查询已登录业务未处理job
	$('.not-sell-num').click(function() {
		window.location.href = $.getJumpPath() + 'jcst/job_registration_list.html?view=job&statusCode=002&dlvday='+AccountDate;
	});
	//到job一览 查询 已终止成本未处理件数
	$('.cost-finishnum').click(function() {
		window.location.href = $.getJumpPath() + 'jcst/job_registration_list.html?view=job&costCode=001&dlvday='+AccountDate;
	});
	//到成本一览，成本本月批准成本 flg = 1
	$('.cost-ad').click(function() {
		window.location.href = $.getJumpPath() + 'jcst/cost_list.html?view=0';
	});
	//到成本一览， 成本本月未批准成本 flg = 0
	$('.cost-no-ad').click(function() {
		window.location.href = $.getJumpPath() + 'jcst/cost_list.html?view=1';
	});
	//跳到job一览 根据keyword 模糊检索
	$('.icon-sousuo').click(function() {
		var keyWord = $('#keyword').val();
		window.location.href = $.getJumpPath() + 'jcst/job_registration_list.html?view=top&keyword=' + keyWord;
	});
	//留言板 点击跳到留言板页面 传参 点击条的 id
	$('.news').click(function() {
		var id = $(this).find('input').val();
		var isread = $(this).find('input.isread').val();
		sessionStorage.setItem("panid", id);
		sessionStorage.setItem("isread",isread);
		sessionStorage.setItem("msgId",id);
		
		//window.location.href = $.getJumpPath()+'master/label_management.html?id='+id;
	});
	//我的标签 传参 点击标签的 文本跳到job一览
	$('#mylable li').click(function() {
		var labletext = $(this).find('span').text();
		window.location.href = $.getJumpPath() + 'jcst/job_registration_list.html?view=job&labelText=' + labletext;
	});
	//我的标签 传参 点击标签的 文本跳到job一览
	$('#syslable li').click(function() {
		var labletext = $(this).find('span').text();
		window.location.href = $.getJumpPath() + 'jcst/job_registration_list.html?view=job&labelText=' + labletext;
	});
	 $('#language').change(function(){
    	var userData = TopObject.getUserInfo();
    	setPersonMoneyCode(userData);
    })

}
//改变留言板状态
function changeMsgStatus(id){
	var top  = {
		msgId:id
	}
		$.ajax({
		url: $.getAjaxPath() + "changeMsgStatus",
		data: JSON.stringify(top),
		headers: {
			"requestID": $.getRequestID(),
			"requestName": $.getRequestNameByJcZH()
		},
		success: function(data) {
			if(data[$.getRequestMetaName()].result == $.getRequestStatusName()) {
				$.resetRequestID(data[$.getRequestUserInfoName()].requestID);
				if(data[$.getRequestDataName()] != null) {
					panelPart(data[$.getRequestDataName()]);
				} else {
					showErrorHandler("EXECUTE_FAIL", "ERROR", "ERROR");
				}
			} else {
				showErrorFunHandler(data[$.getRequestMetaName()].result, "ERROR", "ERROR");
			}
		$('.news').click(function() {
		var id = $(this).find('input').val();
		var isread = $(this).find('input.isread').val();
		sessionStorage.setItem("panid", id);
		sessionStorage.setItem("isread",isread);
		sessionStorage.setItem("msgId",id);
//		if(isread==0){
//			 changeMsgStatus(id);
//		}
//		window.location.href = $.getJumpPath()+'master/label_management.html?id='+id;
	});
		},
		error: function(msg) {
			window.location.href = $.getJumpPath();
		}
	});
}
//留言板
function panelPart(panelList) {
	var str = "";
	for(var i = 0; i < panelList.length; i++) {
		if(panelList[i]['msglevel'] == 1) {
			if(panelList[i]['isread'] == 0) {
			str += '<div class="news" data-toggle="modal" data-target="#myModalBoard" >' +
				'<input type="hidden" value="' + panelList[i]['id'] + '"/>'+
				 '<div class="news-d">	● </div>'+
				        '<input type="hidden" class="isread" value="'+panelList[i]['isread']+'"/>';
			} else {
				str += '<div class="news vs" data-toggle="modal" data-target="#myModalBoard" >' +
				'<input type="hidden" value="' + panelList[i]['id'] + '"/>'+
				'<div class="news-d"></div>';
			}

			str += '<div class="newsz">' +
				'<div class="news-l">' +
				'<i class="iconfont icon-xitongtongzhi"></i>' +
				'</div>' +
				'<div class="news-m overwhite">'+
				'<span>' + panelList[i]['msgtitle'] + '</span>'+
				'<span> - '+panelList[i]['msgtext'].replace(/&nbsp;|<br\/>|<br>|<span>|<\/span>/g, "")+'</span>'+
				'</div>' +
				'<div class="news-r1">' +
				'<i class="iconfont icon-xiala no_animation"></i>' +
				'</div>' +
				'<div class="news-r">' +
				'<div class="news-r-t">' + strToDate(panelList[i]['update'], 1) + '</div>' +
				'<div class="news-r-b">' + strToDate(panelList[i]['update'], 2) + '</div>' +
				'</div>' +
				'</div>' +
				'</div>';
		} else {
			if(panelList[i]['isread'] == 0) {
			str += '<div class="news" data-toggle="modal" data-target="#myModalBoard" >' +
				'<input type="hidden" value="' + panelList[i]['id'] + '"/>'+
				 '<div class="news-d">	● </div>'+
				'<input type="hidden" class="isread" value="'+panelList[i]['isread']+'"/>';
			} else {
				str +=  '<div class="news vs" data-toggle="modal" data-target="#myModalBoard" >' +
				'<input type="hidden" value="' + panelList[i]['id'] + '"/>'+
				'<div class="news-d"></div>';
			}

			str += '<div class="newsz">' +
				'<div class="news-l">' +
				'<i class="iconfont icon-gerentongzhi"></i>' +
				'</div>' +
				'<div class="news-m overwhite">'+
				'<span>' + panelList[i]['msgtitle'] + '</span>'+
				'<span> - '+panelList[i]['msgtext'].replace(/&nbsp;|<br\/>|<br>|<span>|<\/span>/g, "")+'</span>'+
				'</div>' +
				'<div class="news-r1">' +
				'<i class="iconfont icon-xiala no_animation"></i>' +
				'</div>' +
				'<div class="news-r">' +
				'<div class="news-r-t">' + strToDate(panelList[i]['update'], 1) + '</div>' +
				'<div class="news-r-b">' + strToDate(panelList[i]['update'], 2) + '</div>' +
				'</div>' +
				'</div>' +
				'</div>';
		}

	}
	$("#panel-part").html(str);
	//右侧留言列表内容自适应宽度
	var cont_width = $(".newsz").width();
	var first_width = $(".news-l").width();
	var second_width = $(".news-r1").width();
	var thired_width = $(".news-r").width();
	var finally_width = cont_width - first_width - second_width - thired_width;
	$(".news-m").width(finally_width - 6);
}
//lable flg 判断标签 level 0自己: 1 管理者
function lablePart(lableList, flg) {
	var str = "";
	for(var i = 0; i < lableList.length; i++) {
	 if(lableList[i]['lableid']==null||lableList[i]['lableid']==""){
	 	continue ;
	 }else{
	 	if(flg == 0) {
			str += '<li class="s1"><span>' + lableList[i]['labletext'] + '</span></li>';
		} else {
			str += '<li class="s2"><span>' + lableList[i]['labletext'] + '</span></li>';
		}
	 }
		
	}
	if(flg == 0) {
		$('#mylable').html(str);
	} else {
		$('#syslable').html(str);
	}
}
//成本
function costPart(adcount,noadcount,progressCount) {
	var costnoad = parseInt(noadcount);
	var costad = parseInt(adcount);
	//var sum = floatObj.add(costnoad,costad);
	var sum = progressCount;

	$('#cost-all').text(formatNumber(sum,0));
	$('#cost-ad').text(formatNumber(costad,0));
	$('#cost-no-ad').text(formatNumber(costnoad,0));
    var pre5_costad_sub =  floatObj.divide(costad,sum);
    var pre5_costad = floatObj.multiply(pre5_costad_sub,100);
    var pre6_costnoad_sub = floatObj.divide(costnoad,sum);
    var pre6_costnoad= floatObj.multiply(pre6_costnoad_sub,100);
	$('#indicatorContainer5').attr('num', isNaN(pre5_costad) ? '0' : pre5_costad);
	$('#indicatorContainer6').attr('num', isNaN(pre6_costnoad) ? '0' : pre6_costnoad);

}
//job
function jobPart(adcount,noadcount,progressCount,isSellRegistrationNums, notSellRegistrationNums,costNotFinshNums,isSellRegistrationAmt,notSellRegistrationAmt) {
	var jobSellRagNum = parseInt(isSellRegistrationNums);
	var jobNotSellNum = parseInt(notSellRegistrationNums);
	var jobCostFinishNum = parseInt(costNotFinshNums);
//	var sum = jobSellRagNum + jobNotSellNum;
    var sum = floatObj.add(jobSellRagNum,jobNotSellNum);
    var point = $.getDefaultPoint();
	var sellRegAmt = parseFloat(isSellRegistrationAmt).toFixed(point);
	var notSellAmt = parseFloat(notSellRegistrationAmt).toFixed(point);
	$('#job-sell-regnum').text(formatNumber(jobSellRagNum,0));
	$('#job-sell-regamt').text(isNaN(sellRegAmt) ? Number(0).toFixed(point) : formatNumber(sellRegAmt));
	$('#job-not-sell-num').text(formatNumber(jobNotSellNum,0));
	$('#job-not-sell-amt').text(isNaN(notSellAmt) ? Number(0).toFixed(point) : formatNumber(notSellAmt));
	$('#job-cost-finishnum').text(formatNumber(jobCostFinishNum,0));
	$('#job-all-num').text(formatNumber(sum,0));
    //设置货币单位
    var userData = TopObject.getUserInfo();
    setPersonMoneyCode(userData);
    var job2_sub = floatObj.divide(jobSellRagNum,sum);
    var job2 = floatObj.multiply(job2_sub,100);
    
    var job3_sub = floatObj.divide(jobNotSellNum,sum);
    var job3 = floatObj.multiply(job3_sub,100);
    
    var job4_sub = floatObj.divide(jobCostFinishNum,sum);
    var job4 = floatObj.multiply(job4_sub,100);
    
    $('#indicatorContainer2').attr('num', isNaN(job2) ? '0' : job2);
	$('#indicatorContainer3').attr('num', isNaN(job3) ? '0' : job3);
	$('#indicatorContainer4').attr('num', isNaN(job4) ? '0' : job4);

//	$('#indicatorContainer2').attr('num', isNaN(jobSellRagNum / sum * 100) ? '0' : jobSellRagNum / sum * 100);
//	$('#indicatorContainer3').attr('num', isNaN(jobNotSellNum / sum * 100) ? '0' : jobNotSellNum / sum * 100);
//	$('#indicatorContainer4').attr('num', isNaN(jobCostFinishNum / sum * 100) ? '0' : jobCostFinishNum / sum * 100);

	costPart(adcount,noadcount,progressCount);
	calCircle();
}

function calCircle() {
	$('#indicatorContainer2').radialIndicator({
		barColor: '#29bb9c',
		barWidth: 10,
		initValue: 0,
		roundCorner: true,
		percentage: true,
		displayNumber: false
	});
	$('#indicatorContainer3').radialIndicator({
		barColor: '#29bb9c',
		barWidth: 10,
		initValue: 0,
		roundCorner: true,
		percentage: true,
		displayNumber: false
	});
	$('#indicatorContainer4').radialIndicator({
		barColor: '#29bb9c',
		barWidth: 10,
		initValue: 0,
		roundCorner: true,
		percentage: true,
		displayNumber: false
	});
	$('#indicatorContainer5').radialIndicator({
		barColor: '#29bb9c',
		barWidth: 10,
		initValue: 0,
		roundCorner: true,
		percentage: true,
		displayNumber: false
	});
	$('#indicatorContainer6').radialIndicator({
		barColor: '#29bb9c',
		barWidth: 10,
		initValue: 0,
		roundCorner: true,
		percentage: true,
		displayNumber: false
	});
	$('.prg-cont').each(function() {
		var elm = $(this),
			wrap = elm.wrap('<div class="prg-cont-wrap"></div>').closest('.prg-cont-wrap'),
			radObj = wrap.find('.rad-prg').data('radialIndicator');
		var val = wrap.find('.rad-prg').attr("num");
		radObj.animate(val);
	});
}
TopObject = {
	userInfo : null,
	getUserInfo:function(){
		return this.userInfo;
	}
	
}
