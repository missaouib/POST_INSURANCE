<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>    
<div class="pageContent">
	<div class="tabs">
		<div class="tabsContent">
			<div>
				<table>
					<tr>
						<td width="500px">
							<div layoutH="5" id="jbsxBox2organizationTree" style="float:left; display:block; overflow:auto; width:500px; border:solid 1px #CCC; line-height:21px; background:#fff;">
								<c:import url="/management/security/organization/tree"/>
							</div>
						</td>
						<td width="100%">
							<div layoutH="0" id="jbsxBox2organizationList" class="unitBox">
								<c:import url="/management/security/organization/list/1"/>
							</div>
						</td>
					</tr>
				</table>				
			</div>
		</div>
	</div>
</div>