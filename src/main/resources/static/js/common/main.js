var ctx = "";
$(document).ready(function(){
	ctx = $('#ctx').val();
	
	$(".menu_level1 li").click(function () {
		$("li[class='active']").removeAttr("class");
		$(this).addClass("active");
		$("#main_iframe").attr("src",$(this).attr('no'));
	});

});
