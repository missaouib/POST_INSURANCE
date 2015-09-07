<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
<!--
$("#background,#progressBar").hide();
//-->
</script>
<link rel="stylesheet" href="${contextPath}/styles/post/css/jquery.mobile-1.3.2.min.css" type="text/css"></link>
<script src="${contextPath}/styles/post/js/jquery.mobile-1.3.2.min.js"></script>
<style type="text/css">
	.mytable table{border:0px solid #F00;border-collapse: collapse;height: 100%}
	.mytable table tr{border:1px solid #FFF;padding: 0.3em 1em;} 
	.mytable table td{border:1px solid #F00;padding: 0.3em 1em;height: 16px;line-height: 18px;}
	.mytable table span {
	font-size:14px;
	line-height: 18px;
	}
	.mytable span {
	font-size:14px;
	line-height: 18px;
	}
	.mytable table td p{}
	.mytable p{}
</style>
<div class="pageContent">
		<div class="tabsContent" style="height:70;">
				<div class="tabs" currentIndex="0" eventType="click" style="width:760px">
					<div class="tabsHeader">
						<div class="tabsHeaderContent">
							<ul>
								<li><a href="javascript:;"><span>介绍</span></a></li>
								<li><a href="javascript:;"><span>填单</span></a></li>
								<li><a href="javascript:;"><span>核保</span></a></li>
								<li><a href="javascript:;"><span>限额</span></a></li>
								<li><a href="/web/prd/bbb/yb" target="dialog" max="true" rel="dlg_f1" width="800" height="480" ><span>样本</span></a></li>
								<li><a href="/web/prd/bbb/yb2" target="dialog" max="true" rel="dlg_f12" width="800" height="480" ><span>详细样本</span></a></li>
								<li><a href="/web/prd/yhzz/yb" target="dialog" max="true" rel="dlg_yhzz" width="800" height="480" ><span>银行自动转账授权书样本</span></a></li>
								<li><a href="/web/prd/bbb/sell" target="_blank" max="true" rel="dlg_f2"><span>销售</span></a></li>
							</ul>
						</div>
					</div>
					<div class="tabsContent" style="height:345px;">
						<div>
						<div class="mytable">
						<table class="mytable" layoutH="230" width="98%">
						  <tr>
						    <td width="16%" rowspan="2">产品特色</td>
						    <td width="84%"><strong>保障高——1份投入 3重保障 160倍关爱 </strong><br>
						      <br>
						      保障责任可选，可根据自己的出行习惯任意选择，享受最高可达160倍的自驾车（含航空）或公共交通（含航空）保障的同时，还拥有高达16倍的一般意外保障，行业领先。 
						      保障范围广，保险期限可长可短，四种方式任您选。 
						    最高保障高，额度可达240万元，为您的生活和出行保驾护航</td>
						  </tr>
						  <tr>
						    <td><strong>保本好——满期保险金额高于所交保费 </strong><br>
						      <br>
						      手续更简便，高额保障无需体检 
						      购买更安心，犹豫期15天 
						    变现更灵活，保单借款高达90%</td>
						  </tr>
						</table>
						</div>
						</div>
						<div>
						<div data-role="collapsible">
						<h3>填单要求</h3>
						<div class="mytable">
							<table width="100%" class="mytable">
							  <tr>
							    <td colspan="2" align="center">填单要求</td>
						      </tr>
							  <tr>
							    <td colspan="2">差错类型：漏填，漏选，涂改，填写错误，与关联信息不符，其他错误</td>
						      </tr>
							  <tr>
							    <td colspan="2">为保证新契约承保件的客户信息真实性和完整性，<strong class="my">投保单中的填写项目不得出现漏填、错填及涂改现象，由于投保人原因导致的涂改应指导修改人（即投保人）在修改处旁边亲笔签字确认，不得出现除投保人以外的人员修改投保单中的客户信息</strong>。具体要求如下</td>
						      </tr>
							  <tr>
							    <td>以下信息必须<strong><span class="myred">正确填写，不得出现差错</span></strong>：</td>
							    <td><strong class="myred">姓名,职业,出生日期、证件号码、国籍、证件有效期至、受益人信息,险种名称、投保份数、交费期间、保险期间、保险费、小额贷款保险金额、投保告知、投保人抄录项、投保单签名日期、提示书签名日期、回执签名日期、代理机构签章</strong></td>
						      </tr>
							  <tr>
							    <td>为保证整单有效性，以下信息只可以修改两处：</td>
							    <td class="my"><strong>职业代码、电话/手机、联系地址、邮编、保险费合计、交费账户信息、受益顺序、受益比例、被保险人与投保人关系、受益人与被保险人关系、贷款机构、借款合同编号、借款金额、借款期间</strong></td>
						      </tr>
							  <tr>
							    <td>为保证整单有效性，以下信息只可修改三处：</td>
							    <td>性别，有效证件名称，婚姻状况，基本保额，交费方式，生存保险金领取方式，红利领取方式，保险费缴纳方式，保费自动垫交，争议处理方式，备注/特约，代理机构其他信息</td>
						      </tr>
					  </table>
					  </div>
					</div>
					<div data-role="collapsible">
					  <h3>填单要点</h3>
					  <div class="mytable">
					<table width="100%" class="mytable">
					  <tr>
					    <td width="19%">险种名称</td>
					    <td colspan="3"><strong class="my">中邮年年好百倍保两全保险</strong>，注意险种名称有两种责任组合区别</td>
					  </tr>
					  <tr>
					    <td>投保单</td>
					    <td colspan="3">需要正确使用<strong class="my">2015版最新通用投保书</strong>，<span class="myred"><strong>条款</strong></span>为单独印刷本。<br>
					      <strong>如果附加附加意外伤害医疗附加险时必须使用<span class="myred">2016版</span>投保单。</strong></td>
					  </tr>
					  <tr>
					    <td>20万或以上大额件</td>
					    <td colspan="3">大额件必须留出客户身份证复印件，卡折复印件，如果被保险人非投保人本人需要关系证明，如指定受益人，也需关系证明。（反洗钱要求，与省分行稽核要求一致）</td>
					  </tr>
					  <tr>
					    <td rowspan="14">填单要点</td>
					    <td colspan="3"><span class="myred">注：60周岁以上客户期交或65周岁以上趸交需要走人工核保，邮保通限制承保出单。<br>
					        <strong>广东：60周岁或以上必须进行录音录像且转人工核保。</strong></span></td>
					  </tr>
					  <tr>
					    <td colspan="3"><strong><span class="myred">险种名称</span></strong>（<span class="my">两种责任组合</span>）：<br>
					      自驾方面，险种名称填写：<span class="my">百倍保<strong class="my">自驾航空责任组合</strong></span><br>
					      公共交通，险种名称填写：<span class="my">百倍保<strong class="my">公共交通责任组合</strong></span></td>
					  </tr>
					  <tr>
					    <td colspan="3"><strong>附加险</strong>：如交费方式为<strong><span class="my">年交</span></strong>时，<strong><span class="myred">可附加：附加意外伤害医疗</span></strong><br>
					附加意外医疗伤害附加险以保险金额销售，必须使用2016版投保单，<span class="my"><strong>份数处填写保险金额</strong></span>，最低1000元，最高不能超过首期保费且为1000的整数倍，且最高不超过20000元。<br>
					<strong>每1000元基本保险金额保险费为10元。<br>
					<span class="my">附加险有60日宽限期。</span></strong></td>
					  </tr>
					  <tr>
					    <td colspan="3"><strong>职业请填写最高类别的职业</strong>，<span class="my">如建筑水电工（室内），职业代码为：0701015</span><br>
					    五类及五类以上职业拒保！</td>
					  </tr>
					  <tr>
					    <td colspan="3">被保险人年龄：30天至60周岁</td>
					  </tr>
					  <tr>
					    <td colspan="3">基本保险金额可以不用填写。</td>
					  </tr>
					  <tr>
					    <td colspan="3"><p>新增<strong><span style="color:#FF0000">必填</span></strong>项目：<span class="myred">国籍、证件有效期<strong>至、个人收入、居民类型</strong></span>；<br>
					      其他新增填写项目：<br>
					  &nbsp;&nbsp;&nbsp;过去三年平均收入和收入来源（保费大于等于20万必填）；<br>
					  &nbsp;&nbsp;&nbsp;身高、体重。<br>
					    <span class="my"><strong>指定受益人时必须填写</strong></span><strong><span class="myred">受益人国籍、职业、电话和联系地址</span>！</strong></p></td>
					  </tr>
					  <tr>
					    <td colspan="3">份数：2份起，每份500元。</td>
					  </tr>
					  <tr>
					    <td width="10%" rowspan="3">交费<br>
					    期间</td>
					    <td width="12%">趸交</td>
					    <td width="59%">对应保险期间：5年</td>
					  </tr>
					  <tr>
					    <td>3年交</td>
					    <td>对应保险期间：6年</td>
					  </tr>
					  <tr>
					    <td>5年交</td>
					    <td>对应保险期间：10年/30年（保险期间30年只能40周岁以下投保）</td>
					  </tr>
					  <tr>
					    <td colspan="3">告知事项在投保单背面，由投保人阅读告知事项并<strong>亲自</strong>在告知栏勾选实际情况</td>
					  </tr>
					  <tr>
					    <td colspan="3">投保人与被保险人分别需要<strong>本人亲笔签名</strong>，不得由他人代签名</td>
					  </tr>
					  <tr>
					    <td colspan="3">如被保险人为<span class="myred"><strong>非成年人</strong></span>（未满18岁），被保险人签名处由其<strong class="myred">监护人</strong>（如父母）<strong class="myred">亲笔签名</strong>（监护人姓名）</td>
					  </tr>
					</table>
					</div>
					</div>
					<div data-role="collapsible">
					            <h3>给客户的承保资料	&nbsp;&nbsp;&nbsp;&nbsp;<span class="ui-li-count">5项</span></h3>
					            <p>
					                <li>共五项：<br><strong class="my"><span class="myred">1、保险单；<br>
					                2、投保单（客户联）（2015版或2016版，如有附加险必须2016版）；<br>3、投保提示书（投保书附带的那份）；<br>4、保险条款；<br>5、银行自动转账授权书（客户联）；<br>6、收费凭证。</span></strong><br> 以上资料放保单夹中连同保单夹一同给客户。</li>
					            </p>
					        </div>
					        <div data-role="collapsible">
					            <h3>归档资料	&nbsp;&nbsp;&nbsp;&nbsp;<span class="ui-li-count">6项</span></h3>
					            <p>
					                <li>共6项：<br>
					                  <strong class="my"><span class="myred">1、投保单（公司联）（2015版或2016版，如有附加险必须2016版）；<br>2、投保提示书；<br>
					                3、中邮人寿保险股份有限公司银行保险投保书补充声明（单联）（如使用2016版投保单不需提供）；<br>4、银行自动转账授权书（公司联）；<br>
					                5、合同回执（如使用2016版投保单不需提供）；<br>
					                  6、身份证复印件、卡折复印件（每单留存），或相关关系证明。<br><br>特殊情况下：<br></span></strong></li>
					            </p>
					        1、<strong class="my">邮保通出单承保提示书</strong>（适用于邮保通出单无手机号码客户，可由网点人员填写）；<br>
					2、<span class="my"><strong>核心系统出单承保提示书</strong></span>（适用于转人核件无手机号码的客户，可由网点人员填写）<br>
					3、<span class="my"><strong>投保单填写授权委托书</strong></span>（适用于填写有困难或者文化程度低的客户，在客户签名授权后，可由受托人员代为填写投保单）</div>
						</div>
						<div>
						<!-- hb -->
						<div class="mytable">
						<table width="100%" class="mytable">
						  <tr>
						    <td colspan="2"><div align="center"><strong>投保规则</strong></div></td>
						  </tr>
						  <tr>
						    <td width="16%"><div align="center">投保年龄</div></td>
						    <td width="84%">出生满30天至60周岁/出生满30天至40周岁<br>
						    <span class="myred">注：60周岁以上客户期交或65周岁以上趸交需要走人工核保，邮保通限制承保出单。<br>
						    <strong>广东：60周岁或以上必须进行录音录像且转人工核保。</strong></span></td>
						  </tr>
						  <tr>
						    <td width="16%"><div align="center">保险费</div></td>
						    <td>每份500元，<span class="my"><strong>2份起售</strong></span>（1000元）。</td>
						  </tr>
						  <tr>
						    <td width="16%"><div align="center">交费方式</div></td>
						    <td>趸交、3年交、5年交</td>
						  </tr>
						  <tr>
						    <td align="center">附加险</td>
						    <td>如交费方式为<strong><span class="my">年交</span></strong>时，<strong><span class="myred">可附加：附加意外伤害医疗</span></strong><br>
						      附加意外医疗伤害附加险以保险金额销售，必须使用2016版投保单，份数处填写保险金额，最低1000元，不能超过首期保费且为1000的整数倍，最高不超过20000元。<strong>每1000元保险金额保险费为10元。<br>
						      </strong><span class="my">附加险有60日宽限期。</span></td>
						  </tr>
						  <tr>
						    <td width="16%"> <div align="center">保险期间</div></td>
						    <td>5年、6年、10年/30年</td>
						  </tr>
						  <tr>
						    <td rowspan="2"><div align="center">保险责任</div></td>
						    <td><span class="my"><strong>（一）自驾航空责任组合</strong></span><strong><br>
						      </strong>&nbsp;&nbsp;&nbsp;1.满期保险金：<br>
						&nbsp;&nbsp;&nbsp;保险责任期间届满时，按照合同约定的基本保险金额领取<br>
						&nbsp;&nbsp;&nbsp;2.身故、全残保险金：<br>
						&nbsp;&nbsp;&nbsp;给付：1.05倍x所交保险费<br>
						&nbsp;&nbsp;&nbsp;3.意外身故、全残保险金：<br>
						&nbsp;&nbsp;&nbsp;未满18 周岁，给付：1.05倍x所交保险费<br>
						&nbsp;&nbsp;&nbsp;满18 周岁，给付：16倍x所交保险费<br>
						&nbsp;&nbsp;&nbsp;4.航空意外身故、全残保险金：<br>
						&nbsp;&nbsp;&nbsp;未满18 周岁，给付：1.05倍x所交保险费<br>
						&nbsp;&nbsp;&nbsp;满18 周岁，给付：160倍x所交保险费<br>
						&nbsp;&nbsp;&nbsp;5.自驾车意外身故、全残保险金：<br>
						&nbsp;&nbsp;&nbsp;未满18 周岁，给付：1.05倍x所交保险费<br>
						&nbsp;&nbsp;&nbsp;满18 周岁，给付：160倍x所交保险费    </td>
						  </tr>
						  <tr>
						    <td><strong class="my">（二）公共交通责任组合：</strong><br>
						&nbsp;&nbsp;&nbsp;1.满期保险金：<br>
						&nbsp;&nbsp;&nbsp;保险责任期间届满时，按照合同约定的基本保险金额领取<br>
						&nbsp;&nbsp;&nbsp;2.身故、全残保险金：<br>
						&nbsp;&nbsp;&nbsp;给付：1.05倍x所交保险费<br>
						&nbsp;&nbsp;&nbsp;3.意外身故、全残保险金：<br>
						&nbsp;&nbsp;&nbsp;未满18 周岁，给付：1.05倍x所交保险费<br>
						&nbsp;&nbsp;&nbsp;满18 周岁，给付：16倍x所交保险费<br>
						&nbsp;&nbsp;&nbsp;4.公共交通意外身故、全残保险金：<br>
						&nbsp;&nbsp;&nbsp;未满18 周岁，给付：1.05倍x所交保险费<br>
						&nbsp;&nbsp;&nbsp;满18 周岁，给付：160倍x所交保险费<br>
						&nbsp;&nbsp;&nbsp;*除投保人在投保时约定外，本合同默认的责任组合为公共交通责任组合。</td>
						  </tr>
						</table>
						</div>
						<!-- end of hb -->
						</div>
						<div>样本</div>
						<div>详细样本</div>
						<div>银行自动转账授权书填写样本</div>
						<div>销售辅助工具</div>
					</div>
				</div>
		</div>
</div>