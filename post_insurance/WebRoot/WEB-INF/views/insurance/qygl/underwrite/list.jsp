<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<jsp:useBean id="now" class="java.util.Date" scope="page"/>

<dwz:paginationForm action="${contextPath }/qygl/underwrite/list" page="${page }">
	<input type="hidden" name="search_LIKE_formNo" value="${search_LIKE_formNo }"/>
	<input type="hidden" name="search_LIKE_holder" value="${search_LIKE_holder }"/>
	<input type="hidden" name="orgCode" value="${orgCode }"/>
	<input type="hidden" name="name" value="${name }"/>
	<input type="hidden" name="status" value="${status }"/>
	<input type="hidden" name="status_flag" value="${status_flag }"/>
	<input type="hidden" name="search_LTE_sysDate" value="${param.search_LTE_sysDate }"/>
	<input type="hidden" name="search_GTE_sysDate" value="${param.search_GTE_sysDate }"/>
	<input type="hidden" name="search_GTE_provSendDate" value="${param.search_GTE_provSendDate }"/>
	<input type="hidden" name="search_LTE_provSendDate" value="${param.search_LTE_provSendDate }"/>
	<input type="hidden" name="search_GTE_signDate" value="${param.search_GTE_signDate }"/>
	<input type="hidden" name="search_LTE_signDate" value="${param.search_LTE_signDate }"/>
</dwz:paginationForm>

<form method="post" id="hfForm" action="${contextPath }/qygl/underwrite/list" onsubmit="return navTabSearch(this)">
<input type="hidden" name="status_flag" id="status_flag" value="${status_flag }"/>
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
			<tr>
				<td>投保单号：
					<input type="text" id="uwFormNo" style="width: 100px;" name="search_LIKE_formNo" value="${param.search_LIKE_formNo }"/>
				</td>
				<td>投保人：
					<input type="text" id="uwHolder" style="width: 100px;" name="search_LIKE_holder" value="${param.search_LIKE_holder }"/>
				</td>
				<td>
					<label>机构：</label>
					<input name="orgCode" id="uw_orgCode" type="hidden" value="${orgCode }"/>
					<input class="validate[required] required" name="name" id="uw_orgName" type="text" readonly="readonly" style="width: 110px;" value="${name }"/><a class="btnLook" href="${contextPath }/management/security/user/lookup2org" lookupGroup="" title="选择机构" width="400">查</a>
				</td>
				<td>
					<label>状态：</label>
					<form:select path="underwrite.status" id="uwStatus" class="combox">
						<form:option value=""> -- -- </form:option>
						<form:options items="${UWStatusList }" itemLabel="desc"/>
					</form:select>
				</td>
			</tr>
			<tr>
				<td>
					<label>录入日期起：</label>
					<input type="text" name="search_GTE_sysDate" id="uwDate1" class="date validate[required] required" style="width: 80px;" dateFmt="yyyy-MM-dd" readonly="true" value="${param.search_GTE_sysDate }"/><a class="inputDateButton" href="javascript:;">选</a>
				</td>
				<td>
					<label>录入日期止：</label>
					<input type="text" name="search_LTE_sysDate" id="uwDate2" class="date validate[required] required" style="width: 80px;" dateFmt="yyyy-MM-dd" readonly="true" value="${param.search_LTE_sysDate }"/><a class="inputDateButton" href="javascript:;">选</a>
				</td>
				<td>
					<label>合同寄出起：</label>
					<input type="text" name="search_GTE_provSendDate" id="uwDate1" class="date validate[required] required" style="width: 80px;" dateFmt="yyyy-MM-dd" readonly="true" value="${param.search_GTE_provSendDate }"/><a class="inputDateButton" href="javascript:;">选</a>
				</td>
				<td>
					<label>合同寄出止：</label>
					<input type="text" name="search_LTE_provSendDate" id="uwDate2" class="date validate[required] required" style="width: 80px;" dateFmt="yyyy-MM-dd" readonly="true" value="${param.search_LTE_provSendDate }"/><a class="inputDateButton" href="javascript:;">选</a>
				</td>
			</tr>
			<tr>
				<td>
					<label>签单日期起：</label>
					<input type="text" name="search_GTE_signDate" id="uwDate1" class="date validate[required] required" style="width: 80px;" dateFmt="yyyy-MM-dd" readonly="true" value="${param.search_GTE_signDate }"/><a class="inputDateButton" href="javascript:;">选</a>
				</td>
				<td>
					<label>签单日期止：</label>
					<input type="text" name="search_LTE_signDate" id="uwDate2" class="date validate[required] required" style="width: 80px;" dateFmt="yyyy-MM-dd" readonly="true" value="${param.search_LTE_signDate }"/><a class="inputDateButton" href="javascript:;">选</a>
				</td>
				<td>
					&nbsp;
				</td>
				<td>
					&nbsp;
				</td>
			</tr>
			</table>
			<div class="subBar">
				<ul>						
					<li><div class="button"><div class="buttonContent"><button type="submit" onclick="$('#status_flag').val('');">搜索</button></div></div></li>
				</ul>
			</div>
		</div>
	</div>
</form>

<div class="pageContent">
	<div align="center"><%@ include file="../../../warning.inc.jsp" %></div>
	<div class="panelBar">
		<ul class="toolBar">
			<shiro:hasPermission name="UnderWrite:view">
				<li><a iconClass="magnifier" target="dialog" rel="underwrite_edit" mask="true" width="850" height="440" href="${contextPath }/qygl/underwrite/view/{slt_uid}"><span>查看</span></a></li>
				<li class="line">line</li>
				<li><a class="icon" target="_blank" target="dwzExport" href="${contextPath }/qygl/underwrite/toXls?search_LIKE_formNo=${param.search_LIKE_formNo }&orgCode=${orgCode }&status=${status }&search_LTE_sysDate=${param.search_LTE_sysDate }&search_GTE_sysDate=${param.search_GTE_sysDate }&search_GTE_provSendDate=${param.search_GTE_provSendDate}&search_LTE_provSendDate=${param.search_LTE_provSendDate}&search_GTE_signDate=${param.search_GTE_signDate}&search_LTE_signDate=${search_LTE_signDate}&status_flag=${status_flag}"><span>导出</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="UnderWrite:edit">
			<li class="line">line</li>
			<li><a class="edit" target="dialog" rel="underwrite_edit" mask="true" width="800" height="440" href="${contextPath }/qygl/underwrite/create"><span>新建</span></a></li>
			<li class="line">line</li>
			<li><a class="delete" target="ajaxTodo" href="${contextPath }/qygl/underwrite/DelStatus/{slt_uid}" title="确认作废此人核件?"><span>作废</span></a></li>
			<li class="line">line</li>
				<li><a class="edit" target="dialog" rel="underwrite_edit" mask="true" width="800" height="440" href="${contextPath }/qygl/underwrite/update/{slt_uid}"><span>更新</span></a></li>
				<!-- 
				<li><a class="delete" href="${contextPath}/qygl/underwrite/signDateUpdate/{slt_uid}" target="dialog" mask="true" width="550" height="250"><span>回销登记</span></a></li>
				 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="UnderWrite:provEdit">
				<shiro:hasPermission name="UnderWrite:delete">
					<li class="line">line</li>
					<li><a class="delete" target="selectedTodo" rel="ids" href="${contextPath }/qygl/underwrite/delete" title="确认要删除?"><span>删除</span></a></li>
					<li class="line">line</li>
					<li><a class="delete" target="selectedTodo" rel="ids" href="${contextPath }/qygl/underwrite/scanReceipt" title="确认已经扫描回执？"><span>扫描回执</span></a></li>
				</shiro:hasPermission>
			</shiro:hasPermission>
			<li class="line">line</li>
			<li><a class="edit" target="dialog" rel="underwrite_edit" mask="true" width="450" height="340" href="${contextPath }/qygl/underwrite/plan/{slt_uid}"><span>跟进设置</span></a></li>
			<shiro:hasPermission name="UnderWrite:cityEdit">
			<li class="line">line</li>
				<li><a class="edit" href="${contextPath}/qygl/underwrite/cityRec" target="selectedToDialog" relIds="ids" mask="true" width="550" height="250"><span>地市接收</span></a></li>
				<li><a class="edit" href="${contextPath}/qygl/underwrite/citySend" target="selectedToDialog" relIds="ids" mask="true" width="550" height="250"><span>地市寄出</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="UnderWrite:areaEdit">
			<li class="line">line</li>
				<li><a class="edit" href="${contextPath}/qygl/underwrite/areaRec" target="selectedToDialog" relIds="ids" mask="true" width="550" height="250"><span>县区接收</span></a></li>
				<li><a class="edit" href="${contextPath}/qygl/underwrite/areaSend" target="selectedToDialog" relIds="ids" mask="true" width="550" height="250"><span>县区寄出</span></a></li>
			</shiro:hasPermission>
			<li class="line">line</li>
			<li><a class="icon" target="dialog" href="${contextPath }/qygl/underwrite/help" mask="true" width="530" height="430"><span>功能说明</span></a></li>
		</ul>
	</div>
	
	<table class="table" layoutH="245" width="110%">
		<thead>
			<tr>
				<th><input type="checkbox" group="ids" class="checkboxCtrl"></th>			
				<th orderField=organization.name class="${page.orderField eq 'organization.name' ? page.orderDirection : ''}">市县机构</th>
				<th>投保单号</th>
				<th orderField=policyNo class="${page.orderField eq 'policyNo' ? page.orderDirection : ''}">保单号</th>
				<th orderField=signDate class="${page.orderField eq 'signDate' ? page.orderDirection : ''}">签单日期</th>
				<th orderField=provSendDate class="${page.orderField eq 'provSendDate' ? page.orderDirection : ''}">合同寄出</th>
				<th>快递单号</th>
				<th>全流程</th>
				<th>寄出</th>
				<th>扣费</th>
				<th>回销</th>
				<th>回执扫描</th>
				<shiro:hasPermission name="UnderWrite:cityEdit">
				<th>地市接收</th>
				</shiro:hasPermission>
				<shiro:hasPermission name="UnderWrite:areaEdit">
				<th>县区接收</th>
				</shiro:hasPermission>
				<th>投保人</th>
				<th orderField=prd.prdName class="${page.orderField eq 'prd.prdName' ? page.orderDirection : ''}">产品</th>
				<th orderField=ybtDate class="${page.orderField eq 'ybtDate' ? page.orderDirection : ''}">邮保通录入</th>
				<th orderField=planDate class="${page.orderField eq 'planDate' ? page.orderDirection : ''}">跟进日期</th>
				<th>备注</th>
				<th>网点名称</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${underwrites}">
			<tr target="slt_uid" rel="${item.id}">
				<td><input name="ids" value="${item.id}" type="checkbox"></td>
				<td>${item.organization.shortName}</td>
				<td>${item.formNo}</td>
				<td title="${item.policyNo}">${item.policyNo}</td>
				<td><fmt:formatDate value="${item.signDate }" pattern="yyyy-MM-dd"/></td>
				<td><fmt:formatDate value="${item.provSendDate }" pattern="yyyy-MM-dd"/></td>
				<td><a target="_blank" href="https://m.kuaidi100.com/index_all.html?type=ems%20&postid=${item.provEmsNo}"><div style="color: blue;vertical-align:middle;font-weight:normal;">${item.provEmsNo}</div></a></td>
				<td>${item.longDate}</td>
				<td>${item.hadSendDate}</td>
				<td>${item.payFail?"失败":""}</td>
				<td><fmt:formatDate value="${item.billBackDate }" pattern="yyyy-MM-dd"/></td>
				<td>${item.scanReceipt?"已扫描":""}</td>
				<shiro:hasPermission name="UnderWrite:cityEdit">
				<td><fmt:formatDate value="${item.cityReceiveDate }" pattern="yyyy-MM-dd"/></td>
				</shiro:hasPermission>
				<shiro:hasPermission name="UnderWrite:areaEdit">
				<td><fmt:formatDate value="${item.areaReceiveDate }" pattern="yyyy-MM-dd"/></td>
				</shiro:hasPermission>
				<td title="${item.holder}">${fn:substring(item.holder, 0, 4)}</td>
				<td>${item.prd.prdName}</td>
				<td><fmt:formatDate value="${item.ybtDate }" pattern="yyyy-MM-dd"/></td>
				<td><fmt:formatDate value="${item.planDate }" pattern="yyyy-MM-dd"/></td>
				<td title="${item.remark}">${fn:substring(item.remark,0,10)}</td>
				<td>${item.netName}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
</div>