$(document).ready(function(){
	$("#save").click(function(){
		var advance_title = $(".advancetitle").val();
		var advance_url = $(".advanceurl").val();
		if(advance_title == '' || advance_url == '') {
			if(advance_title == '') {
				$(".advancetitle").css("border-color", "red");
				validate($(".advancetitle"),part_language_change_new('NODATE'));
			} else {
				$(".advancetitle").css('border-color', "#cccccc");
				$(".advancetitle").tooltip('destroy');
			}
			if(advance_url == '') {
				$(".advanceurl").css("border-color", "red");
				validate($(".advanceurl"),part_language_change_new('NODATE'));
			} else {
				$(".advanceurl").css('border-color', "#cccccc");
				$(".advanceurl").tooltip('destroy');
			}
		}
	})
})
