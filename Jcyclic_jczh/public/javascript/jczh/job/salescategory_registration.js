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
	//预定,売上点击切换效果
	$('.sale_head').on('click',function(e){
		var data_flag = $(e.target).closest('label').find('input').val();
		//防止两次点击相同active
		if($(e.target).closest('.active').exist()){
			return false;
		}
		if(data_flag == 1){
			$(this).parents('.tollage').siblings('.price_on,.business').toggleClass('hidden');
			$(this).parents('.tollage').siblings('.price,.business_off').toggleClass('hidden');
		}
		if(data_flag == 0){
			$(this).parents('.tollage').siblings('.price_on,.business').toggleClass('hidden');
			$(this).parents('.tollage').siblings('.price,.business_off').toggleClass('hidden');
		}	
	});
	
})
$(function() {
	//初始化原价隐藏变色
	$('.orderPan').find('.form-group').addClass('hidden');
	$('.orderPan').css('background-color','#e8e8e8');
	$('.oder_bo').css('background-image','url(../public/images/triangle_gray.png)');
	//日期框datebox初始化,设定宽度为70%
	$("input[name='dateD'],input[name='dateR']").datebox({});
	//初始化共同画面隐藏
	$('.price,.business_off').addClass('hidden');
	$("input[name='plan_sale_use_date'],input[name='plan_cost_use_date']").datebox();
	$.foreignGoodsShow();

	$.jobCommon();
	$.layerShowDiv('setb2','29%','auto',1,$('#setbig1'));
	//增值税编辑
	$.layerShowDiv('add_tax_bus', 'auto', 'auto', 1, $('.add_tax_edit'));
	//$.layerShowDiv('add_tax_price_on', 'auto', 'auto', 1, $('.add_tax_edit'));	
	initShow();
//	var retention_digit = parseInt($.getDefaultPoint());//売上保留小数
//
//	bus_price_common(retention_digit,3);

		$(".model_first").click(function(e) {
			e.stopPropagation();
		$(".business_off").addClass("hidden");
		
		$(".business").removeClass("hidden");
		$(".model_second").removeClass("active");
		$(".model_first").addClass("active");	
		//state表示on/off状态
		var state1 = $('.judge1').bootstrapSwitch('state');
		var state2 = $('.judge4').bootstrapSwitch('state');
		var state3 = $('.judge2').bootstrapSwitch('state');
		var state4 = $('.judge3').bootstrapSwitch('state');
//		console.log(state1, state2, state3, state4)
		//tip=false表示当前点击状态
		var tip1 = $(".judge1").closest('.active').hasClass("hidden");
		var tip2 = $(".judge4").closest('.active').hasClass("hidden");
		var tip3 = $(".judge2").closest('.active').hasClass("hidden");
		var tip4 = $(".judge3").closest('.active').hasClass("hidden");
		saleStorage.sale_tax = $('.plansale_tax').text();
		saleStorage.sale_foreign_amt = $('.plan_sale_foreign_amt').text(); 
		saleStorage.req_amt = $('.plan_req_amt').text();
		saleStorage.isRecalculate=1;
		//initCal("-1")
		calculationSale()
//		var first ="Turnover"
//		upRateArea(first);
		if(tip1 == false) {
			if(tip3 == false) {
				if(state1 == true || state3 == true) {
					$(".switch").css("height", "345px");
					$(".switchot").css("height", "345px");
				} else if(state1 == false && state3 == false) {
					$(".switch").css("height", "130px");
					$(".switchot").css("height", "130px");
				}
			} else {
				if(state1 == true || state4 == true) {
					$(".switch").css("height", "345px");
					$(".switchot_off").css("height", "345px");
				} else if(state1 == false && state4 == false) {
					$(".switch").css("height", "130px");
					$(".switchot_off").css("height", "130px");
				}
			}
		}
		saleStorage.isRecalculate = 0;
		return false;
	})
//	$(".model_first").click(function(e) {
//		 upRateArea();
//		 return false;
//	})
	$(".model_second").click(function(e) {
		e.stopPropagation();
		$(".business").addClass("hidden");
		$(".business_off").removeClass("hidden");
		$(".model_first").removeClass("active");
		$(".model_second").addClass("active");	
//		 $('label.btn.btn-primary.activeT').siblings('label').css('display','none');
//         $('label.btn.btn-primary').removeClass('activeT');
		//state表示on/off状态
		var state1 = $('.judge1').bootstrapSwitch('state');
		var state2 = $('.judge4').bootstrapSwitch('state');
		var state3 = $('.judge2').bootstrapSwitch('state');
		var state4 = $('.judge3').bootstrapSwitch('state');
		//initCal("-1")
		calculationSale()
//		console.log(state1, state2, state3, state4)
		//tip=false表示当前点击状态
		var tip1 = $(".judge1").closest('.active').hasClass("hidden");
		var tip2 = $(".judge4").closest('.active').hasClass("hidden");
		var tip3 = $(".judge2").closest('.active').hasClass("hidden");
		var tip4 = $(".judge3").closest('.active').hasClass("hidden");
//		console.log(tip1, tip2, tip3, tip4)
//		var second = "Estimate";
//		upRateArea(second);
		if(tip2 == false) {
			if(tip3 == false) {
				if(state2 == true || state3 == true) {
					$(".switch_off").css("height", "345px");
					$(".switchot").css("height", "345px");
				} else if(state2 == false && state3 == false) {
					$(".switch_off").css("height", "130px");
					$(".switchot").css("height", "130px");
				}
			} else {
				if(state2 == true || state4 == true) {
					$(".switch_off").css("height", "345px");
					$(".switchot_off").css("height", "345px");
				} else if(state2 == false || state4 == false) {
					$(".switch_off").css("height", "130px");
					$(".switchot_off").css("height", "130px");
				}
			}
		}
	})
	
	$(".model_thired").click(function(e) {
		e.stopPropagation();
		$(".price").addClass("hidden");
		$(".price_on").removeClass("hidden");
		$(".model_thired").addClass("active");
		$(".model_fourth").removeClass("active");	
		//state表示on/off状态
		var state1 = $('.judge1').bootstrapSwitch('state');
		var state2 = $('.judge4').bootstrapSwitch('state');
		var state3 = $('.judge2').bootstrapSwitch('state');
		var state4 = $('.judge3').bootstrapSwitch('state');
//		console.log(state1, state2, state3, state4)
		//tip=false表示当前点击状态
		var tip1 = $(".judge1").closest('.active').hasClass("hidden");
		var tip2 = $(".judge4").closest('.active').hasClass("hidden");
		var tip3 = $(".judge2").closest('.active').hasClass("hidden");
		var tip4 = $(".judge3").closest('.active').hasClass("hidden");
		saleStorage.sale_tax = $('.plansale_tax').text();
		saleStorage.sale_foreign_amt = $('.plan_sale_foreign_amt').text(); 
		saleStorage.req_amt = $('.plan_req_amt').text();
		saleStorage.isRecalculate=1;
		//initCal("-1")
		calculationSale()
//		console.log(tip1, tip2, tip3, tip4)
//		var second = "cost";
//		upRateArea(second);
		if(tip3 == false) {
			if(tip1 == false) {
				if(state1 == true || state3 == true) {
					$(".switch").css("height", "345px");
					$(".switchot").css("height", "345px");
				} else if(state1 == false && state3 == false) {
					$(".switch").css("height", "130px");
					$(".switchot").css("height", "130px");
				}
			} else {
				if(state2 == true || state3 == true) {
					$(".switch_off").css("height", "345px");
					$(".switchot").css("height", "345px");
				} else if(state2 == false || state3 == false) {
					$(".switch_off").css("height", "130px");
					$(".switchot").css("height", "130px");
				}
			}
		}
		saleStorage.isRecalculate = 0;
		return false;
	})
	
	$(".model_fourth").click(function(e) {
		e.stopPropagation();
		$(".price_on").addClass("hidden");
		$(".price").removeClass("hidden");
		$(".model_thired").removeClass("active");
		$(".model_fourth").addClass("active");	
		//state表示on/off状态
		var state1 = $('.judge1').bootstrapSwitch('state');
		var state2 = $('.judge4').bootstrapSwitch('state');
		var state3 = $('.judge2').bootstrapSwitch('state');
		var state4 = $('.judge3').bootstrapSwitch('state');
//		console.log(state1, state2, state3, state4)
		//tip=false表示当前点击状态
		var tip1 = $(".judge1").closest('.active').hasClass("hidden");
		var tip2 = $(".judge4").closest('.active').hasClass("hidden");
		var tip3 = $(".judge2").closest('.active').hasClass("hidden");
		var tip4 = $(".judge3").closest('.active').hasClass("hidden");
		//initCal("-1")
		saleStorage.sale_tax = $('.plansale_tax').text();
		saleStorage.sale_foreign_amt = $('.plan_sale_foreign_amt').text(); 
		saleStorage.req_amt = $('.plan_req_amt').text();
		saleStorage.isRecalculate=1;
		calculationSale()
//		console.log(tip1, tip2, tip3, tip4)
//		var second = "costEstimate";
//		upRateArea(second);
		if(tip4 == false) {
			if(tip1 == false) {
				if(state1 == true || state4 == true) {
					$(".switch").css("height", "345px");
					$(".switchot_off").css("height", "345px");
				} else if(state1 == false && state4 == false) {
					$(".switch").css("height", "130px");
					$(".switchot_off").css("height", "130px");
				}
			} else {
				if(state2 == true || state4 == true) {
					$(".switch_off").css("height", "345px");
					$(".switchot_off").css("height", "345px");
				} else if(state2 == false || state4 == false) {
					$(".switch_off").css("height", "130px");
					$(".switchot_off").css("height", "130px");
				}
			}
		}
		saleStorage.isRecalculate = 0;
		return false;
	})
	

})

saleStorage= {
	 sale_tax : "",//卖上增值税
	 sale_foreign_amt:"",//卖上金额
	 req_amt:"",//卖上请求金额
	 isRecalculate:0,
}
