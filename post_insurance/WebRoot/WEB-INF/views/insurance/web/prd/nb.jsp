<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
<!--
$("#background,#progressBar").hide();
//-->

function mycount() {
		var copies =  $("#copies").val();
		var payTerm = $('input[name="payTerm"]:checked').val();
		var brst;
		var fjrst;
		if(payTerm == 3) {
			brst = copies * 1000 * 920 / 1000;
			fjrst = copies * 1000 * 80 / 1000;
		} else {
			brst = copies * 1000 * 950 / 1000;
			fjrst = copies * 1000 * 50 / 1000;
		}
		$("#brst")[0].value=brst;
		$("#fj")[0].value=fjrst;
		return true;
	}
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
								<li><a href="/web/prd/nb/yb" target="dialog" max="true" rel="dlg_f1" width="800" height="480" ><span>样本</span></a></li>
								<li><a href="/web/prd/nb/yb2" target="dialog" max="true" rel="dlg_f12" width="800" height="480" ><span>详细样本</span></a></li>
								<li><a href="/web/prd/yhzz/yb" target="dialog" max="true" rel="dlg_yhzz" width="800" height="480" ><span>银行自动转账授权书样本</span></a></li>
								<li><a href="/web/prd/nb/sell" target="_blank" max="true" rel="dlg_f2"><span>销售</span></a></li>
							</ul>
						</div>
					</div>
					<div class="tabsContent" style="height:345px;">
						<div>
						<div class="mytable">
						<table class="mytable" layoutH="230" width="98%">
						  <tr>
						    <td width="11%" rowspan="3">产品特色</td>
						    <td width="89%"><span class="my">（一）保障好</span><br>
						&nbsp;&nbsp;&nbsp;重疾保障：被保险人可获得多达50种的高额重疾保障。<br>
						&nbsp;&nbsp;&nbsp; 身故保障：如被保险人身故或全残（之前未理赔重大疾病保险金），可获得主险所交保费+附加险保额的保险保障。<br>
						&nbsp;&nbsp;&nbsp; 豁免保费：投保人在交费期间发生意外身故或全残（发生意外时未满65周岁），免交以后各期主附险保险费，被保险人仍可获得约定的各项保障和服务。</td>
						  </tr>
						  <tr>
						    <td><span class="my">（二）保底好</span><br>
						&nbsp;&nbsp;&nbsp; 满期还本：保险期满，可一次性返还主附险所交全部保费，并有额外收益。<br>
						&nbsp;&nbsp;&nbsp;  快速返还：犹豫期后即可领取生存年金，生存年金=1.1%*当期主险保险费*交费期间数，领取次数同交费期间数。我们为您提供生存年金累积生息服务。<br>
						&nbsp;&nbsp;&nbsp; 乐享分红：年年分红，累积生息。</td>
						  </tr>
						  <tr>
						    <td>（<span class="my">三）保单好</span><br>
						&nbsp;&nbsp;&nbsp;  犹豫期： 15日犹豫期，安心购买诚信足。<br>
						&nbsp;&nbsp;&nbsp; 变现灵活：行业领先，投保即可申请高达现金价值90%的保单质押借款，最大限度缓解资金压力。<br>
						&nbsp;&nbsp;&nbsp; 免息借款：行业创新，被保险人获得重疾赔付后，可申请免息借款，解燃眉之急。<br>
						&nbsp;&nbsp;&nbsp; 持续保障：如发生重疾责任赔付，主险责任继续有效。</td>
						  </tr>
						</table>
						</div>
						</div>
						<div>
						<div data-role="collapsible">
							<h3>简易计算工具（10年期）</h3>
						<form name="form1" method="post" action="">
						        <label for="copies">份数：</label>
						        <input name="copies" type="text" id="copies" maxlength="5" style="width:75px">
						        <fieldset data-role="controlgroup" data-mini="true">
						          <legend>交费期间：</legend>
						          <input type="radio" name="payTerm" id="payTerm3" value="3" checked="checked" />
						          <label for="payTerm3">三年</label>
						          
						          <input type="radio" name="payTerm" id="payTerm5" value="5" />
						          <label for="payTerm5">五年</label>
						        </fieldset>
						        <input type="button" name="count" id="count" value="计 算" onClick="return mycount( );">
						<label for="brst">年B保费（元）：</label>
						<input name="brst" type="text" id="brst" size="5" readonly>
						<label for="fj">附加险保费（元）：</label>
						<input name="fj" type="text" id="fj" size="5" readonly>
						</form>
						</div>
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
						    <td width="81%" colspan="2">中邮年年好重疾保障计划，注意主险和附加险名称的填写。<br>
						      （邮保通产品代码：0493，附加险不需录入）</td>
						  </tr>
						  <tr>
						    <td>投保单</td>
						    <td colspan="2">需要正确使用<span class="my">年年好重疾保障计划投保书</span>，<span class="myred">条款</span>附在投保书最后。</td>
						  </tr>
						  <tr>
						    <td>20万或以上大额件</td>
						    <td colspan="2">大额件必须留出客户身份证复印件，卡折复印件，如果被保险人非投保人本人需要关系证明，如指定受益人，也需关系证明。（反洗钱要求，与省分行稽核要求一致）</td>
						  </tr>
						  <tr>
						    <td rowspan="13">填单要点</td>
						    <td colspan="2" class="myred">注：60周岁以上客户作为投保人需要走人工核保，邮保通限制承保出单。<br>
						      广东：60周岁或以上必须进行录音录像且转人工核保。</td>
						  </tr>
						  <tr>
						    <td colspan="2">险种名称<br>
						      主险名称：<span class="my">中邮年年好B款年金保险</span><br>
						      （可简写为：年年好B）<br>
						      附件险名称：<span class="myred">中邮附加重大疾病保险</span></td>
						  </tr>
						  <tr>
						    <td colspan="2">被保险人年龄：出生满30天-55周岁</td>
						  </tr>
						  <tr>
						    <td colspan="2">职业请填写最高类别的职业,如建筑水电工（室内），职业代码为：0701015<br>
						        承保职业：1-4类；5-6类转人工核保</td>
						  </tr>
						  <tr>
						    <td colspan="2" class="my">基本保险金额可不用填写。</td>
						  </tr>
						  <tr>
						    <td colspan="2">份数：1份起售 每份1000元</td>
						  </tr>
						  <tr>
						    <td colspan="2">交费期间：3年交、5年交 （保险期间：10年）</td>
						  </tr>
						  <tr>
						    <td rowspan="2">保费比例</td>
						    <td>（3年交）	920：80</td>
						  </tr>
						  <tr>
						    <td>（5年交）	950：50</td>
						  </tr>
						  <tr>
						    <td colspan="2">告知事项在投保单背面，由投保人阅读告知事项并亲自在告知栏勾选实际情况</td>
						  </tr>
						  <tr>
						    <td colspan="2">投保人抄录语句项必须由投保人亲笔抄写</td>
						  </tr>
						  <tr>
						    <td colspan="2">投保人与被保险人分别需要本人亲笔签名，不得由他人代签名</td>
						  </tr>
						  <tr>
						    <td colspan="2">如被保险人为<span class="myred">未成年人</span>（未满18岁），被保险人签名处由其<span class="myred">监护人</span>（如父母）<span class="myred">亲笔签名</span>（监护人姓名）</td>
						  </tr>
						</table>
						</div>
						</div>
						<div data-role="collapsible">
							<h3>被保险人体检要求</h3>
							<div class="mytable">
						<table width="100%" class="mytable">
						  <tr >
						    <td rowspan="2" valign="center" >累计重疾险风险保额（万元） </td>
						    <td rowspan="2" valign="center" >累计寿险风险保额（万元） </td>
						    <td colspan="4" valign="center" >体检项目 </td>
						  </tr>
						  <tr >
						    <td valign="center" >18--30周岁 </td>
						    <td valign="center" >31-40周岁 </td>
						    <td valign="center" >41-50周岁 </td>
						    <td valign="top" >&#8805;&nbsp;51周岁 </td>
						  </tr>
						  <tr >
						    <td valign="center" >5---20（含） </td>
						    <td valign="center" >10---30（含） </td>
						    <td valign="center" >---- </td>
						    <td valign="center" >---- </td>
						    <td valign="center" >---- </td>
						    <td valign="top" >A </td>
						  </tr>
						  <tr >
						    <td valign="center" >20---30（含） </td>
						    <td valign="center" >30---50（含） </td>
						    <td valign="center" >---- </td>
						    <td valign="center" >---- </td>
						    <td valign="center" >A、B </td>
						    <td valign="top" >A、B </td>
						  </tr>
						  <tr >
						    <td valign="center" >30---35（含） </td>
						    <td valign="center" >50---100（含） </td>
						    <td valign="center" >---- </td>
						    <td colspan="3" valign="center" >A、B </td>
						  </tr>
						  <tr >
						    <td valign="center" >35---100（含） </td>
						    <td valign="center" >100---200（含） </td>
						    <td valign="center" >A、B </td>
						    <td colspan="3" valign="center" >A、B、C </td>
						  </tr>
						  <tr >
						    <td valign="center" >&nbsp;</td>
						    <td valign="center" >200以上 </td>
						    <td valign="center" >A、B、C </td>
						    <td colspan="3" valign="center" >A、B、C、D </td>
						  </tr>
						</table>
						</div>
						</div>
						<div data-role="collapsible">
						            <h3>给客户的承保资料 	&nbsp;&nbsp;&nbsp;&nbsp;<span class="ui-li-count">6项</span></h3>
						            <p>
						                <li>共6项：<br>
						                  <span class="myred">1、保险单；<br>
						                  2、投保单（客户联）（2013版或2016版投保单）；<br>3、投保提示书（投保书附带的那份）；<br>
						                  4、产品说明书+保险条款；<br>5、收费凭证；<br>
						                  6、银行自动转账授权书（客户联）。</span> <br>以上资料放保单夹中连同保单夹一同给客户。</li>
						            </p>
						        </div>
						        <div data-role="collapsible">
						            <h3>归档资料	&nbsp;&nbsp;&nbsp;&nbsp;<span class="ui-li-count">6项</span></h3>
						            <p>
						                <li>共6项：<br><span class="myred">1、投保单（公司联）（2013版或2016版投保单）；<br>2、投保提示书；<br>
						                3、中邮人寿保险股份有限公司银行保险投保书补充声明（单联）（如使用2016版投保单不需提供）；<br>4、银行自动转账授权书（公司联）；<br>
						                5、合同回执（如使用2016版投保单不需提供）；<br>
						                  6、身份证复印件、卡折复印件（每单留存），或相关关系证明。<br><br>特殊情况下：<br></span></li>
						                  1、<span class="my">邮保通出单承保提示书</span>（适用于邮保通出单无手机号码客户，可由网点人员填写）；<br>
						2、<span class="my">核心系统出单承保提示书</span>（适用于转人核件无手机号码的客户，可由网点人员填写）<br>
						3、<span class="my">投保单填写授权委托书</span>（适用于填写有困难或者文化程度低的客户，在客户签名授权后，可由受托人员代为填写投保单）
						            </p>
						        </div>
						</div>
						<div>
						<!-- hb -->
						<div class="mytable">
						<table width="100%" class="mytable">
						 <tr>
						   <td colspan="2" align="center"><strong><br>
						    投保规则</strong><br></td>
						 </tr>
						 <tr>
						   <td align="center">投保年龄</td>
						   <td>18周岁至70周岁<br>
						    <span class="myred">注：60周岁以上客户作为投保人需要走人工核保，邮保通限制承保出单。<br>
						    <strong>广东：60周岁或以上必须进行录音录像且转人工核保。</strong>    </span></td>
						 </tr>
						 <tr>
						   <td align="center">被保险人年龄</td>
						   <td>出生满30天至55周岁</td>
						 </tr>
						 <tr>
						   <td align="center">保险费</td>
						   <td>每份1000元，1份起售。</td>
						 </tr>
						 <tr>
						   <td align="center">交费方式</td>
						   <td>3年交、5年交</td>
						 </tr>
						 <tr>
						   <td align="center">保险期间</td>
						   <td>10年</td>
						 </tr>
						 <tr>
						   <td rowspan="2" align="center">保险责任</td>
						   <td><span class="my"><strong>保障</strong>：</span><br>
						       ●多达50种高额重疾保障<br>
						       ●与重疾保障相同的高额生命保障<br>
						       ●豁免保费<br>
						     ●持续保障</td>
						 </tr>
						 <tr>
						   <td><span class="my"><strong>财富</strong>：</span><br>
						       ●满期保险金<br>
						       ●生存年金<br>
						       ●保单红利<br>
						       ●高额保单借款<br>
						     ●免息借款</td>
						 </tr>
						</table>
						:<br>
						<table width="100%" class="mytable">
						  <tr>
						   <td colspan="5" align="center">不需提供健康材料<br>
						    可直接承保的最大份数和保费</td>
						 </tr>
						 <tr>
						   <td align="center">年龄</td>
						   <td colspan="4" align="center">男性</td>
						 </tr>
						 <tr>
						   <td align="center">&nbsp;</td>
						   <td colspan="2" align="center">3年交<br>
						      保10年期</td>
						   <td colspan="2" align="center">5年交<br>
						    保10年期</td>
						 </tr>
						 <tr>
						   <td align="center">&nbsp;</td>
						   <td align="center">份数</td>
						   <td align="center">保费</td>
						   <td align="center">份数</td>
						   <td align="center">保费</td>
						 </tr>
						 <tr>
						   <td align="center">0周岁</td>
						   <td align="center">7</td>
						   <td align="center">7000</td>
						   <td align="center">6</td>
						   <td align="center">6000</td>
						 </tr>
						 <tr>
						   <td align="center">1周岁</td>
						   <td align="center">6</td>
						   <td align="center">6000</td>
						   <td align="center">6</td>
						   <td align="center">6000</td>
						 </tr>
						 <tr>
						   <td align="center">&nbsp;</td>
						   <td colspan="4" align="center">女性</td>
						 </tr>
						 <tr>
						   <td align="center">&nbsp;</td>
						   <td colspan="2" align="center">3年交<br>
						      保10年期</td>
						   <td colspan="2" align="center">5年交<br>
						      保10年期</td>
						 </tr>
						 <tr>
						   <td align="center">&nbsp;</td>
						   <td align="center">份数</td>
						   <td align="center">保费</td>
						   <td align="center">份数</td>
						   <td align="center">保费</td>
						 </tr>
						 <tr>
						   <td align="center">0周岁</td>
						   <td align="center">6</td>
						   <td align="center">6000</td>
						   <td align="center">5</td>
						   <td align="center">5000</td>
						 </tr>
						 <tr>
						   <td align="center">1周岁</td>
						   <td align="center">5</td>
						   <td align="center">5000</td>
						   <td align="center">5</td>
						   <td align="center">5000</td>
						 </tr>
						</table>
						<br>
						<table width="100%" class="mytable">
						  <tr>
						   <td colspan="5" align="center">提供健康材料后<br>
						    可直接承保的份数范围</td>
						 </tr>
						 <tr>
						   <td align="center">年龄</td>
						   <td colspan="2" align="center">男性</td>
						   <td colspan="2" align="center">女性</td>
						 </tr>
						 <tr>
						   <td align="center">&nbsp;</td>
						   <td align="center">3年交<br>
						    保10年期</td>
						   <td align="center">5年交<br>
						    保10年期</td>
						   <td align="center">3年交<br>
						    保10年期</td>
						   <td align="center">5年交<br>
						    保10年期</td>
						 </tr>
						 <tr>
						   <td align="center">0周岁</td>
						   <td align="center">8份-15份</td>
						   <td align="center">7份-14份</td>
						   <td align="center">7份-12份</td>
						   <td align="center">6份-11份</td>
						 </tr>
						 <tr>
						   <td align="center">1周岁</td>
						   <td align="center">7份-13份</td>
						   <td align="center">7份-12份</td>
						   <td align="center">6份-11份</td>
						   <td align="center">6份-10份</td>
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