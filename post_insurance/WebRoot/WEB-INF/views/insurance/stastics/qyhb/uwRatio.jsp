<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<script type="text/javascript">
<!--
function toTips(val) {
	if(val=="city") {
		alert("hi! 请选择具体地市县机构！~~");
		return false;
	}
}
//-->
</script>
<form method="post" id="hfForm" action="${contextPath }/component/stastics/uwRatio" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
					<label>所属机构：</label>
					<input name="orgCode" id="uwr_orgCode" type="hidden" value="${orgCode }"/>
					<input class="validate[required] required" name="name" id="uwr_orgName" type="text" readonly="readonly" style="width: 120px;" value="${name }"/><a class="btnLook" href="${contextPath }/management/security/user/lookup2org" lookupGroup="" title="选择机构" width="400">查</a>
					</td>
					<td>承保日期起：
					<input type="text" name="policyDate1" id="uwrDate1" class="date validate[required] required" style="width: 80px;" dateFmt="yyyy-MM-dd" value="${policyDate1 }"/><a class="inputDateButton" href="javascript:;">选</a>
					</td>
					<td>承保日期止：
					<input type="text" name="policyDate2" id="uwrDate2" class="date validate[required] required" style="width: 80px;" dateFmt="yyyy-MM-dd" value="${policyDate2 }"/><a class="inputDateButton" href="javascript:;">选</a>
					</td>
					<td><label>产品：</label>
					<form:select path="QyStatModel.prdCode" id="urwprdCode" class="combox">
						<form:option value=""> -- </form:option>
						<form:options items="${prds }" itemLabel="prdName" itemValue="prdCode"/>
					</form:select>
					</td>
					<td><label>趸/期缴：</label>
					<form:select path="QyStatModel.perm" id="uwrpperm" class="combox">
						<form:option value="">  --  </form:option>
						<form:option value="1"> 年交 </form:option>
						<form:option value="0"> 趸交 </form:option>
					</form:select>
					</td>
				</tr>
				<tr>
					<td><label>标记：</label>
					<form:select path="QyStatModel.levelFlag" id="uwrplevelflag" class="combox" onchange="javascript:toTips(this.value);">
						<form:option value="prov"> 省级 </form:option>
						<form:option value="city"> 市级 </form:option>
						<form:option value="net"> 网点 </form:option>
					</form:select>
					</td>
					<td><label>银邮：</label>
					<form:select path="QyStatModel.netFlag" id="uwrpnetflag" class="combox">
						<form:option value="">  --  </form:option>
						<form:option value="1"> 邮政代理 </form:option>
						<form:option value="2"> 银行自营 </form:option>
					</form:select>
					</td>
					<td>
					<label>员工单标记：</label>
					<form:select path="QyStatModel.staffFlag" id="uwrpstaffflag" class="combox">
						<form:option value="">  --  </form:option>
						<form:option value="0"> 普通客户 </form:option>
						<form:option value="1"> 员工单 </form:option>
					</form:select>
					</td>
					<td>
					<label>统计类型：</label>
					<form:select path="QyStatModel.statType" id="uwrpstatflag" class="combox">
						<form:option value="Organ">按机构</form:option>
						<form:option value="Prod">按产品 </form:option>
						<form:option value="feeType">缴费类型 </form:option>
					</form:select>
					</td>
					<td>
					<label>剔除犹撤：</label>
					<form:select path="QyStatModel.csFlag" id="uwrpcsflag" class="combox">
						<form:option value="1">剔除犹撤</form:option>
						<form:option value="9">不剔除 </form:option>
					</form:select>
					</td>
				</tr>
				<tr>
					<td>
					<label>出单方式：</label>
					<form:select path="QyStatModel.saleType" id="uwrpcsstype" class="combox">
						<form:option value="%%"> -- </form:option>
						<form:option value="8144%">手机销售</form:option>
						<form:option value="7644%">网银销售</form:option>
						<form:option value="8644%">柜面出单</form:option>
						<form:option value="9644%">老手机</form:option>
					</form:select>
					</td>
					<td>
						<label>保单状态：</label>
						<form:select path="QyStatModel.status" id="uwrpcsstatus" class="combox">
							<form:option value="%%"> -- </form:option>
							<form:option value="有效">有效</form:option>
							<form:option value="终止">终止</form:option>
						</form:select>
					</td>
					<td>
					<td>
					<label>长期险：</label>
						<form:select path="QyStatModel.duration" id="uwrpsdurationFlag" class="combox">
							<form:option value="0">全部</form:option>
							<form:option value="10">长期险</form:option>
						</form:select>
					</td>
					<td>&nbsp;</td>
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
<div class="pageContent">

	<div class="panelBar">
		<ul class="toolBar">
			<li class="line">line</li>
			<li><a class="icon" target="_blank" href="${contextPath }/component/stastics/uwRatio/toXls?duration=${duration }&orgCode=${orgCode }&policyDate1=${policyDate1 }&policyDate2=${policyDate2 }&csDate1=${csDate1 }&csDate2=${csDate2 }&prdCode=${prdCode}&levelFlag=${levelFlag}&netFlag=${netFlag}&perm=${perm}&staffFlag=${staffFlag}&csFlag=${csFlag}&statType=${statType}&saleType=${saleType2}&status=${status}"><span>导出统计结果</span></a></li>
		</ul>
	</div>
	<div id="w_list_print">
		<table class="table" layoutH="175" width="80%">
		<thead>
			<tr>
				<th>序号</th>
				<th>机构名称</th>
				<th>耗时时长</th>
				<th>人核件件数</th>
				<th>回执回销件数</th>
				<th>5日作业完成件数</th>
				<th>5日回销件数</th>
				<th>回执回销率</th>
				<th>5日作业完成率</th>
				<th>5日回执回销率</th>
				<th>全流程时效</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${cmRst}" varStatus="idx">
			<tr>
				<td>${idx.index+1 }</td>
				<td>${item.organName}</td>
				<td style="text-align: right;font-weight:800;"><fmt:formatNumber value="${item.sumdays}" pattern="#,###" /></td>
				<td style="text-align: right;font-weight:800;"><fmt:formatNumber value="${item.policyCounts}" pattern="#,###" /></td>
				<td style="text-align: right;font-weight:800;"><fmt:formatNumber value="${item.huixiaoCounts}" pattern="#,###" /></td>
				<td style="text-align: right;font-weight:800;"><fmt:formatNumber value="${item.job5ds}" pattern="#,###" /></td>
				<td style="text-align: right;font-weight:800;"><fmt:formatNumber value="${item.huixiao5ds}" pattern="#,###.#" /></td>
				<td style="text-align: center;"><fmt:formatNumber type="percent" maxFractionDigits="2" value="${item.huixiaoCounts/item.policyCounts  }" /></td>
				<td style="text-align: center;"><fmt:formatNumber type="percent" maxFractionDigits="2" value="${item.job5ds/item.policyCounts  }" /></td>
				<td style="text-align: center;"><fmt:formatNumber type="percent" maxFractionDigits="2" value="${item.huixiao5ds/item.huixiaoCounts  }" /></td>
				<td style="text-align: center;"><fmt:formatNumber pattern="#,###.##" value="${item.sumdays/item.policyCounts  }" /></td>
			</tr>
			</c:forEach>
			<tr>
				<td>&nbsp;</td>
				<td>合计：</td>
				<td style="text-align: right;font-weight:800;"><fmt:formatNumber value="${sumdays}" pattern="#,###.#" /></td>
				<td style="text-align: right;font-weight:800;"><fmt:formatNumber value="${policyCounts}" pattern="#,###.#" /></td>
				<td style="text-align: right;font-weight:800;"><fmt:formatNumber value="${huixiaoCounts}" pattern="#,###.#" /></td>
				<td style="text-align: right;font-weight:800;"><fmt:formatNumber value="${job5ds}" pattern="#,###.#" /></td>
				<td style="text-align: right;font-weight:800;"><fmt:formatNumber value="${huixiao5ds}" pattern="#,###.#" /></td>
				<td style="text-align: center;"><fmt:formatNumber type="percent" maxFractionDigits="2" value="${hxRatio }" /></td>
				<td style="text-align: center;"><fmt:formatNumber type="percent" maxFractionDigits="2" value="${job5dsRatio }" /></td>
				<td style="text-align: center;"><fmt:formatNumber type="percent" maxFractionDigits="2" value="${hx5dsRatio }" /></td>
				<td style="text-align: center;"><fmt:formatNumber pattern="#,###.##" value="${qlc }" /></td>
			</tr>
		</tbody>
	</table>
	</div>
</div>