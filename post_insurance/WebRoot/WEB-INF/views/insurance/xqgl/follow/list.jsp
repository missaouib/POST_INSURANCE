<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.util.Date"%>
<%@ include file="/WEB-INF/views/include.inc.jsp"%>

<dwz:paginationForm action="${contextPath }/xqgl/follow/list" page="${page }">
	<input type="hidden" name="search_GTE_policy.policyFee" value="${search_GTE_policy_policyFee }"/>
	<input type="hidden" name="search_LTE_policy.policyFee" value="${search_LTE_policy_policyFee }"/>
	<input type="hidden" name="orgCode" value="${policy_orgCode }"/>
	<input type="hidden" name="name" value="${policy_name }"/>
	<input type="hidden" name="search_LTE_policy.policyDate" value="${search_LTE_policy_policyDate }"/>
	<input type="hidden" name="search_GTE_policy.policyDate" value="${search_GTE_policy_policyDate }"/>
	<input type="hidden" name="search_LIKE_policy.policyNo" value="${search_LIKE_policy_policyNo }"/>
	<input type="hidden" name="search_LIKE_policy.formNo" value="${search_LIKE_policy_formNo }"/>
	<input type="hidden" name="feeFrequency" value="${param.feeFrequency }"/>
	<%-- <input type="hidden" name="search_LTE_perm" value="${param.search_LTE_perm }"/> --%>
	<input type="hidden" name="status" value="${param.status }"/>
	<input type="hidden" name="followStatus" value="${param.followStatus }"/>
	<input type="hidden" name="prd.prdFullName" value="${prd_name }"/>
	<input type="hidden" name="attachedFlag" value="${attachedFlag }"/>
	<input type="hidden" name="staffFlag" value="${staffFlag }"/>
	<input type="hidden" name="duration" value="${duration }"/>
	<input type="hidden" name="saleChannel" value="${saleChannel }"/>
	<input type="hidden" name="search_LIKE_policy.holder" value="${search_LIKE_policyholder}"/>
	<input type="hidden" name="holderPhone" value="${holderPhone}"/>
	<input type="hidden" name="netFlag" value="${netFlag}"/>
</dwz:paginationForm>

<form method="post" id="hfForm" action="${contextPath }/xqgl/follow/list" onsubmit="return navTabSearch(this)">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						<label>?????????</label><input type="text" id="pf1" style="width: 50px;" name="search_GTE_policy.policyFee" value="${search_GTE_policy_policyFee }"/>&nbsp;-&nbsp;<input type="text" id="pf2" style="width: 50px;" name="search_LTE_policyFee" value="${param.search_LTE_policyFee }"/>
					</td>
					<td>
						<label>???????????????</label>
						<form:select path="policy.status" id="policyStatus" class="combox">
							<form:option value=""> -- -- </form:option>
							<form:option value="??????">??????</form:option>
							<form:option value="??????">??????</form:option>
							<form:option value="????????????">????????????</form:option>
							<form:option value="??????">??????</form:option>
						</form:select>
					</td>
					<td>
						<label>?????????</label>
						<input name="orgCode" id="uw_orgCode" type="hidden" value="${policy_orgCode }"/>
						<input class="validate[required] required" name="name" id="uw_orgName" type="text" readonly="readonly" style="width: 120px;" value="${policy_name }"/><a class="btnLook" href="${contextPath }/management/security/user/lookup2org" lookupGroup="" title="????????????" width="400">???</a>
					</td>
					<td>
					<label>????????????</label><input type="text" id="policy_holder" style="width: 100px;" name="search_LIKE_policy.holder" value="${search_LIKE_policy_holder }"/>
					</td>
					<td>
					<label>???????????????</label><input type="text" id="ctNum" style="width: 20px;" name="ctNum" value="${ctNum }"/>
					</td>
				</tr>
				<tr>
					<td>
						<label>????????????</label><input type="text" id="hfPolicyNo" style="width: 100px;" name="search_LIKE_policy.policyNo" value="${search_LIKE_policy_policyNo }"/>
					</td>
					<td><label>???????????????</label>
						<form:select path="follow.followStatus" id="followStatus" class="combox">
							<form:option value=""> -- -- </form:option>
							<form:option value="NewStatus">??????</form:option>
							<form:option value="FeedStatus">?????????</form:option>
							<form:option value="CTStatus">?????????</form:option>
						</form:select>
					</td>
					<td>
						<label>???????????????</label>
						<input type="text" name="search_GTE_policy.policyDate" id="cpDate1" class="date validate[required] required" style="width: 80px;" dateFmt="yyyy-MM-dd" readonly="true" value="${search_GTE_policy_policyDate }"/><a class="inputDateButton" href="javascript:;">???</a>
					</td>
					<td>
						<label>???????????????</label>
						<input type="text" name="search_LTE_policy.policyDate" id="cpDate2" class="date validate[required] required" style="width: 80px;" dateFmt="yyyy-MM-dd" readonly="true" value="${search_LTE_policy_policyDate }"/><a class="inputDateButton" href="javascript:;">???</a>
					</td>
					<td>
						<label>?????????</label>
						<input name="prd.prdFullName" type="text" postField="search_LIKE_prdName" suggestFields="prdFullName" class="input-medium validate[required,maxSize[32]] required"
					suggestUrl="/common/lookupPrdSuggest" lookupGroup="prd" value="${prd_name }"/>
					</td>
				</tr>
				<tr>
					<td>
						<label>???????????????</label><input type="text" id="hfformno" style="width: 100px;" name="search_LIKE_policy.formNo" value="${search_LIKE_policy_formNo }"/>
					</td>
					<td>
						<label>???/?????????</label>
					<form:select path="policy.feeFrequency" id="cpFeeFrequency" class="combox">
						<form:option value="">  --  </form:option>
						<form:option value="??????"> ?????? </form:option>
						<form:option value="??????"> ?????? </form:option>
					</form:select>
					</td>
					<td>
						<label>??????????????????</label>
						<form:select path="policy.staffFlag" id="cpastaffFlag" class="combox">
							<form:option value=""> -- -- </form:option>
							<form:option value="0">???</form:option>
							<form:option value="1">???</form:option>
						</form:select>
					</td>
					<td>
						<label>?????????</label>
						<form:select path="policy.netFlag" id="cpanetflag" class="combox">
							<form:option value="">  --  </form:option>
							<form:option value="1"> ???????????? </form:option>
							<form:option value="2"> ???????????? </form:option>
						</form:select>
					</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>
						<label>?????????</label><input type="text" id="cl_hphone" style="width: 100px;" name="holderPhone" value="${holderPhone }"/>
					</td>
					<td>
						<label>????????????</label><input type="text" id="cl_card" style="width: 100px;" name="search_LIKE_policyDtl.holderCardNum" value="${search_LIKE_policy_policyDtl_holderCardNum }"/>
					</td>
					<td>
						<label>????????????</label>
						<form:select path="policy.duration" id="cpadurationFlag" class="combox">
							<form:option value="0">??????</form:option>
							<form:option value="10">?????????</form:option>
						</form:select>
					</td>
					<td>
						<label>???????????????</label>
					<form:select path="policy.saleChannel" id="cpcsstype" class="combox">
						<form:option value=""> -- </form:option>
						<form:option value="8144">????????????</form:option>
						<form:option value="7644">????????????</form:option>
						<form:option value="8644">????????????</form:option>
						<form:option value="9644">?????????</form:option>
					</form:select>
					</td>
					<td>&nbsp;</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>						
					<li><div class="button"><div class="buttonContent"><button type="submit">??????</button></div></div></li>
				</ul>
			</div>
		</div>
	</div>
</form>

<div class="pageContent">

	<div class="panelBar">
		<ul class="toolBar">
			<shiro:hasPermission name="RenewedFollow:view">
				<li><a iconClass="magnifier" target="dialog" rel="follow_edit" mask="true" width="820" height="520" href="${contextPath }/xqgl/follow/view/{slt_uid}"><span>??????????????????</span></a></li>
			</shiro:hasPermission>
			<li class="line">line</li>
			<shiro:hasPermission name="RenewedFollow:edit">
				<li><a class="edit" target="dialog" rel="follow_edit" mask="true" width="820" height="520" href="${contextPath }/xqgl/follow/update/{slt_uid}"><span>????????????</span></a></li>
			</shiro:hasPermission>
				<li class="line">line</li>
				<li><a class="icon" onclick="javascript:urlCheckOverDate(${page.getTotalCount() },92, $('#cpDate1').val(),$('#cpDate2').val(),'${contextPath }/xqgl/follow/toXls?followStatus=${followStatus}&search_GTE_policyFee=${param.search_GTE_policyFee }&search_LTE_policyFee=${param.search_LTE_policyFee }&orgCode=${policy_orgCode }&search_LTE_policyDate=${param.search_LTE_policyDate }&search_GTE_policyDate=${param.search_GTE_policyDate }&feeFrequency=${param.feeFrequency }&prd.prdFullName=${prd_name }&search_LIKE_policyNo=${param.search_LIKE_policyNo }&search_LIKE_formNo=${param.search_LIKE_formNo }&duration=${duration }&encodeStatus=${encodeStatus == null?'null':encodeStatus }&staffFlag=${staffFlag}&attachedFlag=${attachedFlag}&saleChannel=${saleChannel}&ctNum=${ctNum}&netFlag=${netFlag }');"><span>??????Excel</span></a></li>
		</ul>
	</div>
	
	<table class="table" layoutH="210" width="100%">
		<thead>
			<tr>
				<th><input type="checkbox" group="ids" class="checkboxCtrl"></th>
				<th>????????????</th>
				<th>????????????</th>
				<th orderField=policy.policyNo class="${page.orderField eq 'policy.policyNo' ? page.orderDirection : ''}">?????????</th>
				<th orderFieldpolicy.organization.shortName class="${page.orderField eq 'policy.organization.shortName' ? page.orderDirection : ''}">??????</th>
				<th>?????????</th>
				<th>?????????</th>
				<th>????????????</th>
				<th orderField=policy.policyFee class="${page.orderField eq 'policy.policyFee' ? page.orderDirection : ''}">??????</th>
				<th>????????????</th>
				<th>????????????</th>
				<th>????????????</th>
				<th>????????????</th>
				<th>????????????</th>
				<th>??????</th>
				<th>?????????</th>
				<th>?????????</th>
				<th>????????????</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${policies}">
			<tr target="slt_uid" rel="${item.id}">
				<td><input name="ids" value="${item.id}" type="checkbox"></td>
				<td>
				<c:choose>
					<c:when test="${item.status eq 'NewStatus'}">
						<div style="color: red;vertical-align:middle;font-weight:bold;">?????????</div>
					</c:when>
					<c:when test="${item.status eq 'FeedStatus'}">
						<div style="color: blue;vertical-align:middle;font-weight:bold;">?????????</div>
					</c:when>
					<c:otherwise>
						?????????
					</c:otherwise>
				</c:choose>
				</td>
				<td><fmt:formatDate value="${item.followDate }" pattern="yyyy-MM-dd"/></td>
				<td><a target="dialog" rel="view" mask="true" width="820" height="520" href="${contextPath }/client/view/byPolicyNo/${item.policy.policyNo}"><div style="color: blue;vertical-align:middle;font-weight:normal;">${item.policy.policyNo}</div></a></td>
				<td>${item.policy.organization.shortName}</td>
				<td>${item.policy.holder}</td>
				<td>${item.policy.insured}</td>
				<td>${item.policy.prodName}</td>
				<td>${item.policy.policyFee}</td>
				<td>${item.policy.perm}</td>
				<td>${item.policy.policyDtl.duration}</td>
				<td><fmt:formatDate value="${item.policy.policyDate }" pattern="yyyy-MM-dd"/></td>
				<td>${item.policy.status}</td>
				<td>${item.policy.csDate != null?item.policy.csDate:""}</td>
				<td>${item.policy.bankName}</td>
				<td>${item.policy.staffFlag}</td>
				<td>${item.policy.bankCode!=null && item.policy.bankCode.netFlag==2?"???":"???" }</td>
				<td>******${fn:substring(item.policy.policyDtl.holderCardNum,6,17)}</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<!-- ?????? -->
	<dwz:pagination page="${page }"/>
</div>