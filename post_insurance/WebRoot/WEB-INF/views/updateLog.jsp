﻿<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<div class="pageContent">
	<div class="pageFormContent" layoutH="58">
	<fieldset>
		<legend>更新日志信息</legend>
		<textarea layoutH="80" style="width:98%">
20200612:
1，优化数据同步上传批量一次导入，以及保全月报问题数据跳过。
2、修复其他bug
20200523:
1，限制导出时长。
20200519:
1，修复其他bug。
20200515:
1，增加人核件指标分析和导出功能。
2、修复其他bug。
20200513:
1，优化客真重复查询效率。
2、优化员工单检测，增加年份对应。
3、修复其他bug。
20200508:
1，脱敏账号显示。
2、修复其他bug。
20200417:
1，员工数据更新。
2、更新2020年运营质量考核指标导入，显示。
3、增加多次退保计算，及更新到保单列，可导出。
4、修复其他bug。
20200410:
1，网点数据更新。
2、修正客真合格率的统计分析bug。
3、咨询工单催办功能。
4、问题工单导出word模板更新。
5、修复其他bug。
20200409:
1，增加满期领取进度分析统计。
2、增加客户信息真实性综合合格率查询分析功能。
3、增加人核件时效数据同步功能，并优化了人核件明细导出数据，增加分析字段导出。
4、优化统计分析的图表显示功能。
5、修复其他bug。
20200331:
1，增加合同打印信息查询使用组合合同号查询。
2、优化双主险数据的处理（填写录入不合规矩、保单分析、回访等数据都处理了）。
3、修复其他bug。
20200205:
1，增加客真信息重复保单的查看功能。
2、优化回访功能。
3、新增回访成功率的查询。
20200107:
1，修复人核件收费失败提示缺陷。
2、增加人核件回执补扫描登记功能。
3、增加柜面单档案扫描导出功能。
20191228:
1，增加对重复地址、电话和Email的校验。
2、修复电话查询的bug。
3、升级spring、hibernate等框架
20191024:
1，增加手工单时效详单导入，同步补充漏登记人核件数据。
2、优化人核件回单数据，新增表记录回单信息。
3、增强xls，xlsx的数据读取功能
20191016:
1，修复邮银渠道分析导出差错错误的bug。
2、增加支撑市场部、个团部的监管台账下载功能。
20191016:
1，修复统计整改状态占比的bug。
2、增强客真的区分邮银渠道分析。
3、增加满期终止的判断，哪怕客户没有满期保全申请，仍然置为满期终止！
20190930:
1，更新员工单信息（2019年度）。
2、优化登录提示、登录效率、查询效率。
3、祝祖国生日快乐！（成立70周年）
20190920:
1，收付费失败记录合并在一个数据库表。
2、更新保单机构名称和核心一致（保险中心）。
20190905:
1，修复纸质申请率和客真合格率统计口径不一致的问题。
2、修复统计模块的纸质申请率的统计bug。
20190721:
1，新契约综合合格率计算合格率并显示。
20190709:
1，考核指标数据查询功能增加机构选择，可选择全省其他地市机构查看。
20190709:
1，完成考核指标数据导入、查看和下载功能。
20190703:
1，增加了抽检、客真、回访分析中增加“长期险”查询条件。
20190620:
1，修复了咨询工单的相关bug。
2、优化统计分析功能，地市可以看到全省情况。
3、根据电子保单发送失败数据，优化Email的判断。
20191531:
再次优化了抽检分析和客户信息真实性的问题导出数据；修复咨询工单的相关bug；继续优化了其他一些小问题（安全要求）。
20191530:
优化了抽检分析和客户信息真实性的问题导出数据；增加了咨询工单的剩余处理时间和弹窗提醒；优化了其他一些小问题。
20191529:
优化了定时任务，准确处理客户信息真实排查对已经退保情况的关闭操作。
20191527:
增加了咨询工单的指派角色办理功能，重新定义了角色权限。
20190524：
1、增加抽检分析功能、客户信息真实性合格率统计功能、纸质保单申请率分析的功能
2、增强日志记录，下载全部记录日志
3、重构了DAO层，更加合理
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