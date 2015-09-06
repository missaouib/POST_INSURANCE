<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
<!--
$("#background,#progressBar").hide();
//-->

function mycount() {
		var copies =  $("#copies").val();
		var payTerm = $('input[name="payTerm"]:checked').val();
		var duration = $('input[name="duration"]:checked').val();
		var brst;
		var fjrst = 0;
		var fjnumrst = 0;
		if(payTerm == 3) {
			brst = copies * 1000;
			if(duration == 15) {
				fjrst = copies * 50 * 10;
				fjnumrst = copies * 50;
			} else if(duration == 20) {
				fjrst = copies * 50 * 10;
				fjnumrst = copies * 50;
			} else if(duration == 70) {
				fjrst = copies * 100 * 10;
				fjnumrst = copies * 100;
			}
		} else {
			brst = copies * 1000;
			if(duration == 15) {
				fjrst = copies * 30 * 10;
				fjnumrst = copies * 30;
			} else if(duration == 20) {
				fjrst = copies * 30 * 10;
				fjnumrst = copies * 30;
			} else if(duration == 70) {
				fjrst = copies * 100 * 10;
				fjnumrst = copies * 100;
			}
		}
		$("#brst")[0].value=brst;
		$("#fjnum")[0].value=fjnumrst;
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
								<li><a href="/web/prd/nbplus/yb" target="dialog" max="true" rel="dlg_f1" width="800" height="480" ><span>样本</span></a></li>
								<li><a href="/web/prd/nbplus/yb2" target="dialog" max="true" rel="dlg_f12" width="800" height="480" ><span>详细样本</span></a></li>
								<li><a href="/web/prd/yhzz/yb" target="dialog" max="true" rel="dlg_yhzz" width="800" height="480" ><span>银行自动转账授权书样本</span></a></li>
								<li><a href="/web/prd/nbplus/sell" target="_blank" max="true" rel="dlg_f2"><span>销售</span></a></li>
							</ul>
						</div>
					</div>
					<div class="tabsContent" style="height:280px;">
						<div>
						<div class="mytable">
						<table class="mytable" layoutH="230" width="98%">
						  <tr>
						    <td width="11%" rowspan="3">产品特色</td>
						    <td width="89%"><span class="my"><strong>（一）保障好</strong></span><strong><br>
						&nbsp;&nbsp;&nbsp;重疾保障</strong>：被保险人可获得多达50种的高额重疾保障。<br>
						&nbsp;&nbsp;&nbsp; <strong>身故保障</strong>：如被保险人身故或全残（之前未理赔重大疾病保险金），可获得主险所交保费+附加险保额的保险保障。<br>
						&nbsp;&nbsp;&nbsp; <strong>豁免保费</strong>：投保人在交费期间发生意外身故或全残（发生意外时未满65周岁），免交以后各期主附险保险费，被保险人仍可获得约定的各项保障和服务。<br>
						&nbsp;&nbsp;&nbsp; <strong>持续保证</strong>：如发生重疾责任赔付，主险责任继续有效。</td>
						  </tr>
						  <tr>
						    <td><span class="my"><strong>（二）定制好</strong></span><strong><br>
						&nbsp;&nbsp;&nbsp; 投保定制</strong>：保险期间您可以选择保15年、保20年或保至70周岁；<br>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;交费方式您可选择3年交或5年交。<br>
						&nbsp;&nbsp;&nbsp; <strong> 保障定制</strong>：您可选择多份附加险与主险搭配，定制高保障功能组合，获得高额保险保障。<br>
						&nbsp;&nbsp;&nbsp;<strong>理财定制</strong>：您可选择适量附加险与主险搭配，定制偏理财功能组合，做好长期理财规划。</td>
						  </tr>
						  <tr>
						    <td><span class="my"><strong>（二）保底好</strong></span><strong><br>
						&nbsp;&nbsp;&nbsp; 满期还本</strong>：保险期满，可一次性返还满期保险金。<br>
						&nbsp;&nbsp;&nbsp; <strong> 快速返还</strong>：犹豫期后即可领取生存年金，生存年金=1.1%*当期主险保险费*交费期间数，领取次数同交费期间数。我们为您提供生存年金累积生息服务。<br>
						&nbsp;&nbsp;&nbsp; <strong>乐享分红</strong>：年年分红，累积生息。<br>
						<strong>&nbsp;&nbsp;&nbsp; 免息借款</strong>：行业创新，被保险人获得重疾赔付后，可申请免息借款解燃眉之急。<br>
						<strong>&nbsp;&nbsp;&nbsp; 变现灵活</strong>：行业领先，投保即可申请高达现金价值90%的保单质押借款，最大限度缓解资金压力。</td>
						  </tr>
						</table>
						</div>
						</div>
						<div>
						<div data-role="collapsible">
							<h3>简易计算工具（健康保障计划定制版）</h3>
						<form name="form1" method="post" action="">
						        <label for="copies">份数：</label>
						        <input name="copies" type="text" id="copies" maxlength="5" style="width:75px">
						        <fieldset data-role="controlgroup" data-mini="true">
						          <legend>交费期间：</legend>
						          <input type="radio" name="payTerm" id="payTerm3" value="3" checked="checked" />
						          <label for="payTerm3">三年</label>
						          
						          <input type="radio" name="payTerm" id="payTerm5" value="5" />
						          <label for="payTerm5">五年</label>
						          <br>
						        保险期间：<br>
						        <input type="radio" name="duration" id="duration15" value="15" checked="checked" />
						        <label for="duration15">15年</label>
						        <input type="radio" name="duration" id="duration20" value="20" />
						        <label for="duration20">20年</label>
						        <input type="radio" name="duration" id="duration70" value="70" />
						        <label for="duration70">至70周岁</label>
						        </fieldset>
						        <input type="button" name="count" id="count" value="计 算" onClick="return mycount( );">
						<label for="brst">年B保费</label>
						<input name="brst" type="text" id="brst" size="5" readonly>
						<label for="fj">元；附加险最高可买</label>
						<input name="fjnum" type="text" id="fjnum" size="5" readonly>
						<label>份，保费</label>
						<input name="fj" type="text" id="fj" size="5" readonly>
						<label>元</label>
						</form>
						</div>
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
						    <td width="81%" colspan="2">中邮年年好健康保障计划（定制版），注意主险和附加险名称的填写。<br>
						      （邮保通产品代码：<br>
						      主险年年好B - <strong>0706</strong>，<br>
						      中邮附加重大疾病保险 - <strong>0707</strong>）</td>
						  </tr>
						  <tr>
						    <td>投保单</td>
						    <td colspan="2">需要正确使用<strong class="my">年年好重疾保障计划投保书，可用2016版。</strong></td>
						  </tr>
						  <tr>
						    <td>20万或以上大额件</td>
						    <td colspan="2">大额件必须留出客户身份证复印件，卡折复印件，如果被保险人非投保人本人需要关系证明，如指定受益人，也需关系证明。（反洗钱要求，与省分行稽核要求一致）</td>
						  </tr>
						  <tr>
						    <td rowspan="14">填单要点</td>
						    <td colspan="2"class="myred">注：60周岁以上客户作为投保人需要走人工核保，邮保通限制承保出单。<br>
						      <strong>广东：60周岁或以上必须进行录音录像且转人工核保。</strong></td>
						  </tr>
						  <tr>
						    <td colspan="2">险种名称<br>
						      主险名称：<span class="my"><strong>中邮年年好B款年金保险</strong></span><br>
						      （可<strong>简写</strong>为：<strong>年年好B</strong>）<br>
						      附件险名称：<strong class="myred">中邮附加重大疾病保险</strong></td>
						  </tr>
						  <tr>
						    <td colspan="2">被保险人年龄：出生满30天-55周岁</td>
						  </tr>
						  <tr>
						    <td colspan="2"><strong>职业请填写最高类别的职业</strong>,如建筑水电工（室内），职业代码为：0701015<br>
						        <strong>承保职业</strong>：主险1-6类、附加险1-4类（5-6类转人工核保）</td>
						  </tr>
						  <tr>
						    <td colspan="2"class="my"><strong>基本保险金额可不用填写</strong>。</td>
						  </tr>
						  <tr>
						    <td colspan="2">份数：主附险均1份起售 每份1000元、附加险每份10元。</td>
						  </tr>
						  <tr>
						    <td colspan="2"><p>交费期间：3年交、5年交 <br>
						      保险期间：15年、20年、保至70周岁</p></td>
						  </tr>
						  <tr>
						    <td rowspan="3">保费比例</td>
						    <td><p>3年交保15/20年	<br>
						      主附险比例≤1:50（即一份主险最多<strong>50</strong>份附加险）</p></td>
						  </tr>
						  <tr>
						    <td>5年交保15/20年<br>
						      主附险比例≤1:30（即一份主险最多<strong>30</strong>份附加险）</td>
						  </tr>
						  <tr>
						    <td>3/5年交保至70周岁<br>
						      主附险比例≤1:100（即一份主险最多<strong>100</strong>份附加险）</td>
						  </tr>
						  <tr>
						    <td colspan="2">告知事项在投保单背面，由投保人阅读告知事项并<strong>亲自</strong>在告知栏勾选实际情况</td>
						  </tr>
						  <tr>
						    <td colspan="2"><strong>投保人抄录语句项必须由投保人亲笔抄写</strong></td>
						  </tr>
						  <tr>
						    <td colspan="2">投保人与被保险人分别需要<strong>本人亲笔签名</strong>，不得由他人代签名</td>
						  </tr>
						  <tr>
						    <td colspan="2">如被保险人为<span class="myred"><strong>未成年人</strong></span>（未满18岁），被保险人签名处由其<strong><span class="myred">监护人</span></strong>（如父母）<strong><span class="myred">亲笔签名</span></strong>（监护人姓名）</td>
						  </tr>
						</table>
						</div>
						</div>
						<div data-role="collapsible">
							<h3>被保险人体检要求</h3>
							<div class="mytable">
						<table width="100%" class="mytable">
						  <tr >
						    <td rowspan="2" valign="center">累计重疾险风险保额（万元） </td>
						    <td rowspan="2" valign="center">累计寿险风险保额（万元） </td>
						    <td colspan="4" valign="center">体检项目 </td>
						  </tr>
						  <tr >
						    <td valign="center">18--30周岁 </td>
						    <td valign="center">31-40周岁 </td>
						    <td valign="center">41-50周岁 </td>
						    <td valign="top">&#8805;&nbsp;51周岁 </td>
						  </tr>
						  <tr >
						    <td valign="center">5---20（含） </td>
						    <td valign="center">10---30（含） </td>
						    <td valign="center">---- </td>
						    <td valign="center">---- </td>
						    <td valign="center">---- </td>
						    <td valign="top">A </td>
						  </tr>
						  <tr >
						    <td valign="center">20---30（含） </td>
						    <td valign="center">30---50（含） </td>
						    <td valign="center">---- </td>
						    <td valign="center">---- </td>
						    <td valign="center">A、B </td>
						    <td valign="top">A、B </td>
						  </tr>
						  <tr >
						    <td valign="center">30---35（含） </td>
						    <td valign="center">50---100（含） </td>
						    <td valign="center">---- </td>
						    <td colspan="3" valign="center">A、B </td>
						  </tr>
						  <tr >
						    <td valign="center">35---100（含） </td>
						    <td valign="center">100---200（含） </td>
						    <td valign="center">A、B </td>
						    <td colspan="3" valign="center">A、B、C </td>
						  </tr>
						  <tr >
						    <td valign="center">&nbsp;</td>
						    <td valign="center">200以上 </td>
						    <td valign="center">A、B、C </td>
						    <td colspan="3" valign="center">A、B、C、D </td>
						  </tr>
						</table>
						</div>
						</div>
						<div data-role="collapsible">
						            <h3>给客户的承保资料 	&nbsp;&nbsp;&nbsp;&nbsp;<span class="ui-li-count">6项</span></h3>
						            <p>
						                <li>共6项：<br>
						                  <strong class="my"><span class="myred">1、保险单；<br>
						                  2、投保单（客户联）（2013版或2016版投保单）；<br>3、投保提示书（投保书附带的那份）；<br>
						                  4、产品说明书+保险条款；<br>5、收费凭证；<br>
						                  6、银行自动转账授权书（客户联）。</span></strong> <br>以上资料放保单夹中连同保单夹一同给客户。</li>
						            </p>
						        </div>
						        <div data-role="collapsible">
						            <h3>归档资料	&nbsp;&nbsp;&nbsp;&nbsp;<span class="ui-li-count">6项</span></h3>
						            <p>
						                <li>共6项：<br><strong class="my"><span class="myred">1、投保单（公司联）（2013版或2016版投保单）；<br>2、投保提示书；<br>
						                3、中邮人寿保险股份有限公司银行保险投保书补充声明（单联）（如使用2016版投保单不需提供）；<br>4、银行自动转账授权书（公司联）；<br>
						                5、合同回执（如使用2016版投保单不需提供）；<br>
						                  6、身份证复印件、卡折复印件（每单留存），或相关关系证明。<br><br>特殊情况下：<br></span></strong></li>
						                  1、<strong class="my">邮保通出单承保提示书</strong>（适用于邮保通出单无手机号码客户，可由网点人员填写）；<br>
						2、<span class="my"><strong>核心系统出单承保提示书</strong></span>（适用于转人核件无手机号码的客户，可由网点人员填写）<br>
						3、<span class="my"><strong>投保单填写授权委托书</strong></span>（适用于填写有困难或者文化程度低的客户，在客户签名授权后，可由受托人员代为填写投保单）
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
						   <td>主险每份1000元，1份起售；附加险每份10元，1份起售。</td>
						 </tr>
						 <tr>
						   <td align="center">交费方式</td>
						   <td>3年交、5年交</td>
						 </tr>
						 <tr>
						   <td align="center">保险期间</td>
						   <td>15年、20年、至70周岁</td>
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
						    <td width="623" colspan="3"><p align="center"><strong>未成年被保险人各年龄段投保简表 </strong></p></td>
						  </tr>
						  <tr>
						    <td width="137"><p align="center"><strong>被保险人年龄<br>
						      （Y） </strong></p></td>
						    <td width="150"><p align="center"><strong>累计重疾风险保额<br>
						      （SUM） </strong></p></td>
						    <td width="336"><p align="center"><strong>备注 </strong></p></td>
						  </tr>
						  <tr>
						    <td width="137" rowspan="3"><p align="center">30天≤Y≤1周岁 </p></td>
						    <td width="150"><p align="center">0&lt;SUM&lt;10万 </p></td>
						    <td width="336"><p align="left">均无异常，邮保通可直接出单 </p></td>
						  </tr>
						  <tr>
						    <td width="150"><p align="center">10万≤SUM≤20万 </p></td>
						    <td width="336"><p align="left">须提供医学出生证明、婴幼儿健康手册或填写《婴幼儿健康问卷》,如均无异常，邮保通可直接出单 </p></td>
						  </tr>
						  <tr>
						    <td width="150"><p align="center">SUM&gt;20万 </p></td>
						    <td width="336"><p align="left">不接受投保 </p></td>
						  </tr>
						  <tr>
						    <td width="137" rowspan="2"><p align="center">2周岁≤Y≤3周岁 </p></td>
						    <td width="150"><p align="center">0&lt;SUM≤20万 </p></td>
						    <td width="336"><p align="left">均无异常，邮保通可直接出单 </p></td>
						  </tr>
						  <tr>
						    <td width="150"><p align="center">SUM&gt;20万 </p></td>
						    <td width="336"><p align="left">不接受投保 </p></td>
						  </tr>
						  <tr>
						    <td width="137" rowspan="2"><p align="center">4周岁≤Y≤17周岁 </p></td>
						    <td width="150"><p align="center">0&lt;SUM≤30万 </p></td>
						    <td width="336"><p align="left">均无异常，邮保通可直接出单 </p></td>
						  </tr>
						  <tr>
						    <td width="150"><p align="center">SUM&gt;30万 </p></td>
						    <td width="336"><p align="left">不接受投保 </p></td>
						  </tr>
						</table>
						<strong>成年人体检保额表</strong><br>
						<table width="100%" class="mytable">
						  <tr >
						    <td rowspan="2" valign="center"><p >累计重疾险风险保额（万元） </p></td>
						    <td rowspan="2" valign="center"><p >累计寿险风险保额（万元） </p></td>
						    <td colspan="4" valign="center"><p >体检项目 </p></td>
						  </tr>
						  <tr >
						    <td valign="center"><p >18--30周岁 </p></td>
						    <td valign="center"><p >31-40周岁 </p></td>
						    <td valign="center"><p >41-50周岁 </p></td>
						    <td valign="top"><p >&#8805;&nbsp;51周岁 </p></td>
						  </tr>
						  <tr >
						    <td valign="center"><p >5---20（含） </p></td>
						    <td valign="center"><p >10---30（含） </p></td>
						    <td valign="center"><p >---- </p></td>
						    <td valign="center"><p >---- </p></td>
						    <td valign="center"><p >---- </p></td>
						    <td valign="top"><p >A </p></td>
						  </tr>
						  <tr >
						    <td valign="center"><p >20---30（含） </p></td>
						    <td valign="center"><p >30---50（含） </p></td>
						    <td valign="center"><p >---- </p></td>
						    <td valign="center"><p >---- </p></td>
						    <td valign="center"><p >A、B </p></td>
						    <td valign="top"><p >A、B </p></td>
						  </tr>
						  <tr >
						    <td valign="center"><p >30---35（含） </p></td>
						    <td valign="center"><p >50---100（含） </p></td>
						    <td valign="center"><p >---- </p></td>
						    <td colspan="3" valign="center"><p >A、B </p></td>
						  </tr>
						  <tr >
						    <td valign="center"><p >35---100（含） </p></td>
						    <td valign="center"><p >100---200（含） </p></td>
						    <td valign="center"><p >A、B </p></td>
						    <td colspan="3" valign="center"><p >A、B、C </p></td>
						  </tr>
						  <tr >
						    <td valign="center"><p >&nbsp;</p></td>
						    <td valign="center"><p >200以上 </p></td>
						    <td valign="center"><p >A、B、C </p></td>
						    <td colspan="3" valign="center"><p >A、B、C、D </p></td>
						  </tr>
						</table>
						<br>
						<p align="left"><strong>基本保险金额表 </strong></p>
						<p align="center">中邮富富余1号两全保险（分红型）保险金额表（每1000元保险费） </p>
						<p align="right">             单位：人民币元  </p>
						<div class="mytable">
						<table class="mytable">
						  <tbody>
						    <tr>
						      <td width="284"><p align="center">  投保年龄 </p></td>
						      <td width="284"><p align="center">     趸交（一次性交清） </p></td>
						    </tr>
						    <tr>
						      <td width="284"><p align="center">0-25 </p></td>
						      <td width="284"><p align="center">1070 </p></td>
						    </tr>
						    <tr>
						      <td width="284"><p align="center">26-35 </p></td>
						      <td width="284"><p align="center">1067 </p></td>
						    </tr>
						    <tr>
						      <td width="284"><p align="center">36-45 </p></td>
						      <td width="284"><p align="center">1066 </p></td>
						    </tr>
						    <tr>
						      <td width="284"><p align="center">46-55 </p></td>
						      <td width="284"><p align="center">1064 </p></td>
						    </tr>
						    <tr>
						      <td width="284"><p align="center">56-65 </p></td>
						      <td width="284"><p align="center">1058 </p></td>
						    </tr>
						  </tbody>
						</table>
						</div>
						<!-- end of hb -->
						</div>
						</div>
						<div>样本</div>
						<div>详细样本</div>
						<div>银行自动转账授权书填写样本</div>
						<div>销售辅助工具</div>
					</div>
				</div>
		</div>
</div>