<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%@ taglib prefix="dwz" uri="http://www./dwz"%>

<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">	
<head>
	<title></title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="initial-scale=1,user-scalable=no,maximum-scale=1,width=device-width" />
	<style type="text/css" media="screen">
		html, body	{ height:100%; }
		body { margin:0; padding:0; overflow:auto; }
		#flashContent { display:none; }
	</style>
	
	<link rel="stylesheet" type="text/css" href="${contextPath }/js/FlexPaper/css/flexpaper.css" />
	<script type="text/javascript" src="${contextPath }/js/FlexPaper/js/jquery.min.js"></script>
	<script type="text/javascript" src="${contextPath }/js/FlexPaper/js/jquery.extensions.min.js"></script>
	<script type="text/javascript" src="${contextPath }/js/FlexPaper/js/flexpaper.js"></script>
	<script type="text/javascript" src="${contextPath }/js/FlexPaper/js/flexpaper_handlers.js"></script>
</head>
<body>
	<div id="documentViewer" class="flexpaper_viewer" style="position:absolute;left:10px;top:10px;width:100%;height:100%;"></div>
	<script type="text/javascript">   
		$('#documentViewer').FlexPaperViewer( {
			config : {
				DOC : escape("{${contextPath }/uploaddatamanage/document/read/${resource.id}/{format}/[*,0],${numPages}}"),
				Scale : 1.0, 
				ZoomTransition : 'easeOut',
				ZoomTime : 0.5,
				ZoomInterval : 0.1,
				FitPageOnLoad : false,
				FitWidthOnLoad : true,
				FullScreenAsMaxWindow : false,
				ProgressiveLoading : false,
				MinZoomSize : 0.2,
				MaxZoomSize : 5,
				SearchMatchAll : false,
				SearchServiceUrl : '',
				RenderingOrder : "swf,swf",
				ViewModeToolsVisible : false,
				ZoomToolsVisible : true,
				NavToolsVisible : true,
				CursorToolsVisible : false,
				SearchToolsVisible : false,
				key : "$592b0e9424cda8c419f",
				DocSizeQueryService : "",
				jsDirectory : '${contextPath }/js/FlexPaper/js/',
				localeDirectory : '${contextPath }/js/FlexPaper/locale/',
				JSONDataType : 'jsonp',
				WMode : 'window',
				localeChain: 'zh_CN'
			}}
		);
	</script>
</body>
</html>
