<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<form method="post" id="hfForm" action="${contextPath }/component/stastics/underwrite" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
					<label>所属机构：</label>
					<input name="orgCode" id="uw_orgCode" type="hidden" value="${orgCode }"/>
					<input class="validate[required] required" name="name" id="uw_orgName" type="text" readonly="readonly" style="width: 120px;" value="${name }"/>
					<a class="btnLook" href="${contextPath }/management/security/user/lookup2org" lookupGroup="" title="选择机构" width="400">查</a>
					</td>
					<td>承保日期起：
					<input type="text" name="policyDate1" id="uwDate1" class="date validate[required] required" style="width: 80px;" dateFmt="yyyy-MM-dd" value="${policyDate1 }"/><a class="inputDateButton" href="javascript:;">选</a>
					</td>
					<td>承保日期止：
					<input type="text" name="policyDate2" id="uwDate2" class="date validate[required] required" style="width: 80px;" dateFmt="yyyy-MM-dd" value="${policyDate2 }"/><a class="inputDateButton" href="javascript:;">选</a>
					</td>
					<td><label>标记：</label>
					<form:select path="UwModel.levelFlag" id="uwflag" class="combox">
						<form:option value="prov"> 省级 </form:option>
						<form:option value="city"> 市级 </form:option>
					</form:select>
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>						
					<li><div class="button"><div class="buttonContent"><button type="submit">搜索</button></div></div></li>
				</ul>
			</div>
		</div>
	</div>
</form>
<div class="pageContent">

	<div class="panelBar">
		<ul class="toolBar">
			<li class="line">line</li>
			<li><a class="icon" target="_blank" href="${contextPath }/component/stastics/underwrite/toXls?orgCode=${orgCode }&policyDate1=${policyDate1 }&policyDate2=${policyDate2 }&prdCode=${prdCode}&levelFlag=${levelFlag}&netFlag=${netFlag}&perm=${perm}"><span>导出统计结果</span></a></li>
			<li class="line">line</li>
			<li><a class="icon" target="_blank" href="${contextPath }/component/stastics/underwrite/dtlXls?orgCode=${orgCode }&policyDate1=${policyDate1 }&policyDate2=${policyDate2 }&prdCode=${prdCode}&levelFlag=${levelFlag}&netFlag=${netFlag}&perm=${perm}"><span>导出明细数据</span></a></li>
		</ul>
	</div>
	<div id="w_list_print">
		<table class="table" layoutH="120" width="80%">
		<thead>
			<tr>
				<th>序号</th>
				<th>机构</th>
				<th>超10天未回销</th>
				<th>超20天未回销</th>
				<th>超30天未回销</th>
				<th>超50天未回销</th>
				<th>合计</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${cmRst}" varStatus="idx">
			<tr>
				<td>${idx.index+1 }</td>
				<td>${item.organName}</td>
				<td style="text-align: right;width: 100px;font-weight:800; "><fmt:formatNumber value="${item.l10 eq 0?'':item.l10}" pattern="#,###" /></td>
				<td style="text-align: right;width: 100px;font-weight:800;"><fmt:formatNumber value="${item.l20 eq 0?'':item.l20}" pattern="#,###" /></td>
				<td style="text-align: right;width: 100px;font-weight:800;"><fmt:formatNumber value="${item.l30 eq 0?'':item.l30}" pattern="#,###.#" /></td>
				<td style="text-align: right;width: 100px;font-weight:800;"><fmt:formatNumber value="${item.l50 eq 0?'':item.l50}" pattern="#,###.#" /></td>
				<td style="text-align: right;width: 100px;font-weight:800;"><fmt:formatNumber value="${item.sc}" pattern="#,###.#" /></td>
			</tr>
			</c:forEach>
			<tr>
				<td>&nbsp;</td>
				<td>合计：</td>
				<td style="text-align: right;width: 100px;font-weight:800;"><fmt:formatNumber value="${tl10}" pattern="#,###.#" /></td>
				<td style="text-align: right;width: 100px;font-weight:800;"><fmt:formatNumber value="${tl20}" pattern="#,###.#" /></td>
				<td style="text-align: right;width: 100px;font-weight:800;"><fmt:formatNumber value="${tl30}" pattern="#,###.#" /></td>
				<td style="text-align: right;width: 100px;font-weight:800;"><fmt:formatNumber value="${tl50}" pattern="#,###.#" /></td>
				<td style="text-align: right;width: 100px;font-weight:800;"><fmt:formatNumber value="${tsc}" pattern="#,###.#" /></td>
			</tr>
		</tbody>
	</table>
	</div>
</div>