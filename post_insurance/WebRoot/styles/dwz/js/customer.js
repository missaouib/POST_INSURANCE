/**
 * 得到当前活动的navtab的局部区域
 * @returns
 */
function getCurrentNavtabRel(){
	var $pDiv = $('.tabsPage div[class="page unitBox"][style*="block"]').first();
	var $ub = $("div.unitBox", $pDiv);
	if ($ub.length > 0) {
		return $ub.first();
	}
	return $pDiv;
}

navTab.getCurrentNavTab = function () {
    return navTab._getTabs().eq(navTab._currentIndex);
};

/**
 * 自动刷新当前活动的navTab
 * @param json
 */
function dialogReloadNavTab(json) {
    DWZ.ajaxDone(json);
    var tabId = navTab.getCurrentNavTab().attr("tabid");
    if (json.statusCode == DWZ.statusCode.ok) {
        if (json.navTabId || tabId != null) {
            navTab.reload(json.forwardUrl, { navTabId: json.navTabId });
        } else if (json.rel) {
        var $pagerForm = $("#pagerForm", navTab.getCurrentPanel());
        var args = $pagerForm.size() > 0 ? $pagerForm.serializeArray() : {}
        navTabPageBreak(args, json.rel);
    }
		if ("closeCurrent" == json.callbackType) {
			$.pdialog.closeCurrent();
		}
	}
}

/**
 * 自动刷新当前活动的navTab的局部区域
 * @param json
 */
function dialogReloadRel(json) {
    DWZ.ajaxDone(json);
	if (json.statusCode == DWZ.statusCode.ok) {
	    var rel = getCurrentNavtabRel().attr("id");
	    var $pagerForm = $("#pagerForm", navTab.getCurrentPanel());
	    var args = $pagerForm.size() > 0 ? $pagerForm.serializeArray() : {}
	    navTabPageBreak(args, rel);

		if ("closeCurrent" == json.callbackType) {
			$.pdialog.closeCurrent();
		}
	}
}

/**
 * 根据rel自动局部刷新
 * @param json
 */
function reloadRel(json){
	DWZ.ajaxDone(json);
	if (json.statusCode == DWZ.statusCode.ok){
		if (json.navTabId){ //把指定navTab页面标记为需要“重新载入”。注意navTabId不能是当前navTab页面的
			navTab.reloadFlag(json.navTabId);
		} else { //重新载入当前navTab页面
			var $pagerForm = $("#pagerForm", navTab.getCurrentPanel());
			var args = $pagerForm.size()>0 ? $pagerForm.serializeArray() : {}
			if (json.rel != null && json.rel!="") {
				navTabPageBreak(args, json.rel);
			} else {
				var rel = getCurrentNavtabRel().attr("id");
				navTabPageBreak(args, rel);
			}
		}
		
		if ("closeCurrent" == json.callbackType) {
			setTimeout(function(){navTab.closeCurrentTab(json.navTabId);}, 100);
		} else if ("forward" == json.callbackType) {
			navTab.reload(json.forwardUrl);
		} else if ("forwardConfirm" == json.callbackType) {
			alertMsg.confirm(json.confirmMsg || DWZ.msg("forwardConfirmMsg"), {
				okCall: function(){
					navTab.reload(json.forwardUrl);
				}
			});
		} else {
			navTab.getCurrentPanel().find(":input[initValue]").each(function(){
				var initVal = $(this).attr("initValue");
				$(this).val(initVal);
			});
		}
	}
	
}

/**
 * 根据id自动局部刷新，用于organization页面
 * @param json
 */
function dialogReloadRel2Org(json){
	if (json.statusCode == DWZ.statusCode.ok) {
		$("#refreshJbsxBox2organizationTree").click();
	}
	
	dialogReloadRel(json);
}

/**
 * 根据id自动局部刷新，用于module页面
 * @param json
 */
function dialogReloadRel2Module(json){
	if (json.statusCode == DWZ.statusCode.ok) {
		$("#refreshJbsxBox2moduleTree").click();
	}
	
	dialogReloadRel(json);
}

/**
 * 根据父pTabid刷新navTab
 * @param json
 */
function navTabReloadParent(json){
	var pTabid = navTab.getCurrentNavTab().attr("pTabid");
	if (json.navTabId == "") {
		json.navTabId = pTabid;
	}
	navTabAjaxDone(json);
}

DWZ.regPlugins.push(function ($p) {
    var $a = $("a[iconClass]");
    $a.each(function () {
        var imageName = $(this).attr("iconClass");
        var $span = $("span", this);
        if ($span.length == 0) {
            // 用作<td>中的<a>
            $(this).css({
                "background": "url(../styles/dwz/themes/css/images/icons/" + imageName + ".png) no-repeat",
                "background-position": "50% 50%",
                "width": "22px",
                "height": "20px",
                "text-indent": "-1000px",
                "overflow": "hidden",
                "display": "block",
                "float": "left"
            });
        } else {
            // 用作panelBar toolBar中的<span>
            $span.css({
                "background-image": "url(../styles/dwz/themes/css/images/icons/" + imageName + ".png)",
                "background-position": "0 3px"
            });
        }
    });
});
