<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<script type="text/javascript">
<!--
function toTips(val) {
	if(val=="city") {
		alert("hi! 请选择具体地市县机构！~~");
		return false;
	}
}
//-->
</script>
<form method="post" id="hfForm" action="${contextPath }/component/stastics/check" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
					<label>所属机构：</label>
					<input name="orgCode" id="uw_orgCode" type="hidden" value="${orgCode }"/>
					<input class="validate[required] required" name="name" id="uw_orgName" type="text" readonly="readonly" style="width: 120px;" value="${name }"/><a class="btnLook" href="${contextPath }/management/security/user/lookup2org" lookupGroup="" title="选择机构" width="400">查</a>
					</td>
					<td>承保日期起：
					<input type="text" name="policyDate1" id="uwDate1" class="date validate[required] required" style="width: 80px;" dateFmt="yyyy-MM-dd" value="${policyDate1 }"/><a class="inputDateButton" href="javascript:;">选</a>
					</td>
					<td>承保日期止：
					<input type="text" name="policyDate2" id="uwDate2" class="date validate[required] required" style="width: 80px;" dateFmt="yyyy-MM-dd" value="${policyDate2 }"/><a class="inputDateButton" href="javascript:;">选</a>
					</td>
				</tr>
				<tr>
					<td><label>标记：</label>
					<form:select path="CheckModel.levelFlag" id="uwflag" class="combox" onchange="javascript:toTips(this.value);">
						<form:option value="prov"> 省级 </form:option>
						<form:option value="city"> 市级 </form:option>
					</form:select>
					</td>
					<td>
					<label>长期险：</label>
						<form:select path="CheckModel.duration" id="statschckdurationFlag" class="combox">
							<form:option value="0">全部</form:option>
							<form:option value="10">长期险</form:option>
						</form:select>
					</td>
					<td>
					<label>趸/期缴：</label>
					<form:select path="CheckModel.perm" id="pperm" class="combox">
						<form:option value="">  --  </form:option>
						<form:option value="1"> 年交 </form:option>
						<form:option value="0"> 趸交 </form:option>
					</form:select>
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li><div class="button"><div class="buttonContent"><button type="submit">統計</button></div></div></li>
				</ul>
			</div>
		</div>
	</div>
</form>
<div class="pageContent">

	<div class="panelBar">
		<ul class="toolBar">
			<li class="line">line</li>
			<li><a class="icon" target="_blank" href="${contextPath }/component/stastics/checkToXls?orgCode=${orgCode }&policyDate1=${policyDate1 }&policyDate2=${policyDate2 }&levelFlag=${levelFlag}&duration=${duration}&perm=${perm}"><span>导出统计结果</span></a></li>
			<li class="line">line</li>
			<li><a class="icon" target="_blank" href="${contextPath }/component/stastics/checkWrite/dtlToXls?orgCode=${orgCode }&policyDate1=${policyDate1 }&policyDate2=${policyDate2 }&levelFlag=${levelFlag}&duration=${duration}&perm=${perm}"><span>导出填写差错</span></a></li>
			<li class="line">line</li>
			<li><a class="icon" target="_blank" href="${contextPath }/component/stastics/checkRecord/dtlToXls?orgCode=${orgCode }&policyDate1=${policyDate1 }&policyDate2=${policyDate2 }&levelFlag=${levelFlag}&duration=${duration}&perm=${perm}"><span>导出录入差错</span></a></li>
		</ul>
	</div>
	<div id="w_list_print">
		<table class="table" layoutH="120" width="80%">
		<thead>
			<tr>
				<th>序号</th>
				<th>机构编号</th>
				<th>机构名称</th>
				<th>承保件數</th>
				<th>抽检填写件数</th>
				<th>填写差错</th>
				<th>抽检录入件数</th>
				<th>录入差错</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${cmRst}" varStatus="idx">
			<tr>
				<td>${idx.index+1 }</td>
				<td>${item.organCode}</td>
				<td>${item.orgName}</td>
				<td style="text-align: right;font-weight:800;"><fmt:formatNumber value="${item.policyCounts}" pattern="#,###" /></td>
				<td style="text-align: right;font-weight:800;"><fmt:formatNumber value="${item.checkCounts}" pattern="#,###" /></td>
				<td style="text-align: right;font-weight:800;"><fmt:formatNumber value="${item.errCounts}" pattern="#,###" /></td>
				<td style="text-align: right;font-weight:800;"><fmt:formatNumber value="${item.checkRecordCounts}" pattern="#,###" /></td>
				<td style="text-align: right;font-weight:800;"><fmt:formatNumber value="${item.checkRecordErrCounts}" pattern="#,###.#" /></td>
			</tr>
			</c:forEach>
			<tr>
				<td>&nbsp;</td>
				<td>合计：</td>
				<td style="text-align: right;font-weight:800;"><fmt:formatNumber value="${tl10}" pattern="#,###.#" /></td>
				<td style="text-align: right;font-weight:800;"><fmt:formatNumber value="${tl20}" pattern="#,###.#" /></td>
				<td style="text-align: right;font-weight:800;"><fmt:formatNumber value="${tl30}" pattern="#,###.#" /></td>
				<td style="text-align: right;font-weight:800;"><fmt:formatNumber value="${tl50}" pattern="#,###.#" /></td>
				<td style="text-align: right;font-weight:800;"><fmt:formatNumber value="${tsc}" pattern="#,###.#" /></td>
			</tr>
		</tbody>
	</table>
	</div>
</div>