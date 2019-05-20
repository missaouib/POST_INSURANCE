<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
	<div class="pageFormContent" layoutH="58">
	<fieldset>
		<legend>更新日志信息</legend>
		<textarea layoutH="80" style="width:98%">
			20190521:
			1、填写不合格件增加申诉和删除功能；
			2、修复其他小问题。
			20190429 13:00:：
			1、团险理赔案件跟进RC；
			2、完善日志的查询
			20190428 23:00:：
			1、团险理赔案件跟进alpha；
			2、增加调查任务的反馈特性。
			20190425:：
			1、修复客户信息真实性排查的一些bug，修复邮保通柜面出单数据跟进的bug。
			20190423:：
			1、优化了各个模块中计算持续时间的计算：理赔任务、人核件、客服工单、填写不合格件、录入不合格件、咨询工单；
			2、对客服工单、填写不合格件、录入不合格件、咨询工单增加关闭日期和关闭用户名记录字段。
			20190404:：
			1、咨询工单管理RC（未完成授权角色处理和状态查看功能）。
		</textarea>
	</fieldset>
	</div>
	<div class="formBar">
		<ul>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</div>