<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<dwz:paginationForm action="${contextPath }/hfgl/issue/maxlist" page="${page }" onsubmit="return dwzSearch(this, 'dialog');">
	<input type="hidden" name="search_LIKE_issueNo" value="${param.search_LIKE_issueNo }"/>
	<input type="hidden" name="policy.orgCode" value="${policy_orgCode }"/>
	<input type="hidden" name="policy.name" value="${policy_name }"/>
	<input type="hidden" name="search_LTE_readyDate" value="${param.search_LTE_readyDate }"/>
	<input type="hidden" name="search_GTE_readyDate" value="${param.search_GTE_readyDate }"/>
	<input type="hidden" name="search_LTE_policy.policyDate" value="${search_LTE_policy_policyDate }"/>
	<input type="hidden" name="search_GTE_policy.policyDate" value="${search_GTE_policy_policyDate }"/>
	<input type="hidden" name="search_LIKE_policy.policyNo" value="${search_LIKE_policy_policyNo }"/>
	<input type="hidden" name="search_LIKE_hasLetter" value="${search_LIKE_hasLetter }"/>
	<input type="hidden" name="status" value="${status }"/>
	<input type="hidden" name="canCallAgain" value="${canCallAgain }" />
	<input type="hidden" name="hqDealFlag" value="${hqDealFlag }" />
	<input type="hidden" name="orgDealFlag" value="${orgDealFlag }" />
	<input type="hidden" name="search_LIKE_policy.holder" value="${search_LIKE_policy_holder}"/>
</dwz:paginationForm>

<form method="post" rel="hfForm" action="${contextPath }/hfgl/issue/maxlist" onsubmit="return dwzSearch(this, 'dialog');">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						工单号：<input type="text" id="mxhfIssueNo" style="width: 100px;" name="search_LIKE_issueNo" value="${param.search_LIKE_issueNo }"/>
					</td>
					<td>
						<label>状态：</label>
						<form:select path="issue.status" id="mxhfStatus" class="combox">
							<form:option value=""> -- -- </form:option>
							<form:options items="${hfStatusList }" itemLabel="desc" itemValue="desc"/>
						</form:select>
					</td>
					<td>
						<label>所属机构：</label>
						<input name="policy.orgCode" id="mxhf_orgCode" type="hidden" value="${policy_orgCode }"/>
						<input class="validate[required] required" name="policy.name" id="mxhf_orgName" type="text" readonly="readonly" style="width: 100px;" value="${policy_name }"/><a class="btnLook" href="${contextPath }/management/security/user/lookup2org" lookupGroup="policy" title="选择机构" width="400">查</a>
					</td>
					<td>
					投保人：<input type="text" id="policy_holder" style="width: 100px;" name="search_LIKE_policy.holder" value="${search_LIKE_policy_holder }"/>
					</td>
				</tr>
				<tr>
					<td>
						保单号：<input type="text" id="mxhfPolicyNo" style="width: 100px;" name="search_LIKE_policy.policyNo" value="${search_LIKE_policy_policyNo }"/>
					</td>
					<td>
						<label>工单开始日期：</label>
						<input type="text" name="search_GTE_readyDate" id="mxhfDate1" class="date validate[required] required" style="width: 80px;" dateFmt="yyyy-MM-dd" readonly="true" value="${param.search_GTE_readyDate }"/><a class="inputDateButton" href="javascript:;">选</a>
					</td>
					<td>
						<label>工单结束日期：</label>
						<input type="text" name="search_LTE_readyDate" id="mxhfDate2" class="date validate[required] required" style="width: 80px;" dateFmt="yyyy-MM-dd" readonly="true" value="${param.search_LTE_readyDate }"/><a class="inputDateButton" href="javascript:;">选</a>
					</td>
					<td>
						<label>已信函：</label>
						<form:select path="issue.search_LIKE_hasLetter" id="hfHsLetter" class="combox">
							<form:option value=""> -- -- </form:option>
							<form:option value="信函已发"> 信函已发 </form:option>
							<form:option value="信函成功"> 信函成功 </form:option>
							<form:option value="信函失败"> 信函失败 </form:option>
						</form:select>
					</td>
				</tr>
				<tr>
					<td>
						<label>再访：</label>
						<form:select path="issue.canCallAgain" id="canCallAgain" class="combox">
							<form:option value=""> -- -- </form:option>
							<form:option value="true"> 可再访 </form:option>
						</form:select>
					</td>
					<td>
						<label>承保日期：</label>
						<input type="text" name="search_GTE_policy.policyDate" id="hfDate1" class="date validate[required] required" style="width: 80px;" dateFmt="yyyy-MM-dd" readonly="true" value="${search_GTE_policy_policyDate }"/><a class="inputDateButton" href="javascript:;">选</a>
					</td>
					<td>
						<label>承保日期：</label>
						<input type="text" name="search_LTE_policy.policyDate" id="hfDate2" class="date validate[required] required" style="width: 80px;" dateFmt="yyyy-MM-dd" readonly="true" value="${search_LTE_policy_policyDate }"/><a class="inputDateButton" href="javascript:;">选</a>
					</td>
					<td>
						<label>上门类型：：</label>
						<form:select path="issue.dealType" class="combox">
							<form:option value=""> -- </form:option>
							<form:options items="${orgTypeList }" itemLabel="typeDesc" itemValue="typeName"/>
						</form:select>
					</td>
				</tr>
				<tr>
					<td>
						<label>二访结果：</label>
						<form:select path="issue.hqDealFlag" id="hqDealFlag" class="combox">
							<form:option value=""> -- -- </form:option>
							<form:option value="1"> 二访成功 </form:option>
							<form:option value="0"> 二访失败 </form:option>
						</form:select>
					</td>
					<td>
						<label>上门结果：</label>
						<form:select path="issue.orgDealFlag" id="orgDealFlag" class="combox">
							<form:option value=""> -- -- </form:option>
							<form:option value="1"> 上门成功 </form:option>
							<form:option value="0"> 上门失败 </form:option>
						</form:select>
					</td>
					<td>
						<label>二访类型：</label>
						<form:select path="issue.hqDealType" class="combox">
							<form:option value=""> -- </form:option>
							<form:options items="${hqTypeList }" itemLabel="typeDesc" itemValue="typeName"/>
						</form:select>
					</td>
					<td>
						&nbsp;
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
			<shiro:hasPermission name="Callfail:view">
				<li><a class="edit" target="dialog" rel="lookup2organization_edit" mask="true" width="820" height="520" href="${contextPath }/hfgl/issue/view/{slt_uid}"><span>查看</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="Callfail:edit">
				<li><a class="edit" target="dialog" rel="lookup2organization_edit" mask="true" width="820" height="520" href="${contextPath }/hfgl/issue/update/{slt_uid}"><span>回复</span></a></li>
				<li><a class="delete" href="${contextPath}/hfgl/updateResetStatus/{slt_uid}" target="dialog" mask="true" width="550" height="250"><span>电话重置</span></a></li>
				<li class="line">line</li>
				<li><a class="delete" target="selectedTodo" rel="ids" href="${contextPath}/hfgl/batchCallReset" title="确认要设置为可再次二访?"><span>批量可再访</span></a></li>
				<li><a class="delete" href="${contextPath}/hfgl/callReset/{slt_uid}" target="dialog" mask="true" width="550" height="250"><span>可再访</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="Callfail:provEdit">
			<li class="line">line</li>
				<li><a class="edit" target="dialog" rel="lookup2organization_edit" mask="true" width="820" height="520" href="${contextPath }/hfgl/issue/provUpdate/{slt_uid}"><span>省分登记</span></a></li>
				<li><a target="myDialog" rel="ids" href="${contextPath }/hfgl/issue/toSetMailDate" class="edit"><span>批量已发信函</span></a></li>
				<li><a class="delete" target="selectedTodo" rel="ids" href="${contextPath }/hfgl/issue/CloseStatus" title="确认要结案关闭?"><span>结案关闭</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="Callfail:11185Edit">
			<li class="line">line</li>
				<li><a class="edit" target="dialog" rel="lookup2organization_edit" mask="true" width="820" height="520" href="${contextPath }/hfgl/issue/hqUpdate/{slt_uid}"><span>二访中心登记</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="Callfail:view">
				<li class="line">line</li>
				<li><a class="icon" target="_blank" href="${contextPath }/hfgl/toXls?dealType=${dealType }&hqDealType=${hqDealType }&search_LIKE_issueNo=${param.search_LIKE_issueNo }&policy.orgCode=${policy_orgCode }&search_LTE_readyDate=${param.search_LTE_readyDate }&search_GTE_readyDate=${param.search_GTE_readyDate }&search_GTE_policy.policyDate=${search_GTE_policy_policyDate }&search_LTE_policy.policyDate=${search_LTE_policy_policyDate }&search_LIKE_policy.policyNo=${search_LIKE_policy_policyNo }&hasLetter=${encodeHasLetter }&encodeStatus=${encodeStatus == null?'null':encodeStatus }"><span>导出Excel</span></a></li>
			</shiro:hasPermission>
		</ul>
	</div>
	
	<table class="table" layoutH="210" width="200%">
		<thead>
			<tr>
				<th><input type="checkbox" group="ids" class="checkboxCtrl"></th>
				<th>保单机构</th>
				<th orderField=canCallAgain class="${page.orderField eq 'canCallAgain' ? page.orderDirection : ''}">可再访</th>
				<th>可再访备注</th>
				<th>重置电话</th>
				<th>重置日期</th>
				<th orderField=issueNo class="${page.orderField eq 'issueNo' ? page.orderDirection : ''}">工单编号</th>
				<th orderField=status class="${page.orderField eq 'status' ? page.orderDirection : ''}">工单状态</th>
				<th>工单内容</th>
				<th>二访工单内容</th>
				<th>拨打电话</th>
				<th orderField=operateTime class="${page.orderField eq 'operateTime' ? page.orderDirection : ''}">系统导入</th>
				<th style="width: 2%">离犹豫期/天</th>
				<th orderField=policy.policyNo class="${page.orderField eq 'policy.policyNo' ? page.orderDirection : ''}">所属保单号</th>
				<th>承保日期</th>
				<th>投保人</th>
				<th>联系电话</th>
				<th>险种名称</th>
				<th>出单网点</th>
				<th>处理类型</th>
				<th>处理详情</th>
				<th>经办人</th>
				<th orderField=organization.name class="${page.orderField eq 'organization.name' ? page.orderDirection : ''}">所属机构</th>
				<shiro:hasPermission name="Callfail:provEdit">
				<th orderField=hasLetter class="${page.orderField eq 'hasLetter' ? page.orderDirection : ''}">信函记录</th>
				<th>信函日期</th>
				<th>退信原因</th>
				<th>退信时间</th>
				<th>回邮时间</th>
				<th>客户签名时间</th>
				<th>通话号码</th>
				<th>通话开始时间</th>
				<th>通话结束时间</th>
				<th>通话时长</th>
				</shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${issues}">
			<tr target="slt_uid" rel="${item.id}">
				<td><input name="ids" value="${item.id}" type="checkbox"></td>
				<td>
				<c:choose>  
				    <c:when test="${fn:contains(item.policy.organization.name, '直属')}">  
				        <c:out value="${fn:replace(item.policy.organization.name,'邮政局直属中邮保险局','直属')}" />  
				    </c:when>  
				   <c:otherwise>  
				      <c:out value="${fn:replace(item.policy.organization.name,'邮政局中邮保险局','')}" />  
				    </c:otherwise>  
				</c:choose>
				</td>
				<td>${item.canCallAgain}</td>
				<td>${item.canCallAgainRemark}</td>
				<td>${item.resetPhone}</td>
				<td><fmt:formatDate value="${item.resetDate }" pattern="yyyy-MM-dd"/></td>
				<td>${item.issueNo}</td>
				<td>${item.status}</td>
				<td>${item.issueContent}</td>
				<td title="${item.hqDealRst}">${fn:substring(item.hqDealRst, 0, 15)}</td>
				<td>${item.phoneNum}</td>
				<td><fmt:formatDate value="${item.operateTime }" pattern="yyyy-MM-dd"/></td>
				<td><span style="color:red; height:50%; margin-bottom:-contentheight;"><c:if test="${item.lastDateNum<0 }">0</c:if><c:if test="${item.lastDateNum>=0 }">${item.lastDateNum }</c:if></span></td>
				<td>${item.policy.policyNo}</td>
				<td><fmt:formatDate value="${item.policy.policyDate }" pattern="yyyy-MM-dd"/></td>
				<td>${item.policy.holder}</td>
				<td>${item.holderMobile eq ""?item.holderPhone:item.holderMobile}</td>
				<td>${item.policy.prodName}</td>
				<td>
					<c:choose>  
					    <c:when test="${fn:length(item.bankName) > 14}">  
					        <c:out value="${fn:substring(item.bankName, 14, 30)}" />  
					    </c:when>  
					   <c:otherwise>
					      <c:out value="${item.bankName}" />  
					    </c:otherwise>  
					</c:choose>
				</td>
				<td>${item.dealType}</td>
				<td>${item.dealDesc}</td>
				<td>${item.dealMan}</td>
				<td>${fn:replace(item.organization.name,'邮政局中邮保险局','')}</td>
				<shiro:hasPermission name="Callfail:provEdit">
				<td>${item.hasLetter}</td>
				<td><fmt:formatDate value="${item.letterDate }" pattern="yyyy-MM-dd"/></td>
				<td>${item.mailFailReason}</td>
				<td><fmt:formatDate value="${item.mailFailDate }" pattern="yyyy-MM-dd"/></td>
				<td><fmt:formatDate value="${item.mailBackDate }" pattern="yyyy-MM-dd"/></td>
				<td><fmt:formatDate value="${item.clientSignDate }" pattern="yyyy-MM-dd"/></td>
				<td>${item.phoneNum}</td>
				<td>${item.phoneStart}</td>
				<td>${item.phoneEnd}</td>
				<td>${item.phoneTime}</td>
				</shiro:hasPermission>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<dwz:pagination page="${page }" targetType="dialog"/>
</div>