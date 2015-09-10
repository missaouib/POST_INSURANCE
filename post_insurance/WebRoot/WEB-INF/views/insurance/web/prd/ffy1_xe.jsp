<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<script type="text/javascript">
<!--
$("#background,#progressBar").hide();
//-->
</script>

<div class="pageHeader">
	<form id="pagerForm" rel="pagerForm" method="post" action="/web/prd/ffy1/xe" onsubmit="return dialogSearch(this);">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>年龄</label>
				<input type="text" name="old" value="${old }" class="textInput" >
			</li>
		</ul>
		<ul class="searchContent">
			<li>
				<label>交费期间</label>
				<input type="text" name="feeYear" value="${feeYear }" class="textInput" >
			</li>
		</ul>
		<ul class="searchContent">
			<li>
				<label>已购百倍保</label>
				<input type="text" name="bbb" value="${bbb}" class="textInput" >
			</li>
		</ul>
		<ul class="searchContent">
			<li class="nowwap">
				<label>交费期间</label>
				<input type="radio" name="bbb_year" value="1" checked="checked"/>趸交
				<input type="radio" name="bbb_year" value="3"/>3年
				<input type="radio" name="bbb_year" value="5"/>5年
			</li>
		</ul>
		<div class="subBar">
			<ul>
				<li><div class="button"><div class="buttonContent"><button type="submit">修改</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close" >关闭</button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent">
	<table class="table" layoutH="68" targetType="dialog" width="100%">
		<thead>
			<tr>
				<th orderfield="orgNum">限额查询结果</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>${rst }</td>
			</tr>
		</tbody>
	</table>

	<dwz:pagination page="${page }" targetType="dialog"/>
</div>