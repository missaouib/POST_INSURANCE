<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<div class="pageContent sortDrag" selector="h1" layoutH="12">
	<fieldset>
	<legend>待办任务</legend>
	<shiro:hasPermission name="Wtgd:view">
	<div class="panel <c:if test='${fn:length(issueList)<=0}'>close</c:if> collapse" defH="100">
		<h1><c:if test='${fn:length(issueList)>0}'><img alt="有新任务" src="/images/redpoint.png" height="12" width="12"></c:if>待处理问题工单</h1>
		<div>
			<table class="list" width="98%">
				<thead>
					<tr>
						<th>序号</th>
						<th>工单号</th>
						<th>待处理时间</th>
						<th>离结案（天）</th>
						<th>工单状态</th>
						<th>保单号</th>
						<th>保单所属机构</th>
						<th>工单子类型</th>
						<th>工单内容</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="item" items="${issueList}" varStatus="var">
					<tr target="slt_uid" rel="${item.id}">
						<td>${var.index+1 }</td>
						<td>
						<c:if test="${fn:length(login_user.organization.orgCode) > 4}">
						<a target="dialog" rel="lookup2organization_edit" mask="true" width="850" height="520" href="${contextPath }/kfgl/issue/update/${item.id}"><span>${item.issueNo}</span></a>
						</c:if>
						 <c:if test="${fn:length(login_user.organization.orgCode) <= 4}"> 
					     <a target="dialog" rel="lookup2organization_edit" mask="true" width="850" height="520" href="${contextPath }/kfgl/issue/view/${item.id}"><span>${item.issueNo}</span></a>
					    </c:if> 
						</td>
						<td>${item.operateTime }</td>
						<td><span style="color:red; height:50%; margin-bottom:-contentheight;">${item.lastDateNum }</span></td>
						<td>${item.status}</td>
						<td>${item.policy.policyNo}</td>
						<td>${fn:replace(item.policy.organization.name,'中邮保险局','')}</td>
						<td>${item.issueType}</td>
						<td>${item.issueContent}</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	</shiro:hasPermission>
	<shiro:hasPermission name="Cservice:view">
	<div class="panel <c:if test='${fn:length(bqIssueList)<=0}'>close</c:if> collapse" defH="100">
		<h1><c:if test='${fn:length(bqIssueList)>0}'><img alt="有新任务" src="/images/redpoint.png" height="12" width="12"></c:if>待处理保全复核问题</h1>
		<div>
			<table class="list" width="98%">
				<thead>
					<tr>
						<th>序号</th>
						<th>保单号</th>
						<th>保单所属机构</th>
						<th>保全复核问题</th>
						<th>问题产生日期</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="item" items="${bqIssueList}" varStatus="var">
					<tr target="slt_uid" rel="${item.id}">
						<td>${var.index+1 }</td>
						<td>${item.policy.policyNo}</td>
						<td>${fn:replace(item.policy.organization.name,'邮政局中邮保险局','')}</td>
						<td>${item.info}</td>
						<td>${item.csDate}</td>
						<td>
						<c:if test="${fn:length(login_user.organization.orgCode) > 4}">
						<a target="ajaxTodo" href="${contextPath }/bqgl/issue/DealStatus/${item.id}" title="确认更新状态?"><span>已处理</span></a>
						</c:if>
						 <c:if test="${fn:length(login_user.organization.orgCode) <= 4}"> 
					     <a target="ajaxTodo" href="${contextPath }/bqgl/issue/CloseStatus/${item.id}" title="确认关闭?"><span>关闭</span></a>
					    </c:if> 
						</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	</shiro:hasPermission>
	<shiro:hasPermission name="Callfail:view">
	<div class="panel <c:if test='${fn:length(hfIssueList)<=0}'>close</c:if> collapse" defH="100">
		<h1><c:if test='${fn:length(hfIssueList)>0}'><img alt="有新任务" src="/images/redpoint.png" height="12" width="12"></c:if>待上门回访工单</h1>
		<div>
			<table class="list" width="98%">
				<thead>
					<tr>
						<th>序号</th>
						<th>工单号</th>
						<th>待处理时间</th>
						<th>离犹豫期/天</th>
						<th>工单状态</th>
						<th>保单号</th>
						<th>保单所属机构</th>
						<th>工单子类型</th>
						<th>工单内容</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="item" items="${hfIssueList}" varStatus="var">
					<tr target="slt_uid" rel="${item.id}">
						<td>${var.index+1 }</td>
						<td>
						<c:if test="${fn:length(login_user.organization.orgCode) > 4}">
						<a target="dialog" rel="lookup2organization_edit" mask="true" width="850" height="520" href="${contextPath }/hfgl/issue/update/${item.id}"><span>${item.issueNo}</span></a>
						</c:if>
						 <c:if test="${fn:length(login_user.organization.orgCode) <= 4}"> 
					     <a target="dialog" rel="lookup2organization_edit" mask="true" width="850" height="520" href="${contextPath }/hfgl/issue/view/${item.id}"><span>${item.issueNo}</span></a>
					    </c:if> 
						</td>
						<td><fmt:formatDate value="${item.shouldDate }" pattern="yyyy-MM-dd"/></td>
						<td><span style="color:red; height:50%; margin-bottom:-contentheight;"><c:if test="${item.lastDateNum<0 }">0</c:if><c:if test="${item.lastDateNum>=0 }">${item.lastDateNum }</c:if></span></td>
						<td>${item.status}</td>
						<td>${item.policy.policyNo}</td>
						<td>${fn:replace(item.policy.organization.name,'中邮保险局','')}</td>
						<td>${item.issueType}</td>
						<td>${item.issueContent}</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	</shiro:hasPermission>
	<shiro:hasPermission name="Renewed:view">
	<div class="panel <c:if test='${fn:length(xqIssueList)<=0}'>close</c:if> collapse" defH="100">
		<h1><c:if test='${fn:length(xqIssueList)>0}'><img alt="有新任务" src="/images/redpoint.png" height="12" width="12"></c:if>续期催缴工单</h1>
		<div>
			<table class="list" width="98%">
				<thead>
					<tr>
						<th>序号</th>
						<th>保单号</th>
						<th>保单所属机构</th>
						<th>交费对应日</th>
						<th>离宽限期/天</th>
						<th>交费状态</th>
						<th>交费失败原因</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="item" items="${xqIssueList}" varStatus="var">
					<tr target="slt_uid" rel="${item.id}">
						<td>${var.index+1 }</td>
						<td>
						<c:if test="${fn:length(login_user.organization.orgCode) > 4}">
						<a target="dialog" rel="lookup2organization_edit" mask="true" width="850" height="520" href="${contextPath }/xqgl/issue/update/${item.id}"><span>${item.policy.policyNo}</span></a>
						</c:if>
						 <c:if test="${fn:length(login_user.organization.orgCode) <= 4}"> 
					     <a target="dialog" rel="lookup2organization_edit" mask="true" width="850" height="520" href="${contextPath }/xqgl/issue/view/${item.id}"><span>${item.policy.policyNo}</span></a>
					    </c:if> 
						</td>
						<td>${fn:replace(item.policy.organization.name,'中邮保险局','')}</td>
						<td><fmt:formatDate value="${item.feeDate }" pattern="yyyy-MM-dd"/></td>
						<td><span style="color:red; height:50%; margin-bottom:-contentheight;">${item.lastDateNum }</span></td>
						<td>${item.feeStatus}</td>
						<td>${item.feeFailReason}</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	</shiro:hasPermission>
	<shiro:hasPermission name="CheckWrite:view">
	<div class="panel <c:if test='${fn:length(checkWriteIssueList)<=0}'>close</c:if> collapse" defH="100">
		<h1><c:if test='${fn:length(checkWriteIssueList)>0}'><img alt="有新任务" src="/images/redpoint.png" height="12" width="12"></c:if>新契约填写不合格件</h1>
		<div>
			<table class="list" width="98%">
				<thead>
					<tr>
						<th>序号</th>
						<th>保单号</th>
						<th>保单所属机构</th>
						<th>承保日期</th>
						<th>状态</th>
						<th>关键信息</th>
						<th>重要信息</th>
						<th>扫描不完整</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="item" items="${checkWriteIssueList}" varStatus="var">
					<tr target="slt_uid" rel="${item.id}">
						<td>${var.index+1 }</td>
						<td>
						<c:if test="${fn:length(login_user.organization.orgCode) > 4}">
						<a target="dialog" rel="lookup2organization_edit" mask="true" width="850" height="520" href="${contextPath }/qygl/issue/write/update/${item.id}"><span>${item.policy.policyNo}</span></a>
						</c:if>
						 <c:if test="${fn:length(login_user.organization.orgCode) <= 4}"> 
					     <a target="dialog" rel="lookup2organization_edit" mask="true" width="850" height="520" href="${contextPath }/qygl/issue/write/view/${item.id}"><span>${item.policy.policyNo}</span></a>
					    </c:if> 
						</td>
						<td>${fn:replace(item.policy.organization.name,'邮政局中邮保险局','')}</td>
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
						<td>${item.keyInfo=="null"?"":item.keyInfo}</td>
						<td>${item.importanceInfo="null"?"":item.importanceInfo}</td>
						<td>${item.docMiss==null?"":item.docMiss}</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	</shiro:hasPermission>
	<shiro:hasPermission name="CheckRecord:view">
	<div class="panel <c:if test='${fn:length(checkRecordIssueList)<=0}'>close</c:if> collapse" defH="100">
		<h1><c:if test='${fn:length(checkRecordIssueList)>0}'><img alt="有新任务" src="/images/redpoint.png" height="12" width="12"></c:if>新契约录入不合格件</h1>
		<div>
			<table class="list" width="98%">
				<thead>
					<tr>
						<th>序号</th>
						<th>保单号</th>
						<th>保单所属机构</th>
						<th>承保日期</th>
						<th>状态</th>
						<th>关键信息</th>
						<th>重要信息</th>
						<th>扫描不完整</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="item" items="${checkRecordIssueList}" varStatus="var">
					<tr target="slt_uid" rel="${item.id}">
						<td>${var.index+1 }</td>
						<td>
						<c:if test="${fn:length(login_user.organization.orgCode) > 4}">
						<a target="dialog" rel="lookup2organization_edit" mask="true" width="850" height="520" href="${contextPath }/qygl/issue/record/update/${item.id}"><span>${item.policy.policyNo}</span></a>
						</c:if>
						 <c:if test="${fn:length(login_user.organization.orgCode) <= 4}"> 
					     <a target="dialog" rel="lookup2organization_edit" mask="true" width="850" height="520" href="${contextPath }/qygl/issue/record/view/${item.id}"><span>${item.policy.policyNo}</span></a>
					    </c:if> 
						</td>
						<td>${fn:replace(item.policy.organization.name,'邮政局中邮保险局','')}</td>
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
						<td>${item.keyInfo=="null"?"":item.keyInfo}</td>
						<td>${item.importanceInfo="null"?"":item.importanceInfo}</td>
						<td>${item.docMiss==null?"":item.docMiss}</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	</shiro:hasPermission>
	<shiro:hasPermission name="UnderWrite:view">
	<div class="panel <c:if test='${fn:length(underwriteList)<=0}'>close</c:if> collapse" defH="100">
		<h1><c:if test='${fn:length(underwriteList)>0}'><img alt="有新任务" src="/images/redpoint.png" height="12" width="12"></c:if>人核件跟进任务</h1>
		<div>
			<table class="list" width="98%">
				<thead>
					<tr>
						<th>市县机构</th>
						<th>投保单号</th>
						<th>保单号</th>
						<th>投保人</th>
						<th>产品</th>
						<th>邮保通录入时间</th>
						<th>核心录入时间</th>
						<th>复核时间</th>
						<th>签单日期</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="item" items="${underwriteList}">
					<tr target="slt_uid" rel="${item.id}">
						<td>${fn:replace(item.organization.name,'中邮保险局','')}</td>
						<td>
						<a target="dialog" rel="lookup2organization_edit" mask="true" width="550" height="220" href="${contextPath }/qygl/underwrite/signDateUpdate/${item.id}"><span>${item.formNo}</span></a>
						</td>
						<td>${item.policyNo}</td>
						<td>${item.holder}</td>
						<td>${item.prd.prdName}</td>
						<td><fmt:formatDate value="${item.ybtDate }" pattern="yyyy-MM-dd"/></td>
						<td><fmt:formatDate value="${item.sysDate }" pattern="yyyy-MM-dd"/></td>
						<td><fmt:formatDate value="${item.checkDate }" pattern="yyyy-MM-dd"/></td>
						<td><fmt:formatDate value="${item.signDate }" pattern="yyyy-MM-dd"/></td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	</shiro:hasPermission>
	<shiro:hasPermission name="ToBQFailList:view">
		<div class="panel <c:if test='${fn:length(bqtofaillist)<=0}'>close</c:if> collapse" defH="100">
			<h1><c:if test='${fn:length(bqtofaillist)>0}'><img alt="有新任务" src="/images/redpoint.png" height="12" width="12"></c:if>保全付费失败清单</h1>
			<div>
				<table class="list" width="98%">
					<thead>
						<tr>
							<th>管理机构</th>
							<th>账户名</th>
							<th>账号</th>
							<th>金额</th>
							<th>状态描述</th>
							<th>回盘日期</th>
							<th>关联业务号码</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="item" items="${bqtofaillist}">
						<tr target="slt_uid" rel="${item.id}">
							<td>${item.organization.name}</td>
							<td>${item.accountName}</td>
							<td>${item.account}</td>
							<td>${item.money}</td>
							<td>${item.failDesc}</td>
							<td><fmt:formatDate value="${item.backDate }" pattern="yyyy-MM-dd"/></td>
							<td>${item.relNo}</td>
							<td>
								<a target="ajaxTodo" href="${contextPath }/pay/close/${item.id}" title="确认关闭此保全付费失败记录?"><span>关闭</span></a>
							</td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		</shiro:hasPermission>
		<shiro:hasPermission name="FromBQFailList:view">
		<div class="panel <c:if test='${fn:length(bqfromfaillist)<=0}'>close</c:if> collapse" defH="100">
			<h1><c:if test='${fn:length(bqfromfaillist)>0}'><img alt="有新任务" src="/images/redpoint.png" height="12" width="12"></c:if>保全收费失败清单</h1>
			<div>
				<table class="list" width="98%">
					<thead>
						<tr>
							<th>管理机构</th>
							<th>账户名</th>
							<th>账号</th>
							<th>金额</th>
							<th>状态描述</th>
							<th>回盘日期</th>
							<th>关联业务号码</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="item" items="${bqfromfaillist}">
						<tr target="slt_uid" rel="${item.id}">
							<td>${item.organization.name}</td>
							<td>${item.accountName}</td>
							<td>${item.account}</td>
							<td>${item.money}</td>
							<td>${item.failDesc}</td>
							<td><fmt:formatDate value="${item.backDate }" pattern="yyyy-MM-dd"/></td>
							<td>${item.relNo}</td>
							<td>
								<a target="ajaxTodo" href="${contextPath }/pay/close/${item.id}" title="确认关闭此保全收费失败记录?"><span>关闭</span></a>
							</td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		</shiro:hasPermission>
		<shiro:hasPermission name="FromXQFailList:view">
		<div class="panel <c:if test='${fn:length(xqfromfaillist)<=0}'>close</c:if> collapse" defH="100">
			<h1><c:if test='${fn:length(xqfromfaillist)>0}'><img alt="有新任务" src="/images/redpoint.png" height="12" width="12"></c:if>续期扣费失败清单</h1>
			<div>
				<table class="list" width="98%">
					<thead>
						<tr>
							<th>管理机构</th>
							<th>账户名</th>
							<th>账号</th>
							<th>金额</th>
							<th>状态描述</th>
							<th>回盘日期</th>
							<th>关联业务号码</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="item" items="${xqfromfaillist}">
						<tr target="slt_uid" rel="${item.id}">
							<td>${item.organization.name}</td>
							<td>${item.accountName}</td>
							<td>${item.account}</td>
							<td>${item.money}</td>
							<td>${item.failDesc}</td>
							<td><fmt:formatDate value="${item.backDate }" pattern="yyyy-MM-dd"/></td>
							<td>${item.relNo}</td>
							<td>
								<a target="ajaxTodo" href="${contextPath }/pay/close/${item.id}" title="确认关闭此续期扣费失败记录?"><span>关闭</span></a>
							</td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		</shiro:hasPermission>
		<shiro:hasPermission name="FromQYFailList:view">
		<div class="panel <c:if test='${fn:length(qyfromfaillist)<=0}'>close</c:if> collapse" defH="100">
			<h1><c:if test='${fn:length(qyfromfaillist)>0}'><img alt="有新任务" src="/images/redpoint.png" height="12" width="12"></c:if>人核件扣费失败清单</h1>
			<div>
				<table class="list" width="98%">
					<thead>
						<tr>
							<th>管理机构</th>
							<th>账户名</th>
							<th>账号</th>
							<th>金额</th>
							<th>状态描述</th>
							<th>回盘日期</th>
							<th>关联业务号码</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="item" items="${qyfromfaillist}">
						<tr target="slt_uid" rel="${item.id}">
							<td>${item.organization.name}</td>
							<td>${item.accountName}</td>
							<td>${item.account}</td>
							<td>${item.money}</td>
							<td>${item.failDesc}</td>
							<td><fmt:formatDate value="${item.backDate }" pattern="yyyy-MM-dd"/></td>
							<td>${item.relNo}</td>
							<td>
								<a target="ajaxTodo" href="${contextPath }/pay/close/${item.id}" title="确认关闭此人核件扣费失败记录?"><span>关闭</span></a>
							</td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		</shiro:hasPermission>
		<shiro:hasPermission name="ToLPFailList:view">
		<div class="panel <c:if test='${fn:length(lptofaillist)<=0}'>close</c:if> collapse" defH="100">
			<h1><c:if test='${fn:length(lptofaillist)>0}'><img alt="有新任务" src="/images/redpoint.png" height="12" width="12"></c:if>理赔付费失败清单</h1>
			<div>
				<table class="list" width="98%">
					<thead>
						<tr>
							<th>管理机构</th>
							<th>账户名</th>
							<th>账号</th>
							<th>金额</th>
							<th>状态描述</th>
							<th>回盘日期</th>
							<th>关联业务号码</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="item" items="${lptofaillist}">
						<tr target="slt_uid" rel="${item.id}">
							<td>${item.organization.name}</td>
							<td>${item.accountName}</td>
							<td>${item.account}</td>
							<td>${item.money}</td>
							<td>${item.failDesc}</td>
							<td><fmt:formatDate value="${item.backDate }" pattern="yyyy-MM-dd"/></td>
							<td>${item.relNo}</td>
							<td>
								<a target="ajaxTodo" href="${contextPath }/pay/close/${item.id}" title="确认关闭此理赔付费失败记录?"><span>关闭</span></a>
							</td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		</shiro:hasPermission>
</fieldset>
	</div>