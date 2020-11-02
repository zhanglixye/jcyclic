$(document).ready(function() {
	$(".switch").css("height", '130px');
	$(".switchot").css("height", '130px');
	$('input[type=checkbox].boostrap-switch').bootstrapSwitch({
		onSwitchChange: function() {
			$(".switchot").css("height", "345px");
			$(".switch").css("height", "345px");
			var state1 = $('.judge1').bootstrapSwitch('state');
			var state2 = $('.judge2').bootstrapSwitch('state');
			if(state1 == false && state2 == false) {
				$(".switch").css("height", '130px');
				$(".switchot").css("height", '130px');
			}
		}
	});
	onResize_102();
	window.onresize = function(e) {
		onResize_102();
	}

	function onResize_102() {
		var width = document.documentElement.clientWidth;
		if(width < 1590) {
			$('.bt_up_102').closest('.par_ent').css('width', '117%');
			$('.bt_up_102').eq(0).css({
				'margin-left': '0',
				'margin-right': '5px'
			});
		} else {
			$('.bt_up_102').closest('.par_ent').css('width', '102%');
			$('.bt_up_102').eq(0).css({
				'margin-left': '5px',
				'margin-right': '0'
			});
		}
	}
})
$(function() {
	bodyh = $(document).height(); //浏览器当前窗口文档的高度
	navh = bodyh - 135;
	initShow();
	$("#navright").css("height", navh + "px");
	/////////////////////////////////////////
	//按需设置  numnbox保留小数的问题
	var retention_digit = parseInt($.getDefaultPoint()); //売上保留小数
	var retention_digit_p = parseInt($.getDefaultPoint()); //原价保留小数

	bus_price_common(retention_digit, retention_digit_p);
	/////////////////////////////////////////
	$("#set").on("click", function() {
		set = $(".inputWrap").html();
		$("#setnig2").show();
		$("#setblock").html(set);
		var index = layer.index;
		layer.close(index)
	});

	$("#setbigclose").on("click", function() {
		//$("#setbig1").css("display", "none");
		$("#setnig2").css("display", "none");
		$("#setb1").button('toggle')
	});
	$("#setbigclose1").on("click", function() {
		//$("#setbig1").css("display", "none");
		$("#setnig2").css("display", "none");
		$("#setb1").button('toggle')
	});
	//删除卡片
	$("#per").on("click", ".close", function() {
		var ele = $(this);
		removeCard(ele);
	});
	//卡片下拉变化
	$("#per").on("change", "select", function() {
		var ele = $(this);
		cardChoseChange(ele);
	});
	//点击责任者
	$("#per").on("click", ".level_flg", function() {
		var ele = $(this);
		dissAdmin(ele);
	});

	//添加卡片
	$(".addp").on("click", function() {
		var ele = $(this);
		addCard(ele);
	});
	/*
	 *md分配点击事件  切换是否下来中有md担当者
	 * 
	 */
	$("#md-flg").click(function() {
		var ele = $(this);
		toggleMD(ele);
	});

	$("#setblock").on("click", ".close", function() {
		var ele = $(this);
		removeLable(ele);
	});
	$.foreignGoodsShow();
	$('#scheduled-date').datebox({});
	$('#common-day').datebox({});
	$('#common-dayT').datebox({});
	sessionStorage.clear();

	//弹出label框调用
	$.layerShowDiv('setb2', '29%', 'auto', 1, $('#setbig1'));
	//增值税编辑
	$.layerShowDiv('add_tax_bus', 'auto', 'auto', 1, $('.add_tax_edit'));
	$.layerShowDiv('add_tax_price_on', 'auto', 'auto', 1, $('.add_tax_price'));
	var url = $.getJumpPath() + 'common/suppliers_retrieval.html';
	$.layerShow("searchClient", url);
	$.layerShow("searchHDY", url);
	$.layerShow("searchPay", url);
	var url_user = $.getJumpPath() + 'common/employ_retrieval.html';
	$.layerShow("jobCreat-user-1", url_user);
	
	/*
	 * 在跳过的情况下 初始化job登录页面 是默认选中的
	 * 获取md分配box是否选中，判断是否有md分配选项
	 * 
	 */
	var md_flg = $("#md-flg").prop("checked");
	if(md_flg) {
		$('#per').find('.person select:nth-child(n) option[value="3"]').addClass("hidden");
	} else {
		if(!mdReqPower()) {
			$('#per').find('.person select:nth-child(n) option[value="3"]').addClass("hidden");
		} else {
			$('#per').find('.person select:nth-child(n) option[value="3"]').removeClass("hidden");
		}
	}
	clearCard("jobCreat-select-1","jobCreat-usercd-1","jobCreat-username-1","jobCreat-userlevel-1");
});
//是否应该有MD选项
function toggleMD(ele) {
	var md_flg = $("#md-flg").prop("checked");
	var isHaveMD = 0;
	$("#per").find('.person').each(function(index, element) {
		if($(element).find('select').val() == 3) {
			isHaveMD = 1;
			return;
		}
	});
	if(md_flg) {
		$('#per').find('.person select:nth-child(n) option[value="3"]').addClass("hidden");
		if(isHaveMD == 1) {
			var msg = showConfirmMsgHandler("DELETE_CHOSED_MD_CARD");
			var confirmTitle = $.getConfirmMsgTitle();
			$.messager.confirm(confirmTitle, msg, function(r) {
					if(r) {
						$("#per").find('.person').each(function(index, element) {
							if($(element).find('select').val() == 3) {
								$(element).remove();
							}
						})
					} else {

						$("#md-flg").prop("checked", false);
					}
				})
			}
		}
			else {
				if(!mdReqPower()) {
					$('#per').find('.person select:nth-child(n) option[value="3"]').addClass("hidden");
				} else {
					$('#per').find('.person select:nth-child(n) option[value="3"]').removeClass("hidden");
				}
			}
		}
		//添加卡片
		function addCard(ele) {
			var personList = $("#per").find('.person');
			if(personList.length >= 15) {
			   var index = layer.index;
		       layer.close(index);
				showErrorHandler("MAX_CARD_15", 'info', 'info');
				return;
			}
			var beforeID = ele.prev().find('i').eq(1).attr('id');
			if(beforeID == undefined) {
				var num = 0;
			} else {
				var num = parseInt(beforeID.substring(14));
			}
			var iid = "jobCreat-user-" + (num + 1);
			var levelid = "jobCreat-userlevel-" + (num + 1);
			var userid = "jobCreat-usercd-" + (num + 1);
			var username = "jobCreat-username-" + (num + 1);
			var selectID = "jobCreat-select-" + (num + 1);
			var colorid = "person-color-" + (num + 1);
			var selectVal = sessionStorage.getItem("nf");
//			if(selectVal!=null&&selectVal!=''&&selectVal!==undefined){
//			}
			ele.before('<div class="col-md-4 panel person" style="padding:5px;margin-bottom:0;width:225px;background:#fff"><div class="person-t"><div class="person-t-l"><select name="boostrap-select " class="form-control" id="'+selectID+'"><option class="i18n" name="jobregistration_business_ren" value="2" ></option><option class="i18n" name="jobregistration_bear_ren" value="3" ></option></select></div><div class="person-t-r"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button></div></div><div class="person-m"><div class="person-m-l"><i class="iconfont icon-ren" id="'+colorid+'"></i></div><div class="person-m-m"><div name="userlevel" id="' + levelid + '"></div><div class="usercd" id="' + userid + '"></div><div class="username" id="' + username + '"></div></div><div class="person-m-r"><i class="iconfont icon-sousuo" id="' + iid + '" ></i></div></div><div class="person-b"><label><input type="checkbox" class="level_flg" id="inlineCheckbox44" value="option4"> <span class="iconfont icon-duihaok"></span><span class="i18n" name="jobregistration_person_in_charge"></span></label></div></div>');
            
			var domI = ele.prev().find('i').eq(1);
 		    $("#"+domI.attr('id')).parents(".person").find("select").val(selectVal);
			$.layerShow(domI.attr('id'), $.getJumpPath() + 'common/employ_retrieval.html');
            clearCard(selectID,userid,username,levelid);
			var md_flg = $("#md-flg").prop("checked");

			/*
			 * 添加完卡片执行 判断一下是否有md分配  
			 * （有可能 md 分配是先选中的，
			 * 这种情况只在开始判断 新加的卡片无效，所以要在卡片新加后判断一下）
			 */
			if(md_flg) {
				$('#per').find('.person select:nth-child(n) option[value="3"]').addClass("hidden");
			} else {
				if(!mdReqPower()) {
					$('#per').find('.person select:nth-child(n) option[value="3"]').addClass("hidden");
				} else {
					$('#per').find('.person select:nth-child(n) option[value="3"]').removeClass("hidden");
				}

			}
			//加载语言翻译
	 		var insertEle = $(".i18n");
			insertEle.each(function() {
		    // 根据i18n元素的 name 获取内容写入
		        $(this).html(part_language_change_new($(this).attr('name')));
		    });

		}
		//点击责任者
		function dissAdmin(ele) {
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
		//卡片下拉变化
		function cardChoseChange(ele) {
			var isChecked = ele.closest(".person").find(".person-b input").prop("checked");
			if(isChecked) {
				if(ele.val() != "2") {
					ele.val(2);
					showErrorHandler("MD_CANT_TO_ADMIN");
					return;
				}
			}
		}
		//移除卡片
		function removeCard(ele) {
			//如果是最后一个卡片不能删除
			var cardArr = $('#per').find('.person');
			if(cardArr.length == 1) {
				showErrorHandler("MUST_HAVE_CARD");
			} else {
				ele.offsetParent().remove();
			}

		}
		//移除标签
		function removeLable(ele) {
			var lid = ele.prev('span').attr("value");
			var mySelectVal = $(".mySelect-option").children();
			for(var i = 0; i < mySelectVal.length; i++) {
				if(mySelectVal.eq(i).attr("data-value") == lid) {
					mySelectVal.eq(i).click();
				}
			}
			ele.parent().remove();
		}

		function mdReqPower() {

			var powerList = JSON.parse($.getNodeList());
			var flg = isHavePower(powerList, [4]);
			return flg;
		}
		