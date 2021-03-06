<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<script type="text/javascript">
<!--
function toTips(val) {
	if(val=="net") {
		alert("hi, 请选择具体地市县机构 ~~");
		return false;
	}
}
//-->
</script>
<script src="${contextPath}/js/echarts.min.js"></script>
<form method="post" id="hfForm" action="${contextPath }/component/stastics/policy" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
					<label>所属机构：</label>
					<input name="orgCode" id="uw_orgCode" type="hidden" value="${orgCode }"/>
					<input class="validate[required] required" name="name" id="uw_orgName" type="text" readonly="readonly" style="width: 120px;" value="${name }"/><a class="btnLook" href="${contextPath }/management/security/user/lookup2org" lookupGroup="" title="选择机构" width="400">查</a>
					</td>
					<td>承保日期起：
					<input type="text" name="policyDate1" id="uwDate1" class="date validate[required] required" style="width: 80px;" dateFmt="yyyy-MM-dd" value="${policyDate1 }"/><a class="inputDateButton" href="javascript:;">选</a>
					</td>
					<td>承保日期止：
					<input type="text" name="policyDate2" id="uwDate2" class="date validate[required] required" style="width: 80px;" dateFmt="yyyy-MM-dd" value="${policyDate2 }"/><a class="inputDateButton" href="javascript:;">选</a>
					</td>
					<td><label>产品：</label>
					<form:select path="PolicyStatModel.prdCode" id="prdCode" class="combox">
						<form:option value=""> -- </form:option>
						<form:options items="${prds }" itemLabel="prdName" itemValue="prdCode"/>
					</form:select>
					</td>
					<td><label>趸/期缴：</label>
					<form:select path="PolicyStatModel.perm" id="pperm" class="combox">
						<form:option value="">  --  </form:option>
						<form:option value="1"> 年交 </form:option>
						<form:option value="0"> 趸交 </form:option>
					</form:select>
					</td>
				</tr>
				<tr>
					<td><label>标记：</label>
					<form:select path="PolicyStatModel.levelFlag" id="plevelflag" class="combox" onchange="javascript:toTips(this.value);">
						<form:option value="prov"> 省级 </form:option>
						<form:option value="city"> 市级 </form:option>
						<form:option value="net"> 网点 </form:option>
					</form:select>
					</td>
					<td><label>银邮：</label>
					<form:select path="PolicyStatModel.netFlag" id="pnetflag" class="combox">
						<form:option value="">  --  </form:option>
						<form:option value="1"> 邮政代理 </form:option>
						<form:option value="2"> 银行自营 </form:option>
					</form:select>
					</td>
					<td>
					<label>员工单标记：</label>
					<form:select path="PolicyStatModel.staffFlag" id="pstaffflag" class="combox">
						<form:option value="">  --  </form:option>
						<form:option value="0"> 普通客户 </form:option>
						<form:option value="1"> 员工单 </form:option>
					</form:select>
					</td>
					<td>
					<label>统计类型：</label>
					<form:select path="PolicyStatModel.statType" id="pstatflag" class="combox">
						<form:option value="Organ">按机构</form:option>
						<form:option value="Prod">按产品 </form:option>
						<form:option value="feeType">缴费类型 </form:option>
					</form:select>
					</td>
					<td>
					<label>剔除犹撤：</label>
					<form:select path="PolicyStatModel.csFlag" id="pcsflag" class="combox">
						<form:option value="1">剔除犹撤</form:option>
						<form:option value="9">不剔除 </form:option>
					</form:select>
					</td>
				</tr>
				<tr>
					<td>
					<label>出单方式：</label>
					<form:select path="PolicyStatModel.saleType" id="pcsstype" class="combox">
						<form:option value="%%"> -- </form:option>
						<form:option value="8144%">手机销售</form:option>
						<form:option value="7644%">网银销售</form:option>
						<form:option value="8644%">柜面出单</form:option>
						<form:option value="9644%">老手机</form:option>
					</form:select>
					</td>
					<td>
						<label>保单状态：</label>
						<form:select path="PolicyStatModel.status" id="pcsstatus" class="combox">
							<form:option value="%%"> -- </form:option>
							<form:option value="有效">有效</form:option>
							<form:option value="终止">终止</form:option>
						</form:select>
					</td>
					<td>
					<td>
					<label>长期险：</label>
						<form:select path="PolicyStatModel.duration" id="psdurationFlag" class="combox">
							<form:option value="0">全部</form:option>
							<form:option value="10">长期险</form:option>
						</form:select>
					</td>
					<td>
					<label>最低保费：</label>
					<input type="text" name="pFee" id="ps_policyFee" value="${pFee }"/>
					</td>
					<td>&nbsp;</td>
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
<h2 class="contentTitle"><label>统计结果</label>
<a class="buttonActive" target="_blank" href="${contextPath }/component/stastics/policy/toXls?duration=${duration }&orgCode=${orgCode }&policyDate1=${policyDate1 }&policyDate2=${policyDate2 }&csDate1=${csDate1 }&csDate2=${csDate2 }&prdCode=${prdCode}&levelFlag=${levelFlag}&netFlag=${netFlag}&perm=${perm}&staffFlag=${staffFlag}&csFlag=${csFlag}&statType=${statType}&saleType=${saleType2}&status=${status}&pFee=${pFee}"><span>导出统计结果</span></a>
</h2>
<br>
<div class="pageContent" layoutH="150" width="150%">
<div class="row" style="padding: 0 3px;">
	<div style="width:30%;border:1px solid #e66;margin:5px;float:left;min-height:100px">
	<h2 class="contentTitle">列表展示（单位：万元）注： &nbsp;&nbsp;&nbsp;&nbsp;</h2>
		<table class="table" width="100%">
		<thead>
			<tr>
				<th>名称</th>
				<th>件数</th>
				<th>件数占比</th>
				<th>保费</th>
				<th>保费占比</th>
				<th>已保费</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${cmRst}" varStatus="idx">
			<tr>
				<td>
					<c:choose>  
					    <c:when test="${fn:contains(item.statName, '直属')}">  
					        <c:out value="${fn:replace(item.statName,'邮政局直属中邮保险局','直属')}" />  
					    </c:when>  
					   <c:when test="${fn:contains(item.statName, '邮政局中邮保险局')}">  
					      <c:out value="${fn:replace(item.statName,'邮政局中邮保险局','')}" />  
					    </c:when>
					    <c:otherwise>  
					      <c:out value="${item.statName}" />  
					    </c:otherwise>
					</c:choose>
				</td>
				<td style="text-align: right;"><fmt:formatNumber value="${item.policyCount-item.jzhCount}" pattern="#,###.#" /></td>
				<td style="text-align: right;"><fmt:formatNumber value="${(item.policyCount-item.jzhCount)/countPt*100}" pattern="#,###.##" />%</td>
				<td style="text-align: right;"><fmt:formatNumber value="${item.policyFee}" pattern="#,###.#" /></td>
				<td style="text-align: right;"><fmt:formatNumber value="${item.policyFee/sumPt*100}" pattern="#,###.##" />%</td>
				<td style="text-align: right;"><fmt:formatNumber value="${item.hadPolicyFee}" pattern="#,###.##" /></td>
			</tr>
			</c:forEach>
			<tr>
				<td>合计：</td>
				<td style="text-align: right;"><fmt:formatNumber value="${countPt}" pattern="#,###.#" /></td>
				<td>&nbsp;</td>
				<td style="text-align: right;"><fmt:formatNumber value="${sumPt}" pattern="#,###.#" /></td>
				<td>&nbsp;</td>
				<td style="text-align: right;"><fmt:formatNumber value="${sumHadPolicyFee}" pattern="#,###.##" /></td>
			</tr>
		</tbody>
	</table>
	</div>
	<div style="width:65%;border:1px solid #e66;margin:5px;float:left;min-height:10px">
	<div id="policyStatMain" style="width: 800px;height:500px;"></div>
	    <script type="text/javascript">
	        // 基于准备好的dom，初始化echarts实例
	        var myChart = echarts.init(document.getElementById('policyStatMain'));
			
	        option = {
	            tooltip: {
	                trigger: 'axis',
	                axisPointer: {
	                    type: 'cross',
	                    crossStyle: {
	                        color: '#999'
	                    }
	                }
	            },
	            toolbox: {
	                feature: {
	                    dataView: {show: true, readOnly: false},
	                    magicType: {show: true, type: ['line', 'bar']},
	                    restore: {show: true},
	                    saveAsImage: {show: true}
	                }
	            },
	            legend: {
	                data:['件数','保费','件数占比','保费占比']
	            },
	            xAxis: [
	                {
	                    type: 'category',
	                    data: [${col}],
	                    axisPointer: {
	                        type: 'shadow'
	                    }
	                }
	            ],
	            yAxis: [
	                {
	                    type: 'value',
	                    name: '数量',
	                    min: 0,
	                    max: ${maxTB},
	                    interval: 500,
	                    axisLabel: {
	                        formatter: '{value}'
	                    }
	                },
	                {
	                    type: 'value',
	                    name: '占比',
	                    min: 0,
	                    max: ${maxZB},
	                    interval: 10,
	                    axisLabel: {
	                        formatter: '{value} %'
	                    }
	                }
	            ],
	            series: [
	                {
	                    name:'件数',
	                    type:'bar',
	                    data:[${countStr}]
	                },
	                {
	                    name:'保费',
	                    type:'bar',
	                    data:[${sumStr}]
	                },
	                {
	                    name:'件数占比',
	                    type:'line',
	                    yAxisIndex: 1,
	                    data:[${countPtStr}]
	                },
	                {
	                    name:'保费占比',
	                    type:'line',
	                    yAxisIndex: 1,
	                    data:[${sumPtStr}]
	                }
	            ]
	        };
	
	        // 使用刚指定的配置项和数据显示图表。
	        myChart.setOption(option);
	    </script>
	</div>
</div>
</div>