<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<meta http-equiv=”X-UA-Compatible” content=”IE=edge,chrome=1″ />
<meta charset="UTF-8">
<%
response.setContentType("application/vnd.ms-excel");  
response.setHeader("Content-Disposition", "inline; filename=call_fail_dtl.xls");
%>
	<table border="1" cellspacing="1" cellpadding="0">
			<tr>
				<th>序号</th>
                <th>保单机构</th>
                <th>事件编号</th>
                <th>保单号</th>
                <th>投保人</th>
                <th>联系电话</th>
                <th>市县反馈</th>
                <th>重置时间</th>
                <th>离犹豫期(天)</th>
                <!-- <th>工单内容</th> -->
                <th>${status eq "已退保"?"核心系统退保":"工单状态"}</th>
                <th>省分回访结果</th>
                <th>出单网点</th>
                <th>可再访</th>
                <th>出单日期</th>
                <th>回单日期</th>
                <th>待处理时间</th>
                <th>所属机构</th>
                <shiro:hasPermission name="Callfail:provEdit">
                <th>证件号码</th>
                </shiro:hasPermission>
                <th>联系地址</th>
                <th>险种名称</th>
                <th>重置电话</th>
                <th>市县回访类型</th>
                <th>市县回访详情</th>
                <th>市县回访经办</th>
                <th>市县回访时间</th>
                <th>二访日期</th>
                <th>二访类型</th>
                <th>二访详情</th>
                <th>二访人</th>
                <shiro:hasPermission name="Callfail:provEdit">
                <th>信函记录</th>
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
			<c:forEach var="item" items="${reqs}" varStatus="status">
			<tr>
				<td><c:out value="${status.index+1}"/></td>
                <td>${fn:replace(item.policy.organization.name,'邮政局中邮保险局','')}</td>
                <td>${item.issueNo}</td>
                <td style="vnd.ms-excel.numberformat:@">${item.policy.policyNo}</td>
                <td>${item.policy.holder}</td>
                <td>${item.holderMobile eq ""?item.holderPhone:item.holderMobile}</td>
                <td>${item.canCallAgainRemark}（<fmt:formatDate value="${item.resetDate }" pattern="yyyy-MM-dd"/>）</td>
                <td><fmt:formatDate value="${item.resetDate }" pattern="yyyy-MM-dd"/></td>
                <td><c:if test="${item.lastDateNum<0 }">-1</c:if><c:if test="${item.lastDateNum>=0 }">${item.lastDateNum }</c:if></td>
                <%-- <td>${item.issueContent}</td> --%>
                <td>${item.status}</td>
                <td>${item.hqDealTypeElse}</td>
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
                <td>${item.canCallAgain}</td>
                <td><fmt:formatDate value="${item.policy.policyDate}" pattern="yyyy-MM-dd"/></td>
                <td><fmt:formatDate value="${item.policy.billBackDate }" pattern="yyyy-MM-dd"/></td>
                <td><fmt:formatDate value="${item.readyDate }" pattern="yyyy-MM-dd"/></td>
                <td>${fn:replace(item.organization.name,'邮政局中邮保险局','')}</td>
                <shiro:hasPermission name="Callfail:provEdit">
                <td style="vnd.ms-excel.numberformat:@">${item.idCard}</td>
                </shiro:hasPermission>
                <td>${item.addr}</td>
                <td>${item.policy.prodName}</td>
                <td>${item.resetPhone}</td>
                <td>${item.dealType}</td>
                <td>${item.dealDesc}</td>
                <td>${item.dealMan}</td>
                <td><fmt:formatDate value="${item.dealTime }" pattern="yyyy-MM-dd"/></td>
                <td><fmt:formatDate value="${item.hqDealDate }" pattern="yyyy-MM-dd"/></td>
                <td>${item.hqDealType}</td>
                <td>${item.hqDealRst}</td>
                <td>${item.hqDealMan}</td>
                
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
	</table>
