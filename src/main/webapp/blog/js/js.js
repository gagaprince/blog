function showHideTop(){
	var h = $(window).height();
	var t = $(document).scrollTop();
	if(t > h){
		$('#gotop').show();
	}else{
		$('#gotop').hide();
	}
}
$(document).ready(function(e) {
	showHideTop();
	$('#gotop').click(function(){
		$(document).scrollTop(0);	
	});
	$(window).scroll(function(e){
		showHideTop();
	});
	$("#showQR").on("mouseover",function(){
		$("#myQRFrame").addClass("qrshow");
	});
	$("#showQR").on("mouseout",function(){
		$("#myQRFrame").removeClass("qrshow");
	});

});


