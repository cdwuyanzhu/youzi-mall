jQuery(document).ready(function($) {
	//打开窗口
	$('.btn').on('click', function(event) {
		event.preventDefault();
		$('.cd-popup').addClass('is-visible');
		//$(".dialog-addquxiao").hide()
	});
	//关闭窗口
	$('.cd-popup').on('click', function(event) {
		if ($(event.target).is('.icon-guanbi') || $(event.target).is('.cd-popup')) {
			event.preventDefault();
			$(this).removeClass('is-visible');
		}
	});
	//ESC关闭
	$(document).keyup(function(event) {
		if (event.which == '27') {
			$('.cd-popup').removeClass('is-visible');
		}
	});
})
