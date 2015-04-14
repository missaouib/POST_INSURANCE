<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>
<a id="refreshUploaddataList" target="ajax" href="${contextPath}/management/queryuploaddata/list" style="display:none;"></a>
<dwz:paginationForm action="${contextPath }/management/queryuploaddata/list" page="${page }">
	<input type="hidden" name="search_EQ_ny" value="${param.search_EQ_ny }"/>
	<input type="hidden" name="search_EQ_tblMember.id" value="${search_EQ_tblMember_id }"/>
	<input type="hidden" name="search_EQ_tblMember.memberName" value="${search_EQ_tblMember_memberName }"/>
	<input type="hidden" name="search_LIKE_dm" value="${param.search_LIKE_dm }"/>
	<input type="hidden" name="search_LIKE_pl" value="${param.search_LIKE_pl }"/>
	<input type="hidden" name="search_LIKE_pm" value="${param.search_LIKE_pm }"/>
	<input type="hidden" name="search_LIKE_cj" value="${param.search_LIKE_cj }"/>
	<input type="hidden" name="search_LIKE_gg" value="${param.search_LIKE_gg }"/>		
</dwz:paginationForm>

<form method="post" action="${contextPath }/management/queryuploaddata/list" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<ul class="searchContent">
				<li style="width:200px;">
					<label style="width: 40px;">年月：</label>
					<select name="search_EQ_ny" id="search_EQ_ny" name="ny" style="width:120px;">
						<c:forEach var="item" items="${ny}">
						<option value="${item}" ${item == param.search_EQ_ny ? 'selected="selected"' : ''}>${item}</option>
						</c:forEach>
					</select>
				</li>
				<li style="width:180px;">
					<label style="width: 40px;">连锁：</label>
					<input name="search_EQ_tblMember.id" value="${search_EQ_tblMember_id }" type="text" style="display:none;"/>
					<input class="validate[required] required" name="search_EQ_tblMember.memberName" value="${search_EQ_tblMember_memberName}" type="text" readonly="readonly" style="width: 120px;"/>
				</li>
				<li style="width:40px;">
					<label style="width: 40px;"><a class="btnLook" href="${contextPath }/management/uploaddata/lookup2member" lookupGroup="search_EQ_tblMember" title="关联连锁">查找带回</a></label>
				</li>
				<li style="width:200px;">
					<label style="width: 40px;">分店：</label>
					<input type="text" name="search_LIKE_dm" value="${param.search_LIKE_dm }" style="width:120px;"/>
				</li>
			</ul>
			<ul class="searchContent">
				<li style="width:200px;">
					<label style="width: 40px;">品类：</label>
					<input type="text" name="search_LIKE_pl" value="${param.search_LIKE_pl }" style="width:120px;"/>
				</li>
				<li style="width:180px;">
					<label style="width: 40px;">品名：</label>
					<input type="text" name="search_LIKE_pm" value="${param.search_LIKE_pm }" style="width:120px;"/>
				</li>
				<li style="width:40px;">
				</li>
				<li style="width:200px;">
					<label style="width: 40px;">厂家：</label>
					<input type="text" name="search_LIKE_cj" value="${param.search_LIKE_cj }" style="width:120px;"/>
				</li>
				<li style="width:200px;">
					<label style="width: 40px;">规格：</label>
					<input type="text" name="search_LIKE_gg" value="${param.search_LIKE_gg }" style="width:120px;"/>
				</li>
			</ul>
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
		<shiro:hasPermission name="queryuploaddata:delete">
		<c:if test="${search_EQ_tblMember_id>0 and !empty items}">
			<li><a iconClass="group_delete" target="ajaxTodo" callback="dialogReloadRel2UploadData" href="${contextPath}/management/queryuploaddata/delete/${search_EQ_tblMember_id}/${param.search_EQ_ny}" title="确认要删除该连锁此月份数据?"><span>删除连锁月份数据</span></a></li>
		</c:if>
		</shiro:hasPermission>
		</ul>
	</div>
	
	<table class="table" layoutH="162" width="100%">
		<thead>
			<tr>
				<th width="80px">年月</th>
				<th width="80px">连锁</th>
				<th width="120px">分店</th>
				<th width="120px">药店编码</th>
				<th width="80px">城市</th>
				<th width="120px">品类大类</th>
				<th width="120px">品类</th>
				<th width="120px">品类小类</th>
				<th width="180px">品名</th>
				<th width="120px">药品编码</th>
				<th width="120px">医保</th>
				<th width="120px">处方药/非处方药</th>
				<th width="120px">厂家</th>
				<th width="100px">规格</th>
				<th width="60px">单价</th>
				<th width="60px">成本价</th>
				<th width="60px">毛利率</th>
				<th width="80px">销售量</th>
				<th width="80px">销售额</th>
				<th width="60px">进货量</th>
				<th width="80px">月初库存</th>
				<th width="80px">月末库存</th>
				<th width="80px">条码</th>
				<th width="80px">单位</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${items}">
			<tr target="slt_uid" rel="${item.id}">
				<td>${item.ny}</td>
				<td>${item.ls}</td>
				<td>${item.dm}</td>
				<td>${item.ydbm}</td>
				<td>${item.cs}</td>
				<td>${item.pldl}</td>
				<td>${item.pl}</td>
				<td>${item.plzl}</td>
				<td>${item.pm}</td>
				<td>${item.ypbm}</td>
				<td>${item.yb}</td>
				<td>${item.otc}</td>
				<td>${item.cj}</td>
				<td>${item.gg}</td>
				<td>${item.dj}</td>
				<td>${item.cbj}</td>
				<td>${item.mll}</td>
				<td>${item.xsl}</td>
				<td>${item.xse}</td>
				<td>${item.jhl}</td>
				<td>${item.yckc}</td>
				<td>${item.ymkc}</td>
				<td>${item.tm}</td>
				<td>${item.dw}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<!-- 分页 -->
	<dwz:pagination page="${page }"/>
</div>
