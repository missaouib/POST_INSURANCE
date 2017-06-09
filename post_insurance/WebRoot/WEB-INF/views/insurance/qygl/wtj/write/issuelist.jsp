<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<dwz:paginationForm action="${contextPath }/qygl/issue/write/issuelist" page="${page }">
</dwz:paginationForm>

<form method="post" action="${contextPath }/qygl/issue/write/issuelist" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
	</div>
</form>

<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<shiro:hasPermission name="CheckWrite:view">
				<li><a iconClass="magnifier" target="dialog" rel="lookup2organization_edit" mask="true" width="850" height="680" href="${contextPath }/qygl/issue/write/view/{slt_uid}"><span>查看新契约不合格件</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="CheckWrite:edit">
				<li><a class="edit" target="dialog" rel="lookup2organization_edit" mask="true" width="850" height="520" href="${contextPath }/qygl/issue/write/update/{slt_uid}"><span>回复新契约不合格件</span></a></li>
			</shiro:hasPermission>
		</ul>
	</div>
	<table class="table" layoutH="137" width="100%">
		<thead>
			<tr>
				<th><input type="checkbox" group="ids" class="checkboxCtrl"></th>			
				<th orderField=organization.name class="${page.orderField eq 'organization.name' ? page.orderDirection : ''}">所属机构</th>
				<th orderField=policy.policyNo class="${page.orderField eq 'policy.policyNo' ? page.orderDirection : ''}">所属保单号</th>
				<th orderField=policy.policyDate class="${page.orderField eq 'policy.policyDate' ? page.orderDirection : ''}">承保日期</th>
				<th orderField=fixStatus class="${page.orderField eq 'fixStatus' ? page.orderDirection : ''}">问题件状态</th>
				<th orderField=docMiss class="${page.orderField eq 'docMiss' ? page.orderDirection : ''}">资料缺失</th>
				<th orderField=keyInfo class="${page.orderField eq 'keyInfo' ? page.orderDirection : ''}">关键信息</th>
				<th orderField=importanceInfo class="${page.orderField eq 'importanceInfo' ? page.orderDirection : ''}">重要信息</th>
				<th orderField=netName class="${page.orderField eq 'netName' ? page.orderDirection : ''}">网点名称</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${issues}">
			<tr target="slt_uid" rel="${item.id}">
				<td><input name="ids" value="${item.id}" type="checkbox"></td>
				<td>${item.policy.organization.shortName}</td>
				<td>${item.policy.policyNo}</td>
				<td>${item.policy.policyDate}</td>
				<td>
				<c:choose>
					<c:when test="${item.fixStatus eq 'NewStatus'}">
						<span style="color:red; height:50%; margin-bottom:-contentheight;">待处理</span>
					</c:when>
					<c:when test="${item.fixStatus eq 'DealStatus'}">
						已处理
					</c:when>
					<c:when test="${item.fixStatus eq 'ReopenStatus'}">
						重打开
					</c:when>
					<c:otherwise>
						已关闭
					</c:otherwise>
				</c:choose>
				</td>
				<td>${item.docMiss == "null"?"":item.docMiss}</td>
				<td>${item.keyInfo=="null"?"":item.keyInfo}</td>
				<td>${item.importanceInfo=="null"?"":item.importanceInfo}</td>
				<td>
				<c:choose>  
					    <c:when test="${fn:length(item.netName) > 14}">  
					        <c:out value="${fn:substring(item.netName, 14, 30)}" />  
					    </c:when>  
					   <c:otherwise>  
					      <c:out value="${item.netName}" />  
					    </c:otherwise>  
					</c:choose>
				</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
</div>