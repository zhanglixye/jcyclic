$(".switch").css("height", '130px');
$(".switchot").css("height", '130px');
$(".switch_off").css("height", '130px');
$(".switchot_off").css("height", '130px');
$('input[type=checkbox].boostrap-switch').bootstrapSwitch({
	onSwitchChange: function() {
		$(".switchot").css("height", "345px");
		$(".switch").css("height", "345px");
		$(".switchot_off").css("height", "345px");
		$(".switch_off").css("height", "345px");
		var state1 = $('.judge1').bootstrapSwitch('state');
		var state2 = $('.judge2').bootstrapSwitch('state');
		if(state1 == false && state2 == false) {
			$(".switch").css("height", '130px');
			$(".switchot").css("height", '130px');
			$(".switchot_off").css("height", "130px");
			$(".switch_off").css("height", "130px");
		}
	}
});
