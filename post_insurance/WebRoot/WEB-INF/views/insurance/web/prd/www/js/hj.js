//js that dealwith iframe

var $frame =  null;
if(parent.document) $frame = $(parent.document).find('#mainframe' );
$(window).load(function(){
	adaptHeight( $frame ); //frame height adapt to its content
});

$(function() {		
	//frame height adapt to its content when show example img	
	$(".toggleImgButton").click(function(){
		$("img").toggle();
		var frame =  $(parent.document).find('#mainframe');		
		adaptHeight( frame ); 
	});		
});	

function adaptHeight($frame){
	var h=$(document).height() + 6;
	$frame.height(h);
}