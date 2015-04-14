<%@ page import="com.sendtend.web.entity.member.TblMember"%>
<%@ page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%> 
<%!
	public String tree(TblMember member) {
		if (member.getChildren().isEmpty()) {
			return "";
		}
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("<ul>" + "\n");
		for(TblMember m : member.getChildren()) {
			buffer.append("<li><a href=\"javascript:\" onclick=\"$.bringBack({id:'" + m.getId() + "', memberName:'" + m.getMemberName() + "'})\">" + m.getMemberName() + "</a>" + "\n");
			buffer.append(tree(m));
			buffer.append("</li>" + "\n");
		}
		buffer.append("</ul>" + "\n");
		return buffer.toString();
	}
%>
<%
TblMember member = (TblMember)request.getAttribute("member");
%>
<div class="pageContent">
	<div class="pageFormContent" layoutH="58">
		<ul class="tree expand">
			<li><a href="javascript:"><%=member.getMemberName() %></a>
				<%=tree(member) %>
			</li>
		</ul>
	</div>
	
	<div class="formBar">
		<ul>
			<li><div class="button"><div class="buttonContent"><button class="close" type="button">关闭</button></div></div></li>
		</ul>
	</div>
</div>