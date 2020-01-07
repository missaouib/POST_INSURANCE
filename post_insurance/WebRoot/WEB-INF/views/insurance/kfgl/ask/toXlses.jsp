<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="UTF-8">
<%
response.setContentType("application/vnd.ms-excel");  
response.setHeader("Content-Disposition", "inline; filename=Inquires_xls.xls");
%>

<table border="1" cellspacing="1" cellpadding="0">
  <tr>
    <td align="center"><p>___<u>${fn:substring(orgName, 0, 2)}</u>___中邮保险局问题件清单</p></td>
  </tr>
  <tr>
    <td>工单转办/下发意见：
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
签名：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日期：</td>
  </tr>
  <tr>
    <td>工单转办/下发处理结果（由省分相关处理部门或机构填写）：<br />
        <br>
        经办人签字：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日期：
    <td>
    </tr>
    <tr>
    <td>  
	处理意见：
            <br />
        <br>
          负责人签字：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          日期
   </td>
   </tr>
   <tr>
   <td>
      处理结果（由地市、县在以下列表中逐件进行回复）
          <br>
          <br>
          <br>处理意见（由地市、县负责人填写并签章确认）：<br />
          <br>
          负责人签字：
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          日期：
    </td>
  </tr>
  <tr>
    <td><table border="1" cellspacing="1" cellpadding="0">
    <thead>
      <tr>
        <th>机构名称</th>
        <th>工单标记</th>
        <th>流水号</th>
        <th>保单号</th>
        <th>客户姓名</th>
        <th>工单子类</th>
        <th>工单内容</th>
        <th>网点名称</th>
        <th>联系电话</th>
        <th>手机号码</th>
        <th>处理结果</th>
        <th>经办人员</th>
        <th>审核人员</th>
        <th>处理日期</th>
      </tr>
      </thead>
      <tbody>
      <c:forEach var="item" items="${reqs}">
		<tr>
			<td>${item.organName}</td>
			<td>
					<c:choose>
                        <c:when test="${not empty item.policyNos}">
                        	<div style="color: blue;vertical-align:middle;">个险</div>
                        </c:when>
                        <c:when test="${not empty item.gpolicyNo}">
                        	<div style="color: blue;vertical-align:middle;">团险</div>
                        </c:when>
                       <c:otherwise>个险团险 </c:otherwise>
                    </c:choose>
				</td>
			<td>${item.inquireNo}</td>
			<td style="vnd.ms-excel.numberformat:@">${item.policy.policyNo}</td>
			<td>${item.client}</td>
			<td>${item.inquireSubtype}</td>
			<td>${item.inquireDesc}</td>
			<td>
			<c:choose>  
			    <c:when test="${fn:length(item.netName) > 14}">  
			        <c:out value="${fn:substring(item.netName, 14, 30)}" />  
			    </c:when>  
			   <c:otherwise>  
			      <c:out value="${item.policy.bankName}" />  
			    </c:otherwise>  
			</c:choose>
			</td>
			<td style="vnd.ms-excel.numberformat:@">${item.clientPhone1}</td>
			<td style="vnd.ms-excel.numberformat:@">${item.clientPhone2}</td>
			<td>${item.inquireRst}</td>
			<td>${item.dealMan}</td>
			<td>${item.checker}</td>
			<td>${item.dealTime}</td>
		</tr>
		</c:forEach>
		</tbody>
    </table></td>
  </tr>
</table>
