<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<meta http-equiv=”X-UA-Compatible” content=”IE=edge,chrome=1″ />
<meta charset="UTF-8">
<%
response.setContentType("application/vnd.ms-excel");  
response.setHeader("Content-Disposition", "inline; filename=TO_FEE_SUCCESS_LIST.xls");
%>
<table align="center">
<tr>
<td colspan="8" align="center"><div style="font-size: 20; font-weight:bold">集中转账成功清单</div>
</td>
</tr>
<tr>
<td colspan="5">付费机构：中邮人寿保险股份有限公司 </td>
<td colspan="3">打印日期：<fmt:formatDate value="${date}" pattern="yyyy-MM-dd"/></td>
</tr>
<tr>
  <td colspan="5">    批次号：<br /></td>
  <td colspan="3" valign="top">    打印时间：<fmt:formatDate value="${date}" pattern="HH:mm"/></td>
  </tr>
<tr>
  <td colspan="5">银行名称：中国邮政储蓄银行<br /></td>
  <td>&nbsp;</td>
  <td>&nbsp;</td>
  <td>&nbsp;</td>
</tr>
<tr>
  <td colspan="5">保单类型：全部</td>
  <td>&nbsp;</td>
  <td>&nbsp;</td>
  <td>&nbsp;</td>
</tr>
<tr>
  <td>&nbsp;</td>
  <td>&nbsp;</td>
  <td>&nbsp;</td>
  <td>&nbsp;</td>
  <td>&nbsp;</td>
  <td>&nbsp;</td>
  <td>&nbsp;</td>
  <td>&nbsp;</td>
</tr>
</table>
	<table border="1" cellspacing="1" cellpadding="0">
			<tr>
				<th>管理机构</th>
				<th>账户名</th>
				<th>账号</th>
				<th>金额</th>
				<th>状态描述</th>
				<th>回盘日期</th>
				<th>费用类型</th>
				<th>关联业务号码</th>
			</tr>
			<c:forEach var="item" items="${paylists}">
			<tr target="slt_uid" rel="${item.id}">
				<td>${item.organization.name}</td>
				<td>${item.accountName}</td>
				<td style="vnd.ms-excel.numberformat:@">${item.account}</td>
				<td>${item.money}</td>
				<td>${item.failDesc}</td>
				<td><fmt:formatDate value="${item.backDate }" pattern="yyyy-MM-dd"/></td>
				<td>${item.failDesc}</td>
				<td>${item.relNo}</td>
			</tr>
			</c:forEach>
	</table>
	<table align="center">
	  <tr>
	    <td colspan="5"><br /></td>
	    <td colspan="3" valign="top">&nbsp;</td>
      </tr>
	  <tr>
	    <td colspan="5">操作员：</td>
	    <td>经办人签署：</td>
	    <td>&nbsp;</td>
	    <td>&nbsp;</td>
      </tr>
	  <tr>
	    <td>&nbsp;</td>
	    <td>&nbsp;</td>
	    <td>&nbsp;</td>
	    <td>&nbsp;</td>
	    <td>&nbsp;</td>
	    <td>&nbsp;</td>
	    <td>&nbsp;</td>
	    <td>&nbsp;</td>
      </tr>
</table>
