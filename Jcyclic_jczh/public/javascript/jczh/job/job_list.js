//事件添加
function initEvent(){
	$('#content').on('scroll', function() {
		//获取当前滚动栏scroll的高度并赋值
		var top = $('.datagrid-pager-top').offset().top - 50;
		var scrTop = $('#content').scrollTop();
		//开始判断如果导航栏距离顶部的高度等于当前滚动栏的高度则开启悬浮
		if (scrTop >= top) {
			$('.datagrid-pager-top').css({
				'position': 'fixed',
				'top': '112px',
				'z-index': '999999'
			});
		} else { //否则清空悬浮
			$('.datagrid-pager-top').css({
				'position': '',
				'top': ''
			});
		}
	})
	$("#setb1").on("click", function() {
		$(".ser").css("display", "none");
		$(".serb").css("display", "block");
	
	});
	$("#setb2").on("click", function() {
		$(".serb").css("display", "none");
		$(".ser").css("display", "block");
	
	});
	$("#jzd").on("click", function() {
		$("#zy").css("opacity", "0");
		$("#zy").css("position", "absolute");
		$("#zy").find("span.textbox.combo.datebox").find('input[type="text"]').val('');
		$("#zd").css("opacity", "1");
		$("#zd").css("position", "relative");
	});
	$("#jzy").on("click", function() {
		$("#zd").css("opacity", "0");
		$("#zd").css("position", "absolute");
		$("#zd").find("span.textbox.combo.datebox").find('input[type="text"]').val('');
		$("#zy").css("opacity", "1");
		$("#zy").css("position", "relative");
	});
	$(".gdt").scroll(function() {
		$(".datagrid-view2 .datagrid-body").scrollLeft($(this).scrollLeft()); // 横向滚动条
	});
}

$(function() {
	//init input 日期
	var buttons = $.extend([], $.fn.datebox.defaults.buttons);  
    buttons.splice(1, 0, {  
	    text: '清空',  
	    handler : function(target) {  
	        $(target).datebox("setValue", ""); // 设置空值  
	        $(target).datebox("hidePanel"); // 点击清空按钮之后关闭日期选择面板  
	    }  
	});  
	$.fn.datebox.defaults.buttons = buttons; 
	$('#ddda').datebox({});
	//$('#d1a').datebox({});
	var tds = false;
	$("#ddd").datebox({
		onShowPanel: function() {
			var p = $('#ddd').datebox('panel');
			p.find('span.calendar-text').trigger('click');
			p.find(".datebox-button-a").eq(0).hide();
			var span = p.find('span.calendar-text');
			//屏蔽选择今天的按钮  
			p.find('.calendar-text').hide();
			//输入框原本可填，会触发事件，屏蔽掉  
			p.find('.calendar-menu-year').attr("readonly", "readonly");
			if (!tds) //初始化只需要捆绑一次事件就够了  
			setTimeout(function() {
				tds = p.find('div.calendar-menu-month-inner td');
				tds.click(function(e) {
					e.stopPropagation(); //禁止冒泡执行easyui给月份绑定的事件  
					var year = /\d{4}/.exec(span.html())[0] //得到年份  
					month = parseInt($(this).attr('abbr'), 10); //月份  
					$('#ddd').datebox('hidePanel').datebox('setValue', year + '-' + month + '-' + '01'); //设置日期的值  
				});
			});
		},
		formatter: function(d) { //设置格式  
			return d.getFullYear() + '-' + (d.getMonth() < 9 ? '0' + (d.getMonth() + 1) : (d.getMonth() + 1));
		}
	});
	var tds1 = false;
	$("#d1").datebox({
		onShowPanel: function() {
			var p = $('#d1').datebox('panel');
			p.find('span.calendar-text').trigger('click');
			p.find(".datebox-button-a").eq(0).hide();
			var span = p.find('span.calendar-text');
			//屏蔽选择今天的按钮  
			p.find('.calendar-text').hide();
			//输入框原本可填，会触发事件，屏蔽掉  
			p.find('.calendar-menu-year').attr("readonly", "readonly");
			if (!tds1) //初始化只需要捆绑一次事件就够了  
			setTimeout(function() {
				tds1 = p.find('div.calendar-menu-month-inner td');
				tds1.click(function(e) {
					e.stopPropagation(); //禁止冒泡执行easyui给月份绑定的事件  
					var year = /\d{4}/.exec(span.html())[0] //得到年份  
					month = parseInt($(this).attr('abbr'), 10); //月份  
					$('#d1').datebox('hidePanel').datebox('setValue', year + '-' + month + '-' + '01'); //设置日期的值  
				});
			});
		},
		formatter: function(d) { //设置格式  
			return d.getFullYear() + '-' + (d.getMonth() < 9 ? '0' + (d.getMonth() + 1) : (d.getMonth() + 1));
		}
	});
	//首先获取导航栏距离浏览器顶部的高度
	var top = $('.datagrid-pager-top').offset().top;
	//开始监控滚动栏scroll
	$(document).scroll(function() {
		//获取当前滚动栏scroll的高度并赋值
		var scrTop = $(window).scrollTop();
		//开始判断如果导航栏距离顶部的高度等于当前滚动栏的高度则开启悬浮
		if (scrTop >= top) {
			$('.datagrid-pager-top').css({
				'position': 'fixed',
				'top': '0',
				'width': '100%'
			});
		} else { //否则清空悬浮
			$('.datagrid-pager-top').css({
				'position': '',
				'top': ''
			});
		}
	})
	//input text计上月框不可编辑
	$("#zy,#zd").find("span.textbox.combo.datebox").find('input[type="text"]').attr('disabled',true);
	$("#zy,#zd").find("span.textbox.combo.datebox").find('input[type="text"]').css('background-color','#ffffff');
	//事件初始化
	initEvent();
})
