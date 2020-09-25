<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<script type="text/javascript">
<!--
function customAjaxDone(json){
	//alert(json.statusCode);
    if (json.statusCode == DWZ.statusCode.ok){
    	DWZ.ajaxDone(json);
    	dialogReloadNavTab(json);
    	$.pdialog.closeCurrent(); 
    }
    else{
        DWZ.ajaxDone(json);
    }
}

var d = new Date();
var yy = d.getFullYear();
var mm = d.getMonth()+1;
var dd = d.getDate();
var mindate =  yy+"-"+mm+"-"+dd;
var d2 = new Date();
d2.setDate(d.getDate() + 15);
var maxdate = date.getFullYear() +"-"+ (date.getMonth()+1) +"-"+ date.getDate();

//-->
</script>
<div class="pageContent">
<form method="post" action="${contextPath }/lpgl/task/HXDay" class="required-validate pageForm" onsubmit="return iframeCallback(this, customAjaxDone);">
	<input type="hidden" name="id" value="${task.id }">
	<div class="pageFormContent" layouth="58">
		<p>
			<label>任务核心发起日期：</label>
			<input type="text" name="sendDate" class="date" dateFmt="yyyy-MM-dd" value="<fmt:formatDate value="${task.hxDate }" pattern="yyyy-MM-dd"/>"/>
				<a class="inputDateButton" href="javascript:;">选择</a>
		</p>
	</div>
	<div class="formBar">
		<ul>
			<li><div class="button"><div class="buttonContent"><button type="submit">提交确定</button></div></div></li>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close" >关闭</button></div></div></li>
		</ul>
	</div>
</form>
</div>