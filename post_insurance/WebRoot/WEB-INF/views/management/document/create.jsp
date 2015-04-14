<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<div class="pageContent">
	<div class="tabs">
		<div class="tabsContent">
			<div>
				<table>
					<tr>
						<td width="300px">
							<div layoutH="5" id="pageMemberTree" style="float:left; display:block; overflow:auto; width:300px; border:solid 1px #CCC; line-height:21px; background:#fff;">
								<c:import url="/management/document/tree"/>
							</div>
						</td>
						<td width="100%">
							<div layoutH="0" id="pageResourceList" class="unitBox">
								<c:import url="/management/document/resource"/>
							</div>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</div>