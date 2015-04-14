(function($){
    $.fn.capacityFixed = function(options) {
        var opts = $.extend({},options);
        var FixedFun = function(element) {
            var top = $(element).offset().top;
            element.css({
                position: "absolute"
            });
//            $(window).scroll(function() {
//                var scrolls = $(this).scrollTop();
//                if (scrolls > top) {
//                    if (window.XMLHttpRequest) {
//                        element.css({
//                            position: "fixed",
//                            top: 0							
//                        });
//                    } else {
//                        element.css({
//                            top: scrolls
//                        });
//                    }
//                }else {
//                    element.css({
//                        position: "absolute",
//                        top: top
//                    });
//                }
//            });
            element.find(".close-ico").click(function(event){
                element.remove();
                event.preventDefault();
            })
        };
        return $(this).each(function() {
            FixedFun($(this));
        });
    };
})(jQuery);