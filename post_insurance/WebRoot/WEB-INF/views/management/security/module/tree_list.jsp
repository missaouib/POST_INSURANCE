<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>    
<div class="pageContent">
	<div class="tabs">
		<div class="tabsContent">
			<div>
				<table>
					<tr>
						<td width="300px">
							<div layoutH="5" id="jbsxBox2moduleTree" style="float:left; display:block; overflow:auto; width:300px; border:solid 1px #CCC; line-height:21px; background:#fff;">
								<c:import url="/management/security/module/tree"/>
							</div>
						</td>
						<td width="100%">
							<div layoutH="0" id="jbsxBox2moduleList" class="unitBox">
								<c:import url="/management/security/module/list/1"/>
							</div>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</div>