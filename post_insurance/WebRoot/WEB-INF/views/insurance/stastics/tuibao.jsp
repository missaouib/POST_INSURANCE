<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<script src="${contextPath}/js/echarts.common.min.js"></script>
<form method="post" id="hfForm" action="${contextPath }/component/stastics/tuibao" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
					所属机构：
					<input name="orgCode" id="uw_orgCode" type="hidden" value="${orgCode }"/>
					<input class="validate[required] required" name="name" id="uw_orgName" type="text" readonly="readonly" style="width: 120px;" value="${name }"/>
					<a class="btnLook" href="${contextPath }/management/security/user/lookup2org" lookupGroup="" title="选择机构" width="400">查</a>
					</td>
					<td>承保日期起：
					<input type="text" name="policyDate1" id="uwDate1" class="date validate[required] required" style="width: 80px;" dateFmt="yyyy-MM-dd" readonly="true" value="${policyDate1 }"/><a class="inputDateButton" href="javascript:;">选</a>
					</td>
					<td>承保日期止：
					<input type="text" name="policyDate2" id="uwDate2" class="date validate[required] required" style="width: 80px;" dateFmt="yyyy-MM-dd" readonly="true" value="${policyDate2 }"/><a class="inputDateButton" href="javascript:;">选</a>
					</td>
					<td><label>产品：</label>
				<form:select path="CommonModel.prdCode" id="prdCode" class="combox">
						<form:option value=""> -- </form:option>
						<form:options items="${prds }" itemLabel="prdName" itemValue="prdCode"/>
					</form:select>
					</td>
				</tr>
				<tr>
					<td><label>标记：</label>
					<form:select path="CommonModel.levelFlag" id="tbflag" class="combox">
						<form:option value="prov"> 省级 </form:option>
						<form:option value="city"> 市级 </form:option>
					</form:select>
					</td>
					<td>退保日期起：
					<input type="text" name="csDate1" id="uwDate1" class="date validate[required] required" style="width: 80px;" dateFmt="yyyy-MM-dd" readonly="true" value="${csDate1 }"/><a class="inputDateButton" href="javascript:;">选</a>
					</td>
					<td>退保日期止：
					<input type="text" name="csDate2" id="uwDate2" class="date validate[required] required" style="width: 80px;" dateFmt="yyyy-MM-dd" readonly="true" value="${csDate2 }"/><a class="inputDateButton" href="javascript:;">选</a>
					</td>
					<td><label>银邮：</label>
					<form:select path="CommonModel.netFlag" id="netflag" class="combox">
						<form:option value="">  --  </form:option>
						<form:option value="1"> 邮政代理 </form:option>
						<form:option value="2"> 银行自营 </form:option>
					</form:select>
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
<div class="row" style="padding: 0 10px;">
	<div class="sortDrag" style="width:30%;border:1px solid #e66;margin:5px;float:left;min-height:100px">
	<h2 class="contentTitle">列表展示（单位：万元）&nbsp;&nbsp;&nbsp;&nbsp;<a class="icon" target="_blank" href="${contextPath }/qygl/underwrite/toXls?search_LIKE_formNo=${param.search_LIKE_formNo }&orgCode=${orgCode }&status=${status }&search_LTE_sysDate=${param.search_LTE_sysDate }&search_GTE_sysDate=${param.search_GTE_sysDate }&search_EQ_provReceiveDate=${param.search_EQ_provReceiveDate}"><span>导出</span></a></h2>
		<table class="table" layoutH="220" width="100%">
		<thead>
			<tr>
				<th>序号</th>
				<th>机构名称</th>
				<th>退保费</th>
				<th>总保费</th>
				<th>占比</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${cmRst}" varStatus="idx">
			<tr target="slt_uid" rel="${item.organName}">
				<td>${idx.index+1 }</td>
				<td>${item.organName}</td>
				<td>${item.policyFee/10000}</td>
				<td>${item.sumPolicyFee/10000}</td>
				<td><fmt:formatNumber value="${item.policyFee/item.sumPolicyFee}" pattern="#,###.##" />%</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
	<div class="sortDrag" style="width:65%;border:1px solid #e66;margin:5px;float:left;min-height:100px">
	<div id="main" style="width: 800px;height:400px;"></div>
	    <script type="text/javascript">
	        // 基于准备好的dom，初始化echarts实例
	        var myChart = echarts.init(document.getElementById('main'));
	
	        // 指定图表的配置项和数据
	        var option = {
	            title: {
	                text: '退保预警图表'
	            },
	            tooltip: {},
	            legend: {
	                data:['占比']
	            },
	            xAxis: {
	                data: [${col}]
	            },
	            yAxis: {},
	            series: [{
	                name: '占比',
	                type: 'bar',
	                data: [${colData}]
	            }]
	        };
	
	        // 使用刚指定的配置项和数据显示图表。
	        myChart.setOption(option);
	    </script>
	</div>
</div>
</div>