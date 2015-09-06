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
								<li><a href="/web/prd/ffy3/yb" target="dialog" max="true" rel="dlg_f1" width="800" height="480" ><span>样本</span></a></li>
								<li><a href="/web/prd/ffy3/yb2" target="dialog" max="true" rel="dlg_f12" width="800" height="480" ><span>详细样本</span></a></li>
								<li><a href="/web/prd/yhzz/yb" target="dialog" max="true" rel="dlg_yhzz" width="800" height="480" ><span>银行自动转账授权书样本</span></a></li>
								<li><a href="/web/prd/ffy3/sell" target="_blank" max="true" rel="dlg_f2"><span>销售</span></a></li>
							</ul>
						</div>
					</div>
					<div class="tabsContent" style="height:280px;">
						<div>
						<div class="mytable">
						<table class="mytable" layoutH="230" width="98%">
						  <tr>
						    <td width="20%" rowspan="8">产品特色</td>
						    <td width="80%"><div align="left" class="my">期限适中，服务更贴心</div>
						    <div align="left">保险责任期间为6年，满足您对于中长期保险产品的需求。</div></td>
						  </tr>
						  <tr>
						    <td><div align="left" class="my">保障全面，生活更放心</div>
						    <div align="left">提供了实用、全面的人身保障服务，满足您对人身保障的需求。</div></td>
						  </tr>
						  <tr>
						    <td><div align="left" class="my">覆盖广泛，家人更称心</div>
						    <div align="left">趸交或3年交时投保年龄为出生满30天至70周岁，6年交时：出生满30天至50周岁 ，满足您与家人不同人生阶段的保险需求。</div></td>
						  </tr>
						  <tr>
						    <td><div align="left" class="my">保值增值，理财更舒心</div>
						    <div align="left">满期得到全面的人身保障与丰厚的满期保险金之外，将更有机会享受保险期间内公司经营运作产生的红利，满足您的理财需求。</div></td>
						  </tr>
						  <tr>
						    <td><div align="left" class="my">保单借款，用钱更省心</div>
						    <div align="left">可以申请保险单质押借款，缓解您资金短缺的燃眉之急。</div></td>
						  </tr>
						  <tr>
						    <td><div align="left" class="my">交费灵活，选择更悦心</div>
						    <div align="left">趸交（一次性交清）、3年交、6年交三种不同的交费方式，根据您的实际情况，为您提供多种选择。</div></td>
						  </tr>
						</table>
						</div>
						</div>
						<div>
						<!-- td -->
						<div data-role="collapsible" style="display:none">
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
							    <td colspan="2">为保证新契约承保件的客户信息真实性和完整性，<span class="my">投保单中的填写项目不得出现漏填、错填及涂改现象，由于投保人原因导致的涂改应指导修改人（即投保人）在修改处旁边亲笔签字确认，不得出现除投保人以外的人员修改投保单中的客户信息。</span>具体要求如下</td>
						      </tr>
							  <tr>
							    <td>以下信息必须<span class="myred">正确填写，不得出现差错</span>：</td>
							    <td><span class="myred">姓名,职业,出生日期、证件号码、国籍、证件有效期至、受益人信息,险种名称、投保份数、交费期间、保险期间、保险费、小额贷款保险金额、投保告知、投保人抄录项、投保单签名日期、提示书签名日期、回执签名日期、代理机构签章</span></td>
						      </tr>
							  <tr>
							    <td>为保证整单有效性，以下信息只可以修改两处：</td>
							    <td class="my">职业代码、电话/手机、联系地址、邮编、保险费合计、交费账户信息、受益顺序、受益比例、被保险人与投保人关系、受益人与被保险人关系、贷款机构、借款合同编号、借款金额、借款期间</td>
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
					    <td width="81%"><span class="my">中邮富富余3号两全保险（分红型）</span></td>
					  </tr>
					  <tr>
					    <td>投保单</td>
					    <td>必须正确使用<span class="my">富富余3号号投保书</span>，<span class="myred">条款</span>为独立印刷单独一本。<br>
					      如选购附加险，必须使用2016版投保单。</td>
					  </tr>
					  <tr>
					    <td>20万或以上大额件</td>
					    <td>大额件必须留出客户身份证复印件，卡折复印件，如果被保险人非投保人本人需要关系证明，如指定受益人，也需关系证明。（反洗钱要求，与省分行稽核要求一致）</td>
					  </tr>
					  <tr>
					    <td rowspan="13">填单要点</td>
					  </tr>
					  <tr>
					    <td><span class="myred">注：60周岁以上客户期交或65周岁以上客户趸交需要走人工核保，邮保通限制承保出单。<br>
					      广东：60周岁或以上必须进行录音录像且转人工核保。    </span></td>
					  </tr>
					  <tr>
					    <td>险种名称可以直接填写：<span class="my">富富余1号</span></td>
					  </tr>
					  <tr>
					    <td>附加险：如交费方式为<span class="my">年交</span>时，<span class="myred">可附加：附加意外伤害医疗</span><br>
					附加意外医疗伤害附加险以保险金额销售，必须使用2016版投保单，<span class="my">份数处填写保险金额</span>，最低1000元，最高不能超过首期保费且为1000的整数倍，且最高不超过20000元。<br>
					每1000元基本保险金额保险费为10元。</td>
					  </tr>
					  <tr>
					    <td><span class="my">职业请填写最高类别的职业，</span>如建筑水电工（室内），职业代码为：0701015</td>
					  </tr>
					  <tr>
					    <td>基本保险金额可以不用填写。</td>
					  </tr>
					  <tr>
					    <td>份数：2份起售 每份500元</td>
					  </tr>
					  <tr>
					    <td>交费期间：趸交（一次性交清）、3年交、6年交<br>
					      <span class="my">更新承保规则：</span><br>
					      趸交或3年交时：出生满30天至70周岁<br>
					      6年交时：出生满30天至50周岁 </td>
					  </tr>
					  <tr>
					    <td>保险期间：6年</td>
					  </tr>
					  <tr>
					    <td>告知事项在投保单背面，由投保人阅读告知事项并亲自在告知栏勾选实际情况</td>
					  </tr>
					  <tr>
					    <td><span class="my">投保人抄录语句项必须由投保人亲笔抄写</span></td>
					  </tr>
					  <tr>
					    <td>投保人与被保险人分别需要本人亲笔签名，不得由他人代签名</td>
					  </tr>
					  <tr>
					    <td>如被保险人为<span class="myred">未成年人</span>（未满18岁），被保险人签名处由其<span class="myred">监护人</span>（如父母）亲笔签名（监护人姓名）</td>
					  </tr>
					  <tr>
					    <td colspan="2">备注：未成年人保额限制根据保监会规定。</td>
					  </tr>
					</table>
					</div>
					</div>
					<div data-role="collapsible">
					            <h3>给客户的承保资料 	&nbsp;&nbsp;&nbsp;&nbsp;<span class="ui-li-count">6项</span></h3>
					            <p>
					                <li>共6项：<br>
					                  <span class="myred">1、保险单；<br>
					                  2、投保单（客户联）（2011版或2016版，如带附加险必须2016版）；<br>3、投保提示书（投保书附带的那份）；<br>
					                  4、产品说明书+保险条款；<br>5、收费凭证；<br>
					                  6、银行自动转账授权书（客户联）。</span> <br>以上资料放保单夹中连同保单夹一同给客户。</li>
					            </p>
					        </div>
					        <div data-role="collapsible">
					            <h3>归档资料	&nbsp;&nbsp;&nbsp;&nbsp;<span class="ui-li-count">6项</span></h3>
					            <p>
					                <li>共6项：<br><span class="myred">1、投保单（公司联）（2011版或2016版，如带附加险必须2016版）；<br>2、投保提示书；<br>
					                3、中邮人寿保险股份有限公司银行保险投保书补充声明（单联）（如使用2016版投保单不需提供）；<br>4、银行自动转账授权书（公司联）；<br>
					                5、合同回执（如使用2016版投保单不需提供）；<br>
					                  6、身份证复印件、卡折复印件（每单留存），或相关关系证明。<br><br>特殊情况下：<br></span></li>
					                  1、<span class="my">邮保通出单承保提示书</span>（适用于邮保通出单无手机号码客户，可由网点人员填写）；<br>
					2、<span class="my">核心系统出单承保提示书</span>（适用于转人核件无手机号码的客户，可由网点人员填写）<br>
					3、<span class="my">投保单填写授权委托书</span>（适用于填写有困难或者文化程度低的客户，在客户签名授权后，可由受托人员代为填写投保单）
					            </p>
					        </div>
						<!-- end of td -->
						</div>
						<div>
						<!-- hb -->
						<div class="mytable">
						<table width="100%" class="mytable">
						  <tr>
						    <td colspan="2" align="center"><span class="my">投保规则</span></td>
						  </tr>
						  <tr>
						    <td width="16%">承保年龄</td>
						    <td width="84%">趸交或3年交时：出生满30天至70周岁<br>
						      6年交时：出生满30天至50周岁
						      <br>
						    <span class="myred">注：60周岁以上客户期交或65周岁以上趸交需要走人工核保，邮保通限制承保出单。<br>
						    <span>广东：60周岁或以上必须进行录音录像且转人工核保。</span>    </span></td>
						  </tr>
						  <tr>
						    <td width="16%">保险费</td>
						    <td>每份500元，2份起售</td>
						  </tr>
						  <tr>
						    <td width="16%">交费方式</td>
						    <td>趸交（一次性交清）、3年交、6年交</td>
						  </tr>
						  <tr>
						    <td align="center">附加险</td>
						    <td>如交费方式为<span><span class="my">年交</span></span>时，<span><span class="myred">可附加：附加意外伤害医疗</span></span><br>
						      附加意外医疗伤害附加险以保险金额销售，必须使用2016版投保单，份数处填写保险金额，最低1000元，不能超过首期保费且为1000的整数倍，最高不超过20000元。<span>每1000元保险金额保险费为10元。<br>
						      <span class="my">附加险有60日宽限期。</span>      </span></td>
						  </tr>
						  <tr>
						    <td width="16%"> 保险期间</td>
						    <td>6年</td>
						  </tr>
						  <tr>
						    <td align="center"><span>备注</span></td>
						    <td>未成年人保额限制根据保监会规定。</td>
						  </tr>
						  <tr>
						    <td rowspan="4"><span class="my">保险责任：在本合同保险责任期间内，我公司承担下列保险责任</span></td>
						    <td><<span class="my">（一）满期保险金：</span><br>
						    保险责任期间届满时，被保险人仍然生存，我公司按本合同约定的基本保险金额乘以交费年度数给付&ldquo;满期保险金&rdquo;，本合同效力终止。</td>
						  </tr>
						  <tr>
						    <td><span class="my">（二）身故、全残保险金：</span><br>
						      &nbsp;&nbsp;&nbsp;被保险人自保险责任开始日或本合同复效日（以较迟者为准）起<span>1年内因非意外原因</span>导致身故或全残，我公司将按照本合同约定的基本保险金额与105%倍首期保费的较大者给付“意外身故、意外全残保险金”，本合同效力终止。<br>
						      被保险人自保险责任开始日或本合同复效日（以较迟者为准）起<span>1年后因非意外原因</span>导致身故或全残，我公司将按照本合同约定的基本保险金额乘以身故或全残时的交费年度数给付“身故、全残保险金”，本合同效力终止.</td>
						  </tr>
						  <tr>
						    <td><span><span class="my">（三）意外身故、全残保险金：</span></span><br>
						      &nbsp;&nbsp;&nbsp;&nbsp;被保险人在保险责任期间内，因遭受意外伤害事故，并自事故发生之日起180日内（含第180日）以该事故为直接且单独的原因导致身故或全残，我公司将按照本合同约定的基本保险金额乘以身故或全残时的交费年度数的2倍给付&ldquo;意外身故、意外全残保险金&rdquo;，本合同效力终止。<br>
						&nbsp;&nbsp;&nbsp;自事故发生之日起180日内（含第180日）治疗仍未结束的，我公司将按被保险人自意外伤害发生日起第180日的身体情况进行鉴定，并据此决定是否给付&ldquo;意外全残保险金&rdquo;。</td>
						  </tr>
						  <tr>
						    <td><span class="my"><span>（四）交通意外身故、全残保险金：</span></span><br>
						      &nbsp;&nbsp;&nbsp;&nbsp;被保险人以乘客身份搭乘合法商业运营的指定公共交通工具（见释义8）期间因遭受意外伤害事故，并自事故发生之日起180日内（含第180日）以该事故为直接且单独的原因导致身故或全残，我公司将按照本合同约定的基本保险金额乘以身故或全残时的交费年度数的3倍给付&ldquo;交通意外身故、交通意外全残保险金&rdquo;，本合同效力终<br>
						&nbsp;&nbsp;&nbsp;&nbsp;自事故发生之日起180日内（含第180日）治疗仍未结束的，我公司将按被保险人自意外伤害发生日起第180日的身体情况进行鉴定，并据此决定是否给付&ldquo;交通意外全残保险金&rdquo;。</td>
						  </tr>
						</table>
						<br>
						<span class="mycenter">基本保险金额表</span>
						  <br>
						  <span style="text-align:center">中邮富富余3号两全保险（分红型）保险金额表</span><br>
						  <span class="mycenter">（每1000元保险费）</span>
						<div align="right">单位：人民币元</div>
						<table width="95%" class="mytable">
						  <tbody>
						      <tr>
						        <td width="149" nowrap="nowrap">       交费方式</td>
						        <td width="109" rowspan="2">趸交</td>
						        <td width="108" rowspan="2">3年交</td>
						        <td width="195" rowspan="2">6年交</td>
						      </tr>
						      <tr>
						        <td width="149" nowrap="nowrap">年龄</td>
						      </tr>
						      <tr>
						        <td width="149" nowrap="nowrap">0-40</td>
						        <td width="109">1080</td>
						        <td width="108">1062</td>
						        <td width="195">1032</td>
						      </tr>
						      <tr>
						        <td width="149" nowrap="nowrap">41-50</td>
						        <td width="109">1079</td>
						        <td width="108">1061</td>
						        <td width="195">1032</td>
						      </tr>
						      <tr>
						        <td width="149" nowrap="nowrap">51-55</td>
						        <td width="109">1078</td>
						        <td width="108">1060</td>
						        <td width="195">1031</td>
						      </tr>
						      <tr>
						        <td width="149" nowrap="nowrap">56-60</td>
						        <td width="109">1076</td>
						        <td width="108">1059</td>
						        <td width="195">1031</td>
						      </tr>
						      <tr>
						        <td width="149" nowrap="nowrap">61-65</td>
						        <td width="109">1073</td>
						        <td width="108">1057</td>
						        <td width="195">1029</td>
						      </tr>
						      <tr>
						        <td width="149" nowrap="nowrap">66-70</td>
						        <td width="109">1068</td>
						        <td width="108">1053</td>
						        <td width="195">1027</td>
						      </tr>
						    </tbody>
						  </table>
						</div>
						<!-- end of hb -->
						</div>
						<div>限额</div>
						<div>样本</div>
						<div>详细样本</div>
						<div>银行自动转账授权书填写样本</div>
						<div>销售辅助工具</div>
					</div>
				</div>
		</div>
</div>