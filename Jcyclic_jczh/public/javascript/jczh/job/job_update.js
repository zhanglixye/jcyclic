$(document).ready(function(){
	onResize_102();
	window.onresize = function(e){
		onResize_102();
	}
	function onResize_102(){
		var width = document.documentElement.clientWidth;
		if(width < 1590){
			$('.bt_up_102').closest('.par_ent').css('width','117%');
			$('.bt_up_102').eq(0).css({'margin-left':'0','margin-right':'5px'});
		}else{
			$('.bt_up_102').closest('.par_ent').css('width','102%');
			$('.bt_up_102').eq(0).css({'margin-left':'5px','margin-right':'0'});
		}
	}
})
$(function() {
	$('#language').change(function() {
	    var langtyp =$(this).val();
		var itemname ="";
			
			if(langtyp == "jp"){
				$(".card_jp").removeClass('hidden');
				$(".card_zc").addClass('hidden');
				$(".card_zt").addClass('hidden');
				$(".card_en").addClass('hidden');
				
			}
			else if(langtyp == "en"){
				$(".card_jp").addClass('hidden');
				$(".card_zc").addClass('hidden');
				$(".card_zt").addClass('hidden');
				$(".card_en").removeClass('hidden');
			}
			else if(langtyp == "zc"){
				$(".card_jp").addClass('hidden');
				$(".card_zc").removeClass('hidden');
				$(".card_zt").addClass('hidden');
				$(".card_en").addClass('hidden');
			}
			else if(langtyp == "zt"){
				$(".card_jp").addClass('hidden');
				$(".card_zc").addClass('hidden');
				$(".card_zt").removeClass('hidden');
				$(".card_en").addClass('hidden');
			}
			$('#jobuserTable .person-m .userlevel').eq(i).text(itemname);
	       
	})
	var arrDelUser = objStorage.getDelCard();
	$('input.inputDate').datebox();
	$.foreignGoodsShow();
	$.jobCommon();
	$.layerShowDiv('setb2', '29%', 'auto', 1, $('#setbig1'));
	//增值税编辑
	$.layerShowDiv('add_tax_bus', 'auto', 'auto', 1, $('.add_tax_edit'));
	$.layerShowDiv('add_tax_price_on', 'auto', 'auto', 1, $('.add_tax_price'));
	sessionStorage.setItem("isSaleFinishFlg",0);
	initShow(arrDelUser);
	$('#common-day').datebox({});
	$('#common-dayT').datebox({});
	var url = $.getJumpPath() + 'common/suppliers_retrieval.html';
	$.layerShow("searchHDY", url);
	$.layerShow("searchPay", url);

});

function cardInit(arrDelUser,saleFinishFlg){
	
	
		$("#md-flg").click(function() {
		var ele = $(this);
		var md_flg = $("#md-flg").prop("checked");
		if(md_flg){
			mdBox(saleFinishFlg, ele);
		}else{
			cardLogic(saleFinishFlg,'mdFlg-click');
		}
		
		
	});
	//点击责任者
	$("#per").on("click", ".level_flg", function() {
		var ele = $(this);
		choseAdmin(ele);
	});
	
   
	//卡片下拉变化
	$("#per").on("change", "select", function() {
		var ele = $(this);
		requestorChange(ele);
	});
	var mdqx = urlPars.parm("md");
	var powerList = JSON.parse($.getNodeList());
							//判断是否只有割当权限
	var bl = isHavePower(powerList, [13]);
//	var bl = isHavePower(powerList, [777]);
						//判断是否有 job更新的其他权限
	var bl2 = isHavePower(powerList, [9, 10, 11, 12]);
	
	if(mdqx =="nomdfp"){
		 $("#del_job").hide();
		bl2 = false;
	}
//	var bl2 = isHavePower(powerList, [8888]);
						//有割当担当权限  没有 基本情报权限，除了割当担当别的地方不可编辑
    if(bl&&!bl2){
        maskShow();
        		//删除卡片
	$("#per").on("click", ".new-card", function() {
		var ele = $(this);
		var usercd = $(this).parents(".person").find("div.usercode").text();
		var isFind = searchValue(objStorage.getCardMessage(),'usercd',usercd,"usercd");
		//不添加重复的人
		if(![].inArray(arrDelUser,usercd)&&isFind!=""&&usercd!=""){
			arrDelUser.push(usercd);
		}
		//objStorage.delCard = arrDelUser;
		removeCard(ele,arrDelUser);

	});
    }
    
    //
    if(!bl&&bl2){
     maskShow(2);
    
    $('#md-flg').attr("disabled",true);
//  $("#per").find("select").attr("disabled", "disabled");
//	$("#per").find("input").attr("disabled", "disabled");
   // $("#per").find(".addp").addClass('hidden');
       //删除卡片
	$("#per").on("click", ".close", function() {
		var ele = $(this);
		var usercd = $(this).parents(".person").find("div.usercode").text();
		var isFind = searchValue(objStorage.getCardMessage(),'usercd',usercd,"usercd");
		//不添加重复的人
		if(![].inArray(arrDelUser,usercd)&&isFind!=""&&usercd!=""){
			arrDelUser.push(usercd);
		}
		//objStorage.delCard = arrDelUser;
		removeCard(ele,arrDelUser);
	});

    }
    if(bl&&bl2){
      	maskShow(2);
    	//删除卡片
	$("#per").on("click", ".close", function() {
		var ele = $(this);
		var usercd = $(this).parents(".person").find("div.usercode").text();
		var isFind = searchValue(objStorage.getCardMessage(),'usercd',usercd,"usercd");
		//不添加重复的人
		if(![].inArray(arrDelUser,usercd)&&isFind!=""&&usercd!=""){
			arrDelUser.push(usercd);
		}
		
		removeCard(ele,arrDelUser);
	});
    }
    //判断卖上登录状态
    cardLogic(saleFinishFlg);
}
//job更新 卡片编辑逻辑
/*
	 * mdFlg-click 辨别 调用此方法 是否 对mdcheckbox进行 无效化处理 
	 */
function cardLogic(saleFinishFlg,flg,jobEndFlg) {
	var md_flg = $("#md-flg").prop("checked");
	/*
	 * job更新 1.卖上登录完了，mdflg选中的情况  没选中的情况
	 *       2.卖上登录未完了， mdflg选中的情况  没选中的情况
	 */
	//卖上完了 卖上登录完了 ，初始化的卡片不可动） 
	if(saleFinishFlg == 1) {
		if(jobEndFlg==0){
			maskShow(999);
		}else{
			  maskShow();
		}
		  
		if(md_flg) {
			//卖上登录完了 checkbox选中 
			//此时不允许更改卡片部分 （因为只能添加md卡片，但md分配flg被选中，所以不能进行操作）
			if(jobEndFlg!=0){
				$('.addp').addClass("hidden");
			}else{
				$('#md-flg').removeAttr("disabled");
				$('.addp').addClass("hidden");
			}
		} else {
			$('.addp').removeClass("hidden");
			
			$('#per').find('.person select:nth-child(n) option[value="2"]').addClass("hidden");
			if(flg!='mdFlg-click'){
				 $("#md-flg").attr("disabled", "disabled");
			}
		}
	} else {
		//卖上登录未完了 box选中 只能选择 担当者 
		if(md_flg) {
			$('#per').find('.person select:nth-child(n) option[value="3"]').addClass("hidden");
		} else {
			//否则都可选
			if(!mdReqPowerUp()){
			$('#per').find('.person select:nth-child(n) option[value="3"]').addClass("hidden");
			 $("#md-flg").attr("disabled", "disabled");
		   }else{
		   	$('#per').find('.person select:nth-child(n) option[value="3"]').removeClass("hidden");
		   }
		   
		}
	}

}
//删除卡片
function removeCard(ele,arrDelUser) {
	//如果是最后一个卡片不能删除
	var cardArr = $('#per').find('.person');
	if(cardArr.length == 1) {
		showErrorHandler("MUST_HAVE_CARD");
	} else {
		objStorage.delCard = arrDelUser;
		ele.offsetParent().remove();
	}

}
//选择责任者
function choseAdmin(ele) {
	//如果是最后一个卡片不能删除
	var cardArr = $('#per').find('.person input');
	//如果是md担当不允许作为责任者
	if(ele.closest(".person").find("select").val() != "2") {

		ele.prop("checked", false);
		showErrorHandler("MD_CANT_TO_ADMIN");
		return;
	}
	for(var i = 0; i < cardArr.length; i++) {
		cardArr.eq(i).prop("checked", false);
	}
	ele.prop("checked", "checked");
}
//md flg 点击
function mdBox(saleFinishFlg, ele) {
	cardLogic(saleFinishFlg,'mdFlg-click');
	var arrDelUser= objStorage.getDelCard();
	var isHaveMD = 0;
	$("#per").find('.person').each(function(index,element) {
		if($(element).find('select').val() == 3) {
			isHaveMD = 1;
			return;
		}
	});
	if(isHaveMD == 1) {
	var msg = showConfirmMsgHandler("DELETE_CHOSED_MD_CARD");
			var confirmTitle = $.getConfirmMsgTitle();
			$.messager.confirm(confirmTitle, msg, function(r) {
					if(r) {
						$("#per").find('.person').each(function(index, element) {
							if(saleFinishFlg==1){
								
								if($(element).find('select').val() == 3&&$(element).find(".sale-add").val()==1&&mdReqPowerUp()) {
								
								var ele = $(this);
								var usercd = $(this).find("div.usercode").text();
								var isFind = searchValue(objStorage.getCardMessage(),'usercd',usercd,"usercd");
								
								if(![].inArray(arrDelUser,usercd)&&isFind!=""&&usercd!=""){
										arrDelUser.push(usercd);
									}
								$(element).remove();
							}	
							}else{
									if($(element).find('select').val() == 3&&mdReqPowerUp()) {
								
								var ele = $(this);
								var usercd = $(this).find("div.usercode").text();
								var isFind = searchValue(objStorage.getCardMessage(),'usercd',usercd,"usercd");
								
								if(![].inArray(arrDelUser,usercd)&&isFind!=""&&usercd!=""){
										arrDelUser.push(usercd);
									}
								$(element).remove();
							}
							}
						
						});
						objStorage.delCard = arrDelUser;
//						cardLogic(saleFinishFlg);
					} else {

						$("#md-flg").prop("checked", false);
						cardLogic(saleFinishFlg,'mdFlg-click');
						
					}
				})
	}
	
}
//卡片选择担当者变化
function requestorChange(ele) {

	var isChecked = ele.closest(".person").find(".person-b input").prop("checked");
	if(isChecked) {
		if(ele.val() != "2") {
			ele.val(2);
			showErrorHandler("MD_CANT_TO_ADMIN");
			return;
		}
	}
}
//job_update 加遮罩层
function maskShow(flag){
	var mdqx = urlPars.parm("md");
	if(flag == undefined){
		flag = 0;
	}
	if(flag == 1){
		$('.mask.maskOne,.mask.maskThree').addClass('dn');
		$('.mask.maskTwo').removeClass('dn');
	}else if(flag == 0 && mdqx=="nomdfp"){
		$('.mask.maskOne,.mask.maskThree').removeClass('dn');
		$('.mask.maskTwo').addClass('dn');
	}else if(flag == 0){
		$('.mask.maskOne').removeClass('dn');
		$('.mask.maskTwo').addClass('dn');
		$('.mask.maskThree').addClass('dn');
		$('.job_state_z_index').addClass('control-zIndex');
	}else if(flag==2){
		$('.mask.maskOne,.mask.maskThree').addClass('dn');
		$('.mask.maskTwo').addClass('dn');
	}else if(flag==999){
		$('.mask.maskOne').removeClass('dn');
		$('.mask.maskTwo').addClass('hidden');
	}
}
function mdReqPowerUp(){
	
	var powerList = JSON.parse($.getNodeList());
	var flg = isHavePower(powerList,[13]);
	return flg;
}

function jobPowerUp(){
	
	var powerList = JSON.parse($.getNodeList());
	var flg = isHavePower(powerList, [9, 10, 11, 12]);
	return flg;
}
//是否是 企业推广 (トラフィック)
function enterprisePromotion(nodeList){
	var flg = false;
	for(var i = 0;i<nodeList.length;i++){
		if(nodeList[i].roleID==5 || nodeList[i].roleID==6){
			flg = true;
		}
		else{
			if(nodeList[i].roleID!=9 && nodeList[i].roleID!=10){
				flg = false;
			}
		}
	}
	return flg;
}
//是否有 5 或者  6 
function  enPromotion(nodeList){
	var flg = false;
	for(var i = 0;i<nodeList.length;i++){
		if(nodeList[i].roleID==5 || nodeList[i].roleID==6){
			flg = true;
		}
	}
	return flg;
}
