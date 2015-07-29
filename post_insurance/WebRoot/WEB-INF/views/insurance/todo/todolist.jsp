<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<div class="pageContent sortDrag" selector="h1" layoutH="10">
	<div class="tabs" currentIndex="0" eventType="click">
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					<li><a href="/kfgl/issuelist" class="j-ajax"><span><img src="/images/redpoint.png" height="12" width="12">问题工单</span></a></li>
					<shiro:hasPermission name="CheckWrite:view">
					<li><a href="/qygl/issue/write/issuelist" class="j-ajax"><span>契约不合格件（填写）</span></a></li>
					</shiro:hasPermission>
					<shiro:hasPermission name="CheckRecord:view">
					<li><a href="/qygl/issue/record/issuelist" class="j-ajax"><span>契约不合格件（录入）</span></a></li>
					</shiro:hasPermission>
					<shiro:hasPermission name="Cservice:view">
					<li><a href="/bqgl/issuelist" class="j-ajax"><span>保全复核问题</span></a></li>
					</shiro:hasPermission>
					<shiro:hasPermission name="Callfail:view">
					<li><a href="/hfgl/issuelist" class="j-ajax"><span>回访不成功件</span></a></li>
					</shiro:hasPermission>
					<shiro:hasPermission name="Renewed:view">
					<li><a href="/xqgl/issuelist" class="j-ajax"><span>续期催缴件</span></a></li>
					</shiro:hasPermission>
					<shiro:hasPermission name="Underwrite:view">
					<li><a href="/qygl/underwritelist" class="j-ajax"><span>人工核保件跟进</span></a></li>
					</shiro:hasPermission>
				</ul>
			</div>
		</div>
		<div class="tabsContent" style="height:40;">
			<div class="pageContent">
				<div class="panelBar">
					<ul class="toolBar">
						<shiro:hasPermission name="Wtgd:view">
							<li><a iconClass="user_edit" target="dialog" rel="lookup2organization_edit" mask="true" width="850" height="680" href="${contextPath }/kfgl/issue/view/{slt_uid}"><span>查看问题工单</span></a></li>
						</shiro:hasPermission>
						<shiro:hasPermission name="Wtgd:edit">
							<li><a iconClass="user_edit" target="dialog" rel="lookup2organization_edit" mask="true" width="850" height="520" href="${contextPath }/kfgl/issue/update/{slt_uid}"><span>回复问题工单</span></a></li>
						</shiro:hasPermission>
					</ul>
				</div>
				<table class="table" layoutH="137" width="100%">
					<thead>
						<tr>
							<th><input type="checkbox" group="ids" class="checkboxCtrl"></th>			
							<th orderField=organization.name class="${page.orderField eq 'organization.name' ? page.orderDirection : ''}">所属机构</th>
							<th orderField=issueNo class="${page.orderField eq 'issueNo' ? page.orderDirection : ''}">工单编号</th>
							<th>工单内容</th>
							<th>离结案还有（天）</th>
							<th orderField=policy.policyNo class="${page.orderField eq 'policy.policyNo' ? page.orderDirection : ''}">所属保单号</th>
							<th>保单所属机构</th>
							<th orderField=status class="${page.orderField eq 'status' ? page.orderDirection : ''}">工单状态</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="item" items="${issues}">
						<tr target="slt_uid" rel="${item.id}">
							<td><input name="ids" value="${item.id}" type="checkbox"></td>
							<td>${item.organization.name}</td>
							<td>${item.issueNo}</td>
							<td>${item.issueContent}</td>
							<td><span style="color:red; height:50%; margin-bottom:-contentheight;">${item.lastDateNum }</span></td>
							<td>${item.policy.policyNo}</td>
							<td>${item.policy.organization.name}</td>
							<td>${item.status}</td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
				<!-- 分页 -->
				<dwz:pagination page="${page }"/>
			</div>
			<div></div>
			<div></div>
			<div></div>
			<div></div>
			<div></div>
			<div></div>
		</div>
		<div class="tabsFooter">
			<div class="tabsFooterContent"></div>
		</div>
	</div>
</div>